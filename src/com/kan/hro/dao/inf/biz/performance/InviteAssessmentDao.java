package com.kan.hro.dao.inf.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.InviteAssessmentVO;

public interface InviteAssessmentDao
{
   public abstract int countInviteAssessmentVOsByCondition( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract List< Object > getInviteAssessmentVOsByCondition( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract List< Object > getInviteAssessmentVOsByCondition( final InviteAssessmentVO inviteAssessmentVO, final RowBounds rowBounds ) throws KANException;

   public abstract InviteAssessmentVO getInviteAssessmentVOByInviteId( final String id ) throws KANException;

   public abstract int insertInviteAssessment( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract int updateInviteAssessment( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract int deleteInviteAssessment( final InviteAssessmentVO inviteAssessmentVO ) throws KANException;

   public abstract List< Object > getInviteAssessmentVOsByMapParameter( final Map< String, Object > mapParameter ) throws KANException;

   public abstract InviteAssessmentVO getInviteAssessmentVOByRandomKey( final String randomKey ) throws KANException;
}
