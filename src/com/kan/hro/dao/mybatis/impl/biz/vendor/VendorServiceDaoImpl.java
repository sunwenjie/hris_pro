package com.kan.hro.dao.mybatis.impl.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;

public class VendorServiceDaoImpl extends Context implements VendorServiceDao
{

   @Override
   public int countVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      return ( Integer ) select( "countVendorServiceVOsByCondition", vendorServiceVO );
   }

   @Override
   public List< Object > getVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      return selectList( "getVendorServiceVOsByCondition", vendorServiceVO );
   }

   @Override
   public List< Object > getVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorServiceVOByCondition", vendorServiceVO, rowBounds );
   }

   @Override
   public VendorServiceVO getVendorServiceVOByServiceId( final String serviceId ) throws KANException
   {
      return ( VendorServiceVO ) select( "getVendorServiceVOByServiceId", serviceId );
   }

   @Override
   public int insertVendorService( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      return insert( "insertVendorService", vendorServiceVO );
   }

   @Override
   public int updateVendorService( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      return update( "updateVendorService", vendorServiceVO );
   }

   @Override
   public int deleteVendorService( final String serviceId ) throws KANException
   {
      return delete( "deleteVendorService", serviceId );
   }

   @Override
   public List< Object > getVendorServiceVOsByVendorId( final String vendorId ) throws KANException
   {
      return selectList( "getVendorServiceVOsByVendorId", vendorId );
   }

}
