package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.service.inf.management.PositionService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionAction extends BaseAction
{
   public static String accessAction = "HRO_EMPLOYEE_POSITION";

   /**
    * List Object Json
    * 
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

         // 初始化Position Service
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( positionService.getPositionBaseViewsByAccountId( getAccountId( request, response ) ) );
         // Send to client
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
    * List positions
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
         // 获得Action Form
         final PositionVO positionVO = ( PositionVO ) form;

         // 获取的当前登录的accountId
         final String accountId = getAccountId( request, response );
         // 将accountId放入request中,以后获取生成职位树用的positionDTO用
         request.setAttribute( "accountId", accountId );

         positionVO.setAccountId( accountId );

         // 如果子Action是删除用户列表
         if ( positionVO.getSubAction() != null && positionVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( positionVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      request.setAttribute( "role", getRole( request, response ) );
      // 跳转到列表界面
      return mapping.findForward( "listPosition" );
   }

   /**
    * To position modify page
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
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
         // 获得当前主键
         String positionId = request.getParameter( "positionId" );
         if ( KANUtil.filterEmpty( positionId ) == null )
         {
            positionId = ( ( PositionVO ) form ).getPositionId();
         }
         else
         {
            positionId = Cryptogram.decodeString( URLDecoder.decode( positionId, "UTF-8" ) );
         }
         // 获得主键对应对象
         final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
         // 刷新对象，初始化对象列表及国际化
         positionVO.reset( null, request );
         positionVO.setSubAction( VIEW_OBJECT );

         // 获得当前PositionVO对应的SkillId 和 Attachment 数量
         final String[] skillIdArray = positionVO.getSkillIdArray();
         final String[] attachmentArray = positionVO.getAttachmentArray();
         // 发送SkillIdArraySize 和 AttachmentArraySize 至前端用于Tab显示
         request.setAttribute( "skillIdArraySize", ( skillIdArray == null ) ? "0" : skillIdArray.length );
         request.setAttribute( "attachmentArraySize", ( attachmentArray == null ) ? "0" : attachmentArray.length );

         // 传回ActionForm对象到前端
         request.setAttribute( "mgtPositionForm", positionVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "managePosition" );
   }

   /**
    * To position new page
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
      ( ( PositionVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( PositionVO ) form ).setStatus( BaseVO.TRUE );
      // 如果存在Parent PositionId
      final String noEncodedParentPositionId = request.getParameter( "parentPositionId" );

      // 发送SkillIdArray和AttachmentArray 长度默认值
      request.setAttribute( "skillIdArraySize", "0" );
      request.setAttribute( "attachmentArraySize", "0" );

      if ( noEncodedParentPositionId != null && !noEncodedParentPositionId.trim().equals( "" ) )
      {
         String parentPositionId;
         try
         {
            // 获得Parent PositionVO的主键
            parentPositionId = Cryptogram.decodeString( URLDecoder.decode( noEncodedParentPositionId, "UTF-8" ) );
            // 初始化Service接口
            final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
            // 根据主键获得Parent PositionVO
            final PositionVO positionVO = positionService.getPositionVOByPositionId( parentPositionId );
            // 获得Parent PositionVO的Position Name
            final String parentPositionName = positionVO.getTitleZH() + " - " + positionVO.getTitleEN();
            ( ( PositionVO ) form ).setParentPositionId( parentPositionId );
            ( ( PositionVO ) form ).setParentPositionName( parentPositionName );

         }
         catch ( UnsupportedEncodingException e )
         {
            e.printStackTrace();
         }
      }
      // 跳转到新建界面
      return mapping.findForward( "managePosition" );
   }

   /**
    * Add position
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
            final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
            // 获得ActionForm
            final PositionVO positionVO = ( PositionVO ) form;

            positionVO.setAccountId( getAccountId( request, response ) );
            positionVO.setCreateBy( getUserId( request, response ) );
            positionVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            positionService.insertPosition( positionVO );
            // 返回添加成功标记
            success( request );
            // 重新加载常量中的mgtPosition
            constantsInit( "initEmployeePosition", getAccountId( request, response ) );
            // 清空Form条件
            ( ( PositionVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify position
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
            final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
            // 获得当前主键
            final String positionId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionId" ), "UTF-8" ) );
            // 获得主键对应未修改前对象
            final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
            // 修改对象数据
            positionVO.update( ( PositionVO ) form );
            positionVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            positionService.updatePosition( positionVO );
            // 重新加载常量中的mgtPosition
            constantsInit( "initEmployeePosition", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
            // 清空Form条件
            ( ( PositionVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
   * Delete position
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
         // 获得当前主键
         final String positionId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionId" ), "UTF-8" ) );
         // 获得主键对应的PositionVO
         final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
         // 设置PositionVO的相关值
         positionVO.setModifyBy( getUserId( request, response ) );
         positionVO.setModifyDate( new Date() );
         // 删除选定的positionVO
         positionService.deletePosition( positionVO );
         // 重新加载常量中的mgtPosition
         constantsInit( "initEmployeePosition", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete position list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
         // 获得Action Form
         final PositionVO positionVO = ( PositionVO ) form;
         // 存在选中的ID
         if ( positionVO.getPositionIdArray() != null && positionVO.getPositionIdArray().length > 0 )
         {
            // 分割
            for ( String selectedId : positionVO.getPositionIdArray() )
            {
               // 通过id获取id对应的 positionVO
               final PositionVO positionVOForDel = positionService.getPositionVOByPositionId( selectedId );
               // 设置PositionVO的相关值
               positionVOForDel.setModifyBy( getUserId( request, response ) );
               positionVOForDel.setModifyDate( new Date() );
               positionService.deletePosition( positionVOForDel );
            }
         }
         // 重新加载常量中的mgtPosition
         constantsInit( "initEmployeePosition", getAccountId( request, response ) );
         // 清除Selected IDs和子Action
         positionVO.setSelectedIds( "" );
         positionVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
