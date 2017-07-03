package com.kan.hro.service.impl.biz.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeService;
import com.kan.hro.web.actions.biz.employee.EmployeePositionChangeAction;

public class EmployeePositionChangeServiceImpl extends ContextService implements EmployeePositionChangeService
{
   private EmployeePositionChangeDao employeePositionChangeDao;

   private PositionStaffRelationDao positionStaffRelationDao;

   private EmployeeDao employeeDao;

   private EmployeeContractDao employeeContractDao;

   private PositionDao positionDao;

   @Override
   public void getPositionChangeVOsByCondition( final PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( employeePositionChangeDao.countEmployeePositionChangeVOsByCondition( ( EmployeePositionChangeVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeePositionChangeDao.getEmployeePositionChangeVOsByCondition( ( EmployeePositionChangeVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeePositionChangeDao.getEmployeePositionChangeVOsByCondition( ( EmployeePositionChangeVO ) pagedListHolder.getObject() ) );
      }
   }

   @Override
   public EmployeePositionChangeVO getEmployeePositionChangeVOByPositionChangeId( final String positionChangeId ) throws KANException
   {
      return employeePositionChangeDao.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
   }

   @Override
   public void insertEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         String effectiveDate = employeePositionChangeVO.getEffectiveDate();
         SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
         Date newDate = sdf.parse( effectiveDate );
         Calendar calendar = Calendar.getInstance();
         calendar.setTime( newDate );
         calendar.add( Calendar.DATE, -1 );

         employeePositionChangeVO.setStatus( "1" );

         //新增加职位
         if ( StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" ) && !StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" )
               && StringUtils.isNotEmpty( employeePositionChangeVO.getNewPositionId() ) )
         {
            employeePositionChangeVO.setOldStartDate( null );
            employeePositionChangeVO.setOldEndDate( null );
            employeePositionChangeVO.setNewStartDate( effectiveDate );
            employeePositionChangeVO.setNewEndDate( null );
         }

         //修改职位
         if ( !StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" ) && !StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" )
               && StringUtils.isNotEmpty( employeePositionChangeVO.getNewPositionId() ) )
         {
            PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
            positionStaffRelationVO.setStaffId( employeePositionChangeVO.getStaffId() );
            positionStaffRelationVO.setPositionId( employeePositionChangeVO.getOldPositionId() );
            PositionStaffRelationVO positionStaffRelation = positionStaffRelationDao.getPositionStaffRelationVOByStaffAndPositionId( positionStaffRelationVO );

            employeePositionChangeVO.setOldStartDate( KANUtil.filterEmpty( positionStaffRelation.decodeDate( positionStaffRelation.getCreateDate() ) ) );
            employeePositionChangeVO.setOldEndDate( sdf.format( calendar.getTime() ) );
            employeePositionChangeVO.setNewStartDate( effectiveDate );
            employeePositionChangeVO.setNewEndDate( null );
         }

         //删除职位
         if ( !StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" )
               && ( StringUtils.isEmpty( employeePositionChangeVO.getNewPositionId() ) || StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" ) ) )
         {
            PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
            positionStaffRelationVO.setStaffId( employeePositionChangeVO.getStaffId() );
            positionStaffRelationVO.setPositionId( employeePositionChangeVO.getOldPositionId() );
            PositionStaffRelationVO positionStaffRelation = positionStaffRelationDao.getPositionStaffRelationVOByStaffAndPositionId( positionStaffRelationVO );

            employeePositionChangeVO.setOldStartDate( KANUtil.filterEmpty( positionStaffRelation.decodeDate( positionStaffRelation.getCreateDate() ) ) );
            employeePositionChangeVO.setOldEndDate( sdf.format( calendar.getTime() ) );
            employeePositionChangeVO.setNewStartDate( null );
            employeePositionChangeVO.setNewEndDate( null );
            employeePositionChangeVO.setNewBranchId( "0" );
            employeePositionChangeVO.setNewPositionId( "0" );
         }
         // 插入其他信息
         if ( 3 != employeePositionChangeVO.getSubmitFlag() && 4 != employeePositionChangeVO.getSubmitFlag() )
         {
            setChangeInfo( employeePositionChangeVO );
         }
         employeePositionChangeDao.insertEmployeePositionChange( employeePositionChangeVO );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   /**
    * 插入其他的基本信息
    * @param employeePositionChangeVO
    * ,
      ,,,,
      ,,,,,
      ,,,,,
      ,employeeNo,employeeNameZH,employeeNameEN,employeeCertificateNumber
   */
   private void setChangeInfo( EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeePositionChangeVO.getEmployeeId() );
      employeePositionChangeVO.setEmployeeNo( employeeVO.getEmployeeNo() );
      employeePositionChangeVO.setEmployeeNameZH( employeeVO.getNameZH() );
      employeePositionChangeVO.setEmployeeNameEN( employeeVO.getNameEN() );
      employeePositionChangeVO.setEmployeeCertificateNumber( employeeVO.getCertificateNumber() );

      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( employeePositionChangeVO.getAccountId() );

      //old
      final String oldBranchId = employeePositionChangeVO.getOldBranchId();
      if ( KANUtil.filterEmpty( oldBranchId ) != null )
      {
         final BranchVO oldBranchVO = accountConstants.getBranchVOByBranchId( oldBranchId );
         if ( oldBranchVO != null )
         {
            employeePositionChangeVO.setOldBranchNameZH( oldBranchVO.getNameZH() );
            employeePositionChangeVO.setOldBranchNameEN( oldBranchVO.getNameEN() );
            final String oldParentBranchId = oldBranchVO.getParentBranchId();
            final BranchVO oldParentBranchVO = accountConstants.getBranchVOByBranchId( oldParentBranchId );
            if ( oldParentBranchVO != null )
            {
               employeePositionChangeVO.setOldParentBranchId( oldParentBranchId );
               employeePositionChangeVO.setOldParentBranchNameZH( oldParentBranchVO.getNameZH() );
               employeePositionChangeVO.setOldParentBranchNameEN( oldParentBranchVO.getNameEN() );
            }
         }
      }

      final String oldPositionId = employeePositionChangeVO.getOldPositionId();
      if ( KANUtil.filterEmpty( oldPositionId ) != null )
      {
         final PositionVO oldPositionVO = accountConstants.getPositionVOByPositionId( oldPositionId );
         if ( oldPositionVO != null )
         {
            employeePositionChangeVO.setOldPositionNameZH( oldPositionVO.getTitleZH() );
            employeePositionChangeVO.setOldPositionNameEN( oldPositionVO.getTitleEN() );
            final String oldParentPositionId = oldPositionVO.getParentPositionId();
            final PositionVO oldParentPositionVO = accountConstants.getPositionVOByPositionId( oldParentPositionId );
            if ( oldParentPositionVO != null )
            {
               //oldParentPositionId,oldParentPositionNameZH,oldParentPositionNameEN
               employeePositionChangeVO.setOldParentPositionId( oldParentPositionId );
               employeePositionChangeVO.setOldParentPositionNameZH( oldParentPositionVO.getTitleZH() );
               employeePositionChangeVO.setOldParentPositionNameEN( oldParentPositionVO.getTitleEN() );
               employeePositionChangeVO.setOldParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", oldParentPositionId ) );
               employeePositionChangeVO.setOldParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", oldParentPositionId ) );
            }

            final String oldPositionGradeId = oldPositionVO.getPositionGradeId();
            final PositionGradeVO oldPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( oldPositionGradeId );
            if ( oldPositionGradeVO != null )
            {
               //oldPositionGradeId,oldPositionGradeNameZH,oldPositionGradeNameEN,
               employeePositionChangeVO.setOldPositionGradeId( oldPositionGradeId );
               employeePositionChangeVO.setOldPositionGradeNameZH( oldPositionGradeVO.getGradeNameZH() );
               employeePositionChangeVO.setOldPositionGradeNameEN( oldPositionGradeVO.getGradeNameEN() );
            }

         }
      }

      // end    old
      //new
      final String newBranchId = employeePositionChangeVO.getNewBranchId();
      if ( KANUtil.filterEmpty( newBranchId ) != null )
      {
         final BranchVO newBranchVO = accountConstants.getBranchVOByBranchId( newBranchId );
         if ( newBranchVO != null )
         {
            employeePositionChangeVO.setNewBranchNameZH( newBranchVO.getNameZH() );
            employeePositionChangeVO.setNewBranchNameEN( newBranchVO.getNameEN() );
            final String newParentBranchId = newBranchVO.getParentBranchId();
            final BranchVO newParentBranchVO = accountConstants.getBranchVOByBranchId( newParentBranchId );
            if ( newParentBranchVO != null )
            {
               employeePositionChangeVO.setNewParentBranchId( newParentBranchId );
               employeePositionChangeVO.setNewParentBranchNameZH( newParentBranchVO.getNameZH() );
               employeePositionChangeVO.setNewParentBranchNameEN( newParentBranchVO.getNameEN() );
            }
         }
      }

      final String newPositionId = employeePositionChangeVO.getNewPositionId();
      if ( KANUtil.filterEmpty( newPositionId ) != null )
      {
         final PositionVO newPositionVO = accountConstants.getPositionVOByPositionId( newPositionId );
         if ( newPositionVO != null )
         {
            employeePositionChangeVO.setNewPositionNameZH( newPositionVO.getTitleZH() );
            employeePositionChangeVO.setNewPositionNameEN( newPositionVO.getTitleEN() );
            final String newParentPositionId = newPositionVO.getParentPositionId();
            final PositionVO newParentPositionVO = accountConstants.getPositionVOByPositionId( newParentPositionId );
            if ( newParentPositionVO != null )
            {
               employeePositionChangeVO.setNewParentPositionId( newParentPositionId );
               employeePositionChangeVO.setNewParentPositionNameZH( newParentPositionVO.getTitleZH() );
               employeePositionChangeVO.setNewParentPositionNameEN( newParentPositionVO.getTitleEN() );
               employeePositionChangeVO.setNewParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", newParentPositionId ) );
               employeePositionChangeVO.setNewParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", newParentPositionId ) );
            }

            final String newPositionGradeId = newPositionVO.getPositionGradeId();
            final PositionGradeVO newPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( newPositionGradeId );
            if ( newPositionGradeVO != null )
            {
               employeePositionChangeVO.setNewPositionGradeId( newPositionGradeId );
               employeePositionChangeVO.setNewPositionGradeNameZH( newPositionGradeVO.getGradeNameZH() );
               employeePositionChangeVO.setNewPositionGradeNameEN( newPositionGradeVO.getGradeNameEN() );
            }

         }
      }

      // end    old
   }

   @Override
   public void updateEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         String effectiveDate = employeePositionChangeVO.getEffectiveDate();
         SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
         Date newDate = sdf.parse( effectiveDate );
         Calendar calendar = Calendar.getInstance();
         calendar.setTime( newDate );
         calendar.add( Calendar.DATE, -1 );
         //新增加职位
         if ( StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" ) && !StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" )
               && StringUtils.isNotEmpty( employeePositionChangeVO.getNewPositionId() ) )
         {
            employeePositionChangeVO.setOldStartDate( null );
            employeePositionChangeVO.setOldEndDate( null );
            employeePositionChangeVO.setNewStartDate( effectiveDate );
            employeePositionChangeVO.setNewEndDate( null );
         }

         //修改职位
         if ( !StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" ) && !StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" )
               && StringUtils.isNotEmpty( employeePositionChangeVO.getNewPositionId() ) )
         {
            PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
            positionStaffRelationVO.setStaffId( employeePositionChangeVO.getStaffId() );
            positionStaffRelationVO.setPositionId( employeePositionChangeVO.getOldPositionId() );
            PositionStaffRelationVO positionStaffRelation = positionStaffRelationDao.getPositionStaffRelationVOByStaffAndPositionId( positionStaffRelationVO );

            employeePositionChangeVO.setOldStartDate( KANUtil.filterEmpty( positionStaffRelation.decodeDate( positionStaffRelation.getModifyDate() ) ) );
            employeePositionChangeVO.setOldEndDate( sdf.format( calendar.getTime() ) );
            employeePositionChangeVO.setNewStartDate( effectiveDate );
            employeePositionChangeVO.setNewEndDate( null );
         }

         //删除职位
         if ( !StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" )
               && ( StringUtils.isEmpty( employeePositionChangeVO.getNewPositionId() ) || StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" ) ) )
         {
            PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
            positionStaffRelationVO.setStaffId( employeePositionChangeVO.getStaffId() );
            positionStaffRelationVO.setPositionId( employeePositionChangeVO.getOldPositionId() );
            PositionStaffRelationVO positionStaffRelation = positionStaffRelationDao.getPositionStaffRelationVOByStaffAndPositionId( positionStaffRelationVO );

            employeePositionChangeVO.setOldStartDate( KANUtil.filterEmpty( positionStaffRelation.decodeDate( positionStaffRelation.getModifyDate() ) ) );
            employeePositionChangeVO.setOldEndDate( sdf.format( calendar.getTime() ) );
            employeePositionChangeVO.setNewStartDate( null );
            employeePositionChangeVO.setNewEndDate( null );
            employeePositionChangeVO.setNewBranchId( "0" );
            employeePositionChangeVO.setNewPositionId( "0" );
         }
         // 插入其他信息
         if ( 3 != employeePositionChangeVO.getSubmitFlag() && 4 != employeePositionChangeVO.getSubmitFlag() )
         {
            setChangeInfo( employeePositionChangeVO );
         }
         employeePositionChangeDao.updateEmployeePositionChange( employeePositionChangeVO );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public void submitEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      try
      {

         employeePositionChangeDao.updateEmployeePositionChangeStatus( employeePositionChangeVO );

      }
      catch ( final Exception e )
      {

         throw new KANException( e );
      }
   }

   @Override
   public void deleteEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      employeePositionChangeDao.deleteEmployeePositionChange( employeePositionChangeVO );
   }

   @Override
   public void synchronizedEmployeePosition() throws KANException
   {
      // 搜索达到生效条件的异动记录
      final EmployeePositionChangeVO condition = new EmployeePositionChangeVO();
      condition.setStatus( "3" );
      condition.setEffectiveDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
      final List< Object > objectList = employeePositionChangeDao.getEmployeePositionChangeVOsByDateAndStatus( condition );

      if ( objectList == null || objectList.size() == 0 )
         return;

      String accountId = "";
      Map< String, String > employeeMap = new HashMap< String, String >();
      List< String > updateWXList = new ArrayList< String >();
      List< String > deleteWXList = new ArrayList< String >();
      try
      {
         // 开启事务
         this.startTransaction();
         for ( Object object : objectList )
         {
            EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) object;
            accountId = employeePositionChangeVO.getAccountId();
            employeeMap.put( employeePositionChangeVO.getEmployeeId(), employeePositionChangeVO.getStaffId() );
            PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
            positionStaffRelationVO.setStaffId( employeePositionChangeVO.getStaffId() );
            positionStaffRelationVO.setPositionId( employeePositionChangeVO.getOldPositionId() );

            PositionVO positionVO = null;

            // 来自快速异动
            if ( employeePositionChangeVO.getSubmitFlag() == 3 || employeePositionChangeVO.getSubmitFlag() == 4 )
            {

               positionVO = this.positionDao.getPositionVOByPositionId( employeePositionChangeVO.getNewPositionId() );
               if ( positionVO != null )
               {
                  positionVO.setTitleZH( employeePositionChangeVO.getNewPositionNameZH() );
                  positionVO.setTitleEN( employeePositionChangeVO.getNewPositionNameEN() );
                  positionVO.setBranchId( employeePositionChangeVO.getNewBranchId() );
                  positionVO.setPositionGradeId( employeePositionChangeVO.getNewPositionGradeId() );
                  positionVO.setParentPositionId( employeePositionChangeVO.getNewParentPositionId() );
                  positionVO.setModifyBy( employeePositionChangeVO.getModifyBy() );
                  positionVO.setModifyDate( new Date() );

                  this.getPositionDao().updatePosition( positionVO );
                  
                  updateWXList.add( employeePositionChangeVO.getEmployeeId() );
               }
            }
            else
            {
               //新增加
               if ( StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" ) && !StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" )
                     && StringUtils.isNotEmpty( employeePositionChangeVO.getNewPositionId() ) )
               {
                  positionStaffRelationVO.setPositionId( employeePositionChangeVO.getNewPositionId() );
                  positionStaffRelationVO.setStaffType( "1" );
                  positionStaffRelationVO.setDeleted( "1" );
                  positionStaffRelationVO.setStatus( "1" );
                  positionStaffRelationVO.setCreateBy( employeePositionChangeVO.getModifyBy() );
                  positionStaffRelationVO.setCreateDate( new Date() );
                  positionStaffRelationVO.setModifyBy( employeePositionChangeVO.getModifyBy() );
                  positionStaffRelationVO.setModifyDate( new Date() );
                  positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );
               }

               //修改
               if ( !StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" ) && !StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" )
                     && StringUtils.isNotEmpty( employeePositionChangeVO.getNewPositionId() ) )
               {
                  PositionStaffRelationVO positionStaffRelationVODB = positionStaffRelationDao.getPositionStaffRelationVOByPositionStaffRelationId( employeePositionChangeVO.getOldStaffPositionRelationId() );
                  if ( positionStaffRelationVODB != null )
                  {
                     positionStaffRelationVODB.setPositionId( employeePositionChangeVO.getNewPositionId() );
                     positionStaffRelationVODB.setModifyBy( employeePositionChangeVO.getModifyBy() );
                     positionStaffRelationVODB.setModifyDate( new Date() );
                     positionStaffRelationDao.updatePositionStaffRelation( positionStaffRelationVODB );
                  }

                  positionVO = this.positionDao.getPositionVOByPositionId( employeePositionChangeVO.getNewPositionId() );
               }

               //删除
               if ( !StringUtils.equals( employeePositionChangeVO.getOldPositionId(), "0" )
                     && ( StringUtils.isBlank( employeePositionChangeVO.getNewPositionId() ) || StringUtils.equals( employeePositionChangeVO.getNewPositionId(), "0" ) ) )
               {
                  positionStaffRelationDao.deletePositionStaffRelationByStaffIdAndPostionId( positionStaffRelationVO );
                  // 如果是删除
                  deleteWXList.add( employeePositionChangeVO.getEmployeeId() );
               }
               else
               {
                  updateWXList.add( employeePositionChangeVO.getEmployeeId() );
               }

               // 如果保留原有汇报线
               if ( KANUtil.filterEmpty( employeePositionChangeVO.getIsChildChange(), "0" ) != null && "1".equals( employeePositionChangeVO.getIsChildChange() ) )
               {
                  final PositionDTO positionDTO = KANConstants.getKANAccountConstants( accountId ).getPositionDTOByPositionId( employeePositionChangeVO.getOldPositionId() );

                  if ( positionDTO != null && positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
                  {
                     for ( PositionDTO subPositionDTO : positionDTO.getPositionDTOs() )
                     {
                        final PositionVO tempPositionVO = this.getPositionDao().getPositionVOByPositionId( subPositionDTO.getPositionVO().getPositionId() );
                        if ( tempPositionVO != null )
                        {
                           tempPositionVO.setParentPositionId( employeePositionChangeVO.getNewPositionId() );
                           this.getPositionDao().updatePosition( subPositionDTO.getPositionVO() );
                        }
                     }
                  }
               }
            }

            // 异动 - 已生效
            employeePositionChangeVO.setStatus( "5" );
            employeePositionChangeDao.updateEmployeePositionChange( employeePositionChangeVO );

            // 部门是否变更
            boolean isBranchChange = !employeePositionChangeVO.getOldBranchId().equals( employeePositionChangeVO.getNewBranchId() );

            // 更改劳动合同自定义字段
            if ( positionVO != null )
            {
               List< Object > employeeContractVOs = this.employeeContractDao.getEmployeeContractVOsByEmployeeId( employeePositionChangeVO.getEmployeeId() );
               if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
               {
                  for ( Object employeeContractVOObject : employeeContractVOs )
                  {
                     final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

                     // 只更改雇佣状态为在职的
                     if ( !"1".equals( tempEmployeeContractVO.getEmployStatus() ) )
                        continue;

                     // 若部门变更，需要更改Cost Center & BU/Function
                     if ( isBranchChange )
                     {
                        final BranchVO branchVO = KANConstants.getKANAccountConstants( tempEmployeeContractVO.getAccountId() ).getBranchDTOByBranchId( employeePositionChangeVO.getNewBranchId() ).getBranchVO();
                        final EmployeeVO tempEmployeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( tempEmployeeContractVO.getEmployeeId() );
                        tempEmployeeContractVO.setSettlementBranch( branchVO.getSettlementBranch() );
                        tempEmployeeContractVO.setBusinessTypeId( branchVO.getBusinessTypeId() );
                        if ( tempEmployeeVO != null )
                        {
                           tempEmployeeVO.setBusinessTypeId( branchVO.getBusinessTypeId() );
                           this.getEmployeeDao().updateEmployee( tempEmployeeVO );
                        }
                     }

                     JSONObject jsonObject = JSONObject.fromObject( tempEmployeeContractVO.getRemark1() );
                     if ( jsonObject != null && jsonObject.get( "neibuchengwei" ) != null )
                     {
                        jsonObject.put( "neibuchengwei", positionVO.getTitleZH() );
                     }

                     if ( jsonObject != null && jsonObject.get( "zhiweimingchengyingwen" ) != null )
                     {
                        jsonObject.put( "zhiweimingchengyingwen", positionVO.getTitleEN() );
                     }

                     if ( jsonObject != null && jsonObject.get( "yewuhuibaoxianjingli" ) != null && KANUtil.filterEmpty( employeePositionChangeVO.getRemark2() ) != null )
                     {
                        jsonObject.put( "yewuhuibaoxianjingli", employeePositionChangeVO.getRemark2() );
                     }

                     if ( jsonObject != null && KANUtil.filterEmpty( employeePositionChangeVO.getRemark1(), "0" ) != null )
                     {
                        jsonObject.put( "jobrole", employeePositionChangeVO.getRemark1() );
                     }

                     tempEmployeeContractVO.setRemark1( jsonObject.toString() );
                     tempEmployeeContractVO.setModifyDate( new Date() );
                     tempEmployeeContractVO.setModifyBy( employeePositionChangeVO.getModifyBy() );
                     this.employeeContractDao.updateEmployeeContract( tempEmployeeContractVO );
                  }
               }
            }
         }

         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      //      for ( Object object : objectList )
      //      {
      //         EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) object;
      //
      //         try
      //         {
      //            BaseAction.constantsInit( "initPosition", employeePositionChangeVO.getAccountId() );
      //            BaseAction.constantsInit( "initStaff", new String[] { employeePositionChangeVO.getAccountId(), employeePositionChangeVO.getStaffId() } );
      //            BaseAction.constantsInit( "initStaffBaseView", new String[] { employeePositionChangeVO.getAccountId(), employeePositionChangeVO.getStaffId() } );
      //         }
      //         catch ( Exception e )
      //         {
      //         }
      //      }

      try
      {
         BaseAction.constantsInit( "initPositionGroup", accountId );
         BaseAction.constantsInit( "initPosition", accountId );
         for ( Object object : objectList )
         {
            EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) object;
            BaseAction.constantsInit( "initStaff", new String[] { employeePositionChangeVO.getAccountId(), employeePositionChangeVO.getStaffId() } );
            BaseAction.constantsInit( "initStaffBaseView", new String[] { employeePositionChangeVO.getAccountId(), employeePositionChangeVO.getStaffId() } );
         }
         BaseAction.constantsInit( "initBranch", accountId );
      }
      catch ( Exception e )
      {
      }

      updateEmployee( employeeMap, accountId );

      for ( String employeeid : updateWXList )
      {
         BaseAction.syncWXContacts( employeeid );
      }
      for ( String employeeid : deleteWXList )
      {
         BaseAction.syncWXContacts( employeeid, true );
      }
   }

   private void updateEmployee( Map< String, String > employeeMap, String accountId ) throws KANException
   {
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      for ( String employeeId : employeeMap.keySet() )
      {
         EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
         List< PositionDTO > PositionDTOList = accountConstants.getPositionDTOsByStaffId( employeeMap.get( employeeId ) );

         // 初始化冗余字段职位
         String _tempPositionIds = "";
         // 初始化冗余字段部门
         String _tempBranchIds = "";
         // 初始化冗余字段上级部门
         String _tempParentBranchIds = "";
         // 初始化冗余字段上级职位
         String _tempParentPositionIds = "";
         // 初始化冗余字段上级职位所属人
         String _tempParentPositionOwners = "";
         // 初始化冗余字段上级职位部门
         String _tempParentPositionBranchIds = "";
         // 初始化冗余字段办公地址
         String _tempPositionLocationIds = "";
         // 初始化冗余字段职级
         String _tempPositionGradeIds = "";

         for ( PositionDTO positionDTO : PositionDTOList )
         {
            // 获取PositionVO
            final PositionVO positionVO = positionDTO.getPositionVO();

            if ( positionVO != null )
            {
               // 获取BranchVO
               final BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {
                  // 处理部门
                  if ( KANUtil.filterEmpty( _tempBranchIds ) == null )
                  {
                     _tempBranchIds = branchVO.getBranchId();
                  }

                  else
                  {
                     _tempBranchIds = _tempBranchIds + "," + branchVO.getBranchId();
                  }

                  // 处理上级部门
                  if ( KANUtil.filterEmpty( _tempParentBranchIds ) == null )
                  {
                     // 如果没有上级部门，取本身部门
                     if ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null )
                        _tempParentBranchIds = branchVO.getBranchId();
                     else
                        _tempParentBranchIds = branchVO.getParentBranchId();

                  }
                  else
                  {
                     // 如果没有上级部门，取本身部门
                     if ( KANUtil.filterEmpty( branchVO.getParentBranchId(), "0" ) == null )
                     {
                        _tempParentBranchIds = _tempParentBranchIds + "," + branchVO.getBranchId();
                     }
                     else
                     {
                        _tempParentBranchIds = _tempParentBranchIds + "," + branchVO.getParentBranchId();
                     }
                  }
               }

               // 处理职位
               if ( KANUtil.filterEmpty( _tempPositionIds ) == null )
               {
                  _tempPositionIds = positionVO.getPositionId();
               }
               else
               {
                  _tempPositionIds = _tempPositionIds + "," + positionVO.getPositionId();
               }

               // 处理上级职位
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

               // 处理上级职位所属人
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

               // 处理上级职位部门
               final PositionVO parentPositionVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getPositionVOByPositionId( positionVO.getParentPositionId() );
               if ( parentPositionVO != null )
               {
                  if ( KANUtil.filterEmpty( _tempParentPositionBranchIds ) == null )
                  {
                     _tempParentPositionBranchIds = parentPositionVO.getBranchId();
                  }
                  else
                  {
                     _tempParentPositionBranchIds = _tempParentPositionBranchIds + "," + parentPositionVO.getBranchId();
                  }

               }

               // 处理办公地点
               if ( KANUtil.filterEmpty( _tempPositionLocationIds ) == null )
               {
                  _tempPositionLocationIds = positionVO.getLocationId();
               }
               else
               {
                  _tempPositionLocationIds = _tempPositionLocationIds + "," + positionVO.getLocationId();
               }

               // 处理职级
               if ( KANUtil.filterEmpty( _tempPositionGradeIds ) == null )
               {
                  _tempPositionGradeIds = positionVO.getPositionGradeId();
               }
               else
               {
                  _tempPositionGradeIds = _tempPositionGradeIds + "," + positionVO.getPositionGradeId();
               }

            }
         }

         // 塞入各项值
         employeeVO.set_tempPositionIds( _tempPositionIds );
         employeeVO.set_tempBranchIds( _tempBranchIds );
         employeeVO.set_tempParentBranchIds( _tempParentBranchIds );
         employeeVO.set_tempParentPositionIds( _tempParentPositionIds );
         employeeVO.set_tempParentPositionOwners( _tempParentPositionOwners );
         employeeVO.set_tempParentPositionBranchIds( _tempParentPositionBranchIds );
         employeeVO.set_tempPositionLocationIds( _tempPositionLocationIds );
         employeeVO.set_tempPositionGradeIds( _tempPositionGradeIds );
         employeeDao.updateEmployee( employeeVO );
      }

   }

   @Override
   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException
   {
      // 获得ActionFlag
      final HistoryVO history = baseVO.getHistoryVO();
      //通过执行

      history.setObjectId( ( ( EmployeePositionChangeVO ) baseVO ).getPositionChangeId() );

      history.setAccessAction( EmployeePositionChangeAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeePositionChangeAction.accessAction ) );
      history.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );
      //表示的是新工作流
      history.setObjectType( "3" );
      history.setServiceBean( "employeePositionChangeService" );

      history.setNameZH( ( ( EmployeePositionChangeVO ) baseVO ).getEmployeeNameZH() );
      history.setNameEN( ( ( EmployeePositionChangeVO ) baseVO ).getEmployeeNameEN() );
   }

   @Override
   public int getEffectivePositionChangeVOCountByEmployeeId( EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      return employeePositionChangeDao.getEffectivePositionChangeVOCountByEmployeeId( employeePositionChangeVO );
   }

   public EmployeePositionChangeDao getEmployeePositionChangeDao()
   {
      return employeePositionChangeDao;
   }

   public void setEmployeePositionChangeDao( EmployeePositionChangeDao employeePositionChangeDao )
   {
      this.employeePositionChangeDao = employeePositionChangeDao;
   }

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

}
