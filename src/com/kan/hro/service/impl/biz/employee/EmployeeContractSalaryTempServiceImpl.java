package com.kan.hro.service.impl.biz.employee;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryTempService;

public class EmployeeContractSalaryTempServiceImpl extends ContextService implements EmployeeContractSalaryTempService
{
   // 注入EmployeeContractDao
   private EmployeeContractTempDao employeeContractTempDao;

   public EmployeeContractTempDao getEmployeeContractTempDao()
   {
      return employeeContractTempDao;
   }

   public void setEmployeeContractTempDao( EmployeeContractTempDao employeeContractTempDao )
   {
      this.employeeContractTempDao = employeeContractTempDao;
   }

   @Override
   public PagedListHolder getEmployeeContractSalaryTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSalaryTempDao employeeContractSalaryDao = ( EmployeeContractSalaryTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSalaryDao.countEmployeeContractSalaryTempVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSalaryDao.getEmployeeContractSalaryTempVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSalaryDao.getEmployeeContractSalaryTempVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSalaryVO getEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException
   {
      return ( ( EmployeeContractSalaryTempDao ) getDao() ).getEmployeeContractSalaryTempVOByEmployeeSalaryId( employeeSalaryId );
   }

   @Override
   public PagedListHolder getFullEmployeeContractSalaryTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSalaryTempDao employeeContractSalaryDao = ( EmployeeContractSalaryTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSalaryDao.countFullEmployeeContractSalaryTempVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSalaryDao.getFullEmployeeContractSalaryTempVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSalaryDao.getFullEmployeeContractSalaryTempVOsByCondition( ( EmployeeContractSalaryVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSalaryVO getFullEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException
   {
      return ( ( EmployeeContractSalaryTempDao ) getDao() ).getFullEmployeeContractSalaryTempVOByEmployeeSalaryId( employeeSalaryId );
   }

   @Override
   public int insertEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return ( ( EmployeeContractSalaryTempDao ) getDao() ).insertEmployeeContractSalaryTemp( employeeContractSalaryVO );
   }

   @Override
   public int updateEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return ( ( EmployeeContractSalaryTempDao ) getDao() ).updateEmployeeContractSalaryTemp( employeeContractSalaryVO );
   }

   @Override
   public int deleteEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      employeeContractSalaryVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractSalaryTempDao ) getDao() ).updateEmployeeContractSalaryTemp( employeeContractSalaryVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryTempVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractSalaryTempDao ) getDao() ).getEmployeeContractSalaryTempVOsByContractId( contractId );
   }

   @Override
   public boolean hasConflictContractSalaryTempInOneItem( EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException
   {
      final EmployeeContractSalaryVO contractSalaryVO_forSeach = new EmployeeContractSalaryVO();
      contractSalaryVO_forSeach.setAccountId( employeeContractSalaryVO.getAccountId() );
      contractSalaryVO_forSeach.setClientId( employeeContractSalaryVO.getClientId() );
      contractSalaryVO_forSeach.setCorpId( employeeContractSalaryVO.getCorpId() );
      contractSalaryVO_forSeach.setEmployeeId( employeeContractSalaryVO.getEmployeeId() );
      contractSalaryVO_forSeach.setContractId( employeeContractSalaryVO.getContractId() );
      contractSalaryVO_forSeach.setEmployeeSalaryId( employeeContractSalaryVO.getEmployeeSalaryId() );
      contractSalaryVO_forSeach.setItemId( employeeContractSalaryVO.getItemId() );
      List< Object > employeeContracts = ( ( EmployeeContractSalaryTempDao ) getDao() ).getEmployeeContractSalaryTempVOsByCondition( employeeContractSalaryVO );
      long startDateTime = KANUtil.createDate( employeeContractSalaryVO.getStartDate() ).getTime();
      long endDateTime = KANUtil.createDate( employeeContractSalaryVO.getEndDate() ).getTime();
      long targetStartDateTime = 0;
      long targetEndDateTime = 0;
      boolean flag = false;
      for ( Object o : employeeContracts )
      {
         EmployeeContractSalaryVO contractSalaryVO = ( EmployeeContractSalaryVO ) o;
         // 排除当前的劳动合同或者派送协议
         if ( employeeContractSalaryVO.getEmployeeSalaryId() != null && employeeContractSalaryVO.getEmployeeSalaryId().equals( contractSalaryVO.getEmployeeSalaryId() ) )
         {
            continue;
         }
         if ( contractSalaryVO.getStartDate() != null && !"".equals( contractSalaryVO.getStartDate() ) && contractSalaryVO.getEndDate() != null
               && !"".equals( contractSalaryVO.getEndDate() ) && employeeContractSalaryVO.getStartDate() != null && !"".equals( employeeContractSalaryVO.getStartDate() )
               && employeeContractSalaryVO.getEndDate() != null && !"".equals( employeeContractSalaryVO.getEndDate() ) )
         {
            //只判断有开始和结束时间的
            targetStartDateTime = KANUtil.createDate( contractSalaryVO.getStartDate() ).getTime();
            targetEndDateTime = KANUtil.createDate( contractSalaryVO.getEndDate() ).getTime();

            if ( startDateTime > targetEndDateTime || endDateTime < targetStartDateTime )
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
}
