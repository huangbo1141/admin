jQuery(document).ready(
		function() {
			var validator = $("#FormNewModal").validate({
				rules : {
					name : {
						required : true,
					},
					serial : "required",
					type : {
						required : true,
					},
					line : {
						required : true,
					},
					dan : {
						required : true,
					},
					part : {
						required : true,
					},
				},
				messages : {
					time_value : "请输入姓名",
					serial : "请输入工号",
					type : "请输入平台权限",
					line : "请输入生产线",
					dan : "请输入工段",
					part : "请输入部门",
				}
			});
			cm_func.formValidators.FormNewModal = validator;

			var validator = $("#FormEditModal").validate({
				rules : {
					name : {
						required : true,
					},
					serial : "required",
					type : {
						required : true,
					},
					line : {
						required : true,
					},
					dan : {
						required : true,
					},
				},
				messages : {
					time_value : "请输入姓名",
					serial : "请输入工号",
					type : "请输入平台权限",
					line : "请输入生产线",
					dan : "请输入工段",
					part : "请输入部门",
				}
			});
			cm_func.formValidators.FormEditModal = validator;

			$('.line').on(
					'change',
					function() {
						// alert(this.value);
						line_id = this.value;
						dan_list = map_dan[line_id];

						var $el = $(".dan");
						$el.empty(); // remove old options
						for (var i = 0; i < dan_list.length; i++) {
							var dan = dan_list[i];
							$el.append($("<option></option>").attr("value",
									dan.id).text(dan.name));
						}
					});

			$('.usertype').on('change', function() {
				// alert(this.value);
				var index = $(".usertype").index( $(this) );
				var dlgid = "";
				if(index == 0){
					//edit
					dlgid = "FormEditModal";
				}else{
					//new
					dlgid = "FormNewModal";
				}
				val = this.value;
				if (val == "5") {
					$(".divdan").eq(index).hide();
					$(".divline").eq(index).hide();
					
				} else {
					$(".divdan").eq(index).show();
					$(".divline").eq(index).show();
				}
			});
		});


cm_func.updateDialogContentEx = function(infoData) {
	var classname = infoData.className;
	var dlgid = infoData.dlgid;
	var formid = 'Form' + dlgid;
	var pointer = infoData.pointer;
	var model = $(pointer).parent().data("data");
	index = 0;
	if (classname == 'ac_new') {
		//return false;
		index = 1;
	} else if (classname == 'ac_modify') {
		index = 0;
	}
	val = $('.usertype').eq(index).val();
	if(val == "5"){
		$(".divdan").eq(index).hide();
		$(".divline").eq(index).hide();
	}else{
		$(".divdan").eq(index).show();
		$(".divline").eq(index).show();
	}

}