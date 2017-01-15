/*
 * Copyright © 2016 European Support Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.amdocs.zusammen.core.impl.item;

import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.zusammen.core.api.item.ElementManager;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.impl.Messages;
import org.amdocs.zusammen.datatypes.FetchCriteria;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementAction;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;

public class ElementManagerImpl implements ElementManager {
  private static final String UNSUPPORTED_ACTION_ERR_MSG = "Action %s is not supported";

  @Override
  public Collection<ElementInfo> list(SessionContext context, ElementContext elementContext,
                                      Id elementId) {
    return getStateAdaptor(context).list(context, elementContext, elementId);
  }

  @Override
  public ElementInfo getInfo(SessionContext context, ElementContext elementContext,
                             Id elementId, FetchCriteria fetchCriteria) {
    return getStateAdaptor(context).get(context, elementContext, elementId, fetchCriteria);
  }

  @Override
  public CoreElement get(SessionContext context, ElementContext elementContext,
                         Id elementId, FetchCriteria fetchCriteria) {
    return null;
  }

  @Override
  public void save(SessionContext context, ElementContext elementContext,
                   CoreElement element, String message) {
    // TODO error handling
    validateItemVersionExistence(context, elementContext.getItemId(),
        elementContext.getVersionId());

    Namespace parentNamespace =
        element.getAction() == ElementAction.CREATE ? Namespace.EMPTY_NAMESPACE : null;
    traverse(context, elementContext, parentNamespace, element);
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getSearchIndexAdaptor(context).search(context, searchCriteria);
  }

  private void traverse(SessionContext context, ElementContext elementContext,
                        Namespace parentNamespace, CoreElement element) {
    Namespace namespace;
    switch (element.getAction()) {
      case CREATE:
        namespace = create(context, elementContext, parentNamespace, element);
        break;
      case UPDATE:
        namespace = update(context, elementContext, parentNamespace, element);
        break;
      case DELETE:
        namespace = delete(context, elementContext, parentNamespace, element);
        break;
      case IGNORE:
        namespace = ignore(context, elementContext, parentNamespace, element);
        break;
      default:
        throw new RuntimeException(
            String.format(UNSUPPORTED_ACTION_ERR_MSG, element.getAction()));
    }

    element.getSubElements().forEach(subElement ->
        traverse(context, elementContext, namespace, subElement));
  }

  private Namespace create(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace,
                           CoreElement element) {
    element.setId(new Id());
    // todo consider refactoring the set of the element id as the parentId of the sub elements.
    // This create action should act only on the current elemnt and should not access any other
    // elements in the hierarchy.
    element.getSubElements().forEach(subElement -> subElement.setParentId(element.getId()));

    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element);

    getCollaborationAdaptor(context).createElement(context, elementContext, namespace, element);
    getStateAdaptor(context).create(context, getElementInfo(elementContext, namespace, element));
    getSearchIndexAdaptor(context).createElement(context, elementContext, element, Space.PRIVATE);

    return namespace;
  }

  private Namespace update(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element);

    getCollaborationAdaptor(context).saveElement(context, elementContext, namespace, element);
    getStateAdaptor(context).save(context, getElementInfo(elementContext, namespace, element));
    getSearchIndexAdaptor(context).updateElement(context, elementContext, element, Space.PRIVATE);

    return namespace;
  }

  private Namespace delete(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element);

    getCollaborationAdaptor(context).deleteElement(context, elementContext, namespace, element);
    getStateAdaptor(context).delete(context, getElementInfo(elementContext, namespace, element));
    getSearchIndexAdaptor(context).deleteElement(context, elementContext, element, Space.PRIVATE);

    return namespace;
  }

  private Namespace ignore(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    return getNamespace(context, elementContext, parentNamespace, element);
  }

  private Namespace getNamespace(SessionContext context, ElementContext elementContext,
                                 Namespace parentNamespace, CoreElement element) {
    if (parentNamespace != null) {
      return new Namespace(parentNamespace, element.getId());
    }

    ElementInfo elementInfo =
        getStateAdaptor(context).get(context, elementContext, element.getId(), null);
    element.setParentId(elementInfo.getParentId());
    return elementInfo.getNamespace();
  }

  private ElementInfo getElementInfo(ElementContext elementContext, Namespace namespace,
                                     CoreElement coreElement) {
    ElementInfo elementInfo = new ElementInfo(elementContext.getItemId(),
        elementContext.getVersionId(), coreElement.getId(), coreElement.getParentId());
    elementInfo.setInfo(coreElement.getInfo());
    elementInfo.setRelations(coreElement.getRelations());
    return elementInfo;
  }

  private void validateItemVersionExistence(SessionContext context, Id itemId, Id versionId) {
    String space = context.getUser().getUserName();
    if (!getItemVersionStateAdaptor(context).isItemVersionExist(context, itemId, versionId)) {
      String message = getItemStateAdaptor(context).isItemExist(context, itemId)
          ? String
          .format(Messages.ITEM_VERSION_NOT_EXIST, itemId.toString(), versionId.toString(), space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);
      throw new RuntimeException(message);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ElementStateAdaptor getStateAdaptor(SessionContext context) {
    return ElementStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected SearchIndexAdaptor getSearchIndexAdaptor(SessionContext context) {
    return SearchIndexAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionStateAdaptor getItemVersionStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getItemStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}