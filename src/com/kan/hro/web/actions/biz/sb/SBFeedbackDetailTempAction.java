package com.kan.hro.web.actions.biz.sb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.service.inf.biz.sb.SBFeedbackDetailTempService;
import com.kan.hro.service.inf.biz.sb.SBFeedbackHeaderTempService;

public class SBFeedbackDetailTempAction extends BaseAction
{
   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {

         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final SBFeedbackHeaderTempService sbFeedbackHeaderTempService = ( SBFeedbackHeaderTempService ) getService( "sbFeedbackHeaderTempService" );
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );

         // 获得批次主键ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );
         final SBHeaderTempVO sbHeaderTempVO = sbFeedbackHeaderTempService.getSBHeaderTempVOByHeaderId( headerId );
         final String batchId = sbHeaderTempVO.getBatchId();
         final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

         // 国际化
         commonBatchVO.reset( null, request );
         sbHeaderTempVO.reset( null, request );
         request.setAttribute( "commonBatchVO", commonBatchVO );
         request.setAttribute( "sbHeaderTempVO", sbHeaderTempVO );

         // 初始化查询对象SBDetailTempVO
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) form;
         sbDetailTempVO.setHeaderId( headerId );

         if ( new Boolean( ajax ) )
         {
            decodedObject( sbDetailTempVO );
            // 如果子SubAction是删除列表操作deleteObjects
            if ( getSubAction( form ).equalsIgnoreCase( ROLLBACK_OBJECTS ) )
            {
               // 调用删除列表的SubAction
               rollback_objectList( mapping, form, request, response );
            }
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbDetailTempHolder = new PagedListHolder();
         // 传入当前页
         sbDetailTempHolder.setPage( page );

         // 传入排序相关字段
         sbDetailTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         sbDetailTempVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailTempVO.setCorpId( getCorpId( request, response ) );
         }

         // 如果没有指定排序则默认按服务协议流水号排序
         if ( sbDetailTempVO.getSortColumn() == null || sbDetailTempVO.getSortColumn().isEmpty() )
         {
            sbDetailTempVO.setSortColumn( "detailId" );
            sbDetailTempVO.setSortOrder( "desc" );
         }

         // 国际化
         sbDetailTempVO.reset( null, request );
         request.setAttribute( "sbDetailTempForm", sbDetailTempVO );
         // 传入当前值对象
         sbDetailTempHolder.setObject( sbDetailTempVO );
         // 设置页面记录条数
         sbDetailTempHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbFeedbackDetailTempService.getSBDetailTempVOsByCondition( sbDetailTempHolder, true );
         refreshHolder( sbDetailTempHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbDetailTempHolder", sbDetailTempHolder );

         // Ajax
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listDetailTemp" );
   }

   public ActionForward get_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );

         final String detailId = KANUtil.decodeStringFromAjax( request.getParameter( "detailId" ) );
         final SBDetailTempVO sbDetailTempVO = sbFeedbackDetailTempService.getSBDetailTempVOByDetailId( detailId );

         // 传送值
         request.setAttribute( "sbDetailTempForm", sbDetailTempVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "handleSBDetailTempPopup" );
   }

   public ActionForward modify_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );

         final String detailId = KANUtil.decodeStringFromAjax( request.getParameter( "detailId" ) );
         final SBDetailTempVO sbDetailTempVO = sbFeedbackDetailTempService.getSBDetailTempVOByDetailId( detailId );

         SBDetailTempVO tempSBSBDetailTempVO = ( SBDetailTempVO ) form;

         sbDetailTempVO.setAmountCompany( tempSBSBDetailTempVO.getAmountCompany() );
         sbDetailTempVO.setAmountPersonal( tempSBSBDetailTempVO.getAmountPersonal() );

         sbFeedbackDetailTempService.updateSBDetailTemp( sbDetailTempVO );
         // 发送更新成功标记
         success( request, MESSAGE_TYPE_UPDATE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      ( ( SBDetailTempVO ) form ).setDetailId( "" );
      // 跳转到查询
      return list_object( mapping, form, request, response );
   }

   protected void rollback_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );
         // 获得Action Form
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) form;

         // 存在选中的ID
         if ( sbDetailTempVO.getSelectedIds() != null && !sbDetailTempVO.getSelectedIds().trim().isEmpty() )
         {

            // 分割
            for ( String selectedId : sbDetailTempVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 调用删除接口
                  sbFeedbackDetailTempService.deleteSBDetailTemp( KANUtil.decodeStringFromAjax( selectedId ) );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );

         }

         // 清除Selected IDs和子Action
         sbDetailTempVO.setSelectedIds( "" );
         sbDetailTempVO.setSubAction( "" );
      }
      catch ( final Exception e )
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
