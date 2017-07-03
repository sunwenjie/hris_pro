package com.kan.base.domain.management;

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

public class ItemVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 3893666462190273343L;

   /**
    * For DB
    */

   // 科目ID
   private String itemId;

   // 科目编号
   private String itemNo;

   // 科目名称（中文）
   private String nameZH;

   // 科目名称（英文）
   private String nameEN;

   // 科目上限（防止科目输入过大金额）
   private String itemCap;

   // 科目下限（防止科目输入过小金额）
   private String itemFloor;

   // 科目顺序
   private String sequence;

   // 是否计算个税
   private String personalTax;

   // 是否含营业税
   private String companyTax;

   // 计算类型
   private String calculateType;

   // 科目类型
   private String itemType;

   // 个人收入比率
   private String billRatePersonal;

   // 公司营收比率
   private String billRateCompany;

   // 个人支出比率
   private String costRatePersonal;

   // 公司成本比率
   private String costRateCompany;

   // 固定金（个人收入）
   private String billFixPersonal;

   // 固定金（公司营收）
   private String billFixCompany;

   // 固定金（个人支出）
   private String costFixPersonal;

   // 固定金（公司成本）
   private String costFixCompany;

   // 代扣个税（个人）
   private String personalTaxAgent;

   // 科目描述
   private String description;

   //是否级联创建工作单列表 1 创建 0 不创建
   private String isCascade;

   /**
    * For Application
    */
   @JsonIgnore
   // 计算类型
   private List< MappingVO > calculateTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 科目类型
   private List< MappingVO > itemTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 科目分组
   private List< MappingVO > itemGroups = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.calculateTypies = KANUtil.getMappings( request.getLocale(), "item.calculate.type" );
      this.itemTypies = KANUtil.getMappings( request.getLocale(), "item.type" );
      this.itemGroups = KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( request.getLocale().getLanguage() );
      if ( itemGroups != null )
      {
         this.itemGroups.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.itemCap = "";
      this.itemFloor = "";
      this.sequence = "";
      this.personalTax = "";
      this.companyTax = "";
      this.calculateType = "0";
      this.itemType = "0";
      this.billRatePersonal = "";
      this.billRateCompany = "";
      this.costRatePersonal = "";
      this.costRateCompany = "";
      this.personalTaxAgent = "";
      this.billFixPersonal = "";
      this.billFixCompany = "";
      this.costFixPersonal = "";
      this.costFixCompany = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ItemVO itemVO = ( ItemVO ) object;
      this.itemNo = itemVO.getItemNo();
      this.nameZH = itemVO.getNameZH();
      this.nameEN = itemVO.getNameEN();
      this.itemCap = itemVO.getItemCap();
      this.itemFloor = itemVO.getItemFloor();
      this.sequence = itemVO.getSequence();
      this.personalTax = itemVO.getPersonalTax();
      this.companyTax = itemVO.getCompanyTax();
      this.calculateType = itemVO.getCalculateType();
      this.itemType = itemVO.getItemType();
      this.billRatePersonal = itemVO.getBillRatePersonal();
      this.billRateCompany = itemVO.getBillRateCompany();
      this.costRatePersonal = itemVO.getCostRatePersonal();
      this.costRateCompany = itemVO.getCostRateCompany();
      this.billFixPersonal = itemVO.getBillFixPersonal();
      this.billFixCompany = itemVO.getBillFixCompany();
      this.costFixPersonal = itemVO.getCostFixPersonal();
      this.costFixCompany = itemVO.getCostFixCompany();
      this.personalTaxAgent = itemVO.getPersonalTaxAgent();
      this.description = itemVO.getDescription();
      super.setStatus( itemVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodePersonalTax()
   {
      return decodeField( personalTax, super.getFlags() );
   }

   public String getDecodeCalculateType()
   {
      return decodeField( calculateType, calculateTypies );
   }

   public String getDecodeItemType()
   {
      return decodeField( itemType, itemTypies );
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
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

   public String getItemCap()
   {
      return KANUtil.filterEmpty( itemCap );
   }

   public void setItemCap( String itemCap )
   {
      this.itemCap = itemCap;
   }

   public String getItemFloor()
   {
      return KANUtil.filterEmpty( itemFloor );
   }

   public void setItemFloor( String itemFloor )
   {
      this.itemFloor = itemFloor;
   }

   public String getSequence()
   {
      return KANUtil.filterEmpty( sequence );
   }

   public void setSequence( String sequence )
   {
      this.sequence = sequence;
   }

   public String getPersonalTax()
   {
      return personalTax;
   }

   public void setPersonalTax( String personalTax )
   {
      this.personalTax = personalTax;
   }

   public String getCompanyTax()
   {
      return companyTax;
   }

   public void setCompanyTax( String companyTax )
   {
      this.companyTax = companyTax;
   }

   public String getCalculateType()
   {
      return calculateType;
   }

   public void setCalculateType( String calculateType )
   {
      this.calculateType = calculateType;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }

   public String getBillRatePersonal()
   {
      return KANUtil.filterEmpty( billRatePersonal );
   }

   public void setBillRatePersonal( String billRatePersonal )
   {
      this.billRatePersonal = billRatePersonal;
   }

   public String getBillRateCompany()
   {
      return KANUtil.filterEmpty( billRateCompany );
   }

   public void setBillRateCompany( String billRateCompany )
   {
      this.billRateCompany = billRateCompany;
   }

   public String getCostRatePersonal()
   {
      return KANUtil.filterEmpty( costRatePersonal );
   }

   public void setCostRatePersonal( String costRatePersonal )
   {
      this.costRatePersonal = costRatePersonal;
   }

   public String getCostRateCompany()
   {
      return KANUtil.filterEmpty( costRateCompany );
   }

   public void setCostRateCompany( String costRateCompany )
   {
      this.costRateCompany = costRateCompany;
   }

   public String getPersonalTaxAgent()
   {
      return personalTaxAgent;
   }

   public void setPersonalTaxAgent( String personalTaxAgent )
   {
      this.personalTaxAgent = personalTaxAgent;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( itemId );
   }

   public List< MappingVO > getCalculateTypies()
   {
      return calculateTypies;
   }

   public void setCalculateTypies( List< MappingVO > calculateTypies )
   {
      this.calculateTypies = calculateTypies;
   }

   public List< MappingVO > getItemTypies()
   {
      return itemTypies;
   }

   public void setItemTypies( List< MappingVO > itemTypies )
   {
      this.itemTypies = itemTypies;
   }

   public List< MappingVO > getItemGroups()
   {
      return itemGroups;
   }

   public void setItemGroups( List< MappingVO > itemGroups )
   {
      this.itemGroups = itemGroups;
   }

   public String getBillFixPersonal()
   {
      return billFixPersonal;
   }

   public void setBillFixPersonal( String billFixPersonal )
   {
      this.billFixPersonal = billFixPersonal;
   }

   public String getBillFixCompany()
   {
      return billFixCompany;
   }

   public void setBillFixCompany( String billFixCompany )
   {
      this.billFixCompany = billFixCompany;
   }

   public String getCostFixPersonal()
   {
      return costFixPersonal;
   }

   public void setCostFixPersonal( String costFixPersonal )
   {
      this.costFixPersonal = costFixPersonal;
   }

   public String getCostFixCompany()
   {
      return costFixCompany;
   }

   public void setCostFixCompany( String costFixCompany )
   {
      this.costFixCompany = costFixCompany;
   }

   public String getIsCascade()
   {
      return isCascade;
   }

   public void setIsCascade( String isCascade )
   {
      this.isCascade = isCascade;
   }

}
