package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.ReportHeaderDao;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.dao.inf.define.SearchHeaderDao;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.SearchDTO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.util.KANException;

public class SearchHeaderServiceImpl extends ContextService implements SearchHeaderService
{

   private SearchDetailDao searchDetailDao;

   private ListHeaderDao listHeaderDao;

   private ListDetailDao listDetailDao;

   private ReportHeaderDao reportHeaderDao;

   private ReportDetailDao reportDetailDao;

   public ListHeaderDao getListHeaderDao()
   {
      return listHeaderDao;
   }

   public void setListHeaderDao( ListHeaderDao listHeaderDao )
   {
      this.listHeaderDao = listHeaderDao;
   }

   public ReportHeaderDao getReportHeaderDao()
   {
      return reportHeaderDao;
   }

   public void setReportHeaderDao( ReportHeaderDao reportHeaderDao )
   {
      this.reportHeaderDao = reportHeaderDao;
   }

   public SearchDetailDao getSearchDetailDao()
   {
      return searchDetailDao;
   }

   public void setSearchDetailDao( SearchDetailDao searchDetailDao )
   {
      this.searchDetailDao = searchDetailDao;
   }

   public ListDetailDao getListDetailDao()
   {
      return listDetailDao;
   }

   public void setListDetailDao( ListDetailDao listDetailDao )
   {
      this.listDetailDao = listDetailDao;
   }

   public ReportDetailDao getReportDetailDao()
   {
      return reportDetailDao;
   }

   public void setReportDetailDao( ReportDetailDao reportDetailDao )
   {
      this.reportDetailDao = reportDetailDao;
   }

   @Override
   public PagedListHolder getSearchHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SearchHeaderDao searchHeaderDao = ( SearchHeaderDao ) getDao();
      pagedListHolder.setHolderSize( searchHeaderDao.countSearchHeaderVOsByCondition( ( SearchHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( searchHeaderDao.getSearchHeaderVOsByCondition( ( SearchHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( searchHeaderDao.getSearchHeaderVOsByCondition( ( SearchHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SearchHeaderVO getSearchHeaderVOBySearchHeaderId( final String searchHeaderId ) throws KANException
   {
      return ( ( SearchHeaderDao ) getDao() ).getSearchHeaderVOBySearchHeaderId( searchHeaderId );
   }

   @Override
   public int insertSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return ( ( SearchHeaderDao ) getDao() ).insertSearchHeader( searchHeaderVO );
   }

   @Override
   public int updateSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return ( ( SearchHeaderDao ) getDao() ).updateSearchHeader( searchHeaderVO );
   }

   @Override
   public int deleteSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      try
      {
         if ( searchHeaderVO != null && searchHeaderVO.getSearchHeaderId() != null && !( "" ).equals( searchHeaderVO.getSearchHeaderId().trim() ) )
         {
            // ɾ��SearchHeaderVOͬʱ����Ҫɾ����֮����������������
            //��SearchDetail��ListHeader��ListDetail��ReportHeader��ReportDetail��
            // ��������
            startTransaction();

            // ����searchHeaderVO��deletedֵ
            searchHeaderVO.setDeleted( SearchHeaderVO.FALSE );
            // ���ɾ��SearchHeaderVO
            ( ( SearchHeaderDao ) getDao() ).updateSearchHeader( searchHeaderVO );

            // ����SearchHeaderVOɾ��SearchHeaderVO��Ӧ��SearchDetailVOs
            deleteSearchDetailVOsBySearchHeaderVO( searchHeaderVO );

            // ����SearchHeaderVOɾ��SearchHeaderVO��Ӧ��ListHeaderVOs,ListDetailVO
            deleteListHeaderVOsAndListDetailVOsBySearchHeaderVO( searchHeaderVO );

            // ����SearchHeaderVOɾ��ReportHeaderVOs��ReportDetailVOs
            // deleteReportHeaderVOsAndReportDetailVOsBySearchHeaderVO( searchHeaderVO );

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

   // ����SearchHeaderVOɾ��SearchHeaderVO��Ӧ��SearchDetailVOs
   private void deleteSearchDetailVOsBySearchHeaderVO( SearchHeaderVO searchHeaderVO ) throws KANException
   {
      // �½�SearchDetailVO��ɾ������
      final SearchDetailVO searchDetailVO = new SearchDetailVO();
      // ����SearchDetailVO��SearchHeaderId
      searchDetailVO.setSearchHeaderId( searchHeaderVO.getSearchHeaderId() );
      // ����Ѱ�ҵ���detailVO�Լ����ɾ��
      for ( Object objectSearchDetailVO : this.searchDetailDao.getSearchDetailVOsByCondition( searchDetailVO ) )
      {
         ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyBy( searchHeaderVO.getModifyBy() );
         ( ( SearchDetailVO ) objectSearchDetailVO ).setModifyDate( searchHeaderVO.getModifyDate() );
         ( ( SearchDetailVO ) objectSearchDetailVO ).setDeleted( SearchHeaderVO.FALSE );
         this.searchDetailDao.updateSearchDetail( ( SearchDetailVO ) objectSearchDetailVO );
      }
   }

   // ����SearchHeaderVOɾ��SearchHeaderVO��Ӧ��ListHeaderVOs,ListDetailVOs
   private void deleteListHeaderVOsAndListDetailVOsBySearchHeaderVO( SearchHeaderVO searchHeaderVO ) throws KANException
   {
      final ListHeaderVO listHeaderVO = new ListHeaderVO();
      listHeaderVO.setSearchId( searchHeaderVO.getSearchHeaderId() );
      listHeaderVO.setAccountId( searchHeaderVO.getAccountId() );

      ListDetailVO listDetailVO;
      for ( Object objectListHeaderVO : this.listHeaderDao.getListHeaderVOsByCondition( listHeaderVO ) )
      {
         listDetailVO = new ListDetailVO();
         listDetailVO.setListHeaderId( ( ( ListHeaderVO ) objectListHeaderVO ).getListHeaderId() );

         for ( Object objectListDetailVO : this.listDetailDao.getListDetailVOsByCondition( listDetailVO ) )
         {
            ( ( ListDetailVO ) objectListDetailVO ).setModifyBy( searchHeaderVO.getModifyBy() );
            ( ( ListDetailVO ) objectListDetailVO ).setModifyDate( searchHeaderVO.getModifyDate() );
            ( ( ListDetailVO ) objectListDetailVO ).setDeleted( SearchHeaderVO.FALSE );
            this.listDetailDao.updateListDetail( ( ListDetailVO ) objectListDetailVO );
         }

         ( ( ListHeaderVO ) objectListHeaderVO ).setModifyBy( searchHeaderVO.getModifyBy() );
         ( ( ListHeaderVO ) objectListHeaderVO ).setModifyDate( searchHeaderVO.getModifyDate() );
         ( ( ListHeaderVO ) objectListHeaderVO ).setDeleted( SearchHeaderVO.FALSE );
         this.listHeaderDao.updateListHeader( ( ListHeaderVO ) objectListHeaderVO );
      }
   }


   @Override
   public List< SearchDTO > getSearchDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��SearchDTO List
      final List< SearchDTO > searchDTOs = new ArrayList< SearchDTO >();

      // ����AccountId��ȡSearchHeaderVOs
      final List< Object > searchHeaderVOs = ( ( SearchHeaderDao ) getDao() ).getSearchHeaderVOsByAccountId( accountId );

      // ����SearchHeaderVOs
      if ( searchHeaderVOs != null && searchHeaderVOs.size() > 0 )
      {
         for ( Object searchHeaderVOObject : searchHeaderVOs )
         {
            // ��ʼ��SearchDTO
            final SearchDTO searchDTO = new SearchDTO();

            // ����SearchHeaderVO����
            searchDTO.setSearchHeaderVO( ( SearchHeaderVO ) searchHeaderVOObject );
            // ����SearchHeaderId��ȡSearchDetailVO�б�
            final List< Object > searchDetailVOs = this.searchDetailDao.getSearchDetailVOsBySearchHeaderId( ( ( SearchHeaderVO ) searchHeaderVOObject ).getSearchHeaderId() );

            // ����SearchDetailVOs
            if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
            {
               for ( Object searchDetailVOObject : searchDetailVOs )
               {
                  searchDTO.getSearchDetailVOs().add( ( SearchDetailVO ) searchDetailVOObject );
               }
            }

            searchDTOs.add( searchDTO );
         }
      }

      return searchDTOs;
   }
}
