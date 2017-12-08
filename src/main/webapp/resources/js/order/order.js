jQuery(document).ready(function(){
	var validator = $("#FormNewModal").validate({
		rules: {
			start_t: {
				required: true,
			},	
			end_t: {
				required: true,
			},
		},
		messages: {
			start_t: {
				required: "请输入开始时间",
			},			
			end_t: {
				required: "请输入结束时间",
			},
		}
	});
	cm_func.formValidators.FormNewModal = validator;
	
	var validator = $("#FormEditModal").validate({
		rules: {
			start_t: {
				required: true,
			},	
			end_t: {
				required: true,
			},
		},
		messages: {
			start_t: {
				required: "请输入开始时间",
			},			
			end_t: {
				required: "请输入结束时间",
			},
		}
	});
	cm_func.formValidators.FormEditModal = validator;
		
	$('.formsubmit').on('click', function() {
		// alert(this.value);
		line_id = $('#line_input').val();
		start_day = $('#start_day').val();
		end_day = $('#end_day').val();
		
		var url = window.location.href;
		var arrayOfStrings = url.split("?");
		
		var form = document.createElement("form");
		form.method = "POST";
	    form.action = arrayOfStrings[0];  
		var params = {};
		params.start_day = start_day;
		params.end_day = end_day;
		params.line_id = line_id;
		
		var name = $(this).attr("name");
		if(name == 'excel'){
			params.report = 'excel';
		}
		
		for (var property in params) {
		    if (params.hasOwnProperty(property)) {
		        var value = params[property];
		        var element1 = document.createElement("input"); 
		        element1.value=value;
			    element1.name=property;
			    form.appendChild(element1);  
		    }
		}
	    document.body.appendChild(form);
	    form.style.display = 'none';
	    form.submit();
	});
	
	
		
	$('.datepicker2').datetimepicker({
		format: "HH:mm",
    });
	
	$('.datepicker1').each(function( index ) {
		val = $(this).data("value");
		console.log(val);
		$(this).datetimepicker({
			format: "YYYY-MM-DD",
	    }).val(val);
	});
});

cm_func.updateDialogContent = function(infoData) {
	// return false;
	var classname = infoData.className;
	var dlgid = infoData.dlgid;
	var formid = 'Form' + dlgid;
	var pointer = infoData.pointer;
	var model = $(pointer).parent().data("data");
	if (classname == 'ac_detail') {
		// var model = JSON.parse(data_json);
		// prepare Dialog Variables
		var form_inputs = $('#Form' + dlgid + ' label');
		$.each(form_inputs, function(index, item) {
			var name = $(item).attr("name");
			try {
				if(name!=undefined){
					var arrayOfStrings = name.split(";");
					if(arrayOfStrings.length == 2){
						var part1 = arrayOfStrings[0];
						var part2 = arrayOfStrings[1];
						if(model[part1].hasOwnProperty(part2)){
							var value = model[part1][part2];
							if(value!=undefined){
								$(item).html(value);	
							}
						}
					}else if(arrayOfStrings.length == 1){
						var part1 = '';
						var part2 = arrayOfStrings[0];
						if(model.hasOwnProperty(part2)){
							var value = model[part2];
							if(value!=undefined){
								$(item).html(value);	
							}
						}
					}
				}
				
			}
			catch(err) {
			    console.log(err);
			    if(name!=undefined){
			    	$(item).html('');	
			    }
			}
		});
		
		$('.relation').remove();
		var last_relation = undefined;
		for(var i=0; i<model.list_relation.length; i++){
			var relation = model.list_relation[i];
			console.log(relation);
			var s_name = relation.sender.name;
			var r_name = relation.receiver.name;
			var string1 = s_name + ' 指派工单给 '+r_name;
			var s_time = '';
			if(relation.s_time!=undefined){
				s_time = relation.s_time;	
			}
			var r_time = '';
			if(relation.r_time!=undefined){
				r_time = relation.r_time;	
			}
			if(relation.sender.id == relation.receiver.id){
				string1 = s_name + '接收工单并指派给自己';
			}
			
			var div = '<div class="relation col-md-12">\
				<label class="control-label col-md-8">'+string1+'</label>\
				<label class="control-label col-md-4 pull-right">'+relation.duration+'</label>\
				</div>\
				<div class="relation col-md-12">\
					<label class="control-label col-md-12">'+r_time+'</label>\
				</div>\
				';
			$('#list_relation').append(div);
			last_relation = relation;
		}
		
		if(model.data_complete != undefined){
			var relation = model.data_complete;
			var s_name = relation.sender.name;
			var r_name = relation.receiver.name;
			
			var string1 = r_name + "反馈";
			var div = '<div class="relation col-md-12">\
				<label class="control-label col-md-8">'+string1+'</label>\
				<label class="control-label col-md-4 pull-right">'+relation.complete_min+'</label>\
				</div>\
				<div class="relation col-md-12">\
					<label class="control-label col-md-12">'+model.complete+'</label>\
				</div>\
				';
			$('#list_relation').append(div);
		}
		
		

	}else if (classname == 'ac_modify') {
		// var model = JSON.parse(data_json);
		// prepare Dialog Variables
		var form_inputs = $('#Form' + dlgid + ' input');
		$.each(form_inputs, function(index, item) {
			var name = $(item).attr("name");
			var value = model[name];
			$(item).val(value);
		});

		var form_textarea = $('#Form' + dlgid + ' textarea');
		$.each(form_textarea, function(index, item) {
			var name = $(item).attr("name");
			var value = model[name];
			$(item).val(value);
		});

		var form_select = $('#Form' + dlgid + ' select');
		$.each(form_select, function(index, item) {
			var name = $(item).attr("name");
			var value = model[name];
			$(item).val(value);
		});

		cm_func.formValidators[formid].resetForm();
	} 
	return false;
}