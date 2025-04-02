import React, { useEffect, useState } from 'react';
import type { FormProps, CheckboxProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, Space, AutoComplete, Modal } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import {PORT} from '../constants';
import { useNavigate } from 'react-router-dom';
import axios from "axios";

interface SearchProps{
  handleSetUrl: (value:string) => void;
}

const Search: React.FC<SearchProps> = ({
  handleSetUrl
}) => {
  const navigator = useNavigate();
  const today = dayjs();
  const [query, setQuery] = useState("");
  const [options, setOptions] = useState<{value:string, label:string}[]>([]);
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentDate, setCurrentDate] = useState(today.add(1, "day"));

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
    if(departureAirport.length != 3 || arrivalAirport.length != 3 || !(/^[A-Za-z]+$/.test(departureAirport)) || !(/^[A-Za-z]+$/.test(arrivalAirport))){
      showModal();
      return;
    }
    departureAirport = departureAirport.toUpperCase();
    arrivalAirport = arrivalAirport.toUpperCase();
    const departureDate = dayjs(values.departureDate).format(dateFormat); 
    let returnDate = values.returnDate;
    returnDate = returnDate == undefined ? "none" : dayjs(values.returnDate).format(dateFormat);
  
    const adults = values.adults.value;
    const nonStop = values.nonStop;
    const currency = values.currency.value;
  
    let url = `http://localhost:${PORT}/flights?departureAirportCode=${departureAirport}&arrivalAirportCode=${arrivalAirport}&departureDate=${departureDate}&returnDate=${returnDate}&adults=${adults}&nonStop=${nonStop}&currencyCode=${currency}`;
    handleSetUrl(url);
    navigator('/showResult');
  };

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };
  
  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  
  dayjs.extend(customParseFormat);
  const dateFormat = 'YYYY-MM-DD';
  
  let optionsCount = [];
  for(let i = 1; i<10;i++){
    optionsCount.push({
      "value": i,
      "label":i
    });
  }

  const fetchAirports = async (keyword:string) => {
    setLoading(true);
    if(!(/^[A-Za-z]+$/.test(keyword))) return; //Check if it has special characters
    let data:any = [];
    await axios.get(`http://localhost:${PORT}/codes?keyword=${keyword}`).then((response)=>{
      data = response.data;
    }).catch(error =>{console.log(error);})
    setOptions(
      data.map((airport: {detailedName:string, airportCode:string}) => ({
        value: airport.airportCode,
        label: `${airport.detailedName} (${airport.airportCode})`,
      }))
    );
    setLoading(false);
  }

  useEffect(()=>{
    if(query.length < 3){
      setOptions([]);
      return;
    }
    const debounce = setTimeout(()=>{
      fetchAirports(query);
    }, 500);
    return () => clearTimeout(debounce);
  }, [query]);

  const handleDateChange = (date:any, dateString:any) =>{
    setCurrentDate(dayjs(dayjs(dateString).format(dateFormat)));
    console.log(date);
  }

  return(
    <div>
    <Space direction="vertical" size={16}>
        <Card title="Check your flight" className='show-card'>
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

          <AutoComplete
            options={options}
            style={{ width: 300}}
            onSearch={setQuery}
            onSelect={(value)=> console.log("Selected airport: ", value)}
          >
            <Input.Search loading={loading}/>
          </AutoComplete>
          </Form.Item>


          <Form.Item<FieldType>
            label="Arrival Airport"
            name={"arrivalAirport"}
            rules={[{ required: true, message: 'Please input the arrival airport' }]}
          >
            
            <AutoComplete
            options={options}
            style={{ width: 300}}
            onSearch={setQuery}
            onSelect={(value)=> console.log("Selected airport: ", value)}
          >
            <Input.Search loading={loading}/>
          </AutoComplete>

          </Form.Item>

          <Form.Item<FieldType>
            label="Departure date"
            name={"departureDate"}
            rules={[{ required: true, message: 'Please input a date' }]}
          >
          <DatePicker minDate={today} onChange={handleDateChange}/>
          </Form.Item>

          <Form.Item<FieldType>
            label="Return date"
            name={"returnDate"}
          >

          <DatePicker
          minDate={currentDate.add(1, "day")}
          />
          </Form.Item>


          <Form.Item label="Adults:" name={"adults"} rules={[{ required: true, message: 'Please select the number of adults' }]}    >
          <Select
              labelInValue = {true}
              defaultValue={{ value: '0', label: '-' }}
              style={{ width: 120 }}
              options={optionsCount}
          />
          </Form.Item>

          <Form.Item label="Currency: " name={"currency"} rules={[{ required: true, message: 'Please select a currency' }]}    >
          <Select
              labelInValue = {true}
              style={{ width: 120 }}
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
            <Button type="primary" htmlType="submit" className='return-button'>
              Search
            </Button>
          </Form.Item>

        </Form>
        </Card>
        </Space>
        <Modal title="Error message" open={isModalOpen} onCancel={handleCancel} className="modal">
          <h2>The airport code should be 3 characters long and only letters</h2>
        </Modal>   
    </div>
  );
}

export default Search;