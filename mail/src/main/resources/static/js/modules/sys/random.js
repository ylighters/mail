$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'random/list',
        datatype: "json",
        colModel: [		
        	{ label: 'id',name:'id',width:0,hidden:true,key:true},
			{ label: '抽检时间', name: 'operDate', width: 75 },
            { label: '抽检人', name: 'operPersonName', width: 75 },
			{ label: '抽检方式', name: 'type', width: 80, formatter: function(value, options, row){
					if(value=='random'){
						return '<span class="label label-danger">随机抽检</span>';
					}else if(value=='byLever'){
						return '<span class="label label-success">按定等级抽检</span>';
					}else if(value=='byHand'){
						return '<span class="label label-success">手动抽检</span>';
					}else if(value=='byApp'){
						return '<span class="label label-success">app抽检</span>';
					}
				} },
			{ label: '抽检规则', name: 'checkRule', width: 80 },
			{ label: '抽检数量', name: 'count', width: 80 },
			{ label: '备注', name: 'remark', width: 100 }
        ],
        loadonce: false,
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
        },
        
        subGrid: true, // set the subGrid property to true to show expand buttons for each row
        subGridRowExpanded: showChildGrid, // javascript function that will take care of showing the child grid
		/*subgridtype: 'json', // set the subgrid type to json
		subGridUrl: function( params ) { // the url can be a function. In this case we build the urls depending on the id row
			return baseURL+'random/wdList/'+params.id; 
		},
		// description of the subgrid model
		repeateditems:false,
		subGridModel:[{
			    name: ['name','code','lxfs','securityLever','address'],
				mapping:['name','code','lxfs','securityLever','address'],
				width: [60,180,150,100,70],
				align: ["left","left","left","left","right"],
				params: false
		}]*/
    });
    
    
    function showChildGrid(parentRowID, parentRowKey) {
        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";
        var childGridURL = baseURL+'random/wdList/'+parentRowKey;
        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
        $("#" + childGridID).jqGrid({
            url: childGridURL,
            datatype: "json",
            autowidth: true,  
            colModel: [
                { label: '公司\网点名称', name: 'name', key: true, width: 75 },
                { label: '编码', name: 'code', width: 50 },
                { label: '联系方式', name: 'lxfs', width: 50 },
                { label: '安全等级', name: 'securityLever', width: 50 },
                { label: '地址', name: 'address', width: 120 }
            ],
			loadonce: true,
            width: 500,
            height: '100%'
        });

    }

    
    
    
});

function showChildGrid(parentRowID, parentRowKey) {
	 $("#" + parentRowID).append("<h1>我是内容</h1>");

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
			type: null
		},
		showList: true,
        randomSize:0,
		title:null,
		resultList:{},
		companyId:null,
		random:{
			id:null,
			count:0,
			companyName:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "抽检";
			//vm.resultList=[{id:1,name:'测试',securityLever:'A'},{id:1,name:'测试',securityLever:'A'}];
			vm.random = {type:'random',companyName:null};
			vm.getDept();
		},
        getDept: function(){
            //加载部门树
            $.get(baseURL + "company/companyList/all", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.companyList);
            })
        },
		del: function () {
			var randomIds = getSelectedRows();
			if(randomIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "random/delete",
                    contentType: "application/json",
				    data: JSON.stringify(randomIds),
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
		deleteCompanyId:function(companyId){
			var randomId=vm.random.id;
			/*vm.temp.randomId=randomId;
			vm.temp.companyId=companyId;*/
			confirm('确定要删除选中的抽检结果？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "random/deleteResult?companyId="+companyId+"&randomId="+randomId,
                    contentType: "application/json",
				    success: function(r){
						if(r.code == 0){
							alert('删除成功', function(){
                                vm.reloadRs(randomId);
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		reloadRs:function(randomId){
			$.get(baseURL + "random/getAndUpdateRs/"+randomId, function(r){
				vm.randomSize=r.randomSize;
				vm.random.count=r.randomSize;
		    	Vue.set(vm,'resultList',r.resultList );	
			});
		},
		saveOrUpdate: function () {
			if(vm.random.id==null){
				alert("抽检后才能保存");
			}
			$.ajax({
				type: "POST",
			    url: baseURL + 'random/saveRandom',
                contentType: "application/json",
			    data: JSON.stringify(vm.random),
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
		randomCheck:function(){
			vm.random.id=null;
			$.ajax({
				type: "POST",
			    url: baseURL + "random/randomCheck",
                contentType: "application/json",
			    data: JSON.stringify(vm.random),
			    success: function(r){
			    	if(r.code===0){
			    		vm.random.id=r.randomId;
			    		vm.random.count=r.randomSize;
				    	vm.randomSize=r.randomSize;
				    	if(r.randomSize!=0){
				    		Vue.set(vm,'resultList',r.resultList );	
				    	}
			    	}else{
			    		alert(r.msg);
			    	}
			    	
				}
			});
		},
		removeResult:function(){
			vm.random.id=null;
			vm.randomSize=0;
			vm.random.count=0;
			Vue.set(vm,'resultList',{});
		},
		saveRandom:function(){
			if(vm.companyId==null){
				alert("请选择公司/网点后再执行添加操作");
				return;
			}
			var randomId=vm.random.id;
			
			$.ajax({
				type: "POST",
			    url: baseURL + "random/saveResult?companyId="+vm.companyId+"&randomId="+randomId,
                contentType: "application/json",
			    success: function(r){
			    	if(r.code===0){
			    		vm.companyId=null;
			    		vm.random.companyName=null;
			    		vm.random.id=r.randomId;
			    		vm.random.count=r.randomSize;
				    	vm.randomSize=r.randomSize;
				    	if(r.randomSize!=0){
				    		Vue.set(vm,'resultList',r.resultList );	
				    	}
			    	}else{
			    		alert(r.msg);
			    	}
				}
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
                    vm.companyId=node[0].id;
                    vm.random.companyName = node[0].name;
                    layer.close(index);
                }
            });
        },
		reload: function () {
			vm.showList = true;
			console.log(1);
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			console.log(2);
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'type': vm.q.type},
                page:page
            }).trigger("reloadGrid");
			console.log(3);
		}
	}
});