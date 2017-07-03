package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.SocialBenefitSolutionDetailDao;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionDetailService;
import com.kan.base.util.KANException;

public class SocialBenefitSolutionDetailServiceImpl extends ContextService implements SocialBenefitSolutionDetailService
{

   @Override
   public PagedListHolder getSocialBenefitSolutionDetailVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SocialBenefitSolutionDetailDao socialBenefitDetailDao = ( SocialBenefitSolutionDetailDao ) getDao();
      pagedListHolder.setHolderSize( socialBenefitDetailDao.countSocialBenefitSolutionDetailVOsByCondition( ( SocialBenefitSolutionDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( socialBenefitDetailDao.getSocialBenefitSolutionDetailVOsByCondition( ( SocialBenefitSolutionDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( socialBenefitDetailDao.getSocialBenefitSolutionDetailVOsByCondition( ( SocialBenefitSolutionDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SocialBenefitSolutionDetailVO getSocialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( SocialBenefitSolutionDetailDao ) getDao() ).getSocialBenefitSolutionDetailVOByDetailId( detailId );
   }

   @Override
   public int updateSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitDetailVO ) throws KANException
   {
      return ( ( SocialBenefitSolutionDetailDao ) getDao() ).updateSocialBenefitSolutionDetail( socialBenefitDetailVO );
   }

   @Override
   public int insertSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitDetailVO ) throws KANException
   {
      return ( ( SocialBenefitSolutionDetailDao ) getDao() ).insertSocialBenefitSolutionDetail( socialBenefitDetailVO );
   }

   @Override
   public void deleteSocialBenefitSolutionDetail( final SocialBenefitSolutionDetailVO socialBenefitDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final SocialBenefitSolutionDetailVO modifyObject = ( ( SocialBenefitSolutionDetailDao ) getDao() ).getSocialBenefitSolutionDetailVOByDetailId( socialBenefitDetailVO.getDetailId() );
      modifyObject.setDeleted( SocialBenefitSolutionDetailVO.FALSE );
      ( ( SocialBenefitSolutionDetailDao ) getDao() ).updateSocialBenefitSolutionDetail( modifyObject );
   }

   @Override
   public List< Object > getSocialBenefitSolutionDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return ( ( SocialBenefitSolutionDetailDao ) getDao() ).getSocialBenefitSolutionDetailVOsByHeaderId( headerId );
   }

}
