package com.kan.base.service.impl.management;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.SocialBenefitSolutionDetailDao;
import com.kan.base.dao.inf.management.SocialBenefitSolutionHeaderDao;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class SocialBenefitSolutionHeaderServiceImpl extends ContextService implements SocialBenefitSolutionHeaderService
{

   // 注入SocialBenefitSolutionDetailDao
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
   public PagedListHolder getSocialBenefitSolutionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SocialBenefitSolutionHeaderDao socialBenefitSolutionHeaderDao = ( SocialBenefitSolutionHeaderDao ) getDao();
      pagedListHolder.setHolderSize( socialBenefitSolutionHeaderDao.countSocialBenefitSolutionHeaderVOsByCondition( ( SocialBenefitSolutionHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( socialBenefitSolutionHeaderDao.getSocialBenefitSolutionHeaderVOsByCondition( ( SocialBenefitSolutionHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( socialBenefitSolutionHeaderDao.getSocialBenefitSolutionHeaderVOsByCondition( ( SocialBenefitSolutionHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SocialBenefitSolutionHeaderVO getSocialBenefitSolutionHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( SocialBenefitSolutionHeaderDao ) getDao() ).getSocialBenefitSolutionHeaderVOByHeaderId( headerId );
   }

   @Override
   public int updateSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();
         // 先修改主表
         ( ( SocialBenefitSolutionHeaderDao ) getDao() ).updateSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );
         // 再修改从表
         if ( socialBenefitSolutionHeaderVO.getSysDetailIdArray() != null && socialBenefitSolutionHeaderVO.getSysDetailIdArray().length > 0 )
         {
            SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = null;
            for ( int i = 0; i < socialBenefitSolutionHeaderVO.getDetailIdArray().length; i++ )
            {
               socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
               // 主键解码，如果是“0”，不解码，则是新增。
               if ( !socialBenefitSolutionHeaderVO.getDetailIdArray()[ i ].equals( "0" ) )
               {
                  socialBenefitSolutionDetailVO.setDetailId( Cryptogram.decodeString( URLDecoder.decode( socialBenefitSolutionHeaderVO.getDetailIdArray()[ i ], "UTF-8" ) ) );
               }
               socialBenefitSolutionDetailVO.setHeaderId( socialBenefitSolutionHeaderVO.getHeaderId() );
               socialBenefitSolutionDetailVO.setItemId( socialBenefitSolutionHeaderVO.getItemIdArray()[ i ] );
               socialBenefitSolutionDetailVO.setCompanyPercent( socialBenefitSolutionHeaderVO.getCompanyPercentArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalPercent( socialBenefitSolutionHeaderVO.getPersonalPercentArray()[ i ] );
               socialBenefitSolutionDetailVO.setCompanyFloor( socialBenefitSolutionHeaderVO.getCompanyFloorArray()[ i ] );
               socialBenefitSolutionDetailVO.setCompanyCap( socialBenefitSolutionHeaderVO.getCompanyCapArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalFloor( socialBenefitSolutionHeaderVO.getPersonalFloorArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalCap( socialBenefitSolutionHeaderVO.getPersonalCapArray()[ i ] );
               socialBenefitSolutionDetailVO.setCompanyFixAmount( socialBenefitSolutionHeaderVO.getCompanyFixAmountArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalFixAmount( socialBenefitSolutionHeaderVO.getPersonalFixAmountArray()[ i ] );
               socialBenefitSolutionDetailVO.setStartDateLimit( socialBenefitSolutionHeaderVO.getStartDateLimitArray()[ i ] );
               socialBenefitSolutionDetailVO.setEndDateLimit( socialBenefitSolutionHeaderVO.getEndDateLimitArray()[ i ] );
               socialBenefitSolutionDetailVO.setDeleted( SocialBenefitSolutionHeaderVO.TRUE );

               socialBenefitSolutionDetailVO.setAttribute( socialBenefitSolutionHeaderVO.getAttributeArray()[ i ] );
               socialBenefitSolutionDetailVO.setEffective( socialBenefitSolutionHeaderVO.getEffectiveArray()[ i ] );
               socialBenefitSolutionDetailVO.setStartRule( socialBenefitSolutionHeaderVO.getStartRuleArray()[ i ] );
               socialBenefitSolutionDetailVO.setStartRuleRemark( socialBenefitSolutionHeaderVO.getStartRuleRemarkArray()[ i ] );
               socialBenefitSolutionDetailVO.setEndRule( socialBenefitSolutionHeaderVO.getEndRuleArray()[ i ] );
               socialBenefitSolutionDetailVO.setEndRuleRemark( socialBenefitSolutionHeaderVO.getEndRuleRemarkArray()[ i ] );

               if ( socialBenefitSolutionHeaderVO.getIndexArray() != null && socialBenefitSolutionHeaderVO.getIndexArray().length != 0 )
               {
                  int count = 0;
                  for ( String strIndex : socialBenefitSolutionHeaderVO.getIndexArray() )
                  {
                     if ( Integer.valueOf( strIndex ) == i )
                     {
                        count++;
                        break;
                     }
                  }
                  if ( count != 0 )
                  {
                     socialBenefitSolutionDetailVO.setStatus( SocialBenefitSolutionHeaderVO.TRUE );
                  }
                  else
                  {
                     socialBenefitSolutionDetailVO.setStatus( SocialBenefitSolutionHeaderVO.FALSE );
                  }
               }
               else
               {
                  socialBenefitSolutionDetailVO.setStatus( SocialBenefitSolutionHeaderVO.FALSE );
               }
               socialBenefitSolutionDetailVO.setModifyBy( socialBenefitSolutionHeaderVO.getModifyBy() );
               socialBenefitSolutionDetailVO.setModifyDate( socialBenefitSolutionHeaderVO.getModifyDate() );
               socialBenefitSolutionDetailVO.setCreateBy( socialBenefitSolutionHeaderVO.getCreateBy() );
               socialBenefitSolutionDetailVO.setCreateDate( socialBenefitSolutionHeaderVO.getCreateDate() );
               if ( !socialBenefitSolutionHeaderVO.getDetailIdArray()[ i ].equals( "0" ) )
               {
                  // 调用修改方法
                  this.socialBenefitSolutionDetailDao.updateSocialBenefitSolutionDetail( socialBenefitSolutionDetailVO );
               }
               else
               {
                  int count = 0;
                  for ( String strIndex : socialBenefitSolutionHeaderVO.getIndexArray() )
                  {
                     if ( Integer.valueOf( strIndex ) == i )
                     {
                        count++;
                        break;
                     }
                  }
                  if ( count != 0 )
                  {
                     // 调用添加方法
                     socialBenefitSolutionDetailVO.setSysDetailId( socialBenefitSolutionHeaderVO.getSysDetailIdArray()[ i ] );
                     this.socialBenefitSolutionDetailDao.insertSocialBenefitSolutionDetail( socialBenefitSolutionDetailVO );
                  }
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

      return 0;
   }

   @Override
   public int insertSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();
         // 先添加主表
         ( ( SocialBenefitSolutionHeaderDao ) getDao() ).insertSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );

         if ( socialBenefitSolutionHeaderVO.getSysDetailIdArray() != null && socialBenefitSolutionHeaderVO.getSysDetailIdArray().length > 0 )
         {
            SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = null;
            for ( int i = 0; i < socialBenefitSolutionHeaderVO.getSysDetailIdArray().length; i++ )
            {
               socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
               // 定义一个计算器
               int count = 0;
               // 判断社保方案是否勾选
               for ( String strIndex : socialBenefitSolutionHeaderVO.getIndexArray() )
               {
                  if ( Integer.valueOf( strIndex ) == i )
                  {
                     count++;
                     break;
                  }
               }
               if ( count != 0 )
               {
                  socialBenefitSolutionDetailVO.setStatus( SocialBenefitSolutionHeaderVO.TRUE );
               }
               else
               {
                  socialBenefitSolutionDetailVO.setStatus( SocialBenefitSolutionHeaderVO.FALSE );
               }
               socialBenefitSolutionDetailVO.setHeaderId( socialBenefitSolutionHeaderVO.getHeaderId() );
               socialBenefitSolutionDetailVO.setSysDetailId( socialBenefitSolutionHeaderVO.getSysDetailIdArray()[ i ] );
               socialBenefitSolutionDetailVO.setItemId( socialBenefitSolutionHeaderVO.getItemIdArray()[ i ] );
               socialBenefitSolutionDetailVO.setCompanyPercent( socialBenefitSolutionHeaderVO.getCompanyPercentArray()[ i ].trim() );
               socialBenefitSolutionDetailVO.setPersonalPercent( socialBenefitSolutionHeaderVO.getPersonalPercentArray()[ i ].trim() );
               socialBenefitSolutionDetailVO.setCompanyFloor( socialBenefitSolutionHeaderVO.getCompanyFloorArray()[ i ].trim() );
               socialBenefitSolutionDetailVO.setCompanyCap( socialBenefitSolutionHeaderVO.getCompanyCapArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalFloor( socialBenefitSolutionHeaderVO.getPersonalFloorArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalCap( socialBenefitSolutionHeaderVO.getPersonalCapArray()[ i ] );
               socialBenefitSolutionDetailVO.setCompanyFixAmount( socialBenefitSolutionHeaderVO.getCompanyFixAmountArray()[ i ] );
               socialBenefitSolutionDetailVO.setPersonalFixAmount( socialBenefitSolutionHeaderVO.getPersonalFixAmountArray()[ i ] );
               socialBenefitSolutionDetailVO.setStartDateLimit( socialBenefitSolutionHeaderVO.getStartDateLimitArray()[ i ] );
               socialBenefitSolutionDetailVO.setEndDateLimit( socialBenefitSolutionHeaderVO.getEndDateLimitArray()[ i ] );
               socialBenefitSolutionDetailVO.setModifyBy( socialBenefitSolutionHeaderVO.getModifyBy() );
               socialBenefitSolutionDetailVO.setModifyDate( socialBenefitSolutionHeaderVO.getModifyDate() );
               socialBenefitSolutionDetailVO.setCreateBy( socialBenefitSolutionHeaderVO.getCreateBy() );
               socialBenefitSolutionDetailVO.setCreateDate( socialBenefitSolutionHeaderVO.getCreateDate() );

               socialBenefitSolutionDetailVO.setAttribute( socialBenefitSolutionHeaderVO.getAttributeArray()[ i ] );
               socialBenefitSolutionDetailVO.setEffective( socialBenefitSolutionHeaderVO.getEffectiveArray()[ i ] );
               socialBenefitSolutionDetailVO.setStartRule( socialBenefitSolutionHeaderVO.getStartRuleArray()[ i ] );
               socialBenefitSolutionDetailVO.setStartRuleRemark( socialBenefitSolutionHeaderVO.getStartRuleRemarkArray()[ i ] );
               socialBenefitSolutionDetailVO.setEndRule( socialBenefitSolutionHeaderVO.getEndRuleArray()[ i ] );
               socialBenefitSolutionDetailVO.setEndRuleRemark( socialBenefitSolutionHeaderVO.getEndRuleRemarkArray()[ i ] );

               this.socialBenefitSolutionDetailDao.insertSocialBenefitSolutionDetail( socialBenefitSolutionDetailVO );
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
   public void deleteSocialBenefitSolutionHeader( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();
         // 先标记删除主表
         socialBenefitSolutionHeaderVO.setDeleted( SocialBenefitSolutionHeaderVO.FALSE );
         ( ( SocialBenefitSolutionHeaderDao ) getDao() ).updateSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );
         // 从常量（账户）中拿到SocialBenefitSolutionDTO集合
         final List< SocialBenefitSolutionDTO > socialBenefitSolutionDTOs = KANConstants.getKANAccountConstants( socialBenefitSolutionHeaderVO.getAccountId() ).SOCIAL_BENEFIT_SOLUTION_DTO;
         // 初始化对应从表集合
         List< SocialBenefitSolutionDetailVO > socialBenefitSolutionDetailVOs = null;
         if ( socialBenefitSolutionDTOs != null && socialBenefitSolutionDTOs.size() > 0 )
         {
            for ( SocialBenefitSolutionDTO socialBenefitSolutionDTOObject : socialBenefitSolutionDTOs )
            {
               if ( socialBenefitSolutionDTOObject.getSocialBenefitSolutionHeaderVO().getHeaderId().trim().equals( socialBenefitSolutionHeaderVO.getHeaderId() ) )
               {
                  socialBenefitSolutionDetailVOs = socialBenefitSolutionDTOObject.getSocialBenefitSolutionDetailVOs();
                  break;
               }
            }
         }
         // 逐个标记删除
         if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
         {
            for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVOObject : socialBenefitSolutionDetailVOs )
            {
               final SocialBenefitSolutionDetailVO vo = socialBenefitSolutionDetailVOObject;
               vo.setDeleted( SocialBenefitSolutionHeaderVO.FALSE );
               this.socialBenefitSolutionDetailDao.updateSocialBenefitSolutionDetail( vo );
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
   public List< Object > getSocialBenefitSolutionHeaderVOsBySysHeaderId( final String sysHeaderId ) throws KANException
   {
      return ( ( SocialBenefitSolutionHeaderDao ) getDao() ).getSocialBenefitSolutionHeaderVOsBySysHeaderId( sysHeaderId );
   }

   @Override
   public List< SocialBenefitSolutionHeaderVO > getSocialBenefitSolutionHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      final List< SocialBenefitSolutionDTO > socialBenefitSolutionDTOs = KANConstants.getKANAccountConstants( accountId ).SOCIAL_BENEFIT_SOLUTION_DTO;
      final List< SocialBenefitSolutionHeaderVO > socialBenefitSolutionHeaderVOs = new ArrayList< SocialBenefitSolutionHeaderVO >();
      for ( SocialBenefitSolutionDTO socialBenefitSolutionDTO : socialBenefitSolutionDTOs )
      {
         socialBenefitSolutionHeaderVOs.add( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() );
      }

      return socialBenefitSolutionHeaderVOs;
   }

   @Override
   public List< SocialBenefitSolutionDTO > getSocialBenefitSolutionDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化DTO列表对象
      final List< SocialBenefitSolutionDTO > socialBenefitSolutionDTOs = new ArrayList< SocialBenefitSolutionDTO >();

      // 获取当前AccountId下SocialBenefitSolutionHeaderVO所有列表
      final List< Object > sbSolutionHeaderVOs = ( ( SocialBenefitSolutionHeaderDao ) getDao() ).getSocialBenefitSolutionHeaderVOsByAccountId( accountId );

      // 存在SocialBenefitSolutionHeaderVO列表
      if ( sbSolutionHeaderVOs != null && sbSolutionHeaderVOs.size() > 0 )
      {
         for ( Object sbSolutionHeaderVOObject : sbSolutionHeaderVOs )
         {
            // 初始化SocialBenefitSolutionDTO对象
            final SocialBenefitSolutionDTO sbSolutionDTO = new SocialBenefitSolutionDTO();
            sbSolutionDTO.setSocialBenefitSolutionHeaderVO( ( SocialBenefitSolutionHeaderVO ) sbSolutionHeaderVOObject );

            // 获取SocialBenefitSolutionDetailVO List
            final List< Object > sbSolutionDetailVOs = this.getSocialBenefitSolutionDetailDao().getSocialBenefitSolutionDetailVOsByHeaderId( ( ( SocialBenefitSolutionHeaderVO ) sbSolutionHeaderVOObject ).getHeaderId() );

            // 存在SocialBenefitSolutionDetailVO List
            if ( sbSolutionDetailVOs != null && sbSolutionDetailVOs.size() > 0 )
            {
               for ( Object sbDetailSolutionVOObject : sbSolutionDetailVOs )
               {
                  sbSolutionDTO.getSocialBenefitSolutionDetailVOs().add( ( SocialBenefitSolutionDetailVO ) sbDetailSolutionVOObject );
               }
            }

            socialBenefitSolutionDTOs.add( sbSolutionDTO );
         }
      }

      return socialBenefitSolutionDTOs;
   }

   @Override
   public List< Object > getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( ( SocialBenefitSolutionHeaderDao ) getDao() ).getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( socialBenefitSolutionHeaderVO );
   }
}
