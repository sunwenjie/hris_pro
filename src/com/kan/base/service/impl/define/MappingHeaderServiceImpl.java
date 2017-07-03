package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.MappingDetailDao;
import com.kan.base.dao.inf.define.MappingHeaderDao;
import com.kan.base.domain.define.MappingDTO;
import com.kan.base.domain.define.MappingDetailVO;
import com.kan.base.domain.define.MappingHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.MappingHeaderService;
import com.kan.base.util.KANException;

public class MappingHeaderServiceImpl extends ContextService implements MappingHeaderService
{

   private MappingDetailDao mappingDetailDao;

   public MappingDetailDao getMappingDetailDao()
   {
      return mappingDetailDao;
   }

   public void setMappingDetailDao( MappingDetailDao mappingDetailDao )
   {
      this.mappingDetailDao = mappingDetailDao;
   }

   @Override
   public PagedListHolder getMappingHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final MappingHeaderDao mappingHeaderDao = ( MappingHeaderDao ) getDao();
      pagedListHolder.setHolderSize( mappingHeaderDao.countMappingHeaderVOsByCondition( ( MappingHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( mappingHeaderDao.getMappingHeaderVOsByCondition( ( MappingHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( mappingHeaderDao.getMappingHeaderVOsByCondition( ( MappingHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public MappingHeaderVO getMappingHeaderVOByMappingHeaderId( final String mappingHeaderId ) throws KANException
   {
      return ( ( MappingHeaderDao ) getDao() ).getMappingHeaderVOByMappingHeaderId( mappingHeaderId );
   }

   @Override
   public int insertMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      return ( ( MappingHeaderDao ) getDao() ).insertMappingHeader( mappingHeaderVO );
   }

   @Override
   public int updateMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      return ( ( MappingHeaderDao ) getDao() ).updateMappingHeader( mappingHeaderVO );
   }

   @Override
   public int deleteMappingHeader( final MappingHeaderVO mappingHeaderVO ) throws KANException
   {
      try
      {
         // 删除MappingHeaderVO同时，还要删除与之关联到的MappingDetailVO
         if ( mappingHeaderVO != null && mappingHeaderVO.getMappingHeaderId() != null && !mappingHeaderVO.getMappingHeaderId().trim().equals( "" ) )
         {
            // 开启事务
            startTransaction();

            final MappingDetailVO mappingDetailVO = new MappingDetailVO();
            mappingDetailVO.setMappingHeaderId( mappingHeaderVO.getMappingHeaderId() );

            // 先标记删除List Detail
            for ( Object objectMappingDetail : this.mappingDetailDao.getMappingDetailVOsByCondition( mappingDetailVO ) )
            {
               ( ( MappingDetailVO ) objectMappingDetail ).setDeleted( MappingDetailVO.FALSE );
               ( ( MappingDetailVO ) objectMappingDetail ).setModifyBy( mappingHeaderVO.getModifyBy() );
               ( ( MappingDetailVO ) objectMappingDetail ).setModifyDate( mappingHeaderVO.getModifyDate() );
               this.mappingDetailDao.updateMappingDetail( ( ( MappingDetailVO ) objectMappingDetail ) );
            }

            // 最后标记删除List Header
            mappingHeaderVO.setDeleted( MappingHeaderVO.FALSE );
            ( ( MappingHeaderDao ) getDao() ).updateMappingHeader( mappingHeaderVO );

            // 提交事务 
            commitTransaction();
         }
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
   public List< MappingDTO > getMappingDTOsByAccountId( final String accountId ) throws KANException
   {
      try
      {
         // 初始化DTO列表对象
         final List< MappingDTO > mappingDTOs = new ArrayList< MappingDTO >();

         // 获得有效的MappingHeaderVO列表
         final List< Object > mappingHeaderVOs = ( ( MappingHeaderDao ) getDao() ).getMappingHeaderVOsByAccountId( accountId );

         // 遍历mappingHeaderVOs
         if ( mappingHeaderVOs != null && mappingHeaderVOs.size() > 0 )
         {
            for ( Object mappingHeaderVOObject : mappingHeaderVOs )
            {
               // 初始化MappingDTO
               final MappingDTO mappingDTO = new MappingDTO();
               mappingDTO.setMappingHeaderVO( ( MappingHeaderVO ) mappingHeaderVOObject );

               // 获得MappingDetailVO列表
               final List< Object > mappingDetailVOs = this.mappingDetailDao.getMappingDetailVOsByMappingHeaderId( ( ( MappingHeaderVO ) mappingHeaderVOObject ).getMappingHeaderId() );

               // 遍历mappingDetailVOs
               if ( mappingDetailVOs != null && mappingDetailVOs.size() > 0 )
               {
                  for ( Object mappingDetailVOObject : mappingDetailVOs )
                  {
                     mappingDTO.getMappingDetailVOs().add( ( MappingDetailVO ) mappingDetailVOObject );
                  }
               }

               mappingDTOs.add( mappingDTO );
            }
         }

         return mappingDTOs;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }
}
