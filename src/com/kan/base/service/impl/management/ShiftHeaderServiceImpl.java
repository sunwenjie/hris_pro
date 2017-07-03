package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ShiftDetailDao;
import com.kan.base.dao.inf.management.ShiftExceptionDao;
import com.kan.base.dao.inf.management.ShiftHeaderDao;
import com.kan.base.domain.management.ShiftDTO;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.actions.management.ShiftHeaderAction;

public class ShiftHeaderServiceImpl extends ContextService implements ShiftHeaderService
{

   private ShiftDetailDao shiftDetailDao;

   private ShiftExceptionDao shiftExceptionDao;

   public ShiftDetailDao getShiftDetailDao()
   {
      return shiftDetailDao;
   }

   public void setShiftDetailDao( ShiftDetailDao shiftDetailDao )
   {
      this.shiftDetailDao = shiftDetailDao;
   }

   public ShiftExceptionDao getShiftExceptionDao()
   {
      return shiftExceptionDao;
   }

   public void setShiftExceptionDao( ShiftExceptionDao shiftExceptionDao )
   {
      this.shiftExceptionDao = shiftExceptionDao;
   }

   @Override
   public PagedListHolder getShiftHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ShiftHeaderDao shiftHeaderDao = ( ShiftHeaderDao ) getDao();
      pagedListHolder.setHolderSize( shiftHeaderDao.countShiftHeaderVOsByCondition( ( ShiftHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( shiftHeaderDao.getShiftHeaderVOsByCondition( ( ShiftHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( shiftHeaderDao.getShiftHeaderVOsByCondition( ( ShiftHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ShiftHeaderVO getShiftHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( ShiftHeaderDao ) getDao() ).getShiftHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      try
      {
         this.startTransaction();

         // 先插入排班主表
         ( ( ShiftHeaderDao ) getDao() ).insertShiftHeader( shiftHeaderVO );

         // 如果排班类型非自定义
         if ( !shiftHeaderVO.getShiftType().equals( "3" ) )
         {
            // 批量插入排班从表
            batchInsertShiftDetail( shiftHeaderVO );
         }

         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int updateShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 先修改主表
         ( ( ShiftHeaderDao ) getDao() ).updateShiftHeader( shiftHeaderVO );

         // 如果非自定义排班类型
         if ( !shiftHeaderVO.getShiftType().equals( "3" ) )
         {
            // 初始化查找参数
            final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
            shiftDetailVO.setHeaderId( shiftHeaderVO.getHeaderId() );

            // 获取ShiftDetailVO列表
            final List< Object > shiftDetailVOs = this.shiftDetailDao.getShiftDetailVOsByCondition( shiftDetailVO );

            // 逐个物理删除
            if ( shiftDetailVOs != null && shiftDetailVOs.size() > 0 )
            {
               for ( Object shiftDetailVOObject : shiftDetailVOs )
               {
                  this.shiftDetailDao.deleteShiftDetail( ( ( ShiftDetailVO ) shiftDetailVOObject ) );
               }
            }

            // 批量插入排班从表
            batchInsertShiftDetail( shiftHeaderVO );
         }

         // 提交事务
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int deleteShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      shiftHeaderVO.setDeleted( ShiftHeaderVO.FALSE );
      return ( ( ShiftHeaderDao ) getDao() ).updateShiftHeader( shiftHeaderVO );
   }

   @Override
   public List< Object > getAvailableShiftHeaderVOs( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      shiftHeaderVO.setStatus( ShiftHeaderVO.TRUE );
      return ( ( ShiftHeaderDao ) getDao() ).getShiftHeaderVOsByCondition( shiftHeaderVO );
   }

   @Override
   public List< ShiftDTO > getShiftDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化DTO列表对象
      final List< ShiftDTO > shiftDTOs = new ArrayList< ShiftDTO >();

      // 获得有效的ShiftHeaderVO列表
      final List< Object > shiftHeaderVOs = ( ( ShiftHeaderDao ) getDao() ).getShiftHeaderVOsByAccountId( accountId );

      // 遍历ShiftHeaderVO列表
      if ( shiftHeaderVOs != null && shiftHeaderVOs.size() > 0 )
      {
         for ( Object shiftVOObject : shiftHeaderVOs )
         {
            // 初始化ShiftDTO对象
            final ShiftDTO shiftDTO = new ShiftDTO();
            shiftDTO.setShiftHeaderVO( ( ShiftHeaderVO ) shiftVOObject );

            // 获取有效的ShiftDetailVO列表
            final List< Object > shiftDetailVOs = this.shiftDetailDao.getShiftDetailVOsByHeaderId( ( ( ShiftHeaderVO ) shiftVOObject ).getHeaderId() );
            // 遍历ShiftDetailVO列表
            if ( shiftDetailVOs != null && shiftDetailVOs.size() > 0 )
            {
               for ( Object shiftDetailVOObject : shiftDetailVOs )
               {
                  shiftDTO.getShiftDetailVOs().add( ( ShiftDetailVO ) shiftDetailVOObject );
               }
            }

            // 获取有效的ShiftExceptionVO列表
            final List< Object > shiftExceptionVOs = this.getShiftExceptionDao().getShiftExceptionVOsByHeaderId( ( ( ShiftHeaderVO ) shiftVOObject ).getHeaderId() );
            // 遍历ShiftExceptionVO列表
            if ( shiftExceptionVOs != null && shiftExceptionVOs.size() > 0 )
            {
               for ( Object shiftExceptionVOObject : shiftExceptionVOs )
               {
                  shiftDTO.getShiftExceptionVOs().add( ( ShiftExceptionVO ) shiftExceptionVOObject );
               }
            }

            shiftDTOs.add( shiftDTO );
         }
      }

      return shiftDTOs;
   }

   /* 批量添加排班从表 */
   private void batchInsertShiftDetail( final ShiftHeaderVO shiftHeaderVO ) throws KANException
   {
      try
      {
         // 存在排班时间
         if ( shiftHeaderVO.getPeriodArray() != null )
         {
            for ( int i = 1; i <= shiftHeaderVO.getPeriodArray().length; i++ )
            {
               final String[] tempPeriodArray = shiftHeaderVO.getPeriodArray()[ i - 1 ];
               // 初始化ShiftDetailVO
               final ShiftDetailVO tempShiftDetailVO = new ShiftDetailVO();

               // 初始化排班详情中、英文名
               String nameZH = "";
               String nameEN = "";
               // 如果排班按周
               if ( KANUtil.filterEmpty( shiftHeaderVO.getShiftType() ) != null && shiftHeaderVO.getShiftType().trim().equals( "1" ) )
               {
                  nameZH = ShiftHeaderAction.WEEK_ZH[ i % 7 == 0 ? 6 : ( ( i % 7 ) - 1 ) ];
                  nameEN = ShiftHeaderAction.WEEK_EN[ i % 7 == 0 ? 6 : ( ( i % 7 ) - 1 ) ];
               }
               // 如若排班是按天
               else if ( KANUtil.filterEmpty( shiftHeaderVO.getShiftType() ) != null && shiftHeaderVO.getShiftType().trim().equals( "2" ) )
               {
                  nameZH = KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i - 1 );
                  nameEN = KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i - 1 );
                  tempShiftDetailVO.setShiftDay( KANUtil.getStrDate( shiftHeaderVO.getStartDate(), i - 1 ) );
               }

               tempShiftDetailVO.setHeaderId( shiftHeaderVO.getHeaderId() );
               tempShiftDetailVO.setNameZH( nameZH );
               tempShiftDetailVO.setNameEN( nameEN );
               tempShiftDetailVO.setDayIndex( String.valueOf( i ) );
               tempShiftDetailVO.setShiftPeriod( KANUtil.toJasonArray( tempPeriodArray, "," ) );
               tempShiftDetailVO.setCreateBy( shiftHeaderVO.getCreateBy() );
               tempShiftDetailVO.setModifyBy( shiftHeaderVO.getModifyBy() );
               tempShiftDetailVO.setStatus( ShiftHeaderVO.TRUE );

               this.shiftDetailDao.insertShiftDetail( tempShiftDetailVO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
