import { BrowserRouter, Route, Switch } from "react-router-dom";
import Catalog from "./pages/Catalog";
import Home from "./pages/Home";
import Admin from "./pages/Admin/index";
import Navbar from "./core/components/Navbar";

const Routes = () => (
  <BrowserRouter>
    <Navbar />

    <Switch>
      <Route path="/" component={Home} exact />
      <Route path="/catalog" component={Catalog} />
      <Route path="/admin" component={Admin} />
    </Switch>
  </BrowserRouter>
);

export default Routes;
