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
    *	 ��ù��ʵ�����
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getPayslipDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      // ��ʼ������Դ�����п�Ŀ����
      final List< Object > sources = new ArrayList< Object >();
      final List< ItemVO > items = new ArrayList< ItemVO >();

      final List< Object > payslipDetailViewObjects = ( ( PayslipDetailViewDao ) getDao() ).getPayslipDetailViewsByCondition( ( PayslipDetailView ) pagedListHolder.getObject() );
      sources.addAll( getPayslipDTOs( items, payslipDetailViewObjects ) );
      pagedListHolder.setAdditionalObject( items );
      pagedListHolder.setHolderSize( sources.size() );
      // ��������Դ
      pagedListHolder.setSource( new ArrayList< Object >() );

      // �ֶ���ҳ
      if ( isPaged )
      {
         if ( sources != null && sources.size() > 0 )
         {
            // �����ǰҳ��������ҳ���Զ���ת��ҳ
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
    *	����  PayslipDTO ���� �����п�Ŀ ItemVO ����
    *	@param items
    *	@param payslipDetailViewObjects
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private List< Object > getPayslipDTOs( final List< ItemVO > items, final List< Object > payslipDetailViewObjects ) throws KANException
   {
      // ��ʼ������ֵ����
      final List< Object > payslipDTOs = new ArrayList< Object >();

      if ( payslipDetailViewObjects != null && payslipDetailViewObjects.size() > 0 )
      {
         // ��������PayslipDTO �б�
         for ( Object payslipDetailViewObject : payslipDetailViewObjects )
         {
            // ��ʼ��PayslipDetailView
            final PayslipDetailView payslipDetailView = ( PayslipDetailView ) payslipDetailViewObject;

            /** װ��PayslipDTO */
            fetchPayslipDTOs( payslipDTOs, payslipDetailView );

            /** װ��ItemVO���� */
            fetchItemVOs( items, payslipDetailView );
         }
      }

      return payslipDTOs;
   }

   /**  
    * Fetch PayslipDTO
    * 
    *	װ��PayslipDTO
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
            // ��ʼ��PayslipDTO
            final PayslipDTO payslipDTO = ( PayslipDTO ) payslipDTOObject;
            // ��ʼ��PayslipHeaderView
            final PayslipHeaderView payslipHeaderView = payslipDTO.getPayslipHeaderView();

            // ����ڸ��˺�ͬ��ǩ��ȥ����ͬID�ж�
            if ( payslipHeaderView != null && payslipHeaderView.getEmployeeId() != null
                  && payslipHeaderView.getEmployeeId().equals( payslipDetailView.getEmployeeId() )
                  //                  && payslipHeaderView.getContractId() != null
                  //                  && payslipHeaderView.getContractId().equals( payslipDetailView.getContractId() )
                  && payslipHeaderView.getMonthly() != null && payslipHeaderView.getMonthly().equals( payslipDetailView.getMonthly() ) && payslipHeaderView.getStatus() != null
                  && payslipHeaderView.getStatus().equals( payslipDetailView.getStatus() ) )
            {
               // TemplateIdȡ�ϴ�ֵ
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
    *	�������������µ�PayslipDTO �����޸� PayslipDTO
    *	@param payslipDTO
    *	@param payslipDetailView
    *	@return
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private PayslipDTO fetchPayslipDTO( final PayslipDTO payslipDTO, final PayslipDetailView payslipDetailView )
   {
      try
      {
         // ���PayslipDTOΪ����
         if ( payslipDTO.getPayslipDetailViews() == null || payslipDTO.getPayslipDetailViews().size() == 0 )
         {
            payslipDTO.setPayslipHeaderView( getPayslipHeaderView( payslipDetailView ) );
            payslipDTO.getPayslipDetailViews().add( payslipDetailView );
         }
         else
         {
            /** ����PayslipHeaderView */
            // ��ʼ��PayslipHeaderView
            final PayslipHeaderView payslipHeaderView = payslipDTO.getPayslipHeaderView();

            // PayslipHeaderView����ϼ�ֵ
            payslipHeaderView.addBillAmountCompany( payslipDetailView.getBillAmountCompany() );
            payslipHeaderView.addBillAmountPersonal( payslipDetailView.getBillAmountPersonal() );
            payslipHeaderView.addCostAmountCompany( payslipDetailView.getCostAmountCompany() );
            payslipHeaderView.addCostAmountPersonal( payslipDetailView.getCostAmountPersonal() );

            if ( KANUtil.filterEmpty( payslipHeaderView.getHeaderId() ) != null
                  && !KANUtil.filterEmpty( payslipHeaderView.getHeaderId() ).contains( "#" + payslipDetailView.getHeaderId() ) )
            {
               // ��˰���
               payslipHeaderView.addSalaryTax( payslipDetailView.getSalaryTax() );

               // ����˰�������
               payslipHeaderView.addTaxAgentAmountPersonal( payslipDetailView.getTaxAgentAmountPersonal() );

               payslipHeaderView.addAddtionalBillAmountPersonal( payslipDetailView.getAddtionalBillAmountPersonal() );

               // ��дHeaderId
               payslipHeaderView.setHeaderId( payslipHeaderView.getHeaderId() + "#" + payslipDetailView.getHeaderId() );
            }

            // ������籣���ͣ����㡰��˾���������ˡ��ϼ�
            if ( "7".equals( payslipDetailView.getItemType() ) )
            {
               payslipHeaderView.addSbAmountCompany( String.valueOf( Double.valueOf( payslipDetailView.getCostAmountCompany() ) ) );

               payslipHeaderView.addSbAmountPersonal( String.valueOf( Double.valueOf( payslipDetailView.getBillAmountPersonal() )
                     - Double.valueOf( payslipDetailView.getCostAmountPersonal() ) ) );
            }

            // ����Ӧ������
            payslipHeaderView.addSbAmount( getSbAmount( payslipDetailView ) );

            /** ����PayslipDetailView */
            // ��ʶpayslipDetailView�Ƿ����
            Boolean exist = false;

            for ( PayslipDetailView tempPayslipDetailView : payslipDTO.getPayslipDetailViews() )
            {
               // �����Ŀ�Ѵ���
               if ( tempPayslipDetailView != null && tempPayslipDetailView.getItemId() != null && tempPayslipDetailView.getItemId().equals( payslipDetailView.getItemId() ) )
               {
                  // �ö�Ӧ��tempPayslipDetailView����ϼ�ֵ
                  tempPayslipDetailView.addBillAmountCompany( payslipDetailView.getBillAmountCompany() );
                  tempPayslipDetailView.addBillAmountPersonal( payslipDetailView.getBillAmountPersonal() );
                  tempPayslipDetailView.addCostAmountCompany( payslipDetailView.getCostAmountCompany() );
                  tempPayslipDetailView.addCostAmountPersonal( payslipDetailView.getCostAmountPersonal() );

                  exist = true;
                  break;
               }
            }

            // �����Ŀ������
            if ( !exist )
            {
               // ֱ�����PayslipDetailView
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
    *	����PayslipDetailView����PayslipHeaderView
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
      // һ���˴�����ǩ������ʾ�����ͬ�ĺϼƣ�����Ҫ��ͬID
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

      // ��ʼ��
      payslipHeaderView.setSbAmountCompany( "0" );
      payslipHeaderView.setSbAmountPersonal( "0" );

      // ��ʼ����˾�͸��˵��籣�ϼ�
      if ( "7".equals( payslipDetailView.getItemType() ) )
      {
         payslipHeaderView.addSbAmountCompany( String.valueOf( Double.valueOf( payslipDetailView.getCostAmountCompany() ) ) );

         payslipHeaderView.addSbAmountPersonal( String.valueOf( Double.valueOf( payslipDetailView.getBillAmountPersonal() )
               - Double.valueOf( payslipDetailView.getCostAmountPersonal() ) ) );
      }

      // �����籣������ϼ�
      payslipHeaderView.setSbAmount( getSbAmount( payslipDetailView ) );

      // ��������ͬģ������
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

   // ����Ӧ������
   private String getSbAmount( final PayslipDetailView payslipDetailView ) throws KANException
   {
      final String itemId = payslipDetailView.getItemId();

      if ( KANConstants.getKANAccountConstants( payslipDetailView.getAccountId() ) != null )
      {
         final ItemVO itemVO = KANConstants.getKANAccountConstants( payslipDetailView.getAccountId() ).getItemVOByItemId( itemId );

         // �����Ŀ �������˰��Ϊ�Ǽ�ȥ����֧��
         if ( "1".equals( itemVO.getPersonalTax() ) )
         {
            //            return String.valueOf( Double.valueOf( payslipDetailView.getAddtionalBillAmountPersonal() ) - Double.valueOf( payslipDetailView.getCostAmountPersonal() ) );
            return String.valueOf( -Double.valueOf( payslipDetailView.getCostAmountPersonal() ) );
         }
         // �����Ŀ �������˰����Ϊ�ǲ�������֧��
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
    *	���ɰ������п�Ŀ�ļ���
    *	@param payslipDetailView
    *	@param itemVOs
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private void fetchItemVOs( final List< ItemVO > itemVOs, final PayslipDetailView payslipDetailView )
   {
      // �жϿ�Ŀ�Ƿ���ڿ�Ŀ�����У�������������
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

      // ��ʼ������ItemVO
      final ItemVO itemVO = new ItemVO();
      itemVO.setItemId( payslipDetailView.getItemId() );
      itemVO.setItemType( payslipDetailView.getItemType() );
      itemVO.setItemNo( payslipDetailView.getItemNo() );
      itemVOs.add( itemVO );
   }

   /**  
    * FetchItemVOs
    * 
    * �����һ�μ���ǿ����
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
            // ����ǵ�һ�ν���ǿ����
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
