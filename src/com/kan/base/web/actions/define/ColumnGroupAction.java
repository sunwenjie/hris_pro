package com.kan.base.web.actions.define;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnGroupService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ColumnGroupAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_COLUMNGROUP";

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
         final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) form;

         dealSubAction( columnGroupVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder columnGroupHolder = new PagedListHolder();
         // ���뵱ǰҳ
         columnGroupHolder.setPage( page );
         // ���뵱ǰֵ����
         columnGroupHolder.setObject( columnGroupVO );
         // ����ҳ���¼����
         columnGroupHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         columnGroupVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         columnGroupService.getColumnGroupVOsByCondition( columnGroupHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( columnGroupHolder, request );

         // Holder��д��Request����
         request.setAttribute( "columnGroupHolder", columnGroupHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listColumnGroupTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listColumnGroup" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ��ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( ColumnGroupVO ) form ).setUseName( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setUseBorder( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setUsePadding( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setUseMargin( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setIsFlexable( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setIsDisplayed( ColumnGroupVO.TRUE );
      ( ( ColumnGroupVO ) form ).setStatus( ColumnGroupVO.TRUE );
      ( ( ColumnGroupVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageColumnGroup" );
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
            final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );

            // ��õ�ǰFORM
            final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) form;
            columnGroupVO.setAccountId( getAccountId( request, response ) );
            columnGroupVO.setCreateBy( getUserId( request, response ) );
            columnGroupVO.setModifyBy( getUserId( request, response ) );
            columnGroupService.insertColumnGroup( columnGroupVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initColumnGroup", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, columnGroupVO, Operate.ADD, columnGroupVO.getGroupId(), null );
         }
         else
         {
            // ���form
            ( ( ColumnGroupVO ) form ).reset();
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );
         // ������ȡ�����
         String groupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( groupId ) == null )
         {
            groupId = ( ( ColumnGroupVO ) form ).getGroupId();
         }

         // ���    ColumnGroupVO����
         final ColumnGroupVO columnGroupVO = columnGroupService.getColumnGroupVOByGroupId( groupId );
         // ����Add��update
         columnGroupVO.setSubAction( "viewObject" );
         columnGroupVO.reset( null, request );

         // ��ColumnGroupVO����request����
         request.setAttribute( "columnGroupForm", columnGroupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageColumnGroup" );
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
            final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );

            // ��ȡ��������� 
            final String groupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ��ȡColumnGroupVO
            final ColumnGroupVO columnGroupVO = columnGroupService.getColumnGroupVOByGroupId( groupId );
            // װ�ؽ��洫ֵ
            columnGroupVO.update( ( ColumnGroupVO ) form );
            // ��ȡ��¼�û�
            columnGroupVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            columnGroupService.updateColumnGroup( columnGroupVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initColumnGroup", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, columnGroupVO, Operate.MODIFY, columnGroupVO.getGroupId(), null );
         }

         // ���Form
         ( ( ColumnGroupVO ) form ).reset();
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
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );

         // ���Action Form
         final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) form;

         // ����ѡ�е�ID
         if ( columnGroupVO.getSelectedIds() != null && !columnGroupVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : columnGroupVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               columnGroupVO.setGroupId( selectedId );
               columnGroupVO.setModifyBy( getUserId( request, response ) );
               columnGroupVO.setAccountId( getAccountId( request, response ) );
               // ���ɾ��
               columnGroupService.deleteColumnGroup( columnGroupVO );
            }

            insertlog( request, columnGroupVO, Operate.DELETE, null, columnGroupVO.getSelectedIds() );
            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initColumnGroup", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         columnGroupVO.setSelectedIds( "" );
         columnGroupVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
