package com.kan.hro.web.actions.biz.importExcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kan.hro.service.inf.biz.sb.VendorSBTempService;

public class EmployeeContractResignImportAction extends BaseAction
{
   public final static String ACCESSACTION = "HRO_BIZ_EMPLOYEE_CONTRACT_RESIGN";

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
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         if ( new Boolean( ajax ) )
         {
            decodedObject( commonBatchVO );
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
            // ���SubActionΪ�գ�ͨ����������������򡢷�ҳ�򵼳�������Ajax�ύ������������Ҫ���롣
         }

         commonBatchVO.setAccessAction( ACCESSACTION );
         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortColumn( "batchId" );
            commonBatchVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( commonBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         commonBatchService.getCommonBatchVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeContractResignHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listCommonBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listCommonBatch" );
   }

   /**  
    * To EmployeeContractResign
    *	
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
      final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
      final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

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
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // ���Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         // ����ѡ�е�ID
         if ( commonBatchVO.getSelectedIds() != null && !commonBatchVO.getSelectedIds().trim().isEmpty() )
         {
            // ��ʼ��EmployeeContractResignDTO
            List< EmployeeContractResignDTO > employeeContractResignDTOs = new ArrayList< EmployeeContractResignDTO >();

            // �ָ�
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��CommonBatchVO
                  final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempCommonBatchVO.setModifyBy( getUserId( request, response ) );
                  tempCommonBatchVO.setModifyDate( new Date() );

                  // ��ʼ����ѯ
                  EmployeeContractResignVO employeeContractResignVO = new EmployeeContractResignVO();
                  employeeContractResignVO.setAccountId( tempCommonBatchVO.getAccountId() );
                  employeeContractResignVO.setCorpId( tempCommonBatchVO.getCorpId() );
                  employeeContractResignVO.setBatchId( tempCommonBatchVO.getBatchId() );

                  // ��ѯ���������
                  final List< Object > employeeContractResignVOObjects = employeeContractResignService.getEmployeeContractResignVOsByCondition( employeeContractResignVO );

                  if ( employeeContractResignVOObjects != null && employeeContractResignVOObjects.size() > 0 )
                  {
                     for ( Object object : employeeContractResignVOObjects )
                     {
                        // ��ʼ��EmployeeContractResignDTO
                        EmployeeContractResignDTO employeeContractResignDTO = new EmployeeContractResignDTO();
                        // ��ʼ���Ƿ���Ҫ���
                        boolean needAdd = false;

                        EmployeeContractResignVO tempEmployeeContractResignVO = ( EmployeeContractResignVO ) object;

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
                           tempCommonBatchVO.setStatus( "2" );
                           employeeContractResignService.updateEmployeeContractResign( tempEmployeeContractResignVO );
                           commonBatchService.updateCommonBatch( tempCommonBatchVO );
                        }
                     }
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
               }
               else
               {
                  success( request, null, "��ְ���˱������ɹ������������δ�޸ĳɹ����鹤������", MESSAGE_HEADER );
               }
            }
            else
            {
               warning( request, null, "��������Ч��ְ����", MESSAGE_HEADER );
            }

         }

         // ���Selected IDs����Action
         commonBatchVO.setSelectedIds( "" );
         commonBatchVO.setSubAction( "" );
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
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final VendorSBTempService vendorSBTempService = ( VendorSBTempService ) getService( "vendorSBTempService" );

         // ���Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         // ����ѡ�е�ID
         if ( commonBatchVO.getSelectedIds() != null && !commonBatchVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��CommonBatchVO
                  final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
                  // ����ɾ���ӿ�
                  tempCommonBatchVO.setModifyBy( getUserId( request, response ) );
                  tempCommonBatchVO.setModifyDate( new Date() );

                  vendorSBTempService.rollbackBatch( tempCommonBatchVO );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );
         }

         // ���Selected IDs����Action
         commonBatchVO.setSelectedIds( "" );
         commonBatchVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
