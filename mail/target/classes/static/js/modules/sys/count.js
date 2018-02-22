var vm = new Vue({
	el:'#rrapp',
	data:{
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"欢迎页",
        count:{
        	companyCount:0,
        }
	},
	methods: {
		getCount:function(){
			$.getJSON(baseURL + "login/getCountInfo.do", function(r){
				vm.count = r.count;
			});
		}
	},
	created: function(){
		this.getCount();
	}
});

