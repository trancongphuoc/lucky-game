/* eslint-disable eqeqeq */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/alt-text */
import React, { useEffect, useState } from "react";
import PopupOTP from "../images/svgPopup/PopupOTP.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi } from "../util/api/requestUtils";
import util from "../util/util";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const arrType = [
  {
    type: "PHONE",
    value: "wsLoginOtpGameService",
  },
  {
    type: "CANCEL",
    value: "wsCancelGameService",
  },
  {
    type: "BUYTURN",
    value: "wsBuyMoreTurnGameService",
  },
];

const PopupOtp = ({
  onClose,
  isdn,
  transId,
  typeOtp,
  onSuccess,
  token,
  languageUrl,
}) => {
  const [otp, setOpt] = useState("");
  const [checkSuccess, setCheckSuccess] = useState(false);
  const [transIdState, setTransIdState] = useState(transId || "");
  const [errorOtp, setErrorOtp] = useState(false);

  const [tokenState, setTokenState] = useState("");
  const [messageError, setMessageError] = useState("");
  const { t } = useTranslation();

  const handleChange = (e) => setOpt(e.target.value);

  const found = arrType.find((e) => e.type === typeOtp);

  const onClickSendOtp = async () => {
    if (otp) {
      try {
        const dto = {
          wsCode: found ? found.value : "wsRegisterGameService",
          wsRequest: {
            isdn,
            gameCode: "LUCKYGAME",
            language: languageUrl || "LO",
            otpType: "1",
            transId: transIdState,
            otp,
          },
        };

        if (typeOtp !== "PHONE") {
          dto.wsRequest.subService = "LUCKYGAME";
        }

        const res = await callApi("", "POST", dto);
        const { errorCode, message } = res;
        if (errorCode == 0) {
          if (found?.type === "CANCEL") {
            localStorage.setItem("token", "");
            localStorage.setItem("isdn", "");
            window.location.reload();
            onClose();
          } else if (found?.type === "BUYTURN") {
            onClose();
            window.location.reload();
          } else {
            setCheckSuccess(true);
            localStorage.setItem("token", res.wsResponse.token || token);
            localStorage.setItem("isdn", res.wsResponse.isdn || isdn);
            setTokenState(res.wsResponse.token);
          }
        } else if (errorCode == 401) {
          setOpt("");
          setCheckSuccess(true);
          setMessageError(message);
        } else if (errorCode == 416) {
          setOpt("");
          setCheckSuccess(false);
          setErrorOtp(true);
        } else {
          setOpt("");
          setCheckSuccess(false);
        }
      } catch (error) {
        console.log("error", error);
      }
    }
  };

  const reSendOtp = async () => {
    if (typeOtp === "PHONE") {
      try {
        const dto = {
          wsCode: "wsGetOtpGameService",
          wsRequest: {
            isdn,
            token,
            gameCode: "LUCKYGAME",
            language: languageUrl || "LO",
          },
        };
        const res = await callApi("", "POST", dto);
        const { wsResponse } = res;
        setTransIdState(wsResponse.transId);
      } catch (error) {
        console.log("error", error);
      }
    } else if (typeOtp === "BUYTURN") {
      const randomId = util.genCodeRandom(36);
      setTransIdState(randomId);
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
        await callApi("", "POST", dto);
      } catch (error) {
        console.log("error", error);
      }
    } else {
      try {
        const randomId = util.genCodeRandom(36);
        setTransIdState(randomId);
        const dto = {
          wsCode: "wsRegisterGameService",
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
        await callApi("", "POST", dto);
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
          {checkSuccess ? (
            <div
              onClick={() => {
                onClose();
                onSuccess(isdn, tokenState);
                window.location.href = `${window.location.origin}?uuid=&lang=${
                  languageUrl || "LO"
                }`;
                setTimeout(() => {
                  window.location.reload();
                }, 200);
              }}
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
          ) : null}

          {checkSuccess ? (
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
              {messageError ? (
                <div>{messageError}</div>
              ) : (
                <>
                  <div style={{}}>
                    {t("Congratulations, you have successfully registered for")}
                  </div>
                  <div style={{}}>{t("VIP MEMBER")}</div>
                </>
              )}
            </div>
          ) : (
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
              {errorOtp ? (
                <div style={{ fontSize: 12 }}>
                  {t("OTP code is wrong, please get OTP again")}
                </div>
              ) : (
                <div style={{ fontSize: 12 }}>
                  {t("Please enter OTP code sent to SMS")}
                </div>
              )}

              <div
                style={{
                  width: "70%",
                  justifyContent: "center",
                  display: "flex",
                  marginTop: 8,
                }}
              >
                {/* <OtpInput
                  value={otp}
                  onChange={handleChange}
                  numInputs={4}
                  inputStyle={{
                    backgroundColor: "#B46C6C",
                    borderRadius: 4,
                    borderWidth: 0,
                    width: 25,
                    height: 25,
                    marginLeft: 6,
                    marginRight: 6,
                  }}
                  // separator={<span>-</span>}
                /> */}
                <input
                  value={otp}
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
                  placeholder="otp"
                />
              </div>
              <div style={{ fontSize: 12, marginTop: 6 }}>
                {t("OTP code error, request")}{" "}
                <span onClick={reSendOtp} style={{ color: "#FED575" }}>
                  {t("OTP code")}
                </span>
              </div>
              <button
                style={{ marginTop: 8 }}
                onClick={onClickSendOtp}
                className="button-ok"
              >
                {t("Confirm")}
              </button>
            </div>
          )}

          <img className="" style={{}} src={PopupOTP} />
        </div>
      </div>
    </>
  );
};

export default PopupOtp;
