package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ReportSearchDetailDao;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.util.KANException;

public class ReportSearchDetailDaoImpl extends Context implements ReportSearchDetailDao
{

   @Override
   public int countReportSearchDetailVOsByCondition( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      return ( Integer ) select( "countReportSearchDetailVOsByCondition", reportSearchDetailVO );
   }

   @Override
   public List< Object > getReportSearchDetailVOsByCondition( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      return selectList( "getReportSearchDetailVOsByCondition", reportSearchDetailVO );
   }

   @Override
   public List< Object > getReportSearchDetailVOsByCondition( final ReportSearchDetailVO reportSearchDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getReportSearchDetailVOsByCondition", reportSearchDetailVO, rowBounds );
   }

   @Override
   public ReportSearchDetailVO getReportSearchDetailVOByReportSearchDetailId( final String reportSearchDetailId ) throws KANException
   {
      return ( ReportSearchDetailVO ) select( "getReportSearchDetailVOByReportSearchDetailId", reportSearchDetailId );
   }

   @Override
   public int insertReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      return insert( "insertReportSearchDetail", reportSearchDetailVO );
   }

   @Override
   public int updateReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException
   {
      return update( "updateReportSearchDetail", reportSearchDetailVO );
   }

   @Override
   public int deleteReportSearchDetail( final String reportSearchDetailId ) throws KANException
   {
      return delete( "deleteReportSearchDetail", reportSearchDetailId );
   }

   @Override
   public List< Object > getReportSearchDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      return selectList( "getReportSearchDetailVOsByReportHeaderId", reportHeaderId );
   }

}
