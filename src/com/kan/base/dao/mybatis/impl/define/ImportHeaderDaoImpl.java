package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ImportHeaderDao;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.util.KANException;

public class ImportHeaderDaoImpl extends Context implements ImportHeaderDao
{

   @Override
   public int countImportHeaderVOsByCondition( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countImportHeaderVOsByCondition", importHeaderVO );
   }

   @Override
   public List< Object > getImportHeaderVOsByCondition( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      return selectList( "getImportHeaderVOsByCondition", importHeaderVO );
   }

   @Override
   public List< Object > getImportHeaderVOsByCondition( final ImportHeaderVO importHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getImportHeaderVOsByCondition", importHeaderVO, rowBounds );
   }
   
   @Override
   public List< Object > getImportHeaderVOsByAccountId( final String accountId) throws KANException
   {
      return selectList( "getImportHeaderVOsByAccountId", accountId );
   }

   @Override
   public ImportHeaderVO getImportHeaderVOByImportHeaderId( final String importHeaderId ) throws KANException
   {
      return ( ImportHeaderVO ) select( "getImportHeaderVOByImportHeaderId", importHeaderId );
   }

   @Override
   public int insertImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      return insert( "insertImportHeader", importHeaderVO );
   }

   @Override
   public int updateImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      return update( "updateImportHeader", importHeaderVO );
   }

   @Override
   public int deleteImportHeader( final String importHeaderId ) throws KANException
   {
      return delete( "deleteImportHeader", importHeaderId );
   }

}
