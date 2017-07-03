package com.kan.base.web.actions.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.PositionGradeCurrencyVO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionGradeCurrencyService;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jack
 */
public class PositionGradeCurrencyAction extends BaseAction
{

   /**
    * List PositionsGradeCurrency
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

         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "positionGradeCurrencyService" );
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;

         // 如果子Action是删除用户列表
         if ( positionGradeCurrencyVO.getSubAction() != null && positionGradeCurrencyVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }

         // 获取positionGradeId
         String positionGradeId = request.getParameter( "positionGradeId" );
         positionGradeCurrencyVO.setPositionGradeId( positionGradeId );

         final PositionGradeVO positionGradeVO = new PositionGradeVO();
         positionGradeVO.setPositionGradeId( positionGradeId );
         request.setAttribute( "positionGradeForm", positionGradeVO );

         final PagedListHolder pagedListGradeCurrencyHolder = new PagedListHolder();
         // 传入当前页
         pagedListGradeCurrencyHolder.setPage( page );
         // 设置页面记录条数
         pagedListGradeCurrencyHolder.setPageSize( listPageSize );
         // 清空positionGradeCurrencyVO
         positionGradeCurrencyVO.reset();
         // 传入当前值对象
         pagedListGradeCurrencyHolder.setObject( positionGradeCurrencyVO );
         // 调用service 设置是否分页
         positionGradeCurrencyService.getPositionGradeCurrencyVOByCondition( pagedListGradeCurrencyHolder, true );

         // 刷新holder，国际化传值
         refreshHolder( pagedListGradeCurrencyHolder, request );
         request.setAttribute( "pagedListGradeCurrencyHolder", pagedListGradeCurrencyHolder );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();

         // 将当前职级对应的Currency存入Json
         array.addAll( pagedListGradeCurrencyHolder.getSource() );

         request.setAttribute( "currencyListJson", array );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax调用无跳转
            return mapping.findForward( "listPositionGradeCurrency" );
         }
         else
         {
            // 非Ajax调用，Holder需写入Request对象
            request.setAttribute( "pagedListGradeCurrencyHolder", pagedListGradeCurrencyHolder );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listPositionGradeCurrency" );
   }


   
   /**
    * Manage Object
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward manage_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         // 初始化Service接口
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "positionGradeCurrencyService" );
         // 获得ActionForm
         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;
         // 设置账户ID
         positionGradeCurrencyVO.setAccountId( getAccountId( request, response ) );
         // 设置操作人员ID
         positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );

         // 修改positionGradeCurrencyVO
         positionGradeCurrencyService.updatePositionGradeCurrency( positionGradeCurrencyVO );

         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE );
         
         // 清空Form条件
         ( ( PositionGradeCurrencyVO ) form ).reset();
         //         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   
   /**
    * Delete PositionGradeCurrencyList
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final PositionGradeCurrencyService positionGradeCurrencyService = ( PositionGradeCurrencyService ) getService( "positionGradeCurrencyService" );

         final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) form;

         if ( positionGradeCurrencyVO.getSelectedIds() != null && !positionGradeCurrencyVO.getSelectedIds().equals( "" ) )
         {

            for ( String selectedId : positionGradeCurrencyVO.getSelectedIds().split( "," ) )
            {
               //转码
               String newId = new String( selectedId.getBytes( "ISO8859_1" ), "GB2312" );
               positionGradeCurrencyVO.setCurrencyId( newId );
               positionGradeCurrencyVO.setModifyBy( getUserId( request, response ) );
               positionGradeCurrencyService.deletePositionGradeCurrencyByCondition( positionGradeCurrencyVO );
            }

         }
         // 清除Selected IDs和子Action
         positionGradeCurrencyVO.setSelectedIds( "" );
         positionGradeCurrencyVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

}
