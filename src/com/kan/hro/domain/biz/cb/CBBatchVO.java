package com.kan.hro.domain.biz.cb;

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

/**  
 * 项目名称：HRO_V1  
 * 类名称：BatchVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-11  
 */
public class CBBatchVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -8690970165074614616L;

   // 批次Id
   private String batchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 商保方案Id
   private String cbId;

   // 客户Id
   private String clientId;

   // 订单Id
   private String orderId;

   // 服务协议Id
   private String contractId;

   // 月份（例如2013/9）
   private String monthly;

   // 开始时间 - 指Batch运行的时间
   private String startDate;

   // 结束时间 - 指Batch运行的时间
   private String endDate;

   // 描述
   private String description;

   // 商保方案ID
   private String cbSolution;

   /**
    * For App
    */
   // 客户名称
   private String clientName;

   // 包含客户数量
   private String countClientId;
   @JsonIgnore
   // 包含的员工集合（MappingVO中mappingId对应雇员ID、mappingValue对应雇员中文名、mappingTemp对应雇员英文名）
   private List< MappingVO > employees = new ArrayList< MappingVO >();

   // 包含商保方案数量
   private String countHeaderId;

   // 包含订单数量
   private String countOrderId;

   // 包含服务协议数量
   private String countContractId;

   // 包含科目数量
   private String countItemId;

   // 销售成本合计
   private String amountSalesCost;

   // 销售价格合计
   private String amountSalesPrice;
   
   // 合计（采购成本）
   private String amountPurchaseCost;

   // 额外状态 - 社保方案明细的最小状态
   private String additionalStatus;
   @JsonIgnore
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 商保方案
   private List< MappingVO > cbIds = new ArrayList< MappingVO >();

   // 当前页面状态status 标记Id(preview, confirm, submit)
   private String statusFlag;

   // 当前页面类型标记(batch, contract, header, detail)
   private String pageFlag;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.cb.header.status" ) );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.cbIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), super.getCorpId() );

      // 如果entitys、businessTypes 不为空 添加"请选择"选项
      if ( this.entitys != null )
      {
         this.entitys.add( 0, getEmptyMappingVO() );
      }
      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }
      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }
      if ( this.cbIds != null )
      {
         this.cbIds.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void update( final Object object )
   {
      final CBBatchVO cBBatchVO = ( CBBatchVO ) object;
      this.entityId = cBBatchVO.getEntityId();
      this.businessTypeId = cBBatchVO.getBusinessTypeId();
      this.cbId = cBBatchVO.getCbId();
      this.clientId = cBBatchVO.getClientId();
      this.orderId = cBBatchVO.getOrderId();
      this.contractId = cBBatchVO.getContractId();
      this.monthly = cBBatchVO.getMonthly();
      this.startDate = cBBatchVO.getStartDate();
      this.endDate = cBBatchVO.getEndDate();
      this.description = cBBatchVO.getDescription();
      super.setModifyBy( cBBatchVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.cbId = "0";
      this.clientId = "";
      this.orderId = "";
      this.contractId = "";
      this.monthly = "0";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      super.setClientId( "" );
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( batchId );
   }

   // 法务实体ID编码
   public String getEncodedEntityId() throws KANException
   {
      return encodedField( entityId );
   }

   // 业务类型编码
   public String getEncodedBusinessTypeId() throws KANException
   {
      return encodedField( businessTypeId );
   }

   // 商保方案ID编码
   public String getEncodedCBId() throws KANException
   {
      return encodedField( cbId );
   }

   // 客户ID编码
   public String getEncodedClientId() throws KANException
   {
      return encodedField( clientId );
   }

   // 订单ID编码
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // 订单ID编码
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // 获得法务实体名称
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( this.entityId, this.entitys );
   }

   // 获得业务类型名称
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, this.businessTypes );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public String getStatusFlag()
   {
      return statusFlag;
   }

   public void setStatusFlag( String statusFlag )
   {
      this.statusFlag = statusFlag;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }

   public String getAmountSalesCost()
   {
      return amountSalesCost;
   }

   public void setAmountSalesCost( String amountSalesCost )
   {
      this.amountSalesCost = amountSalesCost;
   }

   public String getDecodeAmountSalesCost()
   {
      return formatNumber( amountSalesCost );
   }

   public String getAmountSalesPrice()
   {
      return amountSalesPrice;
   }

   public void setAmountSalesPrice( String amountSalesPrice )
   {
      this.amountSalesPrice = amountSalesPrice;
   }

   public String getDecodeAmountSalesPrice()
   {
      return formatNumber( amountSalesPrice );
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public String getCountClientId()
   {
      return countClientId;
   }

   public void setCountClientId( String countClientId )
   {
      this.countClientId = countClientId;
   }

   public String getCountHeaderId()
   {
      return countHeaderId;
   }

   public void setCountHeaderId( String countHeaderId )
   {
      this.countHeaderId = countHeaderId;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
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

   public String getCbId()
   {
      return cbId;
   }

   public void setCbId( String cbId )
   {
      this.cbId = KANUtil.filterEmpty( cbId );
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = KANUtil.filterEmpty( clientId );
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = KANUtil.filterEmpty( orderId );
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = KANUtil.filterEmpty( contractId );
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getAdditionalStatus()
   {
      return additionalStatus;
   }

   public void setAdditionalStatus( String additionalStatus )
   {
      this.additionalStatus = additionalStatus;
   }

   public final String getDecodeAdditionalStatus()
   {
      return decodeField( this.additionalStatus, KANUtil.getMappings( this.getLocale(), "business.cb.header.status" ) );
   }

   public List< MappingVO > getCbIds()
   {
      return cbIds;
   }

   public void setCbIds( List< MappingVO > cbIds )
   {
      this.cbIds = cbIds;
   }

   public String getCbSolution()
   {
      return cbSolution;
   }

   public void setCbSolution( String cbSolution )
   {
      this.cbSolution = cbSolution;
   }

   public List< MappingVO > getEmployees()
   {
      return employees;
   }

   public void setEmployees( List< MappingVO > employees )
   {
      this.employees = employees;
   }

   // 获得员工姓名（中文）集合
   public String getEmployeeNameZHList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            str.append( mappingVO.getMappingValue() + "、" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return str.toString();
   }

   // 获得员工姓名（英文）集合
   public String getEmployeeNameENList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            str.append( mappingVO.getMappingValue() + "、" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return str.toString();
   }

   // 获得员工集合数量
   public String getEmployeeListSize()
   {
      return String.valueOf( this.employees.size() );
   }

   // 获得员工姓名集合
   public String getEmployeeNameList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {

         // 如果小于或者等于500个人
         if ( employees.size() <= 500 )
         {
            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( MappingVO mappingVO : employees )
               {
                  str.append( mappingVO.getMappingValue() + "、" );
               }
               return str.substring( 0, str.length() - 1 );
            }
            else
            {
               for ( MappingVO mappingVO : employees )
               {
                  str.append( mappingVO.getMappingTemp() + "、" );
               }
               return str.substring( 0, str.length() - 1 );
            }
         }
         // 如果超过500个人只选取前面500个人
         else
         {
            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( int i = 0; i < 500; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "、" );
               }
               return str.substring( 0, str.length() - 1 ) + "...等";
            }
            else
            {
               for ( int i = 0; i < 500; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "、" );
               }
               return str.substring( 0, str.length() - 1 ) + "...";
            }
         }
      }

      return str.toString();
   }

   // 获得部分雇员（前5个）
   public String getEmployeeNameTop5List()
   {
      if ( employees != null )
      {
         if ( employees.size() <= 5 )
         {
            return getEmployeeNameList();
         }
         else
         {
            final StringBuffer str = new StringBuffer();

            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( int i = 0; i < 5; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "、" );
               }
               return str.substring( 0, str.length() - 1 ) + "...等";
            }
            else
            {
               for ( int i = 0; i < 5; i++ )
               {
                  str.append( employees.get( i ).getMappingTemp() + "、" );
               }
               return str.substring( 0, str.length() - 1 ) + "...";
            }

         }
      }
      return null;
   }

   // 获得员工数量
   public String getCountEmployeeId()
   {
      return String.valueOf( employees.size() );
   }

   public String getAmountPurchaseCost()
   {
      return amountPurchaseCost;
   }

   public void setAmountPurchaseCost( String amountPurchaseCost )
   {
      this.amountPurchaseCost = amountPurchaseCost;
   }
   public String getDecodeAmountPurchaseCost()
   {
      return formatNumber( amountPurchaseCost );
   }
}
