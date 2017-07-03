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

public class EmployeeContractSalaryVO extends BaseVO
{

   // Serial Version UID
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    *  For DB
    */

   // 雇员薪酬方案Id，主键
   private String employeeSalaryId;

   // 劳动合同Id
   private String contractId;

   // 科目Id（绑定设置 - 财务 - 科目）
   private String itemId;

   // 计薪方式
   private String salaryType;

   // 折算方式
   private String divideType;

   // 折算方式 - 不满月
   private String divideTypeIncomplete;

   // 不折算科目
   private String excludeDivideItemIds;

   // 基数
   private String base;

   // 基数来源
   private String baseFrom;

   // 比例（当使用基数来源时出现）
   private String percentage;

   // 固定金（当使用基数来源时出现）
   private String fix;

   // 数量
   private String quantity;

   // 折扣（暂时不计算）
   private String discount;

   // 倍率（暂时不计算）
   private String multiple;

   // 发放周期
   private String cycle;

   // 发放起始时间
   private String startDate;

   // 发放截至时间
   private String endDate;

   // 计算结果上限      
   private String resultCap;

   // 计算结果下限
   private String resultFloor;

   // 计算公式类型
   private String formularType;

   // 计算公式
   private String formular;

   // 显示至考勤表（是/否）
   private String showToTS;

   // 试用期是否可使用 
   private String probationUsing;

   // 描述
   private String description;

   /**
    *  For Application
    */
   // 劳动合同或派送协议名称（中文）
   private String contractNameZH;

   // 劳动合同或派送协议名称（英文）
   private String contractNameEN;

   // 劳动合同或派送协议名称（开始时间）
   private String contractStartDate;

   // 劳动合同或派送协议名称（结束时间）
   private String contractEndDate;

   // 单位（hro_tempBranchIds）
   private String department;

   // 岗位（hrm_tempPositionIds）
   private String positionId;

   // 客服人员（即派送协议所属人）
   private String owner;

   // 订单ID
   private String orderId;

   // 订单名称
   private String orderName;

   // 雇员ID
   private String employeeId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 雇员证件号码
   private String certificateNumber;

   // 客户名称(中文)
   private String clientNameZH;

   // 客户名称(英文)
   private String clientNameEN;

   // 服务协议状态
   private String contractStatus;

   private String isDeduct;

   private String itemNameZH;

   private String itemNameEN;

   /** 查询用 **/
   // 薪资方案开始时间
   private String salaryStartDateStart;

   //薪资方案 结束时间
   private String salaryStartDateEnd;

   // 薪资方案开始时间
   private String salaryEndDateStart;

   //薪资方案 结束时间
   private String salaryEndDateEnd;
   @JsonIgnore
   // 科目
   private List< MappingVO > items = new ArrayList< MappingVO >();
   @JsonIgnore
   // 计薪方式
   private List< MappingVO > salaryTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 折算方式
   private List< MappingVO > divideTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 科目
   private List< MappingVO > itemGroups = new ArrayList< MappingVO >();
   @JsonIgnore
   // 薪酬周期
   private List< MappingVO > cycles = new ArrayList< MappingVO >();
   @JsonIgnore
   // 合同状态
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // 不折算科目
   private List< MappingVO > excludeDivideItems = new ArrayList< MappingVO >();
   @JsonIgnore
   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // 职位
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   //查询用 多个合同id
   final List< String > contractIds = new ArrayList< String >();

   /***
    * For Application (Workflow)
    */
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

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeSalaryId );
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getDecodeCycle()
   {
      return decodeField( this.cycle, KANUtil.getMappings( this.getLocale(), "business.cycles" ) );
   }

   public String getDecodeBaseFrom()
   {
      return decodeField( this.baseFrom, KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeItemId()
   {
      return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeMultiple()
   {
      return decodeField( this.multiple, KANUtil.getMappings( this.getLocale(), "business.multiples" ), true );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( this.contractStatus, KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" ) );
   }

   public String getDecodeSalaryType()
   {
      return decodeField( this.salaryType, KANUtil.getMappings( this.getLocale(), "business.employee.contract.salaryType" ) );
   }

   public String getDecodeDivideType()
   {
      return decodeField( this.divideType, KANUtil.getMappings( this.getLocale(), "business.employee.contract.divideType" ) );
   }

   public String getDecodeDivideTypeIncomplete()
   {
      return decodeField( this.divideTypeIncomplete, KANUtil.getMappings( this.getLocale(), "business.employee.contract.divideType" ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.itemId = "0";
      this.salaryType = "0";
      this.divideType = "0";
      this.divideTypeIncomplete = "0";
      this.excludeDivideItemIds = "";
      this.base = "0";
      this.baseFrom = "0";
      this.percentage = "0";
      this.fix = "0";
      this.quantity = "0";
      this.discount = "0";
      this.multiple = "0";
      this.cycle = "0";
      this.startDate = "";
      this.endDate = "";
      this.resultCap = "0";
      this.resultFloor = "0";
      this.formularType = "0";
      this.formular = "";
      this.showToTS = "0";
      this.probationUsing = "0";
      this.description = "";
      this.isDeduct = "0";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.employee.contract.salary.statuses" ) );
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getSalaryItems( this.getLocale().getLanguage(), super.getCorpId() );
      this.itemGroups = KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( this.getLocale().getLanguage(), super.getCorpId() );
      this.salaryTypes = KANUtil.getMappings( this.getLocale(), "business.employee.contract.salaryType" );
      this.divideTypes = KANUtil.getMappings( this.getLocale(), "business.employee.contract.divideType" );
      this.cycles = KANUtil.getMappings( this.getLocale(), "business.cycles" );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );

      if ( this.itemGroups != null )
      {
         this.itemGroups.add( 0, super.getEmptyMappingVO() );
      }

      //6:休假
      this.excludeDivideItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getItemsByType( "6", request.getLocale().getLanguage(), super.getCorpId() );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) object;
      this.itemId = employeeContractSalaryVO.getItemId();
      this.salaryType = employeeContractSalaryVO.getSalaryType();
      this.divideType = employeeContractSalaryVO.getDivideType();
      // TODO 后续需要更正，临时措施（不满月可以有不同的折算方式）
      this.divideTypeIncomplete = employeeContractSalaryVO.getDivideType();
      this.excludeDivideItemIds = employeeContractSalaryVO.getExcludeDivideItemIds();
      this.base = employeeContractSalaryVO.getBase();
      this.baseFrom = employeeContractSalaryVO.getBaseFrom();
      this.percentage = employeeContractSalaryVO.getPercentage();
      this.fix = employeeContractSalaryVO.getFix();
      this.quantity = employeeContractSalaryVO.getQuantity();
      this.discount = employeeContractSalaryVO.getDiscount();
      this.multiple = employeeContractSalaryVO.getMultiple();
      this.cycle = employeeContractSalaryVO.getCycle();
      this.startDate = employeeContractSalaryVO.getStartDate();
      this.endDate = employeeContractSalaryVO.getEndDate();
      this.resultCap = employeeContractSalaryVO.getResultCap();
      this.resultFloor = employeeContractSalaryVO.getResultFloor();
      this.formularType = employeeContractSalaryVO.getFormularType();
      this.formular = employeeContractSalaryVO.getFormular();
      this.showToTS = employeeContractSalaryVO.getShowToTS();
      this.probationUsing = employeeContractSalaryVO.getProbationUsing();
      this.description = employeeContractSalaryVO.getDescription();
      this.isDeduct = employeeContractSalaryVO.getIsDeduct();
      super.setStatus( employeeContractSalaryVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeSalaryId()
   {
      return employeeSalaryId;
   }

   public void setEmployeeSalaryId( String employeeSalaryId )
   {
      this.employeeSalaryId = employeeSalaryId;
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

   public String getSalaryType()
   {
      return KANUtil.filterEmpty( salaryType );
   }

   public void setSalaryType( String salaryType )
   {
      this.salaryType = salaryType;
   }

   public String getBase()
   {
      return formatNumber( KANUtil.filterEmpty( base ) );
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getBaseFrom()
   {
      return KANUtil.filterEmpty( baseFrom ) == null ? "0" : KANUtil.filterEmpty( baseFrom );
   }

   public void setBaseFrom( String baseFrom )
   {
      this.baseFrom = baseFrom;
   }

   public String getPercentage()
   {
      return KANUtil.filterEmpty( percentage ) == null ? "0" : KANUtil.filterEmpty( percentage );
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getFix()
   {
      return KANUtil.filterEmpty( fix ) == null ? "0" : KANUtil.filterEmpty( fix );
   }

   public void setFix( String fix )
   {
      this.fix = fix;
   }

   public String getQuantity()
   {
      return KANUtil.filterEmpty( quantity );
   }

   public void setQuantity( String quantity )
   {
      this.quantity = quantity;
   }

   public String getDiscount()
   {
      return KANUtil.filterEmpty( discount );
   }

   public void setDiscount( String discount )
   {
      this.discount = discount;
   }

   public String getMultiple()
   {
      return KANUtil.filterEmpty( multiple );
   }

   public void setMultiple( String multiple )
   {
      this.multiple = multiple;
   }

   public String getCycle()
   {
      return KANUtil.filterEmpty( cycle );
   }

   public void setCycle( String cycle )
   {
      this.cycle = cycle;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getResultCap()
   {
      return KANUtil.filterEmpty( resultCap );
   }

   public void setResultCap( String resultCap )
   {
      this.resultCap = resultCap;
   }

   public String getResultFloor()
   {
      return KANUtil.filterEmpty( resultFloor );
   }

   public void setResultFloor( String resultFloor )
   {
      this.resultFloor = resultFloor;
   }

   public String getFormularType()
   {
      return KANUtil.filterEmpty( formularType );
   }

   public void setFormularType( String formularType )
   {
      this.formularType = formularType;
   }

   public String getFormular()
   {
      return formular;
   }

   public void setFormular( String formular )
   {
      this.formular = formular;
   }

   public String getShowToTS()
   {
      return showToTS;
   }

   public void setShowToTS( String showToTS )
   {
      this.showToTS = showToTS;
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

   public final String getContractNameZH()
   {
      return contractNameZH;
   }

   public final void setContractNameZH( String contractNameZH )
   {
      this.contractNameZH = contractNameZH;
   }

   public final String getContractNameEN()
   {
      return contractNameEN;
   }

   public final void setContractNameEN( String contractNameEN )
   {
      this.contractNameEN = contractNameEN;
   }

   public String getContractName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage() != null && this.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            return contractNameZH;
         }
         else
         {
            return contractNameEN;
         }
      }
      else
      {
         return contractNameZH;
      }
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEncodedCorpId() throws KANException
   {
      return encodedField( super.getCorpId() );
   }

   public String getOrderId()
   {
      return orderId;
   }

   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
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
         return this.getClientNameEN();
      }
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
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

   public final List< MappingVO > getItems()
   {
      return items;
   }

   public final void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   public final List< MappingVO > getSalaryTypes()
   {
      return salaryTypes;
   }

   public final void setSalaryTypes( List< MappingVO > salaryTypes )
   {
      this.salaryTypes = salaryTypes;
   }

   public final List< MappingVO > getDivideTypes()
   {
      return divideTypes;
   }

   public final void setDivideTypes( List< MappingVO > divideTypes )
   {
      this.divideTypes = divideTypes;
   }

   public final List< MappingVO > getItemGroups()
   {
      return itemGroups;
   }

   public final void setItemGroups( List< MappingVO > itemGroups )
   {
      this.itemGroups = itemGroups;
   }

   public final List< MappingVO > getCycles()
   {
      return cycles;
   }

   public final void setCycles( List< MappingVO > cycles )
   {
      this.cycles = cycles;
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public String getProbationUsing()
   {
      return probationUsing;
   }

   public void setProbationUsing( String probationUsing )
   {
      this.probationUsing = probationUsing;
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

   public String getOrderName()
   {
      return orderName;
   }

   public void setOrderName( String orderName )
   {
      this.orderName = orderName;
   }

   public String getContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractStartDate ) );
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractEndDate ) );
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
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
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
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

   // 解译派送协议所属人
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   // 解译岗位（hrm_tempPositionIds）
   public String getDecodePositionId()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         final String[] positionArray = positionId.split( "," );
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
                  returnString = returnString + "、" + decodeField( position, positions );
               }
            }
         }
      }

      return returnString;
   }

   // 解译单位（hrm_tempBranchIds）
   public String getDecodeDepartment() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getCorpId() ) == null )
      {
         return department;
      }
      else
      {
         String returnStr = "";
         if ( KANUtil.filterEmpty( department ) != null && branchs != null && branchs.size() > 0 )
         {
            for ( String branchId : department.split( "," ) )
            {
               if ( KANUtil.filterEmpty( returnStr ) == null )
               {
                  returnStr = decodeField( branchId, branchs, true );
               }
               else
               {
                  returnStr = returnStr + "、" + decodeField( branchId, branchs, true );
               }
            }
         }

         return returnStr;
      }
   }

   // 解译显示至考勤表
   public String getDecodeShowToTS()
   {
      return decodeField( showToTS, super.getFlags() );
   }

   // 解译试用期可用
   public String getDecodeProbationUsing()
   {
      return decodeField( probationUsing, super.getFlags() );
   }

   // 解译不折算科目
   public String getDecodeExcludeDivideItemIds()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( excludeDivideItemIds ) != null && excludeDivideItems != null && excludeDivideItems.size() > 0 )
      {
         final String[] excludeDivideItemIdArray = KANUtil.jasonArrayToStringArray( excludeDivideItemIds );

         if ( excludeDivideItemIdArray != null && excludeDivideItemIdArray.length > 0 )
         {
            for ( String itemId : excludeDivideItemIdArray )
            {
               for ( MappingVO mappingVO : excludeDivideItems )
               {
                  if ( itemId.equals( mappingVO.getMappingId() ) )
                  {
                     if ( KANUtil.filterEmpty( returnString ) == null )
                     {
                        returnString = mappingVO.getMappingValue();
                     }
                     else
                     {
                        returnString = returnString + "、" + mappingVO.getMappingValue();
                     }
                     break;
                  }
               }
            }
         }
      }

      return returnString;
   }

   public String getRemark()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( itemId ) != null )
      {
         returnString = getDecodeItemId();
         returnString = returnString + "（";
         if ( KANUtil.filterEmpty( baseFrom, "0" ) != null )
         {
            returnString = returnString + getDecodeBaseFrom();
            if ( KANUtil.filterEmpty( percentage, "0" ) != null )
            {
               returnString = returnString + " * " + percentage;
            }

            if ( KANUtil.filterEmpty( fix, "0" ) != null )
            {
               returnString = returnString + " + " + fix;
            }
         }
         else
         {
            if ( KANUtil.filterEmpty( getBase(), "0" ) != null )
            {
               returnString = returnString + " " + getDecodeSalaryType() + " " + getBase();
               if ( ( KANUtil.filterEmpty( cycle, "0" ) != null ) )
               {
                  returnString = returnString + " / " + getDecodeCycle();
               }
            }
         }

         returnString = returnString + "）";
      }

      returnString = returnString + getStartDate();

      if ( KANUtil.filterEmpty( endDate ) != null )
      {
         returnString = returnString + " ~ " + getEndDate();
      }

      return returnString;
   }

   public String getIsDeduct()
   {
      return isDeduct;
   }

   public void setIsDeduct( String isDeduct )
   {
      this.isDeduct = isDeduct;
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

   public List< String > getContractIds()
   {
      return contractIds;
   }

   public String getSalaryStartDateStart()
   {
      return salaryStartDateStart;
   }

   public void setSalaryStartDateStart( String salaryStartDateStart )
   {
      this.salaryStartDateStart = salaryStartDateStart;
   }

   public String getSalaryStartDateEnd()
   {
      return salaryStartDateEnd;
   }

   public void setSalaryStartDateEnd( String salaryStartDateEnd )
   {
      this.salaryStartDateEnd = salaryStartDateEnd;
   }

   public String getSalaryEndDateStart()
   {
      return salaryEndDateStart;
   }

   public void setSalaryEndDateStart( String salaryEndDateStart )
   {
      this.salaryEndDateStart = salaryEndDateStart;
   }

   public String getSalaryEndDateEnd()
   {
      return salaryEndDateEnd;
   }

   public void setSalaryEndDateEnd( String salaryEndDateEnd )
   {
      this.salaryEndDateEnd = salaryEndDateEnd;
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

   public String getContractObject()
   {
      setContractObject( "true" );
      return contractObject;
   }

   public void setContractObject( String contractObject )
   {
      this.contractObject = contractObject;
   }

}
