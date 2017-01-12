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

package org.amdocs.tsuzammen.core.api.types;

import org.amdocs.tsuzammen.datatypes.collaboration.ChangeType;

public class ChangedCoreElement {
  private ChangeType changeType;
  private CoreElement coreElement;

  public void setChangeType(ChangeType changeType) {
    this.changeType = changeType;
  }

  public void setCoreElement(CoreElement coreElement) {
    this.coreElement = coreElement;
  }

  public ChangeType getChangeType() {
    return changeType;
  }

  public CoreElement getCoreElement() {
    return coreElement;
  }
}
