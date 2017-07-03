package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTImportBatchDao;
import com.kan.hro.dao.inf.biz.attendance.OTImportHeaderDao;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;
import com.kan.hro.service.inf.biz.attendance.OTImportHeaderService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�OTImportHeaderServiceImpl  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-11-22 ����07:10:57  
*   
*/
public class OTImportHeaderServiceImpl extends ContextService implements OTImportHeaderService
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
   public PagedListHolder getOTImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OTImportHeaderDao otImportHeaderDao = ( OTImportHeaderDao ) getDao();
      pagedListHolder.setHolderSize( otImportHeaderDao.countOTImportHeaderVOsByCondition( ( OTImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( otImportHeaderDao.getOTImportHeaderVOsByCondition( ( OTImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( otImportHeaderDao.getOTImportHeaderVOsByCondition( ( OTImportHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public void updateOTImportBase( final String allowanceId, final String base ) throws KANException
   {
      //      final OTImportHeaderDao otImportHeaderDao = ( OTImportHeaderDao ) getDao();
      //		otImportHeaderDao.updateAllowanceBase(allowanceId,base);
   }

   @Override
   public int backUpRecord( final String[] ids, final String batchId ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();
         final OTImportHeaderDao otImportHeaderDao = ( OTImportHeaderDao ) getDao();
         otImportHeaderDao.deleteDetailTempRecord( ids );
         otImportHeaderDao.deleteHeaderTempRecord( ids );
         int count = otImportBatchDao.getBatchCountByHeaderId( batchId );
         if ( count == 0 )
         {
            otImportBatchDao.deleteCommonBatchById( batchId );
         }
         // �ύ����
         this.commitTransaction();
         return count;
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public List< Object > getOTImportHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return ( ( OTImportHeaderDao ) getDao() ).getOTImportHeaderVOsByBatchId( batchId );
   }
}
