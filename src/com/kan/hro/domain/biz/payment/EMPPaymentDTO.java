package com.kan.hro.domain.biz.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.management.BankVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.domain.system.IncomeTaxRangeDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.settlement.AdjustmentDTO;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.settlement.SettlementDTO;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�EMPPaymentDTO  
 * ��������  
 * �����ˣ�Kevin Jin  
 * ����ʱ�䣺2013-12-03 
 */
public class EMPPaymentDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1030287169254622050L;

   // EmployeeVO
   private EmployeeVO employeeVO;

   // PaymentDTO List
   private List< PaymentDTO > paymentDTOs = new ArrayList< PaymentDTO >();

   // PaymentAdjustmentDTO List
   private List< PaymentAdjustmentDTO > paymentAdjustmentDTOs = new ArrayList< PaymentAdjustmentDTO >();

   // SettlementDTO List
   private List< SettlementDTO > settlementDTOs = new ArrayList< SettlementDTO >();

   // AdjustmentDTO List
   private List< AdjustmentDTO > adjustmentDTOs = new ArrayList< AdjustmentDTO >();

   // EntityId ������˰��ʶ
   private String entityId;

   public final EmployeeVO getEmployeeVO()
   {
      return employeeVO;
   }

   public final void setEmployeeVO( EmployeeVO employeeVO )
   {
      this.employeeVO = employeeVO;
   }

   public final List< PaymentDTO > getPaymentDTOs()
   {
      return paymentDTOs;
   }

   public final void setPaymentDTOs( List< PaymentDTO > paymentDTOs )
   {
      this.paymentDTOs = paymentDTOs;
   }

   public final List< PaymentAdjustmentDTO > getPaymentAdjustmentDTOs()
   {
      return paymentAdjustmentDTOs;
   }

   public final void setPaymentAdjustmentDTOs( List< PaymentAdjustmentDTO > paymentAdjustmentDTOs )
   {
      this.paymentAdjustmentDTOs = paymentAdjustmentDTOs;
   }

   public final List< SettlementDTO > getSettlementDTOs()
   {
      return settlementDTOs;
   }

   public final void setSettlementDTOs( List< SettlementDTO > settlementDTOs )
   {
      this.settlementDTOs = settlementDTOs;
   }

   public final List< AdjustmentDTO > getAdjustmentDTOs()
   {
      return adjustmentDTOs;
   }

   public final void setAdjustmentDTOs( List< AdjustmentDTO > adjustmentDTOs )
   {
      this.adjustmentDTOs = adjustmentDTOs;
   }

   public final String getEntityId()
   {
      return entityId;
   }

   public final void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   // н�����
   public boolean calculatePayment( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      // ���ʽ���
      if ( this.getSettlementDTOs() != null && this.getSettlementDTOs().size() > 0 )
      {
         // ������������
         for ( SettlementDTO settlementDTO : this.getSettlementDTOs() )
         {
            // ��ʼ��ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = settlementDTO.getClientOrderHeaderVO();

            // ��ʼ��EmployeeContractVO
            final EmployeeContractVO employeeContractVO = settlementDTO.getEmployeeContractVO();

            // ��ʼ����˰����
            IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( employeeContractVO.getIncomeTaxBaseId() );

            if ( incomeTaxBaseVO == null )
            {
               incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( clientOrderHeaderVO.getIncomeTaxBaseId() );

               if ( incomeTaxBaseVO == null )
               {
                  incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
               }
            }

            // ��ʼ����˰����
            IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( employeeContractVO.getIncomeTaxRangeHeaderId() );

            if ( incomeTaxRangeDTO == null )
            {
               incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() );

               if ( incomeTaxRangeDTO == null )
               {
                  incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();
               }
            }

            // ��ʼ����˰�ľ��ȼ���ȡ
            final String taxAccuracy = String.valueOf( incomeTaxBaseVO.getAccuracy() != null ? ( Integer.valueOf( incomeTaxBaseVO.getAccuracy() ) - 1 ) : 0 );
            final String taxRound = incomeTaxBaseVO.getRound();

            // ��ʼ��TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = settlementDTO.getTimesheetHeaderVO();

            // ��ʼ��ServiceContractVO
            final ServiceContractVO serviceContractVO = settlementDTO.getServiceContractVO();

            if ( serviceContractVO != null )
            {
               // ��ȡ��Ҫ�����н���·�
               final String monthly = serviceContractVO.getMonthly();

               // ��ʼ��PaymentDTO
               final PaymentDTO paymentDTO = new PaymentDTO();

               /** ����н��̨�� */
               final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setAccountId( serviceContractVO.getAccountId() );
               paymentHeaderVO.setOrderContractId( serviceContractVO.getContractId() );
               paymentHeaderVO.setEntityId( serviceContractVO.getEntityId() );
               paymentHeaderVO.setBusinessTypeId( serviceContractVO.getBusinessTypeId() );
               paymentHeaderVO.setClientId( serviceContractVO.getClientId() );
               paymentHeaderVO.setCorpId( serviceContractVO.getCorpId() );
               paymentHeaderVO.setOrderId( serviceContractVO.getOrderId() );
               paymentHeaderVO.setContractId( serviceContractVO.getEmployeeContractId() );
               paymentHeaderVO.setEmployeeId( serviceContractVO.getEmployeeId() );
               paymentHeaderVO.setEmployeeNameZH( getEmployeeVO().getNameZH() );
               paymentHeaderVO.setEmployeeNameEN( getEmployeeVO().getNameEN() );
               paymentHeaderVO.setBankId( getEmployeeVO().getBankId() );

               // ��ʼ��BankVO
               final BankVO bankVO = KANConstants.getKANAccountConstants( serviceContractVO.getAccountId() ).getBankVOByBankId( getEmployeeVO().getBankId() );

               if ( bankVO != null )
               {
                  paymentHeaderVO.setBankNameZH( bankVO.getNameZH() );
                  paymentHeaderVO.setBankNameEN( bankVO.getNameEN() );
               }

               paymentHeaderVO.setBankAccount( getEmployeeVO().getBankAccount() );
               paymentHeaderVO.setStartDate( timesheetHeaderVO != null ? timesheetHeaderVO.getStartDate() : "" );
               paymentHeaderVO.setEndDate( timesheetHeaderVO != null ? timesheetHeaderVO.getEndDate() : "" );
               paymentHeaderVO.setCertificateType( getEmployeeVO().getCertificateType() );
               paymentHeaderVO.setCertificateNumber( getEmployeeVO().getCertificateNumber() );
               paymentHeaderVO.setMonthly( monthly );

               // ��ʼ��SalaryVendorId
               String salaryVendorId = employeeContractVO.getSalaryVendorId();

               if ( KANUtil.filterEmpty( salaryVendorId, "0" ) == null )
               {
                  salaryVendorId = clientOrderHeaderVO.getSalaryVendorId();
               }

               paymentHeaderVO.setVendorId( salaryVendorId );
               paymentHeaderVO.setDeleted( "1" );
               paymentHeaderVO.setStatus( PaymentHeaderVO.TRUE );
               paymentHeaderVO.setCreateBy( paymentBatchVO.getCreateBy() );
               paymentHeaderVO.setModifyBy( paymentBatchVO.getModifyBy() );

               // ��ʼ������ֵ
               double billAmountCompany = 0;
               double billAmountPersonal = 0;
               double costAmountCompany = 0;
               double costAmountPersonal = 0;
               // ���Ӻϼƣ����ڸ�˰����
               double addtionalBillAmountPersonal = 0;
               // ����˰����
               double taxAgentAmountPersonal = 0;
               // ���ս�
               double annualBonus = 0;

               if ( settlementDTO.getOrderDetailVOs() != null && settlementDTO.getOrderDetailVOs().size() > 0 )
               {
                  for ( OrderDetailVO orderDetailVO : settlementDTO.getOrderDetailVOs() )
                  {
                     // ��ȡ��ǰ�̱���Ŀ
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( serviceContractVO.getAccountId() ).getItemVOByItemId( orderDetailVO.getItemId() );

                     // �����ĿΪ�գ�������һ��ѭ��
                     if ( itemVO == null )
                     {
                        continue;
                     }

                     // �����ĿΪ����˰���ۼ�Ӧ˰н���������һ��ѭ��
                     if ( KANUtil.filterEmpty( itemVO.getPersonalTaxAgent() ) != null && itemVO.getPersonalTaxAgent().equals( "1" ) )
                     {
                        if ( ( KANUtil.filterEmpty( orderDetailVO.getBillAmountPersonal() ) != null && Double.valueOf( orderDetailVO.getBillAmountPersonal() ) != 0 )
                              || ( KANUtil.filterEmpty( orderDetailVO.getCostAmountPersonal() ) != null && Double.valueOf( orderDetailVO.getCostAmountPersonal() ) != 0 ) )
                        {
                           taxAgentAmountPersonal = taxAgentAmountPersonal + Double.valueOf( orderDetailVO.getBillAmountPersonal() )
                                 - Double.valueOf( orderDetailVO.getCostAmountPersonal() );
                        }

                        continue;
                     }

                     if ( ( KANUtil.filterEmpty( orderDetailVO.getBillAmountPersonal() ) != null && Double.valueOf( orderDetailVO.getBillAmountPersonal() ) != 0 )
                           || ( KANUtil.filterEmpty( orderDetailVO.getCostAmountPersonal() ) != null && Double.valueOf( orderDetailVO.getCostAmountPersonal() ) != 0 )
                           || itemVO.getItemType().equals( "7" ) || itemVO.getItemType().equals( "13" ) )
                     {
                        /** ����н��̨����ϸ */
                        final PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
                        paymentDetailVO.setOrderDetailId( orderDetailVO.getOrderDetailId() );
                        paymentDetailVO.setItemId( itemVO.getItemId() );
                        paymentDetailVO.setItemNo( itemVO.getItemNo() );
                        paymentDetailVO.setNameZH( itemVO.getNameZH() );
                        paymentDetailVO.setNameEN( itemVO.getNameEN() );
                        paymentDetailVO.setBaseCompany( orderDetailVO.getSbBaseCompany() );
                        paymentDetailVO.setBasePersonal( orderDetailVO.getSbBasePersonal() );
                        paymentDetailVO.setBillRateCompany( orderDetailVO.getBillRateCompany() );
                        paymentDetailVO.setBillRatePersonal( orderDetailVO.getBillRatePersonal() );
                        paymentDetailVO.setCostRateCompany( orderDetailVO.getCostRateCompany() );
                        paymentDetailVO.setCostRatePersonal( orderDetailVO.getCostRatePersonal() );
                        paymentDetailVO.setBillFixCompany( orderDetailVO.getBillFixCompany() );
                        paymentDetailVO.setBillFixPersonal( orderDetailVO.getBillFixPersonal() );
                        paymentDetailVO.setCostFixCompany( orderDetailVO.getCostFixCompany() );
                        paymentDetailVO.setCostFixPersonal( orderDetailVO.getCostFixPersonal() );
                        paymentDetailVO.setBillAmountCompany( orderDetailVO.getBillAmountCompany() );
                        paymentDetailVO.setBillAmountPersonal( orderDetailVO.getBillAmountPersonal() );
                        paymentDetailVO.setCostAmountCompany( orderDetailVO.getCostAmountCompany() );
                        paymentDetailVO.setCostAmountPersonal( orderDetailVO.getCostAmountPersonal() );
                        paymentDetailVO.setDeleted( "1" );
                        paymentDetailVO.setStatus( PaymentDetailVO.TRUE );
                        paymentDetailVO.setCreateBy( paymentBatchVO.getCreateBy() );
                        paymentDetailVO.setModifyBy( paymentBatchVO.getModifyBy() );

                        if ( KANUtil.filterEmpty( orderDetailVO.getBillAmountCompany() ) != null && Double.valueOf( orderDetailVO.getBillAmountCompany() ) > 0 )
                        {
                           billAmountCompany = billAmountCompany + Double.valueOf( orderDetailVO.getBillAmountCompany() );
                        }

                        billAmountPersonal = billAmountPersonal + Double.valueOf( orderDetailVO.getBillAmountPersonal() );

                        if ( KANUtil.filterEmpty( orderDetailVO.getCostAmountCompany() ) != null && Double.valueOf( orderDetailVO.getCostAmountCompany() ) > 0 )
                        {
                           costAmountCompany = costAmountCompany + Double.valueOf( orderDetailVO.getCostAmountCompany() );
                        }

                        costAmountPersonal = costAmountPersonal + Double.valueOf( orderDetailVO.getCostAmountPersonal() );

                        if ( KANUtil.filterEmpty( itemVO.getPersonalTax() ) != null && itemVO.getPersonalTax().equals( "1" ) )
                        {
                           if ( KANUtil.filterEmpty( itemVO.getItemId() ) != null && itemVO.getItemId().trim().equals( "18" ) )
                           {
                              annualBonus = annualBonus + Double.valueOf( orderDetailVO.getBillAmountPersonal() );
                           }
                           else
                           {
                              addtionalBillAmountPersonal = addtionalBillAmountPersonal + Double.valueOf( orderDetailVO.getBillAmountPersonal() )
                                    - Double.valueOf( orderDetailVO.getCostAmountPersonal() );

                              paymentDetailVO.setAddtionalBillAmountPersonal( String.valueOf( Double.valueOf( orderDetailVO.getBillAmountPersonal() )
                                    - Double.valueOf( orderDetailVO.getCostAmountPersonal() ) ) );
                           }
                        }

                        paymentDTO.getPaymentDetailVOs().add( paymentDetailVO );
                     }
                  }
               }

               // ���û���ֵ
               paymentHeaderVO.setBillAmountCompany( String.valueOf( billAmountCompany ) );
               paymentHeaderVO.setBillAmountPersonal( String.valueOf( billAmountPersonal ) );
               paymentHeaderVO.setCostAmountCompany( String.valueOf( costAmountCompany ) );
               paymentHeaderVO.setCostAmountPersonal( String.valueOf( costAmountPersonal ) );

               double base = 0;
               if ( KANUtil.filterEmpty( this.getEmployeeVO().getResidencyType(), "0" ) != null
                     && ( this.getEmployeeVO().getResidencyType().equals( "5" ) || this.getEmployeeVO().getResidencyType().equals( "6" ) ) )
               {
                  if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBaseForeigner() ) != null )
                  {
                     base = Double.valueOf( incomeTaxBaseVO.getBaseForeigner() );
                  }
               }
               else
               {
                  if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBase() ) != null )
                  {
                     base = Double.valueOf( incomeTaxBaseVO.getBase() );
                  }
               }

               // Ӧ˰���ʣ��Ѽ���˰����������
               double taxBase = getValue( "1" ) + addtionalBillAmountPersonal - base + taxAgentAmountPersonal;

               //               if ( annualBonus > 0/*&& taxBase < 0*/ )
               //               {
               //                  annualBonus = annualBonus + taxBase;

               //                  if ( annualBonus > 0 )
               //                  {
               //                     addtionalBillAmountPersonal = addtionalBillAmountPersonal - taxBase;
               //                  }
               //                  else
               //                  {
               //                     addtionalBillAmountPersonal = addtionalBillAmountPersonal + annualBonus;
               //                  }
               //               }

               // Ӧ˰���ʣ�δ����˰����������
               paymentHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( addtionalBillAmountPersonal ) );

               // ����˰����
               paymentHeaderVO.setTaxAgentAmountPersonal( String.valueOf( taxAgentAmountPersonal ) );

               // ��˰
               double personalTax = Double.valueOf( KANUtil.round( taxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( taxBase ) / 100
                     - incomeTaxRangeDTO.getDeductByBase( taxBase ) - getValue( "2" );

               if ( annualBonus > 0 )
               {
                  // ���ս���˰
                  double annualBonusTax = 0;
                  // ���ս�Ӧ˰��
                  double annualBonusTaxBase = annualBonus;

                  if ( addtionalBillAmountPersonal + taxAgentAmountPersonal < base )
                  {
                     annualBonusTaxBase = annualBonus - ( base - addtionalBillAmountPersonal );
                  }

                  annualBonusTax = Double.valueOf( KANUtil.round( annualBonusTaxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( annualBonusTaxBase / 12 )
                        / 100 - incomeTaxRangeDTO.getDeductByBase( annualBonusTaxBase / 12 );
                  paymentHeaderVO.setAnnualBonusTax( KANUtil.round( annualBonusTax, taxAccuracy, taxRound ) );
                  // ʵ�����ս�
                  paymentHeaderVO.setRemark5( KANUtil.round( annualBonus - annualBonusTax, taxAccuracy, taxRound ) );
               }

               // ���ս�
               paymentHeaderVO.setAnnualBonus( String.valueOf( annualBonus ) );

               // ���ø�˰
               paymentHeaderVO.setTaxAmountPersonal( KANUtil.round( personalTax, taxAccuracy, taxRound ) );

               // ����PaymentHeaderVO
               paymentDTO.setPaymentHeaderVO( paymentHeaderVO );

               this.getPaymentDTOs().add( paymentDTO );
            }
         }
      }

      // ���ʵ�������
      if ( this.getAdjustmentDTOs() != null && this.getAdjustmentDTOs().size() > 0 )
      {
         // ������������
         for ( AdjustmentDTO adjustmentDTO : this.getAdjustmentDTOs() )
         {
            // ��ʼ��ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = adjustmentDTO.getClientOrderHeaderVO();

            // ��ʼ��EmployeeContractVO
            final EmployeeContractVO employeeContractVO = adjustmentDTO.getEmployeeContractVO();

            // ��ʼ����˰����
            IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( employeeContractVO.getIncomeTaxBaseId() );

            if ( incomeTaxBaseVO == null )
            {
               incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( clientOrderHeaderVO.getIncomeTaxBaseId() );

               if ( incomeTaxBaseVO == null )
               {
                  incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
               }
            }

            // ��ʼ����˰����
            IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( employeeContractVO.getIncomeTaxRangeHeaderId() );

            if ( incomeTaxRangeDTO == null )
            {
               incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() );

               if ( incomeTaxRangeDTO == null )
               {
                  incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();
               }
            }

            // ��ʼ����˰�ľ��ȼ���ȡ
            final String taxAccuracy = incomeTaxBaseVO.getAccuracy();
            final String taxRound = incomeTaxBaseVO.getRound();

            final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentDTO.getAdjustmentHeaderVO();

            // ��ȡ��Ҫ�����н���·�
            final String monthly = adjustmentHeaderVO.getMonthly();

            // ��ʼ��PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();

            /** ����н��̨�� */
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setAccountId( adjustmentHeaderVO.getAccountId() );
            paymentHeaderVO.setOrderContractId( adjustmentHeaderVO.getAdjustmentHeaderId() );
            paymentHeaderVO.setEntityId( adjustmentHeaderVO.getEntityId() );
            paymentHeaderVO.setBusinessTypeId( adjustmentHeaderVO.getBusinessTypeId() );
            paymentHeaderVO.setClientId( adjustmentHeaderVO.getClientId() );
            paymentHeaderVO.setCorpId( adjustmentHeaderVO.getCorpId() );
            paymentHeaderVO.setOrderId( adjustmentHeaderVO.getOrderId() );
            paymentHeaderVO.setContractId( adjustmentHeaderVO.getContractId() );
            paymentHeaderVO.setEmployeeId( adjustmentHeaderVO.getEmployeeId() );
            paymentHeaderVO.setEmployeeNameZH( getEmployeeVO().getNameZH() );
            paymentHeaderVO.setEmployeeNameEN( getEmployeeVO().getNameEN() );
            paymentHeaderVO.setBankId( getEmployeeVO().getBankId() );
            // ��ʼ��BankVO
            final BankVO bankVO = KANConstants.getKANAccountConstants( adjustmentHeaderVO.getAccountId() ).getBankVOByBankId( getEmployeeVO().getBankId() );
            if ( bankVO != null )
            {
               paymentHeaderVO.setBankNameZH( bankVO.getNameZH() );
               paymentHeaderVO.setBankNameEN( bankVO.getNameEN() );
            }
            paymentHeaderVO.setBankAccount( getEmployeeVO().getBankAccount() );
            paymentHeaderVO.setCertificateType( getEmployeeVO().getCertificateType() );
            paymentHeaderVO.setCertificateNumber( getEmployeeVO().getCertificateNumber() );
            paymentHeaderVO.setMonthly( monthly );
            paymentHeaderVO.setDeleted( "1" );
            paymentHeaderVO.setStatus( PaymentHeaderVO.TRUE );
            paymentHeaderVO.setCreateBy( paymentBatchVO.getCreateBy() );
            paymentHeaderVO.setModifyBy( paymentBatchVO.getModifyBy() );

            // ��ʼ������ֵ
            double billAmountCompany = 0;
            double billAmountPersonal = 0;
            double costAmountCompany = 0;
            double costAmountPersonal = 0;
            // ���Ӻϼƣ����ڸ�˰����
            double addtionalBillAmountPersonal = 0;
            // ����˰����
            double taxAgentAmountPersonal = 0;
            // ���ս�
            double annualBonus = 0;

            if ( adjustmentDTO.getAdjustmentDetailVOs() != null && adjustmentDTO.getAdjustmentDetailVOs().size() > 0 )
            {
               for ( AdjustmentDetailVO adjustmentDetailVO : adjustmentDTO.getAdjustmentDetailVOs() )
               {
                  // ��ȡ��ǰ�̱���Ŀ
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( adjustmentHeaderVO.getAccountId() ).getItemVOByItemId( adjustmentDetailVO.getItemId() );

                  // �����ĿΪ�գ�������һ��ѭ��
                  if ( itemVO == null )
                  {
                     continue;
                  }

                  // �����ĿΪ����˰���ۼ�Ӧ˰н���������һ��ѭ��
                  if ( KANUtil.filterEmpty( itemVO.getPersonalTaxAgent() ) != null && itemVO.getPersonalTaxAgent().equals( "1" ) )
                  {
                     if ( ( KANUtil.filterEmpty( adjustmentDetailVO.getBillAmountPersonal() ) != null && Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() ) != 0 )
                           || ( KANUtil.filterEmpty( adjustmentDetailVO.getCostAmountPersonal() ) != null && Double.valueOf( adjustmentDetailVO.getCostAmountPersonal() ) != 0 ) )
                     {
                        taxAgentAmountPersonal = taxAgentAmountPersonal + Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() )
                              - Double.valueOf( adjustmentDetailVO.getCostAmountPersonal() );
                     }

                     continue;
                  }

                  if ( ( KANUtil.filterEmpty( adjustmentDetailVO.getBillAmountPersonal() ) != null && Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() ) != 0 )
                        || ( KANUtil.filterEmpty( adjustmentDetailVO.getCostAmountPersonal() ) != null && Double.valueOf( adjustmentDetailVO.getCostAmountPersonal() ) != 0 )
                        || itemVO.getItemType().equals( "7" ) )
                  {
                     /** ����н��̨����ϸ */
                     final PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
                     paymentDetailVO.setOrderDetailId( adjustmentDetailVO.getAdjustmentDetailId() );
                     paymentDetailVO.setItemId( itemVO.getItemId() );
                     paymentDetailVO.setItemNo( itemVO.getItemNo() );
                     paymentDetailVO.setNameZH( itemVO.getNameZH() );
                     paymentDetailVO.setNameEN( itemVO.getNameEN() );
                     paymentDetailVO.setBaseCompany( "0" );
                     paymentDetailVO.setBasePersonal( "0" );
                     paymentDetailVO.setBillRateCompany( "100" );
                     paymentDetailVO.setBillRatePersonal( "100" );
                     paymentDetailVO.setCostRateCompany( "100" );
                     paymentDetailVO.setCostRatePersonal( "100" );
                     paymentDetailVO.setBillFixCompany( "0" );
                     paymentDetailVO.setBillFixPersonal( "0" );
                     paymentDetailVO.setCostFixCompany( "0" );
                     paymentDetailVO.setCostFixPersonal( "0" );
                     paymentDetailVO.setBillAmountCompany( adjustmentDetailVO.getBillAmountCompany() );
                     paymentDetailVO.setBillAmountPersonal( adjustmentDetailVO.getBillAmountPersonal() );
                     paymentDetailVO.setCostAmountCompany( adjustmentDetailVO.getCostAmountCompany() );
                     paymentDetailVO.setCostAmountPersonal( adjustmentDetailVO.getCostAmountPersonal() );
                     paymentDetailVO.setDeleted( "1" );
                     paymentDetailVO.setStatus( PaymentDetailVO.TRUE );
                     paymentDetailVO.setCreateBy( paymentBatchVO.getCreateBy() );
                     paymentDetailVO.setModifyBy( paymentBatchVO.getModifyBy() );

                     if ( KANUtil.filterEmpty( adjustmentDetailVO.getBillAmountCompany() ) != null && Double.valueOf( adjustmentDetailVO.getBillAmountCompany() ) > 0 )
                     {
                        billAmountCompany = billAmountCompany + Double.valueOf( adjustmentDetailVO.getBillAmountCompany() );
                     }

                     billAmountPersonal = billAmountPersonal + Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() );

                     if ( KANUtil.filterEmpty( adjustmentDetailVO.getCostAmountCompany() ) != null && Double.valueOf( adjustmentDetailVO.getCostAmountCompany() ) > 0 )
                     {
                        costAmountCompany = costAmountCompany + Double.valueOf( adjustmentDetailVO.getCostAmountCompany() );
                     }

                     costAmountPersonal = costAmountPersonal + Double.valueOf( adjustmentDetailVO.getCostAmountPersonal() );

                     if ( KANUtil.filterEmpty( itemVO.getPersonalTax() ) != null && itemVO.getPersonalTax().equals( "1" ) )
                     {
                        if ( KANUtil.filterEmpty( itemVO.getItemId() ) != null && itemVO.getItemId().trim().equals( "18" ) )
                        {
                           annualBonus = annualBonus + Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() );
                        }
                        else
                        {
                           addtionalBillAmountPersonal = addtionalBillAmountPersonal + Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() )
                                 - Double.valueOf( adjustmentDetailVO.getCostAmountPersonal() );

                           paymentDetailVO.setAddtionalBillAmountPersonal( String.valueOf( Double.valueOf( adjustmentDetailVO.getBillAmountPersonal() )
                                 - Double.valueOf( adjustmentDetailVO.getCostAmountPersonal() ) ) );
                        }
                     }

                     paymentDTO.getPaymentDetailVOs().add( paymentDetailVO );
                  }
               }
            }

            // ���û���ֵ
            paymentHeaderVO.setBillAmountCompany( String.valueOf( billAmountCompany ) );
            paymentHeaderVO.setBillAmountPersonal( String.valueOf( billAmountPersonal ) );
            paymentHeaderVO.setCostAmountCompany( String.valueOf( costAmountCompany ) );
            paymentHeaderVO.setCostAmountPersonal( String.valueOf( costAmountPersonal ) );

            double base = 0;
            if ( KANUtil.filterEmpty( this.getEmployeeVO().getResidencyType(), "0" ) != null
                  && ( this.getEmployeeVO().getResidencyType().equals( "5" ) || this.getEmployeeVO().getResidencyType().equals( "6" ) ) )
            {
               if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBaseForeigner() ) != null )
               {
                  base = Double.valueOf( incomeTaxBaseVO.getBaseForeigner() );
               }
            }
            else
            {
               if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBase() ) != null )
               {
                  base = Double.valueOf( incomeTaxBaseVO.getBase() );
               }
            }

            // Ӧ˰���ʣ��Ѽ���˰����������
            double taxBase = getValue( "1" ) + addtionalBillAmountPersonal - base + taxAgentAmountPersonal;

            if ( annualBonus > 0 && taxBase < 0 )
            {
               annualBonus = annualBonus + taxBase;

               if ( annualBonus > 0 )
               {
                  addtionalBillAmountPersonal = addtionalBillAmountPersonal - taxBase;
               }
               else
               {
                  addtionalBillAmountPersonal = addtionalBillAmountPersonal - annualBonus;
               }
            }

            // Ӧ˰���ʣ�δ����˰����������
            paymentHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( addtionalBillAmountPersonal ) );
            // ����˰����            
            paymentHeaderVO.setTaxAgentAmountPersonal( String.valueOf( taxAgentAmountPersonal ) );

            // ��˰
            double personalTax = Double.valueOf( KANUtil.round( taxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( taxBase ) / 100
                  - incomeTaxRangeDTO.getDeductByBase( taxBase ) - getValue( "2" );

            if ( annualBonus > 0 )
            {
               // ���ս�Ӧ˰��
               double annualBonusBase = annualBonus / 12;
               personalTax = personalTax + Double.valueOf( KANUtil.round( annualBonus, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( annualBonusBase ) / 100
                     - incomeTaxRangeDTO.getDeductByBase( annualBonusBase );
            }

            // ���ø�˰
            paymentHeaderVO.setTaxAmountPersonal( KANUtil.round( personalTax, taxAccuracy, taxRound ) );

            // ����PaymentHeaderVO
            paymentDTO.setPaymentHeaderVO( paymentHeaderVO );

            this.getPaymentDTOs().add( paymentDTO );
         }
      }

      return true;
   }

   // н����� - ����
   public boolean calculatePaymentAdjustment( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      if ( this.getPaymentAdjustmentDTOs() != null && this.getPaymentAdjustmentDTOs().size() > 0 )
      {
         // ��ʼ����˰����
         final IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
         // ��ʼ����˰����
         final IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();

         // ��ʼ����˰�ľ��ȼ���ȡ
         final String taxAccuracy = incomeTaxBaseVO.getAccuracy();
         final String taxRound = incomeTaxBaseVO.getRound();

         // ������������
         for ( PaymentAdjustmentDTO paymentAdjustmentDTO : this.getPaymentAdjustmentDTOs() )
         {
            // ��ʼ��PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO tempPaymentAdjustmentHeaderVO = paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO();

            // δ����׼�ĵ���������
            if ( KANUtil.filterEmpty( tempPaymentAdjustmentHeaderVO.getStatus() ) != null
                  && ( tempPaymentAdjustmentHeaderVO.getStatus().equals( "1" ) || tempPaymentAdjustmentHeaderVO.getStatus().equals( "2" ) ) )
            {
               // ��ʼ������ֵ
               double billAmountPersonal = 0;
               double costAmountPersonal = 0;
               // ���Ӻϼƣ����ڸ�˰����
               double addtionalBillAmountPersonal = 0;
               // ����˰����
               double taxAgentAmountPersonal = 0;
               // ���ս�
               double annualBonus = 0;

               if ( paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().size() > 0 )
               {
                  for ( PaymentAdjustmentDetailVO paymentAdjustmentDetailVO : paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() )
                  {
                     // ��ȡ��ǰ�̱���Ŀ
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( tempPaymentAdjustmentHeaderVO.getAccountId() ).getItemVOByItemId( paymentAdjustmentDetailVO.getItemId() );

                     // �����ĿΪ�գ�������һ��ѭ��
                     if ( itemVO == null )
                     {
                        continue;
                     }

                     // �����ĿΪ����˰���ۼ�Ӧ˰н���������һ��ѭ��
                     if ( KANUtil.filterEmpty( itemVO.getPersonalTaxAgent() ) != null && itemVO.getPersonalTaxAgent().equals( "1" ) )
                     {
                        if ( ( KANUtil.filterEmpty( paymentAdjustmentDetailVO.getBillAmountPersonal() ) != null && Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() ) != 0 )
                              || ( KANUtil.filterEmpty( paymentAdjustmentDetailVO.getCostAmountPersonal() ) != null && Double.valueOf( paymentAdjustmentDetailVO.getCostAmountPersonal() ) != 0 ) )
                        {
                           taxAgentAmountPersonal = taxAgentAmountPersonal + Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() )
                                 - Double.valueOf( paymentAdjustmentDetailVO.getCostAmountPersonal() );
                        }

                        continue;
                     }

                     if ( ( KANUtil.filterEmpty( paymentAdjustmentDetailVO.getBillAmountPersonal() ) != null && Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() ) > 0 )
                           || ( KANUtil.filterEmpty( paymentAdjustmentDetailVO.getCostAmountPersonal() ) != null && Double.valueOf( paymentAdjustmentDetailVO.getCostAmountPersonal() ) > 0 ) )
                     {
                        billAmountPersonal = billAmountPersonal + Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() );

                        costAmountPersonal = costAmountPersonal + Double.valueOf( paymentAdjustmentDetailVO.getCostAmountPersonal() );

                        if ( KANUtil.filterEmpty( itemVO.getPersonalTax() ) != null && itemVO.getPersonalTax().equals( "1" ) )
                        {
                           if ( KANUtil.filterEmpty( itemVO.getItemId() ) != null && itemVO.getItemId().trim().equals( "18" ) )
                           {
                              annualBonus = annualBonus + Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() );
                           }
                           else
                           {
                              addtionalBillAmountPersonal = addtionalBillAmountPersonal + Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() )
                                    - Double.valueOf( paymentAdjustmentDetailVO.getCostAmountPersonal() );

                              // �½��Ĺ��ʵ�����ϸ����AddtionalBillAmountPersonal
                              if ( KANUtil.filterEmpty( paymentAdjustmentDetailVO.getAdjustmentDetailId() ) == null )
                              {
                                 paymentAdjustmentDetailVO.setAddtionalBillAmountPersonal( String.valueOf( Double.valueOf( paymentAdjustmentDetailVO.getBillAmountPersonal() )
                                       - Double.valueOf( paymentAdjustmentDetailVO.getCostAmountPersonal() ) ) );
                              }
                           }
                        }
                     }
                  }
               }

               double base = 0;
               if ( KANUtil.filterEmpty( this.getEmployeeVO().getResidencyType(), "0" ) != null
                     && ( this.getEmployeeVO().getResidencyType().equals( "5" ) || this.getEmployeeVO().getResidencyType().equals( "6" ) ) )
               {
                  if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBaseForeigner() ) != null )
                  {
                     base = Double.valueOf( incomeTaxBaseVO.getBaseForeigner() );
                  }
               }
               else
               {
                  if ( KANUtil.filterEmpty( incomeTaxBaseVO.getBase() ) != null )
                  {
                     base = Double.valueOf( incomeTaxBaseVO.getBase() );
                  }
               }

               // Ӧ˰���ʣ��Ѽ���˰����������
               double taxBase = getValue( "1" ) + addtionalBillAmountPersonal - base + taxAgentAmountPersonal;

               if ( annualBonus > 0 && taxBase < 0 )
               {
                  annualBonus = annualBonus + taxBase;

                  if ( annualBonus > 0 )
                  {
                     addtionalBillAmountPersonal = addtionalBillAmountPersonal - taxBase;
                  }
                  else
                  {
                     addtionalBillAmountPersonal = addtionalBillAmountPersonal - annualBonus;
                  }
               }

               // ��˰
               double personalTax = Double.valueOf( KANUtil.round( taxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( taxBase ) / 100
                     - incomeTaxRangeDTO.getDeductByBase( taxBase ) - getValue( "2" );

               if ( annualBonus > 0 )
               {
                  // ���ս�Ӧ˰��
                  double annualBonusBase = annualBonus / 12;
                  personalTax = personalTax + Double.valueOf( KANUtil.round( annualBonus, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( annualBonusBase )
                        / 100 - incomeTaxRangeDTO.getDeductByBase( annualBonusBase );
               }

               // ���ø�������
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setBillAmountPersonal( String.valueOf( billAmountPersonal ) );
               // ���ø���֧��
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setCostAmountPersonal( String.valueOf( costAmountPersonal ) );
               // ���ø�˰
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setTaxAmountPersonal( KANUtil.round( personalTax, taxAccuracy, taxRound ) );
               // ���ø�������ϼ�
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setAddtionalBillAmountPersonal( String.valueOf( addtionalBillAmountPersonal ) );
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setTaxAgentAmountPersonal( String.valueOf( taxAgentAmountPersonal ) );
            }
         }
      }

      return true;
   }

   /***
    * flag == 1 
    * @param flag
    * @return
    */
   private double getValue( final String flag )
   {
      double addtionalBillAmountPersonal = 0;
      double taxAmountPersonal = 0;

      if ( paymentDTOs != null && paymentDTOs.size() > 0 )
      {
         for ( PaymentDTO paymentDTO : paymentDTOs )
         {
            if ( paymentDTO.getPaymentHeaderVO() != null
                  && ( KANUtil.filterEmpty( paymentDTO.getPaymentHeaderVO().getAddtionalBillAmountPersonal() ) != null || KANUtil.filterEmpty( paymentDTO.getPaymentHeaderVO().getTaxAgentAmountPersonal() ) != null ) )
            {
               addtionalBillAmountPersonal = addtionalBillAmountPersonal + Double.valueOf( paymentDTO.getPaymentHeaderVO().getAddtionalBillAmountPersonal() )
                     + Double.valueOf( paymentDTO.getPaymentHeaderVO().getTaxAgentAmountPersonal() );
            }

            if ( paymentDTO.getPaymentHeaderVO() != null && KANUtil.filterEmpty( paymentDTO.getPaymentHeaderVO().getTaxAmountPersonal() ) != null )
            {
               taxAmountPersonal = taxAmountPersonal + Double.valueOf( paymentDTO.getPaymentHeaderVO().getTaxAmountPersonal() );
            }
         }
      }

      if ( paymentAdjustmentDTOs != null && paymentAdjustmentDTOs.size() > 0 )
      {
         for ( PaymentAdjustmentDTO paymentAdjustmentDTO : paymentAdjustmentDTOs )
         {
            if ( KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getStatus() ) != null
                  && ( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getStatus().equals( "3" ) || paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getStatus().equals( "5" ) ) )
            {
               if ( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() != null )
               {
                  addtionalBillAmountPersonal = addtionalBillAmountPersonal
                        + Double.valueOf( KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAddtionalBillAmountPersonal() ) != null ? KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAddtionalBillAmountPersonal() )
                              : "0" )
                        + +Double.valueOf( KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getTaxAgentAmountPersonal() ) != null ? KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getTaxAgentAmountPersonal() )
                              : "0" );
               }

               if ( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() != null
                     && KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getTaxAmountPersonal() ) != null )
               {
                  taxAmountPersonal = taxAmountPersonal + Double.valueOf( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getTaxAmountPersonal() );
               }
            }
         }
      }

      if ( KANUtil.filterEmpty( flag ) != null )
      {
         // ��ȡӦ˰����
         if ( flag.trim().equals( "1" ) )
         {
            return addtionalBillAmountPersonal;
         }
         // ��ȡ��˰
         else
         {
            return taxAmountPersonal;
         }
      }
      else
      {
         return 0;
      }
   }

   public PaymentAdjustmentDTO getPaymentAdjustmentDTOByAdjustmentHeaderId( final String adjustmentHeaderId )
   {
      if ( KANUtil.filterEmpty( adjustmentHeaderId ) != null && this.getPaymentAdjustmentDTOs() != null && this.getPaymentAdjustmentDTOs().size() > 0 )
      {
         for ( PaymentAdjustmentDTO paymentAdjustmentDTO : this.getPaymentAdjustmentDTOs() )
         {
            if ( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() != null
                  && KANUtil.filterEmpty( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAdjustmentHeaderId() ) != null
                  && paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAdjustmentHeaderId().equals( adjustmentHeaderId ) )
            {
               return paymentAdjustmentDTO;
            }
         }
      }

      return null;
   }

   // ���PaymentDTO
   public void addPaymentDTO( final PaymentDTO paymentDTO )
   {
      if ( paymentDTOs.size() == 0 )
      {
         paymentDTOs.add( paymentDTO );
      }
      else
      {
         for ( PaymentDTO tempPaymentDTO : paymentDTOs )
         {
            if ( tempPaymentDTO.getPaymentHeaderVO() != null && KANUtil.filterEmpty( tempPaymentDTO.getPaymentHeaderVO().getPaymentHeaderId() ) != null
                  && tempPaymentDTO.getPaymentHeaderVO().getPaymentHeaderId().equals( paymentDTO.getPaymentHeaderVO().getPaymentHeaderId() ) )
            {
               return;
            }
         }

         paymentDTOs.add( paymentDTO );
      }
   }

   // ���PaymentAdjustmentDTO
   public void addPaymentAdjustmentDTO( final PaymentAdjustmentDTO paymentAdjustmentDTO )
   {
      if ( paymentAdjustmentDTOs.size() == 0 )
      {
         paymentAdjustmentDTOs.add( paymentAdjustmentDTO );
      }
      else
      {
         for ( PaymentAdjustmentDTO tempPaymentAdjustmentDTO : paymentAdjustmentDTOs )
         {
            if ( tempPaymentAdjustmentDTO.getPaymentAdjustmentHeaderVO() != null
                  && KANUtil.filterEmpty( tempPaymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAdjustmentHeaderId() ) != null
                  && tempPaymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAdjustmentHeaderId().equals( paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().getAdjustmentHeaderId() ) )
            {
               return;
            }
         }

         paymentAdjustmentDTOs.add( paymentAdjustmentDTO );
      }
   }
}
