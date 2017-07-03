package com.kan.hro.service.impl.biz.sb;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportDetailDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportDetailVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportDetailService;

public class SBAdjustmentImportDetailServiceImpl extends ContextService implements SBAdjustmentImportDetailService
{
   private SBAdjustmentImportDetailDao sbAdjustmentImportDetailDao;

   @Override
   public PagedListHolder getSBAdjustmentImportDetailVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( sbAdjustmentImportDetailDao.countSBAdjustmentImportDetailVOsByCondition( ( SBAdjustmentImportDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( sbAdjustmentImportDetailDao.getSBAdjustmentImportDetailVOsByCondition( ( SBAdjustmentImportDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbAdjustmentImportDetailDao.getSBAdjustmentImportDetailVOsByCondition( ( SBAdjustmentImportDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   public SBAdjustmentImportDetailDao getSbAdjustmentImportDetailDao()
   {
      return sbAdjustmentImportDetailDao;
   }

   public void setSbAdjustmentImportDetailDao( SBAdjustmentImportDetailDao sbAdjustmentImportDetailDao )
   {
      this.sbAdjustmentImportDetailDao = sbAdjustmentImportDetailDao;
   }
}
