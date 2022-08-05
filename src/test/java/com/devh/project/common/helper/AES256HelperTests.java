package com.devh.project.common.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class AES256HelperTests {
	@InjectMocks
    AES256Helper aes256Helper;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(aes256Helper, "key", "devh0000000000000000000000000000");
    }

    @Test
    public void encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        // given
        final String givenString = "test";
        // when
        String encryptedString = aes256Helper.encrypt(givenString);
        // then
        System.out.println(encryptedString);
    }

    @Test
    public void decrypt() throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidParameterSpecException {
        // given
        final String givenString = "tMUSeRyXcMH9gaZ7wFGs1aFVVj22NFPbwTZhhFvDXbuLdU+Ym2QoyydNF5M1T7gg";
        // when
        String decryptedString = aes256Helper.decrypt(givenString);
        // then
        assertEquals(decryptedString, "test");
    }

    @Test
    public void decryptByCryptoJS() throws Exception {
        // given
        final String givenString = "21ac82b912eaa161f7a38331409f2760cb82b51f9d3285151fe158ef0dace117LuvDo/pdkFLGF3LvnmCyjg==";
        // when
        String decryptedString = aes256Helper.decryptByCryptoJS(givenString);
        // then
        assertEquals(decryptedString, "test");
    }

}
