package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PayslipHeaderView extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = 5917628317712118027L;

   // ���PaymentHeaderId����AdjustmentHeaderId
   private String headerId;

   // ��ԱID
   private String employeeId;

   // ����Э��ID
   private String contractId;

   // �·�
   private String monthly;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // ����Id
   private String orderId;

   // ��Ӧ��ID
   private String vendorId;

   // ��Ӧ�����ƣ����ģ�
   private String vendorNameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String vendorNameEN;

   private String orderDetailId;

   // ��Ա���������ģ�
   private String employeeNameZH;

   // ��Ա������Ӣ�ģ�
   private String employeeNameEN;

   // ����Id
   private String bankId;

   // �������ƣ����ģ�
   private String bankNameZH;

   // �������ƣ�Ӣ�ģ�
   private String bankNameEN;

   // ֧������
   private String bankBranch;

   // �����˻�
   private String bankAccount;

   // н�꿪ʼ����
   private String startDate;

   // н���������
   private String endDate;

   // ֤������
   private String certificateType;

   // ֤������
   private String certificateNumber;

   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ��������룩
   private String billAmountPersonal;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;

   // �ϼƣ�����֧����
   private String costAmountPersonal;

   // ���ʸ�˰���ϼƣ������̨�ʻ������Tax
   private String salaryTax;

   // ���ս�
   private String annualBonus;

   // ���ս���˰
   private String annualBonusTax;

   // ���Ӻϼƣ��������룩������˰ǰ�ӵĽ��
   private String addtionalBillAmountPersonal;

   // ����˰����
   private String taxAgentAmountPersonal;

   /**
    * For Application
    */
   // �籣��˾�ϼ�
   private String sbAmountCompany;

   // �籣���˺ϼ�
   private String sbAmountPersonal;

   // �籣�ۿ�ϼ�
   private String sbAmount;

   // ְλ����
   private String tempPositionIds;

   // �������
   private String clientNO;

   // ��������
   private String tempBranchIds;

   // �ϼ���������
   private String tempParentBranchIds;

   // �����ͬģ��ID
   private String templateId;

   // �����ͬģ������
   private String templateName;

   // ��˰�걨����
   private String cityId;

   //��������
   private String residencyType;

   //��Ŀ������
   private String itemGroupId;

   //���ʲ��
   private String salaryBalance;

   //��ͬ���ʺϼ�
   private String salaryBase;

   //��������
   private String currency;

   // �ɱ�����/ Ӫ�ղ���
   private String settlementBranch;

   // ����
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   // ְλ
   private List< MappingVO > positions = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   private List< MappingVO > banks = new ArrayList< MappingVO >();

   // Ա����Ϣ�Զ����ֶ�
   private String employeeRemark1;

   // �Ͷ���ͬ�Զ����ֶ�
   private String contractRemark1;

   // ��ͬ��ʼ����
   private String contractStartDate;

   // н����㿪ʼ��
   private String circleStartDay;

   // н����������
   private String circleEndDay;

   // �״ι���ʱ��
   private String startWorkDate;

   // �����ʱ��
   private String lastWorkDate;

   // �����һ�ν���ǿ������
   private String fisrtPayMPF;

   // ����Զ����ֶ�
   private Map< String, String > dynaColumns = new HashMap< String, String >();

   @Override
   public String getEncodedId() throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.employeeId = "";
      this.contractId = "";
      this.monthly = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.orderId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.bankId = "";
      this.bankNameZH = "";
      this.bankNameEN = "";
      this.bankAccount = "";
      this.startDate = "";
      this.endDate = "";
      this.certificateType = "";
      this.certificateNumber = "";
      this.billAmountCompany = "";
      this.billAmountPersonal = "";
      this.costAmountCompany = "";
      this.costAmountPersonal = "";
      this.salaryTax = "";
      this.addtionalBillAmountPersonal = "";
      this.itemGroupId = "";
      this.salaryBalance = "";
      this.orderDetailId = "";
      this.currency = "0";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // No Use
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
      this.branchs = KANConstants.getKANAccountConstants( super.getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.banks = KANConstants.getKANAccountConstants( getAccountId() ).getBanks( getLocale().getLanguage(), getCorpId() );
   }

   public final String getHeaderId()
   {
      return headerId;
   }

   public final void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getOrderId()
   {
      return KANUtil.filterEmpty( orderId );
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public String getStartDate()
   {
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate;
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getCertificateType()
   {
      return certificateType;
   }

   public void setCertificateType( String certificateType )
   {
      this.certificateType = certificateType;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public final String getBankNameZH()
   {
      return bankNameZH;
   }

   public final void setBankNameZH( String bankNameZH )
   {
      this.bankNameZH = bankNameZH;
   }

   public final String getBankNameEN()
   {
      return bankNameEN;
   }

   public final void setBankNameEN( String bankNameEN )
   {
      this.bankNameEN = bankNameEN;
   }

   public final String getBankName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getBankNameZH();
         }
         else
         {
            return this.getBankNameEN();
         }
      }
      else
      {
         return this.getBankNameZH();
      }
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
   }

   public String getSalaryTax()
   {
      return salaryTax;
   }

   public void setSalaryTax( String salaryTax )
   {
      this.salaryTax = salaryTax;
   }

   public final String getAddtionalBillAmountPersonal()
   {
      return addtionalBillAmountPersonal;
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public String getDecodeBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public String getDecodeCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public String getDecodeCurrency()
   {
      return getCurrencyByOrderId( currency );
   }

   public String getFormatTaxAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getSalaryTax() ) ) );
   }

   public String getFormatBeforeTaxSalary()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getAddtionalBillAmountPersonal() ) ) );
   }

   // Ӧ������
   public String getAccruedWages()
   {
      return formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAddtionalBillAmountPersonal() ) ) - Double.valueOf( formatNumber( this.getSbAmount() ) ) ) );
   }

   public String getFormatAccruedWages()
   {
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAddtionalBillAmountPersonal() ) ) - Double.valueOf( formatNumber( this.getSbAmount() ) ) ) );
   }

   // Ӧ˰����
   public String getTaxableSalary()
   {
      if ( formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) ) != null )
      {
         return formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) );
      }
      return formatNumber( "0" );
   }

   public String getFormatTaxableSalary()
   {
      if ( formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) ) != null )
      {
         return getCurrencyByOrderId( currency )
               + formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) );
      }
      return getCurrencyByOrderId( currency ) + formatNumber( "0" );
   }

   // ����Ѹ���(����iClick)
   public String getUnionFeePersonal()
   {
      return formatNumber( "0" );
   }

   // ����Ѹ���(����iClick)���Ͻ�����
   public String getFormatUnionFeePersonal()
   {
      return getCurrencyByOrderId( currency ) + getUnionFeePersonal();
   }

   // ����ѹ�˾(����iClick)
   public String getUnionFeeCompany()
   {
      // ָ������ʵ��
      final String[] entityArray = new String[] { "101", "103", "105" };
      if ( !ArrayUtils.contains( entityArray, entityId ) )
      {
         return formatNumber( "0" );
      }

      return formatNumber( String.valueOf( Double.valueOf( getTaxableSalary() ) * 0.02 ) );
   }

   // ����ѹ�˾(����iClick)���Ͻ�����
   public String getFormatUnionFeeCompany()
   {
      return getCurrencyByOrderId( currency ) + getUnionFeeCompany();
   }

   // ��ȡǿ���������
   @SuppressWarnings("unchecked")
   private String getMPFPayDay()
   {
      // �Ͷ���ͬ�Զ����ֶ�
      final Map< String, Object > contractDefineColumnMap = new HashMap< String, Object >();
      contractDefineColumnMap.putAll( JSONObject.fromObject( contractRemark1 ) );
      return contractDefineColumnMap.get( "mpfqijiaori" ) == null ? null : contractDefineColumnMap.get( "mpfqijiaori" ).toString();
   }

   private String getSalaryEndDate()
   {
      if ( KANUtil.filterEmpty( lastWorkDate ) != null )
      {
         String tmpMonthly = KANUtil.getMonthlyByCondition( circleEndDay, lastWorkDate );
         if ( monthly.equals( tmpMonthly ) )
         {
            return lastWorkDate;
         }
      }

      return KANUtil.getEndDate( monthly, circleEndDay );
   }

   // ��ȡ�Ƿ�Ϊ��һ�ν���ǿ���𣨽���iClick��
   public boolean isFirstPayMPF()
   {
      // ǿ���������
      final String mpfStartDate = getMPFPayDay();
      if ( mpfStartDate == null )
         return false;

      // н�ʼ��㿪ʼ��
      final String salaryStartDate = KANUtil.getStartDate( monthly, circleStartDay );
      // н�ʼ��������
      final String salaryEndDate = getSalaryEndDate();
      // ���ϵ�һ�ν�������
      if ( KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) )
            && KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) )
         return true;

      return false;
   }

   // ��ȡ��н����
   public int getSumSalaryMonthNum()
   {
      int num = 0;
      if ( isFirstPayMPF() )
      {
         final String mpfStartDate = getMPFPayDay();
         final Calendar tempCalendar = KANUtil.createCalendar( mpfStartDate );
         // 60�����Ҫ����ǿ��������
         tempCalendar.add( Calendar.DATE, -59 );

         String tmpMonthly = "";
         for ( int i = 1; i <= 2; i++ )
         {
            tmpMonthly = KANUtil.getMonthly( monthly, -i );
            final String salaryStartDate = KANUtil.getStartDate( tmpMonthly, circleStartDay );
            final String salaryEndDate = getSalaryEndDate();

            if ( KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) >= KANUtil.getDays( tempCalendar.getTime() )
                  || ( KANUtil.getDays( tempCalendar.getTime() ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) && KANUtil.getDays( tempCalendar.getTime() ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) ) )
            {
               num++;
            }
         }
      }

      return num;
   }

   // ǿ����˾(����iClick)
   // ��˾����ÿ�¶���Ҫ���ɣ���һ�ι�н����֮ǰ���ܺͣ�ǰһ��/ǰ���£�ȡ���棩
   // ǿ���𣨹�˾��= Ӧ˰����  * 0.05 > 1500 ? 1500 : Ӧ˰����  * 0.05
   public String getMPFCompany()
   {
      // ָ������ʵ��
      final String[] entityArray = new String[] { "91", "93", "95", "99" };
      if ( !ArrayUtils.contains( entityArray, entityId ) )
      {
         return "0";
      }

      // ǿ���������
      final String mpfStartDate = getMPFPayDay();
      if ( mpfStartDate == null )
      {
         return formatNumber( "0" );
      }

      // ��ʵ������ֵ
      double result = 0;

      // ���ڽ��ɷ�Χ
      final String salaryStartDate = KANUtil.getStartDate( monthly, circleStartDay );
      final String salaryEndDate = getSalaryEndDate();
      if ( KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( mpfStartDate ) )
            || ( KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) && KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) ) )
      {
         // ����ǵ�һ�ν��ɣ���Ҫ�ۺ�ǰһ��/ǰ���µĽ��ɽ��
         if ( isFirstPayMPF() && fisrtPayMPF != null )
         {
            result = result + Double.valueOf( fisrtPayMPF );
         }

         result = result + ( Double.valueOf( getTaxableSalary() ) * 0.05 > 1500 ? 1500 : Double.valueOf( getTaxableSalary() ) * 0.05 );
      }

      return formatNumber( String.valueOf( result ) );
   }

   // ǿ�������(����iClick)
   // ��ְ60������������ɣ�60�����������
   // ǿ���𣨸��ˣ�= Ӧ˰����  < 7100 ? 0 else if Ӧ˰����  >30000 then 1500 else 0.05*Ӧ˰����  
   public String getMPFPersonal()
   {
      // ָ������ʵ��
      final String[] entityArray = new String[] { "91", "93", "95", "99" };
      if ( !ArrayUtils.contains( entityArray, entityId ) )
      {
         return "0";
      }

      // ǿ���������
      final String mpfStartDate = getMPFPayDay();
      if ( mpfStartDate == null )
      {
         return formatNumber( "0" );
      }

      // ���ڽ��ɷ�Χ
      final String salaryStartDate = KANUtil.getStartDate( monthly, circleStartDay );
      final String salaryEndDate = getSalaryEndDate();

      if ( KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( mpfStartDate ) )
            || ( KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) && KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) ) )
      {
         double tempTaxableSalary = Double.valueOf( getTaxableSalary() );
         if ( tempTaxableSalary < 7100 )
         {
            return formatNumber( "0" );
         }
         else if ( tempTaxableSalary > 30000 )
         {
            return formatNumber( "1500" );
         }
         else
         {
            return formatNumber( String.valueOf( tempTaxableSalary * 0.05 ) );
         }
      }

      return formatNumber( "0" );
   }

   // ǿ����˾(����iClick)���Ͻ�����
   public String getFormatMPFCompany()
   {
      return getCurrencyByOrderId( currency ) + getMPFCompany();
   }

   // ǿ�������(����iClick)���Ͻ�����
   public String getFormatMPFPersonal()
   {
      return getCurrencyByOrderId( currency ) + getMPFPersonal();
   }

   public String getSbAmount()
   {
      return sbAmount;
   }

   public void setSbAmount( String sbAmount )
   {
      this.sbAmount = sbAmount;
   }

   public void addSbAmount( String sbAmount )
   {
      this.sbAmount = String.valueOf( Double.valueOf( this.sbAmount == null ? "0" : this.sbAmount ) + Double.valueOf( sbAmount == null ? "0" : sbAmount ) );
   }

   public String getFormatAfterTaxSalary()
   {
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
                  - Double.valueOf( getAnnualBonusTax() ) ) );
   }

   // ���ʵ������(iClick)
   public String getFormatIClickAfterTaxSalary()
   {
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
                  - Double.valueOf( getAnnualBonusTax() ) - Double.valueOf( getMPFCompany() ) ) );
   }

   // ��ø��˿�Ŀ�������
   public String getAmountPersonal()
   {
      return formatNumber( String.valueOf( Double.valueOf( getDecodeBillAmountPersonal() ) - Double.valueOf( getDecodeCostAmountPersonal() ) ) );
   }

   public String getFormatAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getDecodeBillAmountPersonal() ) - Double.valueOf( getDecodeCostAmountPersonal() ) ) );
   }

   public void addBillAmountCompany( String billAmountCompany )
   {
      if ( KANUtil.filterEmpty( this.billAmountCompany ) == null )
      {
         this.billAmountCompany = "0";
      }
      this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.billAmountPersonal ) == null )
      {
         this.billAmountPersonal = "0";
      }
      this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      if ( KANUtil.filterEmpty( this.costAmountCompany ) == null )
      {
         this.costAmountCompany = "0";
      }
      this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.costAmountPersonal ) == null )
      {
         this.costAmountPersonal = "0";
      }
      this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
   }

   public void addSbAmountCompany( String sbAmountCompany )
   {
      if ( KANUtil.filterEmpty( this.sbAmountCompany ) == null )
      {
         this.sbAmountCompany = "0";
      }
      this.sbAmountCompany = String.valueOf( Double.valueOf( this.sbAmountCompany ) + Double.valueOf( sbAmountCompany ) );
   }

   public void addSbAmountPersonal( String sbAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.sbAmountPersonal ) == null )
      {
         this.sbAmountPersonal = "0";
      }
      this.sbAmountPersonal = String.valueOf( Double.valueOf( this.sbAmountPersonal ) + Double.valueOf( sbAmountPersonal ) );
   }

   public void addSalaryTax( String salaryTax )
   {
      if ( KANUtil.filterEmpty( this.salaryTax ) == null )
      {
         this.salaryTax = "0";
      }
      this.salaryTax = String.valueOf( Double.valueOf( this.salaryTax ) + Double.valueOf( salaryTax ) );
   }

   public void addTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.taxAgentAmountPersonal ) == null )
      {
         this.taxAgentAmountPersonal = "0";
      }
      this.taxAgentAmountPersonal = String.valueOf( Double.valueOf( this.taxAgentAmountPersonal ) + Double.valueOf( taxAgentAmountPersonal ) );
   }

   public void addAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = String.valueOf( ( KANUtil.filterEmpty( this.addtionalBillAmountPersonal ) != null ? Double.valueOf( this.addtionalBillAmountPersonal ) : 0 )
            + ( KANUtil.filterEmpty( addtionalBillAmountPersonal ) != null ? Double.valueOf( addtionalBillAmountPersonal ) : 0 ) );
   }

   public String getTempPositionIds()
   {
      return tempPositionIds;
   }

   public void setTempPositionIds( String tempPositionIds )
   {
      this.tempPositionIds = tempPositionIds;
   }

   public String getTempBranchIds()
   {
      return tempBranchIds;
   }

   public void setTempBranchIds( String tempBranchIds )
   {
      this.tempBranchIds = tempBranchIds;
   }

   public String getTempParentBranchIds()
   {
      return tempParentBranchIds;
   }

   public void setTempParentBranchIds( String tempParentBranchIds )
   {
      this.tempParentBranchIds = tempParentBranchIds;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   // decodeְλ����
   public String getDecodeTempPositionIds()
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( tempPositionIds ) != null && positions != null && positions.size() > 0 )
      {
         for ( String positionId : tempPositionIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( positionId, positions );
            }
            else
            {
               returnStr = returnStr + "��" + decodeField( positionId, positions );
            }
         }
      }

      return returnStr;
   }

   public String getDecodeJobRole() throws KANException
   {
      if ( KANUtil.filterEmpty( contractRemark1 ) == null )
         return "";
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE" );
      final JSONObject o = JSONObject.fromObject( contractRemark1 );
      if ( o != null && o.get( "jobrole" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "13365" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "jobrole" ).toString(), mappingVOs );
      }

      return "";
   }

   // decode��������
   public String getDecodeTempBranchIds()
   {
      return decodeBranch( tempBranchIds );
   }

   // decode�ϼ���������
   public String getDecodeTempParentBranchIds()
   {
      return decodeBranch( tempParentBranchIds );
   }

   // decode����ʵ��
   public String getDecodeEntity()
   {
      return decodeField( entityId, entitys );
   }

   // decode����ʵ��
   public String getDecodeEntityId()
   {
      return getDecodeEntity();
   }

   public String decodeBranch( final String branchIds )
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( branchIds ) != null && branchs != null && branchs.size() > 0 )
      {
         for ( String branchId : branchIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( branchId, branchs, true );
            }
            else
            {
               returnStr = returnStr + "��" + decodeField( branchId, branchs, true );
            }
         }
      }

      return returnStr;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public String getClientNO()
   {
      return clientNO;
   }

   public void setClientNO( String clientNO )
   {
      this.clientNO = clientNO;
   }

   public String getFormatTaxAgentAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( taxAgentAmountPersonal );
   }

   public void setTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      this.taxAgentAmountPersonal = taxAgentAmountPersonal;
   }

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

   public String getDecodeBankId()
   {
      return decodeField( bankId, banks );
   }

   public String getBankBranch()
   {
      return bankBranch;
   }

   public void setBankBranch( String bankBranch )
   {
      this.bankBranch = bankBranch;
   }

   public String getResidencyType()
   {
      return residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public String getItemGroupId()
   {
      return itemGroupId;
   }

   public void setItemGroupId( String itemGroupId )
   {
      this.itemGroupId = itemGroupId;
   }

   public String getSalaryBalance()
   {
      return formatNumber( salaryBalance );
   }

   public void setSalaryBalance( String salaryBalance )
   {
      this.salaryBalance = salaryBalance;
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public String getTemplateName()
   {
      return templateName;
   }

   public void setTemplateName( String templateName )
   {
      this.templateName = templateName;
   }

   public String getDecodeTemplateId() throws KANException
   {
      return decodeField( this.templateId, KANConstants.getKANAccountConstants( getAccountId() ).getLaborContractTemplates( getLocale().getLanguage(), getCorpId() ) );
   }

   public String getParentBranchs()
   {
      String result = "";
      String positionId = "";
      if ( KANUtil.filterEmpty( this.tempPositionIds ) != null )
      {
         if ( tempPositionIds.contains( "," ) )
         {
            positionId = tempPositionIds.split( "," )[ 0 ];
         }
         else
         {
            positionId = tempPositionIds;
         }
      }
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         final PositionVO positionVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionVOByPositionId( positionId );
         final List< BranchVO > branchVOs = KANConstants.getKANAccountConstants( super.getAccountId() ).getParentBranchVOsByBranchId( positionVO.getBranchId() );
         if ( branchVOs != null && branchVOs.size() > 0 )
         {
            for ( BranchVO branchVO : branchVOs )
            {
               result += branchVO.getNameZH() + ">>";
            }
         }
      }
      return KANUtil.filterEmpty( result ) == null ? "" : result.substring( 0, result.length() - 2 );
   }

   public String getSbAmountCompany()
   {
      return formatNumber( sbAmountCompany );
   }

   public void setSbAmountCompany( String sbAmountCompany )
   {
      this.sbAmountCompany = sbAmountCompany;
   }

   public String getSbAmountPersonal()
   {
      return formatNumber( sbAmountPersonal ).replace( "-", "" );
   }

   public void setSbAmountPersonal( String sbAmountPersonal )
   {
      this.sbAmountPersonal = sbAmountPersonal;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getVendorNameZH()
   {
      return vendorNameZH;
   }

   public void setVendorNameZH( String vendorNameZH )
   {
      this.vendorNameZH = vendorNameZH;
   }

   public String getVendorNameEN()
   {
      return vendorNameEN;
   }

   public void setVendorNameEN( String vendorNameEN )
   {
      this.vendorNameEN = vendorNameEN;
   }

   @SuppressWarnings("unchecked")
   public Map< String, String > getDynaColumns()
   {
      if ( KANUtil.filterEmpty( employeeRemark1 ) != null )
      {
         dynaColumns.putAll( JSONObject.fromObject( employeeRemark1 ) );
      }
      return dynaColumns;
   }

   public void setDynaColumns( Map< String, String > dynaColumns )
   {
      this.dynaColumns = dynaColumns;
   }

   public String getEmployeeRemark1()
   {
      return employeeRemark1;
   }

   public void setEmployeeRemark1( String employeeRemark1 )
   {
      this.employeeRemark1 = employeeRemark1;
   }

   public String getContractStartDate()
   {
      return contractStartDate;
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getCircleEndDay()
   {
      return circleEndDay;
   }

   public void setCircleEndDay( String circleEndDay )
   {
      this.circleEndDay = circleEndDay;
   }

   public String getContractRemark1()
   {
      return contractRemark1;
   }

   public void setContractRemark1( String contractRemark1 )
   {
      this.contractRemark1 = contractRemark1;
   }

   public String getCircleStartDay()
   {
      return circleStartDay;
   }

   public void setCircleStartDay( String circleStartDay )
   {
      this.circleStartDay = circleStartDay;
   }

   public String getCurrency()
   {
      return currency;
   }

   public void setCurrency( String currency )
   {
      this.currency = currency;
   }

   public String getSalaryBase()
   {
      return formatNumber( salaryBase );
   }

   public void setSalaryBase( String salaryBase )
   {
      this.salaryBase = salaryBase;
   }

   public String getStartWorkDate()
   {
      return startWorkDate;
   }

   public void setStartWorkDate( String startWorkDate )
   {
      this.startWorkDate = startWorkDate;
   }

   public String getFisrtPayMPF()
   {
      return fisrtPayMPF;
   }

   public void setFisrtPayMPF( String fisrtPayMPF )
   {
      this.fisrtPayMPF = fisrtPayMPF;
   }

   public String getLastWorkDate()
   {
      return lastWorkDate;
   }

   public void setLastWorkDate( String lastWorkDate )
   {
      this.lastWorkDate = lastWorkDate;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   // �ɱ�����
   public String getDecodeSettlementBranch()
   {
      return decodeField( this.getSettlementBranch(), this.branchs, true );
   }

   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() ) );
   }

   public void setAnnualBonusTax( String annualBonusTax )
   {
      this.annualBonusTax = annualBonusTax;
   }

   public void setAnnualBonus( String annualBonus )
   {
      this.annualBonus = annualBonus;
   }

   /**====================���ֹ�����Start======================**/
   // ���˰ǰ����
   public String getBeforeTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getAddtionalBillAmountPersonal() ) ) );
   }

   // ��ȡ���ս�
   public String getAnnualBonus()
   {
      return formatNumber( annualBonus );
   }

   // ��ô���˰����
   public final String getTaxAgentAmountPersonal()
   {
      return formatNumber( taxAgentAmountPersonal );
   }

   // ��ø�˰�ϼ�
   public String getTaxAmountPersonal()
   {
      return formatNumber( String.valueOf( Double.valueOf( getSalaryTax() ) ) );
   }

   // ������ս���˰  
   public String getAnnualBonusTax()
   {
      return formatNumber( annualBonusTax );
   }

   /***
    * ʵ������ = ˰ǰ���� - ��˰ - ���ս����������ս��ޣ�
    * @return ʵ������
    */
   public String getAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
            - Double.valueOf( getAnnualBonus() ) ) );
   }

   /***
    * ʵ�����ս� = ���ս� - ���ս���˰ 
    * @return ʵ�����ս�
    */
   public String getAfterTaxAnnualBonus()
   {
      return formatNumber( super.getRemark5() );
      //return formatNumber( String.valueOf( Double.valueOf( getAnnualBonus() ) - Double.valueOf( getAnnualBonusTax() ) ) );
   }

   // ���ʵ������(iClick)
   public String getIClickAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getAfterTaxSalary() ) - Double.valueOf( getMPFPersonal() ) ) );
   }

}
