package com.kan.hro.web.actions.biz.travel;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.travel.TravelVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.travel.TravelService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class TravelAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_TRAVEL";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ȡsubAction
         final String subAction = getSubAction( form );

         // ��ʼ��Service�ӿ�
         final TravelService travelService = ( TravelService ) getService( "travelService" );

         // ���Action Form
         final TravelVO travelVO = ( TravelVO ) form;

         // HR_Service��¼��IN_House��¼
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {

            setDataAuth( request, response, travelVO );
         }
         
         // ����ɾ������
         if ( travelVO.getSubAction() != null && travelVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         
         if ( new Boolean( ajax ) )
         {
            decodedObject( travelVO );
         }

         // ���û��ָ��������Ĭ�ϰ�travelId����
         if ( travelVO.getSortColumn() == null || travelVO.getSortColumn().isEmpty() )
         {
            travelVO.setSortColumn( "travelId" );
            travelVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder travelHolder = new PagedListHolder();

         // ���뵱ǰҳ
         travelHolder.setPage( page );
         // ����Object
         travelHolder.setObject( travelVO );
         // ����ҳ���¼����
         travelHolder.setPageSize(listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         travelService.getTravelVOsByCondition( travelHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( travelHolder, request );

         // Holder��д��Request����
         request.setAttribute( "travelHolder", travelHolder );
         request.setAttribute( "role", getRole( request, response ) );

         // Ajax����
         if ( new Boolean( ajax ) )
         {

            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            // Ajax Table����
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTravelTable" );

         }

      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      return mapping.findForward( "listTravel" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // �����ظ��ύ
      this.saveToken( request );

      // InHouse����£�Ĭ�ϵ�ǰ��¼�û�
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( TravelVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }
      else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         ( ( TravelVO ) form ).setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
      }

      // ����Sub Action
      ( ( TravelVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( TravelVO ) form ).setStatus( TravelVO.TRUE );

      // ��ת��������
      return mapping.findForward( "manageTravel" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service
            final TravelService travelService = ( TravelService ) getService( "travelService" );

            // ��ȡ��ǰForm
            final TravelVO travelVO = ( TravelVO ) form;

            // ����ҳ��employeeId
            final String employeeId = travelVO.getEmployeeId();

            // У��employeeId�Ƿ����
            checkEmployeeId( mapping, travelVO, request, response );

            // ���Ϸ���employeeId��ת����ҳ��
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "employeeIdErrorMsg" ) ) != null )
            {
               travelVO.reset();
               travelVO.setEmployeeId( employeeId );
               travelVO.setNameZH( "" );
               travelVO.setNameEN( "" );

               return to_objectNew( mapping, travelVO, request, response );
            }

            travelVO.setCreateBy( getUserId( request, response ) );
            travelVO.setModifyBy( getUserId( request, response ) );

            // ���
            int result = travelService.insertTravel( travelVO );

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
            }

         }

         // ���FORM
         ( ( TravelVO ) form ).reset();
         ( ( TravelVO ) form ).setNameZH( "" );
         ( ( TravelVO ) form ).setNameEN( "" );

      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      // ��ת�б����
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final TravelService travelService = ( TravelService ) getService( "travelService" );
         // ������ȡ�����
         String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( leaveId == null || leaveId.trim().isEmpty() )
         {
            leaveId = ( ( TravelVO ) form ).getTravelId();
         }

         // ���LeaveVO����
         final TravelVO travelVO = travelService.getTravelVOByTravelId( leaveId );

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         travelVO.reset( null, request );
         // �����޸����
         travelVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "travelForm", travelVO );

      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      // ��ת�޸�ҳ��
      return mapping.findForward( "manageTravel" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final TravelService travelService = ( TravelService ) getService( "travelService" );
            // ������ȡ�����
            final String travelId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ���TravelVO����
            final TravelVO travelVO = travelService.getTravelVOByTravelId( travelId );

            // װ�ؽ��洫ֵ
            travelVO.update( ( ( TravelVO ) form ) );

            // ��ȡ��¼�û�
            travelVO.setModifyBy( getUserId( request, response ) );
            travelVO.setModifyDate( new Date() );

            travelService.updateTravel( travelVO );
            success( request, MESSAGE_TYPE_UPDATE );

         }
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final TravelService travelService = ( TravelService ) getService( "travelService" );
         // ���Action Form
         TravelVO travelVO = ( TravelVO ) form;

         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( travelVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : travelVO.getSelectedIds().split( "," ) )
            {
               travelVO = travelService.getTravelVOByTravelId( KANUtil.decodeStringFromAjax( selectedId ) );
               travelVO.setModifyBy( getUserId( request, response ) );
               travelVO.setModifyDate( new Date() );

               if ( travelService.deleteTravel( travelVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_DELETE );
               }
            }
         }

         // ���Selected IDs����Action
         ( ( TravelVO ) form ).setSelectedIds( "" );
         ( ( TravelVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

   }

   /**  
    * �������EmployeeId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

      // ���ActionForm
      final TravelVO travelVO = ( TravelVO ) form;

      // ��ͼ��ȡEmployeeVO 
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( travelVO.getEmployeeId() );

      // ������EmployeeVO��AccountId��ƥ�䵱ǰ
      if ( employeeVO == null
            || ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getAccountId() ) != null && !employeeVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "employeeIdErrorMsg", ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "Ա��" : "��Ա" ) + "ID��Ч��" );
      }

   }
   
   
   // �����ύ
   // Added by siuxia at 2014-06-04
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm
         final TravelVO travelVO = ( TravelVO ) form;
         // ��ʼ��Service�ӿ�
         final TravelService travelService = ( TravelService ) getService( "travelService" );

         // ��ù�ѡID
         final String travelIds = travelVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( travelIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = travelIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String selectId : selectedIdArray )
            {
               // ���LeaveVO
               final TravelVO submitTravelVO = travelService.getTravelVOByTravelId( KANUtil.decodeStringFromAjax( selectId ) );
               submitTravelVO.setModifyBy( getUserId( request, response ) );
               submitTravelVO.setModifyDate( new Date() );
               submitTravelVO.reset( mapping, request );

               rows = rows + travelService.submitTravel( submitTravelVO );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
