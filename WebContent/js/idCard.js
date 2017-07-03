function clsIDCard(CardNo) {
  this.Valid=false;
  this.ID15='';
  this.ID18='';
  this.Local='';
  this.error='';
  if(CardNo!=null){
	  this.SetCardNo(CardNo);
	  this.GetBirthDate();
	  this.GetSex();
  }
}
// �������֤���룬15λ����18λ
clsIDCard.prototype.SetCardNo = function(CardNo) {
  this.ID15='';
  this.ID18='';
  this.Local='';
  CardNo=CardNo.replace(" ","");
  var strCardNo;
  if(CardNo.length==18) {
    pattern= /^\d{17}(\d|x|X)$/;
    if (pattern.exec(CardNo)==null)return;
    strCardNo=CardNo.toUpperCase();	
  } else if(CardNo.length==15){
    pattern= /^\d{15}$/;
    if (pattern.exec(CardNo)==null)return;
    strCardNo=CardNo.substr(0,6)+'19'+CardNo.substr(6,9);
    strCardNo+=this.GetVCode(strCardNo);
  }else{
	  this.error +='���֤���볤�Ȳ���';
	  return;
  }
  this.Valid=this.CheckValid(strCardNo);
};
// У�����֤��Ч��
clsIDCard.prototype.IsValid = function() {
  return this.Valid;
};
// ���������ַ�������ʽ���£�1981-10-10
clsIDCard.prototype.GetBirthDate = function() {
  var BirthDate='';
  if(this.Valid)BirthDate=this.GetBirthYear()+'-'+this.GetBirthMonth()+'-'+this.GetBirthDay();
  return BirthDate;
};
// ���������е��꣬��ʽ���£�1981
clsIDCard.prototype.GetBirthYear = function() {
  var BirthYear='';
  if(this.Valid)BirthYear=this.ID18.substr(6,4);
  return BirthYear;
};
// ���������е��£���ʽ���£�10
clsIDCard.prototype.GetBirthMonth = function() {
  var BirthMonth='';
  if(this.Valid)BirthMonth=this.ID18.substr(10,2);
//  if(BirthMonth.charAt(0)=='0')BirthMonth=BirthMonth.charAt(1);
  return BirthMonth;
};
// ���������е��գ���ʽ���£�10
clsIDCard.prototype.GetBirthDay = function() {
  var BirthDay='';
  if(this.Valid)BirthDay=this.ID18.substr(12,2);
  return BirthDay;
};
// �����Ա�1���У�0��Ů
clsIDCard.prototype.GetSex = function() {
  var Sex='';
  if(this.Valid)Sex=this.ID18.charAt(16)%2;
  return Sex;
};
// ����15λ���֤����
clsIDCard.prototype.Get15 = function() {
  var ID15='';
  if(this.Valid)ID15=this.ID15;
  return ID15;
};
// ����18λ���֤����
clsIDCard.prototype.Get18 = function() {
  var ID18='';
  if(this.Valid)ID18=this.ID18;
  return ID18;
};
// ��������ʡ�����磺�Ϻ��С��㽭ʡ
clsIDCard.prototype.GetLocal = function() {
  var Local='';
  if(this.Valid)Local=this.Local;
  return Local;
};
clsIDCard.prototype.GetError = function() {
	return this.error;
};
clsIDCard.prototype.GetVCode = function(CardNo17) {
  var Wi = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
  var Ai = new Array('1','0','X','9','8','7','6','5','4','3','2');
  var cardNoSum = 0;
  for (var i=0; i<CardNo17.length; i++)cardNoSum+=CardNo17.charAt(i)*Wi[i];
  var seq = cardNoSum%11;
  return Ai[seq];
};
clsIDCard.prototype.CheckValid = function(CardNo18) {
  if(!this.IsDate(CardNo18.substr(6,8))){
	  this.error = '���������';
	  return false;
  }
  var aCity={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������ ",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"���� ",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"���� ",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"};
  if(aCity[parseInt(CardNo18.substr(0,2))]==null){
	  this.error = '��ַ�벻����Ч��Χ��';
	  return false;
  }
  if(this.GetVCode(CardNo18.substr(0,17))!=CardNo18.charAt(17)){
	  this.error = '������Ч�����֤��';
	  return false;
  }
  this.ID18=CardNo18;
  this.ID15=CardNo18.substr(0,6)+CardNo18.substr(8,9);
  this.Local=aCity[parseInt(CardNo18.substr(0,2))];
  return true;
};
clsIDCard.prototype.IsDate = function(strDate) {
  var r = strDate.match(/^(\d{1,4})(\d{1,2})(\d{1,2})$/);
  if(r==null)return false;
  var d= new Date(r[1], r[2]-1, r[3]);
  return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[2]&&d.getDate()==r[3]);
};