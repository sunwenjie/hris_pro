package com.kan.hro.domain.biz.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SettlementAdjustmentImportHeaderVO extends BaseVO
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String batchId;
   /**
    * For DB
    */
   // 调整主表ID
   private String adjustmentHeaderId;

   // 订单ID
   private String orderId;

   // 法务实体ID
   private String entityId;

   // 业务类型ID
   private String businessTypeId;

   // 雇员ID
   private String employeeId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 服务协议ID
   private String contractId;

   // 服务协议（中文）
   private String contractNameZH;
   
   // 服务协议（英文）
   private String contractNameEN;

   // 税率ID
   private String taxId;

   // 税率名称（中文）
   private String taxNameZH;

   // 税率名称（英文）
   private String taxNameEN;

   // 调整日期（费用日期）
   private String adjustmentDate;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 所属部门（Branch ID）
   private String branch;

   // 所属人（Position ID）
   private String owner;

   // 月份（例如2013/9） 
   private String monthly;

   // 是否已算薪酬 
   private String paymentFlag;

   // 描述
   private String description;

   /**
    * For Application
    */

   // 客户名（中文）
   private String clientNameZH;

   // 客户名（英文）
   private String clientNameEN;

   // 法务实体
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypies = new ArrayList< MappingVO >();

   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   // 最近12个月(含次月)
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 所属人部门 - 名称
   private String ownerName;

   // 开始时间  用于判断adjustmentDate
   private String startDate;

   private String endDate;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {

      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.settlement.adjustment.import.status" ) );

      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      if ( entities != null )
      {
         entities.add( 0, getEmptyMappingVO() );
      }

      this.businessTypies = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage() );
      if ( this.businessTypies != null )
      {
         businessTypies.add( 0, getEmptyMappingVO() );
      }

      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( getLocale().getLanguage() );
      if ( branchs != null )
      {
         branchs.add( 0, getEmptyMappingVO() );
      }

      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( request.getLocale().getLanguage() );
      if ( this.monthlies != null )
      {
         monthlies.add( 0, getEmptyMappingVO() );
      }

      this.ownerName = KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   @Override
   public void update( final Object object )
   {
      final String ignoreProperties[] = { "adjustmentHeaderId", "accountId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.contractId = "";
      this.taxId = "";
      this.taxNameZH = "";
      this.taxNameEN = "";
      this.adjustmentDate = "";
      this.billAmountPersonal = "";
      this.billAmountCompany = "";
      this.costAmountPersonal = "";
      this.costAmountCompany = "";
      this.branch = "";
      this.owner = "";
      this.monthly = "0";
      this.paymentFlag = "";
      this.description = "";
      super.setStatus( "0" );
   }

   // 加密雇员ID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // 加密订单ID
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // 加密客户ID
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // 加密服务协议ID
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // 获取所属人姓名
   public String getDecodeOwner()
   {
      return this.ownerName;
   }

   // 解译法务实体
   public String getDecodeLegalEntity()
   {
      return decodeField( entityId, entities );
   }

   // 解译业务类型
   public String getDecodeBusinessType()
   {
      return decodeField( businessTypeId, businessTypies );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentHeaderId );
   }

   public String getAdjustmentHeaderId()
   {
      return adjustmentHeaderId;
   }

   public void setAdjustmentHeaderId( String adjustmentHeaderId )
   {
      this.adjustmentHeaderId = adjustmentHeaderId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
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

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getTaxId()
   {
      return taxId;
   }

   public void setTaxId( String taxId )
   {
      this.taxId = taxId;
   }

   public String getTaxNameZH()
   {
      return taxNameZH;
   }

   public void setTaxNameZH( String taxNameZH )
   {
      this.taxNameZH = taxNameZH;
   }

   public String getTaxNameEN()
   {
      return taxNameEN;
   }

   public void setTaxNameEN( String taxNameEN )
   {
      this.taxNameEN = taxNameEN;
   }

   public String getAdjustmentDate()
   {
      return KANUtil.filterEmpty( decodeDate( adjustmentDate ) );
   }

   public void setAdjustmentDate( String adjustmentDate )
   {
      this.adjustmentDate = adjustmentDate;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
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

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypies()
   {
      return businessTypies;
   }

   public void setBusinessTypies( List< MappingVO > businessTypies )
   {
      this.businessTypies = businessTypies;
   }

   public String getOwnerName()
   {
      return ownerName;
   }

   public void setOwnerName( String ownerName )
   {
      this.ownerName = ownerName;
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

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public String getClientName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public final String getPaymentFlag()
   {
      return paymentFlag;
   }

   public final void setPaymentFlag( String paymentFlag )
   {
      this.paymentFlag = paymentFlag;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
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

   // 加密服务批次ID
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }
}
