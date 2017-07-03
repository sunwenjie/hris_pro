package com.kan.base.web.renders.setting;

public class BaseSetting
{
   /**
    * �Ƿ���CheckBox�ؼ�
    */
   private boolean hasCheckBox = true;
   /***
    * 
    */
   private String checkBoxName;
   /**
    * ��ѡ�򵥻��ص�������
    */
   private String checkBoxClickFunName;
   /**
    * �ڵ㵥���¼�������
    */
   private String noteClickFunName;
   /**
    * �����¼�������Ҫ�ش��Ķ�������
    */
   private String onClickFunParametes[];

   


   public BaseSetting()
   {
      // Ĭ�Ϸ�������
   }
   public BaseSetting(boolean hasCheckBox)
   {
      // Ĭ�Ϸ�������
      this.hasCheckBox = hasCheckBox;
   }

   public boolean isHasCheckBox()
   {
      return hasCheckBox;
   }

   public void setHasCheckBox( boolean hasCheckBox )
   {
      this.hasCheckBox = hasCheckBox;
   }

   public String getNoteClickFunName()
   {
      return noteClickFunName;
   }

   public void setNoteClickFunName( String onClickFunName )
   {
      this.noteClickFunName = onClickFunName;
   }

   public String[] getOnClickFunParametes()
   {
      return onClickFunParametes;
   }
   /***
    * ���ýڵ�ص������ش�������������
    * @param noteClickFunParametes ����������
    */
   public void setOnClickFunParametes( String[] noteClickFunParametes )
   {
      this.onClickFunParametes = noteClickFunParametes;
   }
   /***
    * ���ýڵ�ص������ش�������������
    * @param noteClickFunParametes  �����������ַ���
    * @param split  �м���split����
    */
   public void setOnClickFunParametes( String noteClickFunParametes,String split )
   {
      this.onClickFunParametes = noteClickFunParametes.split( split );
   }
   /**
    * ���ýڵ�ص������ش������������ԣ��м��á�,������
    * @param noteClickFunParametes  �����������ַ���
    */
   public void setOnClickFunParametes( String noteClickFunParametes )
   {
      this.onClickFunParametes = noteClickFunParametes.split( "," );
   }

   public String getCheckBoxClickFunName()
   {
      return checkBoxClickFunName;
   }

   public void setCheckBoxClickFunName( String checkBoxClickFunName )
   {
      this.checkBoxClickFunName = checkBoxClickFunName;
   }
   public String getCheckBoxName()
   {
      return checkBoxName;
   }
   public void setCheckBoxName( String checkBoxName )
   {
      this.checkBoxName = checkBoxName;
   }
  
}
