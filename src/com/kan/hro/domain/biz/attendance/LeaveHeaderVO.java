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
   // ���ID
   private String leaveHeaderId;

   // ��ԱId
   private String employeeId;

   // ����Э��ID
   private String contractId;

   // ��ĿId��
   private String itemId;

   // ʹ������
   private String retrieveId;

   // Ԥ����ٿ�ʼʱ��
   private String estimateStartDate;

   // Ԥ����ٽ���ʱ��
   private String estimateEndDate;

   // ʵ����ٿ�ʼʱ��
   private String actualStartDate;

   // ʵ����ٽ���ʱ��
   private String actualEndDate;

   // Ԥ�Ʒ�����Сʱ��
   private String estimateLegalHours;

   // Ԥ�Ƹ�����Сʱ��
   private String estimateBenefitHours;

   // ʵ�ʷ�����Сʱ��
   private String actualLegalHours;

   // ʵ�ʸ�����Сʱ��
   private String actualBenefitHours;

   // ����
   private String attachment;

   // ����
   private String description;

   // ����״̬
   private String retrieveStatus;

   private String unread;

   // ������Դ
   private String dataFrom;

   /**
    * For Application
    */
   @JsonIgnore
   // ʹ����һ�����
   private String useNextYearHours;
   @JsonIgnore
   // ��Ŀ����
   private String itemNameZH;
   @JsonIgnore
   // ��Ŀ����
   private String itemNameEN;
   @JsonIgnore
   // ����
   private String[] attachmentArray = new String[] {};
   @JsonIgnore
   // ���ڱ�ID
   private String timesheetId;
   @JsonIgnore
   // ��Ա���
   private String employeeNo;
   @JsonIgnore
   // ��Ա�������ģ�
   private String employeeNameZH;
   @JsonIgnore
   // ��Ա����Ӣ�ģ�
   private String employeeNameEN;
   @JsonIgnore
   // ��Ա���֤��
   private String certificateNumber;
   @JsonIgnore
   // �ͻ����
   private String number;
   @JsonIgnore
   // �ͻ��������ģ�
   private String clientNameZH;
   @JsonIgnore
   // �ͻ�����Ӣ�ģ�
   private String clientNameEN;
   @JsonIgnore
   // ��ʾ<a>��ǩ
   private String isLink;
   @JsonIgnore
   // ��ٿ�Ŀ
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����״̬
   private List< MappingVO > retrieveStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���
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

   // ���ܿ�ĿID
   public String getEncodedItemId() throws KANException
   {
      return encodedField( itemId );
   }

   // ���ܹ�ԱID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // ����ContractId
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // ���ܿͻ�ID
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // ���ܿ�ĿID
   public String getDecodeItemId()
   {
      return decodeField( itemId, leaveItems );
   }

   // �������״̬
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
      //         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">�ύ</a>";
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

   // ��ȡ��١�����Сʱ��
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

   // һЩ����������ֶ�
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

   // һЩ����������ֶ�
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
