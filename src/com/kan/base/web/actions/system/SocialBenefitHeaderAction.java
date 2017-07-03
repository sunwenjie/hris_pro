package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SocialBenefitHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.SocialBenefitHeaderRender;

public class SocialBenefitHeaderAction extends BaseAction
{

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
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         // 获得Action Form
         final SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) form;

         // 处理subAction
         dealSubAction( socialBenefitHeaderVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder socialBenefitHeaderHolder = new PagedListHolder();
         // 传入当前页
         socialBenefitHeaderHolder.setPage( page );
         // 传入当前值对象
         socialBenefitHeaderHolder.setObject( socialBenefitHeaderVO );
         // 设置页面记录条数
         socialBenefitHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         socialBenefitHeaderService.getSocialBenefitHeaderVOsByCondition( socialBenefitHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( socialBenefitHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "socialBenefitHeaderHolder", socialBenefitHeaderHolder );
         // 如果是社保政策则 readonly
         request.setAttribute( "PAGE_ACCOUNT_ID", getAccountId( request, response ) );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回SocialBenefitHeader JSP
            return mapping.findForward( "listSocialBenefitHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSocialBenefitHeader" );
   }

   public ActionForward list_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得CityId
         final String cityId = request.getParameter( "cityId" );
         // 社保主表Id
         final String headerId = request.getParameter( "headerId" );
         // 初始化Service
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         // 获得城市ID对应的社保类型
         final List< Object > socialBenefitHeaderVOs = socialBenefitHeaderService.getSocialBenefitHeaderVOsByCityId( cityId );

         // 初始化Response对象设置
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         out.println( SocialBenefitHeaderRender.getSocialBenbfitHeaderVOsByCityId( request, socialBenefitHeaderVOs, headerId ) );
         out.flush();
         out.close();
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 初始化默认属性
      ( ( SocialBenefitHeaderVO ) form ).setAttribute( "1" );
      ( ( SocialBenefitHeaderVO ) form ).setStartRule( "1" );
      ( ( SocialBenefitHeaderVO ) form ).setStartRuleRemark( "15" );
      ( ( SocialBenefitHeaderVO ) form ).setEndRule( "1" );
      ( ( SocialBenefitHeaderVO ) form ).setEndRuleRemark( "15" );
      ( ( SocialBenefitHeaderVO ) form ).setMakeup( "2" );
      ( ( SocialBenefitHeaderVO ) form ).setCompanyAccuracy( "3" );
      ( ( SocialBenefitHeaderVO ) form ).setPersonalAccuracy( "3" );
      ( ( SocialBenefitHeaderVO ) form ).setRound( "1" );

      // 设置Sub Action
      ( ( SocialBenefitHeaderVO ) form ).setStatus( SocialBenefitHeaderVO.TRUE );
      ( ( SocialBenefitHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      
      request.setAttribute( "PAGE_ACCOUNT_ID", getAccountId( request, response ) );

      // 跳转到新建界面  
      return mapping.findForward( "manageSocialBenefitHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化SocialBenefitDetailVO
         final SocialBenefitDetailVO socialBenefitDetailVO = new SocialBenefitDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 获得当前FORM
            final SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) form;
            // checkbox处理社保月份、户籍类型
            socialBenefitHeaderVO.setTermMonth( KANUtil.toJasonArray( socialBenefitHeaderVO.getTermMonthArray(), "," ) );
            socialBenefitHeaderVO.setResidency( KANUtil.toJasonArray( socialBenefitHeaderVO.getResidencyArray(), "," ) );
            socialBenefitHeaderVO.setCreateBy( getUserId( request, response ) );
            socialBenefitHeaderVO.setModifyBy( getUserId( request, response ) );
            socialBenefitHeaderVO.setAccountId( getAccountId( request, response ) );
            socialBenefitHeaderService.insertSocialBenefitHeader( socialBenefitHeaderVO );
            socialBenefitDetailVO.setHeaderId( socialBenefitHeaderVO.getHeaderId() );

            // 重新加载常量中的系统社保
            constants.initSocialBenefit();

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
         }
         else
         {
            // 返回重复提交标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // 清空Form
            ( ( SocialBenefitHeaderVO ) form ).reset();

            return list_object( mapping, form, request, response );
         }

         return new SocialBenefitDetailAction().list_object( mapping, socialBenefitDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取SocialBenefitHeaderVO对象
            final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( headerId );
            // 装载界面传值
            socialBenefitHeaderVO.update( ( SocialBenefitHeaderVO ) form );
            // checkbox处理社保月份、户籍类型
            socialBenefitHeaderVO.setTermMonth( KANUtil.toJasonArray( socialBenefitHeaderVO.getTermMonthArray(), "," ) );
            socialBenefitHeaderVO.setResidency( KANUtil.toJasonArray( socialBenefitHeaderVO.getResidencyArray(), "," ) );
            // 获取登录用户
            socialBenefitHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            socialBenefitHeaderService.updateSocialBenefitHeader( socialBenefitHeaderVO );
            // 重新加载常量中的系统社保
            constants.initSocialBenefit();
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         }
         // 清空Form
         ( ( SocialBenefitHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new SocialBenefitDetailAction().list_object( mapping, new SocialBenefitDetailVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // 获得Action Form
         SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) form;
         // 存在选中的ID
         if ( socialBenefitHeaderVO.getSelectedIds() != null && !socialBenefitHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : socialBenefitHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得要删除的对象
               socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( selectedId );
               // 调用删除接口
               socialBenefitHeaderVO.setHeaderId( selectedId );
               socialBenefitHeaderVO.setAccountId( getAccountId( request, response ) );
               socialBenefitHeaderVO.setModifyBy( getUserId( request, response ) );
               socialBenefitHeaderService.deleteSocialBenefitHeader( socialBenefitHeaderVO );
            }
         }

         // 重新加载常量中的系统社保
         constants.initSocialBenefit();

         // 清除Selected IDs和子Action
         socialBenefitHeaderVO.setSelectedIds( "" );
         socialBenefitHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
