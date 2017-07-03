package com.kan.base.domain.workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowActualDTO
{
   private WorkflowActualVO workflowActualVO = new WorkflowActualVO();
   
   private List<WorkflowActualStepsVO> workflowActualStepsVOs = new ArrayList<WorkflowActualStepsVO>();

   public WorkflowActualVO getWorkflowActualVO()
   {
      return workflowActualVO;
   }

   public void setWorkflowActualVO( WorkflowActualVO workflowActualVO )
   {
      this.workflowActualVO = workflowActualVO;
   }

   public List< WorkflowActualStepsVO > getWorkflowActualStepsVOs()
   {
      return workflowActualStepsVOs;
   }

   public void setWorkflowActualStepsVOs( List< WorkflowActualStepsVO > workflowActualStepsVOs )
   {
      this.workflowActualStepsVOs = workflowActualStepsVOs;
   }
   
   
}
