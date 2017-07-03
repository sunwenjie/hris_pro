package com.kan.hro.domain.biz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1901327754738676836L;

   // ����ID
   private String groupId;

   // �ͻ����
   private String number;

   // �ͻ���������
   private String nameZH;

   // �ͻ�Ӣ������
   private String nameEN;

   private String titleZH;

   private String titleEN;

   // ����ID
   private String cityId;

   // ��ϵ��ַ
   private String address;

   // �ʱ�
   private String postcode;

   // ��Ҫ��ϵ��
   private String mainContact;

   // �绰����
   private String phone;

   // �ƶ��绰
   private String mobile;

   // �������
   private String fax;

   // �����ַ
   private String email;

   // IM1����
   private String im1Type;

   // IM1����
   private String im1;

   // IM2����
   private String im2Type;

   // IM2����
   private String im2;

   // ��վ��ַ
   private String website;

   // ��Ʊ����
   private String invoiceDate;

   // ֧������
   private String paymentTerms;

   // ������ҵ
   private String industry;

   // ��ҵ����
   private String type;

   // ��ҵ��ģ
   private String size;

   // ��˾���
   private String description;

   // �Ƽ���
   private String recommendPerson;

   // �Ƽ��˲���
   private String recommendBranch;

   // �Ƽ���ְλ
   private String recommendPosition;

   // ����ʵ��
   private String legalEntity;

   // ��������
   private String branch;

   // ������
   private String owner;

   // �����󶨺�ͬ����/��
   private String orderBindContract;

   private String logoFile;

   private String logoFileSize;

   private String imageFile;

   private String mobileModuleRightIds;

   // �籣�걨������1:������׼��2:������Ч��������
   private String sbGenerateCondition;

   // �̱��깺������1:������׼��2:������Ч��������
   private String cbGenerateCondition;

   // ���㴦��������1:������׼��2:������Ч��������
   private String settlementCondition;

   // �籣�걨������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
   private String sbGenerateConditionSC;

   // �̱��깺������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
   private String cbGenerateConditionSC;

   // ���㴦��������1:����Э����׼��2:����Э����£�3:����Э��鵵��������Э��
   private String settlementConditionSC;

   /**
    * For Application
    */
   // ��ѯ�ü�������
   private String groupNameForSearch;

   // ����������
   private String groupNameZH;

   // ����Ӣ����
   private String groupNameEN;

   // ���ű��
   private String groupNumber;

   // �������
   private String provinceId;

   private String cityIdTemp;

   // for mobile logon
   private String title;
   @JsonIgnore
   private List< MappingVO > provinces = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ҵ����
   private List< MappingVO > industryTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // IM����
   private List< MappingVO > IMTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ҵ��ģ
   private List< MappingVO > bizsizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ҵ����
   private List< MappingVO > biztypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // �����󶨺�ͬ����/��
   private List< MappingVO > orderBindContracts = new ArrayList< MappingVO >();
   @JsonIgnore
   // �籣�걨�����б� - ����
   private List< MappingVO > sbGenerateConditions = new ArrayList< MappingVO >();
   @JsonIgnore
   // �̱��깺�����б� - ����
   private List< MappingVO > cbGenerateConditions = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���㴦�������б� - ����
   private List< MappingVO > settlementConditions = new ArrayList< MappingVO >();
   @JsonIgnore
   // �籣�걨�����б� - ����Э��
   private List< MappingVO > sbGenerateConditionSCs = new ArrayList< MappingVO >();
   @JsonIgnore
   // �̱��깺�����б� - ����Э��
   private List< MappingVO > cbGenerateConditionSCs = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���㴦�������б� - ����Э��
   private List< MappingVO > settlementConditionSCs = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ͻ���Ӧ����ϵ��ID����
   private String[] clientContactIdArray = new String[] {};
   @JsonIgnore
   // �ͻ���Ӧ����ҵ��ͬID����
   private String[] clientContractIdArray = new String[] {};
   @JsonIgnore
   // �ͻ���Ӧ���˵���ַID����
   private String[] clientInvoiceIdArray = new String[] {};
   @JsonIgnore
   // �ͻ���Ӧ�Ķ���ID����
   private String[] clientOrderHeaderIdArray = new String[] {};
   @JsonIgnore
   // ����ʵ��
   private List< MappingVO > legalEntities = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ͻ�״̬
   private List< MappingVO > clientStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ʾ״̬<a>�ύ����
   private String isLink;
   @JsonIgnore
   // �ֻ�ģ��Ȩ��
   private List< MappingVO > mobileModuleRights = new ArrayList< MappingVO >();
   @JsonIgnore
   private String contactAddress;
   @JsonIgnore
   private String registeredAddress;
   @JsonIgnore
   private String clientContactNameZH;
   @JsonIgnore
   private String clientContactNameEN;
   @JsonIgnore
   private String contactPosition;
   @JsonIgnore
   private String contactWay;
   @JsonIgnore
   private String[] moduleIdArray;
   @JsonIgnore
   private String[] employeeModuleIdArray;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.IMTypes = KANUtil.getMappings( this.getLocale(), "business.client.IMtypes" );
      this.bizsizes = KANUtil.getMappings( this.getLocale(), "business.client.bizsizes" );
      this.biztypes = KANUtil.getMappings( this.getLocale(), "business.client.biztypes" );
      this.orderBindContracts = KANUtil.getMappings( this.getLocale(), "flag" );
      this.sbGenerateConditions = KANUtil.getMappings( this.getLocale(), "options.sb.process.condition" );
      this.cbGenerateConditions = KANUtil.getMappings( this.getLocale(), "options.cb.process.condition" );
      this.settlementConditions = KANUtil.getMappings( this.getLocale(), "options.settlement.process.condition" );
      this.sbGenerateConditionSCs = KANUtil.getMappings( request.getLocale(), "options.sb.process.condition.service.contract" );
      this.cbGenerateConditionSCs = KANUtil.getMappings( request.getLocale(), "options.cb.process.condition.service.contract" );
      this.settlementConditionSCs = KANUtil.getMappings( request.getLocale(), "options.settlement.process.condition.service.contract" );
      this.industryTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getIndustryTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.legalEntities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.clientStatuses = KANUtil.getMappings( request.getLocale(), "business.common.object.status" );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
      this.mobileModuleRights = KANUtil.getMappings( request.getLocale(), "options.mobile.module.right" );
   }

   // ��ȡ����������
   public String getDecodeOwner()
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   // �ͻ�ID����
   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // ����ʵ�����
   public String getEncodedLegalEntity() throws KANException
   {
      return encodedField( legalEntity );
   }

   // ����ID����
   public String getEncodedGroupId() throws KANException
   {
      return encodedField( groupId );
   }

   // ��ȡ����
   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   // ��ȡ��ҵ����
   public String getDecodeType()
   {
      return decodeField( this.type, this.biztypes );
   }

   // ��ȡ����ʵ������
   public String getDecodeLegalEntity()
   {
      return decodeField( this.legalEntity, this.legalEntities );
   }

   // ��ȡ������������
   public String getDecodeBranch()
   {
      return decodeField( this.branch, KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( super.getLocale().getLanguage() ) );
   }

   // ��ȡ��ҵ��ģ
   public String getDecodeSize()
   {
      return decodeField( this.size, this.bizsizes );
   }

   // ��ȡ��ҵ����
   public String getDecodeIndustry()
   {
      return decodeField( this.industry, this.industryTypes );
   }

   // ��ȡIM1����
   public String getDecodeIM1Type()
   {
      return decodeField( this.im1Type, this.IMTypes );
   }

   // ��ȡIM2����
   public String getDecodeIM2Type()
   {
      return decodeField( this.im2Type, this.IMTypes );
   }

   // ��ÿͻ�״̬
   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), clientStatuses );
   }

   public String getLogoFile()
   {
      return logoFile;
   }

   public void setLogoFile( String logoFile )
   {
      this.logoFile = logoFile;
   }

   public String getLogoFileSize()
   {
      return logoFileSize;
   }

   public void setLogoFileSize( String logoFileSize )
   {
      this.logoFileSize = logoFileSize;
   }

   public String getImageFile()
   {
      return imageFile;
   }

   public void setImageFile( String imageFile )
   {
      this.imageFile = imageFile;
   }

   public String getLogoFileName()
   {
      if ( KANUtil.filterEmpty( this.logoFile ) != null )
      {
         int index = this.logoFile.lastIndexOf( "/" );
         if ( index >= 0 )
         {
            return this.logoFile.substring( index + 1, this.logoFile.length() );
         }
      }
      return "";
   }

   @Override
   public void update( Object object )
   {
      final ClientVO clientVO = ( ClientVO ) object;
      this.groupId = clientVO.getGroupId();
      this.address = clientVO.getAddress();
      this.branch = clientVO.getBranch();
      this.cityId = clientVO.getCityId();
      this.description = clientVO.getDescription();
      this.email = clientVO.getEmail();
      this.fax = clientVO.getFax();
      this.im1 = clientVO.getIm1();
      this.im1Type = clientVO.getIm1Type();
      this.im2 = clientVO.getIm2();
      this.im2Type = clientVO.getIm2Type();
      this.industry = clientVO.getIndustry();
      this.invoiceDate = clientVO.getInvoiceDate();
      this.legalEntity = clientVO.getLegalEntity();
      this.mainContact = clientVO.getMainContact();
      this.mobile = clientVO.getMobile();
      this.nameZH = clientVO.getNameZH();
      this.nameEN = clientVO.getNameEN();
      this.titleZH = clientVO.getTitleZH();
      this.titleEN = clientVO.getTitleEN();
      this.number = clientVO.getNumber();
      this.owner = clientVO.getOwner();
      this.paymentTerms = clientVO.getPaymentTerms();
      this.phone = clientVO.getPhone();
      this.postcode = clientVO.getPostcode();
      this.recommendBranch = clientVO.getRecommendBranch();
      this.recommendPerson = clientVO.getRecommendPerson();
      this.recommendPosition = clientVO.getRecommendPosition();
      this.size = clientVO.getSize();
      this.type = clientVO.getType();
      this.website = clientVO.getWebsite();
      this.clientContactIdArray = clientVO.getClientContactIdArray();
      this.clientContractIdArray = clientVO.getClientContractIdArray();
      this.clientOrderHeaderIdArray = clientVO.getClientOrderHeaderIdArray();
      this.clientInvoiceIdArray = clientVO.getClientInvoiceIdArray();
      this.orderBindContract = clientVO.getOrderBindContract();
      this.logoFile = clientVO.getLogoFile();
      this.logoFileSize = clientVO.getLogoFileSize();
      this.imageFile = clientVO.getImageFile();
      this.sbGenerateCondition = clientVO.getSbGenerateCondition();
      this.cbGenerateCondition = clientVO.getCbGenerateCondition();
      this.settlementCondition = clientVO.getSettlementCondition();
      this.sbGenerateConditionSC = clientVO.getSbGenerateConditionSC();
      this.cbGenerateConditionSC = clientVO.getCbGenerateConditionSC();
      this.settlementConditionSC = clientVO.getSettlementConditionSC();
      this.mobileModuleRightIds = clientVO.getMobileModuleRightIds();
      super.setStatus( clientVO.getStatus() );
      super.setModifyBy( clientVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.groupId = "";
      this.address = "";
      this.branch = "";
      this.cityId = null;
      this.description = "";
      this.email = "";
      this.fax = "";
      this.im1 = "";
      this.im1Type = "0";
      this.im2 = "";
      this.im2Type = "0";
      this.industry = "";
      this.invoiceDate = "";
      this.legalEntity = "";
      this.mainContact = "";
      this.mobile = "";
      this.nameZH = "";
      this.nameEN = "";
      this.titleZH = "";
      this.titleEN = "";
      this.number = "";
      this.owner = "";
      this.paymentTerms = "";
      this.phone = "";
      this.postcode = "";
      this.orderBindContract = "0";
      this.logoFile = "";
      this.logoFileSize = "0";
      this.imageFile = "";
      this.mobileModuleRightIds = "";
      this.sbGenerateCondition = "0";
      this.cbGenerateCondition = "0";
      this.settlementCondition = "0";
      this.sbGenerateConditionSC = "0";
      this.cbGenerateConditionSC = "0";
      this.settlementConditionSC = "0";
      this.recommendBranch = "";
      this.recommendPerson = "";
      this.recommendPosition = "";
      this.size = "0";
      this.type = "0";
      this.website = "";
      super.setStatus( "0" );
   }

   public String getGroupId()
   {
      return KANUtil.filterEmpty( groupId );
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
   }

   public String getNumber()
   {
      return number;
   }

   public void setNumber( String number )
   {
      this.number = number;
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

   public String getAddress()
   {
      return address;
   }

   public void setAddress( String address )
   {
      this.address = address;
   }

   public String getCityId()
   {
      return KANUtil.filterEmpty( cityId );
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getMainContact()
   {
      return mainContact;
   }

   public void setMainContact( String mainContact )
   {
      this.mainContact = mainContact;
   }

   public String getPhone()
   {
      return phone;
   }

   public void setPhone( String phone )
   {
      this.phone = phone;
   }

   public String getMobile()
   {
      return mobile;
   }

   public void setMobile( String mobile )
   {
      this.mobile = mobile;
   }

   public String getFax()
   {
      return fax;
   }

   public void setFax( String fax )
   {
      this.fax = fax;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   public String getIm1Type()
   {
      return im1Type;
   }

   public void setIm1Type( String im1Type )
   {
      this.im1Type = im1Type;
   }

   public String getIm1()
   {
      return im1;
   }

   public void setIm1( String im1 )
   {
      this.im1 = im1;
   }

   public String getIm2Type()
   {
      return im2Type;
   }

   public void setIm2Type( String im2Type )
   {
      this.im2Type = im2Type;
   }

   public String getIm2()
   {
      return im2;
   }

   public void setIm2( String im2 )
   {
      this.im2 = im2;
   }

   public String getWebsite()
   {
      return website;
   }

   public void setWebsite( String website )
   {
      this.website = website;
   }

   public String getInvoiceDate()
   {
      return invoiceDate;
   }

   public void setInvoiceDate( String invoiceDate )
   {
      this.invoiceDate = invoiceDate;
   }

   public String getPaymentTerms()
   {
      return paymentTerms;
   }

   public void setPaymentTerms( String paymentTerms )
   {
      this.paymentTerms = paymentTerms;
   }

   public String getIndustry()
   {
      return industry;
   }

   public void setIndustry( String industry )
   {
      this.industry = industry;
   }

   public String getType()
   {
      return type;
   }

   public void setType( String type )
   {
      this.type = type;
   }

   public String getSize()
   {
      return size;
   }

   public void setSize( String size )
   {
      this.size = size;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getRecommendPerson()
   {
      return recommendPerson;
   }

   public void setRecommendPerson( String recommendPerson )
   {
      this.recommendPerson = recommendPerson;
   }

   public String getRecommendBranch()
   {
      return recommendBranch;
   }

   public void setRecommendBranch( String recommendBranch )
   {
      this.recommendBranch = recommendBranch;
   }

   public String getRecommendPosition()
   {
      return recommendPosition;
   }

   public void setRecommendPosition( String recommendPosition )
   {
      this.recommendPosition = recommendPosition;
   }

   public String getLegalEntity()
   {
      return legalEntity;
   }

   public void setLegalEntity( String legalEntity )
   {
      this.legalEntity = legalEntity;
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

   public String getPostcode()
   {
      return postcode;
   }

   public void setPostcode( String postcode )
   {
      this.postcode = postcode;
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

   public List< MappingVO > getIMTypes()
   {
      return IMTypes;
   }

   public void setIMTypes( List< MappingVO > iMTypes )
   {
      IMTypes = iMTypes;
   }

   public List< MappingVO > getBizsizes()
   {
      return bizsizes;
   }

   public void setBizsizes( List< MappingVO > bizsizes )
   {
      this.bizsizes = bizsizes;
   }

   public List< MappingVO > getBiztypes()
   {
      return biztypes;
   }

   public void setBiztypes( List< MappingVO > biztypes )
   {
      this.biztypes = biztypes;
   }

   public String getGroupName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return KANUtil.filterEmpty( groupNameZH );
         }
         else
         {
            return KANUtil.filterEmpty( groupNameEN );
         }
      }
      else
      {
         return groupNameZH;
      }
   }

   public String[] getClientContactIdArray()
   {
      return clientContactIdArray;
   }

   public void setClientContactIdArray( String[] clientContactIdArray )
   {
      this.clientContactIdArray = clientContactIdArray;
   }

   public List< MappingVO > getIndustryTypes()
   {
      return industryTypes;
   }

   public void setIndustryTypes( List< MappingVO > industryTypes )
   {
      this.industryTypes = industryTypes;
   }

   public String[] getClientInvoiceIdArray()
   {
      return clientInvoiceIdArray;
   }

   public void setClientInvoiceIdArray( String[] clientInvoiceIdArray )
   {
      this.clientInvoiceIdArray = clientInvoiceIdArray;
   }

   public String[] getClientContractIdArray()
   {
      return clientContractIdArray;
   }

   public void setClientContractIdArray( String[] clientContractIdArray )
   {
      this.clientContractIdArray = clientContractIdArray;
   }

   public String getClientName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return nameZH;
         }
         else
         {
            return nameEN;
         }
      }
      return nameZH;
   }

   public String getGroupNumber()
   {
      return groupNumber;
   }

   public void setGroupNumber( String groupNumber )
   {
      this.groupNumber = groupNumber;
   }

   public List< MappingVO > getLegalEntities()
   {
      return legalEntities;
   }

   public void setLegalEntities( List< MappingVO > legalEntities )
   {
      this.legalEntities = legalEntities;
   }

   public String getOrderBindContract()
   {
      return orderBindContract;
   }

   public void setOrderBindContract( String orderBindContract )
   {
      this.orderBindContract = orderBindContract;
   }

   public String getSbGenerateCondition()
   {
      return sbGenerateCondition;
   }

   public void setSbGenerateCondition( String sbGenerateCondition )
   {
      this.sbGenerateCondition = sbGenerateCondition;
   }

   public String getCbGenerateCondition()
   {
      return cbGenerateCondition;
   }

   public void setCbGenerateCondition( String cbGenerateCondition )
   {
      this.cbGenerateCondition = cbGenerateCondition;
   }

   public String getSettlementCondition()
   {
      return settlementCondition;
   }

   public void setSettlementCondition( String settlementCondition )
   {
      this.settlementCondition = settlementCondition;
   }

   public List< MappingVO > getOrderBindContracts()
   {
      return orderBindContracts;
   }

   public void setOrderBindContracts( List< MappingVO > orderBindContracts )
   {
      this.orderBindContracts = orderBindContracts;
   }

   public List< MappingVO > getSbGenerateConditions()
   {
      return sbGenerateConditions;
   }

   public void setSbGenerateConditions( List< MappingVO > sbGenerateConditions )
   {
      this.sbGenerateConditions = sbGenerateConditions;
   }

   public List< MappingVO > getCbGenerateConditions()
   {
      return cbGenerateConditions;
   }

   public void setCbGenerateConditions( List< MappingVO > cbGenerateConditions )
   {
      this.cbGenerateConditions = cbGenerateConditions;
   }

   public List< MappingVO > getSettlementConditions()
   {
      return settlementConditions;
   }

   public void setSettlementConditions( List< MappingVO > settlementConditions )
   {
      this.settlementConditions = settlementConditions;
   }

   public String[] getClientOrderHeaderIdArray()
   {
      return clientOrderHeaderIdArray;
   }

   public void setClientOrderHeaderIdArray( String[] clientOrderHeaderIdArray )
   {
      this.clientOrderHeaderIdArray = clientOrderHeaderIdArray;
   }

   public String getSbGenerateConditionSC()
   {
      return sbGenerateConditionSC;
   }

   public void setSbGenerateConditionSC( String sbGenerateConditionSC )
   {
      this.sbGenerateConditionSC = sbGenerateConditionSC;
   }

   public String getCbGenerateConditionSC()
   {
      return cbGenerateConditionSC;
   }

   public void setCbGenerateConditionSC( String cbGenerateConditionSC )
   {
      this.cbGenerateConditionSC = cbGenerateConditionSC;
   }

   public String getSettlementConditionSC()
   {
      return settlementConditionSC;
   }

   public void setSettlementConditionSC( String settlementConditionSC )
   {
      this.settlementConditionSC = settlementConditionSC;
   }

   public List< MappingVO > getSbGenerateConditionSCs()
   {
      return sbGenerateConditionSCs;
   }

   public void setSbGenerateConditionSCs( List< MappingVO > sbGenerateConditionSCs )
   {
      this.sbGenerateConditionSCs = sbGenerateConditionSCs;
   }

   public List< MappingVO > getCbGenerateConditionSCs()
   {
      return cbGenerateConditionSCs;
   }

   public void setCbGenerateConditionSCs( List< MappingVO > cbGenerateConditionSCs )
   {
      this.cbGenerateConditionSCs = cbGenerateConditionSCs;
   }

   public List< MappingVO > getSettlementConditionSCs()
   {
      return settlementConditionSCs;
   }

   public void setSettlementConditionSCs( List< MappingVO > settlementConditionSCs )
   {
      this.settlementConditionSCs = settlementConditionSCs;
   }

   public List< MappingVO > getClientStatuses()
   {
      return clientStatuses;
   }

   public void setClientStatuses( List< MappingVO > clientStatuses )
   {
      this.clientStatuses = clientStatuses;
   }

   public String getIsLink() throws KANException
   {
      if ( "1".equals( super.getStatus() ) || "4".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">�ύ</a>";
      }
      return isLink;
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   @Override
   public ActionErrors validate( ActionMapping mapping, HttpServletRequest request )
   {
      ActionErrors error = new ActionErrors();
      if ( this.getClientId().length() == 0 )
      {
         //usernameError����Դ�������ļ�(ApplicationResources.properties)�е�keyֵһ�¡�username��ĳһ��jspҳ��������Ҫʹ�õ��Ĵ�������һ��
         error.add( "username", new ActionMessage( "usernameError" ) );
      }
      return error;
   }

   public String getGroupNameZH()
   {
      return groupNameZH;
   }

   public void setGroupNameZH( String groupNameZH )
   {
      this.groupNameZH = groupNameZH;
   }

   public String getGroupNameEN()
   {
      return groupNameEN;
   }

   public void setGroupNameEN( String groupNameEN )
   {
      this.groupNameEN = groupNameEN;
   }

   public String getGroupNameForSearch()
   {
      return groupNameForSearch;
   }

   public void setGroupNameForSearch( String groupNameForSearch )
   {
      this.groupNameForSearch = groupNameForSearch;
   }

   public String getMobileModuleRightIds()
   {
      return mobileModuleRightIds;
   }

   public void setMobileModuleRightIds( String mobileModuleRightIds )
   {
      this.mobileModuleRightIds = mobileModuleRightIds;
   }

   public List< MappingVO > getMobileModuleRights()
   {
      return mobileModuleRights;
   }

   public void setMobileModuleRights( List< MappingVO > mobileModuleRights )
   {
      this.mobileModuleRights = mobileModuleRights;
   }

   public String getTitleZH()
   {
      return titleZH;
   }

   public void setTitleZH( String titleZH )
   {
      this.titleZH = titleZH;
   }

   public String getTitleEN()
   {
      return titleEN;
   }

   public void setTitleEN( String titleEN )
   {
      this.titleEN = titleEN;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle( String title )
   {
      this.title = title;
   }

   public String getContactAddress()
   {
      return contactAddress;
   }

   public void setContactAddress( String contactAddress )
   {
      this.contactAddress = contactAddress;
   }

   public String getRegisteredAddress()
   {
      return registeredAddress;
   }

   public void setRegisteredAddress( String registeredAddress )
   {
      this.registeredAddress = registeredAddress;
   }

   public String getClientContactName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.clientContactNameZH;
         }
         else
         {
            return this.clientContactNameEN;
         }
      }
      else
      {
         return this.clientContactNameZH;
      }
   }

   public String getContactPosition()
   {
      return contactPosition;
   }

   public void setContactPosition( String contactPosition )
   {
      this.contactPosition = contactPosition;
   }

   public String getContactWay()
   {
      return contactWay;
   }

   public void setContactWay( String contactWay )
   {
      this.contactWay = contactWay;
   }

   public String getClientContactNameZH()
   {
      return clientContactNameZH;
   }

   public void setClientContactNameZH( String clientContactNameZH )
   {
      this.clientContactNameZH = clientContactNameZH;
   }

   public String getClientContactNameEN()
   {
      return clientContactNameEN;
   }

   public void setClientContactNameEN( String clientContactNameEN )
   {
      this.clientContactNameEN = clientContactNameEN;
   }

   public String[] getModuleIdArray()
   {
      return moduleIdArray;
   }

   public void setModuleIdArray( String[] moduleIdArray )
   {
      this.moduleIdArray = moduleIdArray;
   }

   public String[] getEmployeeModuleIdArray()
   {
      return employeeModuleIdArray;
   }

   public void setEmployeeModuleIdArray( String[] employeeModuleIdArray )
   {
      this.employeeModuleIdArray = employeeModuleIdArray;
   }

   public static long getSerialversionuid()
   {
      return serialVersionUID;
   }
}
