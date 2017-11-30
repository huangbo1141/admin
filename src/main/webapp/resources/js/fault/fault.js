jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {	
			reason_id: "required",	
			name: {
				required: true,
			},
			desc: {
				required: true,
			},
		},
		messages: {
			reason_id: {
				required:"请输入故障类型",
				number:"Please input number"
			},
			name: {
				required:"请输入故障序号",
			},
			desc: {
				required:"请输入原因描述",
			}
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	var validator = $("#FormEditModal").validate({
		rules: {	
			reason_id: "required",	
			name: {
				required: true,
			},
			desc: {
				required: true,
			},
		},
		messages: {
			reason_id: {
				required:"请输入故障类型",
				number:"Please input number"
			},
			name: {
				required:"请输入故障序号",
			},
			desc: {
				required:"请输入原因描述",
			}
		}
	});
	cm_func.formValidators.FormEditModal = validator;
});

cm_func.pagereload = function(event) {
	var infoData = event.data;
	tab = $(infoData.pointer).parent().data("tab");
	if (tab != undefined) {
		var url = window.location.href;
		var arrayOfStrings = url.split("?");
		if (arrayOfStrings.length >= 2) {
			url = arrayOfStrings[0] + '?mt=' + tab;
		} else {
			url = arrayOfStrings[0] + '?mt=' + tab;
		}
		window.location.href = url;
	}else{
		location.reload();
	}

}