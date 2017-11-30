jQuery(document).ready(function() {
	var validator = $("#FormNewLineModal").validate({
		rules : {
			name : "required",
			sort : {
				required : true,
				number : true
			},
		},
		messages : {
			name : "请输入生产线名称",
			sort : {
				required:"请输入序号",
				number:"please input number"
			},
		}
	});
	cm_func.formValidators.FormNewLineModal = validator;

	validator = $("#FormEditLineModal").validate({
		rules : {
			name : "required",
			sort : {
				required : true,
				number : true
			},
		},
		messages : {
			name : "请输入生产线名称",
			sort : {
				required:"请输入序号",
				number:"please input number"
			},
		}
	});
	cm_func.formValidators.FormEditLineModal = validator;

	validator = $("#FormNewDanModal").validate({
		rules : {
			name : "required",
			sort : {
				required : true,
				number : true
			},
			line : {
				required : true,
				number : true
			},
		},
		messages : {
			name : "请输入工段名称",
			sort : {
				required:"请输入序号",
				number:"please input number"
			},
			line : {
				required:"请输入生产线",
				number:"please input number"
			}
		}
	});
	cm_func.formValidators.FormNewDanModal = validator;

	validator = $("#FormEditDanModal").validate({
		rules : {
			name : "required",
			sort : {
				required : true,
				number : true
			},
			line : {
				required : true,
				number : true
			},
		},
		messages : {
			name : "请输入工段名称",
			sort : {
				required:"请输入序号",
				number:"please input number"
			},
			line : {
				required:"请输入生产线",
				number:"please input number"
			}
		}
	});
	cm_func.formValidators.FormEditDanModal = validator;
});

cm_func.pagereload = function(event) {
	var infoData = event.data;
	if (infoData.modeltype != undefined && infoData.modeltype.length > 0) {
		var url = window.location.href;
		var arrayOfStrings = url.split("?");
		if (arrayOfStrings.length >= 2) {
			url = arrayOfStrings[0] + '?mt=' + infoData.modeltype;
		} else {
			url = arrayOfStrings[0] + '?mt=' + infoData.modeltype;
		}
		window.location.href = url;
	}

}