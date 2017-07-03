package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.PositionDao;
import com.kan.base.dao.inf.management.SkillDao;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.domain.management.SkillBaseView;
import com.kan.base.domain.management.SkillDTO;
import com.kan.base.domain.management.SkillVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPSkillService;
import com.kan.base.service.inf.management.SkillService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeSkillDao;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;

public class SkillServiceImpl extends ContextService implements SkillService, CPSkillService
{

   private EmployeeSkillDao employeeSkillDao;

   private PositionDao positionDao;

   public EmployeeSkillDao getEmployeeSkillDao()
   {
      return employeeSkillDao;
   }

   public void setEmployeeSkillDao( EmployeeSkillDao employeeSkillDao )
   {
      this.employeeSkillDao = employeeSkillDao;
   }

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   @Override
   public PagedListHolder getSkillVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SkillDao skillDao = ( SkillDao ) getDao();
      pagedListHolder.setHolderSize( skillDao.countSkillVOsByCondition( ( SkillVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( skillDao.getSkillVOsByCondition( ( SkillVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( skillDao.getSkillVOsByCondition( ( SkillVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SkillVO getSkillVOBySkillId( final String skillId ) throws KANException
   {
      // ʵ����skillVO
      SkillVO skillVO = new SkillVO();
      try
      {
         // ��������
         startTransaction();

         // ͨ��Id��ȡskillVO
         skillVO = ( ( SkillDao ) getDao() ).getSkillVOBySkillId( skillId );
         // ��ȡskillId��Ӧ�ĸ��ڵ��skillVO
         final SkillVO parentSkillVO = ( ( SkillDao ) getDao() ).getSkillVOBySkillId( skillVO.getParentSkillId() );
         // ����skillVO��Ӧ��parentSkillName
         if ( parentSkillVO != null && !"".equals( parentSkillVO.getSkillNameZH().trim() ) )
         {
            // ����parentSkillVO��parentSkillName
            final String parentSkillName = parentSkillVO.getSkillNameZH() + " - " + ( ( parentSkillVO.getSkillNameEN() == null ) ? "" : ( parentSkillVO.getSkillNameEN() ) );
            skillVO.setParentSkillName( parentSkillName );
         }
         else if ( "0".equals( skillVO.getParentSkillId() ) )
         {
            // �Ǹ��ڵ�
            skillVO.setParentSkillName( "" );
         }
         else
         {
            skillVO.setParentSkillName( "" );
         }
         // �ύ���� 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      return skillVO;
   }

   @Override
   public List< SkillVO > getAllSkills() throws KANException
   {
      return null;
   }

   @Override
   public int updateSkill( final SkillVO skillVO ) throws KANException
   {
      return ( ( SkillDao ) getDao() ).updateSkill( skillVO );
   }

   @Override
   public int insertSkill( final SkillVO skillVO ) throws KANException
   {
      return ( ( SkillDao ) getDao() ).insertSkill( skillVO );
   }

   @Override
   public int deleteSkill( SkillVO skillVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();
         // ���ɾ����ǰskillVO
         skillVO.setDeleted( SkillVO.FALSE );
         ( ( SkillDao ) getDao() ).updateSkill( skillVO );

         // �ӳ�����ȡ��ǰ�ڵ㿪ʼ��ְλ��
         final List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( skillVO.getAccountId() ).getSkillDTOsByParentSkillId( skillVO.getSkillId() );

         // �ݹ���ñ��ɾ������
         deleteSkill( skillDTOs, skillVO.getModifyBy(), skillVO.getModifyDate() );

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

   // �ݹ���ɾ������
   private void deleteSkill( List< SkillDTO > skillDTOs, String modifyBy, Date modifyDate )
   {
      if ( skillDTOs != null )
      {
         for ( SkillDTO skillDTO : skillDTOs )
         {
            if ( skillDTO.getSkillDTOs() != null && skillDTO.getSkillDTOs().size() > 0 )
            {
               deleteSkill( skillDTO.getSkillDTOs(), modifyBy, modifyDate );
            }
            // ���ɾ��Position
            if ( skillDTO.getSkillVO() != null )
            {
               skillDTO.getSkillVO().setDeleted( SkillVO.FALSE );
               skillDTO.getSkillVO().setModifyBy( modifyBy );
               skillDTO.getSkillVO().setModifyDate( modifyDate );
               try
               {
                  ( ( SkillDao ) getDao() ).updateSkill( skillDTO.getSkillVO() );
               }
               catch ( KANException e )
               {
                  e.printStackTrace();
               }
            }
         }
      }
   }

   @Override
   public List< SkillDTO > getSkillDTOsByAccountId( final String accountId ) throws KANException
   {
      // ����SkillDTO List�����ڷ�������
      final List< SkillDTO > skillDTOs = new ArrayList< SkillDTO >();

      // �½�SkillVO���ڴ���
      final SkillVO skillVO = new SkillVO();
      // Ĭ�ϸ��ڵ�ĸ��ڵ�ֵΪ��0��
      skillVO.setParentSkillId( "0" );
      skillVO.setAccountId( accountId );

      // ��ø��ڵ�
      final List< Object > rootSkillVOs = ( ( SkillDao ) getDao() ).getSkillVOsByParentSkillId( skillVO );

      for ( Object rootSkillObject : rootSkillVOs )
      {
         // �ݹ����
         skillDTOs.add( fetchSkillDTO( ( SkillVO ) rootSkillObject ) );
      }

      return skillDTOs;
   }
   
   /**�Ż� ȥ���ݹ�����ݿ� 
    * @param accountId
    * @return
    * @throws KANException
    */
   @Override
   public List< SkillDTO > getSkillDTOsByAccountIdOptimize( final String accountId ) throws KANException
   {
      // ����SkillDTO List�����ڷ�������
      final List< SkillDTO > skillDTOs = new ArrayList< SkillDTO >();

      // �½�SkillVO���ڴ���
      final SkillVO skillVO = new SkillVO();
      // Ĭ�ϸ��ڵ�ĸ��ڵ�ֵΪ��0��
      skillVO.setParentSkillId( null );
      skillVO.setAccountId( accountId );

      // ��ø��ڵ�
      final List< Object > rootSkillVOs = ( ( SkillDao ) getDao() ).getSkillVOsByParentSkillId( skillVO );

      for ( Object rootSkillObject : rootSkillVOs )
      {
         // �ݹ����
         if("0".equals(((SkillVO)rootSkillObject).getParentSkillId())){
//            List< Object > subSkillVOs = new ArrayList< Object >();
//            subSkillVOs.addAll( rootSkillVOs );
            skillDTOs.add( fetchSkillDTOOptimize( ( SkillVO ) rootSkillObject,rootSkillVOs ) );
         }
      }

      return skillDTOs;
   }

   // �ݹ鷽��
   private SkillDTO fetchSkillDTOOptimize( final SkillVO skillVO,List< Object > skillVOs ) throws KANException
   {
      final SkillDTO skillDTO = new SkillDTO();
      // ����SkillVO����
      skillDTO.setSkillVO( skillVO );
      for ( Object subSkillObject : skillVOs )
      {
         // �ݹ����
         if(skillVO.getSkillId().equals( (( SkillVO)subSkillObject).getParentSkillId())){
            skillDTO.getSkillDTOs().add( fetchSkillDTOOptimize( ( SkillVO ) subSkillObject ,skillVOs) );
         }
      }
      return skillDTO;
   }
   // �ݹ鷽��
   private SkillDTO fetchSkillDTO( final SkillVO skillVO ) throws KANException
   {
      final SkillDTO skillDTO = new SkillDTO();
      // ����SkillVO����
      skillDTO.setSkillVO( skillVO );

      // ����������һ��Skill
      final SkillVO subSkillVO = new SkillVO();
      subSkillVO.setAccountId( skillVO.getAccountId() );
      subSkillVO.setParentSkillId( skillVO.getSkillId() );
      // ���Ҽ��ܶ�Ӧ�Ĺ�Ա��������
      EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
      employeeSkillVO.setAccountId( skillVO.getAccountId() );
      employeeSkillVO.setSkillId( skillVO.getSkillId() );
      final Integer employeeSkillNumber = this.employeeSkillDao.countEmployeeSkillVOsByCondition( employeeSkillVO );
      // ���Ҽ��ܶ�Ӧ��ְλ��������
      PositionVO positionVO = new PositionVO();
      positionVO.setAccountId( skillVO.getAccountId() );
      positionVO.setSkill( skillVO.getSkillId() );
      final Integer positionSkillNumber = this.positionDao.countPositionVOsByCondition( positionVO );
      skillDTO.setExtended( employeeSkillNumber + positionSkillNumber > 0 ? "1" : "2" );
      final List< Object > subSkillVOs = ( ( SkillDao ) getDao() ).getSkillVOsByParentSkillId( subSkillVO );

      for ( Object subSkillObject : subSkillVOs )
      {
         // �ݹ����
         skillDTO.getSkillDTOs().add( fetchSkillDTO( ( SkillVO ) subSkillObject ) );
      }
      return skillDTO;
   }

   @Override
   public List< SkillBaseView > getSkillBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return getSkillBaseViewsByAccountId( accountId, null );
   }

   @Override
   public List< SkillBaseView > getSkillBaseViewsByAccountId( final String accountId, final String corpId ) throws KANException
   {
      final List< SkillBaseView > skillBaseViews = new ArrayList< SkillBaseView >();
      final SkillVO tempSkillVO = new SkillVO();
      tempSkillVO.setAccountId( accountId );
      tempSkillVO.setCorpId( corpId );
      final List< Object > objectSkillBaseViews = ( ( SkillDao ) getDao() ).getSkillBaseViewsByClientId( tempSkillVO );
      if ( objectSkillBaseViews != null && objectSkillBaseViews.size() > 0 )
      {
         for ( Object objectSkillBaseView : objectSkillBaseViews )
         {
            skillBaseViews.add( ( SkillBaseView ) objectSkillBaseView );
         }
      }
      return skillBaseViews;
   }
}
