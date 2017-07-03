package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ContractTypeDao;
import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.util.KANException;

public class ContractTypeDaoImpl extends Context implements ContractTypeDao
{

   @Override
   public int countContractTypeVOsByCondition( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return ( Integer ) select( "countContractTypeVOsByCondition", contractTypeVO );
   }

   @Override
   public List< Object > getContractTypeVOsByCondition( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return selectList( "getContractTypeVOsByCondition", contractTypeVO );
   }

   @Override
   public List< Object > getContractTypeVOsByCondition( final ContractTypeVO contractTypeVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getContractTypeVOsByCondition", contractTypeVO, rowBounds );
   }

   @Override
   public ContractTypeVO getContractTypeVOByTypeId( final String typeId ) throws KANException
   {
      return ( ContractTypeVO ) select( "getContractTypeVOByTypeId", typeId );
   }

   @Override
   public int insertContractType( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return insert( "insertContractType", contractTypeVO );
   }

   @Override
   public int updateContractType( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return update( "updateContractType", contractTypeVO );
   }

   @Override
   public int deleteContractType( final ContractTypeVO contractTypeVO ) throws KANException
   {
      return delete( "deleteContractType", contractTypeVO );
   }

   @Override
   public List< Object > getContractTypeVOsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getContractTypeVOsByAccountId", accountId );
   }

}
