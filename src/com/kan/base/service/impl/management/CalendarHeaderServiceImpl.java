package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.CalendarDetailDao;
import com.kan.base.dao.inf.management.CalendarHeaderDao;
import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.domain.management.CalendarHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CalendarHeaderService;
import com.kan.base.util.KANException;

public class CalendarHeaderServiceImpl extends ContextService implements CalendarHeaderService
{

   // ע��CalendarDetail Dao
   private CalendarDetailDao calendarDetailDao;

   public CalendarDetailDao getCalendarDetailDao()
   {
      return calendarDetailDao;
   }

   public void setCalendarDetailDao( CalendarDetailDao calendarDetailDao )
   {
      this.calendarDetailDao = calendarDetailDao;
   }

   @Override
   public PagedListHolder getCalendarHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CalendarHeaderDao calendarHeaderDao = ( CalendarHeaderDao ) getDao();
      pagedListHolder.setHolderSize( calendarHeaderDao.countCalendarHeaderVOsByCondition( ( CalendarHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( calendarHeaderDao.getCalendarHeaderVOsByCondition( ( CalendarHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( calendarHeaderDao.getCalendarHeaderVOsByCondition( ( CalendarHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public CalendarHeaderVO getCalendarHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( CalendarHeaderDao ) getDao() ).getCalendarHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return ( ( CalendarHeaderDao ) getDao() ).insertCalendarHeader( calendarHeaderVO );
   }

   @Override
   public int updateCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return ( ( CalendarHeaderDao ) getDao() ).updateCalendarHeader( calendarHeaderVO );
   }

   @Override
   public int deleteCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���ɾ��CalendarHeaderVO 
         calendarHeaderVO.setDeleted( CalendarHeaderVO.FALSE );
         ( ( CalendarHeaderDao ) getDao() ).updateCalendarHeader( calendarHeaderVO );

         // ��ȡCalendarDetailVO List
         final List< Object > calendarDetailVOs = this.getCalendarDetailDao().getCalendarDetailVOsByHeaderId( calendarHeaderVO.getHeaderId() );

         // ����CalendarDetailVO List
         if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
         {
            for ( Object calendarDetailVOObject : calendarDetailVOs )
            {
               ( ( CalendarDetailVO ) calendarDetailVOObject ).setDeleted( CalendarHeaderVO.FALSE );
               ( ( CalendarDetailVO ) calendarDetailVOObject ).setModifyBy( calendarHeaderVO.getModifyBy() );
               ( ( CalendarDetailVO ) calendarDetailVOObject ).setModifyDate( calendarHeaderVO.getModifyDate() );

               // ���ɾ��CalendarDetailVO
               this.getCalendarDetailDao().updateCalendarDetail( ( ( CalendarDetailVO ) calendarDetailVOObject ) );
            }
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

      return 0;
   }

   @Override
   public List< Object > getAvailableCalendarHeaderVOs( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      calendarHeaderVO.setStatus( CalendarHeaderVO.TRUE );
      return ( ( CalendarHeaderDao ) getDao() ).getCalendarHeaderVOsByCondition( calendarHeaderVO );
   }

   @Override
   public List< CalendarDTO > getCalendarDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��DTO�б����
      final List< CalendarDTO > calendarDTOs = new ArrayList< CalendarDTO >();

      // �����Ч��CalendarHeaderVO�б�
      final List< Object > calendarHeaderVOs = ( ( CalendarHeaderDao ) getDao() ).getCalendarHeaderVOsByAccountId( accountId );

      // ����CalendarHeaderVO�б�
      if ( calendarHeaderVOs != null && calendarHeaderVOs.size() > 0 )
      {
         for ( Object calendarVOObject : calendarHeaderVOs )
         {
            // ��ʼ��CalendarDTO����
            final CalendarDTO calendarDTO = new CalendarDTO();

            calendarDTO.setCalendarHeaderVO( ( CalendarHeaderVO ) calendarVOObject );

            // ��ȡ��Ч��CalendarDetailVO�б�
            final List< Object > calendarDetailVOs = this.calendarDetailDao.getCalendarDetailVOsByHeaderId( ( ( CalendarHeaderVO ) calendarVOObject ).getHeaderId() );

            // ����CalendarDetailVO�б�
            if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
            {
               for ( Object calendarDetailVOObject : calendarDetailVOs )
               {
                  calendarDTO.getCalendarDetailVOs().add( ( CalendarDetailVO ) calendarDetailVOObject );
               }
            }
            calendarDTOs.add( calendarDTO );
         }
      }

      return calendarDTOs;
   }
}
