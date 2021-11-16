$(document).ready(function(){
    initSDK('Bot');
});
var chatSettings = {
    enableTimestamp: true,
    showConnectionStatus: true,
    conversationBeginPosition: 'top',
    position: {bottom: '30px', right: '5px'},
    displayActionAsPills: true,
    enableAutocomplete: true,                   // Enables autocomplete suggestions on user input
    //enableBotAudioResponse: true,               // Enables audio utterance of skill responses
    enableClearMessage: true,                   // Enables display of button to clear conversation
    showConnectionStatus: true,
    i18n: {                                     // Provide translations for the strings used in the widget
              en: {                                   // en locale, can be configured for any locale
                     chatTitle: 'BPM Support'    // Set title at chat header
              }
    },
    timestampMode: 'relative',                  // Sets the timestamp mode, relative to current time or default (absolute)
    theme: WebSDK.REDWOOD_DARK,
    URI: 'oda-c1d4603417684749b723021cb229597c-d0iad.data.digitalassistant.oci.oc-test.com',
    channelId: '7978b257-89fe-4f14-9a1e-b39a54f105dc'
    //'dbdfa481-a6cb-45c9-8053-c9ffa3ad1c2b'
    //7cfc6e0f-726f-4662-90d2-d10501c806c0
    //978b257-89fe-4f14-9a1e-b39a54f105dc
    };

function initSDK(name) {
// If WebSDK is not available, reattempt later
if (!document || !WebSDK) {
    setTimeout(function() {
        initSDK(name);
    }, 2000);
    return;
}

// Default name is Bots
if (!name) {
    name = 'Bots';
}

setTimeout(function() {
    var Bots = new WebSDK(chatSettings);    // Initiate library with configuration
    var isFirstConnection = true;
    Bots.on(WebSDK.EVENT.WIDGET_OPENED, function() {
        if ($('input[type="file"]')[0]){
            $('input[type="file"]').hide();
        }

        if (isFirstConnection) {
            Bots.connect()                          // Connect to server
                .then(function() {
                    console.log('Connection Successful');
                })
                .catch(function(reason) {
                    console.log('Connection failed');
                    console.log(reason);
                });
               isFirstConnection = false;
        }
    });

    window[name] = Bots;

}, 0);
}

