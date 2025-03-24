import React from 'react';
import type { FormProps, CheckboxProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, Space } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';

type FieldType = {
  username?: string;
  password?: string;
  departureDate?: string;
  returnDate?: string;
  currency?: string;
  nonStop?: boolean;

};

const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
  console.log('Success:', values);
  //ACCEDER A LA FECHA PARA VER COMO LA ESTA MANEJANDO, PORQUE APARECE COMO "M2"
};

const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
  console.log('Failed:', errorInfo);
};

dayjs.extend(customParseFormat);

const dateFormat = 'YYYY-MM-DD';

const handleChange = (value: { value: string; label: React.ReactNode }) => {
    console.log(value); // { value: "lucy", key: "lucy", label: "Lucy (101)" }
};  

const onChange: CheckboxProps['onChange'] = (e) => {
    console.log(`checked = ${e.target.checked}`);
  };


const Search: React.FC = () => (
    <Space direction="vertical" size={16}>
    <Card title="Check your flight" style={{ width: 1100, backgroundColor:"Gray"}}>
  <Form
    name="basic"
    labelCol={{ span: 10 }}
    wrapperCol={{ span: 30 }}
    style={{ maxWidth: 800 }}
    initialValues={{ remember: true }}
    onFinish={onFinish}
    onFinishFailed={onFinishFailed}
    autoComplete="off"
  >
    <Form.Item<FieldType>
      label="Departure Airport"
      name="username"
      rules={[{ required: true, message: 'Please input the departure airport' }]}
    >
      <Input />
    </Form.Item>

    <Form.Item<FieldType>
      label="Arrival Airport"
      name="password"
      rules={[{ required: true, message: 'Please input the arrival airport' }]}
    >
      <Input/>
    </Form.Item>

    <Form.Item<FieldType>
      label="Departure date"
      name={"departureDate"}
      rules={[{ required: true, message: 'Please a date' }]}
    >
    <DatePicker
    defaultValue={dayjs('2019-09-03', dateFormat)}
    minDate={dayjs('2019-08-01', dateFormat)}
    // maxDate={dayjs('2020-10-31', dateFormat)}
    />
    </Form.Item>


<Form.Item<FieldType>
      label="Return date"
      name={"returnDate"}
      rules={[{ required: true, message: 'Please a date' }]}
    >

    <DatePicker
    // defaultValue={dayjs('2025-09-03', dateFormat)}
    minDate={dayjs('2019-08-01', dateFormat)}
    // maxDate={dayjs('2025-09-03', dateFormat)}
    />
    </Form.Item>


    
    <Form.Item label="Adults: ">
    <Select
    //CAMBIAR TODAS LAS PROPIEDADES PARA QUE QUEDEN BIEN. USAR UN FOR. 
        labelInValue
        defaultValue={{ value: 'lucy', label: '1' }}
        style={{ width: 120 }}
        onChange={handleChange}
        options={[
        {
            value: 'jack',
            label: '1',
        },
        {
            value: 'lucy',
            label: '2',
        },
        {
            value: 'something',
            label: '3',
        },
        ]}
    />
    </Form.Item>


    <Form.Item label="Currency: ">
    <Select
        labelInValue
        defaultValue={{ value: 'lucy', label: 'MXN' }}
        style={{ width: 120 }}
        onChange={handleChange}
        options={[
        {
            value: 'jack',
            label: 'MXN',
        },
        {
            value: 'lucy',
            label: 'USD',
        },
        {
            value: 'something',
            label: 'EUR',
        },
        ]}
    />
    </Form.Item>

    <Form.Item label={null}>
    <Checkbox onChange={onChange}>Non-Stop</Checkbox>
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

export default Search;