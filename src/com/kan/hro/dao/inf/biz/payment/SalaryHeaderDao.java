package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;

public interface SalaryHeaderDao
{
   public abstract int countSalaryHeaderVOsByCondition( final SalaryHeaderVO salaryHeaderVO ) throws KANException;

   public abstract List< Object > getSalaryHeaderVOsByCondition( final SalaryHeaderVO salaryHeaderVO ) throws KANException;

   public abstract List< Object > getSalaryHeaderVOsByCondition( final SalaryHeaderVO salaryHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract SalaryHeaderVO getSalaryHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSalaryHeader( final SalaryHeaderVO salaryHeaderVO ) throws KANException;

   public abstract int updateSalaryHeader( final SalaryHeaderVO salaryHeaderVO ) throws KANException;

   public abstract int deleteSalaryHeader( final String salaryHeaderId ) throws KANException;

   public abstract List< Object > getSalaryHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int deleteSalaryHeaderByHeaderIds(  List< String> headerIds  ) throws KANException;
   
   public abstract int updateSalaryHeaderAfterImport( final String batchId ) throws KANException;
}
