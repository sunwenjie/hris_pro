package com.kan.base.web.actions.workflow;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class WorkflowActualStepsAction extends BaseAction
{
   public static final String ACCESSACTION = "HRO_SEC_WORKFLOW_SEARCH";

   /***
    * 审核列表
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final boolean ajax = new Boolean( request.getParameter( "ajax" ) );
         // 初始化Service接口
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // 获得Action Form
         final WorkflowActualStepsVO workflowActualStepsVO = ( WorkflowActualStepsVO ) form;
         // 获得当前主键
         final String workflowId;
         if ( ajax )
         {
            workflowId = Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ), "UTF-8" ) );
         }
         else
         {
            workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ) );
         }
         if ( ajax )
         {
            decodedObject( workflowActualStepsVO );
         }
         workflowActualStepsVO.setWorkflowId( workflowId );
         // 默认按审批步骤升序排列
         workflowActualStepsVO.setSortColumn( "stepIndex" );
         workflowActualStepsVO.setSortOrder( "asc" );
         request.setAttribute( "enCodeWorkflowId", request.getParameter( "workflowId" ) );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder workflowActualStepsHolder = new PagedListHolder();

         // 传入当前页
         workflowActualStepsHolder.setPage( page );
         // 传入当前值对象
         workflowActualStepsHolder.setObject( workflowActualStepsVO );
         // 设置页面记录条数
         workflowActualStepsHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowActualStepsService.getWorkflowActualStepsVOByCondition( workflowActualStepsHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowActualStepsHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "workflowActualStepsHolder", workflowActualStepsHolder );

         // 获得当前用户的所拥有的positionId 遍历看**有审核权限
         final String staffId = getStaffId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( staffId );
         List< String > positionIds = new ArrayList< String >();
         List< PositionStaffRelationVO > positonStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
         for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
         {
            positionIds.add( positionStaffRelationVO.getPositionId() );
         }
         request.setAttribute( "positionIds", positionIds );

         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );

         request.setAttribute( "workflowActualVO", workflowActualVO );

         // 如果是调用则返回Render生成的字节流
         if ( ajax )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listWorkflowActualStepsTable" );
         }
         else
         {
            //加载object对象
            HistoryService historyService = ( HistoryService ) getService( "historyService" );
            HistoryVO historyVO = historyService.getHistoryVOByWorkflowId( workflowId );
            final String objectClassStr = historyVO.getObjectClass();
            Class< ? > objectClass = Class.forName( objectClassStr );

            String passObjStr = historyVO.getPassObject();
            if ( passObjStr != null && !passObjStr.trim().isEmpty() )
            {
               final BaseVO passObject = ( BaseVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
               passObject.reset( mapping, request );
               request.setAttribute( "passObject", passObject );
            }

            String failObjStr = historyVO.getFailObject();
            if ( failObjStr != null && !failObjStr.trim().isEmpty() )
            {
               final BaseVO failObject = ( BaseVO ) JSONObject.toBean( JSONObject.fromObject( failObjStr ), objectClass );
               failObject.reset( mapping, request );
               request.setAttribute( "originalObject", failObject );
            }

            //            final String serviceBeanId = historyVO.getServiceBean();
            //            final String serviceGetObjByIdMethod = historyVO.getServiceGetObjByIdMethod();
            //            if ( StringUtils.isNotBlank( serviceBeanId ) && StringUtils.isNotBlank( serviceGetObjByIdMethod ) )
            //            {
            //               Object targetService = ServiceLocator.getService( serviceBeanId );
            //
            //               final Class< ? > targetServiceClass = targetService.getClass();
            //               Method method = targetServiceClass.getMethod( serviceGetObjByIdMethod, String.class );
            //               BaseVO originalObject = ( BaseVO ) method.invoke( targetService, historyVO.getObjectId() );
            //               if ( originalObject != null )
            //               {
            //                  originalObject.reset( mapping, request );
            //                  request.setAttribute( "originalObject", originalObject );
            //               }
            //            }
            request.setAttribute( "historyVO", historyVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listWorkflowActualSteps" );
   }

   public ActionForward list_object_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // 获得Action Form
         final WorkflowActualStepsVO workflowActualStepsVO = ( WorkflowActualStepsVO ) form;
         // 获得当前主键
         final String workflowId = request.getParameter( "workflowId" );
         workflowActualStepsVO.setWorkflowId( workflowId );
         // 默认按审批步骤升序排列
         workflowActualStepsVO.setSortColumn( "stepIndex" );
         workflowActualStepsVO.setStepType( "1,2" );
         workflowActualStepsVO.setSortOrder( "asc" );
         request.setAttribute( "enCodeWorkflowId", request.getParameter( "workflowId" ) );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder workflowActualStepsHolder = new PagedListHolder();

         // 传入当前页
         workflowActualStepsHolder.setPage( page );
         // 传入当前值对象
         workflowActualStepsHolder.setObject( workflowActualStepsVO );
         // 设置页面记录条数
         workflowActualStepsHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowActualStepsService.getWorkflowActualStepsVOByCondition( workflowActualStepsHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowActualStepsHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "workflowActualStepsHolder", workflowActualStepsHolder );

         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );

         request.setAttribute( "workflowActualVO", workflowActualVO );
         // Ajax调用无跳转
         return mapping.findForward( "popupListWorkflowActual" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // 获得当前主键
         String stepId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );
         // 获得主键对应对象
         WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );
         // 刷新对象，初始化对象列表及国际化
         workflowActualStepsVO.reset( null, request );

         workflowActualStepsVO.setSubAction( VIEW_OBJECT );
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
      ( ( WorkflowActualStepsVO ) form ).setStatus( WorkflowActualStepsVO.TRUE );
      ( ( WorkflowActualStepsVO ) form ).setSubAction( CREATE_OBJECT );

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

      // 跳转到列表界面
      return null;
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
            final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
            // 获得当前主键
            final String stepId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "stepId" ), "UTF-8" ) );
            // 获得主键对应对象
            final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );
            // 装载界面传值
            workflowActualStepsVO.update( ( WorkflowActualStepsVO ) form );
            // 获取登录用户 设置修改账户id
            workflowActualStepsVO.setModifyBy( getUserId( request, response ) );

            // 审核权限验证?????

            // 修改对象
            workflowActualStepsVO.reset( null, request );
            workflowActualStepsService.updateWorkflowActualStepsVO( workflowActualStepsVO );

            insertlog( request, workflowActualStepsVO, workflowActualStepsVO.getStatus().equals( "3" ) ? Operate.APPROVE : Operate.REJECT, workflowActualStepsVO.getStepId(), ( workflowActualStepsVO.getStatus().equals( "3" ) ? "同意"
                  : "拒绝" )
                  + " workflow审批" );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form条件
         ( ( WorkflowActualStepsVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   // 邮件审批
   public ActionForward modify_object_byMail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // 获得当前主键
         final String stepId = request.getParameter( "stepId" );
         final String workflowId = request.getParameter( "workflowId" );
         final String status = request.getParameter( "status" );
         final String randomKey = request.getParameter( "randomKey" );
         final String userId = request.getParameter( "userId" );

         if ( StringUtils.isNotEmpty( userId ) )
         {
            request.setAttribute( "userId_mail", userId );
         }

         // 获得workflowActualVO
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
         // 获得workflowActualStepsVO
         final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );

         if ( workflowActualVO != null && workflowActualStepsVO != null && randomKey.equals( workflowActualStepsVO.getRandomKey() ) )
         {
            workflowActualStepsVO.setAccountId( workflowActualVO.getAccountId() );
            // 清空随机码
            workflowActualStepsVO.setRandomKey( "" );
            workflowActualStepsVO.setModifyDate( new Date() );
            workflowActualStepsVO.setStatus( status );
            request.setAttribute( "workflowMSG", "操作成功！" + workflowActualVO.getNameZH() + "，已被" + ( "3".equals( status ) ? "批准" : "退回" ) + "！" );

            workflowActualStepsVO.reset( mapping, request );
            workflowActualStepsService.updateWorkflowActualStepsVO( workflowActualStepsVO );

            insertlog( request, workflowActualStepsVO, workflowActualStepsVO.getStatus().equals( "3" ) ? Operate.APPROVE : Operate.REJECT, workflowActualStepsVO.getStepId(), ( workflowActualStepsVO.getStatus().equals( "3" ) ? "同意"
                  : "拒绝" )
                  + " workflow审批(来自邮件的操作)" );
         }
         else
         {
            request.setAttribute( "workflowMSG", "您已经审批过 " + workflowActualVO.getNameZH() + "，请勿重复操作！" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "auditMessage" );
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
   }

   public ActionForward list_object_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // 获得当前主键
         final String workflowId = KANUtil.decodeString( request.getParameter( "workflowId" ) );
         final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
         workflowActualVO.reset( null, request );

         final List< Object > stepVOs = workflowActualStepsService.getWorkflowActualStepsVOsByWorkflowId( workflowId );

         // 获得当前用户的所拥有的positionId 遍历看**有审核权限
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( getStaffId( request, response ) );
         List< String > positionIds = new ArrayList< String >();
         List< PositionStaffRelationVO > positonStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
         for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
         {
            positionIds.add( positionStaffRelationVO.getPositionId() );
         }

         // 找到workflowActualStepsVO
         for ( Object obj : stepVOs )
         {
            WorkflowActualStepsVO tempVO = ( WorkflowActualStepsVO ) obj;
            // 内部职位
            if ( "1".equals( tempVO.getAuditType() ) && positionIds.contains( tempVO.getAuditTargetId() ) )
            {
               request.setAttribute( "workflowActualStepsForm", tempVO );
               break;
            }
            // 内部员工
            else if ( "4".equals( tempVO.getAuditType() ) && tempVO.getAuditTargetId().equals( getStaffId( request, response ) ) )
            {
               request.setAttribute( "workflowActualStepsForm", tempVO );
               break;
            }
         }

         request.setAttribute( "workflowActualVO", workflowActualVO );

         //加载object对象
         HistoryService historyService = ( HistoryService ) getService( "historyService" );
         HistoryVO historyVO = historyService.getHistoryVOByWorkflowId( workflowId );
         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         if ( passObjStr != null && !passObjStr.trim().isEmpty() )
         {
            final BaseVO passObject = ( BaseVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
            passObject.reset( mapping, request );
            request.setAttribute( "passObject", passObject );
         }

         final String serviceBeanId = historyVO.getServiceBean();
         final String serviceGetObjByIdMethod = historyVO.getServiceGetObjByIdMethod();
         Object targetService = ServiceLocator.getService( serviceBeanId );

         final Class< ? > targetServiceClass = targetService.getClass();
         Method method = targetServiceClass.getMethod( serviceGetObjByIdMethod, String.class );
         BaseVO originalObject = ( BaseVO ) method.invoke( targetService, historyVO.getObjectId() );
         if ( originalObject != null )
         {
            originalObject.reset( mapping, request );
         }
         request.setAttribute( "originalObject", originalObject );

         request.setAttribute( "historyVO", historyVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "taskDetail" );
   }

   public ActionForward modify_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
            // 获得当前主键
            final String stepId = request.getParameter( "stepId" );
            // 获得主键对应对象
            final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsService.getWorkflowActualStepsVOByStepsId( stepId );
            // 装载界面传值
            workflowActualStepsVO.update( ( WorkflowActualStepsVO ) form );
            // 获取登录用户 设置修改账户id
            workflowActualStepsVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            workflowActualStepsVO.reset( mapping, request );
            workflowActualStepsService.updateWorkflowActualStepsVO( workflowActualStepsVO );
            insertlog( request, workflowActualStepsVO, workflowActualStepsVO.getStatus().equals( "3" ) ? Operate.APPROVE : Operate.REJECT, workflowActualStepsVO.getStepId(), ( workflowActualStepsVO.getStatus().equals( "3" ) ? "同意"
                  : "拒绝" )
                  + " workflow审批(来自微信的操作)" );
         }
         // 清空Form条件
         ( ( WorkflowActualStepsVO ) form ).reset();
         return new WorkflowActualAction().list_object_unfinished_mobile( mapping, new WorkflowActualVO(), request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void re_send_mail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final String workflowId = request.getParameter( "workflowId" );
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         boolean success = workflowActualStepsService.reSendApprovalMail( workflowId );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         out.print( success ? KANUtil.getProperty( request.getLocale(), "wx.leave.list.confirm.resend.success" )
               : KANUtil.getProperty( request.getLocale(), "wx.leave.list.confirm.resend.fail" ) );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

}
