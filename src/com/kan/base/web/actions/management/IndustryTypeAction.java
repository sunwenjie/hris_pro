package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.IndustryTypeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IndustryTypeAction extends BaseAction
{
	public static final String accessAction = "HRO_MGT_INDUSTRY_TYPE";
   /**  
    * List Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
         // 获得Action Form
         final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) form;
         // 需要设置当前用户AccountId
         industryTypeVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( industryTypeVO.getSubAction() != null && industryTypeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( industryTypeVO );
         }

         // 如果没有指定排序则默认按 typeId排序
         if ( industryTypeVO.getSortColumn() == null || industryTypeVO.getSortColumn().isEmpty() )
         {
            industryTypeVO.setSortColumn( "typeId" );
            industryTypeVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder industryTypeHolder = new PagedListHolder();
         // 传入当前页
         industryTypeHolder.setPage( page );
         // 传入当前值对象
         industryTypeHolder.setObject( industryTypeVO );
         // 设置页面记录条数
         industryTypeHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         industryTypeService.getIndustryTypeVOsByCondition( industryTypeHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( industryTypeHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "industryTypeHolder", industryTypeHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listIndustryTypeTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listIndustryType" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( IndustryTypeVO ) form ).setStatus( IndustryTypeVO.TRUE );
      ( ( IndustryTypeVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageIndustryType" );
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
            final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );

            // 获得当前FORM
            final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) form;
            industryTypeVO.setCreateBy( getUserId( request, response ) );
            industryTypeVO.setModifyBy( getUserId( request, response ) );
            industryTypeVO.setAccountId( getAccountId( request, response ) );
            industryTypeService.insertIndustryType( industryTypeVO );

            // 初始化常量
            constantsInit( "initIndustryType", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form
         ( ( IndustryTypeVO ) form ).reset();
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
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
         // 主键获取需解码
         final String industryTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
         // 获得IndustryTypeVO对象                                                                                          
         final IndustryTypeVO industryTypeVO = industryTypeService.getIndustryTypeVOByIndustryTypeId( industryTypeId );
         // 区分Add和Update
         industryTypeVO.setSubAction( VIEW_OBJECT );
         industryTypeVO.reset( null, request );
         // 将IndustryTypeVO传入request对象
         request.setAttribute( "industryTypeForm", industryTypeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageIndustryType" );
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
            final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
            // 主键获取需解码
            final String industryTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
            // 获取IndustryTypeVO对象
            final IndustryTypeVO industryTypeVO = industryTypeService.getIndustryTypeVOByIndustryTypeId( industryTypeId );
            // 装载界面传值
            industryTypeVO.update( ( IndustryTypeVO ) form );
            // 获取登录用户
            industryTypeVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            industryTypeService.updateIndustryType( industryTypeVO );

            // 初始化常量
            constantsInit( "initIndustryType", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form
         ( ( IndustryTypeVO ) form ).reset();
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
         final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
         // 获得Action Form
         final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) form;
         // 存在选中的ID
         if ( industryTypeVO.getSelectedIds() != null && !industryTypeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : industryTypeVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               industryTypeVO.setTypeId( selectedId );
               industryTypeVO.setAccountId( getAccountId( request, response ) );
               industryTypeVO.setModifyBy( getUserId( request, response ) );
               industryTypeService.deleteIndustryType( industryTypeVO );
            }
         }

         // 初始化常量
         constantsInit( "initIndustryType", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         industryTypeVO.setSelectedIds( "" );
         industryTypeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Options Ajax(显示工业类型下拉框)  
    * @param   name  
    * @param  @return    设定文件  
    * @return String    DOM对象  
    * @Exception 异常对象  
    * @since  CodingExample　Ver(编码范例查看) 1.1  
    */
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
         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // 获取typeId
         final String typeId = request.getParameter( "typeId" );

         // 如果typeId不为空
         if ( typeId != null && !typeId.equals( "" ) )
         {
            // 初始化Service接口
            final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
            // 实例化industryTypeVO用于查询
            IndustryTypeVO industryTypeVO = new IndustryTypeVO();
            industryTypeVO.setAccountId( getAccountId( request, response ) );
            final List< IndustryTypeVO > industryTypeVOs = industryTypeService.getIndustryTypeVOsByIndustryTypeVO( industryTypeVO );
            // 如果联系人存在则遍历并添加到下拉选项中
            if ( industryTypeVOs != null && industryTypeVOs.size() > 0 )
            {
               for ( IndustryTypeVO industryTypeVOTemp : industryTypeVOs )
               {
                  // 如果是中文环境
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( industryTypeVOTemp.getTypeId() );
                     mappingVO.setMappingValue( industryTypeVOTemp.getNameZH() );
                     mappingVOs.add( mappingVO );
                  }
                  // 如果是中文环境
                  else
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( industryTypeVOTemp.getTypeId() );
                     mappingVO.setMappingValue( industryTypeVOTemp.getNameEN() );
                     mappingVOs.add( mappingVO );
                  }
               }
            }
         }
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "typeId", typeId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "" );
   }

}
