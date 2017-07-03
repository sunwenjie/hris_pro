package com.kan.hro.service.inf.biz.payment;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;

public interface PaymentAdjustmentDetailService
{
   // Page Flag常量
   public static String PAGE_FLAG_BATCH = "batch";

   public static String PAGE_FLAG_CONTRACT = "contract";

   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";
   
   // Status Flag常量
   // 调整
   public static String STATUS_FLAG_PREVIEW = "preview";

   public static String STATUS_FLAG_APPROVE = "approve";

   public static String STATUS_FLAG_CONFIRM = "confirm";
   // 确认
   public static String STATUS_FLAG_SUBMIT = "submit";
   
   public abstract PagedListHolder getPaymentAdjustmentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PaymentAdjustmentDetailVO getPaymentAdjustmentDetailVOByAdjustmentDetailId( final String AdjustmentDetailId ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;

   public abstract int updatePaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;

   public abstract int insertPaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;
   
   public abstract int deletePaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;
}
