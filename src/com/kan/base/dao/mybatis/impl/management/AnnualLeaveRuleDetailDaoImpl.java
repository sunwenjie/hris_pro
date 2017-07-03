package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.AnnualLeaveRuleDetailDao;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.util.KANException;

public class AnnualLeaveRuleDetailDaoImpl extends Context implements AnnualLeaveRuleDetailDao
{

   @Override
   public int countAnnualLeaveRuleDetailVOsByCondition( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return ( Integer ) select( "countAnnualLeaveRuleDetailVOsByCondition", annualLeaveRuleDetailVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleDetailVOsByCondition( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return selectList( "getAnnualLeaveRuleDetailVOsByCondition", annualLeaveRuleDetailVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleDetailVOsByCondition( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAnnualLeaveRuleDetailVOsByCondition", annualLeaveRuleDetailVO, rowBounds );
   }

   @Override
   public AnnualLeaveRuleDetailVO getAnnualLeaveRuleDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( AnnualLeaveRuleDetailVO ) select( "getAnnualLeaveRuleDetailVOByDetailId", detailId );
   }

   @Override
   public int insertAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return insert( "insertAnnualLeaveRuleDetail", annualLeaveRuleDetailVO );
   }

   @Override
   public int updateAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return update( "updateAnnualLeaveRuleDetail", annualLeaveRuleDetailVO );
   }

   @Override
   public int deleteAnnualLeaveRuleDetail( final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return delete( "deleteAnnualLeaveRuleDetail", annualLeaveRuleDetailVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getAnnualLeaveRuleDetailVOsByHeaderId", headerId );
   }

}
