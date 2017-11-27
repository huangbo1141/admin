var O = function(someValue) {
	this.formValidators = {};
	this.basepath = "/"+cat.rootdir + "/"+cat.term + "/"+cat.subterm + "/";
	this.cat = cat;
}
var cm_func = new O("chris");
O.prototype.getInfoData = function(className, pointer) {
	var infoData = {};
	infoData.fnName = 'showDialog';
	infoData.className = className;
	infoData.pointer = pointer;
	infoData.cancelFn = cm_func.cancelFn;
	infoData.okFn = cm_func.okFn;
	infoData.ajaxsuccess = 'ajaxsuccess';
	infoData.ajaxfail = 'ajaxfail';
	infoData.fnUnbind = 'fnUnbind';
	infoData.basepath = cm_func.basepath;
	// may get id from pointer
	switch (className) {
	case "ac_new":
		infoData.dlgid = "NewModal";
		break;
	case "ac_modify":
		infoData.dlgid = "EditModal";
		break;
	case "ac_delete":
		infoData.dlgid = "DeleteModal";
		break;
	default:
		break;
	}
	return infoData;
}
O.prototype.updateDialogContent = function(infoData) {
	//return false;
	var classname = infoData.className;
	var dlgid = infoData.dlgid;
	var formid = 'Form' + dlgid;
	var pointer = infoData.pointer;
	var model = $(pointer).parent().data("data");
	if(classname == 'ac_new'){
		return false;
		var form_inputs = $('#Form' + dlgid + ' input');
		$.each(form_inputs, function(index, item) {
			if(!$(item).hasClass("non_clear")){
				$(item).val("");	
			}
			
		});
		var form_textarea = $('#Form' + dlgid + ' textarea');
		$.each(form_textarea, function(index, item) {
			if(!$(item).hasClass("non_clear")){
				$(item).val("");	
			}
		});

		var form_select = $('#Form' + dlgid + ' select');
		$.each(form_select, function(index, item) {
			if(!$(item).hasClass("non_clear")){
				$(item).val($(item).find('option:first').val());	
			}
			
		});
		cm_func.formValidators[formid].resetForm();
	}else if(classname == 'ac_modify'){
		//var model = JSON.parse(data_json);
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
	}else if(classname == 'ac_delete'){
		var form_inputs = $('#Form' + dlgid + ' input');
		$.each(form_inputs, function(index, item) {
			var name = $(item).attr("name");
			var value = model[name];
			$(item).val(value);
		});
	}
	return false;
}
O.prototype.showDialog = function(event) {
	var infoData = event.data;
	var dlgid = infoData.dlgid;
	var okfn = infoData.okFn;
	var cancelfn = infoData.cancelFn;
	var classname = infoData.className;

	var okbtn = $("#" + dlgid + " .okbtn")[0];
	var cancelbtn = $("#" + dlgid + " .cancel")[0];

	$(okbtn).bind("click", infoData, okfn);
	$(cancelbtn).bind("click", infoData, cancelfn);

	
	cm_func.updateDialogContent(infoData);
	$("#" + dlgid).modal('show');
}
O.prototype.okFn = function(event) {
	if (event != undefined) {
		var infoData = event.data;
		var dlgid = infoData.dlgid;
		var proc1 = infoData.ajaxsuccess;
		var proc2 = infoData.ajaxfail;
		var fnUnbind = infoData.fnUnbind;
		var params = {};
		var url = cm_func.basepath + infoData.className;

		var form_datas = $('#Form' + dlgid).serializeArray();
		var params = {};
		params.classname = infoData.className;
		var obj1 = {};
		var index = 0;
		$.each(form_datas, function(ith, value) {
			v = value.value;
			n = value.name;
			obj1[n] = v;
		});
		params.model = obj1;

		var valid = $('#Form' + dlgid).valid();
		
		if (valid) {
			$('.loading').show();
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				data : JSON.stringify(params),
				contentType : "application/json; charset=utf-8",
				success : function(resp, status, xhr) {
					if (resp.response == 200) {
						// success
						// refresh current page
						location.reload();

					} else {
						// error
						alert(resp.error_message);
					}
					$('.loading').hide();
				},
				error : function(request, status, error) {
					alert("error ajax   " + error);
					$('.loading').hide();
				}
			});
			cm_func[fnUnbind](event);
		} else {
			// 
		}

		return false;
	}
}
O.prototype.cancelFn = function(event) {
	var infoData = event.data;
	var fnUnbind = infoData.fnUnbind;
	cm_func[fnUnbind](event);
}
O.prototype.fnUnbind = function(event) {
	if (event != undefined) {
		var infoData = event.data;
		var dlgid = infoData.dlgid;
		var okbtn = $("#" + dlgid + " .okbtn")[0];
		var cancelbtn = $("#" + dlgid + " .cancel")[0];
		$(okbtn).unbind("click");
		$(cancelbtn).unbind("click");

		$("#" + dlgid).modal("hide");
		return false;
	}
}

function checkPermission(classes) {

	var arrayOfStrings = classes.split(" ");
	for (var i = 0; i < arrayOfStrings.length; i++) {
		className = arrayOfStrings[i];
		if (permission.includes(className)) {
			return className;
		}
	}
	if (permission.includes('ac_all')) {
		return "ac_all";
	}
	return null;
}
function K11(event) { // K11
	var classes = $(this).attr('class');
	className = checkPermission(classes);
	if (className == null) {
		// no permission
		// ("#customtable_dialog_mquanxian").modal('show');
		alert("You have no permission for this");
	} else {
		// permission has
		// var rowid = $(this).data("quanxian");
		var pointer = $(this);
		var infoData = cm_func.getInfoData(className, pointer);

		var strFun = infoData.fnName;

		event.data = infoData;
		cm_func[strFun](event)
	}
	return false;
}
$(".ac_new,.ac_modify,.ac_delete,.ac_auth,.ac_enable,.ac_disable").click(K11); // AA1

jQuery(document).ready(function() {
	// cache DOM elements
	// bind new event.
//	$(".collapse").on('hidden.bs.collapse', function(e) {
//		
//		index = $(".collapse").index($(this));
//		alert(index);
//		if(cm_func.cat.listMenu[index].submenu.length == 0){
//			// need to redirect
//			menu = cm_func.cat.listMenu[index];
//			location.href = cm_func.cat.rootdir+"/"+menu.term+"/"+menu.term;
//		}
//	})
	$(".leftmenu").on('click', function(e) {
		
		index = $(".leftmenu").index($(this));
		if(cm_func.cat.listMenu[index].submenu.length == 0){
			// need to redirect
			var menu = cm_func.cat.listMenu[index].menu;
			var url = "/" + cm_func.cat.rootdir+"/"+menu.term+"/"+menu.term;
			location.href = url;
		}
	})
	
	var validator = $("#FormDeleteModal").validate({
		rules: {
			id: "required"		
		},
		messages: {
			id: "Invalid ID"
		}
	});
	cm_func.formValidators.FormDeleteModal = validator;
	
});