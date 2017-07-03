package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ColumnAction extends BaseAction
{

   public static String accessAction = "HRO_DEF_COLUMN";

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // ���Action Form
         final ColumnVO columnVO = ( ColumnVO ) form;

         // ����ɾ������
         if ( columnVO.getSubAction() != null && columnVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( columnVO );
         }

         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( columnVO.getSortColumn() == null || columnVO.getSortColumn().isEmpty() )
         {
            columnVO.setSortColumn( "columnIndex" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder columnHolder = new PagedListHolder();
         // ���뵱ǰҳ
         columnHolder.setPage( page );
         // ���뵱ǰֵ����
         columnHolder.setObject( columnVO );
         // ����ҳ���¼����
         columnHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         columnVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         columnService.getColumnVOsByCondition( columnHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( columnHolder, request );

         // Holder��д��Request����
         request.setAttribute( "columnHolder", columnHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listColumnTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listColumn" );
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // �����ظ��ύ
      this.saveToken( request );

      // ��ʼ��Action Form����
      ( ( ColumnVO ) form ).setStatus( ColumnVO.TRUE );
      ( ( ColumnVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ColumnVO ) form ).setColumnIndex( "100" );
      ( ( ColumnVO ) form ).setValueType( "2" );
      ( ( ColumnVO ) form ).setInputType( "1" );
      ( ( ColumnVO ) form ).setDisplayType( "1" );
      ( ( ColumnVO ) form ).setIsDBColumn( "2" );
      ( ( ColumnVO ) form ).setEditable( ColumnVO.TRUE );
      ( ( ColumnVO ) form ).setIsRequired( ColumnVO.FALSE );
      ( ( ColumnVO ) form ).setValidateLengthMin( "0" );
      ( ( ColumnVO ) form ).setValidateLengthMax( "0" );
      ( ( ColumnVO ) form ).setValidateRangeMin( "0" );
      ( ( ColumnVO ) form ).setValidateRangeMax( "0" );
      ( ( ColumnVO ) form ).setUseThinking( ColumnVO.FALSE );

      // ��ת���½�����
      return mapping.findForward( "manageColumn" );
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �ж��Ƿ�Ϊ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ColumnService columnService = ( ColumnService ) getService( "columnService" );

            // ��õ�ǰFORM
            final ColumnVO columnVO = ( ColumnVO ) form;

            columnVO.setCreateBy( getUserId( request, response ) );
            columnVO.setModifyBy( getUserId( request, response ) );
            columnVO.setAccountId( getAccountId( request, response ) );

            columnVO.setCanImport( ( columnVO.getCanImport() != null && columnVO.getCanImport().equalsIgnoreCase( "on" ) ) ? ColumnVO.TRUE : ColumnVO.FALSE );

            columnService.insertColumn( columnVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, columnVO, Operate.ADD, columnVO.getColumnId(), null );
         }
         else
         {
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // ���Action Form
            ( ( ColumnVO ) form ).reset();

            // ��ת���б����
            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );
         // ������ȡ�����
         String columnId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( columnId ) == null )
         {
            columnId = ( ( ColumnVO ) form ).getColumnId();
         }
         // ���ColumnVO����
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( columnId );

         columnVO.setCanImport( ( columnVO.getCanImport() != null && columnVO.getCanImport().equalsIgnoreCase( ColumnVO.TRUE ) ) ? "on" : "" );

         // ����SubAction
         columnVO.setSubAction( "viewObject" );
         columnVO.reset( null, request );
         // ��ColumnVO����request����
         request.setAttribute( "columnForm", columnVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageColumn" );
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ            
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ColumnService columnService = ( ColumnService ) getService( "columnService" );

            // ��������
            final String columnId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���ColumnVO����
            final ColumnVO columnVO = columnService.getColumnVOByColumnId( columnId );
            // װ�ؽ��洫ֵ
            columnVO.update( ( ColumnVO ) form );
            // ��ȡ��¼�û�
            columnVO.setModifyBy( getUserId( request, response ) );

            columnVO.setCanImport( ( columnVO.getCanImport() != null && columnVO.getCanImport().equalsIgnoreCase( "on" ) ) ? ColumnVO.TRUE : ColumnVO.FALSE );

            // �����޸ķ���
            columnService.updateColumn( columnVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, columnVO, Operate.MODIFY, columnVO.getColumnId(), null );
         }

         // ���Action Form
         ( ( ColumnVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // ���Action Form
         ColumnVO columnVO = ( ColumnVO ) form;
         // ����ѡ�е�ID
         if ( columnVO.getSelectedIds() != null && !columnVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : columnVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final ColumnVO tempColumnVO = columnService.getColumnVOByColumnId( selectedId );
               tempColumnVO.setModifyBy( getUserId( request, response ) );
               tempColumnVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               columnService.deleteColumn( tempColumnVO );
            }

            insertlog( request, columnVO, Operate.DELETE, null, columnVO.getSelectedIds() );
            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         columnVO.setSelectedIds( "" );
         columnVO.setSubAction( "" );
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

         // ��ȡcolumnId
         final String columnId = request.getParameter( "columnId" );

         // ��ʼ��Service
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // ��ȡColumnVO
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( columnId );

         // ��ʼ��JSONObject
         final JSONObject jsonObject = new JSONObject();

         if ( columnVO != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.putAll( JSONObject.fromObject( columnVO ) );
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
