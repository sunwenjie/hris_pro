package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;

public class SalaryDetailDaoImpl extends Context implements SalaryDetailDao
{

   private SalaryHeaderDao salaryHeaderDao;

   public SalaryHeaderDao getSalaryHeaderDao()
   {
      return salaryHeaderDao;
   }

   public void setSalaryHeaderDao( SalaryHeaderDao salaryHeaderDao )
   {
      this.salaryHeaderDao = salaryHeaderDao;
   }

   @Override
   public int countSalaryDetailVOsByCondition( SalaryDetailVO salaryDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSalaryDetailVOsByCondition", salaryDetailVO );
   }

   @Override
   public List< Object > getSalaryDetailVOsByCondition( SalaryDetailVO salaryDetailVO ) throws KANException
   {
      return selectList( "getSalaryDetailVOsByCondition", salaryDetailVO );
   }

   @Override
   public List< Object > getSalaryDetailVOsByCondition( SalaryDetailVO salaryDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSalaryDetailVOsByCondition", salaryDetailVO, rowBounds );
   }

   @Override
   public SalaryDetailVO getSalaryDetailVOByDetailId( String detailId ) throws KANException
   {
      return ( SalaryDetailVO ) select( "getSalaryDetailVOByDetailId", detailId );
   }

   @Override
   public int insertSalaryDetail( SalaryDetailVO salaryDetailVO ) throws KANException
   {
      encodeNumber( salaryDetailVO );
      return insert( "insertSalaryDetail", salaryDetailVO );
   }

   @Override
   public int updateSalaryDetail( SalaryDetailVO salaryDetailVO ) throws KANException
   {
      encodeNumber( salaryDetailVO );
      return update( "updateSalaryDetail", salaryDetailVO );
   }

   @Override
   public int deleteSalaryDetail( String salaryDetailId ) throws KANException
   {
      return delete( "deleteSalaryDetail", salaryDetailId );
   }

   @Override
   public List< Object > getSalaryDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return selectList( "getSalaryDetailVOsByHeaderId", headerId );
   }

   @Override
   public int deleteSalaryDetailByHeaderIds( List< String > headerIds ) throws KANException
   {
      return delete( "deleteSalaryDetailByHeaderIds", headerIds );
   }

   private void encodeNumber( final SalaryDetailVO salaryDetailVO ) throws KANException
   {
      final SalaryHeaderVO salaryHeaderVO = salaryHeaderDao.getSalaryHeaderVOByHeaderId( salaryDetailVO.getSalaryHeaderId() );
      if ( salaryHeaderVO != null )
      {
         final Double increment = Double.parseDouble( Cryptogram.getIncrement( Cryptogram.getPublicCode( salaryHeaderVO.getEmployeeId() ) ) );
         salaryDetailVO.setBillAmountCompany( String.valueOf( increment + Double.parseDouble( salaryDetailVO.getBillAmountCompany() ) ) );
         salaryDetailVO.setBillAmountPersonal( String.valueOf( increment + Double.parseDouble( salaryDetailVO.getBillAmountPersonal() ) ) );
         salaryDetailVO.setCostAmountCompany( String.valueOf( increment + Double.parseDouble( salaryDetailVO.getCostAmountCompany() ) ) );
         salaryDetailVO.setCostAmountPersonal( String.valueOf( increment + Double.parseDouble( salaryDetailVO.getCostAmountPersonal() ) ) );
      }
   }

   /**
    * 导入后加密
    */
   @Override
   public int updateSalaryDetailAfterImport( String batchId ) throws KANException
   {
      return update( "updateSalaryDetailAfterImport", batchId );
   }

}
