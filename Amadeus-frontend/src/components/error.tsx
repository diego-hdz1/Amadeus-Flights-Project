import React from 'react';
import { Button, Result } from 'antd';
import { useNavigate } from 'react-router-dom';


const Error: React.FC = () => {
    const navigator = useNavigate();
    return (
        <div style={{width:"90rem"}}> 
            <Result
            status="error"
            title="Submission Failed"
            subTitle="There are no flights for the input search parameters"
            extra={[
            <Button type="primary" key="console" onClick={()=>navigator("/")}>
                Return to search
            </Button>,
            ]}
            >
        
            </Result> 
        </div>
    );
};


export default Error;