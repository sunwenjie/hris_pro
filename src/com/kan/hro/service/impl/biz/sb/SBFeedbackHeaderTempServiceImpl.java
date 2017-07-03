package com.kan.hro.service.impl.biz.sb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
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
import com.kan.hro.service.inf.biz.sb.SBFeedbackHeaderTempService;
import com.kan.hro.service.inf.biz.sb.SBHeaderTempService;

public class SBFeedbackHeaderTempServiceImpl extends ContextService implements SBFeedbackHeaderTempService
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

   // 调整Detail集合生成调整DTO 集合
   private List< SBAdjustmentDTO > fetchSBAdjustmentDTO( final List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs ) throws KANException
   {
      List< SBAdjustmentDTO > sbAdjustmentDTOs = new ArrayList< SBAdjustmentDTO >();

      if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
      {
         my: for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDetailVOs )
         {

            if ( sbAdjustmentDTOs.size() > 0 )
            {
               for ( SBAdjustmentDTO sbAdjustmentDTO : sbAdjustmentDTOs )
               {
                  final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentDTO.getSbAdjustmentHeaderVO();

                  // ContractId, Monthly, EmployeeSBId 是否匹配
                  if ( sbAdjustmentHeaderVO.getContractId().equals( sbAdjustmentDetailVO.getContractId() )
                        && sbAdjustmentHeaderVO.getMonthly().equals( sbAdjustmentDetailVO.getMonthly() )
                        && sbAdjustmentHeaderVO.getEmployeeSBId().equals( sbAdjustmentDetailVO.getEmployeeSBId() ) )
                  {
                     // 判断SBAdjustmentDetailVO的 AccountMonthly 和 ItemId是否匹配
                     final List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs2 = sbAdjustmentDTO.getSbAdjustmentDetailVOs();

                     for ( SBAdjustmentDetailVO sbAdjustmentDetailVO2 : sbAdjustmentDetailVOs2 )
                     {
                        if ( sbAdjustmentDetailVO2.getItemId().equals( sbAdjustmentDetailVO.getItemId() )
                              && sbAdjustmentDetailVO2.getAccountMonthly().equals( sbAdjustmentDetailVO.getAccountMonthly() ) )
                        {
                           // Detail 合计值添加
                           sbAdjustmentDetailVO.addAmountCompany( sbAdjustmentDetailVO2.getAmountCompany() );
                           sbAdjustmentDetailVO.addAmountPersonal( sbAdjustmentDetailVO2.getAmountPersonal() );

                           // Header 合计值添加
                           sbAdjustmentHeaderVO.addAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
                           sbAdjustmentHeaderVO.addAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );
                           continue my;
                        }
                     }

                     // 合计值添加
                     sbAdjustmentHeaderVO.addAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
                     sbAdjustmentHeaderVO.addAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );
                     sbAdjustmentDetailVOs2.add( sbAdjustmentDetailVO );
                     continue my;
                  }

               }

               // ContractId, Monthly, EmployeeSBId 不匹配
               final SBAdjustmentDTO sbAdjustmentDTO = new SBAdjustmentDTO();
               final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = getSBAdjustmentHeaderVO( sbAdjustmentDetailVO );
               sbAdjustmentDTO.setSbAdjustmentHeaderVO( sbAdjustmentHeaderVO );
               sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );
               sbAdjustmentDTOs.add( sbAdjustmentDTO );
               continue;
            }

            // sbAdjustmentDTOs为空
            final SBAdjustmentDTO sbAdjustmentDTO = new SBAdjustmentDTO();
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = getSBAdjustmentHeaderVO( sbAdjustmentDetailVO );
            sbAdjustmentDTO.setSbAdjustmentHeaderVO( sbAdjustmentHeaderVO );
            sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );
            sbAdjustmentDTOs.add( sbAdjustmentDTO );
         }
      }

      return sbAdjustmentDTOs;
   }

   // 通过SBAdjustmentDetailVO创建SBAdjustmentHeaderVO
   private SBAdjustmentHeaderVO getSBAdjustmentHeaderVO( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
      sbAdjustmentHeaderVO.setAccountId( sbAdjustmentDetailVO.getAccountId() );
      sbAdjustmentHeaderVO.setCorpId( sbAdjustmentDetailVO.getCorpId() );
      sbAdjustmentHeaderVO.setContractId( sbAdjustmentDetailVO.getContractId() );
      sbAdjustmentHeaderVO.setMonthly( sbAdjustmentDetailVO.getMonthly() );
      sbAdjustmentHeaderVO.setEmployeeSBId( sbAdjustmentDetailVO.getEmployeeSBId() );
      sbAdjustmentHeaderVO.setAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
      sbAdjustmentHeaderVO.setAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );

      // 查询对应EmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( sbAdjustmentDetailVO.getContractId() );
      sbAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );
      sbAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
      sbAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
      sbAdjustmentHeaderVO.setClientId( employeeContractVO.getClientId() );
      sbAdjustmentHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );

      sbAdjustmentHeaderVO.setStatus( "1" );

      return sbAdjustmentHeaderVO;
   }

   // 生成调整Detail集合
   private List< SBAdjustmentDetailVO > getSBAdjustmentDetailVOs( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // 初始化返回值
      List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs = new ArrayList< SBAdjustmentDetailVO >();

      /** 查询数据库中已有 SBDetailVO 集合 */
      // 初始化返回集
      List< Object > sbDetailVOs = new ArrayList< Object >();

      final SBHeaderVO tempSBHeaderVO = new SBHeaderVO();
      tempSBHeaderVO.setAccountId( sbHeaderTempVO.getAccountId() );
      tempSBHeaderVO.setCorpId( sbHeaderTempVO.getCorpId() );
      tempSBHeaderVO.setContractId( sbHeaderTempVO.getContractId() );
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( tempSBHeaderVO );

      /** 如果SBHeaderVO所属月份有社保类型社保方案，则公积金和其他类型社保方案不做对比（1、社保；2、公积金；3、综合；4、其他） */
      // 初始化是否包含社保类型社保方案
      boolean includeSBType = false;

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 1 )
      {
         // 查看是否包含社保类型的社保方案
         for ( Object object : sbHeaderVOs )
         {
            SBHeaderVO sbHeaderVO = ( SBHeaderVO ) object;
            if ( sbHeaderVO.getEmployeeSBId() != null )
            {
               final EmployeeContractSBVO employeeContractSBVO = this.employeeContractSBDao.getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( sbHeaderTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );
               if ( socialBenefitSolutionDTO != null
                     && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null
                     && ( "1".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) || "3".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) ) )
               {
                  includeSBType = true;
                  break;
               }
            }
         }
      }

      // 如果包含社保类型社保方案，移除公积金及其他类型社保方案
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 1 )
      {
         if ( includeSBType )
         {
            Iterator< Object > sbHeaderObjectVOIte = sbHeaderVOs.iterator();
            while ( sbHeaderObjectVOIte.hasNext() )
            {
               Object object = ( Object ) sbHeaderObjectVOIte.next();
               SBHeaderVO sbHeaderVO = ( SBHeaderVO ) object;
               if ( sbHeaderVO.getEmployeeSBId() != null )
               {
                  final EmployeeContractSBVO employeeContractSBVO = this.employeeContractSBDao.getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );
                  final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( sbHeaderTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );
                  if ( socialBenefitSolutionDTO != null
                        && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null
                        && ( "2".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) || "4".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) ) )
                  {
                     sbHeaderObjectVOIte.remove();
                  }
               }
            }
         }
      }

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object object : sbHeaderVOs )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) object;
            final SBDetailVO tempSBDetailVO = new SBDetailVO();
            tempSBDetailVO.setAccountId( sbHeaderTempVO.getAccountId() );
            tempSBDetailVO.setCorpId( sbHeaderTempVO.getCorpId() );
            tempSBDetailVO.setContractId( sbHeaderTempVO.getContractId() );
            tempSBDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
            tempSBDetailVO.setStatus( "1,2,3,4" );
            final List< Object > tempSBDetailVOs = this.sbDetailDao.getSBDetailVOsByCondition( tempSBDetailVO );
            sbDetailVOs.addAll( tempSBDetailVOs );
         }
      }

      /** 查询申报反馈表 SBDetailVO 集合*/
      final List< Object > sbDetailTempVOs = fetchSBTempDetailVOs( sbHeaderTempVO );
      // 取月份同意设置账单月份
      final String monthly = sbHeaderTempVO.getMonthly();

      // 初始化所有补缴月份集合
      final Set< String > accountMonthlys = new HashSet< String >();

      // 查找 ContractId, AccountMonthly, ItemId匹配项
      for ( Object sbDetailTempVOObject : sbDetailTempVOs )
      {
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
         accountMonthlys.add( sbDetailTempVO.getAccountMonthly() );

         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            if ( sbDetailVO.getContractId().equals( sbDetailTempVO.getContractId() ) && sbDetailVO.getItemId().equals( sbDetailTempVO.getItemId() )
                  && sbDetailVO.getAccountMonthly().equals( sbDetailTempVO.getAccountMonthly() ) )
            {
               final SBAdjustmentDetailVO sbAdjustmentDetailVO = createSBAdjustmentDetailVO( sbDetailTempVO, sbHeaderTempVO.getAccountId() );

               sbAdjustmentDetailVO.setStatus( "1" );

               // 计算调整值（ -(系统-导入)）
               final String amountCompany = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountCompany() ) - Double.valueOf( sbDetailVO.getAmountCompany() ) );
               final String amountPersonal = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountPersonal() ) - Double.valueOf( sbDetailVO.getAmountPersonal() ) );
               sbAdjustmentDetailVO.setAmountCompany( amountCompany );
               sbAdjustmentDetailVO.setAmountPersonal( amountPersonal );
               sbAdjustmentDetailVOs.add( sbAdjustmentDetailVO );

               // 标记已修改
               sbDetailVO.setUpdataed( true );
               sbDetailTempVO.setUpdataed( true );
            }
         }
      }

      // 数据库中存在未标记的
      for ( Object sbDetailVOObject : sbDetailVOs )
      {
         SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;
         if ( !sbDetailVO.isUpdataed() && accountMonthlys != null && accountMonthlys.contains( sbDetailVO.getAccountMonthly() ) )
         {
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = createSBAdjustmentDetailVO( sbDetailVO, sbHeaderTempVO.getAccountId() );
            sbAdjustmentDetailVO.setStatus( "1" );

            // 计算调整值（ -(系统-导入)）
            final String amountCompany = String.valueOf( Double.valueOf( "0" ) - Double.valueOf( sbDetailVO.getAmountCompany() ) );
            final String amountPersonal = String.valueOf( Double.valueOf( "0" ) - Double.valueOf( sbDetailVO.getAmountPersonal() ) );
            sbAdjustmentDetailVO.setAmountCompany( amountCompany );
            sbAdjustmentDetailVO.setAmountPersonal( amountPersonal );

            sbAdjustmentDetailVOs.add( sbAdjustmentDetailVO );
         }
      }

      // 导入表中存在未标记的
      for ( Object sbDetailTempVOObject : sbDetailTempVOs )
      {
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
         if ( !sbDetailTempVO.isUpdataed() && accountMonthlys != null && accountMonthlys.contains( sbDetailTempVO.getAccountMonthly() ) )
         {
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = createSBAdjustmentDetailVO( sbDetailTempVO, sbHeaderTempVO.getAccountId() );
            sbAdjustmentDetailVO.setStatus( "1" );

            // 计算调整值（ -(系统-导入)）
            final String amountCompany = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountCompany() ) );
            final String amountPersonal = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountPersonal() ) );
            sbAdjustmentDetailVO.setAmountCompany( amountCompany );
            sbAdjustmentDetailVO.setAmountPersonal( amountPersonal );

            sbAdjustmentDetailVOs.add( sbAdjustmentDetailVO );
         }
      }

      // 统一设置调整数据账单月份
      if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
      {
         for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDetailVOs )
         {
            sbAdjustmentDetailVO.setMonthly( monthly );
         }
      }

      return sbAdjustmentDetailVOs;
   }

   private SBAdjustmentDetailVO createSBAdjustmentDetailVO( final SBDetailVO sbDetailVO, final String accountId )
   {
      SBAdjustmentDetailVO sbAdjustmentDetailVO = new SBAdjustmentDetailVO();
      sbAdjustmentDetailVO.setAccountId( sbDetailVO.getAccountId() );
      sbAdjustmentDetailVO.setCorpId( sbDetailVO.getCorpId() );
      sbAdjustmentDetailVO.setContractId( sbDetailVO.getContractId() );
      sbAdjustmentDetailVO.setMonthly( sbDetailVO.getMonthly() );
      sbAdjustmentDetailVO.setAccountMonthly( sbDetailVO.getAccountMonthly() );
      sbAdjustmentDetailVO.setItemId( sbDetailVO.getItemId() );
      if ( sbDetailVO.getItemId() != null )
      {
         try
         {
            final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( sbDetailVO.getItemId() );
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         }
         catch ( KANException e )
         {
            e.printStackTrace();
         }
      }
      sbAdjustmentDetailVO.setEmployeeSBId( sbDetailVO.getEmployeeSBId() );
      sbAdjustmentDetailVO.setOrderId( sbDetailVO.getOrderId() );
      sbAdjustmentDetailVO.setEntityId( sbDetailVO.getEntityId() );
      sbAdjustmentDetailVO.setBusinessTypeId( sbDetailVO.getBusinessTypeId() );
      sbAdjustmentDetailVO.setClientId( sbDetailVO.getClientId() );
      sbAdjustmentDetailVO.setClientNameZH( sbDetailVO.getClientNameZH() );
      sbAdjustmentDetailVO.setClientNameEN( sbDetailVO.getClientNameEN() );
      sbAdjustmentDetailVO.setEmployeeId( sbDetailVO.getEmployeeId() );
      sbAdjustmentDetailVO.setEmployeeNameZH( sbDetailVO.getEmployeeNameZH() );
      sbAdjustmentDetailVO.setEmployeeNameEN( sbDetailVO.getEmployeeNameEN() );
      sbAdjustmentDetailVO.setSbNumber( sbDetailVO.getSbNumber() );
      return sbAdjustmentDetailVO;
   }

   private SBAdjustmentDetailVO createSBAdjustmentDetailVO( final SBDetailTempVO sbDetailTempVO, String accountId )
   {
      SBAdjustmentDetailVO sbAdjustmentDetailVO = new SBAdjustmentDetailVO();
      sbAdjustmentDetailVO.setAccountId( sbDetailTempVO.getAccountId() );
      sbAdjustmentDetailVO.setCorpId( sbDetailTempVO.getCorpId() );
      sbAdjustmentDetailVO.setContractId( sbDetailTempVO.getContractId() );
      sbAdjustmentDetailVO.setMonthly( sbDetailTempVO.getMonthly() );
      sbAdjustmentDetailVO.setAccountMonthly( sbDetailTempVO.getAccountMonthly() );
      sbAdjustmentDetailVO.setItemId( sbDetailTempVO.getItemId() );
      if ( sbDetailTempVO.getItemId() != null )
      {
         try
         {
            final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( sbDetailTempVO.getItemId() );
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         }
         catch ( KANException e )
         {
            e.printStackTrace();
         }
      }
      sbAdjustmentDetailVO.setEmployeeSBId( sbDetailTempVO.getEmployeeSBId() );
      sbAdjustmentDetailVO.setOrderId( sbDetailTempVO.getOrderId() );
      sbAdjustmentDetailVO.setEntityId( sbDetailTempVO.getEntityId() );
      sbAdjustmentDetailVO.setBusinessTypeId( sbDetailTempVO.getBusinessTypeId() );
      sbAdjustmentDetailVO.setClientId( sbDetailTempVO.getClientId() );
      sbAdjustmentDetailVO.setClientNameZH( sbDetailTempVO.getClientNameZH() );
      sbAdjustmentDetailVO.setClientNameEN( sbDetailTempVO.getClientNameEN() );
      sbAdjustmentDetailVO.setEmployeeId( sbDetailTempVO.getEmployeeId() );
      sbAdjustmentDetailVO.setEmployeeNameZH( sbDetailTempVO.getEmployeeNameZH() );
      sbAdjustmentDetailVO.setEmployeeNameEN( sbDetailTempVO.getEmployeeNameEN() );
      sbAdjustmentDetailVO.setSbNumber( sbDetailTempVO.getSbNumber() );
      return sbAdjustmentDetailVO;
   }

   // 整合 SBDetailTempVO 集合
   private List< Object > fetchSBTempDetailVOs( SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // 初始化返回值
      final List< Object > sbDetailTempVOs = new ArrayList< Object >();
      // 查询符合状态的SBDetailVO 集合
      final SBDetailTempVO sbDetailTempVO = new SBDetailTempVO();
      sbDetailTempVO.setHeaderId( sbHeaderTempVO.getHeaderId() );
      sbDetailTempVO.setTempStatus( "1" );
      final List< Object > sbDetailTempVOObjects = this.sbDetailTempDao.getSBDetailTempVOsByCondition( sbDetailTempVO );
      sbDetailTempVOs.addAll( sbDetailTempVOObjects );
      return sbDetailTempVOs;
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

                  // 遍历
                  for ( Object sbDetailTempVOObject : sbDetailTempVOs )
                  {
                     final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

                     if ( KANUtil.filterEmpty( sbDetailTempVO.getStatus() ) != null && Integer.valueOf( sbDetailTempVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                     }
                  }

                  // 如果社保方案明细已全部是需要更改的状态，修改社保方案的状态
                  if ( detailFlag == sbDetailTempVOs.size() )
                  {
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

   @Override
   public int updateBatch( final CommonBatchVO commonBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         int num = 0;

         // 初始化查询对象
         final SBHeaderTempVO sbHeaderTempVO = new SBHeaderTempVO();
         sbHeaderTempVO.setAccountId( commonBatchVO.getAccountId() );
         sbHeaderTempVO.setBatchId( commonBatchVO.getBatchId() );
         // 只更新新建状态SBHeaderTempVO数据
         sbHeaderTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_NEW );
         // 初始化 SBAdjustmentDetailVO 集合
         List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs = new ArrayList< SBAdjustmentDetailVO >();

         final List< Object > sbHeaderTempList = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

         if ( sbHeaderTempList != null && sbHeaderTempList.size() > 0 )
         {
            for ( Object object : sbHeaderTempList )
            {
               SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) object;
               sbAdjustmentDetailVOs.addAll( getSBAdjustmentDetailVOs( tempSBHeaderTempVO ) );
            }
         }

         final List< SBAdjustmentDTO > sbAdjustmentDTOs = fetchSBAdjustmentDTO( sbAdjustmentDetailVOs );

         // 调整数据插入数据库
         if ( sbAdjustmentDTOs != null && sbAdjustmentDTOs.size() > 0 )
         {
            num = sbAdjustmentDTOs.size();
            // 插入 SBAdjustmentHeaderVO
            for ( SBAdjustmentDTO sbAdjustmentDTO : sbAdjustmentDTOs )
            {
               final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentDTO.getSbAdjustmentHeaderVO();
               sbAdjustmentHeaderVO.setCreateBy( sbHeaderTempVO.getModifyBy() );
               sbAdjustmentHeaderVO.setCreateDate( sbHeaderTempVO.getModifyDate() );
               sbAdjustmentHeaderVO.setModifyBy( sbHeaderTempVO.getModifyBy() );
               sbAdjustmentHeaderVO.setModifyDate( sbHeaderTempVO.getModifyDate() );
               this.sbAdjustmentHeaderDao.insertSBAdjustmentHeader( sbAdjustmentHeaderVO );

               // 插入 SBAdjustmentDetailVO
               if ( sbAdjustmentDTO.getSbAdjustmentDetailVOs() != null && sbAdjustmentDTO.getSbAdjustmentDetailVOs().size() > 0 )
               {
                  for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDTO.getSbAdjustmentDetailVOs() )
                  {
                     sbAdjustmentDetailVO.setAdjustmentHeaderId( sbAdjustmentDTO.getSbAdjustmentHeaderVO().getAdjustmentHeaderId() );
                     sbAdjustmentDetailVO.setCreateBy( sbHeaderTempVO.getModifyBy() );
                     sbAdjustmentDetailVO.setCreateDate( sbHeaderTempVO.getModifyDate() );
                     sbAdjustmentDetailVO.setModifyBy( sbHeaderTempVO.getModifyBy() );
                     sbAdjustmentDetailVO.setModifyDate( sbHeaderTempVO.getModifyDate() );
                     this.sbAdjustmentDetailDao.insertSBAdjustmentDetail( sbAdjustmentDetailVO );
                  }
               }
            }
         }

         // 批次标示为已更新
         commonBatchVO.setStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
         this.commonBatchDao.updateCommonBatch( commonBatchVO );

         // 批次对应从表信息标示为已更新
         final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByBatchId( commonBatchVO.getBatchId() );

         if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
         {
            for ( Object object : sbHeaderTempVOs )
            {
               SBHeaderTempVO sbHeaderTempVO2 = ( SBHeaderTempVO ) object;
               // SBHeaderTempVO标记为已更新
               sbHeaderTempVO2.setTempStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
               ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO2 );

               // SBHeaderTempVO对应的SBDetailTempVO集合标记为已更新
               final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO2.getHeaderId() );

               if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
               {
                  for ( Object object3 : sbDetailTempVOs )
                  {
                     SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) object3;
                     tempSBDetailTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
                     this.sbDetailTempDao.updateSBDetailTemp( tempSBDetailTempVO );
                  }
               }
            }
         }

         // 提交事务
         this.commitTransaction();

         return num;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public int updateSBHeaderTemp( SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return 0;
   }
}
