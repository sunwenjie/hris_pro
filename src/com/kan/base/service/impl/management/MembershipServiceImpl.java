package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.MembershipDao;
import com.kan.base.domain.management.MembershipVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPMembershipService;
import com.kan.base.service.inf.management.MembershipService;
import com.kan.base.util.KANException;

public class MembershipServiceImpl extends ContextService implements MembershipService,CPMembershipService
{

   @Override
   public PagedListHolder getMembershipVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final MembershipDao membershipDao = ( MembershipDao ) getDao();
      pagedListHolder.setHolderSize( membershipDao.countMembershipVOsByCondition( ( MembershipVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( membershipDao.getMembershipVOsByCondition( ( MembershipVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( membershipDao.getMembershipVOsByCondition( ( MembershipVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public MembershipVO getMembershipVOByMembershipId( final String membershipId ) throws KANException
   {
      return ( ( MembershipDao ) getDao() ).getMembershipVOByMembershipId( membershipId );
   }

   @Override
   public int insertMembership( final MembershipVO membershipVO ) throws KANException
   {
      return ( ( MembershipDao ) getDao() ).insertMembership( membershipVO );
   }

   @Override
   public int updateMembership( final MembershipVO membershipVO ) throws KANException
   {
      return ( ( MembershipDao ) getDao() ).updateMembership( membershipVO );
   }

   @Override
   public int deleteMembership( final MembershipVO membershipVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final MembershipVO modifyObject = ( ( MembershipDao ) getDao() ).getMembershipVOByMembershipId( membershipVO.getMembershipId() );
      modifyObject.setDeleted( MembershipVO.FALSE );
      return ( ( MembershipDao ) getDao() ).updateMembership( modifyObject );
   }

   @Override
   public List< Object > getMembershipVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( MembershipDao ) getDao() ).getMembershipVOsByAccountId( accountId );
   }

}
