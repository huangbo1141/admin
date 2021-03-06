jQuery(document).ready(function() {
	var validator = $("#FormNewModal").validate({
		rules : {
			time_value : {
				required : true,
			},
			time_type : "required",
			ta : {
				required : true,
				number : true
			},
			oee : {
				required : true,
				number : true
			},
		},
		messages : {
			time_value : {
				required : "请输入",
			},
			time_type : "请输入年月周日",
			ta : {
				required : "请输入TA目标值(%)",
				number : "Please input number"
			},
			oee : {
				required : "请输入OEE目标值(%)",
				number : "Please input number"
			}
		}
	});
	cm_func.formValidators.FormNewModal = validator;

	var validator = $("#FormEditModal").validate({
		rules : {
			time_value : {
				required : true,
			},
			time_type : "required",
			ta : {
				required : true,
				number : true
			},
			oee : {
				required : true,
				number : true
			},
		},
		messages : {
			time_value : {
				required : "请输入"
			},
			time_type : "请输入年月周日",
			ta : {
				required : "请输入TA目标值(%)",
				number : "Please input number"
			},
			oee : {
				required : "请输入OEE目标值(%)",
				number : "Please input number"
			}
		}
	});
	cm_func.formValidators.FormEditModal = validator;

	$("#time_type1").on('change', function() {
		role = $(this).val();
		switch (role) {
		case "1": {
			$("#time_value1").attr("placeholder", "YYYY");
			break;
		}
		case "2": {
			$("#time_value1").attr("placeholder", "YYYY-MM");
			break;
		}
		case "3": {
			$("#time_value1").attr("placeholder", "YYYY-WW");
			break;
		}
		case "4": {
			$("#time_value1").attr("placeholder", "YYYY-MM-DD");
			break;
		}
		}

	});
	$("#time_type2").on('change', function() {
		role = $(this).val();
		switch (role) {
		case "1": {
			$("#time_value2").attr("placeholder", "YYYY");
			break;
		}
		case "2": {
			$("#time_value2").attr("placeholder", "YYYY-MM");
			break;
		}
		case "3": {
			$("#time_value2").attr("placeholder", "YYYY-WW");
			break;
		}
		case "4": {
			$("#time_value2").attr("placeholder", "YYYY-MM-DD");
			break;
		}
		}

	});
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
	} else {
		location.reload();
	}

}