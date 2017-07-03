package com.kan.hro.domain.biz.client;

import java.util.Date;

import net.sf.json.JsonConfig;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.util.KANException;

public class ClientUserVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 3954164885028083416L;

   /**
    * For DB
    */

   // �ͻ��û�ID
   private String clientUserId;

   // �˻�ID
   private String accountId;
   
   private String accountName;

   // �ͻ�ID
   private String clientId;

   // �ͻ���ϵ��ID
   private String clientContactId;

   // �û���
   private String username;

   // ����
   private String password;

   // ��IP��ַ�������ֻ��ָ��IP��ַ����
   private String bindIP;

   // ����¼ʱ��
   private String lastLogin;

   // ����¼IP��ַ
   private String lastLoginIP;

   // �ͻ����治����
   private String superUserId;

   // �ͻ����治���ã�Yes��No��
   private String validatedSuperUser;
   
   //�Ƿ��Զ���¼
   private String cbPersistentCookie;

   /**
    * For Application
    */
   // �ͻ�����
   private String clientName;

   // User token
   private String userToken;
   
// �˻���Ӧ�Ĺ�˾����
   private String entityName;
   
   public static JsonConfig USERVO_JSONCONFIG = new JsonConfig();
   static
   {
      USERVO_JSONCONFIG.setExcludes( new String[] { "historyVO", "password", "bindIP", "lastLogin", "lastLoginIP", "createDate", "modifyDate", "locale", "createBy", "deleted",
            "deleteds", "decodeCreateBy", "decodeCreateDate", "decodeDeleted", "decodeLastLogin", "decodeModifyBy", "decodeModifyDate", "decodeStatus", "emptyMappingVO", "flags",
            "modifyBy", "multipartRequestHandler", "passwordConfirm", "remark3", "remark4", "remark5", "searchField", "selectedIds", "servletWrapper", "sortColumn", "sortOrder",
            "statuses", "subAction", "" } );
   }

   @Override
   public void reset() throws KANException
   {
      this.clientId = "";
      this.clientContactId = "";
      this.username = "";
      this.password = "";
      this.bindIP = "";
      this.lastLogin = "";
      this.lastLoginIP = "";
      this.superUserId = "";
      this.validatedSuperUser = "";
      super.setStatus( "" );
      super.setCorpId( "" );

   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ClientUserVO clientUserVO = ( ClientUserVO ) object;
      this.clientId = clientUserVO.getClientId();
      this.clientContactId = clientUserVO.getClientContactId();
      this.username = clientUserVO.getUsername();
      this.password = clientUserVO.getPassword();
      this.bindIP = clientUserVO.getBindIP();
      this.lastLogin = clientUserVO.getLastLogin();
      this.lastLoginIP = clientUserVO.getLastLoginIP();
      this.superUserId = clientUserVO.getSuperUserId();
      this.validatedSuperUser = clientUserVO.getValidatedSuperUser();
      super.setStatus( clientUserVO.getStatus() );
      super.setModifyBy( clientUserVO.getModifyBy() );
      super.setModifyDate( new Date() );
      super.setCorpId( clientUserVO.getCorpId() );

   }

   public String getClientUserId()
   {
      return clientUserId;
   }

   public void setClientUserId( String clientUserId )
   {
      this.clientUserId = clientUserId;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public String getClientContactId()
   {
      return clientContactId;
   }

   public void setClientContactId( String clientContactId )
   {
      this.clientContactId = clientContactId;
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

   public String getLastLogin()
   {
      return lastLogin;
   }

   public void setLastLogin( String lastLogin )
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
      return encodedField( clientUserId );
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public String getUserToken()
   {
      return userToken;
   }

   public void setUserToken( String userToken )
   {
      this.userToken = userToken;
   }

   public String getAccountName()
   {
      return accountName;
   }

   public void setAccountName( String accountName )
   {
      this.accountName = accountName;
   }

   public String getCbPersistentCookie()
   {
      return cbPersistentCookie;
   }

   public void setCbPersistentCookie( String cbPersistentCookie )
   {
      this.cbPersistentCookie = cbPersistentCookie;
   }

   public String getEntityName()
   {
      return entityName;
   }

   public void setEntityName( String entityName )
   {
      this.entityName = entityName;
   }

   public static UserVO changeToUserVO( ClientUserVO clientUserVO )
   {
      UserVO userVO = new UserVO();
      userVO.setAccountId( clientUserVO.getAccountId() );
      userVO.setClientName( clientUserVO.getClientName() );
      userVO.setClientId( clientUserVO.getClientId() );
      userVO.setCorpId( clientUserVO.getCorpId() );
      userVO.setUserToken( clientUserVO.getUserToken() );
      return userVO;
   }

   public static ClientUserVO changeClinetUserVO( UserVO userVO )
   {
      ClientUserVO clientUserVO = new ClientUserVO();
      clientUserVO.setAccountId( userVO.getAccountId() );
      clientUserVO.setClientName( userVO.getClientName() );
      clientUserVO.setClientId( userVO.getClientId() );
      clientUserVO.setCorpId( userVO.getCorpId() );
      clientUserVO.setUserToken( userVO.getUserToken() );
      return clientUserVO;
   }

}
