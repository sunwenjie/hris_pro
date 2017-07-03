package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.BranchDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeSkillDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeUserDao;
import com.kan.hro.domain.biz.employee.EmployeeBaseView;
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.web.actions.biz.employee.EmployeeAction;

public class EmployeeServiceImpl extends ContextService implements EmployeeService
{
   private EmployeeSkillDao employeeSkillDao;

   private StaffDao staffDao;

   private UserDao userDao;

   private EmployeeUserDao employeeUserDao;

   private PositionDao positionDao;

   private BranchDao branchDao;

   private PositionStaffRelationDao positionStaffRelationDao;

   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeVO";

   public final String SERVICE_BEAN = "employeeService";

   private WorkflowService workflowService;

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public BranchDao getBranchDao()
   {
      return branchDao;
   }

   public void setBranchDao( BranchDao branchDao )
   {
      this.branchDao = branchDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }

   @Override
   public PagedListHolder getEmployeeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeDao employeeDao = ( EmployeeDao ) getDao();
      pagedListHolder.setHolderSize( employeeDao.countEmployeeVOsByCondition( ( EmployeeVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeDao.getEmployeeVOsByCondition( ( EmployeeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeDao.getEmployeeVOsByCondition( ( EmployeeVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeVO getEmployeeVOByEmployeeId( final String employeeId ) throws KANException
   {

      return ( ( EmployeeDao ) getDao() ).getEmployeeVOByEmployeeId( employeeId );
   }

   @Override
   public int insertEmployee( final EmployeeVO employeeVO ) throws KANException
   {

      boolean isPassObject = WorkflowService.isPassObject( employeeVO );

      if ( !isPassObject )
      {

         HistoryVO historyVO = initHistoryVO( employeeVO );
         historyVO.setRightId( KANConstants.MODULE_RIGHT_NEW );
         WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeVO );
         if ( workflowActualDTO != null )
         {
            historyVO.setServiceMethod( "insertEmployee" );
            // û����ӵ����ݿ����д -1
            historyVO.setObjectId( "-1" );
            // 2��ʾ�ǹ�������
            historyVO.setObjectType( "2" );
            // �������
            historyVO.setPassObject( KANUtil.toJSONObject( employeeVO ).toString() );
            workflowService.createWorkflowActual( workflowActualDTO, employeeVO );
            return -1;
         }

      }

      try
      {
         // ��������
         startTransaction();

         employeeVO.set_tempUsername( employeeVO.getUsername() );
         // �ȱ���employee 
         ( ( EmployeeDao ) getDao() ).insertEmployee( employeeVO );

         if ( employeeVO.getSkillIdArray() != null && employeeVO.getSkillIdArray().length > 0 )
         {
            for ( String skillId : employeeVO.getSkillIdArray() )
            {
               EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
               employeeSkillVO.setSkillId( skillId );
               employeeSkillVO.setEmployeeId( employeeVO.getEmployeeId() );
               employeeSkillVO.setCreateBy( employeeVO.getCreateBy() );
               employeeSkillVO.setCreateDate( new Date() );
               employeeSkillDao.insertEmployeeSkill( employeeSkillVO );
            }
         }

         if ( KANUtil.filterEmpty( employeeVO.getCorpId() ) != null )
         {
            // ����һ��staff
            final StaffVO staffVO = new StaffVO();
            staffVO.setEmployeeId( employeeVO.getEmployeeId() );
            staffVO.setAccountId( employeeVO.getAccountId() );
            staffVO.setClientId( employeeVO.getClientId() );
            staffVO.setCorpId( employeeVO.getCorpId() );
            staffVO.setNameZH( employeeVO.getNameZH() );
            staffVO.setNameEN( employeeVO.getNameEN() );
            staffVO.setStaffNo( employeeVO.getEmployeeNo() );
            staffVO.setSalutation( employeeVO.getSalutation() );
            staffVO.setBirthday( employeeVO.getBirthday() );
            staffVO.setMaritalStatus( employeeVO.getStatus() );
            staffVO.setRegistrationHometown( employeeVO.getResidencyCityId() );
            staffVO.setRegistrationAddress( employeeVO.getRecordAddress() );
            staffVO.setPersonalAddress( employeeVO.getPersonalAddress() );
            staffVO.setPersonalPostcode( employeeVO.getPersonalPostcode() );
            staffVO.setCertificateType( employeeVO.getCertificateType() );
            staffVO.setCertificateNumber( employeeVO.getCertificateNumber() );
            staffVO.setDescription( employeeVO.getDescription() );
            staffVO.setBizEmail( employeeVO.getEmail1() );
            staffVO.setBizMobile( employeeVO.getMobile1() );
            staffVO.setPersonalEmail( employeeVO.getEmail2() );
            staffVO.setPersonalMobile( employeeVO.getMobile2() );
            staffVO.setStatus( "1" );
            staffVO.setRemark1( employeeVO.getRemark1() );
            staffVO.setRemark2( employeeVO.getRemark2() );
            staffVO.setRemark3( employeeVO.getRemark3() );
            staffVO.setCreateBy( employeeVO.getCreateBy() );
            staffVO.setCreateDate( new Date() );
            staffVO.setAccountName( employeeVO.getAccountName() );
            staffDao.insertStaff( staffVO );

            // Role���������ݿ�
            staffVO.setRole( employeeVO.getRole() );

            // ���Username��Ϊ�գ�������User����
            if ( employeeVO.getUsername() != null && !employeeVO.getUsername().trim().equals( "" ) )
            {
               String pwd = PassWordUtil.randomPassWord();
               // ����û����Ƿ��Ѿ�����
               final UserVO userVO = new UserVO();
               userVO.setUsername( employeeVO.getUsername() );
               userVO.setAccountId( staffVO.getAccountId() );
               userVO.setStaffId( staffVO.getStaffId() );
               userVO.setPassword( Cryptogram.encodeString( pwd ) );
               userVO.setClientId( employeeVO.getClientId() );
               userVO.setCorpId( employeeVO.getCorpId() );
               userVO.setStatus( "1" );
               SecurityAction.encryptPassword( userVO, pwd );

               // Role���������ݿ�
               userVO.setRole( employeeVO.getRole() );

               // �����ǰ�û��������ڣ�������û���¼
               if ( userDao.login_inHouse( userVO ) == null )
               {
                  userDao.insertUser( userVO );

                  // ���͵�¼��Ϣ
                  userVO.setPassword( pwd );
                  sendUserLoginEmail( staffVO, employeeVO, userVO );
               }
               else
               {
                  logger.error( "����û����ظ�" );
               }
            }
         }
         else if ( KANConstants.ROLE_HR_SERVICE.equals( employeeVO.getRole() ) )
         {

            if ( StringUtils.isNotBlank( employeeVO.getUsername() ) )
            {
               final EmployeeUserVO employeeUserVO = new EmployeeUserVO();
               employeeUserVO.setUsername( employeeVO.getUsername() );
               employeeUserVO.setAccountId( employeeVO.getAccountId() );
               employeeUserVO.setEmployeeId( employeeVO.getEmployeeId() );
               employeeUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord() ) );
               employeeUserVO.setStatus( "1" );

               employeeUserDao.insertEmployeeUser( employeeUserVO );

               // �����û���¼��Ϣ
               sendUserLoginEmail( null, employeeVO, null );
            }
         }

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }
      // ͬ��΢��
      BaseAction.syncWXContacts( employeeVO.getEmployeeId() );

      return 0;
   }

   @Override
   // Reviewed by Kevin Jin at 2014-04-28
   public int updateEmployee( final EmployeeVO employeeVO ) throws KANException
   {
      boolean isPassObject = WorkflowService.isPassObject( employeeVO );

      if ( !isPassObject )
      {
         // ��ʼ��HistoryVO
         final HistoryVO historyVO = initHistoryVO( employeeVO );
         historyVO.setRightId( KANConstants.MODULE_RIGHT_MODIFY );

         // ��ʼ��WorkflowActualDTO
         WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( employeeVO );

         if ( workflowActualDTO != null )
         {
            historyVO.setServiceMethod( "updateEmployee" );
            // ���ݿⲻ���ڵ���д��-1��
            historyVO.setObjectId( employeeVO.getEmployeeId() );
            // 2:������
            historyVO.setObjectType( "2" );
            // �������
            historyVO.setPassObject( KANUtil.toJSONObject( employeeVO ).toString() );
            historyVO.setServiceGetObjByIdMethod( "getEmployeeVOByEmployeeId" );
            this.getWorkflowService().createWorkflowActual( workflowActualDTO, employeeVO );

            return -1;
         }
      }

      try
      {
         this.startTransaction();

         employeeVO.set_tempUsername( employeeVO.getUsername() );
         //   ���޸�EmployeeVO
         ( ( EmployeeDao ) getDao() ).updateEmployee( employeeVO );

         if ( employeeVO.getSkillIdArray() != null && employeeVO.getSkillIdArray().length > 0 )
         {
            // ��ȡEmployeeSkillVO�б�
            final List< Object > employeeSkillVOs = this.getEmployeeSkillDao().getEmployeeSkillVOsByEmployeeId( employeeVO.getEmployeeId() );

            for ( String skillId : employeeVO.getSkillIdArray() )
            {
               boolean exist = false;

               if ( employeeSkillVOs != null && employeeSkillVOs.size() > 0 )
               {
                  for ( Object employeeSkillVOObject : employeeSkillVOs )
                  {
                     if ( employeeSkillVOObject instanceof EmployeeSkillVO )
                     {
                        final EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) employeeSkillVOObject;
                        final String tempSkillId = employeeSkillVO.getSkillId();

                        if ( tempSkillId.equals( skillId ) )
                        {
                           exist = true;

                           break;
                        }
                     }
                  }
               }

               if ( !exist )
               {
                  final EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
                  employeeSkillVO.setSkillId( skillId );
                  employeeSkillVO.setEmployeeId( employeeVO.getEmployeeId() );
                  employeeSkillVO.setCreateBy( employeeVO.getCreateBy() );
                  this.getEmployeeSkillDao().insertEmployeeSkill( employeeSkillVO );
               }
            }
         }

         // ͬ���޸�StaffVO����
         if ( KANUtil.filterEmpty( employeeVO.getCorpId() ) != null )
         {
            // ��ѯһ��StaffVO
            StaffVO staffVO = this.getStaffDao().getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

            if ( staffVO != null )
            {
               staffVO.setNameZH( employeeVO.getNameZH() );
               staffVO.setNameEN( employeeVO.getNameEN() );
               staffVO.setBizEmail( employeeVO.getEmail1() );
               staffVO.setStaffNo( employeeVO.getEmployeeNo() );
               staffVO.setSalutation( employeeVO.getSalutation() );
               staffVO.setBirthday( employeeVO.getBirthday() );
               staffVO.setMaritalStatus( employeeVO.getStatus() );
               staffVO.setRegistrationHometown( employeeVO.getResidencyCityId() );
               staffVO.setRegistrationAddress( employeeVO.getRecordAddress() );
               staffVO.setPersonalAddress( employeeVO.getPersonalAddress() );
               staffVO.setPersonalPostcode( employeeVO.getPersonalPostcode() );
               staffVO.setCertificateType( employeeVO.getCertificateType() );
               staffVO.setCertificateNumber( employeeVO.getCertificateNumber() );
               staffVO.setDescription( employeeVO.getDescription() );
               staffVO.setBizEmail( employeeVO.getEmail1() );
               staffVO.setBizMobile( employeeVO.getMobile1() );
               staffVO.setPersonalEmail( employeeVO.getEmail2() );
               staffVO.setPersonalMobile( employeeVO.getMobile2() );
               staffVO.setRemark1( employeeVO.getRemark1() );
               staffVO.setRemark2( employeeVO.getRemark2() );
               staffVO.setRemark3( employeeVO.getRemark3() );
               staffVO.setModifyDate( new Date() );
               staffVO.setModifyBy( employeeVO.getModifyBy() );
               this.getStaffDao().updateStaff( staffVO );

               // Role���������ݿ�
               staffVO.setRole( employeeVO.getRole() );

               // ���Username��Ϊ�գ�������User����
               if ( KANUtil.filterEmpty( employeeVO.getUsername() ) != null )
               {
                  // ��ȡUserVO
                  UserVO userVO = this.getUserDao().getUserVOByStaffId( staffVO.getStaffId() );

                  if ( userVO == null )
                  {
                     String pwd = PassWordUtil.randomPassWord();
                     userVO = new UserVO();
                     userVO.setAccountId( staffVO.getAccountId() );
                     userVO.setStaffId( staffVO.getStaffId() );
                     userVO.setUsername( employeeVO.getUsername() );
                     userVO.setPassword( Cryptogram.encodeString( pwd ) );
                     userVO.setCorpId( employeeVO.getCorpId() );
                     userVO.setStatus( "1" );
                     SecurityAction.encryptPassword( userVO, pwd );

                     // Role���������ݿ�
                     userVO.setRole( employeeVO.getRole() );

                     // �����û�
                     this.getUserDao().insertUser( userVO );

                     // �����ʼ�
                     userVO.setPassword( pwd );
                     sendUserLoginEmail( staffVO, employeeVO, userVO );
                  }
               }
            }
            else
            {
               // ����һ��StaffVO
               staffVO = new StaffVO();
               staffVO.setEmployeeId( employeeVO.getEmployeeId() );
               staffVO.setAccountId( employeeVO.getAccountId() );
               staffVO.setClientId( employeeVO.getClientId() );
               staffVO.setCorpId( employeeVO.getCorpId() );
               staffVO.setNameZH( employeeVO.getNameZH() );
               staffVO.setNameEN( employeeVO.getNameEN() );
               staffVO.setStaffNo( employeeVO.getEmployeeNo() );
               staffVO.setSalutation( employeeVO.getSalutation() );
               staffVO.setBirthday( employeeVO.getBirthday() );
               staffVO.setMaritalStatus( employeeVO.getStatus() );
               staffVO.setRegistrationHometown( employeeVO.getResidencyCityId() );
               staffVO.setRegistrationAddress( employeeVO.getRecordAddress() );
               staffVO.setPersonalAddress( employeeVO.getPersonalAddress() );
               staffVO.setPersonalPostcode( employeeVO.getPersonalPostcode() );
               staffVO.setCertificateType( employeeVO.getCertificateType() );
               staffVO.setCertificateNumber( employeeVO.getCertificateNumber() );
               staffVO.setDescription( employeeVO.getDescription() );
               staffVO.setBizEmail( employeeVO.getEmail1() );
               staffVO.setBizMobile( employeeVO.getMobile1() );
               staffVO.setPersonalEmail( employeeVO.getEmail2() );
               staffVO.setPersonalMobile( employeeVO.getMobile2() );
               staffVO.setStatus( "1" );
               staffVO.setRemark1( employeeVO.getRemark1() );
               staffVO.setRemark2( employeeVO.getRemark2() );
               staffVO.setRemark3( employeeVO.getRemark3() );
               staffVO.setCreateBy( employeeVO.getCreateBy() );
               staffVO.setCreateDate( new Date() );
               this.getStaffDao().insertStaff( staffVO );

               // Role���������ݿ�
               staffVO.setRole( employeeVO.getRole() );

               // ���Username��Ϊ�գ�������User����
               if ( KANUtil.filterEmpty( employeeVO.getUsername() ) != null )
               {
                  // ����û����Ƿ��Ѿ�����
                  String pwd = PassWordUtil.randomPassWord();
                  final UserVO userVO = new UserVO();
                  userVO.setAccountId( staffVO.getAccountId() );
                  userVO.setStaffId( staffVO.getStaffId() );
                  userVO.setUsername( employeeVO.getUsername() );
                  userVO.setPassword( Cryptogram.encodeString( pwd ) );
                  userVO.setCorpId( employeeVO.getCorpId() );
                  userVO.setStatus( "1" );
                  SecurityAction.encryptPassword( userVO, pwd );
                  // Role���������ݿ�
                  userVO.setRole( employeeVO.getRole() );

                  // �����û�
                  this.getUserDao().insertUser( userVO );

                  // �����ʼ�
                  userVO.setPassword( pwd );
                  sendUserLoginEmail( staffVO, employeeVO, userVO );
               }
            }
         }
         else if ( KANConstants.ROLE_HR_SERVICE.equals( employeeVO.getRole() ) )
         {

            final EmployeeUserVO employeeUserVO = new EmployeeUserVO();
            employeeUserVO.setAccountId( employeeVO.getAccountId() );
            employeeUserVO.setEmployeeId( employeeVO.getEmployeeId() );
            final EmployeeUserVO employeeUserVOTemp = employeeUserDao.getEmployeeUserVOByCondition( employeeUserVO );

            if ( employeeUserVOTemp != null && StringUtils.isNotBlank( employeeVO.getUsername() ) && !employeeVO.getUsername().equals( employeeUserVOTemp.getUsername() ) )
            {
               employeeUserVOTemp.setUsername( employeeVO.getUsername() );
               employeeUserDao.updateEmployeeUser( employeeUserVOTemp );
               //��ԱΪ�����û�
            }
            else if ( employeeUserVOTemp == null && StringUtils.isNotBlank( employeeVO.getUsername() ) )
            {
               employeeUserVO.setUsername( employeeVO.getUsername() );
               employeeUserVO.setEmployeeId( employeeVO.getEmployeeId() );
               employeeUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord() ) );
               employeeUserVO.setStatus( "1" );

               // �����ǰ�û��������ڣ�������û���¼
               employeeUserDao.insertEmployeeUser( employeeUserVO );

               sendUserLoginEmail( null, employeeVO, null );
            }
         }
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      // ͬ��΢��
      BaseAction.syncWXContacts( employeeVO.getEmployeeId() );

      return 1;
   }

   private void sendUserLoginEmail( final StaffVO staffVO, final EmployeeVO employeeVO, final UserVO userVO ) throws KANException
   {
      String email = "";
      if ( staffVO != null )
      {
         email = staffVO.getBizEmail();

         if ( KANUtil.filterEmpty( email ) == null )
         {
            email = staffVO.getPersonalEmail();
         }
      }
      if ( KANUtil.filterEmpty( email ) == null && employeeVO != null )
      {
         email = employeeVO.getEmail1();
         if ( KANUtil.filterEmpty( email ) == null )
         {
            email = employeeVO.getEmail2();
         }
      }

      final List< Object > list = new ArrayList< Object >();
      if ( staffVO != null )
      {
         list.add( staffVO );
      }
      if ( employeeVO != null )
      {
         list.add( employeeVO );
      }
      if ( userVO != null )
      {
         list.add( userVO );
      }
      final Object[] objects = new Object[ list.size() ];
      for ( int i = 0; i < objects.length; i++ )
      {
         objects[ i ] = list.get( i );
      }

      if ( KANUtil.filterEmpty( email ) != null )
      {
         new Mail( staffVO.getAccountId(), email.trim(), ContentUtil.getMailContent_UserCreate( objects ) ).send( true );
      }
   }

   @Override
   public int deleteEmployee( final EmployeeVO employeeVO ) throws KANException
   {
      boolean isPassObject = WorkflowService.isPassObject( employeeVO );
      if ( !isPassObject )
      {
         HistoryVO historyVO = initHistoryVO( employeeVO );
         historyVO.setRightId( KANConstants.MODULE_RIGHT_DELETE );
         WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeVO );
         if ( workflowActualDTO != null )
         {
            historyVO.setServiceMethod( "deleteEmployee" );
            // û����ӵ����ݿ����д -1
            historyVO.setObjectId( employeeVO.getEmployeeId() );
            // 2��ʾ�ǹ�������
            historyVO.setObjectType( "2" );
            // �������
            historyVO.setPassObject( KANUtil.toJSONObject( employeeVO ).toString() );
            historyVO.setServiceGetObjByIdMethod( "getEmployeeVOByEmployeeId" );
            workflowService.createWorkflowActual( workflowActualDTO, employeeVO );
            return -1;
         }

      }

      EmployeeDao dao = ( EmployeeDao ) getDao();
      EmployeeVO employeeVO_DB = dao.getEmployeeVOByEmployeeId( employeeVO.getEmployeeId() );
      employeeVO_DB.setDeleted( BaseVO.FALSE );
      employeeVO_DB.setModifyDate( new Date() );

      dao.updateEmployee( employeeVO_DB );
      // ͬ��΢��
      BaseAction.syncWXContacts( employeeVO_DB.getEmployeeId(), true );

      // ͬ��ɾ��staffVO����
      if ( KANUtil.filterEmpty( employeeVO.getCorpId() ) != null )
      {
         // ��ѯһ��staff
         final StaffVO staffVOTemp = new StaffVO();
         staffVOTemp.setEmployeeId( employeeVO.getEmployeeId() );
         staffVOTemp.setAccountId( employeeVO.getAccountId() );
         staffVOTemp.setClientId( employeeVO.getClientId() );
         staffVOTemp.setCorpId( employeeVO.getCorpId() );

         List< Object > list = staffDao.getStaffVOsByCondition( staffVOTemp );
         if ( list != null && list.size() > 0 )
         {
            StaffVO staffVO = ( StaffVO ) list.get( 0 );
            staffVO.setDeleted( BaseVO.FALSE );
            staffVO.setModifyDate( new Date() );
            staffDao.updateStaff( staffVO );
            // ��ѯ staffpositionrelation
            List< Object > psrs = positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffVO.getStaffId() );
            // ѭ��ɾ����ϵ
            PositionStaffRelationVO positionStaffRelationVO = null;
            for ( Object obj : psrs )
            {
               positionStaffRelationVO = ( PositionStaffRelationVO ) obj;
               positionStaffRelationVO.setDeleted( BaseVO.FALSE );
               staffVO.setModifyDate( new Date() );
               positionStaffRelationDao.updatePositionStaffRelation( positionStaffRelationVO );
            }
            // ��ѯuser
            UserVO userVO = userDao.getUserVOByStaffId( staffVO.getStaffId() );
            if ( userVO != null )
            {
               userVO.setDeleted( BaseVO.FALSE );
               userVO.setModifyDate( new Date() );
               userDao.updateUser( userVO );
            }
         }

      }
      return 1;
   }

   @Override
   public List< Object > getEmployeeBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).getEmployeeBaseViewsByAccountId( accountId );
   }

   @Override
   public List< Object > getEmployeeBaseViewsByCondition( final EmployeeBaseView employeeBaseView ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).getEmployeeBaseViewsByCondition( employeeBaseView );
   }

   @Override
   public List< Object > getEmployeeVOsByPositionId( final String positionId ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).getEmployeeVOsByPositionId( positionId );
   }

   public EmployeeSkillDao getEmployeeSkillDao()
   {
      return employeeSkillDao;
   }

   public void setEmployeeSkillDao( EmployeeSkillDao employeeSkillDao )
   {
      this.employeeSkillDao = employeeSkillDao;
   }

   private HistoryVO initHistoryVO( final EmployeeVO employeeVO )
   {
      HistoryVO history = employeeVO.getHistoryVO();
      history.setAccessAction( EmployeeAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getEmployeeVOByEmployeeId" );
      // ��ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( employeeVO.getAccountId() );
      history.setNameZH( employeeVO.getNameZH() );//ƴ�ӵ�������workflowActualVO��NameZH�ֶ���
      history.setNameEN( employeeVO.getNameEN() );//ƴ�ӵ�������workflowActualVO��NameEN�ֶ���
      return history;
   }

   // ����Position��Staff֮��Ĺ���
   public int insertPositionStaffRelation( final StaffVO staffVO, final EmployeeVO employeeVO ) throws KANException
   {
      try
      {
         // ��ɾ��Position��Ӧ��ϵ��
         this.positionStaffRelationDao.deletePositionStaffRelationByStaffId( staffVO.getStaffId() );
         // Position ��Ҫ���Ա��
         if ( employeeVO.getPositionIdArray() != null && employeeVO.getPositionIdArray().length > 0 )
         {

            // ѭ�����Position��Staff�Ĺ�ϵ
            for ( String positionInfos : employeeVO.getPositionIdArray() )
            {
               // StaffInfos ������positionId��StaffType��AgentStart��AgentEnd
               final String[] positionInfoArray = positionInfos.split( "_" );

               if ( positionInfoArray != null && positionInfoArray.length > 1 )
               {
                  final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
                  positionStaffRelationVO.setStaffId( staffVO.getStaffId() );
                  positionStaffRelationVO.setPositionId( positionInfoArray[ 0 ] );
                  positionStaffRelationVO.setStaffType( positionInfoArray[ 1 ] );
                  if ( positionInfoArray[ 1 ] != null && positionInfoArray[ 1 ].trim().equals( "2" ) && positionInfoArray.length > 3 )
                  {
                     positionStaffRelationVO.setAgentStart( positionInfoArray[ 2 ] );
                     positionStaffRelationVO.setAgentEnd( positionInfoArray[ 3 ] );
                  }
                  positionStaffRelationVO.setCreateBy( employeeVO.getModifyBy() );
                  positionStaffRelationVO.setModifyBy( employeeVO.getModifyBy() );
                  this.positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
               }
            }

         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int updateBaseEmployee( EmployeeVO employeeVO ) throws KANException
   {
      int count = ( ( EmployeeDao ) getDao() ).updateEmployee( employeeVO );
      // ͬ��΢��
      BaseAction.syncWXContacts( employeeVO.getEmployeeId() );
      return count;
   }

   @Override
   public List< Object > getEmployeeNosByEmployeeNoList( final EmployeeVO employeeVO ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).getEmployeeNosByEmployeeNoList( employeeVO );
   }

   @Override
   public List< Object > employeeByLogon( final EmployeeVO employeeVO ) throws KANException
   {
      List< Object > list = ( ( EmployeeDao ) getDao() ).employeeByLogon( employeeVO );
      return list;
   }

   public EmployeeUserDao getEmployeeUserDao()
   {
      return employeeUserDao;
   }

   public void setEmployeeUserDao( EmployeeUserDao employeeUserDao )
   {
      this.employeeUserDao = employeeUserDao;
   }

   @Override
   public List< Object > getEmployeeVOsByAbsEquEmpNo( String employeeNo ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).getEmployeeVOsByAbsEquEmpNo( employeeNo );
   }

   @Override
   // ���ٴ���Ա�� 
   public int quickCreateEmployee( final EmployeeVO employeeVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         employeeVO.set_tempUsername( employeeVO.getUsername() );

         // �ȱ���employee 
         ( ( EmployeeDao ) getDao() ).insertEmployee( employeeVO );
         // ͬ��΢��
         BaseAction.syncWXContacts( employeeVO.getEmployeeId() );

         // ����StaffVO
         insertStaff( employeeVO );

         final StaffVO staffVO = staffDao.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

         // ����Position��Staff֮��Ĺ���
         insertPositionStaffRelation( staffVO, employeeVO );

         // ��ʼ�������ֶ�ְλ
         String _tempPositionIds = "";
         // ��ʼ�������ֶβ���
         String _tempBranchIds = "";
         // ��ʼ�������ֶ��ϼ�����
         String _tempParentBranchIds = "";
         // ��ʼ�������ֶ��ϼ�ְλ
         String _tempParentPositionIds = "";
         // ��ʼ�������ֶ��ϼ�ְλ������
         String _tempParentPositionOwners = "";
         // ��ʼ�������ֶ��ϼ�ְλ����
         String _tempParentPositionBranchIds = "";
         // ��ʼ�������ֶΰ칫��ַ
         String _tempPositionLocationIds = "";
         // ��ʼ�������ֶ�ְ��
         String _tempPositionGradeIds = "";

         final List< Object > positionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByStaffId( staffVO.getStaffId() );

         for ( Object o : positionStaffRelationVOs )
         {
            final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) o;
            final PositionVO positionVO = this.getPositionDao().getPositionVOByPositionId( positionStaffRelationVO.getPositionId() );
            final BranchVO branchVO = this.branchDao.getBranchVOByBranchId( positionVO.getBranchId() );
            final PositionVO parentPositionVO = this.getPositionDao().getPositionVOByPositionId( positionVO.getParentPositionId() );

            _tempPositionIds = positionStaffRelationVO.getPositionId();
            _tempBranchIds = positionVO.getBranchId();
            if ( branchVO != null )
               _tempParentBranchIds = KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null ? branchVO.getBranchId() : branchVO.getParentBranchId();
            _tempParentPositionIds = positionVO.getParentPositionId();
            if ( parentPositionVO != null )
               _tempParentPositionBranchIds = parentPositionVO.getBranchId();
            _tempPositionLocationIds = positionVO.getLocationId();
            _tempPositionGradeIds = positionVO.getPositionGradeId();

            final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionVO.getParentPositionId() );
            if ( parentPositionStaffRelationVOs != null && parentPositionStaffRelationVOs.size() > 0 )
            {
               for ( Object parentO : parentPositionStaffRelationVOs )
               {
                  final PositionStaffRelationVO parentPositionStaffRelationVO = ( PositionStaffRelationVO ) parentO;
                  if ( parentPositionStaffRelationVO != null )
                  {
                     if ( KANUtil.filterEmpty( _tempParentPositionOwners ) == null )
                     {
                        _tempParentPositionOwners = parentPositionStaffRelationVO.getStaffId();
                     }
                     else
                     {
                        _tempParentPositionOwners = _tempParentPositionOwners + "," + parentPositionStaffRelationVO.getStaffId();
                     }
                  }
               }
            }

            employeeVO.set_tempPositionIds( _tempPositionIds );
            employeeVO.set_tempBranchIds( _tempBranchIds );
            employeeVO.set_tempParentBranchIds( _tempParentBranchIds );
            employeeVO.set_tempParentPositionIds( _tempParentPositionIds );
            employeeVO.set_tempParentPositionOwners( _tempParentPositionOwners );
            employeeVO.set_tempParentPositionBranchIds( _tempParentPositionBranchIds );
            employeeVO.set_tempPositionLocationIds( _tempPositionLocationIds );
            employeeVO.set_tempPositionGradeIds( _tempPositionGradeIds );

            ( ( EmployeeDao ) getDao() ).updateEmployee( employeeVO );
         }

         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   // ����StaffVO
   private void insertStaff( final EmployeeVO employeeVO ) throws KANException
   {
      if ( KANUtil.filterEmpty( employeeVO.getCorpId() ) != null )
      {
         // ����һ��staff
         final StaffVO staffVO = new StaffVO();
         staffVO.setEmployeeId( employeeVO.getEmployeeId() );
         staffVO.setAccountId( employeeVO.getAccountId() );
         staffVO.setClientId( employeeVO.getClientId() );
         staffVO.setCorpId( employeeVO.getCorpId() );
         staffVO.setNameZH( employeeVO.getNameZH() );
         staffVO.setNameEN( employeeVO.getNameEN() );
         staffVO.setStaffNo( employeeVO.getEmployeeNo() );
         staffVO.setSalutation( employeeVO.getSalutation() );
         staffVO.setBirthday( employeeVO.getBirthday() );
         staffVO.setMaritalStatus( employeeVO.getStatus() );
         staffVO.setRegistrationHometown( employeeVO.getResidencyCityId() );
         staffVO.setRegistrationAddress( employeeVO.getRecordAddress() );
         staffVO.setPersonalAddress( employeeVO.getPersonalAddress() );
         staffVO.setPersonalPostcode( employeeVO.getPersonalPostcode() );
         staffVO.setCertificateType( employeeVO.getCertificateType() );
         staffVO.setCertificateNumber( employeeVO.getCertificateNumber() );
         staffVO.setDescription( employeeVO.getDescription() );
         staffVO.setBizEmail( employeeVO.getEmail1() );
         staffVO.setBizMobile( employeeVO.getMobile1() );
         staffVO.setPersonalEmail( employeeVO.getEmail2() );
         staffVO.setPersonalMobile( employeeVO.getMobile2() );
         staffVO.setStatus( "1" );
         staffVO.setRemark1( employeeVO.getRemark1() );
         staffVO.setRemark2( employeeVO.getRemark2() );
         staffVO.setRemark3( employeeVO.getRemark3() );
         staffVO.setCreateBy( employeeVO.getCreateBy() );
         staffVO.setCreateDate( new Date() );
         staffVO.setAccountName( employeeVO.getAccountName() );
         staffDao.insertStaff( staffVO );

         // Role���������ݿ�
         staffVO.setRole( employeeVO.getRole() );

         // ���Username��Ϊ�գ�������User����
         if ( employeeVO.getUsername() != null && !employeeVO.getUsername().trim().equals( "" ) )
         {
            // ����û����Ƿ��Ѿ�����
            String pwd = PassWordUtil.randomPassWord();
            final UserVO userVO = new UserVO();
            userVO.setUsername( employeeVO.getUsername() );
            userVO.setAccountId( staffVO.getAccountId() );
            userVO.setStaffId( staffVO.getStaffId() );
            userVO.setPassword( Cryptogram.encodeString( pwd ) );
            userVO.setClientId( employeeVO.getClientId() );
            userVO.setCorpId( employeeVO.getCorpId() );
            userVO.setStatus( "1" );
            SecurityAction.encryptPassword( userVO, pwd );

            // Role���������ݿ�
            userVO.setRole( employeeVO.getRole() );

            // �����ǰ�û��������ڣ�������û���¼
            if ( userDao.login_inHouse( userVO ) == null )
            {
               userDao.insertUser( userVO );

               //���͵�¼��Ϣ
               userVO.setPassword( pwd );
               sendUserLoginEmail( staffVO, employeeVO, userVO );
            }
            else
            {
               logger.error( "����û����ظ�" );
            }
         }
      }
   }

   @Override
   // Add by siuvan 2014-12-11
   // Ա��Tab���ְλajax
   public int addPositionForEmployee( final EmployeeVO employeeVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ��ȡStaffVO
         final StaffVO staffVO = staffDao.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

         if ( staffVO != null )
            // ɾ����ְλ���¼��ϼ�ְλ������
            removeTempParentPositionOwners( staffVO.getStaffId() );

         // ����Staff��Position�Ĺ�ϵ
         insertPositionStaffRelation( staffVO, employeeVO );

         if ( employeeVO != null && employeeVO.getPositionIdArray() != null && employeeVO.getPositionIdArray().length > 0 )
         {
            // ��ʼ�������ֶ�ְλ
            String _tempPositionIds = "";
            // ��ʼ�������ֶβ���
            String _tempBranchIds = "";
            // ��ʼ�������ֶ��ϼ�����
            String _tempParentBranchIds = "";
            // ��ʼ�������ֶ��ϼ�ְλ
            String _tempParentPositionIds = "";
            // ��ʼ�������ֶ��ϼ�ְλ������
            String _tempParentPositionOwners = "";
            // ��ʼ�������ֶ��ϼ�ְλ����
            String _tempParentPositionBranchIds = "";
            // ��ʼ�������ֶΰ칫��ַ
            String _tempPositionLocationIds = "";
            // ��ʼ�������ֶ�ְ��
            String _tempPositionGradeIds = "";

            for ( String str : employeeVO.getPositionIdArray() )
            {
               String positionId = str.split( "_" )[ 0 ];

               // ��ȡPositionVO
               final PositionVO positionVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getPositionVOByPositionId( positionId );

               if ( positionVO != null )
               {
                  // ��ȡBranchVO
                  final BranchVO branchVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getBranchVOByBranchId( positionVO.getBranchId() );
                  if ( branchVO != null )
                  {
                     // ������
                     if ( KANUtil.filterEmpty( _tempBranchIds ) == null )
                        _tempBranchIds = branchVO.getBranchId();
                     else
                        _tempBranchIds = _tempBranchIds + "," + branchVO.getBranchId();

                     // �����ϼ�����
                     if ( KANUtil.filterEmpty( _tempParentBranchIds ) == null )
                     {
                        // ���û���ϼ����ţ�ȡ������
                        if ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null )
                           _tempParentBranchIds = branchVO.getBranchId();
                        else
                           _tempParentBranchIds = branchVO.getParentBranchId();

                     }
                     else
                     {
                        // ���û���ϼ����ţ�ȡ������
                        if ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null )
                           _tempParentBranchIds = _tempParentBranchIds + "," + branchVO.getBranchId();
                        else
                           _tempParentBranchIds = _tempParentBranchIds + "," + branchVO.getParentBranchId();
                     }
                  }

                  // ����ְλ
                  if ( KANUtil.filterEmpty( _tempPositionIds ) == null )
                     _tempPositionIds = positionId;
                  else
                     _tempPositionIds = _tempPositionIds + "," + positionId;

                  // �����ϼ�ְλ
                  if ( KANUtil.filterEmpty( _tempParentPositionIds ) == null )
                  {
                     if ( KANUtil.filterEmpty( positionVO.getParentPositionId(), "0" ) != null )
                     {
                        _tempParentPositionIds = positionVO.getParentPositionId();
                     }
                  }
                  else
                  {
                     if ( KANUtil.filterEmpty( positionVO.getParentPositionId(), "0" ) != null )
                     {
                        _tempParentPositionIds = _tempParentPositionIds + "," + positionVO.getParentPositionId();
                     }
                  }

                  // �����ϼ�ְλ������
                  final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionVO.getParentPositionId() );
                  if ( parentPositionStaffRelationVOs != null && parentPositionStaffRelationVOs.size() > 0 )
                  {
                     for ( Object parentO : parentPositionStaffRelationVOs )
                     {
                        final PositionStaffRelationVO parentPositionStaffRelationVO = ( PositionStaffRelationVO ) parentO;
                        if ( parentPositionStaffRelationVO != null )
                        {
                           if ( KANUtil.filterEmpty( _tempParentPositionOwners ) == null )
                           {
                              _tempParentPositionOwners = parentPositionStaffRelationVO.getStaffId();
                           }
                           else
                           {
                              _tempParentPositionOwners = _tempParentPositionOwners + "," + parentPositionStaffRelationVO.getStaffId();
                           }
                        }
                     }
                  }

                  // �����ϼ�ְλ����
                  final PositionVO parentPositionVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getPositionVOByPositionId( positionVO.getParentPositionId() );
                  if ( parentPositionVO != null )
                  {
                     if ( KANUtil.filterEmpty( _tempParentPositionBranchIds ) == null )
                        _tempParentPositionBranchIds = parentPositionVO.getBranchId();
                     else
                        _tempParentPositionBranchIds = _tempParentPositionBranchIds + "," + parentPositionVO.getBranchId();

                  }

                  // ����칫�ص�
                  if ( KANUtil.filterEmpty( _tempPositionLocationIds ) == null )
                     _tempPositionLocationIds = positionVO.getLocationId();
                  else
                     _tempPositionLocationIds = _tempPositionLocationIds + "," + positionVO.getLocationId();

                  // ����ְ��
                  if ( KANUtil.filterEmpty( _tempPositionGradeIds ) == null )
                     _tempPositionGradeIds = positionVO.getPositionGradeId();
                  else
                     _tempPositionGradeIds = _tempPositionGradeIds + "," + positionVO.getPositionGradeId();

                  // �����¼����ϼ�ְλ������
                  updateSubPositionVOs( positionId, employeeVO.getAccountId() );
               }
            }

            // �������ֵ
            employeeVO.set_tempPositionIds( _tempPositionIds );
            employeeVO.set_tempBranchIds( _tempBranchIds );
            employeeVO.set_tempParentBranchIds( _tempParentBranchIds );
            employeeVO.set_tempParentPositionIds( _tempParentPositionIds );
            employeeVO.set_tempParentPositionOwners( _tempParentPositionOwners );
            employeeVO.set_tempParentPositionBranchIds( _tempParentPositionBranchIds );
            employeeVO.set_tempPositionLocationIds( _tempPositionLocationIds );
            employeeVO.set_tempPositionGradeIds( _tempPositionGradeIds );

            // �־û�EmployeeVO
            ( ( EmployeeDao ) getDao() ).updateEmployee( employeeVO );
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

   // ����ְλʱ����Ҫ�Ը�ְλ�¼�ְλ�ϵ�EmployeeVO��_tempParentPositionOwners����
   private void updateSubPositionVOs( final String positionId, final String accountId ) throws KANException
   {
      final List< Object > parentPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( positionId );
      final PositionVO searchSubPositionVO = new PositionVO();
      searchSubPositionVO.setAccountId( accountId );
      searchSubPositionVO.setParentPositionId( positionId );

      final List< Object > subPositionVOs = this.getPositionDao().getPositionVOsByParentPositionId( searchSubPositionVO );

      if ( subPositionVOs != null && subPositionVOs.size() > 0 )
      {
         final StringBuffer staffIds = new StringBuffer();
         for ( Object o : parentPositionStaffRelationVOs )
         {
            final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) o;
            if ( KANUtil.filterEmpty( staffIds.toString() ) == null )
            {
               staffIds.append( positionStaffRelationVO.getStaffId() );
            }
            else
            {
               staffIds.append( "," + positionStaffRelationVO.getStaffId() );
            }
         }

         for ( Object subPositionVOObject : subPositionVOs )
         {
            final PositionVO subPositionVO = ( PositionVO ) subPositionVOObject;
            final List< Object > subPositionStaffRelationVOs = this.getPositionStaffRelationDao().getPositionStaffRelationVOsByPositionId( subPositionVO.getPositionId() );

            // ���ڻ�ȡPositionStaffRelationVO�б�
            if ( subPositionStaffRelationVOs != null && subPositionStaffRelationVOs.size() > 0 )
            {
               for ( Object o : subPositionStaffRelationVOs )
               {
                  final PositionStaffRelationVO subPositionStaffRelationVO = ( PositionStaffRelationVO ) o;
                  if ( subPositionStaffRelationVO != null && KANUtil.filterEmpty( subPositionStaffRelationVO.getStaffId() ) != null )
                  {
                     final StaffVO staffVO = this.getStaffDao().getStaffVOByStaffId( subPositionStaffRelationVO.getStaffId() );
                     if ( staffVO != null )
                     {
                        final EmployeeVO employeeVO = ( ( EmployeeDao ) getDao() ).getEmployeeVOByEmployeeId( staffVO.getEmployeeId() );
                        if ( employeeVO != null )
                        {
                           employeeVO.set_tempParentPositionOwners( staffIds.toString() );
                           // �־û�EmployeeVO
                           ( ( EmployeeDao ) getDao() ).updateEmployee( employeeVO );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   // ��Ա��������Ϣ�и���ְλ����ɾ���¼�ְλ���ϼ�ְλ������siuvan added 
   private void removeTempParentPositionOwners( final String staffId ) throws KANException
   {
      final List< Object > employeeVOs = ( ( EmployeeDao ) getDao() ).getEmployeeVOsByTempParentPositionOwners( staffId );
      if ( employeeVOs != null && employeeVOs.size() > 0 )
      {
         for ( Object o : employeeVOs )
         {
            final EmployeeVO employeeVO = ( EmployeeVO ) o;
            final List< String > _tempParentPositionOwnerArray = KANUtil.jasonArrayToStringList( employeeVO.get_tempParentPositionOwners() );

            if ( _tempParentPositionOwnerArray != null && _tempParentPositionOwnerArray.size() > 0 )
            {
               _tempParentPositionOwnerArray.remove( staffId );
            }

            employeeVO.set_tempParentPositionOwners( KANUtil.stringListToJasonArray( _tempParentPositionOwnerArray, "," ) );

            if ( KANUtil.filterEmpty( employeeVO.get_tempParentPositionOwners() ) != null )
            {
               employeeVO.set_tempParentPositionOwners( employeeVO.get_tempParentPositionOwners().replace( "{", "" ).replace( "}", "" ) );
            }

            // �־û�EmployeeVO
            ( ( EmployeeDao ) getDao() ).updateEmployee( employeeVO );
         }
      }
   }

   @Override
   public boolean emailIsRegister( final Map< String, Object > parameters ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).emailIsRegister( parameters ).size() >= 1;
   }

   @Override
   public List< Object > getEmployeeVOsByCondition( EmployeeVO employeeVO ) throws KANException
   {
      return ( ( EmployeeDao ) getDao() ).getEmployeeVOsByCondition( employeeVO );
   }

}
