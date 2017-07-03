package com.kan.hro.domain.biz.sb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

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
public class SBBatchVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = 8535391816205528726L;

   // 批次Id
   private String batchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 社保城市Id，多数情况按照社保城市操作
   private String cityId;

   // 订单Id
   private String orderId;

   // 服务协议Id
   private String contractId;

   // 账单月份
   private String monthly;

   // 开始时间 - 指Batch运行的时间
   private String startDate;

   // 结束时间 - 指Batch运行的时间
   private String endDate;

   // 描述
   private String description;

   //社保方案类型
   private String sbType;

   /**
    * For App
    */
   // 客户名称
   private String clientName;

   // 财务编码
   private String clientNumber;

   // flag 社保实际缴纳标识。0正常，1未缴纳
   private String flag;

   // 包含的员工集合（MappingVO中mappingId对应雇员ID、mappingValue对应雇员中文名、mappingTemp对应雇员英文名）
   private List< MappingVO > employees = new ArrayList< MappingVO >();

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 供应商
   private List< MappingVO > vendors = new ArrayList< MappingVO >();

   //社保方案类型 1公积金 2社保 3其他
   private List< MappingVO > sbTypes = new ArrayList< MappingVO >();

   // 社保状态
   private List< MappingVO > sbStatuses = new ArrayList< MappingVO >();

   // 社保状态数组形式
   private String[] sbStatusArray = new String[] {};

   private List< MappingVO > flags = new ArrayList< MappingVO >();

   // 供应商ID
   private String vendorId;

   // 个人费用汇总
   private String amountPersonal;

   // 公司费用汇总
   private String amountCompany;

   // 包含客户数量
   private String countClientId;

   // 包含社保方案数量
   private String countHeaderId;

   // 包含服务协议数量
   private String countContractId;

   // 包含订单数量
   private String countOrderId;

   // 包含科目数量
   private String countItemId;

   private String provinceId;

   private String cityIdTemp;

   // 额外状态 - 社保方案明细的最小状态
   private String additionalStatus;

   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   /**
    * 页面跳转用字段
    */
   // 社保方案Id
   private String headerId;

   // 社保方案明细Id
   private String detailId;

   // 当前页面状态
   private String statusFlag;

   // 当前页面类型
   private String pageFlag;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.header.status" ) );
      this.sbStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.sb.statuses" );
      this.sbTypes = KANUtil.getMappings( this.getLocale(), "def.socialBenefit.solution.sbType" );
      //只保留待申报加保、正常缴纳、待申报退保
      if ( this.sbStatuses != null )
      {
         Iterator< MappingVO > iterator = sbStatuses.iterator();
         while ( iterator.hasNext() )
         {
            MappingVO m = ( MappingVO ) iterator.next();
            if ( "0".equals( m.getMappingId() ) || "1".equals( m.getMappingId() ) || "4".equals( m.getMappingId() ) || "6".equals( m.getMappingId() )
                  || "7".equals( m.getMappingId() ) )
            {
               iterator.remove();
            }
         }
      }

      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );

      // 添加"请选择"选项
      if ( this.provinces != null )
      {
         this.provinces.add( 0, getEmptyMappingVO() );
      }

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

      // 雇员姓名排序
      if ( this.employees != null && this.employees.size() > 1 )
      {
         Collections.sort( employees, new Comparator< MappingVO >()
         {
            @Override
            public int compare( MappingVO o1, MappingVO o2 )
            {
               // 中文名称排序
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  return o1.getMappingValue().compareTo( o2.getMappingValue() );
               }
               // 英文排序
               else
               {
                  if ( o1.getMappingTemp() == null || o2.getMappingTemp() == null )
                     return 0;
                  return o1.getMappingTemp().compareTo( o2.getMappingTemp() );
               }
            }
         } );
      }

      flags = KANUtil.getMappings( getLocale(), "sb.status.flag" );
   }

   @Override
   public void update( final Object object )
   {
      final SBBatchVO sbBatchVO = ( SBBatchVO ) object;
      this.entityId = sbBatchVO.getEntityId();
      this.businessTypeId = sbBatchVO.getBusinessTypeId();
      this.cityId = sbBatchVO.getCityId();
      this.orderId = sbBatchVO.getOrderId();
      this.contractId = sbBatchVO.getContractId();
      this.monthly = sbBatchVO.getMonthly();
      this.description = sbBatchVO.getDescription();
      super.setStatus( sbBatchVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.cityId = "0";
      this.orderId = "";
      this.contractId = "";
      this.monthly = "0";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      super.setClientId( "" );
      super.setStatus( "0" );
   }

   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
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

   // 订单ID编码
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
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

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getOrderId()
   {
      return KANUtil.filterEmpty( orderId );
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
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

   public String getProvinceId()
   {
      return provinceId;
   }

   public void setProvinceId( String provinceId )
   {
      this.provinceId = provinceId;
   }

   public String getCityIdTemp()
   {
      return cityIdTemp;
   }

   public void setCityIdTemp( String cityIdTemp )
   {
      this.cityIdTemp = cityIdTemp;
   }

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
   }

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
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

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getAmountPersonal()
   {
      return amountPersonal;
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getDecodeAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public String getAmountCompany()
   {
      return amountCompany;
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getDecodeAmountCompany()
   {
      return formatNumber( amountCompany );
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

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
   }

   public final String getAdditionalStatus()
   {
      return additionalStatus;
   }

   public final void setAdditionalStatus( String additionalStatus )
   {
      this.additionalStatus = additionalStatus;
   }

   public final String getDecodeAdditionalStatus()
   {
      return decodeField( this.additionalStatus, KANUtil.getMappings( this.getLocale(), "business.sb.header.status" ) );
   }

   public List< MappingVO > getVendors()
   {
      return vendors;
   }

   public void setVendors( List< MappingVO > vendors )
   {
      this.vendors = vendors;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public List< MappingVO > getEmployees()
   {
      return employees;
   }

   public void setEmployees( List< MappingVO > employees )
   {
      this.employees = employees;
   }

   // 获得员工数量
   public String getCountEmployeeId()
   {
      return String.valueOf( employees.size() );
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

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

   public List< MappingVO > getSbStatuses()
   {
      return sbStatuses;
   }

   public void setSbStatuses( List< MappingVO > sbStatuses )
   {
      this.sbStatuses = sbStatuses;
   }

   public List< MappingVO > getSbTypes()
   {
      return sbTypes;
   }

   public void setSbTypes( List< MappingVO > sbTypes )
   {
      this.sbTypes = sbTypes;
   }

   // 获得社保类型名称
   public String getDecodeSBType() throws KANException
   {
      return decodeField( this.sbType, this.sbTypes );
   }

   public String[] getSbStatusArray()
   {
      return sbStatusArray;
   }

   public void setSbStatusArray( String[] sbStatusArray )
   {
      this.sbStatusArray = sbStatusArray;
   }

   public String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public String getFlag()
   {
      return flag;
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
   }

   public List< MappingVO > getFlags()
   {
      return flags;
   }

   public void setFlags( List< MappingVO > flags )
   {
      this.flags = flags;
   }

}
