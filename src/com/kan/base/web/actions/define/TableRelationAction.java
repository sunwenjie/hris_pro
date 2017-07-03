package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TableRelationService;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class TableRelationAction extends BaseAction
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
         // 获得Action Form
         final TableRelationVO tableRelationVO = ( TableRelationVO ) form;

         // 如果没有指定排序则默认按 tableIndex排序
         if ( tableRelationVO.getSortColumn() == null || tableRelationVO.getSortColumn().isEmpty() )
         {
            tableRelationVO.setSortColumn( "moduleType,tableIndex" );
            tableRelationVO.setSortOrder( "asc" );
         }

         // 调用删除方法
         if ( tableRelationVO.getSubAction() != null && tableRelationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( tableRelationVO );
         }

         // 初始化Service接口
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder tablePagedListHolder = new PagedListHolder();
         // 传入当前页
         tablePagedListHolder.setPage( page );
         // 传入当前值对象
         tablePagedListHolder.setObject( tableRelationVO );
         // 设置页面记录条数
         tablePagedListHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         tableRelationVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         tableRelationService.getTableRelationVOsByCondition( tablePagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( tablePagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "tablePagedListHolder", tablePagedListHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax 调用，重新传入AccountId
            request.setAttribute( "accountId", getAccountId( request, response ) );
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listTableRelationTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listTableRelation" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );
      final TableRelationVO tableRelationVO = ( TableRelationVO ) form;
      tableRelationVO.reset( mapping, request );
      // 设置Sub Action
      tableRelationVO.setSubAction( CREATE_OBJECT );
      request.setAttribute( "tableRelationForm", tableRelationVO );

      // 初始化PagedListHolder，用于引用方式调用Service
      PagedListHolder tablePagedListHolder = new PagedListHolder();
      refreshHolder( tablePagedListHolder, request );

      // Holder需写入Request对象
      request.setAttribute( "tablePagedListHolder", tablePagedListHolder );

      final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
      request.setAttribute( "tablelist", tablelist );

      // 跳转到新建界面
      return mapping.findForward( "manageTableRelation" );
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
            final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
            // 获得当前FORM
            final TableRelationVO tableRelationVO = ( TableRelationVO ) form;
            String masterTableId = tableRelationVO.getMasterTableId();
            List< Object > tableRelationVOs = tableRelationService.getTableRelationVOsByCondition( tableRelationVO );
            if ( tableRelationVOs == null || tableRelationVOs.size() == 0 )
            {
               tableRelationVO.setCreateBy( getUserId( request, response ) );
               tableRelationVO.setModifyBy( getUserId( request, response ) );
               tableRelationVO.setAccountId( getAccountId( request, response ) );
               tableRelationVO.setDeleted( "1" );
               tableRelationVO.setStatus( "1" );

               // 添加TableRelationVO
               tableRelationService.insertTableRelationVO( tableRelationVO );
               final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
               request.setAttribute( "tablelist", tablelist );
               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD );
            }
            else
            {
               error( request, MESSAGE_TYPE_ADD, "已有相关报表关系请勿重复添加 。" );
            }

            // 清空Action Form
            ( ( TableRelationVO ) form ).reset();
            ( ( TableRelationVO ) form ).setTableRelationId( null );
            ( ( TableRelationVO ) form ).setMasterTableId( masterTableId );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final TableRelationVO tableRelationVO = ( TableRelationVO ) form;

         // 如果没有指定排序则默认按 tableIndex排序
         if ( tableRelationVO.getSortColumn() == null || tableRelationVO.getSortColumn().isEmpty() )
         {
            tableRelationVO.setSortColumn( "tableRelationId" );
            tableRelationVO.setSortOrder( "desc" );
         }

         // 调用删除方法
         if ( tableRelationVO.getSubAction() != null && tableRelationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_object( mapping, form, request, response );
         }
         else
         {
            decodedObject( tableRelationVO );
         }

         // 初始化Service接口
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder tablePagedListHolder = new PagedListHolder();
         // 传入当前页
         tablePagedListHolder.setPage( page );
         // 传入当前值对象
         tablePagedListHolder.setObject( tableRelationVO );
         // 设置页面记录条数
         tablePagedListHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         tableRelationVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         tableRelationService.getTableRelationVOsByCondition( tablePagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( tablePagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "tablePagedListHolder", tablePagedListHolder );
         request.setAttribute( "tableRelationForm", tableRelationVO );

         final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );

         request.setAttribute( "tablelist", tablelist );

         ( ( TableRelationVO ) form ).setSubAction( MODIFY_OBJECT );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {

            request.setAttribute( "accountId", getAccountId( request, response ) );
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listTableRelationDetailTable" );

         }

         this.saveToken( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "manageTableRelation" );
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
            final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
            // 主键获取需解码
            final String tableRelationId = request.getParameter( "tableRelationId" );
            final TableRelationVO tableRelationForm = ( TableRelationVO ) form;
            String masterTableId = tableRelationForm.getMasterTableId();
            // 获取TableRelationVO对象
            final TableRelationVO tableRelationVO = tableRelationService.getTableRelationVOsByTableRelationId( tableRelationId );

            if ( tableRelationVO != null && tableRelationVO.getTableRelationId() != null )
            {
               tableRelationVO.update( tableRelationForm );
               tableRelationVO.setStatus( "1" );
               // Checkbox处理
               // 获取登录用户
               tableRelationVO.setModifyBy( getUserId( request, response ) );
               // 调用修改方法
               tableRelationService.updateTableRelationVO( tableRelationVO );

               masterTableId = tableRelationVO.getMasterTableId();
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );

            }
            else
            {

               tableRelationForm.setCreateBy( getUserId( request, response ) );
               tableRelationForm.setModifyBy( getUserId( request, response ) );
               tableRelationForm.setAccountId( getAccountId( request, response ) );
               tableRelationForm.setDeleted( "1" );
               tableRelationForm.setStatus( "1" );

               // 添加TableRelationVO
               tableRelationService.insertTableRelationVO( tableRelationForm );
               final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
               request.setAttribute( "tablelist", tablelist );
               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD );
            }

            // 清空Form
            ( ( TableRelationVO ) form ).reset();
            ( ( TableRelationVO ) form ).setTableRelationId( null );
            ( ( TableRelationVO ) form ).setMasterTableId( masterTableId );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         this.saveToken( request );

         final TableRelationVO tableRelationForm = ( TableRelationVO ) form;
         // 初始化Service接口
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );

         final TableRelationVO tableRelationVO = tableRelationService.getTableRelationVOsByTableRelationId( tableRelationForm.getTableRelationId() );
         tableRelationVO.reset( null, request );
         request.setAttribute( "tableRelationForm", tableRelationVO );

         final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
         request.setAttribute( "tablelist", tablelist );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageTableRelationSelectColumnForm" );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 初始化Service接口
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
         // 获得Action Form            
         final TableRelationVO tableRelationVO = ( TableRelationVO ) form;

         String masterTableId = tableRelationVO.getMasterTableId();
         // 存在选中的ID
         if ( tableRelationVO.getTableRelationId() != null && KANUtil.filterEmpty( tableRelationVO.getTableRelationId() ) != null )
         {

            tableRelationService.deleteTableRelationVO( tableRelationVO.getTableRelationId() );

         }
         // 清除Selected IDs和子Action
         tableRelationVO.setSubAction( "" );
         // 清空Form
         ( ( TableRelationVO ) form ).reset();
         ( ( TableRelationVO ) form ).setTableRelationId( null );
         ( ( TableRelationVO ) form ).setMasterTableId( masterTableId );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

   }

   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final String tableId = request.getParameter( "tableId" );
         final String columnId = request.getParameter( "columnId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         if ( KANUtil.filterEmpty( tableId ) != null )
         {
            TableDTO tableDTO = accountConstants.getTableDTOByTableId( tableId );
            // 初始化字段
            final List< MappingVO > columns = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
            // Send to client

            final StringBuffer rs = new StringBuffer();
            boolean selected = false;

            if ( columns != null && columns.size() > 0 )
            {
               if ( columns.size() == 1 )
               {
                  selected = true;
               }
               for ( MappingVO mappingVO : columns )
               {
                  String nameDB = tableDTO.getColumnVOByColumnId( mappingVO.getMappingId() ).getNameDB();
                  rs.append( "<option id=\"option_columnId_" + mappingVO.getMappingId() + "\" value=\"" + nameDB + "\" "
                        + ( ( columnId != null && columnId.trim().equals( nameDB ) || selected ) ? "selected" : "" ) + ">" + nameDB + "(" + mappingVO.getMappingValue()
                        + ")</option>" );
               }
            }

            out.println( rs.toString() );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return null;
   }
}