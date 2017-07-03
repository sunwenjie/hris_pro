package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.TaxTemplateDetailDao;
import com.kan.base.dao.inf.define.TaxTemplateHeaderDao;
import com.kan.base.domain.define.TaxTemplateDTO;
import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TaxTemplateHeaderService;
import com.kan.base.util.KANException;

public class TaxTemplateHeaderServiceImpl extends ContextService implements TaxTemplateHeaderService
{

   private TaxTemplateDetailDao taxTemplateDetailDao;

   public TaxTemplateDetailDao getTaxTemplateDetailDao()
   {
      return taxTemplateDetailDao;
   }

   public void setTaxTemplateDetailDao( TaxTemplateDetailDao taxTemplateDetailDao )
   {
      this.taxTemplateDetailDao = taxTemplateDetailDao;
   }

   @Override
   public PagedListHolder getTaxTemplateHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TaxTemplateHeaderDao taxTemplateHeaderDao = ( TaxTemplateHeaderDao ) getDao();
      pagedListHolder.setHolderSize( taxTemplateHeaderDao.countTaxTemplateHeaderVOsByCondition( ( TaxTemplateHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( taxTemplateHeaderDao.getTaxTemplateHeaderVOsByCondition( ( TaxTemplateHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( taxTemplateHeaderDao.getTaxTemplateHeaderVOsByCondition( ( TaxTemplateHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public TaxTemplateHeaderVO getTaxTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException
   {
      return ( ( TaxTemplateHeaderDao ) getDao() ).getTaxTemplateHeaderVOByTemplateHeaderId( templateHeaderId );
   }

   @Override
   public int insertTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      return ( ( TaxTemplateHeaderDao ) getDao() ).insertTaxTemplateHeader( taxTemplateHeaderVO );
   }

   @Override
   public int updateTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      return ( ( TaxTemplateHeaderDao ) getDao() ).updateTaxTemplateHeader( taxTemplateHeaderVO );
   }

   @Override
   public int deleteTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ��ȡTaxTemplateDetailVO�б�
         final List< Object > taxTemplateDetailVOs = this.getTaxTemplateDetailDao().getTaxTemplateDetailVOsByTemplateHeaderId( taxTemplateHeaderVO.getTemplateHeaderId() );

         // ����TaxTemplateDetailVO�б�
         if ( taxTemplateDetailVOs != null && taxTemplateDetailVOs.size() > 0 )
         {
            for ( Object taxTemplateDetailVOObject : taxTemplateDetailVOs )
            {
               ( ( TaxTemplateDetailVO ) taxTemplateDetailVOObject ).setDeleted( TaxTemplateDetailVO.FALSE );
               ( ( TaxTemplateDetailVO ) taxTemplateDetailVOObject ).setModifyBy( taxTemplateHeaderVO.getModifyBy() );
               ( ( TaxTemplateDetailVO ) taxTemplateDetailVOObject ).setModifyDate( taxTemplateHeaderVO.getModifyDate() );
               this.taxTemplateDetailDao.updateTaxTemplateDetail( ( ( TaxTemplateDetailVO ) taxTemplateDetailVOObject ) );
            }
         }

         // �����ɾ��List Header
         taxTemplateHeaderVO.setDeleted( TaxTemplateHeaderVO.FALSE );
         ( ( TaxTemplateHeaderDao ) getDao() ).updateTaxTemplateHeader( taxTemplateHeaderVO );

         // �ύ���� 
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public List< TaxTemplateDTO > getTaxTemplateDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��TaxTemplateDTO List
      final List< TaxTemplateDTO > taxTemplateDTOs = new ArrayList< TaxTemplateDTO >();

      // ��ȡAccountId��������ЧTaxTemplateHeaderVO
      final List< Object > taxTemplateHeaderVOs = ( ( TaxTemplateHeaderDao ) getDao() ).getTaxTemplateHeaderVOsByAccountId( accountId );

      // ����TaxTemplateHeaderVO List 
      if ( taxTemplateHeaderVOs != null && taxTemplateHeaderVOs.size() > 0 )
      {
         for ( Object taxTemplateHeaderVOObject : taxTemplateHeaderVOs )
         {
            // ��ʼ��TaxTemplateDTO
            final TaxTemplateDTO taxTemplateDTO = new TaxTemplateDTO();

            taxTemplateDTO.setTaxTemplateHeaderVO( ( TaxTemplateHeaderVO ) taxTemplateHeaderVOObject );

            // ��ȡ��ǰTaxTemplateHeaderVO�µ�����TaxTemplateDetailVO List
            final List< Object > taxTemplateDetailVOs = this.getTaxTemplateDetailDao().getTaxTemplateDetailVOsByTemplateHeaderId( ( ( TaxTemplateHeaderVO ) taxTemplateHeaderVOObject ).getTemplateHeaderId() );

            // ����TaxTemplateDetailVO List
            if ( taxTemplateDetailVOs != null && taxTemplateDetailVOs.size() > 0 )
            {
               for ( Object taxTemplateDetailVOObject : taxTemplateDetailVOs )
               {
                  taxTemplateDTO.getTaxTemplateDetailVOs().add( ( TaxTemplateDetailVO ) taxTemplateDetailVOObject );
               }
            }

            taxTemplateDTOs.add( taxTemplateDTO );
         }
      }

      return taxTemplateDTOs;
   }

}
