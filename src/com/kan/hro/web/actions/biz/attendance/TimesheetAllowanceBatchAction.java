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

   // ��ǰAction��Ӧ��Access Action - In House
   public static String accessAction = "HRO_BIZ_ATTENDANCE_TIMESHEET_ALLOWANCE";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final TimesheetAllowanceBatchService timesheetBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

         // ���Action Form
         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
         List< MappingVO > statuses = new ArrayList<MappingVO>();
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingTemp("");
         mappingVO.setMappingId("");
         mappingVO.setMappingValue("��ѡ��");
         statuses.add(mappingVO);
         mappingVO = new MappingVO();
         mappingVO.setMappingTemp("1");
         mappingVO.setMappingId("1");
         mappingVO.setMappingValue("�½�");
         statuses.add(mappingVO);
         mappingVO = new MappingVO();
         mappingVO.setMappingTemp("2");
         mappingVO.setMappingId("2");
         mappingVO.setMappingValue("���ύ");
         statuses.add(mappingVO);
         timesheetBatchVO.setStatuses(statuses);
         
        //��������Ȩ��
         //setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), accessAction, timesheetBatchVO);
         setDataAuth( request, response, timesheetBatchVO );
         
         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( timesheetBatchVO.getSortColumn() == null || timesheetBatchVO.getSortColumn().isEmpty() )
         {
            timesheetBatchVO.setSortOrder( "desc" );
            timesheetBatchVO.setSortColumn( "batchId" );
         }

         // ����subAction
         dealSubAction( timesheetBatchVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( timesheetBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         
         //�ж��Ƿ�ȫѡ��
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
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
         
         
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetBatchService.getTimesheetBatchVOsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "timesheetBatchHolder", pagedListHolder );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listTimesheetAllowanceBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listTimesheetAllowanceBatch" );
   }

   /**
    * ���£�ֱ��update��ʽ����Ľ��
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
	         // ��ȡActionForm
	         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
	         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

	         // ��ʼ��Service�ӿ�
	         final TimesheetAllowanceBatchService timesheetAllowanceBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

	         // ��ù�ѡID
	         final String batchIds = timesheetBatchVO.getSelectedIds();

	         // ���ڹ�ѡID
	         if ( KANUtil.filterEmpty( batchIds ) != null )
	         {
	            // �ָ�ѡ����
	            final String[] selectedIdArray = batchIds.split( "," );

	            int rows = 0;
	            // ����selectedIds �����޸�
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
	 * �˻�,����ɾ��temp��
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
	         // ��ȡActionForm
	         final TimesheetBatchVO timesheetBatchVO = ( TimesheetBatchVO ) form;
	         timesheetBatchVO.setModifyBy( getUserId( request, response ) );

	         // ��ʼ��Service�ӿ�
	         final TimesheetAllowanceBatchService timesheetAllowanceBatchService = ( TimesheetAllowanceBatchService ) getService( "timesheetAllowanceBatchService" );

	         // ��ù�ѡID
	         final String batchIds = timesheetBatchVO.getSelectedIds();

	         // ���ڹ�ѡID
	         if ( KANUtil.filterEmpty( batchIds ) != null )
	         {
	            // �ָ�ѡ����
	            final String[] selectedIdArray = batchIds.split( "," );

	            int rows = 0;
	            // ����selectedIds �����޸�
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
