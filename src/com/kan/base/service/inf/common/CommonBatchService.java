package com.kan.base.service.inf.common;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CommonBatchService
{

   public abstract PagedListHolder getCommonBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CommonBatchVO getCommonBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int updateCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int insertCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

}