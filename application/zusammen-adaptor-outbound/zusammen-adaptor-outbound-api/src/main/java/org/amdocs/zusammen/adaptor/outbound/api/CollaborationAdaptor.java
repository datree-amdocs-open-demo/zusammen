/*
 * Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.adaptor.outbound.api;


import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreMergeChange;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;

public interface CollaborationAdaptor {

  void createItem(SessionContext context, Id itemId, Info info);

  void updateItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                         Id versionId, ItemVersionData data);

  void updateItemVersion(SessionContext context, Id itemId, Id versionId,
                         ItemVersionData data);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  CorePublishResult publishItemVersion(SessionContext context, Id itemId, Id versionId,
                                       String message);

  CoreMergeResult syncItemVersion(SessionContext context, Id itemId, Id versionId);

  CoreMergeResult mergeItemVersion(SessionContext context, Id itemId, Id versionId,
                                   Id sourceVersionId);

  CoreElement getElement(SessionContext context, ElementContext elementContext,
                         Namespace namespace, Id elementId);

  void createElement(SessionContext context, ElementContext elementContext, CoreElement element);

  void updateElement(SessionContext context, ElementContext elementContext, CoreElement element);

  void deleteElement(SessionContext context, ElementContext elementContext, CoreElement element);

  void commitEntities(SessionContext context, ElementContext elementContext, String message);

  ItemVersionHistory listItemVersionHistory(SessionContext context, Id itemId, Id versionId);

  CoreMergeChange revertItemVersionHistory(SessionContext context, Id itemId, Id
      versionId, Id changeId);
}
