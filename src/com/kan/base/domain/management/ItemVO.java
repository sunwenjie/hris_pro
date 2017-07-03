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

   // ��ĿID
   private String itemId;

   // ��Ŀ���
   private String itemNo;

   // ��Ŀ���ƣ����ģ�
   private String nameZH;

   // ��Ŀ���ƣ�Ӣ�ģ�
   private String nameEN;

   // ��Ŀ���ޣ���ֹ��Ŀ��������
   private String itemCap;

   // ��Ŀ���ޣ���ֹ��Ŀ�����С��
   private String itemFloor;

   // ��Ŀ˳��
   private String sequence;

   // �Ƿ�����˰
   private String personalTax;

   // �Ƿ�Ӫҵ˰
   private String companyTax;

   // ��������
   private String calculateType;

   // ��Ŀ����
   private String itemType;

   // �����������
   private String billRatePersonal;

   // ��˾Ӫ�ձ���
   private String billRateCompany;

   // ����֧������
   private String costRatePersonal;

   // ��˾�ɱ�����
   private String costRateCompany;

   // �̶��𣨸������룩
   private String billFixPersonal;

   // �̶��𣨹�˾Ӫ�գ�
   private String billFixCompany;

   // �̶��𣨸���֧����
   private String costFixPersonal;

   // �̶��𣨹�˾�ɱ���
   private String costFixCompany;

   // ���۸�˰�����ˣ�
   private String personalTaxAgent;

   // ��Ŀ����
   private String description;

   //�Ƿ��������������б� 1 ���� 0 ������
   private String isCascade;

   /**
    * For Application
    */
   @JsonIgnore
   // ��������
   private List< MappingVO > calculateTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ŀ����
   private List< MappingVO > itemTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ŀ����
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
