package com.kan.base.web.actions.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;

public class WorkflowActualAction extends BaseAction
{
   /**
    * List WorkflowActual
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // 来自 工作流查询
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // 获得Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         // 如果没有排序，默认按“createDate”倒序
         if ( KANUtil.filterEmpty( workflowActualVO.getSortColumn() ) == null )
         {
            workflowActualVO.setSortColumn( "createDate" );
            workflowActualVO.setSortOrder( "DESC" );
         }

         // 设置查询审核职位为空
         workflowActualVO.getHistoryVO().setPositionId( null );
         // 设置查询创建人为本人
         workflowActualVO.setCreateBy( getUserId( request, response ) );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder workflowActualHolder = new PagedListHolder();

         // 传入当前页
         workflowActualHolder.setPage( page );
         // 传入当前值对象
         workflowActualHolder.setObject( workflowActualVO );

         // 设置页面记录条数
         workflowActualHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowActualService.getWorkflowActualVOsByCondition( workflowActualHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowActualHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "workflowActualHolder", workflowActualHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listWorkflowActualTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listWorkflowActual" );
   }

   /**
    * 通知 待办事项（待审核列表）
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Code Reviewed by Siuvan Xia at 2014-7-10
   public ActionForward list_object_unfinished( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取待办事项的状态
         final String actualStepStatus = request.getParameter( "actualStepStatus" );
         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // 获得Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         if ( actualStepStatus != null && !"".equals( actualStepStatus ) )
         {
            workflowActualVO.setActualStepStatus( actualStepStatus );
         }

         // 如果没有排序，默认按“createDate”倒序
         if ( KANUtil.filterEmpty( workflowActualVO.getSortColumn() ) == null )
         {
            workflowActualVO.setSortColumn( "createDate" );
            workflowActualVO.setSortOrder( "DESC" );
         }

         workflowActualVO.setStaffId( getStaffId( request, response ) );
         workflowActualVO.setLogonUserId( getUserId( request, response ) );
         /* if ( getUsername( request, null ).equalsIgnoreCase( "Administrator" ) )
          {

          }else{
             workflowActualVO.setStaffId( getStaffId( request, response ) );
             workflowActualVO.setLogonUserId( getUserId( request, response ) );
          }
         */
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder workflowActualHolder = new PagedListHolder();
         // 传入当前页
         workflowActualHolder.setPage( page );
         // 传入当前值对象
         workflowActualHolder.setObject( workflowActualVO );
         // 设置页面记录条数
         workflowActualHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowActualService.getWorkflowActualVOsByCondition( workflowActualHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowActualHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "workflowActualHolder", workflowActualHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, null ) );
            // Ajax调用无跳转
            return mapping.findForward( "listWorkflowActualUnfinishedTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到通知 待办事项
      return mapping.findForward( "listWorkflowAcutalUnfinished" );
   }

   /**
    * 获取未读信息条数
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Modify by siuvan @2014-10-22 国际化
   public ActionForward validateRemovePosition_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );

         final String positionIds[] = request.getParameterValues( "positionId" );

         final List< Object > notFinishWorkflowActual = workflowActualService.getNotFinishWorkflowActualVOsByPositionIds( positionIds );
         final StringBuilder sb = new StringBuilder();
         sb.append( "" );
         if ( notFinishWorkflowActual != null && notFinishWorkflowActual.size() > 0 )
         {
            for ( Object obj : notFinishWorkflowActual )
            {
               if ( sb.toString().length() <= 200 )
               {
                  WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) obj;
                  workflowActualVO.reset( mapping, request );
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
                  {
                     sb.append( "存在 " + workflowActualVO.getDecodeActualStepStatus() + "的工作流[" + workflowActualVO.getNameZH() + "]需要审核！\n" );
                  }
                  else
                  {
                     sb.append( "Exists workflow [" + workflowActualVO.getNameEN() + "] need to audit!\n" );
                  }
               }
            }

            if ( sb.toString().length() >= 200 )
            {
               sb.append( "....\n" );
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
               {
                  sb.append( "总共" + notFinishWorkflowActual.size() + "条审批任务！\n" );
                  sb.append( "该" + ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) ? "员工" : "雇员" ) + "删除后将不能审批，确定删除？" );
               }
               else
               {
                  sb.append( "Total " + notFinishWorkflowActual.size() + " audit tasks!\n" );
                  sb.append( "The employee will not be deleted after audit, to confirm the deletion?" );
               }
            }
         }

         JSONObject jsonObject = new JSONObject();
         jsonObject.put( "size", notFinishWorkflowActual.size() );
         jsonObject.put( "message", sb.toString() );

         out.print( jsonObject.toString() );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * 获取未读信息条数
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Code Reviewed by Siuvan Xia at 2014-7-28
   public ActionForward get_notReadCount( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final UserService userService = ( UserService ) getService( "userService" );
         final AccountService accountService = ( AccountService ) getService( "accountService" );

         // 初始化jsonArray
         final JSONArray jsonArray = new JSONArray();

         // 获得Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;
         workflowActualVO.setActualStepStatus( "2" );
         workflowActualVO.setStaffId( getStaffId( request, null ) );
         workflowActualVO.setLogonUserId( getUserId( request, null ) );

         // 统计当前userId待办事项
         int count = 0;
         count = workflowActualService.countWorkflowActualVOsByCondition( workflowActualVO );

         // 初始化AccountVO
         AccountVO accountVO = null;
         accountVO = accountService.getAccountVOByAccountId( getAccountId( request, null ) );

         JSONObject jsonObject = null;
         jsonObject = new JSONObject();
         jsonObject.put( "accountId", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? accountVO.getNameCN() : accountVO.getNameEN() );
         jsonObject.put( "count", count );
         jsonArray.add( jsonObject );

         // 获取当前UserVO
         final StaffDTO staffDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOByUserId( getUserId( request, response ) );
         UserVO userVO = null;
         userVO = staffDTO != null && staffDTO.getUserVO() != null ? staffDTO.getUserVO() : null;
         // 是否存在多个账号
         if ( userVO != null && KANUtil.filterEmpty( userVO.getUserIds() ) != null )
         {
            final List< String > userIdList = KANUtil.jasonArrayToStringList( userVO.getUserIds() );

            // 遍历userId
            if ( userIdList != null && userIdList.size() > 0 )
            {
               for ( String userId : userIdList )
               {
                  // 获取tempUserVO
                  final UserVO tempUserVO = userService.getUserVOByUserId( userId );
                  if ( tempUserVO != null )
                  {
                     workflowActualVO.setAccountId( tempUserVO.getAccountId() );
                     workflowActualVO.setLogonUserId( tempUserVO.getUserId() );

                     count = workflowActualService.countWorkflowActualVOsByCondition( workflowActualVO );
                     accountVO = accountService.getAccountVOByAccountId( tempUserVO.getAccountId() );
                     if ( accountVO != null )
                     {
                        jsonObject = new JSONObject();
                        jsonObject.put( "accountId", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? accountVO.getNameCN() : accountVO.getNameEN() );
                        jsonObject.put( "count", count );
                        jsonArray.add( jsonObject );
                     }
                  }
               }
            }
         }

         out.print( jsonArray.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * To workflowActual modify page
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
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // 获得当前主键
         String workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ) );
         // 获得主键对应对象
         WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
         // 刷新对象，初始化对象列表及国际化
         workflowActualVO.reset( null, request );

         workflowActualVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowActualForm", workflowActualVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageWorkflowActual" );
   }

   /**
    * To workflowActual new page
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
      ( ( WorkflowActualVO ) form ).setStatus( WorkflowActualVO.TRUE );
      ( ( WorkflowActualVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageWorkflowActual" );
   }

   /**
    * Add workflowActual
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
            final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
            // 获得ActionForm
            final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;
            // 设置systeId
            workflowActualVO.setSystemId( KANConstants.SYSTEM_ID );
            // 获取登录用户  - 设置创建用户id
            workflowActualVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            workflowActualVO.setAccountId( getAccountId( request, response ) );
            workflowActualVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            workflowActualService.insertWorkflowActual( workflowActualVO );

         }

         // 清空Form条件
         ( ( WorkflowActualVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify workflowActual
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
            final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
            // 获得当前主键
            final String workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ) );
            // 获得主键对应对象
            final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
            // 装载界面传值
            workflowActualVO.update( ( WorkflowActualVO ) form );
            // 获取登录用户 设置修改账户id
            workflowActualVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            workflowActualService.updateWorkflowActual( workflowActualVO );
            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form条件
         ( ( WorkflowActualVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete workflowActual
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
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualVO workflowActualVO = new WorkflowActualVO();
         // 获得当前主键
         final String workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "GBK" ) );

         // 删除主键对应对象
         workflowActualVO.setWorkflowId( workflowId );
         workflowActualVO.setModifyBy( getUserId( request, response ) );
         workflowActualService.deleteWorkflowActual( workflowActualVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete workflowActual list
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
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // 获得Action Form
         WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;
         // 存在选中的ID
         if ( workflowActualVO.getSelectedIds() != null && !workflowActualVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : workflowActualVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               workflowActualVO.setWorkflowId( selectedId );
               workflowActualVO.setModifyBy( getUserId( request, response ) );
               workflowActualService.deleteWorkflowActual( workflowActualVO );
            }
         }
         // 清除Selected IDs和子Action
         workflowActualVO.setSelectedIds( "" );
         workflowActualVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 选择是查看请假加班。还是查看待办事项
   public ActionForward chooseNotice( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      request.setAttribute( "message_count", request.getParameter( "message_count" ) );
      return mapping.findForward( "chooseNotice" );
   }

   // 获取
   public ActionForward list_object_unfinished_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         final String actualStepStatus = request.getParameter( "actualStepStatus" );

         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // 获得Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         if ( KANUtil.filterEmpty( actualStepStatus ) != null )
         {
            workflowActualVO.setActualStepStatus( actualStepStatus );
         }

         workflowActualVO.setCorpId( getCorpId( request, response ) );

         // 设置accountID
         workflowActualVO.setAccountId( getAccountId( request, response ) );
         workflowActualVO.setCurrentPositionId( getPositionId( request, null ) );
         workflowActualVO.setStaffId( getStaffId( request, response ) );
         workflowActualVO.setLogonUserId( getUserId( request, response ) );

         // 只查看请假和加班的。请假501 加班502
         workflowActualVO.setSystemModuleId( "501,502" );
         workflowActualVO.setSortColumn( "createDate" );
         workflowActualVO.setSortOrder( "desc" );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder workflowActualHolder = new PagedListHolder();

         // 传入当前页
         workflowActualHolder.setPage( page );
         // 传入当前值对象
         workflowActualHolder.setObject( workflowActualVO );

         // 设置页面记录条数
         workflowActualHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowActualService.getWorkflowActualVOsByCondition( workflowActualHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowActualHolder, request );

         if ( new Boolean( getAjax( request ) ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // 初始化PrintWrite对象
            final PrintWriter out = response.getWriter();
            final JSONArray workflowActualJsonArray = JSONArray.fromObject( workflowActualHolder.getSource() );
            final JSONObject workflowActualJsonObject = new JSONObject();
            workflowActualJsonObject.put( "workflowActualJsonArray", workflowActualJsonArray );
            workflowActualJsonObject.put( "page", workflowActualHolder.getPage() );
            workflowActualJsonObject.put( "realPage", workflowActualHolder.getRealPage() );
            workflowActualJsonObject.put( "pageCount", workflowActualHolder.getPageCount() );
            workflowActualJsonObject.put( "nextPage", workflowActualHolder.getNextPage() );
            workflowActualJsonObject.put( "pageSize", workflowActualHolder.getPageSize() );
            out.print( workflowActualJsonObject.toString() );
            out.flush();
            out.close();
            return null;
         }

         // Holder需写入Request对象
         request.setAttribute( "workflowActualHolder", workflowActualHolder );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      // 跳转到通知 待办事项
      return mapping.findForward( "listTask" );
   }

   public ActionForward get_notReadCount_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // 获得Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         //workflowActualVO.setActualStepStatus( "2" );

         // 设置accountID
         workflowActualVO.setAccountId( getAccountId( request, response ) );
         workflowActualVO.setStaffId( getStaffId( request, response ) );
         workflowActualVO.setLogonUserId( getUserId( request, response ) );

         // 只查看请假和加班的工作流
         workflowActualVO.setSystemModuleId( "501,502" );
         final int count = workflowActualService.countWorkflowActualVOsByCondition( workflowActualVO );
         // 查看自己新建的请假和加班
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final LeaveHeaderVO condLeaveHeaderVO = new LeaveHeaderVO();
         condLeaveHeaderVO.setAccountId( getAccountId( request, response ) );
         condLeaveHeaderVO.setCreateBy( getUserId( request, response ) );
         final OTHeaderVO condOTHeaderVO = new OTHeaderVO();
         condOTHeaderVO.setAccountId( getAccountId( request, response ) );
         condOTHeaderVO.setCreateBy( getUserId( request, response ) );
         final int result = leaveHeaderService.count_leaveUnread( condLeaveHeaderVO ) + otHeaderService.count_OTUnread( condOTHeaderVO );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         out.print( count + "##" + result );
         out.flush();
         out.close();
      }
      catch ( IOException e )
      {

         e.printStackTrace();
      }

      return null;
   }

   // 批量操作（同意、不同意）
   // Add by siuvan.xia @ 2014-07-10
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         // 初始化Service接口
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );

         // 获得勾选ID
         final String workflowIds = workflowActualVO.getSelectedIds();

         // 获取审核意见
         final String stepStatus = request.getParameter( "stepStatus" );

         // ajax需解码
         decodedObject( workflowActualVO );

         // 存在勾选ID
         if ( KANUtil.filterEmpty( workflowIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = workflowIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String selectId : selectedIdArray )
            {
               // 获得WorkflowActualStepsVO列表
               final List< Object > workflowActualStepsVOs = workflowActualStepsService.getWorkflowActualStepsVOsByWorkflowId( KANUtil.decodeStringFromAjax( selectId ) );

               // 初始化WorkflowActualStepsVO
               WorkflowActualStepsVO submitObject = null;

               // 遍历找到对应的WorkflowActualStepsVO
               if ( workflowActualStepsVOs != null && workflowActualStepsVOs.size() > 0 )
               {
                  for ( Object workflowActualStepsVOObject : workflowActualStepsVOs )
                  {
                     final WorkflowActualStepsVO tempWorkflowActualStepsVO = ( WorkflowActualStepsVO ) workflowActualStepsVOObject;

                     if ( ( "1".equals( tempWorkflowActualStepsVO.getAuditType() ) && KANUtil.filterEmpty( tempWorkflowActualStepsVO.getAuditTargetId() ) != null && tempWorkflowActualStepsVO.getAuditTargetId().equals( getPositionId( request, null ) ) )
                           || ( "4".equals( tempWorkflowActualStepsVO.getAuditType() ) && ( ( KANUtil.filterEmpty( getStaffId( request, null ) ) != null && getStaffId( request, null ).equals( tempWorkflowActualStepsVO.getAuditTargetId() ) ) || ( KANUtil.filterEmpty( getUserId( request, null ) ) != null && getUserId( request, null ).equals( tempWorkflowActualStepsVO.getAuditTargetId() ) ) ) ) )
                     {
                        // 必须为"待操作"状态（有人利用浏览器的自带的返回重复提交数据）
                        if ( "2".equalsIgnoreCase( tempWorkflowActualStepsVO.getStatus() ) )
                        {
                           submitObject = tempWorkflowActualStepsVO;
                           submitObject.setStatus( stepStatus );

                           // 拒绝获取拒绝原因
                           if ( "4".equals( KANUtil.filterEmpty( stepStatus ) ) )
                           {
                              submitObject.setDescription( workflowActualVO.getDescription() );
                           }
                           break;
                        }
                     }
                  }
               }

               // 必须为"待操作"状态（有人利用浏览器的自带的返回重复提交数据）
               if ( submitObject != null )
               {
                  submitObject.setModifyBy( getUserId( request, response ) );
                  submitObject.setModifyDate( new Date() );
                  submitObject.reset( mapping, request );
                  rows = rows + workflowActualStepsService.updateWorkflowActualStepsVO( submitObject );
               }
            }
            success( request, null, "操作成功，您总共" + ( stepStatus.equals( "3" ) ? "同意" : "拒绝" ) + rows + "条记录！" );

            insertlog( request, workflowActualVO, stepStatus.equals( "3" ) ? Operate.APPROVE : Operate.REJECT, null, ( stepStatus.equals( "3" ) ? "同意" : "拒绝" ) + " workflow审批 "
                  + KANUtil.decodeSelectedIds( workflowIds ) );
         }

         // 清除Selected IDs和子Action
         ( ( WorkflowActualVO ) form ).setSelectedIds( "" );
         ( ( WorkflowActualVO ) form ).setSubAction( SEARCH_OBJECT );
         ( ( WorkflowActualVO ) form ).setActualStepStatus( "2" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_unfinished( mapping, form, request, response );
   }

}
