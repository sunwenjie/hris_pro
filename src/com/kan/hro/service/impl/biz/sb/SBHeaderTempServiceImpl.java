package com.kan.hro.service.impl.biz.sb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailTempDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDTO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDTOTemp;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderTempService;

public class SBHeaderTempServiceImpl extends ContextService implements SBHeaderTempService
{
   // 注入CommonBatchDao
   private CommonBatchDao commonBatchDao;
   // 注入SBBatchDao
   private SBBatchDao sbBatchDao;
   // 注入SBDetailTempDao
   private SBDetailTempDao sbDetailTempDao;
   // 注入EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;
   // 注入SBHeaderDao
   private SBHeaderDao sbHeaderDao;
   // 注入SBDetailDao
   private SBDetailDao sbDetailDao;
   // 注入EmployeeContractDao
   private EmployeeContractDao employeeContractDao;
   // 注入SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;
   // 注入SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public SBDetailTempDao getSbDetailTempDao()
   {
      return sbDetailTempDao;
   }

   public void setSbDetailTempDao( SBDetailTempDao sbDetailTempDao )
   {
      this.sbDetailTempDao = sbDetailTempDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public SBHeaderDao getSbHeaderDao()
   {
      return sbHeaderDao;
   }

   public void setSbHeaderDao( SBHeaderDao sbHeaderDao )
   {
      this.sbHeaderDao = sbHeaderDao;
   }

   public SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   public SBAdjustmentDetailDao getSbAdjustmentDetailDao()
   {
      return sbAdjustmentDetailDao;
   }

   public void setSbAdjustmentDetailDao( SBAdjustmentDetailDao sbAdjustmentDetailDao )
   {
      this.sbAdjustmentDetailDao = sbAdjustmentDetailDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   /**  
    * GetSBHeaderTempVOsByCondition
    *	 获得　SBHeaderTempVO　分组的SBHeaderTempVO集合
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderTempDao.countSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderTempDao.getSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderTempDao.getSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            countSBHeaderTempVOAmount( sbHeaderTempVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * GetAmountVendorSBHeaderTempVOsByCondition
    *	获得按所有供应商按月份分组后的SBHeaderTempVO集合汇总信息
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getAmountVendorSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderTempDao.countAmountVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderTempDao.getAmountVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderTempDao.getAmountVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            // 添加汇总信息
            fetchAmountVendorPaymentSBHeaderTempVO( sbHeaderTempVO );
         }
      }

      // 获得SBHeaderTempVO 下的

      return pagedListHolder;
   }

   /**  
    * GetVendorSBHeaderTempVOsByCondition
    *	 获得指定供应商的SBHeaderTempVO集合信息
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getVendorSBHeaderTempVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderTempDao.countVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderTempDao.getVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderTempDao.getVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            // 添加数据信息
            fetchVendorPaymentSBHeaderTempVO( sbHeaderTempVO );
         }
      }

      // 获得SBHeaderTempVO 下的

      return pagedListHolder;
   }

   /**  
    * CountSBHeaderTempVOAmount
    * 计算单个SBHeaderTempVO的费用合计
    * @param sbHeaderTempVO
    * @throws KANException
    */
   private void countSBHeaderTempVOAmount( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO.getHeaderId() );

      // 计算数据合计
      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         // 初始化合计值
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
            // 如果状态匹配则叠加
            if ( sbDetailTempVO.getStatus().equals( sbHeaderTempVO.getAdditionalStatus() ) )
            {
               amountCompany = amountCompany.add( new BigDecimal( sbDetailTempVO.getAmountCompany() == null ? "0" : sbDetailTempVO.getAmountCompany() ) );
               amountPersonal = amountPersonal.add( new BigDecimal( sbDetailTempVO.getAmountPersonal() == null ? "0" : sbDetailTempVO.getAmountPersonal() ) );
            }
         }
         sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
         sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );
      }

   }

   /**  
    * CountSBHeaderTempVOAmount
    * 计算单个SBHeaderTempVO的费用合计（查询供应商信息用，不分状态）
    * @param sbHeaderTempVO
    * @throws KANException
    */
   private void countVendorSBHeaderTempVOAmount( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO.getHeaderId() );

      // 计算数据合计
      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         // 初始化合计值
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
            amountCompany = amountCompany.add( new BigDecimal( sbDetailTempVO.getAmountCompany() == null ? "0" : sbDetailTempVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( sbDetailTempVO.getAmountPersonal() == null ? "0" : sbDetailTempVO.getAmountPersonal() ) );
         }
         sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
         sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );
      }

   }

   /**  
    * FetchVendorPaymentSBHeaderTempVO
    * 单个供应商社保信息完善（汇总信息）
    * @param sbHeaderTempVO
    * @throws KANException 
    */
   private void fetchAmountVendorPaymentSBHeaderTempVO( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // 初始化包含的雇员集合
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // 查询对象清除状态
      sbHeaderTempVO.setStatus( "" );
      // 初始化合计值
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // 获得各供应商各月份对应的SBHeaderTempVO 集合
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // 分别计算合计
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object tempSBHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) tempSBHeaderTempVOObject;
            countVendorSBHeaderTempVOAmount( tempSBHeaderTempVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderTempVO.getAmountCompany() == null ? "0" : tempSBHeaderTempVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderTempVO.getAmountPersonal() == null ? "0" : tempSBHeaderTempVO.getAmountPersonal() ) );
            sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
            sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );

            // 雇员集合添加数据
            if ( isEmployeeVOExist( tempSBHeaderTempVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderTempVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderTempVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderTempVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderTempVO.setEmployees( employeeMappingVOs );
      }

   }

   /**  
    * FetchVendorPaymentSBHeaderTempVO
    * 单个供应商社保信息完善（按状态显示）
    * @param sbHeaderTempVO
    * @throws KANException 
    */
   private void fetchVendorPaymentSBHeaderTempVO( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // 初始化包含的雇员集合
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // 获取SBHeaderTempVO的原来状态值
      final String status = sbHeaderTempVO.getStatus();

      // 设置查询状态
      sbHeaderTempVO.setStatus( sbHeaderTempVO.getAdditionalStatus() );
      // 初始化合计值
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // 获得各供应商各月份对应的SBHeaderTempVO 集合
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // 分别计算合计
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object tempSBHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) tempSBHeaderTempVOObject;
            countSBHeaderTempVOAmount( tempSBHeaderTempVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderTempVO.getAmountCompany() == null ? "0" : tempSBHeaderTempVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderTempVO.getAmountPersonal() == null ? "0" : tempSBHeaderTempVO.getAmountPersonal() ) );
            sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
            sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );

            // 雇员集合添加数据
            if ( isEmployeeVOExist( tempSBHeaderTempVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderTempVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderTempVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderTempVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderTempVO.setEmployees( employeeMappingVOs );
      }

      // 还原状态值
      sbHeaderTempVO.setStatus( status );
   }

   /**  
    * IsEmployeeVOExist
    *	判断雇员是否存在
    *	@param tempSBHeaderTempVO
    *	@param employeeMappingVOs
    *	@return
    */
   private boolean isEmployeeVOExist( final SBHeaderTempVO SBHeaderTempVO, final List< MappingVO > employeeMappingVOs )
   {
      if ( employeeMappingVOs != null && employeeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : employeeMappingVOs )
         {
            if ( mappingVO.getMappingId().equals( SBHeaderTempVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public List< Object > getSBHeaderTempVOsByBatchId( final String sbBatchId ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByBatchId( sbBatchId );

      // 计算合计值
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            countSBHeaderTempVOAmount( sbHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public SBHeaderTempVO getSBHeaderTempVOByHeaderId( final String headerId ) throws KANException
   {
      final SBHeaderTempVO sbHeaderTempVO = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOByHeaderId( headerId );

      // 计算合计值
      if ( sbHeaderTempVO != null )
      {
         countSBHeaderTempVOAmount( sbHeaderTempVO );
      }

      return sbHeaderTempVO;
   }

   @Override
   public List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // 计算合计值
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            countSBHeaderTempVOAmount( tempSBHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getAmountVendorSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // 计算合计值
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            fetchAmountVendorPaymentSBHeaderTempVO( tempSBHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getVendorSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // 计算合计值
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            fetchVendorPaymentSBHeaderTempVO( tempSBHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public List< SBDTOTemp > getSBDTOTempsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // 初始化SBDTOTemp List
      final List< SBDTOTemp > sbDTOTemps = new ArrayList< SBDTOTemp >();
      // 初始化SBHeaderTempVO List
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            // 初始化缓存对象
            final SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            // 初始化SBDTOTemp
            final SBDTOTemp sbDTOTemp = new SBDTOTemp();
            // 装载SBHeaderTempVO
            sbDTOTemp.setSbHeaderTempVO( tempSBHeaderTempVO );
            // 装载SBDetailTempVO List
            fetchSBDetailTemp( sbDTOTemp, tempSBHeaderTempVO.getHeaderId(), sbHeaderTempVO.getStatus() );

            sbDTOTemps.add( sbDTOTemp );
         }
      }

      return sbDTOTemps;
   }

   // 装载社保明细
   private void fetchSBDetailTemp( final SBDTOTemp sbDTOTemp, final String headerId, final String status ) throws KANException
   {
      // 初始化并装载社保明细
      final List< Object > sbDetailTempVOs = this.getSbDetailTempDao().getSBDetailTempVOsByHeaderId( headerId );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

            // 只提取符合条件的社保明细
            if ( sbDetailTempVO.getStatus() != null && sbDetailTempVO.getStatus().equals( status ) )
            {
               sbDTOTemp.getSbDetailTempVOs().add( ( SBDetailTempVO ) sbDetailTempVOObject );
            }
         }
      }
   }

   public int updateSBHeaderTemp_nt( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      int num = 0;
      final String sbHeaderId = sbHeaderTempVO.getHeaderId();
      // 查找数据库中合同、月份、社保方案匹配的SBHeaderVO数据
      SBHeaderVO sbHeaderVO = new SBHeaderVO();
      sbHeaderVO.setAccountId( sbHeaderTempVO.getAccountId() );
      sbHeaderVO.setCorpId( sbHeaderTempVO.getCorpId() );
      sbHeaderVO.setVendorId( sbHeaderTempVO.getVendorId() );
      sbHeaderVO.setContractId( sbHeaderTempVO.getContractId() );
      sbHeaderVO.setMonthly( sbHeaderTempVO.getMonthly() );
      sbHeaderVO.setEmployeeSBId( sbHeaderTempVO.getEmployeeSBId() );
      // 状态为“批准”
      sbHeaderVO.setStatus( "2" );
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO );

      // 指定合同、账单月份、状态、社保方案的SBHeaderVO只能有一条
      if ( sbHeaderVOs != null && sbHeaderVOs.size() != 1 )
      {
         return 0;
      }

      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderId );
      // 初始化插入调整数据 SBAdjustmentDTO
      final SBAdjustmentDTO sbAdjustmentDTO = new SBAdjustmentDTO();

      // 如果SBHeaderTemp对应的SBDetailTempVO集合不为空（为空则默认为错误数据，不添加调整数据）
      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         if ( sbHeaderVOs != null )
         {
            // 指定合同、月份、状态、社保方案的SBHeaderVO只能有一条
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );

            fetchSBAdjustmentDTO( sbAdjustmentDTO, sbHeaderTempVO, sbDetailTempVOs, sbHeaderVO );
         }
         else
         {
            fetchSBAdjustmentDTO( sbAdjustmentDTO, sbHeaderTempVO, sbDetailTempVOs, null );
         }
      }

      // 如果 SBAdjustmentDTO有效
      if ( sbAdjustmentDTO.getSbAdjustmentHeaderVO() != null )
      {
         num++;
         // 插入 SBAdjustmentHeaderVO
         this.sbAdjustmentHeaderDao.insertSBAdjustmentHeader( sbAdjustmentDTO.getSbAdjustmentHeaderVO() );

         // 插入 SBAdjustmentDetailVO
         if ( sbAdjustmentDTO.getSbAdjustmentDetailVOs() != null && sbAdjustmentDTO.getSbAdjustmentDetailVOs().size() > 0 )
         {
            for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDTO.getSbAdjustmentDetailVOs() )
            {
               sbAdjustmentDetailVO.setAdjustmentHeaderId( sbAdjustmentDTO.getSbAdjustmentHeaderVO().getAdjustmentHeaderId() );
               sbAdjustmentDetailVO.setModifyBy( sbHeaderTempVO.getModifyBy() );
               sbAdjustmentDetailVO.setModifyDate( sbHeaderTempVO.getModifyDate() );
               this.sbAdjustmentDetailDao.insertSBAdjustmentDetail( sbAdjustmentDetailVO );
            }
         }

      }

      // SBHeaderTempVO标记为已更新
      sbHeaderTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
      ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO );
      // SBHeaderTempVO对应的SBDetailTempVO集合标记为已更新
      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object object : sbDetailTempVOs )
         {
            SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) object;
            tempSBDetailTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
            this.sbDetailTempDao.updateSBDetailTemp( tempSBDetailTempVO );
         }
      }

      // 是否更新 CommonBatch 状态
      tryUpdateCommonBatchVO( sbHeaderTempVO );
      return num;
   }

   @Override
   public int updateSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         int num = updateSBHeaderTemp_nt( sbHeaderTempVO );

         // 提交事务
         this.commitTransaction();
         return num;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   /**  
    * Try Update CommonBatchVO
    *	更新SBHeaderTempVO 对应的 CommonBatchVO
    *	@param sbHeaderTempVO
    *	@throws KANException
    */
   private void tryUpdateCommonBatchVO( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // 初始化查询对象
      final SBHeaderTempVO tempSBHeaderTempVO = new SBHeaderTempVO();
      tempSBHeaderTempVO.setAccountId( sbHeaderTempVO.getAccountId() );
      tempSBHeaderTempVO.setBatchId( sbHeaderTempVO.getBatchId() );
      tempSBHeaderTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_NEW );
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( tempSBHeaderTempVO );

      // 如果SBHeaderTempVO 全部 “已更新”，则对应CommonBatchVO改变为 “已更新”
      if ( sbHeaderTempVOs == null || sbHeaderTempVOs.size() == 0 )
      {
         final CommonBatchVO commonBatchVO = this.commonBatchDao.getCommonBatchVOByBatchId( sbHeaderTempVO.getBatchId() );
         commonBatchVO.setStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
         this.commonBatchDao.updateCommonBatch( commonBatchVO );
      }

   }

   /**  
    * Fetch SBAdjustment DTO
    *	
    *	@param sbAdjustmentDTO
    *	@param sbHeaderTempVO
    *	@param sbDetailTempVOs
    *	@param sbHeaderVO
    *	@throws KANException
    */
   private void fetchSBAdjustmentDTO( final SBAdjustmentDTO sbAdjustmentDTO, final SBHeaderTempVO sbHeaderTempVO, final List< Object > sbDetailTempVOs, final SBHeaderVO sbHeaderVO )
         throws KANException
   {
      // 数据库中SBHeaderVO无数据符合，不创建调整数据
      if ( sbHeaderVO == null )
      {
         // 获得调整Header数据
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = getSBAdjustmentHeaderVO( sbHeaderTempVO );

         // 计算SBAdjustmentHeaderVO的合计值
         for ( Object object : sbDetailTempVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) object;
            sbAdjustmentHeaderVO.addAmountCompany( sbDetailVO.getAmountCompany() );
            sbAdjustmentHeaderVO.addAmountPersonal( sbDetailVO.getAmountPersonal() );
         }
         // 设置 SBAdjustmentHeaderVO
         sbAdjustmentDTO.setSbAdjustmentHeaderVO( sbAdjustmentHeaderVO );

         for ( Object object : sbDetailTempVOs )
         {
            SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) object;
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = getSBAdjustmentDetailVO( tempSBDetailTempVO, null );

            if ( sbAdjustmentDetailVO != null )
            {
               // 添加 SBAdjustmentDetailVO
               sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );
            }
         }

      }
      // 数据库中SBHeaderVO有数据符合
      else
      {
         // 获得调整Header数据
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = getSBAdjustmentHeaderVO( sbHeaderTempVO );
         // 初始化标示是否需要创建调整数据
         boolean neetCreateAdjustmentDate = false;

         for ( Object object : sbDetailTempVOs )
         {
            SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) object;
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = getSBAdjustmentDetailVO( tempSBDetailTempVO, sbHeaderVO );

            if ( sbAdjustmentDetailVO != null )
            {
               neetCreateAdjustmentDate = true;
               // 添加 SBAdjustmentDetailVO
               sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );

               // 计算SBAdjustmentHeaderVO的属性值
               sbAdjustmentHeaderVO.addAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
               sbAdjustmentHeaderVO.addAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );
            }
         }

         if ( neetCreateAdjustmentDate )
         {
            sbAdjustmentHeaderVO.setAmountCompany( sbHeaderTempVO.formatNumber( sbAdjustmentHeaderVO.getAmountCompany() ) );
            sbAdjustmentHeaderVO.setAmountPersonal( sbHeaderTempVO.formatNumber( sbAdjustmentHeaderVO.getAmountPersonal() ) );

            // 设置 SBAdjustmentHeaderVO
            sbAdjustmentDTO.setSbAdjustmentHeaderVO( sbAdjustmentHeaderVO );
         }

      }
   }

   /**  
    * Get BAdjustment Detail VO
    *	根据条件生成调整数据
    *	@param tempSBDetailTempVO
    *	@param sbHeaderVO
    *	@return
    *	@throws KANException
    */
   private SBAdjustmentDetailVO getSBAdjustmentDetailVO( final SBDetailTempVO tempSBDetailTempVO, final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // 初始化需要新建的SBDetailVO
      final SBAdjustmentDetailVO sbAdjustmentDetailVO = new SBAdjustmentDetailVO();
      sbAdjustmentDetailVO.setItemId( tempSBDetailTempVO.getItemId() );
      sbAdjustmentDetailVO.setNameEN( tempSBDetailTempVO.getNameEN() );
      sbAdjustmentDetailVO.setNameZH( tempSBDetailTempVO.getNameZH() );
      sbAdjustmentDetailVO.setMonthly( tempSBDetailTempVO.getMonthly() );
      sbAdjustmentDetailVO.setStatus( "1" );
      sbAdjustmentDetailVO.setAmountCompany( "0" );
      sbAdjustmentDetailVO.setAmountPersonal( "0" );

      /** 查询数据库对应 SBDetailVO数据 */
      final SBDetailVO sbDetailVO = new SBDetailVO();
      sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
      sbDetailVO.setItemId( tempSBDetailTempVO.getItemId() );
      sbDetailVO.setStatus( "2" );
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByCondition( sbDetailVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         for ( Object object : sbDetailVOs )
         {
            final SBDetailVO tempSBDetailVO = ( SBDetailVO ) object;

            if ( tempSBDetailVO.getItemId().equals( sbAdjustmentDetailVO.getItemId() ) )
            {
               sbAdjustmentDetailVO.addAmountCompany( tempSBDetailVO.getAmountCompany() );
               sbAdjustmentDetailVO.addAmountPersonal( tempSBDetailVO.getAmountPersonal() );
            }

         }
      }

      /** 查询数据库中已有 AdjustmentDetailVO数据 */
      final SBAdjustmentDetailVO tempSBAdjustmentDetailVO = new SBAdjustmentDetailVO();
      tempSBAdjustmentDetailVO.setAccountId( sbHeaderVO.getAccountId() );
      tempSBAdjustmentDetailVO.setCorpId( sbHeaderVO.getCorpId() );
      tempSBAdjustmentDetailVO.setItemId( tempSBDetailTempVO.getItemId() );
      tempSBAdjustmentDetailVO.setContractId( sbHeaderVO.getContractId() );
      tempSBAdjustmentDetailVO.setMonthly( sbHeaderVO.getMonthly() );
      tempSBAdjustmentDetailVO.setStatus( "3" );
      final List< Object > sbAdjustmentDetailVOs = this.sbAdjustmentDetailDao.getSBAdjustmentDetailVOsByCondition( tempSBAdjustmentDetailVO );

      if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
      {
         for ( Object object : sbAdjustmentDetailVOs )
         {
            final SBAdjustmentDetailVO tempSBAdjustmentDetailVO1 = ( SBAdjustmentDetailVO ) object;

            if ( tempSBAdjustmentDetailVO1.getItemId().equals( sbAdjustmentDetailVO.getItemId() ) )
            {
               sbAdjustmentDetailVO.addAmountCompany( tempSBAdjustmentDetailVO1.getAmountCompany() );
               sbAdjustmentDetailVO.addAmountPersonal( tempSBAdjustmentDetailVO1.getAmountPersonal() );
            }

         }
      }

      // 格式化数据
      sbAdjustmentDetailVO.setAmountCompany( sbHeaderVO.formatNumber( String.valueOf( sbAdjustmentDetailVO.getAmountCompany() ) ) );
      sbAdjustmentDetailVO.setAmountPersonal( sbHeaderVO.formatNumber( String.valueOf( sbAdjustmentDetailVO.getAmountPersonal() ) ) );

      // 如果数值相等不另外创建调整数据
      if ( ( new BigDecimal( sbAdjustmentDetailVO.getAmountCompany() ).compareTo( new BigDecimal( tempSBDetailTempVO.getAmountCompany() ) ) == 0 )
            && ( new BigDecimal( sbAdjustmentDetailVO.getAmountPersonal() ).compareTo( new BigDecimal( tempSBDetailTempVO.getAmountPersonal() ) ) == 0 ) )
      {
         return null;
      }
      else
      {
         // 获得科目信息
         final ItemVO itemVO = KANConstants.getItemVOByItemId( sbAdjustmentDetailVO.getItemId() );
         sbAdjustmentDetailVO.setItemNo( itemVO.getItemNo() );
         sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
         sbAdjustmentDetailVO.setMonthly( sbHeaderVO.getMonthly() );

         // 设置合计值
         sbAdjustmentDetailVO.setAmountCompany( sbHeaderVO.formatNumber( String.valueOf( Double.valueOf( tempSBDetailTempVO.getAmountCompany() )
               - Double.valueOf( sbAdjustmentDetailVO.getAmountCompany() ) ) ) );
         sbAdjustmentDetailVO.setAmountPersonal( sbHeaderVO.formatNumber( String.valueOf( Double.valueOf( tempSBDetailTempVO.getAmountPersonal() )
               - Double.valueOf( sbAdjustmentDetailVO.getAmountPersonal() ) ) ) );
      }

      return sbAdjustmentDetailVO;
   }

   /**  
    * Get SBAdjustment Header VO
    *	生成调整HeaderVO数据
    *	@param sbHeaderTempVO
    *	@return
    * @throws KANException 
    */
   private SBAdjustmentHeaderVO getSBAdjustmentHeaderVO( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
      // 新增调整Header
      sbAdjustmentHeaderVO.setAccountId( sbHeaderTempVO.getAccountId() );
      sbAdjustmentHeaderVO.setEmployeeSBId( sbHeaderTempVO.getEmployeeSBId() );
      sbAdjustmentHeaderVO.setContractId( sbHeaderTempVO.getContractId() );
      sbAdjustmentHeaderVO.setMonthly( sbHeaderTempVO.getMonthly() );
      sbAdjustmentHeaderVO.setVendorId( sbHeaderTempVO.getVendorId() );
      sbAdjustmentHeaderVO.setStatus( "1" );

      // 查询合同获得调整需要数据
      final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( sbHeaderTempVO.getContractId() );
      sbAdjustmentHeaderVO.setClientId( employeeContractVO.getClientId() );
      sbAdjustmentHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      sbAdjustmentHeaderVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
      sbAdjustmentHeaderVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
      sbAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
      sbAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
      sbAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );

      // 初始化值
      sbAdjustmentHeaderVO.setAmountCompany( "0" );
      sbAdjustmentHeaderVO.setAmountPersonal( "0" );

      return sbAdjustmentHeaderVO;
   }

   @Override
   public int insertSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( ( SBHeaderTempDao ) getDao() ).insertSBHeaderTemp( sbHeaderTempVO );
   }

   @Override
   public int deleteSBHeaderTemp( final String sbHeaderId ) throws KANException
   {
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderId );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object object : sbDetailTempVOs )
         {
            final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) object;
            final String detailId = sbDetailTempVO.getDetailId();
            this.sbDetailTempDao.deleteSBDetailTemp( detailId );
         }
      }
      return ( ( SBHeaderTempDao ) getDao() ).deleteSBHeaderTemp( sbHeaderId );
   }

   /**  
    * Submit
    *	 提交社保方案
    *	@param sbHeaderTempVO
    * @return 
    *	@throws KANException
    */
   @Override
   public int submit( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 获得勾选ID数组
         final String selectedIds = sbHeaderTempVO.getSelectedIds();
         // 获得 PageFlag
         final String pageFlag = sbHeaderTempVO.getPageFlag();
         // 获得AccountId
         final String accountId = sbHeaderTempVO.getAccountId();

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // 分割选择项
            final String[] selectedIdArray = selectedIds.split( "," );
            // 提交记录数
            submitRows = selectedIdArray.length;

            // “确认”修改社保状态为“3”
            final String status = "3";

            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // 按照PageFlag提交
               if ( pageFlag.equalsIgnoreCase( SBHeaderTempService.PAGE_FLAG_HEADER ) )
               {
                  submitHeaderTemp( accountId, selectId, status, sbHeaderTempVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBHeaderTempService.PAGE_FLAG_DETAIL ) )
               {
                  submitDetailTemp( accountId, selectId, status, sbHeaderTempVO.getModifyBy() );
               }

            }

            // 尝试更改其父对象状态
            trySubmitBatch( sbHeaderTempVO.getBatchId(), status, sbHeaderTempVO.getModifyBy() );
         }
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return submitRows;
   }

   // 更改社保方案
   private void submitHeaderTemp( final String accountId, final String headerId, final String status, final String userId ) throws KANException
   {
      // 初始化社保方案明细
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( headerId );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
            submitDetailTemp( accountId, sbDetailTempVO.getDetailId(), status, userId );
         }
      }

      // 更改SBHeaderTempVO
      final SBHeaderTempVO sbHeaderTempVO = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOByHeaderId( headerId );
      sbHeaderTempVO.setStatus( status );
      sbHeaderTempVO.setModifyBy( userId );
      sbHeaderTempVO.setModifyDate( new Date() );
      ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO );

   }

   // 更改社保方案明细
   private void submitDetailTemp( final String accountId, final String detailId, final String status, final String userId ) throws KANException
   {
      final SBDetailTempVO sbDetailTempVO = this.sbDetailTempDao.getSBDetailTempVOByDetailId( detailId );

      if ( sbDetailTempVO != null && sbDetailTempVO.getStatus() != null
            && ( sbDetailTempVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) || "4".equals( status ) ) )
      {
         sbDetailTempVO.setStatus( status );
         sbDetailTempVO.setModifyBy( userId );
         sbDetailTempVO.setModifyDate( new Date() );
         this.sbDetailTempDao.updateSBDetailTemp( sbDetailTempVO );
      }

   }

   // 尝试提交批次 - 提交子对象但不清楚父对象是否提交的情况使用
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // 初始化SBHeaderTempVO
         final SBBatchVO sbBatchVO = this.sbBatchDao.getSBBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
         // 初始化SBHeaderTempVO列表
         final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

         if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
         {
            int headerFlag = 0;

            // 遍历
            for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
            {
               final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;

               // 初始化SBDetailTempVO列表
               final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO.getHeaderId() );

               if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
               {
                  int detailFlag = 0;
                  // 初始化变量（判断是否状态都比要修改的状态值大，如果是不修改状态）
                  int upperCount = 0;

                  // 遍历
                  for ( Object sbDetailTempVOObject : sbDetailTempVOs )
                  {
                     final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

                     if ( KANUtil.filterEmpty( sbDetailTempVO.getStatus() ) != null && Integer.valueOf( sbDetailTempVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                        if ( Integer.valueOf( sbDetailTempVO.getStatus() ) > Integer.valueOf( status ) )
                        {
                           upperCount++;
                        }
                     }
                  }

                  // 如果社保方案明细已全部是需要更改的状态，修改社保方案的状态
                  if ( detailFlag == sbDetailTempVOs.size() )
                  {
                     // 如果社保方案明细已全部是需要更改的状态且状态不是全都比要修改的状态值大，修改社保方案的状态
                     if ( upperCount != sbDetailTempVOs.size() )
                     {
                        sbHeaderTempVO.setStatus( status );
                     }
                     sbHeaderTempVO.setStatus( status );
                     sbHeaderTempVO.setModifyBy( userId );
                     sbHeaderTempVO.setModifyDate( new Date() );
                     ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO );
                     submitRows++;

                     // 商保确认
                     if ( KANUtil.filterEmpty( status ) != null && KANUtil.filterEmpty( status ).equals( "3" ) )
                     {
                        final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderTempVO.getEmployeeSBId() );

                        boolean updated = false;

                        // “待申购”状态变为“正常缴纳”
                        if ( employeeContractSBVO.getStatus().equals( "2" ) )
                        {
                           employeeContractSBVO.setStatus( "3" );
                           updated = true;
                        }
                        // “待退购”状态变为“已退购”
                        else if ( employeeContractSBVO.getStatus().equals( "5" ) )
                        {
                           employeeContractSBVO.setStatus( "6" );
                           updated = true;
                        }

                        if ( updated )
                        {
                           this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
                        }
                     }
                  }
               }

               if ( sbHeaderTempVO.getStatus() != null && !sbHeaderTempVO.getStatus().isEmpty() && Integer.valueOf( sbHeaderTempVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }
            }

            // 如果社保方案已全部是需要更改的状态，修改社保批次的状态
            if ( headerFlag == sbHeaderTempVOs.size() )
            {
               sbBatchVO.setStatus( status );
               sbBatchVO.setModifyBy( userId );
               sbBatchVO.setModifyDate( new Date() );
               this.sbBatchDao.updateSBBatch( sbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   /**  
    * GetSBDTOTempsByCondition
    *	查询有效供应商的SBDTOTemp 
    *	@param sbHeaderTempHolder
    *	@throws KANException
    */
   @Override
   public void getSBDTOTempsByCondition( final PagedListHolder sbHeaderTempHolder ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempHolder.getObject();

      if ( sbHeaderTempVO.getPageFlag() != null )
      {
         // 如果是供应商层
         if ( sbHeaderTempVO.getPageFlag().equals( PAGE_FLAG_VENDOR ) )
         {
            sbHeaderTempHolder.setSource( sbHeaderTempDao.getAmountVendorSBHeaderTempVOsByCondition( sbHeaderTempVO ) );
         }
         // 如果是HeaderTemp层
         else if ( sbHeaderTempVO.getPageFlag().equals( PAGE_FLAG_HEADER ) )
         {
            sbHeaderTempHolder.setSource( sbHeaderTempDao.getVendorSBHeaderTempVOsByCondition( sbHeaderTempVO ) );
         }
      }

      fetchSBDTOTemp( sbHeaderTempHolder );
   }

   /**  
    * FetchSBDTOTemp
    *	
    *	@param sbHeaderTempHolder
    *	@throws KANException
    */
   private void fetchSBDTOTemp( final PagedListHolder sbHeaderTempHolder ) throws KANException
   {
      // 初始化SBDTOTemp List
      final List< Object > sbDTOTemps = new ArrayList< Object >();
      // 初始化包含所有科目的集合
      final List< ItemVO > items = new ArrayList< ItemVO >();
      // 获得PageFlag
      final String pageFlag = ( ( SBHeaderTempVO ) sbHeaderTempHolder.getObject() ).getPageFlag();
      // 初始化SBDTOTemp的 Size
      int size = 0;

      if ( sbHeaderTempHolder.getSource() != null && sbHeaderTempHolder.getSource().size() > 0 )
      {
         if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_VENDOR ) )
         {

            for ( Object amountVendorSBHeaderTempVOObject : sbHeaderTempHolder.getSource() )
            {
               SBHeaderTempVO amountVendorPaymentSBHeaderTempVO = ( SBHeaderTempVO ) amountVendorSBHeaderTempVOObject;
               // 清除查询状态条件
               amountVendorPaymentSBHeaderTempVO.setAdditionalStatus( "" );
               // 分别查询
               final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getVendorSBHeaderTempVOsByCondition( amountVendorPaymentSBHeaderTempVO );

               if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
               {
                  for ( Object vendorPaymentSBHeaderTempVOObject : sbHeaderTempVOs )
                  {
                     SBHeaderTempVO vendorPaymentSBHeaderTempVO = ( SBHeaderTempVO ) vendorPaymentSBHeaderTempVOObject;
                     // 初始化SBDTOTemp
                     final SBDTOTemp sbDTOTemp = new SBDTOTemp();
                     // 装载SBHeaderTempVO
                     sbDTOTemp.setSbHeaderTempVO( vendorPaymentSBHeaderTempVO );
                     // 装载SBDetailTempVO List
                     fetchSBDetailTemp( sbDTOTemp, vendorPaymentSBHeaderTempVO, items );
                     sbDTOTemps.add( sbDTOTemp );

                     size++;
                  }
               }

            }
         }
         else if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_HEADER ) )
         {
            for ( Object vendorPaymentSBHeaderTempVOObject : sbHeaderTempHolder.getSource() )
            {
               SBHeaderTempVO vendorPaymentSBHeaderTempVO = ( SBHeaderTempVO ) vendorPaymentSBHeaderTempVOObject;
               // 初始化SBDTOTemp
               final SBDTOTemp sbDTOTemp = new SBDTOTemp();
               // 装载SBHeaderTempVO
               sbDTOTemp.setSbHeaderTempVO( vendorPaymentSBHeaderTempVO );
               // 装载SBDetailTempVO List
               fetchSBDetailTemp( sbDTOTemp, vendorPaymentSBHeaderTempVO, items );
               sbDTOTemps.add( sbDTOTemp );

               size++;
            }
         }

      }

      // SBDTOTemp不存在科目添加空值
      if ( sbDTOTemps != null && sbDTOTemps.size() > 0 )
      {
         fillSBDTOTemps( sbDTOTemps, items );
      }

      sbHeaderTempHolder.setHolderSize( size );
      sbHeaderTempHolder.setSource( sbDTOTemps );
   }

   // 装载社保明细
   private void fetchSBDetailTemp( final SBDTOTemp sbDTOTemp, final SBHeaderTempVO sbHeaderTempVO, final List< ItemVO > items ) throws KANException
   {
      // 初始化查询对象
      final SBDetailTempVO sbDetailTempVO = new SBDetailTempVO();

      sbDetailTempVO.setHeaderId( sbHeaderTempVO.getHeaderId() );
      sbDetailTempVO.setStatus( sbHeaderTempVO.getAdditionalStatus() );

      // 初始化并装载社保明细
      final List< Object > sbDetailTempVOs = this.getSbDetailTempDao().getSBDetailTempVOsByCondition( sbDetailTempVO );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            final SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

            // 计算合计值
            BigDecimal amountCompany = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderTempVO.getAmountCompany() ) == null ) ? "0" : sbHeaderTempVO.getAmountCompany() ).add( new BigDecimal( tempSBDetailTempVO.getAmountCompany() == null ? "0"
                  : tempSBDetailTempVO.getAmountCompany() ) ) );
            BigDecimal amountPersonal = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderTempVO.getAmountPersonal() ) == null ) ? "0" : sbHeaderTempVO.getAmountPersonal() ).add( new BigDecimal( tempSBDetailTempVO.getAmountPersonal() == null ? "0"
                  : tempSBDetailTempVO.getAmountPersonal() ) ) );

            sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
            sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );

            sbDTOTemp.getSbDetailTempVOs().add( ( SBDetailTempVO ) sbDetailTempVOObject );

            // 初始化科目
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( tempSBDetailTempVO.getItemId() );
            itemVO.setItemType( tempSBDetailTempVO.getItemType() );

            // 如果科目不存在则添加
            if ( !isItemExist( itemVO, items ) )
            {
               items.add( itemVO );
            }

         }

      }
   }

   // SBDTOTemp集合填充不存在科目
   private void fillSBDTOTemps( final List< Object > sbDTOTemps, final List< ItemVO > items )
   {
      for ( ItemVO itemVO : items )
      {
         for ( Object sbDTOTempObject : sbDTOTemps )
         {
            final List< SBDetailTempVO > copyList = new ArrayList< SBDetailTempVO >();
            final SBDTOTemp sbDTOTemp = ( SBDTOTemp ) sbDTOTempObject;
            // 获得SBDTOTemp对应SBHeaderTempVO、SBDetailTempVO集合
            final SBHeaderTempVO sbHeaderTempVO = sbDTOTemp.getSbHeaderTempVO();
            List< SBDetailTempVO > sbDetailTempVOs = sbDTOTemp.getSbDetailTempVOs();
            // COPY已存在SBDTOTemp集合
            copyList.addAll( sbDetailTempVOs );

            // 查找当前SBDetailTempVO是否存在该科目，不存在则添加
            fetchItemExistSbDetailTempVOs( itemVO, sbHeaderTempVO, sbDetailTempVOs, copyList );

            // 重新设置SBDTOTemp的SBDetailTempVO集合
            sbDTOTemp.setSbDetailTempVOs( copyList );
         }
      }

   }

   /**  
    * IsItemExistSbDetailTempVOs
    * 判断ItemVO是否存在社保明细数组
    * @param itemVO
    * @param items
    * @return
    */
   private void fetchItemExistSbDetailTempVOs( final ItemVO itemVO, final SBHeaderTempVO sbHeaderTempVO, final List< SBDetailTempVO > sbDetailTempVOs,
         final List< SBDetailTempVO > returnDetailTempVOs )
   {
      Boolean existFlag = false;

      // 判断科目是否存在
      if ( sbDetailTempVOs == null || sbDetailTempVOs.size() == 0 )
      {
         existFlag = false;
      }
      else
      {
         for ( SBDetailTempVO sbDetailTempVO : sbDetailTempVOs )
         {
            if ( itemVO.getItemId().equals( sbDetailTempVO.getItemId() ) )
            {
               existFlag = true;
               break;
            }
         }
      }

      // 如果科目不存在则添加
      if ( !existFlag )
      {
         // 初始化SBDetailTempVO用于添加
         final SBDetailTempVO tempDetailTempVO = new SBDetailTempVO();
         tempDetailTempVO.setHeaderId( sbHeaderTempVO.getHeaderId() );
         tempDetailTempVO.setStatus( sbHeaderTempVO.getStatus() );
         tempDetailTempVO.setItemId( itemVO.getItemId() );
         tempDetailTempVO.setItemType( itemVO.getItemType() );
         returnDetailTempVOs.add( tempDetailTempVO );
      }
   }

   /**  
    * IsItemExistInItemsList
    * 判断科目是否存在于科目集合
    * @param itemId
    * @param items
    * @return
    */
   private boolean isItemExist( final ItemVO itemVO, final List< ItemVO > items )
   {
      if ( items == null || items.size() == 0 )
      {
         return false;
      }
      else
      {
         for ( ItemVO tempTemVO : items )
         {
            if ( itemVO.getItemId().equals( tempTemVO.getItemId() ) )
            {
               return true;
            }
         }
         return false;
      }
   }

}
