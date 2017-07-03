package com.kan.hro.web.actions.biz.cb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.service.inf.biz.cb.CBBatchService;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;

public class CBHeaderAction extends BaseAction
{

   // 当前Action对应的JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.cb.CBDTO";

   // 当前Action对应的Access Action
   public static final String ACCESSACTION_CBBILL = "HRO_CB_BILL";

   // 根据StatusFlag获取状态值Status
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // 初始化，默认为“预览”
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * 获取批次信息和服务协议信息
          */
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         // 获得当前批次主键ID
         String cbBatchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         // 初始化CBBatchVO
         CBBatchVO cbBatchVO = new CBBatchVO();
         cbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         cbBatchVO.setBatchId( cbBatchId );
         cbBatchVO.setCorpId( getCorpId( request, response ) );
         cbBatchVO.setAccountId( getAccountId( request, response ) );

         final List< Object > cbBatchVOs = cbBatchService.getCBBatchVOsByCondition( cbBatchVO );

         if ( cbBatchVOs != null && cbBatchVOs.size() > 0 )
         {
            cbBatchVO = ( CBBatchVO ) cbBatchVOs.get( 0 );
         }

         // 初始化CBHeaderVO
         CBHeaderVO cbHeaderVO = ( CBHeaderVO ) form;

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( cbHeaderVO );
         }

         cbHeaderVO.setBatchId( cbBatchId );
         cbHeaderVO.setCorpId( getCorpId( request, response ) );
         cbHeaderVO.setAccountId( getAccountId( request, response ) );
         cbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );

         // 设置页面的PageFlag和StatusFlag
         cbBatchVO.setPageFlag( CBBatchService.PAGE_FLAG_HEADER );
         cbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         cbBatchVO.reset( null, request );

         request.setAttribute( "cbBatchForm", cbBatchVO );

         /**
          * 获取商保方案列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder cbHeaderHolder = new PagedListHolder();
         // 传入当前页
         cbHeaderHolder.setPage( page );

         // 设置排序
         cbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
            passClientOrders( request, response );
         }

         // 如果没有指定排序则默认按 商保方案流水号排序
         if ( cbHeaderVO.getSortColumn() == null || cbHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            cbHeaderVO.setSortColumn( "employeeCBId,monthly" );
            cbHeaderVO.setSortOrder( "" );
         }

         if ( KANUtil.filterEmpty( cbHeaderVO.getStatus() ) == null )
         {
            if ( request.getParameter( "statusFlag" ) != null )
            {
               cbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
            }
         }

         // 国际化
         cbHeaderVO.reset( null, request );

         if ( cbHeaderVO.getStatus().equals( "1" ) )
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( cbHeaderVO.getStatuses().get( 1 ) );
            cbHeaderVO.setStatuses( statuses );
         }
         else if ( cbHeaderVO.getStatus().equals( "2" ) )
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( cbHeaderVO.getStatuses().get( 2 ) );
            cbHeaderVO.setStatuses( statuses );
         }
         else
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( cbHeaderVO.getStatuses().get( 3 ) );
            statuses.add( cbHeaderVO.getStatuses().get( 4 ) );
            statuses.add( cbHeaderVO.getStatuses().get( 5 ) );
            cbHeaderVO.setStatuses( statuses );
         }

         if ( KANUtil.filterEmpty( cbHeaderVO.getCbStatusArray() ) != null && cbHeaderVO.getCbStatusArray().length > 0 )
         {
            cbHeaderVO.setCbStatus( KANUtil.toJasonArray( cbHeaderVO.getCbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         cbHeaderVO.getCbStatuses().remove( 0 ); // Add by siuxia
         // 传入当前值对象
         cbHeaderHolder.setObject( cbHeaderVO );
         // 设置页面记录条数
         cbHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         cbHeaderService.getCBHeaderVOsByCondition( cbHeaderHolder, true );
         refreshHolder( cbHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "cbHeaderHolder", cbHeaderHolder );

         // 是否显示导出按钮
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_APPROVE ) )
         {
            accessAction = "HRO_CB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_CB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_CB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTable" );
         }
         final String boolSearchHeader = request.getParameter( "searchHeader" );
         // 如果不存在商保明细数据
         if ( cbHeaderHolder == null || cbHeaderHolder.getHolderSize() == 0 )
         {
            if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
            {
               CBBatchVO cbBatch = new CBBatchVO();
               cbBatch.reset( null, request );
               cbBatch.setBatchId( "" );
               request.setAttribute( "cbBatchForm", cbBatch );
               request.setAttribute( "messageInfo", true );
               return new CBAction().list_estimation( mapping, cbBatch, request, response );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listHeader" );
   }

   public ActionForward list_cbBill( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * 获取批次信息和服务协议信息
          */
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );

         // 初始化CBHeaderVO
         CBHeaderVO cbHeaderVO = ( CBHeaderVO ) form;
         cbHeaderVO.setAccountId( getAccountId( request, response ) );

         /**
          * 获取商保方案列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder cbBillHolder = new PagedListHolder();
         // 传入当前页
         cbBillHolder.setPage( page );

         // 设置排序
         cbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
            passClientOrders( request, response );
         }
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            cbHeaderVO.setClientId( BaseAction.getClientId( request, response ) );
         }

         // 如果没有指定排序则默认按 商保方案流水号排序
         if ( cbHeaderVO.getSortColumn() == null || cbHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            cbHeaderVO.setSortColumn( "employeeCBId,monthly" );
            cbHeaderVO.setSortOrder( "" );
         }
         // 国际化
         cbHeaderVO.reset( null, request );
         if ( KANUtil.filterEmpty( cbHeaderVO.getCbStatusArray() ) != null && cbHeaderVO.getCbStatusArray().length > 0 )
         {
            cbHeaderVO.setCbStatus( KANUtil.toJasonArray( cbHeaderVO.getCbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         cbHeaderVO.getCbStatuses().remove( 0 );
         // 传入当前值对象
         cbBillHolder.setObject( cbHeaderVO );
         // 设置页面记录条数
         cbBillHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         cbHeaderService.getCBHeaderVOsByCondition( cbBillHolder, true );
         refreshHolder( cbBillHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "cbBillListHolder", cbBillHolder );

         // 是否显示导出按钮
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listCBBillTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listCBBill" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }
}
