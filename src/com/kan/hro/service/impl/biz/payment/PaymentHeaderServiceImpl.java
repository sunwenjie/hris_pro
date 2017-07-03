package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentHeaderService;

public class PaymentHeaderServiceImpl extends ContextService implements PaymentHeaderService
{
   private PaymentDetailDao paymentDetailDao;

   public PaymentDetailDao getPaymentDetailDao()
   {
      return paymentDetailDao;
   }

   public void setPaymentDetailDao( PaymentDetailDao paymentDetailDao )
   {
      this.paymentDetailDao = paymentDetailDao;
   }

   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   public PaymentAdjustmentHeaderDao getPaymentAdjustmentHeaderDao()
   {
      return paymentAdjustmentHeaderDao;
   }

   public void setPaymentAdjustmentHeaderDao( PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao )
   {
      this.paymentAdjustmentHeaderDao = paymentAdjustmentHeaderDao;
   }

   private PaymentAdjustmentDetailDao paymentAdjustmentDetailDao;

   public PaymentAdjustmentDetailDao getPaymentAdjustmentDetailDao()
   {
      return paymentAdjustmentDetailDao;
   }

   public void setPaymentAdjustmentDetailDao( PaymentAdjustmentDetailDao paymentAdjustmentDetailDao )
   {
      this.paymentAdjustmentDetailDao = paymentAdjustmentDetailDao;
   }

   @Override
   public PagedListHolder getPaymentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentHeaderDao paymentHeaderDao = ( PaymentHeaderDao ) getDao();
      pagedListHolder.setHolderSize( ( ( PaymentHeaderDao ) getDao() ).countPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( paymentHeaderDao.getPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( ( ( PaymentHeaderDao ) getDao() ).getPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object paymentHeaderVOObject : pagedListHolder.getSource() )
         {
            final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;
            amount( paymentHeaderVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * Sum Details to Header
    * 计算公司及个人数值合计(指定PaymentHeaderVO)
    * @param paymentHeaderVO
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-27
   private void amount( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      // 获得PaymentDetailVO列表
      final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( paymentHeaderVO.getPaymentHeaderId() );

      // 计算营收成本合计
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;

            paymentHeaderVO.addBillAmountCompany( paymentDetailVO.getBillAmountCompany() );
            paymentHeaderVO.addBillAmountPersonal( paymentDetailVO.getBillAmountPersonal() );
            paymentHeaderVO.addCostAmountCompany( paymentDetailVO.getCostAmountCompany() );
            paymentHeaderVO.addCostAmountPersonal( paymentDetailVO.getCostAmountPersonal() );
         }
      }
   }

   /**  
    * Get PaymentHeaderDTOs by Condition
    *	 
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-04-27
   public PagedListHolder getPaymentHeaderDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      // 初始化PaymentHeaderVO集合
      List< Object > paymentHeaderVOs = new ArrayList< Object >();

      // 设置符合条件的记录数
      pagedListHolder.setHolderSize( ( ( PaymentHeaderDao ) getDao() ).countPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject() ) );

      // Summary转成Json对象
      final JSONObject jsonObject = JSONObject.fromObject( ( ( PaymentHeaderDao ) getDao() ).summaryPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject() ) );

      // 设置Summary
      pagedListHolder.setSummarization( jsonObject != null ? jsonObject.toString() : "" );

      // 获取查询对象
      final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) pagedListHolder.getObject();
      final String status = paymentHeaderVO.getStatus();
      final List< MappingVO > items = new ArrayList< MappingVO >();

      if ( isPaged )
      {
         // 获得PaymentHeaderVO集合
         paymentHeaderVOs = ( ( PaymentHeaderDao ) getDao() ).getPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) );
      }
      else
      {
         // 获得PaymentHeaderVO集合
         paymentHeaderVOs = ( ( PaymentHeaderDao ) getDao() ).getPaymentHeaderVOsByCondition( ( PaymentHeaderVO ) pagedListHolder.getObject() );
      }

      // 装载PaymentDTO集合
      final List< Object > paymentDTOs = fetchPaymentDTOs( items, paymentHeaderVOs, status );

      pagedListHolder.setSource( paymentDTOs );

      // 科目先按类型再按ID排序
      Collections.sort( items, new Comparator< Object >()
      {
         @Override
         public int compare( Object o1, Object o2 )
         {
            final MappingVO m1 = ( MappingVO ) o1;
            final MappingVO m2 = ( MappingVO ) o2;

            if ( !m1.getMappingTemp().equals( m2.getMappingTemp() ) )
            {
               return Integer.valueOf( m1.getMappingTemp() ) - Integer.valueOf( m2.getMappingTemp() );
            }
            else
            {
               if ( !m1.getMappingId().equals( m2.getMappingId() ) )
               {
                  return Integer.valueOf( m1.getMappingId() ) - Integer.valueOf( m2.getMappingId() );
               }
               else
               {
                  return 0;
               }
            }
         }
      } );

      pagedListHolder.setAdditionalObject( items );

      return pagedListHolder;
   }

   /**
    * 
   * 	Fetch PaymentDTOs
   *	
   *	@param items
   *	@param paymentHeaderVOs
   *	@param status
   *	@return
   *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2014-04-27
   private List< Object > fetchPaymentDTOs( final List< MappingVO > items, final List< Object > paymentHeaderVOs, final String status ) throws KANException
   {
      // 初始化PaymentDTO列表
      final List< Object > paymentDTOs = new ArrayList< Object >();

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

            // 初始化PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentHeaderVO( paymentHeaderVO );

            // 初始化查询对象
            final PaymentDetailVO searchPaymentDetailVO = new PaymentDetailVO();
            searchPaymentDetailVO.setPaymentHeaderId( paymentHeaderVO.getPaymentHeaderId() );
            searchPaymentDetailVO.setStatus( status );
            final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByCondition( searchPaymentDetailVO );

            if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
            {
               for ( Object paymentDetailVOObject : paymentDetailVOs )
               {
                  // 初始化PaymentDetailVO
                  final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
                  
                  final ItemVO itemVO = KANConstants.getKANAccountConstants( paymentHeaderVO.getAccountId() ).getItemVOByItemId( paymentDetailVO.getItemId() );

                  if ( Double.valueOf( paymentDetailVO.getBillAmountCompany() ) != 0 || Double.valueOf( paymentDetailVO.getBillAmountPersonal() ) != 0
                        || Double.valueOf( paymentDetailVO.getCostAmountCompany() ) != 0 || Double.valueOf( paymentDetailVO.getCostAmountPersonal() ) != 0||itemVO!=null&&"13".equals( itemVO.getItemType() ) )
                  {
                     /** 获取PaymentDetailVO - 按照ItemId */
                     loadPaymentDTO( paymentDTO.getPaymentDetailVOs(), paymentDetailVO );

                     /** 填充科目全集 */
                     final MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( paymentDetailVO.getItemId() );
                     mappingVO.setMappingValue( paymentDetailVO.getNameZH() );
                     mappingVO.setMappingStatus( paymentDetailVO.getNameEN() );
                     // TODO 暂时科目英文放这里 siuvan added
                     mappingVO.setMappingTemp( paymentDetailVO.getItemType() );
                     addItem( items, mappingVO );
                  }
               }
            }

            paymentDTOs.add( paymentDTO );
         }
      }

      return paymentDTOs;
   }

   /**
    * 
   * 	Add Item
   *	
   *	@param items
   *	@param mappingVO
    */
   // Reviewed by Kevin Jin at 2014-04-27
   private void addItem( final List< MappingVO > items, final MappingVO mappingVO )
   {
      boolean exist = false;

      for ( MappingVO tempMappingVO : items )
      {
         if ( tempMappingVO.getMappingId().equals( mappingVO.getMappingId() ) )
         {
            exist = true;

            break;
         }
      }

      if ( !exist )
      {
         items.add( mappingVO );
      }
   }

   /**
    * 
   * 	Load PaymentDTO
   *	
   *	@param paymentDetailVOs
   *	@param paymentDetailVO
   *	@return
    */
   // Reviewed by Kevin Jin at 2014-04-27
   private PaymentDetailVO loadPaymentDTO( final List< PaymentDetailVO > paymentDetailVOs, final PaymentDetailVO paymentDetailVO )
   {
      // 遍历PaymentDetailVO列表
      for ( PaymentDetailVO tempPaymentDetailVO : paymentDetailVOs )
      {
         if ( KANUtil.filterEmpty( tempPaymentDetailVO.getItemId() ) != null && KANUtil.filterEmpty( tempPaymentDetailVO.getItemId() ).equals( paymentDetailVO.getItemId() ) )
         {
            // Amount累加 - Detail 
            tempPaymentDetailVO.addBillAmountCompany( String.valueOf( paymentDetailVO.getBillAmountCompany() ) );
            tempPaymentDetailVO.addBillAmountPersonal( String.valueOf( paymentDetailVO.getBillAmountPersonal() ) );
            tempPaymentDetailVO.addCostAmountCompany( String.valueOf( paymentDetailVO.getCostAmountCompany() ) );
            tempPaymentDetailVO.addCostAmountPersonal( String.valueOf( paymentDetailVO.getCostAmountPersonal() ) );

            return tempPaymentDetailVO;
         }
      }

      // 初始化PaymentDetailVO - 未找到
      paymentDetailVOs.add( paymentDetailVO );

      return paymentDetailVO;
   }

   @Override
   public PaymentHeaderVO getPaymentHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      final PaymentHeaderVO paymentHeaderVO = ( ( PaymentHeaderDao ) getDao() ).getPaymentHeaderVOByHeaderId( headerId );

      if ( paymentHeaderVO != null )
      {
         amount( paymentHeaderVO );
      }

      return paymentHeaderVO;
   }

   @Override
   public int updatePaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( ( PaymentHeaderDao ) getDao() ).updatePaymentHeader( paymentHeaderVO );
   }

   @Override
   public int insertPaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( ( PaymentHeaderDao ) getDao() ).insertPaymentHeader( paymentHeaderVO );
   }

   @Override
   public int deletePaymentHeader( final String paymentHeaderId ) throws KANException
   {
      return ( ( PaymentHeaderDao ) getDao() ).deletePaymentHeader( paymentHeaderId );
   }

   @Override
   public List< Object > getPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( ( PaymentHeaderDao ) getDao() ).getPaymentHeaderVOsByCondition( paymentHeaderVO );
   }

   @Override
   public List< Object > getMonthliesByPaymentHeaderVO( PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return ( ( PaymentHeaderDao ) getDao() ).getMonthliesByPaymentHeaderVO( paymentHeaderVO );
   }
}
