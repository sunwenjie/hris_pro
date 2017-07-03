package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ReportColumnVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableColumnSubVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableRelationSubVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.page.PagedReportListHolder;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.define.ReportRender;

public class ReportHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_REPORT";

   //   private void report_clicks_increase( final HttpServletRequest request, final ReportHeaderService reportHeaderService ) throws KANException
   //   {
   //      // 获取主键
   //      final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
   //
   //      // 获取ReportHeaderVO
   //      final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
   //
   //      if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getClicks() ) != null )
   //      {
   //         reportHeaderVO.setClicks( String.valueOf( Integer.valueOf( reportHeaderVO.getClicks() ) + 1 ) );
   //      }
   //
   //      reportHeaderService.updateReportHeader( reportHeaderVO, null );
   //   }

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;

         // 如果子Action是删除
         if ( reportHeaderVO.getSubAction() != null && reportHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( reportHeaderVO );
         }
         // 初始化Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder reportHeaderPagedListHolder = new PagedListHolder();
         // 传入当前页
         reportHeaderPagedListHolder.setPage( page );
         // 传入当前值对象
         reportHeaderPagedListHolder.setObject( reportHeaderVO );
         // 设置页面记录条数
         reportHeaderPagedListHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         reportHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         reportHeaderService.getReportHeaderVOsByCondition( reportHeaderPagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( reportHeaderPagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "reportHeaderPagedListHolder", reportHeaderPagedListHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "accountId", getAccountId( request, null ) );
            return mapping.findForward( "listReportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listReportHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 初始化Form属性
      ( ( ReportHeaderVO ) form ).setStatus( ReportHeaderVO.TRUE );
      ( ( ReportHeaderVO ) form ).setExportExcelType( "3" );
      ( ( ReportHeaderVO ) form ).setIsExportPDF( ReportHeaderVO.FALSE );
      ( ( ReportHeaderVO ) form ).setIsSearchFirst( ReportHeaderVO.FALSE );
      ( ( ReportHeaderVO ) form ).setUsePagination( "on" );
      ( ( ReportHeaderVO ) form ).setPageSize( "15" );
      ( ( ReportHeaderVO ) form ).setLoadPages( String.valueOf( this.loadPages ) );
      ( ( ReportHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      request.setAttribute( "reportDetailPagedListHolder", new PagedListHolder() );

      // 跳转到新建界面
      return mapping.findForward( "manageReportHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

            // 获得当前FORM
            final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;
            // 处理SubAction
            dealSubAction( reportHeaderVO, mapping, form, request, response );
            // 设定当前用户
            reportHeaderVO.setCreateBy( getUserId( request, response ) );
            reportHeaderVO.setModifyBy( getUserId( request, response ) );
            // Checkbox处理
            if ( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.TRUE );
            }
            else
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.FALSE );
            }
            // 调用添加方法
            reportHeaderService.insertReportHeader( reportHeaderVO );
            // 写入request对象
            request.setAttribute( "reportHeaderForm", reportHeaderVO );

            // 返回保存成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, reportHeaderVO, Operate.ADD, reportHeaderVO.getReportHeaderId(), null );
         }
         else
         {
            // 清空form
            ( ( ReportHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 添加成功直接去修改界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加需设定一个记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 主键获取，从request，form中
         String headerId = request.getParameter( "id" );
         ReportHeaderVO reportHeaderVO = null;
         // 修改
         if ( KANUtil.filterEmpty( headerId ) != null )
         {
            headerId = Cryptogram.decodeString( URLDecoder.decode( headerId, "UTF-8" ) );
            reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
         }
         else
         {
            reportHeaderVO = ( ReportHeaderVO ) form;
            headerId = reportHeaderVO.getReportHeaderId();
         }

         // 装载子表
         this.loadSubTable( reportHeaderVO, request, response );
         // 装载column 必须先初始子表
         this.loadColumn( reportHeaderVO, request, response );
         // 获得ReportHeaderVO
         // 刷新对象，初始化对象列表及国际化
         reportHeaderVO.reset( null, request );
         // 区分修改添加
         reportHeaderVO.setSubAction( VIEW_OBJECT );

         // 获得对应ReportSearchDetailVO列表并写入request
         final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
         reportSearchDetailVO.setReportHeaderId( headerId );
         new ReportSearchDetailAction().list_object( mapping, reportSearchDetailVO, request, response );

         // 设置分页字段的Checkbox
         reportHeaderVO.setUsePagination( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equals( ReportHeaderVO.TRUE ) ? "on" : "" );
         // 写入request对象
         request.setAttribute( "reportHeaderForm", reportHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageReportHeader" );
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
            final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对象
            final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
            // 修改时tableId 不能修改
            // String tableId = reportHeaderVO.getTableId();
            // 装载界面传值
            reportHeaderVO.update( ( ( ReportHeaderVO ) form ) );
            // Checkbox处理
            if ( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.TRUE );
            }
            else
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.FALSE );
            }
            // 获取登录用户
            reportHeaderVO.setModifyBy( getUserId( request, response ) );

            // 装载所有表子表的column 用于在删除子表时级联删除 排序和组函数
            Map< String, Map< String, ColumnVO >> tableColumnMap = null;

            if ( StringUtils.isNotBlank( reportHeaderVO.getSortColumns() ) )
            {

               getSubTableColumnsMap( reportHeaderVO, request, response );

            }
            // 调用修改方法
            reportHeaderService.updateReportHeader( reportHeaderVO, tableColumnMap );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), null );
         }
         // 清空Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * 添加排序字段
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_add_sort( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 获得主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // 获得主键对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         // 获得ActionForm
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         // 获得字段ID
         final String columnId = request.getParameter( "columnId" );

         if ( KANUtil.filterEmpty( reportHeaderVO.getSortColumns() ) != null )
         {
            final JSONObject json = JSONObject.fromObject( reportHeaderVO.getSortColumns() );
            json.accumulate( columnId, reportHeaderForm.getDecodeSortColumns() );
            reportHeaderVO.setSortColumns( json.toString() );
         }
         else
         {
            final String sortString = "{" + columnId + ":\"" + reportHeaderForm.getDecodeSortColumns() + "\"}";
            reportHeaderVO.setSortColumns( JSONObject.fromObject( sortString ).toString() );
         }

         // 获取登录用户
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // 调用修改方法
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // 返回添加成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_HEADER_SORT" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), null );

         // 清空Form
         ( ( ReportHeaderVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用直接返回JSP页面
      return mapping.findForward( "manageReportHeaderSpecifySort" );
   }

   /**
    * 移除排序字段
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_remove_sort( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 获得主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // 获得字段ID
         final String columnId = request.getParameter( "columnId" );

         // 获得主键对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );
         jsonObject.remove( columnId );

         if ( jsonObject.keySet().size() == 0 )
         {
            reportHeaderVO.setSortColumns( null );
         }
         else
         {
            reportHeaderVO.setSortColumns( jsonObject.toString() );
         }

         // 获取登录用户
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // 调用修改方法
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // 返回删除成功标记
         success( request, MESSAGE_TYPE_DELETE, null, "MESSAGE_HEADER_SORT" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_remove_sort" );

         // 清空Form
         ( ( ReportHeaderVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用直接返回JSP页面
      return mapping.findForward( "manageReportHeaderSpecifySort" );
   }

   /**
    * 添加分组字段
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_add_group( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 获得主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // 获得ActionForm
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         // 获得字段ID
         final String columnId = request.getParameter( "columnId" );

         // 获得主键对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         if ( KANUtil.filterEmpty( columnId ) != null )
         {
            // 原对象存在分组字段
            if ( KANUtil.filterEmpty( reportHeaderVO.getGroupColumns() ) != null )
            {
               final String oldStr = reportHeaderVO.getGroupColumns().replace( "{", "" ).replace( "}", "" ) + ":" + columnId;
               reportHeaderVO.setGroupColumns( KANUtil.toJasonArray( oldStr.split( ":" ) ) );
            }
            else
            {
               reportHeaderVO.setGroupColumns( "{" + columnId + "}" );
            }

            JSONObject jsonObject = null;
            // 原对象存在统计字段
            if ( KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
            {
               jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );
               jsonObject.put( columnId, reportHeaderForm.getDecodeStatisticsColumns() );
            }
            else
            {
               jsonObject = new JSONObject();
               jsonObject.put( columnId, reportHeaderForm.getDecodeStatisticsColumns() );
            }

            reportHeaderVO.setStatisticsColumns( jsonObject.toString() );

            // 获取登录用户
            reportHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            reportHeaderService.updateReportHeader( reportHeaderVO, null );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_HEADER_GROUP" );

            insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_add_group" );
         }

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // 清空Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用直接返回JSP页面
      return mapping.findForward( "manageReportHeaderSpecifyGroup" );
   }

   /**
    * 移除分组字段
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_remove_group( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 获得主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // 获取columnId
         final String columnId = request.getParameter( "columnId" );

         // 获得主键对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         final List< String > groupColumns = KANUtil.jasonArrayToStringList( reportHeaderVO.getGroupColumns() );

         final List< String > tempGroupColumns = new ArrayList< String >();

         if ( groupColumns != null && groupColumns.size() > 0 )
         {
            for ( String groupColumn : groupColumns )
            {
               if ( !groupColumn.equals( columnId ) )
               {
                  tempGroupColumns.add( groupColumn );
               }
            }
         }

         reportHeaderVO.setGroupColumns( KANUtil.stringListToJasonArray( tempGroupColumns ) );

         // 如果原对象存在统计字段
         if ( KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
         {
            final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );
            for ( Object objKey : jsonObject.keySet() )
            {
               if ( String.valueOf( objKey ).equals( columnId ) )
               {
                  jsonObject.remove( objKey );
                  if ( jsonObject.size() == 0 )
                  {
                     reportHeaderVO.setStatisticsColumns( null );
                  }
                  else
                  {
                     reportHeaderVO.setStatisticsColumns( jsonObject.toString() );
                  }
                  break;
               }
            }
         }

         // 获取登录用户
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // 调用修改方法
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // 返回删除成功标记
         success( request, MESSAGE_TYPE_DELETE, null, "MESSAGE_HEADER_GROUP" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_remove_group" );

         // 清空Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用直接返回JSP页面
      return mapping.findForward( "manageReportHeaderSpecifyGroup" );
   }

   /**
    * 确认发布
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_publish( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 获得主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // 获得主键对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         // 获得当前form
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         reportHeaderVO.setIsPublic( reportHeaderForm.getIsPublic() );
         reportHeaderVO.setModuleType( reportHeaderForm.getModuleType() );
         reportHeaderVO.setPositionIds( KANUtil.toJasonArray( reportHeaderForm.getPositionIdArray() ) );
         reportHeaderVO.setPositionGradeIds( KANUtil.toJasonArray( reportHeaderForm.getPositionGradeIdArray() ) );
         reportHeaderVO.setPositionGroupIds( KANUtil.toJasonArray( reportHeaderForm.getGroupIdArray() ) );

         // 设置为发布状态
         reportHeaderVO.setStatus( "2" );
         // 获取登录用户
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         // 调用修改方法
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         // 修改后对象写入request作用域
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // 返回添加成功标记
         success( request, MESSAGE_TYPE_UPDATE, "发布成功！", "MESSAGE_HEADER_PUBLISH" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_publish" );

         // 初始化常量持久对象
         String[] parametersT = { getAccountId( request, response ), reportHeaderVO.getTableId() };
         constantsInit( "initTableReport", parametersT );
         String[] parametersM = { getAccountId( request, response ), reportHeaderVO.getReportHeaderId() };
         // 初始化常量持久对象
         constantsInit( "initStaffMenu", parametersM );
         // 清空Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用返回JSP页面
      return mapping.findForward( "manageReportHeaderConfirmPublish" );
   }

   /**
    * 执行报表
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward execute_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         // final TableService tableService = (TableService)
         // getService("tableService");
         // 点击率增长
         // report_clicks_increase( request, reportHeaderService );

         // 获取模块名称，用于CSS样式
         final String moduleName = request.getParameter( "moduleName" );

         // 获得当前页
         final String page = request.getParameter( "page" );

         // 当前Form
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         // 获得SubAction
         String subAction = getSubAction( form );
         if ( StringUtils.isBlank( subAction ) )
         {
            subAction = request.getParameter( "subAction" );
            if ( StringUtils.isBlank( subAction ) )
            {
               subAction = "searchObject";
            }
         }

         // 处理SubAction
         dealSubAction( reportHeaderForm, mapping, form, request, response );

         // 获得是否Ajax调用
         String ajax = getAjax( request );

         // 获取主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // 获得主键对象
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         request.setAttribute( "headerId", headerId );
         request.setAttribute( "tableId", reportHeaderVO.getTableId() );
         reportHeaderVO.setAccountId( getAccountId( request, response ) );
         reportHeaderVO.setCorpId( getCorpId( request, response ) );

         //写入排序 
         request.setAttribute( "sortColumn", reportHeaderForm.getSortColumn() );
         request.setAttribute( "sortOrder", reportHeaderForm.getSortOrder() );
         //报表的默认排序 json 
         request.setAttribute( "defaultSortColumn", reportHeaderVO.getSortColumns() );
         // 批准生成
         if ( subAction.equals( APPROVE_OBJECT ) && StringUtils.isBlank( page ) )
         {
            // 第一次执行初始初始缓存
            // 发布时在更新缓冲
            String[] parameters = { getAccountId( request, response ), reportHeaderVO.getTableId() };
            constantsInit( "initTableReport", parameters );
            // 获取TableDTO 数据库中取得最新
            // tableDTO =
            // tableService.getTableDTOByTableId(BaseAction.getAccountId(request,
            // null), reportHeaderVO.getTableId());
         }

         final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );

         // 报表DTO
         final ReportDTO tempReportDTO = tableDTO.getReportDTOByReportHeaderId( headerId );
         // 菜单选中
         if ( KANUtil.filterEmpty( moduleName ) != null )
         {
            request.setAttribute( "moduleName", moduleName );
         }

         // 初始化PagedListHolder
         final PagedReportListHolder pagedListHolder = new PagedReportListHolder();

         // // 反射得到当前报表对象
         // final Class<?> clazz = Class.forName(tableDTO.getTableVO()
         // .getClassName());
         // Object object = clazz.newInstance();

         // 初始化子表关系表
         List< ReportRelationVO > reportRelationList = tempReportDTO.getReportRelationVOs();
         Map< String, TableDTO > subTableMap = new HashMap< String, TableDTO >();

         Map< String, ColumnVO > columnMap = new HashMap< String, ColumnVO >();

         TableDTO subTableDTO = null;
         // 转载所有columnVO 包括 子表
         // 主表
         for ( ColumnVO columnVO : tableDTO.getAllColumnVO() )
         {
            columnMap.put( columnVO.getColumnId(), columnVO );
         }

         // 装载子表
         for ( ReportRelationVO reportRelationVO : reportRelationList )
         {

            subTableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportRelationVO.getSlaveTableId() );
            subTableMap.put( reportRelationVO.getSlaveTableId(), subTableDTO );
            // 子表 列
            for ( ColumnVO columnVO : subTableDTO.getAllColumnVO() )
            {
               columnMap.put( columnVO.getColumnId(), columnVO );
            }

         }

         // final ReportDTO tempReportDTO =
         // tableDTO.getReportDTOByReportHeaderId( headerId );

         // 获取ReportSearchDetailVO列表
         final List< ReportSearchDetailVO > reportSearchDetailVOs = tableDTO.getReportDTOByReportHeaderId( headerId ).getReportSearchDetailVOs();

         // 设置排序
         // KANUtil.setValue(object, "sortColumn",
         // reportHeaderForm.getSortColumn());
         // KANUtil.setValue(object, "sortOrder",
         // reportHeaderForm.getSortOrder());

         // 如果是搜索优先
         if ( !tempReportDTO.isSearchFirst() || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS )
               || subAction.equalsIgnoreCase( APPROVE_OBJECT ) )
         {
            // 初始化临时ReportDTO
            ReportDTO reportDTO = null;
            // 初始化临时ReportSearchDetailVO列表
            List< ReportSearchDetailVO > tempReportSearchDetailVOs = new ArrayList< ReportSearchDetailVO >();
            reportDTO = new ReportDTO();
            // 页面排序
            String sortColumns = null;
            // 如果是搜素
            if ( ( subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( APPROVE_OBJECT ) || new Boolean( getAjax( request ) ) )
                  && !tempReportDTO.isExportFirstAndSearchFirst() )
            {

               // 存在排序字段
               if ( KANUtil.filterEmpty( reportHeaderForm.getSortColumn() ) != null && KANUtil.filterEmpty( reportHeaderForm.getSortOrder() ) != null )
               {
                  sortColumns = reportHeaderForm.getSortColumn() + " " + reportHeaderForm.getSortOrder();
                  // reportHeaderVO.setSortColumns();
               }

               // 装载ReportHeaderVO
               reportDTO.setReportHeaderVO( reportHeaderVO );

               // List<ReportSearchDetailVO>
               if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
               {
                  // 遍历缓存中ReportSearchDetailVO列表
                  for ( ReportSearchDetailVO reportSearchDetailVO : reportSearchDetailVOs )
                  {
                     // 初始化字段对象
                     final ColumnVO columnVO = columnMap.get( reportSearchDetailVO.getColumnId() );

                     // 初始化临时ReportSearchDetailVO
                     final ReportSearchDetailVO tempReportSearchDetailVO = new ReportSearchDetailVO();

                     // // 装载临时ReportSearchDetailVO
                     tempReportSearchDetailVO.update( reportSearchDetailVO );

                     // request作用域获取当前字段
                     String id = "T_" + columnVO.getTableId() + "_" + columnVO.getColumnId();
                     String columnValue = request.getParameter( id );
                     String provinceId = null;
                     // 如果是省份
                     if ( columnVO.getNameDB().equals( "cityId" ) )
                     {
                        // 如果选择了城市 为cityId 只选择了省为选城市 值加上省的前缀
                        provinceId = request.getParameter( id + "_provinceId" );
                        tempReportSearchDetailVO.setTempStr( provinceId );

                        request.setAttribute( id + "_provinceId", provinceId );
                     }

                     if ( KANUtil.filterEmpty( columnValue ) != null )
                     {
                        columnValue = URLDecoder.decode( URLDecoder.decode( columnValue, "UTF-8" ), "UTF-8" );
                     }

                     // 重新写入request
                     request.setAttribute( id, columnValue == null ? "" : columnValue );

                     // 从request作用获取该值
                     tempReportSearchDetailVO.setContent( columnValue );
                     // tempReportSearchDetailVO.setSortColumn(sortColumns);
                     tempReportSearchDetailVOs.add( tempReportSearchDetailVO );
                  }
               }

               // 装载ReportSearchDetailVO列表
               // reportDTO
               // .setReportSearchDetailVOs(tempReportSearchDetailVOs);
               // 生成SQL语句
            }
            //ReportRender.generateReportSQL( request, response, reportHeaderVO, tableDTO, subTableMap, columnMap, tempReportSearchDetailVOs, sortColumns );
            final String sql = getDecodeSQL();
            //getSQL() 加密

            // 传入当前页
            pagedListHolder.setPage( page );
            // 传入当前form
            pagedListHolder.setObject( HashMap.class );
            // 设置页面记录条数
            pagedListHolder.setPageSize( Integer.valueOf( reportHeaderVO.getPageSize() ) );
            // 调用Service方法
            reportHeaderService.executeReportHeader( sql, pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : tempReportDTO.isPaged() );
            // 数显国际化
            // refreshHolsder(pagedListHolder, request);
            // }
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "columnMap", columnMap );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportReport( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // 初始化PrintWrite对象
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ReportRender.generateReportListTable( request, tableDTO, headerId ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }

         // 跳转JSP执行页面
         return mapping.findForward( "generateReportList" );
         // } else {
         // error(request, null, "发布后才能执行！", MESSAGE_HEADER);
         // return to_objectModify(mapping, form, request, response);
         // }
      }
      catch ( final Exception e )
      {
         error( request, null, "报表生成失败,请检查报表。", MESSAGE_HEADER );
         // System.out.println();
         LogFactory.getLog( ReportHeaderAction.class ).info( "Generate report error :" + e.getMessage() );
         return to_objectModify( mapping, form, request, response );
      }
   }

   /**
    * 原本是hro_biz_employee_full_view 
    * 现在需要解密
    * @return
    */
   private String getDecodeSQL()
   {
      StringBuffer sb = new StringBuffer();
      sb.append( "SELECT T_76.employeeId AS T_76_7654,T_76.nameZH AS T_76_7601,T_76.salutation AS T_76_7602,T_76.contractId AS T_76_7663,T_76.contractStatus AS T_76_7667,T_76.salary AS T_76_7668,T_76.allowance AS T_76_7669,T_76.subsidy AS T_76_7670  FROM  " );
      sb.append( " ( SELECT " );
      sb.append( "`a`.`employeeId` AS `employeeId`," );
      sb.append( "`a`.`accountId` AS `accountId`," );
      sb.append( "`a`.`corpId` AS `corpId`," );
      sb.append( "`a`.`employeeNo` AS `employeeNo`," );
      sb.append( "`a`.`nameZH` AS `nameZH`," );
      sb.append( "`a`.`nameEN` AS `nameEN`," );
      sb.append( "`a`.`salutation` AS `salutation`," );
      sb.append( "`a`.`birthday` AS `birthday`," );
      sb.append( "`a`.`maritalStatus` AS `maritalStatus`," );
      sb.append( "`a`.`nationNality` AS `nationNality`," );
      sb.append( "`a`.`birthdayPlace` AS `birthdayPlace`," );
      sb.append( "`a`.`residencyCityId` AS `residencyCityId`," );
      sb.append( "`a`.`residencyAddress` AS `residencyAddress`," );
      sb.append( "`a`.`personalAddress` AS `personalAddress`," );
      sb.append( "`a`.`personalPostcode` AS `personalPostcode`," );
      sb.append( "`a`.`highestEducation` AS `highestEducation`," );
      sb.append( "`a`.`recordNo` AS `recordNo`," );
      sb.append( "`a`.`recordAddress` AS `recordAddress`," );
      sb.append( "`a`.`residencyType` AS `residencyType`," );
      sb.append( "`a`.`graduationDate` AS `graduationDate`," );
      sb.append( "`a`.`startWorkDate` AS `startWorkDate`," );
      sb.append( "`a`.`hasForeignerWorkLicence` AS `hasForeignerWorkLicence`," );
      sb.append( "`a`.`foreignerWorkLicenceNo` AS `foreignerWorkLicenceNo`," );
      sb.append( "`a`.`foreignerWorkLicenceEndDate` AS `foreignerWorkLicenceEndDate`," );
      sb.append( "`a`.`hasResidenceLicence` AS `hasResidenceLicence`," );
      sb.append( "`a`.`residenceNo` AS `residenceNo`," );
      sb.append( "`a`.`residenceStartDate` AS `residenceStartDate`," );
      sb.append( "`a`.`residenceEndDate` AS `residenceEndDate`," );
      sb.append( "`a`.`certificateType` AS `certificateType`," );
      sb.append( "`a`.`certificateNumber` AS `certificateNumber`," );
      sb.append( "`a`.`certificateStartDate` AS `certificateStartDate`," );
      sb.append( "`a`.`certificateEndDate` AS `certificateEndDate`," );
      sb.append( "`a`.`certificateAwardFrom` AS `certificateAwardFrom`," );
      sb.append( "`a`.`bankId` AS `bankId`," );
      sb.append( "`a`.`bankAccount` AS `bankAccount`," );
      sb.append( "`a`.`phone1` AS `phone1`," );
      sb.append( "`a`.`mobile1` AS `mobile1`," );
      sb.append( "`a`.`email1` AS `email1`," );
      sb.append( "`a`.`website1` AS `website1`," );
      sb.append( "`a`.`phone2` AS `phone2`," );
      sb.append( "`a`.`mobile2` AS `mobile2`," );
      sb.append( "`a`.`email2` AS `email2`," );
      sb.append( "`a`.`website2` AS `website2`," );
      sb.append( "`a`.`im1Type` AS `im1Type`," );
      sb.append( "`a`.`im1` AS `im1`," );
      sb.append( "`a`.`im2Type` AS `im2Type`," );
      sb.append( "`a`.`im2` AS `im2`," );
      sb.append( "`a`.`im3Type` AS `im3Type`," );
      sb.append( "`a`.`im3` AS `im3`," );
      sb.append( "`a`.`im4Type` AS `im4Type`," );
      sb.append( "`a`.`im4` AS `im4`," );
      sb.append( "`a`.`branch` AS `branch`," );
      sb.append( "`a`.`owner` AS `owner`," );
      sb.append( "`a`.`photo` AS `photo`," );
      sb.append( "`a`.`attachment` AS `attachment`," );
      sb.append( "`a`.`resumeZH` AS `resumeZH`," );
      sb.append( "`a`.`resumeEN` AS `resumeEN`," );
      sb.append( "`a`.`description` AS `description`," );
      sb.append( "`a`.`deleted` AS `deleted`," );
      sb.append( "`a`.`status` AS `status`," );
      sb.append( "`a`.`remark1` AS `remark1`," );
      sb.append( "`a`.`remark2` AS `remark2`," );
      sb.append( "`a`.`remark3` AS `remark3`," );
      sb.append( "`a`.`remark4` AS `remark4`," );
      sb.append( "`a`.`remark5` AS `remark5`," );
      sb.append( "`a`.`createBy` AS `createBy`," );
      sb.append( "`a`.`createDate` AS `createDate`," );
      sb.append( "`a`.`modifyBy` AS `modifyBy`," );
      sb.append( "`a`.`modifyDate` AS `modifyDate`," );
      sb.append( "`b`.`contractId` AS `contractId`," );
      sb.append( "`b`.`startDate` AS `startDate`," );
      sb.append( "`b`.`endDate` AS `endDate`," );
      sb.append( "`b`.`resignDate` AS `resignDate`," );
      sb.append( "`b`.`status` AS `contractStatus`," );
      //
      sb.append( "SUM((CASE WHEN ((`c`.`itemId` = 1) OR (`c`.`itemId` = 2))" );
      sb.append( "THEN (`c`.`base` - GETINCREMENT(GETPUBLICCODE(`a`.`employeeId`)," );
      sb.append( "'" + KANConstants.PRIVATE_CODE + "')) ELSE 0 END)) AS `salary`," );
      //
      sb.append( "SUM((CASE WHEN ((`c`.`itemId` = 6) OR (`c`.`itemId` = 7)" );
      sb.append( "OR (`c`.`itemId` = 8) OR (`c`.`itemId` = 9))" );
      sb.append( "THEN (`c`.`base` - GETINCREMENT(GETPUBLICCODE(`a`.`employeeId`)," );
      sb.append( "'" + KANConstants.PRIVATE_CODE + "')) ELSE 0 END)) AS `allowance`," );
      //
      sb.append( "SUM((CASE  WHEN ((`c`.`itemId` = 10)" );
      sb.append( "OR (`c`.`itemId` = 11) OR (`c`.`itemId` = 12) OR (`c`.`itemId` = 13))" );
      sb.append( "THEN (`c`.`base` - GETINCREMENT(GETPUBLICCODE(`a`.`employeeId`)," );
      sb.append( "'" + KANConstants.PRIVATE_CODE + "')) ELSE 0 END)) AS `subsidy`" );
      //
      sb.append( "FROM  ((`hro_biz_employee` `a`" );
      sb.append( "LEFT JOIN `hro_biz_employee_contract` `b` ON ((`a`.`employeeId` = `b`.`employeeId`)))" );
      sb.append( "LEFT JOIN `hro_biz_employee_contract_salary` `c` ON ((`b`.`contractId` = `c`.`contractId`)))" );
      sb.append( "WHERE (ISNULL(`b`.`contractId`) OR `b`.`contractId` IN (SELECT " );
      sb.append( "MAX(`hro_biz_employee_contract`.`contractId`)" );
      sb.append( "FROM `hro_biz_employee_contract`" );
      sb.append( "WHERE (`hro_biz_employee_contract`.`status` IN (3 , 5, 6))" );
      sb.append( "GROUP BY `hro_biz_employee_contract`.`employeeId`))" );
      sb.append( "GROUP BY `a`.`employeeId`" );
      sb.append( " ) T_76  WHERE T_76.deleted = 1  AND T_76.accountId = 100017" );
      return sb.toString();
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
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // 获得Action Form
         ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;
         // 存在选中的ID
         if ( reportHeaderVO.getSelectedIds() != null && !reportHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, reportHeaderVO, Operate.DELETE, null, reportHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : reportHeaderVO.getSelectedIds().split( "," ) )
            {
               reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( selectedId );
               reportHeaderVO.setModifyBy( getUserId( request, response ) );
               reportHeaderVO.setModifyDate( new Date() );
               reportHeaderService.deleteReportHeader( reportHeaderVO );
            }

            // 初始化常量持久对象
            String[] parametersT = { getAccountId( request, response ), reportHeaderVO.getTableId() };
            constantsInit( "initTableReport", parametersT );
            String[] parametersM = { getAccountId( request, response ), reportHeaderVO.getReportHeaderId() };
            // 初始化常量持久对象
            constantsInit( "initStaffMenu", parametersM );
         }

         // 清除Selected IDs和子Action
         reportHeaderVO.setSelectedIds( "" );
         reportHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward tableId_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得tableId
         final String tableId = request.getParameter( "tableId" );
         // 获得TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );
         // 初始化JSONObject
         final JSONObject jsonObject = new JSONObject();

         if ( tableDTO != null && tableDTO.getTableVO() != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "nameZH", tableDTO.getTableVO().getNameZH() );
            jsonObject.put( "nameEN", tableDTO.getTableVO().getNameEN() );

            // 设置主表 对应的从表 modify steven 2014-04025 tableRelationVO 对象属性多
            // 简化页面数据所以使用tableRelationSubVO
            jsonObject.put( "unSelectTables", tableDTO.getTableRelationSubVOs() );
         }
         else
         {
            jsonObject.put( "success", "false" );
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

   public ActionForward tableId_change_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得tableId
         final String tableId = request.getParameter( "id" );

         // 初始化ReportSearchHeaderVO列表
         final List< MappingVO > searchHeaderVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSearchHeadersByTableId( tableId, KANUtil.filterEmpty( getCorpId( request, null ) ), request.getLocale().getLanguage() );
         searchHeaderVOs.add( 0, ( ( ReportHeaderVO ) form ).getEmptyMappingVO() );

         // Send to client
         out.println( KANUtil.getSelectHTML( searchHeaderVOs, "searchHeaderId", "searchHeaderId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   @SuppressWarnings("unchecked")
   public ActionForward condition_change_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得columnId
         final String columnId = request.getParameter( "columnId" );

         // 获得ColumnVO
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getColumnVOByColumnId( columnId );

         // 初始化MappingVO列表
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // 初始化StringBuffer
         final StringBuffer rs = new StringBuffer();

         if ( columnVO != null )
         {
            // 下拉框类型 - 系统常量
            if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "1" ) )
            {
               // 获得系统常量选项列表
               final List< MappingVO > systemOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.system" );
               // 遍历系统常量选项
               if ( systemOptions != null && systemOptions.size() > 0 )
               {
                  for ( MappingVO systemOption : systemOptions )
                  {
                     // 获得系统常量选项
                     if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
                     {
                        mappingVOs = KANUtil.getMappings( request.getLocale(), systemOption.getMappingTemp() );
                        break;
                     }
                  }
               }
            }
            // 下拉框类型 - 账户常量
            else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "2" ) )
            {
               // 获得账户常量选项列表
               final List< MappingVO > accountOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.account" );
               // 遍历账户常量选项
               if ( accountOptions != null && accountOptions.size() > 0 )
               {
                  for ( MappingVO accountOption : accountOptions )
                  {
                     // 获得账户常量选项
                     if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
                     {
                        // 初始化Parameter Array
                        String parameters[];

                        if ( KANUtil.filterEmpty( getClientId( request, response ) ) != null )
                        {
                           parameters = new String[] { request.getLocale().getLanguage(), getClientId( request, response ) };
                        }
                        else
                        {
                           parameters = new String[] { request.getLocale().getLanguage() };
                        }

                        mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ), accountOption.getMappingTemp(), parameters );
                        // 添加空的MappingVO对象
                        mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                        break;
                     }
                  }
               }
            }
            // 下拉框类型 - 用户自定义
            else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "3" ) )
            {
               mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( request.getLocale().getLanguage() );
            }
            // 下拉框类型 - 直接值
            else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "4" ) )
            {
               // 如果用户定义的直接值并且不为空
               if ( columnVO.getOptionValue() != null && !columnVO.getOptionValue().trim().equals( "" ) )
               {
                  // 将用户定义的直接值转为JSONObject
                  final JSONObject optionsJSONObject = JSONObject.fromObject( columnVO.getOptionValue().replace( "[{", "{" ).replace( "}]", "}" ) );
                  // 遍历JSONObject
                  final Iterator< ? > keyIterator = optionsJSONObject.keys();
                  while ( keyIterator.hasNext() )
                  {
                     final String key = ( String ) keyIterator.next();
                     // 初始化MappingVO
                     final MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( key );
                     mappingVO.setMappingValue( optionsJSONObject.getString( key ) );
                     // 添加MappingVO至List
                     mappingVOs.add( mappingVO );
                  }
               }
            }

            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "define.report.detail.condition" ) + " </label>" );

            if ( mappingVOs != null && mappingVOs.size() > 0 )
            {
               // 特殊处理， 如果字段是所属部门、省份 - 城市
               if ( KANUtil.filterEmpty( columnVO.getNameDB() ) != null && columnVO.getNameDB().equals( "branch" ) )
               {
                  rs.append( KANUtil.getSelectHTML( mappingVOs, "branch", "small branch", null, null, null ) );

                  // 初始化预留下拉框
                  final List< MappingVO > tempMappingVOs = new ArrayList< MappingVO >();
                  tempMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

                  rs.append( KANUtil.getSelectHTML( tempMappingVOs, "owner", "small owner", null, null, null ) );
               }
               else if ( KANUtil.filterEmpty( columnVO.getNameDB() ) != null && columnVO.getNameDB().equals( "provinceId" ) )
               {
                  rs.append( KANUtil.getSelectHTML( mappingVOs, "provinceId", "provinceId", null, null, null ) );
               }
               else
               {
                  rs.append( "<select name=\"content\" id=\"\" class=\"manageReportSearchDetail_content\">" );
                  rs.append( KANUtil.getOptionHTML( mappingVOs, "content", null ) );
                  rs.append( "</select>" );
               }
            }
            else
            {
               rs.append( "<input type=\"text\" name=\"content\" maxlength=\"100\" class=\"manageReportSearchDetail_content\" />" );
            }
         }

         // Send to client
         out.print( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public ActionForward list_options_ajax_byAccountId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化 Service
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();

         final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();

         reportHeaderVO.setAccountId( getAccountId( request, response ) );

         pagedListHolder.setObject( reportHeaderVO );

         reportHeaderService.getReportHeaderVOsByCondition( pagedListHolder, false );

         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         MappingVO mappingVO = null;

         ReportHeaderVO tempReportHeaderVO = null;
         if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               mappingVO = new MappingVO();
               tempReportHeaderVO = ( ReportHeaderVO ) object;
               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingId( tempReportHeaderVO.getReportHeaderId() );
                  mappingVO.setMappingValue( tempReportHeaderVO.getNameZH() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingId( tempReportHeaderVO.getReportHeaderId() );
                  mappingVO.setMappingValue( tempReportHeaderVO.getNameEN() );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         final String reportId = request.getParameter( "reportId" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "reportId", KANUtil.filterEmpty( reportId ) == null ? "0" : reportId ) );
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
    * 修改装载子表
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @throws KANException
    * @throws InvocationTargetException
    * @throws IllegalAccessException
    */
   private void loadSubTable( ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response ) throws KANException, IllegalAccessException,
         InvocationTargetException
   {

      // 初始化Service接口
      final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

      // 已选择子表
      List< Object > reportRelationVOs = reportHeaderService.getReportRelationVOsByReportHeaderId( reportHeaderVO.getReportHeaderId() );

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // 所有子表
      List< TableRelationSubVO > tableRelationSubVOs = tableDTO.getTableRelationSubVOs();

      // 装配
      List< TableRelationSubVO > unSelectTableList = new ArrayList< TableRelationSubVO >();
      List< TableRelationSubVO > selectTableList = new ArrayList< TableRelationSubVO >();
      ReportRelationVO reportRelationVO = null;
      TableRelationSubVO tempTableRelationSubVO = null;
      if ( reportRelationVOs != null && reportRelationVOs.size() > 0 )
      {

         int flag = 0;
         for ( TableRelationSubVO tableRelationSubVO : tableRelationSubVOs )
         {
            flag = 0;
            tempTableRelationSubVO = new TableRelationSubVO();
            BeanUtils.copyProperties( tempTableRelationSubVO, tableRelationSubVO );
            for ( Object object : reportRelationVOs )
            {
               reportRelationVO = ( ReportRelationVO ) object;
               if ( tempTableRelationSubVO.getSlaveTableId() != null && reportRelationVO.getSlaveTableId() != null
                     && tempTableRelationSubVO.getSlaveTableId().equals( reportRelationVO.getSlaveTableId() ) )
               {
                  tempTableRelationSubVO.setReportRelationId( reportRelationVO.getReportRelationId() );
                  selectTableList.add( tempTableRelationSubVO );
                  flag = 1;
                  break;
               }
            }
            if ( 0 == flag )
            {
               unSelectTableList.add( tempTableRelationSubVO );
            }
         }
      }
      else
      {
         unSelectTableList = tableRelationSubVOs;
      }
      reportHeaderVO.setSelectTablesJson( JSONArray.fromObject( selectTableList ).toString() );
      reportHeaderVO.setUnSelectTablesJson( JSONArray.fromObject( unSelectTableList ).toString() );
   }

   /**
    * 初始表columns
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @throws KANException
    */
   private void loadColumn( ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
      final List< Object > reportColumnList = reportHeaderService.getReportColumnVOsByReportHeaderId( reportHeaderVO.getReportHeaderId() );
      final List< TableColumnSubVO > tableColumnSubVOList = new ArrayList< TableColumnSubVO >();
      // 表 列关系 用于页面显示
      TableColumnSubVO tableColumnSubVO = null;
      List< ColumnVO > clolumnVOList = null;
      ReportDetailVO reportDetailVO = null;
      // 主表
      String masterTableId = reportHeaderVO.getTableId();
      tableColumnSubVO = new TableColumnSubVO();

      tableColumnSubVO.setTableId( reportHeaderVO.getTableId() );
      // 是主表
      tableColumnSubVO.setIsMasterTable( 1 );
      // 获取主表的列
      TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( masterTableId );

      tableColumnSubVO.setNameEN( tableDTO.getTableVO().getNameEN() );
      tableColumnSubVO.setNameZH( tableDTO.getTableVO().getNameZH() );

      ReportColumnVO reportColumnVO = null;
      if ( tableDTO != null )
      {
         clolumnVOList = tableDTO.getAllColumnVO();

         if ( clolumnVOList != null && clolumnVOList.size() > 0 )
         {
            for ( ColumnVO columnVO : clolumnVOList )
            {
               // 是数据库字段
               if ( columnVO != null && ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) || columnVO.getAccountId().equals( getAccountId( request, null ) ) ) )
               {
                  reportColumnVO = new ReportColumnVO();
                  reportColumnVO.setValue( columnVO );
                  for ( Object object : reportColumnList )
                  {
                     reportDetailVO = ( ReportDetailVO ) object;
                     // 列id相等，
                     if ( reportDetailVO.getColumnId().equals( columnVO.getColumnId() ) )
                     {
                        reportColumnVO.setValue( reportDetailVO );
                     }
                  }
                  // 主表列的装配
                  tableColumnSubVO.getReportColumnVOList().add( reportColumnVO );
               }
            }
         }
      }
      // 主表的列
      tableColumnSubVOList.add( tableColumnSubVO );

      if ( reportHeaderVO.getSelectTablesJson() != null && StringUtils.isNotBlank( reportHeaderVO.getSelectTablesJson() ) )
      {

         JSONArray array = JSONArray.fromObject( reportHeaderVO.getSelectTablesJson() );
         TableRelationSubVO[] selectTables = ( TableRelationSubVO[] ) JSONArray.toArray( array, TableRelationSubVO.class );

         if ( selectTables != null && selectTables.length > 0 )
         {
            // 子表
            for ( TableRelationSubVO tableRelationSubVO : selectTables )
            {
               tableColumnSubVO = new TableColumnSubVO();
               tableColumnSubVO.setNameEN( tableRelationSubVO.getSlaveTableNameEN() );
               tableColumnSubVO.setNameZH( tableRelationSubVO.getSlaveTableNameZH() );
               tableColumnSubVO.setTableId( tableRelationSubVO.getSlaveTableId() );
               masterTableId = tableRelationSubVO.getSlaveTableId();
               // 获取子表的列
               tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( masterTableId );
               if ( tableDTO != null )
               {
                  clolumnVOList = tableDTO.getAllColumnVO();

                  if ( clolumnVOList != null && clolumnVOList.size() > 0 )
                  {
                     for ( ColumnVO columnVO : clolumnVOList )
                     {
                        if ( columnVO != null
                              && ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) || columnVO.getAccountId().equals( getAccountId( request, null ) ) ) )
                        {

                           reportColumnVO = new ReportColumnVO();
                           reportColumnVO.setValue( columnVO );
                           for ( Object object : reportColumnList )
                           {
                              reportDetailVO = ( ReportDetailVO ) object;
                              // 列id相等，
                              if ( reportDetailVO.getColumnId().equals( columnVO.getColumnId() ) )
                              {
                                 reportColumnVO.setValue( reportDetailVO );
                              }
                           }
                           // 主表列的装配
                           tableColumnSubVO.getReportColumnVOList().add( reportColumnVO );
                        }
                     }
                  }
               }
               // 主表的列
               tableColumnSubVOList.add( tableColumnSubVO );
            }
         }
      }

      // 载入columns
      reportHeaderVO.setUnSelectColumnsJson( JSONArray.fromObject( tableColumnSubVOList ).toString() );
      reportHeaderVO.setSelectColumnsJson( reportHeaderVO.getUnSelectColumnsJson() );
   }

   public ActionForward add_column( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交s
         // if ( this.isTokenValid( request, true ) )
         // {
         // 初始化Service 接口
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // // 获得headerId
         // final String headerId = KANUtil.decodeStringFromAjax(
         // request.getParameter( "reportHeaderId" ) );

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
         // 获得当前form
         ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;
         reportHeaderVO.setReportHeaderId( headerId );
         // 处理SubAction
         dealSubAction( reportHeaderVO, mapping, form, request, response );
         // 获取登录用户
         reportHeaderVO.setCreateBy( getUserId( request, response ) );
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // 更新列
         reportHeaderService.updateReportColumn( reportHeaderVO );
         // 获得ReportHeaderVO
         // reportHeaderVO =
         // reportHeaderService.getReportHeaderVOByReportHeaderId( headerId
         // );

         // 更新columns 的状态
         this.loadColumn( reportHeaderVO, request, response );
         // 返回新增成功标记
         success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

         // 初始化常量持久对象
         String[] parameters = { getAccountId( request, response ), reportHeaderVO.getTableId() };
         constantsInit( "initTableReport", parameters );

         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // // 清空Form
         // ((ReportHeaderVO) form).reset();
         // }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageReportDetail" );
   }

   // Refresh the page holder for message resource

   /**
    * 获取子表的column 的集合
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private Map< String, Map< String, ColumnVO >> getSubTableColumnsMap( final ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      Map< String, Map< String, ColumnVO >> tableColumnMap = new HashMap< String, Map< String, ColumnVO >>();
      // 初始化Service接口

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // 所有子表
      List< TableRelationSubVO > tableRelationSubVOs = tableDTO.getTableRelationSubVOs();
      Map< String, ColumnVO > columnMap = null;
      List< ColumnVO > columnVOList = null;
      ColumnVO columnVO = null;
      // 装配所有子表的column
      if ( tableRelationSubVOs != null && tableRelationSubVOs.size() > 0 )
      {
         for ( TableRelationSubVO tableRelationSubVO : tableRelationSubVOs )
         {
            columnVOList = tableDTO.getAllColumnVO();
            if ( columnVOList != null && columnVOList.size() > 0 )
            {
               columnMap = new HashMap< String, ColumnVO >();
               for ( Object object : columnVOList )
               {
                  columnVO = ( ColumnVO ) object;
                  columnMap.put( columnVO.getColumnId(), columnVO );
               }
               tableColumnMap.put( tableRelationSubVO.getSlaveTableId(), columnMap );
            }
         }
      }

      return tableColumnMap;

   }
}
