package com.kan.hro.service.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentDetailService;

public class SBAdjustmentDetailServiceImpl extends ContextService implements SBAdjustmentDetailService
{

   // 注入SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;

   public SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   @Override
   public PagedListHolder getSBAdjustmentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBAdjustmentDetailDao sbAdjustmentDetailDao = ( SBAdjustmentDetailDao ) getDao();
      pagedListHolder.setHolderSize( sbAdjustmentDetailDao.countSBAdjustmentDetailVOsByCondition( ( SBAdjustmentDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbAdjustmentDetailDao.getSBAdjustmentDetailVOsByCondition( ( SBAdjustmentDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbAdjustmentDetailDao.getSBAdjustmentDetailVOsByCondition( ( SBAdjustmentDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SBAdjustmentDetailVO getSBAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException
   {
      return ( ( SBAdjustmentDetailDao ) getDao() ).getSBAdjustmentDetailVOByAdjustmentDetailId( adjustmentDetailId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public int updateSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = this.getSbAdjustmentHeaderDao().getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );

         // 修改从表
         ( ( SBAdjustmentDetailDao ) getDao() ).updateSBAdjustmentDetail( sbAdjustmentDetailVO );

         // 重新计算主表合计
         reCalculateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 修改主表
         this.getSbAdjustmentHeaderDao().updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务 
         rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public int insertSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = this.getSbAdjustmentHeaderDao().getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );

         // 插入从表
         sbAdjustmentDetailVO.setMonthly( sbAdjustmentHeaderVO.getMonthly() );
         ( ( SBAdjustmentDetailDao ) getDao() ).insertSBAdjustmentDetail( sbAdjustmentDetailVO );

         // 重新计算主表合计
         reCalculateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 修改主表
         this.getSbAdjustmentHeaderDao().updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务 
         rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public int deleteSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = this.getSbAdjustmentHeaderDao().getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );

         // 标记删除从表
         sbAdjustmentDetailVO.setDeleted( SBAdjustmentDetailVO.FALSE );
         ( ( SBAdjustmentDetailDao ) getDao() ).updateSBAdjustmentDetail( sbAdjustmentDetailVO );

         // 重新计算主表合计
         reCalculateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 修改主表
         this.getSbAdjustmentHeaderDao().updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务 
         rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public List< Object > getSBAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return ( ( SBAdjustmentDetailDao ) getDao() ).getSBAdjustmentDetailVOsByAdjustmentHeaderId( adjustmentHeaderId );
   }

   // 重新计算Adjustment Header的合计值
   // Reviewed by Kevin Jin at 2013-12-06
   private void reCalculateSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      double headerAmountCompany = 0.0;
      double headerAmountPersonal = 0.0;

      // 初始化SBAdjustmentDetailVO列表
      final List< Object > sbAdjustmentDetaiVOs = ( ( SBAdjustmentDetailDao ) getDao() ).getSBAdjustmentDetailVOsByAdjustmentHeaderId( sbAdjustmentHeaderVO.getAdjustmentHeaderId() );

      if ( sbAdjustmentDetaiVOs != null && sbAdjustmentDetaiVOs.size() > 0 )
      {
         for ( Object sbAdjustmentDetaiVOObject : sbAdjustmentDetaiVOs )
         {
            // 初始化SBAdjustmentDetailVO
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) sbAdjustmentDetaiVOObject;

            if ( sbAdjustmentDetailVO.getDeleted() != null && sbAdjustmentDetailVO.getDeleted().equals( "1" ) && sbAdjustmentDetailVO.getStatus() != null
                  && sbAdjustmentDetailVO.getStatus().equals( "1" ) )
            {
               headerAmountCompany += Double.parseDouble( KANUtil.filterEmpty( sbAdjustmentDetailVO.getAmountCompany() ) != null ? sbAdjustmentDetailVO.getAmountCompany() : "0" );
               headerAmountPersonal += Double.parseDouble( KANUtil.filterEmpty( sbAdjustmentDetailVO.getAmountPersonal() ) != null ? sbAdjustmentDetailVO.getAmountPersonal() : "0" );
            }
         }
      }

      sbAdjustmentHeaderVO.setAmountCompany( String.valueOf( headerAmountCompany ) );
      sbAdjustmentHeaderVO.setAmountPersonal( String.valueOf( headerAmountPersonal ) );
   }

}
