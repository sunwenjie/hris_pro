package com.kan.hro.service.inf.biz.vendor;

import java.util.List;

import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorDTO;
import com.kan.hro.domain.biz.vendor.VendorVO;

public interface VendorService
{
   public abstract PagedListHolder getVendorVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract VendorVO getVendorVOByVendorId( final String vendorId ) throws KANException;

   public abstract int insertVendor( final VendorVO vendorVO ) throws KANException;

   public abstract int updateVendor( final VendorVO vendorVO ) throws KANException;

   public abstract int deleteVendor( final VendorVO vendorVO ) throws KANException;

   public abstract List< VendorDTO > getVendorDTOsByAccountId( final String accountId ) throws KANException;

   public abstract VendorDTO getVendorDTOByCondition( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getVendorBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getVendorVOsBySBHeaderId( final String headerId ) throws KANException;

   public abstract int submitVendor( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getVendorVOsByCondition( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getVendorVOsBySBSolutionHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

}
