package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.net.URLEncoder;

import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ClientBaseView implements Serializable
{

   /**  
   * serialVersionUID:��������ӿͻ�AJAX�ã�  
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -1869534535236335333L;
   // �ͻ�ID
   private String id;

   // �ͻ�����
   private String name;

   // �ͻ����ƣ����ģ�
   private String clientNameZH;

   // �ͻ����ƣ�Ӣ�ģ�
   private String clientNameEN;

   // ����ID
   private String groupId;

   // �˻�ID
   private String accountId;

   // ����ʵ��
   private String legalEntity;

   private String conditions;
   
   private String logoFile;
   
   private String logoFileSize;
   
   // �ֻ�ģ��Ȩ��
   private String mobileModuleRightIds;

   public String getEncodedId() throws KANException
   {
      if ( id == null || id.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( id ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public String getLegalEntity()
   {
      return legalEntity;
   }

   public void setLegalEntity( String legalEntity )
   {
      this.legalEntity = legalEntity;
   }

   public String getConditions()
   {
      return conditions;
   }

   public void setConditions( String conditions )
   {
      this.conditions = conditions;
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

   public String getMobileModuleRightIds()
   {
      return mobileModuleRightIds;
   }

   public void setMobileModuleRightIds( String mobileModuleRightIds )
   {
      this.mobileModuleRightIds = mobileModuleRightIds;
   }

}
