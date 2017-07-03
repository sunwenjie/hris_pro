package com.kan.base.domain.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class GroupVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 3155189394559603485L;

   // 职位组ID
   private String groupId;

   // 职位组中文名
   private String nameZH;

   // 职位组英文名
   private String nameEN;

   // HR职能
   private String hrFunction;

   // 职位组描述
   private String description;

   // 数据角色
   private String dataRole;

   /**
    * For App
    */
   // 职组内包含的职位
   private String[] positionIdArray = new String[] {};
   @JsonIgnore
   // 对应的moduleId
   private String[] moduleIdArray = new String[] {};
   @JsonIgnore
   // 访问权限
   private String[] rightIdArray = new String[] {};
   @JsonIgnore
   // 数据权限
   private String[] ruleIdArray = new String[] {};
   @JsonIgnore
   //数据权限
   private String[] ruleIds = new String[] {};
   @JsonIgnore
   //数据权限-部门
   private String[] branchIds;
   @JsonIgnore
   //数据权限-职位
   private String[] positionIds;
   @JsonIgnore
   //数据权限-职级
   private String[] positionGradeIds;
   @JsonIgnore
   //数据权限-项目
   private String[] businessTypeIds;
   @JsonIgnore
   //数据权限-法务实体
   private String[] entityIds;
   @JsonIgnore
   private List< MappingVO > dataRoles = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.dataRoles = KANUtil.getMappings( this.getLocale(), "security.group.datarole" );
   }

   @Override
   public String getEncodedId() throws KANException
   {

      return encodedField( groupId );
   }

   public void update( Object object )
   {
      final GroupVO groupVO = ( GroupVO ) object;
      this.nameEN = groupVO.getNameEN();
      this.nameZH = groupVO.getNameZH();
      this.hrFunction = groupVO.getHrFunction();
      this.description = groupVO.getDescription();
      this.positionIdArray = groupVO.getPositionIdArray();
      this.moduleIdArray = groupVO.getModuleIdArray();
      this.rightIdArray = groupVO.getRightIdArray();
      this.ruleIdArray = groupVO.getRuleIdArray();
      this.dataRole = groupVO.getDataRole();
      this.ruleIds = groupVO.getRuleIds();
      this.branchIds = groupVO.getBranchIds();
      this.positionIds = groupVO.getPositionIds();
      this.positionGradeIds = groupVO.getPositionGradeIds();
      this.businessTypeIds = groupVO.getBusinessTypeIds();
      this.entityIds = groupVO.getEntityIds();
      super.setStatus( groupVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public void reset()
   {
      this.nameEN = "";
      this.nameZH = "";
      this.description = "";
      this.positionIdArray = new String[] {};
      this.moduleIdArray = new String[] {};
      this.rightIdArray = new String[] {};
      this.ruleIdArray = new String[] {};
      this.dataRole = "";
      this.ruleIds = new String[] {};
      this.branchIds = null;
      this.positionIds = null;
      this.positionGradeIds = null;
      this.businessTypeIds = null;
      this.entityIds = null;
      super.setStatus( "0" );
   }

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
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

   public String getHrFunction()
   {
      return hrFunction;
   }

   public void setHrFunction( String hrFunction )
   {
      this.hrFunction = hrFunction;
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public String[] getModuleIdArray()
   {
      return moduleIdArray;
   }

   public void setModuleIdArray( String[] moduleIdArray )
   {
      this.moduleIdArray = moduleIdArray;
   }

   public String[] getRightIdArray()
   {
      return rightIdArray;
   }

   public void setRightIdArray( String[] rightIdArray )
   {
      this.rightIdArray = rightIdArray;
   }

   public String[] getRuleIdArray()
   {
      return ruleIdArray;
   }

   public void setRuleIdArray( String[] ruleIdArray )
   {
      this.ruleIdArray = ruleIdArray;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getDataRole()
   {
      return dataRole;
   }

   public void setDataRole( String dataRole )
   {
      this.dataRole = dataRole;
   }

   public List< MappingVO > getDataRoles()
   {
      return dataRoles;
   }

   public void setDataRoles( List< MappingVO > dataRoles )
   {
      this.dataRoles = dataRoles;
   }

   public String getDataRoleName()
   {

      return decodeField( dataRole, dataRoles );
   }

   public String[] getRuleIds()
   {
      return ruleIds;
   }

   public void setRuleIds( String[] ruleIds )
   {
      this.ruleIds = ruleIds;
   }

   public String[] getBranchIds()
   {
      return branchIds;
   }

   public void setBranchIds( String[] branchIds )
   {
      this.branchIds = branchIds;
   }

   public String[] getPositionIds()
   {
      return positionIds;
   }

   public void setPositionIds( String[] positionIds )
   {
      this.positionIds = positionIds;
   }

   public String[] getPositionGradeIds()
   {
      return positionGradeIds;
   }

   public void setPositionGradeIds( String[] positionGradeIds )
   {
      this.positionGradeIds = positionGradeIds;
   }

   public String[] getBusinessTypeIds()
   {
      return businessTypeIds;
   }

   public void setBusinessTypeIds( String[] businessTypeIds )
   {
      this.businessTypeIds = businessTypeIds;
   }

   public String[] getEntityIds()
   {
      return entityIds;
   }

   public void setEntityIds( String[] entityIds )
   {
      this.entityIds = entityIds;
   }
}
