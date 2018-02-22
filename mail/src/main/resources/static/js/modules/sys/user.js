$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'user/list',
        datatype: "json",
        colModel: [		
        	{ label: 'id',name:'id',width:0,hidden:true,key:true},
			{ label: '用户编码', name: 'code', width: 45},
			{ label: '用户名', name: 'name', width: 45 },
            { label: '所属部门', name: 'deptName', width: 55 },
			{ label: '邮箱', name: 'email', width: 70 },
			{ label: '手机号', name: 'phone', width: 50 },
			{ label: '状态', name: 'state', width: 40, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'addDate', width: 80},
			{ label: '头像', name: 'visitUrl', width: 60, formatter: function(value, options, row){
				if(value!=null && value!=''){
					return '<img alt="图标" src='+httpBaseURL+'myFile/'+value+' width="30" height="30" >';	
				}else{
					return "";
				}
			}},
			{ label: '操作', name: 'id',  width: 80, formatter: function(value, options, row){
				return "<a href='javascript:;' onclick=resetMM('"+value+"') >重置密码</a>"
				}}
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
    
    new AjaxUpload('#upload', {
        action: baseURL + 'file/upload?token='+token+'&time=' + new Date().getTime(),
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
            	//vm.user.visitUrl=httpBaseURL+'myFile/'+r.visitUrl;
            	vm.user.icon=r.fileId;
            	Vue.set(vm.user,'visitUrl',httpBaseURL+'myFile/'+r.visitUrl )
            }else{
                alert(r.msg);
            }
        }
    });
});

function resetMM(id){
	confirm('确定将此用户的密码重置为123456？', function(){
		$.ajax({
			type: "POST",
		    url: baseURL + "user/reset/"+id,
            contentType: "application/json",
		    success: function(r){
				if(r.code == 0){
					alert('操作成功');
				}else{
					alert(r.msg);
				}
			}
		});
	});
}

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
		roleList:{},
		user:{
			state:0,
			deptId:null,
            deptName:null,
			roleList:[],
			visitUrl:null,
			icon:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			$("#user_dywe_pwd").show();
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {deptName:null, deptId:null,state:0, roleList:[],sex:1};
			
			//获取角色信息
			this.getRoleList();

			vm.getDept();
		},
        getDept: function(){
            //加载部门树
            $.get(baseURL + "dept/tree.do", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("id", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.user.deptName = node.name;
				}
            })
        },
		update: function () {
			$("#user_dywe_pwd").hide();
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "user/delete",
                    contentType: "application/json",
				    data: JSON.stringify(userIds),
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
			var url = vm.user.id == null ? "user/save" : "user/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
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
		getUser: function(userId){
			$.get(baseURL + "user/getInfo/"+userId, function(r){
				vm.user = r.user;
				vm.user.pwd = null;

                vm.getDept();
			});
		},
		getRoleList: function(){
			$.get(baseURL + "role/select", function(r){
				vm.roleList = r.roleList;
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
                    vm.user.deptId = node[0].id;
                    vm.user.deptName = node[0].name;

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