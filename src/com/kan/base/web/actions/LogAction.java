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
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final LogService logService = ( LogService ) getService( "logService" );
         // ���Action Form
         final LogVO logVO = ( LogVO ) form;
         // ���SubAction
         final String subAction = getSubAction( form );
         // ����
         if ( KANUtil.filterEmpty( logVO.getSortColumn() ) == null )
         {
            logVO.setSortColumn( "operateTime" );
            logVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder logHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            logHolder.setPage( page );
            // ���뵱ǰֵ����
            logHolder.setObject( logVO );
            // ����ҳ���¼����
            logHolder.setPageSize( listPageSize );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            logService.getLogVOsByCondition( logHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( logHolder, request );
         }

         // Holder��д��Request����
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
      // ��ȡ��һ�ε�log
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