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
      // 实例化skillVO
      SkillVO skillVO = new SkillVO();
      try
      {
         // 开启事务
         startTransaction();

         // 通过Id获取skillVO
         skillVO = ( ( SkillDao ) getDao() ).getSkillVOBySkillId( skillId );
         // 获取skillId对应的父节点的skillVO
         final SkillVO parentSkillVO = ( ( SkillDao ) getDao() ).getSkillVOBySkillId( skillVO.getParentSkillId() );
         // 生成skillVO对应的parentSkillName
         if ( parentSkillVO != null && !"".equals( parentSkillVO.getSkillNameZH().trim() ) )
         {
            // 生成parentSkillVO的parentSkillName
            final String parentSkillName = parentSkillVO.getSkillNameZH() + " - " + ( ( parentSkillVO.getSkillNameEN() == null ) ? "" : ( parentSkillVO.getSkillNameEN() ) );
            skillVO.setParentSkillName( parentSkillName );
         }
         else if ( "0".equals( skillVO.getParentSkillId() ) )
         {
            // 是根节点
            skillVO.setParentSkillName( "" );
         }
         else
         {
            skillVO.setParentSkillName( "" );
         }
         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
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
         // 开启事务
         this.startTransaction();
         // 标记删除当前skillVO
         skillVO.setDeleted( SkillVO.FALSE );
         ( ( SkillDao ) getDao() ).updateSkill( skillVO );

         // 从常量获取当前节点开始的职位树
         final List< SkillDTO > skillDTOs = KANConstants.getKANAccountConstants( skillVO.getAccountId() ).getSkillDTOsByParentSkillId( skillVO.getSkillId() );

         // 递归调用标记删除方法
         deleteSkill( skillDTOs, skillVO.getModifyBy(), skillVO.getModifyDate() );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   // 递归标记删除方法
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
            // 标记删除Position
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
      // 创建SkillDTO List，用于返回数据
      final List< SkillDTO > skillDTOs = new ArrayList< SkillDTO >();

      // 新建SkillVO用于传参
      final SkillVO skillVO = new SkillVO();
      // 默认根节点的父节点值为“0”
      skillVO.setParentSkillId( "0" );
      skillVO.setAccountId( accountId );

      // 获得根节点
      final List< Object > rootSkillVOs = ( ( SkillDao ) getDao() ).getSkillVOsByParentSkillId( skillVO );

      for ( Object rootSkillObject : rootSkillVOs )
      {
         // 递归遍历
         skillDTOs.add( fetchSkillDTO( ( SkillVO ) rootSkillObject ) );
      }

      return skillDTOs;
   }
   
   /**优化 去掉递归调数据库 
    * @param accountId
    * @return
    * @throws KANException
    */
   @Override
   public List< SkillDTO > getSkillDTOsByAccountIdOptimize( final String accountId ) throws KANException
   {
      // 创建SkillDTO List，用于返回数据
      final List< SkillDTO > skillDTOs = new ArrayList< SkillDTO >();

      // 新建SkillVO用于传参
      final SkillVO skillVO = new SkillVO();
      // 默认根节点的父节点值为“0”
      skillVO.setParentSkillId( null );
      skillVO.setAccountId( accountId );

      // 获得根节点
      final List< Object > rootSkillVOs = ( ( SkillDao ) getDao() ).getSkillVOsByParentSkillId( skillVO );

      for ( Object rootSkillObject : rootSkillVOs )
      {
         // 递归遍历
         if("0".equals(((SkillVO)rootSkillObject).getParentSkillId())){
//            List< Object > subSkillVOs = new ArrayList< Object >();
//            subSkillVOs.addAll( rootSkillVOs );
            skillDTOs.add( fetchSkillDTOOptimize( ( SkillVO ) rootSkillObject,rootSkillVOs ) );
         }
      }

      return skillDTOs;
   }

   // 递归方法
   private SkillDTO fetchSkillDTOOptimize( final SkillVO skillVO,List< Object > skillVOs ) throws KANException
   {
      final SkillDTO skillDTO = new SkillDTO();
      // 设置SkillVO对象
      skillDTO.setSkillVO( skillVO );
      for ( Object subSkillObject : skillVOs )
      {
         // 递归调用
         if(skillVO.getSkillId().equals( (( SkillVO)subSkillObject).getParentSkillId())){
            skillDTO.getSkillDTOs().add( fetchSkillDTOOptimize( ( SkillVO ) subSkillObject ,skillVOs) );
         }
      }
      return skillDTO;
   }
   // 递归方法
   private SkillDTO fetchSkillDTO( final SkillVO skillVO ) throws KANException
   {
      final SkillDTO skillDTO = new SkillDTO();
      // 设置SkillVO对象
      skillDTO.setSkillVO( skillVO );

      // 继续查找下一层Skill
      final SkillVO subSkillVO = new SkillVO();
      subSkillVO.setAccountId( skillVO.getAccountId() );
      subSkillVO.setParentSkillId( skillVO.getSkillId() );
      // 查找技能对应的雇员技能数量
      EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
      employeeSkillVO.setAccountId( skillVO.getAccountId() );
      employeeSkillVO.setSkillId( skillVO.getSkillId() );
      final Integer employeeSkillNumber = this.employeeSkillDao.countEmployeeSkillVOsByCondition( employeeSkillVO );
      // 查找技能对应的职位技能数量
      PositionVO positionVO = new PositionVO();
      positionVO.setAccountId( skillVO.getAccountId() );
      positionVO.setSkill( skillVO.getSkillId() );
      final Integer positionSkillNumber = this.positionDao.countPositionVOsByCondition( positionVO );
      skillDTO.setExtended( employeeSkillNumber + positionSkillNumber > 0 ? "1" : "2" );
      final List< Object > subSkillVOs = ( ( SkillDao ) getDao() ).getSkillVOsByParentSkillId( subSkillVO );

      for ( Object subSkillObject : subSkillVOs )
      {
         // 递归调用
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
