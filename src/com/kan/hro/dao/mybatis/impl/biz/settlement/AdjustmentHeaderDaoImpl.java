package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;

public class AdjustmentHeaderDaoImpl extends Context implements AdjustmentHeaderDao
{
   private EmployeeDao employeeDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   @Override
   public int countAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countAdjustmentHeaderVOsByCondition", adjustmentHeaderVO );
   }

   @Override
   public List< Object > getAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return selectList( "getAdjustmentHeaderVOsByCondition", adjustmentHeaderVO );
   }

   @Override
   public List< Object > getAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAdjustmentHeaderVOsByCondition", adjustmentHeaderVO, rowBounds );
   }

   @Override
   public AdjustmentHeaderVO getAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return ( AdjustmentHeaderVO ) select( "getAdjustmentHeaderVOByAdjustmentHeaderId", adjustmentHeaderId );
   }

   @Override
   public int updateAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return update( "updateAdjustmentHeader", adjustmentHeaderVO );
   }

   @Override
   public int insertAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return insert( "insertAdjustmentHeader", adjustmentHeaderVO );
   }

   @Override
   public int deleteAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return delete( "deleteAdjustmentHeader", adjustmentHeaderVO );
   }

   @Override
   public List< Object > getAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getAdjustmentHeaderVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getPaymentAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return selectList( "getPAdjustmentHeaderVOsByCondition", adjustmentHeaderVO );
   }

}
