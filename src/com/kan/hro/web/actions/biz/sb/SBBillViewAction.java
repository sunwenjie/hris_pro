package com.kan.hro.web.actions.biz.sb;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBBillDetailView;
import com.kan.hro.domain.biz.sb.SBBillHeaderView;
import com.kan.hro.service.inf.biz.sb.SBBillViewService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

public class SBBillViewAction extends BaseAction
{

   // 对应java对象
   public final static String JAVA_OBJECT_NAME = "com.kan.hro.domain.biz.sb.SBDTO";

   public final static String accessAction = "JAVA_OBJECT_SBBILL";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = getAjax( request );
         // 初始化Service接口
         final SBBillViewService sbBillViewService = ( SBBillViewService ) getService( "sbBillViewService" );
         // 获得Action Form
         final SBBillDetailView sbBillDetailView = ( SBBillDetailView ) form;

         // 处理Form
         dealSubAction( sbBillDetailView, mapping, form, request, response );

         //处理数据权限
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), SBBillViewAction.accessAction, sbBillDetailView );
            setDataAuth( request, response, sbBillDetailView );
         }

         // 获得SubAction
         final String subAction = getSubAction( sbBillDetailView );
         // 获取ListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         final String vendorId = request.getParameter( "vendorId" );
         if ( KANUtil.filterEmpty( vendorId, "0" ) != null )
         {
            sbBillDetailView.setVendorId( vendorId );
         }

         if ( KANUtil.filterEmpty( sbBillDetailView.getMonthly() ) == null )
         {
            sbBillDetailView.setMonthly( KANUtil.getMonthly( new Date() ) );
         }

         // 替换全角逗号
         if ( KANUtil.filterEmpty( sbBillDetailView.getBatchId() ) != null )
         {
            sbBillDetailView.setBatchId( sbBillDetailView.getBatchId().replace( "，", "," ) );
         }

         // 如果是inHouse
         if ( getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // 如果不具HR职能
            if ( !isHRFunction( request, response ) )
            {
               // 创建人、修改人
               sbBillDetailView.setEmployeeId( getEmployeeId( request, response ) );
            }
         }

         if ( sbBillDetailView.getSbStatusArray() != null && sbBillDetailView.getSbStatusArray().length > 0 )
         {
            sbBillDetailView.setSbStatus( KANUtil.toJasonArray( sbBillDetailView.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
            request.setAttribute( "sbStatus", sbBillDetailView.getSbStatus() );
         }

         final SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setPageFlag( "bill" );
         // 添加供应商下拉框
         sbBatchVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         //处理数据权限;
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_EMPLOYEE ) )
         {
            sbBillDetailView.setEmployeeId( EmployeeSecurityAction.getEmployeeId( request, response ) );
            sbBillDetailView.setRole( KANConstants.ROLE_EMPLOYEE );
            //            //已发放
            //            sbBillDetailView.setStatus( "3" );
            //            sbBillDetailView.setFlag( "1" );
         }
         else if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            sbBillDetailView.setClientId( BaseAction.getClientId( request, response ) );
            sbBillDetailView.setRole( KANConstants.ROLE_CLIENT );
            //            sbBillDetailView.setGroupColumn( "2" );
         }

         final PagedListHolder pagedListHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 设置页面记录条数
            pagedListHolder.setPageSize( listDTO.getPageSize() );
            // 传入当前值对象
            pagedListHolder.setObject( sbBillDetailView );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            sbBillViewService.getSBBillDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().specialExportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // 初始化PrintWrite对象
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_NAME ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSBBillView" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use

   }

   // 加载特殊的HTML
   public void load_special_html( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         final String monthly = request.getParameter( "monthly" );

         final String contractStatus = request.getParameter( "contractStatus" );
         final String employeeSBId = request.getParameter( "employeeSBId" );
         final String sbStatuses = request.getParameter( "sbStatuses" );
         final String status = request.getParameter( "status" );
         final String groupColumn = request.getParameter( "groupColumn" );
         final String sbType = request.getParameter( "sbType" );
         final String flag = request.getParameter( "flag" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取社保方案
         final List< MappingVO > sbs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, null ) );
         if ( sbs != null )
         {
            sbs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // 获取社保状态
         final SBBillHeaderView sbBillHeaderView = new SBBillHeaderView();
         sbBillHeaderView.reset( null, request );

         // 获取月份
         final List< MappingVO > monthlys = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );
         if ( monthlys != null && monthlys.size() > 0 )
         {
            monthlys.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // 初始化StringBuffer
         final StringBuffer rs = new StringBuffer();

         final String contractLabelKey = getRole( request, null ).equals( "1" ) ? "public.contract1.status" : "public.contract2.status";

         if ( !KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, response ) ) )
         {
            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), contractLabelKey ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getContractStatuses(), "contractStatus", "contractStatus", contractStatus, null, null ) );
            rs.append( "</li>" );
         }

         rs.append( "<li>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.monthly" ) + "<img style=\"hight 12px;width :12px;\" src=\"images/tips.png\" title=\""
               + KANUtil.getProperty( request.getLocale(), "sb.bill.month.tips" ) + "\" /></label>" );
         rs.append( KANUtil.getSelectHTML( monthlys, "monthly", "monthly", monthly, null, null ) );
         rs.append( "</li>" );

         if ( !KANConstants.ROLE_EMPLOYEE.equals( BaseAction.getRole( request, response ) ) )
         {

            if ( !BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
            {
               rs.append( "<li>" );
               rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.payment.status" ) + "</label>" );
               rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getFlags(), "flag", "flag", flag, null, null ) );
               rs.append( "</li>" );
            }

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.type" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getSbTypes(), "sbType", "sbType", sbType, null, null ) );
            rs.append( "</li>" );
            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.solution" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbs, "employeeSBId", "employeeSBId", employeeSBId, null, null ) );
            rs.append( "</li>" );

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "public.status" ) + "</label>" );
            rs.append( KANUtil.getSelectHTML( sbBillHeaderView.getStatuses(), "status", "status", status, null, null ) );
            rs.append( "</li>" );

            rs.append( "<li>" );
            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "sb.status" ) + "</label>" );
            rs.append( "<div style=\"width: 220px;\">" );
            if ( sbBillHeaderView.getSbStatuses() != null && sbBillHeaderView.getSbStatuses().size() > 0 )
            {
               for ( MappingVO mappingVO : sbBillHeaderView.getSbStatuses() )
               {
                  if ( !mappingVO.getMappingId().equals( "0" ) )
                  {
                     String checked = "";
                     if ( KANUtil.filterEmpty( sbStatuses ) != null )
                     {
                        for ( String sbStatus : sbStatuses.split( "," ) )
                        {
                           if ( sbStatus.equals( mappingVO.getMappingId() ) )
                           {
                              checked = "checked=\"checked\"";
                           }
                        }
                     }
                     rs.append( "<label class=\"auto\">" );
                     rs.append( "<input type=\"checkbox\" name=\"sbStatusArray\" " + checked + " id=\"sbStatus_" + mappingVO.getMappingId() + "\"  class=\"sbStatus_"
                           + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" />" );
                     rs.append( mappingVO.getMappingValue() );
                     rs.append( "</label>" );
                  }
               }
            }

            rs.append( "</div>" );
            rs.append( "</li>" );

            rs.append( "<li " + ( !getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "style=\"display:none;\"" : "" ) + ">" );
            rs.append( "<label>分组查询</label>" );
            rs.append( "<select name=\"groupColumn\" id=\"groupColumn\" class=\"groupColumn\">" );
            rs.append( "<option value=\"0\">请选择</option>" );
            rs.append( "<option value=\"1\" "
                  + ( ( KANUtil.filterEmpty( groupColumn ) != null && KANUtil.filterEmpty( groupColumn ).equals( "1" ) ) ? "selected=\"selected\"" : "" ) + ">客户</option>" );
            rs.append( "<option value=\"2\" "
                  + ( ( KANUtil.filterEmpty( groupColumn ) != null && KANUtil.filterEmpty( groupColumn ).equals( "2" ) ) ? "selected=\"selected\"" : "" ) + ">社保方案</option>" );
            rs.append( "</select>" );
            rs.append( "</li>" );

         }

         // Send to client
         out.println( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }
}
