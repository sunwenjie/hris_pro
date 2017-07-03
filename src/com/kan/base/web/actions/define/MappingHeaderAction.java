package com.kan.base.web.actions.define;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.MappingHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class MappingHeaderAction extends BaseAction
{
	public static final String accessAction = "HRO_CLIENT_IMPORT";
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
         final MappingHeaderVO mappingHeaderVO = ( MappingHeaderVO ) form;
         // �ж�������import����export
         final String flag = request.getParameter( "flag" );
         if ( "import".equalsIgnoreCase( flag ) )
         {
            mappingHeaderVO.setImportId( "0" );
         }
         else
         {
            mappingHeaderVO.setReportId( "0" );
         }
         // ��Ҫ���õ�ǰ�û�AccountId
         mappingHeaderVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( mappingHeaderVO.getSubAction() != null && mappingHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( mappingHeaderVO );
         }

         // ��ʼ��Service�ӿ�
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder mappingHeaderPagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         mappingHeaderPagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         mappingHeaderPagedListHolder.setObject( mappingHeaderVO );
         // ����ҳ���¼����
         mappingHeaderPagedListHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         mappingHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         mappingHeaderService.getMappingHeaderVOsByCondition( mappingHeaderPagedListHolder, true );
         refreshHolder( mappingHeaderPagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "mappingHeaderPagedListHolder", mappingHeaderPagedListHolder );
         request.setAttribute( "flag", flag );
         request.setAttribute("authAccessAction", flag.equals("import")?"HRO_CLIENT_IMPORT":"HRO_CLIENT_EXPORT");
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listMappingHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listMappingHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( MappingHeaderVO ) form ).setStatus( MappingHeaderVO.TRUE );
      ( ( MappingHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      request.setAttribute( "flag", request.getParameter( "flag" ) );

      // ��ת���½�����
      return mapping.findForward( "manageMappingHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��MappingDetailVO
         final MappingDetailVO mappingDetailVO = new MappingDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

            // ��õ�ǰFORM
            final MappingHeaderVO mappingHeaderVO = ( MappingHeaderVO ) form;
            mappingHeaderVO.setCreateBy( getUserId( request, response ) );
            mappingHeaderVO.setModifyBy( getUserId( request, response ) );
            mappingHeaderVO.setAccountId( getAccountId( request, response ) );

            // ���MappingHeaderVO
            mappingHeaderService.insertMappingHeader( mappingHeaderVO );
            mappingDetailVO.setMappingHeaderId( mappingHeaderVO.getMappingHeaderId() );

            // ���¼��س����е�Table��MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
         }
         else
         {
            // ���form
            ( ( MappingHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // ��תList MappingDetail����
         return new MappingDetailAction().list_object( mapping, mappingDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
            final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

            // ��ȡ���� - �����
            final String mappingHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ���MappingHeaderVO����
            final MappingHeaderVO mappingHeaderVO = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( mappingHeaderId );
            // װ�ؽ��洫ֵ
            mappingHeaderVO.update( ( MappingHeaderVO ) form );
            // ��ȡ��¼�û�
            mappingHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            mappingHeaderService.updateMappingHeader( mappingHeaderVO );

            // ���¼��س����е�Table��MappingHeader
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initMappingHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         }

         // ���Action Form
         ( ( MappingHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new MappingDetailAction().list_object( mapping, new MappingDetailVO(), request, response );
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
         final MappingHeaderService mappingHeaderService = ( MappingHeaderService ) getService( "mappingHeaderService" );

         // ���Action Form
         final MappingHeaderVO mappingHeaderVO = ( MappingHeaderVO ) form;

         // ����ѡ�е�ID
         if ( mappingHeaderVO.getSelectedIds() != null && !mappingHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : mappingHeaderVO.getSelectedIds().split( "," ) )
            {
               // ����ID��ȡ��Ӧ��mappingHeaderVO
               final MappingHeaderVO mappingHeaderVOForDel = mappingHeaderService.getMappingHeaderVOByMappingHeaderId( selectedId );
               mappingHeaderVOForDel.setModifyBy( getUserId( request, response ) );
               mappingHeaderVOForDel.setModifyDate( new Date() );
               mappingHeaderService.deleteMappingHeader( mappingHeaderVOForDel );
            }
         }

         // ���¼��س����е�Table��MappingHeader
         constantsInit( "initTable", getAccountId( request, response ) );
         constantsInit( "initMappingHeader", getAccountId( request, response ) );

         // ���Selected IDs����Action
         mappingHeaderVO.setSelectedIds( "" );
         mappingHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
