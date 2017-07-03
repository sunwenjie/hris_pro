package com.kan.hro.web.actions.biz.importExcel;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeInsuranceNoImportBatchService;

public class EmployeeInsuranceNoImportBatchAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public static final String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CARDNO_TEMP";

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
         final EmployeeInsuranceNoImportBatchService employeeInsuranceNoImportBatchService = ( EmployeeInsuranceNoImportBatchService ) getService( "employeeInsuranceNoImportBatchService" );

         // ���Action Form
         final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = ( EmployeeInsuranceNoImportBatchVO ) form;

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( employeeInsuranceNoImportBatchVO.getSortColumn() == null || employeeInsuranceNoImportBatchVO.getSortColumn().isEmpty() )
         {
            employeeInsuranceNoImportBatchVO.setSortOrder( "desc" );
            employeeInsuranceNoImportBatchVO.setSortColumn( "batchId" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeInsuranceNoImportBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeInsuranceNoImportBatchService.getEmployeeInsuranceNoImportBatchVOsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeInsuranceNoImportBatchHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeInsuranceNoImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listEmployeeInsuranceNoImportBatch" );
   }

   /**
    * ���£�ֱ��update��ʽ����Ľ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = ( EmployeeInsuranceNoImportBatchVO ) form;
         employeeInsuranceNoImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final EmployeeInsuranceNoImportBatchService employeeInsuranceNoImportBatchService = ( EmployeeInsuranceNoImportBatchService ) getService( "employeeInsuranceNoImportBatchService" );

         // ��ù�ѡID
         final String batchIds = employeeInsuranceNoImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeInsuranceNoImportBatchVO submitObject = employeeInsuranceNoImportBatchService.getEmployeeInsuranceNoImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setModifyDate( new Date() );
               rows = rows + employeeInsuranceNoImportBatchService.updateEmployeeInsuranceNoImportBatch( submitObject );
            }

            if ( rows == 0 )
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

   /**
    * �˻�,����ɾ��temp��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = ( EmployeeInsuranceNoImportBatchVO ) form;
         employeeInsuranceNoImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final EmployeeInsuranceNoImportBatchService employeeInsuranceNoImportBatchService = ( EmployeeInsuranceNoImportBatchService ) getService( "employeeInsuranceNoImportBatchService" );

         // ��ù�ѡID
         final String batchIds = employeeInsuranceNoImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeInsuranceNoImportBatchVO submitObject = employeeInsuranceNoImportBatchService.getEmployeeInsuranceNoImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + employeeInsuranceNoImportBatchService.backBatch( submitObject );
            }

            success( request, MESSAGE_TYPE_SUBMIT, "�˻�" );
         }
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

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
