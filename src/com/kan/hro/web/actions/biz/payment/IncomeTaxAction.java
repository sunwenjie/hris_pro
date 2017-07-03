package com.kan.hro.web.actions.biz.payment;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.kan.hro.domain.biz.payment.PayslipHeaderView;
import com.kan.hro.domain.biz.payment.PayslipTaxDTO;
import com.kan.hro.service.inf.biz.payment.PayslipHeaderViewService;

public class IncomeTaxAction extends BaseAction
{

   // ��ǰAction��Ӧ��JavaObjectName
   public static final String JAVA_OBJECT_NAME = "com.kan.hro.domain.biz.payment.PayslipTaxDTO";

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
         final PayslipHeaderViewService payslipHeaderViewService = ( PayslipHeaderViewService ) getService( "payslipHeaderViewService" );
         // ���Action Form
         final PayslipHeaderView payslipHeaderView = ( PayslipHeaderView ) form;
         // ���SubAction
         final String subAction = getSubAction( payslipHeaderView );

         String accessAction = "JAVA_OBJECT_INCOME_TAX";
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, payslipHeaderView );
         setDataAuth( request, response, payslipHeaderView );
         
         if ( KANUtil.filterEmpty( payslipHeaderView.getStatus() ) == null )
         {
            payslipHeaderView.setStatus( "0" );
         }

         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME, KANUtil.filterEmpty( getCorpId( request, response ) ) );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( listDTO.getPageSize() );
            
            if ( new Boolean( ajax ) )
            {
               decodedObject( payslipHeaderView );
            }
            
            // ���뵱ǰֵ����
            pagedListHolder.setObject( payslipHeaderView );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            payslipHeaderViewService.getPayslipTaxDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );

            // ȷ���Ƿ�����
            if ( KANUtil.filterEmpty( payslipHeaderView.getSortOrder() ) != null && KANUtil.filterEmpty( payslipHeaderView.getSortColumn() ) != null )
            {
               // ��ʼ��PayslipTaxDTO
               final PayslipTaxDTO payslipTaxDTO = new PayslipTaxDTO();
               // �趨�����ֶ�
               payslipTaxDTO.setSortOrder( payslipHeaderView.getSortOrder() );
               payslipTaxDTO.setSortColumn( payslipHeaderView.getSortColumn() );
               // ���뵱ǰֵ����
               pagedListHolder.setObject( payslipHeaderView );
               // ����
               Collections.sort( pagedListHolder.getSource(), payslipTaxDTO );
            }
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
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_NAME ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listIncomeTax" );
   }

   // ���������HTML
   public void load_special_html( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         final String monthly = request.getParameter( "monthly" );
         final String status = request.getParameter( "status" );
         final String itemGroupId = request.getParameter( "itemGroupId" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ״̬
         final PayslipHeaderView payslipDetailView = new PayslipHeaderView();
         payslipDetailView.reset( null, request );
         // ȥ��״̬Ϊ���½����Ĳ�ѯ
         payslipDetailView.getStatuses().remove( 1 );

         // ��ȡ�·�
         final List< MappingVO > monthlys = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );
         monthlys.add( 0, payslipDetailView.getEmptyMappingVO() );
         // ��ʼ��StringBuffer
         final StringBuffer rs = new StringBuffer();

         List< MappingVO > itemGroup = new ArrayList< MappingVO >();
         // ��ȡ��Ŀ��������
         List< MappingVO > itemGroupTypes = KANUtil.getMappings( request.getLocale(), "item.group.type" );
         for ( MappingVO mappingVO : itemGroupTypes )
         {
            if ( mappingVO.getMappingId().equals( "0" ) || mappingVO.getMappingId().equals( "2" ) )
            {
               itemGroup.add( mappingVO );
            }
         }
         rs.append( "<li>" );
         rs.append( "<label>��Ŀ��������</label>" );
         rs.append( KANUtil.getSelectHTML( itemGroup, "itemGroupId", "itemGroupId", itemGroupId, null, null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>�˵��·�</label>" );
         rs.append( KANUtil.getSelectHTML( monthlys, "monthly", "monthly", monthly, null, null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>״̬</label>" );
         rs.append( KANUtil.getSelectHTML( payslipDetailView.getStatuses(), "status", "status", status, null, null ) );
         rs.append( "</li>" );

         // Send to client
         out.println( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
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

}
