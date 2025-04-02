import React, { useEffect, useState } from 'react';
import axios from "axios";
import {PORT, SERVER} from '../constants';

interface PaginationControllProps{
    handleData: (data: any) => void;
  }
  
  const PaginationControll: React.FC<PaginationControllProps> = ({
    handleData,
  }) => {
    const [pagination, setPagination] = useState(0);
    
    useEffect(()=>{
        let url = `http://${SERVER}:${PORT}/paginate?pageNumber=${pagination}`;
        axios.get(url).then((response)=>{
            handleData(response.data);
        }).catch(error =>{console.log(error);})
    }, [pagination]);
  
      function decPagination(){
        if (pagination == 0) return;
        setPagination(pagination-1);
      }
    
      function incPagination(){
        setPagination(pagination+1);
      }
  
      return(
        <div className='add-form' style={{backgroundColor : "#4b7674"}}>
          <button onClick={decPagination}>&larr;</button>
          <h3 >Page {pagination+1}</h3>
          <button onClick={incPagination}>&rarr;</button>
        </div>
      );
  };
  
  export default PaginationControll;