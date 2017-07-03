package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ListHeaderServiceImpl extends ContextService implements ListHeaderService
{

   private ListDetailDao listDetailDao;

   public ListDetailDao getListDetailDao()
   {
      return listDetailDao;
   }

   public void setListDetailDao( ListDetailDao listDetailDao )
   {
      this.listDetailDao = listDetailDao;
   }

   @Override
   public PagedListHolder getListHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ListHeaderDao listHeaderDao = ( ListHeaderDao ) getDao();
      pagedListHolder.setHolderSize( listHeaderDao.countListHeaderVOsByCondition( ( ListHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( listHeaderDao.getListHeaderVOsByCondition( ( ListHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( listHeaderDao.getListHeaderVOsByCondition( ( ListHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ListHeaderVO getListHeaderVOByListHeaderId( final String listHeaderId ) throws KANException
   {
      return ( ( ListHeaderDao ) getDao() ).getListHeaderVOByListHeaderId( listHeaderId );
   }

   @Override
   public int insertListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return ( ( ListHeaderDao ) getDao() ).insertListHeader( listHeaderVO );
   }

   @Override
   public int updateListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return ( ( ListHeaderDao ) getDao() ).updateListHeader( listHeaderVO );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public int deleteListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      try
      {
         // ɾ��ListHeaderVOͬʱ����Ҫɾ����֮��������ListDetailVO
         if ( listHeaderVO != null && listHeaderVO.getListHeaderId() != null && !listHeaderVO.getListHeaderId().trim().equals( "" ) )
         {
            // ��������
            startTransaction();

            final ListDetailVO listDetailVO = new ListDetailVO();
            listDetailVO.setListHeaderId( listHeaderVO.getListHeaderId() );

            // �ȱ��ɾ��List Detail
            for ( Object objectListDetail : this.listDetailDao.getListDetailVOsByCondition( listDetailVO ) )
            {
               ( ( ListDetailVO ) objectListDetail ).setDeleted( ListDetailVO.FALSE );
               ( ( ListDetailVO ) objectListDetail ).setModifyBy( listHeaderVO.getModifyBy() );
               ( ( ListDetailVO ) objectListDetail ).setModifyDate( listHeaderVO.getModifyDate() );
               this.listDetailDao.updateListDetail( ( ( ListDetailVO ) objectListDetail ) );
            }

            // �����ɾ��List Header
            listHeaderVO.setDeleted( ListHeaderVO.FALSE );
            ( ( ListHeaderDao ) getDao() ).updateListHeader( listHeaderVO );

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
   public List< ListDTO > getListDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ������ֵ����
      final List< ListDTO > listDTOs = new ArrayList< ListDTO >();

      // ��ȡAccountId������ListHeaderVO
      final List< Object > listHeaderVOs = ( ( ListHeaderDao ) getDao() ).getListHeaderVOsByAccountId( accountId );

      // ����ListHeaderVO List
      if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
      {
         for ( Object listHeaderVOObject : listHeaderVOs )
         {
            // ��ʼ��ListHeaderVO
            final ListHeaderVO listHeaderVO = ( ListHeaderVO ) listHeaderVOObject;

            // ��ȡListDetailVO�б�
            final List< Object > listDetailVOs = this.getListDetailDao().getListDetailVOsByListHeaderId( listHeaderVO.getListHeaderId() );

            // ��ǰListHeaderVO�����������
            if ( KANUtil.filterEmpty( listHeaderVO.getParentId(), "0" ) == null )
            {
               // ��ʼ��ListDTO 
               final ListDTO listDTO = new ListDTO();
               listDTO.setListHeaderVO( listHeaderVO );

               // ����ListDetailVO�б�
               if ( listDetailVOs != null && listDetailVOs.size() > 0 )
               {
                  for ( Object listDetailVOObject : listDetailVOs )
                  {
                     listDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
                  }
               }

               listDTOs.add( listDTO );
            }
            // ��ǰListHeaderVO�����������
            else
            {
               // �ҵ����������󣬲�����������
               fetchSubListDTO( listHeaderVO, listDetailVOs, listDTOs );
            }

         }
      }

      return listDTOs;
   }

   // �ҵ����������󣬲�����������
   private void fetchSubListDTO( final ListHeaderVO listHeaderVO, final List< Object > listDetailVOs, final List< ListDTO > listDTOs )
   {
      if ( listDTOs != null && listDTOs.size() > 0 && listHeaderVO != null && KANUtil.filterEmpty( listHeaderVO.getParentId() ) != null )
      {
         for ( ListDTO tempListDTO : listDTOs )
         {
            if ( tempListDTO.getListHeaderVO().getListHeaderId().equals( listHeaderVO.getParentId() ) )
            {
               final ListDTO subListDTO = new ListDTO();
               subListDTO.setListHeaderVO( listHeaderVO );

               if ( listDetailVOs != null && listDetailVOs.size() > 0 )
               {
                  for ( Object listDetailVOObject : listDetailVOs )
                  {
                     // ( ( ListDetailVO ) listDetailVOObject ).setColumnIndex( "50" );
                     subListDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
                  }
               }

               tempListDTO.getSubListDTOs().add( subListDTO );
            }
         }
      }
   }

}
