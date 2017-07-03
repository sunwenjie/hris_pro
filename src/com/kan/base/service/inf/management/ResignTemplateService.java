package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ResignTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ResignTemplateService
{
   public abstract PagedListHolder getResignTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ResignTemplateVO getResignTemplateVOByResignTemplateId( final String resignTemplateId ) throws KANException;

   public abstract int insertResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract int updateResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract int deleteResignTemplate( final ResignTemplateVO ResignTemplateVO ) throws KANException;

   public abstract List< Object > getResignTemplateVOsByAccountId( final String accountId ) throws KANException;

}
