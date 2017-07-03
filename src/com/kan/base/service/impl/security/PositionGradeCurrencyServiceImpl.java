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
         // ��������
         this.startTransaction();

         // ��ȡְ����ID
         final String positionGradeId = positionGradeCurrencyVO.getPositionGradeId();

         // ��ȡ��Ҫ�޸ĵ�currencyId
         final String currencyId = positionGradeCurrencyVO.getCurrencyId();

         // ͨ�� positionGradeId��ȡ���е�positionGradeId��Ӧ��positionGradeCurrencyVO(������ɾ����positionGradeCurrencyVO)
         final PositionGradeCurrencyVO positionGradeCurrencyVOTemp = new PositionGradeCurrencyVO();
         positionGradeCurrencyVOTemp.setAccountId( positionGradeCurrencyVO.getAccountId() );
         positionGradeCurrencyVOTemp.setPositionGradeId( positionGradeId );
         final List< Object > positionGradeCurrencyVOs = getAllPositionGradeCurrencyVOsByCondition( positionGradeCurrencyVOTemp );

         // �������ݿ��ӦPositionGradeCurrencyVOs�������Ƿ�����ж�����insert����update����
         if ( positionGradeCurrencyVOs.size() > 0 )
         {
            // ��positionGradeCurrencyVOs�л�ȡ�����е�currencyId������һ��������
            final List< String > currencyIdList = new ArrayList< String >();
            for ( Object positionGradeCurrencyObject : positionGradeCurrencyVOs )
            {
               PositionGradeCurrencyVO positionGradeCurrencyVOTemp1 = ( PositionGradeCurrencyVO ) positionGradeCurrencyObject;
               currencyIdList.add( positionGradeCurrencyVOTemp1.getCurrencyId() );
            }

            // �ж��Ƿ�����Ҫ�޸ĵ�currencyId
            if ( currencyIdList.contains( currencyId.trim() ) )
            {
               for ( Object positionGradeCurrencyObject : positionGradeCurrencyVOs )
               {
                  PositionGradeCurrencyVO positionGradeCurrencyVOTemp1 = ( PositionGradeCurrencyVO ) positionGradeCurrencyObject;
                  if ( currencyId.trim().equals( positionGradeCurrencyVOTemp1.getCurrencyId() ) )
                  {
                     // �½�һ��PositionGradeCurrencyVO ������ȡ�������ӦPositionGradeId ��CurrencyId��PositionGradeCurrencyVO
                     final PositionGradeCurrencyVO positionGradeCurrencyVOTemp2 = new PositionGradeCurrencyVO();
                     positionGradeCurrencyVOTemp2.setPositionGradeId( positionGradeId );
                     positionGradeCurrencyVOTemp2.setCurrencyId( currencyId );
                     // ��ȡ���ݿ������е�PositionGradeCurrencyVO
                     final PositionGradeCurrencyVO positionGradeCurrencyVOFromDba = getPositionGradeCurrencyVOByCondition( positionGradeCurrencyVOTemp2 );
                     
                     // ����positionGradeCurrencyVOΪ��ɾ��״̬
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

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
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
