import React, { Component } from 'react';
import { Button, Icon, AutoComplete } from 'antd';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import debounce from 'lodash.debounce';

class Dictionary extends Component {
  state = {
    translations: '(Czeka na podanie)',
    inputLang: 'POLSKI',
    outputLang: 'ANGIELSKI',
    inputWord: '',
    tips: []
  };
  onTranslateHandler = () => {
    const apiUrl =
      'http://localhost:8080/api/dictionary' +
      (this.state.inputLang === 'POLSKI'
        ? `/pl/en/${this.state.inputWord}`
        : `/en/pl/${this.state.inputWord}`);

    axios
      .get(apiUrl)
      .then(res => {
        if (res.data.translations.length > 1) {
          const translations = res.data.translations.reduce(
            (translations, t) => {
              return translations + ', ' + t;
            }
          );
          this.setState({ translations });
        } else {
          this.setState({ translations: res.data.translations });
        }
      })
      .catch(err => toast.error('Nie ma takiego słowa w słowniku'));
  };
  onChangeHandler = e => {
    this.setState({ inputWord: e });
    this.debounceFunction();
  };

  debounceFunction = debounce(() => {
    if (this.state.inputWord) {
      const apiUrl =
        'http://localhost:8080/api/dictionary/tips' +
        (this.state.inputLang === 'POLSKI'
          ? `/pl/en/${this.state.inputWord}`
          : `/en/pl/${this.state.inputWord}`);
      axios
        .get(apiUrl)
        .then(res => {
          this.setState({ tips: res.data });
        })
        .catch(err => console.log(err));
    }
  }, 1000);
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
                this.state.inputLang === 'POLSKI' ? 'ANGIELSKI' : 'POLSKI',
              outputLang:
                this.state.outputLang === 'POLSKI' ? 'ANGIELSKI' : 'POLSKI'
            });
          }}
          type="primary"
        >
          <Icon type="swap" />
        </Button>
        <div className="dict-logic">
          <div className="input-word">
            <AutoComplete
              style={{
                width: '100%'
              }}
              value={this.state.inputWord}
              dataSource={this.state.tips}
              onChange={e => this.onChangeHandler(e)}
              filterOption={(inputValue, option) =>
                option.props.children
                  .toUpperCase()
                  .indexOf(inputValue.toUpperCase()) !== -1}
            />
          </div>

          <div className="output-word">
            TŁUMACZENIE: <br />
            <div className="translation-output">{this.state.translations}</div>
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
