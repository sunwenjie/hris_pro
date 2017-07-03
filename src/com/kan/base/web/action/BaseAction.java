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

   // “爱点击”
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
   // hro 自动登录
   protected final static String AUTO_LOGON_USER = "auto_logon_user";
   protected final static String AUTO_LOGON_EMPLOYEE_USER = "auto_logon_employee_user";

   // hrm 自动登录
   protected final static String AUTO_LOGONI_USER = "auto_logoni_user";

   // Vendor 自动登录
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
    * 后续需要删除 - Start
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
    * 后续需要删除 - End
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
         // Json字节流存至客户端
         String cookieUser = JSONObject.fromObject( userVO, UserVO.USERVO_JSONCONFIG ).toString();
         JSONObject cookieUser_JSON = JSONObject.fromObject( cookieUser );

         request.setAttribute( COOKIE_USER_JSON, cookieUser_JSON );
         final Cookie cookie = new Cookie( COOKIE_USER, URLEncoder.encode( cookieUser, "gbk" ) );
         // cookie.setMaxAge( 60 * 15 );//15分钟
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
      // 指定要转成为的对象的成员属性的数据类型
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
            // 从客户端获取Json字节流并转换成User对象
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

      // 从客户端获取Json字节流并转换成User对象
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

   // 设定权限
   @Deprecated
   public void setHRFunctionRole( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 如果是inHouse
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         request.setAttribute( "isHRFunction", !isHRFunction( request, response ) ? "2" : "1" );
      }
      // 非inHouse默认HRBranch
      else
      {
         request.setAttribute( "isHRFunction", "1" );
      }

   }

   // 显示导出按钮
   public void showExportButton( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 初始化corpId
      final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

      // 获取ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( ( String ) request.getAttribute( "javaObjectName" ), corpId );

      // 判断列表是否需要添加导出功能
      if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
            && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
      {
         request.setAttribute( "isExportExcel", "1" );
      }
   }

   // 获取职位组是否具有HR职能
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

   // 获取EmployeeId(InHouse 情况下)
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

   // 获取BranchId
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
    * 是否为外网 1内网 2外网
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
    * 自动登录删除cookie
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
            return "zh".equalsIgnoreCase( language ) ? "添加" : "Add ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_UPDATE ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "编辑" : "Edit ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_DELETE ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "删除" : "Delete ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_SUBMIT ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "提交" : "Submit ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_ROLLBACK ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "退回" : "Return ";
         }
         else if ( type.trim().equalsIgnoreCase( MESSAGE_TYPE_SAVE ) )
         {
            return "zh".equalsIgnoreCase( language ) ? "保存" : "Save ";
         }
      }

      return "";
   }

   // 传送失败标记给页面
   protected void error( final HttpServletRequest request )
   {
      error( request, null, null, null );
   }

   // 传送失败标记给页面 - 重载
   protected void error( final HttpServletRequest request, final String type )
   {
      error( request, type, null, null );
   }

   // 传送失败标记给页面 - 重载
   protected void error( final HttpServletRequest request, final String type, final String message )
   {
      error( request, type, message, null );
   }

   // 传送失败标记给页面 - 重载
   protected void error( final HttpServletRequest request, final String type, final String message, final String flag )
   {
      final String language = request.getLocale().getLanguage();
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( MESSAGE, message == null ? ( decodeMessageType( type, language ) + ( "zh".equalsIgnoreCase( language ) ? "出错！" : "error!" ) ) : message );
      request.setAttribute( MESSAGE_CLASS, "error" );
   }

   // 传送警告标记给页面
   protected void warning( final HttpServletRequest request )
   {
      warning( request, null, null, null );
   }

   // 传送警告标记给页面 - 重载
   protected void warning( final HttpServletRequest request, final String type )
   {
      warning( request, type, null, null );
   }

   // 传送警告标记给页面 - 重载
   protected void warning( final HttpServletRequest request, final String type, final String message )
   {
      warning( request, type, message, null );
   }

   // 传送警告标记给页面 - 重载
   protected void warning( final HttpServletRequest request, final String type, final String message, final String flag )
   {
      final String language = request.getLocale().getLanguage();
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( MESSAGE, message == null ? decodeMessageType( type, language ) + ( ( "zh".equalsIgnoreCase( language ) ? "警告！" : "warning!" ) ) : message );
      request.setAttribute( MESSAGE_CLASS, "warning" );
   }

   // 传送成功标记给页面
   protected void success( final HttpServletRequest request )
   {
      success( request, null, null, null );
   }

   // 传送成功标记给页面 - 重载
   protected void success( final HttpServletRequest request, final String type )
   {
      success( request, type, null, null );
   }

   // 传送成功标记给页面 - 重载
   protected void success( final HttpServletRequest request, final String type, final String message )
   {
      success( request, type, message, null );
   }

   // 传送成功标记给页面 - 重载
   protected void success( final HttpServletRequest request, final String type, final String message, final String flag )
   {
      final String language = request.getLocale().getLanguage();
      if ( KANUtil.filterEmpty( flag ) != null )
      {
         request.setAttribute( flag, flag );
      }

      request.setAttribute( MESSAGE, message == null ? ( decodeMessageType( type, language ) + ( "zh".equalsIgnoreCase( language ) ? "成功！" : "success!" ) ) : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
   }

   // 传送保存成功标记给页面
   @Deprecated
   protected void addSuccess( final HttpServletRequest request )
   {
      addSuccess( request, "保存成功！", null );
   }

   // 传送保存成功标记给页面 - 重载
   @Deprecated
   protected void addSuccess( final HttpServletRequest request, final String message )
   {
      addSuccess( request, message, null );
   }

   // 传送保存成功标记给页面 - 重载
   @Deprecated
   protected void addSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "保存成功！" : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_ADD );
   }

   // 传送编辑成功标记给页面
   @Deprecated
   protected void updateSuccess( final HttpServletRequest request )
   {
      updateSuccess( request, "编辑成功！", null );
   }

   // 传送编辑成功标记给页面 - 重载
   @Deprecated
   protected void updateSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "编辑成功！" : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_UPDATE );
   }

   // 传送删除成功标记给页面
   @Deprecated
   protected void submitSuccess( final HttpServletRequest request )
   {
      deleteSuccess( request, "提交成功，审核中...", null );
   }

   // 传送删除成功标记给页面 - 重载
   @Deprecated
   protected void submitSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "提交成功，审核中..." : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_SUBMIT );
   }

   // 传送删除成功标记给页面
   @Deprecated
   protected void deleteSuccess( final HttpServletRequest request )
   {
      deleteSuccess( request, "删除成功！", null );
   }

   // 传送删除成功标记给页面 - 重载
   @Deprecated
   protected void deleteSuccess( final HttpServletRequest request, final String message, final String flag )
   {
      if ( flag != null && !flag.trim().isEmpty() )
      {
         request.setAttribute( flag, "" );
      }

      request.setAttribute( SUCCESS_MESSAGE, message == null ? "删除成功！" : message );
      request.setAttribute( MESSAGE_CLASS, "success" );
      request.setAttribute( SUCCESS_MESSAGE_TYPE, SUCCESS_MESSAGE_TYPE_DELETE );
   }

   // 传送删除成功标记给Ajax调用
   protected void deleteSuccessAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "success" );

         out.println( jsonObject );
      }
   }

   // 传送删除成功标记给Ajax调用
   protected void deleteFailedAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "failed" );

         out.println( jsonObject );
      }
   }

   // 传送添加成功标记给Ajax调用
   protected void addSuccessAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "success" );

         out.println( jsonObject );
      }
   }

   // 传送添加成功标记给Ajax调用
   protected void addFailedAjax( final PrintWriter out, final Map< String, Object > map )
   {
      if ( out != null )
      {
         final JSONObject jsonObject = KANUtil.getJSONObject( map );
         jsonObject.put( "status", "failed" );

         out.println( jsonObject );
      }
   }

   // 获取记录被更改的时间戳
   public static String getHisTitle( final BaseVO baseVO, final HttpServletRequest request ) throws KANException
   {
      return baseVO.getDecodeModifyDate() + " " + KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffNameByUserId( baseVO.getModifyBy() ) + " Update";
   }

   /**
    * 修改remark1的自定义字段
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

   // 获取自定义字段值并转换成JSON String
   public static String saveDefineColumns( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // 初始化Map对象，用于装载自定义Column Value
      final Map< String, String > values = new HashMap< String, String >();

      // 遍历Column Group
      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {
            // 遍历Column
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  // 只提取用户自定义的Column Value
                  if ( columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                  {
                     values.put( columnVO.getNameDB(), request.getParameter( accessAction + "_" + columnVO.getNameDB() ) );
                  }
               }
            }
         }
      }

      // 将当前Map转成JSON String
      final JSONObject jsonObject = new JSONObject();
      jsonObject.putAll( values );

      return jsonObject.toString();
   }

   // 将搜索中自定义的字段转成数据库搜索条件
   public static String generateDefineListSearches( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      try
      {
         // 获取当前需要生成控件管理界面的TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
         // 初始化搜索条件字符串
         String searches = "";

         final ListDTO listDTO = tableDTO.getListDTO( getAccountId( request, null ), getCorpId( request, null ) );

         // 遍历Column Group
         if ( tableDTO != null && listDTO != null && listDTO.getSearchDTO() != null && listDTO.getSearchDTO().getSearchDetailVOs() != null
               && listDTO.getSearchDTO().getSearchDetailVOs().size() > 0 )
         {
            for ( SearchDetailVO searchDetailVO : listDTO.getSearchDTO().getSearchDetailVOs() )
            {
               // 初始化字段对象
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

               // 只提取用户自定义的Column Value
               if ( columnVO != null && columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
               {
                  // 初始化搜索字段的内容
                  String searchContent = request.getParameter( accessAction + "_" + columnVO.getNameDB() );
                  // 如果存在搜索值
                  if ( searchContent != null && !searchContent.trim().equals( "" ) && !searchContent.trim().equals( "0" ) )
                  {
                     // 提交内容进行解码
                     searchContent = URLDecoder.decode( searchContent, "UTF-8" );

                     if ( searches != null && !searches.trim().equals( "" ) )
                     {
                        searches = searches + ",";
                     }

                     // 文本框和文本域的搜索方式
                     if ( columnVO.getInputType() != null && ( columnVO.getInputType().equals( "1" ) || columnVO.getInputType().equals( "5" ) ) )
                     {
                        searches = searches + "%\"" + columnVO.getNameDB() + "\":\"%" + searchContent + "%\"%";
                     }
                     // 下拉框和单选框的搜索方式，通常多选框不支持搜索
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

   // 将搜索中自定义的字段转成数据库搜索条件
   public static Set< String > generateDefineRemarkSet( final HttpServletRequest request, final String accessAction, final String tableId ) throws KANException
   {
      try
      {
         Set< String > remarkSet = null;
         // 获取当前需要生成控件管理界面的TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
         final TableDTO subTableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByTableId( tableId );

         final ListDTO listDTO = tableDTO.getListDTO( getAccountId( request, null ), getCorpId( request, null ) );

         // 遍历Column Group
         if ( tableDTO != null && subTableDTO != null && listDTO != null && listDTO.getSearchDTO() != null && listDTO.getSearchDTO().getSearchDetailVOs() != null
               && listDTO.getSearchDTO().getSearchDetailVOs().size() > 0 )
         {
            for ( SearchDetailVO searchDetailVO : listDTO.getSearchDTO().getSearchDetailVOs() )
            {
               // 初始化字段对象
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );
               final ColumnVO subColumnVO = subTableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

               // 只提取用户自定义的Column Value
               if ( columnVO != null && columnVO.getAccountId() != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && subColumnVO != null
                     && subColumnVO.getAccountId() != null && !subColumnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
               {
                  // 初始化搜索字段的内容
                  String searchContent = request.getParameter( accessAction + "_" + columnVO.getNameDB() );
                  // 如果存在搜索值
                  if ( searchContent != null && !searchContent.trim().equals( "" ) && !searchContent.trim().equals( "0" ) )
                  {
                     if ( remarkSet == null )
                     {
                        remarkSet = new HashSet< String >();
                     }

                     // 提交内容进行解码
                     searchContent = URLDecoder.decode( searchContent, "UTF-8" );

                     // 文本框和文本域的搜索方式
                     if ( columnVO.getInputType() != null && ( columnVO.getInputType().equals( "1" ) || columnVO.getInputType().equals( "5" ) ) )
                     {
                        remarkSet.add( "%\"" + columnVO.getNameDB() + "\":\"%" + searchContent + "%\"%" );
                     }
                     // 下拉框和单选框的搜索方式，通常多选框不支持搜索
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

   // 初始化Token
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

   // 添加Token
   public static String addToken( final HttpServletRequest request ) throws KANException
   {
      return "<input type=\"hidden\" id=\"" + BaseAction.TOKEN_NAME + "\" name=\"" + BaseAction.TOKEN_NAME + "\" value=\""
            + ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) + "\" />";
   }

   // 获取Token
   public static String getToken( final HttpServletRequest request ) throws KANException
   {
      return ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );
   }

   // 验证Token
   public boolean isTokenValid( final HttpServletRequest request, final boolean flag )
   {
      try
      {
         // Request中的Token值
         final String tokenValueRequest = ( String ) request.getParameter( TOKEN_NAME );
         // Cache中的Token值
         final String tokenValueCache = ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );

         // 清楚Cache中Token
         if ( flag )
         {
            CachedUtil.delete( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );
         }

         // 判断Request和Cache中的Token是否一致
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

   // Object解码，Ajax传输中文需解码处理
   public void decodedObject( final Object object ) throws KANException
   {
      try
      {
         if ( object != null )
         {
            // 初始化Class超类
            final Class< ? > clazz = object.getClass();
            final Class< ? > clazzSuper = clazz.getSuperclass();
            // 得到Object所有Fields
            final Field[] fieldArray = KANUtil.mergeSuperclassField( clazz.getDeclaredFields(), clazzSuper.getDeclaredFields() );

            // 如果Field数组不为空，则遍历数组
            if ( fieldArray != null && fieldArray.length > 0 )
            {
               for ( Field field : fieldArray )
               {
                  // 如果是字符型字段
                  if ( field.getType().getName().trim().equalsIgnoreCase( "java.lang.String" ) )
                  {
                     final int mod = field.getModifiers();
                     if ( Modifier.isPublic( mod ) && Modifier.isStatic( mod ) && Modifier.isFinal( mod ) )
                     {
                        // 如果是静态常量则忽略后面代码
                        continue;
                     }
                     // 获取当前字段值
                     final Object stringObject = KANUtil.getValue( object, field.getName() );

                     // 如果字段值不为空
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

   // 判断当前Access Action是否需要分页
   public boolean isPaged( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // 初始化TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // 初始化ListDTO
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

   // 获取当前Access Action每页记录数
   public int getPageSize( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // 初始化TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // 初始化ListDTO
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

   // 判断当前Access Action是否搜索优先
   public boolean isSearchFirst( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // 初始化TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // 初始化ListDTO
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

   // 封装Page获取方法
   protected String getPage( final HttpServletRequest request )
   {
      return request.getParameter( "page" );
   }

   // 封装Ajax获取方法
   protected String getAjax( final HttpServletRequest request )
   {
      return request.getParameter( "ajax" );
   }

   // 封装SubAction获取方法
   protected String getSubAction( final ActionForm form ) throws KANException
   {
      final String subAction = ( String ) KANUtil.getValue( form, "subAction" );

      if ( subAction != null && !subAction.trim().equalsIgnoreCase( "null" ) )
      {
         return subAction;
      }

      return "";
   }

   // SubAction处理封装
   protected void dealSubAction( final Object object, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 如果子SubAction是删除列表操作deleteObject
      if ( getSubAction( form ).equalsIgnoreCase( DELETE_OBJECTS ) )
      {
         // 调用删除列表的SubAction
         delete_objectList( mapping, form, request, response );
      }
      // 如果SubAction为空，通常是搜索，点击排序、翻页或导出操作。Ajax提交的搜索内容需要解码。
      else
      {
         decodedObject( object );
      }
   }

   // Return处理封装
   protected ActionForward dealReturn( final String accessAction, final String forwardName, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException, IOException
   {
      // 如果是调用则返回Render生成的字节流
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
            // 初始化PrintWrite对象
            final PrintWriter out = response.getWriter();

            // Send to client
            out.println( ListRender.generateListTable( request, accessAction, new Boolean( KANUtil.filterEmpty( request.getAttribute( "useFixColumn" ) ) ) ) );
            printlnUserDefineMessageForAjaxPage( request, response );
            out.flush();
            out.close();

            return null;
         }
      }

      // 跳转到列表界面
      return mapping.findForward( forwardName );
   }

   // Added by Kevin Jin at 2013-12-06
   protected void passClientOrders( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 初始化帐套集合
      final List< MappingVO > clientOrderHeaderMappingVOs = new ArrayList< MappingVO >();

      // 初始化Service接口
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
      // 初始化ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
      clientOrderHeaderVO.setCorpId( getCorpId( request, response ) );
      clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
      clientOrderHeaderVO.setStatus( "3, 5" );

      // 获得登录客户对应的所有帐套信息
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
         // 初始化PrintWrite对象
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
    * 仅仅用于验证验证码
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
                     // 如果验证过了就不用远程调用了
                     flag = true;
                     break;
                  }
               }
               catch ( Exception e )
               {
                  System.out.println( synchroServer + "被关闭.此错误可以忽略" );
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

      // 1公开 2部门 3上级 4下属 5 私有
      for ( RuleVO ruleVO : list )
      {

         // 读取规则
         if ( StringUtils.isNotBlank( ruleVO.getRemark1() ) && ruleVO.getRemark1().contains( AuthConstants.RULE_VIEW ) )
         {

            // “公开”规则不需考虑读取

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

         // 不可见规则
         if ( StringUtils.isNotBlank( ruleVO.getRemark1() ) && ruleVO.getRemark1().contains( AuthConstants.RULE_HIDE ) )
         {

            // “公开”和“私有”规则，不需考虑不可见

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
                     // 移除当前登录人的position
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
      // dataRole(0:请选择 1:HR 2:HR管理者 3：普通员工 4：普通员工管理者)
      // ruleId (0:私有 1:下级 2:服务员工 3:部门 4:职位 5:职级 6:项目 7:法务实体 8:下级HR 9:全部)
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

      // 登录人组
      for ( GroupDTO groupDTO : loginGroupDTOList )
      {
         if ( StringUtils.equals( groupDTO.getGroupVO().getDataRole(), "2" ) )
         {
            isHRManagerFlag = true;
            break;
         }
      }

      // 如果当前登录人是hr管理者，并且下属是HR管理者或者是HR
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

      // 登录人组
      for ( GroupDTO loginGroupDTO : loginGroupDTOList )
      {
         if ( !StringUtils.equals( loginGroupDTO.getGroupVO().getDataRole(), "0" ) )
         {
            // 设置私有权限
            List< GroupModuleRuleRelationVO > loginGroupModuleRuleRelationVOList = loginGroupDTO.getGroupModuleRuleRelationVOs();
            for ( GroupModuleRuleRelationVO logicGroupModuleRuleRelationVO : loginGroupModuleRuleRelationVOList )
            {
               String ruleId = logicGroupModuleRuleRelationVO.getRuleId();

               // 全部
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

               // 私有(默认值)
               if ( "0".equals( ruleId ) )
               {
                  String rulePrivateID = getEmployeeId( request, response );
                  if ( StringUtils.isNotBlank( rulePrivateID ) )
                  {
                     rulePrivateIds.add( rulePrivateID );
                  }
               }

               // 下级
               if ( "1".equals( ruleId ) )
               {
                  // 设置下级权限(查找下级postion对应的employee)
                  rulePrivateIds.addAll( kanAccountConstants.getEmployeeIdListByPositionId( childrenPosition ) );
               }

               // 服务员工
               if ( "2".equals( ruleId ) )
               {
                  rulePositionIds.add( userVO.getPositionId() );
               }

               // 部门，职位，职级
               if ( "3".equals( ruleId ) || "4".equals( ruleId ) || "5".equals( ruleId ) )
               {
                  List< String > tempPositionList = new ArrayList< String >();

                  if ( "3".equals( ruleId ) )
                  {
                     String[] branchIds = KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() );
                     tempPositionList.addAll( kanAccountConstants.getPositionIdListByBranchId( Arrays.asList( branchIds ), baseVO.getCorpId() ) );
                     ruleBranchIds.addAll( KANUtil.jasonArrayToStringList( logicGroupModuleRuleRelationVO.getRemark1() ) );
                  }

                  // 职位
                  if ( "4".equals( ruleId ) )
                  {
                     tempPositionList.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() ) ) );
                  }

                  // 职级
                  if ( "5".equals( ruleId ) )
                  {
                     String[] positionGradeIds = KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() );
                     tempPositionList.addAll( kanAccountConstants.getPositionIdListByPositionGradeId( Arrays.asList( positionGradeIds ), baseVO.getCorpId() ) );
                  }
                  rulePrivateIds.addAll( kanAccountConstants.getEmployeeIdListByPositionId( tempPositionList ) );
               }

               // 项目
               if ( "6".equals( ruleId ) )
               {
                  ruleBusinessTypeIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() ) ) );
               }

               // 法务实体
               if ( "7".equals( ruleId ) )
               {
                  ruleRuleEntityIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( logicGroupModuleRuleRelationVO.getRemark1() ) ) );
               }

               // 下级HR
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

                           // 全部
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

                           // 服务员工
                           if ( "2".equals( hrRuleId ) )
                           {
                              rulePositionIds.add( hrPosition );
                           }

                           // 部门，职位，职级
                           if ( "3".equals( hrRuleId ) || "4".equals( hrRuleId ) || "5".equals( hrRuleId ) )
                           {
                              List< String > tempHRPositionList = new ArrayList< String >();

                              // 部门
                              if ( "3".equals( hrRuleId ) )
                              {
                                 String[] branchIds = KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() );
                                 tempHRPositionList.addAll( kanAccountConstants.getPositionIdListByBranchId( Arrays.asList( branchIds ), baseVO.getCorpId() ) );
                              }

                              // 职位
                              if ( "4".equals( hrRuleId ) )
                              {
                                 tempHRPositionList.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() ) ) );
                              }
                              // 职级
                              if ( "5".equals( hrRuleId ) )
                              {
                                 String[] positionGradeIds = KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() );
                                 tempHRPositionList.addAll( kanAccountConstants.getPositionIdListByPositionGradeId( Arrays.asList( positionGradeIds ), baseVO.getCorpId() ) );
                              }

                              rulePrivateIds.addAll( kanAccountConstants.getEmployeeIdListByPositionId( tempHRPositionList ) );
                           }

                           // 项目
                           if ( "6".equals( hrRuleId ) )
                           {
                              ruleBusinessTypeIds.addAll( Arrays.asList( KANUtil.jasonArrayToStringArray( hrGroupModuleRuleRelationVO.getRemark1() ) ) );
                           }

                           // 法务实体
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
    * 记录操作日志
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
         // 防止json转换没有初始数据而出错
         if ( object != null && object instanceof ActionForm )
         {
            ( ( ActionForm ) object ).reset( null, request );
         }

         // 如果是员工基本信息
         if ( object != null && object instanceof EmployeeVO )
         {
            log.setEmployeeId( ( ( EmployeeVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeeVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeeVO ) object ).getNameZH() );
            log.setEmployeeNameEN( ( ( EmployeeVO ) object ).getNameEN() );
         }
         // 如果是员工劳动合同
         else if ( object != null && object instanceof EmployeeContractVO )
         {
            log.setEmployeeId( ( ( EmployeeContractVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeeContractVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeeContractVO ) object ).getEmployeeNameZH() );
            log.setEmployeeNameEN( ( ( EmployeeContractVO ) object ).getEmployeeNameEN() );
         }
         // 如果是异动/升迁
         else if ( object != null && object instanceof EmployeePositionChangeVO )
         {
            log.setEmployeeId( ( ( EmployeePositionChangeVO ) object ).getEmployeeId() );
            log.setChangeReason( ( ( EmployeePositionChangeVO ) object ).getRemark3() );
            log.setEmployeeNameZH( ( ( EmployeePositionChangeVO ) object ).getEmployeeNameZH() );
            log.setEmployeeNameEN( ( ( EmployeePositionChangeVO ) object ).getEmployeeNameEN() );
         }
         // 如果是异动/升迁
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
    * 0 表示同步成功.-1表示失败
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
         System.out.println( "record log error : 同步微信通讯录失败;非重要错误" );
         e.printStackTrace();
         return -1;
      }
   }

   /**
    * 同步微信
    */
   public static int syncWXContacts( final String employeeId, final boolean isDeleted )
   {
      int result = 0;
      // 如果是debug就不同步。
      if ( KANConstants.ISDEBUG )
      {
         System.out.println( "调试模式不同步微信" );
         return result;
      }
      try
      {

         EmployeeVO employeeVO = null;
         // 仅用于吴友平
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
            // 如果是 other payroll就跳过

            JSONObject tmpRemark1 = KANUtil.toJSONObject( employeeContractVO.getRemark1() );

            guyuanleixinger = tmpRemark1.getString( "guyuanleixinger" );
         }
         // 如果是吴友平
         if ( employeeVO2 != null )
         {
            final List< Object > objects2 = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeid2 );
            if ( objects2 != null && objects2.size() > 0 )
            {
               employeeContractVO2 = ( EmployeeContractVO ) objects2.get( 0 );
            }
         }
         // 如果是吴友平
         if ( employeeVO2 != null )
         {
            dealWX4Ricky( employeeVO, employeeVO2, employeeContractVO, employeeContractVO2 );
         }
         int errcode = -1;
         int operid = 0;
         String wxmsg = "";
         final String[] opers = new String[] { "", "add", "update", "delete" };
         // 如果是删除. 或者是员工状态不是在职的或者是other on payroll
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
               // errcodeFind 查询结果
               int findCode = jsonObject.getInt( "errcode" );
               if ( KANUtil.filterEmpty( findCode ) != null )
               {
                  if ( findCode == 0 )
                  {
                     // 找到了这个员工 修改
                     WXContactVO wxContactVO = new WXContactVO();
                     wxContactVO.resetWXContactVO( employeeVO, employeeContractVO );
                     final JSONObject updateReturnJson = WXUtil.updateEmployee( wxContactVO );
                     errcode = updateReturnJson.getInt( "errcode" );
                     wxmsg = updateReturnJson.getString( "errmsg" );
                     operid = 2;
                  }
                  // 60111 没找到
                  else if ( findCode == 60111 )
                  {
                     // 没找到. 新增
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
            System.out.println( "微信同步结果:" + ( errcode == 0 ? "success" : "error" ) + ";[" + wxmsg + "],操作类型:" + opers[ operid ] + ",employeeId=" + employeeVO.getEmployeeId() );
            result = ( errcode == 0 ? 0 : -1 );
         }

      }
      catch ( Exception e )
      {
         e.printStackTrace();
         System.out.println( "record log error : 同步微信通讯录失败;非重要错误" );
         return -1;
      }
      return result;
   }

   /**
    * 同步微信不删除
    */
   public static int syncWXContacts_not_delete( final String employeeId, final boolean isDeleted )
   {
      int result = 0;
      // 如果是debug就不同步。
      if ( KANConstants.ISDEBUG )
      {
         System.out.println( "调试模式不同步微信" );
         return result;
      }
      try
      {

         EmployeeVO employeeVO = null;
         // 仅用于吴友平
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
            // 如果是 other payroll就跳过
         }
         // 如果是吴友平
         if ( employeeVO2 != null )
         {
            final List< Object > objects2 = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeid2 );
            if ( objects2 != null && objects2.size() > 0 )
            {
               employeeContractVO2 = ( EmployeeContractVO ) objects2.get( 0 );
            }
         }
         // 如果是吴友平
         if ( employeeVO2 != null )
         {
            dealWX4Ricky( employeeVO, employeeVO2, employeeContractVO, employeeContractVO2 );
         }
         int errcode = -1;
         int operid = 0;
         String wxmsg = "";
         final String[] opers = new String[] { "", "add", "update", "delete" };
         // 如果是删除. 或者是员工状态不是在职的或者是other on payroll
         if ( ( employeeVO != null && isDeleted ) || !"1".equals( employeeVO.getStatus() ) )
         {
            errcode = 2;
            wxmsg = "同步所有不做删除操作";
            operid = 3;
         }
         else if ( employeeVO != null && !isDeleted )
         {
            final JSONObject jsonObject = WXUtil.getEmployee( employeeVO );
            if ( KANUtil.filterEmpty( jsonObject ) != null )
            {
               // errcodeFind 查询结果
               int findCode = jsonObject.getInt( "errcode" );
               if ( KANUtil.filterEmpty( findCode ) != null )
               {
                  if ( findCode == 0 )
                  {
                     // 找到了这个员工 修改
                     WXContactVO wxContactVO = new WXContactVO();
                     wxContactVO.resetWXContactVO( employeeVO, employeeContractVO );
                     final JSONObject updateReturnJson = WXUtil.updateEmployee( wxContactVO );
                     errcode = updateReturnJson.getInt( "errcode" );
                     wxmsg = updateReturnJson.getString( "errmsg" );
                     operid = 2;
                  }
                  // 60111 没找到
                  else if ( findCode == 60111 )
                  {
                     // 没找到. 新增
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
            System.out.println( "微信同步结果:" + ( errcode == 0 ? "success" : "error" ) + ";[" + wxmsg + "],操作类型:" + opers[ operid ] + ",employeeId=" + employeeVO.getEmployeeId() );
            result = ( errcode == 0 ? 0 : -1 );
         }

      }
      catch ( Exception e )
      {
         e.printStackTrace();
         System.out.println( "record log error : 同步微信通讯录失败;非重要错误" );
         return -1;
      }
      return result;
   }

   /**
    * 微信同步.如果是吴友平
    * @param employeeVO
    * @param employeeVO2
    * @param employeeContractVO
    * @param employeeContractVO2
    */
   private static void dealWX4Ricky( EmployeeVO employeeVO, EmployeeVO employeeVO2, EmployeeContractVO employeeContractVO, EmployeeContractVO employeeContractVO2 )
   {
      //dept,location,title,bufunction
      // 对应部门
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
         //1的办公地点
         final JSONObject jsonRemark = KANUtil.toJSONObject( employeeVO.getRemark1() );
         String bangongdidian1 = jsonRemark.getString( "bangongdidian" );
         //2的办公地点
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
         //1的title
         final JSONObject jsonRemark = KANUtil.toJSONObject( employeeContractVO.getRemark1() );
         String zhiweimingchengyingwen1 = jsonRemark.getString( "zhiweimingchengyingwen" );
         //2的title
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