$('#builtByClear').click(function() {
    $('#builtBy').val('');
});

$("#builtBySelect").on("change",function(){
    //Getting Value
    let selValue = $("#builtBySelect").val();
    let beans = $("#builtBy").val();

    if(beans !== "" && beans !== undefined) {
        $("#builtBy").val(beans + ", " + selValue);
    } else {
        $("#builtBy").val(selValue);
    }
});

function filter(keyword) {
    let select = $('#builtBySelect');
    let optionCollection = Array.from(select.options).filter(x => x.text.toLowerCase().startsWith(keyword.toLowerCase()))
}

