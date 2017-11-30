jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			serial: {
				required: true,
			},			
			dan: {
				required: true,
			},
		},
		messages: {
			serial: "请输入工位号",
			dan: "请输入工段",
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	var validator = $("#FormEditModal").validate({
		rules: {
			serial: {
				required: true,
			},			
			dan: {
				required: true,
			},
		},
		messages: {
			serial: "请输入工位号",
			dan: "请输入工段",
		}
	});
	cm_func.formValidators.FormEditModal = validator;
	
	$('#line').on('change', function() {
		// alert(this.value);
		line_id = this.value;
		dan_list = map_dan[line_id];
		
		var $el = $("#dan");
		$el.empty(); // remove old options
		for(var i=0; i<dan_list.length; i++){
			var dan = dan_list[i];		
			$el.append($("<option></option>").attr("value", dan.id).text(dan.name));
		}
	});
	
	$('#view').on('click', function() {
		// alert(this.value);
		dan_id = $('#dan').val();
		line_id = $('#line').val();
		console.log(dan_id,line_id);
		
		var url = window.location.href;
		var arrayOfStrings = url.split("?");
		if (arrayOfStrings.length >= 2) {
			url = arrayOfStrings[0] + '?d=' + dan_id;
		} else {
			url = arrayOfStrings[0] + '?d=' + dan_id;
		}
		window.location.href = url;
	})
});