package com.kan.hro.web.actions.biz.attendance;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceService;
import com.kan.hro.service.inf.biz.attendance.TimesheetBatchService;
import com.kan.hro.service.inf.biz.attendance.TimesheetDetailService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

public class TimesheetHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_TIMESHEET_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );

         // 获取TimesheetBatchVO modify by jacksun 统计信息无法做数据权限
         /* final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );*/

         // 获得Action Form
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;

         //处理数据权限
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), TimesheetBatchAction.accessActionInHouse, timesheetHeaderVO );
            setDataAuth( request, response, timesheetHeaderVO );
         }

         final TimesheetBatchVO condition = new TimesheetBatchVO();
         condition.setBatchId( batchId );
         condition.setAccountId( timesheetHeaderVO.getAccountId() );
         condition.setCorpId( timesheetHeaderVO.getCorpId() );
         //         condition.setHasIn( timesheetHeaderVO.getHasIn() );
         //         condition.setNotIn( timesheetHeaderVO.getNotIn() );
         condition.setRulePublic( timesheetHeaderVO.getRulePublic() );
         condition.setRulePrivateIds( timesheetHeaderVO.getRulePrivateIds() );
         condition.setRulePositionIds( timesheetHeaderVO.getRulePositionIds() );
         condition.setRuleBranchIds( timesheetHeaderVO.getRuleBranchIds() );
         condition.setRuleBusinessTypeIds( timesheetHeaderVO.getRuleBusinessTypeIds() );
         condition.setRuleEntityIds( timesheetHeaderVO.getRuleEntityIds() );
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            condition.setClientId( BaseAction.getClientId( request, response ) );
         }
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByTimesheetBatchVO( condition );

         if ( timesheetBatchVO != null )
         {
            // 写入request
            request.setAttribute( "timesheetBatchForm", timesheetBatchVO );
         }

         // 如果没有指定排序则默认按 monthly排序
         if ( timesheetHeaderVO.getSortColumn() == null || timesheetHeaderVO.getSortColumn().isEmpty() )
         {
            timesheetHeaderVO.setSortOrder( "desc" );
            timesheetHeaderVO.setSortColumn( "a.headerId" );
         }

         //         // 如果是inHouse
         //         if ( getRole( request, null ).equals( "2" ) )
         //         {
         //            // 如果非HR部门
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // 只能查看自己的考勤表
         //               timesheetHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         //            }
         //         }

         // 处理subAction
         dealSubAction( timesheetHeaderVO, mapping, form, request, response );

         timesheetHeaderVO.setBatchId( batchId );

         // 调用删除方法
         if ( timesheetHeaderVO.getSubAction() != null && timesheetHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( timesheetHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( timesheetHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );

         //全部选中
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            timesheetHeaderService.getTimesheetHeaderVOsByCondition( pagedListHolder, false );
            String selectids = "";

            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object pageObject : pagedListHolder.getSource() )
               {
                  selectids = selectids + ( ( TimesheetHeaderVO ) pageObject ).getEncodedId() + ",";
               }
            }
            timesheetHeaderVO.setSelectedIds( selectids );

         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetHeaderService.getTimesheetHeaderVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "timesheetHeaderHolder", pagedListHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTimesheetHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listTimesheetHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // 处理Return
         return dealReturn( null, "generateTimesheetHeader", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
            final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

            // 获取Form
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
            timesheetHeaderVO.setOrderId( KANUtil.filterEmpty( timesheetHeaderVO.getOrderId(), "0" ) );
            timesheetHeaderVO.setCreateBy( getUserId( request, null ) );
            timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

            // 生成Timesheet
            final int rows = timesheetHeaderService.generateTimesheet( timesheetHeaderVO );

            if ( rows > 0 )
            {
               // 返回添加成功标记
               success( request, null, "成功创建 " + rows + " 个考勤表！" );
            }
            else
            {
               // 返回警告标记
               if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
               {
                  warning( request, null, "考勤表未创建。没有符合条件的数据！派送协议状态必须是批准、盖章、归档或当月结束，必须设置日历和排班；订单状态必须是批准或生效；" );
               }
               else if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  warning( request, null, "考勤表未创建。没有符合条件的数据！劳动合同状态必须是批准、盖章、归档或当月结束，必须设置日历和排班；结算规则状态必须是批准或生效；" );
               }
            }
         }
         else
         {
            // 返回失败标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // 清空Form条件
         ( ( TimesheetHeaderVO ) form ).reset();
         ( ( TimesheetHeaderVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 处理Return
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 保存一个token
         this.saveToken( request );

         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 主键获取需解码
         String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( headerId == null || headerId.trim().isEmpty() )
         {
            headerId = ( ( TimesheetHeaderVO ) form ).getHeaderId();
         }

         // 获得TimesheetHeaderVO
         final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );
         timesheetHeaderVO.setSubAction( VIEW_OBJECT );
         timesheetHeaderVO.reset( null, request );

         // 获取服务协议列表
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( timesheetHeaderVO.getContractId() );

         // 雇员服务协议>薪酬方案 - 基本工资是否按小时算
         if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
         {
            for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
               if ( employeeContractSalaryVO.getItemId().equals( "1" ) && employeeContractSalaryVO.getSalaryType().equals( "2" ) )
               {
                  request.setAttribute( "byHour", true );
                  break;
               }
            }
         }
         // 写入作用域
         request.setAttribute( "timesheetHeaderForm", timesheetHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转页面
      return mapping.findForward( "manageTimesheetHeader" );
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
            final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

            // 获取ActionForm
            final TimesheetHeaderVO timesheetHeaderForm = ( TimesheetHeaderVO ) form;

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获取TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );

            // 设值
            timesheetHeaderVO.setTotalWorkHours( timesheetHeaderForm.getTotalWorkHours() );
            timesheetHeaderVO.setTotalWorkDays( timesheetHeaderForm.getTotalWorkDays() );
            timesheetHeaderVO.setTotalFullHours( timesheetHeaderForm.getTotalFullHours() );
            timesheetHeaderVO.setTotalFullDays( timesheetHeaderForm.getTotalFullDays() );
            timesheetHeaderVO.setWorkHoursArray( timesheetHeaderForm.getWorkHoursArray() );
            timesheetHeaderVO.setBaseArray( timesheetHeaderForm.getBaseArray() );
            timesheetHeaderVO.setDayTypeArray( timesheetHeaderForm.getDayTypeArray() );
            timesheetHeaderVO.setDescription( timesheetHeaderForm.getDescription() );
            // 获取登录用户
            timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
            timesheetHeaderVO.setModifyDate( new Date() );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 如果是客户提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               timesheetHeaderVO.reset( mapping, request );

               if ( timesheetHeaderService.submit_header( timesheetHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }
            else
            {
               timesheetHeaderService.updateTimesheetHeader( timesheetHeaderVO );
               success( request, MESSAGE_TYPE_UPDATE );
            }

         }
         // 清空FORM
         ( ( TimesheetHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   // 考勤提交  - 按header
   public ActionForward submit_header( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

         // 获得勾选ID（headerId）
         final String headerIds = timesheetHeaderVO.getSelectedIds();

         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 存在勾选ID（headerId）
         if ( KANUtil.filterEmpty( headerIds ) != null )
         {
            for ( String headerId : headerIds.split( "," ) )
            {
               // 获取TimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( headerId ) );
               tempTimesheetHeaderVO.reset( null, request );
               tempTimesheetHeaderVO.setModifyBy( getUserId( request, response ) );
               tempTimesheetHeaderVO.setModifyDate( new Date() );

               // 逐个提交TimesheetHeaderVO
               if ( timesheetHeaderService.submit_header( tempTimesheetHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }

            insertlog( request, timesheetHeaderVO, Operate.SUBMIT, null, "batchId:" + timesheetHeaderVO.getBatchId() + " headerIds:" + KANUtil.decodeSelectedIds( headerIds ) );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 考勤提交  - 按header
   public ActionForward submit_header1( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

         // 获得勾选ID（headerId）
         final String headerIds = timesheetHeaderVO.getSelectedIds();
         String deocodeHeaderIds = "";
         String employeeNameList = "";

         // 初始化Service接口
         final TimesheetBatchService timesheetBatchService = ( TimesheetBatchService ) getService( "timesheetBatchService" );
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 存在勾选ID（headerId）
         if ( KANUtil.filterEmpty( headerIds ) != null )
         {
            for ( String headerId : headerIds.split( "," ) )
            {
               // 获取TimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( headerId ) );
               if ( tempTimesheetHeaderVO != null )
               {
                  if ( KANUtil.filterEmpty( employeeNameList ) == null )
                  {
                     employeeNameList = tempTimesheetHeaderVO.getEmployeeNameZH();
                  }
                  else
                  {
                     employeeNameList = employeeNameList + "、" + tempTimesheetHeaderVO.getEmployeeNameZH();
                  }
               }

               if ( KANUtil.filterEmpty( deocodeHeaderIds ) == null )
               {
                  deocodeHeaderIds = KANUtil.decodeStringFromAjax( headerId );
               }
               else
               {
                  deocodeHeaderIds = deocodeHeaderIds + "," + KANUtil.decodeStringFromAjax( headerId );
               }
            }
            // 根据勾选的HeaderId获取TimesheetBatchVO
            final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByHeaderIds( deocodeHeaderIds );
            timesheetBatchVO.setDescription( employeeNameList );
            timesheetBatchVO.reset( null, request );
            timesheetBatchVO.setModifyBy( getUserId( request, null ) );
            timesheetBatchVO.setHeaderIds( headerIds );

            if ( timesheetBatchService.submit_batch( timesheetBatchVO ) == -1 )
               success( request, MESSAGE_TYPE_SUBMIT );
            else
               success( request, MESSAGE_TYPE_UPDATE );
         }

         return list_object( mapping, form, request, response );
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
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 获得主键需解码
         final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得ClientVO对象
         final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );

         // 设置数据
         timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
         timesheetHeaderVO.setModifyDate( new Date() );
         timesheetHeaderVO.reset( null, request );

         if ( timesheetHeaderService.submitTimesheetHeader( timesheetHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, timesheetHeaderVO, Operate.SUBMIT, headerId, null );
         }
         else
         {
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, timesheetHeaderVO, Operate.MODIFY, headerId, null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Add by siuvan.xia @ 2014-07-04
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 获得ActionForm
         TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( timesheetHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, timesheetHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( timesheetHeaderVO.getSelectedIds() ) );
            // 分割
            for ( String selectedId : timesheetHeaderVO.getSelectedIds().split( "," ) )
            {
               timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

               // 删除只能“新建”和“退回”状态的考情表
               if ( timesheetHeaderVO != null && KANUtil.filterEmpty( timesheetHeaderVO.getStatus() ) != null
                     && ( timesheetHeaderVO.getStatus().equals( "1" ) || timesheetHeaderVO.getStatus().equals( "4" ) ) )
               {
                  timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
                  timesheetHeaderVO.setModifyDate( new Date() );
                  timesheetHeaderService.deleteTimesheetHeader( timesheetHeaderVO );
               }
            }
         }

         // 清除Selected IDs和子Action
         ( ( TimesheetHeaderVO ) form ).setSelectedIds( "" );
         ( ( TimesheetHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final TimesheetDetailService timesheetDetailService = ( TimesheetDetailService ) getService( "timesheetDetailService" );
         final TimesheetAllowanceService timesheetAllowanceService = ( TimesheetAllowanceService ) getService( "timesheetAllowanceService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ID获取需解码
         final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( headerId ) != null )
         {
            // 获取TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );

            request.setAttribute( "timesheetHeaderForm", timesheetHeaderVO );

            // 获取EmployeeContractSalaryVO列表
            final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( timesheetHeaderVO.getContractId() );

            // 雇员服务协议>薪酬方案 - 基本工资是否按小时算
            if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
            {
               for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
               {
                  if ( ( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject ).getItemId().equals( "1" )
                        && ( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject ).getSalaryType().equals( "2" ) )
                  {
                     request.setAttribute( "byHour", true );
                     break;
                  }
               }
            }

            // 初始化PagedListHolder
            final PagedListHolder timesheetDetailHolder = new PagedListHolder();

            // 初始化考勤详情查询条件
            final TimesheetDetailVO timesheetDetailVO = new TimesheetDetailVO();
            timesheetDetailVO.setHeaderId( headerId );
            timesheetDetailVO.setStatus( BaseVO.TRUE );
            timesheetDetailHolder.setObject( timesheetDetailVO );
            timesheetDetailService.getTimesheetDetailVOsByCondition( timesheetDetailHolder, false );

            // 刷新Holder，国际化传值
            refreshHolder( timesheetDetailHolder, request );
            request.setAttribute( "timesheetDetailHolder", timesheetDetailHolder );

            // 初始化PagedListHolder
            final PagedListHolder timesheetAllowanceHolder = new PagedListHolder();
            // 初始化津贴查询条件
            final TimesheetAllowanceVO timesheetAllowanceVO = new TimesheetAllowanceVO();
            timesheetAllowanceVO.setHeaderId( headerId );
            timesheetAllowanceVO.setStatus( BaseVO.TRUE );
            timesheetAllowanceHolder.setObject( timesheetAllowanceVO );
            timesheetAllowanceService.getTimesheetAllowanceVOsByCondition( timesheetAllowanceHolder, false );

            // 刷新Holder，国际化传值
            refreshHolder( timesheetAllowanceHolder, request );
            // 加载考勤详情Info
            request.setAttribute( "timesheetAllowanceHolder", timesheetAllowanceHolder );

         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSpecialInfo" );
   }

   // 请假、加班时，看是否存在新建或退回的考勤表
   // Add by Siuvan Xia at 2014-8-28
   public void existAvailableTimesheet( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );

         // 提交方式：1、detail提交；2、list单个提交；3、list批量提交
         final String submitType = request.getParameter( "submitType" );

         // 提交时默认没有提示
         String flag = "2";

         // 初始化一些参数
         String accountId = getAccountId( request, null );
         String corpId = getCorpId( request, null );

         // 1、detail提交
         // 2、link提交；
         if ( submitType.equals( "1" ) || submitType.equals( "2" ) )
         {
            // 获取参数
            final String employeeId = request.getParameter( "employeeId" );
            final String contractId = request.getParameter( "contractId" );
            final String startDate = request.getParameter( "startDate" );
            flag = getFlag( accountId, corpId, employeeId, contractId, startDate );
         }
         // 3、checkBox批量提交
         else if ( submitType.equals( "3" ) )
         {
            // 获取参数
            final String selectedIds = request.getParameter( "selectedIds" );
            final String objectName = request.getParameter( "objectName" );

            // 如果是请假
            if ( objectName.equals( "leave" ) )
            {
               for ( String seletctedId : selectedIds.split( "," ) )
               {
                  final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( KANUtil.decodeStringFromAjax( seletctedId ) );
                  flag = getFlag( accountId, corpId, leaveHeaderVO.getEmployeeId(), leaveHeaderVO.getContractId(), leaveHeaderVO.getEstimateStartDate() );
                  if ( flag.equals( "1" ) )
                  {
                     break;
                  }
               }
            }
            // 如果是加班
            else if ( objectName.equals( "ot" ) )
            {
               for ( String seletctedId : selectedIds.split( "," ) )
               {
                  final OTHeaderVO otHeaderVO = otHeaderService.getOTHeaderVOByOTHeaderId( KANUtil.decodeStringFromAjax( seletctedId ) );
                  flag = getFlag( getAccountId( request, null ), getCorpId( request, null ), otHeaderVO.getEmployeeId(), otHeaderVO.getContractId(), otHeaderVO.getEstimateStartDate() );

                  if ( flag.equals( "1" ) )
                  {
                     break;
                  }
               }
            }
         }

         // Send to client
         out.print( flag );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 考勤表是否为新建或退回状态
   private String getFlag( final String accountId, final String corpId, final String employeeId, final String contractId, final String startDate ) throws KANException
   {
      // 提交时默认没有提示
      String flag = "2";
      // 计薪结束日期
      String circleEndDay = "31";
      // 考勤月份
      String monthly = "";

      // 初始化Service
      final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // 获取EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      // 获取EmployeeContractVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO == null ? "0"
            : employeeContractVO.getOrderId() );

      if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getCircleEndDay(), "0" ) != null )
      {
         circleEndDay = clientOrderHeaderVO.getCircleEndDay();
      }

      // 考勤月份
      monthly = KANUtil.getMonthlyByCondition( circleEndDay, startDate );

      final TimesheetHeaderVO searchTimesheetHeaderVO = new TimesheetHeaderVO();
      searchTimesheetHeaderVO.setAccountId( accountId );
      searchTimesheetHeaderVO.setCorpId( corpId );
      searchTimesheetHeaderVO.setEmployeeId( employeeId );
      searchTimesheetHeaderVO.setContractId( contractId );
      searchTimesheetHeaderVO.setMonthly( monthly );

      final List< Object > timesheetHeaderVOs = timesheetHeaderService.getTimesheetHeaderVOsByCondition( searchTimesheetHeaderVO );
      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetVOObject : timesheetHeaderVOs )
         {
            // 非新建、退回状态
            if ( !( ( TimesheetHeaderVO ) timesheetVOObject ).getStatus().equals( "1" ) && !( ( TimesheetHeaderVO ) timesheetVOObject ).getStatus().equals( "4" ) )
            {
               flag = "1";
               break;
            }
         }
      }

      return flag;
   }
}
