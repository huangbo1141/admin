jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			name: "required",			
		},
		messages: {
			name: "Please enter 角色名称*",
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	validator = $("#FormNewModal").validate({
		rules: {
			name: "required",			
		},
		messages: {
			name: "Please enter 角色名称*",
		}
	});
	cm_func.formValidators.FormEditModal = validator;
});