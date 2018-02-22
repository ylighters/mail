$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'android/list',
        datatype: "json",
        colModel: [		
        	{ label: 'id',name:'id',width:0,hidden:true,key:true},
			{ label: '版本', name: 'version', width: 45},
			{ label: '下载', name: 'fileId', width: 75 , formatter: function(value, options, row){
				return "<a href=javascript:; onclick=downloadApp('"+row.fileId+"') >下载</a>";
				}},
           
			{ label: '添加时间', name: 'addDate', width: 90 },
			{ label: '备注', name: 'remark', width: 80 }
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
        action: baseURL + 'file/uploadFile?token='+token+'&time=' + new Date().getTime(),
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(apk)$/.test(extension.toLowerCase()))){
                alert('只支持apk格式！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
            	alert("上传成功！");
            	vm.app.fileId=r.fileId;
            }else{
                alert(r.msg);
            }
        }
    });
});

function downloadApp(fileId){
	
	window.open(baseURL+"file/downloadFile/"+fileId)
}
var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			version: null
		},
		showList: true,
		title:null,
		roleList:{},
		app:{
			fileId:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.app = {fileId:null};
			
		},
		del: function () {
			var appIds = getSelectedRows();
			if(appIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "android/delete",
                    contentType: "application/json",
				    data: JSON.stringify(appIds),
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
		downloadFile:function(){
			if(vm.app.fileId==null||vm.app.fileId==""){
        		alert("数据错误，请刷新后再试！");
        		return;
        	}
        	window.open(baseURL+"file/downloadFile/"+vm.app.fileId)
		},
		saveOrUpdate: function () {
			$.ajax({
				type: "POST",
			    url: baseURL + "android/save",
                contentType: "application/json",
			    data: JSON.stringify(vm.app),
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
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'version': vm.q.version},
                page:page
            }).trigger("reloadGrid");
		}
	}
});