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
         // ���ҳ��Token
         this.saveToken( request );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );
         final CBDetailService cbDetailService = ( CBDetailService ) getService( "cbDetailService" );
         // �������ID
         final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���CBHeaderVO
         final CBHeaderVO cbHeaderVO = cbHeaderService.getCBHeaderVOByHeaderId( headerId );
         cbHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "cbHeaderForm", cbHeaderVO );
         // ���Action Form
         final CBDetailVO cbDetailVO = ( CBDetailVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId������ID
         cbDetailVO.setAccountId( getAccountId( request, response ) );
         cbDetailVO.setHeaderId( headerId );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ�����������
         cbHeaderVO.setRemark1( generateDefineListSearches( request, "HRO_CB_DETAIL" ) );
         // ����SubAction
         dealSubAction( cbHeaderVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, "HRO_CB_DETAIL" ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( cbDetailVO );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( getPageSize( request, "HRO_CB_DETAIL" ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            cbDetailService.getCBDetailVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, "HRO_CB_DETAIL" ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // ����Return
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
