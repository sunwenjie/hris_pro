package com.kan.hro.service.inf.biz.sb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public interface SBDetailService
{
   public abstract PagedListHolder getSBDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SBDetailVO getSBDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateSBDetail( final SBDetailVO sbDetailVO ) throws KANException;

   public abstract int insertSBDetail( final SBDetailVO sbDetailVO ) throws KANException;

   public abstract int deleteSBDetail( final String sbDetailId ) throws KANException;

   public abstract List< Object > getSBDetailVOsByCondition( final SBDetailVO sbDetailVO ) throws KANException;
   
   public abstract List< Object > getSBDetailVOsBySbHeaderCond( final SBHeaderVO sbHeaderVO ) throws KANException;
}
