package com.kan.hro.domain.biz.settlement;

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

/**  
 * 项目名称：HRO_V1  
 * 类名称：BatchTempVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-11  
 */
public class BatchTempVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = -2213525834069584655L;

   // 批次Id
   private String batchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 订单Id
   private String orderId;

   // 服务协议ID
   private String contractId;

   // 月份（例如2013/9）
   private String monthly;

   // 周次（例如2013/35）
   private String weekly;

   // 工资 - 批次包含工资
   private String containSalary;

   // 社保 - 批次包含社保
   private String containSB;

   // 商保 - 批次包含商保
   private String containCB;

   // 服务费 - 批次包含服务费
   private String containServiceFee;

   // 其他  - 批次包含其他
   private String containOther;

   // 会计期
   private String accountPeriod;

   // 开始时间 - 指Batch运行的时间
   private String startDate;

   // 结束时间 - 指Batch运行的时间
   private String endDate;

   // 描述
   private String description;

   /**
    * For App
    */
   // 财务编码/客户编号
   private String clientNumber;

   // 客户名称
   private String clientNameZH;

   // 客户英文名称
   private String clientNameEN;

   // 个人收入汇总
   private String billAmountPersonal;

   // 公司营收汇总
   private String billAmountCompany;

   // 公司成本汇总
   private String costAmountCompany;

   // 个人支出汇总
   private String costAmountPersonal;

   // 包含客户数量
   private String countClientId;

   // 包含订单数量
   private String countOrderId;

   // 包含服务协议数量
   private String countContractId;

   // 包含科目数量
   private String countItemId;

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 周次
   private List< MappingVO > weeklies = new ArrayList< MappingVO >();

   /**
    * 页面跳转用字段
    */

   // 当前页面类型标记(batch, header, contract, detail)
   private String pageFlag;

   // 包含的员工集合（MappingVO中mappingId对应雇员ID、mappingValue对应雇员中文名、mappingTemp对应雇员英文名）
   private List< MappingVO > employees = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage() );
      this.weeklies = KANConstants.getKANAccountConstants( super.getAccountId() ).getWeeks( this.getLocale().getLanguage() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( this.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( this.getLocale().getLanguage(), super.getCorpId() );

      // 添加"请选择"选项
      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }
      if ( this.weeklies != null )
      {
         this.weeklies.add( 0, getEmptyMappingVO() );
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
   public void update( final Object object )
   {
      final BatchTempVO batchTempVO = ( BatchTempVO ) object;
      this.entityId = batchTempVO.getEntityId();
      this.businessTypeId = batchTempVO.getBusinessTypeId();
      this.orderId = batchTempVO.getOrderId();
      this.monthly = batchTempVO.getMonthly();
      this.weekly = batchTempVO.getWeekly();
      this.containSalary = batchTempVO.getContainSalary();
      this.containSB = batchTempVO.getContainSB();
      this.containCB = batchTempVO.getContainCB();
      this.containServiceFee = batchTempVO.getContainServiceFee();
      this.containOther = batchTempVO.getContainOther();
      this.accountPeriod = batchTempVO.getAccountPeriod();
      this.startDate = batchTempVO.getStartDate();
      this.endDate = batchTempVO.getEndDate();
      this.description = batchTempVO.getDescription();
      super.setStatus( batchTempVO.getStatus() );
      super.setModifyBy( batchTempVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.orderId = "";
      this.monthly = "";
      this.weekly = "";
      this.containSalary = "";
      this.containSB = "";
      this.containCB = "";
      this.containServiceFee = "";
      this.containOther = "";
      this.accountPeriod = "";
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

   // 订单ID编码
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // 获得法务实体名称
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( this.getEntityId(), this.entitys );
   }

   // 获得业务类型名称
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.getBusinessTypeId(), this.businessTypes );
   }

   // 是否包含工资
   public String getDecodeContainSalary() throws KANException
   {
      return decodeField( this.getContainSalary(), super.getFlags() );
   }

   // 是否包含社保
   public String getDecodeContainSB() throws KANException
   {
      return decodeField( this.containSB, super.getFlags() );
   }

   // 是否包含商保
   public String getDecodeContainCB() throws KANException
   {
      return decodeField( this.getContainCB(), super.getFlags() );
   }

   // 是否包含服务费
   public String getDecodeContainServiceFee() throws KANException
   {
      return decodeField( this.getContainServiceFee(), super.getFlags() );
   }

   // 是否包含其他
   public String getDecodeContainOther() throws KANException
   {
      return decodeField( this.getContainOther(), super.getFlags() );
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
      this.entityId = KANUtil.filterEmpty( entityId );
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = KANUtil.filterEmpty( businessTypeId );
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = KANUtil.filterEmpty( orderId );
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

   public String getContainSalary()
   {
      return containSalary;
   }

   public void setContainSalary( String containSalary )
   {
      this.containSalary = containSalary;
   }

   public String getContainSB()
   {
      return containSB;
   }

   public void setContainSB( String containSB )
   {
      this.containSB = containSB;
   }

   public String getContainServiceFee()
   {
      return containServiceFee;
   }

   public void setContainServiceFee( String containServiceFee )
   {
      this.containServiceFee = containServiceFee;
   }

   public String getContainOther()
   {
      return containOther;
   }

   public void setContainOther( String containOther )
   {
      this.containOther = containOther;
   }

   public String getAccountPeriod()
   {
      return KANUtil.filterEmpty( decodeDate( this.accountPeriod ) );
   }

   public void setAccountPeriod( String accountPeriod )
   {
      this.accountPeriod = accountPeriod;
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

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = KANUtil.filterEmpty( contractId );
   }

   public String getContainCB()
   {
      return containCB;
   }

   public void setContainCB( String containCB )
   {
      this.containCB = containCB;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
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

   public List< MappingVO > getWeeklies()
   {
      return weeklies;
   }

   public void setWeeklies( List< MappingVO > weeklies )
   {
      this.weeklies = weeklies;
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

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
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

   public String getCountClientId()
   {
      return countClientId;
   }

   public void setCountClientId( String countClientId )
   {
      this.countClientId = countClientId;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
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

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

}
