package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.CommonBatchVO;

public interface CommonBatchService
{

   public abstract PagedListHolder getSalaryBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CommonBatchVO getCommonBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int updateCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int insertCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

}
