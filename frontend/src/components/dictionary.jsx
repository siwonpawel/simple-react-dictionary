import React, { Component } from "react";
import { Button, Icon, Input } from "antd";

class Dictionary extends Component {
  state = {
    translation: "(Czeka na podanie)",
    inputLang: "POLSKI",
    outputLang: "ANGIELSKI",
    inputWord: ""
  };
  onTranslateHandler = () => {
    console.log(`Input word: ${this.state.inputWord}`);
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
      </div>
    );
  }
}

export default Dictionary;
