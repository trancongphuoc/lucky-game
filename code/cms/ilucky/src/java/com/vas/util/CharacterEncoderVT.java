package com.vas.util;

/**
 *
 * @author Trongtv
 */
import com.viettel.utils.Constant;
import java.io.*;

public abstract class CharacterEncoderVT {

    public CharacterEncoderVT() {
    }

    abstract int bytesPerAtom();

    abstract int bytesPerLine();

    abstract void encodeAtom(OutputStream outputstream, byte abyte0[], int i, int j)
            throws IOException;

    public void encodeBuffer(InputStream inputstream, OutputStream outputstream)
            throws IOException {
        byte abyte0[] = new byte[bytesPerLine()];
        encodeBufferPrefix(outputstream);
        int j;
        do {
            j = readFully(inputstream, abyte0);
            if (j == -1) {
                break;
            }
            encodeLinePrefix(outputstream, j);
            for (int i = 0; i < j; i += bytesPerAtom()) {
                if (i + bytesPerAtom() <= j) {
                    encodeAtom(outputstream, abyte0, i, bytesPerAtom());
                } else {
                    encodeAtom(outputstream, abyte0, i, j - i);
                }
            }

            encodeLineSuffix(outputstream);
        } while (j >= bytesPerLine());
        encodeBufferSuffix(outputstream);
    }

    public String encodeBuffer(byte abyte0[]) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        try {
            encodeBuffer(((InputStream) (bytearrayinputstream)), ((OutputStream) (bytearrayoutputstream)));
        } catch (Exception _ex) {Constant.LogNo(_ex);
            throw new Error("ChracterEncoder::encodeBuffer internal error");
        }
        String s;
        try {
            s = bytearrayoutputstream.toString("UTF8");
        } catch (UnsupportedEncodingException _ex) {Constant.LogNo(_ex);
            throw new Error("CharacterEncoder::encodeBuffer internal error(2)");
        }
        return s;
    }

    public void encodeBuffer(byte abyte0[], OutputStream outputstream)
            throws IOException {
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        encodeBuffer(((InputStream) (bytearrayinputstream)), outputstream);
    }

    void encodeBufferPrefix(OutputStream outputstream)
            throws IOException {
        pStream = new PrintStream(outputstream);
    }

    void encodeBufferSuffix(OutputStream outputstream1)
            throws IOException {
    }

    void encodeLinePrefix(OutputStream outputstream1, int j)
            throws IOException {
    }

    void encodeLineSuffix(OutputStream outputstream)
            throws IOException {
        pStream.println();
    }

    protected static int readFully(InputStream inputstream, byte abyte0[])
            throws IOException {
        for (int i = 0; i < abyte0.length; i++) {
            int j = inputstream.read();
            if (j == -1) {
                return i;
            }
            abyte0[i] = (byte) j;
        }

        return abyte0.length;
    }

    protected PrintStream pStream;
}
