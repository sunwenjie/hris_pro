package com.kan.base.web.actions.management;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftDetailService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ShiftHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_ATTENDANCE_SHIFT";

   public static final String WEEK_ZH[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

   public static final String WEEK_EN[] = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

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
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
         // 获得Action Form
         final ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) form;
         // 需要设置当前用户AccountId
         shiftHeaderVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( shiftHeaderVO.getSubAction() != null && shiftHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( shiftHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder shiftHeaderHolder = new PagedListHolder();
         // 传入当前页
         shiftHeaderHolder.setPage( page );
         // 传入当前值对象
         shiftHeaderHolder.setObject( shiftHeaderVO );
         // 设置页面记录条数
         shiftHeaderHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         shiftHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         shiftHeaderService.getShiftHeaderVOsByCondition( shiftHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( shiftHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "shiftHeaderHolder", shiftHeaderHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table调用，直接传回ShiftHeader JSP
            return mapping.findForward( "listShiftHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listShiftHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      ( ( ShiftHeaderVO ) form ).reset( mapping, request );

      // 设置Sub Action
      ( ( ShiftHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( ShiftHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "listShiftDetail" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
            // 获得当前FORM
            final ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) form;

            if ( !shiftHeaderVO.getShiftType().equals( "3" ) && KANUtil.filterEmpty( shiftHeaderVO.getShiftIndex() ) != null )
            {
               // 初始化排班频率
               int index = Integer.parseInt( shiftHeaderVO.getShiftIndex() );

               // 如果排班按周
               if ( shiftHeaderVO.getShiftType().equals( "1" ) )
               {
                  index = index * 7;
               }

               // 初始化二维数组，存放排班时间
               final String[][] tempPeriodArray = new String[ index ][];

               // 循环获取ShiftPeriod
               for ( int i = 0; i < index; i++ )
               {
                  String parameterName = "periodArray" + String.valueOf( i + 1 );
                  final String[] periodArray = request.getParameterValues( parameterName );
                  tempPeriodArray[ i ] = periodArray;
               }

               shiftHeaderVO.setPeriodArray( tempPeriodArray );
            }

            shiftHeaderVO.setCreateBy( getUserId( request, response ) );
            shiftHeaderVO.setModifyBy( getUserId( request, response ) );
            shiftHeaderService.insertShiftHeader( shiftHeaderVO );

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            shiftDetailVO.setHeaderId( shiftHeaderVO.getHeaderId() );

            insertlog( request, shiftHeaderVO, Operate.ADD, shiftHeaderVO.getHeaderId(), null );
         }
         else
         {
            // 清空Action Form
            ( ( ShiftHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new ShiftDetailAction().list_object( mapping, shiftDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取ShiftHeaderVO对象
            final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( headerId );
            // 装载界面传值
            shiftHeaderVO.update( ( ShiftHeaderVO ) form );
            // 获取登录用户
            shiftHeaderVO.setModifyBy( getUserId( request, response ) );

            if ( !shiftHeaderVO.getShiftType().equals( "3" ) && KANUtil.filterEmpty( shiftHeaderVO.getShiftIndex() ) != null )
            {
               // 初始化排班频率
               int index = Integer.parseInt( shiftHeaderVO.getShiftIndex() );

               // 如果排班按周
               if ( shiftHeaderVO.getShiftType().equals( "1" ) )
               {
                  index = index * 7;
               }

               // 初始化二维数组，存放排班时间
               final String[][] tempPeriodArray = new String[ index ][];

               // 循环获取ShiftPeriod
               for ( int i = 0; i < index; i++ )
               {
                  String parameterName = "periodArray" + String.valueOf( i + 1 );
                  final String[] periodArray = request.getParameterValues( parameterName );
                  tempPeriodArray[ i ] = periodArray;
               }

               shiftHeaderVO.setPeriodArray( tempPeriodArray );
            }

            // 调用修改方法
            shiftHeaderService.updateShiftHeader( shiftHeaderVO );

            // 重新加载到缓存中
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, shiftHeaderVO, Operate.MODIFY, shiftHeaderVO.getHeaderId(), null );
         }

         // 清空Action Form
         ( ( ShiftHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new ShiftDetailAction().list_object( mapping, new ShiftDetailVO(), request, response );
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
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );
         // 获得Action Form
         ShiftHeaderVO shiftHeaderVO = ( ShiftHeaderVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( shiftHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, shiftHeaderVO, Operate.DELETE, null, shiftHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : shiftHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( selectedId );
               shiftHeaderVO.setHeaderId( selectedId );
               shiftHeaderVO.setAccountId( getAccountId( request, response ) );
               shiftHeaderVO.setModifyBy( getUserId( request, response ) );
               shiftHeaderService.deleteShiftHeader( shiftHeaderVO );
            }
         }
         // 清除Selected IDs和子Action
         shiftHeaderVO.setSelectedIds( "" );
         shiftHeaderVO.setSubAction( "" );

         constantsInit( "initShiftHeader", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 排班类型
         final String shiftType = request.getParameter( "shiftType" );

         // 获得排班频率
         final String shiftIndex = request.getParameter( "shiftIndex" );

         // 获得开始时间（按天用到）
         final String startDate = request.getParameter( "startDate" );

         // 初始化
         final ShiftHeaderVO shiftHeaderVO = new ShiftHeaderVO();
         shiftHeaderVO.setShiftType( shiftType );
         shiftHeaderVO.setShiftIndex( shiftIndex );
         shiftHeaderVO.setStartDate( startDate );

         // 获取临时ShiftDtailVO列表
         final List< Object > shiftDtailVOs = getTempShiftDetailVOsByCondition( shiftHeaderVO );

         final PagedListHolder shiftDetailHolder = new PagedListHolder();
         shiftDetailHolder.setSource( shiftDtailVOs );
         shiftDetailHolder.setHolderSize( shiftDtailVOs.size() );
         request.setAttribute( "listShiftDetailCount", shiftDtailVOs.size() );
         request.setAttribute( "shiftDetailHolder", shiftDetailHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSpecialInfo" );
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 主键获取
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // 初始化Service接口
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );

         // 获得对应ShiftDetailVO列表并写入request
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
         shiftDetailVO.setHeaderId( headerId );
         shiftDetailVO.setStatus( "1" );

         final PagedListHolder shiftDetailHolder = new PagedListHolder();
         shiftDetailHolder.setObject( shiftDetailVO );

         shiftDetailService.getShiftDetailVOsByCondition( shiftDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( shiftDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "shiftDetailHolder", shiftDetailHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSpecialInfo" );
   }

   /**
    * ajax获取ShiftDetailVO列表
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private List< Object > getTempShiftDetailVOsByCondition( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      try
      {
         // 初始化返回值
         final List< Object > shiftDetailVOs = new ArrayList< Object >();

         if ( shiftHeaderVO != null && KANUtil.filterEmpty( shiftHeaderVO.getShiftType(), "0" ) != null )
         {
            // 如果按周
            if ( shiftHeaderVO.getShiftType().equals( "1" ) )
            {
               for ( int i = 0; i < Integer.parseInt( shiftHeaderVO.getShiftIndex() ) * 7; i++ )
               {
                  // 初始化ShiftDetailVO
                  final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
                  shiftDetailVO.setNameZH( WEEK_ZH[ i > 6 ? i % 7 : i ] );
                  shiftDetailVO.setNameEN( WEEK_EN[ i > 6 ? i % 7 : i ] );
                  shiftDetailVO.setDayIndex( String.valueOf( ( i + 1 ) ) );

                  shiftDetailVOs.add( shiftDetailVO );
               }
            }
            // 如果按天
            else if ( shiftHeaderVO.getShiftType().equals( "2" ) )
            {
               for ( int i = 0; i < Integer.parseInt( shiftHeaderVO.getShiftIndex() ); i++ )
               {
                  // 初始化ShiftDetailVO
                  final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
                  shiftDetailVO.setNameZH( KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i ) );
                  shiftDetailVO.setNameEN( KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i ) );
                  shiftDetailVO.setDayIndex( String.valueOf( ( i + 1 ) ) );

                  shiftDetailVOs.add( shiftDetailVO );
               }
            }
         }

         return shiftDetailVOs;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
