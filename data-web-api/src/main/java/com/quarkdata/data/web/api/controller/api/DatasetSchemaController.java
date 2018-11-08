package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.DataSetSchemaService;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.DATASETSCHEMA)
public class DatasetSchemaController {

    static Logger logger = Logger.getLogger(DatasetSchemaController.class);

    @Autowired
    DataSetSchemaService dataSetSchemaService;

    @ResponseBody
    @RequestMapping(value = "/update_meaning", method = RequestMethod.POST)
    public ResultCode updateMeaning(
            @RequestParam(value = "dataSetId") Long dataSetId,
            @RequestParam(value = "columnName") String columnName,
            @RequestParam(value = "meaning") String meaning,
            @RequestParam(value = "subMeaning") String subMeaning) {

        ResultCode resultCode = null;
        try {
            resultCode = dataSetSchemaService.updateSchemaMeaning(dataSetId, columnName, meaning, subMeaning);
        } catch (Exception e) {
            logger.error("更新采样及过滤方式失败", e);
//            resultCode = ResultUtil.error(Messages.GET_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/get_schema", method = RequestMethod.GET)
    public ResultCode getSchema(
            @RequestParam(value = "dataSetId") Long dataSetId,
            @RequestParam(value = "filter",required = false) String filter,
            @RequestParam(value = "columnType",required = false) String columnType,
            @RequestParam(value = "meaning",required = false) String meaning,
            @RequestParam(value = "subMeaning",required = false) String subMeaning) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = dataSetSchemaService.getSchema(dataSetId, filter, columnType, meaning, subMeaning);
        } catch (Exception e) {
            logger.error("获取数据集结构失败", e);
//            resultCode = ResultUtil.error(Messages.GET_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/check_schema", method = RequestMethod.GET)
    public ResultCode checkSchema(
            @RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = dataSetSchemaService.checkSchema(dataSetId);
        } catch (Exception e) {
            logger.error("检查数据集结构失败", e);
//            resultCode = ResultUtil.error(Messages.GET_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/update_type", method = RequestMethod.POST)
    public ResultCode updateSchemaType(
            @RequestParam(value = "dataSetId") Long dataSetId,
            @RequestParam(value = "columnName") String columnName,
            @RequestParam(value = "columnType") String columnType) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = dataSetSchemaService.updateSchemaType(dataSetId, columnName, columnType);
        } catch (Exception e) {
            logger.error("更新数据集类型失败", e);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/reload_schema", method = RequestMethod.POST)
    public ResultCode reloadSchema(@RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = dataSetSchemaService.reloadSchema(dataSetId);
        } catch (Exception e) {
            logger.error("重新加载数据集结构失败", e);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/get_structure", method = RequestMethod.GET)
    public ResultCode getStructure(@RequestParam(value = "dataSetId") Long dataSetId) {
//        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
//        Long tenantId = 1l;
//        Long createUser = 1l;
        ResultCode resultCode = null;
        try {
            resultCode = dataSetSchemaService.getStructure(dataSetId);
        } catch (Exception e) {
            logger.error("获取数据集结构失败", e);
            resultCode = ResultUtil.error(Messages.GET_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }
}
