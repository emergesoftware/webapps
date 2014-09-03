
/**
 * Validates the browse available
 * routes form
 * 
 * @returns {Boolean}
 */
function filterAvailableBusRoutesList(textBox) {
    
    if (textBox == null)
        return;
    
    var content = textBox.value.trim();
    
    // the class name for the list items
    var className = "list-group-item";
    // the list group elements
    var availableBusRoutesListGroup = 
            document.getElementById("availableBusRoutesListGroup");
    
    // exit the function if the element
    // does not exist
    if (availableBusRoutesListGroup == null)
        return;
    
    // get all the nodes with this class name
    var childNodes = document.getElementsByClassName(className);
    
    //iterate through each - if you find one
    // that contains the text - show it, else
    // hide it
    for (var i = 0; i < childNodes.length; i++) {
        
        var item = childNodes[i];
        
        if (item.innerHTML.indexOf(content.toUpperCase()) >= 0) {
            $(item).css({ display : 'block' });
        }

        else {
            $(item).css({ display : 'none' });
        }
             
    }
    
    // scroll to focus on this form
    // after the filtering
    var browseRoutesForm = document.getElementById("browseRoutesForm");
    if (browseRoutesForm == null)
        return;
    
    $('html, body').animate({
        scrollTop: $(browseRoutesForm).offset().top
    }, 500);
    
}

/**
* Validates the form for finding the
* time tables by route number
* @returns {Boolean}
*/
function checkBusRouteNumberForm() {
    
    // element that carries the value of the route number
    var routeNumber = document.getElementById("routeNumber");
    // element that carries the error message 
    var routeNumberError = document.getElementById("routeNumberError");
    // the DIV that carries the input form group
    var routeNumberFormGroup = document.getElementById("routeNumberFormGroup");
   
   // exit if either of these
   // controls are not found in the
   // HTML document
    if (routeNumber == null || 
            routeNumberError == null || 
            routeNumberFormGroup == null)
        return false;
   
    // prevent form submission if the 
    // user did not enter any route number
    if (routeNumber.value == null || routeNumber.value.length == 0) {
       $(routeNumberError).css({ display : 'block' });
       $(routeNumberFormGroup).addClass('has-error');
       
       return false;
    }

    else {
       $(routeNumberError).css({ display : 'none' });
       $(routeNumberFormGroup).removeClass('has-error');
       return true;
    }
}


