package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TaxTemplateDetailService;
import com.kan.base.service.inf.define.TaxTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class TaxTemplateDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_INCOMETAXTEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );

         // 获得是否Ajax调用
         final String ajax = getAjax( request );

         // 获得Action Form
         final TaxTemplateDetailVO taxTemplateDetailVO = ( TaxTemplateDetailVO ) form;

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 处理SubAction
         dealSubAction( taxTemplateDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );
         final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = taxTemplateDetailVO.getTemplateHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final TaxTemplateHeaderVO taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( headerId );

         // 刷新国际化
         taxTemplateHeaderVO.reset( null, request );

         // 填充省份
         if ( KANUtil.filterEmpty( taxTemplateHeaderVO.getCityId(), "0" ) != null )
         {
            taxTemplateHeaderVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( taxTemplateHeaderVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            taxTemplateHeaderVO.setCityIdTemp( taxTemplateHeaderVO.getCityId() );
         }

         // 设置SubAction
         taxTemplateHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "taxTemplateHeaderForm", taxTemplateHeaderVO );

         // 设置templateHeaderId
         taxTemplateDetailVO.setTemplateHeaderId( headerId );

         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( taxTemplateDetailVO.getSortColumn() == null || taxTemplateDetailVO.getSortColumn().isEmpty() )
         {
            taxTemplateDetailVO.setSortColumn( "columnIndex" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder taxTemplateDetailHolder = new PagedListHolder();
         // 传入当前页
         taxTemplateDetailHolder.setPage( page );
         // 传入当前值对象
         taxTemplateDetailHolder.setObject( taxTemplateDetailVO );
         // 设置页面记录条数
         taxTemplateDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         taxTemplateDetailService.getTaxTemplateDetailVOsByCondition( taxTemplateDetailHolder, true );

         // 设置TaxTemplateDetailForm初始值
         taxTemplateDetailVO.setColumnIndex( "0" );
         taxTemplateDetailVO.setFontSize( "13" );
         taxTemplateDetailVO.setAlign( TaxTemplateDetailVO.TRUE );
         taxTemplateDetailVO.setStatus( TaxTemplateDetailVO.TRUE );

         // 刷新Holder，国际化传值
         refreshHolder( taxTemplateDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "taxTemplateDetailHolder", taxTemplateDetailHolder );

         // Ajax Table调用，直接传回Detail JSP
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listTaxTemplateDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listTaxTemplateDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service 接口
            final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

            // 获得templateHeaderId
            final String templateHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateHeaderId" ), "UTF-8" ) );

            // 获得当前Form
            final TaxTemplateDetailVO taxTemplateDetailVO = ( TaxTemplateDetailVO ) form;

            // 初始化TaxTemplateDetailVO对象
            taxTemplateDetailVO.setTemplateHeaderId( templateHeaderId );
            taxTemplateDetailVO.setCreateBy( getUserId( request, response ) );
            taxTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            taxTemplateDetailVO.setAccountId( getAccountId( request, response ) );

            // 添加TaxTemplateDetailVO
            taxTemplateDetailService.insertTaxTemplateDetail( taxTemplateDetailVO );

            // 重新加载常量中的TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, taxTemplateDetailVO, Operate.ADD, taxTemplateDetailVO.getTemplateDetailId(), null );
         }

         // 清空form
         ( ( TaxTemplateDetailVO ) form ).reset();
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
         // 获取templateDetailId
         final String templateDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 初始化Service接口
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );
         final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

         // 获取TaxTemplateDetailVO
         final TaxTemplateDetailVO taxTemplateDetailVO = taxTemplateDetailService.getTaxTemplateDetailVOByTemplateDetailId( templateDetailId );

         // 获取TaxTemplateHeaderVO
         final TaxTemplateHeaderVO taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( taxTemplateDetailVO.getTemplateHeaderId() );

         // 国际化传值
         taxTemplateDetailVO.reset( null, request );
         // 设置SubAction
         taxTemplateDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "taxTemplateHeaderForm", taxTemplateHeaderVO );
         request.setAttribute( "taxTemplateDetailForm", taxTemplateDetailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Form调用，直接传回Form JSP
      return mapping.findForward( "manageTaxTemplateDetailForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 主键获取 - 需解码
            final String templateDetailId = KANUtil.decodeString( request.getParameter( "templateDetailId" ) );

            // 初始化 Service接口
            final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

            // 获取TaxTemplateDetailVO
            final TaxTemplateDetailVO taxTemplateDetailVO = taxTemplateDetailService.getTaxTemplateDetailVOByTemplateDetailId( templateDetailId );

            // 装载界面传值
            taxTemplateDetailVO.update( ( TaxTemplateDetailVO ) form );

            // 修改TaxTemplateDetailVO
            taxTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            taxTemplateDetailService.updateTaxTemplateDetail( taxTemplateDetailVO );

            // 重新加载常量中的TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, taxTemplateDetailVO, Operate.MODIFY, taxTemplateDetailVO.getTemplateDetailId(), null );
         }

         // 清空Form
         ( ( TaxTemplateDetailVO ) form ).reset();
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
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

         // 获得Action Form
         TaxTemplateDetailVO taxTemplateDetailVO = ( TaxTemplateDetailVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( taxTemplateDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, taxTemplateDetailVO, Operate.DELETE, null, taxTemplateDetailVO.getSelectedIds() );
            // 分割
            for ( String selectedId : taxTemplateDetailVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               taxTemplateDetailVO = taxTemplateDetailService.getTaxTemplateDetailVOByTemplateDetailId( selectedId );
               taxTemplateDetailVO.setModifyBy( getUserId( request, response ) );
               taxTemplateDetailVO.setModifyDate( new Date() );
               taxTemplateDetailService.deleteTaxTemplateDetail( taxTemplateDetailVO );
            }
         }

         // 重新加载常量中的TaxTemplateHeader
         constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         ( ( TaxTemplateDetailVO ) form ).setSelectedIds( "" );
         ( ( TaxTemplateDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
