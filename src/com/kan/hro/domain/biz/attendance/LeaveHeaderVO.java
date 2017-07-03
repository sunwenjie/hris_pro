package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class LeaveHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 5567996109440668464L;

   /**
    * For DB
    */
   // 请假ID
   private String leaveHeaderId;

   // 雇员Id
   private String employeeId;

   // 服务协议ID
   private String contractId;

   // 科目Id，
   private String itemId;

   // 使用销假
   private String retrieveId;

   // 预计请假开始时间
   private String estimateStartDate;

   // 预计请假结束时间
   private String estimateEndDate;

   // 实际请假开始时间
   private String actualStartDate;

   // 实际请假结束时间
   private String actualEndDate;

   // 预计法定假小时数
   private String estimateLegalHours;

   // 预计福利假小时数
   private String estimateBenefitHours;

   // 实际法定假小时数
   private String actualLegalHours;

   // 实际福利假小时数
   private String actualBenefitHours;

   // 附件
   private String attachment;

   // 描述
   private String description;

   // 销假状态
   private String retrieveStatus;

   private String unread;

   // 数据来源
   private String dataFrom;

   /**
    * For Application
    */
   @JsonIgnore
   // 使用下一年年假
   private String useNextYearHours;
   @JsonIgnore
   // 科目名称
   private String itemNameZH;
   @JsonIgnore
   // 科目名称
   private String itemNameEN;
   @JsonIgnore
   // 附件
   private String[] attachmentArray = new String[] {};
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
   // 显示<a>标签
   private String isLink;
   @JsonIgnore
   // 请假科目
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();
   @JsonIgnore
   // 销假状态
   private List< MappingVO > retrieveStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // 年份
   private String year;

   private List< String > itemIds = new ArrayList< String >();
   @JsonIgnore
   private String auditorZH;
   @JsonIgnore
   private String auditorEN;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.leave.statuses" ) );
      this.leaveItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), getCorpId() );
      leaveItems.add( KANConstants.getKANAccountConstants( super.getAccountId() ).getItemByItemId( "25", request.getLocale().getLanguage() ) );
      if ( this.leaveItems != null )
      {
         leaveItems.add( 0, getEmptyMappingVO() );
      }
      this.retrieveStatuses = KANUtil.getMappings( request.getLocale(), "business.attendance.leave.retrieve.statuses" );
   }

   @Override
   public void reset() throws KANException
   {
      super.setClientId( "" );
      this.employeeId = "";
      this.contractId = "";
      this.itemId = "0";
      this.retrieveId = "0";
      this.estimateStartDate = "";
      this.estimateEndDate = "";
      this.actualStartDate = "";
      this.actualEndDate = "";
      this.estimateLegalHours = "";
      this.estimateBenefitHours = "";
      this.actualLegalHours = "";
      this.actualBenefitHours = "";
      this.description = "";
      this.retrieveStatus = "0";
      this.unread = "";
      this.dataFrom = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) object;
      super.setCorpId( leaveHeaderVO.getCorpId() );
      this.employeeId = leaveHeaderVO.getEmployeeId();
      this.contractId = leaveHeaderVO.getContractId();
      this.itemId = leaveHeaderVO.getItemId();
      this.retrieveId = leaveHeaderVO.getRetrieveId();
      this.description = leaveHeaderVO.getDescription();
      this.retrieveStatus = leaveHeaderVO.getRetrieveStatus();
      this.attachment = leaveHeaderVO.getAttachment();
      this.attachmentArray = leaveHeaderVO.getAttachmentArray();
      super.setModifyDate( new Date() );
      super.setStatus( leaveHeaderVO.getStatus() );
      this.timesheetId = leaveHeaderVO.getTimesheetId();
      this.unread = leaveHeaderVO.getUnread();
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

   // 加密ContractId
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // 加密客户ID
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // 解密科目ID
   public String getDecodeItemId()
   {
      return decodeField( itemId, leaveItems );
   }

   // 解密请假状态
   public String getDecodeStatus()
   {
      if ( this.retrieveStatus != null && !this.retrieveStatus.equals( "1" ) )
         return getDecodeRetrieveStatus();

      return decodeField( super.getStatus(), super.getStatuses() );
   }

   public String getLeaveHeaderId()
   {
      return KANUtil.filterEmpty( leaveHeaderId );
   }

   public void setLeaveHeaderId( String leaveHeaderId )
   {
      this.leaveHeaderId = leaveHeaderId;
   }

   public String getEmployeeId()
   {
      return KANUtil.filterEmpty( employeeId );
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getRetrieveId()
   {
      return KANUtil.filterEmpty( retrieveId );
   }

   public void setRetrieveId( String retrieveId )
   {
      this.retrieveId = retrieveId;
   }

   public String getNotFormatEstimateStartDate()
   {
      return this.estimateStartDate;
   }

   public String getNotFormatEstimateEndDate()
   {
      return this.estimateEndDate;
   }

   public String getEstimateStartDate()
   {
      try
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
      catch ( Exception e )
      {
         return "Error Date Format";
      }
   }

   public void setEstimateStartDate( String estimateStartDate )
   {
      this.estimateStartDate = estimateStartDate;
   }

   public String getEstimateEndDate()
   {
      try
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
      catch ( Exception e )
      {
         return "Error Date Format";
      }
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

   public String getEstimateLegalHours()
   {
      return KANUtil.filterEmpty( estimateLegalHours ) == null ? "0" : estimateLegalHours;
   }

   public void setEstimateLegalHours( String estimateLegalHours )
   {
      this.estimateLegalHours = estimateLegalHours;
   }

   public String getEstimateBenefitHours()
   {
      return KANUtil.filterEmpty( estimateBenefitHours ) == null ? "0" : estimateBenefitHours;
   }

   public void setEstimateBenefitHours( String estimateBenefitHours )
   {
      this.estimateBenefitHours = estimateBenefitHours;
   }

   public String getActualLegalHours()
   {
      return KANUtil.filterEmpty( actualLegalHours ) == null ? "0.0" : String.valueOf( Double.valueOf( actualLegalHours ) );
   }

   public void setActualLegalHours( String actualLegalHours )
   {
      this.actualLegalHours = actualLegalHours;
   }

   public String getActualBenefitHours()
   {
      return KANUtil.filterEmpty( actualBenefitHours ) == null ? "0.0" : String.valueOf( Double.valueOf( actualBenefitHours ) );
   }

   public void setActualBenefitHours( String actualBenefitHours )
   {
      this.actualBenefitHours = actualBenefitHours;
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
      return encodedField( leaveHeaderId );
   }

   public List< MappingVO > getLeaveItems()
   {
      return leaveItems;
   }

   public void setLeaveItems( List< MappingVO > leaveItems )
   {
      this.leaveItems = leaveItems;
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
      return KANUtil.filterEmpty( number );
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

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;

   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String getRetrieveStatus()
   {
      return retrieveStatus;
   }

   public void setRetrieveStatus( String retrieveStatus )
   {
      this.retrieveStatus = retrieveStatus;
   }

   public String getIsLink() throws KANException
   {
      //      if ( "1".equals( super.getStatus() ) )
      //      {
      //         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">提交</a>";
      //      }
      //      else 
      if ( "2".equals( super.getStatus() ) || ( "3".equals( super.getStatus() ) && "2".equals( retrieveStatus ) ) )
      {
         isLink = "&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('" + this.getWorkflowId() + "'); />";
      }

      return isLink;
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   public String getTimesheetId()
   {
      return KANUtil.filterEmpty( timesheetId );
   }

   public void setTimesheetId( String timesheetId )
   {
      this.timesheetId = timesheetId;
   }

   // 获取请假、销假小时数
   public String getUseHours()
   {
      double useHours = 0.0;

      if ( KANUtil.filterEmpty( actualStartDate ) == null && KANUtil.filterEmpty( actualEndDate ) == null )
      {
         if ( KANUtil.filterEmpty( getEstimateLegalHours() ) != null )
         {
            useHours = useHours + Double.valueOf( getEstimateLegalHours() );
         }
         if ( KANUtil.filterEmpty( getEstimateBenefitHours() ) != null )
         {
            useHours = useHours + Double.valueOf( getEstimateBenefitHours() );
         }
      }
      else if ( KANUtil.filterEmpty( actualStartDate ) != null && KANUtil.filterEmpty( actualEndDate ) != null )
      {
         if ( KANUtil.filterEmpty( getActualLegalHours() ) != null )
         {
            useHours = useHours + Double.valueOf( getActualLegalHours() );
         }
         if ( KANUtil.filterEmpty( getActualBenefitHours() ) != null )
         {
            useHours = useHours + Double.valueOf( getActualBenefitHours() );
         }
      }

      return String.valueOf( useHours );
   }

   public String getEstimateHours()
   {
      double useHours = 0.0;
      if ( KANUtil.filterEmpty( getEstimateLegalHours() ) != null )
      {
         useHours = useHours + Double.valueOf( getEstimateLegalHours() );
      }
      if ( KANUtil.filterEmpty( getEstimateBenefitHours() ) != null )
      {
         useHours = useHours + Double.valueOf( getEstimateBenefitHours() );
      }
      return String.valueOf( useHours );
   }

   public String getActualHours()
   {
      if ( KANUtil.filterEmpty( getRemark5() ) != null )
         return getRemark5();

      double useHours = 0.0;
      if ( KANUtil.filterEmpty( getActualLegalHours() ) != null )
      {
         useHours = useHours + Double.valueOf( getActualLegalHours() );
      }
      if ( KANUtil.filterEmpty( getActualBenefitHours() ) != null )
      {
         useHours = useHours + Double.valueOf( getActualBenefitHours() );
      }
      return String.valueOf( useHours );
   }

   public List< MappingVO > getRetrieveStatuses()
   {
      return retrieveStatuses;
   }

   public void setRetrieveStatuses( List< MappingVO > retrieveStatuses )
   {
      this.retrieveStatuses = retrieveStatuses;
   }

   public String getDecodeRetrieveStatus()
   {
      return decodeField( retrieveStatus, retrieveStatuses );
   }

   public String getUnread()
   {
      return unread;
   }

   public void setUnread( String unread )
   {
      this.unread = unread;
   }

   // 一些导出特殊的字段
   public String getSpecialStartDate()
   {
      if ( KANUtil.filterEmpty( getActualStartDate() ) == null )
      {
         return getEstimateStartDate();
      }
      else
      {
         return getActualStartDate();
      }
   }

   // 一些导出特殊的字段
   public String getSpecialEndDate()
   {
      if ( KANUtil.filterEmpty( getActualEndDate() ) == null )
      {
         return getEstimateEndDate();
      }
      else
      {
         return getActualEndDate();
      }
   }

   public String getDataFrom()
   {
      return KANUtil.filterEmpty( dataFrom );
   }

   public void setDataFrom( String dataFrom )
   {
      this.dataFrom = dataFrom;
   }

   public String getYear()
   {
      return year;
   }

   public void setYear( String year )
   {
      this.year = year;
   }

   public String getItemNameZH()
   {
      return itemNameZH;
   }

   public void setItemNameZH( String itemNameZH )
   {
      this.itemNameZH = itemNameZH;
   }

   public String getItemNameEN()
   {
      return itemNameEN;
   }

   public void setItemNameEN( String itemNameEN )
   {
      this.itemNameEN = itemNameEN;
   }

   public List< String > getItemIds()
   {
      return itemIds;
   }

   public void setItemIds( List< String > itemIds )
   {
      this.itemIds = itemIds;
   }

   public String getAuditorZH()
   {
      return auditorZH;
   }

   public void setAuditorZH( String auditorZH )
   {
      this.auditorZH = auditorZH;
   }

   public String getAuditorEN()
   {
      return auditorEN;
   }

   public void setAuditorEN( String auditorEN )
   {
      this.auditorEN = auditorEN;
   }

   public String getUseNextYearHours()
   {
      return useNextYearHours;
   }

   public void setUseNextYearHours( String useNextYearHours )
   {
      this.useNextYearHours = useNextYearHours;
   }

}
