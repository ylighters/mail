var map;    // 创建Map实例
var myValue;
/*$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'company/listAll',
        datatype: "json",
        colModel: [			
			{ label: '公司/网点名称', name: 'name', width: 75 },
			{ label: '编码', name: 'code', width: 35 },
			{ label: '地址', name: 'address',  width: 100},
			{ label: '法人', name: 'legalPerson', width:35},
			{ label: '联系方式', name: 'lxfs', width: 50 },
			{ label: '状态', name: 'state', width: 35, formatter: function(value, options, row){
				return value === 0 ? 
						'<span class="label label-danger">禁用</span>' : 
						'<span class="label label-success">正常</span>';
				}},
			{ label: '图标', name: 'visitUrl', width: 50, formatter: function(value, options, row){
				if(value!=null && value!=''){
					return '<img alt="图标" src='+httpBaseURL+'myFile/'+value+' width="30" height="30" >';	
				}else{
					return "";
				}
				}},
			{ label: '位置', name: 'id', width: 35, key:true, formatter: function(value, options, row){
				return "<a href=javascript:; onclick=showWz('"+value+"') >查看</a>";
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
		// enable tree grid
		treeGrid:true,
		// which column is expandable
		ExpandColumn:"name",
		// datatype
		treedatatype:"json",
		// the model used
		"treeGridModel":"nested",
		// configuration of the data comming from server
		"treeReader":{
			"left_field":"lft",
			"right_field":"rgt",
			"level_field":"level",
			"leaf_field":"isLeaf",
			"expanded_field":"expanded",
			"loaded":"loaded",
			"icon_field":"icon"
		},
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
    
 
});*/

var Menu = {
	    id: "menuTable",
	    table: null,
	    layerIndex: -1
	};
	/**
	 * 初始化表格的列
	 */
	Menu.initColumn = function () {
	    var columns = [
	        {field: 'selectItem', radio: true},
	       /* {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},*/
	        {title: '公司/网点名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '80px'},
	        {title: '编码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '70px'},
	        {title: '地址', field: 'address', align: 'center', valign: 'middle', sortable: true, width: '130px'},
	        {title: '法人', field: 'legalPerson', align: 'center', valign: 'middle', sortable: true, width: '50px'},
	        {title: '联系方式', field: 'lxfs', align: 'center', valign: 'middle', sortable: true, width: '70px'},
	        {title: '安全等级', field: 'securityLever', align: 'center', valign: 'middle', sortable: true, width: '70px'},
	        {title: '网点类型', field: 'lever', align: 'center', valign: 'middle', sortable: true, width: '70px', formatter: function(item, index){
	        	return item.lever;
	        }},
	        
	        {title: '图标', field: 'visitUrl', align: 'center', valign: 'middle', sortable: true, width: '60px', formatter: function(item, index){
	        	if(item.visitUrl!=null && item.visitUrl!=''){
					return '<img alt="图标" src='+httpBaseURL+'myFile/'+item.visitUrl+' width="30" height="30" >';	
				}else{
					return "";
				}
	        }},
	        {title: '状态', field: 'state', align: 'center', valign: 'middle', sortable: true, width: '60px', formatter: function(item, index){
	        	return item.state === 0 ? 
						'<span class="label label-danger">禁用</span>' : 
						'<span class="label label-success">正常</span>';
	        }},
	        {title: '位置', field: 'jd', align: 'center', valign: 'middle', sortable: true, width: '60px', formatter: function(item, index){
	        	return "<a href=javascript:; onclick=showWz('"+item.id+"') >查看</a>";
	        }}]
	    return columns;
	};

	function getMenuId () {
	    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
	    if (selected.length == 0) {
	        alert("请选择一条记录");
	        return false;
	    } else {
	        return selected[0].id;
	    }
	}


	$(function () {
	    var colunms = Menu.initColumn();
	    var table = new TreeTable(Menu.id, baseURL + 'company/listAll', colunms);
	    table.setExpandColumn(2);
	    table.setIdField("id");
	    table.setCodeField("id");
	    table.setParentCodeField("parentId");
	    table.setExpandAll(false);
	    table.init();
	    Menu.table = table;
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
	            	$("#company_show_img_div").show();
	            	$("#company_show_img").attr("src",baseURL+'myFile/'+r.visitUrl);
	            	vm.company.icon=r.fileId;
	            	return;
	            }else{
	                alert(r.msg);
	            }
	        }
	    });
	});

function showWz(id){
	layer.open({
		  type: 2, 
		  title:'查看位置',
		  area: ['650px','550px'],
		  content: '/modules/sys/bdMap.html?id='+id
	}); 
};

function setJwd(e){
	$("#company_aoru_jd").val(e.point.lng);
	$("#company_aoru_wd").val(e.point.lat);
	vm.company.jd=e.point.lng;
	vm.company.wd=e.point.lat;
}
function setPlace(){
	map.clearOverlays();    //清除地图上所有覆盖物
	function myFun(){
		var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
		$("#company_aoru_jd").val(pp.lng);
		$("#company_aoru_wd").val(pp.lat);
		vm.company.jd=pp.lng;
		vm.company.wd=pp.lat;
		map.centerAndZoom(pp, 18);
		map.addOverlay(new BMap.Marker(pp));    //添加标注
	}
	var local = new BMap.LocalSearch(map, { //智能搜索
	  onSearchComplete: myFun
	});
	local.search(myValue);
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
			name: null
		},
		showList: true,
		companyList:[],
		title:null,
		operType:'add',
		company:{
			type:0,
			id:null,
			icon:null,
			visitUrl:null,
			parentId:null,
			parentName:null,
			jd:null,
		    wd:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.operType='add';
			$("#company_show_img_div").hide();
			vm.company = {type:0,parentName:null};
			// 百度地图API功能
		    vm.setBdMap();
		    vm.setCompanyList();
		},
		setCompanyList:function(){
			/*$.get(baseURL + "company/companyList/0", function(r){
				vm.companyList=r.companyList;
    		});*/
			//加载部门树
            $.get(baseURL + "company/allList?companyId="+vm.company.id, function(r){
            	
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.companyList);
                var node = ztree.getNodeByParam("id", vm.company.parentId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.company.parentName = node.name;
				}
            })
		},
		setBdMap:function(){
			map = new BMap.Map("allmap");
			if(vm.company.jd!=null&&vm.company.wd!=null){
				var point=new BMap.Point(vm.company.jd,vm.company.wd);
				map.centerAndZoom(point, 15);
				var marker = new BMap.Marker(point);
				map.addOverlay(marker);
			}else{
				map.centerAndZoom(new BMap.Point(116.593,35.420), 15);
				map.setCurrentCity("济宁");          // 设置地图显示的城市 此项是必须设置的
			}
			  // 初始化地图,设置中心点坐标和地图级别
			//map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
			map.addEventListener("click", setJwd);
			ac = new BMap.Autocomplete(    //建立一个自动完成的对象
					{"input" : "suggestId"
					,"location" : map
				});
			ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			    var str = "";
				var _value = e.fromitem.value;
				var value = "";
				if (e.fromitem.index > -1) {
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
				value = "";
				if (e.toitem.index > -1) {
					_value = e.toitem.value;
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
				$("#searchResultPanel").innerHTML = str;
			});
			ac.addEventListener("onconfirm", function(e) {  
				//鼠标点击下拉列表后的事件
				var _value = e.item.value;
					myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
					$("#searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
					setPlace();
			});
		},
		update: function () {
			var company = getMenuId();
			if(company == null){
				return ;
			}
			
			vm.operType='update';
			vm.showList = false;
            vm.title = "修改";
            vm.getCompany(company);
            
           // vm.setBdMap;
           
		},
		deptTree:function(){
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
                    vm.company.parentId = node[0].id;
                    vm.company.parentName = node[0].name;
                    layer.close(index);
                }
            });
			
		},
		del: function () {
			var companyId = getMenuId();
			if(companyId == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "company/deleteOne/"+companyId,
                    contentType: "application/json",
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
		getCompany: function(companyId){
            $.get(baseURL + "company/info/"+companyId, function(r){
            	
            	vm.company = r.company;
            	if(vm.company.icon!=null){
        			$("#company_show_img_div").show();
                	$("#company_show_img").attr("src",baseURL+'myFile/'+vm.company.visitUrl);
        		}
            	vm.setBdMap();
            	vm.setCompanyList();
    		});
		},
		saveOrUpdate: function () {		
			var url = vm.company.id == null ? "company/save" : "company/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.company),
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
	         Menu.table.refresh();
		}
	}
});