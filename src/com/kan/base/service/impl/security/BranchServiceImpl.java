package com.kan.base.service.impl.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.BranchDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.BranchService;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class BranchServiceImpl extends ContextService implements BranchService
{

   private EmployeeDao employeeDao;

   private EmployeePositionChangeDao employeePositionChangeDao;

   private LogDao logDao;

   private PositionDao positionDao;

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   public EmployeePositionChangeDao getEmployeePositionChangeDao()
   {
      return employeePositionChangeDao;
   }

   public void setEmployeePositionChangeDao( EmployeePositionChangeDao employeePositionChangeDao )
   {
      this.employeePositionChangeDao = employeePositionChangeDao;
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
   public PagedListHolder getBranchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BranchDao branchDao = ( BranchDao ) getDao();
      pagedListHolder.setHolderSize( branchDao.countBranchVOsByCondition( ( BranchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( branchDao.getBranchVOsByCondition( ( BranchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( branchDao.getBranchVOsByCondition( ( BranchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public BranchVO getBranchVOByBranchId( final String branchId ) throws KANException
   {
      return ( ( BranchDao ) getDao() ).getBranchVOByBranchId( branchId );
   }

   @Override
   public int updateBranch( final BranchVO branchVO ) throws KANException
   {
      return ( ( BranchDao ) getDao() ).updateBranch( branchVO );
   }

   @Override
   public int insertBranch( final BranchVO branchVO ) throws KANException
   {
      return ( ( BranchDao ) getDao() ).insertBranch( branchVO );
   }

   @Override
   public int deleteBranch( final BranchVO branchVO ) throws KANException
   {
      return ( ( BranchDao ) getDao() ).deleteBranch( branchVO );
   }

   @Override
   public List< Object > getBranchVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( BranchDao ) getDao() ).getBranchVOsByAccountId( accountId );
   }

   @Override
   public List< BranchDTO > getBranchDTOsByAccountId( String accountId ) throws KANException
   {

      final List< BranchDTO > branchDTOs = new ArrayList< BranchDTO >();

      BranchDao dao = ( BranchDao ) getDao();

      BranchVO branchVO = new BranchVO();
      branchVO.setAccountId( accountId );
      branchVO.setParentBranchId( "0" );

      List< Object > rootBranchVOs = dao.getBranchVOsByParentBranchId( branchVO );

      for ( Object obj : rootBranchVOs )
      {
         final List< String > staffIds = KANConstants.getKANAccountConstants( accountId ).getStaffIdsInBranch( ( ( BranchVO ) obj ).getBranchId() );
         ( ( BranchVO ) obj ).setStaffIdsInBranch( staffIds );
         // 递归遍历
         branchDTOs.add( fetchBranchDTO( ( BranchVO ) obj ) );
      }

      return branchDTOs;
   }

   // 递归方法
   private BranchDTO fetchBranchDTO( final BranchVO branchVO ) throws KANException
   {
      final BranchDTO branchDTO = new BranchDTO();
      // 设置PositionVO对象
      branchDTO.setBranchVO( branchVO );

      // 继续查找下一层Position
      final BranchVO subBranchVO = new BranchVO();
      subBranchVO.setAccountId( branchVO.getAccountId() );
      subBranchVO.setParentBranchId( branchVO.getBranchId() );
      final List< Object > subBranchVOs = ( ( BranchDao ) getDao() ).getBranchVOsByParentBranchId( subBranchVO );

      for ( Object subBranchObject : subBranchVOs )
      {
         final List< String > staffIds = KANConstants.getKANAccountConstants( branchVO.getAccountId() ).getStaffIdsInBranch( ( ( BranchVO ) subBranchObject ).getBranchId() );
         ( ( BranchVO ) subBranchObject ).setStaffIdsInBranch( staffIds );
         // 递归调用
         branchDTO.getBranchDTOs().add( fetchBranchDTO( ( BranchVO ) subBranchObject ) );
      }

      return branchDTO;
   }

   @Override
   public List< Object > getBUFuction()
   {
      return ( ( BranchDao ) getDao() ).getBUFuction();
   }

   @Override
   // 复制架构图
   public Set< String > copyO_Chart( final Locale locale, final Map< String, String > parameterMap ) throws KANException
   {
      // 同步微信用到
      final Set< String > employeeIds = new HashSet< String >();

      try
      {
         // 开启事务
         startTransaction();

         // 获取KANAccountConstants
         final KANAccountConstants constants = KANConstants.getKANAccountConstants( parameterMap.get( "accountId" ) );

         // 获取待复制目标BranchDTO
         final BranchDTO branchDTO = constants.getBranchDTOByBranchId( parameterMap.get( "parentBranchId" ) );

         if ( branchDTO != null && branchDTO.getBranchVO() != null )
         {
            // 替换参数parentBranchId
            parameterMap.put( "parentBranchId", "0" );
            // 先复制第一级newParentBranchVO
            final BranchVO newParentBranchVO = dealBranchTransform( branchDTO.getBranchVO(), constants, parameterMap, locale, employeeIds );
            // 替换参数parentBranchId
            parameterMap.put( "parentBranchId", newParentBranchVO.getBranchId() );
            // 然后再复制subBranchVOs
            copySubBranchDTOs( branchDTO.getBranchDTOs(), constants, parameterMap, locale, employeeIds );
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return employeeIds;
   }

   // 复制组织架构 - 递归 - 复制SubBranchDTOs
   private void copySubBranchDTOs( final List< BranchDTO > branchDTOs, final KANAccountConstants constants, final Map< String, String > parameterMap, final Locale locale,
         final Set< String > employeeIds ) throws KANException
   {
      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         for ( BranchDTO tempBranchDTO : branchDTOs )
         {
            final BranchVO parentBranchVO = dealBranchTransform( tempBranchDTO.getBranchVO(), constants, parameterMap, locale, employeeIds );

            if ( tempBranchDTO != null && tempBranchDTO.getBranchDTOs() != null && tempBranchDTO.getBranchDTOs().size() > 0 )
            {
               // 替换参数parentBranchId
               parameterMap.put( "parentBranchId", parentBranchVO.getBranchId() );
               copySubBranchDTOs( tempBranchDTO.getBranchDTOs(), constants, parameterMap, locale, employeeIds );
            }
         }
      }
   }

   // 复制组织架构 - 处理部门转换
   // 1、insert新的部门
   // 2、insert异动记录
   // 3、insert异动日志
   // 4、update职位新部门
   private BranchVO dealBranchTransform( final BranchVO oldBranchVO, final KANAccountConstants constants, final Map< String, String > parameterMap, final Locale locale,
         final Set< String > employeeIds ) throws KANException
   {
      // 获取新部门的中英名字
      final String nameArray[] = parameterMap.get( oldBranchVO.getBranchId() ).split( "_" );
      final String userId = parameterMap.get( "userId" );

      // 1、insert新的部门
      final BranchVO newBranchVO = new BranchVO();
      newBranchVO.setAccountId( oldBranchVO.getAccountId() );
      newBranchVO.setCorpId( oldBranchVO.getCorpId() );
      newBranchVO.setParentBranchId( parameterMap.get( "parentBranchId" ) );
      newBranchVO.setEntityId( oldBranchVO.getEntityId() );
      newBranchVO.setBusinessTypeId( oldBranchVO.getBusinessTypeId() );
      newBranchVO.setBranchCode( oldBranchVO.getBranchCode() );
      newBranchVO.setNameZH( nameArray[ 0 ] );
      newBranchVO.setNameEN( nameArray[ 1 ] );
      newBranchVO.setDescription( "Copy o-chart" );
      newBranchVO.setStatus( "1" );
      newBranchVO.setCreateBy( userId );
      newBranchVO.setCreateDate( new Date() );
      newBranchVO.setModifyBy( userId );
      newBranchVO.setModifyDate( new Date() );

      ( ( BranchDao ) getDao() ).insertBranch( newBranchVO );

      // 2、insert异动记录
      final List< PositionVO > positionVOs = constants.getPositionVOsByBranchId( oldBranchVO.getBranchId() );
      if ( positionVOs != null && positionVOs.size() > 0 )
      {
         for ( PositionVO positionVO : positionVOs )
         {
            final List< StaffDTO > staffDTOs = constants.getStaffDTOsByPositionId( positionVO.getPositionId() );
            if ( staffDTOs != null && staffDTOs.size() > 0 )
            {
               for ( StaffDTO staffDTO : staffDTOs )
               {
                  final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( staffDTO.getStaffVO().getEmployeeId() );
                  final PositionStaffRelationVO relationVO = staffDTO.getPositionStaffRelationVOByPositionId( positionVO.getPositionId() );
                  if ( employeeVO != null && relationVO != null )
                  {
                     final EmployeePositionChangeVO tempEmployeePositionChangeVO = new EmployeePositionChangeVO();
                     tempEmployeePositionChangeVO.setLocale( locale );
                     tempEmployeePositionChangeVO.setAccountId( employeeVO.getAccountId() );
                     tempEmployeePositionChangeVO.setCorpId( employeeVO.getCorpId() );
                     tempEmployeePositionChangeVO.setEmployeeId( employeeVO.getEmployeeId() );
                     tempEmployeePositionChangeVO.setEmployeeNo( employeeVO.getEmployeeNo() );
                     tempEmployeePositionChangeVO.setEmployeeNameZH( employeeVO.getNameZH() );
                     tempEmployeePositionChangeVO.setEmployeeNameEN( employeeVO.getNameEN() );
                     tempEmployeePositionChangeVO.setEmployeeCertificateNumber( employeeVO.getCertificateNumber() );
                     tempEmployeePositionChangeVO.setStaffId( staffDTO.getStaffVO().getStaffId() );
                     tempEmployeePositionChangeVO.setOldStaffPositionRelationId( relationVO.getRelationId() );
                     tempEmployeePositionChangeVO.setEffectiveDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
                     tempEmployeePositionChangeVO.setIsChildChange( "2" );
                     tempEmployeePositionChangeVO.setStatus( "5" );// 已生效
                     tempEmployeePositionChangeVO.setSubmitFlag( 4 );
                     tempEmployeePositionChangeVO.setDescription( "Copy o-chart" );
                     tempEmployeePositionChangeVO.setRemark3( "3" );
                     tempEmployeePositionChangeVO.setCreateBy( userId );
                     tempEmployeePositionChangeVO.setCreateDate( new Date() );
                     tempEmployeePositionChangeVO.setModifyBy( userId );
                     tempEmployeePositionChangeVO.setModifyDate( new Date() );

                     // Department old & new
                     tempEmployeePositionChangeVO.setOldBranchId( oldBranchVO.getBranchId() );
                     tempEmployeePositionChangeVO.setOldBranchNameZH( oldBranchVO.getNameZH() );
                     tempEmployeePositionChangeVO.setOldBranchNameEN( oldBranchVO.getNameEN() );
                     tempEmployeePositionChangeVO.setNewBranchId( newBranchVO.getBranchId() );
                     tempEmployeePositionChangeVO.setNewBranchNameZH( newBranchVO.getNameZH() );
                     tempEmployeePositionChangeVO.setNewBranchNameEN( newBranchVO.getNameEN() );

                     // BU/Function old & new
                     final BranchVO oldParentBranchVO = constants.getBranchVOByBranchId( oldBranchVO.getParentBranchId() );
                     tempEmployeePositionChangeVO.setOldParentBranchId( oldBranchVO.getParentBranchId() );
                     if ( oldParentBranchVO != null )
                     {
                        tempEmployeePositionChangeVO.setOldParentBranchNameZH( oldParentBranchVO.getNameZH() );
                        tempEmployeePositionChangeVO.setOldParentBranchNameEN( oldParentBranchVO.getNameEN() );
                     }
                     final BranchVO newParentBranchVO = ( ( BranchDao ) getDao() ).getBranchVOByBranchId( newBranchVO.getParentBranchId() );
                     tempEmployeePositionChangeVO.setNewParentBranchId( newBranchVO.getParentBranchId() );
                     if ( newParentBranchVO != null )
                     {
                        tempEmployeePositionChangeVO.setNewParentBranchNameZH( newParentBranchVO.getNameZH() );
                        tempEmployeePositionChangeVO.setNewParentBranchNameEN( newParentBranchVO.getNameEN() );
                     }

                     // Working title old & new
                     tempEmployeePositionChangeVO.setOldPositionId( positionVO.getPositionId() );
                     tempEmployeePositionChangeVO.setOldPositionNameZH( positionVO.getTitleZH() );
                     tempEmployeePositionChangeVO.setOldPositionNameEN( positionVO.getTitleEN() );
                     tempEmployeePositionChangeVO.setNewPositionId( positionVO.getPositionId() );
                     tempEmployeePositionChangeVO.setNewPositionNameZH( positionVO.getTitleZH() );
                     tempEmployeePositionChangeVO.setNewPositionNameEN( positionVO.getTitleEN() );

                     // Leader Working title old & new
                     tempEmployeePositionChangeVO.setOldParentPositionId( positionVO.getParentPositionId() );
                     tempEmployeePositionChangeVO.setNewParentPositionId( positionVO.getParentPositionId() );
                     final PositionVO oldParentPositionVO = constants.getPositionVOByPositionId( positionVO.getParentPositionId() );
                     if ( oldParentPositionVO != null )
                     {
                        tempEmployeePositionChangeVO.setOldParentPositionNameZH( oldParentPositionVO.getTitleZH() );
                        tempEmployeePositionChangeVO.setOldParentPositionNameEN( oldParentPositionVO.getTitleEN() );
                        tempEmployeePositionChangeVO.setNewParentPositionNameZH( oldParentPositionVO.getTitleZH() );
                        tempEmployeePositionChangeVO.setNewParentPositionNameEN( oldParentPositionVO.getTitleEN() );
                     }

                     // Direct Report Manager old & new
                     tempEmployeePositionChangeVO.setOldParentPositionOwnersZH( constants.getStaffNamesByPositionId( "zh", positionVO.getParentPositionId() ) );
                     tempEmployeePositionChangeVO.setOldParentPositionOwnersEN( constants.getStaffNamesByPositionId( "en", positionVO.getParentPositionId() ) );
                     tempEmployeePositionChangeVO.setNewParentPositionOwnersZH( constants.getStaffNamesByPositionId( "zh", positionVO.getParentPositionId() ) );
                     tempEmployeePositionChangeVO.setNewParentPositionOwnersEN( constants.getStaffNamesByPositionId( "en", positionVO.getParentPositionId() ) );

                     // Job Grade old & new
                     tempEmployeePositionChangeVO.setOldPositionGradeId( positionVO.getPositionGradeId() );
                     tempEmployeePositionChangeVO.setNewPositionGradeId( positionVO.getPositionGradeId() );
                     final PositionGradeVO oldPositionGradeVO = constants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );
                     if ( oldPositionGradeVO != null )
                     {
                        tempEmployeePositionChangeVO.setOldPositionGradeNameZH( oldPositionGradeVO.getGradeNameZH() );
                        tempEmployeePositionChangeVO.setOldPositionGradeNameEN( oldPositionGradeVO.getGradeNameEN() );
                        tempEmployeePositionChangeVO.setNewPositionGradeNameZH( oldPositionGradeVO.getGradeNameZH() );
                        tempEmployeePositionChangeVO.setNewPositionGradeNameEN( oldPositionGradeVO.getGradeNameEN() );
                     }

                     // 插入异动
                     this.getEmployeePositionChangeDao().insertEmployeePositionChange( tempEmployeePositionChangeVO );

                     // 部门改变
                     employeeVO.set_tempBranchIds( newBranchVO.getBranchId() );
                     employeeVO.set_tempParentBranchIds( newBranchVO.getParentBranchId() );
                     employeeVO.setModifyBy( userId );
                     employeeVO.setModifyDate( new Date() );
                     this.getEmployeeDao().updateEmployee( employeeVO );

                     // 微信同步
                     employeeIds.add( employeeVO.getEmployeeId() );

                     // 3、insert异动日志
                     insertLog( tempEmployeePositionChangeVO, parameterMap.get( "ip" ), parameterMap.get( "createBy" ) );

                     // 4、update职位新部门
                     positionVO.setBranchId( newBranchVO.getBranchId() );
                     this.getPositionDao().updatePosition( positionVO );
                  }
               }
            }
         }
      }

      return newBranchVO;
   }

   // 插入日志
   private void insertLog( final EmployeePositionChangeVO employeePositionChangeVO, final String ip, final String createBy ) throws KANException
   {
      final LogVO log = new LogVO();
      log.setEmployeeId( employeePositionChangeVO.getEmployeeId() );
      log.setChangeReason( employeePositionChangeVO.getRemark3() );
      log.setEmployeeNameZH( employeePositionChangeVO.getEmployeeNameZH() );
      log.setEmployeeNameEN( employeePositionChangeVO.getEmployeeNameEN() );
      log.setType( String.valueOf( Operate.SUBMIT.getIndex() ) );
      log.setModule( EmployeePositionChangeVO.class.getCanonicalName() );
      log.setContent( employeePositionChangeVO == null ? "" : JsonMapper.toLogJson( employeePositionChangeVO ) );
      log.setIp( ip );
      log.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
      log.setOperateBy( createBy );
      log.setpKey( employeePositionChangeVO.getPositionChangeId() );
      log.setRemark( null );

      this.getLogDao().insertLog( log );
   }
}
