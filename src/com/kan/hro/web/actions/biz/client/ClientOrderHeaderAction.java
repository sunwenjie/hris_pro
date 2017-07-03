package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxBaseService;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderLeaveService;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;
import com.kan.hro.service.inf.biz.client.ClientOrderOtherService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderHeaderAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderHeaderAction extends BaseAction
{

   public static String getAccessActionForWorkFlow()
   {
      return "HRO_BIZ_CLIENT_ORDER_HEADER";
   }

   // ��ǰAction��Ӧ��Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) ) )
      {
         return "HRO_BIZ_CLIENT_ORDER_HEADER";
      }
      else
      {
         return "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE";
      }
   }

   /**
    * Get Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ȡOrderHeaderId
         final String orderHeaderId = request.getParameter( "orderHeaderId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getAccountId() != null && clientOrderHeaderVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientOrderHeaderVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientOrderHeaderVO );
            String finalEndDate = getFinalEndDate( clientOrderHeaderVO );
            jsonObject.put( "finalEndDate", finalEndDate );
            jsonObject.put( "EndDate", clientOrderHeaderVO.getContractPeriod() != null && !"0".equals( clientOrderHeaderVO.getContractPeriod() ) ? KANUtil.formatDate( KANUtil.getDate( new Date(), Integer.valueOf( clientOrderHeaderVO.getContractPeriod() ), 0, -1 ), "yyyy-MM-dd" )
                  : null );

            //�����ڽ�������
            jsonObject.put( "probationEndDate", clientOrderHeaderVO.getProbationMonth() != null && !"0".equals( clientOrderHeaderVO.getProbationMonth() ) ? KANUtil.formatDate( KANUtil.getDate( new Date(), 0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" )
                  : null );
            jsonObject.put( "probationMonth", clientOrderHeaderVO.getProbationMonth() );

            jsonObject.put( "contractPeriod", clientOrderHeaderVO.getContractPeriod() );

            //            if ( StringUtils.isNotBlank( clientOrderHeaderVO.getProbationMonth() ) )
            //            {
            //               jsonObject.put( "probationEndDate",  KANUtil.formatDate( KANUtil.getDate( new Date(),0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" ) );
            ////               employeeContractVO.setProbationEndDate( KANUtil.formatDate( KANUtil.getDate( new Date(),0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" ) );
            //            }
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
    * List Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // ���Action Form 
         final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), getAccessAction( request, response ), clientOrderHeaderVO );

         // ����
         decodedObject( clientOrderHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder orderHolder = new PagedListHolder();

         // ���뵱ǰҳ
         orderHolder.setPage( page );
         // ���뵱ǰֵ����
         orderHolder.setObject( clientOrderHeaderVO );
         // ����ҳ���¼����
         orderHolder.setPageSize( listPageSize_popup );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderHeaderService.getClientOrderHeaderVOsByCondition( orderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( orderHolder, request );
         // Holder��д��Request����
         request.setAttribute( "orderHolder", orderHolder );

         // Ajax Table���ã�ֱ�Ӵ���JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Client Order Header
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String accessAction = getAccessAction( request, response );

      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // ���Action Form
         final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientOrderHeaderVO );

         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ�����������
         clientOrderHeaderVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ���û��ָ��������Ĭ�ϰ�OrderHeaderId����
         if ( clientOrderHeaderVO.getSortColumn() == null || clientOrderHeaderVO.getSortColumn().isEmpty() )
         {
            clientOrderHeaderVO.setSortColumn( "orderHeaderId" );
            clientOrderHeaderVO.setSortOrder( "desc" );
         }

         // ����ǿͻ��˵�¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            clientOrderHeaderVO.setCorpId( getCorpId( request, null ) );
         }

         // ����SubAction
         dealSubAction( clientOrderHeaderVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder clientOrderHeaderHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            clientOrderHeaderHolder.setPage( page );
            // ���뵱ǰֵ����
            clientOrderHeaderHolder.setObject( clientOrderHeaderVO );
            // ����ҳ���¼����
            clientOrderHeaderHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( clientOrderHeaderHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", clientOrderHeaderHolder );

         // ����Return
         if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
         {
            return dealReturn( accessAction, "listClientOrderHeader", mapping, form, request, response );
         }
         else
         {
            return dealReturn( accessAction, "listClientOrderHeaderInHouse", mapping, form, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��Entity Mapping�б�
      final List< MappingVO > entities = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntities( request.getLocale().getLanguage() );

      // ��ʼ��Business Type Mapping�б�
      final List< MappingVO > businessTypes = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBusinessTypes( request.getLocale().getLanguage() );

      // ��ʼ��PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      // ���ActionForm
      final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
      // Ĭ������
      clientOrderHeaderVO.setSubAction( CREATE_OBJECT );

      if ( entities != null && entities.size() == 1 )
      {
         clientOrderHeaderVO.setEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );

         // ��ʼ��
         final EntityVO entityVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );
         clientOrderHeaderVO.setBusinessTypeId( entityVO.getBizType() );
      }

      if ( businessTypes != null && businessTypes.size() == 1 )
      {
         clientOrderHeaderVO.setBusinessTypeId( ( ( MappingVO ) businessTypes.get( 0 ) ).getMappingId() );
      }

      clientOrderHeaderVO.setStartDate( KANUtil.formatDate( KANUtil.createDate( null ), "yyyy-MM-dd" ) );
      //��������Ĭ��Ϊ�����
      clientOrderHeaderVO.setCurrency( "CN" );
      //��ͬ����Ĭ��Ϊ3��
      clientOrderHeaderVO.setContractPeriod( "3" );
      clientOrderHeaderVO.setLocked( ClientOrderHeaderVO.FALSE );
      clientOrderHeaderVO.setStatus( ClientOrderHeaderVO.TRUE );
      if ( positionVO != null )
      {
         clientOrderHeaderVO.setBranch( positionVO.getBranchId() );
         clientOrderHeaderVO.setOwner( positionVO.getPositionId() );
      }

      // �����ͨ�� Client�������
      if ( KANUtil.filterEmpty( request.getParameter( "clientId" ) ) != null )
      {
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // ��ȡClientId
         final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );
         // ��ȡClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         // ���ʻ�
         clientVO.reset( null, request );

         // ����ClientOrderHeaderVO ��ز���
         clientOrderHeaderVO.setClientId( clientId );
         clientOrderHeaderVO.setClientNameZH( clientVO.getNameZH() );
         clientOrderHeaderVO.setClientNameEN( clientVO.getNameEN() );
         clientOrderHeaderVO.setEntityId( clientVO.getLegalEntity() );

         // ��ʼ�������󶨺�ͬ
         boolean orderBindContract = false;
         if ( clientVO.getOrderBindContract() != null && clientVO.getOrderBindContract().trim().equals( "1" ) )
         {
            orderBindContract = true;
         }
         else
         {
            orderBindContract = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_ORDER_BIND_CONTRACT;
         }

         request.setAttribute( "orderBindContract", orderBindContract );
      }

      // �����ͨ�� ClientContract�������
      if ( KANUtil.filterEmpty( request.getParameter( "contractId" ) ) != null )
      {
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // ��ȡContractId
         final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );
         // ���ClientContractVO
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         // ���ʻ�
         clientContractVO.reset( null, request );

         // ���ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientContractVO.getClientId() );

         // ����ClientOrderHeaderVO ��ز���
         clientOrderHeaderVO.setClientId( clientContractVO.getClientId() );
         clientOrderHeaderVO.setClientNameZH( clientVO.getNameZH() );
         clientOrderHeaderVO.setClientNameEN( clientVO.getNameEN() );
         clientOrderHeaderVO.setContractId( contractId );
         clientOrderHeaderVO.setEntityId( clientVO.getLegalEntity() );
         clientOrderHeaderVO.setStartDate( clientContractVO.getStartDate() );
         // ����Ĭ�Ͻ����������ã������ͬ�������ڷǿ���ȡ�����ͬ�������ڣ���֮Ϊ�գ�
         //KANUtil.formatDate( KANUtil.getDate( KANUtil.createDate( null ), 3, 0, -1 ), "yyyy-MM-dd" )
         clientOrderHeaderVO.setEndDate( KANUtil.filterEmpty( clientContractVO.getEndDate() ) != null ? clientContractVO.getEndDate() : null );

         // ��ʼ�������󶨺�ͬ
         boolean orderBindContract = false;
         if ( clientVO.getOrderBindContract() != null && clientVO.getOrderBindContract().trim().equals( "1" ) )
         {
            orderBindContract = true;
         }
         else
         {
            orderBindContract = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_ORDER_BIND_CONTRACT;
         }

         request.setAttribute( "orderBindContract", orderBindContract );
      }

      // ����Ǵӿͻ��˵�¼
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         clientOrderHeaderVO.setCorpId( getCorpId( request, null ) );
      }

      // ��ת���½�����
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeader" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderInHouse" );
      }
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
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��õ�ǰ����
         String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( orderHeaderId == null || orderHeaderId.trim().isEmpty() )
         {
            orderHeaderId = ( ( ClientOrderHeaderVO ) form ).getOrderHeaderId();
         }

         // �����������Ҷ�Ӧ��clientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         if ( clientOrderHeaderVO == null )
         {
            clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
         }

         if ( "0".equals( clientOrderHeaderVO.getContractPeriod() ) )
         {
            clientOrderHeaderVO.setContractPeriod( "3" );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderHeaderVO.reset( null, request );
         clientOrderHeaderVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderHeaderForm", clientOrderHeaderVO );

         // ��ʼ��ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientOrderHeaderVO.getClientId() );

         // ��ʼ�������󶨺�ͬ
         boolean orderBindContract = false;
         if ( clientVO.getOrderBindContract() != null && clientVO.getOrderBindContract().trim().equals( "1" ) )
         {
            orderBindContract = true;
         }
         else
         {
            orderBindContract = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_ORDER_BIND_CONTRACT;
         }

         request.setAttribute( "orderBindContract", orderBindContract );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeader" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderInHouse" );
      }

   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String accessAction = getAccessAction( request, response );
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkClientId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            // ���ActionForm
            final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
            // ��ȡ��¼�û�
            clientOrderHeaderVO.setCreateBy( getUserId( request, response ) );
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            // �������Ŀ
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               clientOrderHeaderVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }
            // �����ͬ��������
            final String[] noticeExpires = request.getParameterValues( "noticeExpire" );
            if ( noticeExpires != null && noticeExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeExpire( KANUtil.toJasonArray( noticeExpires, "," ) );
            }
            // �������õ�������
            final String[] noticeProbationExpires = request.getParameterValues( "noticeProbationExpire" );
            if ( noticeProbationExpires != null && noticeProbationExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeProbationExpire( KANUtil.toJasonArray( noticeProbationExpires, "," ) );
            }
            // �������ݵ�������
            final String[] noticeRetires = request.getParameterValues( "noticeRetire" );
            if ( noticeRetires != null && noticeRetires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeRetire( KANUtil.toJasonArray( noticeRetires, "," ) );
            }
            // �����Զ���Column
            clientOrderHeaderVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // TODO ������Ҫ��������ʱ��ʩ���籣�������ֺ��޸ģ�
            clientOrderHeaderVO.setFundMonth( clientOrderHeaderVO.getSbMonth() );

            // TODO ������Ҫ�Ƴ�����ʱ��ʩ�������¿����в�ͬ�����㷽ʽ��
            clientOrderHeaderVO.setDivideTypeIncomplete( clientOrderHeaderVO.getDivideType() );

            // �½�����
            clientOrderHeaderService.insertClientOrderHeader( clientOrderHeaderVO );

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + clientOrderHeaderVO.getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
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
    * Modify Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      final String accessAction = getAccessAction( request, response );
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkClientId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ��õ�ǰ����
            final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // ��������
            clientOrderHeaderVO.update( ( ClientOrderHeaderVO ) form );
            // �����Զ���Column
            clientOrderHeaderVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �������Ŀ
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               clientOrderHeaderVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            clientOrderHeaderVO.reset( mapping, request );
            // �����ͬ��������
            final String[] noticeExpires = request.getParameterValues( "noticeExpire" );
            if ( noticeExpires != null && noticeExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeExpire( KANUtil.toJasonArray( noticeExpires, "," ) );
            }
            // �������õ�������
            final String[] noticeProbationExpires = request.getParameterValues( "noticeProbationExpire" );
            if ( noticeProbationExpires != null && noticeProbationExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeProbationExpire( KANUtil.toJasonArray( noticeProbationExpires, "," ) );
            }
            // �������ݵ�������
            final String[] noticeRetires = request.getParameterValues( "noticeRetire" );
            if ( noticeRetires != null && noticeRetires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeRetire( KANUtil.toJasonArray( noticeRetires, "," ) );
            }
            // ����籣��������
            final String[] noticeHKSBs = request.getParameterValues( "noticeHKSB" );
            if ( noticeHKSBs != null && noticeHKSBs.length > 0 )
            {
               clientOrderHeaderVO.setNoticeHKSB( KANUtil.toJasonArray( noticeHKSBs, "," ) );
            }

            // �����Զ���Column
            clientOrderHeaderVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // ����Ƕ����ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               clientOrderHeaderVO.reset( mapping, request );
               if ( clientOrderHeaderService.submitClientOrderHeader( clientOrderHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, clientOrderHeaderVO, Operate.SUBMIT, clientOrderHeaderVO.getOrderHeaderId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
               }
            }
            else
            {
               clientOrderHeaderService.updateClientOrderHeader( clientOrderHeaderVO );
               // �ж��Ƿ���Ҫת��
               String forwardURL = request.getParameter( "forwardURL" );
               if ( forwardURL != null && !forwardURL.trim().isEmpty() )
               {
                  // ����ת���ַ
                  request.getRequestDispatcher( forwardURL ).forward( request, response );

                  return null;
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
               }
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Submit Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-12
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��õ�ǰ����
         final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���������Ӧ����
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         // ��������
         clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
         clientOrderHeaderVO.setModifyDate( new Date() );
         clientOrderHeaderVO.reset( null, request );
         clientOrderHeaderService.submitClientOrderHeader( clientOrderHeaderVO );
         success( request, MESSAGE_TYPE_SUBMIT );
         insertlog( request, clientOrderHeaderVO, Operate.SUBMIT, clientOrderHeaderVO.getOrderHeaderId(), null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Cancel Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-12
   public ActionForward cancel_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // ��õ�ǰ����
            final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���������Ӧ����
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // ��������
            clientOrderHeaderVO.setStatus( "8" );
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            clientOrderHeaderVO.setModifyDate( new Date() );

            clientOrderHeaderService.updateClientOrderHeader( clientOrderHeaderVO );
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Stop Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-12
   public ActionForward stop_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // ��õ�ǰ����
            final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���������Ӧ����
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // ��������
            clientOrderHeaderVO.setStatus( "7" );
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            clientOrderHeaderVO.setModifyDate( new Date() );

            clientOrderHeaderService.updateClientOrderHeader( clientOrderHeaderVO );
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**
    * Delete Object List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // ���Action Form
         ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderHeaderVO.getSelectedIds() != null && !clientOrderHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderHeaderId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderHeaderVO clientOrderHeaderVOForDel = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId );
                  clientOrderHeaderVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderHeaderVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderHeaderService.deleteClientOrderHeader( clientOrderHeaderVOForDel );
               }
            }

            insertlog( request, clientOrderHeaderVO, Operate.MODIFY, null, KANUtil.decodeSelectedIds( clientOrderHeaderVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         clientOrderHeaderVO.setSelectedIds( "" );
         clientOrderHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tabɾ������
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Row
         long rows = 0;

         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ȡ����
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );

         // ��ȡClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
         clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
         clientOrderHeaderVO.setModifyDate( new Date() );

         // ����״̬����ɾ��
         if ( clientOrderHeaderVO.getStatus() != null
               && ( clientOrderHeaderVO.getStatus().trim().equals( "3" ) || clientOrderHeaderVO.getStatus().trim().equals( "5" )
                     || clientOrderHeaderVO.getStatus().trim().equals( "6" ) || clientOrderHeaderVO.getStatus().trim().equals( "7" ) ) )
         {
            rows = 0;
         }
         else
         {
            // ����ɾ���ӿ�
            rows = clientOrderHeaderService.deleteClientOrderHeader( clientOrderHeaderVO );
         }

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Copy Object
    * ���� ����/����Э��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward copy_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( request.getParameter( "orderHeaderId" ) ) != null )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            // ��ȡ���ݿ���ԭ�ж���
            final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            ( ( ClientOrderHeaderVO ) form ).setOrderHeaderId( orderHeaderId );
            ( ( ClientOrderHeaderVO ) form ).update( clientOrderHeaderVO );
            ( ( ClientOrderHeaderVO ) form ).setClientId( clientOrderHeaderVO.getClientId() );
            // ��״̬��Ϊ�½�
            ( ( ClientOrderHeaderVO ) form ).setStatus( "1" );
            // ��ȡ��¼�û�
            ( ( ClientOrderHeaderVO ) form ).setCreateBy( getUserId( request, response ) );
            ( ( ClientOrderHeaderVO ) form ).setModifyBy( getUserId( request, response ) );
            ( ( ClientOrderHeaderVO ) form ).setCreateDate( new Date() );
            ( ( ClientOrderHeaderVO ) form ).setModifyDate( new Date() );

            // �����Զ���Column
            ( ( ClientOrderHeaderVO ) form ).setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

            // �½�����
            if ( clientOrderHeaderService.copyClientOrderHeader( ( ClientOrderHeaderVO ) form ) > 0 )
            {
               // ������ӳɹ����
               success( request, null, "������Ϣ�ɹ���", MESSAGE_HEADER );
            }
            else
            {
               error( request, null, "������Ϣ���ɹ���", MESSAGE_HEADER );
            }

         }
         else
         {
            error( request, null, "������Ϣ���ɹ���", MESSAGE_HEADER );
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

   // Reviewed by Kevin Jin at 2013-11-04
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��OrderHeaderId
         final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

         // ��ʼ��ClientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         if ( orderHeaderId != null && !orderHeaderId.trim().isEmpty() )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // ��ȡClientOrderHeaderVO
            clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // ˢ�¶��󣬳�ʼ�������б����ʻ�
            if ( clientOrderHeaderVO != null )
            {
               clientOrderHeaderVO.reset( null, request );
            }
         }
         else
         {
            // �Ӱ�Tab��ʼ������
            clientOrderHeaderVO.setApplyOTFirst( "1" );
            clientOrderHeaderVO.setOtLimitByDay( "0" );
            clientOrderHeaderVO.setOtLimitByMonth( "0" );
            clientOrderHeaderVO.setWorkdayOTItemId( "21" );
            clientOrderHeaderVO.setWeekendOTItemId( "22" );
            clientOrderHeaderVO.setHolidayOTItemId( "23" );

            // ����Tab��ʼ������
            clientOrderHeaderVO.setInvoiceType( "2" );
            clientOrderHeaderVO.setSalaryMonth( "1" );
            clientOrderHeaderVO.setSbMonth( "1" );
            clientOrderHeaderVO.setCbMonth( "1" );
            clientOrderHeaderVO.setPersonalSBBurden( "2" );

            // н��Tab��ʼ������
            clientOrderHeaderVO.setCircleStartDay( "1" );
            clientOrderHeaderVO.setCircleEndDay( "31" );
            clientOrderHeaderVO.setAttendanceCheckType( "2" );
            clientOrderHeaderVO.setAttendanceGenerate( "1" );
            clientOrderHeaderVO.setCalendarId( "1" );
            clientOrderHeaderVO.setShiftId( "1" );
            clientOrderHeaderVO.setApproveType( "2" );
            clientOrderHeaderVO.setSalaryType( "1" );
            clientOrderHeaderVO.setDivideType( "2" );

            // ���clientOrderHeaderVO�ĸ�˰��������ֵ
            if ( KANUtil.filterEmpty( clientOrderHeaderVO.getIncomeTaxBaseId() ) == null )
            {
               // ����clientOrderHeaderVO��Ĭ�ϸ�˰������
               if ( clientOrderHeaderVO.getIncomeTaxBases() != null && clientOrderHeaderVO.getIncomeTaxBases().size() > 0 )
               {
                  // ���ֻ��һ����¼
                  if ( clientOrderHeaderVO.getIncomeTaxBases().size() == 2 )
                  {
                     clientOrderHeaderVO.setIncomeTaxBaseId( clientOrderHeaderVO.getIncomeTaxBases().get( 1 ).getMappingId() );
                  }
                  //  ������¼ȡ��һ������Ϊ"Ĭ��"�ļ�¼
                  else
                  {
                     for ( MappingVO incometaxBaseMappingVO : clientOrderHeaderVO.getIncomeTaxBases() )
                     {
                        // ��ʼ��Service�ӿ�
                        final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
                        final IncomeTaxBaseVO incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( incometaxBaseMappingVO.getMappingId() );

                        if ( incomeTaxBaseVO != null && incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equals( "1" ) )
                        {
                           clientOrderHeaderVO.setIncomeTaxBaseId( incometaxBaseMappingVO.getMappingId() );
                           break;
                        }

                     }
                  }
               }
            }

            // ���clientOrderHeaderVO�ĸ�˰��������ֵ
            if ( KANUtil.filterEmpty( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() ) == null )
            {
               // ����clientOrderHeaderVO��Ĭ�ϸ�˰˰��
               if ( clientOrderHeaderVO.getIncomeTaxRangeHeaders() != null && clientOrderHeaderVO.getIncomeTaxRangeHeaders().size() > 0 )
               {
                  // ���ֻ��һ����¼
                  if ( clientOrderHeaderVO.getIncomeTaxRangeHeaders().size() == 2 )
                  {
                     clientOrderHeaderVO.setIncomeTaxRangeHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaders().get( 1 ).getMappingId() );
                  }
                  //  ������¼ȡ��һ������Ϊ"Ĭ��"�ļ�¼
                  else
                  {
                     for ( MappingVO incomeTaxRangeHeaderMappingVO : clientOrderHeaderVO.getIncomeTaxRangeHeaders() )
                     {
                        // ��ʼ��Service�ӿ�
                        final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
                        final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( incomeTaxRangeHeaderMappingVO.getMappingId() );

                        if ( incomeTaxRangeHeaderVO != null && incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equals( "1" ) )
                        {
                           clientOrderHeaderVO.setIncomeTaxRangeHeaderId( incomeTaxRangeHeaderMappingVO.getMappingId() );
                           break;
                        }

                     }
                  }
               }
            }
         }

         // ��ʼ��PagedListHolder
         final PagedListHolder serviceContractHolder = new PagedListHolder();

         // װ��������ϢHolder����
         fetchServiceContractHolder( request, response, serviceContractHolder, orderHeaderId, true );

         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
         final List< Object > clientOrderHeaderRuleVOs = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderHeaderRuleVOs != null && clientOrderHeaderRuleVOs.size() > 0 )
         {
            for ( Object obj : clientOrderHeaderRuleVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // ��ʼ��Service�ӿ�
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         final List< Object > clientOrderDetailVOs = clientOrderDetailService.getClientOrderDetailVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderDetailVOs != null && clientOrderDetailVOs.size() > 0 )
         {
            for ( Object obj : clientOrderDetailVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // ��ʼ��Service�ӿ�
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
         {
            for ( Object obj : clientOrderSBVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // ��ʼ��Service�ӿ�
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         final List< Object > clientOrderCBVOs = clientOrderCBService.getClientOrderCBVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderCBVOs != null && clientOrderCBVOs.size() > 0 )
         {
            for ( Object obj : clientOrderCBVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // ��ʼ��Service�ӿ�
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
         final List< Object > clientOrderLeaveVOs = clientOrderLeaveService.getClientOrderLeaveVOsByOrderHeaderId( orderHeaderId );
         if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
         {
            for ( Object obj : clientOrderLeaveVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // ��ʼ��Service�ӿ�
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );
         final List< Object > clientOrderOTVOs = clientOrderOTService.getClientOrderOTVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderOTVOs != null && clientOrderOTVOs.size() > 0 )
         {
            for ( Object obj : clientOrderOTVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // ��ʼ��Service�ӿ�
         final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );
         final List< Object > clientOrderOtherVOs = clientOrderOtherService.getClientOrderOtherVOsByOrderHeaderId( orderHeaderId );
         if ( clientOrderOtherVOs != null && clientOrderOtherVOs.size() > 0 )
         {
            for ( Object obj : clientOrderOtherVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // н�깩Ӧ��
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         final List< String > headerIds = new ArrayList< String >();
         for ( Object obj : clientOrderSBVOs )
         {
            headerIds.add( ( ( ClientOrderSBVO ) obj ).getSbSolutionId() );
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
         salaryVendors.add( clientOrderHeaderVO.getEmptyMappingVO() );
         for ( Object vendorObject : vendorVOs )
         {
            final VendorVO tempVendorVO = ( VendorVO ) vendorObject;
            final MappingVO tempMappingVO = new MappingVO();
            tempMappingVO.setMappingId( tempVendorVO.getVendorId() );
            tempMappingVO.setMappingValue( "zh".equalsIgnoreCase( getLocale( request ).getLanguage() ) ? tempVendorVO.getNameZH() : tempVendorVO.getNameEN() );
            salaryVendors.add( tempMappingVO );
         }
         clientOrderHeaderVO.setSalaryVendors( salaryVendors );

         // ID
         request.setAttribute( "orderId", orderHeaderId );

         // Form
         clientOrderHeaderVO.setSubAction( request.getParameter( "subAction" ) );
         request.setAttribute( "clientOrderHeaderForm", clientOrderHeaderVO );

         // List
         request.setAttribute( "serviceContractHolder", serviceContractHolder );
         request.setAttribute( "clientOrderHeaderRuleVOs", clientOrderHeaderRuleVOs );
         request.setAttribute( "clientOrderDetailVOs", clientOrderDetailVOs );
         request.setAttribute( "clientOrderSBVOs", clientOrderSBVOs );
         request.setAttribute( "clientOrderCBVOs", clientOrderCBVOs );
         request.setAttribute( "clientOrderLeaveVOs", clientOrderLeaveVOs );
         request.setAttribute( "clientOrderOTVOs", clientOrderOTVOs );
         request.setAttribute( "clientOrderOtherVOs", clientOrderOtherVOs );

         // Tab Number
         request.setAttribute( "numberOfEmployee", ( serviceContractHolder == null ) ? ( "0" ) : ( serviceContractHolder.getHolderSize() ) );
         request.setAttribute( "numberOfClientOrderHeaderRule", ( clientOrderHeaderRuleVOs == null ) ? ( "0" ) : ( clientOrderHeaderRuleVOs.size() ) );
         request.setAttribute( "numberOfClientOrderServiceFee", ( clientOrderDetailVOs == null ) ? ( "0" ) : ( clientOrderDetailVOs.size() ) );
         request.setAttribute( "numberOfClientOrderSB", ( clientOrderSBVOs == null ) ? ( "0" ) : ( clientOrderSBVOs.size() ) );
         request.setAttribute( "numberOfClientOrderCB", ( clientOrderCBVOs == null ) ? ( "0" ) : ( clientOrderCBVOs.size() ) );
         request.setAttribute( "numberOfClientOrderLeave", ( clientOrderLeaveVOs == null ) ? ( "0" ) : ( clientOrderLeaveVOs.size() ) );
         request.setAttribute( "numberOfClientOrderOT", ( clientOrderOTVOs == null ) ? ( "0" ) : ( clientOrderOTVOs.size() ) );
         request.setAttribute( "numberOfClientOrderOther", ( clientOrderOtherVOs == null ) ? ( "0" ) : ( clientOrderOtherVOs.size() ) );
         request.setAttribute( "numberOfAttachment", ( clientOrderHeaderVO == null || clientOrderHeaderVO.getAttachmentArray() == null ) ? ( "0" )
               : ( clientOrderHeaderVO.getAttachmentArray().length ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      final String comeForm = request.getParameter( "comeFrom" );
      if ( KANUtil.filterEmpty( comeForm ) != null && comeForm.equals( "workflow" ) )
      {
         return mapping.findForward( "workflowClientOrderHeaderSpecialInfo" );
      }

      // ����Return
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeaderSpecialInfo" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderSpecialInfoInHouse" );
      }

   }

   // װ��Tab����Э��Holder
   // Add by siuvan.xia @ 2014-07-08
   private void fetchServiceContractHolder( final HttpServletRequest request, final HttpServletResponse response, final PagedListHolder serviceContractHolder,
         final String orderHeaderId, final boolean isPaged ) throws KANException
   {
      // �½��������ѯ����Э��
      if ( KANUtil.filterEmpty( orderHeaderId ) != null )
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ʼ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setOrderId( orderHeaderId );
         employeeContractVO.setFlag( "2" );
         employeeContractVO.setSortColumn( "contractId" );
         employeeContractVO.setSortOrder( "desc" );

         // ���뵱ǰֵ����
         serviceContractHolder.setObject( employeeContractVO );
         // ����ҳ���¼����
         serviceContractHolder.setPageSize( this.listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractService.getEmployeeContractVOsByCondition( serviceContractHolder, isPaged );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( serviceContractHolder, request );
      }

   }

   /**
    * List Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_options_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // ��ȡclientId
         final String clientId = request.getParameter( "clientId" );

         final List< Object > list = clientOrderHeaderService.getClientOrderHeaderBaseViewsByClientId( clientId );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( list );
         // Send to clientOrderHeaderGroup
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return null;
   }

   /**  
    * list_applyOTFirst_options_ajax
    *	���ɡ��Ӱ���Ҫ���롱������
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_applyOTFirst_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ���applyOTFirst ��ֵ
         final String applyOTFirst = request.getParameter( "applyOTFirst" );
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs = KANUtil.getMappings( request.getLocale(), "flag" );
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "applyOTFirst", applyOTFirst ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return null;
   }

   /**  
    * list_item_options_ajax
    *	�����˻���Ӧ����Ŀ��������
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_item_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ���ItemId ��ֵ
         final String itemId = request.getParameter( "itemId" );
         // ��üӰ��Ŀ����
         final String itemType = request.getParameter( "itemType" );
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         // ��õ�ǰAccountId
         final String accountId = getAccountId( request, response );
         // ��õ�ǰ�ŻԶ�Ӧ�ļӰ��Ŀ��
         mappingVOs = KANConstants.getKANAccountConstants( accountId ).getOtItems( request.getLocale().getLanguage() );
         // ��ӡ���ѡ��ѡ��
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, itemType, itemId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return null;
   }

   /**
    * ���ݿͻ�ID��ȡ�ͻ����������б�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-12-13
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ȡOrderId
         final String orderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderId" ) );

         // ��ȡClientId
         String corpId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );

         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            corpId = getCorpId( request, response );
         }

         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         clientOrderHeaderVO.setCorpId( corpId );
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

         out.println( KANUtil.getOptionHTML( mappingVOs, "orderId", orderId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return null;
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
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת��Manage����   
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeader" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderInHouse" );
      }

   }

   /**  
    * �������ClientId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkClientId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      // ��ȡForm
      final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
      // ���ClientId
      final String clientId = KANUtil.filterEmpty( clientOrderHeaderVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "�ͻ�ID������Ч��" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   public void updateEmployeeSBBaseBySolution( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         String orderId = request.getParameter( "orderId" );
         String sbSolutionId = request.getParameter( "sbSolutionId" );
         String accountId = BaseAction.getAccountId( request, response );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         clientOrderHeaderService.updateEmployeeSBBaseBySolution( orderId, sbSolutionId, accountId );
         addSuccessAjax( out, null );
         insertlog( request, form, Operate.MODIFY, orderId, "Ӧ����ͻ���>>updateEmployeeSBBaseBySolution(" + orderId + "," + sbSolutionId + "," + accountId + ")" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private String getFinalEndDate( final ClientOrderHeaderVO clientOrderHeaderVO ) throws Exception
   {
      String finalEndDate = clientOrderHeaderVO.getEndDate();
      final String startDate = clientOrderHeaderVO.getStartDate() + " 00:00:00";
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
      final SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
      final Date sDate = sdf.parse( startDate );
      Calendar calendar = Calendar.getInstance();
      if ( KANUtil.filterEmpty( finalEndDate ) != null )
      {
         final Date eDate = sdf.parse( clientOrderHeaderVO.getEndDate() + " 23:59:59" );
         calendar.setTime( sDate );
         calendar.add( Calendar.YEAR, 3 );
         //�������ʱ�����3���ڣ����ն�����
         if ( calendar.getTime().getTime() > eDate.getTime() )
         {
            finalEndDate = clientOrderHeaderVO.getEndDate();
         }
         else
         {
            // ������3��
            calendar.setTime( KANUtil.getDateAfterMonth( sDate, 36 ) );
            calendar.add( Calendar.DAY_OF_MONTH, -1 );
            finalEndDate = sdf2.format( calendar.getTime() );
         }
      }
      else
      {
         // ��ʱ�������
         calendar.setTime( KANUtil.getDateAfterMonth( sDate, 36 ) );
         calendar.add( Calendar.DAY_OF_MONTH, -1 );
         finalEndDate = sdf2.format( calendar.getTime() );
      }
      return finalEndDate;
   }

   // ����������Ϣ
   // Added by siuvan.xia @ 2014-07-08
   public ActionForward export_service_contract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��OrderHeaderId
         final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

         // ��ʼ��PagedListHolder
         final PagedListHolder pagedListHolder = new PagedListHolder();
         fetchServiceContractHolder( request, response, pagedListHolder, orderHeaderId, false );

         // ��ʼ��������ͷ
         final String[] columnSysNames = new String[] { "contractId", "startDate", "endDate", "employeeId", "employeeNo", "employeeNameZH", "employeeNameEN", "certificateNumber",
               "decodeEmployStatus", "decodeStatus" };

         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "holderName", "pagedListHolder" );
         request.setAttribute( "fileName", ( getRole( request, response ).equals( "1" ) ? "��Ա" : "Ա��" ) );
         request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
         request.setAttribute( "nameSysArray", columnSysNames );

         // �����ļ�
         return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // ������ͷ
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String role = getRole( request, response );
      // ��ʼ��������ͷ
      final String[] columnZHNames = new String[ 10 ];
      columnZHNames[ 0 ] = ( role.equals( "1" ) ? "������Ϣ" : "�Ͷ���ͬ" ) + "ID";
      columnZHNames[ 1 ] = "����ʼʱ��";
      columnZHNames[ 2 ] = "�������ʱ��";
      columnZHNames[ 3 ] = ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "ID";
      columnZHNames[ 4 ] = ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "���";
      columnZHNames[ 5 ] = ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "���������ģ�";
      columnZHNames[ 6 ] = ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "������Ӣ�ģ�";
      columnZHNames[ 7 ] = "֤������";
      columnZHNames[ 8 ] = "��Ӷ״̬";
      columnZHNames[ 9 ] = ( role.equals( "1" ) ? "������Ϣ" : "�Ͷ���ͬ" ) + "״̬";
      return columnZHNames;
   }

   // Added by siuvan.xia 2015-02-09
   public ActionForward calculate_annual_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��OrderHeaderId
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // ��ʼ�� Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         int rows = clientOrderHeaderService.calculateEmployeeAnnualLeave( clientOrderHeaderVO );
         if ( rows > 0 )
         {
            success( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.success" ).replaceAll( "X", String.valueOf( rows ) ) );
            insertlog( request, form, Operate.MODIFY, orderHeaderId, "������٣�rows " + rows );
         }
         else
         {
            warning( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.failure" ) );
            insertlog( request, form, Operate.MODIFY, orderHeaderId, "������٣�rows " + rows );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

}
