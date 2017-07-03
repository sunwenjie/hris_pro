package com.kan.hro.dao.inf.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.PerformanceVO;

public interface PerformanceDao
{
   public abstract int countPerformanceVOsByCondition( final PerformanceVO performanceVO ) throws KANException;

   public abstract List< Object > getPerformanceVOsByCondition( final PerformanceVO performanceVO ) throws KANException;

   public abstract List< Object > getPerformanceVOsByCondition( final PerformanceVO performanceVO, RowBounds rowBounds ) throws KANException;

   public abstract PerformanceVO getPerformanceVOByPerformanceId( final String performanceId ) throws KANException;

   public abstract int insertPerformance( final PerformanceVO performanceVO ) throws KANException;

   public abstract int updatePerformance( final PerformanceVO performanceVO ) throws KANException;

   public abstract int deletePerformance( final String performanceId ) throws KANException;

   public abstract List< Object > getPerformanceVOsByMapParameter( final Map< String, Object > parameters ) throws KANException;

}
