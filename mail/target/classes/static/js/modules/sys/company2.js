var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        dept:{
            parentName:null,
            parentId:null
        }
    }
});

var Company = {
    id: "companyTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Company.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '公司/网点名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '编码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '地址', field: 'address', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '法人', field: 'legalPerson', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '联系方式', field: 'lxfs', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '100px'},        
        {title: '状态', field: 'state', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            return item.state=='0'?"可用":"禁用"
        }},
        {title: '操作', field: 'id',key:true,align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            return "<a href='javascript:;' >查看</a>"
        }},        
        ]
    
    return columns;
};


function getCompanyId () {
    var selected = $('#companyTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}



