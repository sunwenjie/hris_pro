package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeCertificationVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */

   /**
    *雇员证书 - 奖项Id
    */
   private String employeeCertificationId;

   // 员工Id
   private String employeeId;

   //证书 - 奖项Id（绑定设置 - 资质 - 证书 - 奖项）
   private String certificationId;

   // 颁发机构
   private String awardFrom;

   // 颁发日期
   private String awardDate;

   private String description;

   /**
    * For Application
    */
   private List< MappingVO > certificationIds = new ArrayList< MappingVO >();

   // 证书 - 奖项 名称
   private String certificationName;
   private String certificationNameZH;
   private String certificationNameEN;

   // 雇员姓名

   private String employeeName;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeCertificationId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.certificationId = "";
      this.awardFrom = "";
      this.awardDate = "";
      this.description = "";
      super.setStatus( "" );

   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.certificationIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getCertifications( super.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) object;
      this.certificationId = employeeCertificationVO.getCertificationId();
      this.awardFrom = employeeCertificationVO.getAwardFrom();
      this.awardDate = employeeCertificationVO.getAwardDate();
      this.description = employeeCertificationVO.getDescription();
      super.setStatus( employeeCertificationVO.getStatus() );
      super.setModifyBy( employeeCertificationVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public List< MappingVO > getCertificationIds()
   {
      return certificationIds;
   }

   public void setCertificationIds( List< MappingVO > certificationIds )
   {
      this.certificationIds = certificationIds;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeCertificationId()
   {
      return employeeCertificationId;
   }

   public void setEmployeeCertificationId( String employeeCertificationId )
   {
      this.employeeCertificationId = employeeCertificationId;
   }

   public String getCertificationId()
   {
      return certificationId;
   }

   public void setCertificationId( String certificationId )
   {
      this.certificationId = certificationId;
   }

   public String getAwardFrom()
   {
      return awardFrom;
   }

   public void setAwardFrom( String awardFrom )
   {
      this.awardFrom = awardFrom;
   }

   public String getAwardDate()
   {
      return KANUtil.formatDate( this.awardDate, "yyyy-MM-dd", true );
   }

   public void setAwardDate( String awardDate )
   {
      this.awardDate = awardDate;
   }

   public String getCertificationName()
   {
      return certificationName;
   }

   public void setCertificationName( String certificationName )
   {
      this.certificationName = certificationName;
   }

   public String getCertificationNameZH()
   {
      return certificationNameZH;
   }

   public void setCertificationNameZH( String certificationNameZH )
   {
      this.certificationNameZH = certificationNameZH;
   }

   public String getCertificationNameEN()
   {
      return certificationNameEN;
   }

   public String getDecodeCertificationName()
   {
      return decodeField( certificationId, certificationIds );
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( this.employeeId );
   }

   // Tab 显示
   public String getRemark()
   {
      String ret = getDecodeCertificationName();

      if ( KANUtil.filterEmpty( awardDate ) != null )
      {
         ret = ret + " " + getAwardDate();
      }

      return ret;
   }
}
