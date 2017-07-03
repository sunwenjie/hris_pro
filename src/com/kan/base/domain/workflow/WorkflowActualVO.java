package com.kan.base.domain.workflow;

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
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.web.actions.biz.employee.EmployeeContractSalaryAction;

public class WorkflowActualVO extends BaseVO
{

   // Serial Version UID
   private static final long serialVersionUID = 3444398803912923703L;

   /**
    * For DB
    */
   // 工作流ID
   private String workflowId;

   // 关联系统工作流ID
   private String systemId;

   // 工作流定义ID
   private String defineId;

   // 模块ID
   private String workflowModuleId;

   // 触发权限
   private String rightId;

   // 数据对象ID
   private String objectId;

   // 触发工作流的PositionID
   private String positionId;

   // 中文名
   private String nameZH;

   // 英文名
   private String nameEN;

   // 描述
   private String description;

   /**
    * For Application
    */
   private String chineseName;

   private String shortName;

   // 包含JSP页面
   private String includeViewObjJsp;

   private String rightNameZH;

   private String rightNameEN;
   @JsonIgnore
   private List< MappingVO > actualStepStatuses = new ArrayList< MappingVO >();

   // 审核状态
   private String actualStepStatus;

   // 审核时间
   private String actualStepModifyDate;

   // 上报时间
   private String createDateStr;

   // 处理时间
   private String handleDate;

   // 当前userId
   private String logonUserId;

   // 当前staffId
   private String staffId;

   private String contractId;

   // 系统模板，用于手机待办事项。只查看请假和加班
   private String systemModuleId;

   private String accessAction;
   @JsonIgnore
   private List< MappingVO > rights = new ArrayList< MappingVO >();
   @JsonIgnore
   // 职位
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   // 客户有效社保方案
   private List< MappingVO > solutions = new ArrayList< MappingVO >();
   @JsonIgnore
   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( workflowId );
   }

   public String getEncodedObjectId() throws KANException
   {
      if ( objectId == null || objectId.isEmpty() || "-1".equals( objectId ) )
      {
         return null;
      }
      return encodedField( objectId );
   }

   public String getWorkflowId()
   {
      return workflowId;
   }

   public void setWorkflowId( String workflowId )
   {
      this.workflowId = workflowId;
   }

   public String getSystemId()
   {
      return systemId;
   }

   public void setSystemId( String systemId )
   {
      this.systemId = systemId;
   }

   public String getDefineId()
   {
      return defineId;
   }

   public void setDefineId( String defineId )
   {
      this.defineId = defineId;
   }

   public String getWorkflowModuleId()
   {
      return workflowModuleId;
   }

   public void setWorkflowModuleId( String workflowModuleId )
   {
      this.workflowModuleId = workflowModuleId;
   }

   public String getObjectId()
   {
      return objectId;
   }

   public void setObjectId( String objectId )
   {
      this.objectId = objectId;
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

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
     if (KANUtil.filterEmpty(super.getAccountId()) == null) {
       super.setAccountId("100017");
       super.setCorpId("300115");
     }
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "actual.status" ) );
      this.actualStepStatuses = KANUtil.getMappings( getLocale(), "actual.steps.status" );
      this.rights = KANConstants.getRights( request.getLocale().getLanguage() );
      this.rights.add( 0, getEmptyMappingVO() );

      // 外部职位
      if ( KANConstants.ROLE_HR_SERVICE.equals( this.getRole() ) )
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getEmployeePositions( request.getLocale().getLanguage() );
      }
      // 内部职位
      else
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      }

      this.solutions = KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( request.getLocale().getLanguage(), super.getCorpId() );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
   }

   public String getRightId()
   {
      return rightId;
   }

   public void setRightId( String rightId )
   {
      this.rightId = rightId;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getIncludeViewObjJsp()
   {
      return includeViewObjJsp;
   }

   public void setIncludeViewObjJsp( String includeViewObjJsp )
   {
      this.includeViewObjJsp = includeViewObjJsp;
   }

   public String getRightNameZH()
   {
      return rightNameZH;
   }

   public void setRightNameZH( String rightNameZH )
   {
      this.rightNameZH = rightNameZH;
   }

   public String getRightNameEN()
   {
      return rightNameEN;
   }

   public void setRightNameEN( String rightNameEN )
   {
      this.rightNameEN = rightNameEN;
   }

   public String getActualStepStatus()
   {
      return actualStepStatus;
   }

   public List< MappingVO > getActualStepStatuses()
   {
      return actualStepStatuses;
   }

   public void setActualStepStatuses( List< MappingVO > actualStepStatuses )
   {
      this.actualStepStatuses = actualStepStatuses;
   }

   public void setActualStepStatus( String actualStepStatus )
   {
      this.actualStepStatus = actualStepStatus;
   }

   public String getDecodeActualStepStatus()
   {
      return decodeField( actualStepStatus, actualStepStatuses );
   }

   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   public String getHandleDate()
   {
      return handleDate;
   }

   public void setHandleDate( String handleDate )
   {
      this.handleDate = handleDate;
   }

   public String getDecodeHandleDate()
   {
      return decodeDatetime( handleDate );
   }

   @Override
   public String getDecodeCreateDate()
   {
      return decodeDatetime( super.getCreateDate() );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final WorkflowActualVO workflowModelVO = ( WorkflowActualVO ) object;
      this.nameZH = workflowModelVO.getNameZH();
      this.nameEN = workflowModelVO.getNameEN();
      super.setModifyBy( workflowModelVO.getModifyBy() );
      super.setModifyDate( new Date() );

   }

   public String getDecodeCreateBy()
   {
      return decodeUserId( super.getCreateBy() );
   }

   public String getSystemModuleId()
   {
      return systemModuleId;
   }

   public void setSystemModuleId( String systemModuleId )
   {
      this.systemModuleId = systemModuleId;
   }

   public String getLogonUserId()
   {
      return logonUserId;
   }

   public void setLogonUserId( String logonUserId )
   {
      this.logonUserId = logonUserId;
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public List< MappingVO > getRights()
   {
      return rights;
   }

   public void setRights( List< MappingVO > rights )
   {
      this.rights = rights;
   }

   public String getActualStepModifyDate()
   {
      return actualStepModifyDate;
   }

   public void setActualStepModifyDate( String actualStepModifyDate )
   {
      this.actualStepModifyDate = actualStepModifyDate;
   }

   public String getCreateDateStr()
   {
      return createDateStr;
   }

   public void setCreateDateStr( String createDateStr )
   {
      this.createDateStr = createDateStr;
   }

   public String getContractId()
   {
      if ( KANUtil.filterEmpty( contractId ) != null && contractId.length() > 20 )
      {
         JSONObject jsonObject = JSONObject.fromObject( contractId );
         if ( KANUtil.filterEmpty( jsonObject.get( "contractObject" ) ) == null )
         {
            return "";
         }
         return ( String ) jsonObject.get( "contractId" );
      }
      else
      {
         return contractId;
      }
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   // 雇员ID
   public String getEmployeeId()
   {
      return getJSONObjectValue( "employeeId" );
   }

   // 雇员姓名
   public String getEmployeeName()
   {
      if ( getLocale() != null && getLocale().getLanguage().equalsIgnoreCase( "en" ) )
      {
         return getJSONObjectValue( "employeeNameEN" );
      }
      else
      {
         return getJSONObjectValue( "employeeNameZH" );
      }
   }

   // 证件号码
   public String getCertificateNumber()
   {
      return getJSONObjectValue( "certificateNumber" );
   }

   // 社保方案
   public String getDecodeSbSolutionIds()
   {
      return getTempDecodeSbSolutionIds( getJSONObjectValue( "sbSolutionIds" ) );
   }

   // 协议开始时间
   public String getContractStartDate()
   {
      if ( EmployeeContractSalaryAction.accessAction.equalsIgnoreCase( this.accessAction ) )
      {
         return KANUtil.filterEmpty( getJSONObjectValue( "contractStartDate" ) ) == null ? "" : KANUtil.formatDate( getJSONObjectValue( "contractStartDate" ), "yyyy-MM-dd", false );
      }

      return KANUtil.filterEmpty( getJSONObjectValue( "startDate" ) ) == null ? "" : KANUtil.formatDate( getJSONObjectValue( "startDate" ), "yyyy-MM-dd", false );
   }

   // 协议结束时间
   public String getContractEndDate()
   {
      if ( EmployeeContractSalaryAction.accessAction.equalsIgnoreCase( this.accessAction ) )
      {
         return KANUtil.filterEmpty( getJSONObjectValue( "contractEndDate" ) ) == null ? "" : KANUtil.formatDate( getJSONObjectValue( "contractEndDate" ), "yyyy-MM-dd", false );
      }

      return KANUtil.filterEmpty( getJSONObjectValue( "endDate" ) ) == null ? "" : KANUtil.formatDate( getJSONObjectValue( "endDate" ), "yyyy-MM-dd", false );
   }

   // 客户名称
   public String getClientName()
   {
      return getJSONObjectValue( "clientNameZH" );
   }

   // 客服专员
   public String getDecodeOwner()
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), getJSONObjectValue( "owner" ) );
   }

   // 法务实体
   public String getDecodeEntityId()
   {
      return decodeField( getJSONObjectValue( "entityId" ), entitys );
   }

   // 岗位
   public String getDecodePositionId()
   {
      String positionIds = getJSONObjectValue( "positionId" );
      if ( KANUtil.filterEmpty( super.getCorpId() ) != null )
      {
         positionIds = getJSONObjectValue( "_tempPositionIds" );
      }
      String p1 = getTempDecodePositionId( positionIds );
      String p2 = getJSONObjectValue( "additionalPosition" );
      String returnStr = "";
      returnStr = p1;
      if ( KANUtil.filterEmpty( p2 ) != null )
      {
         returnStr = returnStr + "（" + p2 + "）";
      }
      return returnStr;
   }

   // 解译岗位（hrm_tempPositionIds）
   public String getTempDecodePositionId( final String positionId )
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         final String[] positionIdArray = positionId.split( "," );
         if ( positionIdArray != null && positionIdArray.length > 0 )
         {
            for ( String position : positionIdArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( position, positions );
               }
               else
               {
                  returnString = returnString + "、" + decodeField( position, positions );
               }
            }
         }
      }

      return returnString;
   }

   // 解译社保方案
   public String getTempDecodeSbSolutionIds( final String sbSolutionIds )
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( sbSolutionIds ) != null )
      {
         final String[] sbSolutionIdArray = sbSolutionIds.split( "," );
         if ( sbSolutionIdArray != null && sbSolutionIdArray.length > 0 )
         {
            for ( String solutionId : sbSolutionIdArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( solutionId, solutions );
               }
               else
               {
                  returnString = returnString + "、" + decodeField( solutionId, solutions );
               }
            }
         }
      }

      return returnString;
   }

   // 解译部门
   public String getDecodeDepartment()
   {
      if ( KANUtil.filterEmpty( super.getCorpId() ) == null )
      {
         return getJSONObjectValue( "department" );
      }

      String returnString = "";
      if ( getJSONObjectValue( "_tempBranchIds" ) != null )
      {
         final String[] branchIdArray = getJSONObjectValue( "_tempBranchIds" ).split( "," );
         if ( branchIdArray != null && branchIdArray.length > 0 )
         {
            for ( String branchId : branchIdArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( branchId, branchs, true );
               }
               else
               {
                  returnString = returnString + "、" + decodeField( branchId, branchs, true );
               }
            }
         }
      }

      return returnString;
   }

   // 解译成本部门
   public String getDecodeSettlementBranch()
   {
      return decodeField( getJSONObjectValue( "settlementBranch" ), branchs, true );
   }

   public String getJSONObjectValue( final String key )
   {
      if ( StringUtils.isBlank( contractId ) )
         return "";
      JSONObject jsonObject = JSONObject.fromObject( contractId );

      if ( KANUtil.filterEmpty( key ) != null && jsonObject != null )
      {
         return ( String ) jsonObject.get( key );
      }

      return "";
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   public List< MappingVO > getSolutions()
   {
      return solutions;
   }

   public void setSolutions( List< MappingVO > solutions )
   {
      this.solutions = solutions;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public String getAccessAction()
   {
      return accessAction;
   }

   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public String getChineseName()
   {
      return chineseName;
   }

   public void setChineseName( String chineseName )
   {
      this.chineseName = chineseName;
   }

   public String getShortName()
   {
      return shortName;
   }

   public void setShortName( String shortName )
   {
      this.shortName = shortName;
   }

}
