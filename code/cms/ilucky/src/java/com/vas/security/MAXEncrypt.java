/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.security;

/**
 *
 * @author hanhnv
 */
import com.viettel.utils.Constant;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
//hungtv45 change base64 packet sun.misc because it old
import javax.xml.bind.DatatypeConverter;

// Referenced classes of package com.fss.iportal.security:
//            EncryptPackage
public class MAXEncrypt
        implements EncryptPackage {

    public MAXEncrypt() {
        m_strPassPhase = "32142122";
    }

    public void encrypterGeneration() {
        byte salt[] = {
            -87, -101, -56, 50, 86, 52, -29, 3
        };
        int iterationCount = 19;
        try {
            java.security.spec.KeySpec keySpec = new PBEKeySpec(m_strPassPhase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());
            java.security.spec.AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            ecipher.init(1, key, paramSpec);
            dcipher.init(2, key, paramSpec);
        } catch (Exception _e) {Constant.LogNo(_e);
//            //e.printStackTrace();
        }
    }

    public String decode(String saData)
            throws Exception {
        label0:
        {
            byte enc[];
            try {
                byte baData[];
                baData = DatatypeConverter.parseBase64Binary(saData);
                enc = dcipher.doFinal(baData);
            } catch (BadPaddingException _e) {Constant.LogNo(_e);
                break label0;
            } catch (IllegalBlockSizeException _e) {Constant.LogNo(_e);
                break label0;
            }
            return new String(enc, "UTF8");
        }
        return null;
    }

    public String encode(String saData)
            throws Exception {
        label0:
        {
            byte utf8[] = null;
            byte baData[] = null;
            try {
                baData = saData.getBytes("UTF8");
                utf8 = ecipher.doFinal(baData);
            } catch (BadPaddingException _e) {Constant.LogNo(_e);
                //e.printStackTrace();
                break label0;
            } catch (IllegalBlockSizeException _e) {Constant.LogNo(_e);
                //e.printStackTrace();
                break label0;
            }
            //hungtv45 change base64 packet sun because it old
            return DatatypeConverter.printBase64Binary(utf8);
//            return (new BASE64Encoder()).encode(utf8);
        }
        return null;
    }

    public byte[] decode(byte baData[])
            throws Exception {
        label0:
        {
            byte enc[];
            try {
                enc = dcipher.doFinal(baData);
            } catch (BadPaddingException _e) {Constant.LogNo(_e);
                break label0;
            } catch (IllegalBlockSizeException _e) {Constant.LogNo(_e);
                break label0;
            }
            return enc;
        }
        return null;
    }

    public byte[] encode(byte baData[])
            throws Exception {
        label0:
        {
            byte utf8[] = null;
            try {
                utf8 = ecipher.doFinal(baData);
            } catch (BadPaddingException _e) {Constant.LogNo(_e);
                //e.printStackTrace();
                break label0;
            } catch (IllegalBlockSizeException _e) {Constant.LogNo(_e);
                //e.printStackTrace();
                break label0;
            }
            return utf8;
        }
        return null;
    }

    String m_strPassPhase;
    Cipher dcipher;
    Cipher ecipher;
}
