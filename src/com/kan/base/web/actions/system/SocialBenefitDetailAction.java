package com.kan.base.web.actions.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SocialBenefitDetailService;
import com.kan.base.service.inf.system.SocialBenefitHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class SocialBenefitDetailAction extends BaseAction
{
	public static final String accessAction = "HRO_SB_SB_POLICY";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) form;

         // 处理SubAction
         dealSubAction( socialBenefitDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = socialBenefitDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得SocialBenefitHeaderVO
         final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( headerId );

         // 刷新国际化
         socialBenefitHeaderVO.reset( null, request );

         // 设置SubAction
         socialBenefitHeaderVO.setSubAction( VIEW_OBJECT );

         // 如果City Id，则填充Province Id
         if ( KANUtil.filterEmpty( socialBenefitHeaderVO.getCityId(), "0" ) != null )
         {
            socialBenefitHeaderVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( socialBenefitHeaderVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            socialBenefitHeaderVO.setCityIdTemp( socialBenefitHeaderVO.getCityId() );
         }

         // 写入request对象
         request.setAttribute( "socialBenefitHeaderForm", socialBenefitHeaderVO );

         // 如果没有指定排序则默认按 LeaveId排序
         if ( socialBenefitDetailVO.getSortColumn() == null || socialBenefitDetailVO.getSortColumn().isEmpty() )
         {
            socialBenefitDetailVO.setSortColumn( "itemId" );
            socialBenefitDetailVO.setSortOrder( "desc" );
         }

         // 传入主表ID
         socialBenefitDetailVO.setHeaderId( headerId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder socialBenefitDetailHolder = new PagedListHolder();
         // 传入当前页
         socialBenefitDetailHolder.setPage( page );
         // 传入主表ID
         socialBenefitDetailVO.setHeaderId( headerId );
         // 传入当前值对象
         socialBenefitDetailHolder.setObject( socialBenefitDetailVO );
         // 设置页面记录条数
         socialBenefitDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         socialBenefitDetailService.getSocialBenefitDetailVOsByCondition( socialBenefitDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( socialBenefitDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "socialBenefitDetailHolder", socialBenefitDetailHolder );
         request.setAttribute( "PAGE_ACCOUNT_ID", getAccountId( request, response ) );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回SocialBenetifDetail JSP
            return mapping.findForward( "listSocialBenefitDetailTable" );
         }
         ( ( SocialBenefitDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSocialBenefitDetail" );
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
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // 获得当前FORM
            final SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) form;
            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            socialBenefitDetailVO.setHeaderId( headerId );
            socialBenefitDetailVO.setCreateBy( getUserId( request, response ) );
            socialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
            socialBenefitDetailVO.setAccountId( getAccountId( request, response ) );
            // checkbox处理社保月份、户籍类型
            socialBenefitDetailVO.setTermMonth( KANUtil.toJasonArray( socialBenefitDetailVO.getTermMonthArray(), "," ) );
            socialBenefitDetailService.insertSocialBenefitDetail( socialBenefitDetailVO );

            // 重新加载常量中的系统社保
            constants.initSocialBenefit();

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( SocialBenefitDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化HeaderService接口
         final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         // 主键主表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // 获得SocialBenefitDetailVO对象
         final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDetailService.getSocialBenefitDetailVOByDetailId( detailId );
         // 获得SocialBenefitHeaderVO对象
         final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( socialBenefitDetailVO.getHeaderId() );
         // 国际化传值
         socialBenefitDetailVO.reset( null, request );
         // 区分修改添加
         socialBenefitDetailVO.setSubAction( BaseAction.VIEW_OBJECT );
         socialBenefitDetailVO.setStatus( SocialBenefitHeaderVO.TRUE );
         // 传入request对象
         request.setAttribute( "socialBenefitHeaderForm", socialBenefitHeaderVO );
         request.setAttribute( "socialBenefitDetailForm", socialBenefitDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageSocialBenefitDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // 从表ID
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取SocialBenefitDetailVO对象
            final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDetailService.getSocialBenefitDetailVOByDetailId( detailId );
            // 装载界面传值
            socialBenefitDetailVO.update( ( SocialBenefitDetailVO ) form );
            // 获取登录用户
            socialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
            // checkbox处理社保月份、户籍类型
            socialBenefitDetailVO.setTermMonth( KANUtil.toJasonArray( socialBenefitDetailVO.getTermMonthArray(), "," ) );
            // 调用修改方法
            socialBenefitDetailService.updateSocialBenefitDetail( socialBenefitDetailVO );

            // 重新加载常量中的系统社保
            constants.initSocialBenefit();

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }
         // 清空Form
         ( ( SocialBenefitDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // 获得Action Form
         SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) form;
         // 存在选中的ID
         if ( socialBenefitDetailVO.getSelectedIds() != null && !socialBenefitDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : socialBenefitDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               socialBenefitDetailVO = socialBenefitDetailService.getSocialBenefitDetailVOByDetailId( selectedId );
               // 调用删除接口
               socialBenefitDetailVO.setDetailId( selectedId );
               socialBenefitDetailVO.setAccountId( getAccountId( request, response ) );
               socialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
               socialBenefitDetailService.deleteSocialBenefitDetail( socialBenefitDetailVO );
            }
         }

         // 重新加载常量中的系统社保
         constants.initSocialBenefit();

         // 清除Selected IDs和子Action
         socialBenefitDetailVO.setSelectedIds( "" );
         socialBenefitDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
