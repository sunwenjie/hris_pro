package com.kan.hro.domain.biz.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.util.KANUtil;

public class EmployeeFullView extends EmployeeVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -7776525452820830978L;

   /**
    * For View
    */
   // 派送信息ID
   private String contractId;

   // 协议开始时间
   private String startDate;

   // 协议结束时间
   private String endDate;

   // 离职时间
   private String resignDate;

   // 协议状态
   private String contractStatus;

   // 基本工资
   private String salary;

   // 津贴
   private String allowance;

   // 奖金
   private String subsidy;

   /**
    * For Application
    */

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   // 解译合同状态
   public String getDecodeContractStatus()
   {
      return decodeField( contractStatus, KANUtil.getMappings( this.getLocale(), "business.employee.contract.employStatuses" ) );
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
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

   public String getResignDate()
   {
      return resignDate;
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public String getSalary()
   {
      return salary;
   }

   public void setSalary( String salary )
   {
      this.salary = salary;
   }

   public String getAllowance()
   {
      return allowance;
   }

   public void setAllowance( String allowance )
   {
      this.allowance = allowance;
   }

   public String getSubsidy()
   {
      return subsidy;
   }

   public void setSubsidy( String subsidy )
   {
      this.subsidy = subsidy;
   }

}
