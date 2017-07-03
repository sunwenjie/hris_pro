package com.kan.hro.service.impl.biz.cb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.cb.CBBatchDao;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDTO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.service.inf.biz.cb.CBBatchService;
import com.kan.hro.service.inf.biz.sb.SBBatchService;

public class CBBatchServiceImpl extends ContextService implements CBBatchService
{

   // ע��CBHeaderDao
   private CBHeaderDao cbHeaderDao;

   // ע��CBDetailDao
   private CBDetailDao cbDetailDao;

   // ע��EmployeeContractCBDao
   private EmployeeContractCBDao employeeContractCBDao;

   public CBHeaderDao getCbHeaderDao()
   {
      return cbHeaderDao;
   }

   public void setCbHeaderDao( CBHeaderDao cbHeaderDao )
   {
      this.cbHeaderDao = cbHeaderDao;
   }

   public CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   public final EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public final void setEmployeeContractCBDao( EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   @Override
   public PagedListHolder getCBBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CBBatchDao cbBatchDao = ( CBBatchDao ) getDao();
      pagedListHolder.setHolderSize( cbBatchDao.countCBBatchVOsByCondition( ( CBBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( cbBatchDao.getCBBatchVOsByCondition( ( CBBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cbBatchDao.getCBBatchVOsByCondition( ( CBBatchVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object cbBatchVOObject : pagedListHolder.getSource() )
         {
            final CBBatchVO cbBatchVO = ( CBBatchVO ) cbBatchVOObject;
            fetchCBBatchVO( cbBatchVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * FetchCBBatchVO
    * ���㹫˾��������ֵ�ϼ�
    * @param pagedListHolder
    * @throws KANException
    */
   private void fetchCBBatchVO( final CBBatchVO cbBatchVO ) throws KANException
   {
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByBatchId( cbBatchVO.getBatchId() );
      // ��ʼ�������Ĺ�Ա���� ( ȥ�ظ� )
      final Set< MappingVO > employeeMappingVOSet = new HashSet< MappingVO >();
      // �������ݺϼ�
      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {

         // ��ʼ���ϼ�ֵ
         BigDecimal amountSalesCost = new BigDecimal( 0 );
         BigDecimal amountSalesPrice = new BigDecimal( 0 );
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;
            amountSalesCost = amountSalesCost.add( new BigDecimal( cbDetailVO.getAmountSalesCost() ) );
            amountSalesPrice = amountSalesPrice.add( new BigDecimal( cbDetailVO.getAmountSalesPrice() ) );

            // ����ǡ��½���������׼����Ӷ�Ӧ״̬Ա��
            if ( "1".equals( cbBatchVO.getAdditionalStatus() ) || "2".equals( cbBatchVO.getAdditionalStatus() ) )
            {
               if ( cbDetailVO.getStatus().equals( cbBatchVO.getAdditionalStatus() ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( cbDetailVO.getEmployeeId() );
                  mappingVO.setMappingValue( cbDetailVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( cbDetailVO.getEmployeeNameEN() );
                  employeeMappingVOSet.add( mappingVO );
               }
            }
            // ���������ӡ�ȷ�ϡ������ύ�������ѽ��㡱״̬Ա��
            else
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( cbDetailVO.getEmployeeId() );
               mappingVO.setMappingValue( cbDetailVO.getEmployeeNameZH() );
               mappingVO.setMappingTemp( cbDetailVO.getEmployeeNameEN() );
               employeeMappingVOSet.add( mappingVO );
            }
         }
         final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >( employeeMappingVOSet );
         cbBatchVO.setEmployees( employeeMappingVOs );
         cbBatchVO.setAmountSalesCost( amountSalesCost.toString() );
         cbBatchVO.setAmountSalesPrice( amountSalesPrice.toString() );
      }

   }

   /**  
    * countAmount
    * ���㹫˾��������ֵ�ϼ�
    * @param cbHeaderVO
    * @throws KANException
    */
   private void countAmount( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

      // �������ݺϼ�
      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         // ��ʼ���ϼ�ֵ
         BigDecimal amountSalesCost = new BigDecimal( 0 );
         BigDecimal amountSalesPrice = new BigDecimal( 0 );
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;
            amountSalesCost = amountSalesCost.add( new BigDecimal( cbDetailVO.getAmountSalesCost() ) );
            amountSalesPrice = amountSalesPrice.add( new BigDecimal( cbDetailVO.getAmountSalesPrice() ) );
         }
         cbHeaderVO.setAmountSalesCost( amountSalesCost.toString() );
         cbHeaderVO.setAmountSalesPrice( amountSalesPrice.toString() );
      }

   }

   @Override
   public List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException
   {
      List< Object > cbBatchObjects = ( ( CBBatchDao ) getDao() ).getCBBatchVOsByCondition( cbBatchVO );

      if ( cbBatchObjects != null && cbBatchObjects.size() > 0 )
      {
         for ( Object cbBatchVOObject : cbBatchObjects )
         {
            CBBatchVO tempCBBatchVO = ( CBBatchVO ) cbBatchVOObject;
            // ����ϼ�ֵ
            fetchCBBatchVO( tempCBBatchVO );
         }
      }

      return cbBatchObjects;
   }

   @Override
   public CBBatchVO getCBBatchVOByBatchId( final String batchId ) throws KANException
   {
      final CBBatchVO cbBatchVO = ( ( CBBatchDao ) getDao() ).getCBBatchVOByBatchId( batchId );

      if ( cbBatchVO != null )
      {
         // ����ϼ�ֵ
         fetchCBBatchVO( cbBatchVO );
      }

      return cbBatchVO;
   }

   @Override
   public int updateCBBatch( final CBBatchVO cbBatchVO ) throws KANException
   {
      return ( ( CBBatchDao ) getDao() ).updateCBBatch( cbBatchVO );
   }

   @Override
   public int insertCBBatch( final CBBatchVO cbBatchVO ) throws KANException
   {
      return ( ( CBBatchDao ) getDao() ).insertCBBatch( cbBatchVO );
   }

   @Override
   public int rollback( final CBBatchVO cbBatchVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         // ���PageFlag��ѡ����Id��״̬
         final String pageFlag = cbBatchVO.getPageFlag();
         final String selectedIds = cbBatchVO.getSelectedIds();
         final String status = cbBatchVO.getStatus();

         if ( selectedIds != null && !selectedIds.trim().isEmpty() )
         {
            // ����ID����
            final String[] selectedIdArray = selectedIds.split( "," );

            rows = selectedIdArray.length;

            // ��ʼ��BatchId�б�
            final List< String > batchIds = new ArrayList< String >();

            // ����Id����
            for ( String encodedSelectedId : selectedIdArray )
            {
               // ��ʼ��BatchId
               String batchId = null;
               // Id����
               final String selectedId = KANUtil.decodeStringFromAjax( encodedSelectedId );

               // ����PageFlag�˻�
               if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_BATCH ) )
               {
                  batchId = rollbackBatch( selectedId, status );
               }
               else if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_HEADER ) )
               {
                  batchId = rollbackHeader( selectedId, status );
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
    * Rollback Batch
    * ��������Idɾ���̱�
    * @param selectId
    * @param userId
    * @throws KANException
    */
   private String rollbackBatch( final String batchId, final String status ) throws KANException
   {
      // ��������Id��ö�Ӧ��CBHeaderVO List
      final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

      // ������鲻Ϊ��
      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         // ����CBHeaderVO List��ɾ��
         for ( Object objectCBHeaderVO : cbHeaderVOs )
         {
            // ɾ��CBHeaderVO
            rollbackHeader( ( ( CBHeaderVO ) objectCBHeaderVO ).getHeaderId(), status );
         }
      }

      return batchId;
   }

   /**  
    * Rollback Header
    * �˻��̱�����Id
    * @param cbBatchVO
    * @throws KANException
    */
   private String rollbackHeader( final String headerId, final String status ) throws KANException
   {
      // ��ʼ��CBDetailVO�б�
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      // ����
      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         for ( Object objectCBDetailVO : cbDetailVOs )
         {
            // ɾ��CBDetailVO
            rollbackDetail( ( ( CBDetailVO ) objectCBDetailVO ).getDetailId(), status );
         }
      }

      // ɾ��CBHeaderVO
      final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( headerId );

      if ( cbHeaderVO != null )
      {
         return cbHeaderVO.getBatchId();
      }

      return "";
   }

   /**  
    * Rollback CBDetail
    * �˻��̱�������ϸ
    * @param selectId
    * @param userId
    * @throws KANException
    */
   private String rollbackDetail( final String detailId, final String status ) throws KANException
   {
      // ɾ��SBDetailVO
      final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( detailId );

      if ( cbDetailVO != null && cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( status ) && ( Integer.parseInt( cbDetailVO.getStatus() ) ) <= 2 )
      {
         // ɾ��CBDetailVO
         this.getCbDetailDao().deleteCBDetail( detailId );

         final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

         if ( cbHeaderVO != null )
         {
            return cbHeaderVO.getBatchId();
         }
      }

      return "";
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-09
   public int insertCBBatch( final CBBatchVO cbBatchVO, final List< ServiceContractDTO > serviceContractDTOs ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         // ����̱�HeaderVO��DetailVO���ڵĻ���������ݿ�
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            // ����ServiceContractDTOs
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               // ���CBDTO List
               final List< CBDTO > cbDTOs = serviceContractDTO.getCbDTOs();

               if ( cbDTOs != null && cbDTOs.size() > 0 )
               {
                  if ( rows == 0 )
                  {
                     // �������
                     insertCBBatch( cbBatchVO );

                     // һ��������ӳɹ�
                     rows = 1;
                  }

                  for ( CBDTO cbDTO : cbDTOs )
                  {
                     // ���CBHeaderVO
                     final CBHeaderVO cbHeaderVO = cbDTO.getCbHeaderVO();

                     // ���CBDetailVO List
                     final List< CBDetailVO > cbdetailVOs = cbDTO.getCbDetailVOs();

                     if ( cbHeaderVO != null && cbdetailVOs != null && cbdetailVOs.size() > 0 )
                     {
                        // ����BatchId
                        cbHeaderVO.setBatchId( cbBatchVO.getBatchId() );
                        // ����CBHeaderVO
                        this.getCbHeaderDao().insertCBHeader( cbHeaderVO );

                        // ����CBDetailVO List
                        for ( CBDetailVO cbDetailVO : cbdetailVOs )
                        {
                           // ����HeaderId
                           cbDetailVO.setHeaderId( cbHeaderVO.getHeaderId() );
                           // ����CBDetailVO
                           this.getCbDetailDao().insertCBDetail( cbDetailVO );
                        }
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( cbBatchVO.getBatchId() ) != null )
         {
            // ����ִ�н���ʱ��
            cbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            cbBatchVO.setDeleted( CBBatchVO.TRUE );
            cbBatchVO.setStatus( CBBatchVO.TRUE );
            // �޸�����
            updateCBBatch( cbBatchVO );
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
   public int submit( final CBBatchVO cbBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // ��������
         startTransaction();
         // ��ù�ѡID����
         final String selectedIds = cbBatchVO.getSelectedIds();
         // ��� PageFlag
         final String pageFlag = cbBatchVO.getPageFlag();
         // ���subAction
         final String subAction = cbBatchVO.getSubAction();

         // �����ѡ����
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = selectedIds.split( "," );
            // �ύ��¼��
            submitRows = selectedIdArray.length;

            // ʵ�����ַ�����ʾҪ�޸ĵ�״ֵ̬status
            String status = "0";
            // ����actFlag��ȡҪ�޸ĵ�״ֵ̬status
            if ( subAction.equalsIgnoreCase( BaseAction.APPROVE_OBJECTS ) )
            {
               status = "2";
            }
            // �����ȷ��
            else if ( subAction.equalsIgnoreCase( BaseAction.CONFIRM_OBJECTS ) )
            {
               status = "3";
            }
            // ������ύ
            else if ( subAction.equalsIgnoreCase( BaseAction.SUBMIT_OBJECTS ) )
            {
               status = "4";
            }

            // ��ʼ��BatchId�б�
            final List< String > batchIds = new ArrayList< String >();

            // ����
            for ( String encodedSelectId : selectedIdArray )
            {
               // ��ʼ��BatchId
               String batchId = null;
               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // ����PageFlag �ύ����
               if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_BATCH ) )
               {
                  batchId = submitBatch( selectId, status, cbBatchVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
               {
                  batchId = submitHeader( selectId, status, cbBatchVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
               {
                  batchId = submitDetail( selectId, status, cbBatchVO.getModifyBy() );
               }

               if ( KANUtil.filterEmpty( batchId ) != null && !batchIds.contains( batchId ) )
               {
                  batchIds.add( batchId );
               }
            }

            // ���Ը����丸����״̬
            if ( batchIds != null && batchIds.size() > 0 )
            {
               for ( String batchId : batchIds )
               {
                  trySubmitBatch( batchId, status, cbBatchVO.getModifyBy() );
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

   // ��������
   private String submitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      // ��ʼ�� CBHeaderVO����
      final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

      // ������ζ�Ӧ��CBHeaderVO���鲻Ϊ��
      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         // ����
         for ( Object cbHeaderVOObject : cbHeaderVOs )
         {
            final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
            submitHeader( cbHeaderVO.getHeaderId(), status, userId );
         }
      }

      return batchId;
   }

   // �����̱�����
   private String submitHeader( final String headerId, final String status, final String userId ) throws KANException
   {
      // ��ʼ��  DetailVO List -- By HeaderId
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         // ����objectCBDetailVOList���޸�״̬
         for ( Object objectCBDetailVO : cbDetailVOs )
         {
            final CBDetailVO cbDetailVO = ( CBDetailVO ) objectCBDetailVO;
            // ������ϸ
            submitDetail( cbDetailVO.getDetailId(), status, userId );
         }
      }

      // ����CBHeaderVO
      final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( headerId );

      if ( cbHeaderVO != null )
      {
         return cbHeaderVO.getBatchId();
      }

      return "";
   }

   // �����̱�������ϸ
   private String submitDetail( final String detailId, final String status, final String userId ) throws KANException
   {
      final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( detailId );

      if ( cbDetailVO != null && cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) )
      {
         cbDetailVO.setStatus( status );
         cbDetailVO.setModifyBy( userId );
         cbDetailVO.setModifyDate( new Date() );
         this.getCbDetailDao().updateCBDetail( cbDetailVO );

         final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

         if ( cbHeaderVO != null )
         {
            return cbHeaderVO.getBatchId();
         }
      }

      return "";
   }

   // �����ύ���� - �ύ�Ӷ��󵫲�����������Ƿ��ύ�����ʹ��
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // ��ʼ��CBBatchVO
         final CBBatchVO cbBatchVO = ( ( CBBatchDao ) getDao() ).getCBBatchVOByBatchId( batchId );
         // ��ʼ��CBHeaderVO�б�
         final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;

            // ����
            for ( Object cbHeaderVOObject : cbHeaderVOs )
            {
               final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;

               // ��ʼ��CBDetailVO�б�
               final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

               if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
               {
                  int detailFlag = 0;
                  int upperCount = 0;

                  // ����
                  for ( Object cbDetailVOObject : cbDetailVOs )
                  {
                     final CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;

                     if ( KANUtil.filterEmpty( cbDetailVO.getStatus() ) != null && Integer.valueOf( cbDetailVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                        if ( Integer.valueOf( cbDetailVO.getStatus() ) > Integer.valueOf( status ) )
                        {
                           upperCount++;
                        }
                     }
                  }

                  // ����̱�������ϸ��ȫ�����ڵ���Ŀ��״̬���޸��̱�������״̬
                  if ( detailFlag == cbDetailVOs.size() )
                  {
                     if ( upperCount != cbDetailVOs.size() )
                     {
                        cbHeaderVO.setStatus( status );
                     }
                     cbHeaderVO.setModifyBy( userId );
                     cbHeaderVO.setModifyDate( new Date() );

                     // �̱�ȷ��
                     if ( KANUtil.filterEmpty( status ) != null && status.equals( "3" ) )
                     {
                        final EmployeeContractCBVO employeeContractCBVO = this.getEmployeeContractCBDao().getEmployeeContractCBVOByEmployeeCBId( cbHeaderVO.getEmployeeCBId() );

                        boolean updated = false;

                        // �����깺��״̬��Ϊ����������
                        if ( "2".equals( cbHeaderVO.getCbStatus() ) )
                        {
                           //����Ա���̱�״̬�޸�
                           if ( employeeContractCBVO.getStatus().equals( "2" ) )
                           {
                              employeeContractCBVO.setStatus( "3" );
                              updated = true;
                           }
                           cbHeaderVO.setCbStatus( "3" );

                        }
                        // �����˹���״̬��Ϊ�����˹���
                        else if ( "5".equals( cbHeaderVO.getCbStatus() ) )
                        {
                           //����Ա���̱�״̬�޸�
                           if ( employeeContractCBVO.getStatus().equals( "5" ) )
                           {
                              employeeContractCBVO.setStatus( "6" );
                              updated = true;
                           }
                           cbHeaderVO.setCbStatus( "6" );
                           
                        }

                        if ( updated )
                        {
                           this.getEmployeeContractCBDao().updateEmployeeContractCB( employeeContractCBVO );
                        }
                     }

                     this.getCbHeaderDao().updateCBHeader( cbHeaderVO );
                     submitRows++;
                  }
               }

               if ( cbHeaderVO.getStatus() != null && !cbHeaderVO.getStatus().isEmpty() && Integer.valueOf( cbHeaderVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }
            }

            // ����̱�������ȫ������Ҫ���ĵ�״̬���޸��̱����ε�״̬
            if ( headerFlag == cbHeaderVOs.size() )
            {
               cbBatchVO.setStatus( status );
               cbBatchVO.setModifyBy( userId );
               cbBatchVO.setModifyDate( new Date() );
               ( ( CBBatchDao ) getDao() ).updateCBBatch( cbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   // �����˻����� - �˻��Ӷ��󵫲�����������Ƿ��˻ص����ʹ��
   private int tryRollbackBatch( final String batchId ) throws KANException
   {
      int rollbackRows = 0;

      // ��ʼ��CBBatchVO
      final CBBatchVO cbBatchVO = ( ( CBBatchDao ) getDao() ).getCBBatchVOByBatchId( batchId );
      // ��ʼ��CBHeaderVO�б�
      final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( batchId );

      if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
      {
         int headerFlag = 0;

         // ����
         for ( Object cbHeaderVOObject : cbHeaderVOs )
         {
            final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;

            // ��ʼ��CBDetailVO�б�
            final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

            if ( cbDetailVOs == null || cbDetailVOs.size() == 0 )
            {
               this.getCbHeaderDao().deleteCBHeader( cbHeaderVO.getHeaderId() );
               headerFlag++;
               rollbackRows++;
            }
         }

         if ( cbHeaderVOs.size() == headerFlag )
         {
            ( ( CBBatchDao ) getDao() ).deleteCBBatch( cbBatchVO.getBatchId() );
            rollbackRows++;
         }
      }
      else
      {
         ( ( CBBatchDao ) getDao() ).deleteCBBatch( cbBatchVO.getBatchId() );
         rollbackRows++;
      }

      return rollbackRows;
   }

   /**  
    * GetCBDTOsByCondition
    *  ���CBDTO
    * @param pagedListHolder
    * @param isPaged
    * @return
    * @throws KANException
    */
   @Override
   public PagedListHolder getCBDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException
   {
      // ��ò�ѯ����
      final CBBatchVO cbBatchVO = ( CBBatchVO ) pagedListHolder.getObject();
      // ��� PageFlag
      final String pageFlag = cbBatchVO.getPageFlag();
      // ��ʼ��List CBDTO
      List< Object > cbDTOs = new ArrayList< Object >();

      // ����PageFlag�ύ
      if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_BATCH ) )
      {
         cbDTOs = getBatchCBDTO( cbBatchVO );
      }
      else if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_HEADER ) )
      {
         cbDTOs = getHeaderCBDTO( cbBatchVO );
      }

      pagedListHolder.setSource( cbDTOs );

      return pagedListHolder;
   }

   /**  
    * GetBatchCBDTO
    * ������λ��CBDTO����
    * @param cbBatchVO
    * @return
    * @throws KANException 
    */
   private List< Object > getBatchCBDTO( final CBBatchVO cbBatchVO ) throws KANException
   {
      // ��ù�ѡID����
      final String selectedIds = cbBatchVO.getSelectedIds();
      // ��ʼ��ѡ��ID����
      String[] selectedIdArray = { "" };

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         selectedIdArray = selectedIds.split( "," );
      }

      for ( String selectId : selectedIdArray )
      {
         // ��ʼ��CBDTO List
         final List< Object > cbDTOs = new ArrayList< Object >();
         // ��ʼ����ѯ����
         CBBatchVO tempCBBatchVO = new CBBatchVO();
         // ��������ֵ
         tempCBBatchVO.setBatchId( KANUtil.decodeStringFromAjax( selectId ) );
         tempCBBatchVO.setAccountId( cbBatchVO.getAccountId() );
         tempCBBatchVO.setEntityId( cbBatchVO.getEntityId() );
         tempCBBatchVO.setCorpId( cbBatchVO.getCorpId() );
         tempCBBatchVO.setBusinessTypeId( cbBatchVO.getBusinessTypeId() );
         tempCBBatchVO.setOrderId( cbBatchVO.getOrderId() );
         tempCBBatchVO.setContractId( cbBatchVO.getContractId() );
         tempCBBatchVO.setMonthly( cbBatchVO.getMonthly() );
         // ��ѯ��������������CBBatchVO����
         final List< Object > cbBatchVOs = ( ( CBBatchDao ) getDao() ).getCBBatchVOsByCondition( tempCBBatchVO );

         // ��ʼ��CBHeaderVO List
         List< Object > cbHeaderVOs = new ArrayList< Object >();

         if ( cbBatchVOs != null && cbBatchVOs.size() > 0 )
         {
            for ( Object cbBatchVOObject : cbBatchVOs )
            {
               // ��ʼ����ѯ����
               final CBHeaderVO cbHeaderVO = new CBHeaderVO();
               // ��������ֵ
               cbHeaderVO.setBatchId( ( ( CBBatchVO ) cbBatchVOObject ).getBatchId() );
               cbHeaderVO.setStatus( cbBatchVO.getStatus() );
               cbHeaderVO.setCbId( cbBatchVO.getCbId() );
               cbHeaderVO.setAccountId( cbBatchVO.getAccountId() );
               cbHeaderVO.setCorpId( cbBatchVO.getCorpId() );
               cbHeaderVOs.addAll( this.getCbHeaderDao().getCBHeaderVOsByCondition( cbHeaderVO ) );
            }
         }

         // ��ʼ���������п�Ŀ�ļ���
         final List< ItemVO > items = new ArrayList< ItemVO >();

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            for ( Object cbHeaderVOObject : cbHeaderVOs )
            {
               // ��ʼ���������
               final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
               // ����ϼ�
               countAmount( tempCBHeaderVO );
               // ��ʼ��CBDTO
               final CBDTO cbDTO = new CBDTO();

               // װ��CBHeaderVO
               cbDTO.setCbHeaderVO( tempCBHeaderVO );

               // װ��CBDetailVO List
               fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), tempCBBatchVO.getStatus(), items );

               Object cbDTOObject = cbDTO;
               cbDTOs.add( cbDTOObject );
            }

            // CBDTO�����ڿ�Ŀ��ӿ�ֵ
            if ( cbDTOs != null && cbDTOs.size() > 0 )
            {
               fillCBDTOs( cbDTOs, items );
            }

            return cbDTOs;
         }
      }

      return null;
   }

   /**  
    * GetHeaderCBDTO
    * �������λ��CBDTO����
    * @param cbBatchVO
    * @return
    * @throws KANException
    */
   private List< Object > getHeaderCBDTO( final CBBatchVO cbBatchVO ) throws KANException
   {
      // ��ù�ѡID����
      final String selectedIds = cbBatchVO.getSelectedIds();
      // ��ʼ��CBDTO List
      final List< Object > cbDTOs = new ArrayList< Object >();
      // ��ʼ���������п�Ŀ�ļ���
      final List< ItemVO > items = new ArrayList< ItemVO >();

      if ( selectedIds != null && !selectedIds.isEmpty() )
      {
         final String[] selectedIdArray = selectedIds.split( "," );
         for ( String selectId : selectedIdArray )
         {
            // ��ʼ����ѯ����
            final CBHeaderVO cbHeaderVO = new CBHeaderVO();
            // ��������ֵ
            cbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( cbBatchVO.getBatchId() ) );
            cbHeaderVO.setHeaderId( KANUtil.decodeStringFromAjax( selectId ) );
            cbHeaderVO.setStatus( cbBatchVO.getStatus() );
            cbHeaderVO.setAccountId( cbBatchVO.getAccountId() );
            cbHeaderVO.setCorpId( cbBatchVO.getCorpId() );
            cbHeaderVO.setCbId( cbBatchVO.getCbId() );
            // ��ʼ��CBHeaderVO List
            final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByCondition( cbHeaderVO );

            if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
            {
               for ( Object cbHeaderVOObject : cbHeaderVOs )
               {
                  // ��ʼ���������
                  final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
                  // ����ϼ�
                  countAmount( tempCBHeaderVO );
                  // ��ʼ��CBDTO
                  final CBDTO cbDTO = new CBDTO();

                  // װ��CBHeaderVO
                  cbDTO.setCbHeaderVO( tempCBHeaderVO );

                  // װ��CBDetailVO List
                  fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), cbHeaderVO.getStatus(), items );

                  Object cbDTOObject = cbDTO;
                  cbDTOs.add( cbDTOObject );
               }
            }

         }
      }
      else
      {
         // ��ʼ����ѯ����
         final CBHeaderVO cbHeaderVO = new CBHeaderVO();
         // ��������ֵ
         cbHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( cbBatchVO.getBatchId() ) );
         cbHeaderVO.setStatus( cbBatchVO.getStatus() );
         cbHeaderVO.setAccountId( cbBatchVO.getAccountId() );
         cbHeaderVO.setCorpId( cbBatchVO.getCorpId() );
         cbHeaderVO.setCbId( cbBatchVO.getCbId() );
         // ��ʼ��CBHeaderVO List
         final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByCondition( cbHeaderVO );

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            for ( Object cbHeaderVOObject : cbHeaderVOs )
            {
               // ��ʼ���������
               final CBHeaderVO tempCBHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;
               // ����ϼ�
               countAmount( tempCBHeaderVO );
               // ��ʼ��CBDTO
               final CBDTO cbDTO = new CBDTO();

               // װ��CBHeaderVO
               cbDTO.setCbHeaderVO( tempCBHeaderVO );

               // װ��CBDetailVO List
               fetchCBDetail( cbDTO, tempCBHeaderVO.getHeaderId(), cbHeaderVO.getStatus(), items );

               Object cbDTOObject = cbDTO;
               cbDTOs.add( cbDTOObject );
            }
         }

      }

      // CBDTO�����ڿ�Ŀ��ӿ�ֵ
      if ( cbDTOs != null && cbDTOs.size() > 0 )
      {
         fillCBDTOs( cbDTOs, items );
      }

      return cbDTOs;
   }

   // װ���籣��ϸ
   private void fetchCBDetail( final CBDTO cbDTO, final String headerId, final String status, final List< ItemVO > items ) throws KANException
   {
      // ��ʼ����װ���籣��ϸ
      final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( headerId );

      if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
      {
         for ( Object cbDetailVOObject : cbDetailVOs )
         {
            final CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;

            // ֻ��ȡ�����������籣��ϸ
            if ( cbDetailVO.getStatus() != null && cbDetailVO.getStatus().equals( status ) )
            {
               cbDTO.getCbDetailVOs().add( ( CBDetailVO ) cbDetailVOObject );
            }

            // ��ʼ����Ŀ
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( cbDetailVO.getItemId() );
            itemVO.setItemType( cbDetailVO.getItemType() );

            // �����Ŀ�����������
            if ( !isItemExist( itemVO, items ) )
            {
               items.add( itemVO );
            }

         }
      }
   }

   // CBDTO������䲻���ڿ�Ŀ
   private void fillCBDTOs( final List< Object > cbDTOs, final List< ItemVO > items )
   {
      for ( ItemVO itemVO : items )
      {
         for ( Object cbDTOObject : cbDTOs )
         {
            final List< CBDetailVO > copyList = new ArrayList< CBDetailVO >();
            final CBDTO cbDTO = ( CBDTO ) cbDTOObject;
            // ���CBDTO��ӦCBHeaderVO��CBDetailVO����
            final CBHeaderVO cbHeaderVO = cbDTO.getCbHeaderVO();
            List< CBDetailVO > cbDetailVOs = cbDTO.getCbDetailVOs();
            // COPY�Ѵ���CBDTO����
            copyList.addAll( cbDetailVOs );

            // ���ҵ�ǰCBDetailVO�Ƿ���ڸÿ�Ŀ�������������
            fetchItemExistCbDetailVOs( itemVO, cbHeaderVO, cbDetailVOs, copyList );

            // ��������CBDTO��CBDetailVO����
            cbDTO.setCbDetailVOs( copyList );
         }
      }

   }

   /**  
    * IsItemExistCbDetailVOs
    * �ж�ItemVO�Ƿ�����籣��ϸ����
    * @param itemVO
    * @param items
    * @return
    */
   private void fetchItemExistCbDetailVOs( final ItemVO itemVO, final CBHeaderVO cbHeaderVO, final List< CBDetailVO > cbDetailVOs, final List< CBDetailVO > returnDetailVOs )
   {
      Boolean existFlag = false;

      // �жϿ�Ŀ�Ƿ����
      if ( cbDetailVOs == null || cbDetailVOs.size() == 0 )
      {
         existFlag = false;
      }
      else
      {
         for ( CBDetailVO cbDetailVO : cbDetailVOs )
         {
            if ( itemVO.getItemId().equals( cbDetailVO.getItemId() ) )
            {
               existFlag = true;
               break;
            }
         }
      }

      // �����Ŀ�����������
      if ( !existFlag )
      {
         // ��ʼ��CBDetailVO�������
         final CBDetailVO tempDetailVO = new CBDetailVO();
         tempDetailVO.setHeaderId( cbHeaderVO.getHeaderId() );
         tempDetailVO.setStatus( cbHeaderVO.getStatus() );
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

}
