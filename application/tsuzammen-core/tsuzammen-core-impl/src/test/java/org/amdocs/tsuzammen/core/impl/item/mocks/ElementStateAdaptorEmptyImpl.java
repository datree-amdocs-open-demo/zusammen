/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package org.amdocs.tsuzammen.core.impl.item.mocks;

import org.amdocs.tsuzammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.CoreEntity;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementContext;

import java.net.URI;

public class ElementStateAdaptorEmptyImpl implements ElementStateAdaptor {

  @Override
  public URI getNamespace(SessionContext context, ElementContext elementContext,
                          Id elementId) {
    return null;
  }

  @Override
  public boolean isExist(SessionContext context, ElementContext elementContext, Id elementId) {
    return true;
  }

  @Override
  public CoreEntity get(SessionContext context, ElementContext elementContext, Id elementId) {
    return null;
  }

  @Override
  public void create(SessionContext context, ElementContext elementContext, CoreEntity entity) {

  }

  @Override
  public void save(SessionContext context, ElementContext elementContext, CoreEntity entity) {

  }
}
