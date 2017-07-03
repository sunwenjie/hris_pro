package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;

public class SBAdjustmentHeaderDaoImpl extends Context implements SBAdjustmentHeaderDao
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
   public int countSBAdjustmentHeaderVOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSBAdjustmentHeaderVOsByCondition", sbAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getSBAdjustmentHeaderVOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      return selectList( "getSBAdjustmentHeaderVOsByCondition", sbAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getSBAdjustmentHeaderVOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBAdjustmentHeaderVOsByCondition", sbAdjustmentHeaderVO, rowBounds );
   }

   @Override
   public SBAdjustmentHeaderVO getSBAdjustmentHeaderVOByAdjustmentHeaderId( final String sbAdjustmentHeaderId ) throws KANException
   {
      return ( SBAdjustmentHeaderVO ) select( "getSBAdjustmentHeaderVOByAdjustmentHeaderId", sbAdjustmentHeaderId );
   }

   @Override
   public int updateSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( sbAdjustmentHeaderVO.getEmployeeId() );

      // 敏感数据加密
      if ( employeeVO != null )
      {
         if ( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getAmountCompany() ) != null )
         {
            sbAdjustmentHeaderVO.setAmountCompany( sbAdjustmentHeaderVO.getAmountCompany() );
            //            sbAdjustmentHeaderVO.setAmountCompany( Cryptogram.encodeNumber( employeeVO.getPublicCode(), sbAdjustmentHeaderVO.getAmountCompany() ) );
         }

         if ( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getAmountPersonal() ) != null )
         {
            sbAdjustmentHeaderVO.setAmountPersonal( sbAdjustmentHeaderVO.getAmountCompany() );
            //            sbAdjustmentHeaderVO.setAmountPersonal( Cryptogram.encodeNumber( employeeVO.getPublicCode(), sbAdjustmentHeaderVO.getAmountCompany() ) );
         }
      }

      return update( "updateSBAdjustmentHeader", sbAdjustmentHeaderVO );
   }

   @Override
   public int insertSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      return insert( "insertSBAdjustmentHeader", sbAdjustmentHeaderVO );
   }

   @Override
   public int deleteSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      return delete( "deleteSBAdjustmentHeader", sbAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getSBAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getSBAdjustmentHeaderVOsByAccountId", accountId );
   }

}
