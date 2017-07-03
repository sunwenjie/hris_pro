package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface UserService
{

   public abstract PagedListHolder getUserVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract UserVO getUserVOByUserId( final String userId ) throws KANException;
   
   public abstract UserVO getUserVOByStaffId( final String staffId ) throws KANException;
   
   public abstract List< Object > getUserVOsByCorpId( final String corpId ) throws KANException;

   public abstract UserVO login( final UserVO userVO ) throws KANException;

   public abstract UserVO getUserVOByMobile( final String mobile ) throws KANException;

   public abstract UserVO getUserVOByEmail( final String email ) throws KANException;

   public abstract int updateUser( final UserVO userVO ) throws KANException;

   public abstract int insertUser( final UserVO userVO ) throws KANException;

   public abstract void deleteUser( final UserVO userVO ) throws KANException;

   public abstract List< Object > getHistoryUserVOsByUserId( final String userId ) throws KANException;

   public abstract UserVO getHistoryUserVOByHistoryId( final String historyId ) throws KANException;
   
   public abstract List< Object > getClientVOs() throws KANException;

   public abstract UserVO login_inHouse( final UserVO userVO ) throws KANException;
   
   public abstract int countUsersByUserName( final String username ) throws KANException;
   
   public abstract UserVO getUserVOByRemark1( final String remark1 ) throws KANException;
   
   public abstract UserVO getUserVOByRemark5( final String remark5 ) throws KANException;

   public abstract int updatePassWord(UserVO userVO) throws KANException;
}
