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
 *  手机客户端 请假 action
 * @author KW
 *
 */
public class LeaveAction_Wap extends BaseAction
{

   // 当前Action对应的Access Action
   private static String accessAction = "HRO_BIZ_LEAVE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
      StringBuffer returnJson = new StringBuffer();
      try
      {
         final PrintWriter out = response.getWriter();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // 获得Action Form
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
         // 需要设置当前用户AccountId
         leaveHeaderVO.setAccountId( getAccountId( request, response ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容
         leaveHeaderVO.setRemark1( generateDefineListSearches( request, accessAction ) );
         // 处理SubAction
         dealSubAction( leaveHeaderVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder leavePagedListHolder = new PagedListHolder();
         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            leavePagedListHolder.setPage( page );
            // 传入当前值对象
            leavePagedListHolder.setObject( leaveHeaderVO );
            // 设置页面记录条数
            leavePagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            leaveHeaderService.getLeaveHeaderVOsByCondition( leavePagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
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
      // 避免重复提交
      this.saveToken( request );
      // 设置Sub Action
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
    * 添加请假
    */
   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
            // 获得当前FORM
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
            // 设定当前用户
            leaveHeaderVO.setAccountId( getAccountId( request, response ) );
            leaveHeaderVO.setCreateBy( getUserId( request, response ) );
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用添加方法
            leaveHeaderService.insertLeaveHeader( leaveHeaderVO );
         }
         // 清空FORM
         ( ( LeaveHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 添加成功后查询一次返回
      // 跳转列表界面
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // 主键获取需解码
         final String leaveId = request.getParameter( "id" );
         // 获得LeaveVO对象
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
         // 刷新对象，初始化对象列表及国际化
         leaveHeaderVO.reset( null, request );
         // 区分修改添加
         leaveHeaderVO.setSubAction( VIEW_OBJECT );
         // 获取token
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
            // 初始化Service接口
            final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
            // 主键获取需解码
            final String leaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得LeaveVO对象
            final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
            // 装载界面传值
            leaveHeaderVO.update( ( ( LeaveHeaderVO ) form ) );
            // 获取登录用户
            leaveHeaderVO.setModifyBy( getUserId( request, response ) );
            if ( "".equals( leaveHeaderVO.getActualStartDate() ) )
            {
               leaveHeaderVO.setActualStartDate( null );
            }
            if ( "".equals( leaveHeaderVO.getActualEndDate() ) )
            {
               leaveHeaderVO.setActualEndDate( null );
            }
            // 调用修改方法
            leaveHeaderService.updateLeaveHeader( leaveHeaderVO );
            // 返回编辑成功标记
            //updateSuccess( request );

         }
         // 清空FORM
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
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // 获得Action Form
         final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) form;
         // 存在选中的ID

         // 调用删除接口
         leaveHeaderVO.setLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         leaveHeaderVO.setModifyDate( new Date() );
         leaveHeaderService.deleteLeaveHeader( leaveHeaderVO );

         // 清除Selected IDs和子Action
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
         // 初始化Service接口
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveService" );
         // 获得主键需解码
         final String leaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得LeaveVO对象
         final LeaveHeaderVO leaveHeaderVO = leaveHeaderService.getLeaveHeaderVOByLeaveHeaderId( leaveId );
         // 获取登录用户
         leaveHeaderVO.setModifyBy( getUserId( request, response ) );
         // 将请假状态变为 2 提交 - 待批准
         leaveHeaderVO.setStatus( "2" );
         leaveHeaderService.updateLeaveHeader( leaveHeaderVO );
         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE );
         // 清空FORM
         ( ( LeaveHeaderVO ) form ).reset();
         ( ( LeaveHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
         // 重定向list_object
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
         // Ajax调用
         return mapping.findForward( "listSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    *获取 科目和状态的下拉列表
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
         //添加请选择
         itemMappingVOs.add( 0, new MappingVO( "0", "请选择" ) );
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
      returnJson.append( "\"" ).append( "请选择" ).append( "\"" ).append( "," );
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
