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
		
	$('#view').on('click', function() {
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
		format: "YYYY-MM-DD hh:mm:ss",
    });
	
	$('.datepicker1').each(function( index ) {
		val = $(this).data("value");
		console.log(val);
		$(this).datetimepicker({
			format: "YYYY-MM-DD",
	    }).val(val);
	});
});