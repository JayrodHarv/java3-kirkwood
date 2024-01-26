$(document).ready(function() {

    NProgress.configure({ showSpinner: false, trickle: false });
    $('a[href]').click(showBar);
    $('form').submit(showBar);
    let loadingBar = localStorage.getItem("loadingBar");
    loadingBar = loadingBar != null && loadingBar < 1 ? parseFloat(loadingBar) : 0;
    localStorage.setItem("loadingBar", loadingBar);
    let progressInterval;

    function finishBar() {
        NProgress.set(loadingBar);
        setTimeout(function() {
            NProgress.done();
            localStorage.setItem("loadingBar", 1);
        }, 1000);
    }

    function showBar() {
        NProgress.start();
        // clearInterval(progressInterval);
        let count = 0;
        progressInterval = setInterval(function () {
            let inc = 0.005 * ++count;
            if(inc > 0.03) {
                inc = 0.03;
            }
            if(loadingBar < 0.90) {
                NProgress.inc(0.05 - inc);
                loadingBar += 0.05 - inc;
            } else {
                NProgress.inc(0.002);
                loadingBar += 0.002;
            }
            localStorage.setItem("loadingBar", loadingBar);
        }, 1000);
    }

    $(window).on('beforeunload', function() {
        NProgress.done();
        localStorage.setItem("loadingBar", 1);
        clearInterval(progressInterval);
    });
});