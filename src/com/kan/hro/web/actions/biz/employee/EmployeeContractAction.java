/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.LocationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.service.inf.management.LaborContractTemplateService;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.tag.AuthConstants;
import com.kan.base.tag.AuthUtils;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.pdf.PDFTool;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPDFVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractPropertyService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSettlementService;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractAction  
* ��������  
* �����ˣ�Jixiang   
*   
*/
// TODO �������������ϵ��Ͷ���ͬ������ȥ
public class EmployeeContractAction extends BaseAction
{
   // �Ͷ���ͬ
   public static String accessActionLabor = "HRO_BIZ_EMPLOYEE_LABOR_CONTRACT";
   // ����Э��
   public static String ACCESS_ACTION_SERVICE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT";
   // �Ͷ���ͬ��In House��
   public static String ACCESS_ACTION_SERVICE_IN_HOUSE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE";

   // ��ǰAction��Ӧ��Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         return ACCESS_ACTION_SERVICE_IN_HOUSE;
      }
      else
      {
         return ACCESS_ACTION_SERVICE;
      }
   }

   /**  
    * Get Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward get_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null && employeeContractVO.getAccountId() != null && employeeContractVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE )
                  || ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) && employeeContractVO.getCorpId() != null && employeeContractVO.getCorpId().equals( getCorpId( request, response ) ) ) )
            {
               employeeContractVO.reset( mapping, request );
               jsonObject = JSONObject.fromObject( employeeContractVO );
               jsonObject.put( "success", "true" );
            }
            else
            {
               jsonObject.put( "success", "false" );
            }
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to client
         out.println( jsonObject != null ? jsonObject.toString() : "" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   /**  
    * Get Object Ajax Popup
    * ģ̬���޸ĺ�ͬʱ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );
         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         // ��ѯEmployeeContractVO ��Ӧ���籣���̱�����
         final List< Object > allEmployeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );
         final List< Object > allEmployeeContractCBVOs = employeeContractCBService.getEmployeeContractCBVOsByContractId( contractId );
         // ��ʼ���籣���̱���������
         List< Object > employeeContractSBVOs = new ArrayList< Object >();
         List< Object > employeeContractCBVOs = new ArrayList< Object >();

         // Reset EmployeeContractVO
         employeeContractVO.reset( mapping, request );
         employeeContractVO.getEmployStatuses().remove( 0 );

         // Reset List(2:���걨�ӱ���3:�������� ״̬ǰ����ʾ)
         if ( allEmployeeContractSBVOs != null && allEmployeeContractSBVOs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : allEmployeeContractSBVOs )
            {
               EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;

               if ( employeeContractSBVO.getStatus().equals( "2" ) || employeeContractSBVO.getStatus().equals( "3" ) )
               {
                  employeeContractSBVO.reset( mapping, request );
                  employeeContractSBVOs.add( employeeContractSBVO );
               }

            }
         }

         // Reset List(2:���깺��3:�������� ״̬ǰ����ʾ)
         if ( allEmployeeContractCBVOs != null && allEmployeeContractCBVOs.size() > 0 )
         {
            for ( Object employeeContractCBVOObject : allEmployeeContractCBVOs )
            {
               EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObject;
               if ( employeeContractCBVO.getStatus().equals( "2" ) || employeeContractCBVO.getStatus().equals( "3" ) )
               {
                  employeeContractCBVO.reset( mapping, request );
                  employeeContractCBVOs.add( employeeContractCBVO );
               }
            }
         }

         final String defineMinEndDate = KANUtil.formatDate( KANUtil.getDate( new Date(), 0, -2 ), null );
         final String defineMaxEndDate = KANUtil.formatDate( KANUtil.getDate( employeeContractVO.getEndDate(), 0, +1 ), null );

         // ����ֵ
         request.setAttribute( "role", getRole( request, response ) );
         request.setAttribute( "defineMinEndDate", defineMinEndDate );
         request.setAttribute( "defineMaxEndDate", defineMaxEndDate );
         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "employeeContractSBVOs", employeeContractSBVOs );
         request.setAttribute( "employeeContractCBVOs", employeeContractCBVOs );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "handleEmployeeContractPopup" );
   }

   /**
    * List Employee Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final String accessAction = getAccessAction( request, response );

         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ���Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // HR_Service��¼��IN_House��¼
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //            if ( isHRFunction( request, response ) )
            //            {
            //               setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractVO );
            //            }
            //            else
            //            {
            //               employeeContractVO.setEmployeeId( getEmployeeId( request, response ) );
            //            }
            setDataAuth( request, response, employeeContractVO );
         }
         // Ա����¼
         else if ( KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {
            employeeContractVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
            employeeContractVO.setStatus( "3" );
         }

         // ����OrderId��������
         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "status,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }
         employeeContractVO.setRemark1( generateDefineListSearches( request, getAccessAction( request, response ) ) );
         // SubAction����
         dealSubAction( employeeContractVO, mapping, form, request, response );

         employeeContractVO.setFlag( "2" );
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            employeeContractVO.setClientId( BaseAction.getClientId( request, response ) );
         }
         request.setAttribute( "flag", "2" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ����Object
            pagedListHolder.setObject( employeeContractVO );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            employeeContractService.getEmployeeContractVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         // �����In House��¼
         if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            return dealReturn( getAccessAction( request, response ), "listEmployeeContractInHouse", mapping, form, request, response );
         }
         else
         {
            return dealReturn( getAccessAction( request, response ), "listEmployeeContract", mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Employee Contract by Order
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-14
   public ActionForward list_object_order( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ���Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // SubAction����
         dealSubAction( employeeContractVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder
         final PagedListHolder serviceContractHolder = new PagedListHolder();

         // ���OrderId
         String orderId = KANUtil.decodeString( request.getParameter( "orderId" ) );
         if ( KANUtil.filterEmpty( orderId ) == null )
         {
            orderId = employeeContractVO.getOrderId();
         }

         if ( KANUtil.filterEmpty( orderId ) != null )
         {
            employeeContractVO.setOrderId( orderId );
            employeeContractVO.setFlag( "2" );

            if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().trim().isEmpty() )
            {
               employeeContractVO.setSortColumn( "contractId" );
               employeeContractVO.setSortOrder( "desc" );
            }

            // ���뵱ǰֵ����
            serviceContractHolder.setObject( employeeContractVO );
            // ���뵱ǰҳ
            serviceContractHolder.setPage( page );
            // ����ҳ���¼����
            serviceContractHolder.setPageSize( this.listPageSize );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            employeeContractService.getEmployeeContractVOsByCondition( serviceContractHolder, true );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( serviceContractHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "serviceContractHolder", serviceContractHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listServiceContractTable" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��EmployeeContractVO
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

      // ��ʼ��EmployeeId�� OrderId��Flag
      final String employeeId = employeeContractVO.getEmployeeId();
      final String orderId = employeeContractVO.getOrderId();
      final String flag = employeeContractVO.getFlag();

      // ���Form
      employeeContractVO.reset();

      // ���EmployeeId��Ϊ�գ�����
      if ( KANUtil.filterEmpty( employeeId ) != null )
      {
         employeeContractVO.setEmployeeId( KANUtil.decodeString( employeeId ) );
      }

      // ����ʱ�䣨Ԥ�ƣ���Ĭ�ϵ�ǰ��ʼ����
      //final Date tempEndDate = KANUtil.getDate( new Date(), 3, 0, -1 );
      final Date tempProbationEndDate = KANUtil.getDate( new Date(), 0, 3, 0 );

      // Ĭ�Ͽ�ʼʱ��ͽ���ʱ��
      employeeContractVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
      //employeeContractVO.setEndDate( KANUtil.formatDate( tempEndDate, "yyyy-MM-dd" ) );
      employeeContractVO.setProbationEndDate( KANUtil.formatDate( tempProbationEndDate, "yyyy-MM-dd" ) );

      // ���OrderId��Ϊ�գ�����
      if ( KANUtil.filterEmpty( orderId ) != null )
      {
         employeeContractVO.setOrderId( KANUtil.decodeString( orderId ) );

         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getOrderHeaderId() ) != null && clientOrderHeaderVO.getContractPeriod() != null )
         {
            employeeContractVO.setEndDate( KANUtil.formatDate( KANUtil.getDate( new Date(), Integer.valueOf( clientOrderHeaderVO.getContractPeriod() ), 0, -1 ), "yyyy-MM-dd" ) );
         }
         //�����ڽ�������
         if ( clientOrderHeaderVO != null && StringUtils.isNotBlank( clientOrderHeaderVO.getOrderHeaderId() ) && StringUtils.isNotBlank( clientOrderHeaderVO.getProbationMonth() ) )
         {
            employeeContractVO.setProbationEndDate( KANUtil.formatDate( KANUtil.getDate( new Date(), 0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" ) );
         }
         // ��ͬ����ʱ�䲻����������
         /*if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getOrderHeaderId() ) != null
               && KANUtil.getDays( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ) ) < KANUtil.getDays( tempEndDate ) )
         {
            employeeContractVO.setEndDate( KANUtil.formatDate( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ), "yyyy-MM-dd" ) );
         }*/
         employeeContractVO.setCurrency( clientOrderHeaderVO.getCurrency() );
      }

      employeeContractVO.setSubAction( CREATE_OBJECT );
      employeeContractVO.setEmployStatus( "1" );
      employeeContractVO.setLocked( "2" );
      employeeContractVO.setFlag( flag );
      employeeContractVO.setStatus( "1" );
      employeeContractVO.setRemark3( "1" );

      // ��ʼ��PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      if ( positionVO != null )
      {
         employeeContractVO.setBranch( positionVO.getBranchId() );
         employeeContractVO.setOwner( positionVO.getPositionId() );
      }

      // �����INHOUSE
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         return mapping.findForward( "manageEmployeeContractInHouse" );
      }
      else
      {
         // �Ͷ���ͬ
         if ( flag != null && flag.trim().equals( "1" ) )
         {
            return mapping.findForward( "manageEmployeeContract" );
         }
         // ����Э��
         else
         {
            return mapping.findForward( "manageEmployeeContractSEV" );
         }
      }
   }

   /**
    * Add Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   // Modify BY Jixiang Hu 2013-11-26
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkEmployeeId( mapping, form, request, response );
            checkOrderId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ��Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // ���ActionForm
            final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
            employeeContractVO.setCreateBy( getUserId( request, response ) );
            employeeContractVO.setModifyBy( getUserId( request, response ) );

            // �����Inhouse��������Ҫ�ֶ�����ClientId
            if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               employeeContractVO.setClientId( getClientId( request, null ) );
            }

            //���ý������Ļ�������
            if ( KANUtil.filterEmpty( employeeContractVO.getCurrency(), "0" ) == null )
            {
               ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getCurrency() != null && !"".equals( clientOrderHeaderVO.getCurrency() ) )
               {
                  employeeContractVO.setCurrency( clientOrderHeaderVO.getCurrency() );
               }
            }

            final String flag = employeeContractVO.getFlag();

            if ( flag == null || flag.trim().isEmpty() )
            {
               //Ĭ������Ͷ���ͬ
               employeeContractVO.setFlag( "1" );
            }

            // InHouse, Flag: 2
            if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               employeeContractVO.setFlag( "2" );
            }

            // ��֤�����Ƿ��ظ�
            if ( employeeContractService.checkContractConflict( employeeContractVO ) )
            {
               if ( KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) || getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  error( request, null, "ʱ������Ѵ����Ͷ���ͬ" );
               }
               else
               {
                  error( request, null, "ʱ������Ѵ���������Ϣ" );
               }
               employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
               employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );

               return to_objectNew( mapping, form, request, response );
            }
            else
            {

               // �����Զ���Column
               employeeContractVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

               if ( employeeContractVO.getTemplateId() != null && !"0".equals( employeeContractVO.getTemplateId() ) )
               {
                  LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );
                  employeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
               }

               employeeContractService.insertEmployeeContract( employeeContractVO );

               // �ж��Ƿ���Ҫת��
               String forwardURL = request.getParameter( "forwardURL" );
               if ( forwardURL != null && !forwardURL.trim().isEmpty() )
               {
                  // ����ת���ַ
                  forwardURL = forwardURL + employeeContractVO.getEncodedId();
                  request.getRequestDispatcher( forwardURL ).forward( request, response );

                  return null;
               }

               // ������ӳɹ����
               success( request, MESSAGE_TYPE_ADD );

               // ������־
               insertAddEmployeeContractVOLog( ( LogService ) getService( "logService" ), employeeContractVO, getIPAddress( request ) );

               // ����ǵ���ύ��ť
               if ( SUBMIT_OBJECT.equalsIgnoreCase( ( ( EmployeeContractVO ) form ).getSubAction() ) )
               {
                  // �ύʱ������ֵ���ܱ�ǰ̨������,���²������޸�״̬����eg:deleted��
                  final EmployeeContractVO tempEmployeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
                  tempEmployeeContractVO.reset( mapping, request );
                  //modify by Jack.sun 20140530
                  tempEmployeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
                  employeeContractService.submitEmployeeContract( tempEmployeeContractVO );
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
            }

            // ����ת���ַ
            String forwardToModify = "employeeContractAction.do?proc=to_objectModify&flag=" + employeeContractVO.getFlag() + "&id=" + employeeContractVO.getEncodedId();
            request.getRequestDispatcher( forwardToModify ).forward( request, response );

            return null;

         }
         else
         {
            final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
            employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
            employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );
            return to_objectNew( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Continue Object
    *	�Ͷ���ͬ��ǩ
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward continue_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( request.getParameter( "contractId" ) ) != null )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // ��ȡContractId
            final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
            // ��ȡEmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            // �޹̶����޵��Ͷ���ͬ������ǩ
            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null )
            {
               final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
               final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               // װ����EmployeeContractVO
               ( ( EmployeeContractVO ) form ).update( employeeContractVO );
               ( ( EmployeeContractVO ) form ).setMasterContractId( employeeContractVO.getContractId() );
               ( ( EmployeeContractVO ) form ).setClientId( employeeContractVO.getClientId() );
               ( ( EmployeeContractVO ) form ).setStartDate( KANUtil.formatDate( KANUtil.getDate( employeeContractVO.getEndDate(), 0, 0, 1 ) ) );
               ( ( EmployeeContractVO ) form ).setEndDate( KANUtil.formatDate( KANUtil.getDate( ( ( EmployeeContractVO ) form ).getStartDate(), 3, 0, -1 ) ) );
               if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getOrderHeaderId() ) != null && clientOrderHeaderVO.getContractPeriod() != null )
               {
                  ( ( EmployeeContractVO ) form ).setEndDate( KANUtil.formatDate( KANUtil.getDate( ( ( EmployeeContractVO ) form ).getStartDate(), Integer.valueOf( clientOrderHeaderVO.getContractPeriod() ), 0, -1 ), "yyyy-MM-dd" ) );
               }
               ( ( EmployeeContractVO ) form ).setPeriod( String.valueOf( KANUtil.getGapMonth( KANUtil.formatDate( employeeContractVO.getStartDate() ), KANUtil.formatDate( employeeContractVO.getEndDate() ) ) ) );
               // ��ǩ�ĺ�ͬĬ��״̬Ϊ���½���
               ( ( EmployeeContractVO ) form ).setStatus( "1" );
               ( ( EmployeeContractVO ) form ).setCreateBy( getUserId( request, response ) );
               ( ( EmployeeContractVO ) form ).setModifyBy( getUserId( request, response ) );
               ( ( EmployeeContractVO ) form ).setCreateDate( new Date() );
               ( ( EmployeeContractVO ) form ).setModifyDate( new Date() );

               // �����Զ���Column
               if ( KANConstants.ROLE_HR_SERVICE.equals( getRole( request, response ) ) )
               {
                  ( ( EmployeeContractVO ) form ).setRemark1( saveDefineColumns( request, ACCESS_ACTION_SERVICE ) );
               }
               else if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
               {
                  ( ( EmployeeContractVO ) form ).setRemark1( saveDefineColumns( request, ACCESS_ACTION_SERVICE_IN_HOUSE ) );
               }

               // �½�����
               if ( employeeContractService.continueEmployeeContract( ( EmployeeContractVO ) form ) > 0 )
               {
                  // ������ӳɹ����
                  success( request, null, "��ǩ�Ͷ���ͬ�ɹ���", MESSAGE_HEADER );
               }
               else
               {
                  error( request, null, "��ǩ�Ͷ���ͬ���ɹ���", MESSAGE_HEADER );
               }
            }
            else
            {
               warning( request, null, "�޹̶����޵��Ͷ���ͬ�����ٱ���ǩ��", MESSAGE_HEADER );
            }
         }
         else
         {
            error( request, null, "��ǩ�Ͷ���ͬ���ɹ���", MESSAGE_HEADER );
            return null;
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contractId == null || contractId.trim().isEmpty() )
         {
            contractId = ( ( EmployeeContractVO ) form ).getContractId();
         }

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "author_new", AuthUtils.hasAuthority( getAccessAction( request, response ), AuthConstants.RIGHT_NEW, employeeContractVO.getOwner(), request, null ) );

         final String flag = employeeContractVO.getFlag();

         if ( flag != null && "2".equals( flag ) && !KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            // ��ת������Э���½�����
            return mapping.findForward( "manageEmployeeContractSEV" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // ��ת�� �Ͷ���ͬ IN HOUSE
         return mapping.findForward( "manageEmployeeContractInHouse" );
      }
      else
      {
         // ��ת���Ͷ���ͬ�½�����
         return mapping.findForward( "manageEmployeeContract" );
      }

   }

   /**
    * Modify Employee Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );

            // ���ҳ������ֵ
            checkEmployeeId( mapping, form, request, response );
            checkOrderId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ��Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // ��õ�ǰ����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ���EmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
            employeeContractVO.setRemark3( ( ( EmployeeContractVO ) form ).getRemark3() );
            employeeContractVO.update( ( EmployeeContractVO ) form );

            //���ý������Ļ�������
            if ( KANUtil.filterEmpty( employeeContractVO.getCurrency(), "0" ) == null )
            {
               ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getCurrency() != null && !"".equals( clientOrderHeaderVO.getCurrency() ) )
               {
                  employeeContractVO.setCurrency( clientOrderHeaderVO.getCurrency() );
               }
            }

            if ( null != employeeContractVO.getTemplateId() && !"0".equals( employeeContractVO.getTemplateId() ) )
            {
               LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );
               employeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
            }

            if ( KANConstants.ROLE_HR_SERVICE.equals( getRole( request, response ) ) )
            {
               employeeContractVO.setClientId( ( ( EmployeeContractVO ) form ).getClientId() );
            }

            employeeContractVO.setModifyBy( getUserId( request, response ) );

            // ��֤�����Ƿ��ظ�
            if ( employeeContractService.checkContractConflict( employeeContractVO ) )
            {
               if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                     || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
               {
                  error( request, null, "ʱ������Ѵ����Ͷ���ͬ" );
               }
               else
               {
                  error( request, null, "ʱ������Ѵ���������Ϣ" );
               }

               employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
               employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );

               employeeContractVO.setSubAction( VIEW_OBJECT );
               employeeContractVO.reset( null, request );
               employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

               request.setAttribute( "employeeContractForm", employeeContractVO );
               request.setAttribute( "author_new", AuthUtils.hasAuthority( getAccessAction( request, response ), AuthConstants.RIGHT_NEW, employeeContractVO.getOwner(), request, null ) );

               return to_prePage( mapping, form, request, response );
            }
            else
            {
               // �����Զ���Column
               employeeContractVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

               // �ж��Ƿ���Ҫת��
               final String forwardURL = request.getParameter( "forwardURL" );

               if ( KANUtil.filterEmpty( forwardURL ) != null )
               {
                  // �޸Ķ���Siuvan & Kevin�ƶ�
                  employeeContractService.updateEmployeeContract( employeeContractVO );
                  ( ( EmployeeContractVO ) form ).reset();

                  // ����ת���ַ
                  request.getRequestDispatcher( forwardURL ).forward( request, response );

                  return null;
               }
            }

            // �ύ
            if ( KANUtil.filterEmpty( getSubAction( form ) ) != null && KANUtil.filterEmpty( getSubAction( form ) ).equals( SUBMIT_OBJECT ) )
            {
               employeeContractVO.reset( mapping, request );

               if ( employeeContractVO.getWorkflowId() == null )
               {
                  employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
                  String accountId = getAccountId( request, response );
                  KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
                  List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );
                  List< Object > objects = getEmployeeContractPDFVos( employeeContractVO, accountConstants, request );
                  final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( employeeContractVO.getContent(), constantVO, request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );
                  employeeContractVO.setConstantVOs( constantVOs );
                  employeeContractService.submitEmployeeContract( employeeContractVO );
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, employeeContractVO, Operate.SUBMIT, employeeContractVO.getContractId(), null );
               }
               else
               {
                  warning( request, MESSAGE_TYPE_SUBMIT, "�Ѿ����ڹ������벻Ҫ�ظ��ύ��" );
               }
            }
            // ������޸�
            else if ( KANUtil.filterEmpty( getSubAction( form ) ) != null && KANUtil.filterEmpty( getSubAction( form ) ).equals( MODIFY_OBJECT ) )
            {
               employeeContractService.updateEmployeeContract( employeeContractVO );
               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractVO, Operate.MODIFY, employeeContractVO.getContractId(), null );
            }
         }

         // ���Form
         ( ( EmployeeContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Popup
    * ģ̬����ƹ�Ա��ְ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Modify by siuvan.xia @2014-06-27
   public ActionForward modify_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
            final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

            // ��õ�ǰ����
            final String contractId = request.getParameter( "contractId" );
            final String resignDate = request.getParameter( "resignDate" );
            final String lastWorkDate = request.getParameter( "lastWorkDate" );
            final String employStatus = request.getParameter( "employStatus" );
            final String leaveReasons = request.getParameter( "leaveReasons" );
            final String[] solutionId_sbs = request.getParameterValues( "solutionId_sb" );
            final String[] endDate_sb = request.getParameterValues( "endDate_sb" );
            final String[] solutionId_cbs = request.getParameterValues( "solutionId_cb" );
            final String[] endDate_cb = request.getParameterValues( "endDate_cb" );

            final String payment = request.getParameter( "payment" );
            final String hireAgain = request.getParameter( "hireAgain" );
            final String remark5 = request.getParameter( "remark5" );//HR Comments

            final String delete = request.getParameter( "delete" );

            // ��ʼ���Ͷ���ͬ�������籣�������̱����� �޸ļ�¼��
            int employeeContractCount = 0;
            int employeeContractSBCount = 0;
            int employeeContractCBCount = 0;

            // ��ȡEmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            // ��ְ���ڲ�Ϊ��
            if ( KANUtil.filterEmpty( resignDate ) != null )
            {
               employeeContractVO.reset( null, request );
               employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
               employeeContractVO.setResignDate( resignDate );
               employeeContractVO.setLastWorkDate( lastWorkDate );
               employeeContractVO.setEmployStatus( employStatus );
               employeeContractVO.setLeaveReasons( leaveReasons );
               employeeContractVO.setModifyBy( getUserId( request, response ) );
               employeeContractVO.setModifyDate( new Date() );
               employeeContractVO.setPayment( payment );
               employeeContractVO.setHireAgain( hireAgain );
               employeeContractVO.setRemark5( remark5 );
               employeeContractVO.setIp( getIPAddress( request ) );
               //�����㹤��ͬʱɾ��
               if ( "2".equals( payment ) && "2".equals( delete ) )
               {
                  employeeContractVO.setStatus( "1" );
                  employeeContractVO.setDeleted( delete );
               }
               // ��ְ���
               employeeContractCount = employeeContractService.submitEmployeeContract_leave( employeeContractVO );
            }

            // �����籣�˱�ʱ��
            if ( solutionId_sbs != null && solutionId_sbs.length > 0 )
            {
               for ( int i = 0; i < solutionId_sbs.length; i++ )
               {
                  // ��ȡEmployeeContractSBVO
                  final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( solutionId_sbs[ i ] );

                  if ( employeeContractSBVO != null )
                  {
                     employeeContractSBVO.reset( null, request );
                     final String endDate = endDate_sb[ i ];
                     if ( endDate != null && !endDate.trim().isEmpty() )
                     {
                        employeeContractSBVO.setEndDate( endDate );
                        employeeContractSBVO.setModifyBy( getUserId( request, response ) );
                        employeeContractSBVO.setModifyDate( new Date() );

                        employeeContractSBCount = employeeContractSBCount + employeeContractSBService.submitEmployeeContractSB_rollback( employeeContractSBVO );
                     }
                  }
               }
            }

            // �����̱��˱�ʱ��
            if ( solutionId_cbs != null && solutionId_cbs.length > 0 )
            {
               for ( int i = 0; i < solutionId_cbs.length; i++ )
               {
                  // ��ȡEmployeeContractCBVO
                  final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( solutionId_cbs[ i ] );

                  if ( employeeContractCBVO != null )
                  {
                     employeeContractCBVO.reset( null, request );
                     final String endDate = endDate_cb[ i ];
                     if ( endDate != null && !endDate.trim().isEmpty() )
                     {
                        employeeContractCBVO.setEndDate( endDate );
                        employeeContractCBVO.setModifyBy( getUserId( request, response ) );
                        employeeContractCBVO.setModifyDate( new Date() );

                        employeeContractCBCount = employeeContractCBCount + employeeContractCBService.submitEmployeeContractCB_rollback( employeeContractCBVO );
                     }
                  }
               }
            }

            if ( employeeContractCount == 0 && employeeContractSBCount == 0 && employeeContractCBCount == 0 )
            {
               // ������ӳɹ����
               error( request, null, "δѡ����Ч��ְʱ����� �籣/�̱� ����ʱ�䣬�������޸�  ��" );
            }
            else
            {
               final StringBuilder str = new StringBuilder();

               if ( employeeContractCount != 0 )
               {
                  str.append( KANUtil.getProperty( request.getLocale(), "message.prompt.resign.submit.success" ) );
               }

               if ( employeeContractSBCount != 0 || employeeContractCBCount != 0 )
               {
                  str.append( "��" );

                  if ( employeeContractSBCount != 0 )
                  {
                     str.append( Math.abs( employeeContractSBCount ) + "���籣�˱�" + ( employeeContractSBCount < 0 ? "�ύ" : "" ) + "�ɹ�" );
                  }

                  if ( employeeContractCBCount != 0 )
                  {
                     str.append( "��" + Math.abs( employeeContractCBCount ) + "���̱��˱�" + ( employeeContractSBCount < 0 ? "�ύ" : "" ) + "�ɹ�" );
                  }

                  str.append( "��" );
               }
               // ������ӳɹ����
               success( request, null, str.toString() );
               insertlog( request, employeeContractVO, Operate.SUBMIT, employeeContractVO.getContractId(), "��ְ�ύ" );
            }

         }

         // ���Form
         request.setAttribute( "flag", "2" );
         ( ( EmployeeContractVO ) form ).reset();
         ( ( EmployeeContractVO ) form ).setContractId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Renew Employee Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward renew_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String contractId = request.getParameter( "contractId" );

         // ���������Ӧδ�޸�ǰ����
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         // �޸Ķ�������
         employeeContractVO.update( ( EmployeeContractVO ) form );
         employeeContractVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         employeeContractService.updateEmployeeContract( employeeContractVO );
         // ���ر༭�ɹ����
         success( request, null, "���ڳɹ���" );
         insertlog( request, employeeContractVO, Operate.MODIFY, employeeContractVO.getContractId(), "renew_object" );
         // ���Form����
         ( ( EmployeeContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Employee Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // ɾ��������Ӧ����
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setContractId( contractId );
         employeeContractVO.setModifyBy( getUserId( request, response ) );
         employeeContractService.deleteEmployeeContract( employeeContractVO );
         insertlog( request, employeeContractVO, Operate.DELETE, employeeContractVO.getContractId(), null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ���Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         if ( employeeContractVO.getSelectedIds() != null && !employeeContractVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeContractVO.setContractId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractVO.setModifyBy( getUserId( request, response ) );
               employeeContractService.deleteEmployeeContract( employeeContractVO );
            }

            insertlog( request, employeeContractVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         employeeContractVO.setSelectedIds( "" );
         employeeContractVO.setSubAction( "" );
         employeeContractVO.setContractId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
   *  List Special Info HTML
   *  
   *  @param mapping
   *  @param form
   *  @param request
   *  @param response
   *  @return
   *  @throws KANException
   */
   // Reviewed by Kevin Jin at 2013-11-16
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��ContractId
         final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

         //  ����н�귽���б�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );

         final List< Object > tempEmployeeContractSalaryVOs = new ArrayList< Object >();
         if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
         {
            for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
               if ( ( KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) == null )
                     || ( KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) != null && KANUtil.getDays( KANUtil.getDateAfterMonth( KANUtil.createDate( employeeContractSalaryVO.getEndDate() ), 3 ) ) >= KANUtil.getDays( new Date() ) ) )
               {
                  employeeContractSalaryVO.reset( null, request );
                  tempEmployeeContractSalaryVOs.add( employeeContractSalaryVO );
               }
            }
         }

         request.setAttribute( "employeeContractSalaryVOs", tempEmployeeContractSalaryVOs );
         request.setAttribute( "numberOfContractSalary", tempEmployeeContractSalaryVOs == null ? 0 : tempEmployeeContractSalaryVOs.size() );

         //  �����籣�����б�
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : employeeContractSBVOs )
            {
               ( ( ActionForm ) employeeContractSBVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractSBVOs", employeeContractSBVOs );
         request.setAttribute( "numberOfContractSB", employeeContractSBVOs == null ? 0 : employeeContractSBVOs.size() );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         //���ظ����б�
         if ( employeeContractVO == null )
         {
            employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setFlag( request.getParameter( "flag" ) );
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeContractVO.reset( null, request );

         // ����Ӧ�깩Ӧ�� ,���û���籣������ο��������������籣����
         final List< String > headerIds = new ArrayList< String >();
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            for ( Object obj : employeeContractSBVOs )
            {
               headerIds.add( ( ( EmployeeContractSBVO ) obj ).getSbSolutionId() );
            }

         }
         else
         {
            // ʹ�ý������������籣����
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );
            for ( Object obj : clientOrderSBVOs )
            {
               headerIds.add( ( ( ClientOrderSBVO ) obj ).getSbSolutionId() );
            }
         }
         final SocialBenefitSolutionHeaderVO condSBSolutionHeaderVO = new SocialBenefitSolutionHeaderVO();
         condSBSolutionHeaderVO.setAccountId( getAccountId( request, response ) );
         condSBSolutionHeaderVO.setCorpId( getCorpId( request, response ) );
         condSBSolutionHeaderVO.setHeaderIds( headerIds );
         // 3 �����籣
         condSBSolutionHeaderVO.setServiceIds( "3" );
         final List< Object > vendorVOs = vendorService.getVendorVOsBySBSolutionHeaderVO( condSBSolutionHeaderVO );
         List< MappingVO > salaryVendors = new ArrayList< MappingVO >();
         // �����ѡ��
         salaryVendors.add( new MappingVO( "0", "1".equals( getRole( request, null ) ) ? ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "�ο���������" : "Quote Order" )
               : ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "���ս������" : "Quote Calculation Rule" ) ) );
         for ( Object vendorObject : vendorVOs )
         {
            final VendorVO tempVendorVO = ( VendorVO ) vendorObject;
            final MappingVO tempMappingVO = new MappingVO();
            tempMappingVO.setMappingId( tempVendorVO.getVendorId() );
            tempMappingVO.setMappingValue( "zh".equalsIgnoreCase( getLocale( request ).getLanguage() ) ? tempVendorVO.getNameZH() : tempVendorVO.getNameEN() );
            salaryVendors.add( tempMappingVO );
         }
         employeeContractVO.setSalaryVendors( salaryVendors );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "numberOfContractAttachment", employeeContractVO.getAttachmentArray() != null ? employeeContractVO.getAttachmentArray().length : 0 );

         //  �������̱����б�
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final List< Object > employeeContractCBVOs = employeeContractCBService.getEmployeeContractCBVOsByContractId( contractId );

         if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
         {
            for ( Object employeeContractCBVOObject : employeeContractCBVOs )
            {
               ( ( ActionForm ) employeeContractCBVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractCBVOs", employeeContractCBVOs );
         request.setAttribute( "numberOfContractCB", employeeContractCBVOs == null ? 0 : employeeContractCBVOs.size() );

         //  �����ݼٷ����б�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         final List< Object > employeeContractLeaveVOs = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( contractId );

         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
            {
               ( ( ActionForm ) employeeContractLeaveVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractLeaveVOs", employeeContractLeaveVOs );
         request.setAttribute( "numberOfContractLeave", employeeContractLeaveVOs == null ? 0 : employeeContractLeaveVOs.size() );

         //  ���ؼӰ෽���б�
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTVOsByContractId( contractId );

         if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
         {
            for ( Object employeeContractOTVOObject : employeeContractOTVOs )
            {
               ( ( ActionForm ) employeeContractOTVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractOTVOs", employeeContractOTVOs );
         request.setAttribute( "numberOfContractOT", employeeContractOTVOs == null ? 0 : employeeContractOTVOs.size() );

         // ���ع�Ա���������б�
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
         final List< Object > employeeContractOtherVOs = employeeContractOtherService.getEmployeeContractOtherVOsByContractId( contractId );

         if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
         {
            for ( Object employeeContractOtherVOObject : employeeContractOtherVOs )
            {
               ( ( ActionForm ) employeeContractOtherVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractOtherVOs", employeeContractOtherVOs );
         request.setAttribute( "numberOfContractOther", employeeContractOtherVOs == null ? 0 : employeeContractOtherVOs.size() );

         // ���ع�Ա�ɱ�
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );
         final List< Object > employeeContractSettlementVOs = employeeContractSettlementService.getEmployeeContractSettlementVOsByContractId( contractId );

         if ( employeeContractSettlementVOs != null && employeeContractSettlementVOs.size() > 0 )
         {
            for ( Object employeeContractSettlementVOObject : employeeContractSettlementVOs )
            {
               ( ( ActionForm ) employeeContractSettlementVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractSettlementVOs", employeeContractSettlementVOs );
         request.setAttribute( "numberOfContractSettlement", employeeContractSettlementVOs == null ? 0 : employeeContractSettlementVOs.size() );

         // ��������������б�
         final String comeFrom = request.getParameter( "comeFrom" );

         if ( "workflow".equals( comeFrom ) )
         {
            final HistoryService historyService = ( HistoryService ) getService( "historyService" );
            final String historyId = request.getParameter( "historyId" );
            final String type = request.getParameter( "type" );
            final String tabIndex = request.getParameter( "tabIndex" );

            final HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

            if ( historyVO != null )
            {
               final EmployeeContractVO passObject = ( EmployeeContractVO ) JSONObject.toBean( JSONObject.fromObject( historyVO.getPassObject() ), EmployeeContractVO.class );
               final EmployeeContractVO originalObject = ( EmployeeContractVO ) JSONObject.toBean( JSONObject.fromObject( historyVO.getFailObject() ), EmployeeContractVO.class );
               passObject.reset( null, request );
               originalObject.reset( null, request );
               request.setAttribute( "passObject", passObject );
               request.setAttribute( "originalObject", originalObject );
            }

            request.setAttribute( "type", type );
            request.setAttribute( "tabIndex", tabIndex );
            return mapping.findForward( "workflowEmployeeContractSpecialInfo" );
         }

         //���ص�н��Ϣ
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         EmployeeSalaryAdjustmentVO salaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
         salaryAdjustmentVO.setLocale( getLocale( request ) );
         salaryAdjustmentVO.setAccountId( getAccountId( request, response ) );
         salaryAdjustmentVO.setClientId( getClientId( request, response ) );
         salaryAdjustmentVO.setCorpId( getCorpId( request, response ) );
         salaryAdjustmentVO.setContractId( contractId );
         salaryAdjustmentVO.setStatus( "5" );

         final PagedListHolder salaryAdjustmentHolder = new PagedListHolder();
         // ���뵱ǰֵ����
         salaryAdjustmentHolder.setObject( salaryAdjustmentVO );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeSalaryAdjustmentService.getSalaryAdjustmentVOsByCondition( salaryAdjustmentHolder, false );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( salaryAdjustmentHolder, request );
         // Holder��д��Request����
         request.setAttribute( "salaryAdjustmentHolder", salaryAdjustmentHolder );

         return mapping.findForward( "manageEmployeeContractSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * list_object_options_ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-25
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ������ѡ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ʼ��Service�ӿ�                                                                                                                      
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡContractId
         final String flag = request.getParameter( "flag" );

         // ��ȡContractId
         String contractId = request.getParameter( "contractId" );

         // ��ȡClientId
         String clientId = request.getParameter( "clientId" );

         // ��ȡEmployeeId
         final String employeeId = request.getParameter( "employeeId" );

         // ��ʼ��EmployeeContractVO�����ڲ�ѯ
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setClientId( clientId );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setEmployeeId( employeeId );
         employeeContractVO.setFlag( flag );
         employeeContractVO.setOrderAttendanceGenerate( "3" );
         employeeContractVO.setStatus( "3, 5, 6, 7" );
         final List< Object > employeeContractVOs = employeeContractService.getEmployeeContractVOsByCondition( employeeContractVO );

         if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
         {
            // ����
            for ( Object employeeContractVOObject : employeeContractVOs )
            {
               final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempEmployeeContractVO.getContractId() );

               // ��������Ļ���
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameZH() );
               }
               // �����Ӣ�Ļ���
               else
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameEN() );
               }

               mappingVOs.add( mappingVO );
            }

            // ���ֻ��һ��ѡ����Ĭ�ϱ�ѡ��
            if ( contractId != null && contractId.trim().isEmpty() && employeeContractVOs.size() == 1 )
            {
               contractId = ( ( EmployeeContractVO ) employeeContractVOs.get( 0 ) ).getContractId();
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "contractId", contractId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   /**  
    * Generate Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-29
   public ActionForward generate_contract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ������ֵ������
         request.setAttribute( "errorCount", 0 );
         // ���ҳ������ֵ
         checkEmployeeId( mapping, form, request, response );
         checkOrderId( mapping, form, request, response );

         // ҳ����ת����
         if ( request.getAttribute( "errorCount" ) != null && Integer.valueOf( request.getAttribute( "errorCount" ).toString() ) > 0 )
         {
            return to_prePage( mapping, form, request, response );
         }

         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ�� Service�ӿ�
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         //         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         //         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         //         final ClientService clientService = ( ClientService ) getService( "clientService" );
         //         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         //         final LocationService locationService = ( LocationService ) getService( "secLocationService" );

         // ��ȡ�Ͷ���ͬ����
         final String employeeContractId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ��ȡAction Form
         EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // ��ʼ��Temp EmployeeContractVO
         EmployeeContractVO tempEmployeeContractVO = null;
         LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );

         if ( KANUtil.filterEmpty( employeeContractId ) != null )
         {
            tempEmployeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractId );
         }
         // �������
         if ( tempEmployeeContractVO == null )
         {
            // ��֤�����Ƿ��ظ�
            if ( employeeContractService.checkContractConflict( employeeContractVO ) )
            {
               if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                     || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
               {
                  error( request, null, "ʱ������Ѵ����Ͷ���ͬ��" );
               }
               else
               {
                  error( request, null, "ʱ������Ѵ�����ǲ��Ϣ��" );
               }

               employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
               employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );

               return to_objectNew( mapping, form, request, response );
            }

            employeeContractVO.setCreateBy( getUserId( request, response ) );
            employeeContractVO.setModifyBy( getUserId( request, response ) );

            // �����Inhouse��������Ҫ�ֶ�����ClientId
            if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               employeeContractVO.setClientId( getClientId( request, null ) );
            }
            employeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
            employeeContractService.insertEmployeeContract( employeeContractVO );

            // ���»�ȡ
            employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
         }
         else
         {
            tempEmployeeContractVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            tempEmployeeContractVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );
            // �޸Ķ���
            tempEmployeeContractVO.update( employeeContractVO );
            // ��ʼ��
            tempEmployeeContractVO.reset( mapping, request );
            tempEmployeeContractVO.setConstantVOs( null );
            tempEmployeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
            employeeContractService.updateEmployeeContract( tempEmployeeContractVO );
            employeeContractVO = tempEmployeeContractVO;
         }

         if ( KANUtil.filterEmpty( employeeContractVO.getTemplateId(), new String[] { "0" } ) != null )
         {
            // ��ȡ��ͬģ����Ϣ
            final String content = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() ).getContent();

            // ��ʼ��KANAccountConstants
            final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );

            // ��ʼ��EmployeeContractPDFVO
            List< Object > objects = getEmployeeContractPDFVos( employeeContractVO, accountConstants, request );

            // ����EmployeeContractVO��Content
            // Modified by Jason Ji at 2014-04-11
            final List< ConstantVO > constantVOs = accountConstants.getConstantVOsByScopeType( "3" );
            //            employeeContractService.updateEmployeeContract( employeeContractVO, constantVOs);
            employeeContractVO.setContent( MatchUtil.generateContent( content, constantVOs, objects, request ) );
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeContractVO.reset( mapping, request );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeContractForm", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "generateContract" );
   }

   /**  
    * Export Contract PDF
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward export_contract_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractPropertyService employeeContractPropertyService = ( EmployeeContractPropertyService ) getService( "employeeContractPropertyService" );

         // ��ȡ�����ͬ����
         final String employeeContractId = KANUtil.decodeString( request.getParameter( "id" ) );
         // ��ȡ�����ͬ����
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractId );

         // ��ʼ���ļ���
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = employeeContractVO.getNameZH() + fileName;
         }
         else
         {
            fileName = employeeContractVO.getNameEN() + fileName;
         }

         // �����Ͷ���ͬ�����Э��PDF�汾
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< ConstantVO > constantVOList = accountConstants.getConstantVOsByScopeType( "3" );
         List< Object > contractIdList = employeeContractPropertyService.getEmployeeContractPropertyVOsByContractId( employeeContractVO.getContractId() );
         String pageContent = employeeContractVO.getContent();
         String logoFile = "";
         final EntityVO entityVO = accountConstants.getEntityVOByEntityId( employeeContractVO.getEntityId() );
         if ( entityVO != null )
         {
            logoFile = entityVO.getLogoFile();
            if ( StringUtils.contains( logoFile, "##" ) )
            {
               logoFile = logoFile.split( "##" )[ 0 ];
            }
            //            if ( KANUtil.filterEmpty( logoFile ) != null )
            //            {
            //               logoFile = accountConstants.initClientLogoFile( logoFile, "0", entityVO.getCorpId() );
            //            }

            if ( KANUtil.filterEmpty( logoFile ) != null )
            {
               //��ͼƬ������ͼƬд������
               logoFile = PDFTool.smbGet( logoFile, request );
            }
         }
         if ( KANUtil.filterEmpty( logoFile ) == null )
         {
            logoFile = KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) ? accountConstants.getClientLogoFileByCorpId( BaseAction.getCorpId( request, response ) )
                  : accountConstants.OPTIONS_LOGO_FILE;
            logoFile = KANUtil.basePath + File.separatorChar + logoFile;
         }

         String htmlContent = MatchUtil.generateContent( pageContent, constantVOList, contractIdList, request, MatchUtil.FLAG_GET_CONTENT, logoFile );
         //         ByteArrayOutputStream baos = HTMLParseUtil.htmlParsePDF( htmlContent, employeeContractVO.getContractId(), logoFile );

         new DownloadFileAction().download( response, PDFTool.generationPdfDzOrder( htmlContent ), fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "�к�" ) )
         {
            error( request, null, e.getMessage() );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax����
      return mapping.findForward( "" );
   }

   /**
    * Modify Object Step 2
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward modify_object_step2( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkEmployeeId( mapping, form, request, response );
            checkOrderId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ�� Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ��ȡEmployeeContractVO����
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            // ��ȡ��ͬģ����Ϣ
            final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );
            final String content = laborContractTemplateVO == null ? null : laborContractTemplateVO.getContent();

            // װ�ؽ��洫ֵ
            employeeContractVO.setContent( content );
            // ���µ�¼�û����޸�ʱ��
            employeeContractVO.setModifyBy( getUserId( request, response ) );
            employeeContractVO.setModifyDate( new Date() );

            // ��ʼ��ConstantVO List
            //jzy 2014/04/11 modify
            String accountId = getAccountId( request, response );
            KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );
            final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( content, constantVO, request, null, MatchUtil.FLAG_GET_PROPERTIES );

            // ��ʼ��Rows
            int rows = 0;

            // �����޸ķ���
            rows = employeeContractService.updateEmployeeContract( employeeContractVO, constantVOs );

            // ����Ǻ�ͬ�ύ - Ĭ��״̬Ϊ��������
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               employeeContractVO.reset( mapping, request );
               employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
               rows = employeeContractService.submitEmployeeContract( employeeContractVO );
            }

            // ������ʾ��Ϣ
            if ( rows == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }

         // ���Form
         ( ( EmployeeContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Chop Object
    * �ڶ������桰���¡���ť����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
   public ActionForward chop_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡEmployeeContractVO����
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            employeeContractVO.setStatus( "5" );
            employeeContractVO.setModifyBy( getUserId( request, response ) );
            employeeContractVO.setModifyDate( new Date() );

            // �����޸ķ���
            employeeContractService.updateEmployeeContract( employeeContractVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Archive Object
    * �ڶ������桰�鵵����ť����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
   public ActionForward archive_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡEmployeeContractVO����
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            employeeContractVO.setStatus( "6" );
            employeeContractVO.setModifyBy( getUserId( request, response ) );
            employeeContractVO.setModifyDate( new Date() );

            // �����޸ķ���
            employeeContractService.updateEmployeeContract( employeeContractVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * ��֤��Ա��һ���ͻ�����ͬ����ʵ�����Ƿ����ʱ���ظ�������Э��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Modify by Kevin Jin 2014-06-02
   public ActionForward checkContractConflict( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWriter
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ʼ�� EmployeeContractVO
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         if ( !KANConstants.ROLE_HR_SERVICE.equals( getRole( request, null ) ) )
         {
            employeeContractVO.setCorpId( getCorpId( request, null ) );
         }

         // ���û��EntityId �򲻼��ʱ���ͻ��
         if ( employeeContractService.checkContractConflict( employeeContractVO ) )
         {
            out.print( "1" );
         }
         else
         {
            out.print( "0" );
         }

         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**  
    * List Object Ajax
    * ����Э����������ģ̬��
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ���Action Form 
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), getAccessAction( request, response ), employeeContractVO );
         setDataAuth( request, response, employeeContractVO );

         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         employeeContractVO.setFlag( "2" );

         // �����In House��ע��Client ID ֵ
         if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            employeeContractVO.setCorpId( getCorpId( request, response ) );
         }

         // ����
         decodedObject( employeeContractVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder contractHolder = new PagedListHolder();
         // ���뵱ǰҳ
         contractHolder.setPage( page );
         // ���뵱ǰֵ����
         contractHolder.setObject( employeeContractVO );
         // ����ҳ���¼����
         contractHolder.setPageSize( listPageSize_popup );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractService.getEmployeeContractVOsByCondition( contractHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( contractHolder, request );

         // Holder��д��Request����
         request.setAttribute( "contractHolder", contractHolder );

         // �����Ajax����
         if ( new Boolean( getAjax( request ) ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
         }

         // Ajax Table���ã�ֱ�Ӵ���JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Get Object Json
    * ģ̬���ò�ѯ������Ϣ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ʼ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // ע��by siuvan && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null
         if ( employeeContractVO != null && employeeContractVO.getAccountId() != null && employeeContractVO.getAccountId().equals( getAccountId( request, response ) )
               && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) != null )
         {
            employeeContractVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( employeeContractVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to front
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   /**
    * �б����ύ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         // ����ظ��ύ
         if ( employeeContractVO == null )
         {
            return list_object( mapping, form, request, response );
         }
         if ( employeeContractService.checkContractConflict( employeeContractVO ) )
         {
            if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                  || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
            {
               error( request, null, "ʱ������Ѵ����Ͷ���ͬ" );
            }
            else
            {
               error( request, null, "ʱ������Ѵ���������Ϣ" );
            }
            EmployeeContractVO employeeContract = new EmployeeContractVO();
            employeeContract.setAccountId( employeeContractVO.getAccountId() );
            employeeContract.setCorpId( employeeContractVO.getCorpId() );
            return list_object( mapping, employeeContract, request, response );
         }
         employeeContractVO.reset( mapping, request );
         if ( employeeContractVO.getWorkflowId() == null )
         {
            String accountId = getAccountId( request, response );
            KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );
            List< Object > objects = getEmployeeContractPDFVos( employeeContractVO, accountConstants, request );
            final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( employeeContractVO.getContent(), constantVO, request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );

            employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
            employeeContractVO.setConstantVOs( constantVOs );
            employeeContractService.submitEmployeeContract( employeeContractVO );
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, employeeContractVO, Operate.BATCH_SUBMIT, contractId, null );
         }
         else
         {
            warning( request, MESSAGE_TYPE_SUBMIT, "�Ѿ����ڹ������벻Ҫ�ظ��ύ��" );
         }
         final String orderId = employeeContractVO.getOrderId();
         employeeContractVO.reset();
         employeeContractVO.setContractId( "" );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setFlag( "2" );
         employeeContractVO.setEmployeeNameZH( "" );
         employeeContractVO.setEmployeeNameEN( "" );
         employeeContractVO.setClientNameZH( "" );
         employeeContractVO.setClientNameEN( "" );
         employeeContractVO.setClientId( "" );
         employeeContractVO.setCertificateNumber( "" );
         employeeContractVO.setOrderAttendanceGenerate( "" );
         // ���Զ������ύ
         if ( KANUtil.filterEmpty( request.getParameter( "comeFrom" ) ) != null && "order".equals( request.getParameter( "comeFrom" ) ) )
         {
            employeeContractVO.setOrderId( orderId );
            return list_object_order( mapping, employeeContractVO, request, response );
         }
         else
         {
            request.setAttribute( "employeeContractForm", employeeContractVO );
            return list_object( mapping, employeeContractVO, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * �б��������ύ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Add by siuvan.xia @ 2014-07-10
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeContractVO EmployeeContractVO = ( EmployeeContractVO ) form;
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ù�ѡID
         final String contractIds = EmployeeContractVO.getSelectedIds();

         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( contractIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = contractIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            StringBuffer message = new StringBuffer();
            message.append( "���º�ͬ��δ�ɹ��ύ��" );
            boolean haveMessage = false;
            for ( String selectId : selectedIdArray )
            {
               // ���EmployeeContractVO
               final EmployeeContractVO submitObject = employeeContractService.getEmployeeContractVOByContractId( KANUtil.decodeStringFromAjax( selectId ) );
               
               if( "1".equals( submitObject.getStatus() ) || "4".equals( submitObject.getStatus() ) )
               {
                   if ( employeeContractService.checkContractConflict( submitObject ) )
                   {
                      if ( ( KANUtil.filterEmpty( submitObject.getFlag() ) != null && KANUtil.filterEmpty( submitObject.getFlag() ).equals( "1" ) )
                            || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
                      {
                         message.append( submitObject.getContractId() + ",ʱ������Ѵ����Ͷ���ͬ" );
                         haveMessage = true;
                      }
                      break;
                   }
    
                   submitObject.setModifyBy( getUserId( request, response ) );
                   submitObject.setModifyDate( new Date() );
                   submitObject.reset( mapping, request );
                   submitObject.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
    
                   if ( submitObject != null && ( StringUtils.isBlank( submitObject.getTemplateId() ) || "0".equals( submitObject.getTemplateId() ) ) )
                   {
                      message.append( submitObject.getContractId() );
                      message.append( "," );
                      haveMessage = true;
                   }
                   else
                   {
                      if ( submitObject.getWorkflowId() == null )
                      {
                         List< Object > objects = getEmployeeContractPDFVos( submitObject, accountConstants, request );
                         final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( submitObject.getContent(), constantVO, request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );
                         submitObject.setConstantVOs( constantVOs );
                         rows = rows + employeeContractService.submitEmployeeContract( submitObject );
                      }
                   }
                }
            }

            if ( haveMessage )
            {
               warning( request, MESSAGE_TYPE_SUBMIT, message.toString() );
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               final PrintWriter out = response.getWriter();
               //���ر�����������Ҫ��tableList tomcat ���æ�رյ�
               out.println( "<div id='_USER_DEFINE_MSG' class='message warning fadable'  style='display:none;'>" + message.toString() + "</div>" );
            }
            else
            {
               if ( rows < 0 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, form, Operate.BATCH_SUBMIT, null, KANUtil.decodeSelectedIds( contractIds ) );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * To PrePage
    * ҳ��������󷵻��ύҳ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_prePage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ������趨һ���Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Service�ӿ�                                                                                                                      
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( ( ( EmployeeContractVO ) form ).getContractId() );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "author_new", AuthUtils.hasAuthority( getAccessAction( request, response ), AuthConstants.RIGHT_NEW, employeeContractVO.getOwner(), request, null ) );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( ( ( EmployeeContractVO ) form ).getFlag() != null && "2".equals( ( ( EmployeeContractVO ) form ).getFlag() )
            && !KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // ��ת������Э���½�����
         return mapping.findForward( "manageEmployeeContractSEV" );
      }

      // ��ת��Manage����   
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // ��ת�� �Ͷ���ͬ IN HOUSE
         return mapping.findForward( "manageEmployeeContractInHouse" );
      }
      else
      {
         // ��ת���Ͷ���ͬ�½�����
         return mapping.findForward( "manageEmployeeContract" );
      }

   }

   /**  
    * CheckOrderId
    * ��鶩��ID�Ƿ���Ч
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkOrderId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
      // ��ȡForm
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
      // ���OrderId
      final String clientOrderHeaderId = KANUtil.filterEmpty( employeeContractVO.getOrderId() );

      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId );

      if ( clientOrderHeaderVO == null )
      {
         request.setAttribute( "orderIdError", "����ID������Ч��" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   /**  
    * CheckEmployeeId
    * ����ԱID�Ƿ���Ч
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void checkEmployeeId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      // ��ȡForm
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
      // ���EmployeeId
      final String employeeId = KANUtil.filterEmpty( employeeContractVO.getEmployeeId() );

      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

      if ( employeeVO == null )
      {

         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            request.setAttribute( "employeeIdError", "Ա��ID������Ч��" );
         }
         else
         {
            request.setAttribute( "employeeIdError", "��ԱID������Ч��" );
         }

         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   public ActionForward list_object_options_ajax_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ������ѡ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ʼ��Service�ӿ�                                                                                                                      
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡContractId
         final String flag = request.getParameter( "flag" );

         // ��ȡEmployeeId
         final String employeeId = getEmployeeId( request, response );

         // ��ʼ��EmployeeContractVO�����ڲ�ѯ
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setEmployeeId( employeeId );
         employeeContractVO.setFlag( flag );
         employeeContractVO.setStatus( "3, 5, 6" );
         // ����ʱ�䵹��
         employeeContractVO.setSortColumn( "startDate" );
         employeeContractVO.setSortOrder( "desc" );
         final List< Object > employeeContractVOs = employeeContractService.getEmployeeContractVOsByCondition( employeeContractVO );

         if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
         {
            // ����
            for ( Object employeeContractVOObject : employeeContractVOs )
            {
               final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempEmployeeContractVO.getContractId() );

               // ��������Ļ���
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameZH() );
               }
               // �����Ӣ�Ļ���
               else
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameEN() );
               }

               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "contractId", "0" ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   public void setInputValueForPage( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );
      String employeeContractId = request.getParameter( "contractId" );
      final EmployeeContractPropertyService employeeContractPropertyService = ( EmployeeContractPropertyService ) getService( "employeeContractPropertyService" );
      List< Object > list = employeeContractPropertyService.getEmployeeContractPropertyVOsByContractId( KANUtil.decodeString( employeeContractId ) );
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( Object object : list )
      {
         Map< String, String > map = new HashMap< String, String >();
         map.put( "id", ( ( EmployeeContractPropertyVO ) object ).getPropertyName() );
         map.put( "value", ( ( EmployeeContractPropertyVO ) object ).getPropertyValue() );
         listReturn.add( map );
      }
      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }

   private List< Object > getEmployeeContractPDFVos( final EmployeeContractVO employeeContractVO, final KANAccountConstants accountConstants, final HttpServletRequest request )
         throws KANException
   {
      final List< Object > objects = new ArrayList< Object >();
      if ( employeeContractVO != null )
      {
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         // ��ʼ��EntityVO
         final EntityVO entityVO = accountConstants.getEntityVOByEntityId( employeeContractVO.getEntityId() );
         // ��ʼ��EmployeeVO
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         // ��ʼ��EmployeeContractSalaryVO�б�
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( employeeContractVO.getContractId() );
         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
         // ��ʼ��ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( employeeContractVO.getClientId() );
         // ��ʼ��LocationVO
         final LocationVO locationVO = locationService.getLocationVOByLocationId( entityVO.getLocationId() );
         boolean hasProbationUsing = true;
         double baseSalary = 0;
         double probationSalary = 0;
         String probationMonth = "";
         String workAddress = "";
         int index_1 = 0;
         int index_2 = 0;
         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            // ��ʼ��EmployeeContractSalaryVO
            final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
            // �������ʿ�Ŀֻȡһ���ۼ�
            if ( employeeContractSalaryVO.getItemId().equals( "1" ) )
            {
               if ( "1".equals( employeeContractSalaryVO.getProbationUsing() ) )
               {
                  if ( index_1 == 0 )
                  {
                     probationSalary = probationSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
                     index_1++;
                  }
                  hasProbationUsing = false;
               }
               else
               {
                  if ( index_2 == 0 )
                  {
                     baseSalary = baseSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
                     index_2++;
                  }
               }
            }
            // ���ʵ�����Ŀ�ۼ�
            if ( employeeContractSalaryVO.getItemId().equals( "2" ) )
            {
               if ( "1".equals( employeeContractSalaryVO.getProbationUsing() ) )
               {
                  probationSalary = probationSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
                  hasProbationUsing = false;
               }
               else
               {
                  baseSalary = baseSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
               }
            }
         }
         if ( hasProbationUsing )
         {
            probationSalary = baseSalary;
         }
         if ( null != employeeContractVO.getProbationEndDate() )
         {
            //            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            //            {
            probationMonth = employeeContractVO.getProbationEndDate();
            //            }
            //            else
            //            {
            //               probationMonth = employeeContractVO.getProbationMonth() + " month(s)";
            //            }
         }
         else
         {
            if ( "0".equals( clientOrderHeaderVO.getProbationMonth() ) )
            {
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  probationMonth = "��������";
               }
               else
               {
                  probationMonth = "None";
               }
            }
            else
            {
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  probationMonth = clientOrderHeaderVO.getProbationMonth() + "����";
               }
               else
               {
                  probationMonth = clientOrderHeaderVO.getProbationMonth() + " month(s)";
               }
            }
         }
         if ( locationVO != null && getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {
               workAddress = locationVO.getAddressZH();
            }
            else
            {
               workAddress = locationVO.getAddressEN();
            }
         }
         else
         {
            workAddress = clientVO.getAddress();
         }
         final EmployeeContractPDFVO employeeContractPDFVO = new EmployeeContractPDFVO();
         employeeContractPDFVO.setBaseSalary( employeeContractPDFVO.formatNumber( KANUtil.filterEmpty( baseSalary ) ) );
         employeeContractPDFVO.setProbationSalary( employeeContractPDFVO.formatNumber( KANUtil.filterEmpty( probationSalary ) ) );
         employeeContractPDFVO.setProbationMonths( KANUtil.filterEmpty( probationMonth ) );
         employeeContractPDFVO.setWorkAddress( workAddress );
         employeeVO.reset( null, request );
         employeeVO.set_tempPositionIds( employeeVO.getDecode_tempPositionIds() );
         objects.add( employeeContractPDFVO );
         objects.add( employeeVO );
         objects.add( entityVO );
         objects.add( employeeContractVO );
      }
      return objects;
   }

   public ActionForward list_employStatuses_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         mappingVOs.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.work.statuses" ) );
         /*  // ��ȡaccountId
           final String accountId = request.getParameter( "accountId" );

           mappingVOs.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.contract.employStatuses" ) );
           // �������ļ���Ӷ�Ӧ�˻��Ĺ�Ӷ״̬����
           if ( accountId != null )
           {
              // ����� iClick
              if ( ACCOUNT_ID_ICLICK.equals( accountId ) )
              {
                 mappingVOs.clear();
                 mappingVOs.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.contract.iClick.employStatuses" ) );
              }
           }*/

         // ����������
         // Send to client
         final String employStatus = request.getParameter( "employStatus" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "employStatus", KANUtil.filterEmpty( employStatus ) == null ? "0" : employStatus ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   // Added by siuvan.xia 2015-03-17
   public ActionForward calculate_annual_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��contractId
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // ��ʼ�� Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         int rows = employeeContractService.calculateEmployeeAnnualLeave( employeeContractVO );
         if ( rows > 0 )
         {
            success( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.success" ).replaceAll( "X", String.valueOf( rows ) ) );
         }
         else
         {
            warning( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.failure" ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   // Added by siuvan.xia 2015��07��02��09:48:15
   public ActionForward export_excel( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String excelFileName = "";
         // ��ʼ��excel��
         String baseFile = FileSystemView.getFileSystemView().getHomeDirectory().toString();
         if ( !baseFile.contains( "Desktop" ) )
         {
            excelFileName = FileSystemView.getFileSystemView().getHomeDirectory().toString() + "/Desktop/emp.xlsx";
         }
         else
         {
            excelFileName = FileSystemView.getFileSystemView().getHomeDirectory().toString() + "/emp.xlsx";
         }

         // ����File
         final File file = new File( excelFileName );

         if ( file.exists() )
         {
            String arr[] = null;
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // ��ʼ��������
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );
            // ��ȡ������
            final XSSFSheet sheet = workbook.getSheetAt( 0 );
            // ������
            for ( int i = 0; i < sheet.getPhysicalNumberOfRows(); i++ )
            {
               // ��ȡ���϶˵�Ԫ��
               final XSSFRow row = sheet.getRow( i );

               if ( row != null )
               {
                  if ( i == 0 )
                  {
                     // ������
                     for ( int j = 0; j < row.getPhysicalNumberOfCells(); j++ )
                     {
                        // ��ȡ���е�ֵ
                        final XSSFCell cell = row.getCell( j );

                        if ( arr == null )
                        {
                           arr = new String[ row.getPhysicalNumberOfCells() ];
                        }

                        if ( i == 0 )
                        {
                           arr[ j ] = cell.getStringCellValue();
                        }
                     }
                  }
                  else
                  {
                     final String contractId = row.getCell( 0 ).getStringCellValue();
                     final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

                     if ( employeeContractVO == null )
                     {
                        System.out.println( "contractId��Ч" );
                        continue;
                     }

                     for ( int k = 1; k < arr.length; k++ )
                     {
                        String cellValue = "";
                        if ( row.getCell( k ).getCellType() == Cell.CELL_TYPE_NUMERIC )
                        {
                           cellValue = String.valueOf( row.getCell( k ).getNumericCellValue() );
                        }
                        else if ( row.getCell( k ).getCellType() == Cell.CELL_TYPE_STRING )
                        {
                           cellValue = row.getCell( k ).getStringCellValue();
                        }
                        String p = arr[ k ];
                        if ( p.contains( "_" ) )
                        {
                           String remark1 = employeeContractVO.getRemark1();
                           JSONObject jsonObject = JSONObject.fromObject( remark1 );
                           jsonObject.put( p.split( "_" )[ 1 ], cellValue );

                           employeeContractVO.setRemark1( jsonObject.toString() );
                        }
                        else
                        {
                           KANUtil.setValue( employeeContractVO, arr[ k ], cellValue );
                        }
                     }

                     employeeContractService.updateBaseEmployeeContract( employeeContractVO );
                  }
               }
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   private void insertAddEmployeeContractVOLog( final LogService logService, final EmployeeContractVO employeeContractVO, final String ip ) throws KANException
   {
      final LogVO logVO = new LogVO();
      logVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      logVO.setChangeReason( employeeContractVO.getRemark3() );
      logVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
      logVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
      logVO.setType( String.valueOf( Operate.ADD.getIndex() ) );
      logVO.setModule( EmployeeContractVO.class.getCanonicalName() );
      logVO.setContent( "��ͬ��Ӷʱ�䣺" + employeeContractVO.getStartDate() );
      logVO.setIp( ip );
      logVO.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      logVO.setOperateBy( employeeContractVO.decodeUserId( employeeContractVO.getModifyBy() ) );
      logVO.setpKey( employeeContractVO.getContractId() );
      logVO.setRemark( "�Ͷ���ͬ����" );

      logService.insertLog( logVO );
   }

}
