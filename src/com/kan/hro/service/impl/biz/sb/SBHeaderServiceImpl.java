package com.kan.hro.service.impl.biz.sb;

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
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;

public class SBHeaderServiceImpl extends ContextService implements SBHeaderService
{
   // ע��SBBatchDao
   private SBBatchDao sbBatchDao;

   // ע��SBDetailDao
   private SBDetailDao sbDetailDao;

   // ע��EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   public SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   /**  
    * GetSBHeaderVOsByCondition
    *	 ��á�SBHeaderVO�������SBHeaderVO����
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      SBHeaderVO sbHeaderVO = ( SBHeaderVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( sbHeaderDao.countSBHeaderVOsByCondition( sbHeaderVO ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getSBHeaderVOsByCondition( sbHeaderVO ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVOTemp = ( SBHeaderVO ) sbHeaderVOObject;
            sbHeaderVOTemp.setRulePublic( sbHeaderVO.getRulePublic() );
            sbHeaderVOTemp.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            sbHeaderVOTemp.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            sbHeaderVOTemp.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            sbHeaderVOTemp.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            sbHeaderVOTemp.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            countSBHeaderVOAmount( sbHeaderVOTemp );
         }
      }

      return pagedListHolder;
   }

   /**  
    * GetSBContractVOsByCondition
    *	  ��ð�������Э�顡�����SBHeaderVO����
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getSBContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();

      pagedListHolder.setHolderSize( sbHeaderDao.countSBContractVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getSBContractVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getSBContractVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            fetchContractSBHeaderVO( sbHeaderVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * GetAmountVendorSBHeaderVOsByCondition
    *	��ð����й�Ӧ�̰��·ݷ�����SBHeaderVO���ϻ�����Ϣ
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getAmountVendorSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      SBHeaderVO headerVO = ( SBHeaderVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( sbHeaderDao.countAmountVendorSBHeaderVOsByCondition( headerVO ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getAmountVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getAmountVendorSBHeaderVOsByCondition( headerVO ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // ��ӻ�����Ϣ
            sbHeaderVO.setRulePublic( headerVO.getRulePublic() );
            sbHeaderVO.setRulePrivateIds( headerVO.getRulePrivateIds() );
            sbHeaderVO.setRulePositionIds( headerVO.getRulePositionIds() );
            sbHeaderVO.setRuleBranchIds( headerVO.getRuleBranchIds() );
            sbHeaderVO.setRuleBusinessTypeIds( headerVO.getRuleBusinessTypeIds() );
            sbHeaderVO.setRuleEntityIds( headerVO.getRuleEntityIds() );
            fetchAmountVendorPaymentSBHeaderVO( sbHeaderVO );
         }
      }

      // ���SBHeaderVO �µ�

      return pagedListHolder;
   }

   /**  
    * GetVendorSBHeaderVOsByCondition
    *	 ���ָ����Ӧ�̵�SBHeaderVO������Ϣ
    *	@param pagedListHolder
    *	@param isPaged
    *	@return
    *	@throws KANException
    */
   @Override
   public PagedListHolder getVendorSBHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      pagedListHolder.setHolderSize( sbHeaderDao.countVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbHeaderDao.getVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbHeaderDao.getVendorSBHeaderVOsByCondition( ( SBHeaderVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object sbHeaderVOObject : pagedListHolder.getSource() )
         {
            final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // ���������Ϣ
            fetchVendorPaymentSBHeaderVO( sbHeaderVO );
         }
      }

      // ���SBHeaderVO �µ�

      return pagedListHolder;
   }

   /**  
    * CountSBHeaderVOAmount
    * ���㵥��SBHeaderVO�ķ��úϼ�
    * @param sbHeaderVO
    * @throws KANException
    */
   private void countSBHeaderVOAmount( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( sbHeaderVO );
      // ��ʼ�������������·�
      Set< String > accountMonthlySet = new HashSet< String >();

      // �������ݺϼ�
      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // ��ʼ���ϼ�ֵ
         BigDecimal amountCompany = new BigDecimal( 0 );
         BigDecimal amountPersonal = new BigDecimal( 0 );
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // ���״̬ƥ�������
            if ( sbDetailVO.getStatus().equals( sbHeaderVO.getAdditionalStatus() ) )
            {
               amountCompany = amountCompany.add( new BigDecimal( sbDetailVO.getAmountCompany() ) );
               amountPersonal = amountPersonal.add( new BigDecimal( sbDetailVO.getAmountPersonal() ) );
            }

            if ( sbDetailVO.getAccountMonthly() != null )
            {
               accountMonthlySet.add( sbDetailVO.getAccountMonthly() );
            }
         }
         sbHeaderVO.setAmountCompany( amountCompany.toString() );
         sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

         if ( accountMonthlySet.size() > 0 )
         {
            final String[] accountMonthlys = accountMonthlySet.toArray( new String[ 0 ] );
            sbHeaderVO.setAccountMonthlys( accountMonthlys );
         }
      }

   }

   /**  
    * CountSBHeaderVOAmount
    * ���㵥��SBHeaderVO�ķ��úϼƣ���ѯ��Ӧ����Ϣ�ã�����״̬��
    * @param sbHeaderVO
    * @throws KANException
    */
   private void countVendorSBHeaderVOAmount( final SBHeaderVO sbHeaderVO ) throws KANException
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

   /**  
    * FetchContractSBHeaderVO
    * ��������Э���籣��Ϣ����
    * @param sbHeaderVO
    * @param employeeVOs
    * @throws KANException
    */
   private void fetchContractSBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      sbHeaderVO.setStatus( sbHeaderVO.getAdditionalStatus() );
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // ��ʼ���ϼ�ֵ
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // ���Э���Ӧ��SBHeaderVO ����
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // �ֱ����ϼ�
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object tempSBHeaderVOObject : sbHeaderVOs )
         {
            // ����ϼ�ֵ
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) tempSBHeaderVOObject;
            countSBHeaderVOAmount( tempSBHeaderVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderVO.getAmountPersonal() ) );

            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            // ��Ա�����������
            if ( isEmployeeVOExist( tempSBHeaderVO, employeeMappingVOs ) )
            {
               continue;
            }

            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderVO.setEmployees( employeeMappingVOs );
      }
   }

   /**  
    * FetchVendorPaymentSBHeaderVO
    * ������Ӧ���籣��Ϣ���ƣ�������Ϣ��
    * @param sbHeaderVO
    * @throws KANException 
    */
   private void fetchAmountVendorPaymentSBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // ��ѯ�������״̬
      sbHeaderVO.setStatus( "" );
      // ��ʼ���ϼ�ֵ
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // ��ø���Ӧ�̸��·ݶ�Ӧ��SBHeaderVO ����
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // �ֱ����ϼ�
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object tempSBHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) tempSBHeaderVOObject;
            
            tempSBHeaderVO.setRulePublic( sbHeaderVO.getRulePublic() );
            tempSBHeaderVO.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            tempSBHeaderVO.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            tempSBHeaderVO.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            tempSBHeaderVO.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            tempSBHeaderVO.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            
            countVendorSBHeaderVOAmount( tempSBHeaderVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderVO.getAmountPersonal() ) );
            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            // ��Ա�����������
            if ( isEmployeeVOExist( tempSBHeaderVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderVO.setEmployees( employeeMappingVOs );
      }

   }

   /**  
    * FetchVendorPaymentSBHeaderVO
    * ������Ӧ���籣��Ϣ���ƣ���״̬��ʾ��
    * @param sbHeaderVO
    * @throws KANException 
    */
   private void fetchVendorPaymentSBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();
      // ��ȡSBHeaderVO��ԭ��״ֵ̬
      final String status = sbHeaderVO.getStatus();

      // ���ò�ѯ״̬
      sbHeaderVO.setStatus( sbHeaderVO.getAdditionalStatus() );
      // ��ʼ���ϼ�ֵ
      BigDecimal amountCompany = new BigDecimal( 0 );
      BigDecimal amountPersonal = new BigDecimal( 0 );
      // ��ø���Ӧ�̸��·ݶ�Ӧ��SBHeaderVO ����
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // �ֱ����ϼ�
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object tempSBHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) tempSBHeaderVOObject;
            countSBHeaderVOAmount( tempSBHeaderVO );

            amountCompany = amountCompany.add( new BigDecimal( tempSBHeaderVO.getAmountCompany() ) );
            amountPersonal = amountPersonal.add( new BigDecimal( tempSBHeaderVO.getAmountPersonal() ) );
            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            // ��Ա�����������
            if ( isEmployeeVOExist( tempSBHeaderVO, employeeMappingVOs ) )
            {
               continue;
            }
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( tempSBHeaderVO.getEmployeeId() );
            mappingVO.setMappingValue( tempSBHeaderVO.getEmployeeNameZH() );
            mappingVO.setMappingTemp( tempSBHeaderVO.getEmployeeNameEN() );
            employeeMappingVOs.add( mappingVO );
         }
         sbHeaderVO.setEmployees( employeeMappingVOs );
      }

      // ��ԭ״ֵ̬
      sbHeaderVO.setStatus( status );
   }

   /**  
    * IsEmployeeVOExist
    *	�жϹ�Ա�Ƿ����
    *	@param tempSBHeaderVO
    *	@param employeeMappingVOs
    *	@return
    */
   private boolean isEmployeeVOExist( final SBHeaderVO SBHeaderVO, final List< MappingVO > employeeMappingVOs )
   {
      if ( employeeMappingVOs != null && employeeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : employeeMappingVOs )
         {
            if ( mappingVO.getMappingId().equals( SBHeaderVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public List< Object > getSBHeaderVOsByBatchId( final String sbBatchId ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByBatchId( sbBatchId );

      // ����ϼ�ֵ
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            countSBHeaderVOAmount( sbHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public SBHeaderVO getSBHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      final SBHeaderVO sbHeaderVO = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOByHeaderId( headerId );

      // ����ϼ�ֵ
      if ( sbHeaderVO != null )
      {
         countSBHeaderVOAmount( sbHeaderVO );
      }

      return sbHeaderVO;
   }

   @Override
   public List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbContractHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBContractVOsByCondition( sbHeaderVO );

      if ( sbContractHeaderVOs != null && sbContractHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbContractHeaderVOs )
         {
            SBHeaderVO sbContractHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            fetchContractSBHeaderVO( sbContractHeaderVO );
         }
      }

      return sbContractHeaderVOs;
   }

   @Override
   public List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      // ����ϼ�ֵ
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            countSBHeaderVOAmount( tempSBHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getAmountVendorSBHeaderVOsByCondition( sbHeaderVO );

      // ����ϼ�ֵ
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            
            tempSBHeaderVO.setCorpId( sbHeaderVO.getCorpId() );
            tempSBHeaderVO.setRulePublic( sbHeaderVO.getRulePublic() );
            tempSBHeaderVO.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            tempSBHeaderVO.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            tempSBHeaderVO.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            tempSBHeaderVO.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            tempSBHeaderVO.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            fetchAmountVendorPaymentSBHeaderVO( tempSBHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getVendorSBHeaderVOsByCondition( sbHeaderVO );

      // ����ϼ�ֵ
      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            tempSBHeaderVO.setRulePublic( sbHeaderVO.getRulePublic() );
            tempSBHeaderVO.setRulePrivateIds( sbHeaderVO.getRulePrivateIds() );
            tempSBHeaderVO.setRulePositionIds( sbHeaderVO.getRulePositionIds() );
            tempSBHeaderVO.setRuleBranchIds( sbHeaderVO.getRuleBranchIds() );
            tempSBHeaderVO.setRuleBusinessTypeIds( sbHeaderVO.getRuleBusinessTypeIds() );
            tempSBHeaderVO.setRuleEntityIds( sbHeaderVO.getRuleEntityIds() );
            fetchVendorPaymentSBHeaderVO( tempSBHeaderVO );
         }
      }

      return sbHeaderVOs;
   }

   @Override
   public List< SBDTO > getSBDTOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      // ��ʼ��SBDTO List
      final List< SBDTO > sbDTOs = new ArrayList< SBDTO >();
      // ��ʼ��SBHeaderVO List
      final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( sbHeaderVO );

      if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
      {
         for ( Object sbHeaderVOObject : sbHeaderVOs )
         {
            // ��ʼ���������
            final SBHeaderVO tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;
            // ��ʼ��SBDTO
            final SBDTO sbDTO = new SBDTO();

            // װ��SBHeaderVO
            sbDTO.setSbHeaderVO( tempSBHeaderVO );

            // װ��SBDetailVO List
            fetchSBDetail( sbDTO, tempSBHeaderVO.getHeaderId(), sbHeaderVO.getStatus() );

            sbDTOs.add( sbDTO );
         }
      }

      return sbDTOs;
   }

   // װ���籣��ϸ
   private void fetchSBDetail( final SBDTO sbDTO, final String headerId, final String status ) throws KANException
   {
      // ��ʼ����װ���籣��ϸ
      SBHeaderVO sbHeaderVO = new SBHeaderVO();
      sbHeaderVO.setHeaderId( headerId );
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
         }
      }
   }

   @Override
   public int updateSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      int updateRows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��������Э���籣��������״̬
         // Added by Kevin Jin at 2014-07-17
         if ( KANUtil.filterEmpty( sbHeaderVO.getFlag() ) != null && KANUtil.filterEmpty( sbHeaderVO.getFlag() ).equals( "1" ) )
         {
            final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );

            if ( KANUtil.filterEmpty( employeeContractSBVO.getFlag() ) == null || !KANUtil.filterEmpty( employeeContractSBVO.getFlag() ).equals( "1" ) )
            {
               employeeContractSBVO.setFlag( "1" );
               this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
            }
         }

         updateRows = ( ( SBHeaderDao ) getDao() ).updateSBHeader( sbHeaderVO );

         // �ύ����
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return updateRows;
   }

   @Override
   public int insertSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).insertSBHeader( sbHeaderVO );
   }

   @Override
   public int deleteSBHeader( final String sbHeaderId ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).deleteSBHeader( sbHeaderId );
   }

   /**  
    * Submit
    *	 �ύ�籣����
    *	@param sbHeaderVO
    * @return 
    *	@throws KANException
    */
   @Override
   public int submit( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      int submitRows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ù�ѡID����
         final String selectedIds = sbHeaderVO.getSelectedIds();
         // ��� PageFlag
         final String pageFlag = sbHeaderVO.getPageFlag();
         // ���AccountId
         final String accountId = sbHeaderVO.getAccountId();

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
               if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_HEADER ) )
               {
                  submitHeader( accountId, selectId, status, sbHeaderVO.getModifyBy() );
               }
               else if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_DETAIL ) )
               {
                  submitDetail( accountId, selectId, status, sbHeaderVO.getModifyBy() );
               }

            }

            // ���Ը����丸����״̬
            trySubmitBatch( sbHeaderVO.getBatchId(), status, sbHeaderVO.getModifyBy() );
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
   private void submitHeader( final String accountId, final String headerId, final String status, final String userId ) throws KANException
   {
      // ��ʼ���籣������ϸ
      SBHeaderVO headerVO = new SBHeaderVO();
      headerVO.setHeaderId( headerId );
      final List< Object > sbDetailVOs = this.sbDetailDao.getSBDetailVOsByHeaderId( headerVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         // ����
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;
            submitDetail( accountId, sbDetailVO.getDetailId(), status, userId );
         }
      }

      // ����SBHeaderVO
      final SBHeaderVO sbHeaderVO = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOByHeaderId( headerId );
      sbHeaderVO.setStatus( status );
      sbHeaderVO.setModifyBy( userId );
      sbHeaderVO.setModifyDate( new Date() );
      ( ( SBHeaderDao ) getDao() ).updateSBHeader( sbHeaderVO );

   }

   // �����籣������ϸ
   private void submitDetail( final String accountId, final String detailId, final String status, final String userId ) throws KANException
   {
      final SBDetailVO sbDetailVO = this.sbDetailDao.getSBDetailVOByDetailId( detailId );

      if ( sbDetailVO != null && sbDetailVO.getStatus() != null && ( sbDetailVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) || "4".equals( status ) ) )
      {
         sbDetailVO.setStatus( status );
         sbDetailVO.setModifyBy( userId );
         sbDetailVO.setModifyDate( new Date() );
         this.sbDetailDao.updateSBDetail( sbDetailVO );
      }

   }

   // �����ύ���� - �ύ�Ӷ��󵫲�����������Ƿ��ύ�����ʹ��
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = 0;

      if ( status != null && !status.isEmpty() )
      {
         // ��ʼ��SBHeaderVO
         final SBBatchVO sbBatchVO = this.sbBatchDao.getSBBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
         // ��ʼ��SBHeaderVO�б�
         final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

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
                  }

                  // ����籣������ϸ��ȫ������Ҫ���ĵ�״̬���޸��籣������״̬
                  if ( detailFlag == sbDetailVOs.size() )
                  {
                     // ����籣������ϸ��ȫ������Ҫ���ĵ�״̬��״̬����ȫ����Ҫ�޸ĵ�״ֵ̬���޸��籣������״̬
                     if ( upperCount != sbDetailVOs.size() )
                     {
                        sbHeaderVO.setStatus( status );
                     }
                     sbHeaderVO.setModifyBy( userId );
                     sbHeaderVO.setModifyDate( new Date() );
                     ( ( SBHeaderDao ) getDao() ).updateSBHeader( sbHeaderVO );
                     submitRows++;

                     // �̱�ȷ��
                     if ( KANUtil.filterEmpty( status ) != null && KANUtil.filterEmpty( status ).equals( "3" ) )
                     {
                        final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );

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
               this.sbBatchDao.updateSBBatch( sbBatchVO );
               submitRows++;
            }
         }
      }

      return submitRows;
   }

   /**  
    * GetSBDTOsByCondition
    *	��ѯ��Ч��Ӧ�̵�SBDTO 
    *	@param sbHeaderHolder
    *	@throws KANException
    */
   @Override
   public void getSBDTOsByCondition( final PagedListHolder sbHeaderHolder ) throws KANException
   {
      final SBHeaderDao sbHeaderDao = ( SBHeaderDao ) getDao();
      final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderHolder.getObject();

      if ( sbHeaderVO.getPageFlag() != null )
      {
         // ����ǹ�Ӧ�̲�
         if ( sbHeaderVO.getPageFlag().equals( PAGE_FLAG_VENDOR ) )
         {
            sbHeaderHolder.setSource( sbHeaderDao.getAmountVendorSBHeaderVOsByCondition( sbHeaderVO ) );
         }
         // �����Header��
         else if ( sbHeaderVO.getPageFlag().equals( PAGE_FLAG_HEADER ) )
         {
            sbHeaderHolder.setSource( sbHeaderDao.getVendorSBHeaderVOsByCondition( sbHeaderVO ) );
         }
      }

      fetchSBDTO( sbHeaderHolder );
   }

   /**  
    * FetchSBDTO
    *	
    *	@param sbHeaderHolder
    *	@throws KANException
    */
   private void fetchSBDTO( final PagedListHolder sbHeaderHolder ) throws KANException
   {
      // ��ʼ��SBDTO List
      final List< Object > sbDTOs = new ArrayList< Object >();
      // ��ʼ���������п�Ŀ�ļ���
      final List< ItemVO > items = new ArrayList< ItemVO >();
      // ���PageFlag
      final String pageFlag = ( ( SBHeaderVO ) sbHeaderHolder.getObject() ).getPageFlag();
      // ��ʼ��SBDTO�� Size
      int size = 0;

      if ( sbHeaderHolder.getSource() != null && sbHeaderHolder.getSource().size() > 0 )
      {
         if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_VENDOR ) )
         {

            for ( Object amountVendorSBHeaderVOObject : sbHeaderHolder.getSource() )
            {
               SBHeaderVO amountVendorPaymentSBHeaderVO = ( SBHeaderVO ) amountVendorSBHeaderVOObject;
               // �����ѯ״̬����
               amountVendorPaymentSBHeaderVO.setAdditionalStatus( "" );
               // �ֱ��ѯ
               final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getVendorSBHeaderVOsByCondition( amountVendorPaymentSBHeaderVO );

               if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
               {
                  for ( Object vendorPaymentSBHeaderVOObject : sbHeaderVOs )
                  {
                     SBHeaderVO vendorPaymentSBHeaderVO = ( SBHeaderVO ) vendorPaymentSBHeaderVOObject;
                     // ��ʼ��SBDTO
                     final SBDTO sbDTO = new SBDTO();
                     // װ��SBHeaderVO
                     sbDTO.setSbHeaderVO( vendorPaymentSBHeaderVO );
                     // װ��SBDetailVO List
                     fetchSBDetail( sbDTO, vendorPaymentSBHeaderVO, items );
                     sbDTOs.add( sbDTO );

                     size++;
                  }
               }

            }
         }
         else if ( pageFlag.equalsIgnoreCase( PAGE_FLAG_HEADER ) )
         {
            for ( Object vendorPaymentSBHeaderVOObject : sbHeaderHolder.getSource() )
            {
               SBHeaderVO vendorPaymentSBHeaderVO = ( SBHeaderVO ) vendorPaymentSBHeaderVOObject;
               // ��ʼ��SBDTO
               final SBDTO sbDTO = new SBDTO();
               // װ��SBHeaderVO
               sbDTO.setSbHeaderVO( vendorPaymentSBHeaderVO );
               // װ��SBDetailVO List
               fetchSBDetail( sbDTO, vendorPaymentSBHeaderVO, items );
               sbDTOs.add( sbDTO );

               size++;
            }
         }

      }

      // SBDTO�����ڿ�Ŀ��ӿ�ֵ
      if ( sbDTOs != null && sbDTOs.size() > 0 )
      {
         fillSBDTOs( sbDTOs, items );
      }

      sbHeaderHolder.setHolderSize( size );
      sbHeaderHolder.setSource( sbDTOs );
   }

   // װ���籣��ϸ
   private void fetchSBDetail( final SBDTO sbDTO, final SBHeaderVO sbHeaderVO, final List< ItemVO > items ) throws KANException
   {
      // ��ʼ����ѯ����
      final SBDetailVO sbDetailVO = new SBDetailVO();

      sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
      sbDetailVO.setStatus( sbHeaderVO.getAdditionalStatus() );

      // ��ʼ����װ���籣��ϸ
      final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByCondition( sbDetailVO );

      if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
      {
         for ( Object sbDetailVOObject : sbDetailVOs )
         {
            final SBDetailVO tempSBDetailVO = ( SBDetailVO ) sbDetailVOObject;

            // ����ϼ�ֵ
            BigDecimal amountCompany = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderVO.getAmountCompany() ) == null ) ? "0" : sbHeaderVO.getAmountCompany() ).add( new BigDecimal( tempSBDetailVO.getAmountCompany() ) ) );
            BigDecimal amountPersonal = ( new BigDecimal( ( KANUtil.filterEmpty( sbHeaderVO.getAmountPersonal() ) == null ) ? "0" : sbHeaderVO.getAmountPersonal() ).add( new BigDecimal( tempSBDetailVO.getAmountPersonal() ) ) );

            sbHeaderVO.setAmountCompany( amountCompany.toString() );
            sbHeaderVO.setAmountPersonal( amountPersonal.toString() );

            sbDTO.getSbDetailVOs().add( ( SBDetailVO ) sbDetailVOObject );

            // ��ʼ����Ŀ
            final ItemVO itemVO = new ItemVO();
            itemVO.setItemId( tempSBDetailVO.getItemId() );
            itemVO.setItemType( tempSBDetailVO.getItemType() );

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
    * �ж�ItemVO�Ƿ�����籣��ϸ����
    * @param itemVO
    * @param items
    * @return
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
   public List< Object > getMonthliesBySBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).getMonthliesBySBHeaderVO( sbHeaderVO );
   }

   @Override
   public int updateSBHeaderPaid( SBHeaderVO sbHeaderVO ) throws KANException
   {
      int updateRow = 0;

      try
      {
         // ��������
         startTransaction();

         // ��������Э���籣��������״̬
         // Added by Kevin Jin at 2014-07-16
         if ( sbHeaderVO != null && sbHeaderVO.getCertificateNumbers() != null && sbHeaderVO.getCertificateNumbers().size() > 0 )
         {
            for ( String certificateNumber : sbHeaderVO.getCertificateNumbers() )
            {
               SBHeaderVO tempSBHeaderVO = new SBHeaderVO();
               tempSBHeaderVO.setAccountId( sbHeaderVO.getAccountId() );
               tempSBHeaderVO.setBatchId( sbHeaderVO.getBatchId() );
               tempSBHeaderVO.setCertificateNumber( certificateNumber );

               final List< Object > sbHeaderVOs = ( ( SBHeaderDao ) getDao() ).getSBHeaderVOsByCondition( tempSBHeaderVO );

               if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
               {
                  for ( Object sbHeaderVOObject : sbHeaderVOs )
                  {
                     final EmployeeContractSBVO employeeContractSBVO = this.getEmployeeContractSBDao().getEmployeeContractSBVOByEmployeeSBId( ( ( SBHeaderVO ) sbHeaderVOObject ).getEmployeeSBId() );

                     if ( KANUtil.filterEmpty( employeeContractSBVO.getFlag() ) == null || !KANUtil.filterEmpty( employeeContractSBVO.getFlag() ).equals( "1" ) )
                     {
                        employeeContractSBVO.setFlag( "1" );
                        this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
                     }
                  }
               }
            }
         }

         updateRow = ( ( SBHeaderDao ) getDao() ).updateSBHeaderPaid( sbHeaderVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return updateRow;
   }

   @Override
   public String getSBToApplyForMoreStatusCountByHeaderIds( String[] headerId ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).getSBToApplyForMoreStatusCountByHeaderIds( headerId ) + "";
   }

   @Override
   public String getSBToApplyForResigningStatusCountByByHeaderIds( String[] headerId ) throws KANException
   {
      return ( ( SBHeaderDao ) getDao() ).getSBToApplyForResigningStatusCountByByHeaderIds( headerId ) + "";
   }

}
