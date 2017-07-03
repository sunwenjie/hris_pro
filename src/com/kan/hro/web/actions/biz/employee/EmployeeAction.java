/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionDetailService;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.task.SyncTask;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.security.PositionRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeBaseView;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeCertificationService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeEducationService;
import com.kan.hro.service.inf.biz.employee.EmployeeEmergencyService;
import com.kan.hro.service.inf.biz.employee.EmployeeLanguageService;
import com.kan.hro.service.inf.biz.employee.EmployeeLogService;
import com.kan.hro.service.inf.biz.employee.EmployeeMembershipService;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.employee.EmployeeSkillService;
import com.kan.hro.service.inf.biz.employee.EmployeeUserService;
import com.kan.hro.service.inf.biz.employee.EmployeeWorkService;

/**
 * @author Kevin Jin
 */

public class EmployeeAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_EMPLOYEE";

   // ��ǰAction��Ӧ��Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( !KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return "HRO_BIZ_EMPLOYEE";
      }
      else
      {
         return "HRO_BIZ_EMPLOYEE_IN_HOUSE";
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
         // ��ȡEmployeeId
         final String employeeId = request.getParameter( "employeeId" );

         // ��ʼ�� Service
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final PagedListHolder employeeHolder = new PagedListHolder();
         EmployeeVO employeeVO = ( EmployeeVO ) form;
         employeeVO.setEmployeeId( employeeId );
         employeeHolder.setObject( employeeVO );
         setDataAuth( request, response, employeeVO );
         employeeService.getEmployeeVOsByCondition( employeeHolder, false );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();
         if ( employeeHolder != null && employeeHolder.getSource() != null && employeeHolder.getSource().size() > 0 )
         {
            refreshHolder( employeeHolder, request );
            jsonObject = JSONObject.fromObject( ( EmployeeVO ) ( employeeHolder.getSource().get( 0 ) ) );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "errorMsg", ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ? "Ա��" : "��Ա" ) + "ID��Ч��" );
            jsonObject.put( "success", "false" );
         }
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         out.println( jsonObject.toString() );
         out.flush();
         out.close();

         //         // ��ʼ��PrintWrite����
         //         final PrintWriter out = response.getWriter();
         //
         //         // ��ʼ�� Service
         //         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         //
         //         // ��ȡEmployeeId
         //         final String employeeId = request.getParameter( "employeeId" );
         //
         //         // ��ȡ�Ƿ��ǿ���ģ��(��ٺͼӰ�)
         //         final String isAttendance = request.getParameter( "attendance" );
         //
         //         // ��ȡsubAction
         //         final String subAction = request.getParameter( "subAction" );
         //
         //         // ��ʼ�� JSONObject
         //         JSONObject jsonObject = new JSONObject();
         //
         //         boolean pass = true;
         //
         //         // �Ƿ����ΪEmployeeId���
         //         if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( isAttendance ) != null )
         //         {
         //            pass = false;
         //
         //            // ����ǲ鿴
         //            if ( subAction.trim().equalsIgnoreCase( VIEW_OBJECT ) )
         //            {
         //               pass = true;
         //            }
         //            else
         //            {
         //               pass = LeaveHeaderAction.checkIsPass( request, employeeId );
         //            }
         //         }
         //
         //         if ( !pass )
         //         {
         //            jsonObject.put( "errorMsg", "��HRְ��ֻ��Ϊ�Լ���ͬһ����Ա������" + ( ( KANUtil.filterEmpty( isAttendance ) != null && isAttendance.trim().equals( "leave" ) ) ? "���" : "�Ӱ�" ) + "��" );
         //         }
         //         else
         //         {
         //            // ��ʼ��EmployeeVO
         //            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         //            if ( employeeVO != null && employeeVO.getAccountId() != null && employeeVO.getAccountId().equals( getAccountId( request, response ) ) )
         //            {
         //               employeeVO.reset( mapping, request );
         //               jsonObject = JSONObject.fromObject( employeeVO );
         //               jsonObject.put( "success", "true" );
         //            }
         //            else
         //            {
         //               jsonObject.put( "errorMsg", ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ? "Ա��" : "��Ա" ) + "ID��Ч��" );
         //               jsonObject.put( "success", "false" );
         //            }
         //         }

         //          Send to front
         //         out.println( jsonObject.toString() );
         //         out.flush();
         //         out.close();
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
    * ��Ա��������ģ̬��
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
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         // ���Action Form 
         final EmployeeVO employeeVO = ( EmployeeVO ) form;
         // ��ȡ�Ƿ�����Ȩ�޿���
         final String ignoreDataAuth = request.getParameter( "ignoreDataAuth" );
         // forwardҳ��
         final String forward = request.getParameter( "forward" );
         // ��������Ȩ��
         if ( null == ignoreDataAuth || "".equalsIgnoreCase( ignoreDataAuth ) || new Boolean( ignoreDataAuth ) == false )
         {
            setDataAuth( request, response, employeeVO );
         }

         // ����
         decodedObject( employeeVO );
         if ( KANUtil.filterEmpty( employeeVO.getRemark1() ) != null )
         {
            employeeVO.setRemark1( "%\"jiancheng\":\"" + employeeVO.getRemark1() + "%" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder employeeHolder = new PagedListHolder();

         // ���뵱ǰҳ
         employeeHolder.setPage( page );
         // ���뵱ǰֵ����
         employeeHolder.setObject( employeeVO );
         // ����ҳ���¼����
         employeeHolder.setPageSize( listPageSize_popup );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeService.getEmployeeVOsByCondition( employeeHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeHolder", employeeHolder );

         // ����д��role
         request.setAttribute( "role", getRole( request, response ) );

         // Ajax Table���ã�ֱ�Ӵ��� JSP
         return mapping.findForward( forward );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List EmployeeBaseViews by Jason format
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
         // ���Ҫ���������
         final String conditionName = request.getParameter( "conditionName" );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Employee Service
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // ��ʼ�� JSONArray ###
         final JSONArray array = new JSONArray();

         // ��ʼ����������
         final EmployeeBaseView employeeBaseView = new EmployeeBaseView();
         employeeBaseView.setAccountId( getAccountId( request, response ) );
         if ( conditionName != null && !conditionName.trim().equals( "" ) )
         {
            employeeBaseView.setConditions( conditionName );
         }

         array.addAll( employeeService.getEmployeeBaseViewsByCondition( employeeBaseView ) );

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
    * List employee
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
      final String accessAction = getAccessAction( request, response );

      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // ���Action Form
         final EmployeeVO employeeVO = ( EmployeeVO ) form;

         // HR_Service��¼��IN_House��¼
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeVO );
         }
         // �ͻ���¼
         else if ( BaseAction.getRole( request, response ).equalsIgnoreCase( KANConstants.ROLE_CLIENT ) )
         {
            employeeVO.setClientId( BaseAction.getClientId( request, response ) );
         }
         // ��Ա��¼
         else if ( BaseAction.getRole( request, response ).equalsIgnoreCase( KANConstants.ROLE_EMPLOYEE ) )
         {
            employeeVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
         }

         // ���SubAction
         final String subAction = getSubAction( form );

         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( employeeVO.getSortColumn() == null || employeeVO.getSortColumn().isEmpty() )
         {
            employeeVO.setSortColumn( "employeeId" );
            employeeVO.setSortOrder( "desc" );
         }

         // ����Զ�����������
         employeeVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ����SubAction
         dealSubAction( employeeVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder employeeHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            employeeHolder.setPage( page );
            // ����Object
            employeeHolder.setObject( employeeVO );
            // ����ҳ���¼����
            employeeHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            employeeService.getEmployeeVOsByCondition( employeeHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( employeeHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", employeeHolder );
         request.setAttribute( "role", getRole( request, response ) );
         request.setAttribute( "accessAction", getAccessAction( request, response ) );

         // ����Return
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            return dealReturn( accessAction, "listEmployeeInHouse", mapping, form, request, response );
         }
         else
         {
            return dealReturn( accessAction, "listEmployee", mapping, form, request, response );
         }
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
      final EmployeeVO employeeVO = ( ( EmployeeVO ) form );

      final String newType = request.getParameter( "newType" );

      // ��ʼ��PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );
      // ����Sub Action
      employeeVO.setSubAction( CREATE_OBJECT );
      // Ĭ��֤������ - ���֤
      employeeVO.setCertificateType( "1" );
      // Ĭ�Ϲ��� - �й�
      employeeVO.setNationNality( "1" );
      // Ĭ������˾�ҵ���֤  -  ��
      employeeVO.setHasForeignerWorkLicence( "2" );
      // Ĭ�Ͼ�ס֤ - ��
      employeeVO.setHasResidenceLicence( "2" );
      // Ĭ��״̬ - ��ļ
      employeeVO.setStatus( "2" );
      // Ĭ�������ˣ���������
      if ( positionVO != null )
      {
         employeeVO.setBranch( positionVO.getBranchId() );
         employeeVO.setOwner( positionVO.getPositionId() );
      }
      // �춯ԭ�� �½�
      // employeeVO.setRemark3( "1" );
      // ��ת�������½�����
      if ( KANUtil.filterEmpty( newType ) != null )
      {
         return mapping.findForward( "manageEmployeeHRQuick" );
      }

      // ��ת���½�����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return mapping.findForward( "manageEmployeeInHouse" );
      }
      else
      {
         return mapping.findForward( "manageEmployee" );
      }
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
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         // ��õ�ǰ����
         String id = "";

         if ( request.getParameter( "id" ) != null )
         {
            id = KANUtil.decodeString( request.getParameter( "id" ) );
         }
         else
         {
            id = KANUtil.decodeString( ( ( EmployeeVO ) form ).getEmployeeId() );
         }

         //��Ա��½ id Ϊ��ȥcooki ȡֵ
         String role = null;
         if ( StringUtils.isBlank( id ) )
         {
            id = EmployeeSecurityAction.getEmployeeId( request, response );
            role = EmployeeSecurityAction.getRole( request, response );
         }
         // ���������Ӧ����
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeVO.reset( null, request );

         // ���City Id�������Province Id
         if ( employeeVO.getResidencyCityId() != null && !employeeVO.getResidencyCityId().trim().equals( "" ) && !employeeVO.getResidencyCityId().trim().equals( "0" ) )
         {
            employeeVO.setResidencyProvinceId( KANConstants.LOCATION_DTO.getCityVO( employeeVO.getResidencyCityId(), request.getLocale().getLanguage() ).getProvinceId() );
         }
         employeeVO.setSubAction( VIEW_OBJECT );
         if ( StringUtils.isNotBlank( role ) )
         {
            employeeVO.setRole( role );
         }

         request.setAttribute( "employeeForm", employeeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return mapping.findForward( "manageEmployeeInHouse" );
      }
      else
      {
         return mapping.findForward( "manageEmployee" );
      }
   }

   /**
    * Add employee
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
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // ���ActionForm
            final EmployeeVO employeeVO = ( EmployeeVO ) form;

            // ��֤���֤
            checkCertificateNumber( employeeVO.getCertificateNumber(), null, request );
            // ���Ϸ������֤��ת����ҳ��
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "certificationErrorMsg" ) ) != null )
            {
               return to_objectNew( mapping, employeeVO, request, response );
            }

            // ��֤�û���
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               if ( checkUsername( employeeVO.getEmployeeId(), employeeVO.getUsername(), request ) > 0 )
               {
                  return to_objectNew( mapping, employeeVO, request, response );
               }
            }
            else if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               if ( !checkEmail( ( ( EmployeeVO ) form ).getEmail1(), ( ( EmployeeVO ) form ).getUsername(), request ) )
               {
                  return to_objectModify( mapping, form, request, response );
               }

               if ( checkUsernameExist( employeeVO.getEmployeeId(), employeeVO.getUsername(), getAccountId( request, response ), request ) )
               {
                  return to_objectNew( mapping, employeeVO, request, response );
               }
            }

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

               employeeVO.setPhoto( imageFileString.length() > 0 ? imageFileString.substring( 0, imageFileString.length() - 2 ) : null );
            }

            employeeVO.setAccountId( getAccountId( request, response ) );
            employeeVO.setCreateBy( getUserId( request, response ) );
            employeeVO.setModifyBy( getUserId( request, response ) );
            employeeVO.setCorpId( getCorpId( request, response ) );
            // �����Զ���Column
            employeeVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );
            final String[] positionIdArray = request.getParameterValues( "positionIdArray" );
            employeeVO.setPositionIdArray( positionIdArray );
            employeeVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            //fixPhone2( employeeVO, request.getParameter( "hidePhone2" ) );
            // �½�����
            final int result = employeeService.insertEmployee( employeeVO );
            insertlog( request, employeeVO, Operate.ADD, employeeVO.getEmployeeId(), null );

            // ˢ�»���
            if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
            {
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

               if ( staffVO != null )
               {
                  // ˢ�»���
                  //                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  //                  constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  //                  constantsInit( "initPosition", getAccountId( request, response ) );
                  //                  constantsInit( "initBranch", getAccountId( request, response ) );
                  final SyncTask syncTask = new SyncTask();
                  syncTask.addTask( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  syncTask.addTask( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  syncTask.addTask( "initPosition", getAccountId( request, response ) );
                  syncTask.addTask( "initBranch", getAccountId( request, response ) );
                  syncTask.start();
               }
            }

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               employeeVO.reset();
               return list_object( mapping, form, request, response );
            }
            else
            {
               // ����Add�ɹ����
               success( request, MESSAGE_TYPE_ADD );
            }

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + "&employeeId=" + employeeVO.getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            String forwardToModify = "employeeAction.do?proc=to_objectModify&id=" + employeeVO.getEncodedId();
            request.getRequestDispatcher( forwardToModify ).forward( request, response );

            return null;
         }

         // ���Form����
         ( ( EmployeeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Add Employee by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final SocialBenefitSolutionDetailService socialBenefitSolutionDetailService = ( SocialBenefitSolutionDetailService ) getService( "socialBenefitSolutionDetailService" );

         // ��ȡOrderId
         final String orderId = request.getParameter( "orderId" );
         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );

         // ���ActionForm
         final EmployeeVO employeeVO = ( EmployeeVO ) form;
         // Ajax����
         decodedObject( employeeVO );
         employeeVO.setAccountId( getAccountId( request, response ) );
         employeeVO.setCreateBy( getUserId( request, response ) );
         employeeVO.setModifyBy( getUserId( request, response ) );
         employeeVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );

         // �����Զ���Column#
         employeeVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

         // �½�����
         employeeService.insertEmployee( employeeVO );

         // ��ʼ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setClientId( clientOrderHeaderVO.getClientId() );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setEmployeeId( employeeVO.getEmployeeId() );
         employeeContractVO.setEmployeeNameZH( employeeVO.getNameZH() );
         employeeContractVO.setEmployeeNameEN( employeeVO.getNameEN() );
         employeeContractVO.setOrderId( orderId );
         employeeContractVO.setEntityId( clientOrderHeaderVO.getEntityId() );
         employeeContractVO.setBusinessTypeId( clientOrderHeaderVO.getBusinessTypeId() );
         employeeContractVO.setTemplateId( "0" );
         employeeContractVO.setCalendarId( "0" );
         employeeContractVO.setShiftId( "0" );
         employeeContractVO.setAttendanceCheckType( "0" );
         employeeContractVO.setIncomeTaxBaseId( "0" );
         employeeContractVO.setIncomeTaxRangeHeaderId( "0" );
         employeeContractVO.setSickLeaveSalaryId( "0" );
         employeeContractVO.setApplyOTFirst( "0" );
         employeeContractVO.setWorkdayOTItemId( "0" );
         employeeContractVO.setWeekendOTItemId( "0" );
         employeeContractVO.setHolidayOTItemId( "0" );
         employeeContractVO.setFlag( "2" );

         if ( KANUtil.filterEmpty( getRole( request, response ) ) != null && KANUtil.filterEmpty( getRole( request, response ) ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // ��ʼ��PositionVO
            final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

            // Ĭ�������ˣ���������
            if ( positionVO != null )
            {
               employeeContractVO.setBranch( positionVO.getBranchId() );
               employeeContractVO.setOwner( positionVO.getPositionId() );
            }
         }
         else if ( KANUtil.filterEmpty( getRole( request, response ) ) != null && KANUtil.filterEmpty( getRole( request, response ) ).equals( KANConstants.ROLE_HR_SERVICE ) )
         {
            employeeContractVO.setBranch( clientOrderHeaderVO.getBranch() );
            employeeContractVO.setOwner( clientOrderHeaderVO.getOwner() );
         }
         else
         {
            employeeContractVO.setBranch( "0" );
            employeeContractVO.setOwner( "0" );
         }

         employeeContractVO.setSettlementBranch( "0" );
         employeeContractVO.setCreateBy( getUserId( request, null ) );
         employeeContractVO.setModifyBy( getUserId( request, null ) );

         final String tempDate = KANUtil.formatDate( new Date(), "yyyyMM" );
         final String tempEmployeeNameEN = KANUtil.filterEmpty( employeeVO.getNameEN() ) != null ? " (" + employeeVO.getNameEN() + ") " : "";
         employeeContractVO.setNameZH( ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) ? "�Ͷ���ͬ��"
               + employeeVO.getNameZH() + "��- " + tempDate
               : "����Э��" + tempEmployeeNameEN + " - " + tempDate );

         // ����ʱ�䣨Ԥ�ƣ���Ĭ�ϵ�ǰ��ʼ����
         final Date tempEndDate = KANUtil.getDate( new Date(), 3, 0, -1 );

         // Ĭ�Ͽ�ʼʱ��ͽ���ʱ��
         employeeContractVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
         employeeContractVO.setEndDate( KANUtil.formatDate( tempEndDate, "yyyy-MM-dd" ) );

         // ���ClientOrderHeaderVO��Ϊ�գ����Һ�ͬ����ʱ�䲻����������
         if ( clientOrderHeaderVO != null && KANUtil.getDays( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ) ) < KANUtil.getDays( tempEndDate ) )
         {
            employeeContractVO.setEndDate( KANUtil.formatDate( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ), "yyyy-MM-dd" ) );
         }

         employeeContractVO.setEmployStatus( "1" );
         employeeContractVO.setStatus( "1" );
         employeeContractVO.setLocked( "2" );
         employeeContractService.insertEmployeeContract( employeeContractVO );
         // ��ӻ�������
         final String salaryBase = request.getParameter( "salaryBase" );
         if ( KANUtil.filterEmpty( salaryBase ) != null && !"0".equals( salaryBase ) )
         {
            final EmployeeContractSalaryVO employeeContractSalaryVO = new EmployeeContractSalaryVO();
            employeeContractSalaryVO.setContractId( employeeContractVO.getContractId() );
            employeeContractSalaryVO.setItemId( "1" );
            employeeContractSalaryVO.setSalaryType( "1" );
            employeeContractSalaryVO.setDivideType( "2" );
            employeeContractSalaryVO.setExcludeDivideItemIds( "{41,42,43,44,45,46,47}" );
            employeeContractSalaryVO.setBase( salaryBase );
            employeeContractSalaryVO.setBaseFrom( "0" );
            employeeContractSalaryVO.setCycle( "1" );
            employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
            employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
            employeeContractSalaryVO.setResultCap( "0" );
            employeeContractSalaryVO.setResultFloor( "0" );
            employeeContractSalaryVO.setShowToTS( "2" );
            employeeContractSalaryVO.setProbationUsing( "2" );
            employeeContractSalaryVO.setStatus( "1" );
            employeeContractSalaryVO.setRemark1( "{}" );
            employeeContractSalaryVO.setCreateBy( getUserId( request, response ) );
            employeeContractSalaryVO.setCreateDate( new Date() );
            employeeContractSalaryService.insertEmployeeContractSalary( employeeContractSalaryVO );
         }

         final String sbItem = request.getParameter( "sbItem" );
         // ����籣
         if ( KANUtil.filterEmpty( sbItem ) != null && !"0".equals( sbItem ) )
         {
            final List< Object > sbSolutionObjects = socialBenefitSolutionDetailService.getSocialBenefitSolutionDetailVOsByHeaderId( sbItem );
            final String baseCompany = request.getParameter( "baseCompany" );
            final String basePersonal = request.getParameter( "basePersonal" );
            List< String > baseCompanyList = new ArrayList< String >();
            List< String > basePersonalList = new ArrayList< String >();
            List< String > solutionDetailIdList = new ArrayList< String >();

            // ������ڻ�����Χ�ڣ������
            for ( Object object : sbSolutionObjects )
            {
               final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) object;

               final String companyFloor = socialBenefitSolutionDetailVO.getCompanyFloor();
               final String companyCap = socialBenefitSolutionDetailVO.getCompanyCap();
               final String personalFloor = socialBenefitSolutionDetailVO.getPersonalFloor();
               final String personalCap = socialBenefitSolutionDetailVO.getPersonalCap();

               // ����������û���
               if ( KANUtil.filterEmpty( baseCompany ) == null )
               {
                  baseCompanyList.add( companyFloor );
               }
               else
               {
                  if ( Double.parseDouble( baseCompany ) >= Double.parseDouble( companyFloor ) && Double.parseDouble( baseCompany ) <= Double.parseDouble( companyCap ) )
                  {
                     baseCompanyList.add( baseCompany );
                  }
                  else if ( Double.parseDouble( baseCompany ) < Double.parseDouble( companyFloor ) )
                  {
                     baseCompanyList.add( companyFloor );
                  }
                  else if ( Double.parseDouble( baseCompany ) > Double.parseDouble( companyCap ) )
                  {
                     baseCompanyList.add( companyCap );
                  }
               }
               if ( KANUtil.filterEmpty( basePersonal ) == null )
               {
                  baseCompanyList.add( personalFloor );
               }
               else
               {
                  if ( Double.parseDouble( basePersonal ) >= Double.parseDouble( personalFloor ) && Double.parseDouble( basePersonal ) <= Double.parseDouble( personalCap ) )
                  {
                     basePersonalList.add( basePersonal );
                  }
                  else if ( Double.parseDouble( basePersonal ) < Double.parseDouble( personalFloor ) )
                  {
                     basePersonalList.add( personalFloor );
                  }
                  else if ( Double.parseDouble( basePersonal ) > Double.parseDouble( personalCap ) )
                  {
                     basePersonalList.add( personalCap );
                  }
               }

               solutionDetailIdList.add( socialBenefitSolutionDetailVO.getDetailId() );
            }

            final String[] baseCompanyArray = ( String[] ) baseCompanyList.toArray( new String[ baseCompanyList.size() ] );
            final String[] basePersonalArray = ( String[] ) basePersonalList.toArray( new String[ basePersonalList.size() ] );
            final String[] solutionDetailIdArray = ( String[] ) solutionDetailIdList.toArray( new String[ solutionDetailIdList.size() ] );

            final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
            employeeContractSBVO.setAccountId( getAccountId( request, response ) );
            employeeContractSBVO.setContractId( employeeContractVO.getContractId() );
            employeeContractSBVO.setSbSolutionId( sbItem );
            employeeContractSBVO.setBaseCompanyArray( baseCompanyArray );
            employeeContractSBVO.setBasePersonalArray( basePersonalArray );
            employeeContractSBVO.setSolutionDetailIdArray( solutionDetailIdArray );
            employeeContractSBVO.setPersonalSBBurden( "2" );
            employeeContractSBVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
            employeeContractSBVO.setNeedMedicalCard( "2" );
            employeeContractSBVO.setNeedSBCard( "2" );
            employeeContractSBVO.setStatus( "0" );
            employeeContractSBVO.setCreateBy( getUserId( request, response ) );
            employeeContractSBVO.setModifyBy( getUserId( request, response ) );
            employeeContractSBService.insertEmployeeContractSB( employeeContractSBVO );
         }

         final String cbItem = request.getParameter( "cbItem" );
         // ����̱�
         if ( KANUtil.filterEmpty( cbItem ) != null && !"0".equals( cbItem ) )
         {
            final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
            employeeContractCBVO.setAccountId( getAccountId( request, response ) );
            employeeContractCBVO.setContractId( employeeContractVO.getContractId() );
            employeeContractCBVO.setSolutionId( cbItem );
            employeeContractCBVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
            employeeContractCBVO.setStatus( "0" );
            employeeContractCBVO.setCreateBy( getUserId( request, response ) );
            employeeContractCBVO.setModifyBy( getUserId( request, response ) );
            employeeContractCBService.insertEmployeeContractCB( employeeContractCBVO );
         }

         return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Modify employee
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-28
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // ��õ�ǰ����
            String employeeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
            // ��֤���֤
            checkCertificateNumber( ( ( EmployeeVO ) form ).getCertificateNumber(), ( ( EmployeeVO ) form ).getEmployeeId(), request );

            // ���Ϸ������֤��ת����ҳ��
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "certificationErrorMsg" ) ) != null )
            {
               employeeVO.reset();
               return to_objectModify( mapping, form, request, response );
            }

            // ��֤�û���
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               if ( checkUsername( employeeVO.getEmployeeId(), ( ( EmployeeVO ) form ).getUsername(), request ) > 0 )
               {
                  employeeVO.reset();
                  return to_objectModify( mapping, form, request, response );
               }
            }
            else if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
            {
               if ( !checkEmail( ( ( EmployeeVO ) form ).getEmail1(), ( ( EmployeeVO ) form ).getUsername(), request ) )
               {
                  return to_objectModify( mapping, form, request, response );
               }

               if ( checkUsernameExist( employeeVO.getEmployeeId(), ( ( EmployeeVO ) form ).getUsername(), getAccountId( request, response ), request ) )
               {
                  return to_objectModify( mapping, form, request, response );
               }
            }

            employeeVO.update( ( EmployeeVO ) form );
            employeeVO.setModifyDate( new Date() );
            employeeVO.setModifyBy( getUserId( request, response ) );
            employeeVO.reset( mapping, request );
            //fixPhone2( employeeVO, request.getParameter( "hidePhone2" ) );
            // ��Ա��½�Ĳ���
            if ( getRole( request, response ).equals( KANConstants.ROLE_EMPLOYEE ) )
            {
               //�����޸���
               ( ( EmployeeVO ) form ).setOwner( employeeVO.getOwner() );
               ( ( EmployeeVO ) form ).setStatus( employeeVO.getStatus() );
               ( ( EmployeeVO ) form ).setBranch( employeeVO.getOwner() );
            }

            // ��Ƭ
            final String[] imageFileArray = request.getParameterValues( "imageFileArray" );
            String imageFileString = "";

            if ( imageFileArray != null && imageFileArray.length > 0 )
            {
               for ( String imageFile : imageFileArray )
               {
                  if ( KANUtil.filterEmpty( imageFileString ) != null )
                  {
                     imageFileString = imageFileString + "##";
                  }

                  imageFileString = imageFileString + imageFile;
               }
               employeeVO.setPhoto( imageFileString );
            }

            // �����Զ���Column
            employeeVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );
            final String[] positionIdArray = request.getParameterValues( "positionIdArray" );
            employeeVO.setPositionIdArray( positionIdArray );

            // �޸Ķ���
            if ( employeeService.updateEmployee( employeeVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );

               insertlog( request, employeeVO, Operate.SUBMIT, employeeVO.getEmployeeId(), null );
            }
            else
            {
               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeVO, Operate.MODIFY, employeeVO.getEmployeeId(), null );
            }

            // ˢ�»���
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );

               if ( staffVO != null )
               {
                  //                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  //                  constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  //                  constantsInit( "initPosition", getAccountId( request, response ) );
                  //                  constantsInit( "initBranch", getAccountId( request, response ) );
                  final SyncTask syncTask = new SyncTask();
                  syncTask.addTask( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  syncTask.addTask( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  syncTask.addTask( "initPosition", getAccountId( request, response ) );
                  syncTask.addTask( "initBranch", getAccountId( request, response ) );
                  syncTask.start();
               }
            }
         }

         // ���Form����
         ( ( EmployeeVO ) form ).reset();
         ( ( EmployeeVO ) form ).setSortColumn( "employeeId" );
         ( ( EmployeeVO ) form ).setSortOrder( "desc" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify_ Object Skill
    *  �޸Ĺ�Ա/Ա������
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    * Add By��Jack  
    * Add Date��2014-3-8  
    */
   public ActionForward modify_object_skill( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

            // ��á�������ʾ�� SubAction����ԱID EmployeeID
            String subAction = request.getParameter( "subAction" );
            final String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeId" ) );

            // ��Ҫ����
            if ( subAction != null )
            {
               // ���Ϊ����
               if ( subAction.trim().equals( "addObjects" ) )
               {
                  final String[] skillIdArray = request.getParameterValues( "manageSkill_skillIdArray" );
                  if ( skillIdArray != null && skillIdArray.length > 0 )
                  {
                     for ( String skillId : skillIdArray )
                     {
                        final EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
                        employeeSkillVO.setSkillId( skillId );
                        employeeSkillVO.setEmployeeId( employeeId );
                        employeeSkillVO.setCreateBy( getUserId( request, response ) );
                        employeeSkillVO.setModifyBy( getUserId( request, response ) );
                        employeeSkillService.insertEmployeeSkill( employeeSkillVO );
                     }
                     // ���ء���Ӽ��ܳɹ������
                     success( request, null, "�ɹ���Ӽ��� ��" );
                  }
               }
               // ���Ϊɾ��
               else if ( subAction.trim().equals( "deleteObject" ) )
               {
                  final String skillId = request.getParameter( "skillId" );
                  // ��ʼ����ѯ����
                  EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
                  employeeSkillVO.setSkillId( skillId );
                  employeeSkillVO.setEmployeeId( employeeId );
                  final List< Object > employeeSkillVOs = employeeSkillService.getEmployeeSkillVOsByCondition( employeeSkillVO );

                  // ɾ��EmployeeSkillVO
                  if ( employeeSkillVOs != null && employeeSkillVOs.size() > 0 )
                  {
                     employeeSkillVO = ( EmployeeSkillVO ) employeeSkillVOs.get( 0 );
                     employeeSkillVO.setModifyBy( getUserId( request, response ) );
                     employeeSkillVO.setModifyDate( new Date() );
                     employeeSkillVO.setDeleted( "2" );
                     employeeSkillService.updateEmployeeSkill( employeeSkillVO );
                  }

                  // ���ء�ɾ�����ܳɹ������
                  success( request, null, "ɾ�����ܳɹ� ��" );
               }
            }

         }
         // ���Form����
         ( ( EmployeeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employee
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
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = new EmployeeVO();
         // ��õ�ǰ����
         String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeId" ) );

         // ɾ��������Ӧ����
         employeeVO.setEmployeeId( employeeId );
         employeeVO.setModifyBy( getUserId( request, response ) );
         // ����ˢ�»���
         StaffService staffService = ( StaffService ) getService( "staffService" );
         StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );
         employeeService.deleteEmployee( employeeVO );
         insertlog( request, null, Operate.DELETE, employeeId, null );
         employeeVO.reset();

         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            if ( staffVO != null )
            {
               // ˢ�»���
               constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
               constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
               constantsInit( "initPosition", getAccountId( request, response ) );
               constantsInit( "initBranch", getAccountId( request, response ) );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employee list
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
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         // ���Action Form
         EmployeeVO employeeVO = ( EmployeeVO ) form;
         // ����ѡ�е�ID
         if ( employeeVO.getSelectedIds() != null && !employeeVO.getSelectedIds().equals( "" ) )
         {

            insertlog( request, employeeVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeVO.getSelectedIds() ) );
            // �ָ�
            for ( String selectedId : employeeVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeVO.setEmployeeId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeVO.setModifyBy( getUserId( request, response ) );
               // ����ˢ�»���
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );
               employeeService.deleteEmployee( employeeVO );
               if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
               {
                  // ˢ�»���
                  if ( staffVO != null )
                  {
                     constantsInit( "initStaffForDelete", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                     constantsInit( "initPosition", getAccountId( request, response ) );
                     constantsInit( "initBranch", getAccountId( request, response ) );
                  }
               }
            }

         }
         // ���Selected IDs����Action
         employeeVO.setSelectedIds( "" );
         employeeVO.setSubAction( "" );
         employeeVO.setEmployeeId( "" );
         employeeVO.reset();

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
         final String employeeId = request.getParameter( "employeeId" );

         /** �����Ͷ���ͬ�б� Start**/
         // ��ʼ��employeeContractService�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         // ��ʼ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setFlag( "2" );
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setClientId( getClientId( request, response ) );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setEmployeeId( KANUtil.filterEmpty( employeeId ) == null ? "-1" : employeeId );

         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "status,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }

         // SubAction����
         dealSubAction( employeeContractVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setObject( employeeContractVO );
         employeeContractService.getEmployeeContractVOsByCondition( pagedListHolder, false );
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         /** ����Ա����־�б� Start**/
         // ��ʼ��EmployeeLogService
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // ��ȡEmployeeLogVO�б�
         final List< Object > employeeLogVOs = employeeLogService.getEmployeeLogVOsByEmployeeId( employeeId );
         request.setAttribute( "listEmployeeLog", employeeLogVOs );
         request.setAttribute( "listEmployeeLogCount", employeeLogVOs == null ? 0 : employeeLogVOs.size() );

         /** ���ؽ�����ϵ���б� Start**/
         // ��ʼ��EmployeeEmergencyService�ӿ�
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         final List< Object > employeeEmergencyVOs = employeeEmergencyService.getEmployeeEmergencyVOsByEmployeeId( employeeId );
         refreshSource( employeeEmergencyVOs, request );
         request.setAttribute( "listEmployeeEmergency", employeeEmergencyVOs );
         request.setAttribute( "listEmployeeEmergencyCount", employeeEmergencyVOs == null ? 0 : employeeEmergencyVOs.size() );

         /** ���ؽ��������б� Start**/
         // ��ʼ��EmployeeEducationService�ӿ�
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         final List< Object > employeeEducationVOs = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeId );
         refreshSource( employeeEducationVOs, request );
         request.setAttribute( "listEmployeeEducation", employeeEducationVOs );
         request.setAttribute( "listEmployeeEducationCount", employeeEducationVOs == null ? 0 : employeeEducationVOs.size() );

         /** ���ع��������б� Start**/
         // ��ʼ��EmployeeWorkService�ӿ�
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         final List< Object > employeeWorkVOs = employeeWorkService.getEmployeeWorkVOsByEmployeeId( employeeId );
         refreshSource( employeeWorkVOs, request );
         request.setAttribute( "listEmployeeWork", employeeWorkVOs );
         request.setAttribute( "listEmployeeWorkCount", employeeWorkVOs == null ? 0 : employeeWorkVOs.size() );

         /** �������������б� Start**/
         // ��ʼ��employeeEducationService�ӿ�
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         final List< Object > employeeLanguageVOs = employeeLanguageService.getEmployeeLanguageVOsByEmployeeId( employeeId );
         refreshSource( employeeLanguageVOs, request );
         request.setAttribute( "listEmployeeLanguage", employeeLanguageVOs );
         request.setAttribute( "listEmployeeLanguageCount", employeeLanguageVOs == null ? 0 : employeeLanguageVOs.size() );

         /** ���ؼ��������б� Start**/
         // ��ʼ��EmployeeSkillService�ӿ�
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         final List< Object > employeeSkillList = employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId );
         String[] skillIdArray = {};

         if ( employeeSkillList != null && employeeSkillList.size() > 0 )
         {
            // ��ʼ�����ܼ���
            List< String > skillIds = new ArrayList< String >();
            for ( Object obj : employeeSkillList )
            {
               EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) obj;
               employeeSkillVO.reset( mapping, request );
               skillIds.add( employeeSkillVO.getSkillId() );
            }
            skillIdArray = ( String[] ) skillIds.toArray( new String[ skillIds.size() ] );
         }

         //  ���ؼ����б�
         request.setAttribute( "listEmployeeSkill", employeeSkillList );
         request.setAttribute( "listEmployeeSkillCount", employeeSkillList == null ? 0 : employeeSkillList.size() );

         /** ���ؽ�����Ϣ�б� Start**/
         // ��ʼ��EmployeeCertificationService�ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         final List< Object > employeeCertificationVOs = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );
         refreshSource( employeeCertificationVOs, request );
         request.setAttribute( "listEmployeeCertification", employeeCertificationVOs );
         request.setAttribute( "listEmployeeCertificationCount", employeeCertificationVOs == null ? 0 : employeeCertificationVOs.size() );

         /** ��������б� Start**/
         // ��ʼ��EmployeeMembershipService�ӿ�
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         final List< Object > employeeMembershipVOs = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );
         refreshSource( employeeMembershipVOs, request );
         request.setAttribute( "listEmployeeMembership", employeeMembershipVOs );
         request.setAttribute( "listEmployeeMembershipCount", employeeMembershipVOs == null ? 0 : employeeMembershipVOs.size() );

         //��ʼ��EmployeeService
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

         // ���� ���� �б�
         if ( employeeVO == null )
         {
            //����ǿ� ������ӹ�Աʱ��ĸ�����������ǲ鿴��Աʱ��ĸ���
            employeeVO = new EmployeeVO();
         }
         request.setAttribute( "listAttachmentCount", employeeVO.getAttachmentArray() != null ? employeeVO.getAttachmentArray().length : 0 );

         // �����Inhouse ����ְλ
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            StaffService staffService = ( StaffService ) getService( "staffService" );
            final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );
            request.setAttribute( "staffVO", staffVO );

            // ���Position Count����Tab Number��ʾ
            int positionCount = 0;
            if ( staffVO != null )
            {
               positionCount = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionCountByStaffId( staffVO.getStaffId() );
               // ��ȡUser���󣬻��Username
               final UserService userService = ( UserService ) getService( "userService" );
               final UserVO userVO = userService.getUserVOByStaffId( staffVO.getStaffId() );
               if ( userVO != null )
               {
                  employeeVO.setUsername( userVO.getUsername() );
                  staffVO.setUsername( userVO.getUsername() );
               }
            }
            request.setAttribute( "positionCount", positionCount );
         }
         else if ( KANConstants.ROLE_HR_SERVICE.equals( getRole( request, response ) ) )
         {
            // ��ʼ��Service
            final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );
            EmployeeUserVO employeeUserVO = employeeUserService.getEmployeeUserByEmployeeId( employeeId );

            if ( employeeUserVO != null && StringUtils.isNotBlank( employeeUserVO.getUsername() ) )
            {
               employeeVO.setUsername( employeeUserVO.getUsername() );
            }
            else
            {
               employeeVO.setUsername( "" );
            }
         }

         // ����SkillIdArray
         employeeVO.setSkillIdArray( skillIdArray );
         employeeVO.reset( mapping, request );
         employeeVO.getPositions().clear();
         employeeVO.getPositions().add( 0, employeeVO.getEmptyMappingVO() );

         request.setAttribute( "employeeForm", employeeVO );

         /** ����Ա���춯��Ǩ�б� Start**/
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         final EmployeePositionChangeVO positionChangeVO = new EmployeePositionChangeVO();
         positionChangeVO.setAccountId( getAccountId( request, response ) );
         positionChangeVO.setClientId( getClientId( request, response ) );
         positionChangeVO.setCorpId( getCorpId( request, response ) );
         positionChangeVO.setSearchEmployeeId( employeeId );
         positionChangeVO.setStatus( "5" );
         final PagedListHolder positionChangeHolder = new PagedListHolder();
         positionChangeHolder.setObject( positionChangeVO );
         positionChangeService.getPositionChangeVOsByCondition( positionChangeHolder, false );
         refreshHolder( positionChangeHolder, request );
         request.setAttribute( "positionChangeHolder", positionChangeHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return mapping.findForward( "listSpecialInfoInHouse" );
      }
      else
      {
         return mapping.findForward( "listSpecialInfo" );
      }
   }

   /**
    *  ɾ�� ��Ա  �Ͷ���ͬ������Э�飬������ϵ�ˣ�ѧ���������������������ܣ�֤��-������ ������
    *  ˢ���б�������
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward refresh_list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String employeeId = request.getParameter( "employeeId" );
         // ��ȡ��ǰ�û��Ĳ�������
         final String operType = request.getParameter( "operType" );
         String result = "";
         if ( operType.equalsIgnoreCase( "employeeContractAction" ) )
         {
            // �Ͷ���ͬ   ����Э��  
            // ��ʼ��employeeContractService�ӿ�
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final List< Object > employeeContractServiceList = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeId );
            int employeeContractServiceList_1 = 0;//�Ͷ���ͬ��Ŀ
            int employeeContractServiceList_2 = 0;//����Э����Ŀ
            if ( employeeContractServiceList != null && employeeContractServiceList.size() > 0 )
            {
               for ( Object obj : employeeContractServiceList )
               {
                  EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) obj;
                  employeeContractVO.reset( null, request );
                  if ( employeeContractVO.getFlag() != null && "1".equals( employeeContractVO.getFlag() ) )
                  {
                     employeeContractServiceList_1++;
                  }
                  else if ( employeeContractVO.getFlag() != null && "2".equals( employeeContractVO.getFlag() ) )
                  {
                     employeeContractServiceList_2++;
                  }
               }
            }
            //  ˢ�½��Ͷ���ͬ��Ŀ
            result = employeeContractServiceList_1 + "," + employeeContractServiceList_2;
         }
         else if ( operType.equalsIgnoreCase( "employeeEmergencyAction" ) )
         {
            //������ϵ��
            // ��ʼ��employeeEmergencyService�ӿ�
            final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );

            final List< Object > employeeEmergencyServiceList = employeeEmergencyService.getEmployeeEmergencyVOsByEmployeeId( employeeId );

            result = ( employeeEmergencyServiceList == null ? 0 : employeeEmergencyServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeEducationAction" ) )
         {
            //ѧϰ����
            // ��ʼ��employeeEducationService�ӿ�
            final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

            final List< Object > employeeEducationServiceList = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeId );

            //  ����ѧϰ�����б�
            result = ( employeeEducationServiceList == null ? 0 : employeeEducationServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeWorkAction" ) )
         {
            //��������
            // ��ʼ��employeeEducationService�ӿ�
            final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );

            final List< Object > employeeWorkServiceList = employeeWorkService.getEmployeeWorkVOsByEmployeeId( employeeId );

            //  ���ع��������б�
            result = ( employeeWorkServiceList == null ? 0 : employeeWorkServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeSkillAction" ) )
         {
            //����
            // ��ʼ��employeeEducationService�ӿ�
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

            final List< Object > employeeSkillServiceList = employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId );

            //  ���ؼ����б�
            result = ( employeeSkillServiceList == null ? 0 : employeeSkillServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeLanguageAction" ) )
         {
            // ��������
            final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );

            final List< Object > employeeLanguageServiceList = employeeLanguageService.getEmployeeLanguageVOsByEmployeeId( employeeId );

            result = ( employeeLanguageServiceList == null ? 0 : employeeLanguageServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeCertificationAction" ) )
         {
            // ���� ֤��
            // ��ʼ��employeeEducationService�ӿ�
            final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );

            final List< Object > employeeCertificationServiceList = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );

            //  ���� �����б�
            result = ( employeeCertificationServiceList == null ? 0 : employeeCertificationServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeMembershipAction" ) )
         {
            //���
            // ��ʼ��EmployeeMembershipService�ӿ�
            final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );

            final List< Object > employeeMembershipServiceList = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );

            //  ��������б� 
            result = ( employeeMembershipServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeLogAction" ) )
         {
            // ��־����
            final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
            final List< Object > employeeLogVOs = employeeLogService.getEmployeeLogVOsByEmployeeId( employeeId );

            //  ������־�����б� 
            result = ( employeeLogVOs.size() ) + "";
         }

         PrintWriter out = response.getWriter();
         out.print( result );
         out.flush();
         out.close();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * �Ƿ��й�Ա�ڷ�������
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward areEmpsDurServ( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      String returnIds = "";

      try
      {
         final PrintWriter out = response.getWriter();

         // ��ȡ ��ɾ����IDS
         final String[] selectedIds = request.getParameter( "selectedIds" ).split( "," );

         final List< String > selectedIdsList = arrayStr2StrList( selectedIds );

         // ��ʼ��EmployeeContract Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��ȡ�������ڵ��Ͷ���ͬ����Э��
         final List< Object > employeeContracts = employeeContractService.getEmployeeContractsDuringService( selectedIdsList );

         for ( Object o : employeeContracts )
         {
            returnIds += ( ( EmployeeContractVO ) o ).getEmployeeId();
            returnIds += " , ";
         }

         out.print( returnIds );

      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }

      return null;
   }

   public List< String > arrayStr2StrList( String[] selectedIdArray )
   {

      final List< String > selectedIdList = new ArrayList< String >();

      for ( String s : selectedIdArray )
      {
         try
         {
            selectedIdList.add( Cryptogram.decodeString( URLDecoder.decode( s, "UTF-8" ) ) );
         }
         catch ( Exception e )
         {
            e.printStackTrace();
         }
      }

      return selectedIdList;
   }

   // Ajax���ã������Ͷ���ͬ����
   public ActionForward list_special_info_list( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���Action Form
      final EmployeeVO employeeVO = ( EmployeeVO ) form;

      // ��ʼ����ѯ����
      final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
      employeeContractVO.setAccountId( getAccountId( request, response ) );
      employeeContractVO.setClientId( getClientId( request, response ) );
      employeeContractVO.setCorpId( getCorpId( request, response ) );
      employeeContractVO.setFlag( "2" );
      employeeContractVO.setSortColumn( employeeVO.getSortColumn() );
      employeeContractVO.setSortOrder( employeeVO.getSortOrder() );
      employeeContractVO.setEmployeeId( KANUtil.filterEmpty( employeeVO.getEmployeeId() ) == null ? "-1" : employeeVO.getEmployeeId() );

      if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
      {
         employeeContractVO.setSortColumn( "status,contractId" );
         employeeContractVO.setSortOrder( "desc" );
      }

      // SubAction����
      dealSubAction( employeeContractVO, mapping, form, request, response );

      // ��ʼ��PagedListHolder���������÷�ʽ����Service
      final PagedListHolder pagedListHolder = new PagedListHolder();
      pagedListHolder.setObject( employeeContractVO );

      // ��ʼ��employeeContractService�ӿ�
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
      employeeContractService.getEmployeeContractVOsByCondition( pagedListHolder, false );
      // ˢ��Holder�����ʻ���ֵ
      refreshHolder( pagedListHolder, request );

      // Holder��д��Request����
      request.setAttribute( "pagedListHolder", pagedListHolder );
      request.setAttribute( "role", getRole( request, response ) );

      return mapping.findForward( "listEmployeeContractTable" );
   }

   /**
    * ��֤���֤�Ƿ�ע��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    * @throws IOException 
    */
   public ActionForward get_count_byCertificateNumber( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, IOException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         String certificateNumber = request.getParameter( "certificateNumber" );
         String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         out.print( checkCertificateNumber( certificateNumber, employeeId, request ) );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public int checkCertificateNumber( String certificateNumber, String employeeId, final HttpServletRequest request ) throws KANException
   {

      int result = 0;
      if ( KANUtil.filterEmpty( certificateNumber ) != null )
      {
         PagedListHolder pagedListHolder = new PagedListHolder();
         final EmployeeVO employeeVO = new EmployeeVO();
         employeeVO.setCertificateNumber( certificateNumber );
         employeeVO.setAccountId( getAccountId( request, null ) );
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            employeeVO.setCorpId( getCorpId( request, null ) );
         }
         pagedListHolder.setObject( employeeVO );
         // ��ʼ��Service�ӿ�
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         employeeService.getEmployeeVOsByCondition( pagedListHolder, true );
         if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            // �õ� ӵ�и�����֤�����������
            if ( !( ( EmployeeVO ) pagedListHolder.getSource().get( 0 ) ).getEmployeeId().equalsIgnoreCase( employeeId ) )
            {
               result = 1;
               request.setAttribute( "certificationErrorMsg", "���֤�ѱ�ע��" );
            }
         }

      }
      return result;

   }

   /***
    * ����¼��
    * @param employeeId
    * @param username
    * @param request
    * @return count ������
    * @throws KANException
    */
   private int checkUsername( final String employeeId, final String username, final HttpServletRequest request ) throws KANException
   {
      int count = 0;

      if ( KANUtil.filterEmpty( username ) != null )
      {
         // ��ʼ��Service
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final UserService userService = ( UserService ) getService( "userService" );
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // ��ʼ��StaffVO
         StaffVO staffVO = null;
         if ( KANUtil.filterEmpty( employeeId ) != null )
         {
            staffVO = staffService.getStaffVOByEmployeeId( employeeId );
         }

         if ( KANUtil.filterEmpty( employeeId ) == null )
         {
            count++;
            warning( request, null, KANUtil.getProperty( request.getLocale(), "error.not.position" ) );
         }
         else
         {
            // �Ƿ����ְλ
            final StaffVO tempStaffVO = new StaffVO();
            tempStaffVO.setAccountId( getAccountId( request, null ) );
            tempStaffVO.setEmployeeId( employeeId );
            List< Object > positionObjects = positionService.getPositionVOsByEmployeeId( tempStaffVO );
            if ( positionObjects != null && positionObjects.size() > 0 )
            {
               // ��ʼ����������
               final UserVO userVO = new UserVO();
               userVO.setAccountId( getAccountId( request, null ) );
               userVO.setUsername( username );

               // ��ѯ���ݿ��Ƿ��Ѵ��ڴ˶���
               UserVO existUserVO = userService.login( userVO );

               if ( existUserVO == null )
               {
                  userVO.setCorpId( getCorpId( request, null ) );
                  existUserVO = userService.login_inHouse( userVO );
               }

               if ( existUserVO != null && ( staffVO == null || !staffVO.getStaffId().equals( existUserVO.getStaffId() ) ) )
               {
                  count++;
                  warning( request, null, KANUtil.getProperty( request.getLocale(), "error.username.already.exist" ).replace( "XX", username ) );
               }
            }
            else
            {
               count++;
               warning( request, null, KANUtil.getProperty( request.getLocale(), "error.not.position" ) );
            }
         }
      }

      return count;
   }

   // ����¼��
   private boolean checkUsernameExist( final String employeeId, final String username, final String accountId, final HttpServletRequest request ) throws KANException
   {
      boolean exist = false;

      if ( KANUtil.filterEmpty( username ) != null )
      {
         // ��ʼ��Service
         final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );

         EmployeeUserVO employeeUserVO = new EmployeeUserVO();
         employeeUserVO.setUsername( username );
         employeeUserVO.setAccountId( accountId );
         employeeUserVO.setEmployeeId( employeeId );
         if ( employeeUserService.checkUsernameExist( employeeUserVO ) )
         {
            warning( request, null, "����ʧ�ܣ��û����Ѵ��ڣ�" );
            exist = true;
         }
      }

      return exist;
   }

   // ����¼��
   private boolean checkEmail( final String email, final String username, final HttpServletRequest request ) throws KANException
   {
      boolean exist = true;

      if ( StringUtils.isNotBlank( username ) && StringUtils.isBlank( email ) )
      {
         warning( request, null, "����ʧ�ܣ�������Ա�˺ţ������ַ������д��" );
         exist = false;
      }

      return exist;
   }

   // Code Reviewed by Siuvan Xia at 2014-12-11
   public void modifyPosition( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException,
         IOException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ����
         final String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         final String positionValue = request.getParameter( "positionValue" );
         final String[] positionIdArray = positionValue.split( "," );

         // ��ʼ��Service�ӿ� 
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // ��ȡEmployeeVO
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         // ��ȡStaffVO 
         final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );

         employeeVO.setPositionIdArray( positionIdArray );
         // Employee��Position������ϵ��������Employee�����ֶ�
         employeeService.addPositionForEmployee( employeeVO );

         // ˢ�»���
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }

         insertlog( request, employeeVO, Operate.ADD, employeeId, "AJAX ���/ɾ��ְλ" );
         out.print( PositionRender.getPositionsByStaffId( request, staffVO.getStaffId() ) );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���ٴ���Ա��
   public ActionForward quick_add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // ���ActionForm
            final EmployeeVO employeeVO = ( EmployeeVO ) form;
            employeeVO.setAccountId( getAccountId( request, response ) );
            employeeVO.setCreateBy( getUserId( request, response ) );
            employeeVO.setModifyBy( getUserId( request, response ) );
            employeeVO.setCorpId( getCorpId( request, response ) );
            employeeVO.setDeleted( BaseVO.TRUE );
            employeeVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            int result = employeeService.quickCreateEmployee( employeeVO );

            // ˢ�»���
            if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
            {
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

               if ( staffVO != null )
               {
                  // ˢ�»���
                  constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                  constantsInit( "initPosition", getAccountId( request, response ) );
                  constantsInit( "initBranch", getAccountId( request, response ) );
               }
            }

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               employeeVO.reset();
               return list_object( mapping, form, request, response );
            }
            else
            {
               // ����Add�ɹ����
               success( request, MESSAGE_TYPE_ADD );
            }
         }

         // ���Form����
         ( ( EmployeeVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // ���ٴ���Ա������û����Ƿ����Ajax
   public ActionForward username_keyup_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final EmployeeVO employeeVO = ( EmployeeVO ) form;
         decodedObject( employeeVO );
         boolean exist = false;

         if ( KANUtil.filterEmpty( employeeVO.getUsername() ) != null )
         {
            // ��ʼ��Service
            final UserService userService = ( UserService ) getService( "userService" );

            // ��ʼ����������
            final UserVO userVO = new UserVO();
            userVO.setAccountId( getAccountId( request, null ) );
            userVO.setUsername( employeeVO.getUsername() );

            // ��ѯ���ݿ��Ƿ��Ѵ��ڴ˶���
            UserVO existUserVO = userService.login( userVO );

            if ( existUserVO == null )
            {
               userVO.setCorpId( getCorpId( request, null ) );
               existUserVO = userService.login_inHouse( userVO );
            }

            // �û��Ƿ��Ѵ���
            if ( existUserVO != null )
            {
               exist = true;
            }
         }

         if ( exist )
            out.print( "exist" );
         else
            out.print( "notExist" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   // ���ٴ���Ա�����ְλ�����Ƿ�����
   public ActionForward positionName_callback_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String positionId = request.getParameter( "positionId" );
         boolean isVacant = false;

         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionDTOByPositionId( positionId );
         final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOsByPositionId( positionId );
         if ( positionDTO != null && positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getIsVacant() ) != null && staffDTOs != null
               && ( Integer.valueOf( positionDTO.getPositionVO().getIsVacant() ) - staffDTOs.size() ) < 1 )
         {
            // ��������
            isVacant = true;
         }

         if ( isVacant )
            out.print( "yes" );
         else
            out.print( "no" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * ΢����������.��˾�绰������Ӹ��˵绰.ͨѶ¼���治��ʾ
    * @param employeeVO
    * @param hidePhone2
    */
   private void fixPhone2( EmployeeVO employeeVO, String hidePhone2 )
   {
      if ( !StringUtils.isBlank( hidePhone2 ) )
      {
         employeeVO.setPhone2( hidePhone2 );
      }
   }

   public void sync_wx( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException, IOException
   {
      PagedListHolder pagedListHolder = new PagedListHolder();
      EmployeeVO employeeVO = ( EmployeeVO ) form;
      employeeVO.setAccountId( getAccountId( request, response ) );
      employeeVO.setCorpId( getCorpId( request, response ) );
      pagedListHolder.setObject( employeeVO );
      EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      employeeService.getEmployeeVOsByCondition( pagedListHolder, false );
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object o : pagedListHolder.getSource() )
         {
            super.syncWXContacts_not_delete( ( ( EmployeeVO ) o ).getEmployeeId(), false );
         }
      }

      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      final PrintWriter out = response.getWriter();
      out.println( "success" );
      out.flush();
      out.close();
   }

   /***
    * Ա�����ϱ䶯ʱ����֤��˾�����Ψһ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    */
   public void checkEmail_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
   {
      try
      {
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final Map< String, Object > parameters = new HashMap< String, Object >();
         if ( StringUtils.isNotBlank( request.getParameter( "email1" ) ) )
         {
            parameters.put( "email1", request.getParameter( "email1" ) );
         }
         if ( StringUtils.isNotBlank( request.getParameter( "employeeId" ) ) )
         {
            parameters.put( "employeeId", request.getParameter( "employeeId" ) );
         }
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         out.print( String.valueOf( employeeService.emailIsRegister( parameters ) ) );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
      }

   }
}
