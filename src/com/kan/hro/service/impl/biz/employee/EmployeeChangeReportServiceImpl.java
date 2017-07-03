package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JSONUtils;
import com.kan.hro.domain.biz.employee.EmployeeChangeReportVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeChangeReportService;

public class EmployeeChangeReportServiceImpl extends ContextService implements EmployeeChangeReportService
{
   private LogDao logDao;

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   @Override
   public PagedListHolder getEmployeeChangeReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      try
      {
         final List< Object > objects = new ArrayList< Object >();
         final EmployeeChangeReportVO employeeChangeReportVO = ( EmployeeChangeReportVO ) pagedListHolder.getObject();
         final String language = employeeChangeReportVO.getLocale().getLanguage();
         String changeType = employeeChangeReportVO.getChangeType();

         if ( changeType == null )
         {
            changeType = "0";
         }
         /*
         // 员工基本信息
         if ( "1".equals( changeType ) || "0".equals( changeType ) )
         {
            final LogVO employeeModifyLogVO = new LogVO();
            employeeModifyLogVO.setModule( EmployeeVO.class.getCanonicalName() );
            employeeModifyLogVO.setTypeArray( String.valueOf( Operate.ADD.getIndex() ) + "," + String.valueOf( Operate.MODIFY.getIndex() ) );
            employeeModifyLogVO.setSortColumn( "pKey,operateTime" );
            employeeModifyLogVO.setSortOrder( "desc" );
            employeeModifyLogVO.setOperateTimeBegin( employeeChangeReportVO.getOperateTimeBegin() );
            employeeModifyLogVO.setOperateTimeEnd( employeeChangeReportVO.getOperateTimeEnd() );
            employeeModifyLogVO.setEmployeeId( employeeChangeReportVO.getEmployeeId() );
            employeeModifyLogVO.setEmployeeNameZH( employeeChangeReportVO.getEmployeeNameZH() );
            employeeModifyLogVO.setEmployeeNameEN( employeeChangeReportVO.getEmployeeNameEN() );

            final List< Object > employeeModifyLogVOs = this.getLogDao().getLogVOsByCondition( employeeModifyLogVO );
            if ( employeeModifyLogVOs != null && employeeModifyLogVOs.size() > 0 )
            {
               for ( int i = 0; i < employeeModifyLogVOs.size() - 1; i++ )
               {
                  EmployeeChangeReportVO tempEmployeeChangeReportVO = generateEmployeeChangeReportVO( ( LogVO ) employeeModifyLogVOs.get( i + 1 ), ( LogVO ) employeeModifyLogVOs.get( i ), 0, language );
                  if ( tempEmployeeChangeReportVO != null )
                  {
                     tempEmployeeChangeReportVO.setChangeType( "1" );
                     objects.add( tempEmployeeChangeReportVO );
                  }
               }
            }
         } 
         */

         // 员工劳动合同
         if ( "2".equals( changeType ) || "0".equals( changeType ) )
         {
            final LogVO employeeContractModifyLogVO = new LogVO();
            employeeContractModifyLogVO.setModule( EmployeeContractVO.class.getCanonicalName() );
            employeeContractModifyLogVO.setTypeArray( Operate.MODIFY.getIndex() + "," + Operate.SUBMIT.getIndex() );
            employeeContractModifyLogVO.setSortColumn( "pKey,operateTime" );
            employeeContractModifyLogVO.setSortOrder( "desc" );
            employeeContractModifyLogVO.setOperateTimeBegin( employeeChangeReportVO.getOperateTimeBegin() );
            employeeContractModifyLogVO.setOperateTimeEnd( employeeChangeReportVO.getOperateTimeEnd() );
            employeeContractModifyLogVO.setEmployeeId( employeeChangeReportVO.getEmployeeId() );
            employeeContractModifyLogVO.setEmployeeNameZH( employeeChangeReportVO.getEmployeeNameZH() );
            employeeContractModifyLogVO.setEmployeeNameEN( employeeChangeReportVO.getEmployeeNameEN() );
            employeeContractModifyLogVO.setChangeReason( employeeChangeReportVO.getChangeReason() );
            setDataAuth( employeeContractModifyLogVO, employeeChangeReportVO );

            // 修改和提交
            final List< Object > employeeContractModifyLogVOs = this.getLogDao().getLogVOsByCondition( employeeContractModifyLogVO );
            if ( employeeContractModifyLogVOs != null && employeeContractModifyLogVOs.size() > 0 )
            {
               for ( int i = 0; i < employeeContractModifyLogVOs.size() - 1; i++ )
               {
                  EmployeeChangeReportVO tempEmployeeChangeReportVO = generateEmployeeChangeReportVO( ( LogVO ) employeeContractModifyLogVOs.get( i + 1 ), ( LogVO ) employeeContractModifyLogVOs.get( i ), 1, language );
                  if ( tempEmployeeChangeReportVO != null )
                  {
                     tempEmployeeChangeReportVO.setChangeType( "2" );
                     objects.add( tempEmployeeChangeReportVO );
                  }
               }
            }

            // 如果是新增劳动合同
            employeeContractModifyLogVO.setTypeArray( Operate.ADD.getIndex() + "" );
            final List< Object > employeeContractModifyLogVOs_forAdd = this.getLogDao().getLogVOsByCondition( employeeContractModifyLogVO );
            if ( employeeContractModifyLogVOs_forAdd != null && employeeContractModifyLogVOs_forAdd.size() > 0 )
            {
               for ( Object operAddObject : employeeContractModifyLogVOs_forAdd )
               {
                  objects.add( generateEmployeeChangeReportVO_forAdd( ( LogVO ) operAddObject ) );
               }
            }
         }

         // 员工职位调整
         if ( "3".equals( changeType ) || "0".equals( changeType ) )
         {
            final LogVO employeePositionChangeModifyLogVO = new LogVO();
            employeePositionChangeModifyLogVO.setModule( EmployeePositionChangeVO.class.getCanonicalName() );
            employeePositionChangeModifyLogVO.setTypeArray( Operate.ADD.getIndex() + "," + Operate.MODIFY.getIndex() + "," + Operate.SUBMIT.getIndex() );
            employeePositionChangeModifyLogVO.setSortColumn( "operateTime" );
            employeePositionChangeModifyLogVO.setSortOrder( "desc" );
            employeePositionChangeModifyLogVO.setOperateTimeBegin( employeeChangeReportVO.getOperateTimeBegin() );
            employeePositionChangeModifyLogVO.setOperateTimeEnd( employeeChangeReportVO.getOperateTimeEnd() );
            employeePositionChangeModifyLogVO.setEmployeeId( employeeChangeReportVO.getEmployeeId() );
            employeePositionChangeModifyLogVO.setEmployeeNameZH( employeeChangeReportVO.getEmployeeNameZH() );
            employeePositionChangeModifyLogVO.setEmployeeNameEN( employeeChangeReportVO.getEmployeeNameEN() );
            employeePositionChangeModifyLogVO.setChangeReason( employeeChangeReportVO.getChangeReason() );
            setDataAuth( employeePositionChangeModifyLogVO, employeeChangeReportVO );

            final List< Object > employeePositionChangeModifyLogVOs = this.getLogDao().getLogVOsByCondition( employeePositionChangeModifyLogVO );
            if ( employeePositionChangeModifyLogVOs != null && employeePositionChangeModifyLogVOs.size() > 0 )
            {
               for ( Object o : employeePositionChangeModifyLogVOs )
               {
                  final LogVO tempLogVO = ( LogVO ) o;
                  final JSONObject tempJSONObject = JSONObject.fromObject( tempLogVO.getContent() );
                  final Map< Object, String > diffMap = new HashMap< Object, String >();

                  if ( JSONUtils.isDifferent( tempJSONObject.get( "oldParentBranchName" ), tempJSONObject.get( "newParentBranchName" ) ) )
                  {
                     diffMap.put( "parentBranch", JSONUtils.replaceEmpty( tempJSONObject.get( "oldParentBranchName" ), language ) + JSONUtils.DIFF_SPLIT_STR
                           + JSONUtils.replaceEmpty( tempJSONObject.get( "newParentBranchName" ), language ) );
                  }

                  if ( JSONUtils.isDifferent( tempJSONObject.get( "oldParentPositionName" ), tempJSONObject.get( "newParentPositionName" ) ) )
                  {
                     diffMap.put( "parentPosition", JSONUtils.replaceEmpty( tempJSONObject.get( "oldParentPositionName" ), language ) + JSONUtils.DIFF_SPLIT_STR
                           + JSONUtils.replaceEmpty( tempJSONObject.get( "newParentPositionName" ), language ) );
                  }

                  if ( JSONUtils.isDifferent( tempJSONObject.get( "oldPositionGradeName" ), tempJSONObject.get( "newPositionGradeName" ) ) )
                  {
                     diffMap.put( "postionGrade", JSONUtils.replaceEmpty( tempJSONObject.get( "oldPositionGradeName" ), language ) + JSONUtils.DIFF_SPLIT_STR
                           + JSONUtils.replaceEmpty( tempJSONObject.get( "newPositionGradeName" ), language ) );
                  }

                  if ( JSONUtils.isDifferent( tempJSONObject.get( "oldParentPositionOwnersName" ), tempJSONObject.get( "newParentPositionOwnersName" ) ) )
                  {
                     diffMap.put( "lineManager", JSONUtils.replaceEmpty( tempJSONObject.get( "oldParentPositionOwnersName" ), language ) + JSONUtils.DIFF_SPLIT_STR
                           + JSONUtils.replaceEmpty( tempJSONObject.get( "newParentPositionOwnersName" ), language ) );
                  }

                  if ( JSONUtils.isDifferent( tempJSONObject.get( "oldBranchName" ), tempJSONObject.get( "newBranchName" ) ) )
                  {
                     diffMap.put( "branch", JSONUtils.replaceEmpty( tempJSONObject.get( "oldBranchName" ), language ) + JSONUtils.DIFF_SPLIT_STR
                           + JSONUtils.replaceEmpty( tempJSONObject.get( "newBranchName" ), language ) );
                  }

                  if ( JSONUtils.isDifferent( tempJSONObject.get( "oldPositionName" ), tempJSONObject.get( "newPositionName" ) ) )
                  {
                     diffMap.put( "position", JSONUtils.replaceEmpty( tempJSONObject.get( "oldPositionName" ), language ) + JSONUtils.DIFF_SPLIT_STR
                           + JSONUtils.replaceEmpty( tempJSONObject.get( "newPositionName" ), language ) );
                  }

                  if ( diffMap.size() > 0 )
                  {
                     final EmployeeChangeReportVO tempEmployeeChangeReportVO = new EmployeeChangeReportVO();
                     tempEmployeeChangeReportVO.setEmployeeId( tempLogVO.getEmployeeId() );
                     tempEmployeeChangeReportVO.setEmployeeNameZH( tempLogVO.getEmployeeNameZH() );
                     tempEmployeeChangeReportVO.setEmployeeNameEN( tempLogVO.getEmployeeNameEN() );
                     tempEmployeeChangeReportVO.setChangeContent( JSONUtils.toString( diffMap, 2, language ) );
                     tempEmployeeChangeReportVO.setOperateBy( tempLogVO.getOperateBy() );
                     tempEmployeeChangeReportVO.setOperateTime( tempLogVO.getOperateTime() );
                     tempEmployeeChangeReportVO.setChangeType( "3" );
                     tempEmployeeChangeReportVO.setType( tempLogVO.getType() );
                     tempEmployeeChangeReportVO.setChangeReason( tempLogVO.getChangeReason() );

                     objects.add( tempEmployeeChangeReportVO );
                  }
               }
            }
         }

         // 员工薪酬调整
         if ( "4".equals( changeType ) || "0".equals( changeType ) )
         {
            final LogVO employeeSalaryAdjustmentModifyLogVO = new LogVO();
            employeeSalaryAdjustmentModifyLogVO.setModule( EmployeeSalaryAdjustmentVO.class.getCanonicalName() );
            employeeSalaryAdjustmentModifyLogVO.setTypeArray( Operate.ADD.getIndex() + "," + Operate.MODIFY.getIndex() + "," + Operate.SUBMIT.getIndex() );
            employeeSalaryAdjustmentModifyLogVO.setSortColumn( "operateTime" );
            employeeSalaryAdjustmentModifyLogVO.setSortOrder( "desc" );
            employeeSalaryAdjustmentModifyLogVO.setOperateTimeBegin( employeeChangeReportVO.getOperateTimeBegin() );
            employeeSalaryAdjustmentModifyLogVO.setOperateTimeEnd( employeeChangeReportVO.getOperateTimeEnd() );
            employeeSalaryAdjustmentModifyLogVO.setEmployeeId( employeeChangeReportVO.getEmployeeId() );
            employeeSalaryAdjustmentModifyLogVO.setEmployeeNameZH( employeeChangeReportVO.getEmployeeNameZH() );
            employeeSalaryAdjustmentModifyLogVO.setEmployeeNameEN( employeeChangeReportVO.getEmployeeNameEN() );
            employeeSalaryAdjustmentModifyLogVO.setChangeReason( employeeChangeReportVO.getChangeReason() );
            setDataAuth( employeeSalaryAdjustmentModifyLogVO, employeeChangeReportVO );

            final List< Object > employeeSalaryAdjustmentModifyLogVOs = this.getLogDao().getLogVOsByCondition( employeeSalaryAdjustmentModifyLogVO );
            if ( employeeSalaryAdjustmentModifyLogVOs != null && employeeSalaryAdjustmentModifyLogVOs.size() > 0 )
            {
               for ( Object o : employeeSalaryAdjustmentModifyLogVOs )
               {
                  final LogVO tempLogVO = ( LogVO ) o;
                  final JSONObject tempJSONObject = JSONObject.fromObject( tempLogVO.getContent() );
                  final EmployeeChangeReportVO tempEmployeeChangeReportVO = new EmployeeChangeReportVO();

                  if ( tempJSONObject.get( "decodeItemId" ) == null || tempJSONObject.get( "oldBase" ) == null || tempJSONObject.get( "newBase" ) == null )
                     continue;

                  tempEmployeeChangeReportVO.setEmployeeId( tempLogVO.getEmployeeId() );
                  tempEmployeeChangeReportVO.setEmployeeNameZH( tempLogVO.getEmployeeNameZH() );
                  tempEmployeeChangeReportVO.setEmployeeNameEN( tempLogVO.getEmployeeNameEN() );
                  tempEmployeeChangeReportVO.setChangeContent( tempJSONObject.getString( "decodeItemId" ) + ": " + tempJSONObject.getString( "oldBase" ) + " → "
                        + tempJSONObject.getString( "newBase" ) );
                  tempEmployeeChangeReportVO.setOperateBy( tempLogVO.getOperateBy() );
                  tempEmployeeChangeReportVO.setOperateTime( tempLogVO.getOperateTime() );
                  tempEmployeeChangeReportVO.setChangeType( "4" );
                  tempEmployeeChangeReportVO.setType( tempLogVO.getType() );
                  tempEmployeeChangeReportVO.setChangeReason( tempLogVO.getChangeReason() );

                  objects.add( tempEmployeeChangeReportVO );
               }
            }
         }

         pagedListHolder.setHolderSize( objects.size() );
         // 手动分页
         if ( isPaged )
         {
            if ( objects != null && objects.size() > 0 )
            {
               // 如果当前页数大于总页数自动跳转首页
               if ( ( pagedListHolder.getPage() * pagedListHolder.getPageSize() ) >= objects.size() )
               {
                  for ( int i = 0; i < pagedListHolder.getPageSize(); i++ )
                  {
                     pagedListHolder.getSource().add( objects.get( i ) );
                  }
                  pagedListHolder.setPage( "0" );
               }
               else
               {
                  for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                        && i < objects.size(); i++ )
                  {
                     pagedListHolder.getSource().add( objects.get( i ) );
                  }
               }
            }
         }
         else
         {
            pagedListHolder.setSource( objects );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return pagedListHolder;
   }

   private EmployeeChangeReportVO generateEmployeeChangeReportVO( final LogVO modifyLogVOBefore, final LogVO modifylogVOAfter, final int prefixIndex, final String language )
   {
      // 主键一样才比较异同
      if ( modifyLogVOBefore == null || modifylogVOAfter == null || KANUtil.filterEmpty( modifyLogVOBefore.getpKey() ) == null
            || KANUtil.filterEmpty( modifylogVOAfter.getpKey() ) == null || !modifyLogVOBefore.getpKey().equals( modifylogVOAfter.getpKey() ) )
      {
         return null;
      }

      final Map< Object, String > diffMap = new HashMap< Object, String >();
      final JSONObject json1 = JSONObject.fromObject( modifyLogVOBefore.getContent() );
      final JSONObject json2 = JSONObject.fromObject( modifylogVOAfter.getContent() );
      diffMap.putAll( JSONUtils.compareToDifferent( json1, json2, language ) );

      final EmployeeChangeReportVO employeeChangeReportVO = new EmployeeChangeReportVO();
      employeeChangeReportVO.setEmployeeId( modifyLogVOBefore.getEmployeeId() );
      employeeChangeReportVO.setEmployeeNameZH( modifyLogVOBefore.getEmployeeNameZH() );
      employeeChangeReportVO.setEmployeeNameEN( modifyLogVOBefore.getEmployeeNameEN() );
      employeeChangeReportVO.setChangeContent( JSONUtils.toString( diffMap, prefixIndex, language ) );
      employeeChangeReportVO.setOperateBy( modifylogVOAfter.getOperateBy() );
      employeeChangeReportVO.setOperateTime( modifylogVOAfter.getOperateTime() );
      employeeChangeReportVO.setType( modifylogVOAfter.getType() );
      employeeChangeReportVO.setChangeReason( modifylogVOAfter.getChangeReason() );

      return employeeChangeReportVO;
   }

   private void setDataAuth( final LogVO logVO, final EmployeeChangeReportVO employeeChangeReportVO )
   {
      logVO.setRulePrivateIds( employeeChangeReportVO.getRulePrivateIds() );
      logVO.setRulePositionIds( employeeChangeReportVO.getRulePositionIds() );
      logVO.setRuleBranchIds( employeeChangeReportVO.getRuleBranchIds() );
      logVO.setRuleBusinessTypeIds( employeeChangeReportVO.getRuleBusinessTypeIds() );
      logVO.setRuleEntityIds( employeeChangeReportVO.getRuleEntityIds() );
      logVO.setRulePublic( employeeChangeReportVO.getRulePublic() );
   }

   @Override
   public EmployeeChangeReportVO generateEmployeeChangeReportVO_forAdd( LogVO logVO )
   {
      // 如果是新增
      final EmployeeChangeReportVO employeeChangeReportVO = new EmployeeChangeReportVO();
      employeeChangeReportVO.setEmployeeId( logVO.getEmployeeId() );
      employeeChangeReportVO.setEmployeeNameZH( logVO.getEmployeeNameZH() );
      employeeChangeReportVO.setEmployeeNameEN( logVO.getEmployeeNameEN() );
      employeeChangeReportVO.setChangeContent( logVO.getContent() );
      employeeChangeReportVO.setOperateBy( logVO.getOperateBy() );
      employeeChangeReportVO.setOperateTime( logVO.getOperateTime() );
      employeeChangeReportVO.setType( logVO.getType() );
      employeeChangeReportVO.setChangeReason( logVO.getChangeReason() );

      return employeeChangeReportVO;
   }

}
