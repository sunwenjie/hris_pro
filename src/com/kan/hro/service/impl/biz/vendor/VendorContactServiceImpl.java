package com.kan.hro.service.impl.biz.vendor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.RandomUtil;
import com.kan.hro.dao.inf.biz.vendor.VendorContactDao;
import com.kan.hro.dao.inf.biz.vendor.VendorUserDao;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorUserVO;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;

public class VendorContactServiceImpl extends ContextService implements VendorContactService
{

   protected Log logger = LogFactory.getLog( getClass() );

   private VendorUserDao vendorUserDao;

   public VendorUserDao getVendorUserDao()
   {
      return vendorUserDao;
   }

   public void setVendorUserDao( VendorUserDao vendorUserDao )
   {
      this.vendorUserDao = vendorUserDao;
   }

   @Override
   public PagedListHolder getVendorContactVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final VendorContactDao vendorContactDao = ( VendorContactDao ) getDao();
      pagedListHolder.setHolderSize( vendorContactDao.countVendorContactVOsByCondition( ( VendorContactVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( vendorContactDao.getVendorContactVOsByCondition( ( VendorContactVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( vendorContactDao.getVendorContactVOsByCondition( ( VendorContactVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public VendorContactVO getVendorContactVOByVendorContactId( final String vendorContactId ) throws KANException
   {
      return ( ( VendorContactDao ) getDao() ).getVendorContactVOByVendorContactId( vendorContactId );
   }

   @Override
   public int insertVendorContact( final VendorContactVO vendorContactVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 先添加供应商联系人
         ( ( VendorContactDao ) getDao() ).insertVendorContact( vendorContactVO );

         // 如果UserName不为空，则新增VendorUser对象
         if ( KANUtil.filterEmpty( vendorContactVO.getUsername() ) != null )
         {
            // 初始化VendorUserVO
            final VendorUserVO vendorUserVO = new VendorUserVO();

            vendorUserVO.setVendorId( vendorContactVO.getVendorId() );
            vendorUserVO.setUsername( vendorContactVO.getUsername() );
            vendorUserVO.setAccountId( vendorContactVO.getAccountId() );
            vendorUserVO.setVendorContactId( vendorContactVO.getVendorContactId() );
            vendorUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            vendorUserVO.setCreateBy( vendorContactVO.getCreateBy() );
            vendorUserVO.setModifyBy( vendorContactVO.getModifyBy() );
            vendorUserVO.setStatus( VendorContactVO.TRUE );
            vendorUserVO.setRole( vendorContactVO.getRole() );

            // 如果当前用户名不存在，则添加用户记录
            if ( vendorUserDao.getVendorUserVOByUsername( vendorUserVO ) == null )
            {
               vendorUserDao.insertVendorUser( vendorUserVO );
            }

            // 发送邮件通知
            if ( KANUtil.filterEmpty( vendorContactVO.getBizEmail() ) != null )
            {
               //               final String strs[] = ContentUtil.getMailContent_UserCreate_Vendor( new Object[] { vendorUserVO } );
               //
               //               for ( String str : strs )
               //               {
               //                  logger.info( str.toString() );
               //               }

               new Mail( vendorContactVO.getAccountId(), vendorContactVO.getBizEmail(), ContentUtil.getMailContent_UserCreate_Vendor( new Object[] { vendorUserVO } ) ).send( true );
            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int updateVendorContact( final VendorContactVO vendorContactVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();
         // 先修改主表
         ( ( VendorContactDao ) getDao() ).updateVendorContact( vendorContactVO );
         // 如果UserName不为空，则新增VendorUser对象
         if ( KANUtil.filterEmpty( vendorContactVO.getUsername() ) != null )
         {
            // 初始化VendorUserVO
            final VendorUserVO vendorUserVO = new VendorUserVO();

            vendorUserVO.setUsername( vendorContactVO.getUsername() );
            vendorUserVO.setAccountId( vendorContactVO.getAccountId() );

            // 如果当前用户名不存在，则添加用户记录
            if ( vendorUserDao.getVendorUserVOByUsername( vendorUserVO ) == null )
            {
               // 设置属性
               vendorUserVO.setRole( vendorContactVO.getRole() );
               vendorUserVO.setVendorId( vendorContactVO.getVendorId() );
               vendorUserVO.setVendorContactId( vendorContactVO.getVendorContactId() );
               vendorUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
               vendorUserVO.setCreateBy( vendorContactVO.getCreateBy() );
               vendorUserVO.setModifyBy( vendorContactVO.getModifyBy() );
               vendorUserVO.setStatus( VendorContactVO.TRUE );
               vendorUserDao.insertVendorUser( vendorUserVO );

               // 发送邮件通知
               if ( KANUtil.filterEmpty( vendorContactVO.getBizEmail() ) != null )
               {

                  //                  final String strs[] = ContentUtil.getMailContent_UserCreate_Vendor( new Object[] { vendorUserVO } );
                  //
                  //                  for ( String str : strs )
                  //                  {
                  //                     logger.info( str.toString() );
                  //                  }
                  //
                  //                  new Mail( vendorContactVO.getAccountId(), vendorContactVO.getBizEmail(), strs ).send( true );
                  new Mail( vendorContactVO.getAccountId(), vendorContactVO.getBizEmail(), ContentUtil.getMailContent_UserCreate_Vendor( new Object[] { vendorUserVO } ) ).send( true );
               }
            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public int deleteVendorContact( final VendorContactVO vendorContactVO ) throws KANException
   {
      vendorContactVO.setDeleted( VendorContactVO.FALSE );
      return ( ( VendorContactDao ) getDao() ).updateVendorContact( vendorContactVO );
   }

   @Override
   public List< Object > getVendorContactBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( VendorContactDao ) getDao() ).getVendorContactBaseViewsByAccountId( accountId );
   }

   @Override
   public List< Object > getVendorContactVOsByVendorId( final String vendorId ) throws KANException
   {
      return ( ( VendorContactDao ) getDao() ).getVendorContactVOsByVendorId( vendorId );
   }

   @Override
   public List< Object > vendorLogon( final VendorContactVO vendorContactVO ) throws KANException
   {
      return ( ( VendorContactDao ) getDao() ).vendorLogon( vendorContactVO );
   }

}
