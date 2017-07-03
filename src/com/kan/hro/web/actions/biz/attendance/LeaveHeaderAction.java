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

import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveDetailService;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class LeaveHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_LEAVE_HEADER";

   // 当前Action对应的Access Action - In House
   public static String accessActionInHouse = "HRO_BIZ_ATTENDANCE_LEAVE_IN_HOUSE";

   // 当前Action对应的Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( !getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return accessAction;
      }
      else
      {
         return accessActionInHouse;
      }
   }

   // 如果职位组是People Manager
   private void isPeopleManager( HttpServletRequest request ) throws KANException
   {
      final String positionId = getPositionId( request, null );
      PositionDTO positionDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionDTOByPositionId( positionId );

      if ( positionDTO != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
      {
         for ( Object o : positionDTO.getPositionGroupRelationVOs() )
         {
            if ( "203".equals( ( ( PositionGroupRelationVO ) o ).getGroupId() ) )
            {
               request.setAttribute( "pm_hide", "1" );
               break;
            }
         }
      }
   }

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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // 获得Action Form
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;

         // TODO 暂时特殊处理
         if ( KANUtil.filterEmpty( leaveHeaderVO.getNotFormatEstimateStartDate() ) != null )
         {
            leaveHeaderVO.setEstimateStartDate( URLDecoder.decode( URLDecoder.decode( ( String ) leaveHeaderVO.getNotFormatEstimateStartDate(), "UTF-8" ), "UTF-8" ) );
         }

         if ( KANUtil.filterEmpty( leaveHeaderVO.getNotFormatEstimateEndDate() ) != null )
         {
            leaveHeaderVO.setEstimateEndDate( URLDecoder.decode( URLDecoder.decode( ( String ) leaveHeaderVO.getNotFormatEstimateEndDate(), "UTF-8" ), "UTF-8" ) );
         }

         if ( new Boolean( ajax ) )
         {
            decodedObject( leaveHeaderVO );
         }

         // 如果没有指定排序则默认按 LeaveId排序
         if ( leaveHeaderVO.getSortColumn() == null || leaveHeaderVO.getSortColumn().isEmpty() )
         {
            leaveHeaderVO.setSortColumn( "leaveHeaderId" );
            leaveHeaderVO.setSortOrder( "desc" );
         }

         //         // 如果是inHouse
         //         if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         //         {
         //            // 如果不具HR职能
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // 创建人、修改人
         //               leaveHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         //               leaveHeaderVO.setCreateBy( getUserId( request, response ) );
         //               leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         //            }
         //            //雇员登录
         //         }
         //         else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
         //         {
         //            leaveHeaderVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
         //         }

         // 权限设置
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, leaveHeaderVO );
         setDataAuth( request, response, leaveHeaderVO );

         // 调用删除方法
         if ( leaveHeaderVO.getSubAction() != null && leaveHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( leaveHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( leaveHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         leaveHeaderService.getLeaveHeaderVOsByCondition( pagedListHolder, ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "leaveHeaderHolder", pagedListHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               pagedListHolder.setSource( leaveHeaderService.exportLeaveDetailByCondition( leaveHeaderVO ) );
               // 刷新国际化
               refreshHolder( pagedListHolder, request );
               request.setAttribute( "holderName", "leaveHeaderHolder" );
               request.setAttribute( "fileName", "请假" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }

            isPeopleManager( request );
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listLeaveTable" );
         }

         isPeopleManager( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listLeave" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 避免重复提交
      this.saveToken( request );

      // InHouse情况下，默认当前登录用户请假
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( LeaveHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }
      else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         ( ( LeaveHeaderVO ) form ).setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
      }

      // 设置Sub Action
      ( ( LeaveHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );

      // 跳转新增界面
      return mapping.findForward( "manageLeave" );
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
         ( ( LeaveHeaderVO ) form ).setTimesheetId( timesheetId );
         ( ( LeaveHeaderVO ) form ).setEmployeeId( employeeId );
         ( ( LeaveHeaderVO ) form ).setClientId( clientId );
         ( ( LeaveHeaderVO ) form ).setCorpId( getCorpId( request, response ) );
         ( ( LeaveHeaderVO ) form ).setContractId( contractId );
         ( ( LeaveHeaderVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );
         ( ( LeaveHeaderVO ) form ).setItemId( null );
         request.setAttribute( "currDay", currDay + " 00:00" );
         request.setAttribute( "nextDay", KANUtil.formatDate( KANUtil.getDate( currDay, 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageLeave" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-27
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         //判断是不是来自微信的请假
         final String from = request.getParameter( "mobile" );
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // 获取当前Form
            final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
            // 保存附件
            leaveHeaderVO.setAttachmentArray( request.getParameterValues( "attachmentArray" ) );
            leaveHeaderVO.setDataFrom( "1" );

            // 返回页面employeeId
            final String employeeId = leaveHeaderVO.getEmployeeId();
            // 用于保存到passObject 员工信息
            final EmployeeVO tempEmployeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
            if ( tempEmployeeVO != null )
            {
               leaveHeaderVO.setEmployeeNameZH( tempEmployeeVO.getNameZH() );
               leaveHeaderVO.setEmployeeNameEN( tempEmployeeVO.getNameEN() );
               leaveHeaderVO.setEmployeeNo( tempEmployeeVO.getEmployeeNo() );
               leaveHeaderVO.setCertificateNumber( tempEmployeeVO.getCertificateNumber() );
            }

            // 校验employeeId是否存在
            checkEmployeeId( mapping, leaveHeaderVO, request, response );

            // 不合法的employeeId跳转新增页面
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "employeeIdErrorMsg" ) ) != null )
            {
               leaveHeaderVO.reset();
               leaveHeaderVO.setEmployeeId( employeeId );
               leaveHeaderVO.setEmployeeNameZH( "" );
               leaveHeaderVO.setEmployeeNameEN( "" );

               return to_objectNew( mapping, leaveHeaderVO, request, response );
            }

            if ( KANUtil.filterEmpty( leaveHeaderVO.getClientId() ) == null )
            {
               final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );

               if ( employeeContractVO != null )
               {
                  leaveHeaderVO.setClientId( employeeContractVO.getClientId() );
               }
            }

            leaveHeaderVO.setActualLegalHours( "0" );
            leaveHeaderVO.setActualBenefitHours( "0" );
            leaveHeaderVO.setRetrieveStatus( "1" );
            leaveHeaderVO.setCreateBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            leaveHeaderVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

            if ( ( "41".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null
                  && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
                  || ( "48".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
                  || ( "49".equals( KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null ) && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 ) )
            {
               warning( request, null, "数据未产生，无效的请假小时数！" );
            }
            else
            {
               // 添加
               int result = leaveHeaderService.insertLeaveHeader( leaveHeaderVO );
               String remark = KANUtil.filterEmpty( from ) != null ? "来自微信的操作" : null;
               if ( result == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), remark );
               }
               else
               {
                  success( request, MESSAGE_TYPE_ADD );
                  insertlog( request, leaveHeaderVO, Operate.ADD, leaveHeaderVO.getLeaveHeaderId(), remark );
               }
            }
         }

         // 清空FORM
         ( ( LeaveHeaderVO ) form ).reset();
         ( ( LeaveHeaderVO ) form ).setEmployeeNameZH( "" );
         ( ( LeaveHeaderVO ) form ).setEmployeeNameEN( "" );
         ( ( LeaveHeaderVO ) form ).setNumber( "" );
         ( ( LeaveHeaderVO ) form ).setEmployeeNo( "" );
         ( ( LeaveHeaderVO ) form ).setCertificateNumber( "" );

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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // 主键获取需解码
         String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( leaveId == null || leaveId.trim().isEmpty() )
         {
            leaveId = ( ( LeaveHeaderVO ) form ).getLeaveHeaderId();
         }

         // 获得LeaveVO对象
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );

         // 销假情况
         if ( KANUtil.filterEmpty( leaveHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( leaveHeaderVO.getActualEndDate() ) != null )
         {
            leaveHeaderVO.setEstimateStartDate( leaveHeaderVO.getActualStartDate() );
            leaveHeaderVO.setEstimateEndDate( leaveHeaderVO.getActualEndDate() );
            leaveHeaderVO.setEstimateLegalHours( leaveHeaderVO.getActualLegalHours() );
            leaveHeaderVO.setEstimateBenefitHours( leaveHeaderVO.getActualBenefitHours() );
         }

         // 刷新对象，初始化对象列表及国际化
         leaveHeaderVO.reset( null, request );
         // 区分修改添加
         leaveHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "leaveHeaderForm", leaveHeaderVO );

         // 初始化leftHours
         double leftHours = 0.0;

         // 初始化hours
         double hours = Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) + Double.valueOf( leaveHeaderVO.getEstimateLegalHours() );

         // 获取EmployeeContractLeaveVO列表
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( leaveHeaderVO.getContractId() );

         if ( employeeContractLeaveVOs != null )
         {
            for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
            {
               if ( employeeContractLeaveVO.getItemId().equals( leaveHeaderVO.getItemId() ) )
               {
                  boolean isLimit = false;

                  // 休假是否无限制
                  if ( ( "41".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                        || ( "48".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null )
                        || ( "49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                        || ( !"41".equals( employeeContractLeaveVO.getItemId() ) && !"48".equals( employeeContractLeaveVO.getItemId() )
                              && !"49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null ) )
                  {
                     isLimit = true;
                  }

                  if ( !isLimit )
                  {
                     leftHours = Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() ) + Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                  }
                  else
                  {
                     leftHours = 1000;
                  }
               }
            }
         }

         final String leaveEndDate = getLeaveEndDate( leaveHeaderVO.getItemId(), getAccountId( request, null ), leaveHeaderVO.getContractId(), leaveHeaderVO.getEstimateStartDate(), leftHours, hours, null );
         // 如果是主管
         isPeopleManager( request );
         request.setAttribute( "hasDeleteRight", hasDeleteRight( leaveHeaderVO ) );
         // 写入request
         request.setAttribute( "leaveEndDate", leaveEndDate );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转修改页面
      return mapping.findForward( "manageLeave" );
   }

   @Override
   // Reviewed by Kevin Jin at 2014-04-20
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );
            // 主键获取需解码
            final String leaveHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获得LeaveHeaderVO对象
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );

            // 装载界面传值
            leaveHeaderVO.update( ( ( LeaveHeaderVO ) form ) );
            // 保存附件
            leaveHeaderVO.setAttachmentArray( request.getParameterValues( "attachmentArray" ) );

            // 如果是销假，写入实际时间
            if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( "SICK_LEAVE" ) )
            {
               leaveHeaderVO.setActualStartDate( ( ( LeaveHeaderVO ) form ).getEstimateStartDate() );
               leaveHeaderVO.setActualEndDate( ( ( LeaveHeaderVO ) form ).getEstimateEndDate() );
               leaveHeaderVO.setActualLegalHours( ( ( LeaveHeaderVO ) form ).getEstimateLegalHours() );
               leaveHeaderVO.setActualBenefitHours( ( ( LeaveHeaderVO ) form ).getEstimateBenefitHours() );
            }
            else
            {
               leaveHeaderVO.setEstimateStartDate( ( ( LeaveHeaderVO ) form ).getEstimateStartDate() );
               leaveHeaderVO.setEstimateEndDate( ( ( LeaveHeaderVO ) form ).getEstimateEndDate() );
               leaveHeaderVO.setEstimateLegalHours( ( ( LeaveHeaderVO ) form ).getEstimateLegalHours() );
               leaveHeaderVO.setEstimateBenefitHours( ( ( LeaveHeaderVO ) form ).getEstimateBenefitHours() );
            }

            // 获取登录用户
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyDate( new Date() );

            // 如果是客户提交
            if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               leaveHeaderVO.reset( mapping, request );

               if ( leaveHeaderService.submitLeaveHeader( leaveHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, leaveHeaderVO, Operate.MODIFY, leaveHeaderVO.getLeaveHeaderId(), null );
               }
            }
            // 如果客户是销假
            else if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( "SICK_LEAVE" ) )
            {
               leaveHeaderVO.reset( mapping, request );

               if ( leaveHeaderService.sickLeaveHeader( leaveHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), "销假" );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, leaveHeaderVO, Operate.MODIFY, leaveHeaderVO.getLeaveHeaderId(), "销假" );
               }
            }
            else
            {
               leaveHeaderService.updateLeaveHeader( leaveHeaderVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, leaveHeaderVO, Operate.MODIFY, leaveHeaderVO.getLeaveHeaderId(), null );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      if ( "mobile".equalsIgnoreCase( request.getParameter( "from" ) ) )
      {
         return this.list_object_mobile( mapping, form, request, response );
      }
      else
      {
         return to_objectModify( mapping, form, request, response );
      }
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   // Leave detail页面删除请假记录
   // Add by siuvan.xia @ 2014-07-03
   public ActionForward delete_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // 获取要删除的ID
         final String leaveHeaderId = request.getParameter( "leaveHeaderId" );

         // 获取LeaveHeaderVO
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyDate( new Date() );

         if ( leaveHeaderService.deleteLeaveHeader_cleanTS( leaveHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_DELETE );
            insertlog( request, leaveHeaderVO, Operate.DELETE, leaveHeaderId, null );
         }

         return list_object( mapping, form, request, response );
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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // 获得Action Form
         LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;

         // 存在选中的ID
         if ( KANUtil.filterEmpty( leaveHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, leaveHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( leaveHeaderVO.getSelectedIds() ) );
            // 分割
            for ( String selectedId : leaveHeaderVO.getSelectedIds().split( "," ) )
            {
               leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               leaveHeaderVO.setModifyBy( getUserId( request, response ) );
               leaveHeaderVO.setModifyDate( new Date() );

               if ( leaveHeaderService.deleteLeaveHeader( leaveHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_DELETE );
               }
            }
         }

         // 清除Selected IDs和子Action
         ( ( LeaveHeaderVO ) form ).setSelectedIds( "" );
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Reviewed by Kevin Jin at 2013-11-26
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // 获得主键需解码
         final String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得LeaveVO
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyDate( new Date() );
         leaveHeaderVO.reset( mapping, request );

         if ( leaveHeaderService.submitLeaveHeader( leaveHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveId, null );
         }
         else
         {
            error( request, MESSAGE_TYPE_SUBMIT );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 批量提交
   // Added by siuxia at 2014-06-04
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // 获得勾选ID
         final String leaveHeaderIds = leaveHeaderVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( leaveHeaderIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = leaveHeaderIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String selectId : selectedIdArray )
            {
               // 获得LeaveVO
               final LeaveHeaderVO submitLeaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
               submitLeaveHeaderVO.setModifyBy( getUserId( request, response ) );
               submitLeaveHeaderVO.setModifyDate( new Date() );
               submitLeaveHeaderVO.reset( mapping, request );

               rows = rows + leaveHeaderService.submitLeaveHeader( submitLeaveHeaderVO );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, leaveHeaderVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( leaveHeaderIds ) );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, leaveHeaderVO, Operate.MODIFY, null, KANUtil.decodeSelectedIds( leaveHeaderIds ) );
            }
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Reviewed by Kevin Jin at 2013-11-25
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取leaveHeaderId
         final String leaveHeaderId = request.getParameter( "leaveHeaderId" );

         // 获取服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 初始化Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // 显示Tab
         final String noTab = request.getParameter( "noTab" );

         request.setAttribute( "listAttachmentCount", "0" );

         if ( KANUtil.filterEmpty( contractId ) != null )
         {
            // 获取EmployeeContractLeaveVO列表
            final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );

            if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
               {
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "en" ) && employeeContractLeaveVO.getEmployeeLeaveId().contains( "来自结算规则设置" ) )
                  {
                     employeeContractLeaveVO.setEmployeeLeaveId( employeeContractLeaveVO.getEmployeeLeaveId().replace( "来自结算规则设置", "From Calculation Rules" ) );
                  }
                  employeeContractLeaveVO.reset( mapping, request );
               }

               request.setAttribute( "employeeContractLeaveVOs", employeeContractLeaveVOs );
               request.setAttribute( "countEmployeeContractLeaveVO", employeeContractLeaveVOs.size() );
            }
         }

         if ( KANUtil.filterEmpty( leaveHeaderId ) != null )
         {
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
            if ( leaveHeaderVO != null )
            {
               request.setAttribute( "listAttachmentCount", leaveHeaderVO.getAttachmentArray().length );
            }
            request.setAttribute( "leaveHeaderForm", leaveHeaderVO );
         }

         if ( KANUtil.filterEmpty( noTab ) != null && KANUtil.filterEmpty( noTab ).equals( "true" ) )
         {
            return mapping.findForward( "manageLeaveSpecialTable" );
         }

         return mapping.findForward( "manageLeaveSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Reviewed by Kevin Jin at 2013-11-25
   public ActionForward item_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 科目ID
         final String itemId = request.getParameter( "itemId" );

         // 初始化 Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获得EmployeeContractLeaveVO列表
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // 获得ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // 试用期月份
         String probationMonth = "0";
         if ( employeeContractVO != null && KANUtil.filterEmpty( employeeContractVO.getProbationMonth(), "0" ) != null )
         {
            probationMonth = employeeContractVO.getProbationMonth();
         }

         if ( KANUtil.filterEmpty( probationMonth, "0" ) == null && clientOrderHeaderVO != null )
         {
            probationMonth = clientOrderHeaderVO.getProbationMonth();
         }

         // 初始化JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractVO == null || ( employeeContractVO != null && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) == null ) )
         {
            jsonObject.put( "startDate", "" );
            jsonObject.put( "endDate", "" );
         }
         else
         {
            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
               employeeContractVO.setEndDate( "2199-12-31 00:00:00" );

            final String contractEndDate = employeeContractVO.getStatus().equals( "7" ) ? employeeContractVO.getResignDate() : employeeContractVO.getEndDate();
            if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
            {
               for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
               {
                  final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;

                  if ( employeeContractLeaveVO.getItemId() != null && employeeContractLeaveVO.getItemId().equals( itemId ) && employeeContractLeaveVO.getStatus() != null
                        && employeeContractLeaveVO.getStatus().equals( "1" ) )
                  {
                     int year = 0;
                     String startDate = null;
                     String endDate = null;

                     // 如果是年假
                     if ( "41".equals( itemId ) )
                     {
                        endDate = employeeContractLeaveVO.getYear() + "-12-31 00:00";
                        year = Integer.valueOf( employeeContractLeaveVO.getYear() );

                        if ( KANUtil.filterEmpty( employeeContractLeaveVO.getUseNextYearHours() ) == null || !"1".equals( employeeContractLeaveVO.getUseNextYearHours() ) )
                        {
                           endDate = extendAnnualLeaveUsedPeriod( endDate, year, ( employeeContractLeaveVO.getBenefitQuantityDelayMonth() == null ? 0
                                 : Integer.valueOf( employeeContractLeaveVO.getBenefitQuantityDelayMonth() ) ) );
                        }
                     }
                     // 如果是年假（去年）
                     else if ( "48".equals( itemId ) )
                     {
                        Date legalDelayDate = KANUtil.createDate( employeeContractLeaveVO.getLeftLastYearLegalQuantityEndDate() );
                        endDate = KANUtil.formatDate( legalDelayDate, null ) + " 00:00";
                        year = Integer.valueOf( employeeContractLeaveVO.getYear() ) + 1;
                     }
                     else if ( "49".equals( itemId ) )
                     {
                        Date benefitDelayDate = KANUtil.createDate( employeeContractLeaveVO.getLeftLastYearBenefitQuantityEndDate() );
                        endDate = KANUtil.formatDate( benefitDelayDate, null ) + " 00:00";
                        year = Integer.valueOf( employeeContractLeaveVO.getYear() ) + 1;
                     }
                     else
                     {
                        endDate = KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), null ) + " 00:00";
                     }

                     if ( "41".equals( itemId ) || "48".equals( itemId ) || "49".equals( itemId ) )
                     {
                        // 试用期不可用
                        if ( "2".equalsIgnoreCase( employeeContractLeaveVO.getProbationUsing() ) )
                        {
                           // 年假3个月才能用
                           Calendar probationEndCalendar = KANUtil.getCalendar( KANUtil.getDate( employeeContractVO.getStartDate(), 0, 3 ) );
                           startDate = KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
                        }
                        else
                        {
                           startDate = KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" );
                        }

                        // 如果是次年年假
                        if ( year == Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) + 1 )
                        {
                           startDate = year + "-01-01";
                        }
                     }
                     else
                     {
                        startDate = getAvailableLeaveStartDate( employeeContractVO.getStartDate(), employeeContractLeaveVO.getCycle(), employeeContractLeaveVO.getProbationUsing(), probationMonth, employeeContractVO.getProbationEndDate(), String.valueOf( year ) );
                     }

                     jsonObject.put( "startDate", startDate + " 00:00" );
                     jsonObject.put( "endDate", endDate );
                     jsonObject.put( "itemId", employeeContractLeaveVO.getItemId() );
                     break;
                  }
               }
            }

            // 处理日期插件补假，只能在当前时间前一个月
            if ( jsonObject.get( "startDate" ) != null )
            {
               Date nowBeforeOneMonth = KANUtil.getDate( new Date(), 0, -1 );
               Date currStartDate = KANUtil.createDate( jsonObject.get( "startDate" ).toString() );

               if ( nowBeforeOneMonth.getTime() > currStartDate.getTime() )
               {
                  jsonObject.put( "startDate", KANUtil.formatDate( nowBeforeOneMonth, "yyyy-MM-dd" ) + " 00:00" );
               }
            }

            // 处理日期插件获取焦点时，默认选中日期不在请假范围内；
            if ( jsonObject.get( "startDate" ) != null )
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

         // 给请假带上默认时间（按排班来）Add by fox
         if ( jsonObject.get( "defDate" ) != null )
         {
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

            final Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime( KANUtil.createDate( jsonObject.get( "defDate" ).toString() ) );

            ShiftDetailVO shiftDetailVO = new TimesheetDTO().getShiftDetailVO( getAccountId( request, null ), calendarId, shiftId, KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ), TimesheetDTO.EXCEPTION_TYPE_LEAVE );

            if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
            {
               final String[] shiftPeriods = KANUtil.jasonArrayToStringArray( shiftDetailVO.getShiftPeriod() );
               int startPeriod = Integer.valueOf( shiftPeriods[ 0 ] ) - 1;
               int endPeriod = Integer.valueOf( shiftPeriods[ shiftPeriods.length - 1 ] );
               String tempStartDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( startPeriod ) / 2 + ":" + Integer.valueOf( startPeriod )
                     % 2;
               String tempEndDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( endPeriod ) / 2 + ":" + Integer.valueOf( endPeriod ) % 2
                     * 30;

               jsonObject.put( "defDate", KANUtil.formatDate( tempStartDate, "yyyy-MM-dd HH:mm:ss" ) );
               jsonObject.put( "defEndDate", KANUtil.formatDate( tempEndDate, "yyyy-MM-dd HH:mm:ss" ) );
            }
         }

         // 判断这个默认的时间是否超过当前请假剩余小时数的结束时间 Add by siuvan
         if ( jsonObject.get( "defEndDate" ) != null )
         {
            double leftHours = 0;
            if ( employeeContractLeaveVOs != null )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
               {
                  if ( employeeContractLeaveVO.getItemId().equals( itemId ) )
                  {
                     boolean isLimit = false;

                     // 休假是否无限制
                     if ( ( "41".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                           || ( "48".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity(), "0" ) == null )
                           || ( "49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null )
                           || ( !"41".equals( employeeContractLeaveVO.getItemId() ) && !"48".equals( employeeContractLeaveVO.getItemId() )
                                 && !"49".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getBenefitQuantity(), "0" ) == null ) )
                     {
                        isLimit = true;
                     }

                     if ( !isLimit )
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() ) + Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                     }
                     else
                     {
                        leftHours = 1000;
                     }
                  }
               }
            }

            String tmpLeaveEndDate = getLeaveEndDate( itemId, getAccountId( request, null ), contractId, jsonObject.getString( "defDate" ), leftHours, 0, null );
            if ( KANUtil.createCalendar( jsonObject.getString( "defEndDate" ) ).getTime().getTime() > KANUtil.createCalendar( tmpLeaveEndDate ).getTime().getTime() )
            {
               jsonObject.discard( "defEndDate" );
               jsonObject.put( "defEndDate", KANUtil.formatDate( tmpLeaveEndDate, "yyyy-MM-dd HH:mm:ss" ) );
            }

            jsonObject.put( "tmpLeaveEndDate", tmpLeaveEndDate );
         }

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

   /****
    * extendAnnualLeaveUsedPeriod 扩充年假使用期间
    * 1、每年10月份才扩充；
    * 2、扩充月数为年假延迟使用月数
    * 参数1：本来年假使用结束日
    * 参数2：当前年份
    * 参数3：年假延迟月数
    */
   private String extendAnnualLeaveUsedPeriod( String initEndDate, int year, int delayMonth )
   {
      if ( Integer.valueOf( KANUtil.formatDate( new Date(), "MM" ) ) >= 10 )
      {
         return KANUtil.formatDate( KANUtil.getDate( year + "-12-31", 0, delayMonth ), "yyyy-MM-dd HH:mm" );
      }

      return initEndDate;
   }

   // 根据请假所剩时间，算出请假截至时间
   public String getLeaveEndDate( final String itemId, final String accountId, final String contractId, final String startDate, final double leftHours, final double hours,
         final String maxDate ) throws KANException
   {
      // 初始化返回值
      String leaveEndDate = "";

      // 初始化请假剩余时间
      double leaveLeftHours = 0.0;
      leaveLeftHours = leftHours;

      if ( leftHours != -1 && KANUtil.filterEmpty( hours ) != null )
      {
         leaveLeftHours = leaveLeftHours + hours;
      }

      if ( leaveLeftHours == -1 || leaveLeftHours > 0 )
      {
         // 初始化Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获得服务协议
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
            employeeContractVO.setEndDate( "2199-12-31 00:00:00" );

         // 获得服务订单
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         final String contractEndDate = employeeContractVO.getStatus().equals( "7" ) ? employeeContractVO.getResignDate() : employeeContractVO.getEndDate();

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

         if ( leaveLeftHours == -1 && employeeContractVO != null && KANUtil.filterEmpty( calendarId, "0" ) != null && KANUtil.filterEmpty( shiftId, "0" ) != null )
         {
            return KANUtil.formatDate( KANUtil.getDate( contractEndDate, 0, 0, 1 ), "yyyy-MM-dd HH:mm:ss" );
         }

         if ( KANUtil.filterEmpty( calendarId, "0" ) != null && KANUtil.filterEmpty( shiftId, "0" ) != null )
         {
            String endLeaveDatetime = new TimesheetDTO().getEndLeaveDatetime( itemId, accountId, calendarId, shiftId, startDate, Double.valueOf( leftHours ) );

            String endDate = maxDate == null ? contractEndDate : maxDate;

            if ( KANUtil.getDays( KANUtil.createCalendar( endLeaveDatetime ) ) < KANUtil.getDays( KANUtil.getDate( endDate, 0, 0, 1 ) ) )
            {
               leaveEndDate = endLeaveDatetime;
            }
            else
            {
               leaveEndDate = KANUtil.formatDate( KANUtil.getDate( endDate, 0, 0, 1 ), "yyyy-MM-dd HH:mm:ss" );
            }

            if ( "13:".equals( KANUtil.formatDate( leaveEndDate, "HH:" ) ) )
            {
               leaveEndDate = leaveEndDate.replace( "13:", "14:" );
            }
         }
      }

      return leaveEndDate;
   }

   /**
    * 开始日期控件失去焦点，ajax调用后台
    * 根据请假所剩时间，算出截至时间
    */
   // Reviewed by Kevin Jin at 2013-11-26
   public ActionForward get_endDate_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );

         // 获得开始时间
         final String startDate = request.getParameter( "date" );

         final String itemId = request.getParameter( "itemId" );

         // 获取剩余小时数
         Double leftHours = Double.valueOf( request.getParameter( "leftHours" ) );

         // 获取修改时用掉小时数
         double hours = 0.0;
         if ( KANUtil.filterEmpty( request.getParameter( "hours" ) ) != null )
         {
            hours = Double.valueOf( request.getParameter( "hours" ) );
         }

         // forTimesheet
         final String maxDate = request.getParameter( "maxDate" );

         if ( leftHours != -1 && KANUtil.filterEmpty( hours ) != null )
         {
            leftHours = leftHours + Double.valueOf( hours );
         }

         // Send to client
         out.println( getLeaveEndDate( itemId, getAccountId( request, null ), contractId, startDate, leftHours, hours, maxDate ) );
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
    * 开始、结束日期都不为空，算出请假跨度时间（小时）
    */
   // Reviewed by Kevin Jin at 2013-11-26
   public ActionForward calculate_leave_hours_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 开始时间
         final String startDate = request.getParameter( "startDate" );

         // 结束时间
         final String endDate = request.getParameter( "endDate" );

         final String itemId = request.getParameter( "itemId" );

         // 初始化Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // 获得ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

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

         double hours = new TimesheetDTO().getLeaveHours( itemId, getAccountId( request, response ), calendarId, shiftId, startDate, endDate );

         // Send to client
         out.print( hours );
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
    * 
   * 	Get Available Leave Start Date
   *  获取有效开始时间
   *	
   *	@param startDate
   *	@param cycle
   *	@param probationUsing
   *	@param months
   *  @param probationEndDate
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-26
   private static String getAvailableLeaveStartDate( final String startDate, final String cycle, final String probationUsing, final String months, final String probationEndDate,
         final String year ) throws KANException
   {
      try
      {
         // 考虑到试用期月份
         Calendar probationEndCalendar = KANUtil.getCalendar( KANUtil.getDate( startDate, 0, KANUtil.filterEmpty( probationUsing, "0" ) != null
               && probationUsing.trim().equals( "1" ) ? 0 : Integer.parseInt( KANUtil.filterEmpty( months ) != null ? months : "0" ) ) );

         if ( KANUtil.filterEmpty( probationUsing, "0" ) != null && KANUtil.filterEmpty( probationUsing, "0" ).equals( "2" ) && KANUtil.filterEmpty( probationEndDate ) != null )
         {
            if ( KANUtil.getDays( probationEndCalendar ) > KANUtil.getDays( KANUtil.createCalendar( probationEndDate ) ) )
            {
               probationEndCalendar = KANUtil.createCalendar( probationEndDate );
            }
         }

         // 服务期：服务协议开始、结束时间
         if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "1" ) )
         {
            return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
         }
         // 日历年
         else if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "2" ) )
         {
            if ( probationEndCalendar.get( Calendar.YEAR ) >= KANUtil.getCalendar( new Date() ).get( Calendar.YEAR ) )
            {
               return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
            }
            else
            {
               return KANUtil.getCalendar( new Date() ).get( Calendar.YEAR ) + "-01-01";
            }
         }
         // 服务年
         else if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "3" ) )
         {
            final Calendar nextYearStartDateCalendar = KANUtil.getCalendar( KANUtil.getDate( startDate, 1 ) );

            if ( KANUtil.getDays( nextYearStartDateCalendar ) > KANUtil.getDays( new Date() ) )
            {
               return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
            }
            else
            {
               while ( KANUtil.getDays( nextYearStartDateCalendar ) <= KANUtil.getDays( new Date() ) )
               {
                  nextYearStartDateCalendar.add( Calendar.YEAR, 1 );
               }

               // 回退
               nextYearStartDateCalendar.add( Calendar.YEAR, -1 );
               return KANUtil.formatDate( nextYearStartDateCalendar.getTime(), "yyyy-MM-dd" );
            }
         }
         // 固定期
         else if ( KANUtil.filterEmpty( cycle ) != null && cycle.equals( "5" ) )
         {
            Calendar leaveStartCalendar = KANUtil.createCalendar( year + "-01-01" );
            if ( leaveStartCalendar.getTimeInMillis() < probationEndCalendar.getTimeInMillis() )
            {
               return KANUtil.formatDate( probationEndCalendar.getTime(), "yyyy-MM-dd" );
            }
            else
            {
               return KANUtil.formatDate( leaveStartCalendar.getTime(), "yyyy-MM-dd" );
            }
         }
         else
         {
            return startDate;
         }
      }
      catch ( Exception e )
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
      final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;

      // 试图获取EmployeeVO 
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( leaveHeaderVO.getEmployeeId() );

      // 不存在EmployeeVO或AccountId不匹配当前
      if ( employeeVO == null
            || ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getAccountId() ) != null && !employeeVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "employeeIdErrorMsg", ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "员工" : "雇员" ) + "ID无效；" );
      }

      if ( !checkIsPass( request, leaveHeaderVO.getEmployeeId() ) )
      {
         request.setAttribute( "employeeIdErrorMsg", "非HR职能只能为自己或同一部门员工请假；" );
      }

   }

   /**  
    * checkIsPass
    * 加班、请假申请（自己以及同部门）inHouse非hrBranch情况下
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public static boolean checkIsPass( final HttpServletRequest request, final String employeeId ) throws KANException
   {
      // 非InHouse无需验证通过
      if ( !getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return true;
      }

      // InHouse下具有HR职能可为任何人请假
      if ( isHRFunction( request, null ) )
      {
         return true;
      }
      // InHouse下非HR部门职能为自己或是同部门员工请假
      else
      {
         // 如果给自己请假
         if ( KANUtil.filterEmpty( getEmployeeId( request, null ) ) != null && KANUtil.filterEmpty( employeeId ) != null && getEmployeeId( request, null ).equals( employeeId ) )
         {
            return true;
         }
         // 给同一部门员工请假
         else
         {
            // 获取branchId
            final String branchId = getBranchId( request, null );

            // 根据employeeId获取StaffDTO列表
            final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffDTOsByEmployeeId( employeeId );

            // 存在StaffDTO列表
            if ( staffDTOs != null && staffDTOs.size() > 0 )
            {
               // 默认去第一个StaffDTO
               final StaffDTO staffDTO = staffDTOs.get( 0 );

               // 根据staffId获取PositionDTO列表
               final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionDTOsByStaffId( staffDTO.getStaffVO().getStaffId() );

               // 存在PositionDTO列表
               if ( branchId != null && positionDTOs != null && positionDTOs.size() > 0 )
               {
                  // 遍历部门是否一致
                  for ( PositionDTO positionDTO : positionDTOs )
                  {
                     if ( positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getBranchId() ) != null
                           && positionDTO.getPositionVO().getBranchId().equals( branchId ) )
                     {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   // check_leave_date检查请假日期是否重复
   public void check_leave_date( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();

         // 初始化Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

         // 获取参数
         final String leaveHeaderId = request.getParameter( "leaveHeaderId" );
         final String employeeId = request.getParameter( "employeeId" );
         final String contractId = request.getParameter( "contractId" );
         final String startDate = request.getParameter( "startDate" );
         final String endDate = request.getParameter( "endDate" );

         final long startDate_s = KANUtil.createDate( startDate ).getTime();
         final long endDate_s = KANUtil.createDate( endDate ).getTime();

         // 实例化LeaveHeaderVO
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setAccountId( getAccountId( request, null ) );
         leaveHeaderVO.setCorpId( getCorpId( request, null ) );
         leaveHeaderVO.setEmployeeId( employeeId );
         leaveHeaderVO.setContractId( contractId );

         boolean flag = false;

         // 获取LeaveHeaderVO列表
         final List< LeaveHeaderVO > leaveHeaderVOs = leaveHeaderService.getLeaveHeaderVOsByCondition( leaveHeaderVO );

         // 存在LeaveHeaderVO列表
         if ( leaveHeaderVOs != null && leaveHeaderVOs.size() > 0 )
         {
            for ( LeaveHeaderVO tempLeaveHeaderVO : leaveHeaderVOs )
            {
               if ( KANUtil.filterEmpty( leaveHeaderId ) != null && tempLeaveHeaderVO.getLeaveHeaderId().trim().equals( KANUtil.decodeString( leaveHeaderId ) ) )
               {
                  continue;
               }

               if ( "4".equals( tempLeaveHeaderVO.getStatus() ) )
                  continue;

               // 如果当前请假时间段与原有记录交叉
               if ( startDate_s >= KANUtil.createDate( tempLeaveHeaderVO.getEstimateEndDate() ).getTime()
                     || endDate_s <= KANUtil.createDate( tempLeaveHeaderVO.getEstimateStartDate() ).getTime() )
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

      // InHouse情况下，默认当前登录用户请假
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( LeaveHeaderVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
         boolean isForeign = false;
         PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionVOByPositionId( getPositionId( request, null ) );
         if ( employeeVO != null )
         {
            String remark1 = employeeVO.getRemark1();
            JSONObject remarkObject = JSONObject.fromObject( remark1 );
            Object locobject = remarkObject.get( "bangongdidian" );
            if ( locobject != null )
            {
               String loc = String.valueOf( locobject );
               // 57 澳大利亚,106伦敦
               if ( "57".equals( loc ) || "106".equals( loc ) )
               {
                  isForeign = true;
               }
            }
         }

         // BU > iclick SEA > 09:00 ~ 18:00
         if ( positionVO != null )
         {
            BranchVO branchVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( positionVO.getBranchId() );
            if ( branchVO != null )
            {
               BranchVO parentBranchVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( branchVO.getParentBranchId() );
               if ( parentBranchVO != null && "1766".equals( parentBranchVO.getBranchId() ) )
               {
                  isForeign = false;
               }
            }
         }
         request.setAttribute( "isForeign", isForeign );
      }

      // 设置Sub Action
      ( ( LeaveHeaderVO ) form ).setSubAction( SUBMIT_OBJECT );
      ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );

      // 跳转新增界面
      return mapping.findForward( "manageLeaveMobile" );
   }

   public ActionForward list_special_info_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 初始化Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         
         JSONObject jsonResult = new JSONObject();

         JSONArray jsonArray = new JSONArray();

         if ( KANUtil.filterEmpty( contractId ) != null )
         {
            // 获取EmployeeContractLeaveVO列表
            final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );

            if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
               {
                  employeeContractLeaveVO.reset( mapping, request );
                  JSONObject jsonObject = JSONObject.fromObject( employeeContractLeaveVO );
                  jsonArray.add( jsonObject );
               }

            }
        
             // 年假数据
             jsonResult.put("employeeContractLeaveData", jsonArray);
             // 在试用期, 并且试用期不能用年假
             jsonResult.put("inProbationAndCannotUse", inProbationAndCannotUse(contractId,employeeContractLeaveVOs));
             out.print( jsonResult.toString() );
         }
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
   
   /**
    * 
    * @param employeeContractLeaveVOs
    * @return 在试用期, 并且试用期不能用年假
    * true 如果在试用期, 并且试用期不能用年假, 则打开页面事假选项
    * false 否则禁用页面事假选项
    */
   private boolean inProbationAndCannotUse(String contractId,List< EmployeeContractLeaveVO > employeeContractLeaveVOs) throws Exception{
     boolean probationCannotUse = false;
     if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 ){
       final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
       EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId(contractId);
       for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
       {
          final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;
          String itemId = employeeContractLeaveVO.getItemId();
          if ( "41".equals( itemId ) || "48".equals( itemId ) || "49".equals( itemId ) ){
            // 试用期不可用
            if ( "2".equalsIgnoreCase( employeeContractLeaveVO.getProbationUsing() ) )
            {
              Calendar probationEndCalendar = KANUtil.getCalendar( KANUtil.getDate( employeeContractVO.getStartDate(), 0, 3 ) );
              Calendar now = Calendar.getInstance();
              probationCannotUse = now.before(probationEndCalendar);
              if(probationCannotUse){
                break;
              }
            }
          }
       }
     }
     return probationCannotUse;
   }

   public ActionForward submit_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );

            // 获得主键需解码
            final String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得LeaveVO
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyDate( new Date() );
            leaveHeaderVO.reset( mapping, request );
         }
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private void appendAuditor_mobile( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( ( "2".equals( leaveHeaderVO.getStatus() ) || "2".equals( leaveHeaderVO.getRetrieveStatus() ) ) && KANUtil.filterEmpty( leaveHeaderVO.getWorkflowId() ) != null )
      {
         // 初始化Service接口
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );
         // 获得Action Form
         final WorkflowActualStepsVO workflowActualStepsVO = new WorkflowActualStepsVO();
         workflowActualStepsVO.setAccountId( leaveHeaderVO.getAccountId() );
         workflowActualStepsVO.setCorpId( leaveHeaderVO.getCorpId() );
         // 获得当前主键
         workflowActualStepsVO.setWorkflowId( leaveHeaderVO.getWorkflowId() );
         // 默认按审批步骤升序排列
         workflowActualStepsVO.setSortColumn( "stepIndex" );
         workflowActualStepsVO.setStepType( "1,2" );
         workflowActualStepsVO.setSortOrder( "asc" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder workflowActualStepsHolder = new PagedListHolder();
         // 传入当前值对象
         workflowActualStepsHolder.setObject( workflowActualStepsVO );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowActualStepsService.getWorkflowActualStepsVOByCondition( workflowActualStepsHolder, false );

         if ( workflowActualStepsHolder != null && workflowActualStepsHolder.getSource() != null && workflowActualStepsHolder.getSource().size() > 0 )
         {
            for ( Object o : workflowActualStepsHolder.getSource() )
            {
               final WorkflowActualStepsVO vo = ( WorkflowActualStepsVO ) o;
               if ( "2".equals( vo.getStatus() ) )
               {
                  leaveHeaderVO.setAuditorZH( vo.getPositionTitleZH() );
                  leaveHeaderVO.setAuditorEN( vo.getPositionTitleEN() );
                  break;
               }
            }
         }
      }
   }

   // 获取自己的请假和加班
   public ActionForward list_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final String page = request.getParameter( "page" );
         final PagedListHolder pagedListHolderLeave = new PagedListHolder();
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         leaveHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            leaveHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         leaveHeaderVO.setSortColumn( "estimateStartDate" );
         leaveHeaderVO.setSortOrder( "desc" );
         pagedListHolderLeave.setObject( leaveHeaderVO );
         pagedListHolderLeave.setPageSize( listPageSize );
         pagedListHolderLeave.setPage( page );
         leaveHeaderService.getLeaveHeaderVOsByCondition( pagedListHolderLeave, true );
         for ( Object object : pagedListHolderLeave.getSource() )
         {
            ( ( LeaveHeaderVO ) object ).reset( null, request );
            appendAuditor_mobile( ( LeaveHeaderVO ) object );
         }

         // 加班的
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final PagedListHolder pagedListHolderOT = new PagedListHolder();
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         otHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            otHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         otHeaderVO.setSortColumn( "unread desc,estimateStartDate" );
         otHeaderVO.setSortOrder( "desc" );
         pagedListHolderOT.setObject( otHeaderVO );
         pagedListHolderOT.setPageSize( listPageSize );
         pagedListHolderOT.setPage( page );
         otHeaderService.getOTHeaderVOsByCondition( pagedListHolderOT, true );
         for ( Object object : pagedListHolderOT.getSource() )
         {
            ( ( OTHeaderVO ) object ).reset( null, request );
         }

         request.setAttribute( "pagedListHolderLeave", pagedListHolderLeave );
         request.setAttribute( "pagedListHolderOT", pagedListHolderOT );
         if ( new Boolean( request.getParameter( "ajax" ) ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // 初始化PrintWrite对象
            final PrintWriter out = response.getWriter();
            final JSONObject jsonObject = new JSONObject();
            final JSONArray leaveJsonArray = JSONArray.fromObject( pagedListHolderLeave.getSource() );
            final JSONArray otJsonArray = JSONArray.fromObject( pagedListHolderOT.getSource() );
            jsonObject.put( "leave", leaveJsonArray );
            jsonObject.put( "ot", otJsonArray );
            // 默认请假比较大  因为请假加班写到一个页面
            final boolean muchLeave = pagedListHolderLeave.getPageCount() > pagedListHolderOT.getPageCount();
            jsonObject.put( "page", Integer.parseInt( page ) );
            jsonObject.put( "realPage", muchLeave ? pagedListHolderLeave.getRealPage() : pagedListHolderOT.getRealPage() );
            jsonObject.put( "leave_pageCount", pagedListHolderLeave.getPageCount() );
            jsonObject.put( "ot_pageCount", pagedListHolderOT.getPageCount() );
            jsonObject.put( "nextPage", muchLeave ? pagedListHolderLeave.getNextPage() : pagedListHolderOT.getNextPage() );
            jsonObject.put( "pageSize", listPageSize );
            out.print( jsonObject.toString() );
            out.flush();
            out.close();
            return null;

         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listLeaveOT" );
   }

   // 获取自己的请假和加班
   public ActionForward readMessage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         leaveHeaderVO.setCreateBy( getUserId( request, response ) );
         leaveHeaderService.read_Leave( leaveHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return null;
   }

   // 请假删除权限
   // Add by siuvan.xia at 2014-07-03
   private boolean hasDeleteRight( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      // 如果是新建状态
      if ( KANUtil.filterEmpty( leaveHeaderVO.getStatus() ) != null && leaveHeaderVO.getStatus().equals( "1" ) )
         return true;

      // 是否有工作流
      if ( KANUtil.filterEmpty( leaveHeaderVO.getWorkflowId() ) != null )
         return false;

      // 是否有销假
      if ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ) != null && Integer.valueOf( leaveHeaderVO.getRetrieveStatus() ) > 1 )
         return false;

      // 初始化LeaveDetailService
      final LeaveDetailService leaveDetailService = ( LeaveDetailService ) getService( "leaveDetailService" );

      // 获取leaveDetailVO列表
      final List< Object > leaveDetailVOs = leaveDetailService.getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

      int count = 0;
      // 若关联考勤表并且为“新建”或“退回”状态
      if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
      {
         // 初始化TimesheetHeaderService
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         for ( Object leaveDetailVOObject : leaveDetailVOs )
         {
            if ( KANUtil.filterEmpty( ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId() ) != null )
            {
               final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId() );
               if ( timesheetHeaderVO != null && ( timesheetHeaderVO.getStatus().equals( "1" ) || timesheetHeaderVO.getStatus().equals( "4" ) ) )
                  count++;
               else
                  return false;
            }
         }
      }

      if ( count == leaveDetailVOs.size() )
         return true;

      return true;
   }

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "请假ID" );
      nameZHs.add( "请假类别" );
      nameZHs.add( "开始时间" );
      nameZHs.add( "结束时间" );
      nameZHs.add( "请假小时" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（中文）" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（英文）" );
      nameZHs.add( ( role.equals( "1" ) ? "派送信息" : "劳动合同" ) + "ID" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "客户ID" );
         nameZHs.add( "客户名称" );
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
      nameSyses.add( "leaveHeaderId" );
      nameSyses.add( "decodeItemId" );
      nameSyses.add( "specialStartDate" );
      nameSyses.add( "specialEndDate" );
      nameSyses.add( "useHours" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "contractId" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
         nameSyses.add( "clientName" );
      }
      nameSyses.add( "decodeStatus" );
      nameSyses.add( "decodeModifyBy" );
      nameSyses.add( "decodeModifyDate" );
      return KANUtil.stringListToArray( nameSyses );
   }

   public ActionForward list_object_nativeApp( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final JSONObject jsonObject = new JSONObject();
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final PagedListHolder pagedListHolderLeave = new PagedListHolder();
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         leaveHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            leaveHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         leaveHeaderVO.setSortColumn( "unread desc,modifyDate" );
         leaveHeaderVO.setSortOrder( "desc" );
         pagedListHolderLeave.setObject( leaveHeaderVO );
         leaveHeaderService.getLeaveHeaderVOsByCondition( pagedListHolderLeave, false );
         final JSONArray leaveJsonArray = new JSONArray();
         for ( Object object : pagedListHolderLeave.getSource() )
         {
            ( ( LeaveHeaderVO ) object ).reset( null, request );
            final JSONObject tempObject = JSONObject.fromObject( ( LeaveHeaderVO ) object );
            leaveJsonArray.add( tempObject );
         }
         jsonObject.put( "leaveJsonArray", leaveJsonArray );

         // 加班的
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final PagedListHolder pagedListHolderOT = new PagedListHolder();
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setEmployeeId( getEmployeeId( request, null ) );
         otHeaderVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            otHeaderVO.setCorpId( getCorpId( request, null ) );
         }
         otHeaderVO.setSortColumn( "unread desc,createDate" );
         otHeaderVO.setSortOrder( "desc" );
         pagedListHolderOT.setObject( otHeaderVO );
         otHeaderService.getOTHeaderVOsByCondition( pagedListHolderOT, false );
         final JSONArray otJsonArray = new JSONArray();
         for ( Object object : pagedListHolderOT.getSource() )
         {
            ( ( OTHeaderVO ) object ).reset( null, request );
            final JSONObject tempObject = JSONObject.fromObject( ( OTHeaderVO ) object );
            otJsonArray.add( tempObject );
         }
         jsonObject.put( "otJsonArray", otJsonArray );

         out.print( jsonObject.toString() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return null;
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
         String result = "请假失败";
         // 初始化Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取当前Form
         final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();

         leaveHeaderVO.setUnread( request.getParameter( "unread" ) );
         leaveHeaderVO.setContractId( request.getParameter( "contractId" ) );
         leaveHeaderVO.setItemId( request.getParameter( "itemId" ) );
         leaveHeaderVO.setEstimateStartDate( request.getParameter( "estimateStartDate" ) );
         leaveHeaderVO.setEstimateEndDate( request.getParameter( "estimateEndDate" ) );
         leaveHeaderVO.setEstimateLegalHours( request.getParameter( "estimateLegalHours" ) );
         leaveHeaderVO.setEstimateBenefitHours( request.getParameter( "estimateBenefitHours" ) );
         leaveHeaderVO.setDescription( KANUtil.decodeURLFromAjax( request.getParameter( "description" ) ) );
         leaveHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         leaveHeaderVO.setCorpId( getCorpId( request, response ) );

         if ( KANUtil.filterEmpty( leaveHeaderVO.getClientId() ) == null )
         {
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
            if ( employeeContractVO != null )
            {
               leaveHeaderVO.setClientId( employeeContractVO.getClientId() );
            }
         }

         leaveHeaderVO.setActualLegalHours( "0" );
         leaveHeaderVO.setActualBenefitHours( "0" );
         leaveHeaderVO.setRetrieveStatus( "1" );
         leaveHeaderVO.setCreateBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );

         // 保存自定义Column
         leaveHeaderVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

         if ( ( "41".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null
               && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
               || ( "48".equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null && Double.valueOf( leaveHeaderVO.getEstimateLegalHours() ) == 0 )
               || ( "49".equals( KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null ) && Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) == 0 ) )
         {
            result = "数据未产生，无效的请假小时数！";
         }
         else
         {
            // 添加
            leaveHeaderService.insertLeaveHeader( leaveHeaderVO );
            leaveHeaderService.submitLeaveHeader( leaveHeaderVO );
            result = "success";
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

   public ActionForward to_objectModify_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         // 主键获取需解码
         String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( leaveId == null || leaveId.trim().isEmpty() )
         {
            leaveId = ( ( LeaveHeaderVO ) form ).getLeaveHeaderId();
         }

         // 获得LeaveVO对象
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );

         // 销假情况
         if ( KANUtil.filterEmpty( leaveHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( leaveHeaderVO.getActualEndDate() ) != null )
         {
            leaveHeaderVO.setEstimateStartDate( leaveHeaderVO.getActualStartDate() );
            leaveHeaderVO.setEstimateEndDate( leaveHeaderVO.getActualEndDate() );
            leaveHeaderVO.setEstimateLegalHours( leaveHeaderVO.getActualLegalHours() );
            leaveHeaderVO.setEstimateBenefitHours( leaveHeaderVO.getActualBenefitHours() );
         }

         // 刷新对象，初始化对象列表及国际化
         leaveHeaderVO.reset( null, request );
         // 区分修改添加
         leaveHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "leaveHeaderForm", leaveHeaderVO );

         // 初始化leftHours
         double leftHours = 0.0;

         // 初始化hours
         double hours = Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() ) + Double.valueOf( leaveHeaderVO.getEstimateLegalHours() );

         final String leaveEndDate = getLeaveEndDate( leaveHeaderVO.getItemId(), getAccountId( request, null ), leaveHeaderVO.getContractId(), leaveHeaderVO.getEstimateStartDate(), leftHours, hours, null );

         request.setAttribute( "hasDeleteRight", hasDeleteRight( leaveHeaderVO ) );
         // 写入request
         request.setAttribute( "leaveEndDate", leaveEndDate );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转修改页面
      return mapping.findForward( "manageLeaveMobile" );
   }

   public ActionForward deleteLeaveOT_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String type = request.getParameter( "type" );
         final String id = request.getParameter( "id" );
         if ( "leave".equalsIgnoreCase( type ) )
         {
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( id );

            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyDate( new Date() );

            if ( leaveHeaderService.deleteLeaveHeader_cleanTS( leaveHeaderVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_DELETE );
               insertlog( request, leaveHeaderVO, Operate.DELETE, id, "delete leave for WeChar" );
            }
         }
         else if ( "ot".equalsIgnoreCase( type ) )
         {
            final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
            final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( id );
            otHeaderVO.setDeleted( "2" );
            otHeaderService.updateOTHeader( otHeaderVO );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /***
    * 销假提交sick_leave
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward sick_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Token验证
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
            // 主键获取需解码
            final String leaveHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获得LeaveHeaderVO对象
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
            // 传入销假时间
            leaveHeaderVO.setActualStartDate( ( ( LeaveHeaderVO ) form ).getEstimateStartDate() );
            leaveHeaderVO.setActualEndDate( ( ( LeaveHeaderVO ) form ).getEstimateEndDate() );
            leaveHeaderVO.setActualLegalHours( ( ( LeaveHeaderVO ) form ).getEstimateLegalHours() );
            leaveHeaderVO.setActualBenefitHours( ( ( LeaveHeaderVO ) form ).getEstimateBenefitHours() );

            if ( leaveHeaderService.sick_leave( leaveHeaderVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, leaveHeaderVO, Operate.SUBMIT, leaveHeaderVO.getLeaveHeaderId(), "销假" );
            }
         }

         ( ( LeaveHeaderVO ) form ).reset();
         ( ( LeaveHeaderVO ) form ).setEmployeeNameZH( "" );
         ( ( LeaveHeaderVO ) form ).setEmployeeNameEN( "" );
         ( ( LeaveHeaderVO ) form ).setCertificateNumber( "" );
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

}
