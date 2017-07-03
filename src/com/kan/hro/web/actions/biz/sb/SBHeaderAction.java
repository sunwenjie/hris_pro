package com.kan.hro.web.actions.biz.sb;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.service.inf.biz.sb.SBDetailService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

public class SBHeaderAction extends BaseAction
{
   // ��ǰAction��Ӧ��JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �ж��б��Ƿ���Ҫ��ӵ�������
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // �����������ID
         String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         // ��ʼ��SBBatchVO
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setCorpId( getCorpId( request, response ) );
         sbBatchVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbBatchVO );
         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setClientId( getClientId( request, response ) );
            sbBatchVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ��ʼ��SBHeaderVO
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbHeaderVO.setCorpId( getCorpId( request, response ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );

         // ����ҳ���PageFlag��StatusFlag
         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_HEADER );
         sbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );

         sbBatchVO.reset( null, request );
         sbBatchVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );
         request.setAttribute( "sbBatchForm", sbBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         sbHeaderHolder.setPage( page );

         sbHeaderVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         // ������������ֶ�
         sbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );
         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setClientId( getClientId( request, response ) );
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // ���û��ָ��������Ĭ�ϰ�����Э����ˮ������
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "employeeSBId,monthly" );
            sbHeaderVO.setSortOrder( "" );
         }

         if ( KANUtil.filterEmpty( sbHeaderVO.getStatus() ) == null )
         {
            if ( request.getParameter( "statusFlag" ) != null )
            {
               sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
            }
         }

         // ���ʻ�
         sbHeaderVO.reset( null, request );
         sbHeaderVO.getSbStatuses().remove( 0 ); // Add by siuxia
         //sbHeaderVO.getStatuses().add( 0, sbHeaderVO.getEmptyMappingVO() );
         if ( sbHeaderVO.getStatus().equals( "1" ) )
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( sbHeaderVO.getStatuses().get( 1 ) );
            sbHeaderVO.setStatuses( statuses );
         }
         else if ( sbHeaderVO.getStatus().equals( "2" ) )
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( sbHeaderVO.getStatuses().get( 2 ) );
            sbHeaderVO.setStatuses( statuses );
         }
         else
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( sbHeaderVO.getStatuses().get( 3 ) );
            statuses.add( sbHeaderVO.getStatuses().get( 4 ) );
            statuses.add( sbHeaderVO.getStatuses().get( 5 ) );
            sbHeaderVO.setStatuses( statuses );
         }

         // ��ѡ���籣״̬���� Add by siuxia
         if ( sbHeaderVO.getSbStatusArray() != null && sbHeaderVO.getSbStatusArray().length > 0 )
         {
            sbHeaderVO.setSbStatus( KANUtil.toJasonArray( sbHeaderVO.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( sbHeaderVO );
         }

         // ���뵱ǰֵ����
         sbHeaderHolder.setObject( sbHeaderVO );
         // ����ҳ���¼����
         sbHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbHeaderService.getSBHeaderVOsByCondition( sbHeaderHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         refreshHolder( sbHeaderHolder, request );

         request.setAttribute( "sbHeaderForm", sbHeaderVO );
         // Holder��д��Request����
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );

         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               // Holder��д��Request����
               request.setAttribute( "holderName", "pagedListHolder" );
               request.setAttribute( "fileName", "�籣������ϸ" );
               request.setAttribute( "pagedListHolder", sbHeaderHolder );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );
               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }

            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTable" );
         }
         final String boolSearchHeader = request.getParameter( "searchHeader" );

         if ( sbHeaderHolder == null || sbHeaderHolder.getHolderSize() == 0 )
         {
            if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
            {
               SBBatchVO sbBatch = new SBBatchVO();
               sbBatch.reset( null, request );
               sbBatch.setBatchId( "" );
               request.setAttribute( "sbBatchForm", sbBatch );
               request.setAttribute( "messageInfo", true );
               return new SBAction().list_estimation( mapping, sbBatch, request, response );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listHeader" );
   }

   /**  
    * Export Object
    * ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward export_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SBHeaderService sbBatchService = ( SBHeaderService ) getService( "sbHeaderService" );

         // �����������ID
         String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ���Action Form
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         String selectIds = "";
         decodedObject( sbHeaderVO );
         if ( sbHeaderVO.getSelectedIds() != null && sbHeaderVO.getSelectedIds() != "" )
         {
            for ( String selectId : sbHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectIds.length() == 0 )
                  selectIds = KANUtil.decodeStringFromAjax( selectId );
               else
                  selectIds = selectIds + "," + KANUtil.decodeStringFromAjax( selectId );
            }
            sbHeaderVO.setHeaderId( selectIds );
         }
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbHeaderVO.setCorpId( getCorpId( request, response ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );

         // ��ѡ���籣״̬���� Add by siuxia
         if ( sbHeaderVO.getSbStatusArray() != null && sbHeaderVO.getSbStatusArray().length > 0 )
         {
            sbHeaderVO.setSbStatus( KANUtil.toJasonArray( sbHeaderVO.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰֵ����
         pagedListHolder.setObject( sbHeaderVO );
         // ����Service���������ö��󷵻�
         sbBatchService.getSBDTOsByCondition( pagedListHolder );
         // Holder��д��Request����
         request.setAttribute( "holderName", "pagedListHolder" );
         request.setAttribute( "fileName", "�籣" );
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
         request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

         // �����ļ�
         return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ������ͷ
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "���ID" );
      nameZHs.add( "����״̬" );
      nameZHs.add( "�걨�·�" );
      nameZHs.add( "�����·�" );
      nameZHs.add( "�籣��������" );
      nameZHs.add( "��Ӧ��ID" );
      nameZHs.add( "��Ӧ������" );
      nameZHs.add( "������ϢID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "�������У�" );
      nameZHs.add( "ǩԼ�����������" );
      nameZHs.add( "������" );
      nameZHs.add( "������Ϣ��ʼʱ��" );
      nameZHs.add( "������Ϣ״̬" );
      nameZHs.add( "��ʱ��" );
      nameZHs.add( "�籣״̬" );
      nameZHs.add( "�籣����˾��" );
      nameZHs.add( "�籣�����ˣ�" );
      nameZHs.add( "��������" );
      nameZHs.add( "���֤����" );
      nameZHs.add( "״̬" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // ��������
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();

      nameSyses.add( "headerId" );
      nameSyses.add( "flag" );
      nameSyses.add( "monthly" );
      nameSyses.add( "accountMonthlyList" );
      nameSyses.add( "employeeSBName" );
      nameSyses.add( "vendorId" );
      nameSyses.add( "vendorName" );
      nameSyses.add( "contractId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "orderDescription" );
      nameSyses.add( "decodeBranch" );
      nameSyses.add( "contractStartDate" );
      nameSyses.add( "contractStatus" );
      nameSyses.add( "startDate" );
      nameSyses.add( "decodeSbStatus" );
      nameSyses.add( "decodeAmountCompany" );
      nameSyses.add( "decodeAmountPersonal" );
      nameSyses.add( "decodeResidencyType" );
      nameSyses.add( "certificateNumber" );
      nameSyses.add( "decodeAdditionalStatus" );

      return KANUtil.stringListToArray( nameSyses );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // ��ʼ����Ĭ��Ϊ��Ԥ����
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2,13";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
   }

   public ActionForward list_object_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         String monthly = request.getParameter( "monthly" );
         if ( "���������".equals( monthly ) )
         {
            monthly = "";
         }
         if ( KANUtil.filterEmpty( monthly ) != null )
         {
            sbHeaderVO.setMonthly( monthly );
         }
         List< Object > sbHeaderVOs = sbHeaderService.getMonthliesBySBHeaderVO( sbHeaderVO );
         request.setAttribute( "sbHeaderVOs", sbHeaderVOs );
         return mapping.findForward( "sbMonthlyList" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward sbDetail_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );
         EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final String monthly = request.getParameter( "monthly" );
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setMonthly( monthly );
         sbHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         final List< Object > sbDetailObjects = sbDetailService.getSBDetailVOsBySbHeaderCond( sbHeaderVO );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( getEmployeeId( request, response ) );
         request.setAttribute( "sbDetailVOs", sbDetailObjects );
         request.setAttribute( "employeeVO", employeeVO );
         request.setAttribute( "monthly", monthly );
         Float mountPersonal = 0.00f;
         Float mountCompany = 0.00f;
         for ( Object obj : sbDetailObjects )
         {
            mountPersonal += Float.parseFloat( ( ( SBDetailVO ) obj ).getDecodeAmountPersonal() );
            mountCompany += Float.parseFloat( ( ( SBDetailVO ) obj ).getDecodeAmountCompany() );
         }
         DecimalFormat df = new DecimalFormat( "##0.00" );
         request.setAttribute( "mountPersonal", df.format( mountPersonal ) );
         request.setAttribute( "mountCompany", df.format( mountCompany ) );
         return mapping.findForward( "sbDetailMobile" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward modify_flag_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String headerId = request.getParameter( "headerId" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBHeaderVO sbHeaderVO = sbHeaderService.getSBHeaderVOByHeaderId( headerId );
         sbHeaderVO.setFlag( request.getParameter( "flag" ) );
         sbHeaderService.updateSBHeader( sbHeaderVO );
         out.print( "success" );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return null;
   }

   public ActionForward updatePaid( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final String certificateIds = request.getParameter( "certificateIds" );
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         final List< String > ids = new ArrayList< String >();
         Scanner scanner = new Scanner( certificateIds );
         while ( scanner.hasNext() )
         {
            ids.add( scanner.nextLine().trim() );
         }
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setAccountId( getAccountId( request, null ) );
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setCertificateNumbers( ids );
         sbHeaderService.updateSBHeaderPaid( sbHeaderVO );
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return list_object( mapping, form, request, response );
   }

   public ActionForward list_object_nativeAPP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         String monthly = request.getParameter( "monthly" );
         if ( KANUtil.filterEmpty( monthly ) != null )
         {
            sbHeaderVO.setMonthly( monthly );
         }
         final List< Object > sbHeaderVOs = sbHeaderService.getMonthliesBySBHeaderVO( sbHeaderVO );
         final JSONArray jsonArray = new JSONArray();
         for ( Object obj : sbHeaderVOs )
         {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put( "monthly", ( ( SBHeaderVO ) obj ).getMonthly() );
            jsonObject.put( "amountPersonal", ( ( SBHeaderVO ) obj ).getAmountPersonal() );
            jsonObject.put( "amountCompany", ( ( SBHeaderVO ) obj ).getAmountCompany() );
            jsonArray.add( jsonObject );
         }

         out.print( jsonArray.toString() );
         out.flush();
         out.close();
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward to_sbDetail_nativeAPP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );
         EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final String monthly = request.getParameter( "monthly" );
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setMonthly( monthly );
         sbHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         final List< Object > sbDetailObjects = sbDetailService.getSBDetailVOsBySbHeaderCond( sbHeaderVO );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( getEmployeeId( request, response ) );
         request.setAttribute( "sbDetailVOs", sbDetailObjects );
         request.setAttribute( "employeeVO", employeeVO );
         request.setAttribute( "monthly", monthly );
         Float mountPersonal = 0.00f;
         Float mountCompany = 0.00f;
         for ( Object obj : sbDetailObjects )
         {
            mountPersonal += Float.parseFloat( ( ( SBDetailVO ) obj ).getDecodeAmountPersonal() );
            mountCompany += Float.parseFloat( ( ( SBDetailVO ) obj ).getDecodeAmountCompany() );
         }
         //var s1 = [132,second/2,32,10,0];
         //var s2 = [26,44,12,2,second/5];
         //var ticks = ['�籣1','�籣2','�籣3','�籣4','�籣5'];
         // ��ȡ���е��籣����
         final List< String > distinctItemIds = getAllSBList( sbDetailObjects );
         final JSONArray ticks = new JSONArray();
         final JSONArray s1 = new JSONArray();
         final JSONArray s2 = new JSONArray();
         for ( int i = 0; i < distinctItemIds.size(); i++ )
         {
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getItemVOByItemId( distinctItemIds.get( i ) );
            ticks.add( itemVO.getNameZH() );
            //
            Double tempCompany = 0d;
            Double tempPersonal = 0d;
            for ( Object obj : sbDetailObjects )
            {
               final SBDetailVO sbDetailVO = ( SBDetailVO ) obj;
               if ( distinctItemIds.get( i ).equals( sbDetailVO.getItemId() ) )
               {
                  final String tempC = KANUtil.filterEmpty( sbDetailVO.getAmountCompany() ) == null ? "0" : sbDetailVO.getAmountCompany();
                  final String tempP = KANUtil.filterEmpty( sbDetailVO.getAmountPersonal() ) == null ? "0" : sbDetailVO.getAmountPersonal();
                  tempCompany += Double.parseDouble( tempC );
                  tempPersonal += Double.parseDouble( tempP );
               }
            }
            s1.add( tempCompany );
            s2.add( tempPersonal );

         }
         JSONObject jsonObject = new JSONObject();
         jsonObject.put( "ticks", ticks );
         jsonObject.put( "s1", s1 );
         jsonObject.put( "s2", s2 );
         jsonObject.put( "mountPersonal", KANUtil.formatNumber( String.valueOf( mountPersonal ), getAccountId( request, null ) ) );
         jsonObject.put( "mountCompany", KANUtil.formatNumber( String.valueOf( mountCompany ), getAccountId( request, null ) ) );
         out.print( jsonObject.toString() );
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   //���� sbDetailObjects ��ȡ���е��籣
   private List< String > getAllSBList( final List< Object > sbDetailObjects )
   {
      final List< String > itemIds = new ArrayList< String >();
      for ( Object obj : sbDetailObjects )
      {
         final SBDetailVO sbDetailVO = ( SBDetailVO ) obj;
         itemIds.add( sbDetailVO.getItemId() );
      }
      return KANUtil.getDistinctList( itemIds );
   }

   public void checkSBStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final PrintWriter out = response.getWriter();
         String addCount = "0";
         String backCount = "0";
         String selectedIds = request.getParameter( "selectedIds" );
         if ( StringUtils.isNotEmpty( selectedIds ) )
         {
            String[] ids = selectedIds.split( "," );
            String[] headerId = new String[ ids.length ];
            for ( int i = 0; i < headerId.length; i++ )
            {
               headerId[ i ] = KANUtil.decodeStringFromAjax( ids[ i ] );
            }
            addCount = sbHeaderService.getSBToApplyForMoreStatusCountByHeaderIds( headerId );
            backCount = sbHeaderService.getSBToApplyForResigningStatusCountByByHeaderIds( headerId );
         }
         final JSONObject jsonObject = new JSONObject();
         jsonObject.put( "addCount", addCount );
         jsonObject.put( "backCount", backCount );
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_workflow_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // �����������ID
         String historyId = request.getParameter( "historyId" );

         final HistoryService historyService = ( HistoryService ) getService( "historyService" );

         HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

         // ��ʼ��SBBatchVO
         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         final SBBatchVO sbBatchVO = ( SBBatchVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );

         //         sbBatchVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbBatchVO );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setClientId( getClientId( request, response ) );
            sbBatchVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }
         // ��� PageFlag
         final String pageFlag = sbBatchVO.getPageFlag();
         // ���ActionFlag
         final String actionFlag = sbBatchVO.getSubAction();

         final String selectedIds = sbBatchVO.getSelectedIds();

         String objectIdString = "";
         // �ָ�ѡ����
         final String[] selectedIdArray = selectedIds.split( "," );

         for ( int i = 0; i < selectedIdArray.length; i++ )
         {
            // ����
            objectIdString += KANUtil.decodeStringFromAjax( selectedIdArray[ i ] ) + ",";
         }
         if ( StringUtils.isNotBlank( objectIdString ) && objectIdString.length() > 0 )
         {
            objectIdString = objectIdString.substring( 0, objectIdString.length() - 1 );
         }

         // ��ʼ��SBHeaderVO
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setStatus( sbBatchVO.getStatus() );
         sbHeaderVO.setCorpId( getCorpId( request, response ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         // ����PageFlag�ύ
         if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
         {
            sbHeaderVO.setBatchId( objectIdString );
         }
         else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
         {
            sbHeaderVO.setHeaderId( objectIdString );
         }

         setDataAuth( request, response, sbHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         sbHeaderHolder.setPage( page );

         //         sbHeaderVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         // ������������ֶ�
         sbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );
         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setClientId( getClientId( request, response ) );
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // ���û��ָ��������Ĭ�ϰ�����Э����ˮ������
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "employeeSBId,monthly" );
            sbHeaderVO.setSortOrder( "" );
         }

         // ���ʻ�
         sbHeaderVO.reset( null, request );

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( sbHeaderVO );
         }

         // ���뵱ǰֵ����
         sbHeaderHolder.setObject( sbHeaderVO );
         // ����ҳ���¼����
         sbHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbHeaderService.getSBHeaderVOsByCondition( sbHeaderHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         refreshHolder( sbHeaderHolder, request );

         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         // ��ʼ��SBBatchVO
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         SBBatchVO sbBatchVOCondition = new SBBatchVO();
         SBBatchVO sbBatchForm = null;
         sbBatchVOCondition.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );

         // ����PageFlag�ύ
         if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
         {
            sbBatchVOCondition.setBatchId( objectIdString );
         }
         else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
         {
            if ( sbHeaderHolder.getSource() != null && sbHeaderHolder.getSource().size() > 0 )
            {
               sbBatchVOCondition.setBatchId( ( ( SBBatchVO ) sbHeaderHolder.getSource().get( 0 ) ).getBatchId() );
            }
         }
         sbBatchVOCondition.setCorpId( getCorpId( request, response ) );
         sbBatchVOCondition.setAccountId( getAccountId( request, response ) );
         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchForm = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         request.setAttribute( "sbBatchForm", sbBatchForm );

         // Holder��д��Request����
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
         request.setAttribute( "role", getRole( request, response ) );
         //         final String boolSearchHeader = request.getParameter( "searchHeader" );

         //         if ( sbHeaderHolder == null || sbHeaderHolder.getHolderSize() == 0 )
         //         {
         //            if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
         //            {
         //               SBBatchVO sbBatch = new SBBatchVO();
         //               sbBatch.reset( null, request );
         //               sbBatch.setBatchId( "" );
         //               request.setAttribute( "sbBatchForm", sbBatch );
         //               request.setAttribute( "messageInfo", true );
         //               return new SBAction().list_estimation( mapping, sbBatch, request, response );
         //            }
         //         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listHeaderWorkflow" );
   }
}
