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

   // �ļ���С�޶� 100M
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
    * �ļ��ϴ�
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
      // ʹ���ڴ泬��20Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            final String fileName = item.getName();

            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���
               final String encodedFileName = KANUtil.encodeString( fileName );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + encodedFileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               readAndImportDB( localFileString, mapping, request, response );

            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��ļ�ʧ�ܣ�" );
         throw new KANException( e );
      }
      return mapping.findForward( "" );
   }

   private void readAndImportDB( final String localFileString, final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // ��ʼ��Service�ӿ�
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      // ��Ϣ��־
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
            title_orm_property.put( "��Ա���", new Property( "employeeNo", "String", "[\\w\\W]{1,25}", "�ַ�������1-25֮��" ) );
            title_orm_property.put( "��Ա���������ģ�", new Property( "nameZH", "String", "[\\w\\W]{1,100}", "�ַ�������1-100֮��" ) );
            title_orm_property.put( "��Ա������Ӣ�ģ�", new Property( "nameEN", "String", "[\\w\\W]{1,100}", "�ַ�������1-100֮��" ) );
            Property salutation = new Property( "salutation", "Encode", "(����|Ůʿ|1|2)", "����ֻ��Ϊ������������Ůʿ������1������2��" );
            title_orm_property.put( "�ƺ�", salutation );

            salutation.setMappingList( employeeVO.getSalutations() ); /// ͨ���Զ��巽ʽ������list����
            title_orm_property.put( "����", new Property( "birthday", "Date", "", "���ڸ�ʽ����" ) );

            title_orm_property.put( "����", new Property( "residencyCityId", "String", null, null ) );

            Property status = new Property( "birthdayPlace", "Encode" );
            status.setMappingList( employeeVO.getStatuses() );
            title_orm_property.put( "��Ա״̬", status );

            title_orm_property.put( "������", new Property( "birthdayPlace", "String", "[\\w\\W]{1,100}", "�ַ�������1-100֮��" ));

            title_orm_property.put( "������ַ", new Property( "residencyAddress", "String", "[\\w\\W]{1,100}", "�ַ�������1-100֮��" ) );
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
                  employeeVO.reset();//���һ�¶���
               }
               catch ( KANException e )
               {
               }
               //��֤���
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
                  //1,��֤
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
                     messageInfo.append( cellData.getCellRef() + "��Ԫ�����㣺" + property.getErrorMsg() + ",���Ժ�����һ�У�<br/>" );
                     System.out.println( "��֤����" + cellData.getCellRef() + "��Ԫ�����㣺" + property.getErrorMsg() );
                     setStatusMsg( request, "2", messageInfo.toString() );
                     break;
                  }
                  else
                  //2,��ֵ
                  {
                     //                     BeanUtils.setProperty( bean, name, value )
                     try
                     {

                        if ( "Date".equalsIgnoreCase( validateType ) )
                        {
                           Class< ? > propertyType = PropertyUtils.getPropertyType( employeeVO, property.getPropertyName() );
                           if ( propertyType == Date.class )
                           {
                              System.out.println( "Date ��������" );
                              targateValue = KANUtil.createDate( cellData.getValue() );
                           }
                        }
                        else if ( "Encode".equalsIgnoreCase( validateType ) )
                        {
                           // �Զ���ת�� *** ��Ҫ�Ľ�
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

                        // ����ֵ���Ͳ�ƥ��Ŀ��Զ�ת����eg:Double,Float,Integer,Long,Short,String��java.sql.Date�ȣ����ǲ�֧��java.util.Date��
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

               if ( flag )//��֤ͨ���������
               {
                  // �½�����
                  try
                  {
                     employeeService.insertEmployee( employeeVO );
                     messageInfo.append( "��" + ( curRow + 1 ) + "�����ݵ���ɹ���" );
                     setStatusMsg( request, "2", messageInfo.toString() );
                     successCount++;//��������
                  }
                  catch ( KANException e )
                  {
                     messageInfo.append( "��" + ( curRow + 1 ) + "�����ݵ���ʧ�ܣ�" );
                     System.out.println( "####����������������" );
                     setStatusMsg( request, "2", messageInfo.toString() );
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
               }
            }

         }
         //�ĵ�����
         @Override
         public void endDocument() {
            messageInfo.append( "##"+"����ɹ�,�������¼"+successCount+"��" );
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
    * ������Ϣ�Ĵ���
    * 
    * @param request
    * @param info -- 1 �� ����  0 �� ����  2 : �ϴ����
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
    * ��ȡ״̬��Ϣ
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
    * ��ȡ�ļ��ϴ�״̬
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
         // ��ʼ�����ز���
         request.setCharacterEncoding( "UTF-8" );
         response.setContentType( "text/html; charset=UTF-8" );

         // ��ʼ��PrintWriter����
         final PrintWriter out = response.getWriter();

         // ���ص�ǰStatus
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
