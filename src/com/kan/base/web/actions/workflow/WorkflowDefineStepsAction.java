package com.kan.base.web.actions.workflow;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.service.inf.workflow.WorkflowDefineStepsService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class WorkflowDefineStepsAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取是否Ajax调用
         final boolean ajax = new Boolean( request.getParameter( "ajax" ) );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获取当前页
         final String page = request.getParameter( "page" );

         // 获取Action Form
         final WorkflowDefineStepsVO workflowDefineStepsVO = ( WorkflowDefineStepsVO ) form;

         // 如果子Action是删除
         if ( workflowDefineStepsVO.getSubAction() != null && workflowDefineStepsVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );

         // 获取主表主键，如若是正常调用，从form中取，否则ajax需解译两次；       
         String defineId = request.getParameter( "defineId" );
         if ( KANUtil.filterEmpty( defineId ) == null )
         {
            defineId = ( ( WorkflowDefineStepsVO ) form ).getDefineId();
         }
         else
         {
            defineId = KANUtil.decodeStringFromAjax( defineId );
         }

         // 如果request中不存在workflowDefineForm
         if ( request.getAttribute( "workflowDefineForm" ) == null )
         {
            // 获取WorkflowDefineVO
            final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( defineId );
            workflowDefineVO.reset( null, request );
            workflowDefineVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "workflowDefineForm", workflowDefineVO );
         }

         // 设置defineId
         workflowDefineStepsVO.setDefineId( defineId );
         // 此处分页代码
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( workflowDefineStepsVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowDefineStepsService.getWorkflowDefineStepsVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );
         //Holder需写入Request对象
         request.setAttribute( "workflowDefineStepsHolder", pagedListHolder );

         // 默认为步骤为1
         workflowDefineStepsVO.setStepIndex( 1 );

         // Ajax Table调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回 JSP
            return mapping.findForward( "listWorkflowDefineStepsTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "listWorkflowDefineStepsInHouse" );
      }
      else
      {
         return mapping.findForward( "listWorkflowDefineSteps" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      //      // 获取该公司的所以职级
      //      final List< MappingVO > positionGrades = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionGrades( request.getLocale().getLanguage() );
      //      request.setAttribute( "positionGrades", positionGrades );

      // 设置Sub Action
      ( ( WorkflowDefineStepsVO ) form ).setStatus( WorkflowDefineStepsVO.TRUE );
      ( ( WorkflowDefineStepsVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageWorkflowDefineSteps" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
            // 获取ActionForm
            final WorkflowDefineStepsVO workflowDefineStepsVO = ( WorkflowDefineStepsVO ) form;
            final String defineId = workflowDefineStepsVO.getDefineId();
            workflowDefineStepsVO.setDefineId( Cryptogram.decodeString( URLDecoder.decode( defineId, "UTF-8" ) ) );
            // 获取登录用户  - 设置创建用户id
            workflowDefineStepsVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            workflowDefineStepsVO.setAccountId( getAccountId( request, response ) );
            workflowDefineStepsVO.setModifyBy( getUserId( request, response ) );

            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getStaffId() ) == null )
            {
               workflowDefineStepsVO.setStaffId( "0" );
            }
            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getPositionId() ) == null )
            {
               workflowDefineStepsVO.setPositionId( "0" );
            }

            // 新建对象
            workflowDefineStepsService.insertWorkflowDefineSteps( workflowDefineStepsVO );

            // 返回添加成功的标记
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_STEPS" );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineStepsVO, Operate.ADD, workflowDefineStepsVO.getStepId(), null );
         }

         // 清空Form条件
         ( ( WorkflowDefineStepsVO ) form ).reset();
         request.getRequestDispatcher( "workflowDefineAction.do?proc=to_objectModify" ).forward( request, response );

         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
         // 获取当前主键
         String workflowDefineStepsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );
         // 获取主键对应对象
         WorkflowDefineStepsVO workflowDefineStepsVO = workflowDefineStepsService.getWorkflowDefineStepsVOByStepsId( workflowDefineStepsId );
         // 刷新对象，初始化对象列表及国际化
         workflowDefineStepsVO.reset( null, request );

         workflowDefineStepsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineStepsForm", workflowDefineStepsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageWorkflowDefineSteps" );
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );

         // 获取当前主键
         final String workflowDefineStepsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );

         // 获取WorkflowDefineStepsVO
         final WorkflowDefineStepsVO workflowDefineStepsVO = workflowDefineStepsService.getWorkflowDefineStepsVOByStepsId( workflowDefineStepsId );

         // 获取WorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineStepsVO.getDefineId() );

         // 刷新对象，初始化对象列表及国际化
         workflowDefineStepsVO.reset( null, request );

         workflowDefineStepsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineForm", workflowDefineVO );
         request.setAttribute( "workflowDefineStepsForm", workflowDefineStepsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageWorkflowDefineStepsForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
            // 获取当前主键
            final String workflowDefineStepsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "GBK" ) );
            // 获取主键对应对象
            final WorkflowDefineStepsVO workflowDefineStepsVO = workflowDefineStepsService.getWorkflowDefineStepsVOByStepsId( workflowDefineStepsId );
            // 装载界面传值
            workflowDefineStepsVO.update( ( WorkflowDefineStepsVO ) form );
            // 获取登录用户 设置修改账户id
            workflowDefineStepsVO.setModifyBy( getUserId( request, response ) );
            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getStaffId() ) == null )
            {
               workflowDefineStepsVO.setStaffId( "0" );
            }
            if ( KANUtil.filterEmpty( workflowDefineStepsVO.getPositionId() ) == null )
            {
               workflowDefineStepsVO.setPositionId( "0" );
            }
            // 修改对象
            workflowDefineStepsService.updateWorkflowDefineSteps( workflowDefineStepsVO );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_STEPS" );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineStepsVO, Operate.MODIFY, workflowDefineStepsVO.getStepId(), null );
         }
         // 清空Form条件
         ( ( WorkflowDefineStepsVO ) form ).reset();

         request.getRequestDispatcher( "workflowDefineAction.do?proc=to_objectModify" ).forward( request, response );

         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
         final WorkflowDefineStepsVO workflowDefineStepsVO = new WorkflowDefineStepsVO();
         // 获取当前主键
         final String stepId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "GBK" ) );

         // 删除主键对应对象
         workflowDefineStepsVO.setStepId( stepId );
         workflowDefineStepsVO.setModifyBy( getUserId( request, response ) );
         workflowDefineStepsService.deleteWorkflowDefineSteps( workflowDefineStepsVO );
         constantsInit( "initWorkflow", getAccountId( request, response ) );

         insertlog( request, workflowDefineStepsVO, Operate.DELETE, stepId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowDefineStepsService workflowDefineStepsService = ( WorkflowDefineStepsService ) getService( "workflowDefineStepsService" );
         // 获取Action Form
         WorkflowDefineStepsVO workflowDefineStepsVO = ( WorkflowDefineStepsVO ) form;
         final String selectedIdStr = request.getParameter( "selectedIds_steps" );
         // 存在选中的ID
         if ( KANUtil.filterEmpty( selectedIdStr ) != null )
         {
            final String selectedIds[] = selectedIdStr.split( "," );
            workflowDefineStepsService.deleteWorkflowDefineStepsByStepsId( getUserId( request, response ), selectedIds );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineStepsVO, Operate.DELETE, null, selectedIdStr );
         }
         // 清除Selected IDs和子Action
         workflowDefineStepsVO.setSelectedIds( "" );
         workflowDefineStepsVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}