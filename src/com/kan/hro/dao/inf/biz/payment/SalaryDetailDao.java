package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;

public interface SalaryDetailDao
{
   public abstract int countSalaryDetailVOsByCondition( final SalaryDetailVO salaryDetailVO ) throws KANException;

   public abstract List< Object > getSalaryDetailVOsByCondition( final SalaryDetailVO salaryDetailVO ) throws KANException;

   public abstract List< Object > getSalaryDetailVOsByCondition( final SalaryDetailVO salaryDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract SalaryDetailVO getSalaryDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSalaryDetail( final SalaryDetailVO salaryDetailVO ) throws KANException;

   public abstract int updateSalaryDetail( final SalaryDetailVO salaryDetailVO ) throws KANException;

   public abstract int deleteSalaryDetail( final String salaryDetailId ) throws KANException;

   public abstract List< Object > getSalaryDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract int deleteSalaryDetailByHeaderIds( List<String>  headerIds ) throws KANException;
   
   public abstract int updateSalaryDetailAfterImport( final String batchId ) throws KANException;
}
