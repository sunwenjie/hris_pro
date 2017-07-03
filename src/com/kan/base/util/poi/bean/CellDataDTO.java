package com.kan.base.util.poi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.util.KANUtil;

public class CellDataDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 4601650379803266557L;

   private String tableId;

   private String tableName;

   // ��ʶ�Ƿ���Ҫ����(Ĭ�϶���Ҫinsert)
   private Boolean needInsert = true;

   private List< CellData > cellDatas = new ArrayList< CellData >();

   private List< CellDataDTO > subCellDataDTOs = new ArrayList< CellDataDTO >();

   //�м�������Ϣ
   private String errorMessange = "";

   // �м�������Ϣ
   private String extraMessage = "";

   public String getErrorMessange()
   {
      return errorMessange;
   }

   public void setErrorMessange( String errorMessange )
   {
      this.errorMessange = errorMessange;
   }

   public final String getTableId()
   {
      return tableId;
   }

   public final void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public final String getTableName()
   {
      return tableName;
   }

   public final void setTableName( String tableName )
   {
      this.tableName = tableName;
   }

   public final List< CellData > getCellDatas()
   {
      return cellDatas;
   }

   public final void setCellDatas( List< CellData > cellDatas )
   {
      this.cellDatas = cellDatas;
   }

   public final List< CellDataDTO > getSubCellDataDTOs()
   {
      return subCellDataDTOs;
   }

   public final void setSubCellDataDTOs( List< CellDataDTO > subCellDataDTOs )
   {
      this.subCellDataDTOs = subCellDataDTOs;
   }

   public boolean containColumnByNameDB( final ColumnVO columnVO )
   {
      if ( cellDatas != null && cellDatas.size() > 0 && columnVO != null && KANUtil.filterEmpty( columnVO.getNameDB() ) != null )
      {
         for ( CellData cell : cellDatas )
         {
            if ( cell.getColumnVO() != null && KANUtil.filterEmpty( cell.getColumnVO().getNameDB() ) != null && cell.getColumnVO().getNameDB().equals( columnVO.getNameDB() ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public boolean containColumnByName( final ColumnVO columnVO )
   {
      if ( cellDatas != null && cellDatas.size() > 0 && columnVO != null && KANUtil.filterEmpty( columnVO.getNameZH() ) != null )
      {
         String columnName = columnVO.getNameZH();

         if ( KANUtil.filterEmpty( columnName ) != null && columnName.contains( "-" ) && columnName.split( "-" ).length > 1 )
         {
            if ( columnName.contains( "��˾" ) || columnName.contains( "����" ) )
            {
               columnName = columnName.split( "-" )[ 0 ];
            }
            else
            {
               columnName = "-" + columnName.split( "-" )[ 1 ];
            }
         }

         for ( CellData cell : cellDatas )
         {
            if ( cell.getColumnVO() != null && KANUtil.filterEmpty( cell.getColumnVO().getNameZH() ) != null && cell.getColumnVO().getNameZH().contains( columnName ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public boolean containColumnId( final String columnId )
   {
      if ( cellDatas != null && cellDatas.size() > 0 )
      {
         for ( CellData cell : cellDatas )
         {
            if ( cell.getColumnVO() != null && cell.getColumnVO().getColumnId() != null && cell.getColumnVO().getColumnId().equals( columnId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public CellData getPrimaryKeyCellData()
   {
      if ( this.cellDatas != null )
      {

         for ( CellData cell : this.cellDatas )
         {
            if ( cell != null && cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
            {
               return cell;
            }
         }
      }
      return null;
   }

   public CellData getCellDataByColumnNameDB( final String columnNameDB )
   {
      if ( this.cellDatas != null )
      {
         for ( CellData cell : this.cellDatas )
         {
            if ( cell != null && cell.getColumnVO() != null && columnNameDB.equals( cell.getColumnVO().getNameDB() ) )
            {
               return cell;
            }
         }
      }

      return null;
   }

   /**
    * for salaryImport  for billAmountPersonal billA...CostAm...Cost...
    * ͨ��ʹ�������Ǹ�������
    * @param columnNameDB
    * @return
    */
   public CellData getCellDataByColumnNameDB( final String columnNameDB, final String name )
   {
      if ( this.cellDatas != null )
      {
         for ( CellData cell : this.cellDatas )
         {
            // ��Ϊ��4���ֶαȽ����⣬�������ڷ��鵼�����ݴ��У���������nameDB����
            if ( cell != null && cell.getColumnVO() != null && columnNameDB.equals( cell.getColumnVO().getNameDB() )
                  && ( name.equals( cell.getColumnVO().getNameZH() ) || name.equals( cell.getColumnVO().getNameEN() ) ) )
            {
               return cell;
            }
         }
      }

      return null;
   }

   public Object getCellDataDbValueByColumnNameDB( final String columnNameDB )
   {
      if ( this.cellDatas != null )
      {
         for ( CellData cell : this.cellDatas )
         {
            if ( cell != null && cell.getColumnVO() != null && columnNameDB.equals( cell.getColumnVO().getNameDB() ) )
            {
               return cell.getDbValue();
            }
         }
      }
      return null;
   }

   public void addCellDataDbValueByColumnNameDB( final String columnNameDB, final String dbValue )
   {
      if ( this.getCellDataByColumnNameDB( columnNameDB ) != null )
      {
         CellData cellData = this.getCellDataByColumnNameDB( columnNameDB );
         final String existDbvalue = ( String ) cellData.getDbValue();
         cellData.setDbValue( String.valueOf( Double.parseDouble( existDbvalue == null ? "0" : existDbvalue ) + Double.parseDouble( dbValue == null ? "0" : dbValue ) ) );
      }
   }

   public String getAccounId()
   {
      CellData accountIdCell = getCellDataByColumnNameDB( "accountId" );
      if ( accountIdCell != null )
      {
         return accountIdCell.getDbValue().toString();
      }
      return null;
   }

   public String getCorpId()
   {
      CellData clientIdCell = getCellDataByColumnNameDB( "corpId" );
      if ( clientIdCell != null )
      {
         return clientIdCell.getDbValue() != null ? clientIdCell.getDbValue().toString() : null;
      }
      return null;
   }

   public Boolean getNeedInsert()
   {
      return needInsert;
   }

   public void setNeedInsert( Boolean needInsert )
   {
      this.needInsert = needInsert;
   }

   public String getExtraMessage()
   {
      return extraMessage;
   }

   public void setExtraMessage( String extraMessage )
   {
      this.extraMessage = extraMessage;
   }

   // ���� CellDataDTO �е� CellDatas ʱ�����ǿͻ��˴���ͬ��� CellDatas ����ֹ�ظ����
   public void addCellData( final CellData cellData )
   {
      if ( cellData != null )
      {
         final CellData tempCellData = getCellDataByColumnNameDB( cellData.getColumnVO().getNameDB() );
         // ����ͻ��˶�Ӧ��  CellData �Ѿ�����
         if ( tempCellData != null )
         {
            tempCellData.setJdbcDataType( cellData.getJdbcDataType() );
            tempCellData.setDbValue( cellData.getDbValue() );
         }
         else
         {
            this.getCellDatas().add( cellData );
         }
      }
   }

}
