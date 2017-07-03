package com.kan.hro.service.impl.biz.attendance;

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
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetBatchService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction;

public class TimesheetBatchServiceImpl extends ContextService implements TimesheetBatchService
{
   // �������������磬com.kan.base.domain.BaseVO��
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.TimesheetBatchVO";

   // Service Name�����磬Spring�����Bean���� spring�����ļ��� service��ӦBean��ID ��
   public static final String SERVICE_BEAN = "timesheetBatchService";

   // ע��WorkflowService
   private WorkflowService workflowService;

   // ע��TimesheetHeaderService
   private TimesheetHeaderService timesheetHeaderService;

   // ע��TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // ע��EmployeeDao
   private EmployeeDao employeeDao;

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public TimesheetHeaderService getTimesheetHeaderService()
   {
      return timesheetHeaderService;
   }

   public void setTimesheetHeaderService( TimesheetHeaderService timesheetHeaderService )
   {
      this.timesheetHeaderService = timesheetHeaderService;
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
   public PagedListHolder getTimesheetBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final TimesheetBatchDao timesheetBatchDao = ( TimesheetBatchDao ) getDao();
      pagedListHolder.setHolderSize( timesheetBatchDao.countTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( TimesheetBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( batchId );
   }

   @Override
   public int generateTimesheet( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      // ��ʶ���θ���
      int size = 0;

      try
      {
         // ����ִ�п�ʼʱ��
         timesheetBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

         // ��ʼ��TimesheetHeaderVO
         final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
         timesheetHeaderVO.setBatchId( timesheetBatchVO.getBatchId() );
         timesheetHeaderVO.setAccountId( timesheetBatchVO.getAccountId() );
         timesheetHeaderVO.setEmployeeId( timesheetBatchVO.getEmployeeId() );
         timesheetHeaderVO.setEntityId( timesheetBatchVO.getEntityId() );
         timesheetHeaderVO.setBusinessTypeId( timesheetBatchVO.getBusinessTypeId() );
         timesheetHeaderVO.setClientId( timesheetBatchVO.getClientId() );
         timesheetHeaderVO.setCorpId( timesheetBatchVO.getCorpId() );
         timesheetHeaderVO.setOrderId( timesheetBatchVO.getOrderId() );
         timesheetHeaderVO.setEmployeeId( timesheetBatchVO.getEmployeeId() );
         timesheetHeaderVO.setContractId( timesheetBatchVO.getContractId() );
         timesheetHeaderVO.setMonthly( timesheetBatchVO.getMonthly() );
         timesheetHeaderVO.setCreateBy( timesheetBatchVO.getCreateBy() );
         timesheetHeaderVO.setModifyBy( timesheetBatchVO.getModifyBy() );

         // ���ɿ��ڱ�
         int timesheets = this.timesheetHeaderService.generateTimesheet( timesheetHeaderVO, timesheetBatchVO );

         if ( timesheets > 0 )
         {
            size = 1;
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return size;
   }

   @Override
   public int submit_batch( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      // �Ƿ�����������Ķ���
      if ( !WorkflowService.isPassObject( timesheetBatchVO ) )
      {
         // ����historyVO
         final HistoryVO historyVO = generateHistoryVO( timesheetBatchVO );
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( timesheetBatchVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( timesheetBatchVO.getStatus() != null && !timesheetBatchVO.getStatus().trim().equals( "3" ) )
            {
               // ״̬��Ϊ�����
               submit( timesheetBatchVO, "2", true );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submit_batch" );
            historyVO.setObjectId( timesheetBatchVO.getBatchId() );
            String nameZH = timesheetBatchVO.getEmployeeNameTop3();
            if ( KANUtil.filterEmpty( nameZH ) != null && nameZH.split( "��" ).length > 3 )
            {
               nameZH = nameZH + "����";
            }
            historyVO.setNameZH( nameZH );
            historyVO.setNameEN( nameZH );

            // ��׼״̬
            timesheetBatchVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( timesheetBatchVO ).toString();

            // �˻�״̬
            timesheetBatchVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( timesheetBatchVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, timesheetBatchVO );

            return -1;
         }
         // û�й�����
         else
         {
            // ��׼״̬
            return submit( timesheetBatchVO, "3", false );
         }
      }

      return submit( timesheetBatchVO, timesheetBatchVO.getStatus(), true );
   }

   private int submit( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         submit_batch( timesheetBatchVO, status, isWorkflow );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   // �ύ����
   private int submit_batch( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      // ��ʼ��TimesheetHeaderVO�б�
      final List< Object > timesheetHeaderVOs = new ArrayList< Object >();

      // ��ʶ�Ƿ��޸�����
      boolean updated = false;

      // �����¿��ڱ����
      int totalSize = 0;
      // ��ǰ�Ķ����ڱ����
      int currentSize = 0;
      // ԭ�п��ڱ����
      int originalSize = 0;

      // ��ʼ��StringBuffer
      final StringBuffer headerIds = new StringBuffer();
      final StringBuffer employeeName = new StringBuffer();

      // ��ʼ��TimesheetHeaderVO��������
      final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
      tempTimesheetHeaderVO.setAccountId( timesheetBatchVO.getAccountId() );
      tempTimesheetHeaderVO.setBatchId( timesheetBatchVO.getBatchId() );
      tempTimesheetHeaderVO.setCorpId( timesheetBatchVO.getCorpId() );

      timesheetHeaderVOs.addAll( this.getTimesheetHeaderDao().getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );
      totalSize = timesheetHeaderVOs.size();

      timesheetHeaderVOs.clear();
      tempTimesheetHeaderVO.setStatus( status );
      timesheetHeaderVOs.addAll( this.getTimesheetHeaderDao().getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );
      originalSize = timesheetHeaderVOs.size();

      // �������ύ
      if ( isWorkflow )
      {
         // ����Ǵ���ˣ�װ���½����˻�״̬TimesheetHeaderVO
         if ( status.equals( "2" ) )
         {
            tempTimesheetHeaderVO.setStatus( "1,4" );
         }
         // �������׼�����˻أ�װ�ش����״̬TimesheetHeaderVO
         else if ( status.equals( "3" ) || status.equals( "4" ) )
         {
            tempTimesheetHeaderVO.setStatus( "2" );
         }
      }
      // �ǹ������ύ
      else
      {
         // װ���½����˻�״̬TimesheetHeaderVO
         tempTimesheetHeaderVO.setStatus( "1,4" );
      }

      timesheetHeaderVOs.clear();
      timesheetHeaderVOs.addAll( this.getTimesheetHeaderDao().getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );
      currentSize = timesheetHeaderVOs.size();

      updated = totalSize == originalSize + currentSize;

      // �ı�header״̬
      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
            if ( KANUtil.filterEmpty( headerIds.toString() ) == null && KANUtil.filterEmpty( employeeName.toString() ) == null )
            {
               employeeName.append( timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( timesheetHeaderVO.getHeaderId() );
            }
            else
            {
               employeeName.append( "��" + timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( "," + timesheetHeaderVO.getHeaderId() );
            }
            submit_header( timesheetHeaderVO, status, timesheetBatchVO.getModifyBy() );
         }
      }

      // ���ĸ�����״̬
      if ( updated )
      {
         timesheetBatchVO.setStatus( status );
         timesheetBatchVO.setModifyDate( new Date() );
         ( ( TimesheetBatchDao ) getDao() ).updateTimesheetBatch( timesheetBatchVO );
      }

      // ���»�ȡ����TimesheetBatchVO
      TimesheetBatchVO tempTimesheetBatchVO = null;
      if ( KANUtil.filterEmpty( headerIds.toString() ) != null )
      {
         tempTimesheetBatchVO = getTimesheetBatchVOByHeaderIds( headerIds.toString() );
      }

      if ( tempTimesheetBatchVO != null )
      {
         timesheetBatchVO.setCountOrderId( tempTimesheetBatchVO.getCountOrderId() );
         timesheetBatchVO.setCountContractId( tempTimesheetBatchVO.getCountContractId() );
         timesheetBatchVO.setCountHeaderId( tempTimesheetBatchVO.getCountHeaderId() );
         timesheetBatchVO.setTotalWortHours( tempTimesheetBatchVO.getTotalWortHours() );
         timesheetBatchVO.setTotalLeaveHours( tempTimesheetBatchVO.getTotalLeaveHours() );
         timesheetBatchVO.setTotalOTHours( tempTimesheetBatchVO.getTotalOTHours() );
         timesheetBatchVO.setDescription( employeeName.toString() );
      }

      return 0;
   }

   /*�ύ
   private int submit1( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // �ύ����
         submitBatch( timesheetBatchVO, status, isWorkflow );

         // �ύ����
         this.commitTransaction();

         // �������׼�����˻أ�����TimesheetBatchVO��description�ֶ�
         if ( KANUtil.filterEmpty( status ) != null && ( status.equals( "3" ) || status.equals( "4" ) ) )
         {
            // ��ʼ��StringBuffer
            final StringBuffer employeeNameList = new StringBuffer();

            // ��ȡTimesheetHeaderVO�б�
            final List< Object > timesheetHeaderVOs = this.getTimesheetHeaderDao().getTimesheetHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

            // ����TimesheetHeaderVO�б�
            if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
            {
               int size = 0;
               for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
               {
                  final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
                  // ���˻�״̬
                  if ( KANUtil.filterEmpty( timesheetHeaderVO.getStatus() ) != null && !timesheetHeaderVO.getStatus().equals( "4" ) )
                  {
                     // ��ȡEmployeeVO
                     final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( ( ( TimesheetHeaderVO ) timesheetHeaderVOObject ).getEmployeeId() );

                     if ( employeeVO != null )
                     {
                        if ( size == 0 )
                           employeeNameList.append( employeeVO.getNameZH() );
                        else
                           employeeNameList.append( "��" + employeeVO.getNameZH() );
                     }
                  }

                  size++;
               }

               final TimesheetBatchVO tempTimesheetBatchVO = ( ( TimesheetBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( timesheetBatchVO.getBatchId() );
               // �޸�����
               tempTimesheetBatchVO.setDescription( employeeNameList.toString() );
               ( ( TimesheetBatchDao ) getDao() ).updateTimesheetBatch( tempTimesheetBatchVO );
            }

         }
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   /**
    * 
   * submitBatch(�ύ����)  
   * @param  timesheetBatchVO  �ύ����
   * @param  status            �ύ״̬
   * @param  isWorkflow        �Ƿ��߹���������
    */
   /*/ �ύ����
   private void submitBatch( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      // ��ʼ��TimesheetHeaderVO�б�
      final List< Object > timesheetHeaderVOs = new ArrayList< Object >();

      // ��ʼ�����ڱ��ܸ���
      int tsTotalSize = 0;

      // ��ʼ����ǰ״̬���ڱ����
      int tsStatusSize = 0;

      // �Ƿ�ı�����
      boolean updated = false;

      // ��ȡ������������TimesheetHeaderVO
      final List< Object > allTimesheetHeaderVOs = this.timesheetHeaderDao.getTimesheetHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

      tsTotalSize = allTimesheetHeaderVOs != null ? allTimesheetHeaderVOs.size() : 0;

      // ��ʼ��TimesheetHeaderVO��������
      final TimesheetHeaderVO tempSearch = new TimesheetHeaderVO();
      tempSearch.setAccountId( timesheetBatchVO.getAccountId() );
      tempSearch.setBatchId( timesheetBatchVO.getBatchId() );
      tempSearch.setStatus( status );

      // ��ȡԭ�е�ǰ״̬TimesheetHeaderVO
      final List< Object > currStatusTimesheetHeaderVOs = this.timesheetHeaderDao.getTimesheetHeaderVOsByCondition( tempSearch );

      // ԭ�е�ǰ״̬����
      tsStatusSize = currStatusTimesheetHeaderVOs != null ? currStatusTimesheetHeaderVOs.size() : 0;

      // ����ѡ�е�headerIds������header�ύ
      if ( KANUtil.filterEmpty( timesheetBatchVO.getHeaderIds() ) != null )
      {
         // ѡ�е�headerId
         final String[] headerIdArray = timesheetBatchVO.getHeaderIds().split( "," );

         for ( String headerId : headerIdArray )
         {
            timesheetHeaderVOs.add( this.timesheetHeaderDao.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( headerId ) ) );
         }

         updated = tsTotalSize == tsStatusSize + headerIdArray.length;
      }
      // ����batch�ύ
      else
      {
         // �������ύ
         if ( isWorkflow )
         {
            // ����Ǵ����
            if ( status.equals( "2" ) )
            {
               // װ���½����˻�״̬TimesheetHeaderVO
               tempSearch.setStatus( "1,4" );
            }
            // �������׼�����˻�
            else if ( status.equals( "3" ) || status.equals( "4" ) )
            {
               // װ�ش����״̬TimesheetHeaderVO
               tempSearch.setStatus( "2" );
            }
         }
         // �ǹ������ύ
         else
         {
            // װ���½����˻�״̬TimesheetHeaderVO
            tempSearch.setStatus( "1,4" );
         }

         timesheetHeaderVOs.addAll( this.timesheetHeaderDao.getTimesheetHeaderVOsByCondition( tempSearch ) );

         updated = tsTotalSize == tsStatusSize + timesheetHeaderVOs.size();
      }

      // ����TimesheetHeaderVO�б������ύHeader
      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            submitHeader( ( TimesheetHeaderVO ) timesheetHeaderVOObject, status, timesheetBatchVO.getModifyBy() );
         }
      }

      // ���ĸ�����״̬
      if ( updated )
      {
         timesheetBatchVO.setStatus( status );
         timesheetBatchVO.setModifyDate( new Date() );
         ( ( TimesheetBatchDao ) getDao() ).updateTimesheetBatch( timesheetBatchVO );
      }

      // ��������ͳ����Ϣ
      dealTimesheetBatchVO( timesheetBatchVO, timesheetHeaderVOs );
   }

   /* ����TimesheetBatchVO��ͳ����Ϣ
   private void dealTimesheetBatchVO( final TimesheetBatchVO timesheetBatchVO, final List< Object > timesheetHeaderVOs ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer headerIds = new StringBuffer();
      final StringBuffer employeeName = new StringBuffer();

      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
            if ( KANUtil.filterEmpty( headerIds.toString() ) == null && KANUtil.filterEmpty( employeeName.toString() ) == null )
            {
               employeeName.append( timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( timesheetHeaderVO.getHeaderId() );
            }
            else
            {
               employeeName.append( "��" + timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( "," + timesheetHeaderVO.getHeaderId() );
            }
         }
      }

      // ���»�ȡ����TimesheetBatchVO
      final TimesheetBatchVO tempTimesheetBatchVO = getTimesheetBatchVOByHeaderIds( headerIds.toString() );

      if ( tempTimesheetBatchVO != null )
      {
         timesheetBatchVO.setCountOrderId( tempTimesheetBatchVO.getCountOrderId() );
         timesheetBatchVO.setCountContractId( tempTimesheetBatchVO.getCountContractId() );
         timesheetBatchVO.setCountHeaderId( tempTimesheetBatchVO.getCountHeaderId() );
         timesheetBatchVO.setTotalWortHours( tempTimesheetBatchVO.getTotalWortHours() );
         timesheetBatchVO.setTotalLeaveHours( tempTimesheetBatchVO.getTotalLeaveHours() );
         timesheetBatchVO.setTotalOTHours( tempTimesheetBatchVO.getTotalOTHours() );
         timesheetBatchVO.setDescription( employeeName.toString() );
      }

   }*/

   /* �ύHeader
   private void submitHeader( final TimesheetHeaderVO timesheetHeaderVO, final String status, final String userId ) throws KANException
   {
      // ��������Header
      timesheetHeaderVO.setStatus( status );
      timesheetHeaderVO.setModifyBy( userId );
      timesheetHeaderVO.setModifyDate( new Date() );
      this.timesheetHeaderDao.updateTimesheetHeader( timesheetHeaderVO );
   }*/

   // �ύHeader
   private void submit_header( final TimesheetHeaderVO timesheetHeaderVO, final String status, final String userId ) throws KANException
   {
      // ��������Header
      timesheetHeaderVO.setStatus( status );
      timesheetHeaderVO.setModifyBy( userId );
      timesheetHeaderVO.setModifyDate( new Date() );
      this.timesheetHeaderDao.updateTimesheetHeader( timesheetHeaderVO );
   }

   // Generate HistoryVO
   private HistoryVO generateHistoryVO( final TimesheetBatchVO timesheetBatchVO )
   {
      final HistoryVO history = timesheetBatchVO.getHistoryVO();

      history.setAccessAction( TimesheetBatchAction.accessActionInHouse );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( TimesheetBatchAction.accessActionInHouse ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getTimesheetBatchVOByBatchId" );

      // ��ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( timesheetBatchVO.getAccountId() );

      return history;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByHeaderIds( final String headerIds ) throws KANException
   {
      return ( ( TimesheetBatchDao ) getDao() ).getTimesheetBatchVOByHeaderIds( headerIds );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByTimesheetBatchVO( TimesheetBatchVO condition ) throws KANException
   {
      final TimesheetBatchDao timesheetBatchDao = ( TimesheetBatchDao ) getDao();
      List< Object > list = timesheetBatchDao.getTimesheetBatchVOsByCondition( condition );

      if ( list != null && list.size() > 0 )
      {
         return ( TimesheetBatchVO ) list.get( 0 );
      }
      return new TimesheetBatchVO();
   }

}
