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
      // ��ʼ�����ش�����Ϣ
      StringBuffer strb = new StringBuffer();

      // ��ʼ�����ص� CellDataDTO ����
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

               // ��ʼ���Ͷ���ͬ
               EmployeeContractVO employeeContractVO = new EmployeeContractVO();

               for ( CellData cellData : importCellDataDTO.getCellDatas() )
               {
                  if ( cellData != null )
                  {

                     if ( "monthly".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        monthly = cellData.getValue();
                        // �·ݲ���Ϊ��
                        if ( monthly == null )
                        {
                           errorCount++;
                           strb.append( "��" + ( cellData.getRow() + 1 ) + "��[" + cellData.getColumnVO().getNameZH() + "]����Ϊ�ա�</br>" );
                        }
                     }

                     if ( "contractId".equals( cellData.getColumnVO().getNameDB() ) )
                     {
                        contractId = cellData.getValue();
                        // �Ͷ���ͬID����Ϊ��
                        if ( contractId == null )
                        {
                           errorCount++;
                           strb.append( "��" + ( cellData.getRow() + 1 ) + "��[" + cellData.getColumnVO().getNameZH() + "]����Ϊ�ա�</br>" );
                        }
                        else
                        {

                           // ����employeeID��ѯ��Ӧ��EmployeeVO
                           try
                           {
                              employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( contractId );
                              if ( employeeContractVO == null )
                              {
                                 errorCount++;
                                 strb.append( "��" + ( cellData.getRow() + 1 ) + "��δ�ҵ���Ч��[" + cellData.getColumnVO().getNameZH() + "]ID:" + contractId + "</br>" );
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

               /** �����Ա��Ϣδ���á�����empoyeeID��ѯ��employeeVO ��Ȼ������employeeNameZHCellData��employeeNameENCellData��DBValueΪemployeeVO��NameZH��NameEN */
               if ( employeeContractVO != null )
               {
                  // ���employeeSBId
                  employeeContractSBVO = getEmployeeContractVO( importCellDataDTO, contractId, strb, errorCount );

                  // ���CorpId
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

                  // �����ԱId
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

                  // �����Ա������
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

                  // �����ԱӢ����
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

                  // ����ͻ����
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

                  // ����ͻ�������
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

                  // ����ͻ�Ӣ����
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

                  // �����Ӷ״̬
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

                  // ���䶩��ID
                  if ( orderIdCellData == null )
                  {
                     orderIdCellData = new CellData();
                     addColumnInfo( orderIdCellData, "orderId" );
                     orderIdCellData.setJdbcDataType( JDBCDataType.INT );
                     orderIdCellData.setDbValue( employeeContractVO.getOrderId() );
                     importCellDataDTO.getCellDatas().add( orderIdCellData );
                  }

                  // ���䷨��ʵ��ID
                  if ( entityIdCellData == null )
                  {
                     entityIdCellData = new CellData();
                     addColumnInfo( entityIdCellData, "entityId" );
                     entityIdCellData.setJdbcDataType( JDBCDataType.INT );
                     entityIdCellData.setDbValue( employeeContractVO.getEntityId() );
                     importCellDataDTO.getCellDatas().add( entityIdCellData );
                  }

                  // ����ҵ������ID
                  /*if ( businessTypeIdCellData == null )
                  {
                     businessTypeIdCellData = new CellData();
                     addColumnInfo( businessTypeIdCellData, "businessTypeId" );
                     businessTypeIdCellData.setJdbcDataType( JDBCDataType.INT );
                     businessTypeIdCellData.setDbValue( employeeContractVO.getBusinessTypeId() );
                     importCellDataDTO.getCellDatas().add( businessTypeIdCellData );
                  }*/
               }

               // �����Ŀ��Ϣ
               if ( importCellDataDTO.getSubCellDataDTOs() != null )
               {
                  // ���ӱ����ݣ��Ƿ���Ϊ�ǡ���Ϣ���͡����ɽ𡱶�Ҫ��� Celldata��
                  errorCount += verifySubCellDataDTO( importCellDataDTO, monthly, employeeContractSBVO, contractId, strb );
               }

               // ��Ӵӱ������ֶ���Ϣ
               if ( importCellDataDTO.getSubCellDataDTOs() != null )
               {
                  for ( CellDataDTO subImportCellDataDTO : importCellDataDTO.getSubCellDataDTOs() )
                  {
                     try
                     {
                        // У�����ݲ����ɷ��ص� CellDateDTO ����
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

   // CellData�������ֵ
   private void addColumnInfo( final CellData cellData, final String nameDB )
   {
      final ColumnVO columnVO = new ColumnVO();
      columnVO.setNameDB( nameDB );
      columnVO.setIsForeignKey( "2" );
      columnVO.setAccountId( "1" );
      columnVO.setIsDBColumn( "1" );

      cellData.setColumnVO( columnVO );
   }

   // ���ӱ����ݣ��Ƿ���Ϊ�ǡ���Ϣ���͡����ɽ𡱶�Ҫ��� Celldata��
   private int verifySubCellDataDTO( final CellDataDTO importCellDataDTO, final String monthly, EmployeeContractSBVO employeeContractSBVO, final String contractId,
         StringBuffer strb )
   {
      int errorCount = 0;

      List< CellDataDTO > subCellDataDTOs = importCellDataDTO.getSubCellDataDTOs();

      if ( subCellDataDTOs.size() > 1 )
      {
         // ��ʼ�������·�
         String accountMonthly = getAccountMonthly( importCellDataDTO );

         Iterator< CellDataDTO > subCellDataDTOsIte = subCellDataDTOs.iterator();

         while ( subCellDataDTOsIte.hasNext() )
         {
            final CellDataDTO subCellDataDTO = subCellDataDTOsIte.next();
            List< CellData > subCellDatas = subCellDataDTO.getCellDatas();

            if ( subCellDatas != null && subCellDatas.size() > 0 )
            {
               // ��ʼ��Ҫ��ӵ�CellData����
               List< CellData > tempCellDatas = new ArrayList< CellData >();

               for ( CellData cellData : subCellDatas )
               {
                  final ColumnVO columnVO = cellData.getColumnVO();

                  // ����ǲ�������
                  if ( "��������".equals( columnVO.getNameZH() ) )
                  {
                     continue;
                  }

                  if ( ( "��Ϣ".equals( columnVO.getNameZH() ) ) || ( "���ɽ�".equals( columnVO.getNameZH() ) ) )
                  {
                     if ( Double.parseDouble( KANUtil.filterEmpty( cellData.getDbValue() ) ) == 0 )
                     {
                        subCellDataDTOsIte.remove();
                        continue;
                     }
                     else
                     {
                        // �洢 �籣���� ��ֵ
                        final Object amountCompanyDbValue = cellData.getDbValue();

                        // ��ӹ�˾�ϼ� CellDate
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

                        // ��Ӹ��˺ϼ� CellDate
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

                        // ��Ӳ����·� CellDate
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

   // ��ò����·�
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

   // ����籣����ID
   private EmployeeContractSBVO getEmployeeContractVO( final CellDataDTO importCellDataDTO, final String contractId, StringBuffer strb, int errorCount )
   {
      // ���accountId��corpId
      final String accountId = importCellDataDTO.getAccounId();
      final String corpId = importCellDataDTO.getCorpId();
      final List< CellDataDTO > subCellDataDTOs = importCellDataDTO.getSubCellDataDTOs();

      if ( subCellDataDTOs != null && subCellDataDTOs.size() > 0 )
      {
         for ( CellDataDTO cellDataDTO : subCellDataDTOs )
         {
            CellData cellData = cellDataDTO.getCellDataByColumnNameDB( "nameZH" );

            if ( "��������".equals( cellData.getColumnVO().getNameZH() ) )
            {
               // ��ÿ�Ŀ����
               String itemNameZH = ( String ) cellData.getDbValue();

               if ( itemNameZH != null )
               {
                  // ���Item��Ϣ
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
                        /** ��ѯ����Э�����Ƿ����籣���� */
                        // ��ʼ����ѯ����
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
                              strb.append( "��" + ( importCellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "������Э��[ID:" + contractId + "]û����Ч���籣������</br>" );
                           }
                           else
                           {
                              // ��ʼ���������ֵ������籣����
                              List< EmployeeContractSBVO > tempEmployeeContractSBVOs = new ArrayList< EmployeeContractSBVO >();

                              for ( Object object : employeeContractSBVOs )
                              {
                                 EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) object;
                                 final String sbSolutionId = tempEmployeeContractSBVO.getSbSolutionId();

                                 // �鿴��ǰ����������Э�����е��籣�������Ƿ����
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
                                    // ���������籣��������
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
                              // ��ƥ�����Ҹ��籣����
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
                        strb.append( "��" + ( importCellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "������Ϊ:[" + itemNameZH + "]���籣���֡�</br>" );
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
      // ���accountId��corpId
      final String accountId = importCellDataDTO.getAccounId();
      final String corpId = importCellDataDTO.getCorpId();

      List< CellData > cellDatas = subImportCellDataDTO.getCellDatas();
      List< CellData > addCellDatas = new ArrayList< CellData >();

      if ( cellDatas != null && cellDatas.size() > 0 )
      {
         ColumnVO foreignKeyCellColumnVO = new ColumnVO();
         foreignKeyCellColumnVO.setNameDB( "headerId" );
         foreignKeyCellColumnVO.setIsForeignKey( "1" );
         foreignKeyCellColumnVO.setAccountId( "1" );//1 1�������ݿ��ֶΣ�����������account���Զ����ֶ�
         foreignKeyCellColumnVO.setIsDBColumn( "1" );

         CellData foreignKeyCellData = new CellData();
         foreignKeyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
         foreignKeyCellData.setColumnVO( foreignKeyCellColumnVO );
         addCellDatas.add( foreignKeyCellData );
         cellDatas.addAll( addCellDatas );

         for ( CellData cellData : cellDatas )
         {
            final ColumnVO columnVO = cellData.getColumnVO();
            // �����Ӧ���ǡ��������֡�
            if ( "nameZH".equals( columnVO.getNameDB() ) )
            {
               // ��ÿ�Ŀ����
               String itemNameZH = ( String ) cellData.getDbValue();

               if ( itemNameZH != null )
               {
                  // ���Item��Ϣ
                  if ( KANConstants.getKANAccountConstants( accountId ) != null )
                  {
                     final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemName( itemNameZH, corpId );

                     if ( itemVO != null )
                     {
                        // ��ӿ�Ŀ����
                        errorCount += fetchReturnCellDateDTOs( returnCellDateDTOs, importCellDataDTO, subImportCellDataDTO, itemVO, cellData, contractId, monthly, employeeContractSBVO, strb );
                        return errorCount;
                     }
                     else
                     {
                        strb.append( "��" + ( importCellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "��δ�ҵ�����Ϊ���� " + ( String ) cellData.getDbValue() + "�����籣���֡�</br>" );
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

   // ������շ��ص�CellDataDTO ����
   private int fetchReturnCellDateDTOs( List< CellDataDTO > returnCellDateDTOs, CellDataDTO importCellDataDTO, CellDataDTO subImportCellDataDTO, ItemVO itemVO, CellData cellData,
         String contractId, String monthly, EmployeeContractSBVO employeeContractSBVO, StringBuffer strb ) throws KANException
   {
      int errorCount = 0;

      if ( returnCellDateDTOs != null && returnCellDateDTOs.size() > 0 )
      {
         for ( CellDataDTO existcellDataDTO : returnCellDateDTOs )
         {
            // ���ָ�� ContractId �� ָ�������·�������
            if ( contractId.equals( existcellDataDTO.getCellDataDbValueByColumnNameDB( "contractId" ) )
                  && monthly.equals( existcellDataDTO.getCellDataDbValueByColumnNameDB( "monthly" ) ) )
            {
               // ��ÿ�Ŀ��Ӧ�Ĳ����·�
               final List< CellDataDTO > subCellDataDTOs = existcellDataDTO.getSubCellDataDTOs();

               for ( CellDataDTO existSubcellDataDTO : subCellDataDTOs )
               {
                  // �����Ŀ�����Ҳ����·�ƥ��
                  if ( itemVO.getItemId().equals( existSubcellDataDTO.getCellDataDbValueByColumnNameDB( "itemId" ) )
                        && subImportCellDataDTO.getCellDataDbValueByColumnNameDB( "accountMonthly" ).equals( existSubcellDataDTO.getCellDataDbValueByColumnNameDB( "accountMonthly" ) ) )
                  {
                     // ������ǡ����ɽ𡱻��ߡ���Ϣ�� - ����
                     if ( !"77".equals( itemVO.getItemId() ) && !"78".equals( itemVO.getItemId() ) )
                     {
                        strb.append( "��" + ( cellData.getRow() + 1 ) + "���籣����[" + itemVO.getNameZH() + "]�ظ����֡�</br>" );
                        errorCount++;
                        return errorCount;
                     }
                     // ����ǡ����ɽ𡱻��ߡ���Ϣ�� - ���
                     else
                     {
                        existSubcellDataDTO.addCellDataDbValueByColumnNameDB( "amountCompany", ( String ) subImportCellDataDTO.getCellDataDbValueByColumnNameDB( "amountCompany" ) );
                        existSubcellDataDTO.addCellDataDbValueByColumnNameDB( "amountPersonal", ( String ) subImportCellDataDTO.getCellDataDbValueByColumnNameDB( "amountPersonal" ) );
                        return errorCount;
                     }

                  }
               }

               // �����Ŀ�����ڣ�����SubImportCellDataDTO
               addsubCellDate( subImportCellDataDTO, itemVO, monthly );
               existcellDataDTO.getSubCellDataDTOs().add( subImportCellDataDTO );
               return errorCount;
            }
         }

         addsubCellDate( subImportCellDataDTO, itemVO, monthly );
         // ��ʼ��Ҫ������CellDataDTO
         CellDataDTO addcellDataDTO = new CellDataDTO();

         addcellDataDTO.getCellDatas().addAll( importCellDataDTO.getCellDatas() );
         addcellDataDTO.setTableId( importCellDataDTO.getTableId() );
         addcellDataDTO.setTableName( importCellDataDTO.getTableName() );
         addcellDataDTO.getSubCellDataDTOs().add( subImportCellDataDTO );

         // ����CellDataDTO��Ϣ
         addCellDataDTOExtraInfo( addcellDataDTO, contractId, employeeContractSBVO );

         returnCellDateDTOs.add( addcellDataDTO );
         return errorCount;
      }
      addsubCellDate( subImportCellDataDTO, itemVO, monthly );

      // ��ʼ��Ҫ������CellDataDTO
      CellDataDTO addcellDataDTO = new CellDataDTO();

      addcellDataDTO.getCellDatas().addAll( importCellDataDTO.getCellDatas() );
      addcellDataDTO.setTableId( importCellDataDTO.getTableId() );
      addcellDataDTO.setTableName( importCellDataDTO.getTableName() );
      addcellDataDTO.getSubCellDataDTOs().add( subImportCellDataDTO );

      // ����CellDataDTO��Ϣ
      addCellDataDTOExtraInfo( addcellDataDTO, contractId, employeeContractSBVO );

      returnCellDateDTOs.add( addcellDataDTO );
      return errorCount;
   }

   // ����CellDataDTO��Ϣ
   private void addCellDataDTOExtraInfo( final CellDataDTO addCellDataDTO, final String contractId, final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      // ��Ҫ����Ĵӱ��ֶ�
      ColumnVO tempStatusCellColumnVO = new ColumnVO();
      tempStatusCellColumnVO.setNameDB( "tempStatus" );
      tempStatusCellColumnVO.setIsForeignKey( "2" );
      tempStatusCellColumnVO.setAccountId( "1" );
      tempStatusCellColumnVO.setIsDBColumn( "1" );

      CellData tempStatusCellData = new CellData();
      tempStatusCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
      tempStatusCellData.setColumnVO( tempStatusCellColumnVO );

      // ����״̬Ĭ��Ϊ���½���
      tempStatusCellData.setDbValue( "1" );
      addCellDataDTO.getCellDatas().add( tempStatusCellData );

      if ( employeeContractSBVO != null )
      {
         /** �����籣������Ϣ */
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

         // �ӱ�����
         CellData startDateCellData = new CellData();
         addColumnInfo( startDateCellData, "startDate" );
         startDateCellData.setJdbcDataType( JDBCDataType.DATE );
         startDateCellData.setDbValue( employeeContractSBVO.getStartDate() );
         addCellDataDTO.getCellDatas().add( startDateCellData );

         // �˱�����
         CellData endDateCellData = new CellData();
         addColumnInfo( endDateCellData, "endDate" );
         endDateCellData.setJdbcDataType( JDBCDataType.DATE );
         endDateCellData.setDbValue( employeeContractSBVO.getEndDate() );
         addCellDataDTO.getCellDatas().add( endDateCellData );

         // ��Ҫ����ҽ����
         CellData needMedicalCardCellData = new CellData();
         addColumnInfo( needMedicalCardCellData, "needMedicalCard" );
         needMedicalCardCellData.setJdbcDataType( JDBCDataType.INT );
         needMedicalCardCellData.setDbValue( employeeContractSBVO.getNeedMedicalCard() );
         addCellDataDTO.getCellDatas().add( needMedicalCardCellData );

         // ��Ҫ�����籣��
         CellData needSBCardCellData = new CellData();
         addColumnInfo( needSBCardCellData, "needSBCard" );
         needSBCardCellData.setJdbcDataType( JDBCDataType.INT );
         needSBCardCellData.setDbValue( employeeContractSBVO.getNeedSBCard() );
         addCellDataDTO.getCellDatas().add( needSBCardCellData );

         // ҽ�����ʺ�
         CellData medicalNumberCellData = new CellData();
         addColumnInfo( medicalNumberCellData, "medicalNumber" );
         medicalNumberCellData.setJdbcDataType( JDBCDataType.INT );
         medicalNumberCellData.setDbValue( employeeContractSBVO.getMedicalNumber() );
         addCellDataDTO.getCellDatas().add( medicalNumberCellData );

         // �籣���ʺ�
         CellData sbNumberCellData = addCellDataDTO.getCellDataByColumnNameDB( "sbNumber" );
         sbNumberCellData.setDbValue( employeeContractSBVO.getSbNumber() );

         // �������ʺ�
         CellData fundNumberCellData = new CellData();
         addColumnInfo( fundNumberCellData, "fundNumber" );
         fundNumberCellData.setJdbcDataType( JDBCDataType.INT );
         fundNumberCellData.setDbValue( employeeContractSBVO.getFundNumber() );
         addCellDataDTO.getCellDatas().add( fundNumberCellData );
      }
   }

   // ���SubCellDate��Ϣ
   private void addsubCellDate( final CellDataDTO subImportCellDataDTO, final ItemVO itemVO, final String monthly )
   {
      // ��Ҫ����Ĵӱ��ֶ�
      ColumnVO tempStatusCellColumnVO = new ColumnVO();
      tempStatusCellColumnVO.setNameDB( "tempStatus" );
      tempStatusCellColumnVO.setIsForeignKey( "2" );
      tempStatusCellColumnVO.setAccountId( "1" );
      tempStatusCellColumnVO.setIsDBColumn( "1" );

      CellData tempStatusCellData = new CellData();
      tempStatusCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
      tempStatusCellData.setColumnVO( tempStatusCellColumnVO );

      // ����״̬Ĭ��Ϊ���½���
      tempStatusCellData.setDbValue( "1" );
      subImportCellDataDTO.getCellDatas().add( tempStatusCellData );

      // ��Ӳ����·� CellDate
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

      // ���������Ϣ
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
         // �������
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
