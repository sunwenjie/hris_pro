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

   // ��ǰAction��Ӧ��Access Action
   public static String accessAction = "HRO_BIZ_ATTENDANCE_OT_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ȡ����ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��ʼ��Service�ӿ�
         final OTImportHeaderService timesheetHeaderService = ( OTImportHeaderService ) getService( "otImportHeaderService" );
         final OTImportBatchService timesheetBatchService = ( OTImportBatchService ) getService( "otImportBatchService" );

         // ��ȡTimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = timesheetBatchService.getTimesheetBatchVOByBatchId( batchId );
         // д��request
         request.setAttribute( "timesheetBatchForm", timesheetBatchVO );

         // ���Action Form
         final OTImportHeaderVO otImportHeaderVO = ( OTImportHeaderVO ) form;
         
         //��������Ȩ��
         //setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), OTImportBatchAction.accessAction,otImportHeaderVO);
         setDataAuth( request, response, otImportHeaderVO );
         otImportHeaderVO.setBatchId(batchId);

         // ���û��ָ��������Ĭ�ϰ� otheaderId����
         if ( otImportHeaderVO.getSortColumn() == null || otImportHeaderVO.getSortColumn().isEmpty() )
         {
            otImportHeaderVO.setSortOrder( "desc" );
            otImportHeaderVO.setSortColumn( "a.otheaderId" );
         }

         // �����inHouse
//         if ( isInHouseRole( request, response ) )
//         {
//            // �����HR����
//            if ( !isHRFunction( request, response ) )
//            {
//               // ֻ�ܲ鿴�Լ��Ŀ��ڱ�
//               otImportHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
//            }
//         }

         // �趨�鿴����Ȩ��
         setHRFunctionRole( mapping, form, request, response );

         // ����subAction
         dealSubAction( otImportHeaderVO, mapping, form, request, response );

         otImportHeaderVO.setBatchId( batchId );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( otImportHeaderVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
      
         if ( "1".equals( request.getParameter( "selected" ) ) )
         {
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
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
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetHeaderService.getOTImportHeaderVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         

         
         // Holder��д��Request����
         request.setAttribute( "otImportHeaderHolder", pagedListHolder );
         //����״̬
         request.setAttribute("batchStatus", request.getParameter("batchStatus"));
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            // �趨�鿴����Ȩ��
            setHRFunctionRole( mapping, form, request, response );
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "otImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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
