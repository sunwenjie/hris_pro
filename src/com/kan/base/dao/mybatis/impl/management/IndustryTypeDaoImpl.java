package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.IndustryTypeDao;
import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.util.KANException;

public class IndustryTypeDaoImpl extends Context implements IndustryTypeDao
{

   @Override
   public int countIndustryTypeVOsByCondition( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return ( Integer ) select( "countIndustryTypeVOsByCondition", industryTypeVO );
   }

   @Override
   public List< Object > getIndustryTypeVOsByCondition( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return selectList( "getIndustryTypeVOsByCondition", industryTypeVO );
   }

   @Override
   public List< Object > getIndustryTypeVOsByCondition( final IndustryTypeVO industryTypeVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getIndustryTypeVOsByCondition", industryTypeVO, rowBounds );
   }

   @Override
   public IndustryTypeVO getIndustryTypeVOByIndustryTypeId( final String industryTypeId ) throws KANException
   {
      return ( IndustryTypeVO ) select( "getIndustryTypeVOByIndustryTypeId", industryTypeId );
   }

   @Override
   public int insertIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return insert( "insertIndustryType", industryTypeVO );
   }

   @Override
   public int updateIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return update( "updateIndustryType", industryTypeVO );
   }

   @Override
   public int deleteIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException
   {
      return delete( "deleteIndustryType", industryTypeVO );
   }

   @Override
   public List< Object > getIndustryTypeVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getIndustryTypeVOsByAccountId", accountId );
   }

}
