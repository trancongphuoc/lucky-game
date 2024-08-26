/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/alt-text */
import React from "react";
import PopupOTP from "../images/svgPopup/PopupCancel.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi } from "../util/api/requestUtils";
import util from "../util/util";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupCancelScreen = ({
  onClose,
  isdn,
  onSuccess,
  token,
  languageUrl,
}) => {
  const { t } = useTranslation();

  const onSubmit = async () => {
    const randomId = util.genCodeRandom(36);
    try {
      const dto = {
        wsCode: "wsCancelGameService",
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
            <div style={{ fontSize: 12 }}>
              {t("Do you want to cancel VIP MEMBER?")}
            </div>
            <div className="ct-flex-row" style={{ marginTop: 30 }}>
              <button onClick={onSubmit} className="button-yes">
                Yes
              </button>
              <button onClick={onClose} className="button-no">
                No
              </button>
            </div>
          </div>
          <img className="" style={{}} src={PopupOTP} />
        </div>
      </div>
    </>
  );
};

export default PopupCancelScreen;
