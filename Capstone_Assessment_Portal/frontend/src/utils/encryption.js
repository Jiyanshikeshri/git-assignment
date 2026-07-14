/**
 * Utility functions for RSA password encryption
 */

import { JSEncrypt } from "jsencrypt";

/**
 * Encrypt password using RSA public key
 */
export const encryptPassword = (
    password,
    publicKey
) => {

    const encryptor = new JSEncrypt();

    encryptor.setPublicKey(publicKey);

    const encryptedPassword = encryptor.encrypt(
        password
    );

    if (!encryptedPassword) {
        throw new Error(
            "Password encryption failed."
        );
    }

    return encryptedPassword;
};