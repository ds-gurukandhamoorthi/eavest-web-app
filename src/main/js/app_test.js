'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import client from './client';
import follow from './follow'; // function to hop multiple links by "rel"

const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {prdUsers: [], attributes: [], pageSize: 2, links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
	}

	// tag::follow-2[]
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'prdUsers', params: {size: pageSize}}]
		).then(prdUserCollection => {
			return client({
				method: 'GET',
				path: prdUserCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				return prdUserCollection;
			});
		}).done(prdUserCollection => {
			this.setState({
				prdUsers: prdUserCollection.entity._embedded.prdUsers,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: prdUserCollection.entity._links});
		});
	}
	// end::follow-2[]

	// tag::create[]
	onCreate(newPrdUser) {
		follow(client, root, ['prdUsers']).then(prdUserCollection => {
			return client({
				method: 'POST',
				path: prdUserCollection.entity._links.self.href,
				entity: newPrdUser,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [
				{rel: 'prdUsers', params: {'size': this.state.pageSize}}]);
		}).done(response => {
			this.onNavigate(response.entity._links.last.href);
		});
	}
	// end::create[]

	// tag::delete[]
	onDelete(prdUser) {
		client({method: 'DELETE', path: prdUser._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}
	// end::delete[]

	// tag::navigate[]
	onNavigate(navUri) {
		client({method: 'GET', path: navUri}).done(prdUserCollection => {
			this.setState({
				prdUsers: prdUserCollection.entity._embedded.prdUsers,
				attributes: this.state.attributes,
				pageSize: this.state.pageSize,
				links: prdUserCollection.entity._links
			});
		});
	}
	// end::navigate[]

	// tag::update-page-size[]
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}
	// end::update-page-size[]

	// tag::follow-1[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}
	// end::follow-1[]

	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<PrdUserList prdUsers={this.state.prdUsers}
					links={this.state.links}
					pageSize={this.state.pageSize}
					onNavigate={this.onNavigate}
					onDelete={this.onDelete}
					updatePageSize={this.updatePageSize}/>
			</div>
		)
	}
}

// tag::create-dialog[]
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		var newPrdUser = {};
		this.props.attributes.forEach(attribute => {
			newPrdUser[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newPrdUser);

		// clear out the dialog's inputs
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}

	render() {
		var inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field" />
			</p>
		);

		return (
			<div>
				<a href="#createAccount">Create Account</a>

				<div id="createAccount" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new account</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

}
// end::create-dialog[]

class PrdUserList extends React.Component {

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}

	// tag::handle-page-size-updates[]
	handleInput(e) {
		e.preventDefault();
		var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value =
				pageSize.substring(0, pageSize.length - 1);
		}
	}
	// end::handle-page-size-updates[]

	// tag::handle-nav[]
	handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}

	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}

	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}

	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}
	// end::handle-nav[]

	// tag::employee-list-render[]
	render() {
		var prdUsers = this.props.prdUsers.map(prdUser =>
			<PrdUser key={prdUser._links.self.href} prdUser={prdUser} onDelete={this.props.onDelete}/>
		);

		var navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (
			<div>
				<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
				<table>
					<tbody>
						<tr>
							<th>Last Name</th>
							<th>First Name</th>
							<th>E-mail</th>
							<th>Company</th>
							<th></th>
						</tr>
						{prdUsers}
					</tbody>
				</table>
				<div>
					{navLinks}
				</div>
			</div>
		)
	}
	// end::employee-list-render[]
}

// tag::employee[]
class PrdUser extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.prdUser);
	}

	render() {
		return (
			<tr>
				<td>{this.props.prdUser.lastName}</td>
				<td>{this.props.prdUser.firstName}</td>
				<td>{this.props.prdUser.email}</td>
				<td>{this.props.prdUser.company}</td>
				<td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}
// end::employee[]

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
