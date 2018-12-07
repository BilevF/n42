
let createContent = $( "#createContent" );
let showContent = $( "#showContent" );
let findContent = $( "#findContent" );

let menuSeparator = $( "#menuSeparator" );

let createBtn = $( "#menu-button-create" );
let showBtn = $( "#menu-button-show" );
let findBtn = $( "#menu-button-find" );

let hiddenClassName = 'hidden';
let activeBtnClassName = 'menu-button-active';
let menuBtnClassName = 'menu-button';

function closeAllContent() {

    if (!createContent.hasClass (hiddenClassName)) {
        createContent.addClass(hiddenClassName);
    }
    if (!showContent.hasClass (hiddenClassName)) {
        showContent.addClass(hiddenClassName);
    }
    if (!findContent.hasClass (hiddenClassName)) {
        findContent.addClass(hiddenClassName);
    }
}

function deactivateButtons() {

    if (createBtn.hasClass (activeBtnClassName)) {
        createBtn.removeClass(activeBtnClassName);
    }
    if (showBtn.hasClass (activeBtnClassName)) {
        showBtn.removeClass(activeBtnClassName);
    }
    if (findBtn.hasClass (activeBtnClassName)) {
        findBtn.removeClass(activeBtnClassName);
    }
}

function onShow() {
    closeAllContent();
    deactivateButtons();
    menuSeparator.removeClass(hiddenClassName);
    showContent.removeClass(hiddenClassName);

    showBtn.addClass(activeBtnClassName);
}

function onCreate() {
    closeAllContent();
    deactivateButtons();
    menuSeparator.removeClass(hiddenClassName);
    createContent.removeClass(hiddenClassName);

    createBtn.addClass(activeBtnClassName);
}

function onFind() {
    closeAllContent();
    deactivateButtons();
    menuSeparator.removeClass(hiddenClassName);
    findContent.removeClass(hiddenClassName);

    findBtn.addClass(activeBtnClassName);
}


// When the user scrolls the page, execute myFunction
window.onresize = function() {onload()};
window.onload = function() {
    onload();


};

// Get the navbar
let navbar = $('#navbar');
let body = $('body');

function onload() {
    body.css("margin-top", navbar.height() + 10);
}



function onSpeach(word) {
    if (word == null || word == "") return;

    try {

        $("#menu-tabs button[name=" + word + "]").trigger("click");

        $("#createContent:not(."+hiddenClassName+") form[name=" + word + "]").find(':submit').trigger("click");

        $("#showContent:not(."+hiddenClassName+") form[name=" + word + "]").find(':submit').trigger("click");

        $("#findContent:not(."+hiddenClassName+") form[name=" + word + "]").find(':submit').trigger("click");

    } catch (e) {
        console.error(e);
    }
}

try {

    let grammar = '#JSGF V1.0; grammar colors; public <color> = find | show | create | user | users | tariff | tariffs;';

    let speechRecognitionList = window.SpeechGrammarList || window.webkitSpeechGrammarList;
    let speechRecognition = new speechRecognitionList();
    speechRecognition.addFromString(grammar, 1);

    let SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    let recognition = new SpeechRecognition();


    recognition.grammars = speechRecognition;

    recognition.interimResults = true;

    recognition.continuous = true;

    recognition.maxAlternatives = 3;

    recognition.start();

    recognition.onresult = function(event) {


        let current = event.resultIndex;

        let res = event.results[current];

        for (let i = 0; i < res.length; i++) {

            let sRes = res[i].transcript.split(" ");

            sRes.forEach(onSpeach);
        }

    };

    // recognition.onspeechend = function() {
    //     recognition.start();
    // }
}
catch(e) {
    console.error(e);
    $('.no-browser-support').show();
    $('.app').hide();
}
