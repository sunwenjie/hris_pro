package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.util.KANException;

public interface ContractTypeDao
{
   public abstract int countContractTypeVOsByCondition( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract List< Object > getContractTypeVOsByCondition( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract List< Object > getContractTypeVOsByCondition( final ContractTypeVO contractTypeVO, RowBounds rowBounds ) throws KANException;

   public abstract ContractTypeVO getContractTypeVOByTypeId( final String typeId ) throws KANException;

   public abstract int insertContractType( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract int updateContractType( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract int deleteContractType( final ContractTypeVO contractTypeVO ) throws KANException;

   public abstract List< Object > getContractTypeVOsByAccountId( final String accountId ) throws KANException;
}
