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

package org.amdocs.zusammen.core.api.item;


import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.datatypes.itemversion.Tag;

import java.util.Collection;
import java.util.Date;

public interface ItemVersionManager {

  Collection<ItemVersion> list(SessionContext context, Space space, Id itemId);

  boolean isExist(SessionContext context, Space space, Id itemId, Id versionId);

  ItemVersion get(SessionContext context, Space space, Id itemId, Id versionId);

  Id create(SessionContext context, Id itemId, Id baseVersionId, ItemVersionData data);

  void update(SessionContext context, Id itemId, Id versionId, ItemVersionData data);

  void delete(SessionContext context, Id itemId, Id versionId);

  void tag(SessionContext context, Id itemId, Id versionId, Id changeId, Tag tag);

  void publish(SessionContext context, Id itemId, Id versionId, String message);

  CoreMergeResult sync(SessionContext context, Id itemId, Id versionId);

  CoreMergeResult merge(SessionContext context, Id itemId, Id versionId, Id sourceVersionId);

  ItemVersionHistory listHistory(SessionContext context, Id itemId, Id versionId);

  void resetHistory(SessionContext context, Id itemId, Id versionId, String changeRef);

  void updateModificationTime(SessionContext context, Space aPrivate, Id itemId, Id versionId,
                              Date modificationTime);
}
