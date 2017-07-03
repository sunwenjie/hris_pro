package com.kan.hro.service.impl.biz.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.EMPPaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.settlement.AdjustmentDTO;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.settlement.SettlementDTO;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class ServiceContractServiceImpl extends ContextService implements ServiceContractService
{
   // ע��ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ע��EmployeeDao
   private EmployeeDao employeeDao;

   // ע��EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // ע��TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // ע��OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // ע��PaymentAdjustmentHeaderDao
   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   // ע��PaymentAdjustmentDetailDao
   private PaymentAdjustmentDetailDao paymentAdjustmentDetailDao;

   // ע��PaymentHeaderDao
   private PaymentHeaderDao paymentHeaderDao;

   // ע��PaymentDetailDao
   private PaymentDetailDao paymentDetailDao;

   // ע��AdjustmentHeaderDao
   private AdjustmentHeaderDao adjustmentHeaderDao;

   // ע��AdjustmentDetailDao
   private AdjustmentDetailDao adjustmentDetailDao;

   public final ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public final void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public final void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public final TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public final void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public final OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public final void setOrderDetailDao( OrderDetailDao orderDetailDao )
   {
      this.orderDetailDao = orderDetailDao;
   }

   public final PaymentAdjustmentHeaderDao getPaymentAdjustmentHeaderDao()
   {
      return paymentAdjustmentHeaderDao;
   }

   public final void setPaymentAdjustmentHeaderDao( PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao )
   {
      this.paymentAdjustmentHeaderDao = paymentAdjustmentHeaderDao;
   }

   public final PaymentAdjustmentDetailDao getPaymentAdjustmentDetailDao()
   {
      return paymentAdjustmentDetailDao;
   }

   public final void setPaymentAdjustmentDetailDao( PaymentAdjustmentDetailDao paymentAdjustmentDetailDao )
   {
      this.paymentAdjustmentDetailDao = paymentAdjustmentDetailDao;
   }

   public final PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public final void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   public final PaymentDetailDao getPaymentDetailDao()
   {
      return paymentDetailDao;
   }

   public final void setPaymentDetailDao( PaymentDetailDao paymentDetailDao )
   {
      this.paymentDetailDao = paymentDetailDao;
   }

   public final AdjustmentHeaderDao getAdjustmentHeaderDao()
   {
      return adjustmentHeaderDao;
   }

   public final void setAdjustmentHeaderDao( AdjustmentHeaderDao adjustmentHeaderDao )
   {
      this.adjustmentHeaderDao = adjustmentHeaderDao;
   }

   public final AdjustmentDetailDao getAdjustmentDetailDao()
   {
      return adjustmentDetailDao;
   }

   public final void setAdjustmentDetailDao( AdjustmentDetailDao adjustmentDetailDao )
   {
      this.adjustmentDetailDao = adjustmentDetailDao;
   }

   @Override
   public PagedListHolder getServiceContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ServiceContractDao serviceContractDao = ( ServiceContractDao ) getDao();
      pagedListHolder.setHolderSize( serviceContractDao.countServiceContractVOsByCondition( ( ServiceContractVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( serviceContractDao.getServiceContractVOsByCondition( ( ServiceContractVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( serviceContractDao.getServiceContractVOsByCondition( ( ServiceContractVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ServiceContractVO getServiceContractVOByContractId( final String contractId ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).getServiceContractVOByContractId( contractId );
   }

   @Override
   public int updateServiceContract( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).updateServiceContract( serviceContractVO );
   }

   @Override
   public int insertServiceContract( final ServiceContractVO serviceContractVO ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).insertServiceContract( serviceContractVO );
   }

   @Override
   public int deleteServiceContract( final String contractId ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).deleteServiceContract( contractId );
   }

   @Override
   public List< Object > getServiceContractVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( ServiceContractDao ) getDao() ).getServiceContractVOsByOrderHeaderId( orderHeaderId );
   }

   @Override
   // ����н��������ݻ�ȡ
   // Added by Kevin Jin at 2013-12-30
   public EMPPaymentDTO getEMPPaymentDTOByCondition( final ServiceContractVO serviceContractVO, final String adjustmentStatus ) throws KANException
   {
      // ��ʼ��EmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( serviceContractVO.getEmployeeContractId() );
      // ��ʼ��EntityVO
      final EntityVO entityVO = KANConstants.getKANAccountConstants( employeeContractVO.getAccountId() ).getEntityVOByEntityId( employeeContractVO.getEntityId() );

      // ��ʼ��PaymentHeaderVO������������
      PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
      paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );
      paymentHeaderVO.setContractId( serviceContractVO.getEmployeeContractId() );
      paymentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      // ��ȡ�½�״̬��PaymentHeaderVO
      paymentHeaderVO.setStatus( "1" );
      final List< Object > paymentHeaderVOs = getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

      // �����ǰ����Э���Ѵ����½�״̬��н������
      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         return null;
      }

      // ��ʼ��EMPPaymentDTO
      final EMPPaymentDTO empPaymentDTO = new EMPPaymentDTO();

      // װ��EmployeeVO
      empPaymentDTO.setEmployeeVO( getEmployeeDao().getEmployeeVOByEmployeeId( serviceContractVO.getEmployeeId() ) );

      // װ��PaymentDTO�б������Ѿ����ڣ��ϲ���˰ʹ�ã�
      paymentHeaderVO = new PaymentHeaderVO();
      paymentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
      paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

      // �������ʵ����Ҫ������˰
      if ( entityVO != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ) != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ).equals( "1" ) )
      {
         paymentHeaderVO.setEntityId( entityVO.getEntityId() );
      }

      paymentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
      paymentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      fetchPayment( empPaymentDTO, paymentHeaderVO );

      // װ��PaymentAdjustmentDTO�������Ѿ���׼�����ŵĵ������ϲ���˰ʹ�ã�
      final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
      paymentAdjustmentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
      paymentAdjustmentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

      // �������ʵ����Ҫ������˰
      if ( entityVO != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ) != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ).equals( "1" ) )
      {
         paymentAdjustmentHeaderVO.setEntityId( serviceContractVO.getEntityId() );
      }

      paymentAdjustmentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
      paymentAdjustmentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      paymentAdjustmentHeaderVO.setStatus( adjustmentStatus != null ? adjustmentStatus + ", 3, 5" : "3, 5" );
      fetchPaymentAdjustment( empPaymentDTO, paymentAdjustmentHeaderVO );

      return empPaymentDTO;
   }

   @Override
   // ����н�����ݻ�ȡ
   // Added by Kevin Jin at 2013-12-04
   public List< EMPPaymentDTO > getEMPPaymentDTOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException
   {
      // ��ʼ��EMPPaymentDTO List
      final List< EMPPaymentDTO > empPaymentDTOs = new ArrayList< EMPPaymentDTO >();

      // ��ȡ���Ϲ��ʼ��������
      final List< Object > serviceContractVOs = ( ( ServiceContractDao ) getDao() ).getPaymentServiceContractVOsByCondition( serviceContractVO );

      final String optionIndependenceTax = KANConstants.getKANAccountConstants( serviceContractVO.getAccountId() ).OPTIONS_INDEPENDENCE_TAX;

      if ( serviceContractVOs != null && serviceContractVOs.size() > 0 )
      {
         for ( Object serviceContractVOObject : serviceContractVOs )
         {
            // ����ת��
            final ServiceContractVO tempServiceContractVO = ( ServiceContractVO ) serviceContractVOObject;

            // ��ʼ��EntityVO
            final EntityVO entityVO = KANConstants.getKANAccountConstants( tempServiceContractVO.getAccountId() ).getEntityVOByEntityId( tempServiceContractVO.getEntityId() );

            boolean independenceTax = false;

            // �ж��Ƿ������˰
            if ( ( KANUtil.filterEmpty( optionIndependenceTax ) != null && KANUtil.filterEmpty( optionIndependenceTax ).equals( "1" ) ) )
            {
               independenceTax = true;
            }

            // ��ʼ��EmployeeVO
            final EmployeeVO employeeVO = getEmployeeDao().getEmployeeVOByEmployeeId( tempServiceContractVO.getEmployeeId() );

            if ( employeeVO != null )
            {
               // ��ʼ��PaymentHeaderVO������������
               PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setContractId( tempServiceContractVO.getEmployeeContractId() );
               paymentHeaderVO.setMonthly( tempServiceContractVO.getMonthly() );
               // ��ȡ�½�״̬��PaymentHeaderVO
               paymentHeaderVO.setStatus( "1" );
               final List< Object > paymentHeaderVOs = getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

               // �����ǰ����Э���Ѵ����½�״̬��н������
               if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
               {
                  continue;
               }

               // ��ʼ��EMPPaymentDTO
               final EMPPaymentDTO empPaymentDTO = getEMPPaymentDTO( empPaymentDTOs, tempServiceContractVO.getEmployeeId(), independenceTax && entityVO != null ? entityVO.getEntityId()
                     : null );
               empPaymentDTO.setEmployeeVO( getEmployeeDao().getEmployeeVOByEmployeeId( tempServiceContractVO.getEmployeeId() ) );

               // װ��PaymentDTO�б������Ѿ����ڣ��ϲ���˰ʹ�ã�
               paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setAccountId( tempServiceContractVO.getAccountId() );
               paymentHeaderVO.setCorpId( tempServiceContractVO.getCorpId() );

               // �������ʵ����Ҫ������˰
               if ( independenceTax && entityVO != null )
               {
                  paymentHeaderVO.setEntityId( entityVO.getEntityId() );
               }

               paymentHeaderVO.setEmployeeId( tempServiceContractVO.getEmployeeId() );
               paymentHeaderVO.setMonthly( tempServiceContractVO.getMonthly() );
               fetchPayment( empPaymentDTO, paymentHeaderVO );

               // װ��PaymentAdjustmentDTO�������Ѿ���׼�����ŵĵ������ϲ���˰ʹ�ã�
               final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
               paymentAdjustmentHeaderVO.setAccountId( tempServiceContractVO.getAccountId() );
               paymentAdjustmentHeaderVO.setCorpId( tempServiceContractVO.getCorpId() );

               // �������ʵ����Ҫ������˰
               if ( independenceTax )
               {
                  paymentAdjustmentHeaderVO.setEntityId( tempServiceContractVO.getEntityId() );
               }

               paymentAdjustmentHeaderVO.setEmployeeId( tempServiceContractVO.getEmployeeId() );
               paymentAdjustmentHeaderVO.setMonthly( tempServiceContractVO.getMonthly() );
               paymentAdjustmentHeaderVO.setStatus( "3, 5" );
               fetchPaymentAdjustment( empPaymentDTO, paymentAdjustmentHeaderVO );

               // װ��SettlementDTO�б�δ����н������ݣ�
               fetchSettlement( empPaymentDTO, tempServiceContractVO );
            }
         }
      }

      // ��ʼ��AdjustmentHeaderVO
      final AdjustmentHeaderVO adjustmentHeaderVO = new AdjustmentHeaderVO();
      adjustmentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
      adjustmentHeaderVO.setMonthly( serviceContractVO.getMonthly() );
      adjustmentHeaderVO.setEntityId( serviceContractVO.getEntityId() );
      adjustmentHeaderVO.setBusinessTypeId( serviceContractVO.getBusinessTypeId() );
      adjustmentHeaderVO.setClientId( serviceContractVO.getClientId() );
      adjustmentHeaderVO.setCorpId( serviceContractVO.getCorpId() );
      adjustmentHeaderVO.setOrderId( serviceContractVO.getOrderId() );
      adjustmentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
      adjustmentHeaderVO.setContractId( serviceContractVO.getContractId() );

      // ��ȡ���Ϲ��ʼ���Ľ����������
      final List< Object > adjustmentHeaderVOs = this.getAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOsByCondition( adjustmentHeaderVO );

      if ( adjustmentHeaderVOs != null && adjustmentHeaderVOs.size() > 0 )
      {
         for ( Object adjustmentHeaderVOObject : adjustmentHeaderVOs )
         {
            // ����ת��
            final AdjustmentHeaderVO tempAdjustmentHeaderVO = ( AdjustmentHeaderVO ) adjustmentHeaderVOObject;

            // ��ʼ��EntityVO
            final EntityVO entityVO = KANConstants.getKANAccountConstants( tempAdjustmentHeaderVO.getAccountId() ).getEntityVOByEntityId( tempAdjustmentHeaderVO.getEntityId() );

            boolean independenceTax = false;

            // �ж��Ƿ������˰
            if ( entityVO != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ) != null && KANUtil.filterEmpty( entityVO.getIndependenceTax() ).equals( "1" ) )
            {
               independenceTax = true;
            }

            // ��ʼ��EmployeeVO
            final EmployeeVO employeeVO = getEmployeeDao().getEmployeeVOByEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() );

            if ( employeeVO != null )
            {
               // ��ʼ��EMPPaymentDTO
               final EMPPaymentDTO empPaymentDTO = getEMPPaymentDTO( empPaymentDTOs, tempAdjustmentHeaderVO.getEmployeeId(), independenceTax && entityVO != null ? entityVO.getEntityId()
                     : null );
               empPaymentDTO.setEmployeeVO( getEmployeeDao().getEmployeeVOByEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() ) );

               // װ��PaymentDTO�б������Ѿ����ڣ��ϲ���˰ʹ�ã�
               if ( empPaymentDTO.getPaymentDTOs() == null || empPaymentDTO.getPaymentDTOs().size() == 0 )
               {
                  final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
                  paymentHeaderVO.setAccountId( tempAdjustmentHeaderVO.getAccountId() );
                  paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

                  // �������ʵ����Ҫ������˰
                  if ( independenceTax && entityVO != null )
                  {
                     paymentHeaderVO.setEntityId( entityVO.getEntityId() );
                  }
                  paymentHeaderVO.setEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() );
                  paymentHeaderVO.setMonthly( tempAdjustmentHeaderVO.getMonthly() );
                  fetchPayment( empPaymentDTO, paymentHeaderVO );
               }

               // װ��PaymentAdjustmentDTO�������Ѿ���׼�����ŵĵ������ϲ���˰ʹ�ã�
               if ( empPaymentDTO.getPaymentDTOs() == null || empPaymentDTO.getPaymentDTOs().size() == 0 )
               {
                  final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
                  paymentAdjustmentHeaderVO.setAccountId( tempAdjustmentHeaderVO.getAccountId() );
                  paymentAdjustmentHeaderVO.setCorpId( serviceContractVO.getCorpId() );

                  // �������ʵ����Ҫ������˰
                  if ( independenceTax )
                  {
                     paymentAdjustmentHeaderVO.setEntityId( tempAdjustmentHeaderVO.getEntityId() );
                  }

                  paymentAdjustmentHeaderVO.setEmployeeId( tempAdjustmentHeaderVO.getEmployeeId() );
                  paymentAdjustmentHeaderVO.setMonthly( tempAdjustmentHeaderVO.getMonthly() );
                  paymentAdjustmentHeaderVO.setStatus( "3, 5" );
                  fetchPaymentAdjustment( empPaymentDTO, paymentAdjustmentHeaderVO );
               }

               // װ��AdjustmentDTO�б�δ����н������ݣ�
               fetchAdjustment( empPaymentDTO, tempAdjustmentHeaderVO );
            }
         }
      }

      return empPaymentDTOs;
   }

   // װ��PaymentDTO�б�
   // Added by Kevin Jin at 2013-12-04
   private void fetchPayment( final EMPPaymentDTO empPaymentDTO, final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      // ��ȡ���ύ���ѷ��ŵ�PaymentHeaderVO
      paymentHeaderVO.setStatus( "2, 3" );
      final List< Object > paymentHeaderVOs = getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            // ��ʼ��PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();

            // ת��PaymentHeaderVO
            final PaymentHeaderVO tempPaymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

            // װ��PaymentHeaderVO
            paymentDTO.setPaymentHeaderVO( tempPaymentHeaderVO );

            // ��ʼ��PaymentDetailVO�б�
            final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( tempPaymentHeaderVO.getPaymentHeaderId() );

            if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
            {
               for ( Object paymentDetailVOObject : paymentDetailVOs )
               {
                  paymentDTO.getPaymentDetailVOs().add( ( PaymentDetailVO ) paymentDetailVOObject );
               }
            }

            empPaymentDTO.addPaymentDTO( paymentDTO );
         }
      }
   }

   // װ��PaymentAdjustmentDTO�б�
   // Added by Kevin Jin at 2013-12-04
   private void fetchPaymentAdjustment( final EMPPaymentDTO empPaymentDTO, final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      final List< Object > paymentAdjustmentHeaderVOs = getPaymentAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderVO );

      if ( paymentAdjustmentHeaderVOs != null && paymentAdjustmentHeaderVOs.size() > 0 )
      {
         for ( Object paymentAdjustmentHeaderVOObject : paymentAdjustmentHeaderVOs )
         {
            // ��ʼ��PaymentAdjustmentDTO
            final PaymentAdjustmentDTO paymentAdjustmentDTO = new PaymentAdjustmentDTO();

            // ת��PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO tempPaymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) paymentAdjustmentHeaderVOObject;

            // װ��PaymentAdjustmentHeaderVO
            paymentAdjustmentDTO.setPaymentAdjustmentHeaderVO( tempPaymentAdjustmentHeaderVO );

            // ��ʼ��PaymentAdjustmentDetailVO�б�
            final List< Object > paymentAdjustmentDetailVOs = this.getPaymentAdjustmentDetailDao().getPaymentAdjustmentDetailVOsByAdjustmentHeaderId( tempPaymentAdjustmentHeaderVO.getAdjustmentHeaderId() );

            if ( paymentAdjustmentDetailVOs != null && paymentAdjustmentDetailVOs.size() > 0 )
            {
               for ( Object paymentAdjustmentDetailVOObject : paymentAdjustmentDetailVOs )
               {
                  paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().add( ( PaymentAdjustmentDetailVO ) paymentAdjustmentDetailVOObject );
               }
            }

            empPaymentDTO.addPaymentAdjustmentDTO( paymentAdjustmentDTO );
         }
      }
   }

   // װ��SettlementDTO�б�
   // Added by Kevin Jin at 2013-12-04
   private void fetchSettlement( final EMPPaymentDTO empPaymentDTO, final ServiceContractVO serviceContractVO ) throws KANException
   {
      final SettlementDTO settlementDTO = new SettlementDTO();

      // װ��ClientOrderHeaderVO
      if ( KANUtil.filterEmpty( serviceContractVO.getOrderId() ) != null )
      {
         settlementDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( serviceContractVO.getOrderId() ) );
      }

      // װ��EmployeeContactVO
      if ( KANUtil.filterEmpty( serviceContractVO.getEmployeeContractId() ) != null )
      {
         settlementDTO.setEmployeeContractVO( this.getEmployeeContractDao().getEmployeeContractVOByContractId( serviceContractVO.getEmployeeContractId() ) );
      }

      // װ��TimesheetHeaderVO
      if ( KANUtil.filterEmpty( serviceContractVO.getTimesheetId() ) != null )
      {
         settlementDTO.setTimesheetHeaderVO( getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( serviceContractVO.getTimesheetId() ) );
      }

      // װ��ServiceContractVO
      settlementDTO.setServiceContractVO( serviceContractVO );

      // װ��OrderDetailVO�б�
      final List< Object > orderDetailVOs = getOrderDetailDao().getOrderDetailVOsByContractId( serviceContractVO.getContractId() );

      if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
      {
         for ( Object orderDetailVOObject : orderDetailVOs )
         {
            settlementDTO.getOrderDetailVOs().add( ( OrderDetailVO ) orderDetailVOObject );
         }
      }

      empPaymentDTO.getSettlementDTOs().add( settlementDTO );
   }

   // װ��AdjustmentDTO�б�
   // Added by Kevin Jin at 2014-01-16
   private void fetchAdjustment( final EMPPaymentDTO empPaymentDTO, final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      final AdjustmentDTO adjustmentDTO = new AdjustmentDTO();

      // װ��AdjustmentHeaderVO
      adjustmentDTO.setAdjustmentHeaderVO( adjustmentHeaderVO );

      // װ��ClientOrderHeaderVO
      if ( KANUtil.filterEmpty( adjustmentHeaderVO.getOrderId() ) != null )
      {
         adjustmentDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( adjustmentHeaderVO.getOrderId() ) );
      }

      // װ��EmployeeContactVO
      if ( KANUtil.filterEmpty( adjustmentHeaderVO.getContractId() ) != null )
      {
         adjustmentDTO.setEmployeeContractVO( this.getEmployeeContractDao().getEmployeeContractVOByContractId( adjustmentHeaderVO.getContractId() ) );
      }

      // װ��AdjustmentDetailVO�б�
      final List< Object > adjustmentDetailVOs = getAdjustmentDetailDao().getAdjustmentDetailVOsByAdjustmentHeaderId( adjustmentHeaderVO.getAdjustmentHeaderId() );

      if ( adjustmentDetailVOs != null && adjustmentDetailVOs.size() > 0 )
      {
         for ( Object adjustmentDetailVOObject : adjustmentDetailVOs )
         {
            adjustmentDTO.getAdjustmentDetailVOs().add( ( AdjustmentDetailVO ) adjustmentDetailVOObject );
         }
      }

      empPaymentDTO.getAdjustmentDTOs().add( adjustmentDTO );
   }

   // ��ȡEMPPaymentDTO��������������½�
   // Added by Kevin Jin at 2013-12-04
   private EMPPaymentDTO getEMPPaymentDTO( final List< EMPPaymentDTO > empPaymentDTOs, final String employeeId, final String entityId )
   {
      for ( EMPPaymentDTO empPaymentDTO : empPaymentDTOs )
      {
         if ( empPaymentDTO.getEmployeeVO() != null
               && KANUtil.filterEmpty( empPaymentDTO.getEmployeeVO().getEmployeeId() ) != null
               && empPaymentDTO.getEmployeeVO().getEmployeeId().equals( employeeId )
               && ( KANUtil.filterEmpty( entityId ) == null || ( KANUtil.filterEmpty( empPaymentDTO.getEntityId() ) != null && KANUtil.filterEmpty( empPaymentDTO.getEntityId() ).equals( entityId ) ) ) )
         {
            return empPaymentDTO;
         }
      }

      // ��ʼ��EMPPaymentDTO
      final EMPPaymentDTO empPaymentDTO = new EMPPaymentDTO();

      // ��Ҫ������˰�����
      if ( KANUtil.filterEmpty( entityId ) != null )
      {
         empPaymentDTO.setEntityId( entityId );
      }

      empPaymentDTOs.add( empPaymentDTO );

      return empPaymentDTO;
   }
}
