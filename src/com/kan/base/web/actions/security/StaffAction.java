package com.kan.base.web.actions.security;

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
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.security.StaffRender;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeReportService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**
 * @author Kevin
 */
public class StaffAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_SEC_STAFF";

   /**
    * List StaffBaseViews by Jason format
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ��Staff Service
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // ��ʼ��Position Service
         final PositionService positionService = ( PositionService ) getService( "positionService" );
         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();

         List< Object > list = new ArrayList< Object >();

         if ( getRole( request, response ) != null )
         {
            // �����HRM��¼
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               StaffVO staffVO = new StaffVO();
               staffVO.setCorpId( getCorpId( request, response ) );
               staffVO.setAccountId( getAccountId( request, response ) );
               list = staffService.getActiveStaffVOsByCorpId( staffVO );
            }
            // �����HRO��¼
            else if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               list = staffService.getActiveStaffVOsByAccountId( getAccountId( request, response ) );
            }
         }

         if ( list != null && list.size() > 0 )
         {
            for ( Object obj : list )
            {
               final StaffVO staffVO = ( StaffVO ) obj;
               JSONObject jsonObj = new JSONObject();
               jsonObj.put( "id", staffVO.getStaffId() );

               final StringBuffer strb = new StringBuffer();
               strb.append( staffVO.getNameZH() + " - " + staffVO.getNameEN() );

               // ��ѯStaffVO��Ӧ��ְλ
               final List< Object > positionStaffRelationVOs = positionService.getPositionStaffRelationVOsByStaffId( staffVO.getStaffId() );

               if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
               {
                  strb.append( "���ѹ���ְλ��" );

                  int i = 0;
                  for ( Object object : positionStaffRelationVOs )
                  {
                     PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) object;

                     //                     final PositionVO positionVO = positionService.getPositionVOByPositionId( positionStaffRelationVO.getPositionId() );
                     final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( positionStaffRelationVO.getPositionId() );

                     if ( i > 0 )
                     {
                        strb.append( "��" + positionVO.getTitleZH() + "(" + ( "1".equals( positionStaffRelationVO.getStaffType() ) ? "��" : "��" ) + ")" + "-"
                              + positionVO.getTitleEN() );
                     }
                     else
                     {
                        strb.append( positionVO.getTitleZH() + "(" + ( "1".equals( positionStaffRelationVO.getStaffType() ) ? "��" : "��" ) + ")" + "-" + positionVO.getTitleEN() );
                     }

                     i++;
                  }
                  strb.append( "��" );
               }

               jsonObj.put( "name", strb.toString() );

               // b.userId
               jsonObj.put( "nameZH", staffVO.getNameZH() );
               jsonObj.put( "nameEN", staffVO.getNameEN() );
               array.add( jsonObj );
            }
         }

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

   /**
    * List staff
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
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // ���Action Form
         final StaffVO staffVO = ( StaffVO ) form;
         // ���SubAction
         final String subAction = getSubAction( form );

         // ����Զ�����������
         staffVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ����SubAction
         dealSubAction( staffVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder staffHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            staffHolder.setPage( page );
            // ���뵱ǰֵ����
            staffHolder.setObject( staffVO );
            // ����ҳ���¼����
            staffHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            staffService.getStaffVOsByCondition( staffHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( staffHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", staffHolder );

         // ����Return
         return dealReturn( accessAction, "listStaff", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_objectNew
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( StaffVO ) form ).setSubAction( CREATE_OBJECT );
      // Ĭ��״̬��ļ
      ( ( StaffVO ) form ).setStatus( "2" );

      // ��ת���½�����
      return mapping.findForward( "manageStaff" );
   }

   /**
    * to_objectModify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // ��õ�ǰ����
         final String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���������Ӧ����
         final StaffVO staffVO = staffService.getStaffVOByStaffId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         staffVO.reset( null, request );
         staffVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "staffForm", staffVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageStaff" );
   }

   /**
    * Add staff
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final StaffService staffService = ( StaffService ) getService( "staffService" );
            // ���ActionForm
            final StaffVO staffVO = ( StaffVO ) form;

            staffVO.setAccountId( getAccountId( request, response ) );
            staffVO.setCreateBy( getUserId( request, response ) );
            staffVO.setModifyBy( getUserId( request, response ) );
            staffVO.setCorpId( getCorpId( request, response ) );
            staffVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            // �����Զ���Column
            staffVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // �½�����
            staffService.insertStaff( staffVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, staffVO, Operate.ADD, staffVO.getStaffId(), null );

            // ˢ�³���
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
         // ���Form����
         ( ( StaffVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify staff
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final StaffService staffService = ( StaffService ) getService( "staffService" );

            // ��õ�ǰ����
            String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���������Ӧ����
            final StaffVO staffVO = staffService.getStaffVOByStaffId( staffId );
            staffVO.update( ( StaffVO ) form );
            staffVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            staffVO.setRemark1( saveDefineColumns( request, accessAction ) );
            staffVO.setRole( getRole( request, response ) );
            staffVO.setCorpId( getCorpId( request, response ) );
            staffVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );

            // �޸Ķ���
            staffService.updateStaff( staffVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, staffVO, Operate.MODIFY, staffVO.getStaffId(), null );

            // ˢ�³���
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
         // ���Form����
         ( ( StaffVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete staff
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
    * Delete staff list
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
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // ���Action Form
         final StaffVO staffVO = ( StaffVO ) form;

         // ����ѡ�е�ID
         if ( staffVO.getSelectedIds() != null && !staffVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : staffVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               staffVO.setStaffId( KANUtil.decodeStringFromAjax( selectedId ) );
               staffVO.setModifyBy( getUserId( request, response ) );
               staffVO.setModifyDate( new Date() );
               staffService.deleteStaff( staffVO );
            }

            insertlog( request, staffVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( staffVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         ( ( StaffVO ) form ).setSelectedIds( "" );
         ( ( StaffVO ) form ).setSubAction( SEARCH_OBJECT );
         // ˢ�³���
         constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
         constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
         constantsInit( "initPosition", getAccountId( request, response ) );
         constantsInit( "initBranch", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Object Html Thinking Management
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_html_thinking_management( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���PositionId
         final String encodePositionId = request.getParameter( "positionId" );
         final String positionId = Cryptogram.decodeString( URLDecoder.decode( encodePositionId != null ? encodePositionId : "", "UTF-8" ) );

         // Render����
         out.println( StaffRender.getStaffThinkingCombo( request ) + StaffRender.getStaffsByPositionId( request, positionId ) );
         out.flush();
         out.close();

         // Ajax��������ת
         return mapping.findForward( "" );
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
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final UserService userService = ( UserService ) getService( "userService" );
         // ��õ�ǰ����
         String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "staffId" ), "UTF-8" ) );
         // ��ʼ������
         StaffVO staffVO = new StaffVO();

         // �������
         if ( staffService.getStaffVOByStaffId( staffId ) != null )
         {
            staffVO = staffService.getStaffVOByStaffId( staffId );
            // ��ȡUser���󣬻��Username
            final UserVO userVO = userService.getUserVOByStaffId( staffId );
            if ( userVO != null && userVO.getUsername() != null )
            {
               staffVO.setUsername( userVO.getUsername() );
            }
         }

         // ���Tab Number
         final int positionCount = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionCountByStaffId( staffId );
         request.setAttribute( "positionCount", positionCount );
         request.setAttribute( "staffForm", staffVO );

         // Ajax����
         return mapping.findForward( "listSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * ��ȡ��¼����ϸ��Ϣ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         this.saveToken( request );
         final String staffId = getStaffId( request, response );
         final StaffService staffService = ( StaffService ) getService( "staffService" );

         final StaffVO staffVO = staffService.getStaffVOByStaffId( staffId );
         if ( staffVO != null && KANUtil.filterEmpty( staffVO.getEmployeeId() ) != null )
         {
            final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );
            final PagedListHolder employeeReportHolder = new PagedListHolder();
            final EmployeeReportVO condEmployeeReportVO = new EmployeeReportVO();
            condEmployeeReportVO.setAccountId( getAccountId( request, response ) );
            condEmployeeReportVO.setCorpId( getCorpId( request, response ) );
            condEmployeeReportVO.setEmployeeId( staffVO.getEmployeeId() );
            condEmployeeReportVO.setRt( "full" );
            final List< MappingVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, response ) );
            condEmployeeReportVO.setSalarys( itemVOs );
            employeeReportHolder.setPage( "0" );
            employeeReportHolder.setObject( condEmployeeReportVO );
            employeeReportService.getEmployeeReportVOsByCondition( employeeReportHolder, false );
            refreshHolder( employeeReportHolder, request );
            if ( employeeReportHolder.getSource().size() > 0 )
            {
               for(Object obj:employeeReportHolder.getSource()){
                 final EmployeeReportVO tmpVO = (EmployeeReportVO)obj;
                 if("3".equals(tmpVO.getContractStatus())){
                   request.setAttribute( "employeeReportVO", tmpVO );
                 }
               }
               request.setAttribute( "salarys", condEmployeeReportVO.getSalarys() );
            }
         }
         staffVO.reset( null, request );
         staffVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "staffForm", staffVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      if ( "100017".equalsIgnoreCase( getAccountId( request, response ) ) )
      {
         return mapping.findForward( "manageStaff_mobile4iclick" );
      }
      else
      {
         return mapping.findForward( "manageStaff_mobile" );
      }

   }

   public ActionForward modify_object_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final StaffService staffService = ( StaffService ) getService( "staffService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

            // ��õ�ǰ����
            String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���������Ӧ����
            final StaffVO staffVO = staffService.getStaffVOByStaffId( staffId );
            final StaffVO tempStaffVO = ( StaffVO ) form;
            if ( "100017".equalsIgnoreCase( getAccountId( request, response ) ) )
            {
               staffVO.update_mobile4iclick( tempStaffVO );
            }
            else
            {
               staffVO.update_mobile( tempStaffVO );
            }
            staffVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            staffVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // �޸Ķ���
            staffService.updateBaseStaff( staffVO );
            // �޸Ķ�Ӧ��employee��Ϣ
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
            if ( employeeVO != null )
            {
               // for json format
               employeeVO.setLocale( request.getLocale() );
               if ( "100017".equalsIgnoreCase( getAccountId( request, response ) ) )
               {
                  employeeVO.setMaritalStatus( staffVO.getMaritalStatus() );
                  employeeVO.setResidencyAddress( request.getParameter( "residencyAddress" ) );
                  employeeVO.setPersonalAddress( staffVO.getPersonalAddress() );
                  employeeVO.setMobile1( request.getParameter( "mobile1" ) );
                  employeeVO.setPhone1( request.getParameter( "phone1" ) );
                  //employeeVO.setEmail1( staffVO.getBizEmail() );
                  employeeVO.setEmail2( staffVO.getPersonalEmail() );
//                  employeeVO.setBankId( staffVO.getBankId() );
//                  employeeVO.setBankAccount( staffVO.getBankAccount() );
                  final String remark1 = employeeVO.getRemark1();
                  final JSONObject remarkObject = JSONObject.fromObject( remark1 );
                  if ( remarkObject.containsKey( "dushenzinvzhenglingquri" ) )
                  {
                     remarkObject.remove( "dushenzinvzhenglingquri" );
                     remarkObject.put( "dushenzinvzhenglingquri", request.getParameter( "dushenzinvzhenglingquri" ) );
                  }
                  if ( remarkObject.containsKey( "jiehunzhenglingquri" ) )
                  {
                     remarkObject.remove( "jiehunzhenglingquri" );
                     remarkObject.put( "jiehunzhenglingquri", request.getParameter( "jiehunzhenglingquri" ) );
                  }
                  employeeVO.setRemark1( remarkObject.toString() );
                  employeeService.updateEmployee( employeeVO );
               }
               else
               {
                  employeeVO.update_mobile( staffVO );
                  employeeService.updateBaseEmployee( employeeVO );
               }
            }

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            // ˢ�³���
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
         // ���Form����
         ( ( StaffVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify_mobile( mapping, form, request, response );
   }

   public ActionForward get_staff_nativeAPP( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String staffId = getStaffId( request, response );
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( staffId );
         final JSONObject jsonObject = JSONObject.fromObject( staffVO );
         out.print( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public ActionForward modify_staff_nativeAPP( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String staffId = getStaffId( request, response );
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( staffId );
         final String nameZH = KANUtil.decodeURLFromAjax( request.getParameter( "nameZH" ) );
         final String nameEN = KANUtil.decodeURLFromAjax( request.getParameter( "nameEN" ) );
         final String salutation = KANUtil.decodeURLFromAjax( request.getParameter( "salutation" ) );
         final String certificateNumber = KANUtil.decodeURLFromAjax( request.getParameter( "certificateNumber" ) );
         final String personalPhone = KANUtil.decodeURLFromAjax( request.getParameter( "personalPhone" ) );
         final String personalMobile = KANUtil.decodeURLFromAjax( request.getParameter( "personalMobile" ) );
         final String personalEmail = KANUtil.decodeURLFromAjax( request.getParameter( "personalEmail" ) );
         final String personalAddress = KANUtil.decodeURLFromAjax( request.getParameter( "personalAddress" ) );
         staffVO.setNameZH( nameZH );
         staffVO.setNameEN( nameEN );
         staffVO.setSalutation( salutation );
         staffVO.setCertificateNumber( certificateNumber );
         staffVO.setPersonalPhone( personalPhone );
         staffVO.setPersonalMobile( personalMobile );
         staffVO.setPersonalEmail( personalEmail );
         staffVO.setPersonalAddress( personalAddress );
         staffVO.setModifyBy( getUserId( request, response ) );

         // �޸Ķ���
         staffService.updateBaseStaff( staffVO );
         // �޸Ķ�Ӧ��Employee��Ϣ
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
         if ( employeeVO != null )
         {
            employeeVO.update_mobile( staffVO );
            employeeService.updateBaseEmployee( employeeVO );
         }

         out.print( "success" );
         out.flush();
         out.close();
         // ˢ�³���
         constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
         constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
         constantsInit( "initPosition", getAccountId( request, response ) );
         constantsInit( "initBranch", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }
}
