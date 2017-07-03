package com.kan.hro.dao.mybatis.impl.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingHeaderDao;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;

public class BudgetSettingHeaderDaoImpl extends Context implements BudgetSettingHeaderDao
{

   @Override
   public int countBudgetSettingHeaderVOsByCondition( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countBudgetSettingHeaderVOsByCondition", budgetSettingHeaderVO );
   }

   @Override
   public List< Object > getBudgetSettingHeaderVOsByCondition( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return selectList( "getBudgetSettingHeaderVOsByCondition", budgetSettingHeaderVO );
   }

   @Override
   public List< Object > getBudgetSettingHeaderVOsByCondition( BudgetSettingHeaderVO budgetSettingHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBudgetSettingHeaderVOsByCondition", budgetSettingHeaderVO, rowBounds );
   }

   @Override
   public BudgetSettingHeaderVO getBudgetSettingHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( BudgetSettingHeaderVO ) select( "getBudgetSettingHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertBudgetSettingHeader( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return insert( "insertBudgetSettingHeader", budgetSettingHeaderVO );
   }

   @Override
   public int updateBudgetSettingHeader( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return update( "updateBudgetSettingHeader", budgetSettingHeaderVO );
   }

   @Override
   public int deleteBudgetSettingHeader( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return delete( "deleteBudgetSettingHeader", budgetSettingHeaderVO );
   }

   @Override
   public List< Object > getBudgetSettingHeaderVOsByMapParameter( Map< String, Object > mapParameter ) throws KANException
   {
      return selectList( "getBudgetSettingHeaderVOsByMapParameter", mapParameter );
   }

}
