package com.kan.base.web.actions.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionGradeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class PositionGradeAction extends BaseAction
{
   public static String accessAction = "HRO_SEC_POSITIONGRADE";

   /**
    * List Position Grade
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
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
         // 获得Action Form
         final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;

         // 如果子Action是删除用户列表
         if ( positionGradeVO.getSubAction() != null && positionGradeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( positionGradeVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder positionGradeHolder = new PagedListHolder();

         // 传入当前页
         positionGradeHolder.setPage( page );
         // 传入当前值对象
         positionGradeHolder.setObject( positionGradeVO );
         // 设置页面记录条数
         positionGradeHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         positionGradeService.getPositionGradeVOsByCondition( positionGradeHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( positionGradeHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "positionGradeHolder", positionGradeHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listPositionGradeTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listPositionGrade" );
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
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( PositionGradeVO ) form ).setSubAction( CREATE_OBJECT );
      //默认启用状态
      ( ( PositionGradeVO ) form ).setStatus( "1" );

      // 跳转到新建界面
      return mapping.findForward( "managePositionGrade" );
   }

   /**
    * To Object Modifyy
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
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
         // 获得当前主键
         String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( positionGradeId ) == null )
         {
            positionGradeId = ( ( PositionGradeVO ) form ).getPositionGradeId();
         }
         // 获得主键对应对象
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
         // 刷新对象，初始化对象列表及国际化
         positionGradeVO.reset( null, request );
         positionGradeVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "positionGradeForm", positionGradeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "managePositionGrade" );
   }

   /**
    * Add Position Grade
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
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
            // 获得ActionForm
            final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;

            // 获取登录用户
            positionGradeVO.setAccountId( getAccountId( request, response ) );
            positionGradeVO.setCreateBy( getUserId( request, response ) );
            positionGradeVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            positionGradeService.insertPositionGrade( positionGradeVO );

            // 刷新常量
            constantsInit( "initPositionGrade", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, positionGradeVO, Operate.ADD, positionGradeVO.getPositionGradeId(), null );
         }
         else
         {
            // 清空Form条件
            ( ( PositionGradeVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // 跳转到列表界面
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到修改
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Position Grade
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
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
            // 获得当前主键
            final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );

            // 获取登录用户
            positionGradeVO.update( ( PositionGradeVO ) form );
            positionGradeVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            positionGradeService.updatePositionGrade( positionGradeVO );

            // 刷新常量
            constantsInit( "initPositionGrade", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, positionGradeVO, Operate.MODIFY, positionGradeVO.getPositionGradeId(), null );

            // 清空Form条件
            ( ( PositionGradeVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Position Grade
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
      // No Use
   }

   /**
    * Delete Position Grade list
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
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "positionGradeService" );
         // 获得Action Form
         PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;

         // 存在选中的ID
         if ( positionGradeVO.getSelectedIds() != null && !positionGradeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : positionGradeVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               final PositionGradeVO tempPositionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( selectedId );
               tempPositionGradeVO.setModifyBy( getUserId( request, response ) );
               tempPositionGradeVO.setModifyDate( new Date() );
               positionGradeService.deletePositionGrade( tempPositionGradeVO );
            }

            insertlog( request, positionGradeVO, Operate.DELETE, null, positionGradeVO.getSelectedIds() );
         }

         // 刷新常量
         constantsInit( "initPositionGrade", getAccountId( request, response ) );
         // 清除Selected IDs和子Action
         positionGradeVO.setSelectedIds( "" );
         positionGradeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getPositionGradeList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );

      List< PositionGradeVO > positionGradeVOList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).POSITION_GRADE_VO;
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( PositionGradeVO positionGradeVO : positionGradeVOList )
      {
         if ( StringUtils.equals( positionGradeVO.getCorpId(), BaseAction.getCorpId( request, response ) ) )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", positionGradeVO.getPositionGradeId() );
            map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionGradeVO.getGradeNameZH() : positionGradeVO.getGradeNameEN() );
            listReturn.add( map );
         }
      }

      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }
}