loadPage = function (url) {
    $("#main_content").load(url)
};

loadPageByModal = function (url) {
    $.get(url, function (result) {
        $('#your-modal').html(result);
        $('#your-modal').modal('open');
    });
};