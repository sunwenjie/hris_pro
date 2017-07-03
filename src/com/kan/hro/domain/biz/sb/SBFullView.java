package com.kan.hro.domain.biz.sb;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class SBFullView extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4329231289512563104L;

   /**
    * For View
    */
   // 派送信息ID
   private String contractId;

   // 协议开始时间
   private String contractStartDate;

   // 协议结束时间
   private String contractEndDate;

   // 雇员ID
   private String employeeId;

   // 姓名（中文）
   private String employeeNameZH;

   // 姓名（英文）
   private String employeeNameEN;

   // 社保方案ID
   private String employeeSBId;

   // 社保方案名称（中文）
   private String employeeSBNameZH;

   // 社保方案名称（英文）
   private String employeeSBNameEN;

   // 供应商ID
   private String vendorId;

   // 供应商名称（中文）
   private String vendorNameZH;

   // 供应商名称（英文）
   private String vendorNameEN;

   // 账单月份
   private String monthly;

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

   // sb item 
   private String item_61_c;
   private String item_61_p;
   private String item_62_c;
   private String item_62_p;
   private String item_63_c;
   private String item_63_p;
   private String item_64_c;
   private String item_64_p;
   private String item_65_c;
   private String item_65_p;
   private String item_66_c;
   private String item_66_p;
   private String item_67_c;
   private String item_67_p;
   private String item_68_c;
   private String item_68_p;
   private String item_69_c;
   private String item_69_p;
   private String item_70_c;
   private String item_70_p;
   private String item_71_c;
   private String item_71_p;
   private String item_72_c;
   private String item_72_p;
   private String item_73_c;
   private String item_73_p;
   private String item_74_c;
   private String item_74_p;
   private String item_75_c;
   private String item_75_p;

   @Override
   public String getEncodedId() throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update( Object object ) throws KANException
   {
      // No Use
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getContractStartDate()
   {
      return contractStartDate;
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return contractEndDate;
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
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

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public String getEmployeeSBNameZH()
   {
      return employeeSBNameZH;
   }

   public void setEmployeeSBNameZH( String employeeSBNameZH )
   {
      this.employeeSBNameZH = employeeSBNameZH;
   }

   public String getEmployeeSBNameEN()
   {
      return employeeSBNameEN;
   }

   public void setEmployeeSBNameEN( String employeeSBNameEN )
   {
      this.employeeSBNameEN = employeeSBNameEN;
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getItem_61_c()
   {
      return item_61_c;
   }

   public void setItem_61_c( String item_61_c )
   {
      this.item_61_c = item_61_c;
   }

   public String getItem_61_p()
   {
      return item_61_p;
   }

   public void setItem_61_p( String item_61_p )
   {
      this.item_61_p = item_61_p;
   }

   public String getItem_62_c()
   {
      return item_62_c;
   }

   public void setItem_62_c( String item_62_c )
   {
      this.item_62_c = item_62_c;
   }

   public String getItem_62_p()
   {
      return item_62_p;
   }

   public void setItem_62_p( String item_62_p )
   {
      this.item_62_p = item_62_p;
   }

   public String getItem_63_c()
   {
      return item_63_c;
   }

   public void setItem_63_c( String item_63_c )
   {
      this.item_63_c = item_63_c;
   }

   public String getItem_63_p()
   {
      return item_63_p;
   }

   public void setItem_63_p( String item_63_p )
   {
      this.item_63_p = item_63_p;
   }

   public String getItem_64_c()
   {
      return item_64_c;
   }

   public void setItem_64_c( String item_64_c )
   {
      this.item_64_c = item_64_c;
   }

   public String getItem_64_p()
   {
      return item_64_p;
   }

   public void setItem_64_p( String item_64_p )
   {
      this.item_64_p = item_64_p;
   }

   public String getItem_65_c()
   {
      return item_65_c;
   }

   public void setItem_65_c( String item_65_c )
   {
      this.item_65_c = item_65_c;
   }

   public String getItem_65_p()
   {
      return item_65_p;
   }

   public void setItem_65_p( String item_65_p )
   {
      this.item_65_p = item_65_p;
   }

   public String getItem_66_c()
   {
      return item_66_c;
   }

   public void setItem_66_c( String item_66_c )
   {
      this.item_66_c = item_66_c;
   }

   public String getItem_66_p()
   {
      return item_66_p;
   }

   public void setItem_66_p( String item_66_p )
   {
      this.item_66_p = item_66_p;
   }

   public String getItem_67_c()
   {
      return item_67_c;
   }

   public void setItem_67_c( String item_67_c )
   {
      this.item_67_c = item_67_c;
   }

   public String getItem_67_p()
   {
      return item_67_p;
   }

   public void setItem_67_p( String item_67_p )
   {
      this.item_67_p = item_67_p;
   }

   public String getItem_68_c()
   {
      return item_68_c;
   }

   public void setItem_68_c( String item_68_c )
   {
      this.item_68_c = item_68_c;
   }

   public String getItem_68_p()
   {
      return item_68_p;
   }

   public void setItem_68_p( String item_68_p )
   {
      this.item_68_p = item_68_p;
   }

   public String getItem_69_c()
   {
      return item_69_c;
   }

   public void setItem_69_c( String item_69_c )
   {
      this.item_69_c = item_69_c;
   }

   public String getItem_69_p()
   {
      return item_69_p;
   }

   public void setItem_69_p( String item_69_p )
   {
      this.item_69_p = item_69_p;
   }

   public String getItem_70_c()
   {
      return item_70_c;
   }

   public void setItem_70_c( String item_70_c )
   {
      this.item_70_c = item_70_c;
   }

   public String getItem_70_p()
   {
      return item_70_p;
   }

   public void setItem_70_p( String item_70_p )
   {
      this.item_70_p = item_70_p;
   }

   public String getItem_71_c()
   {
      return item_71_c;
   }

   public void setItem_71_c( String item_71_c )
   {
      this.item_71_c = item_71_c;
   }

   public String getItem_71_p()
   {
      return item_71_p;
   }

   public void setItem_71_p( String item_71_p )
   {
      this.item_71_p = item_71_p;
   }

   public String getItem_72_c()
   {
      return item_72_c;
   }

   public void setItem_72_c( String item_72_c )
   {
      this.item_72_c = item_72_c;
   }

   public String getItem_72_p()
   {
      return item_72_p;
   }

   public void setItem_72_p( String item_72_p )
   {
      this.item_72_p = item_72_p;
   }

   public String getItem_73_c()
   {
      return item_73_c;
   }

   public void setItem_73_c( String item_73_c )
   {
      this.item_73_c = item_73_c;
   }

   public String getItem_73_p()
   {
      return item_73_p;
   }

   public void setItem_73_p( String item_73_p )
   {
      this.item_73_p = item_73_p;
   }

   public String getItem_74_c()
   {
      return item_74_c;
   }

   public void setItem_74_c( String item_74_c )
   {
      this.item_74_c = item_74_c;
   }

   public String getItem_74_p()
   {
      return item_74_p;
   }

   public void setItem_74_p( String item_74_p )
   {
      this.item_74_p = item_74_p;
   }

   public String getItem_75_c()
   {
      return item_75_c;
   }

   public void setItem_75_c( String item_75_c )
   {
      this.item_75_c = item_75_c;
   }

   public String getItem_75_p()
   {
      return item_75_p;
   }

   public void setItem_75_p( String item_75_p )
   {
      this.item_75_p = item_75_p;
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

}
