package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderBillHeaderView;

public interface OrderBillHeaderViewDao
{

   public abstract List< Object > getOrderBillHeaderViewsByCondition( final OrderBillHeaderView orderBillHeaderView ) throws KANException;
   
   public abstract List< Object > getSumOrderBillHeaderViewsByCondition( final OrderBillHeaderView orderBillHeaderView ) throws KANException;
   
   public abstract List< Object > getOrderBillDetailViewsByCondition( final OrderBillHeaderView orderBillHeaderView ) throws KANException;
   
   public abstract List< Object > getOrderBillDetailViewsByConditionForExport( final OrderBillHeaderView orderBillHeaderView ) throws KANException;

}
