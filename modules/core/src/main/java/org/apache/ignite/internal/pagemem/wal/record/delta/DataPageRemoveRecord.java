/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.pagemem.wal.record.delta;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.internal.pagemem.PageMemory;
import org.apache.ignite.internal.processors.cache.database.tree.io.DataPageIO;
import org.apache.ignite.internal.util.typedef.internal.S;

/**
 *
 */
public class DataPageRemoveRecord extends PageDeltaRecord {
    /** */
    private int itemId;

    /**
     * @param cacheId Cache ID.
     * @param pageId Page ID.
     * @param itemId Item ID.
     */
    public DataPageRemoveRecord(int cacheId, long pageId, int itemId) {
        super(cacheId, pageId);

        this.itemId = itemId;
    }

    /**
     * @return Item ID.
     */
    public int itemId() {
        return itemId;
    }

    /** {@inheritDoc} */
    @Override public void applyDelta(PageMemory pageMem, long pageAddr)
        throws IgniteCheckedException {
        DataPageIO io = DataPageIO.VERSIONS.forPage(pageAddr);

        io.removeRow(pageAddr, itemId, pageMem.pageSize());
    }

    /** {@inheritDoc} */
    @Override public RecordType type() {
        return RecordType.DATA_PAGE_REMOVE_RECORD;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(DataPageRemoveRecord.class, this, super.toString());
    }
}
