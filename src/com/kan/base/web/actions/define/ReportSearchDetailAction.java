package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.service.inf.define.ReportSearchDetailService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ReportSearchDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得Action Form
         final ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) form;

         // 如果子Action是删除
         if ( reportSearchDetailVO.getSubAction() != null && reportSearchDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

         // 获得主表主键，如若是正常调用，从form中取，否则ajax需解译两次；       
         String headerId = request.getParameter( "reportHeaderId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( ReportSearchDetailVO ) form ).getReportHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得ReportHeaderVO
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
         reportHeaderVO.reset( null, request );
         reportHeaderVO.setSubAction( VIEW_OBJECT );
         // 设置分页字段的Checkbox
         reportHeaderVO.setUsePagination( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equals( ReportHeaderVO.TRUE ) ? "on" : "" );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // 根据外键查找，得到ReportSearchDetailVO集合
         reportSearchDetailVO.setReportHeaderId( headerId );
         // 此处分页代码
         PagedListHolder reportSearchDetailPagedListHolder = new PagedListHolder();
         // 传入当前页
         reportSearchDetailPagedListHolder.setPage( page );
         // 传入当前值对象
         reportSearchDetailPagedListHolder.setObject( reportSearchDetailVO );
         // 设置页面记录条数
         reportSearchDetailPagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         reportSearchDetailService.getReportSearchDetailVOsByCondition( reportSearchDetailPagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( reportSearchDetailPagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "reportSearchDetailPagedListHolder", reportSearchDetailPagedListHolder );

         // Ajax Table调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回 JSP
            return mapping.findForward( "manageReportDetailSearch" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
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
            final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

            // 获取主键
            final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

            // 获取城市ID
            final String cityId = request.getParameter( "cityId" );

            // 获得当前form
            final ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) form;
            reportSearchDetailVO.setReportHeaderId( headerId );

            if ( KANUtil.filterEmpty( cityId ) != null )
            {
               reportSearchDetailVO.setContent( cityId );
            }
            // 处理SubAction
            dealSubAction( reportSearchDetailVO, mapping, form, request, response );
            reportSearchDetailVO.setCreateBy( getUserId( request, response ) );
            reportSearchDetailVO.setModifyBy( getUserId( request, response ) );
            reportSearchDetailService.insertReportSearchDetail( reportSearchDetailVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_SEARCH" );

            insertlog( request, reportSearchDetailVO, Operate.ADD, reportSearchDetailVO.getReportSearchDetailId(), null );

            // 初始化常量持久对象
            //            constantsInit( "initTable", getAccountId( request, response ) );

            this.saveToken( request );
         }
         // 清空Form
         ( ( ReportSearchDetailVO ) form ).reset();
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
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

         // 主键主表ID
         final String searchDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得ReportSearchDetailVO对象
         final ReportSearchDetailVO reportSearchDetailVO = reportSearchDetailService.getReportSearchDetailVOByReportSearchDetailId( searchDetailId );

         // 获得ReportHeaderVO对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( reportSearchDetailVO.getReportHeaderId() );

         // 国际化传值
         reportSearchDetailVO.reset( null, request );

         // 获取tableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );

         // 载入columns
         if ( tableDTO != null )
         {
            // 获得ColumnVO MappingVO形式列表
            final List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               reportSearchDetailVO.getColumns().addAll( columnMappingVOs );
            }
         }

         // 区分修改添加
         reportSearchDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "reportHeaderForm", reportHeaderVO );
         request.setAttribute( "reportSearchDetailForm", reportSearchDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageReportSearchDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
            // 初始化Service接口
            final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

            // 主键获取需解码
            final String reportSearchDetailId = Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( request.getParameter( "reportSearchDetailId" ), "UTF-8" ), "UTF-8" ) );
            // 获得当前form
            final ReportSearchDetailVO reportSearchDetailForm = ( ReportSearchDetailVO ) form;
            // 处理SubAction
            dealSubAction( reportSearchDetailForm, mapping, form, request, response );

            // 获得ReportSearchDetailVO对象
            final ReportSearchDetailVO reportSearchDetailVO = reportSearchDetailService.getReportSearchDetailVOByReportSearchDetailId( reportSearchDetailId );
            // 装载界面传值
            reportSearchDetailVO.update( reportSearchDetailForm );
            // 获取登录用户
            reportSearchDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            reportSearchDetailService.updateReportSearchDetail( reportSearchDetailVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_SEARCH" );

            insertlog( request, reportSearchDetailVO, Operate.MODIFY, reportSearchDetailVO.getReportSearchDetailId(), null );

            this.saveToken( request );

            // 初始化常量持久对象
            //            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // 清空Form
         ( ( ReportSearchDetailVO ) form ).reset();
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
         final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

         // 获得当前form
         ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) form;
         final String selectedIds = reportSearchDetailVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // 分割
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 实例化VendorContactVO以删除
                  final ReportSearchDetailVO tempReportSearchDetailVO = reportSearchDetailService.getReportSearchDetailVOByReportSearchDetailId( selectedId );
                  // 调用删除接口
                  tempReportSearchDetailVO.setModifyBy( getUserId( request, response ) );
                  tempReportSearchDetailVO.setModifyDate( new Date() );
                  reportSearchDetailService.deleteReportSearchDetail( tempReportSearchDetailVO );
               }
            }

            insertlog( request, reportSearchDetailVO, Operate.DELETE, null, reportSearchDetailVO.getSelectedIds() );
            // 初始化常量持久对象
            //            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // 返回删除成功标记
         success( request, MESSAGE_TYPE_DELETE, null, "MESSAGE_SEARCH" );

         // 清除Selected IDs和子Action
         reportSearchDetailVO.setSelectedIds( "" );
         reportSearchDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
