package com.css.fingerprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 指纹加密类
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class CipherHelper {

    private static CipherHelper instance;

    private static final String DEFAULT_KEY_NAME = "defaultKey";

    private static final String KEYSTORE_ALIAS = "keyStoreAlias";

    private static final String HAS_FINGER_KEY = "hasFingerKey";

    private KeyGenerator mKeyGenerator;

    private KeyStore keyStore;

    private CipherHelper() {
        try {
            //用keyStore获取我们的secret key
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //使用KeyGenerator的生成的密钥加密算法是AES，我们将在AndroidKeyStore中保存密钥／数据。
            mKeyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CipherHelper getInstance() {
        if (instance == null) {
            synchronized (CipherHelper.class) {
                if (instance == null) {
                    instance = new CipherHelper();
                }
            }
        }
        return instance;
    }

    /**
     * @des 创建cipher (Cipher类提供了加密和解密的功能) AES/CBC/PKCS7Padding
     */
    public Cipher createCipher() {
        try {
            //getInstance(String transformation)  返回实现指定转换的 Cipher 对象。
            // 参数按"算法/模式/填充模式"
            return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @des 初始化Cipher ,根据KeyPermanentlyInvalidatedExceptiony异常判断指纹库是否发生了变化
     */
    public boolean initCipher(Cipher cipher) {
        try {
            keyStore.load(null);
            //获取密钥
            SecretKey key = (SecretKey) keyStore.getKey(KEYSTORE_ALIAS, null);
            if (cipher == null) {
                cipher = createCipher();
            }
            //ENCRYPT_MODE 加密模式，key为密钥    进行加密
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return false;
        } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e) {
            //该密钥已被永久无效 |未能获得有关私钥
            //指纹库是否发生了变化,这里会抛KeyPermanentlyInvalidatedException
            return true;
        } catch (KeyStoreException | CertificateException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            //密钥库异常|
//            throw new RuntimeException("Failed to init Cipher", e);
            e.printStackTrace();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * @param context
     * @param createNewKey 是否创建新的密钥
     * @des 根据当前指纹库创建一个密钥
     */
    void createKey(Context context, boolean createNewKey) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFAULT_KEY_NAME, Context.MODE_PRIVATE);
        try {
            //首先通过标志位判断用户之前是否创建了密钥,如果已经创建过了并且不需要重新创建就跳过
            if (TextUtils.isEmpty(sharedPreferences.getString(HAS_FINGER_KEY, "")) || createNewKey) {
                //生成密钥参数  1.别名，这个名字可以是任意的  2.设置意图，是加密还是解密
                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEYSTORE_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        //保证了只有指定的block模式下可以加密，解密数据，如果使用其它的block模式，将会被拒绝
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setInvalidatedByBiometricEnrollment(true);
                }
                //始化KeyGenerator
                mKeyGenerator.init(builder.build());
                //生成了SecretKey（加密密钥成功）
                mKeyGenerator.generateKey();
                //用于判断是否存有密钥
                sharedPreferences.edit().putString(HAS_FINGER_KEY, "KEY").apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}