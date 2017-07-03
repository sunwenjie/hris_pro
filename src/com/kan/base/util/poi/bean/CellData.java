package com.kan.base.util.poi.bean;

import org.apache.poi.hssf.util.CellReference;

import com.kan.base.domain.define.ColumnVO;

public class CellData
{
   /**
    * ���õĵ�Ԫ���磺A3
    */
   private String cellRef;

   /**
    * ��
    */
   private int row;

   /***
    * ��
    */
   private int column;

   /**
    * ��Ԫ����������
    */
   private XssfDataType dataType;

   /***
    * ��Ԫ���ֵ
    */
   private String value;

   /***
    * ��ʽ�����ֵ
    */
   private String formateValue;

   /**
    * ��ʽ��ʽ
    */
   private String formatStr;

   /**
    * ���ݿ�����
    */
   private JDBCDataType jdbcDataType;

   /**
    * ���뵽���ݿ��ֵ
    */
   private Object dbValue;

   //��Ԫ����֤������Ϣ
   private String errorMessange;

   // �м�������Ϣ
   private String extraMessage;

   public String getErrorMessange()
   {
      return errorMessange;
   }

   public void setErrorMessange( String errorMessange )
   {
      this.errorMessange = errorMessange;
   }

   /**
    * �ֶ���ţ���Excel�е�������
    */
   private String columnIndex;

   private ColumnVO columnVO;

   public CellData()
   {
      super();
   }

   public CellData( String cellRef, int row, int column, XssfDataType dataType, String value, String formateValue )
   {
      super();
      this.cellRef = cellRef;
      this.row = row;
      this.column = column;
      this.dataType = dataType;
      this.value = value;
      this.formateValue = formateValue;
   }

   public String getCellRef()
   {
      return cellRef;
   }

   public void setCellRef( String cellRef )
   {
      this.cellRef = cellRef;
   }

   public int getRow()
   {
      return row;
   }

   public void setRow( int row )
   {
      this.row = row;
   }

   public int getColumn()
   {
      return column;
   }

   public void setColumn( int column )
   {
      this.column = column;
   }

   public XssfDataType getDataType()
   {
      return dataType;
   }

   public void setDataType( XssfDataType dataType )
   {
      this.dataType = dataType;
   }

   public String getValue()
   {
      return value;
   }

   public void setValue( String value )
   {
      this.value = value;
   }

   public String getFormatStr()
   {
      return formatStr;
   }

   public void setFormatStr( String formatStr )
   {
      this.formatStr = formatStr;
   }

   /**
    * ���ص�Ԫ����б꣨��1��ʼ��
    * @param cellRef �磺B5
    * @return  5
    */
   public int getCellRow()
   {
      CellReference ref = new CellReference( cellRef );
      return ref.getRow() + 1;
   }

   /**
    * ���ص�Ԫ����б꣨��1��ʼ��
    * @param cellRef �磺B5
    * @return  2
    */
   public int getCellCol()
   {
      CellReference ref = new CellReference( cellRef );
      return ref.getCol() + 1;
   }

   public String getFormateValue()
   {
      return formateValue;
   }

   public void setFormateValue( String formateValue )
   {
      this.formateValue = formateValue;
   }

   public JDBCDataType getJdbcDataType()
   {
      return jdbcDataType;
   }

   public void setJdbcDataType( JDBCDataType jdbcDataType )
   {
      this.jdbcDataType = jdbcDataType;
   }

   public Object getDbValue()
   {
      return dbValue;
   }

   public void setDbValue( Object dbValue )
   {
      this.dbValue = dbValue;
   }

   public final String getColumnIndex()
   {
      return columnIndex;
   }

   public final void setColumnIndex( String columnIndex )
   {
      this.columnIndex = columnIndex;
   }

   public ColumnVO getColumnVO()
   {
      return columnVO;
   }

   public void setColumnVO( ColumnVO columnVO )
   {
      this.columnVO = columnVO;
   }

   @Override
   public String toString()
   {
      return "CellData [cellRef=" + cellRef + ", row=" + row + ", column=" + column + ", dataType=" + dataType + ", value=" + value + ", formateValue=" + formateValue
            + ", formatStr=" + formatStr + ", jdbcDataType=" + jdbcDataType + ", dbValue=" + dbValue + "]";
   }

   public String getExtraMessage()
   {
      return extraMessage;
   }

   public void setExtraMessage( String extraMessage )
   {
      this.extraMessage = extraMessage;
   }

}
