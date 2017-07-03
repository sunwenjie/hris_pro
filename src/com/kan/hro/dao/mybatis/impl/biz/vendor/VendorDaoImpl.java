package com.kan.hro.dao.mybatis.impl.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.domain.biz.vendor.VendorVO;

public class VendorDaoImpl extends Context implements VendorDao
{

   @Override
   public int countVendorVOsByCondition( final VendorVO vendorVO ) throws KANException
   {
      return ( Integer ) select( "countVendorVOsByCondition", vendorVO );
   }

   @Override
   public List< Object > getVendorVOsByCondition( final VendorVO vendorVO ) throws KANException
   {
      return selectList( "getVendorVOsByCondition", vendorVO );
   }

   @Override
   public List< Object > getVendorVOsByCondition( final VendorVO vendorVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorVOsByCondition", vendorVO, rowBounds );
   }

   @Override
   public VendorVO getVendorVOByVendorId( final String vendorId ) throws KANException
   {
      return ( VendorVO ) select( "getVendorVOByVendorId", vendorId );
   }

   @Override
   public int insertVendor( final VendorVO vendorVO ) throws KANException
   {
      return insert( "insertVendor", vendorVO );
   }

   @Override
   public int updateVendor( final VendorVO vendorVO ) throws KANException
   {
      return update( "updateVendor", vendorVO );
   }

   @Override
   public int deleteVendor( final VendorVO vendorVO ) throws KANException
   {
      return delete( "deleteVendor", vendorVO );
   }

   @Override
   public List< Object > getVendorBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getVendorBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getVendorVOsBySBHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getVendorVOsBySBHeaderId", headerId );
   }

   @Override
   public List< Object > getVendorVOsBySBSolutionHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return selectList( "getVendorVOsBySBSolutionHeaderVO", socialBenefitSolutionHeaderVO );
   }

}
