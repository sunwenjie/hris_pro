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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         // 获得Action Form
         final SMSConfigVO smsConfigVO = ( SMSConfigVO ) form;

         // 如果子Action是删除列表
         if ( smsConfigVO != null && smsConfigVO.getSubAction() != null && smsConfigVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除列表的Action
            delete_objectList( mapping, form, request, response );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder smsConfigHolder = new PagedListHolder();

         // 传入当前页
         smsConfigHolder.setPage( page );
         // 传入当前值对象
         smsConfigHolder.setObject( smsConfigVO );
         // 设置页面记录条数
         smsConfigHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         smsConfigService.getSMSConfigVOsByCondition( smsConfigHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( smsConfigHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "smsConfigHolder", smsConfigHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listSMSConfigTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         // 获得当前主键
         String configId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "configId" ), "GBK" ) );
         // 获得主键对应对象
         SMSConfigVO smsConfigVO = smsConfigService.getSMSConfigVOByConfigId( configId );
         // 刷新对象，初始化对象列表及国际化
         smsConfigVO.reset( null, request );
         // 解开密码往前端传送
         smsConfigVO.setPassword( Cryptogram.decodeString( smsConfigVO.getPassword() ) );
         smsConfigVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "smsConfigForm", smsConfigVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( SMSConfigVO ) form ).setStatus( SMSConfigVO.TRUE );
      ( ( SMSConfigVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
            // 获得ActionForm
            final SMSConfigVO smsConfigVO = ( SMSConfigVO ) form;

            // 密码需要加密处理
            smsConfigVO.setPassword( Cryptogram.encodeString( smsConfigVO.getPassword() ) );
            // 获取登录用户
            smsConfigVO.setCreateBy( getUserId( request, response ) );
            smsConfigVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            smsConfigService.insertSMSConfig( smsConfigVO );
         }

         ( ( SMSConfigVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
            // 获得当前主键
            final String configId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "configId" ), "GBK" ) );
            // 获得主键对应对象
            final SMSConfigVO smsConfigVO = smsConfigService.getSMSConfigVOByConfigId( configId );

            // 装载界面传值
            smsConfigVO.update( ( SMSConfigVO ) form );
            // 密码需要加密处理
            smsConfigVO.setPassword( Cryptogram.encodeString( smsConfigVO.getPassword() ) );

            // 获取登录用户
            smsConfigVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            smsConfigService.updateSMSConfig( smsConfigVO );
         }

         ( ( SMSConfigVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化Service接口
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         final SMSConfigVO smsConfigVO = new SMSConfigVO();
         // 获得当前主键
         String configId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "configId" ), "GBK" ) );

         // 删除主键对应对象
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
         // 初始化Service接口
         final SMSConfigService smsConfigService = ( SMSConfigService ) getService( "smsConfigService" );
         // 获得Action Form
         SMSConfigVO smsConfigVO = ( SMSConfigVO ) form;
         // 存在选中的ID
         if ( smsConfigVO.getSelectedIds() != null && !smsConfigVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : smsConfigVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               smsConfigVO.setConfigId( selectedId );
               smsConfigVO.setModifyBy( getUserId( request, response ) );
               smsConfigService.deleteSMSConfig( smsConfigVO );
            }
         }
         // 清除Selected IDs和子Action
         smsConfigVO.setSelectedIds( "" );
         smsConfigVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}