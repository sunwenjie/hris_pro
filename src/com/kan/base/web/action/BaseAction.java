/*
 * Created on 2005-6-16
 */
package com.kan.base.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.security.WXContactVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.tag.AuthConstants;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANConstantsService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.RandomUtil;
import com.kan.base.util.WXUtil;
import com.kan.base.util.json.JsonMapper;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author Kevin Jin
 */
public abstract class BaseAction extends DispatchAction
{
   public final Log logger = LogFactory.getLog( getClass() );

   protected final int listPageSize = 10;

   protected final int listPageSize_popup = 5;

   protected final int listPageSize_medium = 15;

   protected final int listPageSize_large = 20;

   protected final int loadPages = 0;

   // ���������
   public final static String ACCOUNT_ID_ICLICK = "100017";

   public final static String SEARCH_OBJECT = "searchObject";

   public final static String DELETE_OBJECT = "deleteObject";

   public final static String DELETE_OBJECTS = "deleteObjects";

   public final static String CREATE_OBJECT = "createObject";

   public final static String MODIFY_OBJECT = "modifyObject";

   public final static String VIEW_OBJECT = "viewObject";

   public final static String ACTIVE_OBJECTS = "activeObjects";

   public final static String DOWNLOAD_OBJECT = "downloadObject";

   public final static String DOWNLOAD_OBJECTS = "downloadObjects";

   public final static String APPROVE_OBJECT = "approveObject";

   public final static String APPROVE_OBJECTS = "approveObjects";

   public final static String CONFIRM_OBJECT = "confirmObject";

   public final static String CONFIRM_OBJECTS = "confirmObjects";

   public final static String SUBMIT_OBJECT = "submitObject";

   public final static String SUBMIT_OBJECTS = "submitObjects";

   public final static String RENEW_OBJECT = "renewObject";
   public final static String RENEW_OBJECTS = "renewObjects";
   public final static String ROLLBACK_OBJECT = "rollbackObject";

   public final static String ROLLBACK_OBJECTS = "rollbackObjects";

   public final static String ISSUE_OBJECT = "issueObject";

   public final static String ISSUE_OBJECTS = "issueObjects";

   protected final static String COOKIE_USER = "KANUser";
   protected final static String COOKIE_EMPLOYEE_USER = "KANEmployeeUser";

   protected final static String COOKIE_SECURITY = "KANSecurity";
   // hro �Զ���¼
   protected final static String AUTO_LOGON_USER = "auto_logon_user";
   protected final static String AUTO_LOGON_EMPLOYEE_USER = "auto_logon_employee_user";

   // hrm �Զ���¼
   protected final static String AUTO_LOGONI_USER = "auto_logoni_user";

   // Vendor �Զ���¼
   protected final static String AUTO_VENDOR_USER = "auto_vendor_user";
   protected final static String AUTO_CLIENT_USER = "auto_client_user";

   public final static String COOKIE_USER_JSON = "__COOKIE_USER_JSON";

   public final static String COOKIE_EMPLOYEE_USER_JSON = "__COOKIE_EMPLOYEE_USER_JSON";
   public final static String MESSAGE = "MESSAGE";

   public final static String MESSAGE_CLASS = "MESSAGE_CLASS";

   protected final static String MESSAGE_HEADER = "MESSAGE_HEADER";

   protected final static String MESSAGE_DETAIL = "MESSAGE_DETAIL";

   protected final static String MESSAGE_TYPE_ADD = "ADD";

   protected final static String MESSAGE_TYPE_UPDATE = "UPDATE";

   protected final static String MESSAGE_TYPE_DELETE = "DELETE";

   protected final static String MESSAGE_TYPE_SUBMIT = "SUBMIT";

   protected final static String MESSAGE_TYPE_ROLLBACK = "RETURN";
   
   protected final static String MESSAGE_TYPE_SAVE = "SAVE";

   /**
    * ������Ҫɾ�� - Start
    */
   @Deprecated
   protected final static String SUCCESS_MESSAGE = "SUCCESS_MESSAGE";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_HEADER = "SUCCESS_MESSAGE_HEADER";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_DETAIL = "SUCCESS_MESSAGE_DETAIL";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_TYPE = "SUCCESS_MESSAGE_TYPE";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_TYPE_ADD = "ADD";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_TYPE_UPDATE = "UPDATE";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_TYPE_DELETE = "DELETE";

   @Deprecated
   protected final static String SUCCESS_MESSAGE_TYPE_SUBMIT = "SUBMIT";

   @Deprecated
   public final static String USER_DEFINE_MESSAGE = "USER_DEFINE_MESSAGE";

   /**
    * ������Ҫɾ�� - End
    */

   public final static String SESSION_NAME_UPLOAD_STATUS = "UPLOAD_STATUS";

   public final static String TOKEN_NAME = "com.kan.token";

   // Abstract Method
   abstract public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   abstract public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   abstract public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   abstract public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   abstract public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   abstract protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   abstract protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException;

   // Get service
   public static Object getService( final String serviceName ) throws KANException
   {
      try
      {
         return ServiceLocator.getService( serviceName );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // Get object from session (Cache Implementation)
   public static Object getObjectFromSession( final HttpServletRequest request, final String sessionName ) throws KANException
   {
      return CachedUtil.get( request, getUserToken( request, null ) + "_" + sessionName );
   }

   // Set object to session (Cache Implementation)
   public static void setObjectToSession( final HttpServletRequest request, final String sessionName, final Object object ) throws KANException
   {
      CachedUtil.set( request, getUserToken( request, null ) + "_" + sessionName, object );
   }

   // Set object to session (Cache Implementation)
   public static void setObjectToSession( final HttpServletRequest request, final String sessionName, final Object object, final int expiry ) throws KANException
   {
      CachedUtil.set( request, getUserToken( request, null ) + "_" + sessionName, object, expiry );
   }

   // Remove object from session (Cache Implementation)
   public static void removeObjectFromSession( final HttpServletRequest request, final String sessionName ) throws KANException
   {
      CachedUtil.delete( request, getUserToken( request, null ) + "_" + sessionName );
   }

   // Refresh the page holder for message resource
   public void refreshHolder( final PagedListHolder userHolder, final HttpServletRequest request )
   {
      if ( userHolder != null && userHolder.getSource() != null && userHolder.getSource().size() > 0 )
      {
         for ( Object pageObject : userHolder.getSource() )
         {
            ( ( ActionForm ) pageObject ).reset( null, request );
         }
      }
   }

   // Refresh the page holder for message resource
   public void refreshSource( final List< Object > sources, final HttpServletRequest request )
   {
      if ( sources != null && sources.size() > 0 )
      {
         for ( Object pageObject : sources )
         {
            ( ( ActionForm ) pageObject ).reset( null, request );
         }
      }
   }

   public void saveUserToClient( final HttpServletResponse response, final HttpServletRequest request, final UserVO userVO ) throws KANException
   {
      try
      {
         // Json�ֽ��������ͻ���
         String cookieUser = JSONObject.fromObject( userVO, UserVO.USERVO_JSONCONFIG ).toString();
         JSONObject cookieUser_JSON = JSONObject.fromObject( cookieUser );

         request.setAttribute( COOKIE_USER_JSON, cookieUser_JSON );
         final Cookie cookie = new Cookie( COOKIE_USER, URLEncoder.encode( cookieUser, "gbk" ) );
         // cookie.setMaxAge( 60 * 15 );//15����
         response.addCookie( cookie );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public static UserVO getUserVOFromClient( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      JSONObject userJson = getUserFromClient( request, response );
      // ָ��Ҫת��Ϊ�Ķ���ĳ�Ա���Ե���������
      Map< String, Class< ? >> map = new HashMap< String, Class< ? >>();
      map.put( "positions", MappingVO.class );
      map.put( "connectCorpUserIds", MappingVO.class );
      return ( UserVO ) JSONObject.toBean( userJson, UserVO.class, map );
   }

   public static JSONObject getUserFromClient( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         JSONObject cookieUser = ( JSONObject ) request.getAttribute( COOKIE_USER_JSON );
         if ( cookieUser != null && KANUtil.filterEmpty( cookieUser.getString( "accountId" ) ) != null )
         {
            return cookieUser;
         }
         else
         {
            // �ӿͻ��˻�ȡJson�ֽ�����ת����User����
            final Cookie cookies[] = request.getCookies();

            if ( cookies != null )
            {
               for ( Cookie cookie : cookies )
               {
                  if ( cookie.getName().equalsIgnoreCase( COOKIE_USER ) )
                  {
                     if ( cookie.getValue() != null && !cookie.getValue().trim().equalsIgnoreCase( "" ) )
                     {
                        JSONObject cookieUser_JSON = JSONObject.fromObject( URLDecoder.decode( cookie.getValue(), "gbk" ) );
                        if ( KANUtil.filterEmpty( cookieUser_JSON.getString( "accountId" ) ) != null )
                        {
                           request.setAttribute( COOKIE_USER_JSON, cookieUser_JSON );
                           return cookieUser_JSON;
                        }
                        else
                        {
                           return null;
                        }
                     }
                     else
                     {
                        return null;
                     }
                  }
               }
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // Get User Token from Cookie object
   public static String getUserToken( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "userToken" ) : ( String ) request.getAttribute( "userToken" );
   }

   // Get userId from Cookie object
   public static String getUserId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "userId" ) : ( String ) request.getAttribute( "userId" );
   }

   // Get staffId from Cookie object
   public static String getStaffId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "staffId" ) : ( String ) request.getAttribute( "staffId" );
   }

   // Get Username from Cookie object
   public static String getUsername( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "username" ) : "";
   }

   // Get AccountId from Cookie object
   public static String getAccountId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "accountId" ) : KANConstants.DEFAULT_ACCOUNTID;
   }

   // Get SystemId from Cookie object
   public static String getSystemId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "systemId" ) : ( String ) request.getAttribute( "systemId" );
   }

   // Get position from Cookie object
   public static String getPositionId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "positionId" ) : ( String ) request.getAttribute( "positionId" );
   }

   // Get position from Cookie object
   public static String getClientId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "clientId" ) : ( String ) request.getAttribute( "clientId" );
   }

   // Get position from Cookie object
   public static String getVendorId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "vendorId" ) : ( String ) request.getAttribute( "vendorId" );
   }

   // Get corpId from Cookie object
   public static String getCorpId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "corpId" ) : KANConstants.DEFAULT_CORPID;
   }

   // Get Title from Cookie object
   public static String getTitle( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      return getTitle( getRole( request, response ) );
   }

   // Get Title by Role
   public static String getTitle( final String role ) throws KANException
   {
      if ( KANUtil.filterEmpty( role ) != null && KANUtil.filterEmpty( role ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return KANConstants.PRODUCT_NAME_HRS;
      }
      else if ( KANUtil.filterEmpty( role ) != null && KANUtil.filterEmpty( role ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return KANConstants.PRODUCT_NAME_INH;
      }
      else if ( KANUtil.filterEmpty( role ) != null && KANUtil.filterEmpty( role ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         return KANConstants.PRODUCT_NAME_HRS;
      }
      else if ( KANUtil.filterEmpty( role ) != null && KANUtil.filterEmpty( role ).equals( KANConstants.ROLE_CLIENT ) )
      {
         return KANConstants.PRODUCT_NAME_HRS;
      }
      else
      {
         return "";
      }
   }

   @Deprecated
   public static boolean isInHouseRole( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      return getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE );
   }

   public static String getRole( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = ( JSONObject ) request.getAttribute( COOKIE_USER_JSON );
      if ( jsonObject != null )
      {
         return jsonObject != null ? ( String ) jsonObject.get( "role" ) : ( String ) request.getAttribute( "role" );
      }

      // �ӿͻ��˻�ȡJson�ֽ�����ת����User����
      final Cookie cookies[] = request.getCookies();
      if ( cookies != null )
      {
         try
         {
            for ( Cookie cookie : cookies )
            {
               if ( cookie.getName().equalsIgnoreCase( COOKIE_USER ) )
               {
                  if ( cookie.getValue() != null && !cookie.getValue().trim().equalsIgnoreCase( "" ) )
                  {
                     JSONObject cookieUser_JSON;
                     cookieUser_JSON = JSONObject.fromObject( URLDecoder.decode( cookie.getValue(), "gbk" ) );
                     if ( cookieUser_JSON != null )
                     {
                        return cookieUser_JSON.getString( "role" );
                     }
                  }

               }
            }
         }
         catch ( UnsupportedEncodingException e )
         {
         }
      }

      return ( String ) request.getAttribute( "role" );
   }

   // �趨Ȩ��
   @Deprecated
   public void setHRFunctionRole( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // �����inHouse
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         request.setAttribute( "isHRFunction", !isHRFunction( request, response ) ? "2" : "1" );
      }
      // ��inHouseĬ��HRBranch
      else
      {
         request.setAttribute( "isHRFunction", "1" );
      }

   }

   // ��ʾ������ť
   public void showExportButton( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ��corpId
      final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

      // ��ȡListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( ( String ) request.getAttribute( "javaObjectName" ), corpId );

      // �ж��б��Ƿ���Ҫ��ӵ�������
      if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
            && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
      {
         request.setAttribute( "isExportExcel", "1" );
      }
   }

   // ��ȡְλ���Ƿ����HRְ��
   /* Add by siuxia 2014-03-06 */
   public static boolean isHRFunction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< GroupDTO > groupDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getGroupDTOsByPositionId( getPositionId( request, response ) );

      if ( groupDTOs != null && groupDTOs.size() > 0 )
      {
         for ( GroupDTO groupDTO : groupDTOs )
         {
            if ( groupDTO.getGroupVO() != null && KANUtil.filterEmpty( groupDTO.getGroupVO().getHrFunction() ) != null && groupDTO.getGroupVO().getHrFunction().trim().equals( "1" ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // ��ȡEmployeeId(InHouse �����)
   /* Add by siuxia 2014-03-05 */
   public static String getEmployeeId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final StaffDTO staffDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOByStaffId( getStaffId( request, response ) );

      if ( staffDTO != null && staffDTO.getStaffVO() != null && KANUtil.filterEmpty( staffDTO.getStaffVO().getEmployeeId() ) != null )
      {
         return staffDTO.getStaffVO().getEmployeeId();
      }

      return null;
   }

   // ��ȡBranchId
   /* Add by siuxia 2014-03-05 */
   public static String getBranchId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );
      if ( positionVO != null )
      {
         final BranchVO branchVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBranchVOByBranchId( positionVO.getBranchId() );

         return branchVO != null ? KANUtil.filterEmpty( branchVO.getBranchId() ) : null;
      }

      return null;
   }

   public static List< MappingVO > getPositions( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      List< MappingVO > list = new ArrayList< MappingVO >();
      if ( jsonObject != null )
      {
         final JSONArray objectArray = ( JSONArray ) jsonObject.get( "positions" );
         List< ? > list2 = JSONArray.toList( objectArray, new MappingVO(), new JsonConfig() );
         for ( Object o : list2 )
         {
            list.add( ( MappingVO ) o );
         }
      }
      return list;
   }

   public static List< MappingVO > getConnectCorpUserIds( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      List< MappingVO > list = new ArrayList< MappingVO >();
      if ( jsonObject != null )
      {
         final JSONArray objectArray = ( JSONArray ) jsonObject.get( "connectCorpUserIds" );
         List< ? > list2 = JSONArray.toList( objectArray, new MappingVO(), new JsonConfig() );
         for ( Object o : list2 )
         {
            list.add( ( MappingVO ) o );
         }
      }
      return list;
   }

   // Get request IP address
   public static String getIPAddress( final HttpServletRequest request )
   {
      if ( request.getHeader( "x-forwarded-for" ) == null )
      {
         return request.getRemoteAddr();
      }
      else
      {
         final String[] forwards = request.getHeader( "x-forwarded-for" ).split( "," );

         for ( String forward : forwards )
         {
            if ( !forward.trim().equalsIgnoreCase( "null" ) && !forward.trim().equalsIgnoreCase( "unknown" ) )
            {
               return forward;
            }
         }
      }

      return null;
   }

   /**
    * �Ƿ�Ϊ���� 1���� 2����
    * 
    * @param ipAddress
    * @return
    */
   public static Integer getIpType( String ipAddress )
   {

//      Integer ipType = 2;
//      if ( StringUtils.isBlank( ipAddress ) )
//      {
//         return ipType;
//      }
//      Pattern p = Pattern.compile( "^((192.168.)|(10.)|(172.16)|(127.0))+[0-9.]+$" );
//      if ( p.matcher( ipAddress ).matches() )
//      {
//         ipType = 1;
//      }
      return 1;
   }

   // Remove the object from client
   public static void removeObjectFromClient( final HttpServletRequest request, final HttpServletResponse response, final String objectName )
   {
      final Cookie cookies[] = request.getCookies();

      if ( cookies != null )
      {
         for ( Cookie cookie : cookies )
         {
            if ( cookie.getName().equalsIgnoreCase( objectName ) )
            {
               if ( COOKIE_USER.equalsIgnoreCase( objectName ) )
               {
                  try
                  {
                     JSONObject userVO_json = getUserFromClient( request, response );
                     if ( userVO_json != null )
                     {
                        Set< String > keySet = ( Set< String > ) userVO_json.keySet();
                        for ( String key : keySet )
                        {
                           if ( !"role".equalsIgnoreCase( key ) )
                           {
                              userVO_json.put( key, "" );
                           }
                        }
                        final Cookie emptyCookie = new Cookie( COOKIE_USER, URLEncoder.encode( userVO_json.toString(), "gbk" ) );
                        response.addCookie( emptyCookie );
                     }
                  }
                  catch ( Exception e )
                  {
                     e.printStackTrace();
                  }
               }
               else
               {
                  // Set empty object to the client
                  final Cookie emptyCookie = new Cookie( objectName, "" );
                  response.addCookie( emptyCookie );
               }
            }
         }
      }
   }

   /**
    * �Զ���¼ɾ��cookie
    * 
    * @author : Ian.huang
    * @date : 2014-6-30
    * @param : @param response
    * @param : @param request
    * @param : @param type
    * @param : @throws KANException
    * @return : void
    */
   public static void removeAutoLogonToCookie( final HttpServletResponse response, final HttpServletRequest request, final String type ) throws KANException
   {
      try
      {
         Cookie[] cookies = request.getCookies();
         if ( cookies != null )
         {
            for ( Cookie cook : cookies )
            {
               String cookieName = cook.getName();
               if ( !cookieName.equalsIgnoreCase( type ) )
               {
                  continue;
               }
               final Cookie cookie = new Cookie( type, "" );
               cookie.setMaxAge( 0 );
               response.addCookie( cookie );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void saveSecurityToClient()
   {

   }

   public void getSecurityFromClient()
   {

   }

   @Deprecated
   protected void addUserDefineMessage( final HttpServletRequest request, final String message )
   {
      String s = ( String ) request.getAttribute( USER_DEFINE_MESSAGE );
      if ( s != null )
      {
         s += message;
      }
      else
      {
         s = message;
      }
      request.setAttribute( USER_DEFINE_MESSAGE, s );
   }

   private String decodeMessageType( final String type, final String language )
   {
      if ( KANUtil.filterEmpty( type ) != null )
      {
         if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_ADD ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "���" : "Add ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_UPDATE ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "�༭" : "Edit ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_DELETE ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "ɾ��" : "Delete ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_SUBMIT ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "�ύ" : "Submit ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_ROLLBACK ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "�˻�" : "Return ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_SAVE ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "����" : "Save ";
         }
      }

      return "";
   }

   // ����ʧ�ܱ�Ǹ�ҳ��
   protected void error( final HttpServletRequest request )
   {
      error( request, null, null, null );
   }

   // ����ʧ�ܱ�Ǹ�ҳ�� - ����
   protected void error( final HttpServletRequest request, final String type )
   {
      error( request, type, null, null );
   }

   // ����ʧ�ܱ�Ǹ�ҳ�� - ����
   protected void error( final HttpServletRequest request, final String type, final String message )
   {
      error( request, type, message, null );
   }

   // ����ʧ�ܱ�Ǹ�ҳ�� - ����
   protected void error( final HttpServletRequest request, final String type, final String message, final String flag )
   {
      final String language = request.getLocale().getLanguage();
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( MESSAGE, message == null ? ( decodeMessageType( type, language ) + ( "zh".equalsIgnoreCase( language ) ? "����" : "error!" ) ) : message );
      request.setAttribute( MESSAGE_CLASS, "error" );
   }

   // ���;����Ǹ�ҳ��
   protected void warning( final HttpServletRequest request )
   {
      warning( request, null, null, null );
   }

   // ���;����Ǹ�ҳ�� - ����
   protected void warning( final HttpServletRequest request, final String type )
   {
      warning( request, type, null, null );
   }

   // ���;����Ǹ�ҳ�� - ����
   protected void warning( final HttpServletRequest request, final String type, final String message )
   {
      warning( request, type, message, null );
   }

   // ���;����Ǹ�ҳ�� - ����
   protected void warning( final HttpServletRequest request, final String type, final String message, final String flag )
   {
      final String language = request.getLocale().getLanguage();
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( MESSAGE, message == null ? decodeMessageType( type, language ) + ( ( "zh".equalsIgnoreCase( language ) ? "���棡" : "warning!" ) ) : message );
      request.setAttribute( MESSAGE_CLASS, "warning" );
   }

   // ���ͳɹ���Ǹ�ҳ��
   protected void success( final HttpServletRequest request )
   {
      success( request, null, null, null );
   }

   // ���ͳɹ���Ǹ�ҳ�� - ����
   protected void success( final HttpServletRequest request, final String type )
   {
      success( request, type, null, null );
   }

   // ���ͳɹ���Ǹ�ҳ�� - ����
   protected void success( final HttpServletRequest request, final String type, final String message )
   {
      success( request, type, message, null );
   }

   // ���ͳɹ���Ǹ�ҳ�� - ����
   protected void success( final HttpServletRequest request, final String type, final String message, final String flag )
   {
      final String language = request.getLocale().getLanguage();
      if ( KANUtil.filterEmpty( flag ) != null )
      {
         request.setAttribute( flag, flag );
      }

      request.setAttribute( MESSAGE, message == null ? ( decodeMessageType( type, language ) + ( "zh".equalsIgnoreCase( language ) ? "�ɹ���" : "success!" ) ) : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
   }

   // ���ͱ���ɹ���Ǹ�ҳ��
   @Deprecated
   protected void addSuccess( final HttpServletRequest request )
   {
      addSuccess( request, "����ɹ���", null );
   }

   // ���ͱ���ɹ���Ǹ�ҳ�� - ����
   @Deprecated
   protected void addSuccess( final HttpServletRequest request, final String message )
   {
      addSuccess( request, message, null );
   }

   // ���ͱ���ɹ���Ǹ�ҳ�� - ����
   @Deprecated
   protected void addSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "����ɹ���" : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_ADD );
   }

   // ���ͱ༭�ɹ���Ǹ�ҳ��
   @Deprecated
   protected void updateSuccess( final HttpServletRequest request )
   {
      updateSuccess( request, "�༭�ɹ���", null );
   }

   // ���ͱ༭�ɹ���Ǹ�ҳ�� - ����
   @Deprecated
   protected void updateSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "�༭�ɹ���" : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_UPDATE );
   }

   // ����ɾ���ɹ���Ǹ�ҳ��
   @Deprecated
   protected void submitSuccess( final HttpServletRequest request )
   {
      deleteSuccess( request, "�ύ�ɹ��������...", null );
   }

   // ����ɾ���ɹ���Ǹ�ҳ�� - ����
   @Deprecated
   protected void submitSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "�ύ�ɹ��������..." : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_SUBMIT );
   }

   // ����ɾ���ɹ���Ǹ�ҳ��
   @Deprecated
   protected void deleteSuccess( final HttpServletRequest request )
   {
      deleteSuccess( request, "ɾ���ɹ���", null );
   }

   // ����ɾ���ɹ���Ǹ�ҳ�� - ����
   @Deprecated
   protected void deleteSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "ɾ���ɹ���" : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_DELETE );
   }

   // ����ɾ���ɹ���Ǹ�Ajax����
   protected void deleteSuccessAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "success" );

         out.println( jsonObject );
      }
   }

   // ����ɾ���ɹ���Ǹ�Ajax����
   protected void deleteFailedAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "failed" );

         out.println( jsonObject );
      }
   }

   // ������ӳɹ���Ǹ�Ajax����
   protected void addSuccessAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "success" );

         out.println( jsonObject );
      }
   }

   // ������ӳɹ���Ǹ�Ajax����
   protected void addFailedAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "failed" );

         out.println( jsonObject );
      }
   }

   // ��ȡ��¼�����ĵ�ʱ���
   public static String getHisTitle( final BaseVO baseVO, final HttpServletRequest request ) throws KANException
   {
      return baseVO.getDecodeModifyDate() + " " + KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffNameByUserId( baseVO.getModifyBy() ) + " Update";
   }

   /**
    * �޸�remark1���Զ����ֶ�
    * 
    * @param columnName
    * @param value
    * @param obj
    */
   public static void setDefineColumn( final String columnName, String value, Object obj )
   {
      final JSONObject jsonObject = JSONObject.fromObject( ( ( BaseVO ) obj ).getRemark1() );
      if ( jsonObject.containsKey( columnName ) )
      {
         jsonObject.put( columnName, value );
         ( ( BaseVO ) obj ).setRemark1( jsonObject.toString() );
      }
   }

   // ��ȡ�Զ����ֶ�ֵ��ת����JSON String
   public static String saveDefineColumns( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // ��ʼ��Map��������װ���Զ���Column Value
      final Map< String, String > values = new HashMap< String, String >();

      // ����Column Group
      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {
            // ����Column
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  // ֻ��ȡ�û��Զ����Column Value
                  if ( columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                  {
                     values.put( columnVO.getNameDB(), request.getParameter( accessAction + "_" + columnVO.getNameDB() ) );
                  }
               }
            }
         }
      }

      // ����ǰMapת��JSON String
      final JSONObject jsonObject = new JSONObject();
      jsonObject.putAll( values );

      return jsonObject.toString();
   }

   // ���������Զ�����ֶ�ת�����ݿ���������
   public static String generateDefineListSearches( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      try
      {
         // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
         // ��ʼ�����������ַ���
         String searches = "";

         final ListDTO listDTO = tableDTO.getListDTO( getAccountId( request, null ), getCorpId( request, null ) );

         // ����Column Group
         if ( tableDTO != null && listDTO != null && listDTO.getSearchDTO() != null && listDTO.getSearchDTO().getSearchDetailVOs() != null
               && listDTO.getSearchDTO().getSearchDetailVOs().size() > 0 )
         {
            for ( SearchDetailVO searchDetailVO : listDTO.getSearchDTO().getSearchDetailVOs() )
            {
               // ��ʼ���ֶζ���
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

               // ֻ��ȡ�û��Զ����Column Value
               if ( columnVO != null && columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
               {
                  // ��ʼ�������ֶε�����
                  String searchContent = request.getParameter( accessAction + "_" + columnVO.getNameDB() );
                  // �����������ֵ
                  if ( searchContent != null && !searchContent.trim().equals( "" ) && !searchContent.trim().equals( "0" ) )
                  {
                     // �ύ���ݽ��н���
                     searchContent = URLDecoder.decode( searchContent, "UTF-8" );

                     if ( searches != null && !searches.trim().equals( "" ) )
                     {
                        searches = searches + ",";
                     }

                     // �ı�����ı����������ʽ
                     if ( columnVO.getInputType() != null && ( columnVO.getInputType().equals( "1" ) || columnVO.getInputType().equals( "5" ) ) )
                     {
                        searches = searches + "%\"" + columnVO.getNameDB() + "\":\"%" + searchContent + "%\"%";
                     }
                     // ������͵�ѡ���������ʽ��ͨ����ѡ��֧������
                     else
                     {
                        searches = searches + "%\"" + columnVO.getNameDB() + "\":\"" + searchContent + "\"%";
                     }
                  }
               }
            }
         }

         return URLEncoder.encode( URLEncoder.encode( searches, "UTF-8" ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���������Զ�����ֶ�ת�����ݿ���������
   public static Set< String > generateDefineRemarkSet( final HttpServletRequest request, final String accessAction, final String tableId ) throws KANException
   {
      try
      {
         Set< String > remarkSet = null;
         // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
         final TableDTO subTableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByTableId( tableId );

         final ListDTO listDTO = tableDTO.getListDTO( getAccountId( request, null ), getCorpId( request, null ) );

         // ����Column Group
         if ( tableDTO != null && subTableDTO != null && listDTO != null && listDTO.getSearchDTO() != null && listDTO.getSearchDTO().getSearchDetailVOs() != null
               && listDTO.getSearchDTO().getSearchDetailVOs().size() > 0 )
         {
            for ( SearchDetailVO searchDetailVO : listDTO.getSearchDTO().getSearchDetailVOs() )
            {
               // ��ʼ���ֶζ���
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );
               final ColumnVO subColumnVO = subTableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

               // ֻ��ȡ�û��Զ����Column Value
               if ( columnVO != null && columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && subColumnVO != null
                     && subColumnVO.getAccountId() != null && !subColumnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
               {
                  // ��ʼ�������ֶε�����
                  String searchContent = request.getParameter( accessAction + "_" + columnVO.getNameDB() );
                  // �����������ֵ
                  if ( searchContent != null && !searchContent.trim().equals( "" ) && !searchContent.trim().equals( "0" ) )
                  {
                     if ( remarkSet == null )
                     {
                        remarkSet = new HashSet< String >();
                     }

                     // �ύ���ݽ��н���
                     searchContent = URLDecoder.decode( searchContent, "UTF-8" );

                     // �ı�����ı����������ʽ
                     if ( columnVO.getInputType() != null && ( columnVO.getInputType().equals( "1" ) || columnVO.getInputType().equals( "5" ) ) )
                     {
                        remarkSet.add( "%\"" + columnVO.getNameDB() + "\":\"%" + searchContent + "%\"%" );
                     }
                     // ������͵�ѡ���������ʽ��ͨ����ѡ��֧������
                     else
                     {
                        remarkSet.add( "%\"" + columnVO.getNameDB() + "\":\"" + searchContent + "\"%" );
                     }
                  }
               }
            }
         }

         if ( remarkSet != null )
            URLEncoder.encode( URLEncoder.encode( remarkSet.toString(), "UTF-8" ), "UTF-8" );

         return remarkSet;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ʼ��Token
   public void saveToken( final HttpServletRequest request )
   {
      try
      {
         CachedUtil.set( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME, RandomUtil.getRandomString( 32 ) );
      }
      catch ( final KANException e )
      {
      }
   }

   // ���Token
   public static String addToken( final HttpServletRequest request ) throws KANException
   {
      return "<input type=\"hidden\" id=\"" + BaseAction.TOKEN_NAME + "\" name=\"" + BaseAction.TOKEN_NAME + "\" value=\""
            + ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) + "\" />";
   }

   // ��ȡToken
   public static String getToken( final HttpServletRequest request ) throws KANException
   {
      return ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );
   }

   // ��֤Token
   public boolean isTokenValid( final HttpServletRequest request, final boolean flag )
   {
      try
      {
         // Request�е�Tokenֵ
         final String tokenValueRequest = ( String ) request.getParameter( TOKEN_NAME );
         // Cache�е�Tokenֵ
         final String tokenValueCache = ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );

         // ���Cache��Token
         if ( flag )
         {
            CachedUtil.delete( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );
         }

         // �ж�Request��Cache�е�Token�Ƿ�һ��
         if ( tokenValueRequest != null && !tokenValueRequest.trim().equals( "" ) && tokenValueCache != null && !tokenValueCache.trim().equals( "" )
               && tokenValueRequest.equalsIgnoreCase( tokenValueCache ) )
         {
            return true;
         }
      }
      catch ( final KANException e )
      {
      }

      return false;
   }

   // Object���룬Ajax������������봦��
   public void decodedObject( final Object object ) throws KANException
   {
      try
      {
         if ( object != null )
         {
            // ��ʼ��Class����
            final Class< ? > clazz = object.getClass();
            final Class< ? > clazzSuper = clazz.getSuperclass();
            // �õ�Object����Fields
            final Field[] fieldArray = KANUtil.mergeSuperclassField( clazz.getDeclaredFields(), clazzSuper.getDeclaredFields() );

            // ���Field���鲻Ϊ�գ����������
            if ( fieldArray != null && fieldArray.length > 0 )
            {
               for ( Field field : fieldArray )
               {
                  // ������ַ����ֶ�
                  if ( field.getType().getName().trim().equalsIgnoreCase( "java.lang.String" ) )
                  {
                     final int mod = field.getModifiers();
                     if ( Modifier.isPublic( mod ) && Modifier.isStatic( mod ) && Modifier.isFinal( mod ) )
                     {
                        // ����Ǿ�̬��������Ժ������
                        continue;
                     }
                     // ��ȡ��ǰ�ֶ�ֵ
                     final Object stringObject = KANUtil.getValue( object, field.getName() );

                     // ����ֶ�ֵ��Ϊ��
                     if ( stringObject != null && !( ( String ) stringObject ).trim().equals( "" ) )
                     {
                        KANUtil.setValue( object, field.getName(), URLDecoder.decode( URLDecoder.decode( ( String ) stringObject, "UTF-8" ), "UTF-8" ) );
                     }
                  }
               }
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // �жϵ�ǰAccess Action�Ƿ���Ҫ��ҳ
   public boolean isPaged( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��ʼ��TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // ��ʼ��ListDTO
      ListDTO listDTO = new ListDTO();
      if ( tableDTO != null )
      {
         listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );
         ;
      }

      if ( listDTO != null )
      {
         return listDTO.isPaged();
      }
      else
      {
         return false;
      }
   }

   // ��ȡ��ǰAccess Actionÿҳ��¼��
   public int getPageSize( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��ʼ��TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // ��ʼ��ListDTO
      ListDTO listDTO = new ListDTO();
      if ( tableDTO != null )
      {
         listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );
      }

      if ( listDTO != null )
      {
         return listDTO.getPageSize();
      }
      else
      {
         return 0;
      }
   }

   // �жϵ�ǰAccess Action�Ƿ���������
   public boolean isSearchFirst( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��ʼ��TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // ��ʼ��ListDTO
      ListDTO listDTO = new ListDTO();
      if ( tableDTO != null )
      {
         listDTO = tableDTO.getListDTO( getAccountId( request, null ), getCorpId( request, null ) );
      }

      if ( listDTO != null )
      {
         return listDTO.isSearchFirst();
      }
      else
      {
         return false;
      }
   }

   // ��װPage��ȡ����
   protected String getPage( final HttpServletRequest request )
   {
      return request.getParameter( "page" );
   }

   // ��װAjax��ȡ����
   protected String getAjax( final HttpServletRequest request )
   {
      return request.getParameter( "ajax" );
   }

   // ��װSubAction��ȡ����
   protected String getSubAction( final ActionForm form ) throws KANException
   {
      final String subAction = ( String ) KANUtil.getValue( form, "subAction" );

      if ( subAction != null && !subAction.trim().equalsIgnoreCase( "null" ) )
      {
         return subAction;
      }

      return "";
   }

   // SubAction�����װ
   protected void dealSubAction( final Object object, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // �����SubAction��ɾ���б����deleteObject
      if ( getSubAction( form ).equalsIgnoreCase( DELETE_OBJECTS ) )
      {
         // ����ɾ���б��SubAction
         delete_objectList( mapping, form, request, response );
      }
      // ���SubActionΪ�գ�ͨ����������������򡢷�ҳ�򵼳�������Ajax�ύ������������Ҫ���롣
      else
      {
         decodedObject( object );
      }
   }

   // Return�����װ
   protected ActionForward dealReturn( final String accessAction, final String forwardName, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException, IOException
   {
      // ����ǵ����򷵻�Render���ɵ��ֽ���
      if ( new Boolean( getAjax( request ) ) )
      {
         if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // insertlog( request, form, Operate.EXPORT, "1",
            // "download excel" );
            return new DownloadFileAction().exportList( mapping, form, request, response );
         }
         else
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();

            // Send to client
            out.println( ListRender.generateListTable( request, accessAction, new Boolean( KANUtil.filterEmpty( request.getAttribute( "useFixColumn" ) ) ) ) );
            printlnUserDefineMessageForAjaxPage( request, response );
            out.flush();
            out.close();

            return null;
         }
      }

      // ��ת���б����
      return mapping.findForward( forwardName );
   }

   // Added by Kevin Jin at 2013-12-06
   protected void passClientOrders( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ�����׼���
      final List< MappingVO > clientOrderHeaderMappingVOs = new ArrayList< MappingVO >();

      // ��ʼ��Service�ӿ�
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
      // ��ʼ��ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
      clientOrderHeaderVO.setCorpId( getCorpId( request, response ) );
      clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
      clientOrderHeaderVO.setStatus( "3, 5" );

      // ��õ�¼�ͻ���Ӧ������������Ϣ
      final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

      if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
      {
         for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs )
         {
            final MappingVO mappingVO = new MappingVO();
            final ClientOrderHeaderVO clientOrderHeaderVOTemp = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;
            mappingVO.setMappingId( clientOrderHeaderVOTemp.getOrderHeaderId() );

            if ( clientOrderHeaderVOTemp.getDescription() != null && !clientOrderHeaderVOTemp.getDescription().trim().isEmpty() )
            {
               mappingVO.setMappingValue( clientOrderHeaderVOTemp.getOrderHeaderId() + " - " + clientOrderHeaderVOTemp.getDescription() );
            }
            else
            {
               mappingVO.setMappingValue( clientOrderHeaderVOTemp.getOrderHeaderId() );
            }

            clientOrderHeaderMappingVOs.add( mappingVO );
         }
      }

      clientOrderHeaderMappingVOs.add( 0, KANUtil.getEmptyMappingVO( getLocale( request ) ) );
      request.setAttribute( "clientOrderHeaderMappingVOs", clientOrderHeaderMappingVOs );
   }

   protected void printlnUserDefineMessageForAjaxPage( final HttpServletRequest request, final HttpServletResponse response ) throws KANException, IOException
   {

      final String userDefineMsg = ( String ) request.getAttribute( USER_DEFINE_MESSAGE );
      if ( userDefineMsg != null && !userDefineMsg.isEmpty() )
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         out.println( "<div id=\"_USER_DEFINE_MSG\" style=\"display:none;\">" + userDefineMsg + "</div>" );
      }
   }

   public static void constantsInit( final String methodName, final String[] parameters ) throws MalformedURLException, RemoteException, NotBoundException, KANException
   {
      if ( KANUtil.filterEmpty( KANConstants.SYNCHRO_SERVERS ) != null )
      {
         String[] synchroServers = null;

         if ( KANConstants.SYNCHRO_SERVERS.contains( "," ) )
         {
            synchroServers = KANConstants.SYNCHRO_SERVERS.trim().split( "," );
         }
         else
         {
            synchroServers = KANConstants.SYNCHRO_SERVERS.trim().split( " " );
         }

         if ( synchroServers != null && synchroServers.length > 0 )
         {
            for ( String synchroServer : synchroServers )
            {
               final KANConstantsService remoteConstants = ( KANConstantsService ) Naming.lookup( "rmi://" + synchroServer + "/KANConstantsService" );

               KANUtil.call( remoteConstants, methodName, parameters );
            }
         }
      }
   }

   public static void constantsInit( final String methodName, final String parameter ) throws MalformedURLException, RemoteException, NotBoundException, KANException
   {
      constantsInit( methodName, new String[] { parameter } );
   }

   /**
    * ����������֤��֤��
    * 
    * @param methodName
    * @param parameters
    * @return
    * @throws MalformedURLException
    * @throws RemoteException
    * @throws NotBoundException
    * @throws KANException
    */
   public static Boolean validCaptcha( final String[] parameters ) throws MalformedURLException, RemoteException, NotBoundException, KANException
   {
      final String methodName = "validCaptcha";
      Object obj = null;
      boolean flag = false;
      if ( KANUtil.filterEmpty( KANConstants.SYNCHRO_SERVERS ) != null )
      {
         String[] synchroServers = null;

         if ( KANConstants.SYNCHRO_SERVERS.contains( "," ) )
         {
            synchroServers = KANConstants.SYNCHRO_SERVERS.trim().split( "," );
         }
         else
         {
            synchroServers = KANConstants.SYNCHRO_SERVERS.trim().split( " " );
         }

         if ( synchroServers != null && synchroServers.length > 0 )
         {
            for ( String synchroServer : synchroServers )
            {
               try
               {
                  final KANConstantsService remoteConstants = ( KANConstantsService ) Naming.lookup( "rmi://" + synchroServer + "/KANConstantsService" );
                  obj = KANUtil.call( remoteConstants, methodName, parameters );
                  if ( obj != null && ( Boolean ) obj )
                  {
                     // �����֤���˾Ͳ���Զ�̵�����
                     flag = true;
                     break;
                  }
               }
               catch ( Exception e )
               {
                  System.out.println( synchroServer + "���ر�.�˴�����Ժ���" );
                  e.printStackTrace();
                  continue;
               }

            }
         }
      }
      return flag;
   }

   public static < T extends BaseVO > void setAuthPositionIds( final String accountId, final UserVO loginUserVO, final String accessAction, final T vo )
   {
      KANAccountConstants constant = KANConstants.getKANAccountConstants( accountId );

      StaffDTO staffDTO = constant.getStaffDTOByUserId( loginUserVO.getUserId() );

      Set< RuleVO > list = constant.getRuleVOs( loginUserVO, accessAction );

      if ( list != null && list.size() == 0 )
      {
         return;
      }

      List< String > inPositionIds = new ArrayList< String >();

      List< String > notInpositionIds = new ArrayList< String >();

      // 1���� 2���� 3�ϼ� 4���� 5 ˽��
      for ( RuleVO ruleVO : list )
      {

         // ��ȡ����
         if ( StringUtils.isNotBlank( ruleVO.getRemark1() ) && ruleVO.getRemark1().contains( AuthConstants.RULE_VIEW ) )
         {

            // �������������迼�Ƕ�ȡ

            if ( AuthConstants.RULE_BRANCH_TYPE.equals( ruleVO.getRuleType() ) )
            {
               inPositionIds.addAll( staffDTO.getBranchPositions() );
            }
            else if ( AuthConstants.RULE_SUPER_TYPE.equals( ruleVO.getRuleType() ) )
            {
               inPositionIds.addAll( staffDTO.getParentPositions() );
            }
            else if ( AuthConstants.RULE_SUB_TYPE.equals( ruleVO.getRuleType() ) )
            {
               inPositionIds.addAll( staffDTO.getChildPositions() );
            }
            else if ( AuthConstants.RULE_PRIVATE_TYPE.equals( ruleVO.getRuleType() ) )
            {
               List< MappingVO > mappingVOs = loginUserVO.getPositions();
               if ( mappingVOs != null )
               {
                  for ( MappingVO mappingVO : mappingVOs )
                  {
                     if ( mappingVO.getMappingId() == null )
                        continue;
                     inPositionIds.add( mappingVO.getMappingId() );
                  }
               }
            }

         }

         // ���ɼ�����
         if ( StringUtils.isNotBlank( ruleVO.getRemark1() ) && ruleVO.getRemark1().contains( AuthConstants.RULE_HIDE ) )
         {

            // ���������͡�˽�С����򣬲��迼�ǲ��ɼ�

            if ( AuthConstants.RULE_BRANCH_TYPE.equals( ruleVO.getRuleType() ) )
            {
               List< String > hideList = staffDTO.getBranchPositions();
               List< MappingVO > mappingVOs = loginUserVO.getPositions();
               if ( mappingVOs != null )
               {
                  for ( MappingVO mappingVO : mappingVOs )
                  {
                     if ( mappingVO.getMappingId() == null )
                        continue;
                     // �Ƴ���ǰ��¼�˵�position
                     hideList.remove( mappingVO.getMappingId() );
                  }
               }
               notInpositionIds.addAll( hideList );
            }
            else if ( AuthConstants.RULE_SUPER_TYPE.equals( ruleVO.getRuleType() ) )
            {
               notInpositionIds.addAll( staffDTO.getParentPositions() );
            }
            else if ( AuthConstants.RULE_SUB_TYPE.equals( ruleVO.getRuleType() ) )
            {
               notInpositionIds.addAll( staffDTO.getChildPositions() );
            }
         }

      }
      vo.setHasIn( KANUtil.convertListToString( inPositionIds, "," ) );
      vo.setNotIn( KANUtil.convertListToString( notInpositionIds, "," ) );

   }

   public static String getPropertyFromCookie( final HttpServletRequest request, final HttpServletResponse response, final String propertyName ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( propertyName ) : ( String ) request.getAttribute( propertyName );
   }

   public static void setDataAuth( final HttpServletRequest request, final HttpServletResponse response, final BaseVO baseVO ) throws KANException
   {
      // dataRole(0:��ѡ�� 1:HR 2:HR������ 3����ͨԱ�� 4����ͨԱ��������)
      // ruleId (0:˽�� 1:�¼� 2:����Ա�� 3:���� 4:ְλ 5:ְ�� 6:��Ŀ 7:����ʵ�� 8:�¼�HR 9:ȫ��)
      UserVO userVO = getUserVOFromClient( request, response );
      KANAccountConstants kanAccountConstants = KANConstants.getKANAccountConstants( getAccountId( request, null ) );
      List< GroupDTO > loginGroupDTOList = kanAccountConstants.getGroupDTOsByPositionId( userVO.getPositionId() );
      // StaffDTO staffDTO = kanAccountConstants.getStaffDTOByUserId(
      // userVO.getUserId() );
      List< String > childrenPosition = kanAccountConstants.getChildPositionIdsByPositionId( userVO.getPositionId() );
      // List< String > childrenPosition = staffDTO.getChildPositions();
      List< String > hrPositionIds = new ArrayList< String >();
      Set< String > rulePrivateIds = new HashSet< String >();
      Set< String > rulePositionIds = new HashSet< String >();
      Set< String > ruleBusinessTypeIds = new HashSet< String >();
      Set< String > ruleRuleEntityIds = new HashSet< String >();
      Set< String > ruleBranchIds = new HashSet< String >();
      boolean isHRManagerFlag = false;
      baseVO.setRulePublic( AuthConstants.RULE_UN_PUBLIC );

      // ��¼����
      for ( GroupDTO groupDTO : loginGroupDTOList )
      {
         if ( StringUtils.equals( groupDTO.getGroupVO().getDataRole(), "2" ) )
         {
            isHRManagerFlag = true;
            break;
         }
      }

      // �����ǰ��¼����hr�����ߣ�����������HR�����߻�����HR
      if ( isHRManagerFlag )
      {
         for ( String childPosition : childrenPosition )
         {
            List< GroupDTO > childrenGroupDTOList = kanAccountConstants.getGroupDTOsByPositionId( childPosition );
            for ( GroupDTO childGroupDTO : childrenGroupDTOList )
            {
               String dataRole = childGroupDTO.getGroupVO().getDataRole();
               if ( "1".equals( dataRole ) || "2".equals( dataRole ) )
               {
                  hrPositionIds.add( childPosition );
               }
            }
         }
      }

      // ��¼����
      for ( GroupDTO loginGroupDTO : loginGroupDTOList )
      {
         if ( !StringUtils.equals( loginGroupDTO.getGroupVO().getDataRole(), "0" ) )
         {
            // ����˽��Ȩ��
            List< GroupModuleRuleRelationVO > loginGroupModuleRuleRelationVOList = loginGroupDTO.getGroupModuleRuleRelationVOs();
            for ( GroupModuleRuleRelationVO logicGroupModuleRuleRelationVO : loginGroupModuleRuleRelationVOList )
            {
               String ruleId = logicGroupModuleRuleRelationVO.getRuleId();

               // ȫ��
               if ( "9".equals( ruleId ) )
               {
                  baseVO.setRulePrivateIds( null );
                  baseVO.setRulePositionIds( null );
                  baseVO.setRuleBranchIds( null );
                  baseVO.setRuleBusinessTypeIds( null );
                  baseVO.setRuleEntityIds( null );
                  baseVO.setRulePublic( AuthConstants.RULE_PUBLIC );
                  return;
               }

               // ˽��(Ĭ��ֵ)
               if ( "0".equals( ruleId ) )
               {
                  String rulePrivateID = getEmployeeId( request, response );
                  if ( StringUtils.isNotBlank( rulePrivateID ) )
                  {
                     rulePrivateIds.add( rulePrivateID );
                  }
               }

               // �¼�
               if ( "1".equals( ruleId ) )
               {
                  // �����¼�Ȩ��(�����¼�postion��Ӧ��employee)
                  rulePrivateIds.addAll( kanAccountConstants.getEmployeeIdListByPositionId( childrenPosition ) );
               }

               // ����Ա��
               if ( "2".equals( ruleId ) )
               {
                  rulePositionIds.add( userVO.getPositionId() );
               }

               // ���ţ�ְλ��ְ��
               if ( "3".equals( ruleId ) || "4".equals( ruleId ) || "5".equals( ruleId ) )
               {
                  List< String > tempPositionList = new ArrayList< String >();

                  if ( "3".equals( ruleId ) )
                  {
                     String[] branchIds = KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() );
                     tempPositionList.addAll( kanAccountConstants.getPositionIdListByBranchId( Arrays.asList( branchIds ), baseVO.getCorpId() ) );
                     ruleBranchIds.addAll( KANUtil.jasonArrayToStringList( logicGroupModuleRuleRelationVO.getRemark1() ) );
                  }

                  // ְλ
                  if ( "4".equals( ruleId ) )
                  {
                     tempPositionList.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() ) ) );
                  }

                  // ְ��
                  if ( "5".equals( ruleId ) )
                  {
                     String[] positionGradeIds = KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() );
                     tempPositionList.addAll( kanAccountConstants.getPositionIdListByPositionGradeId( Arrays.asList( positionGradeIds ), baseVO.getCorpId() ) );
                  }
                  rulePrivateIds.addAll( kanAccountConstants.getEmployeeIdListByPositionId( tempPositionList ) );
               }

               // ��Ŀ
               if ( "6".equals( ruleId ) )
               {
                  ruleBusinessTypeIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() ) ) );
               }

               // ����ʵ��
               if ( "7".equals( ruleId ) )
               {
                  ruleRuleEntityIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() ) ) );
               }

               // �¼�HR
               if ( "8".equals( ruleId ) )
               {
                  for ( String hrPosition : hrPositionIds )
                  {
                     List< GroupDTO > hrGroupDTOList = kanAccountConstants.getGroupDTOsByPositionId( hrPosition );
                     for ( GroupDTO hrGroupDTO : hrGroupDTOList )
                     {
                        List< GroupModuleRuleRelationVO > hrGroupModuleRuleRelationVOList = hrGroupDTO.getGroupModuleRuleRelationVOs();
                        for ( GroupModuleRuleRelationVO hrGroupModuleRuleRelationVO : hrGroupModuleRuleRelationVOList )
                        {
                           String hrRuleId = hrGroupModuleRuleRelationVO.getRuleId();

                           // ȫ��
                           if ( "9".equals( hrRuleId ) )
                           {
                              baseVO.setRulePrivateIds( null );
                              baseVO.setRulePositionIds( null );
                              baseVO.setRuleBranchIds( null );
                              baseVO.setRuleBusinessTypeIds( null );
                              baseVO.setRuleEntityIds( null );
                              baseVO.setRulePublic( AuthConstants.RULE_PUBLIC );
                              return;
                           }

                           // ����Ա��
                           if ( "2".equals( hrRuleId ) )
                           {
                              rulePositionIds.add( hrPosition );
                           }

                           // ���ţ�ְλ��ְ��
                           if ( "3".equals( hrRuleId ) || "4".equals( hrRuleId ) || "5".equals( hrRuleId ) )
                           {
                              List< String > tempHRPositionList = new ArrayList< String >();

                              // ����
                              if ( "3".equals( hrRuleId ) )
                              {
                                 String[] branchIds = KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() );
                                 tempHRPositionList.addAll( kanAccountConstants.getPositionIdListByBranchId( Arrays.asList( branchIds ), baseVO.getCorpId() ) );
                              }

                              // ְλ
                              if ( "4".equals( hrRuleId ) )
                              {
                                 tempHRPositionList.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() ) ) );
                              }
                              // ְ��
                              if ( "5".equals( hrRuleId ) )
                              {
                                 String[] positionGradeIds = KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() );
                                 tempHRPositionList.addAll( kanAccountConstants.getPositionIdListByPositionGradeId( Arrays.asList( positionGradeIds ), baseVO.getCorpId() ) );
                              }

                              rulePrivateIds.addAll( kanAccountConstants.getEmployeeIdListByPositionId( tempHRPositionList ) );
                           }

                           // ��Ŀ
                           if ( "6".equals( hrRuleId ) )
                           {
                              ruleBusinessTypeIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() ) ) );
                           }

                           // ����ʵ��
                           if ( "7".equals( hrRuleId ) )
                           {
                              ruleRuleEntityIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() ) ) );
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      baseVO.setRulePrivateIds( rulePrivateIds.size() == 0 ? null : rulePrivateIds );
      baseVO.setRulePositionIds( rulePositionIds.size() == 0 ? null : rulePositionIds );
      baseVO.setRuleBusinessTypeIds( ruleBusinessTypeIds.size() == 0 ? null : ruleBusinessTypeIds );
      baseVO.setRuleEntityIds( ruleRuleEntityIds.size() == 0 ? null : ruleRuleEntityIds );
      baseVO.setRuleBranchIds( ruleBranchIds.size() == 0 ? null : ruleBranchIds );
   }

   /**
    * ��¼������־
    * 
    * @param request
    * @param object
    * @param op
    * @param remark
    */
   public void insertlog( final HttpServletRequest request, final Object object, Operate op, String pKey, String remark )
   {
      try
      {
         LogVO log = new LogVO();
         // ��ֹjsonת��û�г�ʼ���ݶ�����
         if ( object != null && object instanceof ActionForm )
         {
            ( ( ActionForm ) object ).reset( null, request );
         }

         // �����Ա��������Ϣ
         if ( object != null && object instanceof EmployeeVO )
         {
            log.setEmployeeId( ( ( EmployeeVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeeVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeeVO ) object ).getNameZH() );
            log.setEmployeeNameEN( ( ( EmployeeVO ) object ).getNameEN() );
         }
         // �����Ա���Ͷ���ͬ
         else if ( object != null && object instanceof EmployeeContractVO )
         {
            log.setEmployeeId( ( ( EmployeeContractVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeeContractVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeeContractVO ) object ).getEmployeeNameZH() );
            log.setEmployeeNameEN( ( ( EmployeeContractVO ) object ).getEmployeeNameEN() );
         }
         // ������춯/��Ǩ
         else if ( object != null && object instanceof EmployeePositionChangeVO )
         {
            log.setEmployeeId( ( ( EmployeePositionChangeVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeePositionChangeVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeePositionChangeVO ) object ).getEmployeeNameZH() );
            log.setEmployeeNameEN( ( ( EmployeePositionChangeVO ) object ).getEmployeeNameEN() );
         }
         // ������춯/��Ǩ
         else if ( object != null && object instanceof EmployeeSalaryAdjustmentVO )
         {
            log.setEmployeeId( ( ( EmployeeSalaryAdjustmentVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeeSalaryAdjustmentVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeeSalaryAdjustmentVO ) object ).getEmployeeNameZH() );
            log.setEmployeeNameEN( ( ( EmployeeSalaryAdjustmentVO ) object ).getEmployeeNameEN() );
         }

         UserVO userVO = getUserVOFromClient( request, null );
         if ( userVO != null )
         {
            log.setOperateBy( userVO.decodeUserId( userVO.getUserId() ) );
         }
         else
         {
            log.setOperateBy( ( ( BaseVO ) object ).decodeUserId( ( String ) request.getAttribute( "userId_mail" ) ) );
         }
         log.setType( op.getIndex() + "" );
         log.setModule( object == null ? "" : object.getClass().getCanonicalName() );
         log.setContent( object == null ? "" : JsonMapper.toLogJson( object ) );
         log.setIp( getIPAddress( request ) );
         final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
         log.setOperateTime( sdf.format( new Date() ) );
         log.setpKey( pKey + "" );
         log.setRemark( remark );

         final LogService logService = ( LogService ) getService( "logService" );
         logService.insertLog( log );

      }
      catch ( Exception e )
      {
         e.printStackTrace();
         logger.error( "record log error : **************** " + op.toString() + ":" + ( object == null ? "" : object.getClass().getCanonicalName() ) + " ****************" );
      }

   }

   /**
    * 0 ��ʾͬ���ɹ�.-1��ʾʧ��
    * 
    * @param employeeId
    * @return
    */
   public static int syncWXContacts( final String employeeId )
   {
      try
      {
         return syncWXContacts( employeeId, false );
      }
      catch ( Exception e )
      {
         System.out.println( "record log error : ͬ��΢��ͨѶ¼ʧ��;����Ҫ����" );
         e.printStackTrace();
         return -1;
      }
   }

   /**
    * ͬ��΢��
    */
   public static int syncWXContacts( final String employeeId, final boolean isDeleted )
   {
      int result = 0;
      // �����debug�Ͳ�ͬ����
      if ( KANConstants.ISDEBUG )
      {
         System.out.println( "����ģʽ��ͬ��΢��" );
         return result;
      }
      try
      {

         EmployeeVO employeeVO = null;
         // ����������ƽ
         EmployeeVO employeeVO2 = null;
         EmployeeContractVO employeeContractVO = null;
         EmployeeContractVO employeeContractVO2 = null;
         String employeeid2 = "";
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

         if ( "100008283".equals( employeeId ) || "100012743".equals( employeeId ) )
         {
            employeeid2 = "100008283".equals( employeeId ) ? "100012743" : "100008283";
            employeeVO2 = employeeService.getEmployeeVOByEmployeeId( employeeid2 );
         }

         EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final List< Object > objects = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeId );
         String guyuanleixinger = "";
         if ( objects != null && objects.size() > 0 )
         {
            employeeContractVO = ( EmployeeContractVO ) objects.get( 0 );
            // ����� other payroll������

            JSONObject tmpRemark1 = KANUtil.toJSONObject( employeeContractVO.getRemark1() );

            guyuanleixinger = tmpRemark1.getString( "guyuanleixinger" );
         }
         // ���������ƽ
         if ( employeeVO2 != null )
         {
            final List< Object > objects2 = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeid2 );
            if ( objects2 != null && objects2.size() > 0 )
            {
               employeeContractVO2 = ( EmployeeContractVO ) objects2.get( 0 );
            }
         }
         // ���������ƽ
         if ( employeeVO2 != null )
         {
            dealWX4Ricky( employeeVO, employeeVO2, employeeContractVO, employeeContractVO2 );
         }
         int errcode = -1;
         int operid = 0;
         String wxmsg = "";
         final String[] opers = new String[] { "", "add", "update", "delete" };
         // �����ɾ��. ������Ա��״̬������ְ�Ļ�����other on payroll
         if ( ( employeeVO != null && isDeleted ) || !"1".equals( employeeVO.getStatus() )
               || ( !"1".equals( String.valueOf( guyuanleixinger ) ) && !"3".equals( String.valueOf( guyuanleixinger ) ) ) )
         {
            final JSONObject deleteReturnJson = WXUtil.deleteEmployee( employeeVO );
            errcode = deleteReturnJson.getInt( "errcode" );
            wxmsg = deleteReturnJson.getString( "errmsg" );
            operid = 3;

         }
         else if ( employeeVO != null && !isDeleted )
         {
            final JSONObject jsonObject = WXUtil.getEmployee( employeeVO );
            if ( KANUtil.filterEmpty( jsonObject ) != null )
            {
               // errcodeFind ��ѯ���
               int findCode = jsonObject.getInt( "errcode" );
               if ( KANUtil.filterEmpty( findCode ) != null )
               {
                  if ( findCode == 0 )
                  {
                     // �ҵ������Ա�� �޸�
                     WXContactVO wxContactVO = new WXContactVO();
                     wxContactVO.resetWXContactVO( employeeVO, employeeContractVO );
                     final JSONObject updateReturnJson = WXUtil.updateEmployee( wxContactVO );
                     errcode = updateReturnJson.getInt( "errcode" );
                     wxmsg = updateReturnJson.getString( "errmsg" );
                     operid = 2;
                  }
                  // 60111 û�ҵ�
                  else if ( findCode == 60111 )
                  {
                     // û�ҵ�. ����
                     WXContactVO wxContactVO = new WXContactVO();
                     wxContactVO.resetWXContactVO( employeeVO, employeeContractVO );
                     final JSONObject insertReturnJson = WXUtil.insertEmployee( wxContactVO );
                     errcode = insertReturnJson.getInt( "errcode" );
                     wxmsg = insertReturnJson.getString( "errmsg" );
                     operid = 2;
                  }
               }
            }
         }

         if ( employeeVO != null )
         {
            System.out.println( "΢��ͬ�����:" + ( errcode == 0 ? "success" : "error" ) + ";[" + wxmsg + "],��������:" + opers[ operid ] + ",employeeId=" + employeeVO.getEmployeeId() );
            result = ( errcode == 0 ? 0 : -1 );
         }

      }
      catch ( Exception e )
      {
         e.printStackTrace();
         System.out.println( "record log error : ͬ��΢��ͨѶ¼ʧ��;����Ҫ����" );
         return -1;
      }
      return result;
   }

   /**
    * ͬ��΢�Ų�ɾ��
    */
   public static int syncWXContacts_not_delete( final String employeeId, final boolean isDeleted )
   {
      int result = 0;
      // �����debug�Ͳ�ͬ����
      if ( KANConstants.ISDEBUG )
      {
         System.out.println( "����ģʽ��ͬ��΢��" );
         return result;
      }
      try
      {

         EmployeeVO employeeVO = null;
         // ����������ƽ
         EmployeeVO employeeVO2 = null;
         EmployeeContractVO employeeContractVO = null;
         EmployeeContractVO employeeContractVO2 = null;
         String employeeid2 = "";
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

         if ( "100008283".equals( employeeId ) || "100012743".equals( employeeId ) )
         {
            employeeid2 = "100008283".equals( employeeId ) ? "100012743" : "100008283";
            employeeVO2 = employeeService.getEmployeeVOByEmployeeId( employeeid2 );
         }

         EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final List< Object > objects = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeId );
         if ( objects != null && objects.size() > 0 )
         {
            employeeContractVO = ( EmployeeContractVO ) objects.get( 0 );
            // ����� other payroll������
         }
         // ���������ƽ
         if ( employeeVO2 != null )
         {
            final List< Object > objects2 = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeid2 );
            if ( objects2 != null && objects2.size() > 0 )
            {
               employeeContractVO2 = ( EmployeeContractVO ) objects2.get( 0 );
            }
         }
         // ���������ƽ
         if ( employeeVO2 != null )
         {
            dealWX4Ricky( employeeVO, employeeVO2, employeeContractVO, employeeContractVO2 );
         }
         int errcode = -1;
         int operid = 0;
         String wxmsg = "";
         final String[] opers = new String[] { "", "add", "update", "delete" };
         // �����ɾ��. ������Ա��״̬������ְ�Ļ�����other on payroll
         if ( ( employeeVO != null && isDeleted ) || !"1".equals( employeeVO.getStatus() ) )
         {
            errcode = 2;
            wxmsg = "ͬ�����в���ɾ������";
            operid = 3;
         }
         else if ( employeeVO != null && !isDeleted )
         {
            final JSONObject jsonObject = WXUtil.getEmployee( employeeVO );
            if ( KANUtil.filterEmpty( jsonObject ) != null )
            {
               // errcodeFind ��ѯ���
               int findCode = jsonObject.getInt( "errcode" );
               if ( KANUtil.filterEmpty( findCode ) != null )
               {
                  if ( findCode == 0 )
                  {
                     // �ҵ������Ա�� �޸�
                     WXContactVO wxContactVO = new WXContactVO();
                     wxContactVO.resetWXContactVO( employeeVO, employeeContractVO );
                     final JSONObject updateReturnJson = WXUtil.updateEmployee( wxContactVO );
                     errcode = updateReturnJson.getInt( "errcode" );
                     wxmsg = updateReturnJson.getString( "errmsg" );
                     operid = 2;
                  }
                  // 60111 û�ҵ�
                  else if ( findCode == 60111 )
                  {
                     // û�ҵ�. ����
                     WXContactVO wxContactVO = new WXContactVO();
                     wxContactVO.resetWXContactVO( employeeVO, employeeContractVO );
                     final JSONObject insertReturnJson = WXUtil.insertEmployee( wxContactVO );
                     errcode = insertReturnJson.getInt( "errcode" );
                     wxmsg = insertReturnJson.getString( "errmsg" );
                     operid = 2;
                  }
               }
            }
         }

         if ( employeeVO != null )
         {
            System.out.println( "΢��ͬ�����:" + ( errcode == 0 ? "success" : "error" ) + ";[" + wxmsg + "],��������:" + opers[ operid ] + ",employeeId=" + employeeVO.getEmployeeId() );
            result = ( errcode == 0 ? 0 : -1 );
         }

      }
      catch ( Exception e )
      {
         e.printStackTrace();
         System.out.println( "record log error : ͬ��΢��ͨѶ¼ʧ��;����Ҫ����" );
         return -1;
      }
      return result;
   }

   /**
    * ΢��ͬ��.���������ƽ
    * @param employeeVO
    * @param employeeVO2
    * @param employeeContractVO
    * @param employeeContractVO2
    */
   private static void dealWX4Ricky( EmployeeVO employeeVO, EmployeeVO employeeVO2, EmployeeContractVO employeeContractVO, EmployeeContractVO employeeContractVO2 )
   {
      //dept,location,title,bufunction
      // ��Ӧ����
      if ( KANUtil.filterEmpty( employeeVO2.get_tempParentBranchIds() ) != null && !employeeVO.get_tempParentBranchIds().equals( employeeVO2.get_tempParentBranchIds() ) )
      {
         employeeVO.set_tempParentBranchIds( employeeVO.get_tempParentBranchIds() + "," + employeeVO2.get_tempParentBranchIds() );
      }
      // 
      if ( KANUtil.filterEmpty( employeeVO2.get_tempBranchIds() ) != null && !employeeVO.get_tempBranchIds().equals( employeeVO2.get_tempBranchIds() ) )
      {
         employeeVO.set_tempBranchIds( employeeVO.get_tempBranchIds() + "," + employeeVO2.get_tempBranchIds() );
      }
      // location
      if ( KANUtil.filterEmpty( employeeVO2.getRemark1() ) != null )
      {
         //1�İ칫�ص�
         final JSONObject jsonRemark = KANUtil.toJSONObject( employeeVO.getRemark1() );
         String bangongdidian1 = jsonRemark.getString( "bangongdidian" );
         //2�İ칫�ص�
         final JSONObject jsonRemark1 = KANUtil.toJSONObject( employeeVO2.getRemark1() );
         String bangongdidian2 = jsonRemark1.getString( "bangongdidian" );
         if ( KANUtil.filterEmpty( bangongdidian2 ) != null )
         {
            String bangongdidian = bangongdidian1 + "," + bangongdidian2;
            jsonRemark.put( "bangongdidian", bangongdidian );
            employeeVO.setRemark1( jsonRemark.toString() );
         }
      }
      //title
      if ( KANUtil.filterEmpty( employeeContractVO2.getRemark1() ) != null )
      {
         //1��title
         final JSONObject jsonRemark = KANUtil.toJSONObject( employeeContractVO.getRemark1() );
         String zhiweimingchengyingwen1 = jsonRemark.getString( "zhiweimingchengyingwen" );
         //2��title
         final JSONObject jsonRemark2 = KANUtil.toJSONObject( employeeContractVO2.getRemark1() );
         String zhiweimingchengyingwen2 = jsonRemark2.getString( "zhiweimingchengyingwen" );
         if ( KANUtil.filterEmpty( zhiweimingchengyingwen2 ) != null && !zhiweimingchengyingwen2.trim().equalsIgnoreCase( zhiweimingchengyingwen1.trim() ) )
         {
            String zhiweimingchengyingwen = zhiweimingchengyingwen1 + " & " + zhiweimingchengyingwen2;
            jsonRemark.put( "zhiweimingchengyingwen", zhiweimingchengyingwen );
            employeeContractVO.setRemark1( jsonRemark.toString() );
         }
      }

   }
}