package com.kan.hro.domain.biz.sb;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�DetailVO  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-9-11  
 */
public class SBDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 7064174007246987791L;

   // �籣�ӱ�Id
   private String detailId;

   // �籣����Id
   private String headerId;

   // ��ĿId
   private String itemId;

   // ��Ŀ���
   private String itemNo;

   // ��Ŀ������
   private String nameZH;

   // ��ĿӢ����
   private String nameEN;

   // ���������ˣ�
   private String basePersonal;

   // ��������˾��
   private String baseCompany;

   // ���ʣ����ˣ�
   private String ratePersonal;

   // ���ʣ���˾��
   private String rateCompany;

   // �̶��𣨸��ˣ�
   private String fixPersonal;

   // �̶��𣨹�˾��
   private String fixCompany;

   // �ϼƣ����ˣ�
   private String amountPersonal;

   // �ϼƣ���˾��
   private String amountCompany;

   // �˵��·�
   private String monthly;

   // �����·�
   private String accountMonthly;

   // ����
   private String description;

   /**
    * 
    *	 For App
    */
   // ����Э��ID
   private String contractId;

   // ��Ŀ����
   private String itemType;

   // �ͻ����
   private String clientNo;

   // �ͻ����ƣ�Ӣ�ģ�
   private String clientNameZH;

   // �ͻ����ƣ����ģ�
   private String clientNameEN;

   // ��ԱID
   private String employeeId;

   // ��Ա�������У�
   private String employeeNameZH;

   // ��Ա������Ӣ��
   private String employeeNameEN;

   // �籣����
   private String sbNumber;

   // ��Ϣ
   private String interest;

   // ���ɽ�
   private String overdueFine;

   // �籣����
   private String employeeSBId;

   // ��ʾ
   private boolean updataed;

   // ����ʵ��ID
   private String entityId;

   // ҵ������ID
   private String businessTypeId;

   // ����Id
   private String orderId;

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

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.detail.status" ) );
   }

   @Override
   public void update( final Object object )
   {
      final String ignoreProperties[] = { "detailId", "accountId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.itemId = "";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.basePersonal = "";
      this.baseCompany = "";
      this.ratePersonal = "";
      this.rateCompany = "";
      this.fixPersonal = "";
      this.fixCompany = "";
      this.amountPersonal = "";
      this.amountCompany = "";
      this.monthly = "";
      this.accountMonthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getBasePersonal()
   {
      return basePersonal;
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getDecodeBasePersonal()
   {
      return formatNumber( basePersonal );
   }

   public String getBaseCompany()
   {
      return baseCompany;
   }

   public void setBaseCompany( String baseCompany )
   {
      this.baseCompany = baseCompany;
   }

   public String getDecodeBaseCompany()
   {
      return formatNumber( baseCompany );
   }

   public String getRatePersonal()
   {
      return ratePersonal;
   }

   public void setRatePersonal( String ratePersonal )
   {
      this.ratePersonal = ratePersonal;
   }

   public String getDecodeRatePersonal()
   {
      return formatNumber( ratePersonal );
   }

   public String getRateCompany()
   {
      return rateCompany;
   }

   public void setRateCompany( String rateCompany )
   {
      this.rateCompany = rateCompany;
   }

   public String getDecodeRateCompany()
   {
      return formatNumber( rateCompany );
   }

   public String getFixPersonal()
   {
      return fixPersonal;
   }

   public void setFixPersonal( String fixPersonal )
   {
      this.fixPersonal = fixPersonal;
   }

   public String getDecodeFixPersonal()
   {
      return formatNumber( fixPersonal );
   }

   public String getFixCompany()
   {
      return fixCompany;
   }

   public void setFixCompany( String fixCompany )
   {
      this.fixCompany = fixCompany;
   }

   public String getDecodeFixCompany()
   {
      return formatNumber( fixCompany );
   }

   public String getAmountPersonal()
   {
      return amountPersonal;
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getDecodeAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public String getFormatAmountPersonal()
   {
      return KANUtil.formatNumber2( Double.parseDouble( amountPersonal ) );
   }

   public String getAmountCompany()
   {
      return amountCompany;
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getDecodeAmountCompany()
   {
      return formatNumber( amountCompany );
   }

   public String getFormatAmountCompany()
   {
      return KANUtil.formatNumber2( Double.parseDouble( amountCompany ) );
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public final String getAccountMonthly()
   {
      return accountMonthly;
   }

   public final void setAccountMonthly( String accountMonthly )
   {
      this.accountMonthly = accountMonthly;
   }

   public final String getItemNo()
   {
      return itemNo;
   }

   public final void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public void addAmountCompany( String amountCompany )
   {
      this.amountCompany = String.valueOf( Double.valueOf( this.amountCompany ) + Double.valueOf( amountCompany ) );
   }

   public void addAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = String.valueOf( Double.valueOf( this.amountPersonal ) + Double.valueOf( amountPersonal ) );
   }

   public boolean isUpdataed()
   {
      return updataed;
   }

   public void setUpdataed( boolean updataed )
   {
      this.updataed = updataed;
   }

   public String getClientNo()
   {
      return clientNo;
   }

   public void setClientNo( String clientNo )
   {
      this.clientNo = clientNo;
   }

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public String getClientNameEN()
   {
      return clientNameEN;
   }

   public void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getInterest()
   {
      return interest;
   }

   public void setInterest( String interest )
   {
      this.interest = interest;
   }

   public String getOverdueFine()
   {
      return overdueFine;
   }

   public void setOverdueFine( String overdueFine )
   {
      this.overdueFine = overdueFine;
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
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

}
