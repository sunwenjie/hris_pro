package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;

public class SBAdjustmentDetailDaoImpl extends Context implements SBAdjustmentDetailDao
{
   private EmployeeDao employeeDao;

   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public final void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   @Override
   public int countSBAdjustmentDetailVOsByCondition( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSBAdjustmentDetailVOsByCondition", sbAdjustmentDetailVO );
   }

   @Override
   public List< Object > getSBAdjustmentDetailVOsByCondition( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      return selectList( "getSBAdjustmentDetailVOsByCondition", sbAdjustmentDetailVO );
   }

   @Override
   public List< Object > getSBAdjustmentDetailVOsByCondition( final SBAdjustmentDetailVO sbAdjustmentDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBAdjustmentDetailVOsByCondition", sbAdjustmentDetailVO, rowBounds );
   }

   @Override
   public SBAdjustmentDetailVO getSBAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException
   {
      return ( SBAdjustmentDetailVO ) select( "getSBAdjustmentDetailVOByAdjustmentDetailId", adjustmentDetailId );
   }

   @Override
   public int updateSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      return update( "updateSBAdjustmentDetail", sbAdjustmentDetailVO );
   }

   @Override
   public int insertSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      return insert( "insertSBAdjustmentDetail", sbAdjustmentDetailVO );
   }

   @Override
   public int deleteSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      return delete( "deleteSBAdjustmentDetail", sbAdjustmentDetailVO );
   }

   @Override
   public List< Object > getSBAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return selectList( "getSBAdjustmentDetailVOsByAdjustmentHeaderId", adjustmentHeaderId );
   }

}
