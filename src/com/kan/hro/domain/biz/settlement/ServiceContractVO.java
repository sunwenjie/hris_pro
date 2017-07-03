package com.kan.hro.domain.biz.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ContractVO  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-9-11  
 */
public class ServiceContractVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -2363325150076331103L;

   // ����Э��Id
   private String contractId;

   // �������Э��Id
   private String contractTempId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // ����Id��������Id��
   private String orderId;

   // ��������Id�����㶩����
   private String orderHeaderId;

   // ��ԱId
   private String employeeId;

   // ��Ա����Э��Id
   private String employeeContractId;

   // ���ڱ�Id
   private String timesheetId;

   // ����Э�鿪ʼ����
   private String startDate;

   // ����Э���������
   private String endDate;

   // �ϼƣ��������룩
   private String billAmountPersonal;

   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ�����֧����
   private String costAmountPersonal;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;

   // �������ţ�Branch Id��
   private String branch;

   // �����ˣ�Position Id��
   private String owner;

   // ����
   private String description;

   // Monthly
   private String monthly;

   // �����·�
   private String salaryMonth;

   // �籣�·�
   private String sbMonth;

   // �̱��·�
   private String cbMonth;

   // �������·�
   private String fundMonth;

   // �Ƿ�����н��
   private String paymentFlag;

   /**
    * For App
    */
   // ������Ŀ����
   private String countItemId;

   // ��Ա������
   private String employeeNameZH;

   // ��ԱӢ����
   private String employeeNameEN;

   private String clientNameZH;

   private String clientNameEN;

   // ����ID
   private String batchId;

   private String sbMonthly;

   // for app
   // ����ʵ��
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.entities = KANConstants.getKANAccountConstants( getAccountId() ).getEntities( getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() );

   }

   @Override
   public void update( final Object object )
   {
      final ServiceContractVO serviceContractVO = ( ServiceContractVO ) object;
      this.entityId = serviceContractVO.getEntityId();
      this.businessTypeId = serviceContractVO.getBusinessTypeId();
      this.orderId = serviceContractVO.getOrderId();
      this.orderHeaderId = serviceContractVO.getOrderHeaderId();
      this.employeeId = serviceContractVO.getEmployeeId();
      this.employeeContractId = serviceContractVO.getEmployeeContractId();
      this.timesheetId = serviceContractVO.getTimesheetId();
      this.billAmountPersonal = serviceContractVO.getBillAmountPersonal();
      this.billAmountCompany = serviceContractVO.getBillAmountCompany();
      this.costAmountPersonal = serviceContractVO.getCostAmountPersonal();
      this.costAmountCompany = serviceContractVO.getCostAmountCompany();
      this.branch = serviceContractVO.getBranch();
      this.owner = serviceContractVO.getOwner();
      this.startDate = serviceContractVO.getStartDate();
      this.endDate = serviceContractVO.getEndDate();
      this.description = serviceContractVO.getDescription();
      this.monthly = serviceContractVO.getMonthly();
      this.salaryMonth = serviceContractVO.getSalaryMonth();
      this.sbMonth = serviceContractVO.getSbMonth();
      this.cbMonth = serviceContractVO.getCbMonth();
      this.fundMonth = serviceContractVO.getFundMonth();
      this.paymentFlag = serviceContractVO.getPaymentFlag();
      super.setStatus( serviceContractVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( serviceContractVO.getCorpId() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.orderId = "";
      this.orderHeaderId = "";
      this.employeeId = "";
      this.employeeContractId = "";
      this.timesheetId = "";
      this.billAmountPersonal = "";
      this.billAmountCompany = "";
      this.costAmountPersonal = "";
      this.costAmountCompany = "";
      this.branch = "0";
      this.owner = "0";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      this.monthly = "";
      this.salaryMonth = "";
      this.sbMonth = "";
      this.cbMonth = "";
      this.fundMonth = "";
      this.paymentFlag = "";
      super.setStatus( "0" );
      super.setCorpId( "" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( contractId );
   }

   // BatchId����
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // OrderHeaderId����
   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   // EmployeeId����
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // EmployeeContractId����
   public String getEncodedEmployeeContractId() throws KANException
   {
      return encodedField( employeeContractId );
   }

   // TimesheetId����
   public String getEncodedTimesheetId() throws KANException
   {
      return encodedField( timesheetId );
   }

   // ���������������
   public String getDecodeBranch() throws KANException
   {
      // ��ó����е�BranchVO ����
      final List< BranchVO > branchVOs = KANConstants.getKANAccountConstants( getAccountId() ).BRANCH_VO;

      for ( BranchVO branchVO : branchVOs )
      {
         if ( this.branch.equals( branchVO.getBranchId() ) )
         {
            if ( getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return branchVO.getNameZH();
            }
            else
            {
               return branchVO.getNameEN();
            }

         }
      }
      return null;
   }

   // �������������
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getTimesheetId()
   {
      return timesheetId;
   }

   public void setTimesheetId( String timesheetId )
   {
      this.timesheetId = timesheetId;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public final String getSalaryMonth()
   {
      return salaryMonth;
   }

   public final void setSalaryMonth( String salaryMonth )
   {
      this.salaryMonth = salaryMonth;
   }

   public final String getSbMonth()
   {
      return sbMonth;
   }

   public final void setSbMonth( String sbMonth )
   {
      this.sbMonth = sbMonth;
   }

   public final String getCbMonth()
   {
      return cbMonth;
   }

   public final void setCbMonth( String cbMonth )
   {
      this.cbMonth = cbMonth;
   }

   public final String getFundMonth()
   {
      return fundMonth;
   }

   public final void setFundMonth( String fundMonth )
   {
      this.fundMonth = fundMonth;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
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

   public final String getEmployeeContractId()
   {
      return employeeContractId;
   }

   public final void setEmployeeContractId( String employeeContractId )
   {
      this.employeeContractId = employeeContractId;
   }

   public final String getBatchId()
   {
      return batchId;
   }

   public final void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public final String getContractTempId()
   {
      return contractTempId;
   }

   public final void setContractTempId( String contractTempId )
   {
      this.contractTempId = contractTempId;
   }

   public final String getPaymentFlag()
   {
      return paymentFlag;
   }

   public final void setPaymentFlag( String paymentFlag )
   {
      this.paymentFlag = paymentFlag;
   }

   public final String getEntityId()
   {
      return entityId;
   }

   public final void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public final String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public final void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public final String getOrderId()
   {
      return orderId;
   }

   public final void setOrderId( String orderId )
   {
      this.orderId = orderId;
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

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   // ��÷���ʵ������
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( entityId, entities );
   }

   // ���ҵ����������
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( businessTypeId, businessTypes );
   }

   public String getSbMonthly()
   {
      return sbMonthly;
   }

   public void setSbMonthly( String sbMonthly )
   {
      this.sbMonthly = sbMonthly;
   }

}
