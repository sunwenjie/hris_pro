package com.kan.hro.service.impl.biz.sb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.vendor.VendorContactDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.web.actions.biz.sb.SBAction;

public class SBBatchServiceImpl extends ContextService implements SBBatchService
{

   // ע��SBHeaderDao
   private SBHeaderDao sbHeaderDao;

   // ע��SBDetailDao
   private SBDetailDao sbDetailDao;

   // ע��EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   // ע��VendorContactDao
   private VendorContactDao vendorContactDao;

   // ע��VendorDao
   private VendorDao vendorDao;

   public SBHeaderDao getSbHeaderDao()
   {
      return sbHeaderDao;
   }

   public void setSbHeaderDao( SBHeaderDao sbHeaderDao )
   {
      this.sbHeaderDao = sbHeaderDao;
   }

   public SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public final EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public final void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public VendorContactDao getVendorContactDao()
   {
      return vendorContactDao;
   }

   public void setVendorContactDao( VendorContactDao vendorContactDao )
   {
      this.vendorContactDao = vendorContactDao;
   }

   public VendorDao getVendorDao()
   {
      return vendorDao;
   }

   public void setVendorDao( VendorDao vendorDao )
   {
      this.vendorDao = vendorDao;
   }

   @Override
   public PagedListHolder getSBBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBBatchDao sbBatchDao = ( SBBatchDao ) getDao();
      SBBatchVO sbBatchVO = ( SBBatchVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( sbBatchDao.countSBBatchVOsByCondition( sbBatchVO ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbBatchDao.getSBBatchVOsByCondition( sbBatchVO, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbBatchDao.getSBBatchVOsByCondition( sbBatchVO ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbBatchVOObject : pagedListHolder.getSource() )
         {
            final SBBatchVO sbBatch = ( SBBatchVO ) sbBatchVOObject;
            sbBatch.setRulePublic( sbBatchVO.getRulePublic() );
            sbBatch.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
            sbBatch.setRulePositionIds( sbBatchVO.getRulePositionIds() );
            sbBatch.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
            sbBatch.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
            sbBatch.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );
            fetchSBBatchVO( sbBatch );
         }
      }

      return pagedListHolder;
   }

   /**  
    * FetchSBBatchVO
    *	���㹫˾��������ֵ�ϼ�(ָ������)
    *	@param pagedListHolder
    *	@throws KANException
    */
   private void fetchSBBatchVO( final SBBatchVO sbBatchVO ) throws KANException
   {
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByBatchId( sbBatchVO );

      // ��ʼ�������Ĺ�Ա���� (ȥ�ظ� )
      final Set< MappingVO > employeeMappingVOSet = new HashSet< MappingVO >();

      // �������ݺϼ�
      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // ��ʼ���ϼ�ֵ
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // ����ǡ��½���������׼����Ӷ�Ӧ״̬Ա��
            if ( "1".equals( sbBatchVO.getAdditionalStatus() ) || "2".equals( sbBatchVO.getAdditionalStatus() ) )
            {
               if ( sbDetailVO.getStatus().equals( sbBatchVO.getAdditionalStatus() ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( sbDetailVO.getEmployeeId() );
                  mappingVO.setMappingValue( sbDetailVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( sbDetailVO.getEmployeeNameEN() );
                  employeeMappingVOSet.add( mappingVO );
               }
            }
            // ���������ӡ�ȷ�ϡ������ύ�������ѽ��㡱״̬Ա��
            else
            {
               if ( !"1".equals( sbDetailVO.getStatus() ) && !"2".equals( sbDetailVO.getStatus() ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( sbDetailVO.getEmployeeId() );
                  mappingVO.setMappingValue( sbDetailVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( sbDetailVO.getEmployeeNameEN() );
                  employeeMappingVOSet.add( mappingVO );
               }
            }

            // �����״̬���������������
            if ( sbDetailVO.getStatus().equals( sbBatchVO.getAdditionalStatus() ) )
            {
               amountCompany = amountCompany.add( new BigDecimal( sbDetailVO.getAmountCompany() ) );
               amountPersonal = amountPersonal.add( new BigDecimal( sbDetailVO.getAmountPersonal() ) );
            }
         }
         sbBatchVO.setAmountCompany( amountCompany.toString() );
         sbBatchVO.setAmountPersonal( amountPersonal.toString() );

         final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >( employeeMappingVOSet );
         sbBatchVO.setEmployees( employeeMappingVOs );
      }

   }

   /**  
    * countAmount
    * ���㹫˾��������ֵ�ϼ�(ָ��SBHeaderVO)
    * @param sbHeaderVO
    * @throws KANException
    */
   private void countAmount( SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );

      // �������ݺϼ�
      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // ��ʼ���ϼ�ֵ
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;
            amountCompany = amountCompany.add( new BigDecimal( sbDetailVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( sbDetailVO.getAmountPersonal() ) );
         }
         sbHeaderVO.setAmountCompany( amountCompany.toString() );
         sbHeaderVO.setAmountPersonal( amountPersonal.toString() );
      }

   }

   @Override
   public List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException
   {
      List< Object > sbBatchObjects = ( ( SBBatchDao ) getDao() ).getSBBatchVOsByCondition( sbBatchVO );

      if ( sbBatchObjects != null && sbBatchObjects.size() > 0 )
      {
         for ( Object sbBatchVOObject : sbBatchObjects )
         {
            SBBatchVO tempSBBatchVO = ( SBBatchVO ) sbBatchVOObject;
            tempSBBatchVO.setRulePublic( sbBatchVO.getRulePublic() );
            tempSBBatchVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
            tempSBBatchVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
            tempSBBatchVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
            tempSBBatchVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
            tempSBBatchVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );
            // ����ϼ�ֵ
            fetchSBBatchVO( tempSBBatchVO );
         }
      }

      return sbBatchObjects;
   }

   @Override
   public SBBatchVO getSBBatchVOByBatchId( final String batchId ) throws KANException
   {
      final SBBatchVO sbBatchVO = ( ( SBBatchDao ) getDao() ).getSBBatchVOByBatchId( batchId );

      if ( sbBatchVO != null )
      {
         // ����ϼ�ֵ
         fetchSBBatchVO( sbBatchVO );
      }

      return sbBatchVO;
   }

   @Override
   public int updateSBBatch( final SBBatchVO sbBatchVO ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).updateSBBatch( sbBatchVO );
   }

   @Override
   public int insertSBBatch( final SBBatchVO sbBatchVO ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).insertSBBatch( sbBatchVO );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-04
   public int rollback( final SBBatchVO sbBatchVO, final Map< String, String > statusArgs ) throws KANException
   {
      int rows = 0;
      final String sbStatusNoSocialBenefit = "0";
      final String sbStatusNormal = "3";

      try
      {
         // ��������
         startTransaction();

         // ���PageFlag��ѡ����Id��״̬
         final String pageFlag = sbBatchVO.getPageFlag();
         final String selectedIds = sbBatchVO.getSelectedIds();
         final String status = sbBatchVO.getStatus();
         final String statusAdd = statusArgs.get( "statusAdd" );
         final String statusBack = statusArgs.get( "statusBack" );

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            if ( selectedIds != null && !selectedIds.trim().isEmpty() )
            {
               // ����ID����
               final String[] selectedIdArray = selectedIds.split( "," );

               rows = selectedIdArray.length;

               String[] ids = new String[ rows ];

               for ( int i = 0; i < selectedIdArray.length; i++ )
               {
                  ids[ i ] = KANUtil.decodeStringFromAjax( selectedIdArray[ i ] );
               }

               if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
               {
                  if ( StringUtils.isNotEmpty( statusAdd ) && sbStatusNoSocialBenefit.equals( statusAdd ) )
                  {
                     ( ( SBBatchDao ) getDao() ).updateSBStatusTONoSocialBenefitByBatchId( ids );
                  }
                  if ( StringUtils.isNotEmpty( statusBack ) && sbStatusNormal.equals( statusBack ) )
                  {
                     ( ( SBBatchDao ) getDao() ).updateSBStatusTONormalByBatchId( ids );
                  }
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
               {
                  if ( StringUtils.isNotEmpty( statusAdd ) && sbStatusNoSocialBenefit.equals( statusAdd ) )
                  {
                     sbHeaderDao.updateSBStatusTONoSocialBenefitByHeaderId( ids );
                  }
                  if ( StringUtils.isNotEmpty( statusBack ) && sbStatusNormal.equals( statusBack ) )
                  {
                     sbHeaderDao.updateSBStatusTONormalByHeaderId( ids );
                  }
               }

               // ��ʼ��BatchId�б�
               final List< String > batchIds = new ArrayList< String >();

               // ����
               for ( String encodedSelectedId : selectedIdArray )
               {
                  // ��ʼ��BatchId
                  String batchId = null;
                  // ����
                  final String selectedId = KANUtil.decodeStringFromAjax( encodedSelectedId );

                  // ����PageFlag�˻�
                  if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
                  {
                     batchId = rollbackBatch( selectedId, status );
                  }
                  else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
                  {
                     SBHeaderVO headerVO = new SBHeaderVO();
                     headerVO.setHeaderId( selectedId );
                     batchId = rollbackHeader( headerVO, status );
                  }

                  if ( KANUtil.filterEmpty( batchId ) != null && !batchIds.contains( batchId ) )
                  {
                     batchIds.add( batchId );
                  }
               }

               // �����˻��丸����״̬
               if ( batchIds != null && batchIds.size() > 0 )
               {
                  for ( String batchId : batchIds )
                  {
                     tryRollbackBatch( batchId );
                  }
               }
            }
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   /**  
    * �˻�����
    *	
    *	@param selectId
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   private String rollbackBatch( final String batchId, final String status ) throws KANException
   {
      // ��ʼ��SBHeaderVO�б�
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( batchId );

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         // ����
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            // ɾ��SBHeaderVO
            rollbackHeader( ( ( SBHeaderVO ) sbHeaderVOObject ), status );
         }
      }

      return batchId;
   }

   /**  
    * �˻��籣����
    *	
    *	@param sbBatchVO
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   private String rollbackHeader( final SBHeaderVO headerVO, final String status ) throws KANException
   {
      // ��ʼ��SBDetailVO�б�
      final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByHeaderId( headerVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // ����
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            // ɾ��SBDetailVO
            rollbackDetail( ( ( SBDetailVO ) sbDetailVOObject ).getDetailId(), status );
         }
      }

      // ɾ��SBHeaderVO
      final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( headerVO.getHeaderId() );

      if ( sbHeaderVO != null )
      {
         return sbHeaderVO.getBatchId();
      }

      return "";
   }

   /**  
    * �˻��籣������ϸ
    *	
    *	@param selectId
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   private String rollbackDetail( final String detailId, final String status ) throws KANException
   {
      final SBDetailVO sbDetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( detailId );

      if ( sbDetailVO != null && sbDetailVO.getStatus() != null && sbDetailVO.getStatus().equals( status ) && ( Integer.parseInt( sbDetailVO.getStatus() ) ) <= 2 )
      {
         // ɾ��SBDetailVO
         this.getSbDetailDao().deleteSBDetail( detailId );
         final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbDetailVO.getHeaderId() );

         if ( sbHeaderVO != null )
         {
            return sbHeaderVO.getBatchId();
         }
      }

      return "";
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-09
   public int insertSBBatch( final SBBatchVO sbBatchVO, final List< ServiceContractDTO > serviceContractDTOs ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         // �������������������ݿ�
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            // ����
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               // ���SBDTO List
               final List< SBDTO > sbDTOs = serviceContractDTO.getSbDTOs();

               if ( sbDTOs != null && sbDTOs.size() > 0 )
               {
                  if ( rows == 0 )
                  {
                     // �������
                     insertSBBatch( sbBatchVO );

                     // һ��������ӳɹ�
                     rows = 1;
                  }

                  for ( SBDTO sbDTO : sbDTOs )
                  {
                     // ���SBHeaderVO
                     final SBHeaderVO sbHeaderVO = sbDTO.getSbHeaderVO();

                     // ���SBDetailVO List
                     final List< SBDetailVO > sbdetailVOs = sbDTO.getSbDetailVOs();

                     if ( sbHeaderVO != null && sbdetailVOs != null && sbdetailVOs.size() > 0 )
                     {
                        // ����BatchId
                        sbHeaderVO.setBatchId( sbBatchVO.getBatchId() );
                        // ����SBHeaderVO
                        this.getSbHeaderDao().insertSBHeader( sbHeaderVO );

                        // ����SBDetailVO List
                        for ( SBDetailVO sbDetailVO : sbdetailVOs )
                        {
                           // ����HeaderId
                           sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
                           // ����SBDetailVO
                           this.getSbDetailDao().insertSBDetail( sbDetailVO );
                        }
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( sbBatchVO.getBatchId() ) != null )
         {
            // ����ִ�н���ʱ��
            sbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            sbBatchVO.setDeleted( SBBatchVO.TRUE );
            sbBatchVO.setStatus( SBBatchVO.TRUE );
            // �޸�����
            updateSBBatch( sbBatchVO );
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-04
   public Integer submit( final SBBatchVO sbBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ù�ѡID����
         final String selectedIds = sbBatchVO.getSelectedIds();
         // ��� PageFlag
         final String pageFlag = sbBatchVO.getPageFlag();
         // ���ActionFlag
         final String actionFlag = sbBatchVO.getSubAction();
         // ���AccountId
         final String accountId = sbBatchVO.getAccountId();

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = selectedIds.split( "," );
            // �ύ��¼��
            submitRows = selectedIdArray.length;

            // ��ʼ��״̬�ַ���
            String status = "0";
            // �������׼
            if ( actionFlag.equalsIgnoreCase( BaseAction.APPROVE_OBJECTS ) )
            {
               status = "2";
            }
            // �����ȷ��
            else if ( actionFlag.equalsIgnoreCase( BaseAction.CONFIRM_OBJECTS ) )
            {
               status = "3";
            }
            // ������ύ
            else if ( actionFlag.equalsIgnoreCase( BaseAction.SUBMIT_OBJECTS ) )
            {
               status = "4";
            }
            final SBBatchDao sbBatchDao = ( SBBatchDao ) getDao();
            // ��ʼ��BatchId�б�
            final List< String > batchIds = new ArrayList< String >();
            sbBatchVO.setStatus( status );
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               // ��ʼ��BatchId
               String batchId = null;
               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // ����PageFlag�ύ
               if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
               {
                 
                  //�����ύshi
//                  batchId = submitBatch( sbBatchVO, accountId, selectId, status, sbBatchVO.getModifyBy() );
                  sbBatchVO.setBatchId( selectId );
                  sbBatchVO.setHeaderId( null );
                  sbBatchVO.setDetailId( null );
                  batchId = selectId;
                  sbBatchDao.updateSBDetailStatus(sbBatchVO);
                  sbBatchDao.updateSBHeaderStatus(sbBatchVO);
                  sbBatchDao.updateSBBatchStatus(sbBatchVO);
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
               {
                  sbBatchVO.setHeaderId( selectId );
                  sbBatchVO.setBatchId( null );
                  sbBatchVO.setDetailId( null );
                  sbBatchDao.updateSBHeaderStatus(sbBatchVO);
                  sbBatchDao.updateSBDetailStatus(sbBatchVO);
                  batchId = sbBatchDao.getSBBatchId( sbBatchVO );
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
               {
                  sbBatchVO.setHeaderId( null );
                  sbBatchVO.setBatchId( null );
                  sbBatchVO.setDetailId( selectId );
                  sbBatchDao.updateSBDetailStatus(sbBatchVO);
                  batchId = sbBatchDao.getSBBatchId( sbBatchVO );
               }

               if ( KANUtil.filterEmpty( batchId ) != null && !batchIds.contains( batchId ) )
               {
                  batchIds.add( batchId );
               }
            }

            // ���Ϊ����׼��״̬�����ʼ�����Ӧ��
            if ( KANUtil.filterEmpty( status ) != null && status.equals( "2" ) )
            {
               // ��ù����Ĺ�Ӧ��ID����
               final List< String > vendorIds = getVendorIds( pageFlag, selectedIdArray );

               if ( vendorIds != null && vendorIds.size() > 0 )
               {
                  for ( String vendorId : vendorIds )
                  {
                     // ���VendorVO
                     final VendorVO vendorVO = this.getVendorDao().getVendorVOByVendorId( vendorId );
                     // ��ʼ��VendorContactVO
                     VendorContactVO vendorContactVO = null;
                     String reception = "";

                     // ���Ĭ����ϵ�˴���
                     if ( vendorVO != null && KANUtil.filterEmpty( vendorVO.getMainContact(), "0" ) != null )
                     {
                        vendorContactVO = this.getVendorContactDao().getVendorContactVOByVendorContactId( vendorVO.getMainContact() );

                        // ���Ĭ����ϵ���������
                        if ( vendorContactVO != null && KANUtil.filterEmpty( vendorContactVO.getBizEmail() ) != null )
                        {
                           reception = vendorContactVO.getBizEmail();
                        }
                        // ��Ĭ����ϵ��
                        else
                        {
                           reception = vendorVO.getEmail();
                        }
                     }

                     if ( KANUtil.filterEmpty( reception ) != null )
                     {
                        new Mail( accountId, reception, ContentUtil.getMailContent_SBNotice_Vendor( new Object[] { vendorContactVO, vendorVO } ) ).send( true );
                     }
                  }
               }
            }

            // ���Ը����丸����״̬
            if ( batchIds != null && batchIds.size() > 0 )
            {
               for ( String batchId : batchIds )
               {
                  trySubmitBatch( batchId, status, sbBatchVO.getModifyBy() );
               }
            }
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return submitRows;
   }

   /**  
    * FetchVendorIds
    * ��ù�����Ӧ��ID����
    *	@param sbBatchVO
    * @param selectedIdArray 
    *	@return
    * @throws KANException 
    */
   private List< String > getVendorIds( final String pageFlag, final String[] selectedIdArray ) throws KANException
   {
      // ��ʼ������ֵ
      List< String > vendorIds = new ArrayList< String >();

      // ����ǰ�������׼
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
      {
         for ( String selectedId : selectedIdArray )
         {
            final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );

            if ( sbHeaderVOs != null & sbHeaderVOs.size() > 0 )
            {
               for ( Object sbHeaderVOObject : sbHeaderVOs )
               {
                  final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
                  if ( KANUtil.filterEmpty( sbHeaderVO.getVendorId() ) != null )
                  {
                     // ���vendorId�����������
                     if ( !isVendorIdExist( sbHeaderVO.getVendorId(), vendorIds ) )
                     {
                        vendorIds.add( sbHeaderVO.getVendorId() );
                     }
                  }
               }
            }
         }
      }
      // ����ǰ�Header��׼
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
      {
         for ( String selectedId : selectedIdArray )
         {
            final SBHeaderVO sbHeaderVO = this.sbHeaderDao.getSBHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

            if ( KANUtil.filterEmpty( sbHeaderVO.getVendorId() ) != null )
            {
               // ���vendorId�����������
               if ( !isVendorIdExist( sbHeaderVO.getVendorId(), vendorIds ) )
               {
                  vendorIds.add( sbHeaderVO.getVendorId() );
               }
            }
         }
      }
      // ����ǰ�Detail��׼
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
      {
         for ( String selectedId : selectedIdArray )
         {
            final SBDetailVO sbdetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
            final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbdetailVO.getHeaderId() );

            // ���vendorId�����������
            if ( KANUtil.filterEmpty( sbHeaderVO.getVendorId() ) != null )
            {
               if ( !isVendorIdExist( sbHeaderVO.getVendorId(), vendorIds ) )
               {
                  vendorIds.add( sbHeaderVO.getVendorId() );
               }
            }
         }
      }

      return vendorIds;
   }

   /**  
    * IsVendorIdExist
    *	�жϹ�Ӧ��ID�Ƿ����
    *	@param vendorId
    *	@param vendorIds
    *	@return
    */
   private boolean isVendorIdExist( final String vendorId, final List< String > vendorIds )
   {
      for ( String tempVendorId : vendorIds )
      {
         if ( vendorId.equals( tempVendorId ) )
         {
            return true;
         }
      }
      return false;
   }

   // �����ύ���� - �ύ�Ӷ��󵫲�����������Ƿ��ύ�����ʹ��
   // Reviewed by Kevin Jin at 2013-11-04
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // ��ʼ��SBBatchVO
         final SBBatchVO sbBatchVO = ( ( SBBatchDao ) getDao() ).getSBBatchVOByBatchId( batchId );
         // ��ʼ��SBHeaderVO�б�
         final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( batchId );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;

            // ����
            for ( Object sbHeaderVOObject : sbHeaderVOs )
            {
               final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

               // ��ʼ��SBDetailVO�б�
               final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );

               if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
               {
                  int detailFlag = 0;
                  // ��ʼ���������ж��Ƿ�״̬����Ҫ�޸ĵ�״ֵ̬������ǲ��޸�״̬��
                  int upperCount = 0;
                  double amountCompany = 0;
                  double amountPersonal = 0;

                  // ����
                  for ( Object sbDetailVOObject : sbDetailVOs )
                  {
                     final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

                     if ( KANUtil.filterEmpty( sbDetailVO.getStatus() ) != null && Integer.valueOf( sbDetailVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                        if ( Integer.valueOf( sbDetailVO.getStatus() ) > Integer.valueOf( status ) )
                        {
                           upperCount++;
                        }
                     }

                     // �ۼ��籣��˾���ֺ͸��˲���
                     amountCompany = amountCompany
                           + Double.valueOf( KANUtil.filterEmpty( sbDetailVO.getAmountCompany() ) != null ? KANUtil.filterEmpty( sbDetailVO.getAmountCompany() ) : "0" );
                     amountPersonal = amountPersonal
                           + Double.valueOf( KANUtil.filterEmpty( sbDetailVO.getAmountPersonal() ) != null ? KANUtil.filterEmpty( sbDetailVO.getAmountPersonal() ) : "0" );
                  }

                  if ( detailFlag == sbDetailVOs.size() )
                  {
                     // ����籣������ϸ��ȫ������Ҫ���ĵ�״̬��״̬����ȫ����Ҫ�޸ĵ�״ֵ̬���޸��籣������״̬
                     if ( upperCount != sbDetailVOs.size() )
                     {
                        sbHeaderVO.setStatus( status );
                     }
                     sbHeaderVO.setModifyBy( userId );
                     sbHeaderVO.setModifyDate( new Date() );

                     // ����ǲ��ɵ��籣��������
                     if ( KANUtil.filterEmpty( status ) != null && "3".equals( status ) && !"7".equals( sbHeaderVO.getSbStatus() ) )
                     {
                        final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );

                        boolean updated = false;

                        // �����걨�ӱ���״̬��Ϊ���������ɡ�
                        if ( employeeContractSBVO.getStatus().equals( "2" ) )
                        {
                           employeeContractSBVO.setStatus( "3" );
                           sbHeaderVO.setSbStatus( "3" );
                           updated = true;
                        }
                        // �����걨�˱���״̬��Ϊ�����˱��� - ���˲��ֺ͹�˾������ֵ�Ĳ��仯
                        else if ( employeeContractSBVO.getStatus().equals( "5" ) && amountCompany == 0 && amountPersonal == 0 )
                        {
                           employeeContractSBVO.setStatus( "6" );
                           sbHeaderVO.setSbStatus( "6" );
                           updated = true;
                        }

                        if ( updated )
                        {
                           this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
                        }
                     }

                     this.getSbHeaderDao().updateSBHeader( sbHeaderVO );
                     submitRows++;
                  }
               }

               if ( sbHeaderVO.getStatus() != null && !sbHeaderVO.getStatus().isEmpty() && Integer.valueOf( sbHeaderVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }
            }

            // ����籣������ȫ������Ҫ���ĵ�״̬���޸��籣���ε�״̬
            if ( headerFlag == sbHeaderVOs.size() )
            {
               sbBatchVO.setStatus( status );
               sbBatchVO.setModifyBy( userId );
               sbBatchVO.setModifyDate( new Date() );
               ( ( SBBatchDao ) getDao() ).updateSBBatch( sbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   // �����˻����� - �˻��Ӷ��󵫲�����������Ƿ��˻ص����ʹ��
   // Reviewed by Kevin Jin at 2013-11-04
   private int tryRollbackBatch( final String batchId ) throws KANException
   {
      int rollbackRows = 0;

      // ��ʼ��SBBatchVO
      final SBBatchVO sbBatchVO = ( ( SBBatchDao ) getDao() ).getSBBatchVOByBatchId( batchId );
      // ��ʼ��SBHeaderVO�б�
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByBatchId( batchId );

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         int headerFlag = 0;

         // ����
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // ��ʼ��SBDetailVO�б�
            final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );

            if ( sbDetailVOs == null || sbDetailVOs.size() == 0 )
            {
               this.sbHeaderDao.deleteSBHeader( sbHeaderVO.getHeaderId() );
               headerFlag++;
               rollbackRows++;
            }
         }

         if ( sbHeaderVOs.size() == headerFlag )
         {
            ( ( SBBatchDao ) getDao() ).deleteSBBatch( sbBatchVO.getBatchId() );
            rollbackRows++;
         }
      }
      else
      {
         ( ( SBBatchDao ) getDao() ).deleteSBBatch( sbBatchVO.getBatchId() );
         rollbackRows++;
      }

      return rollbackRows;
   }

   /**  
    * GetSBDTOsByCondition
    *	 ���SBDTO
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException
   {
      // ��ò�ѯ����
      final SBBatchVO sbBatchVO = ( SBBatchVO ) pagedListHolder.getObject();
      // ��� PageFlag
      final String pageFlag = sbBatchVO.getPageFlag();
      // ��ʼ��List SBDTO
      List< Object > sbDTOs = new ArrayList< Object >();

      // ����PageFlag�ύ
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
      {
         sbDTOs = getBatchSBDTO( sbBatchVO );
      }
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
      {
         sbDTOs = getHeaderSBDTO( sbBatchVO );
      }

      pagedListHolder.setSource( sbDTOs );

      return pagedListHolder;
   }

   /**  
    * GetBatchSBDTO
    *	������λ��SBDTO����
    *	@param sbBatchVO
    *	@return
    * @throws KANException 
    */
   private List< Object > getBatchSBDTO( final SBBatchVO sbBatchVO ) throws KANException
   {
      // ��ù�ѡID����
      final String selectedIds = sbBatchVO.getSelectedIds();
      // ��ʼ��ѡ��ID����
      String[] selectedIdArray = { "" };

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         selectedIdArray = selectedIds.split( "," );
      }

      for ( String selectId : selectedIdArray )
      {
         // ��ʼ��SBDTO List
         final List< Object > sbDTOs = new ArrayList< Object >();
         // ��ʼ����ѯ����
         SBBatchVO tempSBBatchVO = new SBBatchVO();
         // ��������ֵ
         tempSBBatchVO.setBatchId( KANUtil.decodeStringFromAjax( selectId ) );
         tempSBBatchVO.setAccountId( sbBatchVO.getAccountId() );
         tempSBBatchVO.setEntityId( sbBatchVO.getEntityId() );
         tempSBBatchVO.setBusinessTypeId( sbBatchVO.getBusinessTypeId() );
         tempSBBatchVO.setCityId( sbBatchVO.getCityId() );
         tempSBBatchVO.setOrderId( sbBatchVO.getOrderId() );
         tempSBBatchVO.setContractId( sbBatchVO.getContractId() );
         tempSBBatchVO.setMonthly( sbBatchVO.getMonthly() );
         tempSBBatchVO.setCorpId( sbBatchVO.getCorpId() );

         tempSBBatchVO.setRulePublic( sbBatchVO.getRulePublic() );
         tempSBBatchVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
         tempSBBatchVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
         tempSBBatchVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
         tempSBBatchVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
         tempSBBatchVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

         // ��ѯ��������������SBBatchVO����
         final List< Object > sbBatchVOs = ( ( SBBatchDao ) getDao() ).getSBBatchVOsByCondition( tempSBBatchVO );

         // ��ʼ��SBHeaderVO List
         List< Object > sbHeaderVOs = new ArrayList< Object >();

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            for ( Object sbBatchVOObject : sbBatchVOs )
            {
               // ��ʼ����ѯ����
               final SBHeaderVO sbHeaderVO = new SBHeaderVO();
               // ��������ֵ
               sbHeaderVO.setBatchId( ( ( SBBatchVO ) sbBatchVOObject ).getBatchId() );
               sbHeaderVO.setStatus( sbBatchVO.getStatus() );
               sbHeaderVO.setVendorId( sbBatchVO.getVendorId() );
               sbHeaderVO.setAccountId( sbBatchVO.getAccountId() );
               sbHeaderVO.setCorpId( sbBatchVO.getCorpId() );

               sbHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
               sbHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
               sbHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
               sbHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
               sbHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
               sbHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );
               sbHeaderVOs.addAll( this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO ) );
            }
         }

         // ��ʼ���������п�Ŀ�ļ���
         final List< ItemVO > items = new ArrayList< ItemVO >();

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            for ( Object sbHeaderVOObject : sbHeaderVOs )
            {
               // ��ʼ���������
               final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

               tempSBHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
               tempSBHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
               tempSBHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
               tempSBHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
               tempSBHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
               tempSBHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

               // ����ϼ�
               countAmount( tempSBHeaderVO );
               // ��ʼ��SBDTO
               final SBDTO sbDTO = new SBDTO();

               // װ��SBHeaderVO
               sbDTO.setSbHeaderVO( tempSBHeaderVO );

               // װ��SBDetailVO List
               fetchSBDetail( sbDTO, tempSBHeaderVO, sbBatchVO.getStatus(), items );

               Object sbDTOObject = sbDTO;
               sbDTOs.add( sbDTOObject );
            }

            // SBDTO�����ڿ�Ŀ��ӿ�ֵ
            if ( sbDTOs != null && sbDTOs.size() > 0 )
            {
               fillSBDTOs( sbDTOs, items );
            }

            return sbDTOs;
         }
      }

      return null;
   }

   /**  
    * GetHeaderSBDTO
    *	�������λ��SBDTO����
    *	@param sbBatchVO
    *	@return
    *	@throws KANException
    */
   private List< Object > getHeaderSBDTO( final SBBatchVO sbBatchVO ) throws KANException
   {
      // ��ù�ѡID����
      final String selectedIds = sbBatchVO.getSelectedIds();
      // ��ʼ��SBDTO List
      final List< Object > sbDTOs = new ArrayList< Object >();
      // ��ʼ���������п�Ŀ�ļ���
      final List< ItemVO > items = new ArrayList< ItemVO >();

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         final String[] selectedIdArray = selectedIds.split( "," );
         for ( String selectId : selectedIdArray )
         {
            // ��ʼ����ѯ����
            final SBHeaderVO sbHeaderVO = new SBHeaderVO();
            // ��������ֵ
            sbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( sbBatchVO.getBatchId() ) );
            sbHeaderVO.setHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
            sbHeaderVO.setStatus( sbBatchVO.getStatus() );
            sbHeaderVO.setAccountId( sbBatchVO.getAccountId() );
            sbHeaderVO.setCorpId( sbBatchVO.getCorpId() );
            sbHeaderVO.setVendorId( sbBatchVO.getVendorId() );

            sbHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
            sbHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
            sbHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
            sbHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
            sbHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
            sbHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

            // ��ѡ���籣״̬���� Add by siuxia
            if ( sbBatchVO.getSbStatusArray() != null && sbBatchVO.getSbStatusArray().length > 0 )
            {
               sbHeaderVO.setSbStatus( KANUtil.toJasonArray( sbBatchVO.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
            }
            // ��ʼ��SBHeaderVO List
            final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO );

            if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
            {
               for ( Object sbHeaderVOObject : sbHeaderVOs )
               {
                  // ��ʼ���������
                  final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

                  tempSBHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
                  tempSBHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
                  tempSBHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
                  tempSBHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
                  tempSBHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
                  tempSBHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

                  // ����ϼ�
                  countAmount( tempSBHeaderVO );
                  // ��ʼ��SBDTO
                  final SBDTO sbDTO = new SBDTO();

                  // װ��SBHeaderVO
                  sbDTO.setSbHeaderVO( tempSBHeaderVO );

                  // װ��SBDetailVO List
                  fetchSBDetail( sbDTO, tempSBHeaderVO, sbHeaderVO.getStatus(), items );

                  Object sbDTOObject = sbDTO;
                  sbDTOs.add( sbDTOObject );
               }
            }

         }
      }
      else
      {
         // ��ʼ����ѯ����
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();
         // ��������ֵ
         sbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( sbBatchVO.getBatchId() ) );
         sbHeaderVO.setStatus( sbBatchVO.getStatus() );
         sbHeaderVO.setAccountId( sbBatchVO.getAccountId() );
         sbHeaderVO.setCorpId( sbBatchVO.getCorpId() );
         sbHeaderVO.setVendorId( sbBatchVO.getVendorId() );

         sbHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
         sbHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
         sbHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
         sbHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
         sbHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
         sbHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

         // ��ѡ���籣״̬���� Add by siuxia
         if ( sbBatchVO.getSbStatusArray() != null && sbBatchVO.getSbStatusArray().length > 0 )
         {
            sbHeaderVO.setSbStatus( KANUtil.toJasonArray( sbBatchVO.getSbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         // ��ʼ��SBHeaderVO List
         final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            for ( Object sbHeaderVOObject : sbHeaderVOs )
            {
               // ��ʼ���������
               final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

               tempSBHeaderVO.setRulePublic( sbBatchVO.getRulePublic() );
               tempSBHeaderVO.setRulePrivateIds( sbBatchVO.getRulePrivateIds() );
               tempSBHeaderVO.setRulePositionIds( sbBatchVO.getRulePositionIds() );
               tempSBHeaderVO.setRuleBranchIds( sbBatchVO.getRuleBranchIds() );
               tempSBHeaderVO.setRuleBusinessTypeIds( sbBatchVO.getRuleBusinessTypeIds() );
               tempSBHeaderVO.setRuleEntityIds( sbBatchVO.getRuleEntityIds() );

               // ����ϼ�
               countAmount( tempSBHeaderVO );
               // ��ʼ��SBDTO
               final SBDTO sbDTO = new SBDTO();

               // װ��SBHeaderVO
               sbDTO.setSbHeaderVO( tempSBHeaderVO );

               // װ��SBDetailVO List
               fetchSBDetail( sbDTO, tempSBHeaderVO, sbHeaderVO.getStatus(), items );

               Object sbDTOObject = sbDTO;
               sbDTOs.add( sbDTOObject );
            }
         }

      }

      // SBDTO�����ڿ�Ŀ��ӿ�ֵ
      if ( sbDTOs != null && sbDTOs.size() > 0 )
      {
         fillSBDTOs( sbDTOs, items );
      }

      return sbDTOs;
   }

   // װ���籣��ϸ
   private void fetchSBDetail( final SBDTO sbDTO, SBHeaderVO sbHeaderVO, final String status, final List< ItemVO > items ) throws KANException
   {
      // ��ʼ����װ���籣��ϸ
      final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByHeaderId( sbHeaderVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // ֻ��ȡ�����������籣��ϸ
            if ( sbDetailVO.getStatus() != null && sbDetailVO.getStatus().equals( status ) )
            {
               sbDTO.getSbDetailVOs().add( ( SBDetailVO ) sbDetailVOObject );
            }

            // ��ʼ����Ŀ
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( sbDetailVO.getItemId() );
            itemVO.setItemType( sbDetailVO.getItemType() );

            // �����Ŀ�����������
            if ( !isItemExist( itemVO, items ) )
            {
               items.add( itemVO );
            }

         }
      }
   }

   // SBDTO������䲻���ڿ�Ŀ
   private void fillSBDTOs( final List< Object > sbDTOs, final List< ItemVO > items )
   {
      for ( ItemVO itemVO : items )
      {
         for ( Object sbDTOObject : sbDTOs )
         {
            final List< SBDetailVO > copyList = new ArrayList< SBDetailVO >();
            final SBDTO sbDTO = ( SBDTO ) sbDTOObject;
            // ���SBDTO��ӦSBHeaderVO��SBDetailVO����
            final SBHeaderVO sbHeaderVO = sbDTO.getSbHeaderVO();
            List< SBDetailVO > sbDetailVOs = sbDTO.getSbDetailVOs();
            // COPY�Ѵ���SBDTO����
            copyList.addAll( sbDetailVOs );

            // ���ҵ�ǰSBDetailVO�Ƿ���ڸÿ�Ŀ�������������
            fetchItemExistSbDetailVOs( itemVO, sbHeaderVO, sbDetailVOs, copyList );

            // ��������SBDTO��SBDetailVO����
            sbDTO.setSbDetailVOs( copyList );
         }
      }

   }

   /**  
    * IsItemExistSbDetailVOs
    *	�ж�ItemVO�Ƿ�����籣��ϸ����
    *	@param itemVO
    *	@param items
    *	@return
    */
   private void fetchItemExistSbDetailVOs( final ItemVO itemVO, final SBHeaderVO sbHeaderVO, final List< SBDetailVO > sbDetailVOs, final List< SBDetailVO > returnDetailVOs )
   {
      Boolean existFlag = false;

      // �жϿ�Ŀ�Ƿ����
      if ( sbDetailVOs == null || sbDetailVOs.size() == 0 )
      {
         existFlag = false;
      }
      else
      {
         for ( SBDetailVO sbDetailVO : sbDetailVOs )
         {
            if ( itemVO.getItemId().equals( sbDetailVO.getItemId() ) )
            {
               existFlag = true;
               break;
            }
         }
      }

      // �����Ŀ�����������
      if ( !existFlag )
      {
         // ��ʼ��SBDetailVO�������
         final SBDetailVO tempDetailVO = new SBDetailVO();
         tempDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
         tempDetailVO.setStatus( sbHeaderVO.getStatus() );
         tempDetailVO.setItemId( itemVO.getItemId() );
         tempDetailVO.setItemType( itemVO.getItemType() );
         returnDetailVOs.add( tempDetailVO );
      }
   }

   /**  
    * IsItemExistInItemsList
    * �жϿ�Ŀ�Ƿ�����ڿ�Ŀ����
    * @param itemId
    * @param items
    * @return
    */
   private boolean isItemExist( final ItemVO itemVO, final List< ItemVO > items )
   {
      if ( items == null || items.size() == 0 )
      {
         return false;
      }
      else
      {
         for ( ItemVO tempTemVO : items )
         {
            if ( itemVO.getItemId().equals( tempTemVO.getItemId() ) )
            {
               return true;
            }
         }
         return false;
      }
   }

   @Override
   public String getSBToApplyForMoreStatusCountByBatchIds( final String[] batchId ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).getSBToApplyForMoreStatusCountByBatchIds( batchId ) + "";
   }

   @Override
   public String getSBToApplyForResigningStatusCountByBatchIds( String[] batchId ) throws KANException
   {
      return ( ( SBBatchDao ) getDao() ).getSBToApplyForResigningStatusCountByBatchIds( batchId ) + "";
   }
   @Override
   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException
   {
      // TODO Auto-generated method stub
      // ���ActionFlag
      final String actionFlag = baseVO.getSubAction();
      //ȷ��ʱ���й�����
      if (StringUtils.isBlank( actionFlag )|| !actionFlag.equalsIgnoreCase( BaseAction.CONFIRM_OBJECTS ) )
      {
        return;
      }
      final HistoryVO history = baseVO.getHistoryVO();
      //ͨ��ִ��
      //history.setPassObject( KANUtil.toJSONObject( baseVO ).toString() );

      //�����id �����Ƕ���Զ��ŷָ��������ǹ�ѡ�Ķ��id
      String selectIdString = ( ( SBBatchVO ) baseVO ).getSelectedIds();
      if(StringUtils.isBlank( selectIdString )){
         return;
      }
      final String[] selectedIdArray = selectIdString.split( "," );
      String objectIdString = "";
      for (int i = 0 ;i<selectedIdArray.length;i++)
      {
         // ����
         objectIdString +=KANUtil.decodeStringFromAjax( selectedIdArray[i] )+",";
      }
      if(StringUtils.isNotBlank( objectIdString )&&objectIdString.length()>0){
         objectIdString = objectIdString.substring(0, objectIdString.length()-1 );
      }
      history.setObjectId( objectIdString );
      //history.setFailObject( KANUtil.toJSONObject( baseVO ).toString() );

      history.setAccessAction( SBAction.ACCESS_ACTION_CONFIRM );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( SBAction.ACCESS_ACTION_CONFIRM ) );
      history.setRightId( KANConstants.MODULE_RIGHT_CONFIRM );
      //��ʾ�����¹�����
      history.setObjectType( "3" );
      history.setServiceBean( "sbBatchService" );
      final String pageFlag = ( ( SBBatchVO ) baseVO ).getPageFlag();
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_BATCH ) )
      {
         history.setRemark4( "sbBatch" );
      }
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) ){
         history.setRemark4( "sbHeader" );
      }
      //      history.setObjectClass( OBJECT_CLASS );
      //      history.setServiceGetObjByIdMethod( "getLeaveHeaderVOByLeaveHeaderId" );

      // ��ʾ�ǹ�������
      //history.setObjectType( "2" );
      //      history.setAccountId( baseVO.getAccountId() );
      //      final String batchId = (( SBBatchVO ) baseVO ).getBatchId();
      //      history.setObjectId( batchId );
      //      leaveHeaderVO.getLeaveHeaderId() 
      //      SBBatchVO sbBatchVO = (SBBatchVO)baseVO;
      //      history.setNameZH( leaveHeaderVO.getEmployeeNameZH() );
      //      history.setNameEN( leaveHeaderVO.getEmployeeNameEN() );
      //      history.setOwner( sbBatchVO.get );
   }

}
