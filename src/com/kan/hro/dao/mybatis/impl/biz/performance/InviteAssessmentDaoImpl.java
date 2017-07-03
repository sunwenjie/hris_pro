package com.kan.hro.dao.mybatis.impl.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.InviteAssessmentDao;
import com.kan.hro.domain.biz.performance.InviteAssessmentVO;

public class InviteAssessmentDaoImpl extends Context implements InviteAssessmentDao
{

   @Override
   public int countInviteAssessmentVOsByCondition( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return ( Integer ) select( "countInviteAssessmentVOsByCondition", inviteAssessmentVO );
   }

   @Override
   public List< Object > getInviteAssessmentVOsByCondition( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return selectList( "getInviteAssessmentVOsByCondition", inviteAssessmentVO );
   }

   @Override
   public List< Object > getInviteAssessmentVOsByCondition( InviteAssessmentVO inviteAssessmentVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getInviteAssessmentVOsByCondition", inviteAssessmentVO, rowBounds );
   }

   @Override
   public InviteAssessmentVO getInviteAssessmentVOByInviteId( String id ) throws KANException
   {
      return ( InviteAssessmentVO ) select( "getInviteAssessmentVOByInviteId", id );
   }

   @Override
   public int insertInviteAssessment( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return insert( "insertInviteAssessment", inviteAssessmentVO );
   }

   @Override
   public int updateInviteAssessment( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return update( "updateInviteAssessment", inviteAssessmentVO );
   }

   @Override
   public int deleteInviteAssessment( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return delete( "deleteInviteAssessment", inviteAssessmentVO );
   }

   @Override
   public List< Object > getInviteAssessmentVOsByMapParameter( Map< String, Object > mapParameter )
   {
      return selectList( "getInviteAssessmentVOsByMapParameter", mapParameter );
   }

   @Override
   public InviteAssessmentVO getInviteAssessmentVOByRandomKey( String randomKey ) throws KANException
   {
      return ( InviteAssessmentVO ) select( "getInviteAssessmentVOByRandomKey", randomKey );
   }

}
