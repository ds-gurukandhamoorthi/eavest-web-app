
/**
 *
 * Eavest v1.0.0
 *
 * @name Eavest JS
 * @author mbe - web agency - https://mbe.ro
 *
 */
var app = {

ready: function() {

//init all
app.init.all();

},

scroll: function() {

var
homeTop = 0,
paddingTop = 50,
_body = jQuery('body'),
_window = jQuery(window),
_topHeader = jQuery('.top-header'),
_headerH1 = jQuery('header h1')
headerHeight = _headerH1.height() + parseInt(_headerH1.css('paddingTop'), 10) + paddingTop;

if (_body.hasClass('home')) {
homeTop = 0;
}
if (jQuery('.home').length > 0 && jQuery('.slider').length > 0) {
var sliderHeight = jQuery('.slider:not(.jquery)').height();
jQuery('.slider.jquery').hide();
if (_window.scrollTop() > sliderHeight-58) {
jQuery('.slider.jquery').show();
} else {
jQuery('.slider.jquery').hide();
}
};

if (_window.scrollTop() > homeTop && _window.width() > 500) {
jQuery('a.footer-arrow').fadeIn('fast');

// if (jQuery('.top-header-absolute').size() <= 0) {

// jQuery('.top-header').css('visibility', 'hidden');


// var _clone = _topHeader;

// _clone
// .addClass('top-header-absolute')
// .removeClass('top-header')
// .css({
// 'top': 0,
// 'visibility': 'visible'
// })
// .prependTo('body');

// // if (jQuery('.rev_slider_wrapper', _clone).size() > 0) {
// // jQuery('.rev_slider_wrapper', _clone).remove();
// // jQuery('.slider', _clone).html('&nbsp;');
// // }
// } else {
// jQuery('.top-header-absolute').show();
// }
} else {

// jQuery('.top-header-absolute')
// .removeClass('top-header-absolute')
// .addClass('top-header')
// // .appendTo('.f-top-header');

// // _topHeader.css('visibility', 'visible');
jQuery('a.footer-arrow').fadeOut('fast');

// // jQuery('.top-header-absolute').hide();
}


app.init.footerArrow.movement();
},

resize: function() {

//footer
app.init.footer();
},

load: function() {
jQuery("a[href='#']").click(function(){
return false;
});
//on load
app.init.warningScroll();

jQuery('.content-main section.subpage img, .content-main .description img')
.removeAttr('width')
.removeAttr('height');
jQuery('.onlyeav').click(function(){
jQuery(this).toggleClass('on');
if(jQuery(this).hasClass('on')){
jQuery('.content-main section.track-records ul li:not(.hided)').not('.prodeav').fadeToggle();
}else{
jQuery('.content-main section.track-records ul li:not(.hided)').not('.prodeav').fadeToggle();
}
});
jQuery('.sml_thankyou').click(function(){
jQuery(this).fadeOut('slow');
});
jQuery('.menumob').click(function(){
jQuery(this).toggleClass('on');
jQuery('.categomenu').slideToggle();
});
jQuery('.prepend').click(function(){
jQuery('.formy').slideToggle();
});
jQuery(".sml_submitbtn").attr("disabled", true);
jQuery( ".sml_emailinput" ).on( "change keyup paste", function() {
var emil = jQuery( ".sml_emailinput" ).val();
if( !validateEmail(emil) || emil == '') {
jQuery(".sml_submitbtn").attr("disabled", true);
}else{
jQuery(".sml_submitbtn").attr("disabled", false);
}
});
setTimeout(function(){
jQuery('.sml_thankyou').fadeOut('slow');
},2500);
app.init.footerArrow.movement();
},

init: {

all: function () {

//secondary navigation
app.init.secondaryNav.ready();

//footer
app.init.footer();

//knob
app.init.knob();

//slider
app.init.slider();

//warning
app.init.warning();

//footer arrow
app.init.footerArrow.ready();


// filters
app.init.filters();

if (jQuery('.home').length > 0 && jQuery('.slider').length > 0) {
var sliderHeight = jQuery('.slider:not(.jquery)').height();
jQuery('.slider.jquery').hide();
if (_window.scrollTop() > sliderHeight-58) {
jQuery('.slider.jquery').show();
} else {
jQuery('.slider.jquery').hide();
}
};
},
secondaryNav: {

ready: function() {

//run the function
app.init.secondaryNav.hash();

//add the event
jQuery(window).on('hashchange', app.init.secondaryNav.hash);

//on click
jQuery('nav.secondary a').on('click', function (e) {
e.preventDefault();

var id = jQuery(this)[0].hash;

extra.scrollTo(id);
window.location.hash = id;
});
},

hash: function(e) {
var _element = jQuery(window.location.hash);

if (_element.size() > 0) {
//e.preventDefault();
extra.scrollTo(_element);
return false;
}
}
},

filters: function() {

var _doFilter = function() {

speed = 'normal';
jQuery('section.track-records ul li').each(function() {

var
_this = jQuery(this),
show = true;

// if ((_this.data('bank').length > 0) && ($('.filter-1 input').val().length > 0)) {
if (!(_this.data('bank').toLowerCase().indexOf($('#filter_1').val().toLowerCase()) > -1)) {
show = false;
}
// }
// if ((_this.data('code_isin').length > 0) && ($('.filter-2 input').val().length > 0)) {
if (!(_this.data('code_isin').toLowerCase().indexOf($('#filter_2').val().toLowerCase()) > -1)) {
show = false;
}
// }
// if ((_this.data('name').length > 0) && ($('.filter-3 input').val().length > 0)) {
if (!(_this.data('name').toLowerCase().indexOf($('#filter_3').val().toLowerCase()) > -1)) {
show = false;
}
// }
// if ((_this.data('name').length > 0) && ($('.filter-3 input').val().length > 0)) {
if (!(_this.data('base').toLowerCase().indexOf($('#filter_4').val().toLowerCase()) > -1)) {
show = false;
}
// }
show ? _this.show(speed, function() {
_this.removeClass('hided');
if(jQuery('section.track-records ul li:visible').length > 0) {
$('.wrapper .center').hide();
} else {
$('.wrapper .center').show();
}
}) : _this.hide(speed, function() {
_this.addClass('hided');
if(jQuery('section.track-records ul li:visible').length > 0) {
$('.wrapper .center').hide();
} else {
$('.wrapper .center').show();
}
});
});


}
// $('.quarter input.text').on('change', function() {
// _doFilter();
// });
// $('.quarter input.text').on('blur', function() {
// _doFilter();
// });
jQuery('.filters input.button').on('click', function(e) {
e.preventDefault();
_doFilter();
});
// set the banks object
var banks = [];

jQuery('input.banks').each(function() {
banks.push({
'data': jQuery(this).val(),
'value': jQuery(this).val()
});
});

jQuery('.filter-1 input').autocomplete({
lookup: banks,
// onSelect: _doFilter()
});

// set the isin codes object
var code_isin = [];

jQuery('input.codes').each(function() {
code_isin.push({
'data': jQuery(this).val(),
'value': jQuery(this).val()
});
});

jQuery('.filter-2 input').autocomplete({
lookup: code_isin
});

// set the product names object
var product_name = [];

jQuery('input.products').each(function() {
product_name.push({
'data': jQuery(this).val(),
'value': jQuery(this).val()
});
});

jQuery('.filter-3 input').autocomplete({
lookup: product_name
});

// set the product bases object
var bases = [];

jQuery('input.bases').each(function() {
bases.push({
'data': jQuery(this).val(),
'value': jQuery(this).val()
});
});

jQuery('.filter-4 input').autocomplete({
lookup: bases
});
},
knob: function() {
jQuery('input.knob').each(function() {
var
_this = jQuery(this),
offset = -_this.val()/100 * 180 + 90;



_this.knob({
angleOffset: offset,
readOnly: true,
height: 30,
width: 30,
thickness: 0.4,
fontSize: '9px',
bgColor: '#ffffff',
fgColor: '#231f20',
lineCap: 'round'
});
});
},
slider: function() {

jQuery('.slider-amount').slider({
value: 6,
min: 1,
max: 11,
step: 1,
slide: function (event, ui) {
var
i = 0,
speed = 'normal',
value = ui.value - 6;

jQuery('section.track-records ul li').each(function() {

// var
// _this = jQuery(this),
// show = false;

// if (value == 0) {
// show = true;
// } else {
// var
// realValue = 6-Math.abs(value),
// capitalProtection = parseInt(_this.data('capital_protection'), 10),
// performance = parseInt(_this.data('performance'), 10);

// if (value < 0) {
// //capital protection

// if (capitalProtection == value*-1) {
// show = true;
// }
// } else {
// //performance
// if (performance == value) {
// show = true;
// }
// }
// }

// show ? _this.show(speed) : _this.hide(speed);
});
}
});
},
footer: function() {
jQuery('footer.main').removeClass('absolute');

if (jQuery('body').height() < jQuery(window).height()) {
jQuery('footer.main').addClass('absolute');
jQuery('section.track-records').css({
'margin-bottom': 180
});
}
},
footerArrow: {
ready: function() {
jQuery('a.footer-arrow').off('click').on('click', function (e) {
e.preventDefault();

extra.scrollTo('body');
});
},
movement: function() {
var
_window = jQuery(window),
_footer = jQuery('footer.main'),
footerOffset = _footer.offset(),
bottomPadding = 20;

if (_window.scrollTop() + _window.height() > footerOffset.top) {
jQuery('a.footer-arrow').css({
bottom: _window.scrollTop() + _window.height() - footerOffset.top + bottomPadding
});
} else {
jQuery('a.footer-arrow').css({
bottom: '20px'
});
}
}
},
warning: function() {

if (jQuery('.terms-page-wrapper').size() > 0) {
jQuery('.terms-page-wrapper a.agree').on('click', function (e) {
e.preventDefault();

jQuery(this).closest('.terms-page-wrapper').fadeOut('fast', function() {
app.init.footer();
});

extra.createShortCookie('eavest_warning_hidden_2', true, 3);
});

if (!extra.readCookie('eavest_warning_hidden_2')) {
jQuery('.terms-page-wrapper').fadeIn('fast', function() {
app.init.footer();
});
}
}
},
warningScroll: function() {

if (jQuery('.terms-page-wrapper').size() > 0) {
jQuery('.terms-page-wrapper .terms-content').mCustomScrollbar({
horizontalScroll: false,
scrollInertia: 100
});
}
}
}
};

function validateEmail($email) {
var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
return emailReg.test( $email );
}

jQuery(app.ready);
jQuery(window).on('scroll', app.scroll);
jQuery(window).on('resize', app.resize);
jQuery(window).on('load', app.load);

/*jQuery(document).on('scroll', function (e) {
var
windowHeight = jQuery(window).height();
scrollTop = jQuery(this).scrollTop();

jQuery('nav.secondary a').removeClass('active');
jQuery('.content-main section.subpage').removeClass('active');
jQuery('.content-main section.subpage').each(function () {
var
_this = jQuery(this),
height = _this.height(),
offset = _this.offset();

if (scrollTop + windowHeight > offset.top + height) {
_this.addClass('active');

jQuery('nav.secondary a.a-'+_this.attr('id')).addClass('active');
}
});
});*/
