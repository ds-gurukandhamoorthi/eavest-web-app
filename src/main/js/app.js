
import React from 'react';
import ReactDOM from 'react-dom';
import {Button, Modal} from 'react-bootstrap';

// This component is the top header toolbar
class Toolbar  extends React.Component {
  constructor(props) {
    super(props);
    this.state = {show: false};
  }

  render() {
    let closeLogin = () => {this.setState({show: false})};
    return(
      <div className="language-switcher">
        <span className="global-linker">
          <a onClick={() => this.setState({show: true})}>Log in</a>
          <a href="https://twitter.com/EAVEST_" target="_blank"><i className="fa fa-twitter"></i></a>
        </span>
        <a href="http://localhost:8080/">fr</a> / <a href="http://localhost:8080/en/">en</a>

        <LoginModal show={this.state.show} onHide={closeLogin} />
      </div>
    );
  }
}

// This component is the log-in modal dialog
class LoginModal extends React.Component {
  render() {
    return (
      <div>
        {/* Modal login form */}
        <Modal show={this.props.show} onHide={this.props.onHide}>
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-sm">LOGIN Dialog</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {/* Put the login form here ... */}
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={this.props.onHide}>Close</Button>
          </Modal.Footer>
        </Modal>
      </div>

    );
  }
}

// tag::create-dialog[]
// This component is the log-in dialog
class LogInDialog extends React.Component {

	render() {
		return (
			<div>
        <a href="#logIn" data-toggle="modal">Log in</a>
        {/* Modal log in form */}
        <div className="modal fade" id="logIn" role="dialog">
          <div className="modal-dialog">

            <div className="modal-content">
              <div className="modal-header">
                <button type="button" className="close" data-dismiss="modal">&times;</button>
                <h4 className="modal-title">LOGIN Dialog</h4>
              </div>
              <div className="modal-body">
                <form className="form-horizontal">
                  <div className="form-group">
                    <label className="control-label col-sm-2" htmlFor="email">Email:</label>
                    <div className="col-sm-4">
                      <input type="email" className="form-control" ref={this.props.eMail} id="email" placeholder="Enter email" defaultValue={this.props.eMail}/>
                    </div>
                  </div>
                  <div className="form-group">
                    <label className="control-label col-sm-2" htmlFor="pwd">Password:</label>
                    <div className="col-sm-4">
                      <input type="password" className="form-control" ref={this.props.password} id="pwd" placeholder="Enter password" defaultValue={this.props.password}/>
                    </div>
                  </div>
                  <div className="form-group">
                    <div className="col-sm-offset-2 col-sm-10">
                      <div className="checkbox">
                        <label><input type="checkbox" /> Remember me</label>
                      </div>
                    </div>
                  </div>
                  <div className="form-group">
                    <div className="col-sm-offset-2 col-sm-10">
                      <button type="submit" className="btn btn-default">Submit</button>
                    </div>
                  </div>
                </form>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

      </div>
		)
	}

}
// end::create-dialog[]

// This component represents the top header banner of eavest web site
class TopHeader extends React.Component {
  render() {
    return (
      <header className="main">
        <div className="wrapper">
          <h1>
            <img src="img/eavest-logo.png" alt="Eavest" />
          </h1>

          <nav>
  					<ul id="menu-main-menu-francais0" className="nav-menu"><li id="menu-item-3015" className="menu-item menu-item-type-post_type menu-item-object-page current-menu-item page_item page-item-2990 current_page_item menu-item-3015"><a href="http://eavest.com/">MISSION</a></li>
  						<li id="menu-item-3043" className="menu-item menu-item-type-post_type menu-item-object-page menu-item-3043"><a href="http://eavest.com/methodologie-2/">Methodologie</a></li>
  						<li id="menu-item-3046" className="menu-item menu-item-type-post_type menu-item-object-page menu-item-3046"><a href="http://eavest.com/sur-mesure/">sur mesure</a></li>
  						<li id="menu-item-154" className="menu-item menu-item-type-post_type menu-item-object-page menu-item-154"><a href="http://eavest.com/track-records/">Produits</a></li>
  						<li id="menu-item-8597" className="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-8597"><a href="http://eavest.com/category/blog/">Le Blog</a></li>
  						<li id="menu-item-3084" className="menu-item menu-item-type-post_type menu-item-object-page menu-item-3084"><a href="http://eavest.com/f-a-q/">FAQ</a></li>
  						<li id="menu-item-2932" className="menu-item menu-item-type-custom menu-item-object-custom menu-item-2932"><a href="mailto:contact@eavest.com">Contact</a></li>
  					</ul>
  				</nav>
        </div>
        <Toolbar />

      </header>
    );
  }
}

ReactDOM.render(
	<TopHeader />,
	document.getElementById('top-header')
)

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
