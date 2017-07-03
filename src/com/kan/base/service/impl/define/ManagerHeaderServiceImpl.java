package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ManagerDetailDao;
import com.kan.base.dao.inf.define.ManagerHeaderDao;
import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.util.KANException;

public class ManagerHeaderServiceImpl extends ContextService implements ManagerHeaderService
{

   // ע��ManagerDetailDao
   private ManagerDetailDao managerDetailDao;

   public ManagerDetailDao getManagerDetailDao()
   {
      return managerDetailDao;
   }

   public void setManagerDetailDao( ManagerDetailDao managerDetailDao )
   {
      this.managerDetailDao = managerDetailDao;
   }

   @Override
   public PagedListHolder getManagerHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ManagerHeaderDao managerHeaderDao = ( ManagerHeaderDao ) getDao();
      pagedListHolder.setHolderSize( managerHeaderDao.countManagerHeaderVOsByCondition( ( ManagerHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( managerHeaderDao.getManagerHeaderVOsByCondition( ( ManagerHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( managerHeaderDao.getManagerHeaderVOsByCondition( ( ManagerHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ManagerHeaderVO getManagerHeaderVOByManagerHeaderId( final String managerHeaderId ) throws KANException
   {
      return ( ( ManagerHeaderDao ) getDao() ).getManagerHeaderVOByManagerHeaderId( managerHeaderId );
   }

   @Override
   public int insertManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return ( ( ManagerHeaderDao ) getDao() ).insertManagerHeader( managerHeaderVO );
   }

   @Override
   public int updateManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      return ( ( ManagerHeaderDao ) getDao() ).updateManagerHeader( managerHeaderVO );
   }

   @Override
   public int deleteManagerHeader( final ManagerHeaderVO managerHeaderVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         managerHeaderVO.setDeleted( ManagerHeaderVO.FALSE );
         // ���ɾ��ManagerHeaderVO
         ( ( ManagerHeaderDao ) getDao() ).updateManagerHeader( managerHeaderVO );

         // ��ȡManagerDetailVO�б�
         final List< Object > managerDetailVOs = this.getManagerDetailDao().getManagerDetailVOsByManagerHeaderId( managerHeaderVO.getManagerHeaderId() );

         // ����ManagerDetailVO�б�
         if ( managerDetailVOs != null && managerDetailVOs.size() > 0 )
         {
            for ( Object managerDetailVOObject : managerDetailVOs )
            {
               ( ( ManagerDetailVO ) managerDetailVOObject ).setDeleted( ManagerHeaderVO.FALSE );
               ( ( ManagerDetailVO ) managerDetailVOObject ).setModifyBy( managerHeaderVO.getModifyBy() );
               ( ( ManagerDetailVO ) managerDetailVOObject ).setModifyDate( managerHeaderVO.getModifyDate() );
               
               // ���ɾ��ManagerDetailVO
               this.getManagerDetailDao().updateManagerDetail( ( ManagerDetailVO ) managerDetailVOObject );
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

      return 0;
   }

   @Override
   public List< ManagerDTO > getManagerDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��ManagerDTO List
      final List< ManagerDTO > managerDTOs = new ArrayList< ManagerDTO >();

      // ����accountId��ȡManagerHeaderVO List
      final List< Object > managerHeaderVOs = ( ( ManagerHeaderDao ) getDao() ).getManagerHeaderVOsByAccountId( accountId );

      // ����ManagerHeaderVO List
      if ( managerHeaderVOs != null && managerHeaderVOs.size() > 0 )
      {
         for ( Object managerHeaderVOObject : managerHeaderVOs )
         {
            // ��ʼ��ManagerDTO
            final ManagerDTO managerDTO = new ManagerDTO();

            // ����ManagerVO
            managerDTO.setManagerHeaderVO( ( ManagerHeaderVO ) managerHeaderVOObject );

            // ����managerHeaderId��ȡManagerDetailVO List
            final List< Object > managerDetailVOs = this.getManagerDetailDao().getManagerDetailVOsByManagerHeaderId( ( ( ManagerHeaderVO ) managerHeaderVOObject ).getManagerHeaderId() );

            // ����ManagerDetailVO List
            if ( managerDetailVOs != null && managerDetailVOs.size() > 0 )
            {
               for ( Object managerDetailVOObject : managerDetailVOs )
               {
                  managerDTO.getManagerDetailVOs().add( ( ManagerDetailVO ) managerDetailVOObject );
               }
            }

            // װ��ManagerDTO
            managerDTOs.add( managerDTO );
         }
      }

      return managerDTOs;
   }

}
