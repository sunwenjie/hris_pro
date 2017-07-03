package com.kan.hro.web.actions.biz.sb;

import java.io.PrintWriter;
import java.util.Date;
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
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBBillDetailView;
import com.kan.hro.domain.biz.sb.SBBillHeaderView;
import com.kan.hro.service.inf.biz.sb.SBBillViewService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

public class SBBillViewAction extends BaseAction
{

   // ��Ӧjava����
   public final static String JAVA_OBJECT_NAME = "com.kan.hro.domain.biz.sb.SBDTO";

   public final static String accessAction = "JAVA_OBJECT_SBBILL";

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
         final SBBillViewService sbBillViewService = ( SBBillViewService ) getService( "sbBillViewService" );
         // ���Action Form
         final SBBillDetailView sbBillDetailView = ( SBBillDetailView ) form;

         // ����Form
         dealSubAction( sbBillDetailView, mapping, form, request, response );

         //��������Ȩ��
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), SBBillViewAction.accessAction, sbBillDetailView );
            setDataAuth( request, response, sbBillDetailView );
         }

         // ���SubAction
         final String subAction = getSubAction( sbBillDetailView );
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         final String vendorId = request.getParameter( "vendorId" );
         if ( KANUtil.filterEmpty( vendorId, "0" ) != null )
         {
            sbBillDetailView.setVendorId( vendorId );
         }

         if ( KANUtil.filterEmpty( sbBillDetailView.getMonthly() ) == null )
         {
            sbBillDetailView.setMonthly( KANUtil.getMonthly( new Date() ) );
         }

         // �滻ȫ�Ƕ���
         if ( KANUtil.filterEmpty( sbBillDetailView.getBatchId() ) != null )
         {
            sbBillDetailView.setBatchId( sbBillDetailView.getBatchId().replace( "��", "," ) );
         }

         // �����inHouse
         if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // �������HRְ��
            if ( !isHRFunction( request, response ) )
            {
               // �����ˡ��޸���
               sbBillDetailView.setEmployeeId( getEmployeeId( request, response ) );
            }
         }

         if ( sbBillDetailView.getSbStatusArray() != null && sbBillDetailView.getSbStatusArray().length > 0 )
         {
            sbBillDetailView.setSbStatus( KANUtil.toJasonArray( sbBillDetailView.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
            request.setAttribute( "sbStatus", sbBillDetailView.getSbStatus() );
         }

         final SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setPageFlag( "bill" );
         // ��ӹ�Ӧ��������
         sbBatchVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         //��������Ȩ��;
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_EMPLOYEE ) )
         {
            sbBillDetailView.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
            sbBillDetailView.setRole( KANConstants.ROLE_EMPLOYEE );
            //            //�ѷ���
            //            sbBillDetailView.setStatus( "3" );
            //            sbBillDetailView.setFlag( "1" );
         }
         else if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            sbBillDetailView.setClientId( BaseAction.getClientId( request, response ) );
            sbBillDetailView.setRole( KANConstants.ROLE_CLIENT );
            //            sbBillDetailView.setGroupColumn( "2" );
         }

         final PagedListHolder pagedListHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( listDTO.getPageSize() );
            // ���뵱ǰֵ����
            pagedListHolder.setObject( sbBillDetailView );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            sbBillViewService.getSBBillDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );
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

               return null;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSBBillView" );
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

   // ���������HTML
   public void load_special_html( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         final String monthly = request.getParameter( "monthly" );

         final String contractStatus = request.getParameter( "contractStatus" );
         final String employeeSBId = request.getParameter( "employeeSBId" );
         final String sbStatuses = request.getParameter( "sbStatuses" );
         final String status = request.getParameter( "status" );
         final String groupColumn = request.getParameter( "groupColumn" );
         final String sbType = request.getParameter( "sbType" );
         final String flag = request.getParameter( "flag" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ�籣����
         final List< MappingVO > sbs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, null ) );
         if ( sbs != null )
         {
            sbs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // ��ȡ�籣״̬
         final SBBillHeaderView sbBillHeaderView = new SBBillHeaderView();
         sbBillHeaderView.reset( null, request );

         // ��ȡ�·�
         final List< MappingVO > monthlys = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );
         if ( monthlys != null && monthlys.size() > 0 )
         {
            monthlys.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // ��ʼ��StringBuffer
         final StringBuffer rs = new StringBuffer();

         final String contractLabelKey = getRole( request, null ).equals( "1" ) ? "public.contract1.status" : "public.contract2.status";

         if ( !KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, response ) ) )
         {
            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), contractLabelKey ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getContractStatuses(), "contractStatus", "contractStatus", contractStatus, null, null ) );
            rs.append( "</li>" );
         }

         rs.append( "<li>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.monthly" ) + "<img style=\"hight 12px;width :12px;\" src=\"images/tips.png\" title=\""
               + KANUtil.getProperty( request.getLocale(), "sb.bill.month.tips" ) + "\" /></label>" );
         rs.append( KANUtil.getSelectHTML( monthlys, "monthly", "monthly", monthly, null, null ) );
         rs.append( "</li>" );

         if ( !KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {

            if ( !BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
            {
               rs.append( "<li>" );
               rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.payment.status" ) + "</label>" );
               rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getFlags(), "flag", "flag", flag, null, null ) );
               rs.append( "</li>" );
            }

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.type" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getSbTypes(), "sbType", "sbType", sbType, null, null ) );
            rs.append( "</li>" );
            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.solution" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbs, "employeeSBId", "employeeSBId", employeeSBId, null, null ) );
            rs.append( "</li>" );

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "public.status" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getStatuses(), "status", "status", status, null, null ) );
            rs.append( "</li>" );

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.status" ) + "</label>" );
            rs.append( "<div style=\"width: 220px;\">" );
            if ( sbBillHeaderView.getSbStatuses() != null && sbBillHeaderView.getSbStatuses().size() > 0 )
            {
               for ( MappingVO mappingVO : sbBillHeaderView.getSbStatuses() )
               {
                  if ( !mappingVO.getMappingId().equals( "0" ) )
                  {
                     String checked = "";
                     if ( KANUtil.filterEmpty( sbStatuses ) != null )
                     {
                        for ( String sbStatus : sbStatuses.split( "," ) )
                        {
                           if ( sbStatus.equals( mappingVO.getMappingId() ) )
                           {
                              checked = "checked=\"checked\"";
                           }
                        }
                     }
                     rs.append( "<label class=\"auto\">" );
                     rs.append( "<input type=\"checkbox\" name=\"sbStatusArray\" " + checked + " id=\"sbStatus_" + mappingVO.getMappingId() + "\"  class=\"sbStatus_"
                           + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" />" );
                     rs.append( mappingVO.getMappingValue() );
                     rs.append( "</label>" );
                  }
               }
            }

            rs.append( "</div>" );
            rs.append( "</li>" );

            rs.append( "<li " + ( !getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "style=\"display:none;\"" : "" ) + ">" );
            rs.append( "<label>�����ѯ</label>" );
            rs.append( "<select name=\"groupColumn\" id=\"groupColumn\" class=\"groupColumn\">" );
            rs.append( "<option value=\"0\">��ѡ��</option>" );
            rs.append( "<option value=\"1\" "
                  + ( ( KANUtil.filterEmpty( groupColumn ) != null && KANUtil.filterEmpty( groupColumn ).equals( "1" ) ) ? "selected=\"selected\"" : "" ) + ">�ͻ�</option>" );
            rs.append( "<option value=\"2\" "
                  + ( ( KANUtil.filterEmpty( groupColumn ) != null && KANUtil.filterEmpty( groupColumn ).equals( "2" ) ) ? "selected=\"selected\"" : "" ) + ">�籣����</option>" );
            rs.append( "</select>" );
            rs.append( "</li>" );

         }

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
}
