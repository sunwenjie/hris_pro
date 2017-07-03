package com.kan.hro.service.impl.biz.vendor;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.service.inf.biz.vendor.VendorServiceService;

public class VendorServiceServiceImpl extends ContextService implements VendorServiceService
{

   @Override
   public PagedListHolder getVendorServiceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final VendorServiceDao vendorServiceDao = ( VendorServiceDao ) getDao();
      pagedListHolder.setHolderSize( vendorServiceDao.countVendorServiceVOsByCondition( ( VendorServiceVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( vendorServiceDao.getVendorServiceVOsByCondition( ( VendorServiceVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( vendorServiceDao.getVendorServiceVOsByCondition( ( VendorServiceVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public VendorServiceVO getVendorServiceVOByServiceId( final String serviceId ) throws KANException
   {
      return ( ( VendorServiceDao ) getDao() ).getVendorServiceVOByServiceId( serviceId );
   }

   @Override
   public int insertVendorService( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      try
      {
         if ( vendorServiceVO != null && vendorServiceVO.getServiceArray().length > 0 )
         {
            String serviceIds = "";
            for ( String str : vendorServiceVO.getServiceArray() )
            {
               serviceIds += str + ",";
            }
            serviceIds = serviceIds.substring( 0, serviceIds.length() - 1 );
            vendorServiceVO.setServiceIds( serviceIds );
         }
         ( ( VendorServiceDao ) getDao() ).insertVendorService( vendorServiceVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int updateVendorService( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      try
      {
         if ( vendorServiceVO != null && vendorServiceVO.getServiceArray().length > 0 )
         {
            String serviceIds = "";
            for ( String str : vendorServiceVO.getServiceArray() )
            {
               serviceIds += str + ",";
            }
            serviceIds = serviceIds.substring( 0, serviceIds.length() - 1 );
            vendorServiceVO.setServiceIds( serviceIds );
         }
         ( ( VendorServiceDao ) getDao() ).updateVendorService( vendorServiceVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int deleteVendorService( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      vendorServiceVO.setDeleted( VendorServiceVO.FALSE );
      return ( ( VendorServiceDao ) getDao() ).updateVendorService( vendorServiceVO );
   }

   @Override
   public List< Object > getVendorServiceVOsByVendorId( final String vendorId ) throws KANException
   {
      final VendorServiceVO vendorServiceVO = new VendorServiceVO();
      vendorServiceVO.setVendorId( vendorId );
      vendorServiceVO.setStatus( BaseVO.TRUE );
      vendorServiceVO.setSortOrder( "ASC" );
      vendorServiceVO.setSortColumn( "cityId,sbHeaderId" );
      return ( ( VendorServiceDao ) getDao() ).getVendorServiceVOsByCondition( vendorServiceVO );
   }

   @Override
   public List< Object > getVendorServiceVOsByCondition( final VendorServiceVO vendorServiceVO ) throws KANException
   {
      return ( ( VendorServiceDao ) getDao() ).getVendorServiceVOsByCondition( vendorServiceVO );
   }

}
