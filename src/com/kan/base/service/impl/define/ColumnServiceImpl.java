package com.kan.base.service.impl.define;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ColumnDao;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.util.KANException;

public class ColumnServiceImpl extends ContextService implements ColumnService
{

   private SearchDetailDao searchDetailDao;

   private ListDetailDao listDetailDao;

   private ReportDetailDao reportDetailDao;

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
   public PagedListHolder getColumnVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ColumnDao columnDao = ( ColumnDao ) getDao();
      pagedListHolder.setHolderSize( columnDao.countColumnVOsByCondition( ( ColumnVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( columnDao.getColumnVOsByCondition( ( ColumnVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( columnDao.getColumnVOsByCondition( ( ColumnVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ColumnVO getColumnVOByColumnId( final String columnId ) throws KANException
   {
      return ( ( ColumnDao ) getDao() ).getColumnVOByColumnId( columnId );
   }

   @Override
   public int insertColumn( final ColumnVO columnVO ) throws KANException
   {
      return ( ( ColumnDao ) getDao() ).insertColumn( columnVO );
   }

   @Override
   public int updateColumn( final ColumnVO columnVO ) throws KANException
   {
      return ( ( ColumnDao ) getDao() ).updateColumn( columnVO );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public int deleteColumn( final ColumnVO columnVO ) throws KANException
   {
      try
      {
         // 关联到的对象（SearchDetailVO，ListDetailVO，ReportDetailVO）
         if ( columnVO != null && columnVO.getColumnId() != null && !columnVO.getColumnId().trim().equals( "" ) )
         {
            // 开启事务
            startTransaction();

            // 标记删除SearchDetailVO对象
            final SearchDetailVO searchDetailVO = new SearchDetailVO();
            searchDetailVO.setAccountId( columnVO.getAccountId() );
            searchDetailVO.setColumnId( columnVO.getColumnId() );

            for ( Object objectSearchDetailVO : this.searchDetailDao.getSearchDetailVOsByCondition( searchDetailVO ) )
            {
               ( ( SearchDetailVO ) objectSearchDetailVO ).setDeleted( SearchDetailVO.FALSE );
               ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyBy( columnVO.getModifyBy() );
               ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyDate( columnVO.getModifyDate() );
               this.searchDetailDao.updateSearchDetail( ( ( SearchDetailVO ) objectSearchDetailVO ) );
            }

            // 标记删除ListDetailVO对象
            final ListDetailVO listDetailVO = new ListDetailVO();
            listDetailVO.setAccountId( columnVO.getAccountId() );
            listDetailVO.setColumnId( columnVO.getColumnId() );

            for ( Object objectListDetailVO : this.listDetailDao.getListDetailVOsByCondition( listDetailVO ) )
            {
               ( ( ListDetailVO ) objectListDetailVO ).setDeleted( ListDetailVO.FALSE );
               ( ( ListDetailVO ) objectListDetailVO ).setModifyBy( columnVO.getModifyBy() );
               ( ( ListDetailVO ) objectListDetailVO ).setModifyDate( columnVO.getModifyDate() );
               this.listDetailDao.updateListDetail( ( ( ListDetailVO ) objectListDetailVO ) );
            }

            // 标记删除ReportDetailVO对象        
            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setAccountId( columnVO.getAccountId() );
            reportDetailVO.setColumnId( columnVO.getColumnId() );

            for ( Object objectReportDetailVO : this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO ) )
            {
               ( ( ReportDetailVO ) objectReportDetailVO ).setDeleted( ReportDetailVO.FALSE );
               ( ( ReportDetailVO ) objectReportDetailVO ).setModifyBy( columnVO.getModifyBy() );
               ( ( ReportDetailVO ) objectReportDetailVO ).setModifyDate( columnVO.getModifyDate() );
               this.reportDetailDao.updateReportDetail( ( ( ReportDetailVO ) objectReportDetailVO ) );
            }

            // 最后标记删除ColumnVO对象
            columnVO.setDeleted( ColumnVO.FALSE );
            ( ( ColumnDao ) getDao() ).updateColumn( columnVO );

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

}
