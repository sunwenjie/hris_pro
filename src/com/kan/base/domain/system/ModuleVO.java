package com.kan.base.domain.system;

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

/**
 * @author Kevin Jin 
 */
public class ModuleVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 2767357035271439535L;

   /**
    * For DB
    */
   private String moduleId;

   private String moduleName;
   
   private int moduleFlag;

   private String nameZH;

   private String nameEN;

   private String titleZH;

   private String titleEN;

   private String property;

   private String moduleType;

   private String accessAction;

   private String defaultAction;

   private String listAction;

   private String newAction;

   private String toNewAction;

   private String modifyAction;

   private String toModifyAction;

   private String deleteAction;

   private String deletesAction;

   private String parentModuleId;

   private String levelOneModuleName;

   private String levelTwoModuleName;

   private String levelThreeModuleName;

   private String description;

   private String moduleIndex;

   private String rightIds;

   private String ruleIds;
   
   private String role;

   /**
    * For Application
    */
   private String staffId;

   private List< MappingVO > moduleTypes = new ArrayList< MappingVO >();
   private List< MappingVO > moduleFlags = new ArrayList< MappingVO >();

   private String[] rightIdArray;

   private String[] ruleIdArray;
   
   private String[] moduleIdArray;
   
   private String menuRole;
   
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.moduleTypes = KANUtil.getMappings( this.getLocale(), "sys.module.types" );
      this.moduleFlags = KANUtil.getMappings( this.getLocale(), "sys.module.moduleFlag" );
   }

   public String getDecodeModuleType()
   {
      return decodeField( moduleType, moduleTypes );
   }
   
   public String getDecodeModuleFlag()
   {
      return decodeField( moduleFlag+"", moduleFlags);
   }

   @Override
   public void update( final Object object )
   {
      final ModuleVO moduleVO = ( ModuleVO ) object;
      this.moduleName = moduleVO.getModuleName();
      this.moduleFlag = moduleVO.getModuleFlag();
      this.nameZH = moduleVO.getNameZH();
      this.nameEN = moduleVO.getNameEN();
      this.titleZH = moduleVO.getTitleZH();
      this.titleEN = moduleVO.getTitleEN();
      this.property = moduleVO.getProperty();
      this.moduleType = moduleVO.getModuleType();
      this.accessAction = moduleVO.getAccessAction();
      this.defaultAction = moduleVO.getDefaultAction();
      this.listAction = moduleVO.getListAction();
      this.newAction = moduleVO.getNewAction();
      this.toNewAction = moduleVO.getToNewAction();
      this.modifyAction = moduleVO.getModifyAction();
      this.toModifyAction = moduleVO.getToModifyAction();
      this.deleteAction = moduleVO.getDeleteAction();
      this.deletesAction = moduleVO.getDeletesAction();
      this.parentModuleId = moduleVO.getParentModuleId();
      this.levelOneModuleName = moduleVO.getLevelOneModuleName();
      this.levelTwoModuleName = moduleVO.getLevelTwoModuleName();
      this.levelThreeModuleName = moduleVO.getLevelThreeModuleName();
      this.description = moduleVO.getDescription();
      this.moduleIndex = moduleVO.getModuleIndex();
      this.rightIds = moduleVO.getRightIds();
      this.ruleIds = moduleVO.getRuleIds();
      this.rightIdArray = moduleVO.getRightIdArray();
      this.ruleIdArray = moduleVO.getRuleIdArray();
      super.setStatus( moduleVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.moduleName = "";
      this.moduleFlag = 0;
      this.nameZH = "";
      this.nameEN = "";
      this.titleZH = "";
      this.titleEN = "";
      this.property = "";
      this.moduleType = "0";
      this.accessAction = "";
      this.defaultAction = "";
      this.listAction = "";
      this.newAction = "";
      this.toNewAction = "";
      this.modifyAction = "";
      this.toModifyAction = "";
      this.deleteAction = "";
      this.deletesAction = "";
      this.parentModuleId = "0";
      this.levelOneModuleName = "";
      this.levelTwoModuleName = "";
      this.levelThreeModuleName = "";
      this.description = "";
      this.moduleIndex = "";
      this.rightIds = "";
      this.ruleIds = "";
      this.rightIdArray = null;
      this.ruleIdArray = null;
      super.setStatus( "0" );
   }

   public String getModuleId()
   {
      return moduleId;
   }

   public void setModuleId( String moduleId )
   {
      this.moduleId = moduleId;
   }

   public String getModuleName()
   {
      return moduleName;
   }

   public void setModuleName( String moduleName )
   {
      this.moduleName = moduleName;
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

   public String getProperty()
   {
      return property;
   }

   public void setProperty( String property )
   {
      this.property = property;
   }

   public String getModuleType()
   {
      return moduleType;
   }

   public void setModuleType( String moduleType )
   {
      this.moduleType = moduleType;
   }

   public String getDefaultAction()
   {
      return defaultAction;
   }

   public void setDefaultAction( String defaultAction )
   {
      this.defaultAction = defaultAction;
   }

   public String getListAction()
   {
      return listAction;
   }

   public void setListAction( String listAction )
   {
      this.listAction = listAction;
   }

   public String getNewAction()
   {
      return newAction;
   }

   public void setNewAction( String newAction )
   {
      this.newAction = newAction;
   }

   public String getToNewAction()
   {
      return toNewAction;
   }

   public void setToNewAction( String toNewAction )
   {
      this.toNewAction = toNewAction;
   }

   public String getModifyAction()
   {
      return modifyAction;
   }

   public void setModifyAction( String modifyAction )
   {
      this.modifyAction = modifyAction;
   }

   public String getToModifyAction()
   {
      return toModifyAction;
   }

   public void setToModifyAction( String toModifyAction )
   {
      this.toModifyAction = toModifyAction;
   }

   public String getDeleteAction()
   {
      return deleteAction;
   }

   public void setDeleteAction( String deleteAction )
   {
      this.deleteAction = deleteAction;
   }

   public String getDeletesAction()
   {
      return deletesAction;
   }

   public void setDeletesAction( String deletesAction )
   {
      this.deletesAction = deletesAction;
   }

   public String getParentModuleId()
   {
      return KANUtil.filterEmpty( parentModuleId );
   }

   public void setParentModuleId( String parentModuleId )
   {
      this.parentModuleId = parentModuleId;
   }

   public String getLevelOneModuleName()
   {
      return levelOneModuleName;
   }

   public void setLevelOneModuleName( String levelOneModuleName )
   {
      this.levelOneModuleName = levelOneModuleName;
   }

   public String getLevelTwoModuleName()
   {
      return levelTwoModuleName;
   }

   public void setLevelTwoModuleName( String levelTwoModuleName )
   {
      this.levelTwoModuleName = levelTwoModuleName;
   }

   public String getLevelThreeModuleName()
   {
      return levelThreeModuleName;
   }

   public void setLevelThreeModuleName( String levelThreeModuleName )
   {
      this.levelThreeModuleName = levelThreeModuleName;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( moduleId );
   }

   public List< MappingVO > getModuleTypes()
   {
      return moduleTypes;
   }

   public void setModuleTypes( List< MappingVO > moduleTypes )
   {
      this.moduleTypes = moduleTypes;
   }

   public String getModuleIndex()
   {
      return moduleIndex;
   }

   public void setModuleIndex( String moduleIndex )
   {
      this.moduleIndex = moduleIndex;
   }

   public String getRightIds()
   {
      return rightIds;
   }

   public void setRightIds( String rightIds )
   {
      this.rightIds = rightIds;
   }

   public String getRuleIds()
   {
      return ruleIds;
   }

   public void setRuleIds( String ruleIds )
   {
      this.ruleIds = ruleIds;
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

   public String[] getModuleIdArray()
   {
      return moduleIdArray;
   }

   public void setModuleIdArray( String[] moduleIdArray )
   {
      this.moduleIdArray = moduleIdArray;
   }

   // 如果Super对权限有过设置
   public List< RightVO > getSuperSelectedRightVOs()
   {
      if ( this.rightIds != null && !this.rightIds.trim().equals( "" ) )
      {
         final List< RightVO > rightVOs = new ArrayList< RightVO >();

         for ( String rightId : KANUtil.jasonArrayToStringArray( this.rightIds ) )
         {
            RightVO rightVO = KANConstants.getRightVOByRightId( rightId );
            if ( rightVO!=null )
            {
               rightVOs.add( KANConstants.getRightVOByRightId( rightId ) );
            }
         }

         return rightVOs;
      }

      return null;
   }

   public List< RightVO > getRightVOs()
   {
      final List< RightVO > rightVOs = getSuperSelectedRightVOs();

      // 如果平台管理员有设置过模块对应的权限
      if ( rightVOs != null )
      {
         return rightVOs;
      }
      // 如果平台管理员未设置过模块对应的权限
      else
      {
         return KANConstants.getRightVOs();
      }
   }

   // 如果Super对规则有过设置
   public List< RuleVO > getSuperSelectedRuleVOs()
   {
      if ( ruleIds != null && !ruleIds.trim().equals( "" ) )
      {
         final List< RuleVO > ruleVOs = new ArrayList< RuleVO >();

         for ( String ruleId : KANUtil.jasonArrayToStringArray( ruleIds ) )
         {
            ruleVOs.add( KANConstants.getRuleVOByRuleId( ruleId ) );
         }

         return ruleVOs;
      }

      return null;
   }

   public List< RuleVO > getRuleVOs()
   {
      final List< RuleVO > ruleVOs = getSuperSelectedRuleVOs();
      // 如果平台管理员有设置过模块对应的规则
      if ( ruleVOs != null )
      {
         return ruleVOs;
      }
      // 如果平台管理员未设置过模块对应的规则
      else
      {
         return KANConstants.getRuleVOs();
      }
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public String getAccessAction()
   {
      return accessAction;
   }

   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   public String getTitleZH()
   {
      return titleZH;
   }

   public void setTitleZH( String titleZH )
   {
      this.titleZH = titleZH;
   }

   public String getTitleEN()
   {
      return titleEN;
   }

   public void setTitleEN( String titleEN )
   {
      this.titleEN = titleEN;
   }

	public int getModuleFlag() {
		return moduleFlag;
	}
	
	public void setModuleFlag(int moduleFlag) {
		this.moduleFlag = moduleFlag;
	}

	public List<MappingVO> getModuleFlags() {
		return moduleFlags;
	}

	public void setModuleFlags(List<MappingVO> moduleFlags) {
		this.moduleFlags = moduleFlags;
	}

   public String getRole()
   {
      return role;
   }

   public void setRole( String role )
   {
      this.role = role;
   }

   public String getMenuRole()
   {
      return menuRole;
   }

   public void setMenuRole( String menuRole )
   {
      this.menuRole = menuRole;
   }
}
