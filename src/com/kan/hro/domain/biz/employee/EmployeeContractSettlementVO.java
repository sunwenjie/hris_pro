package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractSettlementVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986731492707996651L;

   // 雇员成本/营收Id
   private String employeeSettlementId;

   // 合同ID
   private String contractId;

   // 元素ID
   private String itemId;

   // 元素分组
   private String baseFrom;

   // 拆分方式
   private String divideType;

   // 百分比
   private String percentage;

   // 固定金
   private String fix;

   // 生效时间
   private String startDate;

   //结束时间
   private String endDate;

   // 上限
   private String resultCap;

   // 下限
   private String resultFloor;

   // 描述
   private String description;

   // for app

   private List< MappingVO > itemVOs = new ArrayList< MappingVO >();

   private List< MappingVO > itemGroupVOs = new ArrayList< MappingVO >();

   public String getEmployeeSettlementId()
   {
      return employeeSettlementId;
   }

   public void setEmployeeSettlementId( String employeeSettlementId )
   {
      this.employeeSettlementId = employeeSettlementId;
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

   public String getBaseFrom()
   {
      return baseFrom;
   }

   public void setBaseFrom( String baseFrom )
   {
      this.baseFrom = baseFrom;
   }

   public String getDivideType()
   {
      return divideType;
   }

   public void setDivideType( String divideType )
   {
      this.divideType = divideType;
   }

   public String getPercentage()
   {
      return percentage;
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getFix()
   {
      return fix;
   }

   public void setFix( String fix )
   {
      this.fix = fix;
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

   public String getResultCap()
   {
      return resultCap;
   }

   public void setResultCap( String resultCap )
   {
      this.resultCap = resultCap;
   }

   public String getResultFloor()
   {
      return resultFloor;
   }

   public void setResultFloor( String resultFloor )
   {
      this.resultFloor = resultFloor;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getItemVOs()
   {
      return itemVOs;
   }

   public void setItemVOs( List< MappingVO > itemVOs )
   {
      this.itemVOs = itemVOs;
   }

   public List< MappingVO > getItemGroupVOs()
   {
      return itemGroupVOs;
   }

   public void setItemGroupVOs( List< MappingVO > itemGroupVOs )
   {
      this.itemGroupVOs = itemGroupVOs;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeSettlementId );
   }

   @Override
   public void reset() throws KANException
   {
      contractId = "";
      itemId = "0";
      baseFrom = "0";
      divideType = "0";
      percentage = "";
      fix = "";
      startDate = "";
      endDate = "";
      resultCap = "";
      resultFloor = "";
      description = "";
      super.setStatus( "" );
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      itemGroupVOs = KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( getLocale().getLanguage() );
      List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( super.getAccountId() ).ITEM_VO;
      for ( ItemVO itemVO : itemVOs )
      {
         this.itemVOs.add( new MappingVO( itemVO.getItemId(), "zh".equalsIgnoreCase( getLocale().getLanguage() ) ? itemVO.getNameZH() : itemVO.getNameZH() ) );
      }
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) object;
      contractId = employeeContractSettlementVO.getContractId();
      itemId = employeeContractSettlementVO.getItemId();
      baseFrom = employeeContractSettlementVO.getBaseFrom();
      divideType = employeeContractSettlementVO.getDivideType();
      percentage = employeeContractSettlementVO.getPercentage();
      fix = employeeContractSettlementVO.getFix();
      startDate = employeeContractSettlementVO.getStartDate();
      endDate = employeeContractSettlementVO.getEndDate();
      resultCap = employeeContractSettlementVO.getResultCap();
      resultFloor = employeeContractSettlementVO.getResultFloor();
      description = employeeContractSettlementVO.getDescription();
      super.setStatus( employeeContractSettlementVO.getStatus() );
   }

   public String getShowTitle()
   {
      String result = "";
      if ( KANUtil.filterEmpty( itemId ) != null && !"0".equals( itemId ) )
      {
         result = decodeField( itemId, itemVOs );
      }
      else
      {
         result = decodeField( baseFrom, itemGroupVOs );
      }
      return result;
   }

}
