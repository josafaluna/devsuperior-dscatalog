import "../ButtonIcon/style.scss";
import { ReactComponent as ArrowIcon } from "../../assets/images/arrow.svg";

type Props = {
  title: string;
};

const ButtonIcon = ({ title }: Props) => (
  <div className="d-flex">
    <button className="btn btn-primary btn-icon">
      <h5>{title}</h5>
    </button>
    <div className="btn-icon-content">
      <ArrowIcon />
    </div>
  </div>
);

export default ButtonIcon;
