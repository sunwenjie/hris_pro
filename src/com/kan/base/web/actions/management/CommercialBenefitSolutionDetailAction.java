package com.kan.base.web.actions.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CommercialBenefitSolutionDetailService;
import com.kan.base.service.inf.management.CommercialBenefitSolutionHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class CommercialBenefitSolutionDetailAction extends BaseAction
{
   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CB_SOLUTION";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
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
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = ( CommercialBenefitSolutionDetailVO ) form;

         // 处理SubAction
         dealSubAction( commercialBenefitSolutionDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );
         final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = commercialBenefitSolutionDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( headerId );
         // 刷新对象，初始化对象列表及国际化
         commercialBenefitSolutionHeaderVO.reset( null, request );
         // 区分修改、查看
         commercialBenefitSolutionHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "commercialBenefitSolutionHeaderForm", commercialBenefitSolutionHeaderVO );
         request.setAttribute( "listAttachmentCount", commercialBenefitSolutionHeaderVO.getAttachmentArray().length );

         // “费用方式”、“保留小数 ”、“保留方式”带入默认值
         commercialBenefitSolutionDetailVO.setCalculateType( commercialBenefitSolutionHeaderVO.getCalculateType() );
         commercialBenefitSolutionDetailVO.setAccuracy( commercialBenefitSolutionHeaderVO.getAccuracy() );
         commercialBenefitSolutionDetailVO.setRound( commercialBenefitSolutionHeaderVO.getRound() );

         commercialBenefitSolutionDetailVO.setHeaderId( headerId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder commercialBenefitSolutionDetailHolder = new PagedListHolder();
         // 传入当前页
         commercialBenefitSolutionDetailHolder.setPage( page );
         // 传入当前值对象
         commercialBenefitSolutionDetailHolder.setObject( commercialBenefitSolutionDetailVO );
         // 设置页面记录条数
         commercialBenefitSolutionDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOsByCondition( commercialBenefitSolutionDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( commercialBenefitSolutionDetailHolder, request );

         commercialBenefitSolutionDetailVO.setStatus( BaseVO.TRUE );
         commercialBenefitSolutionDetailVO.reset( null, request );
         // Holder需写入Request对象
         request.setAttribute( "commercialBenefitSolutionDetailHolder", commercialBenefitSolutionDetailHolder );
         request.setAttribute( "commercialBenefitSolutionDetailForm", commercialBenefitSolutionDetailVO );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            // Ajax Table调用，直接传回CommercialBenefitSolutionDetail JSP
            return mapping.findForward( "listCommercialBenefitSolutionDetailTable" );
         }
         // 清空subAction
         ( ( CommercialBenefitSolutionDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listCommercialBenefitSolutionDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

            // 获得当前FORM
            final CommercialBenefitSolutionDetailVO commercialBenefitDetailVO = ( CommercialBenefitSolutionDetailVO ) form;
            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            commercialBenefitDetailVO.setHeaderId( headerId );
            commercialBenefitDetailVO.setCreateBy( getUserId( request, response ) );
            commercialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
            commercialBenefitDetailVO.setAccountId( getAccountId( request, response ) );

            // 调用添加方法
            commercialBenefitSolutionDetailService.insertCommercialBenefitSolutionDetail( commercialBenefitDetailVO );

            // 重新加载常量中的CommercialBenefitSolution
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( CommercialBenefitSolutionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
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
         // 初始化Service接口
         final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );
         // 主键主表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // 获得CommercialBenefitSolutionDetailVO对象
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOByDetailId( detailId );
         // 获得CommercialBenefitSolutionHeaderVO对象
         final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( commercialBenefitSolutionDetailVO.getHeaderId() );
         // 国际化传值
         commercialBenefitSolutionDetailVO.reset( null, request );
         // 区分修改添加
         commercialBenefitSolutionDetailVO.setSubAction( VIEW_OBJECT );
         // 传入request对象
         request.setAttribute( "commercialBenefitSolutionHeaderForm", commercialBenefitSolutionHeaderVO );
         request.setAttribute( "commercialBenefitSolutionDetailForm", commercialBenefitSolutionDetailVO );

         // 重写role
         request.setAttribute( "role", getRole( request, null ) );
         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageCommercialBenefitSolutionDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取主键对象
            final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOByDetailId( detailId );
            // 装载界面传值
            commercialBenefitSolutionDetailVO.update( ( CommercialBenefitSolutionDetailVO ) form );
            commercialBenefitSolutionDetailVO.setModifyBy( getUserId( request, response ) );

            // 调用修改接口
            commercialBenefitSolutionDetailService.updateCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );

            // 重新加载常量中的CommercialBenefitSolution
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( CommercialBenefitSolutionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

         // 获得当前form
         CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = ( CommercialBenefitSolutionDetailVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : commercialBenefitSolutionDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               commercialBenefitSolutionDetailVO = commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOByDetailId( selectedId );
               commercialBenefitSolutionDetailVO.setModifyBy( getUserId( request, response ) );
               commercialBenefitSolutionDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               commercialBenefitSolutionDetailService.deleteCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );
            }

            // 重新加载常量中的CommercialBenefitSolution
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         ( ( CommercialBenefitSolutionDetailVO ) form ).setSelectedIds( "" );
         ( ( CommercialBenefitSolutionDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
