package com.kan.hro.service.inf.biz.payment;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;

public interface SalaryDetailService
{
   public abstract PagedListHolder getSalaryDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract SalaryDetailVO getSalaryDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateSalaryDetail( final SalaryDetailVO salaryDetailVO ) throws KANException;

   public abstract int insertSalaryDetail( final SalaryDetailVO salaryDetailVO ) throws KANException;

   public abstract int deleteSalaryDetail( final String salaryDetailId ) throws KANException;

   public abstract List< Object > getSalaryDetailVOsByCondition( final SalaryDetailVO salaryDetailVO ) throws KANException;

}
