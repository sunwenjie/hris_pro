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
   // 当前Action对应的Access Action
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
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 初始化Staff Service
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 初始化Position Service
         final PositionService positionService = ( PositionService ) getService( "positionService" );
         // 初始化 JSONArray
         final JSONArray array = new JSONArray();

         List< Object > list = new ArrayList< Object >();

         if ( getRole( request, response ) != null )
         {
            // 如果是HRM登录
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               StaffVO staffVO = new StaffVO();
               staffVO.setCorpId( getCorpId( request, response ) );
               staffVO.setAccountId( getAccountId( request, response ) );
               list = staffService.getActiveStaffVOsByCorpId( staffVO );
            }
            // 如果是HRO登录
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

               // 查询StaffVO对应的职位
               final List< Object > positionStaffRelationVOs = positionService.getPositionStaffRelationVOsByStaffId( staffVO.getStaffId() );

               if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
               {
                  strb.append( "（已关联职位：" );

                  int i = 0;
                  for ( Object object : positionStaffRelationVOs )
                  {
                     PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) object;

                     //                     final PositionVO positionVO = positionService.getPositionVOByPositionId( positionStaffRelationVO.getPositionId() );
                     final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( positionStaffRelationVO.getPositionId() );

                     if ( i > 0 )
                     {
                        strb.append( "；" + positionVO.getTitleZH() + "(" + ( "1".equals( positionStaffRelationVO.getStaffType() ) ? "主" : "代" ) + ")" + "-"
                              + positionVO.getTitleEN() );
                     }
                     else
                     {
                        strb.append( positionVO.getTitleZH() + "(" + ( "1".equals( positionStaffRelationVO.getStaffType() ) ? "主" : "代" ) + ")" + "-" + positionVO.getTitleEN() );
                     }

                     i++;
                  }
                  strb.append( "）" );
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

      // 跳转到列表界面
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
         // 获得当前页
         final String page = getPage( request );
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 获得Action Form
         final StaffVO staffVO = ( StaffVO ) form;
         // 获得SubAction
         final String subAction = getSubAction( form );

         // 添加自定义搜索内容
         staffVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 处理SubAction
         dealSubAction( staffVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder staffHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            staffHolder.setPage( page );
            // 传入当前值对象
            staffHolder.setObject( staffVO );
            // 设置页面记录条数
            staffHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            staffService.getStaffVOsByCondition( staffHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( staffHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", staffHolder );

         // 处理Return
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( StaffVO ) form ).setSubAction( CREATE_OBJECT );
      // 默认状态招募
      ( ( StaffVO ) form ).setStatus( "2" );

      // 跳转到新建界面
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 获得当前主键
         final String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得主键对应对象
         final StaffVO staffVO = staffService.getStaffVOByStaffId( id );
         // 刷新对象，初始化对象列表及国际化
         staffVO.reset( null, request );
         staffVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "staffForm", staffVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final StaffService staffService = ( StaffService ) getService( "staffService" );
            // 获得ActionForm
            final StaffVO staffVO = ( StaffVO ) form;

            staffVO.setAccountId( getAccountId( request, response ) );
            staffVO.setCreateBy( getUserId( request, response ) );
            staffVO.setModifyBy( getUserId( request, response ) );
            staffVO.setCorpId( getCorpId( request, response ) );
            staffVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            // 保存自定义Column
            staffVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 新建对象
            staffService.insertStaff( staffVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, staffVO, Operate.ADD, staffVO.getStaffId(), null );

            // 刷新常量
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
         // 清空Form条件
         ( ( StaffVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final StaffService staffService = ( StaffService ) getService( "staffService" );

            // 获得当前主键
            String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得主键对应对象
            final StaffVO staffVO = staffService.getStaffVOByStaffId( staffId );
            staffVO.update( ( StaffVO ) form );
            staffVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            staffVO.setRemark1( saveDefineColumns( request, accessAction ) );
            staffVO.setRole( getRole( request, response ) );
            staffVO.setCorpId( getCorpId( request, response ) );
            staffVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );

            // 修改对象
            staffService.updateStaff( staffVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, staffVO, Operate.MODIFY, staffVO.getStaffId(), null );

            // 刷新常量
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
         // 清空Form条件
         ( ( StaffVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 获得Action Form
         final StaffVO staffVO = ( StaffVO ) form;

         // 存在选中的ID
         if ( staffVO.getSelectedIds() != null && !staffVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : staffVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               staffVO.setStaffId( KANUtil.decodeStringFromAjax( selectedId ) );
               staffVO.setModifyBy( getUserId( request, response ) );
               staffVO.setModifyDate( new Date() );
               staffService.deleteStaff( staffVO );
            }

            insertlog( request, staffVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( staffVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         ( ( StaffVO ) form ).setSelectedIds( "" );
         ( ( StaffVO ) form ).setSubAction( SEARCH_OBJECT );
         // 刷新常量
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
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得PositionId
         final String encodePositionId = request.getParameter( "positionId" );
         final String positionId = Cryptogram.decodeString( URLDecoder.decode( encodePositionId != null ? encodePositionId : "", "UTF-8" ) );

         // Render调用
         out.println( StaffRender.getStaffThinkingCombo( request ) + StaffRender.getStaffsByPositionId( request, positionId ) );
         out.flush();
         out.close();

         // Ajax调用无跳转
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
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final UserService userService = ( UserService ) getService( "userService" );
         // 获得当前主键
         String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "staffId" ), "UTF-8" ) );
         // 初始化对象
         StaffVO staffVO = new StaffVO();

         // 如果存在
         if ( staffService.getStaffVOByStaffId( staffId ) != null )
         {
            staffVO = staffService.getStaffVOByStaffId( staffId );
            // 获取User对象，获得Username
            final UserVO userVO = userService.getUserVOByStaffId( staffId );
            if ( userVO != null && userVO.getUsername() != null )
            {
               staffVO.setUsername( userVO.getUsername() );
            }
         }

         // 添加Tab Number
         final int positionCount = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionCountByStaffId( staffId );
         request.setAttribute( "positionCount", positionCount );
         request.setAttribute( "staffForm", staffVO );

         // Ajax调用
         return mapping.findForward( "listSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 获取登录者详细信息
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final StaffService staffService = ( StaffService ) getService( "staffService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

            // 获得当前主键
            String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得主键对应对象
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

            // 保存自定义Column
            staffVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 修改对象
            staffService.updateBaseStaff( staffVO );
            // 修改对应的employee信息
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

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            // 刷新常量
            constantsInit( "initStaff", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initStaffBaseView", new String[] { getAccountId( request, response ), staffVO.getStaffId() } );
            constantsInit( "initPosition", getAccountId( request, response ) );
            constantsInit( "initBranch", getAccountId( request, response ) );
         }
         // 清空Form条件
         ( ( StaffVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化PrintWrite对象
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
         // 初始化PrintWrite对象
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

         // 修改对象
         staffService.updateBaseStaff( staffVO );
         // 修改对应的Employee信息
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
         // 刷新常量
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
