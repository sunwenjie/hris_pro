package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.LaborContractTemplateDao;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.util.KANException;

public class LaborContractTemplateDaoImpl extends Context implements LaborContractTemplateDao
{

   @Override
   public int countLaborContractTemplateVOsByCondition( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return ( Integer ) select( "countLaborContractTemplateVOsByCondition", laborContractTemplateVO );
   }

   @Override
   public List< Object > getLaborContractTemplateVOsByCondition( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return selectList( "getLaborContractTemplateVOsByCondition", laborContractTemplateVO );
   }

   @Override
   public List< Object > getLaborContractTemplateVOsByCondition( final LaborContractTemplateVO laborContractTemplateVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLaborContractTemplateVOsByCondition", laborContractTemplateVO, rowBounds );
   }

   @Override
   public LaborContractTemplateVO getLaborContractTemplateVOByLaborContractTemplateId( final String laborContractTemplateId ) throws KANException
   {
      return ( LaborContractTemplateVO ) select( "getLaborContractTemplateVOByLaborContractTemplateId", laborContractTemplateId );
   }

   @Override
   public int insertLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return insert( "insertLaborContractTemplate", laborContractTemplateVO );
   }

   @Override
   public int updateLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return update( "updateLaborContractTemplate", laborContractTemplateVO );
   }

   @Override
   public int deleteLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException
   {
      return delete( "deleteLaborContractTemplate", laborContractTemplateVO );
   }

   @Override
   public List< Object > getLaborContractTemplateVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getLaborContractTemplateVOsByAccountId", accountId );
   }
   
   @Override
   public List< Object > getLaborContractTemplateVOsByContractTypeId( final String contractTypeId ) throws KANException
   {
      return selectList( "getLaborContractTemplateVOsByContractTypeId", contractTypeId );
   }

}
