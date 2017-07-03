package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.system.RightVO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.util.KANConstants;

public class GroupModuleDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 6282819235171246551L;

   private ModuleVO moduleVO = new ModuleVO();

   // 职位组的权限
   private List< GroupModuleRightRelationVO > groupModuleRightRelationVOs = new ArrayList< GroupModuleRightRelationVO >();

   // 职位组的规则
   private List< GroupModuleRuleRelationVO > groupModuleRuleRelationVOs = new ArrayList< GroupModuleRuleRelationVO >();

   // 获得职位组关联的Right对象列表
   public List< RightVO > getSelectedRightVOs()
   {
      if ( groupModuleRightRelationVOs != null && groupModuleRightRelationVOs.size() > 0 )
      {
         final List< RightVO > rightVOs = new ArrayList< RightVO >();

         for ( GroupModuleRightRelationVO groupModuleRightRelationVO : groupModuleRightRelationVOs )
         {
            rightVOs.add( KANConstants.getRightVOByRightId( groupModuleRightRelationVO.getRightId() ) );
         }

         return rightVOs;
      }
      else
      {
         return null;
      }
   }

   // 获得职位组关联的Rule对象列表
   public List< RuleVO > getSelectedRuleVOs()
   {
      if ( groupModuleRuleRelationVOs != null && groupModuleRuleRelationVOs.size() > 0 )
      {
         final List< RuleVO > ruleVOs = new ArrayList< RuleVO >();

         for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleRuleRelationVOs )
         {
            ruleVOs.add( KANConstants.getRuleVOByRuleId( groupModuleRuleRelationVO.getRuleId() ) );
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

   public List< GroupModuleRightRelationVO > getGroupModuleRightRelationVOs()
   {
      return groupModuleRightRelationVOs;
   }

   public void setGroupModuleRightRelationVOs( List< GroupModuleRightRelationVO > groupModuleRightRelationVOs )
   {
      this.groupModuleRightRelationVOs = groupModuleRightRelationVOs;
   }

   public List< GroupModuleRuleRelationVO > getGroupModuleRuleRelationVOs()
   {
      return groupModuleRuleRelationVOs;
   }

   public void setGroupModuleRuleRelationVOs( List< GroupModuleRuleRelationVO > groupModuleRuleRelationVOs )
   {
      this.groupModuleRuleRelationVOs = groupModuleRuleRelationVOs;
   }

}
