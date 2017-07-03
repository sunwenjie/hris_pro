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
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ImportHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ImportHeaderAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_IMPORT";

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
         final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;
         // 需要设置当前用户AccountId
         importHeaderVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除
         if ( importHeaderVO.getSubAction() != null && importHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( importHeaderVO );
         }

         // 初始化Service接口
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder importHeaderPagedListHolder = new PagedListHolder();
         // 传入当前页
         importHeaderPagedListHolder.setPage( page );
         // 传入当前值对象
         importHeaderPagedListHolder.setObject( importHeaderVO );
         // 设置页面记录条数
         importHeaderPagedListHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         importHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         importHeaderService.getImportHeaderVOsByCondition( importHeaderPagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( importHeaderPagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "importHeaderPagedListHolder", importHeaderPagedListHolder );
         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listImportHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;
      // 设置初始化字段
      importHeaderVO.setStatus( ImportHeaderVO.TRUE );
      importHeaderVO.setSubAction( CREATE_OBJECT );
      List< MappingVO > tables = importHeaderVO.getTables();

      List< ImportHeaderVO > existImportHeaderVOs = null;

      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         existImportHeaderVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getImportHeaderVOs( getLocale( request ).getLanguage(), getCorpId( request, response ) );
      }
      else
      {
         existImportHeaderVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getImportHeaderVOs( getLocale( request ).getLanguage() );
      }

      List< MappingVO > existTables = new ArrayList< MappingVO >();
      // 添加重复的
      if ( existImportHeaderVOs != null && existImportHeaderVOs.size() > 0 )
      {
         for ( ImportHeaderVO tempImportHeaderVO : existImportHeaderVOs )
         {
            for ( int i = 0; i < tables.size(); i++ )
            {
               if ( tables.get( i ).getMappingId().equals( tempImportHeaderVO.getTableId() ) )
                  existTables.add( tables.get( i ) );
            }
         }
      }
      // 删除重复的
      tables.removeAll( existTables );

      importHeaderVO.setTables( tables );

      // 跳转到新建界面
      return mapping.findForward( "manageImportHeader" );
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
            final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

            // 获得当前FORM
            final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;

            // 获取登录用户及账户
            importHeaderVO.setCreateBy( getUserId( request, response ) );
            importHeaderVO.setModifyBy( getUserId( request, response ) );
            importHeaderVO.setAccountId( getAccountId( request, response ) );
            importHeaderService.insertImportHeader( importHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            // 返回保存成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, importHeaderVO, Operate.ADD, importHeaderVO.getImportHeaderId(), null );

            return to_objectModify( mapping, importHeaderVO, request, response );
         }

         // 清空Form
         ( ( ImportHeaderVO ) form ).reset();
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

         // 获得是否AJAX调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 主键获取，从request，form中
         String importHeaderId = request.getParameter( "importHeaderId" );
         if ( KANUtil.filterEmpty( importHeaderId ) != null )
         {
            importHeaderId = Cryptogram.decodeString( URLDecoder.decode( importHeaderId, "UTF-8" ) );
         }
         else
         {
            importHeaderId = ( ( ImportHeaderVO ) form ).getImportHeaderId();
         }

         // 需要得到一个ImportHeader，所以需要初始化ImportHeaderService接口
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );
         // 根据主键获得 ImportHeaderVO对象
         final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( importHeaderId );
         importHeaderVO.setSubAction( VIEW_OBJECT );
         importHeaderVO.reset( null, request );

         ImportDetailVO importDetailVO = new ImportDetailVO();
         importDetailVO.setImportHeaderId( importHeaderId );

         new ImportDetailAction().list_object( mapping, importDetailVO, request, response );

         // 传入request对象
         request.setAttribute( "importHeaderForm", importHeaderVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageImportHeader" );
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
            final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

            // 获得主键
            final String importHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "importHeaderId" ), "UTF-8" ) );
            // 获得ImportHeaderVO对象
            final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( importHeaderId );

            // 装载界面传值
            importHeaderVO.update( ( ImportHeaderVO ) form );

            // 获取登录用户
            importHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            importHeaderService.updateImportHeader( importHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, importHeaderVO, Operate.MODIFY, importHeaderVO.getImportHeaderId(), null );

            return to_objectModify( mapping, importHeaderVO, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
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
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         // 获得主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );

         // 获得主键对象
         final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( headerId );

         // 获得当前form
         final ImportHeaderVO importHeaderForm = ( ImportHeaderVO ) form;

         importHeaderVO.setPositionIds( KANUtil.toJasonArray( importHeaderForm.getPositionIdArray() ) );
         importHeaderVO.setPositionGradeIds( KANUtil.toJasonArray( importHeaderForm.getPositionGradeIdArray() ) );
         importHeaderVO.setPositionGroupIds( KANUtil.toJasonArray( importHeaderForm.getGroupIdArray() ) );

         // 设置为发布状态
         importHeaderVO.setStatus( "2" );
         // 获取登录用户
         importHeaderVO.setModifyBy( getUserId( request, response ) );
         // 调用修改方法
         importHeaderService.updateImportHeader( importHeaderVO );

         // 初始化常量持久对象
         constantsInit( "initImportHeader", getAccountId( request, response ) );

         importHeaderVO.reset( null, request );
         // 修改后对象写入request作用域
         request.setAttribute( "importHeaderForm", importHeaderVO );

         //返回添加成功标记
         success( request, MESSAGE_TYPE_UPDATE, "发布成功！", "MESSAGE_HEADER_PUBLISH" );

         insertlog( request, importHeaderVO, Operate.MODIFY, importHeaderVO.getImportHeaderId(), "modify_object_publish" );

         // 清空Form
         ( ( ImportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用返回JSP页面
      return mapping.findForward( "manageImportConfirmPublish" );
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
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         // 获得Action Form
         ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;

         // 存在选中的ID
         if ( importHeaderVO.getSelectedIds() != null && !importHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, importHeaderVO, Operate.DELETE, null, importHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : importHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( selectedId );
               // 调用删除接口
               importHeaderVO.setModifyBy( getUserId( request, response ) );
               importHeaderVO.setModifyDate( new Date() );
               // 调用删除接口
               importHeaderService.deleteImportHeader( importHeaderVO );
            }
         }

         // 初始化常量持久对象
         constantsInit( "initImportHeader", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         importHeaderVO.setSelectedIds( "" );
         importHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();

         final ImportHeaderVO importHeaderVO = new ImportHeaderVO();

         importHeaderVO.setAccountId( getAccountId( request, response ) );

         pagedListHolder.setObject( importHeaderVO );

         importHeaderService.getImportHeaderVOsByCondition( pagedListHolder, false );

         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         MappingVO mappingVO = null;

         ImportHeaderVO tempImportHeaderVO = null;
         if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               mappingVO = new MappingVO();
               tempImportHeaderVO = ( ImportHeaderVO ) object;
               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingId( tempImportHeaderVO.getImportHeaderId() );
                  mappingVO.setMappingValue( tempImportHeaderVO.getNameZH() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingId( tempImportHeaderVO.getImportHeaderId() );
                  mappingVO.setMappingValue( tempImportHeaderVO.getNameEN() );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         final String importId = request.getParameter( "importId" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "importId", KANUtil.filterEmpty( importId ) == null ? "0" : importId ) );
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

}
