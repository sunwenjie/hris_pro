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
   // ע��EmployeeContractDao
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
    *	 ģ̬�� �޸� ��ͬ�̱�����(������¼)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBTempVO( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // ��ȡ��Ҫ�޸ĵ�����Э��Contract ID����Ҫ�޸ĵ��̱�����ID
      final String contractId = employeeContractCBVO.getContractId();
      final String solutionId = employeeContractCBVO.getSolutionId();

      // ��ʼ����ѯ����
      final EmployeeContractCBVO employeeContractCBVOTemp = new EmployeeContractCBVO();
      employeeContractCBVOTemp.setContractId( contractId );
      employeeContractCBVOTemp.setSolutionId( solutionId );
      employeeContractCBVOTemp.setAccountId( employeeContractCBVO.getAccountId() );

      //  ��������ڸ��̱��������������
      if ( ( ( EmployeeContractCBTempDao ) getDao() ).countFullEmployeeContractCBTempVOsByCondition( employeeContractCBVOTemp ) == 0 )
      {
         // ���Ĳ���λ���
         actFlag = "addObject";
         employeeContractCBVO.setCreateBy( employeeContractCBVO.getModifyBy() );
         // ��ա��˱����ڡ�
         employeeContractCBVO.setEndDate( null );

         // ��ȡSubAction�ж��Ƿ���Ҫ�ύ
         final String subAction = employeeContractCBVO.getSubAction();
         employeeContractCBVO.setStatus( "0" );

         // ���Ϊ���ύ�����״̬Ϊ�����깺�ύ��
         if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
         {
            actFlag = "submitObject";
            employeeContractCBVO.setStatus( "2" );
         }

         // ��������
         updateDateByCondition( employeeContractCBVO );
         // ����
         ( ( EmployeeContractCBTempDao ) getDao() ).insertEmployeeContractCBTemp( employeeContractCBVO );
      }
      // ����������޸�
      else
      {
         // ���Ĳ���λ���
         actFlag = "updateObject";

         final List< Object > employeeContractCBVOs = ( ( EmployeeContractCBTempDao ) getDao() ).getFullEmployeeContractCBTempVOsByCondition( employeeContractCBVOTemp );

         // ����
         for ( Object object : employeeContractCBVOs )
         {
            final EmployeeContractCBVO employeeContractCBVOObject = ( EmployeeContractCBVO ) object;
            employeeContractCBVOObject.setModifyBy( employeeContractCBVO.getModifyBy() );

            // ���EmployeeContractCBVO δ�޸�ǰ��״̬
            final String status = employeeContractCBVOObject.getStatus();

            // �����û�ѡ���ж��Ƿ���Ҫ���·�������
            if ( employeeContractCBVO.getApplyToAllHeader() != null && employeeContractCBVO.getApplyToAllHeader().equals( "1" ) )
            {
               employeeContractCBVOObject.update( employeeContractCBVO );
               // ��������
               updateDateByCondition( employeeContractCBVOObject );
               // ״̬��ԭ
               employeeContractCBVOObject.setStatus( status );
            }

            // ��ȡSubAction�ж��Ƿ���Ҫ�ύ
            final String subAction = employeeContractCBVO.getSubAction();

            if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
            {

               // ��������籣�򡰴��깺�ύ��
               if ( status.equals( "0" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "2" );
               }
               // ����ǡ����˹��������������򡰴��˹��ύ��
               else if ( status.equals( "2" ) || status.equals( "3" ) )
               {
                  actFlag = "submitObject";
                  employeeContractCBVOObject.setStatus( "5" );
               }

            }

            // ����
            ( ( EmployeeContractCBTempDao ) getDao() ).updateEmployeeContractCBTemp( employeeContractCBVOObject );

         }
      }

      return actFlag;
   }

   /**  
    * ModifyEmployeeContractCBVOs
    *	 ģ̬�� �޸� ��ͬ�̱�����(������¼)
    *	@param employeeContractCBVO
    *	@return
    *	@throws KANException
    *	@throws ParseException
    */
   @Override
   public String modifyEmployeeContractCBTempVOs( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractCBVO.getSelectedIds();
      // ����
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
    * ��������Э�鿪ʼ���ںͽ������������̱�����
    * @param employeeContractCBVO
    * @throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.employeeContractTempDao.getEmployeeContractTempVOByContractId( employeeContractCBVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // ���ڸ�ʽ
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // �ж��̱�������ʼʱ���Ƿ���������Ϣ֮ǰ
         if ( employeeContractCBVO.getStartDate() != null && !employeeContractCBVO.getStartDate().isEmpty() )
         {

            final Date cbStartDate = df.parse( employeeContractCBVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( cbStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // �ж��̱���������ʱ���Ƿ���������Ϣ֮��
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
