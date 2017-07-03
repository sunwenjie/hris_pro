package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ImportDetailService
{
   public abstract PagedListHolder getImportDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ImportDetailVO getImportDetailVOByImportDetailId( final String importDetailId ) throws KANException;

   public abstract int insertImportDetail( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract int updateImportDetail( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract int deleteImportDetail( final ImportDetailVO importDetailVO ) throws KANException;

   public abstract List< Object > getImportDetailVOsByImportHeaderId( final String importHeaderId ) throws KANException;
   
   public abstract int deleteImportDetail( final String importHeaderId ) throws KANException;
}
