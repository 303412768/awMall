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

closeModal = function (id) {
    if (id == null || "" == id) {
        id = "page-modal";
    }
    $("#" + id).modal('hide');
    $('#modal-content').html("");
};


queryPage = function (divId, url, query) {
    $.get(url, query, function (result) {
        var current = result.current;
        var total = 6;
        var size = result.size;
        var pages = total % size > 0 ? total / size + 1 : total / size;
        console.log("current:" + current);
        console.log("total:" + total);
        console.log("size:" + size);
        console.log("pages:" + pages);
        $("#" + divId).page({
            pages: pages,
            curr: current,
            groups: 5

        });
        $("#" + divId).on('jump.page.amui', function (context) {
            console.log('点击分页按钮时会触发jump.page.amui事件');
            console.log(context);
        });

    }, "json");
};

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



$.fn.setForm = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (key, value) {
        //input 类型自动回填
        var input = obj.find("input[name=" + key + "]");
        switch (input[0].type) {
            case "text" :
            case "number" :
                input.val(value);
                break;
        }
        //select 类型自动回填
       obj.find("select[name=" + key + "]").val(value);

    });
};





