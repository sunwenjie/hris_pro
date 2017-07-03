/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeCertificationVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeCertificationService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeCertificationAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-10-8 ����03:49:23  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-10-8 ����03:49:23  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeCertificationAction extends BaseAction
{

   public final static String accessAction = "HRO_BIZ_EMPLOYEE_CERTIIFICATION";

   /**
    * List employeeCertification
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
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         // ���Action Form
         final EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;

         // ����Զ�����������######
         // employeeCertificationVO.setRemark1( generateDefineListSearches( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION" ) );

         dealSubAction( employeeCertificationVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeCertificationVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeCertificationService.getEmployeeCertificationVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeCertification", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

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
      // ���ҳ��Token
      this.saveToken( request );
      final String employeeId = KANUtil.decodeString( request.getParameter( "employeeId" ) );
      EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;
      employeeCertificationVO.setEmployeeId( employeeId );
      // ����Sub Action
      employeeCertificationVO.setSubAction( CREATE_OBJECT );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeCertificationVO.getEmployeeId() );
      employeeCertificationVO.setEmployeeName( employeeVO.getName() );
      // ��ת���½�����
      return mapping.findForward( "manageEmployeeCertification" );
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
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         // ��õ�ǰ����
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeCertificationVO ) form ).getEmployeeCertificationId();
         }
         // ���������Ӧ����
         final EmployeeCertificationVO employeeCertificationVO = employeeCertificationService.getEmployeeCertificationVOByEmployeeCertificationId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeCertificationVO.reset( null, request );

         employeeCertificationVO.setSubAction( VIEW_OBJECT );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeCertificationVO.getEmployeeId() );
         employeeCertificationVO.setEmployeeName( employeeVO.getName() );

         request.setAttribute( "employeeCertificationForm", employeeCertificationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeCertification" );
   }

   /**
    * Add employeeCertification
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
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
            // ���ActionForm
            final EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;

            employeeCertificationVO.setAccountId( getAccountId( request, response ) );
            employeeCertificationVO.setCreateBy( getUserId( request, response ) );
            employeeCertificationVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeCertificationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION" ) );

            // �½�����
            employeeCertificationService.insertEmployeeCertification( employeeCertificationVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeCertificationVO, Operate.ADD, employeeCertificationVO.getEmployeeCertificationId(), null );
         }
         ( ( EmployeeCertificationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeCertification
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
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
            // ��õ�ǰ����
            String employeeCertificationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeCertificationVO employeeCertificationVO = employeeCertificationService.getEmployeeCertificationVOByEmployeeCertificationId( employeeCertificationId );

            // ��ȡ��¼�û�
            employeeCertificationVO.update( ( EmployeeCertificationVO ) form );

            employeeCertificationVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeCertificationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION" ) );

            // �޸Ķ���
            employeeCertificationService.updateEmployeeCertification( employeeCertificationVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
            
            insertlog( request, employeeCertificationVO, Operate.MODIFY, employeeCertificationVO.getEmployeeCertificationId(), null );
         }
         // ���Form����
         ( ( EmployeeCertificationVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeCertification
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         final EmployeeCertificationVO employeeCertificationVO = new EmployeeCertificationVO();
         // ��õ�ǰ����
         String employeeCertificationId = request.getParameter( "employeeCertificationId" );

         // ɾ��������Ӧ����
         employeeCertificationVO.setEmployeeCertificationId( KANUtil.decodeStringFromAjax( employeeCertificationId ) );
         employeeCertificationVO.setModifyBy( getUserId( request, response ) );
         employeeCertificationService.deleteEmployeeCertification( employeeCertificationVO );
         insertlog( request, employeeCertificationVO, Operate.DELETE, employeeCertificationId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Cerfication by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Siuvan at 2014-11-20
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );

         // ��õ�ǰ����
         final String employeeCertificationId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeCertificationId" ) );

         // ��ʼ��EmployeeCertificationVO
         final EmployeeCertificationVO employeeCertificationVO = employeeCertificationService.getEmployeeCertificationVOByEmployeeCertificationId( employeeCertificationId );
         employeeCertificationVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeCertificationService.deleteEmployeeCertification( employeeCertificationVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeCertificationVO, Operate.DELETE, employeeCertificationId, "delete_object_ajax" );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeCertification list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         // ���Action Form
         EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;
         // ����ѡ�е�ID
         if ( employeeCertificationVO.getSelectedIds() != null && !employeeCertificationVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeCertificationVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeCertificationVO.setEmployeeCertificationId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeCertificationVO.setModifyBy( getUserId( request, response ) );
               employeeCertificationService.deleteEmployeeCertification( employeeCertificationVO );
            }
         }
         // ���Selected IDs����Action
         employeeCertificationVO.setSelectedIds( "" );
         employeeCertificationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * ��֤֤�� �����Ƿ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward is_employeeCertification_exist( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String employeeId = request.getParameter( "employeeId" );
         //��ʼ���ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         //��ȡ��ǰ��Ա������֤�� ����
         List< Object > employeeCertificationVOs = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );
         //��ȡ��ǰѡ���֤�� ����
         final String certificationId = request.getParameter( "certificationId" );
         boolean flag = false;
         //�жϵ�ǰѡ���֤�� �����Ƿ�����ڵ�ǰ��Ա���еĻ
         EmployeeCertificationVO c = null;
         EmployeeCertificationVO theExistEmployeeCertificationVO = null;
         for ( Object o : employeeCertificationVOs )
         {
            c = ( EmployeeCertificationVO ) o;
            if ( c.getCertificationId().equals( certificationId ) )
            {//�������
               theExistEmployeeCertificationVO = c;
               flag = true;
            }
         }

         if ( flag )
         {
            out.print( theExistEmployeeCertificationVO.encodedField( theExistEmployeeCertificationVO.getEmployeeCertificationId() ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * ��ȡ�Ѵ��ڵ�֤�� ������ڵĻ����ɫ���
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_employeeCertificationsIDs_jsonArray( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         //��ʼ���ӿ�
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         //��ȡ��Աid
         final String employeeId = request.getParameter( "employeeId" );
         //��ȡ��ǰ��Ա������֤�� ����
         List< Object > employeeCertificationVOs = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );

         JSONArray jsonArray = new JSONArray();
         if ( employeeCertificationVOs != null )
         {
            for ( Object o : employeeCertificationVOs )
            {

               jsonArray.add( ( ( EmployeeCertificationVO ) o ).getCertificationId() );
            }
         }
         out.print( jsonArray.toString() );
         out.flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }
}
