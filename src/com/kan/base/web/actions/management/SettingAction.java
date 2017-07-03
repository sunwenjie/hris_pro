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
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final SettingService settingService = ( SettingService ) getService( "settingService" );
         // 获得主键对应对象
         SettingVO settingVO = settingService.getSettingVOByUserId( getUserId( request, response ) );

         // 如果为空则是第一次进入，初始化个人模板
         if ( settingVO == null )
         {
            settingVO = new SettingVO();
            settingVO.setAccountId( getAccountId( request, response ) );

            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               settingVO.setCorpId( getCorpId( request, response ) );
            }

            settingVO.setUserId( getUserId( request, response ) );

            // 设置各项
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
         // 刷新对象，初始化对象列表及国际化
         settingVO.reset( null, request );

         // 传回ActionForm对象到前端
         request.setAttribute( "settingVO", settingVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
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

      // 跳转到修改界面
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