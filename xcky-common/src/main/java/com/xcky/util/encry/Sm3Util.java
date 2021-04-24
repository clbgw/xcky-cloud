package com.xcky.util.encry;

import com.xcky.util.Constants;
import com.xcky.util.Util;

/**
 * SM3消息摘要算法
 *
 * @author lbchen
 */
public class Sm3Util {
    /**
     * Sm3值的长度
     */
    public static final int BYTE_LENGTH = 32;
    
    /**
     * SM3分组长度
     */
    private static final int BLOCK_LENGTH = 64;
    
    /**
     * 缓冲区长度
     */
    private static final int BUFFER_LENGTH = BLOCK_LENGTH;
    
    /**
     * 缓冲区
     */
    private final byte[] xBuf = new byte[BUFFER_LENGTH];
    
    /**
     * 缓冲区偏移量
     */
    private int xBufOff;
    
    /**
     * 初始向量
     */
    private byte[] v = Sm3.IV.clone();
    
    private int cntBlock = 0;
    
    public Sm3Util() {
    }
    
    public Sm3Util(Sm3Util t) {
        System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
        this.xBufOff = t.xBufOff;
        System.arraycopy(t.v, 0, this.v, 0, t.v.length);
    }
    
    /**
     * 签名
     *
     * @param src 源字符串
     * @return 签名后的字节数组
     */
    public byte[] sign(byte[] src) {
        if (null == src) {
            throw new IllegalArgumentException("Source ( byte array ) is null! ");
        }
        reset();
        update(src, src.length);
        byte[] out = new byte[BYTE_LENGTH];
        doFinal(out);
        return out;
    }
    
    /**
     * 验签
     *
     * @param src       源字符串
     * @param signBytes 签名后的字节数组
     * @return 签名结果, true为真, false为假
     */
    public boolean verify(byte[] src, byte[] signBytes) {
        if (null == src) {
            throw new IllegalArgumentException("Source ( byte array ) is null! ");
        }
        byte[] newSignBytes = sign(src);
        int len = newSignBytes.length;
        if (null == signBytes || len != signBytes.length) {
            return false;
        }
        int i = 0;
        while (i < len) {
            if (newSignBytes[i] != signBytes[i]) {
                return false;
            }
            i++;
        }
        return true;
    }
    
    /**
     * SM3结果输出
     *
     * @param out 保存SM3结构的缓冲区
     * @return 字节长度
     */
    private int doFinal(byte[] out) {
        byte[] tmp = doFinal();
        System.arraycopy(tmp, 0, out, 0, tmp.length);
        return BYTE_LENGTH;
    }
    
    /**
     * 重置
     */
    private void reset() {
        xBufOff = 0;
        cntBlock = 0;
        v = Sm3.IV.clone();
    }
    
    /**
     * 明文输入
     *
     * @param in  明文输入缓冲区
     * @param len 明文长度
     */
    private void update(byte[] in, int len) {
        int partLen = BUFFER_LENGTH - xBufOff;
        int inputLen = len;
        int dPos = 0;
        if (partLen < inputLen) {
            System.arraycopy(in, dPos, xBuf, xBufOff, partLen);
            inputLen -= partLen;
            dPos += partLen;
            doUpdate();
            while (inputLen > BUFFER_LENGTH) {
                System.arraycopy(in, dPos, xBuf, 0, BUFFER_LENGTH);
                inputLen -= BUFFER_LENGTH;
                dPos += BUFFER_LENGTH;
                doUpdate();
            }
        }
        System.arraycopy(in, dPos, xBuf, xBufOff, inputLen);
        xBufOff += inputLen;
    }
    
    /**
     * 更新
     */
    private void doUpdate() {
        byte[] b = new byte[BLOCK_LENGTH];
        for (int i = 0; i < BUFFER_LENGTH; i += BLOCK_LENGTH) {
            System.arraycopy(xBuf, i, b, 0, b.length);
            doHash(b);
        }
        xBufOff = 0;
    }
    
    /**
     * 转16进制
     *
     * @param b 字节数组
     */
    private void doHash(byte[] b) {
        byte[] tmp = Sm3.cf(v, b);
        System.arraycopy(tmp, 0, v, 0, v.length);
        cntBlock++;
    }
    
    private byte[] doFinal() {
        byte[] b = new byte[BLOCK_LENGTH];
        byte[] buffer = new byte[xBufOff];
        System.arraycopy(xBuf, 0, buffer, 0, buffer.length);
        byte[] tmp = Sm3.padding(buffer, cntBlock);
        for (int i = 0; i < tmp.length; i += BLOCK_LENGTH) {
            System.arraycopy(tmp, i, b, 0, b.length);
            doHash(b);
        }
        return v;
    }
    
    static class Sm3 {
        public static final byte[] IV = {
                0x73, (byte) 0x80, 0x16, 0x6f, 0x49, 0x14, (byte) 0xb2, (byte) 0xb9,
                0x17, 0x24, 0x42, (byte) 0xd7, (byte) 0xda, (byte) 0x8a, 0x06, 0x00,
                (byte) 0xa9, 0x6f, 0x30, (byte) 0xbc, (byte) 0x16, 0x31, 0x38, (byte) 0xaa,
                (byte) 0xe3, (byte) 0x8d, (byte) 0xee, 0x4d, (byte) 0xb0, (byte) 0xfb, 0x0e, 0x4e
        };
        
        public static int[] Tj = new int[64];
        
        static {
            for (int i = 0; i < Constants.SIX_TEEN; i++) {
                Tj[i] = 0x79cc4519;
            }
            
            for (int i = Constants.SIX_TEEN; i < Constants.SIXTY_FOUR; i++) {
                Tj[i] = 0x7a879d8a;
            }
        }
        
        private static byte[] cf(byte[] vArr, byte[] bArr) {
            int[] v, b;
            v = convert(vArr);
            b = convert(bArr);
            return convert(cf(v, b));
        }
        
        private static int[] convert(byte[] arr) {
            int bitLen = 4;
            int[] out = new int[arr.length / bitLen];
            byte[] tmp = new byte[bitLen];
            for (int i = 0; i < arr.length; i += bitLen) {
                System.arraycopy(arr, i, tmp, 0, bitLen);
                out[i / bitLen] = bigEndianByteToInt(tmp);
            }
            return out;
        }
        
        private static byte[] convert(int[] arr) {
            byte[] out = new byte[arr.length * 4];
            byte[] tmp;
            for (int i = 0; i < arr.length; i++) {
                tmp = bigEndianIntToByte(arr[i]);
                System.arraycopy(tmp, 0, out, i * 4, 4);
            }
            return out;
        }
        
        private static int[] cf(int[] vArr, int[] bArr) {
            int a, b, c, d, e, f, g, h;
            int ss1, ss2, tt1, tt2;
            a = vArr[0];
            b = vArr[1];
            c = vArr[2];
            d = vArr[3];
            e = vArr[4];
            f = vArr[5];
            g = vArr[6];
            h = vArr[7];
            
            int[][] arr = expand(bArr);
            int[] w = arr[0];
            int[] w1 = arr[1];
            
            for (int j = 0; j < Constants.SIXTY_FOUR; j++) {
                ss1 = (bitCycleLeft(a, 12) + e + bitCycleLeft(Tj[j], j));
                ss1 = bitCycleLeft(ss1, 7);
                ss2 = ss1 ^ bitCycleLeft(a, 12);
                tt1 = ffj(a, b, c, j) + d + ss2 + w1[j];
                tt2 = ggj(e, f, g, j) + h + ss1 + w[j];
                d = c;
                c = bitCycleLeft(b, 9);
                b = a;
                a = tt1;
                h = g;
                g = bitCycleLeft(f, 19);
                f = e;
                e = p0(tt2);
            }
            
            int[] out = new int[8];
            out[0] = a ^ vArr[0];
            out[1] = b ^ vArr[1];
            out[2] = c ^ vArr[2];
            out[3] = d ^ vArr[3];
            out[4] = e ^ vArr[4];
            out[5] = f ^ vArr[5];
            out[6] = g ^ vArr[6];
            out[7] = h ^ vArr[7];
            
            return out;
        }
        
        private static int[][] expand(int[] b) {
            int[] w = new int[68];
            int[] w1 = new int[64];
            System.arraycopy(b, 0, w, 0, b.length);
            for (int i = Constants.SIX_TEEN; i < Constants.SIXTY_EIGHT; i++) {
                w[i] = p1(w[i - 16] ^ w[i - 9] ^ bitCycleLeft(w[i - 3], 15))
                        ^ bitCycleLeft(w[i - 13], 7) ^ w[i - 6];
            }
            
            for (int i = 0; i < Constants.SIX_TEEN; i++) {
                w1[i] = w[i] ^ w[i + 4];
            }
            
            return new int[][]{w, w1};
        }
        
        private static byte[] bigEndianIntToByte(int num) {
            return back(Util.intToBytes(num));
        }
        
        private static int bigEndianByteToInt(byte[] bytes) {
            return Util.byteToInt(back(bytes));
        }
        
        private static int ffj(int x, int y, int z, int j) {
            if (j >= 0 && j < Constants.SIX_TEEN) {
                return ff1j(x, y, z);
            } else {
                return ff2j(x, y, z);
            }
        }
        
        private static int ggj(int x, int y, int z, int j) {
            if (j >= 0 && j < Constants.SIX_TEEN) {
                return gg1j(x, y, z);
            } else {
                return gg2j(x, y, z);
            }
        }
        
        private static int ff1j(int x, int y, int z) {
            return x ^ y ^ z;
        }
        
        private static int ff2j(int x, int y, int z) {
            return (x & y) | (x & z) | (y & z);
        }
        
        private static int gg1j(int x, int y, int z) {
            return x ^ y ^ z;
        }
        
        private static int gg2j(int x, int y, int z) {
            return (x & y) | (~x & z);
        }
        
        private static int p0(int x) {
            int y = bitCycleLeft(x, 9);
            int z = bitCycleLeft(x, 17);
            return x ^ y ^ z;
        }
        
        private static int p1(int x) {
            return x ^ bitCycleLeft(x, 15) ^ bitCycleLeft(x, 23);
        }
        
        /**
         * 对最后一个分组字节数据padding
         *
         * @param in   输入字节
         * @param bLen 分组个数
         * @return 分组好的字节
         */
        private static byte[] padding(byte[] in, int bLen) {
            int k = 448 - (8 * in.length + 1) % 512;
            if (k < 0) {
                k = 960 - (8 * in.length + 1) % 512;
            }
            k += 1;
            byte[] padd = new byte[k / 8];
            padd[0] = (byte) 0x80;
            long n = in.length * 8 + bLen * 512;
            byte[] out = new byte[in.length + k / 8 + 64 / 8];
            int pos = 0;
            System.arraycopy(in, 0, out, 0, in.length);
            pos += in.length;
            System.arraycopy(padd, 0, out, pos, padd.length);
            pos += padd.length;
            byte[] tmp = back(Util.longToBytes(n));
            System.arraycopy(tmp, 0, out, pos, tmp.length);
            return out;
        }
        
        /**
         * 字节数组逆序
         *
         * @param in 输入字节
         * @return 排序好的字节
         */
        private static byte[] back(byte[] in) {
            int len = in.length;
            byte[] out = new byte[len];
            for (int i = 0; i < len; i++) {
                out[i] = in[len - i - 1];
            }
            return out;
        }
        
        private static int bitCycleLeft(int n, int bitLen) {
            bitLen %= 32;
            byte[] tmp = bigEndianIntToByte(n);
            int byteLen = bitLen / 8;
            int len = bitLen % 8;
            if (byteLen > 0) {
                tmp = byteCycleLeft(tmp, byteLen);
            }
            
            if (len > 0) {
                tmp = bitSmall8CycleLeft(tmp, len);
            }
            
            return bigEndianByteToInt(tmp);
        }
        
        private static byte[] bitSmall8CycleLeft(byte[] in, int len) {
            byte[] tmp = new byte[in.length];
            int t1, t2, t3;
            for (int i = 0; i < tmp.length; i++) {
                t1 = (byte) ((in[i] & 0x000000ff) << len);
                t2 = (byte) ((in[(i + 1) % tmp.length] & 0x000000ff) >> (8 - len));
                t3 = (byte) (t1 | t2);
                tmp[i] = (byte) t3;
            }
            
            return tmp;
        }
        
        private static byte[] byteCycleLeft(byte[] in, int byteLen) {
            byte[] tmp = new byte[in.length];
            System.arraycopy(in, byteLen, tmp, 0, in.length - byteLen);
            System.arraycopy(in, 0, tmp, in.length - byteLen, byteLen);
            return tmp;
        }
    }
}