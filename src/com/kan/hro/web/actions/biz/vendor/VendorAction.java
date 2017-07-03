package com.kan.hro.web.actions.biz.vendor;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.service.inf.biz.vendor.VendorServiceService;

public class VendorAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_VENDOR";
   // ��ǰAction��Ӧ��JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";

   /**  
    * List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-21
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

         // ��ȡ��Ӧ��vendorId
         final String vendorId = request.getParameter( "vendorId" );

         // ��ȡ�籣����ID
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         if ( KANUtil.filterEmpty( sbSolutionId, "0" ) != null )
         {
            // ��ʼ��Service�ӿ�
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );

            // ����SBSolutionId���VendorVO�б�
            final List< Object > vendorVOs = vendorService.getVendorVOsBySBHeaderId( sbSolutionId );

            // ����������
            if ( vendorVOs != null && vendorVOs.size() > 0 )
            {
               for ( Object vendorVOObject : vendorVOs )
               {
                  final VendorVO vendorVO = ( VendorVO ) vendorVOObject;

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( vendorVO.getVendorId() );
                  // ��������Ļ���
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( vendorVO.getNameZH() );
                  }
                  // ��������Ļ���
                  else
                  {
                     mappingVO.setMappingValue( vendorVO.getNameEN() );
                  }
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "vendorId", vendorId ) );
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

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( vendorService.getVendorBaseViewsByAccountId( getAccountId( request, response ) ) );

         // Send to client
         out.println( array.toString() );
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

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ���Action Form
         final VendorVO vendorVO = ( VendorVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, vendorVO );

         // ���û��ָ��������Ĭ�ϰ� vendorId����
         if ( vendorVO.getSortColumn() == null || vendorVO.getSortColumn().isEmpty() )
         {
            vendorVO.setSortColumn( "vendorId" );
            vendorVO.setSortOrder( "desc" );
         }

         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ�����������
         vendorVO.setRemark1( generateDefineListSearches( request, accessAction ) );
         // ����SubAction
         dealSubAction( vendorVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder vendorPagedListHolder = new PagedListHolder();
         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            vendorPagedListHolder.setPage( page );
            // ���뵱ǰֵ����
            vendorPagedListHolder.setObject( vendorVO );
            // ����ҳ���¼����
            vendorPagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            vendorService.getVendorVOsByCondition( vendorPagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( vendorPagedListHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", vendorPagedListHolder );
         // ����Return
         return dealReturn( accessAction, "listVendor", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // �����ظ��ύ
      this.saveToken( request );

      // ��ʼ��PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      // ���ActionForm
      final VendorVO vendorVO = ( VendorVO ) form;
      // ����Sub Action
      vendorVO.setSubAction( BaseAction.CREATE_OBJECT );
      vendorVO.setType( "2" );
      vendorVO.setStatus( VendorVO.TRUE );
      // ������������������
      if ( positionVO != null )
      {
         vendorVO.setBranch( positionVO.getBranchId() );
         vendorVO.setOwner( positionVO.getPositionId() );
      }
      return mapping.findForward( "manageVendor" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            // ��õ�ǰFORM
            final VendorVO vendorVO = ( VendorVO ) form;
            // �趨��ǰ�û�
            vendorVO.setAccountId( getAccountId( request, response ) );
            vendorVO.setCreateBy( getUserId( request, response ) );
            vendorVO.setModifyBy( getUserId( request, response ) );
            vendorVO.setDeleted( "1" );

            // �����Զ���Column
            vendorVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // ������ӷ���
            int result = vendorService.insertVendor( vendorVO );

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, vendorVO, Operate.SUBMIT, vendorVO.getVendorId(), null );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, vendorVO, Operate.ADD, vendorVO.getVendorId(), null );
            }

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + ( ( VendorVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }
         }
         else
         {
            // ���form
            ( ( VendorVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ������ȡ�����
         String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( vendorId == null || vendorId.trim().isEmpty() )
         {
            vendorId = ( ( VendorVO ) form ).getVendorId();
         }

         // ���VendorVO����
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         vendorVO.reset( null, request );
         // ���City Id�������Province Id
         if ( vendorVO.getCityId() != null && !vendorVO.getCityId().trim().equals( "" ) && !vendorVO.getCityId().trim().equals( "0" ) )
         {
            vendorVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorVO.setCityIdTemp( vendorVO.getCityId() );
         }
         // �����޸����
         vendorVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "vendorForm", vendorVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendor" );
   }

   /**  
    * To ObjectModify InVendor
    *	��Ӧ�̵�¼��ʾ��Ӧ����Ϣ
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_objectModify_inVendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �趨�Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ��õ�¼��Ӧ��ID
         String vendorId = getVendorId( request, response );

         // ���VendorVO����
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         vendorVO.reset( null, request );

         // ���City Id�������Province Id
         if ( vendorVO.getCityId() != null && !vendorVO.getCityId().trim().equals( "" ) && !vendorVO.getCityId().trim().equals( "0" ) )
         {
            vendorVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorVO.setCityIdTemp( vendorVO.getCityId() );
         }

         // �����޸����
         vendorVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "vendorForm", vendorVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendor" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            // ������ȡ�����
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���VendorVO����
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );
            // װ�ؽ��洫ֵ
            vendorVO.update( ( ( VendorVO ) form ) );
            // ��ȡ��¼�û�
            vendorVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            vendorVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // ������ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               vendorVO.reset( mapping, request );
               if ( vendorService.submitVendor( vendorVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, vendorVO, Operate.SUBMIT, vendorVO.getVendorId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, vendorVO, Operate.MODIFY, vendorVO.getVendorId(), null );
               }
            }
            else
            {
               vendorService.updateVendor( vendorVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, vendorVO, Operate.MODIFY, vendorVO.getVendorId(), null );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ���Action Form
         VendorVO vendorVO = ( VendorVO ) form;
         if ( vendorVO.getSelectedIds() != null && !vendorVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, vendorVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( vendorVO.getSelectedIds() ) );
            // �ָ�
            for ( String selectedId : vendorVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  vendorVO = vendorService.getVendorVOByVendorId( KANUtil.decodeStringFromAjax( selectedId ) );
                  vendorVO.setModifyBy( getUserId( request, response ) );
                  vendorVO.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  vendorService.deleteVendor( vendorVO );
               }
            }
         }
         // ���Selected IDs����Action
         vendorVO.setSelectedIds( "" );
         vendorVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ������������
         final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���LeaveVO����
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         // ��ȡ��¼�û�
         vendorVO.setModifyBy( getUserId( request, response ) );
         vendorVO.setLocale( request.getLocale() );

         // ���Action Form
         vendorVO.reset( mapping, request );

         if ( vendorService.submitVendor( vendorVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, vendorVO, Operate.SUBMIT, vendorId, null );
         }
         else
         {
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, vendorVO, Operate.MODIFY, vendorId, null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward to_workflowView( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ������ȡ�����
         final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( vendorId != null && !vendorId.trim().isEmpty() )
         {
            // ��ʼ��Service�ӿ�
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            // ���VendorVO����
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
            // ˢ�¶��󣬳�ʼ�������б����ʻ�
            vendorVO.reset( null, request );
            // д��request����
            request.setAttribute( "vendorVO", vendorVO );
         }
         final String workflowId = request.getParameter( "workflowId" );

         WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );

         WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );

         request.setAttribute( "workflowActualVO", workflowActualVO );

         if ( workflowId != null && !workflowId.trim().isEmpty() )
         {
            HistoryService historyService = ( HistoryService ) getService( "historyService" );
            HistoryVO historyVO = historyService.getHistoryVOByWorkflowId( workflowId );
            String passObjStr = historyVO.getPassObject();
            if ( passObjStr != null && !passObjStr.trim().isEmpty() )
            {
               final VendorVO vendorVO = ( VendorVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), VendorVO.class );
               request.setAttribute( "passObject", vendorVO );
            }

         }

         return mapping.findForward( "workflowVendorView" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
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

         final String ajax = request.getParameter( "ajax" );
         // ��ʼ������Tab Number
         int vendorServiceCount = 0;
         int vendorContactCount = 0;
         int attachmentCount = 0;
         // ID��ȡ�����
         String vendorId = "";
         if ( new Boolean( ajax ) )
         {
            vendorId = Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( request.getParameter( "vendorId" ), "UTF-8" ), "UTF-8" ) );
         }
         else
         {
            vendorId = request.getParameter( "vendorId" );
         }
         // ��ʼ��employeeContractService�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         if ( vendorId != null && !vendorId.equals( "" ) )
         {
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
            vendorVO.reset( null, request );
            attachmentCount = vendorVO.getAttachmentArray().length;

            final PagedListHolder pagedListHolder = new PagedListHolder();
            final VendorServiceVO vendorServiceVO = new VendorServiceVO();
            vendorServiceVO.setVendorId( vendorId );
            vendorServiceVO.setSortOrder( "ASC" );
            vendorServiceVO.setSortColumn( "cityId,sbHeaderId" );
            pagedListHolder.setObject( vendorServiceVO );
            vendorServiceService.getVendorServiceVOsByCondition( pagedListHolder, false );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
            vendorServiceCount = pagedListHolder.getHolderSize();

            // ��ù�Ӧ����ϵ��
            final List< Object > vendorContactVOs = vendorContactService.getVendorContactVOsByVendorId( vendorId );
            //  ���ع�Ӧ��Info
            request.setAttribute( "vendorForm", vendorVO );
            request.setAttribute( "pagedListHolder", pagedListHolder );
            request.setAttribute( "vendorContactVOs", vendorContactVOs );
            request.setAttribute( "accountId", getAccountId( request, null ) );
            request.setAttribute( "username", getUsername( request, null ) );
            vendorContactCount = vendorContactVOs.size();
         }

         request.setAttribute( "listVendorServiceCount", vendorServiceCount );
         request.setAttribute( "listVendorContactCount", vendorContactCount );
         request.setAttribute( "attachmentCount", attachmentCount );

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // ��ʼ����ѯ����SBHeaderVO
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();

         // �����������
         sbHeaderVO.setVendorId( vendorId );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setClientId( getClientId( request, response ) );
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         decodedObject( sbHeaderVO );

         // ���ʻ�
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbHeaderHolder.setPage( getPage( request ) );
         // ������������ֶ�
         sbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ��·�����
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "monthly" );
            sbHeaderVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         sbHeaderHolder.setObject( sbHeaderVO );
         // ����ҳ���¼����
         sbHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ(��ѯ��Ӧ�̶�Ӧ�籣��������)
         sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         refreshHolder( sbHeaderHolder, request );

         // д��Role
         request.setAttribute( "role", getRole( request, response ) );
         // Holder��д��Request����
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
         request.setAttribute( "listVendorPaymentCount", sbHeaderHolder.getSource().size() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listSpecialInfo" );
   }

   public ActionForward getSBMappingVOsByCityId_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ó���ID
         final String cityId = request.getParameter( "cityId" );

         // ��ʼ��Service
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // ��ʼ��MappingVO
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ȡSocialBenefitDTO�б�
         final List< SocialBenefitDTO > socialBenefitDTOs = KANConstants.SOCIAL_BENEFIT_DTO;

         // ��ʼ��SocialbenefitSolutionHeaderVO�б�
         List< Object > socialBenefitSolutionHeaderVOs = null;

         // ����SocialBenefitDTO�б�
         if ( socialBenefitDTOs != null && socialBenefitDTOs.size() > 0 )
         {
            for ( SocialBenefitDTO tempSocialBenefitDTO : socialBenefitDTOs )
            {
               // ���ڵ�ǰ�����籣
               if ( tempSocialBenefitDTO.getSocialBenefitHeaderVO().getCityId().equals( cityId ) )
               {
                  final SocialBenefitSolutionHeaderVO tempVO = new SocialBenefitSolutionHeaderVO();
                  tempVO.setSysHeaderId( tempSocialBenefitDTO.getSocialBenefitHeaderVO().getHeaderId() );
                  tempVO.setAccountId( getAccountId( request, response ) );
                  socialBenefitSolutionHeaderVOs = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( tempVO );
                  break;
               }
            }
         }

         // ����SocialbenefitSolutionHeaderVO�б�
         if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
         {
            for ( Object o : socialBenefitSolutionHeaderVOs )
            {
               // ��ʼ��SocialBenefitSolutionHeaderVO
               final SocialBenefitSolutionHeaderVO temp = ( SocialBenefitSolutionHeaderVO ) o;

               // ��ʼ��MappingVO
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( temp.getHeaderId() );
               mappingVO.setMappingValue( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? temp.getNameZH() : temp.getNameEN() );

               if ( KANUtil.filterEmpty( getCorpId( request, response ) ) == null )
               {
                  if ( KANUtil.filterEmpty( temp.getCorpId() ) == null )
                  {
                     mappingVOs.add( mappingVO );
                  }

               }
               else
               {
                  if ( KANUtil.filterEmpty( temp.getCorpId() ) != null && temp.getCorpId().equals( getCorpId( request, response ) ) )
                  {
                     mappingVOs.add( mappingVO );
                  }
               }
            }
         }

         out.println( KANUtil.getSelectHTML( mappingVOs, "manageVendorService_sbHeaderId", "manageVendorService_sbHeaderId", null, null, null ) );
         out.flush();
         out.close();
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Ajax
    * ��Ӧ�̵���ģ̬��
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ���Action Form 
         final VendorVO vendorVO = ( VendorVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, vendorVO );

         // ����
         decodedObject( vendorVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder vendorHolder = new PagedListHolder();

         // ���뵱ǰҳ
         vendorHolder.setPage( page );
         // ���뵱ǰֵ����
         vendorHolder.setObject( vendorVO );
         // ����ҳ���¼����
         vendorHolder.setPageSize( listPageSize_popup );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         vendorService.getVendorVOsByCondition( vendorHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( vendorHolder, request );
         // Holder��д��Request����
         request.setAttribute( "vendorHolder", vendorHolder );

         // Ajax Table���ã�ֱ�Ӵ��� JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

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
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // ��ȡvendorId
         final String vendorId = request.getParameter( "vendorId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ʼ��VendorVO
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         if ( vendorVO != null && vendorVO.getAccountId() != null && vendorVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            vendorVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( vendorVO );
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

   public List< MappingVO > list_option( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ��ʼ������ֵ
      final List< MappingVO > vendorMappingVOs = new ArrayList< MappingVO >();
      vendorMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

      // ��ʼ�� Service
      final VendorService vendorService = ( VendorService ) getService( "vendorService" );

      // ��ʼ��VendorVO
      final VendorVO vendorVO = new VendorVO();
      vendorVO.setAccountId( getAccountId( request, response ) );
      vendorVO.setCorpId( getCorpId( request, response ) );
      // ��׼״̬
      vendorVO.setStatus( "3" );

      // ��ȡVendorVO�б�
      final List< Object > vendors = vendorService.getVendorVOsByCondition( vendorVO );

      // ����VendorVO�б�
      if ( vendors != null && vendors.size() > 0 )
      {
         for ( Object vendorVOObject : vendors )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( ( ( VendorVO ) vendorVOObject ).getVendorId() );

            if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
            {
               mappingVO.setMappingValue( ( ( VendorVO ) vendorVOObject ).getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( ( ( VendorVO ) vendorVOObject ).getNameEN() );
            }

            vendorMappingVOs.add( mappingVO );
         }
      }

      return vendorMappingVOs;
   }

   public ActionForward list_service_evaluate( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return mapping.findForward( "listServiceEvaluate" );
   }
}
