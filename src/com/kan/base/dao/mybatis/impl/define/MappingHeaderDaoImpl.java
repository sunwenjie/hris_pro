package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.MappingHeaderDao;
import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.util.KANException;

public class MappingHeaderDaoImpl extends Context implements MappingHeaderDao
{

   @Override
   public int countMappingHeaderVOsByCondition( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countMappingHeaderVOsByCondition", mappingHeaderVO );
   }

   @Override
   public List< Object > getMappingHeaderVOsByCondition( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      return selectList( "getMappingHeaderVOsByCondition", mappingHeaderVO );
   }

   @Override
   public List< Object > getMappingHeaderVOsByCondition( final MappingHeaderVO mappingHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMappingHeaderVOsByCondition", mappingHeaderVO, rowBounds );
   }

   @Override
   public MappingHeaderVO getMappingHeaderVOByMappingHeaderId( final String mappingHeaderId ) throws KANException
   {
      return ( MappingHeaderVO ) select( "getMappingHeaderVOByMappingHeaderId", mappingHeaderId );
   }

   @Override
   public int insertMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      return insert( "insertMappingHeader", mappingHeaderVO );
   }

   @Override
   public int updateMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      return update( "updateMappingHeader", mappingHeaderVO );
   }

   @Override
   public int deleteMappingHeader( final String mappingHeaderId ) throws KANException
   {
      return delete( "deleteMappingHeader", mappingHeaderId );
   }

   @Override
   public List< Object > getMappingHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getMappingHeaderVOsByAccountId", accountId );
   }

}
