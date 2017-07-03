package com.kan.base.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.UploadFileAction;
import com.kan.hro.dao.inf.biz.performance.PerformanceDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.performance.PerformanceVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.performance.PerformanceService;

public class ExcelImport4Performance
{
   /**
    * @param localFileString
    * @param request
    * @param importHeaderId
    * @param remark2
    * @throws Exception
    */
   public static void importDB( final String localFileString, final HttpServletRequest request ) throws Exception
   {
      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );

      // excelת����
      final List< PerformanceVO > performanceVOs = new ArrayList< PerformanceVO >();

      final List< String > errorMessageList = new ArrayList< String >();

      int successCount = 0;

      new ReadXlsxHander()
      {

         @Override
         public void optRows( int sheetIndex, int curRow, final List< CellData > row ) throws SQLException
         {
            // Ĭ��ֻ�����һ��Sheet��Ӧ����
            try
            {
               if ( sheetIndex == 0 )
               {
                  if ( curRow >= 13 )
                  {
                     // ����row��ȡperformance
                     final PerformanceVO performanceVO = getPerformanceVOByRow( accountId, corpId, row, errorMessageList, curRow );
                     if ( performanceVO != null )
                     {
                        //                        if ( KANUtil.filterEmpty( performanceVO.getYearPerformanceRating() ) != null )
                        //                        {
                        performanceVO.setAccountId( accountId );
                        performanceVO.setCorpId( corpId );
                        performanceVO.setCreateBy( performanceVO.decodeUserId( BaseAction.getUserId( request, null ) ) );
                        performanceVO.setCreateDate( new Date() );
                        performanceVO.setModifyDate( new Date() );
                        performanceVO.setModifyBy( BaseAction.getUserId( request, null ) );
                        performanceVOs.add( performanceVO );
                        //                        }
                        //                        else
                        //                        {
                        //                           errorMessageList.add( "��" + ( curRow + 1 ) + "�е���ʧ��,û�м�Ч����" );
                        //                        }
                     }
                     else
                     {
                        errorMessageList.add( "��" + ( curRow + 1 ) + "�е���ʧ��,�Ҳ�����Ա����Ϣ" );
                     }
                  }
               }
            }
            catch ( final Exception e )
            {
               e.printStackTrace();
            }
         }

      }.process( localFileString );

      final PerformanceService performanceService = ( PerformanceService ) ServiceLocator.getService( "performanceService" );

      for ( PerformanceVO performanceVO : performanceVOs )
      {
         int count = 0;
         PerformanceVO performanceDB = null;
         if ( ( performanceDB = existPerformance( performanceVO ) ) != null )
         {
            // ������޸��򲻹�status  ͬʱû�����ɹ���н����ְ
            if ( "1".equals( performanceDB.getStatus() ) )
            {
               count = performanceService.updatePerformance( performanceDB, performanceVO );
            }

         }
         else
         {
            // 1 δ��н,2�ѵ�н,4��ְ
            performanceVO.setStatus( "1" );
            count = performanceService.insertPerformance( performanceVO );
         }
         if ( count > 0 )
         {
            successCount++;
         }
         else
         {
            errorMessageList.add( 0, "employeeId=" + performanceVO.getEmployeeId() + "�е���ʧ��.(�ѵ�н��ְλ��Ǩ)" );
         }
      }

      if ( errorMessageList.size() > 0 )
      {
         String errorMsgs = "\n";
         for ( String errorMsg : errorMessageList )
         {
            errorMsgs += errorMsg + "\n";
         }
         UploadFileAction.setStatusMsg( request, "3", errorMsgs, "2" );
      }
      UploadFileAction.setStatusMsg( request, "4", "��" + ( successCount + errorMessageList.size() ) + "��," + successCount + "������ɹ���" );
   }

   /**
    * ����Ѿ������˼�Ч.���޸�
    * @param performanceVO
    * @return
    */
   private static PerformanceVO existPerformance( PerformanceVO performanceVO )
   {
      PerformanceVO condVO = new PerformanceVO();
      PerformanceVO targetVO = null;
      condVO.setEmployeeId( performanceVO.getEmployeeId() );
      condVO.setYearly( performanceVO.getYearly() );
      condVO.setAccountId( performanceVO.getAccountId() );
      condVO.setCorpId( performanceVO.getCorpId() );
      PerformanceDao performanceDao = ( PerformanceDao ) ServiceLocator.getService( "performanceDao" );
      try
      {
         final List< Object > tmpObjects = performanceDao.getPerformanceVOsByCondition( condVO );
         if ( tmpObjects.size() > 0 )
         {
            targetVO = ( PerformanceVO ) tmpObjects.get( 0 );
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         System.err.println( "��ѯ�Ƿ���ڼ�Ч����excelImport4Performance.existPerformance()" );
         return null;
      }
      return targetVO;
   }

   protected static PerformanceVO getPerformanceVOByRow( String accountId, String corpId, List< CellData > row, List< String > errorMessageList, int curRow )
   {
      final String employeeId = row.get( 0 ).getValue();
      PerformanceVO performanceVO = null;
      // checkEmployeeId,����Id
      if ( checkEmployee( employeeId ) )
      {
         performanceVO = new PerformanceVO();
         performanceVO.setEmployeeId( employeeId );
         ///////////
         performanceVO.setYearly( row.get( 1 ).getValue() );
         performanceVO.setFullName( row.get( 2 ).getValue() );
         performanceVO.setShortName( row.get( 3 ).getValue() );
         performanceVO.setChineseName( row.get( 4 ).getValue() );
         performanceVO.setEmploymentEntityEN( row.get( 5 ).getValue() );
         performanceVO.setEmploymentEntityZH( row.get( 6 ).getValue() );
         performanceVO.setCompanyInitial( row.get( 7 ).getValue() );
         performanceVO.setBuFunctionEN( row.get( 8 ).getValue() );
         performanceVO.setBuFunctionZH( row.get( 9 ).getValue() );
         performanceVO.setDepartmentEN( row.get( 10 ).getValue() );
         performanceVO.setDepartmentZH( row.get( 11 ).getValue() );
         performanceVO.setCostCenter( row.get( 12 ).getValue() );
         performanceVO.setFunctionCode( row.get( 13 ).getValue() );
         performanceVO.setLocation( row.get( 14 ).getValue() );
         performanceVO.setJobRole( row.get( 15 ).getValue() );
         performanceVO.setPositionEN( row.get( 16 ).getValue() );
         performanceVO.setPositionZH( row.get( 17 ).getValue() );
         performanceVO.setJobGrade( row.get( 18 ).getValue() );
         performanceVO.setInternalTitle( row.get( 19 ).getValue() );
         performanceVO.setLineBizManager( row.get( 20 ).getValue() );
         performanceVO.setLineHRManager( row.get( 21 ).getValue() );
         performanceVO.setSeniorityDate( row.get( 22 ).getValue() );
         performanceVO.setEmploymentDate( row.get( 23 ).getValue() );
         performanceVO.setShareOptions( row.get( 24 ).getValue() );
         performanceVO.setLastYearPerformanceRating( row.get( 25 ).getValue() );
         performanceVO.setLastYearPerformancePromotion( row.get( 26 ).getValue() );
         performanceVO.setMidYearPromotion( row.get( 27 ).getValue() );
         performanceVO.setMidYearSalaryIncrease( row.get( 28 ).getValue() );
         performanceVO.setCurrencyCode( row.get( 29 ).getValue() );
         performanceVO.setBaseSalaryLocal( row.get( 30 ).getValue() );
         performanceVO.setBaseSalaryUSD( row.get( 31 ).getValue() );
         performanceVO.setAnnualBaseSalaryLocal( row.get( 32 ).getValue() );
         performanceVO.setAnnualBaseSalaryUSD( row.get( 33 ).getValue() );
         performanceVO.setHousingAllowanceLocal( row.get( 34 ).getValue() );
         performanceVO.setChildrenEduAllowanceLocal( row.get( 35 ).getValue() );
         performanceVO.setGuaranteedCashLocal( row.get( 36 ).getValue() );
         performanceVO.setGuaranteedCashUSD( row.get( 37 ).getValue() );
         performanceVO.setMonthlyTarget( row.get( 38 ).getValue() );
         performanceVO.setQuarterlyTarget( row.get( 39 ).getValue() );
         performanceVO.setGpTarget( row.get( 40 ).getValue() );
         performanceVO.setTargetValueLocal( row.get( 41 ).getValue() );
         performanceVO.setTargetValueUSD( row.get( 42 ).getValue() );
         performanceVO.setTtcLocal( row.get( 43 ).getValue() );
         performanceVO.setTtcUSD( row.get( 44 ).getValue() );
         performanceVO.setYearPerformanceRating( row.get( 45 ).getValue() );
         performanceVO.setYearPerformancePromotion( row.get( 46 ).getValue() );
         performanceVO.setRecommendTTCIncrease( row.get( 47 ).getValue() );
         performanceVO.setTtcIncrease( row.get( 48 ).getValue() );
         performanceVO.setNewTTCLocal( row.get( 49 ).getValue() );
         performanceVO.setNewTTCUSD( row.get( 50 ).getValue() );
         performanceVO.setNewBaseSalaryLocal( row.get( 51 ).getValue() );
         performanceVO.setNewBaseSalaryUSD( row.get( 52 ).getValue() );
         performanceVO.setNewAnnualSalaryLocal( row.get( 53 ).getValue() );
         performanceVO.setNewAnnualSalaryUSD( row.get( 54 ).getValue() );
         performanceVO.setNewAnnualHousingAllowance( row.get( 55 ).getValue() );
         performanceVO.setNewAnnualChildrenEduAllowance( row.get( 56 ).getValue() );
         performanceVO.setNewAnnualGuaranteedAllowanceLocal( row.get( 57 ).getValue() );
         performanceVO.setNewAnnualGuatanteedAllowanceUSD( row.get( 58 ).getValue() );
         performanceVO.setNewMonthlyTarget( row.get( 59 ).getValue() );
         performanceVO.setNewQuarterlyTarget( row.get( 60 ).getValue() );
         performanceVO.setNewGPTarget( "YES".equalsIgnoreCase( row.get( 61 ).getValue() ) ? "0.00" : row.get( 61 ).getValue() );
         performanceVO.setYesNewGPTarget( "YES".equalsIgnoreCase( row.get( 61 ).getValue() ) ? 1 : 2 );
         performanceVO.setNewTargetValueLocal( row.get( 62 ).getValue() );
         performanceVO.setNewTargetValueUSD( row.get( 63 ).getValue() );
         performanceVO.setNewJobGrade( row.get( 64 ).getValue() );
         performanceVO.setNewInternalTitle( getInternalTitleByJobGrade( accountId, corpId, row.get( 64 ).getValue() ) );
         performanceVO.setNewPositionEN( row.get( 66 ).getValue() );
         performanceVO.setNewPositionZH( row.get( 67 ).getValue() );
         performanceVO.setNewShareOptions( row.get( 68 ).getValue() );
         performanceVO.setTargetBonus( row.get( 69 ).getValue() );
         performanceVO.setProposedBonus( row.get( 70 ).getValue() );
         //71����Ҫ��, �������ս�80�����
         performanceVO.setProposedPayoutLocal( row.get( 72 ).getValue() );
         performanceVO.setProposedPayoutUSD( row.get( 73 ).getValue() );
         performanceVO.setDescription( row.get( 74 ).getValue() );
         if ( row.size() > 79 )
            performanceVO.setYearEndBonus( row.get( 79 ).getValue() );
         if ( row.size() > 80 )
            performanceVO.setRemark2( row.get( 80 ).getValue() );
         if ( row.size() > 81 )
            performanceVO.setRemark3( row.get( 81 ).getValue() );
         if ( row.size() > 82 )
            performanceVO.setRemark4( row.get( 82 ).getValue() );
      }
      return performanceVO;
   }

   private static String getInternalTitleByJobGrade( String accountId, String corpId, String gradeName )
   {
      String internalTitle = "";
      PositionGradeVO gradeVO = KANConstants.getKANAccountConstants( accountId ).getPositionGradeVOByNameSplit( gradeName, corpId );
      if ( gradeVO != null )
      {
         internalTitle = gradeVO.getGradeNameZH();
      }
      return internalTitle;
   }

   private static boolean checkEmployee( String employeeId )
   {
      final EmployeeService employeeService = ( EmployeeService ) ServiceLocator.getService( "employeeService" );
      try
      {
         final EmployeeVO tmpVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         return tmpVO != null;
      }
      catch ( KANException e )
      {
         e.printStackTrace();
         System.err.println( "����checkEmploye����(��Ҫ����)" );
      }
      return false;
   }

}
