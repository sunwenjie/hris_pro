package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.MappingDetailDao;
import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.util.KANException;

public class MappingDetailDaoImpl extends Context implements MappingDetailDao
{

   @Override
   public int countMappingDetailVOsByCondition( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      return ( Integer ) select( "countMappingDetailVOsByCondition", mappingDetailVO );
   }

   @Override
   public List< Object > getMappingDetailVOsByCondition( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      return selectList( "getMappingDetailVOsByCondition", mappingDetailVO );
   }

   @Override
   public List< Object > getMappingDetailVOsByCondition( final MappingDetailVO mappingDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMappingDetailVOsByCondition", mappingDetailVO, rowBounds );
   }

   @Override
   public MappingDetailVO getMappingDetailVOByMappingDetailId( final String mappingDetailId ) throws KANException
   {
      return ( MappingDetailVO ) select( "getMappingDetailVOByMappingDetailId", mappingDetailId );
   }

   @Override
   public int insertMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      return insert( "insertMappingDetail", mappingDetailVO );
   }

   @Override
   public int updateMappingDetail( final MappingDetailVO mappingDetailVO ) throws KANException
   {
      return update( "updateMappingDetail", mappingDetailVO );
   }

   @Override
   public int deleteMappingDetail( final String defMappingDetailId ) throws KANException
   {
      return delete( "deleteMappingDetail", defMappingDetailId );
   }

   @Override
   public List< Object > getMappingDetailVOsByMappingHeaderId( final String mappingHeaderId ) throws KANException
   {
      return selectList( "getMappingDetailVOsByMappingHeaderId", mappingHeaderId );
   }

}
