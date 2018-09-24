loadPage = function (url) {
    $("#main_content").load(url)
};

loadPageByModal = function (url,width,height) {
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