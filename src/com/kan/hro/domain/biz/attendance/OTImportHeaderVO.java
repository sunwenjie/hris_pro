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

public class OTImportHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = -1753989630267565012L;

   /**
    * For DB
    */
   // �Ӱ�ID
   private String otHeaderId;

   // ��ԱID
   private String employeeId;

   // ����Э��ID
   private String contractId;

   // ��ĿID
   private String itemId;

   // Ԥ�ƼӰ࿪ʼʱ��
   private String estimateStartDate;

   // Ԥ�ƼӰ����ʱ��
   private String estimateEndDate;

   // ʵ�ʼӰ࿪ʼʱ��
   private String actualStartDate;

   // ʵ�ʼӰ����ʱ��
   private String actualEndDate;

   // Ԥ�ƼӰ�Сʱ��
   private String estimateHours;

   // ʵ�ʼӰ�Сʱ��
   private String actualHours;

   // ����
   private String description;

   /**
    * For Application
    */
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

   // �Ӱ��Ŀ
   private List< MappingVO > otItems = new ArrayList< MappingVO >();

   /**
    * For Application2
    */
   // �·�
   private String monthly;

   // ��н��ʼ��
   private String circleStartDay;

   // ��н������
   private String circleEndDay;

   // �����ܼӰ�Сʱ��
   private String totalOTHours;

   // �Ӱ�Сʱ��������
   private String otLimitByMonth;

   // batchId
   private String batchId;

   // temp���е�otHeaderId
   private String otHeaderIdtemp;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.import.batch.statuses" ) );
      this.otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( request.getLocale().getLanguage() );
      if ( otItems != null )
      {
         otItems.add( 0, getEmptyMappingVO() );
      }
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
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final OTImportHeaderVO oTHeaderVO = ( OTImportHeaderVO ) object;
      super.setClientId( oTHeaderVO.getClientId() );
      this.employeeId = oTHeaderVO.getEmployeeId();
      this.contractId = oTHeaderVO.getContractId();
      this.itemId = oTHeaderVO.getItemId();
      this.estimateStartDate = oTHeaderVO.getEstimateStartDate();
      this.estimateEndDate = oTHeaderVO.getEstimateEndDate();
      this.actualStartDate = oTHeaderVO.getActualStartDate();
      this.actualEndDate = oTHeaderVO.getActualEndDate();
      this.estimateHours = oTHeaderVO.getEstimateHours();
      this.actualHours = oTHeaderVO.getActualHours();
      this.description = oTHeaderVO.getDescription();
      super.setStatus( oTHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      this.timesheetId = oTHeaderVO.getTimesheetId();
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

   // ���ܿͻ�ID
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // ����������ϢID
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

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getOtHeaderIdtemp()
   {
      return otHeaderIdtemp;
   }

   public void setOtHeaderIdtemp( String otHeaderIdtemp )
   {
      this.otHeaderIdtemp = otHeaderIdtemp;
   }

}
