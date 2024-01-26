import React, {Component, component} from "react";
class ButtonCellRenderer extends Component{
    constructor(props){
        super(props);
        this.buttonClickedHandler = this.buttonClickedHandler.bind(this);
    }
    buttonClickedHandler(){
        this.props.clicked(this.props.value);
    }
    render(){
        return <button onClick={this.buttonClickedHandler}>
            download
        </button>
    }

}
export default ButtonCellRenderer;
