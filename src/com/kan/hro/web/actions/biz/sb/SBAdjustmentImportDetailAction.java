package com.kan.hro.web.actions.biz.sb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportDetailService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportHeaderService;

public class SBAdjustmentImportDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentImportDetailService sbAdjustmentImportDetailService = ( SBAdjustmentImportDetailService ) getService( "sbAdjustmentImportDetailService" );
         final SBAdjustmentImportHeaderService sbAdjustmentImportHeaderService = ( SBAdjustmentImportHeaderService ) getService( "sbAdjustmentImportHeaderService" );
         // ���Action Form
         final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO = ( SBAdjustmentImportDetailVO ) form;
         sbAdjustmentImportDetailVO.setAdjustmentHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( sbAdjustmentImportDetailVO.getSortColumn() == null || sbAdjustmentImportDetailVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentImportDetailVO.setSortColumn( "a.adjustmentDetailId" );
            sbAdjustmentImportDetailVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentImprotDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentImprotDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentImprotDetailHolder.setObject( sbAdjustmentImportDetailVO );
         // ����ҳ���¼����
         sbAdjustmentImprotDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentImportDetailService.getSBAdjustmentImportDetailVOsByCondition( sbAdjustmentImprotDetailHolder, true );
         refreshHolder( sbAdjustmentImprotDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentImportDetailHolder", sbAdjustmentImprotDetailHolder );
         SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO = sbAdjustmentImportHeaderService.getSBAdjustmentImportHeaderVOsById( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) ,getAccountId( request, response ));
         sbAdjustmentImportHeaderVO.reset( mapping, request );
         request.setAttribute( "sbAdjustmentImportHeaderForm", sbAdjustmentImportHeaderVO );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentImportDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

}
