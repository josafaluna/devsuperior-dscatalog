import { NavLink } from "react-router-dom";
import "./style.scss";

const Navbar = () => (
  <nav className="row bg-primary main-nav">
    <div className="col-2">
      <NavLink to="/" className="nav-logo-text">
        <h4>DS Catalog</h4>
      </NavLink>
    </div>
    <div className="col-6 offset-2">
      <ul className="main-menu">
        <li>
          <NavLink to="/" activeClassName="active" exact>
            HOME
          </NavLink>
        </li>
        <li>
          <NavLink to="/catalog" activeClassName="active">
            CATÁLOGO
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin" activeClassName="active">
            ADMIM
          </NavLink>
        </li>
      </ul>
    </div>
  </nav>
);

export default Navbar;
