$(function() {

// navbar
  $(document).on('scroll', function(){
    var scrollPos = $(window).scrollTop();
    if (scrollPos <= 0) {
      $('.slider').hide();
    } else {
      $('.slider').show();
    }
  });

// footer-arrow hide /show on scroll
  $(document).on('scroll', function(){
    var scrollPos = $(window).scrollTop();
    if (scrollPos <= 0) {
      $('.footer-arrow').hide();
    } else {
      $('.footer-arrow').fadeIn(1000);
    }
  });

  // Stop the arrow before footer
$(window).scroll(function() {
    var elementOffset = $('#footer').offset().top;
    var footerTop = (elementOffset - $(window).scrollTop());
    if(footerTop > ($(window).height() - 20)) {
      $('.footer-arrow').css({
        position: 'fixed',
        bottom: 20
        });
      }

    if( footerTop < ($(window).height() - 20) ){
        $('.footer-arrow').addClass('fixed');
          $('.footer-arrow').css({
            position: 'fixed',
            bottom: ($(window).height() - footerTop),
        });
    }
})

  // scroll up on click arrow big
  $('.footer-arrow').click(function(){
  $('html, body').animate({scrollTop:0}, 'speed');
  });
    // scroll up on click arrow small
  $('#scroll-back-to-top').click(function(){
  $('html, body').animate({scrollTop:0}, 'speed');
  });

// end window load
});

// ACCORDION redirection selon nombre de produits
var refundProducts = []
var tocallProducts = []

$(function() {

  if (refundProducts <= 5) {
    $( '#more-product-1, .more-product-1' ).hide();
  } else {
    $( '#more-product-1, .more-product-1' ).show();
    $('#headingSix, #headingSeven, #headingEight').hide();
  };

  if (tocallProducts <= 5) {
    $( '#more-product-2, .more-product-2' ).hide();
  } else {
    $( '#more-product-1, .more-product-1' ).show();
    $('#heading2Six, #heading2Seven, #heading2Eight').hide();
  };

  // redirection accordion au hover
  // afficher l'accordion total au click qui est en display none avant
  $( 'a #more-product-1' ).click(function(){
    $('#headingSix, #headingSeven, #headingEight').show();
    $( '#more-product-1, .more-product-1' ).hide();
    $('.activestate').css({'font-weight': 'bold', 'color': 'black'});
  });

  $( 'a #more-product-2' ).click(function(){

    $('#heading2Six, #heading2Seven, #heading2Eight').show();
    $( '#more-product-2, .more-product-2' ).hide();
    $('.activestate').css({'font-weight': 'bold', 'color': 'black'});
  });

});

// Tableau collapse perf review

$(function() {
  $('#more-perfReview').click(function(){
    $('.display-on-click, #close').show();
    $('#more-perfReview').hide();
  });
  $('#close').click(function(){
    $('.display-on-click, #close').hide();
    $('#more-perfReview').show();
  })
});

// Set a fontSize relative to a number of words (dynamic content)

$(function() {

    var $quote = $('#figure-month');

    var $numWords = $quote.text().split(" ").length;

    if (($numWords >= 1) && ($numWords <= 2)) {
        $quote.css("font-size", "60px");
    }
    else if (($numWords >= 3) && ($numWords < 5)) {
        $quote.css("font-size", "50px");
    }
    else {
        $quote.css("font-size", "30px");
    }

});
