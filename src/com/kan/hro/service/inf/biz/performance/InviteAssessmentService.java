package com.kan.hro.service.inf.biz.performance;

import java.util.List;
import java.util.Map;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.InviteAssessmentVO;

public interface InviteAssessmentService
{
   public abstract PagedListHolder getInviteAssessmentVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract InviteAssessmentVO getInviteAssessmentVOByInviteId( final String id ) throws KANException;

   public abstract int insertInviteAssessment( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract int updateInviteAssessment( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract int deleteInviteAssessment( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract List< Object > getInviteAssessmentVOsByMapParameter( final Map< String, Object > mapParameter ) throws KANException;
   
   public abstract InviteAssessmentVO getInviteAssessmentVOByRandomKey( final String randomKey ) throws KANException;

}
