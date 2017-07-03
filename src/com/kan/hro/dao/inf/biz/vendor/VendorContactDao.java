package com.kan.hro.dao.inf.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorContactVO;

public interface VendorContactDao
{
   public abstract int countVendorContactVOsByCondition( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract List< Object > getVendorContactVOsByCondition( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract List< Object > getVendorContactVOsByCondition( final VendorContactVO vendorContactVO, final RowBounds rowBounds ) throws KANException;

   public abstract VendorContactVO getVendorContactVOByVendorContactId( final String vendorContactId ) throws KANException;

   public abstract int insertVendorContact( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract int updateVendorContact( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract int deleteVendorContact( final VendorContactVO vendorContactVO ) throws KANException;

   public abstract List< Object > getVendorContactBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getVendorContactVOsByVendorId( final String vendorId ) throws KANException;
   
   public abstract List<Object> vendorLogon(final VendorContactVO vendorContactVO) throws KANException;
}
