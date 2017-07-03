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

   // 注入ManagerHeaderService
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
      // 初始化DTO列表对象
      final List< TableDTO > tableDTOs = new ArrayList< TableDTO >();

      // 获取所有有效的Table
      final List< Object > tableVOs = ( ( TableDao ) getDao() ).getTableVOsByCondition( null );

      // 获取Account下有效ManagerDTO
      final List< ManagerDTO > managerDTOs = this.getManagerHeaderService().getManagerDTOsByAccountId( accountId );

      TableDTO tableDTO;
      // 遍历TableVO列表
      if ( tableVOs != null && tableVOs.size() > 0 )
      {
         for ( Object tableVOObject : tableVOs )
         {
            // 获取ManagerDTO
            final ManagerDTO managerDTO = getManagerDTOByTableId( managerDTOs, ( ( TableVO ) tableVOObject ).getTableId() );
            // 保存TableDTO至列表
            tableDTO = this.getTableDTOByTableId( accountId, ( ( TableVO ) tableVOObject ).getTableId(), managerDTO );
            tableDTOs.add( tableDTO );
            /** 装载Manager（任意个）至Table - End */

            // 保存TableDTO至列表
            // tableDTOs.add( tableDTO );
         }
      }

      if ( tableDTOs != null && tableDTOs.size() > 0 )
      {
         for ( TableDTO dto : tableDTOs )
         {
            // 一些特殊的视图，需要加入特定的自定义字段
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
                     appendColumnVO.setNameSys( "#附加薪酬信息" );
                     appendColumnVO.setNameZH( "#附加薪酬信息" );
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
    * 获取报表的dto 可局部更新tableDTOs
    * 
    * @param accountId
    * @param tableId
    * @return
    * @throws KANException
    */
   @Override
   public TableDTO getTableDTOByTableId( final String accountId, final String tableId, final ManagerDTO managerDTO ) throws KANException
   {

      // 初始化Table对象
      final TableVO tableVO = ( ( TableDao ) getDao() ).getTableVOByTableId( tableId );

      // 标识是否重新排序
      boolean isSort = false;

      // 如果存在ManagerDTO
      if ( managerDTO != null && managerDTO.getManagerHeaderVO() != null )
      {
         tableVO.setNameZH( managerDTO.getManagerHeaderVO().getNameZH() );
         tableVO.setNameEN( managerDTO.getManagerHeaderVO().getNameEN() );
         tableVO.setComments( managerDTO.getManagerHeaderVO().getComments() );
         isSort = true;
      }

      // 初始化TableDTO
      final TableDTO tableDTO = new TableDTO();
      tableDTO.setTableVO( tableVO );

      /** 装载tableRelation至Table - Start */
      final List< Object > tableRelationList = this.tableRelationDao.getTableVOByTableId( tableVO.getTableId() );
      // 表关联列表
      if ( tableRelationList != null && tableRelationList.size() > 0 )
      {
         for ( Object tableRelationObject : tableRelationList )
         {
            // 初始化临时tableRelation
            final TableRelationVO tableRelation = ( TableRelationVO ) tableRelationObject;
            tableDTO.getTableRelationVOs().add( tableRelation );
         }
      }

      /** 装载tableRelation至Table - end */

      /** 装载ColumnGroup（含Column）（任意个）至Table - Start */
      // 初始化ColumnGroup，Column对象
      final List< Object > columnVOs = new ArrayList< Object >();
      // 准备搜索条件
      ColumnVO columnVO = new ColumnVO();
      columnVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      columnVO.setTableId( tableVO.getTableId() );
      columnVO.setStatus( ColumnVO.TRUE );
      columnVO.setSortColumn( "groupId,columnIndex" );
      // 装载系统定义的字段
      columnVOs.addAll( this.columnDao.getAccountColumnVOsByCondition( columnVO ) );

      if ( accountId != null && !accountId.trim().equals( KANConstants.SUPER_ACCOUNT_ID ) )
      {
         // 装载账户定义的字段
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
               // 视图需装载原来table自定义字段
               columnVO.setAccountId( accountId );
               columnVO.setTableId( linkedTableId );
               columnVOs.addAll( this.columnDao.getColumnVOsByCondition( columnVO ) );
            }
         }
      }

      // 遍历ColumnVO列表
      if ( columnVOs != null && columnVOs.size() > 0 )
      {
         for ( Object columnVOObject : columnVOs )
         {
            // 初始化临时ColumnVO
            final ColumnVO tempColumnVO = ( ColumnVO ) columnVOObject;

            // 如果存在页面字段
            if ( managerDTO != null && managerDTO.getManagerDetailVOs() != null && managerDTO.getManagerDetailVOs().size() > 0 )
            {
               for ( ManagerDetailVO managerDetailVO : managerDTO.getManagerDetailVOs() )
               {
                  // 当前字段存在于自定义页面
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

            // 如果当前ColumnVO未分组，则插入默认ColumnGroup；如果不设字段组，运行Else部分
            if ( KANUtil.filterEmpty( tempColumnVO.getGroupId() ) == null )
            {
               tableDTO.getDefaultColumnGroupDTO().getColumnVOs().add( tempColumnVO );
            }
            // 如果当前字段有分组，ColumnVO插入到相应的ColumnGroup中
            else
            {
               final ColumnGroupVO columnGroupVO = this.columnGroupDao.getColumnGroupVOByGroupId( ( ( ColumnVO ) columnVOObject ).getGroupId() );
               tableDTO.getColumnGroupDTO( columnGroupVO ).getColumnVOs().add( tempColumnVO );
            }
         }
      }

      // 重新排序
      if ( isSort )
      {
         columnSort( tableDTO );
      }
      /** 装载ColumnGroup（含Column）至Table - End */

      /** 装载List（单个）至Table - Start */
      // 初始化List，准备搜索条件
      final ListHeaderVO listHeaderVO = new ListHeaderVO();
      listHeaderVO.setAccountId( accountId );
      listHeaderVO.setTableId( tableVO.getTableId() );
      listHeaderVO.setStatus( ListHeaderVO.TRUE );
      // 获取用户定义的List
      final List< Object > listHeaderVOs = this.getListHeaderDao().getAccountListHeaderVOsByCondition( listHeaderVO );

      // 遍历ListHeaderVO列表
      if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
      {
         for ( Object listHeaderVOObject : listHeaderVOs )
         {
            // 初始化ListDTO
            final ListDTO listDTO = new ListDTO();
            // 装载ListHeaderVO到ListDTO对象
            listDTO.setListHeaderVO( ( ListHeaderVO ) listHeaderVOObject );

            // 获取当前ListHeader对应的ListDetail
            final List< Object > listDetailVOs = this.getListDetailDao().getListDetailVOsByListHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getListHeaderId() );
            // 遍历ListDetailVO列表
            if ( listDetailVOs != null && listDetailVOs.size() > 0 )
            {
               for ( Object listDetailVOObject : listDetailVOs )
               {
                  // 装载ListDetailVO到ListDTO对象
                  listDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
               }
            }

            /** 装载Search至List - Start */
            // 初始化SearchDTO
            final SearchDTO searchDTO = new SearchDTO();

            // 装载SearchHeaderVO至SearchDTO
            final SearchHeaderVO searchHeaderVO = this.getSearchHeaderDao().getSearchHeaderVOBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            searchDTO.setSearchHeaderVO( searchHeaderVO );

            // 装载SearchDetailVO至SearchDTO
            final List< Object > searchDetailVOs = this.getSearchDetailDao().getSearchDetailVOsBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            // 遍历SearchDetailVO列表
            if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
            {
               for ( Object searchDetailVOObject : searchDetailVOs )
               {
                  // 装载SearchDetailVO到SearchDTO对象
                  searchDTO.getSearchDetailVOs().add( ( SearchDetailVO ) searchDetailVOObject );
               }
            }
            listDTO.setSearchDTO( searchDTO );
            /** 装载Search至List - End */

            tableDTO.getListDTOs().add( listDTO );
         }
      }
      /** 装载List至Table - End */

      /** 装载Report（任意个）至Table - Start */
      // 初始化Report，准备搜索条件
      final List< Object > reportHeaderVOs = new ArrayList< Object >();
      final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
      reportHeaderVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      reportHeaderVO.setTableId( tableVO.getTableId() );
      // 装载系统定义的报表
      reportHeaderVOs.addAll( this.reportHeaderDao.getReportHeaderVOsByCondition( reportHeaderVO ) );
      // 装载Account定义的报表
      reportHeaderVO.setAccountId( accountId );
      reportHeaderVOs.addAll( this.reportHeaderDao.getAccountReportHeaderVOsByCondition( reportHeaderVO ) );

      // 遍历ReportVO列表
      if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
      {
         final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();
         for ( Object reportHeaderVOObject : reportHeaderVOs )
         {
            // 初始化ReportDTO
            final ReportDTO reportDTO = new ReportDTO();
            // 装载ReportHeader对象至ReportDTO
            reportDTO.setReportHeaderVO( ( ReportHeaderVO ) reportHeaderVOObject );

            // 初始化ReportDetailVO，准备搜索条件
            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setAccountId( accountId );
            reportDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportDetailVO.setStatus( ReportDetailVO.TRUE );
            reportDetailVO.setSortColumn( "columnIndex" );

            // 装载ReportDetailVO列表至ReportDTO
            final List< Object > reportDetailVOs = this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );

            // 遍历ReportDetailVO列表
            if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
            {
               for ( Object reportDetailVOObject : reportDetailVOs )
               {
                  // 装载ReportDetailVO到ReportDTO对象
                  reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
               }
            }

            // 遍历ReportRelationVO列表 子表不区分account
            final List< Object > reportRelationVOs = this.reportRelationDao.getReportRelationVOsByReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );

            if ( reportRelationVOs != null && reportRelationVOs.size() > 0 )
            {
               for ( Object reportRelationVOObject : reportRelationVOs )
               {
                  // 装载ReportRelationVO到ReportDTO对象
                  reportDTO.getReportRelationVOs().add( ( ReportRelationVO ) reportRelationVOObject );
               }
            }

            // 初始化ReportSearchDetailVO，准备搜索条件
            final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
            reportSearchDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportSearchDetailVO.setStatus( BaseVO.TRUE );

            // 装载ReportSearchDetailVO至ReportDTO
            final List< Object > reportSearchDetailVOs = this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO );

            // 遍历reportSearchDetailVO列表
            if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
            {
               for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
               {
                  // 装载ReportSearchDetailVO到ReportDTO对象
                  reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
               }
            }

            // 添加ReportDTO至列表
            reportDTOs.add( reportDTO );
         }
         // 装载ReportDTO列表至TableDTO
         tableDTO.setReportDTOs( reportDTOs );
      }
      /** 装载Report至Table - End */

      /** 装载Manager（任意个）至Table - Start */
      // 初始化ManagerDTO列表
      final List< ManagerDTO > managerDTOs = new ArrayList< ManagerDTO >();

      // 初始化搜索条件
      final ManagerHeaderVO managerHeaderVO = new ManagerHeaderVO();
      managerHeaderVO.setAccountId( accountId );
      managerHeaderVO.setTableId( tableVO.getTableId() );
      managerHeaderVO.setStatus( BaseVO.TRUE );

      tableDTO.setManagerDTOs( managerDTOs );
      /** 装载Manager（任意个）至Table - End */

      return tableDTO;
   }

   /**
    * 获取报表的dto 可局部更新tableDTOs
    * 
    * @param accountId
    * @param tableId
    * @return
    * @throws KANException
    */
   @Override
   public List getReportDTOByTableId( final String accountId, final String tableId ) throws KANException
   {

      /** 装载Report（任意个）至Table - Start */
      // 初始化Report，准备搜索条件
      final List< Object > reportHeaderVOs = new ArrayList< Object >();
      final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
      reportHeaderVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      reportHeaderVO.setTableId( tableId );
      // 装载系统定义的报表
      reportHeaderVOs.addAll( this.reportHeaderDao.getReportHeaderVOsByCondition( reportHeaderVO ) );
      // 装载Account定义的报表
      reportHeaderVO.setAccountId( accountId );
      reportHeaderVOs.addAll( this.reportHeaderDao.getAccountReportHeaderVOsByCondition( reportHeaderVO ) );

      // 遍历ReportVO列表
      final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();
      if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
      {
         for ( Object reportHeaderVOObject : reportHeaderVOs )
         {
            // 初始化ReportDTO
            final ReportDTO reportDTO = new ReportDTO();
            // 装载ReportHeader对象至ReportDTO
            reportDTO.setReportHeaderVO( ( ReportHeaderVO ) reportHeaderVOObject );

            // 初始化ReportDetailVO，准备搜索条件
            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setAccountId( accountId );
            reportDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportDetailVO.setStatus( ReportDetailVO.TRUE );
            reportDetailVO.setSortColumn( "columnIndex" );

            // 装载ReportDetailVO列表至ReportDTO
            final List< Object > reportDetailVOs = this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );

            // 遍历ReportDetailVO列表
            if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
            {
               for ( Object reportDetailVOObject : reportDetailVOs )
               {
                  // 装载ReportDetailVO到ReportDTO对象
                  reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
               }
            }

            // 遍历ReportRelationVO列表 子表不区分account
            final List< Object > reportRelationVOs = this.reportRelationDao.getReportRelationVOsByReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );

            if ( reportRelationVOs != null && reportRelationVOs.size() > 0 )
            {
               for ( Object reportRelationVOObject : reportRelationVOs )
               {
                  // 装载ReportRelationVO到ReportDTO对象
                  reportDTO.getReportRelationVOs().add( ( ReportRelationVO ) reportRelationVOObject );
               }
            }

            // 初始化ReportSearchDetailVO，准备搜索条件
            final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
            reportSearchDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
            reportSearchDetailVO.setStatus( BaseVO.TRUE );

            // 装载ReportSearchDetailVO至ReportDTO
            final List< Object > reportSearchDetailVOs = this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO );

            // 遍历reportSearchDetailVO列表
            if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
            {
               for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
               {
                  // 装载ReportSearchDetailVO到ReportDTO对象
                  reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
               }
            }

            // 添加ReportDTO至列表
            reportDTOs.add( reportDTO );
         }
      }
      /** 装载Report至Table - End */

      return reportDTOs;
   }

   private Map< String, List< ColumnVO >> getTableColumnMapByAccountId( final String accountId ) throws KANException
   {
      // 准备搜索条件
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
            //系统column 或者 当前accountId 的column
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
      // 初始化List，准备搜索条件
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

      //开始装载dto
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
            //处理header
            listDTOVOTemp = new ListDTO();
            listDTOVOTemp.setListHeaderVO( listHeaderVOTemp );
            listDetailVOList = new ArrayList();
            //处理detail
            for ( Object detailObject : listDetailList )
            {
               listDetailVOTemp = ( ListDetailVO ) detailObject;
               if ( listDetailVOTemp.getListHeaderId().equals( listHeaderVOTemp.getListHeaderId() ) )
               {
                  listDetailVOList.add( listDetailVOTemp );
               }
            }
            listDTOVOTemp.setListDetailVOs( listDetailVOList );

            //处理searchheader
            if ( StringUtils.isNotBlank( listHeaderVOTemp.getSearchId() ) )
            {
               // 初始化SearchDTO
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
                        // 装载SearchDetailVO到SearchDTO对象
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
      // 初始化DTO列表对象
      final List< TableDTO > tableDTOs = new ArrayList< TableDTO >();

      // 获取所有有效的Table
      final List< Object > tableVOs = ( ( TableDao ) getDao() ).getTableVOsByCondition( null );

      // 获取Account下有效ManagerDTO
      final List< ManagerDTO > managerDTOs = this.getManagerHeaderService().getManagerDTOsByAccountId( accountId );

      //查询出需哦有column 分组
      List< Object > columnGroupVOList = columnGroupDao.getColumnGroupVOsByAccountId( accountId );

      //table-columnList   表的列
      Map< String, List< ColumnVO >> tableColumnVOMap = this.getTableColumnMapByAccountId( accountId );
      //table-listHeaderList  -begin 表的头
      Map< String, List< ListDTO >> tableListHeaderVOMap = this.getTableListHeaderMapByAccountId( accountId );
      //table-listHeaderList  -end
      // 遍历TableVO列表
      if ( tableVOs != null && tableVOs.size() > 0 )
      {
         for ( Object tableVOObject : tableVOs )
         {
            // 初始化Table对象
            final TableVO tableVO = ( TableVO ) tableVOObject;

            // 获取ManagerDTO
            final ManagerDTO managerDTO = getManagerDTOByTableId( managerDTOs, tableVO.getTableId() );

            // 标识是否重新排序
            boolean isSort = false;

            // 如果存在ManagerDTO
            if ( managerDTO != null && managerDTO.getManagerHeaderVO() != null )
            {
               //tableVO.setNameZH( managerDTO.getManagerHeaderVO().getNameZH() );
               // tableVO.setNameEN( managerDTO.getManagerHeaderVO().getNameEN() );
               tableVO.setComments( managerDTO.getManagerHeaderVO().getComments() );
               isSort = true;
            }

            // 初始化TableDTO
            final TableDTO tableDTO = new TableDTO();
            tableDTO.setTableVO( tableVO );

            /** 装载ColumnGroup（含Column）（任意个）至Table - Start */
            // 初始化ColumnGroup，Column对象
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
                     // 视图需装载原来table自定义字段
                     columnVOs.addAll( tableColumnVOMap.get( linkedTableId ) );
                  }
               }
            }

            // 遍历ColumnVO列表
            if ( columnVOListTemp != null && columnVOListTemp.size() > 0 )
            {
               for ( ColumnVO tempColumnVO : columnVOListTemp )
               {

                  // 如果存在页面字段
                  if ( managerDTO != null && managerDTO.getManagerDetailVOs() != null && managerDTO.getManagerDetailVOs().size() > 0 )
                  {
                     for ( ManagerDetailVO managerDetailVO : managerDTO.getManagerDetailVOs() )
                     {
                        // 当前字段存在于自定义页面
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

                  // 如果当前ColumnVO未分组，则插入默认ColumnGroup；如果不设字段组，运行Else部分
                  if ( KANUtil.filterEmpty( tempColumnVO.getGroupId() ) == null )
                  {
                     tableDTO.getDefaultColumnGroupDTO().getColumnVOs().add( tempColumnVO );
                  }
                  // 如果当前字段有分组，ColumnVO插入到相应的ColumnGroup中
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

            // 重新排序
            if ( isSort )
            {
               columnSort( tableDTO );
            }
            /** 装载ColumnGroup（含Column）至Table - End */

            /** 装载List（单个）至Table - Start */
            // 获取用户定义的List
            final List< ListDTO > listDTOs = tableListHeaderVOMap.get( tableId );
            tableDTO.setListDTOs( listDTOs );
            //            // 遍历ListHeaderVO列表
            //            if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
            //            {
            //               for ( ListHeaderVO listHeaderTemp : listHeaderVOs )
            //               {
            //                  // 初始化ListDTO
            //                  final ListDTO listDTO = new ListDTO();
            //                  // 装载ListHeaderVO到ListDTO对象
            //                  listDTO.setListHeaderVO( listHeaderTemp );
            //
            //                  // 获取当前ListHeader对应的ListDetail
            //                  final List< Object > listDetailVOs = this.getListDetailDao().getListDetailVOsByListHeaderId( listHeaderTemp.getListHeaderId() );
            //                  // 遍历ListDetailVO列表
            //                  if ( listDetailVOs != null && listDetailVOs.size() > 0 )
            //                  {
            //                     for ( Object listDetailVOObject : listDetailVOs )
            //                     {
            //                        // 装载ListDetailVO到ListDTO对象
            //                        listDTO.getListDetailVOs().add( ( ListDetailVO ) listDetailVOObject );
            //                     }
            //                  }
            //
            //                  /** 装载Search至List - Start */
            //                  // 初始化SearchDTO
            //                  final SearchDTO searchDTO = new SearchDTO();
            //
            //                  // 装载SearchHeaderVO至SearchDTO
            //                  final SearchHeaderVO searchHeaderVO = this.getSearchHeaderDao().getSearchHeaderVOBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            //                  searchDTO.setSearchHeaderVO( searchHeaderVO );
            //
            //                  // 装载SearchDetailVO至SearchDTO
            //                  final List< Object > searchDetailVOs = this.getSearchDetailDao().getSearchDetailVOsBySearchHeaderId( ( ( ListHeaderVO ) listHeaderVOObject ).getSearchId() );
            //                  // 遍历SearchDetailVO列表
            //                  if ( searchDetailVOs != null && searchDetailVOs.size() > 0 )
            //                  {
            //                     for ( Object searchDetailVOObject : searchDetailVOs )
            //                     {
            //                        // 装载SearchDetailVO到SearchDTO对象
            //                        searchDTO.getSearchDetailVOs().add( ( SearchDetailVO ) searchDetailVOObject );
            //                     }
            //                  }
            //                  listDTO.setSearchDTO( searchDTO );
            //                  /** 装载Search至List - End */
            //
            //                  tableDTO.getListDTOs().add( listDTO );
            //               }
            //            }
            /** 装载List至Table - End */

            /** 装载Report（任意个）至Table - Start */
            // 初始化Report，准备搜索条件
            final List< Object > reportHeaderVOs = new ArrayList< Object >();
            final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
            reportHeaderVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
            reportHeaderVO.setTableId( tableVO.getTableId() );
            // 装载系统定义的报表
            reportHeaderVOs.addAll( this.reportHeaderDao.getReportHeaderVOsByCondition( reportHeaderVO ) );
            // 装载Account定义的报表
            reportHeaderVO.setAccountId( accountId );
            reportHeaderVOs.addAll( this.reportHeaderDao.getAccountReportHeaderVOsByCondition( reportHeaderVO ) );

            // 遍历ReportVO列表
            if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
            {
               final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();
               for ( Object reportHeaderVOObject : reportHeaderVOs )
               {
                  // 初始化ReportDTO
                  final ReportDTO reportDTO = new ReportDTO();
                  // 装载ReportHeader对象至ReportDTO
                  reportDTO.setReportHeaderVO( ( ReportHeaderVO ) reportHeaderVOObject );

                  // 初始化ReportDetailVO，准备搜索条件
                  final ReportDetailVO reportDetailVO = new ReportDetailVO();
                  reportDetailVO.setAccountId( accountId );
                  reportDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
                  reportDetailVO.setStatus( ReportDetailVO.TRUE );
                  reportDetailVO.setSortColumn( "columnIndex" );

                  // 装载ReportDetailVO列表至ReportDTO
                  final List< Object > reportDetailVOs = this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );

                  // 遍历ReportDetailVO列表
                  if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
                  {
                     for ( Object reportDetailVOObject : reportDetailVOs )
                     {
                        // 装载ReportDetailVO到ReportDTO对象
                        reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
                     }
                  }

                  // 初始化ReportSearchDetailVO，准备搜索条件
                  final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
                  reportSearchDetailVO.setReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() );
                  reportSearchDetailVO.setStatus( BaseVO.TRUE );

                  // 装载ReportSearchDetailVO至ReportDTO
                  final List< Object > reportSearchDetailVOs = this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO );

                  // 遍历reportSearchDetailVO列表
                  if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
                  {
                     for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
                     {
                        // 装载ReportSearchDetailVO到ReportDTO对象
                        reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
                     }
                  }

                  // 添加ReportDTO至列表
                  reportDTOs.add( reportDTO );
               }
               // 装载ReportDTO列表至TableDTO
               tableDTO.setReportDTOs( reportDTOs );
            }
            /** 装载Report至Table - End */

            // 保存TableDTO至列表
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

   // 获取ManagerDTO
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

   // 按照TableId获得对应的TableDTO
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

   // 内部类 - 排序columnIndex
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
