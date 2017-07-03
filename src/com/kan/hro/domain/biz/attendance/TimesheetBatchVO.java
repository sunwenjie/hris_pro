package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TimesheetBatchVO extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 2086711215340245897L;

   /**
    * For DB
    */
   // 批次Id
   private String batchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 订单Id
   private String orderId;

   // 服务协议Id
   private String contractId;

   // 雇员Id
   private String employeeId;

   // 考勤月份（例如2013/9）
   private String monthly;

   // 考勤周次（例如2013/35）
   private String weekly;

   // 开始时间 - 指Batch运行的时间
   private String startDate;

   // 结束时间 - 指Batch运行的时间
   private String endDate;

   // 描述
   private String description;

   private String importExcelName;

   private String totalOTEmployeeZH;

   private String totalOTEmployeeEN;

   /**
    * For Application
    */
   @JsonIgnore
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 最近2月，含次月
   private List< MappingVO > last2Months = new ArrayList< MappingVO >();
   @JsonIgnore
   // 最近12月，
   private List< MappingVO > last12Months = new ArrayList< MappingVO >();
   @JsonIgnore
   // 帐套个数
   private String countOrderId;
   @JsonIgnore
   // 劳动合同个数
   private String countContractId;
   @JsonIgnore
   // 考勤表个数
   private String countHeaderId;
   @JsonIgnore
   // 工作小时数（总）
   private String totalWortHours;
   @JsonIgnore
   // 请假小时数（总）
   private String totalLeaveHours;
   @JsonIgnore
   // 加班小时数（总）
   private String totalOTHours;
   @JsonIgnore
   // 发生金额（总）
   private String totalBases;
   @JsonIgnore
   // 选中headerId
   private String headerIds;
   @JsonIgnore
   // 雇员/员工 姓名（中文）
   private String employeeNameZH;
   @JsonIgnore
   // 雇员/员工 姓名（英文）
   private String employeeNameEN;

   public String getTotalBases()
   {
      return this.formatNumber( totalBases );
   }

   public void setTotalBases( String totalBases )
   {
      this.totalBases = totalBases;
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.timesheet.header.status" ) );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      if ( entitys != null )
      {
         entitys.add( 0, getEmptyMappingVO() );
      }
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      if ( businessTypes != null )
      {
         businessTypes.add( 0, getEmptyMappingVO() );
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
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.orderId = "0";
      this.contractId = "";
      this.employeeId = "";
      this.monthly = "0";
      this.weekly = "0";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      super.setClientId( "" );
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      // No Use
   }

   // 解译法务实体
   public String getDecodeEntityId()
   {
      return decodeField( entityId, entitys );
   }

   // 解译业务类型
   public String getDecodeBusinessTypeId()
   {
      return decodeField( businessTypeId, businessTypes );
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

   public String getOrderId()
   {
      return KANUtil.filterEmpty( orderId );
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEmployeeId()
   {
      return KANUtil.filterEmpty( employeeId );
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
      return KANUtil.filterEmpty( decodeDatetime( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( batchId );
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

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public String getCountHeaderId()
   {
      return countHeaderId;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
   }

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
   }

   public void setCountHeaderId( String countHeaderId )
   {
      this.countHeaderId = countHeaderId;
   }

   // 获取员工姓名集合（前三） 
   private String getEmployeeNameTop3( final String employeeNameListSource )
   {
      if ( KANUtil.filterEmpty( employeeNameListSource ) != null )
      {
         final String[] employeeNameArray = employeeNameListSource.split( "、" );

         if ( employeeNameArray != null && employeeNameArray.length > 0 )
         {
            String returnStr = "";
            int count = 0;
            for ( String employeeName : employeeNameArray )
            {
               if ( count == 3 )
                  break;
               if ( KANUtil.filterEmpty( returnStr ) == null )
               {
                  returnStr = employeeName;
               }
               else
               {
                  returnStr = returnStr + "、" + employeeName;
               }
               count++;
            }

            return returnStr;
         }
      }

      return "";
   }

   // 获取员工姓名集合
   private String getEmployeeNameList( final String employeeNameListSource )
   {
      if ( KANUtil.filterEmpty( employeeNameListSource ) != null )
      {
         final String[] employeeNameArray = employeeNameListSource.split( "、" );

         if ( employeeNameArray != null && employeeNameArray.length > 10 )
         {
            String returnStr = "";
            int count = 0;
            for ( String employeeName : employeeNameArray )
            {
               if ( count == 10 )
                  break;
               if ( KANUtil.filterEmpty( returnStr ) == null )
               {
                  returnStr = employeeName;
               }
               else
               {
                  returnStr = returnStr + "、" + employeeName;
               }
               count++;
            }

            return returnStr;
         }
      }

      return employeeNameListSource;
   }

   public String getEmployeeNameTop3()
   {
      if ( super.getLocale() != null )
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            return getEmployeeNameTop3( totalOTEmployeeZH );
         else
            return getEmployeeNameTop3( totalOTEmployeeEN );
      else
         return getEmployeeNameTop3( totalOTEmployeeZH );
   }

   public String getEmployeeNameList()
   {
      if ( super.getLocale() != null )
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            return getEmployeeNameList( totalOTEmployeeZH );
         else
            return getEmployeeNameList( totalOTEmployeeEN );
      else
         return getEmployeeNameList( totalOTEmployeeZH );
   }

   public String getHeaderIds()
   {
      return headerIds;
   }

   public void setHeaderIds( String headerIds )
   {
      this.headerIds = headerIds;
   }

   public String getTotalWortHours()
   {
      return totalWortHours;
   }

   public void setTotalWortHours( String totalWortHours )
   {
      this.totalWortHours = totalWortHours;
   }

   public String getTotalLeaveHours()
   {
      return totalLeaveHours;
   }

   public void setTotalLeaveHours( String totalLeaveHours )
   {
      this.totalLeaveHours = totalLeaveHours;
   }

   public String getTotalOTHours()
   {
      return totalOTHours;
   }

   public void setTotalOTHours( String totalOTHours )
   {
      this.totalOTHours = totalOTHours;
   }

   public String getImportExcelName()
   {
      return importExcelName;
   }

   public void setImportExcelName( String importExcelName )
   {
      this.importExcelName = importExcelName;
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

   public String getTotalOTEmployeeZH()
   {
      return totalOTEmployeeZH;
   }

   public void setTotalOTEmployeeZH( String totalOTEmployeeZH )
   {
      this.totalOTEmployeeZH = totalOTEmployeeZH;
   }

   public String getTotalOTEmployeeEN()
   {
      return totalOTEmployeeEN;
   }

   public void setTotalOTEmployeeEN( String totalOTEmployeeEN )
   {
      this.totalOTEmployeeEN = totalOTEmployeeEN;
   }

   public String getTotalOTEmployeeName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getTotalOTEmployeeZH();
         }
         else
         {
            return this.getTotalOTEmployeeEN();
         }
      }
      else
      {
         return this.getTotalOTEmployeeZH();
      }
   }
}
