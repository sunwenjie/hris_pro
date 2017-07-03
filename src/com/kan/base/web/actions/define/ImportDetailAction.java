package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.service.inf.define.ImportDetailService;
import com.kan.base.service.inf.define.ImportHeaderService;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ImportDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否AJAX调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final ImportDetailVO importDetailVO = ( ImportDetailVO ) form;
         final boolean isDelete = importDetailVO.getSubAction() != null && importDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );

         // 如果子Action是删除
         if ( isDelete )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化ImportDetailService接口
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );

         // 获得主键       
         String importHeaderId = request.getParameter( "importHeaderId" );
         if ( KANUtil.filterEmpty( importHeaderId ) == null )
         {
            importHeaderId = importDetailVO.getImportHeaderId();
         }
         else
         {
            importHeaderId = KANUtil.decodeStringFromAjax( importHeaderId );
         }

         // 根据主键获得 ImportHeaderVO对象
         final ImportHeaderVO importHeaderVO = new ImportHeaderVO();
         importHeaderVO.setImportHeaderId( importHeaderId );
         // 传入request对象
         request.setAttribute( "importHeaderForm", importHeaderVO );

         // 根据外键查找，得到ImportDetailVO集合
         importDetailVO.setImportHeaderId( importHeaderId );
         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( importDetailVO.getSortColumn() == null || importDetailVO.getSortColumn().isEmpty() )
         {
            importDetailVO.setSortColumn( "columnIndex" );
         }

         ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, BaseAction.getCorpId( request, null ) );
         TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );
         // 初始化字段
         final List< MappingVO > columns = tableDTO.getCanImportColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
         importDetailVO.setColumns( columns );

         // 初始化ColumnVO对象
         final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );//columnService.getColumnVOByColumnId( importDetailVO.getColumnId() );
         request.setAttribute( "columnVO", columnVO );

         // 添加空的下拉选项，即“请选择”
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         importDetailVO.reset( mapping, request );
         importDetailVO.setSubAction( CREATE_OBJECT );
         // 如果从表不要过滤掉已经使用的Column
         if ( KANUtil.filterEmpty( importDTO.getImportHeaderVO().getParentId(), "0" ) == null )
         {
            importDetailVO.setColumns( filterUsedColumns( columns, importDTO.getImportDetailVOs() ) );
         }

         // 此处分页代码
         final PagedListHolder importDetailHolder = new PagedListHolder();
         // 传入当前页
         importDetailHolder.setPage( page );
         // 传入当前值对象
         importDetailHolder.setObject( importDetailVO );
         // 设置页面记录条数
         importDetailHolder.setPageSize( listPageSize_large );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         importDetailService.getImportDetailVOsByCondition( importDetailHolder, true );
         // 初始化列表中ImportDetail的Columns
         for ( Object importDetailVOObject : importDetailHolder.getSource() )
         {
            ( ( ImportDetailVO ) importDetailVOObject ).setColumns( columns );
         }
         // 刷新Holder，国际化传值
         refreshHolder( importDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "importDetailHolder", importDetailHolder );

         importDetailVO.setSubAction( "" );
         importDetailVO.setImportHeaderId( importHeaderId );
         importDetailVO.setColumnWidth( "14" );
         importDetailVO.setFontSize( "13" );
         importDetailVO.setIsDecoded( ImportDetailVO.FALSE );
         importDetailVO.setAlign( "1" );
         importDetailVO.setSort( ImportDetailVO.TRUE );
         importDetailVO.setStatus( ImportDetailVO.TRUE );
         // ImportDetail写入Request对象
         request.setAttribute( "importDetailForm", importDetailVO );

         if ( isDelete )
         {
            return mapping.findForward( "manageImportSelectColumn" );
         }
         else if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "manageImportDetailTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "manageImportSelectColumn" );
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

            //初始化Service 接口
            final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );

            // 获得ImportHeaderId
            final String importHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );

            // 获得当前Form
            final ImportDetailVO importDetailVO = ( ImportDetailVO ) form;

            // 处理SubAction
            dealSubAction( importDetailVO, mapping, form, request, response );
            // 初始化ImportDetailVO对象
            importDetailVO.setImportHeaderId( importHeaderId );
            importDetailVO.setCreateBy( getUserId( request, response ) );
            importDetailVO.setModifyBy( getUserId( request, response ) );
            importDetailVO.setAccountId( getAccountId( request, response ) );
            importDetailService.insertImportDetail( importDetailVO );

            // 初始化常量持久对象
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            importDetailVO.reset();

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, importDetailVO, Operate.ADD, importDetailVO.getImportDetailId(), null );

            list_object( mapping, importDetailVO, request, response );
            this.saveToken( request );

            return mapping.findForward( "manageImportSelectColumn" );
         }
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
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         // 需要得到一个ImportHeader，所以需要初始化ImportHeaderService接口
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         // 获取主键 - 需解码
         final String importDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "importDetailId" ) );

         // 初始化ImportDetailVO对象
         ImportDetailVO importDetailVO = new ImportDetailVO();
         if ( importDetailId != null && !importDetailId.trim().equals( "" ) )
         {
            importDetailVO = importDetailService.getImportDetailVOByImportDetailId( importDetailId );
         }

         importDetailVO.reset( null, request );

         // 根据主键获得 ImportHeaderVO对象
         final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( importDetailVO.getImportHeaderId() );
         request.setAttribute( "importHeaderForm", importHeaderVO );
         if ( importHeaderVO != null )
         {
            final List< MappingVO > columns = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( importHeaderVO.getTableId() ).getCanImportColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
            importDetailVO.setColumns( columns );
         }

         importDetailVO.setSubAction( VIEW_OBJECT );
         // 写入Request
         request.setAttribute( "importDetailForm", importDetailVO );

         // 初始化ColumnVO对象
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( importDetailVO.getColumnId() );
         request.setAttribute( "columnVO", columnVO );

         return mapping.findForward( "manageImportSelectColumnForm" );
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
            final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );

            // 获取主键 - 需解码
            final String importDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "importDetailId" ) );
            // 获得ImportDetailVO对象
            final ImportDetailVO importDetailVO = importDetailService.getImportDetailVOByImportDetailId( importDetailId );
            // 装载界面传值
            importDetailVO.update( ( ImportDetailVO ) form );
            // 处理SubAction
            dealSubAction( importDetailVO, mapping, form, request, response );
            // 获取登录用户
            importDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            importDetailService.updateImportDetail( importDetailVO );

            // 初始化常量持久对象
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, importDetailVO, Operate.MODIFY, importDetailVO.getImportDetailId(), null );

            importDetailVO.reset();

            list_object( mapping, importDetailVO, request, response );

            return mapping.findForward( "manageImportSelectColumn" );
         }

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
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );

         // 获得当前form
         ImportDetailVO importDetailVO = ( ImportDetailVO ) form;
         // 存在选中的ID
         if ( importDetailVO.getSelectedIds() != null && !importDetailVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, importDetailVO, Operate.DELETE, null, importDetailVO.getSelectedIds() );
            // 分割
            for ( String selectedId : importDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               importDetailVO = importDetailService.getImportDetailVOByImportDetailId( selectedId );
               importDetailVO.setModifyBy( getUserId( request, response ) );
               importDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               importDetailService.deleteImportDetail( importDetailVO );
            }

            // 初始化常量持久对象
            constantsInit( "initImportHeader", getAccountId( request, response ) );
         }

         success( request, MESSAGE_TYPE_DELETE );

         // 清除Selected IDs和子Action
         importDetailVO.setSelectedIds( "" );
         importDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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

         // 获得ColumnVO
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getColumnVOByColumnId( columnId );

         // 初始化StringBuffer
         final JSONObject jsonObject = new JSONObject();

         if ( columnVO != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "nameZH", columnVO.getNameZH() );
            jsonObject.put( "nameEN", columnVO.getNameEN() );
            jsonObject.put( "valueType", columnVO.getValueType() );
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

   /***
    * 过滤掉已经添加的字段
   *  filterUsedColumns
   *  
   *  @param allColumns
   *  @param usedColumns
   *  @return
    */
   public static List< MappingVO > filterUsedColumns( final List< MappingVO > allColumns, final List< ImportDetailVO > usedColumns )
   {
      List< MappingVO > columns = new ArrayList< MappingVO >();
      if ( allColumns != null )
      {
         if ( usedColumns == null || usedColumns.size() == 0 )
         {
            columns.addAll( allColumns );
         }
         else
         {
            for ( MappingVO columnMappingVO : allColumns )
            {
               boolean isExist = false;
               for ( ImportDetailVO importDetailVO : usedColumns )
               {
                  if ( columnMappingVO.getMappingId().equals( importDetailVO.getColumnId() ) )
                  {
                     isExist = true;
                  }
               }
               if ( !isExist )
               {
                  columns.add( columnMappingVO );
               }
            }
         }
      }
      return columns;
   }

   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final String importHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );

         // 初始化Service接口
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         final List< Object > importDetails = importDetailService.getImportDetailVOsByImportHeaderId( importHeaderId );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, BaseAction.getCorpId( request, null ) );
         if ( importDTO == null )
         {
            return null;
         }
         TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );
         StringBuffer result = new StringBuffer();
         // 初始化字段
         final List< MappingVO > columns = tableDTO.getCanImportColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
         for ( int i = 0; i < columns.size(); i++ )
         {
            for ( int j = 0; j < importDetails.size(); j++ )
            {
               // 勾选
               if ( columns.get( i ).getMappingId().equals( ( ( ImportDetailVO ) importDetails.get( j ) ).getColumnId() ) )
               {
                  result.append( "<li>" );
                  result.append( "<input type='checkbox' name='selectColumns' checked='checked'  value='" + columns.get( i ).getMappingId() + "'>" );
                  result.append( columns.get( i ).getMappingValue() );
                  result.append( "</li>" );
                  break;
               }
               // 不勾选
               if ( j == importDetails.size() - 1 )
               {
                  result.append( "<li>" );
                  result.append( "<input type='checkbox' name='selectColumns'  value='" + columns.get( i ).getMappingId() + "'>" );
                  result.append( columns.get( i ).getMappingValue() );
                  result.append( "</li>" );
               }
            }
            if ( importDetails.size() == 0 )
            {
               result.append( "<li>" );
               result.append( "<input type='checkbox' name='selectColumns'  value='" + columns.get( i ).getMappingId() + "'>" );
               result.append( columns.get( i ).getMappingValue() );
               result.append( "</li>" );
            }

         }
         out.print( result.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public ActionForward quickChoose( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         String[] selectColumns = request.getParameterValues( "selectColumns" );
         final String importHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );
         // 初始化Service接口
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         // 修改detail为删除
         importDetailService.deleteImportDetail( importHeaderId );
         // 获取所有的table DTO
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, BaseAction.getCorpId( request, null ) );
         TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );
         for ( String selectColumn : selectColumns )
         {
            // 获取当前column对应的columnVO
            final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( selectColumn );

            final ImportDetailVO importDetailVO = new ImportDetailVO();

            // 初始化ImportDetailVO对象
            importDetailVO.setColumnId( columnVO.getColumnId() );
            importDetailVO.setImportHeaderId( importHeaderId );
            importDetailVO.setNameZH( columnVO.getNameZH() );
            importDetailVO.setNameEN( columnVO.getNameEN() );
            importDetailVO.setColumnWidth( "14" );
            importDetailVO.setColumnIndex( columnVO.getColumnIndex() );
            importDetailVO.setFontSize( "13" );
            importDetailVO.setIsDecoded( "2" );
            importDetailVO.setAlign( "1" );
            importDetailVO.setStatus( "1" );
            importDetailVO.setDescription( columnVO.getDescription() );
            importDetailVO.setCreateBy( getUserId( request, response ) );
            importDetailVO.setModifyBy( getUserId( request, response ) );
            importDetailVO.setAccountId( getAccountId( request, response ) );
            importDetailService.insertImportDetail( importDetailVO );
         }
         // 初始化常量持久对象
         constantsInit( "initImportHeader", getAccountId( request, response ) );

         // 返回添加成功标记
         success( request, MESSAGE_TYPE_ADD );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      list_object( mapping, form, request, response );
      return mapping.findForward( "manageImportSelectColumn" );
   }
}