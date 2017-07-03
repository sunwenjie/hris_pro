package com.kan.hro.domain.biz.vendor;

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

public class VendorVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 8942558666927730441L;

   /**
    * For DB
    */

   // ��Ӧ��Id
   private String vendorId;

   // ��Ӧ�����ƣ����ģ�
   private String nameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String nameEN;

   // ���ڳ���ID
   private String cityId;

   // ��ͬ��ʼ����
   private String contractStartDate;

   // ��ͬ��������
   private String contractEndDate;

   // ��Ӧ��ִ�պ�
   private String charterNumber;

   // ����ID
   private String bankId;

   // ��Ӧ����������
   private String bankName;

   // ��Ӧ�������˺�
   private String bankAccount;

   // ��Ӧ�������˺���
   private String bankAccountName;

   // ��Ӧ������ ���ⲿ�ڲ���
   private String type;

   // ����
   private String attachment;

   // ����
   private String description;

   // ����ʵ��
   private String legalEntity;

   // �����˲���
   private String branch;

   // ������
   private String owner;

   // ��ϵ��ַ
   private String address;

   // �ʱ�
   private String postcode;

   // ��Ҫ��ϵ��
   private String mainContact;

   // �绰����
   private String phone;

   // �������
   private String fax;

   // �����ַ
   private String email;

   /**
    * For Application
    */
   // ʡID
   private String provinceId;

   private String cityIdTemp;

   private List< MappingVO > provinces;
   @JsonIgnore
   // ��Ӧ������
   private List< MappingVO > types = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����ʵ��
   private List< MappingVO > legalEntities = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������
   private List< MappingVO > serviceContents = new ArrayList< MappingVO >();

   // �����˲��� - ����
   private String ownerName;

   // Ĭ����ϵ������
   private String contactName;

   // Ĭ����ϵ�����������ģ�
   private String contactNameZH;

   // Ĭ����ϵ��������Ӣ�ģ�
   private String contactNameEN;

   // ����
   private String[] attachmentArray = new String[] {};

   // <a></a>
   private String isLink;

   /**
    * ҳ����ת���ֶ�
    */
   // �籣����Id
   private String headerId;

   // �籣������ϸId
   private String detailId;

   // ��ǰҳ��״̬
   private String statusFlag;

   // ��ǰҳ������
   private String pageFlag;

   private String otherLink;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.common.object.status" ) );
      this.types = KANUtil.getMappings( request.getLocale(), "business.vendor.type" );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );

      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }

      this.serviceContents = KANUtil.getMappings( request.getLocale(), "business.vendor.service.item" );

      this.legalEntities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );

      if ( this.legalEntities != null )
      {
         this.legalEntities.add( 0, super.getEmptyMappingVO() );
      }

      this.ownerName = KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );

      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         this.contactName = this.contactNameZH;
      else
         this.contactName = this.contactNameEN;
   }

   @Override
   public void reset() throws KANException
   {
      this.vendorId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.cityId = "0";
      this.contractStartDate = "";
      this.contractEndDate = "";
      this.charterNumber = "";
      this.bankId = "0";
      this.bankName = "";
      this.bankAccount = "";
      this.bankAccountName = "";
      this.type = "0";
      this.attachment = "";
      this.description = "";
      this.legalEntity = "0";
      this.branch = "0";
      this.address = "";
      this.postcode = "";
      this.mainContact = "";
      this.phone = "";
      this.fax = "";
      this.email = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final VendorVO vendorVO = ( VendorVO ) object;
      this.nameZH = vendorVO.getNameZH();
      this.nameEN = vendorVO.getNameEN();
      this.cityId = vendorVO.getCityId();
      this.contractStartDate = vendorVO.getContractStartDate();
      this.contractEndDate = vendorVO.getContractEndDate();
      this.charterNumber = vendorVO.getCharterNumber();
      this.bankId = vendorVO.getBankId();
      this.bankName = vendorVO.getBankName();
      this.bankAccount = vendorVO.getBankAccount();
      this.bankAccountName = vendorVO.getBankAccountName();
      this.type = vendorVO.getType();
      this.attachment = vendorVO.getAttachment();
      this.description = vendorVO.getDescription();
      this.legalEntity = vendorVO.getLegalEntity();
      this.branch = vendorVO.getBranch();
      this.owner = vendorVO.getOwner();
      this.address = vendorVO.getAddress();
      this.postcode = vendorVO.getPostcode();
      this.mainContact = vendorVO.getMainContact();
      this.phone = vendorVO.getPhone();
      this.fax = vendorVO.getFax();
      this.email = vendorVO.getEmail();
      super.setStatus( vendorVO.getStatus() );
      super.setModifyDate( new Date() );
      this.attachmentArray = vendorVO.getAttachmentArray();
      super.setLocale( vendorVO.getLocale() );
   }

   // ��ȡ����������
   public String getDecodeOwner()
   {
      return this.ownerName;
   }

   // ��ȡ��ϵ������
   public String getDecodeMainContact()
   {
      return this.contactName;
   }

   public String getDecodeType()
   {
      return decodeField( type, types );
   }

   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public String getDecodeLegalEntity()
   {
      return decodeField( legalEntity, legalEntities );
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
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

   public String getCityId()
   {
      return KANUtil.filterEmpty( cityId );
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( contractStartDate ) );
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( contractEndDate ) );
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public String getCharterNumber()
   {
      return charterNumber;
   }

   public void setCharterNumber( String charterNumber )
   {
      this.charterNumber = charterNumber;
   }

   public String getBankName()
   {
      return bankName;
   }

   public void setBankName( String bankName )
   {
      this.bankName = bankName;
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
   }

   public String getBankAccountName()
   {
      return bankAccountName;
   }

   public void setBankAccountName( String bankAccountName )
   {
      this.bankAccountName = bankAccountName;
   }

   public String getType()
   {
      return type;
   }

   public void setType( String type )
   {
      this.type = type;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public List< MappingVO > getTypes()
   {
      return types;
   }

   public void setTypes( List< MappingVO > types )
   {
      this.types = types;
   }

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
   }

   public List< MappingVO > getServiceContents()
   {
      return serviceContents;
   }

   public void setServiceContents( List< MappingVO > serviceContents )
   {
      this.serviceContents = serviceContents;
   }

   public List< MappingVO > getLegalEntities()
   {
      return legalEntities;
   }

   public void setLegalEntities( List< MappingVO > legalEntities )
   {
      this.legalEntities = legalEntities;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( vendorId );
   }

   public String getOwnerName()
   {
      return ownerName;
   }

   public void setOwnerName( String ownerName )
   {
      this.ownerName = ownerName;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   public String getIsLink() throws KANException
   {
      if ( "1".equals( super.getStatus() ) || "4".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">�ύ</a>";
      }
      return isLink;
   }

   public String getOtherLink()
   {
      if ( KANUtil.filterEmpty( this.getWorkflowId() ) != null && ( "2".equals( super.getStatus() ) || "3".equals( super.getStatus() ) ) )
      {
         otherLink = "&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('" + this.getWorkflowId() + "'); />";
      }
      return otherLink;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress( String address )
   {
      this.address = address;
   }

   public String getPostcode()
   {
      return postcode;
   }

   public void setPostcode( String postcode )
   {
      this.postcode = postcode;
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

   public String getContactName()
   {
      return contactName;
   }

   public void setContactName( String contactName )
   {
      this.contactName = contactName;
   }

   public String getContactNameZH()
   {
      return contactNameZH;
   }

   public void setContactNameZH( String contactNameZH )
   {
      this.contactNameZH = contactNameZH;
   }

   public String getContactNameEN()
   {
      return contactNameEN;
   }

   public void setContactNameEN( String contactNameEN )
   {
      this.contactNameEN = contactNameEN;
   }

   public void setOtherLink( String otherLink )
   {
      this.otherLink = otherLink;
   }

}
