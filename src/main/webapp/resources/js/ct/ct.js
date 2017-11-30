jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			name: "required",			
			v1: {
				required: true,
				number: true
			},
			v2: {
				required: true,
				number: true
			},
		},
		messages: {
			name: "请输入总线名称",
			v1: "请输入上午CTs",
			v2: "请输入下午CTs",
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	var validator = $("#FormEditModal").validate({
		rules: {
			name: "required",			
			v1: {
				required: true,
				number: true
			},
			v2: {
				required: true,
				number: true
			},
		},
		messages: {
			name: "请输入总线名称",
			v1: {
				required:"请输入上午CTs",
				number:"please input number"
			},
			v2: {
				required:"请输入下午CTs",
				number:"please input number"
			}
		}
	});
	cm_func.formValidators.FormEditModal = validator;
});