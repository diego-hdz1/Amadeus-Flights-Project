import React from 'react';
import type { FormProps, CheckboxProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, Space } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import {PORT} from '../constants';
import { useNavigate } from 'react-router-dom';

interface SearchProps{
  handleSetUrl: (value:string) => void;
}

const Search: React.FC<SearchProps> = ({
  handleSetUrl
}) => {
  const navigator = useNavigate();

  //TO DO: Lets move this Type to a separate folder
  type FieldType = {
    departureAirport: string;
    arrivalAirport: string;
    departureDate: string;
    returnDate?: string;
    adults: any;
    currency: any;
    nonStop: boolean;
  };

  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
    let departureAirport = encodeURIComponent(values.departureAirport);
    let arrivalAirport = encodeURIComponent(values.arrivalAirport);
    const departureDate = dayjs(values.departureDate).format(dateFormat); 
    let returnDate = values.returnDate;
    returnDate = returnDate == undefined ? "none" : dayjs(values.returnDate).format(dateFormat);
  
    const adults = values.adults.value;
    const nonStop = values.nonStop;
    const currency = values.currency.value;
  
    let url = `http://localhost:${PORT}/flights?departureAirportCode=${departureAirport}&arrivalAirportCode=${arrivalAirport}&departureDate=${departureDate}&returnDate=${returnDate}&adults=${adults}&nonStop=${nonStop}&currencyCode=${currency}`;
    handleSetUrl(url);
    navigator('/prueba');
  };
  
  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  
  dayjs.extend(customParseFormat);
  const dateFormat = 'YYYY-MM-DD';
  
  const handleChange = (value: { value: string; label: React.ReactNode }) => {
      console.log(value); 
  };  

  const today = dayjs();
  let optionsCount = [];
  for(let i = 1; i<10;i++){
    optionsCount.push({
      "value": i,
      "label":i
    });
  }

  return(
    <Space direction="vertical" size={16}>
    <Card title="Check your flight" style={{ width: 1100, backgroundColor:"Gray"}}>
    <Form
      name="basic"
      labelCol={{ span: 10 }}
      wrapperCol={{ span: 30 }}
      style={{ maxWidth: 800 }}
      initialValues={{ remember: true, nonStop: false }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item<FieldType>
        label="Departure Airport"
        name={"departureAirport"}
        rules={[{ required: true, message: 'Please input the departure airport' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Arrival Airport"
        name={"arrivalAirport"}
        rules={[{ required: true, message: 'Please input the arrival airport' }]}
      >
        <Input/>
      </Form.Item>

      <Form.Item<FieldType>
        label="Departure date"
        name={"departureDate"}
        rules={[{ required: true, message: 'Please input a date' }]}
      >
      <DatePicker
      minDate={today}
      //TODO: It needs to change dynamically 
      />
      </Form.Item>


      <Form.Item<FieldType>
        label="Return date"
        name={"returnDate"}
      >

      <DatePicker
      minDate={today.add(1, "day")}
      //TODO: It needs to change dynamically depending on the selected departure date 
      />
      </Form.Item>


      <Form.Item label="Adults:" name={"adults"} rules={[{ required: true, message: 'Please select the number of adults' }]}    >
      <Select
          labelInValue = {true}
          defaultValue={{ value: '0', label: '-' }}
          style={{ width: 120 }}
          onChange={handleChange}
          options={optionsCount}
      />
      </Form.Item>

      <Form.Item label="Currency: " name={"currency"} rules={[{ required: true, message: 'Please select a currency' }]}    >
      <Select
          labelInValue = {true}
          style={{ width: 120 }}
          onChange={handleChange}
          options={[
          {
              value: 'MXN',
              label: 'MXN',
          },
          {
              value: 'USD',
              label: 'USD',
          },
          {
              value: 'EUR',
              label: 'EUR',
          },
          ]}
      />
      
      </Form.Item>
      <Form.Item name="nonStop" valuePropName="checked">
        <Checkbox>Non-Stop</Checkbox>
      </Form.Item>

      <Form.Item label={null}>
        <Button type="primary" htmlType="submit">
          Submit
        </Button>
      </Form.Item>

    </Form>
    </Card>
    </Space>
  );
}

export default Search;