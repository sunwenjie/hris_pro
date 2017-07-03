package com.kan.hro.domain.biz.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class EmployeeLogVO extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -4498850380991327008L;

   /**
    * For DB
    */
   private String employeeLogId;

   private String employeeId;

   private String type;

   private String content;

   private String description;

   /**
    * For Application
    */
   private String employeeName;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeLogId );
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.type = "";
      this.content = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   public String getEmployeeLogId()
   {
      return employeeLogId;
   }

   public void setEmployeeLogId( String employeeLogId )
   {
      this.employeeLogId = employeeLogId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getType()
   {
      return type;
   }

   public void setType( String type )
   {
      this.type = type;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

}
