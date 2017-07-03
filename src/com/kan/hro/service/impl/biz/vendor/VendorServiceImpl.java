package com.kan.hro.service.impl.biz.vendor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.BankVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.vendor.VendorContactDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorDTO;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

public class VendorServiceImpl extends ContextService implements VendorService
{

   // �������������磬com.kan.base.domain.BaseVO��
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.vendor.VendorVO";

   // Service Name�����磬Spring�����Bean���� spring�����ļ��� service��ӦBean��ID ��
   public static final String SERVICE_BEAN = "vendorService";

   // ������service
   private WorkflowService workflowService;

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   // ע�빩Ӧ����ϵ��DAO
   private VendorContactDao vendorContactDao;

   // ע�빩Ӧ�̷���DAO
   private VendorServiceDao vendorServiceDao;

   public VendorContactDao getVendorContactDao()
   {
      return vendorContactDao;
   }

   public void setVendorContactDao( VendorContactDao vendorContactDao )
   {
      this.vendorContactDao = vendorContactDao;
   }

   public VendorServiceDao getVendorServiceDao()
   {
      return vendorServiceDao;
   }

   public void setVendorServiceDao( VendorServiceDao vendorServiceDao )
   {
      this.vendorServiceDao = vendorServiceDao;
   }

   @Override
   public PagedListHolder getVendorVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final VendorDao vendorDao = ( VendorDao ) getDao();
      pagedListHolder.setHolderSize( vendorDao.countVendorVOsByCondition( ( VendorVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( vendorDao.getVendorVOsByCondition( ( VendorVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( vendorDao.getVendorVOsByCondition( ( VendorVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public VendorVO getVendorVOByVendorId( final String vendorId ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOByVendorId( vendorId );
   }

   @Override
   public int insertVendor( final VendorVO vendorVO ) throws KANException
   {
      int rows = 0;
      final BankVO bankVO = KANConstants.getKANAccountConstants( vendorVO.getAccountId() ).getBankVOByBankId( vendorVO.getBankId() );
      if ( bankVO != null )
      {
         vendorVO.setBankName( bankVO.getNameZH() );
      }

      rows = ( ( VendorDao ) getDao() ).insertVendor( vendorVO );

      if ( vendorVO.getSubAction() != null && vendorVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
      {
         rows = submitVendor( vendorVO );
      }

      return rows;
   }

   @Override
   public int updateVendor( final VendorVO vendorVO ) throws KANException
   {
      final BankVO bankVO = KANConstants.getKANAccountConstants( vendorVO.getAccountId() ).getBankVOByBankId( vendorVO.getBankId() );
      if ( bankVO != null )
      {
         vendorVO.setBankName( bankVO.getNameZH() );
      }
      else
      {
         vendorVO.setBankName( null );
      }
      return ( ( VendorDao ) getDao() ).updateVendor( vendorVO );
   }

   @Override
   public int deleteVendor( final VendorVO vendorVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ���ɾ��VendorVO
         vendorVO.setDeleted( VendorVO.FALSE );
         ( ( VendorDao ) getDao() ).updateVendor( vendorVO );

         // ׼����������
         final VendorContactVO vendorContactVO = new VendorContactVO();
         vendorContactVO.setAccountId( vendorVO.getAccountId() );
         vendorContactVO.setVendorId( vendorVO.getVendorId() );
         vendorContactVO.setStatus( VendorVO.TRUE );

         // ��ȡVendorContactVO�б�
         final List< Object > vendorContactVOs = this.vendorContactDao.getVendorContactVOsByCondition( vendorContactVO );

         // ѭ�����ɾ����ϵ��
         if ( vendorContactVOs != null && vendorContactVOs.size() > 0 )
         {
            for ( Object vendorContactVOObject : vendorContactVOs )
            {
               ( ( VendorContactVO ) vendorContactVOObject ).setDeleted( VendorVO.FALSE );
               ( ( VendorContactVO ) vendorContactVOObject ).setModifyBy( vendorVO.getModifyBy() );
               ( ( VendorContactVO ) vendorContactVOObject ).setModifyDate( new Date() );
               this.vendorContactDao.updateVendorContact( ( ( VendorContactVO ) vendorContactVOObject ) );
            }
         }

         // ׼����������
         final VendorServiceVO vendorServiceVO = new VendorServiceVO();
         vendorServiceVO.setAccountId( vendorServiceVO.getAccountId() );
         vendorServiceVO.setVendorId( vendorVO.getVendorId() );
         vendorServiceVO.setStatus( VendorVO.TRUE );

         // ��ȡVendorServiceVO�б�
         final List< Object > vendorServiceVOs = this.vendorServiceDao.getVendorServiceVOsByCondition( vendorServiceVO );

         // ѭ�����ɾ������
         if ( vendorServiceVOs != null && vendorServiceVOs.size() > 0 )
         {
            for ( Object vendorServiceVOObject : vendorServiceVOs )
            {
               ( ( VendorServiceVO ) vendorServiceVOObject ).setDeleted( VendorVO.FALSE );
               ( ( VendorServiceVO ) vendorServiceVOObject ).setModifyBy( vendorVO.getModifyBy() );
               ( ( VendorServiceVO ) vendorServiceVOObject ).setModifyDate( new Date() );
               this.vendorServiceDao.updateVendorService( ( ( VendorServiceVO ) vendorServiceVOObject ) );
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
   public List< VendorDTO > getVendorDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��DTO�б����
      final List< VendorDTO > vendorDTOs = new ArrayList< VendorDTO >();

      final VendorVO vendorVO = new VendorVO();
      vendorVO.setAccountId( accountId );
      // vendorVO.setStatus( VendorVO.TRUE );
      // ��ȡ������Ч��Vendor
      final List< Object > vendorVOs = ( ( VendorDao ) getDao() ).getVendorVOsByCondition( vendorVO );

      // ����VendorVO�б�
      if ( vendorVOs != null && vendorVOs.size() > 0 )
      {
         for ( Object vendorVOObject : vendorVOs )
         {
            // ��ʼ��VendorDTO����
            final VendorDTO vendorDTO = new VendorDTO();

            // װ��VendorVO
            vendorDTO.setVendorVO( ( VendorVO ) vendorVOObject );

            // ��ȡVendorContactVO List
            final List< Object > vendorContactVOs = this.vendorContactDao.getVendorContactVOsByVendorId( vendorDTO.getVendorVO().getVendorId() );

            // װ��VendorContactVO
            if ( vendorContactVOs != null && vendorContactVOs.size() > 0 )
            {
               for ( Object vendorContactVOObject : vendorContactVOs )
               {
                  vendorDTO.getVendorContactVOs().add( ( VendorContactVO ) vendorContactVOObject );
               }
            }

            // ��ȡVendorServiceVO List
            final List< Object > vendorServiceVOs = this.vendorServiceDao.getVendorServiceVOsByVendorId( vendorDTO.getVendorVO().getVendorId() );

            // װ��VendorServiceVO
            if ( vendorServiceVOs != null && vendorServiceVOs.size() > 0 )
            {
               for ( Object vendorServiceVOObject : vendorServiceVOs )
               {
                  vendorDTO.getVendorServiceVOs().add( ( VendorServiceVO ) vendorServiceVOObject );
               }
            }

            vendorDTOs.add( vendorDTO );
         }
      }
      return vendorDTOs;
   }

   @Override
   public List< Object > getVendorBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorBaseViewsByAccountId( accountId );
   }

   @Override
   public List< Object > getVendorVOsBySBHeaderId( String headerId ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOsBySBHeaderId( headerId );
   }

   @Override
   public VendorDTO getVendorDTOByCondition( final VendorVO vendorVO ) throws KANException
   {
      // ��õ�ǰ�˻���Ӧ��DTO�б�
      final List< VendorDTO > vendorDTOs = getVendorDTOsByAccountId( vendorVO.getAccountId() );
      if ( vendorDTOs != null && vendorDTOs.size() > 0 )
      {
         for ( VendorDTO vendorDTO : vendorDTOs )
         {
            if ( vendorDTO.getVendorVO().getVendorId().equals( vendorVO.getVendorId() ) )
            {
               return vendorDTO;
            }
         }
      }
      return null;
   }

   @Override
   public int submitVendor( final VendorVO vendorVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( vendorVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( vendorVO );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( vendorVO );
         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( vendorVO.getStatus() != null && !vendorVO.getStatus().trim().equals( "3" ) )
            {
               // ״̬��Ϊ�����
               vendorVO.setStatus( "2" );
               updateVendor( vendorVO );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submitVendor" );
            historyVO.setObjectId( vendorVO.getVendorId() );

            // ��׼״̬
            vendorVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( vendorVO ).toString();

            // �˻�״̬
            vendorVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( vendorVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, vendorVO );

            return -1;
         }
         // û�й�����
         else
         {
            vendorVO.setStatus( "3" );
            return updateVendor( vendorVO );
         }
      }

      return updateVendor( vendorVO );
   }

   public HistoryVO generateHistoryVO( final VendorVO vendorVO ) throws KANException
   {
      HistoryVO history = vendorVO.getHistoryVO();
      history.setAccessAction( VendorAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( VendorAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getVendorVOByVendorId" );
      history.setAccountId( vendorVO.getAccountId() );
      history.setObjectType( "2" );
      history.setNameZH( vendorVO.getNameZH() );
      history.setNameEN( vendorVO.getNameEN() );

      return history;
   }

   @Override
   public List< Object > getVendorVOsByCondition( final VendorVO vendorVO ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOsByCondition( vendorVO );
   }

   @Override
   public List< Object > getVendorVOsBySBSolutionHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOsBySBSolutionHeaderVO( socialBenefitSolutionHeaderVO );
   }
}
