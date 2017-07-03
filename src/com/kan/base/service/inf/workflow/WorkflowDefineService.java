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
   * ͨ��defineId �߼�ɾ��WorkflowDefine���� )  
   * @param   modifyUserId  �޸��û���id 
   * @param  defineId   ����������id  defineId������һ����Ҳ����������Ҳ������һ������
   * @Exception �쳣����  
   * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public abstract void deleteWorkflowDefineByDefineId( final String modifyUserId , final String ...defineId) throws KANException;
}
