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
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentDetailService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class PaymentAdjustmentDetailServiceImpl extends ContextService implements PaymentAdjustmentDetailService
{
   // ע��PaymentAdjustmentHeaderDao
   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   // ע��ServiceContractService
   private ServiceContractService serviceContractService;

   public PaymentAdjustmentHeaderDao getPaymentAdjustmentHeaderDao()
   {
      return paymentAdjustmentHeaderDao;
   }

   public void setPaymentAdjustmentHeaderDao( PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao )
   {
      this.paymentAdjustmentHeaderDao = paymentAdjustmentHeaderDao;
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
   public PagedListHolder getPaymentAdjustmentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentAdjustmentDetailDao paymentAdjustmentDetailDao = ( PaymentAdjustmentDetailDao ) getDao();
      pagedListHolder.setHolderSize( paymentAdjustmentDetailDao.countPaymentAdjustmentDetailVOsByCondition( ( PaymentAdjustmentDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( paymentAdjustmentDetailDao.getPaymentAdjustmentDetailVOsByCondition( ( PaymentAdjustmentDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentAdjustmentDetailDao.getPaymentAdjustmentDetailVOsByCondition( ( PaymentAdjustmentDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public List< Object > getPaymentAdjustmentDetailVOsByCondition( PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      return null;
   }

   @Override
   public PaymentAdjustmentDetailVO getPaymentAdjustmentDetailVOByAdjustmentDetailId( final String AdjustmentDetailId ) throws KANException
   {
      return ( ( PaymentAdjustmentDetailDao ) getDao() ).getPaymentAdjustmentDetailVOByAdjustmentDetailId( AdjustmentDetailId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-31
   public int insertPaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      int rows = 0;

      try
      {
         startTransaction();

         // ��ʼ�� PaymentAdjustmentHeaderVO
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = this.getPaymentAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );

         // ��ʼ��EMPPaymentDTO
         final ServiceContractVO serviceContractVO = new ServiceContractVO();
         serviceContractVO.setAccountId( paymentAdjustmentHeaderVO.getAccountId() );
         serviceContractVO.setCorpId( paymentAdjustmentDetailVO.getCorpId() );
         serviceContractVO.setEmployeeId( paymentAdjustmentHeaderVO.getEmployeeId() );
         serviceContractVO.setEmployeeContractId( paymentAdjustmentHeaderVO.getContractId() );
         serviceContractVO.setMonthly( paymentAdjustmentHeaderVO.getMonthly() );
         final EMPPaymentDTO empPaymentDTO = this.getServiceContractService().getEMPPaymentDTOByCondition( serviceContractVO, "1" );

         if ( empPaymentDTO != null )
         {
            // ��ʼ��PaymentAdjustmentDTO
            final PaymentAdjustmentDTO paymentAdjustmentDTO = empPaymentDTO.getPaymentAdjustmentDTOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );
            paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().add( paymentAdjustmentDetailVO );

            // ����н�ʵ���
            empPaymentDTO.calculatePaymentAdjustment( paymentAdjustmentHeaderVO );

            if ( paymentAdjustmentDTO != null )
            {
               // ������������
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setModifyBy( paymentAdjustmentDetailVO.getModifyBy() );
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setModifyDate( paymentAdjustmentDetailVO.getModifyDate() );
               this.getPaymentAdjustmentHeaderDao().updatePaymentAdjustmentHeader( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() );

               // ����ӱ�����
               if ( paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().size() > 0 )
               {
                  for ( PaymentAdjustmentDetailVO tempPaymentAdjustmentDetailVO : paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() )
                  {
                     if ( KANUtil.filterEmpty( tempPaymentAdjustmentDetailVO.getAdjustmentDetailId() ) == null )
                     {
                        rows = rows + ( ( PaymentAdjustmentDetailDao ) getDao() ).insertPaymentAdjustmentDetail( tempPaymentAdjustmentDetailVO );
                     }
                  }
               }
            }
         }

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
   // Reviewed by Kevin Jin at 2013-12-31
   public int updatePaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      int rows = 0;

      try
      {
         startTransaction();

         // ��ʼ�� PaymentAdjustmentHeaderVO
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = this.getPaymentAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );

         // ��ʼ��EMPPaymentDTO
         final ServiceContractVO serviceContractVO = new ServiceContractVO();
         serviceContractVO.setAccountId( paymentAdjustmentHeaderVO.getAccountId() );
         serviceContractVO.setCorpId( paymentAdjustmentHeaderVO.getCorpId() );
         serviceContractVO.setEmployeeId( paymentAdjustmentHeaderVO.getEmployeeId() );
         serviceContractVO.setEmployeeContractId( paymentAdjustmentHeaderVO.getContractId() );
         serviceContractVO.setMonthly( paymentAdjustmentHeaderVO.getMonthly() );
         final EMPPaymentDTO empPaymentDTO = this.getServiceContractService().getEMPPaymentDTOByCondition( serviceContractVO, "1" );

         if ( empPaymentDTO != null )
         {
            // ��ʼ��PaymentAdjustmentDTO
            final PaymentAdjustmentDTO paymentAdjustmentDTO = empPaymentDTO.getPaymentAdjustmentDTOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );

            // �������滻PaymentAdjustmentDetailVO
            if ( paymentAdjustmentDTO != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().size() > 0 )
            {
               for ( PaymentAdjustmentDetailVO tempPaymentAdjustmentDetailVO : paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() )
               {
                  if ( tempPaymentAdjustmentDetailVO.getAdjustmentDetailId().equals( paymentAdjustmentDetailVO.getAdjustmentDetailId() ) )
                  {
                     tempPaymentAdjustmentDetailVO.update( paymentAdjustmentDetailVO );
                  }
               }
            }

            // ����н�ʵ���
            empPaymentDTO.calculatePaymentAdjustment( paymentAdjustmentHeaderVO );

            // ���´ӱ�����
            rows = ( ( PaymentAdjustmentDetailDao ) getDao() ).updatePaymentAdjustmentDetail( paymentAdjustmentDetailVO );

            if ( paymentAdjustmentDTO != null )
            {
               // ������������
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setModifyBy( paymentAdjustmentDetailVO.getModifyBy() );
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setModifyDate( paymentAdjustmentDetailVO.getModifyDate() );
               this.getPaymentAdjustmentHeaderDao().updatePaymentAdjustmentHeader( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() );
            }
         }

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
   // Reviewed by Kevin Jin at 2013-12-31
   public int deletePaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException
   {
      int rows = 0;

      try
      {
         startTransaction();

         // ��ʼ�� PaymentAdjustmentHeaderVO
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = this.getPaymentAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );

         // ��ʼ��EMPPaymentDTO
         final ServiceContractVO serviceContractVO = new ServiceContractVO();
         serviceContractVO.setAccountId( paymentAdjustmentHeaderVO.getAccountId() );
         serviceContractVO.setEmployeeId( paymentAdjustmentHeaderVO.getEmployeeId() );
         serviceContractVO.setEmployeeContractId( paymentAdjustmentHeaderVO.getContractId() );
         serviceContractVO.setMonthly( paymentAdjustmentHeaderVO.getMonthly() );
         final EMPPaymentDTO empPaymentDTO = this.getServiceContractService().getEMPPaymentDTOByCondition( serviceContractVO, "1" );

         if ( empPaymentDTO != null )
         {
            // ��ʼ��PaymentAdjustmentDTO
            final PaymentAdjustmentDTO paymentAdjustmentDTO = empPaymentDTO.getPaymentAdjustmentDTOByAdjustmentHeaderId( paymentAdjustmentDetailVO.getAdjustmentHeaderId() );

            // ������ɾ��PaymentAdjustmentDetailVO
            if ( paymentAdjustmentDTO != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().size() > 0 )
            {
               PaymentAdjustmentDetailVO targetPaymentAdjustmentDetailVO = new PaymentAdjustmentDetailVO();

               for ( PaymentAdjustmentDetailVO tempPaymentAdjustmentDetailVO : paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() )
               {
                  if ( tempPaymentAdjustmentDetailVO.getAdjustmentDetailId().equals( paymentAdjustmentDetailVO.getAdjustmentDetailId() ) )
                  {
                     targetPaymentAdjustmentDetailVO = tempPaymentAdjustmentDetailVO;
                  }
               }

               paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().remove( targetPaymentAdjustmentDetailVO );
            }

            // ����н�ʵ���
            empPaymentDTO.calculatePaymentAdjustment( paymentAdjustmentHeaderVO );

            // ���´ӱ�����
            paymentAdjustmentDetailVO.setDeleted( PaymentAdjustmentDetailVO.FALSE );
            rows = ( ( PaymentAdjustmentDetailDao ) getDao() ).updatePaymentAdjustmentDetail( paymentAdjustmentDetailVO );

            if ( paymentAdjustmentDTO != null )
            {
               // ������������
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setModifyBy( paymentAdjustmentDetailVO.getModifyBy() );
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setModifyDate( paymentAdjustmentDetailVO.getModifyDate() );
               this.getPaymentAdjustmentHeaderDao().updatePaymentAdjustmentHeader( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() );
            }
         }

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

}
