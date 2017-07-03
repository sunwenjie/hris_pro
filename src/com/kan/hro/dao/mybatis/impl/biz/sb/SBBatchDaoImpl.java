package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.domain.biz.sb.SBBatchVO;

public class SBBatchDaoImpl extends Context implements SBBatchDao
{

   @Override
   public int countSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException
   {
      return ( Integer ) select( "countSBBatchVOsByCondition", sbBatchVO );
   }

   @Override
   public List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException
   {
      return selectList( "getSBBatchVOsByCondition", sbBatchVO );
   }

   @Override
   public List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBBatchVOsByCondition", sbBatchVO, rowBounds );
   }

   @Override
   public SBBatchVO getSBBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( SBBatchVO ) select( "getSBBatchVOByBatchId", batchId );
   }

   @Override
   public int updateSBBatch( final SBBatchVO sbBatchVO ) throws KANException
   {
      return update( "updateSBBatch", sbBatchVO );
   }

   @Override
   public int insertSBBatch( final SBBatchVO sbBatchVO ) throws KANException
   {
      return insert( "insertSBBatch", sbBatchVO );
   }

   @Override
   public int deleteSBBatch( final String sbBatchId ) throws KANException
   {
      return delete( "deleteSBBatch", sbBatchId );
   }

   @Override
   public int getSBToApplyForMoreStatusCountByBatchIds( final String[] batchId ) throws KANException
   {
      return ( Integer ) select( "getSBToApplyForMoreStatusCountByBatchIds", batchId );
   }
   
   @Override
   public int getSBToApplyForResigningStatusCountByBatchIds( final String[] batchId ) throws KANException
   {
      return ( Integer ) select( "getSBToApplyForResigningStatusCountByBatchIds", batchId );
   }

   @Override
   public void updateSBStatusTONoSocialBenefitByBatchId( final String[] batchId ) throws KANException
   {
      update( "updateSBStatusTONoSocialBenefitByBatchId", batchId );
   }

   @Override
   public void updateSBStatusTONormalByBatchId( final String[] batchId ) throws KANException
   {
      update( "updateSBStatusTONormalByBatchId", batchId );
   }
   
   @Override
   public int updateSBBatchStatus( final SBBatchVO sbBatchVO ) throws KANException
   {
      return update( "updateSBBatchStatus", sbBatchVO );
   }
   
   @Override
   public int updateSBHeaderStatus( final SBBatchVO sbBatchVO ) throws KANException
   {
      return update( "updateSBHeaderStatus", sbBatchVO );
   }
   
   @Override
   public int updateSBDetailStatus( final SBBatchVO sbBatchVO ) throws KANException
   {
      return update( "updateSBDetailStatus", sbBatchVO );
   }
   
   @Override
   public String getSBBatchId( final SBBatchVO sbBatchVO ) throws KANException
   {
      return ( String ) select( "getSBBatchId", sbBatchVO );
   }
   
   
}
