package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;

public class OTHeaderDaoImpl extends Context implements OTHeaderDao
{

   @Override
   public int countOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countOTHeaderVOsByCondition", otHeaderVO );
   }

   @Override
   public List< Object > getOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return selectList( "getOTHeaderVOsByCondition", otHeaderVO );
   }

   @Override
   public List< Object > getOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOTHeaderVOsByCondition", otHeaderVO, rowBounds );
   }

   @Override
   public OTHeaderVO getOTHeaderVOByOTHeaderId( final String otHeaderId ) throws KANException
   {
      return ( OTHeaderVO ) select( "getOTHeaderVOByOTHeaderId", otHeaderId );
   }

   @Override
   public int insertOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return insert( "insertOTHeader", otHeaderVO );
   }

   @Override
   public int updateOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return update( "updateOTHeader", otHeaderVO );
   }

   @Override
   public int deleteOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return delete( "deleteOTHeader", otHeaderVO );
   }

   @Override
   public int count_OTUnread( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( Integer ) select( "count_OTUnread", otHeaderVO );
   }

   @Override
   public int read_OT( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return update( "read_OT", otHeaderVO );
   }

   @Override
   public List< Object > exportOTDetailByCondition( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return selectList( "exportOTDetailByCondition", otHeaderVO );
   }

   @Override
   public OTHeaderVO getOTHeaderVOByOTImportHeaderId( final String otImportHeaderId ) throws KANException
   {
      return ( OTHeaderVO ) select( "getOTHeaderVOByOTImportHeaderId", otImportHeaderId );
   }

}
