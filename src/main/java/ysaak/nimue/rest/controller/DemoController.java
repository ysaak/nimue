package ysaak.nimue.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ysaak.common.exception.BusinessException;
import ysaak.common.exception.generic.GenericErrorCode;
import ysaak.nimue.rest.dto.GreetingDto;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DemoController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public GreetingDto greeting(@RequestParam(value="name", defaultValue="World") String name) {

        return new GreetingDto(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/greetingKo")
    public GreetingDto errorResponse() throws BusinessException {

        throw new BusinessException(GenericErrorCode.NO_DATA_FOUND, "booo");
    }
}
