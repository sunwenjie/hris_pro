package com.kan.hro.dao.mybatis.impl.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.vendor.VendorUserDao;
import com.kan.hro.domain.biz.vendor.VendorUserVO;

public class VendorUserDaoImpl extends Context implements VendorUserDao
{

   @Override
   public int countVendorUserVOsByCondition( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( Integer ) select( "countVendorUserVOsByCondition", vendorUserVO );
   }

   @Override
   public List< Object > getVendorUserVOsByCondition( final VendorUserVO vendorUserVO ) throws KANException
   {
      return selectList( "getVendorUserVOsByCondition", vendorUserVO );
   }

   @Override
   public List< Object > getVendorUserVOsByCondition( final VendorUserVO vendorUserVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorUserVOsByCondition", vendorUserVO, rowBounds );
   }

   @Override
   public VendorUserVO getVendorUserVOByVendorUserId( final String vendorUserId ) throws KANException
   {
      return ( VendorUserVO ) select( "getVendorUserVOByVendorUserId", vendorUserId );
   }

   @Override
   public int insertVendorUser( final VendorUserVO vendorUserVO ) throws KANException
   {
      return insert( "insertVendorUser", vendorUserVO );
   }

   @Override
   public int updateVendorUser( final VendorUserVO vendorUserVO ) throws KANException
   {
      return update( "updateVendorUser", vendorUserVO );
   }

   @Override
   public int deleteVendorUser( final VendorUserVO vendorUserVO ) throws KANException
   {
      return delete( "deleteVendorUser", vendorUserVO );
   }

   @Override
   public VendorUserVO getVendorUserVOByUsername( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( VendorUserVO ) select( "getVendorUserVOByUsername", vendorUserVO );
   }

   @Override
   public VendorUserVO login_inVendor( final VendorUserVO vendorUserVO ) throws KANException
   {
      return ( VendorUserVO ) select( "login_inVendor", vendorUserVO );
   }

}
