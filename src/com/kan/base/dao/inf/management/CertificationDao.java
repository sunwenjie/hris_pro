package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.CertificationVO;
import com.kan.base.util.KANException;

public interface CertificationDao
{
   public abstract int countCertificationVOsByCondition( final CertificationVO certificationVO ) throws KANException;

   public abstract List< Object > getCertificationVOsByCondition( final CertificationVO certificationVO ) throws KANException;

   public abstract List< Object > getCertificationVOsByCondition( final CertificationVO certificationVO, final RowBounds rowBounds ) throws KANException;

   public abstract CertificationVO getCertificationVOByCertificationId( final String certificationId ) throws KANException;

   public abstract int insertCertification( final CertificationVO certificationVO ) throws KANException;

   public abstract int updateCertification( final CertificationVO certificationVO ) throws KANException;

   public abstract int deleteCertification( final CertificationVO certificationVO ) throws KANException;

   public abstract List< Object > getCertificationBaseViewsByAccountId( final String accountId );
   
   public abstract List< Object > getCertificationVOsByAccountId( final String accountId );
}
