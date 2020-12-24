//form데이터 전송
function dataSubmit() {
    var data=new FormData($("#storeAddForm")[0]);

    $.ajax({
        url: "url",
        data: data,
        processData:false,
        contentType:false,
        enctype:'multipart/form-data',
        type:"POST",
    }).done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
    });
}
