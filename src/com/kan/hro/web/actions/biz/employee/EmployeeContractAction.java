/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.LocationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.service.inf.management.LaborContractTemplateService;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.tag.AuthConstants;
import com.kan.base.tag.AuthUtils;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.pdf.PDFTool;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPDFVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractPropertyService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSettlementService;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractAction  
* 类描述：  
* 创建人：Jixiang   
*   
*/
// TODO 后续真正意义上的劳动合同独立出去
public class EmployeeContractAction extends BaseAction
{
   // 劳动合同
   public static String accessActionLabor = "HRO_BIZ_EMPLOYEE_LABOR_CONTRACT";
   // 服务协议
   public static String ACCESS_ACTION_SERVICE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT";
   // 劳动合同（In House）
   public static String ACCESS_ACTION_SERVICE_IN_HOUSE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE";

   // 当前Action对应的Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         return ACCESS_ACTION_SERVICE_IN_HOUSE;
      }
      else
      {
         return ACCESS_ACTION_SERVICE;
      }
   }

   /**  
    * Get Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward get_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null && employeeContractVO.getAccountId() != null && employeeContractVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE )
                  || ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) && employeeContractVO.getCorpId() != null && employeeContractVO.getCorpId().equals( getCorpId( request, response ) ) ) )
            {
               employeeContractVO.reset( mapping, request );
               jsonObject = JSONObject.fromObject( employeeContractVO );
               jsonObject.put( "success", "true" );
            }
            else
            {
               jsonObject.put( "success", "false" );
            }
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to client
         out.println( jsonObject != null ? jsonObject.toString() : "" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   /**  
    * Get Object Ajax Popup
    * 模态框修改合同时间
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );
         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         // 查询EmployeeContractVO 对应的社保、商保集合
         final List< Object > allEmployeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );
         final List< Object > allEmployeeContractCBVOs = employeeContractCBService.getEmployeeContractCBVOsByContractId( contractId );
         // 初始化社保、商保操作集合
         List< Object > employeeContractSBVOs = new ArrayList< Object >();
         List< Object > employeeContractCBVOs = new ArrayList< Object >();

         // Reset EmployeeContractVO
         employeeContractVO.reset( mapping, request );
         employeeContractVO.getEmployStatuses().remove( 0 );

         // Reset List(2:待申报加保，3:正常缴纳 状态前端显示)
         if ( allEmployeeContractSBVOs != null && allEmployeeContractSBVOs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : allEmployeeContractSBVOs )
            {
               EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;

               if ( employeeContractSBVO.getStatus().equals( "2" ) || employeeContractSBVO.getStatus().equals( "3" ) )
               {
                  employeeContractSBVO.reset( mapping, request );
                  employeeContractSBVOs.add( employeeContractSBVO );
               }

            }
         }

         // Reset List(2:待申购，3:正常购买 状态前端显示)
         if ( allEmployeeContractCBVOs != null && allEmployeeContractCBVOs.size() > 0 )
         {
            for ( Object employeeContractCBVOObject : allEmployeeContractCBVOs )
            {
               EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObject;
               if ( employeeContractCBVO.getStatus().equals( "2" ) || employeeContractCBVO.getStatus().equals( "3" ) )
               {
                  employeeContractCBVO.reset( mapping, request );
                  employeeContractCBVOs.add( employeeContractCBVO );
               }
            }
         }

         final String defineMinEndDate = KANUtil.formatDate( KANUtil.getDate( new Date(), 0, -2 ), null );
         final String defineMaxEndDate = KANUtil.formatDate( KANUtil.getDate( employeeContractVO.getEndDate(), 0, +1 ), null );

         // 传送值
         request.setAttribute( "role", getRole( request, response ) );
         request.setAttribute( "defineMinEndDate", defineMinEndDate );
         request.setAttribute( "defineMaxEndDate", defineMaxEndDate );
         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "employeeContractSBVOs", employeeContractSBVOs );
         request.setAttribute( "employeeContractCBVOs", employeeContractCBVOs );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "handleEmployeeContractPopup" );
   }

   /**
    * List Employee Contract
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
         final String accessAction = getAccessAction( request, response );

         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // HR_Service登录、IN_House登录
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //            if ( isHRFunction( request, response ) )
            //            {
            //               setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractVO );
            //            }
            //            else
            //            {
            //               employeeContractVO.setEmployeeId( getEmployeeId( request, response ) );
            //            }
            setDataAuth( request, response, employeeContractVO );
         }
         // 员工登录
         else if ( KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {
            employeeContractVO.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
            employeeContractVO.setStatus( "3" );
         }

         // 过滤OrderId搜索条件
         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 如果没有指定排序则默认按employeeId排序
         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "status,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }
         employeeContractVO.setRemark1( generateDefineListSearches( request, getAccessAction( request, response ) ) );
         // SubAction处理
         dealSubAction( employeeContractVO, mapping, form, request, response );

         employeeContractVO.setFlag( "2" );
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            employeeContractVO.setClientId( BaseAction.getClientId( request, response ) );
         }
         request.setAttribute( "flag", "2" );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 设置Object
            pagedListHolder.setObject( employeeContractVO );
            // 设置页面记录条数
            pagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            employeeContractService.getEmployeeContractVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( pagedListHolder, request );
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         // 如果是In House登录
         if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            return dealReturn( getAccessAction( request, response ), "listEmployeeContractInHouse", mapping, form, request, response );
         }
         else
         {
            return dealReturn( getAccessAction( request, response ), "listEmployeeContract", mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Employee Contract by Order
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-14
   public ActionForward list_object_order( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // SubAction处理
         dealSubAction( employeeContractVO, mapping, form, request, response );

         // 初始化PagedListHolder
         final PagedListHolder serviceContractHolder = new PagedListHolder();

         // 获得OrderId
         String orderId = KANUtil.decodeString( request.getParameter( "orderId" ) );
         if ( KANUtil.filterEmpty( orderId ) == null )
         {
            orderId = employeeContractVO.getOrderId();
         }

         if ( KANUtil.filterEmpty( orderId ) != null )
         {
            employeeContractVO.setOrderId( orderId );
            employeeContractVO.setFlag( "2" );

            if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().trim().isEmpty() )
            {
               employeeContractVO.setSortColumn( "contractId" );
               employeeContractVO.setSortOrder( "desc" );
            }

            // 传入当前值对象
            serviceContractHolder.setObject( employeeContractVO );
            // 传入当前页
            serviceContractHolder.setPage( page );
            // 设置页面记录条数
            serviceContractHolder.setPageSize( this.listPageSize );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            employeeContractService.getEmployeeContractVOsByCondition( serviceContractHolder, true );
            // 刷新Holder，国际化传值
            refreshHolder( serviceContractHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "serviceContractHolder", serviceContractHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listServiceContractTable" );
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
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 初始化EmployeeContractVO
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

      // 初始化EmployeeId， OrderId，Flag
      final String employeeId = employeeContractVO.getEmployeeId();
      final String orderId = employeeContractVO.getOrderId();
      final String flag = employeeContractVO.getFlag();

      // 清空Form
      employeeContractVO.reset();

      // 如果EmployeeId不为空，解密
      if ( KANUtil.filterEmpty( employeeId ) != null )
      {
         employeeContractVO.setEmployeeId( KANUtil.decodeString( employeeId ) );
      }

      // 结束时间（预计），默认当前开始三年
      //final Date tempEndDate = KANUtil.getDate( new Date(), 3, 0, -1 );
      final Date tempProbationEndDate = KANUtil.getDate( new Date(), 0, 3, 0 );

      // 默认开始时间和结束时间
      employeeContractVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
      //employeeContractVO.setEndDate( KANUtil.formatDate( tempEndDate, "yyyy-MM-dd" ) );
      employeeContractVO.setProbationEndDate( KANUtil.formatDate( tempProbationEndDate, "yyyy-MM-dd" ) );

      // 如果OrderId不为空，解密
      if ( KANUtil.filterEmpty( orderId ) != null )
      {
         employeeContractVO.setOrderId( KANUtil.decodeString( orderId ) );

         // 初始化Service接口
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getOrderHeaderId() ) != null && clientOrderHeaderVO.getContractPeriod() != null )
         {
            employeeContractVO.setEndDate( KANUtil.formatDate( KANUtil.getDate( new Date(), Integer.valueOf( clientOrderHeaderVO.getContractPeriod() ), 0, -1 ), "yyyy-MM-dd" ) );
         }
         //试用期结算日期
         if ( clientOrderHeaderVO != null && StringUtils.isNotBlank( clientOrderHeaderVO.getOrderHeaderId() ) && StringUtils.isNotBlank( clientOrderHeaderVO.getProbationMonth() ) )
         {
            employeeContractVO.setProbationEndDate( KANUtil.formatDate( KANUtil.getDate( new Date(), 0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" ) );
         }
         // 合同结束时间不足三年的情况
         /*if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getOrderHeaderId() ) != null
               && KANUtil.getDays( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ) ) < KANUtil.getDays( tempEndDate ) )
         {
            employeeContractVO.setEndDate( KANUtil.formatDate( KANUtil.createDate( clientOrderHeaderVO.getEndDate() ), "yyyy-MM-dd" ) );
         }*/
         employeeContractVO.setCurrency( clientOrderHeaderVO.getCurrency() );
      }

      employeeContractVO.setSubAction( CREATE_OBJECT );
      employeeContractVO.setEmployStatus( "1" );
      employeeContractVO.setLocked( "2" );
      employeeContractVO.setFlag( flag );
      employeeContractVO.setStatus( "1" );
      employeeContractVO.setRemark3( "1" );

      // 初始化PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      if ( positionVO != null )
      {
         employeeContractVO.setBranch( positionVO.getBranchId() );
         employeeContractVO.setOwner( positionVO.getPositionId() );
      }

      // 如果是INHOUSE
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         return mapping.findForward( "manageEmployeeContractInHouse" );
      }
      else
      {
         // 劳动合同
         if ( flag != null && flag.trim().equals( "1" ) )
         {
            return mapping.findForward( "manageEmployeeContract" );
         }
         // 服务协议
         else
         {
            return mapping.findForward( "manageEmployeeContractSEV" );
         }
      }
   }

   /**
    * Add Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   // Modify BY Jixiang Hu 2013-11-26
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化输入值错误数
            request.setAttribute( "errorCount", 0 );
            // 检查页面输入值
            checkEmployeeId( mapping, form, request, response );
            checkOrderId( mapping, form, request, response );

            // 页面跳转控制
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // 初始化Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 获得ActionForm
            final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
            employeeContractVO.setCreateBy( getUserId( request, response ) );
            employeeContractVO.setModifyBy( getUserId( request, response ) );

            // 如果是Inhouse操作。需要手动设置ClientId
            if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               employeeContractVO.setClientId( getClientId( request, null ) );
            }

            //引用结算规则的货币类型
            if ( KANUtil.filterEmpty( employeeContractVO.getCurrency(), "0" ) == null )
            {
               ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getCurrency() != null && !"".equals( clientOrderHeaderVO.getCurrency() ) )
               {
                  employeeContractVO.setCurrency( clientOrderHeaderVO.getCurrency() );
               }
            }

            final String flag = employeeContractVO.getFlag();

            if ( flag == null || flag.trim().isEmpty() )
            {
               //默认添加劳动合同
               employeeContractVO.setFlag( "1" );
            }

            // InHouse, Flag: 2
            if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               employeeContractVO.setFlag( "2" );
            }

            // 验证日期是否重复
            if ( employeeContractService.checkContractConflict( employeeContractVO ) )
            {
               if ( KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) || getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  error( request, null, "时间段内已存在劳动合同" );
               }
               else
               {
                  error( request, null, "时间段内已存在派送信息" );
               }
               employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
               employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );

               return to_objectNew( mapping, form, request, response );
            }
            else
            {

               // 保存自定义Column
               employeeContractVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

               if ( employeeContractVO.getTemplateId() != null && !"0".equals( employeeContractVO.getTemplateId() ) )
               {
                  LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );
                  employeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
               }

               employeeContractService.insertEmployeeContract( employeeContractVO );

               // 判断是否需要转向
               String forwardURL = request.getParameter( "forwardURL" );
               if ( forwardURL != null && !forwardURL.trim().isEmpty() )
               {
                  // 生成转向地址
                  forwardURL = forwardURL + employeeContractVO.getEncodedId();
                  request.getRequestDispatcher( forwardURL ).forward( request, response );

                  return null;
               }

               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD );

               // 插入日志
               insertAddEmployeeContractVOLog( ( LogService ) getService( "logService" ), employeeContractVO, getIPAddress( request ) );

               // 如果是点击提交按钮
               if ( SUBMIT_OBJECT.equalsIgnoreCase( ( ( EmployeeContractVO ) form ).getSubAction() ) )
               {
                  // 提交时，部分值不能被前台带过来,导致不仅仅修改状态。（eg:deleted）
                  final EmployeeContractVO tempEmployeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
                  tempEmployeeContractVO.reset( mapping, request );
                  //modify by Jack.sun 20140530
                  tempEmployeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
                  employeeContractService.submitEmployeeContract( tempEmployeeContractVO );
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
            }

            // 生成转向地址
            String forwardToModify = "employeeContractAction.do?proc=to_objectModify&flag=" + employeeContractVO.getFlag() + "&id=" + employeeContractVO.getEncodedId();
            request.getRequestDispatcher( forwardToModify ).forward( request, response );

            return null;

         }
         else
         {
            final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
            employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
            employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );
            return to_objectNew( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Continue Object
    *	劳动合同续签
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward continue_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( request.getParameter( "contractId" ) ) != null )
         {
            // 初始化Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // 获取ContractId
            final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
            // 获取EmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            // 无固定期限的劳动合同无需续签
            if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null )
            {
               final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
               final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               // 装载新EmployeeContractVO
               ( ( EmployeeContractVO ) form ).update( employeeContractVO );
               ( ( EmployeeContractVO ) form ).setMasterContractId( employeeContractVO.getContractId() );
               ( ( EmployeeContractVO ) form ).setClientId( employeeContractVO.getClientId() );
               ( ( EmployeeContractVO ) form ).setStartDate( KANUtil.formatDate( KANUtil.getDate( employeeContractVO.getEndDate(), 0, 0, 1 ) ) );
               ( ( EmployeeContractVO ) form ).setEndDate( KANUtil.formatDate( KANUtil.getDate( ( ( EmployeeContractVO ) form ).getStartDate(), 3, 0, -1 ) ) );
               if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getOrderHeaderId() ) != null && clientOrderHeaderVO.getContractPeriod() != null )
               {
                  ( ( EmployeeContractVO ) form ).setEndDate( KANUtil.formatDate( KANUtil.getDate( ( ( EmployeeContractVO ) form ).getStartDate(), Integer.valueOf( clientOrderHeaderVO.getContractPeriod() ), 0, -1 ), "yyyy-MM-dd" ) );
               }
               ( ( EmployeeContractVO ) form ).setPeriod( String.valueOf( KANUtil.getGapMonth( KANUtil.formatDate( employeeContractVO.getStartDate() ), KANUtil.formatDate( employeeContractVO.getEndDate() ) ) ) );
               // 续签的合同默认状态为“新建”
               ( ( EmployeeContractVO ) form ).setStatus( "1" );
               ( ( EmployeeContractVO ) form ).setCreateBy( getUserId( request, response ) );
               ( ( EmployeeContractVO ) form ).setModifyBy( getUserId( request, response ) );
               ( ( EmployeeContractVO ) form ).setCreateDate( new Date() );
               ( ( EmployeeContractVO ) form ).setModifyDate( new Date() );

               // 保存自定义Column
               if ( KANConstants.ROLE_HR_SERVICE.equals( getRole( request, response ) ) )
               {
                  ( ( EmployeeContractVO ) form ).setRemark1( saveDefineColumns( request, ACCESS_ACTION_SERVICE ) );
               }
               else if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
               {
                  ( ( EmployeeContractVO ) form ).setRemark1( saveDefineColumns( request, ACCESS_ACTION_SERVICE_IN_HOUSE ) );
               }

               // 新建对象
               if ( employeeContractService.continueEmployeeContract( ( EmployeeContractVO ) form ) > 0 )
               {
                  // 返回添加成功标记
                  success( request, null, "续签劳动合同成功！", MESSAGE_HEADER );
               }
               else
               {
                  error( request, null, "续签劳动合同不成功！", MESSAGE_HEADER );
               }
            }
            else
            {
               warning( request, null, "无固定期限的劳动合同不能再被续签！", MESSAGE_HEADER );
            }
         }
         else
         {
            error( request, null, "续签劳动合同不成功！", MESSAGE_HEADER );
            return null;
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
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
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contractId == null || contractId.trim().isEmpty() )
         {
            contractId = ( ( EmployeeContractVO ) form ).getContractId();
         }

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "author_new", AuthUtils.hasAuthority( getAccessAction( request, response ), AuthConstants.RIGHT_NEW, employeeContractVO.getOwner(), request, null ) );

         final String flag = employeeContractVO.getFlag();

         if ( flag != null && "2".equals( flag ) && !KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            // 跳转到服务协议新建界面
            return mapping.findForward( "manageEmployeeContractSEV" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // 跳转到 劳动合同 IN HOUSE
         return mapping.findForward( "manageEmployeeContractInHouse" );
      }
      else
      {
         // 跳转到劳动合同新建界面
         return mapping.findForward( "manageEmployeeContract" );
      }

   }

   /**
    * Modify Employee Contract
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
            // 初始化输入值错误数
            request.setAttribute( "errorCount", 0 );

            // 检查页面输入值
            checkEmployeeId( mapping, form, request, response );
            checkOrderId( mapping, form, request, response );

            // 页面跳转控制
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // 初始化Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 获得当前主键
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获得EmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
            employeeContractVO.setRemark3( ( ( EmployeeContractVO ) form ).getRemark3() );
            employeeContractVO.update( ( EmployeeContractVO ) form );

            //引用结算规则的货币类型
            if ( KANUtil.filterEmpty( employeeContractVO.getCurrency(), "0" ) == null )
            {
               ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getCurrency() != null && !"".equals( clientOrderHeaderVO.getCurrency() ) )
               {
                  employeeContractVO.setCurrency( clientOrderHeaderVO.getCurrency() );
               }
            }

            if ( null != employeeContractVO.getTemplateId() && !"0".equals( employeeContractVO.getTemplateId() ) )
            {
               LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );
               employeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
            }

            if ( KANConstants.ROLE_HR_SERVICE.equals( getRole( request, response ) ) )
            {
               employeeContractVO.setClientId( ( ( EmployeeContractVO ) form ).getClientId() );
            }

            employeeContractVO.setModifyBy( getUserId( request, response ) );

            // 验证日期是否重复
            if ( employeeContractService.checkContractConflict( employeeContractVO ) )
            {
               if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                     || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
               {
                  error( request, null, "时间段内已存在劳动合同" );
               }
               else
               {
                  error( request, null, "时间段内已存在派送信息" );
               }

               employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
               employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );

               employeeContractVO.setSubAction( VIEW_OBJECT );
               employeeContractVO.reset( null, request );
               employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

               request.setAttribute( "employeeContractForm", employeeContractVO );
               request.setAttribute( "author_new", AuthUtils.hasAuthority( getAccessAction( request, response ), AuthConstants.RIGHT_NEW, employeeContractVO.getOwner(), request, null ) );

               return to_prePage( mapping, form, request, response );
            }
            else
            {
               // 保存自定义Column
               employeeContractVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

               // 判断是否需要转向
               final String forwardURL = request.getParameter( "forwardURL" );

               if ( KANUtil.filterEmpty( forwardURL ) != null )
               {
                  // 修改对象，Siuvan & Kevin移动
                  employeeContractService.updateEmployeeContract( employeeContractVO );
                  ( ( EmployeeContractVO ) form ).reset();

                  // 生成转向地址
                  request.getRequestDispatcher( forwardURL ).forward( request, response );

                  return null;
               }
            }

            // 提交
            if ( KANUtil.filterEmpty( getSubAction( form ) ) != null && KANUtil.filterEmpty( getSubAction( form ) ).equals( SUBMIT_OBJECT ) )
            {
               employeeContractVO.reset( mapping, request );

               if ( employeeContractVO.getWorkflowId() == null )
               {
                  employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
                  String accountId = getAccountId( request, response );
                  KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
                  List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );
                  List< Object > objects = getEmployeeContractPDFVos( employeeContractVO, accountConstants, request );
                  final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( employeeContractVO.getContent(), constantVO, request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );
                  employeeContractVO.setConstantVOs( constantVOs );
                  employeeContractService.submitEmployeeContract( employeeContractVO );
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, employeeContractVO, Operate.SUBMIT, employeeContractVO.getContractId(), null );
               }
               else
               {
                  warning( request, MESSAGE_TYPE_SUBMIT, "已经存在工作流请不要重复提交！" );
               }
            }
            // 如果是修改
            else if ( KANUtil.filterEmpty( getSubAction( form ) ) != null && KANUtil.filterEmpty( getSubAction( form ) ).equals( MODIFY_OBJECT ) )
            {
               employeeContractService.updateEmployeeContract( employeeContractVO );
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractVO, Operate.MODIFY, employeeContractVO.getContractId(), null );
            }
         }

         // 清空Form
         ( ( EmployeeContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Popup
    * 模态框控制雇员离职
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Modify by siuvan.xia @2014-06-27
   public ActionForward modify_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
            final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

            // 获得当前主键
            final String contractId = request.getParameter( "contractId" );
            final String resignDate = request.getParameter( "resignDate" );
            final String lastWorkDate = request.getParameter( "lastWorkDate" );
            final String employStatus = request.getParameter( "employStatus" );
            final String leaveReasons = request.getParameter( "leaveReasons" );
            final String[] solutionId_sbs = request.getParameterValues( "solutionId_sb" );
            final String[] endDate_sb = request.getParameterValues( "endDate_sb" );
            final String[] solutionId_cbs = request.getParameterValues( "solutionId_cb" );
            final String[] endDate_cb = request.getParameterValues( "endDate_cb" );

            final String payment = request.getParameter( "payment" );
            final String hireAgain = request.getParameter( "hireAgain" );
            final String remark5 = request.getParameter( "remark5" );//HR Comments

            final String delete = request.getParameter( "delete" );

            // 初始化劳动合同、包含社保方案、商保方案 修改记录数
            int employeeContractCount = 0;
            int employeeContractSBCount = 0;
            int employeeContractCBCount = 0;

            // 获取EmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            // 离职日期不为空
            if ( KANUtil.filterEmpty( resignDate ) != null )
            {
               employeeContractVO.reset( null, request );
               employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
               employeeContractVO.setResignDate( resignDate );
               employeeContractVO.setLastWorkDate( lastWorkDate );
               employeeContractVO.setEmployStatus( employStatus );
               employeeContractVO.setLeaveReasons( leaveReasons );
               employeeContractVO.setModifyBy( getUserId( request, response ) );
               employeeContractVO.setModifyDate( new Date() );
               employeeContractVO.setPayment( payment );
               employeeContractVO.setHireAgain( hireAgain );
               employeeContractVO.setRemark5( remark5 );
               employeeContractVO.setIp( getIPAddress( request ) );
               //不计算工资同时删除
               if ( "2".equals( payment ) && "2".equals( delete ) )
               {
                  employeeContractVO.setStatus( "1" );
                  employeeContractVO.setDeleted( delete );
               }
               // 离职审核
               employeeContractCount = employeeContractService.submitEmployeeContract_leave( employeeContractVO );
            }

            // 存在社保退保时间
            if ( solutionId_sbs != null && solutionId_sbs.length > 0 )
            {
               for ( int i = 0; i < solutionId_sbs.length; i++ )
               {
                  // 获取EmployeeContractSBVO
                  final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( solutionId_sbs[ i ] );

                  if ( employeeContractSBVO != null )
                  {
                     employeeContractSBVO.reset( null, request );
                     final String endDate = endDate_sb[ i ];
                     if ( endDate != null && !endDate.trim().isEmpty() )
                     {
                        employeeContractSBVO.setEndDate( endDate );
                        employeeContractSBVO.setModifyBy( getUserId( request, response ) );
                        employeeContractSBVO.setModifyDate( new Date() );

                        employeeContractSBCount = employeeContractSBCount + employeeContractSBService.submitEmployeeContractSB_rollback( employeeContractSBVO );
                     }
                  }
               }
            }

            // 存在商保退保时间
            if ( solutionId_cbs != null && solutionId_cbs.length > 0 )
            {
               for ( int i = 0; i < solutionId_cbs.length; i++ )
               {
                  // 获取EmployeeContractCBVO
                  final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( solutionId_cbs[ i ] );

                  if ( employeeContractCBVO != null )
                  {
                     employeeContractCBVO.reset( null, request );
                     final String endDate = endDate_cb[ i ];
                     if ( endDate != null && !endDate.trim().isEmpty() )
                     {
                        employeeContractCBVO.setEndDate( endDate );
                        employeeContractCBVO.setModifyBy( getUserId( request, response ) );
                        employeeContractCBVO.setModifyDate( new Date() );

                        employeeContractCBCount = employeeContractCBCount + employeeContractCBService.submitEmployeeContractCB_rollback( employeeContractCBVO );
                     }
                  }
               }
            }

            if ( employeeContractCount == 0 && employeeContractSBCount == 0 && employeeContractCBCount == 0 )
            {
               // 返回添加成功标记
               error( request, null, "未选择有效离职时间或者 社保/商保 结束时间，数据无修改  。" );
            }
            else
            {
               final StringBuilder str = new StringBuilder();

               if ( employeeContractCount != 0 )
               {
                  str.append( KANUtil.getProperty( request.getLocale(), "message.prompt.resign.submit.success" ) );
               }

               if ( employeeContractSBCount != 0 || employeeContractCBCount != 0 )
               {
                  str.append( "（" );

                  if ( employeeContractSBCount != 0 )
                  {
                     str.append( Math.abs( employeeContractSBCount ) + "条社保退保" + ( employeeContractSBCount < 0 ? "提交" : "" ) + "成功" );
                  }

                  if ( employeeContractCBCount != 0 )
                  {
                     str.append( "，" + Math.abs( employeeContractCBCount ) + "条商保退保" + ( employeeContractSBCount < 0 ? "提交" : "" ) + "成功" );
                  }

                  str.append( "）" );
               }
               // 返回添加成功标记
               success( request, null, str.toString() );
               insertlog( request, employeeContractVO, Operate.SUBMIT, employeeContractVO.getContractId(), "离职提交" );
            }

         }

         // 清空Form
         request.setAttribute( "flag", "2" );
         ( ( EmployeeContractVO ) form ).reset();
         ( ( EmployeeContractVO ) form ).setContractId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Renew Employee Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward renew_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String contractId = request.getParameter( "contractId" );

         // 获得主键对应未修改前对象
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         // 修改对象数据
         employeeContractVO.update( ( EmployeeContractVO ) form );
         employeeContractVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
         employeeContractService.updateEmployeeContract( employeeContractVO );
         // 返回编辑成功标记
         success( request, null, "延期成功！" );
         insertlog( request, employeeContractVO, Operate.MODIFY, employeeContractVO.getContractId(), "renew_object" );
         // 清空Form条件
         ( ( EmployeeContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Employee Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // 删除主键对应对象
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setContractId( contractId );
         employeeContractVO.setModifyBy( getUserId( request, response ) );
         employeeContractService.deleteEmployeeContract( employeeContractVO );
         insertlog( request, employeeContractVO, Operate.DELETE, employeeContractVO.getContractId(), null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract list
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
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         if ( employeeContractVO.getSelectedIds() != null && !employeeContractVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeContractVO.setContractId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractVO.setModifyBy( getUserId( request, response ) );
               employeeContractService.deleteEmployeeContract( employeeContractVO );
            }

            insertlog( request, employeeContractVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         employeeContractVO.setSelectedIds( "" );
         employeeContractVO.setSubAction( "" );
         employeeContractVO.setContractId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
   *  List Special Info HTML
   *  
   *  @param mapping
   *  @param form
   *  @param request
   *  @param response
   *  @return
   *  @throws KANException
   */
   // Reviewed by Kevin Jin at 2013-11-16
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化ContractId
         final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

         //  加载薪酬方案列表
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );

         final List< Object > tempEmployeeContractSalaryVOs = new ArrayList< Object >();
         if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
         {
            for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
               if ( ( KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) == null )
                     || ( KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) != null && KANUtil.getDays( KANUtil.getDateAfterMonth( KANUtil.createDate( employeeContractSalaryVO.getEndDate() ), 3 ) ) >= KANUtil.getDays( new Date() ) ) )
               {
                  employeeContractSalaryVO.reset( null, request );
                  tempEmployeeContractSalaryVOs.add( employeeContractSalaryVO );
               }
            }
         }

         request.setAttribute( "employeeContractSalaryVOs", tempEmployeeContractSalaryVOs );
         request.setAttribute( "numberOfContractSalary", tempEmployeeContractSalaryVOs == null ? 0 : tempEmployeeContractSalaryVOs.size() );

         //  加载社保方案列表
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : employeeContractSBVOs )
            {
               ( ( ActionForm ) employeeContractSBVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractSBVOs", employeeContractSBVOs );
         request.setAttribute( "numberOfContractSB", employeeContractSBVOs == null ? 0 : employeeContractSBVOs.size() );

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         //加载附件列表
         if ( employeeContractVO == null )
         {
            employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setFlag( request.getParameter( "flag" ) );
         }

         // 刷新对象，初始化对象列表及国际化
         employeeContractVO.reset( null, request );

         // 加载应酬供应商 ,如果没有社保方案则参考结算规则里面的社保方案
         final List< String > headerIds = new ArrayList< String >();
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            for ( Object obj : employeeContractSBVOs )
            {
               headerIds.add( ( ( EmployeeContractSBVO ) obj ).getSbSolutionId() );
            }

         }
         else
         {
            // 使用结算规则里面的社保方案
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );
            for ( Object obj : clientOrderSBVOs )
            {
               headerIds.add( ( ( ClientOrderSBVO ) obj ).getSbSolutionId() );
            }
         }
         final SocialBenefitSolutionHeaderVO condSBSolutionHeaderVO = new SocialBenefitSolutionHeaderVO();
         condSBSolutionHeaderVO.setAccountId( getAccountId( request, response ) );
         condSBSolutionHeaderVO.setCorpId( getCorpId( request, response ) );
         condSBSolutionHeaderVO.setHeaderIds( headerIds );
         // 3 代缴社保
         condSBSolutionHeaderVO.setServiceIds( "3" );
         final List< Object > vendorVOs = vendorService.getVendorVOsBySBSolutionHeaderVO( condSBSolutionHeaderVO );
         List< MappingVO > salaryVendors = new ArrayList< MappingVO >();
         // 添加请选择
         salaryVendors.add( new MappingVO( "0", "1".equals( getRole( request, null ) ) ? ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "参考订单设置" : "Quote Order" )
               : ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "参照结算规则" : "Quote Calculation Rule" ) ) );
         for ( Object vendorObject : vendorVOs )
         {
            final VendorVO tempVendorVO = ( VendorVO ) vendorObject;
            final MappingVO tempMappingVO = new MappingVO();
            tempMappingVO.setMappingId( tempVendorVO.getVendorId() );
            tempMappingVO.setMappingValue( "zh".equalsIgnoreCase( getLocale( request ).getLanguage() ) ? tempVendorVO.getNameZH() : tempVendorVO.getNameEN() );
            salaryVendors.add( tempMappingVO );
         }
         employeeContractVO.setSalaryVendors( salaryVendors );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "numberOfContractAttachment", employeeContractVO.getAttachmentArray() != null ? employeeContractVO.getAttachmentArray().length : 0 );

         //  加载社商报案列表
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final List< Object > employeeContractCBVOs = employeeContractCBService.getEmployeeContractCBVOsByContractId( contractId );

         if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
         {
            for ( Object employeeContractCBVOObject : employeeContractCBVOs )
            {
               ( ( ActionForm ) employeeContractCBVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractCBVOs", employeeContractCBVOs );
         request.setAttribute( "numberOfContractCB", employeeContractCBVOs == null ? 0 : employeeContractCBVOs.size() );

         //  加载休假方案列表
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         final List< Object > employeeContractLeaveVOs = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( contractId );

         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
            {
               ( ( ActionForm ) employeeContractLeaveVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractLeaveVOs", employeeContractLeaveVOs );
         request.setAttribute( "numberOfContractLeave", employeeContractLeaveVOs == null ? 0 : employeeContractLeaveVOs.size() );

         //  加载加班方案列表
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTVOsByContractId( contractId );

         if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
         {
            for ( Object employeeContractOTVOObject : employeeContractOTVOs )
            {
               ( ( ActionForm ) employeeContractOTVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractOTVOs", employeeContractOTVOs );
         request.setAttribute( "numberOfContractOT", employeeContractOTVOs == null ? 0 : employeeContractOTVOs.size() );

         // 加载雇员其他设置列表
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
         final List< Object > employeeContractOtherVOs = employeeContractOtherService.getEmployeeContractOtherVOsByContractId( contractId );

         if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
         {
            for ( Object employeeContractOtherVOObject : employeeContractOtherVOs )
            {
               ( ( ActionForm ) employeeContractOtherVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractOtherVOs", employeeContractOtherVOs );
         request.setAttribute( "numberOfContractOther", employeeContractOtherVOs == null ? 0 : employeeContractOtherVOs.size() );

         // 加载雇员成本
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );
         final List< Object > employeeContractSettlementVOs = employeeContractSettlementService.getEmployeeContractSettlementVOsByContractId( contractId );

         if ( employeeContractSettlementVOs != null && employeeContractSettlementVOs.size() > 0 )
         {
            for ( Object employeeContractSettlementVOObject : employeeContractSettlementVOs )
            {
               ( ( ActionForm ) employeeContractSettlementVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractSettlementVOs", employeeContractSettlementVOs );
         request.setAttribute( "numberOfContractSettlement", employeeContractSettlementVOs == null ? 0 : employeeContractSettlementVOs.size() );

         // 工作流审批里的列表
         final String comeFrom = request.getParameter( "comeFrom" );

         if ( "workflow".equals( comeFrom ) )
         {
            final HistoryService historyService = ( HistoryService ) getService( "historyService" );
            final String historyId = request.getParameter( "historyId" );
            final String type = request.getParameter( "type" );
            final String tabIndex = request.getParameter( "tabIndex" );

            final HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

            if ( historyVO != null )
            {
               final EmployeeContractVO passObject = ( EmployeeContractVO ) JSONObject.toBean( JSONObject.fromObject( historyVO.getPassObject() ), EmployeeContractVO.class );
               final EmployeeContractVO originalObject = ( EmployeeContractVO ) JSONObject.toBean( JSONObject.fromObject( historyVO.getFailObject() ), EmployeeContractVO.class );
               passObject.reset( null, request );
               originalObject.reset( null, request );
               request.setAttribute( "passObject", passObject );
               request.setAttribute( "originalObject", originalObject );
            }

            request.setAttribute( "type", type );
            request.setAttribute( "tabIndex", tabIndex );
            return mapping.findForward( "workflowEmployeeContractSpecialInfo" );
         }

         //加载调薪信息
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         EmployeeSalaryAdjustmentVO salaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
         salaryAdjustmentVO.setLocale( getLocale( request ) );
         salaryAdjustmentVO.setAccountId( getAccountId( request, response ) );
         salaryAdjustmentVO.setClientId( getClientId( request, response ) );
         salaryAdjustmentVO.setCorpId( getCorpId( request, response ) );
         salaryAdjustmentVO.setContractId( contractId );
         salaryAdjustmentVO.setStatus( "5" );

         final PagedListHolder salaryAdjustmentHolder = new PagedListHolder();
         // 传入当前值对象
         salaryAdjustmentHolder.setObject( salaryAdjustmentVO );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeSalaryAdjustmentService.getSalaryAdjustmentVOsByCondition( salaryAdjustmentHolder, false );
         // 刷新Holder，国际化传值
         refreshHolder( salaryAdjustmentHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "salaryAdjustmentHolder", salaryAdjustmentHolder );

         return mapping.findForward( "manageEmployeeContractSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * list_object_options_ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-25
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 初始化Service接口                                                                                                                      
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取ContractId
         final String flag = request.getParameter( "flag" );

         // 获取ContractId
         String contractId = request.getParameter( "contractId" );

         // 获取ClientId
         String clientId = request.getParameter( "clientId" );

         // 获取EmployeeId
         final String employeeId = request.getParameter( "employeeId" );

         // 初始化EmployeeContractVO，用于查询
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setClientId( clientId );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setEmployeeId( employeeId );
         employeeContractVO.setFlag( flag );
         employeeContractVO.setOrderAttendanceGenerate( "3" );
         employeeContractVO.setStatus( "3, 5, 6, 7" );
         final List< Object > employeeContractVOs = employeeContractService.getEmployeeContractVOsByCondition( employeeContractVO );

         if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
         {
            // 遍历
            for ( Object employeeContractVOObject : employeeContractVOs )
            {
               final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempEmployeeContractVO.getContractId() );

               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameZH() );
               }
               // 如果是英文环境
               else
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameEN() );
               }

               mappingVOs.add( mappingVO );
            }

            // 如果只有一个选项则默认被选中
            if ( contractId != null && contractId.trim().isEmpty() && employeeContractVOs.size() == 1 )
            {
               contractId = ( ( EmployeeContractVO ) employeeContractVOs.get( 0 ) ).getContractId();
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "contractId", contractId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   /**  
    * Generate Contract
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-29
   public ActionForward generate_contract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化输入值错误数
         request.setAttribute( "errorCount", 0 );
         // 检查页面输入值
         checkEmployeeId( mapping, form, request, response );
         checkOrderId( mapping, form, request, response );

         // 页面跳转控制
         if ( request.getAttribute( "errorCount" ) != null && Integer.valueOf( request.getAttribute( "errorCount" ).toString() ) > 0 )
         {
            return to_prePage( mapping, form, request, response );
         }

         // 设置记号，防止重复提交
         this.saveToken( request );

         // 初始化 Service接口
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         //         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         //         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         //         final ClientService clientService = ( ClientService ) getService( "clientService" );
         //         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         //         final LocationService locationService = ( LocationService ) getService( "secLocationService" );

         // 获取劳动合同主键
         final String employeeContractId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获取Action Form
         EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // 初始化Temp EmployeeContractVO
         EmployeeContractVO tempEmployeeContractVO = null;
         LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );

         if ( KANUtil.filterEmpty( employeeContractId ) != null )
         {
            tempEmployeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractId );
         }
         // 新增情况
         if ( tempEmployeeContractVO == null )
         {
            // 验证日期是否重复
            if ( employeeContractService.checkContractConflict( employeeContractVO ) )
            {
               if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                     || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
               {
                  error( request, null, "时间段内已存在劳动合同！" );
               }
               else
               {
                  error( request, null, "时间段内已存在派遣信息！" );
               }

               employeeContractVO.setEmployeeId( employeeContractVO.getEncodedEmployeeId() );
               employeeContractVO.setOrderId( employeeContractVO.getEncodedOrderId() );

               return to_objectNew( mapping, form, request, response );
            }

            employeeContractVO.setCreateBy( getUserId( request, response ) );
            employeeContractVO.setModifyBy( getUserId( request, response ) );

            // 如果是Inhouse操作。需要手动设置ClientId
            if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               employeeContractVO.setClientId( getClientId( request, null ) );
            }
            employeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
            employeeContractService.insertEmployeeContract( employeeContractVO );

            // 重新获取
            employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
         }
         else
         {
            tempEmployeeContractVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            tempEmployeeContractVO.setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );
            // 修改对象
            tempEmployeeContractVO.update( employeeContractVO );
            // 初始化
            tempEmployeeContractVO.reset( mapping, request );
            tempEmployeeContractVO.setConstantVOs( null );
            tempEmployeeContractVO.setContent( laborContractTemplateVO == null ? "" : laborContractTemplateVO.getContent() );
            employeeContractService.updateEmployeeContract( tempEmployeeContractVO );
            employeeContractVO = tempEmployeeContractVO;
         }

         if ( KANUtil.filterEmpty( employeeContractVO.getTemplateId(), new String[] { "0" } ) != null )
         {
            // 获取合同模板信息
            final String content = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() ).getContent();

            // 初始化KANAccountConstants
            final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );

            // 初始化EmployeeContractPDFVO
            List< Object > objects = getEmployeeContractPDFVos( employeeContractVO, accountConstants, request );

            // 设置EmployeeContractVO的Content
            // Modified by Jason Ji at 2014-04-11
            final List< ConstantVO > constantVOs = accountConstants.getConstantVOsByScopeType( "3" );
            //            employeeContractService.updateEmployeeContract( employeeContractVO, constantVOs);
            employeeContractVO.setContent( MatchUtil.generateContent( content, constantVOs, objects, request ) );
         }

         // 刷新对象，初始化对象列表及国际化
         employeeContractVO.reset( mapping, request );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeContractForm", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "generateContract" );
   }

   /**  
    * Export Contract PDF
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward export_contract_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractPropertyService employeeContractPropertyService = ( EmployeeContractPropertyService ) getService( "employeeContractPropertyService" );

         // 获取商务合同主键
         final String employeeContractId = KANUtil.decodeString( request.getParameter( "id" ) );
         // 获取商务合同对象
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractId );

         // 初始化文件名
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = employeeContractVO.getNameZH() + fileName;
         }
         else
         {
            fileName = employeeContractVO.getNameEN() + fileName;
         }

         // 下载劳动合同或服务协议PDF版本
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< ConstantVO > constantVOList = accountConstants.getConstantVOsByScopeType( "3" );
         List< Object > contractIdList = employeeContractPropertyService.getEmployeeContractPropertyVOsByContractId( employeeContractVO.getContractId() );
         String pageContent = employeeContractVO.getContent();
         String logoFile = "";
         final EntityVO entityVO = accountConstants.getEntityVOByEntityId( employeeContractVO.getEntityId() );
         if ( entityVO != null )
         {
            logoFile = entityVO.getLogoFile();
            if ( StringUtils.contains( logoFile, "##" ) )
            {
               logoFile = logoFile.split( "##" )[ 0 ];
            }
            //            if ( KANUtil.filterEmpty( logoFile ) != null )
            //            {
            //               logoFile = accountConstants.initClientLogoFile( logoFile, "0", entityVO.getCorpId() );
            //            }

            if ( KANUtil.filterEmpty( logoFile ) != null )
            {
               //将图片服务器图片写到本地
               logoFile = PDFTool.smbGet( logoFile, request );
            }
         }
         if ( KANUtil.filterEmpty( logoFile ) == null )
         {
            logoFile = KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) ? accountConstants.getClientLogoFileByCorpId( BaseAction.getCorpId( request, response ) )
                  : accountConstants.OPTIONS_LOGO_FILE;
            logoFile = KANUtil.basePath + File.separatorChar + logoFile;
         }

         String htmlContent = MatchUtil.generateContent( pageContent, constantVOList, contractIdList, request, MatchUtil.FLAG_GET_CONTENT, logoFile );
         //         ByteArrayOutputStream baos = HTMLParseUtil.htmlParsePDF( htmlContent, employeeContractVO.getContractId(), logoFile );

         new DownloadFileAction().download( response, PDFTool.generationPdfDzOrder( htmlContent ), fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "行号" ) )
         {
            error( request, null, e.getMessage() );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax调用
      return mapping.findForward( "" );
   }

   /**
    * Modify Object Step 2
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward modify_object_step2( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化输入值错误数
            request.setAttribute( "errorCount", 0 );
            // 检查页面输入值
            checkEmployeeId( mapping, form, request, response );
            checkOrderId( mapping, form, request, response );

            // 页面跳转控制
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // 初始化 Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获取EmployeeContractVO对象
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            // 获取合同模板信息
            final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( employeeContractVO.getTemplateId() );
            final String content = laborContractTemplateVO == null ? null : laborContractTemplateVO.getContent();

            // 装载界面传值
            employeeContractVO.setContent( content );
            // 更新登录用户及修改时间
            employeeContractVO.setModifyBy( getUserId( request, response ) );
            employeeContractVO.setModifyDate( new Date() );

            // 初始化ConstantVO List
            //jzy 2014/04/11 modify
            String accountId = getAccountId( request, response );
            KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );
            final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( content, constantVO, request, null, MatchUtil.FLAG_GET_PROPERTIES );

            // 初始化Rows
            int rows = 0;

            // 调用修改方法
            rows = employeeContractService.updateEmployeeContract( employeeContractVO, constantVOs );

            // 如果是合同提交 - 默认状态为“审批”
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               employeeContractVO.reset( mapping, request );
               employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
               rows = employeeContractService.submitEmployeeContract( employeeContractVO );
            }

            // 返回提示消息
            if ( rows == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }

         // 清空Form
         ( ( EmployeeContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Chop Object
    * 第二个界面“盖章”按钮触发
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
   public ActionForward chop_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取EmployeeContractVO对象
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            employeeContractVO.setStatus( "5" );
            employeeContractVO.setModifyBy( getUserId( request, response ) );
            employeeContractVO.setModifyDate( new Date() );

            // 调用修改方法
            employeeContractService.updateEmployeeContract( employeeContractVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Archive Object
    * 第二个界面“归档”按钮触发
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
   public ActionForward archive_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取EmployeeContractVO对象
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

            employeeContractVO.setStatus( "6" );
            employeeContractVO.setModifyBy( getUserId( request, response ) );
            employeeContractVO.setModifyDate( new Date() );

            // 调用修改方法
            employeeContractService.updateEmployeeContract( employeeContractVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * 验证雇员在一个客户，相同法务实体下是否存在时间重复的派送协议
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Modify by Kevin Jin 2014-06-02
   public ActionForward checkContractConflict( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWriter
         final PrintWriter out = response.getWriter();

         // 初始化 Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 初始化 EmployeeContractVO
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         if ( !KANConstants.ROLE_HR_SERVICE.equals( getRole( request, null ) ) )
         {
            employeeContractVO.setCorpId( getCorpId( request, null ) );
         }

         // 如果没有EntityId 则不检查时间冲突性
         if ( employeeContractService.checkContractConflict( employeeContractVO ) )
         {
            out.print( "1" );
         }
         else
         {
            out.print( "0" );
         }

         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**  
    * List Object Ajax
    * 服务协议搜索弹出模态框
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得Action Form 
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), getAccessAction( request, response ), employeeContractVO );
         setDataAuth( request, response, employeeContractVO );

         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         employeeContractVO.setFlag( "2" );

         // 如果是In House则注入Client ID 值
         if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            employeeContractVO.setCorpId( getCorpId( request, response ) );
         }

         // 解码
         decodedObject( employeeContractVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder contractHolder = new PagedListHolder();
         // 传入当前页
         contractHolder.setPage( page );
         // 传入当前值对象
         contractHolder.setObject( employeeContractVO );
         // 设置页面记录条数
         contractHolder.setPageSize( listPageSize_popup );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractService.getEmployeeContractVOsByCondition( contractHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( contractHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "contractHolder", contractHolder );

         // 如果是Ajax请求
         if ( new Boolean( getAjax( request ) ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
         }

         // Ajax Table调用，直接传回JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Get Object Json
    * 模态框用查询派送信息
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化 Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 初始化EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         // 注释by siuvan && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null
         if ( employeeContractVO != null && employeeContractVO.getAccountId() != null && employeeContractVO.getAccountId().equals( getAccountId( request, response ) )
               && KANUtil.filterEmpty( employeeContractVO.getStartDate() ) != null )
         {
            employeeContractVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( employeeContractVO );
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

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**
    * 列表点击提交
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         // 如果重复提交
         if ( employeeContractVO == null )
         {
            return list_object( mapping, form, request, response );
         }
         if ( employeeContractService.checkContractConflict( employeeContractVO ) )
         {
            if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                  || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
            {
               error( request, null, "时间段内已存在劳动合同" );
            }
            else
            {
               error( request, null, "时间段内已存在派送信息" );
            }
            EmployeeContractVO employeeContract = new EmployeeContractVO();
            employeeContract.setAccountId( employeeContractVO.getAccountId() );
            employeeContract.setCorpId( employeeContractVO.getCorpId() );
            return list_object( mapping, employeeContract, request, response );
         }
         employeeContractVO.reset( mapping, request );
         if ( employeeContractVO.getWorkflowId() == null )
         {
            String accountId = getAccountId( request, response );
            KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );
            List< Object > objects = getEmployeeContractPDFVos( employeeContractVO, accountConstants, request );
            final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( employeeContractVO.getContent(), constantVO, request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );

            employeeContractVO.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
            employeeContractVO.setConstantVOs( constantVOs );
            employeeContractService.submitEmployeeContract( employeeContractVO );
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, employeeContractVO, Operate.BATCH_SUBMIT, contractId, null );
         }
         else
         {
            warning( request, MESSAGE_TYPE_SUBMIT, "已经存在工作流请不要重复提交！" );
         }
         final String orderId = employeeContractVO.getOrderId();
         employeeContractVO.reset();
         employeeContractVO.setContractId( "" );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setFlag( "2" );
         employeeContractVO.setEmployeeNameZH( "" );
         employeeContractVO.setEmployeeNameEN( "" );
         employeeContractVO.setClientNameZH( "" );
         employeeContractVO.setClientNameEN( "" );
         employeeContractVO.setClientId( "" );
         employeeContractVO.setCertificateNumber( "" );
         employeeContractVO.setOrderAttendanceGenerate( "" );
         // 来自订单的提交
         if ( KANUtil.filterEmpty( request.getParameter( "comeFrom" ) ) != null && "order".equals( request.getParameter( "comeFrom" ) ) )
         {
            employeeContractVO.setOrderId( orderId );
            return list_object_order( mapping, employeeContractVO, request, response );
         }
         else
         {
            request.setAttribute( "employeeContractForm", employeeContractVO );
            return list_object( mapping, employeeContractVO, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 列表点击批量提交
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Add by siuvan.xia @ 2014-07-10
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeeContractVO EmployeeContractVO = ( EmployeeContractVO ) form;
         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得勾选ID
         final String contractIds = EmployeeContractVO.getSelectedIds();

         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< ConstantVO > constantVO = accountConstants.getConstantVOsByScopeType( "3" );

         // 存在勾选ID
         if ( KANUtil.filterEmpty( contractIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = contractIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            StringBuffer message = new StringBuffer();
            message.append( "以下合同，未成功提交：" );
            boolean haveMessage = false;
            for ( String selectId : selectedIdArray )
            {
               // 获得EmployeeContractVO
               final EmployeeContractVO submitObject = employeeContractService.getEmployeeContractVOByContractId( KANUtil.decodeStringFromAjax( selectId ) );
               
               if( "1".equals( submitObject.getStatus() ) || "4".equals( submitObject.getStatus() ) )
               {
                   if ( employeeContractService.checkContractConflict( submitObject ) )
                   {
                      if ( ( KANUtil.filterEmpty( submitObject.getFlag() ) != null && KANUtil.filterEmpty( submitObject.getFlag() ).equals( "1" ) )
                            || ( KANUtil.filterEmpty( getRole( request, null ) ) != null && KANUtil.filterEmpty( getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
                      {
                         message.append( submitObject.getContractId() + ",时间段内已存在劳动合同" );
                         haveMessage = true;
                      }
                      break;
                   }
    
                   submitObject.setModifyBy( getUserId( request, response ) );
                   submitObject.setModifyDate( new Date() );
                   submitObject.reset( mapping, request );
                   submitObject.getHistoryVO().setAccessAction( getAccessAction( request, response ) );
    
                   if ( submitObject != null && ( StringUtils.isBlank( submitObject.getTemplateId() ) || "0".equals( submitObject.getTemplateId() ) ) )
                   {
                      message.append( submitObject.getContractId() );
                      message.append( "," );
                      haveMessage = true;
                   }
                   else
                   {
                      if ( submitObject.getWorkflowId() == null )
                      {
                         List< Object > objects = getEmployeeContractPDFVos( submitObject, accountConstants, request );
                         final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( submitObject.getContent(), constantVO, request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );
                         submitObject.setConstantVOs( constantVOs );
                         rows = rows + employeeContractService.submitEmployeeContract( submitObject );
                      }
                   }
                }
            }

            if ( haveMessage )
            {
               warning( request, MESSAGE_TYPE_SUBMIT, message.toString() );
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               final PrintWriter out = response.getWriter();
               //不关闭流，后续还要画tableList tomcat 会帮忙关闭的
               out.println( "<div id='_USER_DEFINE_MSG' class='message warning fadable'  style='display:none;'>" + message.toString() + "</div>" );
            }
            else
            {
               if ( rows < 0 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, form, Operate.BATCH_SUBMIT, null, KANUtil.decodeSelectedIds( contractIds ) );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * To PrePage
    * 页面输入错误返回提交页面
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_prePage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加需设定一个记号，防止重复提交
         this.saveToken( request );

         // 初始化Service接口                                                                                                                      
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( ( ( EmployeeContractVO ) form ).getContractId() );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "author_new", AuthUtils.hasAuthority( getAccessAction( request, response ), AuthConstants.RIGHT_NEW, employeeContractVO.getOwner(), request, null ) );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( ( ( EmployeeContractVO ) form ).getFlag() != null && "2".equals( ( ( EmployeeContractVO ) form ).getFlag() )
            && !KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // 跳转到服务协议新建界面
         return mapping.findForward( "manageEmployeeContractSEV" );
      }

      // 跳转到Manage界面   
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // 跳转到 劳动合同 IN HOUSE
         return mapping.findForward( "manageEmployeeContractInHouse" );
      }
      else
      {
         // 跳转到劳动合同新建界面
         return mapping.findForward( "manageEmployeeContract" );
      }

   }

   /**  
    * CheckOrderId
    * 检查订单ID是否有效
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkOrderId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
      // 获取Form
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
      // 获得OrderId
      final String clientOrderHeaderId = KANUtil.filterEmpty( employeeContractVO.getOrderId() );

      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId );

      if ( clientOrderHeaderVO == null )
      {
         request.setAttribute( "orderIdError", "订单ID输入无效！" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   /**  
    * CheckEmployeeId
    * 检查雇员ID是否有效
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void checkEmployeeId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      // 获取Form
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;
      // 获得EmployeeId
      final String employeeId = KANUtil.filterEmpty( employeeContractVO.getEmployeeId() );

      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

      if ( employeeVO == null )
      {

         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            request.setAttribute( "employeeIdError", "员工ID输入无效！" );
         }
         else
         {
            request.setAttribute( "employeeIdError", "雇员ID输入无效！" );
         }

         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   public ActionForward list_object_options_ajax_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 初始化Service接口                                                                                                                      
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获取ContractId
         final String flag = request.getParameter( "flag" );

         // 获取EmployeeId
         final String employeeId = getEmployeeId( request, response );

         // 初始化EmployeeContractVO，用于查询
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setEmployeeId( employeeId );
         employeeContractVO.setFlag( flag );
         employeeContractVO.setStatus( "3, 5, 6" );
         // 按照时间倒序
         employeeContractVO.setSortColumn( "startDate" );
         employeeContractVO.setSortOrder( "desc" );
         final List< Object > employeeContractVOs = employeeContractService.getEmployeeContractVOsByCondition( employeeContractVO );

         if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
         {
            // 遍历
            for ( Object employeeContractVOObject : employeeContractVOs )
            {
               final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempEmployeeContractVO.getContractId() );

               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameZH() );
               }
               // 如果是英文环境
               else
               {
                  mappingVO.setMappingValue( tempEmployeeContractVO.getNameEN() );
               }

               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "contractId", "0" ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   public void setInputValueForPage( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );
      String employeeContractId = request.getParameter( "contractId" );
      final EmployeeContractPropertyService employeeContractPropertyService = ( EmployeeContractPropertyService ) getService( "employeeContractPropertyService" );
      List< Object > list = employeeContractPropertyService.getEmployeeContractPropertyVOsByContractId( KANUtil.decodeString( employeeContractId ) );
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( Object object : list )
      {
         Map< String, String > map = new HashMap< String, String >();
         map.put( "id", ( ( EmployeeContractPropertyVO ) object ).getPropertyName() );
         map.put( "value", ( ( EmployeeContractPropertyVO ) object ).getPropertyValue() );
         listReturn.add( map );
      }
      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }

   private List< Object > getEmployeeContractPDFVos( final EmployeeContractVO employeeContractVO, final KANAccountConstants accountConstants, final HttpServletRequest request )
         throws KANException
   {
      final List< Object > objects = new ArrayList< Object >();
      if ( employeeContractVO != null )
      {
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         // 初始化EntityVO
         final EntityVO entityVO = accountConstants.getEntityVOByEntityId( employeeContractVO.getEntityId() );
         // 初始化EmployeeVO
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         // 初始化EmployeeContractSalaryVO列表
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( employeeContractVO.getContractId() );
         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
         // 初始化ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( employeeContractVO.getClientId() );
         // 初始化LocationVO
         final LocationVO locationVO = locationService.getLocationVOByLocationId( entityVO.getLocationId() );
         boolean hasProbationUsing = true;
         double baseSalary = 0;
         double probationSalary = 0;
         String probationMonth = "";
         String workAddress = "";
         int index_1 = 0;
         int index_2 = 0;
         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            // 初始化EmployeeContractSalaryVO
            final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
            // 基本工资科目只取一次累加
            if ( employeeContractSalaryVO.getItemId().equals( "1" ) )
            {
               if ( "1".equals( employeeContractSalaryVO.getProbationUsing() ) )
               {
                  if ( index_1 == 0 )
                  {
                     probationSalary = probationSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
                     index_1++;
                  }
                  hasProbationUsing = false;
               }
               else
               {
                  if ( index_2 == 0 )
                  {
                     baseSalary = baseSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
                     index_2++;
                  }
               }
            }
            // 工资调整科目累加
            if ( employeeContractSalaryVO.getItemId().equals( "2" ) )
            {
               if ( "1".equals( employeeContractSalaryVO.getProbationUsing() ) )
               {
                  probationSalary = probationSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
                  hasProbationUsing = false;
               }
               else
               {
                  baseSalary = baseSalary + Double.parseDouble( employeeContractSalaryVO.getBase() );
               }
            }
         }
         if ( hasProbationUsing )
         {
            probationSalary = baseSalary;
         }
         if ( null != employeeContractVO.getProbationEndDate() )
         {
            //            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            //            {
            probationMonth = employeeContractVO.getProbationEndDate();
            //            }
            //            else
            //            {
            //               probationMonth = employeeContractVO.getProbationMonth() + " month(s)";
            //            }
         }
         else
         {
            if ( "0".equals( clientOrderHeaderVO.getProbationMonth() ) )
            {
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  probationMonth = "无试用期";
               }
               else
               {
                  probationMonth = "None";
               }
            }
            else
            {
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  probationMonth = clientOrderHeaderVO.getProbationMonth() + "个月";
               }
               else
               {
                  probationMonth = clientOrderHeaderVO.getProbationMonth() + " month(s)";
               }
            }
         }
         if ( locationVO != null && getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {
               workAddress = locationVO.getAddressZH();
            }
            else
            {
               workAddress = locationVO.getAddressEN();
            }
         }
         else
         {
            workAddress = clientVO.getAddress();
         }
         final EmployeeContractPDFVO employeeContractPDFVO = new EmployeeContractPDFVO();
         employeeContractPDFVO.setBaseSalary( employeeContractPDFVO.formatNumber( KANUtil.filterEmpty( baseSalary ) ) );
         employeeContractPDFVO.setProbationSalary( employeeContractPDFVO.formatNumber( KANUtil.filterEmpty( probationSalary ) ) );
         employeeContractPDFVO.setProbationMonths( KANUtil.filterEmpty( probationMonth ) );
         employeeContractPDFVO.setWorkAddress( workAddress );
         employeeVO.reset( null, request );
         employeeVO.set_tempPositionIds( employeeVO.getDecode_tempPositionIds() );
         objects.add( employeeContractPDFVO );
         objects.add( employeeVO );
         objects.add( entityVO );
         objects.add( employeeContractVO );
      }
      return objects;
   }

   public ActionForward list_employStatuses_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         mappingVOs.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.work.statuses" ) );
         /*  // 获取accountId
           final String accountId = request.getParameter( "accountId" );

           mappingVOs.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.contract.employStatuses" ) );
           // 从配置文件添加对应账户的雇佣状态集合
           if ( accountId != null )
           {
              // 如果是 iClick
              if ( ACCOUNT_ID_ICLICK.equals( accountId ) )
              {
                 mappingVOs.clear();
                 mappingVOs.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.contract.iClick.employStatuses" ) );
              }
           }*/

         // 生成下拉框
         // Send to client
         final String employStatus = request.getParameter( "employStatus" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "employStatus", KANUtil.filterEmpty( employStatus ) == null ? "0" : employStatus ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   // Added by siuvan.xia 2015-03-17
   public ActionForward calculate_annual_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化contractId
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // 初始化 Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         int rows = employeeContractService.calculateEmployeeAnnualLeave( employeeContractVO );
         if ( rows > 0 )
         {
            success( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.success" ).replaceAll( "X", String.valueOf( rows ) ) );
         }
         else
         {
            warning( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.failure" ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   // Added by siuvan.xia 2015年07月02日09:48:15
   public ActionForward export_excel( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String excelFileName = "";
         // 初始化excel名
         String baseFile = FileSystemView.getFileSystemView().getHomeDirectory().toString();
         if ( !baseFile.contains( "Desktop" ) )
         {
            excelFileName = FileSystemView.getFileSystemView().getHomeDirectory().toString() + "/Desktop/emp.xlsx";
         }
         else
         {
            excelFileName = FileSystemView.getFileSystemView().getHomeDirectory().toString() + "/emp.xlsx";
         }

         // 创建File
         final File file = new File( excelFileName );

         if ( file.exists() )
         {
            String arr[] = null;
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // 初始化工作薄
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );
            // 获取工作表
            final XSSFSheet sheet = workbook.getSheetAt( 0 );
            // 遍历行
            for ( int i = 0; i < sheet.getPhysicalNumberOfRows(); i++ )
            {
               // 读取左上端单元格
               final XSSFRow row = sheet.getRow( i );

               if ( row != null )
               {
                  if ( i == 0 )
                  {
                     // 遍历列
                     for ( int j = 0; j < row.getPhysicalNumberOfCells(); j++ )
                     {
                        // 获取到列的值
                        final XSSFCell cell = row.getCell( j );

                        if ( arr == null )
                        {
                           arr = new String[ row.getPhysicalNumberOfCells() ];
                        }

                        if ( i == 0 )
                        {
                           arr[ j ] = cell.getStringCellValue();
                        }
                     }
                  }
                  else
                  {
                     final String contractId = row.getCell( 0 ).getStringCellValue();
                     final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

                     if ( employeeContractVO == null )
                     {
                        System.out.println( "contractId无效" );
                        continue;
                     }

                     for ( int k = 1; k < arr.length; k++ )
                     {
                        String cellValue = "";
                        if ( row.getCell( k ).getCellType() == Cell.CELL_TYPE_NUMERIC )
                        {
                           cellValue = String.valueOf( row.getCell( k ).getNumericCellValue() );
                        }
                        else if ( row.getCell( k ).getCellType() == Cell.CELL_TYPE_STRING )
                        {
                           cellValue = row.getCell( k ).getStringCellValue();
                        }
                        String p = arr[ k ];
                        if ( p.contains( "_" ) )
                        {
                           String remark1 = employeeContractVO.getRemark1();
                           JSONObject jsonObject = JSONObject.fromObject( remark1 );
                           jsonObject.put( p.split( "_" )[ 1 ], cellValue );

                           employeeContractVO.setRemark1( jsonObject.toString() );
                        }
                        else
                        {
                           KANUtil.setValue( employeeContractVO, arr[ k ], cellValue );
                        }
                     }

                     employeeContractService.updateBaseEmployeeContract( employeeContractVO );
                  }
               }
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   private void insertAddEmployeeContractVOLog( final LogService logService, final EmployeeContractVO employeeContractVO, final String ip ) throws KANException
   {
      final LogVO logVO = new LogVO();
      logVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      logVO.setChangeReason( employeeContractVO.getRemark3() );
      logVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
      logVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
      logVO.setType( String.valueOf( Operate.ADD.getIndex() ) );
      logVO.setModule( EmployeeContractVO.class.getCanonicalName() );
      logVO.setContent( "合同雇佣时间：" + employeeContractVO.getStartDate() );
      logVO.setIp( ip );
      logVO.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      logVO.setOperateBy( employeeContractVO.decodeUserId( employeeContractVO.getModifyBy() ) );
      logVO.setpKey( employeeContractVO.getContractId() );
      logVO.setRemark( "劳动合同新增" );

      logService.insertLog( logVO );
   }

}
