package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderDao;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.settlement.AdjustmentDetailService;

public class AdjustmentDetailServiceImpl extends ContextService implements AdjustmentDetailService
{

   // 注入结算 - 账单调整（主）DAO
   private AdjustmentHeaderDao adjustmentHeaderDao;

   public AdjustmentHeaderDao getAdjustmentHeaderDao()
   {
      return adjustmentHeaderDao;
   }

   public void setAdjustmentHeaderDao( AdjustmentHeaderDao adjustmentHeaderDao )
   {
      this.adjustmentHeaderDao = adjustmentHeaderDao;
   }

   @Override
   public PagedListHolder getAdjustmentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final AdjustmentDetailDao adjustmentDetailDao = ( AdjustmentDetailDao ) getDao();
      pagedListHolder.setHolderSize( adjustmentDetailDao.countAdjustmentDetailVOsByCondition( ( AdjustmentDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( adjustmentDetailDao.getAdjustmentDetailVOsByCondition( ( AdjustmentDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( adjustmentDetailDao.getAdjustmentDetailVOsByCondition( ( AdjustmentDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public AdjustmentDetailVO getAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException
   {
      return ( ( AdjustmentDetailDao ) getDao() ).getAdjustmentDetailVOByAdjustmentDetailId( adjustmentDetailId );
   }

   @Override
   public int updateAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 修改从表
         ( ( AdjustmentDetailDao ) getDao() ).updateAdjustmentDetail( adjustmentDetailVO );

         // 修改主表
         this.adjustmentHeaderDao.updateAdjustmentHeader( updateAdjustmentHeader( adjustmentDetailVO ) );

         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务 
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int insertAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 获取ItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( adjustmentDetailVO.getAccountId() ).getItemVOByItemId( adjustmentDetailVO.getItemId() );
         adjustmentDetailVO.setNameZH( itemVO.getNameZH() );
         adjustmentDetailVO.setNameEN( itemVO.getNameEN() );

         ( ( AdjustmentDetailDao ) getDao() ).insertAdjustmentDetail( adjustmentDetailVO );

         // 修改主表
         this.adjustmentHeaderDao.updateAdjustmentHeader( updateAdjustmentHeader( adjustmentDetailVO ) );
         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务 
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int deleteAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      // 标记删除
      adjustmentDetailVO.setDeleted( AdjustmentDetailVO.FALSE );
      return ( ( AdjustmentDetailDao ) getDao() ).updateAdjustmentDetail( adjustmentDetailVO );
   }

   private AdjustmentHeaderVO updateAdjustmentHeader( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      final AdjustmentHeaderVO adjustmentHeaderVO = this.adjustmentHeaderDao.getAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentDetailVO.getAdjustmentHeaderId() );
      final AdjustmentDetailVO tempDetailVO = new AdjustmentDetailVO();
      double tempHeaderBillAmountPersonal = 0.0;
      double tempHeaderBillAmountCompany = 0.0;
      double tempHeaderCostAmountPersonal = 0.0;
      double tempHeaderCostAmountCompany = 0.0;
      tempDetailVO.setAdjustmentHeaderId( adjustmentDetailVO.getAdjustmentHeaderId() );
      final List< Object > adjustmentDetaiVOs = ( ( AdjustmentDetailDao ) getDao() ).getAdjustmentDetailVOsByCondition( tempDetailVO );
      if ( adjustmentDetaiVOs != null && adjustmentDetaiVOs.size() > 0 )
      {
         for ( Object adjustmentDetaiVOObject : adjustmentDetaiVOs )
         {
            final AdjustmentDetailVO temp = ( AdjustmentDetailVO ) adjustmentDetaiVOObject;
            tempHeaderBillAmountPersonal += Double.parseDouble( temp.getBillAmountPersonal() );
            tempHeaderBillAmountCompany += Double.parseDouble( temp.getBillAmountCompany() );
            tempHeaderCostAmountPersonal += Double.parseDouble( temp.getCostAmountPersonal() );
            tempHeaderCostAmountCompany += Double.parseDouble( temp.getCostAmountCompany() );
         }
      }
      adjustmentHeaderVO.setBillAmountPersonal( tempHeaderBillAmountPersonal + "" );
      adjustmentHeaderVO.setBillAmountCompany( tempHeaderBillAmountCompany + "" );
      adjustmentHeaderVO.setCostAmountPersonal( tempHeaderCostAmountPersonal + "" );
      adjustmentHeaderVO.setCostAmountCompany( tempHeaderCostAmountCompany + "" );
      return adjustmentHeaderVO;
   }
}
