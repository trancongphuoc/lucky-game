/* eslint-disable eqeqeq */
/* eslint-disable jsx-a11y/alt-text */
import React from "react";
import bgIcon from "../images/svgquatang/bgIcon.png";

import A from "../images/quatang/A.svg";
import C from "../images/quatang/C.svg";
import A1 from "../images/quatang/A1.svg";
import I from "../images/quatang/I.svg";
import K from "../images/quatang/K.svg";
import L1 from "../images/quatang/L1.svg";
import O from "../images/quatang/O.svg";
import P from "../images/quatang/P.svg";
import P1 from "../images/quatang/P1.svg";
import U from "../images/quatang/U.svg";
import Y from "../images/quatang/Y.svg";
import L from "../images/quatang/L.svg";
import "./styles.css";

const arrFound = [
  {
    code: "A",
    icon: A,
  },
  {
    code: "A1",
    icon: A1,
  },
  {
    code: "C",
    icon: C,
  },
  {
    code: "I",
    icon: I,
  },
  {
    code: "K",
    icon: K,
  },
  {
    code: "L",
    icon: L,
  },
  {
    code: "L1",
    icon: L1,
  },
  {
    code: "O",
    icon: O,
  },
  {
    code: "P",
    icon: P,
  },
  {
    code: "P1",
    icon: P1,
  },
  {
    code: "U",
    icon: U,
  },
  {
    code: "Y",
    icon: Y,
  },
];

const PopupQua = ({ callback, data }) => {
  const found = arrFound.find((o) => o.code == data.code);
  return (
    <>
      <div className="main-qua ct-flex-col">
        <div>ລາງວັນຂອງທ່ານ</div>
        <div
          style={{ margin: "50px 0px 50px 0px", alignItems: "center" }}
          className="ct-flex-col"
        >
          {found ? (
            <img
              className="popup-icon-qua"
              width={120}
              height={120}
              style={{}}
              src={found.icon}
            />
          ) : (
            <img
              className="popup-icon-qua"
              width={120}
              height={120}
              style={{}}
              src={data.img}
            />
          )}

          <div style={{ marginTop: 10 }}>{data.giftMsg}</div>
        </div>
        <button onClick={() => callback()} className="button-ok">
          OK
        </button>
      </div>
      <div className="main-qua-sub ct-flex-col">
        <img className="" style={{}} src={bgIcon} />
      </div>
    </>
  );
};

export default PopupQua;
