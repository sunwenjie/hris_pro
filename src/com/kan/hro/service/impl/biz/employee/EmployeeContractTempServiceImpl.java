package com.kan.hro.service.impl.biz.employee;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractImportBatchDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractTempService;

public class EmployeeContractTempServiceImpl extends ContextService implements EmployeeContractTempService
{
   private EmployeeContractImportBatchDao employeeContractImportBatchDao;
   private EmployeeDao employeeDao;
   private EmployeeContractDao employeeContractDao;
   private EmployeeContractSalaryDao employeeContractSalaryDao;
   private EmployeeContractSalaryTempDao employeeContractSalaryTempDao;
   private EmployeeContractSBDao employeeContractSBDao;
   private EmployeeContractSBDetailDao employeeContractSBDetailDao;
   private EmployeeContractSBTempDao employeeContractSBTempDao;
   private EmployeeContractCBDao employeeContractCBDao;
   private EmployeeContractCBTempDao employeeContractCBTempDao;
   private EmployeeContractLeaveDao employeeContractLeaveDao;
   private EmployeeContractLeaveTempDao employeeContractLeaveTempDao;
   private EmployeeContractOTDao employeeContractOTDao;
   private EmployeeContractOTTempDao employeeContractOTTempDao;
   private EmployeeContractOtherDao employeeContractOtherDao;
   private EmployeeContractOtherTempDao employeeContractOtherTempDao;
   private LogDao logDao;

   /**    
   *  Get EmployeeContractTempVOs by Condition
   *   
   *  @param pagedListHolder
   *  @param isPaged
   *  @return
   *  @throws KANException
    */
   @Override
   public PagedListHolder getEmployeeContractTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractTempDao employeeContractTempDao = ( EmployeeContractTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractTempDao.countEmployeeContractTempVOsByCondition( ( EmployeeContractTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractTempDao.getEmployeeContractTempVOsByCondition( ( EmployeeContractTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractTempDao.getEmployeeContractTempVOsByCondition( ( EmployeeContractTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractVO getEmployeeContractTempVOByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractTempDao ) getDao() ).getEmployeeContractTempVOByContractId( contractId );
   }

   @Override
   // Reviewed by Kevin Jin at 2014-06-12
   public int updateBatch( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException
   {
      // ÿ500����¼Submitһ��
      int transactionCount = 500;

      try
      {
         final EmployeeContractTempDao employeeContractTempDao = ( EmployeeContractTempDao ) getDao();
         final List< Object > employeeContractTempVOs = employeeContractTempDao.getEmployeeContractTempVOsByBatchId( employeeContractImportBatchVO.getBatchId() );

         boolean isStartTrancation = false;

         if ( employeeContractTempVOs != null && employeeContractTempVOs.size() > 0 )
         {
            int i = 0;
            for ( Object employeeContractTempVOObject : employeeContractTempVOs )
            {
               //ÿ��transactionCount����¼����һ������
               if ( i % transactionCount == 0 )
               {
                  // ��������
                  this.startTransaction();
                  isStartTrancation = true;
               }

               i++;
               final EmployeeContractTempVO employeeContractTempVO = ( EmployeeContractTempVO ) employeeContractTempVOObject;

               // ����Temp��Actual
               submitEmployeeContractTempVO( employeeContractTempVO, employeeContractImportBatchVO.getIp() );

               //ÿ��transactionCount����¼�ύһ������
               if ( i % transactionCount == 0 )
               {
                  // �ύ����
                  this.commitTransaction();
                  isStartTrancation = false;
               }
            }
         }

         if ( !isStartTrancation )
         {
            // ��������
            this.startTransaction();
         }

         // ���Ϊ����
         employeeContractImportBatchVO.setStatus( "2" );
         employeeContractImportBatchDao.updateEmployeeContractImportBatch( employeeContractImportBatchVO );
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
   public int rollbackBatch( EmployeeContractImportBatchVO submitObject ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ֱ��ɾ��
         employeeContractImportBatchDao.deleteEmployeeContractSalaryByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractSBByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractCBByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractLeaveByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractOTByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractOtherByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractByBatchId( submitObject.getBatchId() );
         employeeContractImportBatchDao.deleteEmployeeContractImportBatch( submitObject.getBatchId() );

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
   // Reviewed by Kevin Jin at 2014-06-12
   public int updateByTempContractIds( final String[] tempContractIds, final String ip ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         final EmployeeContractTempDao employeeContractTempDao = ( EmployeeContractTempDao ) getDao();
         // ����batchId ,accountId
         String batchId = "";
         String accountId = "";
         String corpId = "";

         for ( int i = 0; i < tempContractIds.length; i++ )
         {
            final EmployeeContractTempVO employeeContractTempVO = ( EmployeeContractTempVO ) employeeContractTempDao.getEmployeeContractTempVOByContractId( tempContractIds[ i ] );

            if ( i == 0 )
            {
               batchId = employeeContractTempVO.getBatchId();
               accountId = employeeContractTempVO.getAccountId();
               corpId = employeeContractTempVO.getCorpId();
            }

            submitEmployeeContractTempVO( employeeContractTempVO, ip );
         }

         final EmployeeContractTempVO employeeContractTempVO = new EmployeeContractTempVO();
         employeeContractTempVO.setAccountId( accountId );
         employeeContractTempVO.setCorpId( corpId );
         employeeContractTempVO.setBatchId( batchId );
         employeeContractTempVO.setTempStatus( "1" );

         // ���������½�״̬���Ͷ���ͬ����Ϊ��0���������������ε�״̬Ҳ��Ϊ��2�����Ѹ��£�
         if ( employeeContractTempDao.countEmployeeContractTempVOsByCondition( employeeContractTempVO ) == 0 )
         {
            final EmployeeContractImportBatchVO employeeContractImportBatchVO = employeeContractImportBatchDao.getEmployeeContractImportBatchVOByBatchId( batchId );
            employeeContractImportBatchVO.setStatus( "2" );
            employeeContractImportBatchDao.updateEmployeeContractImportBatch( employeeContractImportBatchVO );
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
   // Reviewed by Kevin Jin at 2014-06-12
   public int rollbackByTempContractIds( final String tempContractIds[] ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         final EmployeeContractTempDao employeeContractTempDao = ( EmployeeContractTempDao ) getDao();
         // ����batchId
         String batchId = "";
         String accountId = "";

         for ( int i = 0; i < tempContractIds.length; i++ )
         {
            if ( i == 0 )
            {
               final EmployeeContractTempVO employeeContractTempVO = ( EmployeeContractTempVO ) employeeContractTempDao.getEmployeeContractTempVOByContractId( tempContractIds[ i ] );
               batchId = employeeContractTempVO.getBatchId();
               accountId = employeeContractTempVO.getAccountId();
            }

            // �˻�һ���Ͷ���ͬtemp - ֱ��ɾ��
            employeeContractImportBatchDao.deleteEmployeeContractSalaryByContractId( tempContractIds[ i ] );
            employeeContractImportBatchDao.deleteEmployeeContractSBByContractId( tempContractIds[ i ] );
            employeeContractImportBatchDao.deleteEmployeeContractCBByContractId( tempContractIds[ i ] );
            employeeContractImportBatchDao.deleteEmployeeContractLeaveByContractId( tempContractIds[ i ] );
            employeeContractImportBatchDao.deleteEmployeeContractOTByContractId( tempContractIds[ i ] );
            employeeContractImportBatchDao.deleteEmployeeContractOtherByContractId( tempContractIds[ i ] );
            employeeContractImportBatchDao.deleteEmployeeContractByContractId( tempContractIds[ i ] );
         }

         final EmployeeContractTempVO employeeContractTempVO = new EmployeeContractTempVO();
         employeeContractTempVO.setAccountId( accountId );
         employeeContractTempVO.setBatchId( batchId );
         employeeContractTempVO.setTempStatus( "1" );

         // ���������½�״̬���Ͷ���ͬ����Ϊ��0����������ε�״̬��Ϊ��2���Ѿ���
         if ( employeeContractTempDao.countEmployeeContractTempVOsByCondition( employeeContractTempVO ) == 0 )
         {
            employeeContractTempVO.setTempStatus( "2" );
            final EmployeeContractImportBatchVO employeeContractImportBatchVO = employeeContractImportBatchDao.getEmployeeContractImportBatchVOByBatchId( batchId );

            if ( employeeContractTempDao.countEmployeeContractTempVOsByCondition( employeeContractTempVO ) > 0 )
            {
               // �������Ϊ�Ѹ���
               employeeContractImportBatchVO.setStatus( "2" );
            }
            else
            {
               // �������Ϊ���˻�
               employeeContractImportBatchVO.setStatus( "3" );
            }

            employeeContractImportBatchDao.updateEmployeeContractImportBatch( employeeContractImportBatchVO );
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

   /**
    * �ύһ��Temp�Ͷ���ͬ
   *  Submit EmployeeContractTempVO
   *  
   *  @param employeeContractTempVO
    */
   private void submitEmployeeContractTempVO( final EmployeeContractTempVO employeeContractTempVO, final String ip ) throws KANException
   {
      final EmployeeContractTempDao employeeContractTempDao = ( EmployeeContractTempDao ) getDao();
      final String tempContractId = employeeContractTempVO.getContractId();
      // ����Ϊ�Ѹ���״̬
      employeeContractTempVO.setTempStatus( "2" );
      employeeContractTempDao.updateEmployeeContractTemp( employeeContractTempVO );

      boolean existContract = false;
      final String contractId = KANUtil.filterEmpty( employeeContractTempVO.getRemark4() );
      final String corpId = KANUtil.filterEmpty( employeeContractTempVO.getCorpId() );

      if ( contractId != null )
      {
         // ��ʼ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( contractId );

         if ( employeeContractVO != null )
         {
            existContract = true;
            employeeContractVO.update( employeeContractTempVO );
            employeeContractVO.setCorpId( corpId );

            // �����Զ����ֶ�
            employeeContractVO.setRemark1( employeeContractTempVO.getRemark1() );
            employeeContractVO.setRemark3( employeeContractTempVO.getRemark3() );
            employeeContractVO.setRemark4( employeeContractTempVO.getContractId() );
            //  �޸�EmployeeContractVO
            employeeContractDao.updateEmployeeContract( employeeContractVO );

            //ͬ����ͬ����Ŀ��Ա����Ŀ
            EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            if ( employeeVO != null )
            {
               employeeVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
               employeeDao.updateEmployee( employeeVO );
            }

            // ����н�귽������ʽ��  
            final List< Object > employeeContractSalaryTempVOs = employeeContractSalaryTempDao.getEmployeeContractSalaryTempVOsByContractId( employeeContractTempVO.getContractId() );

            if ( employeeContractSalaryTempVOs != null && employeeContractSalaryTempVOs.size() > 0 )
            {
               for ( Object employeeContractSalaryTempVOObject : employeeContractSalaryTempVOs )
               {
                  // ��ʼ��EmployeeContractSalaryVO
                  final EmployeeContractSalaryVO employeeContractSalaryTempVO = ( EmployeeContractSalaryVO ) employeeContractSalaryTempVOObject;

                  final EmployeeContractSalaryVO employeeContractSalaryVO = new EmployeeContractSalaryVO();
                  employeeContractSalaryVO.setContractId( contractId );
                  employeeContractSalaryVO.setCorpId( corpId );
                  employeeContractSalaryVO.setItemId( employeeContractSalaryTempVO.getItemId() );

                  // ����ʵ�ʱ�����ͬ��Ŀ��н�귽��
                  final List< Object > employeeContractSalaryVOs = employeeContractSalaryDao.getEmployeeContractSalaryVOsByCondition( employeeContractSalaryVO );

                  // û���ҵ�������н�귽��
                  if ( employeeContractSalaryVOs == null || employeeContractSalaryVOs.size() == 0 )
                  {
                     final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = new EmployeeContractSalaryVO();
                     tempEmployeeContractSalaryVO.update( employeeContractSalaryTempVO );
                     tempEmployeeContractSalaryVO.setContractId( contractId );
                     tempEmployeeContractSalaryVO.setRemark4( employeeContractSalaryTempVO.getEmployeeSalaryId() );
                     employeeContractSalaryDao.insertEmployeeContractSalary( tempEmployeeContractSalaryVO );

                     // н��Id����Temp��
                     employeeContractSalaryTempVO.setRemark4( employeeContractSalaryVO.getEmployeeSalaryId() );
                     employeeContractSalaryTempDao.updateEmployeeContractSalaryTemp( employeeContractSalaryTempVO );
                  }
                  // �ҵ�һ�����޸ĸ�н�귽��
                  else if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() == 1 )
                  {
                     final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOs.get( 0 );
                     tempEmployeeContractSalaryVO.update( employeeContractSalaryTempVO );
                     tempEmployeeContractSalaryVO.setContractId( contractId );
                     tempEmployeeContractSalaryVO.setRemark4( employeeContractSalaryTempVO.getEmployeeSalaryId() );
                     employeeContractSalaryDao.updateEmployeeContractSalary( tempEmployeeContractSalaryVO );

                     // н��Id����Temp��
                     employeeContractSalaryTempVO.setRemark4( employeeContractSalaryVO.getEmployeeSalaryId() );
                     employeeContractSalaryTempDao.updateEmployeeContractSalaryTemp( employeeContractSalaryTempVO );
                  }
                  else if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 1 )
                  {
                     EmployeeContractSalaryVO tempEmployeeContractSalaryVO = null;

                     for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
                     {
                        if ( tempEmployeeContractSalaryVO == null )
                        {
                           tempEmployeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
                        }
                        else if ( KANUtil.filterEmpty( tempEmployeeContractSalaryVO.getEndDate() ) != null
                              && KANUtil.filterEmpty( ( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject ).getEndDate() ) != null
                              && KANUtil.getDays( KANUtil.createCalendar( ( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject ).getEndDate() ) ) > KANUtil.getDays( KANUtil.createCalendar( tempEmployeeContractSalaryVO.getEndDate() ) ) )
                        {
                           tempEmployeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
                        }
                     }

                     tempEmployeeContractSalaryVO.update( employeeContractSalaryTempVO );
                     tempEmployeeContractSalaryVO.setContractId( contractId );
                     tempEmployeeContractSalaryVO.setRemark4( employeeContractSalaryTempVO.getEmployeeSalaryId() );
                     employeeContractSalaryDao.updateEmployeeContractSalary( tempEmployeeContractSalaryVO );

                     // н��Id����Temp��
                     employeeContractSalaryTempVO.setRemark4( employeeContractSalaryVO.getEmployeeSalaryId() );
                     employeeContractSalaryTempDao.updateEmployeeContractSalaryTemp( employeeContractSalaryTempVO );
                  }
               }
            }

            // �����籣��������ʽ��  
            final List< Object > employeeContractSBTemps = employeeContractSBTempDao.getEmployeeContractSBTempVOsByContractId( employeeContractTempVO.getContractId() );

            if ( employeeContractSBTemps != null )
            {
               for ( Object employeeContractSBTempObject : employeeContractSBTemps )
               {
                  final EmployeeContractSBTempVO employeeContractSBTempVO = ( EmployeeContractSBTempVO ) employeeContractSBTempObject;

                  final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                  employeeContractSBVO.setContractId( contractId );
                  employeeContractSBVO.setCorpId( corpId );
                  employeeContractSBVO.setSbSolutionId( employeeContractSBTempVO.getSbSolutionId() );
                  //�����籣״̬ 
                  employeeContractSBVO.setSbType( "0" );
                  // ����ʵ�ʱ�����ͬ��Ŀ��н�귽��
                  final List< Object > employeeContractSBs = employeeContractSBDao.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                  if ( employeeContractSBs == null || employeeContractSBs.size() == 0 )
                  {
                     // û���ҵ��������籣��������ʽ��
                     final EmployeeContractSBVO tempEmployeeContractSBVO = new EmployeeContractSBVO();
                     tempEmployeeContractSBVO.update( employeeContractSBTempVO );
                     tempEmployeeContractSBVO.setContractId( contractId );
                     tempEmployeeContractSBVO.setRemark4( employeeContractSBTempVO.getEmployeeSBId() );
                     employeeContractSBDao.insertEmployeeContractSB( tempEmployeeContractSBVO );

                     final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( employeeContractTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBTempVO.getSbSolutionId() );

                     if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() != null )
                     {
                        for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() )
                        {
                           double baseCompany = 0;
                           double basePersonal = 0;

                           // Ĭ�ϻ�ȡ����
                           if ( KANUtil.filterEmpty( employeeContractSBTempVO.getSbBase() ) != null )
                           {
                              baseCompany = basePersonal = Double.valueOf( employeeContractSBTempVO.getSbBase() );
                           }

                           // ��˾����������Χ
                           if ( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) != null
                                 && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) != null )
                           {
                              if ( baseCompany > Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) ) )
                              {
                                 baseCompany = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) );
                              }
                              else if ( baseCompany < Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) ) )
                              {
                                 baseCompany = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) );
                              }
                           }

                           // ���˻���������Χ
                           if ( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) != null
                                 && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) != null )
                           {
                              if ( basePersonal > Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) ) )
                              {
                                 basePersonal = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) );
                              }
                              else if ( basePersonal < Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) ) )
                              {
                                 basePersonal = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) );
                              }
                           }

                           final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                           employeeContractSBDetailVO.setEmployeeSBId( tempEmployeeContractSBVO.getEmployeeSBId() );
                           employeeContractSBDetailVO.setBaseCompany( String.valueOf( baseCompany ) );
                           employeeContractSBDetailVO.setBasePersonal( String.valueOf( basePersonal ) );
                           employeeContractSBDetailVO.setSolutionDetailId( socialBenefitSolutionDetailVO.getDetailId() );// ����detailID
                           employeeContractSBDetailVO.setStatus( "1" );
                           employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
                        }
                     }

                     // Id����Temp��
                     employeeContractSBTempVO.setRemark4( tempEmployeeContractSBVO.getEmployeeSBId() );
                     employeeContractSBTempDao.updateEmployeeContractSBTemp( employeeContractSBTempVO );
                  }
                  else if ( employeeContractSBs != null && employeeContractSBs.size() > 0 )
                  {
                     // ��ȡ��Ա�籣����
                     final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBs.get( 0 );
                     tempEmployeeContractSBVO.update( employeeContractSBTempVO );
                     tempEmployeeContractSBVO.setContractId( contractId );
                     tempEmployeeContractSBVO.setCorpId( employeeContractSBTempVO.getCorpId() );
                     tempEmployeeContractSBVO.setRemark4( employeeContractSBTempVO.getEmployeeSBId() );
                     employeeContractSBDao.updateEmployeeContractSB( tempEmployeeContractSBVO );
                     // ��ȡ��Ա�籣������ϸ
                     final List< Object > employeeContractSBDetailVOs = employeeContractSBDetailDao.getEmployeeContractSBDetailVOsByEmployeeSBId( tempEmployeeContractSBVO.getEmployeeSBId() );

                     if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
                     {
                        for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
                        {
                           // ��ʼ��EmployeeContractSBDetailVO
                           final EmployeeContractSBDetailVO employeeContractSBDetailVO = ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject;
                           // ��ʼ��SocialBenefitSolutionDetailVO
                           final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = KANConstants.getKANAccountConstants( employeeContractTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBTempVO.getSbSolutionId() ).getSocialBenefitSolutionDetailVOByDetailId( employeeContractSBDetailVO.getSolutionDetailId() );

                           double baseCompany = 0;
                           double basePersonal = 0;

                           // Ĭ�ϻ�ȡ����
                           if ( KANUtil.filterEmpty( employeeContractSBTempVO.getSbBase() ) != null )
                           {
                              baseCompany = basePersonal = Double.valueOf( employeeContractSBTempVO.getSbBase() );
                           }

                           // ��˾����������Χ
                           if ( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) != null
                                 && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) != null )
                           {
                              if ( baseCompany > Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) ) )
                              {
                                 baseCompany = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) );
                              }
                              else if ( baseCompany < Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) ) )
                              {
                                 baseCompany = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) );
                              }
                           }

                           // ���˻���������Χ
                           if ( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) != null
                                 && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) != null )
                           {
                              if ( basePersonal > Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) ) )
                              {
                                 basePersonal = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) );
                              }
                              else if ( basePersonal < Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) ) )
                              {
                                 basePersonal = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) );
                              }
                           }

                           employeeContractSBDetailVO.setBaseCompany( String.valueOf( baseCompany ) );
                           employeeContractSBDetailVO.setBasePersonal( String.valueOf( basePersonal ) );
                           employeeContractSBDetailDao.updateEmployeeContractSBDetail( employeeContractSBDetailVO );
                        }
                     }

                     // Id����Temp��
                     employeeContractSBTempVO.setRemark4( employeeContractSBVO.getEmployeeSBId() );
                     employeeContractSBTempDao.updateEmployeeContractSBTemp( employeeContractSBTempVO );
                  }
               }
            }

            // �����̱���������ʽ��
            final List< Object > employeeContractCBTempVOs = employeeContractCBTempDao.getEmployeeContractCBTempVOsByContractId( employeeContractTempVO.getContractId() );

            if ( employeeContractCBTempVOs != null && employeeContractCBTempVOs.size() > 0 )
            {
               for ( Object employeeContractCBTempVOObject : employeeContractCBTempVOs )
               {
                  final EmployeeContractCBVO employeeContractCBTempVO = ( EmployeeContractCBVO ) employeeContractCBTempVOObject;

                  final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
                  employeeContractCBVO.setContractId( contractId );
                  employeeContractCBVO.setSolutionId( employeeContractCBTempVO.getSolutionId() );
                  // ����ʵ�ʱ�����ͬSolutionId���̱�����
                  final List< Object > employeeContractCBVOs = employeeContractCBDao.getEmployeeContractCBVOsByCondition( employeeContractCBVO );

                  if ( employeeContractCBVOs == null || employeeContractCBVOs.size() == 0 )
                  {
                     final EmployeeContractCBVO tempEmployeeContractCBVO = new EmployeeContractCBVO();
                     tempEmployeeContractCBVO.update( employeeContractCBTempVO );
                     tempEmployeeContractCBVO.setContractId( contractId );
                     tempEmployeeContractCBVO.setRemark4( employeeContractCBTempVO.getEmployeeCBId() );
                     employeeContractCBDao.insertEmployeeContractCB( tempEmployeeContractCBVO );

                     // Id����Temp��
                     employeeContractCBTempVO.setRemark4( tempEmployeeContractCBVO.getEmployeeCBId() );
                     employeeContractCBTempDao.updateEmployeeContractCBTemp( employeeContractCBTempVO );

                  }
                  else if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
                  {
                     final EmployeeContractCBVO tempEmployeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOs.get( 0 );
                     tempEmployeeContractCBVO.update( employeeContractCBTempVO );
                     tempEmployeeContractCBVO.setContractId( contractId );
                     tempEmployeeContractCBVO.setRemark4( employeeContractCBTempVO.getEmployeeCBId() );
                     employeeContractCBDao.updateEmployeeContractCB( tempEmployeeContractCBVO );

                     // Id����Temp��
                     employeeContractCBTempVO.setRemark4( tempEmployeeContractCBVO.getEmployeeCBId() );
                     employeeContractCBTempDao.updateEmployeeContractCBTemp( employeeContractCBTempVO );
                  }
               }
            }

            // ������ٷ�������ʽ��  
            final List< Object > employeeContractLeaveTempVOs = employeeContractLeaveTempDao.getEmployeeContractLeaveTempVOsByContractId( employeeContractTempVO.getContractId() );

            if ( employeeContractLeaveTempVOs != null && employeeContractLeaveTempVOs.size() > 0 )
            {
               for ( Object employeeContractLeaveTempVOObject : employeeContractLeaveTempVOs )
               {
                  final EmployeeContractLeaveVO employeeContractLeaveTempVO = ( EmployeeContractLeaveVO ) employeeContractLeaveTempVOObject;

                  final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
                  employeeContractLeaveVO.setContractId( contractId );
                  employeeContractLeaveVO.setItemId( employeeContractLeaveTempVO.getItemId() );

                  // ����ʵ�ʱ�����ͬ��Ŀ��н�귽��
                  final List< Object > employeeContractLeaveVOs = employeeContractLeaveDao.getEmployeeContractLeaveVOsByCondition( employeeContractLeaveVO );

                  if ( employeeContractLeaveVOs == null || employeeContractLeaveVOs.size() == 0 )
                  {
                     final EmployeeContractLeaveVO tempEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
                     tempEmployeeContractLeaveVO.update( employeeContractLeaveTempVO );
                     tempEmployeeContractLeaveVO.setContractId( contractId );
                     tempEmployeeContractLeaveVO.setRemark4( employeeContractLeaveTempVO.getEmployeeLeaveId() );
                     employeeContractLeaveDao.insertEmployeeContractLeave( tempEmployeeContractLeaveVO );

                     // Id����Temp��
                     employeeContractLeaveTempVO.setRemark4( tempEmployeeContractLeaveVO.getEmployeeLeaveId() );
                     employeeContractLeaveTempDao.updateEmployeeContractLeaveTemp( employeeContractLeaveTempVO );

                  }
                  else if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
                  {
                     final EmployeeContractLeaveVO tempEmployeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOs.get( 0 );
                     tempEmployeeContractLeaveVO.update( employeeContractLeaveTempVO );
                     tempEmployeeContractLeaveVO.setContractId( contractId );
                     tempEmployeeContractLeaveVO.setRemark4( employeeContractLeaveTempVO.getEmployeeLeaveId() );
                     employeeContractLeaveDao.updateEmployeeContractLeave( tempEmployeeContractLeaveVO );

                     // Id����Temp��
                     employeeContractLeaveTempVO.setRemark4( tempEmployeeContractLeaveVO.getEmployeeLeaveId() );
                     employeeContractLeaveTempDao.updateEmployeeContractLeaveTemp( employeeContractLeaveTempVO );
                  }
               }
            }

            // ���¼Ӱ෽������ʽ��  
            List< Object > employeeContractOTTempVOs = employeeContractOTTempDao.getEmployeeContractOTTempVOsByContractId( employeeContractTempVO.getContractId() );

            if ( employeeContractOTTempVOs != null && employeeContractOTTempVOs.size() > 0 )
            {
               for ( Object employeeContractOTTempVOObject : employeeContractOTTempVOs )
               {
                  final EmployeeContractOTVO employeeContractOTTempVO = ( EmployeeContractOTVO ) employeeContractOTTempVOObject;

                  final EmployeeContractOTVO employeeContractOTVO = new EmployeeContractOTVO();
                  employeeContractOTVO.setContractId( contractId );
                  employeeContractOTVO.setItemId( employeeContractOTTempVO.getItemId() );

                  // ����ʵ�ʱ�����ͬ��Ŀ��н�귽��
                  final List< Object > employeeContractOTVOs = employeeContractOTDao.getEmployeeContractOTVOsByCondition( employeeContractOTVO );

                  if ( employeeContractOTVOs == null || employeeContractOTVOs.size() == 0 )
                  {
                     final EmployeeContractOTVO tempEmployeeContractOTVO = new EmployeeContractOTVO();
                     tempEmployeeContractOTVO.update( employeeContractOTTempVO );
                     tempEmployeeContractOTVO.setContractId( contractId );
                     tempEmployeeContractOTVO.setRemark4( employeeContractOTTempVO.getEmployeeOTId() );
                     employeeContractOTDao.insertEmployeeContractOT( tempEmployeeContractOTVO );

                     // Id����Temp��
                     employeeContractOTTempVO.setRemark4( tempEmployeeContractOTVO.getEmployeeOTId() );
                     employeeContractOTTempDao.updateEmployeeContractOTTemp( employeeContractOTTempVO );
                  }
                  else if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
                  {
                     final EmployeeContractOTVO tempEmployeeContractOTVO = ( EmployeeContractOTVO ) employeeContractOTVOs.get( 0 );
                     tempEmployeeContractOTVO.update( employeeContractOTTempVO );
                     tempEmployeeContractOTVO.setContractId( contractId );
                     tempEmployeeContractOTVO.setRemark4( employeeContractOTTempVO.getEmployeeOTId() );
                     employeeContractOTDao.updateEmployeeContractOT( tempEmployeeContractOTVO );

                     // Id����Temp��
                     employeeContractOTTempVO.setRemark4( tempEmployeeContractOTVO.getEmployeeOTId() );
                     employeeContractOTTempDao.updateEmployeeContractOTTemp( employeeContractOTTempVO );
                  }
               }
            }

            // ������ٷ�������ʽ��  
            List< Object > employeeContractOtherTempVOs = employeeContractOtherTempDao.getEmployeeContractOtherTempVOsByContractId( employeeContractTempVO.getContractId() );

            if ( employeeContractOtherTempVOs != null && employeeContractOtherTempVOs.size() > 0 )
            {
               for ( Object employeeContractOtherTempVOObject : employeeContractOtherTempVOs )
               {
                  final EmployeeContractOtherVO employeeContractOtherTempVO = ( EmployeeContractOtherVO ) employeeContractOtherTempVOObject;

                  final EmployeeContractOtherVO employeeContractOtherVO = new EmployeeContractOtherVO();
                  employeeContractOtherVO.setContractId( contractId );
                  employeeContractOtherVO.setItemId( employeeContractOtherTempVO.getItemId() );

                  // ����ʵ�ʱ�����ͬ��Ŀ��н�귽��
                  final List< Object > employeeContractOtherVOs = employeeContractOtherDao.getEmployeeContractOtherVOsByCondition( employeeContractOtherVO );

                  if ( employeeContractOtherVOs == null || employeeContractOtherVOs.size() == 0 )
                  {
                     final EmployeeContractOtherVO tempEmployeeContractOtherVO = new EmployeeContractOtherVO();
                     tempEmployeeContractOtherVO.update( employeeContractOtherTempVO );
                     tempEmployeeContractOtherVO.setContractId( contractId );
                     tempEmployeeContractOtherVO.setRemark4( employeeContractOtherTempVO.getEmployeeOtherId() );
                     employeeContractOtherDao.insertEmployeeContractOther( tempEmployeeContractOtherVO );

                     // Id����Temp��
                     employeeContractOtherTempVO.setRemark4( tempEmployeeContractOtherVO.getEmployeeOtherId() );
                     employeeContractOtherTempDao.updateEmployeeContractOtherTemp( employeeContractOtherTempVO );

                  }
                  else if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
                  {
                     final EmployeeContractOtherVO tempEmployeeContractOtherVO = ( EmployeeContractOtherVO ) employeeContractOtherVOs.get( 0 );
                     tempEmployeeContractOtherVO.update( employeeContractOtherTempVO );
                     tempEmployeeContractOtherVO.setContractId( contractId );
                     tempEmployeeContractOtherVO.setRemark4( employeeContractOtherTempVO.getEmployeeOtherId() );
                     employeeContractOtherDao.updateEmployeeContractOther( tempEmployeeContractOtherVO );

                     // Id����Temp��
                     employeeContractOtherTempVO.setRemark4( tempEmployeeContractOtherVO.getEmployeeOtherId() );
                     employeeContractOtherTempDao.updateEmployeeContractOtherTemp( employeeContractOtherTempVO );
                  }
               }
            }
         }
      }

      // ����Э��/�Ͷ���ͬ������
      if ( !existContract )
      {
         // װ��EmployeeContractVO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.update( employeeContractTempVO );
         employeeContractVO.setAccountId( employeeContractTempVO.getAccountId() );
         employeeContractVO.setClientId( employeeContractTempVO.getClientId() );
         employeeContractVO.setCorpId( employeeContractTempVO.getCorpId() );
         employeeContractVO.setCreateBy( employeeContractTempVO.getCreateBy() );

         // �����Զ����ֶ�
         employeeContractVO.setRemark1( employeeContractTempVO.getRemark1() );
         employeeContractVO.setRemark3( employeeContractTempVO.getRemark3() );
         employeeContractVO.setRemark4( employeeContractTempVO.getContractId() );

         // �����Ͷ���ͬ����ʽ��
         this.getEmployeeContractDao().insertEmployeeContract( employeeContractVO );

         // ������־��¼
         final EmployeeContractVO tempEmployeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
         insertAddEmployeeContractVOLog( tempEmployeeContractVO, ip );

         //ͬ����ͬ����Ŀ��Ա����Ŀ
         EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         if ( employeeVO != null )
         {
            employeeVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
            employeeDao.updateEmployee( employeeVO );
         }

         // ��ʱ���е�Remark4������ʽ�������
         employeeContractTempVO.setRemark4( employeeContractVO.getContractId() );
         employeeContractTempDao.updateEmployeeContractTemp( employeeContractTempVO );

         // ����н�귽������ʽ��
         final List< Object > employeeContractSalaryTempVOs = employeeContractSalaryTempDao.getEmployeeContractSalaryTempVOsByContractId( tempContractId );

         if ( employeeContractSalaryTempVOs != null && employeeContractSalaryTempVOs.size() > 0 )
         {
            for ( Object employeeContractSalaryTempVOObject : employeeContractSalaryTempVOs )
            {
               final EmployeeContractSalaryVO employeeContractSalaryTempVO = ( EmployeeContractSalaryVO ) employeeContractSalaryTempVOObject;
               final EmployeeContractSalaryVO employeeContractSalaryVO = new EmployeeContractSalaryVO();
               employeeContractSalaryVO.update( employeeContractSalaryTempVO );
               employeeContractSalaryVO.setContractId( employeeContractVO.getContractId() );
               employeeContractSalaryVO.setRemark4( employeeContractSalaryTempVO.getEmployeeSalaryId() );
               employeeContractSalaryDao.insertEmployeeContractSalary( employeeContractSalaryVO );

               // Id����Temp��
               employeeContractSalaryTempVO.setRemark4( employeeContractSalaryVO.getEmployeeSalaryId() );
               employeeContractSalaryTempDao.updateEmployeeContractSalaryTemp( employeeContractSalaryTempVO );
            }
         }

         // �����籣��������ʽ��
         final List< Object > employeeContractSBTempVOs = employeeContractSBTempDao.getEmployeeContractSBTempVOsByContractId( tempContractId );

         if ( employeeContractSBTempVOs != null && employeeContractSBTempVOs.size() > 0 )
         {
            for ( Object employeeContractSBTempVOObject : employeeContractSBTempVOs )
            {
               final EmployeeContractSBTempVO employeeContractSBTempVO = ( EmployeeContractSBTempVO ) employeeContractSBTempVOObject;
               final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
               employeeContractSBVO.update( employeeContractSBTempVO );
               employeeContractSBVO.setContractId( employeeContractVO.getContractId() );
               employeeContractSBVO.setRemark4( employeeContractSBTempVO.getEmployeeSBId() );
               employeeContractSBDao.insertEmployeeContractSB( employeeContractSBVO );

               SocialBenefitSolutionDTO sbDTO = KANConstants.getKANAccountConstants( employeeContractTempVO.getAccountId() ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBTempVO.getSbSolutionId() );

               if ( sbDTO != null && sbDTO.getSocialBenefitSolutionDetailVOs() != null )
               {
                  for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : sbDTO.getSocialBenefitSolutionDetailVOs() )
                  {
                     double baseCompany = 0;
                     double basePersonal = 0;

                     // Ĭ�ϻ�ȡ����
                     if ( KANUtil.filterEmpty( employeeContractSBTempVO.getSbBase() ) != null )
                     {
                        baseCompany = basePersonal = Double.valueOf( employeeContractSBTempVO.getSbBase() );
                     }

                     // ��˾����������Χ
                     if ( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) != null
                           && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) != null )
                     {
                        if ( baseCompany > Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) ) )
                        {
                           baseCompany = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyCap() ) );
                        }
                        else if ( baseCompany < Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) ) )
                        {
                           baseCompany = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getCompanyFloor() ) );
                        }
                     }

                     // ���˻���������Χ
                     if ( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) != null
                           && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) != null )
                     {
                        if ( basePersonal > Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) ) )
                        {
                           basePersonal = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalCap() ) );
                        }
                        else if ( basePersonal < Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) ) )
                        {
                           basePersonal = Double.valueOf( KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getPersonalFloor() ) );
                        }
                     }

                     final EmployeeContractSBDetailVO contractSBDetailVO = new EmployeeContractSBDetailVO();
                     contractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                     contractSBDetailVO.setBaseCompany( String.valueOf( baseCompany ) );
                     contractSBDetailVO.setBasePersonal( String.valueOf( basePersonal ) );
                     contractSBDetailVO.setSolutionDetailId( socialBenefitSolutionDetailVO.getDetailId() );// ����detailID
                     contractSBDetailVO.setStatus( "1" );
                     employeeContractSBDetailDao.insertEmployeeContractSBDetail( contractSBDetailVO );
                  }
               }
               // Id����Temp��
               employeeContractSBTempVO.setRemark4( employeeContractSBVO.getEmployeeSBId() );
               employeeContractSBTempDao.updateEmployeeContractSBTemp( employeeContractSBTempVO );
            }
         }

         // �����̱���������ʽ��
         final List< Object > employeeContractCBTempVOs = employeeContractCBTempDao.getEmployeeContractCBTempVOsByContractId( tempContractId );

         if ( employeeContractCBTempVOs != null && employeeContractCBTempVOs.size() > 0 )
         {
            for ( Object employeeContractCBTempVOObject : employeeContractCBTempVOs )
            {
               final EmployeeContractCBVO employeeContractCBTempVO = ( EmployeeContractCBVO ) employeeContractCBTempVOObject;
               final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
               employeeContractCBVO.update( employeeContractCBTempVO );
               employeeContractCBVO.setContractId( employeeContractVO.getContractId() );
               employeeContractCBVO.setRemark4( employeeContractCBTempVO.getEmployeeCBId() );
               employeeContractCBDao.insertEmployeeContractCB( employeeContractCBVO );

               // Id����Temp��
               employeeContractCBTempVO.setRemark4( employeeContractCBVO.getEmployeeCBId() );
               employeeContractCBTempDao.updateEmployeeContractCBTemp( employeeContractCBTempVO );
            }
         }

         // ������ٷ�������ʽ��
         final List< Object > employeeContractLeaveTempVOs = employeeContractLeaveTempDao.getEmployeeContractLeaveTempVOsByContractId( tempContractId );

         if ( employeeContractLeaveTempVOs != null && employeeContractLeaveTempVOs.size() > 0 )
         {
            for ( Object employeeContractLeaveTempVOObject : employeeContractLeaveTempVOs )
            {
               final EmployeeContractLeaveVO employeeContractLeaveTempVO = ( EmployeeContractLeaveVO ) employeeContractLeaveTempVOObject;
               final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
               employeeContractLeaveVO.update( employeeContractLeaveTempVO );
               employeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
               employeeContractLeaveVO.setRemark4( employeeContractLeaveTempVO.getEmployeeLeaveId() );
               employeeContractLeaveDao.insertEmployeeContractLeave( employeeContractLeaveVO );

               // Id����Temp��  
               employeeContractLeaveTempVO.setRemark4( employeeContractLeaveVO.getEmployeeLeaveId() );
               employeeContractLeaveTempDao.updateEmployeeContractLeaveTemp( employeeContractLeaveTempVO );
            }
         }

         // ���¼Ӱ෽������ʽ��
         final List< Object > employeeContractOTTempVOs = employeeContractOTTempDao.getEmployeeContractOTTempVOsByContractId( tempContractId );

         if ( employeeContractOTTempVOs != null && employeeContractOTTempVOs.size() > 0 )
         {
            for ( Object employeeContractOTTempVOObject : employeeContractOTTempVOs )
            {
               final EmployeeContractOTVO employeeContractOTTempVO = ( EmployeeContractOTVO ) employeeContractOTTempVOObject;
               final EmployeeContractOTVO employeeContractOTVO = new EmployeeContractOTVO();
               employeeContractOTVO.update( employeeContractOTTempVO );
               employeeContractOTVO.setContractId( employeeContractVO.getContractId() );
               employeeContractOTVO.setRemark4( employeeContractOTTempVO.getEmployeeOTId() );
               employeeContractOTDao.insertEmployeeContractOT( employeeContractOTVO );

               // Id����Temp��  
               employeeContractOTTempVO.setRemark4( employeeContractOTVO.getEmployeeOTId() );
               employeeContractOTTempDao.updateEmployeeContractOTTemp( employeeContractOTTempVO );
            }
         }
         // ������ٷ�������ʽ��
         final List< Object > employeeContractOtherTempVOs = employeeContractOtherTempDao.getEmployeeContractOtherTempVOsByContractId( tempContractId );

         if ( employeeContractOtherTempVOs != null && employeeContractOtherTempVOs.size() > 0 )
         {
            for ( Object employeeContractOtherTempVOObject : employeeContractOtherTempVOs )
            {
               final EmployeeContractOtherVO employeeContractOtherTempVO = ( EmployeeContractOtherVO ) employeeContractOtherTempVOObject;
               final EmployeeContractOtherVO employeeContractOtherVO = new EmployeeContractOtherVO();
               employeeContractOtherVO.update( employeeContractOtherTempVO );
               employeeContractOtherVO.setContractId( employeeContractVO.getContractId() );
               employeeContractOtherVO.setRemark4( employeeContractOtherTempVO.getEmployeeOtherId() );
               employeeContractOtherDao.insertEmployeeContractOther( employeeContractOtherVO );

               // Id����Temp�� 
               employeeContractOtherTempVO.setRemark4( employeeContractOtherVO.getEmployeeOtherId() );
               employeeContractOtherTempDao.updateEmployeeContractOtherTemp( employeeContractOtherTempVO );
            }
         }
      }

      //΢��ͬ��
      BaseAction.syncWXContacts( employeeContractTempVO.getEmployeeId() );
   }

   /**
    * �����Ͷ���ͬ���ʱ��־��¼
    * @throws KANException
    */
   private void insertAddEmployeeContractVOLog( final EmployeeContractVO employeeContractVO, final String ip ) throws KANException
   {
      final LogVO logVO = new LogVO();
      logVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      logVO.setChangeReason( employeeContractVO.getRemark3() );
      logVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
      logVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
      logVO.setType( String.valueOf( Operate.ADD.getIndex() ) );
      logVO.setModule( EmployeeContractVO.class.getCanonicalName() );
      logVO.setContent( "��ͬ��Ӷʱ�䣺" + employeeContractVO.getStartDate() );
      logVO.setIp( ip );
      logVO.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      logVO.setOperateBy( employeeContractVO.decodeUserId( employeeContractVO.getModifyBy() ) );
      logVO.setpKey( employeeContractVO.getContractId() );
      logVO.setRemark( "��Դ-�Ͷ���ͬ����" );

      this.getLogDao().insertLog( logVO );
   }

   public EmployeeContractImportBatchDao getEmployeeContractImportBatchDao()
   {
      return employeeContractImportBatchDao;
   }

   public void setEmployeeContractImportBatchDao( EmployeeContractImportBatchDao employeeContractImportBatchDao )
   {
      this.employeeContractImportBatchDao = employeeContractImportBatchDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   public EmployeeContractSalaryTempDao getEmployeeContractSalaryTempDao()
   {
      return employeeContractSalaryTempDao;
   }

   public void setEmployeeContractSalaryTempDao( EmployeeContractSalaryTempDao employeeContractSalaryTempDao )
   {
      this.employeeContractSalaryTempDao = employeeContractSalaryTempDao;
   }

   public EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public void setEmployeeContractCBDao( EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   public EmployeeContractCBTempDao getEmployeeContractCBTempDao()
   {
      return employeeContractCBTempDao;
   }

   public void setEmployeeContractCBTempDao( EmployeeContractCBTempDao employeeContractCBTempDao )
   {
      this.employeeContractCBTempDao = employeeContractCBTempDao;
   }

   public EmployeeContractLeaveDao getEmployeeContractLeaveDao()
   {
      return employeeContractLeaveDao;
   }

   public void setEmployeeContractLeaveDao( EmployeeContractLeaveDao employeeContractLeaveDao )
   {
      this.employeeContractLeaveDao = employeeContractLeaveDao;
   }

   public EmployeeContractLeaveTempDao getEmployeeContractLeaveTempDao()
   {
      return employeeContractLeaveTempDao;
   }

   public void setEmployeeContractLeaveTempDao( EmployeeContractLeaveTempDao employeeContractLeaveTempDao )
   {
      this.employeeContractLeaveTempDao = employeeContractLeaveTempDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public EmployeeContractSBTempDao getEmployeeContractSBTempDao()
   {
      return employeeContractSBTempDao;
   }

   public void setEmployeeContractSBTempDao( EmployeeContractSBTempDao employeeContractSBTempDao )
   {
      this.employeeContractSBTempDao = employeeContractSBTempDao;
   }

   public EmployeeContractOTDao getEmployeeContractOTDao()
   {
      return employeeContractOTDao;
   }

   public void setEmployeeContractOTDao( EmployeeContractOTDao employeeContractOTDao )
   {
      this.employeeContractOTDao = employeeContractOTDao;
   }

   public EmployeeContractOTTempDao getEmployeeContractOTTempDao()
   {
      return employeeContractOTTempDao;
   }

   public void setEmployeeContractOTTempDao( EmployeeContractOTTempDao employeeContractOTTempDao )
   {
      this.employeeContractOTTempDao = employeeContractOTTempDao;
   }

   public EmployeeContractOtherDao getEmployeeContractOtherDao()
   {
      return employeeContractOtherDao;
   }

   public void setEmployeeContractOtherDao( EmployeeContractOtherDao employeeContractOtherDao )
   {
      this.employeeContractOtherDao = employeeContractOtherDao;
   }

   public EmployeeContractOtherTempDao getEmployeeContractOtherTempDao()
   {
      return employeeContractOtherTempDao;
   }

   public void setEmployeeContractOtherTempDao( EmployeeContractOtherTempDao employeeContractOtherTempDao )
   {
      this.employeeContractOtherTempDao = employeeContractOtherTempDao;
   }

   public EmployeeContractSBDetailDao getEmployeeContractSBDetailDao()
   {
      return employeeContractSBDetailDao;
   }

   public void setEmployeeContractSBDetailDao( EmployeeContractSBDetailDao employeeContractSBDetailDao )
   {
      this.employeeContractSBDetailDao = employeeContractSBDetailDao;
   }

   @Override
   public int updateEmployeeContractClientIdFromOrderIdByBatchId( final String batchId ) throws KANException
   {
      return ( ( EmployeeContractTempDao ) getDao() ).updateEmployeeContractClientIdFromOrderIdByBatchId( batchId );
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

}
