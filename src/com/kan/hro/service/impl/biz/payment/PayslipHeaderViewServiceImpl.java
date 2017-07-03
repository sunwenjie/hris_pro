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
    *	 ���ɹ��ʸ�˰�ϼ� - �¶�
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-05-04
   public PagedListHolder getPayslipTaxDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      // ��ʼ������Դ
      List< Object > sources = new ArrayList< Object >();

      // ����ǵ���
      if ( ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSubAction() != null
            && ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSubAction().equals( BaseAction.DOWNLOAD_OBJECTS ) )
      {
         // ����״̬Ϊ��-���ɵ������ύ���͡��ѷ��š�����
         ( ( PayslipHeaderView ) pagedListHolder.getObject() ).setStatus( "" );
         final List< Object > payslipHeaderViewObjects = ( ( PayslipHeaderViewDao ) getDao() ).getPayslipHeaderViewsByCondition( ( PayslipHeaderView ) pagedListHolder.getObject() );
         final List< Object > payslipTaxDTOs = fetchPayslipTaxDTOs( payslipHeaderViewObjects );
         sources.addAll( payslipTaxDTOs );
      }
      else
      {
         // �������Դ��Ϊ�գ�Ĭ��ȡ֮
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

      // �״β�ѯ����˰��������
      if ( ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSortColumn() == null || ( ( PayslipHeaderView ) pagedListHolder.getObject() ).getSortColumn().isEmpty() )
      {
         if ( sources != null && sources.size() > 0 )
         {
            // �������򣺸�˰�������
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

      // ����˰��Ϊ��0������ʾ
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
      // ��������Դ
      pagedListHolder.setSource( new ArrayList< Object >() );

      // �ֶ���ҳ
      if ( isPaged )
      {
         // װ������
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
    *	���ɸ�˰�ϼ�DTO
    *	@param payslipHeaderViewObjects
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private List< Object > fetchPayslipTaxDTOs( final List< Object > payslipHeaderViewObjects ) throws KANException
   {
      // ��ʼ������ֵ����
      final List< Object > payslipTaxDTOs = new ArrayList< Object >();

      // ���ڹ��ʵ��б�
      if ( payslipHeaderViewObjects != null && payslipHeaderViewObjects.size() > 0 )
      {
         // ��������PayslipTaxDTO �б�
         for ( Object payslipHeaderViewObject : payslipHeaderViewObjects )
         {
            // ��ʼ��PayslipHeaderView
            final PayslipHeaderView payslipHeaderView = ( PayslipHeaderView ) payslipHeaderViewObject;

            // װ��PayslipTaxDTO
            fetchPayslipTaxDTO( payslipTaxDTOs, payslipHeaderView );
         }
      }

      return payslipTaxDTOs;
   }

   /**  
    * Fetch PayslipTaxDTO
    *	��˰�ϼ�DTO��������
    *	@param payslipDetailView
    *	@param payslipTaxDTOs
    * @throws KANException 
    */
   // Reviewed by Kevin Jin at 2014-05-04
   private void fetchPayslipTaxDTO( final List< Object > payslipTaxDTOs, final PayslipHeaderView payslipHeaderView ) throws KANException
   {
      // ��ʼ��������ʾ
      boolean exist = false;

      // PayslipTaxDTO ��������
      if ( payslipTaxDTOs != null && payslipTaxDTOs.size() > 0 )
      {
         // ����
         for ( Object payslipTaxDTOObject : payslipTaxDTOs )
         {
            // ��ʼ��PayslipTaxDTO
            final PayslipTaxDTO payslipTaxDTO = ( PayslipTaxDTO ) payslipTaxDTOObject;

            // ��ʼ��PayslipTaxHeaderView
            final PayslipTaxHeaderView payslipTaxHeaderView = payslipTaxDTO.getPayslipTaxHeaderView();

            // �Ƚ�EmployeeId��Monthly�� Status�� Entity
            if ( payslipTaxHeaderView.getEmployeeId().equals( payslipHeaderView.getEmployeeId() ) && payslipTaxHeaderView.getMonthly().equals( payslipHeaderView.getMonthly() )
                  && payslipTaxHeaderView.getStatus().equals( payslipHeaderView.getStatus() ) && payslipTaxHeaderView.getEntityId().equals( payslipHeaderView.getEntityId() ) )
            {
               if ( payslipHeaderView.getItemGroupId() != null && payslipHeaderView.getItemGroupId().equals( INDEPENDENT_CALCULATE_TIEMGROUP ) )
               {
                  if ( payslipTaxHeaderView.getItemGroupId() != null && payslipTaxHeaderView.getItemGroupId().equals( payslipHeaderView.getItemGroupId() ) )
                  {
                     // �ۼ�
                     payslipTaxHeaderView.addBillAmountCompany( payslipHeaderView.getBillAmountPersonal() );
                     payslipTaxHeaderView.addBillAmountPersonal( payslipHeaderView.getBillAmountCompany() );
                     payslipTaxHeaderView.addCostAmountCompany( payslipHeaderView.getCostAmountCompany() );
                     payslipTaxHeaderView.addCostAmountPersonal( payslipHeaderView.getCostAmountPersonal() );
                     payslipTaxHeaderView.addAddtionalBillAmountPersonal( payslipHeaderView.getAddtionalBillAmountPersonal() );
                     payslipTaxHeaderView.addTaxAgentAmountPersonal( payslipHeaderView.getTaxAgentAmountPersonal() );

                     // �����˰
                     payslipTaxHeaderView.addSalaryTax( payslipHeaderView.getSalaryTax() );

                     exist = true;
                     break;
                  }
                  else
                     continue;
               }
               // �ۼ�
               payslipTaxHeaderView.addBillAmountCompany( payslipHeaderView.getBillAmountPersonal() );
               payslipTaxHeaderView.addBillAmountPersonal( payslipHeaderView.getBillAmountCompany() );
               payslipTaxHeaderView.addCostAmountCompany( payslipHeaderView.getCostAmountCompany() );
               payslipTaxHeaderView.addCostAmountPersonal( payslipHeaderView.getCostAmountPersonal() );
               payslipTaxHeaderView.addAddtionalBillAmountPersonal( payslipHeaderView.getAddtionalBillAmountPersonal() );
               payslipTaxHeaderView.addTaxAgentAmountPersonal( payslipHeaderView.getTaxAgentAmountPersonal() );

               // �����˰
               payslipTaxHeaderView.addSalaryTax( payslipHeaderView.getSalaryTax() );

               exist = true;
               break;
            }
         }
      }

      // ���������
      if ( !exist )
      {
         // ʵ����PayslipTaxDTO
         final PayslipTaxDTO payslipTaxDTO = new PayslipTaxDTO();

         // ��ʼ��PayslipTaxHeaderView
         final PayslipTaxHeaderView payslipTaxHeaderView = getPayslipTaxHeaderView( payslipHeaderView );
         payslipTaxDTO.setPayslipTaxHeaderView( payslipTaxHeaderView );

         payslipTaxDTOs.add( payslipTaxDTO );
      }

   }

   /**  
    * GetPayslipTaxHeaderView
    *	����PayslipDetailView ����PayslipTaxHeaderView
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
