package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.IncomeTaxRangeDetailDao;
import com.kan.base.dao.inf.system.IncomeTaxRangeHeaderDao;
import com.kan.base.domain.system.IncomeTaxRangeDTO;
import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.KANException;

public class IncomeTaxRangeHeaderServiceImpl extends ContextService implements IncomeTaxRangeHeaderService
{

   // ע��IncomeTaxRangeDetailDao
   private IncomeTaxRangeDetailDao incomeTaxRangeDetailDao;

   public IncomeTaxRangeDetailDao getIncomeTaxRangeDetailDao()
   {
      return incomeTaxRangeDetailDao;
   }

   public void setIncomeTaxRangeDetailDao( final IncomeTaxRangeDetailDao incomeTaxRangeDetailDao )
   {
      this.incomeTaxRangeDetailDao = incomeTaxRangeDetailDao;
   }

   @Override
   public List< Object > getIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return ( ( IncomeTaxRangeHeaderDao ) getDao() ).getIncomeTaxRangeHeaderVOsByCondition( incomeTaxRangeHeaderVO );
   }

   @Override
   public PagedListHolder getIncomeTaxRangeHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final IncomeTaxRangeHeaderDao socialBenefitHeaderDao = ( IncomeTaxRangeHeaderDao ) getDao();
      pagedListHolder.setHolderSize( socialBenefitHeaderDao.countIncomeTaxRangeHeaderVOsByCondition( ( IncomeTaxRangeHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( socialBenefitHeaderDao.getIncomeTaxRangeHeaderVOsByCondition( ( IncomeTaxRangeHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( socialBenefitHeaderDao.getIncomeTaxRangeHeaderVOsByCondition( ( IncomeTaxRangeHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public IncomeTaxRangeHeaderVO getIncomeTaxRangeHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( IncomeTaxRangeHeaderDao ) getDao() ).getIncomeTaxRangeHeaderVOByHeaderId( headerId );
   }

   @Override
   public int updateIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return ( ( IncomeTaxRangeHeaderDao ) getDao() ).updateIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );
   }

   @Override
   public int insertIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      return ( ( IncomeTaxRangeHeaderDao ) getDao() ).insertIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );
   }

   @Override
   public void deleteIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ���ɾ��IncomeTaxRangeHeaderVO
         incomeTaxRangeHeaderVO.setDeleted( IncomeTaxRangeHeaderVO.FALSE );
         updateIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );

         // ��ʼ��IncomeTaxRangeDetailVO
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = new IncomeTaxRangeDetailVO();
         incomeTaxRangeDetailVO.setHeaderId( incomeTaxRangeHeaderVO.getHeaderId() );
         final List< Object > incomeTaxRangeDetailVOs = getIncomeTaxRangeDetailDao().getIncomeTaxRangeDetailVOsByCondition( incomeTaxRangeDetailVO );
         
         if ( incomeTaxRangeDetailVOs != null && incomeTaxRangeDetailVOs.size() > 0 )
         {
            for ( Object incomeTaxRangeDetailVOObject : incomeTaxRangeDetailVOs )
            {
               // ���ɾ��IncomeTaxRangeDetailVO
               final IncomeTaxRangeDetailVO tempIncomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) incomeTaxRangeDetailVOObject;
               tempIncomeTaxRangeDetailVO.setModifyBy( incomeTaxRangeHeaderVO.getModifyBy() );
               tempIncomeTaxRangeDetailVO.setModifyDate( incomeTaxRangeHeaderVO.getModifyDate() );
               tempIncomeTaxRangeDetailVO.setDeleted( IncomeTaxRangeHeaderVO.FALSE );
               getIncomeTaxRangeDetailDao().updateIncomeTaxRangeDetail( tempIncomeTaxRangeDetailVO );
            }
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
   }

   @Override
   public List< IncomeTaxRangeDTO > getIncomeTaxRangeDTOs() throws KANException
   {
      // ��ʼ��DTO�б����
      final List< IncomeTaxRangeDTO > incomeTaxRangeDTOs = new ArrayList< IncomeTaxRangeDTO >();

      // ��ʼ��IncomeTaxRangeHeaderVO
      final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = new IncomeTaxRangeHeaderVO();
      incomeTaxRangeHeaderVO.setStatus( IncomeTaxRangeHeaderVO.TRUE );
      // �����Ч��IncomeTaxRangeHeaderVO�б�
      final List< Object > incomeTaxRangeHeaderVOs = getIncomeTaxRangeHeaderVOsByCondition( incomeTaxRangeHeaderVO );

      // ����
      if ( incomeTaxRangeHeaderVOs != null && incomeTaxRangeHeaderVOs.size() > 0 )
      {
         for ( Object incomeTaxRangeHeaderVOObject : incomeTaxRangeHeaderVOs )
         {
            // ��ʼ��IncomeTaxRangeDTO����
            final IncomeTaxRangeDTO incomeTaxRangeDTO = new IncomeTaxRangeDTO();
            incomeTaxRangeDTO.setIncomeTaxRangeHeaderVO( ( IncomeTaxRangeHeaderVO ) incomeTaxRangeHeaderVOObject );

            // ��ʼ��IncomeTaxRangeDetailVO����׼������������
            final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = new IncomeTaxRangeDetailVO();
            incomeTaxRangeDetailVO.setHeaderId( ( ( IncomeTaxRangeHeaderVO ) incomeTaxRangeHeaderVOObject ).getHeaderId() );
            incomeTaxRangeDetailVO.setStatus( IncomeTaxRangeDetailVO.TRUE );
            // ��ȡ��Ч��IncomeTaxRangeDetailVO�б�
            final List< Object > incomeTaxRangeDetailVOs = getIncomeTaxRangeDetailDao().getIncomeTaxRangeDetailVOsByCondition( incomeTaxRangeDetailVO );

            // ����
            if ( incomeTaxRangeDetailVOs != null && incomeTaxRangeDetailVOs.size() > 0 )
            {
               for ( Object incomeTaxRangeDetailVOObject : incomeTaxRangeDetailVOs )
               {
                  incomeTaxRangeDTO.getIncomeTaxRangeDetailVOs().add( ( ( IncomeTaxRangeDetailVO ) incomeTaxRangeDetailVOObject ) );
               }
            }

            // װ�� IncomeTaxRangeDTO������
            incomeTaxRangeDTOs.add( incomeTaxRangeDTO );
         }
      }

      return incomeTaxRangeDTOs;
   }

}
