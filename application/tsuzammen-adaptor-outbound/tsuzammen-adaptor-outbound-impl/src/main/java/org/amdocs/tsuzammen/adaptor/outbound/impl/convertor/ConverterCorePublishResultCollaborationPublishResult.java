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

package org.amdocs.tsuzammen.adaptor.outbound.impl.convertor;

import org.amdocs.tsuzammen.core.api.types.ChangedCoreElement;
import org.amdocs.tsuzammen.core.api.types.CoreElementConflict;

import org.amdocs.tsuzammen.core.api.types.CorePublishResult;
import org.amdocs.tsuzammen.sdk.types.CollaborationChangedElementData;
import org.amdocs.tsuzammen.sdk.types.CollaborationElementDataConflicts;
import org.amdocs.tsuzammen.sdk.types.searchindex.CollaborationPublishResult;

import java.util.ArrayList;
import java.util.Collection;

public class ConverterCorePublishResultCollaborationPublishResult {
  public static CorePublishResult getCorePublishResult(CollaborationPublishResult
                                                           collaborationPublishResult) {
    CorePublishResult corePublishResult = new CorePublishResult();

    corePublishResult
        .setChangedCoreElementCollection(convertChangedElementData(collaborationPublishResult
            .getCollaborationChangedElementDataCollection()));
    return corePublishResult;
  }

  private static Collection<ChangedCoreElement> convertChangedElementData(
      Collection<CollaborationChangedElementData> collaborationChangedElementDataCollection) {

    Collection<ChangedCoreElement> changedCoreElements = new ArrayList<>();
    ChangedCoreElement changedCoreElement;
    for (CollaborationChangedElementData collaborationChangedElementData : collaborationChangedElementDataCollection) {
      changedCoreElement =
          ConverterChangedCoreElementCollaborationChangedElementData.getChangedCoreElement(
              collaborationChangedElementData);
      changedCoreElements.add(changedCoreElement);
    }
    return changedCoreElements;
  }

  private static Collection<CoreElementConflict> convertElementConflicts(
      Collection<CollaborationElementDataConflicts> collaborationElementDataConflicts) {
    Collection<CoreElementConflict> coreElementConflicts = new ArrayList<>();
    CoreElementConflict coreElementConflict;

    for (CollaborationElementDataConflicts conflict : collaborationElementDataConflicts) {
      coreElementConflict = new CoreElementConflict();
      coreElementConflict.
          setLocalCoreElement(ConverterCoreElementElementData.getCoreElement(conflict
              .getLocalElementData()));
      coreElementConflict.setRemoteCoreElement(ConverterCoreElementElementData.getCoreElement
          (conflict
              .getRemoteElementData()));
      coreElementConflicts.add(coreElementConflict);
    }
    return coreElementConflicts;
  }
}
