package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ReportRelationDao;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.util.KANException;

public class ReportRelationDaoImpl extends Context implements  ReportRelationDao
{

	   @Override
	   public int insertReportRelation( final ReportRelationVO reportRelationVO ) throws KANException
	   {
	      return insert( "insertReportRelation", reportRelationVO );
	   }
//   @Override
//   public int countReportRelationVOsByCondition( final ReportRelationVO reportRelationVO ) throws KANException
//   {
//      return ( Integer ) select( "countReportRelationVOsByCondition", reportRelationVO );
//   }
//
//   @Override
//   public List< Object > getReportRelationVOsByCondition( final ReportRelationVO reportRelationVO ) throws KANException
//   {
//      return selectList( "getReportRelationVOsByCondition", reportRelationVO );
//   }
//
//   @Override
//   public List< Object > getReportRelationVOsByCondition( final ReportRelationVO reportRelationVO, final RowBounds rowBounds ) throws KANException
//   {
//      return selectList( "getReportRelationVOsByCondition", reportRelationVO, rowBounds );
//   }
//
//   @Override
//   public ReportRelationVO getReportRelationVOByReportRelationId( final String reportRelationId ) throws KANException
//   {
//      return ( ReportRelationVO ) select( "getReportRelationVOByReportRelationId", reportRelationId );
//   }

	@Override
	public List<Object> getReportRelationVOsByReportHeaderId(
			String reportHeaderId) throws KANException {
		return selectList( "getReportRelationVOsByReportHeaderId", reportHeaderId );
	}



   @Override
   public int updateReportRelation( final ReportRelationVO reportRelationVO ) throws KANException
   {
      return update( "updateReportRelation", reportRelationVO );
   }
//
//   @Override
//   public int deleteReportRelation( final String reportHeaderId ) throws KANException
//   {
//      return delete( "deleteReportRelation", reportHeaderId );
//   }
//
//   @Override
//   public List< Object > getReportRelationVOsByReportHeaderId( final String reportHeaderId ) throws KANException
//   {
//      return selectList( "getReportRelationVOsByReportHeaderId", reportHeaderId );
//   }

}
