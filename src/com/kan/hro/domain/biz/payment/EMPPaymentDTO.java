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
 * 项目名称：HRO_V1  
 * 类名称：EMPPaymentDTO  
 * 类描述：  
 * 创建人：Kevin Jin  
 * 创建时间：2013-12-03 
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

   // EntityId 独立报税标识
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

   // 薪酬计算
   public boolean calculatePayment( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      // 工资结算
      if ( this.getSettlementDTOs() != null && this.getSettlementDTOs().size() > 0 )
      {
         // 遍历结算数据
         for ( SettlementDTO settlementDTO : this.getSettlementDTOs() )
         {
            // 初始化ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = settlementDTO.getClientOrderHeaderVO();

            // 初始化EmployeeContractVO
            final EmployeeContractVO employeeContractVO = settlementDTO.getEmployeeContractVO();

            // 初始化个税起征
            IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( employeeContractVO.getIncomeTaxBaseId() );

            if ( incomeTaxBaseVO == null )
            {
               incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( clientOrderHeaderVO.getIncomeTaxBaseId() );

               if ( incomeTaxBaseVO == null )
               {
                  incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
               }
            }

            // 初始化个税区间
            IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( employeeContractVO.getIncomeTaxRangeHeaderId() );

            if ( incomeTaxRangeDTO == null )
            {
               incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() );

               if ( incomeTaxRangeDTO == null )
               {
                  incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();
               }
            }

            // 初始化个税的精度及截取
            final String taxAccuracy = String.valueOf( incomeTaxBaseVO.getAccuracy() != null ? ( Integer.valueOf( incomeTaxBaseVO.getAccuracy() ) - 1 ) : 0 );
            final String taxRound = incomeTaxBaseVO.getRound();

            // 初始化TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = settlementDTO.getTimesheetHeaderVO();

            // 初始化ServiceContractVO
            final ServiceContractVO serviceContractVO = settlementDTO.getServiceContractVO();

            if ( serviceContractVO != null )
            {
               // 获取需要处理的薪酬月份
               final String monthly = serviceContractVO.getMonthly();

               // 初始化PaymentDTO
               final PaymentDTO paymentDTO = new PaymentDTO();

               /** 设置薪酬台帐 */
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

               // 初始化BankVO
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

               // 初始化SalaryVendorId
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

               // 初始化汇总值
               double billAmountCompany = 0;
               double billAmountPersonal = 0;
               double costAmountCompany = 0;
               double costAmountPersonal = 0;
               // 附加合计，用于个税基数
               double addtionalBillAmountPersonal = 0;
               // 代扣税工资
               double taxAgentAmountPersonal = 0;
               // 年终奖
               double annualBonus = 0;

               if ( settlementDTO.getOrderDetailVOs() != null && settlementDTO.getOrderDetailVOs().size() > 0 )
               {
                  for ( OrderDetailVO orderDetailVO : settlementDTO.getOrderDetailVOs() )
                  {
                     // 获取当前商保科目
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( serviceContractVO.getAccountId() ).getItemVOByItemId( orderDetailVO.getItemId() );

                     // 如果科目为空，跳出这一次循环
                     if ( itemVO == null )
                     {
                        continue;
                     }

                     // 如果科目为代扣税，累计应税薪酬后跳出这一次循环
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
                        /** 设置薪酬台帐明细 */
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

               // 设置汇总值
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

               // 应税工资（已减个税起征基数）
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

               // 应税工资（未减个税起征基数）
               paymentHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( addtionalBillAmountPersonal ) );

               // 代扣税工资
               paymentHeaderVO.setTaxAgentAmountPersonal( String.valueOf( taxAgentAmountPersonal ) );

               // 个税
               double personalTax = Double.valueOf( KANUtil.round( taxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( taxBase ) / 100
                     - incomeTaxRangeDTO.getDeductByBase( taxBase ) - getValue( "2" );

               if ( annualBonus > 0 )
               {
                  // 年终奖个税
                  double annualBonusTax = 0;
                  // 年终奖应税额
                  double annualBonusTaxBase = annualBonus;

                  if ( addtionalBillAmountPersonal + taxAgentAmountPersonal < base )
                  {
                     annualBonusTaxBase = annualBonus - ( base - addtionalBillAmountPersonal );
                  }

                  annualBonusTax = Double.valueOf( KANUtil.round( annualBonusTaxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( annualBonusTaxBase / 12 )
                        / 100 - incomeTaxRangeDTO.getDeductByBase( annualBonusTaxBase / 12 );
                  paymentHeaderVO.setAnnualBonusTax( KANUtil.round( annualBonusTax, taxAccuracy, taxRound ) );
                  // 实发年终奖
                  paymentHeaderVO.setRemark5( KANUtil.round( annualBonus - annualBonusTax, taxAccuracy, taxRound ) );
               }

               // 年终奖
               paymentHeaderVO.setAnnualBonus( String.valueOf( annualBonus ) );

               // 设置个税
               paymentHeaderVO.setTaxAmountPersonal( KANUtil.round( personalTax, taxAccuracy, taxRound ) );

               // 设置PaymentHeaderVO
               paymentDTO.setPaymentHeaderVO( paymentHeaderVO );

               this.getPaymentDTOs().add( paymentDTO );
            }
         }
      }

      // 工资调整结算
      if ( this.getAdjustmentDTOs() != null && this.getAdjustmentDTOs().size() > 0 )
      {
         // 遍历结算数据
         for ( AdjustmentDTO adjustmentDTO : this.getAdjustmentDTOs() )
         {
            // 初始化ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = adjustmentDTO.getClientOrderHeaderVO();

            // 初始化EmployeeContractVO
            final EmployeeContractVO employeeContractVO = adjustmentDTO.getEmployeeContractVO();

            // 初始化个税起征
            IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( employeeContractVO.getIncomeTaxBaseId() );

            if ( incomeTaxBaseVO == null )
            {
               incomeTaxBaseVO = KANConstants.getIncomeTaxBaseVOByBaseId( clientOrderHeaderVO.getIncomeTaxBaseId() );

               if ( incomeTaxBaseVO == null )
               {
                  incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
               }
            }

            // 初始化个税区间
            IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( employeeContractVO.getIncomeTaxRangeHeaderId() );

            if ( incomeTaxRangeDTO == null )
            {
               incomeTaxRangeDTO = KANConstants.getIncomeTaxRangeDTOByHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() );

               if ( incomeTaxRangeDTO == null )
               {
                  incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();
               }
            }

            // 初始化个税的精度及截取
            final String taxAccuracy = incomeTaxBaseVO.getAccuracy();
            final String taxRound = incomeTaxBaseVO.getRound();

            final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentDTO.getAdjustmentHeaderVO();

            // 获取需要处理的薪酬月份
            final String monthly = adjustmentHeaderVO.getMonthly();

            // 初始化PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();

            /** 设置薪酬台帐 */
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
            // 初始化BankVO
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

            // 初始化汇总值
            double billAmountCompany = 0;
            double billAmountPersonal = 0;
            double costAmountCompany = 0;
            double costAmountPersonal = 0;
            // 附加合计，用于个税基数
            double addtionalBillAmountPersonal = 0;
            // 代扣税工资
            double taxAgentAmountPersonal = 0;
            // 年终奖
            double annualBonus = 0;

            if ( adjustmentDTO.getAdjustmentDetailVOs() != null && adjustmentDTO.getAdjustmentDetailVOs().size() > 0 )
            {
               for ( AdjustmentDetailVO adjustmentDetailVO : adjustmentDTO.getAdjustmentDetailVOs() )
               {
                  // 获取当前商保科目
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( adjustmentHeaderVO.getAccountId() ).getItemVOByItemId( adjustmentDetailVO.getItemId() );

                  // 如果科目为空，跳出这一次循环
                  if ( itemVO == null )
                  {
                     continue;
                  }

                  // 如果科目为代扣税，累计应税薪酬后跳出这一次循环
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
                     /** 设置薪酬台帐明细 */
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

            // 设置汇总值
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

            // 应税工资（已减个税起征基数）
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

            // 应税工资（未减个税起征基数）
            paymentHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( addtionalBillAmountPersonal ) );
            // 代扣税工资            
            paymentHeaderVO.setTaxAgentAmountPersonal( String.valueOf( taxAgentAmountPersonal ) );

            // 个税
            double personalTax = Double.valueOf( KANUtil.round( taxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( taxBase ) / 100
                  - incomeTaxRangeDTO.getDeductByBase( taxBase ) - getValue( "2" );

            if ( annualBonus > 0 )
            {
               // 年终奖应税额
               double annualBonusBase = annualBonus / 12;
               personalTax = personalTax + Double.valueOf( KANUtil.round( annualBonus, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( annualBonusBase ) / 100
                     - incomeTaxRangeDTO.getDeductByBase( annualBonusBase );
            }

            // 设置个税
            paymentHeaderVO.setTaxAmountPersonal( KANUtil.round( personalTax, taxAccuracy, taxRound ) );

            // 设置PaymentHeaderVO
            paymentDTO.setPaymentHeaderVO( paymentHeaderVO );

            this.getPaymentDTOs().add( paymentDTO );
         }
      }

      return true;
   }

   // 薪酬计算 - 调整
   public boolean calculatePaymentAdjustment( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      if ( this.getPaymentAdjustmentDTOs() != null && this.getPaymentAdjustmentDTOs().size() > 0 )
      {
         // 初始化个税起征
         final IncomeTaxBaseVO incomeTaxBaseVO = KANConstants.getValidIncomeTaxBaseVO();
         // 初始化个税区间
         final IncomeTaxRangeDTO incomeTaxRangeDTO = KANConstants.getValidIncomeTaxRangeDTO();

         // 初始化个税的精度及截取
         final String taxAccuracy = incomeTaxBaseVO.getAccuracy();
         final String taxRound = incomeTaxBaseVO.getRound();

         // 遍历调整数据
         for ( PaymentAdjustmentDTO paymentAdjustmentDTO : this.getPaymentAdjustmentDTOs() )
         {
            // 初始化PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO tempPaymentAdjustmentHeaderVO = paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO();

            // 未被批准的调整数计算
            if ( KANUtil.filterEmpty( tempPaymentAdjustmentHeaderVO.getStatus() ) != null
                  && ( tempPaymentAdjustmentHeaderVO.getStatus().equals( "1" ) || tempPaymentAdjustmentHeaderVO.getStatus().equals( "2" ) ) )
            {
               // 初始化汇总值
               double billAmountPersonal = 0;
               double costAmountPersonal = 0;
               // 附加合计，用于个税基数
               double addtionalBillAmountPersonal = 0;
               // 代扣税工资
               double taxAgentAmountPersonal = 0;
               // 年终奖
               double annualBonus = 0;

               if ( paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() != null && paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs().size() > 0 )
               {
                  for ( PaymentAdjustmentDetailVO paymentAdjustmentDetailVO : paymentAdjustmentDTO.getPaymentAdjustmentDetailVOs() )
                  {
                     // 获取当前商保科目
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( tempPaymentAdjustmentHeaderVO.getAccountId() ).getItemVOByItemId( paymentAdjustmentDetailVO.getItemId() );

                     // 如果科目为空，跳出这一次循环
                     if ( itemVO == null )
                     {
                        continue;
                     }

                     // 如果科目为代扣税，累计应税薪酬后跳出这一次循环
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

                              // 新建的工资调整明细设置AddtionalBillAmountPersonal
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

               // 应税工资（已减个税起征基数）
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

               // 个税
               double personalTax = Double.valueOf( KANUtil.round( taxBase, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( taxBase ) / 100
                     - incomeTaxRangeDTO.getDeductByBase( taxBase ) - getValue( "2" );

               if ( annualBonus > 0 )
               {
                  // 年终奖应税额
                  double annualBonusBase = annualBonus / 12;
                  personalTax = personalTax + Double.valueOf( KANUtil.round( annualBonus, taxAccuracy, taxRound ) ) * incomeTaxRangeDTO.getPercentageByBase( annualBonusBase )
                        / 100 - incomeTaxRangeDTO.getDeductByBase( annualBonusBase );
               }

               // 设置个人收入
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setBillAmountPersonal( String.valueOf( billAmountPersonal ) );
               // 设置个人支出
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setCostAmountPersonal( String.valueOf( costAmountPersonal ) );
               // 设置个税
               paymentAdjustmentDTO.getPaymentAdjustmentHeaderVO().setTaxAmountPersonal( KANUtil.round( personalTax, taxAccuracy, taxRound ) );
               // 设置附加收入合计
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
         // 获取应税基数
         if ( flag.trim().equals( "1" ) )
         {
            return addtionalBillAmountPersonal;
         }
         // 获取个税
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

   // 添加PaymentDTO
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

   // 添加PaymentAdjustmentDTO
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
