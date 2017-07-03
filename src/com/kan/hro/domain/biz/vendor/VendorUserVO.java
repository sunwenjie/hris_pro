package com.kan.hro.domain.biz.vendor;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kan.base.domain.security.UserVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class VendorUserVO extends UserVO
{
   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = 3954164885028083416L;

   /**
    * For DB
    */
   // ��Ӧ����ϵ���û�ID
   private String vendorUserId;

   // ��Ӧ��ID
   private String vendorId;

   // ��Ӧ����ϵ��ID
   private String vendorContactId;

   // �û���
   private String username;

   // ����
   private String password;

   // ��IP��ַ�������ֻ��ָ��IP��ַ����
   private String bindIP;

   // ����¼ʱ��
   private Date lastLogin;

   // ����¼IP��ַ
   private String lastLoginIP;

   // ��Ӧ�̽��治����
   private String superUserId;

   // ��Ӧ�̽��治���ã�Yes��No��
   private String validatedSuperUser;

   /**
    * For App
    */
   private String vendorUserToken;

   // ��Ӧ��������
   private String vendorNameZH;

   // ��Ӧ��Ӣ����
   private String vendorNameEN;
   //�Ƿ��Զ���¼
   private String cbPersistentCookie;

   @Override
   public void reset()
   {
      this.vendorContactId = "";
      this.username = "";
      this.password = "";
      this.bindIP = "";
      this.lastLoginIP = "";
      this.superUserId = "";
      this.validatedSuperUser = "";
      super.setStatus( "" );

   }

   @Override
   public void update( final Object object )
   {
      final VendorUserVO vendorUserVO = ( VendorUserVO ) object;
      this.vendorContactId = vendorUserVO.getVendorContactId();
      this.username = vendorUserVO.getUsername();
      this.password = vendorUserVO.getPassword();
      this.bindIP = vendorUserVO.getBindIP();
      this.lastLoginIP = vendorUserVO.getLastLoginIP();
      this.superUserId = vendorUserVO.getSuperUserId();
      this.validatedSuperUser = vendorUserVO.getValidatedSuperUser();
      super.setStatus( vendorUserVO.getStatus() );
      super.setModifyBy( vendorUserVO.getModifyBy() );
      super.setModifyDate( new Date() );

   }

   public String getVendorUserId()
   {
      return vendorUserId;
   }

   public void setVendorUserId( String vendorUserId )
   {
      this.vendorUserId = vendorUserId;
   }

   public String getVendorContactId()
   {
      return vendorContactId;
   }

   public void setVendorContactId( String vendorContactId )
   {
      this.vendorContactId = vendorContactId;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword( String password )
   {
      this.password = password;
   }

   public String getBindIP()
   {
      return bindIP;
   }

   public void setBindIP( String bindIP )
   {
      this.bindIP = bindIP;
   }

   public String getDecodeLastLogin()
   {
      if ( this.lastLogin != null )
      {
         return new SimpleDateFormat( KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_DATE_FORMAT + " "
               + KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_TIME_FORMAT ).format( this.lastLogin );
      }
      else
      {
         return "";
      }
   }

   public Date getLastLogin()
   {
      return lastLogin;
   }

   public void setLastLogin( Date lastLogin )
   {
      this.lastLogin = lastLogin;
   }

   public String getLastLoginIP()
   {
      return lastLoginIP;
   }

   public void setLastLoginIP( String lastLoginIP )
   {
      this.lastLoginIP = lastLoginIP;
   }

   public String getSuperUserId()
   {
      return superUserId;
   }

   public void setSuperUserId( String superUserId )
   {
      this.superUserId = superUserId;
   }

   public String getValidatedSuperUser()
   {
      return validatedSuperUser;
   }

   public void setValidatedSuperUser( String validatedSuperUser )
   {
      this.validatedSuperUser = validatedSuperUser;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( vendorUserId );
   }

   public String getEncodedVendorId() throws KANException
   {
      return encodedField( vendorId );
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getVendorUserToken()
   {
      return vendorUserToken;
   }

   public void setVendorUserToken( String vendorUserToken )
   {
      this.vendorUserToken = vendorUserToken;
   }

   public String getVendorNameZH()
   {
      return vendorNameZH;
   }

   public void setVendorNameZH( String vendorNameZH )
   {
      this.vendorNameZH = vendorNameZH;
   }

   public String getVendorNameEN()
   {
      return vendorNameEN;
   }

   public void setVendorNameEN( String vendorNameEN )
   {
      this.vendorNameEN = vendorNameEN;
   }

   public String getVendorName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getVendorNameZH();
         }
         else
         {
            return this.getVendorNameEN();
         }
      }
      else
      {
         return this.getVendorNameZH();
      }
   }

   public String getCbPersistentCookie()
   {
      return cbPersistentCookie;
   }

   public void setCbPersistentCookie( String cbPersistentCookie )
   {
      this.cbPersistentCookie = cbPersistentCookie;
   }
}
