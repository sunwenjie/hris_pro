package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.dao.inf.management.ItemDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportDetailDao;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class AttendanceHeaderImportHandlerImpl implements ExcelImportHandler< List< CellDataDTO > >
{

   private AttendanceImportHeaderDao attendanceImportHeaderDao;

   private AttendanceImportDetailDao attendanceImportDetailDao;

   private EmployeeContractDao employeeContractDao;

   private ItemDao itemDao;

   private EmployeeDao employeeDao;

   public AttendanceImportHeaderDao getAttendanceImportHeaderDao()
   {
      return attendanceImportHeaderDao;
   }

   public void setAttendanceImportHeaderDao( AttendanceImportHeaderDao attendanceImportHeaderDao )
   {
      this.attendanceImportHeaderDao = attendanceImportHeaderDao;
   }

   public AttendanceImportDetailDao getAttendanceImportDetailDao()
   {
      return attendanceImportDetailDao;
   }

   public void setAttendanceImportDetailDao( AttendanceImportDetailDao attendanceImportDetailDao )
   {
      this.attendanceImportDetailDao = attendanceImportDetailDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public ItemDao getItemDao()
   {
      return itemDao;
   }

   public void setItemDao( ItemDao itemDao )
   {
      this.itemDao = itemDao;
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
   public void init( final List< CellDataDTO > importData )
   {

   }

   @Override
   public boolean excuteBeforInsert( final List< CellDataDTO > importDatas, final HttpServletRequest request )
   {
      boolean flag = true;

      if ( importDatas != null && importDatas.size() > 0 )
      {
         // ���accountId��corpId
         final String accountId = importDatas.get( 0 ).getAccounId();
         final String corpId = importDatas.get( 0 ).getCorpId();
         String clientId = null;

         for ( CellDataDTO importDataDTO : importDatas )
         {

            if ( importDataDTO != null )
            {
               CellData employeeContractCellData = importDataDTO.getCellDataByColumnNameDB( "contractId" );

               CellData employeeNameCellData = importDataDTO.getCellDataByColumnNameDB( "employeeName" );

               CellData clientIdCellData = importDataDTO.getCellDataByColumnNameDB( "clientId" );

               CellData totalWorkHoursCellData = importDataDTO.getCellDataByColumnNameDB( "totalWorkHours" );

               CellData totalWorkDaysCellData = importDataDTO.getCellDataByColumnNameDB( "totalWorkDays" );

               CellData totalFullHoursCellData = importDataDTO.getCellDataByColumnNameDB( "totalFullHours" );

               CellData totalFullDaysCellData = importDataDTO.getCellDataByColumnNameDB( "totalFullDays" );

               // totalWorkHours��totalWorkDays��totalFullHours��totalFullDays ����Ϊ��
               if ( KANUtil.filterEmpty( totalWorkHoursCellData.getDbValue() ) == null )
               {
                  totalWorkHoursCellData.setErrorMessange( "��Ԫ��[" + totalWorkHoursCellData.getCellRef() + "]����Ϊ�ա�" );
                  flag = false;
               }
               if ( KANUtil.filterEmpty( totalWorkDaysCellData.getDbValue() ) == null )
               {
                  totalWorkDaysCellData.setErrorMessange( "��Ԫ��[" + totalWorkDaysCellData.getCellRef() + "]����Ϊ�ա�" );
                  flag = false;
               }
               if ( KANUtil.filterEmpty( totalFullHoursCellData.getDbValue() ) == null )
               {
                  totalFullHoursCellData.setErrorMessange( "��Ԫ��[" + totalFullHoursCellData.getCellRef() + "]����Ϊ�ա�" );
                  flag = false;
               }
               if ( KANUtil.filterEmpty( totalFullDaysCellData.getDbValue() ) == null )
               {
                  totalFullDaysCellData.setErrorMessange( "��Ԫ��[" + totalFullDaysCellData.getCellRef() + "]����Ϊ�ա�" );
                  flag = false;
               }

               // ��֤��ͬ��Ч��
               if ( employeeContractCellData.getDbValue() != null )
               {
                  EmployeeContractVO employeeContractVO;
                  try
                  {
                     employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( ( String ) employeeContractCellData.getDbValue() );

                     if ( employeeContractVO == null )
                     {
                        employeeContractCellData.setErrorMessange( "��Ԫ��[" + employeeContractCellData.getCellRef() + "]�Ͷ���ͬID��Ч��" );
                        flag = false;
                     }
                     else
                     {

                        // ��֤��ͬID��Ա�������Ƿ�һ��
                        if ( employeeNameCellData != null && KANUtil.filterEmpty( employeeNameCellData.getValue() ) != null )
                        {
                           final EmployeeVO employeeVO = this.employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );

                           if ( employeeVO != null && !employeeNameCellData.getValue().equals( employeeVO.getNameEN() )
                                 && !employeeNameCellData.getValue().equals( employeeVO.getNameZH() ) )
                           {
                              flag = false;
                              employeeContractCellData.setErrorMessange( "��Ԫ��[" + employeeContractCellData.getCellRef() + "]�Ͷ���ͬ��Ӧ��Ա�������뵥Ԫ��[" + employeeNameCellData.getCellRef()
                                    + "]��������ƥ��!" );
                              continue;
                           }

                        }

                        clientId = employeeContractVO.getClientId();
                     }

                  }
                  catch ( KANException e )
                  {
                     e.printStackTrace();
                  }

               }

               // ���״̬
               final CellData statusData_new = new CellData();
               final ColumnVO statusColumn = new ColumnVO();
               statusColumn.setNameDB( "status" );
               statusColumn.setIsForeignKey( "2" );
               statusColumn.setAccountId( "1" );
               statusColumn.setIsDBColumn( "1" );
               statusData_new.setColumnVO( statusColumn );
               statusData_new.setJdbcDataType( JDBCDataType.VARCHAR );
               statusData_new.setDbValue( "1" );
               statusData_new.setValue( "1" );
               importDataDTO.getCellDatas().add( statusData_new );

               // ���ÿͻ�Id
               if ( clientId != null )
               {
                  if ( clientIdCellData != null )
                  {
                     clientIdCellData.setValue( clientId );
                     clientIdCellData.setDbValue( clientId );
                  }
                  else
                  {
                     final CellData clientIdData_new = new CellData();
                     final ColumnVO clientIdColumn = new ColumnVO();
                     clientIdColumn.setNameDB( "clientId" );
                     clientIdColumn.setIsForeignKey( "1" );
                     clientIdColumn.setAccountId( "1" );
                     clientIdColumn.setIsDBColumn( "1" );
                     clientIdData_new.setColumnVO( clientIdColumn );
                     clientIdData_new.setJdbcDataType( JDBCDataType.INT );
                     clientIdData_new.setDbValue( clientId );
                     clientIdData_new.setValue( clientId );
                     importDataDTO.getCellDatas().add( clientIdData_new );
                  }
               }

               // ��֤�ӱ���Ϣ
               if ( importDataDTO.getSubCellDataDTOs() != null )
               {
                  for ( CellDataDTO subImportDataDTO : importDataDTO.getSubCellDataDTOs() )
                  {
                     if ( subImportDataDTO != null )
                     {
                        // ��ʼ����Ŀ
                        ItemVO itemVO = new ItemVO();

                        CellData itemIdCellData = subImportDataDTO.getCellDataByColumnNameDB( "itemId" );
                        String itemId = KANUtil.filterEmpty( itemIdCellData.getDbValue() );

                        CellData hoursCellData = subImportDataDTO.getCellDataByColumnNameDB( "hours" );
                        String hours = KANUtil.filterEmpty( hoursCellData.getDbValue() );

                        // ��Сʱ���������޿�Ŀ
                        if ( hoursCellData != null && hours != null && itemIdCellData != null && KANUtil.filterEmpty( itemId, "0" ) == null )
                        {
                           flag = false;
                           itemIdCellData.setErrorMessange( "��Ԫ��[" + itemIdCellData.getCellRef() + "]����Ϊ��" );
                        }

                        if ( itemId != null )
                        {
                           try
                           {
                              itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( itemId );
                           }
                           catch ( KANException e )
                           {
                              e.printStackTrace();
                           }
                        }

                        if ( itemVO == null )
                        {
                           flag = false;
                           itemIdCellData.setErrorMessange( "��Ԫ��[" + itemIdCellData.getCellRef() + "]��Ӧ�Ŀ�Ŀ������!" );
                        }
                        else
                        {
                           // ��ӿ�ĿID
                           final CellData itemData_new = new CellData();
                           final ColumnVO itemColumn = new ColumnVO();
                           itemColumn.setNameDB( "itemName" );
                           itemColumn.setIsForeignKey( "2" );
                           itemColumn.setAccountId( "1" );
                           itemColumn.setIsDBColumn( "1" );
                           itemData_new.setColumnVO( itemColumn );
                           itemData_new.setJdbcDataType( JDBCDataType.VARCHAR );
                           itemData_new.setDbValue( itemVO.getNameZH() );
                           itemData_new.setValue( itemVO.getNameZH() );
                           subImportDataDTO.getCellDatas().add( itemData_new );
                        }

                        // �������
                        final CellData headerIdData_new = new CellData();
                        final ColumnVO headerIdColumn = new ColumnVO();
                        headerIdColumn.setNameDB( "headerId" );
                        headerIdColumn.setIsForeignKey( "1" );
                        headerIdColumn.setAccountId( "1" );
                        headerIdColumn.setIsDBColumn( "1" );
                        headerIdData_new.setColumnVO( headerIdColumn );
                        headerIdData_new.setJdbcDataType( JDBCDataType.VARCHAR );
                        headerIdData_new.setDbValue( "" );
                        headerIdData_new.setValue( "" );
                        subImportDataDTO.getCellDatas().add( headerIdData_new );

                     }
                  }
               }

            }

         }
      }
      return flag;
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      return false;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      return false;
   }
}
