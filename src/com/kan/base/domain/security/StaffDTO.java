package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.system.ModuleDTO;

/** 
 * @author Kevin
 */
public class StaffDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -842672533889138340L;

   // ��ǰStaff����
   private StaffVO staffVO = new StaffVO();

   // ��ǰStaff�����Ӧ��User����
   private UserVO userVO = new UserVO();

   // ��ǰStaff������ЩPosition
   private List< PositionStaffRelationVO > positionStaffRelationVOs = new ArrayList< PositionStaffRelationVO >();

   // ��ǰStaff����Щģ����Ȩ�޻����
   private List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

   //�ϼ�position
   private List< String > parentPositions = new ArrayList< String >();

   //�¼�position
   private List< String > childPositions = new ArrayList< String >();

   //ͬ����position
   private List< String > branchPositions = new ArrayList< String >();

   // for export branch excel ֻ���浱ǰְλ
   private List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

   public UserVO getUserVO()
   {
      return userVO;
   }

   public void setUserVO( UserVO userVO )
   {
      this.userVO = userVO;
   }

   public StaffVO getStaffVO()
   {
      return staffVO;
   }

   public void setStaffVO( StaffVO staffVO )
   {
      this.staffVO = staffVO;
   }

   public List< PositionStaffRelationVO > getPositionStaffRelationVOs()
   {
      return positionStaffRelationVOs;
   }

   public void setPositionStaffRelationVOs( List< PositionStaffRelationVO > positionStaffRelationVOs )
   {
      this.positionStaffRelationVOs = positionStaffRelationVOs;
   }

   public List< ModuleDTO > getModuleDTOs()
   {
      return moduleDTOs;
   }

   public void setModuleDTOs( List< ModuleDTO > moduleDTOs )
   {
      this.moduleDTOs = moduleDTOs;
   }

   public ModuleDTO getModuleDTOByModuleId( final String moduleId )
   {
      // �����ǰְλ�����ù���Module��Right��Rule֮��Ĺ�ϵ
      if ( moduleDTOs != null && moduleDTOs.size() > 0 )
      {
         for ( ModuleDTO moduleDTO : moduleDTOs )
         {
            if ( moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getModuleId() != null && moduleDTO.getModuleVO().getModuleId().equals( moduleId ) )
            {
               return moduleDTO;
            }
         }
      }

      return null;
   }

   public PositionStaffRelationVO getPositionStaffRelationVOByPositionId( final String positionId )
   {
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( PositionStaffRelationVO relationVO : positionStaffRelationVOs )
         {
            if ( relationVO != null && relationVO.getPositionId().equals( positionId ) )
            {
               return relationVO;
            }
         }
      }

      return null;
   }

   public List< String > getParentPositions()
   {
      return parentPositions;
   }

   public void setParentPositions( List< String > parentPositions )
   {
      this.parentPositions = parentPositions;
   }

   public List< String > getChildPositions()
   {
      return childPositions;
   }

   public void setChildPositions( List< String > childPositions )
   {
      this.childPositions = childPositions;
   }

   public List< String > getBranchPositions()
   {
      return branchPositions;
   }

   public void setBranchPositions( List< String > branchPositions )
   {
      this.branchPositions = branchPositions;
   }

   public List< PositionDTO > getPositionDTOs()
   {
      return positionDTOs;
   }

   public void setPositionDTOs( List< PositionDTO > positionDTOs )
   {
      this.positionDTOs = positionDTOs;
   }

}
