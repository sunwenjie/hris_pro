/*
 * Created on 2013-05-07 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.web.actions.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.EmailConfigurationVO;
import com.kan.base.service.inf.management.EmailConfigurationService;
import com.kan.base.util.KANException;
import com.kan.base.util.Mail;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class EmailConfigurationAction extends BaseAction
{

   public static String accessAction = "HRO_MANAGEMENT_MAILCONFIGURATION";

   /**
    * To email configuration modify page
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
         final EmailConfigurationService emailConfigurationService = ( EmailConfigurationService ) getService( "emailConfigurationService" );
         // ���������Ӧ����
         EmailConfigurationVO emailConfigurationVO = emailConfigurationService.getEmailConfigurationVOByAccountId( getAccountId( request, response ) );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         emailConfigurationVO.reset( null, request );

         // ����ActionForm����ǰ��
         request.setAttribute( "emailConfigurationForm", emailConfigurationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "modifyEmailConfiguration" );
   }

   /**
    * Modify email configuration
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
            // ��õ�ǰ�Ƿ���Ҫ���Ͳ����ʼ�
            final String sendTestMail = request.getParameter( "sendTestMail" );
            // ��õ�ǰ��Ҫ���͵Ĳ����ʼ���ַ
            final String testMailAddress = request.getParameter( "testMailAddress" );

            // ��ʼ��Service�ӿ�
            final EmailConfigurationService emailConfigurationService = ( EmailConfigurationService ) getService( "emailConfigurationService" );
            // ���������Ӧ����
            final EmailConfigurationVO emailConfigurationVO = emailConfigurationService.getEmailConfigurationVOByAccountId( getAccountId( request, response ) );
            // ֵ������
            emailConfigurationVO.update( ( EmailConfigurationVO ) form );
            // ��ѡ��ת��Jason����
            emailConfigurationVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            emailConfigurationService.updateEmailConfiguration( emailConfigurationVO );
            // ��ʼ�������־ö���
            constantsInit( "initEmailConfiguration", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            // ���Ͳ����ʼ�
            if ( sendTestMail != null && sendTestMail.trim().equalsIgnoreCase( "on" ) )
            {
               new Mail( getAccountId( request, response ), testMailAddress, "Hello Mail from HRIS", "Test Mail from HRIS" ).send( true );
            }

            insertlog( request, emailConfigurationVO, Operate.MODIFY, emailConfigurationVO.getConfigurationId(), sendTestMail != null
                  && sendTestMail.trim().equalsIgnoreCase( "on" ) ? "test email" : null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���޸Ľ���
      return to_objectModify( mapping, form, request, response );
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
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}