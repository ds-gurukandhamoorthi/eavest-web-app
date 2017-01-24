
import React from 'react';
import ReactDOM from 'react-dom';

const Header = () => (
  <section className="top-header-absolute">

    <header className="main">
      <div className="wrapper">
        <h1>
          <img src="img/eavest-logo.png" alt="Eavest" />
        </h1>

        <nav>
          <ul id="menu-main-menu-francais0" className="nav-menu">
            <li id="menu-item-3015" className="menu-item menu-item-type-post_type menu-item-object-page current-menu-item page_item page-item-2990 current_page_item menu-item-3015"><a href="http://localhost:8080/api/">Se connecter</a></li>
            <li id="menu-item-3043" className="menu-item menu-item-type-post_type menu-item-object-page menu-item-3043"><a href="http://localhost:8080/api/createAccount">S'inscrire</a></li>
          </ul>
        </nav>
      </div>
      <div className="language-switcher">
        <span className="global-linker">
          <a href="http://localhost:8080/">Home</a>
          <a href="https://twitter.com/EAVEST_" target="_blank"><i className="fa fa-twitter"></i></a>
        </span>
        <a href="http://localhost:8080/">fr</a> / <a href="http://localhost:8080/en/">en</a>
      </div>

    </header>

    <div className="slider jquery">
      <div className="slider-container">

      </div>
    </div>
  </section>

)

ReactDOM.render(
	<Header />,
	document.getElementById('eav-header')
)
