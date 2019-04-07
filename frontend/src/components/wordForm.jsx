import React, { Component } from "react";
import { Form, Input, Button } from "antd";
import axios from "axios";
import { toast } from "react-toastify";

class WordForm extends Component {
  state = {
    enword: "",
    plword: ""
  };
  handleSubmit = () => {
    const { enword, plword } = this.state;
    const apiUrl = "http://localhost:8080/api/dictionary/pl/en/" + plword;
    axios
      .post(apiUrl, {
        translations: [enword]
      })
      .then(res => toast.success("Udało się dodać tłumaczenie do słownika"))
      .catch(err => toast.error(err));

    this.setState({ enword: "", plword: "" });
  };
  render() {
    return (
      <Form className="add-word-form">
        <Button type="ghost" onClick={() => this.props.history.push("/")}>
          Powrót
        </Button>
        <h1>Dodaj tłumaczenie do słownika</h1>
        <Form.Item>
          <Input
            value={this.state.plword}
            onChange={e => this.setState({ plword: e.target.value })}
            placeholder="Słowo w języku polskim"
          />
        </Form.Item>
        <Form.Item>
          <Input
            value={this.state.enword}
            onChange={e => this.setState({ enword: e.target.value })}
            placeholder="Tłumaczenie w języku angielskim"
          />
        </Form.Item>
        <Form.Item>
          <Button type="primary" onClick={this.handleSubmit}>
            Dodaj tłumaczenie
          </Button>
        </Form.Item>
      </Form>
    );
  }
}

export default WordForm;
