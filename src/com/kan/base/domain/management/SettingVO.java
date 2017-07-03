package com.kan.base.domain.management;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class SettingVO extends BaseVO
{

   /**  
   * SerialVersionUID
   */

   private static final long serialVersionUID = 4448513902972052698L;

   //  设置ID
   private String settingId;

   // 用户ID
   private String userId;

   // 基本信息
   private String baseInfo;

   //基本信息序号
   private String baseInfoRank;

   // 通知
   private String message;

   // 通知序号
   private String messageRank;

   // 数据概览
   private String dataView;

   // 数据概览序号
   private String dataViewRank;

   // 商务合同
   private String clientContract;

   // 商务合同序号
   private String clientContractRank;

   // 订单
   private String orders;

   // 订单序号
   private String ordersRank;

   // 派送信息
   private String contractService;

   // 派送信息序号
   private String contractServiceRank;

   // 考勤
   private String attendance;

   // 考勤序号
   private String attendanceRank;

   // 社保
   private String sb;

   // 社保序号
   private String sbRank;

   // 商报
   private String cb;

   // 商报序号
   private String cbRank;

   // 结算
   private String settlement;

   // 结算序号
   private String settlementRank;

   // 薪酬
   private String payment;

   // 薪酬序号
   private String paymentRank;

   // 收款
   private String income;

   // 收款序号
   private String incomeRank;

   // 人员变更
   private String employeeChange;
   
   private String employeeChangeRank;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( settingId );
   }

   @Override
   public void reset() throws KANException
   {
      this.baseInfo = "";
      this.baseInfoRank = "";
      this.cb = "";
      this.cbRank = "";
      this.clientContract = "";
      this.clientContractRank = "";
      this.contractService = "";
      this.contractServiceRank = "";
      this.dataView = "";
      this.dataViewRank = "";
      this.income = "";
      this.incomeRank = "";
      this.message = "";
      this.messageRank = "";
      this.orders = "";
      this.ordersRank = "";
      this.payment = "";
      this.paymentRank = "";
      this.sb = "";
      this.sbRank = "";
      this.settlement = "";
      this.settlementRank = "";
      this.attendance = "";
      this.attendanceRank = "";
      this.employeeChange = "";
   }

   public void resetInit() throws KANException
   {
      // 基本信息  通知 数据概览  默认禁用
      this.baseInfo = "2";
      this.message = "2";
      this.dataView = "2";
      this.clientContract = "1";
      this.orders = "1";
      this.contractService = "1";
      this.attendance = "1";
      this.sb = "1";
      this.cb = "1";
      this.settlement = "1";
      this.payment = "1";
      this.income = "1";
      this.employeeChange = "1";
      this.baseInfoRank = "1";
      this.messageRank = "2";
      this.dataViewRank = "3";
      this.clientContractRank = "4";
      this.ordersRank = "5";
      this.contractServiceRank = "6";
      this.attendanceRank = "7";
      this.sbRank = "8";
      this.cbRank = "9";
      this.settlementRank = "10";
      this.paymentRank = "11";
      this.incomeRank = "12";
      this.employeeChangeRank = "13";
      super.setModifyDate( new Date() );
      super.setStatus( "1" );
   }

   public void resetInHouseInit() throws KANException
   {
      // 基本信息  通知 数据概览  默认禁用
      this.baseInfo = "2";
      this.message = "2";
      this.dataView = "2";
      // 无商务合同
      this.clientContract = "2";
      // 无订单
      this.orders = "2";
      this.contractService = "1";
      this.attendance = "1";
      this.sb = "1";
      this.cb = "1";
      // 无结算
      this.settlement = "2";
      this.payment = "1";
      this.income = "1";
      this.employeeChange = "1";
      this.baseInfoRank = "1";
      this.messageRank = "2";
      this.dataViewRank = "3";
      this.clientContractRank = "0";
      this.ordersRank = "0";
      this.contractServiceRank = "4";
      this.attendanceRank = "5";
      this.sbRank = "6";
      this.cbRank = "7";
      this.settlementRank = "0";
      this.paymentRank = "8";
      this.incomeRank = "9";
      this.employeeChangeRank = "10";
      super.setModifyDate( new Date() );
      super.setStatus( "1" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SettingVO settingVO = ( SettingVO ) object;
      this.baseInfo = settingVO.getBaseInfo();
      this.baseInfoRank = settingVO.getBaseInfoRank();
      this.message = settingVO.getMessage();
      this.messageRank = settingVO.getMessageRank();
      this.dataView = settingVO.getDataView();
      this.dataViewRank = settingVO.getDataViewRank();
      this.clientContract = settingVO.getClientContract();
      this.clientContractRank = settingVO.getClientContractRank();
      this.orders = settingVO.getOrders();
      this.ordersRank = settingVO.getOrdersRank();
      this.contractService = settingVO.getContractService();
      this.contractServiceRank = settingVO.getContractServiceRank();
      this.attendance = settingVO.getAttendance();
      this.attendanceRank = settingVO.getAttendanceRank();
      this.sb = settingVO.getSb();
      this.sbRank = settingVO.getSbRank();
      this.cb = settingVO.getCb();
      this.cbRank = settingVO.getCbRank();
      this.settlement = settingVO.getSettlement();
      this.settlementRank = settingVO.getSettlementRank();
      this.payment = settingVO.getPayment();
      this.paymentRank = settingVO.getPaymentRank();
      this.income = settingVO.getIncome();
      this.incomeRank = settingVO.getIncomeRank();
      this.employeeChange = settingVO.getEmployeeChange();
      this.employeeChangeRank = settingVO.getEmployeeChangeRank();
      super.setModifyDate( new Date() );
   }

   public String getSettingId()
   {
      return settingId;
   }

   public void setSettingId( String settingId )
   {
      this.settingId = settingId;
   }

   public String getUserId()
   {
      return userId;
   }

   public void setUserId( String userId )
   {
      this.userId = userId;
   }

   public String getBaseInfo()
   {
      return baseInfo;
   }

   public void setBaseInfo( String baseInfo )
   {
      this.baseInfo = baseInfo;
   }

   public String getBaseInfoRank()
   {
      return baseInfoRank;
   }

   public void setBaseInfoRank( String baseInfoRank )
   {
      this.baseInfoRank = baseInfoRank;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage( String message )
   {
      this.message = message;
   }

   public String getMessageRank()
   {
      return messageRank;
   }

   public void setMessageRank( String messageRank )
   {
      this.messageRank = messageRank;
   }

   public String getDataView()
   {
      return dataView;
   }

   public void setDataView( String dataView )
   {
      this.dataView = dataView;
   }

   public String getDataViewRank()
   {
      return dataViewRank;
   }

   public void setDataViewRank( String dataViewRank )
   {
      this.dataViewRank = dataViewRank;
   }

   public String getClientContract()
   {
      return clientContract;
   }

   public void setClientContract( String clientContract )
   {
      this.clientContract = clientContract;
   }

   public String getClientContractRank()
   {
      return clientContractRank;
   }

   public void setClientContractRank( String clientContractRank )
   {
      this.clientContractRank = clientContractRank;
   }

   public String getOrders()
   {
      return orders;
   }

   public void setOrders( String orders )
   {
      this.orders = orders;
   }

   public String getOrdersRank()
   {
      return ordersRank;
   }

   public void setOrdersRank( String ordersRank )
   {
      this.ordersRank = ordersRank;
   }

   public String getContractService()
   {
      return contractService;
   }

   public void setContractService( String contractService )
   {
      this.contractService = contractService;
   }

   public String getContractServiceRank()
   {
      return contractServiceRank;
   }

   public void setContractServiceRank( String contractServiceRank )
   {
      this.contractServiceRank = contractServiceRank;
   }

   public String getAttendance()
   {
      return attendance;
   }

   public void setAttendance( String attendance )
   {
      this.attendance = attendance;
   }

   public String getAttendanceRank()
   {
      return attendanceRank;
   }

   public void setAttendanceRank( String attendanceRank )
   {
      this.attendanceRank = attendanceRank;
   }

   public String getSb()
   {
      return sb;
   }

   public void setSb( String sb )
   {
      this.sb = sb;
   }

   public String getSbRank()
   {
      return sbRank;
   }

   public void setSbRank( String sbRank )
   {
      this.sbRank = sbRank;
   }

   public String getCb()
   {
      return cb;
   }

   public void setCb( String cb )
   {
      this.cb = cb;
   }

   public String getCbRank()
   {
      return cbRank;
   }

   public void setCbRank( String cbRank )
   {
      this.cbRank = cbRank;
   }

   public String getSettlement()
   {
      return settlement;
   }

   public void setSettlement( String settlement )
   {
      this.settlement = settlement;
   }

   public String getSettlementRank()
   {
      return settlementRank;
   }

   public void setSettlementRank( String settlementRank )
   {
      this.settlementRank = settlementRank;
   }

   public String getPayment()
   {
      return payment;
   }

   public void setPayment( String payment )
   {
      this.payment = payment;
   }

   public String getPaymentRank()
   {
      return paymentRank;
   }

   public void setPaymentRank( String paymentRank )
   {
      this.paymentRank = paymentRank;
   }

   public String getIncome()
   {
      return income;
   }

   public void setIncome( String income )
   {
      this.income = income;
   }

   public String getIncomeRank()
   {
      return incomeRank;
   }

   public void setIncomeRank( String incomeRank )
   {
      this.incomeRank = incomeRank;
   }

   public String getEmployeeChange()
   {
      return employeeChange;
   }

   public void setEmployeeChange( String employeeChange )
   {
      this.employeeChange = employeeChange;
   }

   public String getEmployeeChangeRank()
   {
      return employeeChangeRank;
   }

   public void setEmployeeChangeRank( String employeeChangeRank )
   {
      this.employeeChangeRank = employeeChangeRank;
   }

}
