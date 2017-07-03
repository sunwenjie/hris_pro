package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.AnnualLeaveRuleHeaderDao;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.util.KANException;

public class AnnualLeaveRuleHeaderDaoImpl extends Context implements AnnualLeaveRuleHeaderDao
{

   @Override
   public int countAnnualLeaveRuleHeaderVOsByCondition( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countAnnualLeaveRuleHeaderVOsByCondition", annualLeaveRuleHeaderVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleHeaderVOsByCondition( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException
   {
      return selectList( "getAnnualLeaveRuleHeaderVOsByCondition", annualLeaveRuleHeaderVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleHeaderVOsByCondition( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAnnualLeaveRuleHeaderVOsByCondition", annualLeaveRuleHeaderVO, rowBounds );
   }

   @Override
   public AnnualLeaveRuleHeaderVO getAnnualLeaveRuleHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( AnnualLeaveRuleHeaderVO ) select( "getAnnualLeaveRuleHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException
   {
      return insert( "insertAnnualLeaveRuleHeader", annualLeaveRuleHeaderVO );
   }

   @Override
   public int updateAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException
   {
      return update( "updateAnnualLeaveRuleHeader", annualLeaveRuleHeaderVO );
   }

   @Override
   public int deleteAnnualLeaveRuleHeader( final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO ) throws KANException
   {
      return delete( "deleteAnnualLeaveRuleHeader", annualLeaveRuleHeaderVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getAnnualLeaveRuleHeaderVOsByAccountId", accountId );
   }

}
