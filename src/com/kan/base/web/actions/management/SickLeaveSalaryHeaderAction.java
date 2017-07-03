package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SickLeaveSalaryHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SickLeaveSalaryHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_SALARY_SICKLEAVE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         // ���Action Form
         final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         sickLeaveSalaryHeaderVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( sickLeaveSalaryHeaderVO.getSubAction() != null && sickLeaveSalaryHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( sickLeaveSalaryHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sickLeaveSalaryHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sickLeaveSalaryHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         sickLeaveSalaryHeaderHolder.setObject( sickLeaveSalaryHeaderVO );
         // ����ҳ���¼����
         sickLeaveSalaryHeaderHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         sickLeaveSalaryHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOsByCondition( sickLeaveSalaryHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( sickLeaveSalaryHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sickLeaveSalaryHeaderHolder", sickLeaveSalaryHeaderHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table���ã�ֱ�Ӵ���ShiftHeader JSP
            return mapping.findForward( "listSickLeaveSalaryHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSickLeaveSalaryHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      ( ( SickLeaveSalaryHeaderVO ) form ).reset( mapping, request );

      // ����Sub Action
      ( ( SickLeaveSalaryHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( SickLeaveSalaryHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageSickLeaveSalayHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = new SickLeaveSalaryDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
            // ��õ�ǰFORM
            final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) form;

            sickLeaveSalaryHeaderVO.setCreateBy( getUserId( request, response ) );
            sickLeaveSalaryHeaderVO.setModifyBy( getUserId( request, response ) );
            sickLeaveSalaryHeaderService.insertSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );

            // ���¼��ػ���
            constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, sickLeaveSalaryHeaderVO, Operate.ADD, sickLeaveSalaryHeaderVO.getHeaderId(), null );

            sickLeaveSalaryDetailVO.setHeaderId( sickLeaveSalaryHeaderVO.getHeaderId() );
         }
         else
         {
            // ���Action Form
            ( ( SickLeaveSalaryHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new SickLeaveSalaryDetailAction().list_object( mapping, sickLeaveSalaryDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡShiftHeaderVO����
            final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            sickLeaveSalaryHeaderVO.update( ( SickLeaveSalaryHeaderVO ) form );
            // ��ȡ��¼�û�
            sickLeaveSalaryHeaderVO.setModifyBy( getUserId( request, response ) );

            // �����޸ķ���
            sickLeaveSalaryHeaderService.updateSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );

            // ���¼��ػ���
            constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, sickLeaveSalaryHeaderVO, Operate.MODIFY, sickLeaveSalaryHeaderVO.getHeaderId(), null );
         }

         // ���Action Form
         ( ( SickLeaveSalaryHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new SickLeaveSalaryDetailAction().list_object( mapping, new SickLeaveSalaryDetailVO(), request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         // ���Action Form
         SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = ( SickLeaveSalaryHeaderVO ) form;

         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( sickLeaveSalaryHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, sickLeaveSalaryHeaderVO, Operate.DELETE, null, sickLeaveSalaryHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : sickLeaveSalaryHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( selectedId );
               sickLeaveSalaryHeaderVO.setHeaderId( selectedId );
               sickLeaveSalaryHeaderVO.setAccountId( getAccountId( request, response ) );
               sickLeaveSalaryHeaderVO.setModifyBy( getUserId( request, response ) );
               sickLeaveSalaryHeaderService.deleteSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );
            }
         }

         // ���Selected IDs����Action
         sickLeaveSalaryHeaderVO.setSelectedIds( "" );
         sickLeaveSalaryHeaderVO.setSubAction( "" );

         // ���¼��ػ���
         constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
