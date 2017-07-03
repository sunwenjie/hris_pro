package com.kan.hro.dao.mybatis.impl.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.vendor.VendorContactDao;
import com.kan.hro.domain.biz.vendor.VendorContactVO;

public class VendorContactDaoImpl extends Context implements VendorContactDao
{

   @Override
   public int countVendorContactVOsByCondition( final VendorContactVO vendorContactVO ) throws KANException
   {
      return ( Integer ) select( "countVendorContactVOsByCondition", vendorContactVO );
   }

   @Override
   public List< Object > getVendorContactVOsByCondition( final VendorContactVO vendorContactVO ) throws KANException
   {
      return selectList( "getVendorContactVOsByCondition", vendorContactVO );
   }

   @Override
   public List< Object > getVendorContactVOsByCondition( final VendorContactVO vendorContactVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorContactVOsByCondition", vendorContactVO, rowBounds );
   }

   @Override
   public VendorContactVO getVendorContactVOByVendorContactId( final String vendorContactId ) throws KANException
   {
      return ( VendorContactVO ) select( "getVendorContactVOByVendorContactId", vendorContactId );
   }

   @Override
   public int insertVendorContact( final VendorContactVO vendorContactVO ) throws KANException
   {
      return insert( "insertVendorContact", vendorContactVO );
   }

   @Override
   public int updateVendorContact( final VendorContactVO vendorContactVO ) throws KANException
   {
      return update( "updateVendorContact", vendorContactVO );
   }

   @Override
   public int deleteVendorContact( final VendorContactVO vendorContactVO ) throws KANException
   {
      return delete( "deleteVendorContact", vendorContactVO );
   }

   @Override
   public List< Object > getVendorContactBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getVendorContactBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getVendorContactVOsByVendorId( final String vendorId ) throws KANException
   {
      return selectList( "getVendorContactVOsByVendorId", vendorId );
   }

   @Override
   public List< Object > vendorLogon(final VendorContactVO vendorContactVO ) throws KANException
   {
      return selectList( "vendorLogon", vendorContactVO );
   }

}
