package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.CertificationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CertificationService
{
   public abstract PagedListHolder getCertificationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CertificationVO getCertificationVOByCertificationId( final String certificationId ) throws KANException;

   public abstract int insertCertification( final CertificationVO certificationVO ) throws KANException;

   public abstract int updateCertification( final CertificationVO certificationVO ) throws KANException;

   public abstract int deleteCertification( final CertificationVO certificationVO ) throws KANException;

   public abstract List<Object> getCertificationBaseViewsByAccountId(final String accountId );
   
   public abstract List< Object > getCertificationVOsByAccountId( final String accountId );
}
