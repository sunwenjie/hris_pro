package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SocialBenefitSolutionHeaderAction extends BaseAction

{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SB_SOLUTION";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // 获得Action Form
         final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) form;

         // 调用删除方法
         if ( socialBenefitSolutionHeaderVO.getSubAction() != null && socialBenefitSolutionHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( socialBenefitSolutionHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder socialBenefitSolutionHeaderHolder = new PagedListHolder();
         // 传入当前页
         socialBenefitSolutionHeaderHolder.setPage( page );
         // 传入当前值对象
         socialBenefitSolutionHeaderHolder.setObject( socialBenefitSolutionHeaderVO );
         // 设置页面记录条数
         socialBenefitSolutionHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOsByCondition( socialBenefitSolutionHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( socialBenefitSolutionHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "socialBenefitSolutionHeaderHolder", socialBenefitSolutionHeaderHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回SocialBenefitSolutionHeader JSP
            return mapping.findForward( "listSocialBenefitSolutionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listSocialBenefitSolutionHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( SocialBenefitSolutionHeaderVO ) form ).setStatus( SocialBenefitSolutionHeaderVO.TRUE );
      ( ( SocialBenefitSolutionHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( SocialBenefitSolutionHeaderVO ) form ).setPersonalSBBurden( "2" );

      final PagedListHolder socialBenefitSolutionDetailHolder = new PagedListHolder();
      socialBenefitSolutionDetailHolder.setHolderSize( 0 );
      request.setAttribute( "socialBenefitSolutionDetailHolder", socialBenefitSolutionDetailHolder );

      // 跳转到新建界面  
      return mapping.findForward( "manageSocialBenefitSolutionHeader" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-22
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 初始化form
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

            // 获得当下FORM
            final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) form;
            socialBenefitSolutionHeaderVO.setCreateBy( getUserId( request, response ) );
            socialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
            socialBenefitSolutionHeaderVO.setAccountId( getAccountId( request, response ) );

            // 调用添加方法
            socialBenefitSolutionHeaderService.insertSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );
            socialBenefitSolutionDetailVO.setHeaderId( socialBenefitSolutionHeaderVO.getHeaderId() );

            // 初始化常量持久对象
            constantsInit( "initSocialBenefitSolution", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, socialBenefitSolutionHeaderVO, Operate.ADD, socialBenefitSolutionHeaderVO.getHeaderId(), null );
         }
         else
         {
            // 清空Form
            ( ( SocialBenefitSolutionHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转detail界面
      return new SocialBenefitSolutionDetailAction().list_object( mapping, socialBenefitSolutionDetailVO, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-22
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 初始化form
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "headerId" ), "UTF-8" ) );
            socialBenefitSolutionDetailVO.setHeaderId( headerId );

            // 获取SocialBenefitSolutionHeaderVO对象
            final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOByHeaderId( headerId );
            // 装载界面传值
            socialBenefitSolutionHeaderVO.update( ( SocialBenefitSolutionHeaderVO ) form );
            socialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );

            // 调用修改方法
            socialBenefitSolutionHeaderService.updateSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initSocialBenefitSolution", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, socialBenefitSolutionHeaderVO, Operate.MODIFY, socialBenefitSolutionHeaderVO.getHeaderId(), null );
         }

         // 清空Form
         ( ( SocialBenefitSolutionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转detail界面
      return new SocialBenefitSolutionDetailAction().list_object( mapping, socialBenefitSolutionDetailVO, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use

   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-22
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // 获得Action Form
         SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) form;
         // 存在选中的ID
         if ( socialBenefitSolutionHeaderVO.getSelectedIds() != null && !socialBenefitSolutionHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, socialBenefitSolutionHeaderVO, Operate.DELETE, null, socialBenefitSolutionHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : socialBenefitSolutionHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOByHeaderId( selectedId );
               socialBenefitSolutionHeaderVO.setHeaderId( selectedId );
               socialBenefitSolutionHeaderVO.setAccountId( getAccountId( request, response ) );
               socialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
               socialBenefitSolutionHeaderService.deleteSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );
            }

            // 初始化常量持久对象
            constantsInit( "initSocialBenefitSolution", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         socialBenefitSolutionHeaderVO.setSelectedIds( "" );
         socialBenefitSolutionHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

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

         // 初始化Service
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();

         // 获取SocialBenefitSolutionHeaderVO列表
         final List< SocialBenefitSolutionHeaderVO > socialBenefitSolutionHeaderVOs = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOsByAccountId( getAccountId( request, response ) );

         // 遍历并Reset
         if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
         {
            for ( SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO : socialBenefitSolutionHeaderVOs )
            {
               socialBenefitSolutionHeaderVO.reset( mapping, request );
            }
         }

         array.addAll( socialBenefitSolutionHeaderVOs );

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

}
