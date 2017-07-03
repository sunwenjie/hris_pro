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

   // 注入EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // 注入WorkflowService
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

         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 原始商保状态
         final String originalStatus = employeeContractCBVO.getStatus();

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractCBVO );
         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( originalStatus != null )
            {
               if ( originalStatus.trim().equals( "0" ) )
               {
                  // 状态改为申购提交
                  employeeContractCBVO.setStatus( "1" );
                  updateEmployeeContractCB( employeeContractCBVO );
               }
               else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
               {
                  // 状态改为退购提交
                  employeeContractCBVO.setStatus( "4" );

                  // 这里只修改状态
                  final EmployeeContractCBVO tempEmployeeContractCBVO = ( ( EmployeeContractCBDao ) getDao() ).getEmployeeContractCBVOByEmployeeCBId( employeeContractCBVO.getEmployeeCBId() );
                  tempEmployeeContractCBVO.setStatus( employeeContractCBVO.getStatus() );
                  ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( tempEmployeeContractCBVO );
               }

               // Service的方法
               historyVO.setServiceMethod( "submitEmployeeContractCB" );
               historyVO.setObjectId( employeeContractCBVO.getEmployeeCBId() );
            }

            String passObject = null;
            String failObject = null;

            // 无商保
            if ( originalStatus.trim().equals( "0" ) )
            {
               // 加保申购
               employeeContractCBVO.setStatus( "2" );
               passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
               // 加保申购
               employeeContractCBVO.setStatus( "0" );
               failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
            }
            // 待申购
            else if ( originalStatus.trim().equals( "2" ) )
            {
               // 退购批准
               employeeContractCBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
               // 退购拒绝
               employeeContractCBVO.setStatus( "2" );
               failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
            }
            // 正常购买
            else if ( originalStatus.trim().equals( "3" ) )
            {
               //  退购批准
               employeeContractCBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
               //  退购拒绝
               employeeContractCBVO.setStatus( "3" );
               failObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();
            }

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, employeeContractCBVO );

            return -1;
         }
         // 没有工作流
         else
         {
            // 无商保
            if ( originalStatus.trim().equals( "0" ) )
            {
               employeeContractCBVO.setStatus( "2" );
            }
            // 待申报加保和正常缴纳状态
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
      // “3”表示修改类型工作流
      history.setRemark5( employeeContractCBVO.getStatus().equals( "0" ) ? "0" : "3" );
      history.setAccountId( employeeContractCBVO.getAccountId() );
      history.setNameZH( employeeContractCBVO.getEmployeeNameZH() + " - " + ( leave ? "离职退保  - " : "" ) + employeeContractCBVO.getDecodeSolutionId() );
      history.setNameEN( employeeContractCBVO.getEmployeeNameZH() + " - " + ( leave ? "离职退保  - " : "" ) + employeeContractCBVO.getDecodeSolutionId() );
      return history;
   }

   /**  
    * modifyEmployeeContractCBVO
    *	 模态框 修改 合同商保方案(单条记录)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBVO( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // 获取需要修改的派送协议Contract ID及所要修改的商保方案ID
      final String contractId = employeeContractCBVO.getContractId();
      final String solutionId = employeeContractCBVO.getSolutionId();

      // 初始化查询对象
      final EmployeeContractCBVO employeeContractCBVOTemp = new EmployeeContractCBVO();
      employeeContractCBVOTemp.setContractId( contractId );
      employeeContractCBVOTemp.setSolutionId( solutionId );
      
      employeeContractCBVOTemp.setAccountId( employeeContractCBVO.getAccountId() );
      
      //增加corpId赋值 
      employeeContractCBVOTemp.setCorpId( employeeContractCBVO.getCorpId() );

      //  如果不存在该商保方案类型则添加
      if ( ( ( EmployeeContractCBDao ) getDao() ).countFullEmployeeContractCBVOsByCondition( employeeContractCBVOTemp ) == 0 )
      {
         // 更改操作位添加
         actFlag = "addObject";
         employeeContractCBVO.setCreateBy( employeeContractCBVO.getModifyBy() );
         // 清空“退保日期”
         employeeContractCBVO.setEndDate( null );

         // 获取SubAction判断是否需要提交
         final String subAction = employeeContractCBVO.getSubAction();
         employeeContractCBVO.setStatus( "0" );

         // 如果为“提交”则改状态为“待申购提交”
         if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
         {
            actFlag = "submitObject";
            employeeContractCBVO.setStatus( "2" );
         }

         // 更新日期
         updateDateByCondition( employeeContractCBVO );
         // 新增
         ( ( EmployeeContractCBDao ) getDao() ).insertEmployeeContractCB( employeeContractCBVO );
      }
      // 如果存在则修改
      else
      {
         // 更改操作位添加
         actFlag = "updateObject";

         final List< Object > employeeContractCBVOs = ( ( EmployeeContractCBDao ) getDao() ).getFullEmployeeContractCBVOsByCondition( employeeContractCBVOTemp );

         // 遍历
         for ( Object object : employeeContractCBVOs )
         {
            final EmployeeContractCBVO employeeContractCBVOObject = ( EmployeeContractCBVO ) object;
            employeeContractCBVOObject.setModifyBy( employeeContractCBVO.getModifyBy() );

            // 获得EmployeeContractCBVO 未修改前的状态
            final String status = employeeContractCBVOObject.getStatus();

            // 根据用户选择判断是否需要更新方案数据
            if ( employeeContractCBVO.getApplyToAllHeader() != null && employeeContractCBVO.getApplyToAllHeader().equals( "1" ) )
            {
               employeeContractCBVOObject.update( employeeContractCBVO );
               // 更新日期
               updateDateByCondition( employeeContractCBVOObject );
               // 状态还原
               employeeContractCBVOObject.setStatus( status );
            }

            // 获取SubAction判断是否需要提交
            final String subAction = employeeContractCBVO.getSubAction();

            if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
            {

               // 如果是无社保则“待申购提交”
               if ( status.equals( "0" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "2" );
               }
               // 如果是“待退购”或“正常购买”则“待退购提交”
               else if ( status.equals( "2" ) || status.equals( "3" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "5" );
               }

            }

            // 更新
            ( ( EmployeeContractCBDao ) getDao() ).updateEmployeeContractCB( employeeContractCBVOObject );

         }
      }

      return actFlag;
   }

   /**  
    * ModifyEmployeeContractCBVOs
    *	 模态框 修改 合同商保方案(多条记录)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBVOs( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractCBVO.getSelectedIds();
      // 遍历
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
    * 根据派送协议开始日期和结束日期设置商保日期
    * @param employeeContractCBVO
    * @throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( employeeContractCBVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // 日期格式
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // 判断商保方案开始时间是否在派送信息之前
         if ( employeeContractCBVO.getStartDate() != null && !employeeContractCBVO.getStartDate().isEmpty() )
         {

            final Date cbStartDate = df.parse( employeeContractCBVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( cbStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // 判断商保方案结束时间是否在派送信息之后
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
   // 雇员离职商保方案处理
   // Add by siuvan.xia 2014-06-27
   public int submitEmployeeContractCB_rollback( EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      // 是否为审批过后对象
      if ( !WorkflowService.isPassObject( employeeContractCBVO ) )
      {
         // 生成HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractCBVO, true );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 获取有效的工作流
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractCBVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            // Service的方法
            historyVO.setServiceMethod( "submitEmployeeContractCB_rollback" );
            historyVO.setObjectId( employeeContractCBVO.getEmployeeCBId() );

            // 审核通过
            employeeContractCBVO.setStatus( "5" );
            final String passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();

            // 审核未通过
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
   // 雇员离职商保方案处理(不包含事务)
   public int submitEmployeeContractCB_rollback_nt( EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      // 是否为审批过后对象
      if ( !WorkflowService.isPassObject( employeeContractCBVO ) )
      {
         // 生成HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractCBVO, true );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 获取有效的工作流
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractCBVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            // Service的方法
            historyVO.setServiceMethod( "submitEmployeeContractCB_rollback_nt" );
            historyVO.setObjectId( employeeContractCBVO.getEmployeeCBId() );

            // 审核通过
            employeeContractCBVO.setStatus( "5" );
            final String passObject = KANUtil.toJSONObject( employeeContractCBVO ).toString();

            // 审核未通过
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
