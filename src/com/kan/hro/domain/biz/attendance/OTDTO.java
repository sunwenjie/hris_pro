package com.kan.hro.domain.biz.attendance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OTDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -783104268342972552L;

   // OTHeaderVO 
   private OTHeaderVO otHeaderVO;

   // OTDetailVO - DTO
   private List< OTDetailVO > otDetailVOs = new ArrayList< OTDetailVO >();

   public final OTHeaderVO getOtHeaderVO()
   {
      return otHeaderVO;
   }

   public final void setOtHeaderVO( OTHeaderVO otHeaderVO )
   {
      this.otHeaderVO = otHeaderVO;
   }

   public final List< OTDetailVO > getOtDetailVOs()
   {
      return otDetailVOs;
   }

   public final void setOtDetailVOs( List< OTDetailVO > otDetailVOs )
   {
      this.otDetailVOs = otDetailVOs;
   }

}
