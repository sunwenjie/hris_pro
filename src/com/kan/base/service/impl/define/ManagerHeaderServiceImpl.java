package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ManagerDetailDao;
import com.kan.base.dao.inf.define.ManagerHeaderDao;
import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.util.KANException;

public class ManagerHeaderServiceImpl extends ContextService implements ManagerHeaderService
{

   // 注入ManagerDetailDao
   private ManagerDetailDao managerDetailDao;

   public ManagerDetailDao getManagerDetailDao()
   {
      return managerDetailDao;
   }

   public void setManagerDetailDao( ManagerDetailDao managerDetailDao )
   {
      this.managerDetailDao = managerDetailDao;
   }

   @Override
   public PagedListHolder getManagerHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ManagerHeaderDao managerHeaderDao = ( ManagerHeaderDao ) getDao();
      pagedListHolder.setHolderSize( managerHeaderDao.countManagerHeaderVOsByCondition( ( ManagerHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( managerHeaderDao.getManagerHeaderVOsByCondition( ( ManagerHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( managerHeaderDao.getManagerHeaderVOsByCondition( ( ManagerHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ManagerHeaderVO getManagerHeaderVOByManagerHeaderId( final String managerHeaderId ) throws KANException
   {
      return ( ( ManagerHeaderDao ) getDao() ).getManagerHeaderVOByManagerHeaderId( managerHeaderId );
   }

   @Override
   public int insertManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return ( ( ManagerHeaderDao ) getDao() ).insertManagerHeader( managerHeaderVO );
   }

   @Override
   public int updateManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return ( ( ManagerHeaderDao ) getDao() ).updateManagerHeader( managerHeaderVO );
   }

   @Override
   public int deleteManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         managerHeaderVO.setDeleted( ManagerHeaderVO.FALSE );
         // 标记删除ManagerHeaderVO
         ( ( ManagerHeaderDao ) getDao() ).updateManagerHeader( managerHeaderVO );

         // 获取ManagerDetailVO列表
         final List< Object > managerDetailVOs = this.getManagerDetailDao().getManagerDetailVOsByManagerHeaderId( managerHeaderVO.getManagerHeaderId() );

         // 存在ManagerDetailVO列表
         if ( managerDetailVOs != null && managerDetailVOs.size() > 0 )
         {
            for ( Object managerDetailVOObject : managerDetailVOs )
            {
               ( ( ManagerDetailVO ) managerDetailVOObject ).setDeleted( ManagerHeaderVO.FALSE );
               ( ( ManagerDetailVO ) managerDetailVOObject ).setModifyBy( managerHeaderVO.getModifyBy() );
               ( ( ManagerDetailVO ) managerDetailVOObject ).setModifyDate( managerHeaderVO.getModifyDate() );
               
               // 标记删除ManagerDetailVO
               this.getManagerDetailDao().updateManagerDetail( ( ManagerDetailVO ) managerDetailVOObject );
            }
         }

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

   @Override
   public List< ManagerDTO > getManagerDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化ManagerDTO List
      final List< ManagerDTO > managerDTOs = new ArrayList< ManagerDTO >();

      // 按照accountId获取ManagerHeaderVO List
      final List< Object > managerHeaderVOs = ( ( ManagerHeaderDao ) getDao() ).getManagerHeaderVOsByAccountId( accountId );

      // 存在ManagerHeaderVO List
      if ( managerHeaderVOs != null && managerHeaderVOs.size() > 0 )
      {
         for ( Object managerHeaderVOObject : managerHeaderVOs )
         {
            // 初始化ManagerDTO
            final ManagerDTO managerDTO = new ManagerDTO();

            // 设置ManagerVO
            managerDTO.setManagerHeaderVO( ( ManagerHeaderVO ) managerHeaderVOObject );

            // 按照managerHeaderId获取ManagerDetailVO List
            final List< Object > managerDetailVOs = this.getManagerDetailDao().getManagerDetailVOsByManagerHeaderId( ( ( ManagerHeaderVO ) managerHeaderVOObject ).getManagerHeaderId() );

            // 存在ManagerDetailVO List
            if ( managerDetailVOs != null && managerDetailVOs.size() > 0 )
            {
               for ( Object managerDetailVOObject : managerDetailVOs )
               {
                  managerDTO.getManagerDetailVOs().add( ( ManagerDetailVO ) managerDetailVOObject );
               }
            }

            // 装载ManagerDTO
            managerDTOs.add( managerDTO );
         }
      }

      return managerDTOs;
   }

}
