package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.MembershipDao;
import com.kan.base.domain.management.MembershipVO;
import com.kan.base.util.KANException;

public class MembershipDaoImpl extends Context implements MembershipDao
{

   @Override
   public int countMembershipVOsByCondition( final MembershipVO membershipVO ) throws KANException
   {
      return ( Integer ) select( "countMembershipVOsByCondition", membershipVO );
   }

   @Override
   public List< Object > getMembershipVOsByCondition( final MembershipVO membershipVO ) throws KANException
   {
      return selectList( "getMembershipVOsByCondition", membershipVO );
   }

   @Override
   public List< Object > getMembershipVOsByCondition( final MembershipVO membershipVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMembershipVOsByCondition", membershipVO, rowBounds );
   }

   @Override
   public MembershipVO getMembershipVOByMembershipId( final String membershipId ) throws KANException
   {
      return ( MembershipVO ) select( "getMembershipVOByMembershipId", membershipId );
   }

   @Override
   public int insertMembership( final MembershipVO membershipVO ) throws KANException
   {
      return insert( "insertMembership", membershipVO );
   }

   @Override
   public int updateMembership( final MembershipVO membershipVO ) throws KANException
   {
      return update( "updateMembership", membershipVO );
   }

   @Override
   public int deleteMembership( final MembershipVO membershipVO ) throws KANException
   {
      return delete( "deleteMembership", membershipVO );
   }

   @Override
   public List< Object > getMembershipVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getMembershipVOsByAccountId", accountId );
   }

}
