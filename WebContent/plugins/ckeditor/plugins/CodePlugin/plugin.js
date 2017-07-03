var CKEDITOR_CodePlugin_dialog_config =[['请选择', '0'], ['数字类型', '1'], ['字符类型', '2'], ['日期类型', '3']];

CKEDITOR.plugins.add('CodePlugin',
{
    init: function (editor) {
    	
        // Add the link and unlink buttons.
        editor.addCommand('insertProperties', {exec : function( editor ){showPropertiesModal();}}); 
        editor.ui.addButton('Code',
        {                               
            label: CKEDITOR.lang.detect() == 'en' ? 'Parameter' : '参数',
            command: 'insertProperties'
        });
    } 
});