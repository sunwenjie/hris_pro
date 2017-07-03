package com.kan.hro.dao.inf.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.vendor.VendorVO;

public interface VendorDao
{
   public abstract int countVendorVOsByCondition( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getVendorVOsByCondition( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getVendorVOsByCondition( final VendorVO vendorVO, final RowBounds rowBounds ) throws KANException;

   public abstract VendorVO getVendorVOByVendorId( final String vendorId ) throws KANException;

   public abstract int insertVendor( final VendorVO vendorVO ) throws KANException;

   public abstract int updateVendor( final VendorVO vendorVO ) throws KANException;

   public abstract int deleteVendor( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getVendorBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getVendorVOsBySBHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getVendorVOsBySBSolutionHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException;

}
