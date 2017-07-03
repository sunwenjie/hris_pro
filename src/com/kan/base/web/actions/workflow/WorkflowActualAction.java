package com.kan.base.web.actions.workflow;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;

public class WorkflowActualAction extends BaseAction
{
   /**
    * List WorkflowActual
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // ���� ��������ѯ
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // ���Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         // ���û������Ĭ�ϰ���createDate������
         if ( KANUtil.filterEmpty( workflowActualVO.getSortColumn() ) == null )
         {
            workflowActualVO.setSortColumn( "createDate" );
            workflowActualVO.setSortOrder( "DESC" );
         }

         // ���ò�ѯ���ְλΪ��
         workflowActualVO.getHistoryVO().setPositionId( null );
         // ���ò�ѯ������Ϊ����
         workflowActualVO.setCreateBy( getUserId( request, response ) );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder workflowActualHolder = new PagedListHolder();

         // ���뵱ǰҳ
         workflowActualHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowActualHolder.setObject( workflowActualVO );

         // ����ҳ���¼����
         workflowActualHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowActualService.getWorkflowActualVOsByCondition( workflowActualHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowActualHolder, request );
         // Holder��д��Request����
         request.setAttribute( "workflowActualHolder", workflowActualHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax��������ת
            return mapping.findForward( "listWorkflowActualTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listWorkflowActual" );
   }

   /**
    * ֪ͨ �������������б�
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Code Reviewed by Siuvan Xia at 2014-7-10
   public ActionForward list_object_unfinished( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ȡ���������״̬
         final String actualStepStatus = request.getParameter( "actualStepStatus" );
         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // ���Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         if ( actualStepStatus != null && !"".equals( actualStepStatus ) )
         {
            workflowActualVO.setActualStepStatus( actualStepStatus );
         }

         // ���û������Ĭ�ϰ���createDate������
         if ( KANUtil.filterEmpty( workflowActualVO.getSortColumn() ) == null )
         {
            workflowActualVO.setSortColumn( "createDate" );
            workflowActualVO.setSortOrder( "DESC" );
         }

         workflowActualVO.setStaffId( getStaffId( request, response ) );
         workflowActualVO.setLogonUserId( getUserId( request, response ) );
         /* if ( getUsername( request, null ).equalsIgnoreCase( "Administrator" ) )
          {

          }else{
             workflowActualVO.setStaffId( getStaffId( request, response ) );
             workflowActualVO.setLogonUserId( getUserId( request, response ) );
          }
         */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder workflowActualHolder = new PagedListHolder();
         // ���뵱ǰҳ
         workflowActualHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowActualHolder.setObject( workflowActualVO );
         // ����ҳ���¼����
         workflowActualHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowActualService.getWorkflowActualVOsByCondition( workflowActualHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowActualHolder, request );

         // Holder��д��Request����
         request.setAttribute( "workflowActualHolder", workflowActualHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, null ) );
            // Ajax��������ת
            return mapping.findForward( "listWorkflowActualUnfinishedTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת��֪ͨ ��������
      return mapping.findForward( "listWorkflowAcutalUnfinished" );
   }

   /**
    * ��ȡδ����Ϣ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Modify by siuvan @2014-10-22 ���ʻ�
   public ActionForward validateRemovePosition_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );

         final String positionIds[] = request.getParameterValues( "positionId" );

         final List< Object > notFinishWorkflowActual = workflowActualService.getNotFinishWorkflowActualVOsByPositionIds( positionIds );
         final StringBuilder sb = new StringBuilder();
         sb.append( "" );
         if ( notFinishWorkflowActual != null && notFinishWorkflowActual.size() > 0 )
         {
            for ( Object obj : notFinishWorkflowActual )
            {
               if ( sb.toString().length() <= 200 )
               {
                  WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) obj;
                  workflowActualVO.reset( mapping, request );
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
                  {
                     sb.append( "���� " + workflowActualVO.getDecodeActualStepStatus() + "�Ĺ�����[" + workflowActualVO.getNameZH() + "]��Ҫ��ˣ�\n" );
                  }
                  else
                  {
                     sb.append( "Exists workflow [" + workflowActualVO.getNameEN() + "] need to audit!\n" );
                  }
               }
            }

            if ( sb.toString().length() >= 200 )
            {
               sb.append( "....\n" );
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
               {
                  sb.append( "�ܹ�" + notFinishWorkflowActual.size() + "����������\n" );
                  sb.append( "��" + ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) ? "Ա��" : "��Ա" ) + "ɾ���󽫲���������ȷ��ɾ����" );
               }
               else
               {
                  sb.append( "Total " + notFinishWorkflowActual.size() + " audit tasks!\n" );
                  sb.append( "The employee will not be deleted after audit, to confirm the deletion?" );
               }
            }
         }

         JSONObject jsonObject = new JSONObject();
         jsonObject.put( "size", notFinishWorkflowActual.size() );
         jsonObject.put( "message", sb.toString() );

         out.print( jsonObject.toString() );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ��ȡδ����Ϣ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Code Reviewed by Siuvan Xia at 2014-7-28
   public ActionForward get_notReadCount( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final UserService userService = ( UserService ) getService( "userService" );
         final AccountService accountService = ( AccountService ) getService( "accountService" );

         // ��ʼ��jsonArray
         final JSONArray jsonArray = new JSONArray();

         // ���Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;
         workflowActualVO.setActualStepStatus( "2" );
         workflowActualVO.setStaffId( getStaffId( request, null ) );
         workflowActualVO.setLogonUserId( getUserId( request, null ) );

         // ͳ�Ƶ�ǰuserId��������
         int count = 0;
         count = workflowActualService.countWorkflowActualVOsByCondition( workflowActualVO );

         // ��ʼ��AccountVO
         AccountVO accountVO = null;
         accountVO = accountService.getAccountVOByAccountId( getAccountId( request, null ) );

         JSONObject jsonObject = null;
         jsonObject = new JSONObject();
         jsonObject.put( "accountId", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? accountVO.getNameCN() : accountVO.getNameEN() );
         jsonObject.put( "count", count );
         jsonArray.add( jsonObject );

         // ��ȡ��ǰUserVO
         final StaffDTO staffDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffDTOByUserId( getUserId( request, response ) );
         UserVO userVO = null;
         userVO = staffDTO != null && staffDTO.getUserVO() != null ? staffDTO.getUserVO() : null;
         // �Ƿ���ڶ���˺�
         if ( userVO != null && KANUtil.filterEmpty( userVO.getUserIds() ) != null )
         {
            final List< String > userIdList = KANUtil.jasonArrayToStringList( userVO.getUserIds() );

            // ����userId
            if ( userIdList != null && userIdList.size() > 0 )
            {
               for ( String userId : userIdList )
               {
                  // ��ȡtempUserVO
                  final UserVO tempUserVO = userService.getUserVOByUserId( userId );
                  if ( tempUserVO != null )
                  {
                     workflowActualVO.setAccountId( tempUserVO.getAccountId() );
                     workflowActualVO.setLogonUserId( tempUserVO.getUserId() );

                     count = workflowActualService.countWorkflowActualVOsByCondition( workflowActualVO );
                     accountVO = accountService.getAccountVOByAccountId( tempUserVO.getAccountId() );
                     if ( accountVO != null )
                     {
                        jsonObject = new JSONObject();
                        jsonObject.put( "accountId", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? accountVO.getNameCN() : accountVO.getNameEN() );
                        jsonObject.put( "count", count );
                        jsonArray.add( jsonObject );
                     }
                  }
               }
            }
         }

         out.print( jsonArray.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * To workflowActual modify page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // ��õ�ǰ����
         String workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ) );
         // ���������Ӧ����
         WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowActualVO.reset( null, request );

         workflowActualVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "workflowActualForm", workflowActualVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageWorkflowActual" );
   }

   /**
    * To workflowActual new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      // ��ʼ��Service�ӿ�
      // ����Sub Action
      ( ( WorkflowActualVO ) form ).setStatus( WorkflowActualVO.TRUE );
      ( ( WorkflowActualVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageWorkflowActual" );
   }

   /**
    * Add workflowActual
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
            // ���ActionForm
            final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;
            // ����systeId
            workflowActualVO.setSystemId( KANConstants.SYSTEM_ID );
            // ��ȡ��¼�û�  - ���ô����û�id
            workflowActualVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            workflowActualVO.setAccountId( getAccountId( request, response ) );
            workflowActualVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            workflowActualService.insertWorkflowActual( workflowActualVO );

         }

         // ���Form����
         ( ( WorkflowActualVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify workflowActual
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
            // ��õ�ǰ����
            final String workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "UTF-8" ) );
            // ���������Ӧ����
            final WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );
            // װ�ؽ��洫ֵ
            workflowActualVO.update( ( WorkflowActualVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowActualVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            workflowActualService.updateWorkflowActual( workflowActualVO );
            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form����
         ( ( WorkflowActualVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete workflowActual
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         final WorkflowActualVO workflowActualVO = new WorkflowActualVO();
         // ��õ�ǰ����
         final String workflowId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "workflowId" ), "GBK" ) );

         // ɾ��������Ӧ����
         workflowActualVO.setWorkflowId( workflowId );
         workflowActualVO.setModifyBy( getUserId( request, response ) );
         workflowActualService.deleteWorkflowActual( workflowActualVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete workflowActual list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // ���Action Form
         WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;
         // ����ѡ�е�ID
         if ( workflowActualVO.getSelectedIds() != null && !workflowActualVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : workflowActualVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               workflowActualVO.setWorkflowId( selectedId );
               workflowActualVO.setModifyBy( getUserId( request, response ) );
               workflowActualService.deleteWorkflowActual( workflowActualVO );
            }
         }
         // ���Selected IDs����Action
         workflowActualVO.setSelectedIds( "" );
         workflowActualVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ѡ���ǲ鿴��ټӰࡣ���ǲ鿴��������
   public ActionForward chooseNotice( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      request.setAttribute( "message_count", request.getParameter( "message_count" ) );
      return mapping.findForward( "chooseNotice" );
   }

   // ��ȡ
   public ActionForward list_object_unfinished_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         final String actualStepStatus = request.getParameter( "actualStepStatus" );

         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // ���Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         if ( KANUtil.filterEmpty( actualStepStatus ) != null )
         {
            workflowActualVO.setActualStepStatus( actualStepStatus );
         }

         workflowActualVO.setCorpId( getCorpId( request, response ) );

         // ����accountID
         workflowActualVO.setAccountId( getAccountId( request, response ) );
         workflowActualVO.setCurrentPositionId( getPositionId( request, null ) );
         workflowActualVO.setStaffId( getStaffId( request, response ) );
         workflowActualVO.setLogonUserId( getUserId( request, response ) );

         // ֻ�鿴��ٺͼӰ�ġ����501 �Ӱ�502
         workflowActualVO.setSystemModuleId( "501,502" );
         workflowActualVO.setSortColumn( "createDate" );
         workflowActualVO.setSortOrder( "desc" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder workflowActualHolder = new PagedListHolder();

         // ���뵱ǰҳ
         workflowActualHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowActualHolder.setObject( workflowActualVO );

         // ����ҳ���¼����
         workflowActualHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowActualService.getWorkflowActualVOsByCondition( workflowActualHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowActualHolder, request );

         if ( new Boolean( getAjax( request ) ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();
            final JSONArray workflowActualJsonArray = JSONArray.fromObject( workflowActualHolder.getSource() );
            final JSONObject workflowActualJsonObject = new JSONObject();
            workflowActualJsonObject.put( "workflowActualJsonArray", workflowActualJsonArray );
            workflowActualJsonObject.put( "page", workflowActualHolder.getPage() );
            workflowActualJsonObject.put( "realPage", workflowActualHolder.getRealPage() );
            workflowActualJsonObject.put( "pageCount", workflowActualHolder.getPageCount() );
            workflowActualJsonObject.put( "nextPage", workflowActualHolder.getNextPage() );
            workflowActualJsonObject.put( "pageSize", workflowActualHolder.getPageSize() );
            out.print( workflowActualJsonObject.toString() );
            out.flush();
            out.close();
            return null;
         }

         // Holder��д��Request����
         request.setAttribute( "workflowActualHolder", workflowActualHolder );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      // ��ת��֪ͨ ��������
      return mapping.findForward( "listTask" );
   }

   public ActionForward get_notReadCount_mobile( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );
         // ���Action Form
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         //workflowActualVO.setActualStepStatus( "2" );

         // ����accountID
         workflowActualVO.setAccountId( getAccountId( request, response ) );
         workflowActualVO.setStaffId( getStaffId( request, response ) );
         workflowActualVO.setLogonUserId( getUserId( request, response ) );

         // ֻ�鿴��ٺͼӰ�Ĺ�����
         workflowActualVO.setSystemModuleId( "501,502" );
         final int count = workflowActualService.countWorkflowActualVOsByCondition( workflowActualVO );
         // �鿴�Լ��½�����ٺͼӰ�
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final OTHeaderService otHeaderService = ( OTHeaderService ) getService( "otHeaderService" );
         final LeaveHeaderVO condLeaveHeaderVO = new LeaveHeaderVO();
         condLeaveHeaderVO.setAccountId( getAccountId( request, response ) );
         condLeaveHeaderVO.setCreateBy( getUserId( request, response ) );
         final OTHeaderVO condOTHeaderVO = new OTHeaderVO();
         condOTHeaderVO.setAccountId( getAccountId( request, response ) );
         condOTHeaderVO.setCreateBy( getUserId( request, response ) );
         final int result = leaveHeaderService.count_leaveUnread( condLeaveHeaderVO ) + otHeaderService.count_OTUnread( condOTHeaderVO );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         out.print( count + "##" + result );
         out.flush();
         out.close();
      }
      catch ( IOException e )
      {

         e.printStackTrace();
      }

      return null;
   }

   // ����������ͬ�⡢��ͬ�⣩
   // Add by siuvan.xia @ 2014-07-10
   public ActionForward submit_objects( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡActionForm
         final WorkflowActualVO workflowActualVO = ( WorkflowActualVO ) form;

         // ��ʼ��Service�ӿ�
         final WorkflowActualStepsService workflowActualStepsService = ( WorkflowActualStepsService ) getService( "workflowActualStepsService" );

         // ��ù�ѡID
         final String workflowIds = workflowActualVO.getSelectedIds();

         // ��ȡ������
         final String stepStatus = request.getParameter( "stepStatus" );

         // ajax�����
         decodedObject( workflowActualVO );

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( workflowIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = workflowIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String selectId : selectedIdArray )
            {
               // ���WorkflowActualStepsVO�б�
               final List< Object > workflowActualStepsVOs = workflowActualStepsService.getWorkflowActualStepsVOsByWorkflowId( KANUtil.decodeStringFromAjax( selectId ) );

               // ��ʼ��WorkflowActualStepsVO
               WorkflowActualStepsVO submitObject = null;

               // �����ҵ���Ӧ��WorkflowActualStepsVO
               if ( workflowActualStepsVOs != null && workflowActualStepsVOs.size() > 0 )
               {
                  for ( Object workflowActualStepsVOObject : workflowActualStepsVOs )
                  {
                     final WorkflowActualStepsVO tempWorkflowActualStepsVO = ( WorkflowActualStepsVO ) workflowActualStepsVOObject;

                     if ( ( "1".equals( tempWorkflowActualStepsVO.getAuditType() ) && KANUtil.filterEmpty( tempWorkflowActualStepsVO.getAuditTargetId() ) != null && tempWorkflowActualStepsVO.getAuditTargetId().equals( getPositionId( request, null ) ) )
                           || ( "4".equals( tempWorkflowActualStepsVO.getAuditType() ) && ( ( KANUtil.filterEmpty( getStaffId( request, null ) ) != null && getStaffId( request, null ).equals( tempWorkflowActualStepsVO.getAuditTargetId() ) ) || ( KANUtil.filterEmpty( getUserId( request, null ) ) != null && getUserId( request, null ).equals( tempWorkflowActualStepsVO.getAuditTargetId() ) ) ) ) )
                     {
                        // ����Ϊ"������"״̬������������������Դ��ķ����ظ��ύ���ݣ�
                        if ( "2".equalsIgnoreCase( tempWorkflowActualStepsVO.getStatus() ) )
                        {
                           submitObject = tempWorkflowActualStepsVO;
                           submitObject.setStatus( stepStatus );

                           // �ܾ���ȡ�ܾ�ԭ��
                           if ( "4".equals( KANUtil.filterEmpty( stepStatus ) ) )
                           {
                              submitObject.setDescription( workflowActualVO.getDescription() );
                           }
                           break;
                        }
                     }
                  }
               }

               // ����Ϊ"������"״̬������������������Դ��ķ����ظ��ύ���ݣ�
               if ( submitObject != null )
               {
                  submitObject.setModifyBy( getUserId( request, response ) );
                  submitObject.setModifyDate( new Date() );
                  submitObject.reset( mapping, request );
                  rows = rows + workflowActualStepsService.updateWorkflowActualStepsVO( submitObject );
               }
            }
            success( request, null, "�����ɹ������ܹ�" + ( stepStatus.equals( "3" ) ? "ͬ��" : "�ܾ�" ) + rows + "����¼��" );

            insertlog( request, workflowActualVO, stepStatus.equals( "3" ) ? Operate.APPROVE : Operate.REJECT, null, ( stepStatus.equals( "3" ) ? "ͬ��" : "�ܾ�" ) + " workflow���� "
                  + KANUtil.decodeSelectedIds( workflowIds ) );
         }

         // ���Selected IDs����Action
         ( ( WorkflowActualVO ) form ).setSelectedIds( "" );
         ( ( WorkflowActualVO ) form ).setSubAction( SEARCH_OBJECT );
         ( ( WorkflowActualVO ) form ).setActualStepStatus( "2" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_unfinished( mapping, form, request, response );
   }

}
