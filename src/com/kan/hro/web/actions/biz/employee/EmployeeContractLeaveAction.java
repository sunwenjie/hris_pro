/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractLeaveAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-8-23 上午11:01:46  
* 
*/
public class EmployeeContractLeaveAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE";

   /**
    * List employeeContractLeave
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
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         // 获得Action Form
         final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;

         // 添加自定义搜索内容######
         // employeeContractLeaveVO.setRemark1( generateDefineListSearches( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );

         // 如果子SubAction不为空
         if ( employeeContractLeaveVO.getSubAction() != null && !employeeContractLeaveVO.getSubAction().trim().equals( "" ) )
         {
            // 如果子SubAction是删除列表操作
            if ( employeeContractLeaveVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               // 调用删除列表的SubAction
               delete_objectList( mapping, form, request, response );
            }
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( employeeContractLeaveVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeContractLeaveVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractLeaveService.getEmployeeContractLeaveVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // 初始化PrintWrite对象
            final PrintWriter out = response.getWriter();

            // Send to client
            out.println( ListRender.generateListTable( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listEmployeeContractLeave" );
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
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // 添加页面Token
      this.saveToken( request );

      // 初始化Service
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

      // 获得ContractId
      final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

      // 获得EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // 默认值处理
      final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;
      employeeContractLeaveVO.setSubAction( CREATE_OBJECT );
      employeeContractLeaveVO.setContractId( contractId );
      employeeContractLeaveVO.setBenefitQuantity( "0" );
      employeeContractLeaveVO.setLegalQuantity( "0" );
      employeeContractLeaveVO.setCycle( "1" );
      employeeContractLeaveVO.setProbationUsing( "1" );
      employeeContractLeaveVO.setDelayUsing( "2" );
      employeeContractLeaveVO.setStatus( "1" );
      employeeContractLeaveVO.setDescription( "" );

      // 已添加的科目在前台排除
      List< Object > objects = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( contractId );
      String addedItems = "";
      for ( Object obj : objects )
      {
         final EmployeeContractLeaveVO tempVO = ( EmployeeContractLeaveVO ) obj;
         if ( "41".equalsIgnoreCase( tempVO.getItemId() ) )
            continue;

         addedItems += tempVO.getItemId();
         addedItems += ",";
      }

      // 排除年假、年假去年、销假
      addedItems += "48,49,60,";
      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );
      request.setAttribute( "addedItems", KANUtil.filterEmpty( addedItems ) == null ? addedItems : addedItems.substring( 0, addedItems.length() - 1 ) );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractLeave" );
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
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractLeaveVO ) form ).getEmployeeLeaveId();
         }

         // 获得EmployeeContractLeaveVO
         final EmployeeContractLeaveVO employeeContractLeaveVO = employeeContractLeaveService.getEmployeeContractLeaveVOByEmployeeLeaveId( id );
         employeeContractLeaveVO.reset( null, request );
         employeeContractLeaveVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractLeaveVO.getContractId() );
         employeeContractVO.reset( null, request );

         // 已添加的科目在前台排除(不包括自己)
         List< Object > objects = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( employeeContractLeaveVO.getContractId() );
         String addedItems = "";
         for ( Object obj : objects )
         {
            final EmployeeContractLeaveVO tempVO = ( EmployeeContractLeaveVO ) obj;
            if ( !tempVO.getItemId().equals( employeeContractLeaveVO.getItemId() ) )
            {
               addedItems += tempVO.getItemId();
               addedItems += ",";
            }
         }

         // 排除年假、年假去年、销假
         addedItems += "48,49,60,";
         // 设置Attribute
         request.setAttribute( "employeeContractLeaveForm", employeeContractLeaveVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
         request.setAttribute( "addedItems", KANUtil.filterEmpty( addedItems ) == null ? addedItems : addedItems.substring( 0, addedItems.length() - 1 ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractLeave" );
   }

   /**
    * Add Employee Contract Leave
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

            // 获得ActionForm
            final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;
            employeeContractLeaveVO.setCreateBy( getUserId( request, response ) );
            employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeContractLeaveVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );

            // 新建对象
            employeeContractLeaveService.insertEmployeeContractLeave( employeeContractLeaveVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractLeaveVO, Operate.ADD, employeeContractLeaveVO.getEmployeeLeaveId(), null );
         }
         // 重复提交处理 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractLeaveVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // 清空Form条件
         ( ( EmployeeContractLeaveVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract Leave
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

            // 获得当前主键
            final String employeeLeaveId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获得EmployeeContractLeaveVO
            final EmployeeContractLeaveVO employeeContractLeaveVO = employeeContractLeaveService.getEmployeeContractLeaveVOByEmployeeLeaveId( employeeLeaveId );
            employeeContractLeaveVO.update( ( EmployeeContractLeaveVO ) form );
            employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            employeeContractLeaveVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );

            // 修改对象
            employeeContractLeaveService.updateEmployeeContractLeave( employeeContractLeaveVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeContractLeaveVO, Operate.MODIFY, employeeContractLeaveVO.getEmployeeLeaveId(), null );
         }

         // 清空Form条件
         ( ( EmployeeContractLeaveVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract Leave list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

         // 获得Action Form
         final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;

         // 存在选中的ID
         if ( employeeContractLeaveVO.getSelectedIds() != null && !employeeContractLeaveVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractLeaveVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeContractLeaveVO.setEmployeeLeaveId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );
               employeeContractLeaveService.deleteEmployeeContractLeave( employeeContractLeaveVO );
            }

            insertlog( request, employeeContractLeaveVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractLeaveVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         employeeContractLeaveVO.setSelectedIds( "" );
         employeeContractLeaveVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract Leave by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

         // 获得当前主键
         String employeeLeaveId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeLeaveId" ) );

         // 删除主键对应对象
         final EmployeeContractLeaveVO employeeContractLeaveVO = employeeContractLeaveService.getEmployeeContractLeaveVOByEmployeeLeaveId( employeeLeaveId );
         employeeContractLeaveVO.setEmployeeLeaveId( employeeLeaveId );
         employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeContractLeaveService.deleteEmployeeContractLeave( employeeContractLeaveVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractLeaveVO, Operate.DELETE, employeeLeaveId, null );
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
    * List Object Options Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Siuvan
   // Reviewed by Kevin Jin at 2013-11-25
   // 如果是男.则不需要产假
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取科目ID
         final String itemId = request.getParameter( "itemId" );

         // 获取派送信息ID
         final String contractId = request.getParameter( "contractId" );

         boolean isFemale = true;
         // 初始化Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null )
         {
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            if ( employeeVO != null )
            {
               if ( "1".equals( employeeVO.getSalutation() ) )
               {
                  isFemale = false;
               }
            }

         }

         // 获取EmployeeContractLeaveVO列表（包含加班换休）
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );
         // 对于没有的item 这个适用于今年请明年的假, 假期用今年.  eg  16年12月请17年1月. 假期还是16年的
         boolean existItem = false;
         if(KANUtil.filterEmpty(itemId) !=null && employeeContractLeaveVOs!=null && employeeContractLeaveVOs.size()>0){
           for(EmployeeContractLeaveVO contractLeaveVO:employeeContractLeaveVOs){
             if(contractLeaveVO.getItemId().equals(itemId)){
               existItem = true;
               break;
             }
           }
           // 不存在就需要添加
           if(!existItem){
             EmployeeContractLeaveVO tmpVO = new EmployeeContractLeaveVO();
             tmpVO.setAccountId(getAccountId(request, response));
             tmpVO.setCorpId(getCorpId(request, response));
             tmpVO.setLocale(request.getLocale());
             tmpVO.setItemId(itemId);
             employeeContractLeaveVOs.add(tmpVO);
           }
         }

         // 初始化MappingVO列表
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, ( ( EmployeeContractLeaveVO ) form ).getEmptyMappingVO() );

         // 存在EmployeeContractLeaveVO列表（包含加班换休）
         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
            {
               employeeContractLeaveVO.reset( null, request );
               if ( "42".equals( employeeContractLeaveVO.getItemId() ) || "43".equals( employeeContractLeaveVO.getItemId() ) )
               {
                  continue;
               }
               // 如果是男,并且是产假
               if ( !isFemale && "45".equals( employeeContractLeaveVO.getItemId() ) )
               {
                  continue;
               }
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( employeeContractLeaveVO.getItemId() );
               mappingVO.setMappingValue( employeeContractLeaveVO.getDecodeItemId() );
               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "itemId", itemId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // 校验休假设置不能重复
   private boolean checkEmployeeContractLeave( final List< Object > employeeContractLeave, final String itemId, final String year, final String employeeLeaveId )
   {
      boolean flag = true;
      if ( employeeContractLeave != null && employeeContractLeave.size() > 0 )
      {
         for ( Object o : employeeContractLeave )
         {
            final EmployeeContractLeaveVO tempEmployeeContractLeaveVO = ( EmployeeContractLeaveVO ) o;
            if ( employeeLeaveId != null && tempEmployeeContractLeaveVO.getEmployeeLeaveId().equals( employeeLeaveId ) )
               continue;

            // 如果是年假
            if ( "41".equals( itemId ) )
            {
               if ( tempEmployeeContractLeaveVO.getItemId().equals( itemId ) && tempEmployeeContractLeaveVO.getYear().equals( year ) )
               {
                  flag = false;
                  break;
               }
            }
            else
            {
               if ( tempEmployeeContractLeaveVO.getItemId().equals( itemId ) )
               {
                  flag = false;
                  break;
               }
            }
         }
      }

      return flag;
   }

   public void checkEmployeeContractLeave_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

         final String contractId = request.getParameter( "contractId" );
         final String itemId = request.getParameter( "itemId" );
         final String year = request.getParameter( "year" );
         final String employeeLeaveId = request.getParameter( "employeeLeaveId" );

         final List< Object > employeeContractLeaveVOs = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( contractId );

         boolean exist = checkEmployeeContractLeave( employeeContractLeaveVOs, itemId, year, employeeLeaveId );

         out.print( exist ? "1" : "2" );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

}
