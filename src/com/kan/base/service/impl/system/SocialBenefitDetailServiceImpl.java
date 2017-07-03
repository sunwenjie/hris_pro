package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.SocialBenefitSolutionDetailDao;
import com.kan.base.dao.inf.system.SocialBenefitDetailDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SocialBenefitDetailService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SocialBenefitDetailServiceImpl extends ContextService implements SocialBenefitDetailService
{

   // �籣�������˻���
   private SocialBenefitSolutionDetailDao socialBenefitSolutionDetailDao;

   public SocialBenefitSolutionDetailDao getSocialBenefitSolutionDetailDao()
   {
      return socialBenefitSolutionDetailDao;
   }

   public void setSocialBenefitSolutionDetailDao( SocialBenefitSolutionDetailDao socialBenefitSolutionDetailDao )
   {
      this.socialBenefitSolutionDetailDao = socialBenefitSolutionDetailDao;
   }

   @Override
   public PagedListHolder getSocialBenefitDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SocialBenefitDetailDao socialBenefitDetailDao = ( SocialBenefitDetailDao ) getDao();
      pagedListHolder.setHolderSize( socialBenefitDetailDao.countSocialBenefitDetailVOsByCondition( ( SocialBenefitDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( socialBenefitDetailDao.getSocialBenefitDetailVOsByCondition( ( SocialBenefitDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( socialBenefitDetailDao.getSocialBenefitDetailVOsByCondition( ( SocialBenefitDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SocialBenefitDetailVO getSocialBenefitDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( SocialBenefitDetailDao ) getDao() ).getSocialBenefitDetailVOByDetailId( detailId );
   }

   @Override
   public int updateSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         ( ( SocialBenefitDetailDao ) getDao() ).updateSocialBenefitDetail( socialBenefitDetailVO );

         // ��ȡSocialBenefitSolutionDetailVO�б�
         final List< Object > socialBenefitSolutionDetailVOs = this.socialBenefitSolutionDetailDao.getSocialBenefitSolutionDetailVOsBySysDetailId( socialBenefitDetailVO.getDetailId() );

         // ����SocialBenefitSolutionDetailVO�б�
         if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
         {
            // ����籣�ӱ�ϵͳ���Ĺ�˾��������
            final List< MappingVO > companyMappingVOs = KANUtil.generatePercents( socialBenefitDetailVO.getCompanyPercentLow() );

            // ����籣�ӱ�ϵͳ���ĸ��˱�������
            final List< MappingVO > personalMappingVOs = KANUtil.generatePercents( socialBenefitDetailVO.getPersonalPercentLow() );

            for ( Object socialBenefitSolutionDetailVOObject : socialBenefitSolutionDetailVOs )
            {
               // ��ȡSocialBenefitSolutionDetailVO
               final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) socialBenefitSolutionDetailVOObject;

               // �����˾��������������䣬��Ķ�
               if ( !KANUtil.exitsArrayList( companyMappingVOs, socialBenefitSolutionDetailVO.getCompanyPercent() ) )
               {
                  socialBenefitSolutionDetailVO.setCompanyPercent( companyMappingVOs.get( 0 ).getMappingId() );
               }

               // ������˱�������������䣬��Ķ�
               if ( !KANUtil.exitsArrayList( personalMappingVOs, socialBenefitSolutionDetailVO.getPersonalPercent() ) )
               {
                  socialBenefitSolutionDetailVO.setPersonalPercent( personalMappingVOs.get( 0 ).getMappingId() );
               }

               socialBenefitSolutionDetailVO.setCompanyFloor( socialBenefitDetailVO.getCompanyFloor() );
               socialBenefitSolutionDetailVO.setCompanyCap( socialBenefitDetailVO.getCompanyCap() );
               socialBenefitSolutionDetailVO.setPersonalFloor( socialBenefitDetailVO.getPersonalFloor() );
               socialBenefitSolutionDetailVO.setPersonalCap( socialBenefitDetailVO.getPersonalCap() );
               socialBenefitSolutionDetailVO.setCompanyFixAmount( socialBenefitDetailVO.getCompanyFixAmount() );
               socialBenefitSolutionDetailVO.setPersonalFixAmount( socialBenefitDetailVO.getPersonalFixAmount() );
               socialBenefitSolutionDetailVO.setModifyBy( socialBenefitDetailVO.getModifyBy() );
               socialBenefitSolutionDetailVO.setModifyDate( socialBenefitDetailVO.getModifyDate() );

               this.socialBenefitSolutionDetailDao.updateSocialBenefitSolutionDetail( socialBenefitSolutionDetailVO );
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
   public int insertSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      return ( ( SocialBenefitDetailDao ) getDao() ).insertSocialBenefitDetail( socialBenefitDetailVO );
   }

   @Override
   public void deleteSocialBenefitDetail( final SocialBenefitDetailVO socialBenefitDetailVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ���߼�ɾ���籣�ӱ�ϵͳ��
         socialBenefitDetailVO.setDeleted( SocialBenefitDetailVO.FALSE );
         ( ( SocialBenefitDetailDao ) getDao() ).updateSocialBenefitDetail( socialBenefitDetailVO );

         // ��ȡsocialBenefitSolutionDetailVOs
         final List< Object > socialBenefitSolutionDetailVOs = this.socialBenefitSolutionDetailDao.getSocialBenefitSolutionDetailVOsBySysDetailId( socialBenefitDetailVO.getDetailId() );

         // ����ɾ��socialBenefitSolutionDetailVOs
         if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
         {
            for ( Object socialBenefitSolutionDetailVOObject : socialBenefitSolutionDetailVOs )
            {
               final SocialBenefitSolutionDetailVO vo = ( SocialBenefitSolutionDetailVO ) socialBenefitSolutionDetailVOObject;
               vo.setDeleted( SocialBenefitDetailVO.FALSE );
               this.socialBenefitSolutionDetailDao.updateSocialBenefitSolutionDetail( vo );
            }
         }
         
         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

   }

   public static MappingVO getMappingVO( final String source )
   {
      final MappingVO mappingVO = new MappingVO();
      mappingVO.setMappingId( source );
      mappingVO.setMappingValue( source );
      return mappingVO;
   }

   public static List< MappingVO > generatePercents( final String property )
   {
      // ��ʼ������ֵ
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      if ( property != null && !"".equals( property ) )
      {
         // �����ö��
         if ( property.contains( "," ) )
         {
            final String[] enumStr = property.trim().split( "," );

            for ( String str : enumStr )
            {
               mappingVOs.add( getMappingVO( str ) );
            }
         }
         // ���������
         else if ( property.contains( "~" ) )
         {
            int small = ( int ) Math.floor( Double.valueOf( property.trim().split( "~" )[ 0 ] ) );
            int big = ( int ) Math.floor( Double.valueOf( property.trim().split( "~" )[ 1 ] ) );

            mappingVOs.add( getMappingVO( property.trim().split( "~" )[ 0 ] ) );

            for ( int i = small + 1; i <= big; i++ )
            {
               mappingVOs.add( getMappingVO( String.valueOf( i ) ) );
            }

            final Pattern pattern = Pattern.compile( "[0-9]*" );
            // �������Ҳ��Ǵ��࣬���磺12.0
            if ( !pattern.matcher( property.trim().split( "~" )[ 1 ] ).matches() )
            {
               if ( !property.trim().split( "~" )[ 1 ].split( "\\." )[ 1 ].equals( "0" ) && !property.trim().split( "~" )[ 1 ].split( "\\." )[ 1 ].equals( "00" ) )
               {
                  mappingVOs.add( getMappingVO( property.trim().split( "~" )[ 1 ] ) );
               }
            }
         }
         else
         {
            mappingVOs.add( getMappingVO( property ) );
         }

      }

      return mappingVOs;
   }

   public static void main( String[] args )
   {
      final List< MappingVO > mappingVOs = generatePercents( "1~-8" );

      if ( mappingVOs != null && mappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : mappingVOs )
         {
            System.out.println( mappingVO.getMappingId() );
         }
      }
   }

   public static List< MappingVO > generatePercents( final String low, final String hight )
   {
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      if ( KANUtil.filterEmpty( low ) != null && KANUtil.filterEmpty( hight ) != null )
      {

         MappingVO mappingVO = null;
         if ( low.trim().equals( hight.trim() ) )
         {
            mappingVO = new MappingVO();
            mappingVO.setMappingId( low );
            mappingVO.setMappingValue( low );
            mappingVOs.add( mappingVO );
         }
         else
         {
            int small = ( int ) Math.floor( Double.valueOf( low ) );
            int big = ( int ) Math.floor( Double.valueOf( hight ) );
            mappingVO = new MappingVO();
            mappingVO.setMappingId( low );
            mappingVO.setMappingValue( low );
            mappingVOs.add( mappingVO );

            for ( int i = small + 1; i <= big; i++ )
            {
               mappingVO = new MappingVO();
               mappingVO.setMappingId( i + "" );
               mappingVO.setMappingValue( i + "" );
               mappingVOs.add( mappingVO );
            }

            final Pattern pattern = Pattern.compile( "[0-9]*" );
            // �������Ҳ��Ǵ��࣬���磺12.0
            if ( !pattern.matcher( hight ).matches() )
            {
               if ( !hight.split( "\\." )[ 1 ].equals( "0" ) && !hight.split( "\\." )[ 1 ].equals( "00" ) )
               {
                  mappingVO = new MappingVO();
                  mappingVO.setMappingId( hight + "" );
                  mappingVO.setMappingValue( hight + "" );
                  mappingVOs.add( mappingVO );
               }
            }
         }
      }

      return mappingVOs;
   }
}
