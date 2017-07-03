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
* 项目名称：HRO_V1  
* 类名称：OTImportHeaderServiceImpl  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-11-22 下午07:10:57  
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
         // 开启事务
         this.startTransaction();
         final OTImportHeaderDao otImportHeaderDao = ( OTImportHeaderDao ) getDao();
         otImportHeaderDao.deleteDetailTempRecord( ids );
         otImportHeaderDao.deleteHeaderTempRecord( ids );
         int count = otImportBatchDao.getBatchCountByHeaderId( batchId );
         if ( count == 0 )
         {
            otImportBatchDao.deleteCommonBatchById( batchId );
         }
         // 提交事务
         this.commitTransaction();
         return count;
      }
      catch ( final Exception e )
      {
         // 回滚事务
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
