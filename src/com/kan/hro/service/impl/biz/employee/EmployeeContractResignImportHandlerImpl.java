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
                  // ��ʼ���Ͷ���ͬ
                  EmployeeContractVO employeeContractVO = null;

                  // ����Ͷ���ͬ
                  final CellData tempEmployeeContractCellData = importDataDTO.getCellDataByColumnNameDB( "contractId" );
                  if ( tempEmployeeContractCellData != null )
                  {
                     // �Ͷ���ͬID����Ϊ��
                     if ( KANUtil.filterEmpty( tempEmployeeContractCellData.getValue() ) == null )
                     {
                        flag = false;
                        tempEmployeeContractCellData.setErrorMessange( "�� " + ( tempEmployeeContractCellData.getRow() + 1 ) + " �У�"
                              + tempEmployeeContractCellData.getColumnVO().getNameZH() + "����Ϊ�գ�" );
                     }
                     else
                     {
                        employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( tempEmployeeContractCellData.getValue() );
                        if ( employeeContractVO == null )
                        {
                           flag = false;
                           tempEmployeeContractCellData.setErrorMessange( "�� " + ( tempEmployeeContractCellData.getRow() + 1 ) + " �У�["
                                 + tempEmployeeContractCellData.getColumnVO().getNameZH() + "][ID: " + tempEmployeeContractCellData.getValue() + "]δ�ҵ���" );
                        }
                        // �Ͷ���ͬ״̬�Ƿ������ְ
                        else
                        {
                           if ( !"3".equals( employeeContractVO.getStatus() ) && !"5".equals( employeeContractVO.getStatus() ) && !"6".equals( employeeContractVO.getStatus() ) )
                           {
                              flag = false;
                              tempEmployeeContractCellData.setErrorMessange( "�� " + ( tempEmployeeContractCellData.getRow() + 1 ) + " �У�["
                                    + tempEmployeeContractCellData.getColumnVO().getNameZH() + "][ID: " + tempEmployeeContractCellData.getValue() + "]״̬������������ӦΪ������׼�������Ѹ��¡��򡰹鵵������" );
                           }
                        }
                     }

                     // ������֤������Ϣ
                     if ( flag && employeeContractVO != null )
                     {
                        if ( !continueCheckImportData( importDataDTO, tempEmployeeContractCellData, employeeContractVO ) )
                        {
                           flag = false;
                        }
                        else
                        {
                           // �����ֶ�
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

   // ������֤����ְʱ�䣬�����ʱ�䣬��ְ״̬���籣���̱���Ϣ
   private boolean continueCheckImportData( final CellDataDTO importDataDTO, final CellData employeeContractCellData, final EmployeeContractVO employeeContractVO )
         throws KANException
   {
      // ��ʼ�����޸������������Ƿ�
      boolean flag = true;

      final String accountId = importDataDTO.getAccounId();
      final String corpId = importDataDTO.getCorpId();
      final String contractId = employeeContractVO.getContractId();
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );

      // ��ְ���ڲ���Ϊ��
      final CellData resignDateCellData = importDataDTO.getCellDataByColumnNameDB( "resignDate" );
      if ( resignDateCellData != null && KANUtil.filterEmpty( resignDateCellData.getValue() ) == null )
      {
         flag = false;
         resignDateCellData.setErrorMessange( "�� " + ( resignDateCellData.getRow() + 1 ) + " �У�[" + resignDateCellData.getColumnVO().getNameZH() + "]����Ϊ�գ�" );
      }
      // ��ְʱ����Ч��
      else
      {
         // �ж�����Э����ְʱ����Ч��
         final String startDate = employeeContractVO.getStartDate();
         final String endDate = employeeContractVO.getEndDate();

         // �̶����Ͷ���ͬ
         if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
         {
            if ( !KANUtil.between( KANUtil.createDate( startDate ), KANUtil.createDate( endDate ), KANUtil.createDate( resignDateCellData.getValue() ) ) )
            {
               flag = false;
               resignDateCellData.setErrorMessange( "�� " + ( resignDateCellData.getRow() + 1 ) + " �У�[" + resignDateCellData.getColumnVO().getNameZH() + ":"
                     + resignDateCellData.getFormateValue() + "]ʱ�䲻�ں�ͬ��Чʱ�䷶Χ�ڣ�" );
            }
         }
         // �ǹ̶��ڼ��Ͷ���ͬ
         else if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) == null )
         {
            if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( resignDateCellData.getValue() ) ) )
            {
               flag = false;
               resignDateCellData.setErrorMessange( "�� " + ( resignDateCellData.getRow() + 1 ) + " �У�[" + resignDateCellData.getColumnVO().getNameZH() + ":"
                     + resignDateCellData.getFormateValue() + "]ʱ�䲻���ں�ͬ��ʼʱ��֮ǰ��" );
            }
         }
         else
         {
            flag = false;
            resignDateCellData.setErrorMessange( "�� " + ( resignDateCellData.getRow() + 1 ) + " �У��Ͷ���ͬ��ʼʱ���쳣��" );
         }
      }

      // ��ְ״̬����Ϊ��
      final CellData employStatusCellData = importDataDTO.getCellDataByColumnNameDB( "employStatus" );
      if ( employStatusCellData != null && KANUtil.filterEmpty( employStatusCellData.getValue() ) == null )
      {
         flag = false;
         employStatusCellData.setErrorMessange( "�� " + ( employStatusCellData.getRow() + 1 ) + " �У�[" + employStatusCellData.getColumnVO().getNameZH() + "]����Ϊ�գ�" );
      }
      // ��ְ״̬����Ϊ��ְ
      else
      {
         if ( "1".equals( employStatusCellData.getDbValue().toString() ) )
         {
            flag = false;
            employStatusCellData.setErrorMessange( "�� " + ( employStatusCellData.getRow() + 1 ) + " �У�[" + employStatusCellData.getColumnVO().getNameZH() + "]״̬����Ϊ����ְ����" );
         }
      }

      /** �ж��籣������Ч��  start **/
      final CellData sb1IdCellData = importDataDTO.getCellDataByColumnNameDB( "sb1Id" );
      final CellData sb1NameCellData = importDataDTO.getCellDataByColumnNameDB( "sb1Name" );
      final CellData sb1EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "sb1EndDate" );
      final CellData sb2IdCellData = importDataDTO.getCellDataByColumnNameDB( "sb2Id" );
      final CellData sb2NameCellData = importDataDTO.getCellDataByColumnNameDB( "sb2Name" );
      final CellData sb2EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "sb2EndDate" );
      final CellData sb3IdCellData = importDataDTO.getCellDataByColumnNameDB( "sb3Id" );
      final CellData sb3NameCellData = importDataDTO.getCellDataByColumnNameDB( "sb3Name" );
      final CellData sb3EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "sb3EndDate" );

      // ��ȡԱ���籣����List
      final List< Object > employeeContractSBVOObjects = this.getEmployeeContractSBDao().getEmployeeContractSBVOsByContractId( contractId );
      if ( ( sb1IdCellData != null && KANUtil.filterEmpty( sb1IdCellData.getValue() ) != null )
            || ( sb1NameCellData != null && KANUtil.filterEmpty( sb1NameCellData.getValue() ) != null )
            || ( sb2IdCellData != null && KANUtil.filterEmpty( sb2IdCellData.getValue() ) != null )
            || ( sb2NameCellData != null && KANUtil.filterEmpty( sb2NameCellData.getValue() ) != null )
            || ( sb3IdCellData != null && KANUtil.filterEmpty( sb3IdCellData.getValue() ) != null )
            || ( sb3NameCellData != null && KANUtil.filterEmpty( sb3NameCellData.getValue() ) != null ) )
      {
         // Ա��û���籣����
         if ( employeeContractSBVOObjects == null || employeeContractSBVOObjects.size() == 0 )
         {
            flag = false;
            employeeContractCellData.setErrorMessange( "�� " + ( employeeContractCellData.getRow() + 1 ) + " �У��Ҳ���[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                  + contractId + "]�κ��籣������" );
         }
         else
         {
            // ���籣��������
            if ( checkEmployeeContractSBStatus( employeeContractSBVOObjects ) == null )
            {
               flag = false;
               employeeContractCellData.setErrorMessange( "�� " + ( employeeContractCellData.getRow() + 1 ) + " �У��Ҳ���[" + employeeContractCellData.getColumnVO().getNameZH()
                     + "][ID: " + contractId + "]���籣����״̬�����˱��ģ�Ӧ�������걨�ӱ��������������ɡ�����" );
            }
            // ���籣��������
            else
            {
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO1 = getSocialBenefitSolutionDTOByCondition( accountConstants, corpId, sb1IdCellData, sb1NameCellData );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO2 = getSocialBenefitSolutionDTOByCondition( accountConstants, corpId, sb2IdCellData, sb2NameCellData );
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO3 = getSocialBenefitSolutionDTOByCondition( accountConstants, corpId, sb3IdCellData, sb3NameCellData );

               // ����籣1���ڣ���֤ʱ����Ч��
               if ( socialBenefitSolutionDTO1 != null )
               {
                  if ( validateEmployeeContractSB( socialBenefitSolutionDTO1, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, sb1EndDateCellData ) )
                  {
                     addSBCellData( importDataDTO, sb1IdCellData, sb1NameCellData, "sb1", socialBenefitSolutionDTO1 );
                  }
               }

               // ����籣2���ڣ���֤ʱ����Ч��
               if ( socialBenefitSolutionDTO2 != null )
               {
                  if ( validateEmployeeContractSB( socialBenefitSolutionDTO2, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, sb2EndDateCellData ) )
                  {
                     addSBCellData( importDataDTO, sb2IdCellData, sb2NameCellData, "sb2", socialBenefitSolutionDTO2 );
                  }
               }

               // ����籣3���ڣ���֤ʱ����Ч��
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
      /** �ж��籣������Ч��  end **/

      /** �ж��̱�������Ч��  start **/
      final CellData cb1IdCellData = importDataDTO.getCellDataByColumnNameDB( "cb1Id" );
      final CellData cb1NameCellData = importDataDTO.getCellDataByColumnNameDB( "cb1Name" );
      final CellData cb1EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "cb1EndDate" );
      final CellData cb2IdCellData = importDataDTO.getCellDataByColumnNameDB( "cb2Id" );
      final CellData cb2NameCellData = importDataDTO.getCellDataByColumnNameDB( "cb2Name" );
      final CellData cb2EndDateCellData = importDataDTO.getCellDataByColumnNameDB( "cb2EndDate" );

      // ��ȡԱ���̱�����List
      final List< Object > employeeContractCBVOObjects = this.getEmployeeContractCBDao().getEmployeeContractCBVOsByContractId( contractId );
      if ( ( cb1IdCellData != null && KANUtil.filterEmpty( cb1IdCellData.getValue() ) != null )
            || ( cb1NameCellData != null && KANUtil.filterEmpty( cb1NameCellData.getValue() ) != null )
            || ( cb2IdCellData != null && KANUtil.filterEmpty( cb2IdCellData.getValue() ) != null )
            || ( cb2NameCellData != null && KANUtil.filterEmpty( cb2NameCellData.getValue() ) != null ) )
      {
         // Ա��û���̱�����
         if ( employeeContractCBVOObjects == null || employeeContractCBVOObjects.size() == 0 )
         {
            flag = false;
            employeeContractCellData.setErrorMessange( "�� " + ( employeeContractCellData.getRow() + 1 ) + " �У��Ҳ���[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                  + contractId + "]�κ��̱�������" );
         }
         else
         {
            // ���̱���������
            if ( checkEmployeeContractCBStatus( employeeContractCBVOObjects ) == null )
            {
               flag = false;
               employeeContractCellData.setErrorMessange( "�� " + ( employeeContractCellData.getRow() + 1 ) + " �У��Ҳ���[" + employeeContractCellData.getColumnVO().getNameZH()
                     + "][ID: " + contractId + "]���̱�����״̬�����˱��ģ�Ӧ�������걨�ӱ��������������ɡ�����" );
            }
            // ���̱���������
            else
            {
               final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO1 = getCommercialBenefitSolutionDTOByCondition( accountConstants, corpId, cb1IdCellData, cb1NameCellData );
               final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO2 = getCommercialBenefitSolutionDTOByCondition( accountConstants, corpId, cb2IdCellData, cb2NameCellData );

               // ����̱�1���ڣ���֤ʱ����Ч��
               if ( commercialBenefitSolutionDTO1 != null )
               {
                  if ( validateEmployeeContractCB( commercialBenefitSolutionDTO1, employeeContractSBVOObjects, employeeContractVO, employeeContractCellData, cb1EndDateCellData ) )
                  {
                     addCBCellData( importDataDTO, cb1IdCellData, cb1NameCellData, "cb1", commercialBenefitSolutionDTO1 );
                  }
               }

               // ����̱�2���ڣ���֤ʱ����Ч��
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
      /** �ж��̱�������Ч��  end **/

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

   // �����̱�ID CellData
   private CellData getCbIdCellData( final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO, final String nameDB )
   {
      // ���״̬
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

   // �����̱�Name CellData
   private CellData getCbNameCellData( final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO, final String nameDB )
   {
      // ���״̬
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

   // �����籣ID CellData
   private CellData getSbIdCellData( final SocialBenefitSolutionDTO socialBenefitSolutionDTO, final String nameDB )
   {
      // ���״̬
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

   // �����籣Name CellData
   private CellData getSbNameCellData( final SocialBenefitSolutionDTO socialBenefitSolutionDTO, final String nameDB )
   {
      // ���״̬
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

   // ״̬�Ƿ�Ϊ�����걨�ӱ��������������ɡ�״̬
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

   // ״̬�Ƿ�Ϊ�����걨�ӱ��������������ɡ�״̬
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

   // ������������SocialBenefitSolutionDTO
   private SocialBenefitSolutionDTO getSocialBenefitSolutionDTOByCondition( final KANAccountConstants accountConstants, final String corpId, final CellData sbIdCellData,
         final CellData sbNameCellData )
   {
      SocialBenefitSolutionDTO socialBenefitSolutionDTO = null;
      // ��ID����
      if ( sbIdCellData != null && sbNameCellData == null )
      {
         socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByHeaderId( ( String ) sbIdCellData.getDbValue() );
      }
      // ��Name����
      else if ( sbIdCellData == null && sbNameCellData != null )
      {
         socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByName( sbNameCellData.getValue(), corpId );
      }
      // ID/Nameͬʱ����
      else if ( sbIdCellData != null && sbNameCellData != null )
      {
         socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByName( sbNameCellData.getValue(), corpId );
         // ID��Name��ƥ��
         if ( socialBenefitSolutionDTO == null || !socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId().equals( sbIdCellData.getValue() ) )
         {
            return null;
         }
      }

      return socialBenefitSolutionDTO;
   }

   // ������������CommercialBenefitSolutionDTO
   private CommercialBenefitSolutionDTO getCommercialBenefitSolutionDTOByCondition( final KANAccountConstants accountConstants, final String corpId, final CellData cbIdCellData,
         final CellData cbNameCellData )
   {
      CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = null;
      // ��ID����
      if ( cbIdCellData != null && cbNameCellData == null )
      {
         commercialBenefitSolutionDTO = accountConstants.getCommercialBenefitSolutionDTOByHeaderId( ( String ) cbIdCellData.getDbValue() );
      }
      // ��Name����
      else if ( cbIdCellData == null && cbNameCellData != null )
      {
         commercialBenefitSolutionDTO = accountConstants.getCommercialBenefitSolutionDTOByName( cbNameCellData.getValue(), corpId );
      }
      // ID/Nameͬʱ����
      else if ( cbIdCellData != null && cbNameCellData != null )
      {
         commercialBenefitSolutionDTO = accountConstants.getCommercialBenefitSolutionDTOByName( cbNameCellData.getValue(), corpId );
         // ID��Name��ƥ��
         if ( commercialBenefitSolutionDTO == null || !commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId().equals( cbIdCellData.getValue() ) )
         {
            return null;
         }
      }

      return commercialBenefitSolutionDTO;
   }

   // ����solutionId����EmployeeContractSBVO
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

   // ����solutionId����EmployeeContractCBVO
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

   // У���籣��Ϣ����Ч��
   private boolean validateEmployeeContractSB( final SocialBenefitSolutionDTO socialBenefitSolutionDTO, final List< Object > employeeContractSBVOObjects,
         final EmployeeContractVO employeeContractVO, final CellData employeeContractCellData, final CellData sbEndDateCellData ) throws KANException
   {
      try
      {
         boolean flag = true;
         // �˱�ʱ�䲻��Ϊ��
         if ( sbEndDateCellData != null && KANUtil.filterEmpty( sbEndDateCellData.getDbValue() ) == null )
         {
            flag = false;
            sbEndDateCellData.setErrorMessange( "�� " + ( sbEndDateCellData.getRow() + 1 ) + " �У�[" + sbEndDateCellData.getColumnVO().getNameZH() + "]����Ϊ�գ�" );
         }
         else
         {
            final EmployeeContractSBVO employeeContractSBVO = getEmployeeContractSBVOBySolutionId( employeeContractSBVOObjects, socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );
            if ( employeeContractSBVO == null )
            {
               flag = false;
               sbEndDateCellData.setErrorMessange( "�� " + ( sbEndDateCellData.getRow() + 1 ) + " �У�[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                     + employeeContractVO.getContractId() + "]�²������籣����[" + socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getNameZH() + "]��" );
            }
            else
            {
               final List< Object > objects = new ArrayList< Object >();
               objects.add( employeeContractSBVO );
               // ��֤�籣����״̬
               if ( checkEmployeeContractSBStatus( objects ) == null )
               {
                  flag = false;
                  employeeContractCellData.setErrorMessange( "�� " + ( employeeContractCellData.getRow() + 1 ) + " �У��Ҳ���[" + employeeContractCellData.getColumnVO().getNameZH()
                        + "][ID: " + employeeContractVO.getContractId() + "]���籣����״̬�����˱��ģ�Ӧ�������걨�ӱ��������������ɡ�����" );
               }
               // ��֤�˱�ʱ��
               else
               {
                  final String startDate = employeeContractSBVO.getStartDate();
                  final String endDate = employeeContractVO.getEndDate();

                  // �̶����Ͷ���ͬ
                  if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
                  {
                     if ( !KANUtil.between( KANUtil.createDate( startDate ), KANUtil.createDate( endDate ), KANUtil.createDate( sbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        sbEndDateCellData.setErrorMessange( "�� " + ( sbEndDateCellData.getRow() + 1 ) + " �У�[" + sbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + sbEndDateCellData.getFormateValue() + "]ʱ�䲻�ں�ͬ��Чʱ�䷶Χ�ڣ�" );
                     }
                  }
                  // �ǹ̶��ڼ��Ͷ���ͬ
                  else if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) == null )
                  {
                     if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( sbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        sbEndDateCellData.setErrorMessange( "�� " + ( sbEndDateCellData.getRow() + 1 ) + " �У�[" + sbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + sbEndDateCellData.getFormateValue() + "]ʱ�䲻���ں�ͬ��ʼʱ��֮ǰ��" );
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

   // У���籣��Ϣ����Ч��
   private boolean validateEmployeeContractCB( final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO, final List< Object > employeeContractCBVOObjects,
         final EmployeeContractVO employeeContractVO, final CellData employeeContractCellData, final CellData cbEndDateCellData ) throws KANException
   {
      try
      {
         boolean flag = true;
         // �˱�ʱ�䲻��Ϊ��
         if ( cbEndDateCellData != null && KANUtil.filterEmpty( cbEndDateCellData.getDbValue() ) == null )
         {
            flag = false;
            cbEndDateCellData.setErrorMessange( "�� " + ( cbEndDateCellData.getRow() + 1 ) + " �У�[" + cbEndDateCellData.getColumnVO().getNameZH() + "]����Ϊ�գ�" );
         }
         else
         {
            final EmployeeContractCBVO employeeContractCBVO = getEmployeeContractCBVOBySolutionId( employeeContractCBVOObjects, commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );
            if ( employeeContractCBVO == null )
            {
               flag = false;
               cbEndDateCellData.setErrorMessange( "�� " + ( cbEndDateCellData.getRow() + 1 ) + " �У�[" + employeeContractCellData.getColumnVO().getNameZH() + "][ID: "
                     + employeeContractVO.getContractId() + "]�²������籣����[" + commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getNameZH() + "]��" );
            }
            else
            {
               final List< Object > objects = new ArrayList< Object >();
               objects.add( employeeContractCBVO );
               // ��֤�籣����״̬
               if ( checkEmployeeContractCBStatus( objects ) == null )
               {
                  flag = false;
                  employeeContractCellData.setErrorMessange( "�� " + ( employeeContractCellData.getRow() + 1 ) + " �У��Ҳ���[" + employeeContractCellData.getColumnVO().getNameZH()
                        + "][ID: " + employeeContractVO.getContractId() + "]���̱�����״̬�����˱��ģ�Ӧ�������걨�ӱ��������������ɡ�����" );
               }
               // ��֤�˱�ʱ��
               else
               {
                  final String startDate = employeeContractCBVO.getStartDate();
                  final String endDate = employeeContractVO.getEndDate();

                  // �̶����Ͷ���ͬ
                  if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
                  {
                     if ( !KANUtil.between( KANUtil.createDate( startDate ), KANUtil.createDate( endDate ), KANUtil.createDate( cbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        cbEndDateCellData.setErrorMessange( "�� " + ( cbEndDateCellData.getRow() + 1 ) + " �У�[" + cbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + cbEndDateCellData.getFormateValue() + "]ʱ�䲻�ں�ͬ��Чʱ�䷶Χ�ڣ�" );
                     }
                  }
                  // �ǹ̶��ڼ��Ͷ���ͬ
                  else if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) == null )
                  {
                     if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( cbEndDateCellData.getValue() ) ) )
                     {
                        flag = false;
                        cbEndDateCellData.setErrorMessange( "�� " + ( cbEndDateCellData.getRow() + 1 ) + " �У�[" + cbEndDateCellData.getColumnVO().getNameZH() + ":"
                              + cbEndDateCellData.getFormateValue() + "]ʱ�䲻���ں�ͬ��ʼʱ��֮ǰ��" );
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
