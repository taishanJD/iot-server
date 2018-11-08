package com.quarkdata.data.web.api.controller.api;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.DataSetSchemaService;
import com.quarkdata.data.service.PreviewService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.PREVIEW)
public class PreviewController {
    static Logger logger = Logger.getLogger(DataSetController.class);


    @Autowired
    DataSetSchemaService dataSetSchemaService;

    @Autowired
    PreviewService previewService;


    @ResponseBody
    @RequestMapping(value = "/get_sample_filter_method", method = RequestMethod.GET)
    public ResultCode getSampleAndFilterMethod(@RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = previewService.getSampleAndFilterMethod(dataSetId);
        } catch (Exception e) {
            logger.error("获取采样及过滤方式失败", e);
//            resultCode = ResultUtil.error(Messages.GET_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/update_sample_filter_method", method = RequestMethod.POST)
    public ResultCode updateSampleAndFilterMethod(@RequestBody String param) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            Map<String, Object> paramMap = JSON.parseObject(param);
            resultCode = previewService.updateSampleFilterMethod(paramMap);
        } catch (Exception e) {
            logger.error("更新采样及过滤方式失败", e);
//            resultCode = ResultUtil.error(Messages.GET_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/is_mongo_data_exist", method = RequestMethod.GET)
    public ResultCode isMongoDataExist(@RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            boolean res = previewService.isMongoDataExist(dataSetId);
            resultCode = ResultUtil.success(res);
        } catch (Exception e) {
            logger.error("检查mongo数据失败", e);
            resultCode = ResultUtil.error(Messages.DATASET_PREVIEW_FAILED_CODE, Messages.DATASET_PREVIEW_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/sync_data", method = RequestMethod.POST)
    public ResultCode syncData(@RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = previewService.syncData(dataSetId);
        } catch (Exception e) {
            logger.error("重新采样数据失败", e);
            resultCode = ResultUtil.error(Messages.DATASET_PREVIEW_FAILED_CODE, Messages.DATASET_PREVIEW_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/sync_proportion", method = RequestMethod.POST)
    public ResultCode syncProportion(@RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
           resultCode = previewService.syncProportion(dataSetId);
        } catch (Exception e) {
            logger.error("检查mongo数据失败", e);
            resultCode = ResultUtil.error(Messages.DATASET_PREVIEW_FAILED_CODE, Messages.DATASET_PREVIEW_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/get_data", method = RequestMethod.GET)
    public ResultCode getData(@RequestParam(value = "dataSetId") Long dataSetId,
                              @RequestParam(value = "pageNum") Integer pageNum,
                              @RequestParam(value = "pageSize") Integer pageSize,
                              @RequestParam(value = "search", required = false) String search) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = previewService.getData(dataSetId, pageNum, pageSize, search);
        } catch (Exception e) {
            logger.error("获取mongo数据失败", e);
            resultCode = ResultUtil.error(Messages.DATASET_PREVIEW_FAILED_CODE, Messages.DATASET_PREVIEW_FAILED_MSG);
        }
        return resultCode;
    }
}
