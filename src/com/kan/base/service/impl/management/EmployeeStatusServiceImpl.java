package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.EmployeeStatusDao;
import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.EmployeeStatusService;
import com.kan.base.util.KANException;

public class EmployeeStatusServiceImpl extends ContextService implements EmployeeStatusService
{

   @Override
   public PagedListHolder getEmployeeStatusVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeStatusDao employeeStatusDao = ( EmployeeStatusDao ) getDao();
      pagedListHolder.setHolderSize( employeeStatusDao.countEmployeeStatusVOsByCondition( ( EmployeeStatusVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeStatusDao.getEmployeeStatusVOsByCondition( ( EmployeeStatusVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeStatusDao.getEmployeeStatusVOsByCondition( ( EmployeeStatusVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public EmployeeStatusVO getEmployeeStatusVOByEmployeeStatusId( final String employeeStatusId ) throws KANException
   {
      return ( ( EmployeeStatusDao ) getDao() ).getEmployeeStatusVOByEmployeeStatusId( employeeStatusId );
   }

   @Override
   public int insertEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      // 如果设置为默认。则删除之前的默认
      if ( "1".equals( employeeStatusVO.getSetDefault() ) )
      {
         EmployeeStatusVO employeeStatusVOCondition = new EmployeeStatusVO();
         employeeStatusVOCondition.setAccountId( employeeStatusVO.getAccountId() );
         employeeStatusVOCondition.setClientId( employeeStatusVO.getClientId() );
         employeeStatusVOCondition.setSetDefault( employeeStatusVO.getSetDefault() );
         final List< Object > employeeStatuses = ( ( EmployeeStatusDao ) getDao() ).getEmployeeStatusVOsByCondition( employeeStatusVOCondition );
         EmployeeStatusVO employeeStatusVOTemp = null;
         // 清空所有的默认
         for ( Object o : employeeStatuses )
         {
            employeeStatusVOTemp = ( EmployeeStatusVO ) o;
            employeeStatusVOTemp.setSetDefault( "2" );
            ( ( EmployeeStatusDao ) getDao() ).updateEmployeeStatus( employeeStatusVOTemp );
         }
      }
      return ( ( EmployeeStatusDao ) getDao() ).insertEmployeeStatus( employeeStatusVO );
   }

   @Override
   public int updateEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      // 如果设置为默认。则删除之前的默认
      if ( "1".equals( employeeStatusVO.getSetDefault() ) )
      {
         EmployeeStatusVO employeeStatusVOCondition = new EmployeeStatusVO();
         employeeStatusVOCondition.setAccountId( employeeStatusVO.getAccountId() );
         employeeStatusVOCondition.setClientId( employeeStatusVO.getClientId() );
         employeeStatusVOCondition.setSetDefault( employeeStatusVO.getSetDefault() );
         final List< Object > employeeStatuses = ( ( EmployeeStatusDao ) getDao() ).getEmployeeStatusVOsByCondition( employeeStatusVOCondition );
         EmployeeStatusVO employeeStatusVOTemp = null;
         // 清空所有的默认
         for ( Object o : employeeStatuses )
         {
            employeeStatusVOTemp = ( EmployeeStatusVO ) o;
            employeeStatusVOTemp.setSetDefault( "2" );
            ( ( EmployeeStatusDao ) getDao() ).updateEmployeeStatus( employeeStatusVOTemp );
         }
      }
      return ( ( EmployeeStatusDao ) getDao() ).updateEmployeeStatus( employeeStatusVO );
   }

   @Override
   public void deleteEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      // 标记删除employeeStatusVO
      final EmployeeStatusVO modifyObject = ( ( EmployeeStatusDao ) getDao() ).getEmployeeStatusVOByEmployeeStatusId( employeeStatusVO.getEmployeeStatusId() );
      modifyObject.setDeleted( EmployeeStatusVO.FALSE );
      ( ( EmployeeStatusDao ) getDao() ).updateEmployeeStatus( modifyObject );
   }

   @Override
   public List< Object > getEmployeeStatusVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( EmployeeStatusDao ) getDao() ).getEmployeeStatusVOsByAccountId( accountId );
   }

}
