package com.kan.hro.service.inf.biz.performance;

import java.util.List;
import java.util.Map;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.SelfAssessmentVO;

public interface SelfAssessmentService
{
   public abstract PagedListHolder getSelfAssessmentVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SelfAssessmentVO getSelfAssessmentVOByAssessmentId( final String assessmentId ) throws KANException;

   public abstract int insertSelfAssessment( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract int updateSelfAssessment( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract int deleteSelfAssessment( final String assessmentId ) throws KANException;

   public abstract List< Object > getSelfAssessmentVOsByMapParameter( final Map< String, Object > parameters ) throws KANException;

   public abstract int syncSelfAssessmentVOs( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract int syncSelfAssessmentVO( final SelfAssessmentVO selfAssessmentVO ) throws KANException;

   public abstract int insertBatchSelfAssessment( final List< SelfAssessmentVO > objects ) throws KANException;
   
}
