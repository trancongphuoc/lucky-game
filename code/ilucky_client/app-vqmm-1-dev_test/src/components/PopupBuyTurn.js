/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/alt-text */
import React from "react";
import PopupOTP from "../images/svgPopup/PopupBuyTurn.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi } from "../util/api/requestUtils";
import util from "../util/util";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupBuyTurn = ({ onClose, isdn, onSuccess, token, languageUrl }) => {
  const { t } = useTranslation();

  const onSubmit = async () => {
    const randomId = util.genCodeRandom(36);
    try {
      const dto = {
        wsCode: "wsBuyMoreTurnGameService",
        wsRequest: {
          isdn,
          token,
          gameCode: "LUCKYGAME",
          language: languageUrl || "LO",
          otpType: "0",
          transId: randomId,
          subService: "LUCKYGAME",
        },
      };
      const res = await callApi("", "POST", dto);
      // console.log("res", res);
      if (res) {
        onSuccess(randomId);
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <>
      <div className="main-history ct-flex-col">
        <div
          style={{
            position: "relative",
            width: 330,
            height: gui.screenHeight,
            marginTop: 80,
          }}
        >
          <div
            onClick={onClose}
            style={{
              position: "absolute",
              zIndex: 10000,
              top: 10,
              right: -16,
              cursor: "pointer",
            }}
          >
            <img className="" src={IconX} />
          </div>
          <div
            style={{
              position: "absolute",
              height: 130,
              width: "88%",
              top: 24,
              left: 8,
              padding: "24px 16px 0 16px",
              justifyContent: "center",
            }}
            className="ct-flex-col"
          >
            <div
              style={{
                width: 118,
                height: 33,
                border: "1px solid #B46C6C",
                backgroundColor: "#FFF",
                borderRadius: 50,
                justifyContent: "center",
                color: "#000",
              }}
              className="ct-flex-row"
            >
              5
            </div>
            <div style={{ fontSize: 12, marginTop: 6 }}>
              {t("200 Kip/5 turns/day")}
            </div>
            <button
              style={{ marginTop: 8 }}
              onClick={onSubmit}
              className="button-ok"
            >
              {t("Buy")}
            </button>
          </div>
          <img className="" style={{}} src={PopupOTP} />
        </div>
      </div>
    </>
  );
};

export default PopupBuyTurn;
