package com.kan.base.web.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class LogAction extends BaseAction
{

   public final static String accessAction = "HRO_SYS_LOG";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 初始化Service接口
         final LogService logService = ( LogService ) getService( "logService" );
         // 获得Action Form
         final LogVO logVO = ( LogVO ) form;
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 排序
         if ( KANUtil.filterEmpty( logVO.getSortColumn() ) == null )
         {
            logVO.setSortColumn( "operateTime" );
            logVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder logHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            logHolder.setPage( page );
            // 传入当前值对象
            logHolder.setObject( logVO );
            // 设置页面记录条数
            logHolder.setPageSize( listPageSize );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            logService.getLogVOsByCondition( logHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
            // 刷新Holder，国际化传值
            refreshHolder( logHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "logHolder", logHolder );
         if ( new Boolean( getAjax( request ) ) )
         {
            return mapping.findForward( "listLogTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listLog" );
   }

   public ActionForward formatJson( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final String id = request.getParameter( "id" );
      LogService logService = ( LogService ) getService( "logService" );
      final LogVO logvo = logService.getLogVOById( id );
      // 获取上一次的log
      final LogVO perLogvo = logService.getPreLog( logvo );
      String content = "";
      String preContent = "";
      int count = 1;
      try
      {
         if ( logvo != null )
         {
            content = URLEncoder.encode( URLEncoder.encode( logvo.getContent(), "UTF-8" ), "UTF-8" );
         }
         if ( perLogvo != null )
         {
            preContent = URLEncoder.encode( URLEncoder.encode( perLogvo.getContent(), "UTF-8" ), "UTF-8" );
            count = 2;
         }
      }
      catch ( UnsupportedEncodingException e )
      {
         e.printStackTrace();
      }
      request.setAttribute( "content", content );
      request.setAttribute( "preContent", preContent );
      request.setAttribute( "count", count );
      return mapping.findForward( "formatJson" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}