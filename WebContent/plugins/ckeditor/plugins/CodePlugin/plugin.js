var CKEDITOR_CodePlugin_dialog_config =[['��ѡ��', '0'], ['��������', '1'], ['�ַ�����', '2'], ['��������', '3']];

CKEDITOR.plugins.add('CodePlugin',
{
    init: function (editor) {
    	
        // Add the link and unlink buttons.
        editor.addCommand('insertProperties', {exec : function( editor ){showPropertiesModal();}}); 
        editor.ui.addButton('Code',
        {                               
            label: CKEDITOR.lang.detect() == 'en' ? 'Parameter' : '����',
            command: 'insertProperties'
        });
    } 
});