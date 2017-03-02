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
// footer-arrow hide show on scroll
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
    var height = $(window).height();
    var footHeight = $('#footer').offset().top;

if ($(document).height() <= ($(window).height() + $(window).scrollTop()))
{
  $('.footer-arrow').css({
    position: 'fixed',
    bottom: 140
    });
  } else
  {
  $('.footer-arrow').css({
    position: 'fixed',
    bottom: 20,
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
});


// Mouse Over
  $(function() {

// block produits remboursés
    $('#more-1').hide();
    $('#more-2').hide();

    $('.overlay-1').mouseover(function(){
      $('.overlay-1').css({'background':'white', 'opacity':'0.6'});
      $('.overlay-item').css({'background':'white', 'opacity':'0.6'});
      $(this).fadeIn("slow");
      $('#more-1').show();
        $('#more-1').mouseover(function(){
          $('.overlay-1').css({'background':'white', 'opacity':'0.6'});
          $('.overlay-item').css({'background':'white', 'opacity':'0.6'});
          $('#more-1').show();
          $('#more-1').stop(true);
        })
    });
    $('.overlay-1').mouseout(function(){
      $('#more-1').hide();
      $('.overlay-1').css({'background':'transparent','opacity':'1'});
      $('.overlay-item').css({'background':'white','opacity':'1'});
    });

// block produits à rappeler
  $('.overlay-2').mouseover(function(){
    $('.overlay-2').css({'background':'white', 'opacity':'0.6'});
    $('.overlay-item').css({'background':'white', 'opacity':'0.6'});
    $(this).fadeIn("slow");
    $('#more-2').show();
      $('#more-2').mouseover(function(){
        $('.overlay-2').css({'background':'white', 'opacity':'0.6'});
        $('.overlay-item').css({'background':'white', 'opacity':'0.6'});
        $('#more-2').show();
        $('#more-2').stop(true);
      })
    });
    $('.overlay-2').mouseout(function(){
      $('#more-2').hide();
      $('.overlay-2').css({'background':'transparent','opacity':'1'});
      $('.overlay-item').css({'background':'white','opacity':'1'});
    });
  });
