var objId;
loadPage = function (url) {
    $("#main_content").load(url)
};

loadPageByModal = function (url, width, height) {
    $.get(url, function (result) {
        if (null != width) {
            $('#modal-content').css("width", width);
        }
        if (null != height) {
            $('#modal-content').css("height", height);
        }
        $('#modal-content').html(result);
        $('#page-modal').modal('show');
    });
};

updateLoadPageByModal = function (url, uuid, width, height) {
    loadPageByModal(url, width, height);
    objId = uuid;
};

closeModal = function (id) {
    if (id == null || "" == id) {
        id = "page-modal";
    }
    $("#" + id).modal('hide');
    $('#modal-content').html("");
};


/**
 * 表单提交后动态刷新Table
 * @param formId
 * @param tableId
 */
submitAndRefreshTable = function (formId, tableId) {
    var bootstrapValidators = $('#' + formId).data('bootstrapValidator').validate();
    console.log(bootstrapValidators.isValid());
    if (bootstrapValidators.isValid()) {
        $('#' + formId).ajaxSubmit(function (result) {
            if (result.code === 200) {
                $('#' + tableId).bootstrapTable('refresh');
                closeModal("page-modal");
            } else {
                alert(result.msg);
            }
        });
    }

};

deleteRows = function (tableId, url, uuid) {
    var ids = "";
    if (uuid == null || "" == uuid) {
        var rows = $("#" + tableId).bootstrapTable('getSelections');
        if (rows == null || rows == "") {
            alert("请选择数据！");
            return;
        }
        $.each(rows, function (index, obj) {
            ids += obj.uuid + ","
        });
    } else {
        ids = uuid;
    }
    $.post(url + "/" + ids, function (result) {
        if (result.code === 200) {
            $('#' + tableId).bootstrapTable('refresh');
        } else {
            alert(result.msg);
        }
    }, "json");


};


/**
 * 表单自动回填
 * @param jsonValue
 */
$.fn.setForm = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (key, value) {
        //input 类型自动回填
        var input = obj.find("input[name=" + key + "]");
        //select 类型自动回填
        obj.find("select[name=" + key + "]").val(value);
        if (input.length === 1) {
            switch (input[0].type) {
                case "text" :
                case "number" :
                    input.val(value);
                    break;
            }
        }

        var textarea = obj.find("textarea[name=" + key + "]");
        if (null != textarea && textarea.length === 1) {
            textarea.val(value);
        }

    });
};

initSelectInfo = function (selectId, category, selectedValue) {
    var url = "/api/1.0/baseCode/" + category;
    $.get(url, function (result) {
        $.each(result, function (index, obj) {
            $("#" + selectId).append("<option value='" + obj.code + "'>" + obj.name + "</option>");
        });
        if (null != selectedValue) {
            $("#" + selectId).val(selectedValue);
        }
    }, "json");
};


$.ajaxSetup({
    //设置ajax请求结束后的执行动作
    complete: function (XMLHttpRequest, textStatus) {
        if (XMLHttpRequest.status == 403) {
            window.location.href = './login.html';
        }
    }
});

$(function () {
    getUserInfo();
});

function getUserInfo() {
    var url = "/api/1.0/user/currentUser";
    $.get(url, function (result) {
        if (result.code === 200 && result.data !== null) {
            if (result.data.role !== "admin") {
                location.href = "/index.html";
            }
        } else {
            location.href = "/index.html";
        }
    }, "json");
}








