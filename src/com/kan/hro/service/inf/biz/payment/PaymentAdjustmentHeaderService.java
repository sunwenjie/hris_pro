package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;

public interface PaymentAdjustmentHeaderService
{
   // Page Flag常量
   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";

   // Status Flag常量
   // 调整
   public static String STATUS_FLAG_PREVIEW = "preview";

   public static String STATUS_FLAG_APPROVE = "approve";

   public static String STATUS_FLAG_CONFIRM = "confirm";

   // 确认
   public static String STATUS_FLAG_SUBMIT = "submit";

   public abstract PagedListHolder getPaymentAdjustmentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PaymentAdjustmentHeaderVO getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract int updatePaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract int insertPaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract int deletePaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

}
