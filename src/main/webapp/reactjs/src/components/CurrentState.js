import React,{Component} from 'react';

import {Card,Table,Form,Button} from 'react-bootstrap';

import axios from 'axios';

export default class CurrentState extends Component {
constructor(props)
{
super(props);
    this.state = {
            persons : []
    };
}

componentDidMount()
{
    axios.get("http://192.168.90.66:8084/currentstate")
        .then(response => response.data)
        .then((data) => {
            this.setState({persons: data})
        });
}



    render()
    {

        return (
              <Card className = {"border border-dark bg-dark text-white"}>
                       <Card.Header>Current state of workers in FAT</Card.Header>
                       <Card.Body>
                          <Table bordered hover striped variant = "dark">
                          <Table className = {"border border-dark bg-dark text-white"}>
                            <thead>
                              <tr>
                                <th>#</th>
                                <th>worker Name</th>
                                 <th>Entrance data</th>
                                <th>worker ID</th>

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
                                    <td>{person.workerName} </td>
                                    <td>{person.dataWejscia} </td>
                                    <td>{person.workerID} </td>
                              </tr>

                              ))


                              }
                            </tbody>
                          </Table>
                          </Table>

                       </Card.Body>
                   </Card>
        );
    }
}