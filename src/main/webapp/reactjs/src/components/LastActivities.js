import React,{Component} from 'react';

import {Card,Table,Form,Button} from 'react-bootstrap';

import axios from 'axios';

export default class LastActivities extends Component {

constructor(props)
{
super(props);
    this.state = {
            persons : []
    };
}

componentDidMount()
{
    axios.get("http://localhost:8084/lastinout/200")
        .then(response => response.data)
        .then((data) => {
            this.setState({persons: data})
        });
}



    render()
    {

        return (
         <div>
            <Form>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control type="email" placeholder="Enter email" />
                <Form.Text className="text-muted">
                  We'll never share your email with anyone else.
                </Form.Text>
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" />
              </Form.Group>
              <Form.Group controlId="formBasicCheckbox">
                <Form.Check type="checkbox" label="Check me out" />
              </Form.Group>
              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Form>

              <Card className = {"border border-dark bg-dark text-white"}>
                       <Card.Header>Last activities on entrance gate</Card.Header>
                       <Card.Body>
                          <Table bordered hover striped variant = "dark">
                          <Table striped bordered hover>
                            <thead>
                              <tr>
                                <th>#</th>
                                <th>id_karty</th>
                                <th>akcja</th>
                                <th>data</th>
                                <th>nazwisko imie</th>
                                <th>stanowisko</th>
                                <th>firma</th>
                              </tr>
                            </thead>
                            <tbody>
                            {this.state.persons.length === 0 ?
                              <tr align="center">
                              <td colSpan ="7">{this.state.persons.length} persons</td>
                              </tr>
                              :
                              this.state.persons.map((person,index) =>(
                              <tr key={person.id_karty}>
                                    <td>{index+1} </td>
                                    <td>{person.id_karty} </td>
                                    <td>{person.akcja} </td>
                                    <td>{person.data} </td>
                                    <td>{person.nazwisko_imie} </td>
                                    <td>{person.stanowisko} </td>
                                     <td>{person.firma} </td>
                              </tr>

                              ))


                              }
                            </tbody>
                          </Table>
                          </Table>

                       </Card.Body>
                   </Card>

          </div>


        );
    }
}

