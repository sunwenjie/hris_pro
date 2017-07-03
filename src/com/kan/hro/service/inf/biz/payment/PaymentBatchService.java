package com.kan.hro.service.inf.biz.payment;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;

public interface PaymentBatchService
{
   // Page Flag常量
   public static String PAGE_FLAG_BATCH = "batch";

   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";

   // Status Flag常量
   public static String STATUS_FLAG_PREVIEW = "preview";

   public static String STATUS_FLAG_SUBMIT = "submit";

   public static String STATUS_FLAG_ISSUE = "issue";

   public abstract PagedListHolder getPaymentBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract PaymentBatchVO getPaymentBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int updatePaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract int insertPaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract int insertPaymentBatchInHouse( final PaymentBatchVO paymentBatchVO, final BatchTempVO batchTempVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int insertPaymentBatch( final PaymentBatchVO paymentBatchVO, final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int insertPaymentBatch_nt( final PaymentBatchVO paymentBatchVO, final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int rollback( final PaymentBatchVO paymentBatchVO, final String role ) throws KANException;

   public abstract int submit( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract PagedListHolder getPaymentDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException;

}
