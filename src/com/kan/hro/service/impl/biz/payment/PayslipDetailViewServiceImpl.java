package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.List;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.LaborContractTemplateDao;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.payment.PayslipDetailViewDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.payment.PayslipDTO;
import com.kan.hro.domain.biz.payment.PayslipDetailView;
import com.kan.hro.domain.biz.payment.PayslipHeaderView;
import com.kan.hro.service.inf.biz.payment.PayslipDetailViewService;

public class PayslipDetailViewServiceImpl extends ContextService implements PayslipDetailViewService
{
   private OrderDetailDao orderDetailDao;

   private LaborContractTemplateDao laborContractTemplateDao;

   private EmployeeContractDao employeeContractDao;

   private EmployeeContractSalaryDao employeeContractSalaryDao;

   public final OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public final void setOrderDetailDao( OrderDetailDao orderDetailDao )
   {
      this.orderDetailDao = orderDetailDao;
   }

   public final LaborContractTemplateDao getLaborContractTemplateDao()
   {
      return laborContractTemplateDao;
   }

   public final void setLaborContractTemplateDao( LaborContractTemplateDao laborContractTemplateDao )
   {
      this.laborContractTemplateDao = laborContractTemplateDao;
   }

   public final EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public final void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public final EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public final void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   /**  
    * Get PayslipDTOs by Condition
    *	 获得工资单数据
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getPayslipDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      // 初始化数据源和所有科目集合
      final List< Object > sources = new ArrayList< Object >();
      final List< ItemVO > items = new ArrayList< ItemVO >();

      final List< Object > payslipDetailViewObjects = ( ( PayslipDetailViewDao ) getDao() ).getPayslipDetailViewsByCondition( ( PayslipDetailView ) pagedListHolder.getObject() );
      sources.addAll( getPayslipDTOs( items, payslipDetailViewObjects ) );
      pagedListHolder.setAdditionalObject( items );
      pagedListHolder.setHolderSize( sources.size() );
      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );

      // 手动分页
      if ( isPaged )
      {
         if ( sources != null && sources.size() > 0 )
         {
            // 如果当前页数大于总页数自动跳转首页
            if ( ( pagedListHolder.getPage() * pagedListHolder.getPageSize() ) >= sources.size() )
            {
               for ( int i = 0; i < pagedListHolder.getPageSize(); i++ )
               {
                  pagedListHolder.getSource().add( sources.get( i ) );
               }
               pagedListHolder.setPage( "0" );
            }
            else
            {
               for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                     && i < sources.size(); i++ )
               {
                  pagedListHolder.getSource().add( sources.get( i ) );
               }
            }
         }
      }
      else
      {
         pagedListHolder.setSource( sources );
      }

      calculateFirstPayMPF( pagedListHolder.getSource() );
      return pagedListHolder;
   }

   /**  
    * Get PayslipDTOs
    * 
    *	生成  PayslipDTO 集合 和所有科目 ItemVO 集合
    *	@param items
    *	@param payslipDetailViewObjects
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private List< Object > getPayslipDTOs( final List< ItemVO > items, final List< Object > payslipDetailViewObjects ) throws KANException
   {
      // 初始化返回值对象
      final List< Object > payslipDTOs = new ArrayList< Object >();

      if ( payslipDetailViewObjects != null && payslipDetailViewObjects.size() > 0 )
      {
         // 遍历生成PayslipDTO 列表
         for ( Object payslipDetailViewObject : payslipDetailViewObjects )
         {
            // 初始化PayslipDetailView
            final PayslipDetailView payslipDetailView = ( PayslipDetailView ) payslipDetailViewObject;

            /** 装载PayslipDTO */
            fetchPayslipDTOs( payslipDTOs, payslipDetailView );

            /** 装载ItemVO集合 */
            fetchItemVOs( items, payslipDetailView );
         }
      }

      return payslipDTOs;
   }

   /**  
    * Fetch PayslipDTO
    * 
    *	装载PayslipDTO
    *	@param payslipDTOs
    *	@param payslipDetailView
    */
   // Reviewed by Kevin Jin at 2014-05-04
   public void fetchPayslipDTOs( final List< Object > payslipDTOs, final PayslipDetailView payslipDetailView )
   {
      if ( payslipDTOs != null && payslipDTOs.size() > 0 )
      {
         for ( Object payslipDTOObject : payslipDTOs )
         {
            // 初始化PayslipDTO
            final PayslipDTO payslipDTO = ( PayslipDTO ) payslipDTOObject;
            // 初始化PayslipHeaderView
            final PayslipHeaderView payslipHeaderView = payslipDTO.getPayslipHeaderView();

            // 因存在个人合同续签，去除合同ID判断
            if ( payslipHeaderView != null && payslipHeaderView.getEmployeeId() != null
                  && payslipHeaderView.getEmployeeId().equals( payslipDetailView.getEmployeeId() )
                  //                  && payslipHeaderView.getContractId() != null
                  //                  && payslipHeaderView.getContractId().equals( payslipDetailView.getContractId() )
                  && payslipHeaderView.getMonthly() != null && payslipHeaderView.getMonthly().equals( payslipDetailView.getMonthly() ) && payslipHeaderView.getStatus() != null
                  && payslipHeaderView.getStatus().equals( payslipDetailView.getStatus() ) )
            {
               // TemplateId取较大值
               final Double d1 = KANUtil.filterEmpty( payslipHeaderView.getTemplateId() ) == null ? 0 : Double.valueOf( payslipHeaderView.getTemplateId() );
               final Double d2 = KANUtil.filterEmpty( payslipDetailView.getTemplateId() ) == null ? 0 : Double.valueOf( payslipDetailView.getTemplateId() );

               if ( d1.compareTo( d2 ) < 0 )
               {
                  payslipHeaderView.setTemplateId( payslipDetailView.getTemplateId() );
               }

               fetchPayslipDTO( payslipDTO, payslipDetailView );

               return;
            }
         }
      }

      payslipDTOs.add( fetchPayslipDTO( new PayslipDTO(), payslipDetailView ) );
   }

   /**  
    * Get PayslipDTO
    *	根据条件生成新的PayslipDTO 或者修改 PayslipDTO
    *	@param payslipDTO
    *	@param payslipDetailView
    *	@return
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private PayslipDTO fetchPayslipDTO( final PayslipDTO payslipDTO, final PayslipDetailView payslipDetailView )
   {
      try
      {
         // 如果PayslipDTO为新增
         if ( payslipDTO.getPayslipDetailViews() == null || payslipDTO.getPayslipDetailViews().size() == 0 )
         {
            payslipDTO.setPayslipHeaderView( getPayslipHeaderView( payslipDetailView ) );
            payslipDTO.getPayslipDetailViews().add( payslipDetailView );
         }
         else
         {
            /** 处理PayslipHeaderView */
            // 初始化PayslipHeaderView
            final PayslipHeaderView payslipHeaderView = payslipDTO.getPayslipHeaderView();

            // PayslipHeaderView计算合计值
            payslipHeaderView.addBillAmountCompany( payslipDetailView.getBillAmountCompany() );
            payslipHeaderView.addBillAmountPersonal( payslipDetailView.getBillAmountPersonal() );
            payslipHeaderView.addCostAmountCompany( payslipDetailView.getCostAmountCompany() );
            payslipHeaderView.addCostAmountPersonal( payslipDetailView.getCostAmountPersonal() );

            if ( KANUtil.filterEmpty( payslipHeaderView.getHeaderId() ) != null
                  && !KANUtil.filterEmpty( payslipHeaderView.getHeaderId() ).contains( "#" + payslipDetailView.getHeaderId() ) )
            {
               // 个税添加
               payslipHeaderView.addSalaryTax( payslipDetailView.getSalaryTax() );

               // 代扣税工资添加
               payslipHeaderView.addTaxAgentAmountPersonal( payslipDetailView.getTaxAgentAmountPersonal() );

               payslipHeaderView.addAddtionalBillAmountPersonal( payslipDetailView.getAddtionalBillAmountPersonal() );

               // 回写HeaderId
               payslipHeaderView.setHeaderId( payslipHeaderView.getHeaderId() + "#" + payslipDetailView.getHeaderId() );
            }

            // 如果是社保类型，计算“公司”、“个人”合计
            if ( "7".equals( payslipDetailView.getItemType() ) )
            {
               payslipHeaderView.addSbAmountCompany( String.valueOf( Double.valueOf( payslipDetailView.getCostAmountCompany() ) ) );

               payslipHeaderView.addSbAmountPersonal( String.valueOf( Double.valueOf( payslipDetailView.getBillAmountPersonal() )
                     - Double.valueOf( payslipDetailView.getCostAmountPersonal() ) ) );
            }

            // 计算应发工资
            payslipHeaderView.addSbAmount( getSbAmount( payslipDetailView ) );

            /** 处理PayslipDetailView */
            // 标识payslipDetailView是否存在
            Boolean exist = false;

            for ( PayslipDetailView tempPayslipDetailView : payslipDTO.getPayslipDetailViews() )
            {
               // 如果科目已存在
               if ( tempPayslipDetailView != null && tempPayslipDetailView.getItemId() != null && tempPayslipDetailView.getItemId().equals( payslipDetailView.getItemId() ) )
               {
                  // 该对应的tempPayslipDetailView计算合计值
                  tempPayslipDetailView.addBillAmountCompany( payslipDetailView.getBillAmountCompany() );
                  tempPayslipDetailView.addBillAmountPersonal( payslipDetailView.getBillAmountPersonal() );
                  tempPayslipDetailView.addCostAmountCompany( payslipDetailView.getCostAmountCompany() );
                  tempPayslipDetailView.addCostAmountPersonal( payslipDetailView.getCostAmountPersonal() );

                  exist = true;
                  break;
               }
            }

            // 如果科目不存在
            if ( !exist )
            {
               // 直接添加PayslipDetailView
               payslipDTO.getPayslipDetailViews().add( payslipDetailView );
            }
         }
      }
      catch ( NumberFormatException e )
      {
         e.printStackTrace();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

      return payslipDTO;
   }

   /**  
    * Get PayslipHeaderView
    *	根据PayslipDetailView构建PayslipHeaderView
    *	@param payslipDetailView
    *	@return
    * @throws KANException 
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private PayslipHeaderView getPayslipHeaderView( final PayslipDetailView payslipDetailView ) throws KANException
   {
      final PayslipHeaderView payslipHeaderView = new PayslipHeaderView();
      payslipHeaderView.setAccountId( payslipDetailView.getAccountId() );
      payslipHeaderView.setCorpId( payslipDetailView.getCorpId() );
      payslipHeaderView.setHeaderId( "#" + payslipDetailView.getHeaderId() );
      payslipHeaderView.setEmployeeId( payslipDetailView.getEmployeeId() );
      payslipHeaderView.setEmployeeNameZH( payslipDetailView.getEmployeeNameZH() );
      payslipHeaderView.setEmployeeNameEN( payslipDetailView.getEmployeeNameEN() );
      payslipHeaderView.setCertificateNumber( payslipDetailView.getCertificateNumber() );
      payslipHeaderView.setBankAccount( payslipDetailView.getBankAccount() );
      payslipHeaderView.setBankId( payslipDetailView.getBankId() );
      payslipHeaderView.setBankNameZH( payslipDetailView.getBankNameZH() );
      payslipHeaderView.setBankNameEN( payslipDetailView.getBankNameEN() );
      payslipHeaderView.setBankBranch( payslipDetailView.getBankBranch() );
      payslipHeaderView.setClientId( payslipDetailView.getClientId() );
      payslipHeaderView.setCorpId( payslipDetailView.getCorpId() );
      payslipHeaderView.setOrderId( payslipDetailView.getOrderId() );
      payslipHeaderView.setVendorId( payslipDetailView.getVendorId() );
      payslipHeaderView.setVendorNameZH( payslipDetailView.getVendorNameZH() );
      payslipHeaderView.setVendorNameEN( payslipDetailView.getVendorNameEN() );
      // 一个人存在续签，需显示多个合同的合计，不需要合同ID
      //      payslipHeaderView.setContractId( payslipDetailView.getContractId() );
      payslipHeaderView.setMonthly( payslipDetailView.getMonthly() );
      payslipHeaderView.setStatus( payslipDetailView.getStatus() );
      payslipHeaderView.setEntityId( payslipDetailView.getEntityId() );
      payslipHeaderView.setBusinessTypeId( payslipDetailView.getBusinessTypeId() );
      payslipHeaderView.setStartDate( payslipDetailView.getStartDate() );
      payslipHeaderView.setEndDate( payslipDetailView.getEndDate() );
      payslipHeaderView.setCertificateType( payslipDetailView.getCertificateType() );
      payslipHeaderView.setBillAmountCompany( payslipDetailView.getBillAmountCompany() );
      payslipHeaderView.setBillAmountPersonal( payslipDetailView.getBillAmountPersonal() );
      payslipHeaderView.setCostAmountCompany( payslipDetailView.getCostAmountCompany() );
      payslipHeaderView.setCostAmountPersonal( payslipDetailView.getCostAmountPersonal() );
      payslipHeaderView.setSalaryTax( payslipDetailView.getSalaryTax() );
      payslipHeaderView.setAnnualBonusTax( payslipDetailView.getAnnualBonusTax() );
      payslipHeaderView.setAnnualBonus( payslipDetailView.getAnnualBonus() );
      payslipHeaderView.setAddtionalBillAmountPersonal( payslipDetailView.getAddtionalBillAmountPersonal() );
      payslipHeaderView.setTaxAgentAmountPersonal( payslipDetailView.getTaxAgentAmountPersonal() );
      payslipHeaderView.setTempPositionIds( payslipDetailView.getTempPositionIds() );
      payslipHeaderView.setTempBranchIds( payslipDetailView.getTempBranchIds() );
      payslipHeaderView.setTempParentBranchIds( payslipDetailView.getTempParentBranchIds() );
      payslipHeaderView.setCityId( payslipDetailView.getCityId() );
      payslipHeaderView.setClientNO( payslipDetailView.getClientNO() );
      payslipHeaderView.setOrderDetailId( payslipDetailView.getOrderDetailId() );
      payslipHeaderView.setCurrency( payslipDetailView.getCurrency() );
      payslipHeaderView.setTemplateId( payslipDetailView.getTemplateId() );
      payslipHeaderView.setSalaryBalance( "0" );
      payslipHeaderView.setSalaryBase( payslipDetailView.getSalaryBase() );
      payslipHeaderView.setSettlementBranch( payslipDetailView.getSettlementBranch() );

      // 初始化
      payslipHeaderView.setSbAmountCompany( "0" );
      payslipHeaderView.setSbAmountPersonal( "0" );

      // 初始化公司和个人的社保合计
      if ( "7".equals( payslipDetailView.getItemType() ) )
      {
         payslipHeaderView.addSbAmountCompany( String.valueOf( Double.valueOf( payslipDetailView.getCostAmountCompany() ) ) );

         payslipHeaderView.addSbAmountPersonal( String.valueOf( Double.valueOf( payslipDetailView.getBillAmountPersonal() )
               - Double.valueOf( payslipDetailView.getCostAmountPersonal() ) ) );
      }

      // 计算社保公积金合计
      payslipHeaderView.setSbAmount( getSbAmount( payslipDetailView ) );

      // 添加劳务合同模板名称
      try
      {
         final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( payslipDetailView.getContractId() );

         if ( employeeContractVO != null )
         {
            final String tempateId = employeeContractVO.getTemplateId();

            if ( laborContractTemplateDao.getLaborContractTemplateVOByLaborContractTemplateId( tempateId ) != null )
            {
               payslipHeaderView.setTemplateName( laborContractTemplateDao.getLaborContractTemplateVOByLaborContractTemplateId( tempateId ).getNameZH() );
            }
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      payslipHeaderView.setEmployeeRemark1( payslipDetailView.getEmployeeRemark1() );
      payslipHeaderView.setContractRemark1( payslipDetailView.getContractRemark1() );
      payslipHeaderView.setContractStartDate( payslipDetailView.getContractStartDate() );
      payslipHeaderView.setCircleStartDay( payslipDetailView.getCircleStartDay() );
      payslipHeaderView.setCircleEndDay( payslipDetailView.getCircleEndDay() );
      payslipHeaderView.setStartWorkDate( payslipDetailView.getStartWorkDate() );
      payslipHeaderView.setLastWorkDate( payslipDetailView.getLastWorkDate() );
      payslipHeaderView.setRemark5( payslipDetailView.getRemark5() );
      return payslipHeaderView;
   }

   // 计算应发工资
   private String getSbAmount( final PayslipDetailView payslipDetailView ) throws KANException
   {
      final String itemId = payslipDetailView.getItemId();

      if ( KANConstants.getKANAccountConstants( payslipDetailView.getAccountId() ) != null )
      {
         final ItemVO itemVO = KANConstants.getKANAccountConstants( payslipDetailView.getAccountId() ).getItemVOByItemId( itemId );

         // 如果科目 “计算个税”为是减去个人支出
         if ( "1".equals( itemVO.getPersonalTax() ) )
         {
            //            return String.valueOf( Double.valueOf( payslipDetailView.getAddtionalBillAmountPersonal() ) - Double.valueOf( payslipDetailView.getCostAmountPersonal() ) );
            return String.valueOf( -Double.valueOf( payslipDetailView.getCostAmountPersonal() ) );
         }
         // 如果科目 “计算个税”不为是不减个人支出
         else
         {
            return "0";
         }
      }
      else
      {
         return "0";
      }
   }

   /**  
    * FetchItemVOs
    * 
    *	生成包含所有科目的集合
    *	@param payslipDetailView
    *	@param itemVOs
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private void fetchItemVOs( final List< ItemVO > itemVOs, final PayslipDetailView payslipDetailView )
   {
      // 判断科目是否存在科目集合中，不存在则新增
      if ( itemVOs != null && itemVOs.size() > 0 )
      {
         for ( ItemVO itemVO : itemVOs )
         {
            if ( itemVO.getItemId().equals( payslipDetailView.getItemId() ) )
            {
               return;
            }
         }
      }

      // 初始化新增ItemVO
      final ItemVO itemVO = new ItemVO();
      itemVO.setItemId( payslipDetailView.getItemId() );
      itemVO.setItemType( payslipDetailView.getItemType() );
      itemVO.setItemNo( payslipDetailView.getItemNo() );
      itemVOs.add( itemVO );
   }

   /**  
    * FetchItemVOs
    * 
    * 计算第一次加纳强基金
    * @param List< Object > payslipDTOs
    * @throws KANException 
    */
   // Add by siuvan @2015-01-21
   private void calculateFirstPayMPF( final List< Object > payslipDTOs ) throws KANException
   {
      if ( payslipDTOs != null && payslipDTOs.size() > 0 )
      {
         for ( Object o : payslipDTOs )
         {
            final PayslipDTO payslipDTO = ( PayslipDTO ) o;
            // 如果是第一次缴纳强基金
            if ( payslipDTO.getPayslipHeaderView() != null && payslipDTO.getPayslipHeaderView().isFirstPayMPF() && payslipDTO.getPayslipHeaderView().getSumSalaryMonthNum() > 0 )
            {
               final PayslipDetailView payslipDetailView = new PayslipDetailView();
               payslipDetailView.setAccountId( payslipDTO.getPayslipHeaderView().getAccountId() );
               payslipDetailView.setCorpId( payslipDTO.getPayslipHeaderView().getCorpId() );
               payslipDetailView.setEmployeeId( payslipDTO.getPayslipHeaderView().getEmployeeId() );
               payslipDetailView.setMonthlyBegin( KANUtil.getMonthly( payslipDTO.getPayslipHeaderView().getMonthly(), -payslipDTO.getPayslipHeaderView().getSumSalaryMonthNum() ) );
               payslipDetailView.setMonthlyEnd( KANUtil.getMonthly( payslipDTO.getPayslipHeaderView().getMonthly(), -1 ) );

               final List< ItemVO > items = new ArrayList< ItemVO >();

               final List< Object > payslipDetailViewObjects = ( ( PayslipDetailViewDao ) getDao() ).getPayslipDetailViewsByCondition( payslipDetailView );

               final List< Object > historyPayslipDTOs = getPayslipDTOs( items, payslipDetailViewObjects );
               if ( historyPayslipDTOs != null && historyPayslipDTOs.size() > 0 )
               {
                  double m = 0;
                  for ( Object oo : historyPayslipDTOs )
                  {
                     final PayslipDTO historyPayslipDTO = ( PayslipDTO ) oo;
                     double tempTaxableSalary = Double.valueOf( historyPayslipDTO.getPayslipHeaderView().getTaxableSalary() );
                     m = m + ( tempTaxableSalary * 0.05 > 1500 ? 1500 : tempTaxableSalary * 0.05 );
                  }

                  payslipDTO.getPayslipHeaderView().setFisrtPayMPF( String.valueOf( m ) );
               }
            }

         }
      }
   }

}
