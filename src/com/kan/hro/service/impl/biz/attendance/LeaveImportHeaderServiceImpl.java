package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.LeaveImportBatchDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveImportHeaderDao;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.service.inf.biz.attendance.LeaveImportHeaderService;

/**
 * 
 * ��Ŀ���ƣ�HRO_V1 �����ƣ�LeaveImportHeaderServiceImpl
 * 
 * 
 */

public class LeaveImportHeaderServiceImpl extends ContextService implements LeaveImportHeaderService
{

   private LeaveImportBatchDao leaveImportBatchDao;

   public LeaveImportBatchDao getLeaveImportBatchDao()
   {
      return leaveImportBatchDao;
   }

   public void setLeaveImportBatchDao( LeaveImportBatchDao leaveImportBatchDao )
   {
      this.leaveImportBatchDao = leaveImportBatchDao;
   }

   @Override
   public PagedListHolder getLeaveImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final LeaveImportHeaderDao leaveImportHeaderDao = ( LeaveImportHeaderDao ) getDao();
      pagedListHolder.setHolderSize( leaveImportHeaderDao.countLeaveImportHeaderVOsByCondition( ( LeaveImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( leaveImportHeaderDao.getLeaveImportHeaderVOsByCondition( ( LeaveImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( leaveImportHeaderDao.getLeaveImportHeaderVOsByCondition( ( LeaveImportHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public void updateLeaveImportBase( String allowanceId, String base ) throws KANException
   {
      // final LeaveImportHeaderDao leaveImportHeaderDao = (
      // LeaveImportHeaderDao ) getDao();
      // leaveImportHeaderDao.updateAllowanceBase(allowanceId,base);
   }

   @Override
   public int backHeader( LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException
   {
      int i = 1;
      try
      {
         // ��������
         final String[] selectedIds = leaveImportHeaderVO.getSelectedIds().split( "," );
         List< String > selectedIdArray = new ArrayList< String >();
         // ����selectedIds �����޸�
         for ( String encodedSelectId : selectedIds )
         {
            selectedIdArray.add( KANUtil.decodeStringFromAjax( encodedSelectId ) );
         }

         this.startTransaction();

         ( ( LeaveImportHeaderDao ) getDao() ).deleteLeaveImportDetailTempByHeaderIds( selectedIdArray );
         ( ( LeaveImportHeaderDao ) getDao() ).deleteLeaveImportHeaderTempByHeaderIds( selectedIdArray );

         i = ( ( LeaveImportHeaderDao ) getDao() ).countLeaveImportHeaderVOsByBatchId( leaveImportHeaderVO );
         if ( i == 0 )
         {
            this.leaveImportBatchDao.deleteCommonBatchById( leaveImportHeaderVO.getBatchId() );
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

      return i;
   }

   @Override
   public List< Object > getLeaveImportHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return ( ( LeaveImportHeaderDao ) getDao() ).getLeaveImportHeaderVOsByBatchId( batchId );
   }
}
