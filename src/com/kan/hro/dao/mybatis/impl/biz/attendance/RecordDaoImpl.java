package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.RecordDao;
import com.kan.hro.domain.biz.attendance.RecordVO;

public class RecordDaoImpl extends Context implements RecordDao
{

   @Override
   public int countRecordVOsByCondition( final RecordVO recordVO ) throws KANException
   {
      return ( Integer ) select( "countRecordVOsByCondition", recordVO );
   }

   @Override
   public List< Object > getRecordVOsByCondition( final RecordVO recordVO ) throws KANException
   {
      return selectList( "getRecordVOsByCondition", recordVO );
   }

   @Override
   public List< Object > getRecordVOsByCondition( final RecordVO recordVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getRecordVOsByCondition", recordVO, rowBounds );
   }

   @Override
   public RecordVO getRecordVOByRecordId( final String recordId ) throws KANException
   {
      return ( RecordVO ) select( "getRecordVOByRecordId", recordId );
   }

   @Override
   public int insertRecord( final RecordVO recordVO ) throws KANException
   {
      return insert( "insertRecord", recordVO );
   }

   @Override
   public int updateRecord( final RecordVO recordVO ) throws KANException
   {
      return update( "updateRecord", recordVO );
   }

   @Override
   public int deleteRecord( final RecordVO recordVO ) throws KANException
   {
      return delete( "deleteRecord", recordVO );
   }

}
