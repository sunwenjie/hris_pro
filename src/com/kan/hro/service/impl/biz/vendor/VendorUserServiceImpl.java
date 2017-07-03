package com.kan.hro.service.impl.biz.vendor;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.vendor.VendorUserDao;
import com.kan.hro.domain.biz.vendor.VendorUserVO;
import com.kan.hro.service.inf.biz.vendor.VendorUserService;

public class VendorUserServiceImpl extends ContextService implements VendorUserService
{

   @Override
   public PagedListHolder getVendorUserVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final VendorUserDao vendorUserDao = ( VendorUserDao ) getDao();
      pagedListHolder.setHolderSize( vendorUserDao.countVendorUserVOsByCondition( ( VendorUserVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( vendorUserDao.getVendorUserVOsByCondition( ( VendorUserVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( vendorUserDao.getVendorUserVOsByCondition( ( VendorUserVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public VendorUserVO getVendorUserVOByVendorUserId( final String vendorUserId ) throws KANException
   {
      return ( ( VendorUserDao ) getDao() ).getVendorUserVOByVendorUserId( vendorUserId );
   }

   @Override
   public int insertVendorUser( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( ( VendorUserDao ) getDao() ).insertVendorUser( vendorUserVO );
   }

   @Override
   public int updateVendorUser( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( ( VendorUserDao ) getDao() ).updateVendorUser( vendorUserVO );
   }

   @Override
   public int deleteVendorUser( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( ( VendorUserDao ) getDao() ).deleteVendorUser( vendorUserVO );
   }

   @Override
   public VendorUserVO getVendorUserVOByUsername( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( ( VendorUserDao ) getDao() ).getVendorUserVOByUsername( vendorUserVO );
   }

   @Override
   public VendorUserVO login_inVendor( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( ( VendorUserDao ) getDao() ).login_inVendor( vendorUserVO );
   }

}
