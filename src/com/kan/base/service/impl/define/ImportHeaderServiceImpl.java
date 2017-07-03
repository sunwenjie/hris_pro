package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ImportDetailDao;
import com.kan.base.dao.inf.define.ImportHeaderDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ImportHeaderService;
import com.kan.base.util.KANException;

public class ImportHeaderServiceImpl extends ContextService implements ImportHeaderService
{

   private ImportDetailDao importDetailDao;

   public ImportDetailDao getImportDetailDao()
   {
      return importDetailDao;
   }

   public void setImportDetailDao( ImportDetailDao importDetailDao )
   {
      this.importDetailDao = importDetailDao;
   }

   @Override
   public PagedListHolder getImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ImportHeaderDao importHeaderDao = ( ImportHeaderDao ) getDao();
      pagedListHolder.setHolderSize( importHeaderDao.countImportHeaderVOsByCondition( ( ImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( importHeaderDao.getImportHeaderVOsByCondition( ( ImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( importHeaderDao.getImportHeaderVOsByCondition( ( ImportHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ImportHeaderVO getImportHeaderVOByImportHeaderId( final String importHeaderId ) throws KANException
   {
      return ( ( ImportHeaderDao ) getDao() ).getImportHeaderVOByImportHeaderId( importHeaderId );
   }

   @Override
   public int insertImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      return ( ( ImportHeaderDao ) getDao() ).insertImportHeader( importHeaderVO );
   }

   @Override
   public int updateImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      return ( ( ImportHeaderDao ) getDao() ).updateImportHeader( importHeaderVO );
   }

   @Override
   public int deleteImportHeader( final ImportHeaderVO importHeaderVO ) throws KANException
   {
      try
      {
         // 删除ImportHeaderVO同时，还要删除与之关联到的ImportDetailVO
         if ( importHeaderVO != null && importHeaderVO.getImportHeaderId() != null && !importHeaderVO.getImportHeaderId().trim().equals( "" ) )
         {
            // 开启事务
            startTransaction();

            final ImportDetailVO importDetailVO = new ImportDetailVO();
            importDetailVO.setImportHeaderId( importHeaderVO.getImportHeaderId() );

            // 先标记删除List Detail
            for ( Object objectImportDetail : this.importDetailDao.getImportDetailVOsByCondition( importDetailVO ) )
            {
               ( ( ImportDetailVO ) objectImportDetail ).setDeleted( ImportDetailVO.FALSE );
               ( ( ImportDetailVO ) objectImportDetail ).setModifyBy( importHeaderVO.getModifyBy() );
               ( ( ImportDetailVO ) objectImportDetail ).setModifyDate( importHeaderVO.getModifyDate() );
               this.importDetailDao.updateImportDetail( ( ( ImportDetailVO ) objectImportDetail ) );
            }

            // 最后标记删除List Header
            importHeaderVO.setDeleted( ImportHeaderVO.FALSE );
            ( ( ImportHeaderDao ) getDao() ).updateImportHeader( importHeaderVO );

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
   public List< ImportDTO > getImportDTOsByAccountId( String accountId ) throws KANException
   {
      try
      {
         // 初始化DTO列表对象
         final List< ImportDTO > importDTOs = new ArrayList< ImportDTO >();

         // 获得有效的ImportHeaderVO列表
         final List< Object > importHeaderVOs = ( ( ImportHeaderDao ) getDao() ).getImportHeaderVOsByAccountId( accountId );

         // 遍历importHeaderVOs
         if ( importHeaderVOs != null && importHeaderVOs.size() > 0 )
         {
            for ( Object importHeaderVOObject : importHeaderVOs )
            {
               final ImportHeaderVO tempImportHeaderVO = ( ( ImportHeaderVO ) importHeaderVOObject );

               // 初始化ImportDTO
               final ImportDTO importDTO = new ImportDTO();
               importDTO.setImportHeaderVO( tempImportHeaderVO );

               // 初始化搜索条件
               final ImportDetailVO importDetailVO = new ImportDetailVO();
               importDetailVO.setImportHeaderId( tempImportHeaderVO.getImportHeaderId() );
               importDetailVO.setStatus( BaseVO.TRUE );
               importDetailVO.setSortColumn( "columnIndex" );
               importDetailVO.setSortOrder( "asc" );
               // 获得ImportDetailVO列表
               final List< Object > importDetailVOs = this.importDetailDao.getImportDetailVOsByCondition( importDetailVO );

               // 遍历importDetailVOs
               if ( importDetailVOs != null && importDetailVOs.size() > 0 )
               {
                  for ( Object importDetailVOObject : importDetailVOs )
                  {
                     importDTO.getImportDetailVOs().add( ( ( ImportDetailVO ) importDetailVOObject ) );
                  }
               }
               importDTO.setSubImportDTOs( getImportDTOHeaderId( accountId, tempImportHeaderVO.getImportHeaderId(), tempImportHeaderVO.getCorpId() ) );
               importDTOs.add( importDTO );
            }
         }

         return importDTOs;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   public List< ImportDTO > getImportDTOHeaderId( final String accountId, final String importHeaderId, final String corpId ) throws KANException
   {
      try
      {
         List< ImportDTO > importDTOs = new ArrayList< ImportDTO >();

         // 初始化搜索条件
         final ImportHeaderVO importHeaderVO = new ImportHeaderVO();
         importHeaderVO.setAccountId( accountId );
         importHeaderVO.setParentId( importHeaderId );
         importHeaderVO.setCorpId( corpId );
         importHeaderVO.setDeleted( "0" );
         // TODO

         // 获得有效的ImportHeaderVO列表
         final List< Object > importHeaderVOs = ( ( ImportHeaderDao ) getDao() ).getImportHeaderVOsByCondition( importHeaderVO );

         // 遍历importHeaderVOs 
         if ( importHeaderVOs != null && importHeaderVOs.size() > 0 )
         {
            for ( Object importHeaderObj : importHeaderVOs )
            {
               final ImportHeaderVO tempImportHeaderVO = ( ImportHeaderVO ) importHeaderObj;
               // 初始化ImportDTO
               final ImportDTO importDTO = new ImportDTO();
               importDTO.setImportHeaderVO( tempImportHeaderVO );

               // 初始化搜索条件
               final ImportDetailVO importDetailVO = new ImportDetailVO();
               importDetailVO.setImportHeaderId( tempImportHeaderVO.getImportHeaderId() );
               importDetailVO.setStatus( BaseVO.TRUE );
               importDetailVO.setSortColumn( "columnIndex" );
               importDetailVO.setSortOrder( "asc" );
               // 获得ImportDetailVO列表
               final List< Object > importDetailVOs = this.importDetailDao.getImportDetailVOsByCondition( importDetailVO );

               // 遍历importDetailVOs
               if ( importDetailVOs != null && importDetailVOs.size() > 0 )
               {
                  for ( Object importDetailVOObject : importDetailVOs )
                  {
                     importDTO.getImportDetailVOs().add( ( ( ImportDetailVO ) importDetailVOObject ) );
                  }
               }
               importDTOs.add( importDTO );
            }
         }
         return importDTOs;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }
}
