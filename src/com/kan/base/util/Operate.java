package com.kan.base.util;

/**
 * 日志记录操作类型
 * @author Jack
 *
 */
public enum Operate
{
   ADD("新增", "Add", 1), MODIFY("修改", "Modify", 2), DELETE("删除", "Delete", 3), SUBMIT("提交", "Submit", 4), ROllBACK("退回", "Return", 5), APPROVE("同意", "Agree", 7), REJECT("拒绝",
         "Reject", 8), BATCH_SUBMIT("批量提交", "Batch Submit", 9), TRANSFER_HROWNER("转换HR对接人", "Transfer HR Owner", 10);

   private String name;
   private String nameEN;
   private int index;

   // 构造方法
   private Operate( String name, String nameEN, int index )
   {
      this.name = name;
      this.nameEN = nameEN;
      this.index = index;
   }

   public int getIndex()
   {
      return this.index;
   }

   public String getName()
   {
      return this.name;
   }

   public String getNameEN()
   {
      return this.nameEN;
   }

   public String toString()
   {
      return this.index + "_" + this.name;
   }

   public static Operate decodeIndex( final int index )
   {
      Operate result = null;
      Operate[] operates = Operate.values();
      for ( Operate operate : operates )
      {
         if ( index == operate.getIndex() )
         {
            result = operate;
            break;
         }
      }

      return result;
   }

}
