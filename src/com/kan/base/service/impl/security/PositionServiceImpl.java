package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.BranchDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionGroupRelationDao;
import com.kan.base.dao.inf.security.PositionModuleRightRelationDao;
import com.kan.base.dao.inf.security.PositionModuleRuleRelationDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionModuleDTO;
import com.kan.base.domain.security.PositionModuleRightRelationVO;
import com.kan.base.domain.security.PositionModuleRuleRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class PositionServiceImpl extends ContextService implements PositionService
{

   // ע��PositionGroupRelationDao
   private PositionGroupRelationDao positionGroupRelationDao;
   // ע��PositionStaffRelationDao
   private PositionStaffRelationDao positionStaffRelationDao;
   // ע��PositionModuleRightRelationDao
   private PositionModuleRightRelationDao positionModuleRightRelationDao;
   // ע��PositionModuleRuleRelationDao
   private PositionModuleRuleRelationDao positionModuleRuleRelationDao;
   // ע��ModuleDaos
   private ModuleDao moduleDao;
   // ע��StaffDao
   private StaffDao staffDao;
   // ע��EmployeeDao
   private EmployeeDao employeeDao;
   // ע��BranchDao
   private BranchDao branchDao;

   public BranchDao getBranchDao()
   {
      return branchDao;
   }

   public void setBranchDao( BranchDao branchDao )
   {
      this.branchDao = branchDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }

   public PositionGroupRelationDao getPositionGroupRelationDao()
   {
      return positionGroupRelationDao;
   }

   public void setPositionGroupRelationDao( PositionGroupRelationDao positionGroupRelationDao )
   {
      this.positionGroupRelationDao = positionGroupRelationDao;
   }

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public PositionModuleRightRelationDao getPositionModuleRightRelationDao()
   {
      return positionModuleRightRelationDao;
   }

   public void setPositionModuleRightRelationDao( PositionModuleRightRelationDao positionModuleRightRelationDao )
   {
      this.positionModuleRightRelationDao = positionModuleRightRelationDao;
   }

   public PositionModuleRuleRelationDao getPositionModuleRuleRelationDao()
   {
      return positionModuleRuleRelationDao;
   }

   public void setPositionModuleRuleRelationDao( PositionModuleRuleRelationDao positionModuleRuleRelationDao )
   {
      this.positionModuleRuleRelationDao = positionModuleRuleRelationDao;
   }

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   @Override
   public PagedListHolder getPositionVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PositionDao positionDao = ( PositionDao ) getDao();
      pagedListHolder.setHolderSize( positionDao.countPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( positionDao.getPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( positionDao.getPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PositionVO getPositionVOByPositionId( final String positionId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionVOByPositionId( positionId );
   }

   @Override
   // Code review by Kevin at 2013-06-14
   public int updatePosition( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // δ�޸�ǰremove��staffId��Ӧ��EmployeeVO�ġ�_temp...���ֶ�
         removeEmployeeVOTempColumn( positionVO );

         // Update PositionVO
         ( ( PositionDao ) getDao() ).updatePosition( positionVO );

         // ���½���Position��Group�Ĺ���
         insertPositionGroupRelation( positionVO );

         // ���½���Position��Staff�Ĺ���
         insertPositionStaffRelation( positionVO );

         // ����ְλʱ����Ҫ�Ը�ְλ�¼�ְλ�ϵ�EmployeeVO��_tempParentPositionOwners����
         updateSubPositionVOs( positionVO );

         // ���½���Position��Module�Ĺ�����ģ̬���޸��������ݿ⡢�����ٴλ�ȡ��
         //         insertPositionModuleRelation( positionVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   // ����ְλʱ����Ҫ�Ը�ְλ�¼�ְλ�ϵ�EmployeeVO��_tempParentPositionOwners����
   private void updateSubPositionVOs( final PositionVO parentPositionVO ) throws KANException
   {
      final PositionVO searchSubPositionVO = new PositionVO();
      searchSubPositionVO.setAccountId( parentPositionVO.getAccountId() );
      searchSubPositionVO.setParentPositionId( parentPositionVO.getPositionId() );

      final List< Object > subPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( searchSubPositionVO );
      if ( subPositionVOs != null && subPositionVOs.size() > 0 )
      {
         final StringBuffer staffIds = new StringBuffer();
         for ( String staffInfos : parentPositionVO.getStaffIdArray() )
         {
            // StaffInfos ������StaffId��StaffType��AgentStart��AgentEnd
            final String[] staffInfoArray = staffInfos.split( "_" );
            if ( staffInfoArray != null && staffInfoArray.length > 1 )
            {
               if ( KANUtil.filterEmpty( staffIds.toString() ) == null )
               {
                  staffIds.append( staffInfoArray[ 0 ] );
               }
               else
               {
                  staffIds.append( "," + staffInfoArray[ 0 ] );
               }
            }
         }

         for ( Object subPositionVOObject : subPositionVOs )
         {
            final PositionVO subPositionVO = ( PositionVO ) subPositionVOObject;
            final List< Object > subPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( subPositionVO.getPositionId() );

            // ���ڻ�ȡPositionStaffRelationVO�б�
            if ( subPositionStaffRelationVOs != null && subPositionStaffRelationVOs.size() > 0 )
            {
               for ( Object o : subPositionStaffRelationVOs )
               {
                  final PositionStaffRelationVO subPositionStaffRelationVO = ( PositionStaffRelationVO ) o;
                  if ( subPositionStaffRelationVO != null && KANUtil.filterEmpty( subPositionStaffRelationVO.getStaffId() ) != null )
                  {
                     final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( subPositionStaffRelationVO.getStaffId() );
                     if ( staffVO != null )
                     {
                        final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
                        if ( employeeVO != null )
                        {
                           employeeVO.set_tempParentPositionOwners( staffIds.toString() );
                           // �־û�EmployeeVO
                           this.getEmployeeDao().updateEmployee( employeeVO );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   // Add by siuvan.xia at 2014-06-30
   private void removeEmployeeVOTempColumn( final PositionVO positionVO ) throws KANException
   {
      // ��ȡPositionStaffRelationVO�б�
      final List< Object > positionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionVO.getPositionId() );

      // ���ڻ�ȡPositionStaffRelationVO�б�
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( Object o : positionStaffRelationVOs )
         {
            final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) o;
            if ( positionStaffRelationVO != null && KANUtil.filterEmpty( positionStaffRelationVO.getStaffId() ) != null )
            {
               final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffVO != null )
               {
                  final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
                  if ( employeeVO != null )
                  {
                     final List< String > _tempPositionIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempPositionIds() );
                     final List< String > _tempBranchIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempBranchIds() );
                     final List< String > _tempParentBranchIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentBranchIds() );
                     final List< String > _tempParentPositionIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionIds() );
                     final List< String > _tempParentPositionOwnerArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionOwners() );
                     final List< String > _tempParentPositionBranchIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionBranchIds() );
                     final List< String > _tempPositionLocationIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempPositionLocationIds() );
                     final List< String > _tempPositionGradeIdArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempPositionGradeIds() );

                     if ( _tempPositionIdArray != null && _tempPositionIdArray.size() > 0 )
                     {
                        _tempPositionIdArray.remove( positionVO.getPositionId() );
                     }
                     if ( _tempBranchIdArray != null && _tempBranchIdArray.size() > 0 )
                     {
                        _tempBranchIdArray.remove( positionVO.getBranchId() );
                     }
                     if ( _tempParentBranchIdArray != null && _tempParentBranchIdArray.size() > 0 )
                     {
                        final BranchVO branchVO = this.getBranchDao().getBranchVOByBranchId( positionVO.getBranchId() );
                        if ( branchVO != null )
                        {
                           _tempParentBranchIdArray.remove( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId()
                                 : branchVO.getParentBranchId() );
                        }
                     }
                     if ( _tempParentPositionIdArray != null && _tempParentPositionIdArray.size() > 0 )
                     {
                        _tempParentPositionIdArray.remove( positionVO.getParentPositionId() );
                     }

                     if ( _tempParentPositionOwnerArray != null && _tempParentPositionOwnerArray.size() > 0 )
                     {
                        // ��ȡ�ϼ�ְλ��Staff�Ĺ�ϵ����
                        final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionVO.getParentPositionId() );

                        if ( parentPositionStaffRelationVOs != null && parentPositionStaffRelationVOs.size() > 0 )
                        {
                           for ( Object parentObject : parentPositionStaffRelationVOs )
                           {
                              _tempParentPositionOwnerArray.remove( ( ( PositionStaffRelationVO ) parentObject ).getStaffId() );
                           }
                        }
                     }

                     final PositionVO parentPositionVO = this.getPositionVOByPositionId( positionVO.getParentPositionId() );
                     if ( parentPositionVO != null && _tempParentPositionBranchIdArray != null && _tempParentPositionBranchIdArray.size() > 0 )
                     {
                        _tempParentPositionBranchIdArray.remove( parentPositionVO.getBranchId() );
                     }

                     if ( positionVO != null && _tempPositionLocationIdArray != null && _tempPositionLocationIdArray.size() > 0 )
                     {
                        _tempPositionLocationIdArray.remove( positionVO.getLocationId() );
                     }

                     if ( _tempPositionGradeIdArray != null && _tempPositionGradeIdArray.size() > 0 )
                     {
                        _tempPositionGradeIdArray.remove( positionVO.getPositionGradeId() );
                     }

                     employeeVO.set_tempPositionIds( KANUtil.stringListToJasonArray( _tempPositionIdArray, "," ) );
                     employeeVO.set_tempBranchIds( KANUtil.stringListToJasonArray( _tempBranchIdArray, "," ) );
                     employeeVO.set_tempParentBranchIds( KANUtil.stringListToJasonArray( _tempParentBranchIdArray, "," ) );
                     employeeVO.set_tempParentPositionIds( KANUtil.stringListToJasonArray( _tempParentPositionIdArray, "," ) );
                     employeeVO.set_tempParentPositionOwners( KANUtil.stringListToJasonArray( _tempParentPositionOwnerArray, "," ) );
                     employeeVO.set_tempParentPositionBranchIds( KANUtil.stringListToJasonArray( _tempParentPositionBranchIdArray, "," ) );
                     employeeVO.set_tempPositionLocationIds( KANUtil.stringListToJasonArray( _tempPositionLocationIdArray, "," ) );
                     employeeVO.set_tempPositionGradeIds( KANUtil.stringListToJasonArray( _tempPositionGradeIdArray, "," ) );

                     if ( KANUtil.filterEmpty( employeeVO.get_tempPositionIds() ) != null )
                     {
                        employeeVO.set_tempPositionIds( employeeVO.get_tempPositionIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempBranchIds() ) != null )
                     {
                        employeeVO.set_tempBranchIds( employeeVO.get_tempBranchIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentBranchIds() ) != null )
                     {
                        employeeVO.set_tempParentBranchIds( employeeVO.get_tempParentBranchIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionIds() ) != null )
                     {
                        employeeVO.set_tempParentPositionIds( employeeVO.get_tempParentPositionIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionOwners() ) != null )
                     {
                        employeeVO.set_tempParentPositionOwners( employeeVO.get_tempParentPositionOwners().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionBranchIds() ) != null )
                     {
                        employeeVO.set_tempParentPositionBranchIds( employeeVO.get_tempParentPositionBranchIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempPositionLocationIds() ) != null )
                     {
                        employeeVO.set_tempPositionLocationIds( employeeVO.get_tempPositionLocationIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     if ( KANUtil.filterEmpty( employeeVO.get_tempPositionGradeIds() ) != null )
                     {
                        employeeVO.set_tempPositionGradeIds( employeeVO.get_tempPositionGradeIds().replace( "{", "" ).replace( "}", "" ) );
                     }

                     // �־û�EmployeeVO
                     this.getEmployeeDao().updateEmployee( employeeVO );
                  }
               }
            }
         }
      }
   }

   @Override
   // Add by Kevin at 2013-06-14
   public int updatePositionModule( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���½���Position��Module�Ĺ���
         insertPositionModuleRelation( positionVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   /**  
    * UpdatePositionModuleRelationPopup
    *	ģ̬���޸�PositionId��Ӧ����ModuleId��Ȩ�޹���
    *	@param positionVO
    *	@param moduleId
    *	@return
    *	@throws KANException
    */
   @Override
   public int updatePositionModuleRelationPopup( final PositionVO positionVO, final String moduleId ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���½���Position��Module�Ĺ���
         insertPositionModuleRightRelation( positionVO, moduleId );
         insertPositionModuleRuleRelation( positionVO, moduleId );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int insertPosition( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // Insert PositionVO
         ( ( PositionDao ) getDao() ).insertPosition( positionVO );

         // ���½���Position��Group�Ĺ���
         insertPositionGroupRelation( positionVO );

         // ���½���Position��Staff�Ĺ���
         insertPositionStaffRelation( positionVO );

         // ���½���Position��Module�Ĺ���
         insertPositionModuleRelation( positionVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int deletePosition( final PositionVO positionVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // �ӳ�����ȡ��ǰ�ڵ㿪ʼ��ְλ��
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( positionVO.getAccountId() ).getPositionDTOByPositionId( positionVO.getPositionId() );

         // �ݹ���ñ��ɾ������
         deletePosition( positionDTO, positionVO.getModifyBy(), positionVO.getModifyDate() );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   // ���ɾ��Position�ķ������ݹ�
   private void deletePosition( final PositionDTO positionDTO, final String modifyBy, final Date modifyDate ) throws KANException
   {
      if ( positionDTO != null )
      {
         if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
         {
            for ( PositionDTO subPositionDTO : positionDTO.getPositionDTOs() )
            {
               deletePosition( subPositionDTO, modifyBy, modifyDate );
            }
         }

         // ���ɾ��Position��Group�Ĺ�ϵ
         if ( positionDTO.getPositionGroupRelationVOs() != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
         {
            for ( PositionGroupRelationVO positionGroupRelationVO : positionDTO.getPositionGroupRelationVOs() )
            {
               positionGroupRelationVO.setDeleted( PositionGroupRelationVO.FALSE );
               positionGroupRelationVO.setModifyBy( modifyBy );
               positionGroupRelationVO.setModifyDate( modifyDate );
               this.positionGroupRelationDao.updatePositionGroupRelation( positionGroupRelationVO );
            }
         }

         // ���ɾ��Position��Staff�Ĺ�ϵ
         if ( positionDTO.getPositionStaffRelationVOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
         {
            for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
            {
               positionStaffRelationVO.setDeleted( PositionStaffRelationVO.FALSE );
               positionStaffRelationVO.setModifyBy( modifyBy );
               positionStaffRelationVO.setModifyDate( modifyDate );
               this.positionStaffRelationDao.updatePositionStaffRelation( positionStaffRelationVO );
            }
         }

         // ���ɾ��Position��Module�Ĺ�ϵ
         if ( positionDTO.getPositionModuleDTOs() != null && positionDTO.getPositionModuleDTOs().size() > 0 )
         {
            for ( PositionModuleDTO positionModuleDTO : positionDTO.getPositionModuleDTOs() )
            {
               // ���ɾ��Module��Right�Ĺ�ϵ
               if ( positionModuleDTO.getPositionModuleRightRelationVOs() != null && positionModuleDTO.getPositionModuleRightRelationVOs().size() > 0 )
               {
                  for ( PositionModuleRightRelationVO positionModuleRightRelationVO : positionModuleDTO.getPositionModuleRightRelationVOs() )
                  {
                     positionModuleRightRelationVO.setDeleted( PositionModuleRightRelationVO.FALSE );
                     positionModuleRightRelationVO.setModifyBy( modifyBy );
                     positionModuleRightRelationVO.setModifyDate( modifyDate );
                     this.positionModuleRightRelationDao.updatePositionModuleRightRelation( positionModuleRightRelationVO );
                  }
               }

               // ���ɾ��Module��Rule�Ĺ�ϵ
               if ( positionModuleDTO.getPositionModuleRuleRelationVOs() != null && positionModuleDTO.getPositionModuleRuleRelationVOs().size() > 0 )
               {
                  for ( PositionModuleRuleRelationVO positionModuleRuleRelationVO : positionModuleDTO.getPositionModuleRuleRelationVOs() )
                  {
                     positionModuleRuleRelationVO.setDeleted( PositionModuleRuleRelationVO.FALSE );
                     positionModuleRuleRelationVO.setModifyBy( modifyBy );
                     positionModuleRuleRelationVO.setModifyDate( modifyDate );
                     this.positionModuleRuleRelationDao.updatePositionModuleRuleRelation( positionModuleRuleRelationVO );
                  }
               }
            }
         }

         // �����ɾ��Position
         if ( positionDTO.getPositionVO() != null )
         {
            final PositionVO positionVO = positionDTO.getPositionVO();
            positionVO.setDeleted( PositionVO.FALSE );
            positionVO.setModifyBy( modifyBy );
            positionVO.setModifyDate( modifyDate );
            ( ( PositionDao ) getDao() ).updatePosition( positionVO );
         }
      }
   }

   // ����Position��Group֮��Ĺ���
   private int insertPositionGroupRelation( final PositionVO positionVO ) throws KANException
   {
    
         // ��ɾ��Position��Ӧ��ϵ��
         this.positionGroupRelationDao.deletePositionGroupRelationByPositionId( positionVO.getPositionId() );
         // Position ��Ҫ���ְλ��
         if ( positionVO.getGroupIdArray() != null && positionVO.getGroupIdArray().length > 0 )
         {

            // ѭ�����Position��Group�Ĺ�ϵ
            for ( String groupId : positionVO.getGroupIdArray() )
            {
               final PositionGroupRelationVO positionGroupRelationVO = new PositionGroupRelationVO();
               positionGroupRelationVO.setPositionId( positionVO.getPositionId() );
               positionGroupRelationVO.setGroupId( groupId );
               positionGroupRelationVO.setCreateBy( positionVO.getModifyBy() );
               positionGroupRelationVO.setModifyBy( positionVO.getModifyBy() );
               this.positionGroupRelationDao.insertPositionGroupRelation( positionGroupRelationVO );
            }
         }
      
     
      return 0;
   }

   // ����Position��Staff֮��Ĺ��� & ������StaffId��Ӧ��EmployeeVO�е�4����_temp���ֶ�
   // Modify by Siuvan Xia at 2014-8-29
   private int insertPositionStaffRelation( final PositionVO positionVO ) throws KANException
   {
     
         // ��ɾ��Position��Ӧ��ϵ��
         this.positionStaffRelationDao.deletePositionStaffRelationByPositionId( positionVO.getPositionId() );

         // Position ��Ҫ���Ա��
         if ( positionVO.getStaffIdArray() != null && positionVO.getStaffIdArray().length > 0 )
         {
            // ѭ�����Position��Staff�Ĺ�ϵ
            for ( String staffInfos : positionVO.getStaffIdArray() )
            {
               // StaffInfos ������StaffId��StaffType��AgentStart��AgentEnd
               final String[] staffInfoArray = staffInfos.split( "_" );
               String staffId = "";
               if ( staffInfoArray != null && staffInfoArray.length > 1 )
               {
                  staffId = staffInfoArray[ 0 ];
                  final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
                  positionStaffRelationVO.setPositionId( positionVO.getPositionId() );
                  positionStaffRelationVO.setStaffId( staffInfoArray[ 0 ] );
                  positionStaffRelationVO.setStaffType( staffInfoArray[ 1 ] );
                  if ( staffInfoArray[ 1 ] != null && staffInfoArray[ 1 ].trim().equals( "2" ) && staffInfoArray.length > 3 )
                  {

                     if ( KANUtil.filterEmpty( staffInfoArray[ 2 ] ) == null )
                     {
                        staffInfoArray[ 2 ] = null;
                     }

                     if ( KANUtil.filterEmpty( staffInfoArray[ 3 ] ) == null )
                     {
                        staffInfoArray[ 3 ] = null;
                     }

                     positionStaffRelationVO.setAgentStart( staffInfoArray[ 2 ] );
                     positionStaffRelationVO.setAgentEnd( staffInfoArray[ 3 ] );
                  }
                  positionStaffRelationVO.setCreateBy( positionVO.getModifyBy() );
                  positionStaffRelationVO.setModifyBy( positionVO.getModifyBy() );
                  this.positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
               }

               if ( KANUtil.filterEmpty( staffId ) != null )
               {
                  // ��ȡStaffVO
                  final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( staffId );

                  if ( staffVO != null )
                  {
                     // ��ȡEmployeeVO
                     final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );

                     // ��ȡ��ǰstaffId��Ӧ��PositionStaffRelationVO�б�
                     final List< Object > positionStaffRelationVOs = this.positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffId );

                     // ����PositionStaffRelationVO�б�
                     if ( employeeVO != null && positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                     {
                        // ��ʼ��ְλ�ַ���
                        final StringBuffer _tempPositionIds = new StringBuffer();

                        // ��ʼ�������ַ���
                        final StringBuffer _tempBranchIds = new StringBuffer();

                        // ��ʼ���ϼ������ַ���
                        final StringBuffer _tempParentBranchIds = new StringBuffer();

                        // ��ʼ���ϼ�ְλ�ַ���
                        final StringBuffer _tempParentPositionIds = new StringBuffer();

                        // ��ʼ���ϼ�ְλ�������ַ���
                        final StringBuffer _tempParentPositionOwners = new StringBuffer();

                        // ��ʼ���ϼ�ְλ�����ַ���
                        final StringBuffer _tempParentPositionBranchIds = new StringBuffer();

                        // ��ʼ��������ַ�ַ���
                        final StringBuffer _tempPositionLocationIds = new StringBuffer();

                        // ��ʼ��ְ���ַ���
                        final StringBuffer _tempPositionGradeIds = new StringBuffer();

                        for ( Object o : positionStaffRelationVOs )
                        {
                           final PositionVO staffPositionVO = ( ( PositionDao ) getDao() ).getPositionVOByPositionId( ( ( PositionStaffRelationVO ) o ).getPositionId() );
                           if ( staffPositionVO != null )
                           {
                              // ְλ
                              if ( KANUtil.filterEmpty( _tempPositionIds.toString() ) == null )
                              {
                                 _tempPositionIds.append( staffPositionVO.getPositionId() );
                              }
                              else
                              {
                                 if ( !ArrayUtils.contains( _tempPositionIds.toString().split( "," ), staffPositionVO.getPositionId() ) )
                                    _tempPositionIds.append( "," + staffPositionVO.getPositionId() );
                              }

                              // ��������
                              if ( KANUtil.filterEmpty( _tempBranchIds.toString() ) == null )
                              {
                                 _tempBranchIds.append( staffPositionVO.getBranchId() );
                              }
                              else
                              {
                                 if ( !ArrayUtils.contains( _tempBranchIds.toString().split( "," ), staffPositionVO.getBranchId() ) )
                                    _tempBranchIds.append( "," + staffPositionVO.getBranchId() );
                              }

                              // �ϼ�����
                              if ( KANUtil.filterEmpty( staffPositionVO.getBranchId(), "0" ) != null )
                              {
                                 final BranchVO branchVO = this.getBranchDao().getBranchVOByBranchId( staffPositionVO.getBranchId() );
                                 if ( branchVO != null && KANUtil.filterEmpty( _tempParentBranchIds.toString() ) == null )
                                 {
                                    _tempParentBranchIds.append( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId()
                                          : branchVO.getParentBranchId() );
                                 }
                                 else if ( branchVO != null )
                                 {
                                    if ( !ArrayUtils.contains( _tempParentBranchIds.toString().split( "," ), ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId()
                                          : branchVO.getParentBranchId() ) ) )
                                       _tempParentBranchIds.append( ","
                                             + ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId() : branchVO.getParentBranchId() ) );
                                 }
                              }

                              // �ϼ�ְλ
                              if ( KANUtil.filterEmpty( staffPositionVO.getParentPositionId() ) != null )
                              {
                                 if ( KANUtil.filterEmpty( _tempParentPositionIds.toString() ) == null )
                                 {
                                    _tempParentPositionIds.append( staffPositionVO.getParentPositionId() );
                                 }
                                 else
                                 {
                                    if ( !ArrayUtils.contains( _tempParentPositionIds.toString().split( "," ), staffPositionVO.getParentPositionId() ) )
                                       _tempParentPositionIds.append( "," + staffPositionVO.getParentPositionId() );
                                 }
                              }

                              // �ϼ�ְλ������
                              if ( KANUtil.filterEmpty( staffPositionVO.getParentPositionId() ) != null )
                              {
                                 final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( staffPositionVO.getParentPositionId() );
                                 if ( parentPositionStaffRelationVOs != null && parentPositionStaffRelationVOs.size() > 0 )
                                 {
                                    for ( Object parentObject : parentPositionStaffRelationVOs )
                                    {
                                       final PositionStaffRelationVO parentPositionStaffRelationVO = ( PositionStaffRelationVO ) parentObject;
                                       if ( parentPositionStaffRelationVO != null )
                                       {
                                          if ( KANUtil.filterEmpty( _tempParentPositionOwners.toString() ) == null )
                                          {
                                             _tempParentPositionOwners.append( parentPositionStaffRelationVO.getStaffId() );
                                          }
                                          else
                                          {
                                             if ( !ArrayUtils.contains( _tempParentPositionOwners.toString().split( "," ), parentPositionStaffRelationVO.getStaffId() ) )
                                                _tempParentPositionOwners.append( "," + parentPositionStaffRelationVO.getStaffId() );
                                          }
                                       }
                                    }
                                 }
                              }

                              // �ϼ�ְλ����
                              if ( KANUtil.filterEmpty( staffPositionVO.getParentPositionId() ) != null )
                              {
                                 final PositionVO parentPositionVO = this.getPositionVOByPositionId( staffPositionVO.getParentPositionId() );
                                 if ( parentPositionVO != null )
                                 {
                                    if ( KANUtil.filterEmpty( _tempParentPositionBranchIds.toString() ) == null )
                                    {
                                       _tempParentPositionBranchIds.append( parentPositionVO.getBranchId() );
                                    }
                                    else
                                    {
                                       if ( !ArrayUtils.contains( _tempParentPositionBranchIds.toString().split( "," ), parentPositionVO.getBranchId() ) )
                                          _tempParentPositionBranchIds.append( "," + parentPositionVO.getBranchId() );
                                    }
                                 }
                              }

                              // ������ַ
                              if ( KANUtil.filterEmpty( staffPositionVO.getLocationId() ) != null )
                              {
                                 if ( KANUtil.filterEmpty( _tempPositionLocationIds.toString() ) == null )
                                 {
                                    _tempPositionLocationIds.append( staffPositionVO.getLocationId() );
                                 }
                                 else
                                 {
                                    if ( !ArrayUtils.contains( _tempPositionLocationIds.toString().split( "," ), staffPositionVO.getLocationId() ) )
                                       _tempPositionLocationIds.append( "," + staffPositionVO.getLocationId() );
                                 }
                              }

                              // ְ��
                              if ( KANUtil.filterEmpty( staffPositionVO.getPositionGradeId(), "0" ) != null )
                              {
                                 if ( KANUtil.filterEmpty( _tempPositionGradeIds.toString() ) == null )
                                 {
                                    _tempPositionGradeIds.append( staffPositionVO.getPositionGradeId() );
                                 }
                                 else
                                 {
                                    if ( !ArrayUtils.contains( _tempPositionGradeIds.toString().split( "," ), staffPositionVO.getPositionGradeId() ) )
                                       _tempPositionGradeIds.append( "," + staffPositionVO.getPositionGradeId() );
                                 }
                              }
                           }
                        }

                        employeeVO.set_tempPositionIds( _tempPositionIds.toString() );
                        employeeVO.set_tempBranchIds( _tempBranchIds.toString() );
                        employeeVO.set_tempParentBranchIds( _tempParentBranchIds.toString() );
                        employeeVO.set_tempParentPositionIds( _tempParentPositionIds.toString() );
                        employeeVO.set_tempParentPositionOwners( _tempParentPositionOwners.toString() );
                        employeeVO.set_tempParentPositionBranchIds( _tempParentPositionBranchIds.toString() );
                        employeeVO.set_tempPositionLocationIds( _tempPositionLocationIds.toString() );
                        employeeVO.set_tempPositionGradeIds( _tempPositionGradeIds.toString() );

                        // �־û�EmployeeVO
                        this.getEmployeeDao().updateEmployee( employeeVO );
                     }
                  }
               }
            }
         }
     

      return 0;
   }

   // ����Position��Module֮��Ĺ���
   private int insertPositionModuleRelation( final PositionVO positionVO ) throws KANException
   {
      try
      {
         if ( positionVO.getModuleIdArray() != null && positionVO.getModuleIdArray().length > 0 )
         {
            for ( String moduleId : positionVO.getModuleIdArray() )
            {
               // ���PositionId ��Ӧ ModuleId��Ȩ�ޡ�����
               insertPositionModuleRightRelation( positionVO, moduleId );
               insertPositionModuleRuleRelation( positionVO, moduleId );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   /**  
    * InsertPositionModuleRightRelation
    *	���PositionId ��Ӧ ModuleId��Ȩ��
    *	@param positionVO
    *	@param moduleId
    *	@param moduleId
    *	@throws KANException
    */
   private void insertPositionModuleRightRelation( final PositionVO positionVO, final String moduleId ) throws KANException
   {
      // ��ɾ��PositionId��Ӧ ModuleId����Ȩ��
      this.positionModuleRightRelationDao.deletePositionModuleRightRelationByCondition( new PositionModuleRightRelationVO( positionVO.getPositionId(), moduleId, null ) );

      // Position ���Ȩ��
      if ( positionVO.getRightIdArray() != null && positionVO.getRightIdArray().length > 0 )
      {
         // ѭ�����Position��Module��Right�Ĺ�ϵ
         for ( String rightId : positionVO.getRightIdArray() )
         {
            final PositionModuleRightRelationVO positionModuleRightRelationVO = new PositionModuleRightRelationVO();
            positionModuleRightRelationVO.setPositionId( positionVO.getPositionId() );
            positionModuleRightRelationVO.setModuleId( moduleId );
            positionModuleRightRelationVO.setRightId( rightId );
            positionModuleRightRelationVO.setCreateBy( positionVO.getModifyBy() );
            positionModuleRightRelationVO.setModifyBy( positionVO.getModifyBy() );
            this.positionModuleRightRelationDao.insertPositionModuleRightRelation( positionModuleRightRelationVO );
         }
      }

   }

   /**  
    * InsertPositionModuleRuleRelation
    *	���PositionId ��Ӧ ModuleId�Ĺ���
    *	@param positionVO
    *	@param moduleId
    *	@param moduleId
    *	@throws KANException
    */
   private void insertPositionModuleRuleRelation( final PositionVO positionVO, final String moduleId ) throws KANException
   {
      // ��ɾ��Position��Module��Ӧ��ϵ��
      this.positionModuleRuleRelationDao.deletePositionModuleRuleRelationByCondition( new PositionModuleRuleRelationVO( positionVO.getPositionId(), moduleId, null ) );

      // Position ��ӹ���
      if ( positionVO.getRuleIdArray() != null && positionVO.getRuleIdArray().length > 0 )
      {
         // ѭ�����Position��Module��Rule�Ĺ�ϵ
         for ( String ruleId : positionVO.getRuleIdArray() )
         {
            if ( ruleId != null && ruleId.split( "_" ).length == 2 )
            {
               final PositionModuleRuleRelationVO positionModuleRuleRelationVO = new PositionModuleRuleRelationVO();
               positionModuleRuleRelationVO.setPositionId( positionVO.getPositionId() );
               positionModuleRuleRelationVO.setModuleId( moduleId );
               positionModuleRuleRelationVO.setRuleId( ruleId.split( "_" )[ 1 ] );
               positionModuleRuleRelationVO.setCreateBy( positionVO.getModifyBy() );
               positionModuleRuleRelationVO.setModifyBy( positionVO.getModifyBy() );
               this.positionModuleRuleRelationDao.insertPositionModuleRuleRelation( positionModuleRuleRelationVO );
            }
         }
      }

   }

   @Override
   public List< Object > getPositionVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return ( ( PositionGroupRelationDao ) getPositionGroupRelationDao() ).getPositionGroupRelationVOsByGroupId( groupId );
   }

   @Override
   /* Add by Kevin at 2013-06-08 */
   public List< Object > getRelationVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionStaffRelationDao ) getPositionStaffRelationDao() ).getPositionStaffRelationVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getRelationVOsByStaffId( final String staffId ) throws KANException
   {
      return ( ( PositionStaffRelationDao ) getPositionStaffRelationDao() ).getPositionStaffRelationVOsByStaffId( staffId );
   }

   @Override
   public List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionBaseViewsByAccountId( accountId );
   }

   @Override
   /* Add by Kevin at 2013-06-06 */
   public List< PositionDTO > getPositionDTOsByAccountId( final String accountId ) throws KANException
   {
      // ����PositionDTO List�����ڷ�������
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

      // �½�PositionVO���ڴ���
      final PositionVO positionVO = new PositionVO();
      // Ĭ�ϸ��ڵ�ĸ��ڵ�ֵΪ��0��
      positionVO.setParentPositionId( "0" );
      positionVO.setAccountId( accountId );

      // ��ø��ڵ�
      final List< Object > rootPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( positionVO );

      for ( Object rootPositionObject : rootPositionVOs )
      {
         // �ݹ����
         positionDTOs.add( fetchPositionDTO( ( PositionVO ) rootPositionObject ) );
      }

      return positionDTOs;
   }

   // �ݹ鷽��
   /* Add by Kevin at 2013-06-06 */
   private PositionDTO fetchPositionDTO( final PositionVO positionVO ) throws KANException
   {
      final PositionDTO positionDTO = new PositionDTO();
      // ����PositionVO����
      positionDTO.setPositionVO( positionVO );

      // ����PositionVO��Ӧ��Group
      final List< Object > positionGroupRelationVOs = positionGroupRelationDao.getPositionGroupRelationVOsByPositionId( positionVO.getPositionId() );
      if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
      {
         for ( Object positionGroupRelationVOObject : positionGroupRelationVOs )
         {
            positionDTO.getPositionGroupRelationVOs().add( ( PositionGroupRelationVO ) positionGroupRelationVOObject );
         }
      }

      // ����PositionVO�󶨵�Ա��
      final List< Object > positionStaffRelationVOs = positionStaffRelationDao.getPositionStaffRelationVOsByPositionId( positionVO.getPositionId() );
      if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
      {
         for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
         {
            positionDTO.getPositionStaffRelationVOs().add( ( PositionStaffRelationVO ) positionStaffRelationVOObject );
         }
      }

      // ����PositionVO��Module����Ȩ��
      positionDTO.setPositionModuleDTOs( getPositionModuleDTOsByPositionId( positionVO.getPositionId() ) );

      // ����������һ��Position
      final PositionVO subPositionVO = new PositionVO();
      subPositionVO.setAccountId( positionVO.getAccountId() );
      subPositionVO.setParentPositionId( positionVO.getPositionId() );
      final List< Object > subPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( subPositionVO );

      for ( Object subPositionObject : subPositionVOs )
      {
         // �ݹ����
         positionDTO.getPositionDTOs().add( fetchPositionDTO( ( PositionVO ) subPositionObject ) );
      }

      return positionDTO;
   }

   /* Add by Kevin at 2013-06-17, Need to merge the account public right & rule */
   private List< PositionModuleDTO > getPositionModuleDTOsByPositionId( final String positionId ) throws KANException
   {
      // ����ModuleDTO List�����ڷ�������
      final List< PositionModuleDTO > positionModuleDTOs = new ArrayList< PositionModuleDTO >();

      // ��õ�ǰְλ��������Module��Right
      final PositionModuleRightRelationVO positionModuleRightRelationVO = new PositionModuleRightRelationVO( positionId, null, null );
      positionModuleRightRelationVO.setStatus( "1" );
      final List< Object > positionModuleRightRelationVOs = this.getPositionModuleRightRelationVOsByCondition( positionModuleRightRelationVO );

      if ( positionModuleRightRelationVOs != null && positionModuleRightRelationVOs.size() > 0 )
      {
         for ( Object positionModuleRightRelationVOObject : positionModuleRightRelationVOs )
         {
            // ��ʼ��ModuleDTO����
            final String moduleId = ( ( PositionModuleRightRelationVO ) positionModuleRightRelationVOObject ).getModuleId();
            // ���б��л��PositionModuleDTOʵ��
            final PositionModuleDTO positionModuleDTO = getPositionModuleDTOFormList( positionModuleDTOs, moduleId );

            // ��ʼ��PositionModuleDTO�е�ModuleVO����
            if ( positionModuleDTO != null )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() == null )
               {
                  positionModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // ��ʼ��Module��Ӧ��Ȩ��
               positionModuleDTO.getPositionModuleRightRelationVOs().add( ( PositionModuleRightRelationVO ) positionModuleRightRelationVOObject );
            }
         }
      }

      // ��õ�ǰְλ��������Module��Rule
      final PositionModuleRuleRelationVO positionModuleRuleRelationVO = new PositionModuleRuleRelationVO( positionId, null, null );
      positionModuleRuleRelationVO.setStatus( "1" );
      final List< Object > positionModuleRuleRelationVOs = this.getPositionModuleRuleRelationVOsByCondition( positionModuleRuleRelationVO );

      if ( positionModuleRuleRelationVOs != null && positionModuleRuleRelationVOs.size() > 0 )
      {
         for ( Object positionModuleRuleRelationVOObject : positionModuleRuleRelationVOs )
         {
            // ��ʼ��PositionModuleDTO����
            final String moduleId = ( ( PositionModuleRuleRelationVO ) positionModuleRuleRelationVOObject ).getModuleId();
            // ���б��л��PositionModuleDTOʵ��
            final PositionModuleDTO positionModuleDTO = getPositionModuleDTOFormList( positionModuleDTOs, moduleId );

            // ��ʼ��PositionModuleDTO�е�ModuleVO����
            if ( positionModuleDTO != null )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId() == null )
               {
                  positionModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // ��ʼ��Module��Ӧ��Ȩ��
               positionModuleDTO.getPositionModuleRuleRelationVOs().add( ( PositionModuleRuleRelationVO ) positionModuleRuleRelationVOObject );
            }
         }
      }

      return positionModuleDTOs;
   }

   // ���б��л��PositionModuleDTO
   private PositionModuleDTO getPositionModuleDTOFormList( final List< PositionModuleDTO > positionModuleDTOs, final String moduleId )
   {
      // �����ǰ��Ҫ���ҵ�DTO�б�Ϊ�գ�����ModuleIdҲ��Ϊ��
      if ( positionModuleDTOs != null && moduleId != null )
      {
         // DTO�б��к��еĶ���������0
         if ( positionModuleDTOs.size() > 0 )
         {
            // ����DTO�б��ҵ�ƥ��Ĳ�����
            for ( PositionModuleDTO positionModuleDTO : positionModuleDTOs )
            {
               if ( positionModuleDTO.getModuleVO() != null && positionModuleDTO.getModuleVO().getModuleId().equals( moduleId ) )
               {
                  return positionModuleDTO;
               }
            }
         }

         // ����Ҳ���Ŀ������򴴽�ʵ��
         final PositionModuleDTO positionModuleDTO = new PositionModuleDTO();
         positionModuleDTOs.add( positionModuleDTO );
         return positionModuleDTO;
      }

      return null;
   }

   @Override
   public List< Object > getPositionModuleRightRelationVOsByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return ( ( PositionModuleRightRelationDao ) getPositionModuleRightRelationDao() ).getPositionModuleRightRelationVOsByCondition( positionModuleRightRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRuleRelationVOsByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return ( ( PositionModuleRuleRelationDao ) getPositionModuleRuleRelationDao() ).getPositionModuleRuleRelationVOsByCondition( positionModuleRuleRelationVO );
   }

   @Override
   public List< PositionVO > getPositionVOsByPositionVO( PositionVO positionVO ) throws KANException
   {
      // ��ʼ��������PositionVOs
      List< PositionVO > positionVOs = new ArrayList< PositionVO >();
      // �����ݿ��л�ȡδת����PositionVO����PositionVOs
      List< Object > objectPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOByPositionVO( positionVO );
      // ����objectPositionVOs�����positionVOs��
      for ( Object objectPositionVO : objectPositionVOs )
      {
         PositionVO positionVOTemp = ( PositionVO ) objectPositionVO;
         positionVOs.add( positionVOTemp );
      }
      return positionVOs;
   }

   @Override
   public List< Object > getPositionVOsByEmployeeId( StaffVO staffVO ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionVOsByEmployeeId( staffVO );
   }

   @Override
   public int deletePositionStaffRelationByPositionId( String positionId ) throws KANException
   {
      return positionStaffRelationDao.deletePositionStaffRelationByPositionId( positionId );
   }

   @Override
   public int insertPositionStaffRelation( PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByStaffId( final String staffId ) throws KANException
   {
      return positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffId );
   }

}
