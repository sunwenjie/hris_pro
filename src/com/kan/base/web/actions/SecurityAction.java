/*
 * Created on 2007-1-11
 */
package com.kan.base.web.actions;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.domain.security.*;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.service.inf.message.MessageMailService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.util.*;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.system.AccountAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.web.actions.biz.dashboard.DashboardAction;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Jin
 */
public class SecurityAction extends BaseAction
{

   /**  
    * Logon
    *	HRO��¼
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-15
   public ActionForward logon( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service
         final UserService userService = ( UserService ) getService( "userService" );
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         final StaffService staffService = ( StaffService ) getService( "staffService" );

         // ��ʼ��Form
         UserVO userVO = ( UserVO ) form;

         //ֱ������accountId��¼
         if ( userVO.getAccountName().trim() != null && userVO.getAccountName().trim() != "" && checkNumeric( userVO.getAccountName().trim() ) )
         {
            AccountVO tempAccountVO = accountService.getAccountVOByAccountId( userVO.getAccountName().trim() );
            userVO.setAccountName( tempAccountVO.getNameCN() );
            userVO.setAccountId( tempAccountVO.getAccountId() );
         }
         else if ( userVO.getAccountId() == null || userVO.getAccountId().equals( "" ) )
         {
            AccountVO accountVO = accountService.getAccountVOByAccountName( userVO.getAccountName().trim() );
            if ( accountVO != null )
            {
               userVO.setAccountId( accountVO.getAccountId() );
               userVO.setAccountName( accountVO.getNameCN() );
            }
         }

         if ( userVO.getUsername() != null && userVO.getUsername() != "" )
         {
            StaffVO staffVO = new StaffVO();
            staffVO.setBizMobile( userVO.getUsername().trim() );
            staffVO.setBizEmail( userVO.getUsername().trim() );
            staffVO.setBizPhone( userVO.getUsername().trim() );
            staffVO.setPersonalEmail( userVO.getUsername().trim() );
            staffVO.setPersonalMobile( userVO.getUsername().trim() );
            staffVO.setPersonalPhone( userVO.getUsername().trim() );
            staffVO.setOtherPhone( userVO.getUsername().trim() );
            List< Object > staffList = staffService.logon( staffVO );
            if ( staffList.size() == 1 )
            {
               StaffVO temp = ( StaffVO ) staffList.get( 0 );
               userVO.setAccountId( temp.getAccountId() );
               userVO.setUsername( temp.getNameZH() );
            }
         }
         final UserVO loginUserVO = userService.login( userVO );

         // ��ʼ��ActionErrors
         final ActionErrors errors = new ActionErrors();
         // ��ʼ����¼�Ƿ����
         Boolean failedFlag = false;

         // �ж��˻������Ƿ���Ч
         if ( !userVO.getUsername().equalsIgnoreCase( "super" ) )
         {
            // ��ʼ����¼AccountVO
            final AccountVO tempAccountVO = accountService.getAccountVOByAccountId( userVO.getAccountId() );

            if ( tempAccountVO == null )
            {
               errors.add( "LoginAccountError", new ActionMessage( "error.security.account" ) );
               failedFlag = true;
               addErrors( request, errors );
               ( ( UserVO ) form ).reset();
               ( ( UserVO ) form ).setPassword( "" );

               return mapping.findForward( "logon" );
            }
         }

         // �ж��û����Ƿ���Ч
         if ( loginUserVO == null )
         {
            errors.add( "LoginUsernameError", new ActionMessage( "error.security.username" ) );
            failedFlag = true;
         }
         else
         {
            // �ж��û��������Ƿ���Ч
            if ( !loginUserVO.getEncryptPassword().equals( encryptPassword( loginUserVO.getSalt(), userVO.getPassword() ) ) )
            {
               errors.add( "LoginPasswordError", new ActionMessage( "error.security.password" ) );
               failedFlag = true;
            }

            if ( KANUtil.filterEmpty( loginUserVO.getAccountId() ) != null && !KANUtil.filterEmpty( loginUserVO.getAccountId() ).equals( "1" )
                  && !KANUtil.filterEmpty( loginUserVO.getAccountId() ).equals( userVO.getAccountId() ) )
            {
               errors.add( "LoginAccountError", new ActionMessage( "error.security.account" ) );
               failedFlag = true;
            }

            // �жϵ�¼IP�Ƿ���Ч
            if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
               failedFlag = true;
            }
         }

         // �����¼������ת����¼ҳ��
         if ( failedFlag )
         {
            addErrors( request, errors );
            ( ( UserVO ) form ).reset();
            ( ( UserVO ) form ).setPassword( "" );

            return mapping.findForward( "logon" );
         }
         else if ( loginUserVO != null )
         {
            // ��ʼ����¼���󣨹��ʻ���
            loginUserVO.reset( mapping, request );
         }

         // ����û���Ӧ��Account��Ϣ
         final AccountVO accountVO = accountService.getAccountVOByAccountId( loginUserVO.getAccountId() );
         // �����û���˾����
         loginUserVO.setEntityName( accountVO != null ? accountVO.getEntityName() : "" );
         loginUserVO.setAccountName( accountVO.getNameCN() );
         // �Ƿ��Զ���¼
         loginUserVO.setCbPersistentCookie( userVO.getCbPersistentCookie() );
         // �����û���¼���к�
         loginUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // �����û���ɫ
         loginUserVO.setRole( KANConstants.ROLE_HR_SERVICE );
         // ���������¼ʱ��
         loginUserVO.setLastLogin( new Date() );
         // ���������¼IP��ַ
         loginUserVO.setLastLoginIP( getIPAddress( request ) );
         // ����ϵͳID
         loginUserVO.setSystemId( KANConstants.SYS_HR_SERVICE );
         loginUserVO.setModifyDate( new Date() );
         userService.updateUser( loginUserVO );

         // ����Request Attribute
         request.setAttribute( "accountId", loginUserVO.getAccountId() );
         request.setAttribute( "userId", loginUserVO.getUserId() );
         request.setAttribute( "staffId", loginUserVO.getStaffId() );
         request.setAttribute( "username", loginUserVO.getUsername() );
         request.setAttribute( "entityName", loginUserVO.getEntityName() );
         request.setAttribute( "userToken", loginUserVO.getUserToken() );
         request.setAttribute( "role", KANConstants.ROLE_HR_SERVICE );
         request.setAttribute( "systemId", KANConstants.SYS_HR_SERVICE );

         //���cookie
         removeObjectFromClient( request, response, COOKIE_EMPLOYEE_USER );
         removeObjectFromClient( request, response, COOKIE_USER );
         // �����ƽ̨�˻���¼��Super��
         if ( KANUtil.filterEmpty( loginUserVO.getAccountId() ).equalsIgnoreCase( "1" ) )
         {
            // ��UserVO����д��ͻ�Cookie
            this.saveUserToClient( response, request, loginUserVO );
            this.saveUserToCookie( response, request, userVO, AUTO_LOGON_USER );
            return new AccountAction().list_object( mapping, new AccountVO(), request, response );
         }
         // �����һ���û���¼
         else
         {
            // ��ȡPositions
            final List< MappingVO > positions = getPositionsByStaffId( request.getLocale(), loginUserVO.getAccountId(), loginUserVO.getStaffId() );

            // ���û��Position
            if ( positions.size() == 0 )
            {
               errors.add( "LoginPositionError", new ActionMessage( "error.security.position" ) );
               // ������ʾ
               addErrors( request, errors );
               ( ( UserVO ) form ).reset();
               ( ( UserVO ) form ).setPassword( "" );

               return mapping.findForward( "logon" );
            }
            // ���ֻ��һ��Position
            else if ( positions.size() == 1 )
            {
               loginUserVO.setPositionId( positions.get( 0 ).getMappingId() );
               request.setAttribute( "positionId", loginUserVO.getPositionId() );
               request.setAttribute( "positionName", loginUserVO.getPositionName() );
            }

            // Position����
            request.setAttribute( "positions", positions );
            loginUserVO.setPositions( positions );

            // ��UserVO����д��ͻ�Cookie
            this.saveUserToClient( response, request, loginUserVO );
            this.saveUserToCookie( response, request, userVO, AUTO_LOGON_USER );
            ( ( UserVO ) form ).reset();

            // ����ж��Position
            if ( positions.size() > 1 )
            {
               return mapping.findForward( "choosePositionPage" );
            }
            else
            {
               return new DashboardAction().showDashboard( mapping, form, request, response );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   /**  
    * Logon InHouse
    *	HRM��¼
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-12-09
   // Reviewed by Kevin Jin at 2014-04-15
   public ActionForward logon_inHouse( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // ��ʼ��Form
      UserVO userVO = ( UserVO ) form;
      // ��ʼ��ActionErrors
      final ActionErrors errors = new ActionErrors();

      //��֤�Ƿ������
      if ( KANConstants.checkUserInBlackList( userVO.getUsername() ) )
      {
         errors.add( "UserInBlackList", new ActionMessage( "error.security.in.black.list" ) );
         addErrors( request, errors );
         return mapping.findForward( "logon_inHouse" );
      }

      // ��֤��֤��
      //��ȡ��ǰ�û���session ID  
      //      String sessionId = request.getSession().getId();
      String sessionId = request.getParameter( "token" );
      //��ȡ�û��ύ����֤��  
      String captcha = request.getParameter( "imageValidate" );
      boolean flag = false;
      //������֤
      try
      {
         flag = BaseAction.validCaptcha( new String[] { sessionId, captcha } );
         //         flag = CaptchaServiceSingleton.getInstance().validateResponseForID( sessionId, captcha );
      }
      catch ( Exception e )
      {
         errors.add( "VerCodeError", new ActionMessage( "error.security.ver.code.app.error" ) );
         return mapping.findForward( "logon_inHouse" );
      }
      if ( !flag )
      {
         errors.add( "VerCodeError", new ActionMessage( "error.security.ver.code" ) );
         addErrors( request, errors );
         return mapping.findForward( "logon_inHouse" );
      }

      try
      {
         // ��ʼ��Service
         final UserService userService = ( UserService ) getService( "userService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final StaffService staffService = ( StaffService ) getService( "staffService" );

         //ֱ������corpId��¼
         ClientVO clientVO = clientService.getClientVOByCorpId( userVO.getCorpId() );
         userVO.setClientName( clientVO.getNameZH() );
         userVO.setCorpId( clientVO.getCorpId() );
         userVO.setAccountId( clientVO.getAccountId() );

         if ( KANUtil.filterEmpty( userVO.getUsername() ) != null )
         {
            StaffVO staffVO = new StaffVO();
            staffVO.setBizMobile( userVO.getUsername().trim() );
            staffVO.setBizEmail( userVO.getUsername().trim() );
            staffVO.setBizPhone( userVO.getUsername().trim() );
            staffVO.setPersonalEmail( userVO.getUsername().trim() );
            staffVO.setPersonalMobile( userVO.getUsername().trim() );
            staffVO.setPersonalPhone( userVO.getUsername().trim() );
            staffVO.setOtherPhone( userVO.getUsername().trim() );
            staffVO.setAccountId( userVO.getAccountId() );
            List< Object > staffList = staffService.logon( staffVO );
            if ( staffList.size() == 1 )
            {
               StaffVO temp = ( StaffVO ) staffList.get( 0 );
               final UserVO tempUserVO = userService.getUserVOByStaffId( temp.getStaffId() );
               if ( tempUserVO != null )
               {
                  userVO.setUsername( tempUserVO.getUsername() );
                  userVO.setCorpId( temp.getCorpId() );
               }
            }
         }

         // ��ʼ����¼�Ƿ����
         Boolean failedFlag = false;

         // ��¼����
         final UserVO loginUserVO = userService.login_inHouse( userVO );

         // �ж��û����Ƿ���Ч
         if ( loginUserVO == null )
         {
            errors.add( "LoginUsernameError", new ActionMessage( "error.security.username" ) );
            failedFlag = true;
         }
         else
         {
            // �ж��û��������Ƿ���Ч
            if ( !loginUserVO.getEncryptPassword().equals( encryptPassword( loginUserVO.getSalt(), userVO.getPassword() ) ) )
            {
               errors.add( "LoginPasswordError", new ActionMessage( "error.security.password" ) );
               failedFlag = true;
               // ͬ��
               BaseAction.constantsInit( "addErrorCount", userVO.getUsername() );
            }

            // �жϵ�¼IP�Ƿ���Ч
            if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
               failedFlag = true;
            }
         }

         // ������û�����Ч��ת����¼ҳ��
         if ( failedFlag )
         {
            addErrors( request, errors );
            ( ( UserVO ) form ).reset();
            ( ( UserVO ) form ).setPassword( "" );

            return mapping.findForward( "logon_inHouse" );
         }
         else if ( loginUserVO != null )
         {
           // ���ز˵�
           KANAccountConstants accountConstants = KANConstants.getKANAccountConstants(loginUserVO.getAccountId());
           StaffDTO staffDTO = accountConstants.getStaffDTOByUserId(loginUserVO.getUserId());
           // û�в˵�. ��Ҫ����
           if(staffDTO!=null && staffDTO.getModuleDTOs().size()==0){
             constantsInit( "initStaff", new String[] { loginUserVO.getAccountId(), loginUserVO.getStaffId() } );
           }
            // ��ʼ����¼���󣨹��ʻ���
            loginUserVO.reset( mapping, request );
         }

         // �����������
         //         if ( !strongPwd( userVO.getPassword() ) )
         //         {
         //            // �����������������޸�����
         //            this.saveUserToClient( response, request, loginUserVO );
         //            this.saveUserToCookie( response, request, userVO, AUTO_LOGONI_USER );
         //            ( ( UserVO ) form ).reset();
         //            return to_passwordNew( mapping, form, request, response );
         //         }

         Boolean isHr = false;//�Ƿ���HR
         String ip = getIPAddress( request );

         Integer ipType = getIpType( ip );

         // ��ȡPositions
         final List< MappingVO > positions = getPositionsByStaffId( request.getLocale(), loginUserVO.getAccountId(), loginUserVO.getStaffId() );

         //mac�µı���IP����۵������̶�����IP��ֱ��ͨ��
         if ( "0:0:0:0:0:0:0:1".equals( ip ) || "118.140.66.102".equals( ip ) || "223.197.145.129".equals( ip ) )
         {
            ipType = 1;
         }

         if ( ipType == 2 )
         {
            if ( positions != null && positions.size() > 0 )
            {
               lable: for ( MappingVO mappingVO : positions )
               {
                  List< GroupDTO > GroupDTOs = KANConstants.getKANAccountConstants( loginUserVO.getAccountId() ).getGroupDTOsByPositionId( mappingVO.getMappingId() );
                  if ( GroupDTOs != null && GroupDTOs.size() > 0 )
                  {
                     for ( GroupDTO groupDTO : GroupDTOs )
                     {
                        if ( groupDTO.getGroupVO() != null )
                        {
                           String dataRole = groupDTO.getGroupVO().getDataRole();
                           //��HR��hr������  1hr 2hrmanager
                           if ( "1".equals( dataRole ) || "2".equals( dataRole ) )
                           {
                              isHr = true;
                              break lable;
                           }

                        }
                     }
                  }

               }
            }
         }

         //HR��ֹ������¼
         if ( isHr )
         {
            errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
            addErrors( request, errors );
            return mapping.findForward( "logon_inHouse" );
         }

         // ���õ�ǰϵͳ
         clientVO.setSystemId( KANConstants.SYS_IN_HOUSE );
         clientVO.reset( mapping, request );
         // ���ÿͻ���˾����
         loginUserVO.setClientName( clientVO.getClientName() );
         // ����ClientId
         loginUserVO.setClientId( clientVO.getClientId() );
         // ����CorpId
         loginUserVO.setCorpId( clientVO.getCorpId() );
         // �����û���¼���к�
         loginUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // �����û���ɫ
         loginUserVO.setRole( KANConstants.ROLE_IN_HOUSE );
         // ���������¼ʱ��
         loginUserVO.setLastLogin( new Date() );
         // ���������¼IP��ַ
         loginUserVO.setLastLoginIP( getIPAddress( request ) );
         // ����ϵͳID
         loginUserVO.setSystemId( KANConstants.SYS_IN_HOUSE );
         loginUserVO.setModifyDate( new Date() );
         userService.updateUser( loginUserVO );

         // ����Request Attribute
         request.setAttribute( "accountId", loginUserVO.getAccountId() );
         request.setAttribute( "role", KANConstants.ROLE_IN_HOUSE );
         request.setAttribute( "clientId", loginUserVO.getClientId() );
         request.setAttribute( "corpId", loginUserVO.getCorpId() );
         request.setAttribute( "userId", loginUserVO.getUserId() );
         request.setAttribute( "staffId", loginUserVO.getStaffId() );
         request.setAttribute( "username", loginUserVO.getUsername() );
         request.setAttribute( "clientName", loginUserVO.getClientName() );
         request.setAttribute( "userToken", loginUserVO.getUserToken() );
         request.setAttribute( "systemId", loginUserVO.getSystemId() );

         // ���û��Position
         if ( positions.size() == 0 )
         {
            errors.add( "LoginPositionError", new ActionMessage( "error.security.position" ) );
            // ������ʾ
            addErrors( request, errors );
            ( ( UserVO ) form ).reset();
            ( ( UserVO ) form ).setPassword( "" );

            return mapping.findForward( "logon_inHouse" );
         }
         // ���ֻ��һ��Position
         else if ( positions.size() == 1 )
         {
            loginUserVO.setPositionId( positions.get( 0 ).getMappingId() );
            request.setAttribute( "positionId", loginUserVO.getPositionId() );
            request.setAttribute( "positionName", loginUserVO.getPositionName() );
         }

         // Position����
         request.setAttribute( "positions", positions );
         loginUserVO.setPositions( positions );
         userVO.setUserIds( loginUserVO.getUserIds() );
         // ���ع����˺���Ϣ
         loadConnectCorpUserInfo( request, loginUserVO );
         // ��UserVO����д��ͻ�Cookie
         this.saveUserToClient( response, request, loginUserVO );
         this.saveUserToCookie( response, request, userVO, AUTO_LOGONI_USER );
         ( ( UserVO ) form ).reset();

         // ����ж��Position
         if ( positions.size() > 1 )
         {
            return mapping.findForward( "choosePositionPage" );
         }
         else
         {
            return new DashboardAction().showDashboard( mapping, form, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   private boolean strongPwd( String password )
   {
      Pattern pattern = Pattern.compile( "^(?=^.{8,25}$)(?=(?:.*?\\d))(?=.*[a-z])(?=(?:.*?[A-Z]){1})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})(?!.*\\s)[0-9a-zA-Z!@#$%*()_+^&]*$$" );
      Matcher matcher = pattern.matcher( password );
      return matcher.matches();
   }

   /**
    * �л��û�
   * 	changeLogonUser
   *	
   *	@param mapping
   *	@param form
   *	@param request
   *	@param response
   *	@return
   *	@throws KANException
    */
   public ActionForward changeLogonUser( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         final String redirectUrl = request.getParameter( "redirectUrl" );
         // ����Ǹոյ�½���л��˺ŵĻ�����ֱ����ת��dashboard
         if ( redirectUrl.startsWith( "securityAction.do" ) )
         {
            request.setAttribute( "redirectUrl", "dashboardAction.do?proc=showDashboard" );
         }
         else
         {

            request.setAttribute( "redirectUrl", redirectUrl );
         }

         final UserVO loginUserVO = getUserVOFromClient( request, response );

         if ( loginUserVO == null )
         {
            return mapping.findForward( "logon_inHouse" );
         }

         // �����ƽ̨�˻���¼��Super��
         if ( loginUserVO.getAccountId().equalsIgnoreCase( "1" ) )
         {
            return mapping.findForward( "logon_inHouse" );
         }
         // �����һ���û���¼
         else
         {
            final String logonUserId = request.getParameter( "logonUserId" );
            final List< MappingVO > connectCorpUserIds = loginUserVO.getConnectCorpUserIds();

            boolean flag = false;
            // ����ת����logonUserId�ڵ�½���������ӵķ�Χ����
            if ( connectCorpUserIds != null )
            {
               for ( MappingVO corpUserMappingVO : connectCorpUserIds )
               {
                  if ( corpUserMappingVO.getMappingId().equals( logonUserId ) )
                  {
                     flag = true;
                  }
               }
            }
            // �ٵ��Ϸ��Ŀ�����ת��userId
            if ( flag )
            {

               // ��ʼ��Service
               final UserService userService = ( UserService ) getService( "userService" );
               final ClientService clientService = ( ClientService ) getService( "clientService" );
               UserVO targatUserVO = userService.getUserVOByUserId( logonUserId );
               // --- mark
               final ClientVO clientVO = clientService.getClientVOByCorpId( targatUserVO.getCorpId() );
               // �ͻ��˻�Ϊ��
               if ( clientVO == null )
               {
                  return mapping.findForward( "logon_inHouse" );
               }

               // ��ȡPositions
               final List< MappingVO > positions = getPositionsByStaffId( request.getLocale(), targatUserVO.getAccountId(), targatUserVO.getStaffId() );

               // ���õ�ǰϵͳ
               clientVO.setSystemId( KANConstants.SYS_IN_HOUSE );
               clientVO.reset( mapping, request );
               // ���ÿͻ���˾����
               targatUserVO.setClientName( clientVO.getClientName() );
               // ����ClientId
               targatUserVO.setClientId( clientVO.getClientId() );
               // ����CorpId
               targatUserVO.setCorpId( clientVO.getCorpId() );
               // �����û���¼���к�
               targatUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
               // �����û���ɫ
               targatUserVO.setRole( KANConstants.ROLE_IN_HOUSE );
               // ���������¼ʱ��
               targatUserVO.setLastLogin( new Date() );
               // ���������¼IP��ַ
               targatUserVO.setLastLoginIP( getIPAddress( request ) );
               // ����ϵͳID
               targatUserVO.setSystemId( KANConstants.SYS_IN_HOUSE );
               targatUserVO.setModifyDate( new Date() );
               userService.updateUser( targatUserVO );

               // ����Request Attribute
               request.setAttribute( "accountId", targatUserVO.getAccountId() );
               request.setAttribute( "role", KANConstants.ROLE_IN_HOUSE );
               request.setAttribute( "clientId", targatUserVO.getClientId() );
               request.setAttribute( "corpId", targatUserVO.getCorpId() );
               request.setAttribute( "userId", targatUserVO.getUserId() );
               request.setAttribute( "staffId", targatUserVO.getStaffId() );
               request.setAttribute( "username", targatUserVO.getUsername() );
               request.setAttribute( "clientName", targatUserVO.getClientName() );
               request.setAttribute( "userToken", targatUserVO.getUserToken() );
               request.setAttribute( "systemId", targatUserVO.getSystemId() );

               // Position����
               request.setAttribute( "positions", positions );
               targatUserVO.setPositions( positions );

               // ���ع����˺���Ϣ
               loadConnectCorpUserInfo( request, targatUserVO );
               // ��UserVO����д��ͻ�Cookie
               this.saveUserToClient( response, request, targatUserVO );

               // ���û��Position
               if ( positions.size() == 0 )
               {
                  return mapping.findForward( "logon_inHouse" );
               }
               // ���ֻ��һ��Position
               else if ( positions.size() == 1 )
               {
                  targatUserVO.setPositionId( positions.get( 0 ).getMappingId() );
                  request.setAttribute( "positionId", targatUserVO.getPositionId() );
                  request.setAttribute( "positionName", targatUserVO.getPositionName() );
                  // ��UserVO����д��ͻ�Cookie
                  this.saveUserToClient( response, request, targatUserVO );
               }
               else if ( positions.size() > 1 )
               {
                  // ��UserVO����д��ͻ�Cookie
                  this.saveUserToClient( response, request, targatUserVO );
                  return mapping.findForward( "choosePositionPage" );
               }

               ( ( UserVO ) form ).reset();

               return new DashboardAction().showDashboard( mapping, form, request, response );
               //return mapping.findForward( "redirectPage" );
            }

         }
         return mapping.findForward( "logon_inHouse" );
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   /**
    * 
   * 	Choose Position
   *	ѡ��ְλ
   *	@param mapping
   *	@param form
   *	@param request
   *	@param response
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-15
   public ActionForward choosePosition( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡ�û�Json����
         final UserVO loginUserVO = getUserVOFromClient( request, response );
         if ( loginUserVO == null )
         {
            return mapping.findForward( "logon" );
         }

         // �����ƽ̨�˻���¼��Super��
         if ( loginUserVO.getAccountId().equalsIgnoreCase( "1" ) )
         {
            return new AccountAction().list_object( mapping, new AccountVO(), request, response );
         }
         // �����һ���û���¼
         else
         {
            // ��ȡPositionStaffRelationVO�б�
            final List< PositionStaffRelationVO > positonStaffRelationVOs = KANConstants.getKANAccountConstants( loginUserVO.getAccountId() ).getStaffDTOByStaffId( loginUserVO.getStaffId() ).getPositionStaffRelationVOs();

            if ( positonStaffRelationVOs != null && positonStaffRelationVOs.size() > 0 )
            {
               // ��ȡ����ѡ���PositionId
               final String positionId = request.getParameter( "positionId" );

               boolean exist = false;

               for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
               {
                  if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
                  {
                     loginUserVO.setPositionId( positionId );
                     request.setAttribute( "positionId", loginUserVO.getPositionId() );
                     request.setAttribute( "positionName", loginUserVO.getPositionName() );

                     exist = true;
                     break;
                  }
               }

               // ְλδѡ�л�ѡ��
               if ( !exist )
               {
                  return mapping.findForward( "choosePositionPage" );
               }
            }

            // ��UserVO����д��ͻ�Cookie
            this.saveUserToClient( response, request, loginUserVO );

            // ������л��˻����÷����ģ�����ת��ԭ���ĵ�ַ
            final String redirectUrl = KANUtil.filterEmpty( request.getParameter( "redirectUrl" ) );
            if ( redirectUrl != null )
            {
               request.getRequestDispatcher( redirectUrl ).forward( request, response );
               return null;
            }

            // ת��Dashboard
            return new DashboardAction().showDashboard( mapping, form, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   public void loadConnectCorpUserInfo( final HttpServletRequest request, final UserVO loginUserVO ) throws KANException
   {
      if ( loginUserVO != null && loginUserVO.getUserIds() != null )
      {
         // ��ʼ��Service
         final UserService userService = ( UserService ) getService( "userService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final List< MappingVO > connectCorpUserIds = new ArrayList< MappingVO >();
         String[] userIds = KANUtil.jasonArrayToStringArray( loginUserVO.getUserIds() );
         if ( userIds != null && userIds.length > 0 )
         {
            for ( String userId : userIds )
            {
               // ����user��Ӧ�� ��˾���� �� connectCorpUserId
               UserVO userVO = userService.getUserVOByUserId( userId );
               if ( userVO != null )
               {
                  if ( userVO.getCorpId() != null )
                  {
                     ClientVO clientVO = clientService.getClientVOByCorpId( userVO.getCorpId() );
                     if ( clientVO != null )
                     {
                        MappingVO mappingVO = new MappingVO();
                        mappingVO.setMappingId( userId );
                        mappingVO.setMappingValue( clientVO.getClientName() + "-" + userVO.getUsername() );
                        connectCorpUserIds.add( mappingVO );
                     }
                     else
                     {
                        log.error( "�˺��쳣��userId=" + loginUserVO.getUserId() + "���˺Ź�����userId=" + userVO.getUserId() + "û��corpId��" );
                     }
                  }
                  else
                  {
                     log.error( "�˺��쳣��userId=" + loginUserVO.getUserId() + "���˺Ź�����userId=" + userVO.getUserId() + "û��corpId��" );
                  }
               }
            }
         }

         if ( connectCorpUserIds.size() > 0 )
         {
            loginUserVO.setConnectCorpUserIds( connectCorpUserIds );
            request.setAttribute( "connectCorpUserIds", connectCorpUserIds );
         }

      }
   }

   /**
   * 	Change Position
   *	ְλ���
   *	@param mapping
   *	@param form
   *	@param request
   *	@param response
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-15
   public ActionForward changePosition( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "gbk" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final UserVO loginUserVO = getUserVOFromClient( request, response );
         if ( loginUserVO == null )
         {
            out.print( "logon" );
            return null;
         }

         // �����ƽ̨�˻���¼��Super��
         if ( loginUserVO.getAccountId().equalsIgnoreCase( "1" ) )
         {
            out.print( "success" );
         }
         // �����һ���û���¼
         else
         {
            // ��ȡPositionStaffRelationVO�б�
            final List< PositionStaffRelationVO > positonStaffRelationVOs = KANConstants.getKANAccountConstants( loginUserVO.getAccountId() ).getStaffDTOByStaffId( loginUserVO.getStaffId() ).getPositionStaffRelationVOs();
            if ( positonStaffRelationVOs != null && positonStaffRelationVOs.size() > 0 )
            {
               // ֻ��һ��position
               if ( positonStaffRelationVOs.size() == 1 )
               {
                  loginUserVO.setPositionId( positonStaffRelationVOs.get( 0 ).getPositionId() );
                  // ��UserVO����д��ͻ�Cookie
                  this.saveUserToClient( response, request, loginUserVO );
                  out.print( "success" );
               }
               // �ж��position
               else if ( positonStaffRelationVOs.size() > 1 )
               {
                  final String positionId_client = request.getParameter( "positionId" );
                  boolean exist = false;
                  for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
                  {
                     final String positionId = positionStaffRelationVO.getPositionId();
                     if ( positionId.equals( positionId_client ) )
                     {
                        exist = true;
                        break;
                     }
                  }
                  if ( exist )
                  {
                     loginUserVO.setPositionId( positionId_client );
                     // ��UserVO����д��ͻ�Cookie
                     this.saveUserToClient( response, request, loginUserVO );

                     out.print( "success" );
                  }
                  else
                  {
                     out.print( "logon" );
                  }
               }
            }
            else
            {
               loginUserVO.setPositionId( "" );
               // ��UserVO����д��ͻ�Cookie
               this.saveUserToClient( response, request, loginUserVO );
               out.print( "success" );
            }
         }
         out.flush();
         return null;
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   public ActionForward to_index( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return mapping.findForward( "index" );
   }

   public ActionForward logout( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      removeObjectFromClient( request, response, COOKIE_USER );
      removeObjectFromClient( request, response, COOKIE_SECURITY );
      removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
      return mapping.findForward( "logon_inHouse" );
   }

   public ActionForward logout_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final String userid = getUserId( request, response );
      if ( KANUtil.filterEmpty( userid ) != null )
      {
         UserService userService = ( UserService ) getService( "userService" );
         final UserVO userVO = userService.getUserVOByUserId( userid );
         String wxUserName = userVO.getRemark1();
         request.setAttribute( "wxUserName", wxUserName );
         if ( userVO != null )
         {
            userVO.setRemark1( "" );
            userService.updateUser( userVO );
         }
      }
      return logon_inHouse_mobile( mapping, new UserVO(), request, response );
   }

   /**
   * 	Change Password
   *	
   *	@param mapping
   *	@param form
   *	@param request
   *	@param response
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-15
   public ActionForward change_password( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡPassword
         final String newPassword = request.getParameter( "newPassword" );

         if ( KANUtil.filterEmpty( newPassword ) != null && KANUtil.filterEmpty( getUserId( request, response ) ) != null )
         {
            // ��ʼ��Service�ӿ�
            final UserService userService = ( UserService ) getService( "userService" );

            // ��ȡUserVO
            final UserVO userVO = userService.getUserVOByUserId( getUserId( request, response ) );

			userVO.setRemark1("");
			encryptPassword(userVO, newPassword);
			userService.updateUser(userVO);
			request.setAttribute("CHANGEPWD_MESSAGE", "zh".equalsIgnoreCase(request.getLocale().getLanguage())?"�޸ĳɹ��������µ�¼��":"Update success,please relogin");
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return this.logout( mapping, form, request, response );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * ͨ��IP138��ȡ����������IP
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward getWebIp( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         AccountVO accountVO = accountService.getAccountVOByAccountId( "100017" );
         //         URL url = new URL( "http://1111.ip138.com/ic.asp" );
         URL url = new URL( accountVO.getRemark5() );
         BufferedReader br = new BufferedReader( new InputStreamReader( url.openStream() ) );
         String s = "";
         StringBuffer sb = new StringBuffer( "" );
         String webContent = "";
         while ( ( s = br.readLine() ) != null )
         {
            sb.append( s + "\r\n" );
         }
         br.close();
         webContent = sb.toString();
         int start = webContent.indexOf( "[" ) + 1;
         int end = webContent.indexOf( "]" );
         webContent = webContent.substring( start, end );
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         out.print( webContent.trim() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return null;

      }
      return null;
   }

   /*
    *��������м��ܱ��棬����sha1���ܷ�ʽ 
    */
   /*
   public ActionForward encryptPassword( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         final PagedListHolder pagedListHolder = new PagedListHolder();
         final UserVO tempUserVO = new UserVO();
         tempUserVO.setAccountId( "100017" );
         pagedListHolder.setObject( tempUserVO );
         userService.getUserVOsByCondition( pagedListHolder, false );
         if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object obj : pagedListHolder.getSource() )
            {
               UserVO userVO = ( UserVO ) obj;
               encryptPassword(userVO,Cryptogram.decodeString(userVO.getPassword()));
               userService.updatePassWord(userVO);
            }
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return null;

      }
      return null;
   }
   */

   public static void encryptPassword( UserVO userVo, String password ) throws KANException
   {
      byte[] salt = com.kan.base.util.Encrypt.Digests.generateSalt( 8 );
      userVo.setSalt( com.kan.base.util.Encrypt.Encodes.encodeHex( salt ) );
      byte[] hashPassword = com.kan.base.util.Encrypt.Digests.sha1( password.getBytes(), salt );
      userVo.setEncryptPassword( com.kan.base.util.Encrypt.Encodes.encodeHex( hashPassword ) );
   }

   public static String encryptPassword( String salt, String password ) throws KANException
   {
      byte[] hashPassword = com.kan.base.util.Encrypt.Digests.sha1( password.getBytes(), com.kan.base.util.Encrypt.Encodes.decodeHex( salt ) );
      return com.kan.base.util.Encrypt.Encodes.encodeHex( hashPassword );
   }

   /**
    * 
   * 	Logon Inhouse Mobile
   *	
   *	@param mapping
   *	@param form
   *	@param request
   *	@param response
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-16
   public ActionForward logon_inHouse_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service
         final UserService userService = ( UserService ) getService( "userService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��ʼ��Form
         final UserVO userVO = ( UserVO ) form;

         // ��ʼ��ActionErrors
         final ActionErrors errors = new ActionErrors();

         // ��ʼ����¼�Ƿ����
         Boolean failedFlag = false;

         // ��ʼ��ClientVO
         final ClientVO clientVO = clientService.getClientVOByTitle( userVO.getClientTitle() );

         if ( clientVO != null )
         {
            userVO.setCorpId( clientVO.getCorpId() );
         }

         // ��¼����
         final UserVO loginUserVO = userService.login_inHouse( userVO );

         if ( clientVO == null )
         {
            errors.add( "LoginClientError", new ActionMessage( "error.security.client" ) );
            failedFlag = true;
            addErrors( request, errors );
            ( ( UserVO ) form ).reset();
            ( ( UserVO ) form ).setPassword( "" );

            return mapping.findForward( "logon_mobile" );
         }

         // �ж��û����Ƿ���Ч
         if ( loginUserVO == null )
         {
            errors.add( "LoginUsernameError", new ActionMessage( "error.security.username" ) );
            failedFlag = true;
         }
         else
         {
            // �ж������Ƿ���Ч
            if ( !encryptPassword( loginUserVO.getSalt(), userVO.getPassword() ).equals( loginUserVO.getEncryptPassword() ) )
            {
               errors.add( "LoginPasswordError", new ActionMessage( "error.security.password" ) );
               failedFlag = true;
            }

            // �жϵ�¼IP�Ƿ���Ч
            if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
               failedFlag = true;
            }
         }

         // ������û�����Ч��ת����¼ҳ��
         if ( failedFlag )
         {
            addErrors( request, errors );
            ( ( UserVO ) form ).reset();
            ( ( UserVO ) form ).setPassword( "" );
            request.setAttribute( "loginerror", "1" );
            return mapping.findForward( "logon_mobile" );
         }
         else if ( loginUserVO != null )
         {
            // ��ʼ����¼���󣨹��ʻ���
            loginUserVO.reset( mapping, request );
         }

         // ��ȡPositions
         final List< MappingVO > positions = getPositionsByStaffId( request.getLocale(), loginUserVO.getAccountId(), loginUserVO.getStaffId() );

         // ���õ�ǰϵͳ
         clientVO.setSystemId( KANConstants.SYS_IN_HOUSE );
         clientVO.reset( mapping, request );
         // ���ÿͻ���˾����
         loginUserVO.setClientName( clientVO.getClientName() );
         // ����ClientId
         loginUserVO.setClientId( clientVO.getClientId() );
         // ����CorpId
         loginUserVO.setCorpId( clientVO.getCorpId() );
         // �����û���¼���к�
         loginUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // �����û���ɫ
         loginUserVO.setRole( KANConstants.ROLE_IN_HOUSE );
         // ���������¼ʱ��
         loginUserVO.setLastLogin( new Date() );
         // ���������¼IP��ַ
         loginUserVO.setLastLoginIP( getIPAddress( request ) );
         // ����ϵͳID
         loginUserVO.setSystemId( KANConstants.SYS_IN_HOUSE );
         loginUserVO.setModifyDate( new Date() );
         userService.updateUser( loginUserVO );

         // ����Request Attribute
         request.setAttribute( "accountId", loginUserVO.getAccountId() );
         request.setAttribute( "role", KANConstants.ROLE_IN_HOUSE );
         request.setAttribute( "clientId", loginUserVO.getClientId() );
         request.setAttribute( "corpId", loginUserVO.getCorpId() );
         request.setAttribute( "userId", loginUserVO.getUserId() );
         request.setAttribute( "staffId", loginUserVO.getStaffId() );
         request.setAttribute( "username", loginUserVO.getUsername() );
         request.setAttribute( "clientName", loginUserVO.getClientName() );
         request.setAttribute( "userToken", loginUserVO.getUserToken() );
         request.setAttribute( "systemId", loginUserVO.getSystemId() );

         // ���û�п��õ�Position
         if ( positions.size() == 0 )
         {
            errors.add( "LoginPositionError", new ActionMessage( "error.security.position" ) );
            // ������ʾ
            addErrors( request, errors );
            ( ( UserVO ) form ).reset();
            ( ( UserVO ) form ).setPassword( "" );

            return mapping.findForward( "logon_mobile" );
         }
         // ���ֻ��һ��Position
         else if ( positions.size() == 1 )
         {
            loginUserVO.setPositionId( positions.get( 0 ).getMappingId() );
            request.setAttribute( "positionId", loginUserVO.getPositionId() );
            request.setAttribute( "positionName", loginUserVO.getPositionName() );
         }

         // Position����
         request.setAttribute( "positions", positions );
         loginUserVO.setPositions( positions );

         // ��UserVO����д��ͻ�Cookie
         this.saveUserToClient( response, request, loginUserVO );
         // ��clientVO����д��ͻ�Cookie
         if ( "1".equals( request.getParameter( "cbPersistentCookie" ) ) )
         {
            final String userCookie = userVO.getClientTitle() + "##" + userVO.getUsername() + "##" + userVO.getPassword();
            final Cookie cookie = new Cookie( "KAN_LOGON_INFO_MOBILE", URLEncoder.encode( userCookie, "gbk" ) );
            response.addCookie( cookie );
         }
         else
         {
            // �������
            response.addCookie( new Cookie( "KAN_LOGON_INFO_MOBILE", "" ) );
         }
         //�Ƿ��Զ���¼ ,autoLogin==1��������ҳ��
         String[] autoLogins = request.getParameterValues( "autoLogin" );
         String autoLogin = "";
         if ( autoLogins != null && autoLogins.length == 1 )
         {
            autoLogin = autoLogins[ 0 ];
         }
         if ( "1".equals( autoLogin ) && KANUtil.filterEmpty( request.getParameter( "wxUserName" ) ) != null )
         {
            System.out.println( "�������Զ���¼.wxUserName=" + request.getParameter( "wxUserName" ) );
            final UserVO tmpUserVO = userService.getUserVOByUserId( loginUserVO.getUserId() );
            tmpUserVO.setRemark1( request.getParameter( "wxUserName" ) );
            userService.updateUser( tmpUserVO );
         }

         ( ( UserVO ) form ).reset();

         // ����ж��Position
         if ( positions.size() > 1 )
         {
            return mapping.findForward( "choosePositionPage_mobile" );
         }
         else
         {
            return new ActionForward( "/securityAction.do?proc=index_mobile", true );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   public ActionForward index_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return mapping.findForward( "index_mobile" );
   }

   public ActionForward choosePosition_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡ�û�Json����
         final JSONObject userJson = getUserFromClient( request, response );

         if ( userJson == null )
         {
            return mapping.findForward( "logon_inHouse_mobile" );
         }

         //ָ��Ҫת��Ϊ�Ķ���ĳ�Ա���Ե���������
         final Map< String, Class< ? >> map = new HashMap< String, Class< ? >>();
         map.put( "positions", MappingVO.class );
         final UserVO loginUserVO = ( UserVO ) JSONObject.toBean( userJson, UserVO.class, map );

         // �����ƽ̨�˻���¼��Super��
         if ( loginUserVO.getAccountId().equalsIgnoreCase( "1" ) )
         {
            return new AccountAction().list_object( mapping, new AccountVO(), request, response );
         }
         // �����һ���û���¼
         else
         {
            // ��ȡPositionStaffRelationVO�б�
            final List< PositionStaffRelationVO > positonStaffRelationVOs = KANConstants.getKANAccountConstants( loginUserVO.getAccountId() ).getStaffDTOByStaffId( loginUserVO.getStaffId() ).getPositionStaffRelationVOs();

            if ( positonStaffRelationVOs != null && positonStaffRelationVOs.size() > 0 )
            {
               // ��ȡ����ѡ���PositionId
               final String positionId = request.getParameter( "positionId" );

               boolean exist = false;

               for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
               {
                  if ( positionStaffRelationVO.getPositionId().equals( positionId ) )
                  {
                     loginUserVO.setPositionId( positionId );
                     request.setAttribute( "positionId", loginUserVO.getPositionId() );
                     request.setAttribute( "positionName", loginUserVO.getPositionName() );

                     exist = true;
                     break;
                  }
               }

               // ְλδѡ�л�ѡ��
               if ( !exist )
               {
                  return mapping.findForward( "choosePositionPage_mobile" );
               }
            }

            // ��UserVO����д��ͻ�Cookie
            this.saveUserToClient( response, request, loginUserVO );

            // ת����ҳ
            return mapping.findForward( "index_mobile" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   // ����StaffId��ȡPosition Mapping�б�
   // Added by Kevin Jin at 2014-04-15
   private List< MappingVO > getPositionsByStaffId( final Locale locale, final String accountId, final String staffId )
   {
      // ��ʼ��StaffDTO
      final StaffDTO staffDTO = KANConstants.getKANAccountConstants( accountId ).getStaffDTOByStaffId( staffId );
      // ��ȡStaff��Ӧ��Position�б�
      final List< PositionStaffRelationVO > positonStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
      // ��ʼ��Position Mapping List
      final List< MappingVO > positions = new ArrayList< MappingVO >();
      final List< MappingVO > mainPositions = new ArrayList< MappingVO >();
      final List< MappingVO > agentPositions = new ArrayList< MappingVO >();
      if ( positonStaffRelationVOs != null && positonStaffRelationVOs.size() > 0 )
      {
         for ( PositionStaffRelationVO positionStaffRelationVO : positonStaffRelationVOs )
         {
            if ( KANUtil.filterEmpty( positionStaffRelationVO.getStaffType() ) != null && KANUtil.filterEmpty( positionStaffRelationVO.getStaffType() ).equals( "2" ) )
            {
               // ֻҪ��Ч����ʱ�����positionID
               if ( KANUtil.filterEmpty( positionStaffRelationVO.getAgentStart() ) != null && KANUtil.filterEmpty( positionStaffRelationVO.getAgentEnd() ) != null )
               {
                  // �ж��Ƿ��ڴ������ڷ�Χ��
                  if ( KANUtil.getDays( KANUtil.createDate( positionStaffRelationVO.getAgentStart() ) ) <= KANUtil.getDays( new Date() )
                        && KANUtil.getDays( new Date() ) <= KANUtil.getDays( KANUtil.createDate( positionStaffRelationVO.getAgentEnd() ) ) )
                  {
                     final MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( positionStaffRelationVO.getPositionId() );
                     final PositionVO positionVO = KANConstants.getKANAccountConstants( accountId ).getPositionDTOByPositionId( positionStaffRelationVO.getPositionId() ).getPositionVO();

                     if ( KANUtil.filterEmpty( locale.getLanguage() ) != null && KANUtil.filterEmpty( locale.getLanguage() ).equalsIgnoreCase( "ZH" ) )
                     {
                        mappingVO.setMappingValue( positionVO.getTitleZH() );
                     }
                     else
                     {
                        mappingVO.setMappingValue( positionVO.getTitleEN() );
                     }
                     agentPositions.add( mappingVO );
                  }
               }
            }
            else
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( positionStaffRelationVO.getPositionId() );
               final PositionVO positionVO = KANConstants.getKANAccountConstants( accountId ).getPositionDTOByPositionId( positionStaffRelationVO.getPositionId() ).getPositionVO();

               if ( KANUtil.filterEmpty( locale.getLanguage() ) != null && KANUtil.filterEmpty( locale.getLanguage() ).equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( positionVO.getTitleZH() );
               }
               else
               {
                  mappingVO.setMappingValue( positionVO.getTitleEN() );
               }
               mainPositions.add( mappingVO );
            }
         }
      }

      if ( mainPositions.size() > 0 )
         positions.addAll( mainPositions );
      if ( agentPositions.size() > 0 )
         positions.addAll( agentPositions );
      return positions;
   }

   /**
    *  �ַ����Ƿ��ת������
    * 
    * @author : Ian.huang
    * @date   : 2014-6-24
    * @param  : @param str
    * @param  : @return
    * @return : boolean
    */
   public static boolean checkNumeric( String str )
   {
      if ( str == null || str.equals( "" ) )
      {
         return false;
      }
      Pattern pattern = Pattern.compile( "[0-9]*" );
      return pattern.matcher( str ).matches();
   }

   /**
    * �Զ���¼����cookie
    * 
    * @author : Ian.huang
    * @date   : 2014-6-30
    * @param  : @param response
    * @param  : @param request
    * @param  : @param userVO
    * @param  : @param type
    * @param  : @throws KANException
    * @return : void
    */
   private void saveUserToCookie( final HttpServletResponse response, final HttpServletRequest request, final UserVO userVO, final String type ) throws KANException
   {
      try
      {
         if ( userVO.getCbPersistentCookie() != null && userVO.getCbPersistentCookie().equals( "on" ) )
         {
            // Json�ֽ��������ͻ���
            String cookieUser = userVO.getAccountId() + "_" + userVO.getAccountName() + "_" + userVO.getUsername() + "_" + userVO.getPassword() + "_" + userVO.getCorpId();
            final Cookie cookie = new Cookie( type, URLEncoder.encode( cookieUser, "gbk" ) );
            cookie.setMaxAge( 15 * 24 * 60 * 60 );
            response.addCookie( cookie );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 
    * 
    * @author : Ian.huang
    * @date   : 2014-6-30
    * @param  : @param mapping
    * @param  : @param form
    * @param  : @param request
    * @param  : @param response
    * @param  : @return
    * @param  : @throws KANException
    * @return : ActionForward
    */
   public ActionForward autoLogon( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      JSONObject jsonObject = new JSONObject();
      UserVO userVO = new UserVO();
      try
      {
         final UserService userService = ( UserService ) getService( "userService" );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         String type = request.getParameter( "type" );
         if ( type == null || type == "" )
         {
            jsonObject.put( "success", false );
            return mapping.findForward( "" );
         }
         Cookie[] cookies = request.getCookies();
         String[] cooks = null;
         String username = null;
         String password = null;
         String accountId = null;
         String accountName = null;
         String corpId = null;
         boolean flag = false;
         if ( cookies != null )
         {
            for ( Cookie cook : cookies )
            {
               String cookieName = cook.getName();
               if ( !cookieName.equalsIgnoreCase( type ) )
               {
                  continue;
               }
               String cookieValue = cook.getValue();
               cooks = cookieValue.split( "_" );
               if ( cooks.length == 5 )
               {
                  accountId = java.net.URLDecoder.decode( cooks[ 0 ], "gbk" );
                  username = java.net.URLDecoder.decode( cooks[ 2 ], "gbk" );
                  accountName = java.net.URLDecoder.decode( cooks[ 1 ], "gbk" );
                  password = java.net.URLDecoder.decode( cooks[ 3 ], "gbk" );
                  corpId = java.net.URLDecoder.decode( cooks[ 4 ], "gbk" );
               }
            }
         }
         if ( username != null && password != null && accountId != null )
         {
            userVO.setAccountId( accountId );
            userVO.setAccountName( accountName );
            userVO.setUsername( username );
            userVO.setPassword( password );
            userVO.setCorpId( corpId );
            if ( type.equals( AUTO_LOGON_USER ) )
            {
               final UserVO loginUserVO = userService.login( userVO );
               if ( loginUserVO != null )
               {
                  jsonObject = JSONObject.fromObject( userVO );
                  flag = true;
                  // �ж��û��������Ƿ���Ч
                  if ( !KANUtil.decodeString( loginUserVO.getPassword() ).equals( userVO.getPassword() ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGON_USER );
                  }

                  if ( KANUtil.filterEmpty( loginUserVO.getAccountId() ) != null && !KANUtil.filterEmpty( loginUserVO.getAccountId() ).equals( "1" )
                        && !KANUtil.filterEmpty( loginUserVO.getAccountId() ).equals( userVO.getAccountId() ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGON_USER );
                  }

                  // �жϵ�¼IP�Ƿ���Ч
                  if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGON_USER );
                  }

               }
               else
               {
                  flag = false;
                  removeAutoLogonToCookie( response, request, type );
               }
            }
            else if ( type.equals( AUTO_LOGONI_USER ) )
            {
               final UserVO loginUserVO = userService.login_inHouse( userVO );
               if ( loginUserVO != null )
               {
                  jsonObject = JSONObject.fromObject( userVO );
                  flag = true;
                  // �ж��û��������Ƿ���Ч
                  if ( !KANUtil.decodeString( loginUserVO.getPassword() ).equals( userVO.getPassword() ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
                  }
                  // �жϵ�¼IP�Ƿ���Ч
                  if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
                  }

               }
               else
               {
                  flag = false;
                  removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
               }
            }
            jsonObject.put( "success", flag );
            // Send to client
            out.println( jsonObject != null ? jsonObject.toString() : "" );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "" );
   }

   public ActionForward unFrz( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final String userName = request.getParameter( "username" );
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "gbk" );
         BaseAction.constantsInit( "removeUserFromBlackList", userName );
         final PrintWriter out = response.getWriter();
         out.print( userName + "���Ƴ�������" );
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

      return null;
   }

   public ActionForward to_passwordNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return mapping.findForward( "to_passwordNew" );
   }

   public ActionForward logon_wx( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final UserService userService = ( UserService ) getService( "userService" );
      final String code = request.getParameter( "code" );
      System.out.println( "��ȡcode=" + code );
      String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
      AccessToken accessToken = TokenThread.accessToken;
      if ( accessToken == null )
      {
         accessToken = WXUtil.getAccessToken();
      }
      System.out.println( "��ȡtoken=" + accessToken.getToken() );
      requestUrl = requestUrl.replaceAll( "ACCESS_TOKEN", accessToken.getToken() ).replaceAll( "CODE", KANUtil.filterEmpty( code ) == null ? "" : code );
      //
      JSONObject jsonObject = WXUtil.httpRequest( requestUrl, "GET", null );
      System.out.println( "jsonobject=" + jsonObject.toString() );
      //{"UserId":"USERID","DeviceId":"DEVICEID"}
      if ( jsonObject != null )
      {
         Object tmpObject = jsonObject.get( "UserId" );
         if ( tmpObject != null )
         {
            String wxUserName = tmpObject.toString();
            System.out.println( "wxUserName=" + wxUserName );
            if ( KANUtil.filterEmpty( wxUserName ) != null )
            {
               request.setAttribute( "wxUserName", wxUserName );
               // ��֤remark1
               final UserVO userVO = userService.getUserVOByRemark1( wxUserName );
               if ( userVO != null )
               {
                  System.out.println( "������¼:" + wxUserName );
                  // ��ȡPositions
                  final List< MappingVO > positions = getPositionsByStaffId( request.getLocale(), userVO.getAccountId(), userVO.getStaffId() );
                  // �����û���¼���к�
                  userVO.setUserToken( RandomUtil.getRandomString( 16 ) );
                  // �����û���ɫ
                  userVO.setRole( KANConstants.ROLE_IN_HOUSE );
                  // ���������¼ʱ��
                  userVO.setLastLogin( new Date() );
                  // ���������¼IP��ַ
                  userVO.setLastLoginIP( getIPAddress( request ) );
                  // ����ϵͳID
                  userVO.setSystemId( KANConstants.SYS_IN_HOUSE );
                  userVO.setModifyDate( new Date() );
                  userService.updateUser( userVO );

                  // ����Request Attribute
                  request.setAttribute( "accountId", userVO.getAccountId() );
                  request.setAttribute( "role", KANConstants.ROLE_IN_HOUSE );
                  request.setAttribute( "clientId", userVO.getClientId() );
                  request.setAttribute( "corpId", userVO.getCorpId() );
                  request.setAttribute( "userId", userVO.getUserId() );
                  request.setAttribute( "staffId", userVO.getStaffId() );
                  request.setAttribute( "username", userVO.getUsername() );
                  request.setAttribute( "clientName", userVO.getClientName() );
                  request.setAttribute( "userToken", userVO.getUserToken() );
                  request.setAttribute( "systemId", userVO.getSystemId() );

                  // ���û�п��õ�Position
                  if ( positions.size() == 0 )
                  {
                     return mapping.findForward( "logon_mobile" );
                  }
                  // ���ֻ��һ��Position
                  else if ( positions.size() == 1 )
                  {
                     userVO.setPositionId( positions.get( 0 ).getMappingId() );
                     request.setAttribute( "positionId", userVO.getPositionId() );
                     request.setAttribute( "positionName", userVO.getPositionName() );
                  }

                  // Position����
                  request.setAttribute( "positions", positions );
                  userVO.setPositions( positions );

                  // ��UserVO����д��ͻ�Cookie
                  this.saveUserToClient( response, request, userVO );

                  // ����ж��Position
                  if ( positions.size() > 1 )
                  {
                     return mapping.findForward( "choosePositionPage_mobile" );
                  }
                  else
                  {
                     return new ActionForward( "/securityAction.do?proc=index_mobile", true );
                  }
               }
            }
         }
      }

      return mapping.findForward( "logon_mobile" );
   }

   // ȥ���������ҳ��
   public ActionForward toForgetPwd( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      String wxUserName = request.getParameter( "wxUserName" );
      if ( StringUtils.isNotBlank( wxUserName ) )
      {
         UserService userService = ( UserService ) getService( "userService" );
         UserVO condUserVO = new UserVO();
         condUserVO.setUsername( wxUserName );
         condUserVO.setCorpId( KANConstants.DEFAULT_CORPID );
         UserVO userVO = userService.login_inHouse( condUserVO );
         if ( userVO != null )
         {
            StaffService staffService = ( StaffService ) getService( "staffService" );
            StaffVO staffVO = staffService.getStaffVOByStaffId( userVO.getStaffId() );
            if ( staffVO != null )
            {
               EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
               EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
               if ( employeeVO != null && StringUtils.isNotBlank( employeeVO.getEmail1() ) )
               {
                  request.setAttribute( "wxUserEmail", employeeVO.getEmail1() );
               }
            }
         }
      }
      return mapping.findForward( "toForgetPwd" );
   }

   // �ύ��������
   public ActionForward forgetPassword( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws Exception
   {
      String mail = "";
      String username = request.getParameter( "username" );
      if ( StringUtils.contains( username, "@" ) )
      {
         mail = username;
         username = username.split( "@" )[ 0 ].trim();
      }
      UserService userService = ( UserService ) getService( "userService" );
      UserVO userVO = new UserVO();
      userVO.setAccountId( KANConstants.DEFAULT_ACCOUNTID );
      userVO.setCorpId( KANConstants.DEFAULT_CORPID );
      userVO.setUsername( username );
      UserVO forgetPwdUser = userService.login_inHouse( userVO );
      if ( forgetPwdUser != null )
      {
         if ( StringUtils.isBlank( mail ) )
         {
            StaffService staffService = ( StaffService ) getService( "staffService" );
            StaffVO staffVO = staffService.getStaffVOByStaffId( forgetPwdUser.getStaffId() );
            if ( staffVO != null )
            {
               EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
               EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
               mail = employeeVO.getEmail1();
            }
         }
         if ( StringUtils.isNotBlank( mail ) )
         {
            // ����һ�����64λ��֤
            String randomValidCode = UUID.randomUUID().toString().trim().replaceAll( "-", "" );
            forgetPwdUser.setRemark5( randomValidCode );
            forgetPwdUser.setRemark4( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            userService.updateUser( forgetPwdUser );
            // �����޸��ʼ�
            String content = ContentUtil.getMailContent_forgetPassword( forgetPwdUser );
            MessageMailVO messageMailVO = new MessageMailVO();
            messageMailVO.setSystemId( "1" );
            messageMailVO.setAccountId( KANConstants.DEFAULT_ACCOUNTID );
            messageMailVO.setCorpId( KANConstants.DEFAULT_CORPID );
            boolean isZH = "zh".equalsIgnoreCase( request.getLocale().getLanguage() );
            messageMailVO.setTitle( "HRIS" + ( isZH ? " ��������" : " Password Reset" ) );
            messageMailVO.setContent( content );
            messageMailVO.setContentType( "2" );
            messageMailVO.setReception( mail );
            messageMailVO.setShowName( "HRIS NOTICE" );
            messageMailVO.setStatus( "1" );
            messageMailVO.setCreateBy( "system" );
            messageMailVO.setCreateDate( new Date() );
            MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
            messageMailService.insertMessageMail( messageMailVO, KANConstants.DEFAULT_ACCOUNTID );
            request.setAttribute( "resetResult", "success" );
            try{
              BaseAction.constantsInit( "removeUserFromBlackList", username );
              }catch(Exception e){
                System.out.println("�Ƴ�����������. RMI Exception");
              }
         }
         else
         {
            // �Ҳ�����Ӧ����Ϣ
            request.setAttribute( "resetResult", "error" );
         }
      }
      return mapping.findForward( "forgetPasswordResult" );
   }

   // ȥ���������ҳ��
   public ActionForward toResetPassword( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      String validCode = request.getParameter( "validCode" );
      UserService userService = ( UserService ) getService( "userService" );
      UserVO userVO = userService.getUserVOByRemark5( validCode );
      request.setAttribute( "userVO", userVO );
      request.setAttribute( "validCode", validCode );
      request.setAttribute( "action", "resetPassword" );
      String mappingForward = "toResetPassword";
      if ( userVO == null )
      {
         mappingForward = "validCodeExpired";
      }
      return mapping.findForward( mappingForward );

   }

   // �ύ��������
   public ActionForward resetPassword( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      String validCode = request.getParameter( "validCode" );
      UserService userService = ( UserService ) getService( "userService" );
      UserVO userVO = userService.getUserVOByRemark5( validCode );
      if ( userVO == null )
      {
    	  return mapping.findForward( "validCodeExpired" );
      }
      else
      {
	      // ��ȡҳ������
	      String newpassword = request.getParameter( "newpassword" );
	      encryptPassword( userVO, newpassword );
	      // ʹ��һ�����
	      userVO.setRemark5( "" );
	      userVO.setRemark4( "" );
	      userService.updateUser( userVO );
	      try{
	      BaseAction.constantsInit( "removeUserFromBlackList", userVO.getUsername() );
	      }catch(Exception e){
	        System.out.println("�Ƴ�����������. RMI Exception");
	      }
	      //
	      return mapping.findForward( "resetPasswordSuccess" );
      }
   }

   // ȥ�޸�����ҳ��, ���������빲һ��ҳ��
   public ActionForward to_changePwd_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      UserService userService = ( UserService ) getService( "userService" );
      UserVO userVO = userService.getUserVOByUserId( getUserId( request, response ) );
      request.setAttribute( "userVO", userVO );
      request.setAttribute( "action", "changePwd_mobile" );
      return mapping.findForward( "toResetPassword" );
   }

   // �ύ�޸�����
   public ActionForward changePwd_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      UserService userService = ( UserService ) getService( "userService" );
      String userId = BaseAction.getUserId( request, response );
      UserVO userVO = userService.getUserVOByUserId( userId );
      String success = "success";
      if ( userVO != null )
      {
         String newPwd = request.getParameter( "newpassword" );
         userVO.setRemark1( "" );
         encryptPassword( userVO, newPwd );
         userService.updateUser( userVO );
         try{
           BaseAction.constantsInit( "removeUserFromBlackList", userVO.getUsername() );
           }catch(Exception e){
             System.out.println("�Ƴ�����������. RMI Exception");
           }
      }
      else
      {
         success = "error";
      }
      request.setAttribute( "success", success );
      return mapping.findForward( "changePwdResult_mobile" );
   }
}