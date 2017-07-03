package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractResignDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractResignService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

public class EmployeeContractResignServiceImpl extends ContextService implements EmployeeContractResignService
{
   // 注入CommonBatchDao
   private CommonBatchDao commonBatchDao;

   // 注入EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // 注入EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   // 注入EmployeeContractCBDao
   private EmployeeContractCBDao employeeContractCBDao;

   private EmployeeContractService employeeContractService;

   private EmployeeContractSBService employeeContractSBService;

   private EmployeeContractCBService employeeContractCBService;

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public void setEmployeeContractCBDao( EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   public EmployeeContractService getEmployeeContractService()
   {
      return employeeContractService;
   }

   public void setEmployeeContractService( EmployeeContractService employeeContractService )
   {
      this.employeeContractService = employeeContractService;
   }

   public EmployeeContractSBService getEmployeeContractSBService()
   {
      return employeeContractSBService;
   }

   public void setEmployeeContractSBService( EmployeeContractSBService employeeContractSBService )
   {
      this.employeeContractSBService = employeeContractSBService;
   }

   public EmployeeContractCBService getEmployeeContractCBService()
   {
      return employeeContractCBService;
   }

   public void setEmployeeContractCBService( EmployeeContractCBService employeeContractCBService )
   {
      this.employeeContractCBService = employeeContractCBService;
   }

   @Override
   public PagedListHolder getEmployeeContractResignVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeContractResignDao employeeContractResignDao = ( EmployeeContractResignDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractResignDao.countEmployeeContractResignVOsByCondition( ( EmployeeContractResignVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractResignDao.getEmployeeContractResignVOsByCondition( ( EmployeeContractResignVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractResignDao.getEmployeeContractResignVOsByCondition( ( EmployeeContractResignVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public EmployeeContractResignVO getEmployeeContractResignVOByEmployeeContractResignId( final String employeeContractResignId ) throws KANException
   {
      return ( ( EmployeeContractResignDao ) getDao() ).getEmployeeContractResignVOByEmployeeContractResignId( employeeContractResignId );
   }

   @Override
   public int insertEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return ( ( EmployeeContractResignDao ) getDao() ).insertEmployeeContractResign( employeeContractResignVO );
   }

   @Override
   public int updateEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return ( ( EmployeeContractResignDao ) getDao() ).updateEmployeeContractResign( employeeContractResignVO );
   }

   @Override
   public int deleteEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      int n = 0;
      n = ( ( EmployeeContractResignDao ) getDao() ).deleteEmployeeContractResign( employeeContractResignVO );

      /** 是否需要更新对应 CommonBatch **/
      final String batchId = employeeContractResignVO.getBatchId();

      if ( KANUtil.filterEmpty( batchId ) != null )
      {
         // 初始化查询对象
         EmployeeContractResignVO tempEmployeeContractResignVO = new EmployeeContractResignVO();
         tempEmployeeContractResignVO.setAccountId( employeeContractResignVO.getAccountId() );
         tempEmployeeContractResignVO.setCorpId( employeeContractResignVO.getCorpId() );
         tempEmployeeContractResignVO.setBatchId( batchId );

         // 查询是否存在有效 EmployeeContractResignVO
         final List< Object > employeeContractResignVOs = ( ( EmployeeContractResignDao ) getDao() ).getEmployeeContractResignVOsByCondition( tempEmployeeContractResignVO );

         if ( employeeContractResignVOs == null || employeeContractResignVOs.size() == 0 )
         {
            this.commonBatchDao.deleteCommonBatch( batchId );
         }
      }

      return n;
   }

   @Override
   public void submitEmployeeContractResignDTO( final EmployeeContractResignDTO employeeContractResignDTO ) throws KANException
   {
      try
      {
         //开启事物
         startTransaction();

         // 派送协议离职
         final EmployeeContractVO employeeContractVO = employeeContractResignDTO.getEmployeeContractVO();

         this.employeeContractService.submitEmployeeContract_leave_nt( employeeContractVO );

         // 社保退保
         if ( employeeContractResignDTO.getEmployeeContractSBVOs() != null && employeeContractResignDTO.getEmployeeContractSBVOs().size() > 0 )
         {
            for ( EmployeeContractSBVO employeeContractSBVO : employeeContractResignDTO.getEmployeeContractSBVOs() )
            {
               this.employeeContractSBService.submitEmployeeContractSB_rollback_nt( employeeContractSBVO );
            }
         }

         // 商保退保
         if ( employeeContractResignDTO.getEmployeeContractCBVOs() != null && employeeContractResignDTO.getEmployeeContractCBVOs().size() > 0 )
         {
            for ( EmployeeContractCBVO employeeContractCBVO : employeeContractResignDTO.getEmployeeContractCBVOs() )
            {
               this.employeeContractCBService.submitEmployeeContractCB_rollback_nt( employeeContractCBVO );
            }
         }
         //提交事物
         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         e.printStackTrace();
         throw new KANException( e );
      }
   }

   @Override
   public List< Object > getEmployeeContractResignVOsByCondition( EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return ( ( EmployeeContractResignDao ) getDao() ).getEmployeeContractResignVOsByCondition( employeeContractResignVO );
   }

   @Override
   public int backUpRecord( String[] ids, String batchId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         ( ( EmployeeContractResignDao ) getDao() ).deleteHeaderTempRecord( ids );
         int count = ( ( EmployeeContractResignDao ) getDao() ).getHeaderCountByBatchId( batchId );
         if ( count == 0 )
         {
            commonBatchDao.deleteCommonBatch( batchId );
         }
         // 提交事务
         this.commitTransaction();
         return count;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

}
