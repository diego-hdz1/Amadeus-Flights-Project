import React from 'react';
import type { FormProps, CheckboxProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, Space } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';

type FieldType = {
  departureAirport?: string;
  arrivalAirport?: string;
  departureDate?: string;
  returnDate?: string;
  adults?: number;
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

  const today = dayjs();
  let optionsCount = [];
  for(let i = 1; i<10;i++){
    optionsCount.push({
      "value": i,
      "label":i
    });
  }


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
      rules={[{ required: true, message: 'Please a date' }]}
    >
    <DatePicker
    defaultValue={today}
    minDate={today}
    //DEBO DE CAMBIAR PARA QUE SE ACTUALICE LA FECHA SELECCIONADA.
    />
    </Form.Item>


<Form.Item<FieldType>
      label="Return date"
      name={"returnDate"}
      rules={[{ required: true, message: 'Please a date' }]}
    >

    <DatePicker
    minDate={today.add(1, "day")}
    />
    </Form.Item>


    
    <Form.Item label="Adults:" name={"adults"}>
    <Select
    //CAMBIAR TODAS LAS PROPIEDADES PARA QUE QUEDEN BIEN. USAR UN FOR. 
        labelInValue
        defaultValue={{ value: '1', label: '1' }}
        style={{ width: 120 }}
        onChange={handleChange}
        options={optionsCount}
    />
    </Form.Item>


    <Form.Item label="Currency: " name={"currency"}>
    <Select
        labelInValue
        defaultValue={{ value: 'MXN', label: 'MXN' }}
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

    <Form.Item label={null} name={"nonStop"}>
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