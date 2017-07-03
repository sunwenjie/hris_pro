package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TableService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class TableAction extends BaseAction
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
         final TableVO tableVO = ( TableVO ) form;

         // ���û��ָ��������Ĭ�ϰ� tableIndex����
         if ( tableVO.getSortColumn() == null || tableVO.getSortColumn().isEmpty() )
         {
            tableVO.setSortColumn( "moduleType,tableIndex" );
            tableVO.setSortOrder( "asc" );
         }
         
         // ����ɾ������
         if ( tableVO.getSubAction() != null && tableVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( tableVO );
         }

         // ��ʼ��Service�ӿ�
         final TableService tableService = ( TableService ) getService( "tableService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder tablePagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         tablePagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         tablePagedListHolder.setObject( tableVO );
         // ����ҳ���¼����
         tablePagedListHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         tableVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         tableService.getTableVOsByCondition( tablePagedListHolder, true );
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
            return mapping.findForward( "listTableTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listTable" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( TableVO ) form ).setStatus( TableVO.TRUE );
      ( ( TableVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( TableVO ) form ).setTableIndex( "100" );
      ( ( TableVO ) form ).setTableType( TableVO.TRUE );
      ( ( TableVO ) form ).setRole( TableVO.TRUE );

      // ��ת���½�����
      return mapping.findForward( "manageTable" );
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
            final TableService tableService = ( TableService ) getService( "tableService" );
            // ��õ�ǰFORM
            final TableVO tableVO = ( TableVO ) form;
            tableVO.setCreateBy( getUserId( request, response ) );
            tableVO.setModifyBy( getUserId( request, response ) );
            tableVO.setAccountId( getAccountId( request, response ) );

            // Checkbox����
            tableVO.setCanManager( ( tableVO.getCanManager() != null && tableVO.getCanManager().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanList( ( tableVO.getCanList() != null && tableVO.getCanList().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanSearch( ( tableVO.getCanSearch() != null && tableVO.getCanSearch().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanImport( ( tableVO.getCanImport() != null && tableVO.getCanImport().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanReport( ( tableVO.getCanReport() != null && tableVO.getCanManager().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );

            // ���TableVO
            tableService.insertTable( tableVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Action Form
         ( ( TableVO ) form ).reset();
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final TableService tableService = ( TableService ) getService( "tableService" );
         // ������ȡ�����
         final String tableId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "tableId" ), "UTF-8" ) );
         // ���TableVO����
         final TableVO tableVO = tableService.getTableVOByTableId( tableId );
         // Checkbox����
         tableVO.setCanManager( ( tableVO.getCanManager() != null && tableVO.getCanManager().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanList( ( tableVO.getCanList() != null && tableVO.getCanList().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanSearch( ( tableVO.getCanSearch() != null && tableVO.getCanSearch().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanImport( ( tableVO.getCanImport() != null && tableVO.getCanImport().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanReport( ( tableVO.getCanReport() != null && tableVO.getCanReport().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         // ����Add��Update
         tableVO.setSubAction( "viewObject" );
         tableVO.reset( null, request );
         // ��TableVO����request����
         request.setAttribute( "tableForm", tableVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageTable" );
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
            final TableService tableService = ( TableService ) getService( "tableService" );
            // ������ȡ�����
            final String tableId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "tableId" ), "GBK" ) );
            // ��ȡTableVO����
            final TableVO tableVO = tableService.getTableVOByTableId( tableId );
            // װ�ؽ��洫ֵ
            tableVO.update( ( TableVO ) form );
            // Checkbox����
            tableVO.setCanManager( ( tableVO.getCanManager() != null && tableVO.getCanManager().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanList( ( tableVO.getCanList() != null && tableVO.getCanList().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanSearch( ( tableVO.getCanSearch() != null && tableVO.getCanSearch().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanImport( ( tableVO.getCanImport() != null && tableVO.getCanImport().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanReport( ( tableVO.getCanReport() != null && tableVO.getCanReport().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            // ��ȡ��¼�û�
            tableVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            tableService.updateTable( tableVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form
         ( ( TableVO ) form ).reset();
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
         final TableService tableService = ( TableService ) getService( "tableService" );
         // ���Action Form
         final TableVO tableVO = ( TableVO ) form;
         // ����ѡ�е�ID
         if ( tableVO.getSelectedIds() != null && !tableVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : tableVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               tableVO.setTableId( selectedId );
               tableVO.setModifyBy( getUserId( request, response ) );
               tableVO.setAccountId( getAccountId( request, response ) );
               tableService.deleteTable( tableVO );
            }
         }
         // ���Selected IDs����Action
         tableVO.setSelectedIds( "" );
         tableVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   //* Add by siuxia 2014-01-20 *//
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

         // ��ȡtableId
         final String tableId = request.getParameter( "tableId" );

         // ��ȡTableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );

         // ��ʼ��JSONObject
         final JSONObject jsonObject = new JSONObject();

         if ( tableDTO != null && tableDTO.getTableVO() != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "nameZH", tableDTO.getTableVO().getNameZH() );
            jsonObject.put( "nameEN", tableDTO.getTableVO().getNameEN() );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

}