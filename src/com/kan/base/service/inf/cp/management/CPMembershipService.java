package com.kan.base.service.inf.cp.management;

import java.util.List;

import com.kan.base.domain.management.MembershipVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CPMembershipService
{
   public abstract PagedListHolder getMembershipVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract MembershipVO getMembershipVOByMembershipId( final String membershipId ) throws KANException;

   public abstract int insertMembership( final MembershipVO membershipVO ) throws KANException;

   public abstract int updateMembership( final MembershipVO membershipVO ) throws KANException;

   public abstract int deleteMembership( final MembershipVO membershipVO ) throws KANException;
   
   public abstract List< Object > getMembershipVOsByAccountId( final String accountId ) throws KANException;
}
