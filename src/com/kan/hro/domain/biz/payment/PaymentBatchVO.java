package com.kan.hro.domain.biz.payment;

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

public class PaymentBatchVO extends BaseVO
{
   // serialVersionUID
   private static final long serialVersionUID = 6077784650785066542L;

   /**
    * for DB
    */
   // 批次Id
   private String batchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 订单Id
   private String orderId;

   // 服务协议Id
   private String contractId;

   // 雇员Id
   private String employeeId;

   // 薪酬月份
   private String monthly;

   // 薪酬周次
   private String weekly;

   // 开始时间 
   private String startDate;

   // 结束时间
   private String endDate;

   private String description;

   /**
    * for application
    */
   @JsonIgnore
   // 包含的员工集合（MappingVO中mappingId对应雇员ID、mappingValue对应雇员中文名、mappingTemp对应雇员英文名）
   private List< MappingVO > employees = new ArrayList< MappingVO >();

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（个税）
   private String taxAmountPersonal;

   // 客户ID 总和
   private String countClientIds;

   // 订单ID 总和
   private String countOrderIds;

   // 合同ID 总和
   private String countContractIds;

   // 科目ID 总和
   private String countItemIds;

   // 员工ID 总和
   private String countEmployeeIds;

   // 附加合计（个人收入），用于税前加的金额
   private String addtionalBillAmountPersonal;

   private String statusFlag;

   private String pageFlag;

   private String clientName;

   private String employeeNameZH;
   @JsonIgnore
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( this.getLocale().getLanguage() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );

      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }

      if ( this.entitys != null )
      {
         this.entitys.add( 0, getEmptyMappingVO() );
      }

      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.orderId = "";
      this.contractId = "";
      this.employeeId = "";
      this.monthly = "0";
      this.weekly = "0";
      this.description = "";
      this.startDate = "";
      this.endDate = "";
      super.setClientId( "" );
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) object;
      this.entityId = paymentBatchVO.getEntityId();
      this.businessTypeId = paymentBatchVO.getBusinessTypeId();
      this.orderId = paymentBatchVO.getOrderId();
      this.contractId = paymentBatchVO.getContractId();
      this.employeeId = paymentBatchVO.getEmployeeId();
      this.startDate = paymentBatchVO.getStartDate();
      this.endDate = paymentBatchVO.getEndDate();
      this.monthly = paymentBatchVO.getMonthly();
      this.weekly = paymentBatchVO.getWeekly();
      this.description = paymentBatchVO.getDescription();
      super.setStatus( paymentBatchVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( batchId );
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

   public String getOrderId()
   {
      return KANUtil.filterEmpty( orderId );
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public final String getEmployeeId()
   {
      return KANUtil.filterEmpty( employeeId );
   }

   public final void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
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

   public String getCountClientIds()
   {
      return countClientIds;
   }

   public void setCountClientIds( String countClientIds )
   {
      this.countClientIds = countClientIds;
   }

   public String getCountOrderIds()
   {
      return countOrderIds;
   }

   public void setCountOrderIds( String countOrderIds )
   {
      this.countOrderIds = countOrderIds;
   }

   public String getCountContractIds()
   {
      return countContractIds;
   }

   public void setCountContractIds( String countContractIds )
   {
      this.countContractIds = countContractIds;
   }

   public String getCountItemIds()
   {
      return countItemIds;
   }

   public void setCountItemIds( String countItemIds )
   {
      this.countItemIds = countItemIds;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
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

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getTaxAmountPersonal()
   {
      return formatNumber( taxAmountPersonal );
   }

   public void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
   }

   public String getCountEmployeeIds()
   {
      return countEmployeeIds;
   }

   public void setCountEmployeeIds( String countEmployeeIds )
   {
      this.countEmployeeIds = countEmployeeIds;
   }

   public String getAddtionalBillAmountPersonal()
   {
      return addtionalBillAmountPersonal;
   }

   public void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public List< MappingVO > getEmployees()
   {
      return employees;
   }

   public void setEmployees( List< MappingVO > employees )
   {
      this.employees = employees;
   }

   public void addBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany == null ? "0" : this.billAmountCompany )
            + Double.valueOf( KANUtil.filterEmpty( billAmountCompany ) == null ? "0" : billAmountCompany ) );
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal == null ? "0" : this.billAmountPersonal )
            + Double.valueOf( KANUtil.filterEmpty( billAmountPersonal ) == null ? "0" : billAmountPersonal ) );
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany == null ? "0" : this.costAmountCompany )
            + Double.valueOf( KANUtil.filterEmpty( costAmountCompany ) == null ? "0" : costAmountCompany ) );
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal == null ? "0" : this.costAmountPersonal )
            + Double.valueOf( KANUtil.filterEmpty( costAmountPersonal ) == null ? "0" : costAmountPersonal ) );
   }

   public void addTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = String.valueOf( Double.valueOf( this.taxAmountPersonal == null ? "0" : this.taxAmountPersonal )
            + Double.valueOf( KANUtil.filterEmpty( taxAmountPersonal ) == null ? "0" : taxAmountPersonal ) );
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

}
