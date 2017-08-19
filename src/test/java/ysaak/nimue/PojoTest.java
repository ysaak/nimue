package ysaak.nimue;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Assertions;
import ysaak.nimue.rest.dto.GreetingDto;

public class PojoTest {


    @Test
    public void testGettersSetters() {
        // given
        final Class<?> classUnderTest = GreetingDto.class;

        // when

        // then
        Assertions.assertPojoMethodsFor(classUnderTest).areWellImplemented();
    }
}
