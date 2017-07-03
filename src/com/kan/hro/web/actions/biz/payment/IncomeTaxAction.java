package com.kan.hro.web.actions.biz.payment;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import com.kan.hro.domain.biz.payment.PayslipHeaderView;
import com.kan.hro.domain.biz.payment.PayslipTaxDTO;
import com.kan.hro.service.inf.biz.payment.PayslipHeaderViewService;

public class IncomeTaxAction extends BaseAction
{

   // 当前Action对应的JavaObjectName
   public static final String JAVA_OBJECT_NAME = "com.kan.hro.domain.biz.payment.PayslipTaxDTO";

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
         final PayslipHeaderViewService payslipHeaderViewService = ( PayslipHeaderViewService ) getService( "payslipHeaderViewService" );
         // 获得Action Form
         final PayslipHeaderView payslipHeaderView = ( PayslipHeaderView ) form;
         // 获得SubAction
         final String subAction = getSubAction( payslipHeaderView );

         String accessAction = "JAVA_OBJECT_INCOME_TAX";
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, payslipHeaderView );
         setDataAuth( request, response, payslipHeaderView );
         
         if ( KANUtil.filterEmpty( payslipHeaderView.getStatus() ) == null )
         {
            payslipHeaderView.setStatus( "0" );
         }

         // 获取ListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME, KANUtil.filterEmpty( getCorpId( request, response ) ) );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 设置页面记录条数
            pagedListHolder.setPageSize( listDTO.getPageSize() );
            
            if ( new Boolean( ajax ) )
            {
               decodedObject( payslipHeaderView );
            }
            
            // 传入当前值对象
            pagedListHolder.setObject( payslipHeaderView );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            payslipHeaderViewService.getPayslipTaxDTOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : listDTO.isPaged() );

            // 确认是否排序
            if ( KANUtil.filterEmpty( payslipHeaderView.getSortOrder() ) != null && KANUtil.filterEmpty( payslipHeaderView.getSortColumn() ) != null )
            {
               // 初始化PayslipTaxDTO
               final PayslipTaxDTO payslipTaxDTO = new PayslipTaxDTO();
               // 设定排序字段
               payslipTaxDTO.setSortOrder( payslipHeaderView.getSortOrder() );
               payslipTaxDTO.setSortColumn( payslipHeaderView.getSortColumn() );
               // 传入当前值对象
               pagedListHolder.setObject( payslipHeaderView );
               // 排序
               Collections.sort( pagedListHolder.getSource(), payslipTaxDTO );
            }
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
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listIncomeTax" );
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
         final String status = request.getParameter( "status" );
         final String itemGroupId = request.getParameter( "itemGroupId" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取状态
         final PayslipHeaderView payslipDetailView = new PayslipHeaderView();
         payslipDetailView.reset( null, request );
         // 去除状态为“新建”的查询
         payslipDetailView.getStatuses().remove( 1 );

         // 获取月份
         final List< MappingVO > monthlys = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );
         monthlys.add( 0, payslipDetailView.getEmptyMappingVO() );
         // 初始化StringBuffer
         final StringBuffer rs = new StringBuffer();

         List< MappingVO > itemGroup = new ArrayList< MappingVO >();
         // 获取科目分组类型
         List< MappingVO > itemGroupTypes = KANUtil.getMappings( request.getLocale(), "item.group.type" );
         for ( MappingVO mappingVO : itemGroupTypes )
         {
            if ( mappingVO.getMappingId().equals( "0" ) || mappingVO.getMappingId().equals( "2" ) )
            {
               itemGroup.add( mappingVO );
            }
         }
         rs.append( "<li>" );
         rs.append( "<label>科目分组类型</label>" );
         rs.append( KANUtil.getSelectHTML( itemGroup, "itemGroupId", "itemGroupId", itemGroupId, null, null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>账单月份</label>" );
         rs.append( KANUtil.getSelectHTML( monthlys, "monthly", "monthly", monthly, null, null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>状态</label>" );
         rs.append( KANUtil.getSelectHTML( payslipDetailView.getStatuses(), "status", "status", status, null, null ) );
         rs.append( "</li>" );

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

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

}
