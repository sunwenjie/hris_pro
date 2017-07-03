package com.kan.hro.web.actions.biz.payment;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.payment.PayslipDTO;
import com.kan.hro.domain.biz.payment.PayslipDetailView;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.payment.PayslipDetailViewService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class PayslipViewAction extends BaseAction
{

   // ��ǰAction��Ӧ��JavaObjectName
   public static final String JAVA_OBJECT_NAME = "com.kan.hro.domain.biz.payment.PayslipDTO";

   public static final String JAVA_OBJECT_NAME_REPORT = "com.kan.hro.domain.biz.payment.PayslipDTO.Report";

   public static final String JAVA_OBJECT_NAME_REPORT_ICLICK = "com.kan.hro.domain.biz.payment.PayslipDTO.Report-MPF";

   public static String ACCESSACTION = "JAVA_OBJECT_PAYSLIP";

   // �·ݼ�д
   public static final String[] MONTH_SHORT_ARRAY = new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

   public static Map< String, String > BANK_ACCOUNT = new HashMap< String, String >()
   {

      /**  
      * serialVersionUID:TODO����һ�仰�������������ʾʲô��  
      *  
      * @since Ver 1.1  
      */

      private static final long serialVersionUID = 1L;

      {
         put( "", "" );
      }
   };

   /**  
    * List Object
    *	 ��������б�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final PayslipDetailViewService payslipViewService = ( PayslipDetailViewService ) getService( "payslipDetailViewService" );
         // ���Action Form
         final PayslipDetailView payslipDetailView = ( PayslipDetailView ) form;

         // ����Form
         dealSubAction( payslipDetailView, mapping, form, request, response );

         if ( KANUtil.filterEmpty( payslipDetailView.getMonthlyBegin() ) == null || KANUtil.filterEmpty( payslipDetailView.getMonthlyEnd() ) == null )
         {
            payslipDetailView.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            payslipDetailView.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }

         //��������Ȩ��
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESSACTION, payslipDetailView );
            setDataAuth( request, response, payslipDetailView );
         }

         // ���SubAction
         final String subAction = getSubAction( payslipDetailView );
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME, KANUtil.filterEmpty( getCorpId( request, null ) ) );
         // ��������Ȩ��
         if ( KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {
            payslipDetailView.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
            payslipDetailView.setRole( BaseAction.getRole( request, response ) );
            //�ѷ���
            payslipDetailView.setStatus( "3" );
         }
         //         // �����inHouse
         //         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         //         {
         //            // �����HR����
         //            if ( !isHRFunction( request, response ) )
         //            {
         //               // �����ˡ��޸���
         //               payslipDetailView.setEmployeeId( getEmployeeId( request, response ) );
         //            }
         //         }

         PagedListHolder pagedListHolder = new PagedListHolder();

         // �������������
         if ( listDTO != null && !listDTO.isSearchFirst() || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            if ( KANUtil.filterEmpty( payslipDetailView.getMonthly() ) == null )
            {
               payslipDetailView.setMonthly( KANUtil.getMonthly( new Date() ) );
            }
         }
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            payslipDetailView.setClientId( BaseAction.getClientId( request, response ) );
            payslipDetailView.setRole( KANConstants.ROLE_CLIENT );
         }

         // ���뵱ǰֵ����
         pagedListHolder.setObject( payslipDetailView );

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listDTO.getPageSize() );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         payslipViewService.getPayslipDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );

         // ��ʼ��PayslipDTO
         final PayslipDTO payslipDTO = new PayslipDTO();

         // ȷ���Ƿ�����
         if ( KANUtil.filterEmpty( payslipDetailView.getSortOrder() ) != null && KANUtil.filterEmpty( payslipDetailView.getSortColumn() ) != null )
         {
            // �趨�����ֶ�
            payslipDTO.setSortOrder( payslipDetailView.getSortOrder() );
            payslipDTO.setSortColumn( payslipDetailView.getSortColumn() );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( payslipDetailView );
            // ����
            Collections.sort( pagedListHolder.getSource(), payslipDTO );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", super.getRole( request, response ) );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               if ( request.getParameter( "apc" ) != null )
               {
                  return export_apc( mapping, form, request, response );
               }
               //               return new DownloadFileAction().specialExportList( mapping, form, request, response );
               return new DownloadFileAction().specialExportListAddExtendColumns( mapping, form, request, response, listDTO );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_NAME ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPayslipView" );
   }

   /**  
    * Load Special HTML
    *	��������������
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward load_special_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // �����б��Ǳ���
         final String flag = request.getParameter( "flag" );

         final String monthlyBegin = request.getParameter( "monthlyBegin" );
         final String monthlyEnd = request.getParameter( "monthlyEnd" );
         final String status = request.getParameter( "status" );
         final String orderId = request.getParameter( "orderId" );
         final String entityId = request.getParameter( "entityId" );
         final String tempBranchIds = request.getParameter( "tempBranchIds" );
         final String tempPositionIds = request.getParameter( "tempPositionIds" );
         final String tempParentBranchIds = request.getParameter( "tempParentBranchIds" );

         // ��ȡ�·�
         final List< MappingVO > beginMonthlys = KANUtil.getMonthsByCondition( 15, 6 );
         final List< MappingVO > endMonthlys = KANUtil.getMonthsByCondition( 15, 6 );
         // ���㶩��
         final List< MappingVO > orderIds = getClientOrderIds( request, response );
         // ����ʵ��
         final List< MappingVO > entities = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getEntities( request.getLocale().getLanguage(), getCorpId( request, response ) );
         entities.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ״̬
         final List< MappingVO > statuses = KANUtil.getMappings( request.getLocale(), "business.payment.status" );
         // ����
         final List< MappingVO > branchs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBranchs( request.getLocale().getLanguage(), getCorpId( request, null ) );

         if ( branchs != null )
         {
            branchs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            statuses.clear();
            final List< MappingVO > clientStatus = KANUtil.getMappings( request.getLocale(), "business.payment.status" );

            for ( MappingVO mappingVO : clientStatus )
            {
               if ( !mappingVO.getMappingId().equals( "1" ) )
               {
                  statuses.add( mappingVO );
               }
            }
         }

         final List< MappingVO > positions = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositions( request.getLocale().getLanguage(), getCorpId( request, null ) );

         if ( positions != null )
         {
            positions.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // ��ʼ��StringBuffer
         final StringBuffer rs = new StringBuffer();

         if ( KANUtil.filterEmpty( flag ) != null && KANUtil.filterEmpty( flag ).equals( "report" ) )
         {
            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "payment.payslip.report.work.dept" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( branchs, "tempBranchIds", "tempBranchIds", tempBranchIds, null, null ) );
            rs.append( "</li>" );

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "payment.payslip.report.position.name" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( positions, "tempPositionIds", "tempPositionIds", tempPositionIds, null, null ) );
            rs.append( "</li>" );

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "payment.payslip.report.parent.dept" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( branchs, "tempParentBranchIds", "tempParentBranchIds", tempParentBranchIds, null, null ) );
            rs.append( "</li>" );
         }

         rs.append( "<li>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "public.begin.monthly" ) + "</label>" );
         rs.append( KANUtil.getSelectHTML( beginMonthlys, "monthlyBegin", "monthlyBegin", monthlyBegin, "checkMonthly();", null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "public.end.monthly" ) + "</label>" );
         rs.append( KANUtil.getSelectHTML( endMonthlys, "monthlyEnd", "monthlyEnd", monthlyEnd, "checkMonthly();", null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "security.entity" ) + "</label>" );
         rs.append( KANUtil.getSelectHTML( entities, "entityId", "entityId", entityId, null, null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "public.order2" ) + "</label>" );
         rs.append( KANUtil.getSelectHTML( orderIds, "orderId", "orderId", orderId, null, null ) );
         rs.append( "</li>" );

         if ( !KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {
            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "public.status" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( statuses, "status", "status", status, null, null ) );
            rs.append( "</li>" );
         }

         // Send to client
         out.println( rs.toString() );
         out.flush();
         out.close();

         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��ȡ���ʱ����б�
   public ActionForward list_object_report( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final PayslipDetailViewService payslipViewService = ( PayslipDetailViewService ) getService( "payslipDetailViewService" );
         // ���Action Form
         final PayslipDetailView payslipDetailView = ( PayslipDetailView ) form;

         if ( KANUtil.filterEmpty( payslipDetailView.getMonthlyBegin() ) == null || KANUtil.filterEmpty( payslipDetailView.getMonthlyEnd() ) == null )
         {
            payslipDetailView.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            payslipDetailView.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, payslipDetailView );
         setDataAuth( request, response, payslipDetailView );

         // ���SubAction
         final String subAction = getSubAction( payslipDetailView );
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME_REPORT, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �����inHouse
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // �����HR����
            if ( !isHRFunction( request, response ) )
            {
               // �����ˡ��޸���
               payslipDetailView.setEmployeeId( getEmployeeId( request, response ) );
            }
         }

         PagedListHolder pagedListHolder = new PagedListHolder();

         // �������������
         if ( listDTO != null && !listDTO.isSearchFirst() || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            if ( KANUtil.filterEmpty( payslipDetailView.getMonthly() ) == null )
            {
               payslipDetailView.setMonthly( KANUtil.getMonthly( new Date() ) );
            }
         }

         // ���뵱ǰֵ����
         pagedListHolder.setObject( payslipDetailView );

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listDTO.getPageSize() );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         payslipViewService.getPayslipDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );

         // ��ʼ��PayslipDTO
         final PayslipDTO payslipDTO = new PayslipDTO();

         // ȷ���Ƿ�����
         if ( KANUtil.filterEmpty( payslipDetailView.getSortOrder() ) != null && KANUtil.filterEmpty( payslipDetailView.getSortColumn() ) != null )
         {
            // �趨�����ֶ�
            payslipDTO.setSortOrder( payslipDetailView.getSortOrder() );
            payslipDTO.setSortColumn( payslipDetailView.getSortColumn() );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( payslipDetailView );
            // ����
            Collections.sort( pagedListHolder.getSource(), payslipDTO );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               if ( request.getParameter( "apc" ) != null )
               {
                  return export_apc( mapping, form, request, response );
               }
               return new DownloadFileAction().specialExportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_NAME_REPORT ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPayslipViewReport" );
   }

   // ��ȡ���ʱ����б�
   public ActionForward list_object_report_iClick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final PayslipDetailViewService payslipViewService = ( PayslipDetailViewService ) getService( "payslipDetailViewService" );
         // ���Action Form
         final PayslipDetailView payslipDetailView = ( PayslipDetailView ) form;

         if ( KANUtil.filterEmpty( payslipDetailView.getMonthlyBegin() ) == null || KANUtil.filterEmpty( payslipDetailView.getMonthlyEnd() ) == null )
         {
            payslipDetailView.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            payslipDetailView.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, payslipDetailView );
         setDataAuth( request, response, payslipDetailView );

         // ���SubAction
         final String subAction = getSubAction( payslipDetailView );
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME_REPORT_ICLICK, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �����inHouse
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // �����HR����
            if ( !isHRFunction( request, response ) )
            {
               // �����ˡ��޸���
               payslipDetailView.setEmployeeId( getEmployeeId( request, response ) );
            }
         }

         PagedListHolder pagedListHolder = new PagedListHolder();

         // �������������
         if ( listDTO != null && !listDTO.isSearchFirst() || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            if ( KANUtil.filterEmpty( payslipDetailView.getMonthly() ) == null )
            {
               payslipDetailView.setMonthly( KANUtil.getMonthly( new Date() ) );
            }
         }

         // ���뵱ǰֵ����
         pagedListHolder.setObject( payslipDetailView );

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listDTO.getPageSize() );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         payslipViewService.getPayslipDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );

         // ��ʼ��PayslipDTO
         final PayslipDTO payslipDTO = new PayslipDTO();

         // ȷ���Ƿ�����
         if ( KANUtil.filterEmpty( payslipDetailView.getSortOrder() ) != null && KANUtil.filterEmpty( payslipDetailView.getSortColumn() ) != null )
         {
            // �趨�����ֶ�
            payslipDTO.setSortOrder( payslipDetailView.getSortOrder() );
            payslipDTO.setSortColumn( payslipDetailView.getSortColumn() );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( payslipDetailView );
            // ����
            Collections.sort( pagedListHolder.getSource(), payslipDTO );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().specialExportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_NAME_REPORT_ICLICK ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPayslipViewReport-IClick" );
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

   public ActionForward export_apc( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         final PayslipDetailView object = ( PayslipDetailView ) pagedListHolder.getObject();
         final StringBuffer rs = new StringBuffer();
         if ( pagedListHolder != null && KANUtil.filterEmpty( object.getEntityId() ) != null )
         {
            rs.append( "F" + object.getBANK_ACCOUNT().get( object.getEntityId() ) + "I01" );
            rs.append( "SAL" + MONTH_SHORT_ARRAY[ Integer.valueOf( object.getMonthly().split( "/" )[ 1 ] ) - 1 ] + object.getMonthly().split( "/" )[ 0 ].substring( 2, 4 ) + "    " );
            rs.append( KANUtil.formatDate( new Date(), "ddMMyy" ) );
            rs.append( "K********  " );
            rs.append( getStrBatch( 21, " " ) );
            rs.append( "1" );

            int totalCount = 0;
            double totalSalary = 0;

            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object payslipDTOObject : pagedListHolder.getSource() )
               {
                  final PayslipDTO payslipDTO = ( PayslipDTO ) payslipDTOObject;
                  payslipDTO.getPayslipHeaderView().reset( null, request );
                  final String employeeShortName = payslipDTO.getPayslipHeaderView().getDynaColumns().get( "jiancheng" );
                  final String employeeNameEN = payslipDTO.getPayslipHeaderView().getEmployeeNameEN();
                  final String bankName = payslipDTO.getPayslipHeaderView().getDecodeBankId();
                  final String bankAccount = payslipDTO.getPayslipHeaderView().getBankAccount();
                  String afterTaxSalary = payslipDTO.getPayslipHeaderView().getAfterTaxSalary();

                  rs.append( " " );

                  // 2-13     Ա����Ϣ-���  ����ӿո�
                  if ( KANUtil.filterEmpty( employeeShortName ) != null )
                  {
                     if ( employeeShortName.length() > 12 )
                        rs.append( employeeShortName.substring( 0, 12 ) );
                     else
                        rs.append( employeeShortName + getStrBatch( 12 - employeeShortName.length(), " " ) );
                  }
                  else
                  {
                     rs.append( getStrBatch( 12, " " ) );
                  }

                  // 14-33    Ա����Ϣ-Ӣ���� ����ӿո�
                  if ( KANUtil.filterEmpty( employeeNameEN ) != null )
                  {
                     if ( employeeNameEN.length() > 20 )
                        rs.append( employeeNameEN.substring( 0, 20 ) );
                     else
                        rs.append( employeeNameEN + getStrBatch( 20 - employeeNameEN.length(), " " ) );
                  }
                  else
                  {
                     rs.append( getStrBatch( 20, " " ) );
                  }

                  // 34-36    ȡ����
                  if ( KANUtil.filterEmpty( bankName ) != null )
                  {
                     if ( bankName.length() > 3 )
                        rs.append( bankName.substring( 0, 3 ) );
                     else
                        rs.append( bankName + getStrBatch( 3 - bankName.length(), " " ) );
                  }
                  else
                  {
                     rs.append( "ERR" );
                  }

                  // 37-48    ȡ���п��� ����ӿո�
                  if ( KANUtil.filterEmpty( bankAccount ) != null )
                  {
                     if ( bankAccount.length() > 12 )
                        rs.append( bankAccount.substring( 0, 12 ) );
                     else
                        rs.append( bankAccount + getStrBatch( 12 - bankAccount.length(), " " ) );
                  }
                  else
                  {
                     rs.append( "ERR" + getStrBatch( 9, " " ) );
                  }

                  totalSalary = totalSalary + Double.valueOf( afterTaxSalary );

                  // 49-58    Ա��֧�����   ����ǰ�油��0��
                  if ( KANUtil.filterEmpty( afterTaxSalary ) != null )
                  {
                     afterTaxSalary = afterTaxSalary.replace( ".", "" );
                     if ( afterTaxSalary.length() > 10 )
                        rs.append( afterTaxSalary.substring( 0, 10 ) );
                     else
                        rs.append( getStrBatch( 10 - afterTaxSalary.length(), "0" ) + afterTaxSalary );
                  }
                  else
                  {
                     rs.append( getStrBatch( 10, "0" ) );
                  }

                  // 59-80    �ո�
                  rs.append( getStrBatch( 22, " " ) );

                  totalCount++;
               }
            }

            // 44-48    �������ε�����  ����ǰ�油��0��
            if ( totalCount > 9999 )
               rs.replace( 43, 44, String.valueOf( totalCount ) );
            else
               rs.replace( 43, 44, getStrBatch( 5 - String.valueOf( totalCount ).length(), "0" ) + totalCount );

            // 49-58   ֧�������ܶ��С���㣬����ǰ�油��0��
            if ( totalSalary > 0 )
            {
               String temp = KANUtil.formatNumber( String.valueOf( totalSalary ), getAccountId( request, response ) ).replace( ".", "" );
               int length = temp.length();
               if ( length > 10 )
                  rs.replace( 48, 49, temp.substring( 0, 10 ) );
               else
                  rs.replace( 48, 49, getStrBatch( 10 - length, "0" ) + temp );
            }
            else
            {
               rs.replace( 48, 49, getStrBatch( 10, "0" ) );
            }

         }

         // ���÷����ļ�����
         response.setContentType( "text/plain" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "apc.txt", "UTF-8" ).getBytes(), "UTF-8" ) );

         BufferedOutputStream buff = null;
         ServletOutputStream out = null;

         out = response.getOutputStream();
         buff = new BufferedOutputStream( out );

         buff.write( rs.toString().getBytes( "utf-8" ) );
         buff.flush();
         buff.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   private List< MappingVO > getClientOrderIds( HttpServletRequest request, HttpServletResponse response ) throws Exception
   {
      // ��ʼ�� Service
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // ��ʼ��ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
      clientOrderHeaderVO.setCorpId( getCorpId( request, response ) );
      clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
      clientOrderHeaderVO.setStatus( "3, 5" );

      // ��õ�¼�ͻ���Ӧ������������Ϣ
      final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

      // ��ʼ������ѡ��
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

      if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
      {
         for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs )
         {
            // ��ʼ��ClientOrderHeaderVO
            final ClientOrderHeaderVO tempClientOrderHeaderVO = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;

            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempClientOrderHeaderVO.getOrderHeaderId() );
            mappingVO.setMappingValue( tempClientOrderHeaderVO.getOrderHeaderId() );

            // ����������Ϣ
            if ( tempClientOrderHeaderVO.getDescription() != null && !tempClientOrderHeaderVO.getDescription().trim().isEmpty() )
            {
               mappingVO.setMappingValue( tempClientOrderHeaderVO.getOrderHeaderId() + " - " + tempClientOrderHeaderVO.getDescription() );
            }
            else
            {
               mappingVO.setMappingValue( tempClientOrderHeaderVO.getOrderHeaderId() );
            }

            mappingVOs.add( mappingVO );
         }
      }

      return mappingVOs;
   }

   private String getStrBatch( final int number, final String str )
   {
      final StringBuffer rs = new StringBuffer();
      if ( number > 0 )
      {
         for ( int i = 0; i < number; i++ )
         {
            rs.append( str );
         }
      }
      return rs.toString();
   }

}
