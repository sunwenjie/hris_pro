package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.BusinessContractTemplateDao;
import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.util.KANException;

public class BusinessContractTemplateDaoImpl extends Context implements BusinessContractTemplateDao
{

   @Override
   public int countBusinessContractTemplateVOsByCondition( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return ( Integer ) select( "countBusinessContractTemplateVOsByCondition", businessContractTemplateVO );
   }

   @Override
   public List< Object > getBusinessContractTemplateVOsByCondition( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return selectList( "getBusinessContractTemplateVOsByCondition", businessContractTemplateVO );
   }

   @Override
   public List< Object > getBusinessContractTemplateVOsByCondition( final BusinessContractTemplateVO businessContractTemplateVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBusinessContractTemplateVOsByCondition", businessContractTemplateVO, rowBounds );
   }

   @Override
   public BusinessContractTemplateVO getBusinessContractTemplateVOByBusinessContractTemplateId( final String businessContractTemplateId ) throws KANException
   {
      return ( BusinessContractTemplateVO ) select( "getBusinessContractTemplateVOByBusinessContractTemplateId", businessContractTemplateId );
   }

   @Override
   public int insertBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return insert( "insertBusinessContractTemplate", businessContractTemplateVO );
   }

   @Override
   public int updateBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return update( "updateBusinessContractTemplate", businessContractTemplateVO );
   }

   @Override
   public int deleteBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException
   {
      return delete( "deleteBusinessContractTemplate", businessContractTemplateVO );
   }

   @Override
   public List< Object > getBusinessContractTemplateVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getBusinessContractTemplateVOsByAccountId", accountId );
   }

}
