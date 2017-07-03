package com.kan.hro.service.impl.biz.vendor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class VendorSBImportHandlerImpl implements ExcelImportHandler< List< CellDataDTO > >
{
   private EmployeeContractDao employeeContractDao;

   private EmployeeContractSBDao employeeContractSBDao;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   @Override
   public void init( final List< CellDataDTO > importData )
   {
      // ��ʼ�� ��Ҫ�������ݱ��Ϊ��ʱ��
      if ( importData != null )
      {
         for ( CellDataDTO cellDataDTO : importData )
         {
            cellDataDTO.setTableName( cellDataDTO.getTableName() + "_TEMP" );

            if ( cellDataDTO.getSubCellDataDTOs() == null )
               continue;
            for ( CellDataDTO subcellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               subcellDataDTO.setTableName( subcellDataDTO.getTableName() + "_TEMP" );
            }

         }
      }
   }

   @Override
   public boolean excuteBeforInsert( final List< CellDataDTO > importDatas, final HttpServletRequest request )
   {
      boolean flag = true;
      if ( importDatas != null && importDatas.size() > 0 )
      {
         String accountId = importDatas.get( 0 ).getAccounId();
         String corpId = importDatas.get( 0 ).getCorpId();
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< ItemVO > allItemVOs = accountConstants.getItemVOsByCorpId( corpId );

         for ( CellDataDTO importDataDTO : importDatas )
         {
            if ( importDataDTO != null )
            {
               CellData employeeNameZHCellData = null;
               CellData employeeNameENCellData = null;
               CellData orderIdCellData = null;
               CellData startDateCellData = null;
               CellData endDateCellData = null;
               CellData needMedicalCardCellData = null;
               CellData needSBCardCellData = null;
               CellData medicalNumberCellData = null;
               CellData sbNumberCellData = null;
               CellData fundNumberCellData = null;

               // ��ʼ���Ͷ���ͬ
               EmployeeContractVO employeeContractVO = new EmployeeContractVO();
               EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();

               for ( CellData cellData : importDataDTO.getCellDatas() )
               {
                  if ( cellData != null )
                  {
                     if ( "monthly".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        final String monthly = cellData.getValue();
                        // �·ݲ���Ϊ��
                        if ( monthly == null )
                        {
                           flag = false;
                           cellData.setErrorMessange( cellData.getColumnVO().getNameZH() + "����Ϊ��" );
                        }
                     }

                     if ( "contractId".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        final String contractId = cellData.getValue();
                        // �Ͷ���ͬID����Ϊ��
                        if ( contractId == null )
                        {
                           flag = false;
                           cellData.setErrorMessange( cellData.getColumnVO().getNameZH() + "����Ϊ��" );
                        }
                        else
                        {

                           // ����employeeID��ѯ��Ӧ��EmployeeVO
                           try
                           {
                              employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( contractId );
                              if ( employeeContractVO == null )
                              {
                                 flag = false;
                                 cellData.setErrorMessange( "δ�ҵ���Ч��" + cellData.getColumnVO().getNameZH() + "[ID:" + contractId + "]" );
                              }
                           }
                           catch ( KANException e )
                           {
                              e.printStackTrace();
                           }
                        }
                     }
                     else if ( "employeeNameZH".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        employeeNameZHCellData = cellData;
                     }
                     else if ( "employeeNameEN".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        employeeNameENCellData = cellData;
                     }
                     else if ( "orderId".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        orderIdCellData = cellData;
                     }

                     if ( "employeeSBId".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        final String employeeSBId = cellData.getValue();

                        if ( employeeSBId == null )
                        {
                           flag = false;
                           cellData.setErrorMessange( cellData.getColumnVO().getNameZH() + "����Ϊ��" );
                        }
                        else
                        {
                           try
                           {
                              employeeContractSBVO = this.employeeContractSBDao.getEmployeeContractSBVOByEmployeeSBId( employeeSBId );
                              if ( employeeContractSBVO == null )
                              {
                                 flag = false;
                                 cellData.setErrorMessange( "�籣����ID��Ч[" + employeeSBId + "]" );
                              }
                           }
                           catch ( KANException e )
                           {
                              e.printStackTrace();
                           }
                        }

                     }
                     else if ( "startDate".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        startDateCellData = cellData;
                     }
                     else if ( "endDate".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        endDateCellData = cellData;
                     }
                     else if ( "needMedicalCard".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        needMedicalCardCellData = cellData;
                     }
                     else if ( "needSBCard".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        needSBCardCellData = cellData;
                     }
                     else if ( "medicalNumber".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        medicalNumberCellData = cellData;
                     }
                     else if ( "sbNumber".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        sbNumberCellData = cellData;
                     }
                     else if ( "fundNumber".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        fundNumberCellData = cellData;
                     }
                  }
               }

               /** �����Ա��Ϣδ���á�����empoyeeID��ѯ��employeeVO ��Ȼ������employeeNameZHCellData��employeeNameENCellData��DBValueΪemployeeVO��NameZH��NameEN */
               if ( employeeContractVO != null )
               {
                  // �����Ա������
                  if ( employeeNameZHCellData == null )
                  {
                     employeeNameZHCellData = new CellData();

                     final ColumnVO employeeContractZHColumnVO = new ColumnVO();
                     employeeContractZHColumnVO.setNameDB( "employeeNameZH" );
                     employeeContractZHColumnVO.setIsForeignKey( "2" );
                     employeeContractZHColumnVO.setAccountId( "1" );
                     employeeContractZHColumnVO.setIsDBColumn( "1" );

                     employeeNameZHCellData.setColumnVO( employeeContractZHColumnVO );
                     employeeNameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     employeeNameZHCellData.setDbValue( employeeContractVO.getNameZH() );

                     importDataDTO.getCellDatas().add( employeeNameZHCellData );
                  }

                  // �����ԱӢ����
                  if ( employeeNameENCellData == null )
                  {
                     employeeNameENCellData = new CellData();

                     final ColumnVO employeeNameENColumnVO = new ColumnVO();
                     employeeNameENColumnVO.setNameDB( "employeeNameEN" );
                     employeeNameENColumnVO.setIsForeignKey( "2" );
                     employeeNameENColumnVO.setAccountId( "1" );
                     employeeNameENColumnVO.setIsDBColumn( "1" );

                     employeeNameENCellData.setColumnVO( employeeNameENColumnVO );
                     employeeNameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     employeeNameENCellData.setDbValue( employeeContractVO.getNameEN() );

                     importDataDTO.getCellDatas().add( employeeNameENCellData );
                  }

                  // ���䶩��ID
                  if ( orderIdCellData == null )
                  {
                     orderIdCellData = new CellData();

                     final ColumnVO orderIdColumnVO = new ColumnVO();
                     orderIdColumnVO.setNameDB( "orderId" );
                     orderIdColumnVO.setIsForeignKey( "2" );
                     orderIdColumnVO.setAccountId( "1" );
                     orderIdColumnVO.setIsDBColumn( "1" );

                     orderIdCellData.setColumnVO( orderIdColumnVO );
                     orderIdCellData.setJdbcDataType( JDBCDataType.INT );
                     orderIdCellData.setDbValue( employeeContractVO.getOrderId() );

                     importDataDTO.getCellDatas().add( orderIdCellData );
                  }
               }

               /** �����̱�������Ϣ */
               if ( employeeContractSBVO != null )
               {
                  // �ӱ�����
                  if ( startDateCellData == null )
                  {
                     startDateCellData = new CellData();

                     final ColumnVO startDateColumnVO = new ColumnVO();
                     startDateColumnVO.setNameDB( "startDate" );
                     startDateColumnVO.setIsForeignKey( "2" );
                     startDateColumnVO.setAccountId( "1" );
                     startDateColumnVO.setIsDBColumn( "1" );

                     startDateCellData.setColumnVO( startDateColumnVO );
                     startDateCellData.setJdbcDataType( JDBCDataType.DATE );
                     startDateCellData.setDbValue( employeeContractSBVO.getStartDate() );

                     importDataDTO.getCellDatas().add( startDateCellData );
                  }

                  // �˱�����
                  if ( endDateCellData == null )
                  {
                     endDateCellData = new CellData();

                     final ColumnVO endDateColumnVO = new ColumnVO();
                     endDateColumnVO.setNameDB( "endDate" );
                     endDateColumnVO.setIsForeignKey( "2" );
                     endDateColumnVO.setAccountId( "1" );
                     endDateColumnVO.setIsDBColumn( "1" );

                     endDateCellData.setColumnVO( endDateColumnVO );
                     endDateCellData.setJdbcDataType( JDBCDataType.DATE );
                     endDateCellData.setDbValue( employeeContractSBVO.getEndDate() );

                     importDataDTO.getCellDatas().add( endDateCellData );
                  }

                  // ��Ҫ����ҽ����
                  if ( needMedicalCardCellData == null )
                  {
                     needMedicalCardCellData = new CellData();

                     final ColumnVO needMedicalCardColumnVO = new ColumnVO();
                     needMedicalCardColumnVO.setNameDB( "needMedicalCard" );
                     needMedicalCardColumnVO.setIsForeignKey( "2" );
                     needMedicalCardColumnVO.setAccountId( "1" );
                     needMedicalCardColumnVO.setIsDBColumn( "1" );

                     needMedicalCardCellData.setColumnVO( needMedicalCardColumnVO );
                     needMedicalCardCellData.setJdbcDataType( JDBCDataType.INT );
                     needMedicalCardCellData.setDbValue( employeeContractSBVO.getNeedMedicalCard() );

                     importDataDTO.getCellDatas().add( needMedicalCardCellData );
                  }

                  // ��Ҫ�����籣��
                  if ( needSBCardCellData == null )
                  {
                     needSBCardCellData = new CellData();

                     final ColumnVO needSBCardColumnVO = new ColumnVO();
                     needSBCardColumnVO.setNameDB( "needSBCard" );
                     needSBCardColumnVO.setIsForeignKey( "2" );
                     needSBCardColumnVO.setAccountId( "1" );
                     needSBCardColumnVO.setIsDBColumn( "1" );

                     needSBCardCellData.setColumnVO( needSBCardColumnVO );
                     needSBCardCellData.setJdbcDataType( JDBCDataType.INT );
                     needSBCardCellData.setDbValue( employeeContractSBVO.getNeedSBCard() );

                     importDataDTO.getCellDatas().add( needSBCardCellData );
                  }

                  // ҽ�����ʺ�
                  if ( medicalNumberCellData == null )
                  {
                     medicalNumberCellData = new CellData();

                     final ColumnVO medicalNumberColumnVO = new ColumnVO();
                     medicalNumberColumnVO.setNameDB( "medicalNumber" );
                     medicalNumberColumnVO.setIsForeignKey( "2" );
                     medicalNumberColumnVO.setAccountId( "1" );
                     medicalNumberColumnVO.setIsDBColumn( "1" );

                     medicalNumberCellData.setColumnVO( medicalNumberColumnVO );
                     medicalNumberCellData.setJdbcDataType( JDBCDataType.INT );
                     medicalNumberCellData.setDbValue( employeeContractSBVO.getMedicalNumber() );

                     importDataDTO.getCellDatas().add( medicalNumberCellData );
                  }

                  // �籣���ʺ�
                  if ( sbNumberCellData == null )
                  {
                     sbNumberCellData = new CellData();

                     final ColumnVO sbNumberColumnVO = new ColumnVO();
                     sbNumberColumnVO.setNameDB( "sbNumber" );
                     sbNumberColumnVO.setIsForeignKey( "2" );
                     sbNumberColumnVO.setAccountId( "1" );
                     sbNumberColumnVO.setIsDBColumn( "1" );

                     sbNumberCellData.setColumnVO( sbNumberColumnVO );
                     sbNumberCellData.setJdbcDataType( JDBCDataType.INT );
                     sbNumberCellData.setDbValue( employeeContractSBVO.getSbNumber() );

                     importDataDTO.getCellDatas().add( sbNumberCellData );
                  }

                  // �������ʺ�
                  if ( fundNumberCellData == null )
                  {
                     fundNumberCellData = new CellData();

                     final ColumnVO fundNumberColumnVO = new ColumnVO();
                     fundNumberColumnVO.setNameDB( "fundNumber" );
                     fundNumberColumnVO.setIsForeignKey( "2" );
                     fundNumberColumnVO.setAccountId( "1" );
                     fundNumberColumnVO.setIsDBColumn( "1" );

                     fundNumberCellData.setColumnVO( fundNumberColumnVO );
                     fundNumberCellData.setJdbcDataType( JDBCDataType.INT );
                     fundNumberCellData.setDbValue( employeeContractSBVO.getFundNumber() );

                     importDataDTO.getCellDatas().add( fundNumberCellData );
                  }

               }

               if ( importDataDTO.getSubCellDataDTOs() != null )
               {
                  for ( CellDataDTO subImportDataDTO : importDataDTO.getSubCellDataDTOs() )
                  {
                     addDetailTableColumns( subImportDataDTO, allItemVOs );
                  }
               }
            }
         }
      }
      return flag;
   }

   private void addDetailTableColumns( final CellDataDTO subImportDataDTO, final List< ItemVO > allItemVOs )
   {
      List< CellData > cellDatas = subImportDataDTO.getCellDatas();
      List< CellData > addCellDatas = new ArrayList< CellData >();

      if ( cellDatas != null && cellDatas.size() > 0 )
      {

         String[][] copyPropertys = { { "itemId", "INT" }, { "itemNo", "VARCHAR" } };
         // ֻ����һ��
         CellData cellData = cellDatas.get( 0 );
         ColumnVO foreignKeyCellColumnVO = new ColumnVO();
         foreignKeyCellColumnVO.setNameDB( "headerId" );
         foreignKeyCellColumnVO.setIsForeignKey( "1" );
         foreignKeyCellColumnVO.setAccountId( "1" );//1 1�������ݿ��ֶΣ�����������account���Զ����ֶ�
         foreignKeyCellColumnVO.setIsDBColumn( "1" );

         CellData foreignKeyCellData = new CellData();
         foreignKeyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
         foreignKeyCellData.setColumnVO( foreignKeyCellColumnVO );
         addCellDatas.add( foreignKeyCellData );

         // ��ͷ�ַ���
         String headeCellValue1 = cellData.getColumnVO().getNameZH();

         // ���˵��Զ���ӵ�createBy��modifyBy��Ӧ�ĵ�Ԫ��
         if ( headeCellValue1 != null )
         {
            String headeCellValue = headeCellValue1.split( "-" )[ 0 ];
            // ���Item��Ϣ
            ItemVO matchItemVO = null;

            for ( ItemVO itemVO : allItemVOs )
            {
               if ( itemVO.getNameZH().equalsIgnoreCase( headeCellValue ) )
               {
                  matchItemVO = itemVO;
                  break;
               }
            }

            if ( matchItemVO != null )
            {
               for ( String[] property : copyPropertys )
               {

                  ColumnVO addCellColumnVO = new ColumnVO();
                  addCellColumnVO.setNameDB( property[ 0 ] );
                  addCellColumnVO.setIsForeignKey( "2" );
                  addCellColumnVO.setAccountId( "1" );
                  addCellColumnVO.setIsDBColumn( "1" );

                  CellData addCellData = new CellData();
                  addCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( property[ 1 ] ) );
                  addCellData.setColumnVO( addCellColumnVO );

                  try
                  {
                     addCellData.setDbValue( BeanUtils.getProperty( matchItemVO, property[ 0 ] ) );
                  }
                  catch ( Exception e )
                  {
                     e.printStackTrace();
                  }
                  // �������
                  addCellDatas.add( addCellData );
               }
            }
         }
         cellDatas.addAll( addCellDatas );
      }
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      return false;
   }

}
