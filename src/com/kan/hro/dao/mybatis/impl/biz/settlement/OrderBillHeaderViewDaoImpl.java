package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderBillHeaderViewDao;
import com.kan.hro.domain.biz.settlement.OrderBillHeaderView;

public class OrderBillHeaderViewDaoImpl extends Context implements OrderBillHeaderViewDao
{

   @Override
   public List< Object > getOrderBillHeaderViewsByCondition( OrderBillHeaderView orderBillHeaderView ) throws KANException
   {
      return selectList( "getOrderBillHeaderViewsByCondition", orderBillHeaderView );
   }

   @Override
   public List< Object > getOrderBillDetailViewsByCondition( OrderBillHeaderView orderBillHeaderView ) throws KANException
   {
      return selectList( "getOrderBillDetailViewsByCondition", orderBillHeaderView );
   }

   @Override
   public List< Object > getOrderBillDetailViewsByConditionForExport( OrderBillHeaderView orderBillHeaderView ) throws KANException
   {
      return selectList( "getOrderBillDetailViewsByConditionForExport", orderBillHeaderView );
   }

   @Override
   public List< Object > getSumOrderBillHeaderViewsByCondition( OrderBillHeaderView orderBillHeaderView ) throws KANException
   {
      return selectList( "getSumOrderBillHeaderViewsByCondition", orderBillHeaderView );
   }

}
