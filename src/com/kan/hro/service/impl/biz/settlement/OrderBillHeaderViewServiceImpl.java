package com.kan.hro.service.impl.biz.settlement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kan.base.core.ContextService;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.settlement.OrderBillHeaderViewDao;
import com.kan.hro.domain.biz.settlement.OrderBillDetailView;
import com.kan.hro.domain.biz.settlement.OrderBillHeaderView;
import com.kan.hro.domain.biz.settlement.OrderDTO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.settlement.SettlementDTO;
import com.kan.hro.service.inf.biz.settlement.OrderBillHeaderViewService;

public class OrderBillHeaderViewServiceImpl extends ContextService implements OrderBillHeaderViewService
{

   @Override
   public PagedListHolder getOrderBillHeaderViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      OrderBillHeaderViewDao orderBillHeaderViewDao = ( OrderBillHeaderViewDao ) getDao();
      final List< Object > orderBillHeaderViewObjects = orderBillHeaderViewDao.getOrderBillHeaderViewsByCondition( ( OrderBillHeaderView ) pagedListHolder.getObject() );
      // 按照客户，月份，状态分组
      // 获取所有的客户
      final List< String > clientList = getClientGroup( orderBillHeaderViewObjects );
      // 获取所有的月份 
      final List< String > monthlyList = getMonthlyGroup( orderBillHeaderViewObjects );
      // 获取所有的状态
      final List< String > statusList = getStatusGroup( orderBillHeaderViewObjects );
      //  获取所有的订单
      final List< String > orderIdList = getOrderIdGroup( orderBillHeaderViewObjects );
      final List< Object > orderDTOs = new ArrayList< Object >();
      for ( String clientId : clientList )
      {
         for ( String monthly : monthlyList )
         {
            for ( String orderId : orderIdList )
            {
               for ( String status : statusList )
               {
                  final OrderDTO orderDTO = new OrderDTO();
                  final OrderHeaderVO orderHeaderVO = new OrderHeaderVO();
                  final List< ServiceContractVO > serviceContractVOs = new ArrayList< ServiceContractVO >();
                  String tempClientNameZH = "";
                  String tempClientNameEN = "";
                  String tempEntityId = "";
                  String tempBusinessTypeId = "";
                  for ( Object object : orderBillHeaderViewObjects )
                  {
                     final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) object;
                     if ( clientId.equals( orderBillHeaderView.getClientId() ) && monthly.equals( orderBillHeaderView.getMonthly() )
                           && status.equals( orderBillHeaderView.getStatus() ) && orderId.equals( orderBillHeaderView.getOrderId() ) )
                     {
                        final ServiceContractVO serviceContractVO = new ServiceContractVO();
                        //
                        serviceContractVO.setBillAmountCompany( orderBillHeaderView.getBillAmountCompany() );
                        serviceContractVO.setCostAmountCompany( orderBillHeaderView.getCostAmountCompany() );
                        if ( KANUtil.filterEmpty( tempClientNameZH ) == null )
                        {
                           tempClientNameZH = orderBillHeaderView.getClientNameZH();
                        }
                        if ( KANUtil.filterEmpty( tempClientNameEN ) == null )
                        {
                           tempClientNameEN = orderBillHeaderView.getClientNameEN();
                        }
                        if ( KANUtil.filterEmpty( tempEntityId ) == null )
                        {
                           tempEntityId = orderBillHeaderView.getEntityId();
                        }
                        if ( KANUtil.filterEmpty( tempBusinessTypeId ) == null )
                        {
                           tempBusinessTypeId = orderBillHeaderView.getBusinessTypeId();
                        }
                        serviceContractVOs.add( serviceContractVO );
                     }
                  }
                  orderHeaderVO.setClientId( clientId );
                  orderHeaderVO.setMonthly( monthly );
                  orderHeaderVO.setStatus( status );
                  orderHeaderVO.setOrderId( orderId );
                  orderHeaderVO.setClientNameZH( tempClientNameZH );
                  orderHeaderVO.setClientNameEN( tempClientNameEN );
                  orderHeaderVO.setEntityId( tempEntityId );
                  orderHeaderVO.setBusinessTypeId( tempBusinessTypeId );
                  orderDTO.setOrderHeaderVO( orderHeaderVO );
                  // 去除空的影响
                  if ( serviceContractVOs != null && serviceContractVOs.size() > 0 )
                  {
                     orderDTO.setServiceContractVOs( serviceContractVOs );
                     orderDTO.calAmountCompany();
                     orderDTOs.add( orderDTO );
                  }
               }
            }
         }
      }
      pagedListHolder.setHolderSize( orderDTOs.size() );
      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );
      // 手动分页
      if ( isPaged )
      {
         // 装载数据
         if ( orderDTOs != null && orderDTOs.size() > 0 )
         {

            for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                  && i < orderDTOs.size(); i++ )
            {
               pagedListHolder.getSource().add( orderDTOs.get( i ) );
            }

         }
      }
      else
      {
         pagedListHolder.setSource( orderDTOs );
      }
      // 第一层查询服务费
      for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
      {
         final OrderDTO orderDTO = ( OrderDTO ) pagedListHolder.getSource().get( i );
         final String clientId = orderDTO.getOrderHeaderVO().getClientId();
         final String orderId = orderDTO.getOrderHeaderVO().getOrderId();
         final String monthly = orderDTO.getOrderHeaderVO().getMonthly();
         final String status = orderDTO.getOrderHeaderVO().getStatus();
         final String accountId = ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getAccountId();
         final String itemId = "";
         final OrderBillHeaderView tempView = new OrderBillHeaderView( clientId, orderId, monthly, status, itemId );
         tempView.setAccountId( accountId );
         List< Object > resultOrderBillDetailViews = orderBillHeaderViewDao.getOrderBillDetailViewsByCondition( tempView );
         // 统计服务费
         Double item_service_billAmountCompany = 0d;
         for ( Object o : resultOrderBillDetailViews )
         {
            // if itemId =122 .continue;
            if ( isItemType( ( ( OrderBillDetailView ) o ).getItemId(), "9", accountId ) )
            {
               item_service_billAmountCompany += Double.parseDouble( ( ( OrderBillDetailView ) o ).getBillAmountCompany() );
            }
         }
         final String item_service_billAmountCompanyStr = String.valueOf( item_service_billAmountCompany );
         ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setItemServiceFree( item_service_billAmountCompanyStr );
         // 合同统计，人数统计
         final Map< String, Integer > employeeContractMap = getDistinctEmployeeAndContractCount( resultOrderBillDetailViews );
         ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setEmployeeCount( String.valueOf( employeeContractMap.get( "distinctEmployeeCount" ) ) );
         ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setContractCount( String.valueOf( employeeContractMap.get( "distinctContractCount" ) ) );

      }
      formatOrderDTOPagedListHolder( pagedListHolder );
      return pagedListHolder;
   }

   //获取所有不重复的客户
   private List< String > getClientGroup( List< Object > orderCostDetailViewObjects )
   {
      final List< String > allClientList = new ArrayList< String >();
      for ( Object obj : orderCostDetailViewObjects )
      {
         final OrderBillHeaderView orderCostDetailView = ( OrderBillHeaderView ) obj;
         if ( KANUtil.filterEmpty( orderCostDetailView.getClientId() ) != null )
         {
            allClientList.add( orderCostDetailView.getClientId() );
         }
      }
      return getDistinctList( allClientList );
   }

   //获取所有不重复的月份
   private List< String > getMonthlyGroup( List< Object > orderCostDetailViewObjects )
   {
      final List< String > allMonthlyList = new ArrayList< String >();
      for ( Object obj : orderCostDetailViewObjects )
      {
         final OrderBillHeaderView orderCostDetailView = ( OrderBillHeaderView ) obj;
         if ( KANUtil.filterEmpty( orderCostDetailView.getMonthly() ) != null )
         {
            allMonthlyList.add( orderCostDetailView.getMonthly() );
         }
      }
      return getDistinctList( allMonthlyList );
   }

   // 获取所有不重复的状态
   private List< String > getStatusGroup( List< Object > orderCostDetailViewObjects )
   {
      final List< String > allStatusList = new ArrayList< String >();
      for ( Object obj : orderCostDetailViewObjects )
      {
         final OrderBillHeaderView orderCostDetailView = ( OrderBillHeaderView ) obj;
         if ( KANUtil.filterEmpty( orderCostDetailView.getStatus() ) != null )
         {
            allStatusList.add( orderCostDetailView.getStatus() );
         }
      }
      return getDistinctList( allStatusList );
   }

   // 获取所有不重复的订单
   private List< String > getOrderIdGroup( List< Object > orderCostDetailViewObjects )
   {
      final List< String > allOrderIdList = new ArrayList< String >();
      for ( Object obj : orderCostDetailViewObjects )
      {
         final OrderBillHeaderView orderCostDetailView = ( OrderBillHeaderView ) obj;
         if ( KANUtil.filterEmpty( orderCostDetailView.getOrderId() ) != null )
         {
            allOrderIdList.add( orderCostDetailView.getOrderId() );
         }
      }
      return getDistinctList( allOrderIdList );
   }

   @Override
   public PagedListHolder getOrderBillDetailViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      return getOrderBillDetailViewsByCondition( pagedListHolder, isPaged, null );
   }

   public PagedListHolder getOrderBillDetailViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged, List< Object > orderBillDetailViewObjects ) throws KANException
   {
      OrderBillHeaderViewDao orderBillHeaderViewDao = ( OrderBillHeaderViewDao ) getDao();
      if ( orderBillDetailViewObjects == null )
      {
         orderBillDetailViewObjects = orderBillHeaderViewDao.getOrderBillDetailViewsByCondition( ( OrderBillHeaderView ) pagedListHolder.getObject() );
      }
      // 按合同分组
      final List< String > contractIds = getAllContractIds( orderBillDetailViewObjects );
      final List< Object > settlementDTOs = new ArrayList< Object >();
      for ( String contractId : contractIds )
      {
         final SettlementDTO settlementDTO = new SettlementDTO();
         List< OrderDetailVO > orderDetailVOs = new ArrayList< OrderDetailVO >();
         // 合同和名称是一一对应的
         String tempEmployeeNameZH = "";
         String tempEmployeeNameEN = "";
         String tempClientNameZH = "";
         String tempClientNameEN = "";
         String tempEntityId = "";
         String tempBusinessTypeId = "";
         for ( int i = 0; i < orderBillDetailViewObjects.size(); i++ )
         {
            final OrderBillDetailView orderBillDetailView = ( OrderBillDetailView ) orderBillDetailViewObjects.get( i );
            if ( orderBillDetailView.getContractId().equals( contractId ) )
            {
               final OrderDetailVO orderDetailVO = new OrderDetailVO();
               orderDetailVO.setItemId( orderBillDetailView.getItemId() );
               // amountPersonal only?
               // for page
               orderDetailVO.setAmountPersonal( orderBillDetailView.getBillAmountCompany() );
               // for excel
               orderDetailVO.setBillAmountCompany( orderBillDetailView.getBillAmountCompany() );
               orderDetailVO.setCostAmountPersonal( orderBillDetailView.getCostAmountPersonal() );
               orderDetailVO.setSbBillAmountCompany( orderBillDetailView.getSbBillAmountCompany() );
               orderDetailVO.setSbCostAmountPersonal( orderBillDetailView.getSbCostAmountPersonal() );
               orderDetailVO.setBase( orderBillDetailView.getBase() );
               orderDetailVO.setContractId( orderBillDetailView.getContractId() );
               orderDetailVO.setSbBaseCompany( orderBillDetailView.getBaseCompany() );
               orderDetailVO.setSbBasePersonal( orderBillDetailView.getBasePersonal() );
               orderDetailVO.setBillRateCompany( orderBillDetailView.getRateCompany() );
               orderDetailVO.setBillRatePersonal( orderBillDetailView.getRatePersonal() );
               orderDetailVO.setSbHeaderId( orderBillDetailView.getSbheaderId() );
               orderDetailVO.setPersonalSBBurden( orderBillDetailView.getPersonalSBBurden() );
               orderDetailVO.setIsAdjustment( orderBillDetailView.getIsAdjustment() );
               orderDetailVOs.add( orderDetailVO );
               // 中文名
               if ( KANUtil.filterEmpty( tempEmployeeNameZH ) == null )
               {
                  tempEmployeeNameZH = orderBillDetailView.getEmployeeNameZH();
               }
               if ( KANUtil.filterEmpty( tempEmployeeNameEN ) == null )
               {
                  tempEmployeeNameEN = orderBillDetailView.getEmployeeNameEN();
               }
               // 客户名称
               if ( KANUtil.filterEmpty( tempClientNameZH ) == null )
               {
                  tempClientNameZH = orderBillDetailView.getClientNameZH();
               }
               if ( KANUtil.filterEmpty( tempClientNameEN ) == null )
               {
                  tempClientNameEN = orderBillDetailView.getClientNameEN();
               }
               // 法务实体 业务类型
               if ( KANUtil.filterEmpty( tempEntityId ) == null )
               {
                  tempEntityId = orderBillDetailView.getEntityId();
               }
               if ( KANUtil.filterEmpty( tempBusinessTypeId ) == null )
               {
                  tempBusinessTypeId = orderBillDetailView.getBusinessTypeId();
               }
            }

         }
         if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
         {
            // 合计重复的科目
            orderDetailVOs = mergeOrderDetailVOs( orderDetailVOs );
            // 添加收费总计
            OrderDetailVO billAmount = new OrderDetailVO();
            billAmount.setItemId( "999" );
            // amountPersonal only?
            billAmount.setAmountPersonal( getBillAmountByOrderDetailVOs( orderDetailVOs ) );
            // 添加特殊科目，收费总计
            orderDetailVOs.add( billAmount );
            settlementDTO.setOrderDetailVOs( orderDetailVOs );
            ServiceContractVO serviceContractVO = new ServiceContractVO();
            serviceContractVO.setContractId( contractId );
            serviceContractVO.setEmployeeNameZH( tempEmployeeNameZH );
            serviceContractVO.setEmployeeNameEN( tempEmployeeNameEN );
            serviceContractVO.setClientNameZH( tempClientNameZH );
            serviceContractVO.setClientNameEN( tempClientNameEN );
            serviceContractVO.setEntityId( tempEntityId );
            serviceContractVO.setBusinessTypeId( tempBusinessTypeId );
            // clientID,orderId,monthly
            serviceContractVO.setClientId( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getClientId() );
            serviceContractVO.setOrderId( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getOrderId() );
            serviceContractVO.setMonthly( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getMonthly() );
            settlementDTO.setServiceContractVO( serviceContractVO );
            settlementDTOs.add( settlementDTO );
         }

      }

      pagedListHolder.setHolderSize( settlementDTOs.size() );
      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );
      pagedListHolder.setAdditionalObject( getAllContractMappingItems( orderBillDetailViewObjects, ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getAccountId() ) );
      // 手动分页
      if ( isPaged )
      {
         // 装载数据
         if ( settlementDTOs != null && settlementDTOs.size() > 0 )
         {

            for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                  && i < settlementDTOs.size(); i++ )
            {
               pagedListHolder.getSource().add( settlementDTOs.get( i ) );
            }

         }
      }
      else
      {
         pagedListHolder.setSource( settlementDTOs );
      }
      formatSettlementDTOPagedListHolder( pagedListHolder );
      return pagedListHolder;
   }

   private List< ItemVO > getAllContractMappingItems( final List< Object > orderBillDetailViewObjects, final String accountId )
   {
      List< String > contractItems = new ArrayList< String >();
      List< ItemVO > distinctContractMappingItems = new ArrayList< ItemVO >();
      for ( Object obj : orderBillDetailViewObjects )
      {
         final OrderBillDetailView orderBillDetailView = ( OrderBillDetailView ) obj;
         if ( KANUtil.filterEmpty( orderBillDetailView.getItemId() ) != null )
         {
            contractItems.add( orderBillDetailView.getItemId() );
         }
      }
      final List< String > distinctContractItems = KANUtil.getDistinctList( contractItems );

      List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( accountId ).ITEM_VO;
      for ( String contractItem : distinctContractItems )
      {
         for ( ItemVO itemVO : itemVOs )
         {
            if ( contractItem.equals( itemVO.getItemId() ) )
            {
               ItemVO temp = new ItemVO();
               temp.setItemId( contractItem );
               temp.setItemType( "7.5" );
               distinctContractMappingItems.add( temp );
               break;
            }
         }
      }
      // 添加特殊科目，收费总计
      ItemVO tempItemVO = new ItemVO();
      tempItemVO.setItemId( "999" );
      tempItemVO.setItemType( "7.5" );
      distinctContractMappingItems.add( tempItemVO );
      return distinctContractMappingItems;
   }

   private List< String > getAllContractIds( List< Object > orderBillDetailViewObjects )
   {
      List< String > contractIds = new ArrayList< String >();
      for ( Object obj : orderBillDetailViewObjects )
      {
         final OrderBillDetailView orderBillDetailView = ( OrderBillDetailView ) obj;
         contractIds.add( orderBillDetailView.getContractId() );
      }

      return KANUtil.getDistinctList( contractIds );
   }

   // 统计第一层人数
   private Map< String, Integer > getDistinctEmployeeAndContractCount( List< Object > resultOrderBillDetailViews )
   {
      final List< String > employeeIds = new ArrayList< String >();
      final List< String > contractIds = new ArrayList< String >();
      final Map< String, Integer > map = new HashMap< String, Integer >();
      for ( Object obj : resultOrderBillDetailViews )
      {
         employeeIds.add( ( ( OrderBillDetailView ) obj ).getEmployeeId() );
         contractIds.add( ( ( OrderBillDetailView ) obj ).getContractId() );
      }
      map.put( "distinctEmployeeCount", KANUtil.getDistinctList( employeeIds ).size() );
      map.put( "distinctContractCount", KANUtil.getDistinctList( contractIds ).size() );
      return map;
   }

   private String getBillAmountByOrderDetailVOs( List< OrderDetailVO > orderDetailVOs )
   {
      Double sum = 0d;
      for ( OrderDetailVO vo : orderDetailVOs )
      {
         // amountpersonal only?
         sum += Double.parseDouble( vo.getAmountPersonal() == null ? "0" : vo.getAmountPersonal() );
      }
      return String.valueOf( sum );
   }

   // 合并重复科目
   private List< OrderDetailVO > mergeOrderDetailVOs( List< OrderDetailVO > orderDetailVOs )
   {
      final List< OrderDetailVO > mergeOrderDetailVos = new ArrayList< OrderDetailVO >();
      for ( int i = 0; i < orderDetailVOs.size(); i++ )
      {
         boolean flag = false;
         for ( int j = 0; j < mergeOrderDetailVos.size(); j++ )
         {
            // 如果存在，合并
            if ( orderDetailVOs.get( i ).getItemId().equals( mergeOrderDetailVos.get( j ).getItemId() ) )
            //&& "1".equals( orderDetailVOs.get( i ).getIsAdjustment() )
            //&& "1".equals( mergeOrderDetailVos.get( j ).getIsAdjustment() ) && orderDetailVOs.get( i ).getMonthly().equals( mergeOrderDetailVos.get( j ).getMonthly() ) )
            {
               flag = true;
               // 原本值
               final String baseValue = mergeOrderDetailVos.get( j ).getBillAmountCompany();
               // 要添加的值
               final String targetValue = orderDetailVOs.get( i ).getBillAmountCompany();
               final String sumValue = String.valueOf( Double.parseDouble( baseValue ) + Double.parseDouble( targetValue ) );
               // for page 因为页面是生成的。在render里面写死为显示amountPersonal值，所以这里吧billAmountCompany的值放到amountPersonal里面。
               mergeOrderDetailVos.get( j ).setAmountPersonal( sumValue );
               // for excel 导出是定制的，只为单一导出，所以这里也在billAmountCompany保存一份。
               mergeOrderDetailVos.get( j ).setBillAmountCompany( sumValue );
               // billAmountCompany 如果是公司承担个人社保的，已经在billamountCompany加过。billamountpersonal减过，所以这里同济
               // sbbillamountCompany 如果是公司承担个人社保的。个人部分还是写在个人，但是实际上是公司承担。
               // costamountpersonal
               final String baseValue_costAmountPersonal = KANUtil.filterEmpty( mergeOrderDetailVos.get( j ).getCostAmountPersonal() ) == null ? "0"
                     : mergeOrderDetailVos.get( j ).getCostAmountPersonal();
               final String targetValue_costAmountPersonal = KANUtil.filterEmpty( orderDetailVOs.get( i ).getCostAmountPersonal() ) == null ? "0"
                     : orderDetailVOs.get( i ).getCostAmountPersonal();
               mergeOrderDetailVos.get( j ).setCostAmountPersonal( String.valueOf( Double.parseDouble( baseValue_costAmountPersonal )
                     + Double.parseDouble( targetValue_costAmountPersonal ) ) );
               // base
               final String baseValue_base = KANUtil.filterEmpty( mergeOrderDetailVos.get( j ).getBase() ) == null ? "0" : mergeOrderDetailVos.get( j ).getBase();
               final String targetValue_base = KANUtil.filterEmpty( orderDetailVOs.get( i ).getBase() ) == null ? "0" : orderDetailVOs.get( i ).getBase();
               mergeOrderDetailVos.get( j ).setBase( String.valueOf( Double.parseDouble( baseValue_base ) + Double.parseDouble( targetValue_base ) ) );
               // baseCompany
               final String baseValue_baseCompany = KANUtil.filterEmpty( mergeOrderDetailVos.get( j ).getSbBaseCompany() ) == null ? "0"
                     : mergeOrderDetailVos.get( j ).getSbBaseCompany();
               final String targetValue_baseCompany = KANUtil.filterEmpty( orderDetailVOs.get( i ).getSbBaseCompany() ) == null ? "0" : orderDetailVOs.get( i ).getSbBaseCompany();
               mergeOrderDetailVos.get( j ).setSbBaseCompany( String.valueOf( Double.parseDouble( baseValue_baseCompany ) + Double.parseDouble( targetValue_baseCompany ) ) );
               // sbbillAmountCompany
               final String baseValue_sbBillAmountCompay = KANUtil.filterEmpty( mergeOrderDetailVos.get( j ).getSbBillAmountCompany() ) == null ? "0"
                     : mergeOrderDetailVos.get( j ).getSbBillAmountCompany();
               final String targetValue_sbBillAmountCompay = KANUtil.filterEmpty( orderDetailVOs.get( i ).getSbBillAmountCompany() ) == null ? "0"
                     : orderDetailVOs.get( i ).getSbBillAmountCompany();
               mergeOrderDetailVos.get( j ).setSbBillAmountCompany( String.valueOf( Double.parseDouble( baseValue_sbBillAmountCompay )
                     + Double.parseDouble( targetValue_sbBillAmountCompay ) ) );
               // sbcostAmountPersonal
               final String baseValue_sbCostAmountPersonal = KANUtil.filterEmpty( mergeOrderDetailVos.get( j ).getSbCostAmountPersonal() ) == null ? "0"
                     : mergeOrderDetailVos.get( j ).getSbCostAmountPersonal();
               String targetValue_sbCostAmountPersonal = KANUtil.filterEmpty( orderDetailVOs.get( i ).getSbCostAmountPersonal() ) == null ? "0"
                     : orderDetailVOs.get( i ).getSbCostAmountPersonal();
               // 如果是公司承担个人。
               if ( "1".equals( orderDetailVOs.get( i ).getPersonalSBBurden() ) )
               {
                  // 公司承担的部分保存在personalSBBurdenValues，统计社保的时候，加上。
                  final String baseValue_personalSBBurdenValues = KANUtil.filterEmpty( mergeOrderDetailVos.get( j ).getPersonalSBBurdenValues() ) == null ? "0"
                        : mergeOrderDetailVos.get( j ).getPersonalSBBurdenValues();
                  final String personalSBBurdenValues = String.valueOf( Double.parseDouble( baseValue_personalSBBurdenValues )
                        + Double.parseDouble( targetValue_sbCostAmountPersonal ) );
                  mergeOrderDetailVos.get( j ).setPersonalSBBurdenValues( personalSBBurdenValues );
                  // 公司代缴和人，合并到个人。
                  //targetValue_sbCostAmountPersonal = "0";
               }
               mergeOrderDetailVos.get( j ).setSbCostAmountPersonal( String.valueOf( Double.parseDouble( baseValue_sbCostAmountPersonal )
                     + Double.parseDouble( targetValue_sbCostAmountPersonal ) ) );
               // 记录合并的值。用户导出统计运算
               break;
            }

         }
         // 不存在,不合并
         if ( !flag )
         {
            mergeOrderDetailVos.add( orderDetailVOs.get( i ) );
         }
      }
      return mergeOrderDetailVos;
   }

   private void formatOrderDTOPagedListHolder( final PagedListHolder pagedListHolder )
   {
      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         final DecimalFormat df = new DecimalFormat( "##0.00" );
         for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
         {
            final List< ServiceContractVO > serviceContractVOs = ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getServiceContractVOs();
            for ( int j = 0; j < serviceContractVOs.size(); j++ )
            {
               final String billAmountCompany = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getServiceContractVOs().get( j ).getBillAmountCompany() ) ) );
               final String costAmountCompany = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getServiceContractVOs().get( j ).getCostAmountCompany() ) ) );
               ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getServiceContractVOs().get( j ).setBillAmountCompany( billAmountCompany );
               ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getServiceContractVOs().get( j ).setCostAmountCompany( costAmountCompany );
            }
            final String itemServiceFree = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().getItemServiceFree() ) ) );
            ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setItemServiceFree( itemServiceFree );
         }
      }
   }

   private void formatSettlementDTOPagedListHolder( final PagedListHolder pagedListHolder )
   {
      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         final DecimalFormat df = new DecimalFormat( "##0.00" );
         for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
         {
            final List< OrderDetailVO > orderDetailVOs = ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs();
            for ( int j = 0; j < orderDetailVOs.size(); j++ )
            {
               final String billAmountCompany = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getBillAmountCompany() ) ) );
               final String costAmountPersonal = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getCostAmountPersonal() ) ) );
               final String base = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getBase() ) ) );
               final String baseCompany = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getSbBaseCompany() ) ) );
               final String basePersonal = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getSbBasePersonal() ) ) );
               final String rateCompany = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getBillRateCompany() ) ) );
               final String ratePersonal = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getBillRatePersonal() ) ) );
               final String amountPersonal = df.format( Double.parseDouble( KANUtil.formatNumberNull2Zero( ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).getAmountPersonal() ) ) );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setBillAmountCompany( billAmountCompany );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setCostAmountPersonal( costAmountPersonal );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setBase( base );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setSbBaseCompany( baseCompany );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setSbBasePersonal( basePersonal );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setBillRateCompany( rateCompany );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setBillRatePersonal( ratePersonal );
               ( ( SettlementDTO ) pagedListHolder.getSource().get( i ) ).getOrderDetailVOs().get( j ).setAmountPersonal( amountPersonal );
            }
         }
      }
   }

   @Override
   public PagedListHolder getOrderBillDetailViewsByConditionForExport( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {

      OrderBillHeaderViewDao orderBillHeaderViewDao = ( OrderBillHeaderViewDao ) getDao();

      List< Object > orderBillDetailViewObjects = orderBillHeaderViewDao.getOrderBillDetailViewsByConditionForExport( ( OrderBillHeaderView ) pagedListHolder.getObject() );

      return getOrderBillDetailViewsByConditionForExport( pagedListHolder, isPaged, orderBillDetailViewObjects );
   }

   /**
    * 导出专用，同月份的调整合并，其他不合并
    * @param pagedListHolder
    * @param isPaged
    * @param orderBillDetailViewObjects
    * @return
    * @throws KANException
    */
   public PagedListHolder getOrderBillDetailViewsByConditionForExport( PagedListHolder pagedListHolder, boolean isPaged, List< Object > orderBillDetailViewObjects )
         throws KANException
   {
      final OrderBillHeaderViewDao orderBillHeaderViewDao = ( OrderBillHeaderViewDao ) getDao();
      final String accountId = ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getAccountId();
      if ( orderBillDetailViewObjects == null )
      {
         orderBillDetailViewObjects = orderBillHeaderViewDao.getOrderBillDetailViewsByCondition( ( OrderBillHeaderView ) pagedListHolder.getObject() );
      }
      // 按合同分组
      final List< String > contractIds = getAllContractIds( orderBillDetailViewObjects );
      final List< Object > settlementDTOs = new ArrayList< Object >();
      for ( String contractId : contractIds )
      {
         List< OrderDetailVO > orderDetailVOs = new ArrayList< OrderDetailVO >();
         // 合同和名称是一一对应的
         String tempEmployeeNameZH = "";
         String tempEmployeeNameEN = "";
         String tempClientNameZH = "";
         String tempClientNameEN = "";
         String tempEntityId = "";
         String tempBusinessTypeId = "";
         for ( int i = 0; i < orderBillDetailViewObjects.size(); i++ )
         {
            final OrderBillDetailView orderBillDetailView = ( OrderBillDetailView ) orderBillDetailViewObjects.get( i );
            if ( orderBillDetailView.getContractId().equals( contractId ) )
            {
               final OrderDetailVO orderDetailVO = new OrderDetailVO();
               orderDetailVO.setItemId( orderBillDetailView.getItemId() );
               // amountPersonal only?
               // for page
               orderDetailVO.setAmountPersonal( orderBillDetailView.getBillAmountCompany() );
               // for excel
               orderDetailVO.setBillAmountCompany( orderBillDetailView.getBillAmountCompany() );
               orderDetailVO.setCostAmountPersonal( orderBillDetailView.getCostAmountPersonal() );
               orderDetailVO.setSbBillAmountCompany( orderBillDetailView.getSbBillAmountCompany() );
               orderDetailVO.setSbCostAmountPersonal( orderBillDetailView.getSbCostAmountPersonal() );
               orderDetailVO.setBase( orderBillDetailView.getBase() );
               orderDetailVO.setContractId( orderBillDetailView.getContractId() );
               orderDetailVO.setSbBaseCompany( orderBillDetailView.getBaseCompany() );
               orderDetailVO.setSbBasePersonal( orderBillDetailView.getBasePersonal() );
               orderDetailVO.setBillRateCompany( orderBillDetailView.getRateCompany() );
               orderDetailVO.setBillRatePersonal( orderBillDetailView.getRatePersonal() );
               orderDetailVO.setSbHeaderId( orderBillDetailView.getSbheaderId() );
               orderDetailVO.setPersonalSBBurden( orderBillDetailView.getPersonalSBBurden() );
               orderDetailVO.setIsAdjustment( orderBillDetailView.getIsAdjustment() );
               orderDetailVO.setSbMonthly( orderBillDetailView.getSbMonthly() );
               orderDetailVO.setMonthly( orderBillDetailView.getMonthly() );
               orderDetailVOs.add( orderDetailVO );
               // 中文名
               if ( KANUtil.filterEmpty( tempEmployeeNameZH ) == null )
               {
                  tempEmployeeNameZH = orderBillDetailView.getEmployeeNameZH();
               }
               if ( KANUtil.filterEmpty( tempEmployeeNameEN ) == null )
               {
                  tempEmployeeNameEN = orderBillDetailView.getEmployeeNameEN();
               }
               // 客户名称
               if ( KANUtil.filterEmpty( tempClientNameZH ) == null )
               {
                  tempClientNameZH = orderBillDetailView.getClientNameZH();
               }
               if ( KANUtil.filterEmpty( tempClientNameEN ) == null )
               {
                  tempClientNameEN = orderBillDetailView.getClientNameEN();
               }
               // 法务实体 业务类型
               if ( KANUtil.filterEmpty( tempEntityId ) == null )
               {
                  tempEntityId = orderBillDetailView.getEntityId();
               }
               if ( KANUtil.filterEmpty( tempBusinessTypeId ) == null )
               {
                  tempBusinessTypeId = orderBillDetailView.getBusinessTypeId();
               }
            }

         }
         if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
         {
            // 按照是否调整分类，调整同月合并，其他不合并
            // 调整数据
            final List< OrderDetailVO > adjustOrderDetailVOs = new ArrayList< OrderDetailVO >();
            final List< OrderDetailVO > notAdjustOrderDetailVOs = new ArrayList< OrderDetailVO >();
            for ( OrderDetailVO tempVO : orderDetailVOs )
            {
               // 如果是社保。但是又没有社保月份。则这个社保也是个调整.
               if ( "1".equals( tempVO.getIsAdjustment() ) || ( isItemType( tempVO.getItemId(), "7", accountId ) && KANUtil.filterEmpty( tempVO.getSbMonthly() ) == null ) )
               {
                  adjustOrderDetailVOs.add( tempVO );
               }
               else
               {
                  notAdjustOrderDetailVOs.add( tempVO );
               }
            }
            // 非调整数据
            if ( notAdjustOrderDetailVOs != null && notAdjustOrderDetailVOs.size() > 0 )
            {
               // 获取非调整的所有月份，
               final List< String > notAdjustOrderDetailMonthlies = getMonthlyByOrderDetailVO( notAdjustOrderDetailVOs, false, accountId );
               for ( String sbMonthly : notAdjustOrderDetailMonthlies )
               {
                  List< OrderDetailVO > tempNotAdjustOrderDetailVOs = new ArrayList< OrderDetailVO >();
                  for ( OrderDetailVO tempNotAdjustOrderDetailVO : notAdjustOrderDetailVOs )
                  {
                     // 社保月份为当前月，或者是社保月份为空，申报月份为当前月
                     if ( sbMonthly.equals( tempNotAdjustOrderDetailVO.getSbMonthly() )
                           || ( KANUtil.filterEmpty( tempNotAdjustOrderDetailVO.getSbMonthly() ) == null && sbMonthly.equals( tempNotAdjustOrderDetailVO.getMonthly() ) ) )
                     {
                        tempNotAdjustOrderDetailVOs.add( tempNotAdjustOrderDetailVO );
                     }
                  }

                  if ( tempNotAdjustOrderDetailVOs != null && tempNotAdjustOrderDetailVOs.size() > 0 )
                  {
                     // 非调整不用合并
                     // 这里有个例外（调整当前月的社保需要单独拿出来，上面按照月份无法区分）；
                     // 如果是查询当前月，补缴的一组，正常缴纳的一组,notNormal 为补缴当月社保
                     final List< OrderDetailVO > tempNormal = new ArrayList< OrderDetailVO >();
                     final List< OrderDetailVO > tempNotNormal = new ArrayList< OrderDetailVO >();
                     if ( sbMonthly.equals( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getMonthly() ) )
                     {
                        for ( OrderDetailVO vo : tempNotAdjustOrderDetailVOs )
                        {
                           if ( KANUtil.filterEmpty( vo.getSbMonthly() ) == null && isItemType( vo.getItemId(), "7", accountId ) )
                           {
                              tempNotNormal.add( vo );
                           }
                           else
                           {
                              tempNormal.add( vo );
                           }
                        }
                        if ( tempNormal.size() > 0 )
                        {
                           settlementDTOs.add( getSettlementDTO( settlementDTOs, tempNormal, contractId, tempEmployeeNameZH, tempEmployeeNameEN, tempClientNameZH, tempClientNameEN, tempEntityId, tempBusinessTypeId, ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getClientId(), ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getOrderId(), ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getMonthly(), sbMonthly ) );

                        }
                        if ( tempNotNormal.size() > 0 )
                        {
                           settlementDTOs.add( getSettlementDTO( settlementDTOs, tempNotNormal, contractId, tempEmployeeNameZH, tempEmployeeNameEN, tempClientNameZH, tempClientNameEN, tempEntityId, tempBusinessTypeId, ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getClientId(), ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getOrderId(), ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getMonthly(), sbMonthly ) );
                        }

                     }
                     else
                     {
                        settlementDTOs.add( getSettlementDTO( settlementDTOs, tempNotAdjustOrderDetailVOs, contractId, tempEmployeeNameZH, tempEmployeeNameEN, tempClientNameZH, tempClientNameEN, tempEntityId, tempBusinessTypeId, ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getClientId(), ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getOrderId(), ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getMonthly(), sbMonthly ) );
                     }
                  }

               }
            }

            // 如果有调整的数据
            if ( adjustOrderDetailVOs != null && adjustOrderDetailVOs.size() > 0 )
            {
               // 获取调整的所有月份,调整按月分类。同月合并,
               final List< String > adjustOrderDetailMonthlies = getMonthlyByOrderDetailVO( adjustOrderDetailVOs, true, accountId );
               for ( String monthly : adjustOrderDetailMonthlies )
               {
                  List< OrderDetailVO > tempAdjustOrderDetailVOs = new ArrayList< OrderDetailVO >();
                  for ( OrderDetailVO tempAdjustOrderDetailVO : adjustOrderDetailVOs )
                  {
                     if ( monthly.equals( tempAdjustOrderDetailVO.getMonthly() ) )
                     {
                        tempAdjustOrderDetailVOs.add( tempAdjustOrderDetailVO );
                     }
                  }

                  if ( tempAdjustOrderDetailVOs != null && tempAdjustOrderDetailVOs.size() > 0 )
                  {
                     // 合计重复的调整科目
                     tempAdjustOrderDetailVOs = mergeOrderDetailVOs( tempAdjustOrderDetailVOs );
                     final SettlementDTO settlementDTO = new SettlementDTO();
                     //  是调整数据
                     settlementDTO.setIsAdjustment( "1" );
                     settlementDTO.setOrderDetailVOs( tempAdjustOrderDetailVOs );
                     ServiceContractVO serviceContractVO = new ServiceContractVO();
                     serviceContractVO.setContractId( contractId );
                     serviceContractVO.setEmployeeNameZH( tempEmployeeNameZH );
                     serviceContractVO.setEmployeeNameEN( tempEmployeeNameEN );
                     serviceContractVO.setClientNameZH( tempClientNameZH );
                     serviceContractVO.setClientNameEN( tempClientNameEN );
                     serviceContractVO.setEmployeeNameEN( tempClientNameEN );
                     serviceContractVO.setEntityId( tempEntityId );
                     serviceContractVO.setBusinessTypeId( tempBusinessTypeId );
                     // clientID,orderId,monthly
                     serviceContractVO.setClientId( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getClientId() );
                     serviceContractVO.setOrderId( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getOrderId() );
                     serviceContractVO.setMonthly( ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getMonthly() );
                     settlementDTO.setServiceContractVO( serviceContractVO );
                     settlementDTOs.add( settlementDTO );
                  }

               }
            }

         }

      }

      pagedListHolder.setHolderSize( settlementDTOs.size() );
      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );
      pagedListHolder.setAdditionalObject( getAllContractMappingItems( orderBillDetailViewObjects, ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getAccountId() ) );
      // 手动分页
      if ( isPaged )
      {
         // 装载数据
         if ( settlementDTOs != null && settlementDTOs.size() > 0 )
         {

            for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                  && i < settlementDTOs.size(); i++ )
            {
               pagedListHolder.getSource().add( settlementDTOs.get( i ) );
            }

         }
      }
      else
      {
         pagedListHolder.setSource( settlementDTOs );
      }
      formatSettlementDTOPagedListHolder( pagedListHolder );
      return pagedListHolder;
   }

   /**
    * 调整数据不用判断sb表的数据。参考monthly.不用参考sbMonthly .非调整参考monthly
    * @param adjustOrderDetailVOs
    * @return
    */
   private List< String > getMonthlyByOrderDetailVO( final List< OrderDetailVO > orderDetailVOs, final boolean isAdjustment, final String accountId )
   {
      final List< String > allMonthlyList = new ArrayList< String >();
      for ( OrderDetailVO orderDetailVO : orderDetailVOs )
      {
         /*// 如果是调整。且本数据是调整数据,取monthly
         if ( isAdjustment && "1".equals( orderDetailVO.getIsAdjustment() ) && KANUtil.filterEmpty( orderDetailVO.getMonthly() ) != null )
         {
            allMonthlyList.add( orderDetailVO.getMonthly() );
         }
         // 如果不是调整，且数据部是调整数据。如果科目是社保，取sbmonthly，如果不是社保取monthly
         else if ( !isAdjustment && "2".equals( orderDetailVO.getIsAdjustment() ) )
         {
            if ( isItemType( orderDetailVO.getItemId(), "7", accountId ) && KANUtil.filterEmpty( orderDetailVO.getSbMonthly() ) != null )
            {
               allMonthlyList.add( orderDetailVO.getSbMonthly() );
            }
            else if ( !isItemType( orderDetailVO.getItemId(), "7", accountId ) && KANUtil.filterEmpty( orderDetailVO.getMonthly() ) != null )
            {
               allMonthlyList.add( orderDetailVO.getMonthly() );
            }
         }*/
         if ( KANUtil.filterEmpty( orderDetailVO.getMonthly() ) != null )
         {
            allMonthlyList.add( orderDetailVO.getMonthly() );
         }
         if ( KANUtil.filterEmpty( orderDetailVO.getSbMonthly() ) != null )
         {
            allMonthlyList.add( orderDetailVO.getSbMonthly() );
         }
         if ( KANUtil.filterEmpty( orderDetailVO ) != null )
         {
            allMonthlyList.add( orderDetailVO.getSbMonthly() );
         }

      }
      return KANUtil.getDistinctList( allMonthlyList );
   }

   private boolean isItemType( final String itemId, final String itemType, final String accountId )
   {
      boolean flag = false;
      try
      {
         final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( itemId );
         flag = itemType.equals( itemVO.getItemType() );
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return flag;
   }

   private List< String > getDistinctList( List< String > allList )
   {
      final List< String > distinctList = new ArrayList< String >();
      final StringBuffer sb = new StringBuffer();
      for ( int i = allList.size() - 1; i >= 0; i-- )
      {
         if ( sb.toString().contains( "#" + allList.get( i ) + "#" ) )
         {
            // 如果已存在
            allList.remove( i );
         }
         else
         {
            sb.append( "#" ).append( allList.get( i ) ).append( ( "#" ) );
            distinctList.add( allList.get( i ) );
            allList.remove( i );
         }
      }
      return distinctList;
   }

   /**
    * 使用sum函数的第一层查询
    */
   @Override
   public PagedListHolder getSumOrderBillHeaderViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      OrderBillHeaderViewDao orderBillHeaderViewDao = ( OrderBillHeaderViewDao ) getDao();
      final List< Object > orderBillHeaderViewObjects = orderBillHeaderViewDao.getSumOrderBillHeaderViewsByCondition( ( OrderBillHeaderView ) pagedListHolder.getObject() );
      // 按照客户，月份，状态分组,查出的数据已分组

      final List< Object > orderDTOs = new ArrayList< Object >();

      for ( Object object : orderBillHeaderViewObjects )
      {
         final OrderDTO orderDTO = new OrderDTO();
         final List< ServiceContractVO > serviceContractVOs = new ArrayList< ServiceContractVO >();
         final OrderHeaderVO orderHeaderVO = new OrderHeaderVO();
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) object;

         final ServiceContractVO serviceContractVO = new ServiceContractVO();
         //
         serviceContractVO.setBillAmountCompany( orderBillHeaderView.getBillAmountCompany() );
         serviceContractVO.setCostAmountCompany( orderBillHeaderView.getCostAmountCompany() );
         final String tempClientNameZH = orderBillHeaderView.getClientNameZH();
         final String tempClientNameEN = orderBillHeaderView.getClientNameEN();
         final String tempEntityId = orderBillHeaderView.getEntityId();
         final String tempBusinessTypeId = orderBillHeaderView.getBusinessTypeId();
         serviceContractVOs.add( serviceContractVO );
         orderHeaderVO.setClientId( orderBillHeaderView.getClientId() );
         orderHeaderVO.setMonthly( orderBillHeaderView.getMonthly() );
         orderHeaderVO.setStatus( orderBillHeaderView.getStatus() );
         orderHeaderVO.setOrderId( orderBillHeaderView.getOrderId() );
         orderHeaderVO.setClientNameZH( tempClientNameZH );
         orderHeaderVO.setClientNameEN( tempClientNameEN );
         orderHeaderVO.setEntityId( tempEntityId );
         orderHeaderVO.setBusinessTypeId( tempBusinessTypeId );
         orderDTO.setOrderHeaderVO( orderHeaderVO );
         orderDTO.setServiceContractVOs( serviceContractVOs );
         orderDTO.calAmountCompany();
         orderDTOs.add( orderDTO );
      }

      pagedListHolder.setHolderSize( orderDTOs.size() );
      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );
      // 手动分页
      if ( isPaged )
      {
         // 装载数据
         if ( orderDTOs != null && orderDTOs.size() > 0 )
         {

            for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                  && i < orderDTOs.size(); i++ )
            {
               pagedListHolder.getSource().add( orderDTOs.get( i ) );
            }

         }
      }
      else
      {
         pagedListHolder.setSource( orderDTOs );
      }
      // 第一层查询服务费
      for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
      {
         final OrderDTO tempOrderDTO = ( OrderDTO ) pagedListHolder.getSource().get( i );
         final String clientId = tempOrderDTO.getOrderHeaderVO().getClientId();
         final String orderId = tempOrderDTO.getOrderHeaderVO().getOrderId();
         final String monthly = tempOrderDTO.getOrderHeaderVO().getMonthly();
         final String status = tempOrderDTO.getOrderHeaderVO().getStatus();
         final String accountId = ( ( OrderBillHeaderView ) pagedListHolder.getObject() ).getAccountId();
         final String itemId = "";
         final OrderBillHeaderView tempView = new OrderBillHeaderView( clientId, orderId, monthly, status, itemId );
         tempView.setAccountId( accountId );
         List< Object > resultOrderBillDetailViews = orderBillHeaderViewDao.getOrderBillDetailViewsByCondition( tempView );
         // 统计服务费
         Double item_service_billAmountCompany = 0d;
         for ( Object o : resultOrderBillDetailViews )
         {
            // if itemId =122 .continue;
            if ( isItemType( ( ( OrderBillDetailView ) o ).getItemId(), "9", accountId ) )
            {
               item_service_billAmountCompany += Double.parseDouble( ( ( OrderBillDetailView ) o ).getBillAmountCompany() );
            }
         }
         final String item_service_billAmountCompanyStr = String.valueOf( item_service_billAmountCompany );
         ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setItemServiceFree( item_service_billAmountCompanyStr );
         // 合同统计，人数统计
         final Map< String, Integer > employeeContractMap = getDistinctEmployeeAndContractCount( resultOrderBillDetailViews );
         ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setEmployeeCount( String.valueOf( employeeContractMap.get( "distinctEmployeeCount" ) ) );
         ( ( OrderDTO ) pagedListHolder.getSource().get( i ) ).getOrderHeaderVO().setContractCount( String.valueOf( employeeContractMap.get( "distinctContractCount" ) ) );

      }
      formatOrderDTOPagedListHolder( pagedListHolder );
      return pagedListHolder;
   }

   private SettlementDTO getSettlementDTO( final List< Object > settlementDTOs, final List< OrderDetailVO > orderDetailVOs, final String contractId,
         final String tempEmployeeNameZH, final String tempEmployeeNameEN, final String tempClientNameZH, final String tempClientNameEN, final String tempEntityId,
         final String tempBusinessTypeId, final String clientId, final String orderId, final String monthly, final String sbMonthly )
   {
      final SettlementDTO settlementDTO = new SettlementDTO();
      settlementDTO.setOrderDetailVOs( orderDetailVOs );
      ServiceContractVO serviceContractVO = new ServiceContractVO();
      serviceContractVO.setContractId( contractId );
      serviceContractVO.setEmployeeNameZH( tempEmployeeNameZH );
      serviceContractVO.setEmployeeNameEN( tempEmployeeNameEN );
      serviceContractVO.setClientNameZH( tempClientNameZH );
      serviceContractVO.setClientNameEN( tempClientNameEN );
      serviceContractVO.setEntityId( tempEntityId );
      serviceContractVO.setBusinessTypeId( tempBusinessTypeId );
      // clientID,orderId,monthly
      serviceContractVO.setClientId( clientId );
      serviceContractVO.setOrderId( orderId );
      serviceContractVO.setMonthly( monthly );
      serviceContractVO.setSbMonthly( sbMonthly );
      settlementDTO.setServiceContractVO( serviceContractVO );
      return settlementDTO;
   }

}
