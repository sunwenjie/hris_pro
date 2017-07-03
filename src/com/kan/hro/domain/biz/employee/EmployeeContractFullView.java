package com.kan.hro.domain.biz.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class EmployeeContractFullView extends EmployeeContractVO
{

   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = 7967761598408492019L;

   /**
    * For DB
    */

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 雇员编号
   private String employeeNo;

   // 户籍性质
   private String residencyType;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 证件生效日期
   private String certificateStartDate;

   // 证件失效日期
   private String certificateEndDate;

   // 银行
   private String bankId;

   //银行账户
   private String bankAccount;

   // 职位名称
   private String positionName;

   // 职级名称
   private String gradeName;

   // 部门编号
   private String branchCode;

   // 部门名称
   private String branchName;

   // 法务实体
   private String entityId;

   // 业务类型
   private String businessTypeId;

   // Base Salary Info
   private String salary;
   private String allowance;
   private String subsidy;

   // SB Info
   private String sbSolutionId;
   private String sbBase;
   private String sb_startDate;
   private String sb_endDate;

   // CB Info
   private String solutionId;
   private String cb_startDate;
   private String cb_endDate;

   // Leave Info
   private String item_41;
   private String item_42;
   private String item_43;
   private String item_44;
   private String item_45;
   private String item_46;
   private String item_47;

   // OT Info
   private String item_21;
   private String item_22;
   private String item_23;
   private String item_24;
   private String item_25;
   private String item_26;

   // Other Info
   private String item_141;
   private String item_142;
   private String item_143;
   private String item_144;
   private String item_145;
   private String item_146;
   private String item_147;
   private String item_148;
   private String item_149;
   private String item_150;
   private String item_151;
   private String item_161;
   private String item_162;
   private String item_181;
   private String item_182;
   private String item_183;
   private String item_184;
   private String item_185;
   private String item_186;
   private String item_187;
   private String item_188;
   
   /**
    * For Application
    */

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
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

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
   }

   public String getResidencyType()
   {
      return residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
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

   public String getCertificateStartDate()
   {
      return certificateStartDate;
   }

   public void setCertificateStartDate( String certificateStartDate )
   {
      this.certificateStartDate = certificateStartDate;
   }

   public String getCertificateEndDate()
   {
      return certificateEndDate;
   }

   public void setCertificateEndDate( String certificateEndDate )
   {
      this.certificateEndDate = certificateEndDate;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
   }

   public String getPositionName()
   {
      return positionName;
   }

   public void setPositionName( String positionName )
   {
      this.positionName = positionName;
   }

   public String getGradeName()
   {
      return gradeName;
   }

   public void setGradeName( String gradeName )
   {
      this.gradeName = gradeName;
   }

   public String getBranchCode()
   {
      return branchCode;
   }

   public void setBranchCode( String branchCode )
   {
      this.branchCode = branchCode;
   }

   public String getBranchName()
   {
      return branchName;
   }

   public void setBranchName( String branchName )
   {
      this.branchName = branchName;
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

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public String getSbBase()
   {
      return sbBase;
   }

   public void setSbBase( String sbBase )
   {
      this.sbBase = sbBase;
   }

   public String getSb_startDate()
   {
      return sb_startDate;
   }

   public void setSb_startDate( String sb_startDate )
   {
      this.sb_startDate = sb_startDate;
   }

   public String getSb_endDate()
   {
      return sb_endDate;
   }

   public void setSb_endDate( String sb_endDate )
   {
      this.sb_endDate = sb_endDate;
   }

   public String getSolutionId()
   {
      return solutionId;
   }

   public void setSolutionId( String solutionId )
   {
      this.solutionId = solutionId;
   }

   public String getCb_startDate()
   {
      return cb_startDate;
   }

   public void setCb_startDate( String cb_startDate )
   {
      this.cb_startDate = cb_startDate;
   }

   public String getCb_endDate()
   {
      return cb_endDate;
   }

   public void setCb_endDate( String cb_endDate )
   {
      this.cb_endDate = cb_endDate;
   }

   public String getItem_41()
   {
      return item_41;
   }

   public void setItem_41( String item_41 )
   {
      this.item_41 = item_41;
   }

   public String getItem_42()
   {
      return item_42;
   }

   public void setItem_42( String item_42 )
   {
      this.item_42 = item_42;
   }

   public String getItem_43()
   {
      return item_43;
   }

   public void setItem_43( String item_43 )
   {
      this.item_43 = item_43;
   }

   public String getItem_44()
   {
      return item_44;
   }

   public void setItem_44( String item_44 )
   {
      this.item_44 = item_44;
   }

   public String getItem_45()
   {
      return item_45;
   }

   public void setItem_45( String item_45 )
   {
      this.item_45 = item_45;
   }

   public String getItem_46()
   {
      return item_46;
   }

   public void setItem_46( String item_46 )
   {
      this.item_46 = item_46;
   }

   public String getItem_47()
   {
      return item_47;
   }

   public void setItem_47( String item_47 )
   {
      this.item_47 = item_47;
   }

   public String getItem_21()
   {
      return item_21;
   }

   public void setItem_21( String item_21 )
   {
      this.item_21 = item_21;
   }

   public String getItem_22()
   {
      return item_22;
   }

   public void setItem_22( String item_22 )
   {
      this.item_22 = item_22;
   }

   public String getItem_23()
   {
      return item_23;
   }

   public void setItem_23( String item_23 )
   {
      this.item_23 = item_23;
   }

   public String getItem_24()
   {
      return item_24;
   }

   public void setItem_24( String item_24 )
   {
      this.item_24 = item_24;
   }

   public String getItem_25()
   {
      return item_25;
   }

   public void setItem_25( String item_25 )
   {
      this.item_25 = item_25;
   }

   public String getItem_26()
   {
      return item_26;
   }

   public void setItem_26( String item_26 )
   {
      this.item_26 = item_26;
   }

   public String getItem_141()
   {
      return item_141;
   }

   public void setItem_141( String item_141 )
   {
      this.item_141 = item_141;
   }

   public String getItem_142()
   {
      return item_142;
   }

   public void setItem_142( String item_142 )
   {
      this.item_142 = item_142;
   }

   public String getItem_143()
   {
      return item_143;
   }

   public void setItem_143( String item_143 )
   {
      this.item_143 = item_143;
   }

   public String getItem_144()
   {
      return item_144;
   }

   public void setItem_144( String item_144 )
   {
      this.item_144 = item_144;
   }

   public String getItem_145()
   {
      return item_145;
   }

   public void setItem_145( String item_145 )
   {
      this.item_145 = item_145;
   }

   public String getItem_146()
   {
      return item_146;
   }

   public void setItem_146( String item_146 )
   {
      this.item_146 = item_146;
   }

   public String getItem_147()
   {
      return item_147;
   }

   public void setItem_147( String item_147 )
   {
      this.item_147 = item_147;
   }

   public String getItem_148()
   {
      return item_148;
   }

   public void setItem_148( String item_148 )
   {
      this.item_148 = item_148;
   }

   public String getItem_149()
   {
      return item_149;
   }

   public void setItem_149( String item_149 )
   {
      this.item_149 = item_149;
   }

   public String getItem_150()
   {
      return item_150;
   }

   public void setItem_150( String item_150 )
   {
      this.item_150 = item_150;
   }

   public String getItem_151()
   {
      return item_151;
   }

   public void setItem_151( String item_151 )
   {
      this.item_151 = item_151;
   }

   public String getItem_161()
   {
      return item_161;
   }

   public void setItem_161( String item_161 )
   {
      this.item_161 = item_161;
   }

   public String getItem_162()
   {
      return item_162;
   }

   public void setItem_162( String item_162 )
   {
      this.item_162 = item_162;
   }

   public String getItem_181()
   {
      return item_181;
   }

   public void setItem_181( String item_181 )
   {
      this.item_181 = item_181;
   }

   public String getItem_182()
   {
      return item_182;
   }

   public void setItem_182( String item_182 )
   {
      this.item_182 = item_182;
   }

   public String getItem_183()
   {
      return item_183;
   }

   public void setItem_183( String item_183 )
   {
      this.item_183 = item_183;
   }

   public String getItem_184()
   {
      return item_184;
   }

   public void setItem_184( String item_184 )
   {
      this.item_184 = item_184;
   }

   public String getItem_185()
   {
      return item_185;
   }

   public void setItem_185( String item_185 )
   {
      this.item_185 = item_185;
   }

   public String getItem_186()
   {
      return item_186;
   }

   public void setItem_186( String item_186 )
   {
      this.item_186 = item_186;
   }

   public String getItem_187()
   {
      return item_187;
   }

   public void setItem_187( String item_187 )
   {
      this.item_187 = item_187;
   }

   public String getItem_188()
   {
      return item_188;
   }

   public void setItem_188( String item_188 )
   {
      this.item_188 = item_188;
   }

}
