
let createContent = $( "#createContent" );
let showContent = $( "#showContent" );;
let selectContent = $( "#selectContent" );

let menuSeparator = $( "#menuSeparator" );

let createBtn = $( "#menu-button-create" );
let showBtn = $( "#menu-button-show" );
let selectBtn = $( "#menu-button-select" );
let blockBtn = $( "#menu-button-block" );

let hiddenClassName = 'hidden';
let activeBtnClassName = 'menu-button-active';
let menuBtnClassName = 'menu-button';

function init() {
    createContent = $( "#createContent" );
    showContent = $( "#showContent" );
    selectContent = $( "#selectContent" );

    menuSeparator = $( "#menuSeparator" );

    createBtn = $( "#menu-button-create" );
    showBtn = $( "#menu-button-show" );
    selectBtn = $( "#menu-button-select" );
    blockBtn = $( "#menu-button-block" );
}

function closeAllContent() {
    init();

    if (createContent != null && !createContent.hasClass (hiddenClassName)) {
        createContent.addClass(hiddenClassName);
    }
    if (showContent != null && !showContent.hasClass (hiddenClassName)) {
        showContent.addClass(hiddenClassName);
    }

    if (selectContent != null && !selectContent.hasClass (hiddenClassName)) {
        selectContent.addClass(hiddenClassName);
    }
}

function deactivateButtons() {
    init();

    if (createBtn != null && createBtn.hasClass (activeBtnClassName)) {
        createBtn.removeClass(activeBtnClassName);
    }
    if (showBtn != null && showBtn.hasClass (activeBtnClassName)) {
        showBtn.removeClass(activeBtnClassName);
    }

    if (selectBtn != null && selectBtn.hasClass (activeBtnClassName)) {
        selectBtn.removeClass(activeBtnClassName);
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



function onSelect() {
    closeAllContent();
    deactivateButtons();
    menuSeparator.removeClass(hiddenClassName);
    selectContent.removeClass(hiddenClassName);

    selectBtn.addClass(activeBtnClassName);
}


// When the user scrolls the page, execute myFunction
window.onresize = function() {onLoad()};
window.onload = function() {
    onLoad();


};

// Get the navbar
let navbar = $('#navbar');
let body = $('body');

function onLoad() {
    body.css("margin-top", navbar.height() + 10);
}



function onSpeach(word) {
    word = getCommand(word);
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

const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
const SpeechGrammarList = window.SpeechGrammarList || window.webkitSpeechGrammarList;
const SpeechRecognitionEvent = window.SpeechRecognitionEvent || window.webkitSpeechRecognitionEvent;

const commands = {
    найди: 'find',
    найти: 'find',
    покажи: 'show',
    отобрази: 'show',
    создай: 'create',
    создать: 'create',
    пользователь: 'user',
    пользователя: 'user',
    клиент: 'user',
    клиента: 'user',
    пользователи: 'users',
    пользователей: 'users',
    клиенты: 'users',
    клиентов: 'users',
    тариф: 'tariff',
    тарифы: 'tariffs'

};

const commandList = Object.keys(commands);

function getCommand(speechResult) {
    if (speechResult in commands) {
        return commands[speechResult];
    }
    return null;
}

try {

    let grammar1 = '#JSGF V1.0; grammar colors; public <color> = find | show | create | user | users | tariff | tariffs;';

    const grammar = '#JSGF V1.0; grammar commands; public <command> = ' + commandList.join(' | ') + ' ;';

    let speechRecognition = new SpeechGrammarList();
    speechRecognition.addFromString(grammar, 1);

    let recognition = new SpeechRecognition();

    recognition.grammars = speechRecognition;

    // recognition.addFromString(grammar, 1);

    recognition.interimResults = true;

    recognition.continuous = true;

    recognition.lang = 'ru-RU';

    recognition.maxAlternatives = 1;

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
