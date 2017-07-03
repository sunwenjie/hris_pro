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
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final OptionsService optionsService = ( OptionsService ) getService( "optionsService" );
         // 获得主键对应对象
         OptionsVO optionsVO = optionsService.getOptionsVOByAccountId( getAccountId( request, response ) );
         // Checkbox处理
         optionsVO.setUseBrowserLanguage( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equals( OptionsVO.TRUE ) ? "on" : "" );

         // Checkbox处理
         //optionsVO.setIndependenceTax( ( optionsVO.getIndependenceTax() != null && optionsVO.getIndependenceTax().equalsIgnoreCase( BaseVO.TRUE ) ) ? "on" : "" );

         // 刷新对象，初始化对象列表及国际化
         optionsVO.reset( null, request );

         optionsVO.setSubAction( VIEW_OBJECT );

         // 传回ActionForm对象到前端
         request.setAttribute( "optionsForm", optionsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final OptionsService optionsService = ( OptionsService ) getService( "optionsService" );
            // 获得主键对应对象
            final OptionsVO optionsVO = optionsService.getOptionsVOByAccountId( getAccountId( request, response ) );
            // 值对象复制
            optionsVO.update( ( OptionsVO ) form );
            // Checkbox处理
            if ( optionsVO.getUseBrowserLanguage() != null && optionsVO.getUseBrowserLanguage().equalsIgnoreCase( "on" ) )
            {
               optionsVO.setUseBrowserLanguage( OptionsVO.TRUE );
            }
            else
            {
               optionsVO.setUseBrowserLanguage( OptionsVO.FALSE );
            }

            optionsVO.setModifyBy( getUserId( request, response ) );
            // 手机模块权限
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );
            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               optionsVO.setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }
            // 处理checkBox
            //optionsVO.setIndependenceTax( ( optionsVO.getIndependenceTax() != null && optionsVO.getIndependenceTax().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE : BaseVO.FALSE );
            // 修改对象
            optionsVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
            optionsVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );
            optionsService.updateOptionsSyncEntity( optionsVO );

            // 初始化常量持久对象
            constantsInit( "initOptions", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, optionsVO, Operate.MODIFY, optionsVO.getOptionsId(), null );
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