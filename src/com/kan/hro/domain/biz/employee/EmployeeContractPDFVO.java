package com.kan.hro.domain.biz.employee;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;


public class EmployeeContractPDFVO extends BaseVO{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String baseSalary;//��������
	
	private String probationSalary;//�����ڻ�������
	
	private String probationMonths;//�����ڼ�
	
	private String workAddress;//������ַ
	
   private String annualSalary;//��н
   
   private String monthlySalary;//��н
   
   private String positionName;//��λ
   
   private String companyName;//��˾����

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
