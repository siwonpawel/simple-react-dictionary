import React, { Component } from "react";
import { Route, Redirect, Switch } from "react-router-dom";
import Dictionary from "./components/dictionary";
import WordForm from "./components/wordForm";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import Axios from "axios";

class App extends Component {
  render() {
    return (
      <div className="app-wrapper">
        <header className="main-header">Słownik polsko-angielski</header>
        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={true}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnVisibilityChange
          draggable
          pauseOnHover
        />
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
  componentDidMount() {
    Axios.get("http://localhost:8080/api/dictionary")
      .then(console.log)
      .catch(console.log);
  }
}

export default App;
