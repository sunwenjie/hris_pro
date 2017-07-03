package com.kan.hro.domain.biz.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.domain.biz.vendor.VendorVO;

public class EmployeeContractSBDTO implements Serializable
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -2487038371901418856L;

   // Service Contract
   private EmployeeContractSBVO employeeContractSBVO;

   // Vendor 
   private VendorVO vendorVO;

   // Vendor Service
   private VendorServiceVO vendorServiceVO;

   // Service Contract Social Benefit
   private List< EmployeeContractSBDetailVO > employeeContractSBDetailVOs = new ArrayList< EmployeeContractSBDetailVO >();

   public EmployeeContractSBVO getEmployeeContractSBVO()
   {
      return employeeContractSBVO;
   }

   public void setEmployeeContractSBVO( EmployeeContractSBVO employeeContractSBVO )
   {
      this.employeeContractSBVO = employeeContractSBVO;
   }

   public List< EmployeeContractSBDetailVO > getEmployeeContractSBDetailVOs()
   {
      return employeeContractSBDetailVOs;
   }

   public void setEmployeeContractSBDetailVOs( List< EmployeeContractSBDetailVO > employeeContractSBDetailVOs )
   {
      this.employeeContractSBDetailVOs = employeeContractSBDetailVOs;
   }

   public final VendorVO getVendorVO()
   {
      return vendorVO;
   }

   public final void setVendorVO( VendorVO vendorVO )
   {
      this.vendorVO = vendorVO;
   }

   public VendorServiceVO getVendorServiceVO()
   {
      return vendorServiceVO;
   }

   public void setVendorServiceVO( VendorServiceVO vendorServiceVO )
   {
      this.vendorServiceVO = vendorServiceVO;
   }

   public EmployeeContractSBDetailVO getEmployeeContractSBDetailVOBySolutionDetailId( final String solutionDetailId )
   {
      if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
      {
         for ( EmployeeContractSBDetailVO employeeContractSBDetailVO : this.employeeContractSBDetailVOs )
         {
            if ( employeeContractSBDetailVO.getSolutionDetailId() != null && employeeContractSBDetailVO.getSolutionDetailId().trim().equals( solutionDetailId ) )
            {
               return employeeContractSBDetailVO;
            }
         }
      }

      return null;
   }

   public String getSBStartDate()
   {
      if ( employeeContractSBVO != null )
      {
         return employeeContractSBVO.getStartDate();
      }

      return null;
   }

   public String getSBEndDate()
   {
      if ( employeeContractSBVO != null )
      {
         return employeeContractSBVO.getEndDate();
      }

      return null;
   }

   public String getSBStatus()
   {
      if ( employeeContractSBVO != null )
      {
         return employeeContractSBVO.getStatus();
      }

      return null;
   }

}
