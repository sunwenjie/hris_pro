package com.kan.hro.domain.biz.payment;

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

public class PaymentAdjustmentImportHeaderVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
  
   /**
    * for DB
    */
   // 调整主表Id，最小单位服务协议
   private String adjustmentHeaderId;
   
   private String batchId;

   // 订单Id
   private String orderId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 雇员Id
   private String employeeId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 服务协议Id
   private String contractId;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（个税）
   private String taxAmountPersonal;

   // 附加合计（个人收入），用于税前加的金额
   private String addtionalBillAmountPersonal;

   // 所属部门（Branch Id）
   private String branch;

   // 所属人（Position Id）
   private String owner;

   // 调整月份（例如2013/9）
   private String monthly;

   // 描述
   private String description;

   /**
    * For App
    */
   private String pageFlag;

   private String isLink;

   private String clientNameZH;

   private String clientNameEN;

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 法务实体
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentHeaderId );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.payment.adjustment.import.status" ) );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( getLocale().getLanguage() );
      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );


      if ( this.entities != null )
      {
         this.entities.add( 0, getEmptyMappingVO() );
      }

      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }

      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.adjustmentHeaderId = "";
      this.orderId = "";
      this.entityId = "0";
      this.businessTypeId = "0";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.clientNameZH = "";
      this.clientNameEN = "";
      this.contractId = "";
      this.billAmountPersonal = "0";
      this.costAmountPersonal = "0";
      this.taxAmountPersonal = "0";
      this.addtionalBillAmountPersonal = "0";
      this.branch = "0";
      this.owner = "0";
      this.monthly = "0";
      this.description = "";
      super.setStatus( "0" );
      super.setCorpId( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) object;
      this.orderId = paymentAdjustmentHeaderVO.getOrderId();
      this.entityId = paymentAdjustmentHeaderVO.getEntityId();
      this.businessTypeId = paymentAdjustmentHeaderVO.getBusinessTypeId();
      this.employeeId = paymentAdjustmentHeaderVO.getEmployeeId();
      this.employeeNameZH = paymentAdjustmentHeaderVO.getEmployeeNameZH();
      this.employeeNameEN = paymentAdjustmentHeaderVO.getEmployeeNameEN();
      this.clientNameZH = paymentAdjustmentHeaderVO.getClientNameZH();
      this.clientNameEN = paymentAdjustmentHeaderVO.getClientNameEN();
      this.contractId = paymentAdjustmentHeaderVO.getContractId();
      this.billAmountPersonal = paymentAdjustmentHeaderVO.getBillAmountPersonal();
      this.costAmountPersonal = paymentAdjustmentHeaderVO.getCostAmountPersonal();
      this.taxAmountPersonal = paymentAdjustmentHeaderVO.getTaxAmountPersonal();
      this.addtionalBillAmountPersonal = paymentAdjustmentHeaderVO.getAddtionalBillAmountPersonal();
      this.branch = paymentAdjustmentHeaderVO.getBranch();
      this.owner = paymentAdjustmentHeaderVO.getOwner();
      this.monthly = paymentAdjustmentHeaderVO.getMonthly();
      this.description = paymentAdjustmentHeaderVO.getDescription();
      super.setStatus( paymentAdjustmentHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( paymentAdjustmentHeaderVO.getCorpId() );
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

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getTaxAmountPersonal()
   {
      if ( ( formatNumber( taxAmountPersonal ) != null ) && !formatNumber( taxAmountPersonal ).trim().isEmpty() )
      {
         return formatNumber( taxAmountPersonal );
      }
      return "0";
   }

   public void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   public String getIsLink() throws KANException
   {
      if ( "1".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">提交</a>";
      }
      return isLink;
   }

   public final String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public final void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public final String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public final void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public final String getAddtionalBillAmountPersonal()
   {
      return formatNumber( addtionalBillAmountPersonal );
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public String getAmountAdjustment()
   {
      return formatNumber( String.valueOf( Float.parseFloat( billAmountPersonal ) - Float.parseFloat( costAmountPersonal ) - Float.parseFloat( taxAmountPersonal ) ) );
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

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }
// 加密服务批次ID
   public String getEncodedBatchId() throws KANException {
      return encodedField(batchId);
   }
   
}
