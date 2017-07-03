/*
 * Created on 2013-05-06
 */
package com.kan.base.web.actions.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.OptionsVO;
import com.kan.base.service.inf.management.OptionsService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class OptionsAction extends BaseAction
{
   public static String accessAction = "HRO_MANAGEMENT_OPTIONS";

   /**
    * To options modify page
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
         final OptionsService optionsService = ( OptionsService ) getService( "optionsService" );
         // ���������Ӧ����
         OptionsVO optionsVO = optionsService.getOptionsVOByAccountId( getAccountId( request, response ) );
         // Checkbox����
         optionsVO.setUseBrowserLanguage( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equals( OptionsVO.TRUE ) ? "on" : "" );

         // Checkbox����
         //optionsVO.setIndependenceTax( ( optionsVO.getIndependenceTax() != null && optionsVO.getIndependenceTax().equalsIgnoreCase( BaseVO.TRUE ) ) ? "on" : "" );

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         optionsVO.reset( null, request );

         optionsVO.setSubAction( VIEW_OBJECT );

         // ����ActionForm����ǰ��
         request.setAttribute( "optionsForm", optionsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "modifyOptions" );
   }

   /**
    * Modify options
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
            final OptionsService optionsService = ( OptionsService ) getService( "optionsService" );
            // ���������Ӧ����
            final OptionsVO optionsVO = optionsService.getOptionsVOByAccountId( getAccountId( request, response ) );
            // ֵ������
            optionsVO.update( ( OptionsVO ) form );
            // Checkbox����
            if ( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equalsIgnoreCase( "on" ) )
            {
               optionsVO.setUseBrowserLanguage( OptionsVO.TRUE );
            }
            else
            {
               optionsVO.setUseBrowserLanguage( OptionsVO.FALSE );
            }

            optionsVO.setModifyBy( getUserId( request, response ) );
            // �ֻ�ģ��Ȩ��
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );
            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               optionsVO.setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }
            // ����checkBox
            //optionsVO.setIndependenceTax( ( optionsVO.getIndependenceTax() != null && optionsVO.getIndependenceTax().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE : BaseVO.FALSE );
            // �޸Ķ���
            optionsVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
            optionsVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );
            optionsService.updateOptionsSyncEntity( optionsVO );

            // ��ʼ�������־ö���
            constantsInit( "initOptions", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, optionsVO, Operate.MODIFY, optionsVO.getOptionsId(), null );
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