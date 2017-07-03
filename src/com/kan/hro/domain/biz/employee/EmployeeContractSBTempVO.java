package com.kan.hro.domain.biz.employee;

public class EmployeeContractSBTempVO extends EmployeeContractSBVO
{
   // serialVersionUID:TODO（用一句话描述这个变量表示什么）  
   private static final long serialVersionUID = 118971654545L;
   // 基数
   private String sbBase;

   public String getSbBase()
   {
      return sbBase;
   }

   public void setSbBase( String sbBase )
   {
      this.sbBase = sbBase;
   }

}
