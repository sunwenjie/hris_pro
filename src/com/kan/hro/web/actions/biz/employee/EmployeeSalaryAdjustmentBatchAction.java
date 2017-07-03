package com.kan.hro.web.actions.biz.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.tag.AuthConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.poi.POIUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentBatchService;

public class EmployeeSalaryAdjustmentBatchAction extends BaseAction
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
         final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) getService( "employeeSalaryAdjustmentBatchService" );
         // ���Action Form
         final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO = ( EmployeeSalaryAdjustmentBatchVO ) form;
         employeeSalaryAdjustmentBatchVO.setAccessAction( ACCESS_ACTION );
         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( employeeSalaryAdjustmentBatchVO.getSortColumn() == null || employeeSalaryAdjustmentBatchVO.getSortColumn().isEmpty() )
         {
            employeeSalaryAdjustmentBatchVO.setSortOrder( "desc" );
            employeeSalaryAdjustmentBatchVO.setSortColumn( "batchId" );
         }

         //setDataAuth( request, response, employeeSalaryAdjustmentBatchVO );
         Set< String > rulePositionIds = new HashSet< String >();
         rulePositionIds.add( getPositionId( request, null ) );
         employeeSalaryAdjustmentBatchVO.setRulePositionIds( rulePositionIds );
         employeeSalaryAdjustmentBatchVO.setRulePublic( AuthConstants.RULE_UN_PUBLIC );
         
         // ����subAction
         dealSubAction( employeeSalaryAdjustmentBatchVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeSalaryAdjustmentBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeSalaryAdjustmentBatchService.getEmployeeSalaryAdjustmentBatchVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeSalaryAdjustmentBatchHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeSalaryAdjustmentBatchTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listEmployeeSalaryAdjustmentBatch" );
   }

   /***
    * �ύ Ա�� - ������н
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO = ( EmployeeSalaryAdjustmentBatchVO ) form;

         // ��ʼ��Service�ӿ�
         final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) getService( "employeeSalaryAdjustmentBatchService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) != null )
         {
            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : employeeSalaryAdjustmentBatchVO.getSelectedIds().split( "," ) )
            {
               final EmployeeSalaryAdjustmentBatchVO submitObject = employeeSalaryAdjustmentBatchService.getEmployeeSalaryAdjustmentBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setIp( getIPAddress( request ) );
               submitObject.setLocale( request.getLocale() );
               rows = rows + employeeSalaryAdjustmentBatchService.submitEmployeeSalaryAdjustmentBatch( submitObject );
            }

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeSalaryAdjustmentBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) );
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
    * �˻� Ա�� - ������н
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException
    */
   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO = ( EmployeeSalaryAdjustmentBatchVO ) form;

         // ��ʼ��Service�ӿ�
         final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) getService( "employeeSalaryAdjustmentBatchService" );
         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) != null )
         {
            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : employeeSalaryAdjustmentBatchVO.getSelectedIds().split( "," ) )
            {
               final EmployeeSalaryAdjustmentBatchVO rollbackObject = employeeSalaryAdjustmentBatchService.getEmployeeSalaryAdjustmentBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               rows = rows + employeeSalaryAdjustmentBatchService.rollbackEmployeeSalaryAdjustmentBatch( rollbackObject );
            }

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, employeeSalaryAdjustmentBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) );
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

   /***
    * ������н - ������ʼģ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward export_template( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      boolean lang_zh = request.getLocale().getLanguage().equalsIgnoreCase( "zh" );
      try
      {
         String templateName = lang_zh ? "������нģ��.xlsx" : "Batch Adjustment salary Template(EN).xlsx";
         final String path = KANUtil.basePath + "/" + templateName;
         // ����File
         final File file = new File( path );
         // ����ģ��·��
         if ( file.exists() )
         {
            // ��ʼ��������
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );
            final List< MappingVO > salaryItemMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, null ) );
            final String[] itemArray = KANUtil.mappingListToArray( salaryItemMappingVOs );
            POIUtil.SetDataValidation( workbook, itemArray, 1, 3, 1000, 3, ( lang_zh ? "н�ʿ�Ŀ" : "Salary Item" ) );
            final List< MappingVO > changeReasonMappingVOs = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
            final String[] changeReasonArray = KANUtil.mappingListToArray( changeReasonMappingVOs );
            POIUtil.SetDataValidation( workbook, changeReasonArray, 1, 7, 1000, 7, ( lang_zh ? "�춯ԭ��" : "Movement Category" ) );

            // ��ʼ��OutputStream
            final OutputStream os = response.getOutputStream();
            // ���÷����ļ�����
            response.setContentType( "application/x-msdownload" );
            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( templateName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
            // Excel�ļ�д��OutputStream
            workbook.write( os );
            // ���OutputStream
            os.flush();
            //�ر���  
            os.close();
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

}
