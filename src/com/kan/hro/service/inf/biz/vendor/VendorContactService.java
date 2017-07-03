package com.kan.hro.service.inf.biz.vendor;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorContactVO;

public interface VendorContactService
{
   public abstract PagedListHolder getVendorContactVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract VendorContactVO getVendorContactVOByVendorContactId( final String vendorContactId ) throws KANException;

   public abstract int insertVendorContact( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract int updateVendorContact( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract int deleteVendorContact( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract List< Object > getVendorContactBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getVendorContactVOsByVendorId( final String vendorId ) throws KANException;
   
   public abstract List< Object > vendorLogon( final VendorContactVO vendorContactVO ) throws KANException;
}
