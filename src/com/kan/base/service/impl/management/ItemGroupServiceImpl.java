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
   // 注入ItemDao
   private ItemDao itemDao;

   // 注入ItemGroupRelationDao
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
      // 获得科目组
      final ItemGroupVO itemGroupVO = ( ( ItemGroupDao ) getDao() ).getItemGroupVOByItemGroupId( itemGroupId );
      // 根据科目组ID获得额、科目 - 科目组关系
      final List< Object > itemGroupRelationVOs = this.itemGroupRelationDao.getItemGroupRelationVOsByItemGroupId( itemGroupId );
      // 如果itemGroupRelationVOs不为空
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
         // 开始事务
         startTransaction();
         // 先添加科目组
         ( ( ItemGroupDao ) getDao() ).insertItemGroup( itemGroupVO );
         // 如果绑定的科目数组不为空，则向ItemGroupRelationVO中插入数据。
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

               // 调用添加方法
               this.itemGroupRelationDao.insertItemGroupRelation( itemGroupRelationVO );
            }
         }

         // 提交事务
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
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
         // 开启事务
         startTransaction();

         // 获取ItemGroupRelationVO列表
         final List< Object > itemGroupRelationVOs = this.itemGroupRelationDao.getItemGroupRelationVOsByItemGroupId( itemGroupVO.getItemGroupId() );

         // 遍历逻辑删除
         if ( itemGroupRelationVOs != null && itemGroupRelationVOs.size() > 0 )
         {
            for ( Object itemGroupRelationVOObject : itemGroupRelationVOs )
            {
               this.itemGroupRelationDao.deleteItemGroupRelation( ( ItemGroupRelationVO ) itemGroupRelationVOObject );
            }
         }

         // 存在ItemVO
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

         // 首先修改科目组
         ( ( ItemGroupDao ) getDao() ).updateItemGroup( itemGroupVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public int deleteItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      // 标记删除
      final ItemGroupVO modifyObject = ( ( ItemGroupDao ) getDao() ).getItemGroupVOByItemGroupId( itemGroupVO.getItemGroupId() );
      modifyObject.setDeleted( ItemGroupVO.FALSE );
      return ( ( ItemGroupDao ) getDao() ).updateItemGroup( modifyObject );
   }

   @Override
   public List< ItemGroupDTO > getItemGroupDTOsByAccountId( String accountId ) throws KANException
   {
      // 初始化ItemGroupDTO List
      final List< ItemGroupDTO > itemGroupDTOs = new ArrayList< ItemGroupDTO >();

      // 初始化ItemGroupVO List
      final List< Object > itemGroupVOs = ( ( ItemGroupDao ) getDao() ).getItemGroupVOsByAccountId( accountId );

      if ( itemGroupVOs != null && itemGroupVOs.size() > 0 )
      {
         for ( Object itemGroupVOObject : itemGroupVOs )
         {
            // 初始化ItemGroupDTO
            final ItemGroupDTO itemGroupDTO = new ItemGroupDTO();

            // 初始化ItemGroupVO
            final ItemGroupVO itemGroupVO = ( ItemGroupVO ) itemGroupVOObject;

            // 装载ItemGroupVO
            itemGroupDTO.setItemGroupVO( ( ItemGroupVO ) itemGroupVOObject );

            // 初始化ItemGroupRelationVO List
            final List< Object > itemGroupRelationVOs = this.getItemGroupRelationDao().getItemGroupRelationVOsByItemGroupId( itemGroupVO.getItemGroupId() );
            if ( itemGroupRelationVOs != null && itemGroupRelationVOs.size() > 0 )
            {
               for ( Object itemGroupRelationVOObject : itemGroupRelationVOs )
               {
                  // 初始化ItemGroupRelationVO
                  final ItemGroupRelationVO itemGroupRelationVO = ( ItemGroupRelationVO ) itemGroupRelationVOObject;

                  // 初始化ItemVO
                  final ItemVO itemVO = this.getItemDao().getItemVOByItemId( itemGroupRelationVO.getItemId() );

                  // 装载ItemVO
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
