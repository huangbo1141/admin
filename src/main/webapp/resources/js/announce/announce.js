jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			time: "required",			
			content: "required"
		},
		messages: {
			time: "请输入时间",
			content:"请输入内容"
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	$('.datepicker1').datetimepicker({
		format: "YYYY-MM-DD"
    });
});