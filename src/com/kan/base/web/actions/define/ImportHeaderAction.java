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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         importHeaderVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ��
         if ( importHeaderVO.getSubAction() != null && importHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( importHeaderVO );
         }

         // ��ʼ��Service�ӿ�
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder importHeaderPagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         importHeaderPagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         importHeaderPagedListHolder.setObject( importHeaderVO );
         // ����ҳ���¼����
         importHeaderPagedListHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         importHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         importHeaderService.getImportHeaderVOsByCondition( importHeaderPagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( importHeaderPagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "importHeaderPagedListHolder", importHeaderPagedListHolder );
         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listImportHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;
      // ���ó�ʼ���ֶ�
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
      // ����ظ���
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
      // ɾ���ظ���
      tables.removeAll( existTables );

      importHeaderVO.setTables( tables );

      // ��ת���½�����
      return mapping.findForward( "manageImportHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

            // ��õ�ǰFORM
            final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;

            // ��ȡ��¼�û����˻�
            importHeaderVO.setCreateBy( getUserId( request, response ) );
            importHeaderVO.setModifyBy( getUserId( request, response ) );
            importHeaderVO.setAccountId( getAccountId( request, response ) );
            importHeaderService.insertImportHeader( importHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            // ���ر���ɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, importHeaderVO, Operate.ADD, importHeaderVO.getImportHeaderId(), null );

            return to_objectModify( mapping, importHeaderVO, request, response );
         }

         // ���Form
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

         // ����Ƿ�AJAX����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ������ȡ����request��form��
         String importHeaderId = request.getParameter( "importHeaderId" );
         if ( KANUtil.filterEmpty( importHeaderId ) != null )
         {
            importHeaderId = Cryptogram.decodeString( URLDecoder.decode( importHeaderId, "UTF-8" ) );
         }
         else
         {
            importHeaderId = ( ( ImportHeaderVO ) form ).getImportHeaderId();
         }

         // ��Ҫ�õ�һ��ImportHeader��������Ҫ��ʼ��ImportHeaderService�ӿ�
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );
         // ����������� ImportHeaderVO����
         final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( importHeaderId );
         importHeaderVO.setSubAction( VIEW_OBJECT );
         importHeaderVO.reset( null, request );

         ImportDetailVO importDetailVO = new ImportDetailVO();
         importDetailVO.setImportHeaderId( importHeaderId );

         new ImportDetailAction().list_object( mapping, importDetailVO, request, response );

         // ����request����
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

            // �������
            final String importHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "importHeaderId" ), "UTF-8" ) );
            // ���ImportHeaderVO����
            final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( importHeaderId );

            // װ�ؽ��洫ֵ
            importHeaderVO.update( ( ImportHeaderVO ) form );

            // ��ȡ��¼�û�
            importHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            importHeaderService.updateImportHeader( importHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
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
    * ȷ�Ϸ���
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
         // ��ʼ�� Service�ӿ�
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         // �������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );

         // �����������
         final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( headerId );

         // ��õ�ǰform
         final ImportHeaderVO importHeaderForm = ( ImportHeaderVO ) form;

         importHeaderVO.setPositionIds( KANUtil.toJasonArray( importHeaderForm.getPositionIdArray() ) );
         importHeaderVO.setPositionGradeIds( KANUtil.toJasonArray( importHeaderForm.getPositionGradeIdArray() ) );
         importHeaderVO.setPositionGroupIds( KANUtil.toJasonArray( importHeaderForm.getGroupIdArray() ) );

         // ����Ϊ����״̬
         importHeaderVO.setStatus( "2" );
         // ��ȡ��¼�û�
         importHeaderVO.setModifyBy( getUserId( request, response ) );
         // �����޸ķ���
         importHeaderService.updateImportHeader( importHeaderVO );

         // ��ʼ�������־ö���
         constantsInit( "initImportHeader", getAccountId( request, response ) );

         importHeaderVO.reset( null, request );
         // �޸ĺ����д��request������
         request.setAttribute( "importHeaderForm", importHeaderVO );

         //������ӳɹ����
         success( request, MESSAGE_TYPE_UPDATE, "�����ɹ���", "MESSAGE_HEADER_PUBLISH" );

         insertlog( request, importHeaderVO, Operate.MODIFY, importHeaderVO.getImportHeaderId(), "modify_object_publish" );

         // ���Form
         ( ( ImportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���÷���JSPҳ��
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
         // ��ʼ��Service�ӿ�
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         // ���Action Form
         ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) form;

         // ����ѡ�е�ID
         if ( importHeaderVO.getSelectedIds() != null && !importHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, importHeaderVO, Operate.DELETE, null, importHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : importHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( selectedId );
               // ����ɾ���ӿ�
               importHeaderVO.setModifyBy( getUserId( request, response ) );
               importHeaderVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               importHeaderService.deleteImportHeader( importHeaderVO );
            }
         }

         // ��ʼ�������־ö���
         constantsInit( "initImportHeader", getAccountId( request, response ) );

         // ���Selected IDs����Action
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();

         final ImportHeaderVO importHeaderVO = new ImportHeaderVO();

         importHeaderVO.setAccountId( getAccountId( request, response ) );

         pagedListHolder.setObject( importHeaderVO );

         importHeaderService.getImportHeaderVOsByCondition( pagedListHolder, false );

         // ��ʼ������ѡ��
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
               // ��������Ļ���
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

      // ��ת���б����
      return mapping.findForward( "" );
   }

}
