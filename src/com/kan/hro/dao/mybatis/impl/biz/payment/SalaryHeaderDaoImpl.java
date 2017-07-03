package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;

public class SalaryHeaderDaoImpl extends Context implements SalaryHeaderDao
{

   @Override
   public int countSalaryHeaderVOsByCondition( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSalaryHeaderVOsByCondition", salaryHeaderVO );
   }

   @Override
   public List< Object > getSalaryHeaderVOsByCondition( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      return selectList( "getSalaryHeaderVOsByCondition", salaryHeaderVO );
   }

   @Override
   public List< Object > getSalaryHeaderVOsByCondition( SalaryHeaderVO salaryHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSalaryHeaderVOsByCondition", salaryHeaderVO, rowBounds );
   }

   @Override
   public SalaryHeaderVO getSalaryHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( SalaryHeaderVO ) select( "getSalaryHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertSalaryHeader( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      encodeNumber( salaryHeaderVO );
      return insert( "insertSalaryHeader", salaryHeaderVO );
   }

   @Override
   public int updateSalaryHeader( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      encodeNumber( salaryHeaderVO );
      return update( "updateSalaryHeader", salaryHeaderVO );
   }

   @Override
   public int deleteSalaryHeader( String salaryHeaderId ) throws KANException
   {
      return delete( "deleteSalaryHeader", salaryHeaderId );
   }

   @Override
   public List< Object > getSalaryHeaderVOsByBatchId( String batchId ) throws KANException
   {
      return selectList( "getSalaryHeaderVOsByBatchId", batchId );
   }

   @Override
   public int deleteSalaryHeaderByHeaderIds( List< String > headerIds ) throws KANException
   {
      return delete( "deleteSalaryHeaderByHeaderIds", headerIds );
   }

   private void encodeNumber( final SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      final Double increment = Double.parseDouble( Cryptogram.getIncrement( Cryptogram.getPublicCode( salaryHeaderVO.getEmployeeId() ) ) );
      salaryHeaderVO.setBillAmountCompany( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getBillAmountCompany() ) ) );
      salaryHeaderVO.setBillAmountPersonal( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getBillAmountPersonal() ) ) );
      salaryHeaderVO.setCostAmountCompany( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getCostAmountCompany() ) ) );
      salaryHeaderVO.setCostAmountPersonal( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getCostAmountPersonal() ) ) );
      salaryHeaderVO.setTaxAmountPersonal( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getTaxAmountPersonal() ) ) );
      salaryHeaderVO.setAddtionalBillAmountPersonal( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getAddtionalBillAmountPersonal() ) ) );
      salaryHeaderVO.setEstimateSalary( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getEstimateSalary() ) ) );
      salaryHeaderVO.setActualSalary( String.valueOf( increment + Double.parseDouble( salaryHeaderVO.getActualSalary() ) ) );
   }

   /**
    * 导入后加密
    */
   @Override
   public int updateSalaryHeaderAfterImport( String batchId ) throws KANException
   {
      return update( "updateSalaryHeaderAfterImport", batchId );
   }

}
