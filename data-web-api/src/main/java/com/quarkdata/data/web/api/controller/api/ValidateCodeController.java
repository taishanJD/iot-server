package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.dal.rest.quarkshare.ValidateCodeApi;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.web.api.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码获取与校验
 *
 * @author  JiaLei
 */
@Controller
@RequestMapping(value = "/common")
public class ValidateCodeController extends BaseController {

    @Autowired
    private ValidateCodeApi validateCodeApi;

    /**
     * 获取图片验证码
     *
     * @return
     *        ModelMap
     */
    @ResponseBody
    @RequestMapping(value="/get_validate_code", method = RequestMethod.GET)
    public ResultCode<ModelMap> generateCode(){
        return validateCodeApi.getEncodeImg();
    }

    /**
     * 校验验证码
     * @param uuidKey
     *        32位UUID(验证码唯一标识)
     * @param validateCode
     *        用户输入的验证码
     * @return
     *        true / false
     */
    @ResponseBody
    @RequestMapping(value = "/check_validate_code", method = RequestMethod.POST)
    public ResultCode<Boolean> checkValidateCode(@RequestParam(value = "uuidKey") String uuidKey,
                                                 @RequestParam(value = "validateCode") String validateCode) {
        return validateCodeApi.checkValidateCode(uuidKey, validateCode);
    }
}
