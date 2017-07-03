package com.kan.hro.domain.biz.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：HeaderTempVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-11  
 */
public class OrderHeaderTempVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -2763741814202897687L;

   // Order Go主表Id
   private String orderHeaderId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 批次Id
   private String batchId;

   // 客户Id
   private String corpId;

   // 订单Id
   private String orderId;

   // 订单开始日期
   private String startDate;

   // 订单结束日期
   private String endDate;

   // 税率Id
   private String taxId;

   // 税率名称（中文）
   private String taxNameZH;

   // 税率名称（英文）
   private String taxNameEN;

   // 税率备注
   private String taxRemark;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 订单总金额
   private String orderAmount;

   // 所属部门（Branch Id）
   private String branch;

   // 所属人（Position Id）
   private String owner;

   // 描述
   private String description;

   // 月份
   private String monthly;

   /**
    * For App
    */
   // 客户中文名
   private String clientNameZH;

   // 客户中文名
   private String clientNameEN;

   // 包含服务协议数量
   private String countContractId;

   // 包含科目数量
   private String countItemId;

   // 法务实体
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.entities = KANConstants.getKANAccountConstants( getAccountId() ).getEntities( getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void update( final Object object )
   {
      final OrderHeaderTempVO orderHeaderTempVO = ( OrderHeaderTempVO ) object;
      this.entityId = orderHeaderTempVO.getEntityId();
      this.businessTypeId = orderHeaderTempVO.getBusinessTypeId();
      this.batchId = orderHeaderTempVO.getBatchId();
      this.corpId = orderHeaderTempVO.getCorpId();
      this.orderId = orderHeaderTempVO.getOrderId();
      this.taxId = orderHeaderTempVO.getTaxId();
      this.taxNameZH = orderHeaderTempVO.getTaxNameZH();
      this.taxNameEN = orderHeaderTempVO.getTaxNameEN();
      this.taxRemark = orderHeaderTempVO.getTaxRemark();
      this.billAmountPersonal = orderHeaderTempVO.getBillAmountPersonal();
      this.billAmountCompany = orderHeaderTempVO.getBillAmountCompany();
      this.costAmountPersonal = orderHeaderTempVO.getCostAmountPersonal();
      this.costAmountCompany = orderHeaderTempVO.getCostAmountCompany();
      this.orderAmount = orderHeaderTempVO.getOrderAmount();
      this.branch = orderHeaderTempVO.getBranch();
      this.owner = orderHeaderTempVO.getOwner();
      this.startDate = orderHeaderTempVO.getStartDate();
      this.endDate = orderHeaderTempVO.getEndDate();
      this.description = orderHeaderTempVO.getDescription();
      this.monthly = orderHeaderTempVO.getMonthly();
      super.setStatus( orderHeaderTempVO.getStatus() );
      super.setModifyBy( orderHeaderTempVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.corpId = "";
      this.orderId = "";
      this.taxId = "";
      this.taxNameZH = "";
      this.taxNameEN = "";
      this.taxRemark = "";
      this.billAmountPersonal = "";
      this.billAmountCompany = "";
      this.costAmountPersonal = "";
      this.costAmountCompany = "";
      this.orderAmount = "";
      this.branch = "";
      this.owner = "";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      this.monthly = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   // batchId编码
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // CorpId编码
   public String getEncodedCorpId() throws KANException
   {
      return encodedField( corpId );
   }

   // orderId编码
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // taxId编码
   public String getEncodedTaxId() throws KANException
   {
      return encodedField( taxId );
   }

   // 获得法务实体名称
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( entityId, entities );
   }

   // 获得业务类型名称
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( businessTypeId, businessTypes );
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
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

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getCorpId()
   {
      return corpId;
   }

   public void setCorpId( String corpId )
   {
      this.corpId = corpId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
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

   public String getTaxId()
   {
      return taxId;
   }

   public void setTaxId( String taxId )
   {
      this.taxId = taxId;
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

   public final String getOrderAmount()
   {
      return orderAmount;
   }

   public final void setOrderAmount( String orderAmount )
   {
      this.orderAmount = orderAmount;
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

   public String getTaxName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getTaxNameZH();
         }
         else
         {
            return this.getTaxNameEN();
         }
      }
      else
      {
         return this.getTaxNameZH();
      }
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

   public String getTaxRemark()
   {
      return taxRemark;
   }

   public void setTaxRemark( String taxRemark )
   {
      this.taxRemark = taxRemark;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public void addBillAmountCompany( final String billAmountCompany )
   {
      if ( this.billAmountCompany == null || this.billAmountCompany.trim().equals( "" ) )
      {
         this.billAmountCompany = billAmountCompany;
      }
      else
      {
         this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
      }
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      if ( this.billAmountPersonal == null || this.billAmountPersonal.trim().equals( "" ) )
      {
         this.billAmountPersonal = billAmountPersonal;
      }
      else
      {
         this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
      }
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      if ( this.costAmountCompany == null || this.costAmountCompany.trim().equals( "" ) )
      {
         this.costAmountCompany = costAmountCompany;
      }
      else
      {
         this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
      }
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      if ( this.costAmountPersonal == null || this.costAmountPersonal.trim().equals( "" ) )
      {
         this.costAmountPersonal = costAmountPersonal;
      }
      else
      {
         this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
      }
   }

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
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

   // 获得所属部门名称
   public String getDecodeBranch() throws KANException
   {
      // 获得常量中的BranchVO 集合
      final List< BranchVO > branchVOs = KANConstants.getKANAccountConstants( getAccountId() ).BRANCH_VO;

      for ( BranchVO branchVO : branchVOs )
      {
         if ( this.branch.equals( branchVO.getBranchId() ) )
         {
            if ( getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return branchVO.getNameZH();
            }
            else
            {
               return branchVO.getNameEN();
            }

         }
      }
      return null;
   }

   // 获得所属人姓名
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

}
