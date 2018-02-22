$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'kdy/list',
        datatype: "json",
        colModel: [		
        	{ label: 'id',name:'id',width:0,hidden:true,key:true},
			{ label: '用户名', name: 'name', width: 75 },
            { label: '所属公司', name: 'companyName', width: 75 },
			{ label: '身份证', name: 'codeNumber', width: 80 },
			{ label: '快递证', name: 'kdCode', width: 80 },
			{ label: '联系方式', name: 'lxfs', width: 80 },
			{ label: '添加时间', name: 'operDate', width: 90},
			{ label: '备注', name: 'remark', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId"
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
		showList: true,
		title:null,
		kdy:{
			companyName:null,
			id:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.kdy = {companyName:null,id:null};
			vm.getDept();
		},
        getDept: function(){
            //加载部门树
            $.get(baseURL + "company/companyList/all", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.companyList);
                var node = ztree.getNodeByParam("id", vm.kdy.companyId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.kdy.companyName = node.name;
				}
            })
        },
		update: function () {
			var kdyId = getSelectedRow();
			if(kdyId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			
			vm.getKdy(kdyId);
		},
		del: function () {
			var kdyIds = getSelectedRows();
			if(kdyIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "kdy/delete",
                    contentType: "application/json",
				    data: JSON.stringify(kdyIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
			var url = vm.kdy.id == null ? "kdy/save" : "kdy/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.kdy),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getKdy: function(userId){
			$.get(baseURL + "kdy/info/"+userId, function(r){
				vm.kdy = r.kdy;
                vm.getDept();
			});
		},
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.kdy.companyId = node[0].id;
                    vm.kdy.companyName = node[0].name;
                    layer.close(index);
                }
            });
        },
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
		}
	}
});