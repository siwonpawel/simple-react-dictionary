import React, { Component } from "react";
import { Button, Icon, Input, AutoComplete } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";

class Dictionary extends Component {
  state = {
    translation: "(Czeka na podanie)",
    inputLang: "POLSKI",
    outputLang: "ANGIELSKI",
    inputWord: ""
  };
  onTranslateHandler = () => {
    const apiUrl =
      "http://localhost:8080/api/dictionary" +
      (this.state.inputLang === "POLSKI"
        ? `/pl/en/${this.state.inputWord}`
        : `/en/pl/${this.state.inputWord}`);

    axios
      .get(apiUrl)
      .then(res => {
        console.log(res);
      })
      .catch(err => console.log(err));
    this.setState({ inputWord: "" });
  };
  render() {
    return (
      <div className="dictionary">
        <header className="dict-header">
          <p>
            {this.state.inputLang}
          </p>

          <p>
            {this.state.outputLang}
          </p>
        </header>
        <Button
          onClick={() => {
            this.setState({
              inputLang:
                this.state.inputLang === "POLSKI" ? "ANGIELSKI" : "POLSKI",
              outputLang:
                this.state.outputLang === "POLSKI" ? "ANGIELSKI" : "POLSKI"
            });
          }}
          type="primary"
        >
          <Icon type="swap" />
        </Button>
        <div className="dict-logic">
          <div className="input-word">
            <Input
              value={this.state.inputWord}
              onChange={e => this.setState({ inputWord: e.target.value })}
            />
          </div>

          <div className="output-word">
            TŁUMACZENIE: <br />
            <div className="translation-output">{this.state.translation}</div>
          </div>
        </div>
        <Button onClick={this.onTranslateHandler} type="primary">
          Tłumacz
        </Button>
        lub <Link to="/add-word">Dodaj nowe słowo</Link>
      </div>
    );
  }
}

export default Dictionary;
