package com.kan.hro.service.impl.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.InviteAssessmentDao;
import com.kan.hro.domain.biz.performance.InviteAssessmentVO;
import com.kan.hro.service.inf.biz.performance.InviteAssessmentService;

public class InviteAssessmentServiceImpl extends ContextService implements InviteAssessmentService
{

   @Override
   public PagedListHolder getInviteAssessmentVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final InviteAssessmentDao inviteAssessmentDao = ( InviteAssessmentDao ) getDao();
      final InviteAssessmentVO object = ( InviteAssessmentVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( inviteAssessmentDao.countInviteAssessmentVOsByCondition( object ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( inviteAssessmentDao.getInviteAssessmentVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( inviteAssessmentDao.getInviteAssessmentVOsByCondition( object ) );
      }

      return pagedListHolder;
   }

   @Override
   public InviteAssessmentVO getInviteAssessmentVOByInviteId( String id ) throws KANException
   {
      return ( ( InviteAssessmentDao ) getDao() ).getInviteAssessmentVOByInviteId( id );
   }

   @Override
   public int insertInviteAssessment( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return ( ( InviteAssessmentDao ) getDao() ).insertInviteAssessment( inviteAssessmentVO );
   }

   @Override
   public int updateInviteAssessment( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      return ( ( InviteAssessmentDao ) getDao() ).updateInviteAssessment( inviteAssessmentVO );
   }

   @Override
   public int deleteInviteAssessment( InviteAssessmentVO inviteAssessmentVO ) throws KANException
   {
      inviteAssessmentVO.setStatus( InviteAssessmentVO.FALSE );
      return updateInviteAssessment( inviteAssessmentVO );
   }

   @Override
   public List< Object > getInviteAssessmentVOsByMapParameter( Map< String, Object > mapParameter ) throws KANException
   {
      return ( ( InviteAssessmentDao ) getDao() ).getInviteAssessmentVOsByMapParameter( mapParameter );
   }

   @Override
   public InviteAssessmentVO getInviteAssessmentVOByRandomKey( String randomKey ) throws KANException
   {
      return ( ( InviteAssessmentDao ) getDao() ).getInviteAssessmentVOByRandomKey( randomKey );
   }

}
