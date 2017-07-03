package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
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

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.CityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 *   
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-8  
 *   
 */
public class ClientAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT";

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
   // Added by Kevin Jin at 2013-11-05
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��ȡClientId
         final String clientId = request.getParameter( "clientId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ʼ��ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         if ( clientVO != null && clientVO.getAccountId() != null && clientVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientVO );
            jsonObject.put( "success", "true" );

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

            jsonObject.put( "orderBindContract", orderBindContract );
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
    * List Object Json ForFullView
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_json_forFullView( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��ȡ��ǰ��¼�û���accountId
         final String accountId = getAccountId( request, response );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientService.getClientFullViews( accountId ) );

         // Send to front
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

   /**  
    * List Object Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // ���Action Form 
         final ClientVO clientVO = ( ClientVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientVO );

         // ����
         decodedObject( clientVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder clientHolder = new PagedListHolder();

         // ���뵱ǰҳ
         clientHolder.setPage( page );
         // ���뵱ǰֵ����
         clientHolder.setObject( clientVO );
         // ����ҳ���¼����
         clientHolder.setPageSize( listPageSize_popup );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientService.getClientVOsByCondition( clientHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( clientHolder, request );
         // Holder��д��Request����
         request.setAttribute( "clientHolder", clientHolder );

         // Ajax Table���ã�ֱ�Ӵ���JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Object
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // ���Action Form
         final ClientVO clientVO = ( ClientVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientVO );

         // ���SubAction
         final String subAction = getSubAction( form );

         // ����Զ�����������
         clientVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ���û��ָ��������Ĭ�ϰ� ClientId����
         if ( clientVO.getSortColumn() == null || clientVO.getSortColumn().isEmpty() )
         {
            clientVO.setSortColumn( "clientId" );
            clientVO.setSortOrder( "desc" );
         }

         // ����SubAction
         dealSubAction( clientVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder clientHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            clientHolder.setPage( page );
            // ���뵱ǰֵ����
            clientHolder.setObject( clientVO );
            // ����ҳ���¼����
            clientHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            clientService.getClientVOsByCondition( clientHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( clientHolder, request );
         }

         request.setAttribute( "pagedListHolder", clientHolder );

         // ����Return
         return dealReturn( accessAction, "listClient", mapping, form, request, response );
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
   // Reviewed by Kevin Jin at 2013-10-11
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      // ҳ��Reset
      ( ( ClientVO ) form ).reset();
      // ����Sub Action
      ( ( ClientVO ) form ).setSubAction( CREATE_OBJECT );

      // �����½�ҳ��Ĭ��ֵ
      ( ( ClientVO ) form ).setStatus( ClientVO.TRUE );
      ( ( ClientVO ) form ).setOrderBindContract( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_ORDER_BIND_CONTRACT ? "1" : "2" );
      ( ( ClientVO ) form ).setSbGenerateCondition( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SB_GENERATE_CONDITION );
      ( ( ClientVO ) form ).setCbGenerateCondition( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_CB_GENERATE_CONDITION );
      ( ( ClientVO ) form ).setSettlementCondition( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SETTLEMENT_GENERATE_CONDITION );
      ( ( ClientVO ) form ).setSbGenerateConditionSC( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SB_GENERATE_CONDITION_SC );
      ( ( ClientVO ) form ).setCbGenerateConditionSC( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_CB_GENERATE_CONDITION_SC );
      ( ( ClientVO ) form ).setSettlementConditionSC( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SETTLEMENT_GENERATE_CONDITION_SC );

      // �ж��Ƿ�Ӽ���ҳ�����
      final String encodedGroupId = request.getParameter( "groupId" );

      // ����ǴӼ���ҳ�����
      if ( encodedGroupId != null && !encodedGroupId.trim().isEmpty() )
      {
         // ����ClientVO �� groupId
         ( ( ClientVO ) form ).setGroupId( KANUtil.decodeString( encodedGroupId ) );
      }

      // ��ʼ��PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      // ����Ĭ�ϡ��������š����������ˡ�
      if ( positionVO != null )
      {
         ( ( ClientVO ) form ).setBranch( positionVO.getBranchId() );
         ( ( ClientVO ) form ).setOwner( positionVO.getPositionId() );
      }

      // ��ת���½�����
      return mapping.findForward( "manageClient" );
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
   // Reviewed by Kevin Jin at 2013-10-13
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��õ�ǰ����
         String clientId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( clientId == null || clientId.trim().isEmpty() )
         {
            clientId = ( ( ClientVO ) form ).getClientId();
         }

         // ���ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         clientVO.reset( mapping, request );

         // �������City Id�������Province Id
         if ( KANUtil.filterEmpty( clientVO.getCityId(), "0" ) != null )
         {
            // ��ȡCityVO
            final CityVO cityVO = KANConstants.LOCATION_DTO.getCityVO( clientVO.getCityId(), request.getLocale().getLanguage() );

            clientVO.setProvinceId( cityVO != null ? cityVO.getProvinceId() : "0" );
            clientVO.setCityIdTemp( clientVO.getCityId() );
         }

         //  ��ȡ��ǰ�������ͬ������������ͬ��״̬Ϊ�鵵���������벻���޸�
         ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         final List< Object > clientContractObjects = clientContractService.getClientContractVOsByClientId( clientId );
         boolean isFiling = false;
         for ( Object obj : clientContractObjects )
         {
            if ( "6".equals( ( ( ClientContractVO ) obj ).getStatus() ) )
            {
               isFiling = true;
               break;
            }
         }
         request.setAttribute( "isFiling", isFiling );
         clientVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientForm", clientVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClient" );
   }

   /**  
    * To_ObjectModify_Internal
    *	Inhouse�ͻ���Ϣά��
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_objectModify_internal( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��õ�ǰ����
         String corpId = getCorpId( request, response );

         // ���ClientVO
         final ClientVO clientVO = clientService.getClientVOByCorpId( corpId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         clientVO.reset( null, request );

         // �������City Id�������Province Id
         if ( KANUtil.filterEmpty( clientVO.getCityId(), "0" ) != null )
         {
            // ��ȡCityVO
            final CityVO cityVO = KANConstants.LOCATION_DTO.getCityVO( clientVO.getCityId(), request.getLocale().getLanguage() );

            clientVO.setProvinceId( cityVO != null ? cityVO.getProvinceId() : "0" );
            clientVO.setCityIdTemp( clientVO.getCityId() );
         }

         clientVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientForm", clientVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientInternal" );
   }

   /**
    * Add Client
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // ���ActionForm
            final ClientVO clientVO = ( ClientVO ) form;
            // ��ȡ��¼�û�
            clientVO.setCreateBy( getUserId( request, response ) );
            clientVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            clientVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �ֻ�ģ��Ȩ��
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );

            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               clientVO.setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }

            // �½�����
            clientVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
            clientVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );
            clientService.insertClient( clientVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + ( ( ClientVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
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

   /**  
    * Copy Object
    *	���ƿͻ���Ϣ
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward copy_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( request.getParameter( "clientId" ) ) != null )
         {
            // ��ʼ��Service�ӿ�
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // ��ȡ���ݿ���ԭ�ж���
            final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

            ( ( ClientVO ) form ).setClientId( "" );
            ( ( ClientVO ) form ).update( clientVO );
            // ��״̬��Ϊ�½�
            ( ( ClientVO ) form ).setStatus( "1" );
            // ��ȡ��¼�û�
            ( ( ClientVO ) form ).setCreateBy( getUserId( request, response ) );
            ( ( ClientVO ) form ).setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            ( ( ClientVO ) form ).setRemark1( saveDefineColumns( request, accessAction ) );

            // �ֻ�ģ��Ȩ��
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );
            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               ( ( ClientVO ) form ).setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }

            // �½�����
            clientService.insertClient( ( ( ClientVO ) form ) );

            // ������ӳɹ����
            success( request, null, "������Ϣ�ɹ�", MESSAGE_HEADER );

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + ( ( ClientVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );
            }

         }
         else
         {
            // TODO �����ת
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

   /**
    * Modify Client
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientService clientService = ( ClientService ) getService( "clientService" );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ��õ�ǰ����
            String clientId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

            clientVO.update( ( ClientVO ) form );
            // �����Զ���Column
            clientVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // ��Ƭ
            final String[] imageFileArray = request.getParameterValues( "imageFileArray" );
            String imageFileString = "";

            if ( imageFileArray != null && imageFileArray.length > 0 )
            {
               for ( String s : imageFileArray )
               {
                  imageFileString += s;
                  imageFileString += "##";
               }
               clientVO.setImageFile( imageFileString.length() > 0 ? imageFileString.substring( 0, imageFileString.length() - 2 ) : null );
            }
            // �ֻ�ģ��Ȩ��
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );
            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               clientVO.setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }

            clientVO.setModifyBy( getUserId( request, response ) );
            clientVO.reset( mapping, request );

            // �����Զ���Column
            clientVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
            clientVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );

            // ����ǿͻ��ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( clientService.submitClient( clientVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }
            else
            {
               clientService.updateClient( clientVO );
               success( request, MESSAGE_TYPE_UPDATE );
            }
            // ��ʼ�������־ö���
            constantsInit( "initOptions", getAccountId( request, response ) );
            insertlog( request, clientVO, Operate.MODIFY, clientVO.getClientId(), "��ҵ��Ϣ" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ����ǿͻ���¼��ֱ����ת���ͻ���Ϣ����ҳ��
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return to_objectModify_internal( mapping, form, request, response );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Ajax
    *	ajax�޸Ŀͻ���������
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward modify_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��õ�ǰ����
         String clientId = request.getParameter( "clientId" );
         // ��ü���ID
         final String groupId = request.getParameter( "groupId" );
         // ���ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

         if ( clientVO != null )
         {
            // ���¼���ID
            clientVO.setGroupId( groupId );
            clientVO.setModifyBy( getUserId( request, response ) );
            clientVO.reset( mapping, request );
            // �������ݿ�
            clientService.updateClient( clientVO );
            jsonObject.put( "clientVO", clientVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            // �ͻ�ID��Ч
            jsonObject.put( "success", "invalidClientId" );
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
    * Submit Client
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // ������������
         final String clientId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���ClientVO����
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

         // ��������
         clientVO.setModifyBy( getUserId( request, response ) );
         clientVO.setModifyDate( new Date() );
         clientVO.reset( null, request );
         clientVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
         clientVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );
         if ( clientService.submitClient( clientVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
         }
         else
         {
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Delete Client
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
      // No Use
   }

   /**
    * Delete Client List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ���Action Form
         final ClientVO clientVO = ( ClientVO ) form;

         // ����ѡ�е�ID
         if ( clientVO.getSelectedIds() != null && !clientVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : clientVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��ClientVO
                  final ClientVO tempClientVO = clientService.getClientVOByClientId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // ����ɾ���ӿ�
                  tempClientVO.setModifyBy( getUserId( request, response ) );
                  tempClientVO.setModifyDate( new Date() );
                  clientService.deleteClient( tempClientVO );
               }
            }
         }

         // ���Selected IDs����Action
         clientVO.setSelectedIds( "" );
         clientVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Special Info HTML
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��ʼ������Tab Number
         int clientContactCount = 0;
         int clientInvoiceCount = 0;
         int clientContractCount = 0;
         int clientOrderHeaderCount = 0;

         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().isEmpty() )
         {
            // ��õ�ǰ����
            final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );

            // ʵ����ClientVO��Ϊ����ClientDTO������
            ClientVO clientVO = new ClientVO();
            clientVO.setAccountId( getAccountId( request, response ) );
            clientVO.setClientId( clientId );

            // �����������Ҷ�Ӧ��ClientDTO
            final ClientDTO clientDTO = clientService.getClientDTOByClientVO( clientVO );

            if ( clientDTO.getClientVO() != null )
            {
               // ˢ��VO���󣬳�ʼ�������б����ʻ�
               clientDTO.getClientVO().reset( null, request );
               clientDTO.getClientVO().setSubAction( VIEW_OBJECT );
            }

            // ���Client Contact Count����Tab Number��ʾ
            clientContactCount = clientDTO.getClientContactVOs().size();

            // ���Client Invoice Count����Tab Number��ʾ
            clientInvoiceCount = clientDTO.getClientInvoiceVOs().size();

            // ���Client Contract Count����Tab Number��ʾ
            clientContractCount = clientDTO.getClientContractVOs().size();

            // ��ʼ�� Service
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // �½��������ڲ�ѯ
            final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
            clientOrderHeaderVO.setClientId( clientId );
            clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );

            // ��ÿͻ���Ӧ�Ķ����б�
            final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );
            clientOrderHeaderCount = clientOrderHeaderVOs.size();

            request.setAttribute( "clientForm", clientDTO.getClientVO() );
            request.setAttribute( "clientOrderHeaderVOs", clientOrderHeaderVOs );
            request.setAttribute( "clientDTO", clientDTO );
         }

         request.setAttribute( "clientContactCount", clientContactCount );
         request.setAttribute( "clientOrderHeaderCount", clientOrderHeaderCount );
         request.setAttribute( "clientInvoiceCount", clientInvoiceCount );
         request.setAttribute( "clientContractCount", clientContractCount );

         // Ajax����
         return mapping.findForward( "managerSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Object Options Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-25
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

         // ��ȡClientId
         final String clientId = request.getParameter( "clientId" );

         // ��ù�ԱID
         final String employeeId = request.getParameter( "employeeId" );

         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ���ClientVO�б�
         final List< Object > clientVOs = clientService.getClientVOsByEmployeeId( employeeId );

         // ��ʼ��MappingVO�б�
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, ( ( ClientVO ) form ).getEmptyMappingVO() );

         if ( clientVOs != null && clientVOs.size() > 0 )
         {
            for ( Object clientVOObject : clientVOs )
            {
               final ClientVO clientVO = ( ClientVO ) clientVOObject;
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( clientVO.getClientId() );

               // ��������Ļ���
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( clientVO.getNameZH() );
               }
               // �����Ӣ�Ļ���
               else
               {
                  mappingVO.setMappingValue( clientVO.getNameEN() );
               }

               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "clientId", clientId ) );
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
    * Delete Object Ajax
    *	����Ajaxɾ���ͻ�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ��ȡ����
         //         final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
         final String clientId = request.getParameter( "clientId" );

         // ��ȡClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         clientVO.setModifyBy( getUserId( request, response ) );
         clientVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientService.delClientAndGroupRelationByClientId( clientVO );

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

   public ActionForward list_options_ajax_byAccountId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();

         final ClientVO clientVO = new ClientVO();

         clientVO.setAccountId( getAccountId( request, response ) );

         pagedListHolder.setObject( clientVO );

         clientService.getClientVOsByCondition( pagedListHolder, false );

         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         MappingVO mappingVO = null;

         ClientVO tempClientVO = null;
         if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               mappingVO = new MappingVO();
               tempClientVO = ( ClientVO ) object;
               // ��������Ļ���
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingId( tempClientVO.getClientId() );
                  mappingVO.setMappingValue( tempClientVO.getClientName() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingId( tempClientVO.getClientId() );
                  mappingVO.setMappingValue( tempClientVO.getClientName() );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         final String clientId = request.getParameter( "clientId" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "clientId", KANUtil.filterEmpty( clientId ) == null ? "0" : clientId ) );
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

   public ActionForward load_image_file( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // Send to client
         final String clientId = request.getParameter( "clientId" );
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         clientVO.setSubAction( request.getParameter( "subAction" ) );
         clientVO.reset( null, request );
         request.setAttribute( "clientVO", clientVO );
         return mapping.findForward( "imageFileExtend" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // for ���㵥header
   public ActionForward getClientVOAndOrderVOForOrderBillDetailHeader( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ�� Service
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // ��ʼ�� Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ȡClientId
         final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
         final String orderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderId" ) );
         final String monthly = KANUtil.decodeStringFromAjax( request.getParameter( "monthly" ) );

         // ��ʼ��ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );
         clientVO.reset( null, request );
         clientOrderHeaderVO.reset( null, request );
         clientOrderHeaderVO.setMonthly( monthly );
         request.setAttribute( "clientVO", clientVO );
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );
         return mapping.findForward( "orderBillDetailHeader" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
