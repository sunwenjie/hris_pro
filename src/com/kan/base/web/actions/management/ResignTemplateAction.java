package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ResignTemplateVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.LocationVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ResignTemplateService;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.HTMLParseUtil;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.pdf.PDFTool;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPDFVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class ResignTemplateAction extends BaseAction
{
   public static String accessAction = "HRO_MGT_RESIGN_TEMPLATE";

   public static final String STRING_BLANK = "____________";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
         // ���Action Form
         final ResignTemplateVO resignTemplateVO = ( ResignTemplateVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         resignTemplateVO.setAccountId( getAccountId( request, response ) );
         // ����ǿͻ���¼
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            resignTemplateVO.setCorpId( getCorpId( request, response ) );
         }
         // ����ɾ������
         if ( resignTemplateVO.getSubAction() != null && resignTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( resignTemplateVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder resignTemplateHolder = new PagedListHolder();
         // ���뵱ǰҳ
         resignTemplateHolder.setPage( page );
         // ���뵱ǰֵ����
         resignTemplateHolder.setObject( resignTemplateVO );
         // ����ҳ���¼����
         resignTemplateHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         resignTemplateService.getResignTemplateVOsByCondition( resignTemplateHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( resignTemplateHolder, request );
         // Holder��д��Request����
         request.setAttribute( "resignTemplateHolder", resignTemplateHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listResignTemplateTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listResignTemplate" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( ResignTemplateVO ) form ).setStatus( ResignTemplateVO.TRUE );
      ( ( ResignTemplateVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageResignTemplate" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
            // ��õ�ǰFORM
            final ResignTemplateVO resignTemplateVO = ( ResignTemplateVO ) form;

            resignTemplateVO.setCreateBy( getUserId( request, response ) );
            resignTemplateVO.setModifyBy( getUserId( request, response ) );
            resignTemplateVO.setAccountId( getAccountId( request, response ) );
            resignTemplateVO.setClientId( getClientId( request, response ) );
            resignTemplateVO.setCorpId( getCorpId( request, response ) );
            resignTemplateService.insertResignTemplate( resignTemplateVO );

            // ��ʼ������
            constantsInit( "initResignTemplate", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, resignTemplateVO, Operate.ADD, resignTemplateVO.getTemplateId(), null );
         }

         // ���Form
         ( ( ResignTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Ajax Tab
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   public void modify_object_ajax_tab( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         boolean flag = false;

         // ���resignTemplateId
         final String resignTemplateId = request.getParameter( "resignTemplateId" );

         if ( resignTemplateId != null && !resignTemplateId.trim().isEmpty() )
         {
            // ��ʼ�� Service�ӿ�
            final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
            final ResignTemplateVO resignTemplateVO = resignTemplateService.getResignTemplateVOByResignTemplateId( resignTemplateId );

            final String actionFlag = request.getParameter( "actionFlag" );

            // �����ɾ��
            if ( actionFlag != null && actionFlag.trim().equals( "delObject" ) )
            {
               // ����״̬��Ajax
               if ( flag )
               {
                  deleteSuccessAjax( out, null );
               }
               else
               {
                  deleteFailedAjax( out, null );
               }

            }
            else if ( actionFlag != null && actionFlag.trim().equals( "addObject" ) )
            {
               // ����״̬��Ajax
               if ( flag )
               {
                  addSuccessAjax( out, null );
               }
               else
               {
                  addFailedAjax( out, null );
               }

            }
            else
            {
               //final String templateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
               resignTemplateVO.setModifyBy( getUserId( request, response ) );
               resignTemplateVO.setModifyDate( new Date() );
               if ( resignTemplateService.updateResignTemplate( resignTemplateVO ) > 0 )
               {
                  flag = true;
               }
               // ����״̬��Ajax
               if ( flag )
               {
                  addSuccessAjax( out, null );
               }
               else
               {
                  addFailedAjax( out, null );
               }
            }
         }
         constantsInit( "initResignTemplate", getAccountId( request, response ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
         // ������ȡ�����
         String resignTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( resignTemplateId ) == null )
         {
            resignTemplateId = ( ( ResignTemplateVO ) form ).getTemplateId();
         }
         // ���resignTemplateVO����                                                                                          
         final ResignTemplateVO resignTemplateVO = resignTemplateService.getResignTemplateVOByResignTemplateId( resignTemplateId );
         // ����Add��Update
         resignTemplateVO.setSubAction( VIEW_OBJECT );
         resignTemplateVO.reset( null, request );

         request.setAttribute( "resignTemplateId", resignTemplateId );

         request.setAttribute( "resignTemplateForm", resignTemplateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageResignTemplate" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
            // ������ȡ�����
            final String resignTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
            // ��ȡResignTemplateVO����
            final ResignTemplateVO resignTemplateVO = resignTemplateService.getResignTemplateVOByResignTemplateId( resignTemplateId );

            // װ�ؽ��洫ֵ
            resignTemplateVO.update( ( ResignTemplateVO ) form );
            // ��ȡ��¼�û�
            resignTemplateVO.setModifyBy( getAccountId( request, response ) );
            // �����޸ķ���
            resignTemplateService.updateResignTemplate( resignTemplateVO );

            // ��ʼ������
            constantsInit( "initResignTemplate", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, resignTemplateVO, Operate.MODIFY, resignTemplateVO.getTemplateId(), null );
         }
         // ���Form
         ( ( ResignTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
         // ���Action Form
         final ResignTemplateVO resignTemplateVO = ( ResignTemplateVO ) form;
         // ����ѡ�е�ID
         if ( resignTemplateVO.getSelectedIds() != null && !resignTemplateVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : resignTemplateVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               resignTemplateVO.setTemplateId( selectedId );
               resignTemplateVO.setModifyBy( getUserId( request, response ) );
               resignTemplateVO.setModifyDate( new Date() );
               resignTemplateService.deleteResignTemplate( resignTemplateVO );
            }

            insertlog( request, resignTemplateVO, Operate.DELETE, null, resignTemplateVO.getSelectedIds() );
         }

         // ��ʼ������
         constantsInit( "initResignTemplate", getAccountId( request, response ) );

         // ���Selected IDs����Action
         resignTemplateVO.setSelectedIds( "" );
         resignTemplateVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         String status = request.getParameter( "status" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final List< MappingVO > resignTemplateMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getResignTemplates( request.getLocale().getLanguage(), getCorpId( request, response ) );
         final List< MappingVO > templateMappingVOs = new ArrayList< MappingVO >();
         templateMappingVOs.add( KANUtil.getEmptyMappingVO( getLocale( request ) ) );

         for ( MappingVO mappingVO : resignTemplateMappingVOs )
         {
            if ( StringUtils.equals( mappingVO.getMappingId(), "1" ) )
            {
               if ( StringUtils.equals( "7", status ) )
               {
                  templateMappingVOs.add( mappingVO );
               }
            }
            else
            {
               templateMappingVOs.add( mappingVO );
            }
         }

         out.print( KANUtil.getOptionHTML( templateMappingVOs, "templateId", null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   // �����˹���PDF
   public ActionForward exportResign( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         final String contractId = request.getParameter( "contractId" );
         final String resignTemplateId = request.getParameter( "resignTemplateId" );
         final ResignTemplateVO resignTemplateVO = accountConstants.getResignTemplatesById( resignTemplateId, KANUtil.filterEmpty( getCorpId( request, response ) ) );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         final EntityVO entityVO = accountConstants.getEntityVOByEntityId( employeeContractVO.getEntityId() );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( employeeContractVO.getContractId() );
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
         final ClientVO clientVO = clientService.getClientVOByClientId( employeeContractVO.getClientId() );
         final LocationVO locationVO = locationService.getLocationVOByLocationId( entityVO.getLocationId() );
         final EmployeeContractPDFVO employeeContractPDFVO = new EmployeeContractPDFVO();
         boolean hasProbationUsing = true;
         int baseSalary = 0;
         int probationSalary = 0;
         String probationMonth = "";
         String workAddress = "";
         int index_1 = 0;
         int index_2 = 0;

         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            // ��ʼ��EmployeeContractSalaryVO
            final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;

            // �������ʿ�Ŀֻȡһ���ۼ�
            if ( employeeContractSalaryVO.getItemId().equals( "1" ) )
            {
               if ( employeeContractSalaryVO.getProbationUsing().equals( "1" ) )
               {
                  if ( index_1 == 0 )
                  {
                     probationSalary = probationSalary + Integer.parseInt( employeeContractSalaryVO.getBase() );
                     index_1++;
                  }
                  hasProbationUsing = false;
               }
               else
               {
                  if ( index_2 == 0 )
                  {
                     baseSalary = baseSalary + Integer.parseInt( employeeContractSalaryVO.getBase() );
                     index_2++;
                  }
               }
            }

            // ���ʵ�����Ŀ�ۼ�
            if ( employeeContractSalaryVO.getItemId().equals( "2" ) )
            {
               if ( employeeContractSalaryVO.getProbationUsing().equals( "1" ) )
               {
                  probationSalary = probationSalary + Integer.parseInt( employeeContractSalaryVO.getBase() );
                  hasProbationUsing = false;
               }
               else
               {
                  baseSalary = baseSalary + Integer.parseInt( employeeContractSalaryVO.getBase() );
               }
            }
         }

         if ( hasProbationUsing )
         {
            probationSalary = baseSalary;
         }

         if ( null != employeeContractVO.getProbationEndDate() && !"0".equals( employeeContractVO.getProbationEndDate() ) )
         {
            //            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            //            {
            probationMonth = employeeContractVO.getProbationEndDate();
            //            }
            //            else
            //            {
            //               probationMonth = employeeContractVO.getProbationEndDate();
            //            }
         }
         else
         {
            if ( clientOrderHeaderVO.getProbationMonth().equals( "0" ) )
            {
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  probationMonth = "��������";
               }
               else
               {
                  probationMonth = "None";
               }
            }
            else
            {
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  probationMonth = clientOrderHeaderVO.getProbationMonth() + "����";
               }
               else
               {
                  probationMonth = clientOrderHeaderVO.getProbationMonth() + " month(s)";
               }
            }
         }

         if ( locationVO != null && getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {

               workAddress = locationVO.getAddressZH();
            }
            else
            {
               workAddress = locationVO.getAddressEN();
            }
         }
         else
         {
            workAddress = clientVO.getAddress();
         }

         employeeContractPDFVO.setBaseSalary( employeeContractPDFVO.formatNumber( KANUtil.filterEmpty( baseSalary ) ) );
         employeeContractPDFVO.setProbationSalary( employeeContractPDFVO.formatNumber( KANUtil.filterEmpty( probationSalary ) ) );
         employeeContractPDFVO.setProbationMonths( KANUtil.filterEmpty( probationMonth ) );
         employeeContractPDFVO.setWorkAddress( workAddress );
         employeeContractPDFVO.setMonthlySalary( employeeContractPDFVO.getBaseSalary() );
         if ( StringUtils.isNotEmpty( employeeContractPDFVO.getMonthlySalary() ) )
         {
            employeeContractPDFVO.setAnnualSalary( employeeContractPDFVO.formatNumber( new BigDecimal( employeeContractPDFVO.getMonthlySalary() ).multiply( BigDecimal.valueOf( 12 ) ).toString() ) );
         }
         employeeContractPDFVO.setPositionName( employeeContractVO.getAdditionalPosition() );
         // ��ʼ���ļ���
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = resignTemplateVO.getNameZH() + "(" + employeeVO.getNameZH() + ")" + fileName;
            employeeContractPDFVO.setCompanyName( entityVO.getNameZH() );
         }
         else
         {
            fileName = resignTemplateVO.getNameEN() + "(" + employeeVO.getNameEN() + ")" + fileName;
            employeeContractPDFVO.setCompanyName( entityVO.getNameEN() );
         }
         List< ConstantVO > constantVOList = accountConstants.getConstantVOsByScopeType( "5" );
         final List< Object > objects = new ArrayList< Object >();
         objects.add( employeeVO );
         objects.add( entityVO );
         objects.add( employeeContractVO );
         objects.add( employeeContractPDFVO );
         // logoFile���Ȱ��շ���û�а���client
         String logoFile = "";
         if ( entityVO != null )
         {
            logoFile = entityVO.getLogoFile();
            if ( KANUtil.filterEmpty( logoFile ) != null && logoFile.contains( "##" ) )
            {
               logoFile = logoFile.split( "##" )[ 0 ];
            }
            //            if ( KANUtil.filterEmpty( logoFile ) == null )
            //            {
            //               logoFile = accountConstants.initClientLogoFile( logoFile, "0", entityVO.getCorpId() );
            //            }
         }
         if ( KANUtil.filterEmpty( logoFile ) == null )
         {
            logoFile = KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) ? accountConstants.getClientLogoFileByCorpId( BaseAction.getCorpId( request, response ) )
                  : accountConstants.OPTIONS_LOGO_FILE;
         }

         String htmlContent = MatchUtil.generateContent( resignTemplateVO.getContent(), constantVOList, objects, request, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE, logoFile );

         new DownloadFileAction().download( response, PDFTool.generationPdfDzOrder( htmlContent ), fileName );

         //         ByteArrayOutputStream baos = HTMLParseUtil.htmlParsePDF( htmlContent, employeeContractVO.getContractId(), logoFile );
         //         new DownloadFileAction().download( response, baos, fileName );

      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "�к�" ) )
         {
            error( request, null, e.getMessage() );
         }
         else
         {
            throw new KANException( e );
         }
      }
      return null;
   }

   public ActionForward export_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final String resignTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );

         final ResignTemplateService resignTemplateService = ( ResignTemplateService ) getService( "resignTemplateService" );
         final ResignTemplateVO resignTemplateVO = resignTemplateService.getResignTemplateVOByResignTemplateId( resignTemplateId );

         // ��ʼ���ļ���
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = resignTemplateVO.getNameZH() + fileName;
         }
         else
         {
            fileName = resignTemplateVO.getNameEN() + fileName;
         }

         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         String logo = KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) ? accountConstants.getClientLogoFileByCorpId( BaseAction.getCorpId( request, response ) )
               : accountConstants.OPTIONS_LOGO_FILE;
         new DownloadFileAction().download( response, PDFTool.generationPdfDzOrder( this.generateContent( resignTemplateVO.getContent(), logo ) ), fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "�к�" ) )
         {
            error( request, null, e.getMessage() );
            return mapping.findForward( "manageLaborContractTemplate" );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax����
      return mapping.findForward( "" );
   }

   public void checkHTML( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         String returnVlue = "success";
         String content = request.getParameter( "templateContent" );
         try
         {
            if ( StringUtils.isNotEmpty( content ) )
            {
               content = URLDecoder.decode( content, "utf-8" );
               HTMLParseUtil.checkHtmlStyle( content );
            }
         }
         catch ( Exception e )
         {
            if ( StringUtils.contains( e.getMessage(), "�к�" ) )
            {
               returnVlue = e.getMessage();
            }
            else
            {
               returnVlue = "ģ�����ò�����HTML���������ϸ���ģ�������趨!";
            }
         }

         final JSONObject jsonObject = new JSONObject();
         jsonObject.put( "data", returnVlue );
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   private String generateContent( final String replaceContent, final String logo ) throws KANException
   {
      String content = replaceContent;
      if ( StringUtils.isNotEmpty( content ) )
      {
         if ( content.contains( "&ldquo;" ) )
         {
            content = content.replaceAll( "&ldquo;", "\"" );
         }
         if ( content.contains( "&rdquo;" ) )
         {
            content = content.replaceAll( "&rdquo;", "\"" );
         }
         if ( content.contains( "&quot;" ) )
         {
            content = content.replaceAll( "&quot;", "\"" );
         }
         if ( content.contains( "&#39;" ) )
         {
            content = content.replaceAll( "&#39;", "\'" );
         }
         if ( content.contains( "&lt;" ) )
         {
            content = content.replaceAll( "&lt;", "<" );
         }
         if ( content.contains( "&gt;" ) )
         {
            content = content.replaceAll( "&gt;", ">" );
         }
         while ( content.contains( "${" ) )
         {
            int begin = content.indexOf( "${" );
            int end = content.indexOf( "}", begin );
            String replaceString = content.substring( begin, end + 1 );
            content = content.replace( replaceString, STRING_BLANK );
         }
      }
      String logoString = "";
      if ( StringUtils.isNotBlank( logo ) )
      {
         logoString = "<img width='160px' src=\"" + KANUtil.basePath + "/" + logo + "\" border=\"0\"/>";
      }
      content = "<html class='has-js' lang='en'><head><meta http-equiv='content-type' content='text/html; charset=" + KANConstants.WKHTMLTOPDF_HTML_CHARSET + "'></head><body>"
            + logoString + "<div style='margin:auto;width:794px'><div style='padding-left:40px;padding-right:40px;'>" + content;
      content += "</div></div>";
      return content;
   }
}
