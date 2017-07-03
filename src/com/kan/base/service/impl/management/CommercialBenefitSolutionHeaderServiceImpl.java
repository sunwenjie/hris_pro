package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.CommercialBenefitSolutionDetailDao;
import com.kan.base.dao.inf.management.CommercialBenefitSolutionHeaderDao;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CommercialBenefitSolutionHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class CommercialBenefitSolutionHeaderServiceImpl extends ContextService implements CommercialBenefitSolutionHeaderService
{

   private CommercialBenefitSolutionDetailDao commercialBenefitSolutionDetailDao;

   public CommercialBenefitSolutionDetailDao getCommercialBenefitSolutionDetailDao()
   {
      return commercialBenefitSolutionDetailDao;
   }

   public void setCommercialBenefitSolutionDetailDao( CommercialBenefitSolutionDetailDao commercialBenefitSolutionDetailDao )
   {
      this.commercialBenefitSolutionDetailDao = commercialBenefitSolutionDetailDao;
   }

   @Override
   public PagedListHolder getCommercialBenefitSolutionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CommercialBenefitSolutionHeaderDao commercialBenefitSolutionHeaderDao = ( CommercialBenefitSolutionHeaderDao ) getDao();
      pagedListHolder.setHolderSize( commercialBenefitSolutionHeaderDao.countCommercialBenefitSolutionHeaderVOsByCondition( ( CommercialBenefitSolutionHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( commercialBenefitSolutionHeaderDao.getCommercialBenefitSolutionHeaderVOsByCondition( ( CommercialBenefitSolutionHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( commercialBenefitSolutionHeaderDao.getCommercialBenefitSolutionHeaderVOsByCondition( ( CommercialBenefitSolutionHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public CommercialBenefitSolutionHeaderVO getCommercialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).getCommercialBenefitSolutionHeaderVOByHeaderId( headerId );
   }

   @Override
   public int updateCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).updateCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
   }

   @Override
   public int insertCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).insertCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
   }

   @Override
   public void deleteCommercialBenefitSolutionHeader( final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();
         // 首先标记删除主表
         commercialBenefitSolutionHeaderVO.setDeleted( CommercialBenefitSolutionHeaderVO.FALSE );
         ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).updateCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
         // 初始化搜索条件
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = new CommercialBenefitSolutionDetailVO();
         commercialBenefitSolutionDetailVO.setAccountId( commercialBenefitSolutionHeaderVO.getAccountId() );
         commercialBenefitSolutionDetailVO.setHeaderId( commercialBenefitSolutionHeaderVO.getHeaderId() );

         // 获得从表对象集合
         final List< Object > commercialBenefitSolutionDetailVOs = this.commercialBenefitSolutionDetailDao.getCommercialBenefitSolutionDetailVOsByCondition( commercialBenefitSolutionDetailVO );
         // 如果不为空，逐个标记删除
         if ( commercialBenefitSolutionDetailVOs != null && commercialBenefitSolutionDetailVOs.size() > 0 )
         {
            for ( Object commercialBenefitSolutionDetailVOObject : commercialBenefitSolutionDetailVOs )
            {
               final CommercialBenefitSolutionDetailVO vo = ( CommercialBenefitSolutionDetailVO ) commercialBenefitSolutionDetailVOObject;
               vo.setDeleted( ListDetailVO.FALSE );
               vo.setModifyBy( commercialBenefitSolutionHeaderVO.getModifyBy() );
               vo.setModifyDate( new Date() );
               this.commercialBenefitSolutionDetailDao.updateCommercialBenefitSolutionDetail( vo );
            }
         }
         // 提交事务
         commitTransaction();
      }
      catch ( final Exception e )
      {
         //  回滚事务
         rollbackTransaction();
         throw   new KANException( e );
      }
   }

   @Override
   public List< CommercialBenefitSolutionDTO > getCommercialBenefitSolutionDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化CommercialBenefitSolutionDTO List
      final List< CommercialBenefitSolutionDTO > commercialBenefitSolutionDTOs = new ArrayList< CommercialBenefitSolutionDTO >();

      // 获得有效的CommercialBenefitSolutionHeaderVO列表
      final List< Object > commercialBenefitSolutionHeaderVOs = ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).getCommercialBenefitSolutionHeaderVOsAccountId( accountId );

      // 遍历CommercialBenefitSolutionHeaderVO列表
      if ( commercialBenefitSolutionHeaderVOs != null && commercialBenefitSolutionHeaderVOs.size() > 0 )
      {
         for ( Object commercialBenefitSolutionHeaderVOObject : commercialBenefitSolutionHeaderVOs )
         {
            // 初始化CommercialBenefitSolutionDTO对象
            final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = new CommercialBenefitSolutionDTO();
            commercialBenefitSolutionDTO.setCommercialBenefitSolutionHeaderVO( ( CommercialBenefitSolutionHeaderVO ) commercialBenefitSolutionHeaderVOObject );

            // 获取有效的CommercialBenefitSolutionDetailVO列表
            final List< Object > commercialBenefitSolutionDetailVOs = this.commercialBenefitSolutionDetailDao.getCommercialBenefitSolutionDetailVOsByHeaderId( ( ( CommercialBenefitSolutionHeaderVO ) commercialBenefitSolutionHeaderVOObject ).getHeaderId() );

            // 遍历CommercialBenefitSolutionDetailVO列表
            if ( commercialBenefitSolutionDetailVOs != null && commercialBenefitSolutionDetailVOs.size() > 0 )
            {
               for ( Object commercialBenefitSolutionDetailVOObject : commercialBenefitSolutionDetailVOs )
               {
                  commercialBenefitSolutionDTO.getCommercialBenefitSolutionDetailVOs().add( ( CommercialBenefitSolutionDetailVO ) commercialBenefitSolutionDetailVOObject );
               }
            }
            // 装载 CommercialBenefitSolutionDTO到CommercialBenefitSolutionDTO集合
            commercialBenefitSolutionDTOs.add( commercialBenefitSolutionDTO );
         }
      }

      return commercialBenefitSolutionDTOs;
   }

   public List< Object > getCommercialBenefitSolutionHeaderViewsByAccountId( final String accountId ) throws KANException
   {
      List< CommercialBenefitSolutionDTO > commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( accountId ).COMMERCIAL_BENEFIT_SOLUTION_DTO;
      List< Object > list = new ArrayList< Object >();
      for ( CommercialBenefitSolutionDTO cbs : commercialBenefitSolutionDTO )
      {
         CommercialBenefitSolutionHeaderVO vo = cbs.getCommercialBenefitSolutionHeaderVO();
         HashMap< String, Object > map = new HashMap< String, Object >();
         map.put( "id", vo.getHeaderId() );
         map.put( "name", vo.getNameZH() + " - " + vo.getNameEN() );
         list.add( map );
      }
      return list;
   }
}
