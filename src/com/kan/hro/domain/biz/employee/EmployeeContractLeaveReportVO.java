package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractLeaveReportVO extends BaseVO
{

   /***
    * Serial Version UID
    */
   private static final long serialVersionUID = 2669758441877626471L;

   // 雇员ID
   private String employeeId;

   // 雇员姓名（中文）
   private String employeeNameZH;

   // 雇员姓名（英文）
   private String employeeNameEN;

   // 雇员姓名（简称）
   private String employeeShortName;

   // 派送协议ID
   private String contractId;

   // 订单ID
   private String orderId;

   // 休假情况
   private List< EmployeeContractLeaveVO > leaveDetails;

   /**
    * 时间搜索
    */
   private String leaveStartDate;

   private String leaveEndDate;

   private String employStatus;

   /***
    * For app
    */
   // 年份
   private String currYear;
   // 今年年假情况
   private String thisYearLeaveDetails;
   // 去年年假情况
   private String lastYearLeaveDetails;
   // 合同状态
   private String contractStatus;
   private String thisYearTotalHours;
   private String thisYearProbationUsing;
   private String thisYearDelayMonth;
   private String lastYearTotalHours;
   private String lastYearProbationUsing;
   private String lastYearDelayMonth;

   @JsonIgnore
   // 雇员离职状态
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.employStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.work.statuses" );
      contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
      if ( thisYearLeaveDetails != null )
      {
         String tyArray[] = thisYearLeaveDetails.split( "_" );
         setThisYearTotalHours( tyArray[ 1 ] );
         setThisYearProbationUsing( tyArray[ 2 ] );
         setThisYearDelayMonth( tyArray[ 4 ] );
      }
      if ( lastYearLeaveDetails != null )
      {
         String lyArray[] = lastYearLeaveDetails.split( "_" );
         setLastYearTotalHours( lyArray[ 1 ] );
         setLastYearProbationUsing( lyArray[ 2 ] );
         setLastYearDelayMonth( lyArray[ 4 ] );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public String getEmployeeShortName()
   {
      return employeeShortName;
   }

   public void setEmployeeShortName( String employeeShortName )
   {
      this.employeeShortName = employeeShortName;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public List< EmployeeContractLeaveVO > getLeaveDetails()
   {
      return leaveDetails;
   }

   public void setLeaveDetails( List< EmployeeContractLeaveVO > leaveDetails )
   {
      this.leaveDetails = leaveDetails;
   }

   public String getLeaveStartDate()
   {
      return leaveStartDate;
   }

   public void setLeaveStartDate( String leaveStartDate )
   {
      this.leaveStartDate = leaveStartDate;
   }

   public String getLeaveEndDate()
   {
      return leaveEndDate;
   }

   public void setLeaveEndDate( String leaveEndDate )
   {
      this.leaveEndDate = leaveEndDate;
   }

   public String getCurrYear()
   {
      return currYear;
   }

   public void setCurrYear( String currYear )
   {
      this.currYear = currYear;
   }

   public String getThisYearLeaveDetails()
   {
      return thisYearLeaveDetails;
   }

   public void setThisYearLeaveDetails( String thisYearLeaveDetails )
   {
      this.thisYearLeaveDetails = thisYearLeaveDetails;
   }

   public String getLastYearLeaveDetails()
   {
      return lastYearLeaveDetails;
   }

   public void setLastYearLeaveDetails( String lastYearLeaveDetails )
   {
      this.lastYearLeaveDetails = lastYearLeaveDetails;
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public String getThisYearTotalHours()
   {
      return thisYearTotalHours;
   }

   public void setThisYearTotalHours( String thisYearTotalHours )
   {
      this.thisYearTotalHours = thisYearTotalHours;
   }

   public String getThisYearProbationUsing()
   {
      return thisYearProbationUsing;
   }

   public void setThisYearProbationUsing( String thisYearProbationUsing )
   {
      this.thisYearProbationUsing = thisYearProbationUsing;
   }

   public String getThisYearDelayMonth()
   {
      return thisYearDelayMonth;
   }

   public void setThisYearDelayMonth( String thisYearDelayMonth )
   {
      this.thisYearDelayMonth = thisYearDelayMonth;
   }

   public String getLastYearTotalHours()
   {
      return lastYearTotalHours;
   }

   public void setLastYearTotalHours( String lastYearTotalHours )
   {
      this.lastYearTotalHours = lastYearTotalHours;
   }

   public String getLastYearProbationUsing()
   {
      return lastYearProbationUsing;
   }

   public void setLastYearProbationUsing( String lastYearProbationUsing )
   {
      this.lastYearProbationUsing = lastYearProbationUsing;
   }

   public String getLastYearDelayMonth()
   {
      return lastYearDelayMonth;
   }

   public void setLastYearDelayMonth( String lastYearDelayMonth )
   {
      this.lastYearDelayMonth = lastYearDelayMonth;
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public String getShortName()
   {
      if ( StringUtils.isBlank( super.getRemark1() ) )
         return "";
      JSONObject jsonObject = JSONObject.fromObject( super.getRemark1() );

      if ( KANUtil.filterEmpty( "jiancheng" ) != null && jsonObject != null )
      {
         return ( String ) jsonObject.get( "jiancheng" );
      }

      return "";
   }

   public String getDecodeThisYearProbationUsing()
   {
      return decodeField( thisYearProbationUsing, KANUtil.getMappings( this.getLocale(), "flag" ) );
   }

   public String getDecodeLastYearProbationUsing()
   {
      return decodeField( lastYearProbationUsing, KANUtil.getMappings( this.getLocale(), "flag" ) );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( contractStatus, contractStatuses );
   }

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

}
