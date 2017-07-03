package com.kan.hro.web.actions.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractResignService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.web.actions.biz.importExcel.EmployeeContractResignImportAction;
import com.kan.hro.web.actions.biz.importExcel.EmployeeInsuranceNoImportBatchAction;

public class EmployeeContractResignAction extends BaseAction
{

   // ����Э��
   public static String EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT";
   // �Ͷ���ͬ��In House��
   public static String EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE_IN_HOUSE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE";

   // ��ǰAction��Ӧ��Access Action
   public static String getEmployeeContractAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         return EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE_IN_HOUSE;
      }
      else
      {
         return EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE;
      }
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         String batchId = new String();

         // �����������ID
         if ( new Boolean( ajax ) )
         {
            batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         }
         else
         {
            batchId = KANUtil.decodeString( request.getParameter( "batchId" ) );
         }

         final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );
         commonBatchVO.reset( null, request );
         request.setAttribute( "commonBatchVO", commonBatchVO );

         // ��ʼ��Service�ӿ�
         final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
         final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;

         if ( new Boolean( ajax ) )
         {
            decodedObject( employeeContractResignVO );
            // �����SubAction��ɾ���б����deleteObjects
            if ( getSubAction( form ).equalsIgnoreCase( ROLLBACK_OBJECTS ) )
            {
               // ����ɾ���б��SubAction
               rollback_objectList( mapping, form, request, response );
            }
            // �����SubAction�Ǹ����б����submitObjects
            else if ( getSubAction( form ).equalsIgnoreCase( SUBMIT_OBJECTS ) )
            {
               // �����޸��б��SubAction
               submit_objectList( mapping, form, request, response );
            }
            employeeContractResignVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         }
         else
         {
            employeeContractResignVO.setBatchId( batchId );
         }

         // ���û��ָ��������Ĭ�ϰ�  EmployeeContractResignId ����
         if ( employeeContractResignVO.getSortColumn() == null || employeeContractResignVO.getSortColumn().isEmpty() )
         {
            employeeContractResignVO.setSortColumn( "employeeContractResignId" );
            employeeContractResignVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeContractResignVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractResignService.getEmployeeContractResignVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeContractResignHolder", pagedListHolder );

         // ���������Ϊ������ת������ҳ��
         if ( pagedListHolder == null || pagedListHolder.getHolderSize() == 0 )
         {
            request.setAttribute( "messageInfo", true );
            return new EmployeeContractResignImportAction().list_object( mapping, new CommonBatchVO(), request, response );
         }

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listHeaderTemp" );
   }

   /**  
    * To EmployeeContractResign
    *	��Ӧ���籣��ʱ��Headerҳ��
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_employeeContractResign( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // �����������ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // ��ʼ��Service�ӿ�
      final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "employeeContractResignService" );
      final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );

      final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

      commonBatchVO.reset( null, request );
      request.setAttribute( "commonBatchVO", commonBatchVO );

      // ��õ�ǰҳ
      final String page = getPage( request );
      // ����Ƿ�Ajax����
      final String ajax = getAjax( request );
      final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;
      employeeContractResignVO.setBatchId( batchId );

      // ���õ�ǰ�û�AccountId
      employeeContractResignVO.setAccountId( getAccountId( request, response ) );
      // ���õ�ǰ��clientId
      employeeContractResignVO.setClientId( getCorpId( request, response ) );
      // ��ʼ��PagedListHolder���������÷�ʽ����Service
      final PagedListHolder employeeContractResignHolder = new PagedListHolder();
      // ���뵱ǰҳ
      employeeContractResignHolder.setPage( page );

      // ���û��ָ��������Ĭ�ϰ� BatchId����
      if ( employeeContractResignVO.getSortColumn() == null || employeeContractResignVO.getSortColumn().isEmpty() )
      {
         employeeContractResignVO.setSortColumn( "headerId" );
         employeeContractResignVO.setSortOrder( "desc" );
      }

      // ���뵱ǰֵ����
      employeeContractResignHolder.setObject( employeeContractResignVO );
      // ����ҳ���¼����
      employeeContractResignHolder.setPageSize( listPageSize_medium );
      // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
      employeeContractResignService.getEmployeeContractResignVOsByCondition( employeeContractResignHolder, true );
      // ˢ��Holder�����ʻ���ֵ
      refreshHolder( employeeContractResignHolder, request );
      // Holder��д��Request����
      request.setAttribute( "employeeContractResignHolder", employeeContractResignHolder );

      if ( new Boolean( ajax ) )
      {
         // д��Role
         return mapping.findForward( "listEmployeeContractResignTable" );
      }

      return mapping.findForward( "listEmployeeContractResignBody" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   public ActionForward modify_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   protected void submit_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

         // ���Action Form
         final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;

         // ����ѡ�е�ID
         if ( employeeContractResignVO.getSelectedIds() != null && !employeeContractResignVO.getSelectedIds().trim().isEmpty() )
         {
            // ��ʼ��Ҫ�޸ĵ� EmployeeContractResignDTO ����
            List< EmployeeContractResignDTO > employeeContractResignDTOs = new ArrayList< EmployeeContractResignDTO >();

            // �ָ�
            for ( String selectedId : employeeContractResignVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��EmployeeContractResignDTO
                  EmployeeContractResignDTO employeeContractResignDTO = new EmployeeContractResignDTO();
                  // ��ʼ���Ƿ���Ҫ���
                  boolean needAdd = false;

                  // ��ʼ��EmployeeContractResignVO
                  final EmployeeContractResignVO tempEmployeeContractResignVO = employeeContractResignService.getEmployeeContractResignVOByEmployeeContractResignId( KANUtil.decodeStringFromAjax( selectedId ) );

                  if ( tempEmployeeContractResignVO != null && tempEmployeeContractResignVO.getContractId() != null )
                  {
                     employeeContractResignDTO.setEmployeeContractResignId( employeeContractResignVO.getEmployeeContractResignId() );

                     // ��� EmployeeContractVO
                     EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( tempEmployeeContractResignVO.getContractId() );

                     // Ա����ְ
                     if ( KANUtil.filterEmpty( tempEmployeeContractResignVO.getResignDate() ) != null )
                     {
                        employeeContractVO.reset( null, request );
                        employeeContractVO.setResignDate( tempEmployeeContractResignVO.getResignDate() );
                        employeeContractVO.setLeaveReasons( tempEmployeeContractResignVO.getLeaveReasons() );
                        employeeContractVO.setLastWorkDate( tempEmployeeContractResignVO.getLastWorkDate() );
                        employeeContractVO.setEmployStatus( tempEmployeeContractResignVO.getEmployStatus() );
                        employeeContractVO.setModifyBy( getUserId( request, response ) );
                        employeeContractVO.setModifyDate( new Date() );
                        employeeContractVO.getHistoryVO().setAccessAction( getEmployeeContractAccessAction( request, response ) );

                        // ��ְ���
                        needAdd = true;
                        employeeContractResignDTO.setEmployeeContractVO( employeeContractVO );
                     }

                     // �籣1�˱�
                     if ( tempEmployeeContractResignVO.getSb1Id() != null && tempEmployeeContractResignVO.getSb1EndDate() != null )
                     {
                        // ��ʼ����ѯ
                        final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractSBVO.setSbSolutionId( tempEmployeeContractResignVO.getSb1Id() );
                        employeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // ��ѯ����Э���Ӧ���籣����
                        final List< Object > employeeContractSBVOObjects = employeeContractSBService.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                        // ����Э��һ���籣������Ӧֻ�ܶ�Ӧ����һ��
                        if ( employeeContractSBVOObjects != null && employeeContractSBVOObjects.size() > 0 )
                        {
                           final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObjects.get( 0 );
                           tempEmployeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractSBVO.setEndDate( tempEmployeeContractResignVO.getSb1EndDate() );
                           tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractSBVO.setModifyDate( new Date() );

                           // �籣�˱�
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractSBVOs().add( tempEmployeeContractSBVO );
                        }
                     }

                     // �籣2�˱�
                     if ( tempEmployeeContractResignVO.getSb2Id() != null && tempEmployeeContractResignVO.getSb2EndDate() != null )
                     {
                        // ��ʼ����ѯ
                        final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractSBVO.setSbSolutionId( tempEmployeeContractResignVO.getSb2Id() );
                        employeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // ��ѯ����Э���Ӧ���籣����
                        final List< Object > employeeContractSBVOObjects = employeeContractSBService.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                        // ����Э��һ���籣������Ӧֻ�ܶ�Ӧ����һ��
                        if ( employeeContractSBVOObjects != null && employeeContractSBVOObjects.size() > 0 )
                        {
                           final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObjects.get( 0 );
                           tempEmployeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractSBVO.setEndDate( tempEmployeeContractResignVO.getSb2EndDate() );
                           tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractSBVO.setModifyDate( new Date() );

                           // �籣�˱�
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractSBVOs().add( tempEmployeeContractSBVO );
                        }
                     }

                     // �籣3�˱�
                     if ( tempEmployeeContractResignVO.getSb3Id() != null && tempEmployeeContractResignVO.getSb3EndDate() != null )
                     {
                        // ��ʼ����ѯ
                        final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractSBVO.setSbSolutionId( tempEmployeeContractResignVO.getSb3Id() );
                        employeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // ��ѯ����Э���Ӧ���籣����
                        final List< Object > employeeContractSBVOObjects = employeeContractSBService.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                        // ����Э��һ���籣������Ӧֻ�ܶ�Ӧ����һ��
                        if ( employeeContractSBVOObjects != null && employeeContractSBVOObjects.size() > 0 )
                        {
                           final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObjects.get( 0 );
                           tempEmployeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractSBVO.setEndDate( tempEmployeeContractResignVO.getSb3EndDate() );
                           tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractSBVO.setModifyDate( new Date() );

                           // �籣�˱�
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractSBVOs().add( tempEmployeeContractSBVO );
                        }
                     }

                     // �̱�1�˱�
                     if ( tempEmployeeContractResignVO.getCb1Id() != null && tempEmployeeContractResignVO.getCb1EndDate() != null )
                     {
                        // ��ʼ����ѯ
                        final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
                        employeeContractCBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractCBVO.setSolutionId( tempEmployeeContractResignVO.getCb1Id() );
                        employeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // ��ѯ����Э���Ӧ���̱�����
                        final List< Object > employeeContractCBVOObjects = employeeContractCBService.getEmployeeContractCBVOsByCondition( employeeContractCBVO );

                        // ����Э��һ���̱�������Ӧֻ�ܶ�Ӧ����һ��
                        if ( employeeContractCBVOObjects != null && employeeContractCBVOObjects.size() > 0 )
                        {
                           final EmployeeContractCBVO tempEmployeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObjects.get( 0 );
                           tempEmployeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractCBVO.setEndDate( tempEmployeeContractResignVO.getCb1EndDate() );
                           tempEmployeeContractCBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractCBVO.setModifyDate( new Date() );

                           // �̱��˱�
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractCBVOs().add( tempEmployeeContractCBVO );
                        }
                     }

                     // �̱�2�˱�
                     if ( tempEmployeeContractResignVO.getCb2Id() != null && tempEmployeeContractResignVO.getCb2EndDate() != null )
                     {
                        // ��ʼ����ѯ
                        final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
                        employeeContractCBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractCBVO.setSolutionId( tempEmployeeContractResignVO.getCb2Id() );
                        employeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // ��ѯ����Э���Ӧ���̱�����
                        final List< Object > employeeContractCBVOObjects = employeeContractCBService.getEmployeeContractCBVOsByCondition( employeeContractCBVO );

                        // ����Э��һ���̱�������Ӧֻ�ܶ�Ӧ����һ��
                        if ( employeeContractCBVOObjects != null && employeeContractCBVOObjects.size() > 0 )
                        {
                           final EmployeeContractCBVO tempEmployeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObjects.get( 0 );
                           tempEmployeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractCBVO.setEndDate( tempEmployeeContractResignVO.getCb2EndDate() );
                           tempEmployeeContractCBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractCBVO.setModifyDate( new Date() );

                           // �̱��˱�
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractCBVOs().add( tempEmployeeContractCBVO );
                        }
                     }
                  }

                  // ��������ݸ���
                  if ( needAdd )
                  {
                     employeeContractResignDTOs.add( employeeContractResignDTO );
                     // ���� EmployeeContractResignVO ��CommonBatchVO Ϊ���Ѹ��¡�
                     tempEmployeeContractResignVO.setStatus( "2" );
                     employeeContractResignService.updateEmployeeContractResign( tempEmployeeContractResignVO );
                  }
               }
            }

            if ( employeeContractResignDTOs.size() > 0 )
            {
               // ��ʼ���������  EmployeeContractResignVO��ID����
               List< String > employeeContractResignIds = new ArrayList< String >();

               for ( EmployeeContractResignDTO employeeContractResignDTO2 : employeeContractResignDTOs )
               {
                  try
                  {
                     employeeContractResignService.submitEmployeeContractResignDTO( employeeContractResignDTO2 );
                  }
                  catch ( Exception e )
                  {
                     employeeContractResignIds.add( employeeContractResignDTO2.getEmployeeContractResignId() );
                  }
               }

               if ( employeeContractResignIds.size() > 0 )
               {
                  warning( request, null, "��ְ���˱�δ�ɹ���", MESSAGE_HEADER );
                  //                  warning( request, null, "���IDΪ��" + employeeContractResignIds.toString() + "����Ŀ��ְ���˱�δ�ɹ���", MESSAGE_HEADER );
               }
               else
               {

                  // ���Ը��¶�Ӧ�� CommonBatch
                  String batchId = employeeContractResignVO.getBatchId();

                  if ( new Boolean( getAjax( request ) ) && KANUtil.filterEmpty( batchId ) != null )
                  {
                     batchId = KANUtil.decodeStringFromAjax( batchId );
                  }
                  final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

                  // ��ʼ����ѯ����
                  final EmployeeContractResignVO tempEmployeeContractResignVO = new EmployeeContractResignVO();
                  tempEmployeeContractResignVO.setBatchId( batchId );
                  tempEmployeeContractResignVO.setAccountId( employeeContractResignVO.getAccountId() );
                  tempEmployeeContractResignVO.setCorpId( employeeContractResignVO.getCorpId() );

                  final List< Object > employeeContractResignVOObjects = employeeContractResignService.getEmployeeContractResignVOsByCondition( tempEmployeeContractResignVO );

                  my: if ( employeeContractResignVOObjects != null && employeeContractResignVOObjects.size() > 0 )
                  {
                     for ( Object object : employeeContractResignVOObjects )
                     {
                        EmployeeContractResignVO tempEmployeeContractResignVO2 = ( EmployeeContractResignVO ) object;

                        if ( !"2".equals( tempEmployeeContractResignVO2.getStatus() ) )
                        {
                           break my;
                        }
                     }
                     // ���ȫ�����¹��˸��� CommonBatchVO
                     commonBatchVO.setStatus( "2" );
                     commonBatchService.updateCommonBatch( commonBatchVO );
                  }

                  success( request, null, "��ְ���˱������ɹ������������δ�޸ĳɹ����鹤������", MESSAGE_HEADER );
               }
            }

            // ���Selected IDs����Action
            employeeContractResignVO.setSelectedIds( "" );
            employeeContractResignVO.setSubAction( "" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   protected void rollback_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
         // ���Action Form
         final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;

         // ����ѡ�е�ID
         if ( employeeContractResignVO.getSelectedIds() != null && !employeeContractResignVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : employeeContractResignVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  final String employeeContractResignId = KANUtil.decodeStringFromAjax( selectedId );

                  final String batchId = KANUtil.decodeStringFromAjax( employeeContractResignVO.getBatchId() );

                  final EmployeeContractResignVO tempEmployeeContractResignVO = employeeContractResignService.getEmployeeContractResignVOByEmployeeContractResignId( employeeContractResignId );

                  tempEmployeeContractResignVO.setBatchId( batchId );
                  // ����ɾ���ӿ�
                  employeeContractResignService.deleteEmployeeContractResign( tempEmployeeContractResignVO );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );
         }

         // ���Selected IDs����Action
         employeeContractResignVO.setSelectedIds( "" );
         employeeContractResignVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
      String selectedIds = request.getParameter( "selectedIds" );
      if ( StringUtils.isNotEmpty( selectedIds ) )
      {
         String batchId = request.getParameter( "batchId" );
         String[] ids = selectedIds.split( "," );
         for ( int i = 0; i < ids.length; i++ )
         {
            ids[ i ] = KANUtil.decodeStringFromAjax( ids[ i ] );
         }
         int count = employeeContractResignService.backUpRecord( ids, KANUtil.decodeStringFromAjax( batchId ) );
         if ( count == 0 )
         {
            CommonBatchVO commonBatchVO = new CommonBatchVO();
            commonBatchVO.reset( mapping, request );
            return new EmployeeContractResignImportAction().list_object( mapping, commonBatchVO, request, response );
         }
         else
         {
            final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;
            employeeContractResignVO.setBatchId( batchId );
            employeeContractResignVO.setSelectedIds( "" );
            return list_object( mapping, employeeContractResignVO, request, response );
         }
      }
      else
      {
         CommonBatchVO commonBatchVO = new CommonBatchVO();
         commonBatchVO.reset( mapping, request );
         return new EmployeeInsuranceNoImportBatchAction().list_object( mapping, commonBatchVO, request, response );
      }
   }

}
