package com.kan.hro.domain.biz.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SBAdjustmentDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 295464364574237018L;

   // Social Benefit Adjustment Header
   private SBAdjustmentHeaderVO sbAdjustmentHeaderVO;

   // Social Benefit Adjustment Detail
   private List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs = new ArrayList< SBAdjustmentDetailVO >();

   public final SBAdjustmentHeaderVO getSbAdjustmentHeaderVO()
   {
      return sbAdjustmentHeaderVO;
   }

   public final void setSbAdjustmentHeaderVO( SBAdjustmentHeaderVO sbAdjustmentHeaderVO )
   {
      this.sbAdjustmentHeaderVO = sbAdjustmentHeaderVO;
   }

   public final List< SBAdjustmentDetailVO > getSbAdjustmentDetailVOs()
   {
      return sbAdjustmentDetailVOs;
   }

   public final void setSbAdjustmentDetailVOs( List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs )
   {
      this.sbAdjustmentDetailVOs = sbAdjustmentDetailVOs;
   }

}
