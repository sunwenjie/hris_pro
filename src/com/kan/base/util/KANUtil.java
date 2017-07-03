/*
 * Created on 2005-6-25
 */
package com.kan.base.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class KANUtil extends BaseAction
{

   public static String appPath;

   public static String basePath;

   public static String fileUploadBase;

   public static String tempFileUploadBase;

   public static String fileDownloadBase;

   public static String tempFileDownloadBase;

   public static JsonConfig jsonConfig = new JsonConfig();

   static
   {
      final URL url = KANUtil.class.getResource( "/" );
      final String filePath = url.getFile();
      basePath = filePath.substring( 0, filePath.indexOf( "/WEB-INF/classes" ) );
      appPath = basePath + "/WEB-INF";
      fileUploadBase = "/usr/file/upload";
      tempFileUploadBase = "/usr/file/upload/temp";
      fileDownloadBase = "/usr/file/download";
      tempFileDownloadBase = "/usr/file/download/temp";

      // ��ʼ��Ŀ¼
      createFolder( fileUploadBase );
      createFolder( tempFileUploadBase );
      createFolder( fileDownloadBase );
      createFolder( tempFileDownloadBase );

      // ��ʼ��jsonConfig
      jsonConfig.setJsonPropertyFilter( new PropertyFilter()
      {
         public boolean apply( Object source, String name, Object value )
         {
            //������decode��ͨ������ ���� locale ����
            if ( name.startsWith( "decode" ) || name.equals( "locale" )
            /// || MappingVO.class.isAssignableFrom(  source.getClass() )
            )
            {
               return true;
            }
            else if ( value != null && List.class.isAssignableFrom( value.getClass() ) )
            {
               List< ? > list = ( List< ? > ) value;
               if ( list == null || list.size() == 0 )
               {
                  return true;
               }
               else if ( MappingVO.class.isAssignableFrom( list.get( 0 ).getClass() ) )
               {
                  return true;
               }
            }
            else if ( value == null )
            {
               return true;
            }
            return false;
         }
      } );
   }

   public static void createFolder( final String folderName )
   {
      final File folder = new File( folderName );
      if ( !folder.exists() )
      {
         folder.mkdirs();
      }
   }

   public static void deleteFolder( final String folderName )
   {
      final File folder = new File( folderName );
      if ( !folder.exists() )
      {
         folder.delete();
      }
   }

   // ɾ���ǿյ��ļ���
   public static boolean deleteDir( File dir )
   {
      if ( dir.isDirectory() )
      {
         String[] children = dir.list();
         for ( int i = 0; i < children.length; i++ )
         {
            // �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
            boolean success = deleteDir( new File( dir, children[ i ] ) );
            if ( !success )
            {
               return false;
            }
         }
      }
      // Ŀ¼��ʱΪ�գ�����ɾ��
      return dir.delete();
   }

   public static void deleteFile( final String fileFolder, final String id )
   {
      final File file = new File( basePath + "/" + fileFolder + "/" + id + ".txt" );
      if ( file.exists() )
      {
         file.delete();
      }
   }

   public static String modifyFileName( final String location, final String fileName )
   {
      final File file = new File( basePath + "/" + location + fileName );
      final String type = fileName.substring( fileName.lastIndexOf( "." ) );
      final String returnName = RandomUtil.getRandomString( 10 );
      if ( file.exists() )
      {
         file.renameTo( new File( basePath + "/" + location + returnName + type ) );
      }
      return returnName + type;
   }

   public static int compareDate( final String date1, final String date2 )
   {
      return compareDate( date1, date2, "yyyy/MM" );
   }

   /**
    * ����-1 ��ʾ��һ������
    * ����1 ��ʾ����һ�����ֱȽϴ�
    * @param date1
    * @param date2
    * @param formatStr
    * @return
    */
   public static int compareDate( final String date1, final String date2, final String formatStr )
   {
      try
      {
         DateFormat df = new SimpleDateFormat( formatStr, Locale.CHINA );
         final Date d1 = df.parse( date1 );
         final Date d2 = df.parse( date2 );
         if ( d1.before( d2 ) )
         {
            return 1;
         }
         else if ( d1.after( d2 ) )
         {
            return -1;
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         System.err.println( "KANUtil.compareDate��ʽ��ʱ�����" );
      }
      return 0;
   }

   public static Date getEmptyDate()
   {
      final Calendar calendar = Calendar.getInstance();
      calendar.set( 1900, 0, 1, 0, 0, 0 );
      return calendar.getTime();
   }

   public static Date createDate( final String year, final String month, final String day )
   {
      final Calendar calendar = Calendar.getInstance();
      calendar.set( Integer.valueOf( year ).intValue(), Integer.valueOf( month ).intValue() - 1, Integer.valueOf( day ).intValue(), 0, 0, 0 );
      return calendar.getTime();
   }

   public static Date createDate( final String datetime )
   {
      return createCalendar( datetime ).getTime();
   }

   public static Calendar createCalendar( final String datetime )
   {
      final Calendar calendar = Calendar.getInstance();

      if ( KANUtil.filterEmpty( datetime ) == null )
      {
         return calendar;
      }
      else
      {
         String[] dateSplit = null;
         String[] timeSplit = null;
         String sourceDate = null;
         String sourceTime = null;

         if ( datetime.split( " " ).length > 0 )
         {
            sourceDate = datetime.split( " " )[ 0 ];
         }

         if ( datetime.split( " " ).length > 1 )
         {
            sourceTime = datetime.split( " " )[ 1 ];
         }

         // ���ղ�ͬ���ڸ�ʽ�ָ�������
         if ( sourceDate != null )
         {
            if ( sourceDate.contains( "-" ) )
            {
               dateSplit = sourceDate.split( "-" );
            }
            else if ( sourceDate.contains( "/" ) )
            {
               dateSplit = sourceDate.split( "/" );
            }
            else if ( sourceDate.contains( "." ) )
            {
               dateSplit = sourceDate.split( "\\." );
            }
         }

         // ���ղ�ͬ���ڸ�ʽ�ָ�ʱ����
         if ( sourceTime != null )
         {
            if ( sourceTime.contains( ":" ) )
            {
               timeSplit = sourceTime.split( ":" );
            }
         }

         calendar.set( Calendar.YEAR, 0 );
         calendar.set( Calendar.MONTH, 0 );
         calendar.set( Calendar.DATE, 0 );

         if ( dateSplit != null && dateSplit.length > 0 )
         {
            if ( KANUtil.filterEmpty( dateSplit[ 0 ] ) != null )
            {
               calendar.set( Calendar.YEAR, Integer.valueOf( dateSplit[ 0 ] ) );
            }
         }

         if ( dateSplit != null && dateSplit.length > 1 )
         {
            if ( KANUtil.filterEmpty( dateSplit[ 1 ] ) != null )
            {
               calendar.set( Calendar.MONTH, Integer.valueOf( dateSplit[ 1 ] ) - 1 );
            }
         }

         if ( dateSplit != null && dateSplit.length > 2 )
         {
            if ( KANUtil.filterEmpty( dateSplit[ 2 ] ) != null )
            {
               calendar.set( Calendar.DATE, Integer.valueOf( dateSplit[ 2 ] ) );
            }
         }
         else
         {
            calendar.set( Calendar.DATE, 1 );
         }

         calendar.set( Calendar.HOUR_OF_DAY, 0 );
         calendar.set( Calendar.MINUTE, 0 );
         calendar.set( Calendar.SECOND, 0 );

         if ( timeSplit != null && timeSplit.length > 0 )
         {
            if ( KANUtil.filterEmpty( timeSplit[ 0 ] ) != null )
            {
               calendar.set( Calendar.HOUR_OF_DAY, Integer.valueOf( timeSplit[ 0 ] ) );
            }
         }

         if ( timeSplit != null && timeSplit.length > 1 )
         {
            if ( KANUtil.filterEmpty( timeSplit[ 1 ] ) != null )
            {
               calendar.set( Calendar.MINUTE, Integer.valueOf( timeSplit[ 1 ] ) );
            }
         }

         if ( timeSplit != null && timeSplit.length > 2 )
         {
            if ( KANUtil.filterEmpty( timeSplit[ 2 ] ) != null )
            {
               if ( timeSplit[ 2 ].contains( "." ) )
               {
                  calendar.set( Calendar.SECOND, Integer.valueOf( timeSplit[ 2 ].substring( 0, timeSplit[ 2 ].indexOf( "." ) ) ) );
               }
               else
               {
                  calendar.set( Calendar.SECOND, Integer.valueOf( timeSplit[ 2 ] ) );
               }
            }
         }

         calendar.set( Calendar.MILLISECOND, 0 );
      }

      return calendar;
   }

   public static Calendar getStartCalendar( final String monthly, final String circleStartDay )
   {
      final Calendar calender = KANUtil.getFirstCalendar( monthly );

      if ( circleStartDay != null && circleStartDay.matches( "[0-9]*" ) )
      {
         if ( Integer.valueOf( circleStartDay ) != 1 )
         {
            calender.set( Calendar.MONTH, calender.get( Calendar.MONTH ) - 1 );
            calender.set( Calendar.DATE, Integer.valueOf( circleStartDay ) );
         }
      }

      return calender;
   }

   public static String getStartDate( final String monthly, final String circleStartDay )
   {
      return KANUtil.formatDate( getStartCalendar( monthly, circleStartDay ).getTime(), "yyyy-MM-dd" ) + " 00:00:00";
   }

   public static Calendar getEndCalendar( final String monthly, final String circleEndDay )
   {
      final Calendar calender = KANUtil.getLastCalendar( monthly );

      if ( circleEndDay != null && circleEndDay.matches( "[0-9]*" ) )
      {
         if ( Integer.valueOf( circleEndDay ) < calender.get( Calendar.DATE ) )
         {
            calender.set( Calendar.DATE, Integer.valueOf( circleEndDay ) );
         }
      }

      return calender;
   }

   public static String getEndDate( final String monthly, final String circleEndDay )
   {
      return KANUtil.formatDate( getEndCalendar( monthly, circleEndDay ).getTime(), "yyyy-MM-dd" ) + " 23:59:59";
   }

   // ����һ�����ںͼ�н���ڣ���ȡ�����������·�
   /* Add by siuxia 2014-1-15*/
   public static String getMonthlyByCondition( final String circleEndDay, final String targetDate )
   {

      final String referenceMonthly = getMonthly( createCalendar( targetDate ) );

      long endDays = getDays( createCalendar( getEndDate( referenceMonthly, circleEndDay ) ) );

      long targetDays = getDays( createCalendar( targetDate ) );

      if ( targetDays <= endDays )
      {
         return referenceMonthly;
      }

      return getMonthly( referenceMonthly, 1 );
   }

   // ��ø�������Calendar��������
   public static long getGapSeconds( final Calendar calendar, final Calendar calendarTarget )
   {
      return calendar.getTimeInMillis() / 1000 - calendarTarget.getTimeInMillis() / 1000;
   }

   // ��ø�������Calendar��������
   public static long getGapDays( final Calendar calendar, final Calendar calendarTarget )
   {
      final Calendar source = Calendar.getInstance();
      source.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) );
      source.set( Calendar.MONTH, calendar.get( Calendar.MONTH ) );
      source.set( Calendar.DATE, calendar.get( Calendar.DATE ) );
      source.set( Calendar.HOUR, 0 );
      source.set( Calendar.MINUTE, 0 );
      source.set( Calendar.SECOND, 0 );
      source.set( Calendar.MILLISECOND, 0 );

      final Calendar target = Calendar.getInstance();
      target.set( Calendar.YEAR, calendarTarget.get( Calendar.YEAR ) );
      target.set( Calendar.MONTH, calendarTarget.get( Calendar.MONTH ) );
      target.set( Calendar.DATE, calendarTarget.get( Calendar.DATE ) );
      target.set( Calendar.HOUR, 0 );
      target.set( Calendar.MINUTE, 0 );
      target.set( Calendar.SECOND, 0 );
      target.set( Calendar.MILLISECOND, 0 );

      return source.getTimeInMillis() / ( 24 * 60 * 60 * 1000 ) - target.getTimeInMillis() / ( 24 * 60 * 60 * 1000 );
   }

   // ��ø���Calendar������
   public static long getDays( final Calendar calendar )
   {
      final Calendar source = Calendar.getInstance();
      source.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) );
      source.set( Calendar.MONTH, calendar.get( Calendar.MONTH ) );
      source.set( Calendar.DATE, calendar.get( Calendar.DATE ) );
      source.set( Calendar.HOUR, 0 );
      source.set( Calendar.MINUTE, 0 );
      source.set( Calendar.SECOND, 0 );
      source.set( Calendar.MILLISECOND, 0 );

      return source.getTimeInMillis() / ( 24 * 60 * 60 * 1000 );
   }

   public static long getDays( final Date date )
   {
      return getDays( getCalendar( date ) );
   }

   public static String getDateString( final Date date, final String split )
   {
      return getYear( date ) + split + getMonth( date ) + split + getDay( date );
   }

   public static int getNowYear()
   {
      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy" );
      final Date nowDate = new Date();
      return Integer.valueOf( simpleDateFormat.format( nowDate ) ).intValue();
   }

   public static String getYear( final Date date )
   {
      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy" );
      return simpleDateFormat.format( date );
   }

   public static String getMonth( final Date date )
   {
      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "MM" );
      return simpleDateFormat.format( date );
   }

   // ��ȡMonthly
   /* Updated By siuxia @2014-04-03 */
   public static String getMonthly( final Calendar calendar, final String split )
   {
      int month = calendar.get( Calendar.MONTH ) + 1;

      if ( month < 10 )
      {
         return calendar.get( Calendar.YEAR ) + ( KANUtil.filterEmpty( split ) != null ? split : "" ) + "0" + ( calendar.get( Calendar.MONTH ) + 1 );
      }

      return calendar.get( Calendar.YEAR ) + ( KANUtil.filterEmpty( split ) != null ? split : "" ) + ( calendar.get( Calendar.MONTH ) + 1 );
   }

   // ��ȡMonthly
   /* Added By Kevin Jin at 2014-05-20 */
   public static String getMonthly( final Calendar calendar )
   {
      return getMonthly( calendar, "/" );
   }

   // ��ȡMonthly
   public static String getMonthly( final Date date )
   {
      final Calendar calendar = getCalendar( date );

      return getMonthly( calendar );
   }

   // ��ȡMonthly
   /* Added By Kevin Jin at 2014-05-20 */
   public static String getMonthly( final Date date, final String split )
   {
      final Calendar calendar = getCalendar( date );

      return getMonthly( calendar, split );
   }

   // ���ո���Monthly��Gap�����Ҫ�õ���Monthly��monthlyΪ���Ե�ǰʱ��Ϊ׼��gapΪ��Ĭ��Ϊ0
   public static String getMonthly( final String monthly, final int gapMonths )
   {
      // ��ʼ��Calendar
      final Calendar calendar = createCalendar( monthly );
      calendar.add( Calendar.MONTH, gapMonths );

      return getMonthly( calendar );
   }

   /**  
    * Get Months by Condition
    *	����������ѯ�·ݣ� year/month ����ɵ� MappingVO����
    *	@param previousNumber ��ǰ������
    *	@param nextNumber     ���������
    *	@return
    */
   // Updated by siuxia @2014-04-03
   public static List< MappingVO > getMonthsByCondition( final int previousNumber, final int nextNumber )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > months = new ArrayList< MappingVO >();
      // ��ʼ����ǰʱ�䣨According to Next��
      final Calendar calendar = Calendar.getInstance();
      calendar.add( Calendar.MONTH, nextNumber );

      // ����·ݼ���
      for ( int i = previousNumber + nextNumber; i >= 0; i-- )
      {
         // ��ʼ��MappingVO
         final MappingVO mappingVO = new MappingVO();
         int year = calendar.get( Calendar.YEAR );
         int month = calendar.get( Calendar.MONTH ) + 1;
         if ( month < 10 )
         {
            mappingVO.setMappingId( year + "/0" + month );
            mappingVO.setMappingValue( year + "/0" + month );
         }
         else
         {
            mappingVO.setMappingId( year + "/" + month );
            mappingVO.setMappingValue( year + "/" + month );
         }
         months.add( mappingVO );

         calendar.add( Calendar.MONTH, -1 );
      }
      return months;
   }

   /**  
    * Get Weeks by Condition
    * ����������ѯ�ܴΣ� year/week ����ɵ� MappingVO����
    * @param previousNumber ��ǰ���ܴ���
    * @param nextNumber     ������ܴ���
    * @return
    */
   public static List< MappingVO > getWeeksByCondition( final int previousNumber, final int nextNumber )
   {
      // ��ʼ������ֵ����
      final List< MappingVO > weeks = new ArrayList< MappingVO >();
      // ��ʼ��Calendar
      final Calendar calendar = Calendar.getInstance();
      calendar.add( Calendar.WEEK_OF_YEAR, nextNumber );

      // ����ܴμ���
      for ( int i = previousNumber + nextNumber; i >= 0; i-- )
      {
         // ��ʼ��MappingVO
         final MappingVO mappingVO = new MappingVO();
         int year = calendar.get( Calendar.YEAR );
         int week = calendar.get( Calendar.WEEK_OF_YEAR );
         mappingVO.setMappingId( year + "/" + week );
         mappingVO.setMappingValue( year + "/" + week );

         calendar.add( Calendar.WEEK_OF_YEAR, -1 );
         weeks.add( mappingVO );
      }
      return weeks;
   }

   public static Date getDate( final Date date, final int gapYears )
   {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime( date );

      if ( gapYears != 0 )
      {
         calendar.add( Calendar.YEAR, gapYears );
      }

      return calendar.getTime();
   }

   public static Date getDate( final String date, final int gapYears )
   {
      return getDate( createDate( date ), gapYears );
   }

   public static Date getDate( final Date date, final int gapYears, final int gapMonths )
   {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime( date );

      if ( gapYears != 0 )
      {
         calendar.add( Calendar.YEAR, gapYears );
      }

      if ( gapMonths != 0 )
      {
         calendar.add( Calendar.MONTH, gapMonths );
      }

      return calendar.getTime();
   }

   public static Date getDate( final String date, final int gapYears, final int gapMonths )
   {
      return getDate( createDate( date ), gapYears, gapMonths );
   }

   public static Date getDate( final Date date, final int gapYears, final int gapMonths, final int gapDays )
   {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime( date );

      if ( gapYears != 0 )
      {
         calendar.add( Calendar.YEAR, gapYears );
      }

      if ( gapMonths != 0 )
      {
         calendar.add( Calendar.MONTH, gapMonths );
      }

      if ( gapDays != 0 )
      {
         calendar.add( Calendar.DATE, gapDays );
      }

      return calendar.getTime();
   }

   public static Date getDate( final String date, final int gapYears, final int gapMonths, final int gapDays )
   {
      return getDate( createDate( date ), gapYears, gapMonths, gapDays );
   }

   public static String getStrDate( final String dateSource, final int addValue ) throws ParseException
   {
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
      // ��ʼ����׼������
      final GregorianCalendar gc = new GregorianCalendar();
      // �趨��ʼʱ��
      gc.setTime( sdf.parse( dateSource ) );
      // ��ԭ�еĻ�������������
      gc.add( 5, +addValue );
      return sdf.format( gc.getTime() );
   }

   /**
   * 	Get Gap Month
   *	
   *	@param startDate
   *	@param endDate
   *	@return
   *	@throws KANException
    */
   public static int getGapMonth( final String startDate, final String endDate ) throws KANException
   {
      if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
      {
         final Calendar startCalendar = KANUtil.createCalendar( startDate );
         final Calendar endCalendar = KANUtil.createCalendar( endDate );
         int startYear = startCalendar.get( Calendar.YEAR );
         int startMonth = startCalendar.get( Calendar.MONTH ) + 1;
         int endYear = endCalendar.get( Calendar.YEAR );
         int endMonth = endCalendar.get( Calendar.MONTH ) + 1;
         return ( endMonth - startMonth ) + ( endYear - startYear ) * 12;
      }
      else
      {
         return 0;
      }
   }

   public static int getAboutGapMonth( final String startDate, final String endDate ) throws KANException
   {
      int month = getGapMonth( startDate, endDate );
      int day1 = Integer.valueOf( getDay( createDate( startDate ) ) );

      if ( month == 11 )
         return month;

      month = day1 > 15 ? month - 1 : month + 1;

      return month;
   }

   /***
    * 
   * getGapMonth(��ȡ����ʱ���֮��Ŀ�����)
   * circleEndDay(��н��������)  
   * startDate(��ʼ����)  
   * endDate(��������)  
    */
   // Add by siuvan.xia @2014-07-21
   public static int getGapMonth( final String circleEndDay, final String startDate, final String endDate ) throws KANException
   {
      String startMonthly = getMonthlyByCondition( circleEndDay, startDate );
      String endMonthly = getMonthlyByCondition( circleEndDay, endDate );
      return ( Integer.valueOf( endMonthly.split( "/" )[ 1 ] ) - Integer.valueOf( startMonthly.split( "/" )[ 1 ] ) )
            + ( Integer.valueOf( endMonthly.split( "/" )[ 0 ] ) - Integer.valueOf( startMonthly.split( "/" )[ 0 ] ) ) * 12;
   }

   public static String getDay( final Date date )
   {
      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd" );
      return simpleDateFormat.format( date );
   }

   public static String formatDate( final Object date )
   {
      return formatDate( date, null, false );
   }

   public static String formatDate( final Object date, final String format )
   {
      return formatDate( date, format, false );
   }

   /**
    * nullAllowed == true ��Ϊ�շ���null
    * nullAllowed == false ��Ϊ�շ���new Date();
    * @param date
    * @param format
    * @param nullAllowed
    * @return
    */
   public static String formatDate( final Object date, final String format, final boolean nullAllowed )
   {
      String defaultFormat = "yyyy-MM-dd";

      if ( filterEmpty( format ) != null )
      {
         defaultFormat = format;
      }

      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( defaultFormat );

      if ( date != null )
      {
         if ( date instanceof Date )
         {

            return simpleDateFormat.format( date );
         }
         else if ( date instanceof String )
         {
            if ( filterEmpty( ( String ) date ) != null )
            {
               return simpleDateFormat.format( createDate( ( String ) date ) );
            }
         }
      }

      return nullAllowed ? null : simpleDateFormat.format( new Date() );
   }

   public static Calendar getCalendar( final Date date )
   {
      final Calendar calendar = Calendar.getInstance();

      if ( date != null )
      {
         calendar.set( Integer.valueOf( getYear( date ) ), Integer.valueOf( getMonth( date ) ) - 1, Integer.valueOf( getDay( date ) ), 0, 0, 0 );
      }

      return calendar;
   }

   // The source format: 2013/09
   public static Calendar getFirstCalendar( final String source )
   {
      final Calendar calendar = Calendar.getInstance();

      if ( source != null && !source.trim().equals( "" ) && source.contains( "/" ) )
      {
         calendar.set( Integer.valueOf( source.split( "/" )[ 0 ] ), Integer.valueOf( source.split( "/" )[ 1 ] ) - 1, 1, 0, 0, 0 );
      }

      calendar.set( Calendar.DATE, calendar.getActualMinimum( Calendar.DAY_OF_MONTH ) );

      return calendar;
   }

   // The source format: 2013/09
   public static Date getFirstDate( final String source )
   {
      return getFirstCalendar( source ).getTime();
   }

   // The source format: 2013/09
   public static Calendar getLastCalendar( final String source )
   {
      final Calendar calendar = Calendar.getInstance();

      if ( source != null && !source.trim().equals( "" ) && source.contains( "/" ) )
      {
         calendar.set( Integer.valueOf( source.split( "/" )[ 0 ] ), Integer.valueOf( source.split( "/" )[ 1 ] ) - 1, 1, 0, 0, 0 );
      }

      calendar.set( Calendar.DATE, calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) );

      return calendar;
   }

   // The source format: 2013/09
   public static Date getLastDate( final String source )
   {
      return getLastCalendar( source ).getTime();
   }

   public static List< MappingVO > getMappings( final Locale locale, final String key )
   {
      final String keyValue = getProperty( locale, key );
      final List< MappingVO > types = new ArrayList< MappingVO >();

      try
      {
         if ( keyValue != null )
         {
            final String[] elements = keyValue.split( "##" );
            for ( String element : elements )
            {
               final String[] subElement = element.split( ":" );

               final MappingVO mappingVO = new MappingVO();
               if ( subElement.length == 3 )
               {
                  mappingVO.setMappingTemp( subElement[ 2 ].replace( "&#58;", ":" ) );
               }
               if ( subElement.length > 1 )
               {
                  mappingVO.setMappingId( subElement[ 0 ] );
                  mappingVO.setMappingValue( subElement[ 1 ].replace( "&#58;", ":" ) );
                  types.add( mappingVO );
               }
            }
         }
      }
      catch ( Exception e )
      {

      }

      return types;
   }

   public static String getApplicationPropertiesValue( final String key )
   {
      final Resource resource = new ClassPathResource( "application.properties" );
      try
      {
         final Properties props = PropertiesLoaderUtils.loadProperties( resource );
         return props.getProperty( key );
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }

      return "1";
   }

   public static String filterEmpty( final Object source )
   {
      if ( source == null )
      {
         return null;
      }
      else if ( source instanceof String )
      {
         return filterEmpty( ( String ) source );
      }
      else
      {
         return filterEmpty( source.toString() );
      }
   }

   public static String filterEmpty( final String source )
   {
      if ( source != null && !source.trim().isEmpty() )
      {
         return source;
      }
      else
      {
         return null;
      }
   }

   public static String filterEmpty( final Object source, final String filterString )
   {
      if ( filterEmpty( source ) != null && filterEmpty( filterString ) != null )
      {
         if ( ( ( String ) source ).trim().equals( filterString.trim() ) )
         {
            return filterEmpty( ( ( String ) source ).replace( filterString, "" ) );
         }
      }

      return filterEmpty( source );
   }

   public static String filterEmpty( final String source, final String filterString )
   {
      if ( filterEmpty( source ) != null && filterEmpty( filterString ) != null )
      {
         if ( source.trim().equals( filterString.trim() ) )
         {
            return filterEmpty( source.replace( filterString, "" ) );
         }
      }

      return filterEmpty( source );
   }

   public static String filterEmpty( final String source, final String filterStrings[] )
   {
      if ( filterEmpty( source ) != null && filterStrings != null && filterStrings.length > 0 )
      {
         String tempSource = source;

         for ( String filterString : filterStrings )
         {
            tempSource = tempSource.replace( filterString, "" );
         }

         return filterEmpty( tempSource );
      }

      return filterEmpty( source );
   }

   // ���ʻ�������
   public static String getProperty( final Locale locale, final String key )
   {
      return getProperty( locale, key, null, null );
   }

   // ���ʻ�������
   public static String getProperty( final Locale locale, final String key, final Object arg0 )
   {
      return getProperty( locale, key, arg0, null );
   }

   // ���ʻ�������
   public static String getProperty( final Locale locale, final String key, final Object arg0, final Object arg1 )
   {
      // ����һ   �ɸ�Ϊ�Զ�����
      final String[] messageResourcesArray = { "MessageResources/Error/Error", "MessageResources/Business/Business", "MessageResources/Define/Define",
            "MessageResources/Management/Management", "MessageResources/Public/Public", "MessageResources/Security/Security", "MessageResources/System/System",
            "MessageResources/Message/Message", "MessageResources/Workflow/Workflow", "MessageResources/SB/SB", "MessageResources/CB/CB", "MessageResources/Settlement/Settlement",
            "MessageResources/Payment/Payment", "MessageResources/WX/wx", "MessageResources/Title/Title", "MessageResources/Performance/Performance" };

      final List< MessageResources > messageResourcesList;
      messageResourcesList = new ArrayList< MessageResources >( messageResourcesArray.length );
      final MessageResourcesFactory factory = MessageResourcesFactory.createFactory();

      for ( String resource : messageResourcesArray )
      {
         final MessageResources resources = factory.createResources( resource );
         messageResourcesList.add( resources );
      }

      String value = "";

      for ( MessageResources resources : messageResourcesList )
      {
         if ( resources.isPresent( locale, key ) )
         {
            value = resources.getMessage( locale, key, arg0, arg1 );
            //            if ( arg0 != null )
            //            {
            //               value = resources.getMessage( locale, key, arg0 );
            //            }
            //            else
            //            {
            //               value = resources.getMessage( locale, key );
            //            }
            break;
         }
      }

      return value;
   }

   // ��ͨjava Bean ����ת��Ϊ JSONObject ����
   public static JSONObject toJSONObject( final Object obj )
   {

      return JSONObject.fromObject( obj, jsonConfig );
   }

   // Convert to Jason array
   public static String toJasonArray( final String items )
   {
      String returnItem = "";

      if ( items != null )
      {
         String itemsArray[] = items.split( "," );

         for ( String item : itemsArray )
         {
            if ( returnItem == "" )
            {
               returnItem = "{" + item;
            }
            else
            {
               returnItem = returnItem + ":" + item;
            }
         }

         returnItem = returnItem + "}";
      }

      return returnItem;
   }

   // Convert to Jason array 
   public static String toJasonArray( final String[] items )
   {
      return toJasonArray( items, ":" );
   }

   // Convert to Jason array Add By Siuxia
   public static String toJasonArray( final String[] items, final String split )
   {
      String returnItem = "";
      if ( items != null && items.length > 0 )
      {
         for ( String item : items )
         {
            if ( returnItem == "" )
            {
               returnItem = "{" + item;
            }
            else
            {
               returnItem = returnItem + split + item;
            }
         }

         returnItem = returnItem + "}";
      }

      return returnItem;
   }

   // Convert Jason array to string
   public static String jasonArrayToString( final String items )
   {
      if ( items != null )
      {
         return items.replace( "{", "" ).replace( "}", "" ).replace( ":", "," );
      }

      return "";
   }

   // Convert Jason array to string array
   public static String[] jasonArrayToStringArray( final String items )
   {
      if ( filterEmpty( items ) != null )
      {
         return items.replace( "{", "" ).replace( "}", "" ).replace( ":", "," ).split( "," );
      }

      return new String[] {};
   }

   // Convert Jason array to string List
   public static List< String > jasonArrayToStringList( final String items )
   {
      final List< String > itemList = new ArrayList< String >();

      if ( filterEmpty( items ) != null )
      {
         final String[] itemArray = items.replace( "{", "" ).replace( "}", "" ).replace( ":", "," ).split( "," );

         if ( itemArray != null && itemArray.length > 0 )
         {
            for ( String item : itemArray )
            {
               itemList.add( item );
            }
         }
      }

      return itemList;
   }

   // Convert String List to Jason array  add by siuxia
   public static String stringListToJasonArray( final List< String > itemList )
   {
      return stringListToJasonArray( itemList, ":" );
   }

   // Convert String List to Jason array  add by siuxia
   public static String stringListToJasonArray( final List< String > itemList, final String split )
   {
      if ( itemList != null && itemList.size() > 0 )
      {
         final String[] items = new String[ itemList.size() ];
         int idx = 0;
         for ( String item : itemList )
         {
            items[ idx ] = item;
            idx++;
         }

         return toJasonArray( items, split );
      }

      return null;
   }

   // Convert String List to Jason array  add by siuxia
   public static String[] stringListToArray( final List< String > itemList )
   {
      if ( itemList != null && itemList.size() > 0 )
      {
         return itemList.toArray( new String[ itemList.size() ] );
      }
      return null;
   }

   public static String[] mappingListToArray( final List< MappingVO > mappingVOs )
   {
      if ( mappingVOs != null && mappingVOs.size() > 0 )
      {
         final String[] retArray = new String[ mappingVOs.size() ];
         for ( int i = 0; i < mappingVOs.size(); i++ )
         {
            retArray[ i ] = mappingVOs.get( i ).getMappingValue();
         }

         return retArray;
      }

      return null;
   }

   // Generate Select HTML
   public static String getSelectHTML( final List< MappingVO > mappingVOs, final String id, final String className, final String value, final String js, final String style )
   {
      final StringBuffer rs = new StringBuffer();

      // ��װJS��HTML
      String jsHtml = "";
      if ( js != null && !js.trim().equals( "" ) )
      {
         jsHtml = " onchange=\"" + js + "\" ";
      }

      // ��װStyle��HTML
      String styleHtml = "";
      if ( style != null && !style.trim().equals( "" ) )
      {
         styleHtml = " style=\"" + style + "\" ";
      }

      rs.append( "<select id=\"" + id + "\" name=\"" + id + "\" class=\"" + className + "\" " + jsHtml + " " + styleHtml + ">" );
      rs.append( getOptionHTML( mappingVOs, id, value ) );
      rs.append( "</select>" );

      return rs.toString();
   }

   // Generate Option HTML
   public static String getOptionHTML( final List< MappingVO > mappingVOs, final String id, final String value )
   {
      final StringBuffer rs = new StringBuffer();
      boolean selected = false;

      if ( mappingVOs != null && mappingVOs.size() > 0 )
      {
         if ( mappingVOs.size() == 1 )
         {
            selected = true;
         }
         for ( MappingVO mappingVO : mappingVOs )
         {
            rs.append( "<option " + mappingVO.getOptionStyle() + " id=\"option_" + id + "_" + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" "
                  + ( ( value != null && value.trim().equals( mappingVO.getMappingId() ) || selected ) ? "selected" : "" ) + ">" + mappingVO.getMappingValue() + "</option>" );
         }
      }

      return rs.toString();
   }

   public static String getCheckBoxHTML( final List< MappingVO > mappingVOs, final String id, String values, final String subAction, final String split )
   {
      boolean isView = BaseAction.VIEW_OBJECT.equalsIgnoreCase( subAction );
      final StringBuffer rs = new StringBuffer();
      String[] checkIds = null;

      if ( KANUtil.filterEmpty( values ) != null )
      {
         checkIds = jasonArrayToStringArray( values );
      }

      if ( mappingVOs != null && mappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : mappingVOs )
         {
            boolean checked = false;

            if ( checkIds != null && checkIds.length > 0 )
            {
               for ( String str : checkIds )
               {
                  if ( ( str ).equals( mappingVO.getMappingId() ) )
                  {
                     checked = true;
                     break;
                  }
               }
            }

            if ( isView )
            {
               // ��������������ã�ѡ��ѡ
               rs.append( "<span class='noPosition'><input type='checkbox' id='checkBox_" + id + "_" + mappingVO.getMappingId() + "' name='checkBox_" + id + "' value='"
                     + mappingVO.getMappingId() + "' " + ( checked ? " checked " : "" ) + ( " disabled " ) + " />" + mappingVO.getMappingValue() + "&nbsp;</span>"
                     + ( KANUtil.filterEmpty( split ) == null ? " &nbsp; " : split ) );
            }
            else
            {
               // ������½���Ĭ�Ϲ�ѡ����/��������ѡ��,������
               rs.append( "<span class='noPosition'><input type='checkbox' id='checkBox_" + id + "_" + mappingVO.getMappingId() + "' name='checkBox_" + id + "' value='"
                     + mappingVO.getMappingId() + "' " + ( checked ? " checked " : "" ) + " />" + mappingVO.getMappingValue() + "&nbsp;</span>"
                     + ( KANUtil.filterEmpty( split ) == null ? " &nbsp; " : split ) );
            }
         }
      }

      return rs.toString();
   }

   public static String getCheckBoxHTML( final List< MappingVO > mappingVOs, final String id, String values, final String subAction )
   {
      return getCheckBoxHTML( mappingVOs, id, values, subAction, null );
   }

   /**
    * values ��jsonArray
    * @param mappingVOs
    * @param id
    * @param values
    * @return
    */
   // checkbox
   public static String getCheckBoxHTML( final List< MappingVO > mappingVOs, final String id, final String values )
   {
      return getCheckBoxHTML( mappingVOs, id, values, "" );
   }

   // Get value from Object
   public static String getFilterEmptyValue( final Object object, final String field ) throws KANException
   {
      Object retObject = getValue( object, field );
      if ( retObject == null )
      {
         return "";
      }

      return String.valueOf( retObject );
   }

   // Get value from Object
   public static Object getValue( final Object object, final String field ) throws KANException
   {
      return handleValue( object, "get", field, null );
   }

   // Get value from Object
   public static Object getValue( final Object object, final String field, final String parameters[] ) throws KANException
   {
      return handleValue( object, "get", field, parameters );
   }

   // Get value from Object
   public static Object getValue( final Object object, final String field, final String parameters ) throws KANException
   {
      return handleValue( object, "get", field, new Object[] { parameters } );
   }

   // Set value from Object
   public static Object setValue( final Object object, final String field, final String parameters[] ) throws KANException
   {
      return handleValue( object, "set", field, parameters );
   }

   // Set value from Object
   public static Object setValue( final Object object, final String field, final String parameters ) throws KANException
   {
      return handleValue( object, "set", field, new Object[] { parameters } );
   }

   // Call the method of the Object
   public static Object call( final Object object, final String methodString, final String parameters[] ) throws KANException
   {
      return handleValue( object, methodString, null, parameters );
   }

   // Handle value for Object
   public static Object handleValue( final Object object, final String methodString, final String field, final Object parameters[] ) throws KANException
   {
      try
      {
         if ( object != null )
         {
            // ���ɳ�ʼ���ַ���
            final String getMethod = methodString
                  + ( field != null ? String.valueOf( field.toCharArray()[ 0 ] ).toUpperCase() + String.valueOf( field.toCharArray(), 1, field.length() - 1 ) : "" );
            // ��ʼ��Class����
            final Class< ? > clazz = object.getClass();
            // ��ʼ��Method��
            Method method = null;
            // ��Ҫ����
            if ( parameters != null && parameters.length > 0 )
            {
               if ( parameters.length == 1 )
               {
                  method = clazz.getMethod( getMethod, new Class[] { String.class } );
               }
               else if ( parameters.length == 2 )
               {
                  method = clazz.getMethod( getMethod, new Class[] { String.class, String.class } );
               }
               else if ( parameters.length == 3 )
               {
                  method = clazz.getMethod( getMethod, new Class[] { String.class, String.class, String.class } );
               }
               else
               {
                  method = clazz.getMethod( getMethod, new Class[] { String[].class } );
               }

               if ( method != null )
               {
                  return method.invoke( object, parameters );
               }
            }
            // ����Ҫ����
            else
            {
               method = clazz.getMethod( getMethod, new Class< ? >[] {} );

               if ( method != null )
               {
                  return method.invoke( object );
               }
            }
         }
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }

      return null;
   }

   // ���մ����Locale�õ��յ�MappingVO����
   public static MappingVO getEmptyMappingVO( final Locale locale )
   {
      final MappingVO mappingVO = new MappingVO();
      mappingVO.setMappingId( "0" );

      if ( locale != null )
      {
         mappingVO.setMappingValue( KANUtil.getProperty( locale, "public.empty.mapping.value" ) );
      }

      return mappingVO;
   }

   public static String encodeStringWithCryptogram( final String source ) throws KANException
   {
      try
      {
         if ( source != null && !source.isEmpty() )
         {
            return URLEncoder.encode( Cryptogram.encodeString( source ), "UTF-8" );
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���� - ����
   public static String encodeString( final String source ) throws KANException
   {
      try
      {
         if ( source != null && !source.isEmpty() )
         {
            return URLEncoder.encode( URLEncoder.encode( source, "UTF-8" ), "UTF-8" );
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���� - һ��
   public static String decodeString( final String source ) throws KANException
   {
      try
      {
         if ( source != null && !source.isEmpty() )
         {
            return Cryptogram.decodeString( URLDecoder.decode( source, "UTF-8" ) );
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ���� - Ajax�����
   public static String decodeStringFromAjax( final String source ) throws KANException
   {
      try
      {
         if ( source != null && !source.isEmpty() )
         {
            return Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( source, "UTF-8" ), "UTF-8" ) );
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public static String decodeSelectedIds( final String selectedIds ) throws KANException
   {
      try
      {

         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            String sources[] = selectedIds.split( "," );
            StringBuffer buffer = new StringBuffer();
            for ( String string : sources )
            {
               buffer.append( Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( string, "UTF-8" ), "UTF-8" ) ) );
               buffer.append( "," );
            }

            return buffer.toString();

         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
   * Get PositionName with Staffs
   */
   public static String getPositionNameWithStaffs( final PositionVO positionVO, final List< StaffBaseView > staffBaseViews, final Locale locale )
   {
      // ��ʼ��PositionName��StaffName
      String positionName = "";
      String staffName = "";

      // ��ʼ��PositionName
      if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         positionName = positionVO.getTitleZH();
      }
      else
      {
         positionName = positionVO.getTitleEN();
      }

      // ��ʼ��StaffName
      if ( staffBaseViews != null && staffBaseViews.size() > 0 )
      {
         for ( StaffBaseView staffBaseView : staffBaseViews )
         {
            if ( locale.getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               if ( staffName != null && !staffName.trim().equals( "" ) )
               {
                  staffName = staffName + "��";
               }
               staffName = staffName + staffBaseView.getNameZH();
            }
            else
            {
               if ( staffName != null && !staffName.trim().equals( "" ) )
               {
                  staffName = staffName + ", ";
               }
               staffName = staffName + staffBaseView.getNameEN();
            }
         }
      }
      return positionName + " - " + staffName;
   }

   /**
    * ���Column�ֶζ�Ӧ���������Mapping
   * 	getColumnOptionValues
   *	
   *	@param request
   *	@param columnVO
   *	@return
   *	@throws KANException
    */
   @SuppressWarnings("unchecked")
   public static List< MappingVO > getColumnOptionValues( final Locale locale, final ColumnVO columnVO, final String accountId, final String corpId ) throws KANException
   {
      if ( columnVO == null )
         return null;

      // ��ʼ��MappingVO�б�
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // ���������� - ϵͳ����
      if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "1" ) )
      {
         // ���ϵͳ����ѡ���б�
         final List< MappingVO > systemOptions = KANUtil.getMappings( locale, "def.column.option.type.system" );

         // ����ϵͳ����ѡ��
         if ( systemOptions != null && systemOptions.size() > 0 )
         {
            for ( MappingVO systemOption : systemOptions )
            {
               // ���ϵͳ����ѡ��
               if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  mappingVOs = KANUtil.getMappings( locale, systemOption.getMappingTemp() );
               }
            }
         }
      }
      // ���������� - �˻�����
      else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "2" ) )
      {
         // ����˻�����ѡ���б�
         final List< MappingVO > accountOptions = KANUtil.getMappings( locale, "def.column.option.type.account" );

         // �����˻�����ѡ��
         if ( accountOptions != null && accountOptions.size() > 0 )
         {
            for ( MappingVO accountOption : accountOptions )
            {
               // ����˻�����ѡ��
               if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  // ��ʼ��Parameter Array
                  String parameters[];

                  if ( KANUtil.filterEmpty( corpId ) != null )
                  {
                     parameters = new String[] { locale.getLanguage(), corpId };
                  }
                  else
                  {
                     parameters = new String[] { locale.getLanguage() };
                  }

                  mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( accountId ), accountOption.getMappingTemp(), parameters );
                  // ��ӿյ�MappingVO����
                  mappingVOs.add( 0, KANUtil.getEmptyMappingVO( locale ) );
               }
            }
         }
      }
      // ���������� - �û��Զ���
      else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "3" ) )
      {
         mappingVOs = KANConstants.getKANAccountConstants( accountId ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( locale.getLanguage() );
      }
      // ���������� - ֱ��ֵ
      else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "4" ) )
      {
         // ����û������ֱ��ֵ���Ҳ�Ϊ��
         if ( KANUtil.filterEmpty( columnVO.getOptionValue() ) != null )
         {
            // ���û������ֱ��ֵתΪJSONObject
            final JSONObject optionsJSONObject = JSONObject.fromObject( columnVO.getOptionValue().replace( "[{", "{" ).replace( "}]", "}" ) );
            // ����JSONObject
            final Iterator< ? > keyIterator = optionsJSONObject.keys();

            while ( keyIterator.hasNext() )
            {
               final String key = ( String ) keyIterator.next();

               // ��ʼ��MappingVO
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( key );
               mappingVO.setMappingValue( optionsJSONObject.getString( key ) );
               // ���MappingVO��List
               mappingVOs.add( mappingVO );
            }
         }
      }
      // ���������� - Ԥ��
      else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "5" ) )
      {
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( locale ) );
      }

      return mappingVOs;
   }

   /**
    * ͨ��mappingValue ���MappingId
   * 	getMappingIdByMappingList
   *	
   *	@param mappingVOs
   *	@param mappingValue
   *	@return
    */
   public static String getMappingValueByMappingList( final List< MappingVO > mappingVOs, final String mappingId )
   {
      if ( mappingVOs != null && mappingId != null )
      {
         for ( MappingVO mappingVO : mappingVOs )
         {
            if ( mappingId.equals( mappingVO.getMappingId() ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public static String getMappingValueString( final List< MappingVO > mappingVOs )
   {
      final StringBuilder stringBuilder = new StringBuilder();

      if ( mappingVOs != null )
      {
         for ( MappingVO mappingVO : mappingVOs )
         {
            if ( KANUtil.filterEmpty( mappingVO.getMappingId(), "0" ) != null )
            {
               if ( stringBuilder.length() > 0 )
               {
                  stringBuilder.append( "��" );
               }

               stringBuilder.append( mappingVO.getMappingValue() );
            }
         }
      }

      return stringBuilder.toString();
   }

   /***
    * �ж�list ���������Ƿ���Target����, �ȽϷ�����equals()
    * @param list
    * @param target
    * @return
    */
   public static boolean hasContain( Object[] list, Object target )
   {
      if ( list == null )
         return false;
      for ( Object obj : list )
      {
         if ( obj.equals( target ) )
         {
            return true;
         }
      }
      return false;
   }

   public static String getSizeString( final long size )
   {
      String sizeString = "";

      if ( size / 1024 / 1024 / 1024 > 0 )
      {
         sizeString = Math.round( size / 1024 / 1024 / 1024 ) + "G";
      }
      else if ( size / 1024 / 1024 > 0 )
      {
         sizeString = Math.round( size / 1024 / 1024 ) + "M";
      }
      else if ( size / 1024 > 0 )
      {
         sizeString = Math.round( size / 1024 ) + "K";
      }
      else
      {
         sizeString = size + "B";
      }

      return sizeString;
   }

   public static String round( String value, final String accuracy, final String round )
   {
      if ( KANUtil.filterEmpty( value ) != null && ( value.contains( "e" ) || value.contains( "E" ) ) )
      {
         NumberFormat nf = NumberFormat.getInstance();
         nf.setGroupingUsed( false );
         value = String.valueOf( nf.format( Double.parseDouble( value ) ) );
      }

      if ( KANUtil.filterEmpty( value ) != null && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
      {
         return round( Double.valueOf( value ), filterEmpty( accuracy ) == null ? 0 : Integer.valueOf( accuracy ), round );
      }

      return "";
   }

   public static String round( final String value, final int accuracy, final String round )
   {
      if ( KANUtil.filterEmpty( value ) != null && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
      {
         return round( Double.valueOf( value ), accuracy, round );
      }

      return "";
   }

   public static String round( final double value, final String accuracy, final String round )
   {
      return round( Double.valueOf( value ), filterEmpty( accuracy ) == null ? 0 : Integer.valueOf( accuracy ), round );
   }

   public static String round( final double value, final int accuracy, final String round )
   {
      String result = "";

      // Ĭ��Ϊ��������
      int roundType = BigDecimal.ROUND_HALF_UP;

      if ( KANUtil.filterEmpty( round ) != null )
      {
         // ��ȡ
         if ( round.trim().equals( "2" ) )
         {
            roundType = BigDecimal.ROUND_DOWN;
         }
         // ���������Ͻ�λ��������λ��������ȡ
         else if ( round.trim().equals( "3" ) )
         {
            roundType = BigDecimal.ROUND_CEILING;
         }
      }

      // ԭʼ����
      double originalValue = value;

      // ��ֹ������Ӱ����������ͽ�ȡ
      if ( KANUtil.filterEmpty( round ) != null && ( round.trim().equals( "1" ) || round.trim().equals( "2" ) ) )
      {
         if ( originalValue > 0 )
         {
            originalValue = originalValue + 0.0000000001;
         }
         else if ( originalValue < 0 )
         {
            originalValue = originalValue - 0.0000000001;
         }
      }

      result = new BigDecimal( String.valueOf( originalValue ) ).setScale( accuracy, roundType ).toString();

      return result;
   }

   // Format Double value to String value
   public static String format( final double value )
   {
      if ( value > 0.0000000001 || value < -0.0000000001 )
      {
         return String.format( "%.10f", value );
      }
      else
      {
         return "0";
      }
   }

   // ��ȡJSONObject
   public static JSONObject getJSONObject( final String key, final Object object )
   {
      if ( key != null && !key.isEmpty() && object != null )
      {
         final Map< String, Object > map = new HashMap< String, Object >();
         map.put( key, object );
         return getJSONObject( map );
      }
      else
      {
         return new JSONObject();
      }
   }

   // ��ȡJSONObject
   public static JSONObject getJSONObject( final Map< String, Object > map )
   {
      if ( map != null )
      {
         return JSONObject.fromObject( map );
      }
      else
      {
         return new JSONObject();
      }
   }

   /**
    * 
   * 	getCompareHTML
   *	
   *	@param passObject
   *	@param originalObject
   *	@param lable
   *	@param inputType
   *	@return
    */
   public static String getCompareHTML( Object passObject, Object originalObject, String lable )
   {
      return getCompareHTML( passObject, originalObject, lable, "input" );
   }

   public static String getCompareHTML( Object passObject, Object originalObject, String lable, String inputType )
   {
      final String passObj = ( String ) ( passObject == null ? "" : passObject.toString() );
      final String originalObj = ( String ) ( originalObject == null ? "" : originalObject.toString() );
      if ( "input".equals( inputType ) )
      {
         if ( passObj.equals( originalObj ) )
         {
            return "<li><label>" + lable + "</label><input type=\"text\" disabled=\"disabled\" value=\"" + passObj + "\" /></li>";
         }
         else
         {
            return "<li><label><span class=\"highlight\">" + lable + "</span></label><input type=\"text\" class=\"error important\" disabled=\"disabled\" value=\"" + passObj
                  + "\" /></li>";
         }
      }
      else if ( "textarea".equals( inputType ) )
      {
         if ( passObj.equals( originalObj ) )
         {
            return "<li><label>" + lable + "</label><textarea disabled=\"disabled\">" + passObj + "</textarea></li>";
         }
         else
         {
            return "<li><label><span class=\"highlight\">" + lable + "</span></label><textarea class=\"error important\" disabled=\"disabled\">" + passObj + "</textarea></li>";
         }
      }
      else
      {
         if ( passObj.equals( originalObj ) )
         {
            return "<li><label>" + lable + "</label><input type=\"text\" disabled=\"disabled\" value=\"" + passObj + "\" /></li>";
         }
         else
         {
            return "<li><label><span class=\"highlight\">" + lable + "</span></label><input type=\"text\" class=\"error important\" disabled=\"disabled\" value=\"" + passObj
                  + "\" /></li>";
         }
      }

   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /** 
    * ���ָ�����ڵ�ǰһ�� 
    *  
    * @param specifiedDay 
    * @return 
    * @throws Exception 
    */
   public static Date getSpecifiedDayBefore( Date specifiedDay )
   {//������new Date().toLocalString()���ݲ���  
      Calendar c = Calendar.getInstance();
      Date date = specifiedDay;
      c.setTime( date );
      int day = c.get( Calendar.DATE );
      c.set( Calendar.DATE, day - 1 );
      return c.getTime();
   }

   /** 
    * ���ָ�����ڵĺ�һ�� 
    *  
    * @param specifiedDay 
    * @return 
    */
   public static Date getSpecifiedDayAfter( Date specifiedDay )
   {
      Calendar c = Calendar.getInstance();
      Date date = specifiedDay;
      c.setTime( date );
      int day = c.get( Calendar.DATE );
      c.set( Calendar.DATE, day + 1 );
      return c.getTime();
   }

   /**
    *  by iori
    * ��ȡһ��ǰ�Ľ���
    * @return
    */
   public static String lastYear()
   {
      final Date date = new Date();
      int year = Integer.parseInt( new SimpleDateFormat( "yyyy" ).format( date ) ) - 1;
      int month = Integer.parseInt( new SimpleDateFormat( "MM" ).format( date ) );
      int day = Integer.parseInt( new SimpleDateFormat( "dd" ).format( date ) );
      if ( month == 0 )
      {
         year -= 1;
         month = 12;
      }
      else if ( day > 28 )
      {
         if ( month == 2 )
         {
            if ( ( year % 4 == 0 && year % 1 != 0 ) || ( year % 400 == 0 ) )
            {
               day = 29;
            }
            else
               day = 28;
         }
      }
      String y = year + "";
      String m = "";
      String d = "";
      if ( month < 10 )
         m = "0" + month;
      else
         m = month + "";
      if ( day < 10 )
         d = "0" + day;
      else
         d = day + "";

      return y + "-" + m + "-" + d;

   }

   /**
    *  by iori
    *  ��ȡ��12���µĿ�ʼ����ʱ��
    * @return
    */
   public static List< String > getRecent12MonthsDate( Calendar calendar, List< String > targetDates, int times )
   {
      if ( times > 0 )
      {
         // ���ĳ�µ�һ��
         targetDates.add( firstDayOfMonth( calendar ) );
         // ���ĳ�����һ��
         targetDates.add( lastDayOfMonth( calendar ) );
         // �ϸ��µĽ���
         calendar.set( Calendar.MONDAY, calendar.get( Calendar.MONTH ) - 1 );
         times--;
         getRecent12MonthsDate( calendar, targetDates, times );
      }
      return targetDates;
   }

   /** 
    * by iori
    * ĳ�µĵ�һ��
    * @param cal
    */
   public static String firstDayOfMonth( Calendar cal )
   {
      SimpleDateFormat datef = new SimpleDateFormat( "yyyy-MM-dd" );
      cal.set( GregorianCalendar.DAY_OF_MONTH, 1 );
      Date beginTime = cal.getTime();
      return datef.format( beginTime ) + " 00:00:00";
   }

   /**
    * 2013/12
    * @param yearMonth
    * @return
    */
   public static String firstDayOfMonth( String yearMonth )
   {
      SimpleDateFormat datef = new SimpleDateFormat( "yyyy-MM-dd" );
      String year = yearMonth.split( "/" )[ 0 ];
      String month = yearMonth.split( "/" )[ 1 ];
      Calendar cal = Calendar.getInstance();
      cal.set( Calendar.YEAR, Integer.parseInt( year ) );
      cal.set( Calendar.MONTH, Integer.parseInt( month ) - 1 );
      cal.set( GregorianCalendar.DAY_OF_MONTH, cal.getMinimum( Calendar.DATE ) );
      Date beginTime = cal.getTime();
      return datef.format( beginTime ) + " 00:00:00";
   }

   /**
    * ĳ�µ����һ��
    */
   public static String lastDayOfMonth( Calendar cal )
   {
      SimpleDateFormat datef = new SimpleDateFormat( "yyyy-MM-dd" );
      cal.set( Calendar.DATE, 1 );
      cal.roll( Calendar.DATE, -1 );
      Date endTime = cal.getTime();
      return datef.format( endTime ) + " 23:59:59";
   }

   public static String lastDayOfMonth( String yearMonth )
   {
      SimpleDateFormat datef = new SimpleDateFormat( "yyyy-MM-dd" );
      String year = yearMonth.split( "/" )[ 0 ];
      String month = yearMonth.split( "/" )[ 1 ];
      Calendar cal = Calendar.getInstance();
      cal.set( Calendar.YEAR, Integer.parseInt( year ) );
      cal.set( Calendar.MONTH, Integer.parseInt( month ) - 1 );
      cal.set( Calendar.DAY_OF_MONTH, 1 );
      int value = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
      cal.set( Calendar.DAY_OF_MONTH, value );
      Date endTime = cal.getTime();
      return datef.format( endTime ) + " 23:59:59";
   }

   /**
    *  by iori
    *  ��ȡ��n���µ����� ����
    * @return
    */
   public static List< String > getRecent12YearMonths( Calendar calendar, List< String > targetDates, int times )
   {
      if ( times > 0 )
      {
         targetDates.add( firstDayOfMonth( calendar ).substring( 0, 7 ).replace( "-", "/" ) );

         // �ϸ��µĽ���
         calendar.set( Calendar.MONDAY, calendar.get( Calendar.MONTH ) - 1 );
         times--;
         getRecent12YearMonths( calendar, targetDates, times );
      }
      return targetDates;
   }

   /**
    * �ж�ĳ�������Ƿ���ĳ�����ڷ�Χ 
    * by Ian
    * @param  : @param beginDate
    * @param  : @param endDate
    * @param  : @param src
    * @return : boolean
    */
   public static boolean between( Date beginDate, Date endDate, Date src )
   {
      return beginDate.before( src ) && endDate.after( src );
   }

   /**��ĩ��
      * �·��Ǵ�0��ʼ�ģ�����˵�������5�Ļ���ʵ������ʾ����4�·ݵ����һ�죬
      * by Ian
      * @param  : @param year
      * @param  : @param month
      */
   public static String getLastDayOfMonth( int year, int month )
   {
      Calendar cal = Calendar.getInstance();
      cal.set( Calendar.YEAR, year );
      cal.set( Calendar.MONTH, month );
      cal.set( Calendar.DAY_OF_MONTH, cal.getActualMaximum( Calendar.DATE ) );
      return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss  " ).format( cal.getTime() );
   }

   /**������
    * �·��Ǵ�0��ʼ�ģ�����˵�������5�Ļ���ʵ������ʾ����4�·ݵ����һ�죬
    * by Ian
    * @param  : @param year
    * @param  : @param month    
   */

   public static String getFirstDayOfMonth( int year, int month )
   {
      Calendar cal = Calendar.getInstance();
      cal.set( Calendar.YEAR, year );
      cal.set( Calendar.MONTH, month );
      cal.set( Calendar.DAY_OF_MONTH, cal.getMinimum( Calendar.DATE ) );
      return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss " ).format( cal.getTime() );
   }

   /**
    * String => yyyyMMdd 
    * @author : Ian.huang
    * @date   : 2014-7-14
    * @param  : @param Date
    * @param  : @return
    * @return : String
    */
   public static String getFormatDate( String Date )
   {
      try
      {
         SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
         Date date = sdf.parse( Date );
         SimpleDateFormat sd = new SimpleDateFormat( "yyyyMMdd" );
         return sd.format( date.getTime() );
      }
      catch ( ParseException e )
      {
         e.printStackTrace();
      }
      return "";
   }

   /**
    * 
    * @param key
    * @return
    */
   public static String getPropertiesValue( final String path, final String key )
   {
      final Resource resource = new ClassPathResource( path );
      try
      {
         final Properties props = PropertiesLoaderUtils.loadProperties( resource );
         return props.getProperty( key );
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }

      return "";
   }

   public static List< MappingVO > generatePercents( final String property )
   {
      // ��ʼ������ֵ
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      if ( filterEmpty( property ) != null )
      {
         // �����ö��
         if ( property.contains( "," ) )
         {
            final String[] enumStr = property.trim().split( "," );

            for ( String str : enumStr )
            {
               mappingVOs.add( getMappingVO( str ) );
            }
         }
         // ���������
         else if ( property.contains( "~" ) )
         {
            int small = ( int ) Math.floor( Double.valueOf( property.trim().split( "~" )[ 0 ] ) );
            int big = ( int ) Math.floor( Double.valueOf( property.trim().split( "~" )[ 1 ] ) );

            mappingVOs.add( getMappingVO( property.trim().split( "~" )[ 0 ] ) );

            for ( int i = small + 1; i <= big; i++ )
            {
               mappingVOs.add( getMappingVO( String.valueOf( i ) ) );
            }

            final Pattern pattern = Pattern.compile( "[0-9]*" );
            // �������Ҳ��Ǵ��࣬���磺12.0
            if ( !pattern.matcher( property.trim().split( "~" )[ 1 ] ).matches() )
            {
               if ( !property.trim().split( "~" )[ 1 ].split( "\\." )[ 1 ].equals( "0" ) && !property.trim().split( "~" )[ 1 ].split( "\\." )[ 1 ].equals( "00" ) )
               {
                  mappingVOs.add( getMappingVO( property.trim().split( "~" )[ 1 ] ) );
               }
            }
         }
         else
         {
            mappingVOs.add( getMappingVO( property ) );
         }
      }

      return mappingVOs;
   }

   public static MappingVO getMappingVO( final String source )
   {
      final MappingVO mappingVO = new MappingVO();
      mappingVO.setMappingId( source );
      mappingVO.setMappingValue( source );
      return mappingVO;
   }

   public static boolean exitsArrayList( final List< MappingVO > mappingVOs, final String target )
   {
      if ( mappingVOs != null && mappingVOs.size() > 0 && filterEmpty( target ) != null )
      {
         for ( MappingVO mappingVO : mappingVOs )
         {
            if ( filterEmpty( mappingVO.getMappingId() ) != null && mappingVO.getMappingId().equals( target ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public static List< MappingVO > generatePercents( final String low, final String hight )
   {
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      if ( KANUtil.filterEmpty( low ) != null && KANUtil.filterEmpty( hight ) != null )
      {

         MappingVO mappingVO = null;
         if ( low.trim().equals( hight.trim() ) )
         {
            mappingVO = new MappingVO();
            mappingVO.setMappingId( low );
            mappingVO.setMappingValue( low );
            mappingVOs.add( mappingVO );
         }
         else
         {
            int small = ( int ) Math.floor( Double.valueOf( low ) );
            int big = ( int ) Math.floor( Double.valueOf( hight ) );
            mappingVO = new MappingVO();
            mappingVO.setMappingId( low );
            mappingVO.setMappingValue( low );
            mappingVOs.add( mappingVO );

            for ( int i = small + 1; i <= big; i++ )
            {
               mappingVO = new MappingVO();
               mappingVO.setMappingId( i + "" );
               mappingVO.setMappingValue( i + "" );
               mappingVOs.add( mappingVO );
            }

            final Pattern pattern = Pattern.compile( "[0-9]*" );
            // �������Ҳ��Ǵ��࣬���磺12.0
            if ( !pattern.matcher( hight ).matches() )
            {
               if ( !hight.split( "\\." )[ 1 ].equals( "0" ) && !hight.split( "\\." )[ 1 ].equals( "00" ) )
               {
                  mappingVO = new MappingVO();
                  mappingVO.setMappingId( hight + "" );
                  mappingVO.setMappingValue( hight + "" );
                  mappingVOs.add( mappingVO );
               }
            }
         }
      }

      return mappingVOs;
   }

   public static String NoHTML( String Htmlstring )
   {
      String htmlStr = Htmlstring;
      String textStr = "";
      java.util.regex.Pattern p_script;
      java.util.regex.Matcher m_script;
      java.util.regex.Pattern p_style;
      java.util.regex.Matcher m_style;
      java.util.regex.Pattern p_html;
      java.util.regex.Matcher m_html;
      try
      {
         String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
         String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
         String regEx_html = "<[^>]+>";
         p_script = Pattern.compile( regEx_script, Pattern.CASE_INSENSITIVE );
         m_script = p_script.matcher( htmlStr );
         htmlStr = m_script.replaceAll( "" );
         p_style = Pattern.compile( regEx_style, Pattern.CASE_INSENSITIVE );
         m_style = p_style.matcher( htmlStr );
         htmlStr = m_style.replaceAll( "" );
         p_html = Pattern.compile( regEx_html, Pattern.CASE_INSENSITIVE );
         m_html = p_html.matcher( htmlStr );
         htmlStr = m_html.replaceAll( "" );
         textStr = htmlStr.replaceAll( " ", "" );
         textStr = htmlStr.replaceAll( "<", "<" );
         textStr = htmlStr.replaceAll( ">", ">" );
         textStr = htmlStr.replaceAll( "&", "&" );
         textStr = htmlStr.replaceAll( "&nbsp;", "" );
      }
      catch ( Exception e )
      {
         System.err.println( "Html2Text: " + e.getMessage() );
      }
      return textStr;
   }

   /**
    * �·�С��10��ȥ��0  
    * @param yearMonth
    * @return
    */
   /* public static String formatMm2M( String yearMonth )
    {
       if ( '0' == yearMonth.charAt( 5 ) )
       {
          yearMonth = yearMonth.substring( 0, 5 ) + yearMonth.substring( 6, 7 );
       }
       return yearMonth;
    }

    public static String formatM2Mm( String yearMonth )
    {
       if ( yearMonth.length() == 6 )
       {
          yearMonth = yearMonth.substring( 0, 5 ) + "0" + yearMonth.substring( 5, 6 );
       }
       return yearMonth;
    }*/

   // ��ȡ����ǰ��ʱ��
   public static Date getDateBefore( Date d, int day )
   {
      Calendar now = Calendar.getInstance();
      now.setTime( d );
      now.set( Calendar.DATE, now.get( Calendar.DATE ) - day );
      return now.getTime();
   }

   // ��ȡ������ʱ��
   public static Date getDateAfter( Date d, int day )
   {
      Calendar now = Calendar.getInstance();
      now.setTime( d );
      now.set( Calendar.DATE, now.get( Calendar.DATE ) + day );
      return now.getTime();
   }

   // ��ȡ������ǰ��ʱ��
   public static Date getDateBeforeMonth( Date d, int month )
   {
      Calendar now = Calendar.getInstance();
      now.setTime( d );
      now.set( Calendar.MONTH, now.get( Calendar.MONTH ) - month );
      return now.getTime();
   }

   // ��ȡ�����º��ʱ��
   public static Date getDateAfterMonth( Date d, int month )
   {
      Calendar now = Calendar.getInstance();
      now.setTime( d );
      now.set( Calendar.MONTH, now.get( Calendar.MONTH ) + month );
      return now.getTime();
   }

   // ��ȡ���ܼ���ʱ��  day = 1 ������һ   
   // �����ȡ�����¸�����6 �����������������6  user  for email
   public static Date getNextWeekDate( Date date, int day ) throws Exception
   {
      Calendar cal = Calendar.getInstance();
      cal.setTime( date );
      int week = cal.get( Calendar.DAY_OF_WEEK );
      if ( week > 2 )
      {
         cal.add( Calendar.DAY_OF_MONTH, -( week - ( day + 1 ) ) + 7 );
      }
      else
      {
         cal.add( Calendar.DAY_OF_MONTH, ( day + 1 ) - week + 7 );
      }
      return cal.getTime();
   }

   // ��ȡ�¸��µ�һ������ʱ��
   public static Date getFirstDayOfNextMonth( Date date ) throws Exception
   {
      Calendar cal = Calendar.getInstance();
      cal.setTime( date );
      cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) + 1 );
      cal.set( Calendar.DAY_OF_MONTH, 1 );
      return cal.getTime();
   }

   // ��ȡ�¸������һ������ʱ��  
   public static Date getLastDayOfNextMonth( Date date ) throws Exception
   {
      Calendar cal = Calendar.getInstance();
      cal.setTime( date );
      cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) + 1 );
      cal.set( Calendar.DAY_OF_MONTH, cal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
      return cal.getTime();
   }

   // ��ȡ�¸������һ������ʱ��  
   public static Date getOneDayOfNextMonth( Date date ) throws Exception
   {
      Calendar cal = Calendar.getInstance();
      cal.setTime( date );
      cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) + 1 );
      return cal.getTime();
   }

   /**
    * ��ȡ��ǰ���ڼ�
    * @return
    */

   public static int getActualDayOfWeek( Date date ) throws Exception
   {
      int[] weekNums = { 7, 1, 2, 3, 4, 5, 6 };
      Calendar cal = Calendar.getInstance();
      cal.setTime( date );
      return weekNums[ cal.get( Calendar.DAY_OF_WEEK ) - 1 ];
   }

   public static Date getLastestWeekDay( Date date, int day )
   {
      int[] weekNum = { Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY };
      Calendar cal = Calendar.getInstance();
      cal.setTime( date );
      cal.set( Calendar.DAY_OF_WEEK, weekNum[ day - 1 ] );
      if ( day == 7 )
      {
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      }

      return cal.getTime();
   }

   /**
    * ��ȡĳ��ʱ�䣬�޸����ʱ��Ϊ����µĵڼ���
    * @param date
    * @return
    */
   public static Date getFirstDateTimeOfMonth( Date date, int day )
   {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime( date );
      int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
      final int year = calendar.get( Calendar.YEAR );
      final int month = calendar.get( Calendar.MONTH );
      if ( year % 4 == 0 && year % 100 != 0 || year % 400 == 0 )
      {
         months[ 1 ] = 29;
      }
      calendar.set( GregorianCalendar.DAY_OF_MONTH, day > months[ month ] ? months[ month ] : day );
      return calendar.getTime();
   }

   public static List< String > getDistinctList( List< String > allList )
   {

      Set< String > resultSet = new HashSet< String >();
      if ( allList != null )
      {
         for ( String string : allList )
         {
            if ( KANUtil.filterEmpty( string ) != null )
            {
               resultSet.add( string );
            }
         }
      }
      return new ArrayList< String >( resultSet );
   }

   public static int obj2int( Object o )
   {
      String s = o.toString();
      if ( s.contains( "." ) )
      {
         Double d = Double.parseDouble( s );
         return d.intValue();
      }
      else
      {
         Integer i = Integer.parseInt( s );
         return i;
      }
   }

   public static List< MappingVO > getYears( final int begin, final int end )
   {
      final List< MappingVO > years = new ArrayList< MappingVO >();

      for ( int i = end; i >= begin; i-- )
      {
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( String.valueOf( i ) );
         mappingVO.setMappingValue( String.valueOf( i ) );

         years.add( mappingVO );
      }

      return years;
   }

   // String�������򣨳�ȥ�ظ�����
   public static String afterStringArraySort( final String[] arraySource )
   {
      final List< String > stringList = new ArrayList< String >();

      if ( arraySource != null && arraySource.length > 0 )
      {
         stringArraySort( arraySource );

         for ( String s : arraySource )
         {
            if ( arrayListContains( stringList, s ) )
            {
               stringList.add( s );
            }
         }
      }

      return stringListToJasonArray( stringList, "," );
   }

   // String������������Ҫ��Ԫ�ؿ�ת��int����
   public static void stringArraySort( final String[] stringArray )
   {
      String temp;
      for ( int i = 0; i < stringArray.length - 1; i++ )
      {
         for ( int j = stringArray.length - 1; j > i; j-- )
         {
            if ( Integer.valueOf( stringArray[ j ] ) < Integer.valueOf( stringArray[ j - 1 ] ) )
            {
               temp = stringArray[ j ];
               stringArray[ j ] = stringArray[ j - 1 ];
               stringArray[ j - 1 ] = temp;
            }
         }
      }
   }

   // String ArrayList����ĳ��Ԫ��
   public static boolean arrayListContains( final List< String > arrayList, final String target )
   {
      if ( arrayList.size() == 0 )
         return true;

      for ( String str : arrayList )
      {
         if ( str.equals( target ) )
            return false;
      }

      return true;
   }

   public static List< MappingVO > getMonths()
   {
      final List< MappingVO > months = new ArrayList< MappingVO >();

      for ( int i = 1; i <= 12; i++ )
      {
         final MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( String.valueOf( i < 10 ? ( "0" + i ) : i ) );
         mappingVO.setMappingValue( String.valueOf( i < 10 ? ( "0" + i ) : i ) + "��" );

         months.add( mappingVO );
      }

      return months;
   }

   public static String formatNumber2( Double num )
   {
      DecimalFormat df = new DecimalFormat( "##0.00" );
      return df.format( num );
   }
   
   /**
    * ��ʽ��Nλ
    * @param num
    * @param bit
    * @return
    */
   public static String formatNumberN( Double num, int bit )
   {
     if(bit < 1){
       // Ĭ��2λ
       return formatNumberN(num, 2);
     }
     String str = "##0.";
     for(int i = 0;i<bit;i++){
       str+="0";
     }
     DecimalFormat df = new DecimalFormat( str );
     return df.format( num );
   }

   public static byte[] object2bytes( Object object )
   {
      try
      {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream( bos );
         oos.writeObject( object );
         oos.flush();
         return bos.toByteArray();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return null;
      }
   }

   public static Object bytes2Object( byte[] data )
   {
      try
      {

         ByteArrayInputStream bis = new ByteArrayInputStream( data );
         ObjectInputStream ois = new ObjectInputStream( bis );
         return ois.readObject();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * ��listת�����ԡ�regular���ָ���ַ���
    * @param list
    * @param regular
    * @return
    * @author jack.sun
    * @date 2014-4-24
    */
   public static String convertListToString( List< String > list, String regular )
   {
      String result = "";
      if ( list != null && StringUtils.isNotBlank( regular ) )
      {
         for ( String string : list )
         {
            result = result + string + regular;
         }
         if ( StringUtils.isNotBlank( result ) )
         {
            return result.substring( 0, result.length() - regular.length() );
         }

      }
      return result;
   }

   /**
    * ���ԡ�regular���ָ����ַ���ת��list
    * @param source
    * @return
    */
   public static List< String > convertStringToList( String source, String regular )
   {
      if ( StringUtils.isBlank( source ) )
      {
         return null;
      }
      String[] array = source.split( regular );
      return Arrays.asList( array );
   }

   public static String formatNumber( final String numberString, final String accountId )
   {
      if ( filterEmpty( accountId ) != null )
      {
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );

         if ( accountConstants != null )
         {
            if ( filterEmpty( numberString ) != null )
            {
               return KANUtil.round( numberString, accountConstants.OPTIONS_ACCURACY, accountConstants.OPTIONS_ROUND );
            }
            else
            {
               return KANUtil.round( "0", accountConstants.OPTIONS_ACCURACY, accountConstants.OPTIONS_ROUND );
            }
         }
      }

      return numberString != null ? numberString : "0";
   }

   public static String formatMappingVOIds2StringJsonArray( final List< MappingVO > mappingVOs )
   {
      final String[] mappingArr = new String[ mappingVOs.size() ];
      for ( int i = 0; i < mappingVOs.size(); i++ )
      {
         mappingArr[ i ] = mappingVOs.get( i ).getMappingId();
      }
      return KANUtil.toJasonArray( mappingArr, "," );
   }

   public static String formatNumberNull2Zero( String str )
   {
      return KANUtil.filterEmpty( str ) == null ? "0" : str;
   }

   public static String decodeURLFromAjax( final String source ) throws KANException
   {
      try
      {
         if ( source != null && !source.isEmpty() )
         {
            return URLDecoder.decode( URLDecoder.decode( source, "UTF-8" ), "UTF-8" );
         }
         else
         {
            return "";
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public static String getSubString( final String target, final String beginStr, final String endStr )
   {
      if ( filterEmpty( target ) != null )
      {
         return target.substring( target.indexOf( beginStr ) + 1, target.indexOf( endStr ) );
      }

      return "";
   }

   public static Field[] mergeSuperclassField( final Field[] clazz, final Field[] superClazz )
   {
      if ( clazz != null && superClazz != null && superClazz.length > 0 )
      {
         final Field[] retObject = new Field[ clazz.length + superClazz.length ];
         int i = 0;
         for ( Field o : clazz )
         {
            retObject[ i ] = o;
            i++;
         }
         for ( Field o : superClazz )
         {
            retObject[ i ] = o;
            i++;
         }

         return retObject;
      }

      return clazz;
   }

   public static int getMaximumDaysOfMonth( final String monthly )
   {
      final Calendar calendar = KANUtil.getFirstCalendar( monthly );

      return calendar.getActualMaximum( Calendar.DAY_OF_MONTH );
   }

   // ��ʽ�����ڵķ�������0|30
   public static String formatDateForMinute( final String date )
   {
      Calendar calendar = createCalendar( formatDate( date, "yyyy-MM-dd HH:mm:ss" ) );
      int minute = calendar.get( Calendar.MINUTE );
      calendar.set( Calendar.MINUTE, minute < 30 ? 0 : 30 );
      return formatDate( calendar.getTime(), "yyyy-MM-dd HH:mm:ss" );
   }

   public static ByteArrayOutputStream getByteArray( BufferedImage image )
   {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      try
      {
         ImageIO.write( image, "png", bos );
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
      return bos;
   }

   public static int excelStrColToNumber( String str, Integer len )
   {
      int num = 0;
      int result = -1;

      for ( int i = 0; i < len; i++ )
      {
         char ch = str.charAt( len - i - 1 );
         num = ( int ) ( ch - 'A' + 1 );
         num *= Math.pow( 26, i );
         result += num;
      }
      return result;
   }

   private static void setExcelColumnName( StringBuilder str, int col )
   {
      int tmp = col / 26;
      if ( tmp > 26 )
      {
         setExcelColumnName( str, tmp - 1 );
      }
      else if ( tmp > 0 )
      {
         str.append( ( char ) ( tmp + 64 ) );
      }
      str.append( ( char ) ( col % 26 + 65 ) );
   }

   public static String getExcelColumnName( int col )
   {
      StringBuilder str = new StringBuilder( 2 );
      setExcelColumnName( str, col );
      return str.toString();
   }

}