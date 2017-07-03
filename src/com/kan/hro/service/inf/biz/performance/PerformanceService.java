package com.kan.hro.service.inf.biz.performance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.PerformanceVO;

public interface PerformanceService
{
   public abstract PagedListHolder getPerformanceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PerformanceVO getPerformanceVOByPerformanceId( final String performanceId ) throws KANException;

   public abstract int countPerformanceVOsByCondition( final PerformanceVO performanceVO ) throws KANException;

   public abstract int insertPerformance( final PerformanceVO performanceVO ) throws KANException;

   public abstract int updatePerformance( final PerformanceVO performanceVO ) throws KANException;

   public abstract int updatePerformance( final PerformanceVO performanceDB, final PerformanceVO performanceVO ) throws KANException;

   public abstract int deletePerformance( final String performanceId ) throws KANException;

   public abstract int syncPerformance( final List< PerformanceVO > performanceVOs ) throws KANException;

   public abstract XSSFWorkbook generatePerformanceReport( final PagedListHolder pagedListHolder ) throws KANException;

   public final Map< String, String > FORMULA_MAP = new HashMap< String, String >();

   /**
    * 确认调整
    * @param performancevo 查询参数
    * @return 被调整成功的人数
    */
   public abstract int confirmPerformanceInfo( PerformanceVO performanceVO, HttpServletRequest request ) throws KANException;

   public abstract boolean calculateNextYearSalaryDetails( PerformanceVO performanceVO ) throws KANException;

   public abstract int confirmFinalRating( final List< Object > performanceVOs, final String userId ) throws KANException;

   public abstract List< Object > getPerformanceVOsByCondition( final PerformanceVO performanceVO ) throws KANException;

   public abstract int sendAdjustmentSalaryNoticeLetter( final List< Object > performanceVOs, final String userId ) throws KANException;

}
