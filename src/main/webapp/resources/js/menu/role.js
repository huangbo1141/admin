jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			name: "required",			
		},
		messages: {
			name: "请输入角色名称*",
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	validator = $("#FormEditModal").validate({
		rules: {
			name: "required",			
		},
		messages: {
			name: "请输入角色名称*",
		}
	});
	cm_func.formValidators.FormEditModal = validator;
});