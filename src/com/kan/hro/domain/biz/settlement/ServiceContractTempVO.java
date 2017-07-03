package com.kan.hro.domain.biz.settlement;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ContractTempVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-11  
 */
public class ServiceContractTempVO extends BaseVO
{

   // Serial Version UID
   private static final long serialVersionUID = 8664971461724797219L;

   // 服务协议Id
   private String contractId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 客户Id
   private String clientId;

   // 订单Id（或帐套Id）
   private String orderId;

   // 订单主表Id（结算订单表）
   private String orderHeaderId;

   // 雇员Id
   private String employeeId;

   // 雇员服务协议Id
   private String employeeContractId;

   // 考勤表Id
   private String timesheetId;

   // 服务协议开始日期
   private String startDate;

   // 服务协议结束日期
   private String endDate;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 所属部门（Branch Id）
   private String branch;

   // 所属人（Position Id）
   private String owner;

   // 描述
   private String description;

   // Monthly
   private String monthly;

   // 工资月份
   private String salaryMonth;

   // 社保月份
   private String sbMonth;

   // 商保月份
   private String cbMonth;

   // 公积金月份
   private String fundMonth;

   /**
    * For App
    */
   // 包含科目数量
   private String countItemId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 批次ID
   private String batchId;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final ServiceContractTempVO serviceContractTempVO = ( ServiceContractTempVO ) object;
      this.entityId = serviceContractTempVO.getEntityId();
      this.businessTypeId = serviceContractTempVO.getBusinessTypeId();
      this.clientId = serviceContractTempVO.getClientId();
      this.orderId = serviceContractTempVO.getOrderId();
      this.orderHeaderId = serviceContractTempVO.getOrderHeaderId();
      this.employeeId = serviceContractTempVO.getEmployeeId();
      this.employeeContractId = serviceContractTempVO.getEmployeeContractId();
      this.timesheetId = serviceContractTempVO.getTimesheetId();
      this.billAmountPersonal = serviceContractTempVO.getBillAmountPersonal();
      this.billAmountCompany = serviceContractTempVO.getBillAmountCompany();
      this.costAmountPersonal = serviceContractTempVO.getCostAmountPersonal();
      this.costAmountCompany = serviceContractTempVO.getCostAmountCompany();
      this.branch = serviceContractTempVO.getBranch();
      this.owner = serviceContractTempVO.getOwner();
      this.startDate = serviceContractTempVO.getStartDate();
      this.endDate = serviceContractTempVO.getEndDate();
      this.description = serviceContractTempVO.getDescription();
      this.monthly = serviceContractTempVO.getMonthly();
      this.salaryMonth = serviceContractTempVO.getSalaryMonth();
      this.sbMonth = serviceContractTempVO.getSbMonth();
      this.cbMonth = serviceContractTempVO.getCbMonth();
      this.fundMonth = serviceContractTempVO.getFundMonth();
      super.setStatus( serviceContractTempVO.getStatus() );
      super.setModifyBy( serviceContractTempVO.getModifyBy() );
      super.setModifyDate( new Date() );
      super.setCorpId( serviceContractTempVO.getCorpId() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.clientId = "";
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

      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( contractId == null || contractId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( contractId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // BatchId编码
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // OrderHeaderId编码
   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   // EmployeeId编码
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // EmployeeId编码
   public String getEncodedEmployeeContractId() throws KANException
   {
      return encodedField( employeeContractId );
   }

   // TimesheetId编码
   public String getEncodedTimesheetId() throws KANException
   {
      return encodedField( timesheetId );
   }

   // 获得所属部门名称
   public String getDecodeBranch() throws KANException
   {
      // 获得常量中的BranchVO 集合
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

   // 获得所属人姓名
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

   public final String getClientId()
   {
      return clientId;
   }

   public final void setClientId( String clientId )
   {
      this.clientId = clientId;
   }

   public final String getOrderId()
   {
      return orderId;
   }

   public final void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

}
