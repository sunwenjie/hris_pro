package com.kan.hro.web.actions.biz.cb;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.service.inf.biz.cb.CBDetailService;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;

public class CBDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );
         final CBDetailService cbDetailService = ( CBDetailService ) getService( "cbDetailService" );
         // 获得主表ID
         final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得CBHeaderVO
         final CBHeaderVO cbHeaderVO = cbHeaderService.getCBHeaderVOByHeaderId( headerId );
         cbHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "cbHeaderForm", cbHeaderVO );
         // 获得Action Form
         final CBDetailVO cbDetailVO = ( CBDetailVO ) form;
         // 需要设置当前用户AccountId和主表ID
         cbDetailVO.setAccountId( getAccountId( request, response ) );
         cbDetailVO.setHeaderId( headerId );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容
         cbHeaderVO.setRemark1( generateDefineListSearches( request, "HRO_CB_DETAIL" ) );
         // 处理SubAction
         dealSubAction( cbHeaderVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, "HRO_CB_DETAIL" ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 传入当前值对象
            pagedListHolder.setObject( cbDetailVO );
            // 设置页面记录条数
            pagedListHolder.setPageSize( getPageSize( request, "HRO_CB_DETAIL" ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            cbDetailService.getCBDetailVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, "HRO_CB_DETAIL" ) );
            // 刷新Holder，国际化传值
            refreshHolder( pagedListHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // 处理Return
         return dealReturn( "HRO_CB_BATCH", "listCBDetail", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
