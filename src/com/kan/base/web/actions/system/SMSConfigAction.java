/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.SMSConfigVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SMSConfigService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class SMSConfigAction extends BaseAction
{

   /**
    * List SMSConfig
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
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         // ���Action Form
         final SMSConfigVO smsConfigVO = ( SMSConfigVO ) form;

         // �����Action��ɾ���б�
         if ( smsConfigVO != null && smsConfigVO.getSubAction() != null && smsConfigVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���б��Action
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder smsConfigHolder = new PagedListHolder();

         // ���뵱ǰҳ
         smsConfigHolder.setPage( page );
         // ���뵱ǰֵ����
         smsConfigHolder.setObject( smsConfigVO );
         // ����ҳ���¼����
         smsConfigHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         smsConfigService.getSMSConfigVOsByCondition( smsConfigHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( smsConfigHolder, request );

         // Holder��д��Request����
         request.setAttribute( "smsConfigHolder", smsConfigHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listSMSConfigTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listSMSConfig" );
   }

   /**
    * To config modify page
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
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         // ��õ�ǰ����
         String configId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "configId" ), "GBK" ) );
         // ���������Ӧ����
         SMSConfigVO smsConfigVO = smsConfigService.getSMSConfigVOByConfigId( configId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         smsConfigVO.reset( null, request );
         // �⿪������ǰ�˴���
         smsConfigVO.setPassword( Cryptogram.decodeString( smsConfigVO.getPassword() ) );
         smsConfigVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "smsConfigForm", smsConfigVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageSMSConfig" );
   }

   /**
    * To config new page
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
      ( ( SMSConfigVO ) form ).setStatus( SMSConfigVO.TRUE );
      ( ( SMSConfigVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageSMSConfig" );
   }

   /**
    * Add config
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
            final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
            // ���ActionForm
            final SMSConfigVO smsConfigVO = ( SMSConfigVO ) form;

            // ������Ҫ���ܴ���
            smsConfigVO.setPassword( Cryptogram.encodeString( smsConfigVO.getPassword() ) );
            // ��ȡ��¼�û�
            smsConfigVO.setCreateBy( getUserId( request, response ) );
            smsConfigVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            smsConfigService.insertSMSConfig( smsConfigVO );
         }

         ( ( SMSConfigVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify config
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
            final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
            // ��õ�ǰ����
            final String configId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "configId" ), "GBK" ) );
            // ���������Ӧ����
            final SMSConfigVO smsConfigVO = smsConfigService.getSMSConfigVOByConfigId( configId );

            // װ�ؽ��洫ֵ
            smsConfigVO.update( ( SMSConfigVO ) form );
            // ������Ҫ���ܴ���
            smsConfigVO.setPassword( Cryptogram.encodeString( smsConfigVO.getPassword() ) );

            // ��ȡ��¼�û�
            smsConfigVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            smsConfigService.updateSMSConfig( smsConfigVO );
         }

         ( ( SMSConfigVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete config
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
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         final SMSConfigVO smsConfigVO = new SMSConfigVO();
         // ��õ�ǰ����
         String configId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "configId" ), "GBK" ) );

         // ɾ��������Ӧ����
         smsConfigVO.setConfigId( configId );
         smsConfigVO.setModifyBy( getUserId( request, response ) );
         smsConfigService.deleteSMSConfig( smsConfigVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete config list
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
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         // ���Action Form
         SMSConfigVO smsConfigVO = ( SMSConfigVO ) form;
         // ����ѡ�е�ID
         if ( smsConfigVO.getSelectedIds() != null && !smsConfigVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : smsConfigVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               smsConfigVO.setConfigId( selectedId );
               smsConfigVO.setModifyBy( getUserId( request, response ) );
               smsConfigService.deleteSMSConfig( smsConfigVO );
            }
         }
         // ���Selected IDs����Action
         smsConfigVO.setSelectedIds( "" );
         smsConfigVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}