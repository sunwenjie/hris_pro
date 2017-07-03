package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ReportDetailService;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ReportDetailAction extends BaseAction
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
         final ReportDetailVO reportDetailVO = ( ReportDetailVO ) form;

         // 如果子Action是删除
         if ( reportDetailVO.getSubAction() != null && reportDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化ReportDetailService接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

         // 获得主表主键，如若是正常调用，从form中取，否则ajax需解译两次；
         String headerId = request.getParameter( "reportHeaderId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( ReportDetailVO ) form ).getReportHeaderId();
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

         // 根据外键查找，得到ReportDetailVO集合
         reportDetailVO.setReportHeaderId( headerId );
         if ( KANUtil.filterEmpty( reportDetailVO.getSortColumn() ) == null )
         {
            reportDetailVO.setSortColumn( "columnIndex" );
         }
         // 此处分页代码
         final PagedListHolder reportDetailPagedListHolder = new PagedListHolder();
         // 传入当前页
         reportDetailPagedListHolder.setPage( page );
         // 传入当前值对象
         reportDetailPagedListHolder.setObject( reportDetailVO );
         // 设置页面记录条数
         reportDetailPagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         reportDetailService.getReportDetailVOsByCondition( reportDetailPagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( reportDetailPagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "reportDetailPagedListHolder", reportDetailPagedListHolder );

         // Ajax Table调用，直接传回manageReportSelectColumn.jsp
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "manageReportDetail" );
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
            final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

            // 获得headerId
            final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );
            // 获得当前form
            final ReportDetailVO reportDetailVO = ( ReportDetailVO ) form;
            reportDetailVO.setReportHeaderId( headerId );
            // 处理SubAction
            dealSubAction( reportDetailVO, mapping, form, request, response );
            reportDetailVO.setCreateBy( getUserId( request, response ) );
            reportDetailVO.setModifyBy( getUserId( request, response ) );
            reportDetailService.insertReportDetail( reportDetailVO );

            // 返回新增成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, reportDetailVO, Operate.ADD, reportDetailVO.getReportDetailId(), null );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );

            this.saveToken( request );
         }

         // 清空Form
         ( ( ReportDetailVO ) form ).reset();
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
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

         // 主键detailId
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得ReportDetailVO对象
         final ReportDetailVO reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( detailId );

         // 获得ReportHeaderVO对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( reportDetailVO.getReportHeaderId() );

         // 国际化传值
         reportDetailVO.reset( null, request );

         // 获取tableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );

         // 载入columns
         if ( tableDTO != null )
         {
            // 获得ColumnVO MappingVO形式列表
            final List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               reportDetailVO.getColumns().addAll( columnMappingVOs );
            }
         }

         // 分组字段
         if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
         {
            final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );

            if ( jsonObject.get( reportDetailVO.getColumnId() ) != null )
            {
               reportDetailVO.getStatisticsIndex( String.valueOf( jsonObject.get( reportDetailVO.getColumnId() ) ) );
            }
         }

         // 区分修改添加
         reportDetailVO.setSubAction( VIEW_OBJECT );
         // 传入request对象
         request.setAttribute( "reportHeaderForm", reportHeaderVO );
         request.setAttribute( "reportDetailForm", reportDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageReportDetailForm" );
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
            final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

            // 主键获取需解码
            final String reportDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "reportDetailId" ) );
            // 获得当前form
            final ReportDetailVO reportDetailForm = ( ReportDetailVO ) form;
            // 处理SubAction
            dealSubAction( reportDetailForm, mapping, form, request, response );

            // 获得ReportDetailVO对象
            final ReportDetailVO reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( reportDetailId );
            // 装载界面传值
            reportDetailVO.update( reportDetailForm );
            // 获取登录用户
            reportDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            reportDetailService.updateReportDetail( reportDetailVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, reportDetailVO, Operate.MODIFY, reportDetailVO.getReportDetailId(), null );

            this.saveToken( request );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
         }
         // 清空Form
         ( ( ReportDetailVO ) form ).reset();
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
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

         // 获得当前form
         ReportDetailVO reportDetailVO = ( ReportDetailVO ) form;
         final String selectedIds = reportDetailVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // 分割
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 实例化VendorContactVO以删除
                  reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( selectedId );
                  // 调用删除接口
                  reportDetailVO.setModifyBy( getUserId( request, response ) );
                  reportDetailVO.setModifyDate( new Date() );
                  reportDetailService.deleteReportDetail( reportDetailVO );
               }
            }

            insertlog( request, reportDetailVO, Operate.DELETE, null, selectedIds );
            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         reportDetailVO.setSelectedIds( "" );
         reportDetailVO.setSubAction( "" );

         // 返回删除成功标记
         success( request, MESSAGE_TYPE_DELETE, null, MESSAGE_DETAIL );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * 获得有效的选择字段 -废弃
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_available_options_ajax_bak( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取headerId
         final String headerId = KANUtil.decodeString( request.getParameter( "reportHeaderId" ) );

         // 获取tableId
         final String tableId = request.getParameter( "tableId" );

         // 1：选择字段；2：搜索字段
         final String flag = request.getParameter( "flag" );

         // 初始化Service接口
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );
         // final ReportSearchDetailService reportSearchDetailService = (
         // ReportSearchDetailService ) getService(
         // "reportSearchDetailService" );

         // 初始化ReportDetailVO列表
         List< Object > reportDetailVOs = null;

         // 初始化ReportSearchDetailVO列表
         List< Object > reportSearchDetailVOs = null;

         if ( KANUtil.filterEmpty( flag ) != null && flag.equals( "1" ) )
         {
            reportDetailVOs = reportDetailService.getReportDetailVOsByReportHeaderId( headerId );
         }
         else if ( KANUtil.filterEmpty( flag ) != null && flag.equals( "2" ) )
         {
            // reportSearchDetailVOs =
            // reportSearchDetailService.getReportSearchDetailVOsByReportHeaderId(
            // headerId );
         }

         // 初始化tempColumnMappingVOs
         final List< MappingVO > tempColumnMappingVOs = new ArrayList< MappingVO >();
         tempColumnMappingVOs.add( 0, ( ( ReportDetailVO ) form ).getEmptyMappingVO() );

         // 获取tableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );

         if ( tableDTO != null )
         {
            // 获得ColumnVO MappingVO形式列表
            final List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

            final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

            // 滤掉已经存在的字段
            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               for ( MappingVO tempColumnMappingVO : columnMappingVOs )
               {
                  // 标记是否存在
                  boolean exist = false;

                  // 获取ColumnVO
                  final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( tempColumnMappingVO.getMappingId() );

                  // 字段必须为数据库所有或者自定义字段
                  if ( columnVO == null || KANUtil.filterEmpty( columnVO.getAccountId() ) == null || KANUtil.filterEmpty( columnVO.getIsDBColumn() ) == null
                        || columnVO.getIsDBColumn().trim().equals( BaseVO.FALSE ) )
                  {
                     continue;
                  }

                  if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID )
                        || ( !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && ( ( corpId == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( corpId != null && corpId.equals( columnVO.getCorpId() ) ) ) ) )
                  {
                     // 选择字段
                     if ( flag.equals( "1" ) && reportDetailVOs != null && reportDetailVOs.size() > 0 )
                     {
                        for ( Object reportDetailVOObject : reportDetailVOs )
                        {
                           if ( ( ( ReportDetailVO ) reportDetailVOObject ).getColumnId().equals( tempColumnMappingVO.getMappingId() ) )
                           {
                              exist = true;
                              break;
                           }
                        }
                     }
                     // 搜索字段
                     else if ( flag.equals( "2" ) && reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
                     {
                        for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
                        {
                           if ( ( ( ReportSearchDetailVO ) reportSearchDetailVOObject ).getColumnId().equals( tempColumnMappingVO.getMappingId() ) )
                           {
                              exist = true;
                              break;
                           }
                        }
                     }

                     if ( !exist )
                     {
                        tempColumnMappingVOs.add( tempColumnMappingVO );
                     }
                  }
               }
            }
         }

         // Send to client
         out.println( KANUtil.getSelectHTML( tempColumnMappingVOs, "columnId", "columnId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * 获得有效的选择字段
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // siuvan updated to 2014-10-13
   public ActionForward list_available_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取headerId
         final String reportHeaderId = KANUtil.decodeString( request.getParameter( "reportHeaderId" ) );

         // 获取tableId
         final String tableId = request.getParameter( "tableId" );

         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         List< MappingVO > columnMappingVOAll = new ArrayList< MappingVO >();

         columnMappingVOAll.add( 0, ( ( ReportDetailVO ) form ).getEmptyMappingVO() );

         // ReportHeaderVO reportHeaderVO =
         // reportHeaderService.getReportHeaderVOByReportHeaderId(reportHeaderId);
         //主表dto
         TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );
         // List reportColumnList =
         // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());
         // tableDTO.getAllColumnVO();
         List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), null, BaseVO.TRUE );

         setColumnNameWithTableName( ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? tableDTO.getTableVO().getNameZH() : tableDTO.getTableVO().getNameEN() ) + " ("
               + KANUtil.getProperty( request.getLocale(), "define.report.parent" ) + ") ", columnMappingVOs );

         columnMappingVOAll.addAll( columnMappingVOs );

         List< Object > reportRealtionVOList = reportHeaderService.getReportRelationVOsByReportHeaderId( reportHeaderId );

         ReportRelationVO reportRelationVO = null;
         // 子表
         for ( Object object : reportRealtionVOList )
         {

            reportRelationVO = ( ReportRelationVO ) object;
            tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportRelationVO.getSlaveTableId() );
            // List reportColumnList =
            // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());
            // 子表的所有列
            columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), null, BaseVO.TRUE );
            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               setColumnNameWithTableName( ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? tableDTO.getTableVO().getNameZH() : tableDTO.getTableVO().getNameEN() )
                     + " ", columnMappingVOs );

               columnMappingVOAll.addAll( columnMappingVOs );
            }
         }

         // reportSearchDetailVO.setColumns(columnMappingVOAll);

         // Send to client
         out.println( KANUtil.getSelectHTML( columnMappingVOAll, "columnId", "columnId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * 初始化字段下拉框
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_column_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 获取tableId
         final String tableId = request.getParameter( "tableId" );

         // 获得主键ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         // 初始化Service 接口
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         List< Object > reportRelationList = reportHeaderService.getReportRelationVOsByReportHeaderId( headerId );

         // 初始化查找条件
         final ReportDetailVO reportDetailVO = new ReportDetailVO();
         reportDetailVO.setReportHeaderId( headerId );
         reportDetailVO.setStatus( BaseVO.TRUE );
         PagedListHolder reportDetailHolder = new PagedListHolder();
         reportDetailHolder.setObject( reportDetailVO );
         reportDetailService.getReportDetailVOsByCondition( reportDetailHolder, false );

         // 刷新Holder，国际化传值
         refreshHolder( reportDetailHolder, request );

         Map< String, TableDTO > tableDTOMap = new HashMap< String, TableDTO >();
         // 获取tableDTO
         TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );

         tableDTOMap.put( tableDTO.getTableVO().getTableId(), tableDTO );
         // 选择子表
         if ( reportRelationList != null && reportRelationList.size() > 0 )
         {
            for ( Object object : reportRelationList )
            {
               tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( ( ( ReportRelationVO ) object ).getSlaveTableId() );
               tableDTOMap.put( tableDTO.getTableVO().getTableId(), tableDTO );
            }
         }

         // 初始化字段列表
         final List< MappingVO > columnMappingVOs = new ArrayList< MappingVO >();
         columnMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         if ( reportDetailHolder != null && reportDetailHolder.getSource().size() > 0 )
         {
            for ( Object objReportDetailVO : reportDetailHolder.getSource() )
            {
               final ReportDetailVO tempVO = ( ReportDetailVO ) objReportDetailVO;

               // 获取ColumnVO
               final ColumnVO columnVO = tableDTOMap.get( tempVO.getTableId() ).getColumnVOByColumnId( tempVO.getColumnId() );

               if ( columnVO != null && columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && columnVO.getIsDBColumn().trim().equals( BaseVO.TRUE ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempVO.getColumnId() );
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
                  {
                     mappingVO.setMappingValue( tableDTOMap.get( tempVO.getTableId() ).getTableVO().getNameZH() + " " + tempVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( tableDTOMap.get( tempVO.getTableId() ).getTableVO().getNameEN() + " " + tempVO.getNameEN() );
                  }
                  columnMappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getSelectHTML( columnMappingVOs, "columnId", "columnId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * 选择字段change
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward columnId_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取columnId
         final String columnId = request.getParameter( "columnId" );

         // 初始化Service接口
         // final ReportDetailService reportDetailService = (
         // ReportDetailService ) getService( "reportDetailService" );

         // 获得ColumnVO
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getColumnVOByColumnId( columnId );

         // 初始化StringBuffer
         final JSONObject jsonObject = new JSONObject();

         if ( columnVO != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "accountId", columnVO.getAccountId() );
            jsonObject.put( "nameZH", columnVO.getNameZH() );
            jsonObject.put( "nameEN", columnVO.getNameEN() );
            jsonObject.put( "valueType", columnVO.getValueType() );
            jsonObject.put( "inputType", columnVO.getInputType() );
         }
         else
         {
            jsonObject.put( "success", "fasle" );
         }

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * @param tableName
    * @param columnMappingVOs
    */
   private void setColumnNameWithTableName( String tableName, List< MappingVO > columnMappingVOs )
   {

      if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
      {
         for ( MappingVO tempColumnMappingVO : columnMappingVOs )
         {

            // if(columnVO != null
            // && columnVO.getAccountId().equals(
            // KANConstants.SUPER_ACCOUNT_ID)
            // && columnVO.getIsDBColumn().equals(BaseVO.TRUE))
            tempColumnMappingVO.setMappingValue( tableName + tempColumnMappingVO.getMappingValue() );
         }
      }
   }

   /**
    * 初始选择搜索列 下拉
    * 
    * @param reportSearchDetailVO
    * @param request
    * @param response
    * @throws KANException
    */
   protected void getColumns( ReportSearchDetailVO reportSearchDetailVO, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
      String reportHeaderId = reportSearchDetailVO.getReportHeaderId();

      List< MappingVO > columnMappingVOAll = new ArrayList< MappingVO >();

      ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( reportHeaderId );

      TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // List reportColumnList =
      // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());

      List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage() );

      setColumnNameWithTableName( tableDTO.getTableVO().getNameZH() + "(主表)", columnMappingVOs );

      columnMappingVOAll.addAll( columnMappingVOs );

      List< Object > reportRealtionVOList = reportHeaderService.getReportRelationVOsByReportHeaderId( reportHeaderId );

      ReportRelationVO reportRelationVO = null;

      for ( Object object : reportRealtionVOList )
      {

         reportRelationVO = ( ReportRelationVO ) object;
         tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportRelationVO.getSlaveTableId() );
         // List reportColumnList =
         // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());

         columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage() );
         if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
         {

            setColumnNameWithTableName( tableDTO.getTableVO().getNameZH(), columnMappingVOs );

            columnMappingVOAll.addAll( columnMappingVOs );
         }
      }

      reportSearchDetailVO.setColumns( columnMappingVOAll );
   }
}
