package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeEducationVO extends BaseVO
{

   // Serial Version UID

   private static final long serialVersionUID = -3986770799631492651L;

   /**
    *  For DB
    */

   // 学习经历Id
   private String employeeEducationId;

   // 员工Id
   private String employeeId;

   // 学校名称
   private String schoolName;

   // 学制
   private String schoolingLength;

   // 学习形式
   private String studyType;

   // 开始学习时间
   private String startDate;

   // 结束学习时间
   private String endDate;

   // 专业
   private String major;

   // 教育程度（绑定设置 - 资质 - 教育程度）
   private String educationId;

   private String description;

   /**
    *  For Application 
    */

   // 雇员姓名
   private String employeeName;
   @JsonIgnore
   // 教育
   private List< MappingVO > educations = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeEducationId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.schoolName = "";
      this.startDate = "";
      this.endDate = "";
      this.major = "";
      this.educationId = "0";
      this.schoolingLength = "0";
      this.studyType = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.educations = KANConstants.getKANAccountConstants( super.getAccountId() ).getEducations( request.getLocale().getLanguage(), getCorpId() );

      if ( this.educations != null )
      {
         educations.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final String ignoreProperties[] = { "employeeId", "employeeEducationId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeEducationId()
   {
      return employeeEducationId;
   }

   public void setEmployeeEducationId( String employeeEducationId )
   {
      this.employeeEducationId = employeeEducationId;
   }

   public String getSchoolName()
   {
      return schoolName;
   }

   public void setSchoolName( String schoolName )
   {
      this.schoolName = schoolName;
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

   public String getMajor()
   {
      return major;
   }

   public void setMajor( String major )
   {
      this.major = major;
   }

   public String getEducationId()
   {
      return educationId;
   }

   public void setEducationId( String educationId )
   {
      this.educationId = educationId;
   }

   public String getDecodeEducationId()
   {
      return decodeField( educationId, educations );
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

   public List< MappingVO > getEducations()
   {
      return educations;
   }

   public void setEducations( List< MappingVO > educations )
   {
      this.educations = educations;
   }

   public String getSchoolingLength()
   {
      return schoolingLength;
   }

   public void setSchoolingLength( String schoolingLength )
   {
      this.schoolingLength = schoolingLength;
   }

   public String getStudyType()
   {
      return studyType;
   }

   public void setStudyType( String studyType )
   {
      this.studyType = studyType;
   }

   // Tab 显示
   public String getRemark()
   {
      String ret = getSchoolName();

      if ( KANUtil.filterEmpty( educationId, "0" ) != null )
      {
         ret = ret + " [ " + getDecodeEducationId() + " ] ";
      }

      ret = ret + getStartDate() + " ~ ";

      if ( KANUtil.filterEmpty( endDate ) != null )
      {
         ret = ret + getEndDate();
      }
      else
      {
         ret = ret + ( this.getLocale() != null && "zh".equalsIgnoreCase( this.getLocale().getLanguage() ) ? "现在" : "now" );
      }

      return ret;
   }
}
