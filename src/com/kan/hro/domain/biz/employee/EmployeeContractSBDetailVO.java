package com.kan.hro.domain.biz.employee;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

/**  
 *  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�EmployeeContractSBDetailVO  
 * ��������  ��Ա�籣������ϸ
 * �����ˣ�Jixiang  
 * ����ʱ�䣺2013-8-20 ����03:00:07  
 *  
 */

public class EmployeeContractSBDetailVO extends BaseVO
{
   /**
    *  Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    *  For DB 
    */

   // ��Ա�籣������ϸId���������������ù�Ա������
   private String employeeSBDetailId;

   // ��Ա�籣����Id
   private String employeeSBId;

   // �籣������ϸId�������� - ҵ�� - �籣������
   private String solutionDetailId;

   // ��˾����
   private String baseCompany;

   // ���˻���
   private String basePersonal;

   //����
   private String description;

   /**
    *  For Application 
    */

   // ��ĿId
   private String itemId;

   // ��Ŀ���
   private String itemNo;

   // ��Ŀ����(����)
   private String nameZH;

   // ��Ŀ����(Ӣ��)
   private String nameEN;

   // ��˾����(��)
   private String companyCap;

   // ��˾����(��)
   private String companyFloor;

   // ���˻��� (��)
   private String personalCap;

   // ���˻���(��)
   private String personalFloor;

   // ��˾����
   private String companyPercent;

   // ���˱���
   private String personalPercent;

   // ��˾�̶���
   private String companyFixAmount;

   // ���˹̶���
   private String personalFixAmount;

   // �걨��ʼʱ��
   private String startDateLimit;

   // �걨��ֹʱ��
   private String endDateLimit;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeSBDetailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeSBId = "0";
      this.solutionDetailId = "0";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeContractSBDetailVO employeeContractSBDetailSolution = ( EmployeeContractSBDetailVO ) object;
      this.solutionDetailId = employeeContractSBDetailSolution.getSolutionDetailId();
      this.employeeSBId = employeeContractSBDetailSolution.getEmployeeSBId();
      this.employeeSBDetailId = employeeContractSBDetailSolution.getSolutionDetailId();
      this.baseCompany = employeeContractSBDetailSolution.getBaseCompany();
      this.basePersonal = employeeContractSBDetailSolution.getBasePersonal();
      this.description = employeeContractSBDetailSolution.getDescription();
      super.setStatus( employeeContractSBDetailSolution.getStatus() );
      super.setModifyBy( employeeContractSBDetailSolution.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getEmployeeSBDetailId()
   {
      return employeeSBDetailId;
   }

   public void setEmployeeSBDetailId( String employeeSBDetailId )
   {
      this.employeeSBDetailId = employeeSBDetailId;
   }

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public String getSolutionDetailId()
   {
      return solutionDetailId;
   }

   public void setSolutionDetailId( String solutionDetailId )
   {
      this.solutionDetailId = solutionDetailId;
   }

   public String getBasePersonal()
   {
      return formatNumber( basePersonal );
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getBaseCompany()
   {
      return formatNumber( baseCompany );
   }

   public void setBaseCompany( String baseCompany )
   {
      this.baseCompany = baseCompany;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public final String getItemId()
   {
      return itemId;
   }

   public final void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public final String getItemNo()
   {
      return itemNo;
   }

   public final void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public final String getNameZH()
   {
      return nameZH;
   }

   public final void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public final String getNameEN()
   {
      return nameEN;
   }

   public final void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public final String getName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equals( "ZH" ) )
         {
            return this.nameZH;
         }
         else
         {
            return this.nameEN;
         }
      }
      else
      {
         return nameZH;
      }
   }

   public final String getCompanyCap()
   {
      return companyCap;
   }

   public final void setCompanyCap( String companyCap )
   {
      this.companyCap = companyCap;
   }

   public final String getCompanyFloor()
   {
      return companyFloor;
   }

   public final void setCompanyFloor( String companyFloor )
   {
      this.companyFloor = companyFloor;
   }

   public final String getPersonalCap()
   {
      return personalCap;
   }

   public final void setPersonalCap( String personalCap )
   {
      this.personalCap = personalCap;
   }

   public final String getPersonalFloor()
   {
      return personalFloor;
   }

   public final void setPersonalFloor( String personalFloor )
   {
      this.personalFloor = personalFloor;
   }

   public final String getCompanyPercent()
   {
      return companyPercent;
   }

   public final void setCompanyPercent( String companyPercent )
   {
      this.companyPercent = companyPercent;
   }

   public final String getPersonalPercent()
   {
      return personalPercent;
   }

   public final void setPersonalPercent( String personalPercent )
   {
      this.personalPercent = personalPercent;
   }

   public final String getCompanyFixAmount()
   {
      return companyFixAmount;
   }

   public final void setCompanyFixAmount( String companyFixAmount )
   {
      this.companyFixAmount = companyFixAmount;
   }

   public final String getPersonalFixAmount()
   {
      return personalFixAmount;
   }

   public final void setPersonalFixAmount( String personalFixAmount )
   {
      this.personalFixAmount = personalFixAmount;
   }

   public final String getStartDateLimit()
   {
      return startDateLimit;
   }

   public final void setStartDateLimit( String startDateLimit )
   {
      this.startDateLimit = startDateLimit;
   }

   public final String getEndDateLimit()
   {
      return endDateLimit;
   }

   public final void setEndDateLimit( String endDateLimit )
   {
      this.endDateLimit = endDateLimit;
   }

}
