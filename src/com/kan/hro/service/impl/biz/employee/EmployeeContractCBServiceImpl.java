package com.kan.hro.service.impl.biz.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.web.actions.biz.employee.EmployeeContractCBAction;

public class EmployeeContractCBServiceImpl extends ContextService implements EmployeeContractCBService
{

   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractCBVO";

   public final String SERVICE_BEAN = "employeeContractCBService";

   // ע��EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // ע��WorkflowService
   private WorkflowService workflowService;

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
   public PagedListHolder getEmployeeContractCBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractCBDao employeeContractCBDao = ( EmployeeContractCBDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractCBDao.countEmployeeContractCBVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractCBDao.getEmployeeContractCBVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractCBDao.getEmployeeContractCBVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PagedListHolder getFullEmployeeContractCBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractCBDao employeeContractCBDao = ( EmployeeContractCBDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractCBDao.countFullEmployeeContractCBVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractCBDao.getFullEmployeeContractCBVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractCBDao.getFullEmployeeContractCBVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractCBVO getFullEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( ( EmployeeContractCBDao ) getDao() ).getFullEmployeeContractCBVOByEmployeeCBId( employeeCBId );
   }

   @Override
   public EmployeeContractCBVO getEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOByEmployeeCBId( employeeCBId );
   }

   @Override
   public int insertEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      int rows = 0;
      ( ( EmployeeContractCBDao ) getDao() ).insertEmployeeContractCB( employeeContractCBVO );
      if ( employeeContractCBVO.getSubAction() != null && employeeContractCBVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
      {
         employeeContractCBVO.setDeleted( "1" );
         rows = submitEmployeeContractCB( employeeContractCBVO );
      }

      return rows;
   }

   @Override
   public int updateEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVO );
   }

   @Override
   public int deleteEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      employeeContractCBVO.setDeleted( EmployeeContractCBVO.FALSE );
      return updateEmployeeContractCB( employeeContractCBVO );
   }

   @Override
   public List< Object > getEmployeeContractCBVOsByContractId( final String contractId ) throws KANException
   {
      if ( contractId == null || contractId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOsByContractId( contractId );
      }
   }

   @Override
   // AddAdded Kevin Jin at 2013-11-21
   public int submitEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( employeeContractCBVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( employeeContractCBVO, false );

         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ԭʼ�̱�״̬
         final String originalStatus = employeeContractCBVO.getStatus();

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractCBVO );
         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( originalStatus != null )
            {
               if ( originalStatus.trim().equals( "0" ) )
               {
                  // ״̬��Ϊ�깺�ύ
                  employeeContractCBVO.setStatus( "1" );
                  updateEmployeeContractCB( employeeContractCBVO );
               }
               else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
               {
                  // ״̬��Ϊ�˹��ύ
                  employeeContractCBVO.setStatus( "4" );

                  // ����ֻ�޸�״̬
                  final EmployeeContractCBVO tempEmployeeContractCBVO = ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOByEmployeeCBId( employeeContractCBVO.getEmployeeCBId() );
                  tempEmployeeContractCBVO.setStatus( employeeContractCBVO.getStatus() );
                  ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( tempEmployeeContractCBVO );
               }

               // Service�ķ���
               historyVO.setServiceMethod( "submitEmployeeContractCB" );
               historyVO.setObjectId( employeeContractCBVO.getEmployeeCBId() );
            }

            String passObject = null;
            String failObject = null;

            // ���̱�
            if ( originalStatus.trim().equals( "0" ) )
            {
               // �ӱ��깺
               employeeContractCBVO.setStatus( "2" );
               passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
               // �ӱ��깺
               employeeContractCBVO.setStatus( "0" );
               failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
            }
            // ���깺
            else if ( originalStatus.trim().equals( "2" ) )
            {
               // �˹���׼
               employeeContractCBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
               // �˹��ܾ�
               employeeContractCBVO.setStatus( "2" );
               failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
            }
            // ��������
            else if ( originalStatus.trim().equals( "3" ) )
            {
               //  �˹���׼
               employeeContractCBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
               //  �˹��ܾ�
               employeeContractCBVO.setStatus( "3" );
               failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
            }

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, employeeContractCBVO );

            return -1;
         }
         // û�й�����
         else
         {
            // ���̱�
            if ( originalStatus.trim().equals( "0" ) )
            {
               employeeContractCBVO.setStatus( "2" );
            }
            // ���걨�ӱ�����������״̬
            else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
            {
               employeeContractCBVO.setStatus( "5" );
            }

            return updateEmployeeContractCB( employeeContractCBVO );
         }
      }

      return updateEmployeeContractCB( employeeContractCBVO );
   }

   // Reviewed by Kevin Jin at 2013-11-21
   private HistoryVO generateHistoryVO( final EmployeeContractCBVO employeeContractCBVO, final boolean leave ) throws KANException
   {
      final HistoryVO history = employeeContractCBVO.getHistoryVO();
      history.setAccessAction( EmployeeContractCBAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeContractCBAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getEmployeeContractCBVOByEmployeeCBId" );
      history.setObjectType( "2" );
      // ��3����ʾ�޸����͹�����
      history.setRemark5( employeeContractCBVO.getStatus().equals( "0" ) ? "0" : "3" );
      history.setAccountId( employeeContractCBVO.getAccountId() );
      history.setNameZH( employeeContractCBVO.getEmployeeNameZH() + " - " + ( leave ? "��ְ�˱�  - " : "" ) + employeeContractCBVO.getDecodeSolutionId() );
      history.setNameEN( employeeContractCBVO.getEmployeeNameZH() + " - " + ( leave ? "��ְ�˱�  - " : "" ) + employeeContractCBVO.getDecodeSolutionId() );
      return history;
   }

   /**  
    * modifyEmployeeContractCBVO
    *	 ģ̬�� �޸� ��ͬ�̱�����(������¼)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBVO( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // ��ȡ��Ҫ�޸ĵ�����Э��Contract ID����Ҫ�޸ĵ��̱�����ID
      final String contractId = employeeContractCBVO.getContractId();
      final String solutionId = employeeContractCBVO.getSolutionId();

      // ��ʼ����ѯ����
      final EmployeeContractCBVO employeeContractCBVOTemp = new EmployeeContractCBVO();
      employeeContractCBVOTemp.setContractId( contractId );
      employeeContractCBVOTemp.setSolutionId( solutionId );
      
      employeeContractCBVOTemp.setAccountId( employeeContractCBVO.getAccountId() );
      
      //����corpId��ֵ 
      employeeContractCBVOTemp.setCorpId( employeeContractCBVO.getCorpId() );

      //  ��������ڸ��̱��������������
      if ( ( ( EmployeeContractCBDao ) getDao() ).countFullEmployeeContractCBVOsByCondition( employeeContractCBVOTemp ) == 0 )
      {
         // ���Ĳ���λ���
         actFlag = "addObject";
         employeeContractCBVO.setCreateBy( employeeContractCBVO.getModifyBy() );
         // ��ա��˱����ڡ�
         employeeContractCBVO.setEndDate( null );

         // ��ȡSubAction�ж��Ƿ���Ҫ�ύ
         final String subAction = employeeContractCBVO.getSubAction();
         employeeContractCBVO.setStatus( "0" );

         // ���Ϊ���ύ�����״̬Ϊ�����깺�ύ��
         if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
         {
            actFlag = "submitObject";
            employeeContractCBVO.setStatus( "2" );
         }

         // ��������
         updateDateByCondition( employeeContractCBVO );
         // ����
         ( ( EmployeeContractCBDao ) getDao() ).insertEmployeeContractCB( employeeContractCBVO );
      }
      // ����������޸�
      else
      {
         // ���Ĳ���λ���
         actFlag = "updateObject";

         final List< Object > employeeContractCBVOs = ( ( EmployeeContractCBDao ) getDao() ).getFullEmployeeContractCBVOsByCondition( employeeContractCBVOTemp );

         // ����
         for ( Object object : employeeContractCBVOs )
         {
            final EmployeeContractCBVO employeeContractCBVOObject = ( EmployeeContractCBVO ) object;
            employeeContractCBVOObject.setModifyBy( employeeContractCBVO.getModifyBy() );

            // ���EmployeeContractCBVO δ�޸�ǰ��״̬
            final String status = employeeContractCBVOObject.getStatus();

            // �����û�ѡ���ж��Ƿ���Ҫ���·�������
            if ( employeeContractCBVO.getApplyToAllHeader() != null && employeeContractCBVO.getApplyToAllHeader().equals( "1" ) )
            {
               employeeContractCBVOObject.update( employeeContractCBVO );
               // ��������
               updateDateByCondition( employeeContractCBVOObject );
               // ״̬��ԭ
               employeeContractCBVOObject.setStatus( status );
            }

            // ��ȡSubAction�ж��Ƿ���Ҫ�ύ
            final String subAction = employeeContractCBVO.getSubAction();

            if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
            {

               // ��������籣�򡰴��깺�ύ��
               if ( status.equals( "0" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "2" );
               }
               // ����ǡ����˹��������������򡰴��˹��ύ��
               else if ( status.equals( "2" ) || status.equals( "3" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "5" );
               }

            }

            // ����
            ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVOObject );

         }
      }

      return actFlag;
   }

   /**  
    * ModifyEmployeeContractCBVOs
    *	 ģ̬�� �޸� ��ͬ�̱�����(������¼)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBVOs( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractCBVO.getSelectedIds();
      // ����
      for ( String selectedId : selectIds.split( "," ) )
      {

         if ( selectedId != null && !selectedId.equals( "null" ) && !selectedId.isEmpty() )
         {
            employeeContractCBVO.setContractId( selectedId );

            modifyEmployeeContractCBVO( employeeContractCBVO );
         }

      }
      return null;
   }

   /**  
    * UpdateDateByCondition
    * ��������Э�鿪ʼ���ںͽ������������̱�����
    * @param employeeContractCBVO
    * @throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( employeeContractCBVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // ���ڸ�ʽ
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // �ж��̱�������ʼʱ���Ƿ���������Ϣ֮ǰ
         if ( employeeContractCBVO.getStartDate() != null && !employeeContractCBVO.getStartDate().isEmpty() )
         {

            final Date cbStartDate = df.parse( employeeContractCBVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( cbStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // �ж��̱���������ʱ���Ƿ���������Ϣ֮��
         if ( employeeContractCBVO.getEndDate() != null && !employeeContractCBVO.getEndDate().isEmpty() )
         {
            final Date cbEndDate = df.parse( employeeContractCBVO.getEndDate() );
            final Date contractEndDate = df.parse( employeeContractVO.getEndDate() );

            if ( cbEndDate.getTime() > contractEndDate.getTime() )
            {
               employeeContractCBVO.setEndDate( employeeContractVO.getEndDate() );
            }

         }

      }

   }

   @Override
   // ��Ա��ְ�̱���������
   // Add by siuvan.xia 2014-06-27
   public int submitEmployeeContractCB_rollback( EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      // �Ƿ�Ϊ�����������
      if ( !WorkflowService.isPassObject( employeeContractCBVO ) )
      {
         // ����HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractCBVO, true );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ��ȡ��Ч�Ĺ�����
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractCBVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContractCB_rollback" );
            historyVO.setObjectId( employeeContractCBVO.getEmployeeCBId() );

            // ���ͨ��
            employeeContractCBVO.setStatus( "5" );
            final String passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();

            // ���δͨ��
            final EmployeeContractCBVO original = ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOByEmployeeCBId( employeeContractCBVO.getEmployeeCBId() );
            employeeContractCBVO.setStatus( original.getStatus() );
            employeeContractCBVO.setEndDate( null );
            final String failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, employeeContractCBVO );
            return -1;
         }
         else
         {
            employeeContractCBVO.setStatus( "5" );
            ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVO );
         }
      }
      else
      {
         ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVO );
      }

      return 1;
   }

   @Override
   // ��Ա��ְ�̱���������(����������)
   public int submitEmployeeContractCB_rollback_nt( EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      // �Ƿ�Ϊ�����������
      if ( !WorkflowService.isPassObject( employeeContractCBVO ) )
      {
         // ����HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractCBVO, true );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ��ȡ��Ч�Ĺ�����
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractCBVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContractCB_rollback_nt" );
            historyVO.setObjectId( employeeContractCBVO.getEmployeeCBId() );

            // ���ͨ��
            employeeContractCBVO.setStatus( "5" );
            final String passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();

            // ���δͨ��
            final EmployeeContractCBVO original = ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOByEmployeeCBId( employeeContractCBVO.getEmployeeCBId() );
            employeeContractCBVO.setStatus( original.getStatus() );
            employeeContractCBVO.setEndDate( null );
            final String failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractCBVO );
            return -1;
         }
         else
         {
            employeeContractCBVO.setStatus( "5" );
            ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVO );
         }
      }
      else
      {
         ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVO );
      }

      return 1;
   }

   @Override
   public List< Object > getEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOsByCondition( employeeContractCBVO );
   }
}
