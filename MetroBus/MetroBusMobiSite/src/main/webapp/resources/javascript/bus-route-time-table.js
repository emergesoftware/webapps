
var timer = null;

/**
 * Initialisation
 * 
 * @returns {undefined}
 */
function initialise() {
    $(document).ready(function(){

        // start the minutes left counter
        var secondsBeforeNextDeparture = document.getElementById("secondsBeforeNextDeparture");
        var startFrom = secondsBeforeNextDeparture.value;
        
        // if there is no next departure info
        // then exit this function
        if (startFrom < 0) {
            return;
        }
        
        var minutesLeftCounter = document.getElementById("minutesLeftCounter");
        var nextDepartureAlert = document.getElementById("nextDepartureAlert");
        
        if (minutesLeftCounter == null || 
                nextDepartureAlert == null)
            return;
        
        var hoursLeft = 0, 
            minutesLeft = 0, 
            secondsLeft = 0;
            
        // start the timer
        timer = setInterval(function(){
            
            // decrement the seconds
            startFrom--;
            
            // determine the hours, minutes and seconds
            var hoursLeft = Math.floor(startFrom / 3600);
            var minutesLeft = Math.floor((startFrom % 3600) / 60);
            var secondsLeft = startFrom % 60;
            
            // format for display
            var display = "";
            
            if (hoursLeft == 0)
                display = minutesLeft + " min " + secondsLeft + " sec";
            
            else
                display = hoursLeft + " hr " + minutesLeft + " min " 
                    + secondsLeft + " sec";
            
            // set the html content
            $(minutesLeftCounter).html("<b>" + display + "</b>");
            
            // stop the counter once the
            // seconds are on zero
            if (startFrom == 0) {
                clearInterval(timer);
                window.location.reload();
            }
            
        }, 1000);
    });
}

/**
 * Changes the current service type 
 * and re-submits the form
 * 
 * @param {type} value
 * @returns {undefined}
 */
function changeServiceType(value) {
    
    if (value == null || value.length == 0)
        return;
    
    var serviceType = document.getElementById("serviceType");
    if (serviceType == null)
        return;
    
    serviceType.value = value.trim();
    
    var timesTableForm = document.getElementById("timesTableForm");
    if (timesTableForm != null)
        timesTableForm.submit();
    
}

/**
 * Opens a link to display the 
 * route description
 * 
 * @param {type} routeNumber
 * @returns {undefined}
 */
function openRouteDescription(routeNumber) {
    
    if (routeNumber == null || routeNumber.length == 0)
        return;
    window.location = "/metroBusApp/route/describe/" + routeNumber;    
}

/**
 * Opens the bus stops
 * contained in the route
 * 
 * @param {type} routeNumber
 * @returns {undefined}
 */
function openRouteBusStops(routeNumber) {
    if (routeNumber == null || routeNumber.length == 0)
        return;
    window.location = "/metroBusApp/route/bus-stops/" + routeNumber; 
}

/**
 * Opens the areas covered by this
 * route
 * 
 * @param {type} routeNumber
 * @returns {undefined}
 */
function openRouteAreaCoverage(routeNumber) {
    if (routeNumber == null || routeNumber.length == 0)
        return;
    window.location = "/metroBusApp/route/area-coverage/" + routeNumber; 
}


