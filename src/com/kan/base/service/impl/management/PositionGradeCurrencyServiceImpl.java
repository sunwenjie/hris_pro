package com.kan.base.service.impl.management;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.PositionGradeCurrencyDao;
import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPPositionGradeCurrencyService;
import com.kan.base.service.inf.management.PositionGradeCurrencyService;
import com.kan.base.util.KANException;

public class PositionGradeCurrencyServiceImpl extends ContextService implements PositionGradeCurrencyService,CPPositionGradeCurrencyService
{

   @Override
   public PagedListHolder getPositionGradeCurrencyVOsByCondition( final PagedListHolder pagedListGradeCurrencyHolder, final boolean isPaged ) throws KANException
   {

      final PositionGradeCurrencyDao positionGradeCurrencyDao = ( PositionGradeCurrencyDao ) getDao();
      pagedListGradeCurrencyHolder.setHolderSize( positionGradeCurrencyDao.countPositionGradeCurrencyVOByCondition( ( PositionGradeCurrencyVO ) pagedListGradeCurrencyHolder.getObject() ) );
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
   public PositionGradeCurrencyVO getPositionGradeCurrencyVOByCurrencyId( final String currencyId ) throws KANException
   {
      return ( ( PositionGradeCurrencyDao ) getDao() ).getPositionGradeCurrencyVOByCurrencyId( currencyId );
   }

   @Override
   public int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      /**
       * 
      try
      {
         // 开启事务
         startTransaction();
         //  获取positionGradeCurrencyVO对应的positionGradeId
         final String positionGradeId = positionGradeCurrencyVO.getPositionGradeId();
         // 根据positionGradeId获取所有的positionGradeCurrencyVO
         final List< Object > positionGradeCurrencyVOlist = ( ( PositionGradeCurrencyDao ) getDao() ).getAllSearchDetailVOsByPositionGradeId( positionGradeId );
         // 声明一个flag验证保存添加的positionGradeCurrencyVO是否已存在
         Boolean containFlag = true;
         // 遍历searchDetailDaolist查找是否包含要添加的searchDetailVO对应的columnId是否已存在
         for ( Object objectPositionGradeCurrencyVO : positionGradeCurrencyVOlist )
         {
            final PositionGradeCurrencyVO positionGradeCurrencyVOTemp = ( PositionGradeCurrencyVO ) objectPositionGradeCurrencyVO;
            // 如果要添加的positionGradeCurrencyVO已存在
            if ( positionGradeCurrencyVO.getCurrencyId().trim().equals( positionGradeCurrencyVOTemp.getCurrencyId().trim() ) )
            {
               positionGradeCurrencyVOTemp.update( positionGradeCurrencyVO );
               positionGradeCurrencyVOTemp.setDeleted( PositionGradeCurrencyVO.TRUE );
               // update positionGradeCurrencyVO
               ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrency( positionGradeCurrencyVOTemp );
               // 修改containFlag值
               containFlag = false;
               break;
            }
         }
         // 如果要添加的positionGradeCurrencyVO不存在
         if ( containFlag )
         {
            // insert positionGradeCurrencyVO
            ( ( PositionGradeCurrencyDao ) getDao() ).insertPositionGradeCurrency( positionGradeCurrencyVO );
         }
         // 提交事务 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
      */
      return ( ( PositionGradeCurrencyDao ) getDao() ).insertPositionGradeCurrency( positionGradeCurrencyVO );
   }

   @Override
   public void deletePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      // 标记删除positionGradeCurrencyVO
      positionGradeCurrencyVO.setDeleted( PositionGradeCurrencyVO.FALSE );
      ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrency( positionGradeCurrencyVO );
   }

   @Override
   public void updatePositionGradeCurrency( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrency( positionGradeCurrencyVO );

   }

}
