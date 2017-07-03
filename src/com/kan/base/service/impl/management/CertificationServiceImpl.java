package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.CertificationDao;
import com.kan.base.domain.management.CertificationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPCertificationService;
import com.kan.base.service.inf.management.CertificationService;
import com.kan.base.util.KANException;

public class CertificationServiceImpl extends ContextService implements CertificationService,CPCertificationService
{

   @Override
   public PagedListHolder getCertificationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CertificationDao certificationDao = ( CertificationDao ) getDao();
      pagedListHolder.setHolderSize( certificationDao.countCertificationVOsByCondition( ( CertificationVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( certificationDao.getCertificationVOsByCondition( ( CertificationVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( certificationDao.getCertificationVOsByCondition( ( CertificationVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public CertificationVO getCertificationVOByCertificationId( final String certificationId ) throws KANException
   {
      return ( ( CertificationDao ) getDao() ).getCertificationVOByCertificationId( certificationId );
   }

   @Override
   public int insertCertification( final CertificationVO certificationVO ) throws KANException
   {
      return ( ( CertificationDao ) getDao() ).insertCertification( certificationVO );
   }

   @Override
   public int updateCertification( final CertificationVO certificationVO ) throws KANException
   {
      return ( ( CertificationDao ) getDao() ).updateCertification( certificationVO );
   }

   @Override
   public int deleteCertification( final CertificationVO certificationVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final CertificationVO modifyObject = ( ( CertificationDao ) getDao() ).getCertificationVOByCertificationId( certificationVO.getCertificationId() );
      modifyObject.setDeleted( CertificationVO.FALSE );
      return ( ( CertificationDao ) getDao() ).updateCertification( modifyObject );
   }

   @Override
   public List< Object > getCertificationBaseViewsByAccountId(final String accountId )
   {
      // TODO Auto-generated method stub
      return ( ( CertificationDao ) getDao() ).getCertificationBaseViewsByAccountId( accountId );
   }

   @Override
   public List< Object > getCertificationVOsByAccountId( String accountId )
   {
      return ( ( CertificationDao ) getDao() ).getCertificationVOsByAccountId( accountId );
   }

   
}
