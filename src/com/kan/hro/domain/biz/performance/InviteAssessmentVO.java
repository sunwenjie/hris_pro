package com.kan.hro.domain.biz.performance;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

/***
 * º®–ß - —˚«Î∆¿π¿
 * @author Siuvan
 * 
 */
public class InviteAssessmentVO extends BaseVO
{

   /**
    * SerialVersionUID
    */
   private static final long serialVersionUID = -9134990683715095687L;

   /**
    * For DB
    */
   private String inviteId;
   private String assessmentId;
   private String inviteEmployeeId;
   private String randomKey;
   private String answer1;
   private String answer2;
   private String answer3;
   private String answer4;

   /**
    * For app
    */
   private String inviteEmployeeNameZH;
   private String inviteEmployeeNameEN;

   public InviteAssessmentVO()
   {
      super();
   }

   public InviteAssessmentVO( String assessmentId, String inviteEmployeeId )
   {
      super();
      this.assessmentId = assessmentId;
      this.inviteEmployeeId = inviteEmployeeId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( inviteId );
   }

   @Override
   public void reset() throws KANException
   {
      this.answer1 = "";
      this.answer2 = "";
      this.answer3 = "";
      this.answer4 = "";
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final InviteAssessmentVO inviteAssessmentVO = ( InviteAssessmentVO ) object;
      this.answer1 = inviteAssessmentVO.getAnswer1();
      this.answer2 = inviteAssessmentVO.getAnswer2();
      this.answer3 = inviteAssessmentVO.getAnswer3();
      this.answer4 = inviteAssessmentVO.getAnswer4();
      super.setStatus( inviteAssessmentVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   // ¡Ÿ ±«Âø’
   public void tempReset()
   {
      if ( "1".equals( super.getStatus() ) )
      {
         this.answer1 = "";
         this.answer2 = "";
         this.answer3 = "";
         this.answer4 = "";
      }
   }

   public String getInviteId()
   {
      return inviteId;
   }

   public void setInviteId( String inviteId )
   {
      this.inviteId = inviteId;
   }

   public String getAssessmentId()
   {
      return assessmentId;
   }

   public void setAssessmentId( String assessmentId )
   {
      this.assessmentId = assessmentId;
   }

   public String getInviteEmployeeId()
   {
      return inviteEmployeeId;
   }

   public void setInviteEmployeeId( String inviteEmployeeId )
   {
      this.inviteEmployeeId = inviteEmployeeId;
   }

   public String getRandomKey()
   {
      return randomKey;
   }

   public void setRandomKey( String randomKey )
   {
      this.randomKey = randomKey;
   }

   public String getAnswer1()
   {
      return answer1;
   }

   public void setAnswer1( String answer1 )
   {
      this.answer1 = answer1;
   }

   public String getAnswer2()
   {
      return answer2;
   }

   public void setAnswer2( String answer2 )
   {
      this.answer2 = answer2;
   }

   public String getAnswer3()
   {
      return answer3;
   }

   public void setAnswer3( String answer3 )
   {
      this.answer3 = answer3;
   }

   public String getAnswer4()
   {
      return answer4;
   }

   public void setAnswer4( String answer4 )
   {
      this.answer4 = answer4;
   }

   public String getInviteEmployeeNameZH()
   {
      return inviteEmployeeNameZH;
   }

   public void setInviteEmployeeNameZH( String inviteEmployeeNameZH )
   {
      this.inviteEmployeeNameZH = inviteEmployeeNameZH;
   }

   public String getInviteEmployeeNameEN()
   {
      return inviteEmployeeNameEN;
   }

   public void setInviteEmployeeNameEN( String inviteEmployeeNameEN )
   {
      this.inviteEmployeeNameEN = inviteEmployeeNameEN;
   }

}
