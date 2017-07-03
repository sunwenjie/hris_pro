package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ItemDao;
import com.kan.base.dao.inf.management.ItemGroupDao;
import com.kan.base.dao.inf.management.ItemGroupRelationDao;
import com.kan.base.domain.management.ItemGroupDTO;
import com.kan.base.domain.management.ItemGroupRelationVO;
import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemGroupService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ItemGroupServiceImpl extends ContextService implements ItemGroupService
{
   // ע��ItemDao
   private ItemDao itemDao;

   // ע��ItemGroupRelationDao
   private ItemGroupRelationDao itemGroupRelationDao;

   public ItemDao getItemDao()
   {
      return itemDao;
   }

   public void setItemDao( ItemDao itemDao )
   {
      this.itemDao = itemDao;
   }

   public ItemGroupRelationDao getItemGroupRelationDao()
   {
      return itemGroupRelationDao;
   }

   public void setItemGroupRelationDao( ItemGroupRelationDao itemGroupRelationDao )
   {
      this.itemGroupRelationDao = itemGroupRelationDao;
   }

   @Override
   public PagedListHolder getItemGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ItemGroupDao itemGroupDao = ( ItemGroupDao ) getDao();
      pagedListHolder.setHolderSize( itemGroupDao.countItemGroupVOsByCondition( ( ItemGroupVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( itemGroupDao.getItemGroupVOsByCondition( ( ItemGroupVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( itemGroupDao.getItemGroupVOsByCondition( ( ItemGroupVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ItemGroupVO getItemGroupVOByItemGroupId( final String itemGroupId ) throws KANException
   {
      // ��ÿ�Ŀ��
      final ItemGroupVO itemGroupVO = ( ( ItemGroupDao ) getDao() ).getItemGroupVOByItemGroupId( itemGroupId );
      // ���ݿ�Ŀ��ID��ö��Ŀ - ��Ŀ���ϵ
      final List< Object > itemGroupRelationVOs = this.itemGroupRelationDao.getItemGroupRelationVOsByItemGroupId( itemGroupId );
      // ���itemGroupRelationVOs��Ϊ��
      if ( itemGroupRelationVOs != null && itemGroupRelationVOs.size() > 0 )
      {
         final List< String > itemIdList = new ArrayList< String >();
         for ( Object itemGroupRelationVOObject : itemGroupRelationVOs )
         {
            itemIdList.add( ( ( ItemGroupRelationVO ) itemGroupRelationVOObject ).getItemId() );
         }
         itemGroupVO.setItemIdArray( KANUtil.stringListToArray( itemIdList ) );
      }

      return itemGroupVO;
   }

   @Override
   public int insertItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      try
      {
         // ��ʼ����
         startTransaction();
         // ����ӿ�Ŀ��
         ( ( ItemGroupDao ) getDao() ).insertItemGroup( itemGroupVO );
         // ����󶨵Ŀ�Ŀ���鲻Ϊ�գ�����ItemGroupRelationVO�в������ݡ�
         if ( itemGroupVO.getItemIdArray() != null && itemGroupVO.getItemIdArray().length > 0 )
         {
            ItemGroupRelationVO itemGroupRelationVO = null;
            for ( String itemIdStr : itemGroupVO.getItemIdArray() )
            {
               itemGroupRelationVO = new ItemGroupRelationVO();
               itemGroupRelationVO.setItemGroupId( itemGroupVO.getItemGroupId() );
               itemGroupRelationVO.setItemId( itemIdStr );
               itemGroupRelationVO.setCreateBy( itemGroupVO.getCreateBy() );
               itemGroupRelationVO.setCreateDate( new Date() );
               itemGroupRelationVO.setModifyBy( itemGroupVO.getCreateBy() );
               itemGroupRelationVO.setModifyDate( new Date() );

               // ������ӷ���
               this.itemGroupRelationDao.insertItemGroupRelation( itemGroupRelationVO );
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
      return 0;
   }

   @Override
   public int updateItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ��ȡItemGroupRelationVO�б�
         final List< Object > itemGroupRelationVOs = this.itemGroupRelationDao.getItemGroupRelationVOsByItemGroupId( itemGroupVO.getItemGroupId() );

         // �����߼�ɾ��
         if ( itemGroupRelationVOs != null && itemGroupRelationVOs.size() > 0 )
         {
            for ( Object itemGroupRelationVOObject : itemGroupRelationVOs )
            {
               this.itemGroupRelationDao.deleteItemGroupRelation( ( ItemGroupRelationVO ) itemGroupRelationVOObject );
            }
         }

         // ����ItemVO
         if ( itemGroupVO.getItemIdArray() != null && itemGroupVO.getItemIdArray().length > 0 )
         {
            ItemGroupRelationVO itemGroupRelationVO = null;
            for ( String itemId : itemGroupVO.getItemIdArray() )
            {
               itemGroupRelationVO = new ItemGroupRelationVO();
               itemGroupRelationVO.setItemGroupId( itemGroupVO.getItemGroupId() );
               itemGroupRelationVO.setItemId( itemId );
               itemGroupRelationVO.setCreateBy( itemGroupVO.getCreateBy() );
               itemGroupRelationVO.setCreateDate( new Date() );
               itemGroupRelationVO.setModifyBy( itemGroupVO.getCreateBy() );
               itemGroupRelationVO.setModifyDate( new Date() );

               this.itemGroupRelationDao.insertItemGroupRelation( itemGroupRelationVO );
            }
         }

         // �����޸Ŀ�Ŀ��
         ( ( ItemGroupDao ) getDao() ).updateItemGroup( itemGroupVO );

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
   public int deleteItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      // ���ɾ��
      final ItemGroupVO modifyObject = ( ( ItemGroupDao ) getDao() ).getItemGroupVOByItemGroupId( itemGroupVO.getItemGroupId() );
      modifyObject.setDeleted( ItemGroupVO.FALSE );
      return ( ( ItemGroupDao ) getDao() ).updateItemGroup( modifyObject );
   }

   @Override
   public List< ItemGroupDTO > getItemGroupDTOsByAccountId( String accountId ) throws KANException
   {
      // ��ʼ��ItemGroupDTO List
      final List< ItemGroupDTO > itemGroupDTOs = new ArrayList< ItemGroupDTO >();

      // ��ʼ��ItemGroupVO List
      final List< Object > itemGroupVOs = ( ( ItemGroupDao ) getDao() ).getItemGroupVOsByAccountId( accountId );

      if ( itemGroupVOs != null && itemGroupVOs.size() > 0 )
      {
         for ( Object itemGroupVOObject : itemGroupVOs )
         {
            // ��ʼ��ItemGroupDTO
            final ItemGroupDTO itemGroupDTO = new ItemGroupDTO();

            // ��ʼ��ItemGroupVO
            final ItemGroupVO itemGroupVO = ( ItemGroupVO ) itemGroupVOObject;

            // װ��ItemGroupVO
            itemGroupDTO.setItemGroupVO( ( ItemGroupVO ) itemGroupVOObject );

            // ��ʼ��ItemGroupRelationVO List
            final List< Object > itemGroupRelationVOs = this.getItemGroupRelationDao().getItemGroupRelationVOsByItemGroupId( itemGroupVO.getItemGroupId() );
            if ( itemGroupRelationVOs != null && itemGroupRelationVOs.size() > 0 )
            {
               for ( Object itemGroupRelationVOObject : itemGroupRelationVOs )
               {
                  // ��ʼ��ItemGroupRelationVO
                  final ItemGroupRelationVO itemGroupRelationVO = ( ItemGroupRelationVO ) itemGroupRelationVOObject;

                  // ��ʼ��ItemVO
                  final ItemVO itemVO = this.getItemDao().getItemVOByItemId( itemGroupRelationVO.getItemId() );

                  // װ��ItemVO
                  if ( itemVO != null )
                  {
                     itemGroupDTO.getItemVOs().add( itemVO );
                  }
               }
            }

            itemGroupDTOs.add( itemGroupDTO );
         }
      }

      return itemGroupDTOs;
   }

}
