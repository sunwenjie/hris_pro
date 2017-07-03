package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.OptionDetailDao;
import com.kan.base.dao.inf.define.OptionHeaderDao;
import com.kan.base.domain.define.OptionDTO;
import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.OptionHeaderService;
import com.kan.base.util.KANException;

public class OptionHeaderServiceImpl extends ContextService implements OptionHeaderService
{

   private OptionDetailDao optionDetailDao;

   public OptionDetailDao getOptionDetailDao()
   {
      return optionDetailDao;
   }

   public void setOptionDetailDao( OptionDetailDao optionDetailDao )
   {
      this.optionDetailDao = optionDetailDao;
   }

   @Override
   public PagedListHolder getOptionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OptionHeaderDao optionHeaderDao = ( OptionHeaderDao ) getDao();
      pagedListHolder.setHolderSize( optionHeaderDao.countOptionHeaderVOsByCondition( ( OptionHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( optionHeaderDao.getOptionHeaderVOsByCondition( ( OptionHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( optionHeaderDao.getOptionHeaderVOsByCondition( ( OptionHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public OptionHeaderVO getOptionHeaderVOByOptionHeaderId( final String optionHeaderId ) throws KANException
   {
      return ( ( OptionHeaderDao ) getDao() ).getOptionHeaderVOByOptionHeaderId( optionHeaderId );
   }

   @Override
   // Modified by Kevin Jin at 2014-03-06
   public int insertOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         rows = ( ( OptionHeaderDao ) getDao() ).insertOptionHeader( optionHeaderVO );

         // ����Ĭ��ѡ��
         final OptionDetailVO optionDetailVO = new OptionDetailVO();
         optionDetailVO.setOptionHeaderId( optionHeaderVO.getOptionHeaderId() );
         optionDetailVO.setOptionId( "0" );
         optionDetailVO.setOptionNameZH( "��ѡ��" );
         optionDetailVO.setOptionNameEN( "Please Select" );
         optionDetailVO.setOptionValue( "0" );
         optionDetailVO.setStatus( OptionDetailVO.TRUE );
         optionDetailVO.setCreateBy( optionHeaderVO.getCreateBy() );
         optionDetailVO.setCreateDate( optionHeaderVO.getCreateDate() );
         optionDetailVO.setModifyBy( optionHeaderVO.getModifyBy() );
         optionDetailVO.setModifyDate( optionHeaderVO.getModifyDate() );
         this.getOptionDetailDao().insertOptionDetail( optionDetailVO );

         // �ύ���� 
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   public int updateOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      return ( ( OptionHeaderDao ) getDao() ).updateOptionHeader( optionHeaderVO );
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public int deleteOptionHeader( final OptionHeaderVO optionHeaderVO ) throws KANException
   {
      try
      {
         // ���ɾ��Option Headerͬʱ����Ҫ���ɾ����֮��������Option Detail
         if ( optionHeaderVO != null && optionHeaderVO.getOptionHeaderId() != null && !optionHeaderVO.getOptionHeaderId().trim().equals( "" ) )
         {
            // ��������
            startTransaction();

            final OptionDetailVO optionDetailVO = new OptionDetailVO();
            optionDetailVO.setOptionHeaderId( optionHeaderVO.getOptionHeaderId() );

            // �ȱ��ɾ��Option Detail
            for ( Object objectOptionDetailVO : this.optionDetailDao.getOptionDetailVOsByCondition( optionDetailVO ) )
            {
               ( ( OptionDetailVO ) objectOptionDetailVO ).setDeleted( OptionDetailVO.FALSE );
               ( ( OptionDetailVO ) objectOptionDetailVO ).setModifyBy( optionHeaderVO.getModifyBy() );
               ( ( OptionDetailVO ) objectOptionDetailVO ).setModifyDate( optionHeaderVO.getModifyDate() );
               this.optionDetailDao.updateOptionDetail( ( ( OptionDetailVO ) objectOptionDetailVO ) );
            }

            // �����ɾ��Option Header
            optionHeaderVO.setDeleted( OptionHeaderVO.FALSE );
            ( ( OptionHeaderDao ) getDao() ).updateOptionHeader( optionHeaderVO );

            // �ύ���� 
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< OptionDTO > getOptionDTOsByAccountId( final String accountId ) throws KANException
   {
      // ����AccountId��ȡ��Ч��ѡ��
      final List< Object > optionHeaderVOs = ( ( OptionHeaderDao ) getDao() ).getOptionHeaderVOsByAccountId( accountId );
      final List< OptionDTO > optionDTOs = new ArrayList< OptionDTO >();

      // �������ѡ��
      if ( optionHeaderVOs != null && optionHeaderVOs.size() > 0 )
      {
         for ( Object optionHeaderVOObject : optionHeaderVOs )
         {
            // ��ʼ��OptionDTO����
            final OptionDTO optionDTO = new OptionDTO();

            final OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) optionHeaderVOObject;
            optionDTO.setOptionHeaderVO( ( OptionHeaderVO ) optionHeaderVOObject );

            // ����OptionHeaderId�����Ч��ѡ��ֵ
            final List< Object > optionDetailVOs = this.optionDetailDao.getOptionDetailVOsByOptionHeaderId( optionHeaderVO.getOptionHeaderId() );

            // �������ѡ��ֵ
            if ( optionHeaderVOs != null && optionHeaderVOs.size() > 0 )
            {
               for ( Object optionDetailVOObject : optionDetailVOs )
               {
                  optionDTO.getOptionDetailVOs().add( ( OptionDetailVO ) optionDetailVOObject );
               }
            }

            optionDTOs.add( optionDTO );
         }
      }

      return optionDTOs;
   }

}
