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
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�TimesheetHeaderServiceImpl  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-11-22 ����07:10:57  
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
         // ��������
         this.startTransaction();
         row = ( ( TimesheetAllowanceHeaderDao ) getDao() ).deleteAllowanceTempByAllowanceIds( allowanceIds );

         if ( StringUtils.isNotBlank( batchId ) )
         {
            //ɾ��û��detail���ݵ�header��¼
            ( ( TimesheetAllowanceHeaderDao ) getDao() ).deleteEmptyAllowanceHeaderTempBybatchId( KANUtil.decodeStringFromAjax( batchId ) );

            count = ( ( TimesheetAllowanceHeaderDao ) getDao() ).getHeaderCountByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
            if ( count == 0 )
            {
               otImportBatchDao.deleteCommonBatchById( batchId );
            }
         }
         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return new int[] { row, count };
   }
}
