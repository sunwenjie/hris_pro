package com.kan.base.web.actions.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.AnnualLeaveRuleDetailService;
import com.kan.base.service.inf.management.AnnualLeaveRuleHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class AnnualLeaveRuleDetailAction extends BaseAction
{
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) form;
         // 处理SubAction
         dealSubAction( annualLeaveRuleDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = annualLeaveRuleDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
         // 刷新对象，初始化对象列表及国际化
         annualLeaveRuleHeaderVO.reset( null, request );
         // 区分修改、查看
         annualLeaveRuleHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "annualLeaveRuleHeaderForm", annualLeaveRuleHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder annualLeaveRuleDetailHolder = new PagedListHolder();
         annualLeaveRuleDetailVO.setHeaderId( headerId );
         // 传入当前页
         annualLeaveRuleDetailHolder.setPage( page );
         // 传入当前值对象
         annualLeaveRuleDetailHolder.setObject( annualLeaveRuleDetailVO );
         // 设置页面记录条数
         annualLeaveRuleDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOsByCondition( annualLeaveRuleDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( annualLeaveRuleDetailHolder, request );

         annualLeaveRuleDetailVO.setStatus( BaseVO.TRUE );
         annualLeaveRuleDetailVO.reset( null, request );
         // Holder需写入Request对象
         request.setAttribute( "annualLeaveRuleDetailHolder", annualLeaveRuleDetailHolder );
         request.setAttribute( "annualLeaveRuleDetailForm", annualLeaveRuleDetailVO );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回AnnualLeaveRuleDetail JSP
            return mapping.findForward( "listAnnualLeaveRuleDetailTable" );
         }

         // 清空subAction
         ( ( AnnualLeaveRuleDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listAnnualLeaveRuleDetail" );
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
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );

            // 获得当前FORM
            final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) form;

            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            final List< Object > annualLeaveRuleDetailVOs = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOsByHeaderId( headerId );

            if ( checkAnnualLeaveRuleMonthSoction( annualLeaveRuleDetailVOs, annualLeaveRuleDetailVO, null ) )
            {
               annualLeaveRuleDetailVO.setHeaderId( headerId );
               annualLeaveRuleDetailVO.setCreateBy( getUserId( request, response ) );
               annualLeaveRuleDetailVO.setModifyBy( getUserId( request, response ) );
               annualLeaveRuleDetailVO.setAccountId( getAccountId( request, response ) );
               // 调用添加方法
               annualLeaveRuleDetailService.insertAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );

               // 重新加载缓存
               constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

               // 返回保存成功的标记
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

               insertlog( request, annualLeaveRuleDetailVO, Operate.ADD, annualLeaveRuleDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "添加失败！该区间已存在！", MESSAGE_DETAIL );
            }
         }

         // 清空Form
         ( ( AnnualLeaveRuleDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   private boolean checkAnnualLeaveRuleMonthSoction( final List< Object > annualLeaveRuleDetailVOs, final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO, final String detailId )
   {
      boolean flag = true;
      if ( annualLeaveRuleDetailVOs != null && annualLeaveRuleDetailVOs.size() > 0 )
      {
         String positionGradeId = annualLeaveRuleDetailVO.getPositionGradeId();
         String seniority = annualLeaveRuleDetailVO.getSeniority();

         for ( Object o : annualLeaveRuleDetailVOs )
         {
            final AnnualLeaveRuleDetailVO tempAannualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) o;

            if ( detailId != null && tempAannualLeaveRuleDetailVO.getDetailId().equals( detailId ) )
               continue;

            final String tempPositionGradeId = tempAannualLeaveRuleDetailVO.getPositionGradeId();
            final String tempSeniority = tempAannualLeaveRuleDetailVO.getSeniority();

            if ( positionGradeId.equals( tempPositionGradeId ) && seniority.equals( tempSeniority ) )
            {
               flag = false;
               break;
            }
         }
      }

      return flag;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );

         // 初始化Service接口
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );
         // 主键主表ID
         final String detailId = request.getParameter( "detailId" );
         // 获得AnnualLeaveRuleDetailVO对象
         final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOByDetailId( detailId );
         // 获得AnnualLeaveRuleHeaderVO对象
         final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( annualLeaveRuleDetailVO.getHeaderId() );
         // 国际化传值
         annualLeaveRuleDetailVO.reset( null, request );
         // 区分修改添加
         annualLeaveRuleDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "annualLeaveRuleHeaderForm", annualLeaveRuleHeaderVO );
         request.setAttribute( "annualLeaveRuleDetailForm", annualLeaveRuleDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageAnnualLeaveRuleDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );
            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取主键对象
            final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOByDetailId( detailId );
            final List< Object > annualLeaveRuleDetailVOs = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOsByHeaderId( headerId );

            if ( checkAnnualLeaveRuleMonthSoction( annualLeaveRuleDetailVOs, ( AnnualLeaveRuleDetailVO ) form, detailId ) )
            {
               // 装载界面传值
               annualLeaveRuleDetailVO.setHeaderId( headerId );
               annualLeaveRuleDetailVO.update( ( AnnualLeaveRuleDetailVO ) form );
               annualLeaveRuleDetailVO.setModifyBy( getUserId( request, response ) );
               // 调用修改接口
               annualLeaveRuleDetailService.updateAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );

               // 重新加载缓存
               constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

               // 返回编辑成功的标记
               success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

               insertlog( request, annualLeaveRuleDetailVO, Operate.MODIFY, annualLeaveRuleDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "编辑失败！该区间已存在！", MESSAGE_DETAIL );
            }
         }

         // 清空Form
         ( ( AnnualLeaveRuleDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );

         // 获得当前form
         AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) form;

         // 存在选中的ID
         if ( KANUtil.filterEmpty( annualLeaveRuleDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, annualLeaveRuleDetailVO, Operate.DELETE, null, annualLeaveRuleDetailVO.getSelectedIds() );
            // 分割
            for ( String selectedId : annualLeaveRuleDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               annualLeaveRuleDetailVO = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOByDetailId( selectedId );
               annualLeaveRuleDetailVO.setModifyBy( getUserId( request, response ) );
               annualLeaveRuleDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               annualLeaveRuleDetailService.deleteAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );
            }

            // 重新加载缓存
            constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         ( ( AnnualLeaveRuleDetailVO ) form ).setSelectedIds( "" );
         ( ( AnnualLeaveRuleDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
