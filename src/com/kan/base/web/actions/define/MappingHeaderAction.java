package com.kan.base.web.actions.define;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.MappingHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class MappingHeaderAction extends BaseAction
{
	public static final String accessAction = "HRO_CLIENT_IMPORT";
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
         final MappingHeaderVO mappingHeaderVO = ( MappingHeaderVO ) form;
         // 判断是来自import还是export
         final String flag = request.getParameter( "flag" );
         if ( "import".equalsIgnoreCase( flag ) )
         {
            mappingHeaderVO.setImportId( "0" );
         }
         else
         {
            mappingHeaderVO.setReportId( "0" );
         }
         // 需要设置当前用户AccountId
         mappingHeaderVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( mappingHeaderVO.getSubAction() != null && mappingHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( mappingHeaderVO );
         }

         // 初始化Service接口
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder mappingHeaderPagedListHolder = new PagedListHolder();
         // 传入当前页
         mappingHeaderPagedListHolder.setPage( page );
         // 传入当前值对象
         mappingHeaderPagedListHolder.setObject( mappingHeaderVO );
         // 设置页面记录条数
         mappingHeaderPagedListHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         mappingHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         mappingHeaderService.getMappingHeaderVOsByCondition( mappingHeaderPagedListHolder, true );
         refreshHolder( mappingHeaderPagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "mappingHeaderPagedListHolder", mappingHeaderPagedListHolder );
         request.setAttribute( "flag", flag );
         request.setAttribute("authAccessAction", flag.equals("import")?"HRO_CLIENT_IMPORT":"HRO_CLIENT_EXPORT");
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listMappingHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listMappingHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( MappingHeaderVO ) form ).setStatus( MappingHeaderVO.TRUE );
      ( ( MappingHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      request.setAttribute( "flag", request.getParameter( "flag" ) );

      // 跳转到新建界面
      return mapping.findForward( "manageMappingHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化MappingDetailVO
         final MappingDetailVO mappingDetailVO = new MappingDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

            // 获得当前FORM
            final MappingHeaderVO mappingHeaderVO = ( MappingHeaderVO ) form;
            mappingHeaderVO.setCreateBy( getUserId( request, response ) );
            mappingHeaderVO.setModifyBy( getUserId( request, response ) );
            mappingHeaderVO.setAccountId( getAccountId( request, response ) );

            // 添加MappingHeaderVO
            mappingHeaderService.insertMappingHeader( mappingHeaderVO );
            mappingDetailVO.setMappingHeaderId( mappingHeaderVO.getMappingHeaderId() );

            // 重新加载常量中的Table和MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
         }
         else
         {
            // 清空form
            ( ( MappingHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // 跳转List MappingDetail界面
         return new MappingDetailAction().list_object( mapping, mappingDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
            final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

            // 获取主键 - 需解码
            final String mappingHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获得MappingHeaderVO对象
            final MappingHeaderVO mappingHeaderVO = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( mappingHeaderId );
            // 装载界面传值
            mappingHeaderVO.update( ( MappingHeaderVO ) form );
            // 获取登录用户
            mappingHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            mappingHeaderService.updateMappingHeader( mappingHeaderVO );

            // 重新加载常量中的Table和MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         }

         // 清空Action Form
         ( ( MappingHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new MappingDetailAction().list_object( mapping, new MappingDetailVO(), request, response );
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
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

         // 获得Action Form
         final MappingHeaderVO mappingHeaderVO = ( MappingHeaderVO ) form;

         // 存在选中的ID
         if ( mappingHeaderVO.getSelectedIds() != null && !mappingHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : mappingHeaderVO.getSelectedIds().split( "," ) )
            {
               // 根据ID获取对应的mappingHeaderVO
               final MappingHeaderVO mappingHeaderVOForDel = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( selectedId );
               mappingHeaderVOForDel.setModifyBy( getUserId( request, response ) );
               mappingHeaderVOForDel.setModifyDate( new Date() );
               mappingHeaderService.deleteMappingHeader( mappingHeaderVOForDel );
            }
         }

         // 重新加载常量中的Table和MappingHeader
         constantsInit( "initTable", getAccountId( request, response ) );
         constantsInit( "initMappingHeader", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         mappingHeaderVO.setSelectedIds( "" );
         mappingHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
