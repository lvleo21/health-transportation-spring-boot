$('input[id*="switch_"]').click(function () {
    url = "/" + $(this).attr('id').split("_")[1];
    document.location.href = url;
})