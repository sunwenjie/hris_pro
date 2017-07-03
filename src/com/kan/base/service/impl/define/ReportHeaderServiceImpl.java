package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ReportDetailDao;
import com.kan.base.dao.inf.define.ReportHeaderDao;
import com.kan.base.dao.inf.define.ReportRelationDao;
import com.kan.base.dao.inf.define.ReportSearchDetailDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ReportColumnVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableColumnSubVO;
import com.kan.base.domain.define.TableRelationSubVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.page.PagedReportListHolder;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.util.KANException;

public class ReportHeaderServiceImpl extends ContextService implements ReportHeaderService
{

   // ע�뱨��ӱ�DAO
   private ReportDetailDao reportDetailDao;

   // ע�뱨�������ֶ�DAO
   private ReportSearchDetailDao reportSearchDetailDao;

   // ע�뱨���ӱ�DAO
   private ReportRelationDao reportRelationDao;

   public ReportDetailDao getReportDetailDao()
   {
      return reportDetailDao;
   }

   public void setReportDetailDao( ReportDetailDao reportDetailDao )
   {
      this.reportDetailDao = reportDetailDao;
   }

   public ReportSearchDetailDao getReportSearchDetailDao()
   {
      return reportSearchDetailDao;
   }

   public void setReportSearchDetailDao( ReportSearchDetailDao reportSearchDetailDao )
   {
      this.reportSearchDetailDao = reportSearchDetailDao;
   }

   @Override
   public PagedListHolder getReportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ReportHeaderDao reportHeaderDao = ( ReportHeaderDao ) getDao();
      pagedListHolder.setHolderSize( reportHeaderDao.countReportHeaderVOsByCondition( ( ReportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( reportHeaderDao.getReportHeaderVOsByCondition( ( ReportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( reportHeaderDao.getReportHeaderVOsByCondition( ( ReportHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ReportHeaderVO getReportHeaderVOByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      return ( ( ReportHeaderDao ) getDao() ).getReportHeaderVOByReportHeaderId( reportHeaderId );
   }

   @Override
   public int insertReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      int returnInt = 0;
      returnInt = returnInt + ( ( ReportHeaderDao ) getDao() ).insertReportHeader( reportHeaderVO );
      //��ѡ�ӱ�

      String selectTableJson = reportHeaderVO.getSelectTablesJson();
      ReportRelationVO reportRelationVO = null;
      if ( StringUtils.isNotBlank( selectTableJson ) )
      {
         JSONArray jsonArray = JSONArray.fromObject( selectTableJson );
         List< TableRelationSubVO > list = ( List ) JSONArray.toCollection( jsonArray, TableRelationSubVO.class );
         for ( TableRelationSubVO tableRelationVO : list )
         {
            if ( tableRelationVO != null )
            {
               reportRelationVO = new ReportRelationVO();
               reportRelationVO.setSlaveTableId( tableRelationVO.getSlaveTableId() );
               reportRelationVO.setReportHeaderId( reportHeaderVO.getReportHeaderId() );
               reportRelationVO.setModifyBy( reportHeaderVO.getModifyBy() );
               reportRelationVO.setCreateBy( reportHeaderVO.getCreateBy() );
               reportRelationVO.setStatus( ReportRelationVO.TRUE );
               returnInt = returnInt + reportRelationDao.insertReportRelation( reportRelationVO );
            }
         }
      }

      return returnInt;
   }

   @Override
   public int updateReportHeader( final ReportHeaderVO reportHeaderVO, final Map< String, Map< String, ColumnVO >> tableColumnVOMap ) throws KANException
   {
      try
      {
         if ( reportHeaderVO != null && StringUtils.isNotBlank( reportHeaderVO.getReportHeaderId() ) )
         {
            // ��������
            startTransaction();

            ReportRelationVO reportRelationVO = null;

            //����ҳ�洫���� ��ѡ���ӱ������
            if ( reportHeaderVO.getSelectTablesJson() != null && StringUtils.isNotBlank( reportHeaderVO.getSelectTablesJson() ) )
            {
               List< Object > reportRelationList = this.reportRelationDao.getReportRelationVOsByReportHeaderId( reportHeaderVO.getReportHeaderId() );

               JSONArray array = JSONArray.fromObject( reportHeaderVO.getSelectTablesJson() );
               TableRelationSubVO[] selectTables = ( TableRelationSubVO[] ) JSONArray.toArray( array, TableRelationSubVO.class );

               if ( selectTables != null && selectTables.length > 0 )
               {
                  // �ӱ�
                  for ( TableRelationSubVO tableRelationVO : selectTables )
                  {

                     //tableRelationIdΪ�� ����
                     if ( StringUtils.isBlank( tableRelationVO.getReportRelationId() ) )
                     {
                        reportRelationVO = new ReportRelationVO();
                        reportRelationVO.setSlaveTableId( tableRelationVO.getSlaveTableId() );
                        reportRelationVO.setReportHeaderId( reportHeaderVO.getReportHeaderId() );
                        reportRelationVO.setModifyBy( reportHeaderVO.getModifyBy() );
                        reportRelationVO.setCreateBy( reportHeaderVO.getCreateBy() );
                        reportRelationVO.setStatus( ReportRelationVO.TRUE );
                        reportRelationDao.insertReportRelation( reportRelationVO );
                     }
                     //����Ϊ�޸� ����������

                  }
               }

               //ɾ������
               int isDel = 1;
               for ( Object object : reportRelationList )
               {
                  reportRelationVO = ( ReportRelationVO ) object;
                  for ( TableRelationSubVO tableRelationVO : selectTables )
                  {
                     if ( reportRelationVO.getReportRelationId().equals( tableRelationVO.getReportRelationId() ) )
                     {
                        isDel = 0;
                        break;
                     }
                  }

                  if ( isDel == 1 )
                  {
                     reportRelationVO.setModifyBy( reportHeaderVO.getModifyBy() );
                     reportRelationVO.setModifyDate( new Date() );
                     reportRelationVO.setDeleted( BaseVO.FALSE );
                     reportRelationDao.updateReportRelation( reportRelationVO );

                     // �ȱ��ɾ��Report Detail
                     ReportDetailVO reportDetailVO = new ReportDetailVO();
                     reportDetailVO.setReportHeaderId( reportHeaderVO.getReportHeaderId() );
                     reportDetailVO.setTableId( reportRelationVO.getSlaveTableId() );
                     //				   reportDetailVO.setDeleted( BaseVO.FALSE );
                     for ( Object objectReportDetail : this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO ) )
                     {
                        ( ( ReportDetailVO ) objectReportDetail ).setDeleted( BaseVO.FALSE );
                        ( ( ReportDetailVO ) objectReportDetail ).setModifyBy( reportHeaderVO.getModifyBy() );
                        ( ( ReportDetailVO ) objectReportDetail ).setModifyDate( reportHeaderVO.getModifyDate() );
                        this.reportDetailDao.updateReportDetail( ( ( ReportDetailVO ) objectReportDetail ) );
                     }

                     final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
                     reportSearchDetailVO.setReportHeaderId( reportHeaderVO.getReportHeaderId() );
                     reportSearchDetailVO.setTableId( reportRelationVO.getSlaveTableId() );
                     ReportSearchDetailVO tempReportSearchDetailVO;
                     // �ٱ��ɾ��Report Search Detail
                     for ( Object objectReportSearchDetail : this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO ) )
                     {
                        tempReportSearchDetailVO = ( ReportSearchDetailVO ) objectReportSearchDetail;
                        tempReportSearchDetailVO.setDeleted( BaseVO.FALSE );
                        tempReportSearchDetailVO.setModifyBy( reportHeaderVO.getModifyBy() );
                        tempReportSearchDetailVO.setModifyDate( reportHeaderVO.getModifyDate() );
                        this.reportSearchDetailDao.updateReportSearchDetail( tempReportSearchDetailVO );
                     }

                     //ɾ������
                     Map tcolumnVOMap = null;
                     String tableId = reportRelationVO.getSlaveTableId();
                     JSONObject jsonObject = null;
                     if ( tableColumnVOMap != null && StringUtils.isNotBlank( reportHeaderVO.getSortColumns() ) )
                     {
                        jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );
                        //��ȡ�ӱ��������
                        tcolumnVOMap = tableColumnVOMap.get( tableId );
                        jsonObject.keySet();
                        for ( Object key : jsonObject.keySet() )
                        {
                           //�����ֶ����ӱ�� ɾ��
                           if ( tcolumnVOMap.containsKey( key ) )
                           {
                              jsonObject.remove( key );
                           }
                        }
                        reportHeaderVO.setSortColumns( JSONObject.fromObject( jsonObject ).toString() );
                        //						reportHeaderVO.setSortColumns(jsonObject.toString());
                     }

                  }
               }
            }
            //����reportHeader
            ( ( ReportHeaderDao ) getDao() ).updateReportHeader( reportHeaderVO );

            // �ύ���� 
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;

   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public int deleteReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      try
      {
         // ɾ��ReportHeaderVOͬʱ����Ҫɾ����֮������ReportDetailVO
         if ( reportHeaderVO != null && reportHeaderVO.getReportHeaderId() != null && !reportHeaderVO.getReportHeaderId().trim().equals( "" ) )
         {
            // ��������
            startTransaction();

            final ReportDetailVO reportDetailVO = new ReportDetailVO();
            reportDetailVO.setReportHeaderId( reportHeaderVO.getReportHeaderId() );

            // �ȱ��ɾ��Report Detail
            for ( Object objectReportDetail : this.reportDetailDao.getReportDetailVOsByCondition( reportDetailVO ) )
            {
               ( ( ReportDetailVO ) objectReportDetail ).setDeleted( BaseVO.FALSE );
               ( ( ReportDetailVO ) objectReportDetail ).setModifyBy( reportHeaderVO.getModifyBy() );
               ( ( ReportDetailVO ) objectReportDetail ).setModifyDate( reportHeaderVO.getModifyDate() );
               this.reportDetailDao.updateReportDetail( ( ( ReportDetailVO ) objectReportDetail ) );
            }

            final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
            reportSearchDetailVO.setReportHeaderId( reportHeaderVO.getReportHeaderId() );

            // �ٱ��ɾ��Report Search Detail
            for ( Object objectReportSearchDetail : this.reportSearchDetailDao.getReportSearchDetailVOsByCondition( reportSearchDetailVO ) )
            {
               ( ( ReportSearchDetailVO ) objectReportSearchDetail ).setDeleted( BaseVO.FALSE );
               ( ( ReportSearchDetailVO ) objectReportSearchDetail ).setModifyBy( reportHeaderVO.getModifyBy() );
               ( ( ReportSearchDetailVO ) objectReportSearchDetail ).setModifyDate( reportHeaderVO.getModifyDate() );
               this.reportSearchDetailDao.updateReportSearchDetail( reportSearchDetailVO );
            }

            // �����ɾ��Report Header
            reportHeaderVO.setDeleted( BaseVO.FALSE );
            ( ( ReportHeaderDao ) getDao() ).updateReportHeader( reportHeaderVO );

            // �ύ���� 
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< ReportDTO > getReportDTOsByAccountId( final String accountId ) throws KANException
   {
      try
      {
         // ��ʼ��ReportDTO�б�
         final List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();

         // ��ʼ����������
         final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();
         reportHeaderVO.setAccountId( accountId );
         // ��������
         reportHeaderVO.setSortColumn( "clicks" );
         reportHeaderVO.setSortOrder( "desc" );
         // ״̬Ϊ�ѷ���
         reportHeaderVO.setStatus( "2" );

         // �����Ч��ReportHeaderVO�б�
         final List< Object > reportHeaderVOs = ( ( ReportHeaderDao ) getDao() ).getReportHeaderVOsByCondition( reportHeaderVO );

         // ����reportHeaderVOs
         if ( reportHeaderVOs != null && reportHeaderVOs.size() > 0 )
         {
            for ( Object reportHeaderVOObject : reportHeaderVOs )
            {
               reportDTOs.add( getReportDTOByReportHeaderId( ( ( ReportHeaderVO ) reportHeaderVOObject ).getReportHeaderId() ) );
            }
         }

         return reportDTOs;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public PagedReportListHolder executeReportHeader( final String sql, final PagedReportListHolder pagedListHolder, final boolean isPaged )
         throws KANException
   {
      final RowBounds rowBounds = new RowBounds( pagedListHolder.getPage(), pagedListHolder.getPageSize() );
      List< Map< String, Object > > resultObj = null;
      if ( isPaged )
      {
         resultObj = ( ( ReportHeaderDao ) getDao() ).executeReportHeader( sql, rowBounds, pagedListHolder.getObject() );
      }
      else
      {
         resultObj = ( ( ReportHeaderDao ) getDao() ).executeReportHeader( sql, null, pagedListHolder.getObject() );
      }
      pagedListHolder.setHolderSize( ( ( ReportHeaderDao ) getDao() ).countReportHeader( sql ) );

      pagedListHolder.setSourceMap( resultObj );
      return pagedListHolder;
   }

   @Override
   public ReportDTO getReportDTOByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      // ��ʼ��ReportDTO
      final ReportDTO reportDTO = new ReportDTO();

      // ��ȡReportHeaderVO
      final ReportHeaderVO reportHeaderVO = this.getReportHeaderVOByReportHeaderId( reportHeaderId );
      reportDTO.setReportHeaderVO( reportHeaderVO );

      // ��ȡReportDetailVO�б�
      final List< Object > reportDetailVOs = this.getReportDetailDao().getReportDetailVOsByReportHeaderId( reportHeaderId );

      // ����ReportDetailVO�б�
      if ( reportDetailVOs != null && reportDetailVOs.size() > 0 )
      {
         for ( Object reportDetailVOObject : reportDetailVOs )
         {
            reportDTO.getReportDetailVOs().add( ( ReportDetailVO ) reportDetailVOObject );
         }
      }

      // ��ȡReportSearchDetailVO�б�
      final List< Object > reportSearchDetailVOs = this.getReportSearchDetailDao().getReportSearchDetailVOsByReportHeaderId( reportHeaderId );

      // ����ReportSearchDetailVO�б�
      if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
      {
         for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
         {
            reportDTO.getReportSearchDetailVOs().add( ( ReportSearchDetailVO ) reportSearchDetailVOObject );
         }
      }

      return reportDTO;
   }

   public ReportRelationDao getReportRelationDao()
   {
      return reportRelationDao;
   }

   public void setReportRelationDao( ReportRelationDao reportRelationDao )
   {
      this.reportRelationDao = reportRelationDao;
   }

   @Override
   public List< Object > getReportRelationVOsByReportHeaderId( String reportHeaderId ) throws KANException
   {
      // TODO Auto-generated method stub

      return reportRelationDao.getReportRelationVOsByReportHeaderId( reportHeaderId );
      //return null;
   }

   @Override
   public int updateReportColumn( ReportHeaderVO reportHeaderVO ) throws KANException
   {
      // TODO Auto-generated method stub
      try
      {
         // ��������
         startTransaction();
         int returnInt = 0;
         //returnInt = returnInt+( ( ReportHeaderDao ) getDao() ).insertReportHeader( reportHeaderVO );
         //��ѡ�ӱ�

         String selectColumnJson = reportHeaderVO.getSelectColumnsJson();
         //		   ReportRCelationVO reportRelationVO = null;
         ReportDetailVO reportDetailVO = null;
         String operationStatus;
         List< ReportColumnVO > reportColumnVOList = null;
         TableColumnSubVO tableColumnSubVO = null;
         String reportHeaderId = reportHeaderVO.getReportHeaderId();

         //		   MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
         //	        Morpher dynaMorpher = new BeanMorpher( ReportColumnVO.class,  morpherRegistry);  
         //	        morpherRegistry.registerMorpher( dynaMorpher ); 

         if ( StringUtils.isNotBlank( selectColumnJson ) )
         {
            JSONArray jsonArray = JSONArray.fromObject( selectColumnJson );

            Map classMap = new HashMap();
            classMap.put( "reportColumnVOList", ReportColumnVO.class );

            for ( Object obj : jsonArray )
            {

               tableColumnSubVO = ( TableColumnSubVO ) JSONObject.toBean( JSONObject.fromObject( obj ), TableColumnSubVO.class, classMap );
               if ( tableColumnSubVO.getReportColumnVOList() != null && tableColumnSubVO.getReportColumnVOList().size() > 0 )
               {
                  reportColumnVOList = tableColumnSubVO.getReportColumnVOList();
                  for ( ReportColumnVO reportColumnVO : reportColumnVOList )
                  {

                     if ( reportColumnVO != null )
                     {
                        operationStatus = reportColumnVO.getOperStatus();
                        //ɾ��
                        if ( "delete".equals( operationStatus ) && StringUtils.isNotBlank( reportColumnVO.getReportDetailId() ) )
                        {
                           reportDetailVO = new ReportDetailVO();
                           reportDetailVO.setValue( reportColumnVO );
                           reportDetailVO.setDeleted( BaseVO.FALSE );
                           reportDetailVO.setModifyBy( reportHeaderVO.getModifyBy() );
                           reportDetailVO.setModifyDate( new Date() );
                           reportDetailDao.updateReportDetail( reportDetailVO );
                           //����
                        }
                        else if ( "insert".equals( operationStatus ) )
                        {

                           reportDetailVO = new ReportDetailVO();
                           reportDetailVO.setValue( reportColumnVO );
                           reportDetailVO.setCreateBy( reportHeaderVO.getCreateBy() );
                           reportDetailVO.setCreateDate( new Date() );
                           reportDetailVO.setReportHeaderId( reportHeaderId );
                           reportDetailDao.insertReportDetail( reportDetailVO );
                           //�޸�
                        }
                        else if ( "modify".equals( operationStatus ) )
                        {
                           reportDetailVO = new ReportDetailVO();
                           reportDetailVO.setValue( reportColumnVO );
                           reportDetailVO.setModifyBy( reportHeaderVO.getModifyBy() );
                           reportDetailVO.setModifyDate( new Date() );
                           reportDetailDao.updateReportDetail( reportDetailVO );

                        }
                        //reportColumnVO.setInitStatus(reportColumnVO.getOperStatus());

                     }
                  }
               }
            }
         }
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< Object > getReportColumnVOsByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      // ׼����������
      final ReportDetailVO reportDetailVO = new ReportDetailVO();
      reportDetailVO.setReportHeaderId( reportHeaderId );
      //reportDetailVO.setStatus( ReportDetailVO.TRUE );
      reportDetailVO.setDeleted( ReportDetailVO.FALSE );
      reportDetailVO.setSortColumn( "columnIndex" );
      return reportDetailDao.getReportDetailVOsByCondition( reportDetailVO );
   }

   @Override
   public List< Object > getReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      try
      {

         return ( ( ReportHeaderDao ) getDao() ).getReportHeaderVOsByCondition( reportHeaderVO );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }
}
