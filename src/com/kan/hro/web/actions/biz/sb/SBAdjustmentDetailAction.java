package com.kan.hro.web.actions.biz.sb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBDetailService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentDetailService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentHeaderService;

public class SBAdjustmentDetailAction extends BaseAction
{

   /**
    * 申报调整详情
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = getAjax( request );

         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( SBAdjustmentDetailVO ) form ).getAdjustmentHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

         // 社保调整科目处理
         sb_adjustment_item( request, employeeContractSBDetailService, sbAdjustmentHeaderVO.getEmployeeSBId() );

         // 刷新国际化
         sbAdjustmentHeaderVO.reset( null, request );

         // 设置SubAction
         sbAdjustmentHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "sbAdjustmentHeaderForm", sbAdjustmentHeaderVO );

         // 获得Action Form
         final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;

         sbAdjustmentDetailVO.setAdjustmentHeaderId( headerId );

         // 处理SubAction
         dealSubAction( sbAdjustmentDetailVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbAdjustmentDetailHolder = new PagedListHolder();
         // 传入当前页
         sbAdjustmentDetailHolder.setPage( page );
         // 传入当前值对象
         sbAdjustmentDetailHolder.setObject( sbAdjustmentDetailVO );
         // 设置页面记录条数
         sbAdjustmentDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbAdjustmentDetailService.getSBAdjustmentDetailVOsByCondition( sbAdjustmentDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( sbAdjustmentDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbAdjustmentDetailHolder", sbAdjustmentDetailHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用
            return mapping.findForward( "listSBAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSBAdjustmentDetail" );
   }

   /**
    * 申报调整确认详情
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward list_object_confirm( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = getAjax( request );

         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );

         // 获得主表主键
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         // 获得主表对象
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );
         // 社保调整科目处理
         sb_adjustment_item( request, employeeContractSBDetailService, sbAdjustmentHeaderVO.getEmployeeSBId() );

         // 刷新国际化
         sbAdjustmentHeaderVO.reset( null, request );

         // 设置SubAction
         sbAdjustmentHeaderVO.setSubAction( VIEW_OBJECT );

         // 写入request对象
         request.setAttribute( "sbAdjustmentHeaderForm", sbAdjustmentHeaderVO );

         // 获得Action Form
         final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;

         sbAdjustmentDetailVO.setAdjustmentHeaderId( headerId );
         // 处理SubAction
         dealSubAction( sbAdjustmentDetailVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbAdjustmentDetailHolder = new PagedListHolder();
         // 传入当前页
         sbAdjustmentDetailHolder.setPage( page );
         // 传入当前值对象
         sbAdjustmentDetailHolder.setObject( sbAdjustmentDetailVO );
         // 设置页面记录条数
         sbAdjustmentDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbAdjustmentDetailService.getSBAdjustmentDetailVOsByCondition( sbAdjustmentDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( sbAdjustmentDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbAdjustmentDetailHolder", sbAdjustmentDetailHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回CalendarDetail JSP
            return mapping.findForward( "listSBAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSBAdjustmentDetailConfrim" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
            final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得SBAdjustmentHeaderVO
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

            // 获得当前FORM
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;

            String attribute = null;

            if ( sbAdjustmentHeaderVO != null )
            {
               // 获得EmployeeContractSBVO
               final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbAdjustmentHeaderVO.getEmployeeSBId() );

               if ( employeeContractSBVO != null )
               {
                  // 获得SocialBenefitSolutionDTO
                  final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );

                  if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
                  {
                     // 获得socialBenefitDTO
                     final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSysHeaderId() );

                     if ( socialBenefitDTO != null )
                     {
                        // 获取SocialBenefitDetailVO
                        final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDTO.getSocialBenefitDetailVOByItemId( sbAdjustmentDetailVO.getItemId() );

                        if ( socialBenefitDetailVO != null )
                        {
                           attribute = socialBenefitDetailVO.getAttribute();
                        }
                     }
                  }
               }
            }

            // 初始化ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

            int gapMonths = 0;
            if ( attribute != null && KANUtil.filterEmpty( attribute ) != null )
            {
               if ( attribute.trim().equals( "2" ) )
               {
                  gapMonths = 1;
               }
               else if ( attribute.trim().equals( "3" ) )
               {
                  gapMonths = -1;
               }
            }

            sbAdjustmentDetailVO.setAdjustmentHeaderId( headerId );
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
            sbAdjustmentDetailVO.setMonthly( sbAdjustmentHeaderVO.getMonthly() );
            sbAdjustmentDetailVO.setCreateBy( getUserId( request, response ) );
            sbAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            sbAdjustmentDetailService.insertSBAdjustmentDetail( sbAdjustmentDetailVO );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, sbAdjustmentDetailVO, Operate.ADD, sbAdjustmentDetailVO.getAdjustmentDetailId(), null );
         }
         else
         {
            // 返回出错的标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // 清空Form
         ( ( SBAdjustmentDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );

         // 初始化Service接口
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // 主键从表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得SBAdjustmentDetailVO对象
         final SBAdjustmentDetailVO sbAdjustmentDetailVO = sbAdjustmentDetailService.getSBAdjustmentDetailVOByAdjustmentDetailId( detailId );

         // 获得SBAdjustmentHeaderVO对象
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );

         // 社保调整科目处理
         sb_adjustment_item( request, employeeContractSBDetailService, sbAdjustmentHeaderVO.getEmployeeSBId() );

         String attribute = null;

         if ( sbAdjustmentHeaderVO != null )
         {
            // 获得EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbAdjustmentHeaderVO.getEmployeeSBId() );

            if ( employeeContractSBVO != null )
            {
               // 获得SocialBenefitSolutionDTO
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );

               if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
               {
                  // 获得socialBenefitDTO
                  final SocialBenefitDTO socialBenefitDTO = KANConstants.getSocialBenefitDTOBySBHeaderId( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSysHeaderId() );

                  if ( socialBenefitDTO != null )
                  {
                     // 获取SocialBenefitDetailVO
                     final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDTO.getSocialBenefitDetailVOByItemId( sbAdjustmentDetailVO.getItemId() );

                     if ( socialBenefitDetailVO != null )
                     {
                        attribute = socialBenefitDetailVO.getAttribute();
                     }
                  }
               }
            }
         }

         // 初始化ItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

         int gapMonths = 0;
         if ( attribute != null && KANUtil.filterEmpty( attribute ) != null )
         {
            if ( attribute.trim().equals( "2" ) )
            {
               gapMonths = 1;
            }
            else if ( attribute.trim().equals( "3" ) )
            {
               gapMonths = -1;
            }
         }

         if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameZH() ) == null )
         {
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
         }
         if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameEN() ) == null )
         {
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         }
         if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getAccountMonthly() ) == null )
         {
            sbAdjustmentDetailVO.setAccountMonthly( KANUtil.getMonthly( sbAdjustmentHeaderVO.getMonthly(), gapMonths ) );
         }

         // 国际化传值
         sbAdjustmentDetailVO.reset( null, request );
         // 设置SubAction
         sbAdjustmentDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "sbAdjustmentHeaderForm", sbAdjustmentHeaderVO );
         request.setAttribute( "sbAdjustmentDetailForm", sbAdjustmentDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageSBAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );

            // 主键获取 - 需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );

            // 获取主键对象
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = sbAdjustmentDetailService.getSBAdjustmentDetailVOByAdjustmentDetailId( detailId );

            // 装载界面传值
            sbAdjustmentDetailVO.update( ( SBAdjustmentDetailVO ) form );

            // 初始化ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );

            if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameZH() ) == null )
            {
               sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            }
            if ( KANUtil.filterEmpty( sbAdjustmentDetailVO.getNameEN() ) == null )
            {
               sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
            }

            sbAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            sbAdjustmentDetailService.updateSBAdjustmentDetail( sbAdjustmentDetailVO );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, sbAdjustmentDetailVO, Operate.MODIFY, sbAdjustmentDetailVO.getAdjustmentDetailId(), null );
         }
         else
         {
            // 返回出错的标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // 清空Form
         ( ( SBAdjustmentDetailVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBAdjustmentDetailService sbAdjustmentDetailService = ( SBAdjustmentDetailService ) getService( "sbAdjustmentDetailService" );
         // 获得当前form
         SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) form;
         // 存在选中的ID
         if ( sbAdjustmentDetailVO.getSelectedIds() != null && !sbAdjustmentDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : sbAdjustmentDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               sbAdjustmentDetailVO = sbAdjustmentDetailService.getSBAdjustmentDetailVOByAdjustmentDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               sbAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );
               sbAdjustmentDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               sbAdjustmentDetailService.deleteSBAdjustmentDetail( sbAdjustmentDetailVO );
            }

            insertlog( request, sbAdjustmentDetailVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( sbAdjustmentDetailVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         ( ( SBAdjustmentDetailVO ) form ).setSelectedIds( "" );
         ( ( SBAdjustmentDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   //  社保调整科目处理
   private void sb_adjustment_item( final HttpServletRequest request, final EmployeeContractSBDetailService employeeContractSBDetailService, final String employeeSBId )
         throws KANException
   {
      //  获取EmployeeContractSBDetailVO列表
      final List< Object > employeeContractSBDetailVOs = employeeContractSBDetailService.getEmployeeContractSBDetailVOsByEmployeeSBId( employeeSBId );

      // 初始化item列表
      final List< MappingVO > items = new ArrayList< MappingVO >();

      if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
      {
         for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
         {
            // 获取ItemVO
            final ItemVO itemVO = KANConstants.getItemVOByItemId( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getItemId() );

            // 初始化MappingVO
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( itemVO.getItemId() );

            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( itemVO.getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( itemVO.getNameEN() );
            }

            items.add( mappingVO );
         }
      }

      items.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      request.setAttribute( "items", items );
   }

}
