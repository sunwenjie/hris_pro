package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.payment.PayslipHeaderViewDao;
import com.kan.hro.domain.biz.payment.PayslipHeaderView;
import com.kan.hro.domain.biz.payment.PayslipTaxDTO;
import com.kan.hro.domain.biz.payment.PayslipTaxHeaderView;
import com.kan.hro.service.inf.biz.payment.PayslipHeaderViewService;

public class PayslipHeaderViewServiceImpl extends ContextService implements PayslipHeaderViewService
{
   /**  
    * Get PayslipTaxDTOs by Condition
    *	 生成工资个税合计 - 月度
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-05-04
   public PagedListHolder getPayslipTaxDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      // 初始化数据源
      List< Object > sources = new ArrayList< Object >();

      // 如果是导出
      if ( ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSubAction() != null
            && ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSubAction().equals( BaseAction.DOWNLOAD_OBJECTS ) )
      {
         // 设置状态为空-即可倒出“提交”和“已发放”数据
         ( ( PayslipHeaderView ) pagedListHolder.getObject() ).setStatus( "" );
         final List< Object > payslipHeaderViewObjects = ( ( PayslipHeaderViewDao ) getDao() ).getPayslipHeaderViewsByCondition( ( PayslipHeaderView ) pagedListHolder.getObject() );
         final List< Object > payslipTaxDTOs = fetchPayslipTaxDTOs( payslipHeaderViewObjects );
         sources.addAll( payslipTaxDTOs );
      }
      else
      {
         // 如果数据源不为空，默认取之
         if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            sources.addAll( pagedListHolder.getSource() );
         }
         else
         {
            if ( "taxAmountPersonal".equals( ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSortColumn() ) )
            {
               ( ( PayslipHeaderView ) pagedListHolder.getObject() ).setSortColumn( null );
            }
            final List< Object > payslipHeaderViewObjects = ( ( PayslipHeaderViewDao ) getDao() ).getPayslipHeaderViewsByCondition( ( PayslipHeaderView ) pagedListHolder.getObject() );
            final List< Object > payslipTaxDTOs = fetchPayslipTaxDTOs( payslipHeaderViewObjects );
            sources.addAll( payslipTaxDTOs );
         }
      }

      // 首次查询按个税升序排序
      if ( ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSortColumn() == null || ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSortColumn().isEmpty() )
      {
         if ( sources != null && sources.size() > 0 )
         {
            // 数据排序：个税金额升序
            Collections.sort( sources, new Comparator< Object >()
            {
               @Override
               public int compare( Object o1, Object o2 )
               {
                  final PayslipTaxDTO p1 = ( PayslipTaxDTO ) o1;
                  final PayslipTaxDTO p2 = ( PayslipTaxDTO ) o2;

                  if ( Float.parseFloat( p1.getPayslipTaxHeaderView().getTaxAmountPersonal() ) > ( Float.parseFloat( p2.getPayslipTaxHeaderView().getTaxAmountPersonal() ) ) )
                  {
                     return 1;
                  }
                  if ( Float.parseFloat( p1.getPayslipTaxHeaderView().getTaxAmountPersonal() ) < ( Float.parseFloat( p2.getPayslipTaxHeaderView().getTaxAmountPersonal() ) ) )
                  {
                     return -1;
                  }
                  return 0;
               }
            } );
         }

      }

      // “个税”为“0”不显示
//      if ( sources != null && sources.size() > 0 )
//      {
//         final List< Object > tempSources = new ArrayList< Object >();
//         for ( Object o : sources )
//         {
//            PayslipTaxDTO payslipTaxDTO = ( PayslipTaxDTO ) o;
//
//            if ( payslipTaxDTO.getPayslipTaxHeaderView() != null && payslipTaxDTO.getPayslipTaxHeaderView().getSalaryTax() != null
//                  && Double.parseDouble( payslipTaxDTO.getPayslipTaxHeaderView().getSalaryTax() ) != 0 )
//            {
//               tempSources.add( payslipTaxDTO );
//            }
//
//         }
//         sources = tempSources;
//      }

      pagedListHolder.setHolderSize( sources.size() );
      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );

      // 手动分页
      if ( isPaged )
      {
         // 装载数据
         if ( sources != null && sources.size() > 0 )
         {

            for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                  && i < sources.size(); i++ )
            {
               pagedListHolder.getSource().add( sources.get( i ) );
            }

         }
      }
      else
      {
         pagedListHolder.setSource( sources );
      }

      return pagedListHolder;
   }

   /**  
    * Get PayslipTaxDTOs
    *	生成个税合计DTO
    *	@param payslipHeaderViewObjects
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private List< Object > fetchPayslipTaxDTOs( final List< Object > payslipHeaderViewObjects ) throws KANException
   {
      // 初始化返回值对象
      final List< Object > payslipTaxDTOs = new ArrayList< Object >();

      // 存在工资单列表
      if ( payslipHeaderViewObjects != null && payslipHeaderViewObjects.size() > 0 )
      {
         // 遍历生成PayslipTaxDTO 列表
         for ( Object payslipHeaderViewObject : payslipHeaderViewObjects )
         {
            // 初始化PayslipHeaderView
            final PayslipHeaderView payslipHeaderView = ( PayslipHeaderView ) payslipHeaderViewObject;

            // 装载PayslipTaxDTO
            fetchPayslipTaxDTO( payslipTaxDTOs, payslipHeaderView );
         }
      }

      return payslipTaxDTOs;
   }

   /**  
    * Fetch PayslipTaxDTO
    *	个税合计DTO填入数据
    *	@param payslipDetailView
    *	@param payslipTaxDTOs
    * @throws KANException 
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private void fetchPayslipTaxDTO( final List< Object > payslipTaxDTOs, final PayslipHeaderView payslipHeaderView ) throws KANException
   {
      // 初始化包含标示
      boolean exist = false;

      // PayslipTaxDTO 包含数据
      if ( payslipTaxDTOs != null && payslipTaxDTOs.size() > 0 )
      {
         // 遍历
         for ( Object payslipTaxDTOObject : payslipTaxDTOs )
         {
            // 初始化PayslipTaxDTO
            final PayslipTaxDTO payslipTaxDTO = ( PayslipTaxDTO ) payslipTaxDTOObject;

            // 初始化PayslipTaxHeaderView
            final PayslipTaxHeaderView payslipTaxHeaderView = payslipTaxDTO.getPayslipTaxHeaderView();

            // 比较EmployeeId、Monthly、 Status、 Entity
            if ( payslipTaxHeaderView.getEmployeeId().equals( payslipHeaderView.getEmployeeId() ) && payslipTaxHeaderView.getMonthly().equals( payslipHeaderView.getMonthly() )
                  && payslipTaxHeaderView.getStatus().equals( payslipHeaderView.getStatus() ) && payslipTaxHeaderView.getEntityId().equals( payslipHeaderView.getEntityId() ) )
            {
               if ( payslipHeaderView.getItemGroupId() != null && payslipHeaderView.getItemGroupId().equals( INDEPENDENT_CALCULATE_TIEMGROUP ) )
               {
                  if ( payslipTaxHeaderView.getItemGroupId() != null && payslipTaxHeaderView.getItemGroupId().equals( payslipHeaderView.getItemGroupId() ) )
                  {
                     // 累加
                     payslipTaxHeaderView.addBillAmountCompany( payslipHeaderView.getBillAmountPersonal() );
                     payslipTaxHeaderView.addBillAmountPersonal( payslipHeaderView.getBillAmountCompany() );
                     payslipTaxHeaderView.addCostAmountCompany( payslipHeaderView.getCostAmountCompany() );
                     payslipTaxHeaderView.addCostAmountPersonal( payslipHeaderView.getCostAmountPersonal() );
                     payslipTaxHeaderView.addAddtionalBillAmountPersonal( payslipHeaderView.getAddtionalBillAmountPersonal() );
                     payslipTaxHeaderView.addTaxAgentAmountPersonal( payslipHeaderView.getTaxAgentAmountPersonal() );

                     // 计算个税
                     payslipTaxHeaderView.addSalaryTax( payslipHeaderView.getSalaryTax() );

                     exist = true;
                     break;
                  }
                  else
                     continue;
               }
               // 累加
               payslipTaxHeaderView.addBillAmountCompany( payslipHeaderView.getBillAmountPersonal() );
               payslipTaxHeaderView.addBillAmountPersonal( payslipHeaderView.getBillAmountCompany() );
               payslipTaxHeaderView.addCostAmountCompany( payslipHeaderView.getCostAmountCompany() );
               payslipTaxHeaderView.addCostAmountPersonal( payslipHeaderView.getCostAmountPersonal() );
               payslipTaxHeaderView.addAddtionalBillAmountPersonal( payslipHeaderView.getAddtionalBillAmountPersonal() );
               payslipTaxHeaderView.addTaxAgentAmountPersonal( payslipHeaderView.getTaxAgentAmountPersonal() );

               // 计算个税
               payslipTaxHeaderView.addSalaryTax( payslipHeaderView.getSalaryTax() );

               exist = true;
               break;
            }
         }
      }

      // 如果不存在
      if ( !exist )
      {
         // 实例化PayslipTaxDTO
         final PayslipTaxDTO payslipTaxDTO = new PayslipTaxDTO();

         // 初始化PayslipTaxHeaderView
         final PayslipTaxHeaderView payslipTaxHeaderView = getPayslipTaxHeaderView( payslipHeaderView );
         payslipTaxDTO.setPayslipTaxHeaderView( payslipTaxHeaderView );

         payslipTaxDTOs.add( payslipTaxDTO );
      }

   }

   /**  
    * GetPayslipTaxHeaderView
    *	根据PayslipDetailView 生成PayslipTaxHeaderView
    *	@param payslipDetailView
    *	@return
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private PayslipTaxHeaderView getPayslipTaxHeaderView( PayslipHeaderView payslipHeaderView )
   {
      final PayslipTaxHeaderView payslipTaxHeaderView = new PayslipTaxHeaderView();
      payslipTaxHeaderView.setEmployeeId( payslipHeaderView.getEmployeeId() );
      payslipTaxHeaderView.setEmployeeNameZH( payslipHeaderView.getEmployeeNameZH() );
      payslipTaxHeaderView.setEmployeeNameEN( payslipHeaderView.getEmployeeNameEN() );
      payslipTaxHeaderView.setCertificateNumber( payslipHeaderView.getCertificateNumber() );
      payslipTaxHeaderView.setBankAccount( payslipHeaderView.getBankAccount() );
      payslipTaxHeaderView.setBankId( payslipHeaderView.getBankId() );
      payslipTaxHeaderView.setBankNameZH( payslipHeaderView.getBankNameZH() );
      payslipTaxHeaderView.setBankNameEN( payslipHeaderView.getBankNameEN() );
      payslipTaxHeaderView.setClientId( payslipHeaderView.getClientId() );
      payslipTaxHeaderView.setCorpId( payslipHeaderView.getCorpId() );
      payslipTaxHeaderView.setOrderId( payslipHeaderView.getOrderId() );
      payslipTaxHeaderView.setVendorId( payslipHeaderView.getVendorId() );
      payslipTaxHeaderView.setVendorNameZH( payslipHeaderView.getVendorNameZH() );
      payslipTaxHeaderView.setVendorNameEN( payslipHeaderView.getVendorNameEN() );
      payslipTaxHeaderView.setMonthly( payslipHeaderView.getMonthly() );
      payslipTaxHeaderView.setStatus( payslipHeaderView.getStatus() );
      payslipTaxHeaderView.setEntityId( payslipHeaderView.getEntityId() );
      payslipTaxHeaderView.setBusinessTypeId( payslipHeaderView.getBusinessTypeId() );
      payslipTaxHeaderView.setCertificateType( payslipHeaderView.getCertificateType() );
      payslipTaxHeaderView.setBillAmountCompany( payslipHeaderView.getBillAmountCompany() );
      payslipTaxHeaderView.setBillAmountPersonal( payslipHeaderView.getBillAmountPersonal() );
      payslipTaxHeaderView.setCostAmountCompany( payslipHeaderView.getCostAmountCompany() );
      payslipTaxHeaderView.setCostAmountPersonal( payslipHeaderView.getCostAmountPersonal() );
      payslipTaxHeaderView.setAddtionalBillAmountPersonal( payslipHeaderView.getAddtionalBillAmountPersonal() );
      payslipTaxHeaderView.setTaxAgentAmountPersonal( payslipHeaderView.getTaxAgentAmountPersonal() );
      payslipTaxHeaderView.setSalaryTax( payslipHeaderView.getSalaryTax() );
      payslipTaxHeaderView.setCityId( payslipHeaderView.getCityId() );
      payslipTaxHeaderView.setRevenue( ( Double.parseDouble( payslipHeaderView.getAddtionalBillAmountPersonal() ) + Double.parseDouble( payslipHeaderView.getTaxAgentAmountPersonal() ) )
            + "" );
      payslipTaxHeaderView.setNum( "0" );
      payslipTaxHeaderView.setTaxFlag( "1" );
      payslipTaxHeaderView.setItemGroupId( payslipHeaderView.getItemGroupId() );
      payslipTaxHeaderView.setNationNality( "156" );
      int residencyType = Integer.parseInt( payslipHeaderView.getResidencyType() );
      payslipTaxHeaderView.setDeduction( residencyType == 6 || residencyType == 5 ? "4800" : "3500" );
      if ( payslipHeaderView.getItemGroupId() != null && payslipHeaderView.getItemGroupId().equals( INDEPENDENT_CALCULATE_TIEMGROUP ) )
      {
         long firstDay = KANUtil.getDays( KANUtil.getFirstCalendar( KANUtil.getYear( new Date() ) + "/01" ) );
         long lastDay = KANUtil.getDays( KANUtil.getLastCalendar( KANUtil.getYear( new Date() ) + "/12" ) );
         payslipTaxHeaderView.setNumDay( lastDay - firstDay + 1 + "" );
         payslipTaxHeaderView.setItemCode( "010001" );
         payslipTaxHeaderView.setStartDate( KANUtil.formatDate( KANUtil.getFirstDate( KANUtil.getYear( new Date() ) + "/01" ), "yyyyMMdd" ) );
         payslipTaxHeaderView.setEndDate( KANUtil.formatDate( KANUtil.getLastDate( KANUtil.getYear( new Date() ) + "/12" ), "yyyyMMdd" ) );
      }
      else
      {
         payslipTaxHeaderView.setStartDate( KANUtil.formatDate( KANUtil.getFirstDate( payslipHeaderView.getMonthly() ), "yyyyMMdd" ) );
         payslipTaxHeaderView.setEndDate( KANUtil.formatDate( KANUtil.getLastDate( payslipHeaderView.getMonthly() ), "yyyyMMdd" ) );
         int lastDay = Integer.parseInt( KANUtil.getFormatDate( KANUtil.lastDayOfMonth( payslipHeaderView.getMonthly() ) ) );
         int firstDay = Integer.parseInt( KANUtil.getFormatDate( KANUtil.firstDayOfMonth( payslipHeaderView.getMonthly() ) ) );
         payslipTaxHeaderView.setNumDay( lastDay - firstDay + 1 + "" );
         payslipTaxHeaderView.setItemCode( "010000" );
      }
      return payslipTaxHeaderView;
   }
}
