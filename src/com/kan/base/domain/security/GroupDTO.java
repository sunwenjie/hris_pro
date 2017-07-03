package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.system.ModuleVO;
import com.kan.base.util.KANUtil;

/**
 * ��װAccount��Ӧ��Group����
 * 
 * @author Kevin
 */
public class GroupDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -842672533889138340L;

   // ��ǰPosition Group����
   private GroupVO groupVO = new GroupVO();

   // ��ǰGroup������ЩPosition
   private List< PositionGroupRelationVO > positionGroupRelationVOs = new ArrayList< PositionGroupRelationVO >();

   // ��ǰGroup����Щģ����Ȩ�޻����
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

   // ����ModuleId�õ�GroupModuleDTO
   public GroupModuleDTO getGroupModuleDTOByModuleId( final String moduleId )
   {
      // �����ǰְλ�����ù���Module��Right��Rule֮��Ĺ�ϵ
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

   // �õ�����ְλ�������ù���ģ��
   public List< ModuleVO > getModuleVOs()
   {
      final List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();

      // �����ǰְλ�����ù���Module��Right��Rule֮��Ĺ�ϵ
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
