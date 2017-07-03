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
      // ���ɾ��
      recordVO.setDeleted( RecordVO.FALSE );
      return ( ( RecordDao ) getDao() ).updateRecord( recordVO );
   }

   @Override
   public String syncRecord( List< RecordVO > clientRecordVOs, final String startDateStr ) throws KANException
   {
      StringBuffer result = new StringBuffer();
      // ����ʱ���ȡ���ؼ�¼

      final RecordVO condRecordVO = new RecordVO();
      condRecordVO.setStartDate( startDateStr );
      final List< Object > serverRecordVOObjects = ( ( RecordDao ) getDao() ).getRecordVOsByCondition( condRecordVO );
      final List< RecordVO > toInsertRecordList = new ArrayList< RecordVO >();
      for ( RecordVO clientRecord : clientRecordVOs )
      {
         boolean exist = false;
         for ( Object obj : serverRecordVOObjects )
         {
            // �Ƚϼ�¼����ȡδ��ӵļ�¼
            if ( clientRecord.getSerialId().equals( ( ( RecordVO ) obj ).getSerialId() ) )
            {
               exist = true;
               break;
            }
         }
         // ���������,����ӵ����������
         if ( !exist )
         {
            toInsertRecordList.add( clientRecord );
         }
      }
      result.append( "�ͻ��˴򿨼�¼:   " + clientRecordVOs.size() + "��\r\n" );
      result.append( "����˴򿨼�¼:   " + serverRecordVOObjects.size() + "��\r\n" );
      result.append( "�����򿨼�¼:   " + toInsertRecordList.size() + "��\r\n" );
      int insertCount = 0;
      if ( toInsertRecordList != null && toInsertRecordList.size() > 0 )
      {
         final List< String > workIdList = new ArrayList< String >();
         for ( RecordVO recordVO : toInsertRecordList )
         {
            workIdList.add( recordVO.getWorkId() );
         }
         // ��ʼ��employee service
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
                  // �ж�Ա���Ƿ����
                  if ( employeeVO.getEmployeeNo().equals( recordVO.getWorkId() ) )
                  {
                     // ������Ա�����ڣ���������Ա��������
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
      result.append( "ʵ��������¼:   " + insertCount + "��\r\n" );
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
