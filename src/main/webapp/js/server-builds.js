function filter(keyword) {
    let select = $('#builtBySelect');
    let optionCollection = Array.from(select.options).filter(x => x.text.toLowerCase().startsWith(keyword.toLowerCase()))
}