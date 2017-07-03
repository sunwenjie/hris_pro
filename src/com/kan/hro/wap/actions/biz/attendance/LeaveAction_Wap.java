package com.kan.hro.wap.actions.biz.attendance;

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

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;

/**
 *  �ֻ��ͻ��� ��� action
 * @author KW
 *
 */
public class LeaveAction_Wap extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   private static String accessAction = "HRO_BIZ_LEAVE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
      StringBuffer returnJson = new StringBuffer();
      try
      {
         final PrintWriter out = response.getWriter();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // ���Action Form
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ�����������
         leaveHeaderVO.setRemark1( generateDefineListSearches( request, accessAction ) );
         // ����SubAction
         dealSubAction( leaveHeaderVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder leavePagedListHolder = new PagedListHolder();
         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            leavePagedListHolder.setPage( page );
            // ���뵱ǰֵ����
            leavePagedListHolder.setObject( leaveHeaderVO );
            // ����ҳ���¼����
            leavePagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            leaveHeaderService.getLeaveHeaderVOsByCondition( leavePagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( leavePagedListHolder, request );
         }

         LeaveHeaderVO tempLeaveVO = null;
         returnJson.append( "{\"success\":\"success\",\"msg\":" );
         returnJson.append( "[" );
         int index = 0;
         int size = leavePagedListHolder.getSource().size();
         for ( Object o : leavePagedListHolder.getSource() )
         {
            tempLeaveVO = ( LeaveHeaderVO ) o;
            returnJson.append( "{\"itemId\":" + tempLeaveVO.getItemId() + ",\"leaveId\":\"" + tempLeaveVO.getLeaveHeaderId() + "\",\"estimateStartDate\":\""
                  + tempLeaveVO.getEstimateStartDate() + "\",\"estimateEndDate\":\"" + tempLeaveVO.getEstimateEndDate() + "\",\"status\":\"" + tempLeaveVO.getStatus() + "\"}" );
            if ( index < size - 1 )
            {
               returnJson.append( "," );
            }
            index++;
         }
         returnJson.append( "]," ).append( "\"other\":{" + getItemIds_status2Other( mapping, form, request, response ) );
         returnJson.append( ",\"totalCount\":" ).append( leavePagedListHolder.getHolderSize() );
         returnJson.append( ",\"nextPage\":" ).append( leavePagedListHolder.getNextPage() );
         returnJson.append( "}}" );
         out.print( returnJson.toString() );
         out.flush();
         out.close();
         return null;
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
      // ����Sub Action
      ( ( LeaveHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( LeaveHeaderVO ) form ).setStatus( LeaveHeaderVO.TRUE );

      if ( ( ( LeaveHeaderVO ) form ).getStatuses() != null && ( ( LeaveHeaderVO ) form ).getStatuses().size() > 0 )
      {
         for ( MappingVO mappingVO : ( ( LeaveHeaderVO ) form ).getStatuses() )
         {
            if ( mappingVO.getMappingId().equals( "1" ) )
            {
               final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
               mappingVOs.add( mappingVO );
               ( ( LeaveHeaderVO ) form ).setStatuses( mappingVOs );
            }
         }
      }
      String token_name = BaseAction.TOKEN_NAME;
      String token_value = ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );
      response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
      StringBuffer returnJson = new StringBuffer();
      returnJson.append( "{\"success\":\"success\",\"msg\":" );
      returnJson.append( "{" );
      returnJson.append( "\"token_name\":" ).append( "\"" ).append( token_name ).append( "\"," );
      returnJson.append( "\"token_value\":" ).append( "\"" ).append( token_value ).append( "\"" );
      returnJson.append( "}," ).append( "\"other\":\"\"" );
      returnJson.append( "}" );
      try
      {
         PrintWriter out = response.getWriter();
         out.print( returnJson.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return null;
   }

   /**
    * ������
    */
   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
            // ��õ�ǰFORM
            final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
            if ( "".equals( leaveHeaderVO.getActualStartDate() ) )
            {
               leaveHeaderVO.setActualStartDate( null );
            }
            if ( "".equals( leaveHeaderVO.getActualEndDate() ) )
            {
               leaveHeaderVO.setActualEndDate( null );
            }
            if ( !"".equals( leaveHeaderVO.getEstimateStartDate() ) )
            {
               leaveHeaderVO.setEstimateStartDate( leaveHeaderVO.getEstimateStartDate() + ":00.0" );
            }
            if ( !"".equals( leaveHeaderVO.getEstimateEndDate() ) )
            {
               leaveHeaderVO.setEstimateEndDate( leaveHeaderVO.getEstimateEndDate() + ":00.0" );
            }
            leaveHeaderVO.setEstimateLegalHours( "0" );
            leaveHeaderVO.setEstimateBenefitHours( "0" );
            leaveHeaderVO.setActualLegalHours( "0" );
            leaveHeaderVO.setActualBenefitHours( "0" );
            // �趨��ǰ�û�
            leaveHeaderVO.setAccountId( getAccountId( request, response ) );
            leaveHeaderVO.setCreateBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            // ������ӷ���
            leaveHeaderService.insertLeaveHeader( leaveHeaderVO );
         }
         // ���FORM
         ( ( LeaveHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ӳɹ����ѯһ�η���
      // ��ת�б����
      return list_object( mapping, form, request, response );
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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // ������ȡ�����
         final String leaveId = request.getParameter( "id" );
         // ���LeaveVO����
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         leaveHeaderVO.reset( null, request );
         // �����޸����
         leaveHeaderVO.setSubAction( VIEW_OBJECT );
         // ��ȡtoken
         String token_value = ( String ) CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME );
         String token_name = BaseAction.TOKEN_NAME;
         response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
         StringBuffer returnJson = new StringBuffer();
         returnJson.append( "{\"success\":\"success\",\"msg\":" );
         returnJson.append( "{" );
         returnJson.append( "\"leaveVO\":" ).append( JSONObject.fromObject( leaveHeaderVO ) ).append( "," );
         returnJson.append( "\"token_name\":" ).append( "\"" ).append( token_name ).append( "\"," );
         returnJson.append( "\"token_value\":" ).append( "\"" ).append( token_value ).append( "\"" );
         returnJson.append( "}," ).append( "\"other\":\"\"" );
         returnJson.append( "}" );
         PrintWriter out = response.getWriter();
         out.print( returnJson.toString() );
         out.flush();
         out.close();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
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
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
            // ������ȡ�����
            final String leaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���LeaveVO����
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
            // װ�ؽ��洫ֵ
            leaveHeaderVO.update( ( ( LeaveHeaderVO ) form ) );
            // ��ȡ��¼�û�
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            if ( "".equals( leaveHeaderVO.getActualStartDate() ) )
            {
               leaveHeaderVO.setActualStartDate( null );
            }
            if ( "".equals( leaveHeaderVO.getActualEndDate() ) )
            {
               leaveHeaderVO.setActualEndDate( null );
            }
            // �����޸ķ���
            leaveHeaderService.updateLeaveHeader( leaveHeaderVO );
            // ���ر༭�ɹ����
            //updateSuccess( request );

         }
         // ���FORM
         ( ( LeaveHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // ���Action Form
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
         // ����ѡ�е�ID

         // ����ɾ���ӿ�
         leaveHeaderVO.setLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyDate( new Date() );
         leaveHeaderService.deleteLeaveHeader( leaveHeaderVO );

         // ���Selected IDs����Action
         ( ( LeaveHeaderVO ) form ).setSelectedIds( "" );
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
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
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // ������������
         final String leaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���LeaveVO����
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
         // ��ȡ��¼�û�
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         // �����״̬��Ϊ 2 �ύ - ����׼
         leaveHeaderVO.setStatus( "2" );
         leaveHeaderService.updateLeaveHeader( leaveHeaderVO );
         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE );
         // ���FORM
         ( ( LeaveHeaderVO ) form ).reset();
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
         // �ض���list_object
         response.sendRedirect( request.getContextPath() + "/leaveAction.do?proc=list_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Ajax����
         return mapping.findForward( "listSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    *��ȡ ��Ŀ��״̬�������б�
    */
   public ActionForward initItemId_status( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
      StringBuffer returnJson = new StringBuffer();
      try
      {
         final PrintWriter out = response.getWriter();
         returnJson.append( "{\"success\":\"success\",\"msg\":{\"itemId\":" );
         LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
         leaveHeaderVO.reset( mapping, request );
         List< MappingVO > itemMappingVOs = leaveHeaderVO.getLeaveItems();
         //�����ѡ��
         itemMappingVOs.add( 0, new MappingVO( "0", "��ѡ��" ) );
         List< MappingVO > statusMappingVOs = leaveHeaderVO.getStatuses();
         returnJson.append( JSONArray.fromObject( itemMappingVOs ).toString() );
         returnJson.append( "," );
         returnJson.append( "\"statuses\":" );
         returnJson.append( JSONArray.fromObject( statusMappingVOs ).toString() );
         returnJson.append( "},\"other\":\"\"}" );
         out.print( returnJson.toString() );
      }
      catch ( Exception e )
      {

      }
      return null;
   }

   /**
    * for  list_object  other
    */
   public String getItemIds_status2Other( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      StringBuffer returnJson = new StringBuffer();
      returnJson.append( "\"leaveItems\":" );
      LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.reset( mapping, request );
      List< MappingVO > itemMappingVOs = leaveHeaderVO.getLeaveItems();
      List< MappingVO > statusMappingVOs = leaveHeaderVO.getStatuses();
      returnJson.append( "[" );
      int index = 0;
      int size = itemMappingVOs.size();
      returnJson.append( "\"" ).append( "0" ).append( "\"" ).append( "," );
      returnJson.append( "\"" ).append( "��ѡ��" ).append( "\"" ).append( "," );
      for ( MappingVO i : itemMappingVOs )
      {
         returnJson.append( "\"" ).append( i.getMappingId() ).append( "\"" ).append( "," );
         returnJson.append( "\"" ).append( i.getMappingValue() ).append( "\"" );
         if ( index < size - 1 )
         {
            returnJson.append( "," );
         }
         index++;
      }
      returnJson.append( "]," ).append( "\"statuses\":" );
      returnJson.append( "[" );
      index = 0;
      size = statusMappingVOs.size();

      for ( MappingVO s : statusMappingVOs )
      {
         returnJson.append( "\"" ).append( s.getMappingId() ).append( "\"" ).append( "," );
         returnJson.append( "\"" ).append( s.getMappingValue() ).append( "\"" );
         if ( index < size - 1 )
         {
            returnJson.append( "," );
         }
         index++;
      }
      returnJson.append( "]" );
      return returnJson.toString();
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
