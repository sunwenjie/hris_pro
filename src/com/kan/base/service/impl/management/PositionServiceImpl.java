package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.PositionDao;
import com.kan.base.domain.management.PositionDTO;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.cp.management.CPPositionService;
import com.kan.base.service.inf.management.PositionService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class PositionServiceImpl extends ContextService implements PositionService, CPPositionService
{

   private EmployeeContractDao employeeContractDao;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   @Override
   public PagedListHolder getPositionVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final PositionDao positionDao = ( PositionDao ) getDao();
      pagedListHolder.setHolderSize( positionDao.countPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( positionDao.getPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( positionDao.getPositionVOsByCondition( ( PositionVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;

   }

   @Override
   public PositionVO getPositionVOByPositionId( String positionId ) throws KANException
   {
      // ʵ����positionVO
      PositionVO positionVO = new PositionVO();
      try
      {
         // ��������
         startTransaction();

         // ͨ��Id��ȡpositionVO
         positionVO = ( ( PositionDao ) getDao() ).getPositionVOByPositionId( positionId );
         // ��ȡpositionId��Ӧ�ĸ��ڵ��positionVO
         final PositionVO parentPositionVO = ( ( PositionDao ) getDao() ).getPositionVOByPositionId( positionVO.getParentPositionId() );
         // ����positionVO��Ӧ��parentPositionName
         if ( parentPositionVO != null && !"".equals( parentPositionVO.getTitleZH().trim() ) )
         {
            // ����parentPositionVO��parentPositionName
            final String parentPositionName = parentPositionVO.getTitleZH() + " - " + ( ( parentPositionVO.getTitleEN() == null ) ? "" : ( parentPositionVO.getTitleEN() ) );
            positionVO.setParentPositionName( parentPositionName );
         }
         else if ( positionId.equals( "1" ) )
         {
            // �Ǹ��ڵ�
            positionVO.setParentPositionName( "" );
         }
         else
         {
            positionVO.setParentPositionName( "" );
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
      return positionVO;
   }

   @Override
   public int updatePosition( PositionVO positionVO ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).updatePosition( positionVO );
   }

   @Override
   public int insertPosition( PositionVO positionVO ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).insertPosition( positionVO );
   }

   @Override
   public int deletePosition( PositionVO positionVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();
         // ���ɾ����ǰPositionVO
         positionVO.setDeleted( PositionVO.FALSE );
         ( ( PositionDao ) getDao() ).updatePosition( positionVO );

         // �ӳ�����ȡ��ǰ�ڵ㿪ʼ��ְλ��
         final PositionDTO positionDTO = KANConstants.getKANAccountConstants( positionVO.getAccountId() ).getEmployeePositionDTOByPositionId( positionVO.getPositionId() );

         // �ݹ���ñ��ɾ������
         deletePosition( positionDTO, positionVO.getModifyBy(), positionVO.getModifyDate() );

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

   // �ݹ���ɾ������
   private void deletePosition( PositionDTO positionDTO, String modifyBy, Date modifyDate )
   {
      if ( positionDTO.getPositionDTOs() != null )
      {
         for ( PositionDTO childPositionDTO : positionDTO.getPositionDTOs() )
         {
            if ( childPositionDTO.getPositionDTOs() != null && childPositionDTO.getPositionDTOs().size() > 0 )
            {
               deletePosition( childPositionDTO, modifyBy, modifyDate );
            }
            // ���ɾ��Position
            if ( childPositionDTO.getPositionVO() != null )
            {
               childPositionDTO.getPositionVO().setDeleted( PositionVO.FALSE );
               childPositionDTO.getPositionVO().setModifyBy( modifyBy );
               childPositionDTO.getPositionVO().setModifyDate( modifyDate );
               try
               {
                  ( ( PositionDao ) getDao() ).updatePosition( childPositionDTO.getPositionVO() );
               }
               catch ( KANException e )
               {
                  e.printStackTrace();
               }
            }
         }
      }
   }

   @Override
   public List< PositionDTO > getPositionDTOsByAccountId( final String accountId ) throws KANException
   {
      // ����PositionDTO List�����ڷ�������
      final List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

      // �½�PositionVO���ڴ���
      final PositionVO positionVO = new PositionVO();
      // Ĭ�ϸ��ڵ�ĸ��ڵ�ֵΪ��0��
      positionVO.setParentPositionId( "0" );
      positionVO.setAccountId( accountId );

      // ��ø��ڵ�
      final List< Object > rootPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( positionVO );

      for ( Object rootPositionObject : rootPositionVOs )
      {
         // �ݹ����
         positionDTOs.add( fetchPositionDTO( ( PositionVO ) rootPositionObject ) );
      }

      return positionDTOs;
   }

   // �ݹ鷽��
   private PositionDTO fetchPositionDTO( final PositionVO positionVO ) throws KANException
   {
      final PositionDTO positionDTO = new PositionDTO();
      // ����PositionVO����
      positionDTO.setPositionVO( positionVO );

      // ����������һ��Position
      final PositionVO subPositionVO = new PositionVO();
      subPositionVO.setAccountId( positionVO.getAccountId() );
      subPositionVO.setParentPositionId( positionVO.getPositionId() );
      // ���ҵ�ǰְλ��Ӧ�ķ���Э������
      String employeeContractNumber = "0";
      EmployeeContractVO employeeContractVO = new EmployeeContractVO();
      employeeContractVO.setAccountId( positionVO.getAccountId() );
      employeeContractVO.setPositionId( positionVO.getPositionId() );
      employeeContractNumber = String.valueOf( this.employeeContractDao.countEmployeeContractVOsByCondition( employeeContractVO ) );
      positionDTO.setEmployeeContractNumber( employeeContractNumber );

      final List< Object > subPositionVOs = ( ( PositionDao ) getDao() ).getPositionVOsByParentPositionId( subPositionVO );

      for ( Object subPositionObject : subPositionVOs )
      {
         // �ݹ����
         positionDTO.getPositionDTOs().add( fetchPositionDTO( ( PositionVO ) subPositionObject ) );
      }
      return positionDTO;
   }

   @Override
   public List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( PositionDao ) getDao() ).getPositionBaseViewsByAccountId( accountId );
   }

}
