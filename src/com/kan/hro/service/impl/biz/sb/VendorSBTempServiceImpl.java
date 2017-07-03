package com.kan.hro.service.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBDetailTempDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderTempDao;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderTempService;
import com.kan.hro.service.inf.biz.sb.VendorSBTempService;

public class VendorSBTempServiceImpl extends ContextService implements VendorSBTempService
{
   private CommonBatchDao commonBatchDao;
   private SBHeaderTempDao sbHeaderTempDao;
   private SBDetailTempDao sbDetailTempDao;
   private SBHeaderTempService sbHeaderTempService;

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public SBHeaderTempDao getSbHeaderTempDao()
   {
      return sbHeaderTempDao;
   }

   public void setSbHeaderTempDao( SBHeaderTempDao sbHeaderTempDao )
   {
      this.sbHeaderTempDao = sbHeaderTempDao;
   }

   public SBDetailTempDao getSbDetailTempDao()
   {
      return sbDetailTempDao;
   }

   public void setSbDetailTempDao( SBDetailTempDao sbDetailTempDao )
   {
      this.sbDetailTempDao = sbDetailTempDao;
   }

   public SBHeaderTempService getSbHeaderTempService()
   {
      return sbHeaderTempService;
   }

   public void setSbHeaderTempService( SBHeaderTempService sbHeaderTempService )
   {
      this.sbHeaderTempService = sbHeaderTempService;
   }

   @Override
   public PagedListHolder getVendorSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final CommonBatchDao commonBatchDao = ( CommonBatchDao ) getDao();
      pagedListHolder.setHolderSize( commonBatchDao.countCommonBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( commonBatchDao.getCommonBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( commonBatchDao.getCommonBatchVOsByCondition( ( CommonBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public int updateBatch( final CommonBatchVO commonBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         int num = 0;

         // 初始化查询对象
         final SBHeaderTempVO sbHeaderTempVO = new SBHeaderTempVO();
         sbHeaderTempVO.setAccountId( commonBatchVO.getAccountId() );
         sbHeaderTempVO.setBatchId( commonBatchVO.getBatchId() );
         // 只更新新建状态SBHeaderTempVO数据
         sbHeaderTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_NEW );
         final List< Object > sbHeaderTempList = this.sbHeaderTempDao.getSBHeaderTempVOsByCondition( sbHeaderTempVO );

         if ( sbHeaderTempList != null && sbHeaderTempList.size() > 0 )
         {
            for ( Object object : sbHeaderTempList )
            {
               SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) object;
               num += sbHeaderTempService.updateSBHeaderTemp( tempSBHeaderTempVO );
            }
         }

         // 提交事务
         this.commitTransaction();
         return num;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public int rollbackBatch( final CommonBatchVO commonBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         final List< Object > sbHeaderTempList = this.sbHeaderTempDao.getSBHeaderTempVOsByBatchId( commonBatchVO.getBatchId() );

         if ( sbHeaderTempList != null )
         {
            for ( Object o : sbHeaderTempList )
            {
               final SBHeaderTempVO sbheaderTempVO = ( SBHeaderTempVO ) o;
               final String headerId = sbheaderTempVO.getHeaderId();
               this.sbHeaderTempDao.deleteSBHeaderTemp( headerId );

               final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( headerId );

               if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
               {
                  for ( Object object : sbDetailTempVOs )
                  {
                     final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) object;
                     final String detailId = sbDetailTempVO.getDetailId();
                     this.sbDetailTempDao.deleteSBDetailTemp( detailId );
                  }
               }

            }
         }

         // 删除CommonBatchVO信息
         this.commonBatchDao.deleteCommonBatch( commonBatchVO.getBatchId() );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

}
