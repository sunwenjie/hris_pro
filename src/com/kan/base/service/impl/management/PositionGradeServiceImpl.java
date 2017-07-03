package com.kan.base.service.impl.management;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.PositionGradeCurrencyDao;
import com.kan.base.dao.inf.management.PositionGradeDao;
import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPPositionGradeService;
import com.kan.base.service.inf.management.PositionGradeService;
import com.kan.base.util.KANException;

public class PositionGradeServiceImpl extends ContextService implements PositionGradeService,CPPositionGradeService
{
   private PositionGradeCurrencyDao positionGradeCurrencyDao;

   public PositionGradeCurrencyDao getPositionGradeCurrencyDao()
   {
      return positionGradeCurrencyDao;
   }

   public void setPositionGradeCurrencyDao( PositionGradeCurrencyDao positionGradeCurrencyDao )
   {
      this.positionGradeCurrencyDao = positionGradeCurrencyDao;
   }

   @Override
   public PagedListHolder getPositionGradeVOByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final PositionGradeDao positionGradeDao = ( PositionGradeDao ) getDao();
      pagedListHolder.setHolderSize( positionGradeDao.countPositionGradeVOByCondition( ( PositionGradeVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( positionGradeDao.getPositionGradeVOByCondition( ( PositionGradeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( positionGradeDao.getPositionGradeVOByCondition( ( PositionGradeVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;

   }

   @Override
   public PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException
   {

      return ( ( PositionGradeDao ) getDao() ).getPositionGradeVOByPositionGradeId( positionGardeId );
   }

   @Override
   public PositionGradeVO getPositionGradeVOByGradeName( final String gradeName ) throws KANException
   {
      return ( ( PositionGradeDao ) getDao() ).getPositionGradeVOByGradeName( gradeName );
   }

   @Override
   public void deletePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      try
      {
         if ( positionGradeVO != null && positionGradeVO.getPositionGradeId() != null && !( "" ).equals( positionGradeVO.getPositionGradeId().trim() ) )
         {
            // 开启事务
            startTransaction();

            // 设置positionGradeVO的deleted值
            positionGradeVO.setDeleted( PositionGradeVO.FALSE );
            // 标记删除SearchHeaderVO
            ( ( PositionGradeDao ) getDao() ).updatePositionGrade( positionGradeVO );

            // 根据positionGradeVO删除positionGradeVO对应的positionGradeCurrencyVO
            deletePositionGradeCurrencyVOsByPositionGradeVO( positionGradeVO );

            // 提交事务 
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   public void deletePositionGradeCurrencyVOsByPositionGradeVO( final PositionGradeVO positionGradeVO ) throws KANException
   {
      // 获得positionGradeVO的id值
      final String positionGradeId = positionGradeVO.getPositionGradeId();
      // 实例化PositionGradeCurrency
      PositionGradeCurrencyVO positionGradeCurrencyVO = new PositionGradeCurrencyVO();
      // 设置positionGradeCurrencyVO的相关属性值
      positionGradeCurrencyVO.setAccountId( positionGradeVO.getAccountId() );
      positionGradeCurrencyVO.setPositionGradeId( positionGradeId );

      // 根据positionGradeCurrencyId获得PositionGradeCurrency集合
      List< Object > ObjectPositionGradeCurrencyVOList = this.positionGradeCurrencyDao.getPositionGradeCurrencyVOsByCondition( positionGradeCurrencyVO );
      // 如果List不为空
      if ( ObjectPositionGradeCurrencyVOList != null && ObjectPositionGradeCurrencyVOList.size() > 0 )
      {
         // 遍历ObjectPositionGradeCurrencyVOList
         for ( Object objectPositionGradeCurrencyVO : ObjectPositionGradeCurrencyVOList )
         {
            final PositionGradeCurrencyVO positionGradeCurrencyVOForDel = ( PositionGradeCurrencyVO ) objectPositionGradeCurrencyVO;
            positionGradeCurrencyVOForDel.setModifyBy( positionGradeVO.getModifyBy() );
            positionGradeCurrencyVOForDel.setModifyDate( new Date() );
            positionGradeCurrencyVOForDel.setDeleted( PositionGradeCurrencyVO.FALSE );
            // 标记删除该positionGradeCurrencyVO
            this.positionGradeCurrencyDao.updatePositionGradeCurrency( positionGradeCurrencyVOForDel );
         }
      }
   }

   @Override
   public int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {

      return ( ( PositionGradeDao ) getDao() ).updatePositionGrade( positionGradeVO );
   }

   @Override
   public int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return ( ( PositionGradeDao ) getDao() ).insertPositionGrade( positionGradeVO );
   }
   
   @Override
   public List<Object> getPositionGradeVOsByAccountId( final String accountId ) throws KANException
   {

      return ( ( PositionGradeDao ) getDao() ).getPositionGradeVOsByAccountId( accountId );
   }

}
