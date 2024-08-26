/* eslint-disable eqeqeq */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/alt-text */
/* eslint-disable no-unreachable */
/* eslint-disable no-script-url */
/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable default-case */
import "./App.css";

import "./css/typo/typo.css";
import "./css/hc-canvas-luckwheel.css";
import axios from "axios";
import hcLuckywheel from "./js/hc-canvas-luckwheel";
import { useEffect, useState } from "react";
import gui from "./util/gui";
import Background from "./images/free/bg1.png";
import Vongquay from "./images/free/Vongquay.png";
import HieuUngSao from "./images/free/HieuUngSao.png";
import Logo from "./images/icon/logo.png";
import IconKimQuay from "./images/icon/IconKimQuay.svg";
import IconLoa from "./images/icon/IconLoa.svg";
import IconLoaTat from "./images/icon/IconLoaTat.svg";
import IconBack from "./images/IconBack.svg";
import bangdon from "./images/icon/bangdon.svg";
import textBangDon from "./images/icon/textBangDon.svg";
import IconVuongMieng from "./images/svg/IconVuongMieng.svg";
import IconTui from "./images/svg/IconTui.svg";
import IconSach from "./images/svg/IconSach.svg";
import IconFree from "./images/svg/IconFree.svg";
import IconX from "./images/svg/IconX.svg";
import logout from "./images/svg/logout.svg";
import { callApi } from "./util/api/requestUtils";
import React from "react";
import Icon1 from "./images/svgquatang/icon1.svg";
import Icon2 from "./images/svgquatang/icon2.svg";
import Icon3 from "./images/svgquatang/icon3.svg";
import Icon4 from "./images/svgquatang/icon4.svg";
import Icon5 from "./images/svgquatang/icon5.svg";
import Icon6 from "./images/svgquatang/icon6.svg";
import mp3Main from "./mp3/lucky_spin.mp3";
import mp3Done from "./mp3/lucky_done.mp3";
import PopupQua from "./components/PopupQua";
import PopupHistory from "./components/PopupHistory";
import ViewText from "./components/ViewText";
import PopupHuongDan from "./components/PopupHuongDan";
import PopupOtp from "./components/PopupOtp";
import PopupBuyTurn from "./components/PopupBuyTurn";
import util from "./util/util";
import PopupPhone from "./components/PopupPhone";
import PopupCancelScreen from "./components/PopupCancelScreen";
import { useTranslation } from "react-i18next";
import "./util/i18n";

export const prizes = [
  {
    text: "Data",
    img: Icon1,
    number: 1, // 1%,
    percentpage: 0.01, // 1%
    giftCode: ["5MB", "55MB", "555MB", "5555MB"],
    lucky: 0,
    label: "5555MB",
  },
  {
    text: "Upoint",
    img: Icon6,
    number: 1,
    percentpage: 0.05, // 5%
    giftCode: ["5UP", "55UP", "555UP", "5555UP"],
    lucky: 1,
    label: "5555 Upoint",
  },
  {
    text: "Voice",
    img: Icon3,
    number: 1,
    percentpage: 0.24, // 24%
    giftCode: ["5S", "55S", "555S", "5555S"],
    lucky: 2,
    label: "5555s",
  },
  {
    text: "Letter",
    img: Icon4,
    number: 1,
    percentpage: 0.1, // 10%
    giftCode: ["U", "Y", "P1", "P", "O", "L", "L1", "K", "I", "C", "A1", "A"],
    lucky: 3,
    label: "avc",
  },
  {
    text: "Iphone 13",
    img: Icon2,
    number: 1, // 1%,
    percentpage: 0.01, // 1%
    giftCode: ["IPHONE"],
    lucky: 4,
  },
  {
    text: "Stars",
    img: Icon5,
    percentpage: 0.6, // 60%
    giftCode: ["5STARS", "55STARS", "555STARS", "5555STARS"],
    lucky: 5,
    label: "5555 Sao",
  },
];

const convertStringToArray = (str) => {
  let converPath = str
    .split(/[&]+/)
    .map((i) => i.trim())
    .map((i) => ({
      type: i.split(/[=]+/)[0],
      value: i.split(/[=]+/)[1],
    }));
  return converPath;
};

const App = () => {
  let ISDN;
  let TOKEN;
  const paramsArray = window.location.search
    ? convertStringToArray(
        window.location.search.slice(1, window.location.search.length)
      )
    : [];

  const uuid = paramsArray.find((o) => o.type === "uuid")?.value || "";
  const languageUrl = paramsArray.find((o) => o.type === "lang")?.value || "LO";
  const code = paramsArray.find((o) => o.type === "code")?.value || "";
  const tokenLocal = localStorage.getItem("token") || "";
  const isdnLocal = localStorage.getItem("isdn") || "";

  const [tokenState, setTokenState] = useState(tokenLocal);

  const [isMute, setIsMute] = useState(false);
  const [loading, setLoading] = useState(false);
  const [showQua, setShowQua] = useState(false);
  const [ItemTrungThuong, setItemTrungThuong] = useState("");
  const [showHistory, setShowHistory] = useState(false);
  const [reload, setReload] = useState(0);
  const [showHuongDan, setShowHuongDan] = useState(false);
  const [isdn, setIsdn] = useState(isdnLocal || "");
  const [countPlayTurn, setCountPlayTurn] = useState(0);

  const [buyMore, setBuyMore] = useState(false);
  const [showInputPhone, setShowInputPhone] = useState(false);

  const [showOtp, setShowOtp] = useState(false);
  const [transId, setTransId] = useState("");

  const [typeOtp, setTypeOtp] = useState("");
  const [cancelVip, setCancelVip] = useState(false);
  const [isVip, setIsVip] = useState(true);

  const { t, i18n } = useTranslation();

  const [accessToken, setAccessToken] = useState("");

  useEffect(() => {
    wsGetIsdn(uuid);
  }, [uuid]);

  useEffect(() => {
    i18n.changeLanguage(languageUrl || "LO");
  }, [languageUrl]);

  useEffect(() => {
    fetchData();
  }, [isdn]);

  const wsGetIsdn = async (id) => {
    if (tokenLocal) {
      if (id) {
        wsGetIsdnSub(id);
      } else {
        setIsdn(isdnLocal);
        setTokenState(tokenLocal);
        ISDN = isdnLocal;
        wsGetLuckyPlayTurn(isdnLocal);
      }
    } else if (id) {
      wsGetIsdnSub(id);
    } else {
      if (!code) {
        localStorage.setItem("token", "");
        localStorage.setItem("isdn", "");
        // return (window.location.href = gui.urlUniId);
      } else {
        getTokenFollowCode(code);
      }
    }
  };

  const getTokenFollowCode = async (code) => {
    const dto = {
      code,
      clientID: gui.clientId,
      secretKey: gui.secretKey,
    };
    const res = await callApi(
      "https://uid.com.la:9998/users/verifier",
      "POST",
      dto
    );
    setAccessToken(res?.data?.accessToken || "");
    if (res?.data?.accessToken) {
      afterGetToken(res?.data?.accessToken);
    }
  };

  const afterGetToken = (acToken) => {
    const defaultInstance = axios.create({
      headers: {
        "Content-Type": "application/json",
        Accept: "*/*",
        Authorization: "Bearer " + acToken,
      },
    });
    const q = "https://uid.com.la:9998/users";
    defaultInstance
      .get(q)
      .then(async (response) => {
        const phoneOfUser = response.data.data.phone;
        const dto2 = {
          wsCode: "wsAddUserToken",
          wsRequest: {
            isdn: phoneOfUser,
            gameCode: "LUCKYGAME",
            language: languageUrl,
            token: acToken,
          },
        };
        const res2 = await callApi("", "POST", dto2);
        const { wsResponse, errorCode } = res2;
        if (errorCode == 10) {
          localStorage.setItem("token", "");
          localStorage.setItem("isdn", "");
          setTimeout(() => {
            window.location.reload();
          }, 200);
        } else {
          if (wsResponse?.token && wsResponse?.isdn) {
            localStorage.setItem("token", wsResponse?.token || "");
            localStorage.setItem("isdn", wsResponse?.isdn || "");
            setTimeout(() => {
              window.location.reload();
            }, 200);
          } else {
            onLogout();
          }
        }

        return;
      })
      .catch((err) => {});
  };

  const wsGetIsdnSub = async (id) => {
    try {
      const dto = {
        wsCode: "wsGetIsdn",
        wsRequest: {
          uuid: id,
          language: languageUrl || "LO",
        },
      };
      const res = await callApi("", "POST", dto);
      const { wsResponse, errorCode } = res;
      if (errorCode == 10) {
        localStorage.setItem("token", "");
        localStorage.setItem("isdn", "");
        setTimeout(() => {
          window.location.reload();
        }, 200);
      } else if (errorCode == 1) {
        setShowInputPhone(true);
      } else {
        if (wsResponse?.isdn) {
          setIsdn(wsResponse?.isdn);
          ISDN = wsResponse?.isdn;
          TOKEN = wsResponse?.token;
          wsGetLuckyPlayTurn(wsResponse?.isdn);
          setTokenState(wsResponse?.token);
        }
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  const wsGetLuckyPlayTurn = async (id) => {
    try {
      const dto = {
        wsCode: "wsGetLuckyPlayTurn", // api lấy số lượt chơi.
        wsRequest: {
          isdn: id,
          gameCode: "LUCKYGAME",
          language: languageUrl || "LO",
          token: tokenState,
        },
      };
      const res = await callApi("", "POST", dto);
      const { errorCode, wsResponse } = res;
      if (res) {
        if (errorCode == 2) {
          wsRegisterGameService(id);
          setShowOtp(true);
          setTypeOtp("OTP");
        } else {
          setCountPlayTurn(wsResponse?.remainingTurn || 0);
        }
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  const checkPlayTurn = async (id) => {
    try {
      const dto = {
        wsCode: "wsGetLuckyPlayTurn", // api lấy số lượt chơi.
        wsRequest: {
          isdn: id,
          gameCode: "LUCKYGAME",
          language: languageUrl || "LO",
          token: tokenState,
        },
      };
      const res = await callApi("", "POST", dto);
      const { wsResponse, errorCode } = res;
      if (errorCode == 10) {
        localStorage.setItem("token", "");
        localStorage.setItem("isdn", "");
        setTimeout(() => {
          window.location.reload();
        }, 200);
      } else {
        setCountPlayTurn(wsResponse?.remainingTurn || 0);
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  const wsRegisterGameService = async (id) => {
    try {
      const randomId = util.genCodeRandom(36);
      setTransId(randomId);
      const dto = {
        wsCode: "wsRegisterGameService",
        wsRequest: {
          isdn: id,
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
  };

  const audio = new Audio(mp3Main);
  const audioDone = new Audio(mp3Done);

  const fetchData = () => {
    setLoading(true);
    hcLuckywheel.init({
      id: "luckywheel",
      config: function (callback) {
        callback && callback(prizes);
      },
      mode: "both",
      getPrize: async function (callback) {
        audio.play();
        try {
          const dto = {
            wsCode: "wsGenerateGift",
            wsRequest: {
              isdn: ISDN || isdn,
              gameCode: "LUCKYGAME",
              language: languageUrl || "LO",
              subType: "1",
              simType: "0",
              token: TOKEN || tokenState,
            },
          };
          const res = await callApi("", "POST", dto);
          const { wsResponse } = res;
          const found =
            wsResponse?.giftCode &&
            prizes.find((o) =>
              o.giftCode.find((k) => k === wsResponse?.giftCode)
            );
          setItemTrungThuong({
            ...wsResponse,
            ...found,
            code: wsResponse?.giftCode,
          });
          if (found) {
            callback && callback([found?.lucky || 0, found?.lucky || 0]);
          } else if (wsResponse?.remainingTurn === 0) {
            alert("Hết lượt quay");
          }
        } catch (error) {
          console.log("error", error);
        } finally {
          setLoading(false);
        }
      },
      gotBack: function (data) {
        audio.pause();
        audioDone.play();
        audio.currentTime = 0;
        setReload((v) => v + 1);
        setShowQua(true);
        wsGetLuckyPlayTurn(ISDN);
      },
    });
  };

  const callbackOk = async () => {
    setShowQua(false);
    audioDone.pause();
    audio.currentTime = 0;
  };

  const onLogout = (v) => {
    localStorage.setItem("token", "");
    localStorage.setItem("isdn", "");
    // return (window.location.href = gui.urlUniId);
  };

  return (
    <div
      style={{
        backgroundColor: "#333",
        width: gui.screenWidth,
        height: gui.screenHeight,
        minHeight: 896,
        overflow: "hidden",
        display: "flex",
        position: "relative",
        alignItems: "center",
        flexDirection: "column",
        backgroundImage: `url(${Background})`,
        // backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
      }}
    >
      <img
        style={{
          position: "absolute",
          left: 22,
          top: 60,
        }}
        src={Logo}
      />
      <img
        style={{
          position: "absolute",
          left: 22,
          top: 20,
          zIndex: 2,
          cursor: "pointer",
        }}
        src={IconBack}
      />
      <img
        onClick={(v) => setCancelVip(true)}
        style={{
          position: "absolute",
          right: 22,
          top: 55,
          cursor: "pointer",
          zIndex: 2,
        }}
        src={IconX}
      />
      <img
        onClick={() => {
          // const found = document.querySelectorAll("audio");
          // audio.muted = true;
          setIsMute((v) => !v);
        }}
        style={{
          position: "absolute",
          right: 80,
          top: 60,
          cursor: "pointer",
          zIndex: 2,
        }}
        src={!isMute ? IconLoa : IconLoaTat}
      />
      <div
        style={{
          display: "flex",
          zIndex: 1,
          position: "absolute",
          top: 92,
          width: gui.screenWidth,
          justifyContent: "center",
          alignItems: "center",
          left: 0,
          height: "auto",
          flexDirection: "column",
        }}
      >
        <div
          style={{
            zIndex: 10,
            position: "relative",
            height: 105,
          }}
        >
          <img style={{}} src={bangdon} />
          <img
            style={{
              position: "absolute",
              zIndex: 12,
              left: 110,
              top: 25,
            }}
            src={textBangDon}
          />
        </div>

        <img
          style={{
            marginTop: -55,
            zIndex: 11,
            width: 390,
            height: 390,
          }}
          src={Vongquay}
        />
        <img
          style={{
            position: "absolute",
            top: -100,
            zIndex: 1,
          }}
          src={HieuUngSao}
        />
      </div>
      <section id="luckywheel" className="hc-luckywheel">
        <div className="hc-luckywheel-container">
          <canvas className="hc-luckywheel-canvas" width="500px" height="500px">
            Vòng Xoay May Mắn
          </canvas>
        </div>

        <img
          style={{
            position: "absolute",
            top: 131,
            left: 134,
            zIndex: 99,
          }}
          src={IconKimQuay}
        />
        <div className="hc-luckywheel-btn">{t("Spin")}</div>

        {countPlayTurn == 0 ? (
          <div
            onClick={
              isdn
                ? () => {
                    if (!isVip) {
                      wsRegisterGameService(isdn);
                      setShowOtp(true);
                      setTypeOtp("OTP");
                    } else {
                      setBuyMore(true);
                    }
                  }
                : null
            }
            className="buy-more-btn"
          >
            {t("Spin")}
          </div>
        ) : null}

        <div
          style={{
            position: "absolute",
            color: "#FFF",
            width: 210,
            left: 70,
            zIndex: 99,
            bottom: -160,
            fontSize: 14,
          }}
        >
          {t("you have")} {countPlayTurn} {t("turns")}
        </div>
      </section>
      <div
        style={{
          position: "absolute",
          bottom: 0,
          width: "100%",
          zIndex: 98,
        }}
      >
        <div
          className="ct-flex-row"
          style={{
            marginBottom: 12,
            justifyContent: "space-between",
            paddingLeft: 16,
            paddingRight: 16,
          }}
        >
          <div className="ct-flex-col">
            <ItemOption
              onClick={(v) => setShowHuongDan(true)}
              icon={IconVuongMieng}
              text={t("Gift")}
            />
            <ItemOption
              icon={IconSach}
              onClick={(v) => setShowHistory(true)}
              text={t("History")}
            />
            <ItemOption
              onClick={
                isdn
                  ? () => {
                      if (!isVip) {
                        wsRegisterGameService(isdn);
                        setShowOtp(true);
                        setTypeOtp("OTP");
                      } else {
                        setBuyMore(true);
                      }
                    }
                  : null
              }
              icon={IconTui}
              text={t("Buy more turn")}
            />
          </div>
          <div className="ct-flex-col">
            <ItemOption icon={IconFree} />
            {/* <ItemOption onClick={(v) => setCancelVip(true)} icon={IconX} /> */}
            <ItemOption onClick={onLogout} text={t("Logout")} icon={logout} />
          </div>
        </div>
        <ViewText
          languageUrl={languageUrl}
          token={tokenState}
          isdn={isdn}
          reload={reload}
        />
      </div>
      {showQua ? (
        <PopupQua
          token={tokenState}
          isdn={isdn}
          languageUrl={languageUrl}
          data={ItemTrungThuong}
          callback={callbackOk}
        />
      ) : null}
      {buyMore ? (
        <PopupBuyTurn
          token={tokenState}
          languageUrl={languageUrl}
          onClose={() => setBuyMore(false)}
          onSuccess={(e) => {
            setBuyMore(false);
            setTransId(e);
            setTimeout(() => {
              setShowOtp(true);
              setTypeOtp("BUYTURN");
            }, 700);
          }}
          isdn={isdn}
        />
      ) : null}

      {cancelVip ? (
        <PopupCancelScreen
          token={tokenState}
          languageUrl={languageUrl}
          onClose={() => setCancelVip(false)}
          onSuccess={(e) => {
            setCancelVip(false);
            // window.location.reload();
            setTypeOtp("OTP");
            setCountPlayTurn(0);
            setIsVip(false);
            // wsRegisterGameService(isdn);
            // setShowInputPhone(true);
            // localStorage.setItem("token", "");
            // localStorage.setItem("isdn", "");
            // setTransId(e);
            // setTimeout(() => {
            //   setShowOtp(true);
            //   setTypeOtp("CANCEL");
            // }, 700);
          }}
          isdn={isdn}
        />
      ) : null}

      {showHistory ? (
        <PopupHistory
          token={tokenState}
          isdn={isdn}
          languageUrl={languageUrl}
          onClose={() => setShowHistory(false)}
        />
      ) : null}
      {showHuongDan ? (
        <PopupHuongDan
          token={tokenState}
          isdn={isdn}
          languageUrl={languageUrl}
          onClose={() => setShowHuongDan(false)}
        />
      ) : null}
      {showInputPhone ? (
        <PopupPhone
          token={tokenState}
          languageUrl={languageUrl}
          onSuccess={(e) => {
            setShowInputPhone(false);
            setIsdn(e.isdn);
            ISDN = e.isdn;
            setTransId(e.transId);
            setTimeout(() => {
              setShowOtp(true);
              setTypeOtp("PHONE");
            }, 700);
          }}
        />
      ) : null}
      {showOtp ? (
        <PopupOtp
          token={tokenState}
          onSuccess={(isdn, token) => {
            checkPlayTurn(isdn);
            setIsdn(isdn);
            ISDN = isdn;
            setTokenState(token);
          }}
          transId={transId}
          typeOtp={typeOtp}
          languageUrl={languageUrl}
          isdn={isdn}
          onClose={() => {
            checkPlayTurn(isdn);
            setShowOtp(false);
          }}
        />
      ) : null}
    </div>
  );
};

const ItemOption = ({ icon, text, onClick, type }) => (
  <div
    className="ct-flex-col"
    style={{
      marginTop: 10,
      marginBottom: text ? 0 : 14,
      fontSize: 12,
      color: "#FFF",
      cursor: "pointer",
    }}
    onClick={() => onClick && onClick(type)}
  >
    <img style={{}} src={icon} />
    {text ? text : ""}
  </div>
);

export default App;
