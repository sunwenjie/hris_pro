package com.kan.hro.service.impl.biz.employee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentService;
import com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentAction;

public class EmployeeSalaryAdjustmentServiceImpl extends ContextService implements EmployeeSalaryAdjustmentService
{
   private EmployeeContractDao employeeContractDao;

   private EmployeeContractSalaryDao employeeContractSalaryDao;

   private EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao;
   
   private ClientOrderHeaderDao clientOrderHeaderDao;

   @Override
   public void getSalaryAdjustmentVOsByCondition( final PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( employeeSalaryAdjustmentDao.countEmployeeSalaryAdjustmentVOsByCondition( ( EmployeeSalaryAdjustmentVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeSalaryAdjustmentDao.getEmployeeSalaryAdjustmentVOsByCondition( ( EmployeeSalaryAdjustmentVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeSalaryAdjustmentDao.getEmployeeSalaryAdjustmentVOsByCondition( ( EmployeeSalaryAdjustmentVO ) pagedListHolder.getObject() ) );
      }
   }

   @Override
   public EmployeeSalaryAdjustmentVO getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( final String salaryAdjustmentId ) throws KANException
   {
      return employeeSalaryAdjustmentDao.getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( salaryAdjustmentId );
   }

   @Override
   public void insertEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      employeeSalaryAdjustmentDao.insertEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
   }

   @Override
   public void updateEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      employeeSalaryAdjustmentDao.updateEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
   }

   @Override
   public void deleteEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      employeeSalaryAdjustmentDao.deleteEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
   }

   @Override
   public void synchronizedEmployeeSalaryContract() throws KANException
   {
      try
      {
         this.startTransaction();
         synchronizedEmployeeSalaryContract_nt();
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   /***
    * 调薪 - 同步
    */
   @Override
   public void synchronizedEmployeeSalaryContract_nt() throws KANException
   {
      KANAccountConstants constants = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID );
      SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
      // 搜索符合生效日期的调薪数据
      EmployeeSalaryAdjustmentVO condition = new EmployeeSalaryAdjustmentVO();
      condition.setStatus( "3" );
      condition.setEffectiveDate( sdf.format( new Date() ) );
      List< Object > objectList = employeeSalaryAdjustmentDao.getEmployeeSalaryAdjustmentVOByStatusAndDate( condition );
      for ( Object object : objectList )
      {
         EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) object;
         boolean synch_success = false;
         String employeeSalaryId = null;
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentVO.getEmployeeSalaryId() ) == null )
         {
            ItemVO itemVO = constants.getItemVOByItemId( employeeSalaryAdjustmentVO.getItemId() );
            final EmployeeContractSalaryVO insert_DB = initEmployeeContractSalary( employeeSalaryAdjustmentVO.getContractId(), employeeSalaryAdjustmentVO.getItemId(), employeeSalaryAdjustmentVO.getCreateBy() );
            insert_DB.setBase( employeeSalaryAdjustmentVO.getNewBase() );
            insert_DB.setStartDate( employeeSalaryAdjustmentVO.getNewStartDate() );
            insert_DB.setEndDate( employeeSalaryAdjustmentVO.getNewEndDate() );
            insert_DB.setModifyBy( employeeSalaryAdjustmentVO.getCreateBy() );
            insert_DB.setModifyDate( new Date() );

            if ( itemVO != null )
            {
               insert_DB.setItemNameZH( itemVO.getNameZH() );
               insert_DB.setItemNameEN( itemVO.getNameEN() );
            }

            if ( employeeContractSalaryDao.insertEmployeeContractSalary( insert_DB ) > 0 )
            {
               synch_success = true;
               employeeSalaryId = insert_DB.getEmployeeSalaryId();
            }
         }
         else
         {
            EmployeeContractSalaryVO update_DB = employeeContractSalaryDao.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryAdjustmentVO.getEmployeeSalaryId() );
            if ( update_DB != null )
            {
               update_DB.setBase( employeeSalaryAdjustmentVO.getNewBase() );
               update_DB.setStartDate( employeeSalaryAdjustmentVO.getNewStartDate() );
               update_DB.setEndDate( employeeSalaryAdjustmentVO.getNewEndDate() );
               update_DB.setModifyBy( employeeSalaryAdjustmentVO.getCreateBy() );
               update_DB.setModifyDate( new Date() );

               if ( employeeContractSalaryDao.updateEmployeeContractSalary( update_DB ) > 0 )
               {
                  synch_success = true;
                  employeeSalaryId = update_DB.getEmployeeSalaryId();
               }
            }
         }

         // 同步成功，更新状态
         if ( synch_success )
         {
            employeeSalaryAdjustmentVO.setEmployeeSalaryId( employeeSalaryId );
            employeeSalaryAdjustmentVO.setStatus( "5" );
            employeeSalaryAdjustmentVO.setModifyDate( new Date() );
            employeeSalaryAdjustmentDao.updateEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
         }
      }
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeSalaryAdjustmentDao getEmployeeSalaryAdjustmentDao()
   {
      return employeeSalaryAdjustmentDao;
   }

   public void setEmployeeSalaryAdjustmentDao( EmployeeSalaryAdjustmentDao employeeSalaryAdjustmentDao )
   {
      this.employeeSalaryAdjustmentDao = employeeSalaryAdjustmentDao;
   }

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao() {
    return clientOrderHeaderDao;
  }

  public void setClientOrderHeaderDao(ClientOrderHeaderDao clientOrderHeaderDao) {
    this.clientOrderHeaderDao = clientOrderHeaderDao;
  }

  @Override
   public void submitEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         employeeSalaryAdjustmentDao.updateEmployeeSalaryAdjustmentStatus( employeeSalaryAdjustmentVO );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException
   {
      // 获得ActionFlag
      final HistoryVO history = baseVO.getHistoryVO();
      //通过执行

      history.setObjectId( ( ( EmployeeSalaryAdjustmentVO ) baseVO ).getSalaryAdjustmentId() );

      history.setAccessAction( EmployeeSalaryAdjustmentAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeSalaryAdjustmentAction.accessAction ) );
      history.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );
      //表示的是新工作流
      history.setObjectType( "3" );
      history.setServiceBean( "employeeSalaryAdjustmentService" );
      history.setNameZH( ( ( EmployeeSalaryAdjustmentVO ) baseVO ).getEmployeeNameZH() );
      history.setNameEN( ( ( EmployeeSalaryAdjustmentVO ) baseVO ).getEmployeeNameEN() );
   }

   @Override
   public int getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId( EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      return employeeSalaryAdjustmentDao.getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId( employeeSalaryAdjustmentVO );
   }

   public EmployeeContractSalaryVO initEmployeeContractSalary( String contractId, String itemId, String userId ) throws KANException
   {
      EmployeeContractSalaryVO vo = new EmployeeContractSalaryVO();
      vo.setContractId( contractId );
      vo.setItemId( itemId );
      vo.setSalaryType( "1" );
      vo.setBase( "0.00" );
      if ( ArrayUtils.contains( CYCLE_12_ITEM_IDS, itemId ) )
         vo.setCycle( "12" );
      else if ( ArrayUtils.contains( CYCLE_3_ITEM_IDS, itemId ) )
         vo.setCycle( "3" );
      else
         vo.setCycle( "1" );
      vo.setStatus( "1" );
      vo.setDeleted( "1" );
      vo.setDescription( "synchronizedEmployeeSalaryContract_nt" );
      vo.setCreateBy( userId );
      vo.setCreateDate( new Date() );
      vo.setResultCap("0");
      vo.setResultFloor("0");
      vo.setShowToTS("2");
      vo.setProbationUsing("2");
      // 
      EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId(contractId);
      if(employeeContractVO!=null){
        ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId(employeeContractVO.getOrderId());
        if(clientOrderHeaderVO!=null){
          vo.setDivideType(clientOrderHeaderVO.getDivideType());
          vo.setExcludeDivideItemIds(clientOrderHeaderVO.getExcludeDivideItemIds());
        }
      }
      return vo;
   }
}
