package com.kan.hro.dao.inf.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;

public interface VendorServiceDao
{
   public abstract int countVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract List< Object > getVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract List< Object > getVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO, RowBounds rowBounds ) throws KANException;

   public abstract VendorServiceVO getVendorServiceVOByServiceId( final String serviceId ) throws KANException;

   public abstract int insertVendorService( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract int updateVendorService( final VendorServiceVO vendorServiceVO ) throws KANException;

   public abstract int deleteVendorService( final String serviceId ) throws KANException;

   public abstract List< Object > getVendorServiceVOsByVendorId( final String vendorId ) throws KANException;
}
