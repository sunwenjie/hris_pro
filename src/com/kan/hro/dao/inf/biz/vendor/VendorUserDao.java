package com.kan.hro.dao.inf.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorUserVO;

public interface VendorUserDao
{
   public abstract int countVendorUserVOsByCondition( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract List< Object > getVendorUserVOsByCondition( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract List< Object > getVendorUserVOsByCondition( final VendorUserVO vendorUserVO, final RowBounds rowBounds ) throws KANException;
                               
   public abstract VendorUserVO getVendorUserVOByVendorUserId( final String vendorUserId ) throws KANException;

   public abstract int insertVendorUser( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract int updateVendorUser( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract int deleteVendorUser( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract VendorUserVO getVendorUserVOByUsername( final VendorUserVO vendorUserVO ) throws KANException;

   public abstract VendorUserVO login_inVendor( final VendorUserVO vendorUserVO ) throws KANException;

}
