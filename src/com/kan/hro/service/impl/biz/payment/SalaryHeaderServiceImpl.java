package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.CommonBatchDao;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.domain.biz.payment.CommonBatchVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.service.inf.biz.payment.SalaryHeaderService;

public class SalaryHeaderServiceImpl extends ContextService implements SalaryHeaderService
{

   private CommonBatchDao commonBatchDao;

   private EmployeeDao employeeDao;

   private EmployeeContractDao employeeContractDao;

   private SalaryHeaderDao salaryHeaderDao;

   private SalaryDetailDao salaryDetailDao;

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public SalaryHeaderDao getSalaryHeaderDao()
   {
      return salaryHeaderDao;
   }

   public void setSalaryHeaderDao( SalaryHeaderDao salaryHeaderDao )
   {
      this.salaryHeaderDao = salaryHeaderDao;
   }

   public SalaryDetailDao getSalaryDetailDao()
   {
      return salaryDetailDao;
   }

   public void setSalaryDetailDao( SalaryDetailDao salaryDetailDao )
   {
      this.salaryDetailDao = salaryDetailDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   @Override
   public PagedListHolder getSalaryHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SalaryHeaderDao salaryHeaderDao = ( SalaryHeaderDao ) getDao();
      pagedListHolder.setHolderSize( salaryHeaderDao.countSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SalaryHeaderVO getSalaryHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertSalaryHeader( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).insertSalaryHeader( salaryHeaderVO );
   }

   @Override
   public int updateSalaryHeader( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).updateSalaryHeader( salaryHeaderVO );
   }

   @Override
   public int deleteSalaryHeader( String salaryHeaderId ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).deleteSalaryHeader( salaryHeaderId );
   }

   @Override
   public PagedListHolder getSalaryDTOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      // ��ʼ��SalaryHeaderDTO����
      List< Object > salaryDTOs = new ArrayList< Object >();
      // ��ʼ��SalaryDetailVO����
      List< Object > salaryHeaderVOs = new ArrayList< Object >();

      final SalaryHeaderDao salaryHeaderDao = ( SalaryHeaderDao ) getDao();
      pagedListHolder.setHolderSize( salaryHeaderDao.countSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() ) );

      // ��ȡ��ѯ����
      final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) pagedListHolder.getObject();

      // ��ʼ��salaryDetailVO
      SalaryDetailVO salaryDetailVO = new SalaryDetailVO();
      salaryDetailVO.setSalaryHeaderId( salaryHeaderVO.getSalaryHeaderId() );
      salaryDetailVO.setBatchId( salaryHeaderVO.getBatchId() );
      // ��÷���������SalaryDetailVO����
      final List< Object > salaryDetailVOs = this.salaryDetailDao.getSalaryDetailVOsByCondition( salaryDetailVO );
      // ��ʼ����������Item ��Ϣ����
      final List< MappingVO > items = getItems( salaryDetailVOs );

      if ( isPaged )
      {
         // ���salaryHeaderVO����
         salaryHeaderVOs = salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) );

      }
      else
      {
         // ���salaryHeaderVO����
         salaryHeaderVOs = salaryHeaderDao.getSalaryHeaderVOsByCondition( ( SalaryHeaderVO ) pagedListHolder.getObject() );
      }

      // ���salaryHeaderVO����
      salaryDTOs = fillSalaryDetilVOs( items, salaryHeaderVOs, salaryDetailVOs );
      pagedListHolder.setSource( salaryDTOs );
      // ��ӿ�Ŀ����
      pagedListHolder.setAdditionalObject( ( items == null ) ? 15 : ( items.size() * 2 + 30 ) );

      return pagedListHolder;
   }

   private List< Object > fillSalaryDetilVOs( List< MappingVO > items, List< Object > salaryHeaderVOs, List< Object > salaryDetailVOs )
   {
      // ��ʼ��PaymentHeaderDTO����
      final List< Object > salaryHeaderDTOs = new ArrayList< Object >();

      for ( Object salaryHeaderVOObject : salaryHeaderVOs )
      {
         final SalaryHeaderVO tempSalaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;
         // ��ʼ��salaryDetailVO
         SalaryDetailVO salaryDetailVO = new SalaryDetailVO();
         salaryDetailVO.setSalaryHeaderId( tempSalaryHeaderVO.getSalaryHeaderId() );
         // ��ʼ��salaryDetailVO����
         final List< SalaryDetailVO > tempSalaryDetailVOs = new ArrayList< SalaryDetailVO >();

         // ����Item ID ����
         for ( MappingVO mappingVO : items )
         {
            final String itemId = mappingVO.getMappingId();
            // �ж�Item�Ƿ���ڱ��
            Boolean existFlag = true;

            // ����
            for ( Object tempSalaryDetailVOObject : salaryDetailVOs )
            {
               salaryDetailVO = ( SalaryDetailVO ) tempSalaryDetailVOObject;

               // ���PaymentDetailVO��headerId
               // �������PaymentHeaderVO��headerIdƥ���Ҹ�Itemֵ���������
               if ( salaryDetailVO.getSalaryHeaderId().equals( tempSalaryHeaderVO.getSalaryHeaderId() ) && salaryDetailVO.getItemId().equals( itemId ) )
               {
                  tempSalaryDetailVOs.add( salaryDetailVO );
                  existFlag = false;
                  continue;
               }
            }

            // ���PaymentHeaderVO�����и�Item��������洢������
            if ( existFlag )
            {
               salaryDetailVO = new SalaryDetailVO();
               salaryDetailVO.setSalaryHeaderId( tempSalaryHeaderVO.getSalaryHeaderId() );
               salaryDetailVO.setItemId( itemId );
               salaryDetailVO.setNameZH( mappingVO.getMappingValue() );
               salaryDetailVO.setNameEN( mappingVO.getOptionStyle() );
               salaryDetailVO.setItemNo( mappingVO.getMappingTemp() );
               tempSalaryDetailVOs.add( salaryDetailVO );
            }

         }

         // DTOע��
         final SalaryDTO tempSalaryDTO = new SalaryDTO();
         tempSalaryDTO.setSalaryHeaderVO( tempSalaryHeaderVO );
         tempSalaryDTO.setSalaryDetailVOs( tempSalaryDetailVOs );
         salaryHeaderDTOs.add( tempSalaryDTO );
      }
      return salaryHeaderDTOs;
   }

   private List< MappingVO > getItems( List< Object > salaryDetailVOs )
   {
      // ��ʼ��Item��Ϣ����
      final List< MappingVO > items = new ArrayList< MappingVO >();

      // Item���ϲ�������
      for ( Object salaryDetailVOObject : salaryDetailVOs )
      {
         final SalaryDetailVO salaryDetailVO = ( SalaryDetailVO ) salaryDetailVOObject;

         // ��������ڣ�����ӵ�Items����
         if ( !isItemExistInItemsList( items, salaryDetailVO.getItemId() ) )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( salaryDetailVO.getItemId() );
            mappingVO.setMappingValue( salaryDetailVO.getNameZH() );
            mappingVO.setOptionStyle( salaryDetailVO.getNameEN() );
            mappingVO.setMappingTemp( salaryDetailVO.getItemNo() );
            items.add( mappingVO );
         }
      }

      return items;
   }

   private boolean isItemExistInItemsList( List< MappingVO > items, String itemId )
   {
      boolean flag = false;
      for ( MappingVO mappingVO : items )
      {
         if ( mappingVO.getMappingId().equals( itemId ) )
         {
            flag = true;
            break;
         }
      }
      return flag;
   }

   @Override
   public List< Object > getSalaryHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( batchId );
   }

   @Override
   public int submit( final CommonBatchVO commonBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ù�ѡID����
         final String selectedIds = commonBatchVO.getSelectedIds();
         // ��� PageFlag
         final String pageFlag = commonBatchVO.getPageFlag();
         // ���subAction
         final String subAction = commonBatchVO.getSubAction();

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
         // ������˻�
         else if ( subAction.equalsIgnoreCase( BaseAction.ROLLBACK_OBJECT ) )
         {
            status = "0";
         }

         // �����ѡ����
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = selectedIds.split( "," );
            // �ύ��¼��
            submitRows = selectedIdArray.length;

            // ����
            for ( String encodedSelectId : selectedIdArray )
            {
               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );
               // ����PageFlag �ύ����
               if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_BATCH ) )
               {
                  commonBatchVO.setBatchId( selectId );
                  submitBatch( selectId, status, commonBatchVO.getModifyBy() );
               }

               else if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) )
               {
                  submitHeader( selectId, status, commonBatchVO.getModifyBy() );
               }

            }

            // ���Ը����丸����״̬
            submitRows = trySubmitBatch( commonBatchVO.getBatchId(), status, commonBatchVO.getModifyBy() );

            // ���¼���CommonBatchVO �ϼ�ֵ
            sumCommonBatchData( commonBatchVO );
         }
         else if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_BATCH ) )
         {
            submitBatch( commonBatchVO.getBatchId(), status, commonBatchVO.getModifyBy() );
            submitRows++;
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

   private void sumCommonBatchData( final CommonBatchVO commonBatchVO ) throws KANException
   {

      final List< Object > salaryHeaderVOs = this.salaryHeaderDao.getSalaryHeaderVOsByBatchId( commonBatchVO.getBatchId() );

      if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
      {
         // ��ʼ�������ܼ�
         double costAmountCompany = 0;
         //         double billAmountPersonal = 0;
         double costAmountPersonal = 0;

         for ( Object object : salaryHeaderVOs )
         {
            SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) object;

            // ״̬Ϊ���½��������ύ���ۼ�
            if ( "1".equals( salaryHeaderVO.getStatus() ) || "2".equals( salaryHeaderVO.getStatus() ) )
            {
               costAmountCompany += Double.parseDouble( salaryHeaderVO.getCostAmountCompany() == null ? "0" : salaryHeaderVO.getCostAmountCompany() );
               //               billAmountPersonal += Double.parseDouble( salaryHeaderVO.getBillAmountPersonal() == null ? "0" : salaryHeaderVO.getBillAmountPersonal() );
               costAmountPersonal += Double.parseDouble( salaryHeaderVO.getCostAmountPersonal() == null ? "0" : salaryHeaderVO.getCostAmountPersonal() );
            }

         }

         CommonBatchVO tempCommonBatchVO = this.commonBatchDao.getCommonBatchVOByBatchId( commonBatchVO.getBatchId() );
         tempCommonBatchVO.setRemark2( "�ϼƣ�" + tempCommonBatchVO.formatNumber( String.valueOf( costAmountCompany + costAmountPersonal ) ) );
         tempCommonBatchVO.setModifyDate( new Date() );
         this.commonBatchDao.updateCommonBatch( tempCommonBatchVO );
      }

   }

   // �����ύ���� - �ύ�Ӷ��󵫲�����������Ƿ��ύ�����ʹ��
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // ��ʼ��commonBatchVO
         final CommonBatchVO commonBatchVO = this.commonBatchDao.getCommonBatchVOByBatchId( batchId );
         // ��ʼ��salaryHeaderVO�б�
         final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( batchId );

         if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;
            String salaryHeaderstatus = ( ( SalaryHeaderVO ) salaryHeaderVOs.get( 0 ) ).getStatus();

            // ����
            for ( Object salaryHeaderVOObject : salaryHeaderVOs )
            {
               final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;

               if ( salaryHeaderVO.getStatus() != null && !salaryHeaderVO.getStatus().isEmpty()
                     && Integer.valueOf( salaryHeaderVO.getStatus() ).equals( Integer.valueOf( salaryHeaderstatus ) ) )
               {
                  headerFlag++;
               }

            }

            // �����ȫ����ͬһ״̬���޸����ε�״̬
            if ( headerFlag == salaryHeaderVOs.size() && !salaryHeaderstatus.equals( "1" ) )
            {
               commonBatchVO.setStatus( salaryHeaderstatus );
               commonBatchVO.setModifyBy( userId );
               commonBatchVO.setModifyDate( new Date() );
               this.commonBatchDao.updateCommonBatch( commonBatchVO );
               submitRows++;
            }

         }
         else
         {
            if ( commonBatchVO != null )
               this.getCommonBatchDao().deleteCommonBatch( commonBatchVO.getBatchId() );
            submitRows++;
         }

      }

      return submitRows;
   }

   // ��������
   private void submitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      // ��ʼ�� SalaryHeaderDao����
      final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( batchId );

      // ������ζ�Ӧ��SalaryHeader���鲻Ϊ��
      if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
      {
         // ����
         for ( Object salaryHeaderVOObject : salaryHeaderVOs )
         {
            final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;
            submitHeader( salaryHeaderVO.getSalaryHeaderId(), status, userId );
         }
      }

      // ���ĸ�����
      final CommonBatchVO commonBatchVO = this.getCommonBatchDao().getCommonBatchVOByBatchId( batchId );

      if ( KANUtil.filterEmpty( commonBatchVO.getStatus(), "1" ) == null )
      {
         commonBatchVO.setStatus( status );
         commonBatchVO.setModifyBy( userId );
         commonBatchVO.setModifyDate( new Date() );
         this.getCommonBatchDao().updateCommonBatch( commonBatchVO );
      }
   }

   // ���Ĺ���״̬
   // Reviewed by Kevin Jin at 2014-05-20
   private void submitHeader( final String headerId, final String status, final String userId ) throws KANException
   {
      // ��ʼ�� SalaryHeaderDao����
      final SalaryHeaderVO salaryHeaderVO = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOByHeaderId( headerId );

      if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus(), "1" ) == null )
      {
         // �ύ�����
         if ( status.equals( "2" ) )
         {
            salaryHeaderVO.setStatus( status );
         }

         if ( status.equals( "0" ) && salaryHeaderVO.getStatus().equals( "1" ) )
         {
            salaryHeaderVO.setDeleted( "2" );
            salaryHeaderVO.setStatus( status );
         }

         salaryHeaderVO.setModifyBy( userId );
         salaryHeaderVO.setModifyDate( new Date() );

         ( ( SalaryHeaderDao ) getDao() ).updateSalaryHeader( salaryHeaderVO );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2014-06-05
   public List< SalaryDTO > getSalaryDTOsByCondition( SalaryHeaderVO salaryHeaderVO ) throws KANException
   {
      // ��ʼ��SalaryDTO�б�
      final List< SalaryDTO > salaryDTOs = new ArrayList< SalaryDTO >();

      // ��ȡSalaryHeaderVO�б�
      final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByCondition( salaryHeaderVO );

      if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
      {
         for ( Object salaryHeaderVOObject : salaryHeaderVOs )
         {
            // ��ʼ���������
            final SalaryHeaderVO tempSalaryHeaderVO = ( SalaryHeaderVO ) salaryHeaderVOObject;
            // ��ʼ��SalaryDTO
            final SalaryDTO salaryDTO = new SalaryDTO();

            // װ��SalaryHeaderVO
            salaryDTO.setSalaryHeaderVO( tempSalaryHeaderVO );

            // ��ʼ��SalaryDetailVO - ��Ϊ��������
            final SalaryDetailVO salaryDetailVO = new SalaryDetailVO();
            salaryDetailVO.setSalaryHeaderId( tempSalaryHeaderVO.getSalaryHeaderId() );
            salaryDetailVO.setItemType( salaryHeaderVO.getItemTypes() );

            // װ��SalaryDetailVO List
            fetchSalaryDetail( salaryDTO, salaryDetailVO );

            salaryDTOs.add( salaryDTO );
         }
      }

      return salaryDTOs;

   }

   // װ�ع�����ϸ
   // Reviewed by Kevin Jin at 2014-06-05
   private void fetchSalaryDetail( final SalaryDTO salaryDTO, final SalaryDetailVO salaryDetailVO ) throws KANException
   {
      // ��ʼ��������ϸ
      final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByCondition( salaryDetailVO );

      // װ�ع�����ϸ
      if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
      {
         for ( Object salaryDetailVOObject : salaryDetailVOs )
         {
            salaryDTO.getSalaryDetailVOs().add( ( SalaryDetailVO ) salaryDetailVOObject );
         }
      }
   }

   @Override
   public int rollback( final CommonBatchVO commonBatchVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ù�ѡID����
         final String selectedIds = commonBatchVO.getSelectedIds();
         // ��� PageFlag
         final String pageFlag = commonBatchVO.getPageFlag();

         // ʵ�����ַ�����ʾҪ�޸ĵ�״ֵ̬status �˻�
         String status = "0";

         // �����ѡ����
         if ( KANUtil.filterEmpty( selectedIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = selectedIds.split( "," );
            // �ύ��¼��
            submitRows = selectedIdArray.length;
            // ����
            final List< String > ids = new ArrayList< String >();

            // ����
            for ( String encodedSelectId : selectedIdArray )
            {
               ids.add( KANUtil.decodeStringFromAjax( encodedSelectId ) );
            }

            // ����PageFlag �ύ����
            if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_BATCH ) )
            {
               rollbackBatch( ids );
            }
            else if ( pageFlag.equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) )
            {
               rollbackHeader( ids );
               // ���Ը����丸����״̬
               submitRows = trySubmitBatch( commonBatchVO.getBatchId(), status, commonBatchVO.getModifyBy() );
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
   private void rollbackBatch( List< String > batchIds ) throws KANException
   {
      if ( batchIds != null && batchIds.size() > 0 )
      {
         for ( String batchId : batchIds )
         {
            // ɾ��������
            final CommonBatchVO commonBatchVO = this.getCommonBatchDao().getCommonBatchVOByBatchId( batchId );

            if ( commonBatchVO != null )
            {
               // ��ʼ�� SalaryHeaderDao����
               final List< Object > salaryHeaderVOs = ( ( SalaryHeaderDao ) getDao() ).getSalaryHeaderVOsByBatchId( commonBatchVO.getBatchId() );

               // ������ζ�Ӧ��SalaryHeader���鲻Ϊ��
               if ( salaryHeaderVOs != null && salaryHeaderVOs.size() > 0 )
               {
                  // ����
                  List< String > headerIds = new ArrayList< String >();

                  for ( Object salaryHeaderVOObject : salaryHeaderVOs )
                  {
                     headerIds.add( ( ( SalaryHeaderVO ) salaryHeaderVOObject ).getSalaryHeaderId() );

                  }

                  rollbackHeader( headerIds );
               }

               this.getCommonBatchDao().deleteCommonBatch( commonBatchVO.getBatchId() );
            }
         }
      }
   }

   // �˻�
   private void rollbackHeader( List< String > headerIds ) throws KANException
   {
      this.getSalaryDetailDao().deleteSalaryDetailByHeaderIds( headerIds );
      ( ( SalaryHeaderDao ) getDao() ).deleteSalaryHeaderByHeaderIds( headerIds );
   }
}
