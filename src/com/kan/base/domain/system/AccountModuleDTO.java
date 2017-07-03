package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.security.ModuleRightRelationVO;
import com.kan.base.domain.security.ModuleRuleRelationVO;
import com.kan.base.util.KANConstants;

/**
 * 当前Account能访问的Module
 * 
 * @author Kevin
 */
public class AccountModuleDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 3613473959843381707L;

   private ModuleVO moduleVO = new ModuleVO();

   // 用于生成Module树，GlobalModule中不含有此对象
   private List< AccountModuleDTO > accountModuleDTOs = new ArrayList< AccountModuleDTO >();

   // 当前账户全局设定的权限
   private List< ModuleRightRelationVO > moduleRightRelationVOs = new ArrayList< ModuleRightRelationVO >();

   // 当前账户全局设定的规则
   private List< ModuleRuleRelationVO > moduleRuleRelationVOs = new ArrayList< ModuleRuleRelationVO >();

   // 获得账户全局Right对象列表
   public List< RightVO > getAccountRightVOs()
   {
      if ( moduleRightRelationVOs != null && moduleRightRelationVOs.size() > 0 )
      {
         final List< RightVO > rightVOs = new ArrayList< RightVO >();

         for ( ModuleRightRelationVO moduleRightRelationVO : moduleRightRelationVOs )
         {
            rightVOs.add( KANConstants.getRightVOByRightId( moduleRightRelationVO.getRightId() ) );
         }

         return rightVOs;
      }
      else
      {
         return null;
      }
   }

   // 获得账户全局Rule对象列表
   public List< RuleVO > getAccountRuleVOs()
   {
      if ( moduleRuleRelationVOs != null && moduleRuleRelationVOs.size() > 0 )
      {
         final List< RuleVO > ruleVOs = new ArrayList< RuleVO >();

         for ( ModuleRuleRelationVO moduleRuleRelationVO : moduleRuleRelationVOs )
         {
            ruleVOs.add( KANConstants.getRuleVOByRuleId( moduleRuleRelationVO.getRuleId() ) );
         }

         return ruleVOs;
      }
      else
      {
         return null;
      }
   }

   /**
    * For Application 
    */
   public ModuleVO getModuleVO()
   {
      return moduleVO;
   }

   public void setModuleVO( ModuleVO moduleVO )
   {
      this.moduleVO = moduleVO;
   }

   public List< ModuleRightRelationVO > getModuleRightRelationVOs()
   {
      return moduleRightRelationVOs;
   }

   public void setModuleRightRelationVOs( List< ModuleRightRelationVO > moduleRightRelationVOs )
   {
      this.moduleRightRelationVOs = moduleRightRelationVOs;
   }

   public List< ModuleRuleRelationVO > getModuleRuleRelationVOs()
   {
      return moduleRuleRelationVOs;
   }

   public void setModuleRuleRelationVOs( List< ModuleRuleRelationVO > moduleRuleRelationVOs )
   {
      this.moduleRuleRelationVOs = moduleRuleRelationVOs;
   }

   public List< AccountModuleDTO > getAccountModuleDTOs()
   {
      return accountModuleDTOs;
   }

   public void setAccountModuleDTOs( List< AccountModuleDTO > accountModuleDTOs )
   {
      this.accountModuleDTOs = accountModuleDTOs;
   }

}
