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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ShareFolderConfigurationService shareFolderConfigurationService = ( ShareFolderConfigurationService ) getService( "shareFolderConfigurationService" );
         // 获得主键对应对象
         ShareFolderConfigurationVO shareFolderConfigurationVO = shareFolderConfigurationService.getShareFolderConfigurationVOByAccountId( getAccountId( request, response ) );
         // 刷新对象，初始化对象列表及国际化
         shareFolderConfigurationVO.reset( null, request );

         // 传回ActionForm对象到前端
         request.setAttribute( "shareFolderConfigurationForm", shareFolderConfigurationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ShareFolderConfigurationService shareFolderConfigurationService = ( ShareFolderConfigurationService ) getService( "shareFolderConfigurationService" );
            // 获得主键对应对象
            final ShareFolderConfigurationVO shareFolderConfigurationVO = shareFolderConfigurationService.getShareFolderConfigurationVOByAccountId( getAccountId( request, response ) );
            // 值对象复制
            shareFolderConfigurationVO.update( ( ShareFolderConfigurationVO ) form );
            // 多选项转成Jason对象
            shareFolderConfigurationVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            shareFolderConfigurationService.updateShareFolderConfiguration( shareFolderConfigurationVO );
            // 初始化常量持久对象
            constantsInit( "initShareFolderConfiguration", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, shareFolderConfigurationVO, Operate.MODIFY, shareFolderConfigurationVO.getConfigurationId(), null );
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