jQuery(document).ready(function() {
	var validator = $("#FormNewModal").validate({
		rules : {
			name : {
				required : true,
			},
			serial : "required",
			type : {
				required : true,
			},
			line : {
				required : true,
			},
			dan : {
				required : true,
			},
		},
		messages : {
			time_value : "请输入姓名",
			serial : "请输入工号",
			type : "请输入平台权限",
			line : "请输入生产线",
			dan : "请输入工段",
		}
	});
	cm_func.formValidators.FormNewModal = validator;

	var validator = $("#FormEditModal").validate({
		rules : {
			name : {
				required : true,
			},
			serial : "required",
			type : {
				required : true,
			},
			line : {
				required : true,
			},
			dan : {
				required : true,
			},
		},
		messages : {
			time_value : "请输入姓名",
			serial : "请输入工号",
			type : "请输入平台权限",
			line : "请输入生产线",
			dan : "请输入工段",
		}
	});
	cm_func.formValidators.FormEditModal = validator;

	$('.line').on('change', function() {
		// alert(this.value);
		line_id = this.value;
		dan_list = map_dan[line_id];
		
		var $el = $(".dan");
		$el.empty(); // remove old options
		for(var i=0; i<dan_list.length; i++){
			var dan = dan_list[i];		
			$el.append($("<option></option>").attr("value", dan.id).text(dan.name));
		}
	})

});