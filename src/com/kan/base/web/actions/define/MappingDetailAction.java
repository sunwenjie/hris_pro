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

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ImportDetailService;
import com.kan.base.service.inf.define.MappingDetailService;
import com.kan.base.service.inf.define.MappingHeaderService;
import com.kan.base.service.inf.define.ReportDetailService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class MappingDetailAction extends BaseAction
{
	public static final String accessAction = "HRO_CLIENT_IMPORT";
   private void setColumns( final HttpServletRequest request, final String flag, final String headerId ) throws KANException
   {
      // 初始化columns列表
      final List< MappingVO > columns = new ArrayList< MappingVO >();
      columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

      // 导入字段
      if ( KANUtil.filterEmpty( flag ) != null && flag.trim().equals( "import" ) )
      {
         // 获取ImportDTO
         final ImportDTO importDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getImportDTOByImportHeadId( headerId );

         // 存在ImportDTO
         if ( importDTO != null && importDTO.getImportDetailVOs() != null && importDTO.getImportDetailVOs().size() > 0 )
         {
            for ( ImportDetailVO importDetailVO : importDTO.getImportDetailVOs() )
            {
               final MappingVO mappingVO = new MappingVO();

               mappingVO.setMappingId( importDetailVO.getImportDetailId() );

               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( importDetailVO.getNameZH() );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingValue( importDetailVO.getNameEN() );
               }

               columns.add( mappingVO );
            }
         }
      }
      else if ( KANUtil.filterEmpty( flag ) != null && flag.trim().equals( "export" ) )
      {
         // 获取ReportDTO
         final ReportDTO reportDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getReportDTOByReportHeaderId( headerId );

         // 存在ReportDTO
         if ( reportDTO != null && reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
         {
            for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( reportDetailVO.getReportDetailId() );

               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( reportDetailVO.getNameZH() );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingValue( reportDetailVO.getNameEN() );
               }

               columns.add( mappingVO );
            }
         }
      }

      // 写入作用域
      request.setAttribute( "columns", columns );
   }

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
         final MappingDetailVO mappingDetailVO = ( MappingDetailVO ) form;

         // 处理SubAction
         dealSubAction( mappingDetailVO, mapping, form, request, response );

         // 初始化MappingDetailService接口
         final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

         // 获得主表主键
         String mappingHeaderId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( mappingHeaderId ) == null )
         {
            mappingHeaderId = mappingDetailVO.getMappingHeaderId();
         }
         else
         {
            mappingHeaderId = KANUtil.decodeStringFromAjax( mappingHeaderId );
         }

         // 获取MappingHeaderVO
         final MappingHeaderVO mappingHeaderVO = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( mappingHeaderId );

         // 刷新国际化
         mappingHeaderVO.reset( null, request );
         // 设置SubAction
         mappingHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "mappingHeaderForm", mappingHeaderVO );
         // 根据MappingHeaderId查找并得到MappingDetailVO集合
         mappingDetailVO.setMappingHeaderId( mappingHeaderId );

         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( mappingDetailVO.getSortColumn() == null || mappingDetailVO.getSortColumn().isEmpty() )
         {
            mappingDetailVO.setSortColumn( "columnIndex" );
         }

         // 加载导出、导出、列表字段
         if ( mappingHeaderVO != null )
         {
            // 使用列表
            if ( KANUtil.filterEmpty( mappingHeaderVO.getListId(), "0" ) != null )
            {
               // 初始化ListDTO
               final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( mappingHeaderVO.getListId() );

               // 列表为JAVA对象
               if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getUseJavaObject() != null )
               {
                  request.setAttribute( "useJavaObject", "true" );
               }
            }
            // 使用导入和导出
            else
            {
               String headerId = KANUtil.filterEmpty( mappingHeaderVO.getImportId() ) != null ? mappingHeaderVO.getImportId() : mappingHeaderVO.getReportId();
               setColumns( request, request.getParameter( "flag" ), headerId );
            }
         }

         // 此处分页代码
         final PagedListHolder mappingDetailHolder = new PagedListHolder();
         // 传入当前页
         mappingDetailHolder.setPage( page );
         // 传入当前值对象
         mappingDetailHolder.setObject( mappingDetailVO );
         // 设置页面记录条数
         mappingDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         mappingDetailService.getMappingDetailVOsByCondition( mappingDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( mappingDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "mappingDetailHolder", mappingDetailHolder );

         // 设置默认值
         mappingDetailVO.setColumnIndex( "0" );
         mappingDetailVO.setFontSize( "13" );
         mappingDetailVO.setAlign( "1" );
         mappingDetailVO.setStatus( BaseVO.TRUE );

         mappingDetailVO.reset( null, request );

         request.setAttribute( "flag", request.getParameter( "flag" ) );
         request.setAttribute("authAccessAction", request.getParameter( "flag" ).equals("import")?"HRO_CLIENT_IMPORT":"HRO_CLIENT_EXPORT");

         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listMappingDetailTable" );
         }

         ( ( MappingDetailVO ) form ).setSubAction( "" );
         ( ( MappingDetailVO ) form ).setStatus( MappingDetailVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listMappingDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No use
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
            final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );

            // 获得mappingHeaderId
            final String mappingHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得ActionForm
            final MappingDetailVO mappingDetailVO = ( MappingDetailVO ) form;
            // 获取登录用户
            mappingDetailVO.setMappingHeaderId( mappingHeaderId );
            mappingDetailVO.setAccountId( getAccountId( request, response ) );
            mappingDetailVO.setCreateBy( getUserId( request, response ) );
            mappingDetailVO.setModifyBy( getUserId( request, response ) );

            mappingDetailService.insertMappingDetail( mappingDetailVO );

            // 重新加载常量中的Table和MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            request.setAttribute( "flag", request.getParameter( "flag" ) );
         }

         // 清空Form
         ( ( MappingDetailVO ) form ).reset();
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
      // No use
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
         final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

         // 主键获取需解码
         final String mappingDetailId = KANUtil.decodeString( request.getParameter( "mappingDetailId" ) );

         // 获得MappingDetailVO对象
         final MappingDetailVO mappingDetailVO = mappingDetailService.getMappingDetailVOByMappingDetailId( mappingDetailId );

         // 获得MappingHeaderVO对象
         final MappingHeaderVO mappingHeaderVO = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( mappingDetailVO.getMappingHeaderId() );

         // 使用列表导出
         if ( mappingHeaderVO != null && KANUtil.filterEmpty( mappingHeaderVO.getListId(), "0" ) != null )
         {
            // 初始化ListDTO
            final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( mappingHeaderVO.getListId() );

            // 列表为JAVA对象
            if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getUseJavaObject() != null )
            {
               request.setAttribute( "useJavaObject", "true" );
            }
         }

         // 加载导出、导出、列表字段
         if ( mappingHeaderVO != null )
         {
            // 使用列表
            if ( KANUtil.filterEmpty( mappingHeaderVO.getListId(), "0" ) != null )
            {
               // 初始化ListDTO
               final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( mappingHeaderVO.getListId() );

               // 列表为JAVA对象
               if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getUseJavaObject() != null )
               {
                  request.setAttribute( "useJavaObject", "true" );
               }
            }
            // 使用导入和导出
            else
            {
               String headerId = KANUtil.filterEmpty( mappingHeaderVO.getImportId() ) != null ? mappingHeaderVO.getImportId() : mappingHeaderVO.getReportId();
               setColumns( request, request.getParameter( "flag" ), headerId );
            }
         }

         // 刷新国际化
         mappingDetailVO.reset( null, request );
         mappingDetailVO.setSubAction( VIEW_OBJECT );
         // 传入request对象
         request.setAttribute( "mappingHeaderForm", mappingHeaderVO );
         request.setAttribute( "mappingDetailForm", mappingDetailVO );
         request.setAttribute( "flag", request.getParameter( "flag" ) );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageMappingDetailForm" );
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
            final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );

            // 主键获取需解码
            final String mappingDetailId = KANUtil.decodeString( request.getParameter( "mappingDetailId" ) );
            // 获取主键对象
            final MappingDetailVO mappingDetailVO = mappingDetailService.getMappingDetailVOByMappingDetailId( mappingDetailId );

            // 装载界面传值
            mappingDetailVO.update( ( MappingDetailVO ) form );
            // 获取登录用户
            mappingDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            mappingDetailService.updateMappingDetail( mappingDetailVO );

            // 重新加载常量中的Table和MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // 清空Form
         ( ( MappingDetailVO ) form ).reset();
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
         final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );

         // 获得当前form
         MappingDetailVO mappingDetailVO = ( MappingDetailVO ) form;
         // 存在选中的ID
         if ( mappingDetailVO.getSelectedIds() != null && !mappingDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : mappingDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               mappingDetailVO = mappingDetailService.getMappingDetailVOByMappingDetailId( selectedId );
               mappingDetailVO.setModifyBy( getUserId( request, response ) );
               mappingDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               mappingDetailService.deleteMappingDetail( mappingDetailVO );
            }

            // 重新加载常量中的Table和MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         mappingDetailVO.setSelectedIds( "" );
         mappingDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 根据columnID 获取nameZH nameEN
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final String flag = request.getParameter( "flag" );
         final String columnId = request.getParameter( "columnId" );
         JSONObject jsonObject = new JSONObject();
         if ( "import".equalsIgnoreCase( flag ) )
         {
            ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
            final ImportDetailVO importDetailVO = importDetailService.getImportDetailVOByImportDetailId( columnId );
            jsonObject.put( "nameZH", importDetailVO.getNameZH() );
            jsonObject.put( "nameEN", importDetailVO.getNameEN() );
         }
         else
         {
            ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );
            final ReportDetailVO reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( columnId );
            jsonObject.put( "nameZH", reportDetailVO.getNameZH() );
            jsonObject.put( "nameEN", reportDetailVO.getNameEN() );
         }
         // Send to front
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return null;
   }

}
