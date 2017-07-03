/*
 * Created on 2013-05-13
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.WorkflowModuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.RightRender;

/**
 * @author Jixiang.hu
 */
public class WorkflowModuleAction extends BaseAction
{

   /**
    * List WorkflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         // 获得Action Form
         final WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) form;

         boolean isDelete = workflowModuleVO != null && workflowModuleVO.getSubAction() != null && workflowModuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );

         // 如果子Action是删除信息模板
         if ( isDelete )
         {
            // 调用删除信息模板的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序、翻页或导出操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( workflowModuleVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder workflowModuleHolder = new PagedListHolder();

         // 传入当前页
         workflowModuleHolder.setPage( page );
         // 传入当前值对象
         workflowModuleHolder.setObject( workflowModuleVO );
         // 设置页面记录条数
         workflowModuleHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowModuleService.getWorkflowModuleVOsByCondition( workflowModuleHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowModuleHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "workflowModuleHolder", workflowModuleHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) || isDelete )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listWorkflowModuleTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listWorkflowModule" );
   }

   /**
    * To workflowModule modify page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         // 获得当前主键
         String workflowModuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowModuleId" ), "UTF-8" ) );
         // 获得主键对应对象
         final WorkflowModuleVO workflowModuleVO = workflowModuleService.getWorkflowModuleVOByModuleId( workflowModuleId );
         // 刷新对象，初始化对象列表及国际化
         workflowModuleVO.reset( null, request );
         workflowModuleVO.setSubAction( VIEW_OBJECT );
         workflowModuleVO.setModuleTitle( KANConstants.getModuleTitleByModuleId( workflowModuleVO.getModuleId() ) );

         request.setAttribute( "workflowModuleForm", workflowModuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageWorkflowModule" );
   }

   /**
    * To workflowModule new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );
      // 初始化Service接口
      // 设置Sub Action
      ( ( WorkflowModuleVO ) form ).setStatus( WorkflowModuleVO.TRUE );
      ( ( WorkflowModuleVO ) form ).setSubAction( CREATE_OBJECT );
      request.setAttribute( "sysMoldueNamelList", "" );

      // 跳转到新建界面
      return mapping.findForward( "manageWorkflowModule" );
   }

   /**
    * Add workflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
            // 获得ActionForm
            final WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) form;
            // 设置systeId
            workflowModuleVO.setSystemId( KANConstants.SYSTEM_ID );
            // 获取登录用户  - 设置创建用户id
            workflowModuleVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            workflowModuleVO.setAccountId( getAccountId( request, response ) );
            workflowModuleVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            workflowModuleService.insertWorkflowModule( workflowModuleVO );

            //重新初始化WorkflowModule
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            constants.initWorkflowModule();
         }

         // 清空Form条件
         ( ( WorkflowModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   public ActionForward list_Module_rightIds_html_checkBox( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String workflowModuleId = request.getParameter( "workflowModuleId" );

         WorkflowModuleVO workflowModuleVO = null;
         for ( WorkflowModuleVO workflowModuleVOTemp : KANConstants.WORKFLOW_MOFDULE_VO )
         {
            if ( workflowModuleVOTemp.getWorkflowModuleId().equals( workflowModuleId ) )
            {
               workflowModuleVO = workflowModuleVOTemp;
               break;
            }
         }

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();

         if ( workflowModuleVO != null )
         {
            String[] rightIdsArray = workflowModuleVO.getRightIdsArray();
            final String checkBoxName = request.getParameter( "checkBoxName" );
            final String rightIds = request.getParameter( "rightIds" );
            final String selectRightIds[] = KANUtil.jasonArrayToStringArray( rightIds );

            out.println( RightRender.getRightHorizontalMultipleChoice( request, rightIdsArray, checkBoxName, selectRightIds ) );

         }
         else
         {
            out.println( "<font color=red >" + KANUtil.getProperty( request.getLocale(), "workflow.define.trigger.right.load.fial" ) + "</font>" );
         }
         out.flush();
         out.close();
         // Ajax调用无跳转
         return null;

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Modify workflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
            // 获得当前主键
            final String workflowModuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowModuleId" ), "UTF-8" ) );
            // 获得主键对应对象
            final WorkflowModuleVO workflowModuleVO = workflowModuleService.getWorkflowModuleVOByModuleId( workflowModuleId );
            // 装载界面传值
            workflowModuleVO.update( ( WorkflowModuleVO ) form );
            // 获取登录用户 设置修改账户id
            workflowModuleVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            workflowModuleService.updateWorkflowModule( workflowModuleVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            //重新初始化WorkflowModule
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            constants.initWorkflowModule();
         }
         // 清空Form条件
         ( ( WorkflowModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete workflowModule
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         final WorkflowModuleVO workflowModuleVO = new WorkflowModuleVO();
         // 获得当前主键
         final String workflowModuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowModuleId" ), "GBK" ) );

         // 删除主键对应对象
         workflowModuleVO.setWorkflowModuleId( workflowModuleId );
         workflowModuleVO.setModifyBy( getUserId( request, response ) );
         workflowModuleService.deleteWorkflowModule( workflowModuleVO );
         //重新初始化WorkflowModule
         final KANConstants constants = ( KANConstants ) getService( "constants" );
         constants.initWorkflowModule();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete workflowModule list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowModuleService workflowModuleService = ( WorkflowModuleService ) getService( "workflowModuleService" );
         // 获得Action Form
         WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) form;
         // 存在选中的ID
         if ( workflowModuleVO.getSelectedIds() != null && !workflowModuleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : workflowModuleVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               workflowModuleVO.setWorkflowModuleId( selectedId );
               workflowModuleVO.setModifyBy( getUserId( request, response ) );
               workflowModuleService.deleteWorkflowModule( workflowModuleVO );
            }

            //重新初始化WorkflowModule
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            constants.initWorkflowModule();
         }
         // 清除Selected IDs和子Action
         workflowModuleVO.setSelectedIds( "" );
         workflowModuleVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}