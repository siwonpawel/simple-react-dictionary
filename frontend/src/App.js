import React, { Component } from "react";
import { Route, Redirect, Switch, Link } from "react-router-dom";
import Dictionary from "./components/dictionary";
import WordForm from "./components/wordForm";
import "./App.css";

class App extends Component {
  render() {
    return (
      <div className="app-wrapper">
        <header className="main-header">Słownik polsko-angielski</header>

        <Switch>
          <Route path="/dictionary" component={Dictionary} />
          <Route path="/add-word" component={WordForm} />
          <Redirect from="/" exact to="/dictionary" />
          <Redirect to="/not-found" />
        </Switch>

        <footer className="main-footer">
          Kamil Kołodziejczyk, Paweł Siwoń 2019 &copy;
        </footer>
      </div>
    );
  }
}

export default App;
