package com.kan.hro.service.impl.biz.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.web.actions.biz.employee.EmployeeContractSalaryAction;

public class EmployeeContractSalaryServiceImpl extends ContextService implements EmployeeContractSalaryService
{
   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO";

   public final String SERVICE_BEAN = "employeeContractSalaryService";

   // ע��EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // ע��WorkflowService
   private WorkflowService workflowService;

   private EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao;

   private LogDao logDao;

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   public EmployeeSalaryAdjustmentDao getEmployeeSalaryAdjustmentDao()
   {
      return employeeSalaryAdjustmentDao;
   }

   public void setEmployeeSalaryAdjustmentDao( EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao )
   {
      this.employeeSalaryAdjustmentDao = employeeSalaryAdjustmentDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
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
   public PagedListHolder getEmployeeContractSalaryVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSalaryDao employeeContractSalaryDao = ( EmployeeContractSalaryDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSalaryDao.countEmployeeContractSalaryVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSalaryDao.getEmployeeContractSalaryVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSalaryDao.getEmployeeContractSalaryVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSalaryVO getEmployeeContractSalaryVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException
   {
      return ( ( EmployeeContractSalaryDao ) getDao() ).getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
   }

   @Override
   public int insertEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return ( ( EmployeeContractSalaryDao ) getDao() ).insertEmployeeContractSalary( employeeContractSalaryVO );
   }

   @Override
   public int updateEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return ( ( EmployeeContractSalaryDao ) getDao() ).updateEmployeeContractSalary( employeeContractSalaryVO );
   }

   @Override
   public int deleteEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      employeeContractSalaryVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractSalaryDao ) getDao() ).updateEmployeeContractSalary( employeeContractSalaryVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractSalaryDao ) getDao() ).getEmployeeContractSalaryVOsByContractId( contractId );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByContractIdAndItemId( Map< String, Object > parameters ) throws KANException
   {
      return ( ( EmployeeContractSalaryDao ) getDao() ).getEmployeeContractSalaryVOsByContractIdAndItemId( parameters );
   }

   /**  
    * InsertEmployeeContractSalaryPopup
    *	 ģ̬���޸�
    *	@param employeeContractSalaryVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public int insertEmployeeContractSalaryPopup( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException
   {
      final String contractId = employeeContractSalaryVO.getContractId();
      final String selectIds = employeeContractSalaryVO.getSelectedIds();

      // �������ӵ�������
      if ( contractId != null && !contractId.trim().isEmpty() )
      {
         employeeContractSalaryVO.setContractId( contractId.split( "-" )[ 0 ] );
         employeeContractSalaryVO.setCreateBy( employeeContractSalaryVO.getModifyBy() );
         // ����״̬Ϊ���½���
         employeeContractSalaryVO.setStatus( "1" );
         insertEmployeeContractSalary( employeeContractSalaryVO );
      }
      else if ( selectIds != null )
      {
         // �ָ�
         for ( String selectedId : selectIds.split( "," ) )
         {
            if ( selectedId != null && !selectedId.equals( "null" ) )
            {
               // ��ʼ��ContractId
               final String tempContractId = selectedId.split( "-" )[ 0 ];

               // ��ʼ����ѯ����
               final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = new EmployeeContractSalaryVO();
               tempEmployeeContractSalaryVO.setContractId( tempContractId );
               // ��ʼ����������Ϣ�µ�����н�귽��
               final int count = ( ( EmployeeContractSalaryDao ) getDao() ).countEmployeeContractSalaryVOsByCondition( tempEmployeeContractSalaryVO );

               // ���������Ϣ��н�귽�������
               if ( count == 0 )
               {
                  employeeContractSalaryVO.setContractId( tempContractId );
                  employeeContractSalaryVO.setCreateBy( employeeContractSalaryVO.getModifyBy() );
                  // ����״̬Ϊ���½���
                  employeeContractSalaryVO.setStatus( "1" );
                  updateDateByCondition( employeeContractSalaryVO );
                  insertEmployeeContractSalary( employeeContractSalaryVO );
               }
               else if ( selectedId.split( "-" ).length > 1 )
               {
                  // �����������Ҷ���
                  final EmployeeContractSalaryVO dbEmployeeContractSalaryVO = ( ( EmployeeContractSalaryDao ) getDao() ).getEmployeeContractSalaryVOByEmployeeSalaryId( selectedId.split( "-" )[ 1 ] );
                  final String status = dbEmployeeContractSalaryVO.getStatus();
                  dbEmployeeContractSalaryVO.update( employeeContractSalaryVO );
                  dbEmployeeContractSalaryVO.setContractId( tempContractId );
                  dbEmployeeContractSalaryVO.setStatus( status );
                  updateDateByCondition( dbEmployeeContractSalaryVO );
                  updateEmployeeContractSalary( dbEmployeeContractSalaryVO );
               }
            }
         }
      }

      return 0;
   }

   /**  
    * UpdateDateByCondition
    *	��������Э�鿪ʼ���ںͽ�����������н������
    *	@param employeeContractSalaryVO
    *	@throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // ���ڸ�ʽ
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // �ж�н�귽����ʼʱ���Ƿ���������Ϣ֮ǰ
         if ( employeeContractSalaryVO.getStartDate() != null && !employeeContractSalaryVO.getStartDate().isEmpty() )
         {

            final Date salaryStartDate = df.parse( employeeContractSalaryVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( salaryStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // �ж�н�귽������ʱ���Ƿ���������Ϣ֮��
         if ( employeeContractSalaryVO.getEndDate() != null && !employeeContractSalaryVO.getEndDate().isEmpty() )
         {
            final Date salaryEndDate = df.parse( employeeContractSalaryVO.getEndDate() );
            final Date contractEndDate = df.parse( employeeContractVO.getEndDate() );

            if ( salaryEndDate.getTime() > contractEndDate.getTime() )
            {
               employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
            }
         }
      }

   }

   @Override
   // Code Reviewed by Siuvan Xia at 2014-12-3
   public boolean hasConflictContractSalaryInOneItem( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException
   {
      final EmployeeContractSalaryVO searchObject = new EmployeeContractSalaryVO();
      searchObject.setAccountId( employeeContractSalaryVO.getAccountId() );
      searchObject.setCorpId( employeeContractSalaryVO.getCorpId() );
      searchObject.setEmployeeId( employeeContractSalaryVO.getEmployeeId() );
      searchObject.setContractId( employeeContractSalaryVO.getContractId() );
      searchObject.setItemId( employeeContractSalaryVO.getItemId() );

      final List< Object > employeeContractSalaryVOs = ( ( EmployeeContractSalaryDao ) getDao() ).getEmployeeContractSalaryVOsByCondition( searchObject );
      long startDateTime = KANUtil.createDate( employeeContractSalaryVO.getStartDate() ).getTime();
      long endDateTime = KANUtil.createDate( employeeContractSalaryVO.getEndDate() ).getTime();
      boolean flag = false;
      for ( Object o : employeeContractSalaryVOs )
      {
         final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = ( EmployeeContractSalaryVO ) o;

         // �ų���ǰ���Ͷ���ͬ��������Э��
         if ( employeeContractSalaryVO.getEmployeeSalaryId() != null && employeeContractSalaryVO.getEmployeeSalaryId().equals( tempEmployeeContractSalaryVO.getEmployeeSalaryId() ) )
         {
            continue;
         }

         // ֻ�ж��п�ʼ�ͽ���ʱ���
         if ( KANUtil.filterEmpty( tempEmployeeContractSalaryVO.getStartDate() ) != null && KANUtil.filterEmpty( tempEmployeeContractSalaryVO.getEndDate() ) != null
               && KANUtil.filterEmpty( employeeContractSalaryVO.getStartDate() ) != null && KANUtil.filterEmpty( employeeContractSalaryVO.getEndDate() ) != null )
         {
            if ( startDateTime > KANUtil.createDate( tempEmployeeContractSalaryVO.getEndDate() ).getTime()
                  || endDateTime < KANUtil.createDate( tempEmployeeContractSalaryVO.getStartDate() ).getTime() )
            {
               flag = false;
            }
            else
            {
               flag = true;
               break;
            }
         }
      }
      return flag;
   }

   @Override
   /***
    * �����ͨ����
    * 1���ı�employeeContractSalaryVO״̬
    * 2������һ�ʵ�н����
    * @param employeeContractSalaryVO
    * @return
    * @throws KANException
    */
   // Add by siuvan.xia @2015-07-27
   public int submitEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {

      // �ı�employeeContractSalaryVO״̬
      ( ( EmployeeContractSalaryDao ) getDao() ).updateEmployeeContractSalaryStatus( employeeContractSalaryVO );

      final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = ( ( EmployeeContractSalaryDao ) getDao() ).getEmployeeContractSalaryVOByEmployeeSalaryId( employeeContractSalaryVO.getEmployeeSalaryId() );

      // ����һ�ʵ�н����
      final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
      employeeSalaryAdjustmentVO.setAccountId( employeeContractSalaryVO.getAccountId() );
      employeeSalaryAdjustmentVO.setCorpId( employeeContractSalaryVO.getCorpId() );
      employeeSalaryAdjustmentVO.setEmployeeId( employeeContractSalaryVO.getEmployeeId() );
      employeeSalaryAdjustmentVO.setContractId( employeeContractSalaryVO.getContractId() );
      employeeSalaryAdjustmentVO.setEmployeeSalaryId( employeeContractSalaryVO.getEmployeeSalaryId() );
      employeeSalaryAdjustmentVO.setOldBase( "0" );
      employeeSalaryAdjustmentVO.setOldStartDate( null );
      employeeSalaryAdjustmentVO.setOldEndDate( null );
      employeeSalaryAdjustmentVO.setNewBase( tempEmployeeContractSalaryVO.getBase() );
      employeeSalaryAdjustmentVO.setNewStartDate( employeeContractSalaryVO.getStartDate() );
      employeeSalaryAdjustmentVO.setNewEndDate( employeeContractSalaryVO.getEndDate() );
      employeeSalaryAdjustmentVO.setEffectiveDate( KANUtil.formatDate( new Date() ) );
      employeeSalaryAdjustmentVO.setDescription( "System auto generate" );
      employeeSalaryAdjustmentVO.setDeleted( "1" );
      // ״̬Ϊ������Ч��
      employeeSalaryAdjustmentVO.setStatus( "5" );
      // �춯ԭ��Ĭ��Ϊ��New Hire��
      employeeSalaryAdjustmentVO.setRemark3( "1" );
      employeeSalaryAdjustmentVO.setCreateBy( employeeContractSalaryVO.getModifyBy() );
      employeeSalaryAdjustmentVO.setModifyBy( employeeContractSalaryVO.getModifyBy() );

      this.getEmployeeSalaryAdjustmentDao().insertEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );

      final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO_DB = this.getEmployeeSalaryAdjustmentDao().getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( employeeSalaryAdjustmentVO.getSalaryAdjustmentId() );
      employeeSalaryAdjustmentVO_DB.setLocale( employeeContractSalaryVO.getLocale() );

      // ������־
      final LogVO log = new LogVO();
      log.setEmployeeId( employeeSalaryAdjustmentVO_DB.getEmployeeId() );
      log.setChangeReason( employeeSalaryAdjustmentVO_DB.getRemark3() );
      log.setEmployeeNameZH( employeeSalaryAdjustmentVO_DB.getEmployeeNameZH() );
      log.setEmployeeNameEN( employeeSalaryAdjustmentVO_DB.getEmployeeNameEN() );
      log.setType( Operate.SUBMIT.getIndex() + "" );
      log.setModule( EmployeeSalaryAdjustmentVO.class.getCanonicalName() );
      log.setContent( employeeSalaryAdjustmentVO_DB == null ? "" : JsonMapper.toLogJson( employeeSalaryAdjustmentVO_DB ) );
      log.setIp( employeeContractSalaryVO.getIp() );
      log.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      log.setOperateBy( employeeContractSalaryVO.getDecodeModifyBy() );
      log.setpKey( employeeSalaryAdjustmentVO_DB.getSalaryAdjustmentId() );
      log.setRemark( "System auto generate" );
      this.getLogDao().insertLog( log );

      return 0;
   }

   @Override
   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException
   {
      // ���ActionFlag
      final HistoryVO history = baseVO.getHistoryVO();
      // ͨ��ִ��
      history.setObjectId( ( ( EmployeeContractSalaryVO ) baseVO ).getEmployeeSalaryId() );
      history.setAccessAction( EmployeeContractSalaryAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeContractSalaryAction.accessAction ) );
      history.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );
      // ��ʾ�����¹�����
      history.setObjectType( "3" );
      history.setServiceBean( "employeeContractSalaryService" );
      history.setNameZH( ( ( EmployeeContractSalaryVO ) baseVO ).getEmployeeNameZH() );
      history.setNameEN( ( ( EmployeeContractSalaryVO ) baseVO ).getEmployeeNameEN() );
   }

}
