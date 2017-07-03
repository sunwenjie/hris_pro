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
         // ��������
         startTransaction();
         // ���߼�ɾ���籣����ϵͳ��
         socialBenefitHeaderVO.setDeleted( SocialBenefitHeaderVO.FALSE );
         ( ( SocialBenefitHeaderDao ) getDao() ).updateSocialBenefitHeader( socialBenefitHeaderVO );
         // �����籣����ϵͳ��ID����籣���������˻�������
         final List< Object > socialBenefitSolutionHeaderVOs = this.socialBenefitSolutionHeaderDao.getSocialBenefitSolutionHeaderVOsBySysHeaderId( socialBenefitHeaderVO.getHeaderId() );
         // ѭ���߼�ɾ�����������˻�������
         if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
         {
            for ( Object socialBenefitSolutionHeaderVOObject : socialBenefitSolutionHeaderVOs )
            {
               // ���߼�ɾ�����������˻���
               final SocialBenefitSolutionHeaderVO vo = ( SocialBenefitSolutionHeaderVO ) socialBenefitSolutionHeaderVOObject;
               vo.setModifyBy( socialBenefitHeaderVO.getModifyBy() );
               vo.setModifyDate( socialBenefitHeaderVO.getModifyDate() );
               vo.setDeleted( SocialBenefitHeaderVO.FALSE );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( socialBenefitHeaderVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( vo.getHeaderId() );
               if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() > 0 )
               {
                  // ��ѭ���߼�ɾ�������ӱ��˻���
                  for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() )
                  {
                     socialBenefitSolutionDetailVO.setModifyBy( socialBenefitHeaderVO.getModifyBy() );
                     socialBenefitSolutionDetailVO.setModifyDate( socialBenefitHeaderVO.getModifyDate() );
                     socialBenefitSolutionDetailVO.setDeleted( SocialBenefitHeaderVO.FALSE );
                     this.socialBenefitSolutionDetailDao.updateSocialBenefitSolutionDetail( socialBenefitSolutionDetailVO );
                  }
               }
            }
            // ׼���籣�ӱ�ϵͳ����������
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
                  // ������ɾ���ӱ����
                  this.socialBenefitDetailDao.updateSocialBenefitDetail( ( ( SocialBenefitDetailVO ) objDetailVO ) );
               }
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
      // ��ʼ��DTO�б����
      final List< SocialBenefitDTO > socialBenefitDTOs = new ArrayList< SocialBenefitDTO >();

      // �����Ч��SocialBenefitHeaderVO�б�
      final List< Object > socialBenefitHeaderVOs = ( ( SocialBenefitHeaderDao ) getDao() ).getSocialBenefitHeaderVOsByCondition( null );

      // ����SocialBenefitHeaderVO�б�
      if ( socialBenefitHeaderVOs != null && socialBenefitHeaderVOs.size() > 0 )
      {
         for ( Object socialBenefitHeaderVOObject : socialBenefitHeaderVOs )
         {
            // ��ʼ��SocialBenefitDTO����
            final SocialBenefitDTO socialBenefitDTO = new SocialBenefitDTO();
            socialBenefitDTO.setSocialBenefitHeaderVO( ( ( SocialBenefitHeaderVO ) socialBenefitHeaderVOObject ) );

            // ��ȡSocialBenefitDetailVO�б�
            final List< Object > socialBenefitDetailVOs = this.getSocialBenefitDetailDao().getSocialBenefitDetailVOsByHeaderId( ( ( SocialBenefitHeaderVO ) socialBenefitHeaderVOObject ).getHeaderId() );

            // ����SocialBenefitDetailVO�б�
            if ( socialBenefitDetailVOs != null && socialBenefitDetailVOs.size() > 0 )
            {
               for ( Object socialBenefitDetailVOObject : socialBenefitDetailVOs )
               {
                  socialBenefitDTO.getSocialBenefitDetailVOs().add( ( ( SocialBenefitDetailVO ) socialBenefitDetailVOObject ) );
               }
            }

            // װ�� SocialBenefitDTO��SocialBenefitDTO����
            socialBenefitDTOs.add( socialBenefitDTO );
         }
      }

      return socialBenefitDTOs;
   }

}
