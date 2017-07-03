package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.RecordDao;
import com.kan.hro.domain.biz.attendance.RecordVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.RecordService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class RecordServiceImpl extends ContextService implements RecordService
{

   private EmployeeService employeeService;

   public EmployeeService getEmployeeService()
   {
      return employeeService;
   }

   public void setEmployeeService( EmployeeService employeeService )
   {
      this.employeeService = employeeService;
   }

   @Override
   public PagedListHolder getRecordVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final RecordDao recordDao = ( RecordDao ) getDao();
      pagedListHolder.setHolderSize( recordDao.countRecordVOsByCondition( ( RecordVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( recordDao.getRecordVOsByCondition( ( RecordVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( recordDao.getRecordVOsByCondition( ( RecordVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public RecordVO getRecordVOByRecordId( String recordId ) throws KANException
   {
      return ( ( RecordDao ) getDao() ).getRecordVOByRecordId( recordId );
   }

   @Override
   public int insertRecord( RecordVO recordVO ) throws KANException
   {
      return ( ( RecordDao ) getDao() ).insertRecord( recordVO );
   }

   @Override
   public int updateRecord( RecordVO recordVO ) throws KANException
   {
      return ( ( RecordDao ) getDao() ).updateRecord( recordVO );
   }

   @Override
   public int deleteRecord( RecordVO recordVO ) throws KANException
   {
      // 标记删除
      recordVO.setDeleted( RecordVO.FALSE );
      return ( ( RecordDao ) getDao() ).updateRecord( recordVO );
   }

   @Override
   public String syncRecord( List< RecordVO > clientRecordVOs, final String startDateStr ) throws KANException
   {
      StringBuffer result = new StringBuffer();
      // 根据时间获取本地记录

      final RecordVO condRecordVO = new RecordVO();
      condRecordVO.setStartDate( startDateStr );
      final List< Object > serverRecordVOObjects = ( ( RecordDao ) getDao() ).getRecordVOsByCondition( condRecordVO );
      final List< RecordVO > toInsertRecordList = new ArrayList< RecordVO >();
      for ( RecordVO clientRecord : clientRecordVOs )
      {
         boolean exist = false;
         for ( Object obj : serverRecordVOObjects )
         {
            // 比较记录，获取未添加的记录
            if ( clientRecord.getSerialId().equals( ( ( RecordVO ) obj ).getSerialId() ) )
            {
               exist = true;
               break;
            }
         }
         // 如果不存在,则添加到待添加里面
         if ( !exist )
         {
            toInsertRecordList.add( clientRecord );
         }
      }
      result.append( "客户端打卡记录:   " + clientRecordVOs.size() + "条\r\n" );
      result.append( "服务端打卡记录:   " + serverRecordVOObjects.size() + "条\r\n" );
      result.append( "新增打卡记录:   " + toInsertRecordList.size() + "条\r\n" );
      int insertCount = 0;
      if ( toInsertRecordList != null && toInsertRecordList.size() > 0 )
      {
         final List< String > workIdList = new ArrayList< String >();
         for ( RecordVO recordVO : toInsertRecordList )
         {
            workIdList.add( recordVO.getWorkId() );
         }
         // 初始化employee service
         final EmployeeVO condEmployeeVO = new EmployeeVO();
         condEmployeeVO.setAccountId( toInsertRecordList.get( 0 ).getAccountId() );
         condEmployeeVO.setCorpId( toInsertRecordList.get( 0 ).getCorpId() );
         condEmployeeVO.setEmployeeNoList( workIdList );
         List< Object > existEmployeeNOObjects = employeeService.getEmployeeNosByEmployeeNoList( condEmployeeVO );
         if ( existEmployeeNOObjects != null && existEmployeeNOObjects.size() > 0 )
         {
            for ( RecordVO recordVO : toInsertRecordList )
            {

               for ( Object object : existEmployeeNOObjects )
               {
                  final EmployeeVO employeeVO = ( EmployeeVO ) object;
                  // 判断员工是否存在
                  if ( employeeVO.getEmployeeNo().equals( recordVO.getWorkId() ) )
                  {
                     // 如果这个员工存在，则插入这个员工的数据
                     recordVO.setEmployeeId( employeeVO.getEmployeeId() );
                     recordVO.setEmployeeNameZH( employeeVO.getNameZH() );
                     recordVO.setEmployeeNameEN( employeeVO.getNameEN() );
                     recordVO.setEmployeeNo( employeeVO.getEmployeeNo() );
                     ( ( RecordDao ) getDao() ).insertRecord( recordVO );
                     insertCount++;
                     break;
                  }
               }
            }
         }
      }
      result.append( "实际新增记录:   " + insertCount + "条\r\n" );
      return result.toString();
   }

   @Override
   public int insertRecordVOs( final List< Object > recordVOs ) throws KANException
   {
      try
      {
         int rows = 0;
         startTransaction();

         if ( recordVOs != null && recordVOs.size() > 0 )
         {
            for ( Object recordVO : recordVOs )
            {
               rows = rows + ( ( RecordDao ) getDao() ).insertRecord( ( RecordVO ) recordVO );
            }
         }

         this.commitTransaction();

         return rows;
      }
      catch ( final Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

   }

}
