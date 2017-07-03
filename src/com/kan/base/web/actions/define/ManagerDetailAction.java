package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.service.inf.define.ManagerDetailService;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ManagerDetailAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_PAGE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );

         // 获得是否Ajax调用
         final String ajax = getAjax( request );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) form;

         // 处理SubAction
         dealSubAction( managerDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = managerDetailVO.getManagerHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final ManagerHeaderVO managerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( headerId );

         // 刷新国际化
         managerHeaderVO.reset( null, request );
         // 设置SubAction
         managerHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "managerHeaderForm", managerHeaderVO );

         // 设置managerHeaderId
         managerDetailVO.setManagerHeaderId( headerId );

         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( managerDetailVO.getSortColumn() == null || managerDetailVO.getSortColumn().isEmpty() )
         {
            managerDetailVO.setSortColumn( "columnIndex" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder managerDetailHolder = new PagedListHolder();
         // 传入当前页
         managerDetailHolder.setPage( page );
         // 传入当前值对象
         managerDetailHolder.setObject( managerDetailVO );
         // 设置页面记录条数
         managerDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         managerDetailService.getManagerDetailVOsByCondition( managerDetailHolder, true );

         // 刷新Holder，国际化传值
         refreshHolder( managerDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "managerDetailHolder", managerDetailHolder );

         // 初始化字段
         final List< MappingVO > columns = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( managerHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

         managerDetailVO.setColumns( columns );
         managerDetailVO.setColumnIndex( "0" );
         managerDetailVO.setDisplay( ManagerDetailVO.TRUE );
         managerDetailVO.setAlign( ManagerDetailVO.TRUE );
         managerDetailVO.setUseTitle( ManagerDetailVO.FALSE );
         managerDetailVO.setStatus( ManagerDetailVO.TRUE );
         managerDetailVO.reset( null, request );
         request.setAttribute( "managerDetailForm", managerDetailVO );

         // 初始化列表中ManagerDetailVO的Columns
         for ( Object managerDetailVOObject : managerDetailHolder.getSource() )
         {
            ( ( ManagerDetailVO ) managerDetailVOObject ).setColumns( columns );
         }

         // Ajax Table调用，直接传回Detail JSP
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listManagerDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listManagerDetail" );
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
            final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

            // 获得managerHeaderId
            final String managerHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "managerHeaderId" ), "UTF-8" ) );

            // 获得当前Form
            final ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) form;
            // 初始化ManagerDetailVO对象
            managerDetailVO.setManagerHeaderId( managerHeaderId );
            managerDetailVO.setCreateBy( getUserId( request, response ) );
            managerDetailVO.setModifyBy( getUserId( request, response ) );
            managerDetailVO.setAccountId( getAccountId( request, response ) );

            // 添加ManagerDetailVO
            managerDetailService.insertManagerDetail( managerDetailVO );

            // 初始化常量持久对象
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, managerDetailVO, Operate.ADD, managerDetailVO.getManagerDetailId(), null );
         }

         // 清空form
         ( ( ManagerDetailVO ) form ).reset();
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
         // 获取managerDetailId
         final String managerDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 初始化Service接口
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // 获取ManagerDetailVO
         final ManagerDetailVO managerDetailVO = managerDetailService.getManagerDetailVOByManagerDetailId( managerDetailId );

         // 获取ManagerHeaderVO
         final ManagerHeaderVO managerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( managerDetailVO.getManagerHeaderId() );

         // 获取ColumnVO
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( managerDetailVO.getColumnId() );

         request.setAttribute( "system_isRequired", ( columnVO != null && columnVO.getIsRequired().equals( "1" ) ) ? "1" : "2" );
         // 国际化传值
         managerDetailVO.reset( null, request );
         // 设置SubAction
         managerDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "managerHeaderForm", managerHeaderVO );
         request.setAttribute( "managerDetailForm", managerDetailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Form调用，直接传回Form JSP
      return mapping.findForward( "manageManagerDetailForm" );
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
            final String managerDetailId = KANUtil.decodeString( request.getParameter( "managerDetailId" ) );

            // 初始化 Service接口
            final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

            // 获取ManagerDetailVO
            final ManagerDetailVO managerDetailVO = managerDetailService.getManagerDetailVOByManagerDetailId( managerDetailId );

            // 装载界面传值
            managerDetailVO.update( ( ManagerDetailVO ) form );

            // 修改ManagerDetailVO
            managerDetailVO.setModifyBy( getUserId( request, response ) );
            managerDetailService.updateManagerDetail( managerDetailVO );

            // 初始化常量持久对象
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, managerDetailVO, Operate.MODIFY, managerDetailVO.getManagerDetailId(), null );
         }

         // 清空Form
         ( ( ManagerDetailVO ) form ).reset();
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
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

         // 获得Action Form
         ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( managerDetailVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : managerDetailVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               final ManagerDetailVO tempManagerDetailVO = managerDetailService.getManagerDetailVOByManagerDetailId( selectedId );
               tempManagerDetailVO.setModifyBy( getUserId( request, response ) );
               tempManagerDetailVO.setModifyDate( new Date() );
               managerDetailService.deleteManagerDetail( tempManagerDetailVO );
            }

            // 初始化常量持久对象
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            insertlog( request, managerDetailVO, Operate.DELETE, null, managerDetailVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         ( ( ManagerDetailVO ) form ).setSelectedIds( "" );
         ( ( ManagerDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /***
    * 
    * 获取页面有效字段列表
    */
   public ActionForward list_column_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取subAction
         final String subAction = request.getParameter( "subAction" );

         // 获取tableId
         final String tableId = request.getParameter( "tableId" );

         // 获取columnId
         final String columnId = request.getParameter( "columnId" );

         // 获取managerHeaderId
         final String managerHeaderId = request.getParameter( "managerHeaderId" );

         // 获取tableId对应字段列表
         final List< MappingVO > columns = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // 初始化Service
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

         // 获取ManagerDetailVO列表
         final List< Object > managerDetailVOs = managerDetailService.getManagerDetailVOsByManagerHeaderId( managerHeaderId );

         // 初始化MappingVO列表
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // 存在列表字段
         if ( columns != null && columns.size() > 0 )
         {
            // 新增需剔除已经存在MangerDetailVO列表的字段
            if ( KANUtil.filterEmpty( subAction ) != null && subAction.equalsIgnoreCase( CREATE_OBJECT ) )
            {
               for ( MappingVO mappingVO : columns )
               {
                  // 计数器
                  int count = 0;
                  // 存在页面字段
                  if ( managerDetailVOs != null && managerDetailVOs.size() > 0 )
                  {
                     for ( Object managerDetailVOObject : managerDetailVOs )
                     {
                        if ( mappingVO.getMappingId().equals( ( ( ManagerDetailVO ) managerDetailVOObject ).getColumnId() ) )
                        {
                           count++;
                           break;
                        }
                     }
                  }

                  if ( count == 0 )
                  {
                     mappingVOs.add( mappingVO );
                  }
               }
            }
            // 修改
            else
            {
               mappingVOs.addAll( columns );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "columnId", columnId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }
}
