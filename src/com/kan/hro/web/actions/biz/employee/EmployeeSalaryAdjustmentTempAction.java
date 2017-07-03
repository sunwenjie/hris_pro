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
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentTempService;

public class EmployeeSalaryAdjustmentTempAction extends BaseAction
{
   // Ա�� - ������н
   public static final String ACCESS_ACTION = "HRO_EMPLOYEE_BATCH_SALARY_ADJUSTMENT";

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
         final EmployeeSalaryAdjustmentTempService employeeSalaryAdjustmentTempService = ( EmployeeSalaryAdjustmentTempService ) getService( "employeeSalaryAdjustmentTempService" );
         // ���Action Form
         final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO = ( EmployeeSalaryAdjustmentTempVO ) form;
         employeeSalaryAdjustmentTempVO.setBatchId( request.getParameter( "batchId" ) );
         // ����subAction
         dealSubAction( employeeSalaryAdjustmentTempVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeSalaryAdjustmentTempVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeSalaryAdjustmentTempService.getEmployeeSalaryAdjustmentTempVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeSalaryAdjustmentTempHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeSalaryAdjustmentTempTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listEmployeeSalaryAdjustmentTemp" );
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
         final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO = ( EmployeeSalaryAdjustmentTempVO ) form;
         // ��ʼ��Service�ӿ�
         final EmployeeSalaryAdjustmentTempService employeeSalaryAdjustmentTempService = ( EmployeeSalaryAdjustmentTempService ) getService( "employeeSalaryAdjustmentTempService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getSelectedIds() ) != null )
         {
            // �ָ�ѡ����
            final String[] salaryAdjustmentIds = employeeSalaryAdjustmentTempVO.getSelectedIds().split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( int i = 0; i < salaryAdjustmentIds.length; i++ )
            {
               salaryAdjustmentIds[ i ] = KANUtil.decodeStringFromAjax( salaryAdjustmentIds[ i ] );
            }

            rows = employeeSalaryAdjustmentTempService.submitEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentIds( salaryAdjustmentIds, getUserId( request, null ), getIPAddress( request ), request.getLocale() );

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeSalaryAdjustmentTempVO, Operate.SUBMIT, null, "submit_temp:"
                     + KANUtil.decodeSelectedIds( employeeSalaryAdjustmentTempVO.getSelectedIds() ) );
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
         final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO = ( EmployeeSalaryAdjustmentTempVO ) form;
         // ��ʼ��Service�ӿ�
         final EmployeeSalaryAdjustmentTempService employeeSalaryAdjustmentTempService = ( EmployeeSalaryAdjustmentTempService ) getService( "employeeSalaryAdjustmentTempService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getSelectedIds() ) != null )
         {
            // �ָ�ѡ����
            final String[] salaryAdjustmentIds = employeeSalaryAdjustmentTempVO.getSelectedIds().split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( int i = 0; i < salaryAdjustmentIds.length; i++ )
            {
               salaryAdjustmentIds[ i ] = KANUtil.decodeStringFromAjax( salaryAdjustmentIds[ i ] );
            }

            rows = employeeSalaryAdjustmentTempService.rollbackEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentIds( salaryAdjustmentIds, getUserId( request, null ) );

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, employeeSalaryAdjustmentTempVO, Operate.ROllBACK, null, "rollback_temp:"
                     + KANUtil.decodeSelectedIds( employeeSalaryAdjustmentTempVO.getSelectedIds() ) );
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
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
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
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}
