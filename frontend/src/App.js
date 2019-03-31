import React, { Component } from "react";
import Dictionary from "./components/dictionary";
import "./App.css";

class App extends Component {
  render() {
    return (
      <div className="app-wrapper">
        <header className="main-header">Słownik polsko-angielski</header>
        <Dictionary />
        <footer className="main-footer">
          Kamil Kołodziejczyk, Paweł Siwoń 2019 &copy;
        </footer>
      </div>
    );
  }
}

export default App;
