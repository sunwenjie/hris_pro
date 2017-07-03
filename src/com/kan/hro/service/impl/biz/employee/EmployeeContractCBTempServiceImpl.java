package com.kan.hro.service.impl.biz.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBTempService;

public class EmployeeContractCBTempServiceImpl extends ContextService implements EmployeeContractCBTempService
{
   // 注入EmployeeContractDao
   private EmployeeContractTempDao employeeContractTempDao;

   public EmployeeContractTempDao getEmployeeContractTempDao()
   {
      return employeeContractTempDao;
   }

   public void setEmployeeContractTempDao( EmployeeContractTempDao employeeContractTempDao )
   {
      this.employeeContractTempDao = employeeContractTempDao;
   }

   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractCBVO";

   public final String SERVICE_BEAN = "employeeContractCBService";

   @Override
   public PagedListHolder getEmployeeContractCBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractCBTempDao employeeContractCBTempDao = ( EmployeeContractCBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractCBTempDao.countEmployeeContractCBTempVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractCBTempDao.getEmployeeContractCBTempVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractCBTempDao.getEmployeeContractCBTempVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public PagedListHolder getFullEmployeeContractCBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractCBTempDao employeeContractCBTempDao = ( EmployeeContractCBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractCBTempDao.countFullEmployeeContractCBTempVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractCBTempDao.getFullEmployeeContractCBTempVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractCBTempDao.getFullEmployeeContractCBTempVOsByCondition( ( EmployeeContractCBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractCBVO getFullEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( ( EmployeeContractCBTempDao ) getDao() ).getFullEmployeeContractCBTempVOByEmployeeCBId( employeeCBId );
   }

   @Override
   public EmployeeContractCBVO getEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( ( EmployeeContractCBTempDao ) getDao() ).getEmployeeContractCBTempVOByEmployeeCBId( employeeCBId );
   }

   @Override
   public int insertEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( ( EmployeeContractCBTempDao ) getDao() ).insertEmployeeContractCBTemp( employeeContractCBVO );

   }

   @Override
   public int updateEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( ( EmployeeContractCBTempDao ) getDao() ).updateEmployeeContractCBTemp( employeeContractCBVO );
   }

   @Override
   public int deleteEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      employeeContractCBVO.setDeleted( EmployeeContractCBVO.FALSE );
      return updateEmployeeContractCBTemp( employeeContractCBVO );
   }

   @Override
   public List< Object > getEmployeeContractCBTempVOsByContractId( final String contractId ) throws KANException
   {
      if ( contractId == null || contractId.isEmpty() )
      {
         return null;
      }
      else
      {
         return ( ( EmployeeContractCBTempDao ) getDao() ).getEmployeeContractCBTempVOsByContractId( contractId );
      }
   }

   /**  
    * modifyEmployeeContractCBVO
    *	 模态框 修改 合同商保方案(单条记录)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBTempVO( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // 获取需要修改的派送协议Contract ID及所要修改的商保方案ID
      final String contractId = employeeContractCBVO.getContractId();
      final String solutionId = employeeContractCBVO.getSolutionId();

      // 初始化查询对象
      final EmployeeContractCBVO employeeContractCBVOTemp = new EmployeeContractCBVO();
      employeeContractCBVOTemp.setContractId( contractId );
      employeeContractCBVOTemp.setSolutionId( solutionId );
      employeeContractCBVOTemp.setAccountId( employeeContractCBVO.getAccountId() );

      //  如果不存在该商保方案类型则添加
      if ( ( ( EmployeeContractCBTempDao ) getDao() ).countFullEmployeeContractCBTempVOsByCondition( employeeContractCBVOTemp ) == 0 )
      {
         // 更改操作位添加
         actFlag = "addObject";
         employeeContractCBVO.setCreateBy( employeeContractCBVO.getModifyBy() );
         // 清空“退保日期”
         employeeContractCBVO.setEndDate( null );

         // 获取SubAction判断是否需要提交
         final String subAction = employeeContractCBVO.getSubAction();
         employeeContractCBVO.setStatus( "0" );

         // 如果为“提交”则改状态为“待申购提交”
         if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
         {
            actFlag = "submitObject";
            employeeContractCBVO.setStatus( "2" );
         }

         // 更新日期
         updateDateByCondition( employeeContractCBVO );
         // 新增
         ( ( EmployeeContractCBTempDao ) getDao() ).insertEmployeeContractCBTemp( employeeContractCBVO );
      }
      // 如果存在则修改
      else
      {
         // 更改操作位添加
         actFlag = "updateObject";

         final List< Object > employeeContractCBVOs = ( ( EmployeeContractCBTempDao ) getDao() ).getFullEmployeeContractCBTempVOsByCondition( employeeContractCBVOTemp );

         // 遍历
         for ( Object object : employeeContractCBVOs )
         {
            final EmployeeContractCBVO employeeContractCBVOObject = ( EmployeeContractCBVO ) object;
            employeeContractCBVOObject.setModifyBy( employeeContractCBVO.getModifyBy() );

            // 获得EmployeeContractCBVO 未修改前的状态
            final String status = employeeContractCBVOObject.getStatus();

            // 根据用户选择判断是否需要更新方案数据
            if ( employeeContractCBVO.getApplyToAllHeader() != null && employeeContractCBVO.getApplyToAllHeader().equals( "1" ) )
            {
               employeeContractCBVOObject.update( employeeContractCBVO );
               // 更新日期
               updateDateByCondition( employeeContractCBVOObject );
               // 状态还原
               employeeContractCBVOObject.setStatus( status );
            }

            // 获取SubAction判断是否需要提交
            final String subAction = employeeContractCBVO.getSubAction();

            if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
            {

               // 如果是无社保则“待申购提交”
               if ( status.equals( "0" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "2" );
               }
               // 如果是“待退购”或“正常购买”则“待退购提交”
               else if ( status.equals( "2" ) || status.equals( "3" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "5" );
               }

            }

            // 更新
            ( ( EmployeeContractCBTempDao ) getDao() ).updateEmployeeContractCBTemp( employeeContractCBVOObject );

         }
      }

      return actFlag;
   }

   /**  
    * ModifyEmployeeContractCBVOs
    *	 模态框 修改 合同商保方案(多条记录)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBTempVOs( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractCBVO.getSelectedIds();
      // 遍历
      for ( String selectedId : selectIds.split( "," ) )
      {

         if ( selectedId != null && !selectedId.equals( "null" ) && !selectedId.isEmpty() )
         {
            employeeContractCBVO.setContractId( selectedId );

            modifyEmployeeContractCBTempVO( employeeContractCBVO );
         }

      }
      return null;
   }

   /**  
    * UpdateDateByCondition
    * 根据派送协议开始日期和结束日期设置商保日期
    * @param employeeContractCBVO
    * @throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.employeeContractTempDao.getEmployeeContractTempVOByContractId( employeeContractCBVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // 日期格式
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // 判断商保方案开始时间是否在派送信息之前
         if ( employeeContractCBVO.getStartDate() != null && !employeeContractCBVO.getStartDate().isEmpty() )
         {

            final Date cbStartDate = df.parse( employeeContractCBVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( cbStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // 判断商保方案结束时间是否在派送信息之后
         if ( employeeContractCBVO.getEndDate() != null && !employeeContractCBVO.getEndDate().isEmpty() )
         {
            final Date cbEndDate = df.parse( employeeContractCBVO.getEndDate() );
            final Date contractEndDate = df.parse( employeeContractVO.getEndDate() );

            if ( cbEndDate.getTime() > contractEndDate.getTime() )
            {
               employeeContractCBVO.setEndDate( employeeContractVO.getEndDate() );
            }

         }

      }

   }
}
