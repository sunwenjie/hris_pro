package com.kan.base.util;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KANConstantsService extends Remote
{
   public abstract void initOptions( final String accountId ) throws KANException, RemoteException;

   public abstract void initEmailConfiguration( final String accountId ) throws KANException, RemoteException;

   public abstract void initShareFolderConfiguration( final String accountId ) throws KANException, RemoteException;

   public abstract void initTable( final String accountId ) throws KANException, RemoteException;

   public abstract void initColumnGroup( final String accountId ) throws KANException, RemoteException;

   public abstract void initColumnOption( final String accountId ) throws KANException, RemoteException;

   public abstract void initSearchHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initListHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initMappingHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initImportHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initReportHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initManagerHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initBankTemplateHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initTaxTemplateHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initModule( final String accountId ) throws KANException, RemoteException;

   public abstract void initConstant( final String accountId ) throws KANException, RemoteException;

   public abstract void initStaff( final String accountId ) throws KANException, RemoteException;

   public abstract void initStaff( final String accountId, final String staffId ) throws KANException, RemoteException;

   public abstract void initStaffForDelete( final String accountId, final String staffId ) throws KANException, RemoteException;

   public abstract void initStaffBaseView( final String accountId, final String staffId ) throws KANException, RemoteException;

   public abstract void initPosition( final String accountId ) throws KANException, RemoteException;

   /**
    * 添加职位不完全刷新用.导入完成后需要刷新所有职位内存或者隔天生效
    * 导入刷新专用.避免导入过慢.
    * 其他不要调用
    * @param accountId
    * @param position
    * @throws KANException
    * @throws RemoteException
    */
   public abstract void initPosition( final String accountId, final String position ) throws KANException, RemoteException;

   public abstract void initPositionGroup( final String accountId ) throws KANException, RemoteException;

   public abstract void initPositionGrade( final String accountId ) throws KANException, RemoteException;

   public abstract void initEmployeePosition( final String accountId ) throws KANException, RemoteException;

   public abstract void initEmployeePositionGrade( final String accountId ) throws KANException, RemoteException;

   public abstract void initBranch( final String accountId ) throws KANException, RemoteException;

   public abstract void initLocation( final String accountId ) throws KANException, RemoteException;

   public abstract void initSystemLogModule( final String accountId ) throws KANException, RemoteException;

   public abstract void initSystemLogOperType( final String accountId ) throws KANException, RemoteException;

   public abstract void initWorkflow( final String accountId ) throws KANException, RemoteException;

   public abstract void initSkill( final String accountId ) throws KANException, RemoteException;

   public abstract void initEntity( final String accountId ) throws KANException, RemoteException;

   public abstract void initContractType( final String accountId ) throws KANException, RemoteException;

   public abstract void initEmployeeStatus( final String accountId ) throws KANException, RemoteException;

   public abstract void initEducation( final String accountId ) throws KANException, RemoteException;

   public abstract void initLanguage( final String accountId ) throws KANException, RemoteException;

   public abstract void initExchangeRate( final String accountId ) throws KANException, RemoteException;

   public abstract void initItem( final String accountId ) throws KANException, RemoteException;

   public abstract void initBank( final String accountId ) throws KANException, RemoteException;

   public abstract void initTax( final String accountId ) throws KANException, RemoteException;

   public abstract void initItemGroup( final String accountId ) throws KANException, RemoteException;

   public abstract void initBusinessType( final String accountId ) throws KANException, RemoteException;

   public abstract void initItemMapping( final String accountId ) throws KANException, RemoteException;

   public abstract void initMembership( final String accountId ) throws KANException, RemoteException;

   public abstract void initCertification( final String accountId ) throws KANException, RemoteException;

   public abstract void initSocialBenefitSolution( final String accountId ) throws KANException, RemoteException;

   public abstract void initCommercialBenefitSolution( final String accountId ) throws KANException, RemoteException;

   public abstract void initSickLeaveSalary( final String accountId ) throws KANException, RemoteException;

   public abstract void initAnnualLeaveRule( final String accountId ) throws KANException, RemoteException;

   public abstract void initCalendarHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initShiftHeader( final String accountId ) throws KANException, RemoteException;

   public abstract void initBusinessContractTemplate( final String accountId ) throws KANException, RemoteException;

   public abstract void initMessageTemplate( final String accountId ) throws KANException, RemoteException;

   public abstract void initLaborContractTemplate( final String accountId ) throws KANException, RemoteException;

   public abstract void initResignTemplate( final String accountId ) throws KANException, RemoteException;

   public abstract void initStaffMenu( final String accountId, final String reportHeaderId ) throws KANException, RemoteException;

   public abstract void initTableReport( final String accountId, final String tableId ) throws KANException, RemoteException;

   /**
    * 同步添加
    * @param userName
    * @throws KANException
    * @throws RemoteException
    */
   public abstract void addErrorCount( final String userName ) throws KANException, RemoteException;

   /**
    * 同步删除
    * @param userName
    * @throws KANException
    * @throws RemoteException
    */
   public abstract void removeUserFromBlackList( final String userName ) throws KANException, RemoteException;

   /**
    * 远程验证验证码
    * @param sessionId
    * @throws KANException
    * @throws RemoteException
    */
   public abstract Boolean validCaptcha( final String sessionId, final String captcha ) throws KANException, RemoteException;

}
