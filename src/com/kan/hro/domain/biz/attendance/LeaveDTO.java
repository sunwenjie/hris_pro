package com.kan.hro.domain.biz.attendance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeaveDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -4491252098055556048L;

   // LeaveHeaderVO 
   private LeaveHeaderVO leaveHeaderVO;

   // LeaveDetailVO - DTO
   private List< LeaveDetailVO > leaveDetailVOs = new ArrayList< LeaveDetailVO >();

   public final LeaveHeaderVO getLeaveHeaderVO()
   {
      return leaveHeaderVO;
   }

   public final void setLeaveHeaderVO( LeaveHeaderVO leaveHeaderVO )
   {
      this.leaveHeaderVO = leaveHeaderVO;
   }

   public final List< LeaveDetailVO > getLeaveDetailVOs()
   {
      return leaveDetailVOs;
   }

   public final void setLeaveDetailVOs( List< LeaveDetailVO > leaveDetailVOs )
   {
      this.leaveDetailVOs = leaveDetailVOs;
   }

}
