package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.PositionGradeCurrencyDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.security.PositionGradeCurrencyVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.PositionGradeCurrencyService;
import com.kan.base.util.KANException;

public class PositionGradeCurrencyServiceImpl extends ContextService implements PositionGradeCurrencyService
{

   @Override
   public PagedListHolder getPositionGradeCurrencyVOByCondition( final PagedListHolder pagedListGradeCurrencyHolder, final boolean isPaged ) throws KANException
   {

      final PositionGradeCurrencyDao positionGradeCurrencyDao = ( PositionGradeCurrencyDao ) getDao();
      pagedListGradeCurrencyHolder.setHolderSize( positionGradeCurrencyDao.countPositionGradeCurrencyVOsByCondition( ( PositionGradeCurrencyVO ) pagedListGradeCurrencyHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListGradeCurrencyHolder.setSource( positionGradeCurrencyDao.getPositionGradeCurrencyVOsByCondition( ( PositionGradeCurrencyVO ) pagedListGradeCurrencyHolder.getObject(), new RowBounds( pagedListGradeCurrencyHolder.getPage()
               * pagedListGradeCurrencyHolder.getPageSize() + 1, pagedListGradeCurrencyHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListGradeCurrencyHolder.setSource( positionGradeCurrencyDao.getPositionGradeCurrencyVOsByCondition( ( PositionGradeCurrencyVO ) pagedListGradeCurrencyHolder.getObject() ) );
      }

      return pagedListGradeCurrencyHolder;
   }

   @Override
   public PositionGradeCurrencyVO getPositionGradeCurrencyVOByPositionGradeIdAndCurrencyId( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return ( ( PositionGradeCurrencyDao ) getDao() ).getPositionGradeCurrencyVOByPositionGradeIdAndCurrencyId( positionGradeCurrencyVO );
   }

   @Override
   public int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return ( ( PositionGradeCurrencyDao ) getDao() ).insertPositionGradeCurrency( positionGradeCurrencyVO );
   }

   @Override
   public void deletePositionGradeCurrencyByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      ( ( PositionGradeCurrencyDao ) getDao() ).deletePositionGradeCurrencyByCondition( positionGradeCurrencyVO );
   }

   @Override
   public void updatePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 获取职级的ID
         final String positionGradeId = positionGradeCurrencyVO.getPositionGradeId();

         // 获取需要修改的currencyId
         final String currencyId = positionGradeCurrencyVO.getCurrencyId();

         // 通过 positionGradeId获取所有的positionGradeId对应的positionGradeCurrencyVO(包含已删除的positionGradeCurrencyVO)
         final PositionGradeCurrencyVO positionGradeCurrencyVOTemp = new PositionGradeCurrencyVO();
         positionGradeCurrencyVOTemp.setAccountId( positionGradeCurrencyVO.getAccountId() );
         positionGradeCurrencyVOTemp.setPositionGradeId( positionGradeId );
         final List< Object > positionGradeCurrencyVOs = getAllPositionGradeCurrencyVOsByCondition( positionGradeCurrencyVOTemp );

         // 遍历数据库对应PositionGradeCurrencyVOs，根据是否包含判断再做insert或者update动作
         if ( positionGradeCurrencyVOs.size() > 0 )
         {
            // 从positionGradeCurrencyVOs中获取是所有的currencyId并放在一个数组里
            final List< String > currencyIdList = new ArrayList< String >();
            for ( Object positionGradeCurrencyObject : positionGradeCurrencyVOs )
            {
               PositionGradeCurrencyVO positionGradeCurrencyVOTemp1 = ( PositionGradeCurrencyVO ) positionGradeCurrencyObject;
               currencyIdList.add( positionGradeCurrencyVOTemp1.getCurrencyId() );
            }

            // 判断是否含有需要修改的currencyId
            if ( currencyIdList.contains( currencyId.trim() ) )
            {
               for ( Object positionGradeCurrencyObject : positionGradeCurrencyVOs )
               {
                  PositionGradeCurrencyVO positionGradeCurrencyVOTemp1 = ( PositionGradeCurrencyVO ) positionGradeCurrencyObject;
                  if ( currencyId.trim().equals( positionGradeCurrencyVOTemp1.getCurrencyId() ) )
                  {
                     // 新建一个PositionGradeCurrencyVO 用来获取数据里对应PositionGradeId 和CurrencyId的PositionGradeCurrencyVO
                     final PositionGradeCurrencyVO positionGradeCurrencyVOTemp2 = new PositionGradeCurrencyVO();
                     positionGradeCurrencyVOTemp2.setPositionGradeId( positionGradeId );
                     positionGradeCurrencyVOTemp2.setCurrencyId( currencyId );
                     // 获取数据库中已有的PositionGradeCurrencyVO
                     final PositionGradeCurrencyVO positionGradeCurrencyVOFromDba = getPositionGradeCurrencyVOByCondition( positionGradeCurrencyVOTemp2 );
                     
                     // 设置positionGradeCurrencyVO为非删除状态
                     positionGradeCurrencyVOFromDba.setDeleted( BaseVO.TRUE );
                     positionGradeCurrencyVOFromDba.update( positionGradeCurrencyVO );
                     positionGradeCurrencyVOFromDba.setModifyBy( positionGradeCurrencyVO.getModifyBy() );
                     ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrencyByCondition( positionGradeCurrencyVOFromDba );
                     break;
                  }
               }
            }
            else
            {
               positionGradeCurrencyVO.setCreateBy( positionGradeCurrencyVO.getModifyBy() );
               positionGradeCurrencyVO.setModifyBy( positionGradeCurrencyVO.getModifyBy() );
               insertPositionGradeCurrency( positionGradeCurrencyVO );
            }
         }
         else
         {
            positionGradeCurrencyVO.setCreateBy( positionGradeCurrencyVO.getModifyBy() );
            positionGradeCurrencyVO.setModifyBy( positionGradeCurrencyVO.getModifyBy() );
            insertPositionGradeCurrency( positionGradeCurrencyVO );
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public PositionGradeCurrencyVO getPositionGradeCurrencyVOByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      return ( ( PositionGradeCurrencyDao ) getDao() ).getPositionGradeCurrencyVOByCondition( positionGradeCurrencyVO );
   }

   @Override
   public List< Object > getPositionGradeCurrencyVOsByCondition( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      return ( ( PositionGradeCurrencyDao ) getDao() ).getPositionGradeCurrencyVOsByCondition( positionGradeCurrencyVO );
   }
   
   @Override
   public List< Object > getAllPositionGradeCurrencyVOsByCondition( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      return ( ( PositionGradeCurrencyDao ) getDao() ).getAllPositionGradeCurrencyVOsByCondition( positionGradeCurrencyVO );
   }

}
