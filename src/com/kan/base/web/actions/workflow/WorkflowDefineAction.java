/*
 * Created on 2013-05-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.web.actions.workflow;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class WorkflowDefineAction extends BaseAction
{

   public static String accessAction = "HRO_WORKFLOW_CONFIGURATION";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         // ���Action Form
         final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) form;

         boolean isDelete = workflowDefineVO != null && workflowDefineVO.getSubAction() != null && workflowDefineVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );

         // �����Action��ɾ����Ϣģ��
         if ( isDelete )
         {
            // ����ɾ����Ϣģ���Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( workflowDefineVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder workflowDefineHolder = new PagedListHolder();

         // ���뵱ǰҳ
         workflowDefineHolder.setPage( page );
         // ���뵱ǰֵ����
         workflowDefineHolder.setObject( workflowDefineVO );
         // ����ҳ���¼����
         workflowDefineHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         workflowDefineService.getWorkflowDefineVOsByCondition( workflowDefineHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( workflowDefineHolder, request );

         //Holder��д��Request����
         request.setAttribute( "workflowDefineHolder", workflowDefineHolder );

         // �����ajax���û���ɾ�������򷵻�
         if ( new Boolean( ajax ) || isDelete )
         {
            if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
            {
               return mapping.findForward( "listWorkflowDefineTableInHouse" );
            }
            else
            {
               return mapping.findForward( "listWorkflowDefineTable" );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "listWorkflowDefineInHouse" );
      }
      else
      {
         return mapping.findForward( "listWorkflowDefine" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      WorkflowDefineVO workflowDefineVO = ( ( WorkflowDefineVO ) form );
      // ����Sub Action
      workflowDefineVO.setStatus( WorkflowDefineVO.TRUE );
      workflowDefineVO.setSubAction( CREATE_OBJECT );

      /* // �˻������˷�ͣ�õĹ������Ͳ�����
       KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
       for ( WorkflowModuleDTO workflowModuleDTO : accountConstants.WORKFLOW_MODULE_DTO )
       {
          for ( WorkflowDefineDTO WorkflowDefineDTO : workflowModuleDTO.getWorkflowDefineDTO() )
          {
             // ����״̬�µĹ�����Ҫ��������������˵�
             if ( "1".equals( WorkflowDefineDTO.getWorkflowDefineVO().getStatus() ) )
             {
                for ( int i = 0; i < workflowDefineVO.getWorkflowModules().size(); )
                {
                   MappingVO workflowModul = workflowDefineVO.getWorkflowModules().get( i );
                   if ( workflowModul.getMappingId().equals( WorkflowDefineDTO.getWorkflowDefineVO().getWorkflowModuleId() ) )
                   {
                      workflowDefineVO.getWorkflowModules().remove( i );
                   }
                   else
                   {
                      i++;
                   }
                }
             }
          }
       }*/

      // ��ת���½�����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "manageWorkflowDefineInHouse" );
      }
      else
      {
         return mapping.findForward( "manageWorkflowDefine" );
      }
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
            // ���ActionForm
            final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) form;
            // ����systeId
            workflowDefineVO.setSystemId( KANConstants.SYSTEM_ID );
            // ��ȡ��¼�û�  - ���ô����û�id
            workflowDefineVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            workflowDefineVO.setAccountId( getAccountId( request, response ) );
            workflowDefineVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            workflowDefineService.insertWorkflowDefine( workflowDefineVO );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineVO, Operate.ADD, workflowDefineVO.getDefineId(), null );
         }
         else
         {
            // �ظ��ύ����
            ( ( WorkflowDefineVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���޸Ľ���
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );

         // �����������
         String workflowDefineId = request.getParameter( "defineId" );
         if ( workflowDefineId == null || "".equals( workflowDefineId ) )
         {
            workflowDefineId = ( ( WorkflowDefineVO ) form ).getDefineId();
         }
         else
         {
            workflowDefineId = KANUtil.decodeString( workflowDefineId );
         }

         // ���������Ӧ����
         final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         workflowDefineVO.reset( null, request );
         workflowDefineVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "workflowDefineForm", workflowDefineVO );

         loadColumnVOs_toRequest( request, workflowDefineVO.getWorkflowModuleId() );

         // loadWorkflowDefineStepsForm 
         loadWorkflowDefineStepsForm( request, workflowDefineVO, "workflowDefineStepsForm", "" );

         // loadWorkflowDefineRequirementsForm
         loadWorkflowDefineRequirementsForm( request, workflowDefineVO, "workflowDefineRequirementsForm", "" );

         // ��ʼ��WorkflowDefineStepsVO
         final WorkflowDefineStepsVO workflowDefineStepsVO = new WorkflowDefineStepsVO();
         workflowDefineStepsVO.setDefineId( workflowDefineId );

         // ����WorkflowDefineStepsVO�б�
         new WorkflowDefineStepsAction().list_object( mapping, workflowDefineStepsVO, request, response );

         // ��ʼ��WorkflowDefineRequirementsVO
         final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = new WorkflowDefineRequirementsVO();
         workflowDefineRequirementsVO.setDefineId( workflowDefineId );

         // ����WorkflowDefineRequirementsVO�б�
         new WorkflowDefineRequirementsAction().list_object( mapping, workflowDefineRequirementsVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "manageWorkflowDefineInHouse" );
      }
      else
      {
         return mapping.findForward( "manageWorkflowDefine" );
      }
   }

   // ����WorkflowDefineStepsForm��request
   private void loadWorkflowDefineStepsForm( final HttpServletRequest request, final WorkflowDefineVO workflowDefineVO, final String attributeName, final String subAction )
   {
      final WorkflowDefineStepsVO workflowDefineStepsVO = new WorkflowDefineStepsVO();
      workflowDefineStepsVO.setDefineId( workflowDefineVO.getDefineId() );
      workflowDefineStepsVO.setStatus( WorkflowDefineStepsVO.TRUE );
      workflowDefineStepsVO.setSubAction( subAction );
      // Ĭ��Ϊ����Ϊ1
      workflowDefineStepsVO.setStepIndex( 1 );
      workflowDefineStepsVO.reset( null, request );
      request.setAttribute( attributeName, workflowDefineStepsVO );
   }

   private void loadWorkflowDefineRequirementsForm( final HttpServletRequest request, final WorkflowDefineVO workflowDefineVO, final String attributeName, final String subAction )
         throws KANException
   {
      final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = new WorkflowDefineRequirementsVO();
      workflowDefineRequirementsVO.setDefineId( workflowDefineVO.getDefineId() );
      // Ĭ��״̬1������
      workflowDefineRequirementsVO.setStatus( WorkflowDefineStepsVO.TRUE );
      workflowDefineRequirementsVO.setSubAction( subAction );
      // Ĭ���������1 ����
      workflowDefineRequirementsVO.setCombineType( "1" );
      workflowDefineRequirementsVO.setCompareType( "1" );
      workflowDefineRequirementsVO.reset( null, request );
      request.setAttribute( attributeName, workflowDefineRequirementsVO );
   }

   public static void loadColumnVOs_toRequest( final HttpServletRequest request, final String workflowModuleId ) throws KANException
   {
      // workflowDefineRequirementsVO װ��columns
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, null ) );
      WorkflowModuleDTO workflowModuleDTO = accountConstants.getWorkflowModuleDTOByWorkflowModuleId( workflowModuleId );
      if ( workflowModuleDTO != null )
      {
         AccountModuleDTO accountModuleDTO = accountConstants.getAccountModuleDTOByModuleId( workflowModuleDTO.getWorkflowModuleVO().getModuleId() );
         if ( accountModuleDTO != null )
         {
            // accessAction ������{HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT,HRO_BIZ_EMPLOYEE_LABOR_CONTRACT_IN_HOUSE,HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE} ��ʽ
            String accessAction = accountModuleDTO.getModuleVO().getAccessAction();
            List< String > accessActions = KANUtil.jasonArrayToStringList( accessAction );
            TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessActions, getRole( request, null ) );
            if ( tableDTO != null )
            {
               List< MappingVO > columns = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
               request.setAttribute( "REQUEST_CACHE_COLUMNVOS", columns );
            }
         }
      }
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
            // ��õ�ǰ����
            final String workflowDefineId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "defineId" ), "GBK" ) );
            // ���������Ӧ����
            final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineId );
            // װ�ؽ��洫ֵ
            workflowDefineVO.update( ( WorkflowDefineVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            workflowDefineVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            workflowDefineService.updateWorkflowDefine( workflowDefineVO );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_DEFINE" );

            //���³�ʼ��AccounConstance��WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineVO, Operate.MODIFY, workflowDefineVO.getDefineId(), null );
         }

         // ���Form����
         ( ( WorkflowDefineVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         // ��õ�ǰ����
         final String defineId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "defineId" ), "GBK" ) );
         // ɾ��������Ӧ����
         workflowDefineService.deleteWorkflowDefineByDefineId( getUserId( request, response ), defineId );

         //���³�ʼ��AccounConstance��WorkflowModuleDTO
         constantsInit( "initWorkflow", getAccountId( request, response ) );

         insertlog( request, form, Operate.DELETE, defineId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         // ���Action Form
         WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) form;
         // ����ѡ�е�ID
         if ( workflowDefineVO.getSelectedIds() != null && !workflowDefineVO.getSelectedIds().equals( "" ) )
         {
            workflowDefineService.deleteWorkflowDefineByDefineId( getUserId( request, response ), workflowDefineVO.getSelectedIds().split( "," ) );
            success( request, MESSAGE_TYPE_DELETE );

            insertlog( request, workflowDefineVO, Operate.DELETE, null, workflowDefineVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         workflowDefineVO.setSelectedIds( "" );
         workflowDefineVO.setSubAction( "" );

         //���³�ʼ��AccounConstance��WorkflowModuleDTO
         constantsInit( "initWorkflow", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}