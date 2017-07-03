package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.SickLeaveSalaryDetailDao;
import com.kan.base.dao.inf.management.SickLeaveSalaryHeaderDao;
import com.kan.base.domain.management.SickLeaveSalaryDTO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SickLeaveSalaryHeaderService;
import com.kan.base.util.KANException;

public class SickLeaveSalaryHeaderServiceImpl extends ContextService implements SickLeaveSalaryHeaderService
{

   private SickLeaveSalaryDetailDao sickLeaveSalaryDetailDao;

   public SickLeaveSalaryDetailDao getSickLeaveSalaryDetailDao()
   {
      return sickLeaveSalaryDetailDao;
   }

   public void setSickLeaveSalaryDetailDao( SickLeaveSalaryDetailDao sickLeaveSalaryDetailDao )
   {
      this.sickLeaveSalaryDetailDao = sickLeaveSalaryDetailDao;
   }

   @Override
   public PagedListHolder getSickLeaveSalaryHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SickLeaveSalaryHeaderDao SickLeaveSalaryHeaderDao = ( SickLeaveSalaryHeaderDao ) getDao();
      pagedListHolder.setHolderSize( SickLeaveSalaryHeaderDao.countSickLeaveSalaryHeaderVOsByCondition( ( SickLeaveSalaryHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( SickLeaveSalaryHeaderDao.getSickLeaveSalaryHeaderVOsByCondition( ( SickLeaveSalaryHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( SickLeaveSalaryHeaderDao.getSickLeaveSalaryHeaderVOsByCondition( ( SickLeaveSalaryHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SickLeaveSalaryHeaderVO getSickLeaveSalaryHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( ( SickLeaveSalaryHeaderDao ) getDao() ).getSickLeaveSalaryHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertSickLeaveSalaryHeader( SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      try
      {
         this.startTransaction();

         // �Ȳ����Ű�����
         ( ( SickLeaveSalaryHeaderDao ) getDao() ).insertSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );

         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int updateSickLeaveSalaryHeader( SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ���޸�����
         ( ( SickLeaveSalaryHeaderDao ) getDao() ).updateSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );

         // �ύ����
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int deleteSickLeaveSalaryHeader( SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      sickLeaveSalaryHeaderVO.setDeleted( SickLeaveSalaryHeaderVO.FALSE );
      return ( ( SickLeaveSalaryHeaderDao ) getDao() ).updateSickLeaveSalaryHeader( sickLeaveSalaryHeaderVO );
   }

   @Override
   public List< Object > getAvailableSickLeaveSalaryHeaderVOs( SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO ) throws KANException
   {
      sickLeaveSalaryHeaderVO.setStatus( SickLeaveSalaryHeaderVO.TRUE );
      return ( ( SickLeaveSalaryHeaderDao ) getDao() ).getSickLeaveSalaryHeaderVOsByCondition( sickLeaveSalaryHeaderVO );
   }

   @Override
   public List< SickLeaveSalaryDTO > getSickLeaveSalaryDTOsByAccountId( String accountId ) throws KANException
   {
      // ��ʼ��DTO�б����
      final List< SickLeaveSalaryDTO > sickLeaveSalaryDTOs = new ArrayList< SickLeaveSalaryDTO >();

      // �����Ч��SickLeaveSalaryHeaderVO�б�
      final List< Object > SickLeaveSalaryHeaderVOs = ( ( SickLeaveSalaryHeaderDao ) getDao() ).getSickLeaveSalaryHeaderVOsByAccountId( accountId );

      // ����SickLeaveSalaryHeaderVO�б�
      if ( SickLeaveSalaryHeaderVOs != null && SickLeaveSalaryHeaderVOs.size() > 0 )
      {
         for ( Object shiftVOObject : SickLeaveSalaryHeaderVOs )
         {
            // ��ʼ��ShiftDTO����
            final SickLeaveSalaryDTO sickLeaveSalaryDTO = new SickLeaveSalaryDTO();
            sickLeaveSalaryDTO.setSickLeaveSalaryHeaderVO( ( SickLeaveSalaryHeaderVO ) shiftVOObject );

            // ��ȡ��Ч��SickLeaveSalaryDetailVO�б�
            final List< Object > sickLeaveSalaryDetailVOs = this.sickLeaveSalaryDetailDao.getSickLeaveSalaryDetailVOsByHeaderId( ( ( SickLeaveSalaryHeaderVO ) shiftVOObject ).getHeaderId() );
            // ����SickLeaveSalaryDetailVO�б�
            if ( sickLeaveSalaryDetailVOs != null && sickLeaveSalaryDetailVOs.size() > 0 )
            {
               for ( Object sickLeaveSalaryDetailVOObject : sickLeaveSalaryDetailVOs )
               {
                  sickLeaveSalaryDTO.getSickLeaveSalaryDetailVOs().add( ( SickLeaveSalaryDetailVO ) sickLeaveSalaryDetailVOObject );
               }
            }
            sickLeaveSalaryDTOs.add( sickLeaveSalaryDTO );
         }
      }
      return sickLeaveSalaryDTOs;
   }

}
