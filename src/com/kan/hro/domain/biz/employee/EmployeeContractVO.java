package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.web.actions.biz.employee.EmployeeContractAction;

public class EmployeeContractVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   public final static String FLAG_LABOR_CONTRACT = "1";

   public final static String FLAG_SERVICE_AGREEMENT = "2";

   /**
    * For DB
    */

   // �Ͷ���ͬId������
   private String contractId;

   // ��ԱId
   private String employeeId;

   // ����Id
   private String orderId;

   // ��������
   private String department;

   // ְλId��������
   private String positionId;

   // ����ְλ
   private String additionalPosition;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // ��ͬģ��Id
   private String templateId;

   // ֱ�߾���ID ���󶨿ͻ���ϵ�ˣ�
   private String lineManagerId;

   // ��˷�ʽ
   private String approveType;

   // ��ͬ��ţ��鵵ʹ�ã�
   private String contractNo;

   // ����ͬ��
   private String masterContractId;

   // ��ͬ���ƣ����ģ�
   private String nameZH;

   // ��ͬ���ƣ�Ӣ�ģ�
   private String nameEN;

   // ��ͬ����
   @JsonIgnore
   private String content;

   // ��ͬ��ʼʱ��
   private String startDate;

   // ��ͬ����ʱ��
   private String endDate;

   // ��ͬʱ�ޣ�������
   private String period;

   // �Ӱ���Ҫ����
   private String applyOTFirst;

   // ÿ��Ӱ����ޣ�Сʱ��
   private String otLimitByDay;

   // ÿ�¼Ӱ����ޣ�Сʱ��
   private String otLimitByMonth;

   // �����ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱ�
   private String workdayOTItemId;

   // ��Ϣ�ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱ�
   private String weekendOTItemId;

   // �ڼ��ռӰ��Ŀ - ������Ŀ����Ϊ�Ӱ�
   private String holidayOTItemId;

   // ���ڷ�ʽ
   private String attendanceCheckType;

   // ��ְ����
   private String resignDate;

   // �����ʱ��
   private String lastWorkDate;

   // ��ְԭ��
   private String leaveReasons;

   // ��Ա״̬
   private String employStatus;

   // ����
   private String calendarId;

   // �Ű�
   private String shiftId;

   // ���ٹ���
   private String sickLeaveSalaryId;

   // �������Ͷ���ͬ��������Э��
   private String flag;

   // ����
   private String attachment;

   // �������ţ�Branch Id��
   private String branch;

   // �����ˣ�Position Id��
   private String owner;

   // �ɱ�����/ Ӫ�ղ���
   private String settlementBranch;

   // ����
   private String locked;

   // ����
   private String description;

   // н�깩Ӧ��
   private String salaryVendorId;

   // ��˰����ID
   private String incomeTaxBaseId;

   // ��˰˰��ID
   private String incomeTaxRangeHeaderId;

   // ������������0-6���£�
   private String probationMonth;

   // �����ڽ���ʱ��
   private String probationEndDate;

   // ҽ�����ʺ�
   private String medicalNumber;

   // �籣���ʺ�
   private String sbNumber;

   // �������ʺ�
   private String fundNumber;

   // ������
   private String hsNumber;

   // �Ƿ�����
   private String isRetained;

   // �Ƿ���ǩ
   private String isContinued;

   // �Ƿ���Ҫ��ǩ
   private String continueNeeded;

   // �Ƿ���㹤��
   private String payment;

   // �Ƿ�Ƹ
   private String hireAgain;

   /**
    *  For Application
    */
   // Ա�����
   private String employeeShortName;
   // EmployeeVO�Զ����ֶ�
   private String employeeRemark1;

   // ��Ա���
   private String employeeNo;

   // ��Ա��������
   private String employeeNameZH;

   // ��Ա����Ӣ��
   private String employeeNameEN;

   // ֤������
   private String certificateNumber;

   // ��ͬģ��
   private String templateName;

   // ְλ����
   private String positionName;

   // ֱ�߾�������
   private String lineManagerName;

   // ֱ�߾���������
   private String lineManagerNameZH;

   // ֱ�߾���Ӣ����
   private String lineManagerNameEN;

   // ����ʵ������
   private String entityName;

   // �ͻ�����
   private String clientName;

   // �ͻ����
   private String clientNumber;

   // �ͻ����ƣ����ģ�
   private String clientNameZH;

   // �ͻ����ƣ�Ӣ�ģ�
   private String clientNameEN;

   // ����
   private String orderName;

   // ��Ա����
   private String employeeName;

   // �����б�
   private String[] attachmentArray;

   // �·�
   private String monthly;

   // �ܴ�
   private String weekly;

   // ���뷽ʽ
   private String comeFrom;

   // ʡ�� - ���У����ڰ��ճ�����ȡ�籣�������ݣ�
   private String sbCityId;

   // �籣��ʼ���ڣ�������ȡ�������ڶε��籣�������ݣ�
   private String sbStartDate;

   // �籣�������ڣ�������ȡ�������ڶε��籣�������ݣ�
   private String sbEndDate;

   // �̱�����Id��������ȡ����״̬���̱��������ݣ�
   private String cbId;

   // �̱���ʼ���ڣ�������ȡ�������ڶε��̱��������ݣ�
   private String cbStartDate;

   // �̱��������ڣ�������ȡ�������ڶε��̱��������ݣ�
   private String cbEndDate;

   // ���ڽ���Ԥ��ʱ�䣬Ĭ�Ͽ��ڱ���������Э�������3�����ڴ�������Ч���·ݣ�������ȡ�������ڶεĿ��ڴ������ݣ����籣���̱�������Ҳ�ο�
   private String bufferDate;

   // ���� - ��н���ڣ���ʼ��
   private String circleStartDay;

   // ���� - ��н���ڣ�������
   private String circleEndDay;

   // ��ʼ���� - From
   private String startDateFrom;

   // ��ʼ���� - To
   private String startDateTo;

   // �������� - From
   private String endDateFrom;

   // �������� - To
   private String endDateTo;
   @JsonIgnore
   // ��Ա��ְ״̬
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // �Ӱ���Ҫ����
   private List< MappingVO > applyOTFirsts = new ArrayList< MappingVO >();
   @JsonIgnore
   // �Ӱ��Ŀ
   private List< MappingVO > otItems = new ArrayList< MappingVO >();
   @JsonIgnore
   //��ְԭ��
   private List< MappingVO > employeeLeaveReasons = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ʾ״̬<a>�ύ����
   private String isLink;
   @JsonIgnore
   // ��ʾ��������
   private String isShowHandle;

   // ��ͬ������
   private String contractNameZH;

   // ��ͬӢ����
   private String contractNameEN;

   // �����˹�������
   private String bizEmail;

   // ������˽������
   private String personalEmail;
   @JsonIgnore
   private String otherLink;
   @JsonIgnore
   // ��˰������
   private List< MappingVO > incomeTaxBases = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��˰˰��
   private List< MappingVO > incomeTaxRangeHeaders = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���ٹ���
   private List< MappingVO > sickLeaveSalaryIds = new ArrayList< MappingVO >();
   @JsonIgnore
   // н�깩Ӧ��
   private List< MappingVO > salaryVendors = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > changeReasons = new ArrayList< MappingVO >();

   //�籣��������
   private String sbType;

   // �籣״̬������ʽ
   private String[] sbStatusArray;

   // �籣����ID
   private String sbSolutionIds;

   // ���������ʱ����ʶ���Object��������Ϣ
   private String contractObject;
   @JsonIgnore
   // ����
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // ְ��
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   // �����Ͷ���ͬͬʱ���������Ͷ���ͬ���籣����
   private List< Object > employeeContractSBVOs = new ArrayList< Object >();
   @JsonIgnore
   // ְλ
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   // ������
   private List< MappingVO > probationMonths = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< ConstantVO > constantVOs;

   // ְλ��hrmȥEmployeeVO��
   private String _tempPositionIds;

   // ���ţ�hrmȥEmployeeVO��
   private String _tempBranchIds;

   // �����е��Ű�ID
   private String orderShiftId;

   // �����еĿ������ɷ�ʽ
   private String orderAttendanceGenerate;

   //��������
   private String currency;
   @JsonIgnore
   //��������
   private List< MappingVO > currencys = new ArrayList< MappingVO >();

   @JsonIgnore
   private TableDTO tableDTO;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" ) );
      this.applyOTFirsts = KANUtil.getMappings( this.getLocale(), "flag" );
      this.otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( request.getLocale().getLanguage(), super.getCorpId() );
      this.employeeLeaveReasons = KANUtil.getMappings( this.getLocale(), "business.employee.contract.LeaveReasons" );
      this.employStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.work.statuses" );

      if ( this.otItems != null )
      {
         this.otItems.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      this.incomeTaxBases = KANConstants.getIncomeTaxBaseVOs( request.getLocale().getLanguage() );
      this.incomeTaxRangeHeaders = KANConstants.getIncomeTaxRangeVOs( request.getLocale().getLanguage() );
      this.sickLeaveSalaryIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getSickLeaveSalary( request.getLocale().getLanguage(), super.getCorpId() );

      if ( this.sickLeaveSalaryIds != null )
      {
         this.sickLeaveSalaryIds.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );

      try
      {
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
         {
            this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getPositionGrades( this.getLocale().getLanguage(), super.getCorpId() );
         }
         else
         {
            this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getPositionGrades( this.getLocale().getLanguage() );
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      // �ⲿְλ
      if ( KANConstants.ROLE_HR_SERVICE.equals( this.getRole() ) )
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getEmployeePositions( request.getLocale().getLanguage() );
      }
      // �ڲ�ְλ
      else
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      }
      this.probationMonths = KANUtil.getMappings( this.getLocale(), "business.client.order.header.probationMonths" );

      this.lineManagerName = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? lineManagerNameZH : lineManagerNameEN;
      this.currencys = KANUtil.getMappings( this.getLocale(), "business.client.order.Currency" );
      this.changeReasons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      this.tableDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( EmployeeContractAction.ACCESS_ACTION_SERVICE_IN_HOUSE );
   }

   @Override
   @JsonIgnore
   public String getEncodedId() throws KANException
   {
      return encodedField( contractId );
   }

   @JsonIgnore
   public String getEncodedCalendarId() throws KANException
   {
      return encodedField( calendarId );
   }

   @JsonIgnore
   public String getDecodeFlag() throws KANException
   {
      if ( this.flag != null )
      {
         if ( this.flag.trim().equals( "1" ) )
         {
            return "�Ͷ���ͬ";
         }
         else if ( this.flag.trim().equals( "2" ) )
         {
            return "����Э��";
         }
      }

      return "";
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.orderId = "";
      this.department = "";
      this.positionId = "";
      this.additionalPosition = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.templateId = "";
      this.contractNo = "";
      this.masterContractId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.content = "";
      this.startDate = "";
      this.endDate = "";
      this.period = "";
      this.attachment = "";
      this.branch = "";
      this.owner = "0";
      this.settlementBranch = "0";
      this.locked = "";
      this.description = "";
      this.employeeName = "";
      this.resignDate = "";
      this.lastWorkDate = "";
      this.employStatus = "";
      this.leaveReasons = "";
      this.applyOTFirst = "0";
      this.otLimitByDay = "0";
      this.otLimitByMonth = "0";
      this.workdayOTItemId = "0";
      this.weekendOTItemId = "0";
      this.holidayOTItemId = "0";
      this.attendanceCheckType = "0";
      this.calendarId = "0";
      this.shiftId = "0";
      this.sickLeaveSalaryId = "0";
      this.description = "";
      this.lineManagerId = "";
      this.approveType = "0";
      this.incomeTaxBaseId = "0";
      this.incomeTaxRangeHeaderId = "0";
      this.salaryVendorId = "0";
      this.isRetained = "0";
      this.isContinued = "0";
      this.continueNeeded = "0";
      this.currency = "0";
      super.setRemark1( null );
      super.setRemark2( null );
      super.setRemark3( null );
      super.setRemark4( null );
      super.setRemark5( null );
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) object;
      this.employeeId = employeeContractVO.getEmployeeId();
      this.orderId = employeeContractVO.getOrderId();
      this.department = employeeContractVO.getDepartment();
      this.positionId = employeeContractVO.getPositionId();
      this.additionalPosition = employeeContractVO.getAdditionalPosition();
      this.entityId = employeeContractVO.getEntityId();
      this.businessTypeId = employeeContractVO.getBusinessTypeId();
      this.templateId = employeeContractVO.getTemplateId();
      this.lineManagerId = employeeContractVO.getLineManagerId();
      this.approveType = employeeContractVO.getApproveType();
      this.contractNo = employeeContractVO.getContractNo();
      this.nameZH = employeeContractVO.getNameZH();
      this.nameEN = employeeContractVO.getNameEN();
      this.content = employeeContractVO.getContent();
      this.startDate = employeeContractVO.getStartDate();
      this.endDate = employeeContractVO.getEndDate();
      this.period = employeeContractVO.getPeriod();
      this.flag = employeeContractVO.getFlag();
      this.attachment = employeeContractVO.getAttachment();
      this.branch = employeeContractVO.getBranch();
      this.owner = employeeContractVO.getOwner();
      this.settlementBranch = employeeContractVO.getSettlementBranch();
      this.locked = employeeContractVO.getLocked();
      this.description = employeeContractVO.getDescription();
      this.salaryVendorId = employeeContractVO.getSalaryVendorId();
      this.employeeName = employeeContractVO.getEmployeeName();
      this.resignDate = employeeContractVO.getResignDate();
      this.lastWorkDate = employeeContractVO.getLastWorkDate();
      this.employStatus = employeeContractVO.getEmployStatus();
      this.leaveReasons = employeeContractVO.getLeaveReasons();
      this.applyOTFirst = employeeContractVO.getApplyOTFirst();
      this.otLimitByDay = employeeContractVO.getOtLimitByDay();
      this.otLimitByMonth = employeeContractVO.getOtLimitByMonth();
      this.workdayOTItemId = employeeContractVO.getWorkdayOTItemId();
      this.holidayOTItemId = employeeContractVO.getHolidayOTItemId();
      this.weekendOTItemId = employeeContractVO.getWeekendOTItemId();
      this.attendanceCheckType = employeeContractVO.getAttendanceCheckType();
      this.calendarId = employeeContractVO.getCalendarId();
      this.shiftId = employeeContractVO.getShiftId();
      this.sickLeaveSalaryId = employeeContractVO.getSickLeaveSalaryId();
      this.description = employeeContractVO.getDescription();
      this.incomeTaxBaseId = employeeContractVO.getIncomeTaxBaseId();
      this.incomeTaxRangeHeaderId = employeeContractVO.getIncomeTaxRangeHeaderId();
      this.probationMonth = employeeContractVO.getProbationMonth();
      this.probationEndDate = employeeContractVO.getProbationEndDate();
      this.sbNumber = employeeContractVO.getSbNumber();
      this.medicalNumber = employeeContractVO.getMedicalNumber();
      this.fundNumber = employeeContractVO.getFundNumber();
      this.hsNumber = employeeContractVO.getHsNumber();
      this.isRetained = employeeContractVO.getIsRetained();
      this.isContinued = employeeContractVO.getIsContinued();
      this.continueNeeded = employeeContractVO.continueNeeded;
      this.attachmentArray = employeeContractVO.getAttachmentArray();
      this.currency = employeeContractVO.getCurrency();
      super.setStatus( employeeContractVO.getStatus() );
      super.setModifyBy( employeeContractVO.getModifyBy() );
      super.setModifyDate( new Date() );
      this.attachmentArray = employeeContractVO.getAttachmentArray();
   }

   public String getLeaveReasons()
   {
      return leaveReasons;
   }

   public void setLeaveReasons( String leaveReasons )
   {
      this.leaveReasons = leaveReasons;
   }

   public String getResignDate()
   {
      return KANUtil.filterEmpty( decodeDate( resignDate ) );
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getPositionId()
   {
      return KANUtil.filterEmpty( positionId );
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
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

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public String getContractNo()
   {
      return contractNo;
   }

   public void setContractNo( String contractNo )
   {
      this.contractNo = contractNo;
   }

   public String getMasterContractId()
   {
      return masterContractId;
   }

   public void setMasterContractId( String masterContractId )
   {
      this.masterContractId = masterContractId;
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

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
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

   public String getPeriod() throws KANException
   {
      this.period = Integer.toString( KANUtil.getGapMonth( startDate, endDate ) );
      return this.period;
   }

   public void setPeriod( String period )
   {
      this.period = period;
   }

   public String getFlag()
   {
      return KANUtil.filterEmpty( flag );
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
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

   public String getTemplateName()
   {
      return templateName;
   }

   public void setTemplateName( String templateName )
   {
      this.templateName = templateName;
   }

   public String getPositionName()
   {
      return positionName;
   }

   public void setPositionName( String positionName )
   {
      this.positionName = positionName;
   }

   public String getEntityName()
   {
      return entityName;
   }

   public void setEntityName( String entityName )
   {
      this.entityName = entityName;
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getComeFrom()
   {
      return comeFrom;
   }

   public void setComeFrom( String comeFrom )
   {
      this.comeFrom = comeFrom;
   }

   @JsonIgnore
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   @JsonIgnore
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getOrderName()
   {
      return orderName;
   }

   public void setOrderName( String orderName )
   {
      this.orderName = orderName;
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

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getApplyOTFirst()
   {
      return applyOTFirst;
   }

   public void setApplyOTFirst( String applyOTFirst )
   {
      this.applyOTFirst = applyOTFirst;
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

   public String getAttendanceCheckType()
   {
      return attendanceCheckType;
   }

   public void setAttendanceCheckType( String attendanceCheckType )
   {
      this.attendanceCheckType = attendanceCheckType;
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

   public String getName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return nameZH;
         }
         else
         {
            return nameEN;
         }
      }
      else
      {
         return nameZH;
      }
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

   public String getLocked()
   {
      return locked;
   }

   public void setLocked( String locked )
   {
      this.locked = locked;
   }

   public String getSbCityId()
   {
      return sbCityId;
   }

   public void setSbCityId( String sbCityId )
   {
      this.sbCityId = sbCityId;
   }

   public String getSbStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.sbStartDate ) );
   }

   public void setSbStartDate( String sbStartDate )
   {
      this.sbStartDate = sbStartDate;
   }

   public String getSbEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.sbEndDate ) );
   }

   public void setSbEndDate( String sbEndDate )
   {
      this.sbEndDate = sbEndDate;
   }

   public String getCbId()
   {
      return cbId;
   }

   public void setCbId( String cbId )
   {
      this.cbId = cbId;
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

   public List< MappingVO > getApplyOTFirsts()
   {
      return applyOTFirsts;
   }

   public void setApplyOTFirsts( List< MappingVO > applyOTFirsts )
   {
      this.applyOTFirsts = applyOTFirsts;
   }

   public List< MappingVO > getOtItems()
   {
      return otItems;
   }

   public void setOtItems( List< MappingVO > otItems )
   {
      this.otItems = otItems;
   }

   public String getCbStartDate()
   {
      return cbStartDate;
   }

   public void setCbStartDate( String cbStartDate )
   {
      this.cbStartDate = cbStartDate;
   }

   public String getCbEndDate()
   {
      return cbEndDate;
   }

   public void setCbEndDate( String cbEndDate )
   {
      this.cbEndDate = cbEndDate;
   }

   public final String getBufferDate()
   {
      return bufferDate;
   }

   public final void setBufferDate( String bufferDate )
   {
      this.bufferDate = bufferDate;
   }

   public String getLineManagerId()
   {
      return KANUtil.filterEmpty( lineManagerId );
   }

   public void setLineManagerId( String lineManagerId )
   {
      this.lineManagerId = lineManagerId;
   }

   public String getApproveType()
   {
      return KANUtil.filterEmpty( approveType );
   }

   public void setApproveType( String approveType )
   {
      this.approveType = approveType;
   }

   public String getLineManagerName()
   {
      return lineManagerName;
   }

   public void setLineManagerName( String lineManagerName )
   {
      this.lineManagerName = lineManagerName;
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

   public final String getClientNameZH()
   {
      return clientNameZH;
   }

   public final void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public final String getClientNameEN()
   {
      return clientNameEN;
   }

   public final void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   @JsonIgnore
   public String getEncodedrderId() throws KANException
   {
      return encodedField( this.orderId );
   }

   @JsonIgnore
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( this.orderId );
   }

   @JsonIgnore
   public String getEncodedlientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   @JsonIgnore
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   public final String getStartDateFrom()
   {
      return startDateFrom;
   }

   public final void setStartDateFrom( String startDateFrom )
   {
      this.startDateFrom = startDateFrom;
   }

   public final String getStartDateTo()
   {
      return startDateTo;
   }

   public final void setStartDateTo( String startDateTo )
   {
      this.startDateTo = startDateTo;
   }

   public final String getEndDateFrom()
   {
      return endDateFrom;
   }

   public final void setEndDateFrom( String endDateFrom )
   {
      this.endDateFrom = endDateFrom;
   }

   public final String getEndDateTo()
   {
      return endDateTo;
   }

   public final void setEndDateTo( String endDateTo )
   {
      this.endDateTo = endDateTo;
   }

   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   public String getIsLink() throws KANException
   {
      String str = "";
      if ( "0".equals( this.templateId ) && this.attachmentArray == null )
      {
         str = "alert('" + KANUtil.getProperty( this.getLocale(), "popup.not.template.or.attachment" ) + "');return false;";
      }
      if ( "1".equals( super.getStatus() ) || "4".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<a href='#'onclick=\"" + str + "link('employeeContractAction.do?proc=submit_object&flag=2&contractId=" + getEncodedId() + "')\">"
               + KANUtil.getProperty( getLocale(), "button.submit" ) + "</a>";
      }
      return isLink;
   }

   public String getOtherLink()
   {
      if ( KANUtil.filterEmpty( super.getWorkflowId() ) != null )
      {
         otherLink = "&nbsp;&nbsp;<img src='images/magnifer.png' title='" + KANUtil.getProperty( getLocale(), "img.title.tips.view.detials" ) + "' onclick=popupWorkflow('"
               + this.getWorkflowId() + "'); style=\"cursor: pointer\" />";
      }
      return otherLink;
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   // ����entityId ��ȡ������
   public String getWorkPlace() throws KANException
   {
      final EntityVO entityVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntityVOByEntityId( entityId );
      if ( entityVO != null )
      {
         entityVO.setLocations( KANConstants.getKANAccountConstants( super.getAccountId() ).getLocations( this.getLocale().getLanguage(), getCorpId() ) );
      }
      return entityVO == null ? "" : entityVO.getDecodeLocationId();
   }

   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeApproveType() throws KANException
   {
      return decodeField( this.approveType, KANUtil.getMappings( this.getLocale(), "business.employee.contract.approveType" ) );
   }

   public String getDecodeLocked() throws KANException
   {
      return decodeField( this.locked, KANUtil.getMappings( getLocale(), "flag" ) );
   }

   public String getIsShowHandle() throws KANException
   {
      isShowHandle = "&nbsp;&nbsp;<a title='" + KANUtil.getProperty( getLocale(), "link.print.tips" ) + "' onclick=\"showExportResign('" + getContractId() + "','"
            + super.getStatus() + "');\">" + KANUtil.getProperty( getLocale(), "link.print" ) + "</a>";
      // ���ڹ���������ʾ��ְ��������
      if ( KANUtil.filterEmpty( getWorkflowId() ) == null && ( "3".equals( super.getStatus() ) || "5".equals( super.getStatus() ) || "6".equals( super.getStatus() ) ) )
      {
         isShowHandle = isShowHandle + "@&nbsp;&nbsp;<a title='" + KANUtil.getProperty( getLocale(), "link.handle.tips" ) + "' onclick=\"handleContract('" + getContractId()
               + "');\">" + KANUtil.getProperty( getLocale(), "link.handle" ) + "</a>";
      }
      return isShowHandle;
   }

   public void setIsShowHandle( String isShowHandle )
   {
      this.isShowHandle = isShowHandle;
   }

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public String getDecodeTemplateId() throws KANException
   {
      return decodeField( this.templateId, KANConstants.getKANAccountConstants( getAccountId() ).getLaborContractTemplates( getLocale().getLanguage(), getCorpId() ) );
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

   public String getBizEmail()
   {
      return bizEmail;
   }

   public void setBizEmail( String bizEmail )
   {
      this.bizEmail = bizEmail;
   }

   public String getPersonalEmail()
   {
      return personalEmail;
   }

   public void setPersonalEmail( String personalEmail )
   {
      this.personalEmail = personalEmail;
   }

   public String getProbationMonth()
   {
      return probationMonth;
   }

   public void setProbationMonth( String probationMonth )
   {
      this.probationMonth = probationMonth;
   }

   public String getIncomeTaxBaseId()
   {
      return incomeTaxBaseId;
   }

   public void setIncomeTaxBaseId( String incomeTaxBaseId )
   {
      this.incomeTaxBaseId = incomeTaxBaseId;
   }

   public String getIncomeTaxRangeHeaderId()
   {
      return incomeTaxRangeHeaderId;
   }

   public void setIncomeTaxRangeHeaderId( String incomeTaxRangeHeaderId )
   {
      this.incomeTaxRangeHeaderId = incomeTaxRangeHeaderId;
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

   public List< MappingVO > getSickLeaveSalaryIds()
   {
      return sickLeaveSalaryIds;
   }

   public void setSickLeaveSalaryIds( List< MappingVO > sickLeaveSalaryIds )
   {
      this.sickLeaveSalaryIds = sickLeaveSalaryIds;
   }

   public void setOtherLink( String otherLink )
   {
      this.otherLink = otherLink;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

   public String getProbationEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( probationEndDate ) );
   }

   public void setProbationEndDate( String probationEndDate )
   {
      this.probationEndDate = probationEndDate;
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

   public List< MappingVO > getChangeReasons()
   {
      return changeReasons;
   }

   public void setChangeReasons( List< MappingVO > changeReasons )
   {
      this.changeReasons = changeReasons;
   }

   public String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public String[] getSbStatusArray()
   {
      return sbStatusArray;
   }

   public void setSbStatusArray( String[] sbStatusArray )
   {
      this.sbStatusArray = sbStatusArray;
   }

   public String getMedicalNumber()
   {
      return medicalNumber;
   }

   public void setMedicalNumber( String medicalNumber )
   {
      this.medicalNumber = medicalNumber;
   }

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getFundNumber()
   {
      return fundNumber;
   }

   public void setFundNumber( String fundNumber )
   {
      this.fundNumber = fundNumber;
   }

   public String getAdditionalPosition()
   {
      return additionalPosition;
   }

   public void setAdditionalPosition( String additionalPosition )
   {
      this.additionalPosition = additionalPosition;
   }

   public String getHsNumber()
   {
      return hsNumber;
   }

   public void setHsNumber( String hsNumber )
   {
      this.hsNumber = hsNumber;
   }

   public final String getIsRetained()
   {
      return isRetained;
   }

   public final void setIsRetained( String isRetained )
   {
      this.isRetained = isRetained;
   }

   public final String getIsContinued()
   {
      return isContinued;
   }

   public final void setIsContinued( String isContinued )
   {
      this.isContinued = isContinued;
   }

   public final String getContinueNeeded()
   {
      return continueNeeded;
   }

   public final void setContinueNeeded( String continueNeeded )
   {
      this.continueNeeded = continueNeeded;
   }

   public String getSbSolutionIds()
   {
      return sbSolutionIds;
   }

   public void setSbSolutionIds( String sbSolutionIds )
   {
      this.sbSolutionIds = sbSolutionIds;
   }

   public String getContractObject()
   {
      setContractObject( "true" );
      return contractObject;
   }

   public void setContractObject( String contractObject )
   {
      this.contractObject = contractObject;
   }

   public List< MappingVO > getProbationMonths()
   {
      return probationMonths;
   }

   public void setProbationMonths( List< MappingVO > probationMonths )
   {
      this.probationMonths = probationMonths;
   }

   public String getLineManagerNameEN()
   {
      return lineManagerNameEN;
   }

   public void setLineManagerNameEN( String lineManagerNameEN )
   {
      this.lineManagerNameEN = lineManagerNameEN;
   }

   public String getLineManagerNameZH()
   {
      return lineManagerNameZH;
   }

   public void setLineManagerNameZH( String lineManagerNameZH )
   {
      this.lineManagerNameZH = lineManagerNameZH;
   }

   // ���뵥λ��hrm_tempBranchIds��
   public String getDecodeDepartment() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getCorpId() ) == null )
      {
         return department;
      }
      else
      {
         String returnStr = "";
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
         if ( accountConstants != null && KANUtil.filterEmpty( _tempBranchIds ) != null )
         {
            for ( String branchId : _tempBranchIds.split( "," ) )
            {
               final BranchVO branchVO = accountConstants.getBranchVOByBranchId( branchId );
               if ( branchVO != null )
               {
                  if ( KANUtil.filterEmpty( returnStr ) == null )
                  {
                     if ( getLocale() != null && "en".equalsIgnoreCase( getLocale().getLanguage() ) )
                     {
                        returnStr = branchVO.getNameEN();
                     }
                     else
                     {
                        returnStr = branchVO.getNameZH();
                     }
                  }
                  else
                  {
                     if ( getLocale() != null && "en".equalsIgnoreCase( getLocale().getLanguage() ) )
                     {
                        returnStr = returnStr + "��" + branchVO.getNameEN();
                     }
                     else
                     {
                        returnStr = returnStr + "��" + branchVO.getNameZH();
                     }
                  }
               }
            }
         }

         return returnStr;
      }
   }

   public String getDecode_tempBranchIds() throws KANException
   {
      return getDecodeDepartment();
   }

   // �����λ��hrm_tempPositionIds��
   public String getDecodePositionId()
   {
      String returnString = "";
      String positionIds = KANUtil.filterEmpty( super.getCorpId() ) == null ? positionId : _tempPositionIds;
      if ( KANUtil.filterEmpty( positionIds ) != null )
      {
         final String[] positionArray = positionIds.split( "," );
         if ( positionArray != null && positionArray.length > 0 )
         {
            for ( String position : positionArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( position, positions );
               }
               else
               {
                  returnString = returnString + "��" + decodeField( position, positions );
               }
            }
         }
      }

      return returnString;
   }

   public String getDecode_tempPositionIds()
   {
      return getDecodePositionId();
   }

   // �ɱ�����
   public String getDecodeSettlementBranch()
   {
      return decodeField( this.getSettlementBranch(), this.branchs, true );
   }

   // ֱ�߾���
   public String getDecodeLineManagerName()
   {
      return lineManagerName;
   }

   public List< Object > getEmployeeContractSBVOs()
   {
      return employeeContractSBVOs;
   }

   public void setEmployeeContractSBVOs( List< Object > employeeContractSBVOs )
   {
      this.employeeContractSBVOs = employeeContractSBVOs;
   }

   public String get_tempPositionIds()
   {
      return _tempPositionIds;
   }

   public void set_tempPositionIds( String _tempPositionIds )
   {
      this._tempPositionIds = _tempPositionIds;
   }

   public String get_tempBranchIds()
   {
      return _tempBranchIds;
   }

   public void set_tempBranchIds( String _tempBranchIds )
   {
      this._tempBranchIds = _tempBranchIds;
   }

   public List< ConstantVO > getConstantVOs()
   {
      return constantVOs;
   }

   public void setConstantVOs( List< ConstantVO > constantVOs )
   {
      this.constantVOs = constantVOs;
   }

   public String getOrderShiftId()
   {
      return orderShiftId;
   }

   public void setOrderShiftId( String orderShiftId )
   {
      this.orderShiftId = orderShiftId;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public String getOrderAttendanceGenerate()
   {
      return orderAttendanceGenerate;
   }

   public void setOrderAttendanceGenerate( String orderAttendanceGenerate )
   {
      this.orderAttendanceGenerate = orderAttendanceGenerate;
   }

   public String getLastWorkDate()
   {
      return KANUtil.filterEmpty( decodeDate( lastWorkDate ) );
   }

   public void setLastWorkDate( String lastWorkDate )
   {
      this.lastWorkDate = lastWorkDate;
   }

   public String getPayment()
   {
      return payment;
   }

   public void setPayment( String payment )
   {
      this.payment = payment;
   }

   public String getHireAgain()
   {
      return hireAgain;
   }

   public void setHireAgain( String hireAgain )
   {
      this.hireAgain = hireAgain;
   }

   // �ϼ�����ID
   public String getParentBranchId()
   {
      String parentBranchId = "";
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeId );
         if ( staffDTOs != null && staffDTOs.size() == 1 )
         {
            final PositionVO positionVO = accountConstants.getMainPositionVOByStaffId( staffDTOs.get( 0 ).getStaffVO().getStaffId() );
            if ( positionVO != null )
            {
               final BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {
                  parentBranchId = branchVO.getParentBranchId();
               }
            }
         }
      }
      return parentBranchId;

   }

   public String getParentBranchName()
   {
      String parentBranchName = "";
      final String parentBranchId = getParentBranchId();
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( parentBranchId );
         if ( parentBranchVO != null )
         {
            if ( getLocale() != null && "en".equalsIgnoreCase( getLocale().getLanguage() ) )
            {
               parentBranchName = parentBranchVO.getNameEN();
            }
            else
            {
               parentBranchName = parentBranchVO.getNameZH();
            }
         }
      }
      return parentBranchName;
   }

   public String getDeocodeDepartment()
   {
      String str = "";
      final String branch = getSettlementBranch();
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final BranchVO branchVO = accountConstants.getBranchVOByBranchId( branch );
         if ( branchVO != null )
         {
            if ( getLocale() != null && "en".equalsIgnoreCase( getLocale().getLanguage() ) )
            {
               str = branchVO.getNameEN();
            }
            else
            {
               str = branchVO.getNameZH();
            }
         }
      }
      return str;
   }

   //ֱ�߾���
   public String getLineManager()
   {
      String lineManager = "";
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeId );
         if ( staffDTOs != null && staffDTOs.size() == 1 )
         {
            final PositionVO positionVO = accountConstants.getMainPositionVOByStaffId( staffDTOs.get( 0 ).getStaffVO().getStaffId() );
            if ( positionVO != null )
            {
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getParentPositionId() );
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( int i = 0; i < parentStaffDTOs.size(); i++ )
                  {
                     if ( getLocale() != null && "en".equalsIgnoreCase( getLocale().getLanguage() ) )
                     {
                        if ( i == 0 )
                        {
                           lineManager += accountConstants.getStaffNameByStaffId( parentStaffDTOs.get( i ).getStaffVO().getStaffId(), true );
                        }
                        else
                        {
                           lineManager += ( "��" + accountConstants.getStaffNameByStaffId( parentStaffDTOs.get( i ).getStaffVO().getStaffId(), true ) );
                        }
                     }
                     else
                     {
                        if ( i == 0 )
                        {
                           lineManager += accountConstants.getStaffNameByStaffId( parentStaffDTOs.get( i ).getStaffVO().getStaffId(), true );
                        }
                        else
                        {
                           lineManager += ( "��" + accountConstants.getStaffNameByStaffId( parentStaffDTOs.get( i ).getStaffVO().getStaffId(), true ) );
                        }
                     }
                  }
               }
            }
         }
      }
      return lineManager;
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

   public List< MappingVO > getEmployeeLeaveReasons()
   {
      return employeeLeaveReasons;
   }

   public void setEmployeeLeaveReasons( List< MappingVO > employeeLeaveReasons )
   {
      this.employeeLeaveReasons = employeeLeaveReasons;
   }

   public String getEmployeeShortName()
   {
      return employeeShortName;
   }

   public void setEmployeeShortName( String employeeShortName )
   {
      this.employeeShortName = employeeShortName;
   }

   public String getEmployeeRemark1()
   {
      return employeeRemark1;
   }

   public void setEmployeeRemark1( String employeeRemark1 )
   {
      this.employeeRemark1 = employeeRemark1;
   }

   public String getShortName()
   {
      if ( StringUtils.isBlank( employeeRemark1 ) )
         return "";
      JSONObject jsonObject = JSONObject.fromObject( employeeRemark1 );

      if ( KANUtil.filterEmpty( "jiancheng" ) != null && jsonObject != null )
      {
         return ( String ) jsonObject.get( "jiancheng" );
      }

      return "";
   }

   //----------------------------------\\

   public String getBUFunctionZH()
   {
      return getDecodeBranchIdsByCondition( getParentBranchId(), "zh" );
   }

   public String getBUFunctionEN()
   {
      return getDecodeBranchIdsByCondition( getParentBranchId(), "en" );
   }

   public String getDepartmentZH()
   {
      return getDecodeBranchIdsByCondition( _tempBranchIds, "zh" );
   }

   public String getDepartmentEN()
   {
      return getDecodeBranchIdsByCondition( _tempBranchIds, "en" );
   }

   public String getLineManagerZH()
   {
      return getDecodeStaffIdsByCondition( "zh" );
   }

   public String getLineManagerEN()
   {
      return getDecodeStaffIdsByCondition( "en" );
   }

   // ����branchIds
   private String getDecodeBranchIdsByCondition( final String branchIds, final String language )
   {
      if ( KANUtil.filterEmpty( branchIds ) == null )
      {
         return "";
      }
      else
      {
         String returnStr = "";
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
         if ( accountConstants != null )
         {
            for ( String branchId : branchIds.split( "," ) )
            {
               final BranchVO branchVO = accountConstants.getBranchVOByBranchId( branchId );
               if ( branchVO != null )
               {
                  if ( KANUtil.filterEmpty( returnStr ) == null )
                  {
                     if ( "en".equalsIgnoreCase( language ) )
                     {
                        returnStr = branchVO.getNameEN();
                     }
                     else
                     {
                        returnStr = branchVO.getNameZH();
                     }
                  }
                  else
                  {
                     if ( "en".equalsIgnoreCase( language ) )
                     {
                        returnStr = returnStr + "��" + branchVO.getNameEN();
                     }
                     else
                     {
                        returnStr = returnStr + "��" + branchVO.getNameZH();
                     }
                  }
               }
            }
         }

         return returnStr;
      }
   }

   //ֱ�߾���
   private String getDecodeStaffIdsByCondition( final String langguage )
   {
      String lineManager = "";
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeId );
         if ( staffDTOs != null && staffDTOs.size() == 1 )
         {
            final PositionVO positionVO = accountConstants.getMainPositionVOByStaffId( staffDTOs.get( 0 ).getStaffVO().getStaffId() );
            if ( positionVO != null )
            {
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getParentPositionId() );
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( int i = 0; i < parentStaffDTOs.size(); i++ )
                  {
                     if ( "en".equalsIgnoreCase( langguage ) )
                     {
                        if ( i == 0 )
                        {
                           lineManager += parentStaffDTOs.get( i ).getStaffVO().getNameEN();
                        }
                        else
                        {
                           lineManager += ( "��" + parentStaffDTOs.get( i ).getStaffVO().getNameEN() );
                        }
                     }
                     else
                     {
                        if ( i == 0 )
                        {
                           lineManager += parentStaffDTOs.get( i ).getStaffVO().getNameZH();
                        }
                        else
                        {
                           lineManager += ( "��" + parentStaffDTOs.get( i ).getStaffVO().getNameZH() );
                        }
                     }
                  }
               }
            }
         }
      }

      return lineManager;
   }

   public String getPublicCode()
   {
      String publicCode = "";
      publicCode = Cryptogram.getPublicCode( this.employeeId );
      return publicCode;
   }

   /**
    * Decode method list
    */
   // �������
   public String getDecodeCurrency()
   {
      return decodeField( currency, currencys );
   }

   // ���빤���ռӰ��Ŀ
   public String getDecodeWorkdayOTItemId()
   {
      return decodeField( workdayOTItemId, otItems );
   }

   // ����˫���ռӰ��Ŀ
   public String getDecodeWeekendOTItemId()
   {
      return decodeField( weekendOTItemId, otItems );
   }

   // ����ڼ��ռӰ��Ŀ
   public String getDecodeHolidayOTItemId()
   {
      return decodeField( holidayOTItemId, otItems );
   }

   // ���뷨��ʵ��
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( this.entityId, KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( getLocale().getLanguage(), super.getCorpId() ) );
   }

   // ����Ӱ��Ƿ���Ҫ����
   public String getDecodeApplyOTFirst()
   {
      return decodeField( applyOTFirst, applyOTFirsts );
   }

   // ���뿼����˷�ʽ
   public String getDecodeAttendanceCheckType() throws KANException
   {
      return decodeField( this.attendanceCheckType, KANUtil.getMappings( this.getLocale(), "business.client.order.header.attendanceCheckTypes" ) );
   }

   // ���뿼�ڹ�������
   public String getDecodeCalendarId() throws KANException
   {
      return decodeField( this.calendarId, KANConstants.getKANAccountConstants( getAccountId() ).getCalendar( getLocale().getLanguage(), super.getCorpId() ) );
   }

   // ���뿼���Ű෽��
   public String getDecodeShiftId() throws KANException
   {
      if ( KANUtil.filterEmpty( shiftId, "0" ) == null )
      {
         return decodeField( this.orderShiftId, KANConstants.getKANAccountConstants( getAccountId() ).getShift( getLocale().getLanguage(), super.getCorpId() ) );
      }

      return decodeField( this.shiftId, KANConstants.getKANAccountConstants( getAccountId() ).getShift( getLocale().getLanguage(), super.getCorpId() ) );
   }

   // ���벡�ٹ��ʷ���
   public String getDecodeSickLeaveSalaryId()
   {
      return decodeField( sickLeaveSalaryId, sickLeaveSalaryIds );
   }

   // ����ԽӲ���
   public String getDecodeBranch() throws KANException
   {
      return decodeField( this.branch, KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( getLocale().getLanguage(), getCorpId() ), true );
   }

   // ����Խ���
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   // �����Ӷ״̬
   public String getDecodeEmployStatus() throws KANException
   {
      return decodeField( this.employStatus, this.getEmployStatuses() );
   }

   // ����н�깩Ӧ��
   public String getDecodeSalaryVendorId()
   {
      return decodeField( salaryVendorId, salaryVendors );
   }

   // �����˰����
   public String getDecodeIncomeTaxBaseId()
   {
      return decodeField( incomeTaxBaseId, incomeTaxBases );
   }

   // �����˰˰��
   public String getDecodeIncomeTaxRangeHeaderId()
   {
      return decodeField( incomeTaxRangeHeaderId, incomeTaxRangeHeaders );
   }

   // ��������������
   public String getDecodeProbationMonth()
   {
      return decodeField( this.getProbationMonth(), this.probationMonths );
   }

   // �����Ƿ���㹤��
   public String getDecodePayment()
   {
      return decodeField( payment, super.getFlags() );
   }

   // �����Ƿ�Ƹ
   public String getDecodeHireAgain()
   {
      return decodeField( hireAgain, super.getFlags() );
   }

   // �춯ԭ��
   public String getDecodeChangeReason()
   {
      return decodeField( super.getRemark3(), changeReasons );
   }

   // ����jobrole
   public String getDecodeJobrole() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "jobrole" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "13365" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "jobrole" ).toString(), mappingVOs );
      }

      return "";
   }

   // ����֧����ʽ
   public String getDecodeZhifufangshi() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "zhifufangshi" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "13351" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "zhifufangshi" ).toString(), mappingVOs );
      }

      return "";
   }

   // ���� ��Ա����
   public String getDecodeGuyuanleixing() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "guyuanleixinger" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "14001" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "guyuanleixinger" ).toString(), mappingVOs );
      }

      return "";
   }

}
