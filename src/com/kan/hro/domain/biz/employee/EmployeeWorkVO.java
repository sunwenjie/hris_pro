package com.kan.hro.domain.biz.employee;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeWorkVO extends BaseVO
{

   // Serial Version UID

   private static final long serialVersionUID = -3986770799631492651L;

   /**
    *  For DB
    */

   // ѧϰ����Id
   private String employeeWorkId;

   // Ա��Id
   private String employeeId;

   // ��˾����
   private String companyName;

   // ��ʼ����ʱ��
   private String startDate;

   // ��������ʱ�䣨��ǰ��ְ������д��
   private String endDate;

   // ��ְ����
   private String department;

   // ��ְְλ�������� - ���� - �ⲿְλ��
   private String positionId;

   // ְλ����
   private String positionName;

   // ������ҵ
   private String industryId;

   // ��ҵ����
   private String companyType;

   // ��˾��ģ
   private String size;

   // ��ϵ��
   private String contact;

   // ��ϵ�˵绰
   private String phone;

   // ��ϵ���ֻ�
   private String mobile;

   // ��������
   private String description;

   /**
    *  For Application
    */

   // ְλ���ƣ����ģ�
   private String positionNameZH;

   // ְλ���ƣ�Ӣ�ģ�
   private String positionNameEN;

   // ��Ա����
   private String employeeName;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeWorkId );
   }

   public String getDecodePositionName()
   {
      if ( KANUtil.filterEmpty( this.positionId ) != null )
      {
         if ( this.getLocale() != null )
         {
            if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return positionNameZH;
            }
            else
            {
               return positionNameEN;
            }
         }
         else
         {
            return positionNameZH;
         }
      }
      else
      {
         return getPositionName();
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.companyName = "";
      this.startDate = "";
      this.endDate = "";
      this.department = "";
      this.positionId = "";
      this.positionName = "";
      this.industryId = "0";
      this.companyType = "0";
      this.size = "0";
      this.contact = "";
      this.phone = "";
      this.mobile = "";
      this.description = "";
      super.setStatus( "0" );

   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.employee.work.statuses" ) );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) object;
      this.employeeId = employeeWorkVO.getEmployeeId();
      this.companyName = employeeWorkVO.getCompanyName();
      this.startDate = employeeWorkVO.getStartDate();
      this.endDate = employeeWorkVO.getEndDate();
      this.department = employeeWorkVO.getDepartment();
      this.positionId = employeeWorkVO.getPositionId();
      this.positionName = employeeWorkVO.getPositionName();
      this.industryId = employeeWorkVO.getIndustryId();
      this.companyType = employeeWorkVO.getCompanyType();
      this.size = employeeWorkVO.getSize();
      this.contact = employeeWorkVO.getContact();
      this.phone = employeeWorkVO.getPhone();
      this.mobile = employeeWorkVO.getMobile();
      this.description = employeeWorkVO.getDescription();
      super.setStatus( employeeWorkVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( this.employeeId );
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeWorkId()
   {
      return employeeWorkId;
   }

   public void setEmployeeWorkId( String employeeWorkId )
   {
      this.employeeWorkId = employeeWorkId;
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

   public String getCompanyName()
   {
      return companyName;
   }

   public void setCompanyName( String companyName )
   {
      this.companyName = companyName;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getPositionId()
   {
      return KANUtil.filterEmpty( positionId );
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getIndustryId()
   {
      return industryId;
   }

   public void setIndustryId( String industryId )
   {
      this.industryId = industryId;
   }

   public String getCompanyType()
   {
      return companyType;
   }

   public void setCompanyType( String companyType )
   {
      this.companyType = companyType;
   }

   public String getSize()
   {
      return size;
   }

   public void setSize( String size )
   {
      this.size = size;
   }

   public String getContact()
   {
      return contact;
   }

   public void setContact( String contact )
   {
      this.contact = contact;
   }

   public String getPhone()
   {
      return phone;
   }

   public void setPhone( String phone )
   {
      this.phone = phone;
   }

   public String getMobile()
   {
      return mobile;
   }

   public String getPositionName()
   {
      return positionName;
   }

   public void setPositionName( String positionName )
   {
      this.positionName = positionName;
   }

   public void setMobile( String mobile )
   {
      this.mobile = mobile;
   }

   public String getPositionNameZH()
   {
      return positionNameZH;
   }

   public void setPositionNameZH( String positionNameZH )
   {
      this.positionNameZH = positionNameZH;
   }

   public String getPositionNameEN()
   {
      return positionNameEN;
   }

   public void setPositionNameEN( String positionNameEN )
   {
      this.positionNameEN = positionNameEN;
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   // Tab ��ʾ
   public String getRemark()
   {
      String ret = getCompanyName() + " [ " + getDecodePositionName() + " ] " + " " + getStartDate() + " ~ ";

      if ( KANUtil.filterEmpty( endDate ) != null )
      {
         ret = ret + getEndDate();
      }
      else
      {
         ret = ret + ( this.getLocale() != null && "zh".equalsIgnoreCase( this.getLocale().getLanguage() ) ? "����" : "now" );
      }

      return ret;
   }

}
