package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;

public class PositionDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1716719207798393744L;

   // 当前Position对象
   private PositionVO positionVO = new PositionVO();

   // 用于生成Position树
   private List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

   // 当前Position属于哪些Position Group
   private List< PositionGroupRelationVO > positionGroupRelationVOs = new ArrayList< PositionGroupRelationVO >();

   // 当前Position挂上了哪些员工
   private List< PositionStaffRelationVO > positionStaffRelationVOs = new ArrayList< PositionStaffRelationVO >();

   // 当前Position对哪些模块有权限或规则
   private List< PositionModuleDTO > positionModuleDTOs = new ArrayList< PositionModuleDTO >();

   // for shoot
   private List< MappingVO > staffMappingVOs = new ArrayList< MappingVO >();

   // for export branch excel
   private List< BranchVO > parentBranchVOs = new ArrayList< BranchVO >();

   // for export position excel 保存当前职位以及父职位
   private List< PositionVO > parentPositionVOS = new ArrayList< PositionVO >();

   // for history export excel
   List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

   public PositionVO getPositionVO()
   {
      return positionVO;
   }

   public void setPositionVO( final PositionVO positionVO )
   {
      this.positionVO = positionVO;
   }

   public List< PositionDTO > getPositionDTOs()
   {
      return positionDTOs;
   }

   public void setPositionDTOs( final List< PositionDTO > positionDTOs )
   {
      this.positionDTOs = positionDTOs;
   }

   public List< PositionGroupRelationVO > getPositionGroupRelationVOs()
   {
      return positionGroupRelationVOs;
   }

   public void setPositionGroupRelationVOs( final List< PositionGroupRelationVO > positionGroupRelationVOs )
   {
      this.positionGroupRelationVOs = positionGroupRelationVOs;
   }

   public List< PositionStaffRelationVO > getPositionStaffRelationVOs()
   {
      return positionStaffRelationVOs;
   }

   public void setPositionStaffRelationVOs( final List< PositionStaffRelationVO > positionStaffRelationVOs )
   {
      this.positionStaffRelationVOs = positionStaffRelationVOs;
   }

   public List< PositionModuleDTO > getPositionModuleDTOs()
   {
      return positionModuleDTOs;
   }

   public void setPositionModuleDTOs( final List< PositionModuleDTO > positionModuleDTOs )
   {
      this.positionModuleDTOs = positionModuleDTOs;
   }

   // 按照ModuleId得到PositionModuleDTO
   public PositionModuleDTO getPositionModuleDTOByModuleId( final String moduleId )
   {
      // 如果当前职位设置过跟Module，Right和Rule之间的关系
      if ( positionModuleDTOs != null && positionModuleDTOs.size() > 0 )
      {
         for ( PositionModuleDTO positionModuleDTO : positionModuleDTOs )
         {
            if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() != null
                  && positionModuleDTO.getModuleVO().getModuleId().equals( moduleId ) )
            {
               return positionModuleDTO;
            }
         }
      }

      return null;
   }

   // 得到整个职位下设置过的模块
   public List< ModuleVO > getModuleVOs()
   {
      final List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();

      // 如果当前职位设置过跟Module，Right和Rule之间的关系
      if ( positionModuleDTOs != null && positionModuleDTOs.size() > 0 )
      {
         for ( PositionModuleDTO positionModuleDTO : positionModuleDTOs )
         {
            moduleVOs.add( positionModuleDTO.getModuleVO() );
         }
      }

      return moduleVOs;
   }

   // 查询当前Position是否包含目标Staff
   public boolean containsStaffId( final String staffId )
   {
      if ( staffId != null && !staffId.trim().equals( "" ) && positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationVOs )
         {
            if ( positionStaffRelationVO.getStaffId() != null && positionStaffRelationVO.getStaffId().trim().equals( staffId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public void resetStaffMappingVOs()
   {
      fetchResetStaffMappingVOs( this );
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         for ( PositionDTO positionDTO : positionDTOs )
         {
            positionDTO.resetStaffMappingVOs();
         }
      }
   }

   public void fetchResetStaffMappingVOs( final PositionDTO positionDTO )
   {
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( positionVO.getAccountId() );
      final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getPositionId() );
      final List< MappingVO > staffMappingVO = new ArrayList< MappingVO >();
      for ( StaffDTO staffDTO : staffDTOs )
      {
         final List< PositionStaffRelationVO > positionStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
         PositionStaffRelationVO tempRelationVO = new PositionStaffRelationVO();
         for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationVOs )
         {
            if ( positionVO.getPositionId().equals( positionStaffRelationVO.getPositionId() ) )
            {
               tempRelationVO = positionStaffRelationVO;
            }
         }
         // 职位名称，员工姓名，职位是否是代理
         staffMappingVO.add( new MappingVO( staffDTO.getStaffVO().getStaffId(), staffDTO.getStaffVO().getNameZH(), tempRelationVO.getStaffType() ) );
      }
      positionDTO.setStaffMappingVOs( staffMappingVO );
   }

   public List< MappingVO > getStaffMappingVOs()
   {
      return staffMappingVOs;
   }

   public void setStaffMappingVOs( List< MappingVO > employeeMappingVOs )
   {
      this.staffMappingVOs = employeeMappingVOs;
   }

   public List< BranchVO > getParentBranchVOs()
   {
      return parentBranchVOs;
   }

   public void setParentBranchVOs( List< BranchVO > parentBranchVOs )
   {
      this.parentBranchVOs = parentBranchVOs;
   }

   public List< PositionVO > getParentPositionVOS()
   {
      return parentPositionVOS;
   }

   public void setParentPositionVOS( List< PositionVO > parentPositionVOS )
   {
      this.parentPositionVOS = parentPositionVOS;
   }

   public List< StaffDTO > getStaffDTOs()
   {
      return staffDTOs;
   }

   public void setStaffDTOs( List< StaffDTO > staffDTOs )
   {
      this.staffDTOs = staffDTOs;
   }

   public List< String > getChildPositionIds()
   {
      final List< String > childPositionIds = new ArrayList< String >();
      return fetchChildPositionIds( childPositionIds, positionDTOs );
   }

   private List< String > fetchChildPositionIds( final List< String > childPositionIds, final List< PositionDTO > positionDTOs )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         for ( PositionDTO dto : positionDTOs )
         {
            if ( dto != null && dto.getPositionVO() != null )
            {
               childPositionIds.add( dto.getPositionVO().getPositionId() );
               fetchChildPositionIds( childPositionIds, dto.getPositionDTOs() );
            }
         }
      }

      return childPositionIds;
   }

}
