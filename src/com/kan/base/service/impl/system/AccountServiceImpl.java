package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.EmailConfigurationDao;
import com.kan.base.dao.inf.management.OptionsDao;
import com.kan.base.dao.inf.management.ShareFolderConfigurationDao;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.dao.inf.system.AccountDao;
import com.kan.base.dao.inf.system.AccountModuleRelationDao;
import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.domain.management.OptionsVO;
import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class AccountServiceImpl extends ContextService implements AccountService
{
   private UserDao userDao;

   private OptionsDao optionsDao;

   private EmailConfigurationDao emailConfigurationDao;

   private ShareFolderConfigurationDao shareFolderConfigurationDao;

   private AccountModuleRelationDao accountModuleRelationDao;

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }

   public OptionsDao getOptionsDao()
   {
      return optionsDao;
   }

   public void setOptionsDao( OptionsDao optionsDao )
   {
      this.optionsDao = optionsDao;
   }

   public EmailConfigurationDao getEmailConfigurationDao()
   {
      return emailConfigurationDao;
   }

   public void setEmailConfigurationDao( EmailConfigurationDao emailConfigurationDao )
   {
      this.emailConfigurationDao = emailConfigurationDao;
   }

   public ShareFolderConfigurationDao getShareFolderConfigurationDao()
   {
      return shareFolderConfigurationDao;
   }

   public void setShareFolderConfigurationDao( ShareFolderConfigurationDao shareFolderConfigurationDao )
   {
      this.shareFolderConfigurationDao = shareFolderConfigurationDao;
   }

   public AccountModuleRelationDao getAccountModuleRelationDao()
   {
      return accountModuleRelationDao;
   }

   public void setAccountModuleRelationDao( AccountModuleRelationDao accountModuleRelationDao )
   {
      this.accountModuleRelationDao = accountModuleRelationDao;
   }

   @Override
   public PagedListHolder getAccountVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final AccountDao accountDao = ( AccountDao ) getDao();
      pagedListHolder.setHolderSize( accountDao.countAccountVOsByCondition( ( AccountVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( accountDao.getAccountVOsByCondition( ( AccountVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( accountDao.getAccountVOsByCondition( ( AccountVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public AccountVO getAccountVOByAccountId( final String accountId ) throws KANException
   {
      return ( ( AccountDao ) getDao() ).getAccountVOByAccountId( accountId );
   }

   @Override
   public int updateAccount( final AccountVO accountVO ) throws KANException
   {
      // 修改Account对象
      final int accountRows = ( ( AccountDao ) getDao() ).updateAccount( accountVO );

      // 建立Account与Module之间的关联
      insertAccountModule( accountVO );

      return accountRows;
   }

   @Override
   public int insertAccount( final AccountVO accountVO ) throws KANException
   {
      // 添加Account对象
      final int accountRows = ( ( AccountDao ) getDao() ).insertAccount( accountVO );

      // 建立Account与Module之间的关联
      insertAccountModule( accountVO );

      return accountRows;

   }

   // 建立Account与Module之间的关联
   private int insertAccountModule( final AccountVO accountVO ) throws KANException
   {
      // 如果当前Account有设置模块
      if ( accountVO.getModuleIdArray() != null && accountVO.getModuleIdArray().length > 0 )
      {
         // 先删除当前Account跟Module的关联
         accountModuleRelationDao.deleteAccountModuleRelationByAccountId( accountVO.getAccountId() );

         // 遍历ModuleId数组，建立Account与Module的关联
         for ( String moduleId : accountVO.getModuleIdArray() )
         {
            final AccountModuleRelationVO accountModuleRelationVO = new AccountModuleRelationVO();
            accountModuleRelationVO.setAccountId( accountVO.getAccountId() );
            accountModuleRelationVO.setModuleId( moduleId );
            accountModuleRelationVO.setCreateBy( accountVO.getModifyBy() );
            accountModuleRelationVO.setModifyBy( accountVO.getModifyBy() );
            accountModuleRelationDao.insertAccountModuleRelation( accountModuleRelationVO );
         }

         return accountVO.getModuleIdArray().length;
      }

      return 0;
   }

   @Override
   public void deleteAccount( final AccountVO accountVO ) throws KANException
   {
      ( ( AccountDao ) getDao() ).deleteAccount( accountVO );
   }

   @Override
   public List< Object > getAccountBaseViews() throws KANException
   {
      return ( ( AccountDao ) getDao() ).getAccountBaseViews();
   }

   @Override
   public boolean initiateAccount( AccountVO accountVO ) throws KANException
   {
      try
      {
         // 获得需要激活的Account
         final AccountVO targetAccountVO = ( ( AccountDao ) getDao() ).getAccountVOByAccountId( accountVO.getAccountId() );

         if ( targetAccountVO != null && targetAccountVO.getInitialized().trim().equalsIgnoreCase( AccountVO.FALSE ) )
         {
            // 开启事务
            this.startTransaction();

            // 初始化超级管理员
            final UserVO userVO = new UserVO();
            userVO.setAccountId( accountVO.getAccountId() );
            userVO.setUsername( "Admin" );
            userVO.setPassword( Cryptogram.encodeString( "Admin" ) );
            userVO.setStatus( UserVO.TRUE );
            userVO.setCreateBy( accountVO.getCreateBy() );
            userVO.setModifyBy( accountVO.getModifyBy() );
            this.userDao.insertUser( userVO );

            // 初始化选项设置
            final OptionsVO optionsVO = new OptionsVO();
            optionsVO.setAccountId( accountVO.getAccountId() );
            optionsVO.setUseBrowserLanguage( OptionsVO.FALSE );
            optionsVO.setCreateBy( accountVO.getCreateBy() );
            optionsVO.setModifyBy( accountVO.getModifyBy() );
            this.optionsDao.insertOptions( optionsVO );

            // 初始化邮件设置
            final EmailConfigurationVO emailConfigurationVO = new EmailConfigurationVO();
            emailConfigurationVO.setAccountId( accountVO.getAccountId() );
            emailConfigurationVO.setCreateBy( accountVO.getCreateBy() );
            emailConfigurationVO.setModifyBy( accountVO.getModifyBy() );
            this.emailConfigurationDao.insertEmailConfiguration( emailConfigurationVO );

            // 初始化Share Folder设置
            final ShareFolderConfigurationVO shareFolderConfigurationVO = new ShareFolderConfigurationVO();
            shareFolderConfigurationVO.setAccountId( accountVO.getAccountId() );
            shareFolderConfigurationVO.setCreateBy( accountVO.getCreateBy() );
            shareFolderConfigurationVO.setModifyBy( accountVO.getModifyBy() );
            this.shareFolderConfigurationDao.insertShareFolderConfiguration( shareFolderConfigurationVO );

            // 设置初始化状态
            targetAccountVO.setInitialized( AccountVO.TRUE );
            targetAccountVO.setModifyBy( accountVO.getModifyBy() );
            ( ( AccountDao ) getDao() ).updateAccount( targetAccountVO );

            // 提交事务
            this.commitTransaction();
            return true;
         }
         else
         {
            // 未找到需要初始化的对象或对象已被初始化
            return false;
         }

      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public AccountVO getAccountVOByAccountName(final String accountName ) throws KANException
   {
      return ( ( AccountDao ) getDao() ).getAccountVOByAccountName( accountName );
   }
}
