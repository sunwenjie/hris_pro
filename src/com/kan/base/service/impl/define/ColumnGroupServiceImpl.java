package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ColumnDao;
import com.kan.base.dao.inf.define.ColumnGroupDao;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnGroupService;
import com.kan.base.util.KANException;

public class ColumnGroupServiceImpl extends ContextService implements ColumnGroupService
{

   private ColumnDao columnDao;

   private SearchDetailDao searchDetailDao;

   private ListDetailDao listDetailDao;

   private ReportDetailDao reportDetailDao;

   public ColumnDao getColumnDao()
   {
      return columnDao;
   }

   public void setColumnDao( ColumnDao columnDao )
   {
      this.columnDao = columnDao;
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
   public PagedListHolder getColumnGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ColumnGroupDao columnGroupDao = ( ColumnGroupDao ) getDao();
      pagedListHolder.setHolderSize( columnGroupDao.countColumnGroupVOsByCondition( ( ColumnGroupVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( columnGroupDao.getColumnGroupVOsByCondition( ( ColumnGroupVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( columnGroupDao.getColumnGroupVOsByCondition( ( ColumnGroupVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ColumnGroupVO getColumnGroupVOByGroupId( final String groupId ) throws KANException
   {
      return ( ( ColumnGroupDao ) getDao() ).getColumnGroupVOByGroupId( groupId );
   }

   @Override
   public int insertColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      return ( ( ColumnGroupDao ) getDao() ).insertColumnGroup( columnGroupVO );
   }

   @Override
   public int updateColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      return ( ( ColumnGroupDao ) getDao() ).updateColumnGroup( columnGroupVO );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public int deleteColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException
   {
      // 标记删除ColumnGroupVO同时，还要标记删除与之关联到的其他对象（ColumnVO，SearchDetailVO，ListDetailVO，ReportDetailVO）
      try
      {
         // 开启事务
         startTransaction();

         // 关联到ColumnVO对象
         final ColumnVO columnVO = new ColumnVO();
         columnVO.setGroupId( columnGroupVO.getGroupId() );
         columnVO.setAccountId( columnGroupVO.getAccountId() );

         for ( Object objectColumnVO : this.columnDao.getColumnVOsByCondition( columnVO ) )
         {
            // 标记删除 SearchDetailVO
            final SearchDetailVO searchDetailVO = new SearchDetailVO();
            searchDetailVO.setColumnId( ( ( ColumnVO ) ( objectColumnVO ) ).getColumnId() );
            for ( Object objectSearchDetailVO : this.searchDetailDao.getSearchDetailVOsByCondition( searchDetailVO ) )
            {
               ( ( SearchDetailVO ) objectSearchDetailVO ).setDeleted( SearchDetailVO.FALSE );
               ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyBy( columnGroupVO.getModifyBy() );
               ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyDate( columnGroupVO.getModifyDate() );
               this.searchDetailDao.updateSearchDetail( ( ( SearchDetailVO ) objectSearchDetailVO ) );
            }

            // 标记删除 ListDetailVO
            final ListDetailVO listDetailVO = new ListDetailVO();
            listDetailVO.setColumnId( ( ( ColumnVO ) ( objectColumnVO ) ).getColumnId() );
            for ( Object objectListDetailVO : this.listDetailDao.getListDetailVOsByCondition( listDetailVO ) )
            {
               ( ( ListDetailVO ) objectListDetailVO ).setDeleted( ListDetailVO.FALSE );
               ( ( ListDetailVO ) objectListDetailVO ).setModifyBy( columnGroupVO.getModifyBy() );
               ( ( ListDetailVO ) objectListDetailVO ).setModifyDate( columnGroupVO.getModifyDate() );
               this.listDetailDao.updateListDetail( ( ( ListDetailVO ) objectListDetailVO ) );
            }

            // 标记删除 ReportDetailVO
            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setColumnId( ( ( ColumnVO ) ( objectColumnVO ) ).getColumnId() );
            for ( Object objectReportDetailVO : this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO ) )
            {
               ( ( ReportDetailVO ) objectReportDetailVO ).setDeleted( ReportDetailVO.FALSE );
               ( ( ReportDetailVO ) objectReportDetailVO ).setModifyBy( columnGroupVO.getModifyBy() );
               ( ( ReportDetailVO ) objectReportDetailVO ).setModifyDate( columnGroupVO.getModifyDate() );
               this.reportDetailDao.updateReportDetail( ( ( ReportDetailVO ) objectReportDetailVO ) );
            }

            // 标记删除 ColumnVO
            ( ( ColumnVO ) objectColumnVO ).setDeleted( ColumnVO.FALSE );
            ( ( ColumnVO ) objectColumnVO ).setModifyBy( columnGroupVO.getModifyBy() );
            ( ( ColumnVO ) objectColumnVO ).setModifyDate( columnGroupVO.getModifyDate() );
            this.columnDao.updateColumn( ( ( ColumnVO ) objectColumnVO ) );
         }

         // 最后标记删除ColumnGroupVO
         columnGroupVO.setDeleted( ColumnGroupVO.FALSE );
         ( ( ColumnGroupDao ) getDao() ).updateColumnGroup( columnGroupVO );

         // 提交事务
         commitTransaction();
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
   public List< Object > getColumnGroupVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( ColumnGroupDao ) getDao() ).getColumnGroupVOsByAccountId( accountId );
   }

}
