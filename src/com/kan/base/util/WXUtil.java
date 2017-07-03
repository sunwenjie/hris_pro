package com.kan.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kan.base.domain.security.WXContactVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class WXUtil
{
   private static Logger log = LoggerFactory.getLogger( WXUtil.class );

   public final static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=APPID&corpsecret=APPSECRET";

   public static String APPID = "wx059471abeb063951";

   public static String APPSECRET = "NV0GjryO7z1yVZuM3ux-qYETBOrmYGvpiEaFdBYMTUOnr0pyo8U0k9LBZqqH37tg";

   public static String SCOPE = "snsapi_userinfo";

   /**
    * ����https���󲢻�ȡ���
    * 
    * @param requestUrl �����ַ
    * @param requestMethod ����ʽ��GET��POST��
    * @param outputStr �ύ������
    * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ)
    */
   public static JSONObject httpRequest( String requestUrl, String requestMethod, String outputStr )
   {
      JSONObject jsonObject = null;
      StringBuffer buffer = new StringBuffer();
      try
      {
         // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
         TrustManager[] tm = { new MyX509TrustManager() };
         SSLContext sslContext = SSLContext.getInstance( "SSL", "SunJSSE" );
         sslContext.init( null, tm, new java.security.SecureRandom() );
         // ������SSLContext�����еõ�SSLSocketFactory����
         SSLSocketFactory ssf = sslContext.getSocketFactory();

         URL url = new URL( requestUrl );
         HttpsURLConnection httpUrlConn = ( HttpsURLConnection ) url.openConnection();
         httpUrlConn.setSSLSocketFactory( ssf );

         httpUrlConn.setDoOutput( true );
         httpUrlConn.setDoInput( true );
         httpUrlConn.setUseCaches( false );
         // ��������ʽ��GET/POST��
         httpUrlConn.setRequestMethod( requestMethod );

         if ( "GET".equalsIgnoreCase( requestMethod ) )
            httpUrlConn.connect();

         // ����������Ҫ�ύʱ
         if ( null != outputStr )
         {
            OutputStream outputStream = httpUrlConn.getOutputStream();
            // ע������ʽ����ֹ��������
            outputStream.write( outputStr.getBytes( "UTF-8" ) );
            outputStream.close();
         }

         // �����ص�������ת�����ַ���
         InputStream inputStream = httpUrlConn.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader( inputStream, "utf-8" );
         BufferedReader bufferedReader = new BufferedReader( inputStreamReader );

         String str = null;
         while ( ( str = bufferedReader.readLine() ) != null )
         {
            buffer.append( str );
         }
         bufferedReader.close();
         inputStreamReader.close();
         // �ͷ���Դ
         inputStream.close();
         inputStream = null;
         httpUrlConn.disconnect();
         jsonObject = JSONObject.fromObject( buffer.toString() );
      }
      catch ( ConnectException ce )
      {
         log.error( "Weixin server connection timed out." );
      }
      catch ( Exception e )
      {
         log.error( "https request error:{}", e );
      }
      return jsonObject;
   }

   public static AccessToken getAccessToken()
   {
      return getAccessToken( APPID, APPSECRET );
   }

   /** 
    * ��ȡaccess_token 
    *  
    * @param appid ƾ֤ 
    * @param appsecret ��Կ 
    * @return 
    */
   public static AccessToken getAccessToken( String appid, String appsecret )
   {
      AccessToken accessToken = null;

      String requestUrl = access_token_url.replace( "APPID", appid ).replace( "APPSECRET", appsecret );
      JSONObject jsonObject = httpRequest( requestUrl, "GET", null );
      // �������ɹ�  
      if ( null != jsonObject )
      {
         try
         {
            accessToken = new AccessToken();
            accessToken.setToken( jsonObject.getString( "access_token" ) );
            accessToken.setExpiresIn( jsonObject.getInt( "expires_in" ) );
         }
         catch ( JSONException e )
         {
            accessToken = null;
            // ��ȡtokenʧ��  
            log.error( "��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt( "errcode" ), jsonObject.getString( "errmsg" ) );
         }
      }
      return accessToken;
   }


   public static JSONObject getEmployee( EmployeeVO employeeVO )
   {
      String userId = "-1";
      if ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getEmail1() ) != null )
      {
         userId = employeeVO.getEmail1().split( "@" )[ 0 ];
      }
      final String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + WXUtil.getAccessToken().getToken() + "&userid=" + userId;
      return WXUtil.httpRequest( requestUrl, "GET", null );
   }

   public static JSONObject updateEmployee( WXContactVO wxContactVO )
   {
      final String updateUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=" + WXUtil.getAccessToken().getToken();
      return WXUtil.httpRequest( updateUrl, "POST", wxContactVO.toString() );
   }

   public static JSONObject insertEmployee( WXContactVO wxContactVO )
   {
      final String insertUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=" + WXUtil.getAccessToken().getToken();
      JSONObject resultJson = WXUtil.httpRequest( insertUrl, "POST", wxContactVO.toString() );
      int errcode = resultJson.getInt( "errcode" );
      if ( errcode == 0 )
      {
         // �����ӳɹ�,�ͷ�������
         JSONObject jsonObject = interview( wxContactVO.getUserid() );
         int interviewResult = jsonObject.getInt( "errcode" );
         if ( interviewResult == 0 )
         {
            System.err.println( "����ɹ�" );
         }
         else
         {
            System.err.println( "����ʧ��" );
         }
      }
      return resultJson;
   }

   public static JSONObject interview( String userid )
   {
      final String insertUrl = "https://qyapi.weixin.qq.com/cgi-bin/invite/send?access_token=" + WXUtil.getAccessToken().getToken();
      return WXUtil.httpRequest( insertUrl, "POST", "{\"userid\":\"" + userid + "\"}" );
   }

   public static JSONObject deleteEmployee( EmployeeVO employeeVO )
   {
      String userId = "-1";
      if ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getEmail1() ) != null )
      {
         userId = employeeVO.getEmail1().split( "@" )[ 0 ].toLowerCase().replaceAll( " ", "" );
      }
      final String deleteUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=" + WXUtil.getAccessToken().getToken() + "&userid=" + userId;
      return WXUtil.httpRequest( deleteUrl, "GET", null );
   }

   public static List< WXContactVO > getAllWXContact()
   {
      List< WXContactVO > wxContactVOs = new ArrayList< WXContactVO >();
      String getAllUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
      getAllUrl = getAllUrl.replaceAll( "ACCESS_TOKEN", getAccessToken().getToken() );
      //1�Ǹ�Ŀ¼
      getAllUrl = getAllUrl.replaceAll( "DEPARTMENT_ID", "1" );
      //1/0���Ƿ�ݹ��ȡ�Ӳ�������ĳ�Ա
      getAllUrl = getAllUrl.replaceAll( "FETCH_CHILD", "1" );
      //0��ȡȫ����Ա��1��ȡ�ѹ�ע��Ա�б�2��ȡ���ó�Ա�б�4��ȡδ��ע��Ա�б�status�ɵ���
      getAllUrl = getAllUrl.replaceAll( "STATUS", "0" );
      JSONObject jsonObject = WXUtil.httpRequest( getAllUrl, "GET", null );
      String errcode = jsonObject.getString( "errcode" );
      if ( "0".equals( errcode ) )
      {
         JSONArray jsonArray = ( JSONArray ) jsonObject.get( "userlist" );
         for ( int i = 0; i < jsonArray.size(); i++ )
         {
            JSONObject wxObject = jsonArray.getJSONObject( i );
            WXContactVO wxContactVO = new WXContactVO();
            wxContactVO.jsonObject2VO( wxObject );
            wxContactVOs.add( wxContactVO );
         }
      }
      return wxContactVOs;
   }

   public static void main( String[] args )
   {
      List<WXContactVO> wxContactVOs = WXUtil.getAllWXContact();
      if ( wxContactVOs.size() > 0 )
      {
         for ( WXContactVO wxContactVO : wxContactVOs )
         {
            System.out.print( wxContactVO.getUserid() + "#" );
            System.out.print( wxContactVO.getAttrs().getNameCN() + "#" );
            System.out.print( wxContactVO.getAttrs().getNameEN() + "#" );
            System.out.print( wxContactVO.getAttrs().getDept() + "#" );
            System.out.print( wxContactVO.getAttrs().getLocation() + "#" );
            System.out.print( wxContactVO.getAttrs().getTitle() + "#" );
            System.out.print( wxContactVO.getAttrs().getMail() + "#" );
            System.out.print( ( " " + wxContactVO.getAttrs().getPhone() + "#" ).replaceAll( "\r\n", "" ) );
            System.out.print( getDepartment( wxContactVO.getDepartment() ) );
            System.out.println();
         }
      }
   }
   
   public static String getDepartment(int[] department)
   {
     String value = "";
     if (KANUtil.filterEmpty(department) != null)
     {
       for (int i = 0; i < department.length; i++)
       {
         if (i > 0)
         {
           value = value + ",";
         }
         value = value + department[i];
       }
     }
     return value;
   }
   
   //��ȡJSAPI_Ticket   
   public static String jsapi_ticket_url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN&type=jsapi";
   /**
    * ��ȡjsapi_ticket 
    * @return
    */
   public static String JSApiTIcket(){
     int result = 0;  
     String jsApiTicket = null;  
     //ƴװ�����˵�Url  
     String url =  jsapi_ticket_url.replace("ACCESS_TOKEN", getAccessToken().getToken());  
     //���ýӿڻ�ȡjsapi_ticket  
     JSONObject jsonObject = httpRequest(url, "GET", null);  
     // �������ɹ�    
     if (null != jsonObject) {    
         try {    
             jsApiTicket = jsonObject.getString("ticket");  
         } catch (JSONException e) {    
              if (0 != jsonObject.getInt("errcode")) {    
                 result = jsonObject.getInt("errcode");    
                 log.error("JSAPI_Ticket��ȡʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));    
             }    
         }    
     }    
     return jsApiTicket; 
   }
   
   /** 
    * sha1���� 
    * @param str 
    * @return 
    */  
   public static String sha1Encrypt(String str){    
       String signature = null;  
       try  
       {  
           MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
           crypt.reset();  
           crypt.update(str.getBytes("UTF-8"));  
           signature = byteToHex(crypt.digest());  
       }  
       catch (NoSuchAlgorithmException e)  
       {  
           e.printStackTrace();  
       }  
       catch (UnsupportedEncodingException e)  
       {  
           e.printStackTrace();  
       }  
       return signature;  
   }  
   
   private static String byteToHex(final byte[] hash) {  
     Formatter formatter = new Formatter();  
     for (byte b : hash)  
     {  
         formatter.format("%02x", b);  
     }  
     String result = formatter.toString();  
     formatter.close();  
     return result;  
 }  
}
