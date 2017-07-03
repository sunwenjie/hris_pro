package com.kan.hro.service.impl.biz.sb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.kan.base.dao.inf.management.SocialBenefitSolutionDetailDao;
import com.kan.base.dao.inf.management.SocialBenefitSolutionHeaderDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class FeedBackImportHandlerImpl implements ExcelImportHandler< List< CellDataDTO > >
{
   private EmployeeContractDao employeeContractDao;

   private EmployeeContractSBDao employeeContractSBDao;

   private SocialBenefitSolutionDetailDao socialBenefitSolutionDetailDao;

   private SocialBenefitSolutionHeaderDao socialBenefitSolutionHeaderDao;

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

   public SocialBenefitSolutionDetailDao getSocialBenefitSolutionDetailDao()
   {
      return socialBenefitSolutionDetailDao;
   }

   public void setSocialBenefitSolutionDetailDao( SocialBenefitSolutionDetailDao socialBenefitSolutionDetailDao )
   {
      this.socialBenefitSolutionDetailDao = socialBenefitSolutionDetailDao;
   }

   public SocialBenefitSolutionHeaderDao getSocialBenefitSolutionHeaderDao()
   {
      return socialBenefitSolutionHeaderDao;
   }

   public void setSocialBenefitSolutionHeaderDao( SocialBenefitSolutionHeaderDao socialBenefitSolutionHeaderDao )
   {
      this.socialBenefitSolutionHeaderDao = socialBenefitSolutionHeaderDao;
   }

   @Override
   public void init( final List< CellDataDTO > importData )
   {

   }

   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importCellDateDTOs, HttpServletRequest request )
   {
      int errorCount = 0;
      // 初始化返回错误信息
      StringBuffer strb = new StringBuffer();

      // 初始化返回的 CellDataDTO 集合
      List< CellDataDTO > returnCellDateDTOs = new ArrayList< CellDataDTO >();

      if ( importCellDateDTOs != null && importCellDateDTOs.size() > 0 )
      {
         for ( CellDataDTO importCellDataDTO : importCellDateDTOs )
         {
            if ( importCellDataDTO != null )
            {
               String contractId = "";
               String monthly = "";
               EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();

               CellData entityIdCellData = null;
//               CellData businessTypeIdCellData = null;
               CellData corpIdCellData = null;
               CellData employeeIdCellData = null;
               CellData employeeNameZHCellData = null;
               CellData employeeNameENCellData = null;
               CellData clientNoCellData = null;
               CellData clientNameZHCellData = null;
               CellData clientNameENCellData = null;
               CellData employStatusCellData = null;
               CellData orderIdCellData = null;

               // 初始化劳动合同
               EmployeeContractVO employeeContractVO = new EmployeeContractVO();

               for ( CellData cellData : importCellDataDTO.getCellDatas() )
               {
                  if ( cellData != null )
                  {

                     if ( "monthly".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        monthly = cellData.getValue();
                        // 月份不能为空
                        if ( monthly == null )
                        {
                           errorCount++;
                           strb.append( "第" + ( cellData.getRow() + 1 ) + "行[" + cellData.getColumnVO().getNameZH() + "]不能为空。</br>" );
                        }
                     }

                     if ( "contractId".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        contractId = cellData.getValue();
                        // 劳动合同ID不能为空
                        if ( contractId == null )
                        {
                           errorCount++;
                           strb.append( "第" + ( cellData.getRow() + 1 ) + "行[" + cellData.getColumnVO().getNameZH() + "]不能为空。</br>" );
                        }
                        else
                        {

                           // 根据employeeID查询对应的EmployeeVO
                           try
                           {
                              employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( contractId );
                              if ( employeeContractVO == null )
                              {
                                 errorCount++;
                                 strb.append( "第" + ( cellData.getRow() + 1 ) + "行未找到有效的[" + cellData.getColumnVO().getNameZH() + "]ID:" + contractId + "</br>" );
                              }
                           }
                           catch ( KANException e )
                           {
                              e.printStackTrace();
                           }
                        }
                     }
                     else if ( "employeeIdZH".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        employeeIdCellData = cellData;
                     }
                     else if ( "employeeNameZH".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        employeeNameZHCellData = cellData;
                     }
                     else if ( "employeeNameEN".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        employeeNameENCellData = cellData;
                     }
                     else if ( "clientNo".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        clientNoCellData = cellData;
                     }
                     else if ( "clientNameZH".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        clientNameZHCellData = cellData;
                     }
                     else if ( "clientNameEN".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        clientNameENCellData = cellData;
                     }
                     else if ( "employStatus".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        employStatusCellData = cellData;
                     }
                     else if ( "corpId".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        corpIdCellData = cellData;
                     }
                  }
               }

               /** 如果雇员信息未配置、根据empoyeeID查询出employeeVO ，然后设置employeeNameZHCellData，employeeNameENCellData的DBValue为employeeVO的NameZH，NameEN */
               if ( employeeContractVO != null )
               {
                  // 获得employeeSBId
                  employeeContractSBVO = getEmployeeContractVO( importCellDataDTO, contractId, strb, errorCount );

                  // 添加CorpId
                  if ( corpIdCellData == null )
                  {
                     corpIdCellData = new CellData();
                     addColumnInfo( corpIdCellData, "corpId" );
                     corpIdCellData.setJdbcDataType( JDBCDataType.INT );
                     corpIdCellData.setDbValue( employeeContractVO.getCorpId() );
                     importCellDataDTO.getCellDatas().add( corpIdCellData );
                  }
                  else if ( KANUtil.filterEmpty( corpIdCellData.getValue() ) == null )
                  {
                     corpIdCellData.setDbValue( employeeContractVO.getCorpId() );
                  }

                  // 扩充雇员Id
                  if ( employeeIdCellData == null )
                  {
                     employeeIdCellData = new CellData();
                     addColumnInfo( employeeIdCellData, "employeeId" );
                     employeeIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     employeeIdCellData.setDbValue( employeeContractVO.getEmployeeId() );
                     importCellDataDTO.getCellDatas().add( employeeIdCellData );
                  }
                  else if ( KANUtil.filterEmpty( employeeNameZHCellData.getValue() ) == null )
                  {
                     employeeNameZHCellData.setDbValue( employeeContractVO.getEmployeeNameZH() );
                  }

                  // 扩充雇员中文名
                  if ( employeeNameZHCellData == null )
                  {
                     employeeNameZHCellData = new CellData();
                     addColumnInfo( employeeNameZHCellData, "employeeNameZH" );
                     employeeNameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     employeeNameZHCellData.setDbValue( employeeContractVO.getEmployeeNameZH() );
                     importCellDataDTO.getCellDatas().add( employeeNameZHCellData );
                  }
                  else if ( KANUtil.filterEmpty( employeeNameZHCellData.getValue() ) == null )
                  {
                     employeeNameZHCellData.setDbValue( employeeContractVO.getEmployeeNameZH() );
                  }

                  // 扩充雇员英文名
                  if ( employeeNameENCellData == null )
                  {
                     employeeNameENCellData = new CellData();
                     addColumnInfo( employeeNameENCellData, "employeeNameEN" );
                     employeeNameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     employeeNameENCellData.setDbValue( employeeContractVO.getEmployeeNameEN() );
                     importCellDataDTO.getCellDatas().add( employeeNameENCellData );
                  }
                  else if ( KANUtil.filterEmpty( employeeNameENCellData.getValue() ) == null )
                  {
                     employeeNameENCellData.setDbValue( employeeContractVO.getEmployeeNameEN() );
                  }

                  // 扩充客户编号
                  if ( clientNoCellData == null )
                  {
                     clientNoCellData = new CellData();
                     addColumnInfo( clientNoCellData, "clientNo" );
                     clientNoCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     clientNoCellData.setDbValue( employeeContractVO.getClientNumber() );
                     importCellDataDTO.getCellDatas().add( clientNoCellData );
                  }
                  else if ( KANUtil.filterEmpty( clientNameZHCellData.getValue() ) == null )
                  {
                     clientNameZHCellData.setDbValue( employeeContractVO.getEmployeeNameZH() );
                  }

                  // 扩充客户中文名
                  if ( clientNameZHCellData == null )
                  {
                     clientNameZHCellData = new CellData();
                     addColumnInfo( clientNameZHCellData, "clientNameZH" );
                     clientNameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     clientNameZHCellData.setDbValue( employeeContractVO.getClientNameZH() );
                     importCellDataDTO.getCellDatas().add( clientNameZHCellData );
                  }
                  else if ( KANUtil.filterEmpty( clientNameZHCellData.getValue() ) == null )
                  {
                     clientNameZHCellData.setDbValue( employeeContractVO.getClientNameZH() );
                  }

                  // 扩充客户英文名
                  if ( clientNameENCellData == null )
                  {
                     clientNameENCellData = new CellData();
                     addColumnInfo( clientNameENCellData, "clientNameEN" );
                     clientNameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     clientNameENCellData.setDbValue( employeeContractVO.getClientNameEN() );
                     importCellDataDTO.getCellDatas().add( clientNameENCellData );
                  }
                  else if ( KANUtil.filterEmpty( clientNameENCellData.getValue() ) == null )
                  {
                     clientNameENCellData.setDbValue( employeeContractVO.getClientNameEN() );
                  }

                  // 扩充雇佣状态
                  if ( employStatusCellData == null )
                  {
                     employStatusCellData = new CellData();
                     addColumnInfo( employStatusCellData, "employStatus" );
                     employStatusCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                     employStatusCellData.setDbValue( employeeContractVO.getEmployStatus() );
                     importCellDataDTO.getCellDatas().add( employStatusCellData );
                  }
                  else if ( KANUtil.filterEmpty( employStatusCellData.getValue() ) == null )
                  {
                     employStatusCellData.setDbValue( employeeContractVO.getEmployStatus() );
                  }

                  // 扩充订单ID
                  if ( orderIdCellData == null )
                  {
                     orderIdCellData = new CellData();
                     addColumnInfo( orderIdCellData, "orderId" );
                     orderIdCellData.setJdbcDataType( JDBCDataType.INT );
                     orderIdCellData.setDbValue( employeeContractVO.getOrderId() );
                     importCellDataDTO.getCellDatas().add( orderIdCellData );
                  }

                  // 扩充法务实体ID
                  if ( entityIdCellData == null )
                  {
                     entityIdCellData = new CellData();
                     addColumnInfo( entityIdCellData, "entityId" );
                     entityIdCellData.setJdbcDataType( JDBCDataType.INT );
                     entityIdCellData.setDbValue( employeeContractVO.getEntityId() );
                     importCellDataDTO.getCellDatas().add( entityIdCellData );
                  }

                  // 扩充业务类型ID
                  /*if ( businessTypeIdCellData == null )
                  {
                     businessTypeIdCellData = new CellData();
                     addColumnInfo( businessTypeIdCellData, "businessTypeId" );
                     businessTypeIdCellData.setJdbcDataType( JDBCDataType.INT );
                     businessTypeIdCellData.setDbValue( employeeContractVO.getBusinessTypeId() );
                     importCellDataDTO.getCellDatas().add( businessTypeIdCellData );
                  }*/
               }

               // 扩充科目信息
               if ( importCellDataDTO.getSubCellDataDTOs() != null )
               {
                  // 检测从表数据（是否因为是“利息”和“滞纳金”而要添加 Celldata）
                  errorCount += verifySubCellDataDTO( importCellDataDTO, monthly, employeeContractSBVO, contractId, strb );
               }

               // 添加从表数据字段信息
               if ( importCellDataDTO.getSubCellDataDTOs() != null )
               {
                  for ( CellDataDTO subImportCellDataDTO : importCellDataDTO.getSubCellDataDTOs() )
                  {
                     try
                     {
                        // 校验数据并生成返回的 CellDateDTO 集合
                        errorCount += addDetailTableColumns( returnCellDateDTOs, importCellDataDTO, subImportCellDataDTO, contractId, monthly, employeeContractSBVO, strb );
                     }
                     catch ( KANException e )
                     {
                        e.printStackTrace();
                     }
                  }
               }

            }
         }
      }

      importCellDateDTOs.clear();
      importCellDateDTOs.addAll( returnCellDateDTOs );
      importCellDateDTOs = returnCellDateDTOs;

      if ( errorCount != 0 )
      {
         importCellDateDTOs.get( 0 ).getCellDatas().get( 0 ).setErrorMessange( strb.toString() );
         return false;
      }

      return true;
   }

   // CellData添加属性值
   private void addColumnInfo( final CellData cellData, final String nameDB )
   {
      final ColumnVO columnVO = new ColumnVO();
      columnVO.setNameDB( nameDB );
      columnVO.setIsForeignKey( "2" );
      columnVO.setAccountId( "1" );
      columnVO.setIsDBColumn( "1" );

      cellData.setColumnVO( columnVO );
   }

   // 检测从表数据（是否因为是“利息”和“滞纳金”而要添加 Celldata）
   private int verifySubCellDataDTO( final CellDataDTO importCellDataDTO, final String monthly, EmployeeContractSBVO employeeContractSBVO, final String contractId,
         StringBuffer strb )
   {
      int errorCount = 0;

      List< CellDataDTO > subCellDataDTOs = importCellDataDTO.getSubCellDataDTOs();

      if ( subCellDataDTOs.size() > 1 )
      {
         // 初始化补缴月份
         String accountMonthly = getAccountMonthly( importCellDataDTO );

         Iterator< CellDataDTO > subCellDataDTOsIte = subCellDataDTOs.iterator();

         while ( subCellDataDTOsIte.hasNext() )
         {
            final CellDataDTO subCellDataDTO = subCellDataDTOsIte.next();
            List< CellData > subCellDatas = subCellDataDTO.getCellDatas();

            if ( subCellDatas != null && subCellDatas.size() > 0 )
            {
               // 初始化要添加的CellData集合
               List< CellData > tempCellDatas = new ArrayList< CellData >();

               for ( CellData cellData : subCellDatas )
               {
                  final ColumnVO columnVO = cellData.getColumnVO();

                  // 如果是补缴险种
                  if ( "补缴险种".equals( columnVO.getNameZH() ) )
                  {
                     continue;
                  }

                  if ( ( "利息".equals( columnVO.getNameZH() ) ) || ( "滞纳金".equals( columnVO.getNameZH() ) ) )
                  {
                     if ( Double.parseDouble( KANUtil.filterEmpty( cellData.getDbValue() ) ) == 0 )
                     {
                        subCellDataDTOsIte.remove();
                        continue;
                     }
                     else
                     {
                        // 存储 社保险种 的值
                        final Object amountCompanyDbValue = cellData.getDbValue();

                        // 添加公司合计 CellDate
                        CellData amountCompanyCellData = new CellData();

                        final ColumnVO amountCompanyColumnVO = new ColumnVO();
                        amountCompanyColumnVO.setNameDB( "amountCompany" );
                        amountCompanyColumnVO.setNameEN( "Amount Company" );
                        amountCompanyColumnVO.setNameSys( "amountCompany" );
                        amountCompanyColumnVO.setIsForeignKey( "2" );
                        amountCompanyColumnVO.setAccountId( "1" );
                        amountCompanyColumnVO.setIsDBColumn( "1" );

                        amountCompanyCellData.setColumnVO( amountCompanyColumnVO );
                        amountCompanyCellData.setJdbcDataType( JDBCDataType.DOUBLE );
                        amountCompanyCellData.setDbValue( amountCompanyDbValue );
                        tempCellDatas.add( amountCompanyCellData );

                        // 添加个人合计 CellDate
                        CellData amountPersonalCellData = new CellData();

                        final ColumnVO amountPersonalColumnVO = new ColumnVO();
                        amountPersonalColumnVO.setNameDB( "amountPersonal" );
                        amountPersonalColumnVO.setNameEN( "Amount Personal" );
                        amountPersonalColumnVO.setNameSys( "amountPersonal" );
                        amountPersonalColumnVO.setIsForeignKey( "2" );
                        amountPersonalColumnVO.setAccountId( "1" );
                        amountPersonalColumnVO.setIsDBColumn( "1" );

                        amountPersonalCellData.setColumnVO( amountPersonalColumnVO );
                        amountPersonalCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                        amountPersonalCellData.setDbValue( "0" );
                        tempCellDatas.add( amountPersonalCellData );

                        // 添加补缴月份 CellDate
                        CellData accountMonthlyCellData = new CellData();

                        final ColumnVO accountMonthlyColumnVO = new ColumnVO();
                        accountMonthlyColumnVO.setNameDB( "accountMonthly" );
                        accountMonthlyColumnVO.setNameEN( "Account Monthly" );
                        accountMonthlyColumnVO.setNameSys( "accountMonthly" );
                        accountMonthlyColumnVO.setIsForeignKey( "2" );
                        accountMonthlyColumnVO.setAccountId( "1" );
                        accountMonthlyColumnVO.setIsDBColumn( "1" );

                        accountMonthlyCellData.setColumnVO( accountMonthlyColumnVO );
                        accountMonthlyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                        accountMonthlyCellData.setDbValue( accountMonthly );
                        tempCellDatas.add( accountMonthlyCellData );

                        cellData.setDbValue( columnVO.getNameZH() );
                     }
                  }

               }

               subCellDataDTO.getCellDatas().addAll( tempCellDatas );
            }
         }

      }

      return errorCount;
   }

   // 获得补缴月份
   private String getAccountMonthly( final CellDataDTO importCellDataDTO )
   {
      final List< CellDataDTO > subCellDataDTOs = importCellDataDTO.getSubCellDataDTOs();

      if ( subCellDataDTOs != null && subCellDataDTOs.size() > 0 )
      {
         for ( CellDataDTO cellDataDTO : subCellDataDTOs )
         {
            CellData cellData = cellDataDTO.getCellDataByColumnNameDB( "accountMonthly" );

            if ( cellData.getDbValue() != null )
            {
               return ( String ) cellData.getDbValue();
            }
         }
      }
      return null;
   }

   // 获得社保方案ID
   private EmployeeContractSBVO getEmployeeContractVO( final CellDataDTO importCellDataDTO, final String contractId, StringBuffer strb, int errorCount )
   {
      // 获得accountId、corpId
      final String accountId = importCellDataDTO.getAccounId();
      final String corpId = importCellDataDTO.getCorpId();
      final List< CellDataDTO > subCellDataDTOs = importCellDataDTO.getSubCellDataDTOs();

      if ( subCellDataDTOs != null && subCellDataDTOs.size() > 0 )
      {
         for ( CellDataDTO cellDataDTO : subCellDataDTOs )
         {
            CellData cellData = cellDataDTO.getCellDataByColumnNameDB( "nameZH" );

            if ( "补缴险种".equals( cellData.getColumnVO().getNameZH() ) )
            {
               // 获得科目名称
               String itemNameZH = ( String ) cellData.getDbValue();

               if ( itemNameZH != null )
               {
                  // 获得Item信息
                  if ( KANConstants.getKANAccountConstants( accountId ) != null )
                  {
                     ItemVO itemVO = null;
                     try
                     {
                        itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemName( itemNameZH, corpId );
                     }
                     catch ( KANException e )
                     {
                        e.printStackTrace();
                     }

                     if ( itemVO != null )
                     {
                        /** 查询派送协议下是否有社保方案 */
                        // 初始化查询对象
                        EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( contractId );
                        employeeContractSBVO.setAccountId( accountId );
                        employeeContractSBVO.setCorpId( corpId );

                        try
                        {
                           final List< Object > employeeContractSBVOs = this.employeeContractSBDao.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                           if ( employeeContractSBVOs == null || employeeContractSBVOs.size() == 0 )
                           {
                              errorCount++;
                              strb.append( "第" + ( importCellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "行派送协议[ID:" + contractId + "]没有有效的社保方案。</br>" );
                           }
                           else
                           {
                              // 初始化包含险种的所有社保方案
                              List< EmployeeContractSBVO > tempEmployeeContractSBVOs = new ArrayList< EmployeeContractSBVO >();

                              for ( Object object : employeeContractSBVOs )
                              {
                                 EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) object;
                                 final String sbSolutionId = tempEmployeeContractSBVO.getSbSolutionId();

                                 // 查看当前险种在派送协议所有的社保方案中是否存在
                                 SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
                                 socialBenefitSolutionDetailVO.setHeaderId( sbSolutionId );
                                 socialBenefitSolutionDetailVO.setItemId( itemVO.getItemId() );
                                 socialBenefitSolutionDetailVO.setStatus( "1" );
                                 final List< Object > socialBenefitSolutionDetailVOs = this.socialBenefitSolutionDetailDao.getSocialBenefitSolutionDetailVOsByCondition( socialBenefitSolutionDetailVO );

                                 if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
                                 {
                                    final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = this.socialBenefitSolutionHeaderDao.getSocialBenefitSolutionHeaderVOByHeaderId( tempEmployeeContractSBVO.getSbSolutionId() );
                                    tempEmployeeContractSBVO.setSbType( socialBenefitSolutionHeaderVO.getSbType() );
                                    tempEmployeeContractSBVOs.add( tempEmployeeContractSBVO );
                                 }
                              }

                              if ( tempEmployeeContractSBVOs.size() > 0 )
                              {
                                 if ( tempEmployeeContractSBVOs != null && tempEmployeeContractSBVOs.size() > 0 )
                                 {
                                    // 数据排序：社保险种升序
                                    Collections.sort( tempEmployeeContractSBVOs, new Comparator< Object >()
                                    {
                                       @Override
                                       public int compare( Object o1, Object o2 )
                                       {
                                          final EmployeeContractSBVO e1 = ( EmployeeContractSBVO ) o1;
                                          final EmployeeContractSBVO e2 = ( EmployeeContractSBVO ) o2;

                                          if ( Float.parseFloat( e1.getSbType() ) > ( Float.parseFloat( e2.getSbType() ) ) )
                                          {
                                             return 1;
                                          }
                                          if ( Float.parseFloat( e1.getSbType() ) < ( Float.parseFloat( e2.getSbType() ) ) )
                                          {
                                             return -1;
                                          }
                                          return 0;
                                       }
                                    } );
                                 }

                                 return tempEmployeeContractSBVOs.get( 0 );
                              }
                              // 无匹配随便挂个社保方案
                              else
                              {
                                 return ( EmployeeContractSBVO ) employeeContractSBVOs.get( 0 );
                              }
                           }
                        }
                        catch ( KANException e )
                        {
                           e.printStackTrace();
                        }

                     }
                     else
                     {
                        errorCount++;
                        strb.append( "第" + ( importCellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "行名称为:[" + itemNameZH + "]的社保险种。</br>" );
                        break;
                     }
                  }

               }
               break;
            }
         }
      }
      return null;
   }

   private int addDetailTableColumns( List< CellDataDTO > returnCellDateDTOs, final CellDataDTO importCellDataDTO, final CellDataDTO subImportCellDataDTO, final String contractId,
         final String monthly, EmployeeContractSBVO employeeContractSBVO, StringBuffer strb ) throws KANException
   {
      int errorCount = 0;
      // 获得accountId、corpId
      final String accountId = importCellDataDTO.getAccounId();
      final String corpId = importCellDataDTO.getCorpId();

      List< CellData > cellDatas = subImportCellDataDTO.getCellDatas();
      List< CellData > addCellDatas = new ArrayList< CellData >();

      if ( cellDatas != null && cellDatas.size() > 0 )
      {
         ColumnVO foreignKeyCellColumnVO = new ColumnVO();
         foreignKeyCellColumnVO.setNameDB( "headerId" );
         foreignKeyCellColumnVO.setIsForeignKey( "1" );
         foreignKeyCellColumnVO.setAccountId( "1" );//1 1代表数据库字段，其他代表，该account的自定义字段
         foreignKeyCellColumnVO.setIsDBColumn( "1" );

         CellData foreignKeyCellData = new CellData();
         foreignKeyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
         foreignKeyCellData.setColumnVO( foreignKeyCellColumnVO );
         addCellDatas.add( foreignKeyCellData );
         cellDatas.addAll( addCellDatas );

         for ( CellData cellData : cellDatas )
         {
            final ColumnVO columnVO = cellData.getColumnVO();
            // 如果对应的是“补缴险种”
            if ( "nameZH".equals( columnVO.getNameDB() ) )
            {
               // 获得科目名称
               String itemNameZH = ( String ) cellData.getDbValue();

               if ( itemNameZH != null )
               {
                  // 获得Item信息
                  if ( KANConstants.getKANAccountConstants( accountId ) != null )
                  {
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemName( itemNameZH, corpId );

                     if ( itemVO != null )
                     {
                        // 添加科目数据
                        errorCount += fetchReturnCellDateDTOs( returnCellDateDTOs, importCellDataDTO, subImportCellDataDTO, itemVO, cellData, contractId, monthly, employeeContractSBVO, strb );
                        return errorCount;
                     }
                     else
                     {
                        strb.append( "第" + ( importCellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "行未找到名称为：“ " + ( String ) cellData.getDbValue() + "”的社保险种。</br>" );
                        errorCount++;
                     }

                  }
               }

               cellDatas.addAll( addCellDatas );
               break;
            }
         }
      }

      return errorCount;
   }

   // 组合最终返回的CellDataDTO 集合
   private int fetchReturnCellDateDTOs( List< CellDataDTO > returnCellDateDTOs, CellDataDTO importCellDataDTO, CellDataDTO subImportCellDataDTO, ItemVO itemVO, CellData cellData,
         String contractId, String monthly, EmployeeContractSBVO employeeContractSBVO, StringBuffer strb ) throws KANException
   {
      int errorCount = 0;

      if ( returnCellDateDTOs != null && returnCellDateDTOs.size() > 0 )
      {
         for ( CellDataDTO existcellDataDTO : returnCellDateDTOs )
         {
            // 如果指定 ContractId 和 指定所属月份有数据
            if ( contractId.equals( existcellDataDTO.getCellDataDbValueByColumnNameDB( "contractId" ) )
                  && monthly.equals( existcellDataDTO.getCellDataDbValueByColumnNameDB( "monthly" ) ) )
            {
               // 获得科目对应的补缴月份
               final List< CellDataDTO > subCellDataDTOs = existcellDataDTO.getSubCellDataDTOs();

               for ( CellDataDTO existSubcellDataDTO : subCellDataDTOs )
               {
                  // 如果科目存在且补缴月份匹配
                  if ( itemVO.getItemId().equals( existSubcellDataDTO.getCellDataDbValueByColumnNameDB( "itemId" ) )
                        && subImportCellDataDTO.getCellDataDbValueByColumnNameDB( "accountMonthly" ).equals( existSubcellDataDTO.getCellDataDbValueByColumnNameDB( "accountMonthly" ) ) )
                  {
                     // 如果不是“滞纳金”或者“利息” - 报错
                     if ( !"77".equals( itemVO.getItemId() ) && !"78".equals( itemVO.getItemId() ) )
                     {
                        strb.append( "第" + ( cellData.getRow() + 1 ) + "行社保险种[" + itemVO.getNameZH() + "]重复出现。</br>" );
                        errorCount++;
                        return errorCount;
                     }
                     // 如果是“滞纳金”或者“利息” - 添加
                     else
                     {
                        existSubcellDataDTO.addCellDataDbValueByColumnNameDB( "amountCompany", ( String ) subImportCellDataDTO.getCellDataDbValueByColumnNameDB( "amountCompany" ) );
                        existSubcellDataDTO.addCellDataDbValueByColumnNameDB( "amountPersonal", ( String ) subImportCellDataDTO.getCellDataDbValueByColumnNameDB( "amountPersonal" ) );
                        return errorCount;
                     }

                  }
               }

               // 如果科目不存在，扩充SubImportCellDataDTO
               addsubCellDate( subImportCellDataDTO, itemVO, monthly );
               existcellDataDTO.getSubCellDataDTOs().add( subImportCellDataDTO );
               return errorCount;
            }
         }

         addsubCellDate( subImportCellDataDTO, itemVO, monthly );
         // 初始化要新增的CellDataDTO
         CellDataDTO addcellDataDTO = new CellDataDTO();

         addcellDataDTO.getCellDatas().addAll( importCellDataDTO.getCellDatas() );
         addcellDataDTO.setTableId( importCellDataDTO.getTableId() );
         addcellDataDTO.setTableName( importCellDataDTO.getTableName() );
         addcellDataDTO.getSubCellDataDTOs().add( subImportCellDataDTO );

         // 扩充CellDataDTO信息
         addCellDataDTOExtraInfo( addcellDataDTO, contractId, employeeContractSBVO );

         returnCellDateDTOs.add( addcellDataDTO );
         return errorCount;
      }
      addsubCellDate( subImportCellDataDTO, itemVO, monthly );

      // 初始化要新增的CellDataDTO
      CellDataDTO addcellDataDTO = new CellDataDTO();

      addcellDataDTO.getCellDatas().addAll( importCellDataDTO.getCellDatas() );
      addcellDataDTO.setTableId( importCellDataDTO.getTableId() );
      addcellDataDTO.setTableName( importCellDataDTO.getTableName() );
      addcellDataDTO.getSubCellDataDTOs().add( subImportCellDataDTO );

      // 扩充CellDataDTO信息
      addCellDataDTOExtraInfo( addcellDataDTO, contractId, employeeContractSBVO );

      returnCellDateDTOs.add( addcellDataDTO );
      return errorCount;
   }

   // 扩充CellDataDTO信息
   private void addCellDataDTOExtraInfo( final CellDataDTO addCellDataDTO, final String contractId, final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      // 需要扩充的从表字段
      ColumnVO tempStatusCellColumnVO = new ColumnVO();
      tempStatusCellColumnVO.setNameDB( "tempStatus" );
      tempStatusCellColumnVO.setIsForeignKey( "2" );
      tempStatusCellColumnVO.setAccountId( "1" );
      tempStatusCellColumnVO.setIsDBColumn( "1" );

      CellData tempStatusCellData = new CellData();
      tempStatusCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
      tempStatusCellData.setColumnVO( tempStatusCellColumnVO );

      // 设置状态默认为“新建”
      tempStatusCellData.setDbValue( "1" );
      addCellDataDTO.getCellDatas().add( tempStatusCellData );

      if ( employeeContractSBVO != null )
      {
         /** 扩充社保方案信息 */
         // EmployeeSBId
         CellData employeeSBIdCellData = new CellData();
         addColumnInfo( employeeSBIdCellData, "employeeSBId" );
         employeeSBIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
         employeeSBIdCellData.setDbValue( employeeContractSBVO.getEmployeeSBId() );
         addCellDataDTO.getCellDatas().add( employeeSBIdCellData );

         // SbSolutionId
         CellData sbSolutionIdCellData = new CellData();
         addColumnInfo( sbSolutionIdCellData, "sbSolutionId" );
         sbSolutionIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
         sbSolutionIdCellData.setDbValue( employeeContractSBVO.getSbSolutionId() );
         addCellDataDTO.getCellDatas().add( sbSolutionIdCellData );

         // 加保日期
         CellData startDateCellData = new CellData();
         addColumnInfo( startDateCellData, "startDate" );
         startDateCellData.setJdbcDataType( JDBCDataType.DATE );
         startDateCellData.setDbValue( employeeContractSBVO.getStartDate() );
         addCellDataDTO.getCellDatas().add( startDateCellData );

         // 退保日期
         CellData endDateCellData = new CellData();
         addColumnInfo( endDateCellData, "endDate" );
         endDateCellData.setJdbcDataType( JDBCDataType.DATE );
         endDateCellData.setDbValue( employeeContractSBVO.getEndDate() );
         addCellDataDTO.getCellDatas().add( endDateCellData );

         // 需要办理医保卡
         CellData needMedicalCardCellData = new CellData();
         addColumnInfo( needMedicalCardCellData, "needMedicalCard" );
         needMedicalCardCellData.setJdbcDataType( JDBCDataType.INT );
         needMedicalCardCellData.setDbValue( employeeContractSBVO.getNeedMedicalCard() );
         addCellDataDTO.getCellDatas().add( needMedicalCardCellData );

         // 需要办理社保卡
         CellData needSBCardCellData = new CellData();
         addColumnInfo( needSBCardCellData, "needSBCard" );
         needSBCardCellData.setJdbcDataType( JDBCDataType.INT );
         needSBCardCellData.setDbValue( employeeContractSBVO.getNeedSBCard() );
         addCellDataDTO.getCellDatas().add( needSBCardCellData );

         // 医保卡帐号
         CellData medicalNumberCellData = new CellData();
         addColumnInfo( medicalNumberCellData, "medicalNumber" );
         medicalNumberCellData.setJdbcDataType( JDBCDataType.INT );
         medicalNumberCellData.setDbValue( employeeContractSBVO.getMedicalNumber() );
         addCellDataDTO.getCellDatas().add( medicalNumberCellData );

         // 社保卡帐号
         CellData sbNumberCellData = addCellDataDTO.getCellDataByColumnNameDB( "sbNumber" );
         sbNumberCellData.setDbValue( employeeContractSBVO.getSbNumber() );

         // 公积金帐号
         CellData fundNumberCellData = new CellData();
         addColumnInfo( fundNumberCellData, "fundNumber" );
         fundNumberCellData.setJdbcDataType( JDBCDataType.INT );
         fundNumberCellData.setDbValue( employeeContractSBVO.getFundNumber() );
         addCellDataDTO.getCellDatas().add( fundNumberCellData );
      }
   }

   // 添加SubCellDate信息
   private void addsubCellDate( final CellDataDTO subImportCellDataDTO, final ItemVO itemVO, final String monthly )
   {
      // 需要扩充的从表字段
      ColumnVO tempStatusCellColumnVO = new ColumnVO();
      tempStatusCellColumnVO.setNameDB( "tempStatus" );
      tempStatusCellColumnVO.setIsForeignKey( "2" );
      tempStatusCellColumnVO.setAccountId( "1" );
      tempStatusCellColumnVO.setIsDBColumn( "1" );

      CellData tempStatusCellData = new CellData();
      tempStatusCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
      tempStatusCellData.setColumnVO( tempStatusCellColumnVO );

      // 设置状态默认为“新建”
      tempStatusCellData.setDbValue( "1" );
      subImportCellDataDTO.getCellDatas().add( tempStatusCellData );

      // 添加补缴月份 CellDate
      CellData monthlyCellData = new CellData();

      final ColumnVO monthlyColumnVO = new ColumnVO();
      monthlyColumnVO.setNameDB( "monthly" );
      monthlyColumnVO.setNameEN( "Monthly" );
      monthlyColumnVO.setNameSys( "monthly" );
      monthlyColumnVO.setIsForeignKey( "2" );
      monthlyColumnVO.setAccountId( "1" );
      monthlyColumnVO.setIsDBColumn( "1" );

      monthlyCellData.setColumnVO( monthlyColumnVO );
      monthlyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      monthlyCellData.setDbValue( monthly );
      subImportCellDataDTO.getCellDatas().add( monthlyCellData );

      // 添加险种信息
      String[][] copyPropertys = { { "itemId", "INT" }, { "itemNo", "VARCHAR" } };

      for ( String[] property : copyPropertys )
      {
         ColumnVO addCellColumnVO = new ColumnVO();
         addCellColumnVO.setNameDB( property[ 0 ] );
         addCellColumnVO.setIsForeignKey( "2" );
         addCellColumnVO.setAccountId( "1" );
         addCellColumnVO.setIsDBColumn( "1" );

         CellData addCellData = new CellData();
         addCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( property[ 1 ] ) );
         addCellData.setColumnVO( addCellColumnVO );

         try
         {
            addCellData.setDbValue( BeanUtils.getProperty( itemVO, property[ 0 ] ) );
         }
         catch ( Exception e )
         {
            e.printStackTrace();
         }
         // 添加扩充
         subImportCellDataDTO.getCellDatas().add( addCellData );
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
