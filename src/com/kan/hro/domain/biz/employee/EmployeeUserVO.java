package com.kan.hro.domain.biz.employee;

import net.sf.json.JsonConfig;

import com.kan.base.domain.security.UserVO;

public class EmployeeUserVO extends UserVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 3954164885028083416L;

   /**
    * For DB
    */

   // 雇员用户ID
   private String employeeUserId;

   // 雇员ID
   private String employeeId;

   // 雇员名
   private String employeeName;

   // 雇员界面不设置
   private String superUserId;

   // 雇员界面不设置（Yes，No）
   private String validatedSuperUser;

   public static JsonConfig EMPLOYEEUSERVO_JSONCONFIG = new JsonConfig();

   static
   {
      EMPLOYEEUSERVO_JSONCONFIG.setExcludes( new String[] {  "clientId","currentPositionId","emptyMappingVO","encodedClientId","extended","flags",
            "historyVO","statuses","password", "bindIP", "lastLogin", "lastLoginIP",
            "createDate", "modifyDate",  "createBy", "deleted",
            "deleteds", "decodeCreateBy", "decodeCreateDate", "decodeDeleted", "decodeLastLogin", "decodeModifyBy", "decodeModifyDate", "decodeStatus", "emptyMappingVO", "flags",
            "modifyBy", "multipartRequestHandler", "passwordConfirm", "remark3", "remark4", "remark5", "searchField", "selectedIds", "servletWrapper", "sortColumn", "sortOrder",
            "statuses", "subAction", 
            "hasIn" , "inList" , "locale" ,"objectClass","notIn","notInList","validatedSuperUser"     
      } );
   }

   public String getEmployeeUserId()
   {
      return employeeUserId;
   }

   public void setEmployeeUserId( String employeeUserId )
   {
      this.employeeUserId = employeeUserId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getSuperUserId()
   {
      return superUserId;
   }

   public void setSuperUserId( String superUserId )
   {
      this.superUserId = superUserId;
   }

   public String getValidatedSuperUser()
   {
      return validatedSuperUser;
   }

   public void setValidatedSuperUser( String validatedSuperUser )
   {
      this.validatedSuperUser = validatedSuperUser;
   }

}
