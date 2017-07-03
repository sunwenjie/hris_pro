/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.FileUploadVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.FileUploadProgressListener;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.Property;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**
 * @author Kevin Jin
 */

public class EmployeeImportAction extends BaseAction
{

   // 文件大小限定 100M
   protected long maxSize = 1000;

   public ActionForward list_employee( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return null;
   }

   /**
    * List EmployeeBaseViews by Jason format
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      return null;
   }

   /**
    * List employee
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      return null;
   }

   /**
    * to_objectNew
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      return null;
   }

   /**
    * to_objectModify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      return null;
   }

   /**
    * Add employee
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      return null;
   }

   /**
    * Modify employee
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return null;
   }

   /**
    * 文件上传
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward upload( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {



      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过20M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            final String fileName = item.getName();

            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 编码文件名
               final String encodedFileName = KANUtil.encodeString( fileName );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + encodedFileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               readAndImportDB( localFileString, mapping, request, response );

            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传文件失败！" );
         throw new KANException( e );
      }
      return mapping.findForward( "" );
   }

   private void readAndImportDB( final String localFileString, final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // 初始化Service接口
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      // 消息日志
      final StringBuffer messageInfo = new StringBuffer();
      messageInfo.append( "" );
      final EmployeeVO employeeVO = new EmployeeVO();
      employeeVO.setAccountId( getAccountId( request, response ) );
      employeeVO.setCreateBy( getUserId( request, response ) );
      employeeVO.setCreateDate( new Date() );
      employeeVO.setDeleted("1");
      employeeVO.reset( mapping, request );

      ReadXlsxHander readXlsxHander = new ReadXlsxHander()
      {

         int successCount=0;
         
         final List< String > titleNames = new ArrayList< String >();
         //final String targateVOClass = "com.kan.hro.domain.biz.employee.EmployeeVO.java";
         final Map< String, Property > title_orm_property = new HashMap< String, Property >();
         {
            title_orm_property.put( "雇员编号", new Property( "employeeNo", "String", "[\\w\\W]{1,25}", "字符长度在1-25之间" ) );
            title_orm_property.put( "雇员姓名（中文）", new Property( "nameZH", "String", "[\\w\\W]{1,100}", "字符长度在1-100之间" ) );
            title_orm_property.put( "雇员姓名（英文）", new Property( "nameEN", "String", "[\\w\\W]{1,100}", "字符长度在1-100之间" ) );
            Property salutation = new Property( "salutation", "Encode", "(先生|女士|1|2)", "内容只能为“先生”，“女士”，“1”，“2”" );
            title_orm_property.put( "称呼", salutation );

            salutation.setMappingList( employeeVO.getSalutations() ); /// 通过自定义方式填充这个list对象
            title_orm_property.put( "生日", new Property( "birthday", "Date", "", "日期格式错误" ) );

            title_orm_property.put( "籍贯", new Property( "residencyCityId", "String", null, null ) );

            Property status = new Property( "birthdayPlace", "Encode" );
            status.setMappingList( employeeVO.getStatuses() );
            title_orm_property.put( "雇员状态", status );

            title_orm_property.put( "出生地", new Property( "birthdayPlace", "String", "[\\w\\W]{1,100}", "字符长度在1-100之间" ));

            title_orm_property.put( "户籍地址", new Property( "residencyAddress", "String", "[\\w\\W]{1,100}", "字符长度在1-100之间" ) );
         }

         @Override
         public void optRows( int sheetIndex, int curRow, List< CellData > rowlist ) throws SQLException
         {
            if ( curRow == 0 )
            {
               for ( CellData cellDate : rowlist )
               {
                  titleNames.add( cellDate.getValue() );
               }
            }
            else
            {
               try
               {
                  employeeVO.reset();//清空一下对象
               }
               catch ( KANException e )
               {
               }
               //验证标记
               boolean flag = true;
               for ( int i = 0; i < rowlist.size(); i++ )
               {
                  
                  CellData cellData = rowlist.get( i );
                  String title = titleNames.get( i );
                  Property property = ( Property ) title_orm_property.get( title );
                  if ( property == null || cellData == null || cellData.getValue() == null )
                  {
                     continue;
                  }
                  //1,验证
                  String validateType = property.getValidateType();
                  Object targateValue = null;
                  if ( validateType != null && !validateType.isEmpty() )
                  {
                     String regEx = property.getValidateRegEx();
                     if ( validateType.equals( "String" ) )
                     {
                        if ( regEx != null && !regEx.isEmpty() )
                        {
                           Pattern p = Pattern.compile( regEx );
                           Matcher m = p.matcher( cellData.getValue() );
                           flag = m.matches();
                        }
                     }
                     else if ( validateType.equals( "Date" ) )
                     {
                        if ( regEx != null && !regEx.isEmpty() )
                        {
                           Pattern p = Pattern.compile( Property.REG_EX_DATE );
                           Matcher m = p.matcher( cellData.getValue() );
                           flag = m.matches();
                        }
                     }
                     else if ( validateType.equals( "Encode" ) )
                     {

                     }

                  }
                  if ( !flag )
                  {
                     messageInfo.append( cellData.getCellRef() + "单元格不满足：" + property.getErrorMsg() + ",所以忽略这一行！<br/>" );
                     System.out.println( "验证出错：" + cellData.getCellRef() + "单元格不满足：" + property.getErrorMsg() );
                     setStatusMsg( request, "2", messageInfo.toString() );
                     break;
                  }
                  else
                  //2,赋值
                  {
                     //                     BeanUtils.setProperty( bean, name, value )
                     try
                     {

                        if ( "Date".equalsIgnoreCase( validateType ) )
                        {
                           Class< ? > propertyType = PropertyUtils.getPropertyType( employeeVO, property.getPropertyName() );
                           if ( propertyType == Date.class )
                           {
                              System.out.println( "Date 类型数据" );
                              targateValue = KANUtil.createDate( cellData.getValue() );
                           }
                        }
                        else if ( "Encode".equalsIgnoreCase( validateType ) )
                        {
                           // 自定义转换 *** 需要改进
                           // targateValue = employeeVO.getEncodedSalutation( cellData.getValue() );

                           if ( property.getMappingList() != null & cellData.getValue() != null )
                           {
                              for ( MappingVO mappingVO : property.getMappingList() )
                              {
                                 if ( mappingVO.getMappingValue().trim().equalsIgnoreCase( cellData.getValue().trim() ) )
                                 {
                                    targateValue = mappingVO.getMappingId();
                                    break;
                                 }
                              }
                           }
                           targateValue = "0";
                        }
                        else
                        {
                           targateValue = cellData.getValue();
                        }

                        // 属性值类型不匹配的可自动转换（eg:Double,Float,Integer,Long,Short,String，java.sql.Date等，但是不支持java.util.Date）
                        BeanUtils.setProperty( employeeVO, property.getPropertyName(), targateValue );
                     }
                     catch ( IllegalAccessException e )
                     {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                     }
                     catch ( InvocationTargetException e )
                     {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                     }
                     catch ( NoSuchMethodException e )
                     {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                     }
                  }
               }

               if ( flag )//验证通过保存对象
               {
                  // 新建对象
                  try
                  {
                     employeeService.insertEmployee( employeeVO );
                     messageInfo.append( "第" + ( curRow + 1 ) + "行数据导入成功！" );
                     setStatusMsg( request, "2", messageInfo.toString() );
                     successCount++;//数量增加
                  }
                  catch ( KANException e )
                  {
                     messageInfo.append( "第" + ( curRow + 1 ) + "行数据导入失败！" );
                     System.out.println( "####保存对象出错！！！！" );
                     setStatusMsg( request, "2", messageInfo.toString() );
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
               }
            }

         }
         //文档结束
         @Override
         public void endDocument() {
            messageInfo.append( "##"+"导入成功,共导入记录"+successCount+"条" );
            setStatusMsg( request, "3", messageInfo.toString() );
            System.out.println("#################################endDocument#########################################");
            
         }
         
      };

      try
      {
         readXlsxHander.process( localFileString );
      }
      catch ( Exception e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   /**
    * 
    * 错误信息的处理
    * 
    * @param request
    * @param info -- 1 ： 错误  0 ： 正常  2 : 上传完成
    * @param message
    */
   private void setStatusMsg( HttpServletRequest request, String info, String message )
   {
      final FileUploadVO status = ( FileUploadVO ) request.getSession().getAttribute( SESSION_NAME_UPLOAD_STATUS );
      status.setInfo( info );
      status.setStatusMsg( message );
   }

   /**
    * 
    * 获取状态信息
    * 
    * @param request
    * @param out
    */
   private void getStatusMsg( final HttpServletRequest request, final PrintWriter out )
   {
      final FileUploadVO status = ( FileUploadVO ) request.getSession().getAttribute( SESSION_NAME_UPLOAD_STATUS );

      if ( status != null )
      {
         out.println( status.toJSon() );
         if ( status.getInfo() != null && ( status.getInfo().trim().equals( "3" ) ) )
         {
            request.getSession().removeAttribute( SESSION_NAME_UPLOAD_STATUS );
         }
      }
      else
      {
         out.println( new FileUploadVO().toJSon() );
      }
   }

   /**
    * 获取文件上传状态
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward getStatusMessage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化返回参数
         request.setCharacterEncoding( "UTF-8" );
         response.setContentType( "text/html; charset=UTF-8" );

         // 初始化PrintWriter对象
         final PrintWriter out = response.getWriter();

         // 返回当前Status
         getStatusMsg( request, out );

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
