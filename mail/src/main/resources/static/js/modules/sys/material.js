var editor;
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'material/list',
        datatype: "json",
        colModel: [		
        	{ label: 'id',name:'id',width:0,hidden:true,key:true},
			{ label: '标题', name: 'title', width: 75 },
            { label: '添加时间', name: 'addDate', width: 75 },
			{ label: '添加人', name: 'addPersonName', width: 80 },
			{ label: '类型', name: 'type', width: 80 , formatter: function(value, options, row){
				if(value==='kdy'){
					return '<span class="label label-success">公司上传</span>';
				}else{
					return '<span class="label label-danger">邮政局发布</span>';
				}
			}},
			{ label: '内容', name: 'content', width: 80, formatter: function(value, options, row){
				return "<a href=javascript:; onclick=showContent('"+row.id+"') >查看</a>";
				} },
			{ label: '阅读数量', name: 'count', width: 90}
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
    KindEditor.ready(function(K) {
    	editor = K.create('#editor_id',{
    		resizeType : 1,
			allowImageUpload : true,
			filterMode :false,
			showRemote:true,
			items : [ 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image'],
			uploadJson : baseURL+"file/uploadImage",
			afterChange : function() {  
			      //限制字数  
			      var limitNum = 3500;  //设定限制字数  
			      if(this.count('text') > limitNum) {  
				       //超过字数限制自动截取  
				       var strValue = editor.text();  
				       strValue = strValue.substring(0,limitNum);  
				       editor.text(strValue);  
				       $('.word_surplus').html("字数不得超过3500字"); //输入显示  
			       }else{
			    	   $('.word_surplus').html("");
			       } 
			       
			     }   
    	});
    });
    
    new AjaxUpload('#upload', {
        action: baseURL + 'file/uploadFile?token='+token+'&time=' + new Date().getTime(),
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onComplete : function(file, r){
            if(r.code == 0){
            	alert("上传成功！");
            	vm.message.fileIds=r.fileId;
            	return;
            }else{
                alert(r.msg);
            }
        }
    });
    
});

function showContent(id){
	$.get(baseURL + "material/info/"+id, function(r){
		layer.open({
		    type: 1,
		    title:'内容',
		    area: ['700px', '650px'],
		    content:r.message.content //注意，如果str是object，那么需要字符拼接。
		  });
	});
}
var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
		showList: true,
		title:null,
		message:{
			content:null,
			fileIds:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			editor.html("");
			vm.message = {content:null,fileIds:null};
			
		},
        downloadFile:function(){
        	if(vm.message.fileIds==null||vm.message.fileIds==""){
        		alert("数据错误，请刷新后再试！");
        		return;
        	}
        	window.open(baseURL+"file/downloadFile/"+vm.message.fileIds)
        },
		update: function () {
			var messageId = getSelectedRow();
			if(messageId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getMessage(messageId);
		},
		del: function () {
			var messageIds = getSelectedRows();
			if(messageIds == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "material/delete",
                    contentType: "application/json",
				    data: JSON.stringify(messageIds),
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
			var url = vm.message.id == null ? "material/save" : "material/update";
			editor.sync();
			vm.message.content=editor.html();
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.message),
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
		getMessage: function(messageId){
			$.get(baseURL + "material/info/"+messageId, function(r){
				vm.message=r.message;
				editor.html(r.message.content)
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
		}
	}
});