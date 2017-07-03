package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.CertificationDao;
import com.kan.base.domain.management.CertificationVO;
import com.kan.base.util.KANException;

public class CertificationDaoImpl extends Context implements CertificationDao
{

   @Override
   public int countCertificationVOsByCondition( final CertificationVO certificationVO ) throws KANException
   {
      return ( Integer ) select( "countCertificationVOsByCondition", certificationVO );
   }

   @Override
   public List< Object > getCertificationVOsByCondition( final CertificationVO certificationVO ) throws KANException
   {
      return selectList( "getCertificationVOsByCondition", certificationVO );
   }

   @Override
   public List< Object > getCertificationVOsByCondition( final CertificationVO certificationVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCertificationVOsByCondition", certificationVO, rowBounds );
   }

   @Override
   public CertificationVO getCertificationVOByCertificationId( final String certificationId ) throws KANException
   {
      return ( CertificationVO ) select( "getCertificationVOByCertificationId", certificationId );
   }

   @Override
   public int insertCertification( final CertificationVO certificationVO ) throws KANException
   {
      return insert( "insertCertification", certificationVO );
   }

   @Override
   public int updateCertification( final CertificationVO certificationVO ) throws KANException
   {
      return update( "updateCertification", certificationVO );
   }

   @Override
   public int deleteCertification( final CertificationVO certificationVO ) throws KANException
   {
      return delete( "deleteCertification", certificationVO );
   }

   @Override
   public List< Object > getCertificationBaseViewsByAccountId(final String accountId )
   {
      return selectList( "getCertificationBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getCertificationVOsByAccountId( String accountId )
   {
      return selectList( "getCertificationVOsByAccountId", accountId);
   }

}
