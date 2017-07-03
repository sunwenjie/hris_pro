package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.util.KANException;

public class ReportDetailDaoImpl extends Context implements ReportDetailDao
{

   @Override
   public int countReportDetailVOsByCondition( final ReportDetailVO reportDetailVO ) throws KANException
   {
      return ( Integer ) select( "countReportDetailVOsByCondition", reportDetailVO );
   }

   @Override
   public List< Object > getReportDetailVOsByCondition( final ReportDetailVO reportDetailVO ) throws KANException
   {
      return selectList( "getReportDetailVOsByCondition", reportDetailVO );
   }

   @Override
   public List< Object > getReportDetailVOsByCondition( final ReportDetailVO reportDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getReportDetailVOsByCondition", reportDetailVO, rowBounds );
   }

   @Override
   public ReportDetailVO getReportDetailVOByReportDetailId( final String reportDetailId ) throws KANException
   {
      return ( ReportDetailVO ) select( "getReportDetailVOByReportDetailId", reportDetailId );
   }

   @Override
   public int insertReportDetail( final ReportDetailVO reportDetailVO ) throws KANException
   {
      return insert( "insertReportDetail", reportDetailVO );
   }

   @Override
   public int updateReportDetail( final ReportDetailVO reportDetailVO ) throws KANException
   {
      return update( "updateReportDetail", reportDetailVO );
   }

   @Override
   public int deleteReportDetail( final String reportHeaderId ) throws KANException
   {
      return delete( "deleteReportDetail", reportHeaderId );
   }

   @Override
   public List< Object > getReportDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      return selectList( "getReportDetailVOsByReportHeaderId", reportHeaderId );
   }

}
