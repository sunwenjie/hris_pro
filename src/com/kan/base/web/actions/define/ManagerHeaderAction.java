package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ManagerHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_PAGE";

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
         final ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) form;

         // �����Action��ɾ��
         if ( managerHeaderVO.getSubAction() != null && managerHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( managerHeaderVO );
         }

         // ��ʼ��Service�ӿ�
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder managerHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         managerHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         managerHeaderHolder.setObject( managerHeaderVO );
         // ����ҳ���¼����
         managerHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         managerHeaderService.getManagerHeaderVOsByCondition( managerHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( managerHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "managerHeaderHolder", managerHeaderHolder );

         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listManagerHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listManagerHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // �ҵ���ǰAccountId������ManagerDTO
      final List< ManagerDTO > managerDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).MANAGER_DTO;

      // ���tableId�ѽ�����Manager�����Ƴ���
      if ( ( ( ManagerHeaderVO ) form ).getTables() != null && ( ( ManagerHeaderVO ) form ).getTables().size() > 0 && managerDTOs != null && managerDTOs.size() > 0 )
      {
         for ( int i = ( ( ManagerHeaderVO ) form ).getTables().size() - 1; i > 0; i-- )
         {
            for ( ManagerDTO managerDTO : managerDTOs )
            {
               if ( managerDTO.getManagerHeaderVO() != null && managerDTO.getManagerHeaderVO().getTableId() != null
                     && ( ( ManagerHeaderVO ) form ).getTables().get( i ).getMappingId().equals( managerDTO.getManagerHeaderVO().getTableId() ) )
               {
                  ( ( ManagerHeaderVO ) form ).getTables().remove( i );
                  break;
               }
            }
         }
      }

      // ���ó�ʼ���ֶ�
      ( ( ManagerHeaderVO ) form ).setStatus( ManagerHeaderVO.TRUE );
      ( ( ManagerHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageManagerHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��ManagerDetailVO
         final ManagerDetailVO managerDetailVO = new ManagerDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

            // ��õ�ǰFORM
            final ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) form;
            // ��ȡ��¼�û����˻�
            managerHeaderVO.setCreateBy( getUserId( request, response ) );
            managerHeaderVO.setModifyBy( getUserId( request, response ) );
            managerHeaderVO.setAccountId( getAccountId( request, response ) );

            // ���managerHeaderVO
            managerHeaderService.insertManagerHeader( managerHeaderVO );

            managerDetailVO.setManagerHeaderId( managerHeaderVO.getManagerHeaderId() );

            // ��ʼ�������־ö���
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, managerHeaderVO, Operate.ADD, managerHeaderVO.getManagerHeaderId(), null );
         }
         else
         {
            // ���Form
            ( ( ManagerHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // ��תList ManagerDetail����
         return new ManagerDetailAction().list_object( mapping, managerDetailVO, request, response );
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
            final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

            // �������
            final String managerHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���ManagerHeaderVO����
            final ManagerHeaderVO managerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( managerHeaderId );

            // װ�ؽ��洫ֵ
            managerHeaderVO.update( ( ManagerHeaderVO ) form );
            // ��ȡ��¼�û�
            managerHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            managerHeaderService.updateManagerHeader( managerHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, managerHeaderVO, Operate.MODIFY, managerHeaderVO.getManagerHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תList ManagerDetail����
      return new ManagerDetailAction().list_object( mapping, new ManagerDetailVO(), request, response );
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
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

         // ���Action Form
         ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( managerHeaderVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : managerHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               final ManagerHeaderVO tempManagerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( selectedId );
               tempManagerHeaderVO.setModifyBy( getUserId( request, response ) );
               tempManagerHeaderVO.setModifyDate( new Date() );
               managerHeaderService.deleteManagerHeader( tempManagerHeaderVO );
            }

            insertlog( request, managerHeaderVO, Operate.DELETE, null, managerHeaderVO.getSelectedIds() );

            // ��ʼ�������־ö���
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         ( ( ManagerHeaderVO ) form ).setSelectedIds( "" );
         ( ( ManagerHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
