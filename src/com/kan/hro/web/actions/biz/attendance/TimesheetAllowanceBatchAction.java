package com.kan.hro.web.actions.biz.attendance;

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
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceBatchService;

public class TimesheetAllowanceBatchAction extends BaseAction
{

   // 当前Action对应的Access Action - In House
   public static String accessAction = "HRO_BIZ_ATTENDANCE_TIMESHEET_ALLOWANCE";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final TimesheetAllowanceBatchService timesheetBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

         // 获得Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         List< MappingVO > statuses = new ArrayList<MappingVO>();
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingTemp("");
         mappingVO.setMappingId("");
         mappingVO.setMappingValue("请选择");
         statuses.add(mappingVO);
         mappingVO = new MappingVO();
         mappingVO.setMappingTemp("1");
         mappingVO.setMappingId("1");
         mappingVO.setMappingValue("新建");
         statuses.add(mappingVO);
         mappingVO = new MappingVO();
         mappingVO.setMappingTemp("2");
         mappingVO.setMappingId("2");
         mappingVO.setMappingValue("已提交");
         statuses.add(mappingVO);
         timesheetBatchVO.setStatuses(statuses);
         
        //处理数据权限
         //setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), accessAction, timesheetBatchVO);
         setDataAuth( request, response, timesheetBatchVO );
         
         // 如果没有指定排序则默认按 batchId排序
         if ( timesheetBatchVO.getSortColumn() == null || timesheetBatchVO.getSortColumn().isEmpty() )
         {
            timesheetBatchVO.setSortOrder( "desc" );
            timesheetBatchVO.setSortColumn( "batchId" );
         }

         // 处理subAction
         dealSubAction( timesheetBatchVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( timesheetBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         
         //判断是否全选中
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            timesheetBatchService.getTimesheetBatchVOsByCondition( pagedListHolder, false );
            String selectids = "";

            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object pageObject : pagedListHolder.getSource() )
               {
                  selectids = selectids + ( ( TimesheetBatchVO ) pageObject ).getEncodedId() + ",";
               }
            }
            timesheetBatchVO.setSelectedIds( selectids );

         }
         
         
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetBatchService.getTimesheetBatchVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "timesheetBatchHolder", pagedListHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listTimesheetAllowanceBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listTimesheetAllowanceBatch" );
   }

   /**
    * 更新，直接update正式表里的金额
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_batch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
	   try
	      {
	         // 获取ActionForm
	         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
	         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

	         // 初始化Service接口
	         final TimesheetAllowanceBatchService timesheetAllowanceBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

	         // 获得勾选ID
	         final String batchIds = timesheetBatchVO.getSelectedIds();

	         // 存在勾选ID
	         if ( KANUtil.filterEmpty( batchIds ) != null )
	         {
	            // 分割选择项
	            final String[] selectedIdArray = batchIds.split( "," );

	            int rows = 0;
	            // 遍历selectedIds 以做修改
	            for ( String encodedSelectId : selectedIdArray )
	            {
	               final TimesheetBatchVO submitObject = timesheetAllowanceBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
	               submitObject.reset( null, request );
	               submitObject.setModifyBy( getUserId( request, null ) );
	               rows = rows + timesheetAllowanceBatchService.updateBatch( submitObject );
	            }

	            if ( rows < 0 )
	            {
	               success( request, MESSAGE_TYPE_SUBMIT );
	            }
	            else
	            {
	               success( request, MESSAGE_TYPE_UPDATE );
	            }
	            
	            
	         }

	         return list_object( mapping, form, request, response );
	      }
	      catch ( final Exception e )
	      {
	         throw new KANException( e );
	      }
	   
		
	}
	/**
	 * 退回,物理删除temp表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws KANException
	 */
	public ActionForward back_batch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		 try
	      {
	         // 获取ActionForm
	         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
	         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

	         // 初始化Service接口
	         final TimesheetAllowanceBatchService timesheetAllowanceBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

	         // 获得勾选ID
	         final String batchIds = timesheetBatchVO.getSelectedIds();

	         // 存在勾选ID
	         if ( KANUtil.filterEmpty( batchIds ) != null )
	         {
	            // 分割选择项
	            final String[] selectedIdArray = batchIds.split( "," );

	            int rows = 0;
	            // 遍历selectedIds 以做修改
	            for ( String encodedSelectId : selectedIdArray )
	            {
	               final TimesheetBatchVO submitObject = timesheetAllowanceBatchService.getTimesheetBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
	               submitObject.reset( null, request );
	               submitObject.setModifyBy( getUserId( request, null ) );
	               
	               rows = rows + timesheetAllowanceBatchService.backBatch( submitObject );
	            }

	            if ( rows < 0 )
	            {
	               success( request, MESSAGE_TYPE_SUBMIT );
	            }
	            else
	            {
	               success( request, MESSAGE_TYPE_UPDATE );
	            }
	         }
	         return list_object( mapping, form, request, response );
	      }
	      catch ( final Exception e )
	      {
	         throw new KANException( e );
	      }
	   
	}
   

	@Override
	public ActionForward to_objectNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		return null;
	}
	
	@Override
	public ActionForward add_object(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		return null;
	}
	
	@Override
	public ActionForward to_objectModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		return null;
	}
	
	@Override
	public ActionForward modify_object(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		return null;
	}
	
	@Override
	protected void delete_object(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		
	}
	
	@Override
	protected void delete_objectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws KANException {
		
	}
	
	
}
