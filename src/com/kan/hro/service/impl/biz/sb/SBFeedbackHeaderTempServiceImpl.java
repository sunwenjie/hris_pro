package com.kan.hro.service.impl.biz.sb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailTempDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDTO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDTOTemp;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBFeedbackHeaderTempService;
import com.kan.hro.service.inf.biz.sb.SBHeaderTempService;

public class SBFeedbackHeaderTempServiceImpl extends ContextService implements SBFeedbackHeaderTempService
{
   // ע��CommonBatchDao
   private CommonBatchDao commonBatchDao;
   // ע��SBBatchDao
   private SBBatchDao sbBatchDao;
   // ע��SBDetailTempDao
   private SBDetailTempDao sbDetailTempDao;
   // ע��EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;
   // ע��SBHeaderDao
   private SBHeaderDao sbHeaderDao;
   // ע��SBDetailDao
   private SBDetailDao sbDetailDao;
   // ע��EmployeeContractDao
   private EmployeeContractDao employeeContractDao;
   // ע��SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;
   // ע��SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public SBDetailTempDao getSbDetailTempDao()
   {
      return sbDetailTempDao;
   }

   public void setSbDetailTempDao( SBDetailTempDao sbDetailTempDao )
   {
      this.sbDetailTempDao = sbDetailTempDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

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

   public SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   public SBAdjustmentDetailDao getSbAdjustmentDetailDao()
   {
      return sbAdjustmentDetailDao;
   }

   public void setSbAdjustmentDetailDao( SBAdjustmentDetailDao sbAdjustmentDetailDao )
   {
      this.sbAdjustmentDetailDao = sbAdjustmentDetailDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   /**  
    * GetSBHeaderTempVOsByCondition
    *	 ��á�SBHeaderTempVO�������SBHeaderTempVO����
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderTempDao.countSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderTempDao.getSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderTempDao.getSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            countSBHeaderTempVOAmount( sbHeaderTempVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * GetAmountVendorSBHeaderTempVOsByCondition
    *	��ð����й�Ӧ�̰��·ݷ�����SBHeaderTempVO���ϻ�����Ϣ
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getAmountVendorSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderTempDao.countAmountVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderTempDao.getAmountVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderTempDao.getAmountVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            // ��ӻ�����Ϣ
            fetchAmountVendorPaymentSBHeaderTempVO( sbHeaderTempVO );
         }
      }

      // ���SBHeaderTempVO �µ�

      return pagedListHolder;
   }

   /**  
    * GetVendorSBHeaderTempVOsByCondition
    *	 ���ָ����Ӧ�̵�SBHeaderTempVO������Ϣ
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getVendorSBHeaderTempVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderTempDao.countVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderTempDao.getVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderTempDao.getVendorSBHeaderTempVOsByCondition( ( SBHeaderTempVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            // ���������Ϣ
            fetchVendorPaymentSBHeaderTempVO( sbHeaderTempVO );
         }
      }

      // ���SBHeaderTempVO �µ�

      return pagedListHolder;
   }

   /**  
    * CountSBHeaderTempVOAmount
    * ���㵥��SBHeaderTempVO�ķ��úϼ�
    * @param sbHeaderTempVO
    * @throws KANException
    */
   private void countSBHeaderTempVOAmount( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO.getHeaderId() );

      // �������ݺϼ�
      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         // ��ʼ���ϼ�ֵ
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
            // ���״̬ƥ�������
            if ( sbDetailTempVO.getStatus().equals( sbHeaderTempVO.getAdditionalStatus() ) )
            {
               amountCompany = amountCompany.add( new BigDecimal( sbDetailTempVO.getAmountCompany() == null ? "0" : sbDetailTempVO.getAmountCompany() ) );
               amountPersonal = amountPersonal.add( new BigDecimal( sbDetailTempVO.getAmountPersonal() == null ? "0" : sbDetailTempVO.getAmountPersonal() ) );
            }
         }
         sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
         sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );
      }

   }

   /**  
    * CountSBHeaderTempVOAmount
    * ���㵥��SBHeaderTempVO�ķ��úϼƣ���ѯ��Ӧ����Ϣ�ã�����״̬��
    * @param sbHeaderTempVO
    * @throws KANException
    */
   private void countVendorSBHeaderTempVOAmount( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO.getHeaderId() );

      // �������ݺϼ�
      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         // ��ʼ���ϼ�ֵ
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
            amountCompany = amountCompany.add( new BigDecimal( sbDetailTempVO.getAmountCompany() == null ? "0" : sbDetailTempVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( sbDetailTempVO.getAmountPersonal() == null ? "0" : sbDetailTempVO.getAmountPersonal() ) );
         }
         sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
         sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );
      }

   }

   /**  
    * FetchVendorPaymentSBHeaderTempVO
    * ������Ӧ���籣��Ϣ���ƣ�������Ϣ��
    * @param sbHeaderTempVO
    * @throws KANException 
    */
   private void fetchAmountVendorPaymentSBHeaderTempVO( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // ��ѯ�������״̬
      sbHeaderTempVO.setStatus( "" );
      // ��ʼ���ϼ�ֵ
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // ��ø���Ӧ�̸��·ݶ�Ӧ��SBHeaderTempVO ����
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // �ֱ����ϼ�
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object tempSBHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) tempSBHeaderTempVOObject;
            countVendorSBHeaderTempVOAmount( tempSBHeaderTempVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderTempVO.getAmountCompany() == null ? "0" : tempSBHeaderTempVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderTempVO.getAmountPersonal() == null ? "0" : tempSBHeaderTempVO.getAmountPersonal() ) );
            sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
            sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );

            // ��Ա�����������
            if ( isEmployeeVOExist( tempSBHeaderTempVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderTempVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderTempVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderTempVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderTempVO.setEmployees( employeeMappingVOs );
      }

   }

   /**  
    * FetchVendorPaymentSBHeaderTempVO
    * ������Ӧ���籣��Ϣ���ƣ���״̬��ʾ��
    * @param sbHeaderTempVO
    * @throws KANException 
    */
   private void fetchVendorPaymentSBHeaderTempVO( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // ��ȡSBHeaderTempVO��ԭ��״ֵ̬
      final String status = sbHeaderTempVO.getStatus();

      // ���ò�ѯ״̬
      sbHeaderTempVO.setStatus( sbHeaderTempVO.getAdditionalStatus() );
      // ��ʼ���ϼ�ֵ
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // ��ø���Ӧ�̸��·ݶ�Ӧ��SBHeaderTempVO ����
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // �ֱ����ϼ�
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object tempSBHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) tempSBHeaderTempVOObject;
            countSBHeaderTempVOAmount( tempSBHeaderTempVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderTempVO.getAmountCompany() == null ? "0" : tempSBHeaderTempVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderTempVO.getAmountPersonal() == null ? "0" : tempSBHeaderTempVO.getAmountPersonal() ) );
            sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
            sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );

            // ��Ա�����������
            if ( isEmployeeVOExist( tempSBHeaderTempVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderTempVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderTempVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderTempVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderTempVO.setEmployees( employeeMappingVOs );
      }

      // ��ԭ״ֵ̬
      sbHeaderTempVO.setStatus( status );
   }

   /**  
    * IsEmployeeVOExist
    *	�жϹ�Ա�Ƿ����
    *	@param tempSBHeaderTempVO
    *	@param employeeMappingVOs
    *	@return
    */
   private boolean isEmployeeVOExist( final SBHeaderTempVO SBHeaderTempVO, final List< MappingVO > employeeMappingVOs )
   {
      if ( employeeMappingVOs != null && employeeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : employeeMappingVOs )
         {
            if ( mappingVO.getMappingId().equals( SBHeaderTempVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public List< Object > getSBHeaderTempVOsByBatchId( final String sbBatchId ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByBatchId( sbBatchId );

      // ����ϼ�ֵ
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            countSBHeaderTempVOAmount( sbHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public SBHeaderTempVO getSBHeaderTempVOByHeaderId( final String headerId ) throws KANException
   {
      final SBHeaderTempVO sbHeaderTempVO = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOByHeaderId( headerId );

      // ����ϼ�ֵ
      if ( sbHeaderTempVO != null )
      {
         countSBHeaderTempVOAmount( sbHeaderTempVO );
      }

      return sbHeaderTempVO;
   }

   @Override
   public List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // ����ϼ�ֵ
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            countSBHeaderTempVOAmount( tempSBHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getAmountVendorSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // ����ϼ�ֵ
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            fetchAmountVendorPaymentSBHeaderTempVO( tempSBHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getVendorSBHeaderTempVOsByCondition( sbHeaderTempVO );

      // ����ϼ�ֵ
      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            fetchVendorPaymentSBHeaderTempVO( tempSBHeaderTempVO );
         }
      }

      return sbHeaderTempVOs;
   }

   @Override
   public List< SBDTOTemp > getSBDTOTempsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // ��ʼ��SBDTOTemp List
      final List< SBDTOTemp > sbDTOTemps = new ArrayList< SBDTOTemp >();
      // ��ʼ��SBHeaderTempVO List
      final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

      if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
      {
         for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
         {
            // ��ʼ���������
            final SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;
            // ��ʼ��SBDTOTemp
            final SBDTOTemp sbDTOTemp = new SBDTOTemp();
            // װ��SBHeaderTempVO
            sbDTOTemp.setSbHeaderTempVO( tempSBHeaderTempVO );
            // װ��SBDetailTempVO List
            fetchSBDetailTemp( sbDTOTemp, tempSBHeaderTempVO.getHeaderId(), sbHeaderTempVO.getStatus() );

            sbDTOTemps.add( sbDTOTemp );
         }
      }

      return sbDTOTemps;
   }

   // װ���籣��ϸ
   private void fetchSBDetailTemp( final SBDTOTemp sbDTOTemp, final String headerId, final String status ) throws KANException
   {
      // ��ʼ����װ���籣��ϸ
      final List< Object > sbDetailTempVOs = this.getSbDetailTempDao().getSBDetailTempVOsByHeaderId( headerId );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

            // ֻ��ȡ�����������籣��ϸ
            if ( sbDetailTempVO.getStatus() != null && sbDetailTempVO.getStatus().equals( status ) )
            {
               sbDTOTemp.getSbDetailTempVOs().add( ( SBDetailTempVO ) sbDetailTempVOObject );
            }
         }
      }
   }

   // ����Detail�������ɵ���DTO ����
   private List< SBAdjustmentDTO > fetchSBAdjustmentDTO( final List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs ) throws KANException
   {
      List< SBAdjustmentDTO > sbAdjustmentDTOs = new ArrayList< SBAdjustmentDTO >();

      if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
      {
         my: for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDetailVOs )
         {

            if ( sbAdjustmentDTOs.size() > 0 )
            {
               for ( SBAdjustmentDTO sbAdjustmentDTO : sbAdjustmentDTOs )
               {
                  final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentDTO.getSbAdjustmentHeaderVO();

                  // ContractId, Monthly, EmployeeSBId �Ƿ�ƥ��
                  if ( sbAdjustmentHeaderVO.getContractId().equals( sbAdjustmentDetailVO.getContractId() )
                        && sbAdjustmentHeaderVO.getMonthly().equals( sbAdjustmentDetailVO.getMonthly() )
                        && sbAdjustmentHeaderVO.getEmployeeSBId().equals( sbAdjustmentDetailVO.getEmployeeSBId() ) )
                  {
                     // �ж�SBAdjustmentDetailVO�� AccountMonthly �� ItemId�Ƿ�ƥ��
                     final List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs2 = sbAdjustmentDTO.getSbAdjustmentDetailVOs();

                     for ( SBAdjustmentDetailVO sbAdjustmentDetailVO2 : sbAdjustmentDetailVOs2 )
                     {
                        if ( sbAdjustmentDetailVO2.getItemId().equals( sbAdjustmentDetailVO.getItemId() )
                              && sbAdjustmentDetailVO2.getAccountMonthly().equals( sbAdjustmentDetailVO.getAccountMonthly() ) )
                        {
                           // Detail �ϼ�ֵ���
                           sbAdjustmentDetailVO.addAmountCompany( sbAdjustmentDetailVO2.getAmountCompany() );
                           sbAdjustmentDetailVO.addAmountPersonal( sbAdjustmentDetailVO2.getAmountPersonal() );

                           // Header �ϼ�ֵ���
                           sbAdjustmentHeaderVO.addAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
                           sbAdjustmentHeaderVO.addAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );
                           continue my;
                        }
                     }

                     // �ϼ�ֵ���
                     sbAdjustmentHeaderVO.addAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
                     sbAdjustmentHeaderVO.addAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );
                     sbAdjustmentDetailVOs2.add( sbAdjustmentDetailVO );
                     continue my;
                  }

               }

               // ContractId, Monthly, EmployeeSBId ��ƥ��
               final SBAdjustmentDTO sbAdjustmentDTO = new SBAdjustmentDTO();
               final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = getSBAdjustmentHeaderVO( sbAdjustmentDetailVO );
               sbAdjustmentDTO.setSbAdjustmentHeaderVO( sbAdjustmentHeaderVO );
               sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );
               sbAdjustmentDTOs.add( sbAdjustmentDTO );
               continue;
            }

            // sbAdjustmentDTOsΪ��
            final SBAdjustmentDTO sbAdjustmentDTO = new SBAdjustmentDTO();
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = getSBAdjustmentHeaderVO( sbAdjustmentDetailVO );
            sbAdjustmentDTO.setSbAdjustmentHeaderVO( sbAdjustmentHeaderVO );
            sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );
            sbAdjustmentDTOs.add( sbAdjustmentDTO );
         }
      }

      return sbAdjustmentDTOs;
   }

   // ͨ��SBAdjustmentDetailVO����SBAdjustmentHeaderVO
   private SBAdjustmentHeaderVO getSBAdjustmentHeaderVO( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException
   {
      final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
      sbAdjustmentHeaderVO.setAccountId( sbAdjustmentDetailVO.getAccountId() );
      sbAdjustmentHeaderVO.setCorpId( sbAdjustmentDetailVO.getCorpId() );
      sbAdjustmentHeaderVO.setContractId( sbAdjustmentDetailVO.getContractId() );
      sbAdjustmentHeaderVO.setMonthly( sbAdjustmentDetailVO.getMonthly() );
      sbAdjustmentHeaderVO.setEmployeeSBId( sbAdjustmentDetailVO.getEmployeeSBId() );
      sbAdjustmentHeaderVO.setAmountCompany( sbAdjustmentDetailVO.getAmountCompany() );
      sbAdjustmentHeaderVO.setAmountPersonal( sbAdjustmentDetailVO.getAmountPersonal() );

      // ��ѯ��ӦEmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( sbAdjustmentDetailVO.getContractId() );
      sbAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );
      sbAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
      sbAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
      sbAdjustmentHeaderVO.setClientId( employeeContractVO.getClientId() );
      sbAdjustmentHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );

      sbAdjustmentHeaderVO.setStatus( "1" );

      return sbAdjustmentHeaderVO;
   }

   // ���ɵ���Detail����
   private List< SBAdjustmentDetailVO > getSBAdjustmentDetailVOs( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // ��ʼ������ֵ
      List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs = new ArrayList< SBAdjustmentDetailVO >();

      /** ��ѯ���ݿ������� SBDetailVO ���� */
      // ��ʼ�����ؼ�
      List< Object > sbDetailVOs = new ArrayList< Object >();

      final SBHeaderVO tempSBHeaderVO = new SBHeaderVO();
      tempSBHeaderVO.setAccountId( sbHeaderTempVO.getAccountId() );
      tempSBHeaderVO.setCorpId( sbHeaderTempVO.getCorpId() );
      tempSBHeaderVO.setContractId( sbHeaderTempVO.getContractId() );
      final List< Object > sbHeaderVOs = this.sbHeaderDao.getSBHeaderVOsByCondition( tempSBHeaderVO );

      /** ���SBHeaderVO�����·����籣�����籣�������򹫻�������������籣���������Աȣ�1���籣��2��������3���ۺϣ�4�������� */
      // ��ʼ���Ƿ�����籣�����籣����
      boolean includeSBType = false;

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 1 )
      {
         // �鿴�Ƿ�����籣���͵��籣����
         for ( Object object : sbHeaderVOs )
         {
            SBHeaderVO sbHeaderVO = ( SBHeaderVO ) object;
            if ( sbHeaderVO.getEmployeeSBId() != null )
            {
               final EmployeeContractSBVO employeeContractSBVO = this.employeeContractSBDao.getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( sbHeaderTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );
               if ( socialBenefitSolutionDTO != null
                     && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null
                     && ( "1".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) || "3".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) ) )
               {
                  includeSBType = true;
                  break;
               }
            }
         }
      }

      // ��������籣�����籣�������Ƴ����������������籣����
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 1 )
      {
         if ( includeSBType )
         {
            Iterator< Object > sbHeaderObjectVOIte = sbHeaderVOs.iterator();
            while ( sbHeaderObjectVOIte.hasNext() )
            {
               Object object = ( Object ) sbHeaderObjectVOIte.next();
               SBHeaderVO sbHeaderVO = ( SBHeaderVO ) object;
               if ( sbHeaderVO.getEmployeeSBId() != null )
               {
                  final EmployeeContractSBVO employeeContractSBVO = this.employeeContractSBDao.getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );
                  final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( sbHeaderTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );
                  if ( socialBenefitSolutionDTO != null
                        && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null
                        && ( "2".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) || "4".equals( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getSbType() ) ) )
                  {
                     sbHeaderObjectVOIte.remove();
                  }
               }
            }
         }
      }

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object object : sbHeaderVOs )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) object;
            final SBDetailVO tempSBDetailVO = new SBDetailVO();
            tempSBDetailVO.setAccountId( sbHeaderTempVO.getAccountId() );
            tempSBDetailVO.setCorpId( sbHeaderTempVO.getCorpId() );
            tempSBDetailVO.setContractId( sbHeaderTempVO.getContractId() );
            tempSBDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
            tempSBDetailVO.setStatus( "1,2,3,4" );
            final List< Object > tempSBDetailVOs = this.sbDetailDao.getSBDetailVOsByCondition( tempSBDetailVO );
            sbDetailVOs.addAll( tempSBDetailVOs );
         }
      }

      /** ��ѯ�걨������ SBDetailVO ����*/
      final List< Object > sbDetailTempVOs = fetchSBTempDetailVOs( sbHeaderTempVO );
      // ȡ�·�ͬ�������˵��·�
      final String monthly = sbHeaderTempVO.getMonthly();

      // ��ʼ�����в����·ݼ���
      final Set< String > accountMonthlys = new HashSet< String >();

      // ���� ContractId, AccountMonthly, ItemIdƥ����
      for ( Object sbDetailTempVOObject : sbDetailTempVOs )
      {
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
         accountMonthlys.add( sbDetailTempVO.getAccountMonthly() );

         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            if ( sbDetailVO.getContractId().equals( sbDetailTempVO.getContractId() ) && sbDetailVO.getItemId().equals( sbDetailTempVO.getItemId() )
                  && sbDetailVO.getAccountMonthly().equals( sbDetailTempVO.getAccountMonthly() ) )
            {
               final SBAdjustmentDetailVO sbAdjustmentDetailVO = createSBAdjustmentDetailVO( sbDetailTempVO, sbHeaderTempVO.getAccountId() );

               sbAdjustmentDetailVO.setStatus( "1" );

               // �������ֵ�� -(ϵͳ-����)��
               final String amountCompany = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountCompany() ) - Double.valueOf( sbDetailVO.getAmountCompany() ) );
               final String amountPersonal = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountPersonal() ) - Double.valueOf( sbDetailVO.getAmountPersonal() ) );
               sbAdjustmentDetailVO.setAmountCompany( amountCompany );
               sbAdjustmentDetailVO.setAmountPersonal( amountPersonal );
               sbAdjustmentDetailVOs.add( sbAdjustmentDetailVO );

               // ������޸�
               sbDetailVO.setUpdataed( true );
               sbDetailTempVO.setUpdataed( true );
            }
         }
      }

      // ���ݿ��д���δ��ǵ�
      for ( Object sbDetailVOObject : sbDetailVOs )
      {
         SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;
         if ( !sbDetailVO.isUpdataed() && accountMonthlys != null && accountMonthlys.contains( sbDetailVO.getAccountMonthly() ) )
         {
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = createSBAdjustmentDetailVO( sbDetailVO, sbHeaderTempVO.getAccountId() );
            sbAdjustmentDetailVO.setStatus( "1" );

            // �������ֵ�� -(ϵͳ-����)��
            final String amountCompany = String.valueOf( Double.valueOf( "0" ) - Double.valueOf( sbDetailVO.getAmountCompany() ) );
            final String amountPersonal = String.valueOf( Double.valueOf( "0" ) - Double.valueOf( sbDetailVO.getAmountPersonal() ) );
            sbAdjustmentDetailVO.setAmountCompany( amountCompany );
            sbAdjustmentDetailVO.setAmountPersonal( amountPersonal );

            sbAdjustmentDetailVOs.add( sbAdjustmentDetailVO );
         }
      }

      // ������д���δ��ǵ�
      for ( Object sbDetailTempVOObject : sbDetailTempVOs )
      {
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
         if ( !sbDetailTempVO.isUpdataed() && accountMonthlys != null && accountMonthlys.contains( sbDetailTempVO.getAccountMonthly() ) )
         {
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = createSBAdjustmentDetailVO( sbDetailTempVO, sbHeaderTempVO.getAccountId() );
            sbAdjustmentDetailVO.setStatus( "1" );

            // �������ֵ�� -(ϵͳ-����)��
            final String amountCompany = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountCompany() ) );
            final String amountPersonal = String.valueOf( Double.valueOf( sbDetailTempVO.getAmountPersonal() ) );
            sbAdjustmentDetailVO.setAmountCompany( amountCompany );
            sbAdjustmentDetailVO.setAmountPersonal( amountPersonal );

            sbAdjustmentDetailVOs.add( sbAdjustmentDetailVO );
         }
      }

      // ͳһ���õ��������˵��·�
      if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
      {
         for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDetailVOs )
         {
            sbAdjustmentDetailVO.setMonthly( monthly );
         }
      }

      return sbAdjustmentDetailVOs;
   }

   private SBAdjustmentDetailVO createSBAdjustmentDetailVO( final SBDetailVO sbDetailVO, final String accountId )
   {
      SBAdjustmentDetailVO sbAdjustmentDetailVO = new SBAdjustmentDetailVO();
      sbAdjustmentDetailVO.setAccountId( sbDetailVO.getAccountId() );
      sbAdjustmentDetailVO.setCorpId( sbDetailVO.getCorpId() );
      sbAdjustmentDetailVO.setContractId( sbDetailVO.getContractId() );
      sbAdjustmentDetailVO.setMonthly( sbDetailVO.getMonthly() );
      sbAdjustmentDetailVO.setAccountMonthly( sbDetailVO.getAccountMonthly() );
      sbAdjustmentDetailVO.setItemId( sbDetailVO.getItemId() );
      if ( sbDetailVO.getItemId() != null )
      {
         try
         {
            final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( sbDetailVO.getItemId() );
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         }
         catch ( KANException e )
         {
            e.printStackTrace();
         }
      }
      sbAdjustmentDetailVO.setEmployeeSBId( sbDetailVO.getEmployeeSBId() );
      sbAdjustmentDetailVO.setOrderId( sbDetailVO.getOrderId() );
      sbAdjustmentDetailVO.setEntityId( sbDetailVO.getEntityId() );
      sbAdjustmentDetailVO.setBusinessTypeId( sbDetailVO.getBusinessTypeId() );
      sbAdjustmentDetailVO.setClientId( sbDetailVO.getClientId() );
      sbAdjustmentDetailVO.setClientNameZH( sbDetailVO.getClientNameZH() );
      sbAdjustmentDetailVO.setClientNameEN( sbDetailVO.getClientNameEN() );
      sbAdjustmentDetailVO.setEmployeeId( sbDetailVO.getEmployeeId() );
      sbAdjustmentDetailVO.setEmployeeNameZH( sbDetailVO.getEmployeeNameZH() );
      sbAdjustmentDetailVO.setEmployeeNameEN( sbDetailVO.getEmployeeNameEN() );
      sbAdjustmentDetailVO.setSbNumber( sbDetailVO.getSbNumber() );
      return sbAdjustmentDetailVO;
   }

   private SBAdjustmentDetailVO createSBAdjustmentDetailVO( final SBDetailTempVO sbDetailTempVO, String accountId )
   {
      SBAdjustmentDetailVO sbAdjustmentDetailVO = new SBAdjustmentDetailVO();
      sbAdjustmentDetailVO.setAccountId( sbDetailTempVO.getAccountId() );
      sbAdjustmentDetailVO.setCorpId( sbDetailTempVO.getCorpId() );
      sbAdjustmentDetailVO.setContractId( sbDetailTempVO.getContractId() );
      sbAdjustmentDetailVO.setMonthly( sbDetailTempVO.getMonthly() );
      sbAdjustmentDetailVO.setAccountMonthly( sbDetailTempVO.getAccountMonthly() );
      sbAdjustmentDetailVO.setItemId( sbDetailTempVO.getItemId() );
      if ( sbDetailTempVO.getItemId() != null )
      {
         try
         {
            final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( sbDetailTempVO.getItemId() );
            sbAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            sbAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
         }
         catch ( KANException e )
         {
            e.printStackTrace();
         }
      }
      sbAdjustmentDetailVO.setEmployeeSBId( sbDetailTempVO.getEmployeeSBId() );
      sbAdjustmentDetailVO.setOrderId( sbDetailTempVO.getOrderId() );
      sbAdjustmentDetailVO.setEntityId( sbDetailTempVO.getEntityId() );
      sbAdjustmentDetailVO.setBusinessTypeId( sbDetailTempVO.getBusinessTypeId() );
      sbAdjustmentDetailVO.setClientId( sbDetailTempVO.getClientId() );
      sbAdjustmentDetailVO.setClientNameZH( sbDetailTempVO.getClientNameZH() );
      sbAdjustmentDetailVO.setClientNameEN( sbDetailTempVO.getClientNameEN() );
      sbAdjustmentDetailVO.setEmployeeId( sbDetailTempVO.getEmployeeId() );
      sbAdjustmentDetailVO.setEmployeeNameZH( sbDetailTempVO.getEmployeeNameZH() );
      sbAdjustmentDetailVO.setEmployeeNameEN( sbDetailTempVO.getEmployeeNameEN() );
      sbAdjustmentDetailVO.setSbNumber( sbDetailTempVO.getSbNumber() );
      return sbAdjustmentDetailVO;
   }

   // ���� SBDetailTempVO ����
   private List< Object > fetchSBTempDetailVOs( SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      // ��ʼ������ֵ
      final List< Object > sbDetailTempVOs = new ArrayList< Object >();
      // ��ѯ����״̬��SBDetailVO ����
      final SBDetailTempVO sbDetailTempVO = new SBDetailTempVO();
      sbDetailTempVO.setHeaderId( sbHeaderTempVO.getHeaderId() );
      sbDetailTempVO.setTempStatus( "1" );
      final List< Object > sbDetailTempVOObjects = this.sbDetailTempDao.getSBDetailTempVOsByCondition( sbDetailTempVO );
      sbDetailTempVOs.addAll( sbDetailTempVOObjects );
      return sbDetailTempVOs;
   }

   @Override
   public int insertSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return ( ( SBHeaderTempDao ) getDao() ).insertSBHeaderTemp( sbHeaderTempVO );
   }

   @Override
   public int deleteSBHeaderTemp( final String sbHeaderId ) throws KANException
   {
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderId );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object object : sbDetailTempVOs )
         {
            final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) object;
            final String detailId = sbDetailTempVO.getDetailId();
            this.sbDetailTempDao.deleteSBDetailTemp( detailId );
         }
      }
      return ( ( SBHeaderTempDao ) getDao() ).deleteSBHeaderTemp( sbHeaderId );
   }

   /**  
    * Submit
    *	 �ύ�籣����
    *	@param sbHeaderTempVO
    * @return 
    *	@throws KANException
    */
   @Override
   public int submit( final SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ù�ѡID����
         final String selectedIds = sbHeaderTempVO.getSelectedIds();
         // ��� PageFlag
         final String pageFlag = sbHeaderTempVO.getPageFlag();
         // ���AccountId
         final String accountId = sbHeaderTempVO.getAccountId();

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = selectedIds.split( "," );
            // �ύ��¼��
            submitRows = selectedIdArray.length;

            // ��ȷ�ϡ��޸��籣״̬Ϊ��3��
            final String status = "3";

            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // ����PageFlag�ύ
               if ( pageFlag.equalsIgnoreCase( SBHeaderTempService.PAGE_FLAG_HEADER ) )
               {
                  submitHeaderTemp( accountId, selectId, status, sbHeaderTempVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBHeaderTempService.PAGE_FLAG_DETAIL ) )
               {
                  submitDetailTemp( accountId, selectId, status, sbHeaderTempVO.getModifyBy() );
               }

            }

            // ���Ը����丸����״̬
            trySubmitBatch( sbHeaderTempVO.getBatchId(), status, sbHeaderTempVO.getModifyBy() );
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

   // �����籣����
   private void submitHeaderTemp( final String accountId, final String headerId, final String status, final String userId ) throws KANException
   {
      // ��ʼ���籣������ϸ
      final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( headerId );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         // ����
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;
            submitDetailTemp( accountId, sbDetailTempVO.getDetailId(), status, userId );
         }
      }

      // ����SBHeaderTempVO
      final SBHeaderTempVO sbHeaderTempVO = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOByHeaderId( headerId );
      sbHeaderTempVO.setStatus( status );
      sbHeaderTempVO.setModifyBy( userId );
      sbHeaderTempVO.setModifyDate( new Date() );
      ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO );

   }

   // �����籣������ϸ
   private void submitDetailTemp( final String accountId, final String detailId, final String status, final String userId ) throws KANException
   {
      final SBDetailTempVO sbDetailTempVO = this.sbDetailTempDao.getSBDetailTempVOByDetailId( detailId );

      if ( sbDetailTempVO != null && sbDetailTempVO.getStatus() != null
            && ( sbDetailTempVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) || "4".equals( status ) ) )
      {
         sbDetailTempVO.setStatus( status );
         sbDetailTempVO.setModifyBy( userId );
         sbDetailTempVO.setModifyDate( new Date() );
         this.sbDetailTempDao.updateSBDetailTemp( sbDetailTempVO );
      }

   }

   // �����ύ���� - �ύ�Ӷ��󵫲�����������Ƿ��ύ�����ʹ��
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // ��ʼ��SBHeaderTempVO
         final SBBatchVO sbBatchVO = this.sbBatchDao.getSBBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
         // ��ʼ��SBHeaderTempVO�б�
         final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

         if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
         {
            int headerFlag = 0;

            // ����
            for ( Object sbHeaderTempVOObject : sbHeaderTempVOs )
            {
               final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempVOObject;

               // ��ʼ��SBDetailTempVO�б�
               final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO.getHeaderId() );

               if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
               {
                  int detailFlag = 0;

                  // ����
                  for ( Object sbDetailTempVOObject : sbDetailTempVOs )
                  {
                     final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

                     if ( KANUtil.filterEmpty( sbDetailTempVO.getStatus() ) != null && Integer.valueOf( sbDetailTempVO.getStatus() ) >= Integer.valueOf( status ) )
                     {
                        detailFlag++;
                     }
                  }

                  // ����籣������ϸ��ȫ������Ҫ���ĵ�״̬���޸��籣������״̬
                  if ( detailFlag == sbDetailTempVOs.size() )
                  {
                     sbHeaderTempVO.setStatus( status );
                     sbHeaderTempVO.setModifyBy( userId );
                     sbHeaderTempVO.setModifyDate( new Date() );
                     ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO );
                     submitRows++;

                     // �̱�ȷ��
                     if ( KANUtil.filterEmpty( status ) != null && KANUtil.filterEmpty( status ).equals( "3" ) )
                     {
                        final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderTempVO.getEmployeeSBId() );

                        boolean updated = false;

                        // �����깺��״̬��Ϊ���������ɡ�
                        if ( employeeContractSBVO.getStatus().equals( "2" ) )
                        {
                           employeeContractSBVO.setStatus( "3" );
                           updated = true;
                        }
                        // �����˹���״̬��Ϊ�����˹���
                        else if ( employeeContractSBVO.getStatus().equals( "5" ) )
                        {
                           employeeContractSBVO.setStatus( "6" );
                           updated = true;
                        }

                        if ( updated )
                        {
                           this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
                        }
                     }
                  }
               }

               if ( sbHeaderTempVO.getStatus() != null && !sbHeaderTempVO.getStatus().isEmpty() && Integer.valueOf( sbHeaderTempVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }
            }

            // ����籣������ȫ������Ҫ���ĵ�״̬���޸��籣���ε�״̬
            if ( headerFlag == sbHeaderTempVOs.size() )
            {
               sbBatchVO.setStatus( status );
               sbBatchVO.setModifyBy( userId );
               sbBatchVO.setModifyDate( new Date() );
               this.sbBatchDao.updateSBBatch( sbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   /**  
    * GetSBDTOTempsByCondition
    *	��ѯ��Ч��Ӧ�̵�SBDTOTemp 
    *	@param sbHeaderTempHolder
    *	@throws KANException
    */
   @Override
   public void getSBDTOTempsByCondition( final PagedListHolder sbHeaderTempHolder ) throws KANException
   {
      final SBHeaderTempDao sbHeaderTempDao = ( SBHeaderTempDao ) getDao();
      final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) sbHeaderTempHolder.getObject();

      if ( sbHeaderTempVO.getPageFlag() != null )
      {
         // ����ǹ�Ӧ�̲�
         if ( sbHeaderTempVO.getPageFlag().equals( PAGE_FLAG_VENDOR ) )
         {
            sbHeaderTempHolder.setSource( sbHeaderTempDao.getAmountVendorSBHeaderTempVOsByCondition( sbHeaderTempVO ) );
         }
         // �����HeaderTemp��
         else if ( sbHeaderTempVO.getPageFlag().equals( PAGE_FLAG_HEADER ) )
         {
            sbHeaderTempHolder.setSource( sbHeaderTempDao.getVendorSBHeaderTempVOsByCondition( sbHeaderTempVO ) );
         }
      }

      fetchSBDTOTemp( sbHeaderTempHolder );
   }

   /**  
    * FetchSBDTOTemp
    *	
    *	@param sbHeaderTempHolder
    *	@throws KANException
    */
   private void fetchSBDTOTemp( final PagedListHolder sbHeaderTempHolder ) throws KANException
   {
      // ��ʼ��SBDTOTemp List
      final List< Object > sbDTOTemps = new ArrayList< Object >();
      // ��ʼ���������п�Ŀ�ļ���
      final List< ItemVO > items = new ArrayList< ItemVO >();
      // ���PageFlag
      final String pageFlag = ( ( SBHeaderTempVO ) sbHeaderTempHolder.getObject() ).getPageFlag();
      // ��ʼ��SBDTOTemp�� Size
      int size = 0;

      if ( sbHeaderTempHolder.getSource() != null && sbHeaderTempHolder.getSource().size() > 0 )
      {
         if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_VENDOR ) )
         {

            for ( Object amountVendorSBHeaderTempVOObject : sbHeaderTempHolder.getSource() )
            {
               SBHeaderTempVO amountVendorPaymentSBHeaderTempVO = ( SBHeaderTempVO ) amountVendorSBHeaderTempVOObject;
               // �����ѯ״̬����
               amountVendorPaymentSBHeaderTempVO.setAdditionalStatus( "" );
               // �ֱ��ѯ
               final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getVendorSBHeaderTempVOsByCondition( amountVendorPaymentSBHeaderTempVO );

               if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
               {
                  for ( Object vendorPaymentSBHeaderTempVOObject : sbHeaderTempVOs )
                  {
                     SBHeaderTempVO vendorPaymentSBHeaderTempVO = ( SBHeaderTempVO ) vendorPaymentSBHeaderTempVOObject;
                     // ��ʼ��SBDTOTemp
                     final SBDTOTemp sbDTOTemp = new SBDTOTemp();
                     // װ��SBHeaderTempVO
                     sbDTOTemp.setSbHeaderTempVO( vendorPaymentSBHeaderTempVO );
                     // װ��SBDetailTempVO List
                     fetchSBDetailTemp( sbDTOTemp, vendorPaymentSBHeaderTempVO, items );
                     sbDTOTemps.add( sbDTOTemp );

                     size++;
                  }
               }

            }
         }
         else if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_HEADER ) )
         {
            for ( Object vendorPaymentSBHeaderTempVOObject : sbHeaderTempHolder.getSource() )
            {
               SBHeaderTempVO vendorPaymentSBHeaderTempVO = ( SBHeaderTempVO ) vendorPaymentSBHeaderTempVOObject;
               // ��ʼ��SBDTOTemp
               final SBDTOTemp sbDTOTemp = new SBDTOTemp();
               // װ��SBHeaderTempVO
               sbDTOTemp.setSbHeaderTempVO( vendorPaymentSBHeaderTempVO );
               // װ��SBDetailTempVO List
               fetchSBDetailTemp( sbDTOTemp, vendorPaymentSBHeaderTempVO, items );
               sbDTOTemps.add( sbDTOTemp );

               size++;
            }
         }

      }

      // SBDTOTemp�����ڿ�Ŀ��ӿ�ֵ
      if ( sbDTOTemps != null && sbDTOTemps.size() > 0 )
      {
         fillSBDTOTemps( sbDTOTemps, items );
      }

      sbHeaderTempHolder.setHolderSize( size );
      sbHeaderTempHolder.setSource( sbDTOTemps );
   }

   // װ���籣��ϸ
   private void fetchSBDetailTemp( final SBDTOTemp sbDTOTemp, final SBHeaderTempVO sbHeaderTempVO, final List< ItemVO > items ) throws KANException
   {
      // ��ʼ����ѯ����
      final SBDetailTempVO sbDetailTempVO = new SBDetailTempVO();

      sbDetailTempVO.setHeaderId( sbHeaderTempVO.getHeaderId() );
      sbDetailTempVO.setStatus( sbHeaderTempVO.getAdditionalStatus() );

      // ��ʼ����װ���籣��ϸ
      final List< Object > sbDetailTempVOs = this.getSbDetailTempDao().getSBDetailTempVOsByCondition( sbDetailTempVO );

      if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
      {
         for ( Object sbDetailTempVOObject : sbDetailTempVOs )
         {
            final SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) sbDetailTempVOObject;

            // ����ϼ�ֵ
            BigDecimal amountCompany = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderTempVO.getAmountCompany() ) == null ) ? "0" : sbHeaderTempVO.getAmountCompany() ).add( new BigDecimal( tempSBDetailTempVO.getAmountCompany() == null ? "0"
                  : tempSBDetailTempVO.getAmountCompany() ) ) );
            BigDecimal amountPersonal = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderTempVO.getAmountPersonal() ) == null ) ? "0" : sbHeaderTempVO.getAmountPersonal() ).add( new BigDecimal( tempSBDetailTempVO.getAmountPersonal() == null ? "0"
                  : tempSBDetailTempVO.getAmountPersonal() ) ) );

            sbHeaderTempVO.setAmountCompany( amountCompany.toString() );
            sbHeaderTempVO.setAmountPersonal( amountPersonal.toString() );

            sbDTOTemp.getSbDetailTempVOs().add( ( SBDetailTempVO ) sbDetailTempVOObject );

            // ��ʼ����Ŀ
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( tempSBDetailTempVO.getItemId() );
            itemVO.setItemType( tempSBDetailTempVO.getItemType() );

            // �����Ŀ�����������
            if ( !isItemExist( itemVO, items ) )
            {
               items.add( itemVO );
            }

         }

      }
   }

   // SBDTOTemp������䲻���ڿ�Ŀ
   private void fillSBDTOTemps( final List< Object > sbDTOTemps, final List< ItemVO > items )
   {
      for ( ItemVO itemVO : items )
      {
         for ( Object sbDTOTempObject : sbDTOTemps )
         {
            final List< SBDetailTempVO > copyList = new ArrayList< SBDetailTempVO >();
            final SBDTOTemp sbDTOTemp = ( SBDTOTemp ) sbDTOTempObject;
            // ���SBDTOTemp��ӦSBHeaderTempVO��SBDetailTempVO����
            final SBHeaderTempVO sbHeaderTempVO = sbDTOTemp.getSbHeaderTempVO();
            List< SBDetailTempVO > sbDetailTempVOs = sbDTOTemp.getSbDetailTempVOs();
            // COPY�Ѵ���SBDTOTemp����
            copyList.addAll( sbDetailTempVOs );

            // ���ҵ�ǰSBDetailTempVO�Ƿ���ڸÿ�Ŀ�������������
            fetchItemExistSbDetailTempVOs( itemVO, sbHeaderTempVO, sbDetailTempVOs, copyList );

            // ��������SBDTOTemp��SBDetailTempVO����
            sbDTOTemp.setSbDetailTempVOs( copyList );
         }
      }

   }

   /**  
    * IsItemExistSbDetailTempVOs
    * �ж�ItemVO�Ƿ�����籣��ϸ����
    * @param itemVO
    * @param items
    * @return
    */
   private void fetchItemExistSbDetailTempVOs( final ItemVO itemVO, final SBHeaderTempVO sbHeaderTempVO, final List< SBDetailTempVO > sbDetailTempVOs,
         final List< SBDetailTempVO > returnDetailTempVOs )
   {
      Boolean existFlag = false;

      // �жϿ�Ŀ�Ƿ����
      if ( sbDetailTempVOs == null || sbDetailTempVOs.size() == 0 )
      {
         existFlag = false;
      }
      else
      {
         for ( SBDetailTempVO sbDetailTempVO : sbDetailTempVOs )
         {
            if ( itemVO.getItemId().equals( sbDetailTempVO.getItemId() ) )
            {
               existFlag = true;
               break;
            }
         }
      }

      // �����Ŀ�����������
      if ( !existFlag )
      {
         // ��ʼ��SBDetailTempVO�������
         final SBDetailTempVO tempDetailTempVO = new SBDetailTempVO();
         tempDetailTempVO.setHeaderId( sbHeaderTempVO.getHeaderId() );
         tempDetailTempVO.setStatus( sbHeaderTempVO.getStatus() );
         tempDetailTempVO.setItemId( itemVO.getItemId() );
         tempDetailTempVO.setItemType( itemVO.getItemType() );
         returnDetailTempVOs.add( tempDetailTempVO );
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
   public int updateBatch( final CommonBatchVO commonBatchVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();
         int num = 0;

         // ��ʼ����ѯ����
         final SBHeaderTempVO sbHeaderTempVO = new SBHeaderTempVO();
         sbHeaderTempVO.setAccountId( commonBatchVO.getAccountId() );
         sbHeaderTempVO.setBatchId( commonBatchVO.getBatchId() );
         // ֻ�����½�״̬SBHeaderTempVO����
         sbHeaderTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_NEW );
         // ��ʼ�� SBAdjustmentDetailVO ����
         List< SBAdjustmentDetailVO > sbAdjustmentDetailVOs = new ArrayList< SBAdjustmentDetailVO >();

         final List< Object > sbHeaderTempList = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByCondition( sbHeaderTempVO );

         if ( sbHeaderTempList != null && sbHeaderTempList.size() > 0 )
         {
            for ( Object object : sbHeaderTempList )
            {
               SBHeaderTempVO tempSBHeaderTempVO = ( SBHeaderTempVO ) object;
               sbAdjustmentDetailVOs.addAll( getSBAdjustmentDetailVOs( tempSBHeaderTempVO ) );
            }
         }

         final List< SBAdjustmentDTO > sbAdjustmentDTOs = fetchSBAdjustmentDTO( sbAdjustmentDetailVOs );

         // �������ݲ������ݿ�
         if ( sbAdjustmentDTOs != null && sbAdjustmentDTOs.size() > 0 )
         {
            num = sbAdjustmentDTOs.size();
            // ���� SBAdjustmentHeaderVO
            for ( SBAdjustmentDTO sbAdjustmentDTO : sbAdjustmentDTOs )
            {
               final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentDTO.getSbAdjustmentHeaderVO();
               sbAdjustmentHeaderVO.setCreateBy( sbHeaderTempVO.getModifyBy() );
               sbAdjustmentHeaderVO.setCreateDate( sbHeaderTempVO.getModifyDate() );
               sbAdjustmentHeaderVO.setModifyBy( sbHeaderTempVO.getModifyBy() );
               sbAdjustmentHeaderVO.setModifyDate( sbHeaderTempVO.getModifyDate() );
               this.sbAdjustmentHeaderDao.insertSBAdjustmentHeader( sbAdjustmentHeaderVO );

               // ���� SBAdjustmentDetailVO
               if ( sbAdjustmentDTO.getSbAdjustmentDetailVOs() != null && sbAdjustmentDTO.getSbAdjustmentDetailVOs().size() > 0 )
               {
                  for ( SBAdjustmentDetailVO sbAdjustmentDetailVO : sbAdjustmentDTO.getSbAdjustmentDetailVOs() )
                  {
                     sbAdjustmentDetailVO.setAdjustmentHeaderId( sbAdjustmentDTO.getSbAdjustmentHeaderVO().getAdjustmentHeaderId() );
                     sbAdjustmentDetailVO.setCreateBy( sbHeaderTempVO.getModifyBy() );
                     sbAdjustmentDetailVO.setCreateDate( sbHeaderTempVO.getModifyDate() );
                     sbAdjustmentDetailVO.setModifyBy( sbHeaderTempVO.getModifyBy() );
                     sbAdjustmentDetailVO.setModifyDate( sbHeaderTempVO.getModifyDate() );
                     this.sbAdjustmentDetailDao.insertSBAdjustmentDetail( sbAdjustmentDetailVO );
                  }
               }
            }
         }

         // ���α�ʾΪ�Ѹ���
         commonBatchVO.setStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
         this.commonBatchDao.updateCommonBatch( commonBatchVO );

         // ���ζ�Ӧ�ӱ���Ϣ��ʾΪ�Ѹ���
         final List< Object > sbHeaderTempVOs = ( ( SBHeaderTempDao ) getDao() ).getSBHeaderTempVOsByBatchId( commonBatchVO.getBatchId() );

         if ( sbHeaderTempVOs != null && sbHeaderTempVOs.size() > 0 )
         {
            for ( Object object : sbHeaderTempVOs )
            {
               SBHeaderTempVO sbHeaderTempVO2 = ( SBHeaderTempVO ) object;
               // SBHeaderTempVO���Ϊ�Ѹ���
               sbHeaderTempVO2.setTempStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
               ( ( SBHeaderTempDao ) getDao() ).updateSBHeaderTemp( sbHeaderTempVO2 );

               // SBHeaderTempVO��Ӧ��SBDetailTempVO���ϱ��Ϊ�Ѹ���
               final List< Object > sbDetailTempVOs = this.sbDetailTempDao.getSBDetailTempVOsByHeaderId( sbHeaderTempVO2.getHeaderId() );

               if ( sbDetailTempVOs != null && sbDetailTempVOs.size() > 0 )
               {
                  for ( Object object3 : sbDetailTempVOs )
                  {
                     SBDetailTempVO tempSBDetailTempVO = ( SBDetailTempVO ) object3;
                     tempSBDetailTempVO.setTempStatus( SBHeaderTempVO.TEMPSTATUS_UPDATED );
                     this.sbDetailTempDao.updateSBDetailTemp( tempSBDetailTempVO );
                  }
               }
            }
         }

         // �ύ����
         this.commitTransaction();

         return num;
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public int updateSBHeaderTemp( SBHeaderTempVO sbHeaderTempVO ) throws KANException
   {
      return 0;
   }
}
