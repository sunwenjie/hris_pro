/*
 * Created on 2003-4-12
 */
package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JsonConfig;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Kevin Jin
 */
public class UserVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4319794840718321614L;

   /**
    * For DB
    */
   private String userId;

   private String staffId;

   private String username;

   private String password;

   private String bindIP;

   private Date lastLogin;

   private String lastLoginIP;

   private String userIds;

   /**
    * For Application
    */
   // No use for logical
   private String staffName;

   // Used for login
   private String accountName;

   // 账户对应的公司名称
   private String entityName;

   // 客户名称
   private String clientName;

   // 密码确认
   private String passwordConfirm;

   // User token
   private String userToken;
   
   private String encryptPassword;
   
   private String salt;

   // 职位ID
   private String positionId;

   private String clientTitle;

   private String cbPersistentCookie;
   @JsonIgnore
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > connectCorpUserIds = new ArrayList< MappingVO >();

   public static JsonConfig USERVO_JSONCONFIG = new JsonConfig();
   static
   {
      USERVO_JSONCONFIG.setExcludes( new String[] { "historyVO", "password", "bindIP", "lastLogin", "lastLoginIP", "createDate", "modifyDate", "locale", "createBy", "deleted",
            "deleteds", "decodeCreateBy", "decodeCreateDate", "decodeDeleted", "decodeLastLogin", "decodeModifyBy", "decodeModifyDate", "decodeStatus", "emptyMappingVO", "flags",
            "modifyBy", "multipartRequestHandler", "passwordConfirm", "remark3", "remark4", "remark5", "searchField", "selectedIds", "servletWrapper", "sortColumn", "sortOrder",
            "statuses", "subAction", "" } );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final UserVO userVO = ( UserVO ) object;
      this.staffId = userVO.getStaffId();

      // 如果是Admin，则不能更改用户名和状态
      if ( KANUtil.filterEmpty( this.username ) == null || !this.username.trim().equalsIgnoreCase( "Admin" ) )
      {
         this.username = userVO.getUsername();
         super.setStatus( userVO.getStatus() );
      }

      this.bindIP = userVO.getBindIP();
      super.setModifyBy( userVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public void update( final UserVO userVO, final boolean updatePassword )
   {
      update( userVO );

      if ( updatePassword )
      {
         this.password = userVO.getPassword();
      }
   }

   @Override
   public void reset()
   {
      this.staffId = "";
      this.username = "";
      this.bindIP = "";
      super.setStatus( "0" );
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

   public String getUserId()
   {
      return userId;
   }

   public void setUserId( final String userId )
   {
      this.userId = userId;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername( final String username )
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword( final String password )
   {
      this.password = password;
   }

   public String getStaffName()
   {
      return staffName;
   }

   public void setStaffName( final String staffName )
   {
      this.staffName = staffName;
   }

   public String getPasswordConfirm()
   {
      return passwordConfirm;
   }

   public void setPasswordConfirm( final String passwordConfirm )
   {
      this.passwordConfirm = passwordConfirm;
   }

   public Date getLastLogin()
   {
      return lastLogin;
   }

   public void setLastLogin( Date lastLogin )
   {
      this.lastLogin = lastLogin;
   }

   public String getBindIP()
   {
      return bindIP == null ? "" : bindIP;
   }

   public void setBindIP( String bindIP )
   {
      this.bindIP = bindIP;
   }

   public String getLastLoginIP()
   {
      return lastLoginIP == null ? "" : lastLoginIP;
   }

   public void setLastLoginIP( String lastLoginIP )
   {
      this.lastLoginIP = lastLoginIP;
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public String getAccountName()
   {
      return accountName;
   }

   public void setAccountName( String accountName )
   {
      this.accountName = accountName;
   }

   public String getEntityName()
   {
      return entityName;
   }

   public void setEntityName( String entityName )
   {
      this.entityName = entityName;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( userId == null || userId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( userId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getEncodedStaffId() throws KANException
   {
      if ( staffId == null || staffId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( staffId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getUserToken()
   {
      return userToken;
   }

   public void setUserToken( String userToken )
   {
      this.userToken = userToken;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   public String getPositionName()
   {
      return decodeField( positionId, positions );
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public List< MappingVO > getConnectCorpUserIds()
   {
      return connectCorpUserIds;
   }

   public void setConnectCorpUserIds( List< MappingVO > connectCorpUserIds )
   {
      this.connectCorpUserIds = connectCorpUserIds;
   }

   public String getUserIds()
   {
      return userIds;
   }

   public void setUserIds( String userIds )
   {
      this.userIds = userIds;
   }

   public String getClientTitle()
   {
      return clientTitle;
   }

   public void setClientTitle( String clientTitle )
   {
      this.clientTitle = clientTitle;
   }

   public String getCbPersistentCookie()
   {
      return cbPersistentCookie;
   }

   public void setCbPersistentCookie( String cbPersistentCookie )
   {
      this.cbPersistentCookie = cbPersistentCookie;
   }

	public String getEncryptPassword() {
		return encryptPassword;
	}
	
	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
   
   

}