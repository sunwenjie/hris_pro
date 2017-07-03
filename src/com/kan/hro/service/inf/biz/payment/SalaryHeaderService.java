package com.kan.hro.service.inf.biz.payment;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.CommonBatchVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;

public interface SalaryHeaderService
{
	
	// Page Flag常量
	   public static String PAGE_FLAG_BATCH = "batch";

	   public static String PAGE_FLAG_CONTRACT = "contract";

	   public static String PAGE_FLAG_HEADER = "header";

	   public static String PAGE_FLAG_DETAIL = "detail";

	   // Status Flag常量
	   public static String STATUS_FLAG_PREVIEW = "preview";

	   public static String STATUS_FLAG_APPROVE = "approve";

	   public static String STATUS_FLAG_CONFIRM = "confirm";

	   public static String STATUS_FLAG_SUBMIT = "submit";
	   
	   
	   
   public abstract PagedListHolder getSalaryHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getSalaryDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SalaryHeaderVO getSalaryHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSalaryHeader( final SalaryHeaderVO salaryHeaderVO ) throws KANException;

   public abstract int updateSalaryHeader( final SalaryHeaderVO salaryHeaderVO ) throws KANException;

   public abstract int deleteSalaryHeader( final String salaryHeaderId ) throws KANException;

   public abstract List< Object > getSalaryHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int submit(CommonBatchVO commonBatchVO) throws KANException;

   public abstract List< SalaryDTO >  getSalaryDTOsByCondition(SalaryHeaderVO salaryHeaderVO) throws KANException;

   public abstract int rollback( CommonBatchVO commonBatchVO ) throws KANException;;

}
