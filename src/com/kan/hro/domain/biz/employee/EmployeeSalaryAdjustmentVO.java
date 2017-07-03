package com.kan.hro.domain.biz.employee;

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

public class EmployeeSalaryAdjustmentVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String salaryAdjustmentId;

   private String employeeId;

   private String contractId;

   private String itemId;

   private String itemNameZH;

   private String itemNameEN;

   private String oldBase;

   private String oldStartDate;

   private String oldEndDate;

   private String newBase;

   private String newStartDate;

   private String newEndDate;

   private String effectiveDate;

   private String description;

   private String employeeNo;

   private String employeeNameZH;

   private String employeeNameEN;

   private String employeeContractNameZH;

   private String employeeContractNameEN;

   private String employeeContractStartDate;

   private String employeeContractEndDate;

   private String employeeCertificateNumber;

   private String employeeSalaryId;

   private int submitFlag;

   /*
    * For App
    */
   @JsonIgnore
   private List< MappingVO > changeReasons = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > salaryItems = new ArrayList< MappingVO >();

   /***
    * For Application (Workflow)
    */
   // 合同开始时间
   @JsonIgnore
   private String startDate;
   // 合同结束时间
   @JsonIgnore
   private String endDate;
   // 职位
   @JsonIgnore
   private String _tempPositionIds;
   // 部门
   @JsonIgnore
   private String _tempBranchIds;
   // 成本中心
   @JsonIgnore
   private String settlementBranch;
   // 法务实体
   @JsonIgnore
   private String entityId;
   // 工作流审核时，标识这个Object是派送信息
   @JsonIgnore
   private String contractObject;
   @JsonIgnore
   // 对接人
   private String owner;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( salaryAdjustmentId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.contractId = "";
      this.itemId = "";
      this.oldBase = "";
      this.oldStartDate = "";
      this.oldEndDate = "";
      this.newBase = "";
      this.newStartDate = "";
      this.newEndDate = "";
      this.effectiveDate = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.employee.salary.adjustment.statuses" ) );
      this.changeReasons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      this.salaryItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getSalaryItems( super.getLocale().getLanguage(), super.getCorpId() );
      this.salaryItems.add( 0, getEmptyMappingVO() );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) object;
      this.employeeId = employeeSalaryAdjustmentVO.employeeId;
      this.contractId = employeeSalaryAdjustmentVO.contractId;
      this.employeeSalaryId = employeeSalaryAdjustmentVO.employeeSalaryId;
      this.oldBase = employeeSalaryAdjustmentVO.oldBase;
      this.oldStartDate = employeeSalaryAdjustmentVO.oldStartDate;
      this.oldEndDate = employeeSalaryAdjustmentVO.oldEndDate;
      this.newBase = employeeSalaryAdjustmentVO.newBase;
      this.newStartDate = employeeSalaryAdjustmentVO.newStartDate;
      this.newEndDate = employeeSalaryAdjustmentVO.newEndDate;
      this.effectiveDate = employeeSalaryAdjustmentVO.effectiveDate;
      this.submitFlag = employeeSalaryAdjustmentVO.submitFlag;
      this.description = employeeSalaryAdjustmentVO.description;
      super.setRemark3( employeeSalaryAdjustmentVO.getRemark3() );
      super.setModifyDate( new Date() );
      this.itemId = employeeSalaryAdjustmentVO.getItemId();
      this.itemNameZH = employeeSalaryAdjustmentVO.getItemNameZH();
      this.itemNameEN = employeeSalaryAdjustmentVO.getItemNameEN();
   }

   public String getOldStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.oldStartDate ) );
   }

   public void setOldStartDate( String oldStartDate )
   {
      this.oldStartDate = oldStartDate;
   }

   public String getOldEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.oldEndDate ) );
   }

   public void setOldEndDate( String oldEndDate )
   {
      this.oldEndDate = oldEndDate;
   }

   public String getEffectiveDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.effectiveDate ) );
   }

   public void setEffectiveDate( String effectiveDate )
   {
      this.effectiveDate = effectiveDate;
   }

   public String getNewStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.newStartDate ) );
   }

   public void setNewStartDate( String newStartDate )
   {
      this.newStartDate = newStartDate;
   }

   public String getNewEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.newEndDate ) );
   }

   public void setNewEndDate( String newEndDate )
   {
      this.newEndDate = newEndDate;
   }

   public String getSalaryAdjustmentId()
   {
      return salaryAdjustmentId;
   }

   public void setSalaryAdjustmentId( String salaryAdjustmentId )
   {
      this.salaryAdjustmentId = salaryAdjustmentId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getContractId()
   {
      return contractId;
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

   public String getOldBase()
   {
      return formatNumber( KANUtil.filterEmpty( oldBase ) );
   }

   public void setOldBase( String oldBase )
   {
      this.oldBase = oldBase;
   }

   public String getNewBase()
   {
      return formatNumber( KANUtil.filterEmpty( newBase ) );
   }

   public void setNewBase( String newBase )
   {
      this.newBase = newBase;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public String getEmployeeCertificateNumber()
   {
      return employeeCertificateNumber;
   }

   public void setEmployeeCertificateNumber( String employeeCertificateNumber )
   {
      this.employeeCertificateNumber = employeeCertificateNumber;
   }

   public int getSubmitFlag()
   {
      return submitFlag;
   }

   public void setSubmitFlag( int submitFlag )
   {
      this.submitFlag = submitFlag;
   }

   public String getEmployeeName()
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

   public String getEmployeeContractNameZH()
   {
      return employeeContractNameZH;
   }

   public void setEmployeeContractNameZH( String employeeContractNameZH )
   {
      this.employeeContractNameZH = employeeContractNameZH;
   }

   public String getEmployeeContractNameEN()
   {
      return employeeContractNameEN;
   }

   public String getEmployeeContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.employeeContractStartDate ) );
   }

   public void setEmployeeContractStartDate( String employeeContractStartDate )
   {
      this.employeeContractStartDate = employeeContractStartDate;
   }

   public String getEmployeeContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.employeeContractEndDate ) );
   }

   public void setEmployeeContractEndDate( String employeeContractEndDate )
   {
      this.employeeContractEndDate = employeeContractEndDate;
   }

   public void setEmployeeContractNameEN( String employeeContractNameEN )
   {
      this.employeeContractNameEN = employeeContractNameEN;
   }

   public String getEmployeeContractName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getEmployeeContractNameZH();
         }
         else
         {
            return this.getEmployeeContractNameEN();
         }
      }
      else
      {
         return this.getEmployeeContractNameZH();
      }
   }

   public String getEmployeeSalaryId()
   {
      return KANUtil.filterEmpty( employeeSalaryId );
   }

   public void setEmployeeSalaryId( String employeeSalaryId )
   {
      this.employeeSalaryId = employeeSalaryId;
   }

   public String getDecodeItemId()
   {
      if ( this.getLocale() != null )
      {
         return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      else
      {
         return "";
      }
   }

   // 0:请选择##1:新建##2:提交-待批准##3:批准##4:拒绝##5:已生效
   @Override
   public String getDecodeStatus()
   {
      if ( KANUtil.filterEmpty( super.getWorkflowStatus() ) == null )
         return decodeField( super.getStatus(), KANUtil.getMappings( super.getLocale(), "business.employee.salary.adjustment.statuses" ) );

      return decodeField( super.getWorkflowStatus(), KANUtil.getMappings( super.getLocale(), "business.employee.salary.adjustment.statuses" ) );
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

   public List< MappingVO > getChangeReasons()
   {
      return changeReasons;
   }

   public void setChangeReasons( List< MappingVO > changeReasons )
   {
      this.changeReasons = changeReasons;
   }

   public List< MappingVO > getSalaryItems()
   {
      return salaryItems;
   }

   public void setSalaryItems( List< MappingVO > salaryItems )
   {
      this.salaryItems = salaryItems;
   }

   public String getDecodeChangeReason()
   {
      return decodeField( super.getRemark3(), changeReasons );
   }

   public String getStartDate()
   {
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate;
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
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

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   // 证件号码
   public String getCertificateNumber()
   {
      return employeeCertificateNumber;
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

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getItemName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getItemNameZH();
         }
         else
         {
            return this.getItemNameEN();
         }
      }
      else
      {
         return this.getItemNameZH();
      }
   }

}
