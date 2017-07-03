package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.PositionDao;
import com.kan.base.domain.management.PositionDTO;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPPositionService;
import com.kan.base.service.inf.management.PositionService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class PositionServiceImpl extends ContextService implements PositionService, CPPositionService
{

   private EmployeeContractDao employeeContractDao;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
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
   public PositionVO getPositionVOByPositionId( String positionId ) throws KANException
   {
      // 实例化positionVO
      PositionVO positionVO = new PositionVO();
      try
      {
         // 开启事务
         startTransaction();

         // 通过Id获取positionVO
         positionVO = ( ( PositionDao ) getDao() ).getPositionVOByPositionId( positionId );
         // 获取positionId对应的父节点的positionVO
         final PositionVO parentPositionVO = ( ( PositionDao ) getDao() ).getPositionVOByPositionId( positionVO.getParentPositionId() );
         // 生成positionVO对应的parentPositionName
         if ( parentPositionVO != null && !"".equals( parentPositionVO.getTitleZH().trim() ) )
         {
            // 生成parentPositionVO的parentPositionName
            final String parentPositionName = parentPositionVO.getTitleZH() + " - " + ( ( parentPositionVO.getTitleEN() == null ) ? "" : ( parentPositionVO.getTitleEN() ) );
            positionVO.setParentPositionName( parentPositionName );
         }
         else if ( positionId.equals( "1" ) )
         {
            // 是根节点
            positionVO.setParentPositionName( "" );
         }
         else
         {
            positionVO.setParentPositionName( "" );
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
      return positionVO;
   }

   @Override
   public int updatePosition( PositionVO positionVO ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).updatePosition( positionVO );
   }

   @Override
   public int insertPosition( PositionVO positionVO ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).insertPosition( positionVO );
   }

   @Override
   public int deletePosition( PositionVO positionVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         // 标记删除当前PositionVO
         positionVO.setDeleted( PositionVO.FALSE );
         ( ( PositionDao ) getDao() ).updatePosition( positionVO );

         // 从常量获取当前节点开始的职位树
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( positionVO.getAccountId() ).getEmployeePositionDTOByPositionId( positionVO.getPositionId() );

         // 递归调用标记删除方法
         deletePosition( positionDTO, positionVO.getModifyBy(), positionVO.getModifyDate() );

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
   private void deletePosition( PositionDTO positionDTO, String modifyBy, Date modifyDate )
   {
      if ( positionDTO.getPositionDTOs() != null )
      {
         for ( PositionDTO childPositionDTO : positionDTO.getPositionDTOs() )
         {
            if ( childPositionDTO.getPositionDTOs() != null && childPositionDTO.getPositionDTOs().size() > 0 )
            {
               deletePosition( childPositionDTO, modifyBy, modifyDate );
            }
            // 标记删除Position
            if ( childPositionDTO.getPositionVO() != null )
            {
               childPositionDTO.getPositionVO().setDeleted( PositionVO.FALSE );
               childPositionDTO.getPositionVO().setModifyBy( modifyBy );
               childPositionDTO.getPositionVO().setModifyDate( modifyDate );
               try
               {
                  ( ( PositionDao ) getDao() ).updatePosition( childPositionDTO.getPositionVO() );
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
   public List< PositionDTO > getPositionDTOsByAccountId( final String accountId ) throws KANException
   {
      // 创建PositionDTO List，用于返回数据
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

      // 新建PositionVO用于传参
      final PositionVO positionVO = new PositionVO();
      // 默认根节点的父节点值为“0”
      positionVO.setParentPositionId( "0" );
      positionVO.setAccountId( accountId );

      // 获得根节点
      final List< Object > rootPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( positionVO );

      for ( Object rootPositionObject : rootPositionVOs )
      {
         // 递归遍历
         positionDTOs.add( fetchPositionDTO( ( PositionVO ) rootPositionObject ) );
      }

      return positionDTOs;
   }

   // 递归方法
   private PositionDTO fetchPositionDTO( final PositionVO positionVO ) throws KANException
   {
      final PositionDTO positionDTO = new PositionDTO();
      // 设置PositionVO对象
      positionDTO.setPositionVO( positionVO );

      // 继续查找下一层Position
      final PositionVO subPositionVO = new PositionVO();
      subPositionVO.setAccountId( positionVO.getAccountId() );
      subPositionVO.setParentPositionId( positionVO.getPositionId() );
      // 查找当前职位对应的服务协议数量
      String employeeContractNumber = "0";
      EmployeeContractVO employeeContractVO = new EmployeeContractVO();
      employeeContractVO.setAccountId( positionVO.getAccountId() );
      employeeContractVO.setPositionId( positionVO.getPositionId() );
      employeeContractNumber = String.valueOf( this.employeeContractDao.countEmployeeContractVOsByCondition( employeeContractVO ) );
      positionDTO.setEmployeeContractNumber( employeeContractNumber );

      final List< Object > subPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( subPositionVO );

      for ( Object subPositionObject : subPositionVOs )
      {
         // 递归调用
         positionDTO.getPositionDTOs().add( fetchPositionDTO( ( PositionVO ) subPositionObject ) );
      }
      return positionDTO;
   }

   @Override
   public List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionBaseViewsByAccountId( accountId );
   }

}
