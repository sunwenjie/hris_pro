/*
 * Created on 2003-04-12
 */
package com.kan.base.wap.actions.security;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.security.UserRender;

/**
 * @author iori
 */
public class UserAction_Wap extends BaseAction
{

   /**
    * List Users
    * 
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
               userVO.setPassword( Cryptogram.encodeString( userVO.getPassword() ) );
            }
            userVO.setAccountId( getAccountId( request, response ) );
            userVO.setCreateBy( getUserId( request, response ) );
            userVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            userService.insertUser( userVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
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
            // ������ʷ�޸�ʱ���?????
            //userVO.setHisTitle( getHisTitle( userVO, request ) );
            // ֵ������
            if ( changePassword != null && changePassword.trim().equalsIgnoreCase( "on" ) )
            {
               userVO.update( ( UserVO ) form, true );
               // �������ļ����ܺ�������ݿ�
               userVO.setPassword( Cryptogram.encodeString( userVO.getPassword() ) );
            }
            else
            {
               userVO.update( ( UserVO ) form, false );
            }
            userVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            userService.updateUser( userVO );

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

   // ����û����Ƿ����
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
         final String username = request.getParameter( "username" );

         // ��ʼ����������
         final UserVO userVO = new UserVO();
         userVO.setAccountId( getAccountId( request, response ) );
         userVO.setUsername( username );

         // ��ѯ���ݿ��Ƿ��Ѵ��ڴ˶���
         final UserVO existUserVO = userService.login( userVO );

         if ( existUserVO != null )
         {
            out.println( "&#8226; �û��� " + username + " �Ѿ�����" );
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
            userVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            userVO.setModifyBy( getUserId( request, response ) );
            userVO.setModifyDate( new Date() );
            userService.updateUser( userVO );

            final StaffVO staffVO = staffService.getStaffVOByStaffId( userVO.getStaffId() );

            if ( staffVO != null && staffVO.getBizEmail() != null && !staffVO.getBizEmail().equals( "" ) )
            {
               // �������������ʼ�
               // Todo - Kevin
               new Mail( staffVO.getAccountId(), staffVO.getBizEmail(), "�����������óɹ� �� " + staffVO.getNameZH() + " - " + staffVO.getNameEN() + "��", "�û�����" + userVO.getUsername()
                     + "<br>���룺" + Cryptogram.decodeString( userVO.getPassword() ) + "<br>��¼��ַ�� <a href=\"http://www.kanpower.com.cn\">Kanpower ����</a>" ).send( true );

               out.println( "<div class=\"message success fadable\">��������ɹ����ѷ��������䣺" + staffVO.getBizEmail()
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
}