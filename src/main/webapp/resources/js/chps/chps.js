jQuery(document).ready(function(){
	var validator = $("#FormEditModal").validate({
		rules: {			
			password: {
				required: true,
				minlength: 6
			},
		},
		messages: {
			password: {
				required:"请输入密码",
				minlength:"Enter At least 6 letters"
			}
		}
	});
	cm_func.formValidators.FormNewModal = validator;
});