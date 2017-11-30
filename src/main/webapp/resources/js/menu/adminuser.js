jQuery(document).ready(function() {
	var url = "/" + cm_func.cat.rootdir + "/webapi/v1/"+cat.term+"/"+cat.subterm+"/ac_check";
	var validator = $("#FormNewModal").validate({
		rules : {
			name : "required",
			role : "required",
			username : {
				required : true,
				remote : {
					url : url,
					type : "post",
					//dataType : "json",
					// contentType: "application/json",
					// data : JSON.stringify(param)
					data : {
						username : function() {
							return $("#username").val();
						}
					}
				}
			},
			password : {
				required : true
			}
		},
		messages : {
			name : "请输入名称",
			role : "请输入角色",
			username : {
				required : "请输入用户名",
				remote : "Duplicate Username"
			},
			password : "请输入密码"
		}
	});
	cm_func.formValidators.FormNewModal = validator;

	validator = $("#FormEditModal").validate({
		rules : {
			name : "required",
		},
		messages : {
			name : "请输入角色名称*",
		}
	});
	cm_func.formValidators.FormEditModal = validator;

	$("#admin_role").on('change', function() {
		role = $(this).val();
		if (role != undefined && role.length > 0) {
			var url = window.location.href;
			var arrayOfStrings = url.split("?");
			if (arrayOfStrings.length >= 2) {
				url = arrayOfStrings[0] + '?role=' + role;
			} else {
				url = arrayOfStrings[0] + '?role=' + role;
			}
			window.location.href = url;
		}

	});
});
