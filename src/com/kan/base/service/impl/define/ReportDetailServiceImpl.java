package com.kan.base.service.impl.define;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.ReportHeaderDao;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ReportDetailService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ReportDetailServiceImpl extends ContextService implements ReportDetailService
{

   // ע�뱨������DAO
   private ReportHeaderDao reportHeaderDao;

   public ReportHeaderDao getReportHeaderDao()
   {
      return reportHeaderDao;
   }

   public void setReportHeaderDao( ReportHeaderDao reportHeaderDao )
   {
      this.reportHeaderDao = reportHeaderDao;
   }

   @Override
   public PagedListHolder getReportDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ReportDetailDao reportDetailDao = ( ReportDetailDao ) getDao();
      pagedListHolder.setHolderSize( reportDetailDao.countReportDetailVOsByCondition( ( ReportDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( reportDetailDao.getReportDetailVOsByCondition( ( ReportDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( reportDetailDao.getReportDetailVOsByCondition( ( ReportDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ReportDetailVO getReportDetailVOByReportDetailId( final String reportDetailId ) throws KANException
   {
      return ( ( ReportDetailDao ) getDao() ).getReportDetailVOByReportDetailId( reportDetailId );
   }

   @Override
   public int insertReportDetail( final ReportDetailVO reportDetailVO ) throws KANException
   {
//      try
//      {
//         // ��������
//         startTransaction();
//         // ��ӱ����ֶ�
//         ( ( ReportDetailDao ) getDao() ).insertReportDetail( reportDetailVO );
//         // ���ֶ�ʹ�þۺϺ���
//         if ( reportDetailVO.getStatisticsColumns() != null && !"0".equals( reportDetailVO.getStatisticsColumns() ) )
//         {
//            // ��ñ�������
//            final ReportHeaderVO reportHeaderVO = this.reportHeaderDao.getReportHeaderVOByReportHeaderId( reportDetailVO.getReportHeaderId() );
//            reportHeaderVO.setModifyBy( reportDetailVO.getModifyBy() );
//            reportHeaderVO.setModifyDate( new Date() );
//            // �޸�����ͳ���ֶ�
//            updateReportHeader( reportHeaderVO, reportDetailVO.getColumnId(), reportDetailVO.getDecodeStatisticsColumns() );
//         }
//         // �ύ����
//         commitTransaction();
//      }
//      catch ( final Exception e )
//      {
//         // �ع�����
//         rollbackTransaction();
//         throw new KANException( e );
//      }
      return 0;
   }

   @Override
   public int updateReportDetail( final ReportDetailVO reportDetailVO ) throws KANException
   {
      return ( ( ReportDetailDao ) getDao() ).updateReportDetail( reportDetailVO );
   }

   @Override
   public int deleteReportDetail( final ReportDetailVO reportDetailVO ) throws KANException
   {
      // ���ɾ�������ֶ�
      reportDetailVO.setDeleted( ReportDetailVO.FALSE );
      return ( ( ReportDetailDao ) getDao() ).updateReportDetail( reportDetailVO );
   }

   public void updateReportHeader( final ReportHeaderVO reportHeaderVO, final String columnId, final String statisticsColumns ) throws KANException
   {
      if ( reportHeaderVO.getStatisticsColumns() != null && !"".equals( reportHeaderVO.getStatisticsColumns() ) )
      {
         JSONObject json = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );
         json.accumulate( columnId, statisticsColumns );
         reportHeaderVO.setStatisticsColumns( json.toString() );
      }
      else
      {
         reportHeaderVO.setStatisticsColumns( "{" + columnId + ":\"" + statisticsColumns + "\"}" );
      }
      if ( reportHeaderVO.getGroupColumns() != null && !"".equals( reportHeaderVO.getGroupColumns() ) )
      {
         final String temp = reportHeaderVO.getGroupColumns().replace( "{", "" ).replace( "}", "" ) + ":" + columnId;
         reportHeaderVO.setGroupColumns( KANUtil.toJasonArray( temp.split( ":" ) ) );
      }
      else
      {
         reportHeaderVO.setGroupColumns( "{" + columnId + "}" );
      }
      this.reportHeaderDao.updateReportHeader( reportHeaderVO );
   }

   @Override
   public List< Object > getReportDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      // ׼����������
      final ReportDetailVO reportDetailVO =  new ReportDetailVO();
      reportDetailVO.setReportHeaderId( reportHeaderId );
      reportDetailVO.setStatus( ReportDetailVO.TRUE );
      reportDetailVO.setDeleted(ReportDetailVO.FALSE);
      reportDetailVO.setSortColumn( "columnIndex" );
      return ( ( ReportDetailDao ) getDao() ).getReportDetailVOsByCondition( reportDetailVO );
   }


}
