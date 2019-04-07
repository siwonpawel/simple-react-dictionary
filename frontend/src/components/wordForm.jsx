import React, { Component } from "react";
import { Form, Input, Button } from "antd";

class WordForm extends Component {
  state = {
    plword: "",
    enword: ""
  };
  handleSubmit = () => {
    console.log(this.state.plword, this.state.enword);
  };
  render() {
    return (
      <Form className="add-word-form">
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
          <Button onClick={this.handleSubmit} type="primary">
            Dodaj słowo
          </Button>
        </Form.Item>
      </Form>
    );
  }
}

export default WordForm;
