package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.kan.base.dao.inf.management.PositionDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

public class EmployeeContractImportHandlerImpl implements ExcelImportHandler< List< CellDataDTO > >
{
   // ��ʼ��EmployeeDao
   private EmployeeDao employeeDao;

   // ��ʼ��ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ��ʼ��EmployeeContractTempDao
   private EmployeeContractTempDao employeeContractTempDao;

   private EmployeeContractDao employeeContractDao;

   private PositionDao positionDao;

   private StaffDao staffDao;

   // ��ʼ��AccountId
   private String ACCOUNT_ID;

   // ��ʼ��CorpId
   private String CORP_ID;

   private EmployeeContractService employeeContractService;

   @Override
   // Reviewed by Kevin Jin at 2014-05-07
   public void init( List< CellDataDTO > cellDataDTOs )
   {
      // ���������ݱ��Ϊ��ʱ��
      if ( cellDataDTOs != null )
      {
         for ( CellDataDTO cellDataDTO : cellDataDTOs )
         {
            cellDataDTO.setTableName( cellDataDTO.getTableName() + "_TEMP" );

            if ( cellDataDTO.getSubCellDataDTOs() == null )
            {
               continue;
            }

            for ( CellDataDTO subcellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               subcellDataDTO.setTableName( subcellDataDTO.getTableName() + "_TEMP" );
            }
         }
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2014-05-07
   public boolean excuteBeforInsert( final List< CellDataDTO > cellDataDTOs, final HttpServletRequest request )
   {
      boolean flag = true;

      if ( cellDataDTOs != null && cellDataDTOs.size() > 0 )
      {
         // ���ӱ�tableName
         String sbTableName = "hro_biz_employee_contract_sb_temp";
         String salaryTableName = "hro_biz_employee_contract_salary_temp";
         String cbTableName = "hro_biz_employee_contract_cb_temp";
         String leaveTableName = "hro_biz_employee_contract_leave_temp";
         String otTableName = "hro_biz_employee_contract_ot_temp";
         String otherTableName = "hro_biz_employee_contract_other_temp";

         try
         {
            // ��ʼ��AccountId
            ACCOUNT_ID = BaseAction.getAccountId( request, null );
            // ��ʼ��CorpId
            CORP_ID = BaseAction.getCorpId( request, null );
         }
         catch ( final KANException e )
         {
            e.printStackTrace();
         }

         // ��ʼ��KANAccountConstants
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( ACCOUNT_ID );
         // ��ʼ��ItemVO�б�
         final List< ItemVO > itemVOs = accountConstants.getItemVOsByCorpId( CORP_ID );

         for ( CellDataDTO cellDataDTO : cellDataDTOs )
         {
            // ��ʼ���Ƿ���� ContractId
            boolean contractIdExist = false;

            final CellData contractIdCellData = cellDataDTO.getCellDataByColumnNameDB( "contractId" );

            if ( contractIdCellData != null && contractIdCellData.getDbValue() != null )
            {
               contractIdExist = true;

               // ��֤contractId ��Ч��
               try
               {
                  final EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( ( String ) contractIdCellData.getDbValue() );

                  if ( employeeContractVO == null )
                  {
                     flag = false;
                     contractIdCellData.setErrorMessange( "��Ԫ��[" + contractIdCellData.getCellRef() + "]" + contractIdCellData.getColumnVO().getNameZH() + "��ֵ["
                           + contractIdCellData.getDbValue() + "]��Ч��" );
                  }
                  else
                  {
                     // ����Խ��˺ͶԽӲ���
                     if ( !fetchOwnerAndBranchInfo( cellDataDTO, request, employeeContractVO.getBranch(), employeeContractVO.getOwner() ) )
                     {
                        flag = false;
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
               // ����Խ��˺ͶԽӲ���
               try
               {
                  if ( !fetchOwnerAndBranchInfo( cellDataDTO, request, null, null ) )
                  {
                     flag = false;
                  }
               }
               catch ( KANException e )
               {
                  e.printStackTrace();
               }
            }

            // �ж�
            try
            {
               if ( flag )
               {
                  flag = arrangeCellData( cellDataDTO, request );
               }
               else
               {
                  arrangeCellData( cellDataDTO, request );
               }

               // ������ں�ͬ��Ų�����֤ʱ����ڴ���������ͬ
               if ( !contractIdExist && flag )
               {
                  // ���ʱ������Ƿ���������Ч��ͬ
                  EmployeeContractVO employeeContractVO = new EmployeeContractVO();

                  employeeContractVO.setAccountId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "accountId" ) );
                  employeeContractVO.setCorpId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "corpId" ) );
                  employeeContractVO.setClientId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "clientId" ) );
                  employeeContractVO.setEmployeeId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "employeeId" ) );
                  employeeContractVO.setEntityId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "entityId" ) );
                  employeeContractVO.setFlag( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "flag" ) );
                  employeeContractVO.setStartDate( KANUtil.formatDate( cellDataDTO.getCellDataDbValueByColumnNameDB( "startDate" ), "yyyy-MM-dd" ) );
                  employeeContractVO.setEndDate( KANUtil.formatDate( cellDataDTO.getCellDataDbValueByColumnNameDB( "endDate" ), "yyyy-MM-dd" ) );

                  if ( employeeContractService.checkContractConflict( employeeContractVO ) )
                  {
                     flag = false;
                     if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                           || ( KANUtil.filterEmpty( BaseAction.getRole( request, null ) ) != null && KANUtil.filterEmpty( BaseAction.getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
                     {
                        cellDataDTO.getCellDatas().get( 0 ).setErrorMessange( "��" + ( cellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "�к�ͬʱ������Ѵ����Ͷ���ͬ" );
                     }
                     else
                     {
                        cellDataDTO.getCellDatas().get( 0 ).setErrorMessange( "��" + ( cellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "�к�ͬʱ������Ѵ���������Ϣ" );
                     }
                  }

               }
            }
            catch ( final KANException e )
            {
               e.printStackTrace();
            }

            // ��ʼ����ʼʱ�䣬����ʱ��
            final String startDate = getStartDate( cellDataDTO );
            final String endDate = getEndDate( cellDataDTO );
            final String orderId = getOrderId( cellDataDTO );

            //            // ��֤�����ͬ�Ŀ�ʼʱ���Ƿ��ڽ���ʱ��֮ǰ
            //            if ( KANUtil.getDays( KANUtil.createDate( employeeContractVO.getStartDate() ) ) >= KANUtil.getDays( KANUtil.createDate( tempContractEndDate ) )
            //                  || KANUtil.getDays( KANUtil.createDate( employeeContractVO.getEndDate() ) ) <= KANUtil.getDays( KANUtil.createDate( tempContractStartDate ) ) )
            //            {
            //
            //            }

            if ( flag && cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
            {
               final ColumnVO foreignKeyCellColumnVO = new ColumnVO();
               foreignKeyCellColumnVO.setNameDB( "contractId" );
               foreignKeyCellColumnVO.setIsForeignKey( "1" );
               foreignKeyCellColumnVO.setAccountId( "1" );
               foreignKeyCellColumnVO.setIsDBColumn( "1" );

               final CellData foreignKeyCellData = new CellData();
               foreignKeyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               foreignKeyCellData.setColumnVO( foreignKeyCellColumnVO );

               final Iterator< CellDataDTO > iterators = cellDataDTO.getSubCellDataDTOs().iterator();

               List< CellDataDTO > removeCellDataDTOs = new ArrayList< CellDataDTO >();
               while ( iterators.hasNext() )
               {
                  CellDataDTO subCellDataDTO = iterators.next();
                  if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ) != null && subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
                  {
                     // �籣�����ӱ�
                     if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( sbTableName ) )
                     {
                        boolean havesb = fetchSbCellDataDTO( subCellDataDTO, cellDataDTO.getSubCellDataDTOs() );

                        // ��������ֶ��ظ�
                        if ( havesb && subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }

                        if ( !havesb && KANUtil.filterEmpty( subCellDataDTO.getErrorMessange() ) != null )
                        {
                           flag = false;
                        }

                     }
                     // н�귽���ӱ�
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( salaryTableName ) )
                     {
                        CellData baseCellData = subCellDataDTO.getCellDataByColumnNameDB( "base" );
                        if ( baseCellData != null && ( baseCellData.getValue() == null || "".equals( baseCellData.getValue() ) ) )
                        {
                           removeCellDataDTOs.add( subCellDataDTO );
                        }
                        else
                        {
                           fetchSalaryCellDataDTO( subCellDataDTO, itemVOs, startDate, endDate, orderId );
                           // ��������ֶ��ظ�
                           if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                           {
                              subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                           }
                        }
                     }
                     // �̱����� 
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( cbTableName ) )
                     {
                        fetchCbCellDataDTO( subCellDataDTO );

                        // ��������ֶ��ظ�
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                     // ��ٷ���
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( leaveTableName ) )
                     {
                        addLeaveTableColumns( subCellDataDTO, itemVOs );

                        // ��������ֶ��ظ�
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                     // �Ӱ෽��
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( otTableName ) )
                     {
                        addOtTableColumns( subCellDataDTO, itemVOs );

                        // ��������ֶ��ظ�
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                     // ��������
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( otherTableName ) )
                     {
                        addOtherTableColumns( subCellDataDTO, itemVOs );

                        // ��������ֶ��ظ�
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                  }
               }

               if ( removeCellDataDTOs != null && removeCellDataDTOs.size() > 0 )
                  cellDataDTO.getSubCellDataDTOs().removeAll( removeCellDataDTOs );
            }
         }
      }

      return flag;
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      try
      {
         // ����OrderId�����Ͷ���ͬ��ʱ���еĿͻ�Id
         this.getEmployeeContractTempDao().updateEmployeeContractClientIdFromOrderIdByBatchId( batchId );
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      return false;
   }

   // Reviewed by Kevin Jin at 2014-05-07
   private boolean arrangeCellData( final CellDataDTO cellDataDTO, final HttpServletRequest request ) throws KANException
   {
      // ��ʼ��CellData�б�
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();
      // ��ʼ��KANAccountConstants
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( ACCOUNT_ID );
      // ��ʼ��PositionVO
      final PositionVO positionVO = accountConstants.getPositionVOByPositionId( BaseAction.getPositionId( request, null ) );

      final com.kan.base.domain.management.PositionVO mgtposition = new com.kan.base.domain.management.PositionVO();
      mgtposition.setAccountId( ACCOUNT_ID );
      final List< Object > positionList = positionDao.getPositionVOsByCondition( mgtposition );
      // ��ʶ
      boolean flag = true;
      // Row
      //      int row = 0;

      if ( cellDatas != null )
      {
         // ������Ӧ��Ԫ��
         CellData primaryKeyCellData = null;
         CellData employeeIdCellData = null;
         String nameOfEmployeeId = "Ա��ϵͳ���";
         String nameOfCertificateNumber = "���֤��";
         String employeeId = null;
         // ���֤����
         String employeeCertificateNumber = null;

         // ��Ա��������
         String employeeNameZH = null;

         // ��ԱӢ������
         String employeeNameEN = null;

         // ��Ա��������
         String residencyType = null;

         // ��Ա��ϵ�绰
         String phone1 = null;

         // ��Ա������ַ
         String residencyAddress = null;

         // ҵ������������
         String businessTypeNameZH = null;

         // ҵ������Ӣ����
         String businessTypeNameEN = null;

         // ����Client Id
         final CellData clientIdCellData = new CellData();

         // ����Entity Id
         final CellData entityIdCellData = new CellData();

         // ����ҵ������
         CellData businessTypeIdCellData = new CellData();
         // ��ʱҵ������
         final CellData businessTypeIdCellData_Temp = new CellData();

         for ( CellData cellData : cellDatas )
         {
            if ( cellData != null && cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null )
            {
               // ��������
               if ( KANUtil.filterEmpty( cellData.getColumnVO().getIsPrimaryKey() ) != null && KANUtil.filterEmpty( cellData.getColumnVO().getIsPrimaryKey() ).equals( "1" ) )
               {
                  primaryKeyCellData = cellData;
               }
               // OrderId����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "orderId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // ��ʼ��OrderId
                     String orderId = ( String ) cellData.getValue();

                     if ( KANUtil.filterEmpty( orderId ) != null && KANUtil.filterEmpty( orderId ).matches( "[0-9]+" ) )
                     {
                        cellData.setDbValue( orderId );

                        try
                        {
                           final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( orderId );

                           if ( clientOrderHeaderVO != null )
                           {
                              // ����ClientId
                              final ColumnVO clientIdColumnVO = new ColumnVO();
                              clientIdColumnVO.setNameDB( "clientId" );
                              clientIdColumnVO.setIsForeignKey( "2" );
                              clientIdColumnVO.setAccountId( "1" );
                              clientIdColumnVO.setIsDBColumn( "1" );

                              clientIdCellData.setColumnVO( clientIdColumnVO );
                              clientIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                              clientIdCellData.setDbValue( clientOrderHeaderVO.getClientId() );

                              // ����EntityId
                              final ColumnVO entityIdColumnVO = new ColumnVO();
                              entityIdColumnVO.setNameDB( "entityId" );
                              entityIdColumnVO.setIsForeignKey( "2" );
                              entityIdColumnVO.setAccountId( "1" );
                              entityIdColumnVO.setIsDBColumn( "1" );

                              entityIdCellData.setColumnVO( entityIdColumnVO );
                              entityIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                              entityIdCellData.setDbValue( clientOrderHeaderVO.getEntityId() );

                              // ����BusinessTypeId
                              final ColumnVO businessTypeIdColumnVO = new ColumnVO();
                              businessTypeIdColumnVO.setNameDB( "businessTypeId" );
                              businessTypeIdColumnVO.setIsForeignKey( "2" );
                              businessTypeIdColumnVO.setAccountId( "1" );
                              businessTypeIdColumnVO.setIsDBColumn( "1" );

                              businessTypeIdCellData_Temp.setColumnVO( businessTypeIdColumnVO );
                              businessTypeIdCellData_Temp.setJdbcDataType( JDBCDataType.VARCHAR );
                              businessTypeIdCellData_Temp.setDbValue( clientOrderHeaderVO.getBusinessTypeId() );
                           }
                           //�����Ų�����
                           else
                           {
                              cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ[" + cellData.getValue() + "]��Ч�������ڸö���" );
                              flag = false;
                           }
                        }
                        catch ( final Exception e )
                        {
                           e.printStackTrace();
                        }
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ[" + cellData.getValue() + "]��Ч" );
                        flag = false;
                     }
                  }
                  else
                  {
                     cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ����Ϊ��" );
                     flag = false;
                  }
               }
               // EmployeeId����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "employeeId" ) )
               {
                  employeeIdCellData = cellData;
                  nameOfEmployeeId = cellData.getColumnVO().getNameZH();

                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     employeeId = cellData.getValue().trim();
                  }
               }
               // Certificate Number����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "certificateNumber" ) )
               {
                  nameOfCertificateNumber = cellData.getColumnVO().getNameZH();

                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     if ( "���֤����".equals( nameOfCertificateNumber ) && !com.kan.base.util.IDCard.validateCard( cellData.getValue().trim() ) )
                     {
                        flag = false;
                        cellData.setErrorMessange( "��" + String.valueOf( cellData.getRow() + 1 ) + "��[���֤����]��������" );
                     }
                     else
                     {
                        employeeCertificateNumber = cellData.getValue();
                     }
                  }
               }
               // Employee Name ZH����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "employeeNameZH" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     employeeNameZH = cellData.getValue();
                  }
               }
               // Employee Name EN����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "employeeNameEN" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     employeeNameEN = cellData.getValue();
                  }
               }
               // Employee residency Type ����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "residencyType" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     residencyType = cellData.getDbValue().toString();
                  }
               }
               // Employee phone1����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "phone1" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     phone1 = cellData.getValue();
                  }
               }
               // Employee residency Address����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "residencyAddress" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     residencyAddress = cellData.getValue();
                  }
               }
               // Entity Id����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "entityId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // ��ʼ��EntityVO
                     final EntityVO entityVO = accountConstants.getEntityVOByEntityName( cellData.getValue().trim(), CORP_ID );

                     if ( entityVO != null )
                     {
                        cellData.setDbValue( entityVO.getEntityId() );
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ[" + cellData.getValue() + "]��Ч�������ڸ÷���ʵ��" );
                        flag = false;
                     }
                  }
               }
               // Business Type Id
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "businessTypeId" ) )
               {
                  businessTypeIdCellData = cellData;
                  // ��֤ҵ�����͵���Ч��
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // ��ʼ��BusinessTypeVO
                     final BusinessTypeVO businessTypeVO = accountConstants.getBusinessTypeByName( cellData.getValue().trim(), CORP_ID );

                     if ( businessTypeVO != null )
                     {
                        cellData.setDbValue( businessTypeVO.getBusinessTypeId() );
                        businessTypeNameZH = businessTypeVO.getNameZH();
                        businessTypeNameEN = businessTypeVO.getNameEN();
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ[" + cellData.getValue() + "]��Ч�������ڸ�ҵ������" );
                        flag = false;
                     }
                  }
               }
               // Template Id
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "templateId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // ��ʼ��LaborContractTemplateVO
                     final LaborContractTemplateVO laborContractTemplateVO = accountConstants.getLaborContractTemplatesByName( cellData.getValue().trim(), CORP_ID );

                     if ( laborContractTemplateVO != null )
                     {
                        cellData.setDbValue( laborContractTemplateVO.getTemplateId() );
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ[" + cellData.getValue() + "]��Ч�������ڸ��Ͷ���ͬģ��" );
                        flag = false;
                     }
                  }
                  else
                  {
                     cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ����Ϊ��" );
                     flag = false;
                  }
               }
               // �ɱ�����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "settlementBranch" ) )
               {
                  // ����Լ�����Ĳ�������
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     if ( KANUtil.filterEmpty( cellData.getDbValue(), "0" ) == null )
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ[" + cellData.getValue() + "]��Ч�������ڶ�Ӧ�Ĳ���" );
                        flag = false;
                     }
                  }
                  else
                  {
                     cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ����Ϊ��" );
                     flag = false;
                  }
               }
               // positionName
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "positionName" )
                     || KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "positionId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // ��ʼ��LaborContractTemplateVO

                     if ( positionList != null && positionList.size() > 0 )
                     {
                        boolean haspositionId = false;
                        for ( Object obj : positionList )
                        {
                           com.kan.base.domain.management.PositionVO mgtpositionVO = ( com.kan.base.domain.management.PositionVO ) obj;
                           if ( mgtpositionVO != null && ( cellData.getValue().equals( mgtpositionVO.getTitleZH() ) || cellData.getValue().equals( mgtpositionVO.getTitleEN() ) ) )
                           {

                              // ����positionId
                              final ColumnVO positionIdColumnVO = cellData.getColumnVO();
                              positionIdColumnVO.setNameDB( "positionId" );
                              positionIdColumnVO.setIsForeignKey( "2" );
                              positionIdColumnVO.setAccountId( "1" );
                              positionIdColumnVO.setIsDBColumn( "1" );

                              cellData.setJdbcDataType( JDBCDataType.INT );
                              cellData.setColumnVO( positionIdColumnVO );
                              cellData.setDbValue( mgtpositionVO.getPositionId() );
                              haspositionId = true;
                              break;
                           }
                        }

                        if ( !haspositionId )
                        {
                           cellData.setErrorMessange( "[" + cellData.getCellRef() + "]��ֵ" + cellData.getValue() + "��Ч�������ڸ�ְλ��Ϣ" );
                           flag = false;
                        }
                     }
                     else
                     {
                        cellData.setErrorMessange( "ϵͳû���趨ְλ��Ϣ����������趨��" );
                     }
                  }
               }
               // Contract Name ZH����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "nameZH" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) == null )
                  {
                     cellData.setDbValue( ( KANUtil.filterEmpty( businessTypeNameZH ) != null ? businessTypeNameZH : "" ) + ( KANUtil.filterEmpty( CORP_ID ) != null ? "��ͬ" : "Э��" )
                           + ( KANUtil.filterEmpty( employeeNameZH ) != null ? ( "��" + employeeNameZH ) + "��" : "" ) + " - " + KANUtil.getMonthly( new Date(), "" ) );
                  }
               }
               // Contract Name EN����
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "nameEN" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) == null )
                  {
                     cellData.setDbValue( ( KANUtil.filterEmpty( businessTypeNameEN ) != null ? businessTypeNameEN + " " : "" )
                           + ( KANUtil.filterEmpty( CORP_ID ) != null ? "Contract" : "Agreement" )
                           + ( KANUtil.filterEmpty( employeeNameEN ) != null ? ( " (" + businessTypeNameEN ) + ")" : "" ) + " - " + KANUtil.getMonthly( new Date(), "" ) );
                  }
               }
            }
         }

         // ����Client Id����������ֶ��ظ�
         if ( clientIdCellData.getColumnVO() != null && cellDataDTO.getCellDataByColumnNameDB( clientIdCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( clientIdCellData );
         }

         // ����Entity Id����������ֶ��ظ�
         if ( entityIdCellData.getColumnVO() != null && cellDataDTO.getCellDataByColumnNameDB( entityIdCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( entityIdCellData );
         }
         else if ( entityIdCellData.getColumnVO() != null
               && KANUtil.filterEmpty( cellDataDTO.getCellDataByColumnNameDB( entityIdCellData.getColumnVO().getNameDB() ).getDbValue() ) == null )
         {
            cellDataDTO.getCellDataByColumnNameDB( entityIdCellData.getColumnVO().getNameDB() ).setDbValue( entityIdCellData.getDbValue() );
         }

         // ����Business Type Id����������ֶ��ظ�
         if ( businessTypeIdCellData.getColumnVO() == null )
         {
            cellDatas.add( businessTypeIdCellData_Temp );
         }
         else
         {
            if ( KANUtil.filterEmpty( businessTypeIdCellData.getDbValue() ) == null && flag )
            {
               businessTypeIdCellData.setDbValue( businessTypeIdCellData_Temp.getDbValue() );
            }
         }

         if ( employeeId == null && employeeCertificateNumber == null )
         {
            cellDataDTO.setErrorMessange( "��" + ( employeeIdCellData.getRow() + 1 ) + "��[" + nameOfEmployeeId + "��" + nameOfCertificateNumber + "]����һ������Ϊ�ա�" );
            return false;
         }

         try
         {
            EmployeeVO employeeVO = null;

            if ( employeeId != null )
            {
               employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( employeeId );

               if ( employeeVO == null && employeeCertificateNumber == null )
               {
                  cellDataDTO.setErrorMessange( "��" + ( employeeIdCellData.getRow() + 1 ) + "��[" + nameOfEmployeeId + "��" + employeeId + "]��ӦԱ�������ڣ�[" + nameOfCertificateNumber
                        + "]��ֵ����Ϊ��" );
                  return false;
               }
            }

            if ( employeeVO == null && employeeCertificateNumber != null )
            {
               // ��ʼ��EmployeeVO��������
               EmployeeVO tempEmployeeVO = new EmployeeVO();
               tempEmployeeVO.setAccountId( ACCOUNT_ID );
               tempEmployeeVO.setCorpId( CORP_ID );
               tempEmployeeVO.setCertificateNumber( employeeCertificateNumber );
               // �����֤�����ȡԱ��
               final List< Object > employeeVOs = this.getEmployeeDao().getEmployeeVOsByCondition( tempEmployeeVO );

               if ( employeeVOs != null && employeeVOs.size() > 0 )
               {
                  employeeVO = ( EmployeeVO ) employeeVOs.get( 0 );
               }
            }

            if ( employeeVO == null )
            {
               employeeVO = new EmployeeVO();
               employeeVO.setAccountId( ACCOUNT_ID );
               employeeVO.setCorpId( CORP_ID );
               // ���֤
               employeeVO.setCertificateType( "1" );
               employeeVO.setCertificateNumber( employeeCertificateNumber );
               employeeVO.setNameZH( employeeNameZH );
               employeeVO.setResidencyAddress( residencyAddress );
               employeeVO.setResidencyType( residencyType );
               employeeVO.setPhone1( phone1 );
               this.getEmployeeDao().insertEmployee( employeeVO );

               // ���ж��Ƿ����һ���͹�Ա������Staff
               StaffVO staffVO = new StaffVO();
               staffVO = staffDao.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );
               if ( staffVO == null || staffVO.getStaffId() == null )
               {
                  // ����һ��staff
                  final StaffVO staffVO_new = new StaffVO();
                  staffVO_new.setEmployeeId( employeeVO.getEmployeeId() );
                  staffVO_new.setAccountId( employeeVO.getAccountId() );
                  staffVO_new.setClientId( employeeVO.getClientId() );
                  staffVO_new.setCorpId( employeeVO.getCorpId() );
                  staffVO_new.setNameZH( employeeVO.getNameZH() );
                  staffVO_new.setNameEN( employeeVO.getNameEN() );
                  staffVO_new.setStaffNo( employeeVO.getEmployeeNo() );
                  staffVO_new.setSalutation( employeeVO.getSalutation() );
                  staffVO_new.setBirthday( employeeVO.getBirthday() );
                  staffVO_new.setMaritalStatus( employeeVO.getStatus() );
                  staffVO_new.setRegistrationHometown( employeeVO.getResidencyCityId() );
                  staffVO_new.setRegistrationAddress( employeeVO.getRecordAddress() );
                  staffVO_new.setPersonalAddress( employeeVO.getPersonalAddress() );
                  staffVO_new.setPersonalPostcode( employeeVO.getPersonalPostcode() );
                  staffVO_new.setCertificateType( employeeVO.getCertificateType() );
                  staffVO_new.setCertificateNumber( employeeVO.getCertificateNumber() );
                  staffVO_new.setDescription( employeeVO.getDescription() );
                  staffVO_new.setBizEmail( employeeVO.getEmail1() );
                  staffVO_new.setBizMobile( employeeVO.getMobile1() );
                  staffVO_new.setPersonalEmail( employeeVO.getEmail2() );
                  staffVO_new.setPersonalMobile( employeeVO.getMobile2() );
                  staffVO_new.setStatus( "1" );
                  staffVO_new.setRemark1( employeeVO.getRemark1() );
                  staffVO_new.setRemark2( employeeVO.getRemark2() );
                  staffVO_new.setRemark3( employeeVO.getRemark3() );
                  staffVO_new.setCreateBy( employeeVO.getCreateBy() );
                  staffVO_new.setPositionIdArray( employeeVO.getPositionIdArray() );
                  staffVO_new.setCreateDate( new Date() );
                  staffDao.insertStaff( staffVO_new );
                  staffVO = staffVO_new;
               }
               else
               {
                  staffVO.setNameZH( employeeVO.getNameZH() );
                  staffVO.setNameEN( employeeVO.getNameEN() );
                  staffVO.setStaffNo( employeeVO.getEmployeeNo() );
                  staffVO.setSalutation( employeeVO.getSalutation() );
                  staffVO.setBirthday( employeeVO.getBirthday() );
                  staffVO.setMaritalStatus( employeeVO.getStatus() );
                  staffVO.setRegistrationHometown( employeeVO.getResidencyCityId() );
                  staffVO.setRegistrationAddress( employeeVO.getRecordAddress() );
                  staffVO.setPersonalAddress( employeeVO.getPersonalAddress() );
                  staffVO.setPersonalPostcode( employeeVO.getPersonalPostcode() );
                  staffVO.setCertificateType( employeeVO.getCertificateType() );
                  staffVO.setCertificateNumber( employeeVO.getCertificateNumber() );
                  staffVO.setDescription( employeeVO.getDescription() );
                  staffVO.setBizEmail( employeeVO.getEmail1() );
                  staffVO.setBizMobile( employeeVO.getMobile1() );
                  staffVO.setPersonalEmail( employeeVO.getEmail2() );
                  staffVO.setPersonalMobile( employeeVO.getMobile2() );
                  staffVO.setStatus( "1" );
                  staffVO.setRemark1( employeeVO.getRemark1() );
                  staffVO.setRemark2( employeeVO.getRemark2() );
                  staffVO.setRemark3( employeeVO.getRemark3() );
                  staffVO.setCreateBy( employeeVO.getCreateBy() );
                  staffVO.setPositionIdArray( employeeVO.getPositionIdArray() );
                  staffVO.setCreateDate( new Date() );
                  staffDao.updateStaff( staffVO );
               }
            }

            if ( employeeIdCellData == null )
            {
               // ��ʼ��ColumnVO
               final ColumnVO columnVO = new ColumnVO();
               columnVO.setNameDB( "employeeId" );
               columnVO.setIsForeignKey( "2" );
               columnVO.setAccountId( "1" );
               columnVO.setIsDBColumn( "1" );

               employeeIdCellData = new CellData();
               employeeIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               employeeIdCellData.setColumnVO( columnVO );
            }

            employeeIdCellData.setDbValue( employeeVO.getEmployeeId() );
         }
         catch ( final KANException e )
         {
            e.printStackTrace();
         }

         if ( primaryKeyCellData != null )
         {
            // ȥ������������������
            cellDatas.remove( primaryKeyCellData );

            // remak4 ���ԭ����Id
            final ColumnVO remark4ColumnVO = new ColumnVO();
            remark4ColumnVO.setNameDB( "remark4" );
            remark4ColumnVO.setIsForeignKey( "2" );
            remark4ColumnVO.setAccountId( "1" );
            remark4ColumnVO.setIsDBColumn( "1" );

            final CellData remark4CellData = new CellData();
            remark4CellData.setColumnVO( remark4ColumnVO );
            remark4CellData.setJdbcDataType( JDBCDataType.VARCHAR );
            remark4CellData.setDbValue( primaryKeyCellData.getDbValue() );

            // �����ظ����
            if ( cellDataDTO.getCellDataByColumnNameDB( remark4CellData.getColumnVO().getNameDB() ) == null )
            {
               cellDatas.add( remark4CellData );
            }
         }

         // ����Flag
         final ColumnVO flagColumnVO = new ColumnVO();
         flagColumnVO.setNameDB( "flag" );
         flagColumnVO.setIsForeignKey( "2" );
         flagColumnVO.setAccountId( "1" );
         flagColumnVO.setIsDBColumn( "1" );

         final CellData flagCellData = new CellData();
         flagCellData.setColumnVO( flagColumnVO );
         flagCellData.setJdbcDataType( JDBCDataType.INT );
         // Ĭ��Flag = 2������Э��
         flagCellData.setDbValue( "2" );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( flagCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( flagCellData );
         }

         // ����Temp Status
         final ColumnVO tempStatusColumnVO = new ColumnVO();
         tempStatusColumnVO.setNameDB( "tempStatus" );
         tempStatusColumnVO.setIsForeignKey( "2" );
         tempStatusColumnVO.setAccountId( "1" );
         tempStatusColumnVO.setIsDBColumn( "1" );

         final CellData tempStatusCellData = new CellData();
         tempStatusCellData.setJdbcDataType( JDBCDataType.INT );
         tempStatusCellData.setColumnVO( tempStatusColumnVO );
         // Ĭ��TempStatus = 1���½�
         tempStatusCellData.setDbValue( "1" );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( tempStatusCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( tempStatusCellData );
         }

         // ����Line Manager Id
         final ColumnVO lineManagerColumnVO = new ColumnVO();
         lineManagerColumnVO.setNameDB( "lineManagerId" );
         lineManagerColumnVO.setIsForeignKey( "2" );
         lineManagerColumnVO.setAccountId( "1" );
         lineManagerColumnVO.setIsDBColumn( "1" );

         final CellData lineManagerIdCellData = new CellData();
         lineManagerIdCellData.setColumnVO( lineManagerColumnVO );
         lineManagerIdCellData.setJdbcDataType( JDBCDataType.INT );
         lineManagerIdCellData.setDbValue( "0" );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( lineManagerIdCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( lineManagerIdCellData );
         }

         // ����Employ Status
         final ColumnVO employStatusColumnVO = new ColumnVO();
         employStatusColumnVO.setNameDB( "employStatus" );
         employStatusColumnVO.setIsForeignKey( "2" );
         employStatusColumnVO.setAccountId( "1" );
         employStatusColumnVO.setIsDBColumn( "1" );

         final CellData employStatusCellData = new CellData();
         employStatusCellData.setColumnVO( employStatusColumnVO );
         employStatusCellData.setJdbcDataType( JDBCDataType.INT );
         employStatusCellData.setDbValue( "1" );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( employStatusCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( employStatusCellData );
         }

         // ����Locked
         final ColumnVO lockedColumnVO = new ColumnVO();
         lockedColumnVO.setNameDB( "locked" );
         lockedColumnVO.setIsForeignKey( "2" );
         lockedColumnVO.setAccountId( "1" );
         lockedColumnVO.setIsDBColumn( "1" );

         final CellData lockedCellData = new CellData();
         lockedCellData.setColumnVO( lockedColumnVO );
         lockedCellData.setJdbcDataType( JDBCDataType.INT );
         lockedCellData.setDbValue( "2" );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( lockedCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( lockedCellData );
         }

         // ����Ĭ��Brach
         final ColumnVO branchColumnVO = new ColumnVO();
         branchColumnVO.setNameDB( "branch" );
         branchColumnVO.setIsForeignKey( "2" );
         branchColumnVO.setAccountId( "1" );
         branchColumnVO.setIsDBColumn( "1" );

         final CellData branchCellData = new CellData();
         branchCellData.setColumnVO( branchColumnVO );
         branchCellData.setJdbcDataType( JDBCDataType.INT );
         branchCellData.setDbValue( positionVO.getBranchId() );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( branchCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( branchCellData );
         }
         else
         {
            if ( KANUtil.filterEmpty( cellDataDTO.getCellDataByColumnNameDB( branchCellData.getColumnVO().getNameDB() ).getDbValue(), "0" ) == null )
            {
               cellDataDTO.getCellDataByColumnNameDB( branchCellData.getColumnVO().getNameDB() ).setDbValue( positionVO.getBranchId() );
            }
         }

         // ����Ĭ��Owner
         final ColumnVO ownerColumnVO = new ColumnVO();
         ownerColumnVO.setNameDB( "owner" );
         ownerColumnVO.setIsForeignKey( "2" );
         ownerColumnVO.setAccountId( "1" );
         ownerColumnVO.setIsDBColumn( "1" );

         final CellData ownerCellData = new CellData();
         ownerCellData.setColumnVO( ownerColumnVO );
         ownerCellData.setJdbcDataType( JDBCDataType.INT );
         ownerCellData.setDbValue( positionVO.getPositionId() );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( ownerCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( ownerCellData );
         }
         else
         {
            if ( KANUtil.filterEmpty( cellDataDTO.getCellDataByColumnNameDB( ownerCellData.getColumnVO().getNameDB() ).getDbValue(), "0" ) == null )
            {
               cellDataDTO.getCellDataByColumnNameDB( ownerCellData.getColumnVO().getNameDB() ).setDbValue( positionVO.getPositionId() );
            }
         }
      }

      return flag;
   }

   // Reviewed by Kevin Jin at 2014-05-08
   private boolean fetchSbCellDataDTO( final CellDataDTO cellDataDTO, List< CellDataDTO > cellDataDTOs )
   {
      // ��ʼ��CellData�б�
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();
      boolean flag = false;
      if ( cellDatas != null )
      {
         for ( CellData cellData : cellDatas )
         {

            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equalsIgnoreCase( "sbSolutionId" ) )
            {
               if ( KANUtil.filterEmpty( cellData.getValue() ) == null )
               {
                  cellDataDTOs.remove( cellDataDTO );
                  return false;
               }
               // ��ʼ��SocialBenefitSolutionDTO
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getSocialBenefitSolutionDTOByName( cellData.getValue().trim(), CORP_ID );

               if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
               {
                  cellData.setDbValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );

                  flag = true;
                  continue;
               }
               else
               {
                  cellDataDTO.setErrorMessange( "��Ԫ��[" + cellData.getCellRef() + "]�籣���������ڡ�" );
                  cellData.setErrorMessange( "��Ԫ��[" + cellData.getCellRef() + "]�籣���������ڡ�" );
                  flag = false;
               }
            }
            // SB startDate ����
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equalsIgnoreCase( "startDate" ) )
            {
               cellData.setDbValue( KANUtil.formatDate( cellData.getValue(), "yyyy-MM-dd" ) );
               continue;
            }
         }
         if ( flag )
         {
            // ����״̬�ֶ�
            final ColumnVO statusColumnVO = new ColumnVO();
            statusColumnVO.setNameDB( "status" );
            statusColumnVO.setIsForeignKey( "2" );
            statusColumnVO.setAccountId( "1" );
            statusColumnVO.setIsDBColumn( "1" );

            final CellData statusCellData = new CellData();
            statusCellData.setColumnVO( statusColumnVO );
            statusCellData.setDbValue( "2" );
            statusCellData.setJdbcDataType( JDBCDataType.INT );

            // �����ظ����
            if ( cellDataDTO.getCellDataByColumnNameDB( statusCellData.getColumnVO().getNameDB() ) == null )
            {
               cellDataDTO.getCellDatas().add( statusCellData );
            }
         }

      }
      return flag;
   }

   // Reviewed by Kevin Jin at 2014-05-08
   private void fetchSalaryCellDataDTO( final CellDataDTO cellDataDTO, final List< ItemVO > itemVOs, final String startDate, final String endDate, final String orderId )
   {
      // ��ʼ��CellData�б�
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();

      if ( cellDatas != null )
      {
         // ��ʼ��ClientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = null;
         // ��ʼ��Salary Type
         String salaryType = "";
         // ��ʼ��Divide Type
         String divideType = "";
         // ��ʼ��Exclude Divide Item Ids
         String excludeDivideItemIds = "";

         try
         {
            clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( orderId );
            salaryType = clientOrderHeaderVO.getSalaryType();
            divideType = clientOrderHeaderVO.getDivideType();
            excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
         }
         catch ( KANException e )
         {
            e.printStackTrace();
         }

         // ����Salary Type
         final ColumnVO salaryTypeColumnVO = new ColumnVO();
         salaryTypeColumnVO.setNameDB( "salaryType" );
         salaryTypeColumnVO.setIsForeignKey( "2" );
         salaryTypeColumnVO.setAccountId( "1" );
         salaryTypeColumnVO.setIsDBColumn( "1" );

         final CellData salaryTypeCellData = new CellData();
         salaryTypeCellData.setDbValue( KANUtil.filterEmpty( salaryType ) != null ? salaryType : "1" );
         salaryTypeCellData.setJdbcDataType( JDBCDataType.INT );
         salaryTypeCellData.setColumnVO( salaryTypeColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( salaryTypeCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( salaryTypeCellData );
         }

         // ����Divide Type
         final ColumnVO divideTypeColumnVO = new ColumnVO();
         divideTypeColumnVO.setNameDB( "divideType" );
         divideTypeColumnVO.setIsForeignKey( "2" );
         divideTypeColumnVO.setAccountId( "1" );
         divideTypeColumnVO.setIsDBColumn( "1" );

         final CellData divideTypeCellData = new CellData();
         divideTypeCellData.setDbValue( KANUtil.filterEmpty( divideType ) != null ? divideType : "1" );
         divideTypeCellData.setJdbcDataType( JDBCDataType.INT );
         divideTypeCellData.setColumnVO( divideTypeColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( divideTypeCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( divideTypeCellData );
         }

         // ����Exclude Divide Item Ids
         final ColumnVO excludeDivideItemIdsColumnVO = new ColumnVO();
         excludeDivideItemIdsColumnVO.setNameDB( "excludeDivideItemIds" );
         excludeDivideItemIdsColumnVO.setIsForeignKey( "2" );
         excludeDivideItemIdsColumnVO.setAccountId( "1" );
         excludeDivideItemIdsColumnVO.setIsDBColumn( "1" );

         final CellData excludeDivideItemIdsCellData = new CellData();
         excludeDivideItemIdsCellData.setDbValue( KANUtil.filterEmpty( excludeDivideItemIds ) != null ? excludeDivideItemIds : "1" );
         excludeDivideItemIdsCellData.setJdbcDataType( JDBCDataType.INT );
         excludeDivideItemIdsCellData.setColumnVO( excludeDivideItemIdsColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( excludeDivideItemIdsCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( excludeDivideItemIdsCellData );
         }

         // ����Base From
         final ColumnVO baseFromColumnVO = new ColumnVO();
         baseFromColumnVO.setNameDB( "baseFrom" );
         baseFromColumnVO.setIsForeignKey( "2" );
         baseFromColumnVO.setAccountId( "1" );
         baseFromColumnVO.setIsDBColumn( "1" );

         final CellData baseFromCellData = new CellData();
         baseFromCellData.setDbValue( "0" );
         baseFromCellData.setJdbcDataType( JDBCDataType.INT );
         baseFromCellData.setColumnVO( baseFromColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( baseFromCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( baseFromCellData );
         }

         // ����Cycle
         final ColumnVO cycleColumnVO = new ColumnVO();
         cycleColumnVO.setNameDB( "cycle" );
         cycleColumnVO.setIsForeignKey( "2" );
         cycleColumnVO.setAccountId( "1" );
         cycleColumnVO.setIsDBColumn( "1" );

         final CellData cycleCellData = new CellData();
         cycleCellData.setDbValue( "1" );
         cycleCellData.setJdbcDataType( JDBCDataType.INT );
         cycleCellData.setColumnVO( cycleColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( cycleCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( cycleCellData );
         }

         // ����Start Date
         final ColumnVO startDateColumnVO = new ColumnVO();
         startDateColumnVO.setNameDB( "startDate" );
         startDateColumnVO.setIsForeignKey( "2" );
         startDateColumnVO.setAccountId( "1" );
         startDateColumnVO.setIsDBColumn( "1" );

         final CellData startDateCellData = new CellData();
         startDateCellData.setDbValue( startDate );
         startDateCellData.setJdbcDataType( JDBCDataType.INT );
         startDateCellData.setColumnVO( startDateColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( startDateCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( startDateCellData );
         }

         // ����End Date
         final ColumnVO endDateColumnVO = new ColumnVO();
         endDateColumnVO.setNameDB( "endDate" );
         endDateColumnVO.setIsForeignKey( "2" );
         endDateColumnVO.setAccountId( "1" );
         endDateColumnVO.setIsDBColumn( "1" );

         final CellData endDateCellData = new CellData();
         endDateCellData.setDbValue( endDate );
         endDateCellData.setJdbcDataType( JDBCDataType.INT );
         endDateCellData.setColumnVO( endDateColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( endDateCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( endDateCellData );
         }

         // ����Result Cap
         final ColumnVO resultCapColumnVO = new ColumnVO();
         resultCapColumnVO.setNameDB( "resultCap" );
         resultCapColumnVO.setIsForeignKey( "2" );
         resultCapColumnVO.setAccountId( "1" );
         resultCapColumnVO.setIsDBColumn( "1" );

         final CellData resultCapCellData = new CellData();
         resultCapCellData.setDbValue( "0" );
         resultCapCellData.setJdbcDataType( JDBCDataType.INT );
         resultCapCellData.setColumnVO( resultCapColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( resultCapCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( resultCapCellData );
         }

         // ����Result Floor
         final ColumnVO resultFloorColumnVO = new ColumnVO();
         resultFloorColumnVO.setNameDB( "resultFloor" );
         resultFloorColumnVO.setIsForeignKey( "2" );
         resultFloorColumnVO.setAccountId( "1" );
         resultFloorColumnVO.setIsDBColumn( "1" );

         final CellData resultFloorCellData = new CellData();
         resultFloorCellData.setDbValue( "0" );
         resultFloorCellData.setJdbcDataType( JDBCDataType.INT );
         resultFloorCellData.setColumnVO( resultFloorColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( resultFloorCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( resultFloorCellData );
         }

         // ����Show to TS
         final ColumnVO showToTSColumnVO = new ColumnVO();
         showToTSColumnVO.setNameDB( "showToTS" );
         showToTSColumnVO.setIsForeignKey( "2" );
         showToTSColumnVO.setAccountId( "1" );
         showToTSColumnVO.setIsDBColumn( "1" );

         final CellData showToTSCellData = new CellData();
         showToTSCellData.setDbValue( "2" );
         showToTSCellData.setJdbcDataType( JDBCDataType.INT );
         showToTSCellData.setColumnVO( showToTSColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( showToTSCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( showToTSCellData );
         }

         // ����Probation Using
         final ColumnVO probationUsingColumnVO = new ColumnVO();
         probationUsingColumnVO.setNameDB( "probationUsing" );
         probationUsingColumnVO.setIsForeignKey( "2" );
         probationUsingColumnVO.setAccountId( "1" );
         probationUsingColumnVO.setIsDBColumn( "1" );

         final CellData probationUsingCellData = new CellData();
         probationUsingCellData.setDbValue( "2" );
         probationUsingCellData.setJdbcDataType( JDBCDataType.INT );
         probationUsingCellData.setColumnVO( probationUsingColumnVO );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( probationUsingCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( probationUsingCellData );
         }

         String[][] copyPropertys = { { "itemId", "INT" } };

         addTableColumnsForItem( cellDataDTO, itemVOs, copyPropertys );
      }
   }

   private void fetchCbCellDataDTO( CellDataDTO cellDataDTO )
   {

      // ��ʼ��CellData�б�
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();

      if ( cellDatas != null )
      {
         for ( CellData cellData : cellDatas )
         {
            // SB Solution Id ����
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equalsIgnoreCase( "solutionId" ) )
            {
               // ��ʼ��SocialBenefitSolutionDTO
               final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getCommercialBenefitSolutionDTOByName( cellData.getValue().trim(), CORP_ID );

               if ( commercialBenefitSolutionDTO != null && commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO() != null )
               {
                  cellData.setDbValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );
                  break;
               }
            }
         }

         // ����״̬�ֶ�
         final ColumnVO statusColumnVO = new ColumnVO();
         statusColumnVO.setNameDB( "status" );
         statusColumnVO.setIsForeignKey( "2" );
         statusColumnVO.setAccountId( "1" );
         statusColumnVO.setIsDBColumn( "1" );

         final CellData statusCellData = new CellData();
         statusCellData.setColumnVO( statusColumnVO );
         statusCellData.setDbValue( "2" );
         statusCellData.setJdbcDataType( JDBCDataType.INT );

         // �����ظ����
         if ( cellDataDTO.getCellDataByColumnNameDB( statusCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDataDTO.getCellDatas().add( statusCellData );
         }
      }

   }

   private boolean addTableColumnsForItem_leave( final CellDataDTO subImportDataDTO, final List< ItemVO > leaveItemVOs, final String[] copyPropertys )
   {
      boolean isAnnualLeave = false;
      // ��ʼ��CellDataDTO
      final CellDataDTO tempCellDataDTO = new CellDataDTO();

      String tempItemId = "";
      // ����CellData��ƥ���ҵ�itemId
      if ( subImportDataDTO.getCellDatas() != null )
      {
         for ( CellData cellData : subImportDataDTO.getCellDatas() )
         {
            String headeCellValue = cellData.getColumnVO().getNameZH();
            if ( KANUtil.filterEmpty( headeCellValue ) != null )
            {
               if ( headeCellValue.contains( "���" ) && ( headeCellValue.contains( "����" ) || headeCellValue.contains( "����" ) ) )
               {
                  headeCellValue = "���";
                  isAnnualLeave = true;
               }

               for ( ItemVO tempItemVO : leaveItemVOs )
               {
                  if ( tempItemVO.getNameZH().equalsIgnoreCase( headeCellValue ) )
                  {
                     tempItemId = tempItemVO.getItemId();

                     final CellData itemIdCellData = tempCellDataDTO.getCellDataByColumnNameDB( "itemId" );

                     if ( itemIdCellData == null )
                     {
                        final CellData tempItemIdCellData = new CellData();
                        final ColumnVO tempItemIdColumnVO = new ColumnVO();
                        tempItemIdCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( copyPropertys[ 1 ] ) );
                        tempItemIdCellData.setDbValue( tempItemId );
                        tempItemIdColumnVO.setNameDB( copyPropertys[ 0 ] );
                        tempItemIdColumnVO.setIsForeignKey( "2" );
                        tempItemIdColumnVO.setAccountId( "1" );
                        tempItemIdColumnVO.setIsDBColumn( "1" );

                        tempItemIdCellData.setColumnVO( tempItemIdColumnVO );

                        tempCellDataDTO.getCellDatas().add( tempItemIdCellData );
                     }

                     break;
                  }
               }
            }
         }
      }

      if ( tempCellDataDTO.getCellDatas().size() > 0 )
      {
         subImportDataDTO.getCellDatas().addAll( tempCellDataDTO.getCellDatas() );
      }

      return isAnnualLeave;
   }

   private void addLeaveTableColumns( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs )
   {
      String[] copyPropertys = { "itemId", "INT" };
      boolean isAnnualLeave = addTableColumnsForItem_leave( subImportDataDTO, allItemVOs, copyPropertys );

      // �������٣��������ֶ�
      if ( isAnnualLeave )
      {
         // ʹ������
         final CellData cycleCellData = new CellData();
         final ColumnVO cycleColumnVO = new ColumnVO();
         cycleCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
         cycleCellData.setDbValue( "5" );
         cycleColumnVO.setNameDB( "cycle" );
         cycleColumnVO.setIsForeignKey( "2" );
         cycleColumnVO.setAccountId( "1" );
         cycleColumnVO.setIsDBColumn( "1" );
         cycleCellData.setColumnVO( cycleColumnVO );
         subImportDataDTO.getCellDatas().add( cycleCellData );

         // ���
         final CellData yearCellData = new CellData();
         final ColumnVO yearColumnVO = new ColumnVO();
         yearCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
         yearCellData.setDbValue( KANUtil.formatDate( new Date(), "yyyy" ) );
         yearColumnVO.setNameDB( "year" );
         yearColumnVO.setIsForeignKey( "2" );
         yearColumnVO.setAccountId( "1" );
         yearColumnVO.setIsDBColumn( "1" );
         yearCellData.setColumnVO( yearColumnVO );
         subImportDataDTO.getCellDatas().add( yearCellData );
      }
   }

   private void addOtTableColumns( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs )
   {
      String[][] copyPropertys = { { "itemId", "INT" } };
      addTableColumnsForItem( subImportDataDTO, allItemVOs, copyPropertys );
   }

   private void addOtherTableColumns( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs )
   {
      String[][] copyPropertys = { { "itemId", "INT" } };
      addTableColumnsForItem( subImportDataDTO, allItemVOs, copyPropertys );
   }

   private void addTableColumnsForItem( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs, final String[][] copyPropertys )
   {

      List< CellData > cellDatas = subImportDataDTO.getCellDatas();
      List< CellData > tempCellDatas = new ArrayList< CellData >();

      if ( cellDatas != null )
      {
         for ( CellData cellData : cellDatas )
         {
            // ��ͷ�ַ���
            String headeCellValue = cellData.getColumnVO().getNameZH();
            // ���˵��Զ���ӵ�createBy��modifyBy��Ӧ�ĵ�Ԫ��
            if ( headeCellValue != null )
            {
               ItemVO matchItemVO = null;
               for ( ItemVO itemVO : allItemVOs )
               {
                  if ( itemVO.getNameZH().equalsIgnoreCase( headeCellValue ) )
                  {
                     matchItemVO = itemVO;
                     break;
                  }
               }

               if ( matchItemVO != null )
               {
                  for ( String[] property : copyPropertys )
                  {

                     CellData addCellData = new CellData();
                     ColumnVO addCellColumnVO = new ColumnVO();

                     addCellColumnVO.setNameDB( property[ 0 ] );
                     addCellColumnVO.setIsForeignKey( "2" );
                     addCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( property[ 1 ] ) );
                     addCellColumnVO.setAccountId( "1" );
                     addCellColumnVO.setIsDBColumn( "1" );
                     addCellData.setColumnVO( addCellColumnVO );
                     try
                     {
                        addCellData.setDbValue( BeanUtils.getProperty( matchItemVO, property[ 0 ] ) );
                     }
                     catch ( Exception e )
                     {
                        e.printStackTrace();
                     }

                     // ��������۽���(����)
                     if ( "10153".equals( matchItemVO.getItemId() ) )
                     {
                        CellData cycleCellData = subImportDataDTO.getCellDataByColumnNameDB( "cycle" );
                        if ( cycleCellData != null )
                        {
                           cycleCellData.setDbValue( "3" );
                           cycleCellData.setValue( "3" );
                        }
                     }

                     tempCellDatas.add( addCellData );
                  }
               }
            }
         }

         cellDatas.addAll( tempCellDatas );
      }
   }

   // Added by Kevin Jin at 2014-05-08
   private String getStartDate( final CellDataDTO cellDataDTO )
   {
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {
         for ( CellData cellData : cellDataDTO.getCellDatas() )
         {
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equals( "startDate" ) )
            {
               if ( cellData.getFormateValue() == null )
               {
                  return null;
               }
               else
               {
                  String date = KANUtil.formatDate( cellData.getFormateValue(), "yyyy-MM-dd" );
                  cellData.setDbValue( date );
                  return date;
               }
            }
         }
      }

      return null;
   }

   // Added by Kevin Jin at 2014-05-08
   private String getEndDate( final CellDataDTO cellDataDTO )
   {
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {
         for ( CellData cellData : cellDataDTO.getCellDatas() )
         {
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equals( "endDate" ) )
            {
               if ( cellData.getFormateValue() == null )
               {
                  return null;
               }
               else
               {
                  String date = KANUtil.formatDate( cellData.getFormateValue(), "yyyy-MM-dd" );
                  cellData.setDbValue( date );
                  return date;
               }

            }
         }
      }

      return null;
   }

   // Added by Kevin Jin at 2014-06-09
   private String getOrderId( final CellDataDTO cellDataDTO )
   {
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {
         for ( CellData cellData : cellDataDTO.getCellDatas() )
         {
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equals( "orderId" ) )
            {
               return ( String ) cellData.getDbValue();
            }
         }
      }

      return null;
   }

   // ��������˺����������ڲ���
   private boolean fetchOwnerAndBranchInfo( final CellDataDTO cellDataDTO, final HttpServletRequest request, final String branchId, final String owner ) throws KANException
   {
      boolean flag = true;

      // ��ʼ���Ƿ���¶Խ��ˡ��ԽӲ���
      boolean updataFlag = true;

      // ��ʼ���ԽӲ��ţ��Խ�����Ϣ
      String branchId_import = null;
      String owner_import = null;

      final CellData branchCellData = cellDataDTO.getCellDataByColumnNameDB( "branch" );
      final CellData ownerCellData = cellDataDTO.getCellDataByColumnNameDB( "owner" );

      if ( branchCellData != null )
      {
         branchId_import = KANUtil.filterEmpty( branchCellData.getDbValue(), "0" );
      }
      if ( ownerCellData != null )
      {
         owner_import = KANUtil.filterEmpty( ownerCellData.getDbValue(), "0" );
      }

      // ��֤�����Ƿ���Ч
      if ( branchCellData != null && KANUtil.filterEmpty( branchCellData.getValue() ) != null && branchId_import == null )
      {
         flag = false;
         branchCellData.setErrorMessange( "��Ԫ��[ " + branchCellData.getCellRef() + "]'" + branchCellData.getValue() + "'��Ӧ��" + branchCellData.getColumnVO().getNameZH() + "��Ч�����ʵ��" );
      }

      // �Խ���������
      if ( ownerCellData != null )
      {
         // �Խ��������������ݲ�Ϊ��
         if ( owner_import != null )
         {
            final PositionVO positionVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionVOByPositionId( owner_import );

            if ( positionVO != null )
            {

               if ( branchCellData != null )
               {
                  final String branchId_position = KANUtil.filterEmpty( positionVO.getBranchId() );

                  // ��֤�Խ���ְλ�벿���Ƿ�ƥ��
                  if ( branchId_position != null )
                  {

                     if ( !branchId_position.equals( branchCellData.getDbValue() ) )
                     {
                        flag = false;

                        branchCellData.setErrorMessange( "�� " + ( ownerCellData.getRow() + 1 ) + " ��[" + ownerCellData.getCellRef() + "]'" + ownerCellData.getValue() + "'��Ӧ�Ĳ���"
                              + branchCellData.getValue() + "[" + branchCellData.getCellRef() + "]'" + branchCellData.getValue() + "'��ƥ�䣬���ʵ��" );
                     }

                  }
                  else
                  {
                     branchCellData.setDbValue( null );
                  }
               }
               else
               {
                  final CellData branchCellData_new = new CellData();
                  final ColumnVO branchColumn = new ColumnVO();
                  branchColumn.setNameDB( "branch" );
                  branchColumn.setIsForeignKey( "2" );
                  branchColumn.setAccountId( "1" );
                  branchColumn.setIsDBColumn( "1" );
                  branchCellData_new.setColumnVO( branchColumn );
                  branchCellData_new.setJdbcDataType( JDBCDataType.VARCHAR );
                  branchCellData_new.setDbValue( positionVO.getBranchId() );
                  cellDataDTO.getCellDatas().add( branchCellData_new );
               }

            }
            // �Խ�������ֵ��Ч
            else
            {
               ownerCellData.setErrorMessange( "��Ԫ��[ " + ownerCellData.getCellRef() + "]'" + ownerCellData.getValue() + "'��ϵͳ�в�������Чְλ�����ʵ��" );
               flag = false;
            }
         }
         else if ( KANUtil.filterEmpty( ownerCellData.getValue() ) != null )
         {
            flag = false;
            ownerCellData.setErrorMessange( "��Ԫ��[ " + ownerCellData.getCellRef() + "]'" + ownerCellData.getValue() + "'��Ӧ��" + ownerCellData.getColumnVO().getNameZH()
                  + "��Ч���ߵ���ĶԽӲ��Ų�ƥ�䣬���ʵ��" );
         }
         // �����öԽ���������Ϊ��
         else
         {
            updataFlag = false;

            ownerCellData.setValue( BaseAction.getPositionId( request, null ) );
            ownerCellData.setDbValue( BaseAction.getPositionId( request, null ) );

            // ��ӵ�ǰ���������ڲ���Ϊ�ԽӲ���
            final PositionVO positionVO_temp = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionVOByPositionId( BaseAction.getPositionId( request, null ) );
            if ( branchCellData == null )
            {
               final CellData branchCellData_new = new CellData();
               final ColumnVO branchColumn = new ColumnVO();
               branchColumn.setNameDB( "branch" );
               branchColumn.setIsForeignKey( "2" );
               branchColumn.setAccountId( "1" );
               branchColumn.setIsDBColumn( "1" );
               branchCellData_new.setColumnVO( branchColumn );
               branchCellData_new.setJdbcDataType( JDBCDataType.VARCHAR );
               branchCellData_new.setDbValue( positionVO_temp.getBranchId() );
               cellDataDTO.getCellDatas().add( branchCellData_new );
            }
            else
            {
               branchCellData.setDbValue( positionVO_temp.getBranchId() );
            }
         }
      }
      else
      {
         updataFlag = false;

         // ��ӵ�ǰ������Ϊ�Խ���
         final CellData ownerData_new = new CellData();
         final ColumnVO ownerColumn = new ColumnVO();
         ownerColumn.setNameDB( "owner" );
         ownerColumn.setIsForeignKey( "2" );
         ownerColumn.setAccountId( "1" );
         ownerColumn.setIsDBColumn( "1" );
         ownerData_new.setColumnVO( ownerColumn );
         ownerData_new.setJdbcDataType( JDBCDataType.VARCHAR );
         ownerData_new.setDbValue( BaseAction.getPositionId( request, null ) );
         cellDataDTO.getCellDatas().add( ownerData_new );

         // ��ӵ�ǰ���������ڲ���Ϊ�ԽӲ���
         final PositionVO positionVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionVOByPositionId( BaseAction.getPositionId( request, null ) );
         if ( branchCellData == null )
         {
            final CellData branchCellData_new = new CellData();
            final ColumnVO branchColumn = new ColumnVO();
            branchColumn.setNameDB( "branch" );
            branchColumn.setIsForeignKey( "2" );
            branchColumn.setAccountId( "1" );
            branchColumn.setIsDBColumn( "1" );
            branchCellData_new.setColumnVO( branchColumn );
            branchCellData_new.setJdbcDataType( JDBCDataType.VARCHAR );
            branchCellData_new.setDbValue( positionVO.getBranchId() );
            cellDataDTO.getCellDatas().add( branchCellData_new );
         }
         else
         {
            branchCellData.setDbValue( positionVO.getBranchId() );
         }

      }

      // ���ݿ����жԽ��ˣ��ҵ����δ���öԽ��˻��߶Խ���Ϊ�գ��Խ��˲��޸�
      if ( KANUtil.filterEmpty( owner ) != null && !updataFlag )
      {
         cellDataDTO.getCellDataByColumnNameDB( "owner" ).setDbValue( owner );
         cellDataDTO.getCellDataByColumnNameDB( "branch" ).setDbValue( branchId );
      }

      return flag;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public final void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public EmployeeContractTempDao getEmployeeContractTempDao()
   {
      return employeeContractTempDao;
   }

   public void setEmployeeContractTempDao( EmployeeContractTempDao employeeContractTempDao )
   {
      this.employeeContractTempDao = employeeContractTempDao;
   }

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractService getEmployeeContractService()
   {
      return employeeContractService;
   }

   public void setEmployeeContractService( EmployeeContractService employeeContractService )
   {
      this.employeeContractService = employeeContractService;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      return false;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }
}
