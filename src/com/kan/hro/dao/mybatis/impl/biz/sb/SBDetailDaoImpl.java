package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public class SBDetailDaoImpl extends Context implements SBDetailDao
{
   private EmployeeDao employeeDao;

   private SBHeaderDao sbHeaderDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final SBHeaderDao getSbHeaderDao()
   {
      return sbHeaderDao;
   }

   public final void setSbHeaderDao( SBHeaderDao sbHeaderDao )
   {
      this.sbHeaderDao = sbHeaderDao;
   }

   @Override
   public int countSBDetailVOsByCondition( final SBDetailVO sbDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSBDetailVOsByCondition", sbDetailVO );
   }

   @Override
   public List< Object > getSBDetailVOsByCondition( final SBDetailVO sbDetailVO ) throws KANException
   {
      return selectList( "getSBDetailVOsByCondition", sbDetailVO );
   }

   @Override
   public List< Object > getSBDetailVOsByCondition( final SBDetailVO sbDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBDetailVOsByCondition", sbDetailVO, rowBounds );
   }

   @Override
   public SBDetailVO getSBDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( SBDetailVO ) select( "getSBDetailVOByDetailId", detailId );
   }

   @Override
   public int updateSBDetail( final SBDetailVO sbDetailVO ) throws KANException
   {
      return update( "updateSBDetail", sbDetailVO );
   }

   @Override
   public int insertSBDetail( final SBDetailVO sbDetailVO ) throws KANException
   {
      return insert( "insertSBDetail", sbDetailVO );
   }

   @Override
   public int deleteSBDetail( final String sbDetailId ) throws KANException
   {
      return delete( "deleteSBDetail", sbDetailId );
   }

   @Override
   public List< Object > getSBDetailVOsByHeaderId( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getSBDetailVOsByHeaderId", sbHeaderVO );
   }

   @Override
   public List< Object > getSBDetailVOsByBatchId( final SBBatchVO sbBatchVO ) throws KANException
   {
      return selectList( "getSBDetailVOsByBatchId", sbBatchVO );
   }

   @Override
   public List< Object > getSBDetailVOsBySbHeaderCond( SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getSBDetailVOsBySbHeaderCond", sbHeaderVO );
   }

   @Override
   public int getMinSBDetailStatusBySBHeaderId( String sbHeaderId ) throws KANException
   {
      return 0;
   }

}
