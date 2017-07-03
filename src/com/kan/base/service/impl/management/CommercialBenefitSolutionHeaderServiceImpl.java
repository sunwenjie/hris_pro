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
         // ��������
         startTransaction();
         // ���ȱ��ɾ������
         commercialBenefitSolutionHeaderVO.setDeleted( CommercialBenefitSolutionHeaderVO.FALSE );
         ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).updateCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
         // ��ʼ����������
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = new CommercialBenefitSolutionDetailVO();
         commercialBenefitSolutionDetailVO.setAccountId( commercialBenefitSolutionHeaderVO.getAccountId() );
         commercialBenefitSolutionDetailVO.setHeaderId( commercialBenefitSolutionHeaderVO.getHeaderId() );

         // ��ôӱ���󼯺�
         final List< Object > commercialBenefitSolutionDetailVOs = this.commercialBenefitSolutionDetailDao.getCommercialBenefitSolutionDetailVOsByCondition( commercialBenefitSolutionDetailVO );
         // �����Ϊ�գ�������ɾ��
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
         // �ύ����
         commitTransaction();
      }
      catch ( final Exception e )
      {
         //  �ع�����
         rollbackTransaction();
         throw   new KANException( e );
      }
   }

   @Override
   public List< CommercialBenefitSolutionDTO > getCommercialBenefitSolutionDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��CommercialBenefitSolutionDTO List
      final List< CommercialBenefitSolutionDTO > commercialBenefitSolutionDTOs = new ArrayList< CommercialBenefitSolutionDTO >();

      // �����Ч��CommercialBenefitSolutionHeaderVO�б�
      final List< Object > commercialBenefitSolutionHeaderVOs = ( ( CommercialBenefitSolutionHeaderDao ) getDao() ).getCommercialBenefitSolutionHeaderVOsAccountId( accountId );

      // ����CommercialBenefitSolutionHeaderVO�б�
      if ( commercialBenefitSolutionHeaderVOs != null && commercialBenefitSolutionHeaderVOs.size() > 0 )
      {
         for ( Object commercialBenefitSolutionHeaderVOObject : commercialBenefitSolutionHeaderVOs )
         {
            // ��ʼ��CommercialBenefitSolutionDTO����
            final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = new CommercialBenefitSolutionDTO();
            commercialBenefitSolutionDTO.setCommercialBenefitSolutionHeaderVO( ( CommercialBenefitSolutionHeaderVO ) commercialBenefitSolutionHeaderVOObject );

            // ��ȡ��Ч��CommercialBenefitSolutionDetailVO�б�
            final List< Object > commercialBenefitSolutionDetailVOs = this.commercialBenefitSolutionDetailDao.getCommercialBenefitSolutionDetailVOsByHeaderId( ( ( CommercialBenefitSolutionHeaderVO ) commercialBenefitSolutionHeaderVOObject ).getHeaderId() );

            // ����CommercialBenefitSolutionDetailVO�б�
            if ( commercialBenefitSolutionDetailVOs != null && commercialBenefitSolutionDetailVOs.size() > 0 )
            {
               for ( Object commercialBenefitSolutionDetailVOObject : commercialBenefitSolutionDetailVOs )
               {
                  commercialBenefitSolutionDTO.getCommercialBenefitSolutionDetailVOs().add( ( CommercialBenefitSolutionDetailVO ) commercialBenefitSolutionDetailVOObject );
               }
            }
            // װ�� CommercialBenefitSolutionDTO��CommercialBenefitSolutionDTO����
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
