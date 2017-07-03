package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.service.inf.security.PositionService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.web.actions.SecurityAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;

import net.sf.json.JSONObject;

public class StaffServiceImpl extends ContextService implements StaffService
{
   // 报表模块ID
   public static final String[] REPORT_ID_ARRAY = { "257", "354", "449", "499", "549", "599", "649", "656", "749", "799" };

   // 对象类名（例如，com.kan.base.domain.BaseVO）
   public static final String OBJECT_CLASS = "com.kan.base.domain.security.StaffVO";

   // Service Name（例如，Spring定义的Bean。即 spring配置文件中 service对应Bean的ID ）
   public static final String SERVICE_BEAN = "staffService";

   private PositionStaffRelationDao positionStaffRelationDao;

   private PositionService positionService;

   private ReportHeaderService reportHeaderService;

   private WorkflowService workflowService;

   private PositionDao positionDao;

   private EmployeeDao employeeDao;

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public PositionService getPositionService()
   {
      return positionService;
   }

   public void setPositionService( PositionService positionService )
   {
      this.positionService = positionService;
   }

   private UserDao userDao;

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }

   private ModuleDao moduleDao;

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   @Override
   public PagedListHolder getStaffVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final StaffDao staffDao = ( StaffDao ) getDao();
      pagedListHolder.setHolderSize( staffDao.countStaffVOsByCondition( ( StaffVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( staffDao.getStaffVOsByCondition( ( StaffVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( staffDao.getStaffVOsByCondition( ( StaffVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public StaffVO getStaffVOByStaffId( final String staffId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getStaffVOByStaffId( staffId );
   }

   @Override
   /*
    * Code Review at 2013-06-06 by Kevin
    */
   public int updateStaff( final StaffVO staffVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // Update StaffVO
         ( ( StaffDao ) getDao() ).updateStaff( staffVO );
         StaffVO tempStaffVO = ( ( StaffDao ) getDao() ).getStaffVOByStaffId( staffVO.getStaffId() );
         if ( tempStaffVO != null && KANUtil.filterEmpty( tempStaffVO.getEmployeeId() ) != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( tempStaffVO.getEmployeeId() );
            employeeVO.setEmployeeNo( staffVO.getStaffNo() );
            employeeVO.setNameZH( staffVO.getNameZH() );
            employeeVO.setNameEN( staffVO.getNameEN() );
            employeeVO.setSalutation( staffVO.getSalutation() );
            employeeVO.setBirthday( employeeVO.getBirthday() );
            employeeVO.setPhone1( staffVO.getBizPhone() );
            employeeVO.setMobile1( staffVO.getBizMobile() );
            employeeVO.setEmail1( staffVO.getBizEmail() );
            employeeVO.setPhone2( staffVO.getPersonalPhone() );
            employeeVO.setMobile2( staffVO.getPersonalMobile() );
            employeeVO.setEmail2( staffVO.getPersonalEmail() );
            employeeVO.setCertificateType( staffVO.getCertificateType() );
            employeeVO.setCertificateNumber( staffVO.getCertificateNumber() );
            employeeVO.setMaritalStatus( staffVO.getMaritalStatus() );
            employeeVO.setResidencyAddress( staffVO.getRegistrationAddress() );
            employeeVO.setPersonalAddress( staffVO.getPersonalAddress() );
            employeeVO.setPersonalPostcode( staffVO.getPersonalPostcode() );
            employeeVO.setDescription( staffVO.getDescription() );
            employeeVO.set_tempUsername( KANUtil.filterEmpty( staffVO.getUsername() ) );
            employeeDao.updateEmployee( employeeVO );
            tempStaffVO = null;
         }
         // 先删除员工和职位的对应关系
         this.positionStaffRelationDao.deletePositionStaffRelationByStaffId( staffVO.getStaffId() );

         // 如果PositionId不为空，则新增Position跟Staff的关系对象
         if ( staffVO.getPositionIdArray() != null && staffVO.getPositionIdArray().length > 0 )
         {
            for ( String positionId : staffVO.getPositionIdArray() )
            {
               // 添加员工和职位的对应关系
               final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
               positionStaffRelationVO.setStaffId( staffVO.getStaffId() );
               positionStaffRelationVO.setPositionId( positionId );
               positionStaffRelationVO.setStaffType( "1" );
               positionStaffRelationVO.setStatus( "1" );
               positionStaffRelationVO.setCreateBy( staffVO.getModifyBy() );
               positionStaffRelationVO.setModifyBy( staffVO.getModifyBy() );
               positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
            }
         }

         // 如果Username不为空，则新增User对象
         if ( staffVO.getUsername() != null && !staffVO.getUsername().trim().equals( "" ) )
         {
            // 检查用户名是否已经存在
            String pwd = PassWordUtil.randomPassWord();
            final UserVO userVO = new UserVO();
            userVO.setUsername( staffVO.getUsername() );
            userVO.setAccountId( staffVO.getAccountId() );
            userVO.setCorpId( staffVO.getCorpId() );
            userVO.setStaffId( staffVO.getStaffId() );
            userVO.setPassword( Cryptogram.encodeString( pwd ) );
            userVO.setStatus( "1" );
            SecurityAction.encryptPassword( userVO, pwd );

            // 如果当前用户名不存在，则添加用户记录

            boolean userExist = true;
            if ( KANUtil.filterEmpty( userVO.getCorpId() ) == null && userDao.login( userVO ) == null )
            {
               userExist = false;
            }
            else if ( KANUtil.filterEmpty( userVO.getCorpId() ) != null && userDao.login_inHouse( userVO ) == null )
            {
               userExist = false;
            }
            if ( !userExist )
            {
               // 务必保持UserVO和StaffVO一对一
               // Modify by siuvan 2014-04-07
               final UserVO oldUserVO = this.getUserDao().getUserVOByStaffId( staffVO.getStaffId() );
               if ( oldUserVO != null )
               {
                  oldUserVO.setUsername( staffVO.getUsername() );
                  oldUserVO.setModifyDate( new Date() );
                  oldUserVO.setModifyBy( staffVO.getModifyBy() );

                  this.getUserDao().updateUser( oldUserVO );
               }
               else
               {
                  userVO.setCreateBy( staffVO.getModifyBy() );
                  userVO.setModifyBy( staffVO.getModifyBy() );
                  userDao.insertUser( userVO );
                  if ( KANUtil.filterEmpty( staffVO.getBizEmail() ) != null )
                  {
                     userVO.setPassword( pwd );
                     new Mail( staffVO.getAccountId(), staffVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { userVO, staffVO } ) ).send( true );
                  }
               }
            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   /*
    * Code Review at 2013-06-06 by Kevin
    */
   public int insertStaff( final StaffVO staffVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         ( ( StaffDao ) getDao() ).insertStaff( staffVO );

         // 如果PositionId不为空，则新增Position跟Staff的关系对象
         if ( staffVO.getPositionIdArray() != null && staffVO.getPositionIdArray().length > 0 )
         {
            for ( String positionId : staffVO.getPositionIdArray() )
            {
               // 添加员工和职位的对应关系
               final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
               positionStaffRelationVO.setStaffId( staffVO.getStaffId() );
               positionStaffRelationVO.setPositionId( positionId );
               positionStaffRelationVO.setStaffType( "1" );
               positionStaffRelationVO.setStatus( "1" );
               positionStaffRelationVO.setCreateBy( staffVO.getModifyBy() );
               positionStaffRelationVO.setModifyBy( staffVO.getModifyBy() );
               positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
            }
         }

         // 如果Username不为空，则新增User对象
         if ( staffVO.getUsername() != null && !staffVO.getUsername().trim().equals( "" ) )
         {
            // 检查用户名是否已经存在
            String pwd = PassWordUtil.randomPassWord();
            final UserVO userVO = new UserVO();
            userVO.setUsername( staffVO.getUsername() );
            userVO.setAccountId( staffVO.getAccountId() );
            userVO.setStaffId( staffVO.getStaffId() );
            userVO.setPassword( Cryptogram.encodeString( pwd ) );
            userVO.setStatus( "1" );
            SecurityAction.encryptPassword( userVO, pwd );

            // 如果当前用户名不存在，则添加用户记录
            if ( userDao.login( userVO ) == null )
            {
               userVO.setCreateBy( staffVO.getModifyBy() );
               userVO.setModifyBy( staffVO.getModifyBy() );
               userDao.insertUser( userVO );
            }

            if ( KANUtil.filterEmpty( staffVO.getBizEmail() ) != null )
            {
               userVO.setPassword( pwd );
               new Mail( staffVO.getAccountId(), staffVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { userVO, staffVO } ) ).send( true );
            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   /*
    * Code Review at 2013-06-06 by Kevin
    */
   public int deleteStaff( final StaffVO staffVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         // 逻辑删除Staff对象
         staffVO.setDeleted( StaffVO.FALSE );
         ( ( StaffDao ) getDao() ).updateStaff( staffVO );

         // 获取对应StaffId的已有的Relation
         final List< Object > positionStaffRelationVOs = this.positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffVO.getStaffId() );
         // 删除Staff和Position所有Relation
         if ( positionStaffRelationVOs != null && positionStaffRelationVOs.size() > 0 )
         {
            for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
            {
               final PositionStaffRelationVO positionStaffRelationVO = ( PositionStaffRelationVO ) positionStaffRelationVOObject;
               positionStaffRelationVO.setDeleted( BaseVO.FALSE );
               positionStaffRelationVO.setModifyBy( staffVO.getModifyBy() );
               positionStaffRelationVO.setModifyDate( new Date() );
               // 将Delete字段标记为已删除
               positionStaffRelationDao.updatePositionStaffRelation( positionStaffRelationVO );
            }
         }

         // 获取对应的user 删除
         final UserVO userVO = this.userDao.getUserVOByStaffId( staffVO.getStaffId() );
         // modify by steven 2014-05-30 fix bug 304
         if ( userVO != null )
         {
            userVO.setModifyBy( staffVO.getModifyBy() );
            userVO.setModifyDate( staffVO.getModifyDate() );
            userDao.deleteUser( userVO );
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< Object > getStaffVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getStaffVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByPositionId( final String positionId ) throws KANException
   {
      return ( ( PositionStaffRelationDao ) getPositionStaffRelationDao() ).getPositionStaffRelationVOsByPositionId( positionId );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByPositionStaffRelationVO( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return ( ( PositionStaffRelationDao ) getPositionStaffRelationDao() ).getPositionStaffRelationVOsByCondition( positionStaffRelationVO );
   }

   @Override
   public List< Object > getStaffBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getStaffBaseViewsByAccountId( accountId );
   }

   @Override
   public List< Object > getActiveStaffBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getActiveStaffBaseViewsByAccountId( accountId );
   }

   @Override
   public StaffDTO getStaffDTOByStaffId( final String staffId ) throws KANException
   {
      // 初始化StaffVO
      final StaffVO staffVO = ( ( StaffDao ) getDao() ).getStaffVOByStaffId( staffId );

      // 初始化StaffDTO
      final StaffDTO staffDTO = new StaffDTO();

      // 添加StaffVO对象
      staffDTO.setStaffVO( staffVO );

      // 添加PositionStaffRelation
      final List< Object > positionStaffRelationVOs = positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( staffVO.getStaffId() );

      for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
      {
         staffDTO.getPositionStaffRelationVOs().add( ( PositionStaffRelationVO ) positionStaffRelationVOObject );
      }

      // 添加ModuleDTO对象
      staffDTO.setModuleDTOs( getModuleDTOsByStaffId( staffVO.getAccountId(), staffVO.getStaffId(), getRoleByStaff( staffVO ) ) );

      // 添加UserVO对象
      final UserVO userVO = userDao.getUserVOByStaffId( staffVO.getStaffId() );
      staffDTO.setUserVO( userVO );

      return staffDTO;
   }

   @Override
   public List< StaffDTO > getStaffDTOsByAccountId( final String accountId ) throws KANException
   {
      // 创建StaffDTO List，用于返回数据
      final List< StaffDTO > staffDTOs = new ArrayList< StaffDTO >();

      // 获得所有账户有效的Staff对象
      final List< Object > staffVOs = ( ( StaffDao ) getDao() ).getStaffVOsByAccountId( accountId );

      for ( Object staffVOObject : staffVOs )
      {
         final long d1 = new Date().getTime();

         final StaffDTO staffDTO = new StaffDTO();
         // 添加StaffVO对象
         staffDTO.setStaffVO( ( StaffVO ) staffVOObject );

         // 添加PositionStaffRelation
         final List< Object > positionStaffRelationVOs = positionStaffRelationDao.getPositionStaffRelationVOsByStaffId( ( ( StaffVO ) staffVOObject ).getStaffId() );
         for ( Object positionStaffRelationVOObject : positionStaffRelationVOs )
         {
            staffDTO.getPositionStaffRelationVOs().add( ( PositionStaffRelationVO ) positionStaffRelationVOObject );
         }

         // 如果TASK_INIT为true, 则是晚上主动刷新内存
         if(KANConstants.TASK_INIT){
           staffDTO.setModuleDTOs( getModuleDTOsByStaffId( accountId, ( ( StaffVO ) staffVOObject ).getStaffId(), getRoleByStaff( ( StaffVO ) staffVOObject ) ) );
         }
         staffDTOs.add( staffDTO );

         // 添加UserVO对象
         UserVO userVO = userDao.getUserVOByStaffId( ( ( StaffVO ) staffVOObject ).getStaffId() );
         staffDTO.setUserVO( userVO );
         final long d2 = new Date().getTime();
         System.err.println( ( d2 - d1 ) / 1000 );
      }

      return staffDTOs;
   }

   // 私有方法，获取当前staff在环境中的role值
   private String getRoleByStaff( final StaffVO staffVO ) throws KANException
   {
      // 初始化role值
      String role = KANConstants.ROLE_HR_SERVICE;

      // 获取StaffVO
      if ( staffVO != null )
      {
         role = KANUtil.filterEmpty( staffVO.getCorpId() ) == null ? KANConstants.ROLE_HR_SERVICE : KANConstants.ROLE_IN_HOUSE;
      }

      return role;
   }

   // Updated by siuxia at 2014-04-04 //
   /* Add by Kevin at 2013-06-25 */
   public List< ModuleDTO > getModuleDTOsByStaffId( final String accountId, final String staffId, String role ) throws KANException
   {
      // 创建ModuleDTO List，用于返回数据
      final List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

      // 获得根节点，默认根节点的父节点值为“0”
      final List< Object > rootModuleVOs = moduleDao.getModuleVOsByParentModuleId( "0" );

      for ( Object rootModuleObject : rootModuleVOs )
      {
         // 初始化ModuleVO
         final ModuleVO moduleVO = ( ModuleVO ) rootModuleObject;

         // 当前模块role和staff环境下一致
         if ( moduleVO != null && ( moduleVO.getRole().equals( "0" ) || moduleVO.getRole().equals( role ) ) )
         {
            // 递归遍历
            final ModuleDTO moduleDTO = fetchModuleDTOByStaffId( moduleVO, accountId, staffId, role );

            // 如果遍历回来的moduleDTO不为空则添加至列表
            if ( moduleDTO != null && moduleDTO.getModuleDTOs() != null && moduleDTO.getModuleDTOs().size() > 0 )
            {
               moduleDTOs.add( moduleDTO );
            }
         }
      }

      return moduleDTOs;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.kan.base.service.inf.security.StaffService#updateStaffModuleDTO(java
    * .lang.String, java.lang.String, java.lang.String, java.util.List)
    */
   public void updateStaffModuleDTO( final String accountId, final String staffId, final String reportHeaderId, final List< ModuleDTO > moduleDTOs ) throws KANException
   {

      final ReportDTO reportDTO = reportHeaderService.getReportDTOByReportHeaderId( reportHeaderId );
      // 需要删除的菜单
      boolean isDel = false;
      boolean isAdd = false;
      // 需要添加子菜单的菜单
      for ( ModuleDTO moduleDTO : moduleDTOs )
      {

         if ( !isDel )
         {
            // 需要删除的菜单
            isDel = this.deleteStaffModuleDTOCascade( moduleDTO.getModuleDTOs(), reportDTO );
         }

         if ( !isAdd )
         {
            isAdd = this.addStaffModuleDTOCascade( accountId, staffId, moduleDTO.getModuleDTOs(), reportDTO );
         }

         if ( isDel && isAdd )
         {
            return;
         }
      }
   }

   /**
    * @param accountId
    * @param staffId
    * @param moduleDTOs
    * @param reportDTO
    * @throws KANException
    *             递归寻找符合的菜单进行删除添加
    */
   private boolean deleteStaffModuleDTOCascade( final List< ModuleDTO > moduleDTOs, final ReportDTO reportDTO ) throws KANException
   {
      ModuleVO moduleVO = null;
      // 需要删除的菜单
      ModuleDTO moduleDTOTempDel = null;
      boolean returnFlag = false;
      for ( ModuleDTO moduleDTO : moduleDTOs )
      {
         moduleVO = moduleDTO.getModuleVO();

         if ( StringUtils.isNotBlank( moduleDTO.getModuleVO().getRemark5() )
               && ( "Report_" + reportDTO.getReportHeaderVO().getReportHeaderId() ).equals( moduleDTO.getModuleVO().getRemark5() ) )
         {
            // 需要删除的菜单
            moduleDTOTempDel = moduleDTO;
            returnFlag = true;
            break;
         }
         else if ( moduleDTO.getModuleDTOs().size() > 0 )
         {
            returnFlag = this.deleteStaffModuleDTOCascade( moduleDTO.getModuleDTOs(), reportDTO );
         }
         // //返回
         // if(returnFlag)return returnFlag;
      }
      if ( moduleDTOTempDel != null )
      {
         moduleDTOs.remove( moduleDTOTempDel );
      }

      return returnFlag;

   }

   /**
    * @param accountId
    * @param staffId
    * @param moduleDTOs
    * @param reportDTO
    * @throws KANException
    *             递归寻找符合的菜单进行删除添加
    */
   private boolean addStaffModuleDTOCascade( final String accountId, final String staffId, final List< ModuleDTO > moduleDTOs, final ReportDTO reportDTO ) throws KANException
   {
      ModuleVO moduleVO = null;
      // 需要添加子菜单的菜单
      ModuleDTO moduleDTOTempAdd = null;
      boolean returnFlag = false;
      // 初始化ReportHeaderVO
      final ReportHeaderVO reportHeaderVO = reportDTO.getReportHeaderVO();
      for ( ModuleDTO moduleDTO : moduleDTOs )
      {
         moduleVO = moduleDTO.getModuleVO();
         if ( reportHeaderVO != null && "2".equals( reportHeaderVO.getStatus() ) && "1".equals( reportHeaderVO.getDeleted() )
               && StringUtils.isNotBlank( reportHeaderVO.getModuleType() ) && reportHeaderVO.getModuleType().equals( moduleVO.getModuleType() )
               && "customReport".equals( moduleVO.getRemark5() ) )
         {
            // 存在权限
            if ( existModuleByCondition( reportDTO.getReportHeaderVO(), accountId, staffId ) )
            {

               // 获取临时ModuleVO
               moduleDTOTempAdd = new ModuleDTO();
               moduleDTOTempAdd.setModuleVO( getTempModuleVO( moduleVO, reportHeaderVO ) );
               moduleDTO.getModuleDTOs().add( moduleDTOTempAdd );
               returnFlag = true;
               break;
            }
         }
         else if ( moduleDTO.getModuleDTOs().size() > 0 )
         {
            returnFlag = this.addStaffModuleDTOCascade( accountId, staffId, moduleDTO.getModuleDTOs(), reportDTO );
         }
      }
      return returnFlag;

   }

   // 递归方法
   /*
    * Update by Siuvan Xia at 2014-6-13 /* Update by siuxia at 2014-04-04
    */
   /* Add by Kevin at 2013-06-25 */
   private ModuleDTO fetchModuleDTOByStaffId( final ModuleVO moduleVO, final String accountId, final String staffId, String role ) throws KANException
   {
      final ModuleDTO moduleDTO = new ModuleDTO();

      // 如果模块是报表
      if ( moduleVO != null && KANUtil.hasContain( REPORT_ID_ARRAY, moduleVO.getModuleId() ) && existModuleByStaffId( moduleVO.getModuleId(), accountId, staffId ) )
      {
         moduleDTO.setModuleVO( moduleVO );
         final List< ModuleVO > subModuleVOs = getModuleVOs( moduleVO, accountId, staffId );
         if ( subModuleVOs != null )
         {
            for ( ModuleVO subModuleVO : subModuleVOs )
            {
               final ModuleDTO moduleDTOTemp = new ModuleDTO();
               moduleDTOTemp.setModuleVO( subModuleVO );
               moduleDTO.getModuleDTOs().add( moduleDTOTemp );
            }
         }
      }

      if ( moduleVO != null && existModuleByStaffId( moduleVO.getModuleId(), accountId, staffId ) )
      {
         moduleDTO.setModuleVO( moduleVO );

         // 继续查找下一层Module
         final List< Object > subModuleVOs = moduleDao.getModuleVOsByParentModuleId( moduleVO.getModuleId() );

         long d1 = new Date().getTime();

         for ( Object subModuleObject : subModuleVOs )
         {
            // 初始化ModuleVO
            final ModuleVO subModuleVO = ( ModuleVO ) subModuleObject;

            // 当前模块role和staffId所处环境下role一致
            if ( subModuleVO != null && ( subModuleVO.getRole().equals( "0" ) || subModuleVO.getRole().equals( role ) ) )
            {
               // 递归调用
               final ModuleDTO moduleDTOTemp = fetchModuleDTOByStaffId( subModuleVO, accountId, staffId, role );

               // 如果遍历回来的moduleDTO不为空则添加至列表
               if ( moduleDTOTemp != null )
               {
                  moduleDTO.getModuleDTOs().add( moduleDTOTemp );
               }

            }
         }

         long d2 = new Date().getTime();
         System.out.println( "time=" + ( d2 - d1 ) );
      }

      return moduleDTO;
   }

   // 判断当前员工是否有模块的访问权限
   /* Add by Kevin at 2013-06-25 */
   /* modify by Jack.Sun at 2014-04-04 */
   private boolean existModuleByStaffId( final String moduleId, final String accountId, final String staffId ) throws KANException
   {
      if ( moduleId != null && staffId != null && !moduleId.trim().equals( "" ) && !staffId.trim().equals( "" ) )
      {
         // 查询当前Staff是否有权限访问当前菜单
         final ModuleVO moduleVO = new ModuleVO();
         moduleVO.setAccountId( accountId );
         moduleVO.setStaffId( staffId );
         moduleVO.setModuleId( moduleId );

         // 一次查出全局规则，职位访问，职位组访问模块
         final List< Object > groupModuleVOs = moduleDao.getModuleVOsByModuleVO( moduleVO );
         if ( groupModuleVOs != null && groupModuleVOs.size() > 0 )
         {
            return true;
         }

      }

      return false;
   }

   // 获得报表下的子节点
   private List< ModuleVO > getModuleVOs( final ModuleVO moduleVO, final String accountId, final String staffId ) throws KANException
   {
      // 初始化返回对象
      final List< ModuleVO > moduleVOs = new ArrayList< ModuleVO >();
      // 获得ReportDTO列表已发布的
      final List< ReportDTO > reportDTOs = reportHeaderService.getReportDTOsByAccountId( accountId );

      if ( reportDTOs != null && reportDTOs.size() > 0 )
      {
         for ( ReportDTO reportDTO : reportDTOs )
         {
            if ( reportDTO.getReportHeaderVO() != null && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getStatus() ) != null
                  && reportDTO.getReportHeaderVO().getStatus().equals( "2" ) && KANUtil.filterEmpty( reportDTO.getReportHeaderVO().getModuleType() ) != null
                  && reportDTO.getReportHeaderVO().getModuleType().equals( moduleVO.getModuleType() ) )
            {
               // 存在权限
               if ( existModuleByCondition( reportDTO.getReportHeaderVO(), accountId, staffId ) )
               {
                  // 初始化ReportHeaderVO
                  final ReportHeaderVO reportHeaderVO = reportDTO.getReportHeaderVO();

                  // 获取临时ModuleVO
                  moduleVOs.add( getTempModuleVO( moduleVO, reportHeaderVO ) );
               }
            }
         }

         return moduleVOs;
      }

      return null;
   }

   // 获取当前用户是否有访问报表模块的权限
   /* Add by Siuxia at 2013-12-17 */
   private boolean existModuleByCondition( final ReportHeaderVO reportHeaderVO, final String accountId, final String staffId ) throws KANException
   {
      if ( reportHeaderVO != null && KANUtil.filterEmpty( staffId ) != null )
      {
         // 如果是公有的
         if ( KANUtil.filterEmpty( reportHeaderVO.getIsPublic(), "0" ) != null && reportHeaderVO.getIsPublic().equals( "1" ) )
         {
            return true;
         }
         // 如果是私有的
         else
         {
            // 获取当前accountId下的PositionDTO列表
            // 获取当前staffId下的PositionDTO列表

            final List< PositionDTO > staffPositionDTOs = KANConstants.getKANAccountConstants( accountId ).getPositionDTOsByStaffId( staffId );

            if ( staffPositionDTOs != null && staffPositionDTOs.size() > 0 )
            {
               for ( PositionDTO positionDTO : staffPositionDTOs )
               {
                  if ( existModuleByCondition( reportHeaderVO, positionDTO ) )
                  {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   /* Add by Siuxia at 2013-12-17 */
   private boolean existModuleByCondition( final ReportHeaderVO reportHeaderVO, final PositionDTO positionDTO ) throws KANException
   {
      if ( reportHeaderVO == null )
      {
         return false;
      }

      // 当前报表存在权限 - 职级
      if ( KANUtil.filterEmpty( reportHeaderVO.getPositionIds() ) != null )
      {
         if ( positionDTO != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
         {
            for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
            {
               final String[] positionArray = KANUtil.jasonArrayToStringArray( reportHeaderVO.getPositionIds() );

               if ( positionArray != null && positionArray.length > 0 )
               {
                  for ( String positionId : positionArray )
                  {
                     if ( positionId.equals( positionStaffRelationVO.getPositionId() ) )
                     {
                        return true;
                     }
                  }
               }
            }
         }
      }

      // 当前报表存在权限 - 职位组
      if ( KANUtil.filterEmpty( reportHeaderVO.getPositionGroupIds() ) != null )
      {
         if ( positionDTO != null && positionDTO.getPositionGroupRelationVOs().size() > 0 )
         {
            for ( PositionGroupRelationVO positionGroupRelationVO : positionDTO.getPositionGroupRelationVOs() )
            {
               final String[] positionGroupArray = KANUtil.jasonArrayToStringArray( reportHeaderVO.getPositionGroupIds() );

               if ( positionGroupArray != null && positionGroupArray.length > 0 )
               {
                  for ( String positionGroupId : positionGroupArray )
                  {
                     if ( positionGroupId.equals( positionGroupRelationVO.getGroupId() ) )
                     {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   // 初始化临时ModuleVO，附加菜单报表下面
   private ModuleVO getTempModuleVO( final ModuleVO moduleVO, final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      // 初始化ModuleVO
      final ModuleVO tempModuleVO = new ModuleVO();
      tempModuleVO.setNameZH( reportHeaderVO.getNameZH() );
      tempModuleVO.setNameEN( reportHeaderVO.getNameEN() );
      tempModuleVO.setTitleZH( reportHeaderVO.getNameZH() );
      tempModuleVO.setTitleEN( reportHeaderVO.getNameEN() );
      tempModuleVO.setParentModuleId( moduleVO.getModuleId() );
      tempModuleVO.setRemark5( "Report_" + reportHeaderVO.getReportHeaderId() );
      // tempModuleVO.setModuleId(
      // "Report_"+reportHeaderVO.getReportHeaderId() );
      tempModuleVO.setModuleFlag( 1 );

      if ( KANUtil.filterEmpty( moduleVO.getModuleName() ) != null && moduleVO.getModuleName().split( "_" ).length == 3 )
      {
         final String[] menuArray = moduleVO.getModuleName().split( "_" );
         tempModuleVO.setModuleName( menuArray[ 0 ] + "_" + menuArray[ 1 ] + "_" + "Report" + reportHeaderVO.getReportHeaderId() );
      }

      // 关于菜单链接
      // 初始化JSONObject
      final JSONObject jsonObject = new JSONObject();
      jsonObject.put( "id", reportHeaderVO.getEncodedId() );
      jsonObject.put( "moduleName", tempModuleVO.getModuleName() );

      // 如果是直接导出&&非搜索优先
      if ( KANUtil.filterEmpty( reportHeaderVO.getExportExcelType() ) != null && reportHeaderVO.getExportExcelType().trim().equals( "2" )
            && KANUtil.filterEmpty( reportHeaderVO.getIsSearchFirst() ) != null && reportHeaderVO.getIsSearchFirst().trim().equals( "2" ) )
      {
         jsonObject.put( "fileType", "excel" );
         jsonObject.put( "tableId", reportHeaderVO.getTableId() );
         jsonObject.put( "ajax", "true" );
         jsonObject.put( "subAction", "downloadObjects" );
      }

      tempModuleVO.setDefaultAction( jsonObject.toString() );

      return tempModuleVO;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public ReportHeaderService getReportHeaderService()
   {
      return reportHeaderService;
   }

   public void setReportHeaderService( ReportHeaderService reportHeaderService )
   {
      this.reportHeaderService = reportHeaderService;
   }

   @Override
   public List< Object > getActiveStaffVOsByCorpId( final StaffVO staffVO ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getActiveStaffVOsByCorpId( staffVO );
   }

   @Override
   public List< Object > getActiveStaffVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getActiveStaffVOsByAccountId( accountId );
   }

   @Override
   public StaffVO getStaffVOByEmployeeId( String employeeId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getStaffVOByEmployeeId( employeeId );
   }

   // 按照StaffId获得对应的PositionDTO列表 - 递归函数
   private List< PositionDTO > getPositionDTOsByStaffId( final List< PositionDTO > positionDTOs, final String staffId )
   {
      // 当前用户所在的PositionDTO列表
      final List< PositionDTO > matchedPositionDTOs = new ArrayList< PositionDTO >();

      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 遍历当前层级的所有DTO
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // 如果DTO是目标对象，则返回
            if ( positionDTO.containsStaffId( staffId ) )
            {
               matchedPositionDTOs.add( positionDTO );
            }

            // 如果当前DTO的子节点不为空，递归遍历
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               // 返回值直接存入当前PositionDTO列表
               matchedPositionDTOs.addAll( getPositionDTOsByStaffId( positionDTO.getPositionDTOs(), staffId ) );
            }
         }
      }

      return matchedPositionDTOs;
   }

   @Override
   public int insertStaffByEmployee( EmployeeVO employeeVO ) throws KANException
   {
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
      staffVO.setStatus( "1" );
      staffVO.setRemark1( employeeVO.getRemark1() );
      staffVO.setRemark2( employeeVO.getRemark2() );
      staffVO.setRemark3( employeeVO.getRemark3() );
      staffVO.setCreateBy( employeeVO.getCreateBy() );
      staffVO.setCreateDate( new Date() );

      final StaffDao staffDao = ( StaffDao ) getDao();

      return staffDao.insertStaff( staffVO );
   }

   // 获取联系人的 电话(sms) 邮箱(email) userId(系统消息)
   @Override
   public List< Object > getStaffVOsByIds( final String acccountId, final String[] userIds, final String[] positionIds, final String[] groupIds, final String[] branchIds,
         final String[] positionGradeIds ) throws KANException
   {
      final List< Object > staffVOs = new ArrayList< Object >();
      KANAccountConstants kanAccountConstants = KANConstants.getKANAccountConstants( acccountId );
      // userIds
      if ( userIds != null && userIds.length > 0 )
      {
         for ( String userId : userIds )
         {
            final StaffDTO staffDTO = kanAccountConstants.getStaffDTOByUserId( userId );
            staffDTO.getStaffVO().setUserId( userId );
            staffVOs.add( staffDTO.getStaffVO() );
         }
      }
      // positionIds
      if ( positionIds != null && positionIds.length > 0 )
      {
         for ( String positionId : positionIds )
         {
            final List< StaffDTO > tempStaffDTOs = kanAccountConstants.getStaffDTOsByPositionId( positionId );
            for ( StaffDTO staffDTO : tempStaffDTOs )
            {
               staffDTO.getStaffVO().setUserId( staffDTO.getUserVO().getUserId() );
               staffVOs.add( staffDTO.getStaffVO() );
            }
         }
      }
      // groupIds
      if ( groupIds != null && groupIds.length > 0 )
      {
         for ( String groupId : groupIds )
         {
            // 根据职位组ID 得到职位组
            final GroupDTO groupDTO = kanAccountConstants.getGroupDTOByGroupId( groupId );
            // 根据职位组得到职位组和职位的关系
            final List< PositionGroupRelationVO > positionGroupRelationVOs = groupDTO.getPositionGroupRelationVOs();
            for ( PositionGroupRelationVO tempPositionGroupRelationVO : positionGroupRelationVOs )
            {
               // 得到职位
               final String tempPositionId = tempPositionGroupRelationVO.getPositionId();
               // 得到职位上的员工s
               final List< StaffDTO > tempStaffDTOs = kanAccountConstants.getStaffDTOsByPositionId( tempPositionId );
               for ( StaffDTO staffDTO : tempStaffDTOs )
               {
                  staffDTO.getStaffVO().setUserId( staffDTO.getUserVO().getUserId() );
                  staffVOs.add( staffDTO.getStaffVO() );
               }
            }
         }
      }
      // branchIds
      if ( branchIds != null && branchIds.length > 0 )
      {
         for ( String branchId : branchIds )
         {
            List< PositionVO > positionVOs = kanAccountConstants.getPositionVOsByBranchId( branchId );
            for ( PositionVO tempPositionVO : positionVOs )
            {
               final List< StaffDTO > tempStaffDTOs = kanAccountConstants.getStaffDTOsByPositionId( tempPositionVO.getPositionId() );
               for ( StaffDTO staffDTO : tempStaffDTOs )
               {
                  staffDTO.getStaffVO().setUserId( staffDTO.getUserVO().getUserId() );
                  staffVOs.add( staffDTO.getStaffVO() );
               }
            }
         }
      }
      // positionGradeIds
      if ( positionGradeIds != null && positionGradeIds.length > 0 )
      {
         for ( String positionGradeId : positionGradeIds )
         {
            final PositionDTO positionDTO = kanAccountConstants.getPositionDTOByPositionGradeId( positionGradeId );
            final List< StaffDTO > tempStaffDTOs = kanAccountConstants.getStaffDTOsByPositionId( positionDTO.getPositionVO().getPositionId() );
            for ( StaffDTO staffDTO : tempStaffDTOs )
            {
               staffDTO.getStaffVO().setUserId( staffDTO.getUserVO().getUserId() );
               staffVOs.add( staffDTO.getStaffVO() );
            }
         }
      }

      return staffVOs;
   }

   @Override
   public PagedListHolder getStaffVOsByBranchId( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final StaffDao staffDao = ( StaffDao ) getDao();
      pagedListHolder.setHolderSize( staffDao.getCountStaffVOsByBranchId( ( String ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( staffDao.getStaffVOsByBranchId( ( String ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( staffDao.getStaffVOsByBranchId( ( String ) pagedListHolder.getObject() ) );
      }
      final String staffIds = staffObjects2StaffIds( pagedListHolder.getSource() );
      PositionVO tempPositionVO = new PositionVO();
      tempPositionVO.setStaffIds( staffIds );
      tempPositionVO.setBranchId( ( String ) pagedListHolder.getObject() );
      List< Object > positionVOObjects = positionDao.getPositionVOsByStaffIds( tempPositionVO );
      for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
      {
         final StaffVO staffVO = ( StaffVO ) pagedListHolder.getSource().get( i );
         String pTitle = "";
         for ( Object objPosition : positionVOObjects )
         {
            final PositionVO positionVO = ( PositionVO ) objPosition;
            if ( staffVO.getStaffId().equals( positionVO.getStaffId() ) )
            {
               pTitle += positionVO.getTitleZH();
               pTitle += "; ";
            }
         }
         pTitle = KANUtil.filterEmpty( pTitle ) != null ? pTitle.substring( 0, pTitle.length() - 2 ) : "";
         ( ( StaffVO ) pagedListHolder.getSource().get( i ) ).setpTitle( pTitle.length() > 20 ? pTitle.substring( 0, 20 ) + "..." : pTitle );
      }
      return pagedListHolder;
   }

   // 从内存获取
   public PagedListHolder getStaffVOsByBranchId( final String branchId, final PagedListHolder pagedListHolder, final boolean isPaged, final String isSumSubBranchHC,
         final String accountId ) throws KANException
   {
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      List< String > branchStaffIds = new ArrayList< String >();
      final BranchDTO branchDTO = accountConstants.getBranchDTOByBranchId( branchId );
      if ( "1".equals( isSumSubBranchHC ) )
      {
         final List< BranchVO > branchVOs = getAllBranchVO( branchDTO );
         for ( BranchVO branchVO : branchVOs )
         {
            branchStaffIds.addAll( accountConstants.getStaffIdsInBranch( branchVO.getBranchId() ) );
         }
      }
      else
      {
         // 不统计子部门
         branchStaffIds.addAll( accountConstants.getStaffIdsInBranch( branchDTO.getBranchVO().getBranchId() ) );
      }
      branchStaffIds = KANUtil.getDistinctList( branchStaffIds );
      final List< Object > staffVOs = new ArrayList< Object >();
      for ( String id : branchStaffIds )
      {
         final StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( id );
         if ( staffDTO != null )
         {
            staffVOs.add( staffDTO.getStaffVO() );
         }
      }

      pagedListHolder.setHolderSize( staffVOs.size() );

      if ( isPaged )
      {

         for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() ) && i < staffVOs.size(); i++ )
         {
            pagedListHolder.getSource().add( staffVOs.get( i ) );
         }

      }
      else
      {
         pagedListHolder.setSource( staffVOs );
      }
      final String staffIds = staffObjects2StaffIds( pagedListHolder.getSource() );
      PositionVO tempPositionVO = new PositionVO();
      tempPositionVO.setStaffIds( staffIds );
      tempPositionVO.setBranchId( ( String ) pagedListHolder.getObject() );
      List< Object > positionVOObjects = positionDao.getPositionVOsByStaffIds( tempPositionVO );
      for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
      {
         final StaffVO staffVO = ( StaffVO ) pagedListHolder.getSource().get( i );
         String pTitle = "";
         String positionGrade = "";
         for ( Object objPosition : positionVOObjects )
         {
            final PositionVO positionVO = ( PositionVO ) objPosition;
            if ( staffVO.getStaffId().equals( positionVO.getStaffId() ) )
            {
               if ( KANUtil.filterEmpty( positionVO.getTitleZH() ) != null )
               {
                  pTitle += positionVO.getTitleZH();
                  pTitle += "; ";
               }
               if ( KANUtil.filterEmpty( positionVO.getDecodePositionGradeId() ) != null )
               {
                  positionGrade += positionVO.getDecodePositionGradeId();
                  positionGrade += ";";
               }
            }
         }
         pTitle = KANUtil.filterEmpty( pTitle ) != null ? pTitle.substring( 0, pTitle.length() - 2 ) : "";
         positionGrade = KANUtil.filterEmpty( positionGrade ) != null ? positionGrade.substring( 0, positionGrade.length() - 2 ) : "";
         ( ( StaffVO ) pagedListHolder.getSource().get( i ) ).setpTitle( pTitle.length() > 20 ? pTitle.substring( 0, 20 ) + "..." : pTitle );
         ( ( StaffVO ) pagedListHolder.getSource().get( i ) ).setPositionGrade( positionGrade.length() > 20 ? positionGrade.substring( 0, 20 ) + "..." : positionGrade );
      }
      return pagedListHolder;
   }

   private List< BranchVO > getAllBranchVO( BranchDTO branchDTO )
   {
      final List< BranchVO > branchVOs = new ArrayList< BranchVO >();
      if ( branchDTO != null )
      {
         branchVOs.add( branchDTO.getBranchVO() );
      }
      if ( branchDTO != null && branchDTO.getBranchDTOs().size() > 0 )
      {
         for ( BranchDTO tempDTO : branchDTO.getBranchDTOs() )
         {
            branchVOs.addAll( getAllBranchVO( tempDTO ) );
         }
      }
      return branchVOs;
   }

   @Override
   public StaffBaseView getStaffBaseViewByStaffId( String staffId ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).getStaffBaseViewByStaffId( staffId );
   }

   private String staffObjects2StaffIds( List< Object > staffVOs )
   {
      String result = "";
      StaffVO staffVO;
      for ( Object obj : staffVOs )
      {
         staffVO = ( StaffVO ) obj;
         result += staffVO.getStaffId();
         result += ",";
      }
      return KANUtil.filterEmpty( result ) != null ? result.substring( 0, result.length() - 1 ) : "";
   }

   @Override
   public int updateBaseStaff( StaffVO staffVO ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).updateStaff( staffVO );
   }

   @Override
   public List< Object > logon( final StaffVO staffVO ) throws KANException
   {
      return ( ( StaffDao ) getDao() ).logon( staffVO );
   }
}
