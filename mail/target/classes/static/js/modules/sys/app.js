var vm = new Vue({
	el:'#rrapp',
	data:{
        navTitle:"页面详情",
        content:{
        	fileIds:null
        }
	},
	methods: {
		getContent:function(contentId){
			$.getJSON(baseURL + "app/getContentInfo/"+contentId, function(r){
				vm.content = r.contentInfo;
			});
		},
		downloadFile:function(){
        	if(vm.content.fileIds==null||vm.content.fileIds==""){
        		alert("数据错误，请刷新后再试！");
        		return;
        	}
        	window.open(baseURL+"file/downloadFile/"+vm.content.fileIds)
        },
	},
	created: function(){
		var loc = location.href;
		var n1 = loc.length;//地址的总长度
		var n2 = loc.indexOf("?");//取得=号的位置
		var contentId = decodeURI(loc.substr(n2+1, n1-n2));//从=号后面的内容
		this.getContent(contentId);
	}
});

