package com.kan.hro.domain.biz.employee;

import java.io.Serializable;

public class EmployeeBaseView implements Serializable
{
   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = 20130802150043L;
   // Ա��Id
   private String id;
   // Ա�����
   private String employeeNo;
   // ������
   private String nameZH;
   // Ӣ����
   private String nameEN;
   // ֤������
   private String certificateNumber;

   private String name;
   
   private String conditions;
   
   private String accountId;
   // ��Ա��
   private String employeeName;

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getConditions()
   {
      return conditions;
   }

   public void setConditions( String conditions )
   {
      this.conditions = conditions;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
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
