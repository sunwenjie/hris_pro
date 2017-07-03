package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ReportSearchDetailDao;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ReportSearchDetailService;
import com.kan.base.util.KANException;

public class ReportSearchDetailServiceImpl extends ContextService implements ReportSearchDetailService
{

   @Override
   public PagedListHolder getReportSearchDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ReportSearchDetailDao reportSearchDetailDao = ( ReportSearchDetailDao ) getDao();
      pagedListHolder.setHolderSize( reportSearchDetailDao.countReportSearchDetailVOsByCondition( ( ReportSearchDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( reportSearchDetailDao.getReportSearchDetailVOsByCondition( ( ReportSearchDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( reportSearchDetailDao.getReportSearchDetailVOsByCondition( ( ReportSearchDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ReportSearchDetailVO getReportSearchDetailVOByReportSearchDetailId( final String reportSearchDetailId ) throws KANException
   {
      return ( ( ReportSearchDetailDao ) getDao() ).getReportSearchDetailVOByReportSearchDetailId( reportSearchDetailId );
   }

   @Override
   public int insertReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      return ( ( ReportSearchDetailDao ) getDao() ).insertReportSearchDetail( reportSearchDetailVO );
   }

   @Override
   public int updateReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      return ( ( ReportSearchDetailDao ) getDao() ).updateReportSearchDetail( reportSearchDetailVO );
   }

   @Override
   public int deleteReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      // 标记删除
      reportSearchDetailVO.setDeleted( ReportSearchDetailVO.FALSE );
      return ( ( ReportSearchDetailDao ) getDao() ).updateReportSearchDetail( reportSearchDetailVO );
   }

   @Override
   public List< Object > getReportSearchDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      // 准备搜索条件
      final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
      reportSearchDetailVO.setReportHeaderId( reportHeaderId );
      reportSearchDetailVO.setStatus( ReportSearchDetailVO.TRUE );
      reportSearchDetailVO.setSortColumn( "columnIndex" );
      return ( ( ReportSearchDetailDao ) getDao() ).getReportSearchDetailVOsByCondition( reportSearchDetailVO );
   }

}
