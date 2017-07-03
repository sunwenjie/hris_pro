package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.SocialBenefitSolutionDetailDao;
import com.kan.base.dao.inf.management.SocialBenefitSolutionHeaderDao;
import com.kan.base.dao.inf.system.SocialBenefitDetailDao;
import com.kan.base.dao.inf.system.SocialBenefitHeaderDao;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SocialBenefitHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class SocialBenefitHeaderServiceImpl extends ContextService implements SocialBenefitHeaderService
{

   private SocialBenefitDetailDao socialBenefitDetailDao;

   private SocialBenefitSolutionHeaderDao socialBenefitSolutionHeaderDao;

   private SocialBenefitSolutionDetailDao socialBenefitSolutionDetailDao;

   public SocialBenefitDetailDao getSocialBenefitDetailDao()
   {
      return socialBenefitDetailDao;
   }

   public void setSocialBenefitDetailDao( SocialBenefitDetailDao socialBenefitDetailDao )
   {
      this.socialBenefitDetailDao = socialBenefitDetailDao;
   }

   public SocialBenefitSolutionHeaderDao getSocialBenefitSolutionHeaderDao()
   {
      return socialBenefitSolutionHeaderDao;
   }

   public void setSocialBenefitSolutionHeaderDao( SocialBenefitSolutionHeaderDao socialBenefitSolutionHeaderDao )
   {
      this.socialBenefitSolutionHeaderDao = socialBenefitSolutionHeaderDao;
   }

   public SocialBenefitSolutionDetailDao getSocialBenefitSolutionDetailDao()
   {
      return socialBenefitSolutionDetailDao;
   }

   public void setSocialBenefitSolutionDetailDao( SocialBenefitSolutionDetailDao socialBenefitSolutionDetailDao )
   {
      this.socialBenefitSolutionDetailDao = socialBenefitSolutionDetailDao;
   }

   @Override
   public PagedListHolder getSocialBenefitHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SocialBenefitHeaderDao socialBenefitHeaderDao = ( SocialBenefitHeaderDao ) getDao();
      pagedListHolder.setHolderSize( socialBenefitHeaderDao.countSocialBenefitHeaderVOsByCondition( ( SocialBenefitHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( socialBenefitHeaderDao.getSocialBenefitHeaderVOsByCondition( ( SocialBenefitHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( socialBenefitHeaderDao.getSocialBenefitHeaderVOsByCondition( ( SocialBenefitHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SocialBenefitHeaderVO getSocialBenefitHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( SocialBenefitHeaderDao ) getDao() ).getSocialBenefitHeaderVOByHeaderId( headerId );
   }

   @Override
   public int updateSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return ( ( SocialBenefitHeaderDao ) getDao() ).updateSocialBenefitHeader( socialBenefitHeaderVO );
   }

   @Override
   public int insertSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      return ( ( SocialBenefitHeaderDao ) getDao() ).insertSocialBenefitHeader( socialBenefitHeaderVO );
   }

   @Override
   public void deleteSocialBenefitHeader( final SocialBenefitHeaderVO socialBenefitHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();
         // 先逻辑删除社保主表（系统）
         socialBenefitHeaderVO.setDeleted( SocialBenefitHeaderVO.FALSE );
         ( ( SocialBenefitHeaderDao ) getDao() ).updateSocialBenefitHeader( socialBenefitHeaderVO );
         // 根据社保主表（系统）ID获得社保方案主表（账户）集合
         final List< Object > socialBenefitSolutionHeaderVOs = this.socialBenefitSolutionHeaderDao.getSocialBenefitSolutionHeaderVOsBySysHeaderId( socialBenefitHeaderVO.getHeaderId() );
         // 循环逻辑删除方案主表（账户）集合
         if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
         {
            for ( Object socialBenefitSolutionHeaderVOObject : socialBenefitSolutionHeaderVOs )
            {
               // 先逻辑删除方案主表（账户）
               final SocialBenefitSolutionHeaderVO vo = ( SocialBenefitSolutionHeaderVO ) socialBenefitSolutionHeaderVOObject;
               vo.setModifyBy( socialBenefitHeaderVO.getModifyBy() );
               vo.setModifyDate( socialBenefitHeaderVO.getModifyDate() );
               vo.setDeleted( SocialBenefitHeaderVO.FALSE );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( socialBenefitHeaderVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( vo.getHeaderId() );
               if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() > 0 )
               {
                  // 再循环逻辑删除方案从表（账户）
                  for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() )
                  {
                     socialBenefitSolutionDetailVO.setModifyBy( socialBenefitHeaderVO.getModifyBy() );
                     socialBenefitSolutionDetailVO.setModifyDate( socialBenefitHeaderVO.getModifyDate() );
                     socialBenefitSolutionDetailVO.setDeleted( SocialBenefitHeaderVO.FALSE );
                     this.socialBenefitSolutionDetailDao.updateSocialBenefitSolutionDetail( socialBenefitSolutionDetailVO );
                  }
               }
            }
            // 准备社保从表（系统）搜索条件
            final SocialBenefitDetailVO socialBenefitDetailVO = new SocialBenefitDetailVO();
            socialBenefitDetailVO.setHeaderId( socialBenefitHeaderVO.getHeaderId() );
            socialBenefitDetailVO.setStatus( SocialBenefitHeaderVO.TRUE );
            final List< Object > socialBenefitDetailVOs = this.socialBenefitDetailDao.getSocialBenefitDetailVOsByCondition( socialBenefitDetailVO );

            if ( socialBenefitDetailVOs != null && socialBenefitDetailVOs.size() > 0 )
            {
               for ( Object objDetailVO : socialBenefitDetailVOs )
               {
                  ( ( SocialBenefitDetailVO ) objDetailVO ).setModifyBy( socialBenefitHeaderVO.getModifyBy() );
                  ( ( SocialBenefitDetailVO ) objDetailVO ).setModifyDate( socialBenefitHeaderVO.getModifyDate() );
                  ( ( SocialBenefitDetailVO ) objDetailVO ).setDeleted( SocialBenefitDetailVO.FALSE );
                  // 逐个标记删除从表对象
                  this.socialBenefitDetailDao.updateSocialBenefitDetail( ( ( SocialBenefitDetailVO ) objDetailVO ) );
               }
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
   public List< Object > getSocialBenefitHeaderVOsByCityId( final String cityId ) throws KANException
   {
      return ( ( SocialBenefitHeaderDao ) getDao() ).getSocialBenefitHeaderVOsByCityId( cityId );
   }

   @Override
   // Code Reviewed by Siuvan Xia at 2014-7-8
   public List< SocialBenefitDTO > getSocialBenefitDTOs() throws KANException
   {
      // 初始化DTO列表对象
      final List< SocialBenefitDTO > socialBenefitDTOs = new ArrayList< SocialBenefitDTO >();

      // 获得有效的SocialBenefitHeaderVO列表
      final List< Object > socialBenefitHeaderVOs = ( ( SocialBenefitHeaderDao ) getDao() ).getSocialBenefitHeaderVOsByCondition( null );

      // 遍历SocialBenefitHeaderVO列表
      if ( socialBenefitHeaderVOs != null && socialBenefitHeaderVOs.size() > 0 )
      {
         for ( Object socialBenefitHeaderVOObject : socialBenefitHeaderVOs )
         {
            // 初始化SocialBenefitDTO对象
            final SocialBenefitDTO socialBenefitDTO = new SocialBenefitDTO();
            socialBenefitDTO.setSocialBenefitHeaderVO( ( ( SocialBenefitHeaderVO ) socialBenefitHeaderVOObject ) );

            // 获取SocialBenefitDetailVO列表
            final List< Object > socialBenefitDetailVOs = this.getSocialBenefitDetailDao().getSocialBenefitDetailVOsByHeaderId( ( ( SocialBenefitHeaderVO ) socialBenefitHeaderVOObject ).getHeaderId() );

            // 遍历SocialBenefitDetailVO列表
            if ( socialBenefitDetailVOs != null && socialBenefitDetailVOs.size() > 0 )
            {
               for ( Object socialBenefitDetailVOObject : socialBenefitDetailVOs )
               {
                  socialBenefitDTO.getSocialBenefitDetailVOs().add( ( ( SocialBenefitDetailVO ) socialBenefitDetailVOObject ) );
               }
            }

            // 装载 SocialBenefitDTO到SocialBenefitDTO集合
            socialBenefitDTOs.add( socialBenefitDTO );
         }
      }

      return socialBenefitDTOs;
   }

}
