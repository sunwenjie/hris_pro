package com.kan.base.service.inf.workflow;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface WorkflowDefineService
{

   public abstract PagedListHolder getWorkflowDefineVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract WorkflowDefineVO getWorkflowDefineVOByDefineId(final String defineId ) throws KANException;

   public abstract int updateWorkflowDefine( final WorkflowDefineVO workflowDefineVO ) throws KANException;

   public abstract int insertWorkflowDefine( final WorkflowDefineVO workflowDefineVO ) throws KANException;

   public abstract void deleteWorkflowDefine( final WorkflowDefineVO ...workflowDefineVO ) throws KANException;

   /***
    * 
   * 通过defineId 逻辑删除WorkflowDefine对象 )  
   * @param   modifyUserId  修改用户的id 
   * @param  defineId   工作流定义id  defineId可以是一个，也可以是两，也可以是一个数组
   * @Exception 异常对象  
   * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public abstract void deleteWorkflowDefineByDefineId( final String modifyUserId , final String ...defineId) throws KANException;
}
