/*
 * Created on 2003-04-12
 */
package com.kan.base.web.actions.security;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.Operate;
import com.kan.base.util.PassWordUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.renders.security.UserRender;
import com.kan.hro.domain.biz.client.ClientVO;

/**
 * @author Kevin Jin
 */
public class UserAction extends BaseAction
{

   /**
    * List Users
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         // ���Action Form
         final UserVO userVO = ( UserVO ) form;

         // �����Action��ɾ���û��б�
         if ( userVO.getSubAction() != null && userVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( userVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder userHolder = new PagedListHolder();

         // ���뵱ǰҳ
         userHolder.setPage( page );
         // ���뵱ǰֵ����
         userHolder.setObject( userVO );
         // ����ҳ���¼����
         userHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         userService.getUserVOsByCondition( userHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( userHolder, request );

         // Holder��д��Request����
         request.setAttribute( "userHolder", userHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listUserTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listUser" );
   }

   /**
    * To user modify page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         // ��õ�ǰ����
         String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );
         // ���������Ӧ����
         UserVO userVO = userService.getUserVOByUserId( userId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         userVO.reset( null, request );
         userVO.setStaffName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNameByStaffId( userVO.getStaffId() ) );
         userVO.setPassword( "" );
         userVO.setSubAction( VIEW_OBJECT );

         // ����ActionForm����ǰ��
         request.setAttribute( "userForm", userVO );

         // ����޸���ʷ
         final List< Object > historyUserVOs = userService.getHistoryUserVOsByUserId( userId );
         // ������ʷ�޸ļ�¼�б�ǰ��
         request.setAttribute( "historyUserVOs", historyUserVOs );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageUser" );
   }

   /**
    * To user new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( UserVO ) form ).setStatus( UserVO.TRUE );
      ( ( UserVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageUser" );
   }

   /**
    * Add user
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final UserService userService = ( UserService ) getService( "userService" );
            // ���ActionForm
            final UserVO userVO = ( UserVO ) form;
            // �������ļ����ܺ�������ݿ�
            if ( userVO.getPassword() != null && !userVO.getPassword().trim().equals( "" ) )
            {
               SecurityAction.encryptPassword( userVO, userVO.getPassword() );
               userVO.setPassword( Cryptogram.encodeString( userVO.getPassword() ) );
            }
            userVO.setAccountId( getAccountId( request, response ) );
            userVO.setCreateBy( getUserId( request, response ) );
            userVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            userService.insertUser( userVO );

            // ������ӳɹ����
            success( request );
         }
         // ���Form����
         ( ( UserVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify user
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final UserService userService = ( UserService ) getService( "userService" );
            // ��õ�ǰ����
            final String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );
            // ��õ�ǰ�Ƿ���Ҫ�޸�����
            final String changePassword = request.getParameter( "changePassword" );
            // ���������Ӧ����
            final UserVO userVO = userService.getUserVOByUserId( userId );
            // ������ʷ�޸�ʱ��� ????
            // userVO.setHisTitle( getHisTitle( userVO, request ) );
            // ֵ������
            if ( changePassword != null && changePassword.trim().equalsIgnoreCase( "on" ) )
            {
               userVO.update( ( UserVO ) form, true );
               encryptPassword( userVO, userVO.getPassword() );
            }
            else
            {
               userVO.update( ( UserVO ) form, false );
            }
            userVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            userService.updateUser( userVO );

            insertlog( request, userVO, Operate.MODIFY, userId, null );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form����
         ( ( UserVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete user
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         final UserVO userVO = new UserVO();
         // ��õ�ǰ����
         String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );

         // ɾ��������Ӧ����
         userVO.setUserId( userId );
         userVO.setModifyBy( getUserId( request, response ) );
         userService.deleteUser( userVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete user list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         // ���Action Form
         UserVO userVO = ( UserVO ) form;
         // ����ѡ�е�ID
         if ( userVO.getSelectedIds() != null && !userVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : userVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               userVO.setUserId( selectedId );
               userVO.setModifyBy( getUserId( request, response ) );
               userService.deleteUser( userVO );
            }
         }

         // ���Selected IDs����Action
         userVO.setSelectedIds( "" );
         userVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��� �Ƿ���ְλ/�û����Ƿ����
   public ActionForward check_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         // ��ȡ�����Username
         final String username = URLDecoder.decode( URLDecoder.decode( request.getParameter( "username" ), "UTF-8" ), "UTF-8" );
         // ��ȡ�����employeeId
         final String staffId = request.getParameter( "staffId" );
         final String employeeId = request.getParameter( "employeeId" );
         if ( KANUtil.filterEmpty( staffId ) == null && KANUtil.filterEmpty( employeeId ) == null )
         {
            out.println( KANUtil.getProperty( request.getLocale(), "error.not.position" ) );
         }
         else
         {
            StaffDTO staffDTO = null;
            //�Ƿ����ְλ
            if ( KANUtil.filterEmpty( staffId ) != null )
            {
               staffDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOByStaffId( staffId );
            }
            else
            {
               List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOsByEmployeeId( employeeId );
               if ( staffDTOs != null && staffDTOs.size() > 0 )
               {
                  staffDTO = staffDTOs.get( 0 );
               }
            }

            // �Ƿ����ְλ
            if ( staffDTO != null && staffDTO.getPositionStaffRelationVOs() != null && staffDTO.getPositionStaffRelationVOs().size() > 0 )
            {
               // ��ʼ����������
               final UserVO userVO = new UserVO();
               userVO.setAccountId( getAccountId( request, response ) );
               userVO.setUsername( username );

               // ��ѯ���ݿ��Ƿ��Ѵ��ڴ˶���
               UserVO existUserVO = userService.login( userVO );

               if ( existUserVO == null )
               {
                  userVO.setCorpId( getCorpId( request, response ) );
                  existUserVO = userService.login_inHouse( userVO );
               }

               if ( existUserVO != null )
               {
                  out.println( KANUtil.getProperty( request.getLocale(), "error.username.already.exist" ).replace( "XX", username ) );
               }
            }
            else
            {
               out.println( KANUtil.getProperty( request.getLocale(), "error.not.position" ) );
            }
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã�����Forward
      return mapping.findForward( "" );
   }

   // ��ȡ��ʷ���ļ�¼
   public ActionForward get_history_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         // ��ȡ�����HistoryId
         final String historyId = request.getParameter( "historyId" );

         // ��ȡ��ʷ�û�����
         final UserVO historyUserVO = userService.getHistoryUserVOByHistoryId( historyId );

         if ( historyUserVO != null )
         {
            // �����˻�ID
            historyUserVO.setAccountId( getAccountId( request, response ) );
            // ��ʼ������
            historyUserVO.reset( mapping, request );
            // ����Render����HTML
            out.println( UserRender.getHistoryUserVO( request.getLocale(), historyUserVO ) );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã�����Forward
      return mapping.findForward( "" );
   }

   public static void encryptPassword( UserVO userVo, String password ) throws KANException
   {
      byte[] salt = com.kan.base.util.Encrypt.Digests.generateSalt( 8 );
      userVo.setSalt( com.kan.base.util.Encrypt.Encodes.encodeHex( salt ) );
      byte[] hashPassword = com.kan.base.util.Encrypt.Digests.sha1( password.getBytes(), salt );
      userVo.setEncryptPassword( com.kan.base.util.Encrypt.Encodes.encodeHex( hashPassword ) );
   }

   // ��������
   public ActionForward reset_password_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // ��õ�ǰ����
         final String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "staffId" ), "UTF-8" ) );

         // ��ȡ��ʷ�û�����
         UserVO userVO = null;
         if ( staffId != null && !staffId.trim().equals( "" ) )
         {
            userVO = userService.getUserVOByStaffId( staffId );
         }

         // ����û���ΪNULL�������û�����
         if ( userVO != null )
         {
            String pwd = PassWordUtil.randomPassWord() ;
            userVO.setModifyBy( getUserId( request, response ) );
            userVO.setModifyDate( new Date() );
            encryptPassword( userVO, pwd );
            userService.updateUser( userVO );
            try
            {
               BaseAction.constantsInit( "removeUserFromBlackList", userVO.getUsername() );
            }
            catch ( Exception e )
            {
               e.printStackTrace();
            }

            final StaffVO staffVO = staffService.getStaffVOByStaffId( userVO.getStaffId() );

            String email = request.getParameter( "email" );
            final String email2 = request.getParameter( "email2" );
            if ( KANUtil.filterEmpty( email ) == null )
            {
               email = email2;
            }

            if ( KANUtil.filterEmpty( email ) != null )
            {
               // �������������ʼ�
               // Todo - Kevin
               final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
               String accountName = BaseAction.getPropertyFromCookie( request, response, "accountName" );
               new Mail( staffVO.getAccountId(), email, BaseAction.getTitle( request, response ) + "�������óɹ� �� " + staffVO.getNameZH() + " - " + staffVO.getNameEN() + "��", "�û�����"
                     + userVO.getUsername() + "<br>���룺" + pwd ).send( true );

               out.println( "<div class=\"message success fadable\">��������ɹ����ѷ��������䣺" + email
                     + "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
            }
            else
            {
               out.println( "<div class=\"message warning fadable\">��������ɹ����޷���������˾���䡣<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
            }
         }
         else
         {
            out.println( "<div class=\"message error fadable\">�����û������ڡ�<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
         }
         out.println( "<script type=\"text/javascript\">messageWrapperFada();</script>" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã�����Forward
      return mapping.findForward( "" );
   }

   // Added by Jixiang.hu 2013-12-02
   public ActionForward list_clientVO_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );

         final List< Object > clients = userService.getClientVOs();

         final JSONArray array = new JSONArray();
         for ( Object o : clients )
         {
            final ClientVO clientVO = ( ClientVO ) o;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( "id", clientVO.getCorpId() );
            jsonObject.put( "name", clientVO.getNameZH() + ( KANUtil.filterEmpty( clientVO.getNameEN() ) != null ? " - " + clientVO.getNameEN() : "" ) );
            array.add( jsonObject );
         }
         // Send to front
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Staff Service
         final UserService userService = ( UserService ) getService( "userService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            List< Object > list = userService.getUserVOsByCorpId( getCorpId( request, response ) );
            if ( list != null )
            {
               for ( Object obj : list )
               {
                  UserVO userVO = ( UserVO ) obj;
                  JSONObject jsonObj = new JSONObject();
                  jsonObj.put( "id", userVO.getUserId() );
                  jsonObj.put( "name", userVO.getStaffName() );
                  array.add( jsonObj );
               }
            }

         }
         else
         {
            final PagedListHolder pagedListHolder = new PagedListHolder();
            final UserVO tempUserVO = new UserVO();
            tempUserVO.setAccountId( getAccountId( request, response ) );
            pagedListHolder.setObject( tempUserVO );
            userService.getUserVOsByCondition( pagedListHolder, false );
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object obj : pagedListHolder.getSource() )
               {
                  UserVO userVO = ( UserVO ) obj;
                  JSONObject jsonObj = new JSONObject();
                  jsonObj.put( "id", userVO.getUserId() );
                  jsonObj.put( "name", userVO.getStaffName() );
                  array.add( jsonObj );
               }
            }
         }

         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

}