package com.kan.hro.dao.inf.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;

public interface SelfAssessmentDao
{
   public abstract int countSelfAssessmentVOsByCondition( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract List< Object > getSelfAssessmentVOsByCondition( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract List< Object > getSelfAssessmentVOsByCondition( final SelfAssessmentVO selfAssessmentVO, RowBounds rowBounds ) throws KANException;

   public abstract SelfAssessmentVO getSelfAssessmentVOByAssessmentId( final String assessmentId ) throws KANException;

   public abstract int insertSelfAssessment( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract int updateSelfAssessment( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract int deleteSelfAssessment( final String assessmentId ) throws KANException;

   public abstract List< Object > getSelfAssessmentVOsByMapParameter( final Map< String, Object > parameters ) throws KANException;

}
