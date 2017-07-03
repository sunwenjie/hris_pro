package com.kan.hro.web.actions.biz.importExcel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportHeaderVO;
import com.kan.hro.service.inf.biz.employee.EmployeeInsuranceNoImportHeaderService;

public class EmployeeInsuranceNoImportHeaderAction extends BaseAction
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
         final EmployeeInsuranceNoImportHeaderService employeeInsuranceNoImportHeaderService = ( EmployeeInsuranceNoImportHeaderService ) getService( "employeeInsuranceNoImportHeaderService" );

         // ���Action Form
         final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO = ( EmployeeInsuranceNoImportHeaderVO ) form;

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( employeeInsuranceNoImportHeaderVO.getSortColumn() == null || employeeInsuranceNoImportHeaderVO.getSortColumn().isEmpty() )
         {
            employeeInsuranceNoImportHeaderVO.setSortOrder( "desc" );
            employeeInsuranceNoImportHeaderVO.setSortColumn( "a.cardnoId" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeInsuranceNoImportHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeInsuranceNoImportHeaderService.getEmployeeInsuranceNoImportHeaderVOsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeInsuranceNoHeaderHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeInsuranceNoImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listEmployeeInsuranceNoImportHeader" );
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
      final EmployeeInsuranceNoImportHeaderService employeeInsuranceNoImportHeaderService = ( EmployeeInsuranceNoImportHeaderService ) getService( "employeeInsuranceNoImportHeaderService" );
      String ids = request.getParameter( "selectedIds" );
      if ( StringUtils.isNotEmpty( ids ) )
      {
         String batchId = request.getParameter( "batchId" );
         int count = employeeInsuranceNoImportHeaderService.backUpRecord( ids.split( "," ), batchId );
         if ( count == 0 )
         {
            EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = new EmployeeInsuranceNoImportBatchVO();
            employeeInsuranceNoImportBatchVO.reset( mapping, request );
            return new EmployeeInsuranceNoImportBatchAction().list_object( mapping, employeeInsuranceNoImportBatchVO, request, response );
         }
         else
         {
            final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO = ( EmployeeInsuranceNoImportHeaderVO ) form;
            employeeInsuranceNoImportHeaderVO.setBatchId( batchId );
            employeeInsuranceNoImportHeaderVO.setSelectedIds( "" );
            return list_object( mapping, employeeInsuranceNoImportHeaderVO, request, response );
         }
      }
      else
      {
         EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = new EmployeeInsuranceNoImportBatchVO();
         employeeInsuranceNoImportBatchVO.reset( mapping, request );
         return new EmployeeInsuranceNoImportBatchAction().list_object( mapping, employeeInsuranceNoImportBatchVO, request, response );
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
