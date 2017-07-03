package com.kan.base.util.poi;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellRefUtil;
import com.kan.base.util.poi.bean.XssfDataType;

public abstract class ReadXlsxHander extends DefaultHandler
{
   private SharedStringsTable sst;
   private StylesTable styles;
   private StringBuffer lastContents = new StringBuffer();
   private XssfDataType nextDataType;
   private int sheetIndex = -1;
   private List< CellData > rowlist = new ArrayList< CellData >();
   private int curRow = 0; //当前行
   private int curCol = -1; //当前列索引
   private int preCol = -1; //上一列列索引
   private int titleRow = 0; //标题行，一般情况下为0
   private int rowsize = 0; //列数
   private String cellRef;
   // Set when V start element is seen
   private boolean vIsOpen;

   // Used to format numeric cell values.
   private short formatIndex;
   private String formatString;
   private final DataFormatter formatter;

   public ReadXlsxHander()
   {
      this.formatter = new DataFormatter();
   }

   //excel记录行操作方法，以行索引和行元素列表为参数，对一行元素进行操作，元素为String类型
   // public abstract void optRows(int curRow, List<String> rowlist) throws SQLException ;

   //excel记录行操作方法，以sheet索引，行索引和行元素列表为参数，对sheet的一行元素进行操作，元素为String类型
   public abstract void optRows( int sheetIndex, int curRow, List< CellData > rowlist ) throws SQLException;

   //只遍历一个sheet，其中sheetId为要遍历的sheet索引，从1开始，1-3
   public void processOneSheet( String filename, int sheetId ) throws Exception
   {
      OPCPackage pkg = OPCPackage.open( filename );
      XSSFReader r = new XSSFReader( pkg );
      SharedStringsTable sst = r.getSharedStringsTable();

      XMLReader parser = fetchSheetParser( r.getStylesTable(), sst );
      // rId2 found by processing the Workbook
      // 根据 rId# 或 rSheet# 查找sheet
      InputStream sheet2 = r.getSheet( "rId" + sheetId );
      sheetIndex++;
      InputSource sheetSource = new InputSource( sheet2 );
      parser.parse( sheetSource );
      sheet2.close();
   }

   /** 
    * 读取所有工作簿的入口方法 
    * @param path 
    * @throws Exception 
    */
   /**
    * 遍历 excel 文件
    */
   public void process( String filename ) throws Exception
   {
      OPCPackage pkg = OPCPackage.open( filename );
      XSSFReader r = new XSSFReader( pkg );
      SharedStringsTable sst = r.getSharedStringsTable();
      StylesTable styles = r.getStylesTable();
      XMLReader parser = fetchSheetParser( styles, sst );
      Iterator< InputStream > sheets = r.getSheetsData();
      while ( sheets.hasNext() )
      {
         curRow = 0;
         sheetIndex++;
         InputStream sheet = sheets.next();
         InputSource sheetSource = new InputSource( sheet );
         parser.parse( sheetSource );
         sheet.close();
      }
   }

   public XMLReader fetchSheetParser( StylesTable styles, SharedStringsTable sst ) throws SAXException
   {
      XMLReader parser = XMLReaderFactory.createXMLReader( "org.apache.xerces.parsers.SAXParser" );
      this.sst = sst;
      this.styles = styles;
      parser.setContentHandler( this );
      return parser;
   }

   public void startElement( String uri, String localName, String name, Attributes attributes ) throws SAXException
   {

      if ( "inlineStr".equals( name ) || "v".equals( name ) )
      {
         vIsOpen = true;
         // Clear contents cache
         lastContents.setLength( 0 );
      }
      else

      // c => 单元格
      if ( name.equals( "c" ) )
      {
         // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
         String cellType = attributes.getValue( "t" );
         String rowStr = attributes.getValue( "r" );
         this.cellRef = rowStr;
         String cellStyleStr = attributes.getValue( "s" );

         curCol = nameToColumn( rowStr );

         this.nextDataType = XssfDataType.NUMBER;
         if ( "b".equals( cellType ) )
            nextDataType = XssfDataType.BOOL;
         else if ( "e".equals( cellType ) )
            nextDataType = XssfDataType.ERROR;
         else if ( "inlineStr".equals( cellType ) )
            nextDataType = XssfDataType.INLINESTR;
         else if ( "s".equals( cellType ) )
            // 如果下一个元素是 SST 的索引
            nextDataType = XssfDataType.SSTINDEX;
         else if ( "str".equals( cellType ) )
            nextDataType = XssfDataType.FORMULA;
         else if ( cellStyleStr != null )
         {
            // It's a number, but almost certainly one
            //  with a special style or format 
            int styleIndex = Integer.parseInt( cellStyleStr );
            XSSFCellStyle style = this.styles.getStyleAt( styleIndex );
            this.formatIndex = style.getDataFormat();
            this.formatString = style.getDataFormatString();
            if ( this.formatString == null )
               this.formatString = BuiltinFormats.getBuiltinFormat( this.formatIndex );
         }
      }

   }

   public void endElement( String uri, String localName, String name ) throws SAXException
   {
      String thisStr = null;
      // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
      // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
      if ( name.equals( "v" ) )
      {

         int cols = curCol - preCol;
         if ( cols > 1 )
         {
            for ( int i = 1; i < cols; i++ )
            {
               CellData cellData = new CellData();
               cellData.setCellRef( CellRefUtil.cellRefAdd( cellRef, 0, i - cols ) );
               cellData.setColumn( preCol + i );
               cellData.setRow( curRow );
               rowlist.add( preCol + i, cellData );
            }
         }
         preCol = curCol;
         CellData cellData = new CellData();
         cellData.setCellRef( cellRef );
         cellData.setColumn( curCol );
         cellData.setRow( curRow );
         cellData.setFormatStr( formatString );
         cellData.setDataType( nextDataType );

         cellData.setValue( lastContents.toString() );
         // Process the value contents as required.
         // Do now, as characters() may be called more than once
         switch ( nextDataType )
         {

            case BOOL:
               char first = lastContents.charAt( 0 );
               thisStr = first == '0' ? "FALSE" : "TRUE";
               break;

            case ERROR:
               thisStr = "\"ERROR:" + lastContents.toString() + '"';
               break;

            case FORMULA:
               // A formula could result in a string value,
               // so always add double-quote characters.
               thisStr = lastContents.toString();
               break;

            case INLINESTR:
               // TODO: have seen an example of this, so it's untested.
               XSSFRichTextString rtsi = new XSSFRichTextString( lastContents.toString() );
               thisStr = '"' + rtsi.toString() + '"';
               break;

            case SSTINDEX:
               // 根据SST的索引值的到单元格的真正要存储的字符串
               // 这时characters()方法可能会被调用多次
               String sstIndex = lastContents.toString();
               try
               {
                  int idx = Integer.parseInt( sstIndex );
                  XSSFRichTextString rtss = new XSSFRichTextString( sst.getEntryAt( idx ) );
                  thisStr = rtss.toString();
                  cellData.setValue( thisStr );
               }
               catch ( NumberFormatException ex )
               {
                  //output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
                  throw ex;
               }
               break;

            case NUMBER:
               String n = lastContents.toString();
               if ( this.formatString != null )
                  thisStr = formatter.formatRawCellContents( Double.parseDouble( n ), this.formatIndex, this.formatString );

               else
                  thisStr = n;
               break;

            default:
               thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
               break;
         }

         cellData.setFormateValue( thisStr );
         rowlist.add( cellData );
         formatString = null;
      }
      else
      {
         //如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
         if ( name.equals( "row" ) )
         {
            int tmpCols = rowlist.size();
            if ( curRow > this.titleRow && tmpCols < this.rowsize )
            {
               for ( int i = 0; i < this.rowsize - tmpCols; i++ )
               {
                  CellData cellData = new CellData();
                  cellData.setCellRef( CellRefUtil.cellRefAdd( null, curRow, preCol + i + 1 ) );
                  cellData.setColumn( preCol + i + 1 );
                  cellData.setRow( curRow );
                  rowlist.add( cellData );
               }
            }
            try
            {
               optRows( sheetIndex, curRow, rowlist );
            }
            catch ( SQLException e )
            {
               e.printStackTrace();
            }
            if ( curRow == this.titleRow )
            {
               this.rowsize = rowlist.size();
            }
            rowlist = new ArrayList< CellData >();
            curRow++;
            curCol = -1;
            preCol = -1;
         }
      }
   }

   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      //得到单元格内容的值
      if ( vIsOpen )
         lastContents.append( ch, start, length );
   }

   //得到列索引，每一列c元素的r属性构成为字母加数字的形式，字母组合为列索引，数字组合为行索引，
   //如AB45,表示为第（A-A+1）*26+（B-A+1）*26列，45行
   public int getRowIndex( String rowStr )
   {
      rowStr = rowStr.replaceAll( "[^A-Z]", "" );
      byte[] rowAbc = rowStr.getBytes();
      int len = rowAbc.length;
      float num = 0;
      for ( int i = 0; i < len; i++ )
      {
         num += ( rowAbc[ i ] - 'A' + 1 ) * Math.pow( 26, len - i - 1 );
      }
      return ( int ) num;
   }

   public int getTitleRow()
   {
      return titleRow;
   }

   public void setTitleRow( int titleRow )
   {
      this.titleRow = titleRow;
   }

   /**
    * Converts an Excel column name like "C" to a zero-based index.
    *
    * @param name
    * @return Index corresponding to the specified name
    */
   private int nameToColumn( String rowStr )
   {
      String name;
      int firstDigit = -1;
      for ( int c = 0; c < rowStr.length(); ++c )
      {
         if ( Character.isDigit( rowStr.charAt( c ) ) )
         {
            firstDigit = c;
            break;
         }
      }

      name = rowStr.substring( 0, firstDigit );

      int column = -1;
      for ( int i = 0; i < name.length(); ++i )
      {
         int c = name.charAt( i );
         column = ( column + 1 ) * 26 + c - 'A';
      }
      return column;
   }
}
