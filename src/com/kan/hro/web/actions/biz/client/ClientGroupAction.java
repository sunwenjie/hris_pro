package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.kan.hro.domain.biz.client.ClientGroupDTO;
import com.kan.hro.domain.biz.client.ClientGroupVO;
import com.kan.hro.service.inf.biz.client.ClientGroupService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientGroupAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-8  
 */
public class ClientGroupAction extends BaseAction
{

	public final static String accessAction = "HRO_BIZ_CLIENT_GROUP";
   /**
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );

         // 获取当前登录用户的accountId
         final String accountId = getAccountId( request, response );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientGroupService.getClientGroupBaseViews( accountId ) );

         // Send to clientGroup
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**
    * List clientGroup
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
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
         // 获得Action Form
         final ClientGroupVO clientGroupVO = ( ClientGroupVO ) form;
         clientGroupVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientGroupVO.getSubAction() != null && clientGroupVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientGroupVO );
         }

         // 如果没有指定排序则默认按ClientGroupId排序
         if ( clientGroupVO.getSortColumn() == null || clientGroupVO.getSortColumn().isEmpty() )
         {
            clientGroupVO.setSortColumn( "clientGroupId" );
            clientGroupVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientGroupVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";
         // 如果子SubAction不为空
         if ( clientGroupVO.getSubAction() != null )
         {
            subAction = clientGroupVO.getSubAction().trim();
         }
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientGroupService.getClientGroupVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_GROUP" ) );
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
      return mapping.findForward( "listClientGroup" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-07
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( ClientGroupVO ) form ).setSubAction( CREATE_OBJECT );
      // 设置状态默认值
      ( ( ClientGroupVO ) form ).setStatus( ClientGroupVO.TRUE );

      // 跳转到新建界面
      return mapping.findForward( "manageClientGroup" );
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
   // Reviewed by Kevin Jin at 2013-11-07
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );

         // 获得当前主键
         String clientGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( clientGroupId == null || clientGroupId.trim().isEmpty() )
         {
            clientGroupId = ( ( ClientGroupVO ) form ).getClientGroupId();
         }

         // 获得ClientGroupVO
         final ClientGroupVO clientGroupVO = clientGroupService.getClientGroupVOByClientGroupId( clientGroupId );

         // 刷新VO对象，初始化对象列表及国际化
         clientGroupVO.reset( null, request );
         clientGroupVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientGroupForm", clientGroupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientGroup" );
   }

   // Reviewed by Kevin Jin at 2013-11-07
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
            // 获得ActionForm
            final ClientGroupVO clientGroupVO = ( ClientGroupVO ) form;
            // 获取登录用户
            clientGroupVO.setCreateBy( getUserId( request, response ) );
            clientGroupVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientGroupVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 新建对象
            clientGroupService.insertClientGroup( clientGroupVO );
            
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
            
            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + ( ( ClientGroupVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );
               
               return null;
            }

         }

         // 清空Form条件
         ( ( ClientGroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify clientGroup
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
            final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
            // 获得当前主键
            final String clientGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final ClientGroupVO clientGroupVO = clientGroupService.getClientGroupVOByClientGroupId( clientGroupId );
            // 获取登录用户
            clientGroupVO.update( ( ClientGroupVO ) form );
            // 保存自定义Column
            clientGroupVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientGroupVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            clientGroupService.updateClientGroup( clientGroupVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form条件
         ( ( ClientGroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete clientGroup
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
    * Delete clientGroup list
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
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
         // 获得Action Form
         ClientGroupVO clientGroupVO = ( ClientGroupVO ) form;
         // 存在选中的ID
         if ( clientGroupVO.getSelectedIds() != null && !clientGroupVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientGroupVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientGroupId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientGroupVO clientGroupVOForDel = clientGroupService.getClientGroupVOByClientGroupId( clientGroupId );
                  clientGroupVOForDel.setModifyBy( getUserId( request, response ) );
                  clientGroupVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientGroupService.deleteClientGroup( clientGroupVOForDel );
               }
            }
         }
         // 清除Selected IDs和子Action
         clientGroupVO.setSelectedIds( "" );
         clientGroupVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
   * List Special Info HTML
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );

         // 获得当前主键
         final String clientGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "clientGroupId" ), "UTF-8" ) );

         // 初始化ClientGroupDTO
         ClientGroupDTO clientGroupDTO = new ClientGroupDTO();

         // 如果存在clientGroupId
         if ( !clientGroupId.equals( "" ) )
         {
            // 新建一个ClientGroupVO用于查询DTO
            final ClientGroupVO clientGroupVO = new ClientGroupVO();
            clientGroupVO.setAccountId( getAccountId( request, response ) );
            clientGroupVO.setClientGroupId( clientGroupId );
            // 获得ClientGroupDTO
            final Object cbjectClientGroupDTO = clientGroupService.getClientGroupDTOsByClientGroupVO( clientGroupVO );
            clientGroupDTO = ( ClientGroupDTO ) cbjectClientGroupDTO;

            // 刷新VO对象，初始化对象列表及国际化
            clientGroupDTO.getClientGroupVO().reset( null, request );
            clientGroupDTO.getClientGroupVO().setSubAction( VIEW_OBJECT );

         }

         // 添加Client Count用于Tab Number显示
         final int clientCount = clientGroupDTO.getClientBaseViews().size();

         request.setAttribute( "clientCount", clientCount );
         request.setAttribute( "clientGroupDTO", clientGroupDTO );
         request.setAttribute( "clientGroupForm", ( clientGroupDTO.getClientGroupVO() == null ) ? request.getAttribute( "clientGroupForm" ) : clientGroupDTO.getClientGroupVO() );

         // Ajax调用
         return mapping.findForward( "listSpecialInfo" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
