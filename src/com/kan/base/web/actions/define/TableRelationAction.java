package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TableRelationService;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class TableRelationAction extends BaseAction
{

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
         final TableRelationVO tableRelationVO = ( TableRelationVO ) form;

         // ���û��ָ��������Ĭ�ϰ� tableIndex����
         if ( tableRelationVO.getSortColumn() == null || tableRelationVO.getSortColumn().isEmpty() )
         {
            tableRelationVO.setSortColumn( "moduleType,tableIndex" );
            tableRelationVO.setSortOrder( "asc" );
         }

         // ����ɾ������
         if ( tableRelationVO.getSubAction() != null && tableRelationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( tableRelationVO );
         }

         // ��ʼ��Service�ӿ�
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder tablePagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         tablePagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         tablePagedListHolder.setObject( tableRelationVO );
         // ����ҳ���¼����
         tablePagedListHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         tableRelationVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         tableRelationService.getTableRelationVOsByCondition( tablePagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( tablePagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "tablePagedListHolder", tablePagedListHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax ���ã����´���AccountId
            request.setAttribute( "accountId", getAccountId( request, response ) );
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listTableRelationTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listTableRelation" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );
      final TableRelationVO tableRelationVO = ( TableRelationVO ) form;
      tableRelationVO.reset( mapping, request );
      // ����Sub Action
      tableRelationVO.setSubAction( CREATE_OBJECT );
      request.setAttribute( "tableRelationForm", tableRelationVO );

      // ��ʼ��PagedListHolder���������÷�ʽ����Service
      PagedListHolder tablePagedListHolder = new PagedListHolder();
      refreshHolder( tablePagedListHolder, request );

      // Holder��д��Request����
      request.setAttribute( "tablePagedListHolder", tablePagedListHolder );

      final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
      request.setAttribute( "tablelist", tablelist );

      // ��ת���½�����
      return mapping.findForward( "manageTableRelation" );
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
            final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
            // ��õ�ǰFORM
            final TableRelationVO tableRelationVO = ( TableRelationVO ) form;
            String masterTableId = tableRelationVO.getMasterTableId();
            List< Object > tableRelationVOs = tableRelationService.getTableRelationVOsByCondition( tableRelationVO );
            if ( tableRelationVOs == null || tableRelationVOs.size() == 0 )
            {
               tableRelationVO.setCreateBy( getUserId( request, response ) );
               tableRelationVO.setModifyBy( getUserId( request, response ) );
               tableRelationVO.setAccountId( getAccountId( request, response ) );
               tableRelationVO.setDeleted( "1" );
               tableRelationVO.setStatus( "1" );

               // ���TableRelationVO
               tableRelationService.insertTableRelationVO( tableRelationVO );
               final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
               request.setAttribute( "tablelist", tablelist );
               // ������ӳɹ����
               success( request, MESSAGE_TYPE_ADD );
            }
            else
            {
               error( request, MESSAGE_TYPE_ADD, "������ر����ϵ�����ظ���� ��" );
            }

            // ���Action Form
            ( ( TableRelationVO ) form ).reset();
            ( ( TableRelationVO ) form ).setTableRelationId( null );
            ( ( TableRelationVO ) form ).setMasterTableId( masterTableId );
         }

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

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final TableRelationVO tableRelationVO = ( TableRelationVO ) form;

         // ���û��ָ��������Ĭ�ϰ� tableIndex����
         if ( tableRelationVO.getSortColumn() == null || tableRelationVO.getSortColumn().isEmpty() )
         {
            tableRelationVO.setSortColumn( "tableRelationId" );
            tableRelationVO.setSortOrder( "desc" );
         }

         // ����ɾ������
         if ( tableRelationVO.getSubAction() != null && tableRelationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_object( mapping, form, request, response );
         }
         else
         {
            decodedObject( tableRelationVO );
         }

         // ��ʼ��Service�ӿ�
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder tablePagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         tablePagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         tablePagedListHolder.setObject( tableRelationVO );
         // ����ҳ���¼����
         tablePagedListHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         tableRelationVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         tableRelationService.getTableRelationVOsByCondition( tablePagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( tablePagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "tablePagedListHolder", tablePagedListHolder );
         request.setAttribute( "tableRelationForm", tableRelationVO );

         final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );

         request.setAttribute( "tablelist", tablelist );

         ( ( TableRelationVO ) form ).setSubAction( MODIFY_OBJECT );
         // Ajax����
         if ( new Boolean( ajax ) )
         {

            request.setAttribute( "accountId", getAccountId( request, response ) );
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listTableRelationDetailTable" );

         }

         this.saveToken( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "manageTableRelation" );
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
            final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
            // ������ȡ�����
            final String tableRelationId = request.getParameter( "tableRelationId" );
            final TableRelationVO tableRelationForm = ( TableRelationVO ) form;
            String masterTableId = tableRelationForm.getMasterTableId();
            // ��ȡTableRelationVO����
            final TableRelationVO tableRelationVO = tableRelationService.getTableRelationVOsByTableRelationId( tableRelationId );

            if ( tableRelationVO != null && tableRelationVO.getTableRelationId() != null )
            {
               tableRelationVO.update( tableRelationForm );
               tableRelationVO.setStatus( "1" );
               // Checkbox����
               // ��ȡ��¼�û�
               tableRelationVO.setModifyBy( getUserId( request, response ) );
               // �����޸ķ���
               tableRelationService.updateTableRelationVO( tableRelationVO );

               masterTableId = tableRelationVO.getMasterTableId();
               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_UPDATE );

            }
            else
            {

               tableRelationForm.setCreateBy( getUserId( request, response ) );
               tableRelationForm.setModifyBy( getUserId( request, response ) );
               tableRelationForm.setAccountId( getAccountId( request, response ) );
               tableRelationForm.setDeleted( "1" );
               tableRelationForm.setStatus( "1" );

               // ���TableRelationVO
               tableRelationService.insertTableRelationVO( tableRelationForm );
               final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
               request.setAttribute( "tablelist", tablelist );
               // ������ӳɹ����
               success( request, MESSAGE_TYPE_ADD );
            }

            // ���Form
            ( ( TableRelationVO ) form ).reset();
            ( ( TableRelationVO ) form ).setTableRelationId( null );
            ( ( TableRelationVO ) form ).setMasterTableId( masterTableId );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         this.saveToken( request );

         final TableRelationVO tableRelationForm = ( TableRelationVO ) form;
         // ��ʼ��Service�ӿ�
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );

         final TableRelationVO tableRelationVO = tableRelationService.getTableRelationVOsByTableRelationId( tableRelationForm.getTableRelationId() );
         tableRelationVO.reset( null, request );
         request.setAttribute( "tableRelationForm", tableRelationVO );

         final List< MappingVO > tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
         request.setAttribute( "tablelist", tablelist );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageTableRelationSelectColumnForm" );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // ��ʼ��Service�ӿ�
         final TableRelationService tableRelationService = ( TableRelationService ) getService( "tableRelationService" );
         // ���Action Form            
         final TableRelationVO tableRelationVO = ( TableRelationVO ) form;

         String masterTableId = tableRelationVO.getMasterTableId();
         // ����ѡ�е�ID
         if ( tableRelationVO.getTableRelationId() != null && KANUtil.filterEmpty( tableRelationVO.getTableRelationId() ) != null )
         {

            tableRelationService.deleteTableRelationVO( tableRelationVO.getTableRelationId() );

         }
         // ���Selected IDs����Action
         tableRelationVO.setSubAction( "" );
         // ���Form
         ( ( TableRelationVO ) form ).reset();
         ( ( TableRelationVO ) form ).setTableRelationId( null );
         ( ( TableRelationVO ) form ).setMasterTableId( masterTableId );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

   }

   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String tableId = request.getParameter( "tableId" );
         final String columnId = request.getParameter( "columnId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         if ( KANUtil.filterEmpty( tableId ) != null )
         {
            TableDTO tableDTO = accountConstants.getTableDTOByTableId( tableId );
            // ��ʼ���ֶ�
            final List< MappingVO > columns = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
            // Send to client

            final StringBuffer rs = new StringBuffer();
            boolean selected = false;

            if ( columns != null && columns.size() > 0 )
            {
               if ( columns.size() == 1 )
               {
                  selected = true;
               }
               for ( MappingVO mappingVO : columns )
               {
                  String nameDB = tableDTO.getColumnVOByColumnId( mappingVO.getMappingId() ).getNameDB();
                  rs.append( "<option id=\"option_columnId_" + mappingVO.getMappingId() + "\" value=\"" + nameDB + "\" "
                        + ( ( columnId != null && columnId.trim().equals( nameDB ) || selected ) ? "selected" : "" ) + ">" + nameDB + "(" + mappingVO.getMappingValue()
                        + ")</option>" );
               }
            }

            out.println( rs.toString() );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return null;
   }
}