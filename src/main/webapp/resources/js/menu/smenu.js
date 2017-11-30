
jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			name: "required",
			term: {
				required: true,
				minlength: 4
			},
			sort: {
				required: true,
				number: true
			}			
		},
		messages: {
			name: "请输入菜单名称",
			term: {
				required:"请输入",
				minlength:"请输入URL term, at least 4 characters",
			},
			sort: {
				required: "请输入序号",
				number: "please input number"
			}
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	validator = $("#FormEditModal").validate({
		rules: {
			name: "required",
			term: {
				required: true,
				minlength: 4
			},
			sort: {
				required: true,
				number: true
			}			
		},
		messages: {
			name: "请输入菜单名称",
			term: "请输入URL term, at least 4 characters",
			sort: {
				required: "请输入序号",
				number: "请输入number"
			}
		}
	});
	cm_func.formValidators.FormEditModal = validator;
});