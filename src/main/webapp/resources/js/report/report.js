jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
//			create_datetime: {
//				required: true,
//			},			
//			line_id: {
//				required: true,
//				number:true,
//			},
//			dan_id: {
//				required: true,
//				number:true,
//			},
			first_load: {
				required: true,
			},
			last_load: {
				required: true,
			},
			lunch_time: {
				required: true,
				number:true,
			},
			wait_time: {
				required: true,
				number:true,
			},
			output: {
				required: true,
				number:true,
			},
		},
		messages: {
//			create_datetime: {
//				required: "请输入时间",
//			},			
//			line_id: {
//				required: "请输入总线名称",
//				number:"请输入number",
//			},
//			dan_id: {
//				required: "请输入工段名称",
//				number:"请输入number",
//			},
			first_load: {
				required: "请输入第一台上线时间",
			},
			last_load: {
				required: "请输入最后一台下线时间",
			},
			lunch_time: {
				required: "请输入吃饭时间min",
				number:"please input number",
			},
			wait_time: {
				required: "请输入等待上下游时间min",
				number:"please input number",
			},
			output: {
				required: "请输入产量",
				number:"please input number",
			},
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	var validator = $("#FormEditModal").validate({
		rules: {
			create_datetime: {
				required: true,
			},			
			line_id: {
				required: true,
				number:true,
			},
			dan_id: {
				required: true,
				number:true,
			},
			first_load: {
				required: true,
			},
			last_load: {
				required: true,
			},
			lunch_time: {
				required: true,
				number:true,
			},
			wait_time: {
				required: true,
				number:true,
			},
			output: {
				required: true,
				number:true,
			},
		},
		messages: {
			create_datetime: {
				required: "请输入时间",
			},			
			line_id: {
				required: "请输入总线名称",
				number:"please input number",
			},
			dan_id: {
				required: "请输入工段名称",
				number:"please input number",
			},
			first_load: {
				required: "请输入第一台上线时间",
			},
			last_load: {
				required: "请输入最后一台下线时间",
			},
			lunch_time: {
				required: "请输入吃饭时间min",
				number:"please input number",
			},
			wait_time: {
				required: "请输入等待上下游时间min",
				number:"please input number",
			},
			output: {
				required: "请输入产量",
				number:"please input number",
			},
		}
	});
	cm_func.formValidators.FormEditModal = validator;
	
	$('#line').on('change', function() {
		// alert(this.value);
		line_id = this.value;
		dan_list = map_dan[line_id];
		
		var $el = $("#dan");
		$el.empty(); // remove old options
		for(var i=0; i<dan_list.length; i++){
			var dan = dan_list[i];		
			$el.append($("<option></option>").attr("value", dan.id).text(dan.name));
		}
	});
	
	$('#view').on('click', function() {
		// alert(this.value);
		line_id = $('#line_input').val();
		start_day = $('#start_day').val();
		end_day = $('#end_day').val();
		
		var url = window.location.href;
		var arrayOfStrings = url.split("?");
		
		var form = document.createElement("form");
		form.method = "POST";
	    form.action = arrayOfStrings[0];  
		var params = {};
		params.start_day = start_day;
		params.end_day = end_day;
		params.line_id = line_id;
		for (var property in params) {
		    if (params.hasOwnProperty(property)) {
		        var value = params[property];
		        var element1 = document.createElement("input"); 
		        element1.value=value;
			    element1.name=property;
			    form.appendChild(element1);  
		    }
		}
	    document.body.appendChild(form);
	    form.style.display = 'none';
	    form.submit();
	});
	
	
	$('.datetimepicker').datetimepicker({
		format: "YYYY-MM-DD hh:mm:ss"
    });
	
	$('.datepicker1').each(function( index ) {
		val = $(this).data("value");
		console.log(val);
		$(this).datetimepicker({
			format: "YYYY-MM-DD",
	    }).val(val);
	});
});