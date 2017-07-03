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

   // 注入IncomeTaxRangeDetailDao
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
         // 开启事务
         startTransaction();

         // 标记删除IncomeTaxRangeHeaderVO
         incomeTaxRangeHeaderVO.setDeleted( IncomeTaxRangeHeaderVO.FALSE );
         updateIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );

         // 初始化IncomeTaxRangeDetailVO
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = new IncomeTaxRangeDetailVO();
         incomeTaxRangeDetailVO.setHeaderId( incomeTaxRangeHeaderVO.getHeaderId() );
         final List< Object > incomeTaxRangeDetailVOs = getIncomeTaxRangeDetailDao().getIncomeTaxRangeDetailVOsByCondition( incomeTaxRangeDetailVO );
         
         if ( incomeTaxRangeDetailVOs != null && incomeTaxRangeDetailVOs.size() > 0 )
         {
            for ( Object incomeTaxRangeDetailVOObject : incomeTaxRangeDetailVOs )
            {
               // 标记删除IncomeTaxRangeDetailVO
               final IncomeTaxRangeDetailVO tempIncomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) incomeTaxRangeDetailVOObject;
               tempIncomeTaxRangeDetailVO.setModifyBy( incomeTaxRangeHeaderVO.getModifyBy() );
               tempIncomeTaxRangeDetailVO.setModifyDate( incomeTaxRangeHeaderVO.getModifyDate() );
               tempIncomeTaxRangeDetailVO.setDeleted( IncomeTaxRangeHeaderVO.FALSE );
               getIncomeTaxRangeDetailDao().updateIncomeTaxRangeDetail( tempIncomeTaxRangeDetailVO );
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
   }

   @Override
   public List< IncomeTaxRangeDTO > getIncomeTaxRangeDTOs() throws KANException
   {
      // 初始化DTO列表对象
      final List< IncomeTaxRangeDTO > incomeTaxRangeDTOs = new ArrayList< IncomeTaxRangeDTO >();

      // 初始化IncomeTaxRangeHeaderVO
      final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = new IncomeTaxRangeHeaderVO();
      incomeTaxRangeHeaderVO.setStatus( IncomeTaxRangeHeaderVO.TRUE );
      // 获得有效的IncomeTaxRangeHeaderVO列表
      final List< Object > incomeTaxRangeHeaderVOs = getIncomeTaxRangeHeaderVOsByCondition( incomeTaxRangeHeaderVO );

      // 遍历
      if ( incomeTaxRangeHeaderVOs != null && incomeTaxRangeHeaderVOs.size() > 0 )
      {
         for ( Object incomeTaxRangeHeaderVOObject : incomeTaxRangeHeaderVOs )
         {
            // 初始化IncomeTaxRangeDTO对象
            final IncomeTaxRangeDTO incomeTaxRangeDTO = new IncomeTaxRangeDTO();
            incomeTaxRangeDTO.setIncomeTaxRangeHeaderVO( ( IncomeTaxRangeHeaderVO ) incomeTaxRangeHeaderVOObject );

            // 初始化IncomeTaxRangeDetailVO对象，准备搜索条件。
            final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = new IncomeTaxRangeDetailVO();
            incomeTaxRangeDetailVO.setHeaderId( ( ( IncomeTaxRangeHeaderVO ) incomeTaxRangeHeaderVOObject ).getHeaderId() );
            incomeTaxRangeDetailVO.setStatus( IncomeTaxRangeDetailVO.TRUE );
            // 获取有效的IncomeTaxRangeDetailVO列表
            final List< Object > incomeTaxRangeDetailVOs = getIncomeTaxRangeDetailDao().getIncomeTaxRangeDetailVOsByCondition( incomeTaxRangeDetailVO );

            // 遍历
            if ( incomeTaxRangeDetailVOs != null && incomeTaxRangeDetailVOs.size() > 0 )
            {
               for ( Object incomeTaxRangeDetailVOObject : incomeTaxRangeDetailVOs )
               {
                  incomeTaxRangeDTO.getIncomeTaxRangeDetailVOs().add( ( ( IncomeTaxRangeDetailVO ) incomeTaxRangeDetailVOObject ) );
               }
            }

            // 装载 IncomeTaxRangeDTO到集合
            incomeTaxRangeDTOs.add( incomeTaxRangeDTO );
         }
      }

      return incomeTaxRangeDTOs;
   }

}
