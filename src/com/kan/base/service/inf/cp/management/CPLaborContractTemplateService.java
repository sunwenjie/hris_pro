package com.kan.base.service.inf.cp.management;

import java.util.List;

import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CPLaborContractTemplateService
{
   public abstract PagedListHolder getLaborContractTemplateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract LaborContractTemplateVO getLaborContractTemplateVOByLaborContractTemplateId( final String laborContractTemplateId ) throws KANException;

   public abstract int insertLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;

   public abstract int updateLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;

   public abstract int deleteLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;
   
   public abstract List< Object > getLaborContractTemplateVOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getLaborContractTemplateVOsByContractTypeId( final String contractTypeId ) throws KANException;
}
