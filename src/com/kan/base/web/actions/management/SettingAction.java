package com.kan.base.web.actions.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SettingVO;
import com.kan.base.service.inf.management.SettingService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Iori Luo
 */
public class SettingAction extends BaseAction
{
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final SettingService settingService = ( SettingService ) getService( "settingService" );
         // ���������Ӧ����
         SettingVO settingVO = settingService.getSettingVOByUserId( getUserId( request, response ) );

         // ���Ϊ�����ǵ�һ�ν��룬��ʼ������ģ��
         if ( settingVO == null )
         {
            settingVO = new SettingVO();
            settingVO.setAccountId( getAccountId( request, response ) );

            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               settingVO.setCorpId( getCorpId( request, response ) );
            }

            settingVO.setUserId( getUserId( request, response ) );

            // ���ø���
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               settingVO.resetInHouseInit();
            }
            else
            {
               settingVO.resetInit();
            }

            settingVO.setCreateBy( getUserId( request, response ) );
            settingService.insertSetting( settingVO );
         }
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         settingVO.reset( null, request );

         // ����ActionForm����ǰ��
         request.setAttribute( "settingVO", settingVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "modifySetting" );
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
            // ��ʼ��Service�ӿ�
            final SettingService settingService = ( SettingService ) getService( "settingService" );
            SettingVO settingVO = settingService.getSettingVOByUserId( getUserId( request, response ) );
            settingVO.update( ( ( SettingVO ) form ) );
            settingVO.setModifyBy( getUserId( request, response ) );
            settingService.updateSetting( settingVO );

            success( request, MESSAGE_TYPE_UPDATE );
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