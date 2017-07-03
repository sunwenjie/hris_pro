package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ColumnDao;
import com.kan.base.dao.inf.define.ColumnGroupDao;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.ReportHeaderDao;
import com.kan.base.dao.inf.define.ReportRelationDao;
import com.kan.base.dao.inf.define.ReportSearchDetailDao;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.dao.inf.define.SearchHeaderDao;
import com.kan.base.dao.inf.define.TableDao;
import com.kan.base.dao.inf.define.TableRelationDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.SearchDTO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.service.inf.define.TableService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TableServiceImpl extends ContextService implements TableService
{
   static
   {
      SOME_SPECIAL_VIEW_ID_MAP.put( "118", new String[] { "69", "71" } );
      SOME_SPECIAL_VIEW_ID_MAP.put( "119", new String[] { "69", "71" } );
   }

   private ColumnDao columnDao;

   private ColumnGroupDao columnGroupDao;

   private SearchHeaderDao searchHeaderDao;

   private SearchDetailDao searchDetailDao;

   private TableRelationDao tableRelationDao;

   private ListHeaderDao listHeaderDao;

   private ListDetailDao listDetailDao;

   private ReportHeaderDao reportHeaderDao;

   private ReportDetailDao reportDetailDao;

   private ReportRelationDao reportRelationDao;

   private ReportSearchDetailDao reportSearchDetailDao;

   // ע��ManagerHeaderService
   private ManagerHeaderService managerHeaderService;

   public ColumnDao getColumnDao()
   {
      return columnDao;
   }

   public void setColumnDao( ColumnDao columnDao )
   {
      this.columnDao = columnDao;
   }

   public ColumnGroupDao getColumnGroupDao()
   {
      return columnGroupDao;
   }

   public void setColumnGroupDao( ColumnGroupDao columnGroupDao )
   {
      this.columnGroupDao = columnGroupDao;
   }

   public SearchHeaderDao getSearchHeaderDao()
   {
      return searchHeaderDao;
   }

   public void setSearchHeaderDao( SearchHeaderDao searchHeaderDao )
   {
      this.searchHeaderDao = searchHeaderDao;
   }

   public SearchDetailDao getSearchDetailDao()
   {
      return searchDetailDao;
   }

   public void setSearchDetailDao( SearchDetailDao searchDetailDao )
   {
      this.searchDetailDao = searchDetailDao;
   }

   public ListHeaderDao getListHeaderDao()
   {
      return listHeaderDao;
   }

   public void setListHeaderDao( ListHeaderDao listHeaderDao )
   {
      this.listHeaderDao = listHeaderDao;
   }

   public ReportHeaderDao getReportHeaderDao()
   {
      return reportHeaderDao;
   }

   public void setReportHeaderDao( ReportHeaderDao reportHeaderDao )
   {
      this.reportHeaderDao = reportHeaderDao;
   }

   public ReportDetailDao getReportDetailDao()
   {
      return reportDetailDao;
   }

   public void setReportDetailDao( ReportDetailDao reportDetailDao )
   {
      this.reportDetailDao = reportDetailDao;
   }

   public ListDetailDao getListDetailDao()
   {
      return listDetailDao;
   }

   public void setListDetailDao( ListDetailDao listDetailDao )
   {
      this.listDetailDao = listDetailDao;
   }

   public ReportSearchDetailDao getReportSearchDetailDao()
   {
      return reportSearchDetailDao;
   }

   public void setReportSearchDetailDao( ReportSearchDetailDao reportSearchDetailDao )
   {
      this.reportSearchDetailDao = reportSearchDetailDao;
   }

   public ManagerHeaderService getManagerHeaderService()
   {
      return managerHeaderService;
   }

   public void setManagerHeaderService( ManagerHeaderService managerHeaderService )
   {
      this.managerHeaderService = managerHeaderService;
   }

   @Override
   public PagedListHolder getTableVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TableDao tableDao = ( TableDao ) getDao();
      pagedListHolder.setHolderSize( tableDao.countTableVOsByCondition( ( TableVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( tableDao.getTableVOsByCondition( ( TableVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( tableDao.getTableVOsByCondition( ( TableVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public TableVO getTableVOByTableId( final String tableId ) throws KANException
   {
      return ( ( TableDao ) getDao() ).getTableVOByTableId( tableId );
   }

   @Override
   public List< Object > getAvailableTableVOs() throws KANException
   {
      return ( ( TableDao ) getDao() ).getTableVOsByCondition( null );
   }

   @Override
   public List< TableDTO > getTableDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ��DTO�б����
      final List< TableDTO > tableDTOs = new ArrayList< TableDTO >();

      // ��ȡ������Ч��Table
      final List< Object > tableVOs = ( ( TableDao ) getDao() ).getTableVOsByCondition( null );

      // ��ȡAccount����ЧManagerDTO
      final List< ManagerDTO > managerDTOs = this.getManagerHeaderService().getManagerDTOsByAccountId( accountId );

      TableDTO tableDTO;
      // ����TableVO�б�
      if ( tableVOs != null && tableVOs.size() > 0 )
      {
         for ( Object tableVOObject : tableVOs )
         {
            // ��ȡManagerDTO
            final ManagerDTO managerDTO = getManagerDTOByTableId( managerDTOs, ( ( TableVO ) tableVOObject ).getTableId() );
            // ����TableDTO���б�
            tableDTO = this.getTableDTOByTableId( accountId, ( ( TableVO ) tableVOObject ).getTableId(), managerDTO );
            tableDTOs.add( tableDTO );
            /** װ��Manager�����������Table - End */

            // ����TableDTO���б�
            // tableDTOs.add( tableDTO );
         }
      }

      if ( tableDTOs != null && tableDTOs.size() > 0 )
      {
         for ( TableDTO dto : tableDTOs )
         {
            // һЩ�������ͼ����Ҫ�����ض����Զ����ֶ�
            if ( SOME_SPECIAL_VIEW_ID_MAP.get( dto.getTableVO().getTableId() ) != null )
            {
               String[] str = SOME_SPECIAL_VIEW_ID_MAP.get( dto.getTableVO().getTableId() );
               for ( String temps : str )
               {
                  final TableDTO subDTO = getTableDTOByTableId( tableDTOs, temps );
                  if ( subDTO != null )
                  {
                     dto.getDefaultColumnGroupDTO().getColumnVOs().addAll( subDTO.getDefineColumnVOs() );

                     final ColumnVO appendColumnVO = new ColumnVO();
                     appendColumnVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
                     appendColumnVO.setColumnId( "8" );
                     appendColumnVO.setNameDB( "appendSalaryInfo" );
                     appendColumnVO.setNameSys( "#����н����Ϣ" );
                     appendColumnVO.setNameZH( "#����н����Ϣ" );
                     appendColumnVO.setNameEN( "#Append Salary Information" );

                     dto.getDefaultColumnGroupDTO().getColumnVOs().add( appendColumnVO );
                  }
               }
            }
         }
      }

      return tableDTOs;
   }

   /**
    * ��ȡ�����dto �ɾֲ�����tableDTOs
    * 
    * @param accountId
    * @param tableId
    * @return
    * @throws KANException
    */
   @Override
   public TableDTO getTableDTOByTableId( final String accountId, final String tableId, final ManagerDTO managerDTO ) throws KANException
   {

      // ��ʼ��Table����
      final TableVO tableVO = ( ( TableDao ) getDao() ).getTableVOByTableId( tableId );

      // ��ʶ�Ƿ���������
      boolean isSort = false;

      // �������ManagerDTO
      if ( managerDTO != null && managerDTO.getManagerHeaderVO() != null )
      {
         tableVO.setNameZH( managerDTO.getManagerHeaderVO().getNameZH() );
         tableVO.setNameEN( managerDTO.getManagerHeaderVO().getNameEN() );
         tableVO.setComments( managerDTO.getManagerHeaderVO().getComments() );
         isSort = true;
      }

      // ��ʼ��TableDTO
      final TableDTO tableDTO = new TableDTO();
      tableDTO.setTableVO( tableVO );

      /** װ��tableRelation��Table - Start */
      final List< Object > tableRelationList = this.tableRelationDao.getTableVOByTableId( tableVO.getTableId() );
      // ������б�
      if ( tableRelationList != null && tableRelationList.size() > 0 )
      {
         for ( Object tableRelationObject : tableRelationList )
         {
            // ��ʼ����ʱtableRelation
            final TableRelationVO tableRelation = ( TableRelationVO ) tableRelationObject;
            tableDTO.getTableRelationVOs().add( tableRelation );
         }
      }

      /** װ��tableRelation��Table - end */

      /** װ��ColumnGroup����Column�������������Table - Start */
      // ��ʼ��ColumnGroup��Column����
      final List< Object > columnVOs = new ArrayList< Object >();
      // ׼����������
      ColumnVO columnVO = new ColumnVO();
      columnVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      columnVO.setTableId( tableVO.getTableId() );
      columnVO.setStatus( ColumnVO.TRUE );
      columnVO.setSortColumn( "groupId,columnIndex" );
      // װ��ϵͳ������ֶ�
      columnVOs.addAll( this.columnDao.getAccountColumnVOsByCondition( columnVO ) );

      if ( accountId != null && !accountId.trim().equals( KANConstants.SUPER_ACCOUNT_ID ) )
      {
         // װ���˻�������ֶ�
         columnVO.setAccountId( accountId );
         columnVOs.addAll( this.columnDao.getAccountColumnVOsByCondition( columnVO ) );
      }

      if ( accountId != null && !accountId.trim().equals( KANConstants.SUPER_ACCOUNT_ID ) && tableVO != null && KANUtil.filterEmpty( tableVO.getLinkedTableIds() ) != null )
      {
         final String[] linkedTableIdArray = KANUtil.jasonArrayToStringArray( tableVO.getLinkedTableIds() );
         if ( linkedTableIdArray != null && linkedTableIdArray.length > 0 )
         {
            for ( String linkedTableId : linkedTableIdArray )
            {
               // ��ͼ��װ��ԭ��table�Զ����ֶ�
               columnVO.setAccountId( accountId );
               columnVO.setTableId( linkedTableId );
               columnVOs.addAll( this.columnDao.getColumnVOsByCondition( columnVO ) );
            }
         }
      }

      // ����ColumnVO�б�
      if ( columnVOs != null && columnVOs.size() > 0 )
      {
         for ( Object columnVOObject : columnVOs )
         {
            // ��ʼ����ʱColumnVO
            final ColumnVO tempColumnVO = ( ColumnVO ) columnVOObject;

            // �������ҳ���ֶ�
            if ( managerDTO != null && managerDTO.getManagerDetailVOs() != null && managerDTO.getManagerDetailVOs().size() > 0 )
            {
               for ( ManagerDetailVO managerDetailVO : managerDTO.getManagerDetailVOs() )
               {
                  // ��ǰ�ֶδ������Զ���ҳ��
                  if ( managerDetailVO.getColumnId().trim().equals( tempColumnVO.getColumnId() ) )
                  {
                     tempColumnVO.setManagerNameZH( managerDetailVO.getNameZH() );
                     tempColumnVO.setManagerNameEN( managerDetailVO.getNameEN() );
                     tempColumnVO.setGroupId( managerDetailVO.getGroupId() );
                     tempColumnVO.setColumnIndex( managerDetailVO.getColumnIndex() );
                     tempColumnVO.setIsRequired( managerDetailVO.getIsRequired() );
                     tempColumnVO.setDisplayType( managerDetailVO.getDisplay() );
                     tempColumnVO.setUseTitle( managerDetailVO.getUseTitle() );
                     tempColumnVO.setTitleZH( managerDetailVO.getTitleZH() );
                     tempColumnVO.setTitleEN( managerDetailVO.getTitleEN() );
                     tempColumnVO.setAlign( managerDetailVO.getAlign() );
                     break;
                  }
               }
            }

            // �����ǰColumnVOδ���飬�����Ĭ��ColumnGroup����������ֶ��飬����Else����
            if ( KANUtil.filterEmpty( tempColumnVO.getGroupId() ) == null )
            {
               tableDTO.getDefaultColumnGroupDTO().getColumnVOs().add( tempColumnVO );
            }
            // �����ǰ�ֶ��з��飬ColumnVO���뵽��Ӧ��ColumnGroup��
            else
            {
               final ColumnGroupVO columnGroupVO = this.columnGroupDao.getColumnGroupVOByGroupId( ( ( ColumnVO ) columnVOObject ).getGroupId() );
               tableDTO.getColumnGroupDTO( columnGroupVO ).getColumnVOs().add( tempColumnVO );
            }
         }
      }

      // ��������
      if ( isSort )
      {
         columnSort( tableDTO );
      }
      /** װ��ColumnGroup����Column����Table - End */

      /** װ��List����������Table - Start */
      // ��ʼ��List��׼����������
      final ListHeaderVO listHeaderVO = new ListHeaderVO();
      listHeaderVO.setAccountId( accountId );
      listHeaderVO.setTableId( tableVO.getTableId() );
      listHeaderVO.setStatus( ListHeaderVO.TRUE );
      // ��ȡ�û������List
      final List< Object > listHeaderVOs = this.getListHeaderDao().getAccountListHeaderVOsByCondition( listHeaderVO );

      // ����ListHeaderVO�б�
      if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
      {
         for ( Object listHeaderVOObject : listHeaderVOs )
         {
            // ��ʼ��ListDTO
            final ListDTO listDTO = new ListDTO();
            // װ��ListHeaderVO��ListDTO����
            listDTO.setListHeaderVO( ( ListHeaderVO ) listHeaderVOObject );

            // ��ȡ��ǰListHeader��Ӧ��ListDetail
            final List< Object > listDetailVOs = this.getListDetailDao().getListDetailVOsByListHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getListHeaderId() );
            // ����ListDetailVO�б�
            if ( listDetailVOs != null && listDetailVOs.size() > 0 )
            {
               for ( Object listDetailVOObject : listDetailVOs )
               {
                  // װ��ListDetailVO��ListDTO����
                  listDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
               }
            }

            /** װ��Search��List - Start */
            // ��ʼ��SearchDTO
            final SearchDTO searchDTO = new SearchDTO();

            // װ��SearchHeaderVO��SearchDTO
            final SearchHeaderVO searchHeaderVO = this.getSearchHeaderDao().getSearchHeaderVOBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            searchDTO.setSearchHeaderVO( searchHeaderVO );

            // װ��SearchDetailVO��SearchDTO
            final List< Object > searchDetailVOs = this.getSearchDetailDao().getSearchDetailVOsBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            // ����SearchDetailVO�б�
            if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
            {
               for ( Object searchDetailVOObject : searchDetailVOs )
               {
                  // װ��SearchDetailVO��SearchDTO����
                  searchDTO.getSearchDetailVOs().add( ( SearchDetailVO ) searchDetailVOObject );
               }
            }
            listDTO.setSearchDTO( searchDTO );
            /** װ��Search��List - End */

            tableDTO.getListDTOs().add( listDTO );
         }
      }
      /** װ��List��Table - End */

      /** װ��Report�����������Table - Start */
      // ��ʼ��Report��׼����������
      final List< Object > reportHeaderVOs = new ArrayList< Object >();
      final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
      reportHeaderVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      reportHeaderVO.setTableId( tableVO.getTableId() );
      // װ��ϵͳ����ı���
      reportHeaderVOs.addAll( this.reportHeaderDao.getReportHeaderVOsByCondition( reportHeaderVO ) );
      // װ��Account����ı���
      reportHeaderVO.setAccountId( accountId );
      reportHeaderVOs.addAll( this.reportHeaderDao.getAccountReportHeaderVOsByCondition( reportHeaderVO ) );

      // ����ReportVO�б�
      if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
      {
         final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();
         for ( Object reportHeaderVOObject : reportHeaderVOs )
         {
            // ��ʼ��ReportDTO
            final ReportDTO reportDTO = new ReportDTO();
            // װ��ReportHeader������ReportDTO
            reportDTO.setReportHeaderVO( ( ReportHeaderVO ) reportHeaderVOObject );

            // ��ʼ��ReportDetailVO��׼����������
            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setAccountId( accountId );
            reportDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportDetailVO.setStatus( ReportDetailVO.TRUE );
            reportDetailVO.setSortColumn( "columnIndex" );

            // װ��ReportDetailVO�б���ReportDTO
            final List< Object > reportDetailVOs = this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );

            // ����ReportDetailVO�б�
            if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
            {
               for ( Object reportDetailVOObject : reportDetailVOs )
               {
                  // װ��ReportDetailVO��ReportDTO����
                  reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
               }
            }

            // ����ReportRelationVO�б� �ӱ�����account
            final List< Object > reportRelationVOs = this.reportRelationDao.getReportRelationVOsByReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );

            if ( reportRelationVOs != null && reportRelationVOs.size() > 0 )
            {
               for ( Object reportRelationVOObject : reportRelationVOs )
               {
                  // װ��ReportRelationVO��ReportDTO����
                  reportDTO.getReportRelationVOs().add( ( ReportRelationVO ) reportRelationVOObject );
               }
            }

            // ��ʼ��ReportSearchDetailVO��׼����������
            final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
            reportSearchDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportSearchDetailVO.setStatus( BaseVO.TRUE );

            // װ��ReportSearchDetailVO��ReportDTO
            final List< Object > reportSearchDetailVOs = this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO );

            // ����reportSearchDetailVO�б�
            if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
            {
               for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
               {
                  // װ��ReportSearchDetailVO��ReportDTO����
                  reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
               }
            }

            // ���ReportDTO���б�
            reportDTOs.add( reportDTO );
         }
         // װ��ReportDTO�б���TableDTO
         tableDTO.setReportDTOs( reportDTOs );
      }
      /** װ��Report��Table - End */

      /** װ��Manager�����������Table - Start */
      // ��ʼ��ManagerDTO�б�
      final List< ManagerDTO > managerDTOs = new ArrayList< ManagerDTO >();

      // ��ʼ����������
      final ManagerHeaderVO managerHeaderVO = new ManagerHeaderVO();
      managerHeaderVO.setAccountId( accountId );
      managerHeaderVO.setTableId( tableVO.getTableId() );
      managerHeaderVO.setStatus( BaseVO.TRUE );

      tableDTO.setManagerDTOs( managerDTOs );
      /** װ��Manager�����������Table - End */

      return tableDTO;
   }

   /**
    * ��ȡ�����dto �ɾֲ�����tableDTOs
    * 
    * @param accountId
    * @param tableId
    * @return
    * @throws KANException
    */
   @Override
   public List getReportDTOByTableId( final String accountId, final String tableId ) throws KANException
   {

      /** װ��Report�����������Table - Start */
      // ��ʼ��Report��׼����������
      final List< Object > reportHeaderVOs = new ArrayList< Object >();
      final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
      reportHeaderVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      reportHeaderVO.setTableId( tableId );
      // װ��ϵͳ����ı���
      reportHeaderVOs.addAll( this.reportHeaderDao.getReportHeaderVOsByCondition( reportHeaderVO ) );
      // װ��Account����ı���
      reportHeaderVO.setAccountId( accountId );
      reportHeaderVOs.addAll( this.reportHeaderDao.getAccountReportHeaderVOsByCondition( reportHeaderVO ) );

      // ����ReportVO�б�
      final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();
      if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
      {
         for ( Object reportHeaderVOObject : reportHeaderVOs )
         {
            // ��ʼ��ReportDTO
            final ReportDTO reportDTO = new ReportDTO();
            // װ��ReportHeader������ReportDTO
            reportDTO.setReportHeaderVO( ( ReportHeaderVO ) reportHeaderVOObject );

            // ��ʼ��ReportDetailVO��׼����������
            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setAccountId( accountId );
            reportDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportDetailVO.setStatus( ReportDetailVO.TRUE );
            reportDetailVO.setSortColumn( "columnIndex" );

            // װ��ReportDetailVO�б���ReportDTO
            final List< Object > reportDetailVOs = this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );

            // ����ReportDetailVO�б�
            if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
            {
               for ( Object reportDetailVOObject : reportDetailVOs )
               {
                  // װ��ReportDetailVO��ReportDTO����
                  reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
               }
            }

            // ����ReportRelationVO�б� �ӱ�����account
            final List< Object > reportRelationVOs = this.reportRelationDao.getReportRelationVOsByReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );

            if ( reportRelationVOs != null && reportRelationVOs.size() > 0 )
            {
               for ( Object reportRelationVOObject : reportRelationVOs )
               {
                  // װ��ReportRelationVO��ReportDTO����
                  reportDTO.getReportRelationVOs().add( ( ReportRelationVO ) reportRelationVOObject );
               }
            }

            // ��ʼ��ReportSearchDetailVO��׼����������
            final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
            reportSearchDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportSearchDetailVO.setStatus( BaseVO.TRUE );

            // װ��ReportSearchDetailVO��ReportDTO
            final List< Object > reportSearchDetailVOs = this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO );

            // ����reportSearchDetailVO�б�
            if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
            {
               for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
               {
                  // װ��ReportSearchDetailVO��ReportDTO����
                  reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
               }
            }

            // ���ReportDTO���б�
            reportDTOs.add( reportDTO );
         }
      }
      /** װ��Report��Table - End */

      return reportDTOs;
   }

   private Map< String, List< ColumnVO >> getTableColumnMapByAccountId( final String accountId ) throws KANException
   {
      // ׼����������
      ColumnVO columnVO = new ColumnVO();
      columnVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      // columnVO.setTableId( tableVO.getTableId() );
      columnVO.setStatus( ColumnVO.TRUE );
      columnVO.setSortColumn( "tableId,groupId,columnIndex" );

      List< Object > columnVOList = this.columnDao.getAccountColumnVOsByCondition( columnVO );

      Map< String, List< ColumnVO >> tableColumnVOMap = new HashMap< String, List< ColumnVO >>();
      ColumnVO columnVOTemp = null;
      String tableIdTemp = null;
      List< ColumnVO > columnVOListTemp = null;
      for ( Object object : columnVOList )
      {
         columnVOTemp = ( ColumnVO ) object;
         tableIdTemp = columnVOTemp.getTableId();
         if ( tableColumnVOMap.containsKey( tableIdTemp ) )
         {
            columnVOListTemp = tableColumnVOMap.get( tableIdTemp );
            //ϵͳcolumn ���� ��ǰaccountId ��column
            if ( KANConstants.SUPER_ACCOUNT_ID.equals( columnVOTemp.getAccountId() ) || accountId.equals( columnVOTemp.getAccountId() ) )
            {
               if ( columnVOListTemp == null )
               {
                  columnVOListTemp = new ArrayList< ColumnVO >();
                  tableColumnVOMap.put( tableIdTemp, columnVOListTemp );
               }
               columnVOListTemp.add( columnVOTemp );
            }
         }
      }

      return tableColumnVOMap;
   }

   private Map< String, List< ListDTO >> getTableListHeaderMapByAccountId( final String accountId ) throws KANException
   {

      Map< String, List< ListDTO >> tableListHeaderVOMap = new HashMap< String, List< ListDTO >>();
      // ��ʼ��List��׼����������
      //       final ListHeaderVO listHeaderVO = new ListHeaderVO();
      //       listHeaderVO.setAccountId( accountId );
      //       // listHeaderVO.setTableId( tableVO.getTableId() );
      //       listHeaderVO.setStatus( ListHeaderVO.TRUE );
      String tableId = null;
      // ListHeader
      final List< Object > listHeaderList = this.getListHeaderDao().getListHeaderVOsByAccountId( accountId );
      //liekDetail 
      final ListDetailVO listDetailVO = new ListDetailVO();
      listDetailVO.setStatus( ListDetailVO.TRUE );
      listDetailVO.setAccountId( accountId );
      final List< Object > listDetailList = this.getListDetailDao().getListDetailVOsByCondition( listDetailVO );

      //SearchHeader
      final List< Object > searchHeaderList = this.getSearchHeaderDao().getSearchHeaderVOsByAccountId( accountId );

      final SearchDetailVO searchDetailVO = new SearchDetailVO();
      searchDetailVO.setAccountId( accountId );
      searchDetailVO.setStatus( SearchDetailVO.TRUE );
      // SearchDetailVO
      final List< Object > searchDetailVOs = this.getSearchDetailDao().getSearchDetailVOsByCondition( searchDetailVO );

      //��ʼװ��dto
      ListHeaderVO listHeaderVOTemp = null;
      ListDetailVO listDetailVOTemp = null;
      SearchHeaderVO searchHeaderVOTemp = null;
      SearchDetailVO searchDetailVOTemp = null;
      List listDetailVOList = null;
      List searchDetailVOList = null;
      ListDTO listDTOVOTemp = null;
      List< ListDTO > listDTOListTemp = null;
      for ( Object listHeaderObject : listHeaderList )
      {
         listHeaderVOTemp = ( ListHeaderVO ) listHeaderObject;
         tableId = listHeaderVOTemp.getTableId();
         if ( tableListHeaderVOMap.containsKey( tableId ) )
         {
            listDTOListTemp = tableListHeaderVOMap.get( tableId );
            if ( listDTOListTemp == null )
            {
               listDTOListTemp = new ArrayList< ListDTO >();
               tableListHeaderVOMap.put( tableId, listDTOListTemp );
            }
            //����header
            listDTOVOTemp = new ListDTO();
            listDTOVOTemp.setListHeaderVO( listHeaderVOTemp );
            listDetailVOList = new ArrayList();
            //����detail
            for ( Object detailObject : listDetailList )
            {
               listDetailVOTemp = ( ListDetailVO ) detailObject;
               if ( listDetailVOTemp.getListHeaderId().equals( listHeaderVOTemp.getListHeaderId() ) )
               {
                  listDetailVOList.add( listDetailVOTemp );
               }
            }
            listDTOVOTemp.setListDetailVOs( listDetailVOList );

            //����searchheader
            if ( StringUtils.isNotBlank( listHeaderVOTemp.getSearchId() ) )
            {
               // ��ʼ��SearchDTO
               final SearchDTO searchDTO = new SearchDTO();
               searchDetailVOList = new ArrayList();
               for ( Object searchHeaderObject : searchHeaderList )
               {
                  searchHeaderVOTemp = ( SearchHeaderVO ) searchHeaderObject;
                  if ( searchHeaderVOTemp.getSearchHeaderId().equals( listHeaderVOTemp.getSearchId() ) )
                  {
                     searchDTO.setSearchHeaderVO( searchHeaderVOTemp );
                     for ( Object searchDetailVOObject : searchDetailVOs )
                     {
                        // װ��SearchDetailVO��SearchDTO����
                        searchDetailVOTemp = ( SearchDetailVO ) searchDetailVOObject;
                        if ( searchDetailVOTemp.getSearchHeaderId().equals( searchHeaderVOTemp.getSearchHeaderId() ) )
                        {
                           searchDetailVOList.add( searchDetailVOTemp );
                        }
                     }
                     searchDTO.setSearchDetailVOs( searchDetailVOList );
                     break;
                  }
               }
               listDTOVOTemp.setSearchDTO( searchDTO );
            }
            listDTOListTemp.add( listDTOVOTemp );
         }
      }

      return tableListHeaderVOMap;
   }

   public List< TableDTO > getTableDTOsByAccountIdOptimize( final String accountId ) throws KANException
   {
      if ( accountId == null )
         return null;
      // ��ʼ��DTO�б����
      final List< TableDTO > tableDTOs = new ArrayList< TableDTO >();

      // ��ȡ������Ч��Table
      final List< Object > tableVOs = ( ( TableDao ) getDao() ).getTableVOsByCondition( null );

      // ��ȡAccount����ЧManagerDTO
      final List< ManagerDTO > managerDTOs = this.getManagerHeaderService().getManagerDTOsByAccountId( accountId );

      //��ѯ����Ŷ��column ����
      List< Object > columnGroupVOList = columnGroupDao.getColumnGroupVOsByAccountId( accountId );

      //table-columnList   �����
      Map< String, List< ColumnVO >> tableColumnVOMap = this.getTableColumnMapByAccountId( accountId );
      //table-listHeaderList  -begin ���ͷ
      Map< String, List< ListDTO >> tableListHeaderVOMap = this.getTableListHeaderMapByAccountId( accountId );
      //table-listHeaderList  -end
      // ����TableVO�б�
      if ( tableVOs != null && tableVOs.size() > 0 )
      {
         for ( Object tableVOObject : tableVOs )
         {
            // ��ʼ��Table����
            final TableVO tableVO = ( TableVO ) tableVOObject;

            // ��ȡManagerDTO
            final ManagerDTO managerDTO = getManagerDTOByTableId( managerDTOs, tableVO.getTableId() );

            // ��ʶ�Ƿ���������
            boolean isSort = false;

            // �������ManagerDTO
            if ( managerDTO != null && managerDTO.getManagerHeaderVO() != null )
            {
               //tableVO.setNameZH( managerDTO.getManagerHeaderVO().getNameZH() );
               // tableVO.setNameEN( managerDTO.getManagerHeaderVO().getNameEN() );
               tableVO.setComments( managerDTO.getManagerHeaderVO().getComments() );
               isSort = true;
            }

            // ��ʼ��TableDTO
            final TableDTO tableDTO = new TableDTO();
            tableDTO.setTableVO( tableVO );

            /** װ��ColumnGroup����Column�������������Table - Start */
            // ��ʼ��ColumnGroup��Column����
            final List< Object > columnVOs = new ArrayList< Object >();
            final String tableId = tableVO.getTableId();
            final List< ColumnVO > columnVOListTemp = new ArrayList< ColumnVO >();

            columnVOListTemp.addAll( tableColumnVOMap.get( tableId ) );

            if ( accountId != null && !accountId.trim().equals( KANConstants.SUPER_ACCOUNT_ID ) && tableVO != null && KANUtil.filterEmpty( tableVO.getLinkedTableIds() ) != null )
            {
               final String[] linkedTableIdArray = KANUtil.jasonArrayToStringArray( tableVO.getLinkedTableIds() );
               if ( linkedTableIdArray != null && linkedTableIdArray.length > 0 )
               {
                  for ( String linkedTableId : linkedTableIdArray )
                  {
                     // ��ͼ��װ��ԭ��table�Զ����ֶ�
                     columnVOs.addAll( tableColumnVOMap.get( linkedTableId ) );
                  }
               }
            }

            // ����ColumnVO�б�
            if ( columnVOListTemp != null && columnVOListTemp.size() > 0 )
            {
               for ( ColumnVO tempColumnVO : columnVOListTemp )
               {

                  // �������ҳ���ֶ�
                  if ( managerDTO != null && managerDTO.getManagerDetailVOs() != null && managerDTO.getManagerDetailVOs().size() > 0 )
                  {
                     for ( ManagerDetailVO managerDetailVO : managerDTO.getManagerDetailVOs() )
                     {
                        // ��ǰ�ֶδ������Զ���ҳ��
                        if ( managerDetailVO.getColumnId().trim().equals( tempColumnVO.getColumnId() ) )
                        {
                           tempColumnVO.setNameZH( managerDetailVO.getNameZH() );
                           tempColumnVO.setNameEN( managerDetailVO.getNameEN() );
                           tempColumnVO.setGroupId( managerDetailVO.getGroupId() );
                           tempColumnVO.setColumnIndex( managerDetailVO.getColumnIndex() );
                           tempColumnVO.setIsRequired( managerDetailVO.getIsRequired() );
                           tempColumnVO.setDisplayType( managerDetailVO.getDisplay() );
                           tempColumnVO.setUseTitle( managerDetailVO.getUseTitle() );
                           tempColumnVO.setTitleZH( managerDetailVO.getTitleZH() );
                           tempColumnVO.setTitleEN( managerDetailVO.getTitleEN() );
                           tempColumnVO.setAlign( managerDetailVO.getAlign() );
                           break;
                        }
                     }
                  }

                  // �����ǰColumnVOδ���飬�����Ĭ��ColumnGroup����������ֶ��飬����Else����
                  if ( KANUtil.filterEmpty( tempColumnVO.getGroupId() ) == null )
                  {
                     tableDTO.getDefaultColumnGroupDTO().getColumnVOs().add( tempColumnVO );
                  }
                  // �����ǰ�ֶ��з��飬ColumnVO���뵽��Ӧ��ColumnGroup��
                  else
                  {
                     ColumnGroupVO columnGroupVO = null;
                     for ( Object columnGroupObject : columnGroupVOList )
                     {
                        columnGroupVO = ( ColumnGroupVO ) columnGroupObject;
                        if ( columnGroupVO.getGroupId().equals( tempColumnVO.getGroupId() ) )
                           ;
                        tableDTO.getColumnGroupDTO( columnGroupVO ).getColumnVOs().add( tempColumnVO );
                        break;
                     }
                     // final ColumnGroupVO columnGroupVO = this.columnGroupDao.getColumnGroupVOByGroupId( tempColumnVO.getGroupId() );
                  }
               }
            }

            // ��������
            if ( isSort )
            {
               columnSort( tableDTO );
            }
            /** װ��ColumnGroup����Column����Table - End */

            /** װ��List����������Table - Start */
            // ��ȡ�û������List
            final List< ListDTO > listDTOs = tableListHeaderVOMap.get( tableId );
            tableDTO.setListDTOs( listDTOs );
            //            // ����ListHeaderVO�б�
            //            if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
            //            {
            //               for ( ListHeaderVO listHeaderTemp : listHeaderVOs )
            //               {
            //                  // ��ʼ��ListDTO
            //                  final ListDTO listDTO = new ListDTO();
            //                  // װ��ListHeaderVO��ListDTO����
            //                  listDTO.setListHeaderVO( listHeaderTemp );
            //
            //                  // ��ȡ��ǰListHeader��Ӧ��ListDetail
            //                  final List< Object > listDetailVOs = this.getListDetailDao().getListDetailVOsByListHeaderId( listHeaderTemp.getListHeaderId() );
            //                  // ����ListDetailVO�б�
            //                  if ( listDetailVOs != null && listDetailVOs.size() > 0 )
            //                  {
            //                     for ( Object listDetailVOObject : listDetailVOs )
            //                     {
            //                        // װ��ListDetailVO��ListDTO����
            //                        listDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
            //                     }
            //                  }
            //
            //                  /** װ��Search��List - Start */
            //                  // ��ʼ��SearchDTO
            //                  final SearchDTO searchDTO = new SearchDTO();
            //
            //                  // װ��SearchHeaderVO��SearchDTO
            //                  final SearchHeaderVO searchHeaderVO = this.getSearchHeaderDao().getSearchHeaderVOBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            //                  searchDTO.setSearchHeaderVO( searchHeaderVO );
            //
            //                  // װ��SearchDetailVO��SearchDTO
            //                  final List< Object > searchDetailVOs = this.getSearchDetailDao().getSearchDetailVOsBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            //                  // ����SearchDetailVO�б�
            //                  if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
            //                  {
            //                     for ( Object searchDetailVOObject : searchDetailVOs )
            //                     {
            //                        // װ��SearchDetailVO��SearchDTO����
            //                        searchDTO.getSearchDetailVOs().add( ( SearchDetailVO ) searchDetailVOObject );
            //                     }
            //                  }
            //                  listDTO.setSearchDTO( searchDTO );
            //                  /** װ��Search��List - End */
            //
            //                  tableDTO.getListDTOs().add( listDTO );
            //               }
            //            }
            /** װ��List��Table - End */

            /** װ��Report�����������Table - Start */
            // ��ʼ��Report��׼����������
            final List< Object > reportHeaderVOs = new ArrayList< Object >();
            final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
            reportHeaderVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
            reportHeaderVO.setTableId( tableVO.getTableId() );
            // װ��ϵͳ����ı���
            reportHeaderVOs.addAll( this.reportHeaderDao.getReportHeaderVOsByCondition( reportHeaderVO ) );
            // װ��Account����ı���
            reportHeaderVO.setAccountId( accountId );
            reportHeaderVOs.addAll( this.reportHeaderDao.getAccountReportHeaderVOsByCondition( reportHeaderVO ) );

            // ����ReportVO�б�
            if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
            {
               final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();
               for ( Object reportHeaderVOObject : reportHeaderVOs )
               {
                  // ��ʼ��ReportDTO
                  final ReportDTO reportDTO = new ReportDTO();
                  // װ��ReportHeader������ReportDTO
                  reportDTO.setReportHeaderVO( ( ReportHeaderVO ) reportHeaderVOObject );

                  // ��ʼ��ReportDetailVO��׼����������
                  final ReportDetailVO reportDetailVO = new ReportDetailVO();
                  reportDetailVO.setAccountId( accountId );
                  reportDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
                  reportDetailVO.setStatus( ReportDetailVO.TRUE );
                  reportDetailVO.setSortColumn( "columnIndex" );

                  // װ��ReportDetailVO�б���ReportDTO
                  final List< Object > reportDetailVOs = this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );

                  // ����ReportDetailVO�б�
                  if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
                  {
                     for ( Object reportDetailVOObject : reportDetailVOs )
                     {
                        // װ��ReportDetailVO��ReportDTO����
                        reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
                     }
                  }

                  // ��ʼ��ReportSearchDetailVO��׼����������
                  final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
                  reportSearchDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
                  reportSearchDetailVO.setStatus( BaseVO.TRUE );

                  // װ��ReportSearchDetailVO��ReportDTO
                  final List< Object > reportSearchDetailVOs = this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO );

                  // ����reportSearchDetailVO�б�
                  if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
                  {
                     for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
                     {
                        // װ��ReportSearchDetailVO��ReportDTO����
                        reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
                     }
                  }

                  // ���ReportDTO���б�
                  reportDTOs.add( reportDTO );
               }
               // װ��ReportDTO�б���TableDTO
               tableDTO.setReportDTOs( reportDTOs );
            }
            /** װ��Report��Table - End */

            // ����TableDTO���б�
            tableDTOs.add( tableDTO );
         }
      }

      return tableDTOs;
   }

   @Override
   public int insertTable( final TableVO tableVO ) throws KANException
   {
      return ( ( TableDao ) getDao() ).insertTable( tableVO );
   }

   @Override
   public int updateTable( final TableVO tableVO ) throws KANException
   {
      return ( ( TableDao ) getDao() ).updateTable( tableVO );
   }

   @Override
   public int deleteTable( final TableVO tableVO ) throws KANException
   {
      return 0;
   }

   public TableRelationDao getTableRelationDao()
   {
      return tableRelationDao;
   }

   public void setTableRelationDao( TableRelationDao tableRelationDao )
   {
      this.tableRelationDao = tableRelationDao;
   }

   public ReportRelationDao getReportRelationDao()
   {
      return reportRelationDao;
   }

   public void setReportRelationDao( ReportRelationDao reportRelationDao )
   {
      this.reportRelationDao = reportRelationDao;
   }

   // ��ȡManagerDTO
   private ManagerDTO getManagerDTOByTableId( final List< ManagerDTO > managerDTOs, final String tableId )
   {
      if ( managerDTOs != null && managerDTOs.size() > 0 )
      {
         for ( ManagerDTO managerDTO : managerDTOs )
         {
            if ( managerDTO.getManagerHeaderVO().getTableId().trim().equals( tableId ) )
            {
               return managerDTO;
            }
         }
      }

      return null;
   }

   // ����TableId��ö�Ӧ��TableDTO
   private TableDTO getTableDTOByTableId( final List< TableDTO > tableDTOs, final String tableId )
   {
      if ( tableDTOs != null && tableDTOs.size() > 0 )
      {
         for ( TableDTO tableDTO : tableDTOs )
         {
            if ( tableDTO.getTableVO() != null && tableDTO.getTableVO().getTableId() != null && tableDTO.getTableVO().getTableId().trim().equals( tableId ) )
            {
               return tableDTO;
            }
         }
      }
      return null;
   }

   private void columnSort( final TableDTO tableDTO )
   {
      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {
            Collections.sort( columnGroupDTO.getColumnVOs(), new ComparatorColumn() );
         }
      }

   }

   // �ڲ��� - ����columnIndex
   private class ComparatorColumn implements Comparator< ColumnVO >
   {
      @Override
      public int compare( final ColumnVO o1, final ColumnVO o2 )
      {
         if ( KANUtil.filterEmpty( o1.getColumnIndex() ) == null || KANUtil.filterEmpty( o2.getColumnIndex() ) == null )
         {
            return 1;
         }

         return Integer.valueOf( o1.getColumnIndex() ) - Integer.valueOf( o2.getColumnIndex() );
      }
   }

}
