package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ImportDetailDao;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.util.KANException;

public class ImportDetailDaoImpl extends Context implements ImportDetailDao
{

   @Override
   public int countImportDetailVOsByCondition( final ImportDetailVO importDetailVO ) throws KANException
   {
      return ( Integer ) select( "countImportDetailVOsByCondition", importDetailVO );
   }

   @Override
   public List< Object > getImportDetailVOsByCondition( final ImportDetailVO importDetailVO ) throws KANException
   {
      return selectList( "getImportDetailVOsByCondition", importDetailVO );
   }

   @Override
   public List< Object > getImportDetailVOsByCondition( final ImportDetailVO importDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getImportDetailVOsByCondition", importDetailVO, rowBounds );
   }

   @Override
   public ImportDetailVO getImportDetailVOByImportDetailId( final String importDetailId ) throws KANException
   {
      return ( ImportDetailVO ) select( "getImportDetailVOByImportDetailId", importDetailId );
   }

   @Override
   public int insertImportDetail( final ImportDetailVO importDetailVO ) throws KANException
   {
      return insert( "insertImportDetail", importDetailVO );
   }

   @Override
   public int updateImportDetail( final ImportDetailVO importDetailVO ) throws KANException
   {
      return update( "updateImportDetail", importDetailVO );
   }

   @Override
   public int deleteImportDetail( final String defImportDetailId ) throws KANException
   {
      return delete( "deleteImportDetail", defImportDetailId );
   }

   @Override
   public List< Object > getImportDetailVOsByImportHeaderId( final String importHeaderId ) throws KANException
   {
      return selectList( "getImportDetailVOsByImportHeaderId", importHeaderId );
   }

}
