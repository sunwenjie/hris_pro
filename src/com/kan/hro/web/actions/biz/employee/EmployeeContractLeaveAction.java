/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractLeaveAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-8-23 ����11:01:46  
* 
*/
public class EmployeeContractLeaveAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE";

   /**
    * List employeeContractLeave
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
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         // ���Action Form
         final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;

         // ����Զ�����������######
         // employeeContractLeaveVO.setRemark1( generateDefineListSearches( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );

         // �����SubAction��Ϊ��
         if ( employeeContractLeaveVO.getSubAction() != null && !employeeContractLeaveVO.getSubAction().trim().equals( "" ) )
         {
            // �����SubAction��ɾ���б����
            if ( employeeContractLeaveVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               // ����ɾ���б��SubAction
               delete_objectList( mapping, form, request, response );
            }
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( employeeContractLeaveVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeContractLeaveVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractLeaveService.getEmployeeContractLeaveVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();

            // Send to client
            out.println( ListRender.generateListTable( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listEmployeeContractLeave" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��Service
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

      // ���ContractId
      final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

      // ���EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // Ĭ��ֵ����
      final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;
      employeeContractLeaveVO.setSubAction( CREATE_OBJECT );
      employeeContractLeaveVO.setContractId( contractId );
      employeeContractLeaveVO.setBenefitQuantity( "0" );
      employeeContractLeaveVO.setLegalQuantity( "0" );
      employeeContractLeaveVO.setCycle( "1" );
      employeeContractLeaveVO.setProbationUsing( "1" );
      employeeContractLeaveVO.setDelayUsing( "2" );
      employeeContractLeaveVO.setStatus( "1" );
      employeeContractLeaveVO.setDescription( "" );

      // ����ӵĿ�Ŀ��ǰ̨�ų�
      List< Object > objects = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( contractId );
      String addedItems = "";
      for ( Object obj : objects )
      {
         final EmployeeContractLeaveVO tempVO = ( EmployeeContractLeaveVO ) obj;
         if ( "41".equalsIgnoreCase( tempVO.getItemId() ) )
            continue;

         addedItems += tempVO.getItemId();
         addedItems += ",";
      }

      // �ų���١����ȥ�ꡢ����
      addedItems += "48,49,60,";
      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );
      request.setAttribute( "addedItems", KANUtil.filterEmpty( addedItems ) == null ? addedItems : addedItems.substring( 0, addedItems.length() - 1 ) );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractLeave" );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractLeaveVO ) form ).getEmployeeLeaveId();
         }

         // ���EmployeeContractLeaveVO
         final EmployeeContractLeaveVO employeeContractLeaveVO = employeeContractLeaveService.getEmployeeContractLeaveVOByEmployeeLeaveId( id );
         employeeContractLeaveVO.reset( null, request );
         employeeContractLeaveVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractLeaveVO.getContractId() );
         employeeContractVO.reset( null, request );

         // ����ӵĿ�Ŀ��ǰ̨�ų�(�������Լ�)
         List< Object > objects = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( employeeContractLeaveVO.getContractId() );
         String addedItems = "";
         for ( Object obj : objects )
         {
            final EmployeeContractLeaveVO tempVO = ( EmployeeContractLeaveVO ) obj;
            if ( !tempVO.getItemId().equals( employeeContractLeaveVO.getItemId() ) )
            {
               addedItems += tempVO.getItemId();
               addedItems += ",";
            }
         }

         // �ų���١����ȥ�ꡢ����
         addedItems += "48,49,60,";
         // ����Attribute
         request.setAttribute( "employeeContractLeaveForm", employeeContractLeaveVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
         request.setAttribute( "addedItems", KANUtil.filterEmpty( addedItems ) == null ? addedItems : addedItems.substring( 0, addedItems.length() - 1 ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractLeave" );
   }

   /**
    * Add Employee Contract Leave
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

            // ���ActionForm
            final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;
            employeeContractLeaveVO.setCreateBy( getUserId( request, response ) );
            employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeContractLeaveVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );

            // �½�����
            employeeContractLeaveService.insertEmployeeContractLeave( employeeContractLeaveVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractLeaveVO, Operate.ADD, employeeContractLeaveVO.getEmployeeLeaveId(), null );
         }
         // �ظ��ύ���� 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractLeaveVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // ���Form����
         ( ( EmployeeContractLeaveVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract Leave
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

            // ��õ�ǰ����
            final String employeeLeaveId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ���EmployeeContractLeaveVO
            final EmployeeContractLeaveVO employeeContractLeaveVO = employeeContractLeaveService.getEmployeeContractLeaveVOByEmployeeLeaveId( employeeLeaveId );
            employeeContractLeaveVO.update( ( EmployeeContractLeaveVO ) form );
            employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            employeeContractLeaveVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" ) );

            // �޸Ķ���
            employeeContractLeaveService.updateEmployeeContractLeave( employeeContractLeaveVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeContractLeaveVO, Operate.MODIFY, employeeContractLeaveVO.getEmployeeLeaveId(), null );
         }

         // ���Form����
         ( ( EmployeeContractLeaveVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract Leave list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

         // ���Action Form
         final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) form;

         // ����ѡ�е�ID
         if ( employeeContractLeaveVO.getSelectedIds() != null && !employeeContractLeaveVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractLeaveVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeContractLeaveVO.setEmployeeLeaveId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );
               employeeContractLeaveService.deleteEmployeeContractLeave( employeeContractLeaveVO );
            }

            insertlog( request, employeeContractLeaveVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractLeaveVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         employeeContractLeaveVO.setSelectedIds( "" );
         employeeContractLeaveVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract Leave by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

         // ��õ�ǰ����
         String employeeLeaveId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeLeaveId" ) );

         // ɾ��������Ӧ����
         final EmployeeContractLeaveVO employeeContractLeaveVO = employeeContractLeaveService.getEmployeeContractLeaveVOByEmployeeLeaveId( employeeLeaveId );
         employeeContractLeaveVO.setEmployeeLeaveId( employeeLeaveId );
         employeeContractLeaveVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeContractLeaveService.deleteEmployeeContractLeave( employeeContractLeaveVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractLeaveVO, Operate.DELETE, employeeLeaveId, null );
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
    * List Object Options Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Siuvan
   // Reviewed by Kevin Jin at 2013-11-25
   // �������.����Ҫ����
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ��ĿID
         final String itemId = request.getParameter( "itemId" );

         // ��ȡ������ϢID
         final String contractId = request.getParameter( "contractId" );

         boolean isFemale = true;
         // ��ʼ��Service
         final LeaveHeaderService leaveHeaderService = ( LeaveHeaderService ) getService( "leaveHeaderService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null )
         {
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            if ( employeeVO != null )
            {
               if ( "1".equals( employeeVO.getSalutation() ) )
               {
                  isFemale = false;
               }
            }

         }

         // ��ȡEmployeeContractLeaveVO�б������Ӱ໻�ݣ�
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( contractId );
         // ����û�е�item ��������ڽ���������ļ�, �����ý���.  eg  16��12����17��1��. ���ڻ���16���
         boolean existItem = false;
         if(KANUtil.filterEmpty(itemId) !=null && employeeContractLeaveVOs!=null && employeeContractLeaveVOs.size()>0){
           for(EmployeeContractLeaveVO contractLeaveVO:employeeContractLeaveVOs){
             if(contractLeaveVO.getItemId().equals(itemId)){
               existItem = true;
               break;
             }
           }
           // �����ھ���Ҫ���
           if(!existItem){
             EmployeeContractLeaveVO tmpVO = new EmployeeContractLeaveVO();
             tmpVO.setAccountId(getAccountId(request, response));
             tmpVO.setCorpId(getCorpId(request, response));
             tmpVO.setLocale(request.getLocale());
             tmpVO.setItemId(itemId);
             employeeContractLeaveVOs.add(tmpVO);
           }
         }

         // ��ʼ��MappingVO�б�
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, ( ( EmployeeContractLeaveVO ) form ).getEmptyMappingVO() );

         // ����EmployeeContractLeaveVO�б������Ӱ໻�ݣ�
         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
            {
               employeeContractLeaveVO.reset( null, request );
               if ( "42".equals( employeeContractLeaveVO.getItemId() ) || "43".equals( employeeContractLeaveVO.getItemId() ) )
               {
                  continue;
               }
               // �������,�����ǲ���
               if ( !isFemale && "45".equals( employeeContractLeaveVO.getItemId() ) )
               {
                  continue;
               }
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( employeeContractLeaveVO.getItemId() );
               mappingVO.setMappingValue( employeeContractLeaveVO.getDecodeItemId() );
               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "itemId", itemId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // У���ݼ����ò����ظ�
   private boolean checkEmployeeContractLeave( final List< Object > employeeContractLeave, final String itemId, final String year, final String employeeLeaveId )
   {
      boolean flag = true;
      if ( employeeContractLeave != null && employeeContractLeave.size() > 0 )
      {
         for ( Object o : employeeContractLeave )
         {
            final EmployeeContractLeaveVO tempEmployeeContractLeaveVO = ( EmployeeContractLeaveVO ) o;
            if ( employeeLeaveId != null && tempEmployeeContractLeaveVO.getEmployeeLeaveId().equals( employeeLeaveId ) )
               continue;

            // ��������
            if ( "41".equals( itemId ) )
            {
               if ( tempEmployeeContractLeaveVO.getItemId().equals( itemId ) && tempEmployeeContractLeaveVO.getYear().equals( year ) )
               {
                  flag = false;
                  break;
               }
            }
            else
            {
               if ( tempEmployeeContractLeaveVO.getItemId().equals( itemId ) )
               {
                  flag = false;
                  break;
               }
            }
         }
      }

      return flag;
   }

   public void checkEmployeeContractLeave_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeContractLeaveService employeeContractLeaveService = ( EmployeeContractLeaveService ) getService( "employeeContractLeaveService" );

         final String contractId = request.getParameter( "contractId" );
         final String itemId = request.getParameter( "itemId" );
         final String year = request.getParameter( "year" );
         final String employeeLeaveId = request.getParameter( "employeeLeaveId" );

         final List< Object > employeeContractLeaveVOs = employeeContractLeaveService.getEmployeeContractLeaveVOsByContractId( contractId );

         boolean exist = checkEmployeeContractLeave( employeeContractLeaveVOs, itemId, year, employeeLeaveId );

         out.print( exist ? "1" : "2" );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

}
