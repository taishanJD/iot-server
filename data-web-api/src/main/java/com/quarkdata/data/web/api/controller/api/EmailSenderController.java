package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.web.api.controller.BaseController;
import com.quarkdata.data.web.api.controller.RouteKey;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(RouteKey.EMAILSENDER)
public class EmailSenderController extends BaseController {

    @Autowired
    private EmailSenderService emailSenderService;

    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResultCode sendEmail(@RequestParam(value = "receiver") String[] receiver,
                                @RequestParam(value = "emailSubject") String emailSubject,
                                @RequestParam(value = "emailContent") String emailContent,
                                @RequestParam(value = "emailContentType") String emailContentType) {
        return emailSenderService.sendEmail(receiver, emailSubject, emailContent, emailContentType);
    }
}
