package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.OTImportBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceHeaderDao;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceHeaderService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：TimesheetHeaderServiceImpl  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-11-22 下午07:10:57  
*   
*/
public class TimesheetAllowanceHeaderServiceImpl extends ContextService implements TimesheetAllowanceHeaderService
{

   private OTImportBatchDao otImportBatchDao;

   public OTImportBatchDao getOtImportBatchDao()
   {
      return otImportBatchDao;
   }

   public void setOtImportBatchDao( OTImportBatchDao otImportBatchDao )
   {
      this.otImportBatchDao = otImportBatchDao;
   }

   @Override
   public PagedListHolder getTimesheetHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TimesheetAllowanceHeaderDao timesheetHeaderDao = ( TimesheetAllowanceHeaderDao ) getDao();
      pagedListHolder.setHolderSize( timesheetHeaderDao.countTimesheetHeaderVOsByCondition( ( TimesheetHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetHeaderDao.getTimesheetHeaderVOsByCondition( ( TimesheetHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetHeaderDao.getTimesheetHeaderVOsByCondition( ( TimesheetHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public void updateAllowanceBase( String allowanceId, String base ) throws KANException
   {
      final TimesheetAllowanceHeaderDao timesheetHeaderDao = ( TimesheetAllowanceHeaderDao ) getDao();
      timesheetHeaderDao.updateAllowanceBase( allowanceId, base );
   }

   @Override
   public int[] backTimeSheetAllowanceTemp( String[] allowanceIdsArray, String batchId ) throws KANException
   {
      List< String > allowanceIds = new ArrayList< String >();
      for ( String allowanceId : allowanceIdsArray )
      {
         allowanceIds.add( KANUtil.decodeStringFromAjax( allowanceId ) );
      }
      int row = 0;
      int count = 0;
      try
      {
         // 开启事务
         this.startTransaction();
         row = ( ( TimesheetAllowanceHeaderDao ) getDao() ).deleteAllowanceTempByAllowanceIds( allowanceIds );

         if ( StringUtils.isNotBlank( batchId ) )
         {
            //删除没有detail数据的header记录
            ( ( TimesheetAllowanceHeaderDao ) getDao() ).deleteEmptyAllowanceHeaderTempBybatchId( KANUtil.decodeStringFromAjax( batchId ) );

            count = ( ( TimesheetAllowanceHeaderDao ) getDao() ).getHeaderCountByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
            if ( count == 0 )
            {
               otImportBatchDao.deleteCommonBatchById( batchId );
            }
         }
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return new int[] { row, count };
   }
}
