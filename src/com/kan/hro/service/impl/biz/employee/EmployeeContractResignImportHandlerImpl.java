package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class EmployeeContractResignImportHandlerImpl implements ExcelImportHandler< List< CellDataDTO > >
{
   private EmployeeDao employeeDao;

   private EmployeeContractDao employeeContractDao;

   private EmployeeContractSBDao employeeContractSBDao;

   private EmployeeContractCBDao employeeContractCBDao;

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public void setEmployeeContractCBDao( EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   @Override
   public void init( final List< CellDataDTO > importData )
   {

   }

   @Override
   public boolean excuteBeforInsert( final List< CellDataDTO > importDatas, final HttpServletRequest request ) throws KANException
   {
      boolean flag = true;
      try
      {
         if ( importDatas != null && importDatas.size() > 0 )
         {
            for ( CellDataDTO importDataDTO : importDatas )
            {
               if ( importDataDTO != null )
               {
                  // 初始化劳动合同
                  EmployeeContractVO employeeContractVO = null;

                  // 检查劳动合同
                  final CellData tempEmployeeContractCellData = importDataDTO.getCellDataByColumnNameDB( "contractId" );
                  if ( tempEmployeeContractCellData != null )
                  {
                     // 劳动合同ID不能为空
                     if ( KANUtil.filterEmpty( tempEmployeeContractCellData.getValue() ) == null )
                     {
                        flag = false;
                        tempEmployeeContractCellData.setErrorMessange( "第 " + ( tempEmployeeContractCellData.getRow() + 1 ) + " 行，"
                              + tempEmployeeContractCellData.getColumnVO().getNameZH() + "不能为空；" );
                     }
                     else
                     {
                        employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( tempEmployeeContractCellData.getValue() );
                        if ( employeeContractVO == null )
                        {
                           flag = false;
                           tempEmployeeContractCellData.setErrorMessange( "第 " + ( tempEmployeeContractCellData.getRow() + 1 ) + " 行，["
                                 + tempEmployeeContractCellData.getColumnVO().getNameZH() + "][ID: " + tempEmployeeContractCellData.getValue() + "]未找到；" );
                        }
                        // 劳动合同状态是否符合离职
                        else
                        {
                           if ( !"3".equals( employeeContractVO.getStatus() ) && !"5".equals( employeeContractVO.getStatus() ) && !"6".equals( employeeContractVO.getStatus() ) )
                           {
                              flag = false;
                              tempEmployeeContractCellData.setErrorMessange( "第 " + ( tempEmployeeContractCellData.getRow() + 1 ) + " 行，["
                                    + tempEmployeeContractCellData.getColumnVO().getNameZH() + "][ID: " + tempEmployeeContractCellData.getValue() + "]状态不符合条件（应为：“批准”、“已盖章”或“归档”）；" );
                           }
                        }
                     }

                     // 继续验证其他信息
                     if ( flag && employeeContractVO != null )
                     {
                        if ( !continueCheckImportData( importDataDTO, tempEmployeeContractCellData, employeeContractVO ) )
                        {
                           flag = false;
                        }
                        else
                        {
                           // 扩充字段
                           if ( importDataDTO.getCellDataByColumnNameDB( "status" ) == null )
                           {
                              final CellData statusCellData = new CellData();
                              final ColumnVO statusColumnVO = new ColumnVO();
                              statusColumnVO.setNameDB( "status" );
                              statusColumnVO.setIsForeignKey( "2" );
                              statusColumnVO.setAccountId( "1" );
                              statusColumnVO.setIsDBColumn( "1" );
                              statusCellData.setColumnVO( statusColumnVO );
                              statusCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                              statusCellData.setDbValue( "1" );

                              importDataDTO.getCellDatas().add( statusCellData );
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return flag;
   }

   // 继续验证，离职时间，最后工作时间，离职状态，社保、商保信息
   private boolean continueCheckImportData( final CellDataDTO importDataDTO, final CellData employeeContractCellData, final EmployeeContractVO employeeContractVO )
         throws KANException
   {
      // 初始化需修改数据数量和是否
      boolean flag = true;

      final String accountId = importDataDTO.getAccounId();
      final String corpId = importDataDTO.getCorpId();
      final String contractId = employeeContractVO.getContractId();
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );

      // 离职日期不能为空
      final CellData resignDateCellData = importDataDTO.getCellDataByColumnNameDB( "resignDate" );
      if ( resignDateCellData != null && KANUtil.filterEmpty( resignDateCellData.getValue() ) == null )
      {
         flag = false;
         resignDateCellData.setErrorMessange( "第 " + ( resignDateCellData.getRow() + 1 ) + " 行，[" + resignDateCellData.getColumnVO().getNameZH() + "]不能为空；" );
      }
      // 离职时间有效性
      else
      {
         // 判断派送协议离职时间有效性
         final String startDate = employeeContractVO.getStartDate();
         final String endDate = employeeContractVO.getEndDate();

         // 固定期劳动合同
         if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
         {
            if ( !KANUtil.between( KANUtil.createDate( startDate ), KANUtil.createDate( endDate ), KANUtil.createDate( resignDateCellData.getValue() ) ) )
            {
               flag = false;
               resignDateCellData.setErrorMessange( "第 " + ( resignDateCellData.getRow() + 1 ) + " 行，[" + resignDateCellData.getColumnVO().getNameZH() + ":"
                     + resignDateCellData.getFormateValue() + "]时间不在合同有效时间范围内；" );
            }
         }
         // 非固定期间劳动合同
         else if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) == null )
         {
            if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( resignDateCellData.getValue() ) ) )
            {
               flag = false;
               resignDateCellData.setErrorMessange( "第 " + ( resignDateCellData.getRow() + 1 ) + " 行，[" + resignDateCellData.getColumnVO().getNameZH() + ":"
                     + resignDateCellData.getFormateValue() + "]时间不能在合同开始时间之前；" );
            }
         }
         else
         {
            flag = false;
            resignDateCellData.setErrorMessange( "第 " + ( resignDateCellData.getRow() + 1 ) + " 行，劳动合同开始时间异常；" );
         }
      }

      // 离职状态不能为空
      final CellData employStatusCellData = importDataDTO.getCellDataByColumnNameDB( "employStatus" );
      if ( employStatusCellData != null && KANUtil.filterEmpty( employStatusCellData.getValue() ) == null )
      {
         flag = false;
         employStatusCellData.setErrorMessange( "第 " + ( employStatusCellData.getRow() + 1 ) + " 行，[" + employStatusCellData.getColumnVO().getNameZH() + "]不能为空；" );
      }
      // 离职状态必须为离职
      else
      {
         if ( "1".equals( employStatusCellData.getDbValue().toString() ) )
         {
            flag = false;
            employStatusCellData.setErrorMessange( "第 " + ( employStatusCellData.getRow() + 1 ) + " 行，[" + employStatusCellData.getColumnVO().getNameZH() + "]状态不能为“在职”；" );
         }
      }

      /** 判断社保数据有效性  start **/
      final CellData sb1IdCellData = importDataDTO.getCellDataByColumnNameDB( "sb1Id" );
      final CellData sb1NameCellData = importDataDTO.getCellDataByColumnNameDB( "sb1Name" );
      final CellData sb1EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "sb1EndDate" );
      final CellData sb2IdCellData = importDataDTO.getCellDataByColumnNameDB( "sb2Id" );
      final CellData sb2NameCellData = importDataDTO.getCellDataByColumnNameDB( "sb2Name" );
      final CellData sb2EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "sb2EndDate" );
      final CellData sb3IdCellData = importDataDTO.getCellDataByColumnNameDB( "sb3Id" );
      final CellData sb3NameCellData = importDataDTO.getCellDataByColumnNameDB( "sb3Name" );
      final CellData sb3EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "sb3EndDate" );

      // 获取员工社保方案List
      final List< Object > employeeContractSBVOObjects = this.getEmployeeContractSBDao().getEmployeeContractSBVOsByContractId( contractId );
      if ( ( sb1IdCellData != null && KANUtil.filterEmpty( sb1IdCellData.getValue() ) != null )
            || ( sb1NameCellData != null && KANUtil.filterEmpty( sb1NameCellData.getValue() ) != null )
            || ( sb2IdCellData != null && KANUtil.filterEmpty( sb2IdCellData.getValue() ) != null )
            || ( sb2NameCellData != null && KANUtil.filterEmpty( sb2NameCellData.getValue() ) != null )
            || ( sb3IdCellData != null && KANUtil.filterEmpty( sb3IdCellData.getValue() ) != null )
            || ( sb3NameCellData != null && KANUtil.filterEmpty( sb3NameCellData.getValue() ) != null ) )
      {
         // 员工没有社保方案
         if ( employeeContractSBVOObjects == null || employeeContractSBVOObjects.size() == 0 )
         {
            flag = false;
            employeeContractCellData.setErrorMessange( "第 " + ( employeeContractCellData.getRow() + 1 ) + " 行，找不到[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                  + contractId + "]任何社保方案；" );
         }
         else
         {
            // 无社保方案可退
            if ( checkEmployeeContractSBStatus( employeeContractSBVOObjects ) == null )
            {
               flag = false;
               employeeContractCellData.setErrorMessange( "第 " + ( employeeContractCellData.getRow() + 1 ) + " 行，找不到[" + employeeContractCellData.getColumnVO().getNameZH()
                     + "][ID: " + contractId + "]下社保方案状态符合退保的（应：“待申报加保”、“正常缴纳”）；" );
            }
            // 有社保方案可退
            else
            {
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO1 = getSocialBenefitSolutionDTOByCondition( accountConstants, corpId, sb1IdCellData, sb1NameCellData );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO2 = getSocialBenefitSolutionDTOByCondition( accountConstants, corpId, sb2IdCellData, sb2NameCellData );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO3 = getSocialBenefitSolutionDTOByCondition( accountConstants, corpId, sb3IdCellData, sb3NameCellData );

               // 如果社保1存在，验证时间有效性
               if ( socialBenefitSolutionDTO1 != null )
               {
                  if ( validateEmployeeContractSB( socialBenefitSolutionDTO1, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, sb1EndDateCellData ) )
                  {
                     addSBCellData( importDataDTO, sb1IdCellData, sb1NameCellData, "sb1", socialBenefitSolutionDTO1 );
                  }
               }

               // 如果社保2存在，验证时间有效性
               if ( socialBenefitSolutionDTO2 != null )
               {
                  if ( validateEmployeeContractSB( socialBenefitSolutionDTO2, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, sb2EndDateCellData ) )
                  {
                     addSBCellData( importDataDTO, sb2IdCellData, sb2NameCellData, "sb2", socialBenefitSolutionDTO2 );
                  }
               }

               // 如果社保3存在，验证时间有效性
               if ( socialBenefitSolutionDTO3 != null )
               {
                  if ( validateEmployeeContractSB( socialBenefitSolutionDTO3, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, sb3EndDateCellData ) )
                  {
                     addSBCellData( importDataDTO, sb3IdCellData, sb3NameCellData, "sb3", socialBenefitSolutionDTO3 );
                  }
               }
            }
         }
      }
      /** 判断社保数据有效性  end **/

      /** 判断商保数据有效性  start **/
      final CellData cb1IdCellData = importDataDTO.getCellDataByColumnNameDB( "cb1Id" );
      final CellData cb1NameCellData = importDataDTO.getCellDataByColumnNameDB( "cb1Name" );
      final CellData cb1EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "cb1EndDate" );
      final CellData cb2IdCellData = importDataDTO.getCellDataByColumnNameDB( "cb2Id" );
      final CellData cb2NameCellData = importDataDTO.getCellDataByColumnNameDB( "cb2Name" );
      final CellData cb2EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "cb2EndDate" );

      // 获取员工商保方案List
      final List< Object > employeeContractCBVOObjects = this.getEmployeeContractCBDao().getEmployeeContractCBVOsByContractId( contractId );
      if ( ( cb1IdCellData != null && KANUtil.filterEmpty( cb1IdCellData.getValue() ) != null )
            || ( cb1NameCellData != null && KANUtil.filterEmpty( cb1NameCellData.getValue() ) != null )
            || ( cb2IdCellData != null && KANUtil.filterEmpty( cb2IdCellData.getValue() ) != null )
            || ( cb2NameCellData != null && KANUtil.filterEmpty( cb2NameCellData.getValue() ) != null ) )
      {
         // 员工没有商保方案
         if ( employeeContractCBVOObjects == null || employeeContractCBVOObjects.size() == 0 )
         {
            flag = false;
            employeeContractCellData.setErrorMessange( "第 " + ( employeeContractCellData.getRow() + 1 ) + " 行，找不到[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                  + contractId + "]任何商保方案；" );
         }
         else
         {
            // 无商保方案可退
            if ( checkEmployeeContractCBStatus( employeeContractCBVOObjects ) == null )
            {
               flag = false;
               employeeContractCellData.setErrorMessange( "第 " + ( employeeContractCellData.getRow() + 1 ) + " 行，找不到[" + employeeContractCellData.getColumnVO().getNameZH()
                     + "][ID: " + contractId + "]下商保方案状态符合退保的（应：“待申报加保”、“正常缴纳”）；" );
            }
            // 有商保方案可退
            else
            {
               final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO1 = getCommercialBenefitSolutionDTOByCondition( accountConstants, corpId, cb1IdCellData, cb1NameCellData );
               final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO2 = getCommercialBenefitSolutionDTOByCondition( accountConstants, corpId, cb2IdCellData, cb2NameCellData );

               // 如果商保1存在，验证时间有效性
               if ( commercialBenefitSolutionDTO1 != null )
               {
                  if ( validateEmployeeContractCB( commercialBenefitSolutionDTO1, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, cb1EndDateCellData ) )
                  {
                     addCBCellData( importDataDTO, cb1IdCellData, cb1NameCellData, "cb1", commercialBenefitSolutionDTO1 );
                  }
               }

               // 如果商保2存在，验证时间有效性
               if ( commercialBenefitSolutionDTO2 != null )
               {
                  if ( validateEmployeeContractCB( commercialBenefitSolutionDTO2, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, cb2EndDateCellData ) )
                  {
                     addCBCellData( importDataDTO, cb2IdCellData, cb2NameCellData, "cb2", commercialBenefitSolutionDTO2 );
                  }
               }
            }
         }
      }
      /** 判断商保数据有效性  end **/

      return flag;
   }

   private void addCBCellData( final CellDataDTO importDataDTO, final CellData cbIdCellData, final CellData cbNameCellData, final String dbNameType,
         final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO )
   {
      if ( cbIdCellData == null )
      {
         importDataDTO.getCellDatas().add( getCbIdCellData( commercialBenefitSolutionDTO, dbNameType + "Id" ) );
      }
      else if ( KANUtil.filterEmpty( cbIdCellData.getDbValue() ) == null )
      {
         cbIdCellData.setDbValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );
      }

      if ( cbNameCellData == null )
      {
         importDataDTO.getCellDatas().add( getCbNameCellData( commercialBenefitSolutionDTO, dbNameType + "Name" ) );
      }
      else if ( KANUtil.filterEmpty( cbNameCellData.getDbValue() ) == null )
      {
         cbNameCellData.setDbValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() );
      }
   }

   // 生成商保ID CellData
   private CellData getCbIdCellData( final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO, final String nameDB )
   {
      // 添加状态
      CellData cbIdCellData = new CellData();

      final ColumnVO cbIdColumnVO = new ColumnVO();
      cbIdColumnVO.setNameDB( nameDB );
      cbIdColumnVO.setIsForeignKey( "2" );
      cbIdColumnVO.setAccountId( "1" );
      cbIdColumnVO.setIsDBColumn( "1" );

      cbIdCellData.setColumnVO( cbIdColumnVO );
      cbIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      cbIdCellData.setDbValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );
      cbIdCellData.setValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );

      return cbIdCellData;
   }

   // 生成商保Name CellData
   private CellData getCbNameCellData( final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO, final String nameDB )
   {
      // 添加状态
      CellData cbNameCellData = new CellData();

      final ColumnVO cbNameColumnVO = new ColumnVO();
      cbNameColumnVO.setNameDB( nameDB );
      cbNameColumnVO.setIsForeignKey( "2" );
      cbNameColumnVO.setAccountId( "1" );
      cbNameColumnVO.setIsDBColumn( "1" );

      cbNameCellData.setColumnVO( cbNameColumnVO );
      cbNameCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      cbNameCellData.setDbValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() );
      cbNameCellData.setValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() );

      return cbNameCellData;
   }

   private void addSBCellData( final CellDataDTO importDataDTO, final CellData sbIdCellData, final CellData sbNameCellData, final String dbNameType,
         final SocialBenefitSolutionDTO socialBenefitSolutionDTO )
   {
      if ( sbIdCellData == null )
      {
         importDataDTO.getCellDatas().add( getSbIdCellData( socialBenefitSolutionDTO, dbNameType + "Id" ) );
      }
      else if ( KANUtil.filterEmpty( sbIdCellData.getDbValue() ) == null )
      {
         sbIdCellData.setDbValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );
      }

      if ( sbNameCellData == null )
      {
         importDataDTO.getCellDatas().add( getSbNameCellData( socialBenefitSolutionDTO, dbNameType + "Name" ) );
      }
      else if ( KANUtil.filterEmpty( sbNameCellData.getDbValue() ) == null )
      {
         sbNameCellData.setDbValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() );
      }
   }

   // 生成社保ID CellData
   private CellData getSbIdCellData( final SocialBenefitSolutionDTO socialBenefitSolutionDTO, final String nameDB )
   {
      // 添加状态
      CellData sbIdCellData = new CellData();

      final ColumnVO sbIdColumnVO = new ColumnVO();
      sbIdColumnVO.setNameDB( nameDB );
      sbIdColumnVO.setIsForeignKey( "2" );
      sbIdColumnVO.setAccountId( "1" );
      sbIdColumnVO.setIsDBColumn( "1" );

      sbIdCellData.setColumnVO( sbIdColumnVO );
      sbIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      sbIdCellData.setDbValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );
      sbIdCellData.setValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );

      return sbIdCellData;
   }

   // 生成社保Name CellData
   private CellData getSbNameCellData( final SocialBenefitSolutionDTO socialBenefitSolutionDTO, final String nameDB )
   {
      // 添加状态
      CellData sbNameCellData = new CellData();

      final ColumnVO sbNameColumnVO = new ColumnVO();
      sbNameColumnVO.setNameDB( nameDB );
      sbNameColumnVO.setIsForeignKey( "2" );
      sbNameColumnVO.setAccountId( "1" );
      sbNameColumnVO.setIsDBColumn( "1" );

      sbNameCellData.setColumnVO( sbNameColumnVO );
      sbNameCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      sbNameCellData.setDbValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() );
      sbNameCellData.setValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() );

      return sbNameCellData;
   }

   // 状态是否为“待申报加保”、“正常缴纳”状态
   private EmployeeContractSBVO checkEmployeeContractSBStatus( final List< Object > employeeContractSBVOObjects )
   {
      for ( Object object : employeeContractSBVOObjects )
      {
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) object;
         if ( employeeContractSBVO != null
               && ( "2".equals( KANUtil.filterEmpty( employeeContractSBVO.getStatus() ) ) || "3".equals( KANUtil.filterEmpty( employeeContractSBVO.getStatus() ) ) ) )
         {
            return employeeContractSBVO;
         }
      }

      return null;
   }

   // 状态是否为“待申报加保”、“正常缴纳”状态
   private EmployeeContractCBVO checkEmployeeContractCBStatus( final List< Object > employeeContractSBVOObjects )
   {
      for ( Object object : employeeContractSBVOObjects )
      {
         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) object;
         if ( employeeContractCBVO != null
               && ( "2".equals( KANUtil.filterEmpty( employeeContractCBVO.getStatus() ) ) || "3".equals( KANUtil.filterEmpty( employeeContractCBVO.getStatus() ) ) ) )
         {
            return employeeContractCBVO;
         }
      }

      return null;
   }

   // 根据条件查找SocialBenefitSolutionDTO
   private SocialBenefitSolutionDTO getSocialBenefitSolutionDTOByCondition( final KANAccountConstants accountConstants, final String corpId, final CellData sbIdCellData,
         final CellData sbNameCellData )
   {
      SocialBenefitSolutionDTO socialBenefitSolutionDTO = null;
      // 按ID查找
      if ( sbIdCellData != null && sbNameCellData == null )
      {
         socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByHeaderId( ( String ) sbIdCellData.getDbValue() );
      }
      // 按Name查找
      else if ( sbIdCellData == null && sbNameCellData != null )
      {
         socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByName( sbNameCellData.getValue(), corpId );
      }
      // ID/Name同时存在
      else if ( sbIdCellData != null && sbNameCellData != null )
      {
         socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByName( sbNameCellData.getValue(), corpId );
         // ID和Name不匹配
         if ( socialBenefitSolutionDTO == null || !socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId().equals( sbIdCellData.getValue() ) )
         {
            return null;
         }
      }

      return socialBenefitSolutionDTO;
   }

   // 根据条件查找CommercialBenefitSolutionDTO
   private CommercialBenefitSolutionDTO getCommercialBenefitSolutionDTOByCondition( final KANAccountConstants accountConstants, final String corpId, final CellData cbIdCellData,
         final CellData cbNameCellData )
   {
      CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = null;
      // 按ID查找
      if ( cbIdCellData != null && cbNameCellData == null )
      {
         commercialBenefitSolutionDTO = accountConstants.getCommercialBenefitSolutionDTOByHeaderId( ( String ) cbIdCellData.getDbValue() );
      }
      // 按Name查找
      else if ( cbIdCellData == null && cbNameCellData != null )
      {
         commercialBenefitSolutionDTO = accountConstants.getCommercialBenefitSolutionDTOByName( cbNameCellData.getValue(), corpId );
      }
      // ID/Name同时存在
      else if ( cbIdCellData != null && cbNameCellData != null )
      {
         commercialBenefitSolutionDTO = accountConstants.getCommercialBenefitSolutionDTOByName( cbNameCellData.getValue(), corpId );
         // ID和Name不匹配
         if ( commercialBenefitSolutionDTO == null || !commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId().equals( cbIdCellData.getValue() ) )
         {
            return null;
         }
      }

      return commercialBenefitSolutionDTO;
   }

   // 根据solutionId查找EmployeeContractSBVO
   private EmployeeContractSBVO getEmployeeContractSBVOBySolutionId( final List< Object > employeeContractSBVOObjects, final String solutionId )
   {
      for ( Object object : employeeContractSBVOObjects )
      {
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) object;
         if ( employeeContractSBVO.getSbSolutionId().equals( solutionId ) )
         {
            return employeeContractSBVO;
         }
      }

      return null;
   }

   // 根据solutionId查找EmployeeContractCBVO
   private EmployeeContractCBVO getEmployeeContractCBVOBySolutionId( final List< Object > employeeContractCBVOObjects, final String solutionId )
   {
      for ( Object object : employeeContractCBVOObjects )
      {
         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) object;
         if ( employeeContractCBVO.getSolutionId().equals( solutionId ) )
         {
            return employeeContractCBVO;
         }
      }

      return null;
   }

   // 校验社保信息的有效性
   private boolean validateEmployeeContractSB( final SocialBenefitSolutionDTO socialBenefitSolutionDTO, final List< Object > employeeContractSBVOObjects,
         final EmployeeContractVO employeeContractVO, final CellData employeeContractCellData, final CellData sbEndDateCellData ) throws KANException
   {
      try
      {
         boolean flag = true;
         // 退保时间不能为空
         if ( sbEndDateCellData != null && KANUtil.filterEmpty( sbEndDateCellData.getDbValue() ) == null )
         {
            flag = false;
            sbEndDateCellData.setErrorMessange( "第 " + ( sbEndDateCellData.getRow() + 1 ) + " 行，[" + sbEndDateCellData.getColumnVO().getNameZH() + "]不能为空；" );
         }
         else
         {
            final EmployeeContractSBVO employeeContractSBVO = getEmployeeContractSBVOBySolutionId( employeeContractSBVOObjects, socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );
            if ( employeeContractSBVO == null )
            {
               flag = false;
               sbEndDateCellData.setErrorMessange( "第 " + ( sbEndDateCellData.getRow() + 1 ) + " 行，[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                     + employeeContractVO.getContractId() + "]下不存在社保险种[" + socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() + "]；" );
            }
            else
            {
               final List< Object > objects = new ArrayList< Object >();
               objects.add( employeeContractSBVO );
               // 验证社保方案状态
               if ( checkEmployeeContractSBStatus( objects ) == null )
               {
                  flag = false;
                  employeeContractCellData.setErrorMessange( "第 " + ( employeeContractCellData.getRow() + 1 ) + " 行，找不到[" + employeeContractCellData.getColumnVO().getNameZH()
                        + "][ID: " + employeeContractVO.getContractId() + "]下社保方案状态符合退保的（应：“待申报加保”、“正常缴纳”）；" );
               }
               // 验证退保时间
               else
               {
                  final String startDate = employeeContractSBVO.getStartDate();
                  final String endDate = employeeContractVO.getEndDate();

                  // 固定期劳动合同
                  if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
                  {
                     if ( !KANUtil.between( KANUtil.createDate( startDate ), KANUtil.createDate( endDate ), KANUtil.createDate( sbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        sbEndDateCellData.setErrorMessange( "第 " + ( sbEndDateCellData.getRow() + 1 ) + " 行，[" + sbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + sbEndDateCellData.getFormateValue() + "]时间不在合同有效时间范围内；" );
                     }
                  }
                  // 非固定期间劳动合同
                  else if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) == null )
                  {
                     if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( sbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        sbEndDateCellData.setErrorMessange( "第 " + ( sbEndDateCellData.getRow() + 1 ) + " 行，[" + sbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + sbEndDateCellData.getFormateValue() + "]时间不能在合同开始时间之前；" );
                     }
                  }
               }
            }
         }

         return flag;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   // 校验社保信息的有效性
   private boolean validateEmployeeContractCB( final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO, final List< Object > employeeContractCBVOObjects,
         final EmployeeContractVO employeeContractVO, final CellData employeeContractCellData, final CellData cbEndDateCellData ) throws KANException
   {
      try
      {
         boolean flag = true;
         // 退保时间不能为空
         if ( cbEndDateCellData != null && KANUtil.filterEmpty( cbEndDateCellData.getDbValue() ) == null )
         {
            flag = false;
            cbEndDateCellData.setErrorMessange( "第 " + ( cbEndDateCellData.getRow() + 1 ) + " 行，[" + cbEndDateCellData.getColumnVO().getNameZH() + "]不能为空；" );
         }
         else
         {
            final EmployeeContractCBVO employeeContractCBVO = getEmployeeContractCBVOBySolutionId( employeeContractCBVOObjects, commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );
            if ( employeeContractCBVO == null )
            {
               flag = false;
               cbEndDateCellData.setErrorMessange( "第 " + ( cbEndDateCellData.getRow() + 1 ) + " 行，[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                     + employeeContractVO.getContractId() + "]下不存在社保险种[" + commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() + "]；" );
            }
            else
            {
               final List< Object > objects = new ArrayList< Object >();
               objects.add( employeeContractCBVO );
               // 验证社保方案状态
               if ( checkEmployeeContractCBStatus( objects ) == null )
               {
                  flag = false;
                  employeeContractCellData.setErrorMessange( "第 " + ( employeeContractCellData.getRow() + 1 ) + " 行，找不到[" + employeeContractCellData.getColumnVO().getNameZH()
                        + "][ID: " + employeeContractVO.getContractId() + "]下商保方案状态符合退保的（应：“待申报加保”、“正常缴纳”）；" );
               }
               // 验证退保时间
               else
               {
                  final String startDate = employeeContractCBVO.getStartDate();
                  final String endDate = employeeContractVO.getEndDate();

                  // 固定期劳动合同
                  if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
                  {
                     if ( !KANUtil.between( KANUtil.createDate( startDate ), KANUtil.createDate( endDate ), KANUtil.createDate( cbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        cbEndDateCellData.setErrorMessange( "第 " + ( cbEndDateCellData.getRow() + 1 ) + " 行，[" + cbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + cbEndDateCellData.getFormateValue() + "]时间不在合同有效时间范围内；" );
                     }
                  }
                  // 非固定期间劳动合同
                  else if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) == null )
                  {
                     if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( cbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        cbEndDateCellData.setErrorMessange( "第 " + ( cbEndDateCellData.getRow() + 1 ) + " 行，[" + cbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + cbEndDateCellData.getFormateValue() + "]时间不能在合同开始时间之前；" );
                     }
                  }
               }
            }
         }

         return flag;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      return false;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      return false;
   }

}
