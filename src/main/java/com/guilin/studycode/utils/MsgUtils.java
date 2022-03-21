package com.guilin.studycode.utils;

/**
 * @description:消息报文校验位计算
 * @author: puguilin
 * @date: 2022/3/10
 * @version: 1.0
 *
 *
 * 参考来源： https://www.iteye.com/blog/zpball-1631877
 */

public class MsgUtils {

    public static void main(String[] args) {
        MsgUtils msg = new MsgUtils();
        String str = "312e3030696e7465726e616c43323830";
        String s = msg.calculateSum(str);
        System.out.println("s " + s);
    }

    /**
     *
     * 312e 3030 696e 7465 726e 616c 4332 3830
     * 2020 2020 3338 3331 3236 3434 444c 4743
     * 4f4e 3030 3030 3030 3030 3030 3032 5458
     * 4245 4720 3030 3030 4144 4420 4332 3830
     * 2055 5345 523a 4c4f 4749 4e4e 414d 453d
     * 3938 3933 3532 3033 3330 3734 406d 746e
     * 6972 616e 6365 6c6c 2e63 6f6d 2c50 4159
     * 5459 5045 3d32 2c50 5357 443d 7073 7764
     * 7769 6d61 782c 5553 4552 5459 5045 3d30
     * 2c41 4354 4956 4546
     */

    // 消息头＋会话头＋事务头＋操作信息”按32位异或，对异或结果取反后的值为校验和
    public String calculateSum(String msg){
        byte[] arr = msg.getBytes();
        byte[] res = new byte[4];

        //异或操作
        for (int i = 0; i < arr.length; i += 4) {
            res[0] ^= arr[i];
            res[1] ^= arr[i + 1];
            res[2] ^= arr[i + 2];
            res[3] ^= arr[i + 3];
        }

        //取反
        res[0] = (byte) ~res[0];
        res[1] = (byte) ~res[1];
        res[2] = (byte) ~res[2];
        res[3] = (byte) ~res[3];


        String resStr = "";
        for (int i = 0; i < 4; i++) {
            resStr = resStr + byte16(res[i]);
        }
        return resStr;
    }



    /**
     * @param b:
     * @return String
     * @author guilin
     * @description  将单字节转化为16进制
     * @date 2022/3/10 10:38
     */
    private String byte16(byte b) {
        StringBuffer buf = new StringBuffer();
        char[] hexChars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F'
        };

        /**
         *16进制表示的16位或32位整数，高8位位组在前，低8位位组在后。举个例子：
         *     int n = 148;         转换成4字节，16进制
         *     int hi = n >> 8 ;    高16位
         *     int lo = n & 0x00ff;    低16位
         *     String hig = Integer.toHexString(hi);    长度不足，再补0
         *     String low = Integer.toHexString(lo);  16进制
         */

        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
        return buf.toString();
    }


}
