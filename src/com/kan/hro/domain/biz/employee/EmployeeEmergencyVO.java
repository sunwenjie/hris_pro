package com.kan.hro.domain.biz.employee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeEmergencyVO extends BaseVO
{

   // Serial Version UID

   private static final long serialVersionUID = -3986770799631492651L;

   // For DB

   // 紧急联系方式Id

   private String employeeEmergencyId;

   // 员工Id

   private String employeeId;

   // 与紧急联系人关系

   private String relationshipId;

   // 姓名

   private String name;

   // 电话号码

   private String phone;

   // 移动电话

   private String mobile;

   // 联系地址

   private String address;

   //
   //  邮编

   private String postcode;

   private String description;

   /**
    *  for applaycation
    */
   @JsonIgnore
   private List< MappingVO > relationshipIds;

   // 雇员姓名

   private String employeeName;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeEmergencyId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.relationshipId = "";
      this.name = null;
      this.phone = null;
      this.mobile = "";
      this.address = "";
      this.postcode = "";
      this.description = "";
      super.setStatus( "" );

   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.relationshipIds = KANUtil.getMappings( this.getLocale(), "business.employee.emergency.relationshipIds" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final String ignoreProperties[] = { "employeeId", "employeeEmergencyId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   public String getEmployeeEmergencyId()
   {
      return employeeEmergencyId;
   }

   public void setEmployeeEmergencyId( String employeeEmergencyId )
   {
      this.employeeEmergencyId = employeeEmergencyId;
   }

   public String getRelationshipId()
   {
      return relationshipId;
   }

   public void setRelationshipId( String relationshipId )
   {
      this.relationshipId = relationshipId;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
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

   public void setMobile( String mobile )
   {
      this.mobile = mobile;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress( String address )
   {
      this.address = address;
   }

   public String getPostcode()
   {
      return postcode;
   }

   public void setPostcode( String postcode )
   {
      this.postcode = postcode;
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

   public String getDecodeRelationshipId()
   {

      return decodeField( this.relationshipId, this.relationshipIds );
   }

   public List< MappingVO > getRelationshipIds()
   {
      return relationshipIds;
   }

   public void setRelationshipIds( List< MappingVO > relationshipIds )
   {
      this.relationshipIds = relationshipIds;
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
}
