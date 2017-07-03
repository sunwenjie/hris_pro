package com.kan.base.domain.workflow;

import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.system.WorkflowModuleVO;

public class WorkflowModuleDTO
{
   private WorkflowModuleVO workflowModuleVO = new WorkflowModuleVO();

   private List< WorkflowDefineDTO > workflowDefineDTO = new ArrayList< WorkflowDefineDTO >();

   public WorkflowModuleVO getWorkflowModuleVO()
   {
      return workflowModuleVO;
   }

   public void setWorkflowModuleVO( WorkflowModuleVO workflowModuleVO )
   {
      this.workflowModuleVO = workflowModuleVO;
   }

   public List< WorkflowDefineDTO > getWorkflowDefineDTO()
   {
      return workflowDefineDTO;
   }

   public void setWorkflowDefineDTO( List< WorkflowDefineDTO > workflowDefineDTO )
   {
      this.workflowDefineDTO = workflowDefineDTO;
   }

}
