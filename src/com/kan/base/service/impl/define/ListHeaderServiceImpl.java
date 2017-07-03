package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ListHeaderServiceImpl extends ContextService implements ListHeaderService
{

   private ListDetailDao listDetailDao;

   public ListDetailDao getListDetailDao()
   {
      return listDetailDao;
   }

   public void setListDetailDao( ListDetailDao listDetailDao )
   {
      this.listDetailDao = listDetailDao;
   }

   @Override
   public PagedListHolder getListHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ListHeaderDao listHeaderDao = ( ListHeaderDao ) getDao();
      pagedListHolder.setHolderSize( listHeaderDao.countListHeaderVOsByCondition( ( ListHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( listHeaderDao.getListHeaderVOsByCondition( ( ListHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( listHeaderDao.getListHeaderVOsByCondition( ( ListHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ListHeaderVO getListHeaderVOByListHeaderId( final String listHeaderId ) throws KANException
   {
      return ( ( ListHeaderDao ) getDao() ).getListHeaderVOByListHeaderId( listHeaderId );
   }

   @Override
   public int insertListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return ( ( ListHeaderDao ) getDao() ).insertListHeader( listHeaderVO );
   }

   @Override
   public int updateListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return ( ( ListHeaderDao ) getDao() ).updateListHeader( listHeaderVO );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public int deleteListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      try
      {
         // 删除ListHeaderVO同时，还要删除与之关联到的ListDetailVO
         if ( listHeaderVO != null && listHeaderVO.getListHeaderId() != null && !listHeaderVO.getListHeaderId().trim().equals( "" ) )
         {
            // 开启事务
            startTransaction();

            final ListDetailVO listDetailVO = new ListDetailVO();
            listDetailVO.setListHeaderId( listHeaderVO.getListHeaderId() );

            // 先标记删除List Detail
            for ( Object objectListDetail : this.listDetailDao.getListDetailVOsByCondition( listDetailVO ) )
            {
               ( ( ListDetailVO ) objectListDetail ).setDeleted( ListDetailVO.FALSE );
               ( ( ListDetailVO ) objectListDetail ).setModifyBy( listHeaderVO.getModifyBy() );
               ( ( ListDetailVO ) objectListDetail ).setModifyDate( listHeaderVO.getModifyDate() );
               this.listDetailDao.updateListDetail( ( ( ListDetailVO ) objectListDetail ) );
            }

            // 最后标记删除List Header
            listHeaderVO.setDeleted( ListHeaderVO.FALSE );
            ( ( ListHeaderDao ) getDao() ).updateListHeader( listHeaderVO );

            // 提交事务 
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< ListDTO > getListDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化返回值对象
      final List< ListDTO > listDTOs = new ArrayList< ListDTO >();

      // 获取AccountId下所有ListHeaderVO
      final List< Object > listHeaderVOs = ( ( ListHeaderDao ) getDao() ).getListHeaderVOsByAccountId( accountId );

      // 存在ListHeaderVO List
      if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
      {
         for ( Object listHeaderVOObject : listHeaderVOs )
         {
            // 初始化ListHeaderVO
            final ListHeaderVO listHeaderVO = ( ListHeaderVO ) listHeaderVOObject;

            // 获取ListDetailVO列表
            final List< Object > listDetailVOs = this.getListDetailDao().getListDetailVOsByListHeaderId( listHeaderVO.getListHeaderId() );

            // 当前ListHeaderVO无主对象关联
            if ( KANUtil.filterEmpty( listHeaderVO.getParentId(), "0" ) == null )
            {
               // 初始化ListDTO 
               final ListDTO listDTO = new ListDTO();
               listDTO.setListHeaderVO( listHeaderVO );

               // 存在ListDetailVO列表
               if ( listDetailVOs != null && listDetailVOs.size() > 0 )
               {
                  for ( Object listDetailVOObject : listDetailVOs )
                  {
                     listDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
                  }
               }

               listDTOs.add( listDTO );
            }
            // 当前ListHeaderVO有主对象关联
            else
            {
               // 找到关联主对象，并附和主对象
               fetchSubListDTO( listHeaderVO, listDetailVOs, listDTOs );
            }

         }
      }

      return listDTOs;
   }

   // 找到关联主对象，并附和主对象
   private void fetchSubListDTO( final ListHeaderVO listHeaderVO, final List< Object > listDetailVOs, final List< ListDTO > listDTOs )
   {
      if ( listDTOs != null && listDTOs.size() > 0 && listHeaderVO != null && KANUtil.filterEmpty( listHeaderVO.getParentId() ) != null )
      {
         for ( ListDTO tempListDTO : listDTOs )
         {
            if ( tempListDTO.getListHeaderVO().getListHeaderId().equals( listHeaderVO.getParentId() ) )
            {
               final ListDTO subListDTO = new ListDTO();
               subListDTO.setListHeaderVO( listHeaderVO );

               if ( listDetailVOs != null && listDetailVOs.size() > 0 )
               {
                  for ( Object listDetailVOObject : listDetailVOs )
                  {
                     // ( ( ListDetailVO ) listDetailVOObject ).setColumnIndex( "50" );
                     subListDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
                  }
               }

               tempListDTO.getSubListDTOs().add( subListDTO );
            }
         }
      }
   }

}
