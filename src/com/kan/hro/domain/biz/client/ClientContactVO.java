package com.kan.hro.domain.biz.client;

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

public class ClientContactVO extends BaseVO
{

   /**  
   * SerialVersionUID
   */

   private static final long serialVersionUID = 7070458645427930621L;

   // 客户联系人ID
   private String clientContactId;

   // 称呼
   private String salutation;

   // 中文姓名
   private String nameZH;

   // 英文姓名
   private String nameEN;

   // 头衔
   private String title;

   // 所属部门
   private String department;

   // 公司电话
   private String bizPhone;

   // 私人电话
   private String personalPhone;

   // 公司手机号码
   private String bizMobile;

   // 个人手机号码
   private String personalMobile;

   // 其他号码
   private String otherPhone;

   // 传真号码
   private String fax;

   // 公司邮箱
   private String bizEmail;

   // 个人邮箱
   private String personalEmail;

   // 备注
   private String comment;

   // 是否可以发送推送消息到企业邮箱
   private String canAdvBizEmail;

   // 是否可以发送推送消息到个人邮箱
   private String canAdvPersonalEmail;

   // 是否可以发送推送消息到企业短消息
   private String canAdvBizSMS;

   // 是否可以发送推送消息到个人短消息
   private String canAdvPersonalSMS;

   // 所在城市
   private String cityId;

   // 联系地址
   private String address;

   // 邮编
   private String postcode;

   // 所属人所在部门
   private String branch;

   // 所属人
   private String owner;

   // IM1类型
   private String im1Type;

   // IM1号码
   private String im1;

   // IM2类型
   private String im2Type;

   // IM2号码
   private String im2;

   /**
    * For Application
    */
   // 客户编号
   private String clientNumber;

   // 客户中文名称
   private String clientNameZH;

   // 客户英文名称
   private String clientNameEN;

   // 省份ID
   private String provinceId;

   // 城市ID - Temp
   private String cityIdTemp;

   // 用户名
   private String username;

   private String clientUserId;
   
   private String accountName;

   // 省份
   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.client.contact.statuses" ) );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public void update( final Object object )
   {
      final ClientContactVO clientContactVO = ( ClientContactVO ) object;
      this.salutation = clientContactVO.getSalutation();
      this.nameZH = clientContactVO.getNameZH();
      this.nameEN = clientContactVO.getNameEN();
      this.title = clientContactVO.getTitle();
      this.department = clientContactVO.getDepartment();
      this.bizPhone = clientContactVO.getBizPhone();
      this.personalPhone = clientContactVO.getPersonalPhone();
      this.bizMobile = clientContactVO.getBizMobile();
      this.personalMobile = clientContactVO.getPersonalMobile();
      this.otherPhone = clientContactVO.getOtherPhone();
      this.im1 = clientContactVO.getIm1();
      this.im1Type = clientContactVO.getIm1Type();
      this.im2 = clientContactVO.getIm2();
      this.im2Type = clientContactVO.getIm2Type();
      this.owner = clientContactVO.getOwner();
      this.fax = clientContactVO.getFax();
      this.bizEmail = clientContactVO.getBizEmail();
      this.personalEmail = clientContactVO.getPersonalEmail();
      this.comment = clientContactVO.getComment();
      this.canAdvBizEmail = clientContactVO.getCanAdvBizEmail();
      this.canAdvPersonalEmail = clientContactVO.getCanAdvPersonalEmail();
      this.canAdvBizSMS = clientContactVO.getCanAdvBizSMS();
      this.canAdvPersonalSMS = clientContactVO.getCanAdvPersonalSMS();
      this.cityId = clientContactVO.getCityId();
      this.address = clientContactVO.getAddress();
      this.postcode = clientContactVO.getPostcode();
      this.branch = clientContactVO.getBranch();
      this.username = clientContactVO.getUsername();
      super.setStatus( clientContactVO.getStatus() );
      super.setClientId( clientContactVO.getClientId() );
      super.setCorpId( clientContactVO.getCorpId() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.salutation = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.title = "";
      this.department = "";
      this.bizPhone = "";
      this.personalPhone = "";
      this.bizMobile = "";
      this.personalMobile = "";
      this.otherPhone = "";
      this.im1 = "";
      this.im1Type = "0";
      this.im2 = "";
      this.im2Type = "0";
      this.owner = "";
      this.fax = "";
      this.bizEmail = "";
      this.personalEmail = "";
      this.comment = "";
      this.canAdvBizEmail = "";
      this.canAdvPersonalEmail = "";
      this.canAdvBizSMS = "";
      this.canAdvPersonalSMS = "";
      this.cityId = "";
      this.address = "";
      this.postcode = "";
      this.branch = "";
      this.username = "";
      super.setStatus( "0" );
      super.setClientId( "" );
      super.setCorpId( "" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( clientContactId );
   }

   // 获取称呼
   public String getDecodeSalutation()
   {
      return decodeField( salutation, KANUtil.getMappings( this.getLocale(), "salutation" ) );
   }

   // 获取员工状态
   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   // 获得城市
   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public String getClientContactId()
   {
      return clientContactId;
   }

   public void setClientContactId( String clientContactId )
   {
      this.clientContactId = clientContactId;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
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

   public final String getName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNameZH();
         }
         else
         {
            return this.getNameEN();
         }
      }
      else
      {
         return this.getNameZH();
      }
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle( String title )
   {
      this.title = title;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getBizPhone()
   {
      return bizPhone;
   }

   public void setBizPhone( String bizPhone )
   {
      this.bizPhone = bizPhone;
   }

   public String getPersonalPhone()
   {
      return personalPhone;
   }

   public void setPersonalPhone( String personalPhone )
   {
      this.personalPhone = personalPhone;
   }

   public String getBizMobile()
   {
      return bizMobile;
   }

   public void setBizMobile( String bizMobile )
   {
      this.bizMobile = bizMobile;
   }

   public String getPersonalMobile()
   {
      return personalMobile;
   }

   public void setPersonalMobile( String personalMobile )
   {
      this.personalMobile = personalMobile;
   }

   public String getOtherPhone()
   {
      return otherPhone;
   }

   public void setOtherPhone( String otherPhone )
   {
      this.otherPhone = otherPhone;
   }

   public String getFax()
   {
      return fax;
   }

   public void setFax( String fax )
   {
      this.fax = fax;
   }

   public String getBizEmail()
   {
      return bizEmail;
   }

   public void setBizEmail( String bizEmail )
   {
      this.bizEmail = bizEmail;
   }

   public String getPersonalEmail()
   {
      return personalEmail;
   }

   public void setPersonalEmail( String personalEmail )
   {
      this.personalEmail = personalEmail;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment( String comment )
   {
      this.comment = comment;
   }

   public String getCanAdvBizEmail()
   {
      return canAdvBizEmail;
   }

   public void setCanAdvBizEmail( String canAdvBizEmail )
   {
      this.canAdvBizEmail = canAdvBizEmail;
   }

   public String getCanAdvPersonalEmail()
   {
      return canAdvPersonalEmail;
   }

   public void setCanAdvPersonalEmail( String canAdvPersonalEmail )
   {
      this.canAdvPersonalEmail = canAdvPersonalEmail;
   }

   public String getCanAdvBizSMS()
   {
      return canAdvBizSMS;
   }

   public void setCanAdvBizSMS( String canAdvBizSMS )
   {
      this.canAdvBizSMS = canAdvBizSMS;
   }

   public String getCanAdvPersonalSMS()
   {
      return canAdvPersonalSMS;
   }

   public void setCanAdvPersonalSMS( String canAdvPersonalSMS )
   {
      this.canAdvPersonalSMS = canAdvPersonalSMS;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
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

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
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

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
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

   public final String getClientName()
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

   public String getClientUserId()
   {
      return clientUserId;
   }

   public void setClientUserId( String clientUserId )
   {
      this.clientUserId = clientUserId;
   }

   public String getAccountName()
   {
      return accountName;
   }

   public void setAccountName( String accountName )
   {
      this.accountName = accountName;
   }
   
   

}
