/*
 * Created on 2013-05-13
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.OptionsVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.service.inf.system.HRMAccountService;
import com.kan.base.service.inf.system.HROAccountService;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.service.inf.system.PlatFromAccountService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class AccountAction extends BaseAction
{

   public static String accessAction = "HRO_SYS_ACCOUNT";

   // 1:HRO##2:HRM##3:PLATFORM
   private final String TYPE_HRO = "1";

   private final String TYPE_HRM = "2";

   private final String TYPE_PLATFORM = "3";

   /**
    * List AccountBaseViews by Jason format
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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

         // ��ʼ��Account Service
         final AccountService accountService = ( AccountService ) getService( "accountService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( accountService.getAccountBaseViews() );

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

   /**
    * List Accounts
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // ���Action Form
         final AccountVO accountVO = ( AccountVO ) form;

         // �����Action��ɾ���û��б�
         if ( accountVO != null && accountVO.getSubAction() != null && accountVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // �����Action�Ǽ����û��б�
         else if ( accountVO != null && accountVO.getSubAction() != null && accountVO.getSubAction().equalsIgnoreCase( ACTIVE_OBJECTS ) )
         {
            // ���ü����û��б��Action
            active_objectList( mapping, form, request, response );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder accountHolder = new PagedListHolder();

         // ���뵱ǰҳ
         accountHolder.setPage( page );
         // ���뵱ǰֵ����
         accountHolder.setObject( accountVO );
         // ����ҳ���¼����
         accountHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         accountService.getAccountVOsByCondition( accountHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( accountHolder, request );

         // Holder��д��Request����
         request.setAttribute( "accountHolder", accountHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listAccountTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listAccount" );
   }

   /**
    * To account modify page
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // ��õ�ǰ����
         String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );
         // ���������Ӧ����
         AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );
         // Checkbox����
         accountVO.setCanAdvBizEmail( accountVO.getCanAdvBizEmail() != null && accountVO.getCanAdvBizEmail().equals( AccountVO.TRUE ) ? "on" : "" );
         accountVO.setCanAdvPersonalEmail( accountVO.getCanAdvPersonalEmail() != null && accountVO.getCanAdvPersonalEmail().equals( AccountVO.TRUE ) ? "on" : "" );
         accountVO.setCanAdvBizSMS( accountVO.getCanAdvBizSMS() != null && accountVO.getCanAdvBizSMS().equals( AccountVO.TRUE ) ? "on" : "" );
         accountVO.setCanAdvPersonalSMS( accountVO.getCanAdvPersonalSMS() != null && accountVO.getCanAdvPersonalSMS().equals( AccountVO.TRUE ) ? "on" : "" );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         accountVO.reset( null, request );

         // ���City Id�������Province Id
         if ( accountVO.getCityId() != null && !accountVO.getCityId().trim().equals( "" ) && !accountVO.getCityId().trim().equals( "0" ) )
         {
            accountVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( accountVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            accountVO.setCityIdTemp( accountVO.getCityId() );
         }

         accountVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "accountForm", accountVO );
         // ϵͳģ����
         request.setAttribute( "selectedModules", getselectedModules( accountId ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageAccount" );
   }

   
   // ��ʼ��Module����
   private List< ModuleVO > getselectedModules( final String accountId ) throws KANException
   {
      try
      {
         // Clear First
         final List< ModuleVO > tempModuleVOs = new ArrayList< ModuleVO >();
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         // ��ʼ��Module VO
         final List< Object > moduleVOs = moduleService.getAccountModuleVOsByAccountId( accountId );

         if ( moduleVOs != null )
         {
            // ����
            for ( Object moduleVOObject : moduleVOs )
            {
               tempModuleVOs.add( ( ModuleVO ) moduleVOObject );
            }
         }

         return tempModuleVOs;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To account modify page part
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify_internal( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // ��õ�ǰ����
         String accountId = BaseAction.getAccountId( request, response );
         // ���������Ӧ����
         AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         accountVO.reset( null, request );

         // ���City Id�������Province Id
         if ( accountVO.getCityId() != null && !accountVO.getCityId().trim().equals( "" ) && !accountVO.getCityId().trim().equals( "0" ) )
         {
            accountVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( accountVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            accountVO.setCityIdTemp( accountVO.getCityId() );
         }

         accountVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "accountForm", accountVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageAccountInternal" );
   }

   /**
    * To account new page
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
      ( ( AccountVO ) form ).setStatus( AccountVO.TRUE );
      ( ( AccountVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageAccount" );
   }

   /**
    * Add account
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
            final AccountService accountService = ( AccountService ) getService( "accountService" );
            // ���ActionForm
            final AccountVO accountVO = ( AccountVO ) form;
            // Checkbox����
            if ( accountVO.getCanAdvBizEmail() != null && accountVO.getCanAdvBizEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvBizSMS() != null && accountVO.getCanAdvBizSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizSMS( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalEmail() != null && accountVO.getCanAdvPersonalEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalSMS() != null && accountVO.getCanAdvPersonalSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.FALSE );
            }

            // ���ó�ʼ����Ϣ
            accountVO.setInitialized( AccountVO.FALSE );
            // ��ȡ��¼�û�
            accountVO.setCreateBy( getUserId( request, response ) );
            accountVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            accountService.insertAccount( accountVO );
            // ��ʼ�������־ö���
            constantsInit( "initModule", accountVO.getAccountId() );
            constantsInit( "initStaff", accountVO.getAccountId() );

            // ������ӳɹ����
            success( request );
         }

         // ���Form����
         ( ( AccountVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify account part
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_internal( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final AccountService accountService = ( AccountService ) getService( "accountService" );
            // ��õ�ǰ����
            final String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );
            // ���������Ӧ����
            final AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );

            // װ�ؽ��洫ֵ
            accountVO.update( form, true );
            // ��Ƭ
            final String[] imageFileArray = request.getParameterValues( "imageFileArray" );
            if ( imageFileArray == null )
            {
               accountVO.setImageFile( null );
            }
            else
            {
               String imageFileString = "";
               for ( String s : imageFileArray )
               {
                  imageFileString += s;
                  imageFileString += "##";
               }
               accountVO.setImageFile( imageFileString.length() > 0 ? imageFileString.substring( 0, imageFileString.length() - 2 ) : null );
            }
            // ��ȡ��¼�û�
            accountVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            accountService.updateAccount( accountVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( AccountVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify_internal( mapping, form, request, response );
   }

   /**
    * Modify account
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
            final AccountService accountService = ( AccountService ) getService( "accountService" );
            // ��õ�ǰ����
            final String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );
            // ���������Ӧ����
            final AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );

            // װ�ؽ��洫ֵ
            accountVO.update( form );
            // Checkbox����
            if ( accountVO.getCanAdvBizEmail() != null && accountVO.getCanAdvBizEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvBizSMS() != null && accountVO.getCanAdvBizSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizSMS( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalEmail() != null && accountVO.getCanAdvPersonalEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalSMS() != null && accountVO.getCanAdvPersonalSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.FALSE );
            }
            // ��ȡ��¼�û�
            accountVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            accountService.updateAccount( accountVO );
            // ��ʼ�������־ö���
            constantsInit( "initModule", accountId );
            constantsInit( "initStaff", accountId );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( AccountVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete account
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         final AccountVO accountVO = new AccountVO();
         // ��õ�ǰ����
         String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );

         // ɾ��������Ӧ����
         accountVO.setAccountId( accountId );
         accountVO.setModifyBy( getUserId( request, response ) );
         accountService.deleteAccount( accountVO );

         // ��ʼ�������־ö���
         constantsInit( "initModule", accountVO.getAccountId() );
         constantsInit( "initStaff", accountVO.getAccountId() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete account list
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // ���Action Form
         final AccountVO accountVO = ( AccountVO ) form;
         // ����ѡ�е�ID
         if ( accountVO.getSelectedIds() != null && !accountVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : accountVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               accountVO.setAccountId( selectedId );
               accountVO.setModifyBy( getUserId( request, response ) );
               accountService.deleteAccount( accountVO );
            }
         }

         // ��ʼ�������־ö���
         constantsInit( "initModule", accountVO.getAccountId() );
         constantsInit( "initStaff", accountVO.getAccountId() );

         // ���Selected IDs����Action
         accountVO.setSelectedIds( "" );
         accountVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Active Account List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void active_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final AccountService accountService = ( AccountService ) getService( "accountService" );

         final HROAccountService hroAccountService = ( HROAccountService ) getService( "hroAccountService" );
         final HRMAccountService hrmAccountService = ( HRMAccountService ) getService( "hrmAccountService" );
         final PlatFromAccountService platFromAccountService = ( PlatFromAccountService ) getService( "platFormAccountService" );
         // ���Action Form
         AccountVO accountVO = ( AccountVO ) form;
         // ����ѡ�е�ID
         if ( accountVO.getSelectedIds() != null && !accountVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : accountVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               accountVO.setAccountId( selectedId );
               accountVO.setCreateBy( getUserId( request, response ) );
               accountVO.setModifyBy( getUserId( request, response ) );

               final AccountVO targetAccountVO = accountService.getAccountVOByAccountId( accountVO.getAccountId() );
               if ( targetAccountVO.getAccountType().equals( TYPE_HRO ) )
               {
                  hroAccountService.initAccount( targetAccountVO );
               }
               else if ( targetAccountVO.getAccountType().equals( TYPE_HRM ) )
               {
                  hrmAccountService.initAccount( targetAccountVO );
               }
               else if ( targetAccountVO.getAccountType().equals( TYPE_PLATFORM ) )
               {
                  platFromAccountService.initAccount( targetAccountVO );
               }
            }
         }
         // ���Selected IDs����Action
         accountVO.setSelectedIds( "" );
         accountVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}