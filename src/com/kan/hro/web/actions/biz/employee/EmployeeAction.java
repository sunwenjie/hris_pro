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
   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_EMPLOYEE";

   // 当前Action对应的Access Action
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
         // 获取EmployeeId
         final String employeeId = request.getParameter( "employeeId" );

         // 初始化 Service
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final PagedListHolder employeeHolder = new PagedListHolder();
         EmployeeVO employeeVO = ( EmployeeVO ) form;
         employeeVO.setEmployeeId( employeeId );
         employeeHolder.setObject( employeeVO );
         setDataAuth( request, response, employeeVO );
         employeeService.getEmployeeVOsByCondition( employeeHolder, false );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();
         if ( employeeHolder != null && employeeHolder.getSource() != null && employeeHolder.getSource().size() > 0 )
         {
            refreshHolder( employeeHolder, request );
            jsonObject = JSONObject.fromObject( ( EmployeeVO ) ( employeeHolder.getSource().get( 0 ) ) );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "errorMsg", ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ? "员工" : "雇员" ) + "ID无效；" );
            jsonObject.put( "success", "false" );
         }
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         out.println( jsonObject.toString() );
         out.flush();
         out.close();

         //         // 初始化PrintWrite对象
         //         final PrintWriter out = response.getWriter();
         //
         //         // 初始化 Service
         //         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         //
         //         // 获取EmployeeId
         //         final String employeeId = request.getParameter( "employeeId" );
         //
         //         // 获取是否是考勤模块(请假和加班)
         //         final String isAttendance = request.getParameter( "attendance" );
         //
         //         // 获取subAction
         //         final String subAction = request.getParameter( "subAction" );
         //
         //         // 初始化 JSONObject
         //         JSONObject jsonObject = new JSONObject();
         //
         //         boolean pass = true;
         //
         //         // 是否可以为EmployeeId请假
         //         if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( isAttendance ) != null )
         //         {
         //            pass = false;
         //
         //            // 如果是查看
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
         //            jsonObject.put( "errorMsg", "非HR职能只能为自己或同一部门员工申请" + ( ( KANUtil.filterEmpty( isAttendance ) != null && isAttendance.trim().equals( "leave" ) ) ? "请假" : "加班" ) + "；" );
         //         }
         //         else
         //         {
         //            // 初始化EmployeeVO
         //            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         //            if ( employeeVO != null && employeeVO.getAccountId() != null && employeeVO.getAccountId().equals( getAccountId( request, response ) ) )
         //            {
         //               employeeVO.reset( mapping, request );
         //               jsonObject = JSONObject.fromObject( employeeVO );
         //               jsonObject.put( "success", "true" );
         //            }
         //            else
         //            {
         //               jsonObject.put( "errorMsg", ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) ? "员工" : "雇员" ) + "ID无效；" );
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

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**  
    * List Object Ajax
    * 雇员搜索弹出模态框
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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         // 获得Action Form 
         final EmployeeVO employeeVO = ( EmployeeVO ) form;
         // 获取是否数据权限控制
         final String ignoreDataAuth = request.getParameter( "ignoreDataAuth" );
         // forward页面
         final String forward = request.getParameter( "forward" );
         // 处理数据权限
         if ( null == ignoreDataAuth || "".equalsIgnoreCase( ignoreDataAuth ) || new Boolean( ignoreDataAuth ) == false )
         {
            setDataAuth( request, response, employeeVO );
         }

         // 解码
         decodedObject( employeeVO );
         if ( KANUtil.filterEmpty( employeeVO.getRemark1() ) != null )
         {
            employeeVO.setRemark1( "%\"jiancheng\":\"" + employeeVO.getRemark1() + "%" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeHolder = new PagedListHolder();

         // 传入当前页
         employeeHolder.setPage( page );
         // 传入当前值对象
         employeeHolder.setObject( employeeVO );
         // 设置页面记录条数
         employeeHolder.setPageSize( listPageSize_popup );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeService.getEmployeeVOsByCondition( employeeHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( employeeHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeHolder", employeeHolder );

         // 重新写入role
         request.setAttribute( "role", getRole( request, response ) );

         // Ajax Table调用，直接传回 JSP
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
         // 获得要联想的属性
         final String conditionName = request.getParameter( "conditionName" );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Employee Service
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // 初始化 JSONArray ###
         final JSONArray array = new JSONArray();

         // 初始化联想条件
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

      // 跳转到列表界面
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
         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // 获得Action Form
         final EmployeeVO employeeVO = ( EmployeeVO ) form;

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, employeeVO );
         }
         // 客户登录
         else if ( BaseAction.getRole( request, response ).equalsIgnoreCase( KANConstants.ROLE_CLIENT ) )
         {
            employeeVO.setClientId( BaseAction.getClientId( request, response ) );
         }
         // 雇员登录
         else if ( BaseAction.getRole( request, response ).equalsIgnoreCase( KANConstants.ROLE_EMPLOYEE ) )
         {
            employeeVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
         }

         // 获得SubAction
         final String subAction = getSubAction( form );

         // 如果没有指定排序则默认按employeeId排序
         if ( employeeVO.getSortColumn() == null || employeeVO.getSortColumn().isEmpty() )
         {
            employeeVO.setSortColumn( "employeeId" );
            employeeVO.setSortOrder( "desc" );
         }

         // 添加自定义搜索内容
         employeeVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 处理SubAction
         dealSubAction( employeeVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder employeeHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            employeeHolder.setPage( page );
            // 设置Object
            employeeHolder.setObject( employeeVO );
            // 设置页面记录条数
            employeeHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            employeeService.getEmployeeVOsByCondition( employeeHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( employeeHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", employeeHolder );
         request.setAttribute( "role", getRole( request, response ) );
         request.setAttribute( "accessAction", getAccessAction( request, response ) );

         // 处理Return
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
      // 添加页面Token
      this.saveToken( request );
      final EmployeeVO employeeVO = ( ( EmployeeVO ) form );

      final String newType = request.getParameter( "newType" );

      // 初始化PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );
      // 设置Sub Action
      employeeVO.setSubAction( CREATE_OBJECT );
      // 默认证件类型 - 身份证
      employeeVO.setCertificateType( "1" );
      // 默认国籍 - 中国
      employeeVO.setNationNality( "1" );
      // 默认外国人就业许可证  -  否
      employeeVO.setHasForeignerWorkLicence( "2" );
      // 默认居住证 - 否
      employeeVO.setHasResidenceLicence( "2" );
      // 默认状态 - 招募
      employeeVO.setStatus( "2" );
      // 默认所属人，所属部门
      if ( positionVO != null )
      {
         employeeVO.setBranch( positionVO.getBranchId() );
         employeeVO.setOwner( positionVO.getPositionId() );
      }
      // 异动原因 新进
      // employeeVO.setRemark3( "1" );
      // 跳转到快速新建界面
      if ( KANUtil.filterEmpty( newType ) != null )
      {
         return mapping.findForward( "manageEmployeeHRQuick" );
      }

      // 跳转到新建界面
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         // 获得当前主键
         String id = "";

         if ( request.getParameter( "id" ) != null )
         {
            id = KANUtil.decodeString( request.getParameter( "id" ) );
         }
         else
         {
            id = KANUtil.decodeString( ( ( EmployeeVO ) form ).getEmployeeId() );
         }

         //雇员登陆 id 为空去cooki 取值
         String role = null;
         if ( StringUtils.isBlank( id ) )
         {
            id = EmployeeSecurityAction.getEmployeeId( request, response );
            role = EmployeeSecurityAction.getRole( request, response );
         }
         // 获得主键对应对象
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeVO.reset( null, request );

         // 如果City Id，则填充Province Id
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

      // 跳转到编辑界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // 获得ActionForm
            final EmployeeVO employeeVO = ( EmployeeVO ) form;

            // 验证身份证
            checkCertificateNumber( employeeVO.getCertificateNumber(), null, request );
            // 不合法的身份证跳转新增页面
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "certificationErrorMsg" ) ) != null )
            {
               return to_objectNew( mapping, employeeVO, request, response );
            }

            // 验证用户名
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

            // 照片
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
            // 保存自定义Column
            employeeVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );
            final String[] positionIdArray = request.getParameterValues( "positionIdArray" );
            employeeVO.setPositionIdArray( positionIdArray );
            employeeVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            //fixPhone2( employeeVO, request.getParameter( "hidePhone2" ) );
            // 新建对象
            final int result = employeeService.insertEmployee( employeeVO );
            insertlog( request, employeeVO, Operate.ADD, employeeVO.getEmployeeId(), null );

            // 刷新缓存
            if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
            {
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

               if ( staffVO != null )
               {
                  // 刷新缓存
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
               // 返回Add成功标记
               success( request, MESSAGE_TYPE_ADD );
            }

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + "&employeeId=" + employeeVO.getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            String forwardToModify = "employeeAction.do?proc=to_objectModify&id=" + employeeVO.getEncodedId();
            request.getRequestDispatcher( forwardToModify ).forward( request, response );

            return null;
         }

         // 清空Form条件
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
         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final SocialBenefitSolutionDetailService socialBenefitSolutionDetailService = ( SocialBenefitSolutionDetailService ) getService( "socialBenefitSolutionDetailService" );

         // 获取OrderId
         final String orderId = request.getParameter( "orderId" );
         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );

         // 获得ActionForm
         final EmployeeVO employeeVO = ( EmployeeVO ) form;
         // Ajax解码
         decodedObject( employeeVO );
         employeeVO.setAccountId( getAccountId( request, response ) );
         employeeVO.setCreateBy( getUserId( request, response ) );
         employeeVO.setModifyBy( getUserId( request, response ) );
         employeeVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );

         // 保存自定义Column#
         employeeVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

         // 新建对象
         employeeService.insertEmployee( employeeVO );

         // 初始化EmployeeContractVO
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
            // 初始化PositionVO
            final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

            // 默认所属人，所属部门
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
         employeeContractVO.setNameZH( ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) ? "劳动合同（"
               + employeeVO.getNameZH() + "）- " + tempDate
               : "派送协议" + tempEmployeeNameEN + " - " + tempDate );

         // 结束时间（预计），默认当前开始三年
         final Date tempEndDate = KANUtil.getDate( new Date(), 3, 0, -1 );

         // 默认开始时间和结束时间
         employeeContractVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
         employeeContractVO.setEndDate( KANUtil.formatDate( tempEndDate, "yyyy-MM-dd" ) );

         // 如果ClientOrderHeaderVO不为空，并且合同结束时间不足三年的情况
         if ( clientOrderHeaderVO != null && KANUtil.getDays( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ) ) < KANUtil.getDays( tempEndDate ) )
         {
            employeeContractVO.setEndDate( KANUtil.formatDate( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ), "yyyy-MM-dd" ) );
         }

         employeeContractVO.setEmployStatus( "1" );
         employeeContractVO.setStatus( "1" );
         employeeContractVO.setLocked( "2" );
         employeeContractService.insertEmployeeContract( employeeContractVO );
         // 添加基本工资
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
         // 添加社保
         if ( KANUtil.filterEmpty( sbItem ) != null && !"0".equals( sbItem ) )
         {
            final List< Object > sbSolutionObjects = socialBenefitSolutionDetailService.getSocialBenefitSolutionDetailVOsByHeaderId( sbItem );
            final String baseCompany = request.getParameter( "baseCompany" );
            final String basePersonal = request.getParameter( "basePersonal" );
            List< String > baseCompanyList = new ArrayList< String >();
            List< String > basePersonalList = new ArrayList< String >();
            List< String > solutionDetailIdList = new ArrayList< String >();

            // 如果不在基数范围内，则不添加
            for ( Object object : sbSolutionObjects )
            {
               final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) object;

               final String companyFloor = socialBenefitSolutionDetailVO.getCompanyFloor();
               final String companyCap = socialBenefitSolutionDetailVO.getCompanyCap();
               final String personalFloor = socialBenefitSolutionDetailVO.getPersonalFloor();
               final String personalCap = socialBenefitSolutionDetailVO.getPersonalCap();

               // 根据情况设置基数
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
         // 添加商保
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // 获得当前主键
            String employeeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
            // 验证身份证
            checkCertificateNumber( ( ( EmployeeVO ) form ).getCertificateNumber(), ( ( EmployeeVO ) form ).getEmployeeId(), request );

            // 不合法的身份证跳转新增页面
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "certificationErrorMsg" ) ) != null )
            {
               employeeVO.reset();
               return to_objectModify( mapping, form, request, response );
            }

            // 验证用户名
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
            // 雇员登陆的操作
            if ( getRole( request, response ).equals( KANConstants.ROLE_EMPLOYEE ) )
            {
               //不可修改项
               ( ( EmployeeVO ) form ).setOwner( employeeVO.getOwner() );
               ( ( EmployeeVO ) form ).setStatus( employeeVO.getStatus() );
               ( ( EmployeeVO ) form ).setBranch( employeeVO.getOwner() );
            }

            // 照片
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

            // 保存自定义Column
            employeeVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );
            final String[] positionIdArray = request.getParameterValues( "positionIdArray" );
            employeeVO.setPositionIdArray( positionIdArray );

            // 修改对象
            if ( employeeService.updateEmployee( employeeVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );

               insertlog( request, employeeVO, Operate.SUBMIT, employeeVO.getEmployeeId(), null );
            }
            else
            {
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeVO, Operate.MODIFY, employeeVO.getEmployeeId(), null );
            }

            // 刷新缓存
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

         // 清空Form条件
         ( ( EmployeeVO ) form ).reset();
         ( ( EmployeeVO ) form ).setSortColumn( "employeeId" );
         ( ( EmployeeVO ) form ).setSortOrder( "desc" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify_ Object Skill
    *  修改雇员/员工技能
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    * Add By：Jack  
    * Add Date：2014-3-8  
    */
   public ActionForward modify_object_skill( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

            // 获得“操作标示” SubAction，雇员ID EmployeeID
            String subAction = request.getParameter( "subAction" );
            final String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeId" ) );

            // 需要操作
            if ( subAction != null )
            {
               // 如果为新增
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
                     // 返回“添加技能成功”标记
                     success( request, null, "成功添加技能 ！" );
                  }
               }
               // 如果为删除
               else if ( subAction.trim().equals( "deleteObject" ) )
               {
                  final String skillId = request.getParameter( "skillId" );
                  // 初始化查询对象
                  EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
                  employeeSkillVO.setSkillId( skillId );
                  employeeSkillVO.setEmployeeId( employeeId );
                  final List< Object > employeeSkillVOs = employeeSkillService.getEmployeeSkillVOsByCondition( employeeSkillVO );

                  // 删除EmployeeSkillVO
                  if ( employeeSkillVOs != null && employeeSkillVOs.size() > 0 )
                  {
                     employeeSkillVO = ( EmployeeSkillVO ) employeeSkillVOs.get( 0 );
                     employeeSkillVO.setModifyBy( getUserId( request, response ) );
                     employeeSkillVO.setModifyDate( new Date() );
                     employeeSkillVO.setDeleted( "2" );
                     employeeSkillService.updateEmployeeSkill( employeeSkillVO );
                  }

                  // 返回“删除技能成功”标记
                  success( request, null, "删除技能成功 ！" );
               }
            }

         }
         // 清空Form条件
         ( ( EmployeeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = new EmployeeVO();
         // 获得当前主键
         String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeId" ) );

         // 删除主键对应对象
         employeeVO.setEmployeeId( employeeId );
         employeeVO.setModifyBy( getUserId( request, response ) );
         // 用于刷新缓存
         StaffService staffService = ( StaffService ) getService( "staffService" );
         StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );
         employeeService.deleteEmployee( employeeVO );
         insertlog( request, null, Operate.DELETE, employeeId, null );
         employeeVO.reset();

         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            if ( staffVO != null )
            {
               // 刷新缓存
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
         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         // 获得Action Form
         EmployeeVO employeeVO = ( EmployeeVO ) form;
         // 存在选中的ID
         if ( employeeVO.getSelectedIds() != null && !employeeVO.getSelectedIds().equals( "" ) )
         {

            insertlog( request, employeeVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeVO.getSelectedIds() ) );
            // 分割
            for ( String selectedId : employeeVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeVO.setEmployeeId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeVO.setModifyBy( getUserId( request, response ) );
               // 用于刷新缓存
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );
               employeeService.deleteEmployee( employeeVO );
               if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
               {
                  // 刷新缓存
                  if ( staffVO != null )
                  {
                     constantsInit( "initStaffForDelete", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
                     constantsInit( "initPosition", getAccountId( request, response ) );
                     constantsInit( "initBranch", getAccountId( request, response ) );
                  }
               }
            }

         }
         // 清除Selected IDs和子Action
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

         /** 加载劳动合同列表 Start**/
         // 初始化employeeContractService接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         // 初始化EmployeeContractVO
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

         // SubAction处理
         dealSubAction( employeeContractVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setObject( employeeContractVO );
         employeeContractService.getEmployeeContractVOsByCondition( pagedListHolder, false );
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         /** 加载员工日志列表 Start**/
         // 初始化EmployeeLogService
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // 获取EmployeeLogVO列表
         final List< Object > employeeLogVOs = employeeLogService.getEmployeeLogVOsByEmployeeId( employeeId );
         request.setAttribute( "listEmployeeLog", employeeLogVOs );
         request.setAttribute( "listEmployeeLogCount", employeeLogVOs == null ? 0 : employeeLogVOs.size() );

         /** 加载紧急联系人列表 Start**/
         // 初始化EmployeeEmergencyService接口
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         final List< Object > employeeEmergencyVOs = employeeEmergencyService.getEmployeeEmergencyVOsByEmployeeId( employeeId );
         refreshSource( employeeEmergencyVOs, request );
         request.setAttribute( "listEmployeeEmergency", employeeEmergencyVOs );
         request.setAttribute( "listEmployeeEmergencyCount", employeeEmergencyVOs == null ? 0 : employeeEmergencyVOs.size() );

         /** 加载教育经历列表 Start**/
         // 初始化EmployeeEducationService接口
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         final List< Object > employeeEducationVOs = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeId );
         refreshSource( employeeEducationVOs, request );
         request.setAttribute( "listEmployeeEducation", employeeEducationVOs );
         request.setAttribute( "listEmployeeEducationCount", employeeEducationVOs == null ? 0 : employeeEducationVOs.size() );

         /** 加载工作经历列表 Start**/
         // 初始化EmployeeWorkService接口
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         final List< Object > employeeWorkVOs = employeeWorkService.getEmployeeWorkVOsByEmployeeId( employeeId );
         refreshSource( employeeWorkVOs, request );
         request.setAttribute( "listEmployeeWork", employeeWorkVOs );
         request.setAttribute( "listEmployeeWorkCount", employeeWorkVOs == null ? 0 : employeeWorkVOs.size() );

         /** 加载语言能力列表 Start**/
         // 初始化employeeEducationService接口
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         final List< Object > employeeLanguageVOs = employeeLanguageService.getEmployeeLanguageVOsByEmployeeId( employeeId );
         refreshSource( employeeLanguageVOs, request );
         request.setAttribute( "listEmployeeLanguage", employeeLanguageVOs );
         request.setAttribute( "listEmployeeLanguageCount", employeeLanguageVOs == null ? 0 : employeeLanguageVOs.size() );

         /** 加载技术能力列表 Start**/
         // 初始化EmployeeSkillService接口
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         final List< Object > employeeSkillList = employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId );
         String[] skillIdArray = {};

         if ( employeeSkillList != null && employeeSkillList.size() > 0 )
         {
            // 初始化技能集合
            List< String > skillIds = new ArrayList< String >();
            for ( Object obj : employeeSkillList )
            {
               EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) obj;
               employeeSkillVO.reset( mapping, request );
               skillIds.add( employeeSkillVO.getSkillId() );
            }
            skillIdArray = ( String[] ) skillIds.toArray( new String[ skillIds.size() ] );
         }

         //  加载技能列表
         request.setAttribute( "listEmployeeSkill", employeeSkillList );
         request.setAttribute( "listEmployeeSkillCount", employeeSkillList == null ? 0 : employeeSkillList.size() );

         /** 加载奖惩信息列表 Start**/
         // 初始化EmployeeCertificationService接口
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         final List< Object > employeeCertificationVOs = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );
         refreshSource( employeeCertificationVOs, request );
         request.setAttribute( "listEmployeeCertification", employeeCertificationVOs );
         request.setAttribute( "listEmployeeCertificationCount", employeeCertificationVOs == null ? 0 : employeeCertificationVOs.size() );

         /** 加载社会活动列表 Start**/
         // 初始化EmployeeMembershipService接口
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         final List< Object > employeeMembershipVOs = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );
         refreshSource( employeeMembershipVOs, request );
         request.setAttribute( "listEmployeeMembership", employeeMembershipVOs );
         request.setAttribute( "listEmployeeMembershipCount", employeeMembershipVOs == null ? 0 : employeeMembershipVOs.size() );

         //初始化EmployeeService
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

         // 加载 附件 列表
         if ( employeeVO == null )
         {
            //如果是空 则是添加雇员时候的附件，否则就是查看雇员时候的附件
            employeeVO = new EmployeeVO();
         }
         request.setAttribute( "listAttachmentCount", employeeVO.getAttachmentArray() != null ? employeeVO.getAttachmentArray().length : 0 );

         // 如果是Inhouse 加载职位
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            StaffService staffService = ( StaffService ) getService( "staffService" );
            final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );
            request.setAttribute( "staffVO", staffVO );

            // 添加Position Count用于Tab Number显示
            int positionCount = 0;
            if ( staffVO != null )
            {
               positionCount = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionCountByStaffId( staffVO.getStaffId() );
               // 获取User对象，获得Username
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
            // 初始化Service
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

         // 加入SkillIdArray
         employeeVO.setSkillIdArray( skillIdArray );
         employeeVO.reset( mapping, request );
         employeeVO.getPositions().clear();
         employeeVO.getPositions().add( 0, employeeVO.getEmptyMappingVO() );

         request.setAttribute( "employeeForm", employeeVO );

         /** 加载员工异动升迁列表 Start**/
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
    *  删除 雇员  劳动合同，服务协议，紧急联系人，学历经历，工作经历，技能，证书-奖项，社会活动 附件，
    *  刷新列表总条数
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
         // 获取当前用户的操作类型
         final String operType = request.getParameter( "operType" );
         String result = "";
         if ( operType.equalsIgnoreCase( "employeeContractAction" ) )
         {
            // 劳动合同   服务协议  
            // 初始化employeeContractService接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final List< Object > employeeContractServiceList = employeeContractService.getEmployeeContractVOsByEmployeeId( employeeId );
            int employeeContractServiceList_1 = 0;//劳动合同数目
            int employeeContractServiceList_2 = 0;//服务协议数目
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
            //  刷新紧劳动合同数目
            result = employeeContractServiceList_1 + "," + employeeContractServiceList_2;
         }
         else if ( operType.equalsIgnoreCase( "employeeEmergencyAction" ) )
         {
            //紧急联系人
            // 初始化employeeEmergencyService接口
            final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );

            final List< Object > employeeEmergencyServiceList = employeeEmergencyService.getEmployeeEmergencyVOsByEmployeeId( employeeId );

            result = ( employeeEmergencyServiceList == null ? 0 : employeeEmergencyServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeEducationAction" ) )
         {
            //学习经历
            // 初始化employeeEducationService接口
            final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

            final List< Object > employeeEducationServiceList = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeId );

            //  加载学习经历列表
            result = ( employeeEducationServiceList == null ? 0 : employeeEducationServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeWorkAction" ) )
         {
            //工作经历
            // 初始化employeeEducationService接口
            final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );

            final List< Object > employeeWorkServiceList = employeeWorkService.getEmployeeWorkVOsByEmployeeId( employeeId );

            //  加载工作经历列表
            result = ( employeeWorkServiceList == null ? 0 : employeeWorkServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeSkillAction" ) )
         {
            //技能
            // 初始化employeeEducationService接口
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

            final List< Object > employeeSkillServiceList = employeeSkillService.getEmployeeSkillVOsByEmployeeId( employeeId );

            //  加载技能列表
            result = ( employeeSkillServiceList == null ? 0 : employeeSkillServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeLanguageAction" ) )
         {
            // 语言能力
            final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );

            final List< Object > employeeLanguageServiceList = employeeLanguageService.getEmployeeLanguageVOsByEmployeeId( employeeId );

            result = ( employeeLanguageServiceList == null ? 0 : employeeLanguageServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeCertificationAction" ) )
         {
            // 奖项 证书
            // 初始化employeeEducationService接口
            final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );

            final List< Object > employeeCertificationServiceList = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );

            //  加载 奖项列表
            result = ( employeeCertificationServiceList == null ? 0 : employeeCertificationServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeMembershipAction" ) )
         {
            //社会活动
            // 初始化EmployeeMembershipService接口
            final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );

            final List< Object > employeeMembershipServiceList = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );

            //  加载社会活动列表 
            result = ( employeeMembershipServiceList.size() ) + "";
         }
         else if ( operType.equalsIgnoreCase( "employeeLogAction" ) )
         {
            // 日志跟踪
            final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
            final List< Object > employeeLogVOs = employeeLogService.getEmployeeLogVOsByEmployeeId( employeeId );

            //  加载日志跟踪列表 
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
    * 是否有雇员在服务期内
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

         // 获取 被删除的IDS
         final String[] selectedIds = request.getParameter( "selectedIds" ).split( "," );

         final List< String > selectedIdsList = arrayStr2StrList( selectedIds );

         // 初始化EmployeeContract Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取服务期内的劳动合同服务协议
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

   // Ajax调用，用于劳动合同排序
   public ActionForward list_special_info_list( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 获得Action Form
      final EmployeeVO employeeVO = ( EmployeeVO ) form;

      // 初始化查询条件
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

      // SubAction处理
      dealSubAction( employeeContractVO, mapping, form, request, response );

      // 初始化PagedListHolder，用于引用方式调用Service
      final PagedListHolder pagedListHolder = new PagedListHolder();
      pagedListHolder.setObject( employeeContractVO );

      // 初始化employeeContractService接口
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      employeeContractService.getEmployeeContractVOsByCondition( pagedListHolder, false );
      // 刷新Holder，国际化传值
      refreshHolder( pagedListHolder, request );

      // Holder需写入Request对象
      request.setAttribute( "pagedListHolder", pagedListHolder );
      request.setAttribute( "role", getRole( request, response ) );

      return mapping.findForward( "listEmployeeContractTable" );
   }

   /**
    * 验证身份证是否被注册
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
         // 初始化PrintWrite对象
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
         // 初始化Service接口
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         employeeService.getEmployeeVOsByCondition( pagedListHolder, true );
         if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            // 得到 拥有该生分证号码的所有人
            if ( !( ( EmployeeVO ) pagedListHolder.getSource().get( 0 ) ).getEmployeeId().equalsIgnoreCase( employeeId ) )
            {
               result = 1;
               request.setAttribute( "certificationErrorMsg", "身份证已被注册" );
            }
         }

      }
      return result;

   }

   /***
    * 检查登录名
    * @param employeeId
    * @param username
    * @param request
    * @return count 错误标记
    * @throws KANException
    */
   private int checkUsername( final String employeeId, final String username, final HttpServletRequest request ) throws KANException
   {
      int count = 0;

      if ( KANUtil.filterEmpty( username ) != null )
      {
         // 初始化Service
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final UserService userService = ( UserService ) getService( "userService" );
         final PositionService positionService = ( PositionService ) getService( "positionService" );

         // 初始化StaffVO
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
            // 是否存在职位
            final StaffVO tempStaffVO = new StaffVO();
            tempStaffVO.setAccountId( getAccountId( request, null ) );
            tempStaffVO.setEmployeeId( employeeId );
            List< Object > positionObjects = positionService.getPositionVOsByEmployeeId( tempStaffVO );
            if ( positionObjects != null && positionObjects.size() > 0 )
            {
               // 初始化参数对象
               final UserVO userVO = new UserVO();
               userVO.setAccountId( getAccountId( request, null ) );
               userVO.setUsername( username );

               // 查询数据库是否已存在此对象
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

   // 检查登录名
   private boolean checkUsernameExist( final String employeeId, final String username, final String accountId, final HttpServletRequest request ) throws KANException
   {
      boolean exist = false;

      if ( KANUtil.filterEmpty( username ) != null )
      {
         // 初始化Service
         final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );

         EmployeeUserVO employeeUserVO = new EmployeeUserVO();
         employeeUserVO.setUsername( username );
         employeeUserVO.setAccountId( accountId );
         employeeUserVO.setEmployeeId( employeeId );
         if ( employeeUserService.checkUsernameExist( employeeUserVO ) )
         {
            warning( request, null, "操作失败，用户名已存在！" );
            exist = true;
         }
      }

      return exist;
   }

   // 检查登录名
   private boolean checkEmail( final String email, final String username, final HttpServletRequest request ) throws KANException
   {
      boolean exist = true;

      if ( StringUtils.isNotBlank( username ) && StringUtils.isBlank( email ) )
      {
         warning( request, null, "操作失败，创建雇员账号，邮箱地址必须填写！" );
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
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取参数
         final String employeeId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         final String positionValue = request.getParameter( "positionValue" );
         final String[] positionIdArray = positionValue.split( "," );

         // 初始化Service接口 
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // 获取EmployeeVO
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         // 获取StaffVO 
         final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeId );

         employeeVO.setPositionIdArray( positionIdArray );
         // Employee和Position建立关系，并方向到Employee冗余字段
         employeeService.addPositionForEmployee( employeeVO );

         // 刷新缓存
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }

         insertlog( request, employeeVO, Operate.ADD, employeeId, "AJAX 添加/删除职位" );
         out.print( PositionRender.getPositionsByStaffId( request, staffVO.getStaffId() ) );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 快速创建员工
   public ActionForward quick_add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // 获得ActionForm
            final EmployeeVO employeeVO = ( EmployeeVO ) form;
            employeeVO.setAccountId( getAccountId( request, response ) );
            employeeVO.setCreateBy( getUserId( request, response ) );
            employeeVO.setModifyBy( getUserId( request, response ) );
            employeeVO.setCorpId( getCorpId( request, response ) );
            employeeVO.setDeleted( BaseVO.TRUE );
            employeeVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            int result = employeeService.quickCreateEmployee( employeeVO );

            // 刷新缓存
            if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
            {
               final StaffService staffService = ( StaffService ) getService( "staffService" );
               final StaffVO staffVO = staffService.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

               if ( staffVO != null )
               {
                  // 刷新缓存
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
               // 返回Add成功标记
               success( request, MESSAGE_TYPE_ADD );
            }
         }

         // 清空Form条件
         ( ( EmployeeVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // 快速创建员工检查用户名是否存在Ajax
   public ActionForward username_keyup_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final EmployeeVO employeeVO = ( EmployeeVO ) form;
         decodedObject( employeeVO );
         boolean exist = false;

         if ( KANUtil.filterEmpty( employeeVO.getUsername() ) != null )
         {
            // 初始化Service
            final UserService userService = ( UserService ) getService( "userService" );

            // 初始化参数对象
            final UserVO userVO = new UserVO();
            userVO.setAccountId( getAccountId( request, null ) );
            userVO.setUsername( employeeVO.getUsername() );

            // 查询数据库是否已存在此对象
            UserVO existUserVO = userService.login( userVO );

            if ( existUserVO == null )
            {
               userVO.setCorpId( getCorpId( request, null ) );
               existUserVO = userService.login_inHouse( userVO );
            }

            // 用户是否已存在
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

   // 快速创建员工检查职位编制是否已满
   public ActionForward positionName_callback_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final String positionId = request.getParameter( "positionId" );
         boolean isVacant = false;

         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionDTOByPositionId( positionId );
         final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOsByPositionId( positionId );
         if ( positionDTO != null && positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getIsVacant() ) != null && staffDTOs != null
               && ( Integer.valueOf( positionDTO.getPositionVO().getIsVacant() ) - staffDTOs.size() ) < 1 )
         {
            // 编制已满
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
    * 微信特殊需求.公司电话后面添加个人电话.通讯录里面不显示
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
    * 员工资料变动时，保证公司邮箱的唯一性
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
