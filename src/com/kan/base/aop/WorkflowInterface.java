/*
 * Created on 2013-05-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.aop;

import com.kan.base.domain.BaseVO;

/**
 * �����������ӿ�
 * @author steven.wang
 *
 */
public interface WorkflowInterface
{

   
   /**
    * @param baseVO
    * @param arg1
    * @throws Throwable
    * ����ͨ��
    */
   abstract void approveWorkflow( BaseVO baseVO,Object[] arg1) throws Throwable;

//   /**
//    * @param baseVO
//    * @param arg1
//    * @throws Throwable
//    * �����˻�
//    */
//   abstract void backWorkflow( BaseVO baseVO,Object[] arg1) throws Throwable;
   
//   /**
//    * @param baseVO
//    * @param arg1
//    * @throws Throwable
//    * �޸ĵ�ǰҵ���״̬Ϊ����������״̬
//    */
//   abstract void updateStatusWorkflow( BaseVO baseVO,Object[] arg1) throws Throwable;
   
   /**��������ǰ����ʷ����͵�ǰ����
    * @param baseVO
    * @param arg1
    * @throws Throwable
    */
   abstract void generateHistoryVO( BaseVO baseVO,Object[] arg1) throws Throwable;
   
   
   
}