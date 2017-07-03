package com.kan.hro.dao.mybatis.impl.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.SelfAssessmentDao;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;

public class SelfAssessmentDaoImpl extends Context implements SelfAssessmentDao
{

   @Override
   public int countSelfAssessmentVOsByCondition( SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      return ( Integer ) select( "countSelfAssessmentVOsByCondition", selfAssessmentVO );
   }

   @Override
   public List< Object > getSelfAssessmentVOsByCondition( SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      return selectList( "getSelfAssessmentVOsByCondition", selfAssessmentVO );
   }

   @Override
   public List< Object > getSelfAssessmentVOsByCondition( SelfAssessmentVO selfAssessmentVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSelfAssessmentVOsByCondition", selfAssessmentVO, rowBounds );
   }

   @Override
   public SelfAssessmentVO getSelfAssessmentVOByAssessmentId( String assessmentId ) throws KANException
   {
      return ( SelfAssessmentVO ) select( "getSelfAssessmentVOByAssessmentId", assessmentId );
   }

   @Override
   public int insertSelfAssessment( SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      return insert( "insertSelfAssessment", selfAssessmentVO );
   }

   @Override
   public int updateSelfAssessment( SelfAssessmentVO selfAssessmentVO ) throws KANException
   {
      return update( "updateSelfAssessment", selfAssessmentVO );
   }

   @Override
   public int deleteSelfAssessment( String assessmentId ) throws KANException
   {
      return delete( "deleteSelfAssessment", assessmentId );
   }

   @Override
   public List< Object > getSelfAssessmentVOsByMapParameter( Map< String, Object > parameters ) throws KANException
   {
      return selectList( "getSelfAssessmentVOsByMapParameter", parameters );
   }

}
