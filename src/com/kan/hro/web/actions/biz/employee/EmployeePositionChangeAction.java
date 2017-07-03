package com.kan.hro.web.actions.biz.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class EmployeePositionChangeAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_POSITION_CHANGES";

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
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         // ���Action Form
         final EmployeePositionChangeVO positionChangeVO = ( EmployeePositionChangeVO ) form;

         // �����Action��ɾ���û��б�
         if ( positionChangeVO.getSubAction() != null && positionChangeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( positionChangeVO );
         }

         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( positionChangeVO.getSortColumn() == null || positionChangeVO.getSortColumn().isEmpty() )
         {
            positionChangeVO.setSortColumn( "positionChangeId" );
            positionChangeVO.setSortOrder( "desc" );
         }

         setDataAuth( request, response, positionChangeVO );

         // ���SubAction
         final String subAction = getSubAction( form );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder positionChangeHolder = new PagedListHolder();

         // ���뵱ǰҳ
         positionChangeHolder.setPage( page );
         // ���뵱ǰֵ����
         positionChangeHolder.setObject( positionChangeVO );
         // ����ҳ���¼����
         positionChangeHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         positionChangeService.getPositionChangeVOsByCondition( positionChangeHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( positionChangeHolder, request );
         // Holder��д��Request����
         request.setAttribute( "positionChangeHolder", positionChangeHolder );
         request.setAttribute( "isPaged", "1" );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               // ���������excel���ֶ���
               return new DownloadFileAction().exportExcel4EmployeePositionChange( mapping, form, request, response );
            }
            else
            {
               // Ajax��������ת
               return mapping.findForward( "listPositionChangeTable" );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listPositionChange" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      request.setAttribute( "employeePositionList", new ArrayList< PositionVO >() );
      // ����Sub Action
      ( ( EmployeePositionChangeVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "managePositionChange" );
   }

   // �����춯
   public ActionForward to_objectQuick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      // ����Sub Action
      ( ( EmployeePositionChangeVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( EmployeePositionChangeVO ) form ).setSubmitFlag( 3 );
      ( ( EmployeePositionChangeVO ) form ).setStatus( "1" );
      // ��ת���½�����
      return mapping.findForward( "quickPositionChange" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            String accountId = getAccountId( request, response );
            KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // ���ActionForm
            final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
            // ��ȡ��¼�û�
            employeePositionChangeVO.setAccountId( getAccountId( request, response ) );
            employeePositionChangeVO.setCreateBy( getUserId( request, response ) );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );
            employeePositionChangeVO.setIsImmediatelyEffective( StringUtils.isNotEmpty( employeePositionChangeVO.getIsImmediatelyEffective() ) ? "1" : "0" );

            List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeePositionChangeVO.getEmployeeId() );
            if ( staffList != null && staffList.size() > 0 )
            {
               StaffDTO staffDTO = staffList.get( 0 );
               if ( staffDTO != null )
               {
                  StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     employeePositionChangeVO.setStaffId( staffVO.getStaffId() );
                  }
               }
            }

            // �½�����
            positionChangeService.insertEmployeePositionChange( employeePositionChangeVO );

            final EmployeePositionChangeVO tempEmployeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( employeePositionChangeVO.getPositionChangeId() );
            tempEmployeePositionChangeVO.reset( null, request );
            if ( employeePositionChangeVO.getSubmitFlag() == 1 )
            {
               tempEmployeePositionChangeVO.setStatus( "3" );
               tempEmployeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( tempEmployeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( tempEmployeePositionChangeVO );

               // ������ӳɹ����
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, tempEmployeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // ������ӳɹ����
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, tempEmployeePositionChangeVO, Operate.ADD, employeePositionChangeVO.getPositionChangeId(), null );
            }
         }
         else
         {
            // ���Form
            ( ( EmployeePositionChangeVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward add_objectQuick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // ���ActionForm
            final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
            // ��ȡ��¼�û�
            employeePositionChangeVO.setAccountId( getAccountId( request, response ) );
            employeePositionChangeVO.setCreateBy( getUserId( request, response ) );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );
            employeePositionChangeVO.setIsImmediatelyEffective( StringUtils.isNotEmpty( employeePositionChangeVO.getIsImmediatelyEffective() ) ? "1" : "0" );

            // �½�����
            positionChangeService.insertEmployeePositionChange( employeePositionChangeVO );

            final EmployeePositionChangeVO tempEmployeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( employeePositionChangeVO.getPositionChangeId() );
            tempEmployeePositionChangeVO.reset( null, request );
            if ( employeePositionChangeVO.getSubmitFlag() == 4 )
            {
               tempEmployeePositionChangeVO.setStatus( "3" );
               tempEmployeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( tempEmployeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( tempEmployeePositionChangeVO );

               // ������ӳɹ����
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, tempEmployeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // ������ӳɹ����
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, tempEmployeePositionChangeVO, Operate.ADD, employeePositionChangeVO.getPositionChangeId(), null );
            }
         }
         else
         {
            // ���Form
            ( ( EmployeePositionChangeVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // ������ȡ�����
         String positionChangeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( positionChangeId ) == null )
         {
            positionChangeId = ( ( EmployeePositionChangeVO ) form ).getPositionChangeId();
         }

         final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeePositionChangeVO.getEmployeeId() );
         final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( employeePositionChangeVO.getOldPositionId() );
         final BranchDTO branchDTO = accountConstants.getBranchDTOByBranchId( employeePositionChangeVO.getOldBranchId() );
         BranchVO branchVO = new BranchVO();
         PositionVO positionVO = new PositionVO();
         if ( branchDTO != null )
         {
            branchVO = branchDTO.getBranchVO();
            branchVO.reset( null, request );
         }
         if ( positionDTO != null )
         {
            positionVO = positionDTO.getPositionVO();
            positionVO.reset( null, request );
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeePositionChangeVO.reset( null, request );

         employeePositionChangeVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "branchVO", branchVO );
         request.setAttribute( "positionVO", positionVO );
         request.setAttribute( "employeeVO", employeeVO );

         if ( employeePositionChangeVO.getSubmitFlag() == 3 || employeePositionChangeVO.getSubmitFlag() == 4 )
         {
            String oldParentPositionOwnersName = employeePositionChangeVO.getOldParentPositionNameZH() + " / " + employeePositionChangeVO.getOldParentPositionNameEN() + " - "
                  + employeePositionChangeVO.getOldParentPositionOwnersName();
            employeePositionChangeVO.setOldParentPositionOwners( oldParentPositionOwnersName );
            request.setAttribute( "employeePositionChangeForm", employeePositionChangeVO );
            return mapping.findForward( "quickPositionChange" );
         }

         request.setAttribute( "employeePositionChangeForm", employeePositionChangeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "managePositionChange" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // ������ȡ�����
            final String positionChangeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���EmployeePositionChangeVO
            final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
            // ��ȡ��¼�û�
            employeePositionChangeVO.update( ( EmployeePositionChangeVO ) form );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );

            final List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeePositionChangeVO.getEmployeeId() );
            if ( staffList != null && staffList.size() > 0 )
            {
               if ( staffList != null && staffList.size() > 0 )
               {
                  StaffDTO staffDTO = staffList.get( 0 );
                  if ( staffDTO != null )
                  {
                     StaffVO staffVO = staffDTO.getStaffVO();
                     if ( staffVO != null )
                     {
                        employeePositionChangeVO.setStaffId( staffVO.getStaffId() );
                     }
                  }
               }
            }
            // �޸Ķ���
            positionChangeService.updateEmployeePositionChange( employeePositionChangeVO );

            if ( employeePositionChangeVO.getSubmitFlag() == 1 )
            {
               employeePositionChangeVO.setStatus( "3" );
               employeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( employeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( employeePositionChangeVO );

               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeePositionChangeVO, Operate.MODIFY, employeePositionChangeVO.getPositionChangeId(), null );
            }

         }

         // ���Form����
         ( ( EmployeePositionChangeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward modify_objectQuick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // ������ȡ�����
            final String positionChangeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���EmployeePositionChangeVO
            final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
            // ��ȡ��¼�û�
            employeePositionChangeVO.update( ( EmployeePositionChangeVO ) form );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );

            // �޸Ķ���
            positionChangeService.updateEmployeePositionChange( employeePositionChangeVO );

            if ( employeePositionChangeVO.getSubmitFlag() == 4 )
            {
               employeePositionChangeVO.setStatus( "3" );
               employeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( employeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( employeePositionChangeVO );

               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeePositionChangeVO, Operate.MODIFY, employeePositionChangeVO.getPositionChangeId(), null );
            }
         }

         // ���Form����
         ( ( EmployeePositionChangeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         final EmployeePositionChangeVO employeePositionChangeVO = new EmployeePositionChangeVO();
         // ��õ�ǰ����
         String positionChangeId = request.getParameter( "positionChangeId" );

         // ɾ��������Ӧ����
         employeePositionChangeVO.setPositionChangeId( positionChangeId );
         employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
         // ɾ������
         positionChangeService.deleteEmployeePositionChange( employeePositionChangeVO );
         insertlog( request, employeePositionChangeVO, Operate.DELETE, positionChangeId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         // ���Action Form
         EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
         // ����ѡ�е�ID
         if ( employeePositionChangeVO.getSelectedIds() != null && !employeePositionChangeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeePositionChangeVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeePositionChangeVO.setPositionChangeId( selectedId );
               employeePositionChangeVO.setModifyBy( getUserId( request, response ) );

               positionChangeService.deleteEmployeePositionChange( employeePositionChangeVO );
            }

            insertlog( request, employeePositionChangeVO, Operate.DELETE, null, employeePositionChangeVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         employeePositionChangeVO.setSelectedIds( "" );
         employeePositionChangeVO.setSubAction( "" );
         employeePositionChangeVO.setPositionChangeId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward submit_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         // ���Action Form
         EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
         // ����ѡ�е�ID
         if ( employeePositionChangeVO.getSelectedIds() != null && !employeePositionChangeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeePositionChangeVO.getSelectedIds().split( "," ) )
            {
               EmployeePositionChangeVO employeePositionChangeVODB = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( selectedId );
               if ( employeePositionChangeVODB.getWorkflowId() != null )
            	   continue;
               
               employeePositionChangeVODB.setStatus( "3" );
               employeePositionChangeVODB.setModifyBy( getUserId( request, response ) );
               employeePositionChangeVODB.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( employeePositionChangeVODB );
               employeePositionChangeVODB.setLocale( request.getLocale() );
               positionChangeService.submitEmployeePositionChange( employeePositionChangeVODB );
            }

            success( request, MESSAGE_TYPE_SUBMIT );

            insertlog( request, employeePositionChangeVO, Operate.BATCH_SUBMIT, employeePositionChangeVO.getSelectedIds(), null );
         }
         // ���Selected IDs����Action
         employeePositionChangeVO.setSelectedIds( "" );
         employeePositionChangeVO.setSubAction( "" );
         employeePositionChangeVO.setSubmitFlag( 0 );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward synchronized_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         positionChangeService.synchronizedEmployeePosition();
         success( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.synchronized.success" ) );
         insertlog( request, form, Operate.DELETE, null, "synchronized_objects" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public void getPositionsByEmployeeId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         String employeeId = request.getParameter( "employeeId" );
         String oldPositionId = request.getParameter( "oldPositionId" );
         boolean has = false;
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         if ( StringUtils.isNotEmpty( employeeId ) )
         {
            final Map< String, String > emptyMap = new HashMap< String, String >();
            emptyMap.put( "id", "0" );
            emptyMap.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "��ѡ��" : "Please Select" );
            listReturn.add( emptyMap );

            List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeeId );
            if ( staffList != null && staffList.size() > 0 )
            {
               StaffDTO staffDTO = staffList.get( 0 );
               if ( staffDTO != null )
               {
                  StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     List< PositionDTO > positionList = accountConstants.getPositionDTOsByStaffId( staffVO.getStaffId() );
                     for ( PositionDTO positionDTO : positionList )
                     {
                        PositionVO positionVO = positionDTO.getPositionVO();
                        if ( positionVO != null )
                        {
                           Map< String, String > map = new HashMap< String, String >();
                           map.put( "id", positionVO.getPositionId() );
                           map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );
                           listReturn.add( map );

                           if ( StringUtils.isNotEmpty( oldPositionId ) && positionVO.getPositionId().equals( oldPositionId ) )
                           {
                              has = true;
                           }
                        }
                     }
                  }
               }
            }

            if ( StringUtils.isNotEmpty( oldPositionId ) && !has )
            {
               PositionVO positionVO = accountConstants.getPositionVOByPositionId( oldPositionId );
               Map< String, String > map = new HashMap< String, String >();
               map.put( "id", positionVO.getPositionId() );
               map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );
               listReturn.add( map );
            }
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getBranchInfoByPositionId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         String employeeId = request.getParameter( "employeeId" );
         String positionId = request.getParameter( "positionId" );
         Map< String, String > mapReturn = new HashMap< String, String >();
         if ( StringUtils.isNotEmpty( positionId ) )
         {
            //��ȡְλ��Ϣ
            PositionVO positionVO = accountConstants.getPositionVOByPositionId( positionId );
            if ( positionVO != null )
            {
               PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( positionVO.getParentPositionId() );
               if ( parentPositionVO != null )
               {
                  mapReturn.put( "parentPositionName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? parentPositionVO.getTitleZH() : parentPositionVO.getTitleEN() );
               }
               mapReturn.put( "positionId", positionVO.getPositionId() );
               mapReturn.put( "positionName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );
               mapReturn.put( "parentPositionOwnersName", accountConstants.getStaffNamesByPositionId( request.getLocale().getLanguage(), positionVO.getParentPositionId() ) );

               //��ȡ������Ϣ
               BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {
                  BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( branchVO.getParentBranchId() );
                  if ( parentBranchVO != null )
                  {
                     mapReturn.put( "parentBranchName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? parentBranchVO.getNameZH() : parentBranchVO.getNameEN() );
                  }
                  mapReturn.put( "branchId", branchVO.getBranchId() );
                  mapReturn.put( "branchName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? branchVO.getNameZH() : branchVO.getNameEN() );
               }

               //��ȡְ����Ϣ
               PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );
               if ( positionGradeVO != null )
               {
                  mapReturn.put( "positionGradeName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionGradeVO.getGradeNameZH()
                        : positionGradeVO.getGradeNameEN() );
               }

               //��ȡԱ��ְ����ϵ��Ϣ
               String staffPositionRelationId = "";
               if ( StringUtils.isNotEmpty( employeeId ) )
               {
                  String staffId = "";
                  PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionId );
                  List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeeId );
                  if ( staffList != null && staffList.size() > 0 )
                  {
                     if ( staffList != null && staffList.size() > 0 )
                     {
                        StaffDTO staffDTO = staffList.get( 0 );
                        if ( staffDTO != null )
                        {
                           StaffVO staffVO = staffDTO.getStaffVO();
                           if ( staffVO != null )
                           {
                              staffId = staffVO.getStaffId();
                           }
                        }
                     }
                  }
                  if ( positionDTO != null )
                  {
                     List< PositionStaffRelationVO > positionStaffRelationList = positionDTO.getPositionStaffRelationVOs();
                     for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationList )
                     {
                        if ( StringUtils.equals( staffId, positionStaffRelationVO.getStaffId() ) )
                        {
                           staffPositionRelationId = positionStaffRelationVO.getRelationId();
                        }
                     }
                  }
               }
               mapReturn.put( "staffPositionRelationId", staffPositionRelationId );
            }
         }
         JSONObject json = JSONObject.fromObject( mapReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getEmployeeInfoByEmployeeId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String employeeId = request.getParameter( "employeeId" );
         Map< String, String > mapReturn = new HashMap< String, String >();
         //��ȡԱ����Ϣ
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         if ( employeeVO != null )
         {
            mapReturn.put( "employeeId", employeeVO.getEmployeeId() );
            mapReturn.put( "employeeNo", employeeVO.getEmployeeNo() );
            mapReturn.put( "employeeName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? employeeVO.getNameZH() : employeeVO.getNameEN() );
            mapReturn.put( "employeeNameZH", employeeVO.getNameZH() );
            mapReturn.put( "employeeNameEN", employeeVO.getNameEN() );
            mapReturn.put( "employeeCertificateNumber", employeeVO.getCertificateNumber() );
         }

         JSONObject json = JSONObject.fromObject( mapReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getBranchForPage( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         String corpId = getCorpId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         final Map< String, String > emptyMap = new HashMap< String, String >();
         emptyMap.put( "id", "0" );
         emptyMap.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "��ѡ��" : "Please Select" );
         listReturn.add( emptyMap );

         List< MappingVO > mappingVOList = accountConstants.getBranchs( request.getLocale().getLanguage(), corpId );
         for ( MappingVO mappingVO : mappingVOList )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", mappingVO.getMappingId() );
            map.put( "name", StringUtils.replace( mappingVO.getMappingValue(), "&nbsp;", "    " ) );
            listReturn.add( map );
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getPositionIdByBranchId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         String corpId = getCorpId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         String branchId = request.getParameter( "branchId" );
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         List< PositionVO > positionList = accountConstants.getPositionVOsByBranchId( branchId );

         final Map< String, String > emptyMap = new HashMap< String, String >();
         emptyMap.put( "id", "0" );
         emptyMap.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "��ѡ��" : "Please Select" );
         emptyMap.put( "full", "0" );
         listReturn.add( emptyMap );

         for ( PositionVO positionVO : positionList )
         {
            if ( corpId.equals( positionVO.getCorpId() ) )
            {
               int staffNum = accountConstants.getStaffNumByPositionId( request.getLocale().getLanguage(), positionVO.getPositionId() );
               int isVacant = Integer.parseInt( positionVO.getIsVacant() );
               // ְ������
               positionVO.reset( null, request );
               final String positionGradeName = ( KANUtil.filterEmpty( positionVO.getPositionGradeId(), "0" ) == null ? ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "[����ְ��]"
                     : "[No Position Grade]" )
                     : "[" + positionVO.getDecodePositionGradeId() + "]" );

               // ְλ����
               final String positionName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? positionVO.getTitleZH() : positionVO.getTitleEN();

               // ����������ʾ
               String isVacantTips = "";
               if ( isVacant != 0 && staffNum > isVacant )
               {
                  isVacantTips = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "[��������]" : "[All positions are filled.]";
               }

               // ��ȡ�ϼ�ְλstaff
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getPositionId() );

               // �ϼ�ְλ������
               String parenrPositionOwner = "";
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( StaffDTO parentStaffDTO : parentStaffDTOs )
                  {
                     if ( KANUtil.filterEmpty( parenrPositionOwner ) == null )
                     {
                        parenrPositionOwner = request.getLocale().getLanguage().equals( "zh" ) ? parentStaffDTO.getStaffVO().getNameZH() : parentStaffDTO.getStaffVO().getNameEN();
                     }
                     else
                     {
                        parenrPositionOwner = parenrPositionOwner + "��"
                              + ( request.getLocale().getLanguage().equals( "zh" ) ? parentStaffDTO.getStaffVO().getNameZH() : parentStaffDTO.getStaffVO().getNameEN() );
                     }
                  }
               }
               Map< String, String > map = new HashMap< String, String >();
               map.put( "id", positionVO.getPositionId() );
               map.put( "name", positionGradeName + " " + positionName + " " + isVacantTips + " " + parenrPositionOwner );
               if ( isVacant != 0 && staffNum > isVacant )
               {
                  map.put( "full", "1" );
               }
               else
               {
                  map.put( "full", "0" );
               }
               listReturn.add( map );
            }
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void checkPositionChangeStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
         employeePositionChangeVO.setStatus( "5" );
         int count = positionChangeService.getEffectivePositionChangeVOCountByEmployeeId( employeePositionChangeVO );
         Map< String, String > mapReturn = new HashMap< String, String >();
         mapReturn.put( "count", count + "" );
         JSONObject json = JSONObject.fromObject( mapReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_workflow_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         // �����������ID
         String historyId = request.getParameter( "historyId" );

         // ��ʼ��Service�ӿ�
         final HistoryService historyService = ( HistoryService ) getService( "historyService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

         // ��ʼ��SBBatchVO
         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         final EmployeePositionChangeVO employeePositionChange = ( EmployeePositionChangeVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
         if ( employeePositionChange != null )
         {
            final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( employeePositionChange.getPositionChangeId() );
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeePositionChangeVO.getEmployeeId() );

            // ˢ�¶��󣬳�ʼ�������б����ʻ�
            employeePositionChangeVO.reset( null, request );

            employeePositionChangeVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "employeeVO", employeeVO );
            request.setAttribute( "employeePositionChangeForm", employeePositionChangeVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax����
      return mapping.findForward( "managePositionChangeWorkFlow" );
   }

   public void loadPositionInfo_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String employeeId = request.getParameter( "employeeId" );
         final String positionId = request.getParameter( "positionId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final boolean lang_ch = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" );

         final Map< String, String > mapReturn = new HashMap< String, String >();

         if ( StringUtils.isNotEmpty( positionId ) )
         {
            // ��ȡPositionVO
            final PositionVO positionVO = accountConstants.getPositionVOByPositionId( positionId );
            if ( positionVO != null )
            {
               // ����ְλ��Ϣ��Map
               mapReturn.put( "oldPositionId", positionVO.getPositionId() );
               mapReturn.put( "oldPositionName", lang_ch ? positionVO.getTitleZH() : positionVO.getTitleEN() );
               mapReturn.put( "oldPositionNameZH", positionVO.getTitleZH() );
               mapReturn.put( "oldPositionNameEN", positionVO.getTitleEN() );
               mapReturn.put( "oldPositionGradeId", positionVO.getPositionGradeId() );
               mapReturn.put( "oldParentPositionId", positionVO.getParentPositionId() );
               mapReturn.put( "oldBranchId", positionVO.getBranchId() );

               // ��ȡ�ϼ�PositionVO
               final PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( positionVO.getParentPositionId() );
               if ( parentPositionVO != null )
               {
                  // �����ϼ�ְλ��Ϣ��Map
                  mapReturn.put( "oldParentPositionName", lang_ch ? parentPositionVO.getTitleZH() : parentPositionVO.getTitleEN() );
                  mapReturn.put( "oldParentPositionNameZH", parentPositionVO.getTitleZH() );
                  mapReturn.put( "oldParentPositionNameEN", parentPositionVO.getTitleEN() );

                  // �����ϼ�ְλ��������Ϣ��Map
                  mapReturn.put( "oldParentPositionOwners", accountConstants.getStaffNamesByPositionId( request.getLocale().getLanguage(), positionVO.getParentPositionId() ) );
                  mapReturn.put( "oldParentPositionOwnersZH", accountConstants.getStaffNamesByPositionId( "zh", positionVO.getParentPositionId() ) );
                  mapReturn.put( "oldParentPositionOwnersEN", accountConstants.getStaffNamesByPositionId( "en", positionVO.getParentPositionId() ) );
               }

               // ��ȡBranchVO
               final BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {
                  // ���沿����Ϣ��Map
                  mapReturn.put( "oldBranchName", lang_ch ? branchVO.getNameZH() : branchVO.getNameEN() );
                  mapReturn.put( "oldBranchNameZH", branchVO.getNameZH() );
                  mapReturn.put( "oldBranchNameEN", branchVO.getNameEN() );
                  mapReturn.put( "oldParentBranchId", branchVO.getParentBranchId() );

                  // ��ȡ�ϼ�����BranchVO
                  final BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( branchVO.getParentBranchId() );
                  if ( parentBranchVO != null )
                  {
                     // �����ϼ�������Ϣ��Map
                     mapReturn.put( "oldParentBranchName", lang_ch ? parentBranchVO.getNameZH() : parentBranchVO.getNameEN() );
                     mapReturn.put( "oldParentBranchNameZH", parentBranchVO.getNameZH() );
                     mapReturn.put( "oldParentBranchNameEN", parentBranchVO.getNameEN() );
                  }
               }

               // ��ȡְ��PositionGradeVO
               final PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );
               if ( positionGradeVO != null )
               {
                  mapReturn.put( "oldPositionGradeName", lang_ch ? positionGradeVO.getGradeNameZH() : positionGradeVO.getGradeNameEN() );
                  mapReturn.put( "oldPositionGradeNameZH", positionGradeVO.getGradeNameZH() );
                  mapReturn.put( "oldPositionGradeNameEN", positionGradeVO.getGradeNameEN() );
               }

               // ��ȡstaffPositionRelationId
               String tempStaffId = "";
               String oldStaffPositionRelationId = "0";
               if ( StringUtils.isNotEmpty( employeeId ) )
               {
                  final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionId );
                  final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeId );
                  if ( staffDTOs != null && staffDTOs.size() > 0 )
                  {
                     StaffDTO staffDTO = staffDTOs.get( 0 );
                     if ( staffDTO != null && staffDTO.getStaffVO() != null )
                     {
                        tempStaffId = staffDTO.getStaffVO().getStaffId();
                     }
                  }

                  if ( positionDTO != null )
                  {
                     List< PositionStaffRelationVO > positionStaffRelationList = positionDTO.getPositionStaffRelationVOs();
                     for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationList )
                     {
                        if ( StringUtils.equals( tempStaffId, positionStaffRelationVO.getStaffId() ) )
                        {
                           oldStaffPositionRelationId = positionStaffRelationVO.getRelationId();
                           break;
                        }
                     }
                  }
               }

               mapReturn.put( "staffId", tempStaffId );
               mapReturn.put( "oldStaffPositionRelationId", oldStaffPositionRelationId );
            }
         }

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // �ϼ�ְλ�ı�
   public void parentPositionChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String newParentPositionId = request.getParameter( "newParentPositionId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final Map< String, String > mapReturn = new HashMap< String, String >();

         String newParentPositionNameZH = "";
         String newParentPositionNameEN = "";
         String newParentPositionOwnersZH = "";
         String newParentPositionOwnersEN = "";
         if ( StringUtils.isNotEmpty( newParentPositionId ) )
         {
            // ��ȡ�ϼ�PositionVO
            final PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( newParentPositionId );
            if ( parentPositionVO != null )
            {
               newParentPositionNameZH = parentPositionVO.getTitleZH();
               newParentPositionNameEN = parentPositionVO.getTitleEN();
               newParentPositionOwnersZH = accountConstants.getStaffNamesByPositionId( "zh", newParentPositionId );
               newParentPositionOwnersEN = accountConstants.getStaffNamesByPositionId( "en", newParentPositionId );
            }
         }

         // �����ϼ�ְλ��Ϣ��Map
         mapReturn.put( "newParentPositionNameZH", newParentPositionNameZH );
         mapReturn.put( "newParentPositionNameEN", newParentPositionNameEN );
         // �����ϼ�ְλ��������Ϣ��Map
         mapReturn.put( "newParentPositionOwnersZH", newParentPositionOwnersZH );
         mapReturn.put( "newParentPositionOwnersEN", newParentPositionOwnersEN );

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���Ÿı�
   public void branchChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String newBranchId = request.getParameter( "newBranchId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final Map< String, String > mapReturn = new HashMap< String, String >();
         if ( StringUtils.isNotEmpty( newBranchId ) )
         {
            // ��ȡBranchVO
            final BranchVO branchVO = accountConstants.getBranchVOByBranchId( newBranchId );
            if ( branchVO != null )
            {
               mapReturn.put( "newBranchNameZH", branchVO.getNameZH() );
               mapReturn.put( "newBranchNameEN", branchVO.getNameEN() );
               mapReturn.put( "newParentBranchId", branchVO.getParentBranchId() );

               // ��ȡ�ϼ�BranchVO
               final BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( branchVO.getParentBranchId() );
               if ( parentBranchVO != null )
               {
                  mapReturn.put( "newParentBranchNameZH", parentBranchVO.getNameZH() );
                  mapReturn.put( "newParentBranchNameEN", parentBranchVO.getNameEN() );
               }
            }
         }

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ְ���ı�
   public void positionGradeChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String positionGradeId = request.getParameter( "newPositionGradeId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final Map< String, String > mapReturn = new HashMap< String, String >();
         if ( StringUtils.isNotEmpty( positionGradeId ) )
         {
            // ��ȡPositionGradeVO
            final PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( positionGradeId );
            if ( positionGradeVO != null )
            {
               mapReturn.put( "newPositionGradeNameZH", positionGradeVO.getGradeNameZH() );
               mapReturn.put( "newPositionGradeNameEN", positionGradeVO.getGradeNameEN() );
            }
         }

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���ְλ���Ƿ���ڶ���Ա��
   public void checkPositionStaffNum_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "html/text;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String positionId = request.getParameter( "positionId" );
         final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffDTOsByPositionId( positionId );

         String result = "1";
         if ( staffDTOs != null && staffDTOs.size() > 1 )
         {
            result = "2";
         }

         out.print( result );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }
}
