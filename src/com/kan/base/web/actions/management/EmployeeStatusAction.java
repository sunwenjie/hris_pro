package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.EmployeeStatusService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class EmployeeStatusAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );
         // ���Action Form
         final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) form;

         // �����Action��ɾ���û��б�
         if ( employeeStatusVO.getSubAction() != null && employeeStatusVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder employeeStatusHolder = new PagedListHolder();
         // ���뵱ǰҳ
         employeeStatusHolder.setPage( page );
         // ���뵱ǰֵ����
         employeeStatusHolder.setObject( employeeStatusVO );
         // ����ҳ���¼����
         employeeStatusHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeStatusHolder = employeeStatusService.getEmployeeStatusVOsByCondition( employeeStatusHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeStatusHolder, request );

         // employeeStatusHolderд��request
         request.setAttribute( "employeeStatusHolder", employeeStatusHolder );
         // Ajax
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeStatusTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���б����
      request.setAttribute( "role", getRole( request, response ) );
      return mapping.findForward( "listEmployeeStatus" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( EmployeeStatusVO ) form ).setStatus( EmployeeStatusVO.TRUE );
      ( ( EmployeeStatusVO ) form ).setSubAction( CREATE_OBJECT );
      request.setAttribute( "role", getRole( request, response ) );
      return mapping.findForward( "manageEmployeeStatus" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );

            //���ActionForm
            final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) form;
            //��������
            employeeStatusVO.setCreateBy( getUserId( request, response ) );
            employeeStatusVO.setModifyBy( getUserId( request, response ) );
            employeeStatusVO.setAccountId( getAccountId( request, response ) );
            //�½�����
            employeeStatusService.insertEmployeeStatus( employeeStatusVO );

            // ���¼��س����е�EmployeeStatus
            constantsInit( "initEmployeeStatus", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Action Form
         ( ( EmployeeStatusVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service
         final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );
         // ������ȡ�����
         String employeeStatusId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( employeeStatusId ) == null )
         {
            employeeStatusId = ( ( EmployeeStatusVO ) form ).getEmployeeStatusId();
         }
         // �����������
         final EmployeeStatusVO employeeStatusVO = employeeStatusService.getEmployeeStatusVOByEmployeeStatusId( employeeStatusId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeStatusVO.reset( null, request );
         // ����Add��Update
         employeeStatusVO.setSubAction( VIEW_OBJECT );
         // ����ActionForm����ǰ��
         request.setAttribute( "employeeStatusForm", employeeStatusVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      request.setAttribute( "role", getRole( request, response ) );
      return mapping.findForward( "manageEmployeeStatus" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service
            final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );

            // ������ȡ�����
            final String employeeStatusId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // �����������
            final EmployeeStatusVO employeeStatusVO = employeeStatusService.getEmployeeStatusVOByEmployeeStatusId( employeeStatusId );
            // װ�ؽ��洫ֵ
            employeeStatusVO.update( ( EmployeeStatusVO ) form );
            // ��ȡ��¼�û�
            employeeStatusVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            employeeStatusService.updateEmployeeStatus( employeeStatusVO );
            // ���¼��س����е�EmployeeStatus
            constantsInit( "initEmployeeStatus", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Action Form
         ( ( EmployeeStatusVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
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
         final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );

         //���ActionForm
         final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) form;
         if ( employeeStatusVO.getSelectedIds() != null && !employeeStatusVO.getSelectedIds().equals( "" ) )
         {
            for ( String selectedId : employeeStatusVO.getSelectedIds().split( "," ) )
            {
               employeeStatusVO.setEmployeeStatusId( selectedId );
               employeeStatusVO.setModifyBy( getUserId( request, response ) );
               //����ɾ���ӿ�
               employeeStatusService.deleteEmployeeStatus( employeeStatusVO );
            }
         }

         // ���¼��س����е�EmployeeStatus
         constantsInit( "initEmployeeStatus", getAccountId( request, response ) );

         // ���Selected IDs����Action
         employeeStatusVO.setSelectedIds( "" );
         employeeStatusVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}