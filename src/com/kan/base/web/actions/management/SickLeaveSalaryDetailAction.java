package com.kan.base.web.actions.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SickLeaveSalaryDetailService;
import com.kan.base.service.inf.management.SickLeaveSalaryHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SickLeaveSalaryDetailAction extends BaseAction
{
   public static String accessAction = "HRO_SALARY_SICKLEAVE";

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
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) form;

         // 处理SubAction
         dealSubAction( sickLeaveSalaryDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );

         // 获得主表主键
         String headerId = request.getParameter( "id" );

         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = sickLeaveSalaryDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( headerId );
         // 刷新对象，初始化对象列表及国际化
         sickLeaveSalaryHeaderVO.reset( null, request );
         // 区分修改、查看
         sickLeaveSalaryHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "sickLeaveSalaryHeaderForm", sickLeaveSalaryHeaderVO );
         sickLeaveSalaryDetailVO.setHeaderId( headerId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sickLeaveSalaryDetailHolder = new PagedListHolder();
         // 传入当前页
         sickLeaveSalaryDetailHolder.setPage( page );
         // 传入当前值对象
         sickLeaveSalaryDetailHolder.setObject( sickLeaveSalaryDetailVO );
         // 设置页面记录条数
         sickLeaveSalaryDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOsByCondition( sickLeaveSalaryDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( sickLeaveSalaryDetailHolder, request );

         sickLeaveSalaryDetailVO.setStatus( BaseVO.TRUE );
         sickLeaveSalaryDetailVO.reset( null, request );
         // Holder需写入Request对象
         request.setAttribute( "sickLeaveSalaryDetailHolder", sickLeaveSalaryDetailHolder );
         request.setAttribute( "sickLeaveSalaryDetailForm", sickLeaveSalaryDetailVO );

         List< MappingVO > salaryItems = KANConstants.getKANAccountConstants( sickLeaveSalaryHeaderVO.getAccountId() ).getItemsByType( "1", request.getLocale().getLanguage(), sickLeaveSalaryHeaderVO.getCorpId() );
         request.setAttribute( "salaryItems", salaryItems );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回SickLeaveSalaryDetail JSP
            return mapping.findForward( "listSickLeaveSalaryDetailTable" );
         }

         // 清空subAction
         ( ( SickLeaveSalaryDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listsickLeaveSalaryDetail" );
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
            final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );

            // 获得当前FORM
            final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) form;

            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            List< Object > list = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailByHeaderId( headerId );
            boolean flag = true;
            if ( list != null && list.size() > 0 )
            {
               for ( int i = 0; i < list.size(); i++ )
               {
                  SickLeaveSalaryDetailVO sickLeaveSalaryDetail = ( SickLeaveSalaryDetailVO ) list.get( i );

                  int rangeTo = Integer.parseInt( sickLeaveSalaryDetail.getRangeTo() == null || sickLeaveSalaryDetail.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetail.getRangeTo() );
                  int rangeFrom = Integer.parseInt( sickLeaveSalaryDetail.getRangeFrom() );
                  int rangeToVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeTo() == null || sickLeaveSalaryDetailVO.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetailVO.getRangeTo() );
                  int rangeFromVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeFrom() );

                  if ( rangeTo == rangeToVO || rangeToVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( rangeTo == rangeFromVO || rangeFromVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( sickLeaveSalaryDetail.getRangeTo() != null && sickLeaveSalaryDetail.getRangeFrom() != null )
                  {

                     if ( rangeFrom < rangeToVO && rangeToVO < rangeTo )
                     {
                        flag = false;
                        break;
                     }
                     if ( rangeFrom < rangeFromVO && rangeFromVO < rangeTo )
                     {
                        flag = false;
                        break;
                     }
                  }
               }
            }
            if ( flag )
            {
               sickLeaveSalaryDetailVO.setHeaderId( headerId );
               sickLeaveSalaryDetailVO.setCreateBy( getUserId( request, response ) );
               sickLeaveSalaryDetailVO.setModifyBy( getUserId( request, response ) );
               sickLeaveSalaryDetailVO.setAccountId( getAccountId( request, response ) );
               // 调用添加方法
               sickLeaveSalaryDetailService.insertSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );

               // 重新加载缓存
               constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

               // 返回保存成功的标记
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

               insertlog( request, sickLeaveSalaryDetailVO, Operate.ADD, sickLeaveSalaryDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "添加失败！该区间已存在！", MESSAGE_DETAIL );
            }
         }

         // 清空Form
         ( ( SickLeaveSalaryDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );
            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取主键对象
            final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOByDetailId( detailId );

            List< Object > list = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailByHeaderId( headerId );
            boolean flag = true;
            if ( list != null && list.size() > 0 )
            {
               for ( int i = 0; i < list.size(); i++ )
               {
                  SickLeaveSalaryDetailVO sickLeaveSalaryDetail = ( SickLeaveSalaryDetailVO ) list.get( i );
                  if ( sickLeaveSalaryDetail.getDetailId().equals( sickLeaveSalaryDetailVO.getDetailId() ) )
                  {
                     continue;
                  }
                  int rangeTo = Integer.parseInt( sickLeaveSalaryDetail.getRangeTo() == null || sickLeaveSalaryDetail.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetail.getRangeTo() );
                  int rangeFrom = Integer.parseInt( sickLeaveSalaryDetail.getRangeFrom() );
                  int rangeToVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeTo() == null || sickLeaveSalaryDetailVO.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetailVO.getRangeTo() );
                  int rangeFromVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeFrom() );

                  if ( rangeTo == rangeToVO || rangeToVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( rangeTo == rangeFromVO || rangeFromVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( sickLeaveSalaryDetail.getRangeTo() != null && sickLeaveSalaryDetail.getRangeFrom() != null )
                  {

                     if ( rangeFrom < rangeToVO && rangeToVO < rangeTo )
                     {
                        flag = false;
                        break;
                     }
                     if ( rangeFrom < rangeFromVO && rangeFromVO < rangeTo )
                     {

                        flag = false;
                        break;
                     }
                  }
               }
            }

            if ( flag )
            {
               // 装载界面传值
               sickLeaveSalaryDetailVO.setHeaderId( headerId );
               sickLeaveSalaryDetailVO.update( ( SickLeaveSalaryDetailVO ) form );
               sickLeaveSalaryDetailVO.setModifyBy( getUserId( request, response ) );
               // 调用修改接口
               sickLeaveSalaryDetailService.updateSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );

               // 重新加载缓存
               constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

               // 返回编辑成功的标记
               success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

               insertlog( request, sickLeaveSalaryDetailVO, Operate.MODIFY, sickLeaveSalaryDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "编辑失败！该区间已存在！", MESSAGE_DETAIL );
            }
         }

         // 清空Form
         ( ( SickLeaveSalaryDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );

         // 初始化Service接口
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );
         // 主键主表ID
         final String detailId = request.getParameter( "detailId" );
         // 获得SickLeaveSalaryDetailVO对象
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOByDetailId( detailId );
         // 获得SickLeaveSalaryHeaderVO对象
         final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( sickLeaveSalaryDetailVO.getHeaderId() );
         // 国际化传值
         sickLeaveSalaryDetailVO.reset( null, request );
         // 区分修改添加
         sickLeaveSalaryDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "sickLeaveSalaryHeaderForm", sickLeaveSalaryHeaderVO );
         request.setAttribute( "sickLeaveSalaryDetailForm", sickLeaveSalaryDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageSickLeaveSalaryDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );

         // 获得当前form
         SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) form;

         // 存在选中的ID
         if ( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, sickLeaveSalaryDetailVO, Operate.DELETE, null, sickLeaveSalaryDetailVO.getSelectedIds() );
            // 分割
            for ( String selectedId : sickLeaveSalaryDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               sickLeaveSalaryDetailVO = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOByDetailId( selectedId );
               sickLeaveSalaryDetailVO.setModifyBy( getUserId( request, response ) );
               sickLeaveSalaryDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               sickLeaveSalaryDetailService.deleteSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );
            }

            // 重新加载缓存
            constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         ( ( SickLeaveSalaryDetailVO ) form ).setSelectedIds( "" );
         ( ( SickLeaveSalaryDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
