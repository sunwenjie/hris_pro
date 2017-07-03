package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.domain.security.UserVO;
import com.kan.base.util.KANException;

public class UserDaoImpl extends Context implements UserDao
{

   @Override
   public int deleteUser( final UserVO userVO ) throws KANException
   {

      return delete( "deleteUser", userVO );
   }

   @Override
   public UserVO getUserVOByUserId( final String userId ) throws KANException
   {
      return ( UserVO ) select( "getUserVOByUserId", userId );
   }

   @Override
   public UserVO login( final UserVO userVO ) throws KANException
   {
      return ( UserVO ) select( "login", userVO );
   }

   @Override
   public List< Object > getUserVOsByCondition( final UserVO userVO ) throws KANException
   {
      return selectList( "getUserVOsByCondition", userVO );
   }

   @Override
   public List< Object > getUserVOsByCondition( final UserVO userVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getUserVOsByCondition", userVO, rowBounds );
   }

   @Override
   public int insertUser( final UserVO userVO ) throws KANException
   {
      return insert( "insertUser", userVO );
   }

   @Override
   public int updateUser( final UserVO userVO ) throws KANException
   {
      return update( "updateUser", userVO );
   }

   @Override
   public int countUserVOsByCondition( final UserVO userVO ) throws KANException
   {
      return ( Integer ) select( "countUserVOsByCondition", userVO );
   }

   @Override
   public UserVO getUserVOByMobile( final String mobile ) throws KANException
   {
      return ( UserVO ) select( "getUserVOByMobile", mobile );
   }

   @Override
   public UserVO getUserVOByEmail( final String email ) throws KANException
   {
      return ( UserVO ) select( "getUserVOByEmail", email );
   }

   @Override
   public int backupUser( final UserVO userVO ) throws KANException
   {
      return insert( "backupUser", userVO );
   }

   @Override
   public List< Object > getHistoryUserVOsByUserId( final String userId ) throws KANException
   {
      return selectList( "getHistoryUserVOsByUserId", userId );
   }

   @Override
   public UserVO getHistoryUserVOByHistoryId( String historyId ) throws KANException
   {
      return ( UserVO ) select( "getHistoryUserVOByHistoryId", historyId );
   }

   @Override
   public UserVO getUserVOByStaffId( String staffId ) throws KANException
   {
      return ( UserVO ) select( "getUserVOByStaffId", staffId );
   }

   @Override
   public List< Object > getClientVOs() throws KANException
   {
      return selectList( "getClientVOs", "" );
   }

   @Override
   public UserVO login_inHouse( UserVO userVO ) throws KANException
   {
      return ( UserVO ) select( "login_inHouse", userVO );
   }

   @Override
   public List< Object > getUserVOsByCorpId( String corpId ) throws KANException
   {
      return selectList( "getUserVOsByCorpId", corpId );
   }

   @Override
   public int countUsersByUserName( String username ) throws KANException
   {
      return ( Integer ) select( "countUsersByUserName", username );
   }

   @Override
   public UserVO getUserVOByRemark1( String remark1 ) throws KANException
   {
      UserVO userVO = null;
      List< Object > objs = selectList( "getUserVOByRemark1", remark1 );
      if ( objs != null && objs.size() == 1 )
      {
         userVO = ( UserVO ) objs.get( 0 );
      }
      return userVO;
   }

   @Override
   public List< Object > getAccessLoginUser() throws KANException
   {
      return selectList( "getAccessLoginUser", "" );
   }

   @Override
   public UserVO getUserVOByRemark5( final String remark5 ) throws KANException
   {
      final List< Object > objects = selectList( "getUserVOByRemark5", remark5 );
      return objects != null && objects.size() == 1 ? ( UserVO ) objects.get( 0 ) : null;
   }

	@Override
	public int updatePassWord(UserVO userVO) throws KANException {
		 return update( "updatePassWord", userVO );
	}

}