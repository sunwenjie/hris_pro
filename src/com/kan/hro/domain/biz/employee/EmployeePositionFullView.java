package com.kan.hro.domain.biz.employee;

public class EmployeePositionFullView extends EmployeeVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4802357876605872752L;

   // ְλ����
   private String positionName;

   // ְ������
   private String gradeName;

   // ���ű��
   private String branchCode;

   // ��������
   private String branchName;

   // ����ʵ��
   private String entityId;

   // ҵ������
   private String businessTypeId;

   public String getPositionName()
   {
      return positionName;
   }

   public void setPositionName( String positionName )
   {
      this.positionName = positionName;
   }

   public String getGradeName()
   {
      return gradeName;
   }

   public void setGradeName( String gradeName )
   {
      this.gradeName = gradeName;
   }

   public String getBranchCode()
   {
      return branchCode;
   }

   public void setBranchCode( String branchCode )
   {
      this.branchCode = branchCode;
   }

   public String getBranchName()
   {
      return branchName;
   }

   public void setBranchName( String branchName )
   {
      this.branchName = branchName;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

}
