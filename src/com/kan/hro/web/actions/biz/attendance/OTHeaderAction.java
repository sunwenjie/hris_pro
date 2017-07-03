package com.kan.hro.web.actions.biz.attendance;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTDetailService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class OTHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_OT_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取subAction
         final String subAction = getSubAction( form );
         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         // 获得Action Form
         final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;
         // TODO 暂时特殊处理
         if ( KANUtil.filterEmpty( otHeaderVO.getNotFormatEstimateStartDate() ) != null )
         {
            otHeaderVO.setEstimateStartDate( URLDecoder.decode( URLDecoder.decode( ( String ) otHeaderVO.getNotFormatEstimateStartDate(), "UTF-8" ), "UTF-8" ) );
         }
         if ( KANUtil.filterEmpty( otHeaderVO.getNotFormatEstimateEndDate() ) != null )
         {
            otHeaderVO.setEstimateEndDate( URLDecoder.decode( URLDecoder.decode( ( String ) otHeaderVO.getNotFormatEstimateEndDate(), "UTF-8" ), "UTF-8" ) );
         }
         if ( new Boolean( ajax ) )
         {
            decodedObject( otHeaderVO );
         }

         //处理数据权限
         setDataAuth( request, response, otHeaderVO );

         // 如果没有指定排序则默认按 LeaveId排序
         if ( otHeaderVO.getSortColumn() == null || otHeaderVO.getSortColumn().isEmpty() )
         {
            otHeaderVO.setSortColumn( "otHeaderId" );
            otHeaderVO.setSortOrder( "desc" );
         }

         // 调用删除方法
         if ( otHeaderVO.getSubAction() != null && otHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( otHeaderVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( otHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         otHeaderService.getOTHeaderVOsByCondition( pagedListHolder, ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "otHeaderHolder", pagedListHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               pagedListHolder.setSource( otHeaderService.exportOTDetailByCondition( otHeaderVO ) );
               // 刷新国际化
               refreshHolder( pagedListHolder, request );
               request.setAttribute( "holderName", "otHeaderHolder" );
               request.setAttribute( "fileName", "加班" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listOTTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listOT" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 避免重复提交
      this.saveToken( request );

      // InHouse情况下，默认当前登录用户加班
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( OTHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }
      else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         ( ( OTHeaderVO ) form ).setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
      }

      // 设置Sub Action
      ( ( OTHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( OTHeaderVO ) form ).setStatus( OTHeaderVO.TRUE );

      ( ( OTHeaderVO ) form ).setSpecialOT( "2" );

      return mapping.findForward( "manageOT" );
   }

   public ActionForward to_objectNew_for_timesheet( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String employeeId = request.getParameter( "employeeId" );
         final String clientId = request.getParameter( "clientId" );
         final String contractId = request.getParameter( "contractId" );
         final String currDay = request.getParameter( "currDay" );
         final String timesheetId = request.getParameter( "timesheetId" );
         ( ( OTHeaderVO ) form ).setTimesheetId( timesheetId );
         ( ( OTHeaderVO ) form ).setEmployeeId( employeeId );
         ( ( OTHeaderVO ) form ).setClientId( clientId );
         ( ( OTHeaderVO ) form ).setCorpId( getCorpId( request, response ) );
         ( ( OTHeaderVO ) form ).setContractId( contractId );
         ( ( OTHeaderVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( OTHeaderVO ) form ).setStatus( OTHeaderVO.TRUE );
         ( ( OTHeaderVO ) form ).setItemId( null );
         request.setAttribute( "currDay", currDay + " 00:00" );
         request.setAttribute( "nextDay", KANUtil.formatDate( KANUtil.getDate( currDay, 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageOT" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

            // 获取当前FORM
            final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;
            otHeaderVO.setDataFrom( "1" );

            // 返回页面employeeId
            final String employeeId = otHeaderVO.getEmployeeId();
            // 用于保存到passObject 员工信息
            final EmployeeVO tempEmployeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
            if ( tempEmployeeVO != null )
            {
               otHeaderVO.setEmployeeNameZH( tempEmployeeVO.getNameZH() );
               otHeaderVO.setEmployeeNameEN( tempEmployeeVO.getNameEN() );
               otHeaderVO.setEmployeeNo( tempEmployeeVO.getEmployeeNo() );
               otHeaderVO.setCertificateNumber( tempEmployeeVO.getCertificateNumber() );
            }

            // 校验employeeId是否存在
            checkEmployeeId( mapping, otHeaderVO, request, response );

            // 不合法的employeeId跳转新增页面
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "employeeIdErrorMsg" ) ) != null )
            {
               otHeaderVO.reset();
               otHeaderVO.setEmployeeId( employeeId );
               otHeaderVO.setEmployeeNameZH( "" );
               otHeaderVO.setEmployeeNameEN( "" );
               return to_objectNew( mapping, otHeaderVO, request, response );
            }

            if ( KANUtil.filterEmpty( otHeaderVO.getClientId() ) == null )
            {
               final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

               if ( employeeContractVO != null )
               {
                  otHeaderVO.setClientId( employeeContractVO.getClientId() );
               }
            }

            // 设定当前用户
            otHeaderVO.setAccountId( getAccountId( request, response ) );
            otHeaderVO.setCreateBy( getUserId( request, response ) );
            otHeaderVO.setModifyBy( getUserId( request, response ) );

            if ( KANUtil.filterEmpty( otHeaderVO.getEstimateHours() ) != null && Double.valueOf( otHeaderVO.getEstimateHours() ) == 0 )
            {
               warning( request, null, "数据未产生，无效的加班小时数！" );
            }
            else
            {
               // 调用添加方法
               if ( otHeaderService.insertOTHeader( otHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, otHeaderVO, Operate.SUBMIT, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_ADD );
                  insertlog( request, otHeaderVO, Operate.ADD, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), null );
               }
            }

            fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
         }
         // 清空FORM
         ( ( OTHeaderVO ) form ).reset();
         ( ( OTHeaderVO ) form ).setEmployeeNameZH( "" );
         ( ( OTHeaderVO ) form ).setEmployeeNameEN( "" );
         ( ( OTHeaderVO ) form ).setNumber( "" );
         ( ( OTHeaderVO ) form ).setCertificateNumber( "" );
         ( ( OTHeaderVO ) form ).setEmployeeNo( "" );
         final String from = request.getParameter( "mobile" );
         if ( KANUtil.filterEmpty( from ) != null )
         {
            return new SecurityAction().index_mobile( mapping, new UserVO(), request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转列表界面
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         // 主键获取需解码
         final String otHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
         // 获取OTHeaderVO对象
         final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
         if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) != null
               && KANUtil.filterEmpty( otHeaderVO.getActualHours() ) != null )
         {
            otHeaderVO.setEstimateStartDate( otHeaderVO.getActualStartDate() );
            otHeaderVO.setEstimateEndDate( otHeaderVO.getActualEndDate() );
            otHeaderVO.setEstimateHours( otHeaderVO.getActualHours() );
         }
         // 刷新对象，初始化对象列表及国际化
         otHeaderVO.reset( null, request );
         // 区分修改添加
         otHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "otHeaderForm", otHeaderVO );
         request.setAttribute( "hasDeleteRight", hasDeleteRight( otHeaderVO ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageOT" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
            // 主键获取需解码
            final String otHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取OTHeaderVO对象
            final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
            // 装载界面传值
            otHeaderVO.update( ( ( OTHeaderVO ) form ) );
            // 如果是批准状态提交，写入实际时间
            if ( otHeaderVO.getStatus().equals( "3" ) )
            {
               otHeaderVO.setActualStartDate( ( ( OTHeaderVO ) form ).getEstimateStartDate() );
               otHeaderVO.setActualEndDate( ( ( OTHeaderVO ) form ).getEstimateEndDate() );
               otHeaderVO.setActualHours( ( ( OTHeaderVO ) form ).getEstimateHours() );
            }
            else
            {
               otHeaderVO.setEstimateStartDate( ( ( OTHeaderVO ) form ).getEstimateStartDate() );
               otHeaderVO.setEstimateEndDate( ( ( OTHeaderVO ) form ).getEstimateEndDate() );
               otHeaderVO.setEstimateHours( ( ( OTHeaderVO ) form ).getEstimateHours() );

               // 如果是拒绝状态，清空Actual
               if ( "6".equals( otHeaderVO.getStatus() ) )
               {
                  otHeaderVO.setActualStartDate( null );
                  otHeaderVO.setActualEndDate( null );
                  otHeaderVO.setActualHours( null );
               }
            }

            // 获取登录用户
            otHeaderVO.setModifyBy( getUserId( request, response ) );
            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            otHeaderVO.setItemId( "0" );
            // 如果是客户提交
            if ( subAction != null && ( subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) || subAction.trim().equalsIgnoreCase( CONFIRM_OBJECT ) ) )
            {
               otHeaderVO.reset( mapping, request );
               if ( otHeaderService.submitOTHeader( otHeaderVO ) == -1 )
               {
                  success( request, null, ( subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "提交" : "确认" ) + "成功！" );
                  insertlog( request, otHeaderVO, Operate.SUBMIT, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), ( subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "提交"
                        : "确认" ) );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, otHeaderVO, Operate.MODIFY, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), null );
               }
            }
            else
            {
               otHeaderService.updateOTHeader( otHeaderVO );
               success( request, MESSAGE_TYPE_UPDATE );
            }

            if ( KANUtil.filterEmpty( otHeaderVO.getDataFrom(), "1" ) == null )
               fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
         }
         // 清空FORM
         ( ( OTHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   // 删除加班记录
   // Add by siuvan.xia @2014-07-04
   public ActionForward delete_ot( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // 获取要删除的ID
         final String otHeaderId = request.getParameter( "otHeaderId" );

         // 获取LeaveHeaderVO
         final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
         otHeaderVO.setModifyBy( getUserId( request, response ) );
         otHeaderVO.setModifyDate( new Date() );

         if ( otHeaderService.deleteOTHeader_cleanTS( otHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_DELETE );
            insertlog( request, otHeaderVO, Operate.DELETE, otHeaderId, null );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         // 获取Action Form
         OTHeaderVO oTHeaderVO = ( OTHeaderVO ) form;
         // 存在选中的ID
         if ( oTHeaderVO.getSelectedIds() != null && !oTHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, oTHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( oTHeaderVO.getSelectedIds() ) );
            // 分割
            for ( String selectedId : oTHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取删除对象
               oTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               oTHeaderVO.setModifyBy( getUserId( request, response ) );
               oTHeaderVO.setModifyDate( new Date() );
               // 调用删除接口
               otHeaderService.deleteOTHeader( oTHeaderVO );
            }
         }

         // 清除Selected IDs和子Action
         ( ( OTHeaderVO ) form ).setSelectedIds( "" );
         ( ( OTHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final String subAction = request.getParameter( "subAction" );
         // 获取主键需解码
         final String otHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
         // 获取OTVO对象
         final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderId );
         // 获取登录用户
         otHeaderVO.setModifyBy( getUserId( request, response ) );
         otHeaderVO.setModifyDate( new Date() );
         // 走工作流
         otHeaderVO.reset( null, request );

         // 如果是批准状态提交，写入实际时间
         if ( otHeaderVO.getStatus().equals( "3" ) )
         {
            otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
            otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
            otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
         }

         if ( otHeaderService.submitOTHeader( otHeaderVO ) == -1 )
         {
            success( request, null, ( subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "提交" : "确认" ) + "成功！" );
            insertlog( request, otHeaderVO, Operate.SUBMIT, otHeaderId, subAction.equalsIgnoreCase( SUBMIT_OBJECT ) ? "提交" : "确认" );
         }
         else
         {
            error( request, MESSAGE_TYPE_SUBMIT );
         }

         fetchOTHoursDetail( otHeaderVO, mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // 批量提交
   // Added by siuxia at 2014-06-04
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm 
         final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;

         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // 获得勾选ID
         final String otHeaderIds = otHeaderVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( otHeaderIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = otHeaderIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String selectId : selectedIdArray )
            {
               // 获取OTVO对象
               final OTHeaderVO submitOTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
               // 获取登录用户
               submitOTHeaderVO.setModifyBy( getUserId( request, response ) );
               submitOTHeaderVO.setModifyDate( new Date() );
               // 走工作流
               submitOTHeaderVO.reset( null, request );

               // 如果是批量提交，只提交状态为“新建”的
               // 如果是批量确认，只确认状态为“批准”的
               if ( ( otHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) && submitOTHeaderVO.getStatus().equals( "1" ) )
                     || otHeaderVO.getSubAction().equalsIgnoreCase( CONFIRM_OBJECTS ) && submitOTHeaderVO.getStatus().equals( "3" ) )
               {
                  // 如果是批准状态提交，写入实际时间
                  if ( submitOTHeaderVO.getStatus().equals( "3" ) )
                  {
                     submitOTHeaderVO.setActualStartDate( submitOTHeaderVO.getEstimateStartDate() );
                     submitOTHeaderVO.setActualEndDate( submitOTHeaderVO.getEstimateEndDate() );
                     submitOTHeaderVO.setActualHours( submitOTHeaderVO.getEstimateHours() );
                  }

                  rows = rows + otHeaderService.submitOTHeader( submitOTHeaderVO );

                  fetchOTHoursDetail( submitOTHeaderVO, mapping, form, request, response );
               }
            }

            if ( rows == 0 )
            {
               request.setAttribute( "definedMessage", "true" );
            }

            insertlog( request, otHeaderVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( otHeaderIds ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /***
    * 选定服务协议，ajax后台
    */
   public ActionForward contract_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // 获取EmployeeContractOTVO列表
         final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTVOsByContractId( contractId );

         // 获取clientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO == null ? "0"
               : employeeContractVO.getOrderId() );

         // 获得ClientOrderOTVO列表
         final List< Object > clientOrderOTVOs = clientOrderOTService.getClientOrderOTVOsByClientOrderHeaderId( clientOrderHeaderVO == null ? "0"
               : clientOrderHeaderVO.getOrderHeaderId() );

         final JSONObject jsonObject = new JSONObject();

         // 默认有错误
         boolean error = true;

         // 劳动合同开始时间为设定；
         if ( employeeContractVO == null || ( employeeContractVO != null && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) == null ) )
         {
            error = false;
         }

         if ( error )
         {
            error = true;
            jsonObject.put( "contractStartDate", employeeContractVO.getStartDate() );
            jsonObject.put( "contractEndDate", employeeContractVO.getEndDate() );

            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
               employeeContractVO.setEndDate( "2199-12-31 00:00:00" );

            // 标记是否存在
            boolean exist = false;

            final String contractEndDate = employeeContractVO.getStatus().equals( "7" ) ? employeeContractVO.getResignDate() : employeeContractVO.getEndDate();
            // 劳动合同中存在加班设置
            if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
            {
               error = false;
               // 必须存在一个状态为启用
               for ( Object employeeContractTOVOObject : employeeContractOTVOs )
               {
                  if ( ( ( EmployeeContractOTVO ) employeeContractTOVOObject ).getStatus().equals( "1" ) )
                  {
                     exist = true;
                     break;
                  }
               }

               if ( exist )
               {
                  jsonObject.put( "startDate", KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ) + " 00:00" );
                  jsonObject.put( "endDate", KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), null ) + " 00:00" );
               }
            }

            // 帐套中存在加班设置
            if ( !exist && clientOrderOTVOs != null && clientOrderOTVOs.size() > 0 )
            {
               error = false;
               // 必须存在一个状态为启用
               for ( Object clientOrderOTVOObject : clientOrderOTVOs )
               {
                  if ( ( ( ClientOrderOTVO ) clientOrderOTVOObject ).getStatus().equals( "1" ) )
                  {
                     jsonObject.put( "startDate", KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ) + " 00:00" );
                     jsonObject.put( "endDate", KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), null ) + " 00:00" );
                     break;
                  }
               }
            }

            // 处理日期插件获取焦点时
            if ( jsonObject.get( "startDate" ) != null && jsonObject.get( "endDate" ) != null )
            {
               // 初始化Contract Start Calendar
               final Calendar startCalendar = KANUtil.createCalendar( String.valueOf( jsonObject.get( "startDate" ) ) );

               // 初始化Contract End Calendar
               final Calendar endCalendar = KANUtil.createCalendar( String.valueOf( jsonObject.get( "endDate" ) ) );

               // 默认选中当前时间
               jsonObject.put( "defDate", KANUtil.formatDate( new Date(), "yyyy-MM-dd" + " 00:00:00" ) );

               // 如果当前时间不在请假范围内
               if ( KANUtil.getDays( new Date() ) > KANUtil.getDays( endCalendar ) || KANUtil.getDays( new Date() ) < KANUtil.getDays( startCalendar ) )
               {
                  jsonObject.discard( "defDate" );
                  jsonObject.put( "defDate", KANUtil.formatDate( jsonObject.get( "startDate" ), "yyyy-MM-dd" + " 00:00:00" ) );
               }
            }
         }

         String messageError = error ? "不具备加班条件，可能原因：1、" + ( getRole( request, null ).equals( "1" ) ? "派送信息" : "劳动合同" ) + "开始时间未设定；2、没有有效的加班设置；" : "";
         jsonObject.put( "messageError", messageError );

         // Send to client
         out.println( jsonObject.toString() );
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
    * 日期控件失去焦点，ajax调用后台
    * 开始、结束日期都不为空
    */
   public ActionForward calculate_ot_hours_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 开始时间
         final String startDate = request.getParameter( "startDate" );

         // 结束时间
         final String endDate = request.getParameter( "endDate" );

         // 修改状态下带上本来剩余时间
         String extraOTHours = "0";
         /*String extraOTHours = request.getParameter( "otHours" );
         if ( extraOTHours == null || extraOTHours.isEmpty() || KANUtil.filterEmpty( extraOTHours, "null" ) == null )
         {
            extraOTHours = "0";
         }*/

         // 初始化Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // 获得ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // 获取计薪开始、结束日
         String circleStartDay = clientOrderHeaderVO.getCircleStartDay();
         String circleEndDay = clientOrderHeaderVO.getCircleEndDay();

         // 不正常的计薪周期默认开始“1”、结束“31”
         if ( KANUtil.filterEmpty( circleStartDay, "0" ) == null || KANUtil.filterEmpty( circleEndDay, "0" ) == null )
         {
            circleStartDay = "1";
            circleEndDay = "31";
         }

         // 获取有效的加班每月上限小时数
         String otLimitByMonth = employeeContractVO.getOtLimitByMonth();
         if ( KANUtil.filterEmpty( otLimitByMonth ) == null && clientOrderHeaderVO != null )
         {
            otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
         }

         // 不正常的加班每月上限小时数默认无限制“0”
         if ( KANUtil.filterEmpty( otLimitByMonth ) == null )
         {
            otLimitByMonth = "0";
         }

         // 获取每日加班小时数上限
         String otLimitHoursByDay = employeeContractVO.getOtLimitByDay();
         if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null && clientOrderHeaderVO != null )
         {
            otLimitHoursByDay = clientOrderHeaderVO.getOtLimitByDay();
         }

         // 不正常的每日加班小时数上限统默认“24h”
         if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null )
         {
            otLimitHoursByDay = "24";
         }

         // 获取有效日历、排班ID
         String calendarId = employeeContractVO.getCalendarId();
         if ( KANUtil.filterEmpty( calendarId, "0" ) == null && clientOrderHeaderVO != null )
         {
            calendarId = clientOrderHeaderVO.getCalendarId();
         }

         String shiftId = employeeContractVO.getShiftId();
         if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
         {
            shiftId = clientOrderHeaderVO.getShiftId();
         }

         // 返回值
         double reusltOTHours = 0;

         // 如果每月加班小时上限无限制，直接计算
         if ( otLimitByMonth.equals( "0" ) )
         {
            reusltOTHours = new TimesheetDTO().getOTHours( getAccountId( request, response ), calendarId, shiftId, startDate, endDate, Double.valueOf( otLimitHoursByDay ) );
         }
         // 有限制则需另外处理
         else
         {
            final Calendar startCalendar = KANUtil.createCalendar( startDate );
            final Calendar endCalendar = KANUtil.createCalendar( endDate );

            // 初始化OTDetailVO列表
            final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByContractId( contractId );

            // 获取加班跨天数
            final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

            // 遍历处理
            for ( int i = 0; i <= gap; i++ )
            {
               String sDate = i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00";
               String eDate = i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00";

               startCalendar.add( Calendar.DATE, 1 );

               if ( !sDate.equals( eDate ) )
               {
                  // 获取当前天加班小时数
                  double currDayOTHours = new TimesheetDTO().getOTHours( getAccountId( request, response ), calendarId, shiftId, sDate, eDate, Double.valueOf( otLimitHoursByDay ) );

                  // 超过当天上线情况处理
                  if ( Double.valueOf( otLimitHoursByDay ) > 0 && currDayOTHours > Double.valueOf( otLimitHoursByDay ) )
                  {
                     currDayOTHours = Double.valueOf( otLimitHoursByDay );
                  }

                  // 获取当前天所属月份
                  final String currMonthly = KANUtil.getMonthlyByCondition( circleEndDay, sDate );

                  // 每月上限处理
                  reusltOTHours = reusltOTHours
                        + new TimesheetDTO().getAvailableOTHours( otDetailVOs, currMonthly, reusltOTHours, currDayOTHours, Double.valueOf( extraOTHours ), otLimitByMonth, circleEndDay );

               }
            }
         }

         // Send to client
         out.println( reusltOTHours );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // Tab显示
         final String noTab = request.getParameter( "noTab" );

         if ( KANUtil.filterEmpty( contractId, "0" ) != null )
         {
            // 初始化Service
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

            // 获取OTHeaderVO列表
            final List< OTHeaderVO > otHeaderVOs = otHeaderService.getOTHeaderVOsByContracrId( contractId );

            if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
            {
               for ( OTHeaderVO otHeaderVO : otHeaderVOs )
               {
                  otHeaderVO.reset( null, request );
               }
               request.setAttribute( "otHeaderVOs", otHeaderVOs );
               request.setAttribute( "countOTHeaderVO", otHeaderVOs.size() );
            }
         }

         if ( KANUtil.filterEmpty( noTab ) != null && KANUtil.filterEmpty( noTab ).equals( "true" ) )
         {
            return mapping.findForward( "manageOTSpecialTable" );
         }

         return mapping.findForward( "manageOTSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void list_special_info_html_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         // 获取服务协议ID
         final String contractId = request.getParameter( "contractId" );

         final JSONArray jsonArray = new JSONArray();

         if ( KANUtil.filterEmpty( contractId, "0" ) != null )
         {
            // 初始化Service
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

            // 获取OTHeaderVO列表
            final List< OTHeaderVO > otHeaderVOs = otHeaderService.getOTHeaderVOsByContracrId( contractId );

            if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
            {
               for ( OTHeaderVO otHeaderVO : otHeaderVOs )
               {
                  otHeaderVO.reset( null, request );
                  final JSONObject jsonObject = JSONObject.fromObject( otHeaderVO );
                  jsonArray.add( jsonObject );
               }
            }
         }

         out.print( String.valueOf( jsonArray.toString() ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * 检查输入EmployeeId是否有效
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

      // 获得ActionForm
      final OTHeaderVO otHeaderVO = ( OTHeaderVO ) form;

      // 试图获取EmployeeVO 
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( otHeaderVO.getEmployeeId() );

      // 不存在EmployeeVO或AccountId不匹配当前
      if ( employeeVO == null
            || ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getAccountId() ) != null && !employeeVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "employeeIdErrorMsg", ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "员工" : "雇员" ) + "ID无效；" );
      }

      if ( !LeaveHeaderAction.checkIsPass( request, otHeaderVO.getEmployeeId() ) )
      {
         request.setAttribute( "employeeIdErrorMsg", "非HR职能只能为自己或同一部门员工加班；" );
      }

   }

   // check_ot_date检查请假日期是否重复
   public void check_ot_date( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();

         // 初始化Service
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // 获取参数
         final String otHeaderId = request.getParameter( "otHeaderId" );
         final String employeeId = request.getParameter( "employeeId" );
         final String contractId = request.getParameter( "contractId" );
         final String startDate = request.getParameter( "startDate" );
         final String endDate = request.getParameter( "endDate" );

         final long startDate_s = KANUtil.createDate( startDate ).getTime();
         final long endDate_s = KANUtil.createDate( endDate ).getTime();

         // 实例化OTHeaderVO
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setAccountId( getAccountId( request, null ) );
         otHeaderVO.setCorpId( getCorpId( request, null ) );
         otHeaderVO.setEmployeeId( employeeId );
         otHeaderVO.setContractId( contractId );

         boolean flag = false;

         // 获取OTHeaderVO列表
         final List< Object > otHeaderVOs = otHeaderService.getOTHeaderVOsByCondition( otHeaderVO );

         // 存在OTHeaderVO列表
         if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
         {
            for ( Object otHeaderVOObject : otHeaderVOs )
            {
               final OTHeaderVO tempOTHeaderVO = ( OTHeaderVO ) otHeaderVOObject;
               if ( KANUtil.filterEmpty( otHeaderId ) != null && tempOTHeaderVO.getOtHeaderId().trim().equals( KANUtil.decodeString( otHeaderId ) ) )
               {
                  continue;
               }

               if ( startDate_s >= KANUtil.createDate( tempOTHeaderVO.getEstimateEndDate() ).getTime()
                     || endDate_s <= KANUtil.createDate( tempOTHeaderVO.getEstimateStartDate() ).getTime() )
               {
                  flag = false;
               }
               else
               {
                  flag = true;
                  break;
               }
            }
         }

         out.print( String.valueOf( flag ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward to_objectNew_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 避免重复提交
      this.saveToken( request );

      // InHouse情况下，默认当前登录用户加班
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( OTHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }

      // 设置Sub Action
      ( ( OTHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( OTHeaderVO ) form ).setStatus( OTHeaderVO.TRUE );

      return mapping.findForward( "manageOT_mobile" );
   }

   // 获取自己的请假和加班
   public ActionForward readMessage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setAccountId( getAccountId( request, response ) );
         otHeaderVO.setCreateBy( getUserId( request, response ) );
         otHeaderService.read_OT( otHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return null;
   }

   // 加班删除权限
   // Add by siuvan.xia at 2014-07-03
   private boolean hasDeleteRight( final OTHeaderVO otHeaderVO ) throws KANException
   {
      // 如果是新建状态
      if ( KANUtil.filterEmpty( otHeaderVO.getStatus() ) != null && otHeaderVO.getStatus().equals( "1" ) )
         return true;

      // 是否有工作流
      if ( KANUtil.filterEmpty( otHeaderVO.getWorkflowId() ) != null )
         return false;

      // 初始化OTDetailService
      final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

      // 获取OTDetailVO列表
      final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

      int count = 0;
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         // 初始化LeaveHeaderService
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // 加班item为“加班换休”，寻找有无“加班换休”去请假
         for ( Object otDetailVOObject : otDetailVOs )
         {
            if ( KANUtil.filterEmpty( ( ( OTDetailVO ) otDetailVOObject ).getItemId() ) != null && ( ( OTDetailVO ) otDetailVOObject ).getItemId().equals( "25" ) )
            {
               final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
               leaveHeaderVO.setAccountId( otHeaderVO.getAccountId() );
               leaveHeaderVO.setCorpId( otHeaderVO.getCorpId() );
               leaveHeaderVO.setEmployeeId( otHeaderVO.getEmployeeId() );
               leaveHeaderVO.setContractId( otHeaderVO.getContractId() );
               leaveHeaderVO.setItemId( "25" );

               final List< LeaveHeaderVO > leaveHeaderVOs = leaveHeaderService.getLeaveHeaderVOsByCondition( leaveHeaderVO );

               if ( leaveHeaderVOs != null && leaveHeaderVOs.size() > 0 )
                  return false;
            }
         }

         // 初始化TimesheetHeaderService
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 若关联考勤表并且为“新建”或“退回”状态
         for ( Object otDetailVOObject : otDetailVOs )
         {
            if ( KANUtil.filterEmpty( ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId() ) != null )
            {
               final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId() );
               if ( timesheetHeaderVO != null && ( timesheetHeaderVO.getStatus().equals( "1" ) || timesheetHeaderVO.getStatus().equals( "4" ) ) )
                  count++;
               else
                  return false;
            }
         }

         if ( count == otDetailVOs.size() )
            return true;
      }

      return false;
   }

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "加班ID" );
      nameZHs.add( "加班类别" );
      nameZHs.add( "开始时间" );
      nameZHs.add( "结束时间" );
      nameZHs.add( "加班小时" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（中文）" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（英文）" );
      nameZHs.add( ( role.equals( "1" ) ? "派送信息" : "劳动合同" ) + "ID" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "客户ID" );
         nameZHs.add( "客户名称" );
      }
      if ( "100056".equals( getAccountId( request, response ) ) )
      {
         nameZHs.add( "特殊加班" );
      }

      nameZHs.add( "状态" );
      nameZHs.add( "修改人" );
      nameZHs.add( "修改时间" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // 导出属性
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );
      nameSyses.add( "specialOTHeaderId" );
      nameSyses.add( "decodeItemId" );
      nameSyses.add( "estimateStartDate" );
      nameSyses.add( "estimateEndDate" );
      nameSyses.add( "specialOTHours" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "contractId" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
         nameSyses.add( "clientName" );
      }
      if ( "100056".equals( getAccountId( request, response ) ) )
      {
         nameSyses.add( "decodeSpecialOT" );
      }
      nameSyses.add( "decodeStatus" );
      nameSyses.add( "decodeModifyBy" );
      nameSyses.add( "decodeModifyDate" );
      return KANUtil.stringListToArray( nameSyses );
   }

   public ActionForward add_object_nativeAPP( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         String result = "加班请求失败";

         // 初始化Service接口
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取当前FORM
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setUnread( request.getParameter( "unread" ) );
         otHeaderVO.setContractId( request.getParameter( "contractId" ) );
         otHeaderVO.setEstimateStartDate( request.getParameter( "estimateStartDate" ) );
         otHeaderVO.setEstimateEndDate( request.getParameter( "estimateEndDate" ) );
         otHeaderVO.setEstimateHours( request.getParameter( "estimateHours" ) );
         otHeaderVO.setDescription( request.getParameter( "description" ) );
         otHeaderVO.setStatus( "1" );
         otHeaderVO.setCorpId( getCorpId( request, response ) );
         otHeaderVO.setEmployeeId( getEmployeeId( request, null ) );

         if ( KANUtil.filterEmpty( otHeaderVO.getClientId() ) == null )
         {
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

            if ( employeeContractVO != null )
            {
               otHeaderVO.setClientId( employeeContractVO.getClientId() );
            }
         }

         // 设定当前用户
         otHeaderVO.setAccountId( getAccountId( request, response ) );
         otHeaderVO.setCreateBy( getUserId( request, response ) );
         otHeaderVO.setModifyBy( getUserId( request, response ) );

         if ( KANUtil.filterEmpty( otHeaderVO.getEstimateHours() ) != null && Double.valueOf( otHeaderVO.getEstimateHours() ) == 0 )
         {
            result = "数据未产生，无效的加班小时数！";
         }
         else
         {
            // 调用添加方法
            otHeaderService.insertOTHeader( otHeaderVO );
            otHeaderService.submitOTHeader( otHeaderVO );
            result = "success";
            insertlog( request, otHeaderVO, Operate.ADD, KANUtil.decodeSelectedIds( otHeaderVO.getOtHeaderId() ), "Mobile Add" );
         }

         out.print( result );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }

      return null;
   }

   // 优化审批加班页面，加班详细状况。例如：OT1.5 - 2小时；OT3.0 - 4小时
   // Added by siuvan @2014-09-10
   private void fetchOTHoursDetail( final OTHeaderVO otHeaderVO, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
      final OTDetailService otDetailService = ( OTDetailService ) getService( "otDetailService" );

      final OTHeaderVO tempOTHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( otHeaderVO.getOtHeaderId() );
      tempOTHeaderVO.reset( mapping, request );

      final List< Object > otDetailVOs = otDetailService.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         double tempOTHours = 0;
         final JSONObject jsonObject = new JSONObject();
         for ( Object otDetailVOObject : otDetailVOs )
         {
            final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

            if ( KANUtil.filterEmpty( otDetailVO.getActualHours() ) != null )
            {
               tempOTHours = Double.valueOf( otDetailVO.getActualHours() );
            }
            else if ( KANUtil.filterEmpty( otDetailVO.getEstimateHours() ) != null )
            {
               tempOTHours = Double.valueOf( otDetailVO.getEstimateHours() );
            }

            if ( jsonObject.get( otDetailVO.getItemId() ) == null )
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( tempOTHours ) );
            }
            else
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( Double.valueOf( String.valueOf( jsonObject.get( otDetailVO.getItemId() ) ) ) + tempOTHours ) );
            }
         }

         tempOTHeaderVO.setOtDetail( jsonObject.toString() );
         tempOTHeaderVO.setDescription( tempOTHeaderVO.getDecodeOTDetail() );

         otHeaderService.updateOTHeader_onlyUP( tempOTHeaderVO );
      }
   }

}
