package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface BusinessTypeService
{
   public abstract PagedListHolder getBusinessTypeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BusinessTypeVO getBusinessTypeVOByBusinessTypeId( final String businessTypeId ) throws KANException;

   public abstract int insertBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract int updateBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException;

   public abstract int deleteBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException;
   
   public abstract List< Object > getBusinessTypeVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getBusinessTypeBaseViews( final String accountId ) throws KANException;
}
