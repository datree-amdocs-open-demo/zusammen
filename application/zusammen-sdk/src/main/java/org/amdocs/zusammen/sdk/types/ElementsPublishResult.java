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

package org.amdocs.zusammen.sdk.types;

import java.util.ArrayList;
import java.util.Collection;

public class ElementsPublishResult {

  private Collection<ChangedElementData> changedElements = new ArrayList<>();

  public void addChangedElement(ChangedElementData changedElement) {
    this.changedElements.add(changedElement);
  }

  public Collection<ChangedElementData> getChangedElements() {
    return changedElements;
  }

  public void setChangedElements(Collection<ChangedElementData> changedElements) {
    this.changedElements = changedElements;
  }
}
