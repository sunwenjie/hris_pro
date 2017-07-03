package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.UserVO;
import com.kan.base.util.KANException;

public interface UserDao
{

   public abstract int countUserVOsByCondition( final UserVO userVO ) throws KANException;

   public abstract List< Object > getUserVOsByCondition( final UserVO userVO ) throws KANException;

   public abstract List< Object > getUserVOsByCondition( final UserVO userVO, RowBounds rowBounds ) throws KANException;

   public abstract UserVO getUserVOByUserId( final String userId ) throws KANException;

   public abstract UserVO getUserVOByStaffId( final String staffId ) throws KANException;

   public abstract List< Object > getUserVOsByCorpId( final String corpId ) throws KANException;

   public abstract UserVO login( final UserVO userVO ) throws KANException;

   public abstract UserVO getUserVOByMobile( final String mobile ) throws KANException;

   public abstract UserVO getUserVOByEmail( final String email ) throws KANException;

   public abstract int updateUser( final UserVO userVO ) throws KANException;

   public abstract int insertUser( final UserVO userVO ) throws KANException;

   public abstract int deleteUser( final UserVO userVO ) throws KANException;

   public abstract int backupUser( final UserVO userVO ) throws KANException;

   public abstract List< Object > getHistoryUserVOsByUserId( final String userId ) throws KANException;

   public abstract UserVO getHistoryUserVOByHistoryId( final String historyId ) throws KANException;

   public abstract List< Object > getClientVOs() throws KANException;

   public abstract UserVO login_inHouse( final UserVO userVO ) throws KANException;

   public abstract int countUsersByUserName( final String username ) throws KANException;

   //remark1用来做微信登陆
   public abstract UserVO getUserVOByRemark1( final String remark1 ) throws KANException;

   //remark5 用来做找回密码
   public abstract UserVO getUserVOByRemark5( final String remark5 ) throws KANException;

   /**
    * 获取能登陆的用户
    * @return
    * @throws KANException
    */
   public abstract List< Object > getAccessLoginUser() throws KANException;

   public abstract int updatePassWord(UserVO userVO) throws KANException;

}