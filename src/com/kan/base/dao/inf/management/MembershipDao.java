package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.MembershipVO;
import com.kan.base.util.KANException;

public interface MembershipDao
{
   public abstract int countMembershipVOsByCondition( final MembershipVO membershipVO ) throws KANException;

   public abstract List< Object > getMembershipVOsByCondition( final MembershipVO membershipVO ) throws KANException;

   public abstract List< Object > getMembershipVOsByCondition( final MembershipVO membershipVO, final RowBounds rowBounds ) throws KANException;

   public abstract MembershipVO getMembershipVOByMembershipId( final String membershipId ) throws KANException;

   public abstract int insertMembership( final MembershipVO membershipVO ) throws KANException;

   public abstract int updateMembership( final MembershipVO membershipVO ) throws KANException;

   public abstract int deleteMembership( final MembershipVO membershipVO ) throws KANException;

   public abstract List< Object > getMembershipVOsByAccountId( final String accountId ) throws KANException;
}
