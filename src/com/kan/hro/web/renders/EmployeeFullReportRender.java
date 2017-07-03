package com.kan.hro.web.renders;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.service.impl.biz.performance.PerformanceServiceImpl;
import com.kan.hro.web.actions.biz.employee.EmployeeContractAction;

public class EmployeeFullReportRender
{

   public static String generateEmployeeFullReportPartTHeader( HttpServletRequest request ) throws KANException
   {
      final StringBuffer sb = new StringBuffer();

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( EmployeeContractAction.ACCESS_ACTION_SERVICE_IN_HOUSE );

      if ( tableDTO != null )
      {
         final ColumnGroupDTO performanceGroupDTO = tableDTO.getColumnGroupDTOByGroupId( PerformanceServiceImpl.PERFORMANCE_DEFINE_GROUP_ID );
         if ( performanceGroupDTO != null )
         {
            for ( ColumnVO tempColumnVO : performanceGroupDTO.getColumnVOs() )
            {
               if ( KANUtil.filterEmpty( tempColumnVO.getNameDB() ) != null && tempColumnVO.getNameDB().startsWith( PerformanceServiceImpl.COLUMN_NAME_DB_PREFIX ) )
               {
                  sb.append( "<th class=\"header-nosort\">" );
                  sb.append( "<span id='dynaColumns." + tempColumnVO.getNameDB() + "'>" );
                  sb.append( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? tempColumnVO.getNameZH() : tempColumnVO.getNameEN() );
                  sb.append( "</span>&nbsp;&nbsp;</th>" );
               }
            }
         }
      }

      return sb.toString();
   }

   public static String generateEmployeeFullReportPartTBody( HttpServletRequest request ) throws KANException
   {
      final StringBuffer sb = new StringBuffer();

      final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) request.getAttribute( "defineEmployeeReportVO" );

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( EmployeeContractAction.ACCESS_ACTION_SERVICE_IN_HOUSE );

      if ( tableDTO != null )
      {
         final ColumnGroupDTO performanceGroupDTO = tableDTO.getColumnGroupDTOByGroupId( PerformanceServiceImpl.PERFORMANCE_DEFINE_GROUP_ID );
         if ( performanceGroupDTO != null )
         {
            for ( ColumnVO tempColumnVO : performanceGroupDTO.getColumnVOs() )
            {
               if ( KANUtil.filterEmpty( tempColumnVO.getNameDB() ) != null && tempColumnVO.getNameDB().startsWith( PerformanceServiceImpl.COLUMN_NAME_DB_PREFIX ) )
               {
                  sb.append( "<td class='left'>" );
                  sb.append( employeeReportVO.getDynaColumns().get( tempColumnVO.getNameDB() ) == null ? "" : employeeReportVO.getDynaColumns().get( tempColumnVO.getNameDB() ) );
                  sb.append( "</td>" );
               }
            }
         }
      }

      return sb.toString();
   }
}
