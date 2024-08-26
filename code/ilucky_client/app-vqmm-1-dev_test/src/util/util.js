const util = {
  genCodeRandom: function (otpLength) {
    var otpRandom = "";
    let possible =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (let i = 0; i < otpLength; i++) {
      otpRandom += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return otpRandom;
  },
};

export default util;
