var appAvailability = {
    
    check: function(urlScheme, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            "AppAvailability",
            "checkAvailability",
            [urlScheme]
        );
    },
    
    checkBool: function(urlScheme, callback) {
        cordova.exec(
            function(success) { callback(success); },
            function(error) { callback(error); },
            "AppAvailability",
            "checkAvailability",
            [urlScheme]
        );
    },

    info: function(packageName, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            "AppAvailability",
            "getInfo",
            [packageName]
        );
    }
    
};

module.exports = appAvailability;