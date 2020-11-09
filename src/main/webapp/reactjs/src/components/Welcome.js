import React from 'react';

import {Jumbotron} from 'react-bootstrap';


class Welcome extends React.Component{
    render()
     {
     return (
                <Jumbotron className="bg-dark text-white">
                  <h1>Home Page</h1>
                  <p>
                    This programs provides information about FAT workers entrace states.
                    at this stage following options are provided:
                    <ul>
                        <li>
                            Active workers status
                        </li>
                        <li>
                            Current state(amount) of workers
                        </li>
                        <li>
                            Informations about last actions on entrance gate
                        </li>
                    </ul>

                  </p>
                </Jumbotron>
     );
     }

}

export default Welcome;