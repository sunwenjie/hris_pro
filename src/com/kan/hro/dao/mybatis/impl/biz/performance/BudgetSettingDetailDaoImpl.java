package com.kan.hro.dao.mybatis.impl.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingDetailDao;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;

public class BudgetSettingDetailDaoImpl extends Context implements BudgetSettingDetailDao
{

   @Override
   public int countBudgetSettingDetailVOsByCondition( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return ( Integer ) select( "countBudgetSettingDetailVOsByCondition", budgetSettingDetailVO );
   }

   @Override
   public List< Object > getBudgetSettingDetailVOsByCondition( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return selectList( "getBudgetSettingDetailVOsByCondition", budgetSettingDetailVO );
   }

   @Override
   public List< Object > getBudgetSettingDetailVOsByCondition( BudgetSettingDetailVO budgetSettingDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBudgetSettingDetailVOsByCondition", budgetSettingDetailVO, rowBounds );
   }

   @Override
   public BudgetSettingDetailVO getBudgetSettingDetailVOByDetailId( String headerId ) throws KANException
   {
      return ( BudgetSettingDetailVO ) select( "getBudgetSettingDetailVOByDetailId", headerId );
   }

   @Override
   public int insertBudgetSettingDetail( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return insert( "insertBudgetSettingDetail", budgetSettingDetailVO );
   }

   @Override
   public int updateBudgetSettingDetail( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return update( "updateBudgetSettingDetail", budgetSettingDetailVO );
   }

   @Override
   public int deleteBudgetSettingDetail( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return delete( "deleteBudgetSettingDetail", budgetSettingDetailVO );
   }

   @Override
   public List< Object > getBudgetSettingDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return selectList( "getBudgetSettingDetailVOsByHeaderId", headerId );
   }

   @Override
   public BudgetSettingDetailVO matchBudgetSettingDetailVOByMapParameter( Map< String, Object > mapParameter ) throws KANException
   {
      List< Object > list = selectList( "matchBudgetSettingDetailVOByMapParameter", mapParameter );
      return ( list != null && list.size() > 0 ) ? ( ( BudgetSettingDetailVO ) list.get( 0 ) ) : null;
   }

}
