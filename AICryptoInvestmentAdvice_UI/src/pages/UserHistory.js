import React, {useCallback, useRef, useState, useEffect} from "react";
import axios from "axios";
import { AgGridReact } from "ag-grid-react";
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import {Link, useParams} from "react-router-dom";
import './../../src/ag-grid.css';
import ButtonCellRenderer from "../ButtonCellRenderer.jsx";
export default function UserHistory(){
    const [userName, setUserName] = useState(useParams().username)
    const [status, setStatus] = useState(false)
    const [openNews, setOpenNews] = useState(false)
    const [openAdvice, setOpenAdvice] = useState(false)
    const [recordId, setRecordId] = useState()
    const [text, setText] = useState("")
    const gridRef = useRef()
    const [rowData, setRowData] = useState([]);
    const [columnDefs, setColumnDefs] = useState([
        {field: "Record ID"},
        {field: "Date"},
        {field: "Name"}, 
        {field: "Exchange"},
        {field: "Quantity Purchased"},
        {field: "Purchase Price"},
        {field: "Purchase Year"},
        {field: "Price"},
        {field: "Target Price(USD)"},
        {field: "Target Year"},
        {field: "News Titles",
         cellRenderer: ButtonCellRenderer,
         cellRendererParams: {
            clicked: function (){
                setOpenNews(true)
            }
         }
        },
        {field: "AI Investment Advice",
         cellRenderer: ButtonCellRenderer,
         cellRendererParams: {
            clicked: function(){
                setOpenAdvice(true)
            }
         }
        }
    ])



    useEffect(() => {
        async function fetchAdviceRecords(){
            if(userName.length > 0 && status === false){
                const {data} = await axios.get(`http://localhost:8856/aicryptoinvestmentadvice/advice/${userName}`);             
                const res = data.map(
                    record => ({
                        "Record ID": record.id,
                        "Date": record.currentDate,
                        "Name": record.name,
                        "Exchange": record.exchange,
                        "Quantity Purchased": record.purchaseQuantity,
                        "Purchase Price": record.purchasePrice,
                        "Purchase Year": record.purchaseYear,
                        "Price": record.currentPrice,
                        "Target Price(USD)": record.targetPrice,
                        "Target Year": record.targetYear,
                        "News Titles": record.newsTitles,
                        "AI Investment Advice": record.aiAdvice
                    }));
                setRowData(res)
                setStatus(true)
            }
        }
        fetchAdviceRecords()
    },[status]);


    useEffect(() => {
        async function fetchNews(){
            if(openNews && userName !== "" && recordId!== undefined){
                const {data} = await axios.get(`http://localhost:8856/aicryptoinvestmentadvice/advice/${userName}/${recordId}/news`);
                setText(data);
            }
            setOpenNews(false)
        }
        fetchNews()
    },[openNews, userName, recordId]);

    useEffect(() => {
        async function fetchAdvice(){
            if(openAdvice && userName !== "" && recordId !== undefined){
                const {data} = await axios.get(`http://localhost:8856/aicryptoinvestmentadvice/advice/${userName}/${recordId}/advice`);
                setText(data);
            }
            setOpenAdvice(false)
        }
        fetchAdvice()
    },[openAdvice, userName, recordId]);


    useEffect(() => {
        async function downloadTxt(){
            if(text.length > 0){
                const link = document.createElement("a");
                const file = new Blob([text], {type: "text/plain"});
                link.href = URL.createObjectURL(file)
                link.download = userName + ".txt"
                document.body.appendChild(link)
                link.click() 
                document.body.removeChild(link)
                setText("")
            }
        }
        downloadTxt()
    },[text]);




    const onSelectionChanged = useCallback(() => {
        const selectedRow = gridRef.current.api.getSelectedRows();
        if(selectedRow.length === 1){
            setRecordId(selectedRow[0]["Record ID"])
        }
    },[]);
 
    return(
        <div>
            <Link to="/"><LogoutOutlinedIcon/><label>log out</label></Link>
            <div className="ag-theme-material" style={{height: 1200, width: 2388}}>
            <AgGridReact
            ref={gridRef}
            columnDefs={columnDefs} 
            rowData={rowData} 
             ag-header-background-color={"blue"} 
            rowSelection={"single"}
            onSelectionChanged={onSelectionChanged}
            ></AgGridReact>
            </div>
        </div>

    );

}
