package com.kan.hro.web.actions.biz.performance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import net.sf.json.JSONObject;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.DocUtils;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.ZipUtils;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.domain.biz.performance.PerformanceVO;
import com.kan.hro.service.impl.biz.performance.PerformanceServiceImpl;
import com.kan.hro.service.inf.biz.employee.EmployeeReportService;
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;
import com.kan.hro.service.inf.biz.performance.PerformanceService;

public class PerformanceAction extends BaseAction
{

   // Action 标识
   public static final String ACCESS_ACTION = "HRO_PM_YERR";

   /***
    * 导出Zip - exportZip
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return message
    * @throws KANException
    * @throws IOException
    */
   public ActionForward exportZip( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException, IOException
   {
      final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
      final PerformanceVO performanceVO = ( PerformanceVO ) form;
      if ( KANUtil.filterEmpty( performanceVO.getYearly() ) == null )
         performanceVO.setYearly( KANUtil.formatDate( new Date(), "yyyy" ) );
      setDataAuth( request, response, performanceVO );
      decodedObject( performanceVO );
      List< Object > list = performanceService.getPerformanceVOsByCondition( performanceVO );

      String x = File.separator;
      String zipName = "finalzip.zip";
      String tempFileName = FileSystemView.getFileSystemView().getHomeDirectory().toString() + x + "hristemp" + x + KANUtil.formatDate( new Date(), "yyMMddHHmmss" );
      File tempFile = new File( tempFileName );

      if ( !tempFile.exists() )
      {
         tempFile.mkdirs();
      }

      final File finalZipFile = new File( tempFileName + x + zipName );
      final DocUtils docFactory = new DocUtils();
      final boolean lang_zh = "zh".equalsIgnoreCase( request.getLocale().getLanguage() );
      final SimpleDateFormat sdf_cn = new SimpleDateFormat( "yyyy年 MM 月 dd 日" );
      final SimpleDateFormat sdf_en = new SimpleDateFormat( "MMM-dd, yyyy", Locale.ENGLISH );
      final DecimalFormat df = new DecimalFormat( "#,##0.00" );
      Map< String, Object > dataMap = new HashMap< String, Object >();
      dataMap.put( "now_en", sdf_en.format( new Date() ) );
      dataMap.put( "now_zh", sdf_cn.format( new Date() ) );
      for ( Object o : list )
      {
         PerformanceVO thisPerformanceVO = ( PerformanceVO ) o;
         
         if ( KANUtil.filterEmpty( thisPerformanceVO.getYearPerformanceRating() ) == null )
             continue;
         
         String thisYear = thisPerformanceVO.getYearly();
         String lastYear = String.valueOf( Integer.valueOf( thisYear ) + 1 );
         Date effectiveDate = KANUtil.createDate( lastYear + "-01-01" );
         if ( !"1".equals( thisPerformanceVO.getStatus() ) )
         {
            double before_targetIncentive = 0;
            double after_targetIncentive = 0;
            boolean before_gp_yes = false;
            boolean after_gp_yes = false;
            
            if( thisPerformanceVO.getYesGPTarget() == 1 )
            {
                before_gp_yes = true;
            }
            
            if( thisPerformanceVO.getYesNewGPTarget() == 1 )
            {
                after_gp_yes = true;
            }
            
            if( Double.valueOf( thisPerformanceVO.getMonthlyTarget() ) != 0 )
            {
                before_targetIncentive = Double.valueOf( thisPerformanceVO.getMonthlyTarget() );
            }
            
            if( Double.valueOf( thisPerformanceVO.getQuarterlyTarget() ) != 0 )
            {
                before_targetIncentive = Double.valueOf( thisPerformanceVO.getQuarterlyTarget() );
            }
            
            if( Double.valueOf( thisPerformanceVO.getGpTarget() ) != 0 )
            {
                before_targetIncentive = Double.valueOf( thisPerformanceVO.getGpTarget() );
            }
            
            if( Double.valueOf( thisPerformanceVO.getNewMonthlyTarget() ) != 0 )
            {
                after_targetIncentive = Double.valueOf( thisPerformanceVO.getNewMonthlyTarget() );
            }
            
            if( Double.valueOf( thisPerformanceVO.getNewQuarterlyTarget() ) != 0 )
            {
                after_targetIncentive = Double.valueOf( thisPerformanceVO.getNewQuarterlyTarget() );
            }
            
            if( Double.valueOf( thisPerformanceVO.getNewGPTarget() ) != 0 )
            {
                after_targetIncentive = Double.valueOf( thisPerformanceVO.getNewGPTarget() );
            }
            
            
            dataMap.put( "thisYear", thisYear );
            dataMap.put( "lastYear", lastYear );
            dataMap.put( "employeeNameZH", thisPerformanceVO.getChineseName() );
            dataMap.put( "employeeNameEN", thisPerformanceVO.getShortName() );
            dataMap.put( "salaryEffectiveDateZH", sdf_cn.format( effectiveDate ) );
            dataMap.put( "salaryEffectiveDateEN", sdf_en.format( effectiveDate ) );
            String before_positionTitleZH = lang_zh ? ( thisPerformanceVO.getPositionZH() == null ? "" : thisPerformanceVO.getPositionZH() )
                : ( thisPerformanceVO.getPositionEN() == null ? "" : thisPerformanceVO.getPositionEN() );
            String after_positionTitleZH = lang_zh ? ( thisPerformanceVO.getNewPositionZH() == null ? "" : thisPerformanceVO.getNewPositionZH() )
                : ( thisPerformanceVO.getNewPositionEN() == null ? "" : thisPerformanceVO.getNewPositionEN() ); 
            dataMap.put( "before_positionTitleZH", before_positionTitleZH );
            dataMap.put( "after_positionTitleZH", "".equals( after_positionTitleZH ) ? before_positionTitleZH : after_positionTitleZH );
            String before_jobGrade = thisPerformanceVO.getJobGrade();
            String after_jobGrade = thisPerformanceVO.getNewInternalTitle() == null ? "" : thisPerformanceVO.getNewInternalTitle();
            dataMap.put( "before_jobGrade", before_jobGrade );
            dataMap.put( "after_jobGrade", "".equals( after_jobGrade ) ? before_jobGrade : after_jobGrade );
            dataMap.put( "before_annualBaseSalary", df.format( Double.valueOf( thisPerformanceVO.getAnnualBaseSalaryLocal() ) ) );
            dataMap.put( "after_annualBaseSalary", df.format( Double.valueOf( thisPerformanceVO.getNewAnnualSalaryLocal() ) ) );
            dataMap.put( "before_yearEndBonus", df.format( Double.valueOf( thisPerformanceVO.getCurrentYearEndBonus() ) ) );
            dataMap.put( "after_yearEndBonus", df.format( Double.valueOf( thisPerformanceVO.getYearEndBonus() ) ) );
            dataMap.put( "actual_yearEndBonus", df.format( Double.valueOf( thisPerformanceVO.formatNumber_2( thisPerformanceVO.getProposedPayoutLocal() ) ) ) );
            dataMap.put( "entityNameZH", thisPerformanceVO.getEmploymentEntityZH() );
            dataMap.put( "entityNameEN", thisPerformanceVO.getEmploymentEntityEN() );
            dataMap.put( "before_targetIncentive", before_gp_yes ? "YES" : df.format( before_targetIncentive ) );
            dataMap.put( "after_targetIncentive", after_gp_yes ? "YES" : df.format( after_targetIncentive ) );
            

            docFactory.createWord( dataMap, tempFileName + x + thisPerformanceVO.getShortName().trim() + ".doc", lang_zh );
         }
      }

      ZipUtils.zip( tempFileName, finalZipFile );

      OutputStream os = null;
      BufferedInputStream bis = null;

      try
      {
         response.setContentType( "application/zip" );
         response.addHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( zipName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         os = response.getOutputStream();
         bis = new BufferedInputStream( new FileInputStream( finalZipFile ) );
         byte[] b = new byte[ 1024 ];
         int i = 0;

         while ( ( i = bis.read( b ) ) > 0 )
         {
            os.write( b, 0, i );
         }
         bis.close();
         os.flush();
      }
      catch ( Exception e )
      {
         //log.error("", e);
      }
      finally
      {
         if ( bis != null )
         {
            try
            {
               bis.close();
               bis = null;
            }
            catch ( IOException e )
            {
               e.printStackTrace();
            }
         }
         if ( os != null )
         {
            try
            {
               os.close();
               os = null;
            }
            catch ( IOException e )
            {
               e.printStackTrace();
            }
         }
      }

      if ( KANUtil.deleteDir( tempFile ) )
      {
         tempFile.delete();
      }

      return mapping.findForward( "" );
   }

   /***
    * 一键发送调薪通知信 - sendAdjustmentSalaryNoticeLetter
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return message
    * @throws KANException
    * @throws IOException
    */
   public ActionForward sendAdjustmentSalaryNoticeLetter( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException,
         IOException
   {
      final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
      final PerformanceVO performanceVO = ( PerformanceVO ) form;
      if ( KANUtil.filterEmpty( performanceVO.getYearly() ) == null )
         performanceVO.setYearly( KANUtil.formatDate( new Date(), "yyyy" ) );
      setDataAuth( request, response, performanceVO );
      decodedObject( performanceVO );
      List< Object > list = performanceService.getPerformanceVOsByCondition( performanceVO );
      int rows = performanceService.sendAdjustmentSalaryNoticeLetter( list, getUserId( request, response ) );

      // Config the response
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      final PrintWriter out = response.getWriter();
      // response message
      out.print( "发送通知信成功！总共 " + list.size() + " 条数据，产生 " + rows + " 条通知信！" );
      out.flush();
      out.close();
      return mapping.findForward( "" );
   }

   /***
    * 确认最终评分 - confirmFinalRating
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return message
    * @throws KANException
    * @throws IOException
    */
   public ActionForward confirmFinalRating( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException, IOException
   {
      final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
      final PerformanceVO performanceVO = ( PerformanceVO ) form;
      performanceVO.setYearly( request.getParameter("yearly") );
      setDataAuth( request, response, performanceVO );
      decodedObject( performanceVO );
      List< Object > list = performanceService.getPerformanceVOsByCondition( performanceVO );
      int rows = performanceService.confirmFinalRating( list, getUserId( request, response ) );

      // Config the response
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      final PrintWriter out = response.getWriter();
      // response message
      final boolean lang_zh = "zh".equalsIgnoreCase( request.getLocale().getLanguage() );
      if(lang_zh){
        out.print( "确认最终评分成功！总共 " + list.size() + " 条，成功 " + rows + " 条！" );
      }else{
        out.print( "Confirm final rating success！Total " + list.size() + " row(s)，success " + rows + " row(s)！" );
      }
      out.flush();
      out.close();
      return mapping.findForward( "" );
   }

   /**
    * 确认调薪
    * @return
    * @throws IOException 
    */
   public ActionForward confirmAdjust( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException, IOException
   {
      // Config the response
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      final PrintWriter out = response.getWriter();
      final String yearly = request.getParameter( "yearly" );
      final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
      final PerformanceVO condVO = new PerformanceVO();
      condVO.setLocale( request.getLocale() );
      condVO.setIp( getIPAddress( request ) );
      condVO.setModifyBy( getUserId( request, null ) );
      condVO.setYearly( yearly );
      condVO.setStatus( "1" );
      condVO.setAccountId( getAccountId( request, response ) );
      condVO.setCorpId( getCorpId( request, response ) );
      // condVO.setTmpYearPerformanceRating( "0" );
      int count = performanceService.confirmPerformanceInfo( condVO, request );
      out.print( count );
      out.flush();
      out.close();
      return null;
   }

   /**
    * 获取调薪人数
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    * @throws IOException 
    */
   public ActionForward getAdjustmentCount( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
   {
      // Config the response
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "UTF-8" );
      final PrintWriter out = response.getWriter();
      final String yearly = request.getParameter( "yearly" );
      final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );

      final PerformanceVO performanceVO = ( PerformanceVO ) form;
      performanceVO.setAccountId( getAccountId( request, response ) );
      performanceVO.setCorpId( getCorpId( request, response ) );
      performanceVO.setYearly( yearly );
      performanceVO.setStatus( "1" );
      // performanceVO.setTmpYearPerformanceRating( "0" );
      setDataAuth( request, response, performanceVO );
      final PagedListHolder pagedListHolder = new PagedListHolder();
      pagedListHolder.setObject( performanceVO );
      performanceService.getPerformanceVOsByCondition( pagedListHolder, false );

      int salaryAdjustmentSize = 0;
      int positionChangeSize = 0;
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         salaryAdjustmentSize = pagedListHolder.getSource().size();
         for ( Object o : pagedListHolder.getSource() )
         {
            final PerformanceVO tempPerformanceVO = ( PerformanceVO ) o;
            if ( tempPerformanceVO.isNeedPositionChange() )
               positionChangeSize++;
         }
      }
      final boolean lang_zh = "zh".equalsIgnoreCase( request.getLocale().getLanguage() );
      if(lang_zh){
        out.println( "薪酬调整：" + salaryAdjustmentSize + " 人；\r\n职位异动：" + positionChangeSize + " 人；\r\n确认提交吗？" );
      }else{
        out.println( "Salary Adjustment：" + salaryAdjustmentSize + " employee(s)；\r\nPosition changes：" + positionChangeSize + " employee(s)；\r\nAre you sure to confirm？" );
      }
      out.flush();
      out.close();
      return null;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      String selectBUFunctionOption = "";
      BranchVO branchVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( getBranchId( request, null ) );
      if ( branchVO != null )
      {
         if ( "0".equals( branchVO.getParentBranchId() ) )
         {
            selectBUFunctionOption = branchVO.getBranchId();
         }
         else
         {
            BranchVO parentBranchVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getBranchVOByBranchId( branchVO.getParentBranchId() );
            if ( parentBranchVO != null && parentBranchVO.getParentBranchId().equals( "0" ) )
            {
               selectBUFunctionOption = parentBranchVO.getBranchId();
            }
         }
      }

      request.setAttribute( "selectBUFunctionOption", selectBUFunctionOption );

      PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionVOByPositionId( getPositionId( request, response ) );
      if ( positionVO != null )
      {
         PositionVO parentPositionVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getPositionVOByPositionId( positionVO.getParentPositionId() );
         if ( parentPositionVO != null && "0".equals( parentPositionVO.getParentPositionId() ) )
         {
            boolean is_hr = false;
            List< GroupDTO > groupDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getGroupDTOsByPositionId( getPositionId( request, null ) );
            if ( groupDTOs != null && groupDTOs.size() > 0 )
            {
               for ( GroupDTO dto : groupDTOs )
               {
                  if ( dto.getGroupVO() != null && ( "1".equals( dto.getGroupVO().getDataRole() ) || "2".equals( dto.getGroupVO().getDataRole() ) ) )
                  {
                     is_hr = true;
                     break;
                  }
               }
            }

            if ( !is_hr )
            {
               final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
               final Map< String, Object > mapParameter = new HashMap< String, Object >();
               mapParameter.put( "accountId", getAccountId( request, response ) );
               mapParameter.put( "corpId", getCorpId( request, response ) );
               mapParameter.put( "year", Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) );
               final List< Object > list = budgetSettingHeaderService.getBudgetSettingHeaderVOsByMapParameter( mapParameter );
               if ( list != null && list.size() > 0 )
               {
                  final BudgetSettingHeaderVO object = ( BudgetSettingHeaderVO ) list.get( 0 );
                  Date nowDate = new Date();
                  Date beginDate = null;
                  Date endDate = null;

                  beginDate = KANUtil.createDate( object.getStartDate_bu() );
                  endDate = KANUtil.createDate( object.getEndDate_bu() + " 23:59:59" );
                  if ( !( nowDate.getTime() >= beginDate.getTime() && nowDate.getTime() <= endDate.getTime() ) )
                  {
                     request.setAttribute( "canImport", false );
                  }
               }
            }
         }
      }

      try
      {
         final String page = request.getParameter( "page" );
         final String ajax = request.getParameter( "ajax" );
         final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
         final PerformanceVO performanceVO = ( PerformanceVO ) form;
         if ( KANUtil.filterEmpty( performanceVO.getYearly() ) == null )
            performanceVO.setYearly( KANUtil.formatDate( new Date(), "yyyy" ) );
         setDataAuth( request, response, performanceVO );
         decodedObject( performanceVO );
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setPage( page );
         pagedListHolder.setObject( performanceVO );
         pagedListHolder.setPageSize( listPageSize );
         performanceService.getPerformanceVOsByCondition( pagedListHolder, DOWNLOAD_OBJECTS.equalsIgnoreCase( getSubAction( performanceVO ) ) ? false : true );
         refreshHolder( pagedListHolder, request );
         request.setAttribute( "pagedListHolder", pagedListHolder );
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = performanceService.generatePerformanceReport( pagedListHolder );

               final OutputStream os = response.getOutputStream();
               response.setContentType( "application/x-msdownload" );
               response.setHeader( "Content-Disposition", "attachment;filename=\""
                     + new String( URLDecoder.decode( "Performance Reports " + KANUtil.formatDate( new Date(), "yyyyMMdd hhmmss" ) + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" )
                     + "\"" );
               workbook.write( os );
               os.flush();
               os.close();

               return mapping.findForward( "" );
            }
            else
            {
               request.setAttribute( "accountId", getAccountId( request, null ) );
               return mapping.findForward( "listPerformanceTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listPerformance" );
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

   /**
    * 主管点击同步按钮，将员工基本信息更新到PerformanceVO中
    * @return
    */
   public ActionForward sync_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
         final EmployeeReportService employeeReportService = ( EmployeeReportService ) getService( "employeeReportService" );

         final EmployeeReportVO employeeReportVO = new EmployeeReportVO();
         employeeReportVO.setYearly( KANUtil.formatDate( new Date(), "yyyy" ) );
         employeeReportVO.setAccountId( getAccountId( request, response ) );
         employeeReportVO.setCorpId( getCorpId( request, response ) );
         //setDataAuth( request, response, employeeReportVO );

         PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前值对象
         pagedListHolder.setObject( employeeReportVO );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeReportService.getEmployeePerformanceReportVOsByCondition( pagedListHolder, false );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         final List< PerformanceVO > performanceVOs = new ArrayList< PerformanceVO >();

         if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object o : pagedListHolder.getSource() )
            {
               final EmployeeReportVO tempEmployeeReportVO = ( EmployeeReportVO ) o;

               // 语句join performance 年份为空，插入基本信息
               if ( KANUtil.filterEmpty( tempEmployeeReportVO.getYearly() ) == null )
               {
                  String thisYear = KANUtil.formatDate( new Date(), "yyyy" );
                  final PerformanceVO tempPerformanceVO = new PerformanceVO();
                  tempPerformanceVO.setAccountId( getAccountId( request, response ) );
                  tempPerformanceVO.setCorpId( getCorpId( request, response ) );
                  tempPerformanceVO.setEmployeeId( tempEmployeeReportVO.getEmployeeId() );
                  tempPerformanceVO.setYearly( thisYear );
                  tempPerformanceVO.setFullName( tempEmployeeReportVO.getNameEN() );
                  tempPerformanceVO.setShortName( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jiancheng" ) ) );
                  tempPerformanceVO.setChineseName( tempEmployeeReportVO.getNameZH() );
                  tempPerformanceVO.setEmploymentEntityEN( tempEmployeeReportVO.getEntityNameEN() );
                  tempPerformanceVO.setEmploymentEntityZH( tempEmployeeReportVO.getEntityNameZH() );
                  tempPerformanceVO.setCompanyInitial( tempEmployeeReportVO.getEntityTitle() );
                  tempPerformanceVO.setBuFunctionEN( tempEmployeeReportVO.getParentBranchNameEN() );
                  tempPerformanceVO.setBuFunctionZH( tempEmployeeReportVO.getParentBranchNameZH() );
                  tempPerformanceVO.setDepartmentEN( tempEmployeeReportVO.getBranchNameEN() );
                  tempPerformanceVO.setDepartmentZH( tempEmployeeReportVO.getBranchNameZH() );
                  tempPerformanceVO.setCostCenter( tempEmployeeReportVO.getDecodeSettlementBranch() );
                  tempPerformanceVO.setFunctionCode( tempEmployeeReportVO.getDecodeBusinessType() );
                  tempPerformanceVO.setLocation( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "bangongdidian" ) ) );
                  tempPerformanceVO.setJobRole( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jobrole" ) ) );
                  tempPerformanceVO.setPositionEN( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "zhiweimingchengyingwen" ) ) );
                  tempPerformanceVO.setPositionZH( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "neibuchengwei" ) ) );
                  tempPerformanceVO.setJobGrade( KANUtil.filterEmpty( tempEmployeeReportVO.getDecode_tempPositionGradeIds() ) );
                  tempPerformanceVO.setInternalTitle( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "neibuchengwei" ) ) );
                  tempPerformanceVO.setLineBizManager( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "yewuhuibaoxianjingli" ) ) );
                  tempPerformanceVO.setLineHRManager( KANUtil.filterEmpty( tempEmployeeReportVO.getDecode_tempParentPositionOwners() ) );
                  tempPerformanceVO.setSeniorityDate( KANUtil.filterEmpty( tempEmployeeReportVO.getStartWorkDate() ) );
                  tempPerformanceVO.setEmploymentDate( KANUtil.filterEmpty( tempEmployeeReportVO.getContractStartDate() ) );

                  // TODO
                  //tempPerformanceVO.setShareOptions( shareOptions );
                  if ( "2015".equals( thisYear ) )
                  {
                     tempPerformanceVO.setLastYearPerformanceRating( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jixiaonianfen" ) ) );
                  }
                  else
                  {
                     tempPerformanceVO.setLastYearPerformanceRating( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( PerformanceServiceImpl.COLUMN_NAME_DB_PREFIX
                           + ( Integer.valueOf( thisYear ) - 1 ) ) ) );
                  }

                  //tempPerformanceVO.setLastYearPerformancePromotion( lastYearPerformancePromotion );
                  //tempPerformanceVO.setMidYearPromotion( midYearPromotion );
                  //tempPerformanceVO.setMidYearSalaryIncrease( midYearSalaryIncrease );

                  tempPerformanceVO.setCurrencyCode( tempEmployeeReportVO.getCurrencyCode() );
                  tempPerformanceVO.setBaseSalaryLocal( KANUtil.filterEmpty( tempEmployeeReportVO.getMontnlySalary() ) );
                  tempPerformanceVO.setBaseSalaryUSD( KANUtil.filterEmpty( tempEmployeeReportVO.getUSDMontnlySalary() ) );
                  tempPerformanceVO.setAnnualBaseSalaryLocal( KANUtil.filterEmpty( tempEmployeeReportVO.getAnnualSalary() ) );
                  tempPerformanceVO.setAnnualBaseSalaryUSD( KANUtil.filterEmpty( tempEmployeeReportVO.getUSDAnnualSalary() ) );
                  tempPerformanceVO.setHousingAllowanceLocal( KANUtil.filterEmpty( tempEmployeeReportVO.getHousingAllowance() ) );
                  tempPerformanceVO.setChildrenEduAllowanceLocal( KANUtil.filterEmpty( tempEmployeeReportVO.getChildenEducationAllowance() ) );
                  tempPerformanceVO.setGuaranteedCashLocal( tempEmployeeReportVO.getAnnualFixedIncome() );
                  tempPerformanceVO.setGuaranteedCashUSD( tempEmployeeReportVO.getUSDAnnualFixedIncome() );
                  tempPerformanceVO.setMonthlyTarget( tempEmployeeReportVO.getMonthlyTarget() );
                  tempPerformanceVO.setQuarterlyTarget( tempEmployeeReportVO.getQuarterlyTarget() );
                  tempPerformanceVO.setGpTarget( tempEmployeeReportVO.getBonusRebate() );
                  tempPerformanceVO.setYesGPTarget( tempEmployeeReportVO.getYesGPTarget() );
                  tempPerformanceVO.setTargetValueLocal( tempEmployeeReportVO.getAnnualBonus() );
                  tempPerformanceVO.setTargetValueUSD( tempEmployeeReportVO.getUSDAnnualBonus() );
                  tempPerformanceVO.setTtcLocal( tempEmployeeReportVO.getTTC() );
                  tempPerformanceVO.setTtcUSD( tempEmployeeReportVO.getUSDTTC() );
                  tempPerformanceVO.setCurrentYearEndBonus( tempEmployeeReportVO.getCurrentYearEndBonus() );// 当前年终奖

                  tempPerformanceVO.setCreateBy( getUserId( request, response ) );
                  tempPerformanceVO.setCreateDate( new Date() );
                  tempPerformanceVO.setModifyDate( null );
                  tempPerformanceVO.setStatus( "1" );
                  tempPerformanceVO.setRemark5( tempEmployeeReportVO.getBizEmail() );//用以发邮件调薪通知书给员工
                  performanceVOs.add( tempPerformanceVO );
               }
            }
         }

         int rows = performanceService.syncPerformance( performanceVOs );

         if ( rows > 0 )
         {
             success( request, null, "生成成功，共产生 " + rows + " 条记录！" );
         }
         else
         {
             warning( request, null, "生成失败，没有有效的数据！" );
         }

         System.out.println( "insert datas " + rows + " row." );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
         // 主键获取需解码
         String performanceId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( performanceId ) == null )
         {
            performanceId = ( ( PerformanceVO ) form ).getPerformanceId();
         }
         else
         {
            performanceId = Cryptogram.decodeString( URLDecoder.decode( performanceId, "UTF-8" ) );
         }
         // 获得PerformanceVO对象
         final PerformanceVO performanceVO = performanceService.getPerformanceVOByPerformanceId( performanceId );
         // 区分Add和Update
         performanceVO.setSubAction( VIEW_OBJECT );
         // 刷新国际化
         performanceVO.reset( null, request );
         // 将ItemVO传入request对象
         request.setAttribute( "performanceForm", performanceVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "managePerformance" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
            // 主键获取需解码
            final String performanceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取PerformanceVO对象
            final PerformanceVO performanceVO = performanceService.getPerformanceVOByPerformanceId( performanceId );
            // 装载界面传值
            performanceVO.update( ( PerformanceVO ) form );
            // 获取登录用户
            performanceVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            performanceService.updatePerformance( performanceVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
            // 记录日志
            insertlog( request, performanceVO, Operate.MODIFY, performanceVO.getPerformanceId(), null );
         }
         // 清空Form
         ( ( PerformanceVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
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

   /***
    * Ajax获取TTC增长
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void getTTCIncrease_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         JSONObject result = new JSONObject();
         // 获取参数
         String performanceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         String rating = request.getParameter( "rating" );
         String promotion = request.getParameter( "promotion" );
         // 初始化Service
         final PerformanceService performanceService = ( PerformanceService ) getService( "performanceService" );
         PerformanceVO performanceVO = performanceService.getPerformanceVOByPerformanceId( performanceId );
         performanceVO.setYearPerformanceRating( rating );
         performanceVO.setYearPerformancePromotion( promotion );
         performanceService.calculateNextYearSalaryDetails( performanceVO );
         //返回结果
         result.put( "recommendTTCIncrease", performanceVO.getRecommendTTCIncrease() );//推荐TTC增长比率
         result.put( "ttcIncrease", performanceVO.getTtcIncrease() );
         result.put( "newTTCLocal", performanceVO.getNewTTCLocal() );//TTC增长数额
         result.put( "newTTCUSD", performanceVO.getNewTTCUSD() );//TTC增长数额(USD)
         result.put( "newBaseSalaryLocal", performanceVO.getNewBaseSalaryLocal() );//新基本月薪
         result.put( "newBaseSalaryUSD", performanceVO.getNewBaseSalaryUSD() );//新基本月薪(USD)
         result.put( "newAnnualSalaryLocal", performanceVO.getNewAnnualSalaryLocal() );//新基本年薪
         result.put( "newAnnualSalaryUSD", performanceVO.getNewAnnualSalaryUSD() );//新基本年薪(USD)
         result.put( "newAnnualHousingAllowance", performanceVO.getNewAnnualHousingAllowance() );//新房屋补贴
         result.put( "newAnnualChildrenEduAllowance", performanceVO.getNewAnnualChildrenEduAllowance() );//新子女教育津贴
         result.put( "newAnnualGuaranteedAllowanceLocal", performanceVO.getNewAnnualGuaranteedAllowanceLocal() );//新年总收入
         result.put( "newAnnualGuatanteedAllowanceUSD", performanceVO.getNewAnnualGuatanteedAllowanceUSD() );//新年总收入(USD)
         result.put( "yearEndBonus", performanceVO.getYearEndBonus() );//预算年终奖

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // Send to front
         out.println( result );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
      }
   }
}
