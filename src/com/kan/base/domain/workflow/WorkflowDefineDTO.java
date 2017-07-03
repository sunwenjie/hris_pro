package com.kan.base.domain.workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowDefineDTO
{
   // 工作流主表 - WorkflowDefineVO
   private WorkflowDefineVO workflowDefineVO = new WorkflowDefineVO();

   // 工作流从表 触发条件 WorkflowDefineRequirementsVOs
   private List< WorkflowDefineRequirementsVO > workflowDefineRequirementsVOs = new ArrayList< WorkflowDefineRequirementsVO >();

   // 工作流从表 补充条件 WorkflowDefineStepsVOs
   private List< WorkflowDefineStepsVO > workflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

   public WorkflowDefineVO getWorkflowDefineVO()
   {
      return workflowDefineVO;
   }

   public void setWorkflowDefineVO( WorkflowDefineVO workflowDefineVO )
   {
      this.workflowDefineVO = workflowDefineVO;
   }

   public List< WorkflowDefineStepsVO > getWorkflowDefineStepsVOs()
   {
      return workflowDefineStepsVOs;
   }

   public void setWorkflowDefineStepsVOs( List< WorkflowDefineStepsVO > workflowDefineStepsVOs )
   {
      this.workflowDefineStepsVOs = workflowDefineStepsVOs;
   }

   public List< WorkflowDefineRequirementsVO > getWorkflowDefineRequirementsVOs()
   {
      return workflowDefineRequirementsVOs;
   }

   public void setWorkflowDefineRequirementsVOs( List< WorkflowDefineRequirementsVO > workflowDefineRequirementsVOs )
   {
      this.workflowDefineRequirementsVOs = workflowDefineRequirementsVOs;
   }

}
