package com.kan.hro.web.actions.biz.vendor;

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
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorUserVO;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;
import com.kan.hro.service.inf.biz.vendor.VendorUserService;

public class VendorUserAction extends BaseAction
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
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // ���Action Form
         final VendorUserVO vendorUserVO = ( VendorUserVO ) form;

         // �����Action��ɾ���û��б�
         if ( vendorUserVO.getSubAction() != null && vendorUserVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( vendorUserVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder vendorUserHolder = new PagedListHolder();

         // ���뵱ǰҳ
         vendorUserHolder.setPage( page );
         // ���뵱ǰֵ����
         vendorUserHolder.setObject( vendorUserVO );
         // ����ҳ���¼����
         vendorUserHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         vendorUserService.getVendorUserVOsByCondition( vendorUserHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( vendorUserHolder, request );

         // Holder��д��Request����
         request.setAttribute( "vendorUserHolder", vendorUserHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listVendorUserTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listVendorUser" );
   }

   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // ��õ�ǰ����
         String vendorUserId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "vendorUserId" ), "UTF-8" ) );
         // ���������Ӧ����
         VendorUserVO vendorUserVO = vendorUserService.getVendorUserVOByVendorUserId( vendorUserId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         vendorUserVO.reset( null, request );
         //vendorUserVO.setStaffName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNameByStaffId( vendorUserVO.getUserId() ) );
         vendorUserVO.setPassword( "" );
         vendorUserVO.setSubAction( VIEW_OBJECT );

         // ����ActionForm����ǰ��
         request.setAttribute( "vendorUserForm", vendorUserVO );

         //         // ����޸���ʷ
         //         final List< Object > historyVendorUserVOs = vendorUserService.getHistoryVendorUserVOsByUserId( vendorUserId );
         //         // ������ʷ�޸ļ�¼�б�ǰ��
         //         request.setAttribute( "historyVendorUserVOs", historyVendorUserVOs );
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
      ( ( VendorUserVO ) form ).setStatus( VendorUserVO.TRUE );
      ( ( VendorUserVO ) form ).setSubAction( CREATE_OBJECT );

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
            final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
            // ���ActionForm
            final VendorUserVO vendorUserVO = ( VendorUserVO ) form;
            // �������ļ����ܺ�������ݿ�
            if ( vendorUserVO.getPassword() != null && !vendorUserVO.getPassword().trim().equals( "" ) )
            {
               vendorUserVO.setPassword( Cryptogram.encodeString( vendorUserVO.getPassword() ) );
            }
            vendorUserVO.setAccountId( getAccountId( request, response ) );
            vendorUserVO.setCreateBy( getUserId( request, response ) );
            vendorUserVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            vendorUserService.insertVendorUser( vendorUserVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
         // ���Form����
         ( ( VendorUserVO ) form ).reset();
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
      // No Use
      return null;
   }

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
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // ��õ�ǰ����
         final String vendorContactId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorContactId" ) );
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );

         // ��ȡ��ʷ�û�����
         final VendorUserVO tempVendorUserVO = new VendorUserVO();
         VendorUserVO vendorUserVO = new VendorUserVO();
         tempVendorUserVO.setUsername( vendorContactVO.getUsername() );
         tempVendorUserVO.setAccountId( vendorContactVO.getAccountId() );

         if ( vendorContactId != null && !vendorContactId.trim().equals( "" ) && vendorContactVO.getUsername() != null )
         {
            vendorUserVO = vendorUserService.getVendorUserVOByUsername( tempVendorUserVO );
         }

         // ����û���ΪNULL�������û�����
         if ( vendorUserVO != null )
         {
            vendorUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            vendorUserVO.setModifyBy( getUserId( request, response ) );
            vendorUserVO.setModifyDate( new Date() );
            vendorUserService.updateVendorUser( vendorUserVO );

            final String email = request.getParameter( "email" );

            if ( KANUtil.filterEmpty( email ) != null )
            {
               // �������������ʼ�
               final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
               new Mail( vendorContactVO.getAccountId(), email, BaseAction.getTitle( request, response ) + "�������óɹ� �� " + vendorContactVO.getNameZH() + " - " + vendorContactVO.getNameEN() + "��", "��Ӧ��ID��" + vendorUserVO.getVendorId() 
                     + "<br>�û�����"
                     + vendorUserVO.getUsername()
                     + "<br>���룺"
                     + Cryptogram.decodeString( vendorUserVO.getPassword() )
                     + "<br>��¼��ַ�� <a href=\""
                     + domain + "/" + KANConstants.PROJECT_NAME + "/" + "logonv.do"
                     + "\">"
                     + BaseAction.getTitle( request, response ) + "</a>" ).send( true );

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
      // No Use
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
      // No Use
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
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // ��ȡ�����Username
         final String username = request.getParameter( "username" );

         // ��ʼ����������
         final VendorUserVO vendorUserVO = new VendorUserVO();
         vendorUserVO.setAccountId( getAccountId( request, response ) );
         vendorUserVO.setUsername( username );

         // ��ѯ���ݿ��Ƿ��Ѵ��ڴ˶���
         final VendorUserVO existVendorUserVO = vendorUserService.getVendorUserVOByUsername( vendorUserVO );

         if ( existVendorUserVO != null )
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

}