import React from "react";
import {Redirect, Route, Routes} from "react-router-dom";
import NavBar from "./components/NavBar";
import Login from "./components/Login";
import ProductsContainer from "./containers/ProductsContainer";
import ProductContainer from "./containers/ProductContainer";
import CartContainer from "./containers/CartContainer";
import OrderConfirmation from "./components/OrderConfirmation";
import ProductsByGenreContainer from "./containers/ProductsByGenreContainer";
import "./App.css";

class App extends React.Component {
  render() {
    return (
      <div className="App">
        <NavBar />
        <main role="main" className="container-fluid">
          <Routes>
            <Route path="/login">
              <Login/>
            </Route>
            <Route exact path="/">
               <Redirect to="/products" />
            </Route>
            {/* <Route path="/products" render={(props) => (
                <ProductsContainer key={props.location.search} {...props} />)
            } /> */}
            <Route path="/products" children={<ProductContainer />} />
            <Route exact path="/products/:id">
              <ProductContainer/>
            </Route>
            {/* <Route path="/genres" render={(props) => (
                <ProductsByGenreContainer key={props.location.search} {...props} />)
            } /> */}
            <Route path="/genres" children = {<ProductsByGenreContainer/>} />
            <Route path="/cart">
              <CartContainer/>
            </Route>
            <Route path="/orderconfirmation/:orderId">
                <OrderConfirmation/>
            </Route>
          </Routes>
        </main>
          <footer className="footer mt-auto py-3 text-center">
              <div className="container">
                  <span className="text-muted">SivaLabs &copy; 2020.</span>
              </div>
          </footer>
      </div>
    );
  }
}

export default App;
