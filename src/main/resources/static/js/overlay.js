// OVERLAY ALL PAGE
  $(function() {

// block produits remboursés
    $('.overlay-1').mouseover(function(){
      $('.overlay-1, .overlay-item-1').css({'background':'white', 'opacity':'0.6'});
      $('#more-1').show();
        $('#more-1').mouseover(function(){
          $('.overlay-1, .overlay-item-1').css({'background':'white', 'opacity':'0.6'});
          $('#more-1').show();
        })
    });
    $('.overlay-1').mouseout(function(){
      $('#more-1').hide();
      $('.overlay-1, .overlay-item-1').css({'background':'white','opacity':'1'});
    });

// block produits à rappeler
  $('.overlay-2').mouseover(function(){
    $('.overlay-2, .overlay-item-2').css({'background':'white', 'opacity':'0.6'});
    $('#more-2').show();
      $('#more-2').mouseover(function(){
        $('.overlay-2, .overlay-item-2').css({'background':'white', 'opacity':'0.6'});
        $('#more-2').show();
      })
    });
    $('.overlay-2').mouseout(function(){
      $('#more-2').hide();
      $('.overlay-2').css({'background':'transparent','opacity':'1'});
      $('.overlay-item-2').css({'background':'white','opacity':'1'});
    });

// block produit du mois overlay
    $('.white-block').mouseover(function(){
    $('.white-block').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'1'});
    $('.img-product, #month-product-title').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
    $('#more-3').show();
    $('#more-3').css({'opacity':'1'});
      $('#more-3').mouseover(function(){
        $('.img-product, #month-product-title').css({'background':'white', 'opacity':'0.6'});
        $('#more-3').show();
      })
    });
    $('.white-block').mouseout(function(){
      $('#more-3').hide();
      $('.white-block, .img-product, #month-product-title').css({'background':'white','opacity':'1'});
    });

// block produit du mois affichage sm only overlay
    $('#white-block').mouseover(function(){
      $('#white-block, .white-block, #month-product-title, .month-product, .img-product').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-3-sm').show().css({'opacity':'1'});
        $('#more-3-sm').mouseover(function(){
          $('.white-block, #month-product-title, .month-product, .img-product').css({'background':'white', 'opacity':'0.6'});
          $('#more-3-sm').show();
        })
      });
      $('#white-block').mouseout(function(){
        $('#more-3-sm').hide();
        $('#white-block, .white-block, #month-product-title, .month-product, .img-product').css({'background':'white','opacity':'1'});
      });

    // SECTION BLOG grey-block overlay
    $('.grey-block').mouseover(function(){
      $('.over').show();
      $('.over').css({'transition':'all 0.4s ease','opacity': '0.92'});
      $('#more-grey-block').show();
      $('#more-grey-block').mouseover(function(){
        $('.over').show();
      })
    });
    $('.grey-block').mouseout(function(){
      $('#more-grey-block').hide();
      $('.over').hide();
    });

    $('.grey-block').mouseover(function(){
      $('.grey-block, .blue-feature-1, .blue-title-1, .blog-title-1, .blog-img, .article, #blog-1-p').css({'background':'#0251FB', 'opacity':'0.6'});
      $('#more-grey-block').show();
        $('#more-grey-block').mouseover(function(){
          $('.blue-feature-1, .blue-title-1, .blog-title-1, .blog-img, .article, #blog-1-p').css({'background':'#0251FB', 'opacity':'0.6'});
          $('#more-grey-block').show();
        })
    });
    $('.grey-block').mouseout(function(){
      $('#more-grey-block').hide();
      $('.grey-block, .blue-feature-1, .blue-title-1, .blog-title-1, .blog-img, .article, #blog-1-p').css({'background':'#E5E5E5','opacity':'1'});

    });


    // chiffre du mois
    $('.white-block-blog').mouseover(function(){
      $('.white-block-blog, .figure-margin, #figure-month-title, .figure-month, .green-line, #figure-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('#figure-month-title, .figure-month, #figure-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-figure-month').show();
        $('#more-figure-month').mouseover(function(){
          $('.figure-margin, #figure-month-title, .figure-month, .green-line, #figure-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'1'});
          $('#more-figure-month').show();
        })
    });
    $('.white-block-blog').mouseout(function(){
      $('#more-figure-month').hide();
      $('.white-block-blog, .figure-margin, #figure-month-title, .figure-month, .green-line, #figure-p').css({'background':'white','opacity':'1'});
    });

    // grey-block-2
    $('.grey-block-2').mouseover(function(){
      $('.grey-block-2, .blue-title, .blog-title-2, #blog-2-p').css({'background':'#0251FB', 'opacity':'0.6'});
      $('#more-grey-block-2').show();
        $('#more-grey-block-2').mouseover(function(){
          $('.blue-title, .blog-title-2, #blog-2-p').css({'background':'#0251FB', 'opacity':'0.6'});
          $('#more-grey-block-2').show();
        })
    });
    $('.grey-block-2').mouseout(function(){
      $('#more-grey-block-2').hide();
      $('.grey-block-2, .blue-title, .blog-title-2, #blog-2-p').css({'background':'#E5E5E5','opacity':'1'});

    });

    // eavest-block
    $('.black-block-partner').mouseover(function(){
      $('.black-block-partner, .grey-letters-light, .main-title').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('.grey-letters-light, .main-title').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('.main-title').css({'color': 'black'});
      $('#more-eavest-block').show();
        $('#more-eavest-block').mouseover(function(){
          $('.grey-letters-light, .main-title').css({'background':'white', 'opacity':'0.6'});
          $('#more-eavest-block').show();
        })
    });
    $('.black-block-partner').mouseout(function(){
      $('#more-eavest-block').hide();
      $('.black-block-partner, .grey-letters-light, .main-title').css({'background':'black','opacity':'1'});
      $('.main-title').css({'color': 'white'});
    });

    // notre agrément
    $('.grey-block-3').mouseover(function(){
      $('.grey-block-3, #agrement-title, #agrement-p, .img-agreement').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('#agrement-title, #agrement-p, .img-agreement').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-our-agrement').show();
        $('#more-our-agrement').mouseover(function(){
          $('#agrement-title, #agrement-p, .img-agreement').css({'background':'white', 'opacity':'0.6'});
          $('#more-our-agrement').show();
        })
    });
    $('.grey-block-3').mouseout(function(){
      $('#more-our-agrement').hide();
      $('.grey-block-3, #agrement-title, #agrement-p, .img-agreement').css({'background':'#E5E5E5','opacity':'1'});
    });

    // partenaires
    $('.white-block-partner').mouseover(function(){
      $('.white-block-partner').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('#partner-title, #partner-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-partner').show();
        $('#more-partner').mouseover(function(){
          $('#partner-title, #partner-p').css({'background':'white', 'opacity':'0.6'});
          $('#more-partner').show();
        })
    });
    $('.white-block-partner').mouseout(function(){
      $('#more-partner').hide();
      $('.white-block-partner, #partner-title, #partner-p').css({'background':'white','opacity':'1'});
      $('#partner-p').css({'color':'black'});
    });

    // valeurs
    $('.white-block-style').mouseover(function(){
      $('.white-block-style, .values, .padding_right, #value-img1, #value-img2, #value-img3, #value-img4').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('.values, #values-title, .padding_right, #value-img1, #value-img2, #value-img3, #value-img4').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-values').show();
        $('#more-values').mouseover(function(){
          $('.values, .padding_right, #value-img1, #value-img2, #value-img3, #value-img4').css({'background':'white', 'opacity':'0.6'});
          $('#more-values').show();
        })
    });
    $('.white-block-style').mouseout(function(){
      $('#more-values').hide();
      $('.white-block-style, .values, .padding_right, #value-img1, #value-img2, #value-img3, #value-img4, #values-title').css({'background':'white','opacity':'1'});
      $('#values-title').css({'color':'black'});
    });

    // methodologie
    $('.white-block-style-2').mouseover(function(){
      $('.white-block-style-2, #methodo, .our-methodologie, .methodologie, #methodo-numbers, #methodo-2-block, #methodo-p, #methodo-img1, .p-width-2, methodo-img2, .p-width-3, #methodo-img3').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('#methodo-numbers, #methodo-title, #methodo-p, .number, .p-width-2, .p-width-3, #methodo-img1, #methodo-img2, #methodo-img3').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-methodologie').show();
        $('.white-block-style-2').mouseover(function(){
          $('#methodo-numbers, #methodo-title, #methodo-p, .number, .p-width-2, .p-width-3, #methodo-img1, #methodo-img2, #methodo-img3').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
          $('#more-methodologie').show();
        })
    });
    $('.white-block-style-2').mouseout(function(){
      $('#more-methodologie').hide();
      $('.white-block-style-2, #methodo, #methodo-title, .our-methodologie, .methodologie, #methodo-numbers, #methodo-2-block, #methodo-p, #methodo-img1, .p-width-2, methodo-img2, .p-width-3, #methodo-img3').css({'background':'white','opacity':'1'});
      $('#methodo-title').css({'color': 'black'});
    });
    // team
    $('.grey-block-4').mouseover(function(){
      $('.grey-block-4, .team, .team-img, .team-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.92'});
      $('#team-title, .team-img, .team-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
      $('#more-team').show();
        $('.grey-block-4').mouseover(function(){
          $('#team-title, .team-img, .team-p').css({'background':'rgba(255, 255, 255, 0.92)', 'opacity':'0.6'});
          $('#more-team').show();
        })
    });
    $('.grey-block-4').mouseout(function(){
      $('#more-team').hide();
      $('.grey-block-4, .team, .team-img, .team-p').css({'background':'white','opacity':'1'});
      $('#team-title').css({'color':'black'});
    });

  });
