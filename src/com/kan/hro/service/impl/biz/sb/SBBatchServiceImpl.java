package com.kan.hro.service.impl.biz.sb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.vendor.VendorContactDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.web.actions.biz.sb.SBAction;

public class SBBatchServiceImpl extends ContextService implements SBBatchService
{

   // 注入SBHeaderDao
   private SBHeaderDao sbHeaderDao;

   // 注入SBDetailDao
   private SBDetailDao sbDetailDao;

   // 注入EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   // 注入VendorContactDao
   private VendorContactDao vendorContactDao;

   // 注入VendorDao
   private VendorDao vendorDao;

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

   public final EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public final void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public VendorContactDao getVendorContactDao()
   {
      return vendorContactDao;
   }

   public void setVendorContactDao( VendorContactDao vendorContactDao )
   {
      this.vendorContactDao = vendorContactDao;
   }

   public VendorDao getVendorDao()
   {
      return vendorDao;
   }

   public void setVendorDao( VendorDao vendorDao )
   {
      this.vendorDao = vendorDao;
   }

   @Override
   public PagedListHolder getSBBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBBatchDao sbBatchDao = ( SBBatchDao ) getDao();
      SBBatchVO sbBatchVO = ( SBBatchVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( sbBatchDao.countSBBatchVOsByCondition( sbBatchVO ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbBatchDao.getSBBatchVOsByCondition( sbBatchVO, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbBatchDao.getSBBatchVOsByCondition( sbBatchVO ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbBatchVOObject : pagedListHolder.getSource() )
         {
            final SBBatchVO sbBatch = ( SBBatchVO ) sbBatchVOObject;
            sbBatch.setRulePublic( sbBatchVO.getRulePublic() );
            sbBatch.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
            sbBatch.setRulePositionIds( sbBatchVO.getRulePositionIds() );
            sbBatch.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
            sbBatch.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
            sbBatch.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );
            fetchSBBatchVO( sbBatch );
         }
      }

      return pagedListHolder;
   }

   /**  
    * FetchSBBatchVO
    *	计算公司及个人数值合计(指定批次)
    *	@param pagedListHolder
    *	@throws KANException
    */
   private void fetchSBBatchVO( final SBBatchVO sbBatchVO ) throws KANException
   {
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByBatchId( sbBatchVO );

      // 初始化包含的雇员集合 (去重复 )
      final Set< MappingVO > employeeMappingVOSet = new HashSet< MappingVO >();

      // 计算数据合计
      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // 初始化合计值
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // 如果是“新建”、“批准”添加对应状态员工
            if ( "1".equals( sbBatchVO.getAdditionalStatus() ) || "2".equals( sbBatchVO.getAdditionalStatus() ) )
            {
               if ( sbDetailVO.getStatus().equals( sbBatchVO.getAdditionalStatus() ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( sbDetailVO.getEmployeeId() );
                  mappingVO.setMappingValue( sbDetailVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( sbDetailVO.getEmployeeNameEN() );
                  employeeMappingVOSet.add( mappingVO );
               }
            }
            // 其他情况添加“确认”、“提交”、“已结算”状态员工
            else
            {
               if ( !"1".equals( sbDetailVO.getStatus() ) && !"2".equals( sbDetailVO.getStatus() ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( sbDetailVO.getEmployeeId() );
                  mappingVO.setMappingValue( sbDetailVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( sbDetailVO.getEmployeeNameEN() );
                  employeeMappingVOSet.add( mappingVO );
               }
            }

            // 如果“状态”符合则添加运算
            if ( sbDetailVO.getStatus().equals( sbBatchVO.getAdditionalStatus() ) )
            {
               amountCompany = amountCompany.add( new BigDecimal( sbDetailVO.getAmountCompany() ) );
               amountPersonal = amountPersonal.add( new BigDecimal( sbDetailVO.getAmountPersonal() ) );
            }
         }
         sbBatchVO.setAmountCompany( amountCompany.toString() );
         sbBatchVO.setAmountPersonal( amountPersonal.toString() );

         final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >( employeeMappingVOSet );
         sbBatchVO.setEmployees( employeeMappingVOs );
      }

   }

   /**  
    * countAmount
    * 计算公司及个人数值合计(指定SBHeaderVO)
    * @param sbHeaderVO
    * @throws KANException
    */
   private void countAmount( SBHeaderVO sbHeaderVO ) throws KANException
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

   @Override
   public List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException
   {
      List< Object > sbBatchObjects = ( ( SBBatchDao ) getDao() ).getSBBatchVOsByCondition( sbBatchVO );

      if ( sbBatchObjects != null && sbBatchObjects.size() > 0 )
      {
         for ( Object sbBatchVOObject : sbBatchObjects )
         {
            SBBatchVO tempSBBatchVO = ( SBBatchVO ) sbBatchVOObject;
            tempSBBatchVO.setRulePublic( sbBatchVO.getRulePublic() );
            tempSBBatchVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
            tempSBBatchVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
            tempSBBatchVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
            tempSBBatchVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
            tempSBBatchVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );
            // 计算合计值
            fetchSBBatchVO( tempSBBatchVO );
         }
      }

      return sbBatchObjects;
   }

   @Override
   public SBBatchVO getSBBatchVOByBatchId( final String batchId ) throws KANException
   {
      final SBBatchVO sbBatchVO = ( ( SBBatchDao ) getDao() ).getSBBatchVOByBatchId( batchId );

      if ( sbBatchVO != null )
      {
         // 计算合计值
         fetchSBBatchVO( sbBatchVO );
      }

      return sbBatchVO;
   }

   @Override
   public int updateSBBatch( final SBBatchVO sbBatchVO ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).updateSBBatch( sbBatchVO );
   }

   @Override
   public int insertSBBatch( final SBBatchVO sbBatchVO ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).insertSBBatch( sbBatchVO );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-04
   public int rollback( final SBBatchVO sbBatchVO, final Map< String, String > statusArgs ) throws KANException
   {
      int rows = 0;
      final String sbStatusNoSocialBenefit = "0";
      final String sbStatusNormal = "3";

      try
      {
         // 开启事务
         startTransaction();

         // 获得PageFlag、选中项Id、状态
         final String pageFlag = sbBatchVO.getPageFlag();
         final String selectedIds = sbBatchVO.getSelectedIds();
         final String status = sbBatchVO.getStatus();
         final String statusAdd = statusArgs.get( "statusAdd" );
         final String statusBack = statusArgs.get( "statusBack" );

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            if ( selectedIds != null && !selectedIds.trim().isEmpty() )
            {
               // 生成ID数组
               final String[] selectedIdArray = selectedIds.split( "," );

               rows = selectedIdArray.length;

               String[] ids = new String[ rows ];

               for ( int i = 0; i < selectedIdArray.length; i++ )
               {
                  ids[ i ] = KANUtil.decodeStringFromAjax( selectedIdArray[ i ] );
               }

               if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
               {
                  if ( StringUtils.isNotEmpty( statusAdd ) && sbStatusNoSocialBenefit.equals( statusAdd ) )
                  {
                     ( ( SBBatchDao ) getDao() ).updateSBStatusTONoSocialBenefitByBatchId( ids );
                  }
                  if ( StringUtils.isNotEmpty( statusBack ) && sbStatusNormal.equals( statusBack ) )
                  {
                     ( ( SBBatchDao ) getDao() ).updateSBStatusTONormalByBatchId( ids );
                  }
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
               {
                  if ( StringUtils.isNotEmpty( statusAdd ) && sbStatusNoSocialBenefit.equals( statusAdd ) )
                  {
                     sbHeaderDao.updateSBStatusTONoSocialBenefitByHeaderId( ids );
                  }
                  if ( StringUtils.isNotEmpty( statusBack ) && sbStatusNormal.equals( statusBack ) )
                  {
                     sbHeaderDao.updateSBStatusTONormalByHeaderId( ids );
                  }
               }

               // 初始化BatchId列表
               final List< String > batchIds = new ArrayList< String >();

               // 遍历
               for ( String encodedSelectedId : selectedIdArray )
               {
                  // 初始化BatchId
                  String batchId = null;
                  // 解码
                  final String selectedId = KANUtil.decodeStringFromAjax( encodedSelectedId );

                  // 按照PageFlag退回
                  if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
                  {
                     batchId = rollbackBatch( selectedId, status );
                  }
                  else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
                  {
                     SBHeaderVO headerVO = new SBHeaderVO();
                     headerVO.setHeaderId( selectedId );
                     batchId = rollbackHeader( headerVO, status );
                  }

                  if ( KANUtil.filterEmpty( batchId ) != null && !batchIds.contains( batchId ) )
                  {
                     batchIds.add( batchId );
                  }
               }

               // 尝试退回其父对象状态
               if ( batchIds != null && batchIds.size() > 0 )
               {
                  for ( String batchId : batchIds )
                  {
                     tryRollbackBatch( batchId );
                  }
               }
            }
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

      return rows;
   }

   /**  
    * 退回批次
    *	
    *	@param selectId
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   private String rollbackBatch( final String batchId, final String status ) throws KANException
   {
      // 初始化SBHeaderVO列表
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( batchId );

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         // 遍历
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            // 删除SBHeaderVO
            rollbackHeader( ( ( SBHeaderVO ) sbHeaderVOObject ), status );
         }
      }

      return batchId;
   }

   /**  
    * 退回社保方案
    *	
    *	@param sbBatchVO
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   private String rollbackHeader( final SBHeaderVO headerVO, final String status ) throws KANException
   {
      // 初始化SBDetailVO列表
      final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByHeaderId( headerVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // 遍历
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            // 删除SBDetailVO
            rollbackDetail( ( ( SBDetailVO ) sbDetailVOObject ).getDetailId(), status );
         }
      }

      // 删除SBHeaderVO
      final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( headerVO.getHeaderId() );

      if ( sbHeaderVO != null )
      {
         return sbHeaderVO.getBatchId();
      }

      return "";
   }

   /**  
    * 退回社保方案明细
    *	
    *	@param selectId
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   private String rollbackDetail( final String detailId, final String status ) throws KANException
   {
      final SBDetailVO sbDetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( detailId );

      if ( sbDetailVO != null && sbDetailVO.getStatus() != null && sbDetailVO.getStatus().equals( status ) && ( Integer.parseInt( sbDetailVO.getStatus() ) ) <= 2 )
      {
         // 删除SBDetailVO
         this.getSbDetailDao().deleteSBDetail( detailId );
         final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbDetailVO.getHeaderId() );

         if ( sbHeaderVO != null )
         {
            return sbHeaderVO.getBatchId();
         }
      }

      return "";
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-09
   public int insertSBBatch( final SBBatchVO sbBatchVO, final List< ServiceContractDTO > serviceContractDTOs ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 如果存在数据则插入数据库
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            // 遍历
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               // 获得SBDTO List
               final List< SBDTO > sbDTOs = serviceContractDTO.getSbDTOs();

               if ( sbDTOs != null && sbDTOs.size() > 0 )
               {
                  if ( rows == 0 )
                  {
                     // 添加批次
                     insertSBBatch( sbBatchVO );

                     // 一个批次添加成功
                     rows = 1;
                  }

                  for ( SBDTO sbDTO : sbDTOs )
                  {
                     // 获得SBHeaderVO
                     final SBHeaderVO sbHeaderVO = sbDTO.getSbHeaderVO();

                     // 获得SBDetailVO List
                     final List< SBDetailVO > sbdetailVOs = sbDTO.getSbDetailVOs();

                     if ( sbHeaderVO != null && sbdetailVOs != null && sbdetailVOs.size() > 0 )
                     {
                        // 设置BatchId
                        sbHeaderVO.setBatchId( sbBatchVO.getBatchId() );
                        // 插入SBHeaderVO
                        this.getSbHeaderDao().insertSBHeader( sbHeaderVO );

                        // 遍历SBDetailVO List
                        for ( SBDetailVO sbDetailVO : sbdetailVOs )
                        {
                           // 设置HeaderId
                           sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
                           // 插入SBDetailVO
                           this.getSbDetailDao().insertSBDetail( sbDetailVO );
                        }
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( sbBatchVO.getBatchId() ) != null )
         {
            // 批次执行结束时间
            sbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            sbBatchVO.setDeleted( SBBatchVO.TRUE );
            sbBatchVO.setStatus( SBBatchVO.TRUE );
            // 修改批次
            updateSBBatch( sbBatchVO );
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

      return rows;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-04
   public Integer submit( final SBBatchVO sbBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 获得勾选ID数组
         final String selectedIds = sbBatchVO.getSelectedIds();
         // 获得 PageFlag
         final String pageFlag = sbBatchVO.getPageFlag();
         // 获得ActionFlag
         final String actionFlag = sbBatchVO.getSubAction();
         // 获得AccountId
         final String accountId = sbBatchVO.getAccountId();

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // 分割选择项
            final String[] selectedIdArray = selectedIds.split( "," );
            // 提交记录数
            submitRows = selectedIdArray.length;

            // 初始化状态字符串
            String status = "0";
            // 如果是批准
            if ( actionFlag.equalsIgnoreCase( BaseAction.APPROVE_OBJECTS ) )
            {
               status = "2";
            }
            // 如果是确认
            else if ( actionFlag.equalsIgnoreCase( BaseAction.CONFIRM_OBJECTS ) )
            {
               status = "3";
            }
            // 如果是提交
            else if ( actionFlag.equalsIgnoreCase( BaseAction.SUBMIT_OBJECTS ) )
            {
               status = "4";
            }
            final SBBatchDao sbBatchDao = ( SBBatchDao ) getDao();
            // 初始化BatchId列表
            final List< String > batchIds = new ArrayList< String >();
            sbBatchVO.setStatus( status );
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               // 初始化BatchId
               String batchId = null;
               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // 按照PageFlag提交
               if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
               {
                 
                  //批次提交shi
//                  batchId = submitBatch( sbBatchVO, accountId, selectId, status, sbBatchVO.getModifyBy() );
                  sbBatchVO.setBatchId( selectId );
                  sbBatchVO.setHeaderId( null );
                  sbBatchVO.setDetailId( null );
                  batchId = selectId;
                  sbBatchDao.updateSBDetailStatus(sbBatchVO);
                  sbBatchDao.updateSBHeaderStatus(sbBatchVO);
                  sbBatchDao.updateSBBatchStatus(sbBatchVO);
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
               {
                  sbBatchVO.setHeaderId( selectId );
                  sbBatchVO.setBatchId( null );
                  sbBatchVO.setDetailId( null );
                  sbBatchDao.updateSBHeaderStatus(sbBatchVO);
                  sbBatchDao.updateSBDetailStatus(sbBatchVO);
                  batchId = sbBatchDao.getSBBatchId( sbBatchVO );
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
               {
                  sbBatchVO.setHeaderId( null );
                  sbBatchVO.setBatchId( null );
                  sbBatchVO.setDetailId( selectId );
                  sbBatchDao.updateSBDetailStatus(sbBatchVO);
                  batchId = sbBatchDao.getSBBatchId( sbBatchVO );
               }

               if ( KANUtil.filterEmpty( batchId ) != null && !batchIds.contains( batchId ) )
               {
                  batchIds.add( batchId );
               }
            }

            // 如果为“批准”状态则发送邮件给供应商
            if ( KANUtil.filterEmpty( status ) != null && status.equals( "2" ) )
            {
               // 获得关联的供应商ID集合
               final List< String > vendorIds = getVendorIds( pageFlag, selectedIdArray );

               if ( vendorIds != null && vendorIds.size() > 0 )
               {
                  for ( String vendorId : vendorIds )
                  {
                     // 获得VendorVO
                     final VendorVO vendorVO = this.getVendorDao().getVendorVOByVendorId( vendorId );
                     // 初始化VendorContactVO
                     VendorContactVO vendorContactVO = null;
                     String reception = "";

                     // 如果默认联系人存在
                     if ( vendorVO != null && KANUtil.filterEmpty( vendorVO.getMainContact(), "0" ) != null )
                     {
                        vendorContactVO = this.getVendorContactDao().getVendorContactVOByVendorContactId( vendorVO.getMainContact() );

                        // 如果默认联系人邮箱存在
                        if ( vendorContactVO != null && KANUtil.filterEmpty( vendorContactVO.getBizEmail() ) != null )
                        {
                           reception = vendorContactVO.getBizEmail();
                        }
                        // 无默认联系人
                        else
                        {
                           reception = vendorVO.getEmail();
                        }
                     }

                     if ( KANUtil.filterEmpty( reception ) != null )
                     {
                        new Mail( accountId, reception, ContentUtil.getMailContent_SBNotice_Vendor( new Object[] { vendorContactVO, vendorVO } ) ).send( true );
                     }
                  }
               }
            }

            // 尝试更改其父对象状态
            if ( batchIds != null && batchIds.size() > 0 )
            {
               for ( String batchId : batchIds )
               {
                  trySubmitBatch( batchId, status, sbBatchVO.getModifyBy() );
               }
            }
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

   /**  
    * FetchVendorIds
    * 获得关联供应商ID集合
    *	@param sbBatchVO
    * @param selectedIdArray 
    *	@return
    * @throws KANException 
    */
   private List< String > getVendorIds( final String pageFlag, final String[] selectedIdArray ) throws KANException
   {
      // 初始化返回值
      List< String > vendorIds = new ArrayList< String >();

      // 如果是按批次批准
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
      {
         for ( String selectedId : selectedIdArray )
         {
            final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );

            if ( sbHeaderVOs != null & sbHeaderVOs.size() > 0 )
            {
               for ( Object sbHeaderVOObject : sbHeaderVOs )
               {
                  final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
                  if ( KANUtil.filterEmpty( sbHeaderVO.getVendorId() ) != null )
                  {
                     // 如果vendorId不存在则添加
                     if ( !isVendorIdExist( sbHeaderVO.getVendorId(), vendorIds ) )
                     {
                        vendorIds.add( sbHeaderVO.getVendorId() );
                     }
                  }
               }
            }
         }
      }
      // 如果是按Header批准
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
      {
         for ( String selectedId : selectedIdArray )
         {
            final SBHeaderVO sbHeaderVO = this.sbHeaderDao.getSBHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

            if ( KANUtil.filterEmpty( sbHeaderVO.getVendorId() ) != null )
            {
               // 如果vendorId不存在则添加
               if ( !isVendorIdExist( sbHeaderVO.getVendorId(), vendorIds ) )
               {
                  vendorIds.add( sbHeaderVO.getVendorId() );
               }
            }
         }
      }
      // 如果是按Detail批准
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
      {
         for ( String selectedId : selectedIdArray )
         {
            final SBDetailVO sbdetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
            final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbdetailVO.getHeaderId() );

            // 如果vendorId不存在则添加
            if ( KANUtil.filterEmpty( sbHeaderVO.getVendorId() ) != null )
            {
               if ( !isVendorIdExist( sbHeaderVO.getVendorId(), vendorIds ) )
               {
                  vendorIds.add( sbHeaderVO.getVendorId() );
               }
            }
         }
      }

      return vendorIds;
   }

   /**  
    * IsVendorIdExist
    *	判断供应商ID是否存在
    *	@param vendorId
    *	@param vendorIds
    *	@return
    */
   private boolean isVendorIdExist( final String vendorId, final List< String > vendorIds )
   {
      for ( String tempVendorId : vendorIds )
      {
         if ( vendorId.equals( tempVendorId ) )
         {
            return true;
         }
      }
      return false;
   }

   // 尝试提交批次 - 提交子对象但不清楚父对象是否提交的情况使用
   // Reviewed by Kevin Jin at 2013-11-04
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // 初始化SBBatchVO
         final SBBatchVO sbBatchVO = ( ( SBBatchDao ) getDao() ).getSBBatchVOByBatchId( batchId );
         // 初始化SBHeaderVO列表
         final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( batchId );

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
                  double amountCompany = 0;
                  double amountPersonal = 0;

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

                     // 累计社保公司部分和个人部分
                     amountCompany = amountCompany
                           + Double.valueOf( KANUtil.filterEmpty( sbDetailVO.getAmountCompany() ) != null ? KANUtil.filterEmpty( sbDetailVO.getAmountCompany() ) : "0" );
                     amountPersonal = amountPersonal
                           + Double.valueOf( KANUtil.filterEmpty( sbDetailVO.getAmountPersonal() ) != null ? KANUtil.filterEmpty( sbDetailVO.getAmountPersonal() ) : "0" );
                  }

                  if ( detailFlag == sbDetailVOs.size() )
                  {
                     // 如果社保方案明细已全部是需要更改的状态且状态不是全都比要修改的状态值大，修改社保方案的状态
                     if ( upperCount != sbDetailVOs.size() )
                     {
                        sbHeaderVO.setStatus( status );
                     }
                     sbHeaderVO.setModifyBy( userId );
                     sbHeaderVO.setModifyDate( new Date() );

                     // 如果是补缴的社保不做处理
                     if ( KANUtil.filterEmpty( status ) != null && "3".equals( status ) && !"7".equals( sbHeaderVO.getSbStatus() ) )
                     {
                        final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );

                        boolean updated = false;

                        // “待申报加保”状态变为“正常缴纳”
                        if ( employeeContractSBVO.getStatus().equals( "2" ) )
                        {
                           employeeContractSBVO.setStatus( "3" );
                           sbHeaderVO.setSbStatus( "3" );
                           updated = true;
                        }
                        // “待申报退保”状态变为“已退保” - 个人部分和公司部分有值的不变化
                        else if ( employeeContractSBVO.getStatus().equals( "5" ) && amountCompany == 0 && amountPersonal == 0 )
                        {
                           employeeContractSBVO.setStatus( "6" );
                           sbHeaderVO.setSbStatus( "6" );
                           updated = true;
                        }

                        if ( updated )
                        {
                           this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
                        }
                     }

                     this.getSbHeaderDao().updateSBHeader( sbHeaderVO );
                     submitRows++;
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
               ( ( SBBatchDao ) getDao() ).updateSBBatch( sbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   // 尝试退回批次 - 退回子对象但不清楚父对象是否退回的情况使用
   // Reviewed by Kevin Jin at 2013-11-04
   private int tryRollbackBatch( final String batchId ) throws KANException
   {
      int rollbackRows = 0;

      // 初始化SBBatchVO
      final SBBatchVO sbBatchVO = ( ( SBBatchDao ) getDao() ).getSBBatchVOByBatchId( batchId );
      // 初始化SBHeaderVO列表
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( batchId );

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         int headerFlag = 0;

         // 遍历
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // 初始化SBDetailVO列表
            final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );

            if ( sbDetailVOs == null || sbDetailVOs.size() == 0 )
            {
               this.sbHeaderDao.deleteSBHeader( sbHeaderVO.getHeaderId() );
               headerFlag++;
               rollbackRows++;
            }
         }

         if ( sbHeaderVOs.size() == headerFlag )
         {
            ( ( SBBatchDao ) getDao() ).deleteSBBatch( sbBatchVO.getBatchId() );
            rollbackRows++;
         }
      }
      else
      {
         ( ( SBBatchDao ) getDao() ).deleteSBBatch( sbBatchVO.getBatchId() );
         rollbackRows++;
      }

      return rollbackRows;
   }

   /**  
    * GetSBDTOsByCondition
    *	 获得SBDTO
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException
   {
      // 获得查询对象
      final SBBatchVO sbBatchVO = ( SBBatchVO ) pagedListHolder.getObject();
      // 获得 PageFlag
      final String pageFlag = sbBatchVO.getPageFlag();
      // 初始化List SBDTO
      List< Object > sbDTOs = new ArrayList< Object >();

      // 按照PageFlag提交
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
      {
         sbDTOs = getBatchSBDTO( sbBatchVO );
      }
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
      {
         sbDTOs = getHeaderSBDTO( sbBatchVO );
      }

      pagedListHolder.setSource( sbDTOs );

      return pagedListHolder;
   }

   /**  
    * GetBatchSBDTO
    *	多个批次获得SBDTO集合
    *	@param sbBatchVO
    *	@return
    * @throws KANException 
    */
   private List< Object > getBatchSBDTO( final SBBatchVO sbBatchVO ) throws KANException
   {
      // 获得勾选ID数组
      final String selectedIds = sbBatchVO.getSelectedIds();
      // 初始化选择ID集合
      String[] selectedIdArray = { "" };

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         selectedIdArray = selectedIds.split( "," );
      }

      for ( String selectId : selectedIdArray )
      {
         // 初始化SBDTO List
         final List< Object > sbDTOs = new ArrayList< Object >();
         // 初始化查询对象
         SBBatchVO tempSBBatchVO = new SBBatchVO();
         // 设置属性值
         tempSBBatchVO.setBatchId( KANUtil.decodeStringFromAjax( selectId ) );
         tempSBBatchVO.setAccountId( sbBatchVO.getAccountId() );
         tempSBBatchVO.setEntityId( sbBatchVO.getEntityId() );
         tempSBBatchVO.setBusinessTypeId( sbBatchVO.getBusinessTypeId() );
         tempSBBatchVO.setCityId( sbBatchVO.getCityId() );
         tempSBBatchVO.setOrderId( sbBatchVO.getOrderId() );
         tempSBBatchVO.setContractId( sbBatchVO.getContractId() );
         tempSBBatchVO.setMonthly( sbBatchVO.getMonthly() );
         tempSBBatchVO.setCorpId( sbBatchVO.getCorpId() );

         tempSBBatchVO.setRulePublic( sbBatchVO.getRulePublic() );
         tempSBBatchVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
         tempSBBatchVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
         tempSBBatchVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
         tempSBBatchVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
         tempSBBatchVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

         // 查询符合所有条件的SBBatchVO集合
         final List< Object > sbBatchVOs = ( ( SBBatchDao ) getDao() ).getSBBatchVOsByCondition( tempSBBatchVO );

         // 初始化SBHeaderVO List
         List< Object > sbHeaderVOs = new ArrayList< Object >();

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            for ( Object sbBatchVOObject : sbBatchVOs )
            {
               // 初始化查询对象
               final SBHeaderVO sbHeaderVO = new SBHeaderVO();
               // 设置属性值
               sbHeaderVO.setBatchId( ( ( SBBatchVO ) sbBatchVOObject ).getBatchId() );
               sbHeaderVO.setStatus( sbBatchVO.getStatus() );
               sbHeaderVO.setVendorId( sbBatchVO.getVendorId() );
               sbHeaderVO.setAccountId( sbBatchVO.getAccountId() );
               sbHeaderVO.setCorpId( sbBatchVO.getCorpId() );

               sbHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
               sbHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
               sbHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
               sbHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
               sbHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
               sbHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );
               sbHeaderVOs.addAll( this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO ) );
            }
         }

         // 初始化包含所有科目的集合
         final List< ItemVO > items = new ArrayList< ItemVO >();

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            for ( Object sbHeaderVOObject : sbHeaderVOs )
            {
               // 初始化缓存对象
               final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

               tempSBHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
               tempSBHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
               tempSBHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
               tempSBHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
               tempSBHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
               tempSBHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

               // 计算合计
               countAmount( tempSBHeaderVO );
               // 初始化SBDTO
               final SBDTO sbDTO = new SBDTO();

               // 装载SBHeaderVO
               sbDTO.setSbHeaderVO( tempSBHeaderVO );

               // 装载SBDetailVO List
               fetchSBDetail( sbDTO, tempSBHeaderVO, sbBatchVO.getStatus(), items );

               Object sbDTOObject = sbDTO;
               sbDTOs.add( sbDTOObject );
            }

            // SBDTO不存在科目添加空值
            if ( sbDTOs != null && sbDTOs.size() > 0 )
            {
               fillSBDTOs( sbDTOs, items );
            }

            return sbDTOs;
         }
      }

      return null;
   }

   /**  
    * GetHeaderSBDTO
    *	单个批次获得SBDTO集合
    *	@param sbBatchVO
    *	@return
    *	@throws KANException
    */
   private List< Object > getHeaderSBDTO( final SBBatchVO sbBatchVO ) throws KANException
   {
      // 获得勾选ID数组
      final String selectedIds = sbBatchVO.getSelectedIds();
      // 初始化SBDTO List
      final List< Object > sbDTOs = new ArrayList< Object >();
      // 初始化包含所有科目的集合
      final List< ItemVO > items = new ArrayList< ItemVO >();

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         final String[] selectedIdArray = selectedIds.split( "," );
         for ( String selectId : selectedIdArray )
         {
            // 初始化查询对象
            final SBHeaderVO sbHeaderVO = new SBHeaderVO();
            // 设置属性值
            sbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( sbBatchVO.getBatchId() ) );
            sbHeaderVO.setHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
            sbHeaderVO.setStatus( sbBatchVO.getStatus() );
            sbHeaderVO.setAccountId( sbBatchVO.getAccountId() );
            sbHeaderVO.setCorpId( sbBatchVO.getCorpId() );
            sbHeaderVO.setVendorId( sbBatchVO.getVendorId() );

            sbHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
            sbHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
            sbHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
            sbHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
            sbHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
            sbHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

            // 多选框社保状态处理 Add by siuxia
            if ( sbBatchVO.getSbStatusArray() != null && sbBatchVO.getSbStatusArray().length > 0 )
            {
               sbHeaderVO.setSbStatus( KANUtil.toJasonArray( sbBatchVO.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
            }
            // 初始化SBHeaderVO List
            final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO );

            if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
            {
               for ( Object sbHeaderVOObject : sbHeaderVOs )
               {
                  // 初始化缓存对象
                  final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

                  tempSBHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
                  tempSBHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
                  tempSBHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
                  tempSBHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
                  tempSBHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
                  tempSBHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

                  // 计算合计
                  countAmount( tempSBHeaderVO );
                  // 初始化SBDTO
                  final SBDTO sbDTO = new SBDTO();

                  // 装载SBHeaderVO
                  sbDTO.setSbHeaderVO( tempSBHeaderVO );

                  // 装载SBDetailVO List
                  fetchSBDetail( sbDTO, tempSBHeaderVO, sbHeaderVO.getStatus(), items );

                  Object sbDTOObject = sbDTO;
                  sbDTOs.add( sbDTOObject );
               }
            }

         }
      }
      else
      {
         // 初始化查询对象
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         // 设置属性值
         sbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( sbBatchVO.getBatchId() ) );
         sbHeaderVO.setStatus( sbBatchVO.getStatus() );
         sbHeaderVO.setAccountId( sbBatchVO.getAccountId() );
         sbHeaderVO.setCorpId( sbBatchVO.getCorpId() );
         sbHeaderVO.setVendorId( sbBatchVO.getVendorId() );

         sbHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
         sbHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
         sbHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
         sbHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
         sbHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
         sbHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

         // 多选框社保状态处理 Add by siuxia
         if ( sbBatchVO.getSbStatusArray() != null && sbBatchVO.getSbStatusArray().length > 0 )
         {
            sbHeaderVO.setSbStatus( KANUtil.toJasonArray( sbBatchVO.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         // 初始化SBHeaderVO List
         final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            for ( Object sbHeaderVOObject : sbHeaderVOs )
            {
               // 初始化缓存对象
               final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

               tempSBHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
               tempSBHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
               tempSBHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
               tempSBHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
               tempSBHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
               tempSBHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

               // 计算合计
               countAmount( tempSBHeaderVO );
               // 初始化SBDTO
               final SBDTO sbDTO = new SBDTO();

               // 装载SBHeaderVO
               sbDTO.setSbHeaderVO( tempSBHeaderVO );

               // 装载SBDetailVO List
               fetchSBDetail( sbDTO, tempSBHeaderVO, sbHeaderVO.getStatus(), items );

               Object sbDTOObject = sbDTO;
               sbDTOs.add( sbDTOObject );
            }
         }

      }

      // SBDTO不存在科目添加空值
      if ( sbDTOs != null && sbDTOs.size() > 0 )
      {
         fillSBDTOs( sbDTOs, items );
      }

      return sbDTOs;
   }

   // 装载社保明细
   private void fetchSBDetail( final SBDTO sbDTO, SBHeaderVO sbHeaderVO, final String status, final List< ItemVO > items ) throws KANException
   {
      // 初始化并装载社保明细
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

            // 初始化科目
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( sbDetailVO.getItemId() );
            itemVO.setItemType( sbDetailVO.getItemType() );

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
    *	判断ItemVO是否存在社保明细数组
    *	@param itemVO
    *	@param items
    *	@return
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
   public String getSBToApplyForMoreStatusCountByBatchIds( final String[] batchId ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).getSBToApplyForMoreStatusCountByBatchIds( batchId ) + "";
   }

   @Override
   public String getSBToApplyForResigningStatusCountByBatchIds( String[] batchId ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).getSBToApplyForResigningStatusCountByBatchIds( batchId ) + "";
   }
   @Override
   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException
   {
      // TODO Auto-generated method stub
      // 获得ActionFlag
      final String actionFlag = baseVO.getSubAction();
      //确认时才有工作流
      if (StringUtils.isBlank( actionFlag )|| !actionFlag.equalsIgnoreCase( BaseAction.CONFIRM_OBJECTS ) )
      {
        return;
      }
      final HistoryVO history = baseVO.getHistoryVO();
      //通过执行
      //history.setPassObject( KANUtil.toJSONObject( baseVO ).toString() );

      //对象的id 可以是多个以逗号分隔。这里是勾选的多个id
      String selectIdString = ( ( SBBatchVO ) baseVO ).getSelectedIds();
      if(StringUtils.isBlank( selectIdString )){
         return;
      }
      final String[] selectedIdArray = selectIdString.split( "," );
      String objectIdString = "";
      for (int i = 0 ;i<selectedIdArray.length;i++)
      {
         // 解密
         objectIdString +=KANUtil.decodeStringFromAjax( selectedIdArray[i] )+",";
      }
      if(StringUtils.isNotBlank( objectIdString )&&objectIdString.length()>0){
         objectIdString = objectIdString.substring(0, objectIdString.length()-1 );
      }
      history.setObjectId( objectIdString );
      //history.setFailObject( KANUtil.toJSONObject( baseVO ).toString() );

      history.setAccessAction( SBAction.ACCESS_ACTION_CONFIRM );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( SBAction.ACCESS_ACTION_CONFIRM ) );
      history.setRightId( KANConstants.MODULE_RIGHT_CONFIRM );
      //表示的是新工作流
      history.setObjectType( "3" );
      history.setServiceBean( "sbBatchService" );
      final String pageFlag = ( ( SBBatchVO ) baseVO ).getPageFlag();
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
      {
         history.setRemark4( "sbBatch" );
      }
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) ){
         history.setRemark4( "sbHeader" );
      }
      //      history.setObjectClass( OBJECT_CLASS );
      //      history.setServiceGetObjByIdMethod( "getLeaveHeaderVOByLeaveHeaderId" );

      // 表示是工作流的
      //history.setObjectType( "2" );
      //      history.setAccountId( baseVO.getAccountId() );
      //      final String batchId = (( SBBatchVO ) baseVO ).getBatchId();
      //      history.setObjectId( batchId );
      //      leaveHeaderVO.getLeaveHeaderId() 
      //      SBBatchVO sbBatchVO = (SBBatchVO)baseVO;
      //      history.setNameZH( leaveHeaderVO.getEmployeeNameZH() );
      //      history.setNameEN( leaveHeaderVO.getEmployeeNameEN() );
      //      history.setOwner( sbBatchVO.get );
   }

}
