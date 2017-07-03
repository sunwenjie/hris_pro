package com.kan.base.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;

public class BranchDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -7062021385051978150L;

   private BranchVO branchVO = new BranchVO();

   private List< BranchDTO > branchDTOs = new ArrayList< BranchDTO >();

   // for 部门快照
   private EntityVO entityVO = new EntityVO();

   private List< MappingVO > staffMappingVOs = new ArrayList< MappingVO >();

   // for history export excel
   List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

   public BranchVO getBranchVO()
   {
      return branchVO;
   }

   public void setBranchVO( BranchVO branchVO )
   {
      this.branchVO = branchVO;
   }

   public List< BranchDTO > getBranchDTOs()
   {
      return branchDTOs;
   }

   public void setBranchDTOs( List< BranchDTO > branchDTOs )
   {
      this.branchDTOs = branchDTOs;
   }

   public BranchDTO getBranchDTOByBranchId( final String branchId )
   {
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO branchDTO : branchDTOs )
         {
            if ( branchDTO.getBranchVO() != null && branchDTO.getBranchVO().getBranchId() != null && branchDTO.getBranchVO().getBranchId().equals( branchId ) )
            {
               return branchDTO;
            }
         }
      }

      return null;
   }

   public List< BranchVO > getBranchVOs()
   {

      final List< BranchVO > branchVOs = new ArrayList< BranchVO >();
      // 如果当前职位设置过跟Module，Right和Rule之间的关系
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO branchDTO : branchDTOs )
         {
            branchVOs.add( branchDTO.getBranchVO() );
         }
      }

      return branchVOs;
   }

   public EntityVO getEntityVO()
   {
      return entityVO;
   }

   public void setEntityVO( EntityVO entityVO )
   {
      this.entityVO = entityVO;
   }

   public void resetEntity()
   {
      final String entityId = this.branchVO.getEntityId();
      this.entityVO = KANConstants.getKANAccountConstants( this.branchVO.getAccountId() ).getEntityVOByEntityId( entityId );
   }

   public void resetStaffMappingVOs()
   {
      fetchResetStaffMappingVOs( this );
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO branchDTO : branchDTOs )
         {
            branchDTO.resetStaffMappingVOs();
         }
      }
   }

   public void fetchResetStaffMappingVOs( final BranchDTO branchDTO )
   {
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( branchDTO.getBranchVO().getAccountId() );
      final List< PositionVO > positionVOs = accountConstants.getPositionVOsByBranchId( branchDTO.getBranchVO().getBranchId() );
      final List< MappingVO > staffMappingVO = new ArrayList< MappingVO >();
      for ( PositionVO positionVO : positionVOs )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getPositionId() );
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
            // staffId，员工姓名，职位是否是代理
            staffMappingVO.add( new MappingVO( staffDTO.getStaffVO().getStaffId(), staffDTO.getStaffVO().getNameZH(), tempRelationVO.getStaffType() ) );
         }
      }
      branchDTO.setStaffMappingVOs( staffMappingVO );
   }

   public List< MappingVO > getStaffMappingVOs()
   {
      return staffMappingVOs;
   }

   public void setStaffMappingVOs( List< MappingVO > staffMappingVOs )
   {
      this.staffMappingVOs = staffMappingVOs;
   }

   public List< StaffDTO > getStaffDTOs()
   {
      return staffDTOs;
   }

   public void setStaffDTOs( List< StaffDTO > staffDTOs )
   {
      this.staffDTOs = staffDTOs;
   }

}
