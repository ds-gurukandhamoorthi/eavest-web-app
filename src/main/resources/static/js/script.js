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
  if ($(document).height() <= ($(window).height() + $(window).scrollTop()))
  {
    $('.footer-arrow').css({
      position: 'fixed',
      bottom: 130
      });
    } else
    {
    $('.footer-arrow').css({
      position: 'fixed',
      bottom: 20
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
  $('#more-1').hide();
  $(".product-content-1").hover(function(){
      $(this).css("opacity", 0.5);
      $(this).css("background-color", "white");
      $("#more-1").show();
      }, function(){
      $(this).css("opacity", 1);
      $(this).css("background-color", "transparent");
      $("#more-1").hide();
  });

  $('#more-2').hide();
  $(".product-content-2").hover(function(){
    $(this).css("opacity", 0.5);
    $(this).css("background-color", "white");
    $("#more-2").show();
    }, function(){
    $(this).css("opacity", 1);
    $(this).css("background-color", "transparent");
    $("#more-2").hide();
  });
});
