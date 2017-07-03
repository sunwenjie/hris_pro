package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TimesheetReportExportVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 8300351530305962782L;

   // ��������Id
   private String headerId;

   // ��ԱId
   private String employeeId;

   // ��Ա���
   private String employeeNo;

   // ��Ա���������ģ�
   private String employeeNameZH;

   // ��Ա������Ӣ�ģ�
   private String employeeNameEN;

   // ����ʵ��Id
   private String entityId;

   // �����ֶ�ְλ
   private String tempPositionIds;

   // �����ֶβ���
   private String tempParentBranchIds;

   // ��������
   private String tempBranchIds;

   // ��������
   private String department;

   // ���빫˾ʱ��
   private String onboardDate;

   // ��ְ����
   private String resignDate;

   // ֤������
   private String certificateNumber;

   // �����������������ںϼƣ�
   private String totalWorkDays;

   // ȫ���������������ںϼƣ�
   private String totalFullDays;

   //���
   private String annualLeave;

   //ɥ��
   private String bereavementLeave;

   //�����
   private String careLeave;

   //���
   private String maritalLeave;

   //������н��
   private String otherLeave;

   //����
   private String sickLeave;

   //�¼�
   private String compassionateLeave;

   // �Ӱ�1.0
   private String onePointZeroOt;

   //�Ӱ�1.5
   private String onePointFiveOt;

   //�Ӱ�2.0
   private String twoPointZeroOt;

   //�Ӱ�3.0
   private String threePointZeroOt;

   //�Ӱ໻��
   private String oTToLeave;

   // ����Э��Id
   private String contractId;

   // ����Id
   private String orderId;

   // �ͻ����������ģ�
   private String clientNameZH;

   // �ͻ�������Ӣ�ģ�
   private String clientNameEN;

   // �·�
   private String monthly;

   private List< MappingVO > last12Months = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // ����
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   // ְλ
   private List< MappingVO > positions = new ArrayList< MappingVO >();

   // ְλ
   private List< Object > detailList = new ArrayList< Object >();

   // ����12����
   private List< MappingVO > monthlys = new ArrayList< MappingVO >();

   /**
    * For Report
    */
   // ��ѯ��ʼ��
   private String monthlyBegin;

   // ��ѯ��ֹ��
   private String monthlyEnd;

   // ����
   private String monthlyCount;

   // Ӧ�ó���������ƽ����
   private String avgTotalFullDays;

   // ʵ�ʳ���������ƽ����
   private String avgTotalWorkDays;

   /**
    * For Export
    */
   private String titleNameList;

   private String titleIdList;

   private String[] weekDaysName = new String[] {};

   /**
    * ����ѯ��١��Ӱ��Ŀ
    */
   private String resultJSON;
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();
   private List< MappingVO > otItems = new ArrayList< MappingVO >();

   public List< Object > getDetailList()
   {
      return detailList;
   }

   public void setDetailList( List< Object > detailList )
   {
      this.detailList = detailList;
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

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
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

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getOnboardDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.onboardDate ) );
   }

   public void setOnboardDate( String onboardDate )
   {
      this.onboardDate = onboardDate;
   }

   public String getResignDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.resignDate ) );
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getTotalWorkDays()
   {
      return KANUtil.formatNumber( totalWorkDays, getAccountId() );
   }

   public void setTotalWorkDays( String totalWorkDays )
   {
      this.totalWorkDays = totalWorkDays;
   }

   public String getTotalFullDays()
   {
      return KANUtil.formatNumber( totalFullDays, getAccountId() );
   }

   public void setTotalFullDays( String totalFullDays )
   {
      this.totalFullDays = totalFullDays;
   }

   public String getAnnualLeave()
   {
      return KANUtil.formatNumber( annualLeave, getAccountId() );
   }

   public void setAnnualLeave( String annualLeave )
   {
      this.annualLeave = annualLeave;
   }

   public String getBereavementLeave()
   {
      return KANUtil.formatNumber( bereavementLeave, getAccountId() );
   }

   public void setBereavementLeave( String bereavementLeave )
   {
      this.bereavementLeave = bereavementLeave;
   }

   public String getCareLeave()
   {
      return KANUtil.formatNumber( careLeave, getAccountId() );
   }

   public void setCareLeave( String careLeave )
   {
      this.careLeave = careLeave;
   }

   public String getMaritalLeave()
   {
      return KANUtil.formatNumber( maritalLeave, getAccountId() );
   }

   public void setMaritalLeave( String maritalLeave )
   {
      this.maritalLeave = maritalLeave;
   }

   public String getOtherLeave()
   {
      return KANUtil.formatNumber( otherLeave, getAccountId() );
   }

   public void setOtherLeave( String otherLeave )
   {
      this.otherLeave = otherLeave;
   }

   public String getSickLeave()
   {
      return KANUtil.formatNumber( sickLeave, getAccountId() );
   }

   public void setSickLeave( String sickLeave )
   {
      this.sickLeave = sickLeave;
   }

   public String getCompassionateLeave()
   {
      return KANUtil.formatNumber( compassionateLeave, getAccountId() );
   }

   public void setCompassionateLeave( String compassionateLeave )
   {
      this.compassionateLeave = compassionateLeave;
   }

   public String getOnePointZeroOt()
   {
      return KANUtil.formatNumber( onePointZeroOt, getAccountId() );
   }

   public void setOnePointZeroOt( String onePointZeroOt )
   {
      this.onePointZeroOt = onePointZeroOt;
   }

   public String getOnePointFiveOt()
   {
      return KANUtil.formatNumber( onePointFiveOt, getAccountId() );
   }

   public void setOnePointFiveOt( String onePointFiveOt )
   {
      this.onePointFiveOt = onePointFiveOt;
   }

   public String getTwoPointZeroOt()
   {
      return KANUtil.formatNumber( twoPointZeroOt, getAccountId() );
   }

   public void setTwoPointZeroOt( String twoPointZeroOt )
   {
      this.twoPointZeroOt = twoPointZeroOt;
   }

   public String getThreePointZeroOt()
   {
      return KANUtil.formatNumber( threePointZeroOt, getAccountId() );
   }

   public void setThreePointZeroOt( String threePointZeroOt )
   {
      this.threePointZeroOt = threePointZeroOt;
   }

   public String getoTToLeave()
   {
      return KANUtil.formatNumber( oTToLeave, getAccountId() );
   }

   public void setoTToLeave( String oTToLeave )
   {
      this.oTToLeave = oTToLeave;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
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

   public List< MappingVO > getLast12Months()
   {
      return last12Months;
   }

   public void setLast12Months( List< MappingVO > last12Months )
   {
      this.last12Months = last12Months;
   }

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getTempPositionIds()
   {
      return tempPositionIds;
   }

   public void setTempPositionIds( String tempPositionIds )
   {
      this.tempPositionIds = tempPositionIds;
   }

   public String getTempParentBranchIds()
   {
      return tempParentBranchIds;
   }

   public void setTempParentBranchIds( String tempParentBranchIds )
   {
      this.tempParentBranchIds = tempParentBranchIds;
   }

   public String getTempBranchIds()
   {
      return tempBranchIds;
   }

   public void setTempBranchIds( String tempBranchIds )
   {
      this.tempBranchIds = tempBranchIds;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   @Override
   public void reset() throws KANException
   {
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.attendance.timesheet.header.status" ) );
      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.branchs = KANConstants.getKANAccountConstants( super.getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );

      if ( entities != null )
      {
         entities.add( 0, getEmptyMappingVO() );
      }

      this.last12Months = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( request.getLocale().getLanguage() );
      this.monthlys = KANUtil.getMonthsByCondition( 24, 0 );
      this.leaveItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), super.getCorpId() );
      this.otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( request.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void update( Object object ) throws KANException
   {

   }

   // decodeְλ����
   public String getDecodeTempPositionIds()
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( tempPositionIds ) != null && positions != null && positions.size() > 0 )
      {
         for ( String positionId : tempPositionIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( positionId, positions );
            }
            else
            {
               returnStr = returnStr + "��" + decodeField( positionId, positions );
            }
         }
      }

      return returnStr;
   }

   // decodeְλ����
   public String getDecodeTempPositionIds( String tempPositionIds )
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( tempPositionIds ) != null && positions != null && positions.size() > 0 )
      {
         for ( String positionId : tempPositionIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( positionId, positions );
            }
            else
            {
               returnStr = returnStr + "��" + decodeField( positionId, positions );
            }
         }
      }

      return returnStr;
   }

   // decode��������
   public String getDecodeTempBranchIds()
   {
      return decodeBranch( tempBranchIds );
   }

   // decode�ϼ���������
   public String getDecodeTempParentBranchIds()
   {

      if ( KANUtil.filterEmpty( tempParentBranchIds ) != null )
      {
         return decodeBranch( tempParentBranchIds.split( "," )[ 0 ] );
      }
      return decodeBranch( tempParentBranchIds );
   }

   // ���뷨��ʵ��
   public String getDecodeEntityId()
   {
      return decodeField( entityId, entities );
   }

   public String decodeBranch( final String branchIds )
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( branchIds ) != null && branchs != null && branchs.size() > 0 )
      {
         for ( String branchId : branchIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( branchId, branchs, true );
            }
            else
            {
               returnStr = returnStr + "��" + decodeField( branchId, branchs, true );
            }
         }
      }

      return returnStr;
   }

   public String getMonthlyCount()
   {
      return monthlyCount;
   }

   public void setMonthlyCount( String monthlyCount )
   {
      this.monthlyCount = monthlyCount;
   }

   public String getAvgTotalFullDays()
   {
      return avgTotalFullDays;
   }

   public void setAvgTotalFullDays( String avgTotalFullDays )
   {
      this.avgTotalFullDays = avgTotalFullDays;
   }

   public String getAvgTotalWorkDays()
   {
      return avgTotalWorkDays;
   }

   public void setAvgTotalWorkDays( String avgTotalWorkDays )
   {
      this.avgTotalWorkDays = avgTotalWorkDays;
   }

   public String getMonthlyBegin()
   {
      return monthlyBegin;
   }

   public void setMonthlyBegin( String monthlyBegin )
   {
      this.monthlyBegin = monthlyBegin;
   }

   public String getMonthlyEnd()
   {
      return monthlyEnd;
   }

   public void setMonthlyEnd( String monthlyEnd )
   {
      this.monthlyEnd = monthlyEnd;
   }

   public String getTitleNameList()
   {
      return titleNameList;
   }

   public void setTitleNameList( String titleNameList )
   {
      this.titleNameList = titleNameList;
   }

   public String getTitleIdList()
   {
      return titleIdList;
   }

   public void setTitleIdList( String titleIdList )
   {
      this.titleIdList = titleIdList;
   }

   public List< MappingVO > getMonthlys()
   {
      return monthlys;
   }

   public void setMonthlys( List< MappingVO > monthlys )
   {
      this.monthlys = monthlys;
   }

   public String getResultJSON()
   {
      return resultJSON;
   }

   public void setResultJSON( String resultJSON )
   {
      this.resultJSON = resultJSON;
   }

   public List< MappingVO > getItems()
   {
      final List< MappingVO > items = new ArrayList< MappingVO >();
      if ( KANUtil.filterEmpty( resultJSON ) != null )
      {
         final JSONObject jsonObject = JSONObject.fromObject( resultJSON );
         for ( MappingVO leaveItem : leaveItems )
         {
            if ( KANUtil.filterEmpty( jsonObject.get( leaveItem.getMappingId() ) ) != null )
            {
               final MappingVO tempMappingVO = new MappingVO();
               tempMappingVO.setMappingId( leaveItem.getMappingValue() );
               tempMappingVO.setMappingValue( jsonObject.getString( leaveItem.getMappingId() ) );
               items.add( tempMappingVO );
            }
         }
         for ( MappingVO otItem : otItems )
         {
            if ( KANUtil.filterEmpty( jsonObject.get( otItem.getMappingId() ) ) != null )
            {
               final MappingVO tempMappingVO = new MappingVO();
               tempMappingVO.setMappingId( otItem.getMappingValue() );
               tempMappingVO.setMappingValue( jsonObject.getString( otItem.getMappingId() ) );
               items.add( tempMappingVO );
            }
         }
      }
      return items;
   }

   public List< MappingVO > getLeaveItems()
   {
      return leaveItems;
   }

   public void setLeaveItems( List< MappingVO > leaveItems )
   {
      this.leaveItems = leaveItems;
   }

   public List< MappingVO > getOtItems()
   {
      return otItems;
   }

   public void setOtItems( List< MappingVO > otItems )
   {
      this.otItems = otItems;
   }

   public String[] getWeekDaysName()
   {
      return weekDaysName;
   }

   public void setWeekDaysName( String[] weekDaysName )
   {
      this.weekDaysName = weekDaysName;
   }

}
