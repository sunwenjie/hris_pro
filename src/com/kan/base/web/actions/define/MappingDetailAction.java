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
      // ��ʼ��columns�б�
      final List< MappingVO > columns = new ArrayList< MappingVO >();
      columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

      // �����ֶ�
      if ( KANUtil.filterEmpty( flag ) != null && flag.trim().equals( "import" ) )
      {
         // ��ȡImportDTO
         final ImportDTO importDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getImportDTOByImportHeadId( headerId );

         // ����ImportDTO
         if ( importDTO != null && importDTO.getImportDetailVOs() != null && importDTO.getImportDetailVOs().size() > 0 )
         {
            for ( ImportDetailVO importDetailVO : importDTO.getImportDetailVOs() )
            {
               final MappingVO mappingVO = new MappingVO();

               mappingVO.setMappingId( importDetailVO.getImportDetailId() );

               // ��������Ļ���
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
         // ��ȡReportDTO
         final ReportDTO reportDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getReportDTOByReportHeaderId( headerId );

         // ����ReportDTO
         if ( reportDTO != null && reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
         {
            for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( reportDetailVO.getReportDetailId() );

               // ��������Ļ���
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

      // д��������
      request.setAttribute( "columns", columns );
   }

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final MappingDetailVO mappingDetailVO = ( MappingDetailVO ) form;

         // ����SubAction
         dealSubAction( mappingDetailVO, mapping, form, request, response );

         // ��ʼ��MappingDetailService�ӿ�
         final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

         // �����������
         String mappingHeaderId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( mappingHeaderId ) == null )
         {
            mappingHeaderId = mappingDetailVO.getMappingHeaderId();
         }
         else
         {
            mappingHeaderId = KANUtil.decodeStringFromAjax( mappingHeaderId );
         }

         // ��ȡMappingHeaderVO
         final MappingHeaderVO mappingHeaderVO = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( mappingHeaderId );

         // ˢ�¹��ʻ�
         mappingHeaderVO.reset( null, request );
         // ����SubAction
         mappingHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "mappingHeaderForm", mappingHeaderVO );
         // ����MappingHeaderId���Ҳ��õ�MappingDetailVO����
         mappingDetailVO.setMappingHeaderId( mappingHeaderId );

         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( mappingDetailVO.getSortColumn() == null || mappingDetailVO.getSortColumn().isEmpty() )
         {
            mappingDetailVO.setSortColumn( "columnIndex" );
         }

         // ���ص������������б��ֶ�
         if ( mappingHeaderVO != null )
         {
            // ʹ���б�
            if ( KANUtil.filterEmpty( mappingHeaderVO.getListId(), "0" ) != null )
            {
               // ��ʼ��ListDTO
               final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( mappingHeaderVO.getListId() );

               // �б�ΪJAVA����
               if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getUseJavaObject() != null )
               {
                  request.setAttribute( "useJavaObject", "true" );
               }
            }
            // ʹ�õ���͵���
            else
            {
               String headerId = KANUtil.filterEmpty( mappingHeaderVO.getImportId() ) != null ? mappingHeaderVO.getImportId() : mappingHeaderVO.getReportId();
               setColumns( request, request.getParameter( "flag" ), headerId );
            }
         }

         // �˴���ҳ����
         final PagedListHolder mappingDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         mappingDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         mappingDetailHolder.setObject( mappingDetailVO );
         // ����ҳ���¼����
         mappingDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         mappingDetailService.getMappingDetailVOsByCondition( mappingDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( mappingDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "mappingDetailHolder", mappingDetailHolder );

         // ����Ĭ��ֵ
         mappingDetailVO.setColumnIndex( "0" );
         mappingDetailVO.setFontSize( "13" );
         mappingDetailVO.setAlign( "1" );
         mappingDetailVO.setStatus( BaseVO.TRUE );

         mappingDetailVO.reset( null, request );

         request.setAttribute( "flag", request.getParameter( "flag" ) );
         request.setAttribute("authAccessAction", request.getParameter( "flag" ).equals("import")?"HRO_CLIENT_IMPORT":"HRO_CLIENT_EXPORT");

         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listMappingDetailTable" );
         }

         ( ( MappingDetailVO ) form ).setSubAction( "" );
         ( ( MappingDetailVO ) form ).setStatus( MappingDetailVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );

            // ���mappingHeaderId
            final String mappingHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���ActionForm
            final MappingDetailVO mappingDetailVO = ( MappingDetailVO ) form;
            // ��ȡ��¼�û�
            mappingDetailVO.setMappingHeaderId( mappingHeaderId );
            mappingDetailVO.setAccountId( getAccountId( request, response ) );
            mappingDetailVO.setCreateBy( getUserId( request, response ) );
            mappingDetailVO.setModifyBy( getUserId( request, response ) );

            mappingDetailService.insertMappingDetail( mappingDetailVO );

            // ���¼��س����е�Table��MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            request.setAttribute( "flag", request.getParameter( "flag" ) );
         }

         // ���Form
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

         // ������ȡ�����
         final String mappingDetailId = KANUtil.decodeString( request.getParameter( "mappingDetailId" ) );

         // ���MappingDetailVO����
         final MappingDetailVO mappingDetailVO = mappingDetailService.getMappingDetailVOByMappingDetailId( mappingDetailId );

         // ���MappingHeaderVO����
         final MappingHeaderVO mappingHeaderVO = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( mappingDetailVO.getMappingHeaderId() );

         // ʹ���б���
         if ( mappingHeaderVO != null && KANUtil.filterEmpty( mappingHeaderVO.getListId(), "0" ) != null )
         {
            // ��ʼ��ListDTO
            final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( mappingHeaderVO.getListId() );

            // �б�ΪJAVA����
            if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getUseJavaObject() != null )
            {
               request.setAttribute( "useJavaObject", "true" );
            }
         }

         // ���ص������������б��ֶ�
         if ( mappingHeaderVO != null )
         {
            // ʹ���б�
            if ( KANUtil.filterEmpty( mappingHeaderVO.getListId(), "0" ) != null )
            {
               // ��ʼ��ListDTO
               final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( mappingHeaderVO.getListId() );

               // �б�ΪJAVA����
               if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getUseJavaObject() != null )
               {
                  request.setAttribute( "useJavaObject", "true" );
               }
            }
            // ʹ�õ���͵���
            else
            {
               String headerId = KANUtil.filterEmpty( mappingHeaderVO.getImportId() ) != null ? mappingHeaderVO.getImportId() : mappingHeaderVO.getReportId();
               setColumns( request, request.getParameter( "flag" ), headerId );
            }
         }

         // ˢ�¹��ʻ�
         mappingDetailVO.reset( null, request );
         mappingDetailVO.setSubAction( VIEW_OBJECT );
         // ����request����
         request.setAttribute( "mappingHeaderForm", mappingHeaderVO );
         request.setAttribute( "mappingDetailForm", mappingDetailVO );
         request.setAttribute( "flag", request.getParameter( "flag" ) );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );

            // ������ȡ�����
            final String mappingDetailId = KANUtil.decodeString( request.getParameter( "mappingDetailId" ) );
            // ��ȡ��������
            final MappingDetailVO mappingDetailVO = mappingDetailService.getMappingDetailVOByMappingDetailId( mappingDetailId );

            // װ�ؽ��洫ֵ
            mappingDetailVO.update( ( MappingDetailVO ) form );
            // ��ȡ��¼�û�
            mappingDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            mappingDetailService.updateMappingDetail( mappingDetailVO );

            // ���¼��س����е�Table��MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // ���Form
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
         // ��ʼ��Service�ӿ�
         final MappingDetailService mappingDetailService = ( MappingDetailService ) getService( "mappingDetailService" );

         // ��õ�ǰform
         MappingDetailVO mappingDetailVO = ( MappingDetailVO ) form;
         // ����ѡ�е�ID
         if ( mappingDetailVO.getSelectedIds() != null && !mappingDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : mappingDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               mappingDetailVO = mappingDetailService.getMappingDetailVOByMappingDetailId( selectedId );
               mappingDetailVO.setModifyBy( getUserId( request, response ) );
               mappingDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               mappingDetailService.deleteMappingDetail( mappingDetailVO );
            }

            // ���¼��س����е�Table��MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         mappingDetailVO.setSelectedIds( "" );
         mappingDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ����columnID ��ȡnameZH nameEN
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
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

      // ��ת���б����
      return null;
   }

}
