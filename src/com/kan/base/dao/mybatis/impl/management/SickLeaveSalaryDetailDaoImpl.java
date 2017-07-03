package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;


import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.SickLeaveSalaryDetailDao;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.util.KANException;

public class SickLeaveSalaryDetailDaoImpl extends Context implements SickLeaveSalaryDetailDao 
{

   @Override
   public int countSickLeaveSalaryDetailVOsByCondition( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSickLeaveSalaryDetailVOsByCondition", sickLeaveSalaryDetailVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryDetailVOsByCondition( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return selectList( "getSickLeaveSalaryDetailVOsByCondition", sickLeaveSalaryDetailVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryDetailVOsByCondition( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSickLeaveSalaryDetailVOsByCondition", sickLeaveSalaryDetailVO, rowBounds );
   }

   @Override
   public SickLeaveSalaryDetailVO getSickLeaveSalaryDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( SickLeaveSalaryDetailVO ) select( "getSickLeaveSalaryDetailVOByDetailId", detailId );
   }

   @Override
   public int insertSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return insert( "insertSickLeaveSalaryDetail", sickLeaveSalaryDetailVO );
   }

   @Override
   public int updateSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return update( "updateSickLeaveSalaryDetail", sickLeaveSalaryDetailVO );
   }

   public int deleteSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO ) throws KANException
   {
      return delete( "deleteSickLeaveSalaryDetail", sickLeaveSalaryDetailVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getSickLeaveSalaryDetailVOsByHeaderId", headerId );
   }

   @Override
   public List< Object > getSickLeaveSalaryDetailByHeaderId(final String headerId ) throws KANException
   {
      return selectList( "getSickLeaveSalaryDetailByHeaderId", headerId );
   }


}
