package com.kan.hro.web.actions.biz.sb;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderTempService;
import com.kan.hro.service.inf.biz.sb.VendorSBTempService;

public class VendorSBAction extends BaseAction
{
   public final static String ACCESSACTION = "HRO_SB_HEADER";

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
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         if ( new Boolean( ajax ) )
         {
            decodedObject( commonBatchVO );
            // 如果子SubAction是删除列表操作deleteObjects
            if ( getSubAction( form ).equalsIgnoreCase( ROLLBACK_OBJECTS ) )
            {
               // 调用删除列表的SubAction
               rollback_objectList( mapping, form, request, response );
            }
            // 如果子SubAction是更新列表操作submitObjects
            else if ( getSubAction( form ).equalsIgnoreCase( SUBMIT_OBJECTS ) )
            {
               // 调用修改列表的SubAction
               submit_objectList( mapping, form, request, response );
            }
            // 如果SubAction为空，通常是搜索，点击排序、翻页或导出操作。Ajax提交的搜索内容需要解码。
         }

         commonBatchVO.setAccessAction( ACCESSACTION );
         // 如果没有指定排序则默认按 batchId排序
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortColumn( "batchId" );
            commonBatchVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( commonBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         commonBatchService.getCommonBatchVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "vendorSBBatchHolder", pagedListHolder );

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listCommonBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listCommonBatch" );
   }

   /**  
    * To SBHeaderTemp
    *	供应商社保临时表Header页面
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_sbHeaderTemp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 获得批次主键ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // 初始化Service接口
      final SBHeaderTempService sbHeaderTempService = ( SBHeaderTempService ) getService( "sbHeaderTempService" );
      final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

      final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

      commonBatchVO.reset( null, request );
      request.setAttribute( "commonBatchVO", commonBatchVO );

      // 获得当前页
      final String page = getPage( request );
      // 获得是否Ajax调用
      final String ajax = getAjax( request );
      final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) form;
      sbHeaderTempVO.setBatchId( batchId );

      // 设置当前用户AccountId
      sbHeaderTempVO.setAccountId( getAccountId( request, response ) );

      // 设置当前的clientId
      sbHeaderTempVO.setClientId( getCorpId( request, response ) );

      // 初始化PagedListHolder，用于引用方式调用Service
      final PagedListHolder sbHeaderTempHolder = new PagedListHolder();

      // 传入当前页
      sbHeaderTempHolder.setPage( page );

      // 如果没有指定排序则默认按 BatchId排序
      if ( sbHeaderTempVO.getSortColumn() == null || sbHeaderTempVO.getSortColumn().isEmpty() )
      {
         sbHeaderTempVO.setSortColumn( "headerId" );
         sbHeaderTempVO.setSortOrder( "desc" );
      }

      // 传入当前值对象
      sbHeaderTempHolder.setObject( sbHeaderTempVO );
      // 设置页面记录条数
      sbHeaderTempHolder.setPageSize( listPageSize_medium );
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      sbHeaderTempService.getSBHeaderTempVOsByCondition( sbHeaderTempHolder, true );
      // 刷新Holder，国际化传值
      refreshHolder( sbHeaderTempHolder, request );
      // Holder需写入Request对象
      request.setAttribute( "sbHeaderTempHolder", sbHeaderTempHolder );

      if ( new Boolean( ajax ) )
      {
         // 写入Role
         return mapping.findForward( "listSBHeaderTempTable" );
      }

      return mapping.findForward( "listSBHeaderTempBody" );
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

   public ActionForward modify_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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

   protected void submit_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final VendorSBTempService vendorSBTempService = ( VendorSBTempService ) getService( "vendorSBTempService" );

         // 获得Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         // 存在选中的ID
         if ( commonBatchVO.getSelectedIds() != null && !commonBatchVO.getSelectedIds().trim().isEmpty() )
         {
            int rows = 0;

            // 分割
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化CommonBatchVO
                  final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempCommonBatchVO.setModifyBy( getUserId( request, response ) );
                  tempCommonBatchVO.setModifyDate( new Date() );

                  // 调用接口
                  rows += vendorSBTempService.updateBatch( tempCommonBatchVO );
               }

               if ( rows == 0 )
               {
                  warning( request, null, "未创建调整数据", MESSAGE_HEADER );
               }
               else
               {
                  success( request, null, "创建成功  " + String.valueOf( rows ) + " 条调整数据!", MESSAGE_HEADER );
               }

            }

         }

         // 清除Selected IDs和子Action
         commonBatchVO.setSelectedIds( "" );
         commonBatchVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   protected void rollback_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final VendorSBTempService vendorSBTempService = ( VendorSBTempService ) getService( "vendorSBTempService" );

         // 获得Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         // 存在选中的ID
         if ( commonBatchVO.getSelectedIds() != null && !commonBatchVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化CommonBatchVO
                  final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
                  // 调用删除接口
                  tempCommonBatchVO.setModifyBy( getUserId( request, response ) );
                  tempCommonBatchVO.setModifyDate( new Date() );

                  vendorSBTempService.rollbackBatch( tempCommonBatchVO );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );
         }

         // 清除Selected IDs和子Action
         commonBatchVO.setSelectedIds( "" );
         commonBatchVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
