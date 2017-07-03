package com.kan.hro.service.inf.biz.sb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;

public interface SBFeedbackDetailTempService
{
   public abstract PagedListHolder getSBDetailTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SBDetailTempVO getSBDetailTempVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException;

   public abstract int insertSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException;

   public abstract int deleteSBDetailTemp( final String sbDetailId ) throws KANException;

   public abstract List< Object > getSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO ) throws KANException;
   
}
