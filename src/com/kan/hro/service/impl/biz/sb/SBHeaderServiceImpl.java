package com.kan.hro.service.impl.biz.sb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;

public class SBHeaderServiceImpl extends ContextService implements SBHeaderService
{
   // 注入SBBatchDao
   private SBBatchDao sbBatchDao;

   // 注入SBDetailDao
   private SBDetailDao sbDetailDao;

   // 注入EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   public SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   /**  
    * GetSBHeaderVOsByCondition
    *	 获得　SBHeaderVO　分组的SBHeaderVO集合
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      SBHeaderVO sbHeaderVO = ( SBHeaderVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( sbHeaderDao.countSBHeaderVOsByCondition( sbHeaderVO ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVOTemp = ( SBHeaderVO ) sbHeaderVOObject;
            sbHeaderVOTemp.setRulePublic( sbHeaderVO.getRulePublic() );
            sbHeaderVOTemp.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            sbHeaderVOTemp.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            sbHeaderVOTemp.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            sbHeaderVOTemp.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            sbHeaderVOTemp.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            countSBHeaderVOAmount( sbHeaderVOTemp );
         }
      }

      return pagedListHolder;
   }

   /**  
    * GetSBContractVOsByCondition
    *	  获得按　服务协议　分组的SBHeaderVO集合
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();

      pagedListHolder.setHolderSize( sbHeaderDao.countSBContractVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getSBContractVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getSBContractVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            fetchContractSBHeaderVO( sbHeaderVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * GetAmountVendorSBHeaderVOsByCondition
    *	获得按所有供应商按月份分组后的SBHeaderVO集合汇总信息
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getAmountVendorSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      SBHeaderVO headerVO = ( SBHeaderVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( sbHeaderDao.countAmountVendorSBHeaderVOsByCondition( headerVO ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getAmountVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getAmountVendorSBHeaderVOsByCondition( headerVO ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // 添加汇总信息
            sbHeaderVO.setRulePublic( headerVO.getRulePublic() );
            sbHeaderVO.setRulePrivateIds( headerVO.getRulePrivateIds() );
            sbHeaderVO.setRulePositionIds( headerVO.getRulePositionIds() );
            sbHeaderVO.setRuleBranchIds( headerVO.getRuleBranchIds() );
            sbHeaderVO.setRuleBusinessTypeIds( headerVO.getRuleBusinessTypeIds() );
            sbHeaderVO.setRuleEntityIds( headerVO.getRuleEntityIds() );
            fetchAmountVendorPaymentSBHeaderVO( sbHeaderVO );
         }
      }

      // 获得SBHeaderVO 下的

      return pagedListHolder;
   }

   /**  
    * GetVendorSBHeaderVOsByCondition
    *	 获得指定供应商的SBHeaderVO集合信息
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getVendorSBHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderDao.countVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // 添加数据信息
            fetchVendorPaymentSBHeaderVO( sbHeaderVO );
         }
      }

      // 获得SBHeaderVO 下的

      return pagedListHolder;
   }

   /**  
    * CountSBHeaderVOAmount
    * 计算单个SBHeaderVO的费用合计
    * @param sbHeaderVO
    * @throws KANException
    */
   private void countSBHeaderVOAmount( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );
      // 初始化包含的所属月份
      Set< String > accountMonthlySet = new HashSet< String >();

      // 计算数据合计
      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // 初始化合计值
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // 如果状态匹配则叠加
            if ( sbDetailVO.getStatus().equals( sbHeaderVO.getAdditionalStatus() ) )
            {
               amountCompany = amountCompany.add( new BigDecimal( sbDetailVO.getAmountCompany() ) );
               amountPersonal = amountPersonal.add( new BigDecimal( sbDetailVO.getAmountPersonal() ) );
            }

            if ( sbDetailVO.getAccountMonthly() != null )
            {
               accountMonthlySet.add( sbDetailVO.getAccountMonthly() );
            }
         }
         sbHeaderVO.setAmountCompany( amountCompany.toString() );
         sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

         if ( accountMonthlySet.size() > 0 )
         {
            final String[] accountMonthlys = accountMonthlySet.toArray( new String[ 0 ] );
            sbHeaderVO.setAccountMonthlys( accountMonthlys );
         }
      }

   }

   /**  
    * CountSBHeaderVOAmount
    * 计算单个SBHeaderVO的费用合计（查询供应商信息用，不分状态）
    * @param sbHeaderVO
    * @throws KANException
    */
   private void countVendorSBHeaderVOAmount( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );

      // 计算数据合计
      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // 初始化合计值
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;
            amountCompany = amountCompany.add( new BigDecimal( sbDetailVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( sbDetailVO.getAmountPersonal() ) );
         }
         sbHeaderVO.setAmountCompany( amountCompany.toString() );
         sbHeaderVO.setAmountPersonal( amountPersonal.toString() );
      }

   }

   /**  
    * FetchContractSBHeaderVO
    * 单个服务协议社保信息完善
    * @param sbHeaderVO
    * @param employeeVOs
    * @throws KANException
    */
   private void fetchContractSBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      sbHeaderVO.setStatus( sbHeaderVO.getAdditionalStatus() );
      // 初始化包含的雇员集合
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // 初始化合计值
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // 获得协议对应的SBHeaderVO 集合
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // 分别计算合计
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object tempSBHeaderVOObject : sbHeaderVOs )
         {
            // 计算合计值
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) tempSBHeaderVOObject;
            countSBHeaderVOAmount( tempSBHeaderVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderVO.getAmountPersonal() ) );

            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            // 雇员集合添加数据
            if ( isEmployeeVOExist( tempSBHeaderVO, employeeMappingVOs ) )
            {
               continue;
            }

            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderVO.setEmployees( employeeMappingVOs );
      }
   }

   /**  
    * FetchVendorPaymentSBHeaderVO
    * 单个供应商社保信息完善（汇总信息）
    * @param sbHeaderVO
    * @throws KANException 
    */
   private void fetchAmountVendorPaymentSBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // 初始化包含的雇员集合
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // 查询对象清除状态
      sbHeaderVO.setStatus( "" );
      // 初始化合计值
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // 获得各供应商各月份对应的SBHeaderVO 集合
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // 分别计算合计
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object tempSBHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) tempSBHeaderVOObject;
            
            tempSBHeaderVO.setRulePublic( sbHeaderVO.getRulePublic() );
            tempSBHeaderVO.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            tempSBHeaderVO.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            tempSBHeaderVO.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            tempSBHeaderVO.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            tempSBHeaderVO.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            
            countVendorSBHeaderVOAmount( tempSBHeaderVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderVO.getAmountPersonal() ) );
            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            // 雇员集合添加数据
            if ( isEmployeeVOExist( tempSBHeaderVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderVO.setEmployees( employeeMappingVOs );
      }

   }

   /**  
    * FetchVendorPaymentSBHeaderVO
    * 单个供应商社保信息完善（按状态显示）
    * @param sbHeaderVO
    * @throws KANException 
    */
   private void fetchVendorPaymentSBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // 初始化包含的雇员集合
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // 获取SBHeaderVO的原来状态值
      final String status = sbHeaderVO.getStatus();

      // 设置查询状态
      sbHeaderVO.setStatus( sbHeaderVO.getAdditionalStatus() );
      // 初始化合计值
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // 获得各供应商各月份对应的SBHeaderVO 集合
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // 分别计算合计
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object tempSBHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) tempSBHeaderVOObject;
            countSBHeaderVOAmount( tempSBHeaderVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderVO.getAmountPersonal() ) );
            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            // 雇员集合添加数据
            if ( isEmployeeVOExist( tempSBHeaderVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderVO.setEmployees( employeeMappingVOs );
      }

      // 还原状态值
      sbHeaderVO.setStatus( status );
   }

   /**  
    * IsEmployeeVOExist
    *	判断雇员是否存在
    *	@param tempSBHeaderVO
    *	@param employeeMappingVOs
    *	@return
    */
   private boolean isEmployeeVOExist( final SBHeaderVO SBHeaderVO, final List< MappingVO > employeeMappingVOs )
   {
      if ( employeeMappingVOs != null && employeeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : employeeMappingVOs )
         {
            if ( mappingVO.getMappingId().equals( SBHeaderVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public List< Object > getSBHeaderVOsByBatchId( final String sbBatchId ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByBatchId( sbBatchId );

      // 计算合计值
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            countSBHeaderVOAmount( sbHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public SBHeaderVO getSBHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      final SBHeaderVO sbHeaderVO = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOByHeaderId( headerId );

      // 计算合计值
      if ( sbHeaderVO != null )
      {
         countSBHeaderVOAmount( sbHeaderVO );
      }

      return sbHeaderVO;
   }

   @Override
   public List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbContractHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBContractVOsByCondition( sbHeaderVO );

      if ( sbContractHeaderVOs != null && sbContractHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbContractHeaderVOs )
         {
            SBHeaderVO sbContractHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            fetchContractSBHeaderVO( sbContractHeaderVO );
         }
      }

      return sbContractHeaderVOs;
   }

   @Override
   public List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // 计算合计值
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            countSBHeaderVOAmount( tempSBHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getAmountVendorSBHeaderVOsByCondition( sbHeaderVO );

      // 计算合计值
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            
            tempSBHeaderVO.setCorpId( sbHeaderVO.getCorpId() );
            tempSBHeaderVO.setRulePublic( sbHeaderVO.getRulePublic() );
            tempSBHeaderVO.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            tempSBHeaderVO.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            tempSBHeaderVO.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            tempSBHeaderVO.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            tempSBHeaderVO.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            fetchAmountVendorPaymentSBHeaderVO( tempSBHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getVendorSBHeaderVOsByCondition( sbHeaderVO );

      // 计算合计值
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            tempSBHeaderVO.setRulePublic( sbHeaderVO.getRulePublic() );
            tempSBHeaderVO.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            tempSBHeaderVO.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            tempSBHeaderVO.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            tempSBHeaderVO.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            tempSBHeaderVO.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            fetchVendorPaymentSBHeaderVO( tempSBHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public List< SBDTO > getSBDTOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // 初始化SBDTO List
      final List< SBDTO > sbDTOs = new ArrayList< SBDTO >();
      // 初始化SBHeaderVO List
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            // 初始化缓存对象
            final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // 初始化SBDTO
            final SBDTO sbDTO = new SBDTO();

            // 装载SBHeaderVO
            sbDTO.setSbHeaderVO( tempSBHeaderVO );

            // 装载SBDetailVO List
            fetchSBDetail( sbDTO, tempSBHeaderVO.getHeaderId(), sbHeaderVO.getStatus() );

            sbDTOs.add( sbDTO );
         }
      }

      return sbDTOs;
   }

   // 装载社保明细
   private void fetchSBDetail( final SBDTO sbDTO, final String headerId, final String status ) throws KANException
   {
      // 初始化并装载社保明细
      SBHeaderVO sbHeaderVO = new SBHeaderVO();
      sbHeaderVO.setHeaderId( headerId );
      final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByHeaderId( sbHeaderVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // 只提取符合条件的社保明细
            if ( sbDetailVO.getStatus() != null && sbDetailVO.getStatus().equals( status ) )
            {
               sbDTO.getSbDetailVOs().add( ( SBDetailVO ) sbDetailVOObject );
            }
         }
      }
   }

   @Override
   public int updateSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      int updateRows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 更改派送协议社保方案缴纳状态
         // Added by Kevin Jin at 2014-07-17
         if ( KANUtil.filterEmpty( sbHeaderVO.getFlag() ) != null && KANUtil.filterEmpty( sbHeaderVO.getFlag() ).equals( "1" ) )
         {
            final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );

            if ( KANUtil.filterEmpty( employeeContractSBVO.getFlag() ) == null || !KANUtil.filterEmpty( employeeContractSBVO.getFlag() ).equals( "1" ) )
            {
               employeeContractSBVO.setFlag( "1" );
               this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
            }
         }

         updateRows = ( ( SBHeaderDao ) getDao() ).updateSBHeader( sbHeaderVO );

         // 提交事务
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return updateRows;
   }

   @Override
   public int insertSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).insertSBHeader( sbHeaderVO );
   }

   @Override
   public int deleteSBHeader( final String sbHeaderId ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).deleteSBHeader( sbHeaderId );
   }

   /**  
    * Submit
    *	 提交社保方案
    *	@param sbHeaderVO
    * @return 
    *	@throws KANException
    */
   @Override
   public int submit( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 获得勾选ID数组
         final String selectedIds = sbHeaderVO.getSelectedIds();
         // 获得 PageFlag
         final String pageFlag = sbHeaderVO.getPageFlag();
         // 获得AccountId
         final String accountId = sbHeaderVO.getAccountId();

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
               if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_HEADER ) )
               {
                  submitHeader( accountId, selectId, status, sbHeaderVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_DETAIL ) )
               {
                  submitDetail( accountId, selectId, status, sbHeaderVO.getModifyBy() );
               }

            }

            // 尝试更改其父对象状态
            trySubmitBatch( sbHeaderVO.getBatchId(), status, sbHeaderVO.getModifyBy() );
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
   private void submitHeader( final String accountId, final String headerId, final String status, final String userId ) throws KANException
   {
      // 初始化社保方案明细
      SBHeaderVO headerVO = new SBHeaderVO();
      headerVO.setHeaderId( headerId );
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( headerVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // 遍历
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;
            submitDetail( accountId, sbDetailVO.getDetailId(), status, userId );
         }
      }

      // 更改SBHeaderVO
      final SBHeaderVO sbHeaderVO = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOByHeaderId( headerId );
      sbHeaderVO.setStatus( status );
      sbHeaderVO.setModifyBy( userId );
      sbHeaderVO.setModifyDate( new Date() );
      ( ( SBHeaderDao ) getDao() ).updateSBHeader( sbHeaderVO );

   }

   // 更改社保方案明细
   private void submitDetail( final String accountId, final String detailId, final String status, final String userId ) throws KANException
   {
      final SBDetailVO sbDetailVO = this.sbDetailDao.getSBDetailVOByDetailId( detailId );

      if ( sbDetailVO != null && sbDetailVO.getStatus() != null && ( sbDetailVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) || "4".equals( status ) ) )
      {
         sbDetailVO.setStatus( status );
         sbDetailVO.setModifyBy( userId );
         sbDetailVO.setModifyDate( new Date() );
         this.sbDetailDao.updateSBDetail( sbDetailVO );
      }

   }

   // 尝试提交批次 - 提交子对象但不清楚父对象是否提交的情况使用
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // 初始化SBHeaderVO
         final SBBatchVO sbBatchVO = this.sbBatchDao.getSBBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
         // 初始化SBHeaderVO列表
         final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;

            // 遍历
            for ( Object sbHeaderVOObject : sbHeaderVOs )
            {
               final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

               // 初始化SBDetailVO列表
               final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );

               if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
               {
                  int detailFlag = 0;
                  // 初始化变量（判断是否状态都比要修改的状态值大，如果是不修改状态）
                  int upperCount = 0;

                  // 遍历
                  for ( Object sbDetailVOObject : sbDetailVOs )
                  {
                     final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

                     if ( KANUtil.filterEmpty( sbDetailVO.getStatus() ) != null && Integer.valueOf( sbDetailVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                        if ( Integer.valueOf( sbDetailVO.getStatus() ) > Integer.valueOf( status ) )
                        {
                           upperCount++;
                        }
                     }
                  }

                  // 如果社保方案明细已全部是需要更改的状态，修改社保方案的状态
                  if ( detailFlag == sbDetailVOs.size() )
                  {
                     // 如果社保方案明细已全部是需要更改的状态且状态不是全都比要修改的状态值大，修改社保方案的状态
                     if ( upperCount != sbDetailVOs.size() )
                     {
                        sbHeaderVO.setStatus( status );
                     }
                     sbHeaderVO.setModifyBy( userId );
                     sbHeaderVO.setModifyDate( new Date() );
                     ( ( SBHeaderDao ) getDao() ).updateSBHeader( sbHeaderVO );
                     submitRows++;

                     // 商保确认
                     if ( KANUtil.filterEmpty( status ) != null && KANUtil.filterEmpty( status ).equals( "3" ) )
                     {
                        final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );

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

               if ( sbHeaderVO.getStatus() != null && !sbHeaderVO.getStatus().isEmpty() && Integer.valueOf( sbHeaderVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }
            }

            // 如果社保方案已全部是需要更改的状态，修改社保批次的状态
            if ( headerFlag == sbHeaderVOs.size() )
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
    * GetSBDTOsByCondition
    *	查询有效供应商的SBDTO 
    *	@param sbHeaderHolder
    *	@throws KANException
    */
   @Override
   public void getSBDTOsByCondition( final PagedListHolder sbHeaderHolder ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderHolder.getObject();

      if ( sbHeaderVO.getPageFlag() != null )
      {
         // 如果是供应商层
         if ( sbHeaderVO.getPageFlag().equals( PAGE_FLAG_VENDOR ) )
         {
            sbHeaderHolder.setSource( sbHeaderDao.getAmountVendorSBHeaderVOsByCondition( sbHeaderVO ) );
         }
         // 如果是Header层
         else if ( sbHeaderVO.getPageFlag().equals( PAGE_FLAG_HEADER ) )
         {
            sbHeaderHolder.setSource( sbHeaderDao.getVendorSBHeaderVOsByCondition( sbHeaderVO ) );
         }
      }

      fetchSBDTO( sbHeaderHolder );
   }

   /**  
    * FetchSBDTO
    *	
    *	@param sbHeaderHolder
    *	@throws KANException
    */
   private void fetchSBDTO( final PagedListHolder sbHeaderHolder ) throws KANException
   {
      // 初始化SBDTO List
      final List< Object > sbDTOs = new ArrayList< Object >();
      // 初始化包含所有科目的集合
      final List< ItemVO > items = new ArrayList< ItemVO >();
      // 获得PageFlag
      final String pageFlag = ( ( SBHeaderVO ) sbHeaderHolder.getObject() ).getPageFlag();
      // 初始化SBDTO的 Size
      int size = 0;

      if ( sbHeaderHolder.getSource() != null && sbHeaderHolder.getSource().size() > 0 )
      {
         if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_VENDOR ) )
         {

            for ( Object amountVendorSBHeaderVOObject : sbHeaderHolder.getSource() )
            {
               SBHeaderVO amountVendorPaymentSBHeaderVO = ( SBHeaderVO ) amountVendorSBHeaderVOObject;
               // 清除查询状态条件
               amountVendorPaymentSBHeaderVO.setAdditionalStatus( "" );
               // 分别查询
               final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getVendorSBHeaderVOsByCondition( amountVendorPaymentSBHeaderVO );

               if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
               {
                  for ( Object vendorPaymentSBHeaderVOObject : sbHeaderVOs )
                  {
                     SBHeaderVO vendorPaymentSBHeaderVO = ( SBHeaderVO ) vendorPaymentSBHeaderVOObject;
                     // 初始化SBDTO
                     final SBDTO sbDTO = new SBDTO();
                     // 装载SBHeaderVO
                     sbDTO.setSbHeaderVO( vendorPaymentSBHeaderVO );
                     // 装载SBDetailVO List
                     fetchSBDetail( sbDTO, vendorPaymentSBHeaderVO, items );
                     sbDTOs.add( sbDTO );

                     size++;
                  }
               }

            }
         }
         else if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_HEADER ) )
         {
            for ( Object vendorPaymentSBHeaderVOObject : sbHeaderHolder.getSource() )
            {
               SBHeaderVO vendorPaymentSBHeaderVO = ( SBHeaderVO ) vendorPaymentSBHeaderVOObject;
               // 初始化SBDTO
               final SBDTO sbDTO = new SBDTO();
               // 装载SBHeaderVO
               sbDTO.setSbHeaderVO( vendorPaymentSBHeaderVO );
               // 装载SBDetailVO List
               fetchSBDetail( sbDTO, vendorPaymentSBHeaderVO, items );
               sbDTOs.add( sbDTO );

               size++;
            }
         }

      }

      // SBDTO不存在科目添加空值
      if ( sbDTOs != null && sbDTOs.size() > 0 )
      {
         fillSBDTOs( sbDTOs, items );
      }

      sbHeaderHolder.setHolderSize( size );
      sbHeaderHolder.setSource( sbDTOs );
   }

   // 装载社保明细
   private void fetchSBDetail( final SBDTO sbDTO, final SBHeaderVO sbHeaderVO, final List< ItemVO > items ) throws KANException
   {
      // 初始化查询对象
      final SBDetailVO sbDetailVO = new SBDetailVO();

      sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
      sbDetailVO.setStatus( sbHeaderVO.getAdditionalStatus() );

      // 初始化并装载社保明细
      final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByCondition( sbDetailVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            final SBDetailVO tempSBDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // 计算合计值
            BigDecimal amountCompany = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderVO.getAmountCompany() ) == null ) ? "0" : sbHeaderVO.getAmountCompany() ).add( new BigDecimal( tempSBDetailVO.getAmountCompany() ) ) );
            BigDecimal amountPersonal = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderVO.getAmountPersonal() ) == null ) ? "0" : sbHeaderVO.getAmountPersonal() ).add( new BigDecimal( tempSBDetailVO.getAmountPersonal() ) ) );

            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            sbDTO.getSbDetailVOs().add( ( SBDetailVO ) sbDetailVOObject );

            // 初始化科目
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( tempSBDetailVO.getItemId() );
            itemVO.setItemType( tempSBDetailVO.getItemType() );

            // 如果科目不存在则添加
            if ( !isItemExist( itemVO, items ) )
            {
               items.add( itemVO );
            }

         }

      }
   }

   // SBDTO集合填充不存在科目
   private void fillSBDTOs( final List< Object > sbDTOs, final List< ItemVO > items )
   {
      for ( ItemVO itemVO : items )
      {
         for ( Object sbDTOObject : sbDTOs )
         {
            final List< SBDetailVO > copyList = new ArrayList< SBDetailVO >();
            final SBDTO sbDTO = ( SBDTO ) sbDTOObject;
            // 获得SBDTO对应SBHeaderVO、SBDetailVO集合
            final SBHeaderVO sbHeaderVO = sbDTO.getSbHeaderVO();
            List< SBDetailVO > sbDetailVOs = sbDTO.getSbDetailVOs();
            // COPY已存在SBDTO集合
            copyList.addAll( sbDetailVOs );

            // 查找当前SBDetailVO是否存在该科目，不存在则添加
            fetchItemExistSbDetailVOs( itemVO, sbHeaderVO, sbDetailVOs, copyList );

            // 重新设置SBDTO的SBDetailVO集合
            sbDTO.setSbDetailVOs( copyList );
         }
      }

   }

   /**  
    * IsItemExistSbDetailVOs
    * 判断ItemVO是否存在社保明细数组
    * @param itemVO
    * @param items
    * @return
    */
   private void fetchItemExistSbDetailVOs( final ItemVO itemVO, final SBHeaderVO sbHeaderVO, final List< SBDetailVO > sbDetailVOs, final List< SBDetailVO > returnDetailVOs )
   {
      Boolean existFlag = false;

      // 判断科目是否存在
      if ( sbDetailVOs == null || sbDetailVOs.size() == 0 )
      {
         existFlag = false;
      }
      else
      {
         for ( SBDetailVO sbDetailVO : sbDetailVOs )
         {
            if ( itemVO.getItemId().equals( sbDetailVO.getItemId() ) )
            {
               existFlag = true;
               break;
            }
         }
      }

      // 如果科目不存在则添加
      if ( !existFlag )
      {
         // 初始化SBDetailVO用于添加
         final SBDetailVO tempDetailVO = new SBDetailVO();
         tempDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
         tempDetailVO.setStatus( sbHeaderVO.getStatus() );
         tempDetailVO.setItemId( itemVO.getItemId() );
         tempDetailVO.setItemType( itemVO.getItemType() );
         returnDetailVOs.add( tempDetailVO );
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
   public List< Object > getMonthliesBySBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).getMonthliesBySBHeaderVO( sbHeaderVO );
   }

   @Override
   public int updateSBHeaderPaid( SBHeaderVO sbHeaderVO ) throws KANException
   {
      int updateRow = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 更改派送协议社保方案缴纳状态
         // Added by Kevin Jin at 2014-07-16
         if ( sbHeaderVO != null && sbHeaderVO.getCertificateNumbers() != null && sbHeaderVO.getCertificateNumbers().size() > 0 )
         {
            for ( String certificateNumber : sbHeaderVO.getCertificateNumbers() )
            {
               SBHeaderVO tempSBHeaderVO = new SBHeaderVO();
               tempSBHeaderVO.setAccountId( sbHeaderVO.getAccountId() );
               tempSBHeaderVO.setBatchId( sbHeaderVO.getBatchId() );
               tempSBHeaderVO.setCertificateNumber( certificateNumber );

               final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( tempSBHeaderVO );

               if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
               {
                  for ( Object sbHeaderVOObject : sbHeaderVOs )
                  {
                     final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( ( ( SBHeaderVO ) sbHeaderVOObject ).getEmployeeSBId() );

                     if ( KANUtil.filterEmpty( employeeContractSBVO.getFlag() ) == null || !KANUtil.filterEmpty( employeeContractSBVO.getFlag() ).equals( "1" ) )
                     {
                        employeeContractSBVO.setFlag( "1" );
                        this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
                     }
                  }
               }
            }
         }

         updateRow = ( ( SBHeaderDao ) getDao() ).updateSBHeaderPaid( sbHeaderVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return updateRow;
   }

   @Override
   public String getSBToApplyForMoreStatusCountByHeaderIds( String[] headerId ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).getSBToApplyForMoreStatusCountByHeaderIds( headerId ) + "";
   }

   @Override
   public String getSBToApplyForResigningStatusCountByByHeaderIds( String[] headerId ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).getSBToApplyForResigningStatusCountByByHeaderIds( headerId ) + "";
   }

}
