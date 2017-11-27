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
			name: "Please enter 菜单名称",
			term: "Please enter URL term, at least 4 characters",
			sort: {
				required: "Please enter 序号",
				number: "Please enter number"
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
			name: "Please enter 菜单名称",
			term: "Please enter URL term, at least 4 characters",
			sort: {
				required: "Please enter 序号",
				number: "Please enter number"
			}
		}
	});
	cm_func.formValidators.FormEditModal = validator;
});