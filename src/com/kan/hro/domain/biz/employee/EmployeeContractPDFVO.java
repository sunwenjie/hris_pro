package com.kan.hro.domain.biz.employee;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;


public class EmployeeContractPDFVO extends BaseVO{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String baseSalary;//基本工资
	
	private String probationSalary;//试用期基本工资
	
	private String probationMonths;//试用期间
	
	private String workAddress;//工作地址
	
   private String annualSalary;//年薪
   
   private String monthlySalary;//月薪
   
   private String positionName;//岗位
   
   private String companyName;//公司名称

	public String getBaseSalary()
   {
      return baseSalary;
   }

   public void setBaseSalary( String baseSalary )
   {
      this.baseSalary = baseSalary;
   }

   public String getProbationSalary()
   {
      return probationSalary;
   }

   public void setProbationSalary( String probationSalary )
   {
      this.probationSalary = probationSalary;
   }

   public String getProbationMonths()
   {
      return probationMonths;
   }

   public void setProbationMonths( String probationMonths )
   {
      this.probationMonths = probationMonths;
   }

   public String getWorkAddress()
   {
      return workAddress;
   }

   public void setWorkAddress( String workAddress )
   {
      this.workAddress = workAddress;
   }

   public String getAnnualSalary()
   {
      return annualSalary;
   }

   public void setAnnualSalary( String annualSalary )
   {
      this.annualSalary = annualSalary;
   }

   public String getMonthlySalary()
   {
      return monthlySalary;
   }

   public void setMonthlySalary( String monthlySalary )
   {
      this.monthlySalary = monthlySalary;
   }

   public String getPositionName()
   {
      return positionName;
   }

   public void setPositionName( String positionName )
   {
      this.positionName = positionName;
   }

   public String getCompanyName()
   {
      return companyName;
   }

   public void setCompanyName( String companyName )
   {
      this.companyName = companyName;
   }

   @Override
	public String getEncodedId() throws KANException {
		return null;
	}

	@Override
	public void reset() throws KANException {
		
	}

	@Override
	public void update(Object object) throws KANException {
		
	}
}
