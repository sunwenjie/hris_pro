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
* 项目名称：HRO_V1  
* 类名称：WorkflowService  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2014-3-5 下午07:47:25  
* 修改人：Jixiang  
* 修改时间：2014-3-5 下午07:47:25  
* 修改备注：  
* @version   
*   
*/
public class WorkflowService extends ContextService
{
   // 注入 WorkflowActualDao
   private WorkflowActualDao workflowActualDao;

   // 注入WorkflowActualStepsDao
   private WorkflowActualStepsDao workflowActualStepsDao;

   // 注入HistoryDao
   private HistoryDao historyDao;

   // 注入EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // 注入ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入StaffDao
   private StaffDao staffDao;

   // 注入EmployeeDao
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
   *  isApproveObject 是否是审批通过后的对象
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
   *  getWorkflowDefineDTO 匹配account定义的工作流
   *  
   *  @param moduleId   模块ID
   *  @param rightId    权限ID
   *  @param corpId     corpId 
   *  @return
    */
   private List< WorkflowDefineDTO > getWorkflowDefineDTO( final KANAccountConstants accountConstants, final String moduleId, final String rightId, final String corpId )
   {
      // 初始化WorkflowDefineDTO列表
      final List< WorkflowDefineDTO > workflowDefineDTOTemps = new ArrayList< WorkflowDefineDTO >();

      // 遍历常量中的WorkflowModuleDTO
      for ( WorkflowModuleDTO workflowModuleDTO : accountConstants.WORKFLOW_MODULE_DTO )
      {
         // 当前模块moduleId等于WORKFLOW_MODULE_DTO中WorkflowModuleVO的moduleId
         if ( workflowModuleDTO.getWorkflowModuleVO() != null && moduleId != null && moduleId.equals( workflowModuleDTO.getWorkflowModuleVO().getModuleId() ) )
         {
            // 遍历WorkflowDefineDTO列表，找到合适的WorkflowDefineDTO
            if ( workflowModuleDTO.getWorkflowDefineDTO() != null && workflowModuleDTO.getWorkflowDefineDTO().size() > 0 )
            {
               for ( WorkflowDefineDTO workflowDefineDTO : workflowModuleDTO.getWorkflowDefineDTO() )
               {
                  if ( workflowDefineDTO.getWorkflowDefineVO() != null
                        && "1".equals( workflowDefineDTO.getWorkflowDefineVO().getStatus() )
                        && ( ( corpId == null && workflowDefineDTO.getWorkflowDefineVO().getCorpId() == null ) || ( corpId != null
                              && workflowDefineDTO.getWorkflowDefineVO().getCorpId() != null && corpId.equals( workflowDefineDTO.getWorkflowDefineVO().getCorpId() ) ) ) )
                  {
                     // 当前模块的rightId在workflowDefineVO中有定义
                     if ( KANUtil.hasContain( workflowDefineDTO.getWorkflowDefineVO().getRightIdsArray(), rightId ) )
                     {
                        //工作流定义临时变量
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
    * 获得有效的工作流
   *  getValidWorkflowDefineDTO
   *  
   *  @param workflowDefineDTOTemp
   *  @param accountConstants
   *  @param baseVO
   *  @return
    */
   private WorkflowActualDTO getValidWorkflowActualDTO( final WorkflowDefineDTO workflowDefineDTOTemp, final KANAccountConstants accountConstants, final BaseVO baseVO )
   {
      // 工作流定义是否为空
      if ( workflowDefineDTOTemp != null )
      {
         // 初始化 WorkflowActualDTO
         WorkflowActualDTO workflowActualDTO = null;

         // 获取WorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = workflowDefineDTOTemp.getWorkflowDefineVO();

         // 作用范围
         int scope = workflowDefineVO.getScope();

         // 流程审批类型
         String approvalType = workflowDefineVO.getApprovalType();

         // 职位内部
         if ( scope == 1 )
         {
            // 如果是自定义
            if ( "1".equals( approvalType ) )
            {
               // 排序工作流审批顺序
               sortStepIndex( workflowDefineDTOTemp.getWorkflowDefineStepsVOs() );
               workflowActualDTO = createWorkflowActualDTO( workflowDefineDTOTemp, baseVO );
            }
            // 如果是基于组织架构 - 从操作人开始
            else if ( "2".equals( approvalType ) )
            {
               // 获取操作人的positionId
               final String positionId = baseVO.getHistoryVO().getPositionId();

               // 计算工作流步骤
               final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, positionId );
               workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
            }
            // 如果是基于组织架构 - 从对接人开始
            else if ( "3".equals( approvalType ) )
            {
               // 获得  所属部门所输入  按组织架构计算
               String ownerPositionId = null;
               try
               {
                  ownerPositionId = BeanUtils.getProperty( baseVO.getHistoryVO(), "owner" );
               }
               catch ( Exception e )
               {
                  e.printStackTrace();
               }
               // 有owner 并且owner 不为空 
               if ( ownerPositionId != null && !ownerPositionId.trim().isEmpty() )
               {
                  final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, ownerPositionId );
                  workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
               }
               // 如果对象没有所属人的  则  按基于组织架构-从操作人开始 计算  
               else
               {
                  String positionId = baseVO.getHistoryVO().getPositionId();
                  final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, positionId );
                  workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
               }

            }
            // 基于组织架构-从申请人开始
            else if ( "4".equals( approvalType ) )
            {

               String positionId = null;
               try
               {
                  // 通过反射获得employeeId
                  final String employeeId = BeanUtils.getProperty( baseVO, "employeeId" );
                  // 再查询得到staffId
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
                              // 找到一个所属人职位而非代理人职位
                              if ( "1".equals( positionStaffRelationVO.getStaffType() ) )
                              {
                                 positionId = positionStaffRelationVO.getPositionId();
                                 break;
                              }
                           }
                           // 没有找到所属职位，则选择第一个默认代理职位
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
                  logger.error( "审核对象构造函数调用错误！" );
                  e.printStackTrace();
               }
               catch ( NoSuchMethodException e )
               {
                  logger.error( "审核对象没有employeeId属性！" );
                  e.printStackTrace();
               }
               catch ( KANException e )
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

               // 再得到staffId 的positionId( 可能是多个positionId, 那么只考虑staff所属职位，不考虑代理职位， 如果还有多个positionId,这默认选第一个)

               final WorkflowDefineDTO workflowDefineDTO = calculateWorkflowSteps( accountConstants, workflowDefineDTOTemp, positionId );
               workflowActualDTO = createWorkflowActualDTO( workflowDefineDTO, baseVO );
            }
         }
         // 外部
         else if ( scope == 2 )
         {
            // 初始化WorkflowActualDTO
            workflowActualDTO = new WorkflowActualDTO();

            // 初始化WorkflowActualStepsVO列表
            // final List< WorkflowActualStepsVO > workflowActualStepsVOs = workflowActualDTO.getWorkflowActualStepsVOs();

            try
            {
               // 获取对象中的“contractId”
               final String contractId = BeanUtils.getProperty( baseVO, "contractId" );

               // baseVO对象存在“contractId”
               if ( KANUtil.filterEmpty( contractId ) != null )
               {
                  // 获取EmployeeContractVO
                  final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( contractId );

                  if ( employeeContractVO != null )
                  {
                     // 派送信息 - 考勤审核方式
                     String approveType = employeeContractVO.getApproveType();

                     // 派送信息 - 直线经理
                     final String lineManagerId = employeeContractVO.getLineManagerId();

                     // 如果派送信息按照订单设定
                     if ( KANUtil.filterEmpty( approveType, "0" ) == null )
                     {
                        // 获取服务订单ClientOrderHeaderVO
                        final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
                        if ( clientOrderHeaderVO != null )
                        {
                           approveType = clientOrderHeaderVO.getApproveType();
                        }
                     }

                     // 审核方式 - 职位设定
                     if ( "1".equals( approveType ) )
                     {
                        // 获取审核对象positionId
                        final String employeePostionId = employeeContractVO.getPositionId();

                        // 获取审批步骤数
                        final int steps = workflowDefineVO.getSteps();

                        // 初始化职级权重
                        double topPositionGradeWeight = Double.MAX_VALUE;

                        // 1，按外部组织架构计算审核步骤
                        // final WorkflowDefineDTO workflowDefineDTO = getWorkflowDefineDTOByOutOrganization( accountConstants, workflowDefineDTOTemp, employeePostionId );

                        // 获取最高审批职级
                        final com.kan.base.domain.management.PositionGradeVO positionGrade = accountConstants.getEmployeePositionGradeVOByPositionGradeId( workflowDefineVO.getTopPositionGrade() );

                        if ( positionGrade != null )
                        {
                           topPositionGradeWeight = Double.parseDouble( positionGrade.getWeight() );
                        }

                        // 计算WorkflowDefineStepsVO
                        final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs = getWorkflowDefineStepsVOsByOutOrganization( accountConstants, workflowDefineVO, employeePostionId, topPositionGradeWeight, steps );

                        // 1、基于组织架构计算出来事件审核列表
                        for ( int i = 0; i < organizationWorkflowDefineStepsVOs.size(); i++ )
                        {
                           // 初始化WorkflowDefineStepsVO
                           final WorkflowDefineStepsVO workflowDefineStepsVO = organizationWorkflowDefineStepsVOs.get( i );

                           // 获取EmployeeVO列表
                           final List< Object > employeeVOs = this.getEmployeeDao().getEmployeeVOsByPositionId( workflowDefineStepsVO.getPositionId() );

                           // 外部职位没有被EmployeeVO拥有
                           if ( employeeVOs == null || employeeVOs.size() == 0 )
                           {
                              continue;
                           }

                           final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                           tempWorkflowActualStepsVO.setStepIndex( i + 1 );
                           // 职位外部
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

                        // 2、最后审批人加上直线经理
                        if ( KANUtil.filterEmpty( lineManagerId, "0" ) != null )
                        {
                           final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                           tempWorkflowActualStepsVO.setStepIndex( workflowActualDTO.getWorkflowActualStepsVOs().size() + 1 );
                           // 客户联系人
                           tempWorkflowActualStepsVO.setAuditType( "3" );
                           // 派送信息直线经理 
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
                     // 审核方式 - 按直线经理
                     else if ( "2".equals( approveType ) )
                     {
                        // 直接加上审核人
                        if ( KANUtil.filterEmpty( lineManagerId, "0" ) != null )
                        {
                           final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                           tempWorkflowActualStepsVO.setStepIndex( 1 );
                           // 客户联系人
                           tempWorkflowActualStepsVO.setAuditType( "3" );
                           // 派送信息直线经理 
                           tempWorkflowActualStepsVO.setAuditTargetId( lineManagerId );
                           tempWorkflowActualStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                           tempWorkflowActualStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                           tempWorkflowActualStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                           tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
                           tempWorkflowActualStepsVO.setCreateDate( new Date() );

                           workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
                        }
                        // 不存在直线经理
                        else
                        {
                           return null;
                        }
                     }
                     else
                     {
                        return null;
                     }

                     // 初始化派送信息对接人
                     String ownerPositionId = null;
                     try
                     {
                        ownerPositionId = employeeContractVO.getOwner();
                     }
                     catch ( Exception e )
                     {
                        e.printStackTrace();
                     }

                     // 派送信息存在对接人
                     if ( KANUtil.filterEmpty( ownerPositionId, "0" ) != null )
                     {
                        final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
                        // 职位内部
                        tempWorkflowActualStepsVO.setAuditType( "1" );
                        // 所属人
                        tempWorkflowActualStepsVO.setAuditTargetId( ownerPositionId );
                        tempWorkflowActualStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                        tempWorkflowActualStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                        tempWorkflowActualStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                        tempWorkflowActualStepsVO.setCreateBy( baseVO.getCreateBy() );
                        tempWorkflowActualStepsVO.setCreateDate( new Date() );

                        // 如果对接人先参与
                        if ( "1".equals( approvalType ) )
                        {
                           tempWorkflowActualStepsVO.setStepIndex( 0 );
                           workflowActualDTO.getWorkflowActualStepsVOs().add( 0, tempWorkflowActualStepsVO );
                        }
                        // 对接人后参与
                        else if ( "2".equals( approvalType ) )
                        {
                           tempWorkflowActualStepsVO.setStepIndex( workflowActualDTO.getWorkflowActualStepsVOs().size() + 1 );
                           workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
                        }
                        // 对接人不参与
                        else if ( "3".equals( approvalType ) )
                        {
                           // 不用管
                        }
                     }
                  }
                  else
                  {
                     logger.error( "没有查询到ID为" + contractId + "的服务协议！" );
                     throw new Exception();
                  }
               }
               else
               {
                  logger.error( "审核对象contractId属性为空！" );
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
               logger.error( "审核对象构造函数调用错误！" );
               e.printStackTrace();
            }
            catch ( NoSuchMethodException e )
            {
               logger.error( "审核对象没有contractId属性！" );
               e.printStackTrace();
            }
            catch ( Exception e )
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

            // 存在计算出WorkflowActualStepsVOs步骤
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
               // 设置状态为启动状态
               workflowActualDTO.getWorkflowActualVO().setStatus( "1" );
               workflowActualDTO.getWorkflowActualVO().setNameZH( workflowDefineVO.getNameZH() );
               workflowActualDTO.getWorkflowActualVO().setNameEN( workflowDefineVO.getNameEN() );
            }
         }

         if ( workflowActualDTO != null && workflowActualDTO.getWorkflowActualStepsVOs() != null && workflowActualDTO.getWorkflowActualStepsVOs().size() > 0 )
         {
            // 重置审核列表
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
    * 根据组织架构计算得到有效工作流步骤
   * 	getWorkflowDefineDTOByOrganization
   *	
   *	@param accountConstants
   *	@param workflowDefineDTOTemp
   *	@param positionId
   *	@return
    */
   private WorkflowDefineDTO calculateWorkflowSteps( final KANAccountConstants accountConstants, final WorkflowDefineDTO workflowDefineDTOTemp, final String positionId )
   {
      // 工作流定义
      WorkflowDefineVO workflowDefineVO = workflowDefineDTOTemp.getWorkflowDefineVO();

      // 初始化最后审批职级权重
      int topPositionGradeWeight = 0;

      // 最高审批职级
      final String topPositionGrade = workflowDefineVO.getTopPositionGrade();

      // 获取职级PositionGradeVO
      final PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( topPositionGrade );

      // 存在职级
      if ( positionGradeVO != null )
      {
         topPositionGradeWeight = Integer.parseInt( positionGradeVO.getWeight() );
      }
      else
      {
         // 不存在则无限大权重
         topPositionGradeWeight = Integer.MAX_VALUE;

         //没有查找到对应position对象
         System.out.println( "####没有查找到对应topPositionGrade=" + topPositionGrade + "对象" );
      }

      // 审批步骤数
      final int steps = workflowDefineVO.getSteps();

      // 计算基于组织架构审核步骤
      final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs = getWorkflowDefineStepsVOsByOrganization( accountConstants, workflowDefineVO, positionId, topPositionGradeWeight, steps );

      // 计算自定义审核步骤职级权重
      final List< WorkflowDefineStepsVO > customWorkflowDefineStepsVOs = putPositionWeight( accountConstants, workflowDefineDTOTemp.getWorkflowDefineStepsVOs() );

      // 按审核步骤排序
      sortStepIndex( customWorkflowDefineStepsVOs );

      final List< WorkflowDefineStepsVO > finalWorkflowDefineStepsVOs = calculateWorkflowDefineSteps( accountConstants.ACCOUNT_ID, organizationWorkflowDefineStepsVOs, customWorkflowDefineStepsVOs );

      workflowDefineDTOTemp.setWorkflowDefineStepsVOs( finalWorkflowDefineStepsVOs );

      return workflowDefineDTOTemp;
   }

   //   /**
   //    * 根据职位外部组织架构计算得到有效工作流
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
   //      // 工作流定义
   //      WorkflowDefineVO workflowDefineVO = workflowDefineDTOTemp.getWorkflowDefineVO();
   //
   //      //审批步骤数
   //      final int steps = workflowDefineVO.getSteps();
   //
   //      // 基于组织架构计算出来事件审核列表
   //      final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs = getWorkflowDefineStepsVOsByOutOrganization( accountConstants, workflowDefineVO, positionId, steps );
   //
   //      //final List< WorkflowDefineStepsVO > customWorkflowDefineStepsVOs = putOutPositionWeight( accountConstants, workflowDefineDTOTemp.getWorkflowDefineStepsVOs() );
   //      //            
   //      //final List< WorkflowDefineStepsVO > finalWorkflowDefineStepsVOs = figureOutActualWorkflowDefineSteps( organizationWorkflowDefineStepsVOs, customWorkflowDefineStepsVOs );
   //
   //      sortStepIndex( organizationWorkflowDefineStepsVOs );//排序
   //
   //      //workflowDefineDTOTemp.setWorkflowDefineStepsVOs( organizationWorkflowDefineStepsVOs );
   //
   //      return workflowDefineDTOTemp;
   //   }

   /**
    * 根据组织架构 计算审核步骤
   * 	getWorkflowDefineStepsVOsByOrganization
   *	
   *	@param accountConstants
   *	@param workflowDefineVO
   *	@param startPositionId        当前职位
   *	@param topPositionGradeWeight 最高审批职级权重
   *	@param maxSteps               审批步骤数 
   *	@return
    */
   // Code Review by siuvan at 2014-05-22 14:44:19
   private List< WorkflowDefineStepsVO > getWorkflowDefineStepsVOsByOrganization( final KANAccountConstants accountConstants, final WorkflowDefineVO workflowDefineVO,
         final String startPositionId, double topPositionGradeWeight, int maxSteps )
   {
      // 初始化返回值
      final List< WorkflowDefineStepsVO > actualWorkflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

      // 获取当前审批职位PositionDTO
      final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( startPositionId );

      if ( positionDTO != null && positionDTO.getPositionVO() != null )
      {
         // 获取当前职位的上级positionId
         String positionId = positionDTO.getPositionVO().getParentPositionId();

         if ( maxSteps == 0 && topPositionGradeWeight == 0 )
         {
            // return workflowDefineStepsVOs2; 
         }
         else if ( maxSteps == 0 )
         {
            // 如果审批步骤数，默认步骤数无限大
            maxSteps = Integer.MAX_VALUE;
         }
         else if ( topPositionGradeWeight == 0 )
         {
            // 如果职级权重为“0”，默认权重无限大
            topPositionGradeWeight = Double.MAX_VALUE;
         }

         final PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( positionId );
         if ( parentPositionVO != null && parentPositionVO.getParentPositionId().equals( "0" ) )
         {
            topPositionGradeWeight = Double.MAX_VALUE;
         }

         // 遍历装载审核步骤
         for ( int i = 0; i < maxSteps; i++ )
         {
            // 获取当前职位上级PositionDTO
            final PositionDTO parentPositionDTO = accountConstants.getPositionDTOByPositionId( positionId );

            // 找不到当前职位上级或者当前职位没有挂员工
            if ( parentPositionDTO == null || parentPositionDTO.getPositionStaffRelationVOs() == null || parentPositionDTO.getPositionStaffRelationVOs().size() == 0 )
            {
               break;
            }
            else
            {
               // 初始化PositionVO
               final PositionVO positionVO = parentPositionDTO.getPositionVO();

               // positionId 不是null
               if ( positionVO != null && KANUtil.filterEmpty( positionVO.getPositionId() ) != null )
               {
                  // 初始化权重 
                  double weightNum = 0;

                  // 存在职级
                  if ( KANUtil.filterEmpty( positionVO.getPositionGradeId() ) != null )
                  {
                     // 获取职级PositionGradeVO
                     final PositionGradeVO positionGrade = accountConstants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );

                     // 能在缓存中找到职级
                     if ( positionGrade != null )
                     {
                        String weight = positionGrade.getWeight();
                        weightNum = Double.parseDouble( weight );

                        // 大于最高审核职级的权重了 直接跳出
                        if ( weightNum > topPositionGradeWeight )
                        {
                           break;
                        }
                     }
                  }

                  // 把wfdsVO放入到临时的列表里面
                  final WorkflowDefineStepsVO wfdsVO = new WorkflowDefineStepsVO();
                  wfdsVO.setStepType( "1" );// 必须步骤
                  wfdsVO.setAuditType( "1" );// 按职位
                  wfdsVO.setPositionId( positionVO.getPositionId() );
                  wfdsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                  wfdsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                  wfdsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                  wfdsVO.setEmailTemplateId( workflowDefineVO.getEmailTemplateId() );
                  wfdsVO.setSmsTemplateId( workflowDefineVO.getSmsTemplateId() );
                  wfdsVO.setInfoTemplateId( workflowDefineVO.getInfoTemplateId() );
                  wfdsVO.setCreateDate( new Date() );
                  wfdsVO.setReferPositionGrade( positionVO.getPositionGradeId() );//设置职级
                  wfdsVO.setWeight( weightNum );//设置权重
                  wfdsVO.setStepIndex( i + 1 );
                  //###debug
                  wfdsVO.setTitleZH( positionVO.getTitleZH() );
                  wfdsVO.setTitleEN( positionVO.getTitleEN() );

                  actualWorkflowDefineStepsVOs.add( wfdsVO );
                  // 父职位id赋值给临时变量
                  positionId = positionVO.getParentPositionId();
                  // 如果没有父级的Position了则跳出循环查找
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
      System.out.println( "=====基于组织架构算出来的审核职位列表positionId:(weight)=========" );
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
    * 根据组织架构 计算审核步骤（外部职位）
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
      // 初始化WorkflowDefineStepsVO列表
      final List< WorkflowDefineStepsVO > workflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

      // 获取PositionDTO（外部）
      com.kan.base.domain.management.PositionDTO positionDTO = accountConstants.getEmployeePositionDTOByPositionId( startPositionId );

      if ( positionDTO != null && positionDTO.getPositionVO() != null )
      {
         // 上级positionId
         String parentPositionId = positionDTO.getPositionVO().getParentPositionId();

         // 遍历计算WorkflowDefineStepsVO
         for ( int i = 0; i < maxSteps; )
         {
            // 获取上级PoistionDTO
            positionDTO = accountConstants.getEmployeePositionDTOByPositionId( parentPositionId );

            if ( positionDTO != null )
            {
               if ( positionDTO.getPositionVO() != null && KANUtil.filterEmpty( positionDTO.getPositionVO().getPositionId() ) != null )
               {
                  // 获取PositionGradeVO（外部）
                  final com.kan.base.domain.management.PositionGradeVO positionGrade = accountConstants.getEmployeePositionGradeVOByPositionGradeId( positionDTO.getPositionVO().getPositionGradeId() );

                  if ( positionGrade != null && KANUtil.filterEmpty( positionGrade.getWeight() ) != null )
                  {
                     // 超过最高审核职级权重，跳出循环
                     if ( Double.parseDouble( positionGrade.getWeight() ) > topPositionGradeWeight )
                     {
                        break;
                     }
                  }

                  // 初始化临时WorkflowDefineStepsVO
                  final WorkflowDefineStepsVO tempWorkflowDefineStepsVO = new WorkflowDefineStepsVO();
                  tempWorkflowDefineStepsVO.setPositionId( positionDTO.getPositionVO().getPositionId() );
                  tempWorkflowDefineStepsVO.setSendEmail( workflowDefineVO.getSendEmail() );
                  tempWorkflowDefineStepsVO.setSendInfo( workflowDefineVO.getSendInfo() );
                  tempWorkflowDefineStepsVO.setSendSMS( workflowDefineVO.getSendSMS() );
                  tempWorkflowDefineStepsVO.setCreateDate( new Date() );
                  // 设置为待审核状态
                  tempWorkflowDefineStepsVO.setStatus( "1" );
                  // 折半插入法方便插入  两步之间最极端情况可插入4步   (2(8次方)=64 ； 8-2=6)
                  tempWorkflowDefineStepsVO.setStepIndex( ( i + 1 ) * 64 );
                  // debug
                  tempWorkflowDefineStepsVO.addSearchField( "titleZH", positionDTO.getPositionVO().getTitleZH() );

                  workflowDefineStepsVOs.add( tempWorkflowDefineStepsVO );

                  // 上级positionId重新赋值
                  parentPositionId = positionDTO.getPositionVO().getParentPositionId();

                  // 如果上级positionId不存在，则跳出循环查找
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
            // 如果找不到该positionId 对应的DTO则直接跳出循环查找
            else
            {
               break;
            }
         }
      }

      //####debug
      System.out.println( "=====基于外部组织架构算出来的审核职位列表positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{positionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + "} \n " );
      }
      System.out.println( "\n=====end=========" );

      return workflowDefineStepsVOs;
   }

   /**
    * 计算各个步骤审核职位的权限
   * 	addPositionWeight
   *	
   *	@param workflowDefineStepsVOs1
   *	@param accountConstants
   *	@return
    */
   private List< WorkflowDefineStepsVO > putPositionWeight( final KANAccountConstants accountConstants, final List< WorkflowDefineStepsVO > workflowDefineStepsVOs1 )
   {
      // 遍历，计算各个步骤positionId对应的weight权重
      for ( int i = 0; i < workflowDefineStepsVOs1.size(); i++ )
      {
         // 获取第“i”个步骤
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

      System.out.println( "=====数据库定义的 自定义步骤的审核职位列表positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs1 )
      {
         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{postionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + ","
               + ( workflowDefineStepsVO.getStepType().equals( "1" ) ? "必须   " : "非必须   " ) + "} \n " );
      }
      System.out.println( "\n=====end=========" );
      return workflowDefineStepsVOs1;
   }

   //   /**
   //    * 计算各个步骤外部审核职位的权限
   //    * 暂时没有用到
   //   *  addPositionWeight
   //   *  
   //   *  @param workflowDefineStepsVOs1
   //   *  @param accountConstants
   //   *  @return
   //    */
   //   private List< WorkflowDefineStepsVO > putOutPositionWeight( final KANAccountConstants accountConstants, List< WorkflowDefineStepsVO > workflowDefineStepsVOs1 )
   //   {
   //      // 遍历,计算各个步骤positionId对应的weight权重
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
   //      System.out.println( "=====数据库定义的 自定义步骤的外部职位审核职位列表positionId:(weight)=========" );
   //      for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs1 )
   //      {
   //         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{postionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
   //               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + ","
   //               + ( workflowDefineStepsVO.getStepType().equals( "1" ) ? "必须   " : "非必须   " ) + "} \n " );
   //      }
   //      System.out.println( "\n=====end=========" );
   //      return workflowDefineStepsVOs1;
   //   }

   /**
    * 结合自定义步骤和基于组织架构步骤  计算最终审核步骤  -----------mark
   * 	calculateWorkflowDefineSteps
   *	
   *  @param accountId
   *	@param organizationWorkflowDefineStepsVOs 基于组织架构步骤
   *	@param customWorkflowDefineStepsVOs 自定义步骤
    */
   private List< WorkflowDefineStepsVO > calculateWorkflowDefineSteps( final String accountId, final List< WorkflowDefineStepsVO > organizationWorkflowDefineStepsVOs,
         final List< WorkflowDefineStepsVO > customWorkflowDefineStepsVOs )
   {
      // 自定义步骤--对比计算得到最终结果
      final List< WorkflowDefineStepsVO > finalWorkflowDefineStepsVOs = new ArrayList< WorkflowDefineStepsVO >();

      // 基于架构最后审核人的positionId
      String lastPositionId = "";
      final String[] msgNoticeArray = new String[ 3 ];

      //####debug
      System.out.println( "=====自定义审核列表positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : customWorkflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getTitleZH() + ":{auditType=" + workflowDefineStepsVO.getAuditType() + ",stepIndex=" + workflowDefineStepsVO.getStepIndex()
               + ",positionId:" + workflowDefineStepsVO.getPositionId() + ",staffId:" + workflowDefineStepsVO.getStaffId() + ",joinType=" + workflowDefineStepsVO.getJoinType()
               + ",referPositionGrade=" + workflowDefineStepsVO.getReferPositionGrade() + ",referPositionId=" + workflowDefineStepsVO.getReferPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + "} \n " );
      }
      System.out.println( "\n=====end=========" );

      // 结合二者算出最终*****
      if ( customWorkflowDefineStepsVOs != null && customWorkflowDefineStepsVOs.size() > 0 )
      {
         // 基于组织架构步骤为0
         if ( organizationWorkflowDefineStepsVOs != null && organizationWorkflowDefineStepsVOs.size() == 0 )
         {
            // 按自定义顺序顺序的加入到最终的审核步骤中
            for ( WorkflowDefineStepsVO wfdsVO1 : customWorkflowDefineStepsVOs )
            {
               finalWorkflowDefineStepsVOs.add( wfdsVO1 );
            }
         }
         // 二者都不为零
         else
         {
            // 1.添加所有基于组织架构的步骤
            finalWorkflowDefineStepsVOs.addAll( organizationWorkflowDefineStepsVOs );

            final WorkflowDefineStepsVO lastWorkflowDefineStepsVO = organizationWorkflowDefineStepsVOs.get( organizationWorkflowDefineStepsVOs.size() - 1 );
            if ( lastWorkflowDefineStepsVO != null )
            {
               lastPositionId = lastWorkflowDefineStepsVO.getPositionId();

               msgNoticeArray[ 0 ] = lastWorkflowDefineStepsVO.getEmailTemplateId();
               msgNoticeArray[ 1 ] = lastWorkflowDefineStepsVO.getInfoTemplateId();
               msgNoticeArray[ 2 ] = lastWorkflowDefineStepsVO.getSmsTemplateId();
            }

            // 2.逐步合并自定义步骤
            for ( WorkflowDefineStepsVO customWorkflowDefineStepsVO : customWorkflowDefineStepsVOs )
            {
               if ( isDuplicateWorkflowDefineSteps( finalWorkflowDefineStepsVOs, customWorkflowDefineStepsVO, accountId ) )
                  calculateWorkflowDefineSteps( finalWorkflowDefineStepsVOs, customWorkflowDefineStepsVO );
            }
         }
      }
      // 自定义步骤为零
      else
      {
         // 1.添加所有基于组织架构的步骤
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

      // 特殊步骤
      final WorkflowDefineStepsVO specialWorkflowDefineStepsVO = getSpecialWorkflowDefineSteps( accountId, lastPositionId, msgNoticeArray );

      // 野村特殊步骤放置工作流最后一步
      if ( specialWorkflowDefineStepsVO != null )
      {
         finalWorkflowDefineStepsVOs.add( specialWorkflowDefineStepsVO );
      }

      //####debug
      System.out.println( "=====1最终结果审核职位列表positionId:(weight)=========" );
      for ( WorkflowDefineStepsVO workflowDefineStepsVO : finalWorkflowDefineStepsVOs )
      {
         System.out.print( workflowDefineStepsVO.getSearchField().get( "titleZH" ) + ":{positionId:" + workflowDefineStepsVO.getPositionId() + ",weight:"
               + workflowDefineStepsVO.getWeight() + ",setpIndex:" + workflowDefineStepsVO.getStepIndex() + "} \n " );
      }
      System.out.println( "\n=====end=========" );
      return finalWorkflowDefineStepsVOs;
   }

   // 野村特殊步骤
   // Added by siuvan 2015-04-14
   private WorkflowDefineStepsVO getSpecialWorkflowDefineSteps( final String accountId, final String lastPositionId, final String[] msgNoticeArray )
   {
      if ( KANUtil.filterEmpty( accountId ).equals( "100056" ) && KANUtil.filterEmpty( lastPositionId ) != null )
      {
         final PositionVO lastPositionVO = KANConstants.getKANAccountConstants( accountId ).getPositionVOByPositionId( lastPositionId );
         if ( lastPositionVO != null && KANUtil.filterEmpty( lastPositionVO.getTitleZH() ) != null && !KANUtil.filterEmpty( lastPositionVO.getTitleZH() ).contains( "部长" ) )
         {
            final PositionVO parentPositionVO = KANConstants.getKANAccountConstants( accountId ).getPositionVOByPositionId( lastPositionVO.getParentPositionId() );
            if ( parentPositionVO != null && KANUtil.filterEmpty( parentPositionVO.getTitleZH() ) != null && parentPositionVO.getTitleZH().contains( "部长" ) )
            {
               final WorkflowDefineStepsVO tempWorkflowDefineStepsVO = new WorkflowDefineStepsVO();
               tempWorkflowDefineStepsVO.setStepType( "3" );// 参考类型
               tempWorkflowDefineStepsVO.setAuditType( "1" );// 内部职位
               tempWorkflowDefineStepsVO.setPositionId( parentPositionVO.getPositionId() );// 部长PositionId
               tempWorkflowDefineStepsVO.setStepIndex( 99 );
               tempWorkflowDefineStepsVO.setSendEmail( "1" ); // 强制发邮件提醒
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

   // 跳过重复的自定义步骤
   private boolean isDuplicateWorkflowDefineSteps( final List< WorkflowDefineStepsVO > baseStepsVOs, final WorkflowDefineStepsVO defineStepsVO, final String accountId )
   {
      // 审核人类型1:内部职位4:内部员工5:对接人
      String auditType = defineStepsVO.getAuditType();
      // 步骤类型1:必须2:屏蔽3:参考
      String stepType = defineStepsVO.getStepType();
      // 职位ID
      String tempPositionId = defineStepsVO.getPositionId();
      // staffId
      String tempStaffId = defineStepsVO.getStaffId();

      // 如果自定义步骤是必须步骤
      if ( "1".equals( KANUtil.filterEmpty( stepType ) ) )
      {
         // 寻找组织架构步骤有无重复
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
    *   合并单步自定义步骤  -----------mark
    * calculateWorkflowDefineSteps
    *	
    *	@param baseStepsVOs 基于组织架构步骤 （已经是有序的步骤）
    *	@param defineStepsVO 自定义步骤
    */
   // Code Review by siuvan at 2014-05-22 14:57:07
   private List< WorkflowDefineStepsVO > calculateWorkflowDefineSteps( final List< WorkflowDefineStepsVO > baseStepsVOs, final WorkflowDefineStepsVO defineStepsVO )
   {
      // 如果基于组织架构步骤数为“0”，取自定义步骤
      if ( baseStepsVOs.size() == 0 )
      {
         baseStepsVOs.add( defineStepsVO );
      }
      else
      {
         int findIndex = -1;// 找到对应位置的下标
         int parallelStartIndex = -1;// 并行步骤开始下标
         int parallelEndIndex = -1;//并行步骤结束下标
         int equalsIndex = -1;// 找到相同审核步骤的下标
         int stepIndexTemp = -1;// 当前步骤Index

         // 遍历基于组织架构步骤
         for ( int i = 0; i < baseStepsVOs.size(); i++ )
         {
            WorkflowDefineStepsVO workflowDefineStepsVO = baseStepsVOs.get( i );
            // 和上一个步骤Index不同
            if ( stepIndexTemp != workflowDefineStepsVO.getStepIndex() )
            {
               // 找到对应的位置后就不向后寻找索引了，上次搜索所得的并行步骤索引即为找到步骤对应的并行步骤的起始索引
               if ( findIndex == -1 )
               {
                  stepIndexTemp = workflowDefineStepsVO.getStepIndex();
                  parallelStartIndex = i;
                  parallelEndIndex = i;
               }
            }
            // 和上一个步骤Index相同
            else
            {
               //    还是同一并行步骤时 parallelEndIndex才往后
               if ( stepIndexTemp == baseStepsVOs.get( parallelStartIndex ).getStepIndex() )
               {
                  parallelEndIndex = i;
               }
            }

            // 参与方式 
            String joinType = defineStepsVO.getJoinType();
            // 1:按职位
            if ( "1".equals( joinType ) )
            {
               // 遍历找到合适的职位
               if ( workflowDefineStepsVO.getPositionId() != null && workflowDefineStepsVO.getPositionId().equals( defineStepsVO.getReferPositionId() ) )
               {
                  // 只要第一个
                  if ( findIndex == -1 )
                  {
                     findIndex = i;
                  }
               }
            }
            // 2:按职级 
            else if ( "2".endsWith( joinType ) )
            {
               //找到合适的职级，插进去         
               if ( workflowDefineStepsVO.getReferPositionGrade() != null && workflowDefineStepsVO.getReferPositionGrade().equals( defineStepsVO.getReferPositionGrade() ) )
               {
                  // 只要第一个
                  if ( findIndex == -1 )
                  {
                     findIndex = i;
                  }
               }
            }
            else
            {

            }
            // 审批类型 审核类型相同
            if ( workflowDefineStepsVO.getAuditType() != null && workflowDefineStepsVO.getAuditType().equals( defineStepsVO.getAuditType() ) && equalsIndex == -1 )
            {
               // 1内部职位ID  且审核职位相同
               if ( "1".equals( workflowDefineStepsVO.getAuditType() ) && workflowDefineStepsVO.getPositionId() != null
                     && workflowDefineStepsVO.getPositionId().equals( defineStepsVO.getPositionId() ) )
               {
                  equalsIndex = i;
               }
               // 4:内部员工Id 且审核人staffId相同
               else if ( "4".equals( workflowDefineStepsVO.getAuditType() ) && workflowDefineStepsVO.getPositionId() != null
                     && workflowDefineStepsVO.getPositionId().equals( defineStepsVO.getPositionId() ) )
               {
                  equalsIndex = i;
               }
            }

         }
         // 参考职位是自己本身的不参与合并
         if ( equalsIndex == findIndex && equalsIndex != -1 )
         {
            return baseStepsVOs;
         }

         /** 开始合并步骤 **/
         // 参与顺序
         String joinOrderType = defineStepsVO.getJoinOrderType();
         // 步骤类型
         String stepType = defineStepsVO.getStepType();

         // 找到了对应的参考步骤
         if ( findIndex != -1 )
         {
            // 已存在则先删掉
            if ( equalsIndex != -1 )
            {
               // 自定义为参考步骤，但实际步骤为1必须步骤则 该步骤还是必须步骤 ------mark
               // TODO 这个还不是很确定，可能会该
               if ( "3".equals( stepType ) && "1".equals( baseStepsVOs.get( equalsIndex ).getStepType() ) )
               {
                  defineStepsVO.setStepType( "1" );
               }
               // equalsIndex 前后有没有和equalsIndex并行的步骤
               boolean hasParallel = false;
               int listSize = baseStepsVOs.size();
               // equalsIndex 前面有元素 ， 看看 前面一步是否是并行步骤
               if ( equalsIndex - 1 >= 0 )
               {
                  hasParallel = baseStepsVOs.get( equalsIndex ).getStepIndex() == baseStepsVOs.get( equalsIndex - 1 ).getStepIndex();
               }
               if ( !hasParallel && equalsIndex >= 0 && equalsIndex + 1 < listSize )
               {
                  hasParallel = baseStepsVOs.get( equalsIndex ).getStepIndex() == baseStepsVOs.get( equalsIndex + 1 ).getStepIndex();
               }

               baseStepsVOs.remove( equalsIndex );
               // 如果不存在并行的步骤，删除后面的stepIndex自动减1
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
            // 1:必须步骤（工作流中必须含有）3:参考步骤（只发通知不参与审批）
            if ( "1".equals( stepType ) || "3".equals( stepType ) )
            {
               // 1:并行
               if ( "1".equals( joinOrderType ) )
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() );
                  // 插入到parallelEndIndex后面，
                  baseStepsVOs.add( parallelEndIndex + 1, defineStepsVO );

               }// 2:审核前
               else if ( "2".equals( joinOrderType ) )
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() );
                  // parallelStartIndex前面的步骤StepIndex加1 然后  插入到parallelStartIndex前面，
                  for ( int j = parallelStartIndex; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  baseStepsVOs.add( parallelStartIndex, defineStepsVO );

               }// 3:审核后
               else if ( "3".equals( joinOrderType ) )
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() + 1 );
                  // parallelStartIndex后面的步骤StepIndex加1 然后  插入到parallelStartIndex前面，
                  for ( int j = parallelStartIndex + 1; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  // 插入到parallelEndIndex后面，
                  baseStepsVOs.add( parallelEndIndex + 1, defineStepsVO );
               }
               // 默认审核后
               else
               {
                  defineStepsVO.setStepIndex( findDefineStepsVO.getStepIndex() + 1 );
                  // parallelStartIndex后面的步骤StepIndex加1 然后  插入到parallelStartIndex前面，
                  for ( int j = parallelStartIndex + 1; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  // 插入到parallelEndIndex后面，
                  baseStepsVOs.add( parallelEndIndex + 1, defineStepsVO );
               }

            }
            // 2:屏蔽步骤（工作流中含有则进行覆盖）
            else if ( "2".equals( stepType ) )
            {
               // 不存在这一步则忽略
               if ( equalsIndex != -1 )
               {

               }
               // 存在则覆盖消息提醒等设置
               else
               {
                  mergeDefineStepsVO( baseStepsVOs.get( equalsIndex ), defineStepsVO );
               }
            }
            else
            {
               //没写步骤类型
            }
         }
         // 没找到参考**则加入到最后面
         else
         {
            // 已存则覆盖
            if ( equalsIndex != -1 )
            {
               mergeDefineStepsVO( baseStepsVOs.get( equalsIndex ), defineStepsVO );
            }
            else
            {
               // 1:并行     
               if ( "1".equals( joinOrderType ) )
               {
                  // TODO 不确定可能会该
                  // 并行到第一步
                  defineStepsVO.setStepIndex( 1 );
                  // 合并到已经计算出的步骤中
                  baseStepsVOs.add( 0, defineStepsVO );
               }
               // 2:审核前
               else if ( "2".equals( joinOrderType ) )
               {
                  // TODO 不确定可能会该
                  // 放在整个工作流最前面
                  defineStepsVO.setStepIndex( 1 );
                  for ( int j = 0; j < baseStepsVOs.size(); j++ )
                  {
                     baseStepsVOs.get( j ).stepIndexAddOne();
                  }
                  baseStepsVOs.add( 0, defineStepsVO );

               }
               // 3:审核后
               else if ( "3".equals( joinOrderType ) )
               {
                  // TODO 不确定可能会该
                  // 放在整个工作流最后面
                  defineStepsVO.setStepIndex( baseStepsVOs.get( baseStepsVOs.size() - 1 ).getStepIndex() + 1 );
                  baseStepsVOs.add( defineStepsVO );
               }
               // 0:默认为按权重时，按照权重相等则并行，权重不等则串行 --mark---不能考虑， 因为可能审核顺序对应的权重已经不是有序的了
               else
               {
                  // 如果没填则按照审核后
                  defineStepsVO.setStepIndex( baseStepsVOs.get( baseStepsVOs.size() - 1 ).getStepIndex() + 1 );
                  baseStepsVOs.add( defineStepsVO );
               }
            }
         }
      }

      return baseStepsVOs;
   }

   /**
    * 排序
   * 	sortStepIndex
   *	
   *	@param workflowDefineStepsVOs
    */
   private void sortStepIndex( final List< WorkflowDefineStepsVO > workflowDefineStepsVOs )
   {
      // 冒泡排序
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
    * 重置 stepIndex和审核状态 --- mark
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
                  // 全票通过
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

         // 如果是第一步则设置审核状态为 2,审核中状态
         if ( actualIndex == startIndex )
         {
            // 参考步骤直接过
            if ( "3".equals( workflowActualStepsVO.getStepType() ) )
            {
               // 已通知
               workflowActualStepsVO.setStatus( "5" );
            }
            else
            {
               workflowActualStepsVO.setStatus( "2" );
            }
         }
         // 否则 设置审核状态为 1,待操作状态
         else
         {
            workflowActualStepsVO.setStatus( "3".equals( workflowActualStepsVO.getStepType() ) ? "6" : "1" );
         }
      }

      //####debug
      System.out.println( "=====2重置审核列表StepsId ：positionId:(weight)=========" );
      for ( WorkflowActualStepsVO workflowActualStepsVO : workflowActualStepsVOs )
      {
         System.out.print( workflowActualStepsVO.getSearchField().get( "titleZH" ) + ":{AuditTargetId:" + workflowActualStepsVO.getAuditTargetId() + ",AuditType:"
               + workflowActualStepsVO.getAuditType() + ",setpIndex:" + workflowActualStepsVO.getStepIndex() + ",status=" + workflowActualStepsVO.getStatus() + "} \n " );
      }
      System.out.println( "\n=====end=========" );
   }

   /**
    * 根据职位内部定义创建实际工作流
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
         // 初始化WorkflowActualDTO
         final WorkflowActualDTO workflowActualDTO = new WorkflowActualDTO();

         // 获取WorkflowDefineVO
         final WorkflowDefineVO workflowDefineVO = validWorkflowDefineDTO.getWorkflowDefineVO();

         // 存在WorkflowDefineStepsVOs工作流步骤
         if ( workflowDefineVO != null && validWorkflowDefineDTO.getWorkflowDefineStepsVOs() != null && validWorkflowDefineDTO.getWorkflowDefineStepsVOs().size() > 0 )
         {
            // 初始化WorkflowActualVO
            final WorkflowActualVO tempWorkflowActualVO = new WorkflowActualVO();
            // 如果是IN_HOUSE
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

            // 计算工作流实际步骤
            for ( int i = 0; i < validWorkflowDefineDTO.getWorkflowDefineStepsVOs().size(); i++ )
            {
               // 初始化WorkflowDefineStepsVO
               final WorkflowDefineStepsVO workflowDefineStepsVO = validWorkflowDefineDTO.getWorkflowDefineStepsVOs().get( i );

               // 初始化WorkflowActualStepsVO
               final WorkflowActualStepsVO tempWorkflowActualStepsVO = new WorkflowActualStepsVO();
               tempWorkflowActualStepsVO.setStepType( workflowDefineStepsVO.getStepType() );
               tempWorkflowActualStepsVO.setAuditTargetId( workflowDefineStepsVO.getPositionId() );

               // 作用范围  - 内部
               if ( workflowDefineVO.getScope() == 1 )
               {
                  // 内部职位
                  tempWorkflowActualStepsVO.setAuditType( "1" );

                  // 如果审批方式是内部员工
                  if ( "4".equals( workflowDefineStepsVO.getAuditType() ) )
                  {
                     // 内部员工
                     tempWorkflowActualStepsVO.setAuditType( "4" );
                     tempWorkflowActualStepsVO.setAuditTargetId( workflowDefineStepsVO.getStaffId() );
                  }
                  // 如果审批方式是对接人
                  else if ( "5".equals( workflowDefineStepsVO.getAuditType() ) )
                  {
                     tempWorkflowActualStepsVO.setAuditTargetId( baseVO.getHistoryVO().getOwner() );
                  }
               }
               // 作用范围  - 外部
               else if ( workflowDefineVO.getScope() == 2 )
               {
                  tempWorkflowActualStepsVO.setAuditType( "2" );
               }

               // 如果是内部职位 检查职位上有无UserVO
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

                     // 重置计算
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
               // 设置审核状态
               tempWorkflowActualStepsVO.setStatus( "1" );
               workflowActualDTO.getWorkflowActualStepsVOs().add( tempWorkflowActualStepsVO );
            }

            return workflowActualDTO;
         }
      }

      return null;
   }

   /***
    * 将计算出来的额工作流保存到数据库
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
            //  执行工作流
            try
            {
               //开启事物
               startTransaction();
               // #########nameZH 和  nameEN ?????
               final String historyNameZH = baseVO.getHistoryVO().getNameZH();
               final String historyNameEN = baseVO.getHistoryVO().getNameEN();
               workflowActualVO.setNameZH( workflowActualVO.getNameZH() + ( historyNameZH == null ? "" : "(" + historyNameZH + ")" ) );
               workflowActualVO.setNameEN( workflowActualVO.getNameEN() + ( historyNameEN == null ? "" : "(" + historyNameEN + ")" ) );
               workflowActualDao.insertWorkflowActual( workflowActualVO );
               final String workflowId = workflowActualVO.getWorkflowId();

               //2. 将baseVO 保存到BaseHistory表中 同时得到id
               baseVO.getHistoryVO().setWorkflowId( workflowId );

               //3. 保存History
               saveObjectHistory( baseVO );

               //4.按基于组织架构计算审核步骤
               for ( int i = 0; i < workflowActualStepsVOs2.size(); i++ )
               {
                  final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsVOs2.get( i );
                  workflowActualStepsVO.setWorkflowId( workflowId );
                  // 产生一个随机码用来验证邮件审批
                  final String randomKey = UUID.randomUUID().toString().trim().replaceAll( "-", "" );
                  workflowActualStepsVO.setRandomKey( randomKey );
                  workflowActualStepsDao.insertWorkflowActualSteps( workflowActualStepsVO );
                  if ( "2".equals( workflowActualStepsVO.getStatus() ) || "5".equals( workflowActualStepsVO.getStatus() ) )
                  {
                     workflowActualStepsVO.setHistoryVO( baseVO.getHistoryVO() );
                     KANSendMessageUtil.newInstance().sendMessageForWorkflow( workflowActualVO, workflowActualStepsVO );
                  }
               }

               //提交事物
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
            //  执行工作流
            try
            {
               final String historyNameZH = baseVO.getHistoryVO().getNameZH();
               final String historyNameEN = baseVO.getHistoryVO().getNameEN();
               workflowActualVO.setNameZH( workflowActualVO.getNameZH() + ( historyNameZH == null ? "" : "(" + historyNameZH + ")" ) );
               workflowActualVO.setNameEN( workflowActualVO.getNameEN() + ( historyNameEN == null ? "" : "(" + historyNameEN + ")" ) );
               workflowActualDao.insertWorkflowActual( workflowActualVO );
               final String workflowId = workflowActualVO.getWorkflowId();

               //2. 将baseVO 保存到BaseHistory表中 同时得到id
               baseVO.getHistoryVO().setWorkflowId( workflowId );

               //3. 保存History
               saveObjectHistory( baseVO );

               //4.按基于组织架构计算审核步骤
               for ( int i = 0; i < workflowActualStepsVOs2.size(); i++ )
               {
                  final WorkflowActualStepsVO workflowActualStepsVO = workflowActualStepsVOs2.get( i );
                  workflowActualStepsVO.setWorkflowId( workflowId );
                  // 产生一个随机码用来验证邮件审批
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
    * 保存对象到history
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
    * 获得最终的审核步骤
   *  getValidWorkflowDefineDTO
   *  
   *  @param baseVO  审批对象
   *  @return
    */
   public WorkflowActualDTO getValidWorkflowActualDTO( final BaseVO baseVO )
   {
      //判断是不是审批通过后的对象
      if ( isPassObject( baseVO ) )
         return null;

      // 得到KANAccountConstant的WORKFLOW_MODULE_DTO
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( baseVO.getAccountId() );

      // 获得WorkflowDefineDTO列表
      final List< WorkflowDefineDTO > workflowDefineDTOTemps = getWorkflowDefineDTO( accountConstants, baseVO.getHistoryVO().getModuleId(), baseVO.getHistoryVO().getRightId(), baseVO.getCorpId() );

      // 获得WorkflowDefineDTO
      final WorkflowDefineDTO workflowDefineDTOTemp = searchMatchDefineDTO( accountConstants, workflowDefineDTOTemps, baseVO );

      if ( workflowDefineDTOTemp != null )
      {
         // 获得有效的工作流
         final WorkflowActualDTO validWorkflowActualDTO = getValidWorkflowActualDTO( workflowDefineDTOTemp, accountConstants, baseVO );

         return checkWorkflowactualSteps( validWorkflowActualDTO );

      }

      return null;
   }

   // 检查WorkflowactualDTO中的AcutalStepsVOs是否有必须步骤
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
   * 根据自定义条件获得最匹配的workflowDefineDTO
   *	
   *	@param accountConstants 
   *	@param workflowDefineDTOTemps 
   *	@param baseVO 审批对象
   *	@return
    */
   private WorkflowDefineDTO searchMatchDefineDTO( final KANAccountConstants accountConstants, final List< WorkflowDefineDTO > workflowDefineDTOs, final BaseVO baseVO )
   {
      WorkflowDefineDTO workflowDefineDTOTemp = null;

      // 存在工作流配置
      if ( workflowDefineDTOs != null && workflowDefineDTOs.size() > 0 )
      {
         for ( WorkflowDefineDTO workflowDefineDTO : workflowDefineDTOs )
         {
            // 初始化触发条件WorkflowDefineRequirementsVOs
            List< WorkflowDefineRequirementsVO > workflowDefineRequirementsVOs = null;

            boolean isLeaveAccessAction = isLeaveAccessAction( baseVO.getHistoryVO().getAccessAction() );
            boolean isOTAccessAction = isOTAccessAction( baseVO.getHistoryVO().getAccessAction() );

            // 特殊情况如果审批对象是加班、请假
            if ( isLeaveAccessAction || isOTAccessAction )
            {
               workflowDefineRequirementsVOs = workflowDefineDTO.getWorkflowDefineRequirementsVOs();
            }

            // 如果触发条件为空
            if ( workflowDefineRequirementsVOs == null || workflowDefineRequirementsVOs.size() == 0 )
            {
               workflowDefineDTOTemp = workflowDefineDTO;
               break;
            }
            else
            {
               // 获取WorkflowModuleDTO
               final WorkflowModuleDTO workflowModuleDTO = accountConstants.getWorkflowModuleDTOByWorkflowModuleId( workflowDefineDTO.getWorkflowDefineVO().getWorkflowModuleId() );

               if ( workflowModuleDTO != null )
               {
                  // 获取AccountModuleDTO
                  final AccountModuleDTO accountModuleDTO = accountConstants.getAccountModuleDTOByModuleId( workflowModuleDTO.getWorkflowModuleVO().getModuleId() );

                  if ( accountModuleDTO != null )
                  {
                     // 获取accessAction 可能是{HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT,HRO_BIZ_EMPLOYEE_LABOR_CONTRACT_IN_HOUSE,HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE} 格式
                     final String accessAction = accountModuleDTO.getModuleVO().getAccessAction();

                     final List< String > accessActions = KANUtil.jasonArrayToStringList( accessAction );

                     // 获取TableDTO
                     final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessActions, baseVO.getRole() );

                     // 找到触发条件
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
   // 工作流触发条件是否满足
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

            // 比较类型  1:等于
            if ( "1".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue == compareValue );

            }
            // 比较类型  ##2:大于
            else if ( "2".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue > compareValue );
            }
            // 比较类型  ##3:小于
            else if ( "3".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue < compareValue );
            }
            // 比较类型  ##4:大于等于
            else if ( "4".equals( workflowDefineRequirementsVO.getCompareType() ) )
            {
               flag = flag && ( columnValue >= compareValue );
            }
            // 比较类型   ##5:小于等于
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
    * 把第二个人自定义步骤合并到第一个中
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
