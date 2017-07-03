package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.system.ModuleVO;
import com.kan.base.util.KANUtil;

/**
 * 封装Account对应的Group对象
 * 
 * @author Kevin
 */
public class GroupDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -842672533889138340L;

   // 当前Position Group对象
   private GroupVO groupVO = new GroupVO();

   // 当前Group包含哪些Position
   private List< PositionGroupRelationVO > positionGroupRelationVOs = new ArrayList< PositionGroupRelationVO >();

   // 当前Group对哪些模块有权限或规则
   private List< GroupModuleDTO > groupModuleDTOs = new ArrayList< GroupModuleDTO >();
   
   private List< GroupModuleRuleRelationVO > groupModuleRuleRelationVOs = new ArrayList< GroupModuleRuleRelationVO >();

   public List< PositionGroupRelationVO > getPositionGroupRelationVOs()
   {
      return positionGroupRelationVOs;
   }

   public void setPositionGroupRelationVOs( final List< PositionGroupRelationVO > positionGroupRelationVOs )
   {
      this.positionGroupRelationVOs = positionGroupRelationVOs;
   }

   public GroupVO getGroupVO()
   {
      return groupVO;
   }

   public void setGroupVO( GroupVO groupVO )
   {
      this.groupVO = groupVO;
   }

   public List< GroupModuleDTO > getGroupModuleDTOs()
   {
      return groupModuleDTOs;
   }

   public void setGroupModuleDTOs( List< GroupModuleDTO > groupModuleDTOs )
   {
      this.groupModuleDTOs = groupModuleDTOs;
   }

   // 按照ModuleId得到GroupModuleDTO
   public GroupModuleDTO getGroupModuleDTOByModuleId( final String moduleId )
   {
      // 如果当前职位组设置过跟Module，Right和Rule之间的关系
      if ( groupModuleDTOs != null && groupModuleDTOs.size() > 0 )
      {
         for ( GroupModuleDTO groupModuleDTO : groupModuleDTOs )
         {
            if ( groupModuleDTO.getModuleVO() != null && KANUtil.filterEmpty( groupModuleDTO.getModuleVO().getModuleId() ) != null
                  && KANUtil.filterEmpty( groupModuleDTO.getModuleVO().getModuleId() ).equals( moduleId ) )
            {
               return groupModuleDTO;
            }
         }
      }

      return null;
   }

   // 得到整个职位组下设置过的模块
   public List< ModuleVO > getModuleVOs()
   {
      final List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();

      // 如果当前职位组设置过跟Module，Right和Rule之间的关系
      if ( groupModuleDTOs != null && groupModuleDTOs.size() > 0 )
      {
         for ( GroupModuleDTO groupModuleDTO : groupModuleDTOs )
         {
            moduleVOs.add( groupModuleDTO.getModuleVO() );
         }
      }

      return moduleVOs;
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
