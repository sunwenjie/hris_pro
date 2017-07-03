/*
 * Created on 2013-05-07
 */
package com.kan.base.web.actions.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ShareFolderConfigurationVO;
import com.kan.base.service.inf.management.ShareFolderConfigurationService;
import com.kan.base.util.KANException;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class ShareFolderConfigurationAction extends BaseAction
{

   public static String accessAction = "HRO_MANAGEMENT_SHAREFOLDERCONFIGURATION";

   /**
    * To shareFolder configuration modify page
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
         final ShareFolderConfigurationService shareFolderConfigurationService = ( ShareFolderConfigurationService ) getService( "shareFolderConfigurationService" );
         // ���������Ӧ����
         ShareFolderConfigurationVO shareFolderConfigurationVO = shareFolderConfigurationService.getShareFolderConfigurationVOByAccountId( getAccountId( request, response ) );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         shareFolderConfigurationVO.reset( null, request );

         // ����ActionForm����ǰ��
         request.setAttribute( "shareFolderConfigurationForm", shareFolderConfigurationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "modifyShareFolderConfiguration" );
   }

   /**
    * Modify shareFolder configuration
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
            final ShareFolderConfigurationService shareFolderConfigurationService = ( ShareFolderConfigurationService ) getService( "shareFolderConfigurationService" );
            // ���������Ӧ����
            final ShareFolderConfigurationVO shareFolderConfigurationVO = shareFolderConfigurationService.getShareFolderConfigurationVOByAccountId( getAccountId( request, response ) );
            // ֵ������
            shareFolderConfigurationVO.update( ( ShareFolderConfigurationVO ) form );
            // ��ѡ��ת��Jason����
            shareFolderConfigurationVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            shareFolderConfigurationService.updateShareFolderConfiguration( shareFolderConfigurationVO );
            // ��ʼ�������־ö���
            constantsInit( "initShareFolderConfiguration", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, shareFolderConfigurationVO, Operate.MODIFY, shareFolderConfigurationVO.getConfigurationId(), null );
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