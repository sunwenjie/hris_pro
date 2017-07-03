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

public class LeaveImportHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1L;

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

   // ����Id��
   private String batchId;

   /**
    * For Application
    */
   // ����
   private String[] attachmentArray = new String[] {};

   // ���ڱ�ID
   private String timesheetId;

   // ��Ա���
   private String employeeNo;

   // ��Ա�������ģ�
   private String employeeNameZH;

   // ��Ա����Ӣ�ģ�
   private String employeeNameEN;

   // ��Ա���֤��
   private String certificateNumber;

   // �ͻ����
   private String number;

   // �ͻ��������ģ�
   private String clientNameZH;

   // �ͻ�����Ӣ�ģ�
   private String clientNameEN;

   // ��ʾ<a>��ǩ
   private String isLink;

   // ��ٿ�Ŀ
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();

   private String leaveHeaderIdtemp;


   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.import.batch.statuses" ) );
      this.leaveItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), getCorpId() );
      leaveItems.add( KANConstants.getKANAccountConstants( super.getAccountId() ).getItemByItemId( "25", request.getLocale().getLanguage() ) );
      if ( this.leaveItems != null )
      {
         leaveItems.add( 0, getEmptyMappingVO() );
      }
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
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final LeaveImportHeaderVO leaveHeaderVO = ( LeaveImportHeaderVO ) object;
      super.setClientId( leaveHeaderVO.getClientId() );
      this.employeeId = leaveHeaderVO.getEmployeeId();
      this.contractId = leaveHeaderVO.getContractId();
      this.itemId = leaveHeaderVO.getItemId();
      this.retrieveId = leaveHeaderVO.getRetrieveId();
      this.estimateStartDate = leaveHeaderVO.getEstimateStartDate();
      this.estimateEndDate = leaveHeaderVO.getEstimateEndDate();
      this.actualStartDate = leaveHeaderVO.getActualStartDate();
      this.actualEndDate = leaveHeaderVO.getActualEndDate();
      this.estimateLegalHours = leaveHeaderVO.getEstimateLegalHours();
      this.estimateBenefitHours = leaveHeaderVO.getEstimateBenefitHours();
      this.actualLegalHours = leaveHeaderVO.getActualLegalHours();
      this.actualBenefitHours = leaveHeaderVO.getActualBenefitHours();
      this.description = leaveHeaderVO.getDescription();
      this.retrieveStatus = leaveHeaderVO.getRetrieveStatus();
      this.attachment = leaveHeaderVO.getAttachment();
      this.attachmentArray = leaveHeaderVO.getAttachmentArray();
      super.setModifyDate( new Date() );
      super.setStatus( leaveHeaderVO.getStatus() );
      this.timesheetId = leaveHeaderVO.getTimesheetId();
   }

   // ���ܷ�������ID
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
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
      return KANUtil.filterEmpty( actualLegalHours );
   }

   public void setActualLegalHours( String actualLegalHours )
   {
      this.actualLegalHours = actualLegalHours;
   }

   public String getActualBenefitHours()
   {
      return KANUtil.filterEmpty( actualBenefitHours );
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
      if ( "1".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">�ύ</a>";
      }
      else if ( "2".equals( super.getStatus() ) )
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

   public String getUseHours()
   {
      double useHours = 0.0;
      if ( getEstimateLegalHours() != null )
      {
         useHours = useHours + Double.valueOf( getEstimateLegalHours() );
      }
      if ( getEstimateBenefitHours() != null )
      {
         useHours = useHours + Double.valueOf( getEstimateBenefitHours() );
      }

      return String.valueOf( useHours );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getLeaveHeaderIdtemp()
   {
      return leaveHeaderIdtemp;
   }

   public void setLeaveHeaderIdtemp( String leaveHeaderIdtemp )
   {
      this.leaveHeaderIdtemp = leaveHeaderIdtemp;
   }

}
