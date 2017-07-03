package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ContractTypeService
{
   public abstract PagedListHolder getContractTypeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ContractTypeVO getContractTypeVOByTypeId( final String typeId ) throws KANException;

   public abstract int insertContractType( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract int updateContractType( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract int deleteContractType( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract List< Object > getContractTypeVOsByAccountId( final String accountId ) throws KANException;
}
