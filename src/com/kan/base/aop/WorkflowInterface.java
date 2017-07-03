/*
 * Created on 2013-05-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.aop;

import com.kan.base.domain.BaseVO;

/**
 * 工作流审批接口
 * @author steven.wang
 *
 */
public interface WorkflowInterface
{

   
   /**
    * @param baseVO
    * @param arg1
    * @throws Throwable
    * 审批通过
    */
   abstract void approveWorkflow( BaseVO baseVO,Object[] arg1) throws Throwable;

//   /**
//    * @param baseVO
//    * @param arg1
//    * @throws Throwable
//    * 审批退回
//    */
//   abstract void backWorkflow( BaseVO baseVO,Object[] arg1) throws Throwable;
   
//   /**
//    * @param baseVO
//    * @param arg1
//    * @throws Throwable
//    * 修改当前业务的状态为工作流审批状态
//    */
//   abstract void updateStatusWorkflow( BaseVO baseVO,Object[] arg1) throws Throwable;
   
   /**生成审批前的历史对象和当前对象
    * @param baseVO
    * @param arg1
    * @throws Throwable
    */
   abstract void generateHistoryVO( BaseVO baseVO,Object[] arg1) throws Throwable;
   
   
   
}