package com.kan.base.domain.workflow;

import java.io.Serializable;

public class WorkflowApprovedChainVO implements Serializable
{

   // Serial Version UID
   private static final long serialVersionUID = -9185537922150101631L;

   private String stepIndex;

   private String approvedChainZH;

   private String approvedChainEN;

   private String handleDate;

   private String description;

   private String status;

   public String getStepIndex()
   {
      return stepIndex;
   }

   public void setStepIndex( String stepIndex )
   {
      this.stepIndex = stepIndex;
   }

   public String getApprovedChainZH()
   {
      return approvedChainZH;
   }

   public void setApprovedChainZH( String approvedChainZH )
   {
      this.approvedChainZH = approvedChainZH;
   }

   public String getApprovedChainEN()
   {
      return approvedChainEN;
   }

   public void setApprovedChainEN( String approvedChainEN )
   {
      this.approvedChainEN = approvedChainEN;
   }

   public String getHandleDate()
   {
      return handleDate;
   }

   public void setHandleDate( String handleDate )
   {
      this.handleDate = handleDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus( String status )
   {
      this.status = status;
   }

}
