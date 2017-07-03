package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.ReportHeaderDao;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.dao.inf.define.SearchHeaderDao;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.SearchDTO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.util.KANException;

public class SearchHeaderServiceImpl extends ContextService implements SearchHeaderService
{

   private SearchDetailDao searchDetailDao;

   private ListHeaderDao listHeaderDao;

   private ListDetailDao listDetailDao;

   private ReportHeaderDao reportHeaderDao;

   private ReportDetailDao reportDetailDao;

   public ListHeaderDao getListHeaderDao()
   {
      return listHeaderDao;
   }

   public void setListHeaderDao( ListHeaderDao listHeaderDao )
   {
      this.listHeaderDao = listHeaderDao;
   }

   public ReportHeaderDao getReportHeaderDao()
   {
      return reportHeaderDao;
   }

   public void setReportHeaderDao( ReportHeaderDao reportHeaderDao )
   {
      this.reportHeaderDao = reportHeaderDao;
   }

   public SearchDetailDao getSearchDetailDao()
   {
      return searchDetailDao;
   }

   public void setSearchDetailDao( SearchDetailDao searchDetailDao )
   {
      this.searchDetailDao = searchDetailDao;
   }

   public ListDetailDao getListDetailDao()
   {
      return listDetailDao;
   }

   public void setListDetailDao( ListDetailDao listDetailDao )
   {
      this.listDetailDao = listDetailDao;
   }

   public ReportDetailDao getReportDetailDao()
   {
      return reportDetailDao;
   }

   public void setReportDetailDao( ReportDetailDao reportDetailDao )
   {
      this.reportDetailDao = reportDetailDao;
   }

   @Override
   public PagedListHolder getSearchHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SearchHeaderDao searchHeaderDao = ( SearchHeaderDao ) getDao();
      pagedListHolder.setHolderSize( searchHeaderDao.countSearchHeaderVOsByCondition( ( SearchHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( searchHeaderDao.getSearchHeaderVOsByCondition( ( SearchHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( searchHeaderDao.getSearchHeaderVOsByCondition( ( SearchHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SearchHeaderVO getSearchHeaderVOBySearchHeaderId( final String searchHeaderId ) throws KANException
   {
      return ( ( SearchHeaderDao ) getDao() ).getSearchHeaderVOBySearchHeaderId( searchHeaderId );
   }

   @Override
   public int insertSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return ( ( SearchHeaderDao ) getDao() ).insertSearchHeader( searchHeaderVO );
   }

   @Override
   public int updateSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return ( ( SearchHeaderDao ) getDao() ).updateSearchHeader( searchHeaderVO );
   }

   @Override
   public int deleteSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      try
      {
         if ( searchHeaderVO != null && searchHeaderVO.getSearchHeaderId() != null && !( "" ).equals( searchHeaderVO.getSearchHeaderId().trim() ) )
         {
            // 删除SearchHeaderVO同时，还要删除与之关联到的其他对象
            //（SearchDetail，ListHeader，ListDetail，ReportHeader，ReportDetail）
            // 开启事务
            startTransaction();

            // 设置searchHeaderVO的deleted值
            searchHeaderVO.setDeleted( SearchHeaderVO.FALSE );
            // 标记删除SearchHeaderVO
            ( ( SearchHeaderDao ) getDao() ).updateSearchHeader( searchHeaderVO );

            // 根据SearchHeaderVO删除SearchHeaderVO对应的SearchDetailVOs
            deleteSearchDetailVOsBySearchHeaderVO( searchHeaderVO );

            // 根据SearchHeaderVO删除SearchHeaderVO对应的ListHeaderVOs,ListDetailVO
            deleteListHeaderVOsAndListDetailVOsBySearchHeaderVO( searchHeaderVO );

            // 根据SearchHeaderVO删除ReportHeaderVOs，ReportDetailVOs
            // deleteReportHeaderVOsAndReportDetailVOsBySearchHeaderVO( searchHeaderVO );

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

   // 根据SearchHeaderVO删除SearchHeaderVO对应的SearchDetailVOs
   private void deleteSearchDetailVOsBySearchHeaderVO( SearchHeaderVO searchHeaderVO ) throws KANException
   {
      // 新建SearchDetailVO做删除条件
      final SearchDetailVO searchDetailVO = new SearchDetailVO();
      // 设置SearchDetailVO的SearchHeaderId
      searchDetailVO.setSearchHeaderId( searchHeaderVO.getSearchHeaderId() );
      // 遍历寻找到的detailVO以及标记删除
      for ( Object objectSearchDetailVO : this.searchDetailDao.getSearchDetailVOsByCondition( searchDetailVO ) )
      {
         ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyBy( searchHeaderVO.getModifyBy() );
         ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyDate( searchHeaderVO.getModifyDate() );
         ( ( SearchDetailVO ) objectSearchDetailVO ).setDeleted( SearchHeaderVO.FALSE );
         this.searchDetailDao.updateSearchDetail( ( SearchDetailVO ) objectSearchDetailVO );
      }
   }

   // 根据SearchHeaderVO删除SearchHeaderVO对应的ListHeaderVOs,ListDetailVOs
   private void deleteListHeaderVOsAndListDetailVOsBySearchHeaderVO( SearchHeaderVO searchHeaderVO ) throws KANException
   {
      final ListHeaderVO listHeaderVO = new ListHeaderVO();
      listHeaderVO.setSearchId( searchHeaderVO.getSearchHeaderId() );
      listHeaderVO.setAccountId( searchHeaderVO.getAccountId() );

      ListDetailVO listDetailVO;
      for ( Object objectListHeaderVO : this.listHeaderDao.getListHeaderVOsByCondition( listHeaderVO ) )
      {
         listDetailVO = new ListDetailVO();
         listDetailVO.setListHeaderId( ( ( ListHeaderVO ) objectListHeaderVO ).getListHeaderId() );

         for ( Object objectListDetailVO : this.listDetailDao.getListDetailVOsByCondition( listDetailVO ) )
         {
            ( ( ListDetailVO ) objectListDetailVO ).setModifyBy( searchHeaderVO.getModifyBy() );
            ( ( ListDetailVO ) objectListDetailVO ).setModifyDate( searchHeaderVO.getModifyDate() );
            ( ( ListDetailVO ) objectListDetailVO ).setDeleted( SearchHeaderVO.FALSE );
            this.listDetailDao.updateListDetail( ( ListDetailVO ) objectListDetailVO );
         }

         ( ( ListHeaderVO ) objectListHeaderVO ).setModifyBy( searchHeaderVO.getModifyBy() );
         ( ( ListHeaderVO ) objectListHeaderVO ).setModifyDate( searchHeaderVO.getModifyDate() );
         ( ( ListHeaderVO ) objectListHeaderVO ).setDeleted( SearchHeaderVO.FALSE );
         this.listHeaderDao.updateListHeader( ( ListHeaderVO ) objectListHeaderVO );
      }
   }


   @Override
   public List< SearchDTO > getSearchDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化SearchDTO List
      final List< SearchDTO > searchDTOs = new ArrayList< SearchDTO >();

      // 按照AccountId获取SearchHeaderVOs
      final List< Object > searchHeaderVOs = ( ( SearchHeaderDao ) getDao() ).getSearchHeaderVOsByAccountId( accountId );

      // 遍历SearchHeaderVOs
      if ( searchHeaderVOs != null && searchHeaderVOs.size() > 0 )
      {
         for ( Object searchHeaderVOObject : searchHeaderVOs )
         {
            // 初始化SearchDTO
            final SearchDTO searchDTO = new SearchDTO();

            // 设置SearchHeaderVO对象
            searchDTO.setSearchHeaderVO( ( SearchHeaderVO ) searchHeaderVOObject );
            // 按照SearchHeaderId获取SearchDetailVO列表
            final List< Object > searchDetailVOs = this.searchDetailDao.getSearchDetailVOsBySearchHeaderId( ( ( SearchHeaderVO ) searchHeaderVOObject ).getSearchHeaderId() );

            // 遍历SearchDetailVOs
            if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
            {
               for ( Object searchDetailVOObject : searchDetailVOs )
               {
                  searchDTO.getSearchDetailVOs().add( ( SearchDetailVO ) searchDetailVOObject );
               }
            }

            searchDTOs.add( searchDTO );
         }
      }

      return searchDTOs;
   }
}
