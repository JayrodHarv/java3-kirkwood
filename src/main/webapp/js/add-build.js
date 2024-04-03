// $('#builtByClear').click(function() {
//     $('#builtBy').val('');
// });
//
// $("#builtBySelect").on("change",function(){
//     //Getting Value
//     let selValue = $("#builtBySelect").val();
//     let beans = $("#builtBy").val();
//
//     if(beans !== "" && beans !== undefined) {
//         $("#builtBy").val(beans + ", " + selValue);
//     } else {
//         $("#builtBy").val(selValue);
//     }
// });

var selDiv = "";
var storedFiles = [];
$(document).ready(function () {
    $("#image").on("change", handleFileSelect);
    selDiv = $("#imagePreview");
});

function handleFileSelect(e) {
    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);
    filesArr.forEach(function (f) {
        if (!f.type.match("image.*")) {
            return;
        }
        storedFiles.push(f);

        var reader = new FileReader();
        reader.onload = function (e) {
            var html =
                '<img src="' +
                e.target.result +
                "\" data-file='" +
                f.name +
                "alt='Category Image' class='w-100'>";
            selDiv.html(html);
        };
        reader.readAsDataURL(f);
    });
}


