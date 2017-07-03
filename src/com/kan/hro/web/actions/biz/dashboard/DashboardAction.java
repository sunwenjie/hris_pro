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

//      // ��ʼ��Service�ӿ�
//      final SettingService settingService = ( SettingService ) getService( "settingService" );
//      // ���������Ӧ����
//      SettingVO settingVO = settingService.getSettingVOByUserId( getUserId( request, response ) );
//      // ���Ϊ�����ǵ�һ�ν��룬��ʼ������ģ��
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
////         // ���ø���
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
//      // ˢ�¶��󣬳�ʼ�������б����ʻ�
//      settingVO.reset( null, request );
//      // ����ActionForm����ǰ��
//      request.setAttribute( "settingVO", settingVO );

      return mapping.findForward( "showDashboard" );
   }

   /**
    * ajax �����ȡ������Ϣ
    */
   public ActionForward get_baseInfo_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         AccountVO accountVO = dashboardService.getAccountVOByAccountId( getAccountId( request, response ) );
         // ��ʼ�� JSONObject
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
    * ajax �����ȡ֪ͨ
    */
   public ActionForward get_message_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   /**
    * ajax �����ȡ���ݸ���
    */
   public ActionForward get_dataView_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final Map< String, Integer > data = new HashMap< String, Integer >();
         // ��Ӧ�̣�3����ͨ����
         VendorVO vendorVO = new VendorVO();
         vendorVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            vendorVO.setCorpId( getCorpId( request, response ) );
         }
         vendorVO.setStatus( "3" );
         final Integer vendorCount = dashboardService.getVendorCountForDashboard( vendorVO );
         data.put( "vendorCount", vendorCount );
         //�ͻ���3����ͨ����
         ClientVO clientVO = new ClientVO();
         clientVO.setAccountId( getAccountId( request, response ) );
         clientVO.setStatus( "3" );
         final Integer clientCount = dashboardService.getClientCountForDashboard( clientVO );
         data.put( "clientCount", clientCount );
         //�����ͬ��3��׼��5���¡�6�鵵��
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
         //������3��׼��5��Ч��
         ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         clientOrderHeaderVO.setStatus( "3" );
         final Integer clientOrdersCount_3 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "clientOrdersCount_3", clientOrdersCount_3 );
         clientOrderHeaderVO.setStatus( "5" );
         final Integer clientOrdersCount_5 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "clientOrdersCount_5", clientOrdersCount_5 );
         // ��Ա��״̬Ϊ��ְ��1
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
         // ���� XML �ַ���
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
    * ajax �����ȡ�����ͬ
    */
   public ActionForward get_clientContract_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // 1 �½���2����ˣ�3��׼��4�˻أ�5�Ѹ��£�6�鵵
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final ClientContractVO clientContractVO = new ClientContractVO();
         final Map< String, Integer > data = new HashMap< String, Integer >();
         clientContractVO.setAccountId( getAccountId( request, response ) );
         // 1 �½�
         clientContractVO.setStatus( "1" );
         final Integer status_1 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_1", status_1 );
         // 2�����
         clientContractVO.setStatus( "2" );
         final Integer status_2 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_2", status_2 );
         // 3��׼
         clientContractVO.setStatus( "3" );
         final Integer status_3 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_3", status_3 );
         // 4�˻�
         clientContractVO.setStatus( "4" );
         final Integer status_4 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_4", status_4 );
         // 5�Ѹ���
         clientContractVO.setStatus( "5" );
         final Integer status_5 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_5", status_5 );
         // 6�鵵
         clientContractVO.setStatus( "6" );
         final Integer status_6 = dashboardService.getClientContractCountForDashboard( clientContractVO );
         data.put( "status_6", status_6 );
         // ���� XML �ַ���
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
    * ajax �����ȡ����
    */
   public ActionForward get_orders_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // 1 �½���2����ˣ�3��׼��4�˻أ�5��Ч��6������7��ֹ��8ȡ��
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         final Map< String, Integer > data = new HashMap< String, Integer >();
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         // 1 �½�
         clientOrderHeaderVO.setStatus( "1" );
         final Integer status_1 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_1", status_1 );
         // 2�����
         clientOrderHeaderVO.setStatus( "2" );
         final Integer status_2 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_2", status_2 );
         // 3��׼
         clientOrderHeaderVO.setStatus( "3" );
         final Integer status_3 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_3", status_3 );
         // 4�˻�
         clientOrderHeaderVO.setStatus( "4" );
         final Integer status_4 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_4", status_4 );
         // 5��Ч
         clientOrderHeaderVO.setStatus( "5" );
         final Integer status_5 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_5", status_5 );
         // 6����
         clientOrderHeaderVO.setStatus( "6" );
         final Integer status_6 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_6", status_6 );
         // 7��ֹ
         clientOrderHeaderVO.setStatus( "7" );
         final Integer status_7 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_7", status_7 );
         // 8ȡ��
         clientOrderHeaderVO.setStatus( "8" );
         final Integer status_8 = dashboardService.getClientOrdersCountForDashboard( clientOrderHeaderVO );
         data.put( "status_8", status_8 );
         // ���� XML �ַ���
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
    * ajax �����ȡ������Ϣ
    */
   public ActionForward get_contractService_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // 1 �½���2����ˣ�3��׼��4�˻أ�5�Ѹ��£�6�鵵
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         final Map< String, Integer > data = new HashMap< String, Integer >();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            employeeContractVO.setClientId( getClientId( request, response ) );
            employeeContractVO.setCorpId( getCorpId( request, response ) );
         }

         //���÷���Э��
         employeeContractVO.setFlag( "2" );
         // 1 �½�
         employeeContractVO.setStatus( "1" );
         final Integer status_1 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_1", status_1 );
         // 2�����
         employeeContractVO.setStatus( "2" );
         final Integer status_2 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_2", status_2 );
         // 3��׼
         employeeContractVO.setStatus( "3" );
         final Integer status_3 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_3", status_3 );
         // 4�˻�
         employeeContractVO.setStatus( "4" );
         final Integer status_4 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_4", status_4 );
         // 5�Ѹ���
         employeeContractVO.setStatus( "5" );
         final Integer status_5 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_5", status_5 );
         // 6�鵵
         employeeContractVO.setStatus( "6" );
         final Integer status_6 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_6", status_6 );
         // 7����
         employeeContractVO.setStatus( "7" );
         final Integer status_7 = dashboardService.getEmployeeContractCountForDashboard( employeeContractVO );
         data.put( "status_7", status_7 );
         // ���� XML �ַ���
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
    * ajax �����ȡ����
    */
   public ActionForward get_attendance_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final Map< String, List< Integer > > data = new HashMap< String, List< Integer > >();
         final List< Integer > leave_durations = new ArrayList< Integer >();
         final List< Integer > ot_durations = new ArrayList< Integer >();
         // ���       3��׼��5�ѽ���
         LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            leaveHeaderVO.setClientId( getClientId( request, response ) );
            leaveHeaderVO.setCorpId( getCorpId( request, response ) );
            leaveHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         }
         leaveHeaderVO.setStatus( "3,5" );
         // ��12��������
         List< String > recent12Months = KANUtil.getRecent12MonthsDate( Calendar.getInstance(), new ArrayList< String >(), 12 );
         for ( int i = 0; i < recent12Months.size(); i = i + 2 )
         {
            leaveHeaderVO.setEstimateStartDate( recent12Months.get( i ) );
            leaveHeaderVO.setEstimateEndDate( recent12Months.get( i + 1 ) );
            leave_durations.add( dashboardService.getAttendanceLeaveDurationForDashboard( leaveHeaderVO ) );
         }
         // ��ӽ�12���µ����
         data.put( "attendance_leave_durations", leave_durations );
//         // �Ӱ�    3��׼��5�ѽ���
//         OTHeaderVO oTHeaderVO = new OTHeaderVO();
//         oTHeaderVO.setAccountId( getAccountId( request, response ) );
//         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
//         {
//            oTHeaderVO.setClientId( getClientId( request, response ) );
//            oTHeaderVO.setCorpId( getCorpId( request, response ) );
//            // �������HR ְ�� ְ�ܿ����Լ���
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
         // ���� XML �ַ���
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
    * ajax �����ȡ�籣
    */
   public ActionForward get_sb_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final List< Double > status_5_personal = new ArrayList< Double >();
         final List< Double > status_5_company = new ArrayList< Double >();
         final List< Double > status_adj_5_personal = new ArrayList< Double >();
         final List< Double > status_adj_5_company = new ArrayList< Double >();
         // 5�ѽ���  (5�������ĵ���)
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            sbAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            // �������HR ְ�� ְ�ܿ����Լ���
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

            // 5�ѽ���
            sbHeaderVO.setStatus( "5" );
            tempMap = dashboardService.getSBAmountForDashboard( sbHeaderVO );
            status_5_company.add( tempMap.get( "sumCompany" ) );
            status_5_personal.add( tempMap.get( "sumPersonal" ) );
            // 5�������ĵ���
            sbAdjustmentHeaderVO.setStatus( "5" );
            tempMap = dashboardService.getSBAdjAmountForDashboard( sbAdjustmentHeaderVO );
            status_adj_5_company.add( tempMap.get( "sumCompany" ) );
            status_adj_5_personal.add( tempMap.get( "sumPersonal" ) );
         }

         // ���� XML �ַ���
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
    * ajax �����ȡ�̱�
    */
   public ActionForward get_cb_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         final List< Double > status_5_cost = new ArrayList< Double >();
         final List< Double > status_5_price = new ArrayList< Double >();
         // 5�ѽ���  (5�������ĵ���)
         CBHeaderVO cbHeaderVO = new CBHeaderVO();
         cbHeaderVO.setAccountId( getAccountId( request, response ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            cbHeaderVO.setClientId( getClientId( request, response ) );
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
            // �������HR ְ�� ְ�ܿ����Լ���
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
            // 5�ѽ���
            cbHeaderVO.setStatus( "5" );
            tempMap = dashboardService.getCBAmountForDashboard( cbHeaderVO );
            status_5_cost.add( tempMap.get( "amountSalesCost" ) );
            status_5_price.add( tempMap.get( "amountSalesPrice" ) );
         }

         // ���� XML �ַ���
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
    * ajax �����ȡ����
    */
   public ActionForward get_settlement_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );

         // ��ѯ���Ľ������״̬��Ϊ̨��
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

         // ���� XML �ַ���
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
    * ajax �����ȡн��/����
    */
   public ActionForward get_payment_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         //3 �ѷ���  ,5 �������ĵ���
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

         // ���� XML �ַ���
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
    * ajax ����Ա�������Ϣ
    */
   public ActionForward get_employeeChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final DashboardService dashboardService = ( DashboardService ) getService( "dashboardService" );
         List< String > recent12YearMonths = KANUtil.getRecent12YearMonths( Calendar.getInstance(), new ArrayList< String >(), 12 );
         // ��ĩ��ְ,ÿ��������ÿ����ְ
         List< Integer > onJob = new ArrayList< Integer >();
         List< Integer > newEmployees = new ArrayList< Integer >();
         List< Integer > dismission = new ArrayList< Integer >();
         // onjon ����
         final EmployeeContractVO onJobContractVO = new EmployeeContractVO();
         // new ����
         final EmployeeContractVO tempEmployeeContractVO = new EmployeeContractVO();
         // dismission ����
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
            // ��ĩ��ְ  ��ͬδ����
            // ������ְ  ��ͬδ����
            final String year = yearMonth.split( "/" )[ 0 ];
            final String month = yearMonth.split( "/" )[ 1 ];
            onJobContractVO.setStartDateTo( KANUtil.getLastDayOfMonth( Integer.parseInt( year ), Integer.parseInt( month ) - 1 ).substring( 0, 10 ) );
            onJobContractVO.setEndDateFrom( KANUtil.getFirstDayOfMonth( Integer.parseInt( year ), Integer.parseInt( month ) - 1 ).substring( 0, 10 ) );
            onJobContractVO.setStatus( "3,5,6,7" );
            onJob.add( dashboardService.getEmployeeOnJobCountForDashboard( onJobContractVO ) );

            // Ա������  �Ͷ���ͬ��ʼ����
            tempEmployeeContractVO.setMonthly( yearMonth );
            tempEmployeeContractVO.setStatus( "3,5,6" );
            newEmployees.add( dashboardService.getEmployeeNewCountForDashboard( tempEmployeeContractVO ) );

            // ��ְ  �Ͷ���ͬ��ְ����  �����·ݴ�0
            tempEmployeeContractVO2.setMonthly( yearMonth );
            tempEmployeeContractVO2.setStatus( "7" );
            dismission.add( dashboardService.getEmployeeDismissionCountForDashboard( tempEmployeeContractVO2 ) );
         }

         // ���� XML �ַ���
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
    * ajax �����ȡ�տ�
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