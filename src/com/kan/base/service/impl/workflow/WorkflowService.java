package com.kan.base.service.impl.workflow;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.HistoryDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.workflow.WorkflowActualDao;
import com.kan.base.dao.inf.workflow.WorkflowActualStepsDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.domain.workflow.WorkflowDefineDTO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.service.impl.message.KANSendMessageUtil;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�WorkflowService  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2014-3-5 ����07:47:25  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2014-3-5 ����07:47:25  
* �޸ı�ע��  
* @version   
*   
*/
public class WorkflowService extends ContextService
{
   // ע�� WorkflowActualDao
   private WorkflowActualDao workflowActualDao;

   // ע��WorkflowActualStepsDao
   private WorkflowActualStepsDao workflowActualStepsDao;

   // ע��HistoryDao
   private HistoryDao historyDao;

   // ע��EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // ע��ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ע��StaffDao
   private StaffDao staffDao;

   // ע��EmployeeDao
   private EmployeeDao employeeDao;

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public WorkflowActualDao getWorkflowActualDao()
   {
      return workflowActualDao;
   }

   public void setWorkflowActualDao( WorkflowActualDao workflowActualDao )
   {
      this.workflowActualDao = workflowActualDao;
   }

   public WorkflowActualStepsDao getWorkflowActualStepsDao()
   {
      return workflowActualStepsDao;
   }

   public void setWorkflowActualStepsDao( WorkflowActualStepsDao workflowActualStepsDao )
   {
      this.workflowActualStepsDao = workflowActualStepsDao;
   }

   public HistoryDao getHistoryDao()
   {
      return historyDao;
   }

   public void setHistoryDao( HistoryDao historyDao )
   {
      this.historyDao = historyDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }

   /**
    * 
   *  isApproveObject �Ƿ�������ͨ����Ķ���
   *  
   *  @param baseVO
   *  @return
    */
   public static boolean isPassObject( final BaseVO baseVO )
   {
      if ( baseVO != null && KANUtil.filterEmpty( baseVO.getHistoryVO().getHistoryId() ) != null )
      {
         return true;
      }

      return false;
   }

   /**
    * 
   *  getWorkflowDefineDTO ƥ��account����Ĺ�����
   *  
   *  @param moduleId   ģ��ID
   *  @param rightId    Ȩ��ID
   *  @param corpId     corpId 
   *  @return
    */
   private List< WorkflowDefineDTO > getWorkflowDefineDTO( final KANAccountConstants accountConstants, final String moduleId, final String rightId, final String corpId )
   {
      // ��ʼ��WorkflowDefineDTO�б�
      final List< WorkflowDefineDTO > workflowDefineDTOTemps = new ArrayList< WorkflowDefineDTO >();

      // ���������е�WorkflowModuleDTO
      for ( WorkflowModuleDTO workflowModuleDTO : accountConstants.WORKFLOW_MODULE_DTO )
      {
         // ��ǰģ��moduleId����WORKFLOW_MODULE_DTO��WorkflowModuleVO��moduleId
         if ( workflowModuleDTO.getWorkflowModuleVO() != null && moduleId != null && moduleId.equals( workflowModuleDTO.getWorkflowModuleVO().getModuleId() ) )
         {
            // ����WorkflowDefineDTO�б��ҵ����ʵ�WorkflowDefineDTO
            if ( workflowModuleDTO.getWorkflowDefineDTO() != null && workflowModuleDTO.getWorkflowDefineDTO().size() > 0 )
            {
               for ( WorkflowDefineDTO workflowDefineDTO : workflowModuleDTO.getWorkflowDefineDTO() )
               {
                  if ( workflowDefineDTO.getWorkflowDefineVO() != null
                        && "1".equals( workflowDefineDTO.getWorkflowDefineVO().getStatus() )
                        && ( ( corpId == null && workflowDefineDTO.getWorkflowDefineVO().getCorpId() == null ) || ( corpId != null
                              && workflowDefineDTO.getWorkflowDefineVO().getCorpId() != null && corpId.equals( workflowDefineDTO.getWorkflowDefineVO().getCorpId() ) ) ) )
                  {
                     // ��ǰģ���rightId��workflowDefineVO���ж���
                     if ( KANUtil.hasContain( workflowDefineDTO.getWorkflowDefineVO().getRightIdsArray(), rightId ) )
                     {
                        //������������ʱ����
                        final WorkflowDefineDTO workflowDefineDTOTemp = new WorkflowDefineDTO();
                        workflowDefineDTOTemp.setWorkflowDefineStepsVOs( workflowDefineDTO.getWorkflowDefineStepsVOs() );
                        workflowDefineDTOTemp.setWorkflowDefineRequirementsVOs( workflowDefineDTO.getWorkflowDefineRequirementsVOs() );
                        workflowDefineDTOTemp.setWorkflowDefineVO( workflowDefineDTO.getWorkflowDefineVO() );

                        workflowDefineDTOTemps.add( workflowDefineDTOTemp );
                     }
                  }
               }
            }
         }
      }

      return workflowDefineDTOTemps;
   }

   /**
    * �����Ч�Ĺ�����
   *  getValidWorkflowDefineDTO
   *  
   *  @param workflowDefineDTOTemp
   *  @param accountConstants
   *  @param baseVO
   *  @return
    */
   private WorkflowActualDTO getValidWorkflowActualDTO( final WorkflowDefineDTO workflowDefineDTOTemp, final KANAccountConstants accountConstants, final BaseVO baseVO )
   {
      // �����������Ƿ�Ϊ��
      if ( workflowDefineDTOTemp != null )
      {
         // ��ʼ�� WorkflowActualDTO
         WorkflowActualDTO workflowActualDTO = null;

         // ��ȡWorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = workflowDefineDTOTemp.getWorkflowDefineVO();

         // ���÷�Χ
         int scope = workflowDefineVO.getScope();

         // ������������
         String approvalType = workflowDefineVO.getApprovalType();

         // ְλ�ڲ�
         if ( scope == 1 )
         {
            // ������Զ���
            if ( "1".equals( approvalType ) )
            {
               // ������������˳��
               sortStepIndex( workflowDefineDTOTemp.getWorkflowDefineStepsVOs() );
               workflowActualDTO = createWorkflowActualDTO( workflowDefineDTOTemp, baseVO );
            }
            // ����ǻ�����֯�ܹ� - �Ӳ����˿�ʼ
            else if ( "2".equals( approvalType ) )
            {
               // ��ȡ�����˵�positionId
               final String positionId = baseVO.getHistoryVO().getPositionId();

               // ���㹤��������
               final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, positionId );
               workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
            }
            // ����ǻ�����֯�ܹ� - �ӶԽ��˿�ʼ
            else if ( "3".equals( approvalType ) )
            {
               // ���  ��������������  ����֯�ܹ�����
               String ownerPositionId = null;
               try
               {
                  ownerPositionId = BeanUtils.getProperty( baseVO.getHistoryVO(), "owner" );
               }
               catch ( Exception e )
               {
                  e.printStackTrace();
               }
               // ��owner ����owner ��Ϊ�� 
               if ( ownerPositionId != null && !ownerPositionId.trim().isEmpty() )
               {
                  final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, ownerPositionId );
                  workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
               }
               // �������û�������˵�  ��  ��������֯�ܹ�-�Ӳ����˿�ʼ ����  
               else
               {
                  String positionId = baseVO.getHistoryVO().getPositionId();
                  final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, positionId );
                  workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
               }

            }
            // ������֯�ܹ�-�������˿�ʼ
            else if ( "4".equals( approvalType ) )
            {

               String positionId = null;
               try
               {
                  // ͨ��������employeeId
                  final String employeeId = BeanUtils.getProperty( baseVO, "employeeId" );
                  // �ٲ�ѯ�õ�staffId
                  StaffVO staffVO = staffDao.getStaffVOByEmployeeId( employeeId );
                  if ( staffVO != null )
                  {
                     StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( staffVO.getStaffId() );
                     if ( staffDTO != null )
                     {
                        List< PositionStaffRelationVO > positionStaffRelationVOs = staffDTO.getPositionStaffRelationVOs();
                        if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
                        {
                           for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationVOs )
                           {
                              // �ҵ�һ��������ְλ���Ǵ�����ְλ
                              if ( "1".equals( positionStaffRelationVO.getStaffType() ) )
                              {
                                 positionId = positionStaffRelationVO.getPositionId();
                                 break;
                              }
                           }
                           // û���ҵ�����ְλ����ѡ���һ��Ĭ�ϴ���ְλ
                           if ( positionId == null )
                           {
                              positionId = positionStaffRelationVOs.get( 0 ).getPositionId();
                           }
                        }
                     }
                  }
               }
               catch ( IllegalAccessException e )
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               catch ( InvocationTargetException e )
               {
                  logger.error( "��˶����캯�����ô���" );
                  e.printStackTrace();
               }
               catch ( NoSuchMethodException e )
               {
                  logger.error( "��˶���û��employeeId���ԣ�" );
                  e.printStackTrace();
               }
               catch ( KANException e )
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

               // �ٵõ�staffId ��positionId( �����Ƕ��positionId, ��ôֻ����staff����ְλ�������Ǵ���ְλ�� ������ж��positionId,��Ĭ��ѡ��һ��)

               final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, positionId );
               workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
            }
         }
         // �ⲿ
         else if ( scope == 2 )
         {
            // ��ʼ��WorkflowActualDTO
            workflowActualDTO = new WorkflowActualDTO();

            // ��ʼ��WorkflowActualStepsVO�б�
            // final List< WorkflowActualStepsVO > workflowActualStepsVOs = workflowActualDTO.getWorkflowActualStepsVOs();

            try
            {
               // ��ȡ�����еġ�contractId��
               final String contractId = BeanUtils.getProperty( baseVO, "contractId" );

               // baseVO������ڡ�contractId��
               if ( KANUtil.filterEmpty( contractId ) != null )
               {
                  // ��ȡEmployeeContractVO
                  final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( contractId );

                  if ( employeeContractVO != null )
                  {
                     // ������Ϣ - ������˷�ʽ
                     String approveType = employeeContractVO.getApproveType();

                     // ������Ϣ - ֱ�߾���
                     final String lineManagerId = employeeContractVO.getLineManagerId();

                     // ���������Ϣ���ն����趨
                     if ( KANUtil.filterEmpty( approveType, "0" ) == null )
                     {
                        // ��ȡ���񶩵�ClientOrderHeaderVO
                        final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
                        if ( clientOrderHeaderVO != null )
                        {
                           approveType = clientOrderHeaderVO.getApproveType();
                        }
                     }

                     // ��˷�ʽ - ְλ�趨
                     if ( "1".equals( approveType ) )
                     {
                        // ��ȡ��˶���positionId
                        final String employeePostionId = employeeContractVO.getPositionId();

                        // ��ȡ����������
                        final int steps = workflowDefineVO.getSteps();

                        // ��ʼ��ְ��Ȩ��
                        double topPositionGradeWeight = Double.MAX_VALUE;

                        // 1�����ⲿ��֯�ܹ�������˲���
                        // final WorkflowDefineDTO workflowDefineDTO = getWorkflowDefineDTOByOutOrganization( accountConstants, workflowDefineDTOTemp, employeePostionId );

                        // ��ȡ�������ְ��
                        final com.kan.base.domain.management.PositionGradeVO positionGrade = accountConstants.getEmployeePositionGradeVOByPositionGradeId( workflowDefineVO.getTopPositionGrade() );

                        if ( positionGrade != null )
                        {
                           topPositionGradeWeight = Double.parseDouble( positionGrade.getWeight() );
                        }

                        // ����WorkflowDefineStepsVO
                        final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs = getWorkflowDefineStepsVOsByOutOrganization( accountConstants, workflowDefineVO, employeePostionId, topPositionGradeWeight, steps );

                        // 1��������֯�ܹ���������¼�����б�
                        for ( int i = 0; i < organizationWorkflowDefineStepsVOs.size(); i++ )
                        {
                           // ��ʼ��WorkflowDefineStepsVO
                           final WorkflowDefineStepsVO workflowDefineStepsVO = organizationWorkflowDefineStepsVOs.get( i );

                           // ��ȡEmployeeVO�б�
                           final List< Object > employeeVOs = this.getEmployeeDao().getEmployeeVOsByPositionId( workflowDefineStepsVO.getPositionId() );

                           // �ⲿְλû�б�EmployeeVOӵ��
                           if ( employeeVOs == null || employeeVOs.size() == 0 )
                           {
                              continue;
                           }

                           final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                           tempWorkflowActualStepsVO.setStepIndex( i + 1 );
                           // ְλ�ⲿ
                           tempWorkflowActualStepsVO.setAuditType( "2" );
                           tempWorkflowActualStepsVO.setAuditTargetId( workflowDefineStepsVO.getPositionId() );
                           tempWorkflowActualStepsVO.setSendEmail( workflowDefineStepsVO.getSendEmail() );
                           tempWorkflowActualStepsVO.setSendInfo( workflowDefineStepsVO.getSendInfo() );
                           tempWorkflowActualStepsVO.setSendSMS( workflowDefineStepsVO.getSendSMS() );
                           tempWorkflowActualStepsVO.setEmailTemplateType( workflowDefineStepsVO.getEmailTemplateId() );
                           tempWorkflowActualStepsVO.setInfoTemplateType( workflowDefineStepsVO.getInfoTemplateId() );
                           tempWorkflowActualStepsVO.setSmsTemplateType( workflowDefineStepsVO.getSmsTemplateId() );
                           tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
                           tempWorkflowActualStepsVO.setCreateDate( new Date() );

                           workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
                        }

                        // 2����������˼���ֱ�߾���
                        if ( KANUtil.filterEmpty( lineManagerId, "0" ) != null )
                        {
                           final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                           tempWorkflowActualStepsVO.setStepIndex( workflowActualDTO.getWorkflowActualStepsVOs().size() + 1 );
                           // �ͻ���ϵ��
                           tempWorkflowActualStepsVO.setAuditType( "3" );
                           // ������Ϣֱ�߾��� 
                           tempWorkflowActualStepsVO.setAuditTargetId( lineManagerId );
                           tempWorkflowActualStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                           tempWorkflowActualStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                           tempWorkflowActualStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                           tempWorkflowActualStepsVO.setEmailTemplateType( workflowDefineVO.getEmailTemplateId() );
                           tempWorkflowActualStepsVO.setInfoTemplateType( workflowDefineVO.getInfoTemplateId() );
                           tempWorkflowActualStepsVO.setSmsTemplateType( workflowDefineVO.getSmsTemplateId() );
                           tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
                           tempWorkflowActualStepsVO.setCreateDate( new Date() );

                           workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
                        }
                     }
                     // ��˷�ʽ - ��ֱ�߾���
                     else if ( "2".equals( approveType ) )
                     {
                        // ֱ�Ӽ��������
                        if ( KANUtil.filterEmpty( lineManagerId, "0" ) != null )
                        {
                           final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                           tempWorkflowActualStepsVO.setStepIndex( 1 );
                           // �ͻ���ϵ��
                           tempWorkflowActualStepsVO.setAuditType( "3" );
                           // ������Ϣֱ�߾��� 
                           tempWorkflowActualStepsVO.setAuditTargetId( lineManagerId );
                           tempWorkflowActualStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                           tempWorkflowActualStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                           tempWorkflowActualStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                           tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
                           tempWorkflowActualStepsVO.setCreateDate( new Date() );

                           workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
                        }
                        // ������ֱ�߾���
                        else
                        {
                           return null;
                        }
                     }
                     else
                     {
                        return null;
                     }

                     // ��ʼ��������Ϣ�Խ���
                     String ownerPositionId = null;
                     try
                     {
                        ownerPositionId = employeeContractVO.getOwner();
                     }
                     catch ( Exception e )
                     {
                        e.printStackTrace();
                     }

                     // ������Ϣ���ڶԽ���
                     if ( KANUtil.filterEmpty( ownerPositionId, "0" ) != null )
                     {
                        final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                        // ְλ�ڲ�
                        tempWorkflowActualStepsVO.setAuditType( "1" );
                        // ������
                        tempWorkflowActualStepsVO.setAuditTargetId( ownerPositionId );
                        tempWorkflowActualStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                        tempWorkflowActualStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                        tempWorkflowActualStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                        tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
                        tempWorkflowActualStepsVO.setCreateDate( new Date() );

                        // ����Խ����Ȳ���
                        if ( "1".equals( approvalType ) )
                        {
                           tempWorkflowActualStepsVO.setStepIndex( 0 );
                           workflowActualDTO.getWorkflowActualStepsVOs().add( 0, tempWorkflowActualStepsVO );
                        }
                        // �Խ��˺����
                        else if ( "2".equals( approvalType ) )
                        {
                           tempWorkflowActualStepsVO.setStepIndex( workflowActualDTO.getWorkflowActualStepsVOs().size() + 1 );
                           workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
                        }
                        // �Խ��˲�����
                        else if ( "3".equals( approvalType ) )
                        {
                           // ���ù�
                        }
                     }
                  }
                  else
                  {
                     logger.error( "û�в�ѯ��IDΪ" + contractId + "�ķ���Э�飡" );
                     throw new Exception();
                  }
               }
               else
               {
                  logger.error( "��˶���contractId����Ϊ�գ�" );
                  throw new Exception();
               }
            }
            catch ( IllegalAccessException e )
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            catch ( InvocationTargetException e )
            {
               logger.error( "��˶����캯�����ô���" );
               e.printStackTrace();
            }
            catch ( NoSuchMethodException e )
            {
               logger.error( "��˶���û��contractId���ԣ�" );
               e.printStackTrace();
            }
            catch ( Exception e )
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

            // ���ڼ����WorkflowActualStepsVOs����
            if ( workflowActualDTO.getWorkflowActualStepsVOs().size() > 0 )
            {
               workflowActualDTO.getWorkflowActualVO().setSystemId( KANConstants.SYSTEM_ID );
               workflowActualDTO.getWorkflowActualVO().setAccountId( baseVO.getAccountId() );
               workflowActualDTO.getWorkflowActualVO().setCreateBy( baseVO.getCreateBy() );
               workflowActualDTO.getWorkflowActualVO().setPositionId( baseVO.getHistoryVO().getPositionId() );
               workflowActualDTO.getWorkflowActualVO().setWorkflowModuleId( workflowDefineVO.getWorkflowModuleId() );
               workflowActualDTO.getWorkflowActualVO().setDefineId( workflowDefineVO.getDefineId() );
               workflowActualDTO.getWorkflowActualVO().setObjectId( baseVO.getHistoryVO().getObjectId() );
               workflowActualDTO.getWorkflowActualVO().setRightId( baseVO.getHistoryVO().getRightId() );
               // ����״̬Ϊ����״̬
               workflowActualDTO.getWorkflowActualVO().setStatus( "1" );
               workflowActualDTO.getWorkflowActualVO().setNameZH( workflowDefineVO.getNameZH() );
               workflowActualDTO.getWorkflowActualVO().setNameEN( workflowDefineVO.getNameEN() );
            }
         }

         if ( workflowActualDTO != null && workflowActualDTO.getWorkflowActualStepsVOs() != null && workflowActualDTO.getWorkflowActualStepsVOs().size() > 0 )
         {
            // ��������б�
            resetStepIndexAndStatus( workflowActualDTO );
            return workflowActualDTO;
         }
         else
         {
            return null;
         }
      }

      return null;
   }

   /**
    * ������֯�ܹ�����õ���Ч����������
   * 	getWorkflowDefineDTOByOrganization
   *	
   *	@param accountConstants
   *	@param workflowDefineDTOTemp
   *	@param positionId
   *	@return
    */
   private WorkflowDefineDTO calculateWorkflowSteps( final KANAccountConstants accountConstants, final WorkflowDefineDTO workflowDefineDTOTemp, final String positionId )
   {
      // ����������
      WorkflowDefineVO workflowDefineVO = workflowDefineDTOTemp.getWorkflowDefineVO();

      // ��ʼ���������ְ��Ȩ��
      int topPositionGradeWeight = 0;

      // �������ְ��
      final String topPositionGrade = workflowDefineVO.getTopPositionGrade();

      // ��ȡְ��PositionGradeVO
      final PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( topPositionGrade );

      // ����ְ��
      if ( positionGradeVO != null )
      {
         topPositionGradeWeight = Integer.parseInt( positionGradeVO.getWeight() );
      }
      else
      {
         // �����������޴�Ȩ��
         topPositionGradeWeight = Integer.MAX_VALUE;

         //û�в��ҵ���Ӧposition����
         System.out.println( "####û�в��ҵ���ӦtopPositionGrade=" + topPositionGrade + "����" );
      }

      // ����������
      final int steps = workflowDefineVO.getSteps();

      // ���������֯�ܹ���˲���
      final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs = getWorkflowDefineStepsVOsByOrganization( accountConstants, workflowDefineVO, positionId, topPositionGradeWeight, steps );

      // �����Զ�����˲���ְ��Ȩ��
      final List< WorkflowDefineStepsVO > customWorkflowDefineStepsVOs = putPositionWeight( accountConstants, workflowDefineDTOTemp.getWorkflowDefineStepsVOs() );

      // ����˲�������
      sortStepIndex( customWorkflowDefineStepsVOs );

      final List< WorkflowDefineStepsVO > finalWorkflowDefineStepsVOs = calculateWorkflowDefineSteps( accountConstants.ACCOUNT_ID, organizationWorkflowDefineStepsVOs, customWorkflowDefineStepsVOs );

      workflowDefineDTOTemp.setWorkflowDefineStepsVOs( finalWorkflowDefineStepsVOs );

      return workflowDefineDTOTemp;
   }

   //   /**
   //    * ����ְλ�ⲿ��֯�ܹ�����õ���Ч������
   //   *  getWorkflowDefineDTOByOrganization
   //   *  
   //   *  @param accountConstants
   //   *  @param workflowDefineDTOTemp
   //   *  @param positionId
   //   *  @return
   //    */
   //   private WorkflowDefineDTO getWorkflowDefineDTOByOutOrganization( final KANAccountConstants accountConstants, final WorkflowDefineDTO workflowDefineDTOTemp,
   //         final String positionId )
   //   {
   //
   //      // ����������
   //      WorkflowDefineVO workflowDefineVO = workflowDefineDTOTemp.getWorkflowDefineVO();
   //
   //      //����������
   //      final int steps = workflowDefineVO.getSteps();
   //
   //      // ������֯�ܹ���������¼�����б�
   //      final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs = getWorkflowDefineStepsVOsByOutOrganization( accountConstants, workflowDefineVO, positionId, steps );
   //
   //      //final List< WorkflowDefineStepsVO > customWorkflowDefineStepsVOs = putOutPositionWeight( accountConstants, workflowDefineDTOTemp.getWorkflowDefineStepsVOs() );
   //      //            
   //      //final List< WorkflowDefineStepsVO > finalWorkflowDefineStepsVOs = figureOutActualWorkflowDefineSteps( organizationWorkflowDefineStepsVOs, customWorkflowDefineStepsVOs );
   //
   //      sortStepIndex( organizationWorkflowDefineStepsVOs );//����
   //
   //      //workflowDefineDTOTemp.setWorkflowDefineStepsVOs( organizationWorkflowDefineStepsVOs );
   //
   //      return workflowDefineDTOTemp;
   //   }

   /**
    * ������֯�ܹ� ������˲���
   * 	getWorkflowDefineStepsVOsByOrganization
   *	
   *	@param accountConstants
   *	@param workflowDefineVO
   *	@param startPositionId        ��ǰְλ
   *	@param topPositionGradeWeight �������ְ��Ȩ��
   *	@param maxSteps               ���������� 
   *	@return
    */
   // Code Review by siuvan at 2014-05-22 14:44:19
   private List< WorkflowDefineStepsVO > getWorkflowDefineStepsVOsByOrganization( final KANAccountConstants accountConstants, final WorkflowDefineVO workflowDefineVO,
         final String startPositionId, double topPositionGradeWeight, int maxSteps )
   {
      // ��ʼ������ֵ
      final List< WorkflowDefineStepsVO > actualWorkflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

      // ��ȡ��ǰ����ְλPositionDTO
      final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( startPositionId );

      if ( positionDTO != null && positionDTO.getPositionVO() != null )
      {
         // ��ȡ��ǰְλ���ϼ�positionId
         String positionId = positionDTO.getPositionVO().getParentPositionId();

         if ( maxSteps == 0 && topPositionGradeWeight == 0 )
         {
            // return workflowDefineStepsVOs2; 
         }
         else if ( maxSteps == 0 )
         {
            // ���������������Ĭ�ϲ��������޴�
            maxSteps = Integer.MAX_VALUE;
         }
         else if ( topPositionGradeWeight == 0 )
         {
            // ���ְ��Ȩ��Ϊ��0����Ĭ��Ȩ�����޴�
            topPositionGradeWeight = Double.MAX_VALUE;
         }

         final PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( positionId );
         if ( parentPositionVO != null && parentPositionVO.getParentPositionId().equals( "0" ) )
         {
            topPositionGradeWeight = Double.MAX_VALUE;
         }

         // ����װ����˲���
         for ( int i = 0; i < maxSteps; i++ )
         {
            // ��ȡ��ǰְλ�ϼ�PositionDTO
            final PositionDTO parentPositionDTO = accountConstants.getPositionDTOByPositionId( positionId );

            // �Ҳ�����ǰְλ�ϼ����ߵ�ǰְλû�й�Ա��
            if ( parentPositionDTO == null || parentPositionDTO.getPositionStaffRelationVOs() == null || parentPositionDTO.getPositionStaffRelationVOs().size() == 0 )
            {
               break;
            }
            else
            {
               // ��ʼ��PositionVO
               final PositionVO positionVO = parentPositionDTO.getPositionVO();

               // positionId ����null
               if ( positionVO != null && KANUtil.filterEmpty( positionVO.getPositionId() ) != null )
               {
                  // ��ʼ��Ȩ�� 
                  double weightNum = 0;

                  // ����ְ��
                  if ( KANUtil.filterEmpty( positionVO.getPositionGradeId() ) != null )
                  {
                     // ��ȡְ��PositionGradeVO
                     final PositionGradeVO positionGrade = accountConstants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );

                     // ���ڻ������ҵ�ְ��
                     if ( positionGrade != null )
                     {
                        String weight = positionGrade.getWeight();
                        weightNum = Double.parseDouble( weight );

                        // ����������ְ����Ȩ���� ֱ������
                        if ( weightNum > topPositionGradeWeight )
                        {
                           break;
                        }
                     }
                  }

                  // ��wfdsVO���뵽��ʱ���б�����
                  final WorkflowDefineStepsVO wfdsVO = new WorkflowDefineStepsVO();
                  wfdsVO.setStepType( "1" );// ���벽��
                  wfdsVO.setAuditType( "1" );// ��ְλ
                  wfdsVO.setPositionId( positionVO.getPositionId() );
                  wfdsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                  wfdsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                  wfdsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                  wfdsVO.setEmailTemplateId( workflowDefineVO.getEmailTemplateId() );
                  wfdsVO.setSmsTemplateId( workflowDefineVO.getSmsTemplateId() );
                  wfdsVO.setInfoTemplateId( workflowDefineVO.getInfoTemplateId() );
                  wfdsVO.setCreateDate( new Date() );
                  wfdsVO.setReferPositionGrade( positionVO.getPositionGradeId() );//����ְ��
                  wfdsVO.setWeight( weightNum );//����Ȩ��
                  wfdsVO.setStepIndex( i + 1 );
                  //###debug
                  wfdsVO.setTitleZH( positionVO.getTitleZH() );
                  wfdsVO.setTitleEN( positionVO.getTitleEN() );

                  actualWorkflowDefineStepsVOs.add( wfdsVO );
                  // ��ְλid��ֵ����ʱ����
                  positionId = positionVO.getParentPositionId();
                  // ���û�и�����Position��������ѭ������
                  if ( KANUtil.filterEmpty( positionId, "0" ) == null )
                  {
                     break;
                  }
               }
               else
               {
                  break;
               }

            }
         }
      }

      //####debug
      System.out.println( "=====������֯�ܹ�����������ְλ�б�positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : actualWorkflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getTitleZH() + ":{positionId:" + workflowDefineStepsVO.getPositionId() + ",referPositionGrade="
               + workflowDefineStepsVO.getReferPositionGrade() + ",referPositionId=" + workflowDefineStepsVO.getReferPositionId() + ",weight:" + workflowDefineStepsVO.getWeight()
               + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + "} \n " );
      }
      System.out.println( "\n=====end=========" );

      return actualWorkflowDefineStepsVOs;
   }

   /**
    * ������֯�ܹ� ������˲��裨�ⲿְλ��
   *  getWorkflowDefineStepsVOsByOrganization
   *  
   *  @param accountConstants
   *  @param workflowDefineVO
   *  @param startPositionId
   *  @param topPositionGradeWeight
   *  @param maxSteps
   *  @return
    */
   private List< WorkflowDefineStepsVO > getWorkflowDefineStepsVOsByOutOrganization( final KANAccountConstants accountConstants, final WorkflowDefineVO workflowDefineVO,
         final String startPositionId, double topPositionGradeWeight, int maxSteps )
   {
      // ��ʼ��WorkflowDefineStepsVO�б�
      final List< WorkflowDefineStepsVO > workflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

      // ��ȡPositionDTO���ⲿ��
      com.kan.base.domain.management.PositionDTO positionDTO = accountConstants.getEmployeePositionDTOByPositionId( startPositionId );

      if ( positionDTO != null && positionDTO.getPositionVO() != null )
      {
         // �ϼ�positionId
         String parentPositionId = positionDTO.getPositionVO().getParentPositionId();

         // ��������WorkflowDefineStepsVO
         for ( int i = 0; i < maxSteps; )
         {
            // ��ȡ�ϼ�PoistionDTO
            positionDTO = accountConstants.getEmployeePositionDTOByPositionId( parentPositionId );

            if ( positionDTO != null )
            {
               if ( positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getPositionId() ) != null )
               {
                  // ��ȡPositionGradeVO���ⲿ��
                  final com.kan.base.domain.management.PositionGradeVO positionGrade = accountConstants.getEmployeePositionGradeVOByPositionGradeId( positionDTO.getPositionVO().getPositionGradeId() );

                  if ( positionGrade != null && KANUtil.filterEmpty( positionGrade.getWeight() ) != null )
                  {
                     // ����������ְ��Ȩ�أ�����ѭ��
                     if ( Double.parseDouble( positionGrade.getWeight() ) > topPositionGradeWeight )
                     {
                        break;
                     }
                  }

                  // ��ʼ����ʱWorkflowDefineStepsVO
                  final WorkflowDefineStepsVO tempWorkflowDefineStepsVO = new WorkflowDefineStepsVO();
                  tempWorkflowDefineStepsVO.setPositionId( positionDTO.getPositionVO().getPositionId() );
                  tempWorkflowDefineStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                  tempWorkflowDefineStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                  tempWorkflowDefineStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                  tempWorkflowDefineStepsVO.setCreateDate( new Date() );
                  // ����Ϊ�����״̬
                  tempWorkflowDefineStepsVO.setStatus( "1" );
                  // �۰���뷨�������  ����֮���������ɲ���4��   (2(8�η�)=64 �� 8-2=6)
                  tempWorkflowDefineStepsVO.setStepIndex( ( i + 1 ) * 64 );
                  // debug
                  tempWorkflowDefineStepsVO.addSearchField( "titleZH", positionDTO.getPositionVO().getTitleZH() );

                  workflowDefineStepsVOs.add( tempWorkflowDefineStepsVO );

                  // �ϼ�positionId���¸�ֵ
                  parentPositionId = positionDTO.getPositionVO().getParentPositionId();

                  // ����ϼ�positionId�����ڣ�������ѭ������
                  if ( KANUtil.filterEmpty( parentPositionId, "0" ) == null )
                  {
                     break;
                  }

                  i++;
               }
               else
               {
                  break;
               }
            }
            // ����Ҳ�����positionId ��Ӧ��DTO��ֱ������ѭ������
            else
            {
               break;
            }
         }
      }

      //####debug
      System.out.println( "=====�����ⲿ��֯�ܹ�����������ְλ�б�positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{positionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + "} \n " );
      }
      System.out.println( "\n=====end=========" );

      return workflowDefineStepsVOs;
   }

   /**
    * ��������������ְλ��Ȩ��
   * 	addPositionWeight
   *	
   *	@param workflowDefineStepsVOs1
   *	@param accountConstants
   *	@return
    */
   private List< WorkflowDefineStepsVO > putPositionWeight( final KANAccountConstants accountConstants, final List< WorkflowDefineStepsVO > workflowDefineStepsVOs1 )
   {
      // �����������������positionId��Ӧ��weightȨ��
      for ( int i = 0; i < workflowDefineStepsVOs1.size(); i++ )
      {
         // ��ȡ�ڡ�i��������
         final WorkflowDefineStepsVO stepsVO = workflowDefineStepsVOs1.get( i );

         if ( KANUtil.filterEmpty( stepsVO.getPositionId(), "0" ) != null )
         {
            final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( stepsVO.getPositionId() );
            if ( positionDTO != null && positionDTO.getPositionVO() != null )
            {
               final PositionGradeVO positionGrade = accountConstants.getPositionGradeVOByPositionGradeId( positionDTO.getPositionVO().getPositionGradeId() );
               if ( positionGrade == null )
               {
                  stepsVO.setWeight( 0 );
                  stepsVO.setWeight( 0 );
               }
               else
               {
                  stepsVO.setWeight( Double.parseDouble( positionGrade.getWeight() ) );
                  stepsVO.setReferPositionGrade( positionGrade.getPositionGradeId() );
               }
            }
         }
      }

      System.out.println( "=====���ݿⶨ��� �Զ��岽������ְλ�б�positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs1 )
      {
         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{postionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + ","
               + ( workflowDefineStepsVO.getStepType().equals( "1" ) ? "����   " : "�Ǳ���   " ) + "} \n " );
      }
      System.out.println( "\n=====end=========" );
      return workflowDefineStepsVOs1;
   }

   //   /**
   //    * ������������ⲿ���ְλ��Ȩ��
   //    * ��ʱû���õ�
   //   *  addPositionWeight
   //   *  
   //   *  @param workflowDefineStepsVOs1
   //   *  @param accountConstants
   //   *  @return
   //    */
   //   private List< WorkflowDefineStepsVO > putOutPositionWeight( final KANAccountConstants accountConstants, List< WorkflowDefineStepsVO > workflowDefineStepsVOs1 )
   //   {
   //      // ����,�����������positionId��Ӧ��weightȨ��
   //      for ( int i = 0; i < workflowDefineStepsVOs1.size(); i++ )
   //      {
   //         WorkflowDefineStepsVO wfdsVO1 = workflowDefineStepsVOs1.get( i );
   //         String positionId1 = wfdsVO1.getPositionId();
   //         com.kan.base.domain.management.PositionVO positionVO = accountConstants.getEmployeePositionDTOByPositionId( positionId1 ).getPositionVO();
   //         final String postitionGradeId = positionVO.getPositionGradeId();
   //         com.kan.base.domain.management.PositionGradeVO positionGrade = accountConstants.getEmployeePositionGradeVOByPositionGradeId( postitionGradeId );
   //         String weight = positionGrade.getWeight();
   //         double weightNum1 = Double.parseDouble( weight );
   //         wfdsVO1.setWeight( weightNum1 );
   //
   //      }
   //      System.out.println( "=====���ݿⶨ��� �Զ��岽����ⲿְλ���ְλ�б�positionId:(weight)=========" );
   //      for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs1 )
   //      {
   //         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{postionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
   //               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + ","
   //               + ( workflowDefineStepsVO.getStepType().equals( "1" ) ? "����   " : "�Ǳ���   " ) + "} \n " );
   //      }
   //      System.out.println( "\n=====end=========" );
   //      return workflowDefineStepsVOs1;
   //   }

   /**
    * ����Զ��岽��ͻ�����֯�ܹ�����  ����������˲���  -----------mark
   * 	calculateWorkflowDefineSteps
   *	
   *  @param accountId
   *	@param organizationWorkflowDefineStepsVOs ������֯�ܹ�����
   *	@param customWorkflowDefineStepsVOs �Զ��岽��
    */
   private List< WorkflowDefineStepsVO > calculateWorkflowDefineSteps( final String accountId, final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs,
         final List< WorkflowDefineStepsVO > customWorkflowDefineStepsVOs )
   {
      // �Զ��岽��--�Աȼ���õ����ս��
      final List< WorkflowDefineStepsVO > finalWorkflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

      // ���ڼܹ��������˵�positionId
      String lastPositionId = "";
      final String[] msgNoticeArray = new String[ 3 ];

      //####debug
      System.out.println( "=====�Զ�������б�positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : customWorkflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getTitleZH() + ":{auditType=" + workflowDefineStepsVO.getAuditType() + ",stepIndex=" + workflowDefineStepsVO.getStepIndex()
               + ",positionId:" + workflowDefineStepsVO.getPositionId() + ",staffId:" + workflowDefineStepsVO.getStaffId() + ",joinType=" + workflowDefineStepsVO.getJoinType()
               + ",referPositionGrade=" + workflowDefineStepsVO.getReferPositionGrade() + ",referPositionId=" + workflowDefineStepsVO.getReferPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + "} \n " );
      }
      System.out.println( "\n=====end=========" );

      // ��϶����������*****
      if ( customWorkflowDefineStepsVOs != null && customWorkflowDefineStepsVOs.size() > 0 )
      {
         // ������֯�ܹ�����Ϊ0
         if ( organizationWorkflowDefineStepsVOs != null && organizationWorkflowDefineStepsVOs.size() == 0 )
         {
            // ���Զ���˳��˳��ļ��뵽���յ���˲�����
            for ( WorkflowDefineStepsVO wfdsVO1 : customWorkflowDefineStepsVOs )
            {
               finalWorkflowDefineStepsVOs.add( wfdsVO1 );
            }
         }
         // ���߶���Ϊ��
         else
         {
            // 1.������л�����֯�ܹ��Ĳ���
            finalWorkflowDefineStepsVOs.addAll( organizationWorkflowDefineStepsVOs );

            final WorkflowDefineStepsVO lastWorkflowDefineStepsVO = organizationWorkflowDefineStepsVOs.get( organizationWorkflowDefineStepsVOs.size() - 1 );
            if ( lastWorkflowDefineStepsVO != null )
            {
               lastPositionId = lastWorkflowDefineStepsVO.getPositionId();

               msgNoticeArray[ 0 ] = lastWorkflowDefineStepsVO.getEmailTemplateId();
               msgNoticeArray[ 1 ] = lastWorkflowDefineStepsVO.getInfoTemplateId();
               msgNoticeArray[ 2 ] = lastWorkflowDefineStepsVO.getSmsTemplateId();
            }

            // 2.�𲽺ϲ��Զ��岽��
            for ( WorkflowDefineStepsVO customWorkflowDefineStepsVO : customWorkflowDefineStepsVOs )
            {
               if ( isDuplicateWorkflowDefineSteps( finalWorkflowDefineStepsVOs, customWorkflowDefineStepsVO, accountId ) )
                  calculateWorkflowDefineSteps( finalWorkflowDefineStepsVOs, customWorkflowDefineStepsVO );
            }
         }
      }
      // �Զ��岽��Ϊ��
      else
      {
         // 1.������л�����֯�ܹ��Ĳ���
         finalWorkflowDefineStepsVOs.addAll( organizationWorkflowDefineStepsVOs );
         if ( organizationWorkflowDefineStepsVOs != null && organizationWorkflowDefineStepsVOs.size() > 0 )
         {
            final WorkflowDefineStepsVO lastWorkflowDefineStepsVO = organizationWorkflowDefineStepsVOs.get( organizationWorkflowDefineStepsVOs.size() - 1 );
            if ( lastWorkflowDefineStepsVO != null )
            {
               lastPositionId = lastWorkflowDefineStepsVO.getPositionId();

               msgNoticeArray[ 0 ] = lastWorkflowDefineStepsVO.getEmailTemplateId();
               msgNoticeArray[ 1 ] = lastWorkflowDefineStepsVO.getInfoTemplateId();
               msgNoticeArray[ 2 ] = lastWorkflowDefineStepsVO.getSmsTemplateId();
            }
         }
      }

      // ���ⲽ��
      final WorkflowDefineStepsVO specialWorkflowDefineStepsVO = getSpecialWorkflowDefineSteps( accountId, lastPositionId, msgNoticeArray );

      // Ұ�����ⲽ����ù��������һ��
      if ( specialWorkflowDefineStepsVO != null )
      {
         finalWorkflowDefineStepsVOs.add( specialWorkflowDefineStepsVO );
      }

      //####debug
      System.out.println( "=====1���ս�����ְλ�б�positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : finalWorkflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{positionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + "} \n " );
      }
      System.out.println( "\n=====end=========" );
      return finalWorkflowDefineStepsVOs;
   }

   // Ұ�����ⲽ��
   // Added by siuvan 2015-04-14
   private WorkflowDefineStepsVO getSpecialWorkflowDefineSteps( final String accountId, final String lastPositionId, final String[] msgNoticeArray )
   {
      if ( KANUtil.filterEmpty( accountId ).equals( "100056" ) && KANUtil.filterEmpty( lastPositionId ) != null )
      {
         final PositionVO lastPositionVO = KANConstants.getKANAccountConstants( accountId ).getPositionVOByPositionId( lastPositionId );
         if ( lastPositionVO != null && KANUtil.filterEmpty( lastPositionVO.getTitleZH() ) != null && !KANUtil.filterEmpty( lastPositionVO.getTitleZH() ).contains( "����" ) )
         {
            final PositionVO parentPositionVO = KANConstants.getKANAccountConstants( accountId ).getPositionVOByPositionId( lastPositionVO.getParentPositionId() );
            if ( parentPositionVO != null && KANUtil.filterEmpty( parentPositionVO.getTitleZH() ) != null && parentPositionVO.getTitleZH().contains( "����" ) )
            {
               final WorkflowDefineStepsVO tempWorkflowDefineStepsVO = new WorkflowDefineStepsVO();
               tempWorkflowDefineStepsVO.setStepType( "3" );// �ο�����
               tempWorkflowDefineStepsVO.setAuditType( "1" );// �ڲ�ְλ
               tempWorkflowDefineStepsVO.setPositionId( parentPositionVO.getPositionId() );// ����PositionId
               tempWorkflowDefineStepsVO.setStepIndex( 99 );
               tempWorkflowDefineStepsVO.setSendEmail( "1" ); // ǿ�Ʒ��ʼ�����
               tempWorkflowDefineStepsVO.setEmailTemplateId( msgNoticeArray[ 0 ] );
               tempWorkflowDefineStepsVO.setSendInfo( "1" );
               tempWorkflowDefineStepsVO.setInfoTemplateId( msgNoticeArray[ 1 ] );
               tempWorkflowDefineStepsVO.setSendSMS( "0" );
               tempWorkflowDefineStepsVO.setSmsTemplateId( msgNoticeArray[ 2 ] );

               return tempWorkflowDefineStepsVO;
            }
         }
      }

      return null;
   }

   // �����ظ����Զ��岽��
   private boolean isDuplicateWorkflowDefineSteps( final List< WorkflowDefineStepsVO > baseStepsVOs, final WorkflowDefineStepsVO defineStepsVO, final String accountId )
   {
      // ���������1:�ڲ�ְλ4:�ڲ�Ա��5:�Խ���
      String auditType = defineStepsVO.getAuditType();
      // ��������1:����2:����3:�ο�
      String stepType = defineStepsVO.getStepType();
      // ְλID
      String tempPositionId = defineStepsVO.getPositionId();
      // staffId
      String tempStaffId = defineStepsVO.getStaffId();

      // ����Զ��岽���Ǳ��벽��
      if ( "1".equals( KANUtil.filterEmpty( stepType ) ) )
      {
         // Ѱ����֯�ܹ����������ظ�
         for ( WorkflowDefineStepsVO baseStepsVO : baseStepsVOs )
         {
            if ( "4".equals( auditType ) || "5".equals( auditType ) )
            {
               final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( accountId ).getStaffDTOsByPositionId( baseStepsVO.getPositionId() );
               if ( staffDTOs != null && staffDTOs.size() > 0 )
               {
                  for ( StaffDTO staffDTO : staffDTOs )
                  {
                     if ( staffDTO.getStaffVO() != null && staffDTO.getStaffVO().getStaffId().equals( tempStaffId ) )
                     {
                        return false;
                     }
                  }
               }
            }
            else if ( "1".equals( auditType ) )
            {
               if ( baseStepsVO.getPositionId().equals( tempPositionId ) )
               {
                  return false;
               }
            }
         }
      }

      return true;
   }

   /**
    *   �ϲ������Զ��岽��  -----------mark
    * calculateWorkflowDefineSteps
    *	
    *	@param baseStepsVOs ������֯�ܹ����� ���Ѿ�������Ĳ��裩
    *	@param defineStepsVO �Զ��岽��
    */
   // Code Review by siuvan at 2014-05-22 14:57:07
   private List< WorkflowDefineStepsVO > calculateWorkflowDefineSteps( final List< WorkflowDefineStepsVO > baseStepsVOs, final WorkflowDefineStepsVO defineStepsVO )
   {
      // ���������֯�ܹ�������Ϊ��0����ȡ�Զ��岽��
      if ( baseStepsVOs.size() == 0 )
      {
         baseStepsVOs.add( defineStepsVO );
      }
      else
      {
         int findIndex = -1;// �ҵ���Ӧλ�õ��±�
         int parallelStartIndex = -1;// ���в��迪ʼ�±�
         int parallelEndIndex = -1;//���в�������±�
         int equalsIndex = -1;// �ҵ���ͬ��˲�����±�
         int stepIndexTemp = -1;// ��ǰ����Index

         // ����������֯�ܹ�����
         for ( int i = 0; i < baseStepsVOs.size(); i++ )
         {
            WorkflowDefineStepsVO workflowDefineStepsVO = baseStepsVOs.get( i );
            // ����һ������Index��ͬ
            if ( stepIndexTemp != workflowDefineStepsVO.getStepIndex() )
            {
               // �ҵ���Ӧ��λ�ú�Ͳ����Ѱ�������ˣ��ϴ��������õĲ��в���������Ϊ�ҵ������Ӧ�Ĳ��в������ʼ����
               if ( findIndex == -1 )
               {
                  stepIndexTemp = workflowDefineStepsVO.getStepIndex();
                  parallelStartIndex = i;
                  parallelEndIndex = i;
               }
            }
            // ����һ������Index��ͬ
            else
            {
               //    ����ͬһ���в���ʱ parallelEndIndex������
               if ( stepIndexTemp == baseStepsVOs.get( parallelStartIndex ).getStepIndex() )
               {
                  parallelEndIndex = i;
               }
            }

            // ���뷽ʽ 
            String joinType = defineStepsVO.getJoinType();
            // 1:��ְλ
            if ( "1".equals( joinType ) )
            {
               // �����ҵ����ʵ�ְλ
               if ( workflowDefineStepsVO.getPositionId() != null && workflowDefineStepsVO.getPositionId().equals( defineStepsVO.getReferPositionId() ) )
               {
                  // ֻҪ��һ��
                  if ( findIndex == -1 )
                  {
                     findIndex = i;
                  }
               }
            }
            // 2:��ְ�� 
            else if ( "2".endsWith( joinType ) )
            {
               //�ҵ����ʵ�ְ�������ȥ         
               if ( workflowDefineStepsVO.getReferPositionGrade() != null && workflowDefineStepsVO.getReferPositionGrade().equals( defineStepsVO.getReferPositionGrade() ) )
               {
                  // ֻҪ��һ��
                  if ( findIndex == -1 )
                  {
                     findIndex = i;
                  }
               }
            }
            else
            {

            }
            // �������� ���������ͬ
            if ( workflowDefineStepsVO.getAuditType() != null && workflowDefineStepsVO.getAuditType().equals( defineStepsVO.getAuditType() ) && equalsIndex == -1 )
            {
               // 1�ڲ�ְλID  �����ְλ��ͬ
               if ( "1".equals( workflowDefineStepsVO.getAuditType() ) && workflowDefineStepsVO.getPositionId() != null
                     && workflowDefineStepsVO.getPositionId().equals( defineStepsVO.getPositionId() ) )
               {
                  equalsIndex = i;
               }
               // 4:�ڲ�Ա��Id �������staffId��ͬ
               else if ( "4".equals( workflowDefineStepsVO.getAuditType() ) && workflowDefineStepsVO.getPositionId() != null
                     && workflowDefineStepsVO.getPositionId().equals( defineStepsVO.getPositionId() ) )
               {
                  equalsIndex = i;
               }
            }

         }
         // �ο�ְλ���Լ�����Ĳ�����ϲ�
         if ( equalsIndex == findIndex && equalsIndex != -1 )
         {
            return baseStepsVOs;
         }

         /** ��ʼ�ϲ����� **/
         // ����˳��
         String joinOrderType = defineStepsVO.getJoinOrderType();
         // ��������
         String stepType = defineStepsVO.getStepType();

         // �ҵ��˶�Ӧ�Ĳο�����
         if ( findIndex != -1 )
         {
            // �Ѵ�������ɾ��
            if ( equalsIndex != -1 )
            {
               // �Զ���Ϊ�ο����裬��ʵ�ʲ���Ϊ1���벽���� �ò��軹�Ǳ��벽�� ------mark
               // TODO ��������Ǻ�ȷ�������ܻ��
               if ( "3".equals( stepType ) && "1".equals( baseStepsVOs.get( equalsIndex ).getStepType() ) )
               {
                  defineStepsVO.setStepType( "1" );
               }
               // equalsIndex ǰ����û�к�equalsIndex���еĲ���
               boolean hasParallel = false;
               int listSize = baseStepsVOs.size();
               // equalsIndex ǰ����Ԫ�� �� ���� ǰ��һ���Ƿ��ǲ��в���
               if ( equalsIndex - 1 >= 0 )
               {
                  hasParallel = baseStepsVOs.get( equalsIndex ).getStepIndex() == baseStepsVOs.get( equalsIndex - 1 ).getStepIndex();
               }
               if ( !hasParallel && equalsIndex >= 0 && equalsIndex + 1 < listSize )
               {
                  hasParallel = baseStepsVOs.get( equalsIndex ).getStepIndex() == baseStepsVOs.get( equalsIndex + 1 ).getStepIndex();
               }

               baseStepsVOs.remove( equalsIndex );
               // ��������ڲ��еĲ��裬ɾ�������stepIndex�Զ���1
               if ( !hasParallel )
               {
                  for ( int j = equalsIndex; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexSubOne();
                  }
               }
               if ( parallelEndIndex > equalsIndex )
               {
                  parallelEndIndex--;
               }
               if ( parallelStartIndex > equalsIndex )
               {
                  parallelStartIndex--;
               }

               if ( findIndex > equalsIndex )
               {
                  findIndex--;
               }
            }

            WorkflowDefineStepsVO findDefineStepsVO = baseStepsVOs.get( findIndex );
            // 1:���벽�裨�������б��뺬�У�3:�ο����裨ֻ��֪ͨ������������
            if ( "1".equals( stepType ) || "3".equals( stepType ) )
            {
               // 1:����
               if ( "1".equals( joinOrderType ) )
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() );
                  // ���뵽parallelEndIndex���棬
                  baseStepsVOs.add( parallelEndIndex + 1, defineStepsVO );

               }// 2:���ǰ
               else if ( "2".equals( joinOrderType ) )
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() );
                  // parallelStartIndexǰ��Ĳ���StepIndex��1 Ȼ��  ���뵽parallelStartIndexǰ�棬
                  for ( int j = parallelStartIndex; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  baseStepsVOs.add( parallelStartIndex, defineStepsVO );

               }// 3:��˺�
               else if ( "3".equals( joinOrderType ) )
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() + 1 );
                  // parallelStartIndex����Ĳ���StepIndex��1 Ȼ��  ���뵽parallelStartIndexǰ�棬
                  for ( int j = parallelStartIndex + 1; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  // ���뵽parallelEndIndex���棬
                  baseStepsVOs.add( parallelEndIndex + 1, defineStepsVO );
               }
               // Ĭ����˺�
               else
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() + 1 );
                  // parallelStartIndex����Ĳ���StepIndex��1 Ȼ��  ���뵽parallelStartIndexǰ�棬
                  for ( int j = parallelStartIndex + 1; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  // ���뵽parallelEndIndex���棬
                  baseStepsVOs.add( parallelEndIndex + 1, defineStepsVO );
               }

            }
            // 2:���β��裨�������к�������и��ǣ�
            else if ( "2".equals( stepType ) )
            {
               // ��������һ�������
               if ( equalsIndex != -1 )
               {

               }
               // �����򸲸���Ϣ���ѵ�����
               else
               {
                  mergeDefineStepsVO( baseStepsVOs.get( equalsIndex ), defineStepsVO );
               }
            }
            else
            {
               //ûд��������
            }
         }
         // û�ҵ��ο�**����뵽�����
         else
         {
            // �Ѵ��򸲸�
            if ( equalsIndex != -1 )
            {
               mergeDefineStepsVO( baseStepsVOs.get( equalsIndex ), defineStepsVO );
            }
            else
            {
               // 1:����     
               if ( "1".equals( joinOrderType ) )
               {
                  // TODO ��ȷ�����ܻ��
                  // ���е���һ��
                  defineStepsVO.setStepIndex( 1 );
                  // �ϲ����Ѿ�������Ĳ�����
                  baseStepsVOs.add( 0, defineStepsVO );
               }
               // 2:���ǰ
               else if ( "2".equals( joinOrderType ) )
               {
                  // TODO ��ȷ�����ܻ��
                  // ����������������ǰ��
                  defineStepsVO.setStepIndex( 1 );
                  for ( int j = 0; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  baseStepsVOs.add( 0, defineStepsVO );

               }
               // 3:��˺�
               else if ( "3".equals( joinOrderType ) )
               {
                  // TODO ��ȷ�����ܻ��
                  // �������������������
                  defineStepsVO.setStepIndex( baseStepsVOs.get( baseStepsVOs.size() - 1 ).getStepIndex() + 1 );
                  baseStepsVOs.add( defineStepsVO );
               }
               // 0:Ĭ��Ϊ��Ȩ��ʱ������Ȩ��������У�Ȩ�ز������� --mark---���ܿ��ǣ� ��Ϊ�������˳���Ӧ��Ȩ���Ѿ������������
               else
               {
                  // ���û��������˺�
                  defineStepsVO.setStepIndex( baseStepsVOs.get( baseStepsVOs.size() - 1 ).getStepIndex() + 1 );
                  baseStepsVOs.add( defineStepsVO );
               }
            }
         }
      }

      return baseStepsVOs;
   }

   /**
    * ����
   * 	sortStepIndex
   *	
   *	@param workflowDefineStepsVOs
    */
   private void sortStepIndex( final List< WorkflowDefineStepsVO > workflowDefineStepsVOs )
   {
      // ð������
      int size = workflowDefineStepsVOs.size();
      if ( size > 1 )
      {
         for ( int i = 1; i < size; i++ )
         {
            for ( int j = 0; j < size - i; j++ )
            {
               int index = workflowDefineStepsVOs.get( j ).getStepIndex();
               int index2 = workflowDefineStepsVOs.get( j + 1 ).getStepIndex();
               if ( index > index2 )
               {
                  final WorkflowDefineStepsVO temp = workflowDefineStepsVOs.get( j );
                  workflowDefineStepsVOs.set( j, workflowDefineStepsVOs.get( j + 1 ) );
                  workflowDefineStepsVOs.set( j + 1, temp );
               }
            }
         }
      }
   }

   /**
    * ���� stepIndex�����״̬ --- mark
   * resetStepIndex
   *  @param workflowDefineStepsVOs
    */
   private void resetStepIndexAndStatus( final WorkflowActualDTO workflowActualDTO )
   {
      final List< WorkflowActualStepsVO > workflowActualStepsVOs = workflowActualDTO.getWorkflowActualStepsVOs();

      int startIndex = 1;
      for ( int i = 0, actualIndex = 1, tempIndex = 0; i < workflowActualStepsVOs.size(); i++ )
      {
         final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsVOs.get( i );

         if ( i == 0 )
         {
            tempIndex = workflowActualStepsVOs.get( 0 ).getStepIndex();
         }
         else
         {
            if ( tempIndex != workflowActualStepsVO.getStepIndex() )
            {
               tempIndex = workflowActualStepsVO.getStepIndex();
               if ( startIndex == actualIndex )
               {
                  // ȫƱͨ��
                  boolean flag = true;
                  for ( int j = i - 1; j >= 0; j-- )
                  {
                     if ( !"5".equals( workflowActualStepsVOs.get( j ).getStatus() ) )
                     {
                        flag = false;
                     }
                  }
                  if ( flag )
                  {
                     startIndex++;
                  }
               }

               actualIndex++;
            }
         }
         workflowActualStepsVO.setStepIndex( actualIndex );

         // ����ǵ�һ�����������״̬Ϊ 2,�����״̬
         if ( actualIndex == startIndex )
         {
            // �ο�����ֱ�ӹ�
            if ( "3".equals( workflowActualStepsVO.getStepType() ) )
            {
               // ��֪ͨ
               workflowActualStepsVO.setStatus( "5" );
            }
            else
            {
               workflowActualStepsVO.setStatus( "2" );
            }
         }
         // ���� �������״̬Ϊ 1,������״̬
         else
         {
            workflowActualStepsVO.setStatus( "3".equals( workflowActualStepsVO.getStepType() ) ? "6" : "1" );
         }
      }

      //####debug
      System.out.println( "=====2��������б�StepsId ��positionId:(weight)=========" );
      for ( WorkflowActualStepsVO workflowActualStepsVO : workflowActualStepsVOs )
      {
         System.out.print( workflowActualStepsVO.getSearchField().get( "titleZH" ) + ":{AuditTargetId:" + workflowActualStepsVO.getAuditTargetId() + ",AuditType:"
               + workflowActualStepsVO.getAuditType() + ",setpIndex:" + workflowActualStepsVO.getStepIndex() + ",status=" + workflowActualStepsVO.getStatus() + "} \n " );
      }
      System.out.println( "\n=====end=========" );
   }

   /**
    * ����ְλ�ڲ����崴��ʵ�ʹ�����
   * 	createWorkflowActualDTO
   *	
   *	@param validWorkflowDefineDTO
   *	@param baseVO
   *	@return
    */
   private WorkflowActualDTO createWorkflowActualDTO( final WorkflowDefineDTO validWorkflowDefineDTO, final BaseVO baseVO )
   {
      if ( validWorkflowDefineDTO != null )
      {
         // ��ʼ��WorkflowActualDTO
         final WorkflowActualDTO workflowActualDTO = new WorkflowActualDTO();

         // ��ȡWorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = validWorkflowDefineDTO.getWorkflowDefineVO();

         // ����WorkflowDefineStepsVOs����������
         if ( workflowDefineVO != null && validWorkflowDefineDTO.getWorkflowDefineStepsVOs() != null && validWorkflowDefineDTO.getWorkflowDefineStepsVOs().size() > 0 )
         {
            // ��ʼ��WorkflowActualVO
            final WorkflowActualVO tempWorkflowActualVO = new WorkflowActualVO();
            // �����IN_HOUSE
            //            if ( KANConstants.ROLE_IN_HOUSE.equals( baseVO.getRole() ) )
            //            {
            tempWorkflowActualVO.setClientId( baseVO.getClientId() );
            tempWorkflowActualVO.setCorpId( baseVO.getCorpId() );
            //            }
            tempWorkflowActualVO.setSystemId( KANConstants.SYSTEM_ID );
            tempWorkflowActualVO.setAccountId( baseVO.getAccountId() );
            tempWorkflowActualVO.setNameZH( workflowDefineVO.getNameZH() );
            tempWorkflowActualVO.setNameEN( workflowDefineVO.getNameEN() );
            tempWorkflowActualVO.setCreateBy( baseVO.getModifyBy() );
            tempWorkflowActualVO.setPositionId( baseVO.getHistoryVO().getPositionId() );
            tempWorkflowActualVO.setWorkflowModuleId( workflowDefineVO.getWorkflowModuleId() );
            tempWorkflowActualVO.setDefineId( workflowDefineVO.getDefineId() );
            tempWorkflowActualVO.setObjectId( baseVO.getHistoryVO().getObjectId() );
            tempWorkflowActualVO.setRightId( baseVO.getHistoryVO().getRightId() );
            tempWorkflowActualVO.setStatus( "1" );

            workflowActualDTO.setWorkflowActualVO( tempWorkflowActualVO );

            // ���㹤����ʵ�ʲ���
            for ( int i = 0; i < validWorkflowDefineDTO.getWorkflowDefineStepsVOs().size(); i++ )
            {
               // ��ʼ��WorkflowDefineStepsVO
               final WorkflowDefineStepsVO workflowDefineStepsVO = validWorkflowDefineDTO.getWorkflowDefineStepsVOs().get( i );

               // ��ʼ��WorkflowActualStepsVO
               final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
               tempWorkflowActualStepsVO.setStepType( workflowDefineStepsVO.getStepType() );
               tempWorkflowActualStepsVO.setAuditTargetId( workflowDefineStepsVO.getPositionId() );

               // ���÷�Χ  - �ڲ�
               if ( workflowDefineVO.getScope() == 1 )
               {
                  // �ڲ�ְλ
                  tempWorkflowActualStepsVO.setAuditType( "1" );

                  // ���������ʽ���ڲ�Ա��
                  if ( "4".equals( workflowDefineStepsVO.getAuditType() ) )
                  {
                     // �ڲ�Ա��
                     tempWorkflowActualStepsVO.setAuditType( "4" );
                     tempWorkflowActualStepsVO.setAuditTargetId( workflowDefineStepsVO.getStaffId() );
                  }
                  // ���������ʽ�ǶԽ���
                  else if ( "5".equals( workflowDefineStepsVO.getAuditType() ) )
                  {
                     tempWorkflowActualStepsVO.setAuditTargetId( baseVO.getHistoryVO().getOwner() );
                  }
               }
               // ���÷�Χ  - �ⲿ
               else if ( workflowDefineVO.getScope() == 2 )
               {
                  tempWorkflowActualStepsVO.setAuditType( "2" );
               }

               // ������ڲ�ְλ ���ְλ������UserVO
               if ( "1".equals( tempWorkflowActualStepsVO.getAuditType() ) )
               {
                  final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( baseVO.getAccountId() ).getStaffDTOsByPositionId( tempWorkflowActualStepsVO.getAuditTargetId() );
                  if ( staffDTOs != null && staffDTOs.size() > 0 )
                  {
                     int count = 0;
                     for ( StaffDTO staffDTO : staffDTOs )
                     {
                        if ( staffDTO != null && staffDTO.getUserVO() != null )
                        {
                           count++;
                           break;
                        }
                     }

                     if ( count == 0 )
                        continue;

                     // ���ü���
                     count = 0;
                  }
                  else
                  {
                     continue;
                  }
               }

               tempWorkflowActualStepsVO.setStepIndex( workflowDefineStepsVO.getStepIndex() );
               tempWorkflowActualStepsVO.setSendEmail( workflowDefineStepsVO.getSendEmail() );
               tempWorkflowActualStepsVO.setSendInfo( workflowDefineStepsVO.getSendInfo() );
               tempWorkflowActualStepsVO.setSendSMS( workflowDefineStepsVO.getSendSMS() );
               tempWorkflowActualStepsVO.setEmailTemplateType( workflowDefineStepsVO.getEmailTemplateId() );
               tempWorkflowActualStepsVO.setInfoTemplateType( workflowDefineStepsVO.getInfoTemplateId() );
               tempWorkflowActualStepsVO.setSmsTemplateType( workflowDefineStepsVO.getSmsTemplateId() );
               tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
               tempWorkflowActualStepsVO.setCreateDate( new Date() );
               tempWorkflowActualStepsVO.setModifyBy( baseVO.getCreateBy() );
               // �������״̬
               tempWorkflowActualStepsVO.setStatus( "1" );
               workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
            }

            return workflowActualDTO;
         }
      }

      return null;
   }

   /***
    * ����������Ķ�������浽���ݿ�
   * 	createWorkflowActual
   *	
   *	@param validWorkflowActualDTO
   *	@param baseVO
   *	@return
   *	@throws KANException
    */
   public WorkflowActualVO createWorkflowActual( final WorkflowActualDTO validWorkflowActualDTO, final BaseVO baseVO ) throws KANException
   {

      if ( validWorkflowActualDTO != null )
      {
         final List< WorkflowActualStepsVO > workflowActualStepsVOs2 = validWorkflowActualDTO.getWorkflowActualStepsVOs();
         final WorkflowActualVO workflowActualVO = validWorkflowActualDTO.getWorkflowActualVO();

         if ( workflowActualStepsVOs2 != null && workflowActualStepsVOs2.size() > 0 )
         {
            //  ִ�й�����
            try
            {
               //��������
               startTransaction();
               // #########nameZH ��  nameEN ?????
               final String historyNameZH = baseVO.getHistoryVO().getNameZH();
               final String historyNameEN = baseVO.getHistoryVO().getNameEN();
               workflowActualVO.setNameZH( workflowActualVO.getNameZH() + ( historyNameZH == null ? "" : "(" + historyNameZH + ")" ) );
               workflowActualVO.setNameEN( workflowActualVO.getNameEN() + ( historyNameEN == null ? "" : "(" + historyNameEN + ")" ) );
               workflowActualDao.insertWorkflowActual( workflowActualVO );
               final String workflowId = workflowActualVO.getWorkflowId();

               //2. ��baseVO ���浽BaseHistory���� ͬʱ�õ�id
               baseVO.getHistoryVO().setWorkflowId( workflowId );

               //3. ����History
               saveObjectHistory( baseVO );

               //4.��������֯�ܹ�������˲���
               for ( int i = 0; i < workflowActualStepsVOs2.size(); i++ )
               {
                  final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsVOs2.get( i );
                  workflowActualStepsVO.setWorkflowId( workflowId );
                  // ����һ�������������֤�ʼ�����
                  final String randomKey = UUID.randomUUID().toString().trim().replaceAll( "-", "" );
                  workflowActualStepsVO.setRandomKey( randomKey );
                  workflowActualStepsDao.insertWorkflowActualSteps( workflowActualStepsVO );
                  if ( "2".equals( workflowActualStepsVO.getStatus() ) || "5".equals( workflowActualStepsVO.getStatus() ) )
                  {
                     workflowActualStepsVO.setHistoryVO( baseVO.getHistoryVO() );
                     KANSendMessageUtil.newInstance().sendMessageForWorkflow( workflowActualVO, workflowActualStepsVO );
                  }
               }

               //�ύ����
               commitTransaction();

               return workflowActualVO;
            }
            catch ( Exception e )
            {
               rollbackTransaction();
               e.printStackTrace();
               throw new KANException( e );
            }
         }

      }
      return null;
   }

   public WorkflowActualVO createWorkflowActual_nt( final WorkflowActualDTO validWorkflowActualDTO, final BaseVO baseVO ) throws KANException
   {

      if ( validWorkflowActualDTO != null )
      {
         final List< WorkflowActualStepsVO > workflowActualStepsVOs2 = validWorkflowActualDTO.getWorkflowActualStepsVOs();
         final WorkflowActualVO workflowActualVO = validWorkflowActualDTO.getWorkflowActualVO();

         if ( workflowActualStepsVOs2 != null && workflowActualStepsVOs2.size() > 0 )
         {
            //  ִ�й�����
            try
            {
               final String historyNameZH = baseVO.getHistoryVO().getNameZH();
               final String historyNameEN = baseVO.getHistoryVO().getNameEN();
               workflowActualVO.setNameZH( workflowActualVO.getNameZH() + ( historyNameZH == null ? "" : "(" + historyNameZH + ")" ) );
               workflowActualVO.setNameEN( workflowActualVO.getNameEN() + ( historyNameEN == null ? "" : "(" + historyNameEN + ")" ) );
               workflowActualDao.insertWorkflowActual( workflowActualVO );
               final String workflowId = workflowActualVO.getWorkflowId();

               //2. ��baseVO ���浽BaseHistory���� ͬʱ�õ�id
               baseVO.getHistoryVO().setWorkflowId( workflowId );

               //3. ����History
               saveObjectHistory( baseVO );

               //4.��������֯�ܹ�������˲���
               for ( int i = 0; i < workflowActualStepsVOs2.size(); i++ )
               {
                  final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsVOs2.get( i );
                  workflowActualStepsVO.setWorkflowId( workflowId );
                  // ����һ�������������֤�ʼ�����
                  final String randomKey = UUID.randomUUID().toString().trim().replaceAll( "-", "" );
                  workflowActualStepsVO.setRandomKey( randomKey );
                  workflowActualStepsDao.insertWorkflowActualSteps( workflowActualStepsVO );
                  if ( "2".equals( workflowActualStepsVO.getStatus() ) || "5".equals( workflowActualStepsVO.getStatus() ) )
                  {
                     workflowActualStepsVO.setHistoryVO( baseVO.getHistoryVO() );
                     KANSendMessageUtil.newInstance().sendMessageForWorkflow( workflowActualVO, workflowActualStepsVO );
                  }

               }

               return workflowActualVO;
            }
            catch ( Exception e )
            {
               e.printStackTrace();
               throw new KANException( e );
            }
         }

      }
      return null;
   }

   /**
    * �������history
   *  saveObjectHistory
   *  
   *  @param baseVO
   *  @return
    */
   private HistoryVO saveObjectHistory( BaseVO baseVO )
   {
      HistoryVO historyVO = baseVO.getHistoryVO();
      historyDao.insertObject( historyVO );
      return historyVO;

   }

   /**
    * ������յ���˲���
   *  getValidWorkflowDefineDTO
   *  
   *  @param baseVO  ��������
   *  @return
    */
   public WorkflowActualDTO getValidWorkflowActualDTO( final BaseVO baseVO )
   {
      //�ж��ǲ�������ͨ����Ķ���
      if ( isPassObject( baseVO ) )
         return null;

      // �õ�KANAccountConstant��WORKFLOW_MODULE_DTO
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( baseVO.getAccountId() );

      // ���WorkflowDefineDTO�б�
      final List< WorkflowDefineDTO > workflowDefineDTOTemps = getWorkflowDefineDTO( accountConstants, baseVO.getHistoryVO().getModuleId(), baseVO.getHistoryVO().getRightId(), baseVO.getCorpId() );

      // ���WorkflowDefineDTO
      final WorkflowDefineDTO workflowDefineDTOTemp = searchMatchDefineDTO( accountConstants, workflowDefineDTOTemps, baseVO );

      if ( workflowDefineDTOTemp != null )
      {
         // �����Ч�Ĺ�����
         final WorkflowActualDTO validWorkflowActualDTO = getValidWorkflowActualDTO( workflowDefineDTOTemp, accountConstants, baseVO );

         return checkWorkflowactualSteps( validWorkflowActualDTO );

      }

      return null;
   }

   // ���WorkflowactualDTO�е�AcutalStepsVOs�Ƿ��б��벽��
   // Add by siuvan @2014-09-01
   private WorkflowActualDTO checkWorkflowactualSteps( final WorkflowActualDTO validWorkflowActualDTO )
   {
      if ( validWorkflowActualDTO != null && validWorkflowActualDTO.getWorkflowActualStepsVOs() != null && validWorkflowActualDTO.getWorkflowActualStepsVOs().size() > 0 )
      {
         for ( WorkflowActualStepsVO workflowActualStepsVO : validWorkflowActualDTO.getWorkflowActualStepsVOs() )
         {
            if ( workflowActualStepsVO != null && KANUtil.filterEmpty( workflowActualStepsVO.getStepType() ) != null && workflowActualStepsVO.getStepType().equals( "1" ) )
            {
               return validWorkflowActualDTO;
            }
         }
      }

      return null;
   }

   /***
    * 
   * �����Զ������������ƥ���workflowDefineDTO
   *	
   *	@param accountConstants 
   *	@param workflowDefineDTOTemps 
   *	@param baseVO ��������
   *	@return
    */
   private WorkflowDefineDTO searchMatchDefineDTO( final KANAccountConstants accountConstants, final List< WorkflowDefineDTO > workflowDefineDTOs, final BaseVO baseVO )
   {
      WorkflowDefineDTO workflowDefineDTOTemp = null;

      // ���ڹ���������
      if ( workflowDefineDTOs != null && workflowDefineDTOs.size() > 0 )
      {
         for ( WorkflowDefineDTO workflowDefineDTO : workflowDefineDTOs )
         {
            // ��ʼ����������WorkflowDefineRequirementsVOs
            List< WorkflowDefineRequirementsVO > workflowDefineRequirementsVOs = null;

            boolean isLeaveAccessAction = isLeaveAccessAction( baseVO.getHistoryVO().getAccessAction() );
            boolean isOTAccessAction = isOTAccessAction( baseVO.getHistoryVO().getAccessAction() );

            // �������������������ǼӰࡢ���
            if ( isLeaveAccessAction || isOTAccessAction )
            {
               workflowDefineRequirementsVOs = workflowDefineDTO.getWorkflowDefineRequirementsVOs();
            }

            // �����������Ϊ��
            if ( workflowDefineRequirementsVOs == null || workflowDefineRequirementsVOs.size() == 0 )
            {
               workflowDefineDTOTemp = workflowDefineDTO;
               break;
            }
            else
            {
               // ��ȡWorkflowModuleDTO
               final WorkflowModuleDTO workflowModuleDTO = accountConstants.getWorkflowModuleDTOByWorkflowModuleId( workflowDefineDTO.getWorkflowDefineVO().getWorkflowModuleId() );

               if ( workflowModuleDTO != null )
               {
                  // ��ȡAccountModuleDTO
                  final AccountModuleDTO accountModuleDTO = accountConstants.getAccountModuleDTOByModuleId( workflowModuleDTO.getWorkflowModuleVO().getModuleId() );

                  if ( accountModuleDTO != null )
                  {
                     // ��ȡaccessAction ������{HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT,HRO_BIZ_EMPLOYEE_LABOR_CONTRACT_IN_HOUSE,HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE} ��ʽ
                     final String accessAction = accountModuleDTO.getModuleVO().getAccessAction();

                     final List< String > accessActions = KANUtil.jasonArrayToStringList( accessAction );

                     // ��ȡTableDTO
                     final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessActions, baseVO.getRole() );

                     // �ҵ���������
                     boolean isMatch = isMatchDefineDTO( tableDTO, workflowDefineRequirementsVOs, baseVO );

                     if ( isMatch )
                     {
                        workflowDefineDTOTemp = workflowDefineDTO;
                        break;
                     }
                  }
               }
            }
         }
      }

      return workflowDefineDTOTemp;
   }

   // TODO
   // ���������������Ƿ�����
   private boolean isMatchDefineDTO( final TableDTO tableDTO, final List< WorkflowDefineRequirementsVO > workflowDefineRequirementsVOs, final BaseVO baseVO )
   {
      boolean flag = true;
      boolean isLeaveAccessAction = isLeaveAccessAction( baseVO.getHistoryVO().getAccessAction() );
      boolean isOTAccessAction = isOTAccessAction( baseVO.getHistoryVO().getAccessAction() );
      for ( WorkflowDefineRequirementsVO workflowDefineRequirementsVO : workflowDefineRequirementsVOs )
      {
         try
         {
            double columnValue = 0;
            if ( isLeaveAccessAction )
            {
               LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) baseVO;
               double estimateLegalHours = 0, estimateBenefitHours = 0;
               if ( leaveHeaderVO.getEstimateLegalHours() != null )
               {
                  estimateLegalHours = Double.parseDouble( leaveHeaderVO.getEstimateLegalHours() );
               }
               if ( leaveHeaderVO.getEstimateBenefitHours() != null )
               {
                  estimateBenefitHours = Double.parseDouble( leaveHeaderVO.getEstimateBenefitHours() );
               }
               columnValue = estimateLegalHours + estimateBenefitHours;
            }
            if ( isOTAccessAction )
            {
               OTHeaderVO otHeaderVO = ( OTHeaderVO ) baseVO;
               if ( otHeaderVO.getStatus().equals( "3" ) )
               {
                  columnValue = Double.parseDouble( otHeaderVO.getActualHours() );
               }
               else
               {
                  columnValue = Double.parseDouble( otHeaderVO.getEstimateHours() );
               }
            }
            double compareValue = Double.parseDouble( workflowDefineRequirementsVO.getCompareValue() );

            // �Ƚ�����  1:����
            if ( "1".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue == compareValue );

            }
            // �Ƚ�����  ##2:����
            else if ( "2".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue > compareValue );
            }
            // �Ƚ�����  ##3:С��
            else if ( "3".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue < compareValue );
            }
            // �Ƚ�����  ##4:���ڵ���
            else if ( "4".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue >= compareValue );
            }
            // �Ƚ�����   ##5:С�ڵ���
            else if ( "5".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue <= compareValue );
            }
         }
         catch ( Exception e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

      }
      return flag;
   }

   /***
    * �ѵڶ������Զ��岽��ϲ�����һ����
   * 	mergeDefineStepsVO
   *	
   *	@param acutalDefineSteps
   *	@param defineStepsVO
    */
   private void mergeDefineStepsVO( WorkflowDefineStepsVO acutalDefineSteps, WorkflowDefineStepsVO defineStepsVO )
   {
      if ( "1".equals( defineStepsVO.getStepType() ) || "3".equals( defineStepsVO.getStepType() ) )
      {
         acutalDefineSteps.setStepType( defineStepsVO.getStepType() );
      }
      acutalDefineSteps.setSendEmail( defineStepsVO.getSendEmail() );
      acutalDefineSteps.setSendSMS( defineStepsVO.getSendSMS() );
      acutalDefineSteps.setSendInfo( defineStepsVO.getSendInfo() );
      acutalDefineSteps.setEmailTemplateId( defineStepsVO.getEmailTemplateId() );
      acutalDefineSteps.setSmsTemplateId( defineStepsVO.getSmsTemplateId() );
      acutalDefineSteps.setInfoTemplateId( defineStepsVO.getInfoTemplateId() );
   }

   public static boolean isLeaveAccessAction( final String accessAction )
   {
      boolean flag = false;
      String leaveActions[] = { "HRO_BIZ_ATTENDANCE_LEAVE_HEADER" };
      if ( accessAction != null )
      {
         for ( String action : leaveActions )
         {
            if ( action.equalsIgnoreCase( accessAction ) )
            {
               flag = true;
            }
         }
      }
      return flag;
   }

   public static boolean isOTAccessAction( final String accessAction )
   {
      boolean flag = false;
      String leaveActions[] = { "HRO_BIZ_ATTENDANCE_OT_HEADER" };
      for ( String action : leaveActions )
      {
         if ( action.equalsIgnoreCase( accessAction ) )
         {
            flag = true;
         }
      }
      return flag;
   }

}
