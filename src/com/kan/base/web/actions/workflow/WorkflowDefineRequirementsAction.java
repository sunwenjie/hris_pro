package com.kan.base.web.actions.workflow;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.workflow.WorkflowDefineDTO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.service.inf.workflow.WorkflowDefineRequirementsService;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class WorkflowDefineRequirementsAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得是否Ajax调用
         final boolean ajax = new Boolean( request.getParameter( "ajax" ) );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得Action Form
         final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = ( WorkflowDefineRequirementsVO ) form;

         // 如果子Action是删除
         if ( workflowDefineRequirementsVO.getSubAction() != null && workflowDefineRequirementsVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );

         // 获得主表主键，如若是正常调用，从form中取，否则ajax需解译两次；       
         String defineId = request.getParameter( "defineId" );
         if ( KANUtil.filterEmpty( defineId ) == null )
         {
            defineId = ( ( WorkflowDefineRequirementsVO ) form ).getDefineId();
         }
         else
         {
            defineId = KANUtil.decodeStringFromAjax( defineId );
         }

         // 如果request中不存在workflowDefineForm
         if ( request.getAttribute( "workflowDefineForm" ) == null )
         {
            // 获得WorkflowDefineVO
            final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( defineId );
            workflowDefineVO.reset( null, request );
            workflowDefineVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "workflowDefineForm", workflowDefineVO );
         }

         // loadWorkflowDefineObjectType
         loadWorkflowDefineObjectType( request );

         // 设置defineId
         workflowDefineRequirementsVO.setDefineId( defineId );
         // 此处分页代码
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( workflowDefineRequirementsVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowDefineRequirementsService.getWorkflowDefineRequirementsVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );
         //Holder需写入Request对象
         request.setAttribute( "workflowDefineRequirementsHolder", pagedListHolder );

         // 默认顺序为1
         workflowDefineRequirementsVO.setColumnIndex( 1 );

         // Ajax Table调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回 JSP
            return mapping.findForward( "listWorkflowDefineRequirementsTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return mapping.findForward( "listWorkflowDefineRequirementsInHouse" );
      }
      else
      {
         return mapping.findForward( "listWorkflowDefineRequirements" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      //      // 获得该公司的所以职级
      //      final List< MappingVO > positionGrades = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionGrades( request.getLocale().getLanguage() );
      //      request.setAttribute( "positionGrades", positionGrades );

      // 设置Sub Action
      ( ( WorkflowDefineRequirementsVO ) form ).setStatus( WorkflowDefineRequirementsVO.TRUE );
      ( ( WorkflowDefineRequirementsVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageWorkflowDefineRequirements" );
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
            final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
            // 获得ActionForm
            final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = ( WorkflowDefineRequirementsVO ) form;
            final String defineId = workflowDefineRequirementsVO.getDefineId();
            workflowDefineRequirementsVO.setDefineId( Cryptogram.decodeString( URLDecoder.decode( defineId, "UTF-8" ) ) );
            // 获取登录用户  - 设置创建用户id
            workflowDefineRequirementsVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            workflowDefineRequirementsVO.setAccountId( getAccountId( request, response ) );
            workflowDefineRequirementsVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            workflowDefineRequirementsService.insertWorkflowDefineRequirements( workflowDefineRequirementsVO );

            // 返回添加成功的标记
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_REQUIREMENTS" );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );
         }

         // 清空Form条件
         ( ( WorkflowDefineRequirementsVO ) form ).reset();

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
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
         // 获得当前主键
         String workflowDefineRequirementsId = KANUtil.decodeString( request.getParameter( "requirementId" ) );
         // 获得主键对应对象
         WorkflowDefineRequirementsVO workflowDefineRequirementsVO = workflowDefineRequirementsService.getWorkflowDefineRequirementsVOByRequirementsId( workflowDefineRequirementsId );
         // 刷新对象，初始化对象列表及国际化
         workflowDefineRequirementsVO.reset( null, request );

         workflowDefineRequirementsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineRequirementsForm", workflowDefineRequirementsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageWorkflowDefineRequirements" );
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
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );

         // 获取当前主键
         final String requirementId = KANUtil.decodeString( request.getParameter( "requirementId" ) );

         // 获取WorkflowDefineRequirementsVO
         final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = workflowDefineRequirementsService.getWorkflowDefineRequirementsVOByRequirementsId( requirementId );

         // 获取WorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineRequirementsVO.getDefineId() );

         // 获取WorkflowDefineDTO
         final WorkflowDefineDTO workflowDefineDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getWorkflowDefineDTOByDefineId( workflowDefineRequirementsVO.getDefineId() );

         if ( workflowDefineDTO != null && workflowDefineDTO.getWorkflowDefineVO() != null )
         {
            WorkflowDefineAction.loadColumnVOs_toRequest( request, workflowDefineDTO.getWorkflowDefineVO().getWorkflowModuleId() );
         }
         else
         {
            if ( workflowDefineVO != null )
            {
               WorkflowDefineAction.loadColumnVOs_toRequest( request, workflowDefineVO.getWorkflowModuleId() );
            }
         }

         // 刷新对象，初始化对象列表及国际化
         workflowDefineRequirementsVO.reset( null, request );

         workflowDefineRequirementsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowDefineForm", workflowDefineVO );
         request.setAttribute( "workflowDefineRequirementsForm", workflowDefineRequirementsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageWorkflowDefineRequirementsForm" );
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
            final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
            // 获得当前主键
            final String workflowDefineRequirementsId = KANUtil.decodeString( request.getParameter( "requirementId" ) );
            // 获得主键对应对象
            final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = workflowDefineRequirementsService.getWorkflowDefineRequirementsVOByRequirementsId( workflowDefineRequirementsId );
            // 装载界面传值
            workflowDefineRequirementsVO.update( ( WorkflowDefineRequirementsVO ) form );
            // 获取登录用户 设置修改账户id
            workflowDefineRequirementsVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            workflowDefineRequirementsService.updateWorkflowDefineRequirements( workflowDefineRequirementsVO );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_REQUIREMENTS" );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );
         }
         // 清空Form条件
         ( ( WorkflowDefineRequirementsVO ) form ).reset();

         // 跳转到列表界面 
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
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowDefineRequirementsService workflowDefineRequirementsService = ( WorkflowDefineRequirementsService ) getService( "workflowDefineRequirementsService" );
         // 获得Action Form
         WorkflowDefineRequirementsVO workflowDefineRequirementsVO = ( WorkflowDefineRequirementsVO ) form;
         final String selectedIdStr = request.getParameter( "selectedIds_req" );
         // 存在选中的ID
         if ( KANUtil.filterEmpty( selectedIdStr ) != null )
         {
            final String selectedIds[] = selectedIdStr.split( "," );
            workflowDefineRequirementsService.deleteWorkflowDefineRequirementsByRequirementsId( getUserId( request, response ), selectedIds );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         workflowDefineRequirementsVO.setSelectedIds( "" );
         workflowDefineRequirementsVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 加载工作流对象类型
   private void loadWorkflowDefineObjectType( final HttpServletRequest request ) throws KANException
   {
      // 获取WorkflowDefineVO
      final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) request.getAttribute( "workflowDefineForm" );

      // 标记是请假还是加班
      boolean isLeaveAccessAction = false, isOTAccessAction = false;

      // 获取WorkflowModuleDTO
      final WorkflowModuleDTO workflowModuleDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getWorkflowModuleDTOByWorkflowModuleId( workflowDefineVO.getWorkflowModuleId() );

      if ( workflowModuleDTO != null && workflowModuleDTO.getWorkflowModuleVO() != null )
      {
         AccountModuleDTO accountModuleDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getAccountModuleDTOByModuleId( workflowModuleDTO.getWorkflowModuleVO().getModuleId() );
         if ( accountModuleDTO != null )
         {
            String accessActionStr = accountModuleDTO.getModuleVO().getAccessAction();
            String accessActions[] = KANUtil.jasonArrayToStringArray( accessActionStr );
            if ( accessActions != null )
            {
               for ( String accessAction : accessActions )
               {
                  isLeaveAccessAction = isLeaveAccessAction || WorkflowService.isLeaveAccessAction( accessAction );
                  isOTAccessAction = isOTAccessAction || WorkflowService.isOTAccessAction( accessAction );
               }
            }
         }
      }

      request.setAttribute( "isLeaveAccessAction", isLeaveAccessAction );
      request.setAttribute( "isOTAccessAction", isOTAccessAction );
      request.setAttribute( "isLeaveOrOTAccessAction", isLeaveAccessAction || isOTAccessAction );
   }

}