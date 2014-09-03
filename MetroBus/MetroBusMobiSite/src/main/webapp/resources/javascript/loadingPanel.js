function toggleLoadingPanel(show) {

    // get the loading panel element
    var panel = document.getElementById("loadingPanel");
    if (panel == null)
        return;

    // get the left offset for the slide-nav element
    var leftOffset = $('html').offset().left;

    // assign the left offset to the panel
    if (leftOffset != 0) {
        $(panel).css({ left : leftOffset});
    }

    if (show) {
        $(panel).css({ display : 'block' });
    }

    else {
        $(panel).css({ display : 'none' });
    }

}