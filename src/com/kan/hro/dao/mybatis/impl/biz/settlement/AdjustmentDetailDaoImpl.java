package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;

public class AdjustmentDetailDaoImpl extends Context implements AdjustmentDetailDao
{
   private EmployeeDao employeeDao;

   private AdjustmentHeaderDao adjustmentHeaderDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final AdjustmentHeaderDao getAdjustmentHeaderDao()
   {
      return adjustmentHeaderDao;
   }

   public final void setAdjustmentHeaderDao( AdjustmentHeaderDao adjustmentHeaderDao )
   {
      this.adjustmentHeaderDao = adjustmentHeaderDao;
   }

   @Override
   public int countAdjustmentDetailVOsByCondition( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      return ( Integer ) select( "countAdjustmentDetailVOsByCondition", adjustmentDetailVO );
   }

   @Override
   public List< Object > getAdjustmentDetailVOsByCondition( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      return selectList( "getAdjustmentDetailVOsByCondition", adjustmentDetailVO );
   }

   @Override
   public List< Object > getAdjustmentDetailVOsByCondition( final AdjustmentDetailVO adjustmentDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAdjustmentDetailVOsByCondition", adjustmentDetailVO, rowBounds );
   }

   @Override
   public AdjustmentDetailVO getAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException
   {
      return ( AdjustmentDetailVO ) select( "getAdjustmentDetailVOByAdjustmentDetailId", adjustmentDetailId );
   }

   @Override
   public int insertAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      return insert( "insertAdjustmentDetail", adjustmentDetailVO );
   }

   @Override
   public int updateAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      return update( "updateAdjustmentDetail", adjustmentDetailVO );
   }

   @Override
   public int deleteAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException
   {
      return delete( "deleteAdjustmentDetail", adjustmentDetailVO );
   }

   @Override
   public List< Object > getAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return selectList( "getAdjustmentDetailVOsByAdjustmentHeaderId", adjustmentHeaderId );
   }

}
