package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TimesheetAllowanceHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = 4381438623262298501L;

   /**
    * For DB
    */
   // 考勤主表Id
   private String headerId;

   // 雇员Id
   private String employeeId;

   // 订单Id
   private String orderId;

   //批次Id
   private String batchId;

   // 月份
   private String monthly;

   // 周次
   private String weekly;

   // 开始日期
   private String startDate;

   // 结束日期
   private String endDate;

   // 工作小时数（考勤周期合计）
   private String totalWorkHours;

   // 工作天数（考勤周期合计）
   private String totalWorkDays;

   // 全勤小时数（考勤周期合计）
   private String totalFullHours;

   // 全勤天数（考勤周期合计）
   private String totalFullDays;

   // 需要审批 
   private String needAudit;

   // 正常出勤
   private String isNormal;

   // 附件
   private String attachment;

   // 可填写退回原因
   private String description;

   /**
    * For Application
    */
   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 服务协议Id
   private String contractId;

   // 雇员编号
   private String employeeNo;

   // 证件号码
   private String certificateNumber;

   // 雇员姓名（中文）
   private String employeeNameZH;

   // 雇员姓名（英文）
   private String employeeNameEN;

   // 客户姓名（中文）
   private String clientNameZH;

   // 客户姓名（英文）
   private String clientNameEN;

   // 订单 - 计薪周期（开始）
   private String circleStartDay;

   // 订单 - 计薪周期（结束）
   private String circleEndDay;

   // 法务实体
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypies = new ArrayList< MappingVO >();

   // 最近2月，含次月
   private List< MappingVO > last2Months = new ArrayList< MappingVO >();

   // 最近12月，
   private List< MappingVO > last12Months = new ArrayList< MappingVO >();

   // 额外Monthly
   private String additionalMonthly;

   // Detail日期类型 数组形式
   private String[] dayTypeArray = new String[] {};

   // Detail工作小时数 数组形式
   private String[] workHoursArray = new String[] {};

   // Allowance基数 数组形式
   private String[] baseArray = new String[] {};

   // 截至月份 - 数据提取使用
   private String monthlyLimit;
   
   // 基数
   private String base;
   
   // 科目编号
   private String itemNo;

   // 科目名称（中文）
   private String itemNameZH;

   // 科目名称（英文）
   private String itemNameEN;
   
   private int allowanceId; 

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );

      if ( entities != null )
      {
         entities.add( 0, getEmptyMappingVO() );
      }

      this.businessTypies = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );

      if ( businessTypies != null )
      {
         businessTypies.add( 0, getEmptyMappingVO() );
      }

      this.last2Months = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( request.getLocale().getLanguage() );
      if ( last2Months != null )
      {
         last2Months.add( 0, getEmptyMappingVO() );
      }
      this.last12Months = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( request.getLocale().getLanguage() );
      if ( last12Months != null )
      {
         last12Months.add( 0, getEmptyMappingVO() );
      }
      // 考勤状态 - 重设父类中的Status
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.timesheet.header.status" ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.contractId = "";
      this.orderId = "";
      this.batchId = "";
      this.monthly = "0";
      this.weekly = "0";
      this.startDate = "";
      this.endDate = "";
      this.totalWorkHours = "";
      this.totalWorkDays = "";
      this.needAudit = "0";
      this.isNormal = "0";
      this.attachment = "";
      this.description = "";
      super.setStatus( "0" );
      this.base = "";
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final TimesheetAllowanceHeaderVO timeSheetHeaderVO = ( TimesheetAllowanceHeaderVO ) object;
      this.employeeId = timeSheetHeaderVO.getEmployeeId();
      this.contractId = timeSheetHeaderVO.getContractId();
      super.setClientId( timeSheetHeaderVO.getClientId() );
      super.setCorpId( timeSheetHeaderVO.getCorpId() );
      this.monthly = timeSheetHeaderVO.getMonthly();
      this.weekly = timeSheetHeaderVO.getWeekly();
      this.startDate = timeSheetHeaderVO.getStartDate();
      this.endDate = timeSheetHeaderVO.getEndDate();
      this.totalWorkHours = timeSheetHeaderVO.getTotalWorkHours();
      this.totalWorkDays = timeSheetHeaderVO.getTotalWorkDays();
      this.needAudit = timeSheetHeaderVO.getNeedAudit();
      this.isNormal = timeSheetHeaderVO.getIsNormal();
      this.attachment = timeSheetHeaderVO.getAttachment();
      this.description = timeSheetHeaderVO.getDescription();
      super.setStatus( timeSheetHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   // 加密服务批次ID
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // 加密服务雇员ID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // 加密服务订单ID
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // 加密服务协议ID
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // 加密服务客户ID
   public String getEncodedCorpId() throws KANException
   {
      return encodedField( getCorpId() );
   }

   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getWeekly()
   {
      return weekly;
   }

   public void setWeekly( String weekly )
   {
      this.weekly = weekly;
   }

   public String getStartDate()
   {
      return decodeDate( startDate );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return decodeDate( endDate );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getTotalWorkHours()
   {
      return totalWorkHours;
   }

   public void setTotalWorkHours( String totalWorkHours )
   {
      this.totalWorkHours = totalWorkHours;
   }

   public String getTotalWorkDays()
   {
      return totalWorkDays;
   }

   public void setTotalWorkDays( String totalWorkDays )
   {
      this.totalWorkDays = totalWorkDays;
   }

   public String getNeedAudit()
   {
      return needAudit;
   }

   public void setNeedAudit( String needAudit )
   {
      this.needAudit = needAudit;
   }

   public String getIsNormal()
   {
      return isNormal;
   }

   public void setIsNormal( String isNormal )
   {
      this.isNormal = isNormal;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getTotalFullHours()
   {
      return totalFullHours;
   }

   public void setTotalFullHours( String totalFullHours )
   {
      this.totalFullHours = totalFullHours;
   }

   public String getTotalFullDays()
   {
      return totalFullDays;
   }

   public void setTotalFullDays( String totalFullDays )
   {
      this.totalFullDays = totalFullDays;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
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

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public String getClientNameEN()
   {
      return clientNameEN;
   }

   public void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypies()
   {
      return businessTypies;
   }

   public void setBusinessTypies( List< MappingVO > businessTypies )
   {
      this.businessTypies = businessTypies;
   }

   public String getCircleStartDay()
   {
      return circleStartDay;
   }

   public void setCircleStartDay( String circleStartDay )
   {
      this.circleStartDay = circleStartDay;
   }

   public String getCircleEndDay()
   {
      return circleEndDay;
   }

   public void setCircleEndDay( String circleEndDay )
   {
      this.circleEndDay = circleEndDay;
   }

   public String getAdditionalMonthly()
   {
      return additionalMonthly;
   }

   public void setAdditionalMonthly( String additionalMonthly )
   {
      this.additionalMonthly = additionalMonthly;
   }

   public String[] getWorkHoursArray()
   {
      return workHoursArray;
   }

   public void setWorkHoursArray( String[] workHoursArray )
   {
      this.workHoursArray = workHoursArray;
   }

   public String[] getBaseArray()
   {
      return baseArray;
   }

   public void setBaseArray( String[] baseArray )
   {
      this.baseArray = baseArray;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getMonthlyLimit()
   {
      return monthlyLimit;
   }

   public void setMonthlyLimit( String monthlyLimit )
   {
      this.monthlyLimit = monthlyLimit;
   }

   public final String getClientName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getClientNameZH();
         }
         else
         {
            return this.getClientNameEN();
         }
      }
      else
      {
         return this.getClientNameZH();
      }
   }

   public String getEmployeeName()
   {
      return null;
   }

   public List< MappingVO > getLast2Months()
   {
      return last2Months;
   }

   public void setLast2Months( List< MappingVO > last2Months )
   {
      this.last2Months = last2Months;
   }

   public List< MappingVO > getLast12Months()
   {
      return last12Months;
   }

   public void setLast12Months( List< MappingVO > last12Months )
   {
      this.last12Months = last12Months;
   }

   public String[] getDayTypeArray()
   {
      return dayTypeArray;
   }

   public void setDayTypeArray( String[] dayTypeArray )
   {
      this.dayTypeArray = dayTypeArray;
   }

	public String getBase() {
	 
		return   formatNumber( base );
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public String getItemNo() {
		return itemNo;
	}
	
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	public String getItemNameZH() {
		return itemNameZH;
	}
	
	public void setItemNameZH(String itemNameZH) {
		this.itemNameZH = itemNameZH;
	}
	
	public String getItemNameEN() {
		return itemNameEN;
	}
	
	public void setItemNameEN(String itemNameEN) {
		this.itemNameEN = itemNameEN;
	}

	public int getAllowanceId() {
		return allowanceId;
	}

	public void setAllowanceId(int allowanceId) {
		this.allowanceId = allowanceId;
	}
	
	public String getAllowanceIdEncodedId() throws KANException
	{
	      return KANUtil.encodeStringWithCryptogram( String.valueOf(allowanceId) );
	}
	
}
