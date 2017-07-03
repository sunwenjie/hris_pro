package com.kan.base.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.util.poi.bean.XssfDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.UploadFileAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.web.actions.biz.employee.EmployeeContractAction;

public class ExcelImport4EmployeeContract
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

      final List< EmployeeContractVO > employeeContractVOs = new ArrayList< EmployeeContractVO >();

      final EmployeeContractDao employeeContractDao = ( EmployeeContractDao ) ServiceLocator.getService( "employeeContractDao" );

      final List< String > titleList = new ArrayList< String >();

      final List< String > errorMessageList = new ArrayList< String >();

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( accountId ).getTableDTOByAccessAction( EmployeeContractAction.getAccessAction( request, null ) );

      int successCount = 0;

      final String fileName = localFileString.substring( localFileString.lastIndexOf( "/" ) + 1, localFileString.length() );

      final String description = ( String ) request.getAttribute( "description_temp" );

      final List< List< CellDataDTO > > cellDataDTOPackages = new ArrayList< List< CellDataDTO > >();
      new ReadXlsxHander()
      {
         // ��ʼ��CellDataDTO
         List< CellDataDTO > cellDataDTOs = null;

         @Override
         public void optRows( int sheetIndex, int curRow, final List< CellData > cells ) throws SQLException
         {
            try
            {
               if ( sheetIndex == 0 )
               {
                  if ( curRow % 500 == 0 )
                  {
                     cellDataDTOs = new ArrayList< CellDataDTO >();
                     cellDataDTOPackages.add( cellDataDTOs );
                  }

                  if ( curRow == 0 )
                  {
                     // ������
                     for ( int i = 0; i < cells.size(); i++ )
                     {
                        titleList.add( cells.get( i ).getValue() );
                     }
                  }
                  else
                  {
                     cellDataDTOs.add( prepareCellDataDTO( tableDTO, titleList, cells ) );
                  }
               }
            }
            catch ( final Exception e )
            {
               e.printStackTrace();
            }
         }

      }.process( localFileString );

      // ����������
      for ( ColumnVO columnVO : tableDTO.getAllColumnVO() )
      {
         // �����ѡ���  { 2��������3����ѡ��4����ѡ��}������ѡ����
         if ( columnVO != null
               && KANUtil.filterEmpty( columnVO.getInputType() ) != null
               && ( KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "2" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "3" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "4" ) ) )
         {
            columnVO.loadOptionValues( request );
         }
      }

      UploadFileAction.setStatusMsg( request, "3", "������֤��..." );

      boolean validFlag = true;
      for ( List< CellDataDTO > cellDataDTOs : cellDataDTOPackages )
      {
         if ( !validateData( KANConstants.getKANAccountConstants( accountId ), cellDataDTOs ) )
         {
            validFlag = false;
         }
      }

      if ( !validFlag )
      {
         for ( List< CellDataDTO > cellDataDTOs : cellDataDTOPackages )
         {
            uploadErrorMessage( request, cellDataDTOs );
         }
      }
      else
      {
         for ( List< CellDataDTO > cellDataDTOs : cellDataDTOPackages )
         {
            for ( CellDataDTO cellDataDTO : cellDataDTOs )
            {
               EmployeeContractVO employeeContractVO = getEmployeeContractVOByRow( tableDTO, titleList, cellDataDTO.getCellDatas(), employeeContractDao );
               if ( employeeContractVO == null )
               {
                  errorMessageList.add( "contractId������" );
               }
               else
               {
                  employeeContractVOs.add( employeeContractVO );
               }
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
         else
         {
            if ( employeeContractVOs.size() > 0 )
            {
               successCount = insertDB( employeeContractVOs, accountId, corpId, EmployeeContractAction.getAccessAction( request, null ), BaseAction.getPositionId( request, null ), BaseAction.getUserId( request, null ), fileName, description );
            }

            UploadFileAction.setStatusMsg( request, "4", "��" + ( employeeContractVOs.size() ) + "����" + successCount + "������ɹ���" );
         }
      }

   }

   // ���뵽���ݿ�
   private static int insertDB( final List< EmployeeContractVO > employeeContractVOs, final String accountId, final String corpId, final String accessAction, final String onwer,
         final String userId, final String fileName, final String description ) throws KANException, InterruptedException
   {
      // ��ȡ���ӳص�����
      final DataSource dataSource = ( DataSource ) ServiceLocator.getService( "dataSource" );
      final CommonBatchDao commonBatchDao = ( CommonBatchDao ) ServiceLocator.getService( "commonBatchDao" );
      final EmployeeContractTempDao employeeContractTempDao = ( EmployeeContractTempDao ) ServiceLocator.getService( "employeeContractTempDao" );
      // ������ݿ�����ʵ��
      Connection connection = null;

      int rows = 0;

      try
      {
         // ��ʼ�����ݿ�����
         connection = dataSource.getConnection();
         // ��������
         connection.setAutoCommit( false );

         // ����һ������
         final CommonBatchVO commonBatchVO = new CommonBatchVO();
         commonBatchVO.setAccountId( accountId );
         commonBatchVO.setCorpId( corpId );
         commonBatchVO.setAccessAction( accessAction );
         commonBatchVO.setImportExcelName( fileName );
         commonBatchVO.setOwner( onwer );
         commonBatchVO.setDescription( description );
         commonBatchVO.setStatus( "1" );
         commonBatchVO.setDeleted( "1" );
         commonBatchVO.setRemark4( "2" );
         commonBatchVO.setCreateBy( userId );
         commonBatchVO.setModifyBy( userId );
         commonBatchDao.insertCommonBatch( commonBatchVO );

         for ( EmployeeContractVO employeeContractVO : employeeContractVOs )
         {
            final EmployeeContractTempVO employeeContractTempVO = new EmployeeContractTempVO();
            employeeContractTempVO.setAccountId( accountId );
            employeeContractTempVO.setCorpId( corpId );
            employeeContractTempVO.update( employeeContractVO );
            employeeContractTempVO.setBatchId( commonBatchVO.getBatchId() );
            employeeContractTempVO.setRemark1( employeeContractVO.getRemark1() );
            employeeContractTempVO.setRemark3( employeeContractVO.getRemark3() );
            employeeContractTempVO.setRemark4( employeeContractVO.getContractId() );
            employeeContractTempVO.setCreateBy( userId );
            employeeContractTempVO.setCreateDate( new Date() );
            employeeContractTempVO.setModifyBy( userId );
            employeeContractTempVO.setModifyDate( new Date() );

            rows = rows + employeeContractTempDao.insertEmployeeContractTemp( employeeContractTempVO );
         }

         // �ύ���� - Finally
         connection.commit();
      }
      catch ( Exception e )
      {
         try
         {
            // �ع�����
            connection.rollback();
         }
         catch ( final Exception e1 )
         {
            throw new KANException( e );
         }

         throw new KANException( e );
      }
      finally
      {
         try
         {
            if ( connection != null )
            {
               connection.close();
            }
         }
         catch ( final Exception e1 )
         {
            throw new KANException( e1 );
         }
      }

      return rows;
   }

   protected static EmployeeContractVO getEmployeeContractVOByRow( final TableDTO tableDTO, final List< String > titleList, final List< CellData > cells,
         final EmployeeContractDao employeeContractDao ) throws KANException
   {
      final String contractId = cells.get( 0 ).getValue();

      if ( contractId == null || "".equals( contractId ) )
         return null;

      EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( contractId );

      if ( employeeContractVO == null )
         return null;

      for ( CellData cell : cells )
      {
         if ( cell.getColumn() == 0 )
            continue;

         String name = titleList.get( cell.getColumn() );

         ColumnVO columnVO = tableDTO.getColumnVOByName( name );

         if ( columnVO != null )
         {
            if ( KANConstants.SUPER_ACCOUNT_ID.equals( columnVO.getAccountId() ) )
            {
               KANUtil.setValue( employeeContractVO, columnVO.getNameDB(), cell.getDbValue() == null ? null : cell.getDbValue().toString() );
            }
            // �Զ����ֶ�
            else
            {
               JSONObject jsonObject = JSONObject.fromObject( employeeContractVO.getRemark1() );
               if ( jsonObject != null )
               {
                  jsonObject.put( columnVO.getNameDB(), cell.getDbValue() == null ? "" : cell.getDbValue().toString() );
               }

               employeeContractVO.setRemark1( jsonObject.toString() );
            }
         }
      }

      return employeeContractVO;
   }

   private static CellDataDTO prepareCellDataDTO( final TableDTO tableDTO, final List< String > titleList, final List< CellData > cells ) throws KANException
   {
      // ��ʼ��CellDataDTO
      final CellDataDTO cellDataDTO = new CellDataDTO();

      for ( CellData cell : cells )
      {
         String name = titleList.get( cell.getColumn() );
         ColumnVO columnVO = tableDTO.getColumnVOByName( name );
         cell.setColumnVO( columnVO );

         cellDataDTO.getCellDatas().add( cell );
      }

      return cellDataDTO;
   }

   private static boolean validateData( final KANAccountConstants accountConstants, final List< CellDataDTO > cellDataDTOs ) throws KANException
   {
      // ��֤���
      boolean flag = true;
      // ����������
      for ( CellDataDTO cellDataDTO : cellDataDTOs )
      {
         if ( cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() >= 0 )
         {
            if ( !validateRow( accountConstants, cellDataDTO ) )
            {
               flag = false;
            }
         }
      }

      return flag;
   }

   private static boolean validateRow( final KANAccountConstants accountConstants, final CellDataDTO cellDataDTO ) throws KANException
   {
      boolean flag = true;

      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null )
      {
         for ( CellData cell : cellDataDTO.getCellDatas() )
         {
            switch ( validateCell( accountConstants, cell ) )
            {
               case 1:
                  break;
               case -1:
                  flag = false;
                  break;
               case 2:
                  break;

               default:
                  break;
            }
         }
      }

      return flag;
   }

   /**
    * 
   *  Validate Cell
   *  
   *  @param request
   *  @param accountConstants
   *  @param cell
   *  @param columnVO
   *  @return  -1:��֤����,0:��֤ͨ��,1:������,2:��������
    */
   private static int validateCell( final KANAccountConstants accountConstants, final CellData cell )
   {
      final ColumnVO columnVO = cell.getColumnVO();

      // ��֤���
      int flag = 0;
      Pattern pattern = Pattern.compile( "^(?:(?!0000)[0-9]{4}[-/\\.](?:(?:0[1-9]|1[0-2])[-/\\.](?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])[-/\\.](?:29|30)|(?:0[13578]|1[02])[-/\\.]31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)[-/\\.]02[-/\\.]29)(\\s+([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?)?$" );

      // ��֤�Ƿ����
      if ( KANUtil.filterEmpty( columnVO.getIsRequired() ) != null && columnVO.getIsRequired().equals( "1" ) )
      {
         if ( cell == null || KANUtil.filterEmpty( cell.getValue() ) == null )
         {
            cell.setErrorMessange( "[" + cell.getCellRef() + "]��ֵ����Ϊ�գ�" );
            flag = -1;
         }
      }

      // ��Ϊ�ղ���֤
      if ( cell != null && KANUtil.filterEmpty( cell.getValue() ) != null )
      {

         if ( cell.getValue() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) )
         {
            if ( !cell.getValue().trim().matches( "(?!0000)[0-9]{4}[-/\\.](?:0[1-9]|1[0-2])" ) )
            {
               cell.setErrorMessange( "[" + cell.getCellRef() + "]��ʱ�䲻��ȷ��" );
               flag = -1;
            }
         }

         // �Ƿ���ѡ���  { 2��������3����ѡ��4����ѡ��}
         boolean isSelect = KANUtil.filterEmpty( columnVO.getInputType() ) != null
               && ( KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "2" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "3" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "4" ) );

         // ��֤������
         if ( isSelect )
         {
            cell.setJdbcDataType( JDBCDataType.VARCHAR );

            if ( KANUtil.filterEmpty( cell.getValue() ) == null )
            {
               cell.setDbValue( "0" );
            }
            else
            {
               final String value = columnVO.getOptionValueByOptionName( cell.getValue() );

               if ( KANUtil.filterEmpty( value ) != null && value.equals( "0" ) )
               {
               }

               cell.setDbValue( value );
            }
         }
         // ��֤�ֶ�ֵ������  --- ��������
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getValueType().equals( "1" ) )
         {
            cell.setJdbcDataType( JDBCDataType.NUMBER );

            if ( !KANUtil.round( cell.getValue(), accountConstants.OPTIONS_ACCURACY, accountConstants.OPTIONS_ROUND ).matches( "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,4})?$" ) )
            {
               cell.setErrorMessange( "[" + cell.getCellRef() + "]������Ч��ֵ," );
               flag = -1;
            }
            else
            {
               // ��ʼ���ֶδ�С(����)
               double maxRange = 0;
               if ( KANUtil.filterEmpty( columnVO.getValidateRangeMax() ) != null )
               {
                  maxRange = Double.parseDouble( columnVO.getValidateRangeMax() );
               }

               // ��ʼ���ֶδ�С(����)
               double minRange = 0;
               if ( KANUtil.filterEmpty( columnVO.getValidateRangeMin() ) != null )
               {
                  minRange = Double.parseDouble( columnVO.getValidateRangeMin() );
               }

               if ( maxRange != minRange && maxRange != 0 )
               {
                  double cellValue = Double.parseDouble( cell.getValue() );
                  if ( cellValue > maxRange || cellValue < minRange )
                  {
                     cell.setErrorMessange( "[" + cell.getCellRef() + "]��ֵ����" + columnVO.getValidateRangeMin() + "��" + columnVO.getValidateRangeMax() + "֮��," );
                     flag = -1;
                  }
               }

               cell.setDbValue( cell.getValue() );
            }

         }
         //  �ֶ�ֵ������  --- �ַ�����
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getValueType().equals( "2" ) )
         {
            cell.setJdbcDataType( JDBCDataType.VARCHAR );

            // ��ʼ���ֶγ�������
            int maxlength = 0;
            if ( KANUtil.filterEmpty( columnVO.getValidateLengthMax() ) != null )
            {
               maxlength = Integer.parseInt( columnVO.getValidateLengthMax() );
            }

            // ��ʼ���ֶγ�������
            int minlength = 0;
            if ( KANUtil.filterEmpty( columnVO.getValidateLengthMin() ) != null )
            {
               minlength = Integer.parseInt( columnVO.getValidateLengthMin() );
            }

            if ( maxlength != minlength && maxlength != 0 )
            {
               int cellValueLength = cell.getValue().length();
               if ( cellValueLength > maxlength || cellValueLength < minlength )
               {
                  cell.setErrorMessange( "[" + cell.getCellRef() + "]���ַ����Ȳ���" + columnVO.getValidateRangeMin() + "��" + columnVO.getValidateRangeMax() + "֮��," );
                  flag = -1;
               }

            }

            cell.setDbValue( cell.getValue() );
         }
         //  �ֶ�ֵ������  --- ��������
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getValueType().equals( "3" ) )
         {
            cell.setJdbcDataType( JDBCDataType.DATE );

            if ( cell.getDataType() == XssfDataType.NUMBER )
            {
               cell.setDbValue( KANUtil.formatDate( HSSFDateUtil.getJavaDate( Double.parseDouble( cell.getValue() ) ), "yyyy-MM-dd HH:mm:ss" ) );
               cell.setFormateValue( KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) );
               cell.setValue( KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) );
            }
            else
            {
               if ( KANUtil.filterEmpty( cell.getValue() ) != null )
               {
                  if ( pattern.matcher( cell.getValue().trim() ).matches() )
                  {
                     cell.setDbValue( KANUtil.createDate( cell.getValue() ) );
                  }
                  else
                  {
                     cell.setErrorMessange( "[" + cell.getCellRef() + "]��ʱ�䲻��ȷ��" );
                     flag = -1;
                  }
               }
            }
         }
         else
         {
            cell.setJdbcDataType( JDBCDataType.VARCHAR );
            cell.setDbValue( cell.getValue() );
         }
      }

      return flag;
   }

   /**
    * ���´�����Ϣ��Catch
   *  uploadErrorMessage
    * @throws KANException 
   *
    */
   private static void uploadErrorMessage( final HttpServletRequest request, final List< CellDataDTO > cellDataDTOs ) throws KANException
   {
      //�߼�У�������ʾ��Ϣ
      StringBuffer sb = new StringBuffer( "<br/>" );
      for ( CellDataDTO cellDataDTO : cellDataDTOs )
      {

         //�м�����
         if ( StringUtils.isNotBlank( cellDataDTO.getErrorMessange() ) )
         {
            sb.append( cellDataDTO.getErrorMessange() );
            sb.append( "<br/>" );
         }

         //cell�������
         if ( cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
         {
            for ( CellData cell : cellDataDTO.getCellDatas() )
            {
               if ( StringUtils.isNotBlank( cell.getErrorMessange() ) )
               {
                  sb.append( cell.getErrorMessange() );
                  sb.append( "<br/>" );
               }
            }
         }
         if ( cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
         {
            for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               if ( subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
               {
                  for ( CellData subCell : subCellDataDTO.getCellDatas() )
                  {
                     if ( StringUtils.isNotBlank( subCell.getErrorMessange() ) )
                     {
                        sb.append( subCell.getErrorMessange() );
                        sb.append( "<br/>" );
                     }
                  }
               }
            }
         }
      }

      UploadFileAction.setStatusMsg( request, "1", sb.toString(), "2" );
   }

}
