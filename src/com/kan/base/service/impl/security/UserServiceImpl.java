package com.kan.base.service.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.util.KANException;

public class UserServiceImpl extends ContextService implements UserService
{

   @Override
   public PagedListHolder getUserVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final UserDao userDao = ( UserDao ) getDao();
      pagedListHolder.setHolderSize( userDao.countUserVOsByCondition( ( UserVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( userDao.getUserVOsByCondition( ( UserVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( userDao.getUserVOsByCondition( ( UserVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public UserVO getUserVOByUserId( final String userId ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getUserVOByUserId( userId );
   }

   @Override
   public UserVO login( final UserVO userVO ) throws KANException
   {
      return ( ( UserDao ) getDao() ).login( userVO );
   }

   @Override
   public int updateUser( final UserVO userVO ) throws KANException
   {
      //      int effectRows;
      //
      //      try
      //      {
      //         // 打开事务
      //         this.startTransaction();
      //
      //         // Todo - 先备份需要更改的数据
      //         //( ( UserDao ) getDao() ).backupUser( userVO );
      //         // 更改目标数据
      //         effectRows = ( ( UserDao ) getDao() ).updateUser( userVO );
      //
      //         // 提交事务
      //         this.commitTransaction();
      //      }
      //      catch ( final Exception e )
      //      {
      //         this.rollbackTransaction();
      //         throw new KANException( e );
      //      }
      //
      //      return effectRows;

      return ( ( UserDao ) getDao() ).updateUser( userVO );
   }

   @Override
   public int insertUser( final UserVO userVO ) throws KANException
   {
      return ( ( UserDao ) getDao() ).insertUser( userVO );
   }

   @Override
   public void deleteUser( final UserVO userVO ) throws KANException
   {
      ( ( UserDao ) getDao() ).deleteUser( userVO );
   }

   @Override
   public UserVO getUserVOByMobile( final String mobile ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getUserVOByMobile( mobile );
   }

   @Override
   public UserVO getUserVOByEmail( final String email ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getUserVOByEmail( email );
   }

   @Override
   public List< Object > getHistoryUserVOsByUserId( final String userId ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getHistoryUserVOsByUserId( userId );
   }

   @Override
   public UserVO getHistoryUserVOByHistoryId( final String historyId ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getHistoryUserVOByHistoryId( historyId );
   }

   @Override
   public UserVO getUserVOByStaffId( final String staffId ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getUserVOByStaffId( staffId );
   }

   @Override
   public List< Object > getClientVOs() throws KANException
   {
      return ( ( UserDao ) getDao() ).getClientVOs();
   }

   @Override
   public UserVO login_inHouse( final UserVO userVO ) throws KANException
   {
      return ( ( UserDao ) getDao() ).login_inHouse( userVO );
   }

   @Override
   public List< Object > getUserVOsByCorpId( final String corpId ) throws KANException
   {
      return ( ( UserDao ) getDao() ).getUserVOsByCorpId( corpId );
   }

   @Override
   public int countUsersByUserName( String username ) throws KANException
   {
      return ( ( UserDao ) getDao() ).countUsersByUserName( username );
   }

   @Override
   public UserVO getUserVOByRemark1( String remark1 ) throws KANException
   {
      // remark1 记录wx user name
      return ( ( UserDao ) getDao() ).getUserVOByRemark1( remark1 );
   }

   @Override
   public UserVO getUserVOByRemark5( String remark5 ) throws KANException
   {
      // remark5 重置密码记录随机码
      return ( ( UserDao ) getDao() ).getUserVOByRemark5( remark5 );
   }

	@Override
	public int updatePassWord(UserVO userVO) throws KANException
	{
		 return ( ( UserDao ) getDao() ).updatePassWord( userVO );
		
	}

}
