package com.xcky.util;

import com.xcky.util.encry.RsaUtil;

import java.util.Map;

/**
 * @author lbchen
 */
public class RsaUtilTest {
    
    public static void main(String[] args) {
        Map<String, String> keyMap = RsaUtil.createKeys(2048);
        String publicKeyTemp = keyMap.get("publicKey");
        String privateKeyOfPkcs8Temp = keyMap.get("privateKeyOfPKCS8");
        System.out.println("随机生成的公钥:" + publicKeyTemp);
        System.out.println("随机生成的私钥:" + privateKeyOfPkcs8Temp);
        
        String publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxKz0-Eqac7bbHc5p5Sy-AqrpPJTB0f9t6MJeAH3Fj-UjdWtfb923fQFkxixgCz5Hf_rFIKuoTnrw2gJzBz73wgN_EJLVS6reQXWqEK2wKVvE7tOMPHz3ZFyF2ELGxw10rEP1OCO49bAskaUudUFwmURN6FWDyjPgGXXpujOuLZlsAYuz4AKJbqHm7Jui_7Un9yjyDmhWTYUhJX9Y8X2VZjk7Sn2SbiDKkSI3YBtufVsP8OSLKlKDvrYJjRhnIJxlBay-D6bKgcLVJ47f_uvYw-mMYQ8qNmaf-VDpCmwuGOEejLope5t_iz1lwFYOqv2oKth2gkJjzLTzvvm9DIlauQIDAQAB";
        String privateKeyOfPkcs8 =
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDErPT4SppzttsdzmnlLL4Cquk8lMHR_23owl4AfcWP5SN1a19v3bd9AWTGLGALPkd_" +
                        "-sUgq6hOevDaAnMHPvfCA38QktVLqt5BdaoQrbApW8Tu04w8fPdkXIXYQsbHDXSsQ_U4I7j1sCyRpS51QXCZRE3oVYPKM-AZdem6M64tmWwBi7PgAoluoebsm6L_tSf3KPIOaFZNhSElf1jxfZVmOTtKfZJuIMqRIjdgG259Ww_w5IsqUoO-tgmNGGcgnGUFrL4PpsqBwtUnjt_-69jD6YxhDyo2Zp_5UOkKbC4Y4R6Muil7m3-LPWXAVg6q_agq2HaCQmPMtPO--b0MiVq5AgMBAAECggEACO_bCdVKA2wRtN8GTNWlpLVjd89Cp2o18leysc8FRS6iV9mTMqAekF2Z2K93hctEsHoSrxNdyIMpop5t36xq0bQa-APUEWYoR00zdyKNWLPE1R1jx_Pdwf-6TUNriOwBmzpTUEO_SdrBi7S30bgxh6Vk7OPF41BMJJwJDN8AUS4NJWfoJ0ZfYsp0tf4HRyyNjrUfE0vZx5TyTxWnvDFfM3uliljqsMMKFS7rmaaGF-0epaX4F1n_1TbCivvd_QaZyr6P1QMk69PaexyhlSTcjnOMurZ1-AtrkVF28QpaxSL4xp5duhEg44vsdUsIaCc9mfNxs6-Z6b0-XgECq_2NIQKBgQDnab-Cw1LD_XodOpbBHxFoxZQ1Vm6pG7E7V3_XS4Yq1hAjOI3blWSY_Y63Oq7xkkCrO7ik4_YuFnMt0ln3C-3CX0K278igF4xg_S1InzjnoNX8S8S-ND9TAqh5ScJHY302egUfph_E49UN_b5xRU6Qr-2kx7sSYbAO9tnQsFF3dQKBgQDZkmCaZun_dBd_YlpknGYiSZetuZ1uUWZjpyVCkGFgLIqpLWfNZZEjoZaHJWnCVqAainsCL3yOHfsDd3wpg4ZVgst2DedIlzDs8pTEGCOUhvUHuN0EdvHdUbDWsZg1fr1Q--oiYd_hc5ZrQVi3FZiXHqycgYIx1w_jTI_2PvixtQKBgQCyRzSkD8ged5PxGqbzhvTJi5V_dePw4gWrGuDBQ0zMiXeLOtGGvlPuzUB6hnoqXyr1ACBi38BThzYasfhfK9BysyLZfmdIdnvOaJ3PWeLkB3NzWAftJKE4WU7jieBjUREwu9ZgqIHHFGKZ0cc6ylMZgn-JN9o18g5JqQDrFjrc1QKBgBnho8Q6QdO1h6YLmR0mza9-eaCc1_FrlXZ9R51nd23tXoFJeXOhhWZrrnBdLvuDOoBtwLw90d9GCbob2JVHpA9AA9wDQ2QLODK3mKLLDFB6zWMhxojlbVgag6LGdYjHjtW3Yd69Ldiv21De8SK2WvR_HaFwdoBfSPCNBwMP8p2RAoGBAOb9FyYkK6Pn3bj1nzLI6YmADkhIMZO1Rq2aRw2nh0hbm3DSl8mpE-kENiaC8fDDeldkID6C3V2xx6SDYdzC26mNUQoEN00blurhubobAp_-GYWGFh1j8vbSF_KJaJOVgMspNs4fTG8RHtb0hIM0GA1U7U2z7ZrlVG3yE-H5ny-k";
        
        System.out.println("公钥:" + publicKey);
        System.out.println("私钥:" + privateKeyOfPkcs8);
        String src = "param1=abc&param2=123";
        String signStr = RsaUtil.sign(src, privateKeyOfPkcs8, Constants.CHARSET);
        boolean verify = RsaUtil.verify(src, signStr, publicKey, Constants.CHARSET);
        
        System.out.println("源字符串:" + src);
        System.out.println("签名后字符串:" + signStr);
        System.err.println("验签结果:" + verify);
    }
}
