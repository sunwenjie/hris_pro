package com.kan.hro.service.impl.biz.settlement;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportDetailDao;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportDetailVO;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportDetailService;

public class SettlementAdjustmentImportDetailServiceImpl extends ContextService implements SettlementAdjustmentImportDetailService
{
   private SettlementAdjustmentImportDetailDao settlementAdjustmentImportDetailDao;

   @Override
   public PagedListHolder getSettlementAdjustmentImportDetailVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( settlementAdjustmentImportDetailDao.countSettlementAdjustmentImportDetailVOsByCondition( ( SettlementAdjustmentImportDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( settlementAdjustmentImportDetailDao.getSettlementAdjustmentImportDetailVOsByCondition( ( SettlementAdjustmentImportDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( settlementAdjustmentImportDetailDao.getSettlementAdjustmentImportDetailVOsByCondition( ( SettlementAdjustmentImportDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   public SettlementAdjustmentImportDetailDao getSettlementAdjustmentImportDetailDao()
   {
      return settlementAdjustmentImportDetailDao;
   }

   public void setSettlementAdjustmentImportDetailDao( SettlementAdjustmentImportDetailDao settlementAdjustmentImportDetailDao )
   {
      this.settlementAdjustmentImportDetailDao = settlementAdjustmentImportDetailDao;
   }
}
