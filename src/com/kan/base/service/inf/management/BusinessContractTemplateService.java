package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface BusinessContractTemplateService
{
   public abstract PagedListHolder getBusinessContractTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BusinessContractTemplateVO getBusinessContractTemplateVOByBusinessContractTemplateId( final String businessContractTemplateId ) throws KANException;

   public abstract int insertBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract int updateBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract int deleteBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;
   
   public abstract List< Object > getBusinessContractTemplateVOsByAccountId( final String accountId ) throws KANException;

}
