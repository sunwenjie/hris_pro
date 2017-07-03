package com.kan.hro.service.inf.biz.vendor;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorUserVO;

public interface VendorUserService
{
   public abstract PagedListHolder getVendorUserVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract VendorUserVO getVendorUserVOByVendorUserId( final String vendorUserId ) throws KANException;

   public abstract int insertVendorUser( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract int updateVendorUser( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract int deleteVendorUser( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract VendorUserVO getVendorUserVOByUsername( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract VendorUserVO login_inVendor( final VendorUserVO vendorUserVO ) throws KANException;
}
