package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.system.RightVO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.util.KANConstants;

public class PositionModuleDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 287962704583789124L;

   private ModuleVO moduleVO = new ModuleVO();

   // 职位的权限
   private List< PositionModuleRightRelationVO > positionModuleRightRelationVOs = new ArrayList< PositionModuleRightRelationVO >();

   // 职位的规则
   private List< PositionModuleRuleRelationVO > positionModuleRuleRelationVOs = new ArrayList< PositionModuleRuleRelationVO >();

   // 获得职位关联的Right对象列表
   public List< RightVO > getSelectedRightVOs()
   {
      if ( positionModuleRightRelationVOs != null && positionModuleRightRelationVOs.size() > 0 )
      {
         final List< RightVO > rightVOs = new ArrayList< RightVO >();

         for ( PositionModuleRightRelationVO positionModuleRightRelationVO : positionModuleRightRelationVOs )
         {
            rightVOs.add( KANConstants.getRightVOByRightId( positionModuleRightRelationVO.getRightId() ) );
         }

         return rightVOs;
      }
      else
      {
         return null;
      }
   }

   // 获得职位关联的Rule对象列表
   public List< RuleVO > getSelectedRuleVOs()
   {
      if ( positionModuleRuleRelationVOs != null && positionModuleRuleRelationVOs.size() > 0 )
      {
         final List< RuleVO > ruleVOs = new ArrayList< RuleVO >();

         for ( PositionModuleRuleRelationVO positionModuleRuleRelationVO : positionModuleRuleRelationVOs )
         {
            ruleVOs.add( KANConstants.getRuleVOByRuleId( positionModuleRuleRelationVO.getRuleId() ) );
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

   public List< PositionModuleRightRelationVO > getPositionModuleRightRelationVOs()
   {
      return positionModuleRightRelationVOs;
   }

   public void setPositionModuleRightRelationVOs( List< PositionModuleRightRelationVO > positionModuleRightRelationVOs )
   {
      this.positionModuleRightRelationVOs = positionModuleRightRelationVOs;
   }

   public List< PositionModuleRuleRelationVO > getPositionModuleRuleRelationVOs()
   {
      return positionModuleRuleRelationVOs;
   }

   public void setPositionModuleRuleRelationVOs( List< PositionModuleRuleRelationVO > positionModuleRuleRelationVOs )
   {
      this.positionModuleRuleRelationVOs = positionModuleRuleRelationVOs;
   }

}
