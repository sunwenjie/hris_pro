package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.GroupDao;
import com.kan.base.dao.inf.security.GroupModuleRightRelationDao;
import com.kan.base.dao.inf.security.GroupModuleRuleRelationDao;
import com.kan.base.dao.inf.security.PositionGroupRelationDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleDTO;
import com.kan.base.domain.security.GroupModuleRightRelationVO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.GroupService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class GroupServiceImpl extends ContextService implements GroupService
{

   private PositionGroupRelationDao positionGroupRelationDao;

   public PositionGroupRelationDao getPositionGroupRelationDao()
   {
      return positionGroupRelationDao;
   }

   public void setPositionGroupRelationDao( PositionGroupRelationDao positionGroupRelationDao )
   {
      this.positionGroupRelationDao = positionGroupRelationDao;
   }

   // ע��GroupModuleRightRelationDao
   private GroupModuleRightRelationDao groupModuleRightRelationDao;

   public GroupModuleRightRelationDao getGroupModuleRightRelationDao()
   {
      return groupModuleRightRelationDao;
   }

   public void setGroupModuleRightRelationDao( GroupModuleRightRelationDao groupModuleRightRelationDao )
   {
      this.groupModuleRightRelationDao = groupModuleRightRelationDao;
   }

   // ע��GroupModuleRuleRelationDao
   private GroupModuleRuleRelationDao groupModuleRuleRelationDao;

   public GroupModuleRuleRelationDao getGroupModuleRuleRelationDao()
   {
      return groupModuleRuleRelationDao;
   }

   public void setGroupModuleRuleRelationDao( GroupModuleRuleRelationDao groupModuleRuleRelationDao )
   {
      this.groupModuleRuleRelationDao = groupModuleRuleRelationDao;
   }

   // ע��ModuleDao
   private ModuleDao moduleDao;

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   @Override
   public PagedListHolder getGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final GroupDao groupDao = ( GroupDao ) getDao();
      pagedListHolder.setHolderSize( groupDao.countGroupVOsByCondition( ( GroupVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( groupDao.getGroupVOsByCondition( ( GroupVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( groupDao.getGroupVOsByCondition( ( GroupVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public GroupVO getGroupVOByGroupId( final String groupId ) throws KANException
   {
      return ( ( GroupDao ) getDao() ).getGroupVOByGroupId( groupId );
   }

   @Override
   public int updateGroup( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();
         if ( "1".equals( groupVO.getDataRole() ) || "2".equals( groupVO.getDataRole() ) )
         {
            groupVO.setHrFunction( "1" );
         }
         else
         {
            groupVO.setHrFunction( "2" );
         }
         // Updateְλ�����
         ( ( GroupDao ) getDao() ).updateGroup( groupVO );

         // ������ǰGroup��Position�Ĺ�ϵ
         insertGroupPositionRelation( groupVO );

         // ������ǰGroup��Module�Ĺ�ϵ��ģ̬�����޸ģ������ٴ��޸ģ�
         insertGroupModuleRuleRelation( groupVO );

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
   // Add by Kevin at 2013-06-14
   public int updateGroupModule( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���½���Position��Module�Ĺ���
         insertGroupModuleRelation( groupVO );

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
   public int insertGroup( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         if ( "1".equals( groupVO.getDataRole() ) || "2".equals( groupVO.getDataRole() ) )
         {
            groupVO.setHrFunction( "1" );
         }
         else
         {
            groupVO.setHrFunction( "2" );
         }
         
         ( ( GroupDao ) getDao() ).insertGroup( groupVO );

         // ������ǰGroup��Position�Ĺ�ϵ
         insertGroupPositionRelation( groupVO );

         // ������ǰGroup��Module�Ĺ�ϵ
         insertGroupModuleRelation( groupVO );

         insertGroupModuleRuleRelation( groupVO );

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

   // ������ǰGroup��Position�Ĺ�ϵ
   private int insertGroupPositionRelation( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // ɾ��Group��Position�Ķ�Ӧ��ϵ
         this.positionGroupRelationDao.deletePositionGroupRelationByGroupId( groupVO.getGroupId() );

         if ( groupVO.getPositionIdArray() != null && groupVO.getPositionIdArray().length > 0 )
         {
            for ( String positionId : groupVO.getPositionIdArray() )
            {
               final PositionGroupRelationVO positionGroupRelationVO = new PositionGroupRelationVO();
               positionGroupRelationVO.setGroupId( groupVO.getGroupId() );
               positionGroupRelationVO.setPositionId( positionId );
               positionGroupRelationVO.setCreateBy( groupVO.getModifyBy() );
               positionGroupRelationVO.setModifyBy( groupVO.getModifyBy() );
               this.positionGroupRelationDao.insertPositionGroupRelation( positionGroupRelationVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   // ������ǰGroup��Module�Ķ�Ӧ��ϵ
   private int insertGroupModuleRelation( final GroupVO groupVO ) throws KANException
   {
      try
      {

         // ����Group��Module��Relation
         if ( groupVO.getModuleIdArray() != null && groupVO.getModuleIdArray().length > 0 )
         {
            for ( String moduleId : groupVO.getModuleIdArray() )
            {
               insertGroupModuleRightRelation( groupVO, moduleId );
               //               insertGroupModuleRuleRelation( groupVO, moduleId );
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
    * UpdateGroupModuleRelationPopup
    * ģ̬���޸�groupId��Ӧ����ModuleId��Ȩ�޹���
    * @param positionVO
    * @param moduleId
    * @return
    * @throws KANException
    */
   @Override
   public int updateGroupModuleRelationPopup( final GroupVO groupVO, final String moduleId ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���½���Group��Module�Ĺ���
         insertGroupModuleRightRelation( groupVO, moduleId );
         //         insertGroupModuleRuleRelation( groupVO, moduleId );

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
    * InsertGroupModuleRightRelation
    * ���GroupId ��Ӧ ModuleId��Ȩ��
    * @param groupVO
    * @param moduleId
    * @throws KANException
    */
   private void insertGroupModuleRightRelation( final GroupVO groupVO, final String moduleId ) throws KANException
   {
      // ��ɾ��GroupId��Ӧ ModuleId����Ȩ��
      this.groupModuleRightRelationDao.deleteGroupModuleRightRelationByCondition( new GroupModuleRightRelationVO( groupVO.getGroupId(), moduleId, null ) );

      // Group ���Ȩ��
      if ( groupVO.getRightIdArray() != null && groupVO.getRightIdArray().length > 0 )
      {
         // ѭ�����Group��Module��Right�Ĺ�ϵ
         for ( String rightId : groupVO.getRightIdArray() )
         {
            final GroupModuleRightRelationVO groupModuleRightRelationVO = new GroupModuleRightRelationVO();
            groupModuleRightRelationVO.setGroupId( groupVO.getGroupId() );
            groupModuleRightRelationVO.setModuleId( moduleId );
            groupModuleRightRelationVO.setRightId( rightId );
            groupModuleRightRelationVO.setCreateBy( groupVO.getModifyBy() );
            groupModuleRightRelationVO.setModifyBy( groupVO.getModifyBy() );
            this.groupModuleRightRelationDao.insertGroupModuleRightRelation( groupModuleRightRelationVO );
         }
      }
   }

   //   /**  
   //    * InsertGroupModuleRuleRelation
   //    * ���GroupId ��Ӧ ModuleId�Ĺ���
   //    * @param groupVO
   //    * @param moduleId
   //    * @throws KANException
   //    */
   //   private void insertGroupModuleRuleRelation( final GroupVO groupVO, final String moduleId ) throws KANException
   //   {
   //      // ��ɾ��Group��Module��Ӧ��ϵ��
   //      this.groupModuleRuleRelationDao.deleteGroupModuleRuleRelationByCondition( new GroupModuleRuleRelationVO( groupVO.getGroupId(), moduleId, null ) );
   //
   //      // Group ��ӹ���
   //      if ( groupVO.getRuleIdArray() != null && groupVO.getRuleIdArray().length > 0 )
   //      {
   //         // ѭ�����Group��Module��Rule�Ĺ�ϵ
   //         for ( String ruleId : groupVO.getRuleIdArray() )
   //         {
   //            if ( ruleId != null && ruleId.split( "_" ).length == 2 )
   //            {
   //               final GroupModuleRuleRelationVO groupModuleRuleRelationVO = new GroupModuleRuleRelationVO();
   //               groupModuleRuleRelationVO.setGroupId( groupVO.getGroupId() );
   //               groupModuleRuleRelationVO.setModuleId( moduleId );
   //               groupModuleRuleRelationVO.setRuleId( ruleId.split( "_" )[ 1 ] );
   //               groupModuleRuleRelationVO.setCreateBy( groupVO.getModifyBy() );
   //               groupModuleRuleRelationVO.setModifyBy( groupVO.getModifyBy() );
   //               this.groupModuleRuleRelationDao.insertGroupModuleRuleRelation( groupModuleRuleRelationVO );
   //            }
   //         }
   //      }
   //
   //   }

   private void insertGroupModuleRuleRelation( final GroupVO groupVO ) throws KANException
   {
      GroupModuleRuleRelationVO groupModuleRuleRelationVO = new GroupModuleRuleRelationVO();
      groupModuleRuleRelationVO.setGroupId( groupVO.getGroupId() );
      this.groupModuleRuleRelationDao.deleteGroupModuleRuleRelationByGroupId( groupModuleRuleRelationVO );
      if ( !"0".equals( groupVO.getDataRole() ) )
      {
         for ( String ruleId : groupVO.getRuleIds() )
         {
            groupModuleRuleRelationVO = new GroupModuleRuleRelationVO();
            groupModuleRuleRelationVO.setRuleId( ruleId );
            groupModuleRuleRelationVO.setGroupId( groupVO.getGroupId() );
            groupModuleRuleRelationVO.setCreateBy( groupVO.getModifyBy() );
            groupModuleRuleRelationVO.setModifyBy( groupVO.getModifyBy() );

            //����
            if ( ruleId.equals( "3" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getBranchIds() ) );
            }
            //ְλ
            if ( ruleId.equals( "4" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getPositionIds() ) );
            }
            //ְ��
            if ( ruleId.equals( "5" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getPositionGradeIds() ) );
            }
            //��Ŀ
            if ( ruleId.equals( "6" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getBusinessTypeIds() ) );
            }
            //��������
            if ( ruleId.equals( "7" ) )
            {
               groupModuleRuleRelationVO.setRemark1( KANUtil.toJasonArray( groupVO.getEntityIds() ) );
            }
            this.groupModuleRuleRelationDao.insertGroupModuleRuleRelation( groupModuleRuleRelationVO );
         }
      }
   }

   @Override
   public int deleteGroup( final GroupVO groupVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // �ӳ�����ȡ��ǰ�ڵ㿪ʼ��ְλ��DTO
         final GroupDTO groupDTO = KANConstants.getKANAccountConstants( groupVO.getAccountId() ).getGroupDTOByGroupId( groupVO.getGroupId() );
         if ( groupDTO != null )
         {
            // ���ɾ��Group��Position�Ĺ�ϵ
            if ( groupDTO.getPositionGroupRelationVOs() != null && groupDTO.getPositionGroupRelationVOs().size() > 0 )
            {
               for ( PositionGroupRelationVO positionGroupRelationVO : groupDTO.getPositionGroupRelationVOs() )
               {
                  positionGroupRelationVO.setDeleted( PositionGroupRelationVO.FALSE );
                  positionGroupRelationVO.setModifyBy( groupVO.getModifyBy() );
                  positionGroupRelationVO.setModifyDate( groupVO.getModifyDate() );
                  this.positionGroupRelationDao.updatePositionGroupRelation( positionGroupRelationVO );
               }
            }

            // ���ɾ��Group��Module�Ĺ�ϵ
            if ( groupDTO.getGroupModuleDTOs() != null && groupDTO.getGroupModuleDTOs().size() > 0 )
            {
               for ( GroupModuleDTO groupModuleDTO : groupDTO.getGroupModuleDTOs() )
               {
                  // ���ɾ��Module��Right�Ĺ�ϵ
                  if ( groupModuleDTO.getGroupModuleRightRelationVOs() != null && groupModuleDTO.getGroupModuleRightRelationVOs().size() > 0 )
                  {
                     for ( GroupModuleRightRelationVO groupModuleRightRelationVO : groupModuleDTO.getGroupModuleRightRelationVOs() )
                     {
                        groupModuleRightRelationVO.setDeleted( GroupModuleRightRelationVO.FALSE );
                        groupModuleRightRelationVO.setModifyBy( groupVO.getModifyBy() );
                        groupModuleRightRelationVO.setModifyDate( groupVO.getModifyDate() );
                        this.groupModuleRightRelationDao.updateGroupModuleRightRelation( groupModuleRightRelationVO );
                     }
                  }

                  // ���ɾ��Module��Rule�Ĺ�ϵ
                  if ( groupModuleDTO.getGroupModuleRuleRelationVOs() != null && groupModuleDTO.getGroupModuleRuleRelationVOs().size() > 0 )
                  {
                     for ( GroupModuleRuleRelationVO groupModuleRuleRelationVO : groupModuleDTO.getGroupModuleRuleRelationVOs() )
                     {
                        groupModuleRuleRelationVO.setDeleted( GroupModuleRuleRelationVO.FALSE );
                        groupModuleRuleRelationVO.setModifyBy( groupVO.getModifyBy() );
                        groupModuleRuleRelationVO.setModifyDate( groupVO.getModifyDate() );
                        this.groupModuleRuleRelationDao.updateGroupModuleRuleRelation( groupModuleRuleRelationVO );
                     }
                  }
               }
            }
         }

         groupVO.setDeleted( GroupVO.FALSE );
         ( ( GroupDao ) getDao() ).updateGroup( groupVO );

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
   public List< Object > getGroupVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( GroupDao ) getDao() ).getGroupVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getRelationVOsByPositionId( String positionId ) throws KANException
   {
      return ( ( PositionGroupRelationDao ) getPositionGroupRelationDao() ).getPositionGroupRelationVOsByPositionId( positionId );
   }

   @Override
   public List< GroupDTO > getGroupDTOsByAccountId( String accountId ) throws KANException
   {
      // ����GroupDTO List�����ڷ�������
      final List< GroupDTO > groupDTOs = new ArrayList< GroupDTO >();

      // ���Account������Ч��ְλ��
      final List< Object > groupVOs = ( ( GroupDao ) getDao() ).getGroupVOsByAccountId( accountId );
      for ( Object groupVOObject : groupVOs )
      {
         final GroupDTO groupDTO = new GroupDTO();

         // ���Group������GroupDTO
         groupDTO.setGroupVO( ( GroupVO ) groupVOObject );

         // ����GroupVO��Module����Ȩ��
         groupDTO.setGroupModuleDTOs( getGroupModuleDTOsByGroupId( ( ( GroupVO ) groupVOObject ).getGroupId() ) );

         // ����GroupVO��Ӧ��Position
         final List< Object > positionGroupRelationVOs = positionGroupRelationDao.getPositionGroupRelationVOsByGroupId( ( ( GroupVO ) groupVOObject ).getGroupId() );
         if ( positionGroupRelationVOs != null && positionGroupRelationVOs.size() > 0 )
         {
            for ( Object positionGroupRelationVOObject : positionGroupRelationVOs )
            {
               groupDTO.getPositionGroupRelationVOs().add( ( PositionGroupRelationVO ) positionGroupRelationVOObject );
            }
         }

         // ����GroupVO��Ӧ��Rule
         final List< Object > groupModuleRuleRelationVOs = groupModuleRuleRelationDao.getGroupModuleRuleRelationVOsByGroupId( ( ( GroupVO ) groupVOObject ).getGroupId() );
         if ( groupModuleRuleRelationVOs != null && groupModuleRuleRelationVOs.size() > 0 )
         {
            for ( Object groupModuleRuleRelationVO : groupModuleRuleRelationVOs )
            {
               groupDTO.getGroupModuleRuleRelationVOs().add( ( GroupModuleRuleRelationVO ) groupModuleRuleRelationVO );
            }
         }

         // �����GroupDTO�б�
         groupDTOs.add( groupDTO );
      }

      return groupDTOs;
   }

   /* Add by Kevin at 2013-06-24 */
   private List< GroupModuleDTO > getGroupModuleDTOsByGroupId( final String groupId ) throws KANException
   {
      // ����GroupModuleDTO List�����ڷ�������
      final List< GroupModuleDTO > groupModuleDTOs = new ArrayList< GroupModuleDTO >();

      // ��õ�ǰְλ����������Module��Right
      final GroupModuleRightRelationVO groupModuleRightRelationVO = new GroupModuleRightRelationVO( groupId, null, null );
      groupModuleRightRelationVO.setStatus( "1" );
      final List< Object > groupModuleRightRelationVOs = this.getGroupModuleRightRelationVOsByCondition( groupModuleRightRelationVO );

      if ( groupModuleRightRelationVOs != null && groupModuleRightRelationVOs.size() > 0 )
      {
         for ( Object groupModuleRightRelationVOObject : groupModuleRightRelationVOs )
         {
            final String moduleId = ( ( GroupModuleRightRelationVO ) groupModuleRightRelationVOObject ).getModuleId();
            // ���б��л��GroupModuleDTOʵ��
            final GroupModuleDTO groupModuleDTO = getGroupModuleDTOFormList( groupModuleDTOs, moduleId );

            // ��ʼ��GroupModuleDTO�е�ModuleVO����
            if ( groupModuleDTO != null )
            {
               if ( groupModuleDTO.getModuleVO() != null && groupModuleDTO.getModuleVO().getModuleId() == null )
               {
                  groupModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // ��ʼ��Module��Ӧ��Ȩ��
               groupModuleDTO.getGroupModuleRightRelationVOs().add( ( GroupModuleRightRelationVO ) groupModuleRightRelationVOObject );
            }
         }
      }

      // ��õ�ǰְλ����������Module��Rule
      final GroupModuleRuleRelationVO groupModuleRuleRelationVO = new GroupModuleRuleRelationVO( groupId, null, null );
      groupModuleRuleRelationVO.setStatus( "1" );
      final List< Object > groupModuleRuleRelationVOs = this.getGroupModuleRuleRelationVOsByCondition( groupModuleRuleRelationVO );

      if ( groupModuleRuleRelationVOs != null && groupModuleRuleRelationVOs.size() > 0 )
      {
         for ( Object groupModuleRuleRelationVOObject : groupModuleRuleRelationVOs )
         {
            final String moduleId = ( ( GroupModuleRuleRelationVO ) groupModuleRuleRelationVOObject ).getModuleId();
            // ���б��л��GroupModuleDTOʵ��
            final GroupModuleDTO groupModuleDTO = getGroupModuleDTOFormList( groupModuleDTOs, moduleId );

            // ��ʼ��GroupModuleDTO�е�ModuleVO����
            if ( groupModuleDTO != null )
            {
               if ( groupModuleDTO.getModuleVO() != null && groupModuleDTO.getModuleVO().getModuleId() == null )
               {
                  groupModuleDTO.setModuleVO( moduleDao.getModuleVOByModuleId( moduleId ) );
               }
               // ��ʼ��Module��Ӧ��Ȩ��
               groupModuleDTO.getGroupModuleRuleRelationVOs().add( ( GroupModuleRuleRelationVO ) groupModuleRuleRelationVOObject );
            }
         }
      }

      return groupModuleDTOs;
   }

   // ���б��л��GroupModuleDTO
   private GroupModuleDTO getGroupModuleDTOFormList( final List< GroupModuleDTO > groupModuleDTOs, final String moduleId )
   {
      // �����ǰ��Ҫ���ҵ�DTO�б�Ϊ�գ�����ModuleIdҲ��Ϊ��
      if ( groupModuleDTOs != null && moduleId != null )
      {
         // DTO�б��к��еĶ���������0
         if ( groupModuleDTOs.size() > 0 )
         {
            // ����DTO�б��ҵ�ƥ��Ĳ�����
            for ( GroupModuleDTO groupModuleDTO : groupModuleDTOs )
            {
               if ( groupModuleDTO.getModuleVO() != null && groupModuleDTO.getModuleVO().getModuleId().equals( moduleId ) )
               {
                  return groupModuleDTO;
               }
            }
         }

         // ����Ҳ���Ŀ������򴴽�ʵ��
         final GroupModuleDTO groupModuleDTO = new GroupModuleDTO();
         groupModuleDTOs.add( groupModuleDTO );
         return groupModuleDTO;
      }

      return null;
   }

   @Override
   public List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return ( ( GroupModuleRightRelationDao ) getGroupModuleRightRelationDao() ).getGroupModuleRightRelationVOsByCondition( groupModuleRightRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return ( ( GroupModuleRuleRelationDao ) getGroupModuleRuleRelationDao() ).getGroupModuleRuleRelationVOsByCondition( groupModuleRuleRelationVO );
   }

   @Override
   public List< Object > getGroupBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( GroupDao ) getDao() ).getGroupBaseViewsByAccountId( accountId );
   }

   @Override
   public int countPositionGroupRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return this.positionGroupRelationDao.countPositionGroupRelationVOsByGroupId( groupId );
   }

}
