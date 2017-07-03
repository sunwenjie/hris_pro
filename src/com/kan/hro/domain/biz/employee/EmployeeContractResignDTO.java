package com.kan.hro.domain.biz.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmployeeContractResignDTO implements Serializable
{
   // serialVersionUID
   private static final long serialVersionUID = -4110569070528162513L;

   private String employeeContractResignId;

   private EmployeeContractVO employeeContractVO;

   private List< EmployeeContractSBVO > employeeContractSBVOs = new ArrayList< EmployeeContractSBVO >();

   private List< EmployeeContractCBVO > employeeContractCBVOs = new ArrayList< EmployeeContractCBVO >();

   public String getEmployeeContractResignId()
   {
      return employeeContractResignId;
   }

   public void setEmployeeContractResignId( String employeeContractResignId )
   {
      this.employeeContractResignId = employeeContractResignId;
   }

   public EmployeeContractVO getEmployeeContractVO()
   {
      return employeeContractVO;
   }

   public void setEmployeeContractVO( EmployeeContractVO employeeContractVO )
   {
      this.employeeContractVO = employeeContractVO;
   }

   public List< EmployeeContractSBVO > getEmployeeContractSBVOs()
   {
      return employeeContractSBVOs;
   }

   public void setEmployeeContractSBVOs( List< EmployeeContractSBVO > employeeContractSBVOs )
   {
      this.employeeContractSBVOs = employeeContractSBVOs;
   }

   public List< EmployeeContractCBVO > getEmployeeContractCBVOs()
   {
      return employeeContractCBVOs;
   }

   public void setEmployeeContractCBVOs( List< EmployeeContractCBVO > employeeContractCBVOs )
   {
      this.employeeContractCBVOs = employeeContractCBVOs;
   }

}
