package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class OTHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 7805602980439653973L;

   /**
    * For DB
    */
   // 加班ID
   private String otHeaderId;

   // 雇员ID
   private String employeeId;

   // 服务协议ID
   private String contractId;

   // 科目ID
   private String itemId;

   // 预计加班开始时间
   private String estimateStartDate;

   // 预计加班结束时间
   private String estimateEndDate;

   // 实际加班开始时间
   private String actualStartDate;

   // 实际加班结束时间
   private String actualEndDate;

   // 预计加班小时数
   private String estimateHours;

   // 实际加班小时数
   private String actualHours;

   // 描述
   private String description;

   private String unread;

   // 数据来源
   private String dataFrom;

   /**
    * For Application
    */
   @JsonIgnore
   // 考勤表ID
   private String timesheetId;
   @JsonIgnore
   // 雇员编号
   private String employeeNo;
   @JsonIgnore
   // 雇员名（中文）
   private String employeeNameZH;
   @JsonIgnore
   // 雇员名（英文）
   private String employeeNameEN;
   @JsonIgnore
   // 雇员身份证号
   private String certificateNumber;
   @JsonIgnore
   // 客户编号
   private String number;
   @JsonIgnore
   // 客户名（中文）
   private String clientNameZH;
   @JsonIgnore
   // 客户名（英文）
   private String clientNameEN;
   @JsonIgnore
   // 加班科目
   private List< MappingVO > otItems = new ArrayList< MappingVO >();

   /**
    * For Application2
    */
   @JsonIgnore
   // 月份
   private String monthly;
   @JsonIgnore
   // 计薪开始日
   private String circleStartDay;
   @JsonIgnore
   // 计薪结束日
   private String circleEndDay;
   @JsonIgnore
   // 该月总加班小时数
   private String totalOTHours;
   @JsonIgnore
   // 加班小时数月上限
   private String otLimitByMonth;
   @JsonIgnore
   // 加班详情，审核页面用到
   private String otDetail;
   @JsonIgnore
   // 特殊加班
   private String specialOT;
   @JsonIgnore
   // 加班科目
   private List< MappingVO > specialOTs = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.overtime.statuses" ) );
      this.otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( request.getLocale().getLanguage() );
      if ( otItems != null )
      {
         otItems.add( 0, getEmptyMappingVO() );
      }

      this.specialOTs = KANUtil.getMappings( request.getLocale(), "business.attendance.ot.specialOTs" );
   }

   @Override
   public void reset() throws KANException
   {
      super.setClientId( "" );
      this.employeeId = "";
      this.contractId = "";
      this.itemId = "0";
      this.estimateStartDate = "";
      this.estimateEndDate = "";
      this.actualStartDate = "";
      this.actualEndDate = "";
      this.estimateHours = "";
      this.actualHours = "";
      this.description = "";
      this.unread = "";
      this.dataFrom = "";
      this.specialOT = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final OTHeaderVO oTHeaderVO = ( OTHeaderVO ) object;
      super.setCorpId( oTHeaderVO.getCorpId() );
      this.employeeId = oTHeaderVO.getEmployeeId();
      this.contractId = oTHeaderVO.getContractId();
      this.itemId = oTHeaderVO.getItemId();
      this.description = oTHeaderVO.getDescription();
      super.setStatus( oTHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      this.timesheetId = oTHeaderVO.getTimesheetId();
      this.unread = oTHeaderVO.getUnread();
      this.specialOT = oTHeaderVO.getSpecialOT();
      super.setRemark2( oTHeaderVO.getRemark2() );
   }

   // 加密科目ID
   public String getEncodedItemId() throws KANException
   {
      return encodedField( itemId );
   }

   // 加密雇员ID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // 加密客户ID
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // 加密派送信息ID
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getDecodeItemId()
   {
      return decodeField( itemId, otItems );
   }

   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   public String getOtHeaderId()
   {
      return otHeaderId;
   }

   public void setOtHeaderId( String otHeaderId )
   {
      this.otHeaderId = otHeaderId;
   }

   public String getEmployeeId()
   {
      return KANUtil.filterEmpty( employeeId );
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getNotFormatEstimateStartDate() throws KANException
   {
      return this.estimateStartDate;
   }

   public String getNotFormatEstimateEndDate() throws KANException
   {
      return this.estimateEndDate;
   }

   public String getEstimateStartDate() throws KANException
   {
      if ( KANUtil.filterEmpty( this.estimateStartDate ) != null )
      {
         return KANUtil.formatDate( this.estimateStartDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public String getSpecialEstimateStartDate()
   {
      return estimateStartDate;
   }

   public void setEstimateStartDate( String estimateStartDate )
   {
      this.estimateStartDate = estimateStartDate;
   }

   public String getEstimateEndDate()
   {
      if ( KANUtil.filterEmpty( this.estimateEndDate ) != null )
      {
         return KANUtil.formatDate( this.estimateEndDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public String getSpecialEstimateEndDate()
   {
      return estimateEndDate;
   }

   public void setEstimateEndDate( String estimateEndDate )
   {
      this.estimateEndDate = estimateEndDate;
   }

   public String getActualStartDate()
   {
      if ( KANUtil.filterEmpty( this.actualStartDate ) != null )
      {
         return KANUtil.formatDate( this.actualStartDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public void setActualStartDate( String actualStartDate )
   {
      this.actualStartDate = actualStartDate;
   }

   public String getActualEndDate()
   {
      if ( KANUtil.filterEmpty( this.actualEndDate ) != null )
      {
         return KANUtil.formatDate( this.actualEndDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public void setActualEndDate( String actualEndDate )
   {
      this.actualEndDate = actualEndDate;
   }

   public String getEstimateHours()
   {
      return KANUtil.filterEmpty( estimateHours );
   }

   public void setEstimateHours( String estimateHours )
   {
      this.estimateHours = estimateHours;
   }

   public String getActualHours()
   {
      return KANUtil.filterEmpty( actualHours );
   }

   public void setActualHours( String actualHours )
   {
      this.actualHours = actualHours;
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
      return encodedField( otHeaderId );
   }

   public List< MappingVO > getOtItems()
   {
      return otItems;
   }

   public void setOtItems( List< MappingVO > otItems )
   {
      this.otItems = otItems;
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

   public String getNumber()
   {
      return number;
   }

   public void setNumber( String number )
   {
      this.number = number;
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

   public final String getEmployeeName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getEmployeeNameZH();
         }
         else
         {
            return this.getEmployeeNameEN();
         }
      }
      else
      {
         return this.getEmployeeNameZH();
      }
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

   public String getTimesheetId()
   {
      return KANUtil.filterEmpty( timesheetId );
   }

   public void setTimesheetId( String timesheetId )
   {
      this.timesheetId = timesheetId;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
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

   public String getTotalOTHours()
   {
      return KANUtil.filterEmpty( formatNumber( totalOTHours ) );
   }

   public void setTotalOTHours( String totalOTHours )
   {
      this.totalOTHours = totalOTHours;
   }

   public String getOtLimitByMonth()
   {
      return KANUtil.filterEmpty( formatNumber( otLimitByMonth ) );
   }

   public void setOtLimitByMonth( String otLimitByMonth )
   {
      this.otLimitByMonth = otLimitByMonth;
   }

   public String getUnread()
   {
      return unread;
   }

   public void setUnread( String unread )
   {
      this.unread = unread;
   }

   // 一些导出显示的特殊字段
   public String getSpecialOTHeaderId()
   {
      return getOtHeaderId() + ( KANUtil.filterEmpty( getActualStartDate() ) == null ? "（预）" : "" );
   }

   public String getSpecialOTHours()
   {
      if ( "1".equals( this.getStatus() ) || "2".equals( this.getStatus() ) || "3".equals( this.getStatus() ) || "4".equals( this.getStatus() ) )
      {
         return getEstimateHours();
      }
      else if ( "5".equals( this.getStatus() ) || "6".equals( this.getStatus() ) || "7".equals( this.getStatus() ) )
      {
         return getActualHours();
      }

      return "";
   }

   public String getOtDetail()
   {
      return otDetail;
   }

   public void setOtDetail( String otDetail )
   {
      this.otDetail = otDetail;
   }

   // 解译加班详情
   public String getDecodeOTDetail()
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( otDetail ) != null )
      {
         final JSONObject jsonObject = JSONObject.fromObject( otDetail );
         for ( Object key : jsonObject.keySet() )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( key.toString(), otItems ) + "：" + jsonObject.get( key ).toString() + "小时";
            }
            else
            {
               returnStr = returnStr + "；" + decodeField( key.toString(), otItems ) + "：" + jsonObject.get( key ).toString() + "小时";
            }
         }
      }

      return returnStr;
   }

   public String getDataFrom()
   {
      return KANUtil.filterEmpty( dataFrom );
   }

   public void setDataFrom( String dataFrom )
   {
      this.dataFrom = dataFrom;
   }

   public String getSpecialOT()
   {
      return specialOT;
   }

   public void setSpecialOT( String specialOT )
   {
      this.specialOT = specialOT;
   }

   // 解译加班详情
   public String getDecodeSpecialOT()
   {

      if ( KANUtil.filterEmpty( specialOT, "0" ) == null )
      {
         specialOT = "2";
      }
      return decodeField( specialOT, specialOTs );
   }

   public List< MappingVO > getSpecialOTs()
   {
      return specialOTs;
   }

   public void setSpecialOTs( List< MappingVO > specialOTs )
   {
      this.specialOTs = specialOTs;
   }

}
