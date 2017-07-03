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
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.web.actions.biz.employee.EmployeeContractSBAction;

public class EmployeeContractSBServiceImpl extends ContextService implements EmployeeContractSBService
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
   private EmployeeContractDao employeeContractDao;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
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
   public PagedListHolder getEmployeeContractSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBDao employeeContractSBDao = ( EmployeeContractSBDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBDao.countEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBDao.getEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBDao.getEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBVO getEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( ( EmployeeContractSBDao ) getDao() ).getEmployeeContractSBVOByEmployeeSBId( employeeSBId );
   }

   @Override
   public PagedListHolder getFullEmployeeContractSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBDao employeeContractSBDao = ( EmployeeContractSBDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBDao.countFullEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBDao.getFullEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBDao.getFullEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBVO getFullEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( ( EmployeeContractSBDao ) getDao() ).getFullEmployeeContractSBVOByEmployeeSBId( employeeSBId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int insertEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // ��������
         startTransaction();

         rows = ( ( EmployeeContractSBDao ) getDao() ).insertEmployeeContractSB( employeeContractSBVO );

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
            rows = submitEmployeeContractSB( employeeContractSBVO );
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
   public int updateEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // ��������
         startTransaction();

         rows = ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( employeeContractSBVO );

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
         else
         {
            if ( employeeContractSBVO.getSolutionDetailIdArray() != null && employeeContractSBVO.getBaseCompanyArray() != null
                  && employeeContractSBVO.getBasePersonalArray() != null && employeeContractSBVO.getSolutionDetailIdArray().length > 0
                  && employeeContractSBVO.getBaseCompanyArray().length > 0 && employeeContractSBVO.getBasePersonalArray().length > 0 )
            {
               final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
               final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
               final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();
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
   public int deleteEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      employeeContractSBVO.setDeleted( EmployeeContractSBVO.FALSE );
      return updateEmployeeContractSB( employeeContractSBVO );
   }

   @Override
   // AddAdded Kevin Jin at 2013-11-21
   public int submitEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( employeeContractSBVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( employeeContractSBVO, false );

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
                  updateEmployeeContractSB( employeeContractSBVO );
               }
               else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
               {
                  // ״̬��Ϊ�˱��ύ
                  employeeContractSBVO.setStatus( "4" );

                  // ����ֻ�޸�״̬
                  final EmployeeContractSBVO tempEmployeeContractSBVO = ( ( EmployeeContractSBDao ) getDao() ).getEmployeeContractSBVOByEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  tempEmployeeContractSBVO.setStatus( employeeContractSBVO.getStatus() );
                  ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( tempEmployeeContractSBVO );
               }

               // Service�ķ���
               historyVO.setServiceMethod( "submitEmployeeContractSB" );
               historyVO.setObjectId( employeeContractSBVO.getEmployeeSBId() );
            }

            String passObject = null;
            String failObject = null;

            // ���籣
            if ( originalStatus.trim().equals( "0" ) || originalStatus.trim().equals( "6" ) )
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
            // ���籣||���˱�
            if ( originalStatus.trim().equals( "0" ) || originalStatus.trim().equals( "6" ) )
            {
               employeeContractSBVO.setStatus( "2" );
            }
            // ���걨�ӱ�����������״̬
            else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
            {
               employeeContractSBVO.setStatus( "5" );
            }

            return updateEmployeeContractSB( employeeContractSBVO );
         }
      }

      return updateEmployeeContractSB( employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractSBDao ) getDao() ).getEmployeeContractSBVOsByContractId( contractId );
   }

   @Override
   public List< EmployeeContractSBDTO > getEmployeeContractSBDTOsByContractId( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��EmployeeContractSBDTO List
      final List< EmployeeContractSBDTO > employeeContractSBDTOs = new ArrayList< EmployeeContractSBDTO >();

      // ��ʼ����������
      final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
      employeeContractSBVO.setContractId( employeeContractVO.getContractId() );
      employeeContractSBVO.setSbType( employeeContractVO.getSbType() );
      employeeContractSBVO.setStatusArray( employeeContractVO.getSbStatusArray() );

      // ����������ȡEmployeeContractSBVO List
      final List< Object > employeeContractSBVOs = ( ( EmployeeContractSBDao ) getDao() ).getEmployeeContractSBVOsByCondition( employeeContractSBVO );

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
   private HistoryVO generateHistoryVO( final EmployeeContractSBVO employeeContractSBVO, final boolean leave ) throws KANException
   {
      final HistoryVO history = employeeContractSBVO.getHistoryVO();
      history.setAccessAction( EmployeeContractSBAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeContractSBAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getEmployeeContractSBVOByEmployeeSBId" );
      history.setObjectType( "2" );
      // ��3����ʾ�޸����͹�����
      history.setRemark5( employeeContractSBVO.getStatus().equals( "0" ) ? "0" : "3" );
      history.setAccountId( employeeContractSBVO.getAccountId() );
      history.setNameZH( employeeContractSBVO.getEmployeeNameZH() + " - " + ( leave ? "��ְ�˱�  - " : "" ) + employeeContractSBVO.getDecodeSbSolutionId() );
      history.setNameEN( employeeContractSBVO.getEmployeeNameZH() + " - " + ( leave ? "��ְ�˱�  - " : "" ) + employeeContractSBVO.getDecodeSbSolutionId() );
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
   public String modifyEmployeeContractSBVO( final EmployeeContractSBVO employeeContractSBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // ��ȡ��Ҫ�޸ĵ�����Э��Contract ID����Ҫ�޸ĵ��籣����ID
      final String solutionId = employeeContractSBVO.getSbSolutionId();

      // ��ʼ����ѯ����
      final EmployeeContractSBVO employeeContractSBVOTemp = new EmployeeContractSBVO();
      employeeContractSBVOTemp.setContractId( employeeContractSBVO.getContractId() );
      employeeContractSBVOTemp.setSbSolutionId( solutionId );
      employeeContractSBVOTemp.setAccountId( employeeContractSBVO.getAccountId() );
      employeeContractSBVOTemp.setCorpId( employeeContractSBVO.getCorpId() );

      //  ��������ڸ��籣�������������
      if ( ( ( EmployeeContractSBDao ) getDao() ).countFullEmployeeContractSBVOsByCondition( employeeContractSBVOTemp ) == 0 )
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
         ( ( EmployeeContractSBDao ) getDao() ).insertEmployeeContractSB( employeeContractSBVO );

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

         final List< Object > employeeContractSBVOs = ( ( EmployeeContractSBDao ) getDao() ).getFullEmployeeContractSBVOsByCondition( employeeContractSBVOTemp );

         // ����
         for ( Object object : employeeContractSBVOs )
         {
            final EmployeeContractSBVO employeeContractSBVOObject = ( EmployeeContractSBVO ) object;
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
               updateEmployeeContractSB( employeeContractSBVOObject );
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
   public String modifyEmployeeContractSBVOs( final EmployeeContractSBVO employeeContractSBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractSBVO.getSelectedIds();
      // ����
      for ( String selectedId : selectIds.split( "," ) )
      {

         if ( selectedId != null && !selectedId.equals( "null" ) && !selectedId.isEmpty() )
         {
            employeeContractSBVO.setContractId( selectedId );

            modifyEmployeeContractSBVO( employeeContractSBVO );
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
      final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( employeeContractSBVO.getContractId() );

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
   private int updateEmployeeContractSBPopup( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // ��������
         startTransaction();

         rows = ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( employeeContractSBVO );
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
   public PagedListHolder getVendorEmployeeContractSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeContractSBDao employeeContractSBDao = ( EmployeeContractSBDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBDao.countVendorEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBDao.getVendorEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBDao.getVendorEmployeeContractSBVOsByCondition( ( EmployeeContractSBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   // ��Ա��ְ�̱���������
   // Add by siuvan.xia 2014-06-27
   public int submitEmployeeContractSB_rollback( EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      // �Ƿ�Ϊ�����������
      if ( !WorkflowService.isPassObject( employeeContractSBVO ) )
      {
         // ����HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractSBVO, true );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ��ȡ��Ч�Ĺ�����
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractSBVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContractSB_rollback" );
            historyVO.setObjectId( employeeContractSBVO.getEmployeeSBId() );

            // ���ͨ��
            employeeContractSBVO.setStatus( "5" );
            final String passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();

            // ���δͨ��
            final EmployeeContractSBVO original = ( ( EmployeeContractSBDao ) getDao() ).getEmployeeContractSBVOByEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
            employeeContractSBVO.setStatus( original.getStatus() );
            employeeContractSBVO.setEndDate( null );
            final String failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, employeeContractSBVO );
            return -1;
         }
         else
         {
            employeeContractSBVO.setStatus( "5" );
            ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( employeeContractSBVO );
         }
      }
      else
      {
         ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( employeeContractSBVO );
      }

      return 1;
   }

   @Override
   // ��Ա��ְ�̱���������(����������)
   public int submitEmployeeContractSB_rollback_nt( EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      // �Ƿ�Ϊ�����������
      if ( !WorkflowService.isPassObject( employeeContractSBVO ) )
      {
         // ����HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractSBVO, true );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ��ȡ��Ч�Ĺ�����
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractSBVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContractSB_rollback_nt" );
            historyVO.setObjectId( employeeContractSBVO.getEmployeeSBId() );

            // ���ͨ��
            employeeContractSBVO.setStatus( "5" );
            final String passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();

            // ���δͨ��
            final EmployeeContractSBVO original = ( ( EmployeeContractSBDao ) getDao() ).getEmployeeContractSBVOByEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
            employeeContractSBVO.setStatus( original.getStatus() );
            employeeContractSBVO.setEndDate( null );
            final String failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractSBVO );
            return -1;
         }
         else
         {
            employeeContractSBVO.setStatus( "5" );
            ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( employeeContractSBVO );
         }
      }
      else
      {
         ( ( EmployeeContractSBDao ) getDao() ).updateEmployeeContractSB( employeeContractSBVO );
      }

      return 1;
   }

   @Override
   public List< Object > getEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return ( ( EmployeeContractSBDao ) getDao() ).getFullEmployeeContractSBVOsByCondition( employeeContractSBVO );
   }
}
