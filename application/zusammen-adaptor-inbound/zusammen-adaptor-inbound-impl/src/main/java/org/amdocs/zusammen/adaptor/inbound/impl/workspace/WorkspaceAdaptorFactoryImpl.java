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

package org.amdocs.zusammen.adaptor.inbound.impl.workspace;

import org.amdocs.zusammen.adaptor.inbound.api.workspace.WorkspaceAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.workspace.WorkspaceAdaptorFactory;
import org.amdocs.zusammen.datatypes.SessionContext;


public class WorkspaceAdaptorFactoryImpl extends WorkspaceAdaptorFactory {
  private static final WorkspaceAdaptor INSTANCE = new WorkspaceAdaptorImpl();

  @Override
  public WorkspaceAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
