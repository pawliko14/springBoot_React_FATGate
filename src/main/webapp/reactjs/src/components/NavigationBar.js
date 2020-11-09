import React from 'react';

import {Navbar,Nav} from 'react-bootstrap';
import {Link} from 'react-router-dom';

class NavigationBar extends React.Component {
    render(){
        return (
        <Navbar bg="dark" variant="dark">
            <Link to ={""} className="navbar-brand">
                HOME
            </Link>
             <Nav className="mr-auto">
                <Link to = {"test"} className="nav-link"> Current State</Link>
                <Link to = {"test1"} className="nav-link"> Active Workers</Link>
                <Link to = {"test2"} className="nav-link"> last Activities</Link>

              </Nav>
        </Navbar>
        );
    }
}

export default NavigationBar;