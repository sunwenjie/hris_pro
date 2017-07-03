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
         // ��������
         startTransaction();
         //  ��ȡpositionGradeCurrencyVO��Ӧ��positionGradeId
         final String positionGradeId = positionGradeCurrencyVO.getPositionGradeId();
         // ����positionGradeId��ȡ���е�positionGradeCurrencyVO
         final List< Object > positionGradeCurrencyVOlist = ( ( PositionGradeCurrencyDao ) getDao() ).getAllSearchDetailVOsByPositionGradeId( positionGradeId );
         // ����һ��flag��֤������ӵ�positionGradeCurrencyVO�Ƿ��Ѵ���
         Boolean containFlag = true;
         // ����searchDetailDaolist�����Ƿ����Ҫ��ӵ�searchDetailVO��Ӧ��columnId�Ƿ��Ѵ���
         for ( Object objectPositionGradeCurrencyVO : positionGradeCurrencyVOlist )
         {
            final PositionGradeCurrencyVO positionGradeCurrencyVOTemp = ( PositionGradeCurrencyVO ) objectPositionGradeCurrencyVO;
            // ���Ҫ��ӵ�positionGradeCurrencyVO�Ѵ���
            if ( positionGradeCurrencyVO.getCurrencyId().trim().equals( positionGradeCurrencyVOTemp.getCurrencyId().trim() ) )
            {
               positionGradeCurrencyVOTemp.update( positionGradeCurrencyVO );
               positionGradeCurrencyVOTemp.setDeleted( PositionGradeCurrencyVO.TRUE );
               // update positionGradeCurrencyVO
               ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrency( positionGradeCurrencyVOTemp );
               // �޸�containFlagֵ
               containFlag = false;
               break;
            }
         }
         // ���Ҫ��ӵ�positionGradeCurrencyVO������
         if ( containFlag )
         {
            // insert positionGradeCurrencyVO
            ( ( PositionGradeCurrencyDao ) getDao() ).insertPositionGradeCurrency( positionGradeCurrencyVO );
         }
         // �ύ���� 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      */
      return ( ( PositionGradeCurrencyDao ) getDao() ).insertPositionGradeCurrency( positionGradeCurrencyVO );
   }

   @Override
   public void deletePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      // ���ɾ��positionGradeCurrencyVO
      positionGradeCurrencyVO.setDeleted( PositionGradeCurrencyVO.FALSE );
      ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrency( positionGradeCurrencyVO );
   }

   @Override
   public void updatePositionGradeCurrency( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      ( ( PositionGradeCurrencyDao ) getDao() ).updatePositionGradeCurrency( positionGradeCurrencyVO );

   }

}
