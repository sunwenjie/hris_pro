package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.CommonBatchDao;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.domain.biz.payment.CommonBatchVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.service.inf.biz.payment.SalaryHeaderService;

public class SalaryHeaderServiceImpl extends ContextService implements SalaryHeaderService
{

   private CommonBatchDao commonBatchDao;

   private EmployeeDao employeeDao;

   private EmployeeContractDao employeeContractDao;

   private SalaryHeaderDao salaryHeaderDao;

   private SalaryDetailDao salaryDetailDao;

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public SalaryHeaderDao getSalaryHeaderDao()
   {
      return salaryHeaderDao;
   }

   public void setSalaryHeaderDao( SalaryHeaderDao salaryHeaderDao )
   {
      this.salaryHeaderDao = salaryHeaderDao;
   }

   public SalaryDetailDao getSalaryDetailDao()
   {
      return salaryDetailDao;
   }

   public void setSalaryDetailDao( SalaryDetailDao salaryDetailDao )
   {
      this.salaryDetailDao = salaryDetailDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   @Override
   public PagedListHolder getSalaryHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SalaryHeaderDao salaryHeaderDao = ( SalaryHeaderDao ) getDao();
      pagedListHolder.setHolderSize( salaryHeaderDao.countSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SalaryHeaderVO getSalaryHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertSalaryHeader( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).insertSalaryHeader( salaryHeaderVO );
   }

   @Override
   public int updateSalaryHeader( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).updateSalaryHeader( salaryHeaderVO );
   }

   @Override
   public int deleteSalaryHeader( String salaryHeaderId ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).deleteSalaryHeader( salaryHeaderId );
   }

   @Override
   public PagedListHolder getSalaryDTOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      // 初始化SalaryHeaderDTO集合
      List< Object > salaryDTOs = new ArrayList< Object >();
      // 初始化SalaryDetailVO集合
      List< Object > salaryHeaderVOs = new ArrayList< Object >();

      final SalaryHeaderDao salaryHeaderDao = ( SalaryHeaderDao ) getDao();
      pagedListHolder.setHolderSize( salaryHeaderDao.countSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() ) );

      // 获取查询对象
      final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) pagedListHolder.getObject();

      // 初始化salaryDetailVO
      SalaryDetailVO salaryDetailVO = new SalaryDetailVO();
      salaryDetailVO.setSalaryHeaderId( salaryHeaderVO.getSalaryHeaderId() );
      salaryDetailVO.setBatchId( salaryHeaderVO.getBatchId() );
      // 获得符合条件的SalaryDetailVO集合
      final List< Object > salaryDetailVOs = this.salaryDetailDao.getSalaryDetailVOsByCondition( salaryDetailVO );
      // 初始化所有所需Item 信息集合
      final List< MappingVO > items = getItems( salaryDetailVOs );

      if ( isPaged )
      {
         // 获得salaryHeaderVO集合
         salaryHeaderVOs = salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) );

      }
      else
      {
         // 获得salaryHeaderVO集合
         salaryHeaderVOs = salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() );
      }

      // 填充salaryHeaderVO集合
      salaryDTOs = fillSalaryDetilVOs( items, salaryHeaderVOs, salaryDetailVOs );
      pagedListHolder.setSource( salaryDTOs );
      // 添加科目长度
      pagedListHolder.setAdditionalObject( ( items == null ) ? 15 : ( items.size() * 2 + 30 ) );

      return pagedListHolder;
   }

   private List< Object > fillSalaryDetilVOs( List< MappingVO > items, List< Object > salaryHeaderVOs, List< Object > salaryDetailVOs )
   {
      // 初始化PaymentHeaderDTO集合
      final List< Object > salaryHeaderDTOs = new ArrayList< Object >();

      for ( Object salaryHeaderVOObject : salaryHeaderVOs )
      {
         final SalaryHeaderVO tempSalaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;
         // 初始化salaryDetailVO
         SalaryDetailVO salaryDetailVO = new SalaryDetailVO();
         salaryDetailVO.setSalaryHeaderId( tempSalaryHeaderVO.getSalaryHeaderId() );
         // 初始化salaryDetailVO集合
         final List< SalaryDetailVO > tempSalaryDetailVOs = new ArrayList< SalaryDetailVO >();

         // 遍历Item ID 集合
         for ( MappingVO mappingVO : items )
         {
            final String itemId = mappingVO.getMappingId();
            // 判断Item是否存在标记
            Boolean existFlag = true;

            // 遍历
            for ( Object tempSalaryDetailVOObject : salaryDetailVOs )
            {
               salaryDetailVO = ( SalaryDetailVO ) tempSalaryDetailVOObject;

               // 如果PaymentDetailVO的headerId
               // 与遍历的PaymentHeaderVO的headerId匹配且该Item值存在则添加
               if ( salaryDetailVO.getSalaryHeaderId().equals( tempSalaryHeaderVO.getSalaryHeaderId() ) && salaryDetailVO.getItemId().equals( itemId ) )
               {
                  tempSalaryDetailVOs.add( salaryDetailVO );
                  existFlag = false;
                  continue;
               }
            }

            // 如果PaymentHeaderVO不含有该Item的数据则存储空数据
            if ( existFlag )
            {
               salaryDetailVO = new SalaryDetailVO();
               salaryDetailVO.setSalaryHeaderId( tempSalaryHeaderVO.getSalaryHeaderId() );
               salaryDetailVO.setItemId( itemId );
               salaryDetailVO.setNameZH( mappingVO.getMappingValue() );
               salaryDetailVO.setNameEN( mappingVO.getOptionStyle() );
               salaryDetailVO.setItemNo( mappingVO.getMappingTemp() );
               tempSalaryDetailVOs.add( salaryDetailVO );
            }

         }

         // DTO注入
         final SalaryDTO tempSalaryDTO = new SalaryDTO();
         tempSalaryDTO.setSalaryHeaderVO( tempSalaryHeaderVO );
         tempSalaryDTO.setSalaryDetailVOs( tempSalaryDetailVOs );
         salaryHeaderDTOs.add( tempSalaryDTO );
      }
      return salaryHeaderDTOs;
   }

   private List< MappingVO > getItems( List< Object > salaryDetailVOs )
   {
      // 初始化Item信息集合
      final List< MappingVO > items = new ArrayList< MappingVO >();

      // Item集合插入数据
      for ( Object salaryDetailVOObject : salaryDetailVOs )
      {
         final SalaryDetailVO salaryDetailVO = ( SalaryDetailVO ) salaryDetailVOObject;

         // 如果不存在，则添加到Items里面
         if ( !isItemExistInItemsList( items, salaryDetailVO.getItemId() ) )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( salaryDetailVO.getItemId() );
            mappingVO.setMappingValue( salaryDetailVO.getNameZH() );
            mappingVO.setOptionStyle( salaryDetailVO.getNameEN() );
            mappingVO.setMappingTemp( salaryDetailVO.getItemNo() );
            items.add( mappingVO );
         }
      }

      return items;
   }

   private boolean isItemExistInItemsList( List< MappingVO > items, String itemId )
   {
      boolean flag = false;
      for ( MappingVO mappingVO : items )
      {
         if ( mappingVO.getMappingId().equals( itemId ) )
         {
            flag = true;
            break;
         }
      }
      return flag;
   }

   @Override
   public List< Object > getSalaryHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( batchId );
   }

   @Override
   public int submit( final CommonBatchVO commonBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 获得勾选ID数组
         final String selectedIds = commonBatchVO.getSelectedIds();
         // 获得 PageFlag
         final String pageFlag = commonBatchVO.getPageFlag();
         // 获得subAction
         final String subAction = commonBatchVO.getSubAction();

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
         // 如果是退回
         else if ( subAction.equalsIgnoreCase( BaseAction.ROLLBACK_OBJECT ) )
         {
            status = "0";
         }

         // 如果有选择项
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = selectedIds.split( "," );
            // 提交记录数
            submitRows = selectedIdArray.length;

            // 遍历
            for ( String encodedSelectId : selectedIdArray )
            {
               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );
               // 根据PageFlag 提交申请
               if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_BATCH ) )
               {
                  commonBatchVO.setBatchId( selectId );
                  submitBatch( selectId, status, commonBatchVO.getModifyBy() );
               }

               else if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) )
               {
                  submitHeader( selectId, status, commonBatchVO.getModifyBy() );
               }

            }

            // 尝试更改其父对象状态
            submitRows = trySubmitBatch( commonBatchVO.getBatchId(), status, commonBatchVO.getModifyBy() );

            // 从新计算CommonBatchVO 合计值
            sumCommonBatchData( commonBatchVO );
         }
         else if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_BATCH ) )
         {
            submitBatch( commonBatchVO.getBatchId(), status, commonBatchVO.getModifyBy() );
            submitRows++;
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

   private void sumCommonBatchData( final CommonBatchVO commonBatchVO ) throws KANException
   {

      final List< Object > salaryHeaderVOs = this.salaryHeaderDao.getSalaryHeaderVOsByBatchId( commonBatchVO.getBatchId() );

      if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
      {
         // 初始化主表总计
         double costAmountCompany = 0;
         //         double billAmountPersonal = 0;
         double costAmountPersonal = 0;

         for ( Object object : salaryHeaderVOs )
         {
            SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) object;

            // 状态为“新建”、“提交”累加
            if ( "1".equals( salaryHeaderVO.getStatus() ) || "2".equals( salaryHeaderVO.getStatus() ) )
            {
               costAmountCompany += Double.parseDouble( salaryHeaderVO.getCostAmountCompany() == null ? "0" : salaryHeaderVO.getCostAmountCompany() );
               //               billAmountPersonal += Double.parseDouble( salaryHeaderVO.getBillAmountPersonal() == null ? "0" : salaryHeaderVO.getBillAmountPersonal() );
               costAmountPersonal += Double.parseDouble( salaryHeaderVO.getCostAmountPersonal() == null ? "0" : salaryHeaderVO.getCostAmountPersonal() );
            }

         }

         CommonBatchVO tempCommonBatchVO = this.commonBatchDao.getCommonBatchVOByBatchId( commonBatchVO.getBatchId() );
         tempCommonBatchVO.setRemark2( "合计：" + tempCommonBatchVO.formatNumber( String.valueOf( costAmountCompany + costAmountPersonal ) ) );
         tempCommonBatchVO.setModifyDate( new Date() );
         this.commonBatchDao.updateCommonBatch( tempCommonBatchVO );
      }

   }

   // 尝试提交批次 - 提交子对象但不清楚父对象是否提交的情况使用
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // 初始化commonBatchVO
         final CommonBatchVO commonBatchVO = this.commonBatchDao.getCommonBatchVOByBatchId( batchId );
         // 初始化salaryHeaderVO列表
         final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( batchId );

         if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;
            String salaryHeaderstatus = ( ( SalaryHeaderVO ) salaryHeaderVOs.get( 0 ) ).getStatus();

            // 遍历
            for ( Object salaryHeaderVOObject : salaryHeaderVOs )
            {
               final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;

               if ( salaryHeaderVO.getStatus() != null && !salaryHeaderVO.getStatus().isEmpty()
                     && Integer.valueOf( salaryHeaderVO.getStatus() ).equals( Integer.valueOf( salaryHeaderstatus ) ) )
               {
                  headerFlag++;
               }

            }

            // 如果已全部是同一状态，修改批次的状态
            if ( headerFlag == salaryHeaderVOs.size() && !salaryHeaderstatus.equals( "1" ) )
            {
               commonBatchVO.setStatus( salaryHeaderstatus );
               commonBatchVO.setModifyBy( userId );
               commonBatchVO.setModifyDate( new Date() );
               this.commonBatchDao.updateCommonBatch( commonBatchVO );
               submitRows++;
            }

         }
         else
         {
            if ( commonBatchVO != null )
               this.getCommonBatchDao().deleteCommonBatch( commonBatchVO.getBatchId() );
            submitRows++;
         }

      }

      return submitRows;
   }

   // 更改批次
   private void submitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      // 初始化 SalaryHeaderDao数组
      final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( batchId );

      // 如果批次对应的SalaryHeader数组不为空
      if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
      {
         // 遍历
         for ( Object salaryHeaderVOObject : salaryHeaderVOs )
         {
            final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;
            submitHeader( salaryHeaderVO.getSalaryHeaderId(), status, userId );
         }
      }

      // 更改该批次
      final CommonBatchVO commonBatchVO = this.getCommonBatchDao().getCommonBatchVOByBatchId( batchId );

      if ( KANUtil.filterEmpty( commonBatchVO.getStatus(), "1" ) == null )
      {
         commonBatchVO.setStatus( status );
         commonBatchVO.setModifyBy( userId );
         commonBatchVO.setModifyDate( new Date() );
         this.getCommonBatchDao().updateCommonBatch( commonBatchVO );
      }
   }

   // 更改工资状态
   // Reviewed by Kevin Jin at 2014-05-20
   private void submitHeader( final String headerId, final String status, final String userId ) throws KANException
   {
      // 初始化 SalaryHeaderDao数组
      final SalaryHeaderVO salaryHeaderVO = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOByHeaderId( headerId );

      if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus(), "1" ) == null )
      {
         // 提交的情况
         if ( status.equals( "2" ) )
         {
            salaryHeaderVO.setStatus( status );
         }

         if ( status.equals( "0" ) && salaryHeaderVO.getStatus().equals( "1" ) )
         {
            salaryHeaderVO.setDeleted( "2" );
            salaryHeaderVO.setStatus( status );
         }

         salaryHeaderVO.setModifyBy( userId );
         salaryHeaderVO.setModifyDate( new Date() );

         ( ( SalaryHeaderDao ) getDao() ).updateSalaryHeader( salaryHeaderVO );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2014-06-05
   public List< SalaryDTO > getSalaryDTOsByCondition( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      // 初始化SalaryDTO列表
      final List< SalaryDTO > salaryDTOs = new ArrayList< SalaryDTO >();

      // 获取SalaryHeaderVO列表
      final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByCondition( salaryHeaderVO );

      if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
      {
         for ( Object salaryHeaderVOObject : salaryHeaderVOs )
         {
            // 初始化缓存对象
            final SalaryHeaderVO tempSalaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;
            // 初始化SalaryDTO
            final SalaryDTO salaryDTO = new SalaryDTO();

            // 装载SalaryHeaderVO
            salaryDTO.setSalaryHeaderVO( tempSalaryHeaderVO );

            // 初始化SalaryDetailVO - 作为搜索条件
            final SalaryDetailVO salaryDetailVO = new SalaryDetailVO();
            salaryDetailVO.setSalaryHeaderId( tempSalaryHeaderVO.getSalaryHeaderId() );
            salaryDetailVO.setItemType( salaryHeaderVO.getItemTypes() );

            // 装载SalaryDetailVO List
            fetchSalaryDetail( salaryDTO, salaryDetailVO );

            salaryDTOs.add( salaryDTO );
         }
      }

      return salaryDTOs;

   }

   // 装载工资明细
   // Reviewed by Kevin Jin at 2014-06-05
   private void fetchSalaryDetail( final SalaryDTO salaryDTO, final SalaryDetailVO salaryDetailVO ) throws KANException
   {
      // 初始化工资明细
      final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByCondition( salaryDetailVO );

      // 装载工资明细
      if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
      {
         for ( Object salaryDetailVOObject : salaryDetailVOs )
         {
            salaryDTO.getSalaryDetailVOs().add( ( SalaryDetailVO ) salaryDetailVOObject );
         }
      }
   }

   @Override
   public int rollback( final CommonBatchVO commonBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // 开启事务
         startTransaction();

         // 获得勾选ID数组
         final String selectedIds = commonBatchVO.getSelectedIds();
         // 获得 PageFlag
         final String pageFlag = commonBatchVO.getPageFlag();

         // 实例化字符串表示要修改的状态值status 退回
         String status = "0";

         // 如果有选择项
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = selectedIds.split( "," );
            // 提交记录数
            submitRows = selectedIdArray.length;
            // 遍历
            final List< String > ids = new ArrayList< String >();

            // 遍历
            for ( String encodedSelectId : selectedIdArray )
            {
               ids.add( KANUtil.decodeStringFromAjax( encodedSelectId ) );
            }

            // 根据PageFlag 提交申请
            if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_BATCH ) )
            {
               rollbackBatch( ids );
            }
            else if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) )
            {
               rollbackHeader( ids );
               // 尝试更改其父对象状态
               submitRows = trySubmitBatch( commonBatchVO.getBatchId(), status, commonBatchVO.getModifyBy() );
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
   private void rollbackBatch( List< String > batchIds ) throws KANException
   {
      if ( batchIds != null && batchIds.size() > 0 )
      {
         for ( String batchId : batchIds )
         {
            // 删除该批次
            final CommonBatchVO commonBatchVO = this.getCommonBatchDao().getCommonBatchVOByBatchId( batchId );

            if ( commonBatchVO != null )
            {
               // 初始化 SalaryHeaderDao数组
               final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( commonBatchVO.getBatchId() );

               // 如果批次对应的SalaryHeader数组不为空
               if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
               {
                  // 遍历
                  List< String > headerIds = new ArrayList< String >();

                  for ( Object salaryHeaderVOObject : salaryHeaderVOs )
                  {
                     headerIds.add( ( ( SalaryHeaderVO ) salaryHeaderVOObject ).getSalaryHeaderId() );

                  }

                  rollbackHeader( headerIds );
               }

               this.getCommonBatchDao().deleteCommonBatch( commonBatchVO.getBatchId() );
            }
         }
      }
   }

   // 退回
   private void rollbackHeader( List< String > headerIds ) throws KANException
   {
      this.getSalaryDetailDao().deleteSalaryDetailByHeaderIds( headerIds );
      ( ( SalaryHeaderDao ) getDao() ).deleteSalaryHeaderByHeaderIds( headerIds );
   }
}
