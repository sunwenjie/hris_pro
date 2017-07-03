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

public class EmployeePositionChangeVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String positionChangeId;

   private String staffId;

   private String effectiveDate;

   private String newStartDate;

   private String newEndDate;

   private String isImmediatelyEffective;

   private String description;

   private String employeeNo;

   private String employeeCertificateNumber;

   private String oldStartDate;

   private String oldEndDate;

   private String oldStaffPositionRelationId;

   private String employeeId;
   private String employeeNameZH;
   private String employeeNameEN;

   private String oldPositionId;
   private String oldPositionNameZH;
   private String oldPositionNameEN;

   private String oldBranchId;
   private String oldBranchNameZH;
   private String oldBranchNameEN;

   private String newPositionId;
   private String newPositionNameZH;
   private String newPositionNameEN;

   private String newBranchId;
   private String newBranchNameZH;
   private String newBranchNameEN;

   private String oldParentBranchId;
   private String oldParentBranchNameZH;
   private String oldParentBranchNameEN;

   private String oldParentPositionId;
   private String oldParentPositionNameZH;
   private String oldParentPositionNameEN;

   private String oldPositionGradeId;
   private String oldPositionGradeNameZH;
   private String oldPositionGradeNameEN;

   private String oldParentPositionOwners;
   private String oldParentPositionOwnersZH;
   private String oldParentPositionOwnersEN;

   //new 

   private String newParentBranchId;
   private String newParentBranchNameZH;
   private String newParentBranchNameEN;

   private String newParentPositionId;
   private String newParentPositionNameZH;
   private String newParentPositionNameEN;

   private String newPositionGradeId;
   private String newPositionGradeNameZH;
   private String newPositionGradeNameEN;

   private String newParentPositionOwners;
   private String newParentPositionOwnersZH;
   private String newParentPositionOwnersEN;

   private String positionStatus;

   private String searchEmployeeId;

   private int submitFlag;
   @JsonIgnore
   private List< MappingVO > positionStatues = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > changeReasons = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > jobRoles = new ArrayList< MappingVO >();

   // 下属联动？
   private String isChildChange;

   /***
    * For Application (Workflow)
    */
   // 生效时间开始
   @JsonIgnore
   private String effectiveDateStart;
   // 生效时间结束
   @JsonIgnore
   private String effectiveDateEnd;
   // 劳动合同ID 
   @JsonIgnore
   private String contractId;
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
      return encodedField( positionChangeId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.staffId = "";
      this.oldBranchId = "";
      this.oldPositionId = "";
      this.oldStartDate = "";
      this.oldEndDate = "";
      this.newBranchId = "";
      this.newPositionId = "";
      this.newStartDate = "";
      this.newEndDate = "";
      this.effectiveDate = "";
      this.isImmediatelyEffective = "";
      this.description = "";
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.employee.position.change.statuses" ) );
      positionStatues.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.position.change.position.statuses" ) );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.positionGrades = KANConstants.getKANAccountConstants( getAccountId() ).getPositionGrades( this.getLocale().getLanguage(), super.getCorpId() );
      this.positions = KANConstants.getKANAccountConstants( getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      this.changeReasons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
      try
      {
         this.jobRoles = KANUtil.getColumnOptionValues( request.getLocale(), KANConstants.getKANAccountConstants( getAccountId() ).getColumnVOByColumnId( "13365" ), super.getAccountId(), super.getCorpId() );
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) object;
      this.employeeId = employeePositionChangeVO.employeeId;
      this.staffId = employeePositionChangeVO.staffId;
      this.oldBranchId = employeePositionChangeVO.oldBranchId;
      this.oldStaffPositionRelationId = employeePositionChangeVO.oldStaffPositionRelationId;
      this.oldPositionId = employeePositionChangeVO.oldPositionId;
      this.oldStartDate = employeePositionChangeVO.oldStartDate;
      this.oldEndDate = employeePositionChangeVO.oldEndDate;
      this.newBranchId = employeePositionChangeVO.newBranchId;
      this.newPositionId = employeePositionChangeVO.newPositionId;
      this.newStartDate = employeePositionChangeVO.newStartDate;
      this.newEndDate = employeePositionChangeVO.newEndDate;
      this.effectiveDate = employeePositionChangeVO.effectiveDate;
      this.submitFlag = employeePositionChangeVO.submitFlag;
      this.positionStatus = employeePositionChangeVO.getPositionStatus();
      this.isChildChange = employeePositionChangeVO.getIsChildChange();
      this.isImmediatelyEffective = employeePositionChangeVO.isImmediatelyEffective;
      this.description = employeePositionChangeVO.description;
      super.setRemark1( employeePositionChangeVO.getRemark1() );
      super.setRemark3( employeePositionChangeVO.getRemark3() );
      super.setModifyDate( new Date() );
   }

   public String getPositionChangeId()
   {
      return positionChangeId;
   }

   public void setPositionChangeId( String positionChangeId )
   {
      this.positionChangeId = positionChangeId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getOldPositionId()
   {
      return oldPositionId;
   }

   public void setOldPositionId( String oldPositionId )
   {
      this.oldPositionId = oldPositionId;
   }

   public String getOldBranchId()
   {
      return oldBranchId;
   }

   public void setOldBranchId( String oldBranchId )
   {
      this.oldBranchId = oldBranchId;
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

   public String getNewPositionId()
   {
      return newPositionId;
   }

   public void setNewPositionId( String newPositionId )
   {
      this.newPositionId = newPositionId;
   }

   public String getNewBranchId()
   {
      return newBranchId;
   }

   public void setNewBranchId( String newBranchId )
   {
      this.newBranchId = newBranchId;
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

   public String getIsImmediatelyEffective()
   {
      return isImmediatelyEffective;
   }

   public void setIsImmediatelyEffective( String isImmediatelyEffective )
   {
      this.isImmediatelyEffective = isImmediatelyEffective;
   }

   public String getDescription()
   {
      return description;
   }

   @JsonIgnore
   public String getDecodeDescription()
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( description ) != null )
      {
         if ( description.length() > 30 )
         {
            returnStr = "<label title='" + description + "'>" + description.substring( 0, 30 ) + "...</label>";
         }
         else
         {
            returnStr = description;
         }
      }
      return returnStr;

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

   public String getOldBranchNameZH()
   {
      return oldBranchNameZH;
   }

   public void setOldBranchNameZH( String oldBranchNameZH )
   {
      this.oldBranchNameZH = oldBranchNameZH;
   }

   public String getOldBranchNameEN()
   {
      return oldBranchNameEN;
   }

   public void setOldBranchNameEN( String oldBranchNameEN )
   {
      this.oldBranchNameEN = oldBranchNameEN;
   }

   public String getOldPositionNameZH()
   {
      return oldPositionNameZH;
   }

   public void setOldPositionNameZH( String oldPositionNameZH )
   {
      this.oldPositionNameZH = oldPositionNameZH;
   }

   public String getOldPositionNameEN()
   {
      return oldPositionNameEN;
   }

   public void setOldPositionNameEN( String oldPositionNameEN )
   {
      this.oldPositionNameEN = oldPositionNameEN;
   }

   public String getNewBranchNameZH()
   {
      return newBranchNameZH;
   }

   public void setNewBranchNameZH( String newBranchNameZH )
   {
      this.newBranchNameZH = newBranchNameZH;
   }

   public String getNewBranchNameEN()
   {
      return newBranchNameEN;
   }

   public void setNewBranchNameEN( String newBranchNameEN )
   {
      this.newBranchNameEN = newBranchNameEN;
   }

   public String getNewPositionNameZH()
   {
      return newPositionNameZH;
   }

   public void setNewPositionNameZH( String newPositionNameZH )
   {
      this.newPositionNameZH = newPositionNameZH;
   }

   public String getNewPositionNameEN()
   {
      return newPositionNameEN;
   }

   public void setNewPositionNameEN( String newPositionNameEN )
   {
      this.newPositionNameEN = newPositionNameEN;
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public String getSearchEmployeeId()
   {
      return searchEmployeeId;
   }

   public void setSearchEmployeeId( String searchEmployeeId )
   {
      this.searchEmployeeId = searchEmployeeId;
   }

   public String getPositionStatus()
   {
      return positionStatus;
   }

   public List< MappingVO > getChangeReasons()
   {
      return changeReasons;
   }

   public void setChangeReasons( List< MappingVO > changeReasons )
   {
      this.changeReasons = changeReasons;
   }

   public void setPositionStatus( String positionStatus )
   {
      this.positionStatus = positionStatus;
   }

   public List< MappingVO > getPositionStatues()
   {
      return positionStatues;
   }

   public void setPositionStatues( List< MappingVO > positionStatues )
   {
      this.positionStatues = positionStatues;
   }

   public int getSubmitFlag()
   {
      return submitFlag;
   }

   public void setSubmitFlag( int submitFlag )
   {
      this.submitFlag = submitFlag;
   }

   public String getOldParentBranchId()
   {
      return oldParentBranchId;
   }

   public void setOldParentBranchId( String oldParentBranchId )
   {
      this.oldParentBranchId = oldParentBranchId;
   }

   public String getOldParentPositionId()
   {
      return oldParentPositionId;
   }

   public void setOldParentPositionId( String oldParentPositionId )
   {
      this.oldParentPositionId = oldParentPositionId;
   }

   public String getOldPositionGradeId()
   {
      return oldPositionGradeId;
   }

   public void setOldPositionGradeId( String oldPositionGradeId )
   {
      this.oldPositionGradeId = oldPositionGradeId;
   }

   public String getNewParentBranchId()
   {
      return newParentBranchId;
   }

   public void setNewParentBranchId( String newParentBranchId )
   {
      this.newParentBranchId = newParentBranchId;
   }

   public String getNewParentPositionId()
   {
      return newParentPositionId;
   }

   public void setNewParentPositionId( String newParentPositionId )
   {
      this.newParentPositionId = newParentPositionId;
   }

   public String getNewPositionGradeId()
   {
      return newPositionGradeId;
   }

   public void setNewPositionGradeId( String newPositionGradeId )
   {
      this.newPositionGradeId = newPositionGradeId;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   public String getOldParentBranchName()
   {
      return getOldParentBranchNameEN();
   }

   public String getOldParentPositionName()
   {
      if ( "ZH".equalsIgnoreCase( super.getLocale().getLanguage() ) )
      {
         return getOldParentPositionNameZH();
      }
      else
      {
         return getOldParentPositionNameEN();
      }
   }

   public String getOldPositionGradeName()
   {
      if ( "ZH".equalsIgnoreCase( super.getLocale().getLanguage() ) )
      {
         return getOldPositionGradeNameZH();
      }
      else
      {
         return getOldPositionGradeNameZH();
      }

   }

   public String getNewParentBranchName()
   {
      return getNewParentBranchNameEN();
   }

   public String getNewParentPositionName()
   {
      if ( "ZH".equalsIgnoreCase( super.getLocale().getLanguage() ) )
      {
         return getNewParentPositionNameZH();
      }
      else
      {
         return getNewParentPositionNameEN();
      }
   }

   public String getNewPositionGradeName()
   {
      if ( "ZH".equalsIgnoreCase( super.getLocale().getLanguage() ) )
      {
         return getNewPositionGradeNameZH();
      }
      else
      {
         return getNewPositionGradeNameEN();
      }
   }

   public String getOldParentPositionOwners()
   {
      return oldParentPositionOwners;
   }

   public void setOldParentPositionOwners( String oldParentPositionOwners )
   {
      this.oldParentPositionOwners = oldParentPositionOwners;
   }

   public String getNewParentPositionOwners()
   {
      return newParentPositionOwners;
   }

   public void setNewParentPositionOwners( String newParentPositionOwners )
   {
      this.newParentPositionOwners = newParentPositionOwners;
   }

   public String getOldStaffPositionRelationId()
   {
      return oldStaffPositionRelationId;
   }

   public void setOldStaffPositionRelationId( String oldStaffPositionRelationId )
   {
      this.oldStaffPositionRelationId = oldStaffPositionRelationId;
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

   public String getOldBranchName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getOldBranchNameZH();
         }
         else
         {
            return this.getOldBranchNameEN();
         }
      }
      else
      {
         return this.getOldBranchNameZH();
      }
   }

   public String getOldPositionName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getOldPositionNameZH();
         }
         else
         {
            return this.getOldPositionNameEN();
         }
      }
      else
      {
         return this.getOldPositionNameZH();
      }
   }

   public String getNewBranchName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNewBranchNameZH();
         }
         else
         {
            return this.getNewBranchNameEN();
         }
      }
      else
      {
         return this.getNewBranchNameZH();
      }
   }

   public String getNewPositionName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNewPositionNameZH();
         }
         else
         {
            return this.getNewPositionNameEN();
         }
      }
      else
      {
         return this.getNewPositionNameZH();
      }
   }

   public String getDecodePositionStatus()
   {

      return decodeField( this.positionStatus, this.positionStatues );
   }

   public String getOldParentPositionOwnersName()
   {
      //KANAccountConstants kanAccountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      //return kanAccountConstants.getStaffNamesByPositionId( super.getLocale().getLanguage(), oldParentPositionId );
      String name = "";
      if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
      {
         name = getOldParentPositionOwnersZH();
      }
      else
      {
         name = getOldParentPositionOwnersEN();
      }
      return name;
   }

   public String getNewParentPositionOwnersName()
   {
      //      KANAccountConstants kanAccountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      //      return kanAccountConstants.getStaffNamesByPositionId( super.getLocale().getLanguage(), newParentPositionId );
      String name = "";
      if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
      {
         name = getNewParentPositionOwnersZH();
      }
      else
      {
         name = getNewParentPositionOwnersEN();
      }
      return name;
   }

   public String getDecodeChangeReason()
   {
      return decodeField( super.getRemark3(), changeReasons );
   }

   public String getDecodeJobRole()
   {
      return decodeField( super.getRemark1(), jobRoles );
   }

   public String getDecodePositionChangeType()
   {
      if ( "1".equals( this.getPositionStatus() ) )
      {
         return "异动 Change";
      }
      else if ( "2".equals( this.getPositionStatus() ) )
      {
         return "晋升 Promotion";
      }
      else if ( "3".equals( this.getPositionStatus() ) )
      {
         return "调整 Adjustment";
      }
      else if ( "4".equals( this.getPositionStatus() ) )
      {
         return "解除 Relieve";
      }
      return "";
   }

   // 0:请选择##1:新建##2:提交-待批准##3:批准##4:拒绝##5:已生效
   @Override
   public String getDecodeStatus()
   {
      return decodeField( super.getWorkflowStatus(), KANUtil.getMappings( super.getLocale(), "business.employee.position.change.statuses" ) );
   }

   public String getDecodeWorkflowStatus()
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( getWorkflowId() ) != null )
      {
         if ( "4".equals( getWorkflowStatus() ) )
         {
            returnStr = getDecodeStatus();
         }
         else
         {
            if ( "3".equals( getStatus() ) )
            {
               returnStr = getDecodeStatus();
            }
            else if ( "1".equals( getStatus() ) )
            {
               returnStr = "拒绝";
            }
         }
      }
      else
      {
         returnStr = "提交-待批准";
      }
      return returnStr;
   }

   public String getOldParentBranchNameZH()
   {
      return oldParentBranchNameZH;
   }

   public void setOldParentBranchNameZH( String oldParentBranchNameZH )
   {
      this.oldParentBranchNameZH = oldParentBranchNameZH;
   }

   public String getOldParentBranchNameEN()
   {
      return oldParentBranchNameEN;
   }

   public void setOldParentBranchNameEN( String oldParentBranchNameEN )
   {
      this.oldParentBranchNameEN = oldParentBranchNameEN;
   }

   public String getOldParentPositionNameZH()
   {
      return oldParentPositionNameZH;
   }

   public void setOldParentPositionNameZH( String oldParentPositionNameZH )
   {
      this.oldParentPositionNameZH = oldParentPositionNameZH;
   }

   public String getOldParentPositionNameEN()
   {
      return oldParentPositionNameEN;
   }

   public void setOldParentPositionNameEN( String oldParentPositionNameEN )
   {
      this.oldParentPositionNameEN = oldParentPositionNameEN;
   }

   public String getOldPositionGradeNameZH()
   {
      return oldPositionGradeNameZH;
   }

   public void setOldPositionGradeNameZH( String oldPositionGradeNameZH )
   {
      this.oldPositionGradeNameZH = oldPositionGradeNameZH;
   }

   public String getOldPositionGradeNameEN()
   {
      return oldPositionGradeNameEN;
   }

   public void setOldPositionGradeNameEN( String oldPositionGradeNameEN )
   {
      this.oldPositionGradeNameEN = oldPositionGradeNameEN;
   }

   public String getNewParentBranchNameZH()
   {
      return newParentBranchNameZH;
   }

   public void setNewParentBranchNameZH( String newParentBranchNameZH )
   {
      this.newParentBranchNameZH = newParentBranchNameZH;
   }

   public String getNewParentBranchNameEN()
   {
      return newParentBranchNameEN;
   }

   public void setNewParentBranchNameEN( String newParentBranchNameEN )
   {
      this.newParentBranchNameEN = newParentBranchNameEN;
   }

   public String getNewParentPositionNameZH()
   {
      return newParentPositionNameZH;
   }

   public void setNewParentPositionNameZH( String newParentPositionNameZH )
   {
      this.newParentPositionNameZH = newParentPositionNameZH;
   }

   public String getNewParentPositionNameEN()
   {
      return newParentPositionNameEN;
   }

   public void setNewParentPositionNameEN( String newParentPositionNameEN )
   {
      this.newParentPositionNameEN = newParentPositionNameEN;
   }

   public String getNewPositionGradeNameZH()
   {
      return newPositionGradeNameZH;
   }

   public void setNewPositionGradeNameZH( String newPositionGradeNameZH )
   {
      this.newPositionGradeNameZH = newPositionGradeNameZH;
   }

   public String getNewPositionGradeNameEN()
   {
      return newPositionGradeNameEN;
   }

   public void setNewPositionGradeNameEN( String newPositionGradeNameEN )
   {
      this.newPositionGradeNameEN = newPositionGradeNameEN;
   }

   public String getOldParentPositionOwnersZH()
   {
      return oldParentPositionOwnersZH;
   }

   public void setOldParentPositionOwnersZH( String oldParentPositionOwnersZH )
   {
      this.oldParentPositionOwnersZH = oldParentPositionOwnersZH;
   }

   public String getOldParentPositionOwnersEN()
   {
      return oldParentPositionOwnersEN;
   }

   public void setOldParentPositionOwnersEN( String oldParentPositionOwnersEN )
   {
      this.oldParentPositionOwnersEN = oldParentPositionOwnersEN;
   }

   public String getNewParentPositionOwnersZH()
   {
      return newParentPositionOwnersZH;
   }

   public void setNewParentPositionOwnersZH( String newParentPositionOwnersZH )
   {
      this.newParentPositionOwnersZH = newParentPositionOwnersZH;
   }

   public String getNewParentPositionOwnersEN()
   {
      return newParentPositionOwnersEN;
   }

   public void setNewParentPositionOwnersEN( String newParentPositionOwnersEN )
   {
      this.newParentPositionOwnersEN = newParentPositionOwnersEN;
   }

   public String getIsChildChange()
   {
      return isChildChange;
   }

   public void setIsChildChange( String isChildChange )
   {
      this.isChildChange = isChildChange;
   }

   public String getEffectiveDateStart()
   {
      return effectiveDateStart;
   }

   public void setEffectiveDateStart( String effectiveDateStart )
   {
      this.effectiveDateStart = effectiveDateStart;
   }

   public String getEffectiveDateEnd()
   {
      return effectiveDateEnd;
   }

   public void setEffectiveDateEnd( String effectiveDateEnd )
   {
      this.effectiveDateEnd = effectiveDateEnd;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
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

   public List< MappingVO > getJobRoles()
   {
      return jobRoles;
   }

   public void setJobRoles( List< MappingVO > jobRoles )
   {
      this.jobRoles = jobRoles;
   }

}