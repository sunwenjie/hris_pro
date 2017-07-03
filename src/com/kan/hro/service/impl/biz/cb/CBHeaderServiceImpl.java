package com.kan.hro.service.impl.biz.cb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.domain.biz.cb.CBDTO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;

public class CBHeaderServiceImpl extends ContextService implements CBHeaderService
{
   // 注入CBDetailDao
   private CBDetailDao cbDetailDao;

   public CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   @Override
   public PagedListHolder getCBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CBHeaderDao cbHeaderDao = ( CBHeaderDao ) getDao();
      pagedListHolder.setHolderSize( cbHeaderDao.countCBHeaderVOsByCondition( ( CBHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( cbHeaderDao.getCBHeaderVOsByCondition( ( CBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cbHeaderDao.getCBHeaderVOsByCondition( ( CBHeaderVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object cbHeaderVOObject : pagedListHolder.getSource() )
         {
            final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            countAmount( cbHeaderVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * countAmount
    * 
    * @param cbHeaderVO
    * @throws KANException
    */
   private void countAmount( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      final List< Object > cbDetailVOs = this.cbDetailDao.getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

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
   public PagedListHolder getContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CBHeaderDao cbHeaderDao = ( CBHeaderDao ) getDao();
      pagedListHolder.setHolderSize( cbHeaderDao.countContractVOsByCondition( ( CBHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( cbHeaderDao.getContractVOsByCondition( ( CBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cbHeaderDao.getContractVOsByCondition( ( CBHeaderVO ) pagedListHolder.getObject() ) );
      }

      // 计算合计值
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object cbHeaderVOObject : pagedListHolder.getSource() )
         {
            final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            countContractAmount( cbHeaderVO );
         }
      }

      return pagedListHolder;
   }

   @Override
   public List< Object > getCBContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      final List< Object > cbContractHeaderVOs = ( ( CBHeaderDao ) getDao() ).getCBContractVOsByCondition( cbHeaderVO );

      if ( cbContractHeaderVOs != null && cbContractHeaderVOs.size() > 0 )
      {
         for ( Object cbHeaderVOObject : cbContractHeaderVOs )
         {
            CBHeaderVO cbContractHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            countContractAmount( cbContractHeaderVO );
         }
      }

      return cbContractHeaderVOs;
   }

   /**  
    * CountContractAmount
    * 
    * @param cbHeaderVO
    * @throws KANException
    */
   private void countContractAmount( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      // 初始化合计值
      BigDecimal amountSalesCost = new BigDecimal( 0 );
      BigDecimal amountSalesPrice = new BigDecimal( 0 );
      // 获得协议对应的CBHeaderVO 集合
      final List< Object > cbHeaderVOs = ( ( CBHeaderDao ) getDao() ).getCBHeaderVOsByCondition( cbHeaderVO );

      // 分别计算合计
      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         for ( Object tempCBHeaderVOObject : cbHeaderVOs )
         {
            CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) tempCBHeaderVOObject;
            countAmount( tempCBHeaderVO );

            amountSalesCost = amountSalesCost.add( new BigDecimal( tempCBHeaderVO.getAmountSalesCost() ) );
            amountSalesPrice = amountSalesPrice.add( new BigDecimal( tempCBHeaderVO.getAmountSalesPrice() ) );
            cbHeaderVO.setAmountSalesCost( amountSalesCost.toString() );
            cbHeaderVO.setAmountSalesPrice( amountSalesPrice.toString() );
         }
      }

   }

   @Override
   public CBHeaderVO getCBHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      final CBHeaderVO cbHeaderVO = ( ( CBHeaderDao ) getDao() ).getCBHeaderVOByHeaderId( headerId );
      countAmount( cbHeaderVO );
      return cbHeaderVO;
   }

   @Override
   public int updateCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return ( ( CBHeaderDao ) getDao() ).updateCBHeader( cbHeaderVO );
   }

   @Override
   public int insertCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return ( ( CBHeaderDao ) getDao() ).insertCBHeader( cbHeaderVO );
   }

   @Override
   public int deleteCBHeader( final String cbHeaderId ) throws KANException
   {
      return ( ( CBHeaderDao ) getDao() ).deleteCBHeader( cbHeaderId );
   }

   @Override
   public List< Object > getCBHeaderVOsByBatchId( final String cbBatchId ) throws KANException
   {
      final List< Object > cbHeaderVOs = ( ( CBHeaderDao ) getDao() ).getCBHeaderVOsByBatchId( cbBatchId );

      // 计算合计值
      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         for ( Object cbHeaderVOObject : cbHeaderVOs )
         {
            CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            countAmount( cbHeaderVO );
         }
      }

      return cbHeaderVOs;
   }

   @Override
   public List< Object > getCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      final List< Object > cbHeaderVOObjects = ( ( CBHeaderDao ) getDao() ).getCBHeaderVOsByCondition( cbHeaderVO );

      if ( cbHeaderVOObjects != null && cbHeaderVOObjects.size() > 0 )
      {
         for ( Object cbHeaderVOObject : cbHeaderVOObjects )
         {
            final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            countAmount( tempCBHeaderVO );
         }
      }

      return cbHeaderVOObjects;
   }

   @Override
   public List< CBDTO > getCBDTOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      // 初始化CBDTO List
      final List< CBDTO > cbDTOs = new ArrayList< CBDTO >();
      // 初始化CBHeaderVO List
      final List< Object > cbHeaderVOs = ( ( CBHeaderDao ) getDao() ).getCBHeaderVOsByCondition( cbHeaderVO );

      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         for ( Object cbHeaderVOObject : cbHeaderVOs )
         {
            // 初始化缓存对象
            final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            // 初始化CBDTO
            final CBDTO cbDTO = new CBDTO();

            // 装载CBHeaderVO
            cbDTO.setCbHeaderVO( tempCBHeaderVO );

            // 装载CBDetailVO List
            fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), cbHeaderVO.getStatus() );

            cbDTOs.add( cbDTO );
         }
      }

      return cbDTOs;
   }

   // 装载社保明细
   private void fetchCBDetail( final CBDTO cbDTO, final String headerId, final String status ) throws KANException
   {
      // 初始化并装载社保明细
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            final CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;

            // 只提取符合条件的商保明细
            if ( cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( status ) )
            {
               cbDTO.getCbDetailVOs().add( ( CBDetailVO ) cbDetailVO );
            }
         }
      }
   }

}
