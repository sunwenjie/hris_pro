package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.SickLeaveSalaryHeaderDao;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.util.KANException;

public class SickLeaveSalaryHeaderDaoImpl extends Context implements SickLeaveSalaryHeaderDao
{

   @Override
   public int countSickLeaveSalaryHeaderVOsByCondition( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSickLeaveSalaryHeaderVOsByCondition", sickLeaveSalaryHeaderVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryHeaderVOsByCondition( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      return selectList( "getSickLeaveSalaryHeaderVOsByCondition", sickLeaveSalaryHeaderVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryHeaderVOsByCondition( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSickLeaveSalaryHeaderVOsByCondition", sickLeaveSalaryHeaderVO, rowBounds );
   }

   @Override
   public SickLeaveSalaryHeaderVO getSickLeaveSalaryHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( SickLeaveSalaryHeaderVO ) select( "getSickLeaveSalaryHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertSickLeaveSalaryHeader( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      return insert( "insertSickLeaveSalaryHeader", sickLeaveSalaryHeaderVO );
   }

   @Override
   public int updateSickLeaveSalaryHeader( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      return update( "updateSickLeaveSalaryHeader", sickLeaveSalaryHeaderVO );
   }

   @Override
   public int deleteSickLeaveSalaryHeader( final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      return delete( "deleteSickLeaveSalaryHeader", sickLeaveSalaryHeaderVO );
   }

   @Override
   public List< Object > getSickLeaveSalaryHeaderVOsByAccountId( final String accoutnId ) throws KANException
   {
      return selectList( "getSickLeaveSalaryHeaderVOsByAccountId", accoutnId );
   }

}
