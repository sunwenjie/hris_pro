package com.kan.hro.web.actions.biz.dashboard;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SettingVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.service.inf.management.SettingService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.renders.util.DashboardRender;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.dashboard.DashboardService;

/**
 * @author Iori Luo
 */
public class DashboardAction extends BaseAction
{

   public ActionForward showDashboard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      Cookie[] cookies = request.getCookies();
      if ( cookies != null )
      {
         for ( Cookie cookie : cookies )
         {
            if ( "KAN_LOGON_INFO_MOBILE".equals( cookie.getName() ) )
            {
               return new SecurityAction().index_mobile( mapping, new UserVO(), request, response );
            }
         }
      }
      
      SettingVO defSettingVO = new SettingVO();
      defSettingVO.reset( mapping, request );
      defSettingVO.setAttendance( "1" );
      defSettingVO.setAttendanceRank( "1" );
      defSettingVO.setPayment( "1" );
      defSettingVO.setPaymentRank( "2" );
      request.setAttribute( "settingVO", defSettingVO );

//      // 初始化Service接口
//      final SettingService settingService = ( SettingService ) getService( "settingService" );
//      // 获得主键对应对象
//      SettingVO settingVO = settingService.getSettingVOByUserId( getUserId( request, response ) );
//      // 如果为空则是第一次进入，初始化个人模板
//      if ( settingVO == null )
//      {
//         settingVO = new SettingVO();
//         settingVO.setAccountId( getAccountId( request, response ) );
//         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
//         {
//            settingVO.setCorpId( getCorpId( request, response ) );
//         }
//         settingVO.setUserId( getUserId( request, response ) );
//         
//         settingVO.setAttendance( "1" );
//         settingVO.setAttendanceRank( "1" );
//         settingVO.setPayment( "1" );
//         settingVO.setPaymentRank( "1" );
////         // 设置各项
////         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
////         {
////            settingVO.resetInHouseInit();
////         }
////         else
////         {
////            settingVO.resetInit();
////         }
//         settingVO.setCreateBy( getUserId( request, response ) );
//
//         settingService.insertSetting( settingVO );
//      }
//      // 刷新对象，初始化对象列表及国际化
//      settingVO.reset( null, request );
//      // 传回ActionForm对象到前端
//      request.setAttribute( "settingVO", settingVO );

      return mapping.findForward( "showDashboard" );
   }

   /**
    * ajax 请求获取基本信息
    */
   public ActionForward get_baseInfo_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         AccountVO accountVO = dashboardService.getAccountVOByAccountId( getAccountId( request, response ) );
         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();
         if ( accountVO != null )
         {
            accountVO.reset( null, request );
            jsonObject = JSONObject.fromObject( accountVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }
         // Send to front
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
    * ajax 请求获取通知
    */
   public ActionForward get_message_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   /**
    * ajax 请求获取数据概览
    */
   public ActionForward get_dataView_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final Map< String, Integer > data = new HashMap< String, Integer >();
         // 供应商（3审批通过）
         VendorVO vendorVO = new VendorVO();
         vendorVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            vendorVO.setCorpId( getCorpId( request, response ) );
         }
         vendorVO.setStatus( "3" );
         final Integer vendorCount = dashboardService.getVendorCountForDashboard( vendorVO );
         data.put( "vendorCount", vendorCount );
         //客户（3审批通过）
         ClientVO clientVO = new ClientVO();
         clientVO.setAccountId( getAccountId( request, response ) );
         clientVO.setStatus( "3" );
         final Integer clientCount = dashboardService.getClientCountForDashboard( clientVO );
         data.put( "clientCount", clientCount );
         //商务合同（3批准、5盖章、6归档）
         ClientContractVO clientContractVO = new ClientContractVO();
         clientContractVO.setAccountId( getAccountId( request, response ) );
         clientContractVO.setStatus( "3" );
         final Integer clientContractCount_3 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "clientContractCount_3", clientContractCount_3 );
         clientContractVO.setStatus( "5" );
         final Integer clientContractCount_5 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "clientContractCount_5", clientContractCount_5 );
         clientContractVO.setStatus( "6" );
         final Integer clientContractCount_6 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "clientContractCount_6", clientContractCount_6 );
         //订单（3批准、5生效）
         ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         clientOrderHeaderVO.setStatus( "3" );
         final Integer clientOrdersCount_3 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "clientOrdersCount_3", clientOrdersCount_3 );
         clientOrderHeaderVO.setStatus( "5" );
         final Integer clientOrdersCount_5 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "clientOrdersCount_5", clientOrdersCount_5 );
         // 雇员（状态为在职）1
         EmployeeVO employeeVO = new EmployeeVO();
         employeeVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            employeeVO.setCorpId( getCorpId( request, response ) );
         }

         employeeVO.setStatus( "1" );

         //make data interceptor fail
         employeeVO.setHasIn( null );
         employeeVO.setNotIn( null );
         final Integer employeeContractCount_1 = dashboardService.getEmployeeCountForDashboard( employeeVO );
         data.put( "employeeContractCount_1", employeeContractCount_1 );
         // 生成 XML 字符串
         out.println( DashboardRender.generateDataView( request, data, KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ) );
         out.flush();
         out.close();
         vendorVO = null;
         clientVO = null;
         clientContractVO = null;
         clientOrderHeaderVO = null;
         employeeVO = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ajax 请求获取商务合同
    */
   public ActionForward get_clientContract_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 1 新建，2待审核，3批准，4退回，5已盖章，6归档
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final ClientContractVO clientContractVO = new ClientContractVO();
         final Map< String, Integer > data = new HashMap< String, Integer >();
         clientContractVO.setAccountId( getAccountId( request, response ) );
         // 1 新建
         clientContractVO.setStatus( "1" );
         final Integer status_1 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_1", status_1 );
         // 2待审核
         clientContractVO.setStatus( "2" );
         final Integer status_2 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_2", status_2 );
         // 3批准
         clientContractVO.setStatus( "3" );
         final Integer status_3 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_3", status_3 );
         // 4退回
         clientContractVO.setStatus( "4" );
         final Integer status_4 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_4", status_4 );
         // 5已盖章
         clientContractVO.setStatus( "5" );
         final Integer status_5 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_5", status_5 );
         // 6归档
         clientContractVO.setStatus( "6" );
         final Integer status_6 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_6", status_6 );
         // 生成 XML 字符串
         out.println( DashboardRender.generateClientContract( data ) );
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
    * ajax 请求获取订单
    */
   public ActionForward get_orders_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 1 新建，2待审核，3批准，4退回，5生效，6结束，7终止，8取消
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         final Map< String, Integer > data = new HashMap< String, Integer >();
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         // 1 新建
         clientOrderHeaderVO.setStatus( "1" );
         final Integer status_1 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_1", status_1 );
         // 2待审核
         clientOrderHeaderVO.setStatus( "2" );
         final Integer status_2 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_2", status_2 );
         // 3批准
         clientOrderHeaderVO.setStatus( "3" );
         final Integer status_3 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_3", status_3 );
         // 4退回
         clientOrderHeaderVO.setStatus( "4" );
         final Integer status_4 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_4", status_4 );
         // 5生效
         clientOrderHeaderVO.setStatus( "5" );
         final Integer status_5 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_5", status_5 );
         // 6结束
         clientOrderHeaderVO.setStatus( "6" );
         final Integer status_6 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_6", status_6 );
         // 7终止
         clientOrderHeaderVO.setStatus( "7" );
         final Integer status_7 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_7", status_7 );
         // 8取消
         clientOrderHeaderVO.setStatus( "8" );
         final Integer status_8 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_8", status_8 );
         // 生成 XML 字符串
         out.println( DashboardRender.generateClientOrders( data ) );
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
    * ajax 请求获取派送信息
    */
   public ActionForward get_contractService_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 1 新建，2待审核，3批准，4退回，5已盖章，6归档
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         final Map< String, Integer > data = new HashMap< String, Integer >();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            employeeContractVO.setClientId( getClientId( request, response ) );
            employeeContractVO.setCorpId( getCorpId( request, response ) );
         }

         //设置服务协议
         employeeContractVO.setFlag( "2" );
         // 1 新建
         employeeContractVO.setStatus( "1" );
         final Integer status_1 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_1", status_1 );
         // 2待审核
         employeeContractVO.setStatus( "2" );
         final Integer status_2 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_2", status_2 );
         // 3批准
         employeeContractVO.setStatus( "3" );
         final Integer status_3 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_3", status_3 );
         // 4退回
         employeeContractVO.setStatus( "4" );
         final Integer status_4 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_4", status_4 );
         // 5已盖章
         employeeContractVO.setStatus( "5" );
         final Integer status_5 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_5", status_5 );
         // 6归档
         employeeContractVO.setStatus( "6" );
         final Integer status_6 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_6", status_6 );
         // 7结束
         employeeContractVO.setStatus( "7" );
         final Integer status_7 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_7", status_7 );
         // 生成 XML 字符串
         out.println( DashboardRender.generateContractService( request, data, KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ) );
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
    * ajax 请求获取考勤
    */
   public ActionForward get_attendance_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final Map< String, List< Integer > > data = new HashMap< String, List< Integer > >();
         final List< Integer > leave_durations = new ArrayList< Integer >();
         final List< Integer > ot_durations = new ArrayList< Integer >();
         // 请假       3批准，5已结算
         LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            leaveHeaderVO.setClientId( getClientId( request, response ) );
            leaveHeaderVO.setCorpId( getCorpId( request, response ) );
            leaveHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         }
         leaveHeaderVO.setStatus( "3,5" );
         // 近12个月日期
         List< String > recent12Months = KANUtil.getRecent12MonthsDate( Calendar.getInstance(), new ArrayList< String >(), 12 );
         for ( int i = 0; i < recent12Months.size(); i = i + 2 )
         {
            leaveHeaderVO.setEstimateStartDate( recent12Months.get( i ) );
            leaveHeaderVO.setEstimateEndDate( recent12Months.get( i + 1 ) );
            leave_durations.add( dashboardService.getAttendanceLeaveDurationForDashboard( leaveHeaderVO ) );
         }
         // 添加近12个月的请假
         data.put( "attendance_leave_durations", leave_durations );
//         // 加班    3批准，5已结算
//         OTHeaderVO oTHeaderVO = new OTHeaderVO();
//         oTHeaderVO.setAccountId( getAccountId( request, response ) );
//         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
//         {
//            oTHeaderVO.setClientId( getClientId( request, response ) );
//            oTHeaderVO.setCorpId( getCorpId( request, response ) );
//            // 如果不是HR 职能 职能看到自己的
//            if ( !isHRFunction( request, response ) )
//            {
//               oTHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
//            }
//         }
//
//         oTHeaderVO.setStatus( "3,5" );
//         for ( int i = 0; i < recent12Months.size(); i = i + 2 )
//         {
//            oTHeaderVO.setEstimateStartDate( recent12Months.get( i ) );
//            oTHeaderVO.setEstimateEndDate( recent12Months.get( i + 1 ) );
//            ot_durations.add( dashboardService.getAttendanceOTDurationForDashboard( oTHeaderVO ) );
//         }
//         data.put( "attendance_ot_durations", ot_durations );
         // 生成 XML 字符串
         out.println( DashboardRender.generateAttendance( request, data, recent12Months ) );
         out.flush();
         out.close();
         leaveHeaderVO = null;
         //oTHeaderVO = null;
         recent12Months = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ajax 请求获取社保
    */
   public ActionForward get_sb_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final List< Double > status_5_personal = new ArrayList< Double >();
         final List< Double > status_5_company = new ArrayList< Double >();
         final List< Double > status_adj_5_personal = new ArrayList< Double >();
         final List< Double > status_adj_5_company = new ArrayList< Double >();
         // 5已结算  (5审批过的调整)
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            sbAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            // 如果不是HR 职能 职能看到自己的
            if ( !isHRFunction( request, response ) )
            {
               sbHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
               sbAdjustmentHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
            }
         }

         sbAdjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
         List< String > recent12YearMonths = KANUtil.getRecent12YearMonths( Calendar.getInstance(), new ArrayList< String >(), 12 );
         Map< String, Double > tempMap = null;
         for ( String yearMonth : recent12YearMonths )
         {
            sbHeaderVO.setMonthly( yearMonth );
            sbAdjustmentHeaderVO.setMonthly( yearMonth );

            // 5已结算
            sbHeaderVO.setStatus( "5" );
            tempMap = dashboardService.getSBAmountForDashboard( sbHeaderVO );
            status_5_company.add( tempMap.get( "sumCompany" ) );
            status_5_personal.add( tempMap.get( "sumPersonal" ) );
            // 5审批过的调整
            sbAdjustmentHeaderVO.setStatus( "5" );
            tempMap = dashboardService.getSBAdjAmountForDashboard( sbAdjustmentHeaderVO );
            status_adj_5_company.add( tempMap.get( "sumCompany" ) );
            status_adj_5_personal.add( tempMap.get( "sumPersonal" ) );
         }

         // 生成 XML 字符串
         out.println( DashboardRender.generateSB( request, status_5_personal, status_5_company, status_adj_5_personal, status_adj_5_company, recent12YearMonths ) );
         out.flush();
         out.close();
         sbHeaderVO = null;
         sbAdjustmentHeaderVO = null;
         recent12YearMonths = null;
         tempMap = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ajax 请求获取商报
    */
   public ActionForward get_cb_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final List< Double > status_5_cost = new ArrayList< Double >();
         final List< Double > status_5_price = new ArrayList< Double >();
         // 5已结算  (5审批过的调整)
         CBHeaderVO cbHeaderVO = new CBHeaderVO();
         cbHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            cbHeaderVO.setClientId( getClientId( request, response ) );
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
            // 如果不是HR 职能 职能看到自己的
            if ( !isHRFunction( request, response ) )
            {
               cbHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
            }
         }

         List< String > recent12YearMonths = KANUtil.getRecent12YearMonths( Calendar.getInstance(), new ArrayList< String >(), 12 );
         Map< String, Double > tempMap = null;
         for ( String yearMonth : recent12YearMonths )
         {
            cbHeaderVO.setMonthly( yearMonth );
            // 5已结算
            cbHeaderVO.setStatus( "5" );
            tempMap = dashboardService.getCBAmountForDashboard( cbHeaderVO );
            status_5_cost.add( tempMap.get( "amountSalesCost" ) );
            status_5_price.add( tempMap.get( "amountSalesPrice" ) );
         }

         // 生成 XML 字符串
         out.println( DashboardRender.generateCB( request, status_5_cost, status_5_price, recent12YearMonths, KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ) );
         out.flush();
         out.close();
         cbHeaderVO = null;
         recent12YearMonths = null;
         tempMap = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ajax 请求获取结算
    */
   public ActionForward get_settlement_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );

         // 查询出的结果无论状态都为台账
         ServiceContractVO serviceContractVO = new ServiceContractVO();
         AdjustmentHeaderVO adjustmentHeaderVO = new AdjustmentHeaderVO();
         serviceContractVO.setAccountId( getAccountId( request, response ) );
         adjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
         List< String > recent12YearMonths = KANUtil.getRecent12YearMonths( Calendar.getInstance(), new ArrayList< String >(), 12 );
         Map< String, Double > tempMap = null;
         Map< String, Double > tempAdjMap = null;
         final List< Double > billAmountCompanyList = new ArrayList< Double >();
         final List< Double > costAmountCompanyList = new ArrayList< Double >();
         final List< Double > billAmountCompanyAdjList = new ArrayList< Double >();
         final List< Double > costAmountCompanyAdjList = new ArrayList< Double >();
         for ( String yearMonth : recent12YearMonths )
         {
            serviceContractVO.setMonthly( yearMonth );
            adjustmentHeaderVO.setStartDate( KANUtil.firstDayOfMonth( yearMonth ) );
            adjustmentHeaderVO.setEndDate( KANUtil.lastDayOfMonth( yearMonth ) );
            tempMap = dashboardService.getSettlementAmountForDashboard( serviceContractVO );
            tempAdjMap = dashboardService.getSettlementAdjAmountForDashboard( adjustmentHeaderVO );
            billAmountCompanyList.add( tempMap.get( "billAmountCompany" ) );
            costAmountCompanyList.add( tempMap.get( "costAmountCompany" ) );
            billAmountCompanyAdjList.add( tempAdjMap.get( "billAmountCompany" ) );
            costAmountCompanyAdjList.add( tempAdjMap.get( "costAmountCompany" ) );
         }

         // 生成 XML 字符串
         out.println( DashboardRender.generateSettlement( request, billAmountCompanyList, costAmountCompanyList, billAmountCompanyAdjList, costAmountCompanyAdjList, recent12YearMonths ) );
         out.flush();
         out.close();
         serviceContractVO = null;
         adjustmentHeaderVO = null;
         recent12YearMonths = null;
         tempMap = null;
         tempAdjMap = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ajax 请求获取薪酬/工资
    */
   public ActionForward get_payment_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         //3 已发放  ,5 审批过的调整
         PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
         PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
         paymentHeaderVO.setAccountId( getAccountId( request, response ) );
         paymentAdjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            paymentHeaderVO.setCorpId( getCorpId( request, response ) );
            paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            paymentHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
            paymentAdjustmentHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         }
         List< String > recent12YearMonths = KANUtil.getRecent12YearMonths( Calendar.getInstance(), new ArrayList< String >(), 12 );
         Map< String, Double > tempMap = null;
         Map< String, Double > tempAdjMap = null;

         final List< Double > addtionalBillAmountPersonal_status_3 = new ArrayList< Double >();
         final List< Double > taxAmountPersonal_status_3 = new ArrayList< Double >();
         final List< Double > addtionalBillAmountPersonal_adj_status_5 = new ArrayList< Double >();
         final List< Double > taxAmountPersonal_adj_status_5 = new ArrayList< Double >();

         for ( String yearMonth : recent12YearMonths )
         {
            paymentHeaderVO.setMonthly( yearMonth );
            paymentAdjustmentHeaderVO.setMonthly( yearMonth );
            paymentHeaderVO.setStatus( "2" );
            paymentHeaderVO.setStatus( "3" );
            tempMap = dashboardService.getPaymentAmountForDashboard( paymentHeaderVO );
            addtionalBillAmountPersonal_status_3.add( tempMap.get( "addtionalBillAmountPersonal" ) );
            taxAmountPersonal_status_3.add( tempMap.get( "taxAmountPersonal" ) );
            paymentAdjustmentHeaderVO.setStatus( "5" );
            tempAdjMap = dashboardService.getPaymentAdjAmountForDashboard( paymentAdjustmentHeaderVO );
            addtionalBillAmountPersonal_adj_status_5.add( tempAdjMap.get( "addtionalBillAmountPersonal" ) );
            taxAmountPersonal_adj_status_5.add( tempAdjMap.get( "taxAmountPersonal" ) );
         }

         // 生成 XML 字符串
         out.println( DashboardRender.generatePayment( request, addtionalBillAmountPersonal_status_3, taxAmountPersonal_status_3, addtionalBillAmountPersonal_adj_status_5, taxAmountPersonal_adj_status_5, recent12YearMonths ) );
         out.flush();
         out.close();
         paymentHeaderVO = null;
         paymentAdjustmentHeaderVO = null;
         recent12YearMonths = null;
         tempMap = null;
         tempAdjMap = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ajax 请求员工变更信息
    */
   public ActionForward get_employeeChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         List< String > recent12YearMonths = KANUtil.getRecent12YearMonths( Calendar.getInstance(), new ArrayList< String >(), 12 );
         // 月末在职,每月新增，每月离职
         List< Integer > onJob = new ArrayList< Integer >();
         List< Integer > newEmployees = new ArrayList< Integer >();
         List< Integer > dismission = new ArrayList< Integer >();
         // onjon 条件
         final EmployeeContractVO onJobContractVO = new EmployeeContractVO();
         // new 条件
         final EmployeeContractVO tempEmployeeContractVO = new EmployeeContractVO();
         // dismission 条件
         final EmployeeContractVO tempEmployeeContractVO2 = new EmployeeContractVO();
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            onJobContractVO.setClientId( getClientId( request, response ) );
            tempEmployeeContractVO.setClientId( getClientId( request, response ) );
            tempEmployeeContractVO2.setClientId( getClientId( request, response ) );
            onJobContractVO.setCorpId( getCorpId( request, response ) );
            tempEmployeeContractVO.setCorpId( getCorpId( request, response ) );
            tempEmployeeContractVO2.setCorpId( getCorpId( request, response ) );
         }
         onJobContractVO.setAccountId( getAccountId( request, response ) );
         tempEmployeeContractVO.setAccountId( getAccountId( request, response ) );
         tempEmployeeContractVO2.setAccountId( getAccountId( request, response ) );

         for ( String yearMonth : recent12YearMonths )
         {
            // 月末在职  合同未结束
            // 本月在职  合同未结束
            final String year = yearMonth.split( "/" )[ 0 ];
            final String month = yearMonth.split( "/" )[ 1 ];
            onJobContractVO.setStartDateTo( KANUtil.getLastDayOfMonth( Integer.parseInt( year ), Integer.parseInt( month ) - 1 ).substring( 0, 10 ) );
            onJobContractVO.setEndDateFrom( KANUtil.getFirstDayOfMonth( Integer.parseInt( year ), Integer.parseInt( month ) - 1 ).substring( 0, 10 ) );
            onJobContractVO.setStatus( "3,5,6,7" );
            onJob.add( dashboardService.getEmployeeOnJobCountForDashboard( onJobContractVO ) );

            // 员工新增  劳动合同开始日期
            tempEmployeeContractVO.setMonthly( yearMonth );
            tempEmployeeContractVO.setStatus( "3,5,6" );
            newEmployees.add( dashboardService.getEmployeeNewCountForDashboard( tempEmployeeContractVO ) );

            // 离职  劳动合同离职日期  日期月份带0
            tempEmployeeContractVO2.setMonthly( yearMonth );
            tempEmployeeContractVO2.setStatus( "7" );
            dismission.add( dashboardService.getEmployeeDismissionCountForDashboard( tempEmployeeContractVO2 ) );
         }

         // 生成 XML 字符串
         out.println( DashboardRender.generateEmployeeChange( request, onJob, newEmployees, dismission, recent12YearMonths ) );
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
    * ajax 请求获取收款
    */
   public ActionForward get_income_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

}