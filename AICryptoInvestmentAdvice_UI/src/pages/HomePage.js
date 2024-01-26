import React, {useEffect, useState}  from "react";
import axios from "axios";
import {View, Text} from "react-native";
import {Link} from "react-router-dom";
import { Dialog, DialogActions, DialogContent, TextField, Button, Menu, MenuItem} from "@mui/material";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import HistoryOutlinedIcon from '@mui/icons-material/HistoryOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
export default function HomePage(){
    const [cryptoNames, setCryptoNames] = useState([])
    const [exchanges, setExchanges] = useState([])
    const [cryptoNameSelected, setCryptoNameSelected] = useState("")
    const [exchangeSelected, setExchangeSelected] = useState("")
    const [targetNotFound, setTargetNotFound] = useState(true)
    const [checkMarketPrice, setCheckMarketPrice] = useState(false)
    const [currentPrice, setCurrentPrice] = useState(0)
    const [purchasePrice, setPurchasePrice] = useState(0)
    const [quantity, setQuantity] = useState(1)
    const [totalPurchasePrice, setTotalPurchasePrice] = useState("")
    const [purchaseYear, setPurchaseYear] = useState(2023)
    const currentDate = new Date()
    const [targetPrice, setTargetPrice] = useState(0)
    const [targetYear, setTargetYear] = useState(2024)
    const [generateAdvice, setGenerateAdvice] = useState(false) 
    const [aiAdvice, setAIAdvice] = useState("")
    const [openLogInDialog, setOpenLogInDialog] = useState(false)
    const [logInButtonClicked, setLogInButtonClicked] = useState(false)
    const [signUpButtonClicked, setSignUpButtonClicked] = useState(false)
    const [userName, setUserName] = useState("")
    const [password, setPassword] = useState("")
    const [validUser, setValidUser] = useState(false)
    const [profileClicked, setProfileClicked] = useState(false)
    const [anchorEl, setAnchorEl] = React.useState(null)
    const [alertMessage, setAlertMessage] = useState("")
    const [openAlertDialog, setOpenAlertDialog] = useState(false)

    useEffect(() => {
        async function fetchCryptoNames() {
            if(cryptoNames.length === 0){
                const {data} = await axios.get("https://api.coingecko.com/api/v3/coins/list");
                const resultList = []
                data.forEach((value) => {
                    resultList.push(value.id)   
                });
                setCryptoNames(resultList)
            }
        }
        fetchCryptoNames();
    },[cryptoNames]);

    useEffect(() => {
        async function fetchExchanges(){
            if(cryptoNameSelected !== ""){
                const {data} = await axios.get(`https://api.coingecko.com/api/v3/coins/${cryptoNameSelected}/tickers`);
                const resultList = []
                if(data["tickers"].length > 0){
                    data["tickers"].forEach((value) => {
                        if(value.coin_id === cryptoNameSelected && value.target === "USD"){
                            resultList.push(value["market"].identifier)
                            setTargetNotFound(false)
                        }
                    });
                }
                if(targetNotFound){
                    setCheckMarketPrice(true)
                }
                else{
                    setCheckMarketPrice(false)
                }
                setTargetNotFound(true)
                setExchanges([...new Set(resultList)])
           }
        }
        fetchExchanges();
    },[cryptoNameSelected, targetNotFound]);

    useEffect( () => {
        async function fetchMarketPrice() {
            if(cryptoNameSelected !== ""&&checkMarketPrice){
                const {data} = await axios.get(`https://api.coingecko.com/api/v3/coins/${cryptoNameSelected}`);
                setCurrentPrice(data["market_data"]["current_price"].usd)
                setPurchasePrice(data["market_data"]["current_price"].usd)
                setTargetPrice(data["market_data"]["current_price"].usd)
                setCheckMarketPrice(false)
                return;
             }
            }
            fetchMarketPrice()
    }, [cryptoNameSelected,checkMarketPrice]);

    useEffect( () => {
        async function fetchCurrentPrice() {
            if(cryptoNameSelected !== "" &&exchangeSelected !==""){
                const {data} = await axios.get(`https://api.coingecko.com/api/v3/exchanges/${exchangeSelected}/tickers`,{params: {coin_ids: cryptoNameSelected}});
                data["tickers"].forEach((value) => {
                    if(value.coin_id === cryptoNameSelected && value.target=== "USD"){                       
                        setCurrentPrice(value.last)
                        setPurchasePrice(value.last)
                        setTargetPrice(value.last)
                        return;
                    }
                });

             }
            }
            fetchCurrentPrice()
    }, [exchangeSelected, cryptoNameSelected]);

    useEffect( () => {
        async function calculateTotalPurchasePrice() {
            if(purchasePrice !== 0){
              setTotalPurchasePrice(quantity*purchasePrice)
            }
            }
            calculateTotalPurchasePrice()
    }, [quantity, purchasePrice]);
 
    useEffect(() => {
        async function createUser() {
            if(signUpButtonClicked && userName !== "" && password !== ""){
                const userInfo  = {
                    userName: userName,
                    password: password
                }
                const {data} = await axios({
                    method: 'post',
                    url: 'http://localhost:8856/aicryptoinvestementadvice/signup',
                     data: JSON.stringify(userInfo),
                     headers: "Content-type: application/json;charset=utf-8"
                     });    
                if(data === true){
                    setAlertMessage("You've successfully created the account!")
                }
                else{
                    setAlertMessage(userName+" was registered. Please use other username.")
                }
            }
            setSignUpButtonClicked(false)
        }
        createUser()
    }, [signUpButtonClicked, userName, password]);

    useEffect(() => {
        async function validateUser() {
            if(!validUser && logInButtonClicked && userName !== "" && password !== ""){
                const userInfo  = {
                    userName: userName,
                    password: password
                }
                const {data} = await axios({
                    method: 'post',
                    url: 'http://localhost:8856/aicryptoinvestementadvice/login',
                     data: JSON.stringify(userInfo),
                     headers: "Content-type: application/json;charset=utf-8"
                     });    
                if(data === true){
                    setValidUser(true)
                    handleCloseDialog()
                }
                else{
                    setValidUser(false)
                    setAlertMessage("Incorrect username/password")
                }
            }
            setLogInButtonClicked(false)
        }
        validateUser()
    }, [logInButtonClicked, userName, password, validUser]);

    useEffect( () => {
        async function fetchAiAdvice() {
            if(!validUser){
                setOpenAlertDialog(true)
            }
            else if(generateAdvice&&validUser){
                    const cryptoInfo  = {
                        name: cryptoNameSelected, 
                        exchange: exchangeSelected, 
                        purchaseQuantity: quantity,
                        purchasePrice: purchasePrice,
                        purchaseYear: purchaseYear,
                        currentPrice: currentPrice, 
                        currentDate: currentDate,
                        targetPrice: targetPrice,
                        targetYear: targetYear,
                        userName: userName
                    }
                    const {data} = await axios({
                    method: 'post',
                    url: 'http://localhost:8856/aicryptoinvestmentadvice/advice',
                     data: JSON.stringify(cryptoInfo),
                     headers: "Content-type: application/json;charset=utf-8"
                     });
                    setAIAdvice(data)
                }
                setGenerateAdvice(false)

             }
            fetchAiAdvice()
    }, [generateAdvice]);
 



    useEffect(()=> {
        if(!profileClicked){
            setAnchorEl(null)
        }
    }, [profileClicked])
    
    
    const handleCryptoNameSelected = (event) => {
        setCryptoNameSelected(event.target.value);
    }

    const handleExchangeSelected = (event) => {
        setExchangeSelected(event.target.value);
    }

    const handleQuantity = (event) => {
        setQuantity(event.target.value);
    }

    const handlePurchasePrice = (event) => {
        setPurchasePrice(event.target.value);
    }

    const handlePurchaseYear = (event) => {
        setPurchaseYear(event.target.value);
    }

    const handleTargetPrice = (event) => {
        setTargetPrice(event.target.value);
    }

    const handleTargetYear = (event) => {
        setTargetYear(event.target.value);
    }

    const handleGenerateAIAdvice = () => {
        setGenerateAdvice(true)
    }

    const handleOpenLogInDialog = () => {
        setAlertMessage("")
        setOpenLogInDialog(true)
    }

    const handleCloseDialog = () => {
        setOpenLogInDialog(false)
    }

    const handleSignUp = () => {
        setSignUpButtonClicked(true)
    }

    const handleLogIn = () => {
        setLogInButtonClicked(true)
    }

    const handleUserName = (event) => {
        setUserName(event.target.value)
    }

    const handlePassword = (event) => {
        setPassword(event.target.value)
    }

    const handleProfileMenu = (event) => {
        setAnchorEl(event.currentTarget)
    }

    const handleCloseMenu = () => {
            setAnchorEl(null)
    }

    const handleCloseAlertDialog = () => {
        setOpenAlertDialog(false)
    }

    const handleExitProfile = () => {
        setUserName("")
        setPassword("")
        setAnchorEl(null)
        setProfileClicked(false)
        setValidUser(false)
    }


    return (
        <View style={{ justifyContent: "center", alignItems: "center", borderWidth: 3, borderStyle: "solid", borderColor: "white", fontFamily: "Courier"}}>
            <View style={{flexDirection: "row",  justifyContent: "space-between",  backgroundColor: "white", width: "100%"}}>
            <View style={{fontFamily: "Arial"}}>
                <label style={{fontSize: 30, color: "grey", fontWeight: "bold"}}>AI Crypto Investment Advice</label>
            </View>

            <View >
            {!validUser && <button style={{backgroundColor: "white", width: 50, height: 30}} onClick={handleOpenLogInDialog}>log in</button>}
                    <Dialog onClose={handleCloseDialog} open={openLogInDialog}>
                        <DialogContent>
                            <div>
                            <TextField label="name" onChange={handleUserName} fullWidth/>
                            <TextField label="password" onChange={handlePassword} fullWidth/>
                            <Text>{alertMessage}</Text>
                            </div>
                        </DialogContent>
                        <DialogActions>
                            <Button variant="contained" color="primary" onClick={handleSignUp}>Sign up</Button>
                            <Button variant="contained" color="primary" onClick={handleLogIn}>Log In</Button>
                        </DialogActions>
                    </Dialog>
            {validUser && 
            <div>
                {userName!==null && <label style={{fontSize: 20}}>Hello, {userName}!</label>}
                <AccountCircleOutlinedIcon style={{width: 50, height: 50}} onClick={handleProfileMenu}/>
                <Menu open={Boolean(anchorEl)} onClose={handleCloseMenu}  anchorEl={anchorEl} >
                    <MenuItem component={Link} to= {"/userhistory/"+userName}>
                    <label>History</label> <HistoryOutlinedIcon/>
                    </MenuItem>
                    <MenuItem onClick={handleExitProfile}>
                    <label>Log out</label> <LogoutOutlinedIcon/>
                    </MenuItem>
                </Menu>
            </div>}
            </View>
            </View>



            <View style={{ justifyContent: "center", alignItems: "center", fontWeight: "bold", backgroundColor:"#f5f5cc", fontFamily: "Arial"}}>
                <div>
                    <label>Name: </label>
                    <select onChange={handleCryptoNameSelected}>
                        {cryptoNames.map((value) => 
                        <option key={value}>{value}</option>)}
                    </select>
                </div>

                <div>
                    <label>Exchange: </label>
                    {exchanges.length > 0 &&<select onChange={handleExchangeSelected}>
                        <option>Please choose an exchange</option>
                        {exchanges.map((value) => 
                        <option key={value}>
                            {value}
                        </option>)}
                    </select>}
                    {exchanges.length === 0 && <label>no {cryptoNameSelected} / USD pair available in any exchange, market data is displayed</label>}                
                </div>
                
                <div>
                    <label>Purchase Price(USD): </label>
                    <input type = "number" onChange= {handlePurchasePrice} defaultValue = {purchasePrice} key={purchasePrice}/>
                </div>  

                <div>
                    <label>Quantity Purchased: </label>
                    <input type = "number" onChange={handleQuantity} defaultValue = {quantity}/>
                </div>     


                <div>
                    <label>Total Purchase Price (USD): </label>
                    <input value = {totalPurchasePrice} disabled/>
                </div>    

                <div>
                    <label>Purchase Year: </label>
                    <input type = "number" onChange= {handlePurchaseYear} defaultValue = {purchaseYear}/>
                </div>  
                   
                <div>
                    <label>Current Price(USD): </label>
                    <input value={currentPrice} disabled/>           
                </div>    

                <div>
                    <label>Current Date: </label>
                    <input value={Date()} disabled/>           
                </div> 
  

                <div>
                    <label>Target Price(USD): </label>
                    <input type = "number" onChange={handleTargetPrice} defaultValue={targetPrice} key={targetPrice}/>
                </div>

                <div>
                    <label>Target Year: </label>
                    <input type = "number" onChange={handleTargetYear} defaultValue={targetYear}/>
                </div>
                <div>
                    <button onClick={handleGenerateAIAdvice} style={{backgroundColor: "white", width: 250, height: 20}}>Generate AI Investment Advice</button>
                    <Dialog open={openAlertDialog} onClose={handleCloseAlertDialog}>
                        <DialogContent>
                            <label>Please log in</label>
                        </DialogContent>
                    </Dialog>
                </div> 
                <View style={{ justifyContent: "center", alignItems: "center", borderTopWidth: 2, borderStyle: "solid", borderColor: "black", fontWeight: "normal", fontFamily: "Helvetica", whiteSpace: "pre-line"}}>                       
                <Text>{aiAdvice}</Text>
                </View>
            </View>
        </View>



    );
}