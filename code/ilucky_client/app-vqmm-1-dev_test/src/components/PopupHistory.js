/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable jsx-a11y/alt-text */
import React, { useEffect, useState } from "react";
import PopupHistory1 from "../images/svgPopup/PopupHistory.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi } from "../util/api/requestUtils";
import { prizes } from "../App";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupHistory = ({ onClose, isdn, token, languageUrl }) => {
  const [tab, setTab] = useState(1);
  const { t } = useTranslation();
  const [dataTab1, setDataTab1] = useState([]);
  const [dataTab2, setDataTab2] = useState([]);

  useEffect(() => {
    if (isdn) {
      fetchData();
    }
  }, [isdn]);

  useEffect(() => {
    if (isdn) {
      fetchDataTab2();
    }
  }, [isdn]);

  const fetchDataTab2 = async () => {
    try {
      const dto = {
        wsCode: " wsGetHistoryGiftBuyMore",
        wsRequest: {
          isdn,
          token,
          gameCode: "LUCKYGAME",
          language: languageUrl || "LO",
          otpType: "1",
          transId: "123456",
          otp: "123321",
          subService: "LUCKYGAME",
        },
      };

      const res = await callApi("", "POST", dto);
      const { wsResponse } = res;
      setDataTab2(wsResponse || []);
    } catch (error) {
      console.log("error", error);
    }
  };

  const fetchData = async () => {
    try {
      const dto = {
        wsCode: "wsGetHistoryGift",
        wsRequest: {
          isdn,
          token,
          gameCode: "LUCKYGAME",
          language: languageUrl || "LO",
          pageSize: 300,
          pageNumber: 1,
        },
      };
      const res = await callApi("", "POST", dto);
      const { wsResponse } = res;
      setDataTab1(wsResponse || []);
    } catch (error) {
      console.log("error", error);
    } finally {
      //   setLoading(false);
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
              left: 140,
              color: "#4C2626",
            }}
          >
            {t("History")}
          </div>

          <div
            style={{
              position: "absolute",
              height: 387,
              width: "100%",
              top: 26,
              left: 4,
              padding: "30px 16px 16px 16px",
            }}
          >
            <div
              className="ct-flex-row"
              style={{
                justifyContent: "center",
                fontSize: 14,
                width: 300,
              }}
            >
              <div
                onClick={() => setTab(1)}
                className="btn-history-1 ct-flex-row"
                style={{
                  background:
                    tab === 1
                      ? "linear-gradient(180deg, #FED575 9.09%, #E1A920 49.12%, #FDD78D 92.3%)"
                      : "linear-gradient(180deg, #EEEEEE 9.09%, #D6D4D4 49.12%, #EEEEEE 92.3%)",
                }}
              >
                {t("Prize")}
              </div>
              <div
                onClick={() => setTab(2)}
                className="btn-history-2 ct-flex-row"
                style={{
                  background:
                    tab === 2
                      ? "linear-gradient(180deg, #FED575 9.09%, #E1A920 49.12%, #FDD78D 92.3%)"
                      : "linear-gradient(180deg, #EEEEEE 9.09%, #D6D4D4 49.12%, #EEEEEE 92.3%)",
                }}
              >
                {t("Spins")}
              </div>
            </div>
            {tab === 1 ? (
              <>
                <div
                  className="ct-flex-row"
                  style={{
                    justifyContent: "space-between",
                    width: 300,
                    marginTop: 10,
                    color: "#EBB859",
                    fontSize: 12,
                  }}
                >
                  <div>{t("No.")}</div>
                  <div style={{ marginRight: 80 }}> {t("Prize")}</div>
                  <div>{t("Date/Time")}</div>
                </div>
                <div
                  style={{
                    height: 280,
                    width: "90%",
                    overflow: "auto",
                  }}
                >
                  {dataTab1.map((item, index) => {
                    const foundIcon = prizes.find((o) =>
                      o.giftCode.find((e) => e === item.giftCode)
                    );
                    return (
                      <div
                        key={index.toString()}
                        className="ct-flex-row"
                        style={{
                          justifyContent: "space-between",
                          width: "96%",
                          fontSize: 13,
                          height: 25,
                          borderRadius: 3,
                          marginBottom: 8,
                          backgroundColor: "#B46C6C",
                          paddingRight: 6,
                          paddingLeft: 6,
                        }}
                      >
                        <div className="ct-flex-row">
                          <div>{index + 1}</div>
                          <img
                            style={{
                              width: 15,
                              height: 15,
                              marginLeft: 12,
                              marginRight: 6,
                            }}
                            src={foundIcon?.img}
                          />
                          <div>{item.giftDesc}</div>
                        </div>
                        <div>{item.time}</div>
                      </div>
                    );
                  })}
                </div>
              </>
            ) : (
              <>
                <div
                  className="ct-flex-row"
                  style={{
                    justifyContent: "space-between",
                    width: 300,
                    marginTop: 10,
                    color: "#EBB859",
                    fontSize: 12,
                  }}
                >
                  <div>{t("Turns")}</div>
                  <div>{t("Date/Time")}</div>
                </div>
                <div
                  style={{
                    height: 280,
                    width: "90%",
                    overflow: "auto",
                  }}
                >
                  {dataTab2.map((item, index) => {
                    return (
                      <div
                        key={index.toString()}
                        className="ct-flex-row"
                        style={{
                          justifyContent: "space-between",
                          width: "96%",
                          fontSize: 13,
                          height: 25,
                          borderRadius: 3,
                          marginBottom: 8,
                          backgroundColor: "#B46C6C",
                          paddingRight: 6,
                          paddingLeft: 6,
                        }}
                      >
                        <div>{index + 1}</div>
                        <div>date time</div>
                      </div>
                    );
                  })}
                </div>
              </>
            )}
          </div>
          <img className="" style={{}} src={PopupHistory1} />
        </div>
      </div>
    </>
  );
};

export default PopupHistory;
