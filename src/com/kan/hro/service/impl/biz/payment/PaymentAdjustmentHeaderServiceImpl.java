package com.kan.hro.service.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.domain.biz.payment.EMPPaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentHeaderService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class PaymentAdjustmentHeaderServiceImpl extends ContextService implements PaymentAdjustmentHeaderService
{
   // 注入PaymentAdjustmentHeaderDao
   private PaymentAdjustmentDetailDao paymentAdjustmentDetailDao;

   // 注入ServiceContractService
   private ServiceContractService serviceContractService;

   public final PaymentAdjustmentDetailDao getPaymentAdjustmentDetailDao()
   {
      return paymentAdjustmentDetailDao;
   }

   public final void setPaymentAdjustmentDetailDao( PaymentAdjustmentDetailDao paymentAdjustmentDetailDao )
   {
      this.paymentAdjustmentDetailDao = paymentAdjustmentDetailDao;
   }

   public final ServiceContractService getServiceContractService()
   {
      return serviceContractService;
   }

   public final void setServiceContractService( ServiceContractService serviceContractService )
   {
      this.serviceContractService = serviceContractService;
   }

   @Override
   public PagedListHolder getPaymentAdjustmentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao = ( PaymentAdjustmentHeaderDao ) getDao();
      pagedListHolder.setHolderSize( paymentAdjustmentHeaderDao.countPaymentAdjustmentHeaderVOsByCondition( ( PaymentAdjustmentHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( paymentAdjustmentHeaderDao.getPaymentAdjustmentHeaderVOsByCondition( ( PaymentAdjustmentHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentAdjustmentHeaderDao.getPaymentAdjustmentHeaderVOsByCondition( ( PaymentAdjustmentHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PaymentAdjustmentHeaderVO getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return ( ( PaymentAdjustmentHeaderDao ) getDao() ).getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
   }

   @Override
   public int updatePaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      int rows = 0;

      try
      {
         startTransaction();

         // 初始化PaymentAdjustmentDTO
         final PaymentAdjustmentDTO paymentAdjustmentDTO = new PaymentAdjustmentDTO();

         // 设置PaymentAdjustmentHeaderVO
         paymentAdjustmentDTO.setPaymentAdjustmentHeaderVO( paymentAdjustmentHeaderVO );

         if ( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getBillAmountPersonal() ) != null && Double.valueOf( paymentAdjustmentHeaderVO.getBillAmountPersonal() ) > 0
               && KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getCostAmountPersonal() ) != null && Double.valueOf( paymentAdjustmentHeaderVO.getCostAmountPersonal() ) > 0 )
         {
            // 获取PaymentAdjustmentDetailVO列表
            final List< Object > paymentAdjustmentDetailVOs = this.getPaymentAdjustmentDetailDao().getPaymentAdjustmentDetailVOsByAdjustmentHeaderId( paymentAdjustmentHeaderVO.getAdjustmentHeaderId() );

            if ( paymentAdjustmentDetailVOs != null && paymentAdjustmentDetailVOs.size() > 0 )
            {
               for ( Object paymentAdjustmentDetailVOObject : paymentAdjustmentDetailVOs )
               {
                  paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().add( ( PaymentAdjustmentDetailVO ) paymentAdjustmentDetailVOObject );
               }
            }

            // 初始化EMPPaymentDTO
            final ServiceContractVO serviceContractVO = new ServiceContractVO();
            serviceContractVO.setAccountId( paymentAdjustmentHeaderVO.getAccountId() );
            serviceContractVO.setEmployeeId( paymentAdjustmentHeaderVO.getEmployeeId() );
            serviceContractVO.setEmployeeContractId( paymentAdjustmentHeaderVO.getContractId() );
            serviceContractVO.setMonthly( paymentAdjustmentHeaderVO.getMonthly() );
            final EMPPaymentDTO empPaymentDTO = this.getServiceContractService().getEMPPaymentDTOByCondition( serviceContractVO, null );

            if ( empPaymentDTO != null )
            {
               // 添加待审批的PaymentAdjustmentDTO
               empPaymentDTO.getPaymentAdjustmentDTOs().add( paymentAdjustmentDTO );

               // 计算薪资调整
               empPaymentDTO.calculatePaymentAdjustment( paymentAdjustmentHeaderVO );
            }
         }

         // 更新主表数据
         rows = ( ( PaymentAdjustmentHeaderDao ) getDao() ).updatePaymentAdjustmentHeader( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() );

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   public int insertPaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      return ( ( PaymentAdjustmentHeaderDao ) getDao() ).insertPaymentAdjustmentHeader( paymentAdjustmentHeaderVO );
   }

   @Override
   public int deletePaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      paymentAdjustmentHeaderVO.setDeleted( PaymentAdjustmentHeaderVO.FALSE );
      return ( ( PaymentAdjustmentHeaderDao ) getDao() ).updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );
   }

}
