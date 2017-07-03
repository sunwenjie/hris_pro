package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.service.inf.biz.client.ClientContractPropertyService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientContractPropertyAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientContractPropertyAction extends BaseAction
{

   /**
    * List client Order Header
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
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // 获得Action Form
         final ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) form;
         clientContractPropertyVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientContractPropertyVO.getSubAction() != null && clientContractPropertyVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientContractPropertyVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientContractPropertyVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientContractPropertyVO.getSubAction() != null )
         {
            subAction = clientContractPropertyVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientContractPropertyService.getClientContractPropertyVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( pageListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pageListHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // 初始化PrintWrite对象
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_CONTRACT_PROPERTY" ) );
               out.flush();
               out.close();
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listClientContractProperty" );
   }

   /**
    * To object New
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
      ( ( ClientContractPropertyVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageClientContractProperty" );
   }

   /**
    * To Object Modify
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
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // 获得当前主键
         String clientContractPropertyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 根据主键查找对应的clientContractPropertyVO
         final ClientContractPropertyVO clientContractPropertyVO = clientContractPropertyService.getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );

         // 刷新VO对象，初始化对象列表及国际化
         clientContractPropertyVO.reset( null, request );
         // 设置Sub Action
         clientContractPropertyVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientContractPropertyForm", clientContractPropertyVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientContractProperty" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
            // 获得ActionForm
            final ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) form;
            // 获取登录用户
            clientContractPropertyVO.setAccountId( getAccountId( request, response ) );
            clientContractPropertyVO.setCreateBy( getUserId( request, response ) );
            clientContractPropertyVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            clientContractPropertyService.insertClientContractProperty( clientContractPropertyVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         // 清空Form条件
         ( ( ClientContractPropertyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify Object
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
            final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );

            // 获得当前主键
            final String clientContractPropertyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得主键对应对象
            final ClientContractPropertyVO clientContractPropertyVO = clientContractPropertyService.getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );

            // 获取登录用户
            clientContractPropertyVO.update( ( ClientContractPropertyVO ) form );
            clientContractPropertyVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            clientContractPropertyService.updateClientContractProperty( clientContractPropertyVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form条件
         ( ( ClientContractPropertyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete Object
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
      // no use
   }

   /**
    * Delete Object List
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
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // 获得Action Form
         ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) form;

         // 存在选中的ID
         if ( clientContractPropertyVO.getSelectedIds() != null && !clientContractPropertyVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientContractPropertyVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientContractPropertyId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientContractPropertyVO clientContractPropertyVOForDel = clientContractPropertyService.getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );
                  clientContractPropertyVOForDel.setModifyBy( getUserId( request, response ) );
                  clientContractPropertyVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientContractPropertyService.deleteClientContractProperty( clientContractPropertyVOForDel );
               }
            }
         }

         // 清除Selected IDs和子Action
         clientContractPropertyVO.setSelectedIds( "" );
         clientContractPropertyVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
