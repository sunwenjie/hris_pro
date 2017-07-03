package com.kan.base.util;

/**
 * ��־��¼��������
 * @author Jack
 *
 */
public enum Operate
{
   ADD("����", "Add", 1), MODIFY("�޸�", "Modify", 2), DELETE("ɾ��", "Delete", 3), SUBMIT("�ύ", "Submit", 4), ROllBACK("�˻�", "Return", 5), APPROVE("ͬ��", "Agree", 7), REJECT("�ܾ�",
         "Reject", 8), BATCH_SUBMIT("�����ύ", "Batch Submit", 9), TRANSFER_HROWNER("ת��HR�Խ���", "Transfer HR Owner", 10);

   private String name;
   private String nameEN;
   private int index;

   // ���췽��
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
