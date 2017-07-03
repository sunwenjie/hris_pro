package com.kan.hro.service.impl.biz.cb;

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
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.cb.CBBatchDao;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDTO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.service.inf.biz.cb.CBBatchService;
import com.kan.hro.service.inf.biz.sb.SBBatchService;

public class CBBatchServiceImpl extends ContextService implements CBBatchService
{

   // 注入CBHeaderDao
   private CBHeaderDao cbHeaderDao;

   // 注入CBDetailDao
   private CBDetailDao cbDetailDao;

   // 注入EmployeeContractCBDao
   private EmployeeContractCBDao employeeContractCBDao;

   public CBHeaderDao getCbHeaderDao()
   {
      return cbHeaderDao;
   }

   public void setCbHeaderDao( CBHeaderDao cbHeaderDao )
   {
      this.cbHeaderDao = cbHeaderDao;
   }

   public CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   public final EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public final void setEmployeeContractCBDao( EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   @Override
   public PagedListHolder getCBBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CBBatchDao cbBatchDao = ( CBBatchDao ) getDao();
      pagedListHolder.setHolderSize( cbBatchDao.countCBBatchVOsByCondition( ( CBBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( cbBatchDao.getCBBatchVOsByCondition( ( CBBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cbBatchDao.getCBBatchVOsByCondition( ( CBBatchVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object cbBatchVOObject : pagedListHolder.getSource() )
         {
            final CBBatchVO cbBatchVO = ( CBBatchVO ) cbBatchVOObject;
            fetchCBBatchVO( cbBatchVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * FetchCBBatchVO
    * 计算公司及个人数值合计
    * @param pagedListHolder
    * @throws KANException
    */
   private void fetchCBBatchVO( final CBBatchVO cbBatchVO ) throws KANException
   {
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByBatchId( cbBatchVO.getBatchId() );
      // 初始化包含的雇员集合 ( 去重复 )
      final Set< MappingVO > employeeMappingVOSet = new HashSet< MappingVO >();
      // 计算数据合计
      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {

         // 初始化合计值
         BigDecimal amountSalesCost = new BigDecimal( 0 );
         BigDecimal amountSalesPrice = new BigDecimal( 0 );
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;
            amountSalesCost = amountSalesCost.add( new BigDecimal( cbDetailVO.getAmountSalesCost() ) );
            amountSalesPrice = amountSalesPrice.add( new BigDecimal( cbDetailVO.getAmountSalesPrice() ) );

            // 如果是“新建”、“批准”添加对应状态员工
            if ( "1".equals( cbBatchVO.getAdditionalStatus() ) || "2".equals( cbBatchVO.getAdditionalStatus() ) )
            {
               if ( cbDetailVO.getStatus().equals( cbBatchVO.getAdditionalStatus() ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( cbDetailVO.getEmployeeId() );
                  mappingVO.setMappingValue( cbDetailVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( cbDetailVO.getEmployeeNameEN() );
                  employeeMappingVOSet.add( mappingVO );
               }
            }
            // 其他情况添加“确认”、“提交”、“已结算”状态员工
            else
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( cbDetailVO.getEmployeeId() );
               mappingVO.setMappingValue( cbDetailVO.getEmployeeNameZH() );
               mappingVO.setMappingTemp( cbDetailVO.getEmployeeNameEN() );
               employeeMappingVOSet.add( mappingVO );
            }
         }
         final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >( employeeMappingVOSet );
         cbBatchVO.setEmployees( employeeMappingVOs );
         cbBatchVO.setAmountSalesCost( amountSalesCost.toString() );
         cbBatchVO.setAmountSalesPrice( amountSalesPrice.toString() );
      }

   }

   /**  
    * countAmount
    * 计算公司及个人数值合计
    * @param cbHeaderVO
    * @throws KANException
    */
   private void countAmount( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

      // 计算数据合计
      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         // 初始化合计值
         BigDecimal amountSalesCost = new BigDecimal( 0 );
         BigDecimal amountSalesPrice = new BigDecimal( 0 );
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;
            amountSalesCost = amountSalesCost.add( new BigDecimal( cbDetailVO.getAmountSalesCost() ) );
            amountSalesPrice = amountSalesPrice.add( new BigDecimal( cbDetailVO.getAmountSalesPrice() ) );
         }
         cbHeaderVO.setAmountSalesCost( amountSalesCost.toString() );
         cbHeaderVO.setAmountSalesPrice( amountSalesPrice.toString() );
      }

   }

   @Override
   public List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException
   {
      List< Object > cbBatchObjects = ( ( CBBatchDao ) getDao() ).getCBBatchVOsByCondition( cbBatchVO );

      if ( cbBatchObjects != null && cbBatchObjects.size() > 0 )
      {
         for ( Object cbBatchVOObject : cbBatchObjects )
         {
            CBBatchVO tempCBBatchVO = ( CBBatchVO ) cbBatchVOObject;
            // 计算合计值
            fetchCBBatchVO( tempCBBatchVO );
         }
      }

      return cbBatchObjects;
   }

   @Override
   public CBBatchVO getCBBatchVOByBatchId( final String batchId ) throws KANException
   {
      final CBBatchVO cbBatchVO = ( ( CBBatchDao ) getDao() ).getCBBatchVOByBatchId( batchId );

      if ( cbBatchVO != null )
      {
         // 计算合计值
         fetchCBBatchVO( cbBatchVO );
      }

      return cbBatchVO;
   }

   @Override
   public int updateCBBatch( final CBBatchVO cbBatchVO ) throws KANException
   {
      return ( ( CBBatchDao ) getDao() ).updateCBBatch( cbBatchVO );
   }

   @Override
   public int insertCBBatch( final CBBatchVO cbBatchVO ) throws KANException
   {
      return ( ( CBBatchDao ) getDao() ).insertCBBatch( cbBatchVO );
   }

   @Override
   public int rollback( final CBBatchVO cbBatchVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 获得PageFlag、选中项Id、状态
         final String pageFlag = cbBatchVO.getPageFlag();
         final String selectedIds = cbBatchVO.getSelectedIds();
         final String status = cbBatchVO.getStatus();

         if ( selectedIds != null && !selectedIds.trim().isEmpty() )
         {
            // 生成ID数组
            final String[] selectedIdArray = selectedIds.split( "," );

            rows = selectedIdArray.length;

            // 初始化BatchId列表
            final List< String > batchIds = new ArrayList< String >();

            // 遍历Id数组
            for ( String encodedSelectedId : selectedIdArray )
            {
               // 初始化BatchId
               String batchId = null;
               // Id解码
               final String selectedId = KANUtil.decodeStringFromAjax( encodedSelectedId );

               // 按照PageFlag退回
               if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_BATCH ) )
               {
                  batchId = rollbackBatch( selectedId, status );
               }
               else if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_HEADER ) )
               {
                  batchId = rollbackHeader( selectedId, status );
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
    * Rollback Batch
    * 根据批次Id删除商保
    * @param selectId
    * @param userId
    * @throws KANException
    */
   private String rollbackBatch( final String batchId, final String status ) throws KANException
   {
      // 根据批次Id获得对应的CBHeaderVO List
      final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

      // 如果数组不为空
      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         // 遍历CBHeaderVO List以删除
         for ( Object objectCBHeaderVO : cbHeaderVOs )
         {
            // 删除CBHeaderVO
            rollbackHeader( ( ( CBHeaderVO ) objectCBHeaderVO ).getHeaderId(), status );
         }
      }

      return batchId;
   }

   /**  
    * Rollback Header
    * 退回商保方案Id
    * @param cbBatchVO
    * @throws KANException
    */
   private String rollbackHeader( final String headerId, final String status ) throws KANException
   {
      // 初始化CBDetailVO列表
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      // 遍历
      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         for ( Object objectCBDetailVO : cbDetailVOs )
         {
            // 删除CBDetailVO
            rollbackDetail( ( ( CBDetailVO ) objectCBDetailVO ).getDetailId(), status );
         }
      }

      // 删除CBHeaderVO
      final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( headerId );

      if ( cbHeaderVO != null )
      {
         return cbHeaderVO.getBatchId();
      }

      return "";
   }

   /**  
    * Rollback CBDetail
    * 退回商保方案明细
    * @param selectId
    * @param userId
    * @throws KANException
    */
   private String rollbackDetail( final String detailId, final String status ) throws KANException
   {
      // 删除SBDetailVO
      final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( detailId );

      if ( cbDetailVO != null && cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( status ) && ( Integer.parseInt( cbDetailVO.getStatus() ) ) <= 2 )
      {
         // 删除CBDetailVO
         this.getCbDetailDao().deleteCBDetail( detailId );

         final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

         if ( cbHeaderVO != null )
         {
            return cbHeaderVO.getBatchId();
         }
      }

      return "";
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-09
   public int insertCBBatch( final CBBatchVO cbBatchVO, final List< ServiceContractDTO > serviceContractDTOs ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 如果商保HeaderVO和DetailVO存在的话则插入数据库
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            // 遍历ServiceContractDTOs
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               // 获得CBDTO List
               final List< CBDTO > cbDTOs = serviceContractDTO.getCbDTOs();

               if ( cbDTOs != null && cbDTOs.size() > 0 )
               {
                  if ( rows == 0 )
                  {
                     // 添加批次
                     insertCBBatch( cbBatchVO );

                     // 一个批次添加成功
                     rows = 1;
                  }

                  for ( CBDTO cbDTO : cbDTOs )
                  {
                     // 获得CBHeaderVO
                     final CBHeaderVO cbHeaderVO = cbDTO.getCbHeaderVO();

                     // 获得CBDetailVO List
                     final List< CBDetailVO > cbdetailVOs = cbDTO.getCbDetailVOs();

                     if ( cbHeaderVO != null && cbdetailVOs != null && cbdetailVOs.size() > 0 )
                     {
                        // 设置BatchId
                        cbHeaderVO.setBatchId( cbBatchVO.getBatchId() );
                        // 插入CBHeaderVO
                        this.getCbHeaderDao().insertCBHeader( cbHeaderVO );

                        // 遍历CBDetailVO List
                        for ( CBDetailVO cbDetailVO : cbdetailVOs )
                        {
                           // 设置HeaderId
                           cbDetailVO.setHeaderId( cbHeaderVO.getHeaderId() );
                           // 插入CBDetailVO
                           this.getCbDetailDao().insertCBDetail( cbDetailVO );
                        }
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( cbBatchVO.getBatchId() ) != null )
         {
            // 批次执行结束时间
            cbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            cbBatchVO.setDeleted( CBBatchVO.TRUE );
            cbBatchVO.setStatus( CBBatchVO.TRUE );
            // 修改批次
            updateCBBatch( cbBatchVO );
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
   public int submit( final CBBatchVO cbBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // 开启事务
         startTransaction();
         // 获得勾选ID数组
         final String selectedIds = cbBatchVO.getSelectedIds();
         // 获得 PageFlag
         final String pageFlag = cbBatchVO.getPageFlag();
         // 获得subAction
         final String subAction = cbBatchVO.getSubAction();

         // 如果有选择项
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = selectedIds.split( "," );
            // 提交记录数
            submitRows = selectedIdArray.length;

            // 实例化字符串表示要修改的状态值status
            String status = "0";
            // 根据actFlag获取要修改的状态值status
            if ( subAction.equalsIgnoreCase( BaseAction.APPROVE_OBJECTS ) )
            {
               status = "2";
            }
            // 如果是确认
            else if ( subAction.equalsIgnoreCase( BaseAction.CONFIRM_OBJECTS ) )
            {
               status = "3";
            }
            // 如果是提交
            else if ( subAction.equalsIgnoreCase( BaseAction.SUBMIT_OBJECTS ) )
            {
               status = "4";
            }

            // 初始化BatchId列表
            final List< String > batchIds = new ArrayList< String >();

            // 遍历
            for ( String encodedSelectId : selectedIdArray )
            {
               // 初始化BatchId
               String batchId = null;
               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // 根据PageFlag 提交申请
               if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_BATCH ) )
               {
                  batchId = submitBatch( selectId, status, cbBatchVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
               {
                  batchId = submitHeader( selectId, status, cbBatchVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
               {
                  batchId = submitDetail( selectId, status, cbBatchVO.getModifyBy() );
               }

               if ( KANUtil.filterEmpty( batchId ) != null && !batchIds.contains( batchId ) )
               {
                  batchIds.add( batchId );
               }
            }

            // 尝试更改其父对象状态
            if ( batchIds != null && batchIds.size() > 0 )
            {
               for ( String batchId : batchIds )
               {
                  trySubmitBatch( batchId, status, cbBatchVO.getModifyBy() );
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

   // 更改批次
   private String submitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      // 初始化 CBHeaderVO数组
      final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

      // 如果批次对应的CBHeaderVO数组不为空
      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         // 遍历
         for ( Object cbHeaderVOObject : cbHeaderVOs )
         {
            final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            submitHeader( cbHeaderVO.getHeaderId(), status, userId );
         }
      }

      return batchId;
   }

   // 更改商保方案
   private String submitHeader( final String headerId, final String status, final String userId ) throws KANException
   {
      // 初始化  DetailVO List -- By HeaderId
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         // 遍历objectCBDetailVOList以修改状态
         for ( Object objectCBDetailVO : cbDetailVOs )
         {
            final CBDetailVO cbDetailVO = ( CBDetailVO ) objectCBDetailVO;
            // 更改明细
            submitDetail( cbDetailVO.getDetailId(), status, userId );
         }
      }

      // 更改CBHeaderVO
      final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( headerId );

      if ( cbHeaderVO != null )
      {
         return cbHeaderVO.getBatchId();
      }

      return "";
   }

   // 更改商保方案明细
   private String submitDetail( final String detailId, final String status, final String userId ) throws KANException
   {
      final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( detailId );

      if ( cbDetailVO != null && cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) )
      {
         cbDetailVO.setStatus( status );
         cbDetailVO.setModifyBy( userId );
         cbDetailVO.setModifyDate( new Date() );
         this.getCbDetailDao().updateCBDetail( cbDetailVO );

         final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

         if ( cbHeaderVO != null )
         {
            return cbHeaderVO.getBatchId();
         }
      }

      return "";
   }

   // 尝试提交批次 - 提交子对象但不清楚父对象是否提交的情况使用
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // 初始化CBBatchVO
         final CBBatchVO cbBatchVO = ( ( CBBatchDao ) getDao() ).getCBBatchVOByBatchId( batchId );
         // 初始化CBHeaderVO列表
         final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;

            // 遍历
            for ( Object cbHeaderVOObject : cbHeaderVOs )
            {
               final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;

               // 初始化CBDetailVO列表
               final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

               if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
               {
                  int detailFlag = 0;
                  int upperCount = 0;

                  // 遍历
                  for ( Object cbDetailVOObject : cbDetailVOs )
                  {
                     final CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;

                     if ( KANUtil.filterEmpty( cbDetailVO.getStatus() ) != null && Integer.valueOf( cbDetailVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                        if ( Integer.valueOf( cbDetailVO.getStatus() ) > Integer.valueOf( status ) )
                        {
                           upperCount++;
                        }
                     }
                  }

                  // 如果商保方案明细已全部大于等于目标状态，修改商保方案的状态
                  if ( detailFlag == cbDetailVOs.size() )
                  {
                     if ( upperCount != cbDetailVOs.size() )
                     {
                        cbHeaderVO.setStatus( status );
                     }
                     cbHeaderVO.setModifyBy( userId );
                     cbHeaderVO.setModifyDate( new Date() );

                     // 商保确认
                     if ( KANUtil.filterEmpty( status ) != null && status.equals( "3" ) )
                     {
                        final EmployeeContractCBVO employeeContractCBVO = this.getEmployeeContractCBDao().getEmployeeContractCBVOByEmployeeCBId( cbHeaderVO.getEmployeeCBId() );

                        boolean updated = false;

                        // “待申购”状态变为“正常购买”
                        if ( "2".equals( cbHeaderVO.getCbStatus() ) )
                        {
                           //根据员工商保状态修改
                           if ( employeeContractCBVO.getStatus().equals( "2" ) )
                           {
                              employeeContractCBVO.setStatus( "3" );
                              updated = true;
                           }
                           cbHeaderVO.setCbStatus( "3" );

                        }
                        // “待退购”状态变为“已退购”
                        else if ( "5".equals( cbHeaderVO.getCbStatus() ) )
                        {
                           //根据员工商保状态修改
                           if ( employeeContractCBVO.getStatus().equals( "5" ) )
                           {
                              employeeContractCBVO.setStatus( "6" );
                              updated = true;
                           }
                           cbHeaderVO.setCbStatus( "6" );
                           
                        }

                        if ( updated )
                        {
                           this.getEmployeeContractCBDao().updateEmployeeContractCB( employeeContractCBVO );
                        }
                     }

                     this.getCbHeaderDao().updateCBHeader( cbHeaderVO );
                     submitRows++;
                  }
               }

               if ( cbHeaderVO.getStatus() != null && !cbHeaderVO.getStatus().isEmpty() && Integer.valueOf( cbHeaderVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }
            }

            // 如果商保方案已全部是需要更改的状态，修改商保批次的状态
            if ( headerFlag == cbHeaderVOs.size() )
            {
               cbBatchVO.setStatus( status );
               cbBatchVO.setModifyBy( userId );
               cbBatchVO.setModifyDate( new Date() );
               ( ( CBBatchDao ) getDao() ).updateCBBatch( cbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   // 尝试退回批次 - 退回子对象但不清楚父对象是否退回的情况使用
   private int tryRollbackBatch( final String batchId ) throws KANException
   {
      int rollbackRows = 0;

      // 初始化CBBatchVO
      final CBBatchVO cbBatchVO = ( ( CBBatchDao ) getDao() ).getCBBatchVOByBatchId( batchId );
      // 初始化CBHeaderVO列表
      final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         int headerFlag = 0;

         // 遍历
         for ( Object cbHeaderVOObject : cbHeaderVOs )
         {
            final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;

            // 初始化CBDetailVO列表
            final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

            if ( cbDetailVOs == null || cbDetailVOs.size() == 0 )
            {
               this.getCbHeaderDao().deleteCBHeader( cbHeaderVO.getHeaderId() );
               headerFlag++;
               rollbackRows++;
            }
         }

         if ( cbHeaderVOs.size() == headerFlag )
         {
            ( ( CBBatchDao ) getDao() ).deleteCBBatch( cbBatchVO.getBatchId() );
            rollbackRows++;
         }
      }
      else
      {
         ( ( CBBatchDao ) getDao() ).deleteCBBatch( cbBatchVO.getBatchId() );
         rollbackRows++;
      }

      return rollbackRows;
   }

   /**  
    * GetCBDTOsByCondition
    *  获得CBDTO
    * @param pagedListHolder
    * @param isPaged
    * @return
    * @throws KANException
    */
   @Override
   public PagedListHolder getCBDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException
   {
      // 获得查询对象
      final CBBatchVO cbBatchVO = ( CBBatchVO ) pagedListHolder.getObject();
      // 获得 PageFlag
      final String pageFlag = cbBatchVO.getPageFlag();
      // 初始化List CBDTO
      List< Object > cbDTOs = new ArrayList< Object >();

      // 按照PageFlag提交
      if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_BATCH ) )
      {
         cbDTOs = getBatchCBDTO( cbBatchVO );
      }
      else if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_HEADER ) )
      {
         cbDTOs = getHeaderCBDTO( cbBatchVO );
      }

      pagedListHolder.setSource( cbDTOs );

      return pagedListHolder;
   }

   /**  
    * GetBatchCBDTO
    * 多个批次获得CBDTO集合
    * @param cbBatchVO
    * @return
    * @throws KANException 
    */
   private List< Object > getBatchCBDTO( final CBBatchVO cbBatchVO ) throws KANException
   {
      // 获得勾选ID数组
      final String selectedIds = cbBatchVO.getSelectedIds();
      // 初始化选择ID集合
      String[] selectedIdArray = { "" };

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         selectedIdArray = selectedIds.split( "," );
      }

      for ( String selectId : selectedIdArray )
      {
         // 初始化CBDTO List
         final List< Object > cbDTOs = new ArrayList< Object >();
         // 初始化查询对象
         CBBatchVO tempCBBatchVO = new CBBatchVO();
         // 设置属性值
         tempCBBatchVO.setBatchId( KANUtil.decodeStringFromAjax( selectId ) );
         tempCBBatchVO.setAccountId( cbBatchVO.getAccountId() );
         tempCBBatchVO.setEntityId( cbBatchVO.getEntityId() );
         tempCBBatchVO.setCorpId( cbBatchVO.getCorpId() );
         tempCBBatchVO.setBusinessTypeId( cbBatchVO.getBusinessTypeId() );
         tempCBBatchVO.setOrderId( cbBatchVO.getOrderId() );
         tempCBBatchVO.setContractId( cbBatchVO.getContractId() );
         tempCBBatchVO.setMonthly( cbBatchVO.getMonthly() );
         // 查询符合所有条件的CBBatchVO集合
         final List< Object > cbBatchVOs = ( ( CBBatchDao ) getDao() ).getCBBatchVOsByCondition( tempCBBatchVO );

         // 初始化CBHeaderVO List
         List< Object > cbHeaderVOs = new ArrayList< Object >();

         if ( cbBatchVOs != null && cbBatchVOs.size() > 0 )
         {
            for ( Object cbBatchVOObject : cbBatchVOs )
            {
               // 初始化查询对象
               final CBHeaderVO cbHeaderVO = new CBHeaderVO();
               // 设置属性值
               cbHeaderVO.setBatchId( ( ( CBBatchVO ) cbBatchVOObject ).getBatchId() );
               cbHeaderVO.setStatus( cbBatchVO.getStatus() );
               cbHeaderVO.setCbId( cbBatchVO.getCbId() );
               cbHeaderVO.setAccountId( cbBatchVO.getAccountId() );
               cbHeaderVO.setCorpId( cbBatchVO.getCorpId() );
               cbHeaderVOs.addAll( this.getCbHeaderDao().getCBHeaderVOsByCondition( cbHeaderVO ) );
            }
         }

         // 初始化包含所有科目的集合
         final List< ItemVO > items = new ArrayList< ItemVO >();

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            for ( Object cbHeaderVOObject : cbHeaderVOs )
            {
               // 初始化缓存对象
               final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
               // 计算合计
               countAmount( tempCBHeaderVO );
               // 初始化CBDTO
               final CBDTO cbDTO = new CBDTO();

               // 装载CBHeaderVO
               cbDTO.setCbHeaderVO( tempCBHeaderVO );

               // 装载CBDetailVO List
               fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), tempCBBatchVO.getStatus(), items );

               Object cbDTOObject = cbDTO;
               cbDTOs.add( cbDTOObject );
            }

            // CBDTO不存在科目添加空值
            if ( cbDTOs != null && cbDTOs.size() > 0 )
            {
               fillCBDTOs( cbDTOs, items );
            }

            return cbDTOs;
         }
      }

      return null;
   }

   /**  
    * GetHeaderCBDTO
    * 单个批次获得CBDTO集合
    * @param cbBatchVO
    * @return
    * @throws KANException
    */
   private List< Object > getHeaderCBDTO( final CBBatchVO cbBatchVO ) throws KANException
   {
      // 获得勾选ID数组
      final String selectedIds = cbBatchVO.getSelectedIds();
      // 初始化CBDTO List
      final List< Object > cbDTOs = new ArrayList< Object >();
      // 初始化包含所有科目的集合
      final List< ItemVO > items = new ArrayList< ItemVO >();

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         final String[] selectedIdArray = selectedIds.split( "," );
         for ( String selectId : selectedIdArray )
         {
            // 初始化查询对象
            final CBHeaderVO cbHeaderVO = new CBHeaderVO();
            // 设置属性值
            cbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( cbBatchVO.getBatchId() ) );
            cbHeaderVO.setHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
            cbHeaderVO.setStatus( cbBatchVO.getStatus() );
            cbHeaderVO.setAccountId( cbBatchVO.getAccountId() );
            cbHeaderVO.setCorpId( cbBatchVO.getCorpId() );
            cbHeaderVO.setCbId( cbBatchVO.getCbId() );
            // 初始化CBHeaderVO List
            final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByCondition( cbHeaderVO );

            if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
            {
               for ( Object cbHeaderVOObject : cbHeaderVOs )
               {
                  // 初始化缓存对象
                  final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
                  // 计算合计
                  countAmount( tempCBHeaderVO );
                  // 初始化CBDTO
                  final CBDTO cbDTO = new CBDTO();

                  // 装载CBHeaderVO
                  cbDTO.setCbHeaderVO( tempCBHeaderVO );

                  // 装载CBDetailVO List
                  fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), cbHeaderVO.getStatus(), items );

                  Object cbDTOObject = cbDTO;
                  cbDTOs.add( cbDTOObject );
               }
            }

         }
      }
      else
      {
         // 初始化查询对象
         final CBHeaderVO cbHeaderVO = new CBHeaderVO();
         // 设置属性值
         cbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( cbBatchVO.getBatchId() ) );
         cbHeaderVO.setStatus( cbBatchVO.getStatus() );
         cbHeaderVO.setAccountId( cbBatchVO.getAccountId() );
         cbHeaderVO.setCorpId( cbBatchVO.getCorpId() );
         cbHeaderVO.setCbId( cbBatchVO.getCbId() );
         // 初始化CBHeaderVO List
         final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByCondition( cbHeaderVO );

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            for ( Object cbHeaderVOObject : cbHeaderVOs )
            {
               // 初始化缓存对象
               final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
               // 计算合计
               countAmount( tempCBHeaderVO );
               // 初始化CBDTO
               final CBDTO cbDTO = new CBDTO();

               // 装载CBHeaderVO
               cbDTO.setCbHeaderVO( tempCBHeaderVO );

               // 装载CBDetailVO List
               fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), cbHeaderVO.getStatus(), items );

               Object cbDTOObject = cbDTO;
               cbDTOs.add( cbDTOObject );
            }
         }

      }

      // CBDTO不存在科目添加空值
      if ( cbDTOs != null && cbDTOs.size() > 0 )
      {
         fillCBDTOs( cbDTOs, items );
      }

      return cbDTOs;
   }

   // 装载社保明细
   private void fetchCBDetail( final CBDTO cbDTO, final String headerId, final String status, final List< ItemVO > items ) throws KANException
   {
      // 初始化并装载社保明细
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            final CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;

            // 只提取符合条件的社保明细
            if ( cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( status ) )
            {
               cbDTO.getCbDetailVOs().add( ( CBDetailVO ) cbDetailVOObject );
            }

            // 初始化科目
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( cbDetailVO.getItemId() );
            itemVO.setItemType( cbDetailVO.getItemType() );

            // 如果科目不存在则添加
            if ( !isItemExist( itemVO, items ) )
            {
               items.add( itemVO );
            }

         }
      }
   }

   // CBDTO集合填充不存在科目
   private void fillCBDTOs( final List< Object > cbDTOs, final List< ItemVO > items )
   {
      for ( ItemVO itemVO : items )
      {
         for ( Object cbDTOObject : cbDTOs )
         {
            final List< CBDetailVO > copyList = new ArrayList< CBDetailVO >();
            final CBDTO cbDTO = ( CBDTO ) cbDTOObject;
            // 获得CBDTO对应CBHeaderVO、CBDetailVO集合
            final CBHeaderVO cbHeaderVO = cbDTO.getCbHeaderVO();
            List< CBDetailVO > cbDetailVOs = cbDTO.getCbDetailVOs();
            // COPY已存在CBDTO集合
            copyList.addAll( cbDetailVOs );

            // 查找当前CBDetailVO是否存在该科目，不存在则添加
            fetchItemExistCbDetailVOs( itemVO, cbHeaderVO, cbDetailVOs, copyList );

            // 重新设置CBDTO的CBDetailVO集合
            cbDTO.setCbDetailVOs( copyList );
         }
      }

   }

   /**  
    * IsItemExistCbDetailVOs
    * 判断ItemVO是否存在社保明细数组
    * @param itemVO
    * @param items
    * @return
    */
   private void fetchItemExistCbDetailVOs( final ItemVO itemVO, final CBHeaderVO cbHeaderVO, final List< CBDetailVO > cbDetailVOs, final List< CBDetailVO > returnDetailVOs )
   {
      Boolean existFlag = false;

      // 判断科目是否存在
      if ( cbDetailVOs == null || cbDetailVOs.size() == 0 )
      {
         existFlag = false;
      }
      else
      {
         for ( CBDetailVO cbDetailVO : cbDetailVOs )
         {
            if ( itemVO.getItemId().equals( cbDetailVO.getItemId() ) )
            {
               existFlag = true;
               break;
            }
         }
      }

      // 如果科目不存在则添加
      if ( !existFlag )
      {
         // 初始化CBDetailVO用于添加
         final CBDetailVO tempDetailVO = new CBDetailVO();
         tempDetailVO.setHeaderId( cbHeaderVO.getHeaderId() );
         tempDetailVO.setStatus( cbHeaderVO.getStatus() );
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

}
