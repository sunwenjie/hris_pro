package com.kan.hro.service.inf.biz.vendor;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;

public interface VendorServiceService
{
   public abstract PagedListHolder getVendorServiceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract VendorServiceVO getVendorServiceVOByServiceId( final String serviceId ) throws KANException;

   public abstract int insertVendorService( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract int updateVendorService( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract int deleteVendorService( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract List< Object > getVendorServiceVOsByVendorId( final String vendorId ) throws KANException;

   public abstract List< Object > getVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO ) throws KANException;
}
