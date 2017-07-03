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
   private int curRow = 0; //��ǰ��
   private int curCol = -1; //��ǰ������
   private int preCol = -1; //��һ��������
   private int titleRow = 0; //�����У�һ�������Ϊ0
   private int rowsize = 0; //����
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

   //excel��¼�в���������������������Ԫ���б�Ϊ��������һ��Ԫ�ؽ��в�����Ԫ��ΪString����
   // public abstract void optRows(int curRow, List<String> rowlist) throws SQLException ;

   //excel��¼�в�����������sheet����������������Ԫ���б�Ϊ��������sheet��һ��Ԫ�ؽ��в�����Ԫ��ΪString����
   public abstract void optRows( int sheetIndex, int curRow, List< CellData > rowlist ) throws SQLException;

   //ֻ����һ��sheet������sheetIdΪҪ������sheet��������1��ʼ��1-3
   public void processOneSheet( String filename, int sheetId ) throws Exception
   {
      OPCPackage pkg = OPCPackage.open( filename );
      XSSFReader r = new XSSFReader( pkg );
      SharedStringsTable sst = r.getSharedStringsTable();

      XMLReader parser = fetchSheetParser( r.getStylesTable(), sst );
      // rId2 found by processing the Workbook
      // ���� rId# �� rSheet# ����sheet
      InputStream sheet2 = r.getSheet( "rId" + sheetId );
      sheetIndex++;
      InputSource sheetSource = new InputSource( sheet2 );
      parser.parse( sheetSource );
      sheet2.close();
   }

   /** 
    * ��ȡ���й���������ڷ��� 
    * @param path 
    * @throws Exception 
    */
   /**
    * ���� excel �ļ�
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

      // c => ��Ԫ��
      if ( name.equals( "c" ) )
      {
         // �����һ��Ԫ���� SST ����������nextIsString���Ϊtrue
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
            // �����һ��Ԫ���� SST ������
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
      // v => ��Ԫ���ֵ�������Ԫ�����ַ�����v��ǩ��ֵΪ���ַ�����SST�е�����
      // ����Ԫ�����ݼ���rowlist�У�����֮ǰ��ȥ���ַ���ǰ��Ŀհ׷�
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
               // ����SST������ֵ�ĵ���Ԫ�������Ҫ�洢���ַ���
               // ��ʱcharacters()�������ܻᱻ���ö��
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
         //�����ǩ����Ϊ row ����˵���ѵ���β������ optRows() ����
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
      //�õ���Ԫ�����ݵ�ֵ
      if ( vIsOpen )
         lastContents.append( ch, start, length );
   }

   //�õ���������ÿһ��cԪ�ص�r���Թ���Ϊ��ĸ�����ֵ���ʽ����ĸ���Ϊ���������������Ϊ��������
   //��AB45,��ʾΪ�ڣ�A-A+1��*26+��B-A+1��*26�У�45��
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
