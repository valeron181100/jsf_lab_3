/// <reference path="../typings/globals/jquery/index.d.ts" />

var isSidePanelOpened = false;

var x_coord = 10;
var y_coord = 10;
var r_coord = 10;

$(document).ready(function(){
    if(!navigator.cookieEnabled){
        myalert("Cookie-файлы отключены. Чтобы просматривать историю запросов, \nа также для отображения точек на графике, необходимо включить cookie.",
            5000)
    }
});

$(window).scroll((event)=>{
    const scrolled = window.scrollY;
    var headerHeight = $('#mainHeader').height();
    if(scrolled >= headerHeight){
        $('#header').css({'position' : 'fixed',
            'margin-top': -headerHeight});
        $('#sidePanel').css({'position' : 'fixed',
            'margin-top': -headerHeight});
    }
    else{
        $('#header').css({'position' : 'static',
            'margin-top': 0});
        $('#sidePanel').css({'position' : 'absolute',
            'margin-top': '-4vh'});
    }
});

function historyBtnClick() {
    if($('#historyView').css('display') === 'none'){
        $('#historyView').attr('style', 'display: static;');
    }
    $('#mainView').css('display', 'none');
}

function mainPageBtnClick() {
    if($('#mainView').css('display') === 'none'){
        $('#mainView').attr('style', 'display: static;');
    }
    $('#historyView').css('display', 'none');
}

function mainContentClick() {
    if(isSidePanelOpened){
        document.getElementById("sidePanel").className = "flyInAnimClass";
        isSidePanelOpened = false;
    }
}

function getR() {
    r_coord = $(document.getElementById('coordform:hidden-txt-r')).val();
    return r_coord;
}

function clickSubmitButton() {
    let yInputEl = $(document.getElementById('coordform:y-input'));
    let xInputEl = $(document.getElementById('coordform:hidden-txt-x'));
    let rInputEl = $(document.getElementById('coordform:hidden-txt-r'));
    let submBtn = $(document.getElementById('coordform:submitButton'));

    yInputEl.val(y_coord);
    xInputEl.val(x_coord);
    rInputEl.val(r_coord);

    submBtn.click();
    console.log('submit button was clicked programmaticly');
}

function myalert(text, duration = 2000) {
    var div = document.createElement('div');
    div.classList.add('alertMessage');
    div.innerHTML = text;
    $(div).css('opacity', 0);
    document.body.appendChild(div);

    $(div).animate({
        opacity: 1.0
    }, 1000);

    setTimeout(()=>{
        $(div).animate({
            opacity: 0.0
        }, 1000, 'swing', function () {
            div.remove();
        });
    }, duration);
}

function playButtonClickAnimation() {

    // playButtonAnimation("menuButton");

    if(!isSidePanelOpened){
        document.getElementById("sidePanel").className = "flyOutAnimClass";
        isSidePanelOpened = true;
    }
    else{
        document.getElementById("sidePanel").className = "flyInAnimClass";
        isSidePanelOpened = false;
    }
}

function playButtonAnimation(element){
    element.classList.remove("buttonAnim2Class");
    element.classList.add("buttonAnimClass");

    element.addEventListener('animationend', ()=> {
        element.classList.remove("buttonAnimClass");
        element.classList.add("buttonAnim2Class");
        element.addEventListener('animationend', ()=>{
            element.classList.remove("buttonAnim2Class");
        })
    }, false);
}

function displaySliderValue(fromElementId, toElementId) {
    let fromElement = $(document.getElementById(fromElementId));
    let toElement = $(document.getElementById(toElementId));

    toElement.text(fromElement.val());
}