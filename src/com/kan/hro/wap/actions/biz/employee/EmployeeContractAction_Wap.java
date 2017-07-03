/*
 * Created on 2013-04-11
 */

package com.kan.hro.wap.actions.biz.employee;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractBaseView;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**
 * @author iori
 */

public class EmployeeContractAction_Wap extends BaseAction
{

   

   /**
    * @author Siuvan
    * ����У����ݹ�Աѡ������ÿͻ��б�
    */

   /*public ActionForward list_client_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ù�ԱID
         final String employeeId = request.getParameter( "employeeId" );
         // ��ʼ��Servce�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         // ��ʼ������
         final ClientBaseView clientBaseView = new ClientBaseView();
         clientBaseView.setId( employeeId );
         clientBaseView.setAccountId( getAccountId( request, response ) );
         // ��ù�ԱID��Ӧ�Ŀͻ��б�
         final List< Object > clientBaseViews = employeeContractService.getClientBaseViewsByCondition( clientBaseView );
         // ��ʼ��MappingVO�б�
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         if ( clientBaseViews != null && clientBaseViews.size() > 0 )
         {
            for ( Object clientBaseViewObject : clientBaseViews )
            {
               final ClientBaseView baseView = ( ClientBaseView ) clientBaseViewObject;
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( baseView.getId() );
               mappingVO.setMappingValue( baseView.getName() );
               mappingVOs.add( mappingVO );
            }
         }
         mappingVOs.add( 0, ( ( EmployeeContractVO ) form ).getEmptyMappingVO() );
         if ( mappingVOs != null && mappingVOs.size() > 0 )
         {
            StringBuffer returnJson = new StringBuffer();
            //�õ��ͻ��б�
            returnJson.append( "{\"success\":\"success\",\"msg\":" );
           
            returnJson.append( JSONArray.fromObject( mappingVOs ).toString() );
            returnJson.append( "," ).append( "\"other\":\"\"");
            returnJson.append( "}" );
            
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();
            // Send to client
            out.println( returnJson.toString() );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         new KANException( e );
      }
      return null;
   }*/
   
   /***
    * @author iori
    * ���ݿͻ�ID��÷���Э��
    */

   public ActionForward list_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ÿͻ�ID
         final String clientId = request.getParameter( "clientId" );
         // ��ù�ԱID
         final String employeeId = request.getParameter( "employeeId" );
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         // ��ʼ������
         final EmployeeContractBaseView employeeContractBaseView = new EmployeeContractBaseView();
         employeeContractBaseView.setClientId( clientId );
         employeeContractBaseView.setEmployeeId( employeeId );
         employeeContractBaseView.setAccountId( getAccountId( request, response ) );
         // ��ÿͻ�ID��Ӧ�ķ���Э���б�
         final List< Object > employeeContractBaseViews = employeeContractService.getEmployeeContractBaseViewsByClientId( employeeContractBaseView );
         // ��ʼ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         if ( employeeContractBaseViews != null && employeeContractBaseViews.size() > 0 )
         {
            for ( Object employeeContractBaseViewObject : employeeContractBaseViews )
            {
               final EmployeeContractBaseView baseView = ( EmployeeContractBaseView ) employeeContractBaseViewObject;
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( baseView.getId() );
               mappingVO.setMappingValue( baseView.getName() );
               mappingVOs.add( mappingVO );
            }
         }
         mappingVOs.add( 0, ( ( EmployeeContractVO ) form ).getEmptyMappingVO() );
         if ( mappingVOs != null && mappingVOs.size() > 0 )
         {
            StringBuffer returnJson = new StringBuffer();
            //�õ��ͻ��б�
            returnJson.append( "{\"success\":\"success\",\"msg\":" );
            returnJson.append( JSONArray.fromObject( mappingVOs ).toString() );
          
            returnJson.append( "," ).append( "\"other\":\"\"");
            returnJson.append( "}" );
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();
            // Send to client
            out.println( returnJson.toString() );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
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
