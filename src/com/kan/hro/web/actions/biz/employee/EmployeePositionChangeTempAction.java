package com.kan.hro.web.actions.biz.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeTempService;

public class EmployeePositionChangeTempAction extends BaseAction
{

   // Ա�� - �����춯
   public static final String ACCESS_ACTION = "HRO_EMPLOYEE_BATCH_POSITION_CHANGE";

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
         final EmployeePositionChangeTempService employeePositionChangeTempService = ( EmployeePositionChangeTempService ) getService( "employeePositionChangeTempService" );
         // ���Action Form
         final EmployeePositionChangeTempVO employeePositionChangeTempVO = ( EmployeePositionChangeTempVO ) form;
         employeePositionChangeTempVO.setBatchId( request.getParameter( "batchId" ) );
         // ����subAction
         dealSubAction( employeePositionChangeTempVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeePositionChangeTempVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeePositionChangeTempService.getEmployeePositionChangeTempVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeePositionChangeTempHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeePositionChangeTempTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listEmployeePositionChangeTemp" );
   }

   /***
    * �ύ submit_temp
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_temp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeePositionChangeTempVO employeePositionChangeTempVO = ( EmployeePositionChangeTempVO ) form;
         // ��ʼ��Service�ӿ�
         final EmployeePositionChangeTempService employeePositionChangeTempService = ( EmployeePositionChangeTempService ) getService( "employeePositionChangeTempService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( employeePositionChangeTempVO.getSelectedIds() ) != null )
         {
            // �ָ�ѡ����
            final String[] positionChangeIds = employeePositionChangeTempVO.getSelectedIds().split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( int i = 0; i < positionChangeIds.length; i++ )
            {
               positionChangeIds[ i ] = KANUtil.decodeStringFromAjax( positionChangeIds[ i ] );
            }

            rows = employeePositionChangeTempService.submitEmployeePositionChangeTempVOByPositionChangeIds( positionChangeIds, getUserId( request, null ), getIPAddress( request ), request.getLocale() );

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeePositionChangeTempVO, Operate.SUBMIT, null, "submit_temp:" + KANUtil.decodeSelectedIds( employeePositionChangeTempVO.getSelectedIds() ) );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /***
    * �˻� rollback_temp
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_temp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeePositionChangeTempVO employeePositionChangeTempVO = ( EmployeePositionChangeTempVO ) form;
         // ��ʼ��Service�ӿ�
         final EmployeePositionChangeTempService employeePositionChangeTempService = ( EmployeePositionChangeTempService ) getService( "employeePositionChangeTempService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( employeePositionChangeTempVO.getSelectedIds() ) != null )
         {
            // �ָ�ѡ����
            final String[] positionChangeIds = employeePositionChangeTempVO.getSelectedIds().split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( int i = 0; i < positionChangeIds.length; i++ )
            {
               positionChangeIds[ i ] = KANUtil.decodeStringFromAjax( positionChangeIds[ i ] );
            }

            rows = employeePositionChangeTempService.rollbackEmployeePositionChangeTempVOByPositionChangeIds( positionChangeIds, getUserId( request, null ) );

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, employeePositionChangeTempVO, Operate.ROllBACK, null, "rollback_temp:"
                     + KANUtil.decodeSelectedIds( employeePositionChangeTempVO.getSelectedIds() ) );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
