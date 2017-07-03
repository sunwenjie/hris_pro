package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.service.inf.define.ReportSearchDetailService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ReportSearchDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ���Action Form
         final ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) form;

         // �����Action��ɾ��
         if ( reportSearchDetailVO.getSubAction() != null && reportSearchDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

         // ��������������������������ã���form��ȡ������ajax��������Σ�       
         String headerId = request.getParameter( "reportHeaderId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( ReportSearchDetailVO ) form ).getReportHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ���ReportHeaderVO
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
         reportHeaderVO.reset( null, request );
         reportHeaderVO.setSubAction( VIEW_OBJECT );
         // ���÷�ҳ�ֶε�Checkbox
         reportHeaderVO.setUsePagination( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equals( ReportHeaderVO.TRUE ) ? "on" : "" );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ����������ң��õ�ReportSearchDetailVO����
         reportSearchDetailVO.setReportHeaderId( headerId );
         // �˴���ҳ����
         PagedListHolder reportSearchDetailPagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         reportSearchDetailPagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         reportSearchDetailPagedListHolder.setObject( reportSearchDetailVO );
         // ����ҳ���¼����
         reportSearchDetailPagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         reportSearchDetailService.getReportSearchDetailVOsByCondition( reportSearchDetailPagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( reportSearchDetailPagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "reportSearchDetailPagedListHolder", reportSearchDetailPagedListHolder );

         // Ajax Table����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ��� JSP
            return mapping.findForward( "manageReportDetailSearch" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

            // ��ȡ����
            final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

            // ��ȡ����ID
            final String cityId = request.getParameter( "cityId" );

            // ��õ�ǰform
            final ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) form;
            reportSearchDetailVO.setReportHeaderId( headerId );

            if ( KANUtil.filterEmpty( cityId ) != null )
            {
               reportSearchDetailVO.setContent( cityId );
            }
            // ����SubAction
            dealSubAction( reportSearchDetailVO, mapping, form, request, response );
            reportSearchDetailVO.setCreateBy( getUserId( request, response ) );
            reportSearchDetailVO.setModifyBy( getUserId( request, response ) );
            reportSearchDetailService.insertReportSearchDetail( reportSearchDetailVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_SEARCH" );

            insertlog( request, reportSearchDetailVO, Operate.ADD, reportSearchDetailVO.getReportSearchDetailId(), null );

            // ��ʼ�������־ö���
            //            constantsInit( "initTable", getAccountId( request, response ) );

            this.saveToken( request );
         }
         // ���Form
         ( ( ReportSearchDetailVO ) form ).reset();
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

         // ��������ID
         final String searchDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���ReportSearchDetailVO����
         final ReportSearchDetailVO reportSearchDetailVO = reportSearchDetailService.getReportSearchDetailVOByReportSearchDetailId( searchDetailId );

         // ���ReportHeaderVO����
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( reportSearchDetailVO.getReportHeaderId() );

         // ���ʻ���ֵ
         reportSearchDetailVO.reset( null, request );

         // ��ȡtableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );

         // ����columns
         if ( tableDTO != null )
         {
            // ���ColumnVO MappingVO��ʽ�б�
            final List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               reportSearchDetailVO.getColumns().addAll( columnMappingVOs );
            }
         }

         // �����޸����
         reportSearchDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "reportHeaderForm", reportHeaderVO );
         request.setAttribute( "reportSearchDetailForm", reportSearchDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageReportSearchDetailForm" );
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

            // ������ȡ�����
            final String reportSearchDetailId = Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( request.getParameter( "reportSearchDetailId" ), "UTF-8" ), "UTF-8" ) );
            // ��õ�ǰform
            final ReportSearchDetailVO reportSearchDetailForm = ( ReportSearchDetailVO ) form;
            // ����SubAction
            dealSubAction( reportSearchDetailForm, mapping, form, request, response );

            // ���ReportSearchDetailVO����
            final ReportSearchDetailVO reportSearchDetailVO = reportSearchDetailService.getReportSearchDetailVOByReportSearchDetailId( reportSearchDetailId );
            // װ�ؽ��洫ֵ
            reportSearchDetailVO.update( reportSearchDetailForm );
            // ��ȡ��¼�û�
            reportSearchDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            reportSearchDetailService.updateReportSearchDetail( reportSearchDetailVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_SEARCH" );

            insertlog( request, reportSearchDetailVO, Operate.MODIFY, reportSearchDetailVO.getReportSearchDetailId(), null );

            this.saveToken( request );

            // ��ʼ�������־ö���
            //            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // ���Form
         ( ( ReportSearchDetailVO ) form ).reset();
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
         // ��ʼ��Service�ӿ�
         final ReportSearchDetailService reportSearchDetailService = ( ReportSearchDetailService ) getService( "reportSearchDetailService" );

         // ��õ�ǰform
         ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) form;
         final String selectedIds = reportSearchDetailVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ʵ����VendorContactVO��ɾ��
                  final ReportSearchDetailVO tempReportSearchDetailVO = reportSearchDetailService.getReportSearchDetailVOByReportSearchDetailId( selectedId );
                  // ����ɾ���ӿ�
                  tempReportSearchDetailVO.setModifyBy( getUserId( request, response ) );
                  tempReportSearchDetailVO.setModifyDate( new Date() );
                  reportSearchDetailService.deleteReportSearchDetail( tempReportSearchDetailVO );
               }
            }

            insertlog( request, reportSearchDetailVO, Operate.DELETE, null, reportSearchDetailVO.getSelectedIds() );
            // ��ʼ�������־ö���
            //            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // ����ɾ���ɹ����
         success( request, MESSAGE_TYPE_DELETE, null, "MESSAGE_SEARCH" );

         // ���Selected IDs����Action
         reportSearchDetailVO.setSelectedIds( "" );
         reportSearchDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
