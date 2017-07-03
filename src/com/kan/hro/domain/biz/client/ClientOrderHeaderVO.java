package com.kan.hro.domain.biz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientOrderHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -6940426782686484733L;

   /**
    * For DB
    */

   // ��������Id
   private String orderHeaderId;

   // �����ͬId
   private String contractId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // ��Ʊ��ַId
   private String invoiceAddressId;

   // �������ƣ����ģ�
   private String nameZH;

   // �������ƣ�Ӣ�ģ�
   private String nameEN;

   // ������ʼʱ��
   private String startDate;

   // ��������ʱ��
   private String endDate;

   // ��н���� - ��ʼ��
   private String circleStartDay;

   // ��н���� - ������
   private String circleEndDay;

   // н�깩Ӧ��
   private String salaryVendorId;

   // ��н����
   private String payrollDay;

   // �����·�
   private String salaryMonth;

   // �籣�·�
   private String sbMonth;

   // �̱��·�
   private String cbMonth;

   // �������·�
   private String fundMonth;

   // ���۷�ʽ
   private String salesType;

   // ��Ʊ��ʽ
   private String invoiceType;

   // ���㷽ʽ
   private String settlementType;

   // ����������
   private String probationMonth;

   // ��������
   private String serviceScope;

   // �籣���˲��ֹ�˾�е�
   private String personalSBBurden;

   // �Ӱ���Ҫ����
   private String applyOTFirst;

   // ÿ��Ӱ�Сʱ������
   private String otLimitByDay;

   // ÿ�¼Ӱ�Сʱ������
   private String otLimitByMonth;

   // �����ռӰ��Ŀ
   private String workdayOTItemId;

   // ��Ϣ�ռӰ��Ŀ
   private String weekendOTItemId;

   // �ڼ��ռӰ��Ŀ
   private String holidayOTItemId;

   // ���ڷ�ʽ
   private String attendanceCheckType;

   // ��������
   private String attendanceGenerate;

   // ��˷�ʽ
   private String approveType;

   // ����Id
   private String calendarId;

   // �Ű�Id
   private String shiftId;

   // ���ٹ���Id
   private String sickLeaveSalaryId;

   // ˰��Id
   private String taxId;

   // ����
   private String attachment;

   // �������ţ�Branch Id��
   private String branch;

   // �����ˣ�Position Id��
   private String owner;

   // ����
   private String locked;

   // ��������
   private String noticeExpire;

   // �����ڵ�������
   private String noticeProbationExpire;

   // ���ݵ�������
   private String noticeRetire;

   // ����籣����
   private String noticeHKSB;

   // ����
   private String description;

   // ��н��ʽ
   private String salaryType;

   // ���㷽ʽ
   private String divideType;

   // ���㷽ʽ - ������
   private String divideTypeIncomplete;

   // �������Ŀ
   private String excludeDivideItemIds;

   // ��˰����ID
   private String incomeTaxBaseId;

   // ��˰˰��ID
   private String incomeTaxRangeHeaderId;

   /**
    * For Application
    */
   @JsonIgnore
   // ״̬��ʾ<a>��ǩ
   private String isLink;
   @JsonIgnore
   // �����б�
   private String[] attachmentArray;
   @JsonIgnore
   // �ͻ����
   private String clientNumber;
   @JsonIgnore
   // �ͻ���������
   private String clientNameZH;
   @JsonIgnore
   // �ͻ�Ӣ������
   private String clientNameEN;
   @JsonIgnore
   // �����ͬ���
   private String contractNumber;
   @JsonIgnore
   // ������
   private String monthly;
   @JsonIgnore
   // ��ԱID
   private String employeeId;
   @JsonIgnore
   // ����Э��ID
   private String employeeContractId;
   @JsonIgnore
   // �����ͬ���ƣ����ģ�
   private String contractNameZH;
   @JsonIgnore
   // �����ͬ���ƣ�Ӣ�ģ�
   private String contractNameEN;
   @JsonIgnore
   //��������
   private String currency;
   @JsonIgnore
   //��ͬ����
   private String contractPeriod;
   @JsonIgnore
   // �·�����
   private List< MappingVO > monthTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���۷�ʽ
   private List< MappingVO > salesTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ʊ��ʽ
   private List< MappingVO > invoiceTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���㷽ʽ 
   private List< MappingVO > settlementTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������
   private List< MappingVO > serviceScopes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();
   @JsonIgnore
   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // Ա��
   private List< StaffBaseView > staffBaseViews = new ArrayList< StaffBaseView >();
   @JsonIgnore
   // ����
   private List< MappingVO > lockeds = new ArrayList< MappingVO >();
   @JsonIgnore
   // ������
   private List< String > settlementFlags = new ArrayList< String >();
   @JsonIgnore
   // �Ӱ��Ŀ
   private List< MappingVO > otItems = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��н���ڣ�ÿ�£�
   private List< MappingVO > payrollDays = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��н���ڣ���ʼ��
   private List< MappingVO > circleStartDays = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��н���ڣ�������
   private List< MappingVO > circleEndDays = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������
   private List< MappingVO > attendanceCheckTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������
   private List< MappingVO > attendanceGenerates = new ArrayList< MappingVO >();
   @JsonIgnore
   // �������
   private List< MappingVO > approveTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ������
   private List< MappingVO > probationMonths = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����
   private List< MappingVO > calendarIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // �Ű�
   private List< MappingVO > shiftIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���ٹ���
   private List< MappingVO > sickLeaveSalaryIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��н��ʽ
   private List< MappingVO > salaryTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���㷽ʽ
   private List< MappingVO > divideTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // �������Ŀ
   private List< MappingVO > excludeDivideItems = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��˰������
   private List< MappingVO > incomeTaxBases = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��˰˰��
   private List< MappingVO > incomeTaxRangeHeaders = new ArrayList< MappingVO >();
   @JsonIgnore
   // н�깩Ӧ��
   private List< MappingVO > salaryVendors = new ArrayList< MappingVO >();
   @JsonIgnore
   //��������
   private List< MappingVO > currencys = new ArrayList< MappingVO >();
   @JsonIgnore
   //��ͬ����
   private List< MappingVO > contractPeriods = new ArrayList< MappingVO >();


   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.client.order.header.status" ) );

      final KANAccountConstants accountContants = KANConstants.getKANAccountConstants( super.getAccountId() );

      this.monthTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.monthTypes" );
      this.salesTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.salesTypes" );
      this.invoiceTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.invoiceTypes" );
      this.settlementTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.settlementTypes" );
      this.lockeds = KANUtil.getMappings( this.getLocale(), "flag" );
      this.serviceScopes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.serviceScopes" );
      this.entitys = accountContants.getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = accountContants.getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.staffBaseViews = accountContants.STAFF_BASEVIEW;
      this.otItems = accountContants.getOtItems( request.getLocale().getLanguage(), super.getCorpId() );
      this.payrollDays = KANUtil.getMappings( this.getLocale(), "business.client.dates" );
      this.circleStartDays = KANUtil.getMappings( this.getLocale(), "business.client.dates" );
      this.circleEndDays = KANUtil.getMappings( this.getLocale(), "business.client.dates" );
      this.attendanceCheckTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.attendanceCheckTypes" );
      this.attendanceGenerates = KANUtil.getMappings( this.getLocale(), "business.client.order.header.attendanceGenerates" );
      this.approveTypes = KANUtil.getMappings( this.getLocale(), "business.employee.contract.approveType" );
      this.probationMonths = KANUtil.getMappings( this.getLocale(), "business.client.order.header.probationMonths" );
      this.calendarIds = accountContants.getCalendar( request.getLocale().getLanguage(), super.getCorpId() );
      this.shiftIds = accountContants.getShift( request.getLocale().getLanguage(), super.getCorpId() );
      this.sickLeaveSalaryIds = accountContants.getSickLeaveSalary( request.getLocale().getLanguage(), super.getCorpId() );
      this.salaryTypes = KANUtil.getMappings( this.getLocale(), "business.employee.contract.salaryType" );
      this.divideTypes = KANUtil.getMappings( this.getLocale(), "business.employee.contract.divideType" );
      // ��6���ݼٿ�Ŀ
      this.excludeDivideItems = accountContants.getItemsByType( "6", request.getLocale().getLanguage(), super.getCorpId() );
      this.incomeTaxBases = KANConstants.getIncomeTaxBaseVOs( request.getLocale().getLanguage() );
      this.incomeTaxRangeHeaders = KANConstants.getIncomeTaxRangeVOs( request.getLocale().getLanguage() );
      this.currencys = KANUtil.getMappings( this.getLocale(), "business.client.order.Currency" );
      this.contractPeriods = KANUtil.getMappings( this.getLocale(), "business.client.order.Contract.Period" );

      if ( this.calendarIds != null )
      {
         this.calendarIds.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      if ( this.shiftIds != null )
      {
         this.shiftIds.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      if ( this.sickLeaveSalaryIds != null )
      {
         this.sickLeaveSalaryIds.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      if ( this.incomeTaxBases != null )
      {
         this.incomeTaxBases.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      if ( this.incomeTaxRangeHeaders != null )
      {
         this.incomeTaxRangeHeaders.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

   }

   public List< MappingVO > getTaxes()
   {
      final List< MappingVO > taxes = KANConstants.getKANAccountConstants( super.getAccountId() ).getTaxes( super.getLocale().getLanguage() );

      if ( taxes != null )
      {
         taxes.add( 0, KANUtil.getEmptyMappingVO( super.getLocale() ) );
      }

      return taxes;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   // ��ͬID����
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // ��ȡ����ʵ��
   public String getDecodeEntityId()
   {
      return decodeField( this.getEntityId(), this.entitys );
   }

   // ��ȡҵ������
   public String getDecodeBusinessTypeId()
   {
      return decodeField( this.getBusinessTypeId(), this.businessTypes );
   }

   // ����������
   public String getDecodeProbationMonth()
   {
      return decodeField( this.getProbationMonth(), this.probationMonths );
   }

   // ���۷�ʽ
   public String getDecodeSalesType()
   {
      return decodeField( salesType, this.salesTypes );
   }

   // ��������
   public String getDecodeServiceScope()
   {
      return decodeField( this.getServiceScope(), this.serviceScopes );
   }

   // ���㷽ʽ
   public String getDecodeSettlementType()
   {
      return decodeField( settlementType, this.settlementTypes );
   }

   // ��ȡ��Ʊ��ʽ
   public String getDecodeInvoiceType()
   {
      return decodeField( this.getInvoiceType(), this.invoiceTypes );
   }

   // ��ȡ������������
   public String getDecodeBranch()
   {
      return decodeField( this.branch, KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( super.getLocale().getLanguage() ), true );
   }

   // ��ȡ����
   public String getDecodeLocked()
   {
      return decodeField( this.getLocked(), this.lockeds );
   }

   // ��ȡ����������
   public String getDecodeOwner()
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.invoiceAddressId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.circleStartDay = "";
      this.circleEndDay = "";
      this.payrollDay = "0";
      this.salaryMonth = "0";
      this.sbMonth = "";
      this.cbMonth = "";
      this.fundMonth = "";
      this.salesType = "0";
      this.invoiceType = "0";
      this.settlementType = "0";
      this.probationMonth = "0";
      this.serviceScope = "0";
      this.applyOTFirst = "0";
      this.personalSBBurden = "";
      this.otLimitByDay = "";
      this.otLimitByMonth = "";
      this.workdayOTItemId = "0";
      this.weekendOTItemId = "0";
      this.holidayOTItemId = "0";
      this.calendarId = "0";
      this.shiftId = "0";
      this.sickLeaveSalaryId = "0";
      this.attachment = "";
      this.branch = "";
      this.owner = "";
      this.locked = "";
      this.noticeExpire = "";
      this.noticeProbationExpire = "";
      this.noticeRetire = "";
      this.noticeHKSB = "";
      this.description = "";
      this.taxId = "0";
      this.attendanceCheckType = "0";
      this.attendanceGenerate = "0";
      this.approveType = "0";
      this.salaryType = "0";
      this.divideType = "0";
      this.divideTypeIncomplete = "0";
      this.excludeDivideItemIds = "";
      this.incomeTaxBaseId = "";
      this.incomeTaxRangeHeaderId = "";
      this.salaryVendorId = "0";
      this.currency = "0";
      this.contractPeriod = "0";
      super.setStatus( "0" );
      super.setClientId( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) object;
      this.contractId = clientOrderHeaderVO.getContractId();
      this.entityId = clientOrderHeaderVO.getEntityId();
      this.businessTypeId = clientOrderHeaderVO.getBusinessTypeId();
      this.invoiceAddressId = clientOrderHeaderVO.getInvoiceAddressId();
      this.nameZH = clientOrderHeaderVO.getNameZH();
      this.nameEN = clientOrderHeaderVO.getNameEN();
      this.startDate = clientOrderHeaderVO.getStartDate();
      this.endDate = clientOrderHeaderVO.getEndDate();
      this.circleStartDay = clientOrderHeaderVO.getCircleStartDay();
      this.circleEndDay = clientOrderHeaderVO.getCircleEndDay();
      this.salaryVendorId = clientOrderHeaderVO.getSalaryVendorId();
      this.payrollDay = clientOrderHeaderVO.getPayrollDay();
      this.salaryMonth = clientOrderHeaderVO.getSalaryMonth();
      this.sbMonth = clientOrderHeaderVO.getSbMonth();
      this.cbMonth = clientOrderHeaderVO.getCbMonth();
      // TODO ������Ҫ��������ʱ��ʩ���籣�������ֺ��޸ģ�
      this.fundMonth = clientOrderHeaderVO.getSbMonth();
      this.salesType = clientOrderHeaderVO.getSalesType();
      this.invoiceType = clientOrderHeaderVO.getInvoiceType();
      this.settlementType = clientOrderHeaderVO.getSettlementType();
      this.probationMonth = clientOrderHeaderVO.getProbationMonth();
      this.serviceScope = clientOrderHeaderVO.getServiceScope();
      this.personalSBBurden = clientOrderHeaderVO.getPersonalSBBurden();
      this.applyOTFirst = clientOrderHeaderVO.getApplyOTFirst();
      this.otLimitByDay = clientOrderHeaderVO.getOtLimitByDay();
      this.otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
      this.workdayOTItemId = clientOrderHeaderVO.getWorkdayOTItemId();
      this.weekendOTItemId = clientOrderHeaderVO.getWeekendOTItemId();
      this.holidayOTItemId = clientOrderHeaderVO.getHolidayOTItemId();
      this.calendarId = clientOrderHeaderVO.getCalendarId();
      this.shiftId = clientOrderHeaderVO.getShiftId();
      this.sickLeaveSalaryId = clientOrderHeaderVO.getSickLeaveSalaryId();
      this.attachment = clientOrderHeaderVO.getAttachment();
      this.branch = clientOrderHeaderVO.getBranch();
      this.owner = clientOrderHeaderVO.getOwner();
      this.locked = clientOrderHeaderVO.getLocked();
      this.description = clientOrderHeaderVO.getDescription();
      this.taxId = clientOrderHeaderVO.getTaxId();
      this.attendanceCheckType = clientOrderHeaderVO.getAttendanceCheckType();
      this.attendanceGenerate = clientOrderHeaderVO.getAttendanceGenerate();
      this.approveType = clientOrderHeaderVO.getApproveType();
      this.salaryType = clientOrderHeaderVO.getSalaryType();
      this.divideType = clientOrderHeaderVO.getDivideType();
      // TODO ������Ҫ��������ʱ��ʩ�������¿����в�ͬ�����㷽ʽ��
      this.divideTypeIncomplete = clientOrderHeaderVO.getDivideType();
      this.excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
      this.noticeExpire = clientOrderHeaderVO.getNoticeExpire();
      this.noticeProbationExpire = clientOrderHeaderVO.getNoticeProbationExpire();
      this.noticeRetire = clientOrderHeaderVO.getNoticeRetire();
      this.noticeHKSB = clientOrderHeaderVO.getNoticeHKSB();
      this.currency = clientOrderHeaderVO.getCurrency();
      this.contractPeriod = clientOrderHeaderVO.getContractPeriod();
      this.incomeTaxBaseId = clientOrderHeaderVO.getIncomeTaxBaseId();
      this.incomeTaxRangeHeaderId = clientOrderHeaderVO.getIncomeTaxRangeHeaderId();
      super.setClientId( clientOrderHeaderVO.getClientId() );
      super.setCorpId( clientOrderHeaderVO.getCorpId() );
      super.setModifyDate( new Date() );
      super.setModifyBy( clientOrderHeaderVO.getModifyBy() );
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId, "0" );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
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

   public String getInvoiceAddressId()
   {
      return invoiceAddressId;
   }

   public void setInvoiceAddressId( String invoiceAddressId )
   {
      this.invoiceAddressId = invoiceAddressId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
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

   public String getPayrollDay()
   {
      return payrollDay;
   }

   public void setPayrollDay( String payrollDay )
   {
      this.payrollDay = payrollDay;
   }

   public String getSalaryMonth()
   {
      return salaryMonth;
   }

   public void setSalaryMonth( String salaryMonth )
   {
      this.salaryMonth = salaryMonth;
   }

   public String getSbMonth()
   {
      return sbMonth;
   }

   public void setSbMonth( String sbMonth )
   {
      this.sbMonth = sbMonth;
   }

   public String getSalesType()
   {
      return salesType;
   }

   public void setSalesType( String salesType )
   {
      this.salesType = salesType;
   }

   public String getInvoiceType()
   {
      return invoiceType;
   }

   public void setInvoiceType( String invoiceType )
   {
      this.invoiceType = invoiceType;
   }

   public String getSettlementType()
   {
      return settlementType;
   }

   public void setSettlementType( String settlementType )
   {
      this.settlementType = settlementType;
   }

   public String getProbationMonth()
   {
      return probationMonth;
   }

   public void setProbationMonth( String probationMonth )
   {
      this.probationMonth = probationMonth;
   }

   public String getServiceScope()
   {
      return serviceScope;
   }

   public void setServiceScope( String serviceScope )
   {
      this.serviceScope = serviceScope;
   }

   public String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getOtLimitByDay()
   {
      return KANUtil.filterEmpty( otLimitByDay );
   }

   public void setOtLimitByDay( String otLimitByDay )
   {
      this.otLimitByDay = otLimitByDay;
   }

   public String getOtLimitByMonth()
   {
      return KANUtil.filterEmpty( otLimitByMonth );
   }

   public void setOtLimitByMonth( String otLimitByMonth )
   {
      this.otLimitByMonth = otLimitByMonth;
   }

   public List< MappingVO > getMonthTypes()
   {
      return monthTypes;
   }

   public void setMonthTypes( List< MappingVO > monthTypes )
   {
      this.monthTypes = monthTypes;
   }

   public List< MappingVO > getSalesTypes()
   {
      return salesTypes;
   }

   public void setSalesTypes( List< MappingVO > salesTypes )
   {
      this.salesTypes = salesTypes;
   }

   public List< MappingVO > getInvoiceTypes()
   {
      return invoiceTypes;
   }

   public void setInvoiceTypes( List< MappingVO > invoiceTypes )
   {
      this.invoiceTypes = invoiceTypes;
   }

   public List< MappingVO > getSettlementTypes()
   {
      return settlementTypes;
   }

   public void setSettlementTypes( List< MappingVO > settlementTypes )
   {
      this.settlementTypes = settlementTypes;
   }

   public List< MappingVO > getProbationMonths()
   {
      return probationMonths;
   }

   public void setProbationMonths( List< MappingVO > probationMonths )
   {
      this.probationMonths = probationMonths;
   }

   public List< MappingVO > getServiceScopes()
   {
      return serviceScopes;
   }

   public void setServiceScopes( List< MappingVO > serviceScopes )
   {
      this.serviceScopes = serviceScopes;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public String getCalendarId()
   {
      return calendarId;
   }

   public void setCalendarId( String calendarId )
   {
      this.calendarId = calendarId;
   }

   public String getShiftId()
   {
      return shiftId;
   }

   public void setShiftId( String shiftId )
   {
      this.shiftId = shiftId;
   }

   public final String getSickLeaveSalaryId()
   {
      return sickLeaveSalaryId;
   }

   public final void setSickLeaveSalaryId( String sickLeaveSalaryId )
   {
      this.sickLeaveSalaryId = sickLeaveSalaryId;
   }

   public String getApplyOTFirst()
   {
      return applyOTFirst;
   }

   public void setApplyOTFirst( String applyOTFirst )
   {
      this.applyOTFirst = applyOTFirst;
   }

   public List< StaffBaseView > getStaffBaseViews()
   {
      return staffBaseViews;
   }

   public void setStaffBaseViews( List< StaffBaseView > staffBaseViews )
   {
      this.staffBaseViews = staffBaseViews;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public String getAttendanceCheckType()
   {
      return attendanceCheckType;
   }

   public void setAttendanceCheckType( String attendanceCheckType )
   {
      this.attendanceCheckType = attendanceCheckType;
   }

   public String getTaxId()
   {
      return taxId;
   }

   public void setTaxId( String taxId )
   {
      this.taxId = taxId;
   }

   public List< MappingVO > getAttendanceCheckTypes()
   {
      return attendanceCheckTypes;
   }

   public void setAttendanceCheckTypes( List< MappingVO > attendanceCheckTypes )
   {
      this.attendanceCheckTypes = attendanceCheckTypes;
   }

   public String getWorkdayOTItemId()
   {
      return workdayOTItemId;
   }

   public void setWorkdayOTItemId( String workdayOTItemId )
   {
      this.workdayOTItemId = workdayOTItemId;
   }

   public String getWeekendOTItemId()
   {
      return weekendOTItemId;
   }

   public void setWeekendOTItemId( String weekendOTItemId )
   {
      this.weekendOTItemId = weekendOTItemId;
   }

   public String getHolidayOTItemId()
   {
      return holidayOTItemId;
   }

   public void setHolidayOTItemId( String holidayOTItemId )
   {
      this.holidayOTItemId = holidayOTItemId;
   }

   public String getLocked()
   {
      return locked;
   }

   public void setLocked( String locked )
   {
      this.locked = locked;
   }

   public String getClientName()
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getEmployeeContractId()
   {
      return employeeContractId;
   }

   public void setEmployeeContractId( String employeeContractId )
   {
      this.employeeContractId = employeeContractId;
   }

   public String getAttendanceGenerate()
   {
      return attendanceGenerate;
   }

   public void setAttendanceGenerate( String attendanceGenerate )
   {
      this.attendanceGenerate = attendanceGenerate;
   }

   public List< MappingVO > getAttendanceGenerates()
   {
      return attendanceGenerates;
   }

   public void setAttendanceGenerates( List< MappingVO > attendanceGenerates )
   {
      this.attendanceGenerates = attendanceGenerates;
   }

   public List< MappingVO > getLockeds()
   {
      return lockeds;
   }

   public void setLockeds( List< MappingVO > lockeds )
   {
      this.lockeds = lockeds;
   }

   public String getCbMonth()
   {
      return cbMonth;
   }

   public void setCbMonth( String cbMonth )
   {
      this.cbMonth = cbMonth;
   }

   public final String getFundMonth()
   {
      return fundMonth;
   }

   public final void setFundMonth( String fundMonth )
   {
      this.fundMonth = fundMonth;
   }

   public List< String > getSettlementFlags()
   {
      return settlementFlags;
   }

   public void setSettlementFlags( List< String > settlementFlags )
   {
      this.settlementFlags = settlementFlags;
   }

   public List< MappingVO > getOtItems()
   {
      return otItems;
   }

   public void setOtItems( List< MappingVO > otItems )
   {
      this.otItems = otItems;
   }

   public String getIsLink() throws KANException
   {
      if ( super.getStatus() != null && ( super.getStatus().equals( "1" ) || super.getStatus().equals( "4" ) || super.getStatus().equals( "8" ) ) )
      {
         isLink = " &nbsp; <a onclick=\"submit_object('" + getEncodedId() + "')\">�ύ</a>";
      }
      if ( "2".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('" + this.getWorkflowId() + "'); />";
      }
      return isLink;
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

   public String getContractNumber()
   {
      return contractNumber;
   }

   public void setContractNumber( String contractNumber )
   {
      this.contractNumber = contractNumber;
   }

   public final String getApproveType()
   {
      return approveType;
   }

   public final void setApproveType( String approveType )
   {
      this.approveType = approveType;
   }

   public final String getEmployeeId()
   {
      return employeeId;
   }

   public final void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getSalaryType()
   {
      return salaryType;
   }

   public void setSalaryType( String salaryType )
   {
      this.salaryType = salaryType;
   }

   public String getDivideType()
   {
      return divideType;
   }

   public void setDivideType( String divideType )
   {
      this.divideType = divideType;
   }

   public final String getDivideTypeIncomplete()
   {
      return divideTypeIncomplete;
   }

   public final void setDivideTypeIncomplete( String divideTypeIncomplete )
   {
      this.divideTypeIncomplete = divideTypeIncomplete;
   }

   public List< MappingVO > getPayrollDays()
   {
      return payrollDays;
   }

   public void setPayrollDays( List< MappingVO > payrollDays )
   {
      this.payrollDays = payrollDays;
   }

   public List< MappingVO > getCircleStartDays()
   {
      return circleStartDays;
   }

   public void setCircleStartDays( List< MappingVO > circleStartDays )
   {
      this.circleStartDays = circleStartDays;
   }

   public List< MappingVO > getCircleEndDays()
   {
      return circleEndDays;
   }

   public void setCircleEndDays( List< MappingVO > circleEndDays )
   {
      this.circleEndDays = circleEndDays;
   }

   public List< MappingVO > getApproveTypes()
   {
      return approveTypes;
   }

   public void setApproveTypes( List< MappingVO > approveTypes )
   {
      this.approveTypes = approveTypes;
   }

   public List< MappingVO > getCalendarIds()
   {
      return calendarIds;
   }

   public void setCalendarIds( List< MappingVO > calendarIds )
   {
      this.calendarIds = calendarIds;
   }

   public List< MappingVO > getShiftIds()
   {
      return shiftIds;
   }

   public void setShiftIds( List< MappingVO > shiftIds )
   {
      this.shiftIds = shiftIds;
   }

   public final List< MappingVO > getSickLeaveSalaryIds()
   {
      return sickLeaveSalaryIds;
   }

   public final void setSickLeaveSalaryIds( List< MappingVO > sickLeaveSalaryIds )
   {
      this.sickLeaveSalaryIds = sickLeaveSalaryIds;
   }

   public List< MappingVO > getSalaryTypes()
   {
      return salaryTypes;
   }

   public void setSalaryTypes( List< MappingVO > salaryTypes )
   {
      this.salaryTypes = salaryTypes;
   }

   public List< MappingVO > getDivideTypes()
   {
      return divideTypes;
   }

   public void setDivideTypes( List< MappingVO > divideTypes )
   {
      this.divideTypes = divideTypes;
   }

   public String getNoticeExpire()
   {
      return noticeExpire;
   }

   public void setNoticeExpire( String noticeExpire )
   {
      this.noticeExpire = noticeExpire;
   }

   public String getExcludeDivideItemIds()
   {
      return excludeDivideItemIds;
   }

   public void setExcludeDivideItemIds( String excludeDivideItemIds )
   {
      this.excludeDivideItemIds = excludeDivideItemIds;
   }

   public List< MappingVO > getExcludeDivideItems()
   {
      return excludeDivideItems;
   }

   public void setExcludeDivideItems( List< MappingVO > excludeDivideItems )
   {
      this.excludeDivideItems = excludeDivideItems;
   }

   public String getIncomeTaxBaseId()
   {
      return KANUtil.filterEmpty( incomeTaxBaseId, "0" );
   }

   public void setIncomeTaxBaseId( String incomeTaxBaseId )
   {
      this.incomeTaxBaseId = incomeTaxBaseId;
   }

   public String getIncomeTaxRangeHeaderId()
   {
      return KANUtil.filterEmpty( incomeTaxRangeHeaderId, "0" );
   }

   public void setIncomeTaxRangeHeaderId( String incomeTaxRangeHeaderId )
   {
      this.incomeTaxRangeHeaderId = incomeTaxRangeHeaderId;
   }

   public String getNoticeProbationExpire()
   {
      return noticeProbationExpire;
   }

   public void setNoticeProbationExpire( String noticeProbationExpire )
   {
      this.noticeProbationExpire = noticeProbationExpire;
   }

   public List< MappingVO > getIncomeTaxBases()
   {
      return incomeTaxBases;
   }

   public void setIncomeTaxBases( List< MappingVO > incomeTaxBases )
   {
      this.incomeTaxBases = incomeTaxBases;
   }

   public List< MappingVO > getIncomeTaxRangeHeaders()
   {
      return incomeTaxRangeHeaders;
   }

   public void setIncomeTaxRangeHeaders( List< MappingVO > incomeTaxRangeHeaders )
   {
      this.incomeTaxRangeHeaders = incomeTaxRangeHeaders;
   }

   public String getContractNameZH()
   {
      return contractNameZH;
   }

   public void setContractNameZH( String contractNameZH )
   {
      this.contractNameZH = contractNameZH;
   }

   public String getContractNameEN()
   {
      return contractNameEN;
   }

   public void setContractNameEN( String contractNameEN )
   {
      this.contractNameEN = contractNameEN;
   }

   public String getSalaryVendorId()
   {
      return salaryVendorId;
   }

   public void setSalaryVendorId( String salaryVendorId )
   {
      this.salaryVendorId = salaryVendorId;
   }

   public List< MappingVO > getSalaryVendors()
   {
      return salaryVendors;
   }

   public void setSalaryVendors( List< MappingVO > salaryVendors )
   {
      this.salaryVendors = salaryVendors;
   }

   public String getNoticeRetire()
   {
      return noticeRetire;
   }

   public void setNoticeRetire( String noticeRetire )
   {
      this.noticeRetire = noticeRetire;
   }

   public String getNoticeHKSB()
   {
      return noticeHKSB;
   }

   public void setNoticeHKSB( String noticeHKSB )
   {
      this.noticeHKSB = noticeHKSB;
   }

   public String getCurrency()
   {
      return currency;
   }

   public void setCurrency( String currency )
   {
      this.currency = currency;
   }

   public List< MappingVO > getCurrencys()
   {
      return currencys;
   }

   public void setCurrencys( List< MappingVO > currencys )
   {
      this.currencys = currencys;
   }

   public String getContractPeriod()
   {
      return contractPeriod;
   }

   public void setContractPeriod( String contractPeriod )
   {
      this.contractPeriod = contractPeriod;
   }

   public List< MappingVO > getContractPeriods()
   {
      return contractPeriods;
   }

   public void setContractPeriods( List< MappingVO > contractPeriods )
   {
      this.contractPeriods = contractPeriods;
   }

}
