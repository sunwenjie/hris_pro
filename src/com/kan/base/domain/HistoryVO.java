package com.kan.base.domain;

import java.io.Serializable;
import java.util.Date;

import com.kan.base.util.KANUtil;

public class HistoryVO implements Serializable
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -4490781681039470081L;

   /**
    * For DB
    */
   // 历史记录编号
   private String historyId;

   private String accountId;

   // 工作流编号
   private String workflowId;

   // 历史更改标题
   private String hisTitle = " ";

   // 历史更改描述
   private String hisDescription;

   // 目标对象ID
   private String objectId;

   // 目标对象Type （1:历史，2:工作流）
   private String objectType;

   // 模块中的操作链接
   private String accessAction;

   // 对象类名（例如，com.kan.base.domain.BaseVO）
   private String objectClass;

   // Service Name（例如，Spring定义的Bean）
   private String serviceBean;

   // Service Method（例如，insertStaff）
   private String serviceMethod;

   // Service serviceGetObjByIdMethod
   private String serviceGetObjByIdMethod;

   // 审核通过目标对象的Jason字符串
   private String passObject;

   // 审核失败目标对象的Jason字符串
   private String failObject;

   // 用以判断当前用户使用哪个职位进行操作
   private String positionId;

   // 用来标识最新审批人的意见（1：同意；2：同意）
   private String tempStatus;

   /**
    * For Application
    */
   // 审核对象的对接人
   private String owner;

   // 模块ID
   private String moduleId;

   // 权限ID
   private String rightId;

   // 对象中文名
   private String nameZH;

   // 对象英文名
   private String nameEN;

   private String remark1;

   private String remark2;

   private String remark3;

   private String remark4;

   private String remark5;

   private String deleted;

   private String status;

   private String createBy;

   private Date createDate = new Date();

   private String modifyBy;

   private Date modifyDate = new Date();

   public String getHistoryId()
   {
      return historyId;
   }

   public void setHistoryId( String historyId )
   {
      this.historyId = historyId;
   }

   public String getWorkflowId()
   {
      return workflowId;
   }

   public void setWorkflowId( String workflowId )
   {
      this.workflowId = workflowId;
   }

   public String getHisTitle()
   {
      return hisTitle;
   }

   public void setHisTitle( String hisTitle )
   {
      this.hisTitle = hisTitle;
   }

   public String getHisDescription()
   {
      return hisDescription;
   }

   public void setHisDescription( String hisDescription )
   {
      this.hisDescription = hisDescription;
   }

   public String getObjectId()
   {
      return objectId;
   }

   public void setObjectId( String objectId )
   {
      this.objectId = objectId;
   }

   public String getObjectType()
   {
      return objectType;
   }

   public void setObjectType( String objectType )
   {
      this.objectType = objectType;
   }

   public String getPassObject()
   {
      return passObject;
   }

   public void setPassObject( String passObject )
   {
      this.passObject = passObject;
   }

   public String getFailObject()
   {
      return failObject;
   }

   public void setFailObject( String failObject )
   {
      this.failObject = failObject;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getModuleId()
   {
      return moduleId;
   }

   public void setModuleId( String moduleId )
   {
      this.moduleId = moduleId;
   }

   public String getRightId()
   {
      return rightId;
   }

   public void setRightId( String rightId )
   {
      this.rightId = rightId;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public String getAccessAction()
   {
      return accessAction;
   }

   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   public String getObjectClass()
   {
      return objectClass;
   }

   public void setObjectClass( String objectClass )
   {
      this.objectClass = objectClass;
   }

   public String getServiceBean()
   {
      return serviceBean;
   }

   public void setServiceBean( String serviceBean )
   {
      this.serviceBean = serviceBean;
   }

   public String getServiceMethod()
   {
      return serviceMethod;
   }

   public void setServiceMethod( String serviceMethod )
   {
      this.serviceMethod = serviceMethod;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public String getServiceGetObjByIdMethod()
   {
      return serviceGetObjByIdMethod;
   }

   public void setServiceGetObjByIdMethod( String serviceGetObjByIdMethod )
   {
      this.serviceGetObjByIdMethod = serviceGetObjByIdMethod;
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

   public String getRemark1()
   {
      return remark1;
   }

   public void setRemark1( String remark1 )
   {
      this.remark1 = remark1;
   }

   public String getRemark2()
   {
      return remark2;
   }

   public void setRemark2( String remark2 )
   {
      this.remark2 = remark2;
   }

   public String getRemark3()
   {
      return remark3;
   }

   public void setRemark3( String remark3 )
   {
      this.remark3 = remark3;
   }

   public String getRemark4()
   {
      return remark4;
   }

   public void setRemark4( String remark4 )
   {
      this.remark4 = remark4;
   }

   public String getRemark5()
   {
      return remark5;
   }

   public void setRemark5( String remark5 )
   {
      this.remark5 = remark5;
   }

   public String getDeleted()
   {
      return deleted;
   }

   public void setDeleted( String deleted )
   {
      this.deleted = deleted;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus( String status )
   {
      this.status = status;
   }

   public String getCreateBy()
   {
      return createBy;
   }

   public void setCreateBy( String createBy )
   {
      this.createBy = createBy;
   }

   public Date getCreateDate()
   {
      return createDate;
   }

   public void setCreateDate( Date createDate )
   {
      this.createDate = createDate;
   }

   public String getModifyBy()
   {
      return modifyBy;
   }

   public void setModifyBy( String modifyBy )
   {
      this.modifyBy = modifyBy;
   }

   public Date getModifyDate()
   {
      return modifyDate;
   }

   public void setModifyDate( Date modifyDate )
   {
      this.modifyDate = modifyDate;
   }

   public String getTempStatus()
   {
      return KANUtil.filterEmpty( tempStatus );
   }

   public void setTempStatus( String tempStatus )
   {
      this.tempStatus = tempStatus;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }
}
