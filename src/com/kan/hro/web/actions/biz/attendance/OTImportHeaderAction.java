package com.kan.hro.web.actions.biz.attendance;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.OTImportBatchService;
import com.kan.hro.service.inf.biz.attendance.OTImportHeaderService;

public class OTImportHeaderAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_OT_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获取批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 初始化Service接口
         final OTImportHeaderService timesheetHeaderService = ( OTImportHeaderService ) getService( "otImportHeaderService" );
         final OTImportBatchService timesheetBatchService = ( OTImportBatchService ) getService( "otImportBatchService" );

         // 获取TimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );
         // 写入request
         request.setAttribute( "timesheetBatchForm", timesheetBatchVO );

         // 获得Action Form
         final OTImportHeaderVO otImportHeaderVO = ( OTImportHeaderVO ) form;
         
         //处理数据权限
         //setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), OTImportBatchAction.accessAction,otImportHeaderVO);
         setDataAuth( request, response, otImportHeaderVO );
         otImportHeaderVO.setBatchId(batchId);

         // 如果没有指定排序则默认按 otheaderId排序
         if ( otImportHeaderVO.getSortColumn() == null || otImportHeaderVO.getSortColumn().isEmpty() )
         {
            otImportHeaderVO.setSortOrder( "desc" );
            otImportHeaderVO.setSortColumn( "a.otheaderId" );
         }

         // 如果是inHouse
//         if ( isInHouseRole( request, response ) )
//         {
//            // 如果非HR部门
//            if ( !isHRFunction( request, response ) )
//            {
//               // 只能查看自己的考勤表
//               otImportHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
//            }
//         }

         // 设定查看更改权限
         setHRFunctionRole( mapping, form, request, response );

         // 处理subAction
         dealSubAction( otImportHeaderVO, mapping, form, request, response );

         otImportHeaderVO.setBatchId( batchId );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( otImportHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
      
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            timesheetHeaderService.getOTImportHeaderVOsByCondition( pagedListHolder, false );
            String selectids = "";

            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object pageObject : pagedListHolder.getSource() )
               {
                  selectids = selectids + ( ( OTImportHeaderVO ) pageObject ).getEncodedId() + ",";
               }
            }
            otImportHeaderVO.setSelectedIds( selectids );

         }
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetHeaderService.getOTImportHeaderVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         

         
         // Holder需写入Request对象
         request.setAttribute( "otImportHeaderHolder", pagedListHolder );
         //批次状态
         request.setAttribute("batchStatus", request.getParameter("batchStatus"));
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            // 设定查看更改权限
            setHRFunctionRole( mapping, form, request, response );
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "otImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "otImportHeader" );
   }



	@Override
	public ActionForward to_objectNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ActionForward add_object(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ActionForward to_objectModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ActionForward modify_object(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void delete_object(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void delete_objectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		// TODO Auto-generated method stub
		
	}
	
	public void backUpRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		final OTImportHeaderService timesheetHeaderService = ( OTImportHeaderService ) getService( "otImportHeaderService" );
		String[] ids = request.getParameterValues("ids");
		String batchId = request.getParameter("batchId");
		int count = timesheetHeaderService.backUpRecord(ids,KANUtil.decodeStringFromAjax( batchId ));
		try {
			final PrintWriter out = response.getWriter();
			out.print(count+"");
			out.flush();
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
