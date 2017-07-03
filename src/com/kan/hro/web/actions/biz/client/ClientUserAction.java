/*
 * Created on 2003-04-12
 */
package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.service.inf.biz.client.ClientContactService;
import com.kan.hro.service.inf.biz.client.ClientUserService;

public class ClientUserAction extends BaseAction
{

   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // ���Action Form
         final ClientUserVO clientContactVO = ( ClientUserVO ) form;

         // �����Action��ɾ���û��б�
         if ( clientContactVO.getSubAction() != null && clientContactVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientContactVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder clientContactHolder = new PagedListHolder();

         // ���뵱ǰҳ
         clientContactHolder.setPage( page );
         // ���뵱ǰֵ����
         clientContactHolder.setObject( clientContactVO );
         // ����ҳ���¼����
         clientContactHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientUserService.getClientUserVOsByCondition( clientContactHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( clientContactHolder, request );

         // Holder��д��Request����
         request.setAttribute( "clientContactHolder", clientContactHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listClientUserTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listClientUser" );
   }

   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // ��õ�ǰ����
         String clientContactId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "clientContactId" ), "UTF-8" ) );
         // ���������Ӧ����
         ClientUserVO clientContactVO = clientUserService.getClientUserVOByUserId( clientContactId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         clientContactVO.reset( null, request );
         //clientContactVO.setStaffName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNameByStaffId( clientContactVO.getUserId() ) );
         clientContactVO.setPassword( "" );
         clientContactVO.setSubAction( VIEW_OBJECT );

         // ����ActionForm����ǰ��
         request.setAttribute( "clientContactForm", clientContactVO );

         //         // ����޸���ʷ
         //         final List< Object > historyClientUserVOs = clientUserService.getHistoryClientUserVOsByUserId( clientContactId );
         //         // ������ʷ�޸ļ�¼�б�ǰ��
         //         request.setAttribute( "historyClientUserVOs", historyClientUserVOs );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageUser" );
   }

   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( ClientUserVO ) form ).setStatus( ClientUserVO.TRUE );
      ( ( ClientUserVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageUser" );
   }

   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
            // ���ActionForm
            final ClientUserVO clientContactVO = ( ClientUserVO ) form;
            // �������ļ����ܺ�������ݿ�
            if ( clientContactVO.getPassword() != null && !clientContactVO.getPassword().trim().equals( "" ) )
            {
               clientContactVO.setPassword( Cryptogram.encodeString( clientContactVO.getPassword() ) );
            }
            clientContactVO.setAccountId( getAccountId( request, response ) );
            clientContactVO.setCreateBy( getUserId( request, response ) );
            clientContactVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            clientUserService.insertClientUser( clientContactVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
         // ���Form����
         ( ( ClientUserVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return null;
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
      //      try
      //      {
      //         // ��ʼ��Service�ӿ�
      //         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
      //         final ClientUserVO clientContactVO = new ClientUserVO();
      //         // ��õ�ǰ����
      //         String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );
      //
      //         // ɾ��������Ӧ����
      //         clientContactVO.setUserId( userId );
      //         clientContactVO.setModifyBy( getUserId( request, response ) );
      //         clientUserService.deleteUser( clientContactVO );
      //      }
      //      catch ( final Exception e )
      //      {
      //         throw new KANException( e );
      //      }
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
         // ��ȡ�����Username
         final String username = request.getParameter( "username" );
         // ��ȡ�ͻ�ID
         final String clientId = request.getParameter( "clientId" );
         // ��ʼ��Service�ӿ�
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // ��ʼ��ClientUserVO
         final ClientUserVO clientUserVO = new ClientUserVO();

         if ( KANUtil.filterEmpty( clientId ) == null )
         {
            out.println( "&#8226; ����ѡ��ͻ���" );
         }
         else
         {
            clientUserVO.setClientId( clientId );
            clientUserVO.setUsername( username );

            if ( clientUserService.isExistByCondition( clientUserVO ) )
            {
               out.println( "&#8226; �û��� " + username + " �Ѿ����ڣ�" );
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
      return null;
   }

   // ��ȡ��ʷ���ļ�¼
   public ActionForward get_history_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // ��ʼ��Service�ӿ�
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
         // ��õ�ǰ����
         final String staffId = request.getParameter( "staffId" );

         // ��ȡ��ʷ�û�����
         ClientUserVO userVO = null;
         if ( staffId != null && !staffId.trim().equals( "" ) )
         {
            userVO = clientUserService.getClientUserVOByUserId( staffId );
         }

         // ����û���ΪNULL�������û�����
         if ( userVO != null )
         {
            ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( userVO.getClientContactId() );
            userVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            userVO.setModifyBy( getUserId( request, response ) );
            userVO.setModifyDate( new Date() );
            clientUserService.updateClientUser( userVO );
            userVO.setRole( KANConstants.ROLE_CLIENT );
            final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? ( KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME )
                  : ( KANConstants.HTTP + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME );
            final String url = domain + "/logonc.do";
            String accountName = BaseAction.getPropertyFromCookie( request, response, "accountName" );
            new Mail( clientContactVO.getAccountId(), clientContactVO.getBizEmail(), BaseAction.getTitle( request, response ) + "�������óɹ� ", "�˻���" + accountName + "<br>�û�����"
                  + clientContactVO.getUsername() + "<br>���룺" + Cryptogram.decodeString( userVO.getPassword() ) + "<br>��¼��ַ�� <a href=\"" + url + "\">"
                  + BaseAction.getTitle( request, response ) + "</a>" ).send( true );

            out.println( "<div class=\"message success fadable\">��������ɹ����ѷ��������䣺" + clientContactVO.getBizEmail()
                  + "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );

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