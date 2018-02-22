$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'video/list',
        datatype: "json",
        colModel: [		
        	{ label: 'id',name:'id',width:0,hidden:true,key:true},
			{ label: '公司/网点名称', name: 'companyName', width: 75 },
            { label: '监控地址', name: 'url', width: 75 },
			{ label: '状态', name: 'state', width: 80 , formatter: function(value, options, row){
				return value === 0 ? 
						'<span class="label label-danger">禁用</span>' : 
						'<span class="label label-success">正常</span>';
				}},
			{ label: '操作时间', name: 'operDate', width: 80 },
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
        }
    }
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			name: null
		},
		showList: true,
		title:null,
		video:{
			companyName:null,
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.video = {companyName:null,state:0};
			vm.getDept();
		},
        getDept: function(){
            //加载部门树
            $.get(baseURL + "company/companyList/all", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.companyList);
                var node = ztree.getNodeByParam("id", vm.kdy.companyId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.video.companyName = node.name;
				}
            })
        },
		update: function () {
			var videoId = getSelectedRow();
			if(videoId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			
			vm.getVideo(videoId);
		},
		del: function () {
			var videoIds = getSelectedRows();
			if(videoIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "video/delete",
                    contentType: "application/json",
				    data: JSON.stringify(videoIds),
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
			var url = vm.video.id == null ? "video/save" : "video/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.video),
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
		getVideo: function(videoId){
			$.get(baseURL + "video/info/"+videoId, function(r){
				vm.video = r.video;
                vm.getDept();
			});
		},
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择所属公司/网点",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.video.companyId = node[0].id;
                    vm.video.companyName = node[0].name;
                    layer.close(index);
                }
            });
        },
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		}
	}
});