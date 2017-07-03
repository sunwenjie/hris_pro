package com.kan.hro.dao.mybatis.impl.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.PerformanceDao;
import com.kan.hro.domain.biz.performance.PerformanceVO;

public class PerformanceDaoImpl extends Context implements PerformanceDao
{

   @Override
   public int countPerformanceVOsByCondition( PerformanceVO performanceVO ) throws KANException
   {
      return ( Integer ) select( "countPerformanceVOsByCondition", performanceVO );
   }

   @Override
   public List< Object > getPerformanceVOsByCondition( PerformanceVO performanceVO ) throws KANException
   {
      return selectList( "getPerformanceVOsByCondition", performanceVO );
   }

   @Override
   public List< Object > getPerformanceVOsByCondition( PerformanceVO performanceVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPerformanceVOsByCondition", performanceVO, rowBounds );
   }

   @Override
   public PerformanceVO getPerformanceVOByPerformanceId( String performanceId ) throws KANException
   {
      return ( PerformanceVO ) select( "getPerformanceVOByPerformanceId", performanceId );
   }

   @Override
   public int insertPerformance( PerformanceVO performanceVO ) throws KANException
   {
      return insert( "insertPerformance", performanceVO );
   }

   @Override
   public int updatePerformance( PerformanceVO performanceVO ) throws KANException
   {
      return update( "updatePerformance", performanceVO );
   }

   @Override
   public int deletePerformance( String performanceId ) throws KANException
   {
      return delete( "deletePerformance", performanceId );
   }

   @Override
   public List< Object > getPerformanceVOsByMapParameter( Map< String, Object > parameters )throws KANException
   {
      return selectList( "getPerformanceVOsByMapParameter", parameters );
   }

}
