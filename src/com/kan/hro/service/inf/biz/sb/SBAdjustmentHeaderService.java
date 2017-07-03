package com.kan.hro.service.inf.biz.sb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentDTO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;

public interface SBAdjustmentHeaderService
{
   public abstract PagedListHolder getSBAdjustmentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SBAdjustmentHeaderVO getSBAdjustmentHeaderVOByAdjustmentHeaderId( final String sbAdjustmentHeaderId ) throws KANException;

   public abstract int updateSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract int insertSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract int deleteSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;
   
   public abstract List< Object > getSBAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< SBAdjustmentDTO > getSBAdjustmentDTOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;
}
