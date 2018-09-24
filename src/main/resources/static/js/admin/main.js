loadPage = function (url) {
    $("#main_content").load(url)
};

loadPageByModal = function (url, width, height) {
    $.get(url, function (result) {
        if (null != width) {
            $('#page-modal').css("width", width);
        }
        if (null != height) {
            $('#page-modal').css("height", height);
        }
        $('#page-modal').html(result);
        $('#page-modal').modal('open');
    });
};

closeModal = function (id) {
    if (id == null || "" == id) {
        id = "page-modal";
    }
    $("#" + id).modal('close');
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
        $("#"+divId).on('jump.page.amui', function(context) {
            console.log('点击分页按钮时会触发jump.page.amui事件');
            console.log(context);
        });

    }, "json");
};