package com.kan.base.web.renders.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.SettingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**
 * Render for New or Modify Page
 * 
 * @author Kevin
 */
public class DashboardRender
{
   public static String generateDataView( HttpServletRequest request, Map< String, Integer > data, boolean isInHouseRole ) throws KANException
   {
      StringBuffer result = new StringBuffer();
      // ��Ӧ�̣�����ͨ����
      final Integer vendorCount = data.get( "vendorCount" );
      // �ͻ�������ͨ����
      final Integer clientCount = data.get( "clientCount" );
      // �����ͬ����׼�����¡��鵵��3 5 6 
      final Integer clientContractCount_3 = data.get( "clientContractCount_3" );
      final Integer clientContractCount_5 = data.get( "clientContractCount_5" );
      final Integer clientContractCount_6 = data.get( "clientContractCount_6" );
      // ��������׼����Ч��3 5
      final Integer clientOrdersCount_3 = data.get( "clientOrdersCount_3" );
      final Integer clientOrdersCount_5 = data.get( "clientOrdersCount_5" );
      // ��Ա(��ְ) 1
      final Integer employeeContractCount_1 = data.get( "employeeContractCount_1" );
      //outCnvbaseFont �⻭������ outCnvbaseFont �����С outCnvbaseFontColor ������ɫ
      result.append( "<chart caption='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view" ) + "' yAxisName='' xAxisName='"
            + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view.detail" ) + "' " + commonChartStyle( "dataView" ) + " >" );
      result.append( "<categories>" );
      result.append( "<category label='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view.vendor" ) + "' />" );
      if ( !isInHouseRole )
      {
         result.append( "<category label='�ͻ�' />" );
         result.append( "<category label='�����ͬ' />" );
         result.append( "<category label='����' />" );
      }

      if ( !isInHouseRole )
         result.append( "<category label='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view.employee1" ) + "' />" );
      else
         result.append( "<category label='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view.employee2" ) + "' />" );
      result.append( "</categories>" );
      result.append( "<dataset>" );
      result.append( "<set color='cdc3f8'  value='" + vendorCount + "' toolText='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view.vendor.approval" )
            + ":" + vendorCount + "'/> " );
      if ( !isInHouseRole )
      {
         result.append( "<set color='f8f6c3'  value='" + clientCount + "' toolText='����ͨ��:" + clientCount + "'/> " );
         result.append( "<set color='c9f8c3'  value='" + ( clientContractCount_3 + clientContractCount_5 + clientContractCount_6 ) + "' toolText='��׼:" + clientContractCount_3
               + " , ����:" + clientContractCount_5 + " , �鵵:" + clientContractCount_6 + "'/> " );
         result.append( "<set color='f8c3da'  value='" + ( clientOrdersCount_3 + clientOrdersCount_5 ) + "' toolText='��׼:" + clientOrdersCount_3 + " , ��Ч:" + clientOrdersCount_5
               + "'/> " );
      }
      result.append( "<set color='c3e7f8'  value='" + ( employeeContractCount_1 ) + "' toolText='"
            + KANUtil.getProperty( request.getLocale(), "management.dashboard.data.view.employee.the.job" ) + ":" + employeeContractCount_1 + "'/> " );
      result.append( "</dataset>" );
      result.append( commonStyle() );
      result.append( "</chart>" );
      return result.toString();
   }

   // �����ͬ
   public static String generateClientContract( Map< String, Integer > data ) throws KANException
   {
      // 1 �½���2����ˣ�3��׼��4�˻أ�5�Ѹ��£�6�鵵
      StringBuffer result = new StringBuffer();
      final Integer status_1 = data.get( "status_1" );
      final Integer status_2 = data.get( "status_2" );
      final Integer status_3 = data.get( "status_3" );
      final Integer status_4 = data.get( "status_4" );
      final Integer status_5 = data.get( "status_5" );
      final Integer status_6 = data.get( "status_6" );
      result.append( "<chart caption='�����ͬ' bgAngle='360' startingAngle='70' " + commonChartStyle( "clientContract" ) + " >" );
      result.append( status_1 == 0 ? "" : "<set color='92f2f6' label='�½�' value='" + status_1 + "' /> " );
      result.append( status_2 == 0 ? "" : "<set color='b5f692' label='�����' value='" + status_2 + "' /> " );
      result.append( status_3 == 0 ? "" : "<set color='f6db92' label='��׼' value='" + status_3 + "'  /> " );
      result.append( status_4 == 0 ? "" : "<set color='f692ae' label='�˻�' value='" + status_4 + "'  /> " );
      result.append( status_5 == 0 ? "" : "<set color='f092f6' label='�Ѹ���' value='" + status_5 + "'  /> " );
      result.append( status_6 == 0 ? "" : "<set color='bcf692' label='�鵵' value='" + status_6 + "' /> " );
      result.append( commonStyle() );
      result.append( "</chart>" );
      return result.toString();
   }

   // �ͻ�����
   public static String generateClientOrders( Map< String, Integer > data ) throws KANException
   {
      // 1 �½���2����ˣ�3��׼��4�˻أ�5��Ч��6������7��ֹ��8ȡ��
      StringBuffer result = new StringBuffer();
      final Integer status_1 = data.get( "status_1" );
      final Integer status_2 = data.get( "status_2" );
      final Integer status_3 = data.get( "status_3" );
      final Integer status_4 = data.get( "status_4" );
      final Integer status_5 = data.get( "status_5" );
      final Integer status_6 = data.get( "status_6" );
      final Integer status_7 = data.get( "status_7" );
      final Integer status_8 = data.get( "status_8" );
      result.append( "<chart caption='����'  " + commonChartStyle( "clientOrders" ) + " >" );
      result.append( status_1 == 0 ? "" : "<set color='ADADAD' label='�½�' value='" + status_1 + "'  /> " );
      result.append( status_2 == 0 ? "" : "<set color='4A4AFF' label='�����' value='" + status_2 + "'  /> " );
      result.append( status_3 == 0 ? "" : "<set color='45b9f5' label='��׼' value='" + status_3 + "' /> " );
      result.append( status_4 == 0 ? "" : "<set color='FF5151' label='�˻�' value='" + status_4 + "' /> " );
      result.append( status_5 == 0 ? "" : "<set color='A3D1D1' label='��Ч' value='" + status_5 + "' /> " );
      result.append( status_6 == 0 ? "" : "<set color='8080C0' label='����' value='" + status_6 + "' /> " );
      result.append( status_7 == 0 ? "" : "<set color='984B4B' label='��ֹ' value='" + status_7 + "' /> " );
      result.append( status_8 == 0 ? "" : "<set color='f5b145' label='ȡ��' value='" + status_8 + "' /> " );
      result.append( commonStyle() );
      result.append( "</chart>" );
      return result.toString();
   }

   // ����Э��
   public static String generateContractService( HttpServletRequest request, Map< String, Integer > data, boolean isRoleInHouse ) throws KANException
   {
      final List< MappingVO > contractStatuses = KANUtil.getMappings( request.getLocale(), "business.employee.contract.statuses" );
      // 1 �½���2����ˣ�3��׼��4�˻أ�5�Ѹ��£�6�鵵
      StringBuffer result = new StringBuffer();
      final Integer status_1 = data.get( "status_1" );
      final Integer status_2 = data.get( "status_2" );
      final Integer status_3 = data.get( "status_3" );
      final Integer status_4 = data.get( "status_4" );
      final Integer status_5 = data.get( "status_5" );
      final Integer status_6 = data.get( "status_6" );
      final Integer status_7 = data.get( "status_7" );
      final String caption = isRoleInHouse ? KANUtil.getProperty( request.getLocale(), "management.dashboard.contract2" )
            : KANUtil.getProperty( request.getLocale(), "management.dashboard.contract1" );
      result.append( "<chart caption='" + caption + "' " + commonChartStyle( "contractService" ) + ">" );
      result.append( status_1 == 0 ? "" : "<set  label='" + contractStatuses.get( 1 ).getMappingValue() + "' value='" + status_1 + "' color='2675B4'  /> " );
      result.append( status_2 == 0 ? "" : "<set  label='" + contractStatuses.get( 2 ).getMappingValue() + "' value='" + status_2 + "' color='268926' /> " );
      result.append( status_3 == 0 ? "" : "<set  label='" + contractStatuses.get( 3 ).getMappingValue() + "' value='" + status_3 + "' color='5dc0dd' isSliced='1' /> " );
      result.append( status_4 == 0 ? "" : "<set  label='" + contractStatuses.get( 4 ).getMappingValue() + "' value='" + status_4 + "' color='ADADAD'  /> " );
      result.append( status_5 == 0 ? "" : "<set  label='" + contractStatuses.get( 5 ).getMappingValue() + "' value='" + status_5 + "' color='C2780D' /> " );
      result.append( status_6 == 0 ? "" : "<set  label='" + contractStatuses.get( 6 ).getMappingValue() + "' value='" + status_6 + "' color='f5b145' /> " );
      result.append( status_7 == 0 ? "" : "<set  label='" + contractStatuses.get( 7 ).getMappingValue() + "' value='" + status_7 + "' color='432877' /> " );
      result.append( commonStyle() );
      result.append( "</chart>" );
      return result.toString();
   }

   // ����
   public static String generateAttendance( HttpServletRequest request, Map< String, List< Integer > > data, List< String > months ) throws KANException
   {
      StringBuffer result = new StringBuffer();
      final List< Integer > attendance_leave_duration = data.get( "attendance_leave_durations" );
      //final List< Integer > attendance_ot_duration = data.get( "attendance_ot_durations" );

      result.append( "<chart caption='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.attendance" ) + "' yAxisName='' " + commonChartStyle( "attendance" )
            + ">" );
      result.append( "<categories>" );
      for ( int i = months.size() - 1; i >= 0; i = i - 2 )
      {
         result.append( "<category label='" + months.get( i ).substring( 0, 7 ) + "' /> " );
      }
      result.append( "</categories>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.attendance.leave" ) + "' color='FF5151' >" );
      for ( int i = attendance_leave_duration.size() - 1; i >= 0; i-- )
      {
         result.append( "<set  value='" + attendance_leave_duration.get( i ) + "' /> " );
      }
      result.append( "</dataset>" );
//      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.attendance.ot" ) + "' color='0072E3'>" );
//      for ( int i = attendance_ot_duration.size() - 1; i >= 0; i-- )
//      {
//         result.append( "<set  value='" + attendance_ot_duration.get( i ) + "' /> " );
//      }
      //result.append( "</dataset>" );
      result.append( commonStyle() );
      result.append( "</chart>" );

      return result.toString();
   }

   /**
    * �籣
    * @param status_3 ȷ��
    * @param status_4 �ύ
    * @param status_5 �ѽ���
    * @param status_adj_5 ����
    * @param recent12YearMonths
    * @return
    */
   public static String generateSB( HttpServletRequest request, List< Double > status_5_personal, List< Double > status_5_company, List< Double > status_adj_5_personal,
         List< Double > status_adj_5_company, List< String > recent12YearMonths )
   {
      StringBuffer result = new StringBuffer();
      final DecimalFormat df = new DecimalFormat( "###,##0.00" );
      result.append( "<chart  caption='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.sb" ) + "' yAxisName='' " + commonChartStyle( "sb" ) + "> " );
      result.append( "<categories>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         result.append( "<category label='" + recent12YearMonths.get( i ) + "' />" );
      }
      result.append( "</categories>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.sb.company" ) + "' color='23f1c8'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         final Double companySum = status_5_company.get( i ) + status_adj_5_company.get( i );
         String toolText = KANUtil.getProperty( request.getLocale(), "management.dashboard.sb.company.total" ) + ":��" + status_5_company.get( i ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "management.dashboard.sb.adjustment" ) + ":��" + df.format( status_adj_5_company.get( i ) );
         result.append( "<set  value='" + companySum + "' toolText='" + toolText + "' /> " );
      }
      result.append( "</dataset>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.sb.personal" ) + "' color='f1ea23'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         // �������ݺ��·����ݳ��ȶ�Ϊ���
         final Double personSum = status_5_personal.get( i ) + status_adj_5_personal.get( i );
         String toolText = KANUtil.getProperty( request.getLocale(), "management.dashboard.sb.personal.total" ) + ":��" + df.format( status_5_personal.get( i ) ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "management.dashboard.sb.adjustment" ) + ":��" + df.format( status_adj_5_personal.get( i ) );
         result.append( "<set  value='" + personSum + "' toolText='" + toolText + "' /> " );
      }
      result.append( "</dataset>" );
      result.append( commonStyle() );
      result.append( "</chart>" );

      return result.toString();
   }

   public static String generateCB( HttpServletRequest request, List< Double > status_5_cost, List< Double > status_5_price, List< String > recent12YearMonths, boolean flag )
   {
      // inhouse  ֻ���ɱ�,hr service ֻ���۸�
      StringBuffer result = new StringBuffer();
      result.append( "<chart  caption='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.cb" ) + "' yAxisName='' " + commonChartStyle( "cb" ) + ">" );
      result.append( "<categories>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         result.append( "<category label='" + recent12YearMonths.get( i ) + "' />" );
      }
      result.append( "</categories>" );

      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.cb.settled" ) + "'  color='ebee19'>" );
      if ( flag )
      {
         for ( int i = status_5_cost.size() - 1; i >= 0; i-- )
         {
            result.append( "<set value='" + status_5_cost.get( i ) + "' />" );
         }
      }
      else
      {
         for ( int i = status_5_price.size() - 1; i >= 0; i-- )
         {
            result.append( "<set value='" + status_5_price.get( i ) + "' />" );
         }
      }
      result.append( "</dataset>" );
      result.append( commonStyle() );
      result.append( "</chart>" );

      return result.toString();
   }

   public static String generateSettlement( HttpServletRequest request, List< Double > billAmountCompanyList, List< Double > costAmountCompanyList,
         List< Double > billAmountCompanyAdjList, List< Double > costAmountCompanyAdjList, List< String > recent12YearMonths )
   {
      StringBuffer result = new StringBuffer();
      final DecimalFormat df = new DecimalFormat( "###,##0.00" );
      result.append( "<chart  caption='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement" ) + "' PYaxisname='' sFormatNumberScale='1' "
            + commonChartStyle( "settlement" ) + ">" );
      result.append( "<categories>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         result.append( "<category label='" + recent12YearMonths.get( i ) + "' />" );
      }
      result.append( "</categories>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.bill.amount" ) + "' color='5ec5f5' showValues='1'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         final Double billSum = billAmountCompanyList.get( i ) + billAmountCompanyAdjList.get( i );
         String toolText = KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.bill.amount.toal" ) + ":��" + df.format( billSum ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.bill.amount" ) + ":��" + df.format( billAmountCompanyList.get( i ) ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.adjustment" ) + ":��" + df.format( billAmountCompanyAdjList.get( i ) ) + "\r\n";
         result.append( "<set value='" + billSum + "' toolText='" + toolText + "'/>" );
      }
      result.append( "</dataset>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.cost.amount" ) + "' color='f577ae' showValues='1'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         final Double costSum = costAmountCompanyList.get( i ) + costAmountCompanyAdjList.get( i );
         String toolText = KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.cost.amount.total" ) + ":��" + df.format( costSum ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.pay.amount" ) + ":��" + df.format( costAmountCompanyList.get( i ) ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.adjustment" ) + ":��" + df.format( costAmountCompanyAdjList.get( i ) ) + "\r\n";
         result.append( "<set  value='" + costSum + "' toolText='" + toolText + "' />" );
      }
      result.append( "</dataset>" );

      result.append( "<dataset seriesname='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.settlement.profit.trend" )
            + "' showValues='0' parentYAxis='S' color='66ff00' lineThickness='1'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         final Double costSum = costAmountCompanyList.get( i ) + costAmountCompanyAdjList.get( i );
         final Double billSum = billAmountCompanyList.get( i ) + billAmountCompanyAdjList.get( i );
         result.append( "<set  value='" + ( billSum - costSum ) + "' />" );
      }
      result.append( "</dataset>" );

      result.append( commonStyle() );
      result.append( "</chart>" );
      return result.toString();
   }

   public static String generatePayment( HttpServletRequest request, List< Double > addtionalBillAmountPersonal_status_3, List< Double > taxAmountPersonal_status_3,
         List< Double > addtionalBillAmountPersonal_adj_status_5, List< Double > taxAmountPersonal_adj_status_5, List< String > recent12YearMonths )
   {
      StringBuffer result = new StringBuffer();
      final DecimalFormat df = new DecimalFormat( "###,##0.00" );
      result.append( "<chart  caption='" + KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment" ) + "' yAxisName='' " + commonChartStyle( "payment" ) + ">" );
      result.append( "<categories>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         result.append( "<category label='" + recent12YearMonths.get( i ) + "' />" );
      }
      result.append( "</categories>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment.actual" ) + "' color='74cdf7'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         final Double addtionalBillAmountPersonalSum = addtionalBillAmountPersonal_status_3.get( i ) + addtionalBillAmountPersonal_adj_status_5.get( i );
         String toolText = KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment.personal" ) + ":��" + df.format( addtionalBillAmountPersonal_status_3.get( i ) )
               + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment.adjustmnet" ) + ":��" + df.format( addtionalBillAmountPersonal_adj_status_5.get( i ) );
         result.append( "<set  value='" + addtionalBillAmountPersonalSum + "' toolText='" + toolText + "' />" );
      }
      result.append( "</dataset>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment.tax" ) + "' color='f7f160'>" );
      for ( int i = recent12YearMonths.size() - 1; i >= 0; i-- )
      {
         final Double taxSum = taxAmountPersonal_status_3.get( i ) + taxAmountPersonal_adj_status_5.get( i );
         String toolText = KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment.tax" ) + ":��" + df.format( taxAmountPersonal_status_3.get( i ) ) + "\r\n";
         toolText += KANUtil.getProperty( request.getLocale(), "managment.dashboard.payment.adjustmnet" ) + ":��" + df.format( taxAmountPersonal_adj_status_5.get( i ) );
         result.append( "<set  value='" + taxSum + "' toolText='" + toolText + "' />" );
      }
      result.append( "</dataset>" );
      result.append( commonStyle() );
      result.append( "</chart>" );
      return result.toString();
   }

   public static String generateEmployeeChange( HttpServletRequest request, List< Integer > onJob, List< Integer > newEmployees, List< Integer > dismission, List< String > months )
   {
      StringBuffer result = new StringBuffer();

      result.append( "<chart caption='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.employee.change" ) + "' yAxisName='' "
            + commonChartStyle( "employeeChange" ) + ">" );
      result.append( "<categories>" );
      for ( int i = months.size() - 1; i >= 0; i-- )
      {
         result.append( "<category label='" + months.get( i ) + "' /> " );
      }
      result.append( "</categories>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.employee.change.on.job" ) + "' color='FF5151' >" );
      for ( int i = onJob.size() - 1; i >= 0; i-- )
      {
         result.append( "<set  value='" + onJob.get( i ) + "' /> " );
      }
      result.append( "</dataset>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.employee.change.new" ) + "' color='0072E3'>" );
      for ( int i = newEmployees.size() - 1; i >= 0; i-- )
      {
         result.append( "<set  value='" + newEmployees.get( i ) + "' /> " );
      }
      result.append( "</dataset>" );
      result.append( "<dataset seriesName='" + KANUtil.getProperty( request.getLocale(), "management.dashboard.employee.change.dismission" ) + "' color='ebee19'>" );
      for ( int i = dismission.size() - 1; i >= 0; i-- )
      {
         result.append( "<set  value='" + dismission.get( i ) + "' /> " );
      }
      result.append( "</dataset>" );
      result.append( commonStyle() );
      result.append( "</chart>" );

      return result.toString();
   }

   private static String commonStyle()
   {
      final String path = "/KAN-HRO-INF/dashboard.properties";
      StringBuffer result = new StringBuffer();
      result.append( "<styles>" );
      result.append( "<definition>" );
      result.append( "<style name='myCaptionFont' type='font' " );
      result.append( " font='" + KANUtil.getPropertiesValue( path, "font" ) + "' " );
      result.append( " size='" + KANUtil.getPropertiesValue( path, "size" ) + "' " );
      result.append( " color='" + KANUtil.getPropertiesValue( path, "color" ) + "' " );
      result.append( " bold='" + KANUtil.getPropertiesValue( path, "bold" ) + "' " );
      result.append( " italic='" + KANUtil.getPropertiesValue( path, "italic" ) + "' " );
      result.append( " underline='" + KANUtil.getPropertiesValue( path, "underline" ) + "' " );
      result.append( " bgColor='" + KANUtil.getPropertiesValue( path, "bgColor" ) + "' " );
      result.append( " borderColor='" + KANUtil.getPropertiesValue( path, "borderColor" ) + "' " );
      result.append( " leftMargin='" + KANUtil.getPropertiesValue( path, "leftMargin" ) + "' " );
      result.append( " letterSpacing='" + KANUtil.getPropertiesValue( path, "letterSpacing" ) + "' " );
      result.append( " />" );
      result.append( "<style name='myDataLabelsFont' type='font' " );
      result.append( " font='" + KANUtil.getPropertiesValue( path, "l_font" ) + "' " );
      result.append( " size='" + KANUtil.getPropertiesValue( path, "l_size" ) + "' " );
      result.append( " color='" + KANUtil.getPropertiesValue( path, "l_color" ) + "' " );
      result.append( " />" );
      result.append( "<style name='myXAxisName' type='font' " );
      result.append( " font='" + KANUtil.getPropertiesValue( path, "x_font" ) + "' " );
      result.append( " size='" + KANUtil.getPropertiesValue( path, "x_size" ) + "' " );
      result.append( " color='" + KANUtil.getPropertiesValue( path, "x_color" ) + "' " );
      result.append( " />" );
      result.append( "<style name='myYAxisName' type='font' " );
      result.append( " font='" + KANUtil.getPropertiesValue( path, "y_font" ) + "' " );
      result.append( " size='" + KANUtil.getPropertiesValue( path, "y_size" ) + "' " );
      result.append( " color='" + KANUtil.getPropertiesValue( path, "y_color" ) + "' " );
      result.append( " />" );
      result.append( "</definition>" );
      result.append( "<application>" );
      result.append( "<apply toObject='Caption' styles='myCaptionFont' />" );
      result.append( "<apply toObject='DataLabels' styles='myDataLabelsFont' />" );
      result.append( "<apply toObject='XAxisName' styles='myXAxisName' />" );
      result.append( "<apply toObject='YAxisName' styles='myYAxisName' />" );
      result.append( "</application>" );
      result.append( "</styles>" );
      return result.toString();
   }

   private static String commonChartStyle( String type )
   {
      final String path = "/KAN-HRO-INF/dashboard.properties";
      type += ".";
      StringBuffer result = new StringBuffer();
      result.append( " showLabels='" + KANUtil.getPropertiesValue( path, type + "showLabels" ) + "' " );
      result.append( " rotateYAxisName='" + KANUtil.getPropertiesValue( path, type + "y_rotateYAxisName" ) + "' " );
      result.append( " labelDisplay='" + KANUtil.getPropertiesValue( path, type + "labelDisplay" ) + "' " );
      result.append( " useEllipsesWhenOverflow='" + KANUtil.getPropertiesValue( path, type + "useEllipsesWhenOverflow" ) + "' " );
      result.append( " slantLabels='" + KANUtil.getPropertiesValue( path, type + "slantLabels" ) + "' " );
      result.append( " labelStep='" + KANUtil.getPropertiesValue( path, type + "labelStep" ) + "' " );
      result.append( " showValues='" + KANUtil.getPropertiesValue( path, type + "showValues" ) + "' " );
      result.append( " rotateValues='" + KANUtil.getPropertiesValue( path, type + "rotateValues" ) + "' " );
      result.append( " placeValuesInside='" + KANUtil.getPropertiesValue( path, type + "placeValuesInside" ) + "' " );
      result.append( " baseFont='" + KANUtil.getPropertiesValue( path, type + "baseFont" ) + "' " );
      result.append( " baseFontSize='" + KANUtil.getPropertiesValue( path, type + "baseFontSize" ) + "' " );
      result.append( " baseFontColor='" + KANUtil.getPropertiesValue( path, type + "baseFontColor" ) + "' " );
      result.append( " bgcolor='" + KANUtil.getPropertiesValue( path, type + "chart_bgcolor" ) + "' " );
      result.append( " canvasBorderThickness='" + KANUtil.getPropertiesValue( path, type + "canvasBorderThickness" ) + "' " );
      result.append( " canvasBorderColor='" + KANUtil.getPropertiesValue( path, type + "canvasBorderColor" ) + "' " );
      result.append( " plotFillAngle='" + KANUtil.getPropertiesValue( path, type + "plotFillAngle" ) + "' " );
      result.append( " plotBorderColor='" + KANUtil.getPropertiesValue( path, type + "plotBorderColor" ) + "' " );
      result.append( " showAlternateVGridColor='" + KANUtil.getPropertiesValue( path, type + "showAlternateVGridColor" ) + "' " );
      result.append( " divLineAlpha='" + KANUtil.getPropertiesValue( path, type + "divLineAlpha" ) + "' " );
      result.append( " showBorder='" + KANUtil.getPropertiesValue( path, type + "showBorder" ) + "' " );
      result.append( " bordercolor='" + KANUtil.getPropertiesValue( path, type + "bordercolor" ) + "' " );
      result.append( " useRoundEdges='" + KANUtil.getPropertiesValue( path, type + "useRoundEdges" ) + "' " );
      result.append( " showSum='" + KANUtil.getPropertiesValue( path, type + "showSum" ) + "' " );
      result.append( " showLegend='" + KANUtil.getPropertiesValue( path, type + "showLegend" ) + "' " );
      result.append( " numberPrefix='" + KANUtil.getPropertiesValue( path, type + "numberPrefix" ) + "' " );
      result.append( " decimals='" + KANUtil.getPropertiesValue( path, type + "decimals" ) + "' " );
      result.append( " formatNumberScale='" + KANUtil.getPropertiesValue( path, type + "formatNumberScale" ) + "' " );
      result.append( " showAboutMenuItem='" + KANUtil.getPropertiesValue( path, "showAboutMenuItem" ) + "' " );
      result.append( " exportEnabled='" + KANUtil.getPropertiesValue( path, "exportEnabled" ) + "' " );
      result.append( " exportAtClient='" + KANUtil.getPropertiesValue( path, "exportAtClient" ) + "' " );
      result.append( " exportHandler='" + type + KANUtil.getPropertiesValue( path, "exportHandler" ) + "' " );
      result.append( " exportDialogMessage='" + KANUtil.getPropertiesValue( path, "exportDialogMessage" ) + "' " );
      result.append( " exportFormats='" + KANUtil.getPropertiesValue( path, "exportFormats" ) + "' " );
      return result.toString();
   }

   public static String generateDashboard( final HttpServletRequest request ) throws KANException
   {
      StringBuffer result = new StringBuffer();
      SettingVO settingVO = ( SettingVO ) request.getAttribute( "settingVO" );
      Map< String, Integer > namesMap = new HashMap< String, Integer >();
      namesMap.put( "baseInfo", KANUtil.filterEmpty( settingVO.getBaseInfoRank() ) != null ? Integer.parseInt( settingVO.getBaseInfoRank() ) : -1 );
      namesMap.put( "message", KANUtil.filterEmpty( settingVO.getMessageRank() ) != null ? Integer.parseInt( settingVO.getMessageRank() ) : -1 );
      namesMap.put( "dataView", KANUtil.filterEmpty( settingVO.getDataViewRank() ) != null ? Integer.parseInt( settingVO.getDataViewRank() ) : -1 );
      namesMap.put( "clientContract", KANUtil.filterEmpty( settingVO.getClientContractRank() ) != null ? Integer.parseInt( settingVO.getClientContractRank() ) : -1 );
      namesMap.put( "orders", KANUtil.filterEmpty( settingVO.getOrdersRank() ) != null ? Integer.parseInt( settingVO.getOrdersRank() ) : -1 );
      namesMap.put( "contractService", KANUtil.filterEmpty( settingVO.getContractServiceRank() ) != null ? Integer.parseInt( settingVO.getContractServiceRank() ) : -1 );
      namesMap.put( "attendance", KANUtil.filterEmpty( settingVO.getAttendanceRank() ) != null ? Integer.parseInt( settingVO.getAttendanceRank() ) : -1 );
      namesMap.put( "sb", KANUtil.filterEmpty( settingVO.getSbRank() ) != null ? Integer.parseInt( settingVO.getSbRank() ) : -1 );
      namesMap.put( "cb", KANUtil.filterEmpty( settingVO.getCbRank() ) != null ? Integer.parseInt( settingVO.getCbRank() ) : -1 );
      namesMap.put( "settlement", KANUtil.filterEmpty( settingVO.getSettlementRank() ) != null ? Integer.parseInt( settingVO.getSettlementRank() ) : -1 );
      namesMap.put( "payment", KANUtil.filterEmpty( settingVO.getPaymentRank() ) != null ? Integer.parseInt( settingVO.getPaymentRank() ) : -1 );
      namesMap.put( "income", KANUtil.filterEmpty( settingVO.getIncomeRank() ) != null ? Integer.parseInt( settingVO.getIncomeRank() ) : -1 );
      namesMap.put( "employeeChange", KANUtil.filterEmpty( settingVO.getEmployeeChangeRank() ) != null ? Integer.parseInt( settingVO.getEmployeeChangeRank() ) : -1 );
      String[] names = new String[] { "baseInfo", "message", "dataView", "clientContract", "orders", "contractService", "attendance", "sb", "cb", "settlement", "payment",
            "income", "employeeChange" };
      // ����
      for ( int i = 0; i < names.length; i++ )
      {
         for ( int j = i + 1; j < names.length; j++ )
         {
            if ( namesMap.get( names[ i ] ) > namesMap.get( names[ j ] ) )
            {
               String temp = names[ i ];
               names[ i ] = names[ j ];
               names[ j ] = temp;
            }
         }
      }
      for ( String name : names )
      {
         // ���� �籣 �̱� ����
         if ( "baseInfo".equals( name ) && "1".equals( settingVO.getBaseInfo() ) && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"baseInfo\" class=\"box\" style=\"display:none\">" );
            result.append( "<div class=\"head\"><label>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.base.info" ) + "</label></div>" );
            result.append( "<div class=\"\"><table style=\"border: 0px\"><tr>" );
            result.append( "<td><div class=\"photo-frame\"><img src=\"images/logo/kanlogo_blue_en_signatrue_choice_b.png\" alt=\"logo\" /></div></td>" );
            result.append( "<td>" );
            //result.append( "<ol><li><h1>About Company</h1></li></ol>" );
            result.append( "<ol><li><h3>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.base.info.account" )
                  + "</h3><ol><li><span id=\"nameCN\"></span></li></ol></li></ol>" );
            result.append( "<ol><li><h3>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.base.info.account.company.name" )
                  + "</h3><ol><li><span id=\"entityName\"></span></li></ol></li></ol>" );
            result.append( "<ol><li><h3>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.base.info.work.telephone" )
                  + "</h3><ol><li><span id=\"bizPhone\"></span></li></ol></li></ol>" );
            result.append( "<ol><li><h3>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.base.info.email.address" )
                  + "</h3><ol><li><span id=\"bizEmail\"></span></li></ol></li></ol>" );
            result.append( "<ol><li><h3>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.base.info.branch" )
                  + "</h3><ol><li><span id=\"department\"></span></li></ol></li></ol>" );
            result.append( "</td></tr></table></div></div>" );
         }
         if ( "message".equals( name ) && "1".equals( settingVO.getMessage() ) && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"message\" class=\"box\" style=\"display:none\">" );
            result.append( "<div class=\"head\" style=\"background-color:#ECF5FF;\"><label>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.notice" )
                  + "</label></div>" );
            result.append( "<div class=\"\"><ol><li><h1>Notice</h1></li></ol>" );
            result.append( "<ol><li style=\"display: none\"><h3>�ʼ�</h3><ol><li>δ����0���⣬����0����</li></ol></li>" );
            result.append( "<li><h3>"
                  + KANUtil.getProperty( request.getLocale(), "management.dashboard.notice.TODO" )
                  + "</h3><ol><li>"
                  + KANUtil.getProperty( request.getLocale(), "management.dashboard.notice.todo" )
                  + "(<a href=\"#\" onclick=\"link('workflowActualAction.do?proc=list_object_unfinished&actualStepStatus=2');\"><span id=\"sys_taskInfo_d\" class=\"highlight\">0</span></a>)"
                  + ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "��" : "" ) + "</li></ol></li>" );
            result.append( "<li><h3>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.notice.system.message" ) + "</h3><ol>" );
            result.append( "<li>" + KANUtil.getProperty( request.getLocale(), "management.dashboard.notice.unread" )
                  + "(<a href=\"#\" onclick=\"link('messageInfoAction.do?proc=list_receive&receptionStatus=2');\"><span id=\"sys_messageInfo_d\" class=\"highlight\">0</span></a>)"
                  + ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "��" : "" ) + "��" );
            result.append( KANUtil.getProperty( request.getLocale(), "management.dashboard.notice.total" )
                  + "(<a href=\"#\" onclick=\"link('messageInfoAction.do?proc=list_receive');\"><span id=\"sys_allMessageInfo_d\">0</span></a>)"
                  + ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "��" : "" ) + "</li>" );
            result.append( "</ol></li><li>" );
            result.append( "<ol><li><h3>&nbsp;</h3><ol><li>&nbsp;</li></ol></li></ol>" );
            result.append( "<ol><li><h3>&nbsp;</h3><ol><li>&nbsp;</li></ol></li></ol>" );
            result.append( "<ol><li><h3>&nbsp;</h3><ol><li>&nbsp;</li></ol></li></ol>" );
            result.append( "</li></ol></div></div>" );
         }
         if ( "dataView".equals( name ) && "1".equals( settingVO.getDataView() ) && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"dataView\" class=\"\" style=\"display:none\">" );
            //result.append( "<div class=\"head\"><label>���ݸ���</label></div>" );
            result.append( "<div class=\"dataView\" id=\"chart_dataView\"></div></div>" );
         }
         if ( "clientContract".equals( name ) && "1".equals( settingVO.getClientContract() )
               && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"clientContract\" class=\"\" style=\"display:none\">" );
            //result.append( "<div class=\"head\"><label>�����ͬ</label></div>" );
            result.append( "<div class=\"clientContract\" id=\"chart_clientContract\"></div></div>" );
         }
         if ( "orders".equals( name ) && "1".equals( settingVO.getOrders() ) && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"orders\" class=\"\" style=\"display:none\">" );
            //result.append( "<div class=\"head\"><label>����</label></div>" );
            result.append( "<div class=\"orders\" id=\"chart_orders\"></div></div>" );
         }
         if ( "contractService".equals( name ) && "1".equals( settingVO.getContractService() )
               && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"contractService\" class=\"\" style=\"display:none\">" );
            // result.append( "<div class=\"head\"><label>" + ( "1".equals( request.getAttribute( "role" ) ) ? "����Э��" : "�Ͷ���ͬ" ) + "</label></div>" );
            result.append( "<div class=\" contractService\" id=\"chart_contractService\"></div></div>" );
         }
         if ( "attendance".equals( name ) && "1".equals( settingVO.getAttendance() ) )
         {
            result.append( "<div id=\"attendance\" class=\"\" style=\"display:none\">" );
            // result.append( "<div class=\"head\"><label>����</label></div>" );
            result.append( "<div class=\" attendance\" id=\"chart_attendance\"></div></div>" );
         }
         if ( "sb".equals( name ) && "1".equals( settingVO.getSb() ) )
         {
            result.append( "<div id=\"sb\" class=\"\" style=\"display:none\">" );
            // result.append( "<div class=\"head\"><label>�籣</label></div>" );
            result.append( "<div class=\" sb\" id=\"chart_sb\"></div></div>" );
         }
         if ( "cb".equals( name ) && "1".equals( settingVO.getCb() ) )
         {
            result.append( "<div id=\"cb\" class=\"\" style=\"display:none\">" );
            //  result.append( "<div class=\"head\"><label>�̱�</label></div>" );
            result.append( "<div class=\" cb\" id=\"chart_cb\"></div></div>" );
         }
         if ( "settlement".equals( name ) && "1".equals( settingVO.getSettlement() ) && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"settlement\" class=\"\" style=\"display:none\">" );
            // result.append( "<div class=\"head\"><label>����</label></div>" );
            result.append( "<div class=\" settlement\" id=\"chart_settlement\"></div></div>" );
         }
         if ( "payment".equals( name ) && "1".equals( settingVO.getPayment() ) )
         {
            result.append( "<div id=\"payment\" class=\"\" style=\"display:none\">" );
            //result.append( "<div class=\"head\"><label>н��</label></div>" );
            result.append( "<div class=\" payment\" id=\"chart_payment\"></div></div>" );
         }
         if ( "employeeChange".equals( name ) && "1".equals( settingVO.getEmployeeChange() )
               && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {
            result.append( "<div id=\"employeeChange\" class=\"\" style=\"display:none\">" );
            //result.append( "<div class=\"head\"><label>н��</label></div>" );
            result.append( "<div class=\" employeeChange\" id=\"chart_employeeChange\"></div></div>" );
         }
         if ( "income".equals( name ) && "1".equals( settingVO.getIncome() ) && ( BaseAction.isHRFunction( request, null ) || !BaseAction.isInHouseRole( request, null ) ) )
         {

         }
      }

      return result.toString();
   }

}
