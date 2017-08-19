package ysaak.nimue.core.service;

import org.junit.Assert;

import java.security.SecureRandom;

abstract class AbstractServiceTest {
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    protected String getRandomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    protected void assertNullOrEmpty(String str) {
        Assert.assertTrue(str == null || str.isEmpty());
    }
}
