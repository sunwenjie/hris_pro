package com.kan.hro.web.actions.biz.travel;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.travel.TravelVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.travel.TravelService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class TravelAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_TRAVEL";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取subAction
         final String subAction = getSubAction( form );

         // 初始化Service接口
         final TravelService travelService = ( TravelService ) getService( "travelService" );

         // 获得Action Form
         final TravelVO travelVO = ( TravelVO ) form;

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {

            setDataAuth( request, response, travelVO );
         }
         
         // 调用删除方法
         if ( travelVO.getSubAction() != null && travelVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         
         if ( new Boolean( ajax ) )
         {
            decodedObject( travelVO );
         }

         // 如果没有指定排序则默认按travelId排序
         if ( travelVO.getSortColumn() == null || travelVO.getSortColumn().isEmpty() )
         {
            travelVO.setSortColumn( "travelId" );
            travelVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder travelHolder = new PagedListHolder();

         // 传入当前页
         travelHolder.setPage( page );
         // 设置Object
         travelHolder.setObject( travelVO );
         // 设置页面记录条数
         travelHolder.setPageSize(listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         travelService.getTravelVOsByCondition( travelHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
         // 刷新Holder，国际化传值
         refreshHolder( travelHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "travelHolder", travelHolder );
         request.setAttribute( "role", getRole( request, response ) );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {

            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            // Ajax Table调用
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listTravelTable" );

         }

      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      return mapping.findForward( "listTravel" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 避免重复提交
      this.saveToken( request );

      // InHouse情况下，默认当前登录用户
      if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 初始化Service接口
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         final StaffVO staffVO = staffService.getStaffVOByStaffId( getStaffId( request, response ) );
         ( ( TravelVO ) form ).setEmployeeId( staffVO.getEmployeeId() );
      }
      else if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         ( ( TravelVO ) form ).setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
      }

      // 设置Sub Action
      ( ( TravelVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( TravelVO ) form ).setStatus( TravelVO.TRUE );

      // 跳转新增界面
      return mapping.findForward( "manageTravel" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service
            final TravelService travelService = ( TravelService ) getService( "travelService" );

            // 获取当前Form
            final TravelVO travelVO = ( TravelVO ) form;

            // 返回页面employeeId
            final String employeeId = travelVO.getEmployeeId();

            // 校验employeeId是否存在
            checkEmployeeId( mapping, travelVO, request, response );

            // 不合法的employeeId跳转新增页面
            if ( KANUtil.filterEmpty( ( String ) request.getAttribute( "employeeIdErrorMsg" ) ) != null )
            {
               travelVO.reset();
               travelVO.setEmployeeId( employeeId );
               travelVO.setNameZH( "" );
               travelVO.setNameEN( "" );

               return to_objectNew( mapping, travelVO, request, response );
            }

            travelVO.setCreateBy( getUserId( request, response ) );
            travelVO.setModifyBy( getUserId( request, response ) );

            // 添加
            int result = travelService.insertTravel( travelVO );

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
            }

         }

         // 清空FORM
         ( ( TravelVO ) form ).reset();
         ( ( TravelVO ) form ).setNameZH( "" );
         ( ( TravelVO ) form ).setNameEN( "" );

      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      // 跳转列表界面
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final TravelService travelService = ( TravelService ) getService( "travelService" );
         // 主键获取需解码
         String leaveId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( leaveId == null || leaveId.trim().isEmpty() )
         {
            leaveId = ( ( TravelVO ) form ).getTravelId();
         }

         // 获得LeaveVO对象
         final TravelVO travelVO = travelService.getTravelVOByTravelId( leaveId );

         // 刷新对象，初始化对象列表及国际化
         travelVO.reset( null, request );
         // 区分修改添加
         travelVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "travelForm", travelVO );

      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      // 跳转修改页面
      return mapping.findForward( "manageTravel" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final TravelService travelService = ( TravelService ) getService( "travelService" );
            // 主键获取需解码
            final String travelId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获得TravelVO对象
            final TravelVO travelVO = travelService.getTravelVOByTravelId( travelId );

            // 装载界面传值
            travelVO.update( ( ( TravelVO ) form ) );

            // 获取登录用户
            travelVO.setModifyBy( getUserId( request, response ) );
            travelVO.setModifyDate( new Date() );

            travelService.updateTravel( travelVO );
            success( request, MESSAGE_TYPE_UPDATE );

         }
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final TravelService travelService = ( TravelService ) getService( "travelService" );
         // 获得Action Form
         TravelVO travelVO = ( TravelVO ) form;

         // 存在选中的ID
         if ( KANUtil.filterEmpty( travelVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : travelVO.getSelectedIds().split( "," ) )
            {
               travelVO = travelService.getTravelVOByTravelId( KANUtil.decodeStringFromAjax( selectedId ) );
               travelVO.setModifyBy( getUserId( request, response ) );
               travelVO.setModifyDate( new Date() );

               if ( travelService.deleteTravel( travelVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_DELETE );
               }
            }
         }

         // 清除Selected IDs和子Action
         ( ( TravelVO ) form ).setSelectedIds( "" );
         ( ( TravelVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

   }

   /**  
    * 检查输入EmployeeId是否有效
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

      // 获得ActionForm
      final TravelVO travelVO = ( TravelVO ) form;

      // 试图获取EmployeeVO 
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( travelVO.getEmployeeId() );

      // 不存在EmployeeVO或AccountId不匹配当前
      if ( employeeVO == null
            || ( employeeVO != null && KANUtil.filterEmpty( employeeVO.getAccountId() ) != null && !employeeVO.getAccountId().equals( getAccountId( request, response ) ) ) )
      {
         request.setAttribute( "employeeIdErrorMsg", ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) ? "员工" : "雇员" ) + "ID无效；" );
      }

   }
   
   
   // 批量提交
   // Added by siuxia at 2014-06-04
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm
         final TravelVO travelVO = ( TravelVO ) form;
         // 初始化Service接口
         final TravelService travelService = ( TravelService ) getService( "travelService" );

         // 获得勾选ID
         final String travelIds = travelVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( travelIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = travelIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String selectId : selectedIdArray )
            {
               // 获得LeaveVO
               final TravelVO submitTravelVO = travelService.getTravelVOByTravelId( KANUtil.decodeStringFromAjax( selectId ) );
               submitTravelVO.setModifyBy( getUserId( request, response ) );
               submitTravelVO.setModifyDate( new Date() );
               submitTravelVO.reset( mapping, request );

               rows = rows + travelService.submitTravel( submitTravelVO );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
