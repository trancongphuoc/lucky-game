/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/alt-text */
import React, { useState } from "react";
import PopupPhones from "../images/svgPopup/PopupPhone.svg";

import "./styles.css";
import { callApi } from "../util/api/requestUtils";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupPhone = ({ onSuccess, languageUrl }) => {
  const [phone, setPhone] = useState("");
  const { t } = useTranslation();

  const handleChange = (e) => setPhone(e.target.value);

  const onClickSendOtp = async () => {
    if (phone) {
      try {
        const dto = {
          wsCode: "wsGetOtpGameService",
          wsRequest: {
            isdn: phone,
            gameCode: "LUCKYGAME",
            language: languageUrl || "LO",
          },
        };
        const res = await callApi("", "POST", dto);
        const { wsResponse } = res;
        onSuccess(wsResponse);
      } catch (error) {
        console.log("error", error);
      }
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
            <div style={{}}>{t("Login")}</div>
            <div
              style={{
                width: "70%",
                justifyContent: "center",
                display: "flex",
                marginTop: 8,
              }}
            >
              <input
                style={{
                  width: 186,
                  height: 33,
                  border: "1px solid #B46C6C",
                  backgroundColor: "transparent",
                  borderRadius: 4,
                  color: "#FFF",
                  paddingLeft: 12,
                  paddingRight: 12,
                }}
                onChange={handleChange}
                placeholder={t("Enter the phone number")}
              />
            </div>

            <button
              style={{ marginTop: 20 }}
              onClick={onClickSendOtp}
              className="button-ok"
            >
              {t("Send OTP")}
            </button>
          </div>
          <img className="" style={{}} src={PopupPhones} />
        </div>
      </div>
    </>
  );
};

export default PopupPhone;
