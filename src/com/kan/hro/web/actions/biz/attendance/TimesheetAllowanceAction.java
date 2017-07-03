package com.kan.hro.web.actions.biz.attendance;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;

public class TimesheetAllowanceAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
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
         // 初始化TimesheetHeaderVO
         final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final TimesheetAllowanceService timesheetAllowanceService = ( TimesheetAllowanceService ) getService( "timesheetAllowanceService" );
            // 获得当前FORM
            final TimesheetAllowanceVO timesheetAllowanceVO = ( TimesheetAllowanceVO ) form;
            timesheetAllowanceVO.setCreateBy( getUserId( request, response ) );
            timesheetAllowanceVO.setModifyBy( getUserId( request, response ) );
            timesheetAllowanceVO.setAccountId( getAccountId( request, response ) );

            timesheetAllowanceService.insertTimesheetAllowance( timesheetAllowanceVO );
            timesheetHeaderVO.setHeaderId( timesheetAllowanceVO.getHeaderId() );
         }
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final TimesheetBatchVO timesheetBatchVO = new TimesheetBatchVO();
            timesheetBatchVO.reset( null, request );
            return new TimesheetBatchAction().list_object( mapping, timesheetBatchVO, request, response );
         }

         // 清空Form
         ( ( TimesheetAllowanceVO ) form ).reset();

         return new TimesheetHeaderAction().to_objectModify( mapping, timesheetHeaderVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         final TimesheetAllowanceService timesheetAllowanceService = ( TimesheetAllowanceService ) getService( "timesheetAllowanceService" );

         // 获取主键 - 需解码
         final String allowanceId = KANUtil.decodeString( request.getParameter( "allowanceId" ) );

         // 获取TimesheetAllowanceVO
         final TimesheetAllowanceVO timesheetAllowanceVO = timesheetAllowanceService.getTimesheetAllowanceVOByAllowanceId( allowanceId );

         if ( timesheetAllowanceVO != null )
         {
            timesheetAllowanceVO.reset( null, request );
            timesheetAllowanceVO.setSubAction( VIEW_OBJECT );
            final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( timesheetAllowanceVO.getHeaderId() );

            // 写入request
            request.setAttribute( "timesheetHeaderForm", timesheetHeaderVO );
            request.setAttribute( "timesheetAllowanceForm", timesheetAllowanceVO );
         }

         // AJAX调用跳转FORM页面
         return mapping.findForward( "manageTimesheetAllowanceForm" );
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
         // 初始化TimesheetHeaderVO
         final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();

         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final TimesheetAllowanceService timesheetAllowanceService = ( TimesheetAllowanceService ) getService( "timesheetAllowanceService" );

            // 获取ActionForm
            final TimesheetAllowanceVO timesheetAllowanceForm = ( TimesheetAllowanceVO ) form;

            // 主键获取需解码
            final String allowanceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "allowanceId" ), "UTF-8" ) );

            // 获取TimesheetHeaderVO
            final TimesheetAllowanceVO timesheetAllowanceVO = timesheetAllowanceService.getTimesheetAllowanceVOByAllowanceId( allowanceId );

            // 获取登录用户
            timesheetAllowanceVO.setModifyBy( getUserId( request, response ) );
            timesheetAllowanceVO.setModifyDate( new Date() );
            timesheetAllowanceVO.update( timesheetAllowanceForm );

            timesheetAllowanceService.updateTimesheetAllowance( timesheetAllowanceVO );
            timesheetHeaderVO.setHeaderId( timesheetAllowanceVO.getHeaderId() );
         }
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final TimesheetBatchVO timesheetBatchVO = new TimesheetBatchVO();
            timesheetBatchVO.reset( null, request );
            return new TimesheetBatchAction().list_object( mapping, timesheetBatchVO, request, response );
         }
         // 清空FORM
         ( ( TimesheetAllowanceVO ) form ).reset();

         return new TimesheetHeaderAction().to_objectModify( mapping, timesheetHeaderVO, request, response );
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
      // No Use
   }

}
