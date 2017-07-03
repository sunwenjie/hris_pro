package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBDetailTempDao;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;

public class SBDetailTempDaoImpl extends Context implements SBDetailTempDao
{

   @Override
   public int countSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return ( Integer ) select( "countSBDetailTempVOsByCondition", sbDetailTempVO );
   }

   @Override
   public List< Object > getSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return selectList( "getSBDetailTempVOsByCondition", sbDetailTempVO );
   }

   @Override
   public List< Object > getSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBDetailTempVOsByCondition", sbDetailTempVO, rowBounds );
   }

   @Override
   public SBDetailTempVO getSBDetailTempVOByDetailId( final String detailId ) throws KANException
   {
      return ( SBDetailTempVO ) select( "getSBDetailTempVOByDetailId", detailId );
   }

   @Override
   public int updateSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return update( "updateSBDetailTemp", sbDetailTempVO );
   }

   @Override
   public int insertSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return insert( "insertSBDetailTemp", sbDetailTempVO );
   }

   @Override
   public int deleteSBDetailTemp( final String sbDetailId ) throws KANException
   {
      return delete( "deleteSBDetailTemp", sbDetailId );
   }

   @Override
   public List< Object > getSBDetailTempVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getSBDetailTempVOsByHeaderId", headerId );
   }

   @Override
   public List< Object > getSBDetailTempVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getSBDetailTempVOsByBatchId", batchId );
   }

}
