package com.kan.hro.service.impl.biz.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBTempService;
import com.kan.hro.web.actions.biz.employee.EmployeeContractSBAction;

public class EmployeeContractSBTempServiceImpl extends ContextService implements EmployeeContractSBTempService
{
   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractSBVO";

   public final String SERVICE_BEAN = "employeeContractSBService";

   // ע��VendorDao
   private VendorDao vendorDao;

   // ע��VendorServiceDao
   private VendorServiceDao vendorServiceDao;

   // ע��EmployeeContractSBDetailDao
   private EmployeeContractSBDetailDao employeeContractSBDetailDao;

   // ע��WorkflowService
   private WorkflowService workflowService;

   // ע��EmployeeContractDao
   private EmployeeContractTempDao employeeContractTempDao;

   public EmployeeContractTempDao getEmployeeContractTempDao()
   {
      return employeeContractTempDao;
   }

   public void setEmployeeContractTempDao( EmployeeContractTempDao employeeContractTempDao )
   {
      this.employeeContractTempDao = employeeContractTempDao;
   }

   public final VendorDao getVendorDao()
   {
      return vendorDao;
   }

   public final void setVendorDao( VendorDao vendorDao )
   {
      this.vendorDao = vendorDao;
   }

   public final VendorServiceDao getVendorServiceDao()
   {
      return vendorServiceDao;
   }

   public final void setVendorServiceDao( VendorServiceDao vendorServiceDao )
   {
      this.vendorServiceDao = vendorServiceDao;
   }

   public EmployeeContractSBDetailDao getEmployeeContractSBDetailDao()
   {
      return employeeContractSBDetailDao;
   }

   public void setEmployeeContractSBDetailDao( EmployeeContractSBDetailDao employeeContractSBDetailDao )
   {
      this.employeeContractSBDetailDao = employeeContractSBDetailDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   @Override
   public PagedListHolder getEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBTempDao employeeContractSBTempDao = ( EmployeeContractSBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBTempDao.countEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBTempVO getEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( ( EmployeeContractSBTempDao ) getDao() ).getEmployeeContractSBTempVOByEmployeeSBId( employeeSBId );
   }

   @Override
   public PagedListHolder getFullEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBTempDao employeeContractSBDao = ( EmployeeContractSBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBDao.countFullEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBDao.getFullEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBDao.getFullEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBTempVO getFullEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( ( EmployeeContractSBTempDao ) getDao() ).getFullEmployeeContractSBTempVOByEmployeeSBId( employeeSBId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int insertEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // ��������
         startTransaction();

         rows = ( ( EmployeeContractSBTempDao ) getDao() ).insertEmployeeContractSBTemp( employeeContractSBVO );

         final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
         final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
         final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();

         if ( solutionDetailIdArray != null && baseCompanyArray != null && basePersonalArray != null && solutionDetailIdArray.length > 0 && baseCompanyArray.length > 0
               && basePersonalArray.length > 0 )
         {
            // ֱ�Ӳ����籣��ϸ
            for ( int i = 0; i < solutionDetailIdArray.length; i++ )
            {
               if ( baseCompanyArray.length > i && basePersonalArray.length > i )
               {
                  final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                  employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  employeeContractSBDetailVO.setSolutionDetailId( solutionDetailIdArray[ i ].trim() );
                  employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ i ].trim() );
                  employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ i ].trim() );
                  employeeContractSBDetailVO.setCreateBy( employeeContractSBVO.getCreateBy() );
                  employeeContractSBDetailVO.setCreateDate( new Date() );
                  employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getModifyBy() );
                  employeeContractSBDetailVO.setModifyDate( new Date() );
                  employeeContractSBDetailVO.setStatus( EmployeeContractSBDetailVO.TRUE );
                  employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
               }
            }
         }

         // �ύ����
         commitTransaction();

         if ( employeeContractSBVO.getSubAction() != null && employeeContractSBVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
         {
            employeeContractSBVO.setDeleted( "1" );
            rows = submitEmployeeContractSBTemp( employeeContractSBVO );
         }

         return rows;
      }
      catch ( Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int updateEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // ��������
         startTransaction();

         rows = ( ( EmployeeContractSBTempDao ) getDao() ).updateEmployeeContractSBTemp( employeeContractSBVO );

         //��ѯ�籣������ϸ
         final List< Object > employeeContractSBDetailVOs = this.employeeContractSBDetailDao.getEmployeeContractSBDetailVOsByEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );

         if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
         {

            final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
            final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
            final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();

            if ( solutionDetailIdArray != null && baseCompanyArray != null && basePersonalArray != null && solutionDetailIdArray.length > 0 && baseCompanyArray.length > 0
                  && basePersonalArray.length > 0 )
            {
               boolean exist[] = new boolean[ solutionDetailIdArray.length ];
               for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
               {
                  final EmployeeContractSBDetailVO employeeContractSBDetailVO = ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject;
                  String solutionDetailId = employeeContractSBDetailVO.getSolutionDetailId();
                  int index = -1;
                  for ( int i = 0; i < solutionDetailIdArray.length; i++ )
                  {

                     if ( solutionDetailId.equals( solutionDetailIdArray[ i ].trim() ) )
                     {
                        index = i;
                        exist[ i ] = true;
                        break;
                     }
                  }

                  if ( index > -1 )
                  {
                     // ����
                     employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ index ].trim() );
                     employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ index ].trim() );
                     employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getCreateBy() );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     employeeContractSBDetailDao.updateEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }
                  else
                  {
                     // �߼�ɾ��
                     employeeContractSBDetailVO.setDeleted( EmployeeContractSBDetailVO.FALSE );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     employeeContractSBDetailDao.updateEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }

               }

               // ��������Ŀ�Ŀ
               for ( int i = 0; i < exist.length; i++ )
               {
                  if ( !exist[ i ] )
                  {
                     final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                     employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                     employeeContractSBDetailVO.setSolutionDetailId( solutionDetailIdArray[ i ].trim() );
                     employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ i ].trim() );
                     employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ i ] );
                     employeeContractSBDetailVO.setCreateBy( employeeContractSBVO.getCreateBy() );
                     employeeContractSBDetailVO.setCreateDate( new Date() );
                     employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getModifyBy() );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     employeeContractSBDetailVO.setStatus( EmployeeContractSBDetailVO.TRUE );
                     employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }
               }
            }
         }

         // �ύ����
         commitTransaction();

         return rows;
      }
      catch ( Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int deleteEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      employeeContractSBVO.setDeleted( EmployeeContractSBVO.FALSE );
      return updateEmployeeContractSBTemp( employeeContractSBVO );
   }

   @Override
   // AddAdded Kevin Jin at 2013-11-21
   public int submitEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( employeeContractSBVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( employeeContractSBVO );

         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ԭʼ�籣״̬
         final String originalStatus = employeeContractSBVO.getStatus();

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractSBVO );
         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( originalStatus != null )
            {
               if ( originalStatus.trim().equals( "0" ) )
               {
                  // ״̬��Ϊ�걨�ύ
                  employeeContractSBVO.setStatus( "1" );
               }
               else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
               {
                  // ״̬��Ϊ�˱��ύ
                  employeeContractSBVO.setStatus( "4" );
               }

               updateEmployeeContractSBTemp( employeeContractSBVO );

               // Service�ķ���
               historyVO.setServiceMethod( "submitEmployeeContractSB" );
               historyVO.setObjectId( employeeContractSBVO.getEmployeeSBId() );
            }

            String passObject = null;
            String failObject = null;

            // ���籣
            if ( originalStatus.trim().equals( "0" ) )
            {
               // �ӱ���׼
               employeeContractSBVO.setStatus( "2" );
               passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
               // �ӱ��ܾ�
               employeeContractSBVO.setStatus( "0" );
               failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
            }
            // ���걨�ӱ�
            else if ( originalStatus.trim().equals( "2" ) )
            {
               // �˱���׼
               employeeContractSBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
               // �˱��ܾ�
               employeeContractSBVO.setStatus( "2" );
               failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
            }
            // ��������
            else if ( originalStatus.trim().equals( "3" ) )
            {
               //  �˱���׼
               employeeContractSBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
               //  �˱��ܾ�
               employeeContractSBVO.setStatus( "3" );
               failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
            }

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, employeeContractSBVO );

            return -1;
         }
         // û�й�����
         else
         {
            // ���籣
            if ( originalStatus.trim().equals( "0" ) )
            {
               employeeContractSBVO.setStatus( "2" );
            }
            // ���걨�ӱ�����������״̬
            else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
            {
               employeeContractSBVO.setStatus( "5" );
            }

            return updateEmployeeContractSBTemp( employeeContractSBVO );
         }
      }

      return updateEmployeeContractSBTemp( employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBTempVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractSBTempDao ) getDao() ).getEmployeeContractSBTempVOsByContractId( contractId );
   }

   @Override
   public List< EmployeeContractSBDTO > getEmployeeContractSBTempDTOsByContractId( final String contractId ) throws KANException
   {
      // ��ʼ��EmployeeContractSBDTO List
      final List< EmployeeContractSBDTO > employeeContractSBDTOs = new ArrayList< EmployeeContractSBDTO >();

      // ����������ȡEmployeeContractSBVO List
      final List< Object > employeeContractSBVOs = ( ( EmployeeContractSBTempDao ) getDao() ).getEmployeeContractSBTempVOsByContractId( contractId );

      // �������EmployeeContractSBVO List���ݣ�����
      if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
      {
         for ( Object employeeContractSBVOObject : employeeContractSBVOs )
         {
            final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
            // ��ʼ��EmployeeContractSBDTO����
            final EmployeeContractSBDTO employeeContractSBDTO = new EmployeeContractSBDTO();

            /** װ��DTO���ݶ��� */
            // װ�ع�Ӧ��
            employeeContractSBDTO.setVendorVO( this.getVendorDao().getVendorVOByVendorId( tempEmployeeContractSBVO.getVendorId() ) );

            // װ�ع�Ӧ�̷���
            employeeContractSBDTO.setVendorServiceVO( this.getVendorServiceDao().getVendorServiceVOByServiceId( tempEmployeeContractSBVO.getVendorServiceId() ) );

            // װ��EmployeeContractSBVO
            employeeContractSBDTO.setEmployeeContractSBVO( tempEmployeeContractSBVO );

            // װ��EmployeeContractSBDetailVO
            final List< Object > employeeContractSBDetailVOs = this.getEmployeeContractSBDetailDao().getEmployeeContractSBDetailVOsByEmployeeSBId( ( tempEmployeeContractSBVO ).getEmployeeSBId() );
            if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
            {
               for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
               {
                  employeeContractSBDTO.getEmployeeContractSBDetailVOs().add( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject );
               }
            }

            employeeContractSBDTOs.add( employeeContractSBDTO );
         }
      }

      return employeeContractSBDTOs;
   }

   // Reviewed by Kevin Jin at 2013-11-21
   private HistoryVO generateHistoryVO( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      final HistoryVO history = employeeContractSBVO.getHistoryVO();
      history.setAccessAction( EmployeeContractSBAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeContractSBAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getEmployeeContractSBVOByEmployeeSBId" );
      // �����ȸ�������ID
      history.setObjectType( "2" );
      history.setAccountId( employeeContractSBVO.getAccountId() );
      history.setNameZH( employeeContractSBVO.getDecodeSbSolutionId() );
      history.setNameEN( employeeContractSBVO.getDecodeSbSolutionId() );
      return history;
   }

   /**  
    * modifyEmployeeContractSBVO
    *  ģ̬�� �޸� ��ͬ�籣����(������¼)
    * @param employeeContractSBVO
    * @return
    * @throws KANException
    * @throws ParseException
    */
   @Override
   public String modifyEmployeeContractSBTempVO( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // ��ȡ��Ҫ�޸ĵ�����Э��Contract ID����Ҫ�޸ĵ��籣����ID
      employeeContractSBVO.setContractId( KANUtil.decodeStringFromAjax( employeeContractSBVO.getContractId() ) );
      final String solutionId = employeeContractSBVO.getSbSolutionId();

      // ��ʼ����ѯ����
      final EmployeeContractSBTempVO employeeContractSBVOTemp = new EmployeeContractSBTempVO();
      employeeContractSBVOTemp.setContractId( employeeContractSBVO.getContractId() );
      employeeContractSBVOTemp.setSbSolutionId( solutionId );
      employeeContractSBVOTemp.setAccountId( employeeContractSBVO.getAccountId() );

      //  ��������ڸ��籣�������������
      if ( ( ( EmployeeContractSBTempDao ) getDao() ).countFullEmployeeContractSBTempVOsByCondition( employeeContractSBVOTemp ) == 0 )
      {
         // ���Ĳ���Ϊ���
         actFlag = "addObject";
         employeeContractSBVO.setCreateBy( employeeContractSBVO.getModifyBy() );
         // ��ա��˱����ڡ�
         employeeContractSBVO.setEndDate( null );

         employeeContractSBVO.setStatus( "0" );

         // ��ȡSubAction�ж��Ƿ���Ҫ�ύ
         final String subAction = employeeContractSBVO.getSubAction();

         if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
         {
            actFlag = "submitObject";
            employeeContractSBVO.setStatus( "2" );
         }

         // ��������
         updateDateByCondition( employeeContractSBVO );
         // ����
         ( ( EmployeeContractSBTempDao ) getDao() ).insertEmployeeContractSBTemp( employeeContractSBVO );

         final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
         final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
         final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();

         if ( solutionDetailIdArray != null && baseCompanyArray != null && basePersonalArray != null && solutionDetailIdArray.length > 0 && baseCompanyArray.length > 0
               && basePersonalArray.length > 0 )
         {
            // ֱ�Ӳ����籣��ϸ
            for ( int i = 0; i < solutionDetailIdArray.length; i++ )
            {
               if ( baseCompanyArray.length > i && basePersonalArray.length > i )
               {
                  final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                  employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  employeeContractSBDetailVO.setSolutionDetailId( solutionDetailIdArray[ i ].trim() );
                  employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ i ].trim() );
                  employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ i ].trim() );
                  employeeContractSBDetailVO.setCreateBy( employeeContractSBVO.getCreateBy() );
                  employeeContractSBDetailVO.setCreateDate( new Date() );
                  employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getModifyBy() );
                  employeeContractSBDetailVO.setModifyDate( new Date() );
                  employeeContractSBDetailVO.setStatus( EmployeeContractSBDetailVO.TRUE );
                  employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
               }
            }
         }

      }
      // ����������޸�
      else
      {
         // ���Ĳ���Ϊ���
         actFlag = "updateObject";

         final List< Object > employeeContractSBVOs = ( ( EmployeeContractSBTempDao ) getDao() ).getFullEmployeeContractSBTempVOsByCondition( employeeContractSBVOTemp );

         // ����
         for ( Object object : employeeContractSBVOs )
         {
            final EmployeeContractSBTempVO employeeContractSBVOObject = ( EmployeeContractSBTempVO ) object;
            employeeContractSBVOObject.setModifyBy( employeeContractSBVO.getModifyBy() );

            // ���EmployeeContractSBVO δ�޸�ǰ��״̬
            final String status = employeeContractSBVOObject.getStatus();

            // �����û�ѡ���ж��Ƿ���Ҫ���·�������
            if ( employeeContractSBVO.getApplyToAllHeader() != null && employeeContractSBVO.getApplyToAllHeader().equals( "1" ) )
            {
               employeeContractSBVOObject.update( employeeContractSBVO );
               // ��������
               updateDateByCondition( employeeContractSBVOObject );
               // ״̬��ԭ
               employeeContractSBVOObject.setStatus( status );
            }

            // ��ȡSubAction�ж��Ƿ���Ҫ�ύ
            final String subAction = employeeContractSBVO.getSubAction();

            if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
            {

               // ��������籣�򡰴��걨�ӱ���
               if ( status.equals( "0" ) )
               {
                  actFlag = "submitObject";
                  employeeContractSBVOObject.setStatus( "2" );
               }
               // ����ǡ����˹��������������򡰴��걨�˱���
               else if ( status.equals( "2" ) || status.equals( "3" ) )
               {
                  actFlag = "submitObject";
                  employeeContractSBVOObject.setStatus( "5" );
               }

            }

            // �����û�ѡ���ж��Ƿ���Ҫ���·�����ϸ����
            if ( employeeContractSBVO.getApplyToAllDetail() != null && employeeContractSBVO.getApplyToAllDetail().equals( "1" ) )
            {
               updateEmployeeContractSBTemp( employeeContractSBVOObject );
               // ����
            }
            else
            {
               // ֻ����Header��Ϣ
               updateEmployeeContractSBPopup( employeeContractSBVOObject );
            }

         }
      }

      return actFlag;
   }

   /**  
    * ModifyEmployeeContractSBVOs
    *  ģ̬�� �޸� ��ͬ�籣����(������¼)
    * @param employeeContractSBVO
    * @return
    * @throws KANException
    * @throws ParseException
    */
   @Override
   public String modifyEmployeeContractSBTempVOs( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractSBVO.getSelectedIds();
      // ����
      for ( String selectedId : selectIds.split( "," ) )
      {

         if ( selectedId != null && !selectedId.equals( "null" ) && !selectedId.isEmpty() )
         {
            employeeContractSBVO.setContractId( selectedId );

            modifyEmployeeContractSBTempVO( employeeContractSBVO );
         }

      }
      return null;
   }

   /**  
    * UpdateDateByCondition
    * ��������Э�鿪ʼ���ںͽ������������籣����
    * @param employeeContractSBVO
    * @throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.employeeContractTempDao.getEmployeeContractTempVOByContractId( employeeContractSBVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // ���ڸ�ʽ
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // �ж��籣������ʼʱ���Ƿ���������Ϣ֮ǰ
         if ( employeeContractSBVO.getStartDate() != null && !employeeContractSBVO.getStartDate().isEmpty() )
         {

            final Date sbStartDate = df.parse( employeeContractSBVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( sbStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractSBVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // �ж��籣��������ʱ���Ƿ���������Ϣ֮��
         if ( employeeContractSBVO.getEndDate() != null && !employeeContractSBVO.getEndDate().isEmpty() )
         {
            final Date sbEndDate = df.parse( employeeContractSBVO.getEndDate() );
            final Date contractEndDate = df.parse( employeeContractVO.getEndDate() );

            if ( sbEndDate.getTime() > contractEndDate.getTime() )
            {
               employeeContractSBVO.setEndDate( employeeContractVO.getEndDate() );
            }

         }

      }

   }

   /**  
    * updateEmployeeContractSBPopup
    *	ģ̬������̱�Header��Ϣ
    *	@param employeeContractSBVO
    *	@return
    *	@throws KANException
    */
   private int updateEmployeeContractSBPopup( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // ��������
         startTransaction();

         rows = ( ( EmployeeContractSBTempDao ) getDao() ).updateEmployeeContractSBTemp( employeeContractSBVO );
         // �ύ����
         commitTransaction();

         return rows;
      }
      catch ( Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public PagedListHolder getVendorEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeContractSBTempDao employeeContractSBTempDao = ( EmployeeContractSBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBTempDao.countVendorEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getVendorEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getVendorEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }
}
