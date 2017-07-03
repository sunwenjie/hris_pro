package com.kan.hro.service.impl.biz.performance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.PerformanceDao;
import com.kan.hro.dao.inf.biz.performance.SelfAssessmentDao;
import com.kan.hro.domain.biz.performance.PerformanceVO;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;
import com.kan.hro.service.inf.biz.performance.PerformanceService;
import com.kan.hro.service.inf.biz.performance.SelfAssessmentService;

public class SelfAssessmentServiceImpl extends ContextService implements SelfAssessmentService
{

   private PerformanceDao performanceDao;

   private PerformanceService performanceService;

   public PerformanceService getPerformanceService()
   {
      return performanceService;
   }

   public void setPerformanceService( PerformanceService performanceService )
   {
      this.performanceService = performanceService;
   }

   public PerformanceDao getPerformanceDao()
   {
      return performanceDao;
   }

   public void setPerformanceDao( PerformanceDao performanceDao )
   {
      this.performanceDao = performanceDao;
   }

   @Override
   public PagedListHolder getSelfAssessmentVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SelfAssessmentDao selfAssessmentDao = ( SelfAssessmentDao ) getDao();
      final SelfAssessmentVO object = ( SelfAssessmentVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( selfAssessmentDao.countSelfAssessmentVOsByCondition( object ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( selfAssessmentDao.getSelfAssessmentVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( selfAssessmentDao.getSelfAssessmentVOsByCondition( object ) );
      }

      return pagedListHolder;
   }

   @Override
   public SelfAssessmentVO getSelfAssessmentVOByAssessmentId( String assessmentId ) throws KANException
   {
      return ( ( SelfAssessmentDao ) getDao() ).getSelfAssessmentVOByAssessmentId( assessmentId );
   }

   @Override
   public int insertSelfAssessment( SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      return ( ( SelfAssessmentDao ) getDao() ).insertSelfAssessment( selfAssessmentVO );
   }

   @Override
   public int updateSelfAssessment( SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      System.out.println( selfAssessmentVO.getAccomplishments_bu() );
      return ( ( SelfAssessmentDao ) getDao() ).updateSelfAssessment( selfAssessmentVO );
   }

   @Override
   public int deleteSelfAssessment( String assessmentId ) throws KANException
   {
      return ( ( SelfAssessmentDao ) getDao() ).deleteSelfAssessment( assessmentId );
   }

   @Override
   public List< Object > getSelfAssessmentVOsByMapParameter( Map< String, Object > parameters ) throws KANException
   {
      return ( ( SelfAssessmentDao ) getDao() ).getSelfAssessmentVOsByMapParameter( parameters );
   }

   private int syncSelfAssessmentVO_nt( final SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      final Map< String, Object > parameters = new HashMap< String, Object >();
      parameters.put( "year", selfAssessmentVO.getYear() );
      parameters.put( "employeeId", selfAssessmentVO.getEmployeeId() );
      try
      {
         final List< Object > performanceVOs = performanceDao.getPerformanceVOsByMapParameter( parameters );
         if ( performanceVOs != null && performanceVOs.size() > 0 )
         {
            final PerformanceVO performanceVO = ( PerformanceVO ) performanceVOs.get( 0 );
            performanceVO.setYearPerformanceRating( selfAssessmentVO.getRating_bu() );
            performanceVO.setYearPerformancePromotion( "1".equals( selfAssessmentVO.getIsPromotion_pm() ) ? "P" : "N" );
            performanceVO.setModifyBy( selfAssessmentVO.getModifyBy() );
            performanceVO.setModifyDate( new Date() );
            performanceService.calculateNextYearSalaryDetails( performanceVO );
            performanceDao.updatePerformance( performanceVO );
            // 评估同步完成
            selfAssessmentVO.setStatus( "4" );
            selfAssessmentVO.setModifyBy( selfAssessmentVO.getModifyBy() );
            selfAssessmentVO.setModifyDate( new Date() );
            selfAssessmentVO.setRemark2( performanceVO.getPerformanceId() );// 关联Performance
            ( ( SelfAssessmentDao ) getDao() ).updateSelfAssessment( selfAssessmentVO );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int syncSelfAssessmentVO( final SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      try
      {
         this.startTransaction();
         syncSelfAssessmentVO_nt( selfAssessmentVO );
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int syncSelfAssessmentVOs( final SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      int successRows = 0;
      try
      {
         final List< Object > sync_list = ( ( SelfAssessmentDao ) getDao() ).getSelfAssessmentVOsByCondition( selfAssessmentVO );
         this.startTransaction();

         for ( Object sync_object : sync_list )
         {
            final SelfAssessmentVO obj = ( SelfAssessmentVO ) sync_object;
            final Map< String, Object > parameters = new HashMap< String, Object >();
            parameters.put( "year", obj.getYear() );
            parameters.put( "employeeId", obj.getEmployeeId() );

            final List< Object > performanceVOs = performanceDao.getPerformanceVOsByMapParameter( parameters );
            if ( performanceVOs != null && performanceVOs.size() > 0 )
            {
               final PerformanceVO performanceVO = ( PerformanceVO ) performanceVOs.get( 0 );
               performanceVO.setYearPerformanceRating( obj.getRating_bu() );
               performanceVO.setYearPerformancePromotion( "1".equals( obj.getIsPromotion_pm() ) ? "P" : null );
               performanceVO.setModifyBy( selfAssessmentVO.getModifyBy() );
               performanceVO.setModifyDate( new Date() );
               performanceService.calculateNextYearSalaryDetails( performanceVO );
               successRows += performanceDao.updatePerformance( performanceVO );
               // 评估同步完成
               obj.setStatus( "4" );
               obj.setModifyBy( selfAssessmentVO.getModifyBy() );
               obj.setModifyDate( new Date() );
               obj.setRemark2( performanceVO.getPerformanceId() );// 关联Performance
               ( ( SelfAssessmentDao ) getDao() ).updateSelfAssessment( obj );
            }
         }

         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return successRows;
   }

   @Override
   public int insertBatchSelfAssessment( List< SelfAssessmentVO > objects ) throws KANException
   {
      int rows = 0;
      try
      {
         if ( objects != null && objects.size() > 0 )
         {
            startTransaction();

            for ( SelfAssessmentVO o : objects )
            {
               rows += ( ( SelfAssessmentDao ) getDao() ).insertSelfAssessment( o );

               //syncSelfAssessmentVO_nt( o );
            }

            commitTransaction();
         }
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }
      return rows;
   }
}
