package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.BusinessTypeDao;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.util.KANException;

public class BusinessTypeDaoImpl extends Context implements BusinessTypeDao
{

   @Override
   public int countBusinessTypeVOsByCondition( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return ( Integer ) select( "countBusinessTypeVOsByCondition", businessTypeVO );
   }

   @Override
   public List< Object > getBusinessTypeVOsByCondition( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return selectList( "getBusinessTypeVOsByCondition", businessTypeVO );
   }

   @Override
   public List< Object > getBusinessTypeVOsByCondition( final BusinessTypeVO businessTypeVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBusinessTypeVOsByCondition", businessTypeVO, rowBounds );
   }

   @Override
   public BusinessTypeVO getBusinessTypeVOByBusinessTypeId( final String businessTypeId ) throws KANException
   {
      return ( BusinessTypeVO ) select( "getBusinessTypeVOByBusinessTypeId", businessTypeId );
   }

   @Override
   public int insertBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return insert( "insertBusinessType", businessTypeVO );
   }

   @Override
   public int updateBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return update( "updateBusinessType", businessTypeVO );
   }

   @Override
   public int deleteBusinessType( final BusinessTypeVO businessTypeVO ) throws KANException
   {
      return delete( "deleteBusinessType", businessTypeVO );
   }

   @Override
   public List< Object > getBusinessTypeVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getBusinessTypeVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getBusinessTypeBaseViews( final String accountId ) throws KANException
   {
      return selectList( "getBusinessTypeBaseViews", accountId );
   }

}
